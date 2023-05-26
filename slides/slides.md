---
theme: default
class: 'text-center'
highlighter: shiki
lineNumbers: true
transition: none
# use UnoCSS
css: unocss
aspectRatio: "16/10"
hideInToc: true
---

# SSO en live coding
## (sans framework)

<br>
<br>

### Daniel Garnier-Moiroux

Riviera Dev, 2023-07-11

<!-- 
presenter notes go here 
TODO: splash image
-->

---
layout: image-right
image: /daniel-intro.jpg
hideInToc: true
class: smaller
---

#### Daniel
### Garnier-Moiroux
<br>

Software Engineer

- <logos-spring-icon /> VMWare+Tanzu+Spring
- <logos-twitter /> @Kehrlann
- <logos-firefox /> https://garnier.wf/
- <logos-github-icon /> github.com/Kehrlann/
- <fluent-emoji-flat-envelope-with-arrow /> dgarnier@vmware.com

---
hideInToc: true
---

# Le programme

<Toc maxDepth="2"></Toc>

---
layout: section
title: "OAuth2, OpenID: qu'est-ce que c'est?"
---

# OAuth2, OpenID
<br >

## C'est quoi, au juste?

---
title: OAuth2
level: 2
---

# OAuth 2 & 2.1

<br>

- Un framework d' *[🇬🇧 Authorization]*
  - Pour donner la **permission** à des **applications** d'accéder à des **ressources** via **HTTP**.
- Via des *jetons* appelés `access_token`
- Un ensemble de spécifications
  - **https://oauth.net/specs/** (parfois un peu ... rudes ...)

Notez: ça ne parle pas d'**identité**...

---
title: OAuth2 - Exemple
level: 3
---

# OAuth 2 & 2.1

Par exemple:

🧑🏻 **Daniel**

autorise

🖥️ **mon-album-photo.example.com**

à accéder à ses photos

📸 **Google Photos**

(sans partager son 🔐 mot de passe Google)

---
title: OpenID Connect
level: 2
---

# OpenID Connect

<br>

- Un framework d' *[🇬🇧 Authentication]*
  - But: fournir à des **applications** tierces des données sur son identités détenues par un **provider
d'identité**.
  - Et donc faire du **Single-Sign-On**  (SSO)
- Basé sur OAuth2, avec des jetons appelés `id_token`
- Et bien sûr... des specs!
  - **https://openid.net/developers/specs/**

---
layout: section
title: "Pourquoi et comment?"
---

# Pourquoi et comment?

---
layout: image
title: "Don't do this"
image: /sso-1-give-password.png
---

---
layout: image
title: "Don't do this - bad idea"
image: /sso-2-give-password-bad-idea.png
---

---
layout: image
title: "High level authorization_code overview"
image: /sso-3-high-level-authorization-code.png
---

---
layout: image
title: "High level overview - token types"
image: /sso-4-high-level-token-types.png
---

---
layout: image
title: "High level authorization_code overview"
image: /sso-3-high-level-authorization-code.png
---

---
layout: image
title: "Closer look"
image: /sso-5-more-details-oauth.png
---

---
layout: image
title: "Just SSO"
image: /sso-6-just-sso.png
---


---
layout: center
title: Live-coding
---

# ~~~ Let's code!

<img src="/cat-code.gif" style="width: 600px; text-align:center;" />
