/*     */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config.none;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.map.IndexedLookupTableAssetMap;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.codec.validation.LateValidator;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
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
/*     */ import java.util.Arrays;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ParallelInteraction
/*     */   extends Interaction
/*     */ {
/*     */   public static final BuilderCodec<ParallelInteraction> CODEC;
/*     */   protected String[] interactions;
/*     */   
/*     */   static {
/*  37 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ParallelInteraction.class, ParallelInteraction::new, Interaction.ABSTRACT_CODEC).documentation("Runs the provided interactions in parallel to this interaction chain.")).appendInherited(new KeyedCodec("Interactions", (Codec)new ArrayCodec((Codec)RootInteraction.CHILD_ASSET_CODEC, x$0 -> new String[x$0])), (i, s) -> i.interactions = s, i -> i.interactions, (i, parent) -> i.interactions = parent.interactions).documentation("The collection of interaction roots to run in parallel via forks.").addValidator(Validators.nonNull()).addValidatorLate(() -> RootInteraction.VALIDATOR_CACHE.getArrayValidator().late()).addValidator(Validators.arraySizeRange(2, 2147483647)).add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public WaitForDataFrom getWaitForDataFrom() {
/*  44 */     return WaitForDataFrom.None;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void tick0(boolean firstRun, float time, @NonNullDecl InteractionType type, @Nonnull InteractionContext context, @NonNullDecl CooldownHandler cooldownHandler) {
/*  49 */     IndexedLookupTableAssetMap<String, RootInteraction> assetMap = RootInteraction.getAssetMap();
/*     */ 
/*     */     
/*  52 */     context.execute(RootInteraction.getRootInteractionOrUnknown(this.interactions[0]));
/*  53 */     for (int i = 1; i < this.interactions.length; i++) {
/*  54 */       String interaction = this.interactions[i];
/*  55 */       context.fork(context.duplicate(), RootInteraction.getRootInteractionOrUnknown(interaction), true);
/*     */     } 
/*  57 */     (context.getState()).state = InteractionState.Finished;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void simulateTick0(boolean firstRun, float time, @NonNullDecl InteractionType type, @Nonnull InteractionContext context, @NonNullDecl CooldownHandler cooldownHandler) {
/*  62 */     IndexedLookupTableAssetMap<String, RootInteraction> assetMap = RootInteraction.getAssetMap();
/*     */     
/*  64 */     context.execute(RootInteraction.getRootInteractionOrUnknown(this.interactions[0]));
/*  65 */     (context.getState()).state = InteractionState.Finished;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean walk(@Nonnull Collector collector, @Nonnull InteractionContext context) {
/*  70 */     for (int i = 0; i < this.interactions.length; i++) {
/*  71 */       String root = this.interactions[i];
/*  72 */       if (InteractionManager.walkInteractions(collector, context, ParallelTag.of(i), RootInteraction.getRootInteractionOrUnknown(root).getInteractionIds())) {
/*  73 */         return true;
/*     */       }
/*     */     } 
/*  76 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean needsRemoteSync() {
/*  82 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected Interaction generatePacket() {
/*  88 */     return (Interaction)new com.hypixel.hytale.protocol.ParallelInteraction();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void configurePacket(Interaction packet) {
/*  93 */     super.configurePacket(packet);
/*  94 */     com.hypixel.hytale.protocol.ParallelInteraction p = (com.hypixel.hytale.protocol.ParallelInteraction)packet;
/*  95 */     int[] chainingNext = p.next = new int[this.interactions.length];
/*  96 */     for (int i = 0; i < this.interactions.length; i++) {
/*  97 */       chainingNext[i] = RootInteraction.getRootInteractionIdOrUnknown(this.interactions[i]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 105 */     return "ParallelInteraction{interactions=" + Arrays.toString((Object[])this.interactions) + "} " + super
/* 106 */       .toString();
/*     */   }
/*     */   
/*     */   private static class ParallelTag implements CollectorTag {
/*     */     private final int index;
/*     */     
/*     */     private ParallelTag(int index) {
/* 113 */       this.index = index;
/*     */     }
/*     */     
/*     */     public int getIndex() {
/* 117 */       return this.index;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(@Nullable Object o) {
/* 122 */       if (this == o) return true; 
/* 123 */       if (o == null || getClass() != o.getClass()) return false;
/*     */       
/* 125 */       ParallelTag that = (ParallelTag)o;
/* 126 */       return (this.index == that.index);
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 131 */       return this.index;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 137 */       return "ParallelTag{index=" + this.index + "}";
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public static ParallelTag of(int index) {
/* 144 */       return new ParallelTag(index);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\none\ParallelInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */