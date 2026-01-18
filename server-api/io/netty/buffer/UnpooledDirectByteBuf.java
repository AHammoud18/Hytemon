/*     */ package io.netty.buffer;
/*     */ 
/*     */ import io.netty.util.internal.CleanableDirectBuffer;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.channels.ClosedChannelException;
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
/*     */ public class UnpooledDirectByteBuf
/*     */   extends AbstractReferenceCountedByteBuf
/*     */ {
/*     */   private final ByteBufAllocator alloc;
/*     */   CleanableDirectBuffer cleanable;
/*     */   ByteBuffer buffer;
/*     */   private ByteBuffer tmpNioBuf;
/*     */   private int capacity;
/*     */   private boolean doNotFree;
/*     */   private final boolean allowSectionedInternalNioBufferAccess;
/*     */   
/*     */   public UnpooledDirectByteBuf(ByteBufAllocator alloc, int initialCapacity, int maxCapacity) {
/*  57 */     this(alloc, initialCapacity, maxCapacity, true);
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
/*     */   UnpooledDirectByteBuf(ByteBufAllocator alloc, int initialCapacity, int maxCapacity, boolean allowSectionedInternalNioBufferAccess) {
/*  71 */     super(maxCapacity);
/*  72 */     ObjectUtil.checkNotNull(alloc, "alloc");
/*  73 */     ObjectUtil.checkPositiveOrZero(initialCapacity, "initialCapacity");
/*  74 */     ObjectUtil.checkPositiveOrZero(maxCapacity, "maxCapacity");
/*  75 */     if (initialCapacity > maxCapacity) {
/*  76 */       throw new IllegalArgumentException(String.format("initialCapacity(%d) > maxCapacity(%d)", new Object[] {
/*  77 */               Integer.valueOf(initialCapacity), Integer.valueOf(maxCapacity)
/*     */             }));
/*     */     }
/*  80 */     this.alloc = alloc;
/*  81 */     setByteBuffer(allocateDirectBuffer(initialCapacity), false);
/*  82 */     this.allowSectionedInternalNioBufferAccess = allowSectionedInternalNioBufferAccess;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected UnpooledDirectByteBuf(ByteBufAllocator alloc, ByteBuffer initialBuffer, int maxCapacity) {
/*  91 */     this(alloc, initialBuffer, maxCapacity, false, true);
/*     */   }
/*     */ 
/*     */   
/*     */   UnpooledDirectByteBuf(ByteBufAllocator alloc, ByteBuffer initialBuffer, int maxCapacity, boolean doFree, boolean slice) {
/*  96 */     super(maxCapacity);
/*  97 */     ObjectUtil.checkNotNull(alloc, "alloc");
/*  98 */     ObjectUtil.checkNotNull(initialBuffer, "initialBuffer");
/*  99 */     if (!initialBuffer.isDirect()) {
/* 100 */       throw new IllegalArgumentException("initialBuffer is not a direct buffer.");
/*     */     }
/* 102 */     if (initialBuffer.isReadOnly()) {
/* 103 */       throw new IllegalArgumentException("initialBuffer is a read-only buffer.");
/*     */     }
/*     */     
/* 106 */     int initialCapacity = initialBuffer.remaining();
/* 107 */     if (initialCapacity > maxCapacity) {
/* 108 */       throw new IllegalArgumentException(String.format("initialCapacity(%d) > maxCapacity(%d)", new Object[] {
/* 109 */               Integer.valueOf(initialCapacity), Integer.valueOf(maxCapacity)
/*     */             }));
/*     */     }
/* 112 */     this.alloc = alloc;
/* 113 */     this.doNotFree = !doFree;
/* 114 */     setByteBuffer((slice ? initialBuffer.slice() : initialBuffer).order(ByteOrder.BIG_ENDIAN), false);
/* 115 */     writerIndex(initialCapacity);
/* 116 */     this.allowSectionedInternalNioBufferAccess = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected ByteBuffer allocateDirect(int initialCapacity) {
/* 125 */     return ByteBuffer.allocateDirect(initialCapacity);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected void freeDirect(ByteBuffer buffer) {
/* 134 */     PlatformDependent.freeDirectBuffer(buffer);
/*     */   }
/*     */   
/*     */   protected CleanableDirectBuffer allocateDirectBuffer(int capacity) {
/* 138 */     return PlatformDependent.allocateDirect(capacity);
/*     */   }
/*     */   
/*     */   void setByteBuffer(CleanableDirectBuffer cleanableDirectBuffer, boolean tryFree) {
/* 142 */     if (tryFree) {
/* 143 */       CleanableDirectBuffer oldCleanable = this.cleanable;
/* 144 */       ByteBuffer oldBuffer = this.buffer;
/* 145 */       if (oldBuffer != null) {
/* 146 */         if (this.doNotFree) {
/* 147 */           this.doNotFree = false;
/*     */         }
/* 149 */         else if (oldCleanable != null) {
/* 150 */           oldCleanable.clean();
/*     */         } else {
/* 152 */           freeDirect(oldBuffer);
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 158 */     this.cleanable = cleanableDirectBuffer;
/* 159 */     this.buffer = cleanableDirectBuffer.buffer();
/* 160 */     this.tmpNioBuf = null;
/* 161 */     this.capacity = this.buffer.remaining();
/*     */   }
/*     */   
/*     */   void setByteBuffer(ByteBuffer buffer, boolean tryFree) {
/* 165 */     if (tryFree) {
/* 166 */       ByteBuffer oldBuffer = this.buffer;
/* 167 */       if (oldBuffer != null) {
/* 168 */         if (this.doNotFree) {
/* 169 */           this.doNotFree = false;
/*     */         } else {
/* 171 */           freeDirect(oldBuffer);
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 176 */     this.buffer = buffer;
/* 177 */     this.tmpNioBuf = null;
/* 178 */     this.capacity = buffer.remaining();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDirect() {
/* 183 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int capacity() {
/* 188 */     return this.capacity;
/*     */   }
/*     */   
/*     */   public ByteBuf capacity(int newCapacity) {
/*     */     int bytesToCopy;
/* 193 */     checkNewCapacity(newCapacity);
/* 194 */     int oldCapacity = this.capacity;
/* 195 */     if (newCapacity == oldCapacity) {
/* 196 */       return this;
/*     */     }
/*     */     
/* 199 */     if (newCapacity > oldCapacity) {
/* 200 */       bytesToCopy = oldCapacity;
/*     */     } else {
/* 202 */       trimIndicesToCapacity(newCapacity);
/* 203 */       bytesToCopy = newCapacity;
/*     */     } 
/* 205 */     ByteBuffer oldBuffer = this.buffer;
/* 206 */     CleanableDirectBuffer newBuffer = allocateDirectBuffer(newCapacity);
/* 207 */     oldBuffer.position(0).limit(bytesToCopy);
/* 208 */     newBuffer.buffer().position(0).limit(bytesToCopy);
/* 209 */     newBuffer.buffer().put(oldBuffer).clear();
/* 210 */     setByteBuffer(newBuffer, true);
/* 211 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBufAllocator alloc() {
/* 216 */     return this.alloc;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteOrder order() {
/* 221 */     return ByteOrder.BIG_ENDIAN;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasArray() {
/* 226 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] array() {
/* 231 */     throw new UnsupportedOperationException("direct buffer");
/*     */   }
/*     */ 
/*     */   
/*     */   public int arrayOffset() {
/* 236 */     throw new UnsupportedOperationException("direct buffer");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasMemoryAddress() {
/* 241 */     CleanableDirectBuffer cleanable = this.cleanable;
/* 242 */     return (cleanable != null && cleanable.hasMemoryAddress());
/*     */   }
/*     */ 
/*     */   
/*     */   public long memoryAddress() {
/* 247 */     ensureAccessible();
/* 248 */     if (!hasMemoryAddress()) {
/* 249 */       throw new UnsupportedOperationException();
/*     */     }
/* 251 */     return this.cleanable.memoryAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getByte(int index) {
/* 256 */     ensureAccessible();
/* 257 */     return _getByte(index);
/*     */   }
/*     */ 
/*     */   
/*     */   protected byte _getByte(int index) {
/* 262 */     return this.buffer.get(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public short getShortLE(int index) {
/* 267 */     ensureAccessible();
/* 268 */     return _getShortLE(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public short getShort(int index) {
/* 273 */     ensureAccessible();
/* 274 */     return _getShort(index);
/*     */   }
/*     */ 
/*     */   
/*     */   protected short _getShort(int index) {
/* 279 */     if (PlatformDependent.hasVarHandle()) {
/* 280 */       return VarHandleByteBufferAccess.getShortBE(this.buffer, index);
/*     */     }
/* 282 */     return this.buffer.getShort(index);
/*     */   }
/*     */ 
/*     */   
/*     */   protected short _getShortLE(int index) {
/* 287 */     if (PlatformDependent.hasVarHandle()) {
/* 288 */       return VarHandleByteBufferAccess.getShortLE(this.buffer, index);
/*     */     }
/* 290 */     return ByteBufUtil.swapShort(this.buffer.getShort(index));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getUnsignedMedium(int index) {
/* 295 */     ensureAccessible();
/* 296 */     return _getUnsignedMedium(index);
/*     */   }
/*     */ 
/*     */   
/*     */   protected int _getUnsignedMedium(int index) {
/* 301 */     return (getByte(index) & 0xFF) << 16 | (
/* 302 */       getByte(index + 1) & 0xFF) << 8 | 
/* 303 */       getByte(index + 2) & 0xFF;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int _getUnsignedMediumLE(int index) {
/* 308 */     return getByte(index) & 0xFF | (
/* 309 */       getByte(index + 1) & 0xFF) << 8 | (
/* 310 */       getByte(index + 2) & 0xFF) << 16;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getIntLE(int index) {
/* 315 */     ensureAccessible();
/* 316 */     return _getIntLE(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getInt(int index) {
/* 321 */     ensureAccessible();
/* 322 */     return _getInt(index);
/*     */   }
/*     */ 
/*     */   
/*     */   protected int _getInt(int index) {
/* 327 */     if (PlatformDependent.hasVarHandle()) {
/* 328 */       return VarHandleByteBufferAccess.getIntBE(this.buffer, index);
/*     */     }
/* 330 */     return this.buffer.getInt(index);
/*     */   }
/*     */ 
/*     */   
/*     */   protected int _getIntLE(int index) {
/* 335 */     if (PlatformDependent.hasVarHandle()) {
/* 336 */       return VarHandleByteBufferAccess.getIntLE(this.buffer, index);
/*     */     }
/* 338 */     return ByteBufUtil.swapInt(this.buffer.getInt(index));
/*     */   }
/*     */ 
/*     */   
/*     */   public long getLongLE(int index) {
/* 343 */     ensureAccessible();
/* 344 */     return _getLongLE(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public long getLong(int index) {
/* 349 */     ensureAccessible();
/* 350 */     return _getLong(index);
/*     */   }
/*     */ 
/*     */   
/*     */   protected long _getLong(int index) {
/* 355 */     if (PlatformDependent.hasVarHandle()) {
/* 356 */       return VarHandleByteBufferAccess.getLongBE(this.buffer, index);
/*     */     }
/* 358 */     return this.buffer.getLong(index);
/*     */   }
/*     */ 
/*     */   
/*     */   protected long _getLongLE(int index) {
/* 363 */     if (PlatformDependent.hasVarHandle()) {
/* 364 */       return VarHandleByteBufferAccess.getLongLE(this.buffer, index);
/*     */     }
/* 366 */     return ByteBufUtil.swapLong(this.buffer.getLong(index));
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, ByteBuf dst, int dstIndex, int length) {
/* 371 */     checkDstIndex(index, length, dstIndex, dst.capacity());
/* 372 */     if (dst.hasArray()) {
/* 373 */       getBytes(index, dst.array(), dst.arrayOffset() + dstIndex, length);
/* 374 */     } else if (dst.nioBufferCount() > 0) {
/* 375 */       for (ByteBuffer bb : dst.nioBuffers(dstIndex, length)) {
/* 376 */         int bbLen = bb.remaining();
/* 377 */         getBytes(index, bb);
/* 378 */         index += bbLen;
/*     */       } 
/*     */     } else {
/* 381 */       dst.setBytes(dstIndex, this, index, length);
/*     */     } 
/* 383 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, byte[] dst, int dstIndex, int length) {
/* 388 */     getBytes(index, dst, dstIndex, length, false);
/* 389 */     return this;
/*     */   }
/*     */   void getBytes(int index, byte[] dst, int dstIndex, int length, boolean internal) {
/*     */     ByteBuffer tmpBuf;
/* 393 */     checkDstIndex(index, length, dstIndex, dst.length);
/*     */ 
/*     */     
/* 396 */     if (internal) {
/* 397 */       tmpBuf = internalNioBuffer(index, length);
/*     */     } else {
/* 399 */       tmpBuf = (ByteBuffer)this.buffer.duplicate().clear().position(index).limit(index + length);
/*     */     } 
/* 401 */     tmpBuf.get(dst, dstIndex, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf readBytes(byte[] dst, int dstIndex, int length) {
/* 406 */     checkReadableBytes(length);
/* 407 */     getBytes(this.readerIndex, dst, dstIndex, length, true);
/* 408 */     this.readerIndex += length;
/* 409 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, ByteBuffer dst) {
/* 414 */     getBytes(index, dst, false);
/* 415 */     return this;
/*     */   }
/*     */   void getBytes(int index, ByteBuffer dst, boolean internal) {
/*     */     ByteBuffer tmpBuf;
/* 419 */     checkIndex(index, dst.remaining());
/*     */ 
/*     */     
/* 422 */     if (internal) {
/* 423 */       tmpBuf = internalNioBuffer(index, dst.remaining());
/*     */     } else {
/* 425 */       tmpBuf = (ByteBuffer)this.buffer.duplicate().clear().position(index).limit(index + dst.remaining());
/*     */     } 
/* 427 */     dst.put(tmpBuf);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf readBytes(ByteBuffer dst) {
/* 432 */     int length = dst.remaining();
/* 433 */     checkReadableBytes(length);
/* 434 */     getBytes(this.readerIndex, dst, true);
/* 435 */     this.readerIndex += length;
/* 436 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setByte(int index, int value) {
/* 441 */     ensureAccessible();
/* 442 */     _setByte(index, value);
/* 443 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setByte(int index, int value) {
/* 448 */     this.buffer.put(index, (byte)(value & 0xFF));
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setShortLE(int index, int value) {
/* 453 */     ensureAccessible();
/* 454 */     _setShortLE(index, value);
/* 455 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setShort(int index, int value) {
/* 460 */     ensureAccessible();
/* 461 */     _setShort(index, value);
/* 462 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setShort(int index, int value) {
/* 467 */     if (PlatformDependent.hasVarHandle()) {
/* 468 */       VarHandleByteBufferAccess.setShortBE(this.buffer, index, value);
/*     */       return;
/*     */     } 
/* 471 */     this.buffer.putShort(index, (short)(value & 0xFFFF));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setShortLE(int index, int value) {
/* 476 */     if (PlatformDependent.hasVarHandle()) {
/* 477 */       VarHandleByteBufferAccess.setShortLE(this.buffer, index, value);
/*     */       return;
/*     */     } 
/* 480 */     this.buffer.putShort(index, ByteBufUtil.swapShort((short)value));
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setMediumLE(int index, int value) {
/* 485 */     ensureAccessible();
/* 486 */     _setMediumLE(index, value);
/* 487 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setMedium(int index, int value) {
/* 492 */     ensureAccessible();
/* 493 */     _setMedium(index, value);
/* 494 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setMedium(int index, int value) {
/* 499 */     setByte(index, (byte)(value >>> 16));
/* 500 */     setByte(index + 1, (byte)(value >>> 8));
/* 501 */     setByte(index + 2, (byte)value);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setMediumLE(int index, int value) {
/* 506 */     setByte(index, (byte)value);
/* 507 */     setByte(index + 1, (byte)(value >>> 8));
/* 508 */     setByte(index + 2, (byte)(value >>> 16));
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setIntLE(int index, int value) {
/* 513 */     ensureAccessible();
/* 514 */     _setIntLE(index, value);
/* 515 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setInt(int index, int value) {
/* 520 */     ensureAccessible();
/* 521 */     _setInt(index, value);
/* 522 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setInt(int index, int value) {
/* 527 */     if (PlatformDependent.hasVarHandle()) {
/* 528 */       VarHandleByteBufferAccess.setIntBE(this.buffer, index, value);
/*     */       return;
/*     */     } 
/* 531 */     this.buffer.putInt(index, value);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setIntLE(int index, int value) {
/* 536 */     if (PlatformDependent.hasVarHandle()) {
/* 537 */       VarHandleByteBufferAccess.setIntLE(this.buffer, index, value);
/*     */       return;
/*     */     } 
/* 540 */     this.buffer.putInt(index, ByteBufUtil.swapInt(value));
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setLong(int index, long value) {
/* 545 */     ensureAccessible();
/* 546 */     _setLong(index, value);
/* 547 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setLongLE(int index, long value) {
/* 552 */     ensureAccessible();
/* 553 */     _setLongLE(index, value);
/* 554 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setLong(int index, long value) {
/* 559 */     if (PlatformDependent.hasVarHandle()) {
/* 560 */       VarHandleByteBufferAccess.setLongBE(this.buffer, index, value);
/*     */       return;
/*     */     } 
/* 563 */     this.buffer.putLong(index, value);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setLongLE(int index, long value) {
/* 568 */     if (PlatformDependent.hasVarHandle()) {
/* 569 */       VarHandleByteBufferAccess.setLongLE(this.buffer, index, value);
/*     */       return;
/*     */     } 
/* 572 */     this.buffer.putLong(index, ByteBufUtil.swapLong(value));
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setBytes(int index, ByteBuf src, int srcIndex, int length) {
/* 577 */     checkSrcIndex(index, length, srcIndex, src.capacity());
/* 578 */     if (src.nioBufferCount() > 0) {
/* 579 */       for (ByteBuffer bb : src.nioBuffers(srcIndex, length)) {
/* 580 */         int bbLen = bb.remaining();
/* 581 */         setBytes(index, bb);
/* 582 */         index += bbLen;
/*     */       } 
/*     */     } else {
/* 585 */       src.getBytes(srcIndex, this, index, length);
/*     */     } 
/* 587 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setBytes(int index, byte[] src, int srcIndex, int length) {
/* 592 */     checkSrcIndex(index, length, srcIndex, src.length);
/* 593 */     ByteBuffer tmpBuf = internalNioBuffer(index, length);
/* 594 */     tmpBuf.put(src, srcIndex, length);
/* 595 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setBytes(int index, ByteBuffer src) {
/* 600 */     ensureAccessible();
/* 601 */     if (src == this.tmpNioBuf) {
/* 602 */       src = src.duplicate();
/*     */     }
/* 604 */     ByteBuffer tmpBuf = internalNioBuffer(index, src.remaining());
/* 605 */     tmpBuf.put(src);
/* 606 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, OutputStream out, int length) throws IOException {
/* 611 */     getBytes(index, out, length, false);
/* 612 */     return this;
/*     */   }
/*     */   
/*     */   void getBytes(int index, OutputStream out, int length, boolean internal) throws IOException {
/* 616 */     ensureAccessible();
/* 617 */     if (length == 0) {
/*     */       return;
/*     */     }
/* 620 */     ByteBufUtil.readBytes(alloc(), internal ? internalNioBuffer() : this.buffer.duplicate(), index, length, out);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf readBytes(OutputStream out, int length) throws IOException {
/* 625 */     checkReadableBytes(length);
/* 626 */     getBytes(this.readerIndex, out, length, true);
/* 627 */     this.readerIndex += length;
/* 628 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBytes(int index, GatheringByteChannel out, int length) throws IOException {
/* 633 */     return getBytes(index, out, length, false);
/*     */   }
/*     */   private int getBytes(int index, GatheringByteChannel out, int length, boolean internal) throws IOException {
/*     */     ByteBuffer tmpBuf;
/* 637 */     ensureAccessible();
/* 638 */     if (length == 0) {
/* 639 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 643 */     if (internal) {
/* 644 */       tmpBuf = internalNioBuffer(index, length);
/*     */     } else {
/* 646 */       tmpBuf = (ByteBuffer)this.buffer.duplicate().clear().position(index).limit(index + length);
/*     */     } 
/* 648 */     return out.write(tmpBuf);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBytes(int index, FileChannel out, long position, int length) throws IOException {
/* 653 */     return getBytes(index, out, position, length, false);
/*     */   }
/*     */   
/*     */   private int getBytes(int index, FileChannel out, long position, int length, boolean internal) throws IOException {
/* 657 */     ensureAccessible();
/* 658 */     if (length == 0) {
/* 659 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 663 */     ByteBuffer tmpBuf = internal ? internalNioBuffer(index, length) : (ByteBuffer)this.buffer.duplicate().clear().position(index).limit(index + length);
/* 664 */     return out.write(tmpBuf, position);
/*     */   }
/*     */ 
/*     */   
/*     */   public int readBytes(GatheringByteChannel out, int length) throws IOException {
/* 669 */     checkReadableBytes(length);
/* 670 */     int readBytes = getBytes(this.readerIndex, out, length, true);
/* 671 */     this.readerIndex += readBytes;
/* 672 */     return readBytes;
/*     */   }
/*     */ 
/*     */   
/*     */   public int readBytes(FileChannel out, long position, int length) throws IOException {
/* 677 */     checkReadableBytes(length);
/* 678 */     int readBytes = getBytes(this.readerIndex, out, position, length, true);
/* 679 */     this.readerIndex += readBytes;
/* 680 */     return readBytes;
/*     */   }
/*     */ 
/*     */   
/*     */   public int setBytes(int index, InputStream in, int length) throws IOException {
/* 685 */     ensureAccessible();
/* 686 */     if (this.buffer.hasArray()) {
/* 687 */       return in.read(this.buffer.array(), this.buffer.arrayOffset() + index, length);
/*     */     }
/* 689 */     byte[] tmp = ByteBufUtil.threadLocalTempArray(length);
/* 690 */     int readBytes = in.read(tmp, 0, length);
/* 691 */     if (readBytes <= 0) {
/* 692 */       return readBytes;
/*     */     }
/* 694 */     ByteBuffer tmpBuf = internalNioBuffer(index, readBytes);
/* 695 */     tmpBuf.put(tmp, 0, readBytes);
/* 696 */     return readBytes;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int setBytes(int index, ScatteringByteChannel in, int length) throws IOException {
/* 702 */     ensureAccessible();
/* 703 */     ByteBuffer tmpBuf = internalNioBuffer(index, length);
/*     */     try {
/* 705 */       return in.read(tmpBuf);
/* 706 */     } catch (ClosedChannelException ignored) {
/* 707 */       return -1;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int setBytes(int index, FileChannel in, long position, int length) throws IOException {
/* 713 */     ensureAccessible();
/* 714 */     ByteBuffer tmpBuf = internalNioBuffer(index, length);
/*     */     try {
/* 716 */       return in.read(tmpBuf, position);
/* 717 */     } catch (ClosedChannelException ignored) {
/* 718 */       return -1;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int nioBufferCount() {
/* 724 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer[] nioBuffers(int index, int length) {
/* 729 */     return new ByteBuffer[] { nioBuffer(index, length) };
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean isContiguous() {
/* 734 */     return true;
/*     */   }
/*     */   
/*     */   public ByteBuf copy(int index, int length) {
/*     */     ByteBuffer src;
/* 739 */     ensureAccessible();
/*     */     
/*     */     try {
/* 742 */       src = (ByteBuffer)this.buffer.duplicate().clear().position(index).limit(index + length);
/* 743 */     } catch (IllegalArgumentException ignored) {
/* 744 */       throw new IndexOutOfBoundsException("Too many bytes to read - Need " + (index + length));
/*     */     } 
/*     */     
/* 747 */     return alloc().directBuffer(length, maxCapacity()).writeBytes(src);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer internalNioBuffer(int index, int length) {
/* 752 */     checkIndex(index, length);
/* 753 */     if (!this.allowSectionedInternalNioBufferAccess) {
/* 754 */       throw new UnsupportedOperationException("Bug: unsafe access to shared internal chunk buffer");
/*     */     }
/* 756 */     return (ByteBuffer)internalNioBuffer().clear().position(index).limit(index + length);
/*     */   }
/*     */   
/*     */   private ByteBuffer internalNioBuffer() {
/* 760 */     ByteBuffer tmpNioBuf = this.tmpNioBuf;
/* 761 */     if (tmpNioBuf == null) {
/* 762 */       this.tmpNioBuf = tmpNioBuf = this.buffer.duplicate();
/*     */     }
/* 764 */     return tmpNioBuf;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer nioBuffer(int index, int length) {
/* 769 */     checkIndex(index, length);
/* 770 */     return PlatformDependent.offsetSlice(this.buffer, index, length);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void deallocate() {
/* 775 */     ByteBuffer buffer = this.buffer;
/* 776 */     if (buffer == null) {
/*     */       return;
/*     */     }
/*     */     
/* 780 */     this.buffer = null;
/*     */     
/* 782 */     if (!this.doNotFree) {
/* 783 */       if (this.cleanable != null) {
/* 784 */         this.cleanable.clean();
/* 785 */         this.cleanable = null;
/*     */       } else {
/* 787 */         freeDirect(buffer);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf unwrap() {
/* 794 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\buffer\UnpooledDirectByteBuf.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */