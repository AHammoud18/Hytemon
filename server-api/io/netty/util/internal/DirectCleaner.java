/*    */ package io.netty.util.internal;
/*    */ 
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
/*    */ final class DirectCleaner
/*    */   implements Cleaner
/*    */ {
/*    */   public CleanableDirectBuffer allocate(int capacity) {
/* 23 */     return new CleanableDirectBufferImpl(PlatformDependent.allocateDirectNoCleaner(capacity));
/*    */   }
/*    */ 
/*    */   
/*    */   public void freeDirectBuffer(ByteBuffer buffer) {
/* 28 */     PlatformDependent.freeDirectNoCleaner(buffer);
/*    */   }
/*    */   
/*    */   CleanableDirectBuffer reallocate(CleanableDirectBuffer buffer, int capacity) {
/* 32 */     ByteBuffer newByteBuffer = PlatformDependent.reallocateDirectNoCleaner(buffer.buffer(), capacity);
/* 33 */     return new CleanableDirectBufferImpl(newByteBuffer);
/*    */   }
/*    */   
/*    */   private static final class CleanableDirectBufferImpl implements CleanableDirectBuffer {
/*    */     private final ByteBuffer buffer;
/*    */     
/*    */     private CleanableDirectBufferImpl(ByteBuffer buffer) {
/* 40 */       this.buffer = buffer;
/*    */     }
/*    */ 
/*    */     
/*    */     public ByteBuffer buffer() {
/* 45 */       return this.buffer;
/*    */     }
/*    */ 
/*    */     
/*    */     public void clean() {
/* 50 */       PlatformDependent.freeDirectNoCleaner(this.buffer);
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\DirectCleaner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */