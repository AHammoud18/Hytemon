/*    */ package com.hypixel.hytale.server.core.modules.interaction;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.protocol.InteractionType;
/*    */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class InteractionSimulationHandler
/*    */   implements IInteractionSimulationHandler {
/* 12 */   private final boolean[] isDown = new boolean[InteractionType.VALUES.length];
/*    */ 
/*    */   
/*    */   public void setState(@Nonnull InteractionType type, boolean state) {
/* 16 */     this.isDown[type.ordinal()] = state;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isCharging(boolean firstRun, float time, @Nonnull InteractionType type, InteractionContext context, Ref<EntityStore> ref, CooldownHandler cooldownHandler) {
/* 23 */     return this.isDown[type.ordinal()];
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean shouldCancelCharging(boolean firstRun, float time, InteractionType type, InteractionContext context, Ref<EntityStore> ref, CooldownHandler cooldownHandler) {
/* 30 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public float getChargeValue(boolean firstRun, float time, InteractionType type, InteractionContext context, Ref<EntityStore> ref, CooldownHandler cooldownHandler) {
/* 37 */     return time;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\InteractionSimulationHandler.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */