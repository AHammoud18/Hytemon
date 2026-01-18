/*     */ package io.netty.buffer;
/*     */ 
/*     */ import io.netty.util.Recycler;
/*     */ import io.netty.util.internal.ObjectPool;
/*     */ import io.netty.util.internal.PlatformDependent;
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
/*     */ final class PooledUnsafeHeapByteBuf
/*     */   extends PooledHeapByteBuf
/*     */ {
/*  24 */   private static final Recycler<PooledUnsafeHeapByteBuf> RECYCLER = new Recycler<PooledUnsafeHeapByteBuf>()
/*     */     {
/*     */       protected PooledUnsafeHeapByteBuf newObject(Recycler.Handle<PooledUnsafeHeapByteBuf> handle)
/*     */       {
/*  28 */         return new PooledUnsafeHeapByteBuf((ObjectPool.Handle)handle, 0);
/*     */       }
/*     */     };
/*     */   
/*     */   static PooledUnsafeHeapByteBuf newUnsafeInstance(int maxCapacity) {
/*  33 */     PooledUnsafeHeapByteBuf buf = (PooledUnsafeHeapByteBuf)RECYCLER.get();
/*  34 */     buf.reuse(maxCapacity);
/*  35 */     return buf;
/*     */   }
/*     */   
/*     */   private PooledUnsafeHeapByteBuf(ObjectPool.Handle<PooledUnsafeHeapByteBuf> recyclerHandle, int maxCapacity) {
/*  39 */     super((ObjectPool.Handle)recyclerHandle, maxCapacity);
/*     */   }
/*     */ 
/*     */   
/*     */   protected byte _getByte(int index) {
/*  44 */     return UnsafeByteBufUtil.getByte(this.memory, idx(index));
/*     */   }
/*     */ 
/*     */   
/*     */   protected short _getShort(int index) {
/*  49 */     return UnsafeByteBufUtil.getShort(this.memory, idx(index));
/*     */   }
/*     */ 
/*     */   
/*     */   protected short _getShortLE(int index) {
/*  54 */     return UnsafeByteBufUtil.getShortLE(this.memory, idx(index));
/*     */   }
/*     */ 
/*     */   
/*     */   protected int _getUnsignedMedium(int index) {
/*  59 */     return UnsafeByteBufUtil.getUnsignedMedium(this.memory, idx(index));
/*     */   }
/*     */ 
/*     */   
/*     */   protected int _getUnsignedMediumLE(int index) {
/*  64 */     return UnsafeByteBufUtil.getUnsignedMediumLE(this.memory, idx(index));
/*     */   }
/*     */ 
/*     */   
/*     */   protected int _getInt(int index) {
/*  69 */     return UnsafeByteBufUtil.getInt(this.memory, idx(index));
/*     */   }
/*     */ 
/*     */   
/*     */   protected int _getIntLE(int index) {
/*  74 */     return UnsafeByteBufUtil.getIntLE(this.memory, idx(index));
/*     */   }
/*     */ 
/*     */   
/*     */   protected long _getLong(int index) {
/*  79 */     return UnsafeByteBufUtil.getLong(this.memory, idx(index));
/*     */   }
/*     */ 
/*     */   
/*     */   protected long _getLongLE(int index) {
/*  84 */     return UnsafeByteBufUtil.getLongLE(this.memory, idx(index));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setByte(int index, int value) {
/*  89 */     UnsafeByteBufUtil.setByte(this.memory, idx(index), value);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setShort(int index, int value) {
/*  94 */     UnsafeByteBufUtil.setShort(this.memory, idx(index), value);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setShortLE(int index, int value) {
/*  99 */     UnsafeByteBufUtil.setShortLE(this.memory, idx(index), value);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setMedium(int index, int value) {
/* 104 */     UnsafeByteBufUtil.setMedium(this.memory, idx(index), value);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setMediumLE(int index, int value) {
/* 109 */     UnsafeByteBufUtil.setMediumLE(this.memory, idx(index), value);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setInt(int index, int value) {
/* 114 */     UnsafeByteBufUtil.setInt(this.memory, idx(index), value);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setIntLE(int index, int value) {
/* 119 */     UnsafeByteBufUtil.setIntLE(this.memory, idx(index), value);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setLong(int index, long value) {
/* 124 */     UnsafeByteBufUtil.setLong(this.memory, idx(index), value);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setLongLE(int index, long value) {
/* 129 */     UnsafeByteBufUtil.setLongLE(this.memory, idx(index), value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setZero(int index, int length) {
/* 134 */     checkIndex(index, length);
/* 135 */     UnsafeByteBufUtil.setZero(this.memory, idx(index), length);
/* 136 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeZero(int length) {
/* 141 */     ensureWritable(length);
/* 142 */     int wIndex = this.writerIndex;
/* 143 */     UnsafeByteBufUtil.setZero(this.memory, idx(wIndex), length);
/* 144 */     this.writerIndex = wIndex + length;
/* 145 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected SwappedByteBuf newSwappedByteBuf() {
/* 151 */     if (PlatformDependent.isUnaligned())
/*     */     {
/* 153 */       return new UnsafeHeapSwappedByteBuf(this);
/*     */     }
/* 155 */     return super.newSwappedByteBuf();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\buffer\PooledUnsafeHeapByteBuf.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */