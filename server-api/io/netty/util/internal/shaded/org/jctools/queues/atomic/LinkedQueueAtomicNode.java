/*    */ package io.netty.util.internal.shaded.org.jctools.queues.atomic;
/*    */ 
/*    */ import java.util.concurrent.atomic.AtomicReference;
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
/*    */ public final class LinkedQueueAtomicNode<E>
/*    */   extends AtomicReference<LinkedQueueAtomicNode<E>>
/*    */ {
/*    */   private static final long serialVersionUID = 2404266111789071508L;
/*    */   private E value;
/*    */   
/*    */   public LinkedQueueAtomicNode() {}
/*    */   
/*    */   public LinkedQueueAtomicNode(E val) {
/* 33 */     spValue(val);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public E getAndNullValue() {
/* 43 */     E temp = lpValue();
/* 44 */     spValue((E)null);
/* 45 */     return temp;
/*    */   }
/*    */ 
/*    */   
/*    */   public E lpValue() {
/* 50 */     return this.value;
/*    */   }
/*    */ 
/*    */   
/*    */   public void spValue(E newValue) {
/* 55 */     this.value = newValue;
/*    */   }
/*    */ 
/*    */   
/*    */   public void soNext(LinkedQueueAtomicNode<E> n) {
/* 60 */     lazySet(n);
/*    */   }
/*    */ 
/*    */   
/*    */   public void spNext(LinkedQueueAtomicNode<E> n) {
/* 65 */     lazySet(n);
/*    */   }
/*    */ 
/*    */   
/*    */   public LinkedQueueAtomicNode<E> lvNext() {
/* 70 */     return get();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\shaded\org\jctools\queues\atomic\LinkedQueueAtomicNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */