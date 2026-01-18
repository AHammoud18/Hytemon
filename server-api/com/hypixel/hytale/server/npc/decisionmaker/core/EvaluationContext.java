/*    */ package com.hypixel.hytale.server.npc.decisionmaker.core;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EvaluationContext
/*    */ {
/*    */   private double minimumUtility;
/*    */   private double minimumWeightCoefficient;
/*    */   private float predictability;
/*    */   private long lastUsedNanos;
/*    */   
/*    */   public double getMinimumUtility() {
/* 16 */     return this.minimumUtility;
/*    */   }
/*    */   
/*    */   public void setMinimumUtility(double minimumUtility) {
/* 20 */     if (minimumUtility >= 1.0D || minimumUtility < 0.0D) throw new IllegalArgumentException("Minimum utility must be greater than or equal to 0 and less than 1!"); 
/* 21 */     this.minimumUtility = minimumUtility;
/*    */   }
/*    */   
/*    */   public double getMinimumWeightCoefficient() {
/* 25 */     return this.minimumWeightCoefficient;
/*    */   }
/*    */   
/*    */   public void setMinimumWeightCoefficient(double minimumWeightCoefficient) {
/* 29 */     if (minimumWeightCoefficient < 0.0D) throw new IllegalArgumentException("Minimum weight coefficient must be greater than or equal to 0!"); 
/* 30 */     this.minimumWeightCoefficient = minimumWeightCoefficient;
/*    */   }
/*    */   
/*    */   public float getPredictability() {
/* 34 */     return this.predictability;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setPredictability(float predictability) {
/* 43 */     if (predictability > 1.0F || predictability < 0.0F) throw new IllegalArgumentException("Predictability must be a value between 0 and 1!"); 
/* 44 */     this.predictability = predictability;
/*    */   }
/*    */   
/*    */   public long getLastUsedNanos() {
/* 48 */     return this.lastUsedNanos;
/*    */   }
/*    */   
/*    */   public void setLastUsedNanos(long lastUsedNanos) {
/* 52 */     this.lastUsedNanos = lastUsedNanos;
/*    */   }
/*    */   
/*    */   public void reset() {
/* 56 */     this.minimumUtility = 0.0D;
/* 57 */     this.minimumWeightCoefficient = 0.0D;
/* 58 */     this.predictability = 1.0F;
/* 59 */     this.lastUsedNanos = Evaluator.NOT_USED;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\decisionmaker\core\EvaluationContext.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */