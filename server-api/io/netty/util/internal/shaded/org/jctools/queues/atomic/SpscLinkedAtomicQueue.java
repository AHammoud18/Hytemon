/*     */ package io.netty.util.internal.shaded.org.jctools.queues.atomic;
/*     */ 
/*     */ import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue;
/*     */ import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueueUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SpscLinkedAtomicQueue<E>
/*     */   extends BaseLinkedAtomicQueue<E>
/*     */ {
/*     */   public SpscLinkedAtomicQueue() {
/*  41 */     LinkedQueueAtomicNode<E> node = newNode();
/*  42 */     spProducerNode(node);
/*  43 */     spConsumerNode(node);
/*     */     
/*  45 */     node.soNext(null);
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
/*     */   public boolean offer(E e) {
/*  65 */     if (null == e) {
/*  66 */       throw new NullPointerException();
/*     */     }
/*  68 */     LinkedQueueAtomicNode<E> nextNode = newNode(e);
/*  69 */     LinkedQueueAtomicNode<E> oldNode = lpProducerNode();
/*  70 */     soProducerNode(nextNode);
/*     */ 
/*     */ 
/*     */     
/*  74 */     oldNode.soNext(nextNode);
/*  75 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int fill(MessagePassingQueue.Supplier<E> s) {
/*  80 */     return MessagePassingQueueUtil.fillUnbounded(this, s);
/*     */   }
/*     */ 
/*     */   
/*     */   public int fill(MessagePassingQueue.Supplier<E> s, int limit) {
/*  85 */     if (null == s)
/*  86 */       throw new IllegalArgumentException("supplier is null"); 
/*  87 */     if (limit < 0)
/*  88 */       throw new IllegalArgumentException("limit is negative:" + limit); 
/*  89 */     if (limit == 0)
/*  90 */       return 0; 
/*  91 */     LinkedQueueAtomicNode<E> tail = newNode((E)s.get());
/*  92 */     LinkedQueueAtomicNode<E> head = tail;
/*  93 */     for (int i = 1; i < limit; i++) {
/*  94 */       LinkedQueueAtomicNode<E> temp = newNode((E)s.get());
/*     */       
/*  96 */       tail.spNext(temp);
/*  97 */       tail = temp;
/*     */     } 
/*  99 */     LinkedQueueAtomicNode<E> oldPNode = lpProducerNode();
/* 100 */     soProducerNode(tail);
/*     */     
/* 102 */     oldPNode.soNext(head);
/* 103 */     return limit;
/*     */   }
/*     */ 
/*     */   
/*     */   public void fill(MessagePassingQueue.Supplier<E> s, MessagePassingQueue.WaitStrategy wait, MessagePassingQueue.ExitCondition exit) {
/* 108 */     MessagePassingQueueUtil.fill(this, s, wait, exit);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\shaded\org\jctools\queues\atomic\SpscLinkedAtomicQueue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */