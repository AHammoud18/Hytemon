/*     */ package io.netty.buffer;
/*     */ 
/*     */ import io.netty.util.Recycler;
/*     */ import io.netty.util.internal.ObjectPool;
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
/*     */ class PooledHeapByteBuf
/*     */   extends PooledByteBuf<byte[]>
/*     */ {
/*  28 */   private static final Recycler<PooledHeapByteBuf> RECYCLER = new Recycler<PooledHeapByteBuf>()
/*     */     {
/*     */       protected PooledHeapByteBuf newObject(Recycler.Handle<PooledHeapByteBuf> handle)
/*     */       {
/*  32 */         return new PooledHeapByteBuf((ObjectPool.Handle<? extends PooledHeapByteBuf>)handle, 0);
/*     */       }
/*     */     };
/*     */   
/*     */   static PooledHeapByteBuf newInstance(int maxCapacity) {
/*  37 */     PooledHeapByteBuf buf = (PooledHeapByteBuf)RECYCLER.get();
/*  38 */     buf.reuse(maxCapacity);
/*  39 */     return buf;
/*     */   }
/*     */   
/*     */   PooledHeapByteBuf(ObjectPool.Handle<? extends PooledHeapByteBuf> recyclerHandle, int maxCapacity) {
/*  43 */     super((ObjectPool.Handle)recyclerHandle, maxCapacity);
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean isDirect() {
/*  48 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected byte _getByte(int index) {
/*  53 */     return HeapByteBufUtil.getByte(this.memory, idx(index));
/*     */   }
/*     */ 
/*     */   
/*     */   protected short _getShort(int index) {
/*  58 */     return HeapByteBufUtil.getShort(this.memory, idx(index));
/*     */   }
/*     */ 
/*     */   
/*     */   protected short _getShortLE(int index) {
/*  63 */     return HeapByteBufUtil.getShortLE(this.memory, idx(index));
/*     */   }
/*     */ 
/*     */   
/*     */   protected int _getUnsignedMedium(int index) {
/*  68 */     return HeapByteBufUtil.getUnsignedMedium(this.memory, idx(index));
/*     */   }
/*     */ 
/*     */   
/*     */   protected int _getUnsignedMediumLE(int index) {
/*  73 */     return HeapByteBufUtil.getUnsignedMediumLE(this.memory, idx(index));
/*     */   }
/*     */ 
/*     */   
/*     */   protected int _getInt(int index) {
/*  78 */     return HeapByteBufUtil.getInt(this.memory, idx(index));
/*     */   }
/*     */ 
/*     */   
/*     */   protected int _getIntLE(int index) {
/*  83 */     return HeapByteBufUtil.getIntLE(this.memory, idx(index));
/*     */   }
/*     */ 
/*     */   
/*     */   protected long _getLong(int index) {
/*  88 */     return HeapByteBufUtil.getLong(this.memory, idx(index));
/*     */   }
/*     */ 
/*     */   
/*     */   protected long _getLongLE(int index) {
/*  93 */     return HeapByteBufUtil.getLongLE(this.memory, idx(index));
/*     */   }
/*     */ 
/*     */   
/*     */   public final ByteBuf getBytes(int index, ByteBuf dst, int dstIndex, int length) {
/*  98 */     checkDstIndex(index, length, dstIndex, dst.capacity());
/*  99 */     if (dst.hasMemoryAddress() && PlatformDependent.hasUnsafe()) {
/* 100 */       PlatformDependent.copyMemory(this.memory, idx(index), dst.memoryAddress() + dstIndex, length);
/* 101 */     } else if (dst.hasArray()) {
/* 102 */       getBytes(index, dst.array(), dst.arrayOffset() + dstIndex, length);
/*     */     } else {
/* 104 */       dst.setBytes(dstIndex, this.memory, idx(index), length);
/*     */     } 
/* 106 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public final ByteBuf getBytes(int index, byte[] dst, int dstIndex, int length) {
/* 111 */     checkDstIndex(index, length, dstIndex, dst.length);
/* 112 */     System.arraycopy(this.memory, idx(index), dst, dstIndex, length);
/* 113 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public final ByteBuf getBytes(int index, ByteBuffer dst) {
/* 118 */     int length = dst.remaining();
/* 119 */     checkIndex(index, length);
/* 120 */     dst.put(this.memory, idx(index), length);
/* 121 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public final ByteBuf getBytes(int index, OutputStream out, int length) throws IOException {
/* 126 */     checkIndex(index, length);
/* 127 */     out.write(this.memory, idx(index), length);
/* 128 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setByte(int index, int value) {
/* 133 */     HeapByteBufUtil.setByte(this.memory, idx(index), value);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setShort(int index, int value) {
/* 138 */     HeapByteBufUtil.setShort(this.memory, idx(index), value);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setShortLE(int index, int value) {
/* 143 */     HeapByteBufUtil.setShortLE(this.memory, idx(index), value);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setMedium(int index, int value) {
/* 148 */     HeapByteBufUtil.setMedium(this.memory, idx(index), value);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setMediumLE(int index, int value) {
/* 153 */     HeapByteBufUtil.setMediumLE(this.memory, idx(index), value);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setInt(int index, int value) {
/* 158 */     HeapByteBufUtil.setInt(this.memory, idx(index), value);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setIntLE(int index, int value) {
/* 163 */     HeapByteBufUtil.setIntLE(this.memory, idx(index), value);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setLong(int index, long value) {
/* 168 */     HeapByteBufUtil.setLong(this.memory, idx(index), value);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setLongLE(int index, long value) {
/* 173 */     HeapByteBufUtil.setLongLE(this.memory, idx(index), value);
/*     */   }
/*     */ 
/*     */   
/*     */   public final ByteBuf setBytes(int index, ByteBuf src, int srcIndex, int length) {
/* 178 */     checkSrcIndex(index, length, srcIndex, src.capacity());
/* 179 */     if (src.hasMemoryAddress() && PlatformDependent.hasUnsafe()) {
/* 180 */       PlatformDependent.copyMemory(src.memoryAddress() + srcIndex, this.memory, idx(index), length);
/* 181 */     } else if (src.hasArray()) {
/* 182 */       setBytes(index, src.array(), src.arrayOffset() + srcIndex, length);
/*     */     } else {
/* 184 */       src.getBytes(srcIndex, this.memory, idx(index), length);
/*     */     } 
/* 186 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public final ByteBuf setBytes(int index, byte[] src, int srcIndex, int length) {
/* 191 */     checkSrcIndex(index, length, srcIndex, src.length);
/* 192 */     System.arraycopy(src, srcIndex, this.memory, idx(index), length);
/* 193 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public final ByteBuf setBytes(int index, ByteBuffer src) {
/* 198 */     int length = src.remaining();
/* 199 */     checkIndex(index, length);
/* 200 */     src.get(this.memory, idx(index), length);
/* 201 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public final int setBytes(int index, InputStream in, int length) throws IOException {
/* 206 */     checkIndex(index, length);
/* 207 */     return in.read(this.memory, idx(index), length);
/*     */   }
/*     */ 
/*     */   
/*     */   public final ByteBuf copy(int index, int length) {
/* 212 */     checkIndex(index, length);
/* 213 */     ByteBuf copy = alloc().heapBuffer(length, maxCapacity());
/* 214 */     return copy.writeBytes(this.memory, idx(index), length);
/*     */   }
/*     */ 
/*     */   
/*     */   final ByteBuffer duplicateInternalNioBuffer(int index, int length) {
/* 219 */     checkIndex(index, length);
/* 220 */     return ByteBuffer.wrap(this.memory, idx(index), length).slice();
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean hasArray() {
/* 225 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public final byte[] array() {
/* 230 */     ensureAccessible();
/* 231 */     return this.memory;
/*     */   }
/*     */ 
/*     */   
/*     */   public final int arrayOffset() {
/* 236 */     return this.offset;
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean hasMemoryAddress() {
/* 241 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public final long memoryAddress() {
/* 246 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   protected final ByteBuffer newInternalNioBuffer(byte[] memory) {
/* 251 */     return ByteBuffer.wrap(memory);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\buffer\PooledHeapByteBuf.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */