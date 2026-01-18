/*    */ package com.google.crypto.tink.tinkkey.internal;
/*    */ 
/*    */ import com.google.crypto.tink.KeyTemplate;
/*    */ import com.google.crypto.tink.proto.KeyData;
/*    */ import com.google.crypto.tink.tinkkey.TinkKey;
/*    */ import com.google.errorprone.annotations.Immutable;
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
/*    */ @Immutable
/*    */ public final class ProtoKey
/*    */   implements TinkKey
/*    */ {
/*    */   private final KeyData keyData;
/*    */   private final boolean hasSecret;
/*    */   private final KeyTemplate.OutputPrefixType outputPrefixType;
/*    */   
/*    */   public ProtoKey(KeyData keyData, KeyTemplate.OutputPrefixType opt) {
/* 41 */     this.hasSecret = isSecret(keyData);
/* 42 */     this.keyData = keyData;
/* 43 */     this.outputPrefixType = opt;
/*    */   }
/*    */   
/*    */   private static boolean isSecret(KeyData keyData) {
/* 47 */     return (keyData.getKeyMaterialType() == KeyData.KeyMaterialType.UNKNOWN_KEYMATERIAL || keyData
/* 48 */       .getKeyMaterialType() == KeyData.KeyMaterialType.SYMMETRIC || keyData
/* 49 */       .getKeyMaterialType() == KeyData.KeyMaterialType.ASYMMETRIC_PRIVATE);
/*    */   }
/*    */   
/*    */   public KeyData getProtoKey() {
/* 53 */     return this.keyData;
/*    */   }
/*    */   
/*    */   public KeyTemplate.OutputPrefixType getOutputPrefixType() {
/* 57 */     return this.outputPrefixType;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasSecret() {
/* 62 */     return this.hasSecret;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public KeyTemplate getKeyTemplate() {
/* 71 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\tinkkey\internal\ProtoKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */