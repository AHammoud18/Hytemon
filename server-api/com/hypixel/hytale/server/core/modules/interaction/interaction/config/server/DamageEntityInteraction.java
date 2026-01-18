/*     */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config.server;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.map.IndexedLookupTableAssetMap;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.codec.codecs.map.MapCodec;
/*     */ import com.hypixel.hytale.codec.validation.LateValidator;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.system.EcsEvent;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.math.util.TrigMathUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.math.vector.Vector4d;
/*     */ import com.hypixel.hytale.protocol.DamageEffects;
/*     */ import com.hypixel.hytale.protocol.Interaction;
/*     */ import com.hypixel.hytale.protocol.InteractionState;
/*     */ import com.hypixel.hytale.protocol.InteractionType;
/*     */ import com.hypixel.hytale.protocol.WaitForDataFrom;
/*     */ import com.hypixel.hytale.server.core.asset.type.entityeffect.config.EntityEffect;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.Item;
/*     */ import com.hypixel.hytale.server.core.entity.Entity;
/*     */ import com.hypixel.hytale.server.core.entity.EntitySnapshot;
/*     */ import com.hypixel.hytale.server.core.entity.EntityUtils;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*     */ import com.hypixel.hytale.server.core.entity.LivingEntity;
/*     */ import com.hypixel.hytale.server.core.entity.effect.EffectControllerComponent;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.entity.knockback.KnockbackComponent;
/*     */ import com.hypixel.hytale.server.core.inventory.Inventory;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
/*     */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*     */ import com.hypixel.hytale.server.core.meta.DynamicMetaStore;
/*     */ import com.hypixel.hytale.server.core.meta.MetaKey;
/*     */ import com.hypixel.hytale.server.core.modules.debug.DebugUtils;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.HeadRotation;
/*     */ import com.hypixel.hytale.server.core.modules.entity.damage.Damage;
/*     */ import com.hypixel.hytale.server.core.modules.entity.damage.DamageCalculatorSystems;
/*     */ import com.hypixel.hytale.server.core.modules.entity.damage.DamageCause;
/*     */ import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
/*     */ import com.hypixel.hytale.server.core.modules.entitystats.asset.EntityStatType;
/*     */ import com.hypixel.hytale.server.core.modules.entitystats.modifier.StaticModifier;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.data.Collector;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.none.SelectInteraction;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.server.combat.DamageCalculator;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.server.combat.DamageClass;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.server.combat.DamageEffects;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.server.combat.Knockback;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.server.combat.TargetEntityEffect;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.operation.Label;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.operation.Operation;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.operation.OperationsBuilder;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.objects.Object2FloatMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.Map;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.stream.IntStream;
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
/*     */ public class DamageEntityInteraction
/*     */   extends Interaction
/*     */ {
/*     */   @Nonnull
/*     */   public static final BuilderCodec<DamageEntityInteraction> CODEC;
/*     */   private static final int FAILED_LABEL_INDEX = 0;
/*     */   private static final int SUCCESS_LABEL_INDEX = 1;
/*     */   private static final int BLOCKED_LABEL_INDEX = 2;
/*     */   private static final int ANGLED_LABEL_OFFSET = 3;
/*     */   public static final int ARMOR_RESISTANCE_FLAT_MODIFIER = 0;
/*     */   public static final int ARMOR_RESISTANCE_MULTIPLIER_MODIFIER = 1;
/*     */   
/*     */   static {
/* 140 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(DamageEntityInteraction.class, DamageEntityInteraction::new, Interaction.ABSTRACT_CODEC).documentation("Damages the target entity.")).appendInherited(new KeyedCodec("DamageCalculator", (Codec)DamageCalculator.CODEC), (i, a) -> i.damageCalculator = a, i -> i.damageCalculator, (i, parent) -> i.damageCalculator = parent.damageCalculator).add()).appendInherited(new KeyedCodec("DamageEffects", (Codec)DamageEffects.CODEC), (i, o) -> i.damageEffects = o, i -> i.damageEffects, (i, parent) -> i.damageEffects = parent.damageEffects).add()).appendInherited(new KeyedCodec("AngledDamage", (Codec)new ArrayCodec((Codec)AngledDamage.CODEC, x$0 -> new AngledDamage[x$0])), (i, o) -> i.angledDamage = o, i -> i.angledDamage, (i, parent) -> i.angledDamage = parent.angledDamage).add()).appendInherited(new KeyedCodec("TargetedDamage", (Codec)new MapCodec((Codec)TargetedDamage.CODEC, java.util.HashMap::new)), (i, o) -> i.targetedDamage = o, i -> i.targetedDamage, (i, parent) -> i.targetedDamage = parent.targetedDamage).addValidator(Validators.nonNull()).add()).appendInherited(new KeyedCodec("EntityStatsOnHit", (Codec)new ArrayCodec((Codec)EntityStatOnHit.CODEC, x$0 -> new EntityStatOnHit[x$0])), (damageEntityInteraction, entityStatOnHit) -> damageEntityInteraction.entityStatsOnHit = entityStatOnHit, damageEntityInteraction -> damageEntityInteraction.entityStatsOnHit, (damageEntityInteraction, parent) -> damageEntityInteraction.entityStatsOnHit = parent.entityStatsOnHit).documentation("EntityStats to apply based on the hits resulting from this interaction.").add()).appendInherited(new KeyedCodec("Next", Interaction.CHILD_ASSET_CODEC), (interaction, s) -> interaction.next = s, interaction -> interaction.next, (interaction, parent) -> interaction.next = parent.next).documentation("The interactions to run when this interaction succeeds.").addValidatorLate(() -> VALIDATOR_CACHE.getValidator().late()).add()).appendInherited(new KeyedCodec("Failed", Interaction.CHILD_ASSET_CODEC), (interaction, s) -> interaction.failed = s, interaction -> interaction.failed, (interaction, parent) -> interaction.failed = parent.failed).documentation("The interactions to run when this interaction fails.").addValidatorLate(() -> VALIDATOR_CACHE.getValidator().late()).add()).appendInherited(new KeyedCodec("Blocked", Interaction.CHILD_ASSET_CODEC), (interaction, s) -> interaction.blocked = s, interaction -> interaction.blocked, (interaction, parent) -> interaction.blocked = parent.blocked).documentation("The interactions to run when this interaction fails.").addValidatorLate(() -> VALIDATOR_CACHE.getValidator().late()).add()).afterDecode(o -> { String[] keys = o.sortedTargetDamageKeys = (String[])o.targetedDamage.keySet().toArray(()); Arrays.sort((Object[])keys); for (int i = 0; i < keys.length; i++) { String k = keys[i]; ((TargetedDamage)o.targetedDamage.get(k)).index = i; }  })).build();
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
/* 165 */   private static final MetaKey<DamageCalculatorSystems.Sequence> SEQUENTIAL_HITS = META_REGISTRY.registerMetaObject(i -> new DamageCalculatorSystems.Sequence());
/* 166 */   private static final MetaKey<Integer> NEXT_INDEX = META_REGISTRY.registerMetaObject();
/* 167 */   private static final MetaKey<Damage[]> QUEUED_DAMAGE = META_REGISTRY.registerMetaObject();
/*     */   
/*     */   protected DamageCalculator damageCalculator;
/*     */   @Nullable
/*     */   protected DamageEffects damageEffects;
/*     */   protected AngledDamage[] angledDamage;
/*     */   protected EntityStatOnHit[] entityStatsOnHit;
/* 174 */   protected Map<String, TargetedDamage> targetedDamage = Collections.emptyMap();
/*     */ 
/*     */ 
/*     */   
/*     */   protected String[] sortedTargetDamageKeys;
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected String next;
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected String blocked;
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected String failed;
/*     */ 
/*     */ 
/*     */   
/*     */   protected void tick0(boolean firstRun, float time, @NonNullDecl InteractionType type, @Nonnull InteractionContext context, @NonNullDecl CooldownHandler cooldownHandler) {
/* 197 */     Ref<EntityStore> targetRef = context.getTargetEntity();
/* 198 */     if (targetRef == null || !targetRef.isValid() || !context.getEntity().isValid()) {
/* 199 */       context.jump(context.getLabel(0));
/* 200 */       (context.getState()).nextLabel = 0;
/* 201 */       (context.getState()).state = InteractionState.Failed;
/*     */       
/*     */       return;
/*     */     } 
/* 205 */     CommandBuffer<EntityStore> commandBuffer = context.getCommandBuffer();
/* 206 */     assert commandBuffer != null;
/*     */     
/* 208 */     if (processDamage(context, (Damage[])context.getInstanceStore().getIfPresentMetaObject(QUEUED_DAMAGE)))
/*     */       return; 
/* 210 */     Ref<EntityStore> ref = context.getOwningEntity();
/* 211 */     Vector4d hit = (Vector4d)context.getMetaStore().getMetaObject(Interaction.HIT_LOCATION);
/*     */     
/* 213 */     Damage.EntitySource source = new Damage.EntitySource(ref);
/* 214 */     attemptEntityDamage0((Damage.Source)source, context, context.getEntity(), targetRef, hit);
/*     */ 
/*     */     
/* 217 */     if (SelectInteraction.SHOW_VISUAL_DEBUG && hit != null) {
/* 218 */       DebugUtils.addSphere(((EntityStore)commandBuffer.getExternalData()).getWorld(), new Vector3d(hit.x, hit.y, hit.z), new Vector3f(1.0F, 0.0F, 0.0F), 0.20000000298023224D, 5.0F);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void simulateTick0(boolean firstRun, float time, @NonNullDecl InteractionType type, @Nonnull InteractionContext context, @NonNullDecl CooldownHandler cooldownHandler) {
/* 224 */     tick0(firstRun, time, type, context, cooldownHandler);
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
/*     */   private boolean processDamage(@Nonnull InteractionContext context, @Nullable Damage[] queuedDamage) {
/* 237 */     if (queuedDamage == null) return false;
/*     */     
/* 239 */     boolean failed = true;
/* 240 */     boolean blocked = false;
/* 241 */     for (Damage queue : queuedDamage) {
/* 242 */       if (!queue.isCancelled()) {
/* 243 */         failed = false;
/*     */       }
/* 245 */       if (((Boolean)queue.getMetaObject(Damage.BLOCKED)).booleanValue()) {
/* 246 */         blocked = true;
/*     */       }
/*     */     } 
/*     */     
/* 250 */     if (failed) {
/* 251 */       context.jump(context.getLabel(0));
/* 252 */       (context.getState()).nextLabel = 0;
/* 253 */       (context.getState()).state = InteractionState.Failed;
/* 254 */     } else if (blocked) {
/* 255 */       context.jump(context.getLabel(2));
/* 256 */       (context.getState()).nextLabel = 2;
/* 257 */       (context.getState()).state = InteractionState.Finished;
/*     */     } else {
/* 259 */       int index = ((Integer)context.getInstanceStore().getMetaObject(NEXT_INDEX)).intValue();
/* 260 */       (context.getState()).nextLabel = index;
/* 261 */       context.jump(context.getLabel(index));
/* 262 */       (context.getState()).state = InteractionState.Finished;
/*     */     } 
/*     */     
/* 265 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void compile(@Nonnull OperationsBuilder builder) {
/* 270 */     Label[] labels = new Label[3 + ((this.angledDamage != null) ? this.angledDamage.length : 0) + this.targetedDamage.size()];
/* 271 */     builder.addOperation((Operation)this, labels);
/* 272 */     Label endLabel = builder.createUnresolvedLabel();
/*     */     
/* 274 */     labels[0] = builder.createLabel();
/* 275 */     if (this.failed != null) Interaction.getInteractionOrUnknown(this.failed).compile(builder); 
/* 276 */     builder.jump(endLabel);
/*     */     
/* 278 */     labels[1] = builder.createLabel();
/* 279 */     if (this.next != null) Interaction.getInteractionOrUnknown(this.next).compile(builder); 
/* 280 */     builder.jump(endLabel);
/*     */     
/* 282 */     labels[2] = builder.createLabel();
/* 283 */     if (this.blocked != null) Interaction.getInteractionOrUnknown(this.blocked).compile(builder); 
/* 284 */     builder.jump(endLabel);
/*     */     
/* 286 */     int offset = 3;
/* 287 */     if (this.angledDamage != null) {
/* 288 */       for (AngledDamage damage : this.angledDamage) {
/* 289 */         labels[offset++] = builder.createLabel();
/* 290 */         String next = damage.next;
/* 291 */         if (next == null) next = this.next; 
/* 292 */         if (next != null) Interaction.getInteractionOrUnknown(next).compile(builder); 
/* 293 */         builder.jump(endLabel);
/*     */       } 
/*     */     }
/*     */     
/* 297 */     if (!this.targetedDamage.isEmpty()) {
/* 298 */       for (String k : this.sortedTargetDamageKeys) {
/* 299 */         TargetedDamage entry = this.targetedDamage.get(k);
/*     */         
/* 301 */         labels[offset++] = builder.createLabel();
/* 302 */         String next = entry.next;
/* 303 */         if (next == null) next = this.next; 
/* 304 */         if (next != null) Interaction.getInteractionOrUnknown(next).compile(builder); 
/* 305 */         builder.jump(endLabel);
/*     */       } 
/*     */     }
/*     */     
/* 309 */     builder.resolveLabel(endLabel);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean walk(@Nonnull Collector collector, @Nonnull InteractionContext context) {
/* 314 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected Interaction generatePacket() {
/* 320 */     return (Interaction)new com.hypixel.hytale.protocol.DamageEntityInteraction();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void configurePacket(Interaction packet) {
/* 325 */     super.configurePacket(packet);
/* 326 */     com.hypixel.hytale.protocol.DamageEntityInteraction p = (com.hypixel.hytale.protocol.DamageEntityInteraction)packet;
/* 327 */     p.damageEffects = (this.damageEffects != null) ? this.damageEffects.toPacket() : null;
/* 328 */     p.next = Interaction.getInteractionIdOrUnknown(this.next);
/* 329 */     p.failed = Interaction.getInteractionIdOrUnknown(this.failed);
/* 330 */     p.blocked = Interaction.getInteractionIdOrUnknown(this.blocked);
/*     */     
/* 332 */     if (this.angledDamage != null) {
/* 333 */       p.angledDamage = new com.hypixel.hytale.protocol.AngledDamage[this.angledDamage.length];
/* 334 */       for (int i = 0; i < this.angledDamage.length; i++) {
/* 335 */         p.angledDamage[i] = this.angledDamage[i].toAngledDamagePacket();
/*     */       }
/*     */     } 
/*     */     
/* 339 */     if (this.entityStatsOnHit != null) {
/* 340 */       p.entityStatsOnHit = new com.hypixel.hytale.protocol.EntityStatOnHit[this.entityStatsOnHit.length];
/* 341 */       for (int i = 0; i < this.entityStatsOnHit.length; i++) {
/* 342 */         p.entityStatsOnHit[i] = this.entityStatsOnHit[i].toPacket();
/*     */       }
/*     */     } 
/*     */     
/* 346 */     p.targetedDamage = (Map)new Object2ObjectOpenHashMap();
/* 347 */     for (Map.Entry<String, TargetedDamage> e : this.targetedDamage.entrySet()) {
/* 348 */       p.targetedDamage.put(e.getKey(), ((TargetedDamage)e.getValue()).toTargetedDamagePacket());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean needsRemoteSync() {
/* 354 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public WaitForDataFrom getWaitForDataFrom() {
/* 360 */     return WaitForDataFrom.None;
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
/*     */   private void attemptEntityDamage0(@Nonnull Damage.Source source, @Nonnull InteractionContext context, @Nonnull Ref<EntityStore> attackerRef, @Nonnull Ref<EntityStore> targetRef, @Nullable Vector4d hit) {
/*     */     Vector3f attackerDirection;
/* 378 */     CommandBuffer<EntityStore> commandBuffer = context.getCommandBuffer();
/* 379 */     assert commandBuffer != null;
/*     */     
/* 381 */     DamageCalculator damageCalculator = this.damageCalculator;
/* 382 */     DamageEffects damageEffects = this.damageEffects;
/*     */     
/* 384 */     EntitySnapshot targetSnapshot = context.getSnapshot(targetRef, (ComponentAccessor)commandBuffer);
/* 385 */     EntitySnapshot attackerSnapshot = context.getSnapshot(attackerRef, (ComponentAccessor)commandBuffer);
/* 386 */     Vector3d targetPos = targetSnapshot.getPosition();
/* 387 */     Vector3d attackerPos = attackerSnapshot.getPosition();
/* 388 */     float angleBetween = TrigMathUtil.atan2(attackerPos.x - targetPos.x, attackerPos.z - targetPos.z);
/*     */     
/* 390 */     int nextLabel = 1;
/*     */     
/* 392 */     if (this.angledDamage != null) {
/* 393 */       float angleBetweenRotation = MathUtil.wrapAngle(angleBetween + 3.1415927F - targetSnapshot.getBodyRotation().getYaw());
/*     */       
/* 395 */       for (int i = 0; i < this.angledDamage.length; i++) {
/* 396 */         AngledDamage angledDamage = this.angledDamage[i];
/* 397 */         if (Math.abs(MathUtil.compareAngle(angleBetweenRotation, angledDamage.angleRad)) < angledDamage.angleDistanceRad) {
/* 398 */           damageCalculator = (angledDamage.damageCalculator == null) ? damageCalculator : angledDamage.damageCalculator;
/* 399 */           damageEffects = (angledDamage.damageEffects == null) ? damageEffects : angledDamage.damageEffects;
/* 400 */           nextLabel = 3 + i;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 406 */     String hitDetail = (String)context.getMetaStore().getIfPresentMetaObject(HIT_DETAIL);
/* 407 */     if (hitDetail != null) {
/*     */       
/* 409 */       TargetedDamage entry = this.targetedDamage.get(hitDetail);
/* 410 */       if (entry != null) {
/* 411 */         damageCalculator = (entry.damageCalculator == null) ? damageCalculator : entry.damageCalculator;
/* 412 */         damageEffects = (entry.damageEffects == null) ? damageEffects : entry.damageEffects;
/* 413 */         nextLabel = entry.index;
/*     */       } 
/*     */     } 
/*     */     
/* 417 */     context.getInstanceStore().putMetaObject(NEXT_INDEX, Integer.valueOf(nextLabel));
/* 418 */     if (damageCalculator == null)
/*     */       return; 
/* 420 */     DynamicMetaStore<Interaction> metaStore = (DynamicMetaStore<Interaction>)context.getMetaStore().getMetaObject(SelectInteraction.SELECT_META_STORE);
/* 421 */     DamageCalculatorSystems.Sequence sequentialHits = (metaStore == null) ? new DamageCalculatorSystems.Sequence() : (DamageCalculatorSystems.Sequence)metaStore.getMetaObject(SEQUENTIAL_HITS);
/* 422 */     Object2FloatMap<DamageCause> damage = damageCalculator.calculateDamage(getRunTime());
/*     */ 
/*     */     
/* 425 */     HeadRotation attackerHeadRotationComponent = (HeadRotation)commandBuffer.getComponent(attackerRef, HeadRotation.getComponentType());
/* 426 */     if (attackerHeadRotationComponent != null) {
/* 427 */       attackerDirection = attackerHeadRotationComponent.getRotation();
/*     */     } else {
/* 429 */       attackerDirection = Vector3f.ZERO;
/*     */     } 
/*     */     
/* 432 */     if (damage != null && !damage.isEmpty()) {
/*     */ 
/*     */ 
/*     */       
/* 436 */       double[] knockbackMultiplier = { 1.0D };
/* 437 */       float[] armorDamageModifiers = { 0.0F, 1.0F };
/*     */       
/* 439 */       calculateKnockbackAndArmorModifiers(damageCalculator.getDamageClass(), damage, targetRef, attackerRef, armorDamageModifiers, knockbackMultiplier, (ComponentAccessor<EntityStore>)commandBuffer);
/*     */       
/* 441 */       KnockbackComponent knockbackComponent = null;
/* 442 */       if (damageEffects != null && damageEffects.getKnockback() != null) {
/* 443 */         knockbackComponent = (KnockbackComponent)commandBuffer.getComponent(targetRef, KnockbackComponent.getComponentType());
/* 444 */         if (knockbackComponent == null) {
/* 445 */           knockbackComponent = new KnockbackComponent();
/* 446 */           commandBuffer.putComponent(targetRef, KnockbackComponent.getComponentType(), (Component)knockbackComponent);
/*     */         } 
/* 448 */         Knockback knockback = damageEffects.getKnockback();
/* 449 */         knockbackComponent.setVelocity(knockback.calculateVector(attackerPos, attackerDirection.getYaw(), targetPos).scale(knockbackMultiplier[0]));
/* 450 */         knockbackComponent.setVelocityType(knockback.getVelocityType());
/* 451 */         knockbackComponent.setVelocityConfig(knockback.getVelocityConfig());
/* 452 */         knockbackComponent.setDuration(knockback.getDuration());
/*     */       } 
/*     */       
/* 455 */       Player attackerPlayerComponent = (Player)commandBuffer.getComponent(attackerRef, Player.getComponentType());
/* 456 */       ItemStack itemInHand = (attackerPlayerComponent == null || attackerPlayerComponent.canApplyItemStackPenalties(attackerRef, (ComponentAccessor)commandBuffer)) ? context.getHeldItem() : null;
/* 457 */       Damage[] hits = DamageCalculatorSystems.queueDamageCalculator(((EntityStore)commandBuffer.getExternalData()).getWorld(), damage, targetRef, context.getCommandBuffer(), source, itemInHand);
/*     */       
/* 459 */       if (hits.length > 0) {
/* 460 */         Damage firstDamage = hits[0];
/* 461 */         DamageCalculatorSystems.DamageSequence seq = new DamageCalculatorSystems.DamageSequence(sequentialHits, damageCalculator);
/* 462 */         seq.setEntityStatOnHit(this.entityStatsOnHit);
/* 463 */         firstDamage.putMetaObject(DamageCalculatorSystems.DAMAGE_SEQUENCE, seq);
/* 464 */         if (damageEffects != null) {
/* 465 */           damageEffects.addToDamage(firstDamage);
/*     */         }
/*     */         
/* 468 */         for (Damage damageEvent : hits) {
/* 469 */           if (knockbackComponent != null) damageEvent.putMetaObject(Damage.KNOCKBACK_COMPONENT, knockbackComponent); 
/* 470 */           float damageValue = damageEvent.getAmount();
/* 471 */           damageValue += armorDamageModifiers[0];
/* 472 */           damageEvent.setAmount(damageValue * Math.max(0.0F, armorDamageModifiers[1]));
/* 473 */           if (hit != null) {
/* 474 */             damageEvent.putMetaObject(Damage.HIT_LOCATION, hit);
/*     */             
/* 476 */             float hitAngleRad = TrigMathUtil.atan2(attackerPos.x - hit.x, attackerPos.z - hit.z);
/* 477 */             hitAngleRad = MathUtil.wrapAngle(hitAngleRad - attackerDirection.getYaw());
/*     */             
/* 479 */             float hitAngleDeg = hitAngleRad * 57.295776F;
/* 480 */             damageEvent.putMetaObject(Damage.HIT_ANGLE, Float.valueOf(hitAngleDeg));
/*     */           } 
/*     */           
/* 483 */           commandBuffer.invoke(targetRef, (EcsEvent)damageEvent);
/*     */         } 
/*     */         
/* 486 */         processDamage(context, hits);
/*     */       } 
/*     */ 
/*     */       
/* 490 */       context.getInstanceStore().putMetaObject(QUEUED_DAMAGE, hits);
/*     */     } 
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
/*     */   private static void calculateKnockbackAndArmorModifiers(@Nonnull DamageClass damageClass, @Nonnull Object2FloatMap<DamageCause> damage, @Nonnull Ref<EntityStore> targetRef, @Nonnull Ref<EntityStore> attackerRef, float[] armorDamageModifiers, double[] knockbackMultiplier, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*     */     LivingEntity livingEntity;
/* 513 */     EffectControllerComponent effectControllerComponent = (EffectControllerComponent)componentAccessor.getComponent(targetRef, EffectControllerComponent.getComponentType());
/*     */ 
/*     */     
/* 516 */     if (effectControllerComponent != null) {
/* 517 */       knockbackMultiplier[0] = IntStream.of(effectControllerComponent
/* 518 */           .getActiveEffectIndexes())
/* 519 */         .mapToObj(i -> (EntityEffect)((IndexedLookupTableAssetMap)EntityEffect.getAssetStore().getAssetMap()).getAsset(i))
/* 520 */         .filter(effect -> (effect != null && effect.getApplicationEffects() != null))
/* 521 */         .mapToDouble(effect -> effect.getApplicationEffects().getKnockbackMultiplier())
/* 522 */         .reduce(1.0D, (a, b) -> a * b);
/*     */     }
/*     */     
/* 525 */     Entity entity = EntityUtils.getEntity(attackerRef, componentAccessor); if (entity instanceof LivingEntity) { livingEntity = (LivingEntity)entity; } else { return; }
/* 526 */      Inventory inventory = livingEntity.getInventory();
/* 527 */     if (inventory == null) {
/*     */       return;
/*     */     }
/* 530 */     ItemContainer armorContainer = inventory.getArmor();
/* 531 */     if (armorContainer == null)
/* 532 */       return;  float knockbackEnhancementModifier = 1.0F; short i;
/* 533 */     for (i = 0; i < armorContainer.getCapacity(); i = (short)(i + 1)) {
/* 534 */       ItemStack itemStack = armorContainer.getItemStack(i);
/* 535 */       if (itemStack != null && !itemStack.isEmpty()) {
/*     */         
/* 537 */         Item item = itemStack.getItem();
/* 538 */         if (item.getArmor() != null) {
/*     */           
/* 540 */           Map<DamageCause, StaticModifier[]> armorDamageEnhancementMap = item.getArmor().getDamageEnhancementValues();
/*     */           
/* 542 */           for (ObjectIterator<DamageCause> objectIterator = damage.keySet().iterator(); objectIterator.hasNext(); ) { DamageCause damageCause = objectIterator.next();
/* 543 */             if (armorDamageEnhancementMap != null) {
/* 544 */               StaticModifier[] armorDamageEnhancementValue = armorDamageEnhancementMap.get(damageCause);
/* 545 */               if (armorDamageEnhancementValue != null) {
/* 546 */                 for (StaticModifier staticModifier : armorDamageEnhancementValue) {
/* 547 */                   if (staticModifier.getCalculationType() == StaticModifier.CalculationType.ADDITIVE) {
/* 548 */                     armorDamageModifiers[0] = armorDamageModifiers[0] + staticModifier.getAmount();
/*     */                   } else {
/*     */                     
/* 551 */                     armorDamageModifiers[1] = armorDamageModifiers[1] + staticModifier.getAmount();
/*     */                   } 
/*     */                 } 
/*     */               }
/*     */             } 
/* 556 */             Map<DamageCause, Float> knockbackEnhancements = item.getArmor().getKnockbackEnhancements();
/* 557 */             if (knockbackEnhancements == null)
/* 558 */               continue;  knockbackEnhancementModifier += ((Float)knockbackEnhancements.get(damageCause)).floatValue(); }
/*     */ 
/*     */           
/* 561 */           StaticModifier[] damageClassModifier = (StaticModifier[])item.getArmor().getDamageClassEnhancement().get(damageClass);
/* 562 */           if (damageClassModifier != null)
/* 563 */             for (StaticModifier modifier : damageClassModifier) {
/* 564 */               if (modifier.getCalculationType() == StaticModifier.CalculationType.ADDITIVE) {
/* 565 */                 armorDamageModifiers[0] = armorDamageModifiers[0] + modifier.getAmount();
/*     */               } else {
/*     */                 
/* 568 */                 armorDamageModifiers[1] = armorDamageModifiers[1] + modifier.getAmount();
/*     */               } 
/*     */             }  
/*     */         } 
/*     */       } 
/* 573 */     }  knockbackMultiplier[0] = knockbackMultiplier[0] * knockbackEnhancementModifier;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class TargetedDamage
/*     */   {
/*     */     public static final BuilderCodec<TargetedDamage> CODEC;
/*     */ 
/*     */ 
/*     */     
/*     */     protected int index;
/*     */ 
/*     */ 
/*     */     
/*     */     protected DamageCalculator damageCalculator;
/*     */ 
/*     */ 
/*     */     
/*     */     protected Map<String, TargetEntityEffect> targetEntityEffects;
/*     */ 
/*     */     
/*     */     protected DamageEffects damageEffects;
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     protected String next;
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/* 605 */       CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(TargetedDamage.class, TargetedDamage::new).appendInherited(new KeyedCodec("DamageCalculator", (Codec)DamageCalculator.CODEC), (i, a) -> i.damageCalculator = a, i -> i.damageCalculator, (i, parent) -> i.damageCalculator = parent.damageCalculator).add()).appendInherited(new KeyedCodec("TargetEntityEffects", (Codec)new MapCodec((Codec)TargetEntityEffect.CODEC, java.util.HashMap::new)), (i, map) -> i.targetEntityEffects = map, i -> i.targetEntityEffects, (i, parent) -> i.targetEntityEffects = parent.targetEntityEffects).add()).appendInherited(new KeyedCodec("DamageEffects", (Codec)DamageEffects.CODEC), (i, o) -> i.damageEffects = o, i -> i.damageEffects, (i, parent) -> i.damageEffects = parent.damageEffects).add()).appendInherited(new KeyedCodec("Next", Interaction.CHILD_ASSET_CODEC), (interaction, s) -> interaction.next = s, interaction -> interaction.next, (interaction, parent) -> interaction.next = parent.next).documentation("The interactions to run when this interaction succeeds.").addValidatorLate(() -> Interaction.VALIDATOR_CACHE.getValidator().late()).add()).build();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public com.hypixel.hytale.protocol.TargetedDamage toTargetedDamagePacket() {
/* 616 */       return new com.hypixel.hytale.protocol.TargetedDamage(this.index, this.damageEffects.toPacket(), Interaction.getInteractionIdOrUnknown(this.next));
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 622 */       return "TargetedDamage{damageCalculator=" + String.valueOf(this.damageCalculator) + ", targetEntityEffects=" + String.valueOf(this.targetEntityEffects) + ", damageEffects=" + String.valueOf(this.damageEffects) + ", next='" + this.next + "'}";
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class AngledDamage
/*     */     extends TargetedDamage
/*     */   {
/*     */     public static final BuilderCodec<AngledDamage> CODEC;
/*     */ 
/*     */ 
/*     */     
/*     */     protected float angleRad;
/*     */ 
/*     */ 
/*     */     
/*     */     protected float angleDistanceRad;
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/* 645 */       CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(AngledDamage.class, AngledDamage::new, DamageEntityInteraction.TargetedDamage.CODEC).appendInherited(new KeyedCodec("Angle", (Codec)Codec.FLOAT), (o, i) -> o.angleRad = i.floatValue() * 0.017453292F, o -> Float.valueOf(o.angleRad * 57.295776F), (o, p) -> o.angleRad = p.angleRad).add()).appendInherited(new KeyedCodec("AngleDistance", (Codec)Codec.FLOAT), (o, i) -> o.angleDistanceRad = i.floatValue() * 0.017453292F, o -> Float.valueOf(o.angleDistanceRad * 57.295776F), (o, p) -> o.angleDistanceRad = p.angleDistanceRad).add()).build();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public com.hypixel.hytale.protocol.AngledDamage toAngledDamagePacket() {
/* 652 */       DamageEffects damageEffectsPacket = (this.damageEffects == null) ? null : this.damageEffects.toPacket();
/* 653 */       return new com.hypixel.hytale.protocol.AngledDamage(this.angleRad, this.angleDistanceRad, damageEffectsPacket, Interaction.getInteractionIdOrUnknown(this.next));
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 659 */       return "AngledDamage{angleRad=" + this.angleRad + ", angleDistanceRad=" + this.angleDistanceRad + "} " + super
/*     */ 
/*     */         
/* 662 */         .toString();
/*     */     }
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
/*     */   public static class EntityStatOnHit
/*     */     implements NetworkSerializable<com.hypixel.hytale.protocol.EntityStatOnHit>
/*     */   {
/*     */     public static final BuilderCodec<EntityStatOnHit> CODEC;
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
/*     */     public EntityStatOnHit() {
/* 717 */       this.multipliersPerEntitiesHit = DEFAULT_MULTIPLIERS_PER_ENTITIES_HIT;
/* 718 */       this.multiplierPerExtraEntityHit = 0.05F;
/*     */     } static { CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(EntityStatOnHit.class, EntityStatOnHit::new).appendInherited(new KeyedCodec("EntityStatId", (Codec)Codec.STRING), (entityStatOnHitInteraction, s) -> entityStatOnHitInteraction.entityStatId = s, entityStatOnHitInteraction -> entityStatOnHitInteraction.entityStatId, (entityStatOnHitInteraction, parent) -> entityStatOnHitInteraction.entityStatId = parent.entityStatId).documentation("The id of the EntityStat that will be affected by the interaction.").addValidator(Validators.nonNull()).addValidator(EntityStatType.VALIDATOR_CACHE.getValidator()).add()).appendInherited(new KeyedCodec("Amount", (Codec)Codec.FLOAT), (entityStatOnHitInteraction, integer) -> entityStatOnHitInteraction.amount = integer.floatValue(), entityStatOnHitInteraction -> Float.valueOf(entityStatOnHitInteraction.amount), (entityStatOnHitInteraction, parent) -> entityStatOnHitInteraction.amount = parent.amount).documentation("The base amount for a single entity hit.").add()).appendInherited(new KeyedCodec("MultipliersPerEntitiesHit", (Codec)Codec.FLOAT_ARRAY), (entityStatOnHitInteraction, doubles) -> entityStatOnHitInteraction.multipliersPerEntitiesHit = doubles, entityStatOnHitInteraction -> entityStatOnHitInteraction.multipliersPerEntitiesHit, (entityStatOnHitInteraction, parent) -> entityStatOnHitInteraction.multipliersPerEntitiesHit = parent.multipliersPerEntitiesHit).documentation("An array of multipliers corresponding to how much the amount should be multiplied by for each entity hit.").addValidator(Validators.nonEmptyFloatArray()).add()).appendInherited(new KeyedCodec("MultiplierPerExtraEntityHit", (Codec)Codec.FLOAT), (entityStatOnHitInteraction, aDouble) -> entityStatOnHitInteraction.multiplierPerExtraEntityHit = aDouble.floatValue(), entityStatOnHitInteraction -> Float.valueOf(entityStatOnHitInteraction.multiplierPerExtraEntityHit), (entityStatOnHitInteraction, parent) -> entityStatOnHitInteraction.multiplierPerExtraEntityHit = parent.multiplierPerExtraEntityHit).documentation("When the number of entity hit is higher than the number of multipliers defined, the amount will be multiplied by this multiplier for each extra entity hit.").add()).afterDecode(entityStatOnHitInteraction -> {
/*     */             if (entityStatOnHitInteraction.entityStatId == null)
/*     */               return;  entityStatOnHitInteraction.entityStatIndex = EntityStatType.getAssetMap().getIndex(entityStatOnHitInteraction.entityStatId);
/*     */           })).build(); } public static final float[] DEFAULT_MULTIPLIERS_PER_ENTITIES_HIT = new float[] { 1.0F, 0.6F, 0.4F, 0.2F, 0.1F }; public static final float DEFAULT_MULTIPLIER_PER_EXTRA_ENTITY_HIT = 0.05F; protected String entityStatId; public void processEntityStatsOnHit(int hits, @Nonnull EntityStatMap statMap) { float multiplier;
/* 723 */       if (hits == 0) {
/*     */         return;
/*     */       }
/*     */       
/* 727 */       if (hits <= this.multipliersPerEntitiesHit.length) {
/* 728 */         multiplier = this.multipliersPerEntitiesHit[hits - 1];
/*     */       } else {
/* 730 */         multiplier = this.multiplierPerExtraEntityHit;
/*     */       } 
/*     */       
/* 733 */       statMap.addStatValue(EntityStatMap.Predictable.SELF, this.entityStatIndex, multiplier * this.amount); }
/*     */     
/*     */     protected float amount; protected float[] multipliersPerEntitiesHit; protected float multiplierPerExtraEntityHit; private int entityStatIndex;
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 739 */       return "EntityStatOnHit{entityStatId='" + this.entityStatId + "', amount=" + this.amount + ", multipliersPerEntitiesHit=" + 
/*     */ 
/*     */         
/* 742 */         Arrays.toString(this.multipliersPerEntitiesHit) + ", multiplierPerExtraEntityHit=" + this.multiplierPerExtraEntityHit + ", entityStatIndex=" + this.entityStatIndex + "}";
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public com.hypixel.hytale.protocol.EntityStatOnHit toPacket() {
/* 751 */       return new com.hypixel.hytale.protocol.EntityStatOnHit(this.entityStatIndex, this.amount, this.multipliersPerEntitiesHit, this.multiplierPerExtraEntityHit);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\server\DamageEntityInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */