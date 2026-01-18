/*     */ package com.google.crypto.tink.subtle;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.InsecureSecretKeyAccess;
/*     */ import com.google.crypto.tink.config.internal.TinkFipsUtil;
/*     */ import com.google.crypto.tink.prf.HmacPrfKey;
/*     */ import com.google.crypto.tink.prf.Prf;
/*     */ import com.google.errorprone.annotations.Immutable;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.InvalidAlgorithmParameterException;
/*     */ import java.security.Key;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.util.Arrays;
/*     */ import javax.crypto.Mac;
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
/*     */ @Immutable
/*     */ @AccessesPartialKey
/*     */ public final class PrfHmacJce
/*     */   implements Prf
/*     */ {
/*  36 */   public static final TinkFipsUtil.AlgorithmFipsCompatibility FIPS = TinkFipsUtil.AlgorithmFipsCompatibility.ALGORITHM_REQUIRES_BORINGCRYPTO;
/*     */ 
/*     */   
/*     */   static final int MIN_KEY_SIZE_IN_BYTES = 16;
/*     */ 
/*     */   
/*  42 */   private final ThreadLocal<Mac> localMac = new ThreadLocal<Mac>()
/*     */     {
/*     */       
/*     */       protected Mac initialValue()
/*     */       {
/*     */         try {
/*  48 */           Mac mac = EngineFactory.MAC.getInstance(PrfHmacJce.this.algorithm);
/*  49 */           mac.init(PrfHmacJce.this.key);
/*  50 */           return mac;
/*  51 */         } catch (GeneralSecurityException ex) {
/*  52 */           throw new IllegalStateException(ex);
/*     */         } 
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   private final String algorithm;
/*     */   
/*     */   private final Key key;
/*     */   private final int maxOutputLength;
/*     */   
/*     */   public PrfHmacJce(String algorithm, Key key) throws GeneralSecurityException {
/*  64 */     if (!FIPS.isCompatible()) {
/*  65 */       throw new GeneralSecurityException("Can not use HMAC in FIPS-mode, as BoringCrypto module is not available.");
/*     */     }
/*     */ 
/*     */     
/*  69 */     this.algorithm = algorithm;
/*  70 */     this.key = key;
/*  71 */     if ((key.getEncoded()).length < 16) {
/*  72 */       throw new InvalidAlgorithmParameterException("key size too small, need at least 16 bytes");
/*     */     }
/*     */ 
/*     */     
/*  76 */     switch (algorithm) {
/*     */       case "HMACSHA1":
/*  78 */         this.maxOutputLength = 20;
/*     */         break;
/*     */       case "HMACSHA224":
/*  81 */         this.maxOutputLength = 28;
/*     */         break;
/*     */       case "HMACSHA256":
/*  84 */         this.maxOutputLength = 32;
/*     */         break;
/*     */       case "HMACSHA384":
/*  87 */         this.maxOutputLength = 48;
/*     */         break;
/*     */       case "HMACSHA512":
/*  90 */         this.maxOutputLength = 64;
/*     */         break;
/*     */       default:
/*  93 */         throw new NoSuchAlgorithmException("unknown Hmac algorithm: " + algorithm);
/*     */     } 
/*     */ 
/*     */     
/*  97 */     this.localMac.get();
/*     */   }
/*     */ 
/*     */   
/*     */   public static Prf create(HmacPrfKey key) throws GeneralSecurityException {
/* 102 */     return new PrfHmacJce("HMAC" + key
/* 103 */         .getParameters().getHashType(), new SecretKeySpec(key
/* 104 */           .getKeyBytes().toByteArray(InsecureSecretKeyAccess.get()), "HMAC"));
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] compute(byte[] data, int outputLength) throws GeneralSecurityException {
/* 109 */     if (outputLength > this.maxOutputLength) {
/* 110 */       throw new InvalidAlgorithmParameterException("tag size too big");
/*     */     }
/*     */     
/* 113 */     ((Mac)this.localMac.get()).update(data);
/* 114 */     return Arrays.copyOf(((Mac)this.localMac.get()).doFinal(), outputLength);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxOutputLength() {
/* 119 */     return this.maxOutputLength;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\subtle\PrfHmacJce.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */