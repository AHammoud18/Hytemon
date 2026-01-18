/*    */ package com.hypixel.hytale.builtin.buildertools.prefabeditor;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Tri<A, B, C>
/*    */ {
/*    */   private final A left;
/*    */   private final B middle;
/*    */   private final C right;
/*    */   
/*    */   public Tri(A left, B middle, C right) {
/* 12 */     this.left = left;
/* 13 */     this.middle = middle;
/* 14 */     this.right = right;
/*    */   }
/*    */   
/*    */   public A getLeft() {
/* 18 */     return this.left;
/*    */   }
/*    */   
/*    */   public B getMiddle() {
/* 22 */     return this.middle;
/*    */   }
/*    */   
/*    */   public C getRight() {
/* 26 */     return this.right;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\prefabeditor\Tri.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */