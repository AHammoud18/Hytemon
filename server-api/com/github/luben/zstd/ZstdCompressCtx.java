/*     */ package com.github.luben.zstd;
/*     */ 
/*     */ import com.github.luben.zstd.util.Native;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ public class ZstdCompressCtx
/*     */   extends AutoCloseBase
/*     */ {
/*     */   static {
/*  12 */     Native.load();
/*     */   }
/*     */   
/*  15 */   private long nativePtr = 0L;
/*     */   
/*  17 */   private ZstdDictCompress compression_dict = null;
/*     */   
/*  19 */   private SequenceProducer seqprod = null;
/*     */   
/*  21 */   private long seqprod_state = 0L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZstdCompressCtx() {
/*  32 */     this.nativePtr = init();
/*  33 */     if (0L == this.nativePtr) {
/*  34 */       throw new IllegalStateException("ZSTD_createCompressCtx failed");
/*     */     }
/*  36 */     storeFence();
/*     */   }
/*     */   
/*     */   void doClose() {
/*  40 */     if (this.nativePtr != 0L) {
/*  41 */       free(this.nativePtr);
/*  42 */       this.nativePtr = 0L;
/*  43 */       if (this.seqprod != null) {
/*  44 */         this.seqprod.freeState(this.seqprod_state);
/*  45 */         this.seqprod = null;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void ensureOpen() {
/*  51 */     if (this.nativePtr == 0L) {
/*  52 */       throw new IllegalStateException("Compression context is closed");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZstdCompressCtx setLevel(int paramInt) {
/*  61 */     ensureOpen();
/*  62 */     acquireSharedLock();
/*  63 */     setLevel0(this.nativePtr, paramInt);
/*  64 */     releaseSharedLock();
/*  65 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZstdCompressCtx setMagicless(boolean paramBoolean) {
/*  75 */     ensureOpen();
/*  76 */     acquireSharedLock();
/*  77 */     Zstd.setCompressionMagicless(this.nativePtr, paramBoolean);
/*  78 */     releaseSharedLock();
/*  79 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZstdCompressCtx setChecksum(boolean paramBoolean) {
/*  87 */     ensureOpen();
/*  88 */     acquireSharedLock();
/*  89 */     setChecksum0(this.nativePtr, paramBoolean);
/*  90 */     releaseSharedLock();
/*  91 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ZstdCompressCtx setWorkers(int paramInt) {
/*  97 */     ensureOpen();
/*  98 */     acquireSharedLock();
/*     */     try {
/* 100 */       long l = Zstd.setCompressionWorkers(this.nativePtr, paramInt);
/* 101 */       if (Zstd.isError(l)) {
/* 102 */         throw new ZstdException(l);
/*     */       }
/*     */     } finally {
/* 105 */       releaseSharedLock();
/*     */     } 
/* 107 */     return this;
/*     */   }
/*     */   
/*     */   public ZstdCompressCtx setOverlapLog(int paramInt) {
/* 111 */     ensureOpen();
/* 112 */     acquireSharedLock();
/*     */     try {
/* 114 */       long l = Zstd.setCompressionOverlapLog(this.nativePtr, paramInt);
/* 115 */       if (Zstd.isError(l)) {
/* 116 */         throw new ZstdException(l);
/*     */       }
/*     */     } finally {
/* 119 */       releaseSharedLock();
/*     */     } 
/* 121 */     return this;
/*     */   }
/*     */   
/*     */   public ZstdCompressCtx setJobSize(int paramInt) {
/* 125 */     ensureOpen();
/* 126 */     acquireSharedLock();
/*     */     try {
/* 128 */       long l = Zstd.setCompressionJobSize(this.nativePtr, paramInt);
/* 129 */       if (Zstd.isError(l)) {
/* 130 */         throw new ZstdException(l);
/*     */       }
/*     */     } finally {
/* 133 */       releaseSharedLock();
/*     */     } 
/* 135 */     return this;
/*     */   }
/*     */   
/*     */   public ZstdCompressCtx setTargetLength(int paramInt) {
/* 139 */     ensureOpen();
/* 140 */     acquireSharedLock();
/*     */     try {
/* 142 */       long l = Zstd.setCompressionTargetLength(this.nativePtr, paramInt);
/* 143 */       if (Zstd.isError(l)) {
/* 144 */         throw new ZstdException(l);
/*     */       }
/*     */     } finally {
/* 147 */       releaseSharedLock();
/*     */     } 
/* 149 */     return this;
/*     */   }
/*     */   
/*     */   public ZstdCompressCtx setMinMatch(int paramInt) {
/* 153 */     ensureOpen();
/* 154 */     acquireSharedLock();
/*     */     try {
/* 156 */       long l = Zstd.setCompressionMinMatch(this.nativePtr, paramInt);
/* 157 */       if (Zstd.isError(l)) {
/* 158 */         throw new ZstdException(l);
/*     */       }
/*     */     } finally {
/* 161 */       releaseSharedLock();
/*     */     } 
/* 163 */     return this;
/*     */   }
/*     */   
/*     */   public ZstdCompressCtx setSearchLog(int paramInt) {
/* 167 */     ensureOpen();
/* 168 */     acquireSharedLock();
/*     */     try {
/* 170 */       long l = Zstd.setCompressionSearchLog(this.nativePtr, paramInt);
/* 171 */       if (Zstd.isError(l)) {
/* 172 */         throw new ZstdException(l);
/*     */       }
/*     */     } finally {
/* 175 */       releaseSharedLock();
/*     */     } 
/* 177 */     return this;
/*     */   }
/*     */   
/*     */   public ZstdCompressCtx setChainLog(int paramInt) {
/* 181 */     ensureOpen();
/* 182 */     acquireSharedLock();
/*     */     try {
/* 184 */       long l = Zstd.setCompressionChainLog(this.nativePtr, paramInt);
/* 185 */       if (Zstd.isError(l)) {
/* 186 */         throw new ZstdException(l);
/*     */       }
/*     */     } finally {
/* 189 */       releaseSharedLock();
/*     */     } 
/* 191 */     return this;
/*     */   }
/*     */   
/*     */   public ZstdCompressCtx setHashLog(int paramInt) {
/* 195 */     ensureOpen();
/* 196 */     acquireSharedLock();
/*     */     try {
/* 198 */       long l = Zstd.setCompressionHashLog(this.nativePtr, paramInt);
/* 199 */       if (Zstd.isError(l)) {
/* 200 */         throw new ZstdException(l);
/*     */       }
/*     */     } finally {
/* 203 */       releaseSharedLock();
/*     */     } 
/* 205 */     return this;
/*     */   }
/*     */   
/*     */   public ZstdCompressCtx setWindowLog(int paramInt) {
/* 209 */     ensureOpen();
/* 210 */     acquireSharedLock();
/*     */     try {
/* 212 */       long l = Zstd.setCompressionWindowLog(this.nativePtr, paramInt);
/* 213 */       if (Zstd.isError(l)) {
/* 214 */         throw new ZstdException(l);
/*     */       }
/*     */     } finally {
/* 217 */       releaseSharedLock();
/*     */     } 
/* 219 */     return this;
/*     */   }
/*     */   
/*     */   public ZstdCompressCtx setStrategy(int paramInt) {
/* 223 */     ensureOpen();
/* 224 */     acquireSharedLock();
/*     */     try {
/* 226 */       long l = Zstd.setCompressionStrategy(this.nativePtr, paramInt);
/* 227 */       if (Zstd.isError(l)) {
/* 228 */         throw new ZstdException(l);
/*     */       }
/*     */     } finally {
/* 231 */       releaseSharedLock();
/*     */     } 
/* 233 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZstdCompressCtx setContentSize(boolean paramBoolean) {
/* 241 */     ensureOpen();
/* 242 */     acquireSharedLock();
/* 243 */     setContentSize0(this.nativePtr, paramBoolean);
/* 244 */     releaseSharedLock();
/* 245 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZstdCompressCtx setDictID(boolean paramBoolean) {
/* 254 */     ensureOpen();
/* 255 */     acquireSharedLock();
/* 256 */     setDictID0(this.nativePtr, paramBoolean);
/* 257 */     releaseSharedLock();
/* 258 */     return this;
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
/*     */   public ZstdCompressCtx setLong(int paramInt) {
/* 272 */     ensureOpen();
/* 273 */     acquireSharedLock();
/* 274 */     Zstd.setCompressionLong(this.nativePtr, paramInt);
/* 275 */     releaseSharedLock();
/* 276 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZstdCompressCtx registerSequenceProducer(SequenceProducer paramSequenceProducer) {
/* 284 */     ensureOpen();
/* 285 */     acquireSharedLock();
/*     */     try {
/* 287 */       if (this.seqprod != null) {
/* 288 */         this.seqprod.freeState(this.seqprod_state);
/* 289 */         this.seqprod = null;
/*     */       } 
/*     */       
/* 292 */       if (paramSequenceProducer == null) {
/* 293 */         Zstd.registerSequenceProducer(this.nativePtr, 0L, 0L);
/*     */       } else {
/* 295 */         this.seqprod_state = paramSequenceProducer.createState();
/* 296 */         Zstd.registerSequenceProducer(this.nativePtr, this.seqprod_state, paramSequenceProducer.getFunctionPointer());
/* 297 */         this.seqprod = paramSequenceProducer;
/*     */       } 
/* 299 */     } catch (Exception exception) {
/* 300 */       this.seqprod = null;
/* 301 */       Zstd.registerSequenceProducer(this.nativePtr, 0L, 0L);
/* 302 */       throw exception;
/*     */     } finally {
/* 304 */       releaseSharedLock();
/*     */     } 
/* 306 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZstdCompressCtx setSequenceProducerFallback(boolean paramBoolean) {
/* 315 */     ensureOpen();
/* 316 */     acquireSharedLock();
/*     */     try {
/* 318 */       long l = Zstd.setSequenceProducerFallback(this.nativePtr, paramBoolean);
/* 319 */       if (Zstd.isError(l)) {
/* 320 */         throw new ZstdException(l);
/*     */       }
/*     */     } finally {
/* 323 */       releaseSharedLock();
/*     */     } 
/* 325 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZstdCompressCtx setSearchForExternalRepcodes(Zstd.ParamSwitch paramParamSwitch) {
/* 334 */     ensureOpen();
/* 335 */     acquireSharedLock();
/*     */     try {
/* 337 */       long l = Zstd.setSearchForExternalRepcodes(this.nativePtr, paramParamSwitch.getValue());
/* 338 */       if (Zstd.isError(l)) {
/* 339 */         throw new ZstdException(l);
/*     */       }
/*     */     } finally {
/* 342 */       releaseSharedLock();
/*     */     } 
/* 344 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZstdCompressCtx setValidateSequences(Zstd.ParamSwitch paramParamSwitch) {
/* 353 */     ensureOpen();
/* 354 */     acquireSharedLock();
/*     */     try {
/* 356 */       long l = Zstd.setValidateSequences(this.nativePtr, paramParamSwitch.getValue());
/* 357 */       if (Zstd.isError(l)) {
/* 358 */         throw new ZstdException(l);
/*     */       }
/*     */     } finally {
/* 361 */       releaseSharedLock();
/*     */     } 
/* 363 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZstdCompressCtx setEnableLongDistanceMatching(Zstd.ParamSwitch paramParamSwitch) {
/* 371 */     ensureOpen();
/* 372 */     acquireSharedLock();
/*     */     try {
/* 374 */       long l = Zstd.setEnableLongDistanceMatching(this.nativePtr, paramParamSwitch.getValue());
/* 375 */       if (Zstd.isError(l)) {
/* 376 */         throw new ZstdException(l);
/*     */       }
/*     */     } finally {
/* 379 */       releaseSharedLock();
/*     */     } 
/* 381 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   long getNativePtr() {
/* 386 */     return this.nativePtr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZstdCompressCtx loadDict(ZstdDictCompress paramZstdDictCompress) {
/* 395 */     ensureOpen();
/* 396 */     acquireSharedLock();
/* 397 */     paramZstdDictCompress.acquireSharedLock();
/*     */     try {
/* 399 */       long l = loadCDictFast0(this.nativePtr, paramZstdDictCompress);
/* 400 */       if (Zstd.isError(l)) {
/* 401 */         throw new ZstdException(l);
/*     */       }
/*     */       
/* 404 */       this.compression_dict = paramZstdDictCompress;
/*     */     } finally {
/* 406 */       paramZstdDictCompress.releaseSharedLock();
/* 407 */       releaseSharedLock();
/*     */     } 
/* 409 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZstdCompressCtx loadDict(byte[] paramArrayOfbyte) {
/* 419 */     ensureOpen();
/* 420 */     acquireSharedLock();
/*     */     try {
/* 422 */       long l = loadCDict0(this.nativePtr, paramArrayOfbyte);
/* 423 */       if (Zstd.isError(l)) {
/* 424 */         throw new ZstdException(l);
/*     */       }
/* 426 */       this.compression_dict = null;
/*     */     } finally {
/* 428 */       releaseSharedLock();
/*     */     } 
/* 430 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZstdFrameProgression getFrameProgression() {
/* 439 */     ensureOpen();
/* 440 */     acquireSharedLock();
/*     */     try {
/* 442 */       return getFrameProgression0(this.nativePtr);
/*     */     } finally {
/* 444 */       releaseSharedLock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() {
/* 454 */     ensureOpen();
/* 455 */     acquireSharedLock();
/*     */     try {
/* 457 */       long l = reset0(this.nativePtr);
/* 458 */       if (Zstd.isError(l)) {
/* 459 */         throw new ZstdException(l);
/*     */       }
/*     */     } finally {
/* 462 */       releaseSharedLock();
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
/*     */   public void setPledgedSrcSize(long paramLong) {
/* 477 */     ensureOpen();
/* 478 */     acquireSharedLock();
/*     */     try {
/* 480 */       long l = setPledgedSrcSize0(this.nativePtr, paramLong);
/* 481 */       if (Zstd.isError(l)) {
/* 482 */         throw new ZstdException(l);
/*     */       }
/*     */     } finally {
/* 485 */       releaseSharedLock();
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
/*     */   public boolean compressDirectByteBufferStream(ByteBuffer paramByteBuffer1, ByteBuffer paramByteBuffer2, EndDirective paramEndDirective) {
/* 500 */     ensureOpen();
/* 501 */     acquireSharedLock();
/*     */     try {
/* 503 */       long l = compressDirectByteBufferStream0(this.nativePtr, paramByteBuffer1, paramByteBuffer1.position(), paramByteBuffer1.limit(), paramByteBuffer2, paramByteBuffer2.position(), paramByteBuffer2.limit(), paramEndDirective.value());
/* 504 */       if ((l & 0x80000000L) != 0L) {
/* 505 */         long l1 = -(l & 0xFFL);
/* 506 */         throw new ZstdException(l1, Zstd.getErrorName(l1));
/*     */       } 
/* 508 */       paramByteBuffer2.position((int)(l & 0x7FFFFFFFL));
/* 509 */       paramByteBuffer1.position((int)(l >>> 32L) & Integer.MAX_VALUE);
/* 510 */       return (l >>> 63L == 1L);
/*     */     } finally {
/* 512 */       releaseSharedLock();
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
/*     */   
/*     */   public int compressDirectByteBuffer(ByteBuffer paramByteBuffer1, int paramInt1, int paramInt2, ByteBuffer paramByteBuffer2, int paramInt3, int paramInt4) {
/* 542 */     ensureOpen();
/* 543 */     if (!paramByteBuffer2.isDirect()) {
/* 544 */       throw new IllegalArgumentException("srcBuff must be a direct buffer");
/*     */     }
/* 546 */     if (!paramByteBuffer1.isDirect()) {
/* 547 */       throw new IllegalArgumentException("dstBuff must be a direct buffer");
/*     */     }
/* 549 */     Objects.checkFromIndexSize(paramInt3, paramInt4, paramByteBuffer2.limit());
/* 550 */     Objects.checkFromIndexSize(paramInt1, paramInt2, paramByteBuffer1.limit());
/*     */     
/* 552 */     acquireSharedLock();
/*     */     
/*     */     try {
/* 555 */       long l = compressDirectByteBuffer0(this.nativePtr, paramByteBuffer1, paramInt1, paramInt2, paramByteBuffer2, paramInt3, paramInt4);
/* 556 */       if (Zstd.isError(l)) {
/* 557 */         throw new ZstdException(l);
/*     */       }
/* 559 */       if (l > 2147483647L) {
/* 560 */         throw new ZstdException(Zstd.errGeneric(), "Output size is greater than MAX_INT");
/*     */       }
/* 562 */       return (int)l;
/*     */     } finally {
/* 564 */       releaseSharedLock();
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
/*     */   public int compressByteArray(byte[] paramArrayOfbyte1, int paramInt1, int paramInt2, byte[] paramArrayOfbyte2, int paramInt3, int paramInt4) {
/* 586 */     Objects.checkFromIndexSize(paramInt3, paramInt4, paramArrayOfbyte2.length);
/* 587 */     Objects.checkFromIndexSize(paramInt1, paramInt2, paramArrayOfbyte1.length);
/*     */     
/* 589 */     ensureOpen();
/* 590 */     acquireSharedLock();
/*     */     
/*     */     try {
/* 593 */       long l = compressByteArray0(this.nativePtr, paramArrayOfbyte1, paramInt1, paramInt2, paramArrayOfbyte2, paramInt3, paramInt4);
/* 594 */       if (Zstd.isError(l)) {
/* 595 */         throw new ZstdException(l);
/*     */       }
/* 597 */       if (l > 2147483647L) {
/* 598 */         throw new ZstdException(Zstd.errGeneric(), "Output size is greater than MAX_INT");
/*     */       }
/* 600 */       return (int)l;
/*     */     } finally {
/* 602 */       releaseSharedLock();
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
/*     */   public int compress(ByteBuffer paramByteBuffer1, ByteBuffer paramByteBuffer2) {
/* 628 */     int i = compressDirectByteBuffer(paramByteBuffer1, paramByteBuffer1
/* 629 */         .position(), paramByteBuffer1
/* 630 */         .limit() - paramByteBuffer1.position(), paramByteBuffer2, paramByteBuffer2
/*     */         
/* 632 */         .position(), paramByteBuffer2
/* 633 */         .limit() - paramByteBuffer2.position());
/*     */     
/* 635 */     paramByteBuffer2.position(paramByteBuffer2.limit());
/* 636 */     paramByteBuffer1.position(paramByteBuffer1.position() + i);
/* 637 */     return i;
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
/*     */   public ByteBuffer compress(ByteBuffer paramByteBuffer) throws ZstdException {
/* 652 */     long l = Zstd.compressBound((paramByteBuffer.limit() - paramByteBuffer.position()));
/* 653 */     if (l > 2147483647L) {
/* 654 */       throw new ZstdException(Zstd.errGeneric(), "Max output size is greater than MAX_INT");
/*     */     }
/* 656 */     ByteBuffer byteBuffer = ByteBuffer.allocateDirect((int)l);
/* 657 */     int i = compressDirectByteBuffer(byteBuffer, 0, (int)l, paramByteBuffer, paramByteBuffer
/*     */ 
/*     */ 
/*     */         
/* 661 */         .position(), paramByteBuffer
/* 662 */         .limit() - paramByteBuffer.position());
/*     */     
/* 664 */     paramByteBuffer.position(paramByteBuffer.limit());
/*     */     
/* 666 */     byteBuffer.limit(i);
/*     */ 
/*     */ 
/*     */     
/* 670 */     return byteBuffer;
/*     */   }
/*     */   
/*     */   public int compress(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) {
/* 674 */     return compressByteArray(paramArrayOfbyte1, 0, paramArrayOfbyte1.length, paramArrayOfbyte2, 0, paramArrayOfbyte2.length);
/*     */   }
/*     */   
/*     */   public byte[] compress(byte[] paramArrayOfbyte) {
/* 678 */     long l = Zstd.compressBound(paramArrayOfbyte.length);
/* 679 */     if (l > 2147483647L) {
/* 680 */       throw new ZstdException(Zstd.errGeneric(), "Max output size is greater than MAX_INT");
/*     */     }
/* 682 */     byte[] arrayOfByte = new byte[(int)l];
/* 683 */     int i = compressByteArray(arrayOfByte, 0, arrayOfByte.length, paramArrayOfbyte, 0, paramArrayOfbyte.length);
/* 684 */     return Arrays.copyOfRange(arrayOfByte, 0, i);
/*     */   }
/*     */   
/*     */   private static native long init();
/*     */   
/*     */   private static native void free(long paramLong);
/*     */   
/*     */   private static native void setLevel0(long paramLong, int paramInt);
/*     */   
/*     */   private static native void setChecksum0(long paramLong, boolean paramBoolean);
/*     */   
/*     */   private static native void setContentSize0(long paramLong, boolean paramBoolean);
/*     */   
/*     */   private static native void setDictID0(long paramLong, boolean paramBoolean);
/*     */   
/*     */   private native long loadCDictFast0(long paramLong, ZstdDictCompress paramZstdDictCompress);
/*     */   
/*     */   private native long loadCDict0(long paramLong, byte[] paramArrayOfbyte);
/*     */   
/*     */   private static native ZstdFrameProgression getFrameProgression0(long paramLong);
/*     */   
/*     */   private static native long reset0(long paramLong);
/*     */   
/*     */   private static native long setPledgedSrcSize0(long paramLong1, long paramLong2);
/*     */   
/*     */   private static native long compressDirectByteBufferStream0(long paramLong, ByteBuffer paramByteBuffer1, int paramInt1, int paramInt2, ByteBuffer paramByteBuffer2, int paramInt3, int paramInt4, int paramInt5);
/*     */   
/*     */   private static native long compressDirectByteBuffer0(long paramLong, ByteBuffer paramByteBuffer1, int paramInt1, int paramInt2, ByteBuffer paramByteBuffer2, int paramInt3, int paramInt4);
/*     */   
/*     */   private static native long compressByteArray0(long paramLong, byte[] paramArrayOfbyte1, int paramInt1, int paramInt2, byte[] paramArrayOfbyte2, int paramInt3, int paramInt4);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\github\luben\zstd\ZstdCompressCtx.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */