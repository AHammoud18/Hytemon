/*    */ package io.netty.handler.codec.quic;
/*    */ 
/*    */ import io.netty.util.internal.PlatformDependent;
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
/*    */ final class BoringSSLSessionTicketCallback
/*    */ {
/*    */   private volatile byte[][] sessionKeys;
/*    */   
/*    */   byte[] findSessionTicket(byte[] keyname) {
/* 28 */     byte[][] keys = this.sessionKeys;
/* 29 */     if (keys == null || keys.length == 0) {
/* 30 */       return null;
/*    */     }
/* 32 */     if (keyname == null) {
/* 33 */       return keys[0];
/*    */     }
/*    */     
/* 36 */     for (int i = 0; i < keys.length; i++) {
/* 37 */       byte[] key = keys[i];
/* 38 */       if (PlatformDependent.equals(keyname, 0, key, 1, keyname.length)) {
/* 39 */         return key;
/*    */       }
/*    */     } 
/* 42 */     return null;
/*    */   }
/*    */   
/*    */   void setSessionTicketKeys(SslSessionTicketKey[] keys) {
/* 46 */     if (keys != null && keys.length != 0) {
/* 47 */       byte[][] sessionKeys = new byte[keys.length][];
/* 48 */       for (int i = 0; i < keys.length; i++) {
/* 49 */         SslSessionTicketKey key = keys[i];
/* 50 */         byte[] binaryKey = new byte[49];
/*    */         
/* 52 */         binaryKey[0] = (i == 0) ? 1 : 0;
/* 53 */         int dstCurPos = 1;
/* 54 */         System.arraycopy(key.name, 0, binaryKey, dstCurPos, 16);
/* 55 */         dstCurPos += 16;
/* 56 */         System.arraycopy(key.hmacKey, 0, binaryKey, dstCurPos, 16);
/* 57 */         dstCurPos += 16;
/* 58 */         System.arraycopy(key.aesKey, 0, binaryKey, dstCurPos, 16);
/* 59 */         sessionKeys[i] = binaryKey;
/*    */       } 
/* 61 */       this.sessionKeys = sessionKeys;
/*    */     } else {
/* 63 */       this.sessionKeys = null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\BoringSSLSessionTicketCallback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */