/*     */ package com.google.crypto.tink.hybrid.internal;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.HybridDecrypt;
/*     */ import com.google.crypto.tink.HybridEncrypt;
/*     */ import com.google.crypto.tink.InsecureSecretKeyAccess;
/*     */ import com.google.crypto.tink.KeyManager;
/*     */ import com.google.crypto.tink.Parameters;
/*     */ import com.google.crypto.tink.PrivateKeyManager;
/*     */ import com.google.crypto.tink.config.internal.TinkFipsUtil;
/*     */ import com.google.crypto.tink.hybrid.HpkeParameters;
/*     */ import com.google.crypto.tink.hybrid.HpkePrivateKey;
/*     */ import com.google.crypto.tink.hybrid.HpkeProtoSerialization;
/*     */ import com.google.crypto.tink.hybrid.HpkePublicKey;
/*     */ import com.google.crypto.tink.internal.BigIntegerEncoding;
/*     */ import com.google.crypto.tink.internal.KeyCreator;
/*     */ import com.google.crypto.tink.internal.KeyManagerRegistry;
/*     */ import com.google.crypto.tink.internal.LegacyKeyManagerImpl;
/*     */ import com.google.crypto.tink.internal.MutableKeyCreationRegistry;
/*     */ import com.google.crypto.tink.internal.MutableParametersRegistry;
/*     */ import com.google.crypto.tink.internal.MutablePrimitiveRegistry;
/*     */ import com.google.crypto.tink.internal.PrimitiveConstructor;
/*     */ import com.google.crypto.tink.proto.HpkePrivateKey;
/*     */ import com.google.crypto.tink.proto.HpkePublicKey;
/*     */ import com.google.crypto.tink.proto.KeyData;
/*     */ import com.google.crypto.tink.subtle.EllipticCurves;
/*     */ import com.google.crypto.tink.subtle.X25519;
/*     */ import com.google.crypto.tink.util.Bytes;
/*     */ import com.google.crypto.tink.util.SecretBytes;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.KeyPair;
/*     */ import java.security.interfaces.ECPrivateKey;
/*     */ import java.security.interfaces.ECPublicKey;
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
/*     */ public final class HpkePrivateKeyManager
/*     */ {
/*  60 */   private static final PrimitiveConstructor<HpkePrivateKey, HybridDecrypt> HYBRID_DECRYPT_PRIMITIVE_CONSTRUCTOR = PrimitiveConstructor.create(HpkeDecrypt::create, HpkePrivateKey.class, HybridDecrypt.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  65 */   private static final PrimitiveConstructor<HpkePublicKey, HybridEncrypt> HYBRID_ENCRYPT_PRIMITIVE_CONSTRUCTOR = PrimitiveConstructor.create(HpkeEncrypt::create, HpkePublicKey.class, HybridEncrypt.class);
/*     */ 
/*     */ 
/*     */   
/*  69 */   private static final PrivateKeyManager<HybridDecrypt> legacyPrivateKeyManager = LegacyKeyManagerImpl.createPrivateKeyManager(
/*  70 */       getKeyType(), HybridDecrypt.class, HpkePrivateKey.parser());
/*     */ 
/*     */   
/*  73 */   private static final KeyManager<HybridEncrypt> legacyPublicKeyManager = LegacyKeyManagerImpl.create(
/*  74 */       HpkePublicKeyManager.getKeyType(), HybridEncrypt.class, KeyData.KeyMaterialType.ASYMMETRIC_PUBLIC, 
/*     */ 
/*     */       
/*  77 */       HpkePublicKey.parser());
/*     */ 
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   private static HpkePrivateKey createKey(HpkeParameters parameters, @Nullable Integer idRequirement) throws GeneralSecurityException {
/*     */     SecretBytes privateKeyBytes;
/*     */     Bytes publicKeyBytes;
/*  85 */     if (parameters.getKemId().equals(HpkeParameters.KemId.DHKEM_X25519_HKDF_SHA256)) {
/*  86 */       byte[] privateKeyByteArray = X25519.generatePrivateKey();
/*  87 */       privateKeyBytes = SecretBytes.copyFrom(privateKeyByteArray, InsecureSecretKeyAccess.get());
/*  88 */       publicKeyBytes = Bytes.copyFrom(X25519.publicFromPrivate(privateKeyByteArray));
/*  89 */     } else if (parameters.getKemId().equals(HpkeParameters.KemId.DHKEM_P256_HKDF_SHA256) || parameters
/*  90 */       .getKemId().equals(HpkeParameters.KemId.DHKEM_P384_HKDF_SHA384) || parameters
/*  91 */       .getKemId().equals(HpkeParameters.KemId.DHKEM_P521_HKDF_SHA512)) {
/*  92 */       EllipticCurves.CurveType curveType = HpkeUtil.nistHpkeKemToCurve(parameters.getKemId());
/*  93 */       KeyPair keyPair = EllipticCurves.generateKeyPair(curveType);
/*     */       
/*  95 */       publicKeyBytes = Bytes.copyFrom(
/*  96 */           EllipticCurves.pointEncode(curveType, EllipticCurves.PointFormatType.UNCOMPRESSED, ((ECPublicKey)keyPair
/*     */ 
/*     */             
/*  99 */             .getPublic()).getW()));
/*     */       
/* 101 */       privateKeyBytes = SecretBytes.copyFrom(
/* 102 */           BigIntegerEncoding.toBigEndianBytesOfFixedLength(((ECPrivateKey)keyPair
/* 103 */             .getPrivate()).getS(), 
/* 104 */             HpkeUtil.getEncodedPrivateKeyLength(parameters.getKemId())), 
/* 105 */           InsecureSecretKeyAccess.get());
/*     */     } else {
/* 107 */       throw new GeneralSecurityException("Unknown KEM ID");
/*     */     } 
/* 109 */     HpkePublicKey publicKey = HpkePublicKey.create(parameters, publicKeyBytes, idRequirement);
/* 110 */     return HpkePrivateKey.create(publicKey, privateKeyBytes);
/*     */   }
/*     */ 
/*     */   
/* 114 */   private static final KeyCreator<HpkeParameters> KEY_CREATOR = HpkePrivateKeyManager::createKey;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void registerPair(boolean newKeyAllowed) throws GeneralSecurityException {
/* 122 */     if (!TinkFipsUtil.AlgorithmFipsCompatibility.ALGORITHM_NOT_FIPS.isCompatible()) {
/* 123 */       throw new GeneralSecurityException("Registering HPKE Hybrid Encryption is not supported in FIPS mode");
/*     */     }
/*     */     
/* 126 */     HpkeProtoSerialization.register();
/* 127 */     MutableParametersRegistry.globalInstance().putAll(namedParameters());
/* 128 */     MutablePrimitiveRegistry.globalInstance()
/* 129 */       .registerPrimitiveConstructor(HYBRID_DECRYPT_PRIMITIVE_CONSTRUCTOR);
/* 130 */     MutablePrimitiveRegistry.globalInstance()
/* 131 */       .registerPrimitiveConstructor(HYBRID_ENCRYPT_PRIMITIVE_CONSTRUCTOR);
/* 132 */     MutableKeyCreationRegistry.globalInstance().add(KEY_CREATOR, HpkeParameters.class);
/* 133 */     KeyManagerRegistry.globalInstance().registerKeyManager((KeyManager)legacyPrivateKeyManager, newKeyAllowed);
/* 134 */     KeyManagerRegistry.globalInstance()
/* 135 */       .registerKeyManager(legacyPublicKeyManager, false);
/*     */   }
/*     */   
/*     */   static String getKeyType() {
/* 139 */     return "type.googleapis.com/google.crypto.tink.HpkePrivateKey";
/*     */   }
/*     */   
/*     */   private static Map<String, Parameters> namedParameters() throws GeneralSecurityException {
/* 143 */     Map<String, Parameters> result = new HashMap<>();
/* 144 */     result.put("DHKEM_X25519_HKDF_SHA256_HKDF_SHA256_AES_128_GCM", 
/*     */         
/* 146 */         HpkeParameters.builder()
/* 147 */         .setVariant(HpkeParameters.Variant.TINK)
/* 148 */         .setKemId(HpkeParameters.KemId.DHKEM_X25519_HKDF_SHA256)
/* 149 */         .setKdfId(HpkeParameters.KdfId.HKDF_SHA256)
/* 150 */         .setAeadId(HpkeParameters.AeadId.AES_128_GCM)
/* 151 */         .build());
/* 152 */     result.put("DHKEM_X25519_HKDF_SHA256_HKDF_SHA256_AES_128_GCM_RAW", 
/*     */         
/* 154 */         HpkeParameters.builder()
/* 155 */         .setVariant(HpkeParameters.Variant.NO_PREFIX)
/* 156 */         .setKemId(HpkeParameters.KemId.DHKEM_X25519_HKDF_SHA256)
/* 157 */         .setKdfId(HpkeParameters.KdfId.HKDF_SHA256)
/* 158 */         .setAeadId(HpkeParameters.AeadId.AES_128_GCM)
/* 159 */         .build());
/* 160 */     result.put("DHKEM_X25519_HKDF_SHA256_HKDF_SHA256_AES_256_GCM", 
/*     */         
/* 162 */         HpkeParameters.builder()
/* 163 */         .setVariant(HpkeParameters.Variant.TINK)
/* 164 */         .setKemId(HpkeParameters.KemId.DHKEM_X25519_HKDF_SHA256)
/* 165 */         .setKdfId(HpkeParameters.KdfId.HKDF_SHA256)
/* 166 */         .setAeadId(HpkeParameters.AeadId.AES_256_GCM)
/* 167 */         .build());
/* 168 */     result.put("DHKEM_X25519_HKDF_SHA256_HKDF_SHA256_AES_256_GCM_RAW", 
/*     */         
/* 170 */         HpkeParameters.builder()
/* 171 */         .setVariant(HpkeParameters.Variant.NO_PREFIX)
/* 172 */         .setKemId(HpkeParameters.KemId.DHKEM_X25519_HKDF_SHA256)
/* 173 */         .setKdfId(HpkeParameters.KdfId.HKDF_SHA256)
/* 174 */         .setAeadId(HpkeParameters.AeadId.AES_256_GCM)
/* 175 */         .build());
/* 176 */     result.put("DHKEM_X25519_HKDF_SHA256_HKDF_SHA256_CHACHA20_POLY1305", 
/*     */         
/* 178 */         HpkeParameters.builder()
/* 179 */         .setVariant(HpkeParameters.Variant.TINK)
/* 180 */         .setKemId(HpkeParameters.KemId.DHKEM_X25519_HKDF_SHA256)
/* 181 */         .setKdfId(HpkeParameters.KdfId.HKDF_SHA256)
/* 182 */         .setAeadId(HpkeParameters.AeadId.CHACHA20_POLY1305)
/* 183 */         .build());
/* 184 */     result.put("DHKEM_X25519_HKDF_SHA256_HKDF_SHA256_CHACHA20_POLY1305_RAW", 
/*     */         
/* 186 */         HpkeParameters.builder()
/* 187 */         .setVariant(HpkeParameters.Variant.NO_PREFIX)
/* 188 */         .setKemId(HpkeParameters.KemId.DHKEM_X25519_HKDF_SHA256)
/* 189 */         .setKdfId(HpkeParameters.KdfId.HKDF_SHA256)
/* 190 */         .setAeadId(HpkeParameters.AeadId.CHACHA20_POLY1305)
/* 191 */         .build());
/* 192 */     result.put("DHKEM_P256_HKDF_SHA256_HKDF_SHA256_AES_128_GCM", 
/*     */         
/* 194 */         HpkeParameters.builder()
/* 195 */         .setVariant(HpkeParameters.Variant.TINK)
/* 196 */         .setKemId(HpkeParameters.KemId.DHKEM_P256_HKDF_SHA256)
/* 197 */         .setKdfId(HpkeParameters.KdfId.HKDF_SHA256)
/* 198 */         .setAeadId(HpkeParameters.AeadId.AES_128_GCM)
/* 199 */         .build());
/* 200 */     result.put("DHKEM_P256_HKDF_SHA256_HKDF_SHA256_AES_128_GCM_RAW", 
/*     */         
/* 202 */         HpkeParameters.builder()
/* 203 */         .setVariant(HpkeParameters.Variant.NO_PREFIX)
/* 204 */         .setKemId(HpkeParameters.KemId.DHKEM_P256_HKDF_SHA256)
/* 205 */         .setKdfId(HpkeParameters.KdfId.HKDF_SHA256)
/* 206 */         .setAeadId(HpkeParameters.AeadId.AES_128_GCM)
/* 207 */         .build());
/* 208 */     result.put("DHKEM_P256_HKDF_SHA256_HKDF_SHA256_AES_256_GCM", 
/*     */         
/* 210 */         HpkeParameters.builder()
/* 211 */         .setVariant(HpkeParameters.Variant.TINK)
/* 212 */         .setKemId(HpkeParameters.KemId.DHKEM_P256_HKDF_SHA256)
/* 213 */         .setKdfId(HpkeParameters.KdfId.HKDF_SHA256)
/* 214 */         .setAeadId(HpkeParameters.AeadId.AES_256_GCM)
/* 215 */         .build());
/* 216 */     result.put("DHKEM_P256_HKDF_SHA256_HKDF_SHA256_AES_256_GCM_RAW", 
/*     */         
/* 218 */         HpkeParameters.builder()
/* 219 */         .setVariant(HpkeParameters.Variant.NO_PREFIX)
/* 220 */         .setKemId(HpkeParameters.KemId.DHKEM_P256_HKDF_SHA256)
/* 221 */         .setKdfId(HpkeParameters.KdfId.HKDF_SHA256)
/* 222 */         .setAeadId(HpkeParameters.AeadId.AES_256_GCM)
/* 223 */         .build());
/* 224 */     result.put("DHKEM_P384_HKDF_SHA384_HKDF_SHA384_AES_128_GCM", 
/*     */         
/* 226 */         HpkeParameters.builder()
/* 227 */         .setVariant(HpkeParameters.Variant.TINK)
/* 228 */         .setKemId(HpkeParameters.KemId.DHKEM_P384_HKDF_SHA384)
/* 229 */         .setKdfId(HpkeParameters.KdfId.HKDF_SHA384)
/* 230 */         .setAeadId(HpkeParameters.AeadId.AES_128_GCM)
/* 231 */         .build());
/* 232 */     result.put("DHKEM_P384_HKDF_SHA384_HKDF_SHA384_AES_128_GCM_RAW", 
/*     */         
/* 234 */         HpkeParameters.builder()
/* 235 */         .setVariant(HpkeParameters.Variant.NO_PREFIX)
/* 236 */         .setKemId(HpkeParameters.KemId.DHKEM_P384_HKDF_SHA384)
/* 237 */         .setKdfId(HpkeParameters.KdfId.HKDF_SHA384)
/* 238 */         .setAeadId(HpkeParameters.AeadId.AES_128_GCM)
/* 239 */         .build());
/* 240 */     result.put("DHKEM_P384_HKDF_SHA384_HKDF_SHA384_AES_256_GCM", 
/*     */         
/* 242 */         HpkeParameters.builder()
/* 243 */         .setVariant(HpkeParameters.Variant.TINK)
/* 244 */         .setKemId(HpkeParameters.KemId.DHKEM_P384_HKDF_SHA384)
/* 245 */         .setKdfId(HpkeParameters.KdfId.HKDF_SHA384)
/* 246 */         .setAeadId(HpkeParameters.AeadId.AES_256_GCM)
/* 247 */         .build());
/* 248 */     result.put("DHKEM_P384_HKDF_SHA384_HKDF_SHA384_AES_256_GCM_RAW", 
/*     */         
/* 250 */         HpkeParameters.builder()
/* 251 */         .setVariant(HpkeParameters.Variant.NO_PREFIX)
/* 252 */         .setKemId(HpkeParameters.KemId.DHKEM_P384_HKDF_SHA384)
/* 253 */         .setKdfId(HpkeParameters.KdfId.HKDF_SHA384)
/* 254 */         .setAeadId(HpkeParameters.AeadId.AES_256_GCM)
/* 255 */         .build());
/* 256 */     result.put("DHKEM_P521_HKDF_SHA512_HKDF_SHA512_AES_128_GCM", 
/*     */         
/* 258 */         HpkeParameters.builder()
/* 259 */         .setVariant(HpkeParameters.Variant.TINK)
/* 260 */         .setKemId(HpkeParameters.KemId.DHKEM_P521_HKDF_SHA512)
/* 261 */         .setKdfId(HpkeParameters.KdfId.HKDF_SHA512)
/* 262 */         .setAeadId(HpkeParameters.AeadId.AES_128_GCM)
/* 263 */         .build());
/* 264 */     result.put("DHKEM_P521_HKDF_SHA512_HKDF_SHA512_AES_128_GCM_RAW", 
/*     */         
/* 266 */         HpkeParameters.builder()
/* 267 */         .setVariant(HpkeParameters.Variant.NO_PREFIX)
/* 268 */         .setKemId(HpkeParameters.KemId.DHKEM_P521_HKDF_SHA512)
/* 269 */         .setKdfId(HpkeParameters.KdfId.HKDF_SHA512)
/* 270 */         .setAeadId(HpkeParameters.AeadId.AES_128_GCM)
/* 271 */         .build());
/* 272 */     result.put("DHKEM_P521_HKDF_SHA512_HKDF_SHA512_AES_256_GCM", 
/*     */         
/* 274 */         HpkeParameters.builder()
/* 275 */         .setVariant(HpkeParameters.Variant.TINK)
/* 276 */         .setKemId(HpkeParameters.KemId.DHKEM_P521_HKDF_SHA512)
/* 277 */         .setKdfId(HpkeParameters.KdfId.HKDF_SHA512)
/* 278 */         .setAeadId(HpkeParameters.AeadId.AES_256_GCM)
/* 279 */         .build());
/* 280 */     result.put("DHKEM_P521_HKDF_SHA512_HKDF_SHA512_AES_256_GCM_RAW", 
/*     */         
/* 282 */         HpkeParameters.builder()
/* 283 */         .setVariant(HpkeParameters.Variant.NO_PREFIX)
/* 284 */         .setKemId(HpkeParameters.KemId.DHKEM_P521_HKDF_SHA512)
/* 285 */         .setKdfId(HpkeParameters.KdfId.HKDF_SHA512)
/* 286 */         .setAeadId(HpkeParameters.AeadId.AES_256_GCM)
/* 287 */         .build());
/* 288 */     return Collections.unmodifiableMap(result);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\hybrid\internal\HpkePrivateKeyManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */