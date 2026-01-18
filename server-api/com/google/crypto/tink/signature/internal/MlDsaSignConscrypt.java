/*     */ package com.google.crypto.tink.signature.internal;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.InsecureSecretKeyAccess;
/*     */ import com.google.crypto.tink.PublicKeySign;
/*     */ import com.google.crypto.tink.config.internal.TinkFipsUtil;
/*     */ import com.google.crypto.tink.internal.ConscryptUtil;
/*     */ import com.google.crypto.tink.signature.MlDsaParameters;
/*     */ import com.google.crypto.tink.signature.MlDsaPrivateKey;
/*     */ import com.google.errorprone.annotations.Immutable;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.KeyFactory;
/*     */ import java.security.PrivateKey;
/*     */ import java.security.Provider;
/*     */ import java.security.Signature;
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
/*     */ public final class MlDsaSignConscrypt
/*     */   implements PublicKeySign
/*     */ {
/*  42 */   public static final TinkFipsUtil.AlgorithmFipsCompatibility FIPS = TinkFipsUtil.AlgorithmFipsCompatibility.ALGORITHM_NOT_FIPS;
/*     */ 
/*     */   
/*     */   private static final String TEST_WORKLOAD = "test workload";
/*     */ 
/*     */   
/*     */   private final byte[] outputPrefix;
/*     */ 
/*     */   
/*     */   private final PrivateKey privateKey;
/*     */ 
/*     */   
/*     */   private final String algorithm;
/*     */   
/*     */   private final int signatureLength;
/*     */   
/*     */   private final Provider provider;
/*     */ 
/*     */   
/*     */   private MlDsaSignConscrypt(byte[] outputPrefix, PrivateKey privateKey, String algorithm, int signatureLength, Provider provider) {
/*  62 */     this.outputPrefix = outputPrefix;
/*  63 */     this.privateKey = privateKey;
/*  64 */     this.algorithm = algorithm;
/*  65 */     this.signatureLength = signatureLength;
/*  66 */     this.provider = provider;
/*     */   }
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   public static PublicKeySign createWithProvider(MlDsaPrivateKey mlDsaPrivateKey, Provider provider) throws GeneralSecurityException {
/*  72 */     if (provider == null) {
/*  73 */       throw new NullPointerException("provider must not be null");
/*     */     }
/*  75 */     if (!FIPS.isCompatible()) {
/*  76 */       throw new GeneralSecurityException("Can not use ML-DSA in FIPS-mode, as it is not yet certified in Conscrypt.");
/*     */     }
/*     */     
/*  79 */     MlDsaParameters.MlDsaInstance mlDsaInstance = mlDsaPrivateKey.getPublicKey().getParameters().getMlDsaInstance();
/*  80 */     if (mlDsaInstance != MlDsaParameters.MlDsaInstance.ML_DSA_65) {
/*  81 */       throw new GeneralSecurityException("Only ML-DSA-65 currently supported");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  86 */     PrivateKey privateKey = KeyFactory.getInstance("ML-DSA-65", provider).generatePrivate(new MlDsaVerifyConscrypt.RawKeySpec(mlDsaPrivateKey
/*     */           
/*  88 */           .getPrivateSeed().toByteArray(InsecureSecretKeyAccess.get())));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  94 */     byte[] testSignature = signInternal("test workload"
/*  95 */         .getBytes(StandardCharsets.UTF_8), mlDsaPrivateKey
/*  96 */         .getOutputPrefix().toByteArray(), privateKey, "ML-DSA-65", 3309, provider);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 103 */     MlDsaVerifyConscrypt verifier = (MlDsaVerifyConscrypt)MlDsaVerifyConscrypt.createWithProvider(mlDsaPrivateKey.getPublicKey(), provider);
/* 104 */     verifier.verify(testSignature, "test workload".getBytes(StandardCharsets.UTF_8));
/*     */ 
/*     */     
/* 107 */     return new MlDsaSignConscrypt(mlDsaPrivateKey
/* 108 */         .getOutputPrefix().toByteArray(), privateKey, "ML-DSA-65", 3309, provider);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   public static PublicKeySign create(MlDsaPrivateKey mlDsaPrivateKey) throws GeneralSecurityException {
/* 118 */     if (!FIPS.isCompatible()) {
/* 119 */       throw new GeneralSecurityException("Can not use ML-DSA in FIPS-mode, as it is not yet certified in Conscrypt.");
/*     */     }
/*     */     
/* 122 */     Provider provider = ConscryptUtil.providerOrNull();
/* 123 */     if (provider == null) {
/* 124 */       throw new GeneralSecurityException("Obtaining Conscrypt provider failed");
/*     */     }
/* 126 */     return createWithProvider(mlDsaPrivateKey, provider);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isSupported() {
/* 131 */     return MlDsaVerifyConscrypt.isSupported();
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] sign(byte[] data) throws GeneralSecurityException {
/* 136 */     return signInternal(data, this.outputPrefix, this.privateKey, this.algorithm, this.signatureLength, this.provider);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static byte[] signInternal(byte[] data, byte[] outputPrefix, PrivateKey privateKey, String algorithm, int signatureLength, Provider provider) throws GeneralSecurityException {
/* 147 */     Signature signer = Signature.getInstance(algorithm, provider);
/* 148 */     signer.initSign(privateKey);
/* 149 */     signer.update(data);
/* 150 */     byte[] signature = new byte[outputPrefix.length + signatureLength];
/* 151 */     if (outputPrefix.length > 0) {
/* 152 */       System.arraycopy(outputPrefix, 0, signature, 0, outputPrefix.length);
/*     */     }
/*     */ 
/*     */     
/* 156 */     signer.sign(signature, outputPrefix.length, signatureLength);
/* 157 */     return signature;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\signature\internal\MlDsaSignConscrypt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */