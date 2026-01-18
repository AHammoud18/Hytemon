/*     */ package com.github.luben.zstd;
/*     */ 
/*     */ import java.io.FilterInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
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
/*     */ public class ZstdInputStream
/*     */   extends FilterInputStream
/*     */ {
/*     */   private ZstdInputStreamNoFinalizer inner;
/*     */   
/*     */   public ZstdInputStream(InputStream paramInputStream) throws IOException {
/*  23 */     super(paramInputStream);
/*  24 */     this.inner = new ZstdInputStreamNoFinalizer(paramInputStream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZstdInputStream(InputStream paramInputStream, BufferPool paramBufferPool) throws IOException {
/*  33 */     super(paramInputStream);
/*  34 */     this.inner = new ZstdInputStreamNoFinalizer(paramInputStream, paramBufferPool);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void setFinalize(boolean paramBoolean) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void finalize() throws Throwable {
/*  52 */     close();
/*     */   }
/*     */   
/*     */   public static long recommendedDInSize() {
/*  56 */     return ZstdInputStreamNoFinalizer.recommendedDInSize();
/*     */   }
/*     */   
/*     */   public static long recommendedDOutSize() {
/*  60 */     return ZstdInputStreamNoFinalizer.recommendedDOutSize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZstdInputStream setContinuous(boolean paramBoolean) {
/*  69 */     this.inner.setContinuous(paramBoolean);
/*  70 */     return this;
/*     */   }
/*     */   
/*     */   public boolean getContinuous() {
/*  74 */     return this.inner.getContinuous();
/*     */   }
/*     */   
/*     */   public ZstdInputStream setDict(byte[] paramArrayOfbyte) throws IOException {
/*  78 */     this.inner.setDict(paramArrayOfbyte);
/*  79 */     return this;
/*     */   }
/*     */   public ZstdInputStream setDict(ZstdDictDecompress paramZstdDictDecompress) throws IOException {
/*  82 */     this.inner.setDict(paramZstdDictDecompress);
/*  83 */     return this;
/*     */   }
/*     */   
/*     */   public ZstdInputStream setLongMax(int paramInt) throws IOException {
/*  87 */     this.inner.setLongMax(paramInt);
/*  88 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZstdInputStream setRefMultipleDDicts(boolean paramBoolean) throws IOException {
/*  98 */     this.inner.setRefMultipleDDicts(paramBoolean);
/*  99 */     return this;
/*     */   }
/*     */   
/*     */   public int read(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
/* 103 */     return this.inner.read(paramArrayOfbyte, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public int read() throws IOException {
/* 107 */     return this.inner.read();
/*     */   }
/*     */   
/*     */   public int available() throws IOException {
/* 111 */     return this.inner.available();
/*     */   }
/*     */ 
/*     */   
/*     */   public long skip(long paramLong) throws IOException {
/* 116 */     return this.inner.skip(paramLong);
/*     */   }
/*     */   
/*     */   public boolean markSupported() {
/* 120 */     return this.inner.markSupported();
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 125 */     this.inner.close();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\github\luben\zstd\ZstdInputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */