/*     */ package com.google.crypto.tink.prf;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.KeyManager;
/*     */ import com.google.crypto.tink.KeyTemplate;
/*     */ import com.google.crypto.tink.Parameters;
/*     */ import com.google.crypto.tink.config.internal.TinkFipsUtil;
/*     */ import com.google.crypto.tink.internal.KeyCreator;
/*     */ import com.google.crypto.tink.internal.KeyManagerRegistry;
/*     */ import com.google.crypto.tink.internal.LegacyKeyManagerImpl;
/*     */ import com.google.crypto.tink.internal.MutableKeyCreationRegistry;
/*     */ import com.google.crypto.tink.internal.MutableParametersRegistry;
/*     */ import com.google.crypto.tink.internal.MutablePrimitiveRegistry;
/*     */ import com.google.crypto.tink.internal.PrimitiveConstructor;
/*     */ import com.google.crypto.tink.internal.TinkBugException;
/*     */ import com.google.crypto.tink.prf.internal.HkdfPrfProtoSerialization;
/*     */ import com.google.crypto.tink.proto.HkdfPrfKey;
/*     */ import com.google.crypto.tink.proto.KeyData;
/*     */ import com.google.crypto.tink.subtle.prf.HkdfStreamingPrf;
/*     */ import com.google.crypto.tink.subtle.prf.PrfImpl;
/*     */ import com.google.crypto.tink.subtle.prf.StreamingPrf;
/*     */ import com.google.crypto.tink.util.SecretBytes;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
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
/*     */ public class HkdfPrfKeyManager
/*     */ {
/*     */   private static void validate(HkdfPrfParameters parameters) throws GeneralSecurityException {
/*  50 */     if (parameters.getKeySizeBytes() < 32) {
/*  51 */       throw new GeneralSecurityException("Key size must be at least 32");
/*     */     }
/*  53 */     if (parameters.getHashType() != HkdfPrfParameters.HashType.SHA256 && parameters
/*  54 */       .getHashType() != HkdfPrfParameters.HashType.SHA512) {
/*  55 */       throw new GeneralSecurityException("Hash type must be SHA256 or SHA512");
/*     */     }
/*     */   }
/*     */   
/*     */   private static StreamingPrf createStreamingPrf(HkdfPrfKey key) throws GeneralSecurityException {
/*  60 */     validate(key.getParameters());
/*  61 */     return HkdfStreamingPrf.create(key);
/*     */   }
/*     */   
/*     */   private static Prf createPrf(HkdfPrfKey key) throws GeneralSecurityException {
/*  65 */     return (Prf)PrfImpl.wrap(createStreamingPrf(key));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  70 */   private static final PrimitiveConstructor<HkdfPrfKey, StreamingPrf> STREAMING_HKDF_PRF_CONSTRUCTOR = PrimitiveConstructor.create(HkdfPrfKeyManager::createStreamingPrf, HkdfPrfKey.class, StreamingPrf.class);
/*     */ 
/*     */   
/*  73 */   private static final PrimitiveConstructor<HkdfPrfKey, Prf> HKDF_PRF_CONSTRUCTOR = PrimitiveConstructor.create(HkdfPrfKeyManager::createPrf, HkdfPrfKey.class, Prf.class);
/*     */ 
/*     */   
/*  76 */   private static final KeyManager<Prf> legacyKeyManager = LegacyKeyManagerImpl.create(
/*  77 */       getKeyType(), Prf.class, KeyData.KeyMaterialType.SYMMETRIC, 
/*     */ 
/*     */       
/*  80 */       HkdfPrfKey.parser());
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   private static HkdfPrfKey newKey(HkdfPrfParameters parameters, @Nullable Integer idRequirement) throws GeneralSecurityException {
/*  85 */     if (idRequirement != null) {
/*  86 */       throw new GeneralSecurityException("Id Requirement is not supported for HKDF PRF keys");
/*     */     }
/*  88 */     validate(parameters);
/*  89 */     return HkdfPrfKey.builder()
/*  90 */       .setParameters(parameters)
/*  91 */       .setKeyBytes(SecretBytes.randomBytes(parameters.getKeySizeBytes()))
/*  92 */       .build();
/*     */   }
/*     */ 
/*     */   
/*  96 */   static final KeyCreator<HkdfPrfParameters> KEY_CREATOR = HkdfPrfKeyManager::newKey;
/*     */   private static final int MIN_KEY_SIZE = 32;
/*     */   
/*     */   static String getKeyType() {
/* 100 */     return "type.googleapis.com/google.crypto.tink.HkdfPrfKey";
/*     */   }
/*     */   
/*     */   private static Map<String, Parameters> namedParameters() throws GeneralSecurityException {
/* 104 */     Map<String, Parameters> result = new HashMap<>();
/* 105 */     result.put("HKDF_SHA256", PredefinedPrfParameters.HKDF_SHA256);
/* 106 */     return Collections.unmodifiableMap(result);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void register(boolean newKeyAllowed) throws GeneralSecurityException {
/* 115 */     if (!TinkFipsUtil.AlgorithmFipsCompatibility.ALGORITHM_NOT_FIPS.isCompatible()) {
/* 116 */       throw new GeneralSecurityException("Registering HKDF PRF is not supported in FIPS mode");
/*     */     }
/* 118 */     HkdfPrfProtoSerialization.register();
/* 119 */     MutablePrimitiveRegistry.globalInstance().registerPrimitiveConstructor(HKDF_PRF_CONSTRUCTOR);
/* 120 */     MutablePrimitiveRegistry.globalInstance()
/* 121 */       .registerPrimitiveConstructor(STREAMING_HKDF_PRF_CONSTRUCTOR);
/* 122 */     MutableKeyCreationRegistry.globalInstance().add(KEY_CREATOR, HkdfPrfParameters.class);
/* 123 */     MutableParametersRegistry.globalInstance().putAll(namedParameters());
/* 124 */     KeyManagerRegistry.globalInstance().registerKeyManager(legacyKeyManager, newKeyAllowed);
/*     */   }
/*     */   
/*     */   public static String staticKeyType() {
/* 128 */     return getKeyType();
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
/*     */   public static final KeyTemplate hkdfSha256Template() {
/* 141 */     return (KeyTemplate)TinkBugException.exceptionIsBug(() -> KeyTemplate.createFrom(HkdfPrfParameters.builder().setKeySizeBytes(32).setHashType(HkdfPrfParameters.HashType.SHA256).build()));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\prf\HkdfPrfKeyManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */