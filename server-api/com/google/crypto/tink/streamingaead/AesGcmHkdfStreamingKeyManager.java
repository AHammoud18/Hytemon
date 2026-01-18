/*     */ package com.google.crypto.tink.streamingaead;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.KeyManager;
/*     */ import com.google.crypto.tink.KeyTemplate;
/*     */ import com.google.crypto.tink.Parameters;
/*     */ import com.google.crypto.tink.SecretKeyAccess;
/*     */ import com.google.crypto.tink.StreamingAead;
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
/*     */ import com.google.crypto.tink.proto.AesGcmHkdfStreamingKey;
/*     */ import com.google.crypto.tink.proto.KeyData;
/*     */ import com.google.crypto.tink.streamingaead.internal.AesGcmHkdfStreamingProtoSerialization;
/*     */ import com.google.crypto.tink.subtle.AesGcmHkdfStreaming;
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
/*     */ 
/*     */ public final class AesGcmHkdfStreamingKeyManager
/*     */ {
/*  56 */   private static final PrimitiveConstructor<AesGcmHkdfStreamingKey, StreamingAead> AES_GCM_HKDF_STREAMING_AEAD_PRIMITIVE_CONSTRUCTOR = PrimitiveConstructor.create(AesGcmHkdfStreaming::create, AesGcmHkdfStreamingKey.class, StreamingAead.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  62 */   private static final KeyManager<StreamingAead> legacyKeyManager = LegacyKeyManagerImpl.create(
/*  63 */       getKeyType(), StreamingAead.class, KeyData.KeyMaterialType.SYMMETRIC, 
/*     */ 
/*     */       
/*  66 */       AesGcmHkdfStreamingKey.parser());
/*     */   
/*     */   static String getKeyType() {
/*  69 */     return "type.googleapis.com/google.crypto.tink.AesGcmHkdfStreamingKey";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  74 */   private static final KeyCreator<AesGcmHkdfStreamingParameters> KEY_CREATOR = AesGcmHkdfStreamingKeyManager::creatAesGcmHkdfStreamingKey;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   private static AesGcmHkdfStreamingKey creatAesGcmHkdfStreamingKey(AesGcmHkdfStreamingParameters parameters, @Nullable Integer idRequirement) throws GeneralSecurityException {
/*  81 */     return AesGcmHkdfStreamingKey.create(parameters, 
/*  82 */         SecretBytes.randomBytes(parameters.getKeySizeBytes()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  88 */   private static final MutableKeyDerivationRegistry.InsecureKeyCreator<AesGcmHkdfStreamingParameters> KEY_DERIVER = AesGcmHkdfStreamingKeyManager::createAesGcmHkdfStreamingKeyFromRandomness;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   static AesGcmHkdfStreamingKey createAesGcmHkdfStreamingKeyFromRandomness(AesGcmHkdfStreamingParameters parameters, InputStream stream, @Nullable Integer idRequirement, SecretKeyAccess access) throws GeneralSecurityException {
/*  98 */     return AesGcmHkdfStreamingKey.create(parameters, 
/*  99 */         Util.readIntoSecretBytes(stream, parameters.getKeySizeBytes(), access));
/*     */   }
/*     */   
/*     */   private static Map<String, Parameters> namedParameters() throws GeneralSecurityException {
/* 103 */     Map<String, Parameters> result = new HashMap<>();
/* 104 */     result.put("AES128_GCM_HKDF_4KB", PredefinedStreamingAeadParameters.AES128_GCM_HKDF_4KB);
/* 105 */     result.put("AES128_GCM_HKDF_1MB", PredefinedStreamingAeadParameters.AES128_GCM_HKDF_1MB);
/* 106 */     result.put("AES256_GCM_HKDF_4KB", PredefinedStreamingAeadParameters.AES256_GCM_HKDF_4KB);
/* 107 */     result.put("AES256_GCM_HKDF_1MB", PredefinedStreamingAeadParameters.AES256_GCM_HKDF_1MB);
/* 108 */     return Collections.unmodifiableMap(result);
/*     */   }
/*     */   
/*     */   public static void register(boolean newKeyAllowed) throws GeneralSecurityException {
/* 112 */     if (!TinkFipsUtil.AlgorithmFipsCompatibility.ALGORITHM_NOT_FIPS.isCompatible()) {
/* 113 */       throw new GeneralSecurityException("Registering AES-GCM HKDF Streaming AEAD is not supported in FIPS mode");
/*     */     }
/*     */     
/* 116 */     AesGcmHkdfStreamingProtoSerialization.register();
/* 117 */     MutableParametersRegistry.globalInstance().putAll(namedParameters());
/* 118 */     MutableKeyDerivationRegistry.globalInstance()
/* 119 */       .add(KEY_DERIVER, AesGcmHkdfStreamingParameters.class);
/* 120 */     MutableKeyCreationRegistry.globalInstance()
/* 121 */       .add(KEY_CREATOR, AesGcmHkdfStreamingParameters.class);
/* 122 */     MutablePrimitiveRegistry.globalInstance()
/* 123 */       .registerPrimitiveConstructor(AES_GCM_HKDF_STREAMING_AEAD_PRIMITIVE_CONSTRUCTOR);
/* 124 */     KeyManagerRegistry.globalInstance().registerKeyManager(legacyKeyManager, newKeyAllowed);
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
/*     */   public static final KeyTemplate aes128GcmHkdf4KBTemplate() {
/* 138 */     return (KeyTemplate)TinkBugException.exceptionIsBug(() -> KeyTemplate.createFrom(AesGcmHkdfStreamingParameters.builder().setKeySizeBytes(16).setDerivedAesGcmKeySizeBytes(16).setCiphertextSegmentSizeBytes(4096).setHkdfHashType(AesGcmHkdfStreamingParameters.HashType.SHA256).build()));
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
/*     */   public static final KeyTemplate aes128GcmHkdf1MBTemplate() {
/* 160 */     return (KeyTemplate)TinkBugException.exceptionIsBug(() -> KeyTemplate.createFrom(AesGcmHkdfStreamingParameters.builder().setKeySizeBytes(16).setDerivedAesGcmKeySizeBytes(16).setCiphertextSegmentSizeBytes(1048576).setHkdfHashType(AesGcmHkdfStreamingParameters.HashType.SHA256).build()));
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
/*     */   public static final KeyTemplate aes256GcmHkdf4KBTemplate() {
/* 182 */     return (KeyTemplate)TinkBugException.exceptionIsBug(() -> KeyTemplate.createFrom(AesGcmHkdfStreamingParameters.builder().setKeySizeBytes(32).setDerivedAesGcmKeySizeBytes(32).setCiphertextSegmentSizeBytes(4096).setHkdfHashType(AesGcmHkdfStreamingParameters.HashType.SHA256).build()));
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
/*     */   public static final KeyTemplate aes256GcmHkdf1MBTemplate() {
/* 204 */     return (KeyTemplate)TinkBugException.exceptionIsBug(() -> KeyTemplate.createFrom(AesGcmHkdfStreamingParameters.builder().setKeySizeBytes(32).setDerivedAesGcmKeySizeBytes(32).setCiphertextSegmentSizeBytes(1048576).setHkdfHashType(AesGcmHkdfStreamingParameters.HashType.SHA256).build()));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\streamingaead\AesGcmHkdfStreamingKeyManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */