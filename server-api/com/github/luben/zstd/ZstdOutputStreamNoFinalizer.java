/*     */ package com.github.luben.zstd;
/*     */ 
/*     */ import com.github.luben.zstd.util.Native;
/*     */ import java.io.FilterOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ZstdOutputStreamNoFinalizer
/*     */   extends FilterOutputStream
/*     */ {
/*     */   private final long stream;
/*     */   
/*     */   static {
/*  18 */     Native.load();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  24 */   private long srcPos = 0L;
/*  25 */   private long dstPos = 0L;
/*     */   private final BufferPool bufferPool;
/*     */   private final ByteBuffer dstByteBuffer;
/*     */   private final byte[] dst;
/*     */   private boolean isClosed = false;
/*  30 */   private static final int dstSize = (int)recommendedCOutSize();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean closeFrameOnFlush = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean frameClosed = true;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean frameStarted = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZstdOutputStreamNoFinalizer(OutputStream paramOutputStream, int paramInt) throws IOException {
/*  51 */     this(paramOutputStream, NoPool.INSTANCE);
/*  52 */     Zstd.setCompressionLevel(this.stream, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZstdOutputStreamNoFinalizer(OutputStream paramOutputStream) throws IOException {
/*  60 */     this(paramOutputStream, NoPool.INSTANCE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZstdOutputStreamNoFinalizer(OutputStream paramOutputStream, BufferPool paramBufferPool, int paramInt) throws IOException {
/*  69 */     this(paramOutputStream, paramBufferPool);
/*  70 */     Zstd.setCompressionLevel(this.stream, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZstdOutputStreamNoFinalizer(OutputStream paramOutputStream, BufferPool paramBufferPool) throws IOException {
/*  79 */     super(paramOutputStream);
/*     */     
/*  81 */     this.stream = createCStream();
/*  82 */     this.bufferPool = paramBufferPool;
/*  83 */     this.dstByteBuffer = Zstd.getArrayBackedBuffer(paramBufferPool, dstSize);
/*  84 */     this.dst = this.dstByteBuffer.array();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized ZstdOutputStreamNoFinalizer setChecksum(boolean paramBoolean) throws IOException {
/*  93 */     if (!this.frameClosed) {
/*  94 */       throw new IllegalStateException("Change of parameter on initialized stream");
/*     */     }
/*  96 */     int i = Zstd.setCompressionChecksums(this.stream, paramBoolean);
/*  97 */     if (Zstd.isError(i)) {
/*  98 */       throw new ZstdIOException(i);
/*     */     }
/* 100 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized ZstdOutputStreamNoFinalizer setLevel(int paramInt) throws IOException {
/* 109 */     if (!this.frameClosed) {
/* 110 */       throw new IllegalStateException("Change of parameter on initialized stream");
/*     */     }
/* 112 */     int i = Zstd.setCompressionLevel(this.stream, paramInt);
/* 113 */     if (Zstd.isError(i)) {
/* 114 */       throw new ZstdIOException(i);
/*     */     }
/* 116 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized ZstdOutputStreamNoFinalizer setLong(int paramInt) throws IOException {
/* 125 */     if (!this.frameClosed) {
/* 126 */       throw new IllegalStateException("Change of parameter on initialized stream");
/*     */     }
/* 128 */     int i = Zstd.setCompressionLong(this.stream, paramInt);
/* 129 */     if (Zstd.isError(i)) {
/* 130 */       throw new ZstdIOException(i);
/*     */     }
/* 132 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized ZstdOutputStreamNoFinalizer setWorkers(int paramInt) throws IOException {
/* 141 */     if (!this.frameClosed) {
/* 142 */       throw new IllegalStateException("Change of parameter on initialized stream");
/*     */     }
/* 144 */     int i = Zstd.setCompressionWorkers(this.stream, paramInt);
/* 145 */     if (Zstd.isError(i)) {
/* 146 */       throw new ZstdIOException(i);
/*     */     }
/* 148 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized ZstdOutputStreamNoFinalizer setOverlapLog(int paramInt) throws IOException {
/* 158 */     if (!this.frameClosed) {
/* 159 */       throw new IllegalStateException("Change of parameter on initialized stream");
/*     */     }
/* 161 */     int i = Zstd.setCompressionOverlapLog(this.stream, paramInt);
/* 162 */     if (Zstd.isError(i)) {
/* 163 */       throw new ZstdIOException(i);
/*     */     }
/* 165 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized ZstdOutputStreamNoFinalizer setJobSize(int paramInt) throws IOException {
/* 175 */     if (!this.frameClosed) {
/* 176 */       throw new IllegalStateException("Change of parameter on initialized stream");
/*     */     }
/* 178 */     int i = Zstd.setCompressionJobSize(this.stream, paramInt);
/* 179 */     if (Zstd.isError(i)) {
/* 180 */       throw new ZstdIOException(i);
/*     */     }
/* 182 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized ZstdOutputStreamNoFinalizer setTargetLength(int paramInt) throws IOException {
/* 191 */     if (!this.frameClosed) {
/* 192 */       throw new IllegalStateException("Change of parameter on initialized stream");
/*     */     }
/* 194 */     int i = Zstd.setCompressionTargetLength(this.stream, paramInt);
/* 195 */     if (Zstd.isError(i)) {
/* 196 */       throw new ZstdIOException(i);
/*     */     }
/* 198 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized ZstdOutputStreamNoFinalizer setMinMatch(int paramInt) throws IOException {
/* 207 */     if (!this.frameClosed) {
/* 208 */       throw new IllegalStateException("Change of parameter on initialized stream");
/*     */     }
/* 210 */     int i = Zstd.setCompressionMinMatch(this.stream, paramInt);
/* 211 */     if (Zstd.isError(i)) {
/* 212 */       throw new ZstdIOException(i);
/*     */     }
/* 214 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized ZstdOutputStreamNoFinalizer setSearchLog(int paramInt) throws IOException {
/* 224 */     if (!this.frameClosed) {
/* 225 */       throw new IllegalStateException("Change of parameter on initialized stream");
/*     */     }
/* 227 */     int i = Zstd.setCompressionSearchLog(this.stream, paramInt);
/* 228 */     if (Zstd.isError(i)) {
/* 229 */       throw new ZstdIOException(i);
/*     */     }
/* 231 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized ZstdOutputStreamNoFinalizer setChainLog(int paramInt) throws IOException {
/* 241 */     if (!this.frameClosed) {
/* 242 */       throw new IllegalStateException("Change of parameter on initialized stream");
/*     */     }
/* 244 */     int i = Zstd.setCompressionChainLog(this.stream, paramInt);
/* 245 */     if (Zstd.isError(i)) {
/* 246 */       throw new ZstdIOException(i);
/*     */     }
/* 248 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized ZstdOutputStreamNoFinalizer setHashLog(int paramInt) throws IOException {
/* 257 */     if (!this.frameClosed) {
/* 258 */       throw new IllegalStateException("Change of parameter on initialized stream");
/*     */     }
/* 260 */     int i = Zstd.setCompressionHashLog(this.stream, paramInt);
/* 261 */     if (Zstd.isError(i)) {
/* 262 */       throw new ZstdIOException(i);
/*     */     }
/* 264 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized ZstdOutputStreamNoFinalizer setWindowLog(int paramInt) throws IOException {
/* 273 */     if (!this.frameClosed) {
/* 274 */       throw new IllegalStateException("Change of parameter on initialized stream");
/*     */     }
/* 276 */     int i = Zstd.setCompressionWindowLog(this.stream, paramInt);
/* 277 */     if (Zstd.isError(i)) {
/* 278 */       throw new ZstdIOException(i);
/*     */     }
/* 280 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized ZstdOutputStreamNoFinalizer setStrategy(int paramInt) throws IOException {
/* 289 */     if (!this.frameClosed) {
/* 290 */       throw new IllegalStateException("Change of parameter on initialized stream");
/*     */     }
/* 292 */     int i = Zstd.setCompressionStrategy(this.stream, paramInt);
/* 293 */     if (Zstd.isError(i)) {
/* 294 */       throw new ZstdIOException(i);
/*     */     }
/* 296 */     return this;
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
/*     */   public synchronized ZstdOutputStreamNoFinalizer setCloseFrameOnFlush(boolean paramBoolean) {
/* 309 */     if (!this.frameClosed) {
/* 310 */       throw new IllegalStateException("Change of parameter on initialized stream");
/*     */     }
/* 312 */     this.closeFrameOnFlush = paramBoolean;
/* 313 */     return this;
/*     */   }
/*     */   
/*     */   public synchronized ZstdOutputStreamNoFinalizer setDict(byte[] paramArrayOfbyte) throws IOException {
/* 317 */     if (!this.frameClosed) {
/* 318 */       throw new IllegalStateException("Change of parameter on initialized stream");
/*     */     }
/* 320 */     int i = Zstd.loadDictCompress(this.stream, paramArrayOfbyte, paramArrayOfbyte.length);
/* 321 */     if (Zstd.isError(i)) {
/* 322 */       throw new ZstdIOException(i);
/*     */     }
/* 324 */     return this;
/*     */   }
/*     */   
/*     */   public synchronized ZstdOutputStreamNoFinalizer setDict(ZstdDictCompress paramZstdDictCompress) throws IOException {
/* 328 */     if (!this.frameClosed) {
/* 329 */       throw new IllegalStateException("Change of parameter on initialized stream");
/*     */     }
/* 331 */     int i = Zstd.loadFastDictCompress(this.stream, paramZstdDictCompress);
/* 332 */     if (Zstd.isError(i)) {
/* 333 */       throw new ZstdIOException(i);
/*     */     }
/* 335 */     return this;
/*     */   }
/*     */   
/*     */   public synchronized void write(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
/* 339 */     if (this.isClosed) {
/* 340 */       throw new IOException("StreamClosed");
/*     */     }
/* 342 */     if (this.frameClosed) {
/* 343 */       int j = resetCStream(this.stream);
/* 344 */       if (Zstd.isError(j)) {
/* 345 */         throw new ZstdIOException(j);
/*     */       }
/* 347 */       this.frameClosed = false;
/* 348 */       this.frameStarted = true;
/*     */     } 
/* 350 */     int i = paramInt1 + paramInt2;
/* 351 */     this.srcPos = paramInt1;
/* 352 */     while (this.srcPos < i) {
/* 353 */       int j = compressStream(this.stream, this.dst, dstSize, paramArrayOfbyte, i);
/* 354 */       if (Zstd.isError(j)) {
/* 355 */         throw new ZstdIOException(j);
/*     */       }
/* 357 */       if (this.dstPos > 0L) {
/* 358 */         this.out.write(this.dst, 0, (int)this.dstPos);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void write(int paramInt) throws IOException {
/* 364 */     byte[] arrayOfByte = new byte[1];
/* 365 */     arrayOfByte[0] = (byte)paramInt;
/* 366 */     write(arrayOfByte, 0, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void flush() throws IOException {
/* 373 */     if (this.isClosed) {
/* 374 */       throw new IOException("StreamClosed");
/*     */     }
/* 376 */     if (!this.frameClosed) {
/* 377 */       if (this.closeFrameOnFlush)
/*     */       
/*     */       { while (true)
/*     */         
/* 381 */         { int i = endStream(this.stream, this.dst, dstSize);
/* 382 */           if (Zstd.isError(i)) {
/* 383 */             throw new ZstdIOException(i);
/*     */           }
/* 385 */           this.out.write(this.dst, 0, (int)this.dstPos);
/* 386 */           if (i <= 0) {
/* 387 */             this.frameClosed = true;
/*     */           } else {
/*     */             continue;
/*     */           } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 399 */           this.out.flush(); }  } else { int i; do { i = flushStream(this.stream, this.dst, dstSize); if (Zstd.isError(i)) throw new ZstdIOException(i);  this.out.write(this.dst, 0, (int)this.dstPos); } while (i > 0); this.out.flush(); }
/*     */     
/*     */     }
/*     */   }
/*     */   
/*     */   public synchronized void close() throws IOException {
/* 405 */     close(true);
/*     */   }
/*     */   
/*     */   public synchronized void closeWithoutClosingParentStream() throws IOException {
/* 409 */     close(false);
/*     */   }
/*     */ 
/*     */   
/*     */   private void close(boolean paramBoolean) throws IOException {
/* 414 */     if (this.isClosed) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 422 */       if (!this.frameStarted) {
/* 423 */         int i = resetCStream(this.stream);
/* 424 */         if (Zstd.isError(i)) {
/* 425 */           throw new ZstdIOException(i);
/*     */         }
/* 427 */         this.frameClosed = false;
/*     */       } 
/*     */       
/* 430 */       if (!this.frameClosed) {
/*     */         int i; do {
/* 432 */           i = endStream(this.stream, this.dst, dstSize);
/* 433 */           if (Zstd.isError(i)) {
/* 434 */             throw new ZstdIOException(i);
/*     */           }
/* 436 */           this.out.write(this.dst, 0, (int)this.dstPos);
/* 437 */         } while (i > 0);
/*     */       } 
/* 439 */       if (paramBoolean) {
/* 440 */         this.out.close();
/*     */       }
/*     */     } finally {
/*     */       
/* 444 */       this.isClosed = true;
/* 445 */       this.bufferPool.release(this.dstByteBuffer);
/* 446 */       freeCStream(this.stream);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static native long recommendedCOutSize();
/*     */   
/*     */   private static native long createCStream();
/*     */   
/*     */   private static native int freeCStream(long paramLong);
/*     */   
/*     */   private native int resetCStream(long paramLong);
/*     */   
/*     */   private native int compressStream(long paramLong, byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, int paramInt2);
/*     */   
/*     */   private native int flushStream(long paramLong, byte[] paramArrayOfbyte, int paramInt);
/*     */   
/*     */   private native int endStream(long paramLong, byte[] paramArrayOfbyte, int paramInt);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\github\luben\zstd\ZstdOutputStreamNoFinalizer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */