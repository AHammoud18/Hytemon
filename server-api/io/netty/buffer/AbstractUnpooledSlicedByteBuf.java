/*     */ package io.netty.buffer;
/*     */ 
/*     */ import io.netty.util.ByteProcessor;
/*     */ import io.netty.util.internal.MathUtil;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.channels.FileChannel;
/*     */ import java.nio.channels.GatheringByteChannel;
/*     */ import java.nio.channels.ScatteringByteChannel;
/*     */ import java.nio.charset.Charset;
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
/*     */ abstract class AbstractUnpooledSlicedByteBuf
/*     */   extends AbstractDerivedByteBuf
/*     */ {
/*     */   private final ByteBuf buffer;
/*     */   private final int adjustment;
/*     */   
/*     */   AbstractUnpooledSlicedByteBuf(ByteBuf buffer, int index, int length) {
/*  37 */     super(length);
/*  38 */     checkSliceOutOfBounds(index, length, buffer);
/*     */     
/*  40 */     if (buffer instanceof AbstractUnpooledSlicedByteBuf) {
/*  41 */       this.buffer = ((AbstractUnpooledSlicedByteBuf)buffer).buffer;
/*  42 */       ((AbstractUnpooledSlicedByteBuf)buffer).adjustment += index;
/*  43 */     } else if (buffer instanceof DuplicatedByteBuf) {
/*  44 */       this.buffer = buffer.unwrap();
/*  45 */       this.adjustment = index;
/*     */     } else {
/*  47 */       this.buffer = buffer;
/*  48 */       this.adjustment = index;
/*     */     } 
/*     */     
/*  51 */     initLength(length);
/*  52 */     writerIndex(length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void initLength(int length) {}
/*     */ 
/*     */ 
/*     */   
/*     */   int length() {
/*  63 */     return capacity();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf unwrap() {
/*  68 */     return this.buffer;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBufAllocator alloc() {
/*  73 */     return unwrap().alloc();
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public ByteOrder order() {
/*  79 */     return unwrap().order();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDirect() {
/*  84 */     return unwrap().isDirect();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf capacity(int newCapacity) {
/*  89 */     throw new UnsupportedOperationException("sliced buffer");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasArray() {
/*  94 */     return unwrap().hasArray();
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] array() {
/*  99 */     return unwrap().array();
/*     */   }
/*     */ 
/*     */   
/*     */   public int arrayOffset() {
/* 104 */     return idx(unwrap().arrayOffset());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasMemoryAddress() {
/* 109 */     return unwrap().hasMemoryAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   public long memoryAddress() {
/* 114 */     return unwrap().memoryAddress() + this.adjustment;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getByte(int index) {
/* 119 */     checkIndex(index, 1);
/* 120 */     return _getByte(index);
/*     */   }
/*     */ 
/*     */   
/*     */   protected byte _getByte(int index) {
/* 125 */     return unwrap().getByte(idx(index));
/*     */   }
/*     */ 
/*     */   
/*     */   public short getShort(int index) {
/* 130 */     checkIndex(index, 2);
/* 131 */     return _getShort(index);
/*     */   }
/*     */ 
/*     */   
/*     */   protected short _getShort(int index) {
/* 136 */     return unwrap().getShort(idx(index));
/*     */   }
/*     */ 
/*     */   
/*     */   public short getShortLE(int index) {
/* 141 */     checkIndex(index, 2);
/* 142 */     return _getShortLE(index);
/*     */   }
/*     */ 
/*     */   
/*     */   protected short _getShortLE(int index) {
/* 147 */     return unwrap().getShortLE(idx(index));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getUnsignedMedium(int index) {
/* 152 */     checkIndex(index, 3);
/* 153 */     return _getUnsignedMedium(index);
/*     */   }
/*     */ 
/*     */   
/*     */   protected int _getUnsignedMedium(int index) {
/* 158 */     return unwrap().getUnsignedMedium(idx(index));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getUnsignedMediumLE(int index) {
/* 163 */     checkIndex(index, 3);
/* 164 */     return _getUnsignedMediumLE(index);
/*     */   }
/*     */ 
/*     */   
/*     */   protected int _getUnsignedMediumLE(int index) {
/* 169 */     return unwrap().getUnsignedMediumLE(idx(index));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getInt(int index) {
/* 174 */     checkIndex(index, 4);
/* 175 */     return _getInt(index);
/*     */   }
/*     */ 
/*     */   
/*     */   protected int _getInt(int index) {
/* 180 */     return unwrap().getInt(idx(index));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getIntLE(int index) {
/* 185 */     checkIndex(index, 4);
/* 186 */     return _getIntLE(index);
/*     */   }
/*     */ 
/*     */   
/*     */   protected int _getIntLE(int index) {
/* 191 */     return unwrap().getIntLE(idx(index));
/*     */   }
/*     */ 
/*     */   
/*     */   public long getLong(int index) {
/* 196 */     checkIndex(index, 8);
/* 197 */     return _getLong(index);
/*     */   }
/*     */ 
/*     */   
/*     */   protected long _getLong(int index) {
/* 202 */     return unwrap().getLong(idx(index));
/*     */   }
/*     */ 
/*     */   
/*     */   public long getLongLE(int index) {
/* 207 */     checkIndex(index, 8);
/* 208 */     return _getLongLE(index);
/*     */   }
/*     */ 
/*     */   
/*     */   protected long _getLongLE(int index) {
/* 213 */     return unwrap().getLongLE(idx(index));
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf duplicate() {
/* 218 */     return slice(0, capacity()).setIndex(readerIndex(), writerIndex());
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf retainedDuplicate() {
/* 223 */     return retainedSlice(0, capacity()).setIndex(readerIndex(), writerIndex());
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf copy(int index, int length) {
/* 228 */     checkIndex0(index, length);
/* 229 */     return unwrap().copy(idx(index), length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf slice(int index, int length) {
/* 234 */     checkIndex0(index, length);
/* 235 */     return unwrap().slice(idx(index), length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, ByteBuf dst, int dstIndex, int length) {
/* 240 */     checkIndex0(index, length);
/* 241 */     unwrap().getBytes(idx(index), dst, dstIndex, length);
/* 242 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, byte[] dst, int dstIndex, int length) {
/* 247 */     checkIndex0(index, length);
/* 248 */     unwrap().getBytes(idx(index), dst, dstIndex, length);
/* 249 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, ByteBuffer dst) {
/* 254 */     checkIndex0(index, dst.remaining());
/* 255 */     unwrap().getBytes(idx(index), dst);
/* 256 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setByte(int index, int value) {
/* 261 */     checkIndex(index, 1);
/* 262 */     _setByte(index, value);
/* 263 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public CharSequence getCharSequence(int index, int length, Charset charset) {
/* 268 */     checkIndex0(index, length);
/* 269 */     return unwrap().getCharSequence(idx(index), length, charset);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setByte(int index, int value) {
/* 274 */     unwrap().setByte(idx(index), value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setShort(int index, int value) {
/* 279 */     checkIndex(index, 2);
/* 280 */     _setShort(index, value);
/* 281 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setShort(int index, int value) {
/* 286 */     unwrap().setShort(idx(index), value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setShortLE(int index, int value) {
/* 291 */     checkIndex(index, 2);
/* 292 */     _setShortLE(index, value);
/* 293 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setShortLE(int index, int value) {
/* 298 */     unwrap().setShortLE(idx(index), value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setMedium(int index, int value) {
/* 303 */     checkIndex(index, 3);
/* 304 */     _setMedium(index, value);
/* 305 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setMedium(int index, int value) {
/* 310 */     unwrap().setMedium(idx(index), value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setMediumLE(int index, int value) {
/* 315 */     checkIndex(index, 3);
/* 316 */     _setMediumLE(index, value);
/* 317 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setMediumLE(int index, int value) {
/* 322 */     unwrap().setMediumLE(idx(index), value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setInt(int index, int value) {
/* 327 */     checkIndex(index, 4);
/* 328 */     _setInt(index, value);
/* 329 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setInt(int index, int value) {
/* 334 */     unwrap().setInt(idx(index), value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setIntLE(int index, int value) {
/* 339 */     checkIndex(index, 4);
/* 340 */     _setIntLE(index, value);
/* 341 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setIntLE(int index, int value) {
/* 346 */     unwrap().setIntLE(idx(index), value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setLong(int index, long value) {
/* 351 */     checkIndex(index, 8);
/* 352 */     _setLong(index, value);
/* 353 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setLong(int index, long value) {
/* 358 */     unwrap().setLong(idx(index), value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setLongLE(int index, long value) {
/* 363 */     checkIndex(index, 8);
/* 364 */     _setLongLE(index, value);
/* 365 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setLongLE(int index, long value) {
/* 370 */     unwrap().setLongLE(idx(index), value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setBytes(int index, byte[] src, int srcIndex, int length) {
/* 375 */     checkIndex0(index, length);
/* 376 */     unwrap().setBytes(idx(index), src, srcIndex, length);
/* 377 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setBytes(int index, ByteBuf src, int srcIndex, int length) {
/* 382 */     checkIndex0(index, length);
/* 383 */     unwrap().setBytes(idx(index), src, srcIndex, length);
/* 384 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setBytes(int index, ByteBuffer src) {
/* 389 */     checkIndex0(index, src.remaining());
/* 390 */     unwrap().setBytes(idx(index), src);
/* 391 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, OutputStream out, int length) throws IOException {
/* 396 */     checkIndex0(index, length);
/* 397 */     unwrap().getBytes(idx(index), out, length);
/* 398 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBytes(int index, GatheringByteChannel out, int length) throws IOException {
/* 403 */     checkIndex0(index, length);
/* 404 */     return unwrap().getBytes(idx(index), out, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBytes(int index, FileChannel out, long position, int length) throws IOException {
/* 409 */     checkIndex0(index, length);
/* 410 */     return unwrap().getBytes(idx(index), out, position, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public int setBytes(int index, InputStream in, int length) throws IOException {
/* 415 */     checkIndex0(index, length);
/* 416 */     return unwrap().setBytes(idx(index), in, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public int setBytes(int index, ScatteringByteChannel in, int length) throws IOException {
/* 421 */     checkIndex0(index, length);
/* 422 */     return unwrap().setBytes(idx(index), in, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public int setBytes(int index, FileChannel in, long position, int length) throws IOException {
/* 427 */     checkIndex0(index, length);
/* 428 */     return unwrap().setBytes(idx(index), in, position, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public int nioBufferCount() {
/* 433 */     return unwrap().nioBufferCount();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer nioBuffer(int index, int length) {
/* 438 */     checkIndex0(index, length);
/* 439 */     return unwrap().nioBuffer(idx(index), length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer[] nioBuffers(int index, int length) {
/* 444 */     checkIndex0(index, length);
/* 445 */     return unwrap().nioBuffers(idx(index), length);
/*     */   }
/*     */ 
/*     */   
/*     */   public int forEachByte(int index, int length, ByteProcessor processor) {
/* 450 */     checkIndex0(index, length);
/* 451 */     int ret = unwrap().forEachByte(idx(index), length, processor);
/* 452 */     if (ret >= this.adjustment) {
/* 453 */       return ret - this.adjustment;
/*     */     }
/* 455 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int forEachByteDesc(int index, int length, ByteProcessor processor) {
/* 461 */     checkIndex0(index, length);
/* 462 */     int ret = unwrap().forEachByteDesc(idx(index), length, processor);
/* 463 */     if (ret >= this.adjustment) {
/* 464 */       return ret - this.adjustment;
/*     */     }
/* 466 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final int idx(int index) {
/* 474 */     return index + this.adjustment;
/*     */   }
/*     */   
/*     */   static void checkSliceOutOfBounds(int index, int length, ByteBuf buffer) {
/* 478 */     if (MathUtil.isOutOfBounds(index, length, buffer.capacity()))
/* 479 */       throw new IndexOutOfBoundsException(buffer + ".slice(" + index + ", " + length + ')'); 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\buffer\AbstractUnpooledSlicedByteBuf.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */