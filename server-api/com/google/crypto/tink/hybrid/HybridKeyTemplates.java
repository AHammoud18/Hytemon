/*     */ package com.google.crypto.tink.hybrid;
/*     */ 
/*     */ import com.google.crypto.tink.aead.AeadKeyTemplates;
/*     */ import com.google.crypto.tink.proto.EcPointFormat;
/*     */ import com.google.crypto.tink.proto.EciesAeadDemParams;
/*     */ import com.google.crypto.tink.proto.EciesAeadHkdfKeyFormat;
/*     */ import com.google.crypto.tink.proto.EciesAeadHkdfParams;
/*     */ import com.google.crypto.tink.proto.EciesHkdfKemParams;
/*     */ import com.google.crypto.tink.proto.EllipticCurveType;
/*     */ import com.google.crypto.tink.proto.HashType;
/*     */ import com.google.crypto.tink.proto.KeyTemplate;
/*     */ import com.google.crypto.tink.proto.OutputPrefixType;
/*     */ import com.google.protobuf.ByteString;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class HybridKeyTemplates
/*     */ {
/*  66 */   private static final byte[] EMPTY_SALT = new byte[0];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  82 */   public static final KeyTemplate ECIES_P256_HKDF_HMAC_SHA256_AES128_GCM = createEciesAeadHkdfKeyTemplate(EllipticCurveType.NIST_P256, HashType.SHA256, EcPointFormat.UNCOMPRESSED, AeadKeyTemplates.AES128_GCM, OutputPrefixType.TINK, EMPTY_SALT);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 107 */   public static final KeyTemplate ECIES_P256_HKDF_HMAC_SHA256_AES128_GCM_COMPRESSED_WITHOUT_PREFIX = createEciesAeadHkdfKeyTemplate(EllipticCurveType.NIST_P256, HashType.SHA256, EcPointFormat.COMPRESSED, AeadKeyTemplates.AES128_GCM, OutputPrefixType.RAW, EMPTY_SALT);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 132 */   public static final KeyTemplate ECIES_P256_HKDF_HMAC_SHA256_AES128_CTR_HMAC_SHA256 = createEciesAeadHkdfKeyTemplate(EllipticCurveType.NIST_P256, HashType.SHA256, EcPointFormat.UNCOMPRESSED, AeadKeyTemplates.AES128_CTR_HMAC_SHA256, OutputPrefixType.TINK, EMPTY_SALT);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */   public static KeyTemplate createEciesAeadHkdfKeyTemplate(EllipticCurveType curve, HashType hashType, EcPointFormat ecPointFormat, KeyTemplate demKeyTemplate, OutputPrefixType outputPrefixType, byte[] salt) {
/* 155 */     EciesAeadHkdfKeyFormat format = EciesAeadHkdfKeyFormat.newBuilder().setParams(createEciesAeadHkdfParams(curve, hashType, ecPointFormat, demKeyTemplate, salt)).build();
/* 156 */     return KeyTemplate.newBuilder()
/* 157 */       .setTypeUrl(EciesAeadHkdfPrivateKeyManager.getKeyType())
/* 158 */       .setOutputPrefixType(outputPrefixType)
/* 159 */       .setValue(format.toByteString())
/* 160 */       .build();
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
/*     */   @Deprecated
/*     */   public static EciesAeadHkdfParams createEciesAeadHkdfParams(EllipticCurveType curve, HashType hashType, EcPointFormat ecPointFormat, KeyTemplate demKeyTemplate, byte[] salt) {
/* 178 */     EciesHkdfKemParams kemParams = EciesHkdfKemParams.newBuilder().setCurveType(curve).setHkdfHashType(hashType).setHkdfSalt(ByteString.copyFrom(salt)).build();
/*     */ 
/*     */     
/* 181 */     EciesAeadDemParams demParams = EciesAeadDemParams.newBuilder().setAeadDem(demKeyTemplate).build();
/* 182 */     return EciesAeadHkdfParams.newBuilder()
/* 183 */       .setKemParams(kemParams)
/* 184 */       .setDemParams(demParams)
/* 185 */       .setEcPointFormat(ecPointFormat)
/* 186 */       .build();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\hybrid\HybridKeyTemplates.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */