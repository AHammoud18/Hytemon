/*     */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config.client;
/*     */ 
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.validation.LateValidator;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.protocol.Interaction;
/*     */ import com.hypixel.hytale.protocol.InteractionState;
/*     */ import com.hypixel.hytale.protocol.InteractionSyncData;
/*     */ import com.hypixel.hytale.protocol.InteractionType;
/*     */ import com.hypixel.hytale.protocol.WaitForDataFrom;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionManager;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.IInteractionSimulationHandler;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.data.Collector;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.data.CollectorTag;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.data.StringTag;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.operation.Label;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.operation.Operation;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.operation.OperationsBuilder;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
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
/*     */ 
/*     */ 
/*     */ public class FirstClickInteraction
/*     */   extends Interaction
/*     */ {
/*     */   @Nonnull
/*     */   public static final BuilderCodec<FirstClickInteraction> CODEC;
/*     */   
/*     */   static {
/*  53 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(FirstClickInteraction.class, FirstClickInteraction::new, Interaction.ABSTRACT_CODEC).documentation("An interaction that runs a different interaction based on if this chain was from a click or due to the key being held down.")).appendInherited(new KeyedCodec("Click", Interaction.CHILD_ASSET_CODEC), (interaction, s) -> interaction.click = s, interaction -> interaction.click, (interaction, parent) -> interaction.click = parent.click).documentation("The interaction to run if this chain was initiated by a click.").addValidatorLate(() -> VALIDATOR_CACHE.getValidator().late()).add()).appendInherited(new KeyedCodec("Held", Interaction.CHILD_ASSET_CODEC), (interaction, s) -> interaction.held = s, interaction -> interaction.held, (interaction, parent) -> interaction.held = parent.held).documentation("The interaction to run if this chain was initiated by holding down the key.").addValidatorLate(() -> VALIDATOR_CACHE.getValidator().late()).add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  59 */   public static final StringTag TAG_CLICK = StringTag.of("Click");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  65 */   public static final StringTag TAG_HELD = StringTag.of("Held");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int HELD_LABEL_INDEX = 0;
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected String click;
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected String held;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public WaitForDataFrom getWaitForDataFrom() {
/*  87 */     return WaitForDataFrom.Client;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void tick0(boolean firstRun, float time, @NonNullDecl InteractionType type, @Nonnull InteractionContext context, @NonNullDecl CooldownHandler cooldownHandler) {
/*  92 */     InteractionSyncData clientState = context.getClientState();
/*  93 */     assert clientState != null;
/*     */     
/*  95 */     if (clientState.state == InteractionState.Failed && context.hasLabels()) {
/*  96 */       (context.getState()).state = InteractionState.Failed;
/*  97 */       context.jump(context.getLabel(0));
/*     */       return;
/*     */     } 
/* 100 */     (context.getState()).state = InteractionState.Finished;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void simulateTick0(boolean firstRun, float time, @NonNullDecl InteractionType type, @Nonnull InteractionContext context, @NonNullDecl CooldownHandler cooldownHandler) {
/* 105 */     Ref<EntityStore> ref = context.getEntity();
/* 106 */     InteractionManager interactionManager = context.getInteractionManager();
/* 107 */     assert interactionManager != null;
/*     */     
/* 109 */     IInteractionSimulationHandler simulationHandler = interactionManager.getInteractionSimulationHandler();
/* 110 */     if (!simulationHandler.isCharging(firstRun, time, type, context, ref, cooldownHandler)) {
/* 111 */       (context.getState()).state = InteractionState.Finished;
/*     */     } else {
/* 113 */       (context.getState()).state = InteractionState.Failed;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void compile(@Nonnull OperationsBuilder builder) {
/* 119 */     if (this.click == null && this.held == null) {
/* 120 */       builder.addOperation((Operation)this);
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 126 */     Label failedLabel = builder.createUnresolvedLabel();
/* 127 */     Label endLabel = builder.createUnresolvedLabel();
/*     */     
/* 129 */     builder.addOperation((Operation)this, new Label[] { failedLabel });
/*     */ 
/*     */     
/* 132 */     if (this.click != null) {
/* 133 */       Interaction nextInteraction = Interaction.getInteractionOrUnknown(this.click);
/* 134 */       nextInteraction.compile(builder);
/*     */     } 
/*     */     
/* 137 */     if (this.held != null) {
/* 138 */       builder.jump(endLabel);
/*     */     }
/*     */ 
/*     */     
/* 142 */     builder.resolveLabel(failedLabel);
/* 143 */     if (this.held != null) {
/* 144 */       Interaction failedInteraction = Interaction.getInteractionOrUnknown(this.held);
/* 145 */       failedInteraction.compile(builder);
/*     */     } 
/*     */     
/* 148 */     builder.resolveLabel(endLabel);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean walk(@Nonnull Collector collector, @Nonnull InteractionContext context) {
/* 153 */     if (this.click != null && 
/* 154 */       InteractionManager.walkInteraction(collector, context, (CollectorTag)TAG_CLICK, this.click)) return true;
/*     */     
/* 156 */     if (this.held != null && 
/* 157 */       InteractionManager.walkInteraction(collector, context, (CollectorTag)TAG_HELD, this.held)) {
/* 158 */       return true;
/*     */     }
/*     */     
/* 161 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected Interaction generatePacket() {
/* 167 */     return (Interaction)new com.hypixel.hytale.protocol.FirstClickInteraction();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void configurePacket(Interaction packet) {
/* 172 */     super.configurePacket(packet);
/* 173 */     com.hypixel.hytale.protocol.FirstClickInteraction p = (com.hypixel.hytale.protocol.FirstClickInteraction)packet;
/* 174 */     p.click = Interaction.getInteractionIdOrUnknown(this.click);
/* 175 */     p.held = Interaction.getInteractionIdOrUnknown(this.held);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean needsRemoteSync() {
/* 180 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 186 */     return "FirstClickInteraction{click='" + this.click + "', held='" + this.held + "'} " + super
/*     */ 
/*     */       
/* 189 */       .toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\client\FirstClickInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */