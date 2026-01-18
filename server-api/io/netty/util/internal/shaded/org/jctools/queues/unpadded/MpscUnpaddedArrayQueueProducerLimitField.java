/*    */ package io.netty.util.internal.shaded.org.jctools.queues.unpadded;
/*    */ 
/*    */ import io.netty.util.internal.shaded.org.jctools.util.UnsafeAccess;
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
/*    */ abstract class MpscUnpaddedArrayQueueProducerLimitField<E>
/*    */   extends MpscUnpaddedArrayQueueMidPad<E>
/*    */ {
/* 73 */   private static final long P_LIMIT_OFFSET = UnsafeAccess.fieldOffset(MpscUnpaddedArrayQueueProducerLimitField.class, "producerLimit");
/*    */   
/*    */   private volatile long producerLimit;
/*    */ 
/*    */   
/*    */   MpscUnpaddedArrayQueueProducerLimitField(int capacity) {
/* 79 */     super(capacity);
/* 80 */     this.producerLimit = capacity;
/*    */   }
/*    */   
/*    */   final long lvProducerLimit() {
/* 84 */     return this.producerLimit;
/*    */   }
/*    */   
/*    */   final void soProducerLimit(long newValue) {
/* 88 */     UnsafeAccess.UNSAFE.putOrderedLong(this, P_LIMIT_OFFSET, newValue);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\shaded\org\jctools\queue\\unpadded\MpscUnpaddedArrayQueueProducerLimitField.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */