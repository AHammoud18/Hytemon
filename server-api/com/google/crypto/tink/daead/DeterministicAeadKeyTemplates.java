/*    */ package com.google.crypto.tink.daead;
/*    */ 
/*    */ import com.google.crypto.tink.proto.AesSivKeyFormat;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ public final class DeterministicAeadKeyTemplates
/*    */ {
/* 57 */   public static final KeyTemplate AES256_SIV = createAesSivKeyTemplate(64);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static KeyTemplate createAesSivKeyTemplate(int keySize) {
/* 64 */     AesSivKeyFormat format = AesSivKeyFormat.newBuilder().setKeySize(keySize).build();
/* 65 */     return KeyTemplate.newBuilder()
/* 66 */       .setValue(format.toByteString())
/* 67 */       .setTypeUrl(AesSivKeyManager.getKeyType())
/* 68 */       .setOutputPrefixType(OutputPrefixType.TINK)
/* 69 */       .build();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\daead\DeterministicAeadKeyTemplates.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */