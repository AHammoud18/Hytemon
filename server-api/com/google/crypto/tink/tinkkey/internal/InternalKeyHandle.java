/*    */ package com.google.crypto.tink.tinkkey.internal;
/*    */ 
/*    */ import com.google.crypto.tink.internal.KeyStatusTypeProtoConverter;
/*    */ import com.google.crypto.tink.proto.KeyStatusType;
/*    */ import com.google.crypto.tink.tinkkey.KeyHandle;
/*    */ import com.google.crypto.tink.tinkkey.TinkKey;
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
/*    */ public final class InternalKeyHandle
/*    */   extends KeyHandle
/*    */ {
/*    */   public InternalKeyHandle(TinkKey key, KeyStatusType status, int keyId) {
/* 30 */     super(key, KeyStatusTypeProtoConverter.fromProto(status), keyId);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\tinkkey\internal\InternalKeyHandle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */