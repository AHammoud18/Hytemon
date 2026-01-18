/*    */ package com.google.crypto.tink.internal;
/*    */ 
/*    */ import com.google.crypto.tink.proto.KeyStatusType;
/*    */ import com.google.crypto.tink.tinkkey.KeyHandle;
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
/*    */ public final class KeyStatusTypeProtoConverter
/*    */ {
/*    */   public static KeyHandle.KeyStatusType fromProto(KeyStatusType keyStatusTypeProto) {
/* 29 */     switch (keyStatusTypeProto) {
/*    */       case ENABLED:
/* 31 */         return KeyHandle.KeyStatusType.ENABLED;
/*    */       case DISABLED:
/* 33 */         return KeyHandle.KeyStatusType.DISABLED;
/*    */       case DESTROYED:
/* 35 */         return KeyHandle.KeyStatusType.DESTROYED;
/*    */     } 
/* 37 */     throw new IllegalArgumentException("Unknown key status type.");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static KeyStatusType toProto(KeyHandle.KeyStatusType status) {
/* 43 */     switch (status) {
/*    */       case ENABLED:
/* 45 */         return KeyStatusType.ENABLED;
/*    */       case DISABLED:
/* 47 */         return KeyStatusType.DISABLED;
/*    */       case DESTROYED:
/* 49 */         return KeyStatusType.DESTROYED;
/*    */     } 
/* 51 */     throw new IllegalArgumentException("Unknown key status type.");
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\internal\KeyStatusTypeProtoConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */