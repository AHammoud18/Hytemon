/*     */ package io.netty.util.internal.shaded.org.jctools.queues.unpadded;
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
/*     */ abstract class MpscUnpaddedArrayQueueConsumerIndexField<E>
/*     */   extends MpscUnpaddedArrayQueueL2Pad<E>
/*     */ {
/* 109 */   private static final long C_INDEX_OFFSET = UnsafeAccess.fieldOffset(MpscUnpaddedArrayQueueConsumerIndexField.class, "consumerIndex");
/*     */   
/*     */   private volatile long consumerIndex;
/*     */   
/*     */   MpscUnpaddedArrayQueueConsumerIndexField(int capacity) {
/* 114 */     super(capacity);
/*     */   }
/*     */ 
/*     */   
/*     */   public final long lvConsumerIndex() {
/* 119 */     return this.consumerIndex;
/*     */   }
/*     */   
/*     */   final long lpConsumerIndex() {
/* 123 */     return UnsafeAccess.UNSAFE.getLong(this, C_INDEX_OFFSET);
/*     */   }
/*     */   
/*     */   final void soConsumerIndex(long newValue) {
/* 127 */     UnsafeAccess.UNSAFE.putOrderedLong(this, C_INDEX_OFFSET, newValue);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\shaded\org\jctools\queue\\unpadded\MpscUnpaddedArrayQueueConsumerIndexField.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */