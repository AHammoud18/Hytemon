/*     */ package io.netty.util.internal.shaded.org.jctools.queues.atomic;
/*     */ 
/*     */ import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue;
/*     */ import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueueUtil;
/*     */ import io.netty.util.internal.shaded.org.jctools.util.RangeUtil;
/*     */ import java.util.concurrent.atomic.AtomicLongArray;
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
/*     */ public class MpmcAtomicArrayQueue<E>
/*     */   extends MpmcAtomicArrayQueueL3Pad<E>
/*     */ {
/* 271 */   public static final int MAX_LOOK_AHEAD_STEP = Integer.getInteger("jctools.mpmc.max.lookahead.step", 4096).intValue();
/*     */   
/*     */   private final int lookAheadStep;
/*     */   
/*     */   public MpmcAtomicArrayQueue(int capacity) {
/* 276 */     super(RangeUtil.checkGreaterThanOrEqual(capacity, 2, "capacity"));
/* 277 */     this.lookAheadStep = Math.max(2, Math.min(capacity() / 4, MAX_LOOK_AHEAD_STEP));
/*     */   } public boolean offer(E e) {
/*     */     long pIndex;
/*     */     int seqOffset;
/*     */     long seq;
/* 282 */     if (null == e) {
/* 283 */       throw new NullPointerException();
/*     */     }
/* 285 */     int mask = this.mask;
/* 286 */     long capacity = (mask + 1);
/* 287 */     AtomicLongArray sBuffer = this.sequenceBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 292 */     long cIndex = Long.MIN_VALUE;
/*     */     do {
/* 294 */       pIndex = lvProducerIndex();
/* 295 */       seqOffset = AtomicQueueUtil.calcCircularLongElementOffset(pIndex, mask);
/* 296 */       seq = AtomicQueueUtil.lvLongElement(sBuffer, seqOffset);
/*     */       
/* 298 */       if (seq >= pIndex)
/*     */         continue; 
/* 300 */       if (pIndex - capacity >= cIndex && pIndex - capacity >= (
/*     */         
/* 302 */         cIndex = lvConsumerIndex())) {
/* 303 */         return false;
/*     */       }
/*     */       
/* 306 */       seq = pIndex + 1L;
/*     */     
/*     */     }
/* 309 */     while (seq > pIndex || 
/*     */       
/* 311 */       !casProducerIndex(pIndex, pIndex + 1L));
/*     */     
/* 313 */     AtomicQueueUtil.spRefElement(this.buffer, AtomicQueueUtil.calcCircularRefElementOffset(pIndex, mask), e);
/*     */     
/* 315 */     AtomicQueueUtil.soLongElement(sBuffer, seqOffset, pIndex + 1L);
/* 316 */     return true;
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
/* 328 */     AtomicLongArray sBuffer = this.sequenceBuffer;
/* 329 */     int mask = this.mask;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 335 */     long pIndex = -1L;
/*     */     while (true) {
/* 337 */       long cIndex = lvConsumerIndex();
/* 338 */       int seqOffset = AtomicQueueUtil.calcCircularLongElementOffset(cIndex, mask);
/* 339 */       long seq = AtomicQueueUtil.lvLongElement(sBuffer, seqOffset);
/* 340 */       long expectedSeq = cIndex + 1L;
/* 341 */       if (seq < expectedSeq) {
/*     */         
/* 343 */         if (cIndex >= pIndex && cIndex == (
/*     */           
/* 345 */           pIndex = lvProducerIndex()))
/*     */         {
/* 347 */           return null;
/*     */         }
/*     */         
/* 350 */         seq = expectedSeq + 1L;
/*     */       } 
/*     */       
/* 353 */       if (seq <= expectedSeq && 
/*     */         
/* 355 */         casConsumerIndex(cIndex, cIndex + 1L)) {
/* 356 */         int offset = AtomicQueueUtil.calcCircularRefElementOffset(cIndex, mask);
/* 357 */         E e = AtomicQueueUtil.lpRefElement(this.buffer, offset);
/* 358 */         AtomicQueueUtil.spRefElement(this.buffer, offset, null);
/*     */         
/* 360 */         AtomicQueueUtil.soLongElement(sBuffer, seqOffset, cIndex + mask + 1L);
/* 361 */         return e;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public E peek() {
/* 367 */     AtomicLongArray sBuffer = this.sequenceBuffer;
/* 368 */     int mask = this.mask;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 374 */     long pIndex = -1L;
/*     */     
/*     */     while (true) {
/* 377 */       long cIndex = lvConsumerIndex();
/* 378 */       int seqOffset = AtomicQueueUtil.calcCircularLongElementOffset(cIndex, mask);
/* 379 */       long seq = AtomicQueueUtil.lvLongElement(sBuffer, seqOffset);
/* 380 */       long expectedSeq = cIndex + 1L;
/* 381 */       if (seq < expectedSeq) {
/*     */         
/* 383 */         if (cIndex >= pIndex && cIndex == (
/*     */           
/* 385 */           pIndex = lvProducerIndex()))
/*     */         {
/* 387 */           return null; }  continue;
/*     */       } 
/* 389 */       if (seq == expectedSeq) {
/* 390 */         int offset = AtomicQueueUtil.calcCircularRefElementOffset(cIndex, mask);
/* 391 */         E e = AtomicQueueUtil.lvRefElement(this.buffer, offset);
/* 392 */         if (lvConsumerIndex() == cIndex)
/* 393 */           return e; 
/*     */       } 
/*     */     } 
/*     */   } public boolean relaxedOffer(E e) {
/*     */     long pIndex;
/*     */     int seqOffset;
/*     */     long seq;
/* 400 */     if (null == e) {
/* 401 */       throw new NullPointerException();
/*     */     }
/* 403 */     int mask = this.mask;
/* 404 */     AtomicLongArray sBuffer = this.sequenceBuffer;
/*     */ 
/*     */ 
/*     */     
/*     */     do {
/* 409 */       pIndex = lvProducerIndex();
/* 410 */       seqOffset = AtomicQueueUtil.calcCircularLongElementOffset(pIndex, mask);
/* 411 */       seq = AtomicQueueUtil.lvLongElement(sBuffer, seqOffset);
/* 412 */       if (seq < pIndex)
/*     */       {
/* 414 */         return false;
/*     */       }
/* 416 */     } while (seq > pIndex || 
/*     */       
/* 418 */       !casProducerIndex(pIndex, pIndex + 1L));
/*     */     
/* 420 */     AtomicQueueUtil.spRefElement(this.buffer, AtomicQueueUtil.calcCircularRefElementOffset(pIndex, mask), e);
/* 421 */     AtomicQueueUtil.soLongElement(sBuffer, seqOffset, pIndex + 1L);
/* 422 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public E relaxedPoll() {
/* 427 */     AtomicLongArray sBuffer = this.sequenceBuffer;
/* 428 */     int mask = this.mask;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     while (true) {
/* 434 */       long cIndex = lvConsumerIndex();
/* 435 */       int seqOffset = AtomicQueueUtil.calcCircularLongElementOffset(cIndex, mask);
/* 436 */       long seq = AtomicQueueUtil.lvLongElement(sBuffer, seqOffset);
/* 437 */       long expectedSeq = cIndex + 1L;
/* 438 */       if (seq < expectedSeq) {
/* 439 */         return null;
/*     */       }
/* 441 */       if (seq <= expectedSeq && 
/*     */         
/* 443 */         casConsumerIndex(cIndex, cIndex + 1L)) {
/* 444 */         int offset = AtomicQueueUtil.calcCircularRefElementOffset(cIndex, mask);
/* 445 */         E e = AtomicQueueUtil.lpRefElement(this.buffer, offset);
/* 446 */         AtomicQueueUtil.spRefElement(this.buffer, offset, null);
/* 447 */         AtomicQueueUtil.soLongElement(sBuffer, seqOffset, cIndex + mask + 1L);
/* 448 */         return e;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public E relaxedPeek() {
/* 454 */     AtomicLongArray sBuffer = this.sequenceBuffer;
/* 455 */     int mask = this.mask;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     while (true) {
/* 462 */       long cIndex = lvConsumerIndex();
/* 463 */       int seqOffset = AtomicQueueUtil.calcCircularLongElementOffset(cIndex, mask);
/* 464 */       long seq = AtomicQueueUtil.lvLongElement(sBuffer, seqOffset);
/* 465 */       long expectedSeq = cIndex + 1L;
/* 466 */       if (seq < expectedSeq)
/* 467 */         return null; 
/* 468 */       if (seq == expectedSeq) {
/* 469 */         int offset = AtomicQueueUtil.calcCircularRefElementOffset(cIndex, mask);
/* 470 */         E e = AtomicQueueUtil.lvRefElement(this.buffer, offset);
/* 471 */         if (lvConsumerIndex() == cIndex) {
/* 472 */           return e;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public int drain(MessagePassingQueue.Consumer<E> c, int limit) {
/* 479 */     if (null == c)
/* 480 */       throw new IllegalArgumentException("c is null"); 
/* 481 */     if (limit < 0)
/* 482 */       throw new IllegalArgumentException("limit is negative: " + limit); 
/* 483 */     if (limit == 0)
/* 484 */       return 0; 
/* 485 */     AtomicLongArray sBuffer = this.sequenceBuffer;
/* 486 */     int mask = this.mask;
/* 487 */     AtomicReferenceArray<E> buffer = this.buffer;
/* 488 */     int maxLookAheadStep = Math.min(this.lookAheadStep, limit);
/* 489 */     int consumed = 0;
/* 490 */     while (consumed < limit) {
/* 491 */       int remaining = limit - consumed;
/* 492 */       int lookAheadStep = Math.min(remaining, maxLookAheadStep);
/* 493 */       long cIndex = lvConsumerIndex();
/* 494 */       long lookAheadIndex = cIndex + lookAheadStep - 1L;
/* 495 */       int lookAheadSeqOffset = AtomicQueueUtil.calcCircularLongElementOffset(lookAheadIndex, mask);
/* 496 */       long lookAheadSeq = AtomicQueueUtil.lvLongElement(sBuffer, lookAheadSeqOffset);
/* 497 */       long expectedLookAheadSeq = lookAheadIndex + 1L;
/* 498 */       if (lookAheadSeq == expectedLookAheadSeq && casConsumerIndex(cIndex, expectedLookAheadSeq)) {
/* 499 */         for (int i = 0; i < lookAheadStep; i++) {
/* 500 */           long index = cIndex + i;
/* 501 */           int seqOffset = AtomicQueueUtil.calcCircularLongElementOffset(index, mask);
/* 502 */           int offset = AtomicQueueUtil.calcCircularRefElementOffset(index, mask);
/* 503 */           long expectedSeq = index + 1L;
/* 504 */           while (AtomicQueueUtil.lvLongElement(sBuffer, seqOffset) != expectedSeq);
/*     */           
/* 506 */           E e = AtomicQueueUtil.lpRefElement(buffer, offset);
/* 507 */           AtomicQueueUtil.spRefElement(buffer, offset, null);
/* 508 */           AtomicQueueUtil.soLongElement(sBuffer, seqOffset, index + mask + 1L);
/* 509 */           c.accept(e);
/*     */         } 
/* 511 */         consumed += lookAheadStep; continue;
/*     */       } 
/* 513 */       if (lookAheadSeq < expectedLookAheadSeq && 
/* 514 */         notAvailable(cIndex, mask, sBuffer, cIndex + 1L)) {
/* 515 */         return consumed;
/*     */       }
/*     */       
/* 518 */       return consumed + drainOneByOne(c, remaining);
/*     */     } 
/*     */     
/* 521 */     return limit;
/*     */   }
/*     */   
/*     */   private int drainOneByOne(MessagePassingQueue.Consumer<E> c, int limit) {
/* 525 */     AtomicLongArray sBuffer = this.sequenceBuffer;
/* 526 */     int mask = this.mask;
/* 527 */     AtomicReferenceArray<E> buffer = this.buffer;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 532 */     for (int i = 0; i < limit; ) {
/*     */       while (true) {
/* 534 */         long cIndex = lvConsumerIndex();
/* 535 */         int seqOffset = AtomicQueueUtil.calcCircularLongElementOffset(cIndex, mask);
/* 536 */         long seq = AtomicQueueUtil.lvLongElement(sBuffer, seqOffset);
/* 537 */         long expectedSeq = cIndex + 1L;
/* 538 */         if (seq < expectedSeq) {
/* 539 */           return i;
/*     */         }
/* 541 */         if (seq <= expectedSeq && 
/*     */           
/* 543 */           casConsumerIndex(cIndex, cIndex + 1L))
/* 544 */         { int offset = AtomicQueueUtil.calcCircularRefElementOffset(cIndex, mask);
/* 545 */           E e = AtomicQueueUtil.lpRefElement(buffer, offset);
/* 546 */           AtomicQueueUtil.spRefElement(buffer, offset, null);
/* 547 */           AtomicQueueUtil.soLongElement(sBuffer, seqOffset, cIndex + mask + 1L);
/* 548 */           c.accept(e); break; } 
/*     */       }  i++;
/* 550 */     }  return limit;
/*     */   }
/*     */ 
/*     */   
/*     */   public int fill(MessagePassingQueue.Supplier<E> s, int limit) {
/* 555 */     if (null == s)
/* 556 */       throw new IllegalArgumentException("supplier is null"); 
/* 557 */     if (limit < 0)
/* 558 */       throw new IllegalArgumentException("limit is negative:" + limit); 
/* 559 */     if (limit == 0)
/* 560 */       return 0; 
/* 561 */     AtomicLongArray sBuffer = this.sequenceBuffer;
/* 562 */     int mask = this.mask;
/* 563 */     AtomicReferenceArray<E> buffer = this.buffer;
/* 564 */     int maxLookAheadStep = Math.min(this.lookAheadStep, limit);
/* 565 */     int produced = 0;
/* 566 */     while (produced < limit) {
/* 567 */       int remaining = limit - produced;
/* 568 */       int lookAheadStep = Math.min(remaining, maxLookAheadStep);
/* 569 */       long pIndex = lvProducerIndex();
/* 570 */       long lookAheadIndex = pIndex + lookAheadStep - 1L;
/* 571 */       int lookAheadSeqOffset = AtomicQueueUtil.calcCircularLongElementOffset(lookAheadIndex, mask);
/* 572 */       long lookAheadSeq = AtomicQueueUtil.lvLongElement(sBuffer, lookAheadSeqOffset);
/* 573 */       long expectedLookAheadSeq = lookAheadIndex;
/* 574 */       if (lookAheadSeq == expectedLookAheadSeq && casProducerIndex(pIndex, expectedLookAheadSeq + 1L)) {
/* 575 */         for (int i = 0; i < lookAheadStep; i++) {
/* 576 */           long index = pIndex + i;
/* 577 */           int seqOffset = AtomicQueueUtil.calcCircularLongElementOffset(index, mask);
/* 578 */           int offset = AtomicQueueUtil.calcCircularRefElementOffset(index, mask);
/* 579 */           while (AtomicQueueUtil.lvLongElement(sBuffer, seqOffset) != index);
/*     */ 
/*     */           
/* 582 */           AtomicQueueUtil.soRefElement(buffer, offset, s.get());
/* 583 */           AtomicQueueUtil.soLongElement(sBuffer, seqOffset, index + 1L);
/*     */         } 
/* 585 */         produced += lookAheadStep; continue;
/*     */       } 
/* 587 */       if (lookAheadSeq < expectedLookAheadSeq && 
/* 588 */         notAvailable(pIndex, mask, sBuffer, pIndex)) {
/* 589 */         return produced;
/*     */       }
/*     */       
/* 592 */       return produced + fillOneByOne(s, remaining);
/*     */     } 
/*     */     
/* 595 */     return limit;
/*     */   }
/*     */   
/*     */   private boolean notAvailable(long index, int mask, AtomicLongArray sBuffer, long expectedSeq) {
/* 599 */     int seqOffset = AtomicQueueUtil.calcCircularLongElementOffset(index, mask);
/* 600 */     long seq = AtomicQueueUtil.lvLongElement(sBuffer, seqOffset);
/* 601 */     if (seq < expectedSeq) {
/* 602 */       return true;
/*     */     }
/* 604 */     return false;
/*     */   }
/*     */   
/*     */   private int fillOneByOne(MessagePassingQueue.Supplier<E> s, int limit) {
/* 608 */     AtomicLongArray sBuffer = this.sequenceBuffer;
/* 609 */     int mask = this.mask;
/* 610 */     AtomicReferenceArray<E> buffer = this.buffer;
/*     */ 
/*     */ 
/*     */     
/* 614 */     for (int i = 0; i < limit; i++) {
/*     */       long pIndex; int seqOffset; long seq; do {
/* 616 */         pIndex = lvProducerIndex();
/* 617 */         seqOffset = AtomicQueueUtil.calcCircularLongElementOffset(pIndex, mask);
/* 618 */         seq = AtomicQueueUtil.lvLongElement(sBuffer, seqOffset);
/* 619 */         if (seq < pIndex)
/*     */         {
/* 621 */           return i;
/*     */         }
/* 623 */       } while (seq > pIndex || 
/*     */         
/* 625 */         !casProducerIndex(pIndex, pIndex + 1L));
/*     */       
/* 627 */       AtomicQueueUtil.soRefElement(buffer, AtomicQueueUtil.calcCircularRefElementOffset(pIndex, mask), s.get());
/* 628 */       AtomicQueueUtil.soLongElement(sBuffer, seqOffset, pIndex + 1L);
/*     */     } 
/* 630 */     return limit;
/*     */   }
/*     */ 
/*     */   
/*     */   public int drain(MessagePassingQueue.Consumer<E> c) {
/* 635 */     return MessagePassingQueueUtil.drain(this, c);
/*     */   }
/*     */ 
/*     */   
/*     */   public int fill(MessagePassingQueue.Supplier<E> s) {
/* 640 */     return MessagePassingQueueUtil.fillBounded(this, s);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drain(MessagePassingQueue.Consumer<E> c, MessagePassingQueue.WaitStrategy w, MessagePassingQueue.ExitCondition exit) {
/* 645 */     MessagePassingQueueUtil.drain(this, c, w, exit);
/*     */   }
/*     */ 
/*     */   
/*     */   public void fill(MessagePassingQueue.Supplier<E> s, MessagePassingQueue.WaitStrategy wait, MessagePassingQueue.ExitCondition exit) {
/* 650 */     MessagePassingQueueUtil.fill(this, s, wait, exit);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\shaded\org\jctools\queues\atomic\MpmcAtomicArrayQueue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */