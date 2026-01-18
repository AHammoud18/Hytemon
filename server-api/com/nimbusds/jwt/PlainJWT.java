/*     */ package com.nimbusds.jwt;
/*     */ 
/*     */ import com.nimbusds.jose.JOSEObject;
/*     */ import com.nimbusds.jose.Payload;
/*     */ import com.nimbusds.jose.PlainHeader;
/*     */ import com.nimbusds.jose.PlainObject;
/*     */ import com.nimbusds.jose.shaded.jcip.ThreadSafe;
/*     */ import com.nimbusds.jose.util.Base64URL;
/*     */ import java.text.ParseException;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @ThreadSafe
/*     */ public class PlainJWT
/*     */   extends PlainObject
/*     */   implements JWT
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private JWTClaimsSet claimsSet;
/*     */   
/*     */   public PlainJWT(JWTClaimsSet claimsSet) {
/*  60 */     super(claimsSet.toPayload());
/*  61 */     this.claimsSet = claimsSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PlainJWT(PlainHeader header, JWTClaimsSet claimsSet) {
/*  74 */     super(header, claimsSet.toPayload());
/*  75 */     this.claimsSet = claimsSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PlainJWT(Base64URL firstPart, Base64URL secondPart) throws ParseException {
/*  93 */     super(firstPart, secondPart);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JWTClaimsSet getJWTClaimsSet() throws ParseException {
/* 101 */     if (this.claimsSet != null) {
/* 102 */       return this.claimsSet;
/*     */     }
/*     */     
/* 105 */     Map<String, Object> jsonObject = getPayload().toJSONObject();
/* 106 */     if (jsonObject == null) {
/* 107 */       throw new ParseException("Payload of unsecured JOSE object is not a valid JSON object", 0);
/*     */     }
/*     */     
/* 110 */     this.claimsSet = JWTClaimsSet.parse(jsonObject);
/* 111 */     return this.claimsSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setPayload(Payload payload) {
/* 120 */     this.claimsSet = null;
/* 121 */     super.setPayload(payload);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PlainJWT parse(String s) throws ParseException {
/* 138 */     Base64URL[] parts = JOSEObject.split(s);
/*     */     
/* 140 */     if (!parts[2].toString().isEmpty())
/*     */     {
/* 142 */       throw new ParseException("Unexpected third Base64URL part in the unsecured JWT object", 0);
/*     */     }
/*     */     
/* 145 */     return new PlainJWT(parts[0], parts[1]);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jwt\PlainJWT.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */