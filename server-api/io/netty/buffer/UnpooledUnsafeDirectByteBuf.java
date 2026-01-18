/*     */ package io.netty.buffer;
/*     */ 
/*     */ import io.netty.util.internal.CleanableDirectBuffer;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
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
/*     */ public class UnpooledUnsafeDirectByteBuf
/*     */   extends UnpooledDirectByteBuf
/*     */ {
/*     */   long memoryAddress;
/*     */   
/*     */   public UnpooledUnsafeDirectByteBuf(ByteBufAllocator alloc, int initialCapacity, int maxCapacity) {
/*  43 */     super(alloc, initialCapacity, maxCapacity);
/*     */   }
/*     */ 
/*     */   
/*     */   UnpooledUnsafeDirectByteBuf(ByteBufAllocator alloc, int initialCapacity, int maxCapacity, boolean allowSectionedInternalNioBufferAccess) {
/*  48 */     super(alloc, initialCapacity, maxCapacity, allowSectionedInternalNioBufferAccess);
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
/*     */   protected UnpooledUnsafeDirectByteBuf(ByteBufAllocator alloc, ByteBuffer initialBuffer, int maxCapacity) {
/*  66 */     super(alloc, initialBuffer, maxCapacity, false, true);
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
/*     */   protected UnpooledUnsafeDirectByteBuf(ByteBufAllocator alloc, boolean slice, ByteBuffer initialBuffer, int maxCapacity) {
/*  85 */     super(alloc, initialBuffer, maxCapacity, false, slice);
/*     */   }
/*     */   
/*     */   UnpooledUnsafeDirectByteBuf(ByteBufAllocator alloc, ByteBuffer initialBuffer, int maxCapacity, boolean doFree) {
/*  89 */     super(alloc, initialBuffer, maxCapacity, doFree, false);
/*     */   }
/*     */ 
/*     */   
/*     */   final void setByteBuffer(ByteBuffer buffer, boolean tryFree) {
/*  94 */     super.setByteBuffer(buffer, tryFree);
/*  95 */     this.memoryAddress = PlatformDependent.directBufferAddress(buffer);
/*     */   }
/*     */ 
/*     */   
/*     */   final void setByteBuffer(CleanableDirectBuffer cleanableDirectBuffer, boolean tryFree) {
/* 100 */     super.setByteBuffer(cleanableDirectBuffer, tryFree);
/* 101 */     this.memoryAddress = PlatformDependent.directBufferAddress(cleanableDirectBuffer.buffer());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasMemoryAddress() {
/* 106 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public long memoryAddress() {
/* 111 */     ensureAccessible();
/* 112 */     return this.memoryAddress;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getByte(int index) {
/* 117 */     checkIndex(index);
/* 118 */     return _getByte(index);
/*     */   }
/*     */ 
/*     */   
/*     */   protected byte _getByte(int index) {
/* 123 */     return UnsafeByteBufUtil.getByte(addr(index));
/*     */   }
/*     */ 
/*     */   
/*     */   public short getShort(int index) {
/* 128 */     checkIndex(index, 2);
/* 129 */     return _getShort(index);
/*     */   }
/*     */ 
/*     */   
/*     */   protected short _getShort(int index) {
/* 134 */     return UnsafeByteBufUtil.getShort(addr(index));
/*     */   }
/*     */ 
/*     */   
/*     */   protected short _getShortLE(int index) {
/* 139 */     return UnsafeByteBufUtil.getShortLE(addr(index));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getUnsignedMedium(int index) {
/* 144 */     checkIndex(index, 3);
/* 145 */     return _getUnsignedMedium(index);
/*     */   }
/*     */ 
/*     */   
/*     */   protected int _getUnsignedMedium(int index) {
/* 150 */     return UnsafeByteBufUtil.getUnsignedMedium(addr(index));
/*     */   }
/*     */ 
/*     */   
/*     */   protected int _getUnsignedMediumLE(int index) {
/* 155 */     return UnsafeByteBufUtil.getUnsignedMediumLE(addr(index));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getInt(int index) {
/* 160 */     checkIndex(index, 4);
/* 161 */     return _getInt(index);
/*     */   }
/*     */ 
/*     */   
/*     */   protected int _getInt(int index) {
/* 166 */     return UnsafeByteBufUtil.getInt(addr(index));
/*     */   }
/*     */ 
/*     */   
/*     */   protected int _getIntLE(int index) {
/* 171 */     return UnsafeByteBufUtil.getIntLE(addr(index));
/*     */   }
/*     */ 
/*     */   
/*     */   public long getLong(int index) {
/* 176 */     checkIndex(index, 8);
/* 177 */     return _getLong(index);
/*     */   }
/*     */ 
/*     */   
/*     */   protected long _getLong(int index) {
/* 182 */     return UnsafeByteBufUtil.getLong(addr(index));
/*     */   }
/*     */ 
/*     */   
/*     */   protected long _getLongLE(int index) {
/* 187 */     return UnsafeByteBufUtil.getLongLE(addr(index));
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, ByteBuf dst, int dstIndex, int length) {
/* 192 */     UnsafeByteBufUtil.getBytes(this, addr(index), index, dst, dstIndex, length);
/* 193 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   void getBytes(int index, byte[] dst, int dstIndex, int length, boolean internal) {
/* 198 */     UnsafeByteBufUtil.getBytes(this, addr(index), index, dst, dstIndex, length);
/*     */   }
/*     */ 
/*     */   
/*     */   void getBytes(int index, ByteBuffer dst, boolean internal) {
/* 203 */     UnsafeByteBufUtil.getBytes(this, addr(index), index, dst);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setByte(int index, int value) {
/* 208 */     checkIndex(index);
/* 209 */     _setByte(index, value);
/* 210 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setByte(int index, int value) {
/* 215 */     UnsafeByteBufUtil.setByte(addr(index), value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setShort(int index, int value) {
/* 220 */     checkIndex(index, 2);
/* 221 */     _setShort(index, value);
/* 222 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setShort(int index, int value) {
/* 227 */     UnsafeByteBufUtil.setShort(addr(index), value);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setShortLE(int index, int value) {
/* 232 */     UnsafeByteBufUtil.setShortLE(addr(index), value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setMedium(int index, int value) {
/* 237 */     checkIndex(index, 3);
/* 238 */     _setMedium(index, value);
/* 239 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setMedium(int index, int value) {
/* 244 */     UnsafeByteBufUtil.setMedium(addr(index), value);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setMediumLE(int index, int value) {
/* 249 */     UnsafeByteBufUtil.setMediumLE(addr(index), value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setInt(int index, int value) {
/* 254 */     checkIndex(index, 4);
/* 255 */     _setInt(index, value);
/* 256 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setInt(int index, int value) {
/* 261 */     UnsafeByteBufUtil.setInt(addr(index), value);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setIntLE(int index, int value) {
/* 266 */     UnsafeByteBufUtil.setIntLE(addr(index), value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setLong(int index, long value) {
/* 271 */     checkIndex(index, 8);
/* 272 */     _setLong(index, value);
/* 273 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setLong(int index, long value) {
/* 278 */     UnsafeByteBufUtil.setLong(addr(index), value);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setLongLE(int index, long value) {
/* 283 */     UnsafeByteBufUtil.setLongLE(addr(index), value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setBytes(int index, ByteBuf src, int srcIndex, int length) {
/* 288 */     UnsafeByteBufUtil.setBytes(this, addr(index), index, src, srcIndex, length);
/* 289 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setBytes(int index, byte[] src, int srcIndex, int length) {
/* 294 */     UnsafeByteBufUtil.setBytes(this, addr(index), index, src, srcIndex, length);
/* 295 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setBytes(int index, ByteBuffer src) {
/* 300 */     UnsafeByteBufUtil.setBytes(this, addr(index), index, src);
/* 301 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   void getBytes(int index, OutputStream out, int length, boolean internal) throws IOException {
/* 306 */     UnsafeByteBufUtil.getBytes(this, addr(index), index, out, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public int setBytes(int index, InputStream in, int length) throws IOException {
/* 311 */     return UnsafeByteBufUtil.setBytes(this, addr(index), index, in, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf copy(int index, int length) {
/* 316 */     return UnsafeByteBufUtil.copy(this, addr(index), index, length);
/*     */   }
/*     */   
/*     */   final long addr(int index) {
/* 320 */     return this.memoryAddress + index;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SwappedByteBuf newSwappedByteBuf() {
/* 325 */     if (PlatformDependent.isUnaligned())
/*     */     {
/* 327 */       return new UnsafeDirectSwappedByteBuf(this);
/*     */     }
/* 329 */     return super.newSwappedByteBuf();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setZero(int index, int length) {
/* 334 */     checkIndex(index, length);
/* 335 */     UnsafeByteBufUtil.setZero(addr(index), length);
/* 336 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeZero(int length) {
/* 341 */     ensureWritable(length);
/* 342 */     int wIndex = this.writerIndex;
/* 343 */     UnsafeByteBufUtil.setZero(addr(wIndex), length);
/* 344 */     this.writerIndex = wIndex + length;
/* 345 */     return this;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\buffer\UnpooledUnsafeDirectByteBuf.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */