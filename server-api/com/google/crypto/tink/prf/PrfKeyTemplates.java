/*    */ package com.google.crypto.tink.prf;
/*    */ 
/*    */ import com.google.crypto.tink.proto.AesCmacPrfKeyFormat;
/*    */ import com.google.crypto.tink.proto.HashType;
/*    */ import com.google.crypto.tink.proto.HkdfPrfKeyFormat;
/*    */ import com.google.crypto.tink.proto.HkdfPrfParams;
/*    */ import com.google.crypto.tink.proto.HmacPrfKeyFormat;
/*    */ import com.google.crypto.tink.proto.HmacPrfParams;
/*    */ import com.google.crypto.tink.proto.KeyTemplate;
/*    */ import com.google.crypto.tink.proto.OutputPrefixType;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ public final class PrfKeyTemplates
/*    */ {
/*    */   private static KeyTemplate createHkdfKeyTemplate() {
/* 58 */     HkdfPrfKeyFormat format = HkdfPrfKeyFormat.newBuilder().setKeySize(32).setParams(HkdfPrfParams.newBuilder().setHash(HashType.SHA256)).build();
/* 59 */     return KeyTemplate.newBuilder()
/* 60 */       .setValue(format.toByteString())
/* 61 */       .setTypeUrl(HkdfPrfKeyManager.staticKeyType())
/* 62 */       .setOutputPrefixType(OutputPrefixType.RAW)
/* 63 */       .build();
/*    */   }
/*    */   
/*    */   private static KeyTemplate createHmacTemplate(int keySize, HashType hashType) {
/* 67 */     HmacPrfParams params = HmacPrfParams.newBuilder().setHash(hashType).build();
/*    */     
/* 69 */     HmacPrfKeyFormat format = HmacPrfKeyFormat.newBuilder().setParams(params).setKeySize(keySize).build();
/* 70 */     return KeyTemplate.newBuilder()
/* 71 */       .setTypeUrl(HmacPrfKeyManager.getKeyType())
/* 72 */       .setValue(format.toByteString())
/* 73 */       .setOutputPrefixType(OutputPrefixType.RAW)
/* 74 */       .build();
/*    */   }
/*    */   
/*    */   private static KeyTemplate createAes256CmacTemplate() {
/* 78 */     AesCmacPrfKeyFormat format = AesCmacPrfKeyFormat.newBuilder().setKeySize(32).build();
/* 79 */     return KeyTemplate.newBuilder()
/* 80 */       .setTypeUrl(AesCmacPrfKeyManager.getKeyType())
/* 81 */       .setValue(format.toByteString())
/* 82 */       .setOutputPrefixType(OutputPrefixType.RAW)
/* 83 */       .build();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 95 */   public static final KeyTemplate HKDF_SHA256 = createHkdfKeyTemplate();
/*    */   
/* 97 */   public static final KeyTemplate HMAC_SHA256_PRF = createHmacTemplate(32, HashType.SHA256);
/* 98 */   public static final KeyTemplate HMAC_SHA512_PRF = createHmacTemplate(64, HashType.SHA512);
/* 99 */   public static final KeyTemplate AES_CMAC_PRF = createAes256CmacTemplate();
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\prf\PrfKeyTemplates.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */