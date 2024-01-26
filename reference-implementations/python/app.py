from uuid import uuid4

from dotenv import dotenv_values
from flask import Flask, redirect, render_template, session, url_for

config = dotenv_values("../../.env")

app = Flask(__name__)
app.secret_key = uuid4().bytes

app.config["OIDC_CLIENT_SECRETS"] = {
    "web": {
        "auth_uri": "https://dev-51438889.okta.com/oauth2/default/v1/authorize",
        "client_id": config["SSO_CLIENT_ID"],
        "client_secret": config["SSO_CLIENT_SECRET"],
        "issuer": "https://dev-51438889.okta.com/oauth2/default",
        "redirect_uris": ["https://localhost:5001/authorize"],
        "token_uri": "https://dev-51438889.okta.com/oauth2/default/v1/token",
    }
}
app.config["OIDC_SCOPES"] = "openid email profile"

from flask_oidc import OpenIDConnect

oidc = OpenIDConnect(app)


@app.route("/")
def hello():
    if oidc.user_loggedin:
        return render_template(
            "authenticated.html",
            username=session["oidc_auth_profile"].get("name"),
            attributes=session["oidc_auth_profile"],
        )
    return render_template("anonymous.html")


@app.route("/login")
@oidc.require_login
def login():
    return redirect(url_for("hello"))


if __name__ == "__main__":
    app.run(debug=True, port=5001)
