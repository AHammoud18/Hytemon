/*     */ package com.google.crypto.tink.mac;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.KeyManager;
/*     */ import com.google.crypto.tink.KeyTemplate;
/*     */ import com.google.crypto.tink.Mac;
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
/*     */ import com.google.crypto.tink.mac.internal.AesCmacProtoSerialization;
/*     */ import com.google.crypto.tink.mac.internal.ChunkedAesCmacImpl;
/*     */ import com.google.crypto.tink.proto.AesCmacKey;
/*     */ import com.google.crypto.tink.proto.KeyData;
/*     */ import com.google.crypto.tink.subtle.PrfMac;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class AesCmacKeyManager
/*     */ {
/*     */   private static final int KEY_SIZE_IN_BYTES = 32;
/*     */   
/*     */   private static void validateParameters(AesCmacParameters parameters) throws GeneralSecurityException {
/*  56 */     if (parameters.getKeySizeBytes() != 32) {
/*  57 */       throw new GeneralSecurityException("AesCmacKey size wrong, must be 32 bytes");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   private static AesCmacKey createAesCmacKey(AesCmacParameters parameters, @Nullable Integer idRequirement) throws GeneralSecurityException {
/*  65 */     validateParameters(parameters);
/*  66 */     return AesCmacKey.builder()
/*  67 */       .setParameters(parameters)
/*  68 */       .setAesKeyBytes(SecretBytes.randomBytes(parameters.getKeySizeBytes()))
/*  69 */       .setIdRequirement(idRequirement)
/*  70 */       .build();
/*     */   }
/*     */   
/*     */   private static ChunkedMac createChunkedMac(AesCmacKey key) throws GeneralSecurityException {
/*  74 */     validateParameters(key.getParameters());
/*  75 */     return ChunkedAesCmacImpl.create(key);
/*     */   }
/*     */   
/*     */   private static Mac createMac(AesCmacKey key) throws GeneralSecurityException {
/*  79 */     validateParameters(key.getParameters());
/*  80 */     return PrfMac.create(key);
/*     */   }
/*     */   
/*  83 */   private static final KeyCreator<AesCmacParameters> KEY_CREATOR = AesCmacKeyManager::createAesCmacKey;
/*     */ 
/*     */ 
/*     */   
/*  87 */   private static final PrimitiveConstructor<AesCmacKey, ChunkedMac> CHUNKED_MAC_PRIMITIVE_CONSTRUCTOR = PrimitiveConstructor.create(AesCmacKeyManager::createChunkedMac, AesCmacKey.class, ChunkedMac.class);
/*     */ 
/*     */   
/*  90 */   private static final PrimitiveConstructor<AesCmacKey, Mac> MAC_PRIMITIVE_CONSTRUCTOR = PrimitiveConstructor.create(AesCmacKeyManager::createMac, AesCmacKey.class, Mac.class);
/*     */   
/*  92 */   private static final KeyManager<Mac> legacyKeyManager = LegacyKeyManagerImpl.create("type.googleapis.com/google.crypto.tink.AesCmacKey", Mac.class, KeyData.KeyMaterialType.SYMMETRIC, 
/*     */ 
/*     */ 
/*     */       
/*  96 */       AesCmacKey.parser());
/*     */   
/*     */   public static void register(boolean newKeyAllowed) throws GeneralSecurityException {
/*  99 */     if (!TinkFipsUtil.AlgorithmFipsCompatibility.ALGORITHM_NOT_FIPS.isCompatible()) {
/* 100 */       throw new GeneralSecurityException("Registering AES CMAC is not supported in FIPS mode");
/*     */     }
/* 102 */     AesCmacProtoSerialization.register();
/* 103 */     MutableKeyCreationRegistry.globalInstance().add(KEY_CREATOR, AesCmacParameters.class);
/* 104 */     MutablePrimitiveRegistry.globalInstance()
/* 105 */       .registerPrimitiveConstructor(CHUNKED_MAC_PRIMITIVE_CONSTRUCTOR);
/* 106 */     MutablePrimitiveRegistry.globalInstance()
/* 107 */       .registerPrimitiveConstructor(MAC_PRIMITIVE_CONSTRUCTOR);
/* 108 */     MutableParametersRegistry.globalInstance().putAll(namedParameters());
/* 109 */     KeyManagerRegistry.globalInstance().registerKeyManager(legacyKeyManager, newKeyAllowed);
/*     */   }
/*     */   
/*     */   private static Map<String, Parameters> namedParameters() throws GeneralSecurityException {
/* 113 */     Map<String, Parameters> result = new HashMap<>();
/* 114 */     result.put("AES_CMAC", PredefinedMacParameters.AES_CMAC);
/* 115 */     result.put("AES256_CMAC", PredefinedMacParameters.AES_CMAC);
/* 116 */     result.put("AES256_CMAC_RAW", 
/*     */         
/* 118 */         AesCmacParameters.builder()
/* 119 */         .setKeySizeBytes(32)
/* 120 */         .setTagSizeBytes(16)
/* 121 */         .setVariant(AesCmacParameters.Variant.NO_PREFIX)
/* 122 */         .build());
/* 123 */     return Collections.unmodifiableMap(result);
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
/*     */   public static final KeyTemplate aes256CmacTemplate() {
/* 136 */     return (KeyTemplate)TinkBugException.exceptionIsBug(() -> KeyTemplate.createFrom(AesCmacParameters.builder().setKeySizeBytes(32).setTagSizeBytes(16).setVariant(AesCmacParameters.Variant.TINK).build()));
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
/*     */   public static final KeyTemplate rawAes256CmacTemplate() {
/* 156 */     return (KeyTemplate)TinkBugException.exceptionIsBug(() -> KeyTemplate.createFrom(AesCmacParameters.builder().setKeySizeBytes(32).setTagSizeBytes(16).setVariant(AesCmacParameters.Variant.NO_PREFIX).build()));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\mac\AesCmacKeyManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */