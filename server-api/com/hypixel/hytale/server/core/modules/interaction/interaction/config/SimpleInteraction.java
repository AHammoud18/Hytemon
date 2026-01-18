/*     */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config;
/*     */ 
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.validation.LateValidator;
/*     */ import com.hypixel.hytale.protocol.Interaction;
/*     */ import com.hypixel.hytale.protocol.InteractionState;
/*     */ import com.hypixel.hytale.protocol.InteractionType;
/*     */ import com.hypixel.hytale.protocol.WaitForDataFrom;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionManager;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.data.Collector;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.data.CollectorTag;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.data.StringTag;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.operation.Label;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.operation.OperationsBuilder;
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
/*     */ public class SimpleInteraction
/*     */   extends Interaction
/*     */ {
/*     */   public static final BuilderCodec<SimpleInteraction> CODEC;
/*     */   
/*     */   static {
/*  59 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(SimpleInteraction.class, SimpleInteraction::new, Interaction.ABSTRACT_CODEC).documentation("A interaction that does nothing other than base interaction features. Can be used for simple delays or triggering animations in between other interactions.")).appendInherited(new KeyedCodec("Next", Interaction.CHILD_ASSET_CODEC), (interaction, s) -> interaction.next = s, interaction -> interaction.next, (interaction, parent) -> interaction.next = parent.next).documentation("The interactions to run when this interaction succeeds.").addValidatorLate(() -> VALIDATOR_CACHE.getValidator().late()).add()).appendInherited(new KeyedCodec("Failed", Interaction.CHILD_ASSET_CODEC), (interaction, s) -> interaction.failed = s, interaction -> interaction.failed, (interaction, parent) -> interaction.failed = parent.failed).documentation("The interactions to run when this interaction fails.").addValidatorLate(() -> VALIDATOR_CACHE.getValidator().late()).add()).build();
/*     */   }
/*  61 */   private static final StringTag TAG_NEXT = StringTag.of("Next");
/*  62 */   private static final StringTag TAG_FAILED = StringTag.of("Failed");
/*     */   
/*     */   private static final int FAILED_LABEL_INDEX = 0;
/*     */   
/*     */   @Nullable
/*     */   protected String next;
/*     */   
/*     */   @Nullable
/*     */   protected String failed;
/*     */   
/*     */   protected SimpleInteraction() {}
/*     */   
/*     */   public SimpleInteraction(String id) {
/*  75 */     super(id);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public WaitForDataFrom getWaitForDataFrom() {
/*  81 */     return WaitForDataFrom.None;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void tick0(boolean firstRun, float time, @NonNullDecl InteractionType type, @Nonnull InteractionContext context, @NonNullDecl CooldownHandler cooldownHandler) {
/*  86 */     if ((context.getState()).state == InteractionState.Failed && context.hasLabels()) {
/*  87 */       context.jump(context.getLabel(0));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void simulateTick0(boolean firstRun, float time, @NonNullDecl InteractionType type, @Nonnull InteractionContext context, @NonNullDecl CooldownHandler cooldownHandler) {
/*  95 */     if (getWaitForDataFrom() == WaitForDataFrom.Server && context.getServerState() != null && (context.getServerState()).state == InteractionState.Failed) {
/*  96 */       (context.getState()).state = InteractionState.Failed;
/*     */     }
/*     */     
/*  99 */     tick0(firstRun, time, type, context, cooldownHandler);
/*     */   }
/*     */ 
/*     */   
/*     */   public void compile(@Nonnull OperationsBuilder builder) {
/* 104 */     if (this.next == null && this.failed == null) {
/* 105 */       builder.addOperation(this);
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 111 */     Label failedLabel = builder.createUnresolvedLabel();
/* 112 */     Label endLabel = builder.createUnresolvedLabel();
/*     */     
/* 114 */     builder.addOperation(this, new Label[] { failedLabel });
/*     */ 
/*     */     
/* 117 */     if (this.next != null) {
/* 118 */       Interaction nextInteraction = Interaction.getInteractionOrUnknown(this.next);
/* 119 */       nextInteraction.compile(builder);
/*     */     } 
/*     */     
/* 122 */     if (this.failed != null) builder.jump(endLabel);
/*     */ 
/*     */     
/* 125 */     builder.resolveLabel(failedLabel);
/* 126 */     if (this.failed != null) {
/* 127 */       Interaction failedInteraction = Interaction.getInteractionOrUnknown(this.failed);
/* 128 */       failedInteraction.compile(builder);
/*     */     } 
/*     */     
/* 131 */     builder.resolveLabel(endLabel);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean walk(@Nonnull Collector collector, @Nonnull InteractionContext context) {
/* 136 */     if (this.next != null && 
/* 137 */       InteractionManager.walkInteraction(collector, context, (CollectorTag)TAG_NEXT, this.next)) return true;
/*     */     
/* 139 */     if (this.failed != null && 
/* 140 */       InteractionManager.walkInteraction(collector, context, (CollectorTag)TAG_FAILED, this.failed)) return true;
/*     */     
/* 142 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNullDecl
/*     */   protected Interaction generatePacket() {
/* 148 */     return (Interaction)new com.hypixel.hytale.protocol.SimpleInteraction();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void configurePacket(Interaction packet) {
/* 153 */     super.configurePacket(packet);
/* 154 */     com.hypixel.hytale.protocol.SimpleInteraction p = (com.hypixel.hytale.protocol.SimpleInteraction)packet;
/* 155 */     p.next = Interaction.getInteractionIdOrUnknown(this.next);
/* 156 */     p.failed = Interaction.getInteractionIdOrUnknown(this.failed);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean needsRemoteSync() {
/* 161 */     return (needsRemoteSync(this.next) || needsRemoteSync(this.failed));
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 166 */     return "SimpleInteraction{next='" + this.next + "'failed='" + this.failed + "'} " + super
/*     */ 
/*     */       
/* 169 */       .toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\SimpleInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */