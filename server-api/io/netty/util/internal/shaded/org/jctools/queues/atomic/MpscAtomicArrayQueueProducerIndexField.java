/*    */ package io.netty.util.internal.shaded.org.jctools.queues.atomic;
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
/*    */ abstract class MpscAtomicArrayQueueProducerIndexField<E>
/*    */   extends MpscAtomicArrayQueueL1Pad<E>
/*    */ {
/* 83 */   private static final AtomicLongFieldUpdater<MpscAtomicArrayQueueProducerIndexField> P_INDEX_UPDATER = AtomicLongFieldUpdater.newUpdater(MpscAtomicArrayQueueProducerIndexField.class, "producerIndex");
/*    */   
/*    */   private volatile long producerIndex;
/*    */   
/*    */   MpscAtomicArrayQueueProducerIndexField(int capacity) {
/* 88 */     super(capacity);
/*    */   }
/*    */ 
/*    */   
/*    */   public final long lvProducerIndex() {
/* 93 */     return this.producerIndex;
/*    */   }
/*    */   
/*    */   final boolean casProducerIndex(long expect, long newValue) {
/* 97 */     return P_INDEX_UPDATER.compareAndSet(this, expect, newValue);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\shaded\org\jctools\queues\atomic\MpscAtomicArrayQueueProducerIndexField.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */