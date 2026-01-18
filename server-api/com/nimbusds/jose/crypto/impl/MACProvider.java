/*     */ package com.nimbusds.jose.crypto.impl;
/*     */ 
/*     */ import com.nimbusds.jose.JOSEException;
/*     */ import com.nimbusds.jose.JWSAlgorithm;
/*     */ import com.nimbusds.jose.KeyLengthException;
/*     */ import com.nimbusds.jose.util.ByteUtils;
/*     */ import com.nimbusds.jose.util.StandardCharset;
/*     */ import java.util.Collections;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.Set;
/*     */ import javax.crypto.SecretKey;
/*     */ import javax.crypto.spec.SecretKeySpec;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class MACProvider
/*     */   extends BaseJWSProvider
/*     */ {
/*     */   public static final Set<JWSAlgorithm> SUPPORTED_ALGORITHMS;
/*     */   private final byte[] secret;
/*     */   private final SecretKey secretKey;
/*     */   
/*     */   static {
/*  60 */     Set<JWSAlgorithm> algs = new LinkedHashSet<>();
/*  61 */     algs.add(JWSAlgorithm.HS256);
/*  62 */     algs.add(JWSAlgorithm.HS384);
/*  63 */     algs.add(JWSAlgorithm.HS512);
/*  64 */     SUPPORTED_ALGORITHMS = Collections.unmodifiableSet(algs);
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
/*     */   public static Set<JWSAlgorithm> getCompatibleAlgorithms(int secretLength) {
/*  79 */     Set<JWSAlgorithm> hmacAlgs = new LinkedHashSet<>();
/*     */     
/*  81 */     if (secretLength >= 256) {
/*  82 */       hmacAlgs.add(JWSAlgorithm.HS256);
/*     */     }
/*  84 */     if (secretLength >= 384) {
/*  85 */       hmacAlgs.add(JWSAlgorithm.HS384);
/*     */     }
/*  87 */     if (secretLength >= 512) {
/*  88 */       hmacAlgs.add(JWSAlgorithm.HS512);
/*     */     }
/*  90 */     return Collections.unmodifiableSet(hmacAlgs);
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
/*     */   public static int getMinRequiredSecretLength(JWSAlgorithm alg) throws JOSEException {
/* 109 */     if (JWSAlgorithm.HS256.equals(alg))
/* 110 */       return 256; 
/* 111 */     if (JWSAlgorithm.HS384.equals(alg))
/* 112 */       return 384; 
/* 113 */     if (JWSAlgorithm.HS512.equals(alg)) {
/* 114 */       return 512;
/*     */     }
/* 116 */     throw new JOSEException(AlgorithmSupportMessage.unsupportedJWSAlgorithm(alg, SUPPORTED_ALGORITHMS));
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
/*     */   protected static String getJCAAlgorithmName(JWSAlgorithm alg) throws JOSEException {
/* 137 */     if (alg.equals(JWSAlgorithm.HS256))
/* 138 */       return "HMACSHA256"; 
/* 139 */     if (alg.equals(JWSAlgorithm.HS384))
/* 140 */       return "HMACSHA384"; 
/* 141 */     if (alg.equals(JWSAlgorithm.HS512)) {
/* 142 */       return "HMACSHA512";
/*     */     }
/* 144 */     throw new JOSEException(AlgorithmSupportMessage.unsupportedJWSAlgorithm(alg, SUPPORTED_ALGORITHMS));
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
/*     */ 
/*     */   
/*     */   protected MACProvider(byte[] secret) throws KeyLengthException {
/* 175 */     super(getCompatibleAlgorithms(ByteUtils.bitLength(secret.length)));
/*     */     
/* 177 */     if (ByteUtils.bitLength(secret) < 256)
/*     */     {
/*     */ 
/*     */       
/* 181 */       throw new KeyLengthException("The secret length must be at least 256 bits");
/*     */     }
/*     */     
/* 184 */     this.secret = secret;
/* 185 */     this.secretKey = null;
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
/*     */   protected MACProvider(SecretKey secretKey) throws KeyLengthException {
/* 202 */     super(
/* 203 */         (secretKey.getEncoded() != null) ? 
/*     */         
/* 205 */         getCompatibleAlgorithms(ByteUtils.bitLength(secretKey.getEncoded())) : 
/*     */ 
/*     */         
/* 208 */         SUPPORTED_ALGORITHMS);
/*     */ 
/*     */ 
/*     */     
/* 212 */     if (secretKey.getEncoded() != null && ByteUtils.bitLength(secretKey.getEncoded()) < 256)
/*     */     {
/*     */ 
/*     */       
/* 216 */       throw new KeyLengthException("The secret length must be at least 256 bits");
/*     */     }
/*     */     
/* 219 */     this.secretKey = secretKey;
/* 220 */     this.secret = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SecretKey getSecretKey() {
/* 230 */     if (this.secretKey != null)
/* 231 */       return this.secretKey; 
/* 232 */     if (this.secret != null) {
/* 233 */       return new SecretKeySpec(this.secret, "MAC");
/*     */     }
/* 235 */     throw new IllegalStateException("Unexpected state");
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
/*     */   public byte[] getSecret() {
/* 248 */     if (this.secretKey != null)
/* 249 */       return this.secretKey.getEncoded(); 
/* 250 */     if (this.secret != null) {
/* 251 */       return this.secret;
/*     */     }
/* 253 */     throw new IllegalStateException("Unexpected state");
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
/*     */   public String getSecretString() {
/* 267 */     byte[] secret = getSecret();
/*     */     
/* 269 */     if (secret == null) {
/* 270 */       return null;
/*     */     }
/*     */     
/* 273 */     return new String(secret, StandardCharset.UTF_8);
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
/*     */   protected void ensureSecretLengthSatisfiesAlgorithm(JWSAlgorithm alg) throws JOSEException {
/* 292 */     if (getSecret() == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 297 */     int minRequiredBitLength = getMinRequiredSecretLength(alg);
/*     */     
/* 299 */     if (ByteUtils.bitLength(getSecret()) < minRequiredBitLength)
/* 300 */       throw new KeyLengthException("The secret length for " + alg + " must be at least " + minRequiredBitLength + " bits"); 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\crypto\impl\MACProvider.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */