/*     */ package io.netty.util.internal.shaded.org.jctools.queues.atomic.unpadded;
/*     */ 
/*     */ import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue;
/*     */ import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueueUtil;
/*     */ import io.netty.util.internal.shaded.org.jctools.queues.atomic.AtomicQueueUtil;
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
/*     */ public class MpscAtomicUnpaddedArrayQueue<E>
/*     */   extends MpscAtomicUnpaddedArrayQueueL3Pad<E>
/*     */ {
/*     */   public MpscAtomicUnpaddedArrayQueue(int capacity) {
/* 158 */     super(capacity);
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
/* 170 */     if (null == e) {
/* 171 */       throw new NullPointerException();
/*     */     }
/* 173 */     int mask = this.mask;
/* 174 */     long capacity = (mask + 1);
/* 175 */     long producerLimit = lvProducerLimit();
/*     */     
/*     */     do {
/* 178 */       pIndex = lvProducerIndex();
/* 179 */       long available = producerLimit - pIndex;
/* 180 */       long size = capacity - available;
/* 181 */       if (size < threshold)
/* 182 */         continue;  long cIndex = lvConsumerIndex();
/* 183 */       size = pIndex - cIndex;
/* 184 */       if (size >= threshold)
/*     */       {
/* 186 */         return false;
/*     */       }
/*     */       
/* 189 */       producerLimit = cIndex + capacity;
/*     */       
/* 191 */       soProducerLimit(producerLimit);
/*     */     
/*     */     }
/* 194 */     while (!casProducerIndex(pIndex, pIndex + 1L));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 200 */     int offset = AtomicQueueUtil.calcCircularRefElementOffset(pIndex, mask);
/* 201 */     AtomicQueueUtil.soRefElement(this.buffer, offset, e);
/*     */     
/* 203 */     return true;
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
/* 218 */     if (null == e) {
/* 219 */       throw new NullPointerException();
/*     */     }
/*     */     
/* 222 */     int mask = this.mask;
/* 223 */     long producerLimit = lvProducerLimit();
/*     */     
/*     */     do {
/* 226 */       pIndex = lvProducerIndex();
/* 227 */       if (pIndex < producerLimit)
/* 228 */         continue;  long cIndex = lvConsumerIndex();
/* 229 */       producerLimit = cIndex + mask + 1L;
/* 230 */       if (pIndex >= producerLimit)
/*     */       {
/* 232 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 236 */       soProducerLimit(producerLimit);
/*     */     
/*     */     }
/* 239 */     while (!casProducerIndex(pIndex, pIndex + 1L));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 245 */     int offset = AtomicQueueUtil.calcCircularRefElementOffset(pIndex, mask);
/* 246 */     AtomicQueueUtil.soRefElement(this.buffer, offset, e);
/*     */     
/* 248 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int failFastOffer(E e) {
/* 258 */     if (null == e) {
/* 259 */       throw new NullPointerException();
/*     */     }
/* 261 */     int mask = this.mask;
/* 262 */     long capacity = (mask + 1);
/* 263 */     long pIndex = lvProducerIndex();
/* 264 */     long producerLimit = lvProducerLimit();
/* 265 */     if (pIndex >= producerLimit) {
/* 266 */       long cIndex = lvConsumerIndex();
/* 267 */       producerLimit = cIndex + capacity;
/* 268 */       if (pIndex >= producerLimit)
/*     */       {
/* 270 */         return 1;
/*     */       }
/*     */       
/* 273 */       soProducerLimit(producerLimit);
/*     */     } 
/*     */ 
/*     */     
/* 277 */     if (!casProducerIndex(pIndex, pIndex + 1L))
/*     */     {
/* 279 */       return -1;
/*     */     }
/*     */     
/* 282 */     int offset = AtomicQueueUtil.calcCircularRefElementOffset(pIndex, mask);
/* 283 */     AtomicQueueUtil.soRefElement(this.buffer, offset, e);
/*     */     
/* 285 */     return 0;
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
/* 299 */     long cIndex = lpConsumerIndex();
/* 300 */     int offset = AtomicQueueUtil.calcCircularRefElementOffset(cIndex, this.mask);
/*     */     
/* 302 */     AtomicReferenceArray<E> buffer = this.buffer;
/*     */     
/* 304 */     E e = (E)AtomicQueueUtil.lvRefElement(buffer, offset);
/* 305 */     if (null == e)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 311 */       if (cIndex != lvProducerIndex()) {
/*     */         do {
/* 313 */           e = (E)AtomicQueueUtil.lvRefElement(buffer, offset);
/* 314 */         } while (e == null);
/*     */       } else {
/* 316 */         return null;
/*     */       } 
/*     */     }
/* 319 */     AtomicQueueUtil.spRefElement(buffer, offset, null);
/* 320 */     soConsumerIndex(cIndex + 1L);
/* 321 */     return e;
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
/* 336 */     AtomicReferenceArray<E> buffer = this.buffer;
/* 337 */     long cIndex = lpConsumerIndex();
/* 338 */     int offset = AtomicQueueUtil.calcCircularRefElementOffset(cIndex, this.mask);
/* 339 */     E e = (E)AtomicQueueUtil.lvRefElement(buffer, offset);
/* 340 */     if (null == e)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 346 */       if (cIndex != lvProducerIndex()) {
/*     */         do {
/* 348 */           e = (E)AtomicQueueUtil.lvRefElement(buffer, offset);
/* 349 */         } while (e == null);
/*     */       } else {
/* 351 */         return null;
/*     */       } 
/*     */     }
/* 354 */     return e;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean relaxedOffer(E e) {
/* 359 */     return offer(e);
/*     */   }
/*     */ 
/*     */   
/*     */   public E relaxedPoll() {
/* 364 */     AtomicReferenceArray<E> buffer = this.buffer;
/* 365 */     long cIndex = lpConsumerIndex();
/* 366 */     int offset = AtomicQueueUtil.calcCircularRefElementOffset(cIndex, this.mask);
/*     */     
/* 368 */     E e = (E)AtomicQueueUtil.lvRefElement(buffer, offset);
/* 369 */     if (null == e) {
/* 370 */       return null;
/*     */     }
/* 372 */     AtomicQueueUtil.spRefElement(buffer, offset, null);
/* 373 */     soConsumerIndex(cIndex + 1L);
/* 374 */     return e;
/*     */   }
/*     */ 
/*     */   
/*     */   public E relaxedPeek() {
/* 379 */     AtomicReferenceArray<E> buffer = this.buffer;
/* 380 */     int mask = this.mask;
/* 381 */     long cIndex = lpConsumerIndex();
/* 382 */     return (E)AtomicQueueUtil.lvRefElement(buffer, AtomicQueueUtil.calcCircularRefElementOffset(cIndex, mask));
/*     */   }
/*     */ 
/*     */   
/*     */   public int drain(MessagePassingQueue.Consumer<E> c, int limit) {
/* 387 */     if (null == c)
/* 388 */       throw new IllegalArgumentException("c is null"); 
/* 389 */     if (limit < 0)
/* 390 */       throw new IllegalArgumentException("limit is negative: " + limit); 
/* 391 */     if (limit == 0)
/* 392 */       return 0; 
/* 393 */     AtomicReferenceArray<E> buffer = this.buffer;
/* 394 */     int mask = this.mask;
/* 395 */     long cIndex = lpConsumerIndex();
/* 396 */     for (int i = 0; i < limit; i++) {
/* 397 */       long index = cIndex + i;
/* 398 */       int offset = AtomicQueueUtil.calcCircularRefElementOffset(index, mask);
/* 399 */       E e = (E)AtomicQueueUtil.lvRefElement(buffer, offset);
/* 400 */       if (null == e) {
/* 401 */         return i;
/*     */       }
/* 403 */       AtomicQueueUtil.spRefElement(buffer, offset, null);
/*     */       
/* 405 */       soConsumerIndex(index + 1L);
/* 406 */       c.accept(e);
/*     */     } 
/* 408 */     return limit;
/*     */   }
/*     */   public int fill(MessagePassingQueue.Supplier<E> s, int limit) {
/*     */     long pIndex;
/*     */     int actualLimit;
/* 413 */     if (null == s)
/* 414 */       throw new IllegalArgumentException("supplier is null"); 
/* 415 */     if (limit < 0)
/* 416 */       throw new IllegalArgumentException("limit is negative:" + limit); 
/* 417 */     if (limit == 0)
/* 418 */       return 0; 
/* 419 */     int mask = this.mask;
/* 420 */     long capacity = (mask + 1);
/* 421 */     long producerLimit = lvProducerLimit();
/*     */ 
/*     */     
/*     */     do {
/* 425 */       pIndex = lvProducerIndex();
/* 426 */       long available = producerLimit - pIndex;
/* 427 */       if (available <= 0L) {
/* 428 */         long cIndex = lvConsumerIndex();
/* 429 */         producerLimit = cIndex + capacity;
/* 430 */         available = producerLimit - pIndex;
/* 431 */         if (available <= 0L)
/*     */         {
/* 433 */           return 0;
/*     */         }
/*     */         
/* 436 */         soProducerLimit(producerLimit);
/*     */       } 
/*     */       
/* 439 */       actualLimit = Math.min((int)available, limit);
/* 440 */     } while (!casProducerIndex(pIndex, pIndex + actualLimit));
/*     */     
/* 442 */     AtomicReferenceArray<E> buffer = this.buffer;
/* 443 */     for (int i = 0; i < actualLimit; i++) {
/*     */       
/* 445 */       int offset = AtomicQueueUtil.calcCircularRefElementOffset(pIndex + i, mask);
/* 446 */       AtomicQueueUtil.soRefElement(buffer, offset, s.get());
/*     */     } 
/* 448 */     return actualLimit;
/*     */   }
/*     */ 
/*     */   
/*     */   public int drain(MessagePassingQueue.Consumer<E> c) {
/* 453 */     return drain(c, capacity());
/*     */   }
/*     */ 
/*     */   
/*     */   public int fill(MessagePassingQueue.Supplier<E> s) {
/* 458 */     return MessagePassingQueueUtil.fillBounded((MessagePassingQueue)this, s);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drain(MessagePassingQueue.Consumer<E> c, MessagePassingQueue.WaitStrategy w, MessagePassingQueue.ExitCondition exit) {
/* 463 */     MessagePassingQueueUtil.drain((MessagePassingQueue)this, c, w, exit);
/*     */   }
/*     */ 
/*     */   
/*     */   public void fill(MessagePassingQueue.Supplier<E> s, MessagePassingQueue.WaitStrategy wait, MessagePassingQueue.ExitCondition exit) {
/* 468 */     MessagePassingQueueUtil.fill((MessagePassingQueue)this, s, wait, exit);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public int weakOffer(E e) {
/* 476 */     return failFastOffer(e);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\shaded\org\jctools\queues\atomi\\unpadded\MpscAtomicUnpaddedArrayQueue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */