/*     */ package io.netty.buffer;
/*     */ 
/*     */ import io.netty.util.Recycler;
/*     */ import io.netty.util.internal.ObjectPool;
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
/*     */ final class PooledDirectByteBuf
/*     */   extends PooledByteBuf<ByteBuffer>
/*     */ {
/*  29 */   private static final Recycler<PooledDirectByteBuf> RECYCLER = new Recycler<PooledDirectByteBuf>()
/*     */     {
/*     */       protected PooledDirectByteBuf newObject(Recycler.Handle<PooledDirectByteBuf> handle)
/*     */       {
/*  33 */         return new PooledDirectByteBuf((ObjectPool.Handle)handle, 0);
/*     */       }
/*     */     };
/*     */   
/*     */   static PooledDirectByteBuf newInstance(int maxCapacity) {
/*  38 */     PooledDirectByteBuf buf = (PooledDirectByteBuf)RECYCLER.get();
/*  39 */     buf.reuse(maxCapacity);
/*  40 */     return buf;
/*     */   }
/*     */   
/*     */   private PooledDirectByteBuf(ObjectPool.Handle<PooledDirectByteBuf> recyclerHandle, int maxCapacity) {
/*  44 */     super((ObjectPool.Handle)recyclerHandle, maxCapacity);
/*     */   }
/*     */ 
/*     */   
/*     */   protected ByteBuffer newInternalNioBuffer(ByteBuffer memory) {
/*  49 */     return memory.duplicate();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDirect() {
/*  54 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected byte _getByte(int index) {
/*  59 */     return this.memory.get(idx(index));
/*     */   }
/*     */ 
/*     */   
/*     */   protected short _getShort(int index) {
/*  64 */     return this.memory.getShort(idx(index));
/*     */   }
/*     */ 
/*     */   
/*     */   protected short _getShortLE(int index) {
/*  69 */     return ByteBufUtil.swapShort(_getShort(index));
/*     */   }
/*     */ 
/*     */   
/*     */   protected int _getUnsignedMedium(int index) {
/*  74 */     index = idx(index);
/*  75 */     return (this.memory.get(index) & 0xFF) << 16 | (this.memory
/*  76 */       .get(index + 1) & 0xFF) << 8 | this.memory
/*  77 */       .get(index + 2) & 0xFF;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int _getUnsignedMediumLE(int index) {
/*  82 */     index = idx(index);
/*  83 */     return this.memory.get(index) & 0xFF | (this.memory
/*  84 */       .get(index + 1) & 0xFF) << 8 | (this.memory
/*  85 */       .get(index + 2) & 0xFF) << 16;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int _getInt(int index) {
/*  90 */     return this.memory.getInt(idx(index));
/*     */   }
/*     */ 
/*     */   
/*     */   protected int _getIntLE(int index) {
/*  95 */     return ByteBufUtil.swapInt(_getInt(index));
/*     */   }
/*     */ 
/*     */   
/*     */   protected long _getLong(int index) {
/* 100 */     return this.memory.getLong(idx(index));
/*     */   }
/*     */ 
/*     */   
/*     */   protected long _getLongLE(int index) {
/* 105 */     return ByteBufUtil.swapLong(_getLong(index));
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, ByteBuf dst, int dstIndex, int length) {
/* 110 */     checkDstIndex(index, length, dstIndex, dst.capacity());
/* 111 */     if (dst.hasArray()) {
/* 112 */       getBytes(index, dst.array(), dst.arrayOffset() + dstIndex, length);
/* 113 */     } else if (dst.nioBufferCount() > 0) {
/* 114 */       for (ByteBuffer bb : dst.nioBuffers(dstIndex, length)) {
/* 115 */         int bbLen = bb.remaining();
/* 116 */         getBytes(index, bb);
/* 117 */         index += bbLen;
/*     */       } 
/*     */     } else {
/* 120 */       dst.setBytes(dstIndex, this, index, length);
/*     */     } 
/* 122 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, byte[] dst, int dstIndex, int length) {
/* 127 */     checkDstIndex(index, length, dstIndex, dst.length);
/* 128 */     _internalNioBuffer(index, length, true).get(dst, dstIndex, length);
/* 129 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf readBytes(byte[] dst, int dstIndex, int length) {
/* 134 */     checkDstIndex(length, dstIndex, dst.length);
/* 135 */     _internalNioBuffer(this.readerIndex, length, false).get(dst, dstIndex, length);
/* 136 */     this.readerIndex += length;
/* 137 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, ByteBuffer dst) {
/* 142 */     dst.put(duplicateInternalNioBuffer(index, dst.remaining()));
/* 143 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf readBytes(ByteBuffer dst) {
/* 148 */     int length = dst.remaining();
/* 149 */     checkReadableBytes(length);
/* 150 */     dst.put(_internalNioBuffer(this.readerIndex, length, false));
/* 151 */     this.readerIndex += length;
/* 152 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, OutputStream out, int length) throws IOException {
/* 157 */     getBytes(index, out, length, false);
/* 158 */     return this;
/*     */   }
/*     */   
/*     */   private void getBytes(int index, OutputStream out, int length, boolean internal) throws IOException {
/* 162 */     checkIndex(index, length);
/* 163 */     if (length == 0) {
/*     */       return;
/*     */     }
/* 166 */     ByteBufUtil.readBytes(alloc(), internal ? internalNioBuffer() : this.memory.duplicate(), idx(index), length, out);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf readBytes(OutputStream out, int length) throws IOException {
/* 171 */     checkReadableBytes(length);
/* 172 */     getBytes(this.readerIndex, out, length, true);
/* 173 */     this.readerIndex += length;
/* 174 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setByte(int index, int value) {
/* 179 */     this.memory.put(idx(index), (byte)value);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setShort(int index, int value) {
/* 184 */     this.memory.putShort(idx(index), (short)value);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setShortLE(int index, int value) {
/* 189 */     _setShort(index, ByteBufUtil.swapShort((short)value));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setMedium(int index, int value) {
/* 194 */     index = idx(index);
/* 195 */     this.memory.put(index, (byte)(value >>> 16));
/* 196 */     this.memory.put(index + 1, (byte)(value >>> 8));
/* 197 */     this.memory.put(index + 2, (byte)value);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setMediumLE(int index, int value) {
/* 202 */     index = idx(index);
/* 203 */     this.memory.put(index, (byte)value);
/* 204 */     this.memory.put(index + 1, (byte)(value >>> 8));
/* 205 */     this.memory.put(index + 2, (byte)(value >>> 16));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setInt(int index, int value) {
/* 210 */     this.memory.putInt(idx(index), value);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setIntLE(int index, int value) {
/* 215 */     _setInt(index, ByteBufUtil.swapInt(value));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setLong(int index, long value) {
/* 220 */     this.memory.putLong(idx(index), value);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setLongLE(int index, long value) {
/* 225 */     _setLong(index, ByteBufUtil.swapLong(value));
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setBytes(int index, ByteBuf src, int srcIndex, int length) {
/* 230 */     checkSrcIndex(index, length, srcIndex, src.capacity());
/* 231 */     if (src.hasArray()) {
/* 232 */       setBytes(index, src.array(), src.arrayOffset() + srcIndex, length);
/* 233 */     } else if (src.nioBufferCount() > 0) {
/* 234 */       for (ByteBuffer bb : src.nioBuffers(srcIndex, length)) {
/* 235 */         int bbLen = bb.remaining();
/* 236 */         setBytes(index, bb);
/* 237 */         index += bbLen;
/*     */       } 
/*     */     } else {
/* 240 */       src.getBytes(srcIndex, this, index, length);
/*     */     } 
/* 242 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setBytes(int index, byte[] src, int srcIndex, int length) {
/* 247 */     checkSrcIndex(index, length, srcIndex, src.length);
/* 248 */     _internalNioBuffer(index, length, false).put(src, srcIndex, length);
/* 249 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setBytes(int index, ByteBuffer src) {
/* 254 */     int length = src.remaining();
/* 255 */     checkIndex(index, length);
/* 256 */     ByteBuffer tmpBuf = internalNioBuffer();
/* 257 */     if (src == tmpBuf) {
/* 258 */       src = src.duplicate();
/*     */     }
/*     */     
/* 261 */     index = idx(index);
/* 262 */     tmpBuf.limit(index + length).position(index);
/* 263 */     tmpBuf.put(src);
/* 264 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int setBytes(int index, InputStream in, int length) throws IOException {
/* 269 */     checkIndex(index, length);
/* 270 */     byte[] tmp = ByteBufUtil.threadLocalTempArray(length);
/* 271 */     int readBytes = in.read(tmp, 0, length);
/* 272 */     if (readBytes <= 0) {
/* 273 */       return readBytes;
/*     */     }
/* 275 */     ByteBuffer tmpBuf = internalNioBuffer();
/* 276 */     tmpBuf.position(idx(index));
/* 277 */     tmpBuf.put(tmp, 0, readBytes);
/* 278 */     return readBytes;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf copy(int index, int length) {
/* 283 */     checkIndex(index, length);
/* 284 */     ByteBuf copy = alloc().directBuffer(length, maxCapacity());
/* 285 */     return copy.writeBytes(this, index, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasArray() {
/* 290 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] array() {
/* 295 */     throw new UnsupportedOperationException("direct buffer");
/*     */   }
/*     */ 
/*     */   
/*     */   public int arrayOffset() {
/* 300 */     throw new UnsupportedOperationException("direct buffer");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasMemoryAddress() {
/* 305 */     PoolChunk<ByteBuffer> chunk = this.chunk;
/* 306 */     return (chunk != null && chunk.cleanable.hasMemoryAddress());
/*     */   }
/*     */ 
/*     */   
/*     */   public long memoryAddress() {
/* 311 */     ensureAccessible();
/* 312 */     if (!hasMemoryAddress()) {
/* 313 */       throw new UnsupportedOperationException();
/*     */     }
/* 315 */     return this.chunk.cleanable.memoryAddress() + this.offset;
/*     */   }
/*     */ 
/*     */   
/*     */   long _memoryAddress() {
/* 320 */     return hasMemoryAddress() ? (this.chunk.cleanable.memoryAddress() + this.offset) : 0L;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\buffer\PooledDirectByteBuf.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */