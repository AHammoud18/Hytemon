/*    */ package com.hypixel.hytale.server.core.modules.interaction.interaction.operation;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.protocol.InteractionState;
/*    */ import com.hypixel.hytale.protocol.InteractionType;
/*    */ import com.hypixel.hytale.protocol.WaitForDataFrom;
/*    */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*    */ import com.hypixel.hytale.server.core.entity.LivingEntity;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class JumpOperation
/*    */   implements Operation
/*    */ {
/*    */   private final Label target;
/*    */   
/*    */   protected JumpOperation(Label target) {
/* 20 */     this.target = target;
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick(@Nonnull Ref<EntityStore> ref, @Nonnull LivingEntity entity, boolean firstRun, float time, @Nonnull InteractionType type, @Nonnull InteractionContext context, @Nonnull CooldownHandler cooldownHandler) {
/* 25 */     context.setOperationCounter(this.target.getIndex());
/* 26 */     (context.getState()).state = InteractionState.Finished;
/*    */   }
/*    */ 
/*    */   
/*    */   public void simulateTick(@Nonnull Ref<EntityStore> ref, @Nonnull LivingEntity entity, boolean firstRun, float time, @Nonnull InteractionType type, @Nonnull InteractionContext context, @Nonnull CooldownHandler cooldownHandler) {
/* 31 */     context.setOperationCounter(this.target.getIndex());
/* 32 */     (context.getState()).state = InteractionState.Finished;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public WaitForDataFrom getWaitForDataFrom() {
/* 38 */     return WaitForDataFrom.None;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 44 */     return "JumpOperation{target=" + String.valueOf(this.target) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\operation\JumpOperation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */