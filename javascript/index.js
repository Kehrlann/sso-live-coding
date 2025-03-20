const express = require("express");
const app = express();

app.use(express.static("static"));
app.use(
  require("express-session")({
    secret: "sessionsecret",
    resave: true,
    saveUninitialized: true
  })
);

app.engine("handlebars", require("express-handlebars").engine());
app.set("view engine", "handlebars");

const config = require("dotenv").config({ path: "../.env" }).parsed;
const client_id = config.SSO_CLIENT_ID;
const client_secret = config.SSO_CLIENT_SECRET;
const redirect_uri = "http://localhost:3000/oauth2/callback";

app.get("/", (req, res) => {
  session = req.session;
  if (!session.username) {
    const loginUri = new URL("https://accounts.google.com/o/oauth2/v2/auth");
    loginUri.searchParams.append("client_id", client_id);
    loginUri.searchParams.append("redirect_uri", redirect_uri);
    loginUri.searchParams.append("response_type", "code");
    loginUri.searchParams.append("scope", "openid email profile");
    res.render("anonymous", { loginUri });
  } else {
    res.render("authenticated", {
      username: session.username,
      attributes: session.attributes
    });
  }
});

app.post("/login", (req, res) => {
  session = req.session;
  session.username = "Daniel";
  session.attributes = {
    firstName: "Daniel",
    lastName: "Garnier-Moiroux",
    team: "Spring",
    userType: "hardcoded"
  };
  res.redirect("/");
});

app.post("/logout", (req, res) => {
  session = req.session;
  session.username = null;
  session.attributes = null;
  res.redirect("/");
});

app.get("/oauth2/callback", (req, res) => {
  const code = req.query.code;
  console.log(code);
  fetch("https://oauth2.googleapis.com/token", {
    method: "POST",
    body: new URLSearchParams({
      code,
      client_id,
      redirect_uri,
      grant_type: "authorization_code"
    }),
    headers: {
      authorization: `Basic ${btoa(client_id + ":" + client_secret)}`,
      "Content-Type": "application/x-www-form-urlencoded"
    }
  })
    .then(r => r.json())
    .then(r => {
      const id_token = r.id_token;
      const payload = id_token.split(".")[1];
      const parsed = JSON.parse(atob(payload));
      console.log(parsed);
      const session = req.session;
      session.username = parsed.email;
      session.attributes = parsed;
      res.redirect("/");
    });
});


app.listen(3000, () => {
  console.log(`Example app running at http://localhost:3000`);
});
