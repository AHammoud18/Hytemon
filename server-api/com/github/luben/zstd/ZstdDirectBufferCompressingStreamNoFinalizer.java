/*     */ package com.github.luben.zstd;
/*     */ 
/*     */ import com.github.luben.zstd.util.Native;
/*     */ import java.io.Closeable;
/*     */ import java.io.Flushable;
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
/*     */ 
/*     */ public class ZstdDirectBufferCompressingStreamNoFinalizer implements Closeable, Flushable {
/*     */   private ByteBuffer target;
/*     */   
/*     */   static {
/*  13 */     Native.load();
/*     */   }
/*     */   
/*     */   private final long stream;
/*     */   private int consumed;
/*     */   private int produced;
/*     */   private boolean closed;
/*     */   private boolean initialized;
/*     */   private int level;
/*     */   private byte[] dict;
/*     */   private ZstdDictCompress fastDict;
/*     */   
/*     */   protected ByteBuffer flushBuffer(ByteBuffer paramByteBuffer) throws IOException {
/*  26 */     return paramByteBuffer;
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
/*     */   public ZstdDirectBufferCompressingStreamNoFinalizer(ByteBuffer paramByteBuffer, int paramInt) throws IOException {
/*  40 */     this.consumed = 0;
/*  41 */     this.produced = 0;
/*  42 */     this.closed = false;
/*  43 */     this.initialized = false;
/*  44 */     this.level = Zstd.defaultCompressionLevel();
/*  45 */     this.dict = null;
/*  46 */     this.fastDict = null;
/*     */     if (!paramByteBuffer.isDirect()) {
/*     */       throw new IllegalArgumentException("Target buffer should be a direct buffer");
/*     */     }
/*     */     this.target = paramByteBuffer;
/*     */     this.level = paramInt;
/*     */     this.stream = createCStream();
/*     */   }
/*     */   
/*     */   public static int recommendedOutputBufferSize() {
/*     */     return (int)recommendedCOutSize();
/*     */   }
/*     */   
/*     */   public ZstdDirectBufferCompressingStreamNoFinalizer setDict(byte[] paramArrayOfbyte) {
/*  60 */     if (this.initialized) {
/*  61 */       throw new IllegalStateException("Change of parameter on initialized stream");
/*     */     }
/*  63 */     this.dict = paramArrayOfbyte;
/*  64 */     this.fastDict = null;
/*  65 */     return this;
/*     */   }
/*     */   
/*     */   public ZstdDirectBufferCompressingStreamNoFinalizer setDict(ZstdDictCompress paramZstdDictCompress) {
/*  69 */     if (this.initialized) {
/*  70 */       throw new IllegalStateException("Change of parameter on initialized stream");
/*     */     }
/*  72 */     this.dict = null;
/*  73 */     this.fastDict = paramZstdDictCompress;
/*  74 */     return this;
/*     */   }
/*     */   
/*     */   public void compress(ByteBuffer paramByteBuffer) throws IOException {
/*  78 */     if (!paramByteBuffer.isDirect()) {
/*  79 */       throw new IllegalArgumentException("Source buffer should be a direct buffer");
/*     */     }
/*  81 */     if (this.closed) {
/*  82 */       throw new IOException("Stream closed");
/*     */     }
/*  84 */     if (!this.initialized) {
/*  85 */       long l = 0L;
/*  86 */       ZstdDictCompress zstdDictCompress = this.fastDict;
/*  87 */       if (zstdDictCompress != null) {
/*  88 */         zstdDictCompress.acquireSharedLock();
/*     */         try {
/*  90 */           l = initCStreamWithFastDict(this.stream, zstdDictCompress);
/*     */         } finally {
/*  92 */           zstdDictCompress.releaseSharedLock();
/*     */         } 
/*  94 */       } else if (this.dict != null) {
/*  95 */         l = initCStreamWithDict(this.stream, this.dict, this.dict.length, this.level);
/*     */       } else {
/*  97 */         l = initCStream(this.stream, this.level);
/*     */       } 
/*  99 */       if (Zstd.isError(l)) {
/* 100 */         throw new ZstdIOException(l);
/*     */       }
/* 102 */       this.initialized = true;
/*     */     } 
/* 104 */     while (paramByteBuffer.hasRemaining()) {
/* 105 */       if (!this.target.hasRemaining()) {
/* 106 */         this.target = flushBuffer(this.target);
/* 107 */         if (!this.target.isDirect()) {
/* 108 */           throw new IllegalArgumentException("Target buffer should be a direct buffer");
/*     */         }
/* 110 */         if (!this.target.hasRemaining()) {
/* 111 */           throw new IOException("The target buffer has no more space, even after flushing, and there are still bytes to compress");
/*     */         }
/*     */       } 
/* 114 */       long l = compressDirectByteBuffer(this.stream, this.target, this.target.position(), this.target.remaining(), paramByteBuffer, paramByteBuffer.position(), paramByteBuffer.remaining());
/* 115 */       if (Zstd.isError(l)) {
/* 116 */         throw new ZstdIOException(l);
/*     */       }
/* 118 */       this.target.position(this.target.position() + this.produced);
/* 119 */       paramByteBuffer.position(paramByteBuffer.position() + this.consumed);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void flush() throws IOException {
/* 125 */     if (this.closed) {
/* 126 */       throw new IOException("Already closed");
/*     */     }
/* 128 */     if (this.initialized) {
/*     */       long l;
/*     */       do {
/* 131 */         l = flushStream(this.stream, this.target, this.target.position(), this.target.remaining());
/* 132 */         if (Zstd.isError(l)) {
/* 133 */           throw new ZstdIOException(l);
/*     */         }
/* 135 */         this.target.position(this.target.position() + this.produced);
/* 136 */         this.target = flushBuffer(this.target);
/* 137 */         if (!this.target.isDirect()) {
/* 138 */           throw new IllegalArgumentException("Target buffer should be a direct buffer");
/*     */         }
/* 140 */         if (l > 0L && !this.target.hasRemaining())
/*     */         {
/* 142 */           throw new IOException("The target buffer has no more space, even after flushing, and there are still bytes to compress");
/*     */         }
/*     */       }
/* 145 */       while (l > 0L);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 151 */     if (!this.closed)
/*     */       try {
/* 153 */         if (this.initialized) {
/*     */           long l;
/*     */           do {
/* 156 */             l = endStream(this.stream, this.target, this.target.position(), this.target.remaining());
/* 157 */             if (Zstd.isError(l)) {
/* 158 */               throw new ZstdIOException(l);
/*     */             }
/* 160 */             this.target.position(this.target.position() + this.produced);
/* 161 */             this.target = flushBuffer(this.target);
/* 162 */             if (!this.target.isDirect()) {
/* 163 */               throw new IllegalArgumentException("Target buffer should be a direct buffer");
/*     */             }
/* 165 */             if (l > 0L && !this.target.hasRemaining()) {
/* 166 */               throw new IOException("The target buffer has no more space, even after flushing, and there are still bytes to compress");
/*     */             }
/* 168 */           } while (l > 0L);
/*     */         } 
/*     */       } finally {
/*     */         
/* 172 */         freeCStream(this.stream);
/* 173 */         this.closed = true;
/* 174 */         this.initialized = false;
/* 175 */         this.target = null;
/*     */       }  
/*     */   }
/*     */   
/*     */   private static native long recommendedCOutSize();
/*     */   
/*     */   private static native long createCStream();
/*     */   
/*     */   private static native long freeCStream(long paramLong);
/*     */   
/*     */   private native long initCStream(long paramLong, int paramInt);
/*     */   
/*     */   private native long initCStreamWithDict(long paramLong, byte[] paramArrayOfbyte, int paramInt1, int paramInt2);
/*     */   
/*     */   private native long initCStreamWithFastDict(long paramLong, ZstdDictCompress paramZstdDictCompress);
/*     */   
/*     */   private native long compressDirectByteBuffer(long paramLong, ByteBuffer paramByteBuffer1, int paramInt1, int paramInt2, ByteBuffer paramByteBuffer2, int paramInt3, int paramInt4);
/*     */   
/*     */   private native long flushStream(long paramLong, ByteBuffer paramByteBuffer, int paramInt1, int paramInt2);
/*     */   
/*     */   private native long endStream(long paramLong, ByteBuffer paramByteBuffer, int paramInt1, int paramInt2);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\github\luben\zstd\ZstdDirectBufferCompressingStreamNoFinalizer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */