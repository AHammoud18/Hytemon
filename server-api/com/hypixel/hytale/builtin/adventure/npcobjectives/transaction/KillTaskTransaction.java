/*    */ package com.hypixel.hytale.builtin.adventure.npcobjectives.transaction;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.adventure.npcobjectives.resources.KillTrackerResource;
/*    */ import com.hypixel.hytale.builtin.adventure.npcobjectives.task.KillTask;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.Objective;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.transaction.TransactionRecord;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class KillTaskTransaction
/*    */   extends TransactionRecord
/*    */ {
/*    */   @Nonnull
/*    */   private final KillTask task;
/*    */   @Nonnull
/*    */   private final Objective objective;
/*    */   @Nonnull
/*    */   private final ComponentAccessor<EntityStore> componentAccessor;
/*    */   
/*    */   public KillTaskTransaction(@Nonnull KillTask task, @Nonnull Objective objective, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 22 */     this.task = task;
/* 23 */     this.objective = objective;
/* 24 */     this.componentAccessor = componentAccessor;
/*    */   }
/*    */ 
/*    */   
/*    */   public void revert() {
/* 29 */     ((KillTrackerResource)this.componentAccessor.getResource(KillTrackerResource.getResourceType())).unwatch(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public void complete() {
/* 34 */     ((KillTrackerResource)this.componentAccessor.getResource(KillTrackerResource.getResourceType())).unwatch(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public void unload() {
/* 39 */     ((KillTrackerResource)this.componentAccessor.getResource(KillTrackerResource.getResourceType())).unwatch(this);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public KillTask getTask() {
/* 44 */     return this.task;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public Objective getObjective() {
/* 49 */     return this.objective;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldBeSerialized() {
/* 54 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\npcobjectives\transaction\KillTaskTransaction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */