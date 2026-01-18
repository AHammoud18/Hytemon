/*     */ package com.github.luben.zstd;
/*     */ 
/*     */ import com.github.luben.zstd.util.Native;
/*     */ import java.nio.ByteBuffer;
/*     */ 
/*     */ public class ZstdDictCompress
/*     */   extends SharedDictBase {
/*     */   static {
/*   9 */     Native.load();
/*     */   }
/*     */   
/*  12 */   private long nativePtr = 0L;
/*     */   
/*  14 */   private ByteBuffer sharedDict = null;
/*     */   
/*  16 */   private int level = Zstd.defaultCompressionLevel();
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
/*     */   public ByteBuffer getByReferenceBuffer() {
/*  28 */     return this.sharedDict;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZstdDictCompress(byte[] paramArrayOfbyte, int paramInt) {
/*  38 */     this(paramArrayOfbyte, 0, paramArrayOfbyte.length, paramInt);
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
/*     */   public ZstdDictCompress(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3) {
/*  50 */     this.level = paramInt3;
/*  51 */     if (paramArrayOfbyte.length - paramInt1 < 0) {
/*  52 */       throw new IllegalArgumentException("Dictionary buffer is to short");
/*     */     }
/*     */     
/*  55 */     init(paramArrayOfbyte, paramInt1, paramInt2, paramInt3);
/*     */     
/*  57 */     if (0L == this.nativePtr) {
/*  58 */       throw new IllegalStateException("ZSTD_createCDict failed");
/*     */     }
/*     */ 
/*     */     
/*  62 */     storeFence();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZstdDictCompress(ByteBuffer paramByteBuffer, int paramInt) {
/*  72 */     this(paramByteBuffer, paramInt, false);
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
/*     */   public ZstdDictCompress(ByteBuffer paramByteBuffer, int paramInt, boolean paramBoolean) {
/*  84 */     this.level = paramInt;
/*  85 */     int i = paramByteBuffer.limit() - paramByteBuffer.position();
/*  86 */     if (!paramByteBuffer.isDirect()) {
/*  87 */       throw new IllegalArgumentException("dict must be a direct buffer");
/*     */     }
/*  89 */     if (i < 0) {
/*  90 */       throw new IllegalArgumentException("dict cannot be empty.");
/*     */     }
/*  92 */     initDirect(paramByteBuffer, paramByteBuffer.position(), i, paramInt, paramBoolean ? 1 : 0);
/*     */     
/*  94 */     if (this.nativePtr == 0L) {
/*  95 */       throw new IllegalStateException("ZSTD_createCDict failed");
/*     */     }
/*  97 */     if (paramBoolean) {
/*  98 */       this.sharedDict = paramByteBuffer;
/*     */     }
/*     */ 
/*     */     
/* 102 */     storeFence();
/*     */   }
/*     */ 
/*     */   
/*     */   int level() {
/* 107 */     return this.level;
/*     */   }
/*     */ 
/*     */   
/*     */   void doClose() {
/* 112 */     if (this.nativePtr != 0L) {
/* 113 */       free();
/* 114 */       this.nativePtr = 0L;
/* 115 */       this.sharedDict = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private native void init(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3);
/*     */   
/*     */   private native void initDirect(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
/*     */   
/*     */   private native void free();
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\github\luben\zstd\ZstdDictCompress.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */