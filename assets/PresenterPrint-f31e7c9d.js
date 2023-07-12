import{d as m,i as _,a as p,u as h,b as u,c as d,e as g,f as n,g as t,t as o,h as a,F as f,r as v,n as x,j as b,o as i,k as y,l as k,m as N,p as w,q as P,_ as S}from"./index-ab9c0722.js";import{N as V}from"./NoteDisplay-7cd37f40.js";const j={class:"m-4"},L={class:"mb-10"},T={class:"text-4xl font-bold mt-2"},B={class:"opacity-50"},C={class:"text-lg"},D={class:"font-bold flex gap-2"},H={class:"opacity-50"},z=t("div",{class:"flex-auto"},null,-1),F={key:0,class:"border-gray-400/50 mb-8"},M=m({__name:"PresenterPrint",setup(q){_(p),h(`
@page {
  size: A4;
  margin-top: 1.5cm;
  margin-bottom: 1cm;
}
* {
  -webkit-print-color-adjust: exact;
}
html,
html body,
html #app,
html #page-root {
  height: auto;
  overflow: auto !important;
}
`),u({title:`Notes - ${d.title}`});const r=g(()=>b.slice(0,-1).map(s=>{var l;return(l=s.meta)==null?void 0:l.slide}).filter(s=>s!==void 0&&s.noteHTML!==""));return(s,l)=>(i(),n("div",{id:"page-root",style:x(a(P))},[t("div",j,[t("div",L,[t("h1",T,o(a(d).title),1),t("div",B,o(new Date().toLocaleString()),1)]),(i(!0),n(f,null,v(a(r),(e,c)=>(i(),n("div",{key:c,class:"flex flex-col gap-4 break-inside-avoid-page"},[t("div",null,[t("h2",C,[t("div",D,[t("div",H,o(e==null?void 0:e.no)+"/"+o(a(y)),1),k(" "+o(e==null?void 0:e.title)+" ",1),z])]),N(V,{"note-html":e.noteHTML,class:"max-w-full"},null,8,["note-html"])]),c<a(r).length-1?(i(),n("hr",F)):w("v-if",!0)]))),128))])],4))}}),R=S(M,[["__file","/Users/dgarnier/workspace/daniel/github/sso-live-coding/slides/node_modules/@slidev/client/internals/PresenterPrint.vue"]]);export{R as default};
