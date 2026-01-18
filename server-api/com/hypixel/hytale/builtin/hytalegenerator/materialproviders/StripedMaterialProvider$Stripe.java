/*    */ package com.hypixel.hytale.builtin.hytalegenerator.materialproviders;
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
/*    */ public class Stripe
/*    */ {
/*    */   private final int topY;
/*    */   private final int bottomY;
/*    */   
/*    */   public Stripe(int topY, int bottomY) {
/* 39 */     this.topY = topY;
/* 40 */     this.bottomY = bottomY;
/*    */   }
/*    */   
/*    */   public boolean contains(int y) {
/* 44 */     return (y >= this.bottomY && y <= this.topY);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\materialproviders\StripedMaterialProvider$Stripe.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */