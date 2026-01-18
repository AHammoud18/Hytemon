/*     */ package com.google.crypto.tink.prf;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.KeyManager;
/*     */ import com.google.crypto.tink.KeyTemplate;
/*     */ import com.google.crypto.tink.Parameters;
/*     */ import com.google.crypto.tink.SecretKeyAccess;
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
/*     */ import com.google.crypto.tink.prf.internal.HmacPrfProtoSerialization;
/*     */ import com.google.crypto.tink.proto.HmacPrfKey;
/*     */ import com.google.crypto.tink.proto.KeyData;
/*     */ import com.google.crypto.tink.subtle.PrfHmacJce;
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
/*     */ public final class HmacPrfKeyManager
/*     */ {
/*  55 */   private static final PrimitiveConstructor<HmacPrfKey, Prf> PRF_PRIMITIVE_CONSTRUCTOR = PrimitiveConstructor.create(PrfHmacJce::create, HmacPrfKey.class, Prf.class);
/*     */ 
/*     */ 
/*     */   
/*  59 */   private static final KeyManager<Prf> legacyKeyManager = LegacyKeyManagerImpl.create(
/*  60 */       getKeyType(), Prf.class, KeyData.KeyMaterialType.SYMMETRIC, 
/*     */ 
/*     */       
/*  63 */       HmacPrfKey.parser());
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   private static HmacPrfKey newKey(HmacPrfParameters parameters, @Nullable Integer idRequirement) throws GeneralSecurityException {
/*  68 */     if (idRequirement != null) {
/*  69 */       throw new GeneralSecurityException("Id Requirement is not supported for HMAC PRF keys");
/*     */     }
/*  71 */     return HmacPrfKey.builder()
/*  72 */       .setParameters(parameters)
/*  73 */       .setKeyBytes(SecretBytes.randomBytes(parameters.getKeySizeBytes()))
/*  74 */       .build();
/*     */   }
/*     */ 
/*     */   
/*  78 */   private static final KeyCreator<HmacPrfParameters> KEY_CREATOR = HmacPrfKeyManager::newKey;
/*     */ 
/*     */   
/*     */   static String getKeyType() {
/*  82 */     return "type.googleapis.com/google.crypto.tink.HmacPrfKey";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  87 */   private static final MutableKeyDerivationRegistry.InsecureKeyCreator<HmacPrfParameters> KEY_DERIVER = HmacPrfKeyManager::createHmacKeyFromRandomness;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   static HmacPrfKey createHmacKeyFromRandomness(HmacPrfParameters parameters, InputStream stream, @Nullable Integer idRequirement, SecretKeyAccess access) throws GeneralSecurityException {
/*  96 */     return HmacPrfKey.builder()
/*  97 */       .setParameters(parameters)
/*  98 */       .setKeyBytes(Util.readIntoSecretBytes(stream, parameters.getKeySizeBytes(), access))
/*  99 */       .build();
/*     */   }
/*     */   
/*     */   private static Map<String, Parameters> namedParameters() throws GeneralSecurityException {
/* 103 */     Map<String, Parameters> result = new HashMap<>();
/* 104 */     result.put("HMAC_SHA256_PRF", PredefinedPrfParameters.HMAC_SHA256_PRF);
/* 105 */     result.put("HMAC_SHA512_PRF", PredefinedPrfParameters.HMAC_SHA512_PRF);
/* 106 */     return Collections.unmodifiableMap(result);
/*     */   }
/*     */   
/* 109 */   private static final TinkFipsUtil.AlgorithmFipsCompatibility FIPS = TinkFipsUtil.AlgorithmFipsCompatibility.ALGORITHM_REQUIRES_BORINGCRYPTO;
/*     */ 
/*     */   
/*     */   public static void register(boolean newKeyAllowed) throws GeneralSecurityException {
/* 113 */     if (!FIPS.isCompatible()) {
/* 114 */       throw new GeneralSecurityException("Can not use HMAC in FIPS-mode, as BoringCrypto module is not available.");
/*     */     }
/*     */     
/* 117 */     HmacPrfProtoSerialization.register();
/* 118 */     MutablePrimitiveRegistry.globalInstance()
/* 119 */       .registerPrimitiveConstructor(PRF_PRIMITIVE_CONSTRUCTOR);
/* 120 */     MutableParametersRegistry.globalInstance().putAll(namedParameters());
/* 121 */     MutableKeyCreationRegistry.globalInstance().add(KEY_CREATOR, HmacPrfParameters.class);
/* 122 */     MutableKeyDerivationRegistry.globalInstance().add(KEY_DERIVER, HmacPrfParameters.class);
/* 123 */     KeyManagerRegistry.globalInstance()
/* 124 */       .registerKeyManagerWithFipsCompatibility(legacyKeyManager, FIPS, newKeyAllowed);
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
/*     */   public static final KeyTemplate hmacSha256Template() {
/* 138 */     return (KeyTemplate)TinkBugException.exceptionIsBug(() -> KeyTemplate.createFrom(HmacPrfParameters.builder().setKeySizeBytes(32).setHashType(HmacPrfParameters.HashType.SHA256).build()));
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
/*     */   public static final KeyTemplate hmacSha512Template() {
/* 158 */     return (KeyTemplate)TinkBugException.exceptionIsBug(() -> KeyTemplate.createFrom(HmacPrfParameters.builder().setKeySizeBytes(64).setHashType(HmacPrfParameters.HashType.SHA512).build()));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\prf\HmacPrfKeyManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */