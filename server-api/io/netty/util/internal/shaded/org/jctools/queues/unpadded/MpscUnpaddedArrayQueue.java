/*     */ package io.netty.util.internal.shaded.org.jctools.queues.unpadded;
/*     */ 
/*     */ import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue;
/*     */ import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueueUtil;
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
/*     */ public class MpscUnpaddedArrayQueue<E>
/*     */   extends MpscUnpaddedArrayQueueL3Pad<E>
/*     */ {
/*     */   public MpscUnpaddedArrayQueue(int capacity) {
/* 157 */     super(capacity);
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
/* 169 */     if (null == e) {
/* 170 */       throw new NullPointerException();
/*     */     }
/* 172 */     long mask = this.mask;
/* 173 */     long capacity = mask + 1L;
/* 174 */     long producerLimit = lvProducerLimit();
/*     */     
/*     */     do {
/* 177 */       pIndex = lvProducerIndex();
/* 178 */       long available = producerLimit - pIndex;
/* 179 */       long size = capacity - available;
/* 180 */       if (size < threshold)
/* 181 */         continue;  long cIndex = lvConsumerIndex();
/* 182 */       size = pIndex - cIndex;
/* 183 */       if (size >= threshold)
/*     */       {
/* 185 */         return false;
/*     */       }
/*     */       
/* 188 */       producerLimit = cIndex + capacity;
/*     */       
/* 190 */       soProducerLimit(producerLimit);
/*     */     
/*     */     }
/* 193 */     while (!casProducerIndex(pIndex, pIndex + 1L));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 199 */     long offset = UnsafeRefArrayAccess.calcCircularRefElementOffset(pIndex, mask);
/* 200 */     UnsafeRefArrayAccess.soRefElement((Object[])this.buffer, offset, e);
/*     */     
/* 202 */     return true;
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
/* 217 */     if (null == e) {
/* 218 */       throw new NullPointerException();
/*     */     }
/*     */     
/* 221 */     long mask = this.mask;
/* 222 */     long producerLimit = lvProducerLimit();
/*     */     
/*     */     do {
/* 225 */       pIndex = lvProducerIndex();
/* 226 */       if (pIndex < producerLimit)
/* 227 */         continue;  long cIndex = lvConsumerIndex();
/* 228 */       producerLimit = cIndex + mask + 1L;
/* 229 */       if (pIndex >= producerLimit)
/*     */       {
/* 231 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 235 */       soProducerLimit(producerLimit);
/*     */     
/*     */     }
/* 238 */     while (!casProducerIndex(pIndex, pIndex + 1L));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 244 */     long offset = UnsafeRefArrayAccess.calcCircularRefElementOffset(pIndex, mask);
/* 245 */     UnsafeRefArrayAccess.soRefElement((Object[])this.buffer, offset, e);
/*     */     
/* 247 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int failFastOffer(E e) {
/* 257 */     if (null == e) {
/* 258 */       throw new NullPointerException();
/*     */     }
/* 260 */     long mask = this.mask;
/* 261 */     long capacity = mask + 1L;
/* 262 */     long pIndex = lvProducerIndex();
/* 263 */     long producerLimit = lvProducerLimit();
/* 264 */     if (pIndex >= producerLimit) {
/* 265 */       long cIndex = lvConsumerIndex();
/* 266 */       producerLimit = cIndex + capacity;
/* 267 */       if (pIndex >= producerLimit)
/*     */       {
/* 269 */         return 1;
/*     */       }
/*     */       
/* 272 */       soProducerLimit(producerLimit);
/*     */     } 
/*     */ 
/*     */     
/* 276 */     if (!casProducerIndex(pIndex, pIndex + 1L))
/*     */     {
/* 278 */       return -1;
/*     */     }
/*     */     
/* 281 */     long offset = UnsafeRefArrayAccess.calcCircularRefElementOffset(pIndex, mask);
/* 282 */     UnsafeRefArrayAccess.soRefElement((Object[])this.buffer, offset, e);
/*     */     
/* 284 */     return 0;
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
/* 298 */     long cIndex = lpConsumerIndex();
/* 299 */     long offset = UnsafeRefArrayAccess.calcCircularRefElementOffset(cIndex, this.mask);
/*     */     
/* 301 */     E[] buffer = this.buffer;
/*     */     
/* 303 */     E e = (E)UnsafeRefArrayAccess.lvRefElement((Object[])buffer, offset);
/* 304 */     if (null == e)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 310 */       if (cIndex != lvProducerIndex()) {
/*     */         do {
/* 312 */           e = (E)UnsafeRefArrayAccess.lvRefElement((Object[])buffer, offset);
/* 313 */         } while (e == null);
/*     */       } else {
/* 315 */         return null;
/*     */       } 
/*     */     }
/* 318 */     UnsafeRefArrayAccess.spRefElement((Object[])buffer, offset, null);
/* 319 */     soConsumerIndex(cIndex + 1L);
/* 320 */     return e;
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
/* 335 */     E[] buffer = this.buffer;
/* 336 */     long cIndex = lpConsumerIndex();
/* 337 */     long offset = UnsafeRefArrayAccess.calcCircularRefElementOffset(cIndex, this.mask);
/* 338 */     E e = (E)UnsafeRefArrayAccess.lvRefElement((Object[])buffer, offset);
/* 339 */     if (null == e)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 345 */       if (cIndex != lvProducerIndex()) {
/*     */         do {
/* 347 */           e = (E)UnsafeRefArrayAccess.lvRefElement((Object[])buffer, offset);
/* 348 */         } while (e == null);
/*     */       } else {
/* 350 */         return null;
/*     */       } 
/*     */     }
/* 353 */     return e;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean relaxedOffer(E e) {
/* 358 */     return offer(e);
/*     */   }
/*     */ 
/*     */   
/*     */   public E relaxedPoll() {
/* 363 */     E[] buffer = this.buffer;
/* 364 */     long cIndex = lpConsumerIndex();
/* 365 */     long offset = UnsafeRefArrayAccess.calcCircularRefElementOffset(cIndex, this.mask);
/*     */     
/* 367 */     E e = (E)UnsafeRefArrayAccess.lvRefElement((Object[])buffer, offset);
/* 368 */     if (null == e) {
/* 369 */       return null;
/*     */     }
/* 371 */     UnsafeRefArrayAccess.spRefElement((Object[])buffer, offset, null);
/* 372 */     soConsumerIndex(cIndex + 1L);
/* 373 */     return e;
/*     */   }
/*     */ 
/*     */   
/*     */   public E relaxedPeek() {
/* 378 */     E[] buffer = this.buffer;
/* 379 */     long mask = this.mask;
/* 380 */     long cIndex = lpConsumerIndex();
/* 381 */     return (E)UnsafeRefArrayAccess.lvRefElement((Object[])buffer, UnsafeRefArrayAccess.calcCircularRefElementOffset(cIndex, mask));
/*     */   }
/*     */ 
/*     */   
/*     */   public int drain(MessagePassingQueue.Consumer<E> c, int limit) {
/* 386 */     if (null == c)
/* 387 */       throw new IllegalArgumentException("c is null"); 
/* 388 */     if (limit < 0)
/* 389 */       throw new IllegalArgumentException("limit is negative: " + limit); 
/* 390 */     if (limit == 0)
/* 391 */       return 0; 
/* 392 */     E[] buffer = this.buffer;
/* 393 */     long mask = this.mask;
/* 394 */     long cIndex = lpConsumerIndex();
/* 395 */     for (int i = 0; i < limit; i++) {
/* 396 */       long index = cIndex + i;
/* 397 */       long offset = UnsafeRefArrayAccess.calcCircularRefElementOffset(index, mask);
/* 398 */       E e = (E)UnsafeRefArrayAccess.lvRefElement((Object[])buffer, offset);
/* 399 */       if (null == e) {
/* 400 */         return i;
/*     */       }
/* 402 */       UnsafeRefArrayAccess.spRefElement((Object[])buffer, offset, null);
/*     */       
/* 404 */       soConsumerIndex(index + 1L);
/* 405 */       c.accept(e);
/*     */     } 
/* 407 */     return limit;
/*     */   }
/*     */   public int fill(MessagePassingQueue.Supplier<E> s, int limit) {
/*     */     long pIndex;
/*     */     int actualLimit;
/* 412 */     if (null == s)
/* 413 */       throw new IllegalArgumentException("supplier is null"); 
/* 414 */     if (limit < 0)
/* 415 */       throw new IllegalArgumentException("limit is negative:" + limit); 
/* 416 */     if (limit == 0)
/* 417 */       return 0; 
/* 418 */     long mask = this.mask;
/* 419 */     long capacity = mask + 1L;
/* 420 */     long producerLimit = lvProducerLimit();
/*     */ 
/*     */     
/*     */     do {
/* 424 */       pIndex = lvProducerIndex();
/* 425 */       long available = producerLimit - pIndex;
/* 426 */       if (available <= 0L) {
/* 427 */         long cIndex = lvConsumerIndex();
/* 428 */         producerLimit = cIndex + capacity;
/* 429 */         available = producerLimit - pIndex;
/* 430 */         if (available <= 0L)
/*     */         {
/* 432 */           return 0;
/*     */         }
/*     */         
/* 435 */         soProducerLimit(producerLimit);
/*     */       } 
/*     */       
/* 438 */       actualLimit = Math.min((int)available, limit);
/* 439 */     } while (!casProducerIndex(pIndex, pIndex + actualLimit));
/*     */     
/* 441 */     E[] buffer = this.buffer;
/* 442 */     for (int i = 0; i < actualLimit; i++) {
/*     */       
/* 444 */       long offset = UnsafeRefArrayAccess.calcCircularRefElementOffset(pIndex + i, mask);
/* 445 */       UnsafeRefArrayAccess.soRefElement((Object[])buffer, offset, s.get());
/*     */     } 
/* 447 */     return actualLimit;
/*     */   }
/*     */ 
/*     */   
/*     */   public int drain(MessagePassingQueue.Consumer<E> c) {
/* 452 */     return drain(c, capacity());
/*     */   }
/*     */ 
/*     */   
/*     */   public int fill(MessagePassingQueue.Supplier<E> s) {
/* 457 */     return MessagePassingQueueUtil.fillBounded(this, s);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drain(MessagePassingQueue.Consumer<E> c, MessagePassingQueue.WaitStrategy w, MessagePassingQueue.ExitCondition exit) {
/* 462 */     MessagePassingQueueUtil.drain(this, c, w, exit);
/*     */   }
/*     */ 
/*     */   
/*     */   public void fill(MessagePassingQueue.Supplier<E> s, MessagePassingQueue.WaitStrategy wait, MessagePassingQueue.ExitCondition exit) {
/* 467 */     MessagePassingQueueUtil.fill(this, s, wait, exit);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\shaded\org\jctools\queue\\unpadded\MpscUnpaddedArrayQueue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */