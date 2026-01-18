/*    */ package io.netty.handler.codec.quic;
/*    */ 
/*    */ import io.netty.util.internal.ObjectUtil;
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
/*    */ final class HmacSignQuicConnectionIdGenerator
/*    */   implements QuicConnectionIdGenerator
/*    */ {
/* 27 */   static final QuicConnectionIdGenerator INSTANCE = new HmacSignQuicConnectionIdGenerator();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ByteBuffer newId(int length) {
/* 34 */     throw new UnsupportedOperationException("HmacSignQuicConnectionIdGenerator should always have an input to sign with");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ByteBuffer newId(ByteBuffer buffer, int length) {
/* 40 */     ObjectUtil.checkNotNull(buffer, "buffer");
/* 41 */     ObjectUtil.checkPositive(buffer.remaining(), "buffer");
/* 42 */     ObjectUtil.checkInRange(length, 0, maxConnectionIdLength(), "length");
/*    */     
/* 44 */     return Hmac.sign(buffer, length);
/*    */   }
/*    */ 
/*    */   
/*    */   public int maxConnectionIdLength() {
/* 49 */     return Quiche.QUICHE_MAX_CONN_ID_LEN;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isIdempotent() {
/* 54 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\HmacSignQuicConnectionIdGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */