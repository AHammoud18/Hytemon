/*    */ package io.netty.util.internal.shaded.org.jctools.queues.atomic;
/*    */ 
/*    */ import io.netty.util.internal.shaded.org.jctools.util.Pow2;
/*    */ import io.netty.util.internal.shaded.org.jctools.util.RangeUtil;
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
/*    */ abstract class MpscChunkedAtomicArrayQueueColdProducerFields<E>
/*    */   extends BaseMpscLinkedAtomicArrayQueue<E>
/*    */ {
/*    */   protected final long maxQueueCapacity;
/*    */   
/*    */   MpscChunkedAtomicArrayQueueColdProducerFields(int initialCapacity, int maxCapacity) {
/* 34 */     super(initialCapacity);
/* 35 */     RangeUtil.checkGreaterThanOrEqual(maxCapacity, 4, "maxCapacity");
/* 36 */     RangeUtil.checkLessThan(Pow2.roundToPowerOfTwo(initialCapacity), Pow2.roundToPowerOfTwo(maxCapacity), "initialCapacity");
/* 37 */     this.maxQueueCapacity = Pow2.roundToPowerOfTwo(maxCapacity) << 1L;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\shaded\org\jctools\queues\atomic\MpscChunkedAtomicArrayQueueColdProducerFields.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */