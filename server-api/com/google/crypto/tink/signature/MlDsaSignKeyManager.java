/*     */ package com.google.crypto.tink.signature;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.InsecureSecretKeyAccess;
/*     */ import com.google.crypto.tink.Parameters;
/*     */ import com.google.crypto.tink.config.internal.TinkFipsUtil;
/*     */ import com.google.crypto.tink.internal.ConscryptUtil;
/*     */ import com.google.crypto.tink.internal.KeyCreator;
/*     */ import com.google.crypto.tink.internal.MutableKeyCreationRegistry;
/*     */ import com.google.crypto.tink.internal.MutableParametersRegistry;
/*     */ import com.google.crypto.tink.signature.internal.MlDsaProtoSerialization;
/*     */ import com.google.crypto.tink.signature.internal.MlDsaVerifyConscrypt;
/*     */ import com.google.crypto.tink.util.Bytes;
/*     */ import com.google.crypto.tink.util.SecretBytes;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.KeyFactory;
/*     */ import java.security.KeyPair;
/*     */ import java.security.KeyPairGenerator;
/*     */ import java.security.Provider;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ final class MlDsaSignKeyManager
/*     */ {
/*     */   static final String ML_DSA_65_ALGORITHM = "ML-DSA-65";
/*     */   
/*     */   static String getPublicKeyType() {
/*  49 */     return "type.googleapis.com/google.crypto.tink.MlDsaPublicKey";
/*     */   }
/*     */   
/*     */   static String getPrivateKeyType() {
/*  53 */     return "type.googleapis.com/google.crypto.tink.MlDsaPrivateKey";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  58 */   private static final KeyCreator<MlDsaParameters> KEY_CREATOR = MlDsaSignKeyManager::createKey;
/*  59 */   private static final TinkFipsUtil.AlgorithmFipsCompatibility FIPS = TinkFipsUtil.AlgorithmFipsCompatibility.ALGORITHM_NOT_FIPS;
/*     */ 
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   private static MlDsaPrivateKey createKey(MlDsaParameters parameters, @Nullable Integer idRequirement) throws GeneralSecurityException {
/*  65 */     Provider provider = ConscryptUtil.providerOrNull();
/*  66 */     if (provider == null) {
/*  67 */       throw new GeneralSecurityException("Obtaining Conscrypt provider failed");
/*     */     }
/*     */     
/*  70 */     KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("ML-DSA-65", provider);
/*  71 */     KeyPair keyPair = keyPairGenerator.generateKeyPair();
/*  72 */     KeyFactory keyFactory = KeyFactory.getInstance("ML-DSA-65", provider);
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
/*  83 */     MlDsaPublicKey publicKey = MlDsaPublicKey.builder().setSerializedPublicKey(Bytes.copyFrom(((MlDsaVerifyConscrypt.RawKeySpec)keyFactory.<MlDsaVerifyConscrypt.RawKeySpec>getKeySpec(keyPair.getPublic(), MlDsaVerifyConscrypt.RawKeySpec.class)).getEncoded())).setParameters(parameters).setIdRequirement(idRequirement).build();
/*     */     
/*  85 */     SecretBytes privateSeed = SecretBytes.copyFrom(((MlDsaVerifyConscrypt.RawKeySpec)keyFactory
/*     */         
/*  87 */         .<MlDsaVerifyConscrypt.RawKeySpec>getKeySpec(keyPair.getPrivate(), MlDsaVerifyConscrypt.RawKeySpec.class))
/*  88 */         .getEncoded(), 
/*  89 */         InsecureSecretKeyAccess.get());
/*     */     
/*  91 */     return MlDsaPrivateKey.createWithoutVerification(publicKey, privateSeed);
/*     */   }
/*     */   
/*     */   private static Map<String, Parameters> namedParameters() throws GeneralSecurityException {
/*  95 */     return Map.of("ML_DSA_65", 
/*     */         
/*  97 */         MlDsaParameters.create(MlDsaParameters.MlDsaInstance.ML_DSA_65, MlDsaParameters.Variant.TINK), "ML_DSA_65_RAW", 
/*     */         
/*  99 */         MlDsaParameters.create(MlDsaParameters.MlDsaInstance.ML_DSA_65, MlDsaParameters.Variant.NO_PREFIX));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void registerPair() throws GeneralSecurityException {
/* 106 */     if (!FIPS.isCompatible()) {
/* 107 */       throw new GeneralSecurityException("Cannot use ML-DSA in FIPS-mode, as it is not yet certified in Conscrypt.");
/*     */     }
/*     */     
/* 110 */     if (ConscryptUtil.providerOrNull() == null) {
/* 111 */       throw new GeneralSecurityException("Cannot use ML-DSA without Conscrypt provider");
/*     */     }
/* 113 */     MlDsaProtoSerialization.register();
/* 114 */     MutableParametersRegistry.globalInstance().putAll(namedParameters());
/* 115 */     MutableKeyCreationRegistry.globalInstance().add(KEY_CREATOR, MlDsaParameters.class);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\signature\MlDsaSignKeyManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */