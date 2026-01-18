/*    */ package com.github.luben.zstd;
/*    */ 
/*    */ import com.github.luben.zstd.util.Native;
/*    */ import java.io.IOException;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ public class ZstdDirectBufferDecompressingStreamNoFinalizer
/*    */   extends BaseZstdBufferDecompressingStreamNoFinalizer {
/*    */   static {
/* 10 */     Native.load();
/*    */   }
/*    */   
/*    */   public ZstdDirectBufferDecompressingStreamNoFinalizer(ByteBuffer paramByteBuffer) {
/* 14 */     super(paramByteBuffer);
/* 15 */     if (!paramByteBuffer.isDirect()) {
/* 16 */       throw new IllegalArgumentException("Source buffer should be a direct buffer");
/*    */     }
/* 18 */     this.source = paramByteBuffer;
/* 19 */     this.stream = createDStream();
/* 20 */     initDStream(this.stream);
/*    */   }
/*    */ 
/*    */   
/*    */   public int read(ByteBuffer paramByteBuffer) throws IOException {
/* 25 */     if (!paramByteBuffer.isDirect()) {
/* 26 */       throw new IllegalArgumentException("Target buffer should be a direct buffer");
/*    */     }
/* 28 */     return readInternal(paramByteBuffer, true);
/*    */   }
/*    */ 
/*    */   
/*    */   long createDStream() {
/* 33 */     return createDStreamNative();
/*    */   }
/*    */ 
/*    */   
/*    */   long freeDStream(long paramLong) {
/* 38 */     return freeDStreamNative(paramLong);
/*    */   }
/*    */ 
/*    */   
/*    */   long initDStream(long paramLong) {
/* 43 */     return initDStreamNative(paramLong);
/*    */   }
/*    */ 
/*    */   
/*    */   long decompressStream(long paramLong, ByteBuffer paramByteBuffer1, int paramInt1, int paramInt2, ByteBuffer paramByteBuffer2, int paramInt3, int paramInt4) {
/* 48 */     return decompressStreamNative(paramLong, paramByteBuffer1, paramInt1, paramInt2, paramByteBuffer2, paramInt3, paramInt4);
/*    */   }
/*    */   
/*    */   public static int recommendedTargetBufferSize() {
/* 52 */     return (int)recommendedDOutSizeNative();
/*    */   }
/*    */   
/*    */   private static native long createDStreamNative();
/*    */   
/*    */   private static native long freeDStreamNative(long paramLong);
/*    */   
/*    */   private native long initDStreamNative(long paramLong);
/*    */   
/*    */   private native long decompressStreamNative(long paramLong, ByteBuffer paramByteBuffer1, int paramInt1, int paramInt2, ByteBuffer paramByteBuffer2, int paramInt3, int paramInt4);
/*    */   
/*    */   private static native long recommendedDOutSizeNative();
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\github\luben\zstd\ZstdDirectBufferDecompressingStreamNoFinalizer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */