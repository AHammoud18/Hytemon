/*    */ package io.netty.handler.codec.http3;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.buffer.ByteBufHolder;
/*    */ import io.netty.util.ReferenceCounted;
/*    */ 
/*    */ 
/*    */ public interface Http3DataFrame
/*    */   extends ByteBufHolder, Http3RequestStreamFrame, Http3PushStreamFrame
/*    */ {
/*    */   Http3DataFrame touch(Object paramObject);
/*    */   
/*    */   Http3DataFrame touch();
/*    */   
/*    */   Http3DataFrame retain(int paramInt);
/*    */   
/*    */   Http3DataFrame retain();
/*    */   
/*    */   Http3DataFrame replace(ByteBuf paramByteBuf);
/*    */   
/*    */   Http3DataFrame retainedDuplicate();
/*    */   
/*    */   Http3DataFrame duplicate();
/*    */   
/*    */   Http3DataFrame copy();
/*    */   
/*    */   default long type() {
/* 28 */     return 0L;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http3\Http3DataFrame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */