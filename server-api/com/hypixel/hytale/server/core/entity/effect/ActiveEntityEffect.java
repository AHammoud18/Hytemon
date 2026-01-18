/*     */ package com.hypixel.hytale.server.core.entity.effect;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.system.EcsEvent;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.protocol.ChangeStatBehaviour;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.asset.type.entityeffect.config.EntityEffect;
/*     */ import com.hypixel.hytale.server.core.modules.entity.damage.Damage;
/*     */ import com.hypixel.hytale.server.core.modules.entity.damage.DamageCalculatorSystems;
/*     */ import com.hypixel.hytale.server.core.modules.entity.damage.DamageCause;
/*     */ import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.server.combat.DamageCalculator;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.server.combat.DamageEffects;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.ints.Int2FloatMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2FloatMap;
/*     */ import java.util.Locale;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
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
/*     */ public class ActiveEntityEffect
/*     */   implements Damage.Source
/*     */ {
/*     */   @Nonnull
/*     */   public static final BuilderCodec<ActiveEntityEffect> CODEC;
/*     */   private static final float DEFAULT_DURATION = 1.0F;
/*     */   
/*     */   static {
/*  82 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ActiveEntityEffect.class, ActiveEntityEffect::new).append(new KeyedCodec("EntityEffectId", (Codec)Codec.STRING), (entityEffect, x) -> entityEffect.entityEffectId = x, entityEffect -> entityEffect.entityEffectId).add()).append(new KeyedCodec("InitialDuration", (Codec)Codec.FLOAT), (entityEffect, x) -> entityEffect.initialDuration = x.floatValue(), entityEffect -> Float.valueOf(entityEffect.initialDuration)).add()).append(new KeyedCodec("RemainingDuration", (Codec)Codec.FLOAT), (entityEffect, x) -> entityEffect.remainingDuration = x.floatValue(), entityEffect -> Float.valueOf(entityEffect.remainingDuration)).add()).append(new KeyedCodec("SinceLastDamage", (Codec)Codec.FLOAT), (entityEffect, x) -> entityEffect.sinceLastDamage = x.floatValue(), entityEffect -> Float.valueOf(entityEffect.sinceLastDamage)).add()).append(new KeyedCodec("HasBeenDamaged", (Codec)Codec.BOOLEAN), (entityEffect, x) -> entityEffect.hasBeenDamaged = x.booleanValue(), entityEffect -> Boolean.valueOf(entityEffect.hasBeenDamaged)).add()).append(new KeyedCodec("SequentialHits", (Codec)DamageCalculatorSystems.Sequence.CODEC), (entityEffect, x) -> entityEffect.sequentialHits = x, entityEffect -> entityEffect.sequentialHits).add()).append(new KeyedCodec("Infinite", (Codec)Codec.BOOLEAN), (entityEffect, aBoolean) -> entityEffect.infinite = aBoolean.booleanValue(), entityEffect -> Boolean.valueOf(entityEffect.infinite)).add()).append(new KeyedCodec("Debuff", (Codec)Codec.BOOLEAN), (entityEffect, aBoolean) -> entityEffect.debuff = aBoolean.booleanValue(), entityEffect -> Boolean.valueOf(entityEffect.debuff)).add()).append(new KeyedCodec("StatusEffectIcon", (Codec)Codec.STRING), (entityEffect, aString) -> entityEffect.statusEffectIcon = aString, entityEffect -> entityEffect.statusEffectIcon).add()).append(new KeyedCodec("Invulnerable", (Codec)Codec.BOOLEAN), (entityEffect, aBoolean) -> entityEffect.invulnerable = aBoolean.booleanValue(), entityEffect -> Boolean.valueOf(entityEffect.invulnerable)).add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  93 */   private static final Message MESSAGE_GENERAL_DAMAGE_CAUSES_UNKNOWN = Message.translation("server.general.damageCauses.unknown");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String entityEffectId;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int entityEffectIndex;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float initialDuration;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float remainingDuration;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean infinite;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean debuff;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected String statusEffectIcon;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float sinceLastDamage;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean hasBeenDamaged;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean invulnerable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private DamageCalculatorSystems.Sequence sequentialHits;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ActiveEntityEffect() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ActiveEntityEffect(@Nonnull String entityEffectId, int entityEffectIndex, float initialDuration, float remainingDuration, boolean infinite, boolean debuff, @Nullable String statusEffectIcon, float sinceLastDamage, boolean hasBeenDamaged, @Nonnull DamageCalculatorSystems.Sequence sequentialHits, boolean invulnerable) {
/* 186 */     this.entityEffectId = entityEffectId;
/* 187 */     this.entityEffectIndex = entityEffectIndex;
/* 188 */     this.initialDuration = initialDuration;
/* 189 */     this.remainingDuration = remainingDuration;
/* 190 */     this.infinite = infinite;
/* 191 */     this.debuff = debuff;
/* 192 */     this.statusEffectIcon = statusEffectIcon;
/* 193 */     this.sinceLastDamage = sinceLastDamage;
/* 194 */     this.hasBeenDamaged = hasBeenDamaged;
/* 195 */     this.sequentialHits = sequentialHits;
/* 196 */     this.invulnerable = invulnerable;
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
/*     */   public ActiveEntityEffect(@Nonnull String entityEffectId, int entityEffectIndex, float duration, boolean debuff, @Nullable String statusEffectIcon, boolean invulnerable) {
/* 215 */     this(entityEffectId, entityEffectIndex, duration, duration, false, debuff, statusEffectIcon, 0.0F, false, new DamageCalculatorSystems.Sequence(), invulnerable);
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
/*     */   public ActiveEntityEffect(@Nonnull String entityEffectId, int entityEffectIndex, boolean infinite, boolean invulnerable) {
/* 230 */     this(entityEffectId, entityEffectIndex, 1.0F, 1.0F, infinite, false, "", 0.0F, false, new DamageCalculatorSystems.Sequence(), invulnerable);
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
/*     */   public void tick(@Nonnull CommandBuffer<EntityStore> commandBuffer, @Nonnull Ref<EntityStore> ref, @Nonnull EntityEffect entityEffect, @Nonnull EntityStatMap entityStatMapComponent, float dt) {
/* 247 */     int cyclesToRun = calculateCyclesToRun(entityEffect, dt);
/*     */     
/* 249 */     tickDamage(commandBuffer, ref, entityEffect, cyclesToRun);
/* 250 */     tickStatChanges(commandBuffer, ref, entityEffect, entityStatMapComponent, cyclesToRun);
/*     */     
/* 252 */     if (!this.infinite) this.remainingDuration -= dt;
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int calculateCyclesToRun(@Nonnull EntityEffect entityEffect, float dt) {
/* 263 */     int cycles = 0;
/* 264 */     float damageCalculatorCooldown = entityEffect.getDamageCalculatorCooldown();
/*     */     
/* 266 */     if (damageCalculatorCooldown > 0.0F) {
/* 267 */       this.sinceLastDamage += dt;
/*     */ 
/*     */ 
/*     */       
/* 271 */       cycles = MathUtil.fastFloor(this.sinceLastDamage / damageCalculatorCooldown);
/* 272 */       this.sinceLastDamage %= damageCalculatorCooldown;
/* 273 */     } else if (!this.hasBeenDamaged) {
/* 274 */       cycles = 1;
/* 275 */       this.hasBeenDamaged = true;
/*     */     } 
/* 277 */     return cycles;
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
/*     */   private static void tickStatChanges(@Nonnull CommandBuffer<EntityStore> commandBuffer, @Nonnull Ref<EntityStore> ref, @Nonnull EntityEffect entityEffect, @Nonnull EntityStatMap entityStatMapComponent, int cyclesToRun) {
/* 294 */     Int2FloatMap entityStats = entityEffect.getEntityStats();
/* 295 */     if (entityStats == null)
/*     */       return; 
/* 297 */     if (cyclesToRun <= 0) {
/*     */       return;
/*     */     }
/*     */     
/* 301 */     DamageEffects statModifierEffects = entityEffect.getStatModifierEffects();
/* 302 */     if (statModifierEffects != null) {
/* 303 */       statModifierEffects.spawnAtEntity(commandBuffer, ref);
/*     */     }
/*     */     
/* 306 */     entityStatMapComponent.processStatChanges(EntityStatMap.Predictable.ALL, entityStats, entityEffect
/*     */ 
/*     */         
/* 309 */         .getValueType(), ChangeStatBehaviour.Add);
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
/*     */   private void tickDamage(@Nonnull CommandBuffer<EntityStore> commandBuffer, @Nonnull Ref<EntityStore> ref, @Nonnull EntityEffect entityEffect, int cyclesToRun) {
/* 328 */     DamageCalculator damageCalculator = entityEffect.getDamageCalculator();
/* 329 */     if (damageCalculator == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 334 */     if (cyclesToRun <= 0) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 339 */     Object2FloatMap<DamageCause> relativeDamage = damageCalculator.calculateDamage(this.initialDuration);
/* 340 */     if (relativeDamage == null || relativeDamage.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/* 344 */     World world = ((EntityStore)commandBuffer.getExternalData()).getWorld();
/* 345 */     DamageEffects damageEffects = entityEffect.getDamageEffects();
/* 346 */     Damage[] hits = DamageCalculatorSystems.queueDamageCalculator(world, relativeDamage, ref, commandBuffer, this, null);
/* 347 */     for (Damage damageEvent : hits) {
/* 348 */       DamageCalculatorSystems.DamageSequence damageSequence = new DamageCalculatorSystems.DamageSequence(this.sequentialHits, damageCalculator);
/* 349 */       damageEvent.putMetaObject(DamageCalculatorSystems.DAMAGE_SEQUENCE, damageSequence);
/* 350 */       if (damageEffects != null) {
/* 351 */         damageEffects.addToDamage(damageEvent);
/*     */       }
/* 353 */       commandBuffer.invoke(ref, (EcsEvent)damageEvent);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEntityEffectIndex() {
/* 361 */     return this.entityEffectIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getInitialDuration() {
/* 368 */     return this.initialDuration;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getRemainingDuration() {
/* 375 */     return this.remainingDuration;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInfinite() {
/* 382 */     return this.infinite;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDebuff() {
/* 389 */     return this.debuff;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInvulnerable() {
/* 396 */     return this.invulnerable;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Message getDeathMessage(@Nonnull Damage info, @Nonnull Ref<EntityStore> targetRef, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*     */     Message damageCauseMessage;
/* 404 */     EntityEffect entityEffect = (EntityEffect)EntityEffect.getAssetMap().getAsset(this.entityEffectIndex);
/* 405 */     if (entityEffect != null) {
/* 406 */       String locale = entityEffect.getLocale();
/* 407 */       String reason = (locale != null) ? locale : entityEffect.getId().toLowerCase(Locale.ROOT);
/* 408 */       damageCauseMessage = Message.translation("server.general.damageCauses." + reason);
/*     */     } else {
/* 410 */       damageCauseMessage = MESSAGE_GENERAL_DAMAGE_CAUSES_UNKNOWN;
/*     */     } 
/*     */     
/* 413 */     return Message.translation("server.general.killedBy")
/* 414 */       .param("damageSource", damageCauseMessage);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 420 */     return "ActiveEntityEffect{entityEffectIndex='" + this.entityEffectIndex + "', initialDuration=" + this.initialDuration + ", remainingDuration=" + this.remainingDuration + ", damageCooldown=" + this.sinceLastDamage + ", hasBeenDamaged=" + this.hasBeenDamaged + ", sequentialHits=" + String.valueOf(this.sequentialHits) + ", infinite=" + this.infinite + ", debuff=" + this.debuff + ", statusEffectIcon=" + this.statusEffectIcon + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\entity\effect\ActiveEntityEffect.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */