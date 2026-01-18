/*     */ package com.hypixel.hytale.server.core.modules.interaction;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.map.EnumMapCodec;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.protocol.InteractionType;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.Collections;
/*     */ import java.util.EnumMap;
/*     */ import java.util.Map;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Interactions
/*     */   implements Component<EntityStore>
/*     */ {
/*     */   @Nonnull
/*     */   public static final BuilderCodec<Interactions> CODEC;
/*     */   
/*     */   @Nonnull
/*     */   public static ComponentType<EntityStore, Interactions> getComponentType() {
/*  28 */     return InteractionModule.get().getInteractionsComponentType();
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
/*     */   static {
/*  43 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(Interactions.class, Interactions::new).appendInherited(new KeyedCodec("Interactions", (Codec)new EnumMapCodec(InteractionType.class, (Codec)Codec.STRING, false)), (o, v) -> o.interactions = v, o -> o.interactions, (o, p) -> o.interactions = p.interactions).add()).build();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*  48 */   private Map<InteractionType, String> interactions = new EnumMap<>(InteractionType.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private String interactionHint;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isNetworkOutdated = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Interactions(@Nonnull Map<InteractionType, String> interactions) {
/*  76 */     this.interactions = new EnumMap<>(interactions);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String getInteractionId(@Nonnull InteractionType type) {
/*  87 */     return this.interactions.get(type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInteractionId(@Nonnull InteractionType type, @Nonnull String interactionId) {
/*  97 */     this.interactions.put(type, interactionId);
/*  98 */     this.isNetworkOutdated = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Map<InteractionType, String> getInteractions() {
/* 106 */     return Collections.unmodifiableMap(this.interactions);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String getInteractionHint() {
/* 111 */     return this.interactionHint;
/*     */   }
/*     */   
/*     */   public void setInteractionHint(@Nullable String interactionHint) {
/* 115 */     this.interactionHint = interactionHint;
/* 116 */     this.isNetworkOutdated = true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Component<EntityStore> clone() {
/* 123 */     Interactions clone = new Interactions(this.interactions);
/* 124 */     clone.interactionHint = this.interactionHint;
/* 125 */     return clone;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean consumeNetworkOutdated() {
/* 134 */     boolean tmp = this.isNetworkOutdated;
/* 135 */     this.isNetworkOutdated = false;
/* 136 */     return tmp;
/*     */   }
/*     */   
/*     */   public Interactions() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\Interactions.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */