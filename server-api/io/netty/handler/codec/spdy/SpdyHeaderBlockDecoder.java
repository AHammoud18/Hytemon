/*    */ package io.netty.handler.codec.spdy;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.buffer.ByteBufAllocator;
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
/*    */ public abstract class SpdyHeaderBlockDecoder
/*    */ {
/*    */   static SpdyHeaderBlockDecoder newInstance(SpdyVersion spdyVersion, int maxHeaderSize) {
/* 30 */     return new SpdyHeaderBlockZlibDecoder(spdyVersion, maxHeaderSize);
/*    */   }
/*    */   
/*    */   abstract void decode(ByteBufAllocator paramByteBufAllocator, ByteBuf paramByteBuf, SpdyHeadersFrame paramSpdyHeadersFrame) throws Exception;
/*    */   
/*    */   abstract void endHeaderBlock(SpdyHeadersFrame paramSpdyHeadersFrame) throws Exception;
/*    */   
/*    */   abstract void end();
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\spdy\SpdyHeaderBlockDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */