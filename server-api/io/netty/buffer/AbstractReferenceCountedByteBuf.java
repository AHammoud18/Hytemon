/*    */ package io.netty.buffer;
/*    */ 
/*    */ import io.netty.util.ReferenceCounted;
/*    */ import io.netty.util.internal.RefCnt;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class AbstractReferenceCountedByteBuf
/*    */   extends AbstractByteBuf
/*    */ {
/* 27 */   private final RefCnt refCnt = new RefCnt();
/*    */   
/*    */   protected AbstractReferenceCountedByteBuf(int maxCapacity) {
/* 30 */     super(maxCapacity);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   boolean isAccessible() {
/* 37 */     return RefCnt.isLiveNonVolatile(this.refCnt);
/*    */   }
/*    */ 
/*    */   
/*    */   public int refCnt() {
/* 42 */     return RefCnt.refCnt(this.refCnt);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected final void setRefCnt(int count) {
/* 49 */     RefCnt.setRefCnt(this.refCnt, count);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected final void resetRefCnt() {
/* 56 */     RefCnt.resetRefCnt(this.refCnt);
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuf retain() {
/* 61 */     RefCnt.retain(this.refCnt);
/* 62 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuf retain(int increment) {
/* 67 */     RefCnt.retain(this.refCnt, increment);
/* 68 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuf touch() {
/* 73 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuf touch(Object hint) {
/* 78 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean release() {
/* 83 */     return handleRelease(RefCnt.release(this.refCnt));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean release(int decrement) {
/* 88 */     return handleRelease(RefCnt.release(this.refCnt, decrement));
/*    */   }
/*    */   
/*    */   private boolean handleRelease(boolean result) {
/* 92 */     if (result) {
/* 93 */       deallocate();
/*    */     }
/* 95 */     return result;
/*    */   }
/*    */   
/*    */   protected abstract void deallocate();
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\buffer\AbstractReferenceCountedByteBuf.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */