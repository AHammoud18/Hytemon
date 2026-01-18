/*    */ package io.netty.util.internal.shaded.org.jctools.queues;
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
/*    */ abstract class MpscChunkedArrayQueueColdProducerFields<E>
/*    */   extends BaseMpscLinkedArrayQueue<E>
/*    */ {
/*    */   protected final long maxQueueCapacity;
/*    */   
/*    */   MpscChunkedArrayQueueColdProducerFields(int initialCapacity, int maxCapacity) {
/* 30 */     super(initialCapacity);
/* 31 */     RangeUtil.checkGreaterThanOrEqual(maxCapacity, 4, "maxCapacity");
/* 32 */     RangeUtil.checkLessThan(Pow2.roundToPowerOfTwo(initialCapacity), Pow2.roundToPowerOfTwo(maxCapacity), "initialCapacity");
/*    */     
/* 34 */     this.maxQueueCapacity = Pow2.roundToPowerOfTwo(maxCapacity) << 1L;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\shaded\org\jctools\queues\MpscChunkedArrayQueueColdProducerFields.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */