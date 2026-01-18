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
/*     */ abstract class MpmcArrayQueueConsumerIndexField<E>
/*     */   extends MpmcArrayQueueL2Pad<E>
/*     */ {
/* 100 */   private static final long C_INDEX_OFFSET = UnsafeAccess.fieldOffset(MpmcArrayQueueConsumerIndexField.class, "consumerIndex");
/*     */   
/*     */   private volatile long consumerIndex;
/*     */ 
/*     */   
/*     */   MpmcArrayQueueConsumerIndexField(int capacity) {
/* 106 */     super(capacity);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final long lvConsumerIndex() {
/* 112 */     return this.consumerIndex;
/*     */   }
/*     */ 
/*     */   
/*     */   final boolean casConsumerIndex(long expect, long newValue) {
/* 117 */     return UnsafeAccess.UNSAFE.compareAndSwapLong(this, C_INDEX_OFFSET, expect, newValue);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\shaded\org\jctools\queues\MpmcArrayQueueConsumerIndexField.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */