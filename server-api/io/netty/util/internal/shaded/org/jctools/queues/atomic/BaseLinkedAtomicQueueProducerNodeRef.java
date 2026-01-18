/*     */ package io.netty.util.internal.shaded.org.jctools.queues.atomic;
/*     */ 
/*     */ import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class BaseLinkedAtomicQueueProducerNodeRef<E>
/*     */   extends BaseLinkedAtomicQueuePad0<E>
/*     */ {
/*  83 */   private static final AtomicReferenceFieldUpdater<BaseLinkedAtomicQueueProducerNodeRef, LinkedQueueAtomicNode> P_NODE_UPDATER = AtomicReferenceFieldUpdater.newUpdater(BaseLinkedAtomicQueueProducerNodeRef.class, LinkedQueueAtomicNode.class, "producerNode");
/*     */   
/*     */   private volatile LinkedQueueAtomicNode<E> producerNode;
/*     */   
/*     */   final void spProducerNode(LinkedQueueAtomicNode<E> newValue) {
/*  88 */     P_NODE_UPDATER.lazySet(this, newValue);
/*     */   }
/*     */   
/*     */   final void soProducerNode(LinkedQueueAtomicNode<E> newValue) {
/*  92 */     P_NODE_UPDATER.lazySet(this, newValue);
/*     */   }
/*     */   
/*     */   final LinkedQueueAtomicNode<E> lvProducerNode() {
/*  96 */     return this.producerNode;
/*     */   }
/*     */   
/*     */   final boolean casProducerNode(LinkedQueueAtomicNode<E> expect, LinkedQueueAtomicNode<E> newValue) {
/* 100 */     return P_NODE_UPDATER.compareAndSet(this, expect, newValue);
/*     */   }
/*     */   
/*     */   final LinkedQueueAtomicNode<E> lpProducerNode() {
/* 104 */     return this.producerNode;
/*     */   }
/*     */   
/*     */   protected final LinkedQueueAtomicNode<E> xchgProducerNode(LinkedQueueAtomicNode<E> newValue) {
/* 108 */     return P_NODE_UPDATER.getAndSet(this, newValue);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\shaded\org\jctools\queues\atomic\BaseLinkedAtomicQueueProducerNodeRef.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */