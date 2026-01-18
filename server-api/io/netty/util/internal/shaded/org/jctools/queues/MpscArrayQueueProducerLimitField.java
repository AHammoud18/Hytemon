/*     */ package io.netty.util.internal.shaded.org.jctools.queues;
/*     */ 
/*     */ import io.netty.util.internal.shaded.org.jctools.util.UnsafeAccess;
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
/*     */ abstract class MpscArrayQueueProducerLimitField<E>
/*     */   extends MpscArrayQueueMidPad<E>
/*     */ {
/*  97 */   private static final long P_LIMIT_OFFSET = UnsafeAccess.fieldOffset(MpscArrayQueueProducerLimitField.class, "producerLimit");
/*     */ 
/*     */   
/*     */   private volatile long producerLimit;
/*     */ 
/*     */   
/*     */   MpscArrayQueueProducerLimitField(int capacity) {
/* 104 */     super(capacity);
/* 105 */     this.producerLimit = capacity;
/*     */   }
/*     */ 
/*     */   
/*     */   final long lvProducerLimit() {
/* 110 */     return this.producerLimit;
/*     */   }
/*     */ 
/*     */   
/*     */   final void soProducerLimit(long newValue) {
/* 115 */     UnsafeAccess.UNSAFE.putOrderedLong(this, P_LIMIT_OFFSET, newValue);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\shaded\org\jctools\queues\MpscArrayQueueProducerLimitField.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */