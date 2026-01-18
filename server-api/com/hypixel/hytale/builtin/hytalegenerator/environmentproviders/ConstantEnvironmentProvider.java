/*    */ package com.hypixel.hytale.builtin.hytalegenerator.environmentproviders;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ConstantEnvironmentProvider extends EnvironmentProvider {
/*    */   private final int value;
/*    */   
/*    */   public ConstantEnvironmentProvider(int value) {
/*  9 */     this.value = value;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getValue(@Nonnull EnvironmentProvider.Context context) {
/* 14 */     return this.value;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\environmentproviders\ConstantEnvironmentProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */