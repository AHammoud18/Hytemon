/*    */ package io.netty.handler.ssl;
/*    */ 
/*    */ import io.netty.internal.tcnative.SessionTicketKey;
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
/*    */ public final class OpenSslSessionTicketKey
/*    */ {
/*    */   public static final int NAME_SIZE = 16;
/*    */   public static final int HMAC_KEY_SIZE = 16;
/*    */   public static final int AES_KEY_SIZE = 16;
/*    */   public static final int TICKET_KEY_SIZE = 48;
/*    */   final SessionTicketKey key;
/*    */   
/*    */   public OpenSslSessionTicketKey(byte[] name, byte[] hmacKey, byte[] aesKey) {
/* 52 */     this.key = new SessionTicketKey((byte[])name.clone(), (byte[])hmacKey.clone(), (byte[])aesKey.clone());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte[] name() {
/* 60 */     return (byte[])this.key.getName().clone();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte[] hmacKey() {
/* 68 */     return (byte[])this.key.getHmacKey().clone();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte[] aesKey() {
/* 76 */     return (byte[])this.key.getAesKey().clone();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ssl\OpenSslSessionTicketKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */