/*    */ package com.google.crypto.tink.hybrid.internal;
/*    */ 
/*    */ import com.google.crypto.tink.util.Bytes;
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
/*    */ @Immutable
/*    */ public class HpkeKemPrivateKey
/*    */ {
/*    */   private final Bytes serializedPrivate;
/*    */   private final Bytes serializedPublic;
/*    */   
/*    */   public HpkeKemPrivateKey(Bytes serializedPrivate, Bytes serializedPublic) {
/* 30 */     this.serializedPrivate = serializedPrivate;
/* 31 */     this.serializedPublic = serializedPublic;
/*    */   }
/*    */ 
/*    */   
/*    */   Bytes getSerializedPrivate() {
/* 36 */     return this.serializedPrivate;
/*    */   }
/*    */   
/*    */   Bytes getSerializedPublic() {
/* 40 */     return this.serializedPublic;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\hybrid\internal\HpkeKemPrivateKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */