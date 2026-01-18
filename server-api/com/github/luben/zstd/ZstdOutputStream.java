/*     */ package com.github.luben.zstd;
/*     */ 
/*     */ import java.io.FilterOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
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
/*     */ public class ZstdOutputStream
/*     */   extends FilterOutputStream
/*     */ {
/*     */   private ZstdOutputStreamNoFinalizer inner;
/*     */   
/*     */   @Deprecated
/*     */   public ZstdOutputStream(OutputStream paramOutputStream, int paramInt, boolean paramBoolean1, boolean paramBoolean2) throws IOException {
/*  23 */     super(paramOutputStream);
/*  24 */     this.inner = new ZstdOutputStreamNoFinalizer(paramOutputStream, paramInt);
/*  25 */     this.inner.setCloseFrameOnFlush(paramBoolean1);
/*  26 */     this.inner.setChecksum(paramBoolean2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public ZstdOutputStream(OutputStream paramOutputStream, int paramInt, boolean paramBoolean) throws IOException {
/*  35 */     super(paramOutputStream);
/*  36 */     this.inner = new ZstdOutputStreamNoFinalizer(paramOutputStream, paramInt);
/*  37 */     this.inner.setCloseFrameOnFlush(paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZstdOutputStream(OutputStream paramOutputStream, int paramInt) throws IOException {
/*  46 */     this(paramOutputStream, NoPool.INSTANCE);
/*  47 */     this.inner.setLevel(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZstdOutputStream(OutputStream paramOutputStream) throws IOException {
/*  55 */     this(paramOutputStream, NoPool.INSTANCE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZstdOutputStream(OutputStream paramOutputStream, BufferPool paramBufferPool, int paramInt) throws IOException {
/*  64 */     this(paramOutputStream, paramBufferPool);
/*  65 */     this.inner.setLevel(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZstdOutputStream(OutputStream paramOutputStream, BufferPool paramBufferPool) throws IOException {
/*  74 */     super(paramOutputStream);
/*  75 */     this.inner = new ZstdOutputStreamNoFinalizer(paramOutputStream, paramBufferPool);
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
/*     */   
/*     */   protected void finalize() throws Throwable {
/*  94 */     close();
/*     */   }
/*     */   
/*     */   public static long recommendedCOutSize() {
/*  98 */     return ZstdOutputStreamNoFinalizer.recommendedCOutSize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZstdOutputStream setChecksum(boolean paramBoolean) throws IOException {
/* 107 */     this.inner.setChecksum(paramBoolean);
/* 108 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZstdOutputStream setLevel(int paramInt) throws IOException {
/* 117 */     this.inner.setLevel(paramInt);
/* 118 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZstdOutputStream setLong(int paramInt) throws IOException {
/* 127 */     this.inner.setLong(paramInt);
/* 128 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZstdOutputStream setWorkers(int paramInt) throws IOException {
/* 137 */     this.inner.setWorkers(paramInt);
/* 138 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZstdOutputStream setOverlapLog(int paramInt) throws IOException {
/* 148 */     this.inner.setOverlapLog(paramInt);
/* 149 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZstdOutputStream setJobSize(int paramInt) throws IOException {
/* 159 */     this.inner.setJobSize(paramInt);
/* 160 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZstdOutputStream setTargetLength(int paramInt) throws IOException {
/* 169 */     this.inner.setTargetLength(paramInt);
/* 170 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZstdOutputStream setMinMatch(int paramInt) throws IOException {
/* 179 */     this.inner.setMinMatch(paramInt);
/* 180 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZstdOutputStream setSearchLog(int paramInt) throws IOException {
/* 190 */     this.inner.setSearchLog(paramInt);
/* 191 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZstdOutputStream setChainLog(int paramInt) throws IOException {
/* 201 */     this.inner.setChainLog(paramInt);
/* 202 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZstdOutputStream setHashLog(int paramInt) throws IOException {
/* 211 */     this.inner.setHashLog(paramInt);
/* 212 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZstdOutputStream setWindowLog(int paramInt) throws IOException {
/* 221 */     this.inner.setWindowLog(paramInt);
/* 222 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZstdOutputStream setStrategy(int paramInt) throws IOException {
/* 231 */     this.inner.setStrategy(paramInt);
/* 232 */     return this;
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
/*     */   public ZstdOutputStream setCloseFrameOnFlush(boolean paramBoolean) {
/* 246 */     this.inner.setCloseFrameOnFlush(paramBoolean);
/* 247 */     return this;
/*     */   }
/*     */   
/*     */   public ZstdOutputStream setDict(byte[] paramArrayOfbyte) throws IOException {
/* 251 */     this.inner.setDict(paramArrayOfbyte);
/* 252 */     return this;
/*     */   }
/*     */   
/*     */   public ZstdOutputStream setDict(ZstdDictCompress paramZstdDictCompress) throws IOException {
/* 256 */     this.inner.setDict(paramZstdDictCompress);
/* 257 */     return this;
/*     */   }
/*     */   
/*     */   public void write(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
/* 261 */     this.inner.write(paramArrayOfbyte, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public void write(int paramInt) throws IOException {
/* 265 */     this.inner.write(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flush() throws IOException {
/* 272 */     this.inner.flush();
/*     */   }
/*     */   
/*     */   public void close() throws IOException {
/* 276 */     this.inner.close();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\github\luben\zstd\ZstdOutputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */