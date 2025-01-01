const uuid = require("uuid");
const express = require("express");

const app = express();
const port = 3001;

app.use(express.static("static"));
app.use(
  require("express-session")({
    secret: "sessionsecret",
    resave: true,
    saveUninitialized: true,
  })
);

app.engine("handlebars", require("express-handlebars").engine());
app.set("view engine", "handlebars");

const config = require("dotenv").config({ path: "../../.env" }).parsed;

// Use Passport-JS for session management
const passport = require("passport");
app.use(passport.authenticate("session"));
passport.serializeUser(function (user, cb) {
  process.nextTick(function () {
    cb(null, user);
  });
});

passport.deserializeUser(function (user, cb) {
  process.nextTick(function () {
    return cb(null, user);
  });
});

// Use passport-openidconnect to use OIDC login
// See: https://www.passportjs.org/packages/passport-openidconnect/
const OpenIDConnectStrategy = require("passport-openidconnect");
passport.use(
  new OpenIDConnectStrategy(
    {
      issuer: "https://accounts.google.com",
      authorizationURL:
        "https://accounts.google.com/o/oauth2/v2/auth",
      tokenURL: "https://oauth2.googleapis.com/token",
      userInfoURL: "https://openidconnect.googleapis.com/v1/userinfo",
      clientID: config.SSO_CLIENT_ID,
      clientSecret: config.SSO_CLIENT_SECRET,
      callbackURL: `http://localhost:${port}/oauth2/callback`,
      skipUserProfile: false,
      scope: "profile email",
    },
    (issuer, profile, cb) => {
      return cb(null, profile);
    }
  )
);

app.get("/", (req, res) => {
  if (!req.user) {
    res.render("anonymous");
  } else {
    res.render("authenticated", {
      username: req.user.displayName,
      attributes: req.user,
      helpers: {
        json: JSON.stringify,
      },
    });
  }
});

app.post("/logout", (req, res) => {
  req.logout(() => res.redirect("/"));
});

// The login page now uses passport
app.get("/login", passport.authenticate("openidconnect"));

// You do need to register the callback URL
app.get(
  "/oauth2/callback",
  passport.authenticate("openidconnect", {
    failureRedirect: "/failure",
    failureMessage: true,
  }),
  (req, res) => {
    res.redirect("/");
  }
);

app.get("/failure", (req, res) => {
  console.error(req.session.messages);
  res.redirect("/");
});

app.listen(port, () => {
  console.log(`Example app running at http://localhost:${port}`);
});
