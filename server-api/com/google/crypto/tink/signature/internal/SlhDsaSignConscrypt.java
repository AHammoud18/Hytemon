/*     */ package com.google.crypto.tink.signature.internal;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.InsecureSecretKeyAccess;
/*     */ import com.google.crypto.tink.PublicKeySign;
/*     */ import com.google.crypto.tink.config.internal.TinkFipsUtil;
/*     */ import com.google.crypto.tink.internal.ConscryptUtil;
/*     */ import com.google.crypto.tink.signature.SlhDsaParameters;
/*     */ import com.google.crypto.tink.signature.SlhDsaPrivateKey;
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
/*     */ 
/*     */ 
/*     */ @Immutable
/*     */ public class SlhDsaSignConscrypt
/*     */   implements PublicKeySign
/*     */ {
/*  44 */   public static final TinkFipsUtil.AlgorithmFipsCompatibility FIPS = TinkFipsUtil.AlgorithmFipsCompatibility.ALGORITHM_NOT_FIPS;
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
/*     */   
/*     */   private final int signatureLength;
/*     */ 
/*     */   
/*     */   private final Provider provider;
/*     */ 
/*     */ 
/*     */   
/*     */   public SlhDsaSignConscrypt(byte[] outputPrefix, PrivateKey privateKey, String algorithm, int signatureLength, Provider provider) {
/*  67 */     this.outputPrefix = outputPrefix;
/*  68 */     this.privateKey = privateKey;
/*  69 */     this.algorithm = algorithm;
/*  70 */     this.signatureLength = signatureLength;
/*  71 */     this.provider = provider;
/*     */   }
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   public static PublicKeySign createWithProvider(SlhDsaPrivateKey slhDsaPrivateKey, Provider provider) throws GeneralSecurityException {
/*  77 */     if (provider == null) {
/*  78 */       throw new NullPointerException("provider must not be null");
/*     */     }
/*  80 */     if (!FIPS.isCompatible()) {
/*  81 */       throw new GeneralSecurityException("Can not use SLH-DSA in FIPS-mode, as it is not yet certified in Conscrypt.");
/*     */     }
/*     */ 
/*     */     
/*  85 */     SlhDsaParameters parameters = slhDsaPrivateKey.getParameters();
/*  86 */     if (parameters.getPrivateKeySize() != 64 || parameters
/*  87 */       .getHashType() != SlhDsaParameters.HashType.SHA2 || parameters
/*  88 */       .getSignatureType() != SlhDsaParameters.SignatureType.SMALL_SIGNATURE) {
/*  89 */       throw new GeneralSecurityException("Unsupported SLH-DSA parameters");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  94 */     PrivateKey privateKey = KeyFactory.getInstance("SLH-DSA-SHA2-128S", provider).generatePrivate(new SlhDsaVerifyConscrypt.RawKeySpec(slhDsaPrivateKey
/*     */ 
/*     */           
/*  97 */           .getPrivateKeyBytes()
/*  98 */           .toByteArray(InsecureSecretKeyAccess.get())));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 104 */     byte[] testSignature = signInternal("test workload"
/* 105 */         .getBytes(StandardCharsets.UTF_8), slhDsaPrivateKey
/* 106 */         .getOutputPrefix().toByteArray(), privateKey, "SLH-DSA-SHA2-128S", 7856, provider);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 112 */     SlhDsaVerifyConscrypt verifier = (SlhDsaVerifyConscrypt)SlhDsaVerifyConscrypt.createWithProvider(slhDsaPrivateKey.getPublicKey(), provider);
/* 113 */     verifier.verify(testSignature, "test workload".getBytes(StandardCharsets.UTF_8));
/*     */ 
/*     */     
/* 116 */     return new SlhDsaSignConscrypt(slhDsaPrivateKey
/* 117 */         .getOutputPrefix().toByteArray(), privateKey, "SLH-DSA-SHA2-128S", 7856, provider);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   public static PublicKeySign create(SlhDsaPrivateKey slhDsaPrivateKey) throws GeneralSecurityException {
/* 128 */     if (!FIPS.isCompatible()) {
/* 129 */       throw new GeneralSecurityException("Can not use SLH-DSA in FIPS-mode, as it is not yet certified in Conscrypt.");
/*     */     }
/*     */     
/* 132 */     Provider provider = ConscryptUtil.providerOrNull();
/* 133 */     if (provider == null) {
/* 134 */       throw new GeneralSecurityException("Obtaining Conscrypt provider failed");
/*     */     }
/* 136 */     return createWithProvider(slhDsaPrivateKey, provider);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isSupported() {
/* 142 */     return SlhDsaVerifyConscrypt.isSupported();
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] sign(byte[] data) throws GeneralSecurityException {
/* 147 */     return signInternal(data, this.outputPrefix, this.privateKey, this.algorithm, this.signatureLength, this.provider);
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
/* 158 */     Signature signer = Signature.getInstance(algorithm, provider);
/* 159 */     signer.initSign(privateKey);
/* 160 */     signer.update(data);
/* 161 */     byte[] signature = new byte[outputPrefix.length + signatureLength];
/* 162 */     if (outputPrefix.length > 0) {
/* 163 */       System.arraycopy(outputPrefix, 0, signature, 0, outputPrefix.length);
/*     */     }
/*     */ 
/*     */     
/* 167 */     signer.sign(signature, outputPrefix.length, signatureLength);
/* 168 */     return signature;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\signature\internal\SlhDsaSignConscrypt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */