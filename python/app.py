import json
from uuid import uuid4
from flask import Flask, render_template, redirect, url_for, session, request
from requests import post, get
from urllib.parse import urlencode
from base64 import urlsafe_b64decode

app = Flask(__name__)
app.secret_key = uuid4().bytes

@app.route("/")
def hello():
    if "username" in session:
        return render_template(
            "authenticated.html",
            username=session["username"],
            attributes=session["attributes"],
        )

    return render_template("anonymous.html")


@app.route("/login", methods=["POST"])
def login():
    session["username"] = "Daniel"
    session["attributes"] = {
        "firstName": "Daniel",
        "lastName": "Garnier-Moiroux",
        "company": "VMware",
        "userType": "hardcoded",
    }
    return redirect(url_for("hello"))


@app.route("/logout", methods=["POST"])
def logout():
    session.clear()
    return redirect(url_for("hello"))


@app.route("/conferences")
def conferences():
    error = None
    conferences = []

    response = get(
        "http://localhost:8081/conferences?userId=-1"
    )
    if response.status_code != 200:
        error = response.text
    else:
        conferences = response.json()

    return render_template("conferences.html", conferences=conferences, error=error)


if __name__ == "__main__":
    app.run(debug=True)
