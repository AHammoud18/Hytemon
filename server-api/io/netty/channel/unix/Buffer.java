/*    */ package io.netty.channel.unix;
/*    */ 
/*    */ import io.netty.util.internal.CleanableDirectBuffer;
/*    */ import io.netty.util.internal.PlatformDependent;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.nio.ByteOrder;
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
/*    */ public final class Buffer
/*    */ {
/*    */   @Deprecated
/*    */   public static void free(ByteBuffer buffer) {
/* 36 */     PlatformDependent.freeDirectBuffer(buffer);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public static ByteBuffer allocateDirectWithNativeOrder(int capacity) {
/* 45 */     return ByteBuffer.allocateDirect(capacity).order(
/* 46 */         PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static CleanableDirectBuffer allocateDirectBufferWithNativeOrder(int capacity) {
/* 54 */     CleanableDirectBuffer cleanableDirectBuffer = PlatformDependent.allocateDirect(capacity);
/* 55 */     cleanableDirectBuffer.buffer().order(
/* 56 */         PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
/* 57 */     return cleanableDirectBuffer;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static long memoryAddress(ByteBuffer buffer) {
/* 64 */     assert buffer.isDirect();
/* 65 */     if (PlatformDependent.hasUnsafe()) {
/* 66 */       return PlatformDependent.directBufferAddress(buffer);
/*    */     }
/* 68 */     return memoryAddress0(buffer);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static int addressSize() {
/* 75 */     if (PlatformDependent.hasUnsafe()) {
/* 76 */       return PlatformDependent.addressSize();
/*    */     }
/* 78 */     return addressSize0();
/*    */   }
/*    */   
/*    */   private static native int addressSize0();
/*    */   
/*    */   private static native long memoryAddress0(ByteBuffer paramByteBuffer);
/*    */   
/*    */   public static ByteBuffer wrapMemoryAddressWithNativeOrder(long memoryAddress, int capacity) {
/* 86 */     return wrapMemoryAddress(memoryAddress, capacity).order(ByteOrder.nativeOrder());
/*    */   }
/*    */   
/*    */   public static native ByteBuffer wrapMemoryAddress(long paramLong, int paramInt);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channe\\unix\Buffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */