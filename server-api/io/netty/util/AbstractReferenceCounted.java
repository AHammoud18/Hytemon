/*    */ package io.netty.util;
/*    */ 
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
/*    */ public abstract class AbstractReferenceCounted
/*    */   implements ReferenceCounted
/*    */ {
/* 25 */   private final RefCnt refCnt = new RefCnt();
/*    */ 
/*    */   
/*    */   public int refCnt() {
/* 29 */     return RefCnt.refCnt(this.refCnt);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void setRefCnt(int refCnt) {
/* 36 */     RefCnt.setRefCnt(this.refCnt, refCnt);
/*    */   }
/*    */ 
/*    */   
/*    */   public ReferenceCounted retain() {
/* 41 */     RefCnt.retain(this.refCnt);
/* 42 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public ReferenceCounted retain(int increment) {
/* 47 */     RefCnt.retain(this.refCnt, increment);
/* 48 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public ReferenceCounted touch() {
/* 53 */     return touch(null);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean release() {
/* 58 */     return handleRelease(RefCnt.release(this.refCnt));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean release(int decrement) {
/* 63 */     return handleRelease(RefCnt.release(this.refCnt, decrement));
/*    */   }
/*    */   
/*    */   private boolean handleRelease(boolean result) {
/* 67 */     if (result) {
/* 68 */       deallocate();
/*    */     }
/* 70 */     return result;
/*    */   }
/*    */   
/*    */   protected abstract void deallocate();
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\AbstractReferenceCounted.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */