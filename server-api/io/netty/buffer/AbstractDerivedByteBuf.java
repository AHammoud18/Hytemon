/*     */ package io.netty.buffer;
/*     */ 
/*     */ import io.netty.util.ReferenceCounted;
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
/*     */ @Deprecated
/*     */ public abstract class AbstractDerivedByteBuf
/*     */   extends AbstractByteBuf
/*     */ {
/*     */   protected AbstractDerivedByteBuf(int maxCapacity) {
/*  31 */     super(maxCapacity);
/*     */   }
/*     */ 
/*     */   
/*     */   final boolean isAccessible() {
/*  36 */     return isAccessible0();
/*     */   }
/*     */   
/*     */   boolean isAccessible0() {
/*  40 */     return unwrap().isAccessible();
/*     */   }
/*     */ 
/*     */   
/*     */   public final int refCnt() {
/*  45 */     return refCnt0();
/*     */   }
/*     */   
/*     */   int refCnt0() {
/*  49 */     return unwrap().refCnt();
/*     */   }
/*     */ 
/*     */   
/*     */   public final ByteBuf retain() {
/*  54 */     return retain0();
/*     */   }
/*     */   
/*     */   ByteBuf retain0() {
/*  58 */     unwrap().retain();
/*  59 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public final ByteBuf retain(int increment) {
/*  64 */     return retain0(increment);
/*     */   }
/*     */   
/*     */   ByteBuf retain0(int increment) {
/*  68 */     unwrap().retain(increment);
/*  69 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public final ByteBuf touch() {
/*  74 */     return touch0();
/*     */   }
/*     */   
/*     */   ByteBuf touch0() {
/*  78 */     unwrap().touch();
/*  79 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public final ByteBuf touch(Object hint) {
/*  84 */     return touch0(hint);
/*     */   }
/*     */   
/*     */   ByteBuf touch0(Object hint) {
/*  88 */     unwrap().touch(hint);
/*  89 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean release() {
/*  94 */     return release0();
/*     */   }
/*     */   
/*     */   boolean release0() {
/*  98 */     return unwrap().release();
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean release(int decrement) {
/* 103 */     return release0(decrement);
/*     */   }
/*     */   
/*     */   boolean release0(int decrement) {
/* 107 */     return unwrap().release(decrement);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isReadOnly() {
/* 112 */     return unwrap().isReadOnly();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer internalNioBuffer(int index, int length) {
/* 117 */     return nioBuffer(index, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer nioBuffer(int index, int length) {
/* 122 */     return unwrap().nioBuffer(index, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isContiguous() {
/* 127 */     return unwrap().isContiguous();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\buffer\AbstractDerivedByteBuf.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */