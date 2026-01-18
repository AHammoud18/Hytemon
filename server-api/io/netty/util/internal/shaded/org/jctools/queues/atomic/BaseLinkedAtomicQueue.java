/*     */ package io.netty.util.internal.shaded.org.jctools.queues.atomic;
/*     */ 
/*     */ import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue;
/*     */ import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueueUtil;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class BaseLinkedAtomicQueue<E>
/*     */   extends BaseLinkedAtomicQueuePad2<E>
/*     */ {
/*     */   public final Iterator<E> iterator() {
/* 257 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 262 */     return getClass().getName();
/*     */   }
/*     */   
/*     */   protected final LinkedQueueAtomicNode<E> newNode() {
/* 266 */     return new LinkedQueueAtomicNode<E>();
/*     */   }
/*     */   
/*     */   protected final LinkedQueueAtomicNode<E> newNode(E e) {
/* 270 */     return new LinkedQueueAtomicNode<E>(e);
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
/*     */   
/*     */   public final int size() {
/* 287 */     LinkedQueueAtomicNode<E> chaserNode = lvConsumerNode();
/* 288 */     LinkedQueueAtomicNode<E> producerNode = lvProducerNode();
/* 289 */     int size = 0;
/*     */     
/* 291 */     while (chaserNode != producerNode && chaserNode != null && size < Integer.MAX_VALUE) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 296 */       LinkedQueueAtomicNode<E> next = chaserNode.lvNext();
/*     */       
/* 298 */       if (next == chaserNode) {
/* 299 */         return size;
/*     */       }
/* 301 */       chaserNode = next;
/* 302 */       size++;
/*     */     } 
/* 304 */     return size;
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
/*     */   public boolean isEmpty() {
/* 319 */     LinkedQueueAtomicNode<E> consumerNode = lvConsumerNode();
/* 320 */     LinkedQueueAtomicNode<E> producerNode = lvProducerNode();
/* 321 */     return (consumerNode == producerNode);
/*     */   }
/*     */ 
/*     */   
/*     */   protected E getSingleConsumerNodeValue(LinkedQueueAtomicNode<E> currConsumerNode, LinkedQueueAtomicNode<E> nextNode) {
/* 326 */     E nextValue = nextNode.getAndNullValue();
/*     */ 
/*     */ 
/*     */     
/* 330 */     currConsumerNode.soNext(currConsumerNode);
/* 331 */     spConsumerNode(nextNode);
/*     */     
/* 333 */     return nextValue;
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
/*     */ 
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
/* 359 */     LinkedQueueAtomicNode<E> currConsumerNode = lpConsumerNode();
/* 360 */     LinkedQueueAtomicNode<E> nextNode = currConsumerNode.lvNext();
/* 361 */     if (nextNode != null)
/* 362 */       return getSingleConsumerNodeValue(currConsumerNode, nextNode); 
/* 363 */     if (currConsumerNode != lvProducerNode()) {
/* 364 */       nextNode = spinWaitForNextNode(currConsumerNode);
/*     */       
/* 366 */       return getSingleConsumerNodeValue(currConsumerNode, nextNode);
/*     */     } 
/* 368 */     return null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public E peek() {
/* 391 */     LinkedQueueAtomicNode<E> currConsumerNode = lpConsumerNode();
/* 392 */     LinkedQueueAtomicNode<E> nextNode = currConsumerNode.lvNext();
/* 393 */     if (nextNode != null)
/* 394 */       return nextNode.lpValue(); 
/* 395 */     if (currConsumerNode != lvProducerNode()) {
/* 396 */       nextNode = spinWaitForNextNode(currConsumerNode);
/*     */       
/* 398 */       return nextNode.lpValue();
/*     */     } 
/* 400 */     return null;
/*     */   }
/*     */   
/*     */   LinkedQueueAtomicNode<E> spinWaitForNextNode(LinkedQueueAtomicNode<E> currNode) {
/*     */     LinkedQueueAtomicNode<E> nextNode;
/* 405 */     while ((nextNode = currNode.lvNext()) == null);
/*     */ 
/*     */     
/* 408 */     return nextNode;
/*     */   }
/*     */ 
/*     */   
/*     */   public E relaxedPoll() {
/* 413 */     LinkedQueueAtomicNode<E> currConsumerNode = lpConsumerNode();
/* 414 */     LinkedQueueAtomicNode<E> nextNode = currConsumerNode.lvNext();
/* 415 */     if (nextNode != null) {
/* 416 */       return getSingleConsumerNodeValue(currConsumerNode, nextNode);
/*     */     }
/* 418 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public E relaxedPeek() {
/* 423 */     LinkedQueueAtomicNode<E> nextNode = lpConsumerNode().lvNext();
/* 424 */     if (nextNode != null) {
/* 425 */       return nextNode.lpValue();
/*     */     }
/* 427 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean relaxedOffer(E e) {
/* 432 */     return offer(e);
/*     */   }
/*     */ 
/*     */   
/*     */   public int drain(MessagePassingQueue.Consumer<E> c, int limit) {
/* 437 */     if (null == c)
/* 438 */       throw new IllegalArgumentException("c is null"); 
/* 439 */     if (limit < 0)
/* 440 */       throw new IllegalArgumentException("limit is negative: " + limit); 
/* 441 */     if (limit == 0)
/* 442 */       return 0; 
/* 443 */     LinkedQueueAtomicNode<E> chaserNode = lpConsumerNode();
/* 444 */     for (int i = 0; i < limit; i++) {
/* 445 */       LinkedQueueAtomicNode<E> nextNode = chaserNode.lvNext();
/* 446 */       if (nextNode == null) {
/* 447 */         return i;
/*     */       }
/*     */       
/* 450 */       E nextValue = getSingleConsumerNodeValue(chaserNode, nextNode);
/* 451 */       chaserNode = nextNode;
/* 452 */       c.accept(nextValue);
/*     */     } 
/* 454 */     return limit;
/*     */   }
/*     */ 
/*     */   
/*     */   public int drain(MessagePassingQueue.Consumer<E> c) {
/* 459 */     return MessagePassingQueueUtil.drain(this, c);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drain(MessagePassingQueue.Consumer<E> c, MessagePassingQueue.WaitStrategy wait, MessagePassingQueue.ExitCondition exit) {
/* 464 */     MessagePassingQueueUtil.drain(this, c, wait, exit);
/*     */   }
/*     */ 
/*     */   
/*     */   public int capacity() {
/* 469 */     return -1;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\shaded\org\jctools\queues\atomic\BaseLinkedAtomicQueue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */