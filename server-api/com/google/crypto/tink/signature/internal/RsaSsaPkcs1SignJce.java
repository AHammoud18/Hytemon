/*     */ package com.google.crypto.tink.signature.internal;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.InsecureSecretKeyAccess;
/*     */ import com.google.crypto.tink.PublicKeySign;
/*     */ import com.google.crypto.tink.PublicKeyVerify;
/*     */ import com.google.crypto.tink.config.internal.TinkFipsUtil;
/*     */ import com.google.crypto.tink.signature.RsaSsaPkcs1Parameters;
/*     */ import com.google.crypto.tink.signature.RsaSsaPkcs1PrivateKey;
/*     */ import com.google.crypto.tink.subtle.Bytes;
/*     */ import com.google.crypto.tink.subtle.EngineFactory;
/*     */ import com.google.crypto.tink.subtle.RsaSsaPkcs1VerifyJce;
/*     */ import com.google.crypto.tink.subtle.Validators;
/*     */ import com.google.errorprone.annotations.Immutable;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.KeyFactory;
/*     */ import java.security.Provider;
/*     */ import java.security.Signature;
/*     */ import java.security.interfaces.RSAPrivateCrtKey;
/*     */ import java.security.spec.RSAPrivateCrtKeySpec;
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
/*     */ @Immutable
/*     */ public final class RsaSsaPkcs1SignJce
/*     */   implements PublicKeySign
/*     */ {
/*  45 */   public static final TinkFipsUtil.AlgorithmFipsCompatibility FIPS = TinkFipsUtil.AlgorithmFipsCompatibility.ALGORITHM_REQUIRES_BORINGCRYPTO;
/*     */ 
/*     */   
/*  48 */   private static final byte[] EMPTY = new byte[0];
/*  49 */   private static final byte[] legacyMessageSuffix = new byte[] { 0 };
/*  50 */   private static final byte[] testData = new byte[] { 1, 2, 3 };
/*     */ 
/*     */   
/*     */   private final RSAPrivateCrtKey privateKey;
/*     */ 
/*     */   
/*     */   private final String signatureAlgorithm;
/*     */ 
/*     */   
/*     */   private final byte[] outputPrefix;
/*     */ 
/*     */   
/*     */   private final byte[] messageSuffix;
/*     */ 
/*     */   
/*     */   private final PublicKeyVerify verifier;
/*     */   
/*     */   @Nullable
/*     */   Provider conscryptOrNull;
/*     */ 
/*     */   
/*     */   private static void validateHash(RsaSsaPkcs1Parameters.HashType hash) throws GeneralSecurityException {
/*  72 */     if (hash == RsaSsaPkcs1Parameters.HashType.SHA256 || hash == RsaSsaPkcs1Parameters.HashType.SHA384 || hash == RsaSsaPkcs1Parameters.HashType.SHA512) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  77 */     throw new GeneralSecurityException("Unsupported hash: " + hash);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private RsaSsaPkcs1SignJce(RSAPrivateCrtKey privateKey, RsaSsaPkcs1Parameters.HashType hash, byte[] outputPrefix, byte[] messageSuffix, PublicKeyVerify verifier, @Nullable Provider conscryptOrNull) throws GeneralSecurityException {
/*  88 */     if (!FIPS.isCompatible()) {
/*  89 */       throw new GeneralSecurityException("Can not use RSA PKCS1.5 in FIPS-mode, as BoringCrypto module is not available.");
/*     */     }
/*     */ 
/*     */     
/*  93 */     validateHash(hash);
/*  94 */     Validators.validateRsaModulusSize(privateKey.getModulus().bitLength());
/*  95 */     Validators.validateRsaPublicExponent(privateKey.getPublicExponent());
/*  96 */     this.privateKey = privateKey;
/*  97 */     this.signatureAlgorithm = RsaSsaPkcs1VerifyConscrypt.toRsaSsaPkcs1Algo(hash);
/*  98 */     this.outputPrefix = outputPrefix;
/*  99 */     this.messageSuffix = messageSuffix;
/* 100 */     this.verifier = verifier;
/* 101 */     this.conscryptOrNull = conscryptOrNull;
/*     */   }
/*     */   
/*     */   public static PublicKeySign create(RsaSsaPkcs1PrivateKey key) throws GeneralSecurityException {
/* 105 */     Provider conscryptOrNull = RsaSsaPkcs1VerifyConscrypt.conscryptProviderOrNull();
/* 106 */     return createWithProviderOrNull(key, conscryptOrNull);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PublicKeySign createWithProvider(RsaSsaPkcs1PrivateKey key, Provider provider) throws GeneralSecurityException {
/* 115 */     if (provider == null) {
/* 116 */       throw new NullPointerException("provider must not be null");
/*     */     }
/* 118 */     return createWithProviderOrNull(key, provider);
/*     */   }
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   private static PublicKeySign createWithProviderOrNull(RsaSsaPkcs1PrivateKey key, @Nullable Provider providerOrNull) throws GeneralSecurityException {
/*     */     KeyFactory keyFactory;
/*     */     PublicKeyVerify verifier;
/* 126 */     if (providerOrNull != null) {
/* 127 */       keyFactory = KeyFactory.getInstance("RSA", providerOrNull);
/*     */     } else {
/* 129 */       keyFactory = (KeyFactory)EngineFactory.KEY_FACTORY.getInstance("RSA");
/*     */     } 
/*     */ 
/*     */     
/* 133 */     RSAPrivateCrtKey privateKey = (RSAPrivateCrtKey)keyFactory.generatePrivate(new RSAPrivateCrtKeySpec(key
/*     */           
/* 135 */           .getPublicKey().getModulus(), key
/* 136 */           .getParameters().getPublicExponent(), key
/* 137 */           .getPrivateExponent().getBigInteger(InsecureSecretKeyAccess.get()), key
/* 138 */           .getPrimeP().getBigInteger(InsecureSecretKeyAccess.get()), key
/* 139 */           .getPrimeQ().getBigInteger(InsecureSecretKeyAccess.get()), key
/* 140 */           .getPrimeExponentP().getBigInteger(InsecureSecretKeyAccess.get()), key
/* 141 */           .getPrimeExponentQ().getBigInteger(InsecureSecretKeyAccess.get()), key
/* 142 */           .getCrtCoefficient().getBigInteger(InsecureSecretKeyAccess.get())));
/*     */     
/* 144 */     if (providerOrNull != null) {
/* 145 */       verifier = RsaSsaPkcs1VerifyConscrypt.createWithProvider(key.getPublicKey(), providerOrNull);
/*     */     } else {
/* 147 */       verifier = RsaSsaPkcs1VerifyJce.create(key.getPublicKey());
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 156 */     PublicKeySign signer = new RsaSsaPkcs1SignJce(privateKey, key.getParameters().getHashType(), key.getOutputPrefix().toByteArray(), key.getParameters().getVariant().equals(RsaSsaPkcs1Parameters.Variant.LEGACY) ? legacyMessageSuffix : EMPTY, verifier, providerOrNull);
/*     */ 
/*     */     
/* 159 */     byte[] unused = signer.sign(testData);
/* 160 */     return signer;
/*     */   }
/*     */   
/*     */   private Signature getSignature() throws GeneralSecurityException {
/* 164 */     if (this.conscryptOrNull != null) {
/* 165 */       return Signature.getInstance(this.signatureAlgorithm, this.conscryptOrNull);
/*     */     }
/* 167 */     return (Signature)EngineFactory.SIGNATURE.getInstance(this.signatureAlgorithm);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] sign(byte[] data) throws GeneralSecurityException {
/* 172 */     Signature signer = getSignature();
/* 173 */     signer.initSign(this.privateKey);
/* 174 */     signer.update(data);
/* 175 */     if (this.messageSuffix.length > 0) {
/* 176 */       signer.update(this.messageSuffix);
/*     */     }
/* 178 */     byte[] signature = signer.sign();
/* 179 */     if (this.outputPrefix.length > 0) {
/* 180 */       signature = Bytes.concat(new byte[][] { this.outputPrefix, signature });
/*     */     }
/*     */     try {
/* 183 */       this.verifier.verify(signature, data);
/* 184 */     } catch (GeneralSecurityException e) {
/* 185 */       throw new IllegalStateException("RSA signature computation error", e);
/*     */     } 
/* 187 */     return signature;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\signature\internal\RsaSsaPkcs1SignJce.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */