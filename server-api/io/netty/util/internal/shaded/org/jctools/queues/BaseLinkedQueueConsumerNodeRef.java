/*     */ package io.netty.util.internal.shaded.org.jctools.queues;
/*     */ 
/*     */ import io.netty.util.internal.shaded.org.jctools.util.UnsafeAccess;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class BaseLinkedQueueConsumerNodeRef<E>
/*     */   extends BaseLinkedQueuePad1<E>
/*     */ {
/* 100 */   private static final long C_NODE_OFFSET = UnsafeAccess.fieldOffset(BaseLinkedQueueConsumerNodeRef.class, "consumerNode");
/*     */   
/*     */   private LinkedQueueNode<E> consumerNode;
/*     */ 
/*     */   
/*     */   final void spConsumerNode(LinkedQueueNode<E> newValue) {
/* 106 */     this.consumerNode = newValue;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   final LinkedQueueNode<E> lvConsumerNode() {
/* 112 */     return (LinkedQueueNode<E>)UnsafeAccess.UNSAFE.getObjectVolatile(this, C_NODE_OFFSET);
/*     */   }
/*     */ 
/*     */   
/*     */   final LinkedQueueNode<E> lpConsumerNode() {
/* 117 */     return this.consumerNode;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\shaded\org\jctools\queues\BaseLinkedQueueConsumerNodeRef.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */