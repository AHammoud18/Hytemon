/*     */ package com.google.crypto.tink.signature.internal;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.PublicKeyVerify;
/*     */ import com.google.crypto.tink.config.internal.TinkFipsUtil;
/*     */ import com.google.crypto.tink.internal.ConscryptUtil;
/*     */ import com.google.crypto.tink.internal.Util;
/*     */ import com.google.crypto.tink.signature.RsaSsaPkcs1Parameters;
/*     */ import com.google.crypto.tink.signature.RsaSsaPkcs1PublicKey;
/*     */ import com.google.crypto.tink.subtle.Validators;
/*     */ import com.google.errorprone.annotations.Immutable;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.KeyFactory;
/*     */ import java.security.NoSuchProviderException;
/*     */ import java.security.Provider;
/*     */ import java.security.Signature;
/*     */ import java.security.interfaces.RSAPublicKey;
/*     */ import java.security.spec.RSAPublicKeySpec;
/*     */ import java.util.Arrays;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public final class RsaSsaPkcs1VerifyConscrypt
/*     */   implements PublicKeyVerify
/*     */ {
/*  46 */   public static final TinkFipsUtil.AlgorithmFipsCompatibility FIPS = TinkFipsUtil.AlgorithmFipsCompatibility.ALGORITHM_REQUIRES_BORINGCRYPTO;
/*     */ 
/*     */   
/*  49 */   private static final byte[] EMPTY = new byte[0];
/*  50 */   private static final byte[] legacyMessageSuffix = new byte[] { 0 };
/*     */   
/*     */   @Nullable
/*     */   static Provider conscryptProviderOrNull() {
/*  54 */     if (Util.isAndroid() && Util.getAndroidApiLevel().intValue() <= 21)
/*     */     {
/*     */       
/*  57 */       return null;
/*     */     }
/*  59 */     return ConscryptUtil.providerOrNull();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private final RSAPublicKey publicKey;
/*     */ 
/*     */   
/*     */   private final String signatureAlgorithm;
/*     */ 
/*     */   
/*     */   private final byte[] outputPrefix;
/*     */   
/*     */   private final byte[] messageSuffix;
/*     */   
/*     */   private final Provider conscrypt;
/*     */ 
/*     */   
/*     */   public static String toRsaSsaPkcs1Algo(RsaSsaPkcs1Parameters.HashType hashType) throws GeneralSecurityException {
/*  78 */     if (hashType == RsaSsaPkcs1Parameters.HashType.SHA256) {
/*  79 */       return "SHA256withRSA";
/*     */     }
/*  81 */     if (hashType == RsaSsaPkcs1Parameters.HashType.SHA384) {
/*  82 */       return "SHA384withRSA";
/*     */     }
/*  84 */     if (hashType == RsaSsaPkcs1Parameters.HashType.SHA512) {
/*  85 */       return "SHA512withRSA";
/*     */     }
/*  87 */     throw new GeneralSecurityException("unknown hash type");
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
/*     */   public static PublicKeyVerify create(RsaSsaPkcs1PublicKey key) throws GeneralSecurityException {
/*  99 */     Provider conscrypt = conscryptProviderOrNull();
/* 100 */     if (conscrypt == null) {
/* 101 */       throw new NoSuchProviderException("RSA-PKCS1.5 using Conscrypt is not supported.");
/*     */     }
/* 103 */     return createWithProvider(key, conscrypt);
/*     */   }
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   public static PublicKeyVerify createWithProvider(RsaSsaPkcs1PublicKey key, Provider conscrypt) throws GeneralSecurityException {
/* 109 */     KeyFactory keyFactory = KeyFactory.getInstance("RSA", conscrypt);
/*     */ 
/*     */     
/* 112 */     RSAPublicKey publicKey = (RSAPublicKey)keyFactory.generatePublic(new RSAPublicKeySpec(key
/* 113 */           .getModulus(), key.getParameters().getPublicExponent()));
/*     */     
/* 115 */     return new RsaSsaPkcs1VerifyConscrypt(publicKey, key
/*     */         
/* 117 */         .getParameters().getHashType(), key
/* 118 */         .getOutputPrefix().toByteArray(), 
/* 119 */         key.getParameters().getVariant().equals(RsaSsaPkcs1Parameters.Variant.LEGACY) ? 
/* 120 */         legacyMessageSuffix : 
/* 121 */         EMPTY, conscrypt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private RsaSsaPkcs1VerifyConscrypt(RSAPublicKey pubKey, RsaSsaPkcs1Parameters.HashType hashType, byte[] outputPrefix, byte[] messageSuffix, Provider conscrypt) throws GeneralSecurityException {
/* 132 */     if (!FIPS.isCompatible()) {
/* 133 */       throw new GeneralSecurityException("Can not use RSA-PKCS1.5 in FIPS-mode, as BoringCrypto module is not available.");
/*     */     }
/*     */     
/* 136 */     Validators.validateRsaModulusSize(pubKey.getModulus().bitLength());
/* 137 */     Validators.validateRsaPublicExponent(pubKey.getPublicExponent());
/* 138 */     this.publicKey = pubKey;
/* 139 */     this.signatureAlgorithm = toRsaSsaPkcs1Algo(hashType);
/* 140 */     this.outputPrefix = outputPrefix;
/* 141 */     this.messageSuffix = messageSuffix;
/* 142 */     this.conscrypt = conscrypt;
/*     */   }
/*     */ 
/*     */   
/*     */   public void verify(byte[] signature, byte[] data) throws GeneralSecurityException {
/* 147 */     if (!Util.isPrefix(this.outputPrefix, signature)) {
/* 148 */       throw new GeneralSecurityException("Invalid signature (output prefix mismatch)");
/*     */     }
/* 150 */     Signature verifier = Signature.getInstance(this.signatureAlgorithm, this.conscrypt);
/* 151 */     verifier.initVerify(this.publicKey);
/* 152 */     verifier.update(data);
/* 153 */     if (this.messageSuffix.length > 0) {
/* 154 */       verifier.update(this.messageSuffix);
/*     */     }
/* 156 */     boolean verified = false;
/*     */     
/*     */     try {
/* 159 */       byte[] signatureNoPrefix = Arrays.copyOfRange(signature, this.outputPrefix.length, signature.length);
/* 160 */       verified = verifier.verify(signatureNoPrefix);
/* 161 */     } catch (RuntimeException ex) {
/* 162 */       verified = false;
/*     */     } 
/* 164 */     if (!verified)
/* 165 */       throw new GeneralSecurityException("Invalid signature"); 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\signature\internal\RsaSsaPkcs1VerifyConscrypt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */