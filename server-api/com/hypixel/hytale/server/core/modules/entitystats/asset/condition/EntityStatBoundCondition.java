/*     */ package com.hypixel.hytale.server.core.modules.entitystats.asset.condition;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.validation.LateValidator;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
/*     */ import com.hypixel.hytale.server.core.modules.entitystats.EntityStatValue;
/*     */ import com.hypixel.hytale.server.core.modules.entitystats.EntityStatsModule;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class EntityStatBoundCondition
/*     */   extends Condition
/*     */ {
/*     */   @Nonnull
/*     */   public static final BuilderCodec<EntityStatBoundCondition> CODEC;
/*     */   protected String unknownStat;
/*     */   
/*     */   static {
/*  36 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.abstractBuilder(EntityStatBoundCondition.class, Condition.BASE_CODEC).append(new KeyedCodec("Stat", (Codec)Codec.STRING), (condition, value) -> condition.unknownStat = value, condition -> condition.unknownStat).documentation("The stat to evaluate the condition against.").addValidator(Validators.nonNull()).addValidatorLate(() -> EntityStatType.VALIDATOR_CACHE.getValidator().late()).add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  46 */   protected int stat = Integer.MIN_VALUE;
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
/*     */   public EntityStatBoundCondition(boolean inverse, int stat) {
/*  63 */     super(inverse);
/*  64 */     this.stat = stat;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean eval0(@Nonnull ComponentAccessor<EntityStore> componentAccessor, @Nonnull Ref<EntityStore> ref, @Nonnull Instant currentTime) {
/*  71 */     if (this.stat == Integer.MIN_VALUE) {
/*  72 */       this.stat = EntityStatType.getAssetMap().getIndex(this.unknownStat);
/*     */     }
/*     */     
/*  75 */     EntityStatMap entityStatMapComponent = (EntityStatMap)componentAccessor.getComponent(ref, EntityStatsModule.get().getEntityStatMapComponentType());
/*  76 */     if (entityStatMapComponent == null) return false;
/*     */     
/*  78 */     EntityStatValue statValue = entityStatMapComponent.get(this.stat);
/*  79 */     if (statValue == null) return false;
/*     */     
/*  81 */     return eval0(ref, currentTime, statValue);
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
/*     */   @Nonnull
/*     */   public String toString() {
/*  97 */     return "EntityStatBoundCondition{unknownStat='" + this.unknownStat + "', stat=" + this.stat + "} " + super
/*     */ 
/*     */       
/* 100 */       .toString();
/*     */   }
/*     */   
/*     */   protected EntityStatBoundCondition() {}
/*     */   
/*     */   public abstract boolean eval0(@Nonnull Ref<EntityStore> paramRef, @Nonnull Instant paramInstant, @Nonnull EntityStatValue paramEntityStatValue);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entitystats\asset\condition\EntityStatBoundCondition.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */