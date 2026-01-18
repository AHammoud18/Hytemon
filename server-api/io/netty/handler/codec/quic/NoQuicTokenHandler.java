/*    */ package io.netty.handler.codec.quic;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.net.InetSocketAddress;
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
/*    */ final class NoQuicTokenHandler
/*    */   implements QuicTokenHandler
/*    */ {
/* 29 */   public static final QuicTokenHandler INSTANCE = new NoQuicTokenHandler();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean writeToken(ByteBuf out, ByteBuf dcid, InetSocketAddress address) {
/* 36 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int validateToken(ByteBuf token, InetSocketAddress address) {
/* 41 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public int maxTokenLength() {
/* 46 */     return 0;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\NoQuicTokenHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */