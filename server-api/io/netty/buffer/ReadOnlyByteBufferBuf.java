/*     */ package io.netty.buffer;
/*     */ 
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.ReadOnlyBufferException;
/*     */ import java.nio.channels.FileChannel;
/*     */ import java.nio.channels.GatheringByteChannel;
/*     */ import java.nio.channels.ScatteringByteChannel;
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
/*     */ class ReadOnlyByteBufferBuf
/*     */   extends AbstractReferenceCountedByteBuf
/*     */ {
/*     */   protected final ByteBuffer buffer;
/*     */   private final ByteBufAllocator allocator;
/*     */   private ByteBuffer tmpNioBuf;
/*     */   
/*     */   ReadOnlyByteBufferBuf(ByteBufAllocator allocator, ByteBuffer buffer) {
/*  41 */     super(buffer.remaining());
/*  42 */     if (!buffer.isReadOnly()) {
/*  43 */       throw new IllegalArgumentException("must be a readonly buffer: " + StringUtil.simpleClassName(buffer));
/*     */     }
/*     */     
/*  46 */     this.allocator = allocator;
/*  47 */     this.buffer = buffer.slice().order(ByteOrder.BIG_ENDIAN);
/*  48 */     writerIndex(this.buffer.limit());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void deallocate() {}
/*     */ 
/*     */   
/*     */   public boolean isWritable() {
/*  56 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isWritable(int numBytes) {
/*  61 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf ensureWritable(int minWritableBytes) {
/*  66 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */ 
/*     */   
/*     */   public int ensureWritable(int minWritableBytes, boolean force) {
/*  71 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getByte(int index) {
/*  76 */     ensureAccessible();
/*  77 */     return _getByte(index);
/*     */   }
/*     */ 
/*     */   
/*     */   protected byte _getByte(int index) {
/*  82 */     return this.buffer.get(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public short getShort(int index) {
/*  87 */     ensureAccessible();
/*  88 */     return _getShort(index);
/*     */   }
/*     */ 
/*     */   
/*     */   protected short _getShort(int index) {
/*  93 */     return this.buffer.getShort(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public short getShortLE(int index) {
/*  98 */     ensureAccessible();
/*  99 */     return _getShortLE(index);
/*     */   }
/*     */ 
/*     */   
/*     */   protected short _getShortLE(int index) {
/* 104 */     return ByteBufUtil.swapShort(this.buffer.getShort(index));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getUnsignedMedium(int index) {
/* 109 */     ensureAccessible();
/* 110 */     return _getUnsignedMedium(index);
/*     */   }
/*     */ 
/*     */   
/*     */   protected int _getUnsignedMedium(int index) {
/* 115 */     return (getByte(index) & 0xFF) << 16 | (
/* 116 */       getByte(index + 1) & 0xFF) << 8 | 
/* 117 */       getByte(index + 2) & 0xFF;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getUnsignedMediumLE(int index) {
/* 122 */     ensureAccessible();
/* 123 */     return _getUnsignedMediumLE(index);
/*     */   }
/*     */ 
/*     */   
/*     */   protected int _getUnsignedMediumLE(int index) {
/* 128 */     return getByte(index) & 0xFF | (
/* 129 */       getByte(index + 1) & 0xFF) << 8 | (
/* 130 */       getByte(index + 2) & 0xFF) << 16;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getInt(int index) {
/* 135 */     ensureAccessible();
/* 136 */     return _getInt(index);
/*     */   }
/*     */ 
/*     */   
/*     */   protected int _getInt(int index) {
/* 141 */     return this.buffer.getInt(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getIntLE(int index) {
/* 146 */     ensureAccessible();
/* 147 */     return _getIntLE(index);
/*     */   }
/*     */ 
/*     */   
/*     */   protected int _getIntLE(int index) {
/* 152 */     return ByteBufUtil.swapInt(this.buffer.getInt(index));
/*     */   }
/*     */ 
/*     */   
/*     */   public long getLong(int index) {
/* 157 */     ensureAccessible();
/* 158 */     return _getLong(index);
/*     */   }
/*     */ 
/*     */   
/*     */   protected long _getLong(int index) {
/* 163 */     return this.buffer.getLong(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public long getLongLE(int index) {
/* 168 */     ensureAccessible();
/* 169 */     return _getLongLE(index);
/*     */   }
/*     */ 
/*     */   
/*     */   protected long _getLongLE(int index) {
/* 174 */     return ByteBufUtil.swapLong(this.buffer.getLong(index));
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, ByteBuf dst, int dstIndex, int length) {
/* 179 */     return getBytes(index, dst, dstIndex, length, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf readBytes(ByteBuf dst, int dstIndex, int length) {
/* 184 */     checkReadableBytes(length);
/* 185 */     getBytes(this.readerIndex, dst, dstIndex, length, true);
/* 186 */     this.readerIndex += length;
/* 187 */     return this;
/*     */   }
/*     */   
/*     */   protected ByteBuf getBytes(int index, ByteBuf dst, int dstIndex, int length, boolean internal) {
/* 191 */     checkDstIndex(index, length, dstIndex, dst.capacity());
/* 192 */     if (dst.hasArray()) {
/* 193 */       getBytes(index, dst.array(), dst.arrayOffset() + dstIndex, length);
/* 194 */     } else if (dst.nioBufferCount() > 0) {
/* 195 */       for (ByteBuffer bb : dst.nioBuffers(dstIndex, length)) {
/* 196 */         int bbLen = bb.remaining();
/* 197 */         getBytes(index, bb, internal);
/* 198 */         index += bbLen;
/*     */       } 
/*     */     } else {
/* 201 */       dst.setBytes(dstIndex, this, index, length);
/*     */     } 
/* 203 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, byte[] dst, int dstIndex, int length) {
/* 208 */     return getBytes(index, dst, dstIndex, length, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf readBytes(byte[] dst, int dstIndex, int length) {
/* 213 */     checkReadableBytes(length);
/* 214 */     getBytes(this.readerIndex, dst, dstIndex, length, true);
/* 215 */     this.readerIndex += length;
/* 216 */     return this;
/*     */   }
/*     */   
/*     */   protected ByteBuf getBytes(int index, byte[] dst, int dstIndex, int length, boolean internal) {
/* 220 */     checkDstIndex(index, length, dstIndex, dst.length);
/*     */     
/* 222 */     ByteBuffer tmpBuf = nioBuffer(internal);
/* 223 */     tmpBuf.clear().position(index).limit(index + length);
/* 224 */     tmpBuf.get(dst, dstIndex, length);
/* 225 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, ByteBuffer dst) {
/* 230 */     return getBytes(index, dst, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf readBytes(ByteBuffer dst) {
/* 235 */     int length = dst.remaining();
/* 236 */     checkReadableBytes(length);
/* 237 */     getBytes(this.readerIndex, dst, true);
/* 238 */     this.readerIndex += length;
/* 239 */     return this;
/*     */   }
/*     */   
/*     */   private ByteBuf getBytes(int index, ByteBuffer dst, boolean internal) {
/* 243 */     checkIndex(index, dst.remaining());
/*     */     
/* 245 */     ByteBuffer tmpBuf = nioBuffer(internal);
/* 246 */     tmpBuf.clear().position(index).limit(index + dst.remaining());
/* 247 */     dst.put(tmpBuf);
/* 248 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setByte(int index, int value) {
/* 253 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setByte(int index, int value) {
/* 258 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setShort(int index, int value) {
/* 263 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setShort(int index, int value) {
/* 268 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setShortLE(int index, int value) {
/* 273 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setShortLE(int index, int value) {
/* 278 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setMedium(int index, int value) {
/* 283 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setMedium(int index, int value) {
/* 288 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setMediumLE(int index, int value) {
/* 293 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setMediumLE(int index, int value) {
/* 298 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setInt(int index, int value) {
/* 303 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setInt(int index, int value) {
/* 308 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setIntLE(int index, int value) {
/* 313 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setIntLE(int index, int value) {
/* 318 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setLong(int index, long value) {
/* 323 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setLong(int index, long value) {
/* 328 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setLongLE(int index, long value) {
/* 333 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setLongLE(int index, long value) {
/* 338 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */ 
/*     */   
/*     */   public int capacity() {
/* 343 */     return maxCapacity();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf capacity(int newCapacity) {
/* 348 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBufAllocator alloc() {
/* 353 */     return this.allocator;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteOrder order() {
/* 358 */     return ByteOrder.BIG_ENDIAN;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf unwrap() {
/* 363 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isReadOnly() {
/* 368 */     return this.buffer.isReadOnly();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDirect() {
/* 373 */     return this.buffer.isDirect();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, OutputStream out, int length) throws IOException {
/* 378 */     return getBytes(index, out, length, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf readBytes(OutputStream out, int length) throws IOException {
/* 383 */     checkReadableBytes(length);
/* 384 */     getBytes(this.readerIndex, out, length, true);
/* 385 */     this.readerIndex += length;
/* 386 */     return this;
/*     */   }
/*     */   
/*     */   private ByteBuf getBytes(int index, OutputStream out, int length, boolean internal) throws IOException {
/* 390 */     ensureAccessible();
/* 391 */     if (length == 0) {
/* 392 */       return this;
/*     */     }
/*     */     
/* 395 */     if (this.buffer.hasArray()) {
/* 396 */       out.write(this.buffer.array(), index + this.buffer.arrayOffset(), length);
/*     */     } else {
/* 398 */       byte[] tmp = ByteBufUtil.threadLocalTempArray(length);
/* 399 */       ByteBuffer tmpBuf = nioBuffer(internal);
/* 400 */       tmpBuf.clear().position(index);
/* 401 */       tmpBuf.get(tmp, 0, length);
/* 402 */       out.write(tmp, 0, length);
/*     */     } 
/* 404 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBytes(int index, GatheringByteChannel out, int length) throws IOException {
/* 409 */     return getBytes(index, out, length, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public int readBytes(GatheringByteChannel out, int length) throws IOException {
/* 414 */     checkReadableBytes(length);
/* 415 */     int readBytes = getBytes(this.readerIndex, out, length, true);
/* 416 */     this.readerIndex += readBytes;
/* 417 */     return readBytes;
/*     */   }
/*     */   
/*     */   private int getBytes(int index, GatheringByteChannel out, int length, boolean internal) throws IOException {
/* 421 */     ensureAccessible();
/* 422 */     if (length == 0) {
/* 423 */       return 0;
/*     */     }
/*     */     
/* 426 */     ByteBuffer tmpBuf = nioBuffer(internal);
/* 427 */     tmpBuf.clear().position(index).limit(index + length);
/* 428 */     return out.write(tmpBuf);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBytes(int index, FileChannel out, long position, int length) throws IOException {
/* 433 */     return getBytes(index, out, position, length, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public int readBytes(FileChannel out, long position, int length) throws IOException {
/* 438 */     checkReadableBytes(length);
/* 439 */     int readBytes = getBytes(this.readerIndex, out, position, length, true);
/* 440 */     this.readerIndex += readBytes;
/* 441 */     return readBytes;
/*     */   }
/*     */   
/*     */   private int getBytes(int index, FileChannel out, long position, int length, boolean internal) throws IOException {
/* 445 */     ensureAccessible();
/* 446 */     if (length == 0) {
/* 447 */       return 0;
/*     */     }
/*     */     
/* 450 */     ByteBuffer tmpBuf = nioBuffer(internal);
/* 451 */     tmpBuf.clear().position(index).limit(index + length);
/* 452 */     return out.write(tmpBuf, position);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setBytes(int index, ByteBuf src, int srcIndex, int length) {
/* 457 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setBytes(int index, byte[] src, int srcIndex, int length) {
/* 462 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setBytes(int index, ByteBuffer src) {
/* 467 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */ 
/*     */   
/*     */   public int setBytes(int index, InputStream in, int length) throws IOException {
/* 472 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */ 
/*     */   
/*     */   public int setBytes(int index, ScatteringByteChannel in, int length) throws IOException {
/* 477 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */ 
/*     */   
/*     */   public int setBytes(int index, FileChannel in, long position, int length) throws IOException {
/* 482 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */   
/*     */   protected final ByteBuffer internalNioBuffer() {
/* 486 */     ByteBuffer tmpNioBuf = this.tmpNioBuf;
/* 487 */     if (tmpNioBuf == null) {
/* 488 */       this.tmpNioBuf = tmpNioBuf = this.buffer.duplicate();
/*     */     }
/* 490 */     return tmpNioBuf;
/*     */   }
/*     */   
/*     */   public ByteBuf copy(int index, int length) {
/*     */     ByteBuffer src;
/* 495 */     ensureAccessible();
/*     */ 
/*     */     
/*     */     try {
/* 499 */       src = (ByteBuffer)this.buffer.duplicate().clear().position(index).limit(index + length);
/* 500 */     } catch (IllegalArgumentException ignored) {
/* 501 */       throw new IndexOutOfBoundsException("Too many bytes to read - Need " + (index + length));
/*     */     } 
/*     */     
/* 504 */     ByteBuf dst = src.isDirect() ? alloc().directBuffer(length) : alloc().heapBuffer(length);
/* 505 */     dst.writeBytes(src);
/* 506 */     return dst;
/*     */   }
/*     */ 
/*     */   
/*     */   public int nioBufferCount() {
/* 511 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer[] nioBuffers(int index, int length) {
/* 516 */     return new ByteBuffer[] { nioBuffer(index, length) };
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer nioBuffer(int index, int length) {
/* 521 */     checkIndex(index, length);
/* 522 */     return (ByteBuffer)this.buffer.duplicate().position(index).limit(index + length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer internalNioBuffer(int index, int length) {
/* 527 */     ensureAccessible();
/* 528 */     return (ByteBuffer)internalNioBuffer().clear().position(index).limit(index + length);
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean isContiguous() {
/* 533 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasArray() {
/* 538 */     return this.buffer.hasArray();
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] array() {
/* 543 */     return this.buffer.array();
/*     */   }
/*     */ 
/*     */   
/*     */   public int arrayOffset() {
/* 548 */     return this.buffer.arrayOffset();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasMemoryAddress() {
/* 553 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public long memoryAddress() {
/* 558 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   private ByteBuffer nioBuffer(boolean internal) {
/* 562 */     return internal ? internalNioBuffer() : this.buffer.duplicate();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf duplicate() {
/* 567 */     return new ReadOnlyDuplicatedByteBuf(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf slice(int index, int length) {
/* 572 */     return new ReadOnlySlicedByteBuf(this, index, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf asReadOnly() {
/* 577 */     return this;
/*     */   }
/*     */   
/*     */   private static final class ReadOnlySlicedByteBuf
/*     */     extends SlicedByteBuf {
/*     */     ReadOnlySlicedByteBuf(ByteBuf buffer, int index, int length) {
/* 583 */       super(buffer, index, length);
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteBuf asReadOnly() {
/* 588 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteBuf slice(int index, int length) {
/* 593 */       return new ReadOnlySlicedByteBuf(this, index, length);
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteBuf duplicate() {
/* 598 */       return slice(0, capacity()).setIndex(readerIndex(), writerIndex());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isWritable() {
/* 603 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isWritable(int numBytes) {
/* 608 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public int ensureWritable(int minWritableBytes, boolean force) {
/* 613 */       return 1;
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class ReadOnlyDuplicatedByteBuf
/*     */     extends DuplicatedByteBuf {
/*     */     ReadOnlyDuplicatedByteBuf(ByteBuf buffer) {
/* 620 */       super(buffer);
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteBuf asReadOnly() {
/* 625 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteBuf slice(int index, int length) {
/* 630 */       return new ReadOnlyByteBufferBuf.ReadOnlySlicedByteBuf(this, index, length);
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteBuf duplicate() {
/* 635 */       return new ReadOnlyDuplicatedByteBuf(this);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isWritable() {
/* 640 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isWritable(int numBytes) {
/* 645 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public int ensureWritable(int minWritableBytes, boolean force) {
/* 650 */       return 1;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\buffer\ReadOnlyByteBufferBuf.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */