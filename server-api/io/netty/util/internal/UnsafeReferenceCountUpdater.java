/*    */ package io.netty.util.internal;
/*    */ 
/*    */ import io.netty.util.ReferenceCounted;
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
/*    */ public abstract class UnsafeReferenceCountUpdater<T extends ReferenceCounted>
/*    */   extends ReferenceCountUpdater<T>
/*    */ {
/*    */   protected abstract long refCntFieldOffset();
/*    */   
/*    */   protected final void safeInitializeRawRefCnt(T refCntObj, int value) {
/* 29 */     PlatformDependent.safeConstructPutInt(refCntObj, refCntFieldOffset(), value);
/*    */   }
/*    */ 
/*    */   
/*    */   protected final int getAndAddRawRefCnt(T refCntObj, int increment) {
/* 34 */     return PlatformDependent.getAndAddInt(refCntObj, refCntFieldOffset(), increment);
/*    */   }
/*    */ 
/*    */   
/*    */   protected final int getRawRefCnt(T refCnt) {
/* 39 */     return PlatformDependent.getInt(refCnt, refCntFieldOffset());
/*    */   }
/*    */ 
/*    */   
/*    */   protected final int getAcquireRawRefCnt(T refCnt) {
/* 44 */     return PlatformDependent.getVolatileInt(refCnt, refCntFieldOffset());
/*    */   }
/*    */ 
/*    */   
/*    */   protected final void setReleaseRawRefCnt(T refCnt, int value) {
/* 49 */     PlatformDependent.putOrderedInt(refCnt, refCntFieldOffset(), value);
/*    */   }
/*    */ 
/*    */   
/*    */   protected final boolean casRawRefCnt(T refCnt, int expected, int value) {
/* 54 */     return PlatformDependent.compareAndSwapInt(refCnt, refCntFieldOffset(), expected, value);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\UnsafeReferenceCountUpdater.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */