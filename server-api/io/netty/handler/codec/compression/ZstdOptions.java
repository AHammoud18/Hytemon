/*    */ package io.netty.handler.codec.compression;
/*    */ 
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ZstdOptions
/*    */   implements CompressionOptions
/*    */ {
/*    */   private final int blockSize;
/*    */   private final int compressionLevel;
/*    */   private final int maxEncodeSize;
/* 41 */   static final ZstdOptions DEFAULT = new ZstdOptions(ZstdConstants.DEFAULT_COMPRESSION_LEVEL, 65536, 2147483647);
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
/*    */   ZstdOptions(int compressionLevel, int blockSize, int maxEncodeSize) {
/* 55 */     if (!Zstd.isAvailable()) {
/* 56 */       throw new IllegalStateException("zstd-jni is not available", Zstd.cause());
/*    */     }
/*    */     
/* 59 */     this.compressionLevel = ObjectUtil.checkInRange(compressionLevel, ZstdConstants.MIN_COMPRESSION_LEVEL, ZstdConstants.MAX_COMPRESSION_LEVEL, "compressionLevel");
/*    */     
/* 61 */     this.blockSize = ObjectUtil.checkPositive(blockSize, "blockSize");
/* 62 */     this.maxEncodeSize = ObjectUtil.checkPositive(maxEncodeSize, "maxEncodeSize");
/*    */   }
/*    */   
/*    */   public int compressionLevel() {
/* 66 */     return this.compressionLevel;
/*    */   }
/*    */   
/*    */   public int blockSize() {
/* 70 */     return this.blockSize;
/*    */   }
/*    */   
/*    */   public int maxEncodeSize() {
/* 74 */     return this.maxEncodeSize;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\compression\ZstdOptions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */