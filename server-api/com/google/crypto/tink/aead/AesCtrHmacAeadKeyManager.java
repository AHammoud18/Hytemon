/*     */ package com.google.crypto.tink.aead;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.Aead;
/*     */ import com.google.crypto.tink.KeyManager;
/*     */ import com.google.crypto.tink.KeyTemplate;
/*     */ import com.google.crypto.tink.Parameters;
/*     */ import com.google.crypto.tink.SecretKeyAccess;
/*     */ import com.google.crypto.tink.aead.internal.AesCtrHmacAeadProtoSerialization;
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
/*     */ import com.google.crypto.tink.proto.AesCtrHmacAeadKey;
/*     */ import com.google.crypto.tink.proto.KeyData;
/*     */ import com.google.crypto.tink.subtle.EncryptThenAuthenticate;
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
/*     */ public final class AesCtrHmacAeadKeyManager
/*     */ {
/*     */   private static void validate(AesCtrHmacAeadParameters parameters) throws GeneralSecurityException {
/*  55 */     if (parameters.getAesKeySizeBytes() != 16 && parameters.getAesKeySizeBytes() != 32) {
/*  56 */       throw new GeneralSecurityException("AES key size must be 16 or 32 bytes");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  62 */   private static final PrimitiveConstructor<AesCtrHmacAeadKey, Aead> AES_CTR_HMAC_AEAD_PRIMITIVE_CONSTRUCTOR = PrimitiveConstructor.create(EncryptThenAuthenticate::create, AesCtrHmacAeadKey.class, Aead.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  68 */   private static final KeyManager<Aead> legacyKeyManager = LegacyKeyManagerImpl.create(
/*  69 */       getKeyType(), Aead.class, KeyData.KeyMaterialType.SYMMETRIC, 
/*     */ 
/*     */       
/*  72 */       AesCtrHmacAeadKey.parser());
/*     */   
/*     */   static String getKeyType() {
/*  75 */     return "type.googleapis.com/google.crypto.tink.AesCtrHmacAeadKey";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  80 */   private static final MutableKeyDerivationRegistry.InsecureKeyCreator<AesCtrHmacAeadParameters> KEY_DERIVER = AesCtrHmacAeadKeyManager::createAesCtrHmacAeadKeyFromRandomness;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   static AesCtrHmacAeadKey createAesCtrHmacAeadKeyFromRandomness(AesCtrHmacAeadParameters parameters, InputStream stream, @Nullable Integer idRequirement, SecretKeyAccess access) throws GeneralSecurityException {
/*  98 */     return AesCtrHmacAeadKey.builder()
/*  99 */       .setParameters(parameters)
/* 100 */       .setIdRequirement(idRequirement)
/* 101 */       .setAesKeyBytes(Util.readIntoSecretBytes(stream, parameters.getAesKeySizeBytes(), access))
/* 102 */       .setHmacKeyBytes(Util.readIntoSecretBytes(stream, parameters.getHmacKeySizeBytes(), access))
/* 103 */       .build();
/*     */   }
/*     */ 
/*     */   
/* 107 */   private static final KeyCreator<AesCtrHmacAeadParameters> KEY_CREATOR = AesCtrHmacAeadKeyManager::createAesCtrHmacAeadKey;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   static AesCtrHmacAeadKey createAesCtrHmacAeadKey(AesCtrHmacAeadParameters parameters, @Nullable Integer idRequirement) throws GeneralSecurityException {
/* 114 */     validate(parameters);
/* 115 */     return AesCtrHmacAeadKey.builder()
/* 116 */       .setParameters(parameters)
/* 117 */       .setIdRequirement(idRequirement)
/* 118 */       .setAesKeyBytes(SecretBytes.randomBytes(parameters.getAesKeySizeBytes()))
/* 119 */       .setHmacKeyBytes(SecretBytes.randomBytes(parameters.getHmacKeySizeBytes()))
/* 120 */       .build();
/*     */   }
/*     */   
/*     */   private static Map<String, Parameters> namedParameters() throws GeneralSecurityException {
/* 124 */     Map<String, Parameters> result = new HashMap<>();
/*     */     
/* 126 */     result.put("AES128_CTR_HMAC_SHA256", PredefinedAeadParameters.AES128_CTR_HMAC_SHA256);
/* 127 */     result.put("AES128_CTR_HMAC_SHA256_RAW", 
/*     */         
/* 129 */         AesCtrHmacAeadParameters.builder()
/* 130 */         .setAesKeySizeBytes(16)
/* 131 */         .setHmacKeySizeBytes(32)
/* 132 */         .setTagSizeBytes(16)
/* 133 */         .setIvSizeBytes(16)
/* 134 */         .setHashType(AesCtrHmacAeadParameters.HashType.SHA256)
/* 135 */         .setVariant(AesCtrHmacAeadParameters.Variant.NO_PREFIX)
/* 136 */         .build());
/*     */     
/* 138 */     result.put("AES256_CTR_HMAC_SHA256", PredefinedAeadParameters.AES256_CTR_HMAC_SHA256);
/* 139 */     result.put("AES256_CTR_HMAC_SHA256_RAW", 
/*     */         
/* 141 */         AesCtrHmacAeadParameters.builder()
/* 142 */         .setAesKeySizeBytes(32)
/* 143 */         .setHmacKeySizeBytes(32)
/* 144 */         .setTagSizeBytes(32)
/* 145 */         .setIvSizeBytes(16)
/* 146 */         .setHashType(AesCtrHmacAeadParameters.HashType.SHA256)
/* 147 */         .setVariant(AesCtrHmacAeadParameters.Variant.NO_PREFIX)
/* 148 */         .build());
/*     */     
/* 150 */     return Collections.unmodifiableMap(result);
/*     */   }
/*     */   
/* 153 */   private static final TinkFipsUtil.AlgorithmFipsCompatibility FIPS = TinkFipsUtil.AlgorithmFipsCompatibility.ALGORITHM_REQUIRES_BORINGCRYPTO;
/*     */ 
/*     */   
/*     */   public static void register(boolean newKeyAllowed) throws GeneralSecurityException {
/* 157 */     if (!FIPS.isCompatible()) {
/* 158 */       throw new GeneralSecurityException("Can not use AES-CTR-HMAC in FIPS-mode, as BoringCrypto module is not available.");
/*     */     }
/*     */     
/* 161 */     AesCtrHmacAeadProtoSerialization.register();
/* 162 */     MutablePrimitiveRegistry.globalInstance()
/* 163 */       .registerPrimitiveConstructor(AES_CTR_HMAC_AEAD_PRIMITIVE_CONSTRUCTOR);
/* 164 */     MutableParametersRegistry.globalInstance().putAll(namedParameters());
/* 165 */     MutableKeyDerivationRegistry.globalInstance().add(KEY_DERIVER, AesCtrHmacAeadParameters.class);
/* 166 */     MutableKeyCreationRegistry.globalInstance().add(KEY_CREATOR, AesCtrHmacAeadParameters.class);
/* 167 */     KeyManagerRegistry.globalInstance()
/* 168 */       .registerKeyManagerWithFipsCompatibility(legacyKeyManager, FIPS, newKeyAllowed);
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
/*     */   public static final KeyTemplate aes128CtrHmacSha256Template() {
/* 183 */     return (KeyTemplate)TinkBugException.exceptionIsBug(() -> KeyTemplate.createFrom(AesCtrHmacAeadParameters.builder().setAesKeySizeBytes(16).setHmacKeySizeBytes(32).setIvSizeBytes(16).setTagSizeBytes(16).setHashType(AesCtrHmacAeadParameters.HashType.SHA256).setVariant(AesCtrHmacAeadParameters.Variant.TINK).build()));
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
/*     */   public static final KeyTemplate aes256CtrHmacSha256Template() {
/* 208 */     return (KeyTemplate)TinkBugException.exceptionIsBug(() -> KeyTemplate.createFrom(AesCtrHmacAeadParameters.builder().setAesKeySizeBytes(32).setHmacKeySizeBytes(32).setIvSizeBytes(16).setTagSizeBytes(32).setHashType(AesCtrHmacAeadParameters.HashType.SHA256).setVariant(AesCtrHmacAeadParameters.Variant.TINK).build()));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\aead\AesCtrHmacAeadKeyManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */