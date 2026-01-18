/*     */ package io.netty.util.internal.shaded.org.jctools.queues;
/*     */ 
/*     */ import io.netty.util.internal.shaded.org.jctools.util.PortableJvmInfo;
/*     */ import io.netty.util.internal.shaded.org.jctools.util.Pow2;
/*     */ import io.netty.util.internal.shaded.org.jctools.util.RangeUtil;
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
/*     */ abstract class BaseMpscLinkedArrayQueue<E>
/*     */   extends BaseMpscLinkedArrayQueueColdProducerFields<E>
/*     */   implements MessagePassingQueue<E>, QueueProgressIndicators
/*     */ {
/* 177 */   private static final Object JUMP = new Object();
/* 178 */   private static final Object BUFFER_CONSUMED = new Object();
/*     */ 
/*     */   
/*     */   private static final int CONTINUE_TO_P_INDEX_CAS = 0;
/*     */   
/*     */   private static final int RETRY = 1;
/*     */   
/*     */   private static final int QUEUE_FULL = 2;
/*     */   
/*     */   private static final int QUEUE_RESIZE = 3;
/*     */ 
/*     */   
/*     */   public BaseMpscLinkedArrayQueue(int initialCapacity) {
/* 191 */     RangeUtil.checkGreaterThanOrEqual(initialCapacity, 2, "initialCapacity");
/*     */     
/* 193 */     int p2capacity = Pow2.roundToPowerOfTwo(initialCapacity);
/*     */     
/* 195 */     long mask = (p2capacity - 1 << 1);
/*     */     
/* 197 */     E[] buffer = (E[])UnsafeRefArrayAccess.allocateRefArray(p2capacity + 1);
/* 198 */     this.producerBuffer = buffer;
/* 199 */     this.producerMask = mask;
/* 200 */     this.consumerBuffer = buffer;
/* 201 */     this.consumerMask = mask;
/* 202 */     soProducerLimit(mask);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 208 */     return IndexedQueueSizeUtil.size(this, 2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 218 */     return ((lvConsumerIndex() - lvProducerIndex()) / 2L == 0L);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 224 */     return getClass().getName();
/*     */   }
/*     */   public boolean offer(E e) {
/*     */     long mask;
/*     */     E[] buffer;
/*     */     long pIndex;
/* 230 */     if (null == e)
/*     */     {
/* 232 */       throw new NullPointerException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     while (true) {
/* 241 */       long producerLimit = lvProducerLimit();
/* 242 */       pIndex = lvProducerIndex();
/*     */       
/* 244 */       if ((pIndex & 0x1L) == 1L) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 251 */       mask = this.producerMask;
/* 252 */       buffer = this.producerBuffer;
/*     */ 
/*     */ 
/*     */       
/* 256 */       if (producerLimit <= pIndex) {
/*     */         
/* 258 */         int result = offerSlowPath(mask, pIndex, producerLimit);
/* 259 */         switch (result) {
/*     */           case 1:
/*     */             continue;
/*     */ 
/*     */ 
/*     */           
/*     */           case 2:
/* 266 */             return false;
/*     */           case 3:
/* 268 */             resize(mask, buffer, pIndex, e, (MessagePassingQueue.Supplier<E>)null);
/* 269 */             return true;
/*     */         } 
/*     */       
/*     */       } 
/* 273 */       if (casProducerIndex(pIndex, pIndex + 2L)) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 279 */     long offset = LinkedArrayQueueUtil.modifiedCalcCircularRefElementOffset(pIndex, mask);
/* 280 */     UnsafeRefArrayAccess.soRefElement((Object[])buffer, offset, e);
/* 281 */     return true;
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
/*     */   public E poll() {
/* 293 */     E[] buffer = this.consumerBuffer;
/* 294 */     long cIndex = lpConsumerIndex();
/* 295 */     long mask = this.consumerMask;
/*     */     
/* 297 */     long offset = LinkedArrayQueueUtil.modifiedCalcCircularRefElementOffset(cIndex, mask);
/* 298 */     Object e = UnsafeRefArrayAccess.lvRefElement((Object[])buffer, offset);
/* 299 */     if (e == null) {
/*     */       
/* 301 */       long pIndex = lvProducerIndex();
/*     */       
/* 303 */       if ((cIndex - pIndex) / 2L == 0L)
/*     */       {
/* 305 */         return null;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       do {
/* 311 */         e = UnsafeRefArrayAccess.lvRefElement((Object[])buffer, offset);
/*     */       }
/* 313 */       while (e == null);
/*     */     } 
/*     */     
/* 316 */     if (e == JUMP) {
/*     */       
/* 318 */       E[] nextBuffer = nextBuffer(buffer, mask);
/* 319 */       return newBufferPoll(nextBuffer, cIndex);
/*     */     } 
/*     */     
/* 322 */     UnsafeRefArrayAccess.soRefElement((Object[])buffer, offset, null);
/* 323 */     soConsumerIndex(cIndex + 2L);
/* 324 */     return (E)e;
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
/*     */   public E peek() {
/* 336 */     E[] buffer = this.consumerBuffer;
/* 337 */     long cIndex = lpConsumerIndex();
/* 338 */     long mask = this.consumerMask;
/*     */     
/* 340 */     long offset = LinkedArrayQueueUtil.modifiedCalcCircularRefElementOffset(cIndex, mask);
/* 341 */     Object e = UnsafeRefArrayAccess.lvRefElement((Object[])buffer, offset);
/* 342 */     if (e == null) {
/*     */       
/* 344 */       long pIndex = lvProducerIndex();
/*     */       
/* 346 */       if ((cIndex - pIndex) / 2L == 0L)
/*     */       {
/* 348 */         return null;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       do {
/* 354 */         e = UnsafeRefArrayAccess.lvRefElement((Object[])buffer, offset);
/*     */       }
/* 356 */       while (e == null);
/*     */     } 
/* 358 */     if (e == JUMP)
/*     */     {
/* 360 */       return newBufferPeek(nextBuffer(buffer, mask), cIndex);
/*     */     }
/* 362 */     return (E)e;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int offerSlowPath(long mask, long pIndex, long producerLimit) {
/* 370 */     long cIndex = lvConsumerIndex();
/* 371 */     long bufferCapacity = getCurrentBufferCapacity(mask);
/*     */     
/* 373 */     if (cIndex + bufferCapacity > pIndex) {
/*     */       
/* 375 */       if (!casProducerLimit(producerLimit, cIndex + bufferCapacity))
/*     */       {
/*     */         
/* 378 */         return 1;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 383 */       return 0;
/*     */     } 
/*     */ 
/*     */     
/* 387 */     if (availableInQueue(pIndex, cIndex) <= 0L)
/*     */     {
/*     */       
/* 390 */       return 2;
/*     */     }
/*     */     
/* 393 */     if (casProducerIndex(pIndex, pIndex + 1L))
/*     */     {
/*     */       
/* 396 */       return 3;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 401 */     return 1;
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
/*     */   private E[] nextBuffer(E[] buffer, long mask) {
/* 413 */     long offset = nextArrayOffset(mask);
/* 414 */     E[] nextBuffer = (E[])UnsafeRefArrayAccess.lvRefElement((Object[])buffer, offset);
/* 415 */     this.consumerBuffer = nextBuffer;
/* 416 */     this.consumerMask = (LinkedArrayQueueUtil.length((Object[])nextBuffer) - 2 << 1);
/* 417 */     UnsafeRefArrayAccess.soRefElement((Object[])buffer, offset, BUFFER_CONSUMED);
/* 418 */     return nextBuffer;
/*     */   }
/*     */ 
/*     */   
/*     */   private static long nextArrayOffset(long mask) {
/* 423 */     return LinkedArrayQueueUtil.modifiedCalcCircularRefElementOffset(mask + 2L, Long.MAX_VALUE);
/*     */   }
/*     */ 
/*     */   
/*     */   private E newBufferPoll(E[] nextBuffer, long cIndex) {
/* 428 */     long offset = LinkedArrayQueueUtil.modifiedCalcCircularRefElementOffset(cIndex, this.consumerMask);
/* 429 */     E n = (E)UnsafeRefArrayAccess.lvRefElement((Object[])nextBuffer, offset);
/* 430 */     if (n == null)
/*     */     {
/* 432 */       throw new IllegalStateException("new buffer must have at least one element");
/*     */     }
/* 434 */     UnsafeRefArrayAccess.soRefElement((Object[])nextBuffer, offset, null);
/* 435 */     soConsumerIndex(cIndex + 2L);
/* 436 */     return n;
/*     */   }
/*     */ 
/*     */   
/*     */   private E newBufferPeek(E[] nextBuffer, long cIndex) {
/* 441 */     long offset = LinkedArrayQueueUtil.modifiedCalcCircularRefElementOffset(cIndex, this.consumerMask);
/* 442 */     E n = (E)UnsafeRefArrayAccess.lvRefElement((Object[])nextBuffer, offset);
/* 443 */     if (null == n)
/*     */     {
/* 445 */       throw new IllegalStateException("new buffer must have at least one element");
/*     */     }
/* 447 */     return n;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public long currentProducerIndex() {
/* 453 */     return lvProducerIndex() / 2L;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public long currentConsumerIndex() {
/* 459 */     return lvConsumerIndex() / 2L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean relaxedOffer(E e) {
/* 468 */     return offer(e);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public E relaxedPoll() {
/* 475 */     E[] buffer = this.consumerBuffer;
/* 476 */     long cIndex = lpConsumerIndex();
/* 477 */     long mask = this.consumerMask;
/*     */     
/* 479 */     long offset = LinkedArrayQueueUtil.modifiedCalcCircularRefElementOffset(cIndex, mask);
/* 480 */     Object e = UnsafeRefArrayAccess.lvRefElement((Object[])buffer, offset);
/* 481 */     if (e == null)
/*     */     {
/* 483 */       return null;
/*     */     }
/* 485 */     if (e == JUMP) {
/*     */       
/* 487 */       E[] nextBuffer = nextBuffer(buffer, mask);
/* 488 */       return newBufferPoll(nextBuffer, cIndex);
/*     */     } 
/* 490 */     UnsafeRefArrayAccess.soRefElement((Object[])buffer, offset, null);
/* 491 */     soConsumerIndex(cIndex + 2L);
/* 492 */     return (E)e;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public E relaxedPeek() {
/* 499 */     E[] buffer = this.consumerBuffer;
/* 500 */     long cIndex = lpConsumerIndex();
/* 501 */     long mask = this.consumerMask;
/*     */     
/* 503 */     long offset = LinkedArrayQueueUtil.modifiedCalcCircularRefElementOffset(cIndex, mask);
/* 504 */     Object e = UnsafeRefArrayAccess.lvRefElement((Object[])buffer, offset);
/* 505 */     if (e == JUMP)
/*     */     {
/* 507 */       return newBufferPeek(nextBuffer(buffer, mask), cIndex);
/*     */     }
/* 509 */     return (E)e;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int fill(MessagePassingQueue.Supplier<E> s) {
/* 515 */     long result = 0L;
/* 516 */     int capacity = capacity();
/*     */     
/*     */     while (true) {
/* 519 */       int filled = fill(s, PortableJvmInfo.RECOMENDED_OFFER_BATCH);
/* 520 */       if (filled == 0)
/*     */       {
/* 522 */         return (int)result;
/*     */       }
/* 524 */       result += filled;
/*     */       
/* 526 */       if (result > capacity)
/* 527 */         return (int)result; 
/*     */     } 
/*     */   } public int fill(MessagePassingQueue.Supplier<E> s, int limit) {
/*     */     long mask;
/*     */     E[] buffer;
/*     */     long pIndex, batchIndex;
/* 533 */     if (null == s)
/* 534 */       throw new IllegalArgumentException("supplier is null"); 
/* 535 */     if (limit < 0)
/* 536 */       throw new IllegalArgumentException("limit is negative:" + limit); 
/* 537 */     if (limit == 0) {
/* 538 */       return 0;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     while (true) {
/* 546 */       long producerLimit = lvProducerLimit();
/* 547 */       pIndex = lvProducerIndex();
/*     */       
/* 549 */       if ((pIndex & 0x1L) == 1L) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 558 */       mask = this.producerMask;
/* 559 */       buffer = this.producerBuffer;
/*     */ 
/*     */ 
/*     */       
/* 563 */       batchIndex = Math.min(producerLimit, pIndex + 2L * limit);
/*     */       
/* 565 */       if (pIndex >= producerLimit) {
/*     */         
/* 567 */         int result = offerSlowPath(mask, pIndex, producerLimit);
/* 568 */         switch (result) {
/*     */           case 0:
/*     */           case 1:
/*     */             continue;
/*     */ 
/*     */           
/*     */           case 2:
/* 575 */             return 0;
/*     */           case 3:
/* 577 */             resize(mask, buffer, pIndex, (E)null, s);
/* 578 */             return 1;
/*     */         } 
/*     */ 
/*     */       
/*     */       } 
/* 583 */       if (casProducerIndex(pIndex, batchIndex))
/*     */         break; 
/* 585 */     }  int claimedSlots = (int)((batchIndex - pIndex) / 2L);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 590 */     for (int i = 0; i < claimedSlots; i++) {
/*     */       
/* 592 */       long offset = LinkedArrayQueueUtil.modifiedCalcCircularRefElementOffset(pIndex + 2L * i, mask);
/* 593 */       UnsafeRefArrayAccess.soRefElement((Object[])buffer, offset, s.get());
/*     */     } 
/* 595 */     return claimedSlots;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void fill(MessagePassingQueue.Supplier<E> s, MessagePassingQueue.WaitStrategy wait, MessagePassingQueue.ExitCondition exit) {
/* 601 */     MessagePassingQueueUtil.fill(this, s, wait, exit);
/*     */   }
/*     */ 
/*     */   
/*     */   public int drain(MessagePassingQueue.Consumer<E> c) {
/* 606 */     return drain(c, capacity());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int drain(MessagePassingQueue.Consumer<E> c, int limit) {
/* 612 */     return MessagePassingQueueUtil.drain(this, c, limit);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drain(MessagePassingQueue.Consumer<E> c, MessagePassingQueue.WaitStrategy wait, MessagePassingQueue.ExitCondition exit) {
/* 618 */     MessagePassingQueueUtil.drain(this, c, wait, exit);
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
/* 633 */     return new WeakIterator<E>(this.consumerBuffer, lvConsumerIndex(), lvProducerIndex());
/*     */   }
/*     */   
/*     */   private static class WeakIterator<E>
/*     */     implements Iterator<E>
/*     */   {
/*     */     private final long pIndex;
/*     */     private long nextIndex;
/*     */     private E nextElement;
/*     */     private E[] currentBuffer;
/*     */     private int mask;
/*     */     
/*     */     WeakIterator(E[] currentBuffer, long cIndex, long pIndex) {
/* 646 */       this.pIndex = pIndex >> 1L;
/* 647 */       this.nextIndex = cIndex >> 1L;
/* 648 */       setBuffer(currentBuffer);
/* 649 */       this.nextElement = getNext();
/*     */     }
/*     */ 
/*     */     
/*     */     public void remove() {
/* 654 */       throw new UnsupportedOperationException("remove");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 660 */       return (this.nextElement != null);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public E next() {
/* 666 */       E e = this.nextElement;
/* 667 */       if (e == null)
/*     */       {
/* 669 */         throw new NoSuchElementException();
/*     */       }
/* 671 */       this.nextElement = getNext();
/* 672 */       return e;
/*     */     }
/*     */ 
/*     */     
/*     */     private void setBuffer(E[] buffer) {
/* 677 */       this.currentBuffer = buffer;
/* 678 */       this.mask = LinkedArrayQueueUtil.length((Object[])buffer) - 2;
/*     */     }
/*     */ 
/*     */     
/*     */     private E getNext() {
/* 683 */       while (this.nextIndex < this.pIndex) {
/*     */         
/* 685 */         long index = this.nextIndex++;
/* 686 */         E e = (E)UnsafeRefArrayAccess.lvRefElement((Object[])this.currentBuffer, UnsafeRefArrayAccess.calcCircularRefElementOffset(index, this.mask));
/*     */         
/* 688 */         if (e == null) {
/*     */           continue;
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 694 */         if (e != BaseMpscLinkedArrayQueue.JUMP)
/*     */         {
/* 696 */           return e;
/*     */         }
/*     */ 
/*     */         
/* 700 */         int nextBufferIndex = this.mask + 1;
/* 701 */         Object nextBuffer = UnsafeRefArrayAccess.lvRefElement((Object[])this.currentBuffer, 
/* 702 */             UnsafeRefArrayAccess.calcRefElementOffset(nextBufferIndex));
/*     */         
/* 704 */         if (nextBuffer == BaseMpscLinkedArrayQueue.BUFFER_CONSUMED || nextBuffer == null)
/*     */         {
/*     */           
/* 707 */           return null;
/*     */         }
/*     */         
/* 710 */         setBuffer((E[])nextBuffer);
/*     */         
/* 712 */         e = (E)UnsafeRefArrayAccess.lvRefElement((Object[])this.currentBuffer, UnsafeRefArrayAccess.calcCircularRefElementOffset(index, this.mask));
/*     */         
/* 714 */         if (e == null) {
/*     */           continue;
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 720 */         return e;
/*     */       } 
/*     */ 
/*     */       
/* 724 */       return null;
/*     */     }
/*     */   }
/*     */   
/*     */   private void resize(long oldMask, E[] oldBuffer, long pIndex, E e, MessagePassingQueue.Supplier<E> s) {
/*     */     E[] newBuffer;
/* 730 */     assert e == null || s != null;
/* 731 */     int newBufferLength = getNextBufferSize(oldBuffer);
/*     */ 
/*     */     
/*     */     try {
/* 735 */       newBuffer = (E[])UnsafeRefArrayAccess.allocateRefArray(newBufferLength);
/*     */     }
/* 737 */     catch (OutOfMemoryError oom) {
/*     */       
/* 739 */       assert lvProducerIndex() == pIndex + 1L;
/* 740 */       soProducerIndex(pIndex);
/* 741 */       throw oom;
/*     */     } 
/*     */     
/* 744 */     this.producerBuffer = newBuffer;
/* 745 */     int newMask = newBufferLength - 2 << 1;
/* 746 */     this.producerMask = newMask;
/*     */     
/* 748 */     long offsetInOld = LinkedArrayQueueUtil.modifiedCalcCircularRefElementOffset(pIndex, oldMask);
/* 749 */     long offsetInNew = LinkedArrayQueueUtil.modifiedCalcCircularRefElementOffset(pIndex, newMask);
/*     */     
/* 751 */     UnsafeRefArrayAccess.soRefElement((Object[])newBuffer, offsetInNew, (e == null) ? s.get() : e);
/* 752 */     UnsafeRefArrayAccess.soRefElement((Object[])oldBuffer, nextArrayOffset(oldMask), newBuffer);
/*     */ 
/*     */     
/* 755 */     long cIndex = lvConsumerIndex();
/* 756 */     long availableInQueue = availableInQueue(pIndex, cIndex);
/* 757 */     RangeUtil.checkPositive(availableInQueue, "availableInQueue");
/*     */ 
/*     */ 
/*     */     
/* 761 */     soProducerLimit(pIndex + Math.min(newMask, availableInQueue));
/*     */ 
/*     */     
/* 764 */     soProducerIndex(pIndex + 2L);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 769 */     UnsafeRefArrayAccess.soRefElement((Object[])oldBuffer, offsetInOld, JUMP);
/*     */   }
/*     */   
/*     */   protected abstract long availableInQueue(long paramLong1, long paramLong2);
/*     */   
/*     */   public abstract int capacity();
/*     */   
/*     */   protected abstract int getNextBufferSize(E[] paramArrayOfE);
/*     */   
/*     */   protected abstract long getCurrentBufferCapacity(long paramLong);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\shaded\org\jctools\queues\BaseMpscLinkedArrayQueue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */