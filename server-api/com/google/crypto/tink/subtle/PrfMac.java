/*     */ package com.google.crypto.tink.subtle;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.InsecureSecretKeyAccess;
/*     */ import com.google.crypto.tink.Mac;
/*     */ import com.google.crypto.tink.mac.AesCmacKey;
/*     */ import com.google.crypto.tink.mac.AesCmacParameters;
/*     */ import com.google.crypto.tink.mac.HmacKey;
/*     */ import com.google.crypto.tink.mac.HmacParameters;
/*     */ import com.google.crypto.tink.prf.AesCmacPrfKey;
/*     */ import com.google.crypto.tink.prf.AesCmacPrfParameters;
/*     */ import com.google.crypto.tink.prf.Prf;
/*     */ import com.google.errorprone.annotations.Immutable;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.InvalidAlgorithmParameterException;
/*     */ import java.util.Arrays;
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
/*     */ @Immutable
/*     */ @AccessesPartialKey
/*     */ public class PrfMac
/*     */   implements Mac
/*     */ {
/*  42 */   private static final byte[] formatVersion = new byte[] { 0 };
/*     */ 
/*     */   
/*     */   static final int MIN_TAG_SIZE_IN_BYTES = 10;
/*     */   
/*     */   private final Prf wrappedPrf;
/*     */   
/*     */   private final int tagSize;
/*     */   
/*     */   private final byte[] outputPrefix;
/*     */   
/*     */   private final byte[] plaintextLegacySuffix;
/*     */ 
/*     */   
/*     */   public PrfMac(Prf wrappedPrf, int tagSize) throws GeneralSecurityException {
/*  57 */     this.wrappedPrf = wrappedPrf;
/*  58 */     this.tagSize = tagSize;
/*  59 */     this.outputPrefix = new byte[0];
/*  60 */     this.plaintextLegacySuffix = new byte[0];
/*     */ 
/*     */     
/*  63 */     if (tagSize < 10) {
/*  64 */       throw new InvalidAlgorithmParameterException("tag size too small, need at least 10 bytes");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  70 */     Object unused = wrappedPrf.compute(new byte[0], tagSize);
/*     */   }
/*     */   
/*     */   private PrfMac(AesCmacKey key) throws GeneralSecurityException {
/*  74 */     this.wrappedPrf = createPrf(key);
/*     */ 
/*     */     
/*  77 */     this.tagSize = key.getParameters().getCryptographicTagSizeBytes();
/*  78 */     this.outputPrefix = key.getOutputPrefix().toByteArray();
/*  79 */     if (key.getParameters().getVariant().equals(AesCmacParameters.Variant.LEGACY)) {
/*  80 */       this.plaintextLegacySuffix = Arrays.copyOf(formatVersion, formatVersion.length);
/*     */     } else {
/*  82 */       this.plaintextLegacySuffix = new byte[0];
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private PrfMac(HmacKey key) throws GeneralSecurityException {
/*  89 */     this
/*     */ 
/*     */ 
/*     */       
/*  93 */       .wrappedPrf = new PrfHmacJce("HMAC" + key.getParameters().getHashType(), new SecretKeySpec(key.getKeyBytes().toByteArray(InsecureSecretKeyAccess.get()), "HMAC"));
/*     */ 
/*     */     
/*  96 */     this.tagSize = key.getParameters().getCryptographicTagSizeBytes();
/*  97 */     this.outputPrefix = key.getOutputPrefix().toByteArray();
/*  98 */     if (key.getParameters().getVariant().equals(HmacParameters.Variant.LEGACY)) {
/*  99 */       this.plaintextLegacySuffix = Arrays.copyOf(formatVersion, formatVersion.length);
/*     */     } else {
/* 101 */       this.plaintextLegacySuffix = new byte[0];
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static Mac create(AesCmacKey key) throws GeneralSecurityException {
/* 107 */     return new PrfMac(key);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Mac create(HmacKey key) throws GeneralSecurityException {
/* 112 */     return new PrfMac(key);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] computeMac(byte[] data) throws GeneralSecurityException {
/* 117 */     if (this.plaintextLegacySuffix.length > 0) {
/* 118 */       return Bytes.concat(new byte[][] { this.outputPrefix, this.wrappedPrf
/* 119 */             .compute(Bytes.concat(new byte[][] { data, this.plaintextLegacySuffix }, ), this.tagSize) });
/*     */     }
/* 121 */     return Bytes.concat(new byte[][] { this.outputPrefix, this.wrappedPrf.compute(data, this.tagSize) });
/*     */   }
/*     */ 
/*     */   
/*     */   public void verifyMac(byte[] mac, byte[] data) throws GeneralSecurityException {
/* 126 */     if (!Bytes.equal(computeMac(data), mac)) {
/* 127 */       throw new GeneralSecurityException("invalid MAC");
/*     */     }
/*     */   }
/*     */   
/*     */   @AccessesPartialKey
/*     */   private static Prf createPrf(AesCmacKey key) throws GeneralSecurityException {
/* 133 */     return PrfAesCmac.create(
/* 134 */         AesCmacPrfKey.create(
/* 135 */           AesCmacPrfParameters.create(key.getParameters().getKeySizeBytes()), key
/* 136 */           .getAesKey()));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\subtle\PrfMac.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */