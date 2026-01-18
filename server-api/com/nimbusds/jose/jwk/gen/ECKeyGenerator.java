/*     */ package com.nimbusds.jose.jwk.gen;
/*     */ 
/*     */ import com.nimbusds.jose.JOSEException;
/*     */ import com.nimbusds.jose.jwk.Curve;
/*     */ import com.nimbusds.jose.jwk.ECKey;
/*     */ import com.nimbusds.jose.jwk.JWK;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.KeyPair;
/*     */ import java.security.KeyPairGenerator;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.interfaces.ECPublicKey;
/*     */ import java.security.spec.ECParameterSpec;
/*     */ import java.util.Objects;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ECKeyGenerator
/*     */   extends JWKGenerator<ECKey>
/*     */ {
/*     */   private final Curve crv;
/*     */   
/*     */   public ECKeyGenerator(Curve crv) {
/*  65 */     this.crv = Objects.<Curve>requireNonNull(crv);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ECKey generate() throws JOSEException {
/*     */     KeyPairGenerator generator;
/*  73 */     ECParameterSpec ecSpec = this.crv.toECParameterSpec();
/*     */ 
/*     */     
/*     */     try {
/*  77 */       if (this.keyStore != null) {
/*     */         
/*  79 */         generator = KeyPairGenerator.getInstance("EC", this.keyStore.getProvider());
/*  80 */       } else if (this.provider != null) {
/*  81 */         generator = KeyPairGenerator.getInstance("EC", this.provider);
/*     */       } else {
/*  83 */         generator = KeyPairGenerator.getInstance("EC");
/*     */       } 
/*  85 */       if (this.secureRandom != null) {
/*  86 */         generator.initialize(ecSpec, this.secureRandom);
/*     */       } else {
/*     */         
/*  89 */         generator.initialize(ecSpec);
/*     */       } 
/*  91 */     } catch (NoSuchAlgorithmException|java.security.InvalidAlgorithmParameterException e) {
/*  92 */       throw new JOSEException(e.getMessage(), e);
/*     */     } 
/*     */     
/*  95 */     KeyPair kp = generator.generateKeyPair();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 105 */     ECKey.Builder builder = (new ECKey.Builder(this.crv, (ECPublicKey)kp.getPublic())).privateKey(kp.getPrivate()).keyUse(this.use).keyOperations(this.ops).algorithm(this.alg).expirationTime(this.exp).notBeforeTime(this.nbf).issueTime(this.iat).keyStore(this.keyStore);
/*     */     
/* 107 */     if (this.tprKid) {
/* 108 */       builder.keyIDFromThumbprint();
/*     */     } else {
/* 110 */       builder.keyID(this.kid);
/*     */     } 
/*     */     
/* 113 */     return builder.build();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\jwk\gen\ECKeyGenerator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */