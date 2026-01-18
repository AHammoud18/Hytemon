/*     */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config.client;
/*     */ 
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.system.EcsEvent;
/*     */ import com.hypixel.hytale.event.IBaseEvent;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.protocol.Interaction;
/*     */ import com.hypixel.hytale.protocol.InteractionState;
/*     */ import com.hypixel.hytale.protocol.InteractionType;
/*     */ import com.hypixel.hytale.server.core.HytaleServer;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*     */ import com.hypixel.hytale.server.core.event.events.ecs.UseBlockEvent;
/*     */ import com.hypixel.hytale.server.core.event.events.entity.LivingEntityUseBlockEvent;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.RootInteraction;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ public class UseBlockInteraction
/*     */   extends SimpleBlockInteraction
/*     */ {
/*     */   @Nonnull
/*  31 */   public static final BuilderCodec<UseBlockInteraction> CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(UseBlockInteraction.class, UseBlockInteraction::new, SimpleBlockInteraction.CODEC)
/*  32 */     .documentation("Attempts to use the target block, executing interactions on it if any."))
/*  33 */     .build();
/*     */ 
/*     */ 
/*     */   
/*     */   protected void interactWithBlock(@Nonnull World world, @Nonnull CommandBuffer<EntityStore> commandBuffer, @Nonnull InteractionType type, @Nonnull InteractionContext context, @Nullable ItemStack itemInHand, @Nonnull Vector3i targetBlock, @Nonnull CooldownHandler cooldownHandler) {
/*  38 */     doInteraction(type, context, world, targetBlock, true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void simulateInteractWithBlock(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nullable ItemStack itemInHand, @Nonnull World world, @Nonnull Vector3i targetBlock) {
/*  44 */     doInteraction(type, context, world, targetBlock, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void doInteraction(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nonnull World world, @Nonnull Vector3i targetBlock, boolean fireEvent) {
/*  63 */     BlockType blockType = world.getBlockType(targetBlock);
/*  64 */     String blockTypeInteraction = (String)blockType.getInteractions().get(type);
/*  65 */     if (blockTypeInteraction == null) {
/*  66 */       (context.getState()).state = InteractionState.Failed;
/*     */       
/*     */       return;
/*     */     } 
/*  70 */     Ref<EntityStore> ref = context.getEntity();
/*  71 */     CommandBuffer<EntityStore> commandBuffer = context.getCommandBuffer();
/*  72 */     assert commandBuffer != null;
/*     */     
/*  74 */     if (fireEvent) {
/*  75 */       UseBlockEvent.Pre event = new UseBlockEvent.Pre(type, context, targetBlock, blockType);
/*  76 */       commandBuffer.invoke(ref, (EcsEvent)event);
/*     */       
/*  78 */       if (event.isCancelled()) {
/*  79 */         (context.getState()).state = InteractionState.Failed;
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/*  84 */     (context.getState()).state = InteractionState.Finished;
/*  85 */     context.execute(RootInteraction.getRootInteractionOrUnknown(blockTypeInteraction));
/*     */     
/*  87 */     if (fireEvent) {
/*  88 */       UseBlockEvent.Post event = new UseBlockEvent.Post(type, context, targetBlock, blockType);
/*  89 */       commandBuffer.invoke(ref, (EcsEvent)event);
/*     */ 
/*     */       
/*  92 */       HytaleServer.get().getEventBus()
/*  93 */         .dispatchFor(LivingEntityUseBlockEvent.class, world.getName())
/*  94 */         .dispatch((IBaseEvent)new LivingEntityUseBlockEvent(context.getEntity(), blockType.getId()));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected Interaction generatePacket() {
/* 101 */     return (Interaction)new com.hypixel.hytale.protocol.UseBlockInteraction();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 107 */     return "UseBlockInteraction{} " + super.toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\client\UseBlockInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */