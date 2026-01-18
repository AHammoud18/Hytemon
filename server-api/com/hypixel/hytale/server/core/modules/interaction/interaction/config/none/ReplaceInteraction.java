/*     */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config.none;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.validation.LateValidator;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.protocol.Interaction;
/*     */ import com.hypixel.hytale.protocol.InteractionState;
/*     */ import com.hypixel.hytale.protocol.InteractionType;
/*     */ import com.hypixel.hytale.protocol.WaitForDataFrom;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionManager;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.RootInteraction;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.data.Collector;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.data.CollectorTag;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.data.StringTag;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.logging.Level;
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
/*     */ public class ReplaceInteraction
/*     */   extends Interaction
/*     */ {
/*     */   @Nonnull
/*     */   public static final BuilderCodec<ReplaceInteraction> CODEC;
/*     */   
/*     */   static {
/*  55 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ReplaceInteraction.class, ReplaceInteraction::new, Interaction.ABSTRACT_CODEC).documentation("Runs the interaction defined by the interaction variables if defined.")).appendInherited(new KeyedCodec("DefaultValue", (Codec)RootInteraction.CHILD_ASSET_CODEC), (i, s) -> i.defaultValue = s, i -> i.defaultValue, (i, parent) -> i.defaultValue = parent.defaultValue).addValidatorLate(() -> RootInteraction.VALIDATOR_CACHE.getValidator().late()).add()).appendInherited(new KeyedCodec("Var", (Codec)Codec.STRING), (i, s) -> i.variable = s, i -> i.variable, (i, parent) -> i.variable = parent.variable).addValidator(Validators.nonNull()).add()).appendInherited(new KeyedCodec("DefaultOk", (Codec)Codec.BOOLEAN), (i, s) -> i.defaultOk = s.booleanValue(), i -> Boolean.valueOf(i.defaultOk), (i, parent) -> i.defaultOk = parent.defaultOk).add()).build();
/*  56 */   } private static final StringTag TAG_DEFAULT = StringTag.of("Default");
/*  57 */   private static final StringTag TAG_VARS = StringTag.of("Vars");
/*     */   
/*     */   @Nullable
/*     */   protected String defaultValue;
/*     */   
/*     */   protected String variable;
/*     */   
/*     */   protected boolean defaultOk;
/*     */   
/*     */   @Nonnull
/*     */   public WaitForDataFrom getWaitForDataFrom() {
/*  68 */     return WaitForDataFrom.None;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void tick0(boolean firstRun, float time, @NonNullDecl InteractionType type, @Nonnull InteractionContext context, @NonNullDecl CooldownHandler cooldownHandler) {
/*  73 */     if (Interaction.failed((context.getState()).state))
/*  74 */       return;  if (!firstRun)
/*     */       return; 
/*  76 */     doReplace(context, true);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void simulateTick0(boolean firstRun, float time, @NonNullDecl InteractionType type, @Nonnull InteractionContext context, @NonNullDecl CooldownHandler cooldownHandler) {
/*  81 */     if (Interaction.failed((context.getState()).state))
/*  82 */       return;  if (!firstRun)
/*     */       return; 
/*  84 */     doReplace(context, false);
/*     */   }
/*     */   
/*     */   private void doReplace(@Nonnull InteractionContext context, boolean log) {
/*  88 */     Map<String, String> vars = context.getInteractionVars();
/*  89 */     String next = (vars == null) ? null : vars.get(this.variable);
/*     */     
/*  91 */     if (next == null && !this.defaultOk && log)
/*     */     {
/*  93 */       ((HytaleLogger.Api)HytaleLogger.getLogger()
/*  94 */         .at(Level.SEVERE)
/*  95 */         .atMostEvery(1, TimeUnit.MINUTES))
/*  96 */         .log("Missing replacement interactions for interaction: %s for var %s on item %s", this.id, this.variable, context.getHeldItem());
/*     */     }
/*     */     
/*  99 */     if (next == null) next = this.defaultValue;
/*     */     
/* 101 */     if (next == null) {
/* 102 */       (context.getState()).state = InteractionState.Failed;
/*     */       
/*     */       return;
/*     */     } 
/* 106 */     RootInteraction nextInteraction = RootInteraction.getRootInteractionOrUnknown(next);
/* 107 */     (context.getState()).state = InteractionState.Finished;
/* 108 */     context.execute(nextInteraction);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean needsRemoteSync() {
/* 115 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean walk(@Nonnull Collector collector, @Nonnull InteractionContext context) {
/* 120 */     if (this.defaultValue != null && 
/* 121 */       InteractionManager.walkInteractions(collector, context, (CollectorTag)TAG_DEFAULT, RootInteraction.getRootInteractionOrUnknown(this.defaultValue).getInteractionIds())) {
/* 122 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 126 */     Map<String, String> vars = context.getInteractionVars();
/* 127 */     if (vars == null) return false;
/*     */     
/* 129 */     String interactionIds = vars.get(this.variable);
/* 130 */     if (interactionIds == null) return false; 
/* 131 */     return InteractionManager.walkInteractions(collector, context, (CollectorTag)TAG_VARS, RootInteraction.getRootInteractionOrUnknown(interactionIds).getInteractionIds());
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected Interaction generatePacket() {
/* 137 */     return (Interaction)new com.hypixel.hytale.protocol.ReplaceInteraction();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void configurePacket(Interaction packet) {
/* 142 */     super.configurePacket(packet);
/* 143 */     com.hypixel.hytale.protocol.ReplaceInteraction p = (com.hypixel.hytale.protocol.ReplaceInteraction)packet;
/* 144 */     p.defaultValue = RootInteraction.getRootInteractionIdOrUnknown(this.defaultValue);
/* 145 */     p.variable = this.variable;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 151 */     return "ReplaceInteraction{defaultValue='" + this.defaultValue + "', variable='" + this.variable + "', defaultOk=" + this.defaultOk + "} " + super
/*     */ 
/*     */ 
/*     */       
/* 155 */       .toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\none\ReplaceInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */