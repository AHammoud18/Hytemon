/*    */ package io.netty.handler.codec.quic;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.buffer.ByteBufHolder;
/*    */ import io.netty.buffer.Unpooled;
/*    */ import io.netty.util.ReferenceCounted;
/*    */ 
/*    */ 
/*    */ public interface QuicStreamFrame
/*    */   extends ByteBufHolder
/*    */ {
/*    */   boolean hasFin();
/*    */   
/*    */   QuicStreamFrame copy();
/*    */   
/*    */   QuicStreamFrame duplicate();
/*    */   
/*    */   QuicStreamFrame retainedDuplicate();
/*    */   
/*    */   QuicStreamFrame replace(ByteBuf paramByteBuf);
/*    */   
/*    */   QuicStreamFrame retain();
/*    */   
/*    */   QuicStreamFrame retain(int paramInt);
/*    */   
/*    */   QuicStreamFrame touch();
/*    */   
/*    */   QuicStreamFrame touch(Object paramObject);
/*    */   
/* 30 */   public static final QuicStreamFrame EMPTY_FIN = new QuicStreamFrame()
/*    */     {
/*    */       public boolean hasFin() {
/* 33 */         return true;
/*    */       }
/*    */ 
/*    */       
/*    */       public QuicStreamFrame copy() {
/* 38 */         return this;
/*    */       }
/*    */ 
/*    */       
/*    */       public QuicStreamFrame duplicate() {
/* 43 */         return this;
/*    */       }
/*    */ 
/*    */       
/*    */       public QuicStreamFrame retainedDuplicate() {
/* 48 */         return this;
/*    */       }
/*    */ 
/*    */       
/*    */       public QuicStreamFrame replace(ByteBuf content) {
/* 53 */         return new DefaultQuicStreamFrame(content, hasFin());
/*    */       }
/*    */ 
/*    */       
/*    */       public QuicStreamFrame retain() {
/* 58 */         return this;
/*    */       }
/*    */ 
/*    */       
/*    */       public QuicStreamFrame retain(int increment) {
/* 63 */         return this;
/*    */       }
/*    */ 
/*    */       
/*    */       public QuicStreamFrame touch() {
/* 68 */         return this;
/*    */       }
/*    */ 
/*    */       
/*    */       public QuicStreamFrame touch(Object hint) {
/* 73 */         return this;
/*    */       }
/*    */ 
/*    */       
/*    */       public ByteBuf content() {
/* 78 */         return Unpooled.EMPTY_BUFFER;
/*    */       }
/*    */ 
/*    */       
/*    */       public int refCnt() {
/* 83 */         return 1;
/*    */       }
/*    */ 
/*    */       
/*    */       public boolean release() {
/* 88 */         return false;
/*    */       }
/*    */ 
/*    */       
/*    */       public boolean release(int decrement) {
/* 93 */         return false;
/*    */       }
/*    */     };
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\QuicStreamFrame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */