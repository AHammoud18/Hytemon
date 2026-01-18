/*    */ package io.netty.handler.codec.compression;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.handler.codec.ByteToMessageDecoder;
/*    */ import io.netty.util.internal.ObjectUtil;
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
/*    */ public abstract class ZlibDecoder
/*    */   extends ByteToMessageDecoder
/*    */ {
/*    */   protected final int maxAllocation;
/*    */   
/*    */   public ZlibDecoder() {
/* 39 */     this(0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ZlibDecoder(int maxAllocation) {
/* 49 */     this.maxAllocation = ObjectUtil.checkPositiveOrZero(maxAllocation, "maxAllocation");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract boolean isClosed();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ByteBuf prepareDecompressBuffer(ChannelHandlerContext ctx, ByteBuf buffer, int preferredSize) {
/* 63 */     if (buffer == null) {
/* 64 */       if (this.maxAllocation == 0) {
/* 65 */         return ctx.alloc().heapBuffer(preferredSize);
/*    */       }
/*    */       
/* 68 */       return ctx.alloc().heapBuffer(Math.min(preferredSize, this.maxAllocation), this.maxAllocation);
/*    */     } 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 74 */     if (buffer.ensureWritable(preferredSize, true) == 1) {
/*    */ 
/*    */ 
/*    */       
/* 78 */       decompressionBufferExhausted(buffer.duplicate());
/* 79 */       buffer.skipBytes(buffer.readableBytes());
/* 80 */       throw new DecompressionException("Decompression buffer has reached maximum size: " + buffer.maxCapacity());
/*    */     } 
/*    */     
/* 83 */     return buffer;
/*    */   }
/*    */   
/*    */   protected void decompressionBufferExhausted(ByteBuf buffer) {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\compression\ZlibDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */