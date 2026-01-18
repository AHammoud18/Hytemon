/*     */ package com.google.crypto.tink.mac;
/*     */ 
/*     */ import com.google.crypto.tink.proto.AesCmacKeyFormat;
/*     */ import com.google.crypto.tink.proto.AesCmacParams;
/*     */ import com.google.crypto.tink.proto.HashType;
/*     */ import com.google.crypto.tink.proto.HmacKeyFormat;
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
/*     */ @Deprecated
/*     */ public final class MacKeyTemplates
/*     */ {
/*  72 */   public static final KeyTemplate HMAC_SHA256_128BITTAG = createHmacKeyTemplate(32, 16, HashType.SHA256);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  86 */   public static final KeyTemplate HMAC_SHA256_256BITTAG = createHmacKeyTemplate(32, 32, HashType.SHA256);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 100 */   public static final KeyTemplate HMAC_SHA512_256BITTAG = createHmacKeyTemplate(64, 32, HashType.SHA512);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 114 */   public static final KeyTemplate HMAC_SHA512_512BITTAG = createHmacKeyTemplate(64, 64, HashType.SHA512);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 127 */   public static final KeyTemplate AES_CMAC = KeyTemplate.newBuilder()
/* 128 */     .setValue(
/* 129 */       AesCmacKeyFormat.newBuilder()
/* 130 */       .setKeySize(32)
/* 131 */       .setParams(AesCmacParams.newBuilder().setTagSize(16).build())
/* 132 */       .build()
/* 133 */       .toByteString())
/* 134 */     .setTypeUrl("type.googleapis.com/google.crypto.tink.AesCmacKey")
/* 135 */     .setOutputPrefixType(OutputPrefixType.TINK)
/* 136 */     .build();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static KeyTemplate createHmacKeyTemplate(int keySize, int tagSize, HashType hashType) {
/* 146 */     HmacParams params = HmacParams.newBuilder().setHash(hashType).setTagSize(tagSize).build();
/*     */ 
/*     */ 
/*     */     
/* 150 */     HmacKeyFormat format = HmacKeyFormat.newBuilder().setParams(params).setKeySize(keySize).build();
/* 151 */     return KeyTemplate.newBuilder()
/* 152 */       .setValue(format.toByteString())
/* 153 */       .setTypeUrl(HmacKeyManager.getKeyType())
/* 154 */       .setOutputPrefixType(OutputPrefixType.TINK)
/* 155 */       .build();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\mac\MacKeyTemplates.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */