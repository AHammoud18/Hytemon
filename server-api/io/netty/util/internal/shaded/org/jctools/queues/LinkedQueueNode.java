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
/*    */ public final class LinkedQueueNode<E>
/*    */ {
/* 24 */   private static final long NEXT_OFFSET = UnsafeAccess.fieldOffset(LinkedQueueNode.class, "next");
/*    */   
/*    */   private E value;
/*    */   
/*    */   private volatile LinkedQueueNode<E> next;
/*    */   
/*    */   public LinkedQueueNode() {
/* 31 */     this(null);
/*    */   }
/*    */ 
/*    */   
/*    */   public LinkedQueueNode(E val) {
/* 36 */     spValue(val);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public E getAndNullValue() {
/* 46 */     E temp = lpValue();
/* 47 */     spValue(null);
/* 48 */     return temp;
/*    */   }
/*    */ 
/*    */   
/*    */   public E lpValue() {
/* 53 */     return this.value;
/*    */   }
/*    */ 
/*    */   
/*    */   public void spValue(E newValue) {
/* 58 */     this.value = newValue;
/*    */   }
/*    */ 
/*    */   
/*    */   public void soNext(LinkedQueueNode<E> n) {
/* 63 */     UnsafeAccess.UNSAFE.putOrderedObject(this, NEXT_OFFSET, n);
/*    */   }
/*    */ 
/*    */   
/*    */   public void spNext(LinkedQueueNode<E> n) {
/* 68 */     UnsafeAccess.UNSAFE.putObject(this, NEXT_OFFSET, n);
/*    */   }
/*    */ 
/*    */   
/*    */   public LinkedQueueNode<E> lvNext() {
/* 73 */     return this.next;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\shaded\org\jctools\queues\LinkedQueueNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */