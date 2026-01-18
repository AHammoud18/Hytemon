/*     */ package com.google.crypto.tink.hybrid;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.HybridDecrypt;
/*     */ import com.google.crypto.tink.HybridEncrypt;
/*     */ import com.google.crypto.tink.InsecureSecretKeyAccess;
/*     */ import com.google.crypto.tink.KeyManager;
/*     */ import com.google.crypto.tink.KeyTemplate;
/*     */ import com.google.crypto.tink.Parameters;
/*     */ import com.google.crypto.tink.PrivateKeyManager;
/*     */ import com.google.crypto.tink.aead.AesCtrHmacAeadParameters;
/*     */ import com.google.crypto.tink.aead.AesGcmParameters;
/*     */ import com.google.crypto.tink.config.internal.TinkFipsUtil;
/*     */ import com.google.crypto.tink.hybrid.internal.EciesProtoSerialization;
/*     */ import com.google.crypto.tink.internal.EllipticCurvesUtil;
/*     */ import com.google.crypto.tink.internal.KeyCreator;
/*     */ import com.google.crypto.tink.internal.KeyManagerRegistry;
/*     */ import com.google.crypto.tink.internal.LegacyKeyManagerImpl;
/*     */ import com.google.crypto.tink.internal.MutableKeyCreationRegistry;
/*     */ import com.google.crypto.tink.internal.MutableParametersRegistry;
/*     */ import com.google.crypto.tink.internal.MutablePrimitiveRegistry;
/*     */ import com.google.crypto.tink.internal.PrimitiveConstructor;
/*     */ import com.google.crypto.tink.internal.TinkBugException;
/*     */ import com.google.crypto.tink.proto.EciesAeadHkdfPrivateKey;
/*     */ import com.google.crypto.tink.proto.EciesAeadHkdfPublicKey;
/*     */ import com.google.crypto.tink.proto.KeyData;
/*     */ import com.google.crypto.tink.subtle.EciesAeadHkdfHybridDecrypt;
/*     */ import com.google.crypto.tink.subtle.EciesAeadHkdfHybridEncrypt;
/*     */ import com.google.crypto.tink.subtle.EllipticCurves;
/*     */ import com.google.crypto.tink.util.SecretBigInteger;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.KeyPair;
/*     */ import java.security.interfaces.ECPrivateKey;
/*     */ import java.security.interfaces.ECPublicKey;
/*     */ import java.security.spec.ECParameterSpec;
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
/*     */ public final class EciesAeadHkdfPrivateKeyManager
/*     */ {
/*  63 */   private static final PrimitiveConstructor<EciesPrivateKey, HybridDecrypt> HYBRID_DECRYPT_PRIMITIVE_CONSTRUCTOR = PrimitiveConstructor.create(EciesAeadHkdfHybridDecrypt::create, EciesPrivateKey.class, HybridDecrypt.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  68 */   private static final PrimitiveConstructor<EciesPublicKey, HybridEncrypt> HYBRID_ENCRYPT_PRIMITIVE_CONSTRUCTOR = PrimitiveConstructor.create(EciesAeadHkdfHybridEncrypt::create, EciesPublicKey.class, HybridEncrypt.class);
/*     */ 
/*     */ 
/*     */   
/*  72 */   private static final PrivateKeyManager<HybridDecrypt> legacyPrivateKeyManager = LegacyKeyManagerImpl.createPrivateKeyManager(
/*  73 */       getKeyType(), HybridDecrypt.class, 
/*     */       
/*  75 */       EciesAeadHkdfPrivateKey.parser());
/*     */ 
/*     */   
/*  78 */   private static final KeyManager<HybridEncrypt> legacyPublicKeyManager = LegacyKeyManagerImpl.create(
/*  79 */       EciesAeadHkdfPublicKeyManager.getKeyType(), HybridEncrypt.class, KeyData.KeyMaterialType.ASYMMETRIC_PUBLIC, 
/*     */ 
/*     */       
/*  82 */       EciesAeadHkdfPublicKey.parser());
/*     */ 
/*     */   
/*     */   private static final ECParameterSpec toParameterSpec(EciesParameters.CurveType curveType) throws GeneralSecurityException {
/*  86 */     if (curveType == EciesParameters.CurveType.NIST_P256) {
/*  87 */       return EllipticCurvesUtil.NIST_P256_PARAMS;
/*     */     }
/*  89 */     if (curveType == EciesParameters.CurveType.NIST_P384) {
/*  90 */       return EllipticCurvesUtil.NIST_P384_PARAMS;
/*     */     }
/*  92 */     if (curveType == EciesParameters.CurveType.NIST_P521) {
/*  93 */       return EllipticCurvesUtil.NIST_P521_PARAMS;
/*     */     }
/*  95 */     throw new GeneralSecurityException("Unsupported curve type: " + curveType);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   private static EciesPrivateKey createKey(EciesParameters parameters, @Nullable Integer idRequirement) throws GeneralSecurityException {
/* 102 */     KeyPair keyPair = EllipticCurves.generateKeyPair(toParameterSpec(parameters.getCurveType()));
/* 103 */     ECPublicKey ecPubKey = (ECPublicKey)keyPair.getPublic();
/* 104 */     ECPrivateKey ecPrivKey = (ECPrivateKey)keyPair.getPrivate();
/*     */ 
/*     */     
/* 107 */     EciesPublicKey publicKey = EciesPublicKey.createForNistCurve(parameters, ecPubKey.getW(), idRequirement);
/* 108 */     return EciesPrivateKey.createForNistCurve(publicKey, 
/*     */         
/* 110 */         SecretBigInteger.fromBigInteger(ecPrivKey.getS(), InsecureSecretKeyAccess.get()));
/*     */   }
/*     */ 
/*     */   
/* 114 */   private static final KeyCreator<EciesParameters> KEY_CREATOR = EciesAeadHkdfPrivateKeyManager::createKey;
/*     */ 
/*     */   
/*     */   static String getKeyType() {
/* 118 */     return "type.googleapis.com/google.crypto.tink.EciesAeadHkdfPrivateKey";
/*     */   }
/*     */   
/*     */   private static Map<String, Parameters> namedParameters() throws GeneralSecurityException {
/* 122 */     Map<String, Parameters> result = new HashMap<>();
/* 123 */     result.put("ECIES_P256_HKDF_HMAC_SHA256_AES128_GCM", 
/*     */         
/* 125 */         EciesParameters.builder()
/* 126 */         .setCurveType(EciesParameters.CurveType.NIST_P256)
/* 127 */         .setHashType(EciesParameters.HashType.SHA256)
/* 128 */         .setNistCurvePointFormat(EciesParameters.PointFormat.UNCOMPRESSED)
/* 129 */         .setVariant(EciesParameters.Variant.TINK)
/* 130 */         .setDemParameters(
/* 131 */           (Parameters)AesGcmParameters.builder()
/* 132 */           .setIvSizeBytes(12)
/* 133 */           .setKeySizeBytes(16)
/* 134 */           .setTagSizeBytes(16)
/* 135 */           .setVariant(AesGcmParameters.Variant.NO_PREFIX)
/* 136 */           .build())
/* 137 */         .build());
/* 138 */     result.put("ECIES_P256_HKDF_HMAC_SHA256_AES128_GCM_RAW", 
/*     */         
/* 140 */         EciesParameters.builder()
/* 141 */         .setCurveType(EciesParameters.CurveType.NIST_P256)
/* 142 */         .setHashType(EciesParameters.HashType.SHA256)
/* 143 */         .setNistCurvePointFormat(EciesParameters.PointFormat.UNCOMPRESSED)
/* 144 */         .setVariant(EciesParameters.Variant.NO_PREFIX)
/* 145 */         .setDemParameters(
/* 146 */           (Parameters)AesGcmParameters.builder()
/* 147 */           .setIvSizeBytes(12)
/* 148 */           .setKeySizeBytes(16)
/* 149 */           .setTagSizeBytes(16)
/* 150 */           .setVariant(AesGcmParameters.Variant.NO_PREFIX)
/* 151 */           .build())
/* 152 */         .build());
/* 153 */     result.put("ECIES_P256_COMPRESSED_HKDF_HMAC_SHA256_AES128_GCM", 
/*     */         
/* 155 */         EciesParameters.builder()
/* 156 */         .setCurveType(EciesParameters.CurveType.NIST_P256)
/* 157 */         .setHashType(EciesParameters.HashType.SHA256)
/* 158 */         .setNistCurvePointFormat(EciesParameters.PointFormat.COMPRESSED)
/* 159 */         .setVariant(EciesParameters.Variant.TINK)
/* 160 */         .setDemParameters(
/* 161 */           (Parameters)AesGcmParameters.builder()
/* 162 */           .setIvSizeBytes(12)
/* 163 */           .setKeySizeBytes(16)
/* 164 */           .setTagSizeBytes(16)
/* 165 */           .setVariant(AesGcmParameters.Variant.NO_PREFIX)
/* 166 */           .build())
/* 167 */         .build());
/* 168 */     result.put("ECIES_P256_COMPRESSED_HKDF_HMAC_SHA256_AES128_GCM_RAW", 
/*     */         
/* 170 */         EciesParameters.builder()
/* 171 */         .setCurveType(EciesParameters.CurveType.NIST_P256)
/* 172 */         .setHashType(EciesParameters.HashType.SHA256)
/* 173 */         .setNistCurvePointFormat(EciesParameters.PointFormat.COMPRESSED)
/* 174 */         .setVariant(EciesParameters.Variant.NO_PREFIX)
/* 175 */         .setDemParameters(
/* 176 */           (Parameters)AesGcmParameters.builder()
/* 177 */           .setIvSizeBytes(12)
/* 178 */           .setKeySizeBytes(16)
/* 179 */           .setTagSizeBytes(16)
/* 180 */           .setVariant(AesGcmParameters.Variant.NO_PREFIX)
/* 181 */           .build())
/* 182 */         .build());
/*     */     
/* 184 */     result.put("ECIES_P256_HKDF_HMAC_SHA256_AES128_GCM_COMPRESSED_WITHOUT_PREFIX", 
/*     */         
/* 186 */         EciesParameters.builder()
/* 187 */         .setCurveType(EciesParameters.CurveType.NIST_P256)
/* 188 */         .setHashType(EciesParameters.HashType.SHA256)
/* 189 */         .setNistCurvePointFormat(EciesParameters.PointFormat.COMPRESSED)
/* 190 */         .setVariant(EciesParameters.Variant.NO_PREFIX)
/* 191 */         .setDemParameters(
/* 192 */           (Parameters)AesGcmParameters.builder()
/* 193 */           .setIvSizeBytes(12)
/* 194 */           .setKeySizeBytes(16)
/* 195 */           .setTagSizeBytes(16)
/* 196 */           .setVariant(AesGcmParameters.Variant.NO_PREFIX)
/* 197 */           .build())
/* 198 */         .build());
/* 199 */     result.put("ECIES_P256_HKDF_HMAC_SHA256_AES128_CTR_HMAC_SHA256", 
/*     */         
/* 201 */         EciesParameters.builder()
/* 202 */         .setCurveType(EciesParameters.CurveType.NIST_P256)
/* 203 */         .setHashType(EciesParameters.HashType.SHA256)
/* 204 */         .setNistCurvePointFormat(EciesParameters.PointFormat.UNCOMPRESSED)
/* 205 */         .setVariant(EciesParameters.Variant.TINK)
/* 206 */         .setDemParameters(
/* 207 */           (Parameters)AesCtrHmacAeadParameters.builder()
/* 208 */           .setAesKeySizeBytes(16)
/* 209 */           .setHmacKeySizeBytes(32)
/* 210 */           .setTagSizeBytes(16)
/* 211 */           .setIvSizeBytes(16)
/* 212 */           .setHashType(AesCtrHmacAeadParameters.HashType.SHA256)
/* 213 */           .setVariant(AesCtrHmacAeadParameters.Variant.NO_PREFIX)
/* 214 */           .build())
/* 215 */         .build());
/* 216 */     result.put("ECIES_P256_HKDF_HMAC_SHA256_AES128_CTR_HMAC_SHA256_RAW", 
/*     */         
/* 218 */         EciesParameters.builder()
/* 219 */         .setCurveType(EciesParameters.CurveType.NIST_P256)
/* 220 */         .setHashType(EciesParameters.HashType.SHA256)
/* 221 */         .setNistCurvePointFormat(EciesParameters.PointFormat.UNCOMPRESSED)
/* 222 */         .setVariant(EciesParameters.Variant.NO_PREFIX)
/* 223 */         .setDemParameters(
/* 224 */           (Parameters)AesCtrHmacAeadParameters.builder()
/* 225 */           .setAesKeySizeBytes(16)
/* 226 */           .setHmacKeySizeBytes(32)
/* 227 */           .setTagSizeBytes(16)
/* 228 */           .setIvSizeBytes(16)
/* 229 */           .setHashType(AesCtrHmacAeadParameters.HashType.SHA256)
/* 230 */           .setVariant(AesCtrHmacAeadParameters.Variant.NO_PREFIX)
/* 231 */           .build())
/* 232 */         .build());
/* 233 */     result.put("ECIES_P256_COMPRESSED_HKDF_HMAC_SHA256_AES128_CTR_HMAC_SHA256", 
/*     */         
/* 235 */         EciesParameters.builder()
/* 236 */         .setCurveType(EciesParameters.CurveType.NIST_P256)
/* 237 */         .setHashType(EciesParameters.HashType.SHA256)
/* 238 */         .setNistCurvePointFormat(EciesParameters.PointFormat.COMPRESSED)
/* 239 */         .setVariant(EciesParameters.Variant.TINK)
/* 240 */         .setDemParameters(
/* 241 */           (Parameters)AesCtrHmacAeadParameters.builder()
/* 242 */           .setAesKeySizeBytes(16)
/* 243 */           .setHmacKeySizeBytes(32)
/* 244 */           .setTagSizeBytes(16)
/* 245 */           .setIvSizeBytes(16)
/* 246 */           .setHashType(AesCtrHmacAeadParameters.HashType.SHA256)
/* 247 */           .setVariant(AesCtrHmacAeadParameters.Variant.NO_PREFIX)
/* 248 */           .build())
/* 249 */         .build());
/* 250 */     result.put("ECIES_P256_COMPRESSED_HKDF_HMAC_SHA256_AES128_CTR_HMAC_SHA256_RAW", 
/*     */         
/* 252 */         EciesParameters.builder()
/* 253 */         .setCurveType(EciesParameters.CurveType.NIST_P256)
/* 254 */         .setHashType(EciesParameters.HashType.SHA256)
/* 255 */         .setNistCurvePointFormat(EciesParameters.PointFormat.COMPRESSED)
/* 256 */         .setVariant(EciesParameters.Variant.NO_PREFIX)
/* 257 */         .setDemParameters(
/* 258 */           (Parameters)AesCtrHmacAeadParameters.builder()
/* 259 */           .setAesKeySizeBytes(16)
/* 260 */           .setHmacKeySizeBytes(32)
/* 261 */           .setTagSizeBytes(16)
/* 262 */           .setIvSizeBytes(16)
/* 263 */           .setHashType(AesCtrHmacAeadParameters.HashType.SHA256)
/* 264 */           .setVariant(AesCtrHmacAeadParameters.Variant.NO_PREFIX)
/* 265 */           .build())
/* 266 */         .build());
/* 267 */     return Collections.unmodifiableMap(result);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void registerPair(boolean newKeyAllowed) throws GeneralSecurityException {
/* 276 */     if (!TinkFipsUtil.AlgorithmFipsCompatibility.ALGORITHM_NOT_FIPS.isCompatible()) {
/* 277 */       throw new GeneralSecurityException("Registering ECIES Hybrid Encryption is not supported in FIPS mode");
/*     */     }
/*     */     
/* 280 */     EciesProtoSerialization.register();
/* 281 */     MutableParametersRegistry.globalInstance().putAll(namedParameters());
/* 282 */     MutablePrimitiveRegistry.globalInstance()
/* 283 */       .registerPrimitiveConstructor(HYBRID_DECRYPT_PRIMITIVE_CONSTRUCTOR);
/* 284 */     MutablePrimitiveRegistry.globalInstance()
/* 285 */       .registerPrimitiveConstructor(HYBRID_ENCRYPT_PRIMITIVE_CONSTRUCTOR);
/* 286 */     MutableKeyCreationRegistry.globalInstance().add(KEY_CREATOR, EciesParameters.class);
/* 287 */     KeyManagerRegistry.globalInstance().registerKeyManager((KeyManager)legacyPrivateKeyManager, newKeyAllowed);
/* 288 */     KeyManagerRegistry.globalInstance().registerKeyManager(legacyPublicKeyManager, false);
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
/*     */   public static final KeyTemplate eciesP256HkdfHmacSha256Aes128GcmTemplate() {
/* 306 */     return (KeyTemplate)TinkBugException.exceptionIsBug(() -> KeyTemplate.createFrom(EciesParameters.builder().setCurveType(EciesParameters.CurveType.NIST_P256).setHashType(EciesParameters.HashType.SHA256).setNistCurvePointFormat(EciesParameters.PointFormat.UNCOMPRESSED).setVariant(EciesParameters.Variant.TINK).setDemParameters((Parameters)AesGcmParameters.builder().setIvSizeBytes(12).setKeySizeBytes(16).setTagSizeBytes(16).setVariant(AesGcmParameters.Variant.NO_PREFIX).build()).build()));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final KeyTemplate rawEciesP256HkdfHmacSha256Aes128GcmCompressedTemplate() {
/* 339 */     return (KeyTemplate)TinkBugException.exceptionIsBug(() -> KeyTemplate.createFrom(EciesParameters.builder().setCurveType(EciesParameters.CurveType.NIST_P256).setHashType(EciesParameters.HashType.SHA256).setNistCurvePointFormat(EciesParameters.PointFormat.COMPRESSED).setVariant(EciesParameters.Variant.NO_PREFIX).setDemParameters((Parameters)AesGcmParameters.builder().setIvSizeBytes(12).setKeySizeBytes(16).setTagSizeBytes(16).setVariant(AesGcmParameters.Variant.NO_PREFIX).build()).build()));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final KeyTemplate eciesP256HkdfHmacSha256Aes128CtrHmacSha256Template() {
/* 375 */     return (KeyTemplate)TinkBugException.exceptionIsBug(() -> KeyTemplate.createFrom(EciesParameters.builder().setCurveType(EciesParameters.CurveType.NIST_P256).setHashType(EciesParameters.HashType.SHA256).setNistCurvePointFormat(EciesParameters.PointFormat.UNCOMPRESSED).setVariant(EciesParameters.Variant.TINK).setDemParameters((Parameters)AesCtrHmacAeadParameters.builder().setAesKeySizeBytes(16).setHmacKeySizeBytes(32).setTagSizeBytes(16).setIvSizeBytes(16).setHashType(AesCtrHmacAeadParameters.HashType.SHA256).setVariant(AesCtrHmacAeadParameters.Variant.NO_PREFIX).build()).build()));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final KeyTemplate rawEciesP256HkdfHmacSha256Aes128CtrHmacSha256CompressedTemplate() {
/* 414 */     return (KeyTemplate)TinkBugException.exceptionIsBug(() -> KeyTemplate.createFrom(EciesParameters.builder().setCurveType(EciesParameters.CurveType.NIST_P256).setHashType(EciesParameters.HashType.SHA256).setNistCurvePointFormat(EciesParameters.PointFormat.COMPRESSED).setVariant(EciesParameters.Variant.NO_PREFIX).setDemParameters((Parameters)AesCtrHmacAeadParameters.builder().setAesKeySizeBytes(16).setHmacKeySizeBytes(32).setTagSizeBytes(16).setIvSizeBytes(16).setHashType(AesCtrHmacAeadParameters.HashType.SHA256).setVariant(AesCtrHmacAeadParameters.Variant.NO_PREFIX).build()).build()));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\hybrid\EciesAeadHkdfPrivateKeyManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */