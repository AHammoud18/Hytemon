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
/*    */ 
/*    */ 
/*    */ public abstract class SpdyHeaderBlockEncoder
/*    */ {
/*    */   static SpdyHeaderBlockEncoder newInstance(SpdyVersion version, int compressionLevel, int windowBits, int memLevel) {
/* 32 */     return new SpdyHeaderBlockZlibEncoder(version, compressionLevel);
/*    */   }
/*    */   
/*    */   abstract ByteBuf encode(ByteBufAllocator paramByteBufAllocator, SpdyHeadersFrame paramSpdyHeadersFrame) throws Exception;
/*    */   
/*    */   abstract void end();
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\spdy\SpdyHeaderBlockEncoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */