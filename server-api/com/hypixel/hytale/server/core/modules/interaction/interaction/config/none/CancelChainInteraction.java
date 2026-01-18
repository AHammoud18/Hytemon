/*    */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config.none;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.protocol.Interaction;
/*    */ import com.hypixel.hytale.protocol.InteractionType;
/*    */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.client.ChainingInteraction;
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
/*    */ 
/*    */ public class CancelChainInteraction
/*    */   extends SimpleInstantInteraction
/*    */ {
/*    */   @Nonnull
/*    */   public static final BuilderCodec<CancelChainInteraction> CODEC;
/*    */   protected String chainId;
/*    */   
/*    */   static {
/* 36 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(CancelChainInteraction.class, CancelChainInteraction::new, SimpleInstantInteraction.CODEC).documentation("Cancels an active chaining state for the given chain id.")).appendInherited(new KeyedCodec("ChainId", (Codec)Codec.STRING), (o, i) -> o.chainId = i, o -> o.chainId, (o, p) -> o.chainId = p.chainId).documentation("The ID of the chain to cancel.").addValidator(Validators.nonNull()).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   protected Interaction generatePacket() {
/* 46 */     return (Interaction)new com.hypixel.hytale.protocol.CancelChainInteraction();
/*    */   }
/*    */ 
/*    */   
/*    */   protected void configurePacket(Interaction packet) {
/* 51 */     super.configurePacket(packet);
/* 52 */     com.hypixel.hytale.protocol.CancelChainInteraction p = (com.hypixel.hytale.protocol.CancelChainInteraction)packet;
/* 53 */     p.chainId = this.chainId;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void firstRun(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nonnull CooldownHandler cooldownHandler) {}
/*    */ 
/*    */ 
/*    */   
/*    */   protected void simulateFirstRun(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nonnull CooldownHandler cooldownHandler) {
/* 63 */     Ref<EntityStore> ref = context.getEntity();
/* 64 */     CommandBuffer<EntityStore> commandBuffer = context.getCommandBuffer();
/* 65 */     ChainingInteraction.Data dataComponent = (ChainingInteraction.Data)commandBuffer.ensureAndGetComponent(ref, ChainingInteraction.Data.getComponentType());
/* 66 */     dataComponent.getNamedMap().removeInt(this.chainId);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\none\CancelChainInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */