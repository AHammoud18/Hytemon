/*     */ package com.nimbusds.jwt;
/*     */ 
/*     */ import com.nimbusds.jose.JOSEObject;
/*     */ import com.nimbusds.jose.JWSHeader;
/*     */ import com.nimbusds.jose.JWSObject;
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
/*     */ public class SignedJWT
/*     */   extends JWSObject
/*     */   implements JWT
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private JWTClaimsSet claimsSet;
/*     */   
/*     */   public SignedJWT(JWSHeader header, JWTClaimsSet claimsSet) {
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
/*     */   public SignedJWT(Base64URL firstPart, Base64URL secondPart, Base64URL thirdPart) throws ParseException {
/*  82 */     super(firstPart, secondPart, thirdPart);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JWTClaimsSet getJWTClaimsSet() throws ParseException {
/*  90 */     if (this.claimsSet != null) {
/*  91 */       return this.claimsSet;
/*     */     }
/*     */     
/*  94 */     Map<String, Object> json = getPayload().toJSONObject();
/*     */     
/*  96 */     if (json == null) {
/*  97 */       throw new ParseException("Payload of JWS object is not a valid JSON object", 0);
/*     */     }
/*     */     
/* 100 */     this.claimsSet = JWTClaimsSet.parse(json);
/* 101 */     return this.claimsSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setPayload(Payload payload) {
/* 110 */     this.claimsSet = null;
/* 111 */     super.setPayload(payload);
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
/*     */   public static SignedJWT parse(String s) throws ParseException {
/* 129 */     Base64URL[] parts = JOSEObject.split(s);
/*     */     
/* 131 */     if (parts.length != 3) {
/* 132 */       throw new ParseException("Unexpected number of Base64URL parts, must be three", 0);
/*     */     }
/*     */     
/* 135 */     return new SignedJWT(parts[0], parts[1], parts[2]);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jwt\SignedJWT.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */