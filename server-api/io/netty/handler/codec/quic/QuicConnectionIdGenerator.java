/*    */ package io.netty.handler.codec.quic;
/*    */ 
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
/*    */ public interface QuicConnectionIdGenerator
/*    */ {
/*    */   ByteBuffer newId(int paramInt);
/*    */   
/*    */   ByteBuffer newId(ByteBuffer paramByteBuffer, int paramInt);
/*    */   
/*    */   default ByteBuffer newId(ByteBuffer scid, ByteBuffer dcid, int length) {
/* 55 */     return newId(dcid, length);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   int maxConnectionIdLength();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   boolean isIdempotent();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static QuicConnectionIdGenerator randomGenerator() {
/* 79 */     return SecureRandomQuicConnectionIdGenerator.INSTANCE;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static QuicConnectionIdGenerator signGenerator() {
/* 88 */     return HmacSignQuicConnectionIdGenerator.INSTANCE;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\QuicConnectionIdGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */