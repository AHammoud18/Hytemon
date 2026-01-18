/*     */ package com.nimbusds.jose.jwk.gen;
/*     */ 
/*     */ import com.nimbusds.jose.JOSEException;
/*     */ import com.nimbusds.jose.jwk.JWK;
/*     */ import com.nimbusds.jose.jwk.RSAKey;
/*     */ import java.security.KeyPair;
/*     */ import java.security.KeyPairGenerator;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.interfaces.RSAPublicKey;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RSAKeyGenerator
/*     */   extends JWKGenerator<RSAKey>
/*     */ {
/*     */   public static final int MIN_KEY_SIZE_BITS = 2048;
/*     */   private final int size;
/*     */   
/*     */   public RSAKeyGenerator(int size) {
/*  60 */     this(size, false);
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
/*     */   public RSAKeyGenerator(int size, boolean allowWeakKeys) {
/*  74 */     if (!allowWeakKeys && size < 2048) {
/*  75 */       throw new IllegalArgumentException("The key size must be at least 2048 bits");
/*     */     }
/*  77 */     this.size = size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RSAKey generate() throws JOSEException {
/*     */     KeyPairGenerator generator;
/*     */     try {
/*  87 */       if (this.keyStore != null) {
/*     */         
/*  89 */         generator = KeyPairGenerator.getInstance("RSA", this.keyStore.getProvider());
/*  90 */       } else if (this.provider != null) {
/*  91 */         generator = KeyPairGenerator.getInstance("RSA", this.provider);
/*     */       } else {
/*  93 */         generator = KeyPairGenerator.getInstance("RSA");
/*     */       } 
/*  95 */       if (this.secureRandom != null) {
/*  96 */         generator.initialize(this.size, this.secureRandom);
/*     */       } else {
/*     */         
/*  99 */         generator.initialize(this.size);
/*     */       } 
/* 101 */     } catch (NoSuchAlgorithmException e) {
/* 102 */       throw new JOSEException(e.getMessage(), e);
/*     */     } 
/*     */     
/* 105 */     KeyPair kp = generator.generateKeyPair();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 115 */     RSAKey.Builder builder = (new RSAKey.Builder((RSAPublicKey)kp.getPublic())).privateKey(kp.getPrivate()).keyUse(this.use).keyOperations(this.ops).algorithm(this.alg).expirationTime(this.exp).notBeforeTime(this.nbf).issueTime(this.iat).keyStore(this.keyStore);
/*     */     
/* 117 */     if (this.tprKid) {
/* 118 */       builder.keyIDFromThumbprint();
/*     */     } else {
/* 120 */       builder.keyID(this.kid);
/*     */     } 
/*     */     
/* 123 */     return builder.build();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\jwk\gen\RSAKeyGenerator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */