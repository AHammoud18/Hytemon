/*    */ package com.hypixel.hytale.builtin.hytalegenerator.tintproviders;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NoTintProvider
/*    */   extends TintProvider
/*    */ {
/*    */   @Nonnull
/*    */   public TintProvider.Result getValue(@Nonnull TintProvider.Context context) {
/* 13 */     return TintProvider.Result.WITHOUT_VALUE;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\tintproviders\NoTintProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */