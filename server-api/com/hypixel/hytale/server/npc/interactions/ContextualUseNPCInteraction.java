/*     */ package com.hypixel.hytale.server.npc.interactions;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.protocol.InteractionState;
/*     */ import com.hypixel.hytale.protocol.InteractionType;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ContextualUseNPCInteraction
/*     */   extends SimpleInstantInteraction
/*     */ {
/*     */   @Nonnull
/*     */   public static final BuilderCodec<ContextualUseNPCInteraction> CODEC;
/*     */   protected String context;
/*     */   
/*     */   static {
/*  38 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ContextualUseNPCInteraction.class, ContextualUseNPCInteraction::new, SimpleInstantInteraction.CODEC).documentation("Interacts with the target NPC passing in context for it to use.")).appendInherited(new KeyedCodec("Context", (Codec)Codec.STRING), (interaction, s) -> interaction.context = s, interaction -> interaction.context, (interaction, parent) -> interaction.context = parent.context).documentation("The provided context for the use action.").addValidator(Validators.nonNull()).add()).build();
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
/*     */   public ContextualUseNPCInteraction(String id) {
/*  51 */     super(id);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ContextualUseNPCInteraction() {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void firstRun(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nonnull CooldownHandler cooldownHandler) {
/*  62 */     Ref<EntityStore> targetRef = context.getTargetEntity();
/*  63 */     if (targetRef == null) {
/*  64 */       (context.getState()).state = InteractionState.Failed;
/*     */       
/*     */       return;
/*     */     } 
/*  68 */     CommandBuffer<EntityStore> commandBuffer = context.getCommandBuffer();
/*  69 */     Ref<EntityStore> ref = context.getEntity();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  74 */     Player playerComponent = (Player)commandBuffer.getComponent(ref, Player.getComponentType());
/*  75 */     if (playerComponent == null) {
/*  76 */       HytaleLogger.getLogger().at(Level.INFO).log("UseNPCInteraction requires a Player but was used for: %s", ref);
/*  77 */       (context.getState()).state = InteractionState.Failed;
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  82 */     NPCEntity npcComponent = (NPCEntity)commandBuffer.getComponent(targetRef, NPCEntity.getComponentType());
/*  83 */     if (npcComponent == null) {
/*  84 */       HytaleLogger.getLogger().at(Level.INFO).log("UseNPCInteraction requires a target NPC");
/*  85 */       (context.getState()).state = InteractionState.Failed;
/*     */       
/*     */       return;
/*     */     } 
/*  89 */     if (!npcComponent.getRole().getStateSupport().willInteractWith(ref)) {
/*  90 */       (context.getState()).state = InteractionState.Failed;
/*     */       
/*     */       return;
/*     */     } 
/*  94 */     npcComponent.getRole().getStateSupport().addContextualInteraction(ref, this.context);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 100 */     return "ContextualUseNPCInteraction{} " + super.toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\interactions\ContextualUseNPCInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */