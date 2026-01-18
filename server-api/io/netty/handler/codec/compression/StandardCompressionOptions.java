/*     */ package io.netty.handler.codec.compression;
/*     */ 
/*     */ import com.aayushatharva.brotli4j.encoder.Encoder;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class StandardCompressionOptions
/*     */ {
/*     */   public static BrotliOptions brotli() {
/*  36 */     return BrotliOptions.DEFAULT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static BrotliOptions brotli(Encoder.Parameters parameters) {
/*  48 */     return new BrotliOptions(parameters);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BrotliOptions brotli(int quality, int window, BrotliMode mode) {
/*  60 */     ObjectUtil.checkInRange(quality, 0, 11, "quality");
/*  61 */     ObjectUtil.checkInRange(window, 10, 24, "window");
/*  62 */     ObjectUtil.checkNotNull(mode, "mode");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  67 */     Encoder.Parameters parameters = (new Encoder.Parameters()).setQuality(quality).setWindow(window).setMode(mode.adapt());
/*  68 */     return new BrotliOptions(parameters);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ZstdOptions zstd() {
/*  77 */     return ZstdOptions.DEFAULT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ZstdOptions zstd(int compressionLevel, int blockSize, int maxEncodeSize) {
/*  88 */     return new ZstdOptions(compressionLevel, blockSize, maxEncodeSize);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SnappyOptions snappy() {
/*  95 */     return new SnappyOptions();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static GzipOptions gzip() {
/* 103 */     return GzipOptions.DEFAULT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static GzipOptions gzip(int compressionLevel, int windowBits, int memLevel) {
/* 122 */     return new GzipOptions(compressionLevel, windowBits, memLevel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DeflateOptions deflate() {
/* 130 */     return DeflateOptions.DEFAULT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DeflateOptions deflate(int compressionLevel, int windowBits, int memLevel) {
/* 149 */     return new DeflateOptions(compressionLevel, windowBits, memLevel);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\compression\StandardCompressionOptions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */