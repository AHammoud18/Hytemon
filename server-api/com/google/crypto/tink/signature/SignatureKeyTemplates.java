/*     */ package com.google.crypto.tink.signature;
/*     */ 
/*     */ import com.google.crypto.tink.proto.EcdsaKeyFormat;
/*     */ import com.google.crypto.tink.proto.EcdsaParams;
/*     */ import com.google.crypto.tink.proto.EcdsaSignatureEncoding;
/*     */ import com.google.crypto.tink.proto.EllipticCurveType;
/*     */ import com.google.crypto.tink.proto.HashType;
/*     */ import com.google.crypto.tink.proto.KeyTemplate;
/*     */ import com.google.crypto.tink.proto.OutputPrefixType;
/*     */ import com.google.crypto.tink.proto.RsaSsaPkcs1KeyFormat;
/*     */ import com.google.crypto.tink.proto.RsaSsaPkcs1Params;
/*     */ import com.google.crypto.tink.proto.RsaSsaPssKeyFormat;
/*     */ import com.google.crypto.tink.proto.RsaSsaPssParams;
/*     */ import com.google.protobuf.ByteString;
/*     */ import java.math.BigInteger;
/*     */ import java.security.spec.RSAKeyGenParameterSpec;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SignatureKeyTemplates
/*     */ {
/*  80 */   public static final KeyTemplate ECDSA_P256 = createEcdsaKeyTemplate(HashType.SHA256, EllipticCurveType.NIST_P256, EcdsaSignatureEncoding.DER, OutputPrefixType.TINK);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  98 */   public static final KeyTemplate ECDSA_P384 = createEcdsaKeyTemplate(HashType.SHA512, EllipticCurveType.NIST_P384, EcdsaSignatureEncoding.DER, OutputPrefixType.TINK);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 116 */   public static final KeyTemplate ECDSA_P521 = createEcdsaKeyTemplate(HashType.SHA512, EllipticCurveType.NIST_P521, EcdsaSignatureEncoding.DER, OutputPrefixType.TINK);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 134 */   public static final KeyTemplate ECDSA_P256_IEEE_P1363 = createEcdsaKeyTemplate(HashType.SHA256, EllipticCurveType.NIST_P256, EcdsaSignatureEncoding.IEEE_P1363, OutputPrefixType.TINK);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 152 */   public static final KeyTemplate ECDSA_P384_IEEE_P1363 = createEcdsaKeyTemplate(HashType.SHA512, EllipticCurveType.NIST_P384, EcdsaSignatureEncoding.IEEE_P1363, OutputPrefixType.TINK);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 172 */   public static final KeyTemplate ECDSA_P256_IEEE_P1363_WITHOUT_PREFIX = createEcdsaKeyTemplate(HashType.SHA256, EllipticCurveType.NIST_P256, EcdsaSignatureEncoding.IEEE_P1363, OutputPrefixType.RAW);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 190 */   public static final KeyTemplate ECDSA_P521_IEEE_P1363 = createEcdsaKeyTemplate(HashType.SHA512, EllipticCurveType.NIST_P521, EcdsaSignatureEncoding.IEEE_P1363, OutputPrefixType.TINK);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 203 */   public static final KeyTemplate ED25519 = KeyTemplate.newBuilder()
/* 204 */     .setTypeUrl(Ed25519PrivateKeyManager.getKeyType())
/* 205 */     .setOutputPrefixType(OutputPrefixType.TINK)
/* 206 */     .build();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 219 */   public static final KeyTemplate ED25519WithRawOutput = KeyTemplate.newBuilder()
/* 220 */     .setTypeUrl(Ed25519PrivateKeyManager.getKeyType())
/* 221 */     .setOutputPrefixType(OutputPrefixType.RAW)
/* 222 */     .build();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static KeyTemplate createEcdsaKeyTemplate(HashType hashType, EllipticCurveType curve, EcdsaSignatureEncoding encoding, OutputPrefixType prefixType) {
/* 240 */     EcdsaParams params = EcdsaParams.newBuilder().setHashType(hashType).setCurve(curve).setEncoding(encoding).build();
/* 241 */     EcdsaKeyFormat format = EcdsaKeyFormat.newBuilder().setParams(params).build();
/* 242 */     return KeyTemplate.newBuilder()
/* 243 */       .setValue(format.toByteString())
/* 244 */       .setTypeUrl(EcdsaSignKeyManager.getKeyType())
/* 245 */       .setOutputPrefixType(prefixType)
/* 246 */       .build();
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
/* 261 */   public static final KeyTemplate RSA_SSA_PKCS1_3072_SHA256_F4 = createRsaSsaPkcs1KeyTemplate(HashType.SHA256, 3072, RSAKeyGenParameterSpec.F4, OutputPrefixType.TINK);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 276 */   public static final KeyTemplate RSA_SSA_PKCS1_3072_SHA256_F4_WITHOUT_PREFIX = createRsaSsaPkcs1KeyTemplate(HashType.SHA256, 3072, RSAKeyGenParameterSpec.F4, OutputPrefixType.RAW);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 291 */   public static final KeyTemplate RSA_SSA_PKCS1_4096_SHA512_F4 = createRsaSsaPkcs1KeyTemplate(HashType.SHA512, 4096, RSAKeyGenParameterSpec.F4, OutputPrefixType.TINK);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static KeyTemplate createRsaSsaPkcs1KeyTemplate(HashType hashType, int modulusSize, BigInteger publicExponent, OutputPrefixType prefixType) {
/* 302 */     RsaSsaPkcs1Params params = RsaSsaPkcs1Params.newBuilder().setHashType(hashType).build();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 308 */     RsaSsaPkcs1KeyFormat format = RsaSsaPkcs1KeyFormat.newBuilder().setParams(params).setModulusSizeInBits(modulusSize).setPublicExponent(ByteString.copyFrom(publicExponent.toByteArray())).build();
/* 309 */     return KeyTemplate.newBuilder()
/* 310 */       .setValue(format.toByteString())
/* 311 */       .setTypeUrl(RsaSsaPkcs1SignKeyManager.getKeyType())
/* 312 */       .setOutputPrefixType(prefixType)
/* 313 */       .build();
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
/* 329 */   public static final KeyTemplate RSA_SSA_PSS_3072_SHA256_SHA256_32_F4 = createRsaSsaPssKeyTemplate(HashType.SHA256, HashType.SHA256, 32, 3072, RSAKeyGenParameterSpec.F4);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 345 */   public static final KeyTemplate RSA_SSA_PSS_4096_SHA512_SHA512_64_F4 = createRsaSsaPssKeyTemplate(HashType.SHA512, HashType.SHA512, 64, 4096, RSAKeyGenParameterSpec.F4);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static KeyTemplate createRsaSsaPssKeyTemplate(HashType sigHash, HashType mgf1Hash, int saltLength, int modulusSize, BigInteger publicExponent) {
/* 365 */     RsaSsaPssParams params = RsaSsaPssParams.newBuilder().setSigHash(sigHash).setMgf1Hash(mgf1Hash).setSaltLength(saltLength).build();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 371 */     RsaSsaPssKeyFormat format = RsaSsaPssKeyFormat.newBuilder().setParams(params).setModulusSizeInBits(modulusSize).setPublicExponent(ByteString.copyFrom(publicExponent.toByteArray())).build();
/* 372 */     return KeyTemplate.newBuilder()
/* 373 */       .setValue(format.toByteString())
/* 374 */       .setTypeUrl(RsaSsaPssSignKeyManager.getKeyType())
/* 375 */       .setOutputPrefixType(OutputPrefixType.TINK)
/* 376 */       .build();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\signature\SignatureKeyTemplates.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */