/*     */ package com.google.crypto.tink.aead;
/*     */ 
/*     */ import com.google.crypto.tink.proto.AesCtrHmacAeadKeyFormat;
/*     */ import com.google.crypto.tink.proto.AesCtrKeyFormat;
/*     */ import com.google.crypto.tink.proto.AesCtrParams;
/*     */ import com.google.crypto.tink.proto.AesEaxKeyFormat;
/*     */ import com.google.crypto.tink.proto.AesEaxParams;
/*     */ import com.google.crypto.tink.proto.AesGcmKeyFormat;
/*     */ import com.google.crypto.tink.proto.HashType;
/*     */ import com.google.crypto.tink.proto.HmacKeyFormat;
/*     */ import com.google.crypto.tink.proto.HmacParams;
/*     */ import com.google.crypto.tink.proto.KeyTemplate;
/*     */ import com.google.crypto.tink.proto.KmsEnvelopeAeadKeyFormat;
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
/*     */ public final class AeadKeyTemplates
/*     */ {
/*  75 */   public static final KeyTemplate AES128_GCM = createAesGcmKeyTemplate(16);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  89 */   public static final KeyTemplate AES256_GCM = createAesGcmKeyTemplate(32);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 100 */   public static final KeyTemplate AES128_EAX = createAesEaxKeyTemplate(16, 16);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 111 */   public static final KeyTemplate AES256_EAX = createAesEaxKeyTemplate(32, 16);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 126 */   public static final KeyTemplate AES128_CTR_HMAC_SHA256 = createAesCtrHmacAeadKeyTemplate(16, 16, 32, 16, HashType.SHA256);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 141 */   public static final KeyTemplate AES256_CTR_HMAC_SHA256 = createAesCtrHmacAeadKeyTemplate(32, 16, 32, 32, HashType.SHA256);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 150 */   public static final KeyTemplate CHACHA20_POLY1305 = KeyTemplate.newBuilder()
/* 151 */     .setTypeUrl(ChaCha20Poly1305KeyManager.getKeyType())
/* 152 */     .setOutputPrefixType(OutputPrefixType.TINK)
/* 153 */     .build();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 162 */   public static final KeyTemplate XCHACHA20_POLY1305 = KeyTemplate.newBuilder()
/* 163 */     .setTypeUrl(XChaCha20Poly1305KeyManager.getKeyType())
/* 164 */     .setOutputPrefixType(OutputPrefixType.TINK)
/* 165 */     .build();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static KeyTemplate createAesGcmKeyTemplate(int keySize) {
/* 174 */     AesGcmKeyFormat format = AesGcmKeyFormat.newBuilder().setKeySize(keySize).build();
/* 175 */     return KeyTemplate.newBuilder()
/* 176 */       .setValue(format.toByteString())
/* 177 */       .setTypeUrl(AesGcmKeyManager.getKeyType())
/* 178 */       .setOutputPrefixType(OutputPrefixType.TINK)
/* 179 */       .build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static KeyTemplate createAesEaxKeyTemplate(int keySize, int ivSize) {
/* 190 */     AesEaxKeyFormat format = AesEaxKeyFormat.newBuilder().setKeySize(keySize).setParams(AesEaxParams.newBuilder().setIvSize(ivSize).build()).build();
/* 191 */     return KeyTemplate.newBuilder()
/* 192 */       .setValue(format.toByteString())
/* 193 */       .setTypeUrl(AesEaxKeyManager.getKeyType())
/* 194 */       .setOutputPrefixType(OutputPrefixType.TINK)
/* 195 */       .build();
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
/*     */   public static KeyTemplate createAesCtrHmacAeadKeyTemplate(int aesKeySize, int ivSize, int hmacKeySize, int tagSize, HashType hashType) {
/* 207 */     AesCtrKeyFormat aesCtrKeyFormat = AesCtrKeyFormat.newBuilder().setParams(AesCtrParams.newBuilder().setIvSize(ivSize).build()).setKeySize(aesKeySize).build();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 212 */     HmacKeyFormat hmacKeyFormat = HmacKeyFormat.newBuilder().setParams(HmacParams.newBuilder().setHash(hashType).setTagSize(tagSize).build()).setKeySize(hmacKeySize).build();
/*     */ 
/*     */ 
/*     */     
/* 216 */     AesCtrHmacAeadKeyFormat format = AesCtrHmacAeadKeyFormat.newBuilder().setAesCtrKeyFormat(aesCtrKeyFormat).setHmacKeyFormat(hmacKeyFormat).build();
/* 217 */     return KeyTemplate.newBuilder()
/* 218 */       .setValue(format.toByteString())
/* 219 */       .setTypeUrl(AesCtrHmacAeadKeyManager.getKeyType())
/* 220 */       .setOutputPrefixType(OutputPrefixType.TINK)
/* 221 */       .build();
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
/*     */   public static KeyTemplate createKmsEnvelopeAeadKeyTemplate(String kekUri, KeyTemplate dekTemplate) {
/* 237 */     KmsEnvelopeAeadKeyFormat format = KmsEnvelopeAeadKeyFormat.newBuilder().setDekTemplate(dekTemplate).setKekUri(kekUri).build();
/* 238 */     return KeyTemplate.newBuilder()
/* 239 */       .setValue(format.toByteString())
/* 240 */       .setTypeUrl(KmsEnvelopeAeadKeyManager.getKeyType())
/* 241 */       .setOutputPrefixType(OutputPrefixType.RAW)
/* 242 */       .build();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\aead\AeadKeyTemplates.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */