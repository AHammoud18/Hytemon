/*    */ package io.netty.handler.ssl;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.buffer.ByteBufAllocator;
/*    */ import io.netty.buffer.ByteBufUtil;
/*    */ import io.netty.channel.AbstractCoalescingBufferQueue;
/*    */ import io.netty.channel.Channel;
/*    */ import io.netty.util.internal.PlatformDependent;
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
/*    */ abstract class SslHandlerCoalescingBufferQueue
/*    */   extends AbstractCoalescingBufferQueue
/*    */ {
/*    */   private final boolean wantsDirectBuffer;
/*    */   
/*    */   SslHandlerCoalescingBufferQueue(Channel channel, int initSize, boolean wantsDirectBuffer) {
/* 37 */     super(channel, initSize);
/* 38 */     this.wantsDirectBuffer = wantsDirectBuffer;
/*    */   }
/*    */ 
/*    */   
/*    */   protected abstract int wrapDataSize();
/*    */   
/*    */   protected ByteBuf compose(ByteBufAllocator alloc, ByteBuf cumulation, ByteBuf next) {
/* 45 */     return attemptCopyToCumulation(cumulation, next, wrapDataSize()) ? cumulation : 
/* 46 */       copyAndCompose(alloc, cumulation, next);
/*    */   }
/*    */ 
/*    */   
/*    */   protected ByteBuf composeFirst(ByteBufAllocator allocator, ByteBuf first, int bufferSize) {
/*    */     ByteBuf newFirst;
/* 52 */     if (this.wantsDirectBuffer) {
/* 53 */       newFirst = allocator.directBuffer(bufferSize);
/*    */     } else {
/* 55 */       newFirst = allocator.heapBuffer(bufferSize);
/*    */     } 
/*    */     try {
/* 58 */       newFirst.writeBytes(first);
/* 59 */     } catch (Throwable cause) {
/* 60 */       newFirst.release();
/* 61 */       PlatformDependent.throwException(cause);
/*    */     } 
/* 63 */     assert !first.isReadable();
/* 64 */     first.release();
/* 65 */     return newFirst;
/*    */   }
/*    */ 
/*    */   
/*    */   protected ByteBuf removeEmptyValue() {
/* 70 */     return null;
/*    */   }
/*    */   
/*    */   private static boolean attemptCopyToCumulation(ByteBuf cumulation, ByteBuf next, int wrapDataSize) {
/* 74 */     int inReadableBytes = next.readableBytes();
/*    */     
/* 76 */     if (inReadableBytes == 0) {
/* 77 */       next.release();
/* 78 */       return true;
/*    */     } 
/* 80 */     int cumulationCapacity = cumulation.capacity();
/* 81 */     if (wrapDataSize - cumulation.readableBytes() >= inReadableBytes && ((cumulation
/*    */ 
/*    */ 
/*    */       
/* 85 */       .isWritable(inReadableBytes) && cumulationCapacity >= wrapDataSize) || (cumulationCapacity < wrapDataSize && 
/*    */       
/* 87 */       ByteBufUtil.ensureWritableSuccess(cumulation.ensureWritable(inReadableBytes, false))))) {
/* 88 */       cumulation.writeBytes(next);
/* 89 */       next.release();
/* 90 */       return true;
/*    */     } 
/* 92 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ssl\SslHandlerCoalescingBufferQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */