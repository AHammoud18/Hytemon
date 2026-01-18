/*     */ package com.nimbusds.jose.jwk;
/*     */ 
/*     */ import com.nimbusds.jose.Algorithm;
/*     */ import com.nimbusds.jose.util.Base64;
/*     */ import com.nimbusds.jose.util.Base64URL;
/*     */ import com.nimbusds.jose.util.JSONObjectUtils;
/*     */ import com.nimbusds.jose.util.X509CertChainUtils;
/*     */ import com.nimbusds.jwt.util.DateUtils;
/*     */ import java.net.URI;
/*     */ import java.text.ParseException;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
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
/*     */ final class JWKMetadata
/*     */ {
/*     */   static KeyType parseKeyType(Map<String, Object> o) throws ParseException {
/*     */     try {
/*  58 */       return KeyType.parse(JSONObjectUtils.getString(o, "kty"));
/*  59 */     } catch (IllegalArgumentException e) {
/*  60 */       throw new ParseException(e.getMessage(), 0);
/*     */     } 
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
/*     */   static KeyUse parseKeyUse(Map<String, Object> o) throws ParseException {
/*  78 */     return KeyUse.parse(JSONObjectUtils.getString(o, "use"));
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
/*     */   static Set<KeyOperation> parseKeyOperations(Map<String, Object> o) throws ParseException {
/*  94 */     return KeyOperation.parse(JSONObjectUtils.getStringList(o, "key_ops"));
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
/*     */   static Algorithm parseAlgorithm(Map<String, Object> o) throws ParseException {
/* 110 */     return Algorithm.parse(JSONObjectUtils.getString(o, "alg"));
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
/*     */   static String parseKeyID(Map<String, Object> o) throws ParseException {
/* 126 */     return JSONObjectUtils.getString(o, "kid");
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
/*     */   static URI parseX509CertURL(Map<String, Object> o) throws ParseException {
/* 142 */     return JSONObjectUtils.getURI(o, "x5u");
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
/*     */   static Base64URL parseX509CertThumbprint(Map<String, Object> o) throws ParseException {
/* 159 */     return JSONObjectUtils.getBase64URL(o, "x5t");
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
/*     */   static Base64URL parseX509CertSHA256Thumbprint(Map<String, Object> o) throws ParseException {
/* 176 */     return JSONObjectUtils.getBase64URL(o, "x5t#S256");
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
/*     */   static List<Base64> parseX509CertChain(Map<String, Object> o) throws ParseException {
/* 195 */     List<Base64> chain = X509CertChainUtils.toBase64List(JSONObjectUtils.getJSONArray(o, "x5c"));
/*     */     
/* 197 */     if (chain == null || !chain.isEmpty()) {
/* 198 */       return chain;
/*     */     }
/*     */     
/* 201 */     return null;
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
/*     */   static Date parseExpirationTime(Map<String, Object> o) throws ParseException {
/* 217 */     if (o.get("exp") == null) {
/* 218 */       return null;
/*     */     }
/*     */     
/* 221 */     return DateUtils.fromSecondsSinceEpoch(JSONObjectUtils.getLong(o, "exp"));
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
/*     */   static Date parseNotBeforeTime(Map<String, Object> o) throws ParseException {
/* 237 */     if (o.get("nbf") == null) {
/* 238 */       return null;
/*     */     }
/*     */     
/* 241 */     return DateUtils.fromSecondsSinceEpoch(JSONObjectUtils.getLong(o, "nbf"));
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
/*     */   static Date parseIssueTime(Map<String, Object> o) throws ParseException {
/* 257 */     if (o.get("iat") == null) {
/* 258 */       return null;
/*     */     }
/*     */     
/* 261 */     return DateUtils.fromSecondsSinceEpoch(JSONObjectUtils.getLong(o, "iat"));
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
/*     */   static KeyRevocation parseKeyRevocation(Map<String, Object> o) throws ParseException {
/* 277 */     if (o.get("revoked") == null) {
/* 278 */       return null;
/*     */     }
/*     */     
/* 281 */     return KeyRevocation.parse(JSONObjectUtils.getJSONObject(o, "revoked"));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\jwk\JWKMetadata.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */