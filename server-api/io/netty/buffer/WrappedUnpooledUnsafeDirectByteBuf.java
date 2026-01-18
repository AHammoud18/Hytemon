/*    */ package io.netty.buffer;
/*    */ 
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
/*    */ final class WrappedUnpooledUnsafeDirectByteBuf
/*    */   extends UnpooledUnsafeDirectByteBuf
/*    */ {
/*    */   WrappedUnpooledUnsafeDirectByteBuf(ByteBufAllocator alloc, long memoryAddress, int size, boolean doFree) {
/* 25 */     super(alloc, PlatformDependent.directBuffer(memoryAddress, size), size, doFree);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void freeDirect(ByteBuffer buffer) {
/* 30 */     PlatformDependent.freeMemory(this.memoryAddress);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\buffer\WrappedUnpooledUnsafeDirectByteBuf.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */