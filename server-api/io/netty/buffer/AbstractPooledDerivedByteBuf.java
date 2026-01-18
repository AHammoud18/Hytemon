/*     */ package io.netty.buffer;
/*     */ 
/*     */ import io.netty.util.IllegalReferenceCountException;
/*     */ import io.netty.util.Recycler;
/*     */ import io.netty.util.internal.ObjectPool;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
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
/*     */ abstract class AbstractPooledDerivedByteBuf
/*     */   extends AbstractReferenceCountedByteBuf
/*     */ {
/*     */   private final Recycler.EnhancedHandle<AbstractPooledDerivedByteBuf> recyclerHandle;
/*     */   private AbstractByteBuf rootParent;
/*     */   private ByteBuf parent;
/*     */   
/*     */   AbstractPooledDerivedByteBuf(ObjectPool.Handle<? extends AbstractPooledDerivedByteBuf> recyclerHandle) {
/*  43 */     super(0);
/*  44 */     this.recyclerHandle = (Recycler.EnhancedHandle)recyclerHandle;
/*     */   }
/*     */ 
/*     */   
/*     */   final void parent(ByteBuf newParent) {
/*  49 */     assert newParent instanceof SimpleLeakAwareByteBuf;
/*  50 */     this.parent = newParent;
/*     */   }
/*     */ 
/*     */   
/*     */   public final AbstractByteBuf unwrap() {
/*  55 */     AbstractByteBuf rootParent = this.rootParent;
/*  56 */     if (rootParent == null) {
/*  57 */       throw new IllegalReferenceCountException();
/*     */     }
/*  59 */     return rootParent;
/*     */   }
/*     */ 
/*     */   
/*     */   final <U extends AbstractPooledDerivedByteBuf> U init(AbstractByteBuf unwrapped, ByteBuf wrapped, int readerIndex, int writerIndex, int maxCapacity) {
/*  64 */     wrapped.retain();
/*  65 */     this.parent = wrapped;
/*  66 */     this.rootParent = unwrapped;
/*     */     
/*     */     try {
/*  69 */       maxCapacity(maxCapacity);
/*  70 */       setIndex0(readerIndex, writerIndex);
/*  71 */       resetRefCnt();
/*     */ 
/*     */       
/*  74 */       AbstractPooledDerivedByteBuf abstractPooledDerivedByteBuf = this;
/*  75 */       wrapped = null;
/*  76 */       return (U)abstractPooledDerivedByteBuf;
/*     */     } finally {
/*  78 */       if (wrapped != null) {
/*  79 */         this.parent = this.rootParent = null;
/*  80 */         wrapped.release();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void deallocate() {
/*  90 */     ByteBuf parent = this.parent;
/*     */     
/*  92 */     this.parent = this.rootParent = null;
/*  93 */     this.recyclerHandle.unguardedRecycle(this);
/*  94 */     parent.release();
/*     */   }
/*     */ 
/*     */   
/*     */   public final ByteBufAllocator alloc() {
/*  99 */     return unwrap().alloc();
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public final ByteOrder order() {
/* 105 */     return unwrap().order();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isReadOnly() {
/* 110 */     return unwrap().isReadOnly();
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean isDirect() {
/* 115 */     return unwrap().isDirect();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasArray() {
/* 120 */     return unwrap().hasArray();
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] array() {
/* 125 */     return unwrap().array();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasMemoryAddress() {
/* 130 */     return unwrap().hasMemoryAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isContiguous() {
/* 135 */     return unwrap().isContiguous();
/*     */   }
/*     */ 
/*     */   
/*     */   public final int nioBufferCount() {
/* 140 */     return unwrap().nioBufferCount();
/*     */   }
/*     */ 
/*     */   
/*     */   public final ByteBuffer internalNioBuffer(int index, int length) {
/* 145 */     return nioBuffer(index, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public final ByteBuf retainedSlice() {
/* 150 */     int index = readerIndex();
/* 151 */     return retainedSlice(index, writerIndex() - index);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf slice(int index, int length) {
/* 156 */     ensureAccessible();
/*     */     
/* 158 */     return new PooledNonRetainedSlicedByteBuf(this, unwrap(), index, length);
/*     */   }
/*     */   
/*     */   final ByteBuf duplicate0() {
/* 162 */     ensureAccessible();
/*     */     
/* 164 */     return new PooledNonRetainedDuplicateByteBuf(this, unwrap());
/*     */   }
/*     */   
/*     */   private static final class PooledNonRetainedDuplicateByteBuf extends UnpooledDuplicatedByteBuf {
/*     */     private final ByteBuf referenceCountDelegate;
/*     */     
/*     */     PooledNonRetainedDuplicateByteBuf(ByteBuf referenceCountDelegate, AbstractByteBuf buffer) {
/* 171 */       super(buffer);
/* 172 */       this.referenceCountDelegate = referenceCountDelegate;
/*     */     }
/*     */ 
/*     */     
/*     */     boolean isAccessible0() {
/* 177 */       return this.referenceCountDelegate.isAccessible();
/*     */     }
/*     */ 
/*     */     
/*     */     int refCnt0() {
/* 182 */       return this.referenceCountDelegate.refCnt();
/*     */     }
/*     */ 
/*     */     
/*     */     ByteBuf retain0() {
/* 187 */       this.referenceCountDelegate.retain();
/* 188 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     ByteBuf retain0(int increment) {
/* 193 */       this.referenceCountDelegate.retain(increment);
/* 194 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     ByteBuf touch0() {
/* 199 */       this.referenceCountDelegate.touch();
/* 200 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     ByteBuf touch0(Object hint) {
/* 205 */       this.referenceCountDelegate.touch(hint);
/* 206 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     boolean release0() {
/* 211 */       return this.referenceCountDelegate.release();
/*     */     }
/*     */ 
/*     */     
/*     */     boolean release0(int decrement) {
/* 216 */       return this.referenceCountDelegate.release(decrement);
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteBuf duplicate() {
/* 221 */       ensureAccessible();
/* 222 */       return new PooledNonRetainedDuplicateByteBuf(this.referenceCountDelegate, this);
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteBuf retainedDuplicate() {
/* 227 */       return PooledDuplicatedByteBuf.newInstance(unwrap(), this, readerIndex(), writerIndex());
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteBuf slice(int index, int length) {
/* 232 */       checkIndex(index, length);
/* 233 */       return new AbstractPooledDerivedByteBuf.PooledNonRetainedSlicedByteBuf(this.referenceCountDelegate, unwrap(), index, length);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ByteBuf retainedSlice() {
/* 239 */       return retainedSlice(readerIndex(), capacity());
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteBuf retainedSlice(int index, int length) {
/* 244 */       return PooledSlicedByteBuf.newInstance(unwrap(), this, index, length);
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class PooledNonRetainedSlicedByteBuf
/*     */     extends UnpooledSlicedByteBuf {
/*     */     private final ByteBuf referenceCountDelegate;
/*     */     
/*     */     PooledNonRetainedSlicedByteBuf(ByteBuf referenceCountDelegate, AbstractByteBuf buffer, int index, int length) {
/* 253 */       super(buffer, index, length);
/* 254 */       this.referenceCountDelegate = referenceCountDelegate;
/*     */     }
/*     */ 
/*     */     
/*     */     boolean isAccessible0() {
/* 259 */       return this.referenceCountDelegate.isAccessible();
/*     */     }
/*     */ 
/*     */     
/*     */     int refCnt0() {
/* 264 */       return this.referenceCountDelegate.refCnt();
/*     */     }
/*     */ 
/*     */     
/*     */     ByteBuf retain0() {
/* 269 */       this.referenceCountDelegate.retain();
/* 270 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     ByteBuf retain0(int increment) {
/* 275 */       this.referenceCountDelegate.retain(increment);
/* 276 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     ByteBuf touch0() {
/* 281 */       this.referenceCountDelegate.touch();
/* 282 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     ByteBuf touch0(Object hint) {
/* 287 */       this.referenceCountDelegate.touch(hint);
/* 288 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     boolean release0() {
/* 293 */       return this.referenceCountDelegate.release();
/*     */     }
/*     */ 
/*     */     
/*     */     boolean release0(int decrement) {
/* 298 */       return this.referenceCountDelegate.release(decrement);
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteBuf duplicate() {
/* 303 */       ensureAccessible();
/* 304 */       return (new AbstractPooledDerivedByteBuf.PooledNonRetainedDuplicateByteBuf(this.referenceCountDelegate, unwrap()))
/* 305 */         .setIndex(idx(readerIndex()), idx(writerIndex()));
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteBuf retainedDuplicate() {
/* 310 */       return PooledDuplicatedByteBuf.newInstance(unwrap(), this, idx(readerIndex()), idx(writerIndex()));
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteBuf slice(int index, int length) {
/* 315 */       checkIndex(index, length);
/* 316 */       return new PooledNonRetainedSlicedByteBuf(this.referenceCountDelegate, unwrap(), idx(index), length);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ByteBuf retainedSlice() {
/* 322 */       return retainedSlice(0, capacity());
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteBuf retainedSlice(int index, int length) {
/* 327 */       return PooledSlicedByteBuf.newInstance(unwrap(), this, idx(index), length);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\buffer\AbstractPooledDerivedByteBuf.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */