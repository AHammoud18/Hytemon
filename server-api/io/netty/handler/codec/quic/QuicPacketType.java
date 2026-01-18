/*    */ package io.netty.handler.codec.quic;
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
/*    */ public enum QuicPacketType
/*    */ {
/* 26 */   INITIAL,
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 31 */   RETRY,
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 36 */   HANDSHAKE,
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 41 */   ZERO_RTT,
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 46 */   SHORT,
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 51 */   VERSION_NEGOTIATION;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static QuicPacketType of(byte type) {
/* 60 */     switch (type) {
/*    */       case 1:
/* 62 */         return INITIAL;
/*    */       case 2:
/* 64 */         return RETRY;
/*    */       case 3:
/* 66 */         return HANDSHAKE;
/*    */       case 4:
/* 68 */         return ZERO_RTT;
/*    */       case 5:
/* 70 */         return SHORT;
/*    */       case 6:
/* 72 */         return VERSION_NEGOTIATION;
/*    */     } 
/* 74 */     throw new IllegalArgumentException("Unknown QUIC packet type: " + type);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\QuicPacketType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */