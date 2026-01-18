/*    */ package io.netty.util.internal.shaded.org.jctools.queues.atomic.unpadded;
/*    */ 
/*    */ import java.util.concurrent.atomic.AtomicLongFieldUpdater;
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
/*    */ abstract class MpscAtomicUnpaddedArrayQueueProducerIndexField<E>
/*    */   extends MpscAtomicUnpaddedArrayQueueL1Pad<E>
/*    */ {
/* 39 */   private static final AtomicLongFieldUpdater<MpscAtomicUnpaddedArrayQueueProducerIndexField> P_INDEX_UPDATER = AtomicLongFieldUpdater.newUpdater(MpscAtomicUnpaddedArrayQueueProducerIndexField.class, "producerIndex");
/*    */   
/*    */   private volatile long producerIndex;
/*    */   
/*    */   MpscAtomicUnpaddedArrayQueueProducerIndexField(int capacity) {
/* 44 */     super(capacity);
/*    */   }
/*    */ 
/*    */   
/*    */   public final long lvProducerIndex() {
/* 49 */     return this.producerIndex;
/*    */   }
/*    */   
/*    */   final boolean casProducerIndex(long expect, long newValue) {
/* 53 */     return P_INDEX_UPDATER.compareAndSet(this, expect, newValue);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\shaded\org\jctools\queues\atomi\\unpadded\MpscAtomicUnpaddedArrayQueueProducerIndexField.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */