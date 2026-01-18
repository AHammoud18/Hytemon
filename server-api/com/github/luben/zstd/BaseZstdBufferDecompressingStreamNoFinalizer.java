/*     */ package com.github.luben.zstd;
/*     */ 
/*     */ import java.io.Closeable;
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class BaseZstdBufferDecompressingStreamNoFinalizer
/*     */   implements Closeable
/*     */ {
/*     */   protected long stream;
/*     */   protected ByteBuffer source;
/*     */   protected boolean closed = false;
/*     */   private boolean finishedFrame = false;
/*     */   private boolean streamEnd = false;
/*     */   private int consumed;
/*     */   private int produced;
/*     */   
/*     */   BaseZstdBufferDecompressingStreamNoFinalizer(ByteBuffer paramByteBuffer) {
/*  23 */     this.source = paramByteBuffer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ByteBuffer refill(ByteBuffer paramByteBuffer) {
/*  33 */     return paramByteBuffer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasRemaining() {
/*  40 */     return (!this.streamEnd && (this.source.hasRemaining() || !this.finishedFrame));
/*     */   }
/*     */   
/*     */   public BaseZstdBufferDecompressingStreamNoFinalizer setDict(byte[] paramArrayOfbyte) throws IOException {
/*  44 */     long l = Zstd.loadDictDecompress(this.stream, paramArrayOfbyte, paramArrayOfbyte.length);
/*  45 */     if (Zstd.isError(l)) {
/*  46 */       throw new ZstdIOException(l);
/*     */     }
/*  48 */     return this;
/*     */   }
/*     */   
/*     */   public BaseZstdBufferDecompressingStreamNoFinalizer setDict(ZstdDictDecompress paramZstdDictDecompress) throws IOException {
/*  52 */     paramZstdDictDecompress.acquireSharedLock();
/*     */     try {
/*  54 */       long l = Zstd.loadFastDictDecompress(this.stream, paramZstdDictDecompress);
/*  55 */       if (Zstd.isError(l)) {
/*  56 */         throw new ZstdIOException(l);
/*     */       }
/*     */     } finally {
/*  59 */       paramZstdDictDecompress.releaseSharedLock();
/*     */     } 
/*  61 */     return this;
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
/*     */   public BaseZstdBufferDecompressingStreamNoFinalizer setLongMax(int paramInt) throws IOException {
/*  74 */     long l = Zstd.setDecompressionLongMax(this.stream, paramInt);
/*  75 */     if (Zstd.isError(l)) {
/*  76 */       throw new ZstdIOException(l);
/*     */     }
/*  78 */     return this;
/*     */   }
/*     */   
/*     */   int readInternal(ByteBuffer paramByteBuffer, boolean paramBoolean) throws IOException {
/*  82 */     if (this.closed) {
/*  83 */       throw new IOException("Stream closed");
/*     */     }
/*  85 */     if (this.streamEnd) {
/*  86 */       return 0;
/*     */     }
/*     */     
/*  89 */     long l = decompressStream(this.stream, paramByteBuffer, paramByteBuffer.position(), paramByteBuffer.remaining(), this.source, this.source.position(), this.source.remaining());
/*  90 */     if (Zstd.isError(l)) {
/*  91 */       throw new ZstdIOException(l);
/*     */     }
/*     */     
/*  94 */     this.source.position(this.source.position() + this.consumed);
/*  95 */     paramByteBuffer.position(paramByteBuffer.position() + this.produced);
/*     */     
/*  97 */     if (!this.source.hasRemaining()) {
/*  98 */       this.source = refill(this.source);
/*  99 */       if (!paramBoolean && this.source.isDirect()) {
/* 100 */         throw new IllegalArgumentException("Source buffer should be a non-direct buffer");
/*     */       }
/* 102 */       if (paramBoolean && !this.source.isDirect()) {
/* 103 */         throw new IllegalArgumentException("Source buffer should be a direct buffer");
/*     */       }
/*     */     } 
/*     */     
/* 107 */     this.finishedFrame = (l == 0L);
/* 108 */     if (this.finishedFrame)
/*     */     {
/* 110 */       this.streamEnd = !this.source.hasRemaining();
/*     */     }
/*     */     
/* 113 */     return this.produced;
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() {
/* 118 */     if (!this.closed)
/*     */       try {
/* 120 */         freeDStream(this.stream);
/*     */       } finally {
/* 122 */         this.closed = true;
/* 123 */         this.source = null;
/*     */       }  
/*     */   }
/*     */   
/*     */   public abstract int read(ByteBuffer paramByteBuffer) throws IOException;
/*     */   
/*     */   abstract long createDStream();
/*     */   
/*     */   abstract long freeDStream(long paramLong);
/*     */   
/*     */   abstract long initDStream(long paramLong);
/*     */   
/*     */   abstract long decompressStream(long paramLong, ByteBuffer paramByteBuffer1, int paramInt1, int paramInt2, ByteBuffer paramByteBuffer2, int paramInt3, int paramInt4);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\github\luben\zstd\BaseZstdBufferDecompressingStreamNoFinalizer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */