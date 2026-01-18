/*     */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config.none;
/*     */ 
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.protocol.Interaction;
/*     */ import com.hypixel.hytale.protocol.InteractionType;
/*     */ import com.hypixel.hytale.protocol.WaitForDataFrom;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionManager;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.data.Collector;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.data.CollectorTag;
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
/*     */ public class SerialInteraction
/*     */   extends Interaction
/*     */ {
/*     */   @Nonnull
/*     */   public static final BuilderCodec<SerialInteraction> CODEC;
/*     */   protected String[] interactions;
/*     */   
/*     */   static {
/*  43 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(SerialInteraction.class, SerialInteraction::new, BuilderCodec.abstractBuilder(Interaction.class).build()).documentation("Runs the given interactions in order.")).appendInherited(new KeyedCodec("Interactions", CHILD_ASSET_CODEC_ARRAY), (o, i) -> o.interactions = i, o -> o.interactions, (o, p) -> o.interactions = p.interactions).documentation("A list of interactions to run. They will be executed in the order specified sequentially.").addValidator(Validators.nonNull()).addValidator((Validator)Validators.nonNullArrayElements()).add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void tick0(boolean firstRun, float time, @NonNullDecl InteractionType type, @NonNullDecl InteractionContext context, @NonNullDecl CooldownHandler cooldownHandler) {
/*  49 */     throw new IllegalStateException("Should not be reached");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void simulateTick0(boolean firstRun, float time, @NonNullDecl InteractionType type, @NonNullDecl InteractionContext context, @NonNullDecl CooldownHandler cooldownHandler) {
/*  54 */     throw new IllegalStateException("Should not be reached");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean walk(@Nonnull Collector collector, @Nonnull InteractionContext context) {
/*  59 */     for (int i = 0; i < this.interactions.length; i++) {
/*  60 */       if (InteractionManager.walkInteraction(collector, context, SerialTag.of(i), this.interactions[i])) return true; 
/*     */     } 
/*  62 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void compile(@Nonnull OperationsBuilder builder) {
/*  67 */     for (String interaction : this.interactions) {
/*  68 */       Interaction.getInteractionOrUnknown(interaction).compile(builder);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected Interaction generatePacket() {
/*  75 */     return (Interaction)new com.hypixel.hytale.protocol.SerialInteraction();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void configurePacket(Interaction packet) {
/*  80 */     super.configurePacket(packet);
/*  81 */     com.hypixel.hytale.protocol.SerialInteraction p = (com.hypixel.hytale.protocol.SerialInteraction)packet;
/*  82 */     int[] serialInteractions = p.serialInteractions = new int[this.interactions.length];
/*  83 */     for (int i = 0; i < this.interactions.length; i++) {
/*  84 */       serialInteractions[i] = Interaction.getInteractionIdOrUnknown(this.interactions[i]);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean needsRemoteSync() {
/*  90 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public WaitForDataFrom getWaitForDataFrom() {
/*  96 */     return WaitForDataFrom.None;
/*     */   }
/*     */   
/*     */   private static class SerialTag implements CollectorTag {
/*     */     private final int index;
/*     */     
/*     */     private SerialTag(int index) {
/* 103 */       this.index = index;
/*     */     }
/*     */     
/*     */     public int getIndex() {
/* 107 */       return this.index;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(@Nullable Object o) {
/* 112 */       if (this == o) return true; 
/* 113 */       if (o == null || getClass() != o.getClass()) return false;
/*     */       
/* 115 */       SerialTag that = (SerialTag)o;
/* 116 */       return (this.index == that.index);
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 121 */       return this.index;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 127 */       return "SerialTag{index=" + this.index + "}";
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public static SerialTag of(int index) {
/* 134 */       return new SerialTag(index);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\none\SerialInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */