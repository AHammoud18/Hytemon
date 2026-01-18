/*     */ package com.google.crypto.tink.streamingaead;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.KeyManager;
/*     */ import com.google.crypto.tink.KeyTemplate;
/*     */ import com.google.crypto.tink.Parameters;
/*     */ import com.google.crypto.tink.StreamingAead;
/*     */ import com.google.crypto.tink.config.internal.TinkFipsUtil;
/*     */ import com.google.crypto.tink.internal.KeyCreator;
/*     */ import com.google.crypto.tink.internal.KeyManagerRegistry;
/*     */ import com.google.crypto.tink.internal.LegacyKeyManagerImpl;
/*     */ import com.google.crypto.tink.internal.MutableKeyCreationRegistry;
/*     */ import com.google.crypto.tink.internal.MutableParametersRegistry;
/*     */ import com.google.crypto.tink.internal.MutablePrimitiveRegistry;
/*     */ import com.google.crypto.tink.internal.PrimitiveConstructor;
/*     */ import com.google.crypto.tink.internal.TinkBugException;
/*     */ import com.google.crypto.tink.proto.AesCtrHmacStreamingKey;
/*     */ import com.google.crypto.tink.proto.KeyData;
/*     */ import com.google.crypto.tink.streamingaead.internal.AesCtrHmacStreamingProtoSerialization;
/*     */ import com.google.crypto.tink.subtle.AesCtrHmacStreaming;
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
/*     */ 
/*     */ public final class AesCtrHmacStreamingKeyManager
/*     */ {
/*  53 */   private static final PrimitiveConstructor<AesCtrHmacStreamingKey, StreamingAead> AES_CTR_HMAC_STREAMING_AEAD_PRIMITIVE_CONSTRUCTOR = PrimitiveConstructor.create(AesCtrHmacStreaming::create, AesCtrHmacStreamingKey.class, StreamingAead.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  60 */   private static final KeyCreator<AesCtrHmacStreamingParameters> KEY_CREATOR = AesCtrHmacStreamingKeyManager::createAesCtrHmacStreamingKey;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   private static AesCtrHmacStreamingKey createAesCtrHmacStreamingKey(AesCtrHmacStreamingParameters parameters, @Nullable Integer idRequirement) throws GeneralSecurityException {
/*  67 */     return AesCtrHmacStreamingKey.create(parameters, 
/*  68 */         SecretBytes.randomBytes(parameters.getKeySizeBytes()));
/*     */   }
/*     */ 
/*     */   
/*  72 */   private static final KeyManager<StreamingAead> legacyKeyManager = LegacyKeyManagerImpl.create(
/*  73 */       getKeyType(), StreamingAead.class, KeyData.KeyMaterialType.SYMMETRIC, 
/*     */ 
/*     */       
/*  76 */       AesCtrHmacStreamingKey.parser());
/*     */   
/*     */   static String getKeyType() {
/*  79 */     return "type.googleapis.com/google.crypto.tink.AesCtrHmacStreamingKey";
/*     */   }
/*     */   
/*     */   private static Map<String, Parameters> namedParameters() throws GeneralSecurityException {
/*  83 */     Map<String, Parameters> result = new HashMap<>();
/*  84 */     result.put("AES128_CTR_HMAC_SHA256_4KB", PredefinedStreamingAeadParameters.AES128_CTR_HMAC_SHA256_4KB);
/*     */ 
/*     */     
/*  87 */     result.put("AES128_CTR_HMAC_SHA256_1MB", PredefinedStreamingAeadParameters.AES128_CTR_HMAC_SHA256_1MB);
/*     */ 
/*     */     
/*  90 */     result.put("AES256_CTR_HMAC_SHA256_4KB", PredefinedStreamingAeadParameters.AES256_CTR_HMAC_SHA256_4KB);
/*     */ 
/*     */     
/*  93 */     result.put("AES256_CTR_HMAC_SHA256_1MB", PredefinedStreamingAeadParameters.AES256_CTR_HMAC_SHA256_1MB);
/*     */ 
/*     */     
/*  96 */     return Collections.unmodifiableMap(result);
/*     */   }
/*     */   
/*     */   public static void register(boolean newKeyAllowed) throws GeneralSecurityException {
/* 100 */     if (!TinkFipsUtil.AlgorithmFipsCompatibility.ALGORITHM_NOT_FIPS.isCompatible()) {
/* 101 */       throw new GeneralSecurityException("Registering AES CTR HMAC Streaming AEAD is not supported in FIPS mode");
/*     */     }
/*     */     
/* 104 */     AesCtrHmacStreamingProtoSerialization.register();
/* 105 */     MutableParametersRegistry.globalInstance().putAll(namedParameters());
/* 106 */     MutableKeyCreationRegistry.globalInstance()
/* 107 */       .add(KEY_CREATOR, AesCtrHmacStreamingParameters.class);
/* 108 */     MutablePrimitiveRegistry.globalInstance()
/* 109 */       .registerPrimitiveConstructor(AES_CTR_HMAC_STREAMING_AEAD_PRIMITIVE_CONSTRUCTOR);
/* 110 */     KeyManagerRegistry.globalInstance().registerKeyManager(legacyKeyManager, newKeyAllowed);
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
/*     */   public static final KeyTemplate aes128CtrHmacSha2564KBTemplate() {
/* 126 */     return (KeyTemplate)TinkBugException.exceptionIsBug(() -> KeyTemplate.createFrom(AesCtrHmacStreamingParameters.builder().setKeySizeBytes(16).setDerivedKeySizeBytes(16).setHkdfHashType(AesCtrHmacStreamingParameters.HashType.SHA256).setHmacHashType(AesCtrHmacStreamingParameters.HashType.SHA256).setHmacTagSizeBytes(Integer.valueOf(32)).setCiphertextSegmentSizeBytes(4096).build()));
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
/*     */   
/*     */   public static final KeyTemplate aes128CtrHmacSha2561MBTemplate() {
/* 152 */     return (KeyTemplate)TinkBugException.exceptionIsBug(() -> KeyTemplate.createFrom(AesCtrHmacStreamingParameters.builder().setKeySizeBytes(16).setDerivedKeySizeBytes(16).setHkdfHashType(AesCtrHmacStreamingParameters.HashType.SHA256).setHmacHashType(AesCtrHmacStreamingParameters.HashType.SHA256).setHmacTagSizeBytes(Integer.valueOf(32)).setCiphertextSegmentSizeBytes(1048576).build()));
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
/*     */   
/*     */   public static final KeyTemplate aes256CtrHmacSha2564KBTemplate() {
/* 178 */     return (KeyTemplate)TinkBugException.exceptionIsBug(() -> KeyTemplate.createFrom(AesCtrHmacStreamingParameters.builder().setKeySizeBytes(32).setDerivedKeySizeBytes(32).setHkdfHashType(AesCtrHmacStreamingParameters.HashType.SHA256).setHmacHashType(AesCtrHmacStreamingParameters.HashType.SHA256).setHmacTagSizeBytes(Integer.valueOf(32)).setCiphertextSegmentSizeBytes(4096).build()));
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
/*     */   
/*     */   public static final KeyTemplate aes256CtrHmacSha2561MBTemplate() {
/* 204 */     return (KeyTemplate)TinkBugException.exceptionIsBug(() -> KeyTemplate.createFrom(AesCtrHmacStreamingParameters.builder().setKeySizeBytes(32).setDerivedKeySizeBytes(32).setHkdfHashType(AesCtrHmacStreamingParameters.HashType.SHA256).setHmacHashType(AesCtrHmacStreamingParameters.HashType.SHA256).setHmacTagSizeBytes(Integer.valueOf(32)).setCiphertextSegmentSizeBytes(1048576).build()));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\streamingaead\AesCtrHmacStreamingKeyManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */