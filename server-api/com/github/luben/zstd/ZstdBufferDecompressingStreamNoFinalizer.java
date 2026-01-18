/*    */ package com.github.luben.zstd;
/*    */ 
/*    */ import com.github.luben.zstd.util.Native;
/*    */ import java.io.IOException;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ public class ZstdBufferDecompressingStreamNoFinalizer
/*    */   extends BaseZstdBufferDecompressingStreamNoFinalizer {
/*    */   static {
/* 10 */     Native.load();
/*    */   }
/*    */   
/*    */   public ZstdBufferDecompressingStreamNoFinalizer(ByteBuffer paramByteBuffer) {
/* 14 */     super(paramByteBuffer);
/* 15 */     if (paramByteBuffer.isDirect()) {
/* 16 */       throw new IllegalArgumentException("Source buffer should be a non-direct buffer");
/*    */     }
/* 18 */     this.stream = createDStream();
/* 19 */     initDStream(this.stream);
/*    */   }
/*    */ 
/*    */   
/*    */   public int read(ByteBuffer paramByteBuffer) throws IOException {
/* 24 */     if (paramByteBuffer.isDirect()) {
/* 25 */       throw new IllegalArgumentException("Target buffer should be a non-direct buffer");
/*    */     }
/* 27 */     return readInternal(paramByteBuffer, false);
/*    */   }
/*    */ 
/*    */   
/*    */   long createDStream() {
/* 32 */     return createDStreamNative();
/*    */   }
/*    */ 
/*    */   
/*    */   long freeDStream(long paramLong) {
/* 37 */     return freeDStreamNative(paramLong);
/*    */   }
/*    */ 
/*    */   
/*    */   long initDStream(long paramLong) {
/* 42 */     return initDStreamNative(paramLong);
/*    */   }
/*    */ 
/*    */   
/*    */   long decompressStream(long paramLong, ByteBuffer paramByteBuffer1, int paramInt1, int paramInt2, ByteBuffer paramByteBuffer2, int paramInt3, int paramInt4) {
/* 47 */     if (!paramByteBuffer2.hasArray()) {
/* 48 */       throw new IllegalArgumentException("provided source ByteBuffer lacks array");
/*    */     }
/* 50 */     if (!paramByteBuffer1.hasArray()) {
/* 51 */       throw new IllegalArgumentException("provided destination ByteBuffer lacks array");
/*    */     }
/* 53 */     byte[] arrayOfByte1 = paramByteBuffer1.array();
/* 54 */     byte[] arrayOfByte2 = paramByteBuffer2.array();
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 59 */     return decompressStreamNative(paramLong, arrayOfByte1, paramInt1 + paramByteBuffer1.arrayOffset(), paramInt2, arrayOfByte2, paramInt3 + paramByteBuffer2.arrayOffset(), paramInt4);
/*    */   }
/*    */   
/*    */   public static int recommendedTargetBufferSize() {
/* 63 */     return (int)recommendedDOutSizeNative();
/*    */   }
/*    */   
/*    */   private native long createDStreamNative();
/*    */   
/*    */   private native long freeDStreamNative(long paramLong);
/*    */   
/*    */   private native long initDStreamNative(long paramLong);
/*    */   
/*    */   private native long decompressStreamNative(long paramLong, byte[] paramArrayOfbyte1, int paramInt1, int paramInt2, byte[] paramArrayOfbyte2, int paramInt3, int paramInt4);
/*    */   
/*    */   private static native long recommendedDOutSizeNative();
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\github\luben\zstd\ZstdBufferDecompressingStreamNoFinalizer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */