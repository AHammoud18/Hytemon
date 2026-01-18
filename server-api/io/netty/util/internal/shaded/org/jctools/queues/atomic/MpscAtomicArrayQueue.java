/*     */ package io.netty.util.internal.shaded.org.jctools.queues.atomic;
/*     */ 
/*     */ import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue;
/*     */ import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueueUtil;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MpscAtomicArrayQueue<E>
/*     */   extends MpscAtomicArrayQueueL3Pad<E>
/*     */ {
/*     */   public MpscAtomicArrayQueue(int capacity) {
/* 344 */     super(capacity);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean offerIfBelowThreshold(E e, int threshold) {
/*     */     long pIndex;
/* 356 */     if (null == e) {
/* 357 */       throw new NullPointerException();
/*     */     }
/* 359 */     int mask = this.mask;
/* 360 */     long capacity = (mask + 1);
/* 361 */     long producerLimit = lvProducerLimit();
/*     */     
/*     */     do {
/* 364 */       pIndex = lvProducerIndex();
/* 365 */       long available = producerLimit - pIndex;
/* 366 */       long size = capacity - available;
/* 367 */       if (size < threshold)
/* 368 */         continue;  long cIndex = lvConsumerIndex();
/* 369 */       size = pIndex - cIndex;
/* 370 */       if (size >= threshold)
/*     */       {
/* 372 */         return false;
/*     */       }
/*     */       
/* 375 */       producerLimit = cIndex + capacity;
/*     */       
/* 377 */       soProducerLimit(producerLimit);
/*     */     
/*     */     }
/* 380 */     while (!casProducerIndex(pIndex, pIndex + 1L));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 386 */     int offset = AtomicQueueUtil.calcCircularRefElementOffset(pIndex, mask);
/* 387 */     AtomicQueueUtil.soRefElement(this.buffer, offset, e);
/*     */     
/* 389 */     return true;
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
/*     */   public boolean offer(E e) {
/*     */     long pIndex;
/* 404 */     if (null == e) {
/* 405 */       throw new NullPointerException();
/*     */     }
/*     */     
/* 408 */     int mask = this.mask;
/* 409 */     long producerLimit = lvProducerLimit();
/*     */     
/*     */     do {
/* 412 */       pIndex = lvProducerIndex();
/* 413 */       if (pIndex < producerLimit)
/* 414 */         continue;  long cIndex = lvConsumerIndex();
/* 415 */       producerLimit = cIndex + mask + 1L;
/* 416 */       if (pIndex >= producerLimit)
/*     */       {
/* 418 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 422 */       soProducerLimit(producerLimit);
/*     */     
/*     */     }
/* 425 */     while (!casProducerIndex(pIndex, pIndex + 1L));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 431 */     int offset = AtomicQueueUtil.calcCircularRefElementOffset(pIndex, mask);
/* 432 */     AtomicQueueUtil.soRefElement(this.buffer, offset, e);
/*     */     
/* 434 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int failFastOffer(E e) {
/* 444 */     if (null == e) {
/* 445 */       throw new NullPointerException();
/*     */     }
/* 447 */     int mask = this.mask;
/* 448 */     long capacity = (mask + 1);
/* 449 */     long pIndex = lvProducerIndex();
/* 450 */     long producerLimit = lvProducerLimit();
/* 451 */     if (pIndex >= producerLimit) {
/* 452 */       long cIndex = lvConsumerIndex();
/* 453 */       producerLimit = cIndex + capacity;
/* 454 */       if (pIndex >= producerLimit)
/*     */       {
/* 456 */         return 1;
/*     */       }
/*     */       
/* 459 */       soProducerLimit(producerLimit);
/*     */     } 
/*     */ 
/*     */     
/* 463 */     if (!casProducerIndex(pIndex, pIndex + 1L))
/*     */     {
/* 465 */       return -1;
/*     */     }
/*     */     
/* 468 */     int offset = AtomicQueueUtil.calcCircularRefElementOffset(pIndex, mask);
/* 469 */     AtomicQueueUtil.soRefElement(this.buffer, offset, e);
/*     */     
/* 471 */     return 0;
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
/*     */   public E poll() {
/* 485 */     long cIndex = lpConsumerIndex();
/* 486 */     int offset = AtomicQueueUtil.calcCircularRefElementOffset(cIndex, this.mask);
/*     */     
/* 488 */     AtomicReferenceArray<E> buffer = this.buffer;
/*     */     
/* 490 */     E e = AtomicQueueUtil.lvRefElement(buffer, offset);
/* 491 */     if (null == e)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 497 */       if (cIndex != lvProducerIndex()) {
/*     */         do {
/* 499 */           e = AtomicQueueUtil.lvRefElement(buffer, offset);
/* 500 */         } while (e == null);
/*     */       } else {
/* 502 */         return null;
/*     */       } 
/*     */     }
/* 505 */     AtomicQueueUtil.spRefElement(buffer, offset, null);
/* 506 */     soConsumerIndex(cIndex + 1L);
/* 507 */     return e;
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
/*     */   public E peek() {
/* 522 */     AtomicReferenceArray<E> buffer = this.buffer;
/* 523 */     long cIndex = lpConsumerIndex();
/* 524 */     int offset = AtomicQueueUtil.calcCircularRefElementOffset(cIndex, this.mask);
/* 525 */     E e = AtomicQueueUtil.lvRefElement(buffer, offset);
/* 526 */     if (null == e)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 532 */       if (cIndex != lvProducerIndex()) {
/*     */         do {
/* 534 */           e = AtomicQueueUtil.lvRefElement(buffer, offset);
/* 535 */         } while (e == null);
/*     */       } else {
/* 537 */         return null;
/*     */       } 
/*     */     }
/* 540 */     return e;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean relaxedOffer(E e) {
/* 545 */     return offer(e);
/*     */   }
/*     */ 
/*     */   
/*     */   public E relaxedPoll() {
/* 550 */     AtomicReferenceArray<E> buffer = this.buffer;
/* 551 */     long cIndex = lpConsumerIndex();
/* 552 */     int offset = AtomicQueueUtil.calcCircularRefElementOffset(cIndex, this.mask);
/*     */     
/* 554 */     E e = AtomicQueueUtil.lvRefElement(buffer, offset);
/* 555 */     if (null == e) {
/* 556 */       return null;
/*     */     }
/* 558 */     AtomicQueueUtil.spRefElement(buffer, offset, null);
/* 559 */     soConsumerIndex(cIndex + 1L);
/* 560 */     return e;
/*     */   }
/*     */ 
/*     */   
/*     */   public E relaxedPeek() {
/* 565 */     AtomicReferenceArray<E> buffer = this.buffer;
/* 566 */     int mask = this.mask;
/* 567 */     long cIndex = lpConsumerIndex();
/* 568 */     return AtomicQueueUtil.lvRefElement(buffer, AtomicQueueUtil.calcCircularRefElementOffset(cIndex, mask));
/*     */   }
/*     */ 
/*     */   
/*     */   public int drain(MessagePassingQueue.Consumer<E> c, int limit) {
/* 573 */     if (null == c)
/* 574 */       throw new IllegalArgumentException("c is null"); 
/* 575 */     if (limit < 0)
/* 576 */       throw new IllegalArgumentException("limit is negative: " + limit); 
/* 577 */     if (limit == 0)
/* 578 */       return 0; 
/* 579 */     AtomicReferenceArray<E> buffer = this.buffer;
/* 580 */     int mask = this.mask;
/* 581 */     long cIndex = lpConsumerIndex();
/* 582 */     for (int i = 0; i < limit; i++) {
/* 583 */       long index = cIndex + i;
/* 584 */       int offset = AtomicQueueUtil.calcCircularRefElementOffset(index, mask);
/* 585 */       E e = AtomicQueueUtil.lvRefElement(buffer, offset);
/* 586 */       if (null == e) {
/* 587 */         return i;
/*     */       }
/* 589 */       AtomicQueueUtil.spRefElement(buffer, offset, null);
/*     */       
/* 591 */       soConsumerIndex(index + 1L);
/* 592 */       c.accept(e);
/*     */     } 
/* 594 */     return limit;
/*     */   }
/*     */   public int fill(MessagePassingQueue.Supplier<E> s, int limit) {
/*     */     long pIndex;
/*     */     int actualLimit;
/* 599 */     if (null == s)
/* 600 */       throw new IllegalArgumentException("supplier is null"); 
/* 601 */     if (limit < 0)
/* 602 */       throw new IllegalArgumentException("limit is negative:" + limit); 
/* 603 */     if (limit == 0)
/* 604 */       return 0; 
/* 605 */     int mask = this.mask;
/* 606 */     long capacity = (mask + 1);
/* 607 */     long producerLimit = lvProducerLimit();
/*     */ 
/*     */     
/*     */     do {
/* 611 */       pIndex = lvProducerIndex();
/* 612 */       long available = producerLimit - pIndex;
/* 613 */       if (available <= 0L) {
/* 614 */         long cIndex = lvConsumerIndex();
/* 615 */         producerLimit = cIndex + capacity;
/* 616 */         available = producerLimit - pIndex;
/* 617 */         if (available <= 0L)
/*     */         {
/* 619 */           return 0;
/*     */         }
/*     */         
/* 622 */         soProducerLimit(producerLimit);
/*     */       } 
/*     */       
/* 625 */       actualLimit = Math.min((int)available, limit);
/* 626 */     } while (!casProducerIndex(pIndex, pIndex + actualLimit));
/*     */     
/* 628 */     AtomicReferenceArray<E> buffer = this.buffer;
/* 629 */     for (int i = 0; i < actualLimit; i++) {
/*     */       
/* 631 */       int offset = AtomicQueueUtil.calcCircularRefElementOffset(pIndex + i, mask);
/* 632 */       AtomicQueueUtil.soRefElement(buffer, offset, s.get());
/*     */     } 
/* 634 */     return actualLimit;
/*     */   }
/*     */ 
/*     */   
/*     */   public int drain(MessagePassingQueue.Consumer<E> c) {
/* 639 */     return drain(c, capacity());
/*     */   }
/*     */ 
/*     */   
/*     */   public int fill(MessagePassingQueue.Supplier<E> s) {
/* 644 */     return MessagePassingQueueUtil.fillBounded(this, s);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drain(MessagePassingQueue.Consumer<E> c, MessagePassingQueue.WaitStrategy w, MessagePassingQueue.ExitCondition exit) {
/* 649 */     MessagePassingQueueUtil.drain(this, c, w, exit);
/*     */   }
/*     */ 
/*     */   
/*     */   public void fill(MessagePassingQueue.Supplier<E> s, MessagePassingQueue.WaitStrategy wait, MessagePassingQueue.ExitCondition exit) {
/* 654 */     MessagePassingQueueUtil.fill(this, s, wait, exit);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public int weakOffer(E e) {
/* 662 */     return failFastOffer(e);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\shaded\org\jctools\queues\atomic\MpscAtomicArrayQueue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */