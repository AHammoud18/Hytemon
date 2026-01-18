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
/*     */ abstract class BaseMpscLinkedArrayQueueConsumerFields<E>
/*     */   extends BaseMpscLinkedArrayQueuePad2<E>
/*     */ {
/*  99 */   private static final long C_INDEX_OFFSET = UnsafeAccess.fieldOffset(BaseMpscLinkedArrayQueueConsumerFields.class, "consumerIndex");
/*     */   
/*     */   private volatile long consumerIndex;
/*     */   
/*     */   protected long consumerMask;
/*     */   
/*     */   protected E[] consumerBuffer;
/*     */   
/*     */   public final long lvConsumerIndex() {
/* 108 */     return this.consumerIndex;
/*     */   }
/*     */ 
/*     */   
/*     */   final long lpConsumerIndex() {
/* 113 */     return UnsafeAccess.UNSAFE.getLong(this, C_INDEX_OFFSET);
/*     */   }
/*     */ 
/*     */   
/*     */   final void soConsumerIndex(long newValue) {
/* 118 */     UnsafeAccess.UNSAFE.putOrderedLong(this, C_INDEX_OFFSET, newValue);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\shaded\org\jctools\queues\BaseMpscLinkedArrayQueueConsumerFields.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */