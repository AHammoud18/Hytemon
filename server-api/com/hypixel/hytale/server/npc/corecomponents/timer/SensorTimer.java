/*    */ package com.hypixel.hytale.server.npc.corecomponents.timer;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.SensorBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderSensorBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.timer.builders.BuilderSensorTimer;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import com.hypixel.hytale.server.npc.util.Timer;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class SensorTimer extends SensorBase {
/*    */   protected final double minTimeRemaining;
/*    */   protected final double maxTimeRemaining;
/*    */   protected final Timer timer;
/*    */   protected final Timer.TimerState timerState;
/*    */   
/*    */   public SensorTimer(@Nonnull BuilderSensorTimer builderSensorTimer, @Nonnull BuilderSupport builderSupport) {
/* 22 */     super((BuilderSensorBase)builderSensorTimer);
/* 23 */     this.timer = builderSensorTimer.getTimer(builderSupport);
/* 24 */     double[] timerThresholds = builderSensorTimer.getRemainingTimeRange(builderSupport);
/* 25 */     this.minTimeRemaining = timerThresholds[0];
/* 26 */     this.maxTimeRemaining = timerThresholds[1];
/* 27 */     this.timerState = builderSensorTimer.getTimerState();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, double dt, @Nonnull Store<EntityStore> store) {
/* 32 */     if (!super.matches(ref, role, dt, store)) return false;
/*    */     
/* 34 */     if (!this.timer.isInitialised()) {
/* 35 */       return ((this.timerState == Timer.TimerState.ANY || this.timerState == Timer.TimerState.STOPPED) && isBetween(0.0D));
/*    */     }
/* 37 */     return (this.timer.isInState(this.timerState) && isBetween(this.timer.getValue()));
/*    */   }
/*    */ 
/*    */   
/*    */   public InfoProvider getSensorInfo() {
/* 42 */     return null;
/*    */   }
/*    */   
/*    */   protected boolean isBetween(double value) {
/* 46 */     return (value >= this.minTimeRemaining && value <= this.maxTimeRemaining);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\timer\SensorTimer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */