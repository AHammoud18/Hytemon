/*     */ package io.netty.util.internal.shaded.org.jctools.queues.atomic.unpadded;
/*     */ 
/*     */ import java.util.concurrent.atomic.AtomicLongFieldUpdater;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class MpscAtomicUnpaddedArrayQueueConsumerIndexField<E>
/*     */   extends MpscAtomicUnpaddedArrayQueueL2Pad<E>
/*     */ {
/* 110 */   private static final AtomicLongFieldUpdater<MpscAtomicUnpaddedArrayQueueConsumerIndexField> C_INDEX_UPDATER = AtomicLongFieldUpdater.newUpdater(MpscAtomicUnpaddedArrayQueueConsumerIndexField.class, "consumerIndex");
/*     */   
/*     */   private volatile long consumerIndex;
/*     */   
/*     */   MpscAtomicUnpaddedArrayQueueConsumerIndexField(int capacity) {
/* 115 */     super(capacity);
/*     */   }
/*     */ 
/*     */   
/*     */   public final long lvConsumerIndex() {
/* 120 */     return this.consumerIndex;
/*     */   }
/*     */   
/*     */   final long lpConsumerIndex() {
/* 124 */     return this.consumerIndex;
/*     */   }
/*     */   
/*     */   final void soConsumerIndex(long newValue) {
/* 128 */     C_INDEX_UPDATER.lazySet(this, newValue);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\shaded\org\jctools\queues\atomi\\unpadded\MpscAtomicUnpaddedArrayQueueConsumerIndexField.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */