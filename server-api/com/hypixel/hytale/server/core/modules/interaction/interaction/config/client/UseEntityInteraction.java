/*    */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config.client;
/*    */ 
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.protocol.Interaction;
/*    */ import com.hypixel.hytale.protocol.InteractionState;
/*    */ import com.hypixel.hytale.protocol.InteractionSyncData;
/*    */ import com.hypixel.hytale.protocol.InteractionType;
/*    */ import com.hypixel.hytale.protocol.WaitForDataFrom;
/*    */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.Interactions;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.RootInteraction;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class UseEntityInteraction
/*    */   extends SimpleInstantInteraction
/*    */ {
/*    */   @Nonnull
/* 25 */   public static final BuilderCodec<UseEntityInteraction> CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(UseEntityInteraction.class, UseEntityInteraction::new, SimpleInstantInteraction.CODEC)
/* 26 */     .documentation("Attempts to use the target entity, executing interactions on it if any."))
/* 27 */     .build();
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public WaitForDataFrom getWaitForDataFrom() {
/* 32 */     return WaitForDataFrom.Client;
/*    */   }
/*    */   
/*    */   protected final void firstRun(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nonnull CooldownHandler cooldownHandler) {
/*    */     String interaction;
/* 37 */     InteractionSyncData chainData = context.getClientState();
/* 38 */     assert chainData != null;
/*    */ 
/*    */     
/* 41 */     CommandBuffer<EntityStore> commandBuffer = context.getCommandBuffer();
/* 42 */     assert commandBuffer != null;
/*    */     
/* 44 */     Ref<EntityStore> targetRef = ((EntityStore)commandBuffer.getStore().getExternalData()).getRefFromNetworkId(chainData.entityId);
/* 45 */     if (targetRef == null || !targetRef.isValid()) {
/* 46 */       (context.getState()).state = InteractionState.Failed;
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 51 */     Interactions interactionsComponent = (Interactions)commandBuffer.getComponent(targetRef, Interactions.getComponentType());
/* 52 */     if (interactionsComponent != null) {
/* 53 */       interaction = interactionsComponent.getInteractionId(type);
/*    */     } else {
/* 55 */       (context.getState()).state = InteractionState.Failed;
/*    */       
/*    */       return;
/*    */     } 
/* 59 */     if (interaction == null) {
/* 60 */       (context.getState()).state = InteractionState.Failed;
/*    */       return;
/*    */     } 
/* 63 */     context.execute(RootInteraction.getRootInteractionOrUnknown(interaction));
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   protected Interaction generatePacket() {
/* 69 */     return (Interaction)new com.hypixel.hytale.protocol.UseEntityInteraction();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean needsRemoteSync() {
/* 74 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 80 */     return "UseEntityInteraction{} " + super.toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\client\UseEntityInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */