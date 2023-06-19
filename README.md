# Live coding SSO

This repo is a companion for a "live-coding Single Sign On" conference.

## How to run the examples

1. Clone this repo
2. Copy `template.env` to `.env` at the root of the repo
3. Obtain a client_id and client_secret from Google, and set the following redirect uris:
    1. `http://localhost:8080/oauth2/callback` (for java)
    2. `http://localhost:5000/oauth2/callback` (for python)
    3. `http://localhost:3000/oauth2/callback` (for node)
4. Save the credentials in your `.env` file


### java

Using JDK 17+, navigate to the `java` directory and start the app with gradle:

```
cd java
./gradlew bootRun
open http://localhost:8080
```


### python

Using [Poetry](https://python-poetry.org/), navigate to the `python` directory,
and run the app with `flask`:

```
cd python
poetry install
poetry run flask run
open http://localhost:5000
```


### nodejs

Using node 18, navigate to the `javascript` directory, and run with `start` (or `live`):

```
cd javascript
npm install
npm run start
open http://localhost:3000
```
