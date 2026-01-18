/*    */ package com.hypixel.hytale.builtin.beds.sleep.resources;
/*    */ 
/*    */ import com.hypixel.hytale.protocol.InstantData;
/*    */ import com.hypixel.hytale.protocol.packets.world.SleepClock;
/*    */ import com.hypixel.hytale.server.core.modules.time.WorldTimeResource;
/*    */ import java.time.Instant;
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class WorldSlumber
/*    */   implements WorldSleep
/*    */ {
/*    */   private final Instant startInstant;
/*    */   private final Instant targetInstant;
/*    */   private final InstantData startInstantData;
/*    */   private final InstantData targetInstantData;
/*    */   private final float irlDurationSeconds;
/* 18 */   private float progressSeconds = 0.0F;
/*    */   
/*    */   public WorldSlumber(Instant startInstant, Instant targetInstant, float irlDurationSeconds) {
/* 21 */     this.startInstant = startInstant;
/* 22 */     this.targetInstant = targetInstant;
/* 23 */     this.startInstantData = WorldTimeResource.instantToInstantData(startInstant);
/* 24 */     this.targetInstantData = WorldTimeResource.instantToInstantData(targetInstant);
/* 25 */     this.irlDurationSeconds = irlDurationSeconds;
/*    */   }
/*    */   
/*    */   public Instant getStartInstant() {
/* 29 */     return this.startInstant;
/*    */   }
/*    */   
/*    */   public Instant getTargetInstant() {
/* 33 */     return this.targetInstant;
/*    */   }
/*    */   
/*    */   public InstantData getStartInstantData() {
/* 37 */     return this.startInstantData;
/*    */   }
/*    */   
/*    */   public InstantData getTargetInstantData() {
/* 41 */     return this.targetInstantData;
/*    */   }
/*    */   
/*    */   public float getProgressSeconds() {
/* 45 */     return this.progressSeconds;
/*    */   }
/*    */   
/*    */   public void incProgressSeconds(float seconds) {
/* 49 */     this.progressSeconds += seconds;
/* 50 */     this.progressSeconds = Math.min(this.progressSeconds, this.irlDurationSeconds);
/*    */   }
/*    */   
/*    */   public float getIrlDurationSeconds() {
/* 54 */     return this.irlDurationSeconds;
/*    */   }
/*    */   
/*    */   public SleepClock createSleepClock() {
/* 58 */     float progress = this.progressSeconds / this.irlDurationSeconds;
/* 59 */     return new SleepClock(this.startInstantData, this.targetInstantData, progress, this.irlDurationSeconds);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\beds\sleep\resources\WorldSlumber.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */