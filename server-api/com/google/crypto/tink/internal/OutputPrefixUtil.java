/*    */ package com.google.crypto.tink.internal;
/*    */ 
/*    */ import com.google.crypto.tink.util.Bytes;
/*    */ import java.nio.ByteBuffer;
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
/*    */ public final class OutputPrefixUtil
/*    */ {
/*    */   public static final int NON_EMPTY_PREFIX_SIZE = 5;
/*    */   public static final byte LEGACY_START_BYTE = 0;
/*    */   public static final byte TINK_START_BYTE = 1;
/* 32 */   public static final Bytes EMPTY_PREFIX = Bytes.copyFrom(new byte[0]);
/*    */   
/*    */   public static final Bytes getLegacyOutputPrefix(int keyId) {
/* 35 */     return Bytes.copyFrom(
/* 36 */         ByteBuffer.allocate(5)
/* 37 */         .put((byte)0)
/* 38 */         .putInt(keyId)
/* 39 */         .array());
/*    */   }
/*    */   
/*    */   public static final Bytes getTinkOutputPrefix(int keyId) {
/* 43 */     return Bytes.copyFrom(
/* 44 */         ByteBuffer.allocate(5)
/* 45 */         .put((byte)1)
/* 46 */         .putInt(keyId)
/* 47 */         .array());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\internal\OutputPrefixUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */