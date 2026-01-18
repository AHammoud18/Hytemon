/*     */ package com.nimbusds.jwt;
/*     */ 
/*     */ import com.nimbusds.jose.JOSEObject;
/*     */ import com.nimbusds.jose.JWEHeader;
/*     */ import com.nimbusds.jose.JWEObject;
/*     */ import com.nimbusds.jose.Payload;
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
/*     */ public class EncryptedJWT
/*     */   extends JWEObject
/*     */   implements JWT
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private JWTClaimsSet claimsSet;
/*     */   
/*     */   public EncryptedJWT(JWEHeader header, JWTClaimsSet claimsSet) {
/*  60 */     super(header, claimsSet.toPayload());
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
/*     */   public EncryptedJWT(Base64URL firstPart, Base64URL secondPart, Base64URL thirdPart, Base64URL fourthPart, Base64URL fifthPart) throws ParseException {
/*  90 */     super(firstPart, secondPart, thirdPart, fourthPart, fifthPart);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JWTClaimsSet getJWTClaimsSet() throws ParseException {
/*  98 */     if (this.claimsSet != null) {
/*  99 */       return this.claimsSet;
/*     */     }
/*     */     
/* 102 */     Payload payload = getPayload();
/*     */     
/* 104 */     if (payload == null) {
/* 105 */       return null;
/*     */     }
/*     */     
/* 108 */     Map<String, Object> json = payload.toJSONObject();
/*     */     
/* 110 */     if (json == null) {
/* 111 */       throw new ParseException("Payload of JWE object is not a valid JSON object", 0);
/*     */     }
/*     */     
/* 114 */     this.claimsSet = JWTClaimsSet.parse(json);
/* 115 */     return this.claimsSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setPayload(Payload payload) {
/* 124 */     this.claimsSet = null;
/* 125 */     super.setPayload(payload);
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
/*     */   public static EncryptedJWT parse(String s) throws ParseException {
/* 143 */     Base64URL[] parts = JOSEObject.split(s);
/*     */     
/* 145 */     if (parts.length != 5) {
/* 146 */       throw new ParseException("Unexpected number of Base64URL parts, must be five", 0);
/*     */     }
/*     */     
/* 149 */     return new EncryptedJWT(parts[0], parts[1], parts[2], parts[3], parts[4]);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jwt\EncryptedJWT.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */