/*    */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config.client;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.protocol.Interaction;
/*    */ import com.hypixel.hytale.protocol.InteractionState;
/*    */ import com.hypixel.hytale.protocol.InteractionSyncData;
/*    */ import com.hypixel.hytale.protocol.InteractionType;
/*    */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CooldownConditionInteraction
/*    */   extends SimpleInstantInteraction
/*    */ {
/*    */   @Nonnull
/*    */   public static final BuilderCodec<CooldownConditionInteraction> CODEC;
/*    */   private String cooldown;
/*    */   
/*    */   static {
/* 37 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(CooldownConditionInteraction.class, CooldownConditionInteraction::new, SimpleInstantInteraction.CODEC).documentation("Checks if a given cooldown is complete.")).appendInherited(new KeyedCodec("Id", (Codec)Codec.STRING), (o, i) -> o.cooldown = i, o -> o.cooldown, (o, p) -> o.cooldown = p.cooldown).documentation("The ID of the cooldown to check for in this condition.").addValidator(Validators.nonNull()).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void firstRun(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nonnull CooldownHandler cooldownHandler) {
/* 46 */     assert this.cooldown != null;
/*    */     
/* 48 */     InteractionSyncData state = context.getState();
/*    */     
/* 50 */     CommandBuffer<EntityStore> commandBuffer = context.getCommandBuffer();
/* 51 */     assert commandBuffer != null;
/*    */     
/* 53 */     Ref<EntityStore> ref = context.getEntity();
/* 54 */     Player playerComponent = (Player)commandBuffer.getComponent(ref, Player.getComponentType());
/*    */     
/* 56 */     if (playerComponent == null && context.getClientState() != null) {
/* 57 */       state.state = (context.getClientState()).state;
/*    */       
/*    */       return;
/*    */     } 
/* 61 */     if (checkCooldown(cooldownHandler, this.cooldown)) {
/* 62 */       state.state = InteractionState.Failed;
/*    */     } else {
/* 64 */       state.state = InteractionState.Finished;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean checkCooldown(@Nonnull CooldownHandler cooldownHandler, @Nonnull String cooldownId) {
/* 76 */     CooldownHandler.Cooldown cooldown = cooldownHandler.getCooldown(cooldownId);
/* 77 */     return (cooldown != null && cooldown.hasCooldown(false));
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   protected Interaction generatePacket() {
/* 83 */     return (Interaction)new com.hypixel.hytale.protocol.CooldownConditionInteraction();
/*    */   }
/*    */ 
/*    */   
/*    */   protected void configurePacket(Interaction packet) {
/* 88 */     super.configurePacket(packet);
/* 89 */     com.hypixel.hytale.protocol.CooldownConditionInteraction p = (com.hypixel.hytale.protocol.CooldownConditionInteraction)packet;
/* 90 */     p.cooldownId = this.cooldown;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 96 */     return "CooldownConditionInteraction{} " + super
/* 97 */       .toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\client\CooldownConditionInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */