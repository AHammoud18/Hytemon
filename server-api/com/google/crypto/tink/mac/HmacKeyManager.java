/*     */ package com.google.crypto.tink.mac;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.KeyManager;
/*     */ import com.google.crypto.tink.KeyTemplate;
/*     */ import com.google.crypto.tink.Mac;
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
/*     */ import com.google.crypto.tink.mac.internal.HmacProtoSerialization;
/*     */ import com.google.crypto.tink.proto.HmacKey;
/*     */ import com.google.crypto.tink.proto.KeyData;
/*     */ import com.google.crypto.tink.subtle.PrfMac;
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
/*     */ public final class HmacKeyManager
/*     */ {
/*  55 */   private static final PrimitiveConstructor<HmacKey, ChunkedMac> CHUNKED_MAC_PRIMITIVE_CONSTRUCTOR = PrimitiveConstructor.create(com.google.crypto.tink.mac.internal.ChunkedHmacImpl::new, HmacKey.class, ChunkedMac.class);
/*     */   
/*  57 */   private static final PrimitiveConstructor<HmacKey, Mac> MAC_PRIMITIVE_CONSTRUCTOR = PrimitiveConstructor.create(PrfMac::create, HmacKey.class, Mac.class);
/*     */ 
/*     */   
/*  60 */   private static final KeyManager<Mac> legacyKeyManager = LegacyKeyManagerImpl.create("type.googleapis.com/google.crypto.tink.HmacKey", Mac.class, KeyData.KeyMaterialType.SYMMETRIC, 
/*     */ 
/*     */ 
/*     */       
/*  64 */       HmacKey.parser());
/*     */   
/*     */   static String getKeyType() {
/*  67 */     return "type.googleapis.com/google.crypto.tink.HmacKey";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  72 */   private static final MutableKeyDerivationRegistry.InsecureKeyCreator<HmacParameters> KEY_DERIVER = HmacKeyManager::createHmacKeyFromRandomness;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   static HmacKey createHmacKeyFromRandomness(HmacParameters parameters, InputStream stream, @Nullable Integer idRequirement, SecretKeyAccess access) throws GeneralSecurityException {
/*  82 */     return HmacKey.builder()
/*  83 */       .setParameters(parameters)
/*  84 */       .setKeyBytes(Util.readIntoSecretBytes(stream, parameters.getKeySizeBytes(), access))
/*  85 */       .setIdRequirement(idRequirement)
/*  86 */       .build();
/*     */   }
/*     */ 
/*     */   
/*  90 */   private static final KeyCreator<HmacParameters> KEY_CREATOR = HmacKeyManager::createNewHmacKey;
/*     */ 
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   static HmacKey createNewHmacKey(HmacParameters parameters, @Nullable Integer idRequirement) throws GeneralSecurityException {
/*  96 */     return HmacKey.builder()
/*  97 */       .setParameters(parameters)
/*  98 */       .setKeyBytes(SecretBytes.randomBytes(parameters.getKeySizeBytes()))
/*  99 */       .setIdRequirement(idRequirement)
/* 100 */       .build();
/*     */   }
/*     */   
/*     */   private static Map<String, Parameters> namedParameters() throws GeneralSecurityException {
/* 104 */     Map<String, Parameters> result = new HashMap<>();
/* 105 */     result.put("HMAC_SHA256_128BITTAG", PredefinedMacParameters.HMAC_SHA256_128BITTAG);
/* 106 */     result.put("HMAC_SHA256_128BITTAG_RAW", 
/*     */         
/* 108 */         HmacParameters.builder()
/* 109 */         .setKeySizeBytes(32)
/* 110 */         .setTagSizeBytes(16)
/* 111 */         .setVariant(HmacParameters.Variant.NO_PREFIX)
/* 112 */         .setHashType(HmacParameters.HashType.SHA256)
/* 113 */         .build());
/* 114 */     result.put("HMAC_SHA256_256BITTAG", 
/*     */         
/* 116 */         HmacParameters.builder()
/* 117 */         .setKeySizeBytes(32)
/* 118 */         .setTagSizeBytes(32)
/* 119 */         .setVariant(HmacParameters.Variant.TINK)
/* 120 */         .setHashType(HmacParameters.HashType.SHA256)
/* 121 */         .build());
/* 122 */     result.put("HMAC_SHA256_256BITTAG_RAW", 
/*     */         
/* 124 */         HmacParameters.builder()
/* 125 */         .setKeySizeBytes(32)
/* 126 */         .setTagSizeBytes(32)
/* 127 */         .setVariant(HmacParameters.Variant.NO_PREFIX)
/* 128 */         .setHashType(HmacParameters.HashType.SHA256)
/* 129 */         .build());
/* 130 */     result.put("HMAC_SHA512_128BITTAG", 
/*     */         
/* 132 */         HmacParameters.builder()
/* 133 */         .setKeySizeBytes(64)
/* 134 */         .setTagSizeBytes(16)
/* 135 */         .setVariant(HmacParameters.Variant.TINK)
/* 136 */         .setHashType(HmacParameters.HashType.SHA512)
/* 137 */         .build());
/* 138 */     result.put("HMAC_SHA512_128BITTAG_RAW", 
/*     */         
/* 140 */         HmacParameters.builder()
/* 141 */         .setKeySizeBytes(64)
/* 142 */         .setTagSizeBytes(16)
/* 143 */         .setVariant(HmacParameters.Variant.NO_PREFIX)
/* 144 */         .setHashType(HmacParameters.HashType.SHA512)
/* 145 */         .build());
/* 146 */     result.put("HMAC_SHA512_256BITTAG", 
/*     */         
/* 148 */         HmacParameters.builder()
/* 149 */         .setKeySizeBytes(64)
/* 150 */         .setTagSizeBytes(32)
/* 151 */         .setVariant(HmacParameters.Variant.TINK)
/* 152 */         .setHashType(HmacParameters.HashType.SHA512)
/* 153 */         .build());
/* 154 */     result.put("HMAC_SHA512_256BITTAG_RAW", 
/*     */         
/* 156 */         HmacParameters.builder()
/* 157 */         .setKeySizeBytes(64)
/* 158 */         .setTagSizeBytes(32)
/* 159 */         .setVariant(HmacParameters.Variant.NO_PREFIX)
/* 160 */         .setHashType(HmacParameters.HashType.SHA512)
/* 161 */         .build());
/* 162 */     result.put("HMAC_SHA512_512BITTAG", PredefinedMacParameters.HMAC_SHA512_512BITTAG);
/* 163 */     result.put("HMAC_SHA512_512BITTAG_RAW", 
/*     */         
/* 165 */         HmacParameters.builder()
/* 166 */         .setKeySizeBytes(64)
/* 167 */         .setTagSizeBytes(64)
/* 168 */         .setVariant(HmacParameters.Variant.NO_PREFIX)
/* 169 */         .setHashType(HmacParameters.HashType.SHA512)
/* 170 */         .build());
/* 171 */     return Collections.unmodifiableMap(result);
/*     */   }
/*     */   
/* 174 */   private static final TinkFipsUtil.AlgorithmFipsCompatibility FIPS = TinkFipsUtil.AlgorithmFipsCompatibility.ALGORITHM_REQUIRES_BORINGCRYPTO;
/*     */ 
/*     */   
/*     */   public static void register(boolean newKeyAllowed) throws GeneralSecurityException {
/* 178 */     if (!FIPS.isCompatible()) {
/* 179 */       throw new GeneralSecurityException("Can not use HMAC in FIPS-mode, as BoringCrypto module is not available.");
/*     */     }
/*     */     
/* 182 */     HmacProtoSerialization.register();
/* 183 */     MutablePrimitiveRegistry.globalInstance()
/* 184 */       .registerPrimitiveConstructor(CHUNKED_MAC_PRIMITIVE_CONSTRUCTOR);
/* 185 */     MutablePrimitiveRegistry.globalInstance()
/* 186 */       .registerPrimitiveConstructor(MAC_PRIMITIVE_CONSTRUCTOR);
/* 187 */     MutableParametersRegistry.globalInstance().putAll(namedParameters());
/* 188 */     MutableKeyCreationRegistry.globalInstance().add(KEY_CREATOR, HmacParameters.class);
/* 189 */     MutableKeyDerivationRegistry.globalInstance().add(KEY_DERIVER, HmacParameters.class);
/* 190 */     KeyManagerRegistry.globalInstance()
/* 191 */       .registerKeyManagerWithFipsCompatibility(legacyKeyManager, FIPS, newKeyAllowed);
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
/*     */   public static final KeyTemplate hmacSha256HalfDigestTemplate() {
/* 205 */     return (KeyTemplate)TinkBugException.exceptionIsBug(() -> KeyTemplate.createFrom(HmacParameters.builder().setKeySizeBytes(32).setTagSizeBytes(16).setHashType(HmacParameters.HashType.SHA256).setVariant(HmacParameters.Variant.TINK).build()));
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
/*     */   public static final KeyTemplate hmacSha256Template() {
/* 227 */     return (KeyTemplate)TinkBugException.exceptionIsBug(() -> KeyTemplate.createFrom(HmacParameters.builder().setKeySizeBytes(32).setTagSizeBytes(32).setHashType(HmacParameters.HashType.SHA256).setVariant(HmacParameters.Variant.TINK).build()));
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
/*     */   public static final KeyTemplate hmacSha512HalfDigestTemplate() {
/* 249 */     return (KeyTemplate)TinkBugException.exceptionIsBug(() -> KeyTemplate.createFrom(HmacParameters.builder().setKeySizeBytes(64).setTagSizeBytes(32).setHashType(HmacParameters.HashType.SHA512).setVariant(HmacParameters.Variant.TINK).build()));
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
/*     */   public static final KeyTemplate hmacSha512Template() {
/* 271 */     return (KeyTemplate)TinkBugException.exceptionIsBug(() -> KeyTemplate.createFrom(HmacParameters.builder().setKeySizeBytes(64).setTagSizeBytes(64).setHashType(HmacParameters.HashType.SHA512).setVariant(HmacParameters.Variant.TINK).build()));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\mac\HmacKeyManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */