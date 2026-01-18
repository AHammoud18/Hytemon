/*    */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config.data;
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
/*    */ public class Node<T>
/*    */ {
/* 63 */   public static final Node[] EMPTY_ARRAY = new Node[0];
/*    */   
/*    */   private final Node<T> parent;
/*    */   
/* 67 */   private Node<T>[] children = (Node<T>[])EMPTY_ARRAY;
/*    */   
/*    */   private T data;
/*    */ 
/*    */   
/*    */   Node(Node<T> parent) {
/* 73 */     this.parent = parent;
/*    */   }
/*    */   
/*    */   public Node<T> getParent() {
/* 77 */     return this.parent;
/*    */   }
/*    */   
/*    */   public Node<T>[] getChildren() {
/* 81 */     return this.children;
/*    */   }
/*    */   
/*    */   public T getData() {
/* 85 */     return this.data;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\data\TreeCollector$Node.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */