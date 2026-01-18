/*     */ package io.netty.buffer;
/*     */ 
/*     */ import io.netty.util.Recycler;
/*     */ import io.netty.util.internal.ObjectPool;
/*     */ import java.io.IOException;
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
/*     */ abstract class PooledByteBuf<T>
/*     */   extends AbstractReferenceCountedByteBuf
/*     */ {
/*     */   private final Recycler.EnhancedHandle<PooledByteBuf<T>> recyclerHandle;
/*     */   protected PoolChunk<T> chunk;
/*     */   protected long handle;
/*     */   protected T memory;
/*     */   protected int offset;
/*     */   protected int length;
/*     */   int maxLength;
/*     */   PoolThreadCache cache;
/*     */   ByteBuffer tmpNioBuf;
/*     */   private ByteBufAllocator allocator;
/*     */   
/*     */   protected PooledByteBuf(ObjectPool.Handle<? extends PooledByteBuf<T>> recyclerHandle, int maxCapacity) {
/*  46 */     super(maxCapacity);
/*  47 */     this.recyclerHandle = (Recycler.EnhancedHandle)recyclerHandle;
/*     */   }
/*     */ 
/*     */   
/*     */   void init(PoolChunk<T> chunk, ByteBuffer nioBuffer, long handle, int offset, int length, int maxLength, PoolThreadCache cache, boolean threadLocal) {
/*  52 */     init0(chunk, nioBuffer, handle, offset, length, maxLength, cache, true, threadLocal);
/*     */   }
/*     */   
/*     */   void initUnpooled(PoolChunk<T> chunk, int length) {
/*  56 */     init0(chunk, (ByteBuffer)null, 0L, 0, length, length, (PoolThreadCache)null, false, false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void init0(PoolChunk<T> chunk, ByteBuffer nioBuffer, long handle, int offset, int length, int maxLength, PoolThreadCache cache, boolean pooled, boolean threadLocal) {
/*  62 */     assert handle >= 0L;
/*  63 */     assert chunk != null;
/*  64 */     assert !PoolChunk.isSubpage(handle) || chunk.arena.sizeClass
/*  65 */       .size2SizeIdx(maxLength) <= chunk.arena.sizeClass.smallMaxSizeIdx : "Allocated small sub-page handle for a buffer size that isn't \"small.\"";
/*     */ 
/*     */     
/*  68 */     chunk.incrementPinnedMemory(maxLength);
/*  69 */     this.chunk = chunk;
/*  70 */     this.memory = chunk.memory;
/*  71 */     this.tmpNioBuf = nioBuffer;
/*  72 */     this.allocator = chunk.arena.parent;
/*  73 */     this.cache = cache;
/*  74 */     this.handle = handle;
/*  75 */     this.offset = offset;
/*  76 */     this.length = length;
/*  77 */     this.maxLength = maxLength;
/*  78 */     PooledByteBufAllocator.onAllocateBuffer(this, pooled, threadLocal);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final void reuse(int maxCapacity) {
/*  85 */     maxCapacity(maxCapacity);
/*  86 */     resetRefCnt();
/*  87 */     setIndex0(0, 0);
/*  88 */     discardMarks();
/*     */   }
/*     */ 
/*     */   
/*     */   public final int capacity() {
/*  93 */     return this.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public int maxFastWritableBytes() {
/*  98 */     return Math.min(this.maxLength, maxCapacity()) - this.writerIndex;
/*     */   }
/*     */ 
/*     */   
/*     */   public final ByteBuf capacity(int newCapacity) {
/* 103 */     if (newCapacity == this.length) {
/* 104 */       ensureAccessible();
/* 105 */       return this;
/*     */     } 
/* 107 */     checkNewCapacity(newCapacity);
/* 108 */     if (!this.chunk.unpooled)
/*     */     {
/* 110 */       if (newCapacity > this.length) {
/* 111 */         if (newCapacity <= this.maxLength) {
/* 112 */           this.length = newCapacity;
/* 113 */           return this;
/*     */         } 
/* 115 */       } else if (newCapacity > this.maxLength >>> 1 && (this.maxLength > 512 || newCapacity > this.maxLength - 16)) {
/*     */ 
/*     */         
/* 118 */         this.length = newCapacity;
/* 119 */         trimIndicesToCapacity(newCapacity);
/* 120 */         return this;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 125 */     PooledByteBufAllocator.onReallocateBuffer(this, newCapacity);
/* 126 */     this.chunk.arena.reallocate(this, newCapacity);
/* 127 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public final ByteBufAllocator alloc() {
/* 132 */     return this.allocator;
/*     */   }
/*     */ 
/*     */   
/*     */   public final ByteOrder order() {
/* 137 */     return ByteOrder.BIG_ENDIAN;
/*     */   }
/*     */ 
/*     */   
/*     */   public final ByteBuf unwrap() {
/* 142 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public final ByteBuf retainedDuplicate() {
/* 147 */     return PooledDuplicatedByteBuf.newInstance(this, this, readerIndex(), writerIndex());
/*     */   }
/*     */ 
/*     */   
/*     */   public final ByteBuf retainedSlice() {
/* 152 */     int index = readerIndex();
/* 153 */     return retainedSlice(index, writerIndex() - index);
/*     */   }
/*     */ 
/*     */   
/*     */   public final ByteBuf retainedSlice(int index, int length) {
/* 158 */     return PooledSlicedByteBuf.newInstance(this, this, index, length);
/*     */   }
/*     */   
/*     */   protected final ByteBuffer internalNioBuffer() {
/* 162 */     ByteBuffer tmpNioBuf = this.tmpNioBuf;
/* 163 */     if (tmpNioBuf == null) {
/* 164 */       this.tmpNioBuf = tmpNioBuf = newInternalNioBuffer(this.memory);
/*     */     } else {
/* 166 */       tmpNioBuf.clear();
/*     */     } 
/* 168 */     return tmpNioBuf;
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract ByteBuffer newInternalNioBuffer(T paramT);
/*     */   
/*     */   protected final void deallocate() {
/* 175 */     if (this.handle >= 0L) {
/* 176 */       PooledByteBufAllocator.onDeallocateBuffer(this);
/* 177 */       long handle = this.handle;
/* 178 */       this.handle = -1L;
/* 179 */       this.memory = null;
/* 180 */       this.chunk.arena.free(this.chunk, this.tmpNioBuf, handle, this.maxLength, this.cache);
/* 181 */       this.tmpNioBuf = null;
/* 182 */       this.chunk = null;
/* 183 */       this.cache = null;
/* 184 */       this.recyclerHandle.unguardedRecycle(this);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected final int idx(int index) {
/* 189 */     return this.offset + index;
/*     */   }
/*     */   
/*     */   final ByteBuffer _internalNioBuffer(int index, int length, boolean duplicate) {
/* 193 */     index = idx(index);
/* 194 */     ByteBuffer buffer = duplicate ? newInternalNioBuffer(this.memory) : internalNioBuffer();
/* 195 */     buffer.limit(index + length).position(index);
/* 196 */     return buffer;
/*     */   }
/*     */   
/*     */   ByteBuffer duplicateInternalNioBuffer(int index, int length) {
/* 200 */     checkIndex(index, length);
/* 201 */     return _internalNioBuffer(index, length, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public final ByteBuffer internalNioBuffer(int index, int length) {
/* 206 */     checkIndex(index, length);
/* 207 */     return _internalNioBuffer(index, length, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public final int nioBufferCount() {
/* 212 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public final ByteBuffer nioBuffer(int index, int length) {
/* 217 */     return duplicateInternalNioBuffer(index, length).slice();
/*     */   }
/*     */ 
/*     */   
/*     */   public final ByteBuffer[] nioBuffers(int index, int length) {
/* 222 */     return new ByteBuffer[] { nioBuffer(index, length) };
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean isContiguous() {
/* 227 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public final int getBytes(int index, GatheringByteChannel out, int length) throws IOException {
/* 232 */     return out.write(duplicateInternalNioBuffer(index, length));
/*     */   }
/*     */ 
/*     */   
/*     */   public final int readBytes(GatheringByteChannel out, int length) throws IOException {
/* 237 */     checkReadableBytes(length);
/* 238 */     int readBytes = out.write(_internalNioBuffer(this.readerIndex, length, false));
/* 239 */     this.readerIndex += readBytes;
/* 240 */     return readBytes;
/*     */   }
/*     */ 
/*     */   
/*     */   public final int getBytes(int index, FileChannel out, long position, int length) throws IOException {
/* 245 */     return out.write(duplicateInternalNioBuffer(index, length), position);
/*     */   }
/*     */ 
/*     */   
/*     */   public final int readBytes(FileChannel out, long position, int length) throws IOException {
/* 250 */     checkReadableBytes(length);
/* 251 */     int readBytes = out.write(_internalNioBuffer(this.readerIndex, length, false), position);
/* 252 */     this.readerIndex += readBytes;
/* 253 */     return readBytes;
/*     */   }
/*     */ 
/*     */   
/*     */   public final int setBytes(int index, ScatteringByteChannel in, int length) throws IOException {
/*     */     try {
/* 259 */       return in.read(internalNioBuffer(index, length));
/* 260 */     } catch (ClosedChannelException ignored) {
/* 261 */       return -1;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public final int setBytes(int index, FileChannel in, long position, int length) throws IOException {
/*     */     try {
/* 268 */       return in.read(internalNioBuffer(index, length), position);
/* 269 */     } catch (ClosedChannelException ignored) {
/* 270 */       return -1;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\buffer\PooledByteBuf.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */