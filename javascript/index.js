const express = require('express')
const app = express()

app.use(express.static('static'))
app.use(require('express-session')({ secret: "sessionsecret", resave: true, saveUninitialized: true }))

app.engine('handlebars', require('express-handlebars').engine())
app.set('view engine', 'handlebars')

app.get('/', (req, res) => {
  session = req.session
  if (!session.username) {
    res.render('anonymous')
  } else {
    res.render('authenticated', {
      username: session.username,
      attributes: session.attributes
    })
  }
})

app.post('/login', (req, res) => {
  session = req.session
  session.username = "Daniel"
  session.attributes = {
    "firstName": "Daniel",
    "lastName": "Garnier-Moiroux",
    "company": "VMware",
    "userType": "hardcoded",
  }
  res.redirect('/')
})

app.post('/logout', (req, res) => {
  session = req.session
  session.username = null
  session.attributes = null
  res.redirect('/')
})


app.listen(3000, () => {
  console.log(`Example app listening on port ${3000}`)
})
