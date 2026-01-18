/*     */ package io.netty.util.internal.shaded.org.jctools.queues;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SpscLinkedQueue<E>
/*     */   extends BaseLinkedQueue<E>
/*     */ {
/*     */   public SpscLinkedQueue() {
/*  36 */     LinkedQueueNode<E> node = newNode();
/*  37 */     spProducerNode(node);
/*  38 */     spConsumerNode(node);
/*  39 */     node.soNext(null);
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
/*     */   public boolean offer(E e) {
/*  60 */     if (null == e)
/*     */     {
/*  62 */       throw new NullPointerException();
/*     */     }
/*  64 */     LinkedQueueNode<E> nextNode = newNode(e);
/*  65 */     LinkedQueueNode<E> oldNode = lpProducerNode();
/*  66 */     soProducerNode(nextNode);
/*     */ 
/*     */ 
/*     */     
/*  70 */     oldNode.soNext(nextNode);
/*  71 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int fill(MessagePassingQueue.Supplier<E> s) {
/*  77 */     return MessagePassingQueueUtil.fillUnbounded(this, s);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int fill(MessagePassingQueue.Supplier<E> s, int limit) {
/*  83 */     if (null == s)
/*  84 */       throw new IllegalArgumentException("supplier is null"); 
/*  85 */     if (limit < 0)
/*  86 */       throw new IllegalArgumentException("limit is negative:" + limit); 
/*  87 */     if (limit == 0) {
/*  88 */       return 0;
/*     */     }
/*  90 */     LinkedQueueNode<E> tail = newNode(s.get());
/*  91 */     LinkedQueueNode<E> head = tail;
/*  92 */     for (int i = 1; i < limit; i++) {
/*     */       
/*  94 */       LinkedQueueNode<E> temp = newNode(s.get());
/*     */       
/*  96 */       tail.spNext(temp);
/*  97 */       tail = temp;
/*     */     } 
/*  99 */     LinkedQueueNode<E> oldPNode = lpProducerNode();
/* 100 */     soProducerNode(tail);
/*     */     
/* 102 */     oldPNode.soNext(head);
/* 103 */     return limit;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void fill(MessagePassingQueue.Supplier<E> s, MessagePassingQueue.WaitStrategy wait, MessagePassingQueue.ExitCondition exit) {
/* 109 */     MessagePassingQueueUtil.fill(this, s, wait, exit);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\shaded\org\jctools\queues\SpscLinkedQueue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */