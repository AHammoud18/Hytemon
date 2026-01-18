/*    */ package com.github.luben.zstd;
/*    */ 
/*    */ import java.lang.ref.SoftReference;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.util.concurrent.ConcurrentLinkedQueue;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RecyclingBufferPool
/*    */   implements BufferPool
/*    */ {
/* 14 */   public static final BufferPool INSTANCE = new RecyclingBufferPool();
/*    */   
/* 16 */   private static final int buffSize = Math.max(Math.max(
/* 17 */         (int)ZstdOutputStreamNoFinalizer.recommendedCOutSize(), 
/* 18 */         (int)ZstdInputStreamNoFinalizer.recommendedDInSize()), 
/* 19 */       (int)ZstdInputStreamNoFinalizer.recommendedDOutSize());
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 24 */   private final ConcurrentLinkedQueue<SoftReference<ByteBuffer>> pool = new ConcurrentLinkedQueue<>();
/*    */ 
/*    */ 
/*    */   
/*    */   public ByteBuffer get(int paramInt) {
/* 29 */     if (paramInt > buffSize) {
/* 30 */       throw new RuntimeException("Unsupported buffer size: " + paramInt + ". Supported buffer sizes: " + buffSize + " or smaller.");
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     while (true) {
/* 39 */       SoftReference<ByteBuffer> softReference = this.pool.poll();
/*    */       
/* 41 */       if (softReference == null) {
/* 42 */         return ByteBuffer.allocate(buffSize);
/*    */       }
/* 44 */       ByteBuffer byteBuffer = softReference.get();
/* 45 */       if (byteBuffer != null) {
/* 46 */         return byteBuffer;
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void release(ByteBuffer paramByteBuffer) {
/* 53 */     if (paramByteBuffer.capacity() >= buffSize) {
/* 54 */       paramByteBuffer.clear();
/* 55 */       this.pool.add(new SoftReference<>(paramByteBuffer));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\github\luben\zstd\RecyclingBufferPool.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */