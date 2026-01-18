/*    */ package io.netty.handler.codec.quic;
/*    */ 
/*    */ import io.netty.util.internal.ObjectUtil;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.security.SecureRandom;
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
/*    */ final class SecureRandomQuicConnectionIdGenerator
/*    */   implements QuicConnectionIdGenerator
/*    */ {
/* 24 */   private static final SecureRandom RANDOM = new SecureRandom();
/*    */   
/* 26 */   static final QuicConnectionIdGenerator INSTANCE = new SecureRandomQuicConnectionIdGenerator();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ByteBuffer newId(int length) {
/* 33 */     ObjectUtil.checkInRange(length, 0, maxConnectionIdLength(), "length");
/* 34 */     byte[] bytes = new byte[length];
/* 35 */     RANDOM.nextBytes(bytes);
/* 36 */     return ByteBuffer.wrap(bytes);
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer newId(ByteBuffer buffer, int length) {
/* 41 */     return newId(length);
/*    */   }
/*    */ 
/*    */   
/*    */   public int maxConnectionIdLength() {
/* 46 */     return Quiche.QUICHE_MAX_CONN_ID_LEN;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isIdempotent() {
/* 51 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\SecureRandomQuicConnectionIdGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */