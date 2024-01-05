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

# Live-coding SSO
## (from scratch!)

<br>
<br>

### Daniel Garnier-Moiroux

Devoxx Belgium, 2023-10-05

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

- <logos-spring-icon /> Broadcom+Tanzu+Spring
- <logos-twitter /> @Kehrlann
- <logos-mastodon-icon /> @Kehrlann@hachyderm.io
- <logos-firefox /> https://garnier.wf/
- <logos-github-icon /> github.com/Kehrlann/
- <fluent-emoji-flat-envelope-with-arrow /> dgarnier@vmware.com

---
hideInToc: true
---

# What we'll talk about

<Toc maxDepth="2"></Toc>

---
layout: section
title: "OAuth2, OpenID: What's that?"
---

# OAuth2, OpenID
<br >

## What even is this?

---
title: OAuth2
level: 2
---

# OAuth 2 & 2.1

<br>

- An *Authorization* framework
  - Goal: Grant **applications** the **permission** to access **ressources** through **HTTP**.
- Using *tokens*, in this case `access_token`
- A long list of specs
  - **https://oauth.net/specs/** (sometimes a bit ... dry ...)

---
title: OAuth2 - Example
level: 3
---

# OAuth 2 & 2.1

For example:

🧑🏻 **Daniel**

authorizes

🖥️ **my-photo-book.example.com**

to access his pictures hosted on

📸 **Google Photos**

(without sharing his 🔐 Google password)

---
title: OAuth2
layout: fact
level: 3
---

## Notice: we're not saying anything about **identity**...

---
title: OpenID Connect
level: 2
---

# OpenID Connect

<br>

- An *Authentication* framework
  - Goal: give third-party **applications** identity data managed by an **identity provider**
  - Therefore enabling **Single-Sign-On** (SSO)
- Based on OAuth2, this time using `id_token`
- And of course... specs!
  - **https://openid.net/developers/specs/**

---
layout: section
title: "How and why, with images"
level: 1
---

# How and why?

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

# ~~~ Let's code!

<img src="/cat-code.gif" style="width: 600px; text-align:center;" />

---
layout: image-right
image: /no-idea.jpg
hideInToc: true
---

## 🚨**WARNING**🚨

<br>The stunts in this live-coding are performed by a professional<sup>1</sup>. Do **NOT** push any
of this code to production. **EVER**. Use a library.

<br><sup>1</sup> *dubious claim*

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

## Remember!

<br>Don't do this. Use a library.

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

- <logos-twitter /> @Kehrlann
- <logos-mastodon-icon /> @Kehrlann@hachyderm.io
- <logos-firefox /> https://garnier.wf/
- <fluent-emoji-flat-envelope-with-arrow /> dgarnier@vmware.com


---
layout: image
hideInToc: true
image: /meet-me.jpg
class: end
---

# **Thank you 😊**

