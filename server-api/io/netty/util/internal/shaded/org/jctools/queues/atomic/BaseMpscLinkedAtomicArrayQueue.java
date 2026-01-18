/*     */ package io.netty.util.internal.shaded.org.jctools.queues.atomic;
/*     */ 
/*     */ import io.netty.util.internal.shaded.org.jctools.queues.IndexedQueueSizeUtil;
/*     */ import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue;
/*     */ import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueueUtil;
/*     */ import io.netty.util.internal.shaded.org.jctools.queues.QueueProgressIndicators;
/*     */ import io.netty.util.internal.shaded.org.jctools.util.PortableJvmInfo;
/*     */ import io.netty.util.internal.shaded.org.jctools.util.Pow2;
/*     */ import io.netty.util.internal.shaded.org.jctools.util.RangeUtil;
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
/*     */ abstract class BaseMpscLinkedAtomicArrayQueue<E>
/*     */   extends BaseMpscLinkedAtomicArrayQueueColdProducerFields<E>
/*     */   implements MessagePassingQueue<E>, QueueProgressIndicators
/*     */ {
/* 282 */   private static final Object JUMP = new Object();
/*     */   
/* 284 */   private static final Object BUFFER_CONSUMED = new Object();
/*     */ 
/*     */   
/*     */   private static final int CONTINUE_TO_P_INDEX_CAS = 0;
/*     */ 
/*     */   
/*     */   private static final int RETRY = 1;
/*     */ 
/*     */   
/*     */   private static final int QUEUE_FULL = 2;
/*     */   
/*     */   private static final int QUEUE_RESIZE = 3;
/*     */ 
/*     */   
/*     */   public BaseMpscLinkedAtomicArrayQueue(int initialCapacity) {
/* 299 */     RangeUtil.checkGreaterThanOrEqual(initialCapacity, 2, "initialCapacity");
/* 300 */     int p2capacity = Pow2.roundToPowerOfTwo(initialCapacity);
/*     */     
/* 302 */     long mask = (p2capacity - 1 << 1);
/*     */     
/* 304 */     AtomicReferenceArray<E> buffer = AtomicQueueUtil.allocateRefArray(p2capacity + 1);
/* 305 */     this.producerBuffer = buffer;
/* 306 */     this.producerMask = mask;
/* 307 */     this.consumerBuffer = buffer;
/* 308 */     this.consumerMask = mask;
/*     */     
/* 310 */     soProducerLimit(mask);
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 315 */     return IndexedQueueSizeUtil.size(this, 2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 324 */     return ((lvConsumerIndex() - lvProducerIndex()) / 2L == 0L);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 329 */     return getClass().getName();
/*     */   } public boolean offer(E e) {
/*     */     long mask;
/*     */     AtomicReferenceArray<E> buffer;
/*     */     long pIndex;
/* 334 */     if (null == e) {
/* 335 */       throw new NullPointerException();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     while (true) {
/* 341 */       long producerLimit = lvProducerLimit();
/* 342 */       pIndex = lvProducerIndex();
/*     */       
/* 344 */       if ((pIndex & 0x1L) == 1L) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */       
/* 349 */       mask = this.producerMask;
/* 350 */       buffer = this.producerBuffer;
/*     */ 
/*     */       
/* 353 */       if (producerLimit <= pIndex) {
/* 354 */         int result = offerSlowPath(mask, pIndex, producerLimit);
/* 355 */         switch (result) {
/*     */           case 1:
/*     */             continue;
/*     */ 
/*     */           
/*     */           case 2:
/* 361 */             return false;
/*     */           case 3:
/* 363 */             resize(mask, buffer, pIndex, e, (MessagePassingQueue.Supplier<E>)null);
/* 364 */             return true;
/*     */         } 
/*     */       } 
/* 367 */       if (casProducerIndex(pIndex, pIndex + 2L)) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */     
/* 372 */     int offset = AtomicQueueUtil.modifiedCalcCircularRefElementOffset(pIndex, mask);
/*     */     
/* 374 */     AtomicQueueUtil.soRefElement(buffer, offset, e);
/* 375 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public E poll() {
/* 386 */     AtomicReferenceArray<E> buffer = this.consumerBuffer;
/* 387 */     long cIndex = lpConsumerIndex();
/* 388 */     long mask = this.consumerMask;
/* 389 */     int offset = AtomicQueueUtil.modifiedCalcCircularRefElementOffset(cIndex, mask);
/* 390 */     Object e = AtomicQueueUtil.lvRefElement(buffer, offset);
/* 391 */     if (e == null) {
/* 392 */       long pIndex = lvProducerIndex();
/*     */       
/* 394 */       if ((cIndex - pIndex) / 2L == 0L) {
/* 395 */         return null;
/*     */       }
/*     */ 
/*     */       
/*     */       do {
/* 400 */         e = AtomicQueueUtil.lvRefElement(buffer, offset);
/* 401 */       } while (e == null);
/*     */     } 
/* 403 */     if (e == JUMP) {
/* 404 */       AtomicReferenceArray<E> nextBuffer = nextBuffer(buffer, mask);
/* 405 */       return newBufferPoll(nextBuffer, cIndex);
/*     */     } 
/*     */     
/* 408 */     AtomicQueueUtil.soRefElement(buffer, offset, null);
/*     */     
/* 410 */     soConsumerIndex(cIndex + 2L);
/* 411 */     return (E)e;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public E peek() {
/* 422 */     AtomicReferenceArray<E> buffer = this.consumerBuffer;
/* 423 */     long cIndex = lpConsumerIndex();
/* 424 */     long mask = this.consumerMask;
/* 425 */     int offset = AtomicQueueUtil.modifiedCalcCircularRefElementOffset(cIndex, mask);
/* 426 */     Object e = AtomicQueueUtil.lvRefElement(buffer, offset);
/* 427 */     if (e == null) {
/* 428 */       long pIndex = lvProducerIndex();
/*     */       
/* 430 */       if ((cIndex - pIndex) / 2L == 0L) {
/* 431 */         return null;
/*     */       }
/*     */ 
/*     */       
/*     */       do {
/* 436 */         e = AtomicQueueUtil.lvRefElement(buffer, offset);
/* 437 */       } while (e == null);
/*     */     } 
/* 439 */     if (e == JUMP) {
/* 440 */       return newBufferPeek(nextBuffer(buffer, mask), cIndex);
/*     */     }
/* 442 */     return (E)e;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int offerSlowPath(long mask, long pIndex, long producerLimit) {
/* 449 */     long cIndex = lvConsumerIndex();
/* 450 */     long bufferCapacity = getCurrentBufferCapacity(mask);
/* 451 */     if (cIndex + bufferCapacity > pIndex) {
/* 452 */       if (!casProducerLimit(producerLimit, cIndex + bufferCapacity))
/*     */       {
/* 454 */         return 1;
/*     */       }
/*     */       
/* 457 */       return 0;
/*     */     } 
/*     */     
/* 460 */     if (availableInQueue(pIndex, cIndex) <= 0L)
/*     */     {
/* 462 */       return 2;
/*     */     }
/* 464 */     if (casProducerIndex(pIndex, pIndex + 1L))
/*     */     {
/* 466 */       return 3;
/*     */     }
/*     */     
/* 469 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private AtomicReferenceArray<E> nextBuffer(AtomicReferenceArray<E> buffer, long mask) {
/* 480 */     int offset = nextArrayOffset(mask);
/* 481 */     AtomicReferenceArray<E> nextBuffer = AtomicQueueUtil.<AtomicReferenceArray<E>>lvRefElement((AtomicReferenceArray)buffer, offset);
/* 482 */     this.consumerBuffer = nextBuffer;
/* 483 */     this.consumerMask = (AtomicQueueUtil.length(nextBuffer) - 2 << 1);
/* 484 */     AtomicQueueUtil.soRefElement(buffer, offset, BUFFER_CONSUMED);
/* 485 */     return nextBuffer;
/*     */   }
/*     */   
/*     */   private static int nextArrayOffset(long mask) {
/* 489 */     return AtomicQueueUtil.modifiedCalcCircularRefElementOffset(mask + 2L, Long.MAX_VALUE);
/*     */   }
/*     */   
/*     */   private E newBufferPoll(AtomicReferenceArray<E> nextBuffer, long cIndex) {
/* 493 */     int offset = AtomicQueueUtil.modifiedCalcCircularRefElementOffset(cIndex, this.consumerMask);
/* 494 */     E n = AtomicQueueUtil.lvRefElement(nextBuffer, offset);
/* 495 */     if (n == null) {
/* 496 */       throw new IllegalStateException("new buffer must have at least one element");
/*     */     }
/* 498 */     AtomicQueueUtil.soRefElement(nextBuffer, offset, null);
/* 499 */     soConsumerIndex(cIndex + 2L);
/* 500 */     return n;
/*     */   }
/*     */   
/*     */   private E newBufferPeek(AtomicReferenceArray<E> nextBuffer, long cIndex) {
/* 504 */     int offset = AtomicQueueUtil.modifiedCalcCircularRefElementOffset(cIndex, this.consumerMask);
/* 505 */     E n = AtomicQueueUtil.lvRefElement(nextBuffer, offset);
/* 506 */     if (null == n) {
/* 507 */       throw new IllegalStateException("new buffer must have at least one element");
/*     */     }
/* 509 */     return n;
/*     */   }
/*     */ 
/*     */   
/*     */   public long currentProducerIndex() {
/* 514 */     return lvProducerIndex() / 2L;
/*     */   }
/*     */ 
/*     */   
/*     */   public long currentConsumerIndex() {
/* 519 */     return lvConsumerIndex() / 2L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean relaxedOffer(E e) {
/* 527 */     return offer(e);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public E relaxedPoll() {
/* 533 */     AtomicReferenceArray<E> buffer = this.consumerBuffer;
/* 534 */     long cIndex = lpConsumerIndex();
/* 535 */     long mask = this.consumerMask;
/* 536 */     int offset = AtomicQueueUtil.modifiedCalcCircularRefElementOffset(cIndex, mask);
/* 537 */     Object e = AtomicQueueUtil.lvRefElement(buffer, offset);
/* 538 */     if (e == null) {
/* 539 */       return null;
/*     */     }
/* 541 */     if (e == JUMP) {
/* 542 */       AtomicReferenceArray<E> nextBuffer = nextBuffer(buffer, mask);
/* 543 */       return newBufferPoll(nextBuffer, cIndex);
/*     */     } 
/* 545 */     AtomicQueueUtil.soRefElement(buffer, offset, null);
/* 546 */     soConsumerIndex(cIndex + 2L);
/* 547 */     return (E)e;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public E relaxedPeek() {
/* 553 */     AtomicReferenceArray<E> buffer = this.consumerBuffer;
/* 554 */     long cIndex = lpConsumerIndex();
/* 555 */     long mask = this.consumerMask;
/* 556 */     int offset = AtomicQueueUtil.modifiedCalcCircularRefElementOffset(cIndex, mask);
/* 557 */     Object e = AtomicQueueUtil.lvRefElement(buffer, offset);
/* 558 */     if (e == JUMP) {
/* 559 */       return newBufferPeek(nextBuffer(buffer, mask), cIndex);
/*     */     }
/* 561 */     return (E)e;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int fill(MessagePassingQueue.Supplier<E> s) {
/* 567 */     long result = 0L;
/* 568 */     int capacity = capacity();
/*     */     while (true) {
/* 570 */       int filled = fill(s, PortableJvmInfo.RECOMENDED_OFFER_BATCH);
/* 571 */       if (filled == 0) {
/* 572 */         return (int)result;
/*     */       }
/* 574 */       result += filled;
/* 575 */       if (result > capacity)
/* 576 */         return (int)result; 
/*     */     } 
/*     */   }
/*     */   
/*     */   public int fill(MessagePassingQueue.Supplier<E> s, int limit) {
/* 581 */     if (null == s)
/* 582 */       throw new IllegalArgumentException("supplier is null"); 
/* 583 */     if (limit < 0)
/* 584 */       throw new IllegalArgumentException("limit is negative:" + limit); 
/* 585 */     if (limit == 0) {
/* 586 */       return 0;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     while (true) {
/* 592 */       long producerLimit = lvProducerLimit();
/* 593 */       long pIndex = lvProducerIndex();
/*     */       
/* 595 */       if ((pIndex & 0x1L) == 1L) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 602 */       long mask = this.producerMask;
/* 603 */       AtomicReferenceArray<E> buffer = this.producerBuffer;
/*     */ 
/*     */ 
/*     */       
/* 607 */       long batchIndex = Math.min(producerLimit, pIndex + 2L * limit);
/* 608 */       if (pIndex >= producerLimit) {
/* 609 */         int result = offerSlowPath(mask, pIndex, producerLimit);
/* 610 */         switch (result) {
/*     */           case 0:
/*     */           case 1:
/*     */             continue;
/*     */           
/*     */           case 2:
/* 616 */             return 0;
/*     */           case 3:
/* 618 */             resize(mask, buffer, pIndex, (E)null, s);
/* 619 */             return 1;
/*     */         } 
/*     */       
/*     */       } 
/* 623 */       if (casProducerIndex(pIndex, batchIndex)) {
/* 624 */         int claimedSlots = (int)((batchIndex - pIndex) / 2L);
/*     */ 
/*     */ 
/*     */         
/* 628 */         for (int i = 0; i < claimedSlots; i++) {
/* 629 */           int offset = AtomicQueueUtil.modifiedCalcCircularRefElementOffset(pIndex + 2L * i, mask);
/* 630 */           AtomicQueueUtil.soRefElement(buffer, offset, s.get());
/*     */         } 
/* 632 */         return claimedSlots;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   public void fill(MessagePassingQueue.Supplier<E> s, MessagePassingQueue.WaitStrategy wait, MessagePassingQueue.ExitCondition exit) {
/* 637 */     MessagePassingQueueUtil.fill(this, s, wait, exit);
/*     */   }
/*     */ 
/*     */   
/*     */   public int drain(MessagePassingQueue.Consumer<E> c) {
/* 642 */     return drain(c, capacity());
/*     */   }
/*     */ 
/*     */   
/*     */   public int drain(MessagePassingQueue.Consumer<E> c, int limit) {
/* 647 */     return MessagePassingQueueUtil.drain(this, c, limit);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drain(MessagePassingQueue.Consumer<E> c, MessagePassingQueue.WaitStrategy wait, MessagePassingQueue.ExitCondition exit) {
/* 652 */     MessagePassingQueueUtil.drain(this, c, wait, exit);
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
/* 667 */     return new WeakIterator<E>(this.consumerBuffer, lvConsumerIndex(), lvProducerIndex());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class WeakIterator<E>
/*     */     implements Iterator<E>
/*     */   {
/*     */     private final long pIndex;
/*     */     
/*     */     private long nextIndex;
/*     */     
/*     */     private E nextElement;
/*     */     
/*     */     private AtomicReferenceArray<E> currentBuffer;
/*     */     
/*     */     private int mask;
/*     */ 
/*     */     
/*     */     WeakIterator(AtomicReferenceArray<E> currentBuffer, long cIndex, long pIndex) {
/* 687 */       this.pIndex = pIndex >> 1L;
/* 688 */       this.nextIndex = cIndex >> 1L;
/* 689 */       setBuffer(currentBuffer);
/* 690 */       this.nextElement = getNext();
/*     */     }
/*     */ 
/*     */     
/*     */     public void remove() {
/* 695 */       throw new UnsupportedOperationException("remove");
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 700 */       return (this.nextElement != null);
/*     */     }
/*     */ 
/*     */     
/*     */     public E next() {
/* 705 */       E e = this.nextElement;
/* 706 */       if (e == null) {
/* 707 */         throw new NoSuchElementException();
/*     */       }
/* 709 */       this.nextElement = getNext();
/* 710 */       return e;
/*     */     }
/*     */     
/*     */     private void setBuffer(AtomicReferenceArray<E> buffer) {
/* 714 */       this.currentBuffer = buffer;
/* 715 */       this.mask = AtomicQueueUtil.length(buffer) - 2;
/*     */     }
/*     */     
/*     */     private E getNext() {
/* 719 */       while (this.nextIndex < this.pIndex) {
/* 720 */         long index = this.nextIndex++;
/* 721 */         E e = AtomicQueueUtil.lvRefElement(this.currentBuffer, AtomicQueueUtil.calcCircularRefElementOffset(index, this.mask));
/*     */         
/* 723 */         if (e == null) {
/*     */           continue;
/*     */         }
/*     */         
/* 727 */         if (e != BaseMpscLinkedAtomicArrayQueue.JUMP) {
/* 728 */           return e;
/*     */         }
/*     */         
/* 731 */         int nextBufferIndex = this.mask + 1;
/* 732 */         Object nextBuffer = AtomicQueueUtil.lvRefElement(this.currentBuffer, AtomicQueueUtil.calcRefElementOffset(nextBufferIndex));
/* 733 */         if (nextBuffer == BaseMpscLinkedAtomicArrayQueue.BUFFER_CONSUMED || nextBuffer == null)
/*     */         {
/* 735 */           return null;
/*     */         }
/* 737 */         setBuffer((AtomicReferenceArray<E>)nextBuffer);
/*     */         
/* 739 */         e = AtomicQueueUtil.lvRefElement(this.currentBuffer, AtomicQueueUtil.calcCircularRefElementOffset(index, this.mask));
/*     */         
/* 741 */         if (e == null) {
/*     */           continue;
/*     */         }
/* 744 */         return e;
/*     */       } 
/*     */       
/* 747 */       return null;
/*     */     } }
/*     */   
/*     */   private void resize(long oldMask, AtomicReferenceArray<E> oldBuffer, long pIndex, E e, MessagePassingQueue.Supplier<E> s) {
/*     */     AtomicReferenceArray<E> newBuffer;
/* 752 */     assert e == null || s != null;
/* 753 */     int newBufferLength = getNextBufferSize(oldBuffer);
/*     */     
/*     */     try {
/* 756 */       newBuffer = AtomicQueueUtil.allocateRefArray(newBufferLength);
/* 757 */     } catch (OutOfMemoryError oom) {
/* 758 */       assert lvProducerIndex() == pIndex + 1L;
/* 759 */       soProducerIndex(pIndex);
/* 760 */       throw oom;
/*     */     } 
/* 762 */     this.producerBuffer = newBuffer;
/* 763 */     int newMask = newBufferLength - 2 << 1;
/* 764 */     this.producerMask = newMask;
/* 765 */     int offsetInOld = AtomicQueueUtil.modifiedCalcCircularRefElementOffset(pIndex, oldMask);
/* 766 */     int offsetInNew = AtomicQueueUtil.modifiedCalcCircularRefElementOffset(pIndex, newMask);
/*     */     
/* 768 */     AtomicQueueUtil.soRefElement(newBuffer, offsetInNew, (e == null) ? s.get() : e);
/*     */     
/* 770 */     AtomicQueueUtil.soRefElement(oldBuffer, nextArrayOffset(oldMask), newBuffer);
/*     */     
/* 772 */     long cIndex = lvConsumerIndex();
/* 773 */     long availableInQueue = availableInQueue(pIndex, cIndex);
/* 774 */     RangeUtil.checkPositive(availableInQueue, "availableInQueue");
/*     */ 
/*     */     
/* 777 */     soProducerLimit(pIndex + Math.min(newMask, availableInQueue));
/*     */     
/* 779 */     soProducerIndex(pIndex + 2L);
/*     */ 
/*     */     
/* 782 */     AtomicQueueUtil.soRefElement(oldBuffer, offsetInOld, JUMP);
/*     */   }
/*     */   
/*     */   protected abstract long availableInQueue(long paramLong1, long paramLong2);
/*     */   
/*     */   public abstract int capacity();
/*     */   
/*     */   protected abstract int getNextBufferSize(AtomicReferenceArray<E> paramAtomicReferenceArray);
/*     */   
/*     */   protected abstract long getCurrentBufferCapacity(long paramLong);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\shaded\org\jctools\queues\atomic\BaseMpscLinkedAtomicArrayQueue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */