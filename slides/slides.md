---
theme: default
class: 'text-center'
highlighter: shiki
lineNumbers: true
transition: none
# use UnoCSS
css: unocss
aspectRatio: "16/9"
colorSchema: "light"
hideInToc: true
---

# SSO en live-coding
## (sans framework)

<br>
<br>

### Daniel Garnier-Moiroux

SnowCamp, 2024-02-01


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

- <logos-spring-icon /> Broadcom+Tanzu+Spring
- <logos-mastodon-icon /> @Kehrlann@hachyderm.io
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
title: OAuth2
layout: fact
level: 3
---

## Notez: ça ne parle pas d'**identité** ...

---
title: OpenID Connect
level: 2
---

# OpenID Connect

<br>

- Un framework d' *[🇬🇧 Authentication]*
  - But: fournir à des **applications** tierces des données sur son identité détenues par un **provider
d'identité**.
  - Et donc faire du **Single-Sign-On** (SSO)
- Basé sur OAuth2, avec des jetons appelés `id_token`
- Et bien sûr... des specs!
  - **https://openid.net/developers/specs/**

---
layout: section
title: "Pourquoi et comment, en images"
level: 1
---

# Pourquoi et comment?

---
layout: image
image: /sso-1-give-password.png
hideInToc: true
---

---
layout: image
image: /sso-2-give-password-bad-idea.png
hideInToc: true
---

---
layout: image
image: /sso-3-high-level-authorization-code.png
hideInToc: true
---

---
layout: image
image: /sso-4-high-level-token-types.png
hideInToc: true
---

---
layout: image
image: /sso-3-high-level-authorization-code.png
hideInToc: true
---

---
layout: image
image: /sso-5-more-details-oauth.png
hideInToc: true
---

---
layout: image
image: /sso-6-just-sso.png
hideInToc: true
---

---
layout: center
title: Live-coding
---

# ~~~ Du code!

<img src="/cat-code.gif" style="width: 600px; text-align:center;" />

---
layout: image-right
image: /no-idea.jpg
hideInToc: true
---

## 🚨**WARNING**🚨

<br>Ce live-coding est réalisé par un professionel<sup>1</sup>. N'essayez surtout pas de le
reproduire en prod.

<br><sup>1</sup> ou pas 🥸

---
layout: image
image: /sso-7-get-code.png
hideInToc: true
---

---
layout: image
image: /sso-8-code-where.png
hideInToc: true
---

---
layout: image
image: /sso-9-authorization-uri.png
hideInToc: true
---

---
layout: image
image: /sso-10-redirect.png
hideInToc: true
---

---
layout: image
image: /sso-11-exchange-token.png
hideInToc: true
---

---
layout: image
image: /sso-12-jwt.png
hideInToc: true
---

---
layout: image
image: /sso-13-access-token.png
hideInToc: true
---

---
layout: image-right
image: /safety-cat.jpg
hideInToc: true
---

## Rappel!

<br>Ne faites pas ça! Utilisez une lib.

---
layout: default
hideInToc: true
---

# References

<br>

### **https://github.com/Kehrlann/sso-live-coding**

<!-- ouch the hack -->
<!-- https://mobile.devoxx.com/events/dvbe23/talks/2943/details -->
<div style="float:right; margin-right: 50px; text-align: center;">
  <img src="/qr-code.png" style="margin-bottom: -45px; margin-top: -15px;" >
</div>

<br>

- <logos-mastodon-icon /> @Kehrlann@hachyderm.io
- <logos-twitter /> @Kehrlann
- <logos-firefox /> https://garnier.wf/
- <fluent-emoji-flat-envelope-with-arrow /> dgarnier@vmware.com


---
layout: image
hideInToc: true
image: /meet-me.jpg
class: end
---

# **Merci 😊**

