/*     */ package com.nimbusds.jose.jwk.gen;
/*     */ 
/*     */ import com.nimbusds.jose.Algorithm;
/*     */ import com.nimbusds.jose.JOSEException;
/*     */ import com.nimbusds.jose.jwk.JWK;
/*     */ import com.nimbusds.jose.jwk.KeyOperation;
/*     */ import com.nimbusds.jose.jwk.KeyUse;
/*     */ import java.security.KeyStore;
/*     */ import java.security.Provider;
/*     */ import java.security.SecureRandom;
/*     */ import java.util.Date;
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
/*     */ public abstract class JWKGenerator<T extends JWK>
/*     */ {
/*     */   protected KeyUse use;
/*     */   protected Set<KeyOperation> ops;
/*     */   protected Algorithm alg;
/*     */   protected String kid;
/*     */   protected boolean tprKid;
/*     */   protected Date exp;
/*     */   protected Date nbf;
/*     */   protected Date iat;
/*     */   protected KeyStore keyStore;
/*     */   protected Provider provider;
/*     */   protected SecureRandom secureRandom;
/*     */   
/*     */   public JWKGenerator<T> keyUse(KeyUse use) {
/* 123 */     this.use = use;
/* 124 */     return this;
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
/*     */   public JWKGenerator<T> keyOperations(Set<KeyOperation> ops) {
/* 137 */     this.ops = ops;
/* 138 */     return this;
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
/*     */   public JWKGenerator<T> algorithm(Algorithm alg) {
/* 151 */     this.alg = alg;
/* 152 */     return this;
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
/*     */   public JWKGenerator<T> keyID(String kid) {
/* 167 */     this.kid = kid;
/* 168 */     return this;
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
/*     */   public JWKGenerator<T> keyIDFromThumbprint(boolean tprKid) {
/* 186 */     this.tprKid = tprKid;
/* 187 */     return this;
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
/*     */   public JWKGenerator<T> expirationTime(Date exp) {
/* 200 */     this.exp = exp;
/* 201 */     return this;
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
/*     */   public JWKGenerator<T> notBeforeTime(Date nbf) {
/* 214 */     this.nbf = nbf;
/* 215 */     return this;
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
/*     */   public JWKGenerator<T> issueTime(Date iat) {
/* 228 */     this.iat = iat;
/* 229 */     return this;
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
/*     */   public JWKGenerator<T> keyStore(KeyStore keyStore) {
/* 244 */     this.keyStore = keyStore;
/* 245 */     return this;
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
/*     */   public JWKGenerator<T> provider(Provider provider) {
/* 258 */     this.provider = provider;
/* 259 */     return this;
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
/*     */   public JWKGenerator<T> secureRandom(SecureRandom secureRandom) {
/* 273 */     this.secureRandom = secureRandom;
/* 274 */     return this;
/*     */   }
/*     */   
/*     */   public abstract T generate() throws JOSEException;
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\jwk\gen\JWKGenerator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */