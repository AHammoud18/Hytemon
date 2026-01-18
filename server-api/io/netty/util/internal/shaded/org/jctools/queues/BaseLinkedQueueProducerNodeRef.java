/*    */ package io.netty.util.internal.shaded.org.jctools.queues;
/*    */ 
/*    */ import io.netty.util.internal.shaded.org.jctools.util.UnsafeAccess;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ abstract class BaseLinkedQueueProducerNodeRef<E>
/*    */   extends BaseLinkedQueuePad0<E>
/*    */ {
/* 47 */   static final long P_NODE_OFFSET = UnsafeAccess.fieldOffset(BaseLinkedQueueProducerNodeRef.class, "producerNode");
/*    */   
/*    */   private volatile LinkedQueueNode<E> producerNode;
/*    */ 
/*    */   
/*    */   final void spProducerNode(LinkedQueueNode<E> newValue) {
/* 53 */     UnsafeAccess.UNSAFE.putObject(this, P_NODE_OFFSET, newValue);
/*    */   }
/*    */ 
/*    */   
/*    */   final void soProducerNode(LinkedQueueNode<E> newValue) {
/* 58 */     UnsafeAccess.UNSAFE.putOrderedObject(this, P_NODE_OFFSET, newValue);
/*    */   }
/*    */ 
/*    */   
/*    */   final LinkedQueueNode<E> lvProducerNode() {
/* 63 */     return this.producerNode;
/*    */   }
/*    */ 
/*    */   
/*    */   final boolean casProducerNode(LinkedQueueNode<E> expect, LinkedQueueNode<E> newValue) {
/* 68 */     return UnsafeAccess.UNSAFE.compareAndSwapObject(this, P_NODE_OFFSET, expect, newValue);
/*    */   }
/*    */ 
/*    */   
/*    */   final LinkedQueueNode<E> lpProducerNode() {
/* 73 */     return this.producerNode;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\shaded\org\jctools\queues\BaseLinkedQueueProducerNodeRef.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */