/*     */ package com.github.luben.zstd;
/*     */ 
/*     */ import com.github.luben.zstd.util.Native;
/*     */ import java.nio.ByteBuffer;
/*     */ 
/*     */ public class ZstdDictDecompress
/*     */   extends SharedDictBase {
/*     */   static {
/*   9 */     Native.load();
/*     */   }
/*     */   
/*  12 */   private long nativePtr = 0L;
/*     */   
/*  14 */   private ByteBuffer sharedDict = null;
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
/*  26 */     return this.sharedDict;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZstdDictDecompress(byte[] paramArrayOfbyte) {
/*  35 */     this(paramArrayOfbyte, 0, paramArrayOfbyte.length);
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
/*     */   public ZstdDictDecompress(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/*  47 */     init(paramArrayOfbyte, paramInt1, paramInt2);
/*     */     
/*  49 */     if (this.nativePtr == 0L) {
/*  50 */       throw new IllegalStateException("ZSTD_createDDict failed");
/*     */     }
/*     */ 
/*     */     
/*  54 */     storeFence();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZstdDictDecompress(ByteBuffer paramByteBuffer) {
/*  64 */     this(paramByteBuffer, false);
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
/*     */   public ZstdDictDecompress(ByteBuffer paramByteBuffer, boolean paramBoolean) {
/*  76 */     int i = paramByteBuffer.limit() - paramByteBuffer.position();
/*  77 */     if (!paramByteBuffer.isDirect()) {
/*  78 */       throw new IllegalArgumentException("dict must be a direct buffer");
/*     */     }
/*  80 */     if (i < 0) {
/*  81 */       throw new IllegalArgumentException("dict cannot be empty.");
/*     */     }
/*  83 */     initDirect(paramByteBuffer, paramByteBuffer.position(), i, paramBoolean ? 1 : 0);
/*     */     
/*  85 */     if (this.nativePtr == 0L) {
/*  86 */       throw new IllegalStateException("ZSTD_createDDict failed");
/*     */     }
/*  88 */     if (paramBoolean) {
/*  89 */       this.sharedDict = paramByteBuffer;
/*     */     }
/*     */ 
/*     */     
/*  93 */     storeFence();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void doClose() {
/*  99 */     if (this.nativePtr != 0L) {
/* 100 */       free();
/* 101 */       this.nativePtr = 0L;
/* 102 */       this.sharedDict = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private native void init(byte[] paramArrayOfbyte, int paramInt1, int paramInt2);
/*     */   
/*     */   private native void initDirect(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2, int paramInt3);
/*     */   
/*     */   private native void free();
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\github\luben\zstd\ZstdDictDecompress.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */