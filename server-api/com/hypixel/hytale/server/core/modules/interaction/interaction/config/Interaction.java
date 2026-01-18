/*     */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config;
/*     */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*     */ import com.hypixel.hytale.assetstore.AssetKeyValidator;
/*     */ import com.hypixel.hytale.assetstore.AssetStore;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetCodec;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetCodecMapCodec;
/*     */ import com.hypixel.hytale.assetstore.codec.ContainedAssetCodec;
/*     */ import com.hypixel.hytale.assetstore.map.IndexedLookupTableAssetMap;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.codec.codecs.map.EnumMapCodec;
/*     */ import com.hypixel.hytale.codec.schema.metadata.Metadata;
/*     */ import com.hypixel.hytale.codec.schema.metadata.ui.UIEditor;
/*     */ import com.hypixel.hytale.codec.validation.ValidatorCache;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.spatial.SpatialResource;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector4d;
/*     */ import com.hypixel.hytale.protocol.BlockPosition;
/*     */ import com.hypixel.hytale.protocol.ForkedChainId;
/*     */ import com.hypixel.hytale.protocol.GameMode;
/*     */ import com.hypixel.hytale.protocol.Interaction;
/*     */ import com.hypixel.hytale.protocol.InteractionSettings;
/*     */ import com.hypixel.hytale.protocol.InteractionState;
/*     */ import com.hypixel.hytale.protocol.InteractionSyncData;
/*     */ import com.hypixel.hytale.protocol.InteractionType;
/*     */ import com.hypixel.hytale.protocol.ItemAnimation;
/*     */ import com.hypixel.hytale.protocol.packets.interaction.PlayInteractionFor;
/*     */ import com.hypixel.hytale.server.core.asset.common.BlockyAnimationCache;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.Item;
/*     */ import com.hypixel.hytale.server.core.asset.type.itemanimation.config.ItemPlayerAnimations;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionChain;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionManager;
/*     */ import com.hypixel.hytale.server.core.entity.LivingEntity;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.inventory.Inventory;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*     */ import com.hypixel.hytale.server.core.meta.MetaKey;
/*     */ import com.hypixel.hytale.server.core.meta.MetaRegistry;
/*     */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.damage.Damage;
/*     */ import com.hypixel.hytale.server.core.modules.entity.tracker.NetworkId;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.data.Collector;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.none.simple.SendMessageInteraction;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.operation.Operation;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.operation.OperationsBuilder;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectList;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectListIterator;
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public abstract class Interaction implements Operation, JsonAssetWithMap<String, IndexedLookupTableAssetMap<String, Interaction>>, NetworkSerializable<Interaction> {
/*     */   static {
/*  70 */     CODEC = new AssetCodecMapCodec((Codec)Codec.STRING, (t, k) -> t.id = k, t -> t.id, (t, data) -> t.data = data, t -> t.data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static final AssetCodecMapCodec<String, Interaction> CODEC;
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  82 */   public static final Codec<String> CHILD_ASSET_CODEC = (Codec<String>)new ContainedAssetCodec(Interaction.class, (AssetCodec)CODEC); @Nonnull
/*     */   public static final Codec<String[]> CHILD_ASSET_CODEC_ARRAY;
/*     */   @Nonnull
/*     */   public static final BuilderCodec<Interaction> ABSTRACT_CODEC;
/*     */   
/*     */   static {
/*  88 */     CHILD_ASSET_CODEC_ARRAY = (Codec<String[]>)new ArrayCodec(CHILD_ASSET_CODEC, x$0 -> new String[x$0]);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 175 */     ABSTRACT_CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.abstractBuilder(Interaction.class).appendInherited(new KeyedCodec("ViewDistance", (Codec)Codec.DOUBLE), (damageEffects, s) -> damageEffects.viewDistance = s.doubleValue(), damageEffects -> Double.valueOf(damageEffects.viewDistance), (damageEffects, parent) -> damageEffects.viewDistance = parent.viewDistance).documentation("Configures the distance in which other players will be able to see the effects of this interaction.").add()).appendInherited(new KeyedCodec("Effects", (Codec)InteractionEffects.CODEC), (interaction, o) -> interaction.effects = o, interaction -> interaction.effects, (interaction, parent) -> interaction.effects = parent.effects).documentation("Sets effects that will be applied whilst the interaction is running.").add()).appendInherited(new KeyedCodec("HorizontalSpeedMultiplier", (Codec)Codec.FLOAT), (activationEffects, s) -> activationEffects.horizontalSpeedMultiplier = s.floatValue(), activationEffects -> Float.valueOf(activationEffects.horizontalSpeedMultiplier), (activationEffects, parent) -> activationEffects.horizontalSpeedMultiplier = parent.horizontalSpeedMultiplier).documentation("The multiplier to apply to the horizontal speed of the entity whilst this interaction is running.").metadata((Metadata)new UIEditor((UIEditor.EditorComponent)new UIEditor.FormattedNumber(Double.valueOf(0.1D), null, null))).add()).appendInherited(new KeyedCodec("RunTime", (Codec)Codec.FLOAT), (activationEffects, s) -> activationEffects.runTime = s.floatValue(), activationEffects -> Float.valueOf(activationEffects.runTime), (activationEffects, parent) -> activationEffects.runTime = parent.runTime).documentation("The time in seconds this interaction should run for. \n\nIf *Effects.WaitForAnimationToFinish* is set and the length of the animation is longer than the runtime then the interaction will run for longer than the set time.").metadata((Metadata)new UIEditor((UIEditor.EditorComponent)new UIEditor.FormattedNumber(Double.valueOf(0.01D), "s", null))).add()).appendInherited(new KeyedCodec("CancelOnItemChange", (Codec)Codec.BOOLEAN), (damageEffects, s) -> damageEffects.cancelOnItemChange = s.booleanValue(), damageEffects -> Boolean.valueOf(damageEffects.cancelOnItemChange), (damageEffects, parent) -> damageEffects.cancelOnItemChange = parent.cancelOnItemChange).documentation("Whether the interaction will be cancelled when the entity's held item changes.").add()).appendInherited(new KeyedCodec("Rules", (Codec)InteractionRules.CODEC), (o, i) -> o.rules = i, o -> o.rules, (o, p) -> o.rules = p.rules).documentation("A set of rules that control when this interaction can run.").addValidator(Validators.nonNull()).add()).appendInherited(new KeyedCodec("Settings", (Codec)new EnumMapCodec(GameMode.class, (Codec)((BuilderCodec.Builder)BuilderCodec.builder(InteractionSettings.class, InteractionSettings::new).appendInherited(new KeyedCodec("AllowSkipOnClick", (Codec)Codec.BOOLEAN), (settings, s) -> settings.allowSkipOnClick = s.booleanValue(), settings -> Boolean.valueOf(settings.allowSkipOnClick), (settings, parent) -> settings.allowSkipOnClick = parent.allowSkipOnClick).documentation("Whether to skip this interaction when another click is sent.").add()).build())), (interaction, o) -> interaction.settings = o, interaction -> interaction.settings, (interaction, parent) -> interaction.settings = parent.settings).documentation("Per a gamemode settings.").add()).appendInherited(new KeyedCodec("Camera", (Codec)InteractionCameraSettings.CODEC), (o, i) -> o.camera = i, o -> o.camera, (o, p) -> o.camera = p.camera).documentation("Configures the camera behaviour for this interaction.").add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 181 */   public static final ValidatorCache<String> VALIDATOR_CACHE = new ValidatorCache((Validator)new AssetKeyValidator(Interaction::getAssetStore));
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static AssetStore<String, Interaction, IndexedLookupTableAssetMap<String, Interaction>> ASSET_STORE;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static AssetStore<String, Interaction, IndexedLookupTableAssetMap<String, Interaction>> getAssetStore() {
/* 193 */     if (ASSET_STORE == null) ASSET_STORE = AssetRegistry.getAssetStore(Interaction.class); 
/* 194 */     return ASSET_STORE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static IndexedLookupTableAssetMap<String, Interaction> getAssetMap() {
/* 201 */     return (IndexedLookupTableAssetMap<String, Interaction>)getAssetStore().getAssetMap();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 207 */   public static final MetaRegistry<InteractionContext> CONTEXT_META_REGISTRY = new MetaRegistry();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 212 */   public static final MetaRegistry<Interaction> META_REGISTRY = new MetaRegistry();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 217 */   public static final MetaKey<Ref<EntityStore>> TARGET_ENTITY = CONTEXT_META_REGISTRY.registerMetaObject(data -> null);
/*     */ 
/*     */ 
/*     */   
/* 221 */   public static final MetaKey<Vector4d> HIT_LOCATION = CONTEXT_META_REGISTRY.registerMetaObject(data -> null);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 226 */   public static final MetaKey<String> HIT_DETAIL = CONTEXT_META_REGISTRY.registerMetaObject(data -> null);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 231 */   public static final MetaKey<BlockPosition> TARGET_BLOCK = CONTEXT_META_REGISTRY.registerMetaObject(data -> null);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 236 */   public static final MetaKey<BlockPosition> TARGET_BLOCK_RAW = CONTEXT_META_REGISTRY.registerMetaObject(data -> null);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 241 */   public static final MetaKey<Integer> TARGET_SLOT = CONTEXT_META_REGISTRY.registerMetaObject(data -> Integer.valueOf(0));
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 246 */   public static final MetaKey<Float> TIME_SHIFT = META_REGISTRY.registerMetaObject(data -> null);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 251 */   public static final MetaKey<Damage> DAMAGE = CONTEXT_META_REGISTRY.registerMetaObject(data -> null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String id;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AssetExtraInfo.Data data;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean unknown;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 273 */   protected double viewDistance = 96.0D;
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 278 */   protected InteractionEffects effects = new InteractionEffects();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 284 */   protected float horizontalSpeedMultiplier = 1.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float runTime;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean cancelOnItemChange = true;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 300 */   protected Map<GameMode, InteractionSettings> settings = Collections.emptyMap();
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 305 */   protected InteractionRules rules = InteractionRules.DEFAULT_RULES;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected InteractionCameraSettings camera;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private transient SoftReference<Interaction> cachedPacket;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Interaction(@Nonnull String id) {
/* 334 */     this.id = id;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getId() {
/* 339 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUnknown() {
/* 346 */     return this.unknown;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public InteractionEffects getEffects() {
/* 354 */     return this.effects;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getHorizontalSpeedMultiplier() {
/* 361 */     return this.horizontalSpeedMultiplier;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getViewDistance() {
/* 368 */     return this.viewDistance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getRunTime() {
/* 375 */     return this.runTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCancelOnItemChange() {
/* 382 */     return this.cancelOnItemChange;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public InteractionRules getRules() {
/* 388 */     return this.rules;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Map<GameMode, InteractionSettings> getSettings() {
/* 396 */     return this.settings;
/*     */   }
/*     */ 
/*     */   
/*     */   public final void tick(@Nonnull Ref<EntityStore> ref, @Nonnull LivingEntity entity, boolean firstRun, float time, @Nonnull InteractionType type, @Nonnull InteractionContext context, @Nonnull CooldownHandler cooldownHandler) {
/*     */     Float shift;
/* 402 */     int previousCounter = context.getOperationCounter();
/* 403 */     int previousDepth = context.getChain().getCallDepth();
/*     */     
/* 405 */     if (!tickInternal(entity, time, type, context)) {
/* 406 */       tick0(firstRun, time, type, context, cooldownHandler);
/*     */     }
/*     */     
/* 409 */     InteractionSyncData data = context.getState();
/* 410 */     trySkipChain(ref, time, context, data);
/*     */ 
/*     */ 
/*     */     
/* 414 */     switch (data.state) { case Failed: case Finished:
/*     */       case Skip:
/* 416 */         shift = (Float)context.getInstanceStore().getMetaObject(TIME_SHIFT);
/* 417 */         if (shift != null) context.setTimeShift(shift.floatValue());
/*     */         
/* 419 */         if (context.getOperationCounter() == previousCounter && previousDepth == context.getChain().getCallDepth())
/* 420 */           context.setOperationCounter(context.getOperationCounter() + 1); 
/*     */         break;
/*     */       case ItemChanged:
/* 423 */         throw new InteractionManager.ChainCancelledException(data.state); }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public final void simulateTick(@Nonnull Ref<EntityStore> ref, @Nonnull LivingEntity entity, boolean firstRun, float time, @Nonnull InteractionType type, @Nonnull InteractionContext context, @Nonnull CooldownHandler cooldownHandler) {
/*     */     Float shift;
/* 430 */     int previousCounter = context.getOperationCounter();
/* 431 */     int previousDepth = context.getChain().getSimulatedCallDepth();
/*     */     
/* 433 */     if (!tickInternal(entity, time, type, context)) {
/* 434 */       simulateTick0(firstRun, time, type, context, cooldownHandler);
/*     */     }
/*     */     
/* 437 */     InteractionSyncData data = context.getState();
/* 438 */     trySkipChain(ref, time, context, data);
/*     */ 
/*     */ 
/*     */     
/* 442 */     switch (data.state) { case Failed: case Finished:
/*     */       case Skip:
/* 444 */         shift = (Float)context.getInstanceStore().getMetaObject(TIME_SHIFT);
/* 445 */         if (shift != null) context.setTimeShift(shift.floatValue());
/*     */         
/* 447 */         if (context.getOperationCounter() == previousCounter && previousDepth == context.getChain().getSimulatedCallDepth()) {
/* 448 */           context.setOperationCounter(context.getOperationCounter() + 1);
/*     */         }
/*     */         break; }
/*     */   
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
/*     */   private boolean tickInternal(@Nonnull LivingEntity entity, float time, @Nonnull InteractionType type, @Nonnull InteractionContext context) {
/* 464 */     Inventory inventory = entity.getInventory();
/*     */     
/* 466 */     if (UUIDUtil.isEmptyOrNull((context.getChain().getChainData()).proxyId) && this.cancelOnItemChange && ((type != InteractionType.Equipped && inventory
/* 467 */       .getActiveSlot(context.getHeldItemSectionId()) != context.getHeldItemSlot()) || (context
/* 468 */       .getHeldItemSlot() != -1 && !ItemStack.isEquivalentType(context.getHeldItemContainer().getItemStack((short)context.getHeldItemSlot()), context.getHeldItem())))) {
/* 469 */       InteractionSyncData data = context.getState();
/* 470 */       data.state = InteractionState.ItemChanged;
/* 471 */       data.progress = time;
/* 472 */       return true;
/*     */     } 
/*     */     
/* 475 */     if (!failed((context.getState()).state)) {
/* 476 */       double animationDuration = 0.0D;
/* 477 */       if (this.effects.isWaitForAnimationToFinish()) {
/* 478 */         ItemStack heldItem = context.getHeldItem();
/* 479 */         Item item = (heldItem != null) ? heldItem.getItem() : null;
/* 480 */         animationDuration = getAnimationDuration(item);
/*     */       } 
/*     */       
/* 483 */       InteractionSyncData data = context.getState();
/* 484 */       float maxTime = Math.max(this.runTime, (float)animationDuration);
/* 485 */       if (time < maxTime) {
/* 486 */         data.state = InteractionState.NotFinished;
/*     */       } else {
/* 488 */         if (maxTime > 0.0F) context.getInstanceStore().putMetaObject(TIME_SHIFT, Float.valueOf(time - maxTime)); 
/* 489 */         data.state = InteractionState.Finished;
/*     */       } 
/* 491 */       data.progress = time;
/*     */     } 
/* 493 */     return false;
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
/*     */   private void trySkipChain(@Nonnull Ref<EntityStore> ref, float time, @Nonnull InteractionContext context, @Nonnull InteractionSyncData data) {
/* 505 */     CommandBuffer<EntityStore> commandBuffer = context.getCommandBuffer();
/* 506 */     assert commandBuffer != null;
/*     */     
/* 508 */     Player playerComponent = (Player)commandBuffer.getComponent(ref, Player.getComponentType());
/*     */     
/* 510 */     GameMode gameMode = (playerComponent != null) ? playerComponent.getGameMode() : GameMode.Adventure;
/* 511 */     InteractionSettings interactionSettings = this.settings.get(gameMode);
/*     */     
/* 513 */     InteractionChain chain = context.getChain();
/* 514 */     assert chain != null;
/*     */     
/* 516 */     if (!chain.isFirstRun() && data.state == InteractionState.NotFinished)
/*     */     {
/*     */       
/* 519 */       if ((context.allowSkipChainOnClick() || (interactionSettings != null && interactionSettings.allowSkipOnClick)) && context.getClientState() != null && (context.getClientState()).state == InteractionState.Skip) {
/* 520 */         data.state = InteractionState.Skip;
/* 521 */         data.progress = time;
/*     */       } 
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
/*     */   public void compile(@Nonnull OperationsBuilder builder) {
/* 536 */     builder.addOperation(this);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handle(@Nonnull Ref<EntityStore> ref, boolean firstRun, float time, @Nonnull InteractionType type, @Nonnull InteractionContext context) {
/* 583 */     Ref<EntityStore> entity = context.getEntity();
/* 584 */     if (!entity.isValid()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 590 */     if (!ref.isValid()) {
/*     */       return;
/*     */     }
/*     */     
/* 594 */     InteractionSyncData serverData = context.getState();
/* 595 */     InteractionChain chain = context.getChain();
/* 596 */     assert chain != null;
/*     */     
/* 598 */     if (serverData.state != InteractionState.NotFinished) {
/* 599 */       if (firstRun && serverData.state == InteractionState.Finished) {
/* 600 */         sendPlayInteract(entity, context, chain, false);
/*     */       }
/* 602 */       sendPlayInteract(entity, context, chain, true);
/*     */       
/*     */       return;
/*     */     } 
/* 606 */     if (firstRun) sendPlayInteract(entity, context, chain, false);
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public InteractionChain mapForkChain(@Nonnull InteractionContext context, @Nonnull InteractionChainData data) {
/* 618 */     return null;
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
/*     */   private void sendPlayInteract(@Nonnull Ref<EntityStore> entity, @Nonnull InteractionContext context, @Nonnull InteractionChain chain, boolean cancel) {
/* 633 */     CommandBuffer<EntityStore> commandBuffer = context.getCommandBuffer();
/* 634 */     assert commandBuffer != null;
/*     */     
/* 636 */     String itemId = null;
/* 637 */     InteractionType interactionType = chain.getBaseType();
/*     */     
/* 639 */     int assetId = getInteractionIdOrUnknown(this.id);
/* 640 */     int chainId = chain.getChainId();
/* 641 */     ForkedChainId forkedChainId = chain.getForkedChainId();
/* 642 */     int operationIndex = chain.getOperationIndex();
/*     */     
/* 644 */     NetworkId networkIdComponent = (NetworkId)commandBuffer.getComponent(entity, NetworkId.getComponentType());
/* 645 */     assert networkIdComponent != null;
/*     */     
/* 647 */     int entityNetworkId = networkIdComponent.getId();
/* 648 */     PlayInteractionFor packet = new PlayInteractionFor(entityNetworkId, chainId, forkedChainId, operationIndex, assetId, itemId, interactionType, cancel);
/*     */     
/* 650 */     TransformComponent transformComponent = (TransformComponent)commandBuffer.getComponent(entity, TransformComponent.getComponentType());
/* 651 */     assert transformComponent != null;
/* 652 */     Vector3d position = transformComponent.getPosition();
/*     */     
/* 654 */     SpatialResource<Ref<EntityStore>, EntityStore> playerSpatialResource = (SpatialResource<Ref<EntityStore>, EntityStore>)commandBuffer.getResource(EntityModule.get().getPlayerSpatialResourceType());
/* 655 */     ObjectList<Ref<EntityStore>> results = SpatialResource.getThreadLocalReferenceList();
/* 656 */     playerSpatialResource.getSpatialStructure().collect(position, this.viewDistance, (List)results);
/*     */     
/* 658 */     Ref<EntityStore> owningEntityRef = context.getOwningEntity();
/* 659 */     ComponentType<EntityStore, PlayerRef> playerRefComponentType = PlayerRef.getComponentType();
/* 660 */     for (ObjectListIterator<Ref<EntityStore>> objectListIterator = results.iterator(); objectListIterator.hasNext(); ) { Ref<EntityStore> playerRef = objectListIterator.next();
/*     */ 
/*     */       
/* 663 */       if (chain.requiresClient() && playerRef.equals(owningEntityRef))
/*     */         continue; 
/* 665 */       PlayerRef playerPlayerRefComponent = (PlayerRef)commandBuffer.getComponent(playerRef, playerRefComponentType);
/* 666 */       assert playerPlayerRefComponent != null;
/* 667 */       playerPlayerRefComponent.getPacketHandler().writeNoCache((Packet)packet); }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public final Interaction toPacket() {
/* 674 */     Interaction cached = (this.cachedPacket == null) ? null : this.cachedPacket.get();
/* 675 */     if (cached != null) return cached;
/*     */     
/* 677 */     Interaction packet = generatePacket();
/* 678 */     configurePacket(packet);
/* 679 */     this.cachedPacket = new SoftReference<>(packet);
/*     */     
/* 681 */     return packet;
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
/*     */   protected void configurePacket(Interaction packet) {
/* 693 */     packet.waitForDataFrom = getWaitForDataFrom();
/* 694 */     packet.effects = this.effects.toPacket();
/* 695 */     packet.horizontalSpeedMultiplier = this.horizontalSpeedMultiplier;
/* 696 */     packet.runTime = this.runTime;
/* 697 */     packet.cancelOnItemChange = this.cancelOnItemChange;
/* 698 */     packet.settings = this.settings;
/*     */     
/* 700 */     packet.rules = this.rules.toPacket();
/* 701 */     if (this.data != null) {
/* 702 */       packet.tags = this.data.getTags().keySet().toIntArray();
/*     */     }
/*     */     
/* 705 */     if (this.camera != null) packet.camera = this.camera.toPacket();
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected double getAnimationDuration(@Nullable Item item) {
/*     */     String playerAnimationsId;
/* 717 */     if (this.effects.itemPlayerAnimationsId != null) {
/* 718 */       playerAnimationsId = this.effects.itemPlayerAnimationsId;
/* 719 */     } else if (item != null) {
/* 720 */       playerAnimationsId = item.getPlayerAnimationsId();
/*     */     } else {
/* 722 */       playerAnimationsId = "Default";
/*     */     } 
/*     */     
/* 725 */     ItemPlayerAnimations playerAnimations = (ItemPlayerAnimations)ItemPlayerAnimations.getAssetMap().getAsset(playerAnimationsId);
/* 726 */     if (playerAnimations == null) return 0.0D;
/*     */     
/* 728 */     ItemAnimation itemAnimation = (this.effects.getItemAnimationId() != null) ? (ItemAnimation)playerAnimations.getAnimations().get(this.effects.getItemAnimationId()) : null;
/* 729 */     if (itemAnimation == null) return 0.0D;
/*     */     
/* 731 */     BlockyAnimationCache.BlockyAnimation animation = BlockyAnimationCache.getNow(itemAnimation.firstPerson);
/* 732 */     if (animation == null) return 0.0D;
/*     */     
/* 734 */     return animation.getDurationSeconds() * itemAnimation.speed;
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
/*     */   
/*     */   public String toString() {
/* 763 */     return "Interaction{id='" + this.id + "', viewDistance=" + this.viewDistance + ", effects=" + String.valueOf(this.effects) + ", horizontalSpeedMultiplier=" + this.horizontalSpeedMultiplier + ", runTime=" + this.runTime + ", cancelOnItemChange=" + this.cancelOnItemChange + ", settings=" + String.valueOf(this.settings) + ", rules=" + String.valueOf(this.rules) + ", camera=" + String.valueOf(this.camera) + "}";
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
/*     */   public static boolean failed(@Nonnull InteractionState state) {
/* 785 */     switch (state) { default: throw new MatchException(null, null);case Failed: case Skip: case ItemChanged: case Finished: case NotFinished: break; }  return false;
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
/*     */   @Nullable
/*     */   @Deprecated
/*     */   public static Interaction getInteractionOrUnknown(@Nonnull String id) {
/* 803 */     return (Interaction)getAssetMap().getAsset(getInteractionIdOrUnknown(id));
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
/*     */   @Deprecated
/*     */   public static int getInteractionIdOrUnknown(@Nullable String id) {
/* 817 */     if (id == null) return Integer.MIN_VALUE;
/*     */     
/* 819 */     IndexedLookupTableAssetMap<String, Interaction> assetMap = getAssetMap();
/* 820 */     int interactionId = assetMap.getIndex(id);
/* 821 */     if (interactionId == Integer.MIN_VALUE) {
/*     */       
/* 823 */       HytaleLogger.getLogger().at(Level.WARNING).log("Missing interaction %s", id);
/* 824 */       getAssetStore().loadAssets("Hytale:Hytale", List.of(new SendMessageInteraction(id, "Missing interaction: " + id)));
/*     */       
/* 826 */       int index = assetMap.getIndex(id);
/* 827 */       if (index == Integer.MIN_VALUE) {
/* 828 */         throw new IllegalArgumentException("Unknown key! " + id);
/*     */       }
/* 830 */       interactionId = index;
/*     */     } 
/*     */     
/* 833 */     return interactionId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static boolean needsRemoteSync(@Nullable String id) {
/* 844 */     if (id == null) return false;
/*     */     
/* 846 */     Interaction interaction = getInteractionOrUnknown(id);
/* 847 */     if (interaction == null) {
/* 848 */       throw new IllegalArgumentException("Unknown interaction: " + id);
/*     */     }
/*     */     
/* 851 */     return interaction.needsRemoteSync();
/*     */   }
/*     */   
/*     */   public Interaction() {}
/*     */   
/*     */   protected abstract void tick0(boolean paramBoolean, float paramFloat, @Nonnull InteractionType paramInteractionType, @Nonnull InteractionContext paramInteractionContext, @Nonnull CooldownHandler paramCooldownHandler);
/*     */   
/*     */   protected abstract void simulateTick0(boolean paramBoolean, float paramFloat, @Nonnull InteractionType paramInteractionType, @Nonnull InteractionContext paramInteractionContext, @Nonnull CooldownHandler paramCooldownHandler);
/*     */   
/*     */   public abstract boolean walk(@Nonnull Collector paramCollector, @Nonnull InteractionContext paramInteractionContext);
/*     */   
/*     */   @Nonnull
/*     */   protected abstract Interaction generatePacket();
/*     */   
/*     */   public abstract boolean needsRemoteSync();
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\Interaction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */