/*    */ package io.netty.util.internal;
/*    */ 
/*    */ import io.netty.util.ReferenceCounted;
/*    */ import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
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
/*    */ public abstract class AtomicReferenceCountUpdater<T extends ReferenceCounted>
/*    */   extends ReferenceCountUpdater<T>
/*    */ {
/*    */   protected abstract AtomicIntegerFieldUpdater<T> updater();
/*    */   
/*    */   protected final void safeInitializeRawRefCnt(T refCntObj, int value) {
/* 31 */     updater().set(refCntObj, value);
/*    */   }
/*    */ 
/*    */   
/*    */   protected final int getAndAddRawRefCnt(T refCntObj, int increment) {
/* 36 */     return updater().getAndAdd(refCntObj, increment);
/*    */   }
/*    */ 
/*    */   
/*    */   protected final int getRawRefCnt(T refCnt) {
/* 41 */     return updater().get(refCnt);
/*    */   }
/*    */ 
/*    */   
/*    */   protected final int getAcquireRawRefCnt(T refCnt) {
/* 46 */     return updater().get(refCnt);
/*    */   }
/*    */ 
/*    */   
/*    */   protected final void setReleaseRawRefCnt(T refCnt, int value) {
/* 51 */     updater().lazySet(refCnt, value);
/*    */   }
/*    */ 
/*    */   
/*    */   protected final boolean casRawRefCnt(T refCnt, int expected, int value) {
/* 56 */     return updater().compareAndSet(refCnt, expected, value);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\AtomicReferenceCountUpdater.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */