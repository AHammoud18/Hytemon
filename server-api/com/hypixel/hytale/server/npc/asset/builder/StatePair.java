/*    */ package com.hypixel.hytale.server.npc.asset.builder;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StatePair
/*    */ {
/*    */   private final String fullStateName;
/*    */   private final int state;
/*    */   private final int subState;
/*    */   
/*    */   public StatePair(String fullStateName, int state, int subState) {
/* 12 */     this.fullStateName = fullStateName;
/* 13 */     this.state = state;
/* 14 */     this.subState = subState;
/*    */   }
/*    */   
/*    */   public String getFullStateName() {
/* 18 */     return this.fullStateName;
/*    */   }
/*    */   
/*    */   public int getState() {
/* 22 */     return this.state;
/*    */   }
/*    */   
/*    */   public int getSubState() {
/* 26 */     return this.subState;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\StatePair.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */