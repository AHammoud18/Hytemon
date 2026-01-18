/*     */ package io.netty.util.internal.shaded.org.jctools.queues.atomic;
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
/*     */ abstract class BaseMpscLinkedAtomicArrayQueueProducerFields<E>
/*     */   extends BaseMpscLinkedAtomicArrayQueuePad1<E>
/*     */ {
/*  88 */   private static final AtomicLongFieldUpdater<BaseMpscLinkedAtomicArrayQueueProducerFields> P_INDEX_UPDATER = AtomicLongFieldUpdater.newUpdater(BaseMpscLinkedAtomicArrayQueueProducerFields.class, "producerIndex");
/*     */   
/*     */   private volatile long producerIndex;
/*     */ 
/*     */   
/*     */   public final long lvProducerIndex() {
/*  94 */     return this.producerIndex;
/*     */   }
/*     */   
/*     */   final void soProducerIndex(long newValue) {
/*  98 */     P_INDEX_UPDATER.lazySet(this, newValue);
/*     */   }
/*     */   
/*     */   final boolean casProducerIndex(long expect, long newValue) {
/* 102 */     return P_INDEX_UPDATER.compareAndSet(this, expect, newValue);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\shaded\org\jctools\queues\atomic\BaseMpscLinkedAtomicArrayQueueProducerFields.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */