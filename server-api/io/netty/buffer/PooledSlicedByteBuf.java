/*     */ package io.netty.buffer;
/*     */ 
/*     */ import io.netty.util.ByteProcessor;
/*     */ import io.netty.util.Recycler;
/*     */ import io.netty.util.internal.ObjectPool;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.ByteBuffer;
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
/*     */ final class PooledSlicedByteBuf
/*     */   extends AbstractPooledDerivedByteBuf
/*     */ {
/*  35 */   private static final Recycler<PooledSlicedByteBuf> RECYCLER = new Recycler<PooledSlicedByteBuf>()
/*     */     {
/*     */       protected PooledSlicedByteBuf newObject(Recycler.Handle<PooledSlicedByteBuf> handle)
/*     */       {
/*  39 */         return new PooledSlicedByteBuf((ObjectPool.Handle)handle);
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   static PooledSlicedByteBuf newInstance(AbstractByteBuf unwrapped, ByteBuf wrapped, int index, int length) {
/*  45 */     AbstractUnpooledSlicedByteBuf.checkSliceOutOfBounds(index, length, unwrapped);
/*  46 */     return newInstance0(unwrapped, wrapped, index, length);
/*     */   }
/*     */   int adjustment;
/*     */   
/*     */   private static PooledSlicedByteBuf newInstance0(AbstractByteBuf unwrapped, ByteBuf wrapped, int adjustment, int length) {
/*  51 */     PooledSlicedByteBuf slice = (PooledSlicedByteBuf)RECYCLER.get();
/*  52 */     slice.init(unwrapped, wrapped, 0, length, length);
/*  53 */     slice.discardMarks();
/*  54 */     slice.adjustment = adjustment;
/*     */     
/*  56 */     return slice;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private PooledSlicedByteBuf(ObjectPool.Handle<PooledSlicedByteBuf> handle) {
/*  62 */     super((ObjectPool.Handle)handle);
/*     */   }
/*     */ 
/*     */   
/*     */   public int capacity() {
/*  67 */     return maxCapacity();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf capacity(int newCapacity) {
/*  72 */     throw new UnsupportedOperationException("sliced buffer");
/*     */   }
/*     */ 
/*     */   
/*     */   public int arrayOffset() {
/*  77 */     return idx(unwrap().arrayOffset());
/*     */   }
/*     */ 
/*     */   
/*     */   public long memoryAddress() {
/*  82 */     return unwrap().memoryAddress() + this.adjustment;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer nioBuffer(int index, int length) {
/*  87 */     checkIndex0(index, length);
/*  88 */     return unwrap().nioBuffer(idx(index), length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer[] nioBuffers(int index, int length) {
/*  93 */     checkIndex0(index, length);
/*  94 */     return unwrap().nioBuffers(idx(index), length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf copy(int index, int length) {
/*  99 */     checkIndex0(index, length);
/* 100 */     return unwrap().copy(idx(index), length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf slice(int index, int length) {
/* 105 */     checkIndex0(index, length);
/* 106 */     return super.slice(idx(index), length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf retainedSlice(int index, int length) {
/* 111 */     checkIndex0(index, length);
/* 112 */     return newInstance0(unwrap(), this, idx(index), length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf duplicate() {
/* 117 */     return slice(0, capacity()).setIndex(readerIndex(), writerIndex());
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf retainedDuplicate() {
/* 122 */     return retainedSlice(0, capacity()).setIndex(readerIndex(), writerIndex());
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getByte(int index) {
/* 127 */     checkIndex(index, 1);
/* 128 */     return _getByte(index);
/*     */   }
/*     */ 
/*     */   
/*     */   protected byte _getByte(int index) {
/* 133 */     return unwrap()._getByte(idx(index));
/*     */   }
/*     */ 
/*     */   
/*     */   public short getShort(int index) {
/* 138 */     checkIndex(index, 2);
/* 139 */     return _getShort(index);
/*     */   }
/*     */ 
/*     */   
/*     */   protected short _getShort(int index) {
/* 144 */     return unwrap()._getShort(idx(index));
/*     */   }
/*     */ 
/*     */   
/*     */   public short getShortLE(int index) {
/* 149 */     checkIndex(index, 2);
/* 150 */     return _getShortLE(index);
/*     */   }
/*     */ 
/*     */   
/*     */   protected short _getShortLE(int index) {
/* 155 */     return unwrap()._getShortLE(idx(index));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getUnsignedMedium(int index) {
/* 160 */     checkIndex(index, 3);
/* 161 */     return _getUnsignedMedium(index);
/*     */   }
/*     */ 
/*     */   
/*     */   protected int _getUnsignedMedium(int index) {
/* 166 */     return unwrap()._getUnsignedMedium(idx(index));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getUnsignedMediumLE(int index) {
/* 171 */     checkIndex(index, 3);
/* 172 */     return _getUnsignedMediumLE(index);
/*     */   }
/*     */ 
/*     */   
/*     */   protected int _getUnsignedMediumLE(int index) {
/* 177 */     return unwrap()._getUnsignedMediumLE(idx(index));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getInt(int index) {
/* 182 */     checkIndex(index, 4);
/* 183 */     return _getInt(index);
/*     */   }
/*     */ 
/*     */   
/*     */   protected int _getInt(int index) {
/* 188 */     return unwrap()._getInt(idx(index));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getIntLE(int index) {
/* 193 */     checkIndex(index, 4);
/* 194 */     return _getIntLE(index);
/*     */   }
/*     */ 
/*     */   
/*     */   protected int _getIntLE(int index) {
/* 199 */     return unwrap()._getIntLE(idx(index));
/*     */   }
/*     */ 
/*     */   
/*     */   public long getLong(int index) {
/* 204 */     checkIndex(index, 8);
/* 205 */     return _getLong(index);
/*     */   }
/*     */ 
/*     */   
/*     */   protected long _getLong(int index) {
/* 210 */     return unwrap()._getLong(idx(index));
/*     */   }
/*     */ 
/*     */   
/*     */   public long getLongLE(int index) {
/* 215 */     checkIndex(index, 8);
/* 216 */     return _getLongLE(index);
/*     */   }
/*     */ 
/*     */   
/*     */   protected long _getLongLE(int index) {
/* 221 */     return unwrap()._getLongLE(idx(index));
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, ByteBuf dst, int dstIndex, int length) {
/* 226 */     checkIndex0(index, length);
/* 227 */     unwrap().getBytes(idx(index), dst, dstIndex, length);
/* 228 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, byte[] dst, int dstIndex, int length) {
/* 233 */     checkIndex0(index, length);
/* 234 */     unwrap().getBytes(idx(index), dst, dstIndex, length);
/* 235 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, ByteBuffer dst) {
/* 240 */     checkIndex0(index, dst.remaining());
/* 241 */     unwrap().getBytes(idx(index), dst);
/* 242 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setByte(int index, int value) {
/* 247 */     checkIndex(index, 1);
/* 248 */     _setByte(index, value);
/* 249 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setByte(int index, int value) {
/* 254 */     unwrap()._setByte(idx(index), value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setShort(int index, int value) {
/* 259 */     checkIndex(index, 2);
/* 260 */     _setShort(index, value);
/* 261 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setShort(int index, int value) {
/* 266 */     unwrap()._setShort(idx(index), value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setShortLE(int index, int value) {
/* 271 */     checkIndex(index, 2);
/* 272 */     _setShortLE(index, value);
/* 273 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setShortLE(int index, int value) {
/* 278 */     unwrap()._setShortLE(idx(index), value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setMedium(int index, int value) {
/* 283 */     checkIndex(index, 3);
/* 284 */     _setMedium(index, value);
/* 285 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setMedium(int index, int value) {
/* 290 */     unwrap()._setMedium(idx(index), value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setMediumLE(int index, int value) {
/* 295 */     checkIndex(index, 3);
/* 296 */     _setMediumLE(index, value);
/* 297 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setMediumLE(int index, int value) {
/* 302 */     unwrap()._setMediumLE(idx(index), value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setInt(int index, int value) {
/* 307 */     checkIndex(index, 4);
/* 308 */     _setInt(index, value);
/* 309 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setInt(int index, int value) {
/* 314 */     unwrap()._setInt(idx(index), value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setIntLE(int index, int value) {
/* 319 */     checkIndex(index, 4);
/* 320 */     _setIntLE(index, value);
/* 321 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setIntLE(int index, int value) {
/* 326 */     unwrap()._setIntLE(idx(index), value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setLong(int index, long value) {
/* 331 */     checkIndex(index, 8);
/* 332 */     _setLong(index, value);
/* 333 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setLong(int index, long value) {
/* 338 */     unwrap()._setLong(idx(index), value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setLongLE(int index, long value) {
/* 343 */     checkIndex(index, 8);
/* 344 */     _setLongLE(index, value);
/* 345 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setLongLE(int index, long value) {
/* 350 */     unwrap()._setLongLE(idx(index), value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setBytes(int index, byte[] src, int srcIndex, int length) {
/* 355 */     checkIndex0(index, length);
/* 356 */     unwrap().setBytes(idx(index), src, srcIndex, length);
/* 357 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setBytes(int index, ByteBuf src, int srcIndex, int length) {
/* 362 */     checkIndex0(index, length);
/* 363 */     unwrap().setBytes(idx(index), src, srcIndex, length);
/* 364 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setBytes(int index, ByteBuffer src) {
/* 369 */     checkIndex0(index, src.remaining());
/* 370 */     unwrap().setBytes(idx(index), src);
/* 371 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, OutputStream out, int length) throws IOException {
/* 377 */     checkIndex0(index, length);
/* 378 */     unwrap().getBytes(idx(index), out, length);
/* 379 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBytes(int index, GatheringByteChannel out, int length) throws IOException {
/* 385 */     checkIndex0(index, length);
/* 386 */     return unwrap().getBytes(idx(index), out, length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBytes(int index, FileChannel out, long position, int length) throws IOException {
/* 392 */     checkIndex0(index, length);
/* 393 */     return unwrap().getBytes(idx(index), out, position, length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int setBytes(int index, InputStream in, int length) throws IOException {
/* 399 */     checkIndex0(index, length);
/* 400 */     return unwrap().setBytes(idx(index), in, length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int setBytes(int index, ScatteringByteChannel in, int length) throws IOException {
/* 406 */     checkIndex0(index, length);
/* 407 */     return unwrap().setBytes(idx(index), in, length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int setBytes(int index, FileChannel in, long position, int length) throws IOException {
/* 413 */     checkIndex0(index, length);
/* 414 */     return unwrap().setBytes(idx(index), in, position, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public int forEachByte(int index, int length, ByteProcessor processor) {
/* 419 */     checkIndex0(index, length);
/* 420 */     int ret = unwrap().forEachByte(idx(index), length, processor);
/* 421 */     if (ret < this.adjustment) {
/* 422 */       return -1;
/*     */     }
/* 424 */     return ret - this.adjustment;
/*     */   }
/*     */ 
/*     */   
/*     */   public int forEachByteDesc(int index, int length, ByteProcessor processor) {
/* 429 */     checkIndex0(index, length);
/* 430 */     int ret = unwrap().forEachByteDesc(idx(index), length, processor);
/* 431 */     if (ret < this.adjustment) {
/* 432 */       return -1;
/*     */     }
/* 434 */     return ret - this.adjustment;
/*     */   }
/*     */   
/*     */   private int idx(int index) {
/* 438 */     return index + this.adjustment;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\buffer\PooledSlicedByteBuf.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */