/*    */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config.data;
/*    */ 
/*    */ import com.hypixel.hytale.function.function.TriFunction;
/*    */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction;
/*    */ import java.util.Arrays;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TreeCollector<T>
/*    */   implements Collector
/*    */ {
/*    */   private final TriFunction<CollectorTag, InteractionContext, Interaction, T> function;
/*    */   private Node<T> root;
/*    */   private Node<T> current;
/*    */   
/*    */   public TreeCollector(TriFunction<CollectorTag, InteractionContext, Interaction, T> function) {
/* 22 */     this.function = function;
/*    */   }
/*    */   
/*    */   public Node<T> getRoot() {
/* 26 */     return this.root;
/*    */   }
/*    */ 
/*    */   
/*    */   public void start() {
/* 31 */     this.root = new Node<>(null);
/* 32 */     this.current = this.root;
/*    */   }
/*    */ 
/*    */   
/*    */   public void into(@Nonnull InteractionContext context, Interaction interaction) {
/* 37 */     if (this.current.children != null) {
/* 38 */       this.current.children = Arrays.<Node<T>>copyOf(this.current.children, this.current.children.length + 1);
/*    */     } else {
/* 40 */       this.current.children = (Node<T>[])new Node[1];
/*    */     } 
/* 42 */     this.current = this.current.children[this.current.children.length - 1] = new Node<>(this.current);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean collect(@Nonnull CollectorTag tag, @Nonnull InteractionContext context, @Nonnull Interaction interaction) {
/* 47 */     this.current.data = (T)this.function.apply(tag, context, interaction);
/* 48 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public void outof() {
/* 53 */     this.current = this.current.parent;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void finished() {}
/*    */ 
/*    */   
/*    */   public static class Node<T>
/*    */   {
/* 63 */     public static final Node[] EMPTY_ARRAY = new Node[0];
/*    */     
/*    */     private final Node<T> parent;
/*    */     
/* 67 */     private Node<T>[] children = (Node<T>[])EMPTY_ARRAY;
/*    */     
/*    */     private T data;
/*    */ 
/*    */     
/*    */     Node(Node<T> parent) {
/* 73 */       this.parent = parent;
/*    */     }
/*    */     
/*    */     public Node<T> getParent() {
/* 77 */       return this.parent;
/*    */     }
/*    */     
/*    */     public Node<T>[] getChildren() {
/* 81 */       return this.children;
/*    */     }
/*    */     
/*    */     public T getData() {
/* 85 */       return this.data;
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\data\TreeCollector.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */