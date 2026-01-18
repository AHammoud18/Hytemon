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
/*     */ final class PooledDuplicatedByteBuf
/*     */   extends AbstractPooledDerivedByteBuf
/*     */ {
/*  33 */   private static final Recycler<PooledDuplicatedByteBuf> RECYCLER = new Recycler<PooledDuplicatedByteBuf>()
/*     */     {
/*     */       protected PooledDuplicatedByteBuf newObject(Recycler.Handle<PooledDuplicatedByteBuf> handle)
/*     */       {
/*  37 */         return new PooledDuplicatedByteBuf((ObjectPool.Handle)handle);
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   static PooledDuplicatedByteBuf newInstance(AbstractByteBuf unwrapped, ByteBuf wrapped, int readerIndex, int writerIndex) {
/*  43 */     PooledDuplicatedByteBuf duplicate = (PooledDuplicatedByteBuf)RECYCLER.get();
/*  44 */     duplicate.init(unwrapped, wrapped, readerIndex, writerIndex, unwrapped.maxCapacity());
/*  45 */     duplicate.markReaderIndex();
/*  46 */     duplicate.markWriterIndex();
/*     */     
/*  48 */     return duplicate;
/*     */   }
/*     */   
/*     */   private PooledDuplicatedByteBuf(ObjectPool.Handle<PooledDuplicatedByteBuf> handle) {
/*  52 */     super((ObjectPool.Handle)handle);
/*     */   }
/*     */ 
/*     */   
/*     */   public int capacity() {
/*  57 */     return unwrap().capacity();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf capacity(int newCapacity) {
/*  62 */     unwrap().capacity(newCapacity);
/*  63 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int arrayOffset() {
/*  68 */     return unwrap().arrayOffset();
/*     */   }
/*     */ 
/*     */   
/*     */   public long memoryAddress() {
/*  73 */     return unwrap().memoryAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer nioBuffer(int index, int length) {
/*  78 */     return unwrap().nioBuffer(index, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer[] nioBuffers(int index, int length) {
/*  83 */     return unwrap().nioBuffers(index, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf copy(int index, int length) {
/*  88 */     return unwrap().copy(index, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf retainedSlice(int index, int length) {
/*  93 */     return PooledSlicedByteBuf.newInstance(unwrap(), this, index, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf duplicate() {
/*  98 */     return duplicate0().setIndex(readerIndex(), writerIndex());
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf retainedDuplicate() {
/* 103 */     return newInstance(unwrap(), this, readerIndex(), writerIndex());
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getByte(int index) {
/* 108 */     return unwrap().getByte(index);
/*     */   }
/*     */ 
/*     */   
/*     */   protected byte _getByte(int index) {
/* 113 */     return unwrap()._getByte(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public short getShort(int index) {
/* 118 */     return unwrap().getShort(index);
/*     */   }
/*     */ 
/*     */   
/*     */   protected short _getShort(int index) {
/* 123 */     return unwrap()._getShort(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public short getShortLE(int index) {
/* 128 */     return unwrap().getShortLE(index);
/*     */   }
/*     */ 
/*     */   
/*     */   protected short _getShortLE(int index) {
/* 133 */     return unwrap()._getShortLE(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getUnsignedMedium(int index) {
/* 138 */     return unwrap().getUnsignedMedium(index);
/*     */   }
/*     */ 
/*     */   
/*     */   protected int _getUnsignedMedium(int index) {
/* 143 */     return unwrap()._getUnsignedMedium(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getUnsignedMediumLE(int index) {
/* 148 */     return unwrap().getUnsignedMediumLE(index);
/*     */   }
/*     */ 
/*     */   
/*     */   protected int _getUnsignedMediumLE(int index) {
/* 153 */     return unwrap()._getUnsignedMediumLE(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getInt(int index) {
/* 158 */     return unwrap().getInt(index);
/*     */   }
/*     */ 
/*     */   
/*     */   protected int _getInt(int index) {
/* 163 */     return unwrap()._getInt(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getIntLE(int index) {
/* 168 */     return unwrap().getIntLE(index);
/*     */   }
/*     */ 
/*     */   
/*     */   protected int _getIntLE(int index) {
/* 173 */     return unwrap()._getIntLE(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public long getLong(int index) {
/* 178 */     return unwrap().getLong(index);
/*     */   }
/*     */ 
/*     */   
/*     */   protected long _getLong(int index) {
/* 183 */     return unwrap()._getLong(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public long getLongLE(int index) {
/* 188 */     return unwrap().getLongLE(index);
/*     */   }
/*     */ 
/*     */   
/*     */   protected long _getLongLE(int index) {
/* 193 */     return unwrap()._getLongLE(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, ByteBuf dst, int dstIndex, int length) {
/* 198 */     unwrap().getBytes(index, dst, dstIndex, length);
/* 199 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, byte[] dst, int dstIndex, int length) {
/* 204 */     unwrap().getBytes(index, dst, dstIndex, length);
/* 205 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, ByteBuffer dst) {
/* 210 */     unwrap().getBytes(index, dst);
/* 211 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setByte(int index, int value) {
/* 216 */     unwrap().setByte(index, value);
/* 217 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setByte(int index, int value) {
/* 222 */     unwrap()._setByte(index, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setShort(int index, int value) {
/* 227 */     unwrap().setShort(index, value);
/* 228 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setShort(int index, int value) {
/* 233 */     unwrap()._setShort(index, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setShortLE(int index, int value) {
/* 238 */     unwrap().setShortLE(index, value);
/* 239 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setShortLE(int index, int value) {
/* 244 */     unwrap()._setShortLE(index, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setMedium(int index, int value) {
/* 249 */     unwrap().setMedium(index, value);
/* 250 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setMedium(int index, int value) {
/* 255 */     unwrap()._setMedium(index, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setMediumLE(int index, int value) {
/* 260 */     unwrap().setMediumLE(index, value);
/* 261 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setMediumLE(int index, int value) {
/* 266 */     unwrap()._setMediumLE(index, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setInt(int index, int value) {
/* 271 */     unwrap().setInt(index, value);
/* 272 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setInt(int index, int value) {
/* 277 */     unwrap()._setInt(index, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setIntLE(int index, int value) {
/* 282 */     unwrap().setIntLE(index, value);
/* 283 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setIntLE(int index, int value) {
/* 288 */     unwrap()._setIntLE(index, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setLong(int index, long value) {
/* 293 */     unwrap().setLong(index, value);
/* 294 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setLong(int index, long value) {
/* 299 */     unwrap()._setLong(index, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setLongLE(int index, long value) {
/* 304 */     unwrap().setLongLE(index, value);
/* 305 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setLongLE(int index, long value) {
/* 310 */     unwrap().setLongLE(index, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setBytes(int index, byte[] src, int srcIndex, int length) {
/* 315 */     unwrap().setBytes(index, src, srcIndex, length);
/* 316 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setBytes(int index, ByteBuf src, int srcIndex, int length) {
/* 321 */     unwrap().setBytes(index, src, srcIndex, length);
/* 322 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setBytes(int index, ByteBuffer src) {
/* 327 */     unwrap().setBytes(index, src);
/* 328 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, OutputStream out, int length) throws IOException {
/* 334 */     unwrap().getBytes(index, out, length);
/* 335 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBytes(int index, GatheringByteChannel out, int length) throws IOException {
/* 341 */     return unwrap().getBytes(index, out, length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBytes(int index, FileChannel out, long position, int length) throws IOException {
/* 347 */     return unwrap().getBytes(index, out, position, length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int setBytes(int index, InputStream in, int length) throws IOException {
/* 353 */     return unwrap().setBytes(index, in, length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int setBytes(int index, ScatteringByteChannel in, int length) throws IOException {
/* 359 */     return unwrap().setBytes(index, in, length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int setBytes(int index, FileChannel in, long position, int length) throws IOException {
/* 365 */     return unwrap().setBytes(index, in, position, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public int forEachByte(int index, int length, ByteProcessor processor) {
/* 370 */     return unwrap().forEachByte(index, length, processor);
/*     */   }
/*     */ 
/*     */   
/*     */   public int forEachByteDesc(int index, int length, ByteProcessor processor) {
/* 375 */     return unwrap().forEachByteDesc(index, length, processor);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\buffer\PooledDuplicatedByteBuf.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */