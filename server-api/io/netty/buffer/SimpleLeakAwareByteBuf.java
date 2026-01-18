/*     */ package io.netty.buffer;
/*     */ 
/*     */ import io.netty.util.IllegalReferenceCountException;
/*     */ import io.netty.util.ReferenceCounted;
/*     */ import io.netty.util.ResourceLeakTracker;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.ThrowableUtil;
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
/*     */ class SimpleLeakAwareByteBuf
/*     */   extends WrappedByteBuf
/*     */ {
/*     */   private final ByteBuf trackedByteBuf;
/*     */   final ResourceLeakTracker<ByteBuf> leak;
/*     */   
/*     */   SimpleLeakAwareByteBuf(ByteBuf wrapped, ByteBuf trackedByteBuf, ResourceLeakTracker<ByteBuf> leak) {
/*  38 */     super(wrapped);
/*  39 */     this.trackedByteBuf = (ByteBuf)ObjectUtil.checkNotNull(trackedByteBuf, "trackedByteBuf");
/*  40 */     this.leak = (ResourceLeakTracker<ByteBuf>)ObjectUtil.checkNotNull(leak, "leak");
/*     */   }
/*     */   
/*     */   SimpleLeakAwareByteBuf(ByteBuf wrapped, ResourceLeakTracker<ByteBuf> leak) {
/*  44 */     this(wrapped, wrapped, leak);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf slice() {
/*  49 */     return newSharedLeakAwareByteBuf(super.slice());
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf retainedSlice() {
/*     */     try {
/*  55 */       return unwrappedDerived(super.retainedSlice());
/*  56 */     } catch (IllegalReferenceCountException irce) {
/*  57 */       ThrowableUtil.addSuppressed((Throwable)irce, this.leak.getCloseStackTraceIfAny());
/*  58 */       throw irce;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf retainedSlice(int index, int length) {
/*     */     try {
/*  65 */       return unwrappedDerived(super.retainedSlice(index, length));
/*  66 */     } catch (IllegalReferenceCountException irce) {
/*  67 */       ThrowableUtil.addSuppressed((Throwable)irce, this.leak.getCloseStackTraceIfAny());
/*  68 */       throw irce;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf retainedDuplicate() {
/*     */     try {
/*  75 */       return unwrappedDerived(super.retainedDuplicate());
/*  76 */     } catch (IllegalReferenceCountException irce) {
/*  77 */       ThrowableUtil.addSuppressed((Throwable)irce, this.leak.getCloseStackTraceIfAny());
/*  78 */       throw irce;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf readRetainedSlice(int length) {
/*     */     try {
/*  85 */       return unwrappedDerived(super.readRetainedSlice(length));
/*  86 */     } catch (IllegalReferenceCountException irce) {
/*  87 */       ThrowableUtil.addSuppressed((Throwable)irce, this.leak.getCloseStackTraceIfAny());
/*  88 */       throw irce;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf slice(int index, int length) {
/*  94 */     return newSharedLeakAwareByteBuf(super.slice(index, length));
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf duplicate() {
/*  99 */     return newSharedLeakAwareByteBuf(super.duplicate());
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf readSlice(int length) {
/* 104 */     return newSharedLeakAwareByteBuf(super.readSlice(length));
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf asReadOnly() {
/* 109 */     return newSharedLeakAwareByteBuf(super.asReadOnly());
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf touch() {
/* 114 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf touch(Object hint) {
/* 119 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf retain() {
/*     */     try {
/* 125 */       return super.retain();
/* 126 */     } catch (IllegalReferenceCountException irce) {
/* 127 */       ThrowableUtil.addSuppressed((Throwable)irce, this.leak.getCloseStackTraceIfAny());
/* 128 */       throw irce;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf retain(int increment) {
/*     */     try {
/* 135 */       return super.retain(increment);
/* 136 */     } catch (IllegalReferenceCountException irce) {
/* 137 */       ThrowableUtil.addSuppressed((Throwable)irce, this.leak.getCloseStackTraceIfAny());
/* 138 */       throw irce;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean release() {
/*     */     try {
/* 145 */       if (super.release()) {
/* 146 */         closeLeak();
/* 147 */         return true;
/*     */       } 
/* 149 */       return false;
/* 150 */     } catch (IllegalReferenceCountException irce) {
/* 151 */       ThrowableUtil.addSuppressed((Throwable)irce, this.leak.getCloseStackTraceIfAny());
/* 152 */       throw irce;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean release(int decrement) {
/*     */     try {
/* 159 */       if (super.release(decrement)) {
/* 160 */         closeLeak();
/* 161 */         return true;
/*     */       } 
/* 163 */       return false;
/* 164 */     } catch (IllegalReferenceCountException irce) {
/* 165 */       ThrowableUtil.addSuppressed((Throwable)irce, this.leak.getCloseStackTraceIfAny());
/* 166 */       throw irce;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void closeLeak() {
/* 173 */     boolean closed = this.leak.close(this.trackedByteBuf);
/* 174 */     assert closed;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf order(ByteOrder endianness) {
/* 179 */     if (order() == endianness) {
/* 180 */       return this;
/*     */     }
/* 182 */     return newSharedLeakAwareByteBuf(super.order(endianness));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ByteBuf unwrappedDerived(ByteBuf derived) {
/* 189 */     ByteBuf unwrappedDerived = unwrapSwapped(derived);
/*     */     
/* 191 */     if (unwrappedDerived instanceof AbstractPooledDerivedByteBuf) {
/*     */       
/* 193 */       ((AbstractPooledDerivedByteBuf)unwrappedDerived).parent(this);
/*     */ 
/*     */       
/* 196 */       return newLeakAwareByteBuf(derived, AbstractByteBuf.leakDetector.trackForcibly(derived));
/*     */     } 
/* 198 */     return newSharedLeakAwareByteBuf(derived);
/*     */   }
/*     */ 
/*     */   
/*     */   private static ByteBuf unwrapSwapped(ByteBuf buf) {
/* 203 */     if (buf instanceof SwappedByteBuf) {
/*     */       do {
/* 205 */         buf = buf.unwrap();
/* 206 */       } while (buf instanceof SwappedByteBuf);
/*     */       
/* 208 */       return buf;
/*     */     } 
/* 210 */     return buf;
/*     */   }
/*     */ 
/*     */   
/*     */   private SimpleLeakAwareByteBuf newSharedLeakAwareByteBuf(ByteBuf wrapped) {
/* 215 */     return newLeakAwareByteBuf(wrapped, this.trackedByteBuf, this.leak);
/*     */   }
/*     */ 
/*     */   
/*     */   private SimpleLeakAwareByteBuf newLeakAwareByteBuf(ByteBuf wrapped, ResourceLeakTracker<ByteBuf> leakTracker) {
/* 220 */     return newLeakAwareByteBuf(wrapped, wrapped, leakTracker);
/*     */   }
/*     */ 
/*     */   
/*     */   protected SimpleLeakAwareByteBuf newLeakAwareByteBuf(ByteBuf buf, ByteBuf trackedByteBuf, ResourceLeakTracker<ByteBuf> leakTracker) {
/* 225 */     return new SimpleLeakAwareByteBuf(buf, trackedByteBuf, leakTracker);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\buffer\SimpleLeakAwareByteBuf.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */