/*     */ package com.google.crypto.tink.signature.internal;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.InsecureSecretKeyAccess;
/*     */ import com.google.crypto.tink.PublicKeySign;
/*     */ import com.google.crypto.tink.config.internal.TinkFipsUtil;
/*     */ import com.google.crypto.tink.signature.RsaSsaPssParameters;
/*     */ import com.google.crypto.tink.signature.RsaSsaPssPrivateKey;
/*     */ import com.google.crypto.tink.subtle.Bytes;
/*     */ import com.google.crypto.tink.subtle.Validators;
/*     */ import com.google.errorprone.annotations.Immutable;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.KeyFactory;
/*     */ import java.security.NoSuchProviderException;
/*     */ import java.security.Provider;
/*     */ import java.security.Signature;
/*     */ import java.security.interfaces.RSAPrivateCrtKey;
/*     */ import java.security.spec.PSSParameterSpec;
/*     */ import java.security.spec.RSAPrivateCrtKeySpec;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public final class RsaSsaPssSignConscrypt
/*     */   implements PublicKeySign
/*     */ {
/*  40 */   public static final TinkFipsUtil.AlgorithmFipsCompatibility FIPS = TinkFipsUtil.AlgorithmFipsCompatibility.ALGORITHM_REQUIRES_BORINGCRYPTO;
/*     */ 
/*     */   
/*  43 */   private static final byte[] EMPTY = new byte[0];
/*  44 */   private static final byte[] legacyMessageSuffix = new byte[] { 0 };
/*     */ 
/*     */   
/*     */   private final RSAPrivateCrtKey privateKey;
/*     */ 
/*     */   
/*     */   private final String signatureAlgorithm;
/*     */ 
/*     */   
/*     */   private final PSSParameterSpec parameterSpec;
/*     */ 
/*     */   
/*     */   private final byte[] outputPrefix;
/*     */   
/*     */   private final byte[] messageSuffix;
/*     */   
/*     */   private final Provider conscrypt;
/*     */ 
/*     */   
/*     */   public static PublicKeySign create(RsaSsaPssPrivateKey key) throws GeneralSecurityException {
/*  64 */     Provider conscrypt = RsaSsaPssVerifyConscrypt.conscryptProviderOrNull();
/*  65 */     return createWithProvider(key, conscrypt);
/*     */   }
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   public static PublicKeySign createWithProvider(RsaSsaPssPrivateKey key, Provider conscrypt) throws GeneralSecurityException {
/*  71 */     if (conscrypt == null) {
/*  72 */       throw new NoSuchProviderException("RSA SSA PSS using Conscrypt is not supported.");
/*     */     }
/*  74 */     KeyFactory keyFactory = KeyFactory.getInstance("RSA", conscrypt);
/*  75 */     RsaSsaPssParameters params = key.getParameters();
/*     */ 
/*     */     
/*  78 */     RSAPrivateCrtKey privateKey = (RSAPrivateCrtKey)keyFactory.generatePrivate(new RSAPrivateCrtKeySpec(key
/*     */           
/*  80 */           .getPublicKey().getModulus(), params
/*  81 */           .getPublicExponent(), key
/*  82 */           .getPrivateExponent().getBigInteger(InsecureSecretKeyAccess.get()), key
/*  83 */           .getPrimeP().getBigInteger(InsecureSecretKeyAccess.get()), key
/*  84 */           .getPrimeQ().getBigInteger(InsecureSecretKeyAccess.get()), key
/*  85 */           .getPrimeExponentP().getBigInteger(InsecureSecretKeyAccess.get()), key
/*  86 */           .getPrimeExponentQ().getBigInteger(InsecureSecretKeyAccess.get()), key
/*  87 */           .getCrtCoefficient().getBigInteger(InsecureSecretKeyAccess.get())));
/*  88 */     return new RsaSsaPssSignConscrypt(privateKey, params
/*     */         
/*  90 */         .getSigHashType(), params
/*  91 */         .getMgf1HashType(), params
/*  92 */         .getSaltLengthBytes(), key
/*  93 */         .getOutputPrefix().toByteArray(), 
/*  94 */         params.getVariant().equals(RsaSsaPssParameters.Variant.LEGACY) ? 
/*  95 */         legacyMessageSuffix : 
/*  96 */         EMPTY, conscrypt);
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
/*     */   private RsaSsaPssSignConscrypt(RSAPrivateCrtKey privateKey, RsaSsaPssParameters.HashType sigHash, RsaSsaPssParameters.HashType mgf1Hash, int saltLength, byte[] outputPrefix, byte[] messageSuffix, Provider conscrypt) throws GeneralSecurityException {
/* 109 */     if (!FIPS.isCompatible()) {
/* 110 */       throw new GeneralSecurityException("Cannot use RSA PSS in FIPS-mode, as BoringCrypto module is not available.");
/*     */     }
/*     */     
/* 113 */     Validators.validateRsaModulusSize(privateKey.getModulus().bitLength());
/* 114 */     Validators.validateRsaPublicExponent(privateKey.getPublicExponent());
/* 115 */     this.privateKey = privateKey;
/* 116 */     this.signatureAlgorithm = RsaSsaPssVerifyConscrypt.getConscryptRsaSsaPssAlgo(sigHash);
/* 117 */     this
/* 118 */       .parameterSpec = RsaSsaPssVerifyConscrypt.getPssParameterSpec(sigHash, mgf1Hash, saltLength);
/* 119 */     this.outputPrefix = outputPrefix;
/* 120 */     this.messageSuffix = messageSuffix;
/* 121 */     this.conscrypt = conscrypt;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] sign(byte[] data) throws GeneralSecurityException {
/* 126 */     Signature signer = Signature.getInstance(this.signatureAlgorithm, this.conscrypt);
/* 127 */     signer.initSign(this.privateKey);
/* 128 */     signer.setParameter(this.parameterSpec);
/* 129 */     signer.update(data);
/* 130 */     if (this.messageSuffix.length > 0) {
/* 131 */       signer.update(this.messageSuffix);
/*     */     }
/* 133 */     byte[] signature = signer.sign();
/* 134 */     if (this.outputPrefix.length == 0) {
/* 135 */       return signature;
/*     */     }
/* 137 */     return Bytes.concat(new byte[][] { this.outputPrefix, signature });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\signature\internal\RsaSsaPssSignConscrypt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */