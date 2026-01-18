/*     */ package com.google.crypto.tink.streamingaead;
/*     */ 
/*     */ import com.google.crypto.tink.proto.AesCtrHmacStreamingKeyFormat;
/*     */ import com.google.crypto.tink.proto.AesCtrHmacStreamingParams;
/*     */ import com.google.crypto.tink.proto.AesGcmHkdfStreamingKeyFormat;
/*     */ import com.google.crypto.tink.proto.AesGcmHkdfStreamingParams;
/*     */ import com.google.crypto.tink.proto.HashType;
/*     */ import com.google.crypto.tink.proto.HmacParams;
/*     */ import com.google.crypto.tink.proto.KeyTemplate;
/*     */ import com.google.crypto.tink.proto.OutputPrefixType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Deprecated
/*     */ public final class StreamingAeadKeyTemplates
/*     */ {
/*  77 */   public static final KeyTemplate AES128_CTR_HMAC_SHA256_4KB = createAesCtrHmacStreamingKeyTemplate(16, HashType.SHA256, 16, HashType.SHA256, 32, 4096);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  93 */   public static final KeyTemplate AES128_CTR_HMAC_SHA256_1MB = createAesCtrHmacStreamingKeyTemplate(16, HashType.SHA256, 16, HashType.SHA256, 32, 1048576);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 109 */   public static final KeyTemplate AES256_CTR_HMAC_SHA256_4KB = createAesCtrHmacStreamingKeyTemplate(32, HashType.SHA256, 32, HashType.SHA256, 32, 4096);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 125 */   public static final KeyTemplate AES256_CTR_HMAC_SHA256_1MB = createAesCtrHmacStreamingKeyTemplate(32, HashType.SHA256, 32, HashType.SHA256, 32, 1048576);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 139 */   public static final KeyTemplate AES128_GCM_HKDF_4KB = createAesGcmHkdfStreamingKeyTemplate(16, HashType.SHA256, 16, 4096);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 153 */   public static final KeyTemplate AES128_GCM_HKDF_1MB = createAesGcmHkdfStreamingKeyTemplate(16, HashType.SHA256, 16, 1048576);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 167 */   public static final KeyTemplate AES256_GCM_HKDF_4KB = createAesGcmHkdfStreamingKeyTemplate(32, HashType.SHA256, 32, 4096);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 181 */   public static final KeyTemplate AES256_GCM_HKDF_1MB = createAesGcmHkdfStreamingKeyTemplate(32, HashType.SHA256, 32, 1048576);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static KeyTemplate createAesCtrHmacStreamingKeyTemplate(int mainKeySize, HashType hkdfHashType, int derivedKeySize, HashType macHashType, int tagSize, int ciphertextSegmentSize) {
/* 197 */     HmacParams hmacParams = HmacParams.newBuilder().setHash(macHashType).setTagSize(tagSize).build();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 204 */     AesCtrHmacStreamingParams params = AesCtrHmacStreamingParams.newBuilder().setCiphertextSegmentSize(ciphertextSegmentSize).setDerivedKeySize(derivedKeySize).setHkdfHashType(hkdfHashType).setHmacParams(hmacParams).build();
/*     */ 
/*     */ 
/*     */     
/* 208 */     AesCtrHmacStreamingKeyFormat format = AesCtrHmacStreamingKeyFormat.newBuilder().setParams(params).setKeySize(mainKeySize).build();
/* 209 */     return KeyTemplate.newBuilder()
/* 210 */       .setValue(format.toByteString())
/* 211 */       .setTypeUrl(AesCtrHmacStreamingKeyManager.getKeyType())
/* 212 */       .setOutputPrefixType(OutputPrefixType.RAW)
/* 213 */       .build();
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
/*     */   public static KeyTemplate createAesGcmHkdfStreamingKeyTemplate(int mainKeySize, HashType hkdfHashType, int derivedKeySize, int ciphertextSegmentSize) {
/* 227 */     AesGcmHkdfStreamingParams keyParams = AesGcmHkdfStreamingParams.newBuilder().setCiphertextSegmentSize(ciphertextSegmentSize).setDerivedKeySize(derivedKeySize).setHkdfHashType(hkdfHashType).build();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 232 */     AesGcmHkdfStreamingKeyFormat format = AesGcmHkdfStreamingKeyFormat.newBuilder().setKeySize(mainKeySize).setParams(keyParams).build();
/* 233 */     return KeyTemplate.newBuilder()
/* 234 */       .setValue(format.toByteString())
/* 235 */       .setTypeUrl(AesGcmHkdfStreamingKeyManager.getKeyType())
/* 236 */       .setOutputPrefixType(OutputPrefixType.RAW)
/* 237 */       .build();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\streamingaead\StreamingAeadKeyTemplates.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */