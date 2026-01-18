/*     */ package io.netty.util.internal.shaded.org.jctools.queues;
/*     */ 
/*     */ import io.netty.util.internal.shaded.org.jctools.util.UnsafeRefArrayAccess;
/*     */ import java.util.Iterator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MpscArrayQueue<E>
/*     */   extends MpscArrayQueueL3Pad<E>
/*     */ {
/*     */   public MpscArrayQueue(int capacity) {
/* 212 */     super(capacity);
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
/*     */   public boolean offerIfBelowThreshold(E e, int threshold) {
/*     */     long pIndex;
/* 225 */     if (null == e)
/*     */     {
/* 227 */       throw new NullPointerException();
/*     */     }
/*     */     
/* 230 */     long mask = this.mask;
/* 231 */     long capacity = mask + 1L;
/*     */     
/* 233 */     long producerLimit = lvProducerLimit();
/*     */ 
/*     */     
/*     */     do {
/* 237 */       pIndex = lvProducerIndex();
/* 238 */       long available = producerLimit - pIndex;
/* 239 */       long size = capacity - available;
/* 240 */       if (size < threshold)
/*     */         continue; 
/* 242 */       long cIndex = lvConsumerIndex();
/* 243 */       size = pIndex - cIndex;
/* 244 */       if (size >= threshold)
/*     */       {
/* 246 */         return false;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 251 */       producerLimit = cIndex + capacity;
/*     */ 
/*     */       
/* 254 */       soProducerLimit(producerLimit);
/*     */ 
/*     */     
/*     */     }
/* 258 */     while (!casProducerIndex(pIndex, pIndex + 1L));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 265 */     long offset = UnsafeRefArrayAccess.calcCircularRefElementOffset(pIndex, mask);
/* 266 */     UnsafeRefArrayAccess.soRefElement((Object[])this.buffer, offset, e);
/* 267 */     return true;
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
/*     */   public boolean offer(E e) {
/*     */     long pIndex;
/* 283 */     if (null == e)
/*     */     {
/* 285 */       throw new NullPointerException();
/*     */     }
/*     */ 
/*     */     
/* 289 */     long mask = this.mask;
/* 290 */     long producerLimit = lvProducerLimit();
/*     */ 
/*     */     
/*     */     do {
/* 294 */       pIndex = lvProducerIndex();
/* 295 */       if (pIndex < producerLimit)
/*     */         continue; 
/* 297 */       long cIndex = lvConsumerIndex();
/* 298 */       producerLimit = cIndex + mask + 1L;
/*     */       
/* 300 */       if (pIndex >= producerLimit)
/*     */       {
/* 302 */         return false;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 308 */       soProducerLimit(producerLimit);
/*     */ 
/*     */     
/*     */     }
/* 312 */     while (!casProducerIndex(pIndex, pIndex + 1L));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 319 */     long offset = UnsafeRefArrayAccess.calcCircularRefElementOffset(pIndex, mask);
/* 320 */     UnsafeRefArrayAccess.soRefElement((Object[])this.buffer, offset, e);
/* 321 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int failFastOffer(E e) {
/* 332 */     if (null == e)
/*     */     {
/* 334 */       throw new NullPointerException();
/*     */     }
/* 336 */     long mask = this.mask;
/* 337 */     long capacity = mask + 1L;
/* 338 */     long pIndex = lvProducerIndex();
/* 339 */     long producerLimit = lvProducerLimit();
/* 340 */     if (pIndex >= producerLimit) {
/*     */       
/* 342 */       long cIndex = lvConsumerIndex();
/* 343 */       producerLimit = cIndex + capacity;
/* 344 */       if (pIndex >= producerLimit)
/*     */       {
/* 346 */         return 1;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 351 */       soProducerLimit(producerLimit);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 356 */     if (!casProducerIndex(pIndex, pIndex + 1L))
/*     */     {
/* 358 */       return -1;
/*     */     }
/*     */ 
/*     */     
/* 362 */     long offset = UnsafeRefArrayAccess.calcCircularRefElementOffset(pIndex, mask);
/* 363 */     UnsafeRefArrayAccess.soRefElement((Object[])this.buffer, offset, e);
/* 364 */     return 0;
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
/*     */   public E poll() {
/* 379 */     long cIndex = lpConsumerIndex();
/* 380 */     long offset = UnsafeRefArrayAccess.calcCircularRefElementOffset(cIndex, this.mask);
/*     */     
/* 382 */     E[] buffer = this.buffer;
/*     */ 
/*     */     
/* 385 */     E e = (E)UnsafeRefArrayAccess.lvRefElement((Object[])buffer, offset);
/* 386 */     if (null == e)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 393 */       if (cIndex != lvProducerIndex()) {
/*     */         
/*     */         do
/*     */         {
/* 397 */           e = (E)UnsafeRefArrayAccess.lvRefElement((Object[])buffer, offset);
/*     */         }
/* 399 */         while (e == null);
/*     */       }
/*     */       else {
/*     */         
/* 403 */         return null;
/*     */       } 
/*     */     }
/*     */     
/* 407 */     UnsafeRefArrayAccess.spRefElement((Object[])buffer, offset, null);
/* 408 */     soConsumerIndex(cIndex + 1L);
/* 409 */     return e;
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
/*     */   
/*     */   public E peek() {
/* 425 */     E[] buffer = this.buffer;
/*     */     
/* 427 */     long cIndex = lpConsumerIndex();
/* 428 */     long offset = UnsafeRefArrayAccess.calcCircularRefElementOffset(cIndex, this.mask);
/* 429 */     E e = (E)UnsafeRefArrayAccess.lvRefElement((Object[])buffer, offset);
/* 430 */     if (null == e)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 437 */       if (cIndex != lvProducerIndex()) {
/*     */         
/*     */         do
/*     */         {
/* 441 */           e = (E)UnsafeRefArrayAccess.lvRefElement((Object[])buffer, offset);
/*     */         }
/* 443 */         while (e == null);
/*     */       }
/*     */       else {
/*     */         
/* 447 */         return null;
/*     */       } 
/*     */     }
/* 450 */     return e;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean relaxedOffer(E e) {
/* 456 */     return offer(e);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public E relaxedPoll() {
/* 462 */     E[] buffer = this.buffer;
/* 463 */     long cIndex = lpConsumerIndex();
/* 464 */     long offset = UnsafeRefArrayAccess.calcCircularRefElementOffset(cIndex, this.mask);
/*     */ 
/*     */     
/* 467 */     E e = (E)UnsafeRefArrayAccess.lvRefElement((Object[])buffer, offset);
/* 468 */     if (null == e)
/*     */     {
/* 470 */       return null;
/*     */     }
/*     */     
/* 473 */     UnsafeRefArrayAccess.spRefElement((Object[])buffer, offset, null);
/* 474 */     soConsumerIndex(cIndex + 1L);
/* 475 */     return e;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public E relaxedPeek() {
/* 481 */     E[] buffer = this.buffer;
/* 482 */     long mask = this.mask;
/* 483 */     long cIndex = lpConsumerIndex();
/* 484 */     return (E)UnsafeRefArrayAccess.lvRefElement((Object[])buffer, UnsafeRefArrayAccess.calcCircularRefElementOffset(cIndex, mask));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int drain(MessagePassingQueue.Consumer<E> c, int limit) {
/* 490 */     if (null == c)
/* 491 */       throw new IllegalArgumentException("c is null"); 
/* 492 */     if (limit < 0)
/* 493 */       throw new IllegalArgumentException("limit is negative: " + limit); 
/* 494 */     if (limit == 0) {
/* 495 */       return 0;
/*     */     }
/* 497 */     E[] buffer = this.buffer;
/* 498 */     long mask = this.mask;
/* 499 */     long cIndex = lpConsumerIndex();
/*     */     
/* 501 */     for (int i = 0; i < limit; i++) {
/*     */       
/* 503 */       long index = cIndex + i;
/* 504 */       long offset = UnsafeRefArrayAccess.calcCircularRefElementOffset(index, mask);
/* 505 */       E e = (E)UnsafeRefArrayAccess.lvRefElement((Object[])buffer, offset);
/* 506 */       if (null == e)
/*     */       {
/* 508 */         return i;
/*     */       }
/* 510 */       UnsafeRefArrayAccess.spRefElement((Object[])buffer, offset, null);
/* 511 */       soConsumerIndex(index + 1L);
/* 512 */       c.accept(e);
/*     */     } 
/* 514 */     return limit;
/*     */   }
/*     */   
/*     */   public int fill(MessagePassingQueue.Supplier<E> s, int limit) {
/*     */     long pIndex;
/*     */     int actualLimit;
/* 520 */     if (null == s)
/* 521 */       throw new IllegalArgumentException("supplier is null"); 
/* 522 */     if (limit < 0)
/* 523 */       throw new IllegalArgumentException("limit is negative:" + limit); 
/* 524 */     if (limit == 0) {
/* 525 */       return 0;
/*     */     }
/* 527 */     long mask = this.mask;
/* 528 */     long capacity = mask + 1L;
/* 529 */     long producerLimit = lvProducerLimit();
/*     */ 
/*     */ 
/*     */     
/*     */     do {
/* 534 */       pIndex = lvProducerIndex();
/* 535 */       long available = producerLimit - pIndex;
/* 536 */       if (available <= 0L) {
/*     */         
/* 538 */         long cIndex = lvConsumerIndex();
/* 539 */         producerLimit = cIndex + capacity;
/* 540 */         available = producerLimit - pIndex;
/* 541 */         if (available <= 0L)
/*     */         {
/* 543 */           return 0;
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 548 */         soProducerLimit(producerLimit);
/*     */       } 
/*     */       
/* 551 */       actualLimit = Math.min((int)available, limit);
/*     */     }
/* 553 */     while (!casProducerIndex(pIndex, pIndex + actualLimit));
/*     */     
/* 555 */     E[] buffer = this.buffer;
/* 556 */     for (int i = 0; i < actualLimit; i++) {
/*     */ 
/*     */       
/* 559 */       long offset = UnsafeRefArrayAccess.calcCircularRefElementOffset(pIndex + i, mask);
/* 560 */       UnsafeRefArrayAccess.soRefElement((Object[])buffer, offset, s.get());
/*     */     } 
/* 562 */     return actualLimit;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int drain(MessagePassingQueue.Consumer<E> c) {
/* 568 */     return drain(c, capacity());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int fill(MessagePassingQueue.Supplier<E> s) {
/* 574 */     return MessagePassingQueueUtil.fillBounded(this, s);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drain(MessagePassingQueue.Consumer<E> c, MessagePassingQueue.WaitStrategy w, MessagePassingQueue.ExitCondition exit) {
/* 580 */     MessagePassingQueueUtil.drain(this, c, w, exit);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void fill(MessagePassingQueue.Supplier<E> s, MessagePassingQueue.WaitStrategy wait, MessagePassingQueue.ExitCondition exit) {
/* 586 */     MessagePassingQueueUtil.fill(this, s, wait, exit);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\shaded\org\jctools\queues\MpscArrayQueue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */