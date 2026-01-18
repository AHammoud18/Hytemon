/*     */ package com.github.luben.zstd;
/*     */ 
/*     */ import com.github.luben.zstd.util.Native;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ public class ZstdDecompressCtx
/*     */   extends AutoCloseBase
/*     */ {
/*     */   static {
/*  11 */     Native.load();
/*     */   }
/*     */   
/*  14 */   private long nativePtr = 0L;
/*     */   
/*  16 */   private ZstdDictDecompress decompression_dict = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZstdDecompressCtx() {
/*  27 */     this.nativePtr = init();
/*  28 */     if (0L == this.nativePtr) {
/*  29 */       throw new IllegalStateException("ZSTD_createDeCompressCtx failed");
/*     */     }
/*  31 */     storeFence();
/*     */   }
/*     */   
/*     */   void doClose() {
/*  35 */     if (this.nativePtr != 0L) {
/*  36 */       free(this.nativePtr);
/*  37 */       this.nativePtr = 0L;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZstdDecompressCtx setMagicless(boolean paramBoolean) {
/*  46 */     ensureOpen();
/*  47 */     acquireSharedLock();
/*  48 */     Zstd.setDecompressionMagicless(this.nativePtr, paramBoolean);
/*  49 */     releaseSharedLock();
/*  50 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZstdDecompressCtx loadDict(ZstdDictDecompress paramZstdDictDecompress) {
/*  59 */     ensureOpen();
/*  60 */     acquireSharedLock();
/*  61 */     paramZstdDictDecompress.acquireSharedLock();
/*     */     try {
/*  63 */       long l = loadDDictFast0(this.nativePtr, paramZstdDictDecompress);
/*  64 */       if (Zstd.isError(l)) {
/*  65 */         throw new ZstdException(l);
/*     */       }
/*     */       
/*  68 */       this.decompression_dict = paramZstdDictDecompress;
/*     */     } finally {
/*  70 */       paramZstdDictDecompress.releaseSharedLock();
/*  71 */       releaseSharedLock();
/*     */     } 
/*  73 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZstdDecompressCtx loadDict(byte[] paramArrayOfbyte) {
/*  84 */     ensureOpen();
/*  85 */     acquireSharedLock();
/*     */     try {
/*  87 */       long l = loadDDict0(this.nativePtr, paramArrayOfbyte);
/*  88 */       if (Zstd.isError(l)) {
/*  89 */         throw new ZstdException(l);
/*     */       }
/*  91 */       this.decompression_dict = null;
/*     */     } finally {
/*  93 */       releaseSharedLock();
/*     */     } 
/*  95 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() {
/* 105 */     ensureOpen();
/* 106 */     acquireSharedLock();
/*     */     try {
/* 108 */       long l = reset0(this.nativePtr);
/* 109 */       if (Zstd.isError(l)) {
/* 110 */         throw new ZstdException(l);
/*     */       }
/*     */     } finally {
/* 113 */       releaseSharedLock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void ensureOpen() {
/* 121 */     if (this.nativePtr == 0L) {
/* 122 */       throw new IllegalStateException("Decompression context is closed");
/*     */     }
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
/*     */   public boolean decompressDirectByteBufferStream(ByteBuffer paramByteBuffer1, ByteBuffer paramByteBuffer2) {
/* 135 */     ensureOpen();
/* 136 */     acquireSharedLock();
/*     */     try {
/* 138 */       long l = decompressDirectByteBufferStream0(this.nativePtr, paramByteBuffer1, paramByteBuffer1.position(), paramByteBuffer1.limit(), paramByteBuffer2, paramByteBuffer2.position(), paramByteBuffer2.limit());
/* 139 */       if ((l & 0x80000000L) != 0L) {
/* 140 */         long l1 = -(l & 0xFFL);
/* 141 */         throw new ZstdException(l1, Zstd.getErrorName(l1));
/*     */       } 
/* 143 */       paramByteBuffer2.position((int)(l & 0x7FFFFFFFL));
/* 144 */       paramByteBuffer1.position((int)(l >>> 32L) & Integer.MAX_VALUE);
/* 145 */       return (l >>> 63L == 1L);
/*     */     } finally {
/* 147 */       releaseSharedLock();
/*     */     } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int decompressDirectByteBuffer(ByteBuffer paramByteBuffer1, int paramInt1, int paramInt2, ByteBuffer paramByteBuffer2, int paramInt3, int paramInt4) {
/* 176 */     ensureOpen();
/* 177 */     if (!paramByteBuffer2.isDirect()) {
/* 178 */       throw new IllegalArgumentException("srcBuff must be a direct buffer");
/*     */     }
/* 180 */     if (!paramByteBuffer1.isDirect()) {
/* 181 */       throw new IllegalArgumentException("dstBuff must be a direct buffer");
/*     */     }
/* 183 */     Objects.checkFromIndexSize(paramInt3, paramInt4, paramByteBuffer2.limit());
/* 184 */     Objects.checkFromIndexSize(paramInt1, paramInt2, paramByteBuffer1.limit());
/*     */     
/* 186 */     acquireSharedLock();
/*     */     
/*     */     try {
/* 189 */       long l = decompressDirectByteBuffer0(this.nativePtr, paramByteBuffer1, paramInt1, paramInt2, paramByteBuffer2, paramInt3, paramInt4);
/* 190 */       if (Zstd.isError(l)) {
/* 191 */         throw new ZstdException(l);
/*     */       }
/* 193 */       if (l > 2147483647L) {
/* 194 */         throw new ZstdException(Zstd.errGeneric(), "Output size is greater than MAX_INT");
/*     */       }
/* 196 */       return (int)l;
/*     */     } finally {
/* 198 */       releaseSharedLock();
/*     */     } 
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
/*     */   public int decompressByteArray(byte[] paramArrayOfbyte1, int paramInt1, int paramInt2, byte[] paramArrayOfbyte2, int paramInt3, int paramInt4) {
/* 218 */     Objects.checkFromIndexSize(paramInt3, paramInt4, paramArrayOfbyte2.length);
/* 219 */     Objects.checkFromIndexSize(paramInt1, paramInt2, paramArrayOfbyte1.length);
/*     */     
/* 221 */     ensureOpen();
/* 222 */     acquireSharedLock();
/*     */     
/*     */     try {
/* 225 */       long l = decompressByteArray0(this.nativePtr, paramArrayOfbyte1, paramInt1, paramInt2, paramArrayOfbyte2, paramInt3, paramInt4);
/* 226 */       if (Zstd.isError(l)) {
/* 227 */         throw new ZstdException(l);
/*     */       }
/* 229 */       if (l > 2147483647L) {
/* 230 */         throw new ZstdException(Zstd.errGeneric(), "Output size is greater than MAX_INT");
/*     */       }
/* 232 */       return (int)l;
/*     */     } finally {
/* 234 */       releaseSharedLock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int decompressByteArrayToDirectByteBuffer(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2, byte[] paramArrayOfbyte, int paramInt3, int paramInt4) {
/* 241 */     if (!paramByteBuffer.isDirect()) {
/* 242 */       throw new IllegalArgumentException("dstBuff must be a direct buffer");
/*     */     }
/*     */     
/* 245 */     Objects.checkFromIndexSize(paramInt3, paramInt4, paramArrayOfbyte.length);
/* 246 */     Objects.checkFromIndexSize(paramInt1, paramInt2, paramByteBuffer.limit());
/*     */     
/* 248 */     ensureOpen();
/* 249 */     acquireSharedLock();
/*     */     
/*     */     try {
/* 252 */       long l = decompressByteArrayToDirectByteBuffer0(this.nativePtr, paramByteBuffer, paramInt1, paramInt2, paramArrayOfbyte, paramInt3, paramInt4);
/* 253 */       if (Zstd.isError(l)) {
/* 254 */         throw new ZstdException(l);
/*     */       }
/* 256 */       if (l > 2147483647L) {
/* 257 */         throw new ZstdException(Zstd.errGeneric(), "Output size is greater than MAX_INT");
/*     */       }
/* 259 */       return (int)l;
/*     */     } finally {
/* 261 */       releaseSharedLock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int decompressDirectByteBufferToByteArray(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, ByteBuffer paramByteBuffer, int paramInt3, int paramInt4) {
/* 268 */     if (!paramByteBuffer.isDirect()) {
/* 269 */       throw new IllegalArgumentException("srcBuff must be a direct buffer");
/*     */     }
/*     */     
/* 272 */     Objects.checkFromIndexSize(paramInt3, paramInt4, paramByteBuffer.limit());
/* 273 */     Objects.checkFromIndexSize(paramInt1, paramInt2, paramArrayOfbyte.length);
/*     */     
/* 275 */     ensureOpen();
/* 276 */     acquireSharedLock();
/*     */     
/*     */     try {
/* 279 */       long l = decompressDirectByteBufferToByteArray0(this.nativePtr, paramArrayOfbyte, paramInt1, paramInt2, paramByteBuffer, paramInt3, paramInt4);
/* 280 */       if (Zstd.isError(l)) {
/* 281 */         throw new ZstdException(l);
/*     */       }
/* 283 */       if (l > 2147483647L) {
/* 284 */         throw new ZstdException(Zstd.errGeneric(), "Output size is greater than MAX_INT");
/*     */       }
/* 286 */       return (int)l;
/*     */     } finally {
/* 288 */       releaseSharedLock();
/*     */     } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int decompress(ByteBuffer paramByteBuffer1, ByteBuffer paramByteBuffer2) throws ZstdException {
/* 316 */     int i = decompressDirectByteBuffer(paramByteBuffer1, paramByteBuffer1
/* 317 */         .position(), paramByteBuffer1
/* 318 */         .limit() - paramByteBuffer1.position(), paramByteBuffer2, paramByteBuffer2
/*     */         
/* 320 */         .position(), paramByteBuffer2
/* 321 */         .limit() - paramByteBuffer2.position());
/* 322 */     paramByteBuffer2.position(paramByteBuffer2.limit());
/* 323 */     paramByteBuffer1.position(paramByteBuffer1.position() + i);
/* 324 */     return i;
/*     */   }
/*     */   
/*     */   public int decompress(ByteBuffer paramByteBuffer, byte[] paramArrayOfbyte) throws ZstdException {
/* 328 */     int i = decompressByteArrayToDirectByteBuffer(paramByteBuffer, paramByteBuffer
/* 329 */         .position(), paramByteBuffer
/* 330 */         .limit() - paramByteBuffer.position(), paramArrayOfbyte, 0, paramArrayOfbyte.length);
/*     */ 
/*     */ 
/*     */     
/* 334 */     paramByteBuffer.position(paramByteBuffer.position() + i);
/* 335 */     return i;
/*     */   }
/*     */   
/*     */   public int decompress(byte[] paramArrayOfbyte, ByteBuffer paramByteBuffer) throws ZstdException {
/* 339 */     int i = decompressDirectByteBufferToByteArray(paramArrayOfbyte, 0, paramArrayOfbyte.length, paramByteBuffer, paramByteBuffer
/*     */ 
/*     */ 
/*     */         
/* 343 */         .position(), paramByteBuffer
/* 344 */         .limit() - paramByteBuffer.position());
/* 345 */     paramByteBuffer.position(paramByteBuffer.limit());
/* 346 */     return i;
/*     */   }
/*     */   
/*     */   public ByteBuffer decompress(ByteBuffer paramByteBuffer, int paramInt) throws ZstdException {
/* 350 */     ByteBuffer byteBuffer = ByteBuffer.allocateDirect(paramInt);
/* 351 */     int i = decompressDirectByteBuffer(byteBuffer, 0, paramInt, paramByteBuffer, paramByteBuffer.position(), paramByteBuffer.limit() - paramByteBuffer.position());
/* 352 */     paramByteBuffer.position(paramByteBuffer.limit());
/*     */ 
/*     */     
/* 355 */     return byteBuffer;
/*     */   }
/*     */   
/*     */   public int decompress(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) {
/* 359 */     return decompressByteArray(paramArrayOfbyte1, 0, paramArrayOfbyte1.length, paramArrayOfbyte2, 0, paramArrayOfbyte2.length);
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
/*     */   public byte[] decompress(byte[] paramArrayOfbyte, int paramInt) throws ZstdException {
/* 372 */     return decompress(paramArrayOfbyte, 0, paramArrayOfbyte.length, paramInt);
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
/*     */   public byte[] decompress(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3) throws ZstdException {
/* 387 */     if (paramInt3 < 0) {
/* 388 */       throw new ZstdException(Zstd.errGeneric(), "Original size should not be negative");
/*     */     }
/* 390 */     byte[] arrayOfByte = new byte[paramInt3];
/* 391 */     int i = decompressByteArray(arrayOfByte, 0, arrayOfByte.length, paramArrayOfbyte, paramInt1, paramInt2);
/* 392 */     if (i != paramInt3) {
/* 393 */       return Arrays.copyOfRange(arrayOfByte, 0, i);
/*     */     }
/* 395 */     return arrayOfByte;
/*     */   }
/*     */   
/*     */   private static native long init();
/*     */   
/*     */   private static native void free(long paramLong);
/*     */   
/*     */   private static native long loadDDictFast0(long paramLong, ZstdDictDecompress paramZstdDictDecompress);
/*     */   
/*     */   private static native long loadDDict0(long paramLong, byte[] paramArrayOfbyte);
/*     */   
/*     */   private static native long reset0(long paramLong);
/*     */   
/*     */   private static native long decompressDirectByteBufferStream0(long paramLong, ByteBuffer paramByteBuffer1, int paramInt1, int paramInt2, ByteBuffer paramByteBuffer2, int paramInt3, int paramInt4);
/*     */   
/*     */   private static native long decompressDirectByteBuffer0(long paramLong, ByteBuffer paramByteBuffer1, int paramInt1, int paramInt2, ByteBuffer paramByteBuffer2, int paramInt3, int paramInt4);
/*     */   
/*     */   private static native long decompressByteArray0(long paramLong, byte[] paramArrayOfbyte1, int paramInt1, int paramInt2, byte[] paramArrayOfbyte2, int paramInt3, int paramInt4);
/*     */   
/*     */   private static native long decompressByteArrayToDirectByteBuffer0(long paramLong, ByteBuffer paramByteBuffer, int paramInt1, int paramInt2, byte[] paramArrayOfbyte, int paramInt3, int paramInt4);
/*     */   
/*     */   private static native long decompressDirectByteBufferToByteArray0(long paramLong, byte[] paramArrayOfbyte, int paramInt1, int paramInt2, ByteBuffer paramByteBuffer, int paramInt3, int paramInt4);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\github\luben\zstd\ZstdDecompressCtx.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */