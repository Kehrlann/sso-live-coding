import json
from uuid import uuid4
from flask import Flask, render_template, redirect, url_for, session, request
from requests import post
from urllib.parse import urlencode
from base64 import urlsafe_b64decode
from dotenv import dotenv_values

config = dotenv_values("../.env")

app = Flask(__name__)
app.secret_key = uuid4().bytes

client_id = config["SSO_CLIENT_ID"]
client_secret = config["SSO_CLIENT_SECRET"]
redirect_uri = "http://localhost:5000/oauth2/callback"


@app.route("/")
def hello():
    if "username" in session:
        print(session["attributes"])
        return render_template(
            "authenticated.html",
            username=session["username"],
            attributes=session["attributes"],
        )

    encodedParams = urlencode(
        {
            "response_type": "code",
            "client_id": client_id,
            "redirect_uri": redirect_uri,
            "scope": "openid profile email",
        }
    )

    login_uri = "https://accounts.google.com/o/oauth2/v2/auth?" + encodedParams
    return render_template("anonymous.html", login_uri=login_uri)


@app.route("/login", methods=["POST"])
def login():
    session["username"] = "Daniel"
    session["attributes"] = {
        "firstName": "Daniel",
        "lastName": "Garnier-Moiroux",
        "team": "Spring",
        "userType": "hardcoded",
    }
    return redirect(url_for("hello"))


@app.route("/logout", methods=["POST"])
def logout():
    session.clear()
    return redirect(url_for("hello"))


@app.route("/oauth2/callback")
def callback():
    code = request.args.get("code")
    resp = post(
        "https://oauth2.googleapis.com/token",
        data={
            "grant_type": "authorization_code",
            "code": code,
            "redirect_uri": redirect_uri,
        },
        auth=(client_id, client_secret),
    ).json()
    payload = resp["id_token"].split(".")[1]
    decoded = urlsafe_b64decode(payload)
    claims = json.loads(decoded)
    session["username"] = claims["email"]
    session["attributes"] = claims
    return redirect(url_for("hello"))


if __name__ == "__main__":
    app.run(debug=True)
