/*    */ package io.netty.util.internal.shaded.org.jctools.queues;
/*    */ 
/*    */ import io.netty.util.internal.shaded.org.jctools.util.UnsafeLongArrayAccess;
/*    */ import java.util.Iterator;
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
/*    */ public abstract class ConcurrentSequencedCircularArrayQueue<E>
/*    */   extends ConcurrentCircularArrayQueue<E>
/*    */ {
/*    */   protected final long[] sequenceBuffer;
/*    */   
/*    */   public ConcurrentSequencedCircularArrayQueue(int capacity) {
/* 24 */     super(capacity);
/* 25 */     int actualCapacity = (int)(this.mask + 1L);
/*    */     
/* 27 */     this.sequenceBuffer = UnsafeLongArrayAccess.allocateLongArray(actualCapacity);
/* 28 */     for (long i = 0L; i < actualCapacity; i++)
/*    */     {
/* 30 */       UnsafeLongArrayAccess.soLongElement(this.sequenceBuffer, UnsafeLongArrayAccess.calcCircularLongElementOffset(i, this.mask), i);
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\shaded\org\jctools\queues\ConcurrentSequencedCircularArrayQueue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */