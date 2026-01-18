/*     */ package com.google.crypto.tink.aead;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.Aead;
/*     */ import com.google.crypto.tink.KeyManager;
/*     */ import com.google.crypto.tink.KeyTemplate;
/*     */ import com.google.crypto.tink.Parameters;
/*     */ import com.google.crypto.tink.SecretKeyAccess;
/*     */ import com.google.crypto.tink.aead.internal.AesGcmProtoSerialization;
/*     */ import com.google.crypto.tink.config.internal.TinkFipsUtil;
/*     */ import com.google.crypto.tink.internal.KeyCreator;
/*     */ import com.google.crypto.tink.internal.KeyManagerRegistry;
/*     */ import com.google.crypto.tink.internal.LegacyKeyManagerImpl;
/*     */ import com.google.crypto.tink.internal.MutableKeyCreationRegistry;
/*     */ import com.google.crypto.tink.internal.MutableKeyDerivationRegistry;
/*     */ import com.google.crypto.tink.internal.MutableParametersRegistry;
/*     */ import com.google.crypto.tink.internal.MutablePrimitiveRegistry;
/*     */ import com.google.crypto.tink.internal.PrimitiveConstructor;
/*     */ import com.google.crypto.tink.internal.TinkBugException;
/*     */ import com.google.crypto.tink.internal.Util;
/*     */ import com.google.crypto.tink.proto.AesGcmKey;
/*     */ import com.google.crypto.tink.proto.KeyData;
/*     */ import com.google.crypto.tink.subtle.AesGcmJce;
/*     */ import com.google.crypto.tink.util.SecretBytes;
/*     */ import java.io.InputStream;
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
/*     */ 
/*     */ public final class AesGcmKeyManager
/*     */ {
/*     */   private static final void validate(AesGcmParameters parameters) throws GeneralSecurityException {
/*  54 */     if (parameters.getKeySizeBytes() == 24) {
/*  55 */       throw new GeneralSecurityException("192 bit AES GCM Parameters are not valid");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*  60 */   private static final PrimitiveConstructor<AesGcmKey, Aead> AES_GCM_PRIMITIVE_CONSTRUCTOR = PrimitiveConstructor.create(AesGcmJce::create, AesGcmKey.class, Aead.class);
/*     */   
/*     */   static String getKeyType() {
/*  63 */     return "type.googleapis.com/google.crypto.tink.AesGcmKey";
/*     */   }
/*     */ 
/*     */   
/*  67 */   private static final KeyManager<Aead> legacyKeyManager = LegacyKeyManagerImpl.create(
/*  68 */       getKeyType(), Aead.class, KeyData.KeyMaterialType.SYMMETRIC, 
/*     */ 
/*     */       
/*  71 */       AesGcmKey.parser());
/*     */   
/*     */   private static Map<String, Parameters> namedParameters() throws GeneralSecurityException {
/*  74 */     Map<String, Parameters> result = new HashMap<>();
/*  75 */     result.put("AES128_GCM", PredefinedAeadParameters.AES128_GCM);
/*  76 */     result.put("AES128_GCM_RAW", 
/*     */         
/*  78 */         AesGcmParameters.builder()
/*  79 */         .setIvSizeBytes(12)
/*  80 */         .setKeySizeBytes(16)
/*  81 */         .setTagSizeBytes(16)
/*  82 */         .setVariant(AesGcmParameters.Variant.NO_PREFIX)
/*  83 */         .build());
/*  84 */     result.put("AES256_GCM", PredefinedAeadParameters.AES256_GCM);
/*  85 */     result.put("AES256_GCM_RAW", 
/*     */         
/*  87 */         AesGcmParameters.builder()
/*  88 */         .setIvSizeBytes(12)
/*  89 */         .setKeySizeBytes(32)
/*  90 */         .setTagSizeBytes(16)
/*  91 */         .setVariant(AesGcmParameters.Variant.NO_PREFIX)
/*  92 */         .build());
/*  93 */     return Collections.unmodifiableMap(result);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  98 */   private static final MutableKeyDerivationRegistry.InsecureKeyCreator<AesGcmParameters> KEY_DERIVER = AesGcmKeyManager::createAesGcmKeyFromRandomness;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   static AesGcmKey createAesGcmKeyFromRandomness(AesGcmParameters parameters, InputStream stream, @Nullable Integer idRequirement, SecretKeyAccess access) throws GeneralSecurityException {
/* 107 */     validate(parameters);
/* 108 */     return AesGcmKey.builder()
/* 109 */       .setParameters(parameters)
/* 110 */       .setIdRequirement(idRequirement)
/* 111 */       .setKeyBytes(Util.readIntoSecretBytes(stream, parameters.getKeySizeBytes(), access))
/* 112 */       .build();
/*     */   }
/*     */ 
/*     */   
/* 116 */   private static final KeyCreator<AesGcmParameters> KEY_CREATOR = AesGcmKeyManager::createAesGcmKey;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   private static AesGcmKey createAesGcmKey(AesGcmParameters parameters, @Nullable Integer idRequirement) throws GeneralSecurityException {
/* 123 */     validate(parameters);
/* 124 */     return AesGcmKey.builder()
/* 125 */       .setParameters(parameters)
/* 126 */       .setIdRequirement(idRequirement)
/* 127 */       .setKeyBytes(SecretBytes.randomBytes(parameters.getKeySizeBytes()))
/* 128 */       .build();
/*     */   }
/*     */   
/* 131 */   private static final TinkFipsUtil.AlgorithmFipsCompatibility FIPS = TinkFipsUtil.AlgorithmFipsCompatibility.ALGORITHM_REQUIRES_BORINGCRYPTO;
/*     */ 
/*     */   
/*     */   public static void register(boolean newKeyAllowed) throws GeneralSecurityException {
/* 135 */     if (!FIPS.isCompatible()) {
/* 136 */       throw new GeneralSecurityException("Can not use AES-GCM in FIPS-mode, as BoringCrypto module is not available.");
/*     */     }
/*     */     
/* 139 */     AesGcmProtoSerialization.register();
/* 140 */     MutablePrimitiveRegistry.globalInstance()
/* 141 */       .registerPrimitiveConstructor(AES_GCM_PRIMITIVE_CONSTRUCTOR);
/* 142 */     MutableParametersRegistry.globalInstance().putAll(namedParameters());
/* 143 */     MutableKeyDerivationRegistry.globalInstance().add(KEY_DERIVER, AesGcmParameters.class);
/* 144 */     MutableKeyCreationRegistry.globalInstance().add(KEY_CREATOR, AesGcmParameters.class);
/* 145 */     KeyManagerRegistry.globalInstance()
/* 146 */       .registerKeyManagerWithFipsCompatibility(legacyKeyManager, FIPS, newKeyAllowed);
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
/*     */ 
/*     */   
/*     */   public static final KeyTemplate aes128GcmTemplate() {
/* 161 */     return (KeyTemplate)TinkBugException.exceptionIsBug(() -> KeyTemplate.createFrom(AesGcmParameters.builder().setIvSizeBytes(12).setKeySizeBytes(16).setTagSizeBytes(16).setVariant(AesGcmParameters.Variant.TINK).build()));
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
/*     */   public static final KeyTemplate rawAes128GcmTemplate() {
/* 186 */     return (KeyTemplate)TinkBugException.exceptionIsBug(() -> KeyTemplate.createFrom(AesGcmParameters.builder().setIvSizeBytes(12).setKeySizeBytes(16).setTagSizeBytes(16).setVariant(AesGcmParameters.Variant.NO_PREFIX).build()));
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
/*     */   public static final KeyTemplate aes256GcmTemplate() {
/* 209 */     return (KeyTemplate)TinkBugException.exceptionIsBug(() -> KeyTemplate.createFrom(AesGcmParameters.builder().setIvSizeBytes(12).setKeySizeBytes(32).setTagSizeBytes(16).setVariant(AesGcmParameters.Variant.TINK).build()));
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
/*     */   public static final KeyTemplate rawAes256GcmTemplate() {
/* 234 */     return (KeyTemplate)TinkBugException.exceptionIsBug(() -> KeyTemplate.createFrom(AesGcmParameters.builder().setIvSizeBytes(12).setKeySizeBytes(32).setTagSizeBytes(16).setVariant(AesGcmParameters.Variant.NO_PREFIX).build()));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\aead\AesGcmKeyManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */