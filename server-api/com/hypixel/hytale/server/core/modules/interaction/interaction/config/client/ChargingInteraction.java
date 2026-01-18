/*     */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config.client;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.map.EnumMapCodec;
/*     */ import com.hypixel.hytale.codec.codecs.map.Float2ObjectMapCodec;
/*     */ import com.hypixel.hytale.codec.validation.LateValidator;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.protocol.ChargingDelay;
/*     */ import com.hypixel.hytale.protocol.Interaction;
/*     */ import com.hypixel.hytale.protocol.InteractionState;
/*     */ import com.hypixel.hytale.protocol.InteractionSyncData;
/*     */ import com.hypixel.hytale.protocol.InteractionType;
/*     */ import com.hypixel.hytale.protocol.WaitForDataFrom;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionChain;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionManager;
/*     */ import com.hypixel.hytale.server.core.entity.damage.DamageDataComponent;
/*     */ import com.hypixel.hytale.server.core.meta.MetaKey;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.IInteractionSimulationHandler;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.RootInteraction;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.data.Collector;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.data.CollectorTag;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.data.StringTag;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.operation.Label;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.operation.Operation;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.operation.OperationsBuilder;
/*     */ import com.hypixel.hytale.server.core.modules.time.TimeResource;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.floats.Float2IntOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.floats.Float2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.floats.FloatIterator;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import java.util.Arrays;
/*     */ import java.util.Map;
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
/*     */ public class ChargingInteraction
/*     */   extends Interaction
/*     */ {
/*     */   @Nonnull
/*     */   public static final BuilderCodec<ChargingDelay> DELAY_CODEC;
/*     */   public static final BuilderCodec<ChargingInteraction> ABSTRACT_CODEC;
/*     */   @Nonnull
/*     */   public static final BuilderCodec<ChargingInteraction> CODEC;
/*     */   
/*     */   static {
/* 106 */     DELAY_CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ChargingDelay.class, ChargingDelay::new).documentation("Configuration for delay when the user is attacked.\nThe delay will be between **MinDelay** when the incoming at **MinHealth** and **MaxDelay** when the incoming damage is at or above **MaxHealth**.")).appendInherited(new KeyedCodec("MinDelay", (Codec)Codec.FLOAT), (o, i) -> o.minDelay = i.floatValue(), o -> Float.valueOf(o.minDelay), (o, p) -> o.minDelay = p.minDelay).documentation("The smallest amount of delay that can be applied.").addValidator(Validators.nonNull()).addValidator(Validators.greaterThanOrEqual(Float.valueOf(0.0F))).add()).appendInherited(new KeyedCodec("MaxDelay", (Codec)Codec.FLOAT), (o, i) -> o.maxDelay = i.floatValue(), o -> Float.valueOf(o.maxDelay), (o, p) -> o.maxDelay = p.maxDelay).documentation("The largest amount of delay that can be applied.").addValidator(Validators.nonNull()).addValidator(Validators.greaterThanOrEqual(Float.valueOf(0.0F))).add()).appendInherited(new KeyedCodec("MaxTotalDelay", (Codec)Codec.FLOAT), (o, i) -> o.maxTotalDelay = i.floatValue(), o -> Float.valueOf(o.maxTotalDelay), (o, p) -> o.maxTotalDelay = p.maxTotalDelay).documentation("The max amount of delay applied during this interaction before any additional delay is ignored.").addValidator(Validators.nonNull()).addValidator(Validators.greaterThanOrEqual(Float.valueOf(0.0F))).add()).appendInherited(new KeyedCodec("MinHealth", (Codec)Codec.FLOAT), (o, i) -> o.minHealth = i.floatValue(), o -> Float.valueOf(o.minHealth), (o, p) -> o.minHealth = p.minHealth).documentation("The amount of health (as a percentage between 1.0 and 0.0) where if the user's health is below the value then the delay wont be applied.").addValidator(Validators.nonNull()).addValidator(Validators.greaterThanOrEqual(Float.valueOf(0.0F))).add()).appendInherited(new KeyedCodec("MaxHealth", (Codec)Codec.FLOAT), (o, i) -> o.maxHealth = i.floatValue(), o -> Float.valueOf(o.maxHealth), (o, p) -> o.maxHealth = p.maxHealth).documentation("The amount of health (as a percentage between 1.0 and 0.0) where if the user's health is above the value then the delay will be capped.").addValidator(Validators.nonNull()).addValidator(Validators.greaterThanOrEqual(Float.valueOf(0.0F))).add()).build();
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
/*     */     
/* 161 */     ABSTRACT_CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.abstractBuilder(ChargingInteraction.class, Interaction.ABSTRACT_CODEC).appendInherited(new KeyedCodec("FailOnDamage", (Codec)Codec.BOOLEAN), (interaction, s) -> interaction.failOnDamage = s.booleanValue(), interaction -> Boolean.valueOf(interaction.failOnDamage), (interaction, parent) -> interaction.failOnDamage = parent.failOnDamage).documentation("Whether the interaction will be cancelled and the item removed when the entity takes damage").add()).appendInherited(new KeyedCodec("CancelOnOtherClick", (Codec)Codec.BOOLEAN), (interaction, b) -> interaction.cancelOnOtherClick = b.booleanValue(), interaction -> Boolean.valueOf(interaction.cancelOnOtherClick), (interaction, parent) -> interaction.cancelOnOtherClick = parent.cancelOnOtherClick).add()).appendInherited(new KeyedCodec("Forks", (Codec)new EnumMapCodec(InteractionType.class, (Codec)RootInteraction.CHILD_ASSET_CODEC)), (o, i) -> o.forks = i, o -> o.forks, (o, p) -> o.forks = p.forks).documentation("A collection of interactions to fork into when the input associated with the interaction type is used.\n\nFor example listing a `Primary` interaction type here with interactions will allow the user to press the input tied to the `Primary` interaction type whilst holding the input used to run the current interaction to run the specified interactions. e.g. Having a shield that you can hold `Secondary` to block and whilst blocking press `Primary` to shield bash.\n\nThis does not cancel the current interaction when triggered but the `CancelOnOtherClick` check will still run and may cancel the interaction.\n\nThe existing forks will continue to run even if this interaction ends.").addValidatorLate(() -> RootInteraction.VALIDATOR_CACHE.getMapValueValidator().late()).add()).afterDecode(interaction -> { float max = 0.0F; if (interaction.next != null) { FloatIterator iterator = interaction.next.keySet().iterator(); while (iterator.hasNext()) { float nextFloat = iterator.nextFloat(); if (nextFloat > max) max = nextFloat;  }  interaction.sortedKeys = interaction.next.keySet().toFloatArray(); Arrays.sort(interaction.sortedKeys); }  interaction.highestChargeValue = max; })).appendInherited(new KeyedCodec("Failed", Interaction.CHILD_ASSET_CODEC), (interaction, s) -> interaction.failed = s, interaction -> interaction.failed, (interaction, parent) -> interaction.failed = parent.failed).documentation("The interactions to run when this interaction fails.").addValidatorLate(() -> VALIDATOR_CACHE.getValidator().late()).add()).build();
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
/*     */     
/* 216 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ChargingInteraction.class, ChargingInteraction::new, ABSTRACT_CODEC).documentation("An interaction that holds until the key is released (or a time limit is reached) and executes different interactions based on how long the key was pressed.")).appendInherited(new KeyedCodec("AllowIndefiniteHold", (Codec)Codec.BOOLEAN), (interaction, s) -> interaction.allowIndefiniteHold = s.booleanValue(), interaction -> Boolean.valueOf(interaction.allowIndefiniteHold), (interaction, parent) -> interaction.allowIndefiniteHold = parent.allowIndefiniteHold).add()).appendInherited(new KeyedCodec("DisplayProgress", (Codec)Codec.BOOLEAN), (interaction, s) -> interaction.displayProgress = s.booleanValue(), interaction -> Boolean.valueOf(interaction.displayProgress), (interaction, parent) -> interaction.displayProgress = parent.displayProgress).add()).appendInherited(new KeyedCodec("Next", (Codec)new Float2ObjectMapCodec(Interaction.CHILD_ASSET_CODEC, it.unimi.dsi.fastutil.floats.Float2ObjectOpenHashMap::new)), (interaction, s) -> interaction.next = s, interaction -> interaction.next, (interaction, parent) -> interaction.next = parent.next).addValidatorLate(() -> VALIDATOR_CACHE.getMapValueValidator().late()).add()).appendInherited(new KeyedCodec("MouseSensitivityAdjustmentTarget", (Codec)Codec.FLOAT), (interaction, doubles) -> interaction.mouseSensitivityAdjustmentTarget = doubles.floatValue(), interaction -> Float.valueOf(interaction.mouseSensitivityAdjustmentTarget), (interaction, parent) -> interaction.mouseSensitivityAdjustmentTarget = parent.mouseSensitivityAdjustmentTarget).documentation("What is the target modifier to apply to mouse sensitivity while this interaction is active.").addValidator(Validators.range(Float.valueOf(0.0F), Float.valueOf(1.0F))).add()).appendInherited(new KeyedCodec("MouseSensitivityAdjustmentDuration", (Codec)Codec.FLOAT), (interaction, doubles) -> interaction.mouseSensitivityAdjustmentDuration = doubles.floatValue(), interaction -> Float.valueOf(interaction.mouseSensitivityAdjustmentDuration), (interaction, parent) -> interaction.mouseSensitivityAdjustmentDuration = parent.mouseSensitivityAdjustmentDuration).documentation("Override the global linear modifier adjustment with this as the time to go from 1.0 to 0.0.").add()).appendInherited(new KeyedCodec("Delay", (Codec)DELAY_CODEC), (o, i) -> o.chargingDelay = i, o -> o.chargingDelay, (o, p) -> o.chargingDelay = p.chargingDelay).documentation("Settings that allow for delaying the charging interaction on damage.").add()).build();
/*     */   }
/* 218 */   private static final MetaKey<Object2IntMap<InteractionType>> FORK_COUNTS = Interaction.META_REGISTRY.registerMetaObject(i -> new Object2IntOpenHashMap());
/* 219 */   private static final MetaKey<InteractionChain> FORKED_CHAIN = Interaction.META_REGISTRY.registerMetaObject(i -> null);
/*     */   
/*     */   private static final float CHARGING_HELD = -1.0F;
/*     */   
/*     */   private static final float CHARGING_CANCELED = -2.0F;
/* 224 */   private static final StringTag TAG_FAILED = StringTag.of("Failed");
/*     */   
/*     */   protected boolean allowIndefiniteHold;
/*     */   protected boolean displayProgress = true;
/*     */   protected boolean cancelOnOtherClick = true;
/*     */   protected boolean failOnDamage;
/* 230 */   protected float mouseSensitivityAdjustmentTarget = 1.0F;
/* 231 */   protected float mouseSensitivityAdjustmentDuration = 1.0F;
/*     */   
/*     */   @Nullable
/*     */   protected String failed;
/*     */   
/*     */   @Nullable
/*     */   protected Float2ObjectMap<String> next;
/*     */   
/*     */   protected float[] sortedKeys;
/*     */   
/*     */   protected Map<InteractionType, String> forks;
/*     */   
/*     */   @Nullable
/*     */   protected ChargingDelay chargingDelay;
/*     */   
/*     */   protected float highestChargeValue;
/*     */   
/*     */   @Nonnull
/*     */   public WaitForDataFrom getWaitForDataFrom() {
/* 250 */     return WaitForDataFrom.Client;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void tick0(boolean firstRun, float time, @NonNullDecl InteractionType type, @Nonnull InteractionContext context, @NonNullDecl CooldownHandler cooldownHandler) {
/* 255 */     InteractionSyncData clientData = context.getClientState();
/*     */     
/* 257 */     if ((context.getClientState()).state == InteractionState.Failed && context.hasLabels()) {
/* 258 */       (context.getState()).state = InteractionState.Failed;
/* 259 */       context.jump(context.getLabel((this.next != null) ? this.next.size() : 0));
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 265 */     if (clientData.forkCounts != null && this.forks != null) {
/*     */       int i;
/*     */       
/* 268 */       Object2IntMap<InteractionType> serverForkCounts = (Object2IntMap<InteractionType>)context.getInstanceStore().getMetaObject(FORK_COUNTS);
/* 269 */       InteractionChain forked = (InteractionChain)context.getInstanceStore().getMetaObject(FORKED_CHAIN);
/* 270 */       if (forked != null && forked.getServerState() != InteractionState.NotFinished) forked = null;
/*     */       
/* 272 */       boolean matches = true;
/* 273 */       for (Map.Entry<InteractionType, Integer> e : (Iterable<Map.Entry<InteractionType, Integer>>)clientData.forkCounts.entrySet()) {
/* 274 */         int serverCount = serverForkCounts.getInt(e.getKey());
/* 275 */         String forkInteraction = this.forks.get(e.getKey());
/* 276 */         if (forked == null && serverCount < ((Integer)e.getValue()).intValue() && forkInteraction != null) {
/* 277 */           InteractionContext forkContext = context.duplicate();
/* 278 */           forked = context.fork(e.getKey(), forkContext, RootInteraction.getRootInteractionOrUnknown(forkInteraction), true);
/* 279 */           context.getInstanceStore().putMetaObject(FORKED_CHAIN, forked);
/* 280 */           serverCount++;
/* 281 */           serverForkCounts.put(e.getKey(), serverCount);
/*     */         } 
/* 283 */         i = matches & ((serverCount == ((Integer)e.getValue()).intValue()) ? 1 : 0);
/*     */       } 
/*     */       
/* 286 */       if (i == 0) {
/*     */         
/* 288 */         (context.getState()).state = InteractionState.NotFinished;
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 293 */     if (clientData.chargeValue == -1.0F) {
/* 294 */       (context.getState()).state = InteractionState.NotFinished; return;
/*     */     } 
/* 296 */     if (clientData.chargeValue == -2.0F) {
/*     */       
/* 298 */       (context.getState()).state = InteractionState.Finished;
/*     */       
/*     */       return;
/*     */     } 
/* 302 */     (context.getState()).state = InteractionState.Finished;
/*     */     
/* 304 */     float chargeValue = clientData.chargeValue;
/*     */     
/* 306 */     if (this.next == null)
/* 307 */       return;  jumpToChargeValue(context, chargeValue);
/*     */     
/* 309 */     CommandBuffer<EntityStore> commandBuffer = context.getCommandBuffer();
/* 310 */     assert commandBuffer != null;
/*     */     
/* 312 */     Ref<EntityStore> ref = context.getEntity();
/*     */     
/* 314 */     DamageDataComponent damageDataComponent = (DamageDataComponent)commandBuffer.getComponent(ref, DamageDataComponent.getComponentType());
/* 315 */     assert damageDataComponent != null;
/*     */     
/* 317 */     damageDataComponent.setLastChargeTime(((TimeResource)commandBuffer.getResource(TimeResource.getResourceType())).getNow());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void simulateTick0(boolean firstRun, float time, @NonNullDecl InteractionType type, @Nonnull InteractionContext context, @NonNullDecl CooldownHandler cooldownHandler) {
/* 323 */     Ref<EntityStore> ref = context.getEntity();
/* 324 */     IInteractionSimulationHandler simulationHandler = context.getInteractionManager().getInteractionSimulationHandler();
/*     */ 
/*     */ 
/*     */     
/* 328 */     if (simulationHandler.isCharging(firstRun, time, type, context, ref, cooldownHandler) && (this.allowIndefiniteHold || time < this.highestChargeValue)) {
/* 329 */       if (this.failOnDamage && simulationHandler.shouldCancelCharging(firstRun, time, type, context, ref, cooldownHandler)) {
/* 330 */         (context.getState()).state = InteractionState.Failed;
/*     */         
/*     */         return;
/*     */       } 
/* 334 */       (context.getState()).state = InteractionState.NotFinished;
/*     */     } else {
/* 336 */       (context.getState()).state = InteractionState.Finished;
/* 337 */       float chargeValue = simulationHandler.getChargeValue(firstRun, time, type, context, ref, cooldownHandler);
/* 338 */       (context.getState()).chargeValue = chargeValue;
/*     */       
/* 340 */       if (this.next == null)
/* 341 */         return;  jumpToChargeValue(context, chargeValue);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void jumpToChargeValue(@Nonnull InteractionContext context, float chargeValue) {
/* 347 */     float closestDiff = 2.1474836E9F;
/* 348 */     int closestValue = -1;
/* 349 */     int index = 0;
/* 350 */     for (float e : this.sortedKeys) {
/* 351 */       if (chargeValue < e) {
/* 352 */         index++;
/*     */       }
/*     */       else {
/*     */         
/* 356 */         float diff = chargeValue - e;
/* 357 */         if (closestValue == -1 || diff < closestDiff) {
/* 358 */           closestDiff = diff;
/* 359 */           closestValue = index;
/*     */         } 
/* 361 */         index++;
/*     */       } 
/* 363 */     }  if (closestValue != -1) context.jump(context.getLabel(closestValue));
/*     */   
/*     */   }
/*     */   
/*     */   public void compile(@Nonnull OperationsBuilder builder) {
/* 368 */     Label end = builder.createUnresolvedLabel();
/* 369 */     Label[] labels = new Label[((this.next != null) ? this.next.size() : 0) + 1];
/*     */     int i;
/* 371 */     for (i = 0; i < labels.length; i++) {
/* 372 */       labels[i] = builder.createUnresolvedLabel();
/*     */     }
/*     */     
/* 375 */     builder.addOperation((Operation)this, labels);
/* 376 */     builder.jump(end);
/*     */     
/* 378 */     if (this.sortedKeys != null) {
/* 379 */       for (i = 0; i < this.sortedKeys.length; i++) {
/* 380 */         float key = this.sortedKeys[i];
/* 381 */         builder.resolveLabel(labels[i]);
/* 382 */         Interaction interaction = Interaction.getInteractionOrUnknown((String)this.next.get(key));
/* 383 */         interaction.compile(builder);
/* 384 */         builder.jump(end);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 389 */     int failedIndex = (this.sortedKeys != null) ? this.sortedKeys.length : 0;
/* 390 */     builder.resolveLabel(labels[failedIndex]);
/*     */     
/* 392 */     if (this.failed != null) {
/* 393 */       Interaction interaction = Interaction.getInteractionOrUnknown(this.failed);
/* 394 */       interaction.compile(builder);
/*     */     } 
/*     */     
/* 397 */     builder.resolveLabel(end);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean walk(@Nonnull Collector collector, @Nonnull InteractionContext context) {
/* 402 */     if (this.next != null) {
/* 403 */       for (ObjectIterator<Float2ObjectMap.Entry<String>> objectIterator = this.next.float2ObjectEntrySet().iterator(); objectIterator.hasNext(); ) { Float2ObjectMap.Entry<String> entry = objectIterator.next();
/* 404 */         if (InteractionManager.walkInteraction(collector, context, ChargingTag.of(entry.getFloatKey()), (String)entry.getValue())) {
/* 405 */           return true;
/*     */         } }
/*     */     
/*     */     }
/*     */     
/* 410 */     if (this.failed != null && 
/* 411 */       InteractionManager.walkInteraction(collector, context, (CollectorTag)TAG_FAILED, this.failed)) {
/* 412 */       return true;
/*     */     }
/*     */     
/* 415 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected Interaction generatePacket() {
/* 421 */     return (Interaction)new com.hypixel.hytale.protocol.ChargingInteraction();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void configurePacket(Interaction packet) {
/* 426 */     super.configurePacket(packet);
/* 427 */     com.hypixel.hytale.protocol.ChargingInteraction p = (com.hypixel.hytale.protocol.ChargingInteraction)packet;
/*     */     
/* 429 */     p.allowIndefiniteHold = this.allowIndefiniteHold;
/* 430 */     p.mouseSensitivityAdjustmentTarget = this.mouseSensitivityAdjustmentTarget;
/* 431 */     p.mouseSensitivityAdjustmentDuration = this.mouseSensitivityAdjustmentDuration;
/* 432 */     p.displayProgress = this.displayProgress;
/* 433 */     p.cancelOnOtherClick = this.cancelOnOtherClick;
/* 434 */     p.failOnDamage = this.failOnDamage;
/* 435 */     p.failed = Interaction.getInteractionIdOrUnknown(this.failed);
/* 436 */     p.chargingDelay = this.chargingDelay;
/*     */     
/* 438 */     if (this.next != null) {
/* 439 */       Float2IntOpenHashMap chargedNext = new Float2IntOpenHashMap();
/* 440 */       for (ObjectIterator<Float2ObjectMap.Entry<String>> objectIterator = this.next.float2ObjectEntrySet().iterator(); objectIterator.hasNext(); ) { Float2ObjectMap.Entry<String> e = objectIterator.next();
/* 441 */         chargedNext.put(e.getFloatKey(), Interaction.getInteractionIdOrUnknown((String)e.getValue())); }
/*     */ 
/*     */       
/* 444 */       p.chargedNext = (Map)chargedNext;
/*     */     } 
/* 446 */     if (this.forks != null) {
/* 447 */       Object2IntOpenHashMap<InteractionType> intForks = new Object2IntOpenHashMap();
/* 448 */       for (Map.Entry<InteractionType, String> e : this.forks.entrySet()) {
/* 449 */         intForks.put(e.getKey(), RootInteraction.getRootInteractionIdOrUnknown(e.getValue()));
/*     */       }
/* 451 */       p.forks = (Map)intForks;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean needsRemoteSync() {
/* 457 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 463 */     return "ChargingInteraction{allowIndefiniteHold=" + this.allowIndefiniteHold + ", displayProgress=" + this.displayProgress + ", mouseSensitivityAdjustmentTarget=" + this.mouseSensitivityAdjustmentTarget + ", mouseSensitivityAdjustmentDuration=" + this.mouseSensitivityAdjustmentDuration + ", cancelOnOtherClick=" + this.cancelOnOtherClick + ", next=" + String.valueOf(this.next) + ", forks=" + String.valueOf(this.forks) + ", highestChargeValue=" + this.highestChargeValue + ", failOnDamage=" + this.failOnDamage + "} " + super
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 473 */       .toString();
/*     */   }
/*     */   
/*     */   private static class ChargingTag implements CollectorTag {
/*     */     private final float seconds;
/*     */     
/*     */     private ChargingTag(float seconds) {
/* 480 */       this.seconds = seconds;
/*     */     }
/*     */     
/*     */     public double getSeconds() {
/* 484 */       return this.seconds;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(@Nullable Object o) {
/* 489 */       if (this == o) return true; 
/* 490 */       if (o == null || getClass() != o.getClass()) return false;
/*     */       
/* 492 */       ChargingTag that = (ChargingTag)o;
/* 493 */       return (Float.compare(that.seconds, this.seconds) == 0);
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 498 */       return (this.seconds != 0.0F) ? Float.floatToIntBits(this.seconds) : 0;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 504 */       return "ChargingTag{seconds=" + this.seconds + "}";
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public static ChargingTag of(float seconds) {
/* 511 */       return new ChargingTag(seconds);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\client\ChargingInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */