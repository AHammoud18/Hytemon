/*     */ package io.netty.util.internal.shaded.org.jctools.queues.atomic;
/*     */ 
/*     */ import io.netty.util.internal.shaded.org.jctools.queues.IndexedQueueSizeUtil;
/*     */ import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue;
/*     */ import io.netty.util.internal.shaded.org.jctools.queues.QueueProgressIndicators;
/*     */ import io.netty.util.internal.shaded.org.jctools.queues.SupportsIterator;
/*     */ import io.netty.util.internal.shaded.org.jctools.util.Pow2;
/*     */ import java.util.AbstractQueue;
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.concurrent.atomic.AtomicReferenceArray;
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
/*     */ public abstract class AtomicReferenceArrayQueue<E>
/*     */   extends AbstractQueue<E>
/*     */   implements IndexedQueueSizeUtil.IndexedQueue, QueueProgressIndicators, MessagePassingQueue<E>, SupportsIterator
/*     */ {
/*     */   protected final AtomicReferenceArray<E> buffer;
/*     */   protected final int mask;
/*     */   
/*     */   public AtomicReferenceArrayQueue(int capacity) {
/*  39 */     int actualCapacity = Pow2.roundToPowerOfTwo(capacity);
/*  40 */     this.mask = actualCapacity - 1;
/*  41 */     this.buffer = new AtomicReferenceArray<E>(actualCapacity);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*  47 */     return getClass().getName();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/*  53 */     while (poll() != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int capacity() {
/*  62 */     return this.mask + 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int size() {
/*  72 */     return IndexedQueueSizeUtil.size(this, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean isEmpty() {
/*  78 */     return IndexedQueueSizeUtil.isEmpty(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final long currentProducerIndex() {
/*  84 */     return lvProducerIndex();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final long currentConsumerIndex() {
/*  90 */     return lvConsumerIndex();
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
/*     */   public final Iterator<E> iterator() {
/* 105 */     long cIndex = lvConsumerIndex();
/* 106 */     long pIndex = lvProducerIndex();
/*     */     
/* 108 */     return new WeakIterator<E>(cIndex, pIndex, this.mask, this.buffer);
/*     */   }
/*     */   
/*     */   private static class WeakIterator<E>
/*     */     implements Iterator<E> {
/*     */     private final long pIndex;
/*     */     private final int mask;
/*     */     private final AtomicReferenceArray<E> buffer;
/*     */     private long nextIndex;
/*     */     private E nextElement;
/*     */     
/*     */     WeakIterator(long cIndex, long pIndex, int mask, AtomicReferenceArray<E> buffer) {
/* 120 */       this.nextIndex = cIndex;
/* 121 */       this.pIndex = pIndex;
/* 122 */       this.mask = mask;
/* 123 */       this.buffer = buffer;
/* 124 */       this.nextElement = getNext();
/*     */     }
/*     */ 
/*     */     
/*     */     public void remove() {
/* 129 */       throw new UnsupportedOperationException("remove");
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 134 */       return (this.nextElement != null);
/*     */     }
/*     */ 
/*     */     
/*     */     public E next() {
/* 139 */       E e = this.nextElement;
/* 140 */       if (e == null)
/* 141 */         throw new NoSuchElementException(); 
/* 142 */       this.nextElement = getNext();
/* 143 */       return e;
/*     */     }
/*     */     
/*     */     private E getNext() {
/* 147 */       int mask = this.mask;
/* 148 */       AtomicReferenceArray<E> buffer = this.buffer;
/* 149 */       while (this.nextIndex < this.pIndex) {
/* 150 */         int offset = AtomicQueueUtil.calcCircularRefElementOffset(this.nextIndex++, mask);
/* 151 */         E e = AtomicQueueUtil.lvRefElement(buffer, offset);
/* 152 */         if (e != null) {
/* 153 */           return e;
/*     */         }
/*     */       } 
/* 156 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\shaded\org\jctools\queues\atomic\AtomicReferenceArrayQueue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */