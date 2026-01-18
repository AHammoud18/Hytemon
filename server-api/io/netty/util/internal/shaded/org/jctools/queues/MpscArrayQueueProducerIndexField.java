/*    */ package io.netty.util.internal.shaded.org.jctools.queues;
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
/*    */ abstract class MpscArrayQueueProducerIndexField<E>
/*    */   extends MpscArrayQueueL1Pad<E>
/*    */ {
/* 48 */   private static final long P_INDEX_OFFSET = UnsafeAccess.fieldOffset(MpscArrayQueueProducerIndexField.class, "producerIndex");
/*    */   
/*    */   private volatile long producerIndex;
/*    */ 
/*    */   
/*    */   MpscArrayQueueProducerIndexField(int capacity) {
/* 54 */     super(capacity);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public final long lvProducerIndex() {
/* 60 */     return this.producerIndex;
/*    */   }
/*    */ 
/*    */   
/*    */   final boolean casProducerIndex(long expect, long newValue) {
/* 65 */     return UnsafeAccess.UNSAFE.compareAndSwapLong(this, P_INDEX_OFFSET, expect, newValue);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\shaded\org\jctools\queues\MpscArrayQueueProducerIndexField.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */