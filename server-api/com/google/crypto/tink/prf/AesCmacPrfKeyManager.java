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
/*     */ import com.google.crypto.tink.prf.internal.AesCmacPrfProtoSerialization;
/*     */ import com.google.crypto.tink.proto.AesCmacPrfKey;
/*     */ import com.google.crypto.tink.proto.KeyData;
/*     */ import com.google.crypto.tink.subtle.PrfAesCmac;
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
/*     */ public final class AesCmacPrfKeyManager
/*     */ {
/*     */   private static Prf createPrimitive(AesCmacPrfKey key) throws GeneralSecurityException {
/*  49 */     validate(key.getParameters());
/*  50 */     return PrfAesCmac.create(key);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  55 */   private static final PrimitiveConstructor<AesCmacPrfKey, Prf> PRF_PRIMITIVE_CONSTRUCTOR = PrimitiveConstructor.create(AesCmacPrfKeyManager::createPrimitive, AesCmacPrfKey.class, Prf.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void validate(AesCmacPrfParameters parameters) throws GeneralSecurityException {
/*  61 */     if (parameters.getKeySizeBytes() != 32) {
/*  62 */       throw new GeneralSecurityException("Key size must be 32 bytes");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*  67 */   private static final KeyManager<Prf> legacyKeyManager = LegacyKeyManagerImpl.create(
/*  68 */       getKeyType(), Prf.class, KeyData.KeyMaterialType.SYMMETRIC, 
/*     */ 
/*     */       
/*  71 */       AesCmacPrfKey.parser());
/*     */ 
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   private static AesCmacPrfKey newKey(AesCmacPrfParameters parameters, @Nullable Integer idRequirement) throws GeneralSecurityException {
/*  77 */     if (idRequirement != null) {
/*  78 */       throw new GeneralSecurityException("Id Requirement is not supported for AES CMAC PRF keys");
/*     */     }
/*  80 */     validate(parameters);
/*  81 */     return AesCmacPrfKey.create(parameters, SecretBytes.randomBytes(parameters.getKeySizeBytes()));
/*     */   }
/*     */ 
/*     */   
/*  85 */   private static final KeyCreator<AesCmacPrfParameters> KEY_CREATOR = AesCmacPrfKeyManager::newKey;
/*     */ 
/*     */   
/*     */   static String getKeyType() {
/*  89 */     return "type.googleapis.com/google.crypto.tink.AesCmacPrfKey";
/*     */   }
/*     */   
/*     */   private static Map<String, Parameters> namedParameters() throws GeneralSecurityException {
/*  93 */     Map<String, Parameters> result = new HashMap<>();
/*  94 */     result.put("AES256_CMAC_PRF", PredefinedPrfParameters.AES_CMAC_PRF);
/*     */     
/*  96 */     result.put("AES_CMAC_PRF", PredefinedPrfParameters.AES_CMAC_PRF);
/*  97 */     return Collections.unmodifiableMap(result);
/*     */   }
/*     */   
/*     */   public static void register(boolean newKeyAllowed) throws GeneralSecurityException {
/* 101 */     if (!TinkFipsUtil.AlgorithmFipsCompatibility.ALGORITHM_NOT_FIPS.isCompatible()) {
/* 102 */       throw new GeneralSecurityException("Registering AES CMAC PRF is not supported in FIPS mode");
/*     */     }
/* 104 */     AesCmacPrfProtoSerialization.register();
/* 105 */     MutableKeyCreationRegistry.globalInstance().add(KEY_CREATOR, AesCmacPrfParameters.class);
/* 106 */     MutablePrimitiveRegistry.globalInstance()
/* 107 */       .registerPrimitiveConstructor(PRF_PRIMITIVE_CONSTRUCTOR);
/* 108 */     MutableParametersRegistry.globalInstance().putAll(namedParameters());
/* 109 */     KeyManagerRegistry.globalInstance().registerKeyManager(legacyKeyManager, newKeyAllowed);
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
/*     */   public static final KeyTemplate aes256CmacTemplate() {
/* 131 */     return (KeyTemplate)TinkBugException.exceptionIsBug(() -> KeyTemplate.createFrom(AesCmacPrfParameters.create(32)));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\prf\AesCmacPrfKeyManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */