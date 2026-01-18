/*     */ package com.google.crypto.tink.signature.internal;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.PublicKeyVerify;
/*     */ import com.google.crypto.tink.config.internal.TinkFipsUtil;
/*     */ import com.google.crypto.tink.internal.ConscryptUtil;
/*     */ import com.google.crypto.tink.internal.Util;
/*     */ import com.google.crypto.tink.signature.RsaSsaPssParameters;
/*     */ import com.google.crypto.tink.signature.RsaSsaPssPublicKey;
/*     */ import com.google.crypto.tink.subtle.Validators;
/*     */ import com.google.errorprone.annotations.Immutable;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.KeyFactory;
/*     */ import java.security.NoSuchProviderException;
/*     */ import java.security.Provider;
/*     */ import java.security.Signature;
/*     */ import java.security.interfaces.RSAPublicKey;
/*     */ import java.security.spec.MGF1ParameterSpec;
/*     */ import java.security.spec.PSSParameterSpec;
/*     */ import java.security.spec.RSAPublicKeySpec;
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
/*     */ @Immutable
/*     */ public final class RsaSsaPssVerifyConscrypt
/*     */   implements PublicKeyVerify
/*     */ {
/*  44 */   public static final TinkFipsUtil.AlgorithmFipsCompatibility FIPS = TinkFipsUtil.AlgorithmFipsCompatibility.ALGORITHM_REQUIRES_BORINGCRYPTO;
/*     */ 
/*     */   
/*  47 */   private static final byte[] EMPTY = new byte[0];
/*  48 */   private static final byte[] legacyMessageSuffix = new byte[] { 0 };
/*     */   private static final String MGF_1 = "MGF1";
/*     */   private static final int TRAILER_FIELD_BC = 1;
/*     */   private final RSAPublicKey publicKey;
/*     */   private final String signatureAlgorithm;
/*     */   
/*     */   @Nullable
/*     */   static Provider conscryptProviderOrNull() {
/*  56 */     if (Util.isAndroid() && Util.getAndroidApiLevel().intValue() <= 23)
/*     */     {
/*  58 */       return null;
/*     */     }
/*  60 */     return ConscryptUtil.providerOrNull();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final PSSParameterSpec parameterSpec;
/*     */ 
/*     */ 
/*     */   
/*     */   private final byte[] outputPrefix;
/*     */ 
/*     */ 
/*     */   
/*     */   private final byte[] messageSuffix;
/*     */ 
/*     */ 
/*     */   
/*     */   private final Provider conscrypt;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String getConscryptRsaSsaPssAlgo(RsaSsaPssParameters.HashType hash) {
/*  85 */     if (hash == RsaSsaPssParameters.HashType.SHA256)
/*  86 */       return "SHA256withRSA/PSS"; 
/*  87 */     if (hash == RsaSsaPssParameters.HashType.SHA384)
/*  88 */       return "SHA384withRSA/PSS"; 
/*  89 */     if (hash == RsaSsaPssParameters.HashType.SHA512) {
/*  90 */       return "SHA512withRSA/PSS";
/*     */     }
/*  92 */     throw new IllegalArgumentException("Unsupported hash: " + hash);
/*     */   }
/*     */ 
/*     */   
/*     */   private static String getMdName(RsaSsaPssParameters.HashType sigHash) {
/*  97 */     if (sigHash == RsaSsaPssParameters.HashType.SHA256)
/*  98 */       return "SHA-256"; 
/*  99 */     if (sigHash == RsaSsaPssParameters.HashType.SHA384)
/* 100 */       return "SHA-384"; 
/* 101 */     if (sigHash == RsaSsaPssParameters.HashType.SHA512) {
/* 102 */       return "SHA-512";
/*     */     }
/* 104 */     throw new IllegalArgumentException("Unsupported MD hash: " + sigHash);
/*     */   }
/*     */   
/*     */   private static MGF1ParameterSpec getMgf1Hash(RsaSsaPssParameters.HashType mgf1Hash) {
/* 108 */     if (mgf1Hash == RsaSsaPssParameters.HashType.SHA256)
/* 109 */       return MGF1ParameterSpec.SHA256; 
/* 110 */     if (mgf1Hash == RsaSsaPssParameters.HashType.SHA384)
/* 111 */       return MGF1ParameterSpec.SHA384; 
/* 112 */     if (mgf1Hash == RsaSsaPssParameters.HashType.SHA512) {
/* 113 */       return MGF1ParameterSpec.SHA512;
/*     */     }
/* 115 */     throw new IllegalArgumentException("Unsupported MGF1 hash: " + mgf1Hash);
/*     */   }
/*     */ 
/*     */   
/*     */   static PSSParameterSpec getPssParameterSpec(RsaSsaPssParameters.HashType sigHash, RsaSsaPssParameters.HashType mgf1Hash, int saltLength) {
/* 120 */     return new PSSParameterSpec(
/* 121 */         getMdName(sigHash), "MGF1", getMgf1Hash(mgf1Hash), saltLength, 1);
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
/*     */   private RsaSsaPssVerifyConscrypt(RSAPublicKey pubKey, RsaSsaPssParameters.HashType sigHash, RsaSsaPssParameters.HashType mgf1Hash, int saltLength, byte[] outputPrefix, byte[] messageSuffix, Provider conscrypt) throws GeneralSecurityException {
/* 133 */     if (!FIPS.isCompatible()) {
/* 134 */       throw new GeneralSecurityException("Cannot use RSA SSA PSS in FIPS-mode, as BoringCrypto module is not available.");
/*     */     }
/*     */     
/* 137 */     if (!sigHash.equals(mgf1Hash)) {
/* 138 */       throw new GeneralSecurityException("sigHash and mgf1Hash must be the same");
/*     */     }
/* 140 */     Validators.validateRsaModulusSize(pubKey.getModulus().bitLength());
/* 141 */     Validators.validateRsaPublicExponent(pubKey.getPublicExponent());
/* 142 */     this.publicKey = pubKey;
/* 143 */     this.signatureAlgorithm = getConscryptRsaSsaPssAlgo(sigHash);
/* 144 */     this.parameterSpec = getPssParameterSpec(sigHash, mgf1Hash, saltLength);
/* 145 */     this.outputPrefix = outputPrefix;
/* 146 */     this.messageSuffix = messageSuffix;
/* 147 */     this.conscrypt = conscrypt;
/*     */   }
/*     */   
/*     */   public static PublicKeyVerify create(RsaSsaPssPublicKey key) throws GeneralSecurityException {
/* 151 */     Provider conscrypt = conscryptProviderOrNull();
/* 152 */     return createWithProvider(key, conscrypt);
/*     */   }
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   public static PublicKeyVerify createWithProvider(RsaSsaPssPublicKey key, Provider conscrypt) throws GeneralSecurityException {
/* 158 */     if (conscrypt == null) {
/* 159 */       throw new NoSuchProviderException("RSA SSA PSS using Conscrypt is not supported.");
/*     */     }
/* 161 */     KeyFactory keyFactory = KeyFactory.getInstance("RSA", conscrypt);
/*     */ 
/*     */     
/* 164 */     RSAPublicKey publicKey = (RSAPublicKey)keyFactory.generatePublic(new RSAPublicKeySpec(key
/* 165 */           .getModulus(), key.getParameters().getPublicExponent()));
/* 166 */     RsaSsaPssParameters params = key.getParameters();
/* 167 */     return new RsaSsaPssVerifyConscrypt(publicKey, params
/*     */         
/* 169 */         .getSigHashType(), params
/* 170 */         .getMgf1HashType(), params
/* 171 */         .getSaltLengthBytes(), key
/* 172 */         .getOutputPrefix().toByteArray(), 
/* 173 */         key.getParameters().getVariant().equals(RsaSsaPssParameters.Variant.LEGACY) ? 
/* 174 */         legacyMessageSuffix : 
/* 175 */         EMPTY, conscrypt);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void verify(byte[] signature, byte[] data) throws GeneralSecurityException {
/* 181 */     if (!Util.isPrefix(this.outputPrefix, signature)) {
/* 182 */       throw new GeneralSecurityException("Invalid signature (output prefix mismatch)");
/*     */     }
/* 184 */     Signature verifier = Signature.getInstance(this.signatureAlgorithm, this.conscrypt);
/* 185 */     verifier.initVerify(this.publicKey);
/* 186 */     verifier.setParameter(this.parameterSpec);
/* 187 */     verifier.update(data);
/* 188 */     if (this.messageSuffix.length > 0) {
/* 189 */       verifier.update(this.messageSuffix);
/*     */     }
/* 191 */     if (!verifier.verify(signature, this.outputPrefix.length, signature.length - this.outputPrefix.length))
/* 192 */       throw new GeneralSecurityException("signature verification failed"); 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\signature\internal\RsaSsaPssVerifyConscrypt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */