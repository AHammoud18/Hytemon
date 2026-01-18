/*    */ package com.hypixel.hytale.builtin.adventure.objectives.completion;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.Objective;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.config.completion.ObjectiveCompletionAsset;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ObjectiveCompletion
/*    */ {
/*    */   @Nonnull
/*    */   protected final ObjectiveCompletionAsset asset;
/*    */   
/*    */   public ObjectiveCompletion(@Nonnull ObjectiveCompletionAsset asset) {
/* 20 */     this.asset = asset;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public ObjectiveCompletionAsset getAsset() {
/* 25 */     return this.asset;
/*    */   }
/*    */ 
/*    */   
/*    */   public abstract void handle(@Nonnull Objective paramObjective, @Nonnull ComponentAccessor<EntityStore> paramComponentAccessor);
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 33 */     return "ObjectiveCompletion{asset=" + String.valueOf(this.asset) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\completion\ObjectiveCompletion.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */