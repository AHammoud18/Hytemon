/*    */ package io.netty.util.internal;
/*    */ 
/*    */ import io.netty.util.ReferenceCounted;
/*    */ import java.lang.invoke.VarHandle;
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
/*    */ 
/*    */ public abstract class VarHandleReferenceCountUpdater<T extends ReferenceCounted>
/*    */   extends ReferenceCountUpdater<T>
/*    */ {
/*    */   protected abstract VarHandle varHandle();
/*    */   
/*    */   protected final void safeInitializeRawRefCnt(T refCntObj, int value) {
/* 31 */     varHandle().set((ReferenceCounted)refCntObj, value);
/*    */   }
/*    */ 
/*    */   
/*    */   protected final int getAndAddRawRefCnt(T refCntObj, int increment) {
/* 36 */     return varHandle().getAndAdd((ReferenceCounted)refCntObj, increment);
/*    */   }
/*    */ 
/*    */   
/*    */   protected final int getRawRefCnt(T refCnt) {
/* 41 */     return varHandle().get((ReferenceCounted)refCnt);
/*    */   }
/*    */ 
/*    */   
/*    */   protected final int getAcquireRawRefCnt(T refCnt) {
/* 46 */     return varHandle().getAcquire((ReferenceCounted)refCnt);
/*    */   }
/*    */ 
/*    */   
/*    */   protected final void setReleaseRawRefCnt(T refCnt, int value) {
/* 51 */     varHandle().setRelease((ReferenceCounted)refCnt, value);
/*    */   }
/*    */ 
/*    */   
/*    */   protected final boolean casRawRefCnt(T refCnt, int expected, int value) {
/* 56 */     return varHandle().compareAndSet((ReferenceCounted)refCnt, expected, value);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\VarHandleReferenceCountUpdater.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */