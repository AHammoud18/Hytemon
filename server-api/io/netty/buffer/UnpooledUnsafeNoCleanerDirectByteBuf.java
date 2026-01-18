/*    */ package io.netty.buffer;
/*    */ 
/*    */ import io.netty.util.internal.CleanableDirectBuffer;
/*    */ import io.netty.util.internal.PlatformDependent;
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
/*    */ 
/*    */ class UnpooledUnsafeNoCleanerDirectByteBuf
/*    */   extends UnpooledUnsafeDirectByteBuf
/*    */ {
/*    */   UnpooledUnsafeNoCleanerDirectByteBuf(ByteBufAllocator alloc, int initialCapacity, int maxCapacity, boolean allowSectionedInternalNioBufferAccess) {
/* 26 */     super(alloc, initialCapacity, maxCapacity, allowSectionedInternalNioBufferAccess);
/*    */   }
/*    */ 
/*    */   
/*    */   protected CleanableDirectBuffer allocateDirectBuffer(int capacity) {
/* 31 */     return PlatformDependent.allocateDirectBufferNoCleaner(capacity);
/*    */   }
/*    */ 
/*    */   
/*    */   protected ByteBuffer allocateDirect(int initialCapacity) {
/* 36 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   CleanableDirectBuffer reallocateDirect(CleanableDirectBuffer oldBuffer, int initialCapacity) {
/* 40 */     return PlatformDependent.reallocateDirectBufferNoCleaner(oldBuffer, initialCapacity);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void freeDirect(ByteBuffer buffer) {
/* 45 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuf capacity(int newCapacity) {
/* 50 */     checkNewCapacity(newCapacity);
/*    */     
/* 52 */     int oldCapacity = capacity();
/* 53 */     if (newCapacity == oldCapacity) {
/* 54 */       return this;
/*    */     }
/*    */     
/* 57 */     trimIndicesToCapacity(newCapacity);
/* 58 */     setByteBuffer(reallocateDirect(this.cleanable, newCapacity), false);
/* 59 */     return this;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\buffer\UnpooledUnsafeNoCleanerDirectByteBuf.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */