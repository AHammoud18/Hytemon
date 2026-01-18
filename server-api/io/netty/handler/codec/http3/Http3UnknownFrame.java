/*    */ package io.netty.handler.codec.http3;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.buffer.ByteBufHolder;
/*    */ import io.netty.util.ReferenceCounted;
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
/*    */ public interface Http3UnknownFrame
/*    */   extends Http3RequestStreamFrame, Http3PushStreamFrame, Http3ControlStreamFrame, ByteBufHolder
/*    */ {
/*    */   Http3UnknownFrame touch(Object paramObject);
/*    */   
/*    */   Http3UnknownFrame touch();
/*    */   
/*    */   Http3UnknownFrame retain(int paramInt);
/*    */   
/*    */   Http3UnknownFrame retain();
/*    */   
/*    */   Http3UnknownFrame replace(ByteBuf paramByteBuf);
/*    */   
/*    */   Http3UnknownFrame retainedDuplicate();
/*    */   
/*    */   Http3UnknownFrame duplicate();
/*    */   
/*    */   Http3UnknownFrame copy();
/*    */   
/*    */   default long length() {
/* 41 */     return content().readableBytes();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http3\Http3UnknownFrame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */