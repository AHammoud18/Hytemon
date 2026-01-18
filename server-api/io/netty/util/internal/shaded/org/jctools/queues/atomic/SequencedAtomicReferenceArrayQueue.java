/*    */ package io.netty.util.internal.shaded.org.jctools.queues.atomic;
/*    */ 
/*    */ import java.util.concurrent.atomic.AtomicLongArray;
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
/*    */ public abstract class SequencedAtomicReferenceArrayQueue<E>
/*    */   extends AtomicReferenceArrayQueue<E>
/*    */ {
/*    */   protected final AtomicLongArray sequenceBuffer;
/*    */   
/*    */   public SequencedAtomicReferenceArrayQueue(int capacity) {
/* 28 */     super(capacity);
/* 29 */     int actualCapacity = this.mask + 1;
/*    */     
/* 31 */     this.sequenceBuffer = new AtomicLongArray(actualCapacity);
/* 32 */     for (int i = 0; i < actualCapacity; i++)
/*    */     {
/* 34 */       soSequence(this.sequenceBuffer, i, i);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   protected final long calcSequenceOffset(long index) {
/* 40 */     return calcSequenceOffset(index, this.mask);
/*    */   }
/*    */ 
/*    */   
/*    */   protected static int calcSequenceOffset(long index, int mask) {
/* 45 */     return (int)index & mask;
/*    */   }
/*    */ 
/*    */   
/*    */   protected final void soSequence(AtomicLongArray buffer, int offset, long e) {
/* 50 */     buffer.lazySet(offset, e);
/*    */   }
/*    */ 
/*    */   
/*    */   protected final long lvSequence(AtomicLongArray buffer, int offset) {
/* 55 */     return buffer.get(offset);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\shaded\org\jctools\queues\atomic\SequencedAtomicReferenceArrayQueue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */