const uuid = require("uuid");
const express = require("express");
const app = express();

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

const config = require("dotenv").config({ path: "../.env" }).parsed;
const client_id = config.SSO_CLIENT_ID;
const client_secret = config.SSO_CLIENT_SECRET;
const redirect_uri = "http://localhost:3000/oauth2/callback";

app.get("/", (req, res) => {
  session = req.session;
  if (!session.username) {
    const loginUri = new URL(
      "https://dev-51438889.okta.com/oauth2/default/v1/authorize"
    );
    loginUri.searchParams.append("client_id", client_id);
    loginUri.searchParams.append("redirect_uri", redirect_uri);
    loginUri.searchParams.append("response_type", "code");
    loginUri.searchParams.append("scope", "openid email profile conference.list");
    loginUri.searchParams.append("state", uuid.v4());
    res.render("anonymous", { loginUri });
  } else {
    res.render("authenticated", {
      username: session.username,
      attributes: session.attributes,
    });
  }
});

app.post("/login", (req, res) => {
  session = req.session;
  session.username = "Daniel";
  session.attributes = {
    firstName: "Daniel",
    lastName: "Garnier-Moiroux",
    company: "VMware",
    userType: "hardcoded",
  };
  res.redirect("/");
});

app.post("/logout", (req, res) => {
  session = req.session;
  session.destroy(() => res.redirect("/"));
});

app.get("/oauth2/callback", (req, res) => {
  const code = req.query.code;
  console.log(code);
  fetch("https://dev-51438889.okta.com/oauth2/default/v1/token", {
    method: "POST",
    body: new URLSearchParams({
      code,
      redirect_uri,
      grant_type: "authorization_code",
    }),
    headers: {
      authorization: `Basic ${btoa(client_id + ":" + client_secret)}`,
      "Content-Type": "application/x-www-form-urlencoded",
    },
  })
    .then((r) => r.json())
    .then((r) => {
      console.log(r);
      const id_token = r.id_token;
      const payload = id_token.split(".")[1];
      const parsed = JSON.parse(atob(payload));
      console.log(parsed);
      const session = req.session;
      session.username = parsed.email;
      session.attributes = parsed;
      session.access_token = r.access_token;
      res.redirect("/");
    });
});

app.get("/conferences", (req, res) => {
  fetch("http://localhost:8081/conferences", {
    headers: {
      authorization: `Bearer ${req.session.access_token}`,
    },
  })
    .then((r) => {
      if (r.status == 200) {
        return r.json();
      } else {
        return r.text().then((t) => {
          throw new Error(t);
        });
      }
    })
    .then((r) =>
      res.render("conferences", {
        error: null,
        conferences: r,
      })
    )
    .catch((e) => {
      console.log(e);
      res.render("conferences", {
        error: e,
        conferences: [],
      });
    });
});

app.listen(3000, () => {
  console.log(`Example app running at http://localhost:3000`);
});
