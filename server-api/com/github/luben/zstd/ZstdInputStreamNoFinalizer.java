/*     */ package com.github.luben.zstd;
/*     */ 
/*     */ import com.github.luben.zstd.util.Native;
/*     */ import java.io.FilterInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ZstdInputStreamNoFinalizer
/*     */   extends FilterInputStream
/*     */ {
/*     */   private final long stream;
/*     */   
/*     */   static {
/*  23 */     Native.load();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  28 */   private long dstPos = 0L;
/*  29 */   private long srcPos = 0L;
/*  30 */   private long srcSize = 0L;
/*     */   private boolean needRead = true;
/*     */   private final BufferPool bufferPool;
/*     */   private final ByteBuffer srcByteBuffer;
/*     */   private final byte[] src;
/*  35 */   private static final int srcBuffSize = (int)recommendedDInSize();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isContinuous = false;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean frameFinished = true;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isClosed = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZstdInputStreamNoFinalizer(InputStream paramInputStream) throws IOException {
/*  54 */     this(paramInputStream, NoPool.INSTANCE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZstdInputStreamNoFinalizer(InputStream paramInputStream, BufferPool paramBufferPool) throws IOException {
/*  63 */     super(paramInputStream);
/*  64 */     this.bufferPool = paramBufferPool;
/*  65 */     this.srcByteBuffer = Zstd.getArrayBackedBuffer(paramBufferPool, srcBuffSize);
/*  66 */     this.src = this.srcByteBuffer.array();
/*     */     
/*  68 */     synchronized (this) {
/*  69 */       this.stream = createDStream();
/*  70 */       initDStream(this.stream);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized ZstdInputStreamNoFinalizer setContinuous(boolean paramBoolean) {
/*  80 */     this.isContinuous = paramBoolean;
/*  81 */     return this;
/*     */   }
/*     */   
/*     */   public synchronized boolean getContinuous() {
/*  85 */     return this.isContinuous;
/*     */   }
/*     */   
/*     */   public synchronized ZstdInputStreamNoFinalizer setDict(byte[] paramArrayOfbyte) throws IOException {
/*  89 */     int i = Zstd.loadDictDecompress(this.stream, paramArrayOfbyte, paramArrayOfbyte.length);
/*  90 */     if (Zstd.isError(i)) {
/*  91 */       throw new ZstdIOException(i);
/*     */     }
/*  93 */     return this;
/*     */   }
/*     */   
/*     */   public synchronized ZstdInputStreamNoFinalizer setDict(ZstdDictDecompress paramZstdDictDecompress) throws IOException {
/*  97 */     paramZstdDictDecompress.acquireSharedLock();
/*     */     try {
/*  99 */       int i = Zstd.loadFastDictDecompress(this.stream, paramZstdDictDecompress);
/* 100 */       if (Zstd.isError(i)) {
/* 101 */         throw new ZstdIOException(i);
/*     */       }
/*     */     } finally {
/* 104 */       paramZstdDictDecompress.releaseSharedLock();
/*     */     } 
/* 106 */     return this;
/*     */   }
/*     */   
/*     */   public synchronized ZstdInputStreamNoFinalizer setLongMax(int paramInt) throws IOException {
/* 110 */     int i = Zstd.setDecompressionLongMax(this.stream, paramInt);
/* 111 */     if (Zstd.isError(i)) {
/* 112 */       throw new ZstdIOException(i);
/*     */     }
/* 114 */     return this;
/*     */   }
/*     */   
/*     */   public synchronized ZstdInputStreamNoFinalizer setRefMultipleDDicts(boolean paramBoolean) throws IOException {
/* 118 */     int i = Zstd.setRefMultipleDDicts(this.stream, paramBoolean);
/* 119 */     if (Zstd.isError(i)) {
/* 120 */       throw new ZstdIOException(i);
/*     */     }
/* 122 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized int read(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
/* 127 */     if (paramInt1 < 0 || paramInt2 > paramArrayOfbyte.length - paramInt1) {
/* 128 */       throw new IndexOutOfBoundsException("Requested length " + paramInt2 + " from offset " + paramInt1 + " in buffer of size " + paramArrayOfbyte.length);
/*     */     }
/*     */     
/* 131 */     if (paramInt2 == 0) {
/* 132 */       return 0;
/*     */     }
/* 134 */     int i = 0;
/* 135 */     while (!i) {
/* 136 */       i = readInternal(paramArrayOfbyte, paramInt1, paramInt2);
/*     */     }
/* 138 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   int readInternal(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
/* 144 */     if (this.isClosed) {
/* 145 */       throw new IOException("Stream closed");
/*     */     }
/*     */ 
/*     */     
/* 149 */     if (paramInt1 < 0 || paramInt2 > paramArrayOfbyte.length - paramInt1) {
/* 150 */       throw new IndexOutOfBoundsException("Requested length " + paramInt2 + " from offset " + paramInt1 + " in buffer of size " + paramArrayOfbyte.length);
/*     */     }
/*     */     
/* 153 */     int i = paramInt1 + paramInt2;
/* 154 */     this.dstPos = paramInt1;
/* 155 */     long l = -1L;
/*     */     
/* 157 */     while (this.dstPos < i && l < this.dstPos) {
/*     */ 
/*     */       
/* 160 */       if (this.needRead && (this.in.available() > 0 || this.dstPos == paramInt1)) {
/* 161 */         this.srcSize = this.in.read(this.src, 0, srcBuffSize);
/* 162 */         this.srcPos = 0L;
/* 163 */         if (this.srcSize < 0L) {
/* 164 */           this.srcSize = 0L;
/* 165 */           if (this.frameFinished)
/* 166 */             return -1; 
/* 167 */           if (this.isContinuous) {
/* 168 */             this.srcSize = (int)(this.dstPos - paramInt1);
/* 169 */             if (this.srcSize > 0L) {
/* 170 */               return (int)this.srcSize;
/*     */             }
/* 172 */             return -1;
/*     */           } 
/* 174 */           throw new ZstdIOException(Zstd.errCorruptionDetected(), "Truncated source");
/*     */         } 
/*     */         
/* 177 */         this.frameFinished = false;
/*     */       } 
/*     */       
/* 180 */       l = this.dstPos;
/* 181 */       int j = decompressStream(this.stream, paramArrayOfbyte, i, this.src, (int)this.srcSize);
/*     */       
/* 183 */       if (Zstd.isError(j)) {
/* 184 */         throw new ZstdIOException(j);
/*     */       }
/*     */ 
/*     */       
/* 188 */       if (j == 0) {
/* 189 */         this.frameFinished = true;
/*     */ 
/*     */         
/* 192 */         this.needRead = (this.srcPos == this.srcSize);
/* 193 */         return (int)(this.dstPos - paramInt1);
/*     */       } 
/*     */ 
/*     */       
/* 197 */       this.needRead = (this.dstPos < i);
/*     */     } 
/*     */     
/* 200 */     return (int)(this.dstPos - paramInt1);
/*     */   }
/*     */   
/*     */   public synchronized int read() throws IOException {
/* 204 */     byte[] arrayOfByte = new byte[1];
/* 205 */     int i = 0;
/* 206 */     while (!i) {
/* 207 */       i = readInternal(arrayOfByte, 0, 1);
/*     */     }
/* 209 */     if (i == 1) {
/* 210 */       return arrayOfByte[0] & 0xFF;
/*     */     }
/* 212 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized int available() throws IOException {
/* 217 */     if (this.isClosed) {
/* 218 */       throw new IOException("Stream closed");
/*     */     }
/* 220 */     if (!this.needRead) {
/* 221 */       return 1;
/*     */     }
/* 223 */     return this.in.available();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean markSupported() {
/* 229 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized long skip(long paramLong) throws IOException {
/* 234 */     if (this.isClosed) {
/* 235 */       throw new IOException("Stream closed");
/*     */     }
/* 237 */     if (paramLong <= 0L) {
/* 238 */       return 0L;
/*     */     }
/* 240 */     int i = (int)recommendedDOutSize();
/* 241 */     if (i > paramLong) {
/* 242 */       i = (int)paramLong;
/*     */     }
/* 244 */     ByteBuffer byteBuffer = Zstd.getArrayBackedBuffer(this.bufferPool, i);
/* 245 */     long l = paramLong;
/*     */     try {
/* 247 */       byte[] arrayOfByte = byteBuffer.array();
/* 248 */       while (l > 0L) {
/* 249 */         int j = read(arrayOfByte, 0, (int)Math.min(i, l));
/* 250 */         if (j < 0) {
/*     */           break;
/*     */         }
/* 253 */         l -= j;
/*     */       } 
/*     */     } finally {
/* 256 */       this.bufferPool.release(byteBuffer);
/*     */     } 
/* 258 */     return paramLong - l;
/*     */   }
/*     */   
/*     */   public synchronized void close() throws IOException {
/* 262 */     if (this.isClosed) {
/*     */       return;
/*     */     }
/* 265 */     this.isClosed = true;
/* 266 */     this.bufferPool.release(this.srcByteBuffer);
/* 267 */     freeDStream(this.stream);
/* 268 */     this.in.close();
/*     */   }
/*     */   
/*     */   public static native long recommendedDInSize();
/*     */   
/*     */   public static native long recommendedDOutSize();
/*     */   
/*     */   private static native long createDStream();
/*     */   
/*     */   private static native int freeDStream(long paramLong);
/*     */   
/*     */   private native int initDStream(long paramLong);
/*     */   
/*     */   private native int decompressStream(long paramLong, byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, int paramInt2);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\github\luben\zstd\ZstdInputStreamNoFinalizer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */