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
/*    */ abstract class MpscAtomicUnpaddedArrayQueueProducerLimitField<E>
/*    */   extends MpscAtomicUnpaddedArrayQueueMidPad<E>
/*    */ {
/* 74 */   private static final AtomicLongFieldUpdater<MpscAtomicUnpaddedArrayQueueProducerLimitField> P_LIMIT_UPDATER = AtomicLongFieldUpdater.newUpdater(MpscAtomicUnpaddedArrayQueueProducerLimitField.class, "producerLimit");
/*    */   
/*    */   private volatile long producerLimit;
/*    */ 
/*    */   
/*    */   MpscAtomicUnpaddedArrayQueueProducerLimitField(int capacity) {
/* 80 */     super(capacity);
/* 81 */     this.producerLimit = capacity;
/*    */   }
/*    */   
/*    */   final long lvProducerLimit() {
/* 85 */     return this.producerLimit;
/*    */   }
/*    */   
/*    */   final void soProducerLimit(long newValue) {
/* 89 */     P_LIMIT_UPDATER.lazySet(this, newValue);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\shaded\org\jctools\queues\atomi\\unpadded\MpscAtomicUnpaddedArrayQueueProducerLimitField.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */