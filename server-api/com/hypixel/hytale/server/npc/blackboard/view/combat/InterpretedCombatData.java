/*    */ package com.hypixel.hytale.server.npc.blackboard.view.combat;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class InterpretedCombatData
/*    */ {
/*    */   private String attack;
/*    */   private boolean charging;
/*    */   private float currentElapsedTime;
/*    */   private boolean performingMeleeAttack;
/*    */   private boolean performingRangedAttack;
/*    */   private boolean performingBlock;
/*    */   
/*    */   public String getAttack() {
/* 18 */     return this.attack;
/*    */   }
/*    */   
/*    */   public void setAttack(String attack) {
/* 22 */     this.attack = attack;
/*    */   }
/*    */   
/*    */   public boolean isCharging() {
/* 26 */     return this.charging;
/*    */   }
/*    */   
/*    */   public void setCharging(boolean charging) {
/* 30 */     this.charging = charging;
/*    */   }
/*    */   
/*    */   public float getCurrentElapsedTime() {
/* 34 */     return this.currentElapsedTime;
/*    */   }
/*    */   
/*    */   public void setCurrentElapsedTime(float currentElapsedTime) {
/* 38 */     this.currentElapsedTime = currentElapsedTime;
/*    */   }
/*    */   
/*    */   public boolean isPerformingMeleeAttack() {
/* 42 */     return this.performingMeleeAttack;
/*    */   }
/*    */   
/*    */   public void setPerformingMeleeAttack(boolean performingMeleeAttack) {
/* 46 */     this.performingMeleeAttack = performingMeleeAttack;
/*    */   }
/*    */   
/*    */   public boolean isPerformingRangedAttack() {
/* 50 */     return this.performingRangedAttack;
/*    */   }
/*    */   
/*    */   public void setPerformingRangedAttack(boolean performingRangedAttack) {
/* 54 */     this.performingRangedAttack = performingRangedAttack;
/*    */   }
/*    */   
/*    */   public boolean isPerformingBlock() {
/* 58 */     return this.performingBlock;
/*    */   }
/*    */   
/*    */   public void setPerformingBlock(boolean performingBlock) {
/* 62 */     this.performingBlock = performingBlock;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public InterpretedCombatData clone() {
/* 69 */     InterpretedCombatData data = new InterpretedCombatData();
/* 70 */     data.attack = this.attack;
/* 71 */     data.charging = this.charging;
/* 72 */     data.currentElapsedTime = this.currentElapsedTime;
/* 73 */     data.performingMeleeAttack = this.performingMeleeAttack;
/* 74 */     data.performingRangedAttack = this.performingRangedAttack;
/* 75 */     data.performingBlock = this.performingBlock;
/* 76 */     return data;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\blackboard\view\combat\InterpretedCombatData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */