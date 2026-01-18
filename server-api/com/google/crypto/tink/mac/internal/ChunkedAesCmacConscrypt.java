/*     */ package com.google.crypto.tink.mac.internal;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.InsecureSecretKeyAccess;
/*     */ import com.google.crypto.tink.config.internal.TinkFipsUtil;
/*     */ import com.google.crypto.tink.internal.Util;
/*     */ import com.google.crypto.tink.mac.AesCmacKey;
/*     */ import com.google.crypto.tink.mac.AesCmacParameters;
/*     */ import com.google.crypto.tink.mac.ChunkedMac;
/*     */ import com.google.crypto.tink.mac.ChunkedMacComputation;
/*     */ import com.google.crypto.tink.mac.ChunkedMacVerification;
/*     */ import com.google.crypto.tink.subtle.Bytes;
/*     */ import com.google.errorprone.annotations.Immutable;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.Provider;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ @Immutable
/*     */ public final class ChunkedAesCmacConscrypt
/*     */   implements ChunkedMac
/*     */ {
/*  43 */   private static final TinkFipsUtil.AlgorithmFipsCompatibility FIPS = TinkFipsUtil.AlgorithmFipsCompatibility.ALGORITHM_NOT_FIPS;
/*     */ 
/*     */   
/*     */   private final byte[] outputPrefix;
/*     */ 
/*     */   
/*     */   private final AesCmacParameters parameters;
/*     */ 
/*     */   
/*     */   private final SecretKeySpec secretKeySpec;
/*     */   
/*     */   private final Provider conscrypt;
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   private static SecretKeySpec toSecretKeySpec(AesCmacKey key) {
/*  59 */     return new SecretKeySpec(key.getAesKey().toByteArray(InsecureSecretKeyAccess.get()), "AES");
/*     */   }
/*     */ 
/*     */   
/*     */   private ChunkedAesCmacConscrypt(AesCmacKey key, Provider conscrypt) throws GeneralSecurityException {
/*  64 */     if (conscrypt == null) {
/*  65 */       throw new IllegalArgumentException("conscrypt is null");
/*     */     }
/*  67 */     if (!FIPS.isCompatible()) {
/*  68 */       throw new GeneralSecurityException("Cannot use AES-CMAC in FIPS-mode.");
/*     */     }
/*     */     try {
/*  71 */       Mac mac = Mac.getInstance("AESCMAC", conscrypt);
/*  72 */     } catch (NoSuchAlgorithmException e) {
/*  73 */       throw new GeneralSecurityException("AES-CMAC not available.", e);
/*     */     } 
/*  75 */     this.conscrypt = conscrypt;
/*  76 */     this.outputPrefix = key.getOutputPrefix().toByteArray();
/*  77 */     this.parameters = key.getParameters();
/*  78 */     this.secretKeySpec = toSecretKeySpec(key);
/*     */   }
/*     */   
/*     */   private static final class AesCmacComputation
/*     */     implements ChunkedMacComputation {
/*  83 */     private static final byte[] legacyFormatVersion = new byte[] { 0 };
/*     */ 
/*     */     
/*     */     private final byte[] outputPrefix;
/*     */     
/*     */     private final AesCmacParameters parameters;
/*     */     
/*     */     private final Mac aesCmac;
/*     */     
/*     */     private boolean finalized = false;
/*     */ 
/*     */     
/*     */     private AesCmacComputation(SecretKeySpec secretKeySpec, AesCmacParameters parameters, byte[] outputPrefix, Provider conscrypt) throws GeneralSecurityException {
/*  96 */       this.parameters = parameters;
/*  97 */       this.outputPrefix = outputPrefix;
/*  98 */       this.aesCmac = Mac.getInstance("AESCMAC", conscrypt);
/*  99 */       this.aesCmac.init(secretKeySpec);
/*     */     }
/*     */ 
/*     */     
/*     */     public void update(ByteBuffer data) {
/* 104 */       if (this.finalized) {
/* 105 */         throw new IllegalStateException("Cannot update after computing the MAC tag. Please create a new object.");
/*     */       }
/*     */       
/* 108 */       this.aesCmac.update(data);
/*     */     }
/*     */ 
/*     */     
/*     */     public byte[] computeMac() throws GeneralSecurityException {
/* 113 */       if (this.finalized) {
/* 114 */         throw new IllegalStateException("Cannot compute after computing the MAC tag. Please create a new object.");
/*     */       }
/*     */       
/* 117 */       this.finalized = true;
/* 118 */       if (this.parameters.getVariant() == AesCmacParameters.Variant.LEGACY) {
/* 119 */         this.aesCmac.update(legacyFormatVersion);
/*     */       }
/* 121 */       return Bytes.concat(new byte[][] { this.outputPrefix, 
/*     */             
/* 123 */             Arrays.copyOf(this.aesCmac.doFinal(), this.parameters.getCryptographicTagSizeBytes()) });
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public ChunkedMacComputation createComputation() throws GeneralSecurityException {
/* 129 */     return new AesCmacComputation(this.secretKeySpec, this.parameters, this.outputPrefix, this.conscrypt);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ChunkedMacVerification createVerification(byte[] tag) throws GeneralSecurityException {
/* 135 */     if (!Util.isPrefix(this.outputPrefix, tag)) {
/* 136 */       throw new GeneralSecurityException("Wrong tag prefix");
/*     */     }
/* 138 */     return ChunkedMacVerificationFromComputation.create(createComputation(), tag);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ChunkedMac create(AesCmacKey key, Provider conscrypt) throws GeneralSecurityException {
/* 143 */     return new ChunkedAesCmacConscrypt(key, conscrypt);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\mac\internal\ChunkedAesCmacConscrypt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */