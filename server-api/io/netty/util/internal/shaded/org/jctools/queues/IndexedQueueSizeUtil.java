/*    */ package io.netty.util.internal.shaded.org.jctools.queues;
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
/*    */ public final class IndexedQueueSizeUtil
/*    */ {
/*    */   public static final int PLAIN_DIVISOR = 1;
/*    */   public static final int IGNORE_PARITY_DIVISOR = 2;
/*    */   
/*    */   public static int size(IndexedQueue iq, int divisor) {
/* 47 */     long before, currentProducerIndex, after = iq.lvConsumerIndex();
/*    */ 
/*    */     
/*    */     do {
/* 51 */       before = after;
/*    */       
/* 53 */       currentProducerIndex = iq.lvProducerIndex();
/* 54 */       after = iq.lvConsumerIndex();
/* 55 */     } while (before != after);
/*    */     
/* 57 */     long size = (currentProducerIndex - after) / divisor;
/*    */ 
/*    */ 
/*    */     
/* 61 */     return sanitizedSize(iq.capacity(), size);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static int sanitizedSize(int capacity, long size) {
/* 67 */     if (size < 0L)
/*    */     {
/* 69 */       return 0;
/*    */     }
/* 71 */     if (capacity != -1 && size > capacity)
/*    */     {
/* 73 */       return capacity;
/*    */     }
/*    */     
/* 76 */     if (size > 2147483647L)
/*    */     {
/* 78 */       return Integer.MAX_VALUE;
/*    */     }
/* 80 */     return (int)size;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean isEmpty(IndexedQueue iq) {
/* 89 */     return (iq.lvConsumerIndex() >= iq.lvProducerIndex());
/*    */   }
/*    */   
/*    */   public static interface IndexedQueue {
/*    */     long lvConsumerIndex();
/*    */     
/*    */     long lvProducerIndex();
/*    */     
/*    */     int capacity();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\shaded\org\jctools\queues\IndexedQueueSizeUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */