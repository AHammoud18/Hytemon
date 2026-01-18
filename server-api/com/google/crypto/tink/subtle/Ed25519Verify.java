/*     */ package com.google.crypto.tink.subtle;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.PublicKeyVerify;
/*     */ import com.google.crypto.tink.config.internal.TinkFipsUtil;
/*     */ import com.google.crypto.tink.internal.Ed25519;
/*     */ import com.google.crypto.tink.internal.Util;
/*     */ import com.google.crypto.tink.signature.Ed25519Parameters;
/*     */ import com.google.crypto.tink.signature.Ed25519PublicKey;
/*     */ import com.google.crypto.tink.signature.internal.Ed25519VerifyJce;
/*     */ import com.google.crypto.tink.util.Bytes;
/*     */ import com.google.errorprone.annotations.Immutable;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public final class Ed25519Verify
/*     */   implements PublicKeyVerify
/*     */ {
/*  56 */   public static final TinkFipsUtil.AlgorithmFipsCompatibility FIPS = TinkFipsUtil.AlgorithmFipsCompatibility.ALGORITHM_NOT_FIPS;
/*     */ 
/*     */   
/*     */   public static final int PUBLIC_KEY_LEN = 32;
/*     */   
/*     */   public static final int SIGNATURE_LEN = 64;
/*     */   
/*     */   private final Bytes publicKey;
/*     */   
/*     */   private final byte[] outputPrefix;
/*     */   
/*     */   private final byte[] messageSuffix;
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   public static PublicKeyVerify create(Ed25519PublicKey key) throws GeneralSecurityException {
/*  72 */     if (!FIPS.isCompatible()) {
/*  73 */       throw new GeneralSecurityException("Can not use Ed25519 in FIPS-mode.");
/*     */     }
/*     */     try {
/*  76 */       return Ed25519VerifyJce.create(key);
/*  77 */     } catch (GeneralSecurityException generalSecurityException) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  84 */       (new byte[1])[0] = 0; return new Ed25519Verify(key.getPublicKeyBytes().toByteArray(), key.getOutputPrefix().toByteArray(), key.getParameters().getVariant().equals(Ed25519Parameters.Variant.LEGACY) ? new byte[1] : 
/*  85 */           new byte[0]);
/*     */     } 
/*     */   }
/*     */   public Ed25519Verify(byte[] publicKey) {
/*  89 */     this(publicKey, new byte[0], new byte[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   private Ed25519Verify(byte[] publicKey, byte[] outputPrefix, byte[] messageSuffix) {
/*  94 */     if (!FIPS.isCompatible())
/*     */     {
/*     */ 
/*     */       
/*  98 */       throw new IllegalStateException(new GeneralSecurityException("Can not use Ed25519 in FIPS-mode."));
/*     */     }
/*     */ 
/*     */     
/* 102 */     if (publicKey.length != 32) {
/* 103 */       throw new IllegalArgumentException(
/* 104 */           String.format("Given public key's length is not %s.", new Object[] { Integer.valueOf(32) }));
/*     */     }
/* 106 */     this.publicKey = Bytes.copyFrom(publicKey);
/* 107 */     this.outputPrefix = outputPrefix;
/* 108 */     this.messageSuffix = messageSuffix;
/* 109 */     Ed25519.init();
/*     */   }
/*     */   
/*     */   private void noPrefixVerify(byte[] signature, byte[] data) throws GeneralSecurityException {
/* 113 */     if (signature.length != 64) {
/* 114 */       throw new GeneralSecurityException(
/* 115 */           String.format("The length of the signature is not %s.", new Object[] { Integer.valueOf(64) }));
/*     */     }
/* 117 */     if (!Ed25519.verify(data, signature, this.publicKey.toByteArray())) {
/* 118 */       throw new GeneralSecurityException("Signature check failed.");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void verify(byte[] signature, byte[] data) throws GeneralSecurityException {
/* 124 */     if (this.outputPrefix.length == 0 && this.messageSuffix.length == 0) {
/* 125 */       noPrefixVerify(signature, data);
/*     */       return;
/*     */     } 
/* 128 */     if (!Util.isPrefix(this.outputPrefix, signature)) {
/* 129 */       throw new GeneralSecurityException("Invalid signature (output prefix mismatch)");
/*     */     }
/* 131 */     byte[] dataCopy = data;
/* 132 */     if (this.messageSuffix.length != 0) {
/* 133 */       dataCopy = Bytes.concat(new byte[][] { data, this.messageSuffix });
/*     */     }
/* 135 */     byte[] signatureNoPrefix = Arrays.copyOfRange(signature, this.outputPrefix.length, signature.length);
/* 136 */     noPrefixVerify(signatureNoPrefix, dataCopy);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\subtle\Ed25519Verify.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */