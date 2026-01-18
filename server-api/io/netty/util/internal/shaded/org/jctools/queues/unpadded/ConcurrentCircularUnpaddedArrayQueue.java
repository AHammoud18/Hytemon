/*     */ package io.netty.util.internal.shaded.org.jctools.queues.unpadded;
/*     */ 
/*     */ import io.netty.util.internal.shaded.org.jctools.queues.IndexedQueueSizeUtil;
/*     */ import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue;
/*     */ import io.netty.util.internal.shaded.org.jctools.queues.QueueProgressIndicators;
/*     */ import io.netty.util.internal.shaded.org.jctools.queues.SupportsIterator;
/*     */ import io.netty.util.internal.shaded.org.jctools.util.Pow2;
/*     */ import io.netty.util.internal.shaded.org.jctools.util.UnsafeRefArrayAccess;
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class ConcurrentCircularUnpaddedArrayQueue<E>
/*     */   extends ConcurrentCircularUnpaddedArrayQueueL0Pad<E>
/*     */   implements MessagePassingQueue<E>, IndexedQueueSizeUtil.IndexedQueue, QueueProgressIndicators, SupportsIterator
/*     */ {
/*     */   protected final long mask;
/*     */   protected final E[] buffer;
/*     */   
/*     */   ConcurrentCircularUnpaddedArrayQueue(int capacity) {
/*  45 */     int actualCapacity = Pow2.roundToPowerOfTwo(capacity);
/*  46 */     this.mask = (actualCapacity - 1);
/*  47 */     this.buffer = (E[])UnsafeRefArrayAccess.allocateRefArray(actualCapacity);
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/*  52 */     return IndexedQueueSizeUtil.size(this, 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/*  57 */     return IndexedQueueSizeUtil.isEmpty(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  62 */     return getClass().getName();
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/*  67 */     while (poll() != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int capacity() {
/*  74 */     return (int)(this.mask + 1L);
/*     */   }
/*     */ 
/*     */   
/*     */   public long currentProducerIndex() {
/*  79 */     return lvProducerIndex();
/*     */   }
/*     */ 
/*     */   
/*     */   public long currentConsumerIndex() {
/*  84 */     return lvConsumerIndex();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<E> iterator() {
/*  99 */     long cIndex = lvConsumerIndex();
/* 100 */     long pIndex = lvProducerIndex();
/* 101 */     return new WeakIterator<E>(cIndex, pIndex, this.mask, this.buffer);
/*     */   }
/*     */ 
/*     */   
/*     */   private static class WeakIterator<E>
/*     */     implements Iterator<E>
/*     */   {
/*     */     private final long pIndex;
/*     */     
/*     */     private final long mask;
/*     */     
/*     */     private final E[] buffer;
/*     */     private long nextIndex;
/*     */     private E nextElement;
/*     */     
/*     */     WeakIterator(long cIndex, long pIndex, long mask, E[] buffer) {
/* 117 */       this.nextIndex = cIndex;
/* 118 */       this.pIndex = pIndex;
/* 119 */       this.mask = mask;
/* 120 */       this.buffer = buffer;
/* 121 */       this.nextElement = getNext();
/*     */     }
/*     */ 
/*     */     
/*     */     public void remove() {
/* 126 */       throw new UnsupportedOperationException("remove");
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 131 */       return (this.nextElement != null);
/*     */     }
/*     */ 
/*     */     
/*     */     public E next() {
/* 136 */       E e = this.nextElement;
/* 137 */       if (e == null)
/* 138 */         throw new NoSuchElementException(); 
/* 139 */       this.nextElement = getNext();
/* 140 */       return e;
/*     */     }
/*     */     
/*     */     private E getNext() {
/* 144 */       while (this.nextIndex < this.pIndex) {
/* 145 */         long offset = UnsafeRefArrayAccess.calcCircularRefElementOffset(this.nextIndex++, this.mask);
/* 146 */         E e = (E)UnsafeRefArrayAccess.lvRefElement((Object[])this.buffer, offset);
/* 147 */         if (e != null) {
/* 148 */           return e;
/*     */         }
/*     */       } 
/* 151 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\shaded\org\jctools\queue\\unpadded\ConcurrentCircularUnpaddedArrayQueue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */