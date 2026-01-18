/*    */ package com.hypixel.hytale.server.npc.util;
/*    */ 
/*    */ import com.hypixel.hytale.common.thread.ticking.Tickable;
/*    */ import com.hypixel.hytale.server.core.asset.type.attitude.Attitude;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AttitudeMemoryEntry
/*    */   implements Tickable
/*    */ {
/*    */   private final Attitude attitudeOverride;
/*    */   private final double initialDuration;
/*    */   private double remainingDuration;
/*    */   
/*    */   public AttitudeMemoryEntry(Attitude attitudeOverride, double initialDuration) {
/* 17 */     this.attitudeOverride = attitudeOverride;
/* 18 */     this.initialDuration = initialDuration;
/* 19 */     this.remainingDuration = initialDuration;
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick(float dt) {
/* 24 */     this.remainingDuration = Math.max(this.remainingDuration - dt, 0.0D);
/*    */   }
/*    */   
/*    */   public double getRemainingDuration() {
/* 28 */     return this.remainingDuration;
/*    */   }
/*    */   
/*    */   public double getInitialDuration() {
/* 32 */     return this.initialDuration;
/*    */   }
/*    */   
/*    */   public Attitude getAttitudeOverride() {
/* 36 */     return this.attitudeOverride;
/*    */   }
/*    */   
/*    */   public boolean isExpired() {
/* 40 */     return (this.remainingDuration <= 0.0D);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\np\\util\AttitudeMemoryEntry.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */