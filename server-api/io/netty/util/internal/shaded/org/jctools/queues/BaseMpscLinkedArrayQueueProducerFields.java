/*    */ package io.netty.util.internal.shaded.org.jctools.queues;
/*    */ 
/*    */ import io.netty.util.internal.shaded.org.jctools.util.UnsafeAccess;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ abstract class BaseMpscLinkedArrayQueueProducerFields<E>
/*    */   extends BaseMpscLinkedArrayQueuePad1<E>
/*    */ {
/* 55 */   private static final long P_INDEX_OFFSET = UnsafeAccess.fieldOffset(BaseMpscLinkedArrayQueueProducerFields.class, "producerIndex");
/*    */ 
/*    */   
/*    */   private volatile long producerIndex;
/*    */ 
/*    */   
/*    */   public final long lvProducerIndex() {
/* 62 */     return this.producerIndex;
/*    */   }
/*    */ 
/*    */   
/*    */   final void soProducerIndex(long newValue) {
/* 67 */     UnsafeAccess.UNSAFE.putOrderedLong(this, P_INDEX_OFFSET, newValue);
/*    */   }
/*    */ 
/*    */   
/*    */   final boolean casProducerIndex(long expect, long newValue) {
/* 72 */     return UnsafeAccess.UNSAFE.compareAndSwapLong(this, P_INDEX_OFFSET, expect, newValue);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\shaded\org\jctools\queues\BaseMpscLinkedArrayQueueProducerFields.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */