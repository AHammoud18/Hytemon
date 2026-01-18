/*    */ package com.hypixel.hytale.server.npc.interactions;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.protocol.InteractionType;
/*    */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.IInteractionSimulationHandler;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NPCInteractionSimulationHandler
/*    */   implements IInteractionSimulationHandler
/*    */ {
/*    */   private float requestedChargeTime;
/*    */   
/*    */   public void setState(InteractionType type, boolean state) {}
/*    */   
/*    */   public boolean isCharging(boolean firstRun, float time, InteractionType type, InteractionContext context, Ref<EntityStore> ref, CooldownHandler cooldownHandler) {
/* 24 */     return (time < this.requestedChargeTime);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean shouldCancelCharging(boolean firstRun, float time, InteractionType type, InteractionContext context, Ref<EntityStore> ref, CooldownHandler cooldownHandler) {
/* 30 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public float getChargeValue(boolean firstRun, float time, InteractionType type, InteractionContext context, Ref<EntityStore> ref, CooldownHandler cooldownHandler) {
/* 36 */     return this.requestedChargeTime;
/*    */   }
/*    */   
/*    */   public void requestChargeTime(float chargeTime) {
/* 40 */     this.requestedChargeTime = chargeTime;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\interactions\NPCInteractionSimulationHandler.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */