/*     */ package com.hypixel.hytale.server.core.modules.entitystats.asset.condition;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.lookup.CodecMapCodec;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.server.core.modules.entitystats.asset.EntityStatType;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.time.Instant;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Condition
/*     */ {
/*     */   @Nonnull
/*  24 */   public static final CodecMapCodec<Condition> CODEC = new CodecMapCodec();
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected static final BuilderCodec<Condition> BASE_CODEC;
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean inverse;
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  38 */     BASE_CODEC = ((BuilderCodec.Builder)BuilderCodec.abstractBuilder(Condition.class).append(new KeyedCodec("Inverse", (Codec)Codec.BOOLEAN), (regenerating, value) -> regenerating.inverse = value.booleanValue(), regenerating -> Boolean.valueOf(regenerating.inverse)).documentation("Determines whether the condition is inverted.").add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Condition() {
/*  49 */     this.inverse = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Condition(boolean inverse) {
/*  58 */     this.inverse = inverse;
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
/*     */   public boolean eval(@Nonnull ComponentAccessor<EntityStore> componentAccessor, @Nonnull Ref<EntityStore> ref, @Nonnull Instant currentTime) {
/*  72 */     return (this.inverse != eval0(componentAccessor, ref, currentTime));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean allConditionsMet(@Nonnull ComponentAccessor<EntityStore> componentAccessor, @Nonnull Ref<EntityStore> ref, @Nonnull Instant currentTime, @Nonnull EntityStatType.Regenerating regenerating) {
/* 100 */     if (regenerating.getConditions() == null) return true;
/*     */     
/* 102 */     return allConditionsMet(componentAccessor, ref, currentTime, regenerating.getConditions());
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
/*     */   public static boolean allConditionsMet(@Nonnull ComponentAccessor<EntityStore> componentAccessor, @Nonnull Ref<EntityStore> ref, @Nonnull Instant currentTime, @Nonnull Condition[] conditions) {
/* 118 */     boolean allMet = true;
/* 119 */     for (Condition condition : conditions) {
/* 120 */       if (!condition.eval(componentAccessor, ref, currentTime)) {
/* 121 */         allMet = false;
/*     */         break;
/*     */       } 
/*     */     } 
/* 125 */     return allMet;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 131 */     return "Condition{inverse=" + this.inverse + "}";
/*     */   }
/*     */   
/*     */   public abstract boolean eval0(@Nonnull ComponentAccessor<EntityStore> paramComponentAccessor, @Nonnull Ref<EntityStore> paramRef, @Nonnull Instant paramInstant);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entitystats\asset\condition\Condition.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */