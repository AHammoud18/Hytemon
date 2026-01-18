/*    */ package com.hypixel.hytale.builtin.hytalegenerator.tintproviders;
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
/*    */ public class Result
/*    */ {
/* 14 */   public static final Result WITHOUT_VALUE = new Result();
/*    */   
/*    */   public final int tint;
/*    */   public final boolean hasValue;
/*    */   
/*    */   public Result(int tint) {
/* 20 */     this.tint = tint;
/* 21 */     this.hasValue = true;
/*    */   }
/*    */   
/*    */   public Result() {
/* 25 */     this.tint = TintProvider.DEFAULT_TINT;
/* 26 */     this.hasValue = false;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\tintproviders\TintProvider$Result.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */