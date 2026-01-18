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
/*     */ abstract class BaseMpscLinkedArrayQueueColdProducerFields<E>
/*     */   extends BaseMpscLinkedArrayQueuePad3<E>
/*     */ {
/* 145 */   private static final long P_LIMIT_OFFSET = UnsafeAccess.fieldOffset(BaseMpscLinkedArrayQueueColdProducerFields.class, "producerLimit");
/*     */   
/*     */   private volatile long producerLimit;
/*     */   
/*     */   protected long producerMask;
/*     */   protected E[] producerBuffer;
/*     */   
/*     */   final long lvProducerLimit() {
/* 153 */     return this.producerLimit;
/*     */   }
/*     */ 
/*     */   
/*     */   final boolean casProducerLimit(long expect, long newValue) {
/* 158 */     return UnsafeAccess.UNSAFE.compareAndSwapLong(this, P_LIMIT_OFFSET, expect, newValue);
/*     */   }
/*     */ 
/*     */   
/*     */   final void soProducerLimit(long newValue) {
/* 163 */     UnsafeAccess.UNSAFE.putOrderedLong(this, P_LIMIT_OFFSET, newValue);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\shaded\org\jctools\queues\BaseMpscLinkedArrayQueueColdProducerFields.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */