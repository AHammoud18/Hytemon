/*    */ package io.netty.util.internal.shaded.org.jctools.queues.unpadded;
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
/*    */ abstract class MpscUnpaddedArrayQueueProducerIndexField<E>
/*    */   extends MpscUnpaddedArrayQueueL1Pad<E>
/*    */ {
/* 38 */   private static final long P_INDEX_OFFSET = UnsafeAccess.fieldOffset(MpscUnpaddedArrayQueueProducerIndexField.class, "producerIndex");
/*    */   
/*    */   private volatile long producerIndex;
/*    */   
/*    */   MpscUnpaddedArrayQueueProducerIndexField(int capacity) {
/* 43 */     super(capacity);
/*    */   }
/*    */ 
/*    */   
/*    */   public final long lvProducerIndex() {
/* 48 */     return this.producerIndex;
/*    */   }
/*    */   
/*    */   final boolean casProducerIndex(long expect, long newValue) {
/* 52 */     return UnsafeAccess.UNSAFE.compareAndSwapLong(this, P_INDEX_OFFSET, expect, newValue);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\shaded\org\jctools\queue\\unpadded\MpscUnpaddedArrayQueueProducerIndexField.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */