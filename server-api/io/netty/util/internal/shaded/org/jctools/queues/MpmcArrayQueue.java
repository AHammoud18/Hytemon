/*     */ package io.netty.util.internal.shaded.org.jctools.queues;
/*     */ 
/*     */ import io.netty.util.internal.shaded.org.jctools.util.RangeUtil;
/*     */ import io.netty.util.internal.shaded.org.jctools.util.UnsafeLongArrayAccess;
/*     */ import io.netty.util.internal.shaded.org.jctools.util.UnsafeRefArrayAccess;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MpmcArrayQueue<E>
/*     */   extends MpmcArrayQueueL3Pad<E>
/*     */ {
/* 170 */   public static final int MAX_LOOK_AHEAD_STEP = Integer.getInteger("jctools.mpmc.max.lookahead.step", 4096).intValue();
/*     */   
/*     */   private final int lookAheadStep;
/*     */   
/*     */   public MpmcArrayQueue(int capacity) {
/* 175 */     super(RangeUtil.checkGreaterThanOrEqual(capacity, 2, "capacity"));
/* 176 */     this.lookAheadStep = Math.max(2, Math.min(capacity() / 4, MAX_LOOK_AHEAD_STEP));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean offer(E e) {
/*     */     long pIndex, seqOffset, seq;
/* 182 */     if (null == e)
/*     */     {
/* 184 */       throw new NullPointerException();
/*     */     }
/* 186 */     long mask = this.mask;
/* 187 */     long capacity = mask + 1L;
/* 188 */     long[] sBuffer = this.sequenceBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 193 */     long cIndex = Long.MIN_VALUE;
/*     */     
/*     */     do {
/* 196 */       pIndex = lvProducerIndex();
/* 197 */       seqOffset = UnsafeLongArrayAccess.calcCircularLongElementOffset(pIndex, mask);
/* 198 */       seq = UnsafeLongArrayAccess.lvLongElement(sBuffer, seqOffset);
/*     */       
/* 200 */       if (seq >= pIndex) {
/*     */         continue;
/*     */       }
/* 203 */       if (pIndex - capacity >= cIndex && pIndex - capacity >= (
/* 204 */         cIndex = lvConsumerIndex()))
/*     */       {
/* 206 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 210 */       seq = pIndex + 1L;
/*     */ 
/*     */     
/*     */     }
/* 214 */     while (seq > pIndex || 
/* 215 */       !casProducerIndex(pIndex, pIndex + 1L));
/*     */ 
/*     */     
/* 218 */     UnsafeRefArrayAccess.spRefElement((Object[])this.buffer, UnsafeRefArrayAccess.calcCircularRefElementOffset(pIndex, mask), e);
/*     */     
/* 220 */     UnsafeLongArrayAccess.soLongElement(sBuffer, seqOffset, pIndex + 1L);
/* 221 */     return true;
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
/*     */   public E poll() {
/* 234 */     long cIndex, seq, seqOffset, expectedSeq, sBuffer[] = this.sequenceBuffer;
/* 235 */     long mask = this.mask;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 241 */     long pIndex = -1L;
/*     */     
/*     */     do {
/* 244 */       cIndex = lvConsumerIndex();
/* 245 */       seqOffset = UnsafeLongArrayAccess.calcCircularLongElementOffset(cIndex, mask);
/* 246 */       seq = UnsafeLongArrayAccess.lvLongElement(sBuffer, seqOffset);
/* 247 */       expectedSeq = cIndex + 1L;
/* 248 */       if (seq >= expectedSeq) {
/*     */         continue;
/*     */       }
/* 251 */       if (cIndex >= pIndex && cIndex == (
/* 252 */         pIndex = lvProducerIndex()))
/*     */       {
/*     */         
/* 255 */         return null;
/*     */       }
/*     */ 
/*     */       
/* 259 */       seq = expectedSeq + 1L;
/*     */ 
/*     */     
/*     */     }
/* 263 */     while (seq > expectedSeq || 
/* 264 */       !casConsumerIndex(cIndex, cIndex + 1L));
/*     */     
/* 266 */     long offset = UnsafeRefArrayAccess.calcCircularRefElementOffset(cIndex, mask);
/* 267 */     E e = (E)UnsafeRefArrayAccess.lpRefElement((Object[])this.buffer, offset);
/* 268 */     UnsafeRefArrayAccess.spRefElement((Object[])this.buffer, offset, null);
/*     */     
/* 270 */     UnsafeLongArrayAccess.soLongElement(sBuffer, seqOffset, cIndex + mask + 1L);
/* 271 */     return e;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public E peek() {
/* 278 */     long[] sBuffer = this.sequenceBuffer;
/* 279 */     long mask = this.mask;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 285 */     long pIndex = -1L;
/*     */ 
/*     */     
/*     */     while (true) {
/* 289 */       long cIndex = lvConsumerIndex();
/* 290 */       long seqOffset = UnsafeLongArrayAccess.calcCircularLongElementOffset(cIndex, mask);
/* 291 */       long seq = UnsafeLongArrayAccess.lvLongElement(sBuffer, seqOffset);
/* 292 */       long expectedSeq = cIndex + 1L;
/* 293 */       if (seq < expectedSeq) {
/*     */ 
/*     */         
/* 296 */         if (cIndex >= pIndex && cIndex == (
/* 297 */           pIndex = lvProducerIndex()))
/*     */         {
/*     */           
/* 300 */           return null; } 
/*     */         continue;
/*     */       } 
/* 303 */       if (seq == expectedSeq) {
/*     */         
/* 305 */         long offset = UnsafeRefArrayAccess.calcCircularRefElementOffset(cIndex, mask);
/* 306 */         E e = (E)UnsafeRefArrayAccess.lvRefElement((Object[])this.buffer, offset);
/* 307 */         if (lvConsumerIndex() == cIndex) {
/* 308 */           return e;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean relaxedOffer(E e) {
/*     */     long pIndex, seqOffset, seq;
/* 316 */     if (null == e)
/*     */     {
/* 318 */       throw new NullPointerException();
/*     */     }
/* 320 */     long mask = this.mask;
/* 321 */     long[] sBuffer = this.sequenceBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     do {
/* 328 */       pIndex = lvProducerIndex();
/* 329 */       seqOffset = UnsafeLongArrayAccess.calcCircularLongElementOffset(pIndex, mask);
/* 330 */       seq = UnsafeLongArrayAccess.lvLongElement(sBuffer, seqOffset);
/* 331 */       if (seq < pIndex)
/*     */       {
/* 333 */         return false;
/*     */       }
/*     */     }
/* 336 */     while (seq > pIndex || 
/* 337 */       !casProducerIndex(pIndex, pIndex + 1L));
/*     */ 
/*     */     
/* 340 */     UnsafeRefArrayAccess.spRefElement((Object[])this.buffer, UnsafeRefArrayAccess.calcCircularRefElementOffset(pIndex, mask), e);
/* 341 */     UnsafeLongArrayAccess.soLongElement(sBuffer, seqOffset, pIndex + 1L);
/* 342 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public E relaxedPoll() {
/* 348 */     long cIndex, seqOffset, seq, expectedSeq, sBuffer[] = this.sequenceBuffer;
/* 349 */     long mask = this.mask;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     do {
/* 357 */       cIndex = lvConsumerIndex();
/* 358 */       seqOffset = UnsafeLongArrayAccess.calcCircularLongElementOffset(cIndex, mask);
/* 359 */       seq = UnsafeLongArrayAccess.lvLongElement(sBuffer, seqOffset);
/* 360 */       expectedSeq = cIndex + 1L;
/* 361 */       if (seq < expectedSeq)
/*     */       {
/* 363 */         return null;
/*     */       }
/*     */     }
/* 366 */     while (seq > expectedSeq || 
/* 367 */       !casConsumerIndex(cIndex, cIndex + 1L));
/*     */     
/* 369 */     long offset = UnsafeRefArrayAccess.calcCircularRefElementOffset(cIndex, mask);
/* 370 */     E e = (E)UnsafeRefArrayAccess.lpRefElement((Object[])this.buffer, offset);
/* 371 */     UnsafeRefArrayAccess.spRefElement((Object[])this.buffer, offset, null);
/* 372 */     UnsafeLongArrayAccess.soLongElement(sBuffer, seqOffset, cIndex + mask + 1L);
/* 373 */     return e;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public E relaxedPeek() {
/* 380 */     long[] sBuffer = this.sequenceBuffer;
/* 381 */     long mask = this.mask;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     while (true) {
/* 390 */       long cIndex = lvConsumerIndex();
/* 391 */       long seqOffset = UnsafeLongArrayAccess.calcCircularLongElementOffset(cIndex, mask);
/* 392 */       long seq = UnsafeLongArrayAccess.lvLongElement(sBuffer, seqOffset);
/* 393 */       long expectedSeq = cIndex + 1L;
/* 394 */       if (seq < expectedSeq)
/*     */       {
/* 396 */         return null;
/*     */       }
/* 398 */       if (seq == expectedSeq) {
/*     */         
/* 400 */         long offset = UnsafeRefArrayAccess.calcCircularRefElementOffset(cIndex, mask);
/* 401 */         E e = (E)UnsafeRefArrayAccess.lvRefElement((Object[])this.buffer, offset);
/* 402 */         if (lvConsumerIndex() == cIndex) {
/* 403 */           return e;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int drain(MessagePassingQueue.Consumer<E> c, int limit) {
/* 412 */     if (null == c)
/* 413 */       throw new IllegalArgumentException("c is null"); 
/* 414 */     if (limit < 0)
/* 415 */       throw new IllegalArgumentException("limit is negative: " + limit); 
/* 416 */     if (limit == 0) {
/* 417 */       return 0;
/*     */     }
/* 419 */     long[] sBuffer = this.sequenceBuffer;
/* 420 */     long mask = this.mask;
/* 421 */     E[] buffer = this.buffer;
/* 422 */     int maxLookAheadStep = Math.min(this.lookAheadStep, limit);
/* 423 */     int consumed = 0;
/*     */     
/* 425 */     while (consumed < limit) {
/*     */       
/* 427 */       int remaining = limit - consumed;
/* 428 */       int lookAheadStep = Math.min(remaining, maxLookAheadStep);
/* 429 */       long cIndex = lvConsumerIndex();
/* 430 */       long lookAheadIndex = cIndex + lookAheadStep - 1L;
/* 431 */       long lookAheadSeqOffset = UnsafeLongArrayAccess.calcCircularLongElementOffset(lookAheadIndex, mask);
/* 432 */       long lookAheadSeq = UnsafeLongArrayAccess.lvLongElement(sBuffer, lookAheadSeqOffset);
/* 433 */       long expectedLookAheadSeq = lookAheadIndex + 1L;
/* 434 */       if (lookAheadSeq == expectedLookAheadSeq && casConsumerIndex(cIndex, expectedLookAheadSeq)) {
/*     */         
/* 436 */         for (int i = 0; i < lookAheadStep; i++) {
/*     */           
/* 438 */           long index = cIndex + i;
/* 439 */           long seqOffset = UnsafeLongArrayAccess.calcCircularLongElementOffset(index, mask);
/* 440 */           long offset = UnsafeRefArrayAccess.calcCircularRefElementOffset(index, mask);
/* 441 */           long expectedSeq = index + 1L;
/* 442 */           while (UnsafeLongArrayAccess.lvLongElement(sBuffer, seqOffset) != expectedSeq);
/*     */ 
/*     */ 
/*     */           
/* 446 */           E e = (E)UnsafeRefArrayAccess.lpRefElement((Object[])buffer, offset);
/* 447 */           UnsafeRefArrayAccess.spRefElement((Object[])buffer, offset, null);
/* 448 */           UnsafeLongArrayAccess.soLongElement(sBuffer, seqOffset, index + mask + 1L);
/* 449 */           c.accept(e);
/*     */         } 
/* 451 */         consumed += lookAheadStep;
/*     */         
/*     */         continue;
/*     */       } 
/* 455 */       if (lookAheadSeq < expectedLookAheadSeq)
/*     */       {
/* 457 */         if (notAvailable(cIndex, mask, sBuffer, cIndex + 1L))
/*     */         {
/* 459 */           return consumed;
/*     */         }
/*     */       }
/* 462 */       return consumed + drainOneByOne(c, remaining);
/*     */     } 
/*     */     
/* 465 */     return limit;
/*     */   }
/*     */ 
/*     */   
/*     */   private int drainOneByOne(MessagePassingQueue.Consumer<E> c, int limit) {
/* 470 */     long[] sBuffer = this.sequenceBuffer;
/* 471 */     long mask = this.mask;
/* 472 */     E[] buffer = this.buffer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 478 */     for (int i = 0; i < limit; i++) {
/*     */       long cIndex, seqOffset, seq, expectedSeq;
/*     */       
/*     */       do {
/* 482 */         cIndex = lvConsumerIndex();
/* 483 */         seqOffset = UnsafeLongArrayAccess.calcCircularLongElementOffset(cIndex, mask);
/* 484 */         seq = UnsafeLongArrayAccess.lvLongElement(sBuffer, seqOffset);
/* 485 */         expectedSeq = cIndex + 1L;
/* 486 */         if (seq < expectedSeq)
/*     */         {
/* 488 */           return i;
/*     */         }
/*     */       }
/* 491 */       while (seq > expectedSeq || 
/* 492 */         !casConsumerIndex(cIndex, cIndex + 1L));
/*     */       
/* 494 */       long offset = UnsafeRefArrayAccess.calcCircularRefElementOffset(cIndex, mask);
/* 495 */       E e = (E)UnsafeRefArrayAccess.lpRefElement((Object[])buffer, offset);
/* 496 */       UnsafeRefArrayAccess.spRefElement((Object[])buffer, offset, null);
/* 497 */       UnsafeLongArrayAccess.soLongElement(sBuffer, seqOffset, cIndex + mask + 1L);
/* 498 */       c.accept(e);
/*     */     } 
/* 500 */     return limit;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int fill(MessagePassingQueue.Supplier<E> s, int limit) {
/* 506 */     if (null == s)
/* 507 */       throw new IllegalArgumentException("supplier is null"); 
/* 508 */     if (limit < 0)
/* 509 */       throw new IllegalArgumentException("limit is negative:" + limit); 
/* 510 */     if (limit == 0) {
/* 511 */       return 0;
/*     */     }
/* 513 */     long[] sBuffer = this.sequenceBuffer;
/* 514 */     long mask = this.mask;
/* 515 */     E[] buffer = this.buffer;
/* 516 */     int maxLookAheadStep = Math.min(this.lookAheadStep, limit);
/* 517 */     int produced = 0;
/*     */     
/* 519 */     while (produced < limit) {
/*     */       
/* 521 */       int remaining = limit - produced;
/* 522 */       int lookAheadStep = Math.min(remaining, maxLookAheadStep);
/* 523 */       long pIndex = lvProducerIndex();
/* 524 */       long lookAheadIndex = pIndex + lookAheadStep - 1L;
/* 525 */       long lookAheadSeqOffset = UnsafeLongArrayAccess.calcCircularLongElementOffset(lookAheadIndex, mask);
/* 526 */       long lookAheadSeq = UnsafeLongArrayAccess.lvLongElement(sBuffer, lookAheadSeqOffset);
/* 527 */       long expectedLookAheadSeq = lookAheadIndex;
/* 528 */       if (lookAheadSeq == expectedLookAheadSeq && casProducerIndex(pIndex, expectedLookAheadSeq + 1L)) {
/*     */         
/* 530 */         for (int i = 0; i < lookAheadStep; i++) {
/*     */           
/* 532 */           long index = pIndex + i;
/* 533 */           long seqOffset = UnsafeLongArrayAccess.calcCircularLongElementOffset(index, mask);
/* 534 */           long offset = UnsafeRefArrayAccess.calcCircularRefElementOffset(index, mask);
/* 535 */           while (UnsafeLongArrayAccess.lvLongElement(sBuffer, seqOffset) != index);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 540 */           UnsafeRefArrayAccess.soRefElement((Object[])buffer, offset, s.get());
/* 541 */           UnsafeLongArrayAccess.soLongElement(sBuffer, seqOffset, index + 1L);
/*     */         } 
/* 543 */         produced += lookAheadStep;
/*     */         
/*     */         continue;
/*     */       } 
/* 547 */       if (lookAheadSeq < expectedLookAheadSeq)
/*     */       {
/* 549 */         if (notAvailable(pIndex, mask, sBuffer, pIndex))
/*     */         {
/* 551 */           return produced;
/*     */         }
/*     */       }
/* 554 */       return produced + fillOneByOne(s, remaining);
/*     */     } 
/*     */     
/* 557 */     return limit;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean notAvailable(long index, long mask, long[] sBuffer, long expectedSeq) {
/* 562 */     long seqOffset = UnsafeLongArrayAccess.calcCircularLongElementOffset(index, mask);
/* 563 */     long seq = UnsafeLongArrayAccess.lvLongElement(sBuffer, seqOffset);
/* 564 */     if (seq < expectedSeq)
/*     */     {
/* 566 */       return true;
/*     */     }
/* 568 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private int fillOneByOne(MessagePassingQueue.Supplier<E> s, int limit) {
/* 573 */     long[] sBuffer = this.sequenceBuffer;
/* 574 */     long mask = this.mask;
/* 575 */     E[] buffer = this.buffer;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 580 */     for (int i = 0; i < limit; i++) {
/*     */       long pIndex, seqOffset, seq;
/*     */       
/*     */       do {
/* 584 */         pIndex = lvProducerIndex();
/* 585 */         seqOffset = UnsafeLongArrayAccess.calcCircularLongElementOffset(pIndex, mask);
/* 586 */         seq = UnsafeLongArrayAccess.lvLongElement(sBuffer, seqOffset);
/* 587 */         if (seq < pIndex)
/*     */         {
/* 589 */           return i;
/*     */         }
/*     */       }
/* 592 */       while (seq > pIndex || 
/* 593 */         !casProducerIndex(pIndex, pIndex + 1L));
/*     */       
/* 595 */       UnsafeRefArrayAccess.soRefElement((Object[])buffer, UnsafeRefArrayAccess.calcCircularRefElementOffset(pIndex, mask), s.get());
/* 596 */       UnsafeLongArrayAccess.soLongElement(sBuffer, seqOffset, pIndex + 1L);
/*     */     } 
/* 598 */     return limit;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int drain(MessagePassingQueue.Consumer<E> c) {
/* 604 */     return MessagePassingQueueUtil.drain(this, c);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int fill(MessagePassingQueue.Supplier<E> s) {
/* 610 */     return MessagePassingQueueUtil.fillBounded(this, s);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drain(MessagePassingQueue.Consumer<E> c, MessagePassingQueue.WaitStrategy w, MessagePassingQueue.ExitCondition exit) {
/* 616 */     MessagePassingQueueUtil.drain(this, c, w, exit);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void fill(MessagePassingQueue.Supplier<E> s, MessagePassingQueue.WaitStrategy wait, MessagePassingQueue.ExitCondition exit) {
/* 622 */     MessagePassingQueueUtil.fill(this, s, wait, exit);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\shaded\org\jctools\queues\MpmcArrayQueue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */