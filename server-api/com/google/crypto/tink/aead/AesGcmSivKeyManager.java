/*     */ package com.google.crypto.tink.aead;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.Aead;
/*     */ import com.google.crypto.tink.KeyManager;
/*     */ import com.google.crypto.tink.KeyTemplate;
/*     */ import com.google.crypto.tink.Parameters;
/*     */ import com.google.crypto.tink.SecretKeyAccess;
/*     */ import com.google.crypto.tink.aead.internal.AesGcmSivProtoSerialization;
/*     */ import com.google.crypto.tink.aead.subtle.AesGcmSiv;
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
/*     */ import com.google.crypto.tink.proto.AesGcmSivKey;
/*     */ import com.google.crypto.tink.proto.KeyData;
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
/*     */ 
/*     */ 
/*     */ public final class AesGcmSivKeyManager
/*     */ {
/*  55 */   private static final PrimitiveConstructor<AesGcmSivKey, Aead> AES_GCM_SIV_PRIMITIVE_CONSTRUCTOR = PrimitiveConstructor.create(AesGcmSiv::create, AesGcmSivKey.class, Aead.class);
/*     */ 
/*     */ 
/*     */   
/*  59 */   private static final KeyCreator<AesGcmSivParameters> KEY_CREATOR = AesGcmSivKeyManager::createAesGcmSivKey;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  64 */   private static final MutableKeyDerivationRegistry.InsecureKeyCreator<AesGcmSivParameters> KEY_DERIVER = AesGcmSivKeyManager::createAesGcmSivKeyFromRandomness;
/*     */ 
/*     */   
/*  67 */   private static final KeyManager<Aead> legacyKeyManager = LegacyKeyManagerImpl.create("type.googleapis.com/google.crypto.tink.AesGcmSivKey", Aead.class, KeyData.KeyMaterialType.SYMMETRIC, 
/*     */ 
/*     */ 
/*     */       
/*  71 */       AesGcmSivKey.parser());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   static AesGcmSivKey createAesGcmSivKeyFromRandomness(AesGcmSivParameters parameters, InputStream stream, @Nullable Integer idRequirement, SecretKeyAccess access) throws GeneralSecurityException {
/*  80 */     return AesGcmSivKey.builder()
/*  81 */       .setParameters(parameters)
/*  82 */       .setIdRequirement(idRequirement)
/*  83 */       .setKeyBytes(Util.readIntoSecretBytes(stream, parameters.getKeySizeBytes(), access))
/*  84 */       .build();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   private static AesGcmSivKey createAesGcmSivKey(AesGcmSivParameters parameters, @Nullable Integer idRequirement) throws GeneralSecurityException {
/*  91 */     return AesGcmSivKey.builder()
/*  92 */       .setParameters(parameters)
/*  93 */       .setIdRequirement(idRequirement)
/*  94 */       .setKeyBytes(SecretBytes.randomBytes(parameters.getKeySizeBytes()))
/*  95 */       .build();
/*     */   }
/*     */   
/*     */   private static Map<String, Parameters> namedParameters() throws GeneralSecurityException {
/*  99 */     Map<String, Parameters> result = new HashMap<>();
/*     */     
/* 101 */     result.put("AES128_GCM_SIV", 
/*     */         
/* 103 */         AesGcmSivParameters.builder()
/* 104 */         .setKeySizeBytes(16)
/* 105 */         .setVariant(AesGcmSivParameters.Variant.TINK)
/* 106 */         .build());
/* 107 */     result.put("AES128_GCM_SIV_RAW", 
/*     */         
/* 109 */         AesGcmSivParameters.builder()
/* 110 */         .setKeySizeBytes(16)
/* 111 */         .setVariant(AesGcmSivParameters.Variant.NO_PREFIX)
/* 112 */         .build());
/* 113 */     result.put("AES256_GCM_SIV", 
/*     */         
/* 115 */         AesGcmSivParameters.builder()
/* 116 */         .setKeySizeBytes(32)
/* 117 */         .setVariant(AesGcmSivParameters.Variant.TINK)
/* 118 */         .build());
/* 119 */     result.put("AES256_GCM_SIV_RAW", 
/*     */         
/* 121 */         AesGcmSivParameters.builder()
/* 122 */         .setKeySizeBytes(32)
/* 123 */         .setVariant(AesGcmSivParameters.Variant.NO_PREFIX)
/* 124 */         .build());
/*     */     
/* 126 */     return Collections.unmodifiableMap(result);
/*     */   }
/*     */   
/*     */   public static void register(boolean newKeyAllowed) throws GeneralSecurityException {
/* 130 */     if (!TinkFipsUtil.AlgorithmFipsCompatibility.ALGORITHM_NOT_FIPS.isCompatible()) {
/* 131 */       throw new GeneralSecurityException("Registering AES GCM SIV is not supported in FIPS mode");
/*     */     }
/* 133 */     AesGcmSivProtoSerialization.register();
/* 134 */     MutableParametersRegistry.globalInstance().putAll(namedParameters());
/* 135 */     MutableKeyDerivationRegistry.globalInstance().add(KEY_DERIVER, AesGcmSivParameters.class);
/* 136 */     MutableKeyCreationRegistry.globalInstance().add(KEY_CREATOR, AesGcmSivParameters.class);
/* 137 */     MutablePrimitiveRegistry.globalInstance()
/* 138 */       .registerPrimitiveConstructor(AES_GCM_SIV_PRIMITIVE_CONSTRUCTOR);
/* 139 */     KeyManagerRegistry.globalInstance().registerKeyManager(legacyKeyManager, newKeyAllowed);
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
/*     */   public static final KeyTemplate aes128GcmSivTemplate() {
/* 152 */     return (KeyTemplate)TinkBugException.exceptionIsBug(() -> KeyTemplate.createFrom(AesGcmSivParameters.builder().setKeySizeBytes(16).setVariant(AesGcmSivParameters.Variant.TINK).build()));
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
/*     */   public static final KeyTemplate rawAes128GcmSivTemplate() {
/* 173 */     return (KeyTemplate)TinkBugException.exceptionIsBug(() -> KeyTemplate.createFrom(AesGcmSivParameters.builder().setKeySizeBytes(16).setVariant(AesGcmSivParameters.Variant.NO_PREFIX).build()));
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
/*     */   public static final KeyTemplate aes256GcmSivTemplate() {
/* 192 */     return (KeyTemplate)TinkBugException.exceptionIsBug(() -> KeyTemplate.createFrom(AesGcmSivParameters.builder().setKeySizeBytes(32).setVariant(AesGcmSivParameters.Variant.TINK).build()));
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
/*     */   public static final KeyTemplate rawAes256GcmSivTemplate() {
/* 213 */     return (KeyTemplate)TinkBugException.exceptionIsBug(() -> KeyTemplate.createFrom(AesGcmSivParameters.builder().setKeySizeBytes(32).setVariant(AesGcmSivParameters.Variant.NO_PREFIX).build()));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\aead\AesGcmSivKeyManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */