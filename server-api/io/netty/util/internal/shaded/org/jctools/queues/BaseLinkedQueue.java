/*     */ package io.netty.util.internal.shaded.org.jctools.queues;
/*     */ 
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
/*     */ abstract class BaseLinkedQueue<E>
/*     */   extends BaseLinkedQueuePad2<E>
/*     */ {
/*     */   public final Iterator<E> iterator() {
/* 151 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 157 */     return getClass().getName();
/*     */   }
/*     */ 
/*     */   
/*     */   protected final LinkedQueueNode<E> newNode() {
/* 162 */     return new LinkedQueueNode<E>();
/*     */   }
/*     */ 
/*     */   
/*     */   protected final LinkedQueueNode<E> newNode(E e) {
/* 167 */     return new LinkedQueueNode<E>(e);
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
/*     */   public final int size() {
/* 185 */     LinkedQueueNode<E> chaserNode = lvConsumerNode();
/* 186 */     LinkedQueueNode<E> producerNode = lvProducerNode();
/* 187 */     int size = 0;
/*     */     
/* 189 */     while (chaserNode != producerNode && chaserNode != null && size < Integer.MAX_VALUE) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 194 */       LinkedQueueNode<E> next = chaserNode.lvNext();
/*     */       
/* 196 */       if (next == chaserNode)
/*     */       {
/* 198 */         return size;
/*     */       }
/* 200 */       chaserNode = next;
/* 201 */       size++;
/*     */     } 
/* 203 */     return size;
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
/*     */   public boolean isEmpty() {
/* 219 */     LinkedQueueNode<E> consumerNode = lvConsumerNode();
/* 220 */     LinkedQueueNode<E> producerNode = lvProducerNode();
/* 221 */     return (consumerNode == producerNode);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected E getSingleConsumerNodeValue(LinkedQueueNode<E> currConsumerNode, LinkedQueueNode<E> nextNode) {
/* 227 */     E nextValue = nextNode.getAndNullValue();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 232 */     currConsumerNode.soNext(currConsumerNode);
/* 233 */     spConsumerNode(nextNode);
/*     */     
/* 235 */     return nextValue;
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
/*     */   
/*     */   public E poll() {
/* 262 */     LinkedQueueNode<E> currConsumerNode = lpConsumerNode();
/* 263 */     LinkedQueueNode<E> nextNode = currConsumerNode.lvNext();
/* 264 */     if (nextNode != null)
/*     */     {
/* 266 */       return getSingleConsumerNodeValue(currConsumerNode, nextNode);
/*     */     }
/* 268 */     if (currConsumerNode != lvProducerNode()) {
/*     */       
/* 270 */       nextNode = spinWaitForNextNode(currConsumerNode);
/*     */       
/* 272 */       return getSingleConsumerNodeValue(currConsumerNode, nextNode);
/*     */     } 
/* 274 */     return null;
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
/*     */   public E peek() {
/* 298 */     LinkedQueueNode<E> currConsumerNode = lpConsumerNode();
/* 299 */     LinkedQueueNode<E> nextNode = currConsumerNode.lvNext();
/* 300 */     if (nextNode != null)
/*     */     {
/* 302 */       return nextNode.lpValue();
/*     */     }
/* 304 */     if (currConsumerNode != lvProducerNode()) {
/*     */       
/* 306 */       nextNode = spinWaitForNextNode(currConsumerNode);
/*     */       
/* 308 */       return nextNode.lpValue();
/*     */     } 
/* 310 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   LinkedQueueNode<E> spinWaitForNextNode(LinkedQueueNode<E> currNode) {
/*     */     LinkedQueueNode<E> nextNode;
/* 316 */     while ((nextNode = currNode.lvNext()) == null);
/*     */ 
/*     */ 
/*     */     
/* 320 */     return nextNode;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public E relaxedPoll() {
/* 326 */     LinkedQueueNode<E> currConsumerNode = lpConsumerNode();
/* 327 */     LinkedQueueNode<E> nextNode = currConsumerNode.lvNext();
/* 328 */     if (nextNode != null)
/*     */     {
/* 330 */       return getSingleConsumerNodeValue(currConsumerNode, nextNode);
/*     */     }
/* 332 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public E relaxedPeek() {
/* 338 */     LinkedQueueNode<E> nextNode = lpConsumerNode().lvNext();
/* 339 */     if (nextNode != null)
/*     */     {
/* 341 */       return nextNode.lpValue();
/*     */     }
/* 343 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean relaxedOffer(E e) {
/* 349 */     return offer(e);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int drain(MessagePassingQueue.Consumer<E> c, int limit) {
/* 355 */     if (null == c)
/* 356 */       throw new IllegalArgumentException("c is null"); 
/* 357 */     if (limit < 0)
/* 358 */       throw new IllegalArgumentException("limit is negative: " + limit); 
/* 359 */     if (limit == 0) {
/* 360 */       return 0;
/*     */     }
/* 362 */     LinkedQueueNode<E> chaserNode = lpConsumerNode();
/* 363 */     for (int i = 0; i < limit; i++) {
/*     */       
/* 365 */       LinkedQueueNode<E> nextNode = chaserNode.lvNext();
/*     */       
/* 367 */       if (nextNode == null)
/*     */       {
/* 369 */         return i;
/*     */       }
/*     */       
/* 372 */       E nextValue = getSingleConsumerNodeValue(chaserNode, nextNode);
/* 373 */       chaserNode = nextNode;
/* 374 */       c.accept(nextValue);
/*     */     } 
/* 376 */     return limit;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int drain(MessagePassingQueue.Consumer<E> c) {
/* 382 */     return MessagePassingQueueUtil.drain(this, c);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drain(MessagePassingQueue.Consumer<E> c, MessagePassingQueue.WaitStrategy wait, MessagePassingQueue.ExitCondition exit) {
/* 388 */     MessagePassingQueueUtil.drain(this, c, wait, exit);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int capacity() {
/* 394 */     return -1;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\shaded\org\jctools\queues\BaseLinkedQueue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */