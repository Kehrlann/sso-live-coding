import json
from uuid import uuid4
from flask import Flask, render_template, redirect, url_for, session, request
from uuid import uuid4

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
        "team": "Spring",
        "userType": "hardcoded",
    }
    return redirect(url_for("hello"))


@app.route("/logout", methods=["POST"])
def logout():
    session.clear()
    return redirect(url_for("hello"))


if __name__ == "__main__":
    app.run(debug=True)
