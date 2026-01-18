/*    */ package com.hypixel.hytale.builtin.hytalegenerator;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.positionproviders.PositionProvider;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.propdistributions.Assignments;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PropField
/*    */ {
/*    */   @Nonnull
/*    */   private final Assignments assignments;
/*    */   @Nonnull
/*    */   private final PositionProvider positionProvider;
/*    */   private final int runtime;
/*    */   
/*    */   public PropField(int runtime, @Nonnull Assignments assignments, @Nonnull PositionProvider positionProvider) {
/* 18 */     this.runtime = runtime;
/* 19 */     this.assignments = assignments;
/* 20 */     this.positionProvider = positionProvider;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public PositionProvider getPositionProvider() {
/* 25 */     return this.positionProvider;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public Assignments getPropDistribution() {
/* 30 */     return this.assignments;
/*    */   }
/*    */   
/*    */   public int getRuntime() {
/* 34 */     return this.runtime;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\PropField.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */