/*     */ package io.netty.util.internal.shaded.org.jctools.queues;
/*     */ 
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
/*     */ abstract class ConcurrentCircularArrayQueue<E>
/*     */   extends ConcurrentCircularArrayQueueL0Pad<E>
/*     */   implements MessagePassingQueue<E>, IndexedQueueSizeUtil.IndexedQueue, QueueProgressIndicators, SupportsIterator
/*     */ {
/*     */   protected final long mask;
/*     */   protected final E[] buffer;
/*     */   
/*     */   ConcurrentCircularArrayQueue(int capacity) {
/*  57 */     int actualCapacity = Pow2.roundToPowerOfTwo(capacity);
/*  58 */     this.mask = (actualCapacity - 1);
/*  59 */     this.buffer = (E[])UnsafeRefArrayAccess.allocateRefArray(actualCapacity);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/*  65 */     return IndexedQueueSizeUtil.size(this, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/*  71 */     return IndexedQueueSizeUtil.isEmpty(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*  77 */     return getClass().getName();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/*  83 */     while (poll() != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int capacity() {
/*  92 */     return (int)(this.mask + 1L);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public long currentProducerIndex() {
/*  98 */     return lvProducerIndex();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public long currentConsumerIndex() {
/* 104 */     return lvConsumerIndex();
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
/* 119 */     long cIndex = lvConsumerIndex();
/* 120 */     long pIndex = lvProducerIndex();
/*     */     
/* 122 */     return new WeakIterator<E>(cIndex, pIndex, this.mask, this.buffer);
/*     */   }
/*     */   
/*     */   private static class WeakIterator<E> implements Iterator<E> {
/*     */     private final long pIndex;
/*     */     private final long mask;
/*     */     private final E[] buffer;
/*     */     private long nextIndex;
/*     */     private E nextElement;
/*     */     
/*     */     WeakIterator(long cIndex, long pIndex, long mask, E[] buffer) {
/* 133 */       this.nextIndex = cIndex;
/* 134 */       this.pIndex = pIndex;
/* 135 */       this.mask = mask;
/* 136 */       this.buffer = buffer;
/* 137 */       this.nextElement = getNext();
/*     */     }
/*     */ 
/*     */     
/*     */     public void remove() {
/* 142 */       throw new UnsupportedOperationException("remove");
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 147 */       return (this.nextElement != null);
/*     */     }
/*     */ 
/*     */     
/*     */     public E next() {
/* 152 */       E e = this.nextElement;
/* 153 */       if (e == null)
/* 154 */         throw new NoSuchElementException(); 
/* 155 */       this.nextElement = getNext();
/* 156 */       return e;
/*     */     }
/*     */     
/*     */     private E getNext() {
/* 160 */       while (this.nextIndex < this.pIndex) {
/* 161 */         long offset = UnsafeRefArrayAccess.calcCircularRefElementOffset(this.nextIndex++, this.mask);
/* 162 */         E e = (E)UnsafeRefArrayAccess.lvRefElement((Object[])this.buffer, offset);
/* 163 */         if (e != null) {
/* 164 */           return e;
/*     */         }
/*     */       } 
/* 167 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\shaded\org\jctools\queues\ConcurrentCircularArrayQueue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */