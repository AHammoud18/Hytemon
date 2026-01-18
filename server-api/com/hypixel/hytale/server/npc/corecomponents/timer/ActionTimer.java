/*     */ package com.hypixel.hytale.server.npc.corecomponents.timer;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.ActionBase;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderActionBase;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.timer.builders.BuilderActionTimer;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.timer.builders.BuilderActionTimerContinue;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.timer.builders.BuilderActionTimerModify;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.timer.builders.BuilderActionTimerPause;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.timer.builders.BuilderActionTimerRestart;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.timer.builders.BuilderActionTimerStart;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.timer.builders.BuilderActionTimerStop;
/*     */ import com.hypixel.hytale.server.npc.role.Role;
/*     */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*     */ import com.hypixel.hytale.server.npc.util.Timer;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class ActionTimer extends ActionBase {
/*     */   protected final Timer timer;
/*     */   protected final Timer.TimerAction action;
/*     */   protected double minStartValue;
/*     */   protected double maxStartValue;
/*     */   protected double minRestartValue;
/*     */   
/*     */   public ActionTimer(@Nonnull BuilderActionTimer builderActionTimer, @Nonnull BuilderSupport builderSupport) {
/*  28 */     super((BuilderActionBase)builderActionTimer);
/*  29 */     this.timer = builderActionTimer.getTimer(builderSupport);
/*  30 */     this.action = builderActionTimer.getTimerAction();
/*     */   }
/*     */   protected double maxValue; protected double rate; protected double increaseValue; protected boolean modifyRepeating; protected boolean repeating;
/*     */   public ActionTimer(@Nonnull BuilderActionTimerStart builderActionTimerStart, @Nonnull BuilderSupport builderSupport) {
/*  34 */     this((BuilderActionTimer)builderActionTimerStart, builderSupport);
/*  35 */     double[] startValueRange = builderActionTimerStart.getStartValueRange(builderSupport);
/*  36 */     this.minStartValue = startValueRange[0];
/*  37 */     this.maxStartValue = startValueRange[1];
/*  38 */     double[] restartValueRange = builderActionTimerStart.getRestartValueRange(builderSupport);
/*  39 */     this.minRestartValue = restartValueRange[0];
/*  40 */     this.maxValue = restartValueRange[1];
/*  41 */     this.rate = builderActionTimerStart.getRate(builderSupport);
/*  42 */     this.repeating = builderActionTimerStart.isRepeating(builderSupport);
/*     */   }
/*     */   
/*     */   public ActionTimer(@Nonnull BuilderActionTimerModify builderActionTimerModify, @Nonnull BuilderSupport builderSupport) {
/*  46 */     this((BuilderActionTimer)builderActionTimerModify, builderSupport);
/*  47 */     this.increaseValue = builderActionTimerModify.getIncreaseValue(builderSupport);
/*  48 */     double[] restartValueRange = builderActionTimerModify.getRestartValueRange(builderSupport);
/*  49 */     this.minRestartValue = restartValueRange[0];
/*  50 */     this.maxValue = restartValueRange[1];
/*  51 */     this.rate = builderActionTimerModify.getRate(builderSupport);
/*  52 */     this.minStartValue = builderActionTimerModify.getSetValue(builderSupport);
/*  53 */     this.repeating = builderActionTimerModify.isRepeating(builderSupport);
/*  54 */     this.modifyRepeating = builderActionTimerModify.isModifyRepeating();
/*     */   }
/*     */   
/*     */   public ActionTimer(BuilderActionTimerPause builderActionTimerPause, @Nonnull BuilderSupport builderSupport) {
/*  58 */     this((BuilderActionTimer)builderActionTimerPause, builderSupport);
/*     */   }
/*     */   
/*     */   public ActionTimer(BuilderActionTimerStop builderActionTimerStop, @Nonnull BuilderSupport builderSupport) {
/*  62 */     this((BuilderActionTimer)builderActionTimerStop, builderSupport);
/*     */   }
/*     */   
/*     */   public ActionTimer(BuilderActionTimerContinue builderActionTimerContinue, @Nonnull BuilderSupport builderSupport) {
/*  66 */     this((BuilderActionTimer)builderActionTimerContinue, builderSupport);
/*     */   }
/*     */   
/*     */   public ActionTimer(BuilderActionTimerRestart builderActionTimerRestart, @Nonnull BuilderSupport builderSupport) {
/*  70 */     this((BuilderActionTimer)builderActionTimerRestart, builderSupport);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/*  75 */     super.execute(ref, role, sensorInfo, dt, store);
/*  76 */     switch (this.action) { case START:
/*  77 */         executeStartAction(); break;
/*  78 */       case PAUSE: executePauseAction(); break;
/*  79 */       case STOP: executeStopAction(); break;
/*  80 */       case MODIFY: executeModifyAction(); break;
/*  81 */       case CONTINUE: executeContinueAction(); break;
/*  82 */       case RESTART: executeRestartAction(); break; }
/*     */     
/*  84 */     return true;
/*     */   }
/*     */   
/*     */   protected void executeRestartAction() {
/*  88 */     if (this.timer.isInitialised()) this.timer.restart(); 
/*     */   }
/*     */   
/*     */   protected void executeModifyAction() {
/*  92 */     if (this.timer.isInitialised() && !this.timer.isStopped()) {
/*  93 */       if (this.minRestartValue > 0.0D) this.timer.setMinRestartValue(this.minRestartValue); 
/*  94 */       if (this.maxValue > 0.0D) this.timer.setMaxValue(this.maxValue); 
/*  95 */       if (this.minStartValue > 0.0D) this.timer.setValue(this.minStartValue); 
/*  96 */       if (this.increaseValue > 0.0D) this.timer.addValue(this.increaseValue); 
/*  97 */       if (this.rate > 0.0D) this.timer.setRate(this.rate); 
/*  98 */       if (this.modifyRepeating) this.timer.setRepeating(this.repeating); 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void executeContinueAction() {
/* 103 */     if (this.timer.isInitialised()) this.timer.resume(); 
/*     */   }
/*     */   
/*     */   protected void executePauseAction() {
/* 107 */     if (this.timer.isInitialised()) this.timer.pause(); 
/*     */   }
/*     */   
/*     */   protected void executeStopAction() {
/* 111 */     if (this.timer.isInitialised()) this.timer.stop(); 
/*     */   }
/*     */   
/*     */   protected void executeStartAction() {
/* 115 */     if (!this.timer.isInitialised()) this.timer.start(this.minStartValue, this.maxStartValue, this.minRestartValue, this.maxValue, this.rate, this.repeating); 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\timer\ActionTimer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */