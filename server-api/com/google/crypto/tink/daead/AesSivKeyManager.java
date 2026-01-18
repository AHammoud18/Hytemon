/*     */ package com.google.crypto.tink.daead;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.DeterministicAead;
/*     */ import com.google.crypto.tink.KeyManager;
/*     */ import com.google.crypto.tink.KeyTemplate;
/*     */ import com.google.crypto.tink.Parameters;
/*     */ import com.google.crypto.tink.SecretKeyAccess;
/*     */ import com.google.crypto.tink.config.internal.TinkFipsUtil;
/*     */ import com.google.crypto.tink.daead.internal.AesSivProtoSerialization;
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
/*     */ import com.google.crypto.tink.proto.AesSivKey;
/*     */ import com.google.crypto.tink.proto.KeyData;
/*     */ import com.google.crypto.tink.subtle.AesSiv;
/*     */ import com.google.crypto.tink.util.SecretBytes;
/*     */ import java.io.InputStream;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.InvalidAlgorithmParameterException;
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
/*     */ public final class AesSivKeyManager
/*     */ {
/*     */   private static DeterministicAead createDeterministicAead(AesSivKey key) throws GeneralSecurityException {
/*  56 */     validateParameters(key.getParameters());
/*  57 */     return (DeterministicAead)AesSiv.create(key);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  62 */   private static final PrimitiveConstructor<AesSivKey, DeterministicAead> AES_SIV_PRIMITIVE_CONSTRUCTOR = PrimitiveConstructor.create(AesSivKeyManager::createDeterministicAead, AesSivKey.class, DeterministicAead.class);
/*     */   
/*     */   private static final int KEY_SIZE_IN_BYTES = 64;
/*     */ 
/*     */   
/*     */   static String getKeyType() {
/*  68 */     return "type.googleapis.com/google.crypto.tink.AesSivKey";
/*     */   }
/*     */ 
/*     */   
/*  72 */   private static final KeyManager<DeterministicAead> legacyKeyManager = LegacyKeyManagerImpl.create(
/*  73 */       getKeyType(), DeterministicAead.class, KeyData.KeyMaterialType.SYMMETRIC, 
/*     */ 
/*     */       
/*  76 */       AesSivKey.parser());
/*     */ 
/*     */   
/*     */   private static void validateParameters(AesSivParameters parameters) throws GeneralSecurityException {
/*  80 */     if (parameters.getKeySizeBytes() != 64) {
/*  81 */       throw new InvalidAlgorithmParameterException("invalid key size: " + parameters
/*     */           
/*  83 */           .getKeySizeBytes() + ". Valid keys must have " + '@' + " bytes.");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  92 */   private static final MutableKeyDerivationRegistry.InsecureKeyCreator<AesSivParameters> KEY_DERIVER = AesSivKeyManager::createAesSivKeyFromRandomness;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   static AesSivKey createAesSivKeyFromRandomness(AesSivParameters parameters, InputStream stream, @Nullable Integer idRequirement, SecretKeyAccess access) throws GeneralSecurityException {
/* 101 */     validateParameters(parameters);
/* 102 */     return AesSivKey.builder()
/* 103 */       .setParameters(parameters)
/* 104 */       .setIdRequirement(idRequirement)
/* 105 */       .setKeyBytes(Util.readIntoSecretBytes(stream, parameters.getKeySizeBytes(), access))
/* 106 */       .build();
/*     */   }
/*     */ 
/*     */   
/* 110 */   private static final KeyCreator<AesSivParameters> KEY_CREATOR = AesSivKeyManager::newKey;
/*     */ 
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   static AesSivKey newKey(AesSivParameters parameters, @Nullable Integer idRequirement) throws GeneralSecurityException {
/* 116 */     validateParameters(parameters);
/* 117 */     return AesSivKey.builder()
/* 118 */       .setParameters(parameters)
/* 119 */       .setIdRequirement(idRequirement)
/* 120 */       .setKeyBytes(SecretBytes.randomBytes(parameters.getKeySizeBytes()))
/* 121 */       .build();
/*     */   }
/*     */   
/*     */   private static Map<String, Parameters> namedParameters() throws GeneralSecurityException {
/* 125 */     Map<String, Parameters> result = new HashMap<>();
/* 126 */     result.put("AES256_SIV", PredefinedDeterministicAeadParameters.AES256_SIV);
/* 127 */     result.put("AES256_SIV_RAW", 
/*     */         
/* 129 */         AesSivParameters.builder()
/* 130 */         .setKeySizeBytes(64)
/* 131 */         .setVariant(AesSivParameters.Variant.NO_PREFIX)
/* 132 */         .build());
/* 133 */     return Collections.unmodifiableMap(result);
/*     */   }
/*     */   
/*     */   public static void register(boolean newKeyAllowed) throws GeneralSecurityException {
/* 137 */     if (!TinkFipsUtil.AlgorithmFipsCompatibility.ALGORITHM_NOT_FIPS.isCompatible()) {
/* 138 */       throw new GeneralSecurityException("Registering AES SIV is not supported in FIPS mode");
/*     */     }
/* 140 */     AesSivProtoSerialization.register();
/* 141 */     MutablePrimitiveRegistry.globalInstance()
/* 142 */       .registerPrimitiveConstructor(AES_SIV_PRIMITIVE_CONSTRUCTOR);
/* 143 */     MutableParametersRegistry.globalInstance().putAll(namedParameters());
/* 144 */     MutableKeyDerivationRegistry.globalInstance().add(KEY_DERIVER, AesSivParameters.class);
/* 145 */     MutableKeyCreationRegistry.globalInstance().add(KEY_CREATOR, AesSivParameters.class);
/* 146 */     KeyManagerRegistry.globalInstance().registerKeyManager(legacyKeyManager, newKeyAllowed);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final KeyTemplate aes256SivTemplate() {
/* 153 */     return (KeyTemplate)TinkBugException.exceptionIsBug(() -> KeyTemplate.createFrom(AesSivParameters.builder().setKeySizeBytes(64).setVariant(AesSivParameters.Variant.TINK).build()));
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
/*     */   public static final KeyTemplate rawAes256SivTemplate() {
/* 167 */     return (KeyTemplate)TinkBugException.exceptionIsBug(() -> KeyTemplate.createFrom(AesSivParameters.builder().setKeySizeBytes(64).setVariant(AesSivParameters.Variant.NO_PREFIX).build()));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\daead\AesSivKeyManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */