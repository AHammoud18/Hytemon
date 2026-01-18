/*    */ package io.netty.handler.codec.quic;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.buffer.Unpooled;
/*    */ import io.netty.util.CharsetUtil;
/*    */ import io.netty.util.NetUtil;
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
/*    */ 
/*    */ public final class InsecureQuicTokenHandler
/*    */   implements QuicTokenHandler
/*    */ {
/*    */   private static final String SERVER_NAME = "netty";
/* 34 */   private static final byte[] SERVER_NAME_BYTES = "netty".getBytes(CharsetUtil.US_ASCII);
/* 35 */   private static final ByteBuf SERVER_NAME_BUFFER = Unpooled.unreleasableBuffer(
/* 36 */       Unpooled.wrappedBuffer(SERVER_NAME_BYTES)).asReadOnly();
/*    */ 
/*    */   
/* 39 */   static final int MAX_TOKEN_LEN = 20 + (NetUtil.LOCALHOST6
/* 40 */     .getAddress()).length + SERVER_NAME_BYTES.length;
/*    */   
/*    */   private InsecureQuicTokenHandler() {
/* 43 */     Quic.ensureAvailability();
/*    */   }
/*    */   
/* 46 */   public static final InsecureQuicTokenHandler INSTANCE = new InsecureQuicTokenHandler();
/*    */ 
/*    */   
/*    */   public boolean writeToken(ByteBuf out, ByteBuf dcid, InetSocketAddress address) {
/* 50 */     byte[] addr = address.getAddress().getAddress();
/* 51 */     out.writeBytes(SERVER_NAME_BYTES)
/* 52 */       .writeBytes(addr)
/* 53 */       .writeBytes(dcid, dcid.readerIndex(), dcid.readableBytes());
/* 54 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public int validateToken(ByteBuf token, InetSocketAddress address) {
/* 59 */     byte[] addr = address.getAddress().getAddress();
/*    */     
/* 61 */     int minLength = SERVER_NAME_BYTES.length + (address.getAddress().getAddress()).length;
/* 62 */     if (token.readableBytes() <= SERVER_NAME_BYTES.length + addr.length) {
/* 63 */       return -1;
/*    */     }
/*    */     
/* 66 */     if (!SERVER_NAME_BUFFER.equals(token.slice(0, SERVER_NAME_BYTES.length))) {
/* 67 */       return -1;
/*    */     }
/* 69 */     ByteBuf addressBuffer = Unpooled.wrappedBuffer(addr);
/*    */     try {
/* 71 */       if (!addressBuffer.equals(token.slice(SERVER_NAME_BYTES.length, addr.length))) {
/* 72 */         return -1;
/*    */       }
/*    */     } finally {
/* 75 */       addressBuffer.release();
/*    */     } 
/* 77 */     return minLength;
/*    */   }
/*    */ 
/*    */   
/*    */   public int maxTokenLength() {
/* 82 */     return MAX_TOKEN_LEN;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\InsecureQuicTokenHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */