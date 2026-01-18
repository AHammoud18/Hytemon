/*     */ package com.google.crypto.tink.aead;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.Aead;
/*     */ import com.google.crypto.tink.KeyManager;
/*     */ import com.google.crypto.tink.KeyTemplate;
/*     */ import com.google.crypto.tink.Parameters;
/*     */ import com.google.crypto.tink.aead.internal.AesEaxProtoSerialization;
/*     */ import com.google.crypto.tink.config.internal.TinkFipsUtil;
/*     */ import com.google.crypto.tink.internal.KeyCreator;
/*     */ import com.google.crypto.tink.internal.KeyManagerRegistry;
/*     */ import com.google.crypto.tink.internal.LegacyKeyManagerImpl;
/*     */ import com.google.crypto.tink.internal.MutableKeyCreationRegistry;
/*     */ import com.google.crypto.tink.internal.MutableParametersRegistry;
/*     */ import com.google.crypto.tink.internal.MutablePrimitiveRegistry;
/*     */ import com.google.crypto.tink.internal.PrimitiveConstructor;
/*     */ import com.google.crypto.tink.internal.TinkBugException;
/*     */ import com.google.crypto.tink.proto.AesEaxKey;
/*     */ import com.google.crypto.tink.proto.KeyData;
/*     */ import com.google.crypto.tink.subtle.AesEaxJce;
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
/*     */ public final class AesEaxKeyManager
/*     */ {
/*     */   private static final void validate(AesEaxParameters parameters) throws GeneralSecurityException {
/*  50 */     if (parameters.getKeySizeBytes() == 24) {
/*  51 */       throw new GeneralSecurityException("192 bit AES GCM Parameters are not valid");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*  56 */   private static final PrimitiveConstructor<AesEaxKey, Aead> AES_EAX_PRIMITIVE_CONSTRUCTOR = PrimitiveConstructor.create(AesEaxJce::create, AesEaxKey.class, Aead.class);
/*     */   
/*     */   static String getKeyType() {
/*  59 */     return "type.googleapis.com/google.crypto.tink.AesEaxKey";
/*     */   }
/*     */ 
/*     */   
/*  63 */   private static final KeyManager<Aead> legacyKeyManager = LegacyKeyManagerImpl.create(
/*  64 */       getKeyType(), Aead.class, KeyData.KeyMaterialType.SYMMETRIC, 
/*     */ 
/*     */       
/*  67 */       AesEaxKey.parser());
/*     */   
/*     */   private static Map<String, Parameters> namedParameters() throws GeneralSecurityException {
/*  70 */     Map<String, Parameters> result = new HashMap<>();
/*  71 */     result.put("AES128_EAX", PredefinedAeadParameters.AES128_EAX);
/*  72 */     result.put("AES128_EAX_RAW", 
/*     */         
/*  74 */         AesEaxParameters.builder()
/*  75 */         .setIvSizeBytes(16)
/*  76 */         .setKeySizeBytes(16)
/*  77 */         .setTagSizeBytes(16)
/*  78 */         .setVariant(AesEaxParameters.Variant.NO_PREFIX)
/*  79 */         .build());
/*  80 */     result.put("AES256_EAX", PredefinedAeadParameters.AES256_EAX);
/*  81 */     result.put("AES256_EAX_RAW", 
/*     */         
/*  83 */         AesEaxParameters.builder()
/*  84 */         .setIvSizeBytes(16)
/*  85 */         .setKeySizeBytes(32)
/*  86 */         .setTagSizeBytes(16)
/*  87 */         .setVariant(AesEaxParameters.Variant.NO_PREFIX)
/*  88 */         .build());
/*     */     
/*  90 */     return Collections.unmodifiableMap(result);
/*     */   }
/*     */ 
/*     */   
/*  94 */   private static final KeyCreator<AesEaxParameters> KEY_CREATOR = AesEaxKeyManager::createAesEaxKey;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   private static AesEaxKey createAesEaxKey(AesEaxParameters parameters, @Nullable Integer idRequirement) throws GeneralSecurityException {
/* 101 */     validate(parameters);
/* 102 */     return AesEaxKey.builder()
/* 103 */       .setParameters(parameters)
/* 104 */       .setIdRequirement(idRequirement)
/* 105 */       .setKeyBytes(SecretBytes.randomBytes(parameters.getKeySizeBytes()))
/* 106 */       .build();
/*     */   }
/*     */   
/*     */   public static void register(boolean newKeyAllowed) throws GeneralSecurityException {
/* 110 */     if (!TinkFipsUtil.AlgorithmFipsCompatibility.ALGORITHM_NOT_FIPS.isCompatible()) {
/* 111 */       throw new GeneralSecurityException("Registering AES EAX is not supported in FIPS mode");
/*     */     }
/* 113 */     AesEaxProtoSerialization.register();
/* 114 */     MutablePrimitiveRegistry.globalInstance()
/* 115 */       .registerPrimitiveConstructor(AES_EAX_PRIMITIVE_CONSTRUCTOR);
/* 116 */     MutableParametersRegistry.globalInstance().putAll(namedParameters());
/* 117 */     MutableKeyCreationRegistry.globalInstance().add(KEY_CREATOR, AesEaxParameters.class);
/* 118 */     KeyManagerRegistry.globalInstance().registerKeyManager(legacyKeyManager, newKeyAllowed);
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
/*     */   public static final KeyTemplate aes128EaxTemplate() {
/* 131 */     return (KeyTemplate)TinkBugException.exceptionIsBug(() -> KeyTemplate.createFrom(AesEaxParameters.builder().setIvSizeBytes(16).setKeySizeBytes(16).setTagSizeBytes(16).setVariant(AesEaxParameters.Variant.TINK).build()));
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
/*     */   public static final KeyTemplate rawAes128EaxTemplate() {
/* 152 */     return (KeyTemplate)TinkBugException.exceptionIsBug(() -> KeyTemplate.createFrom(AesEaxParameters.builder().setIvSizeBytes(16).setKeySizeBytes(16).setTagSizeBytes(16).setVariant(AesEaxParameters.Variant.NO_PREFIX).build()));
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
/*     */   public static final KeyTemplate aes256EaxTemplate() {
/* 173 */     return (KeyTemplate)TinkBugException.exceptionIsBug(() -> KeyTemplate.createFrom(AesEaxParameters.builder().setIvSizeBytes(16).setKeySizeBytes(32).setTagSizeBytes(16).setVariant(AesEaxParameters.Variant.TINK).build()));
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
/*     */   public static final KeyTemplate rawAes256EaxTemplate() {
/* 194 */     return (KeyTemplate)TinkBugException.exceptionIsBug(() -> KeyTemplate.createFrom(AesEaxParameters.builder().setIvSizeBytes(16).setKeySizeBytes(32).setTagSizeBytes(16).setVariant(AesEaxParameters.Variant.NO_PREFIX).build()));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\aead\AesEaxKeyManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */