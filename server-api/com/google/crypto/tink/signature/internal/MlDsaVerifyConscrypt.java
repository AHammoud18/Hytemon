/*     */ package com.google.crypto.tink.signature.internal;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.PublicKeyVerify;
/*     */ import com.google.crypto.tink.config.internal.TinkFipsUtil;
/*     */ import com.google.crypto.tink.internal.ConscryptUtil;
/*     */ import com.google.crypto.tink.internal.Util;
/*     */ import com.google.crypto.tink.signature.MlDsaParameters;
/*     */ import com.google.crypto.tink.signature.MlDsaPublicKey;
/*     */ import com.google.errorprone.annotations.Immutable;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.KeyFactory;
/*     */ import java.security.Provider;
/*     */ import java.security.PublicKey;
/*     */ import java.security.Signature;
/*     */ import java.security.spec.EncodedKeySpec;
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
/*     */ public final class MlDsaVerifyConscrypt
/*     */   implements PublicKeyVerify
/*     */ {
/*  39 */   public static final TinkFipsUtil.AlgorithmFipsCompatibility FIPS = TinkFipsUtil.AlgorithmFipsCompatibility.ALGORITHM_NOT_FIPS;
/*     */ 
/*     */   
/*     */   static final int ML_DSA_65_SIG_LENGTH = 3309;
/*     */ 
/*     */   
/*     */   static final String ML_DSA_65_ALGORITHM = "ML-DSA-65";
/*     */ 
/*     */   
/*     */   private final byte[] outputPrefix;
/*     */ 
/*     */   
/*     */   private final PublicKey publicKey;
/*     */ 
/*     */   
/*     */   private final String algorithm;
/*     */   
/*     */   private final int signatureLength;
/*     */   
/*     */   private final Provider provider;
/*     */ 
/*     */   
/*     */   private MlDsaVerifyConscrypt(byte[] outputPrefix, PublicKey publicKey, String algorithm, int signatureLength, Provider provider) {
/*  62 */     this.outputPrefix = outputPrefix;
/*  63 */     this.publicKey = publicKey;
/*  64 */     this.algorithm = algorithm;
/*  65 */     this.signatureLength = signatureLength;
/*  66 */     this.provider = provider;
/*     */   }
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   public static PublicKeyVerify createWithProvider(MlDsaPublicKey mlDsaPublicKey, Provider provider) throws GeneralSecurityException {
/*  72 */     if (provider == null) {
/*  73 */       throw new NullPointerException("provider must not be null");
/*     */     }
/*  75 */     if (!FIPS.isCompatible()) {
/*  76 */       throw new GeneralSecurityException("Can not use ML-DSA in FIPS-mode, as it is not yet certified in Conscrypt.");
/*     */     }
/*     */ 
/*     */     
/*  80 */     MlDsaParameters.MlDsaInstance mlDsaInstance = mlDsaPublicKey.getParameters().getMlDsaInstance();
/*  81 */     if (mlDsaInstance != MlDsaParameters.MlDsaInstance.ML_DSA_65) {
/*  82 */       throw new GeneralSecurityException("Only ML-DSA-65 currently supported");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  88 */     PublicKey publicKey = KeyFactory.getInstance("ML-DSA-65", provider).generatePublic(new RawKeySpec(mlDsaPublicKey.getSerializedPublicKey().toByteArray()));
/*     */     
/*  90 */     return new MlDsaVerifyConscrypt(mlDsaPublicKey
/*  91 */         .getOutputPrefix().toByteArray(), publicKey, "ML-DSA-65", 3309, provider);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   public static PublicKeyVerify create(MlDsaPublicKey mlDsaPublicKey) throws GeneralSecurityException {
/* 101 */     if (!FIPS.isCompatible()) {
/* 102 */       throw new GeneralSecurityException("Can not use ML-DSA in FIPS-mode, as it is not yet certified in Conscrypt.");
/*     */     }
/*     */     
/* 105 */     Provider provider = ConscryptUtil.providerOrNull();
/* 106 */     if (provider == null) {
/* 107 */       throw new GeneralSecurityException("Obtaining Conscrypt provider failed");
/*     */     }
/* 109 */     return createWithProvider(mlDsaPublicKey, provider);
/*     */   }
/*     */ 
/*     */   
/*     */   public void verify(byte[] signature, byte[] data) throws GeneralSecurityException {
/* 114 */     if (!Util.isPrefix(this.outputPrefix, signature)) {
/* 115 */       throw new GeneralSecurityException("Invalid signature (output prefix mismatch)");
/*     */     }
/* 117 */     if (signature.length != this.outputPrefix.length + this.signatureLength) {
/* 118 */       throw new GeneralSecurityException("Invalid signature length");
/*     */     }
/* 120 */     Signature verifier = Signature.getInstance(this.algorithm, this.provider);
/* 121 */     verifier.initVerify(this.publicKey);
/* 122 */     verifier.update(data);
/* 123 */     if (!verifier.verify(signature, this.outputPrefix.length, this.signatureLength)) {
/* 124 */       throw new GeneralSecurityException("Invalid signature");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isSupported() {
/* 130 */     if (!FIPS.isCompatible()) {
/* 131 */       return false;
/*     */     }
/*     */     
/* 134 */     Provider provider = ConscryptUtil.providerOrNull();
/* 135 */     if (provider == null) {
/* 136 */       return false;
/*     */     }
/*     */     
/*     */     try {
/* 140 */       KeyFactory unusedKeyFactory = KeyFactory.getInstance("ML-DSA-65", provider);
/* 141 */       Signature unusedSignature = Signature.getInstance("ML-DSA-65", provider);
/* 142 */       return true;
/* 143 */     } catch (GeneralSecurityException e) {
/* 144 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static final class RawKeySpec
/*     */     extends EncodedKeySpec {
/*     */     public RawKeySpec(byte[] encoded) {
/* 151 */       super(encoded);
/*     */     }
/*     */ 
/*     */     
/*     */     public String getFormat() {
/* 156 */       return "raw";
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\signature\internal\MlDsaVerifyConscrypt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */