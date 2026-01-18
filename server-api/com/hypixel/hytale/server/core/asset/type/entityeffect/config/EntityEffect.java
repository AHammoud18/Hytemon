/*     */ package com.hypixel.hytale.server.core.asset.type.entityeffect.config;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*     */ import com.hypixel.hytale.assetstore.AssetKeyValidator;
/*     */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*     */ import com.hypixel.hytale.assetstore.AssetStore;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetCodec;
/*     */ import com.hypixel.hytale.assetstore.codec.ContainedAssetCodec;
/*     */ import com.hypixel.hytale.assetstore.map.IndexedLookupTableAssetMap;
/*     */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.codecs.EnumCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.codec.codecs.map.MapCodec;
/*     */ import com.hypixel.hytale.codec.codecs.map.Object2FloatMapCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.ValidatorCache;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.protocol.EntityEffect;
/*     */ import com.hypixel.hytale.protocol.OverlapBehavior;
/*     */ import com.hypixel.hytale.protocol.ValueType;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.ItemArmor;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.ModelAsset;
/*     */ import com.hypixel.hytale.server.core.asset.type.soundevent.config.SoundEvent;
/*     */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*     */ import com.hypixel.hytale.server.core.modules.entity.damage.DamageCause;
/*     */ import com.hypixel.hytale.server.core.modules.entitystats.EntityStatsModule;
/*     */ import com.hypixel.hytale.server.core.modules.entitystats.asset.EntityStatType;
/*     */ import com.hypixel.hytale.server.core.modules.entitystats.modifier.StaticModifier;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.server.combat.DamageCalculator;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.server.combat.DamageEffects;
/*     */ import it.unimi.dsi.fastutil.ints.Int2FloatMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2FloatMap;
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityEffect
/*     */   implements JsonAssetWithMap<String, IndexedLookupTableAssetMap<String, EntityEffect>>, NetworkSerializable<EntityEffect>
/*     */ {
/*     */   @Nonnull
/*     */   public static final AssetBuilderCodec<String, EntityEffect> CODEC;
/*     */   
/*     */   static {
/* 244 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(EntityEffect.class, EntityEffect::new, (Codec)Codec.STRING, (entityEffect, s) -> entityEffect.id = s, entityEffect -> entityEffect.id, (asset, data) -> asset.data = data, asset -> asset.data).appendInherited(new KeyedCodec("Name", (Codec)Codec.STRING), (entityEffect, s) -> entityEffect.name = s, entityEffect -> entityEffect.name, (entityEffect, parent) -> entityEffect.name = parent.name).documentation("The name of this entity effect that will be displayed in the UI. This must be a localization key.").add()).appendInherited(new KeyedCodec("ApplicationEffects", (Codec)ApplicationEffects.CODEC), (entityEffect, s) -> entityEffect.applicationEffects = s, entityEffect -> entityEffect.applicationEffects, (entityEffect, parent) -> entityEffect.applicationEffects = parent.applicationEffects).add()).appendInherited(new KeyedCodec("WorldRemovalSoundEventId", (Codec)Codec.STRING), (entityEffect, s) -> entityEffect.worldRemovalSoundEventId = s, entityEffect -> entityEffect.worldRemovalSoundEventId, (entityEffect, parent) -> entityEffect.worldRemovalSoundEventId = parent.worldRemovalSoundEventId).addValidator(SoundEvent.VALIDATOR_CACHE.getValidator()).add()).appendInherited(new KeyedCodec("LocalRemovalSoundEventId", (Codec)Codec.STRING), (entityEffect, s) -> entityEffect.localRemovalSoundEventId = s, entityEffect -> entityEffect.localRemovalSoundEventId, (entityEffect, parent) -> entityEffect.localRemovalSoundEventId = parent.localRemovalSoundEventId).addValidator(SoundEvent.VALIDATOR_CACHE.getValidator()).add()).appendInherited(new KeyedCodec("DamageCalculator", (Codec)DamageCalculator.CODEC), (entityEffect, s) -> entityEffect.damageCalculator = s, entityEffect -> entityEffect.damageCalculator, (entityEffect, parent) -> entityEffect.damageCalculator = parent.damageCalculator).add()).appendInherited(new KeyedCodec("DamageCalculatorCooldown", (Codec)Codec.FLOAT), (entityEffect, s) -> entityEffect.damageCalculatorCooldown = s.floatValue(), entityEffect -> Float.valueOf(entityEffect.damageCalculatorCooldown), (entityEffect, parent) -> entityEffect.damageCalculatorCooldown = parent.damageCalculatorCooldown).addValidator(Validators.greaterThanOrEqual(Float.valueOf(0.0F))).add()).appendInherited(new KeyedCodec("DamageEffects", (Codec)DamageEffects.CODEC), (entityEffect, s) -> entityEffect.damageEffects = s, entityEffect -> entityEffect.damageEffects, (entityEffect, parent) -> entityEffect.damageEffects = parent.damageEffects).add()).appendInherited(new KeyedCodec("StatModifierEffects", (Codec)DamageEffects.CODEC), (entityEffect, s) -> entityEffect.statModifierEffects = s, entityEffect -> entityEffect.statModifierEffects, (entityEffect, parent) -> entityEffect.statModifierEffects = parent.statModifierEffects).documentation("Effects to play when stat modifiers are applied and updated.").add()).appendInherited(new KeyedCodec("ModelOverride", (Codec)ModelOverride.CODEC), (entityEffect, o) -> entityEffect.modelOverride = o, entityEffect -> entityEffect.modelOverride, (entityEffect, parent) -> entityEffect.modelOverride = parent.modelOverride).add()).appendInherited(new KeyedCodec("ModelChange", (Codec)Codec.STRING), (entityEffect, s) -> entityEffect.modelChange = s, entityEffect -> entityEffect.modelChange, (entityEffect, parent) -> entityEffect.modelChange = parent.modelChange).addValidator(ModelAsset.VALIDATOR_CACHE.getValidator()).documentation("A model to change the affected entity's appearance to.").add()).append(new KeyedCodec("RawStatModifiers", (Codec)new MapCodec((Codec)new ArrayCodec((Codec)StaticModifier.CODEC, x$0 -> new StaticModifier[x$0]), java.util.HashMap::new)), (entityEffect, map) -> entityEffect.rawStatModifiers = map, entityEffect -> entityEffect.rawStatModifiers).addValidator((Validator)EntityStatType.VALIDATOR_CACHE.getMapKeyValidator().late()).add()).appendInherited(new KeyedCodec("StatModifiers", (Codec)new Object2FloatMapCodec((Codec)Codec.STRING, it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap::new)), (entityEffect, o) -> entityEffect.unknownEntityStats = o, entityEffect -> entityEffect.unknownEntityStats, (entityEffect, parent) -> entityEffect.entityStats = parent.entityStats).addValidator((Validator)EntityStatType.VALIDATOR_CACHE.getMapKeyValidator()).documentation("Modifiers to apply to EntityStats.").add()).appendInherited(new KeyedCodec("ValueType", (Codec)new EnumCodec(ValueType.class)), (entityEffect, valueType) -> entityEffect.valueType = valueType, entityEffect -> entityEffect.valueType, (entityEffect, parent) -> entityEffect.valueType = parent.valueType).documentation("Enum to specify if the StatModifiers must be considered as absolute values or percent. Default value is Absolute. When using ValueType.Absolute, '100' matches the max value.").addValidator(Validators.nonNull()).add()).appendInherited(new KeyedCodec("Duration", (Codec)Codec.FLOAT), (entityEffect, o) -> entityEffect.duration = o.floatValue(), entityEffect -> Float.valueOf(entityEffect.duration), (entityEffect, parent) -> entityEffect.duration = parent.duration).documentation("Value used by default unless specified otherwise in the method's call.").add()).appendInherited(new KeyedCodec("OverlapBehavior", (Codec)OverlapBehavior.CODEC), (entityEffect, s) -> entityEffect.overlapBehavior = s, entityEffect -> entityEffect.overlapBehavior, (entityEffect, parent) -> entityEffect.overlapBehavior = parent.overlapBehavior).documentation("Value used by default unless specified otherwise in the method's call.").add()).appendInherited(new KeyedCodec("Infinite", (Codec)Codec.BOOLEAN), (entityEffect, aBoolean) -> entityEffect.infinite = aBoolean.booleanValue(), entityEffect -> Boolean.valueOf(entityEffect.infinite), (entityEffect, parent) -> entityEffect.infinite = parent.infinite).documentation("Value used by default unless specified otherwise in the method's call.").add()).appendInherited(new KeyedCodec("Debuff", (Codec)Codec.BOOLEAN), (entityEffect, aBoolean) -> entityEffect.debuff = aBoolean.booleanValue(), entityEffect -> Boolean.valueOf(entityEffect.debuff), (entityEffect, parent) -> entityEffect.debuff = parent.debuff).documentation("Value used by default unless specified otherwise in the method's call.").add()).appendInherited(new KeyedCodec("Locale", (Codec)Codec.STRING), (entityEffect, aString) -> entityEffect.locale = aString, entityEffect -> entityEffect.locale, (entityEffect, parent) -> entityEffect.locale = parent.locale).documentation("An optional translation key, used to display the damage cause upon death.").add()).appendInherited(new KeyedCodec("StatusEffectIcon", (Codec)Codec.STRING), (entityEffect, aString) -> entityEffect.statusEffectIcon = aString, entityEffect -> entityEffect.statusEffectIcon, (entityEffect, parent) -> entityEffect.statusEffectIcon = parent.statusEffectIcon).documentation("Value used by default unless specified otherwise in the method's call.").add()).appendInherited(new KeyedCodec("RemovalBehavior", (Codec)RemovalBehavior.CODEC), (entityEffect, removalBehavior) -> entityEffect.removalBehavior = removalBehavior, entityEffect -> entityEffect.removalBehavior, (entityEffect, parent) -> entityEffect.removalBehavior = parent.removalBehavior).documentation("Value used by default unless specified otherwise in the method's call.").add()).appendInherited(new KeyedCodec("Invulnerable", (Codec)Codec.BOOLEAN), (entityEffect, aBoolean) -> entityEffect.invulnerable = aBoolean.booleanValue(), entityEffect -> Boolean.valueOf(entityEffect.invulnerable), (entityEffect, parent) -> entityEffect.invulnerable = parent.invulnerable).documentation("Determines whether this effect applies the invulnerable component to the entity whilst active.").add()).appendInherited(new KeyedCodec("DamageResistance", (Codec)new MapCodec((Codec)new ArrayCodec((Codec)StaticModifier.CODEC, x$0 -> new StaticModifier[x$0]), java.util.HashMap::new)), (entityEffect, map) -> entityEffect.damageResistanceValuesRaw = map, entityEffect -> entityEffect.damageResistanceValuesRaw, (entityEffect, parent) -> entityEffect.damageResistanceValuesRaw = parent.damageResistanceValuesRaw).addValidator((Validator)DamageCause.VALIDATOR_CACHE.getMapKeyValidator()).add()).afterDecode(entityEffect -> { entityEffect.entityStats = EntityStatsModule.resolveEntityStats(entityEffect.unknownEntityStats); entityEffect.statModifiers = EntityStatsModule.resolveEntityStats(entityEffect.rawStatModifiers); if (entityEffect.damageResistanceValuesRaw != null && !entityEffect.damageResistanceValuesRaw.isEmpty()) entityEffect.damageResistanceValues = ItemArmor.convertStringKeyToDamageCause(entityEffect.damageResistanceValuesRaw);  if (entityEffect.worldRemovalSoundEventId != null) entityEffect.worldRemovalSoundEventIndex = SoundEvent.getAssetMap().getIndex(entityEffect.worldRemovalSoundEventId);  if (entityEffect.localRemovalSoundEventId != null) entityEffect.localRemovalSoundEventIndex = SoundEvent.getAssetMap().getIndex(entityEffect.localRemovalSoundEventId);  })).build();
/*     */   }
/* 246 */   public static final Codec<String> CHILD_ASSET_CODEC = (Codec<String>)new ContainedAssetCodec(EntityEffect.class, (AssetCodec)CODEC);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private static AssetStore<String, EntityEffect, IndexedLookupTableAssetMap<String, EntityEffect>> STORE;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static AssetStore<String, EntityEffect, IndexedLookupTableAssetMap<String, EntityEffect>> getAssetStore() {
/* 259 */     if (STORE == null) STORE = AssetRegistry.getAssetStore(EntityEffect.class); 
/* 260 */     return STORE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static IndexedLookupTableAssetMap<String, EntityEffect> getAssetMap() {
/* 268 */     return (IndexedLookupTableAssetMap<String, EntityEffect>)getAssetStore().getAssetMap();
/*     */   }
/*     */   
/* 271 */   public static final ValidatorCache<String> VALIDATOR_CACHE = new ValidatorCache((Validator)new AssetKeyValidator(EntityEffect::getAssetStore));
/*     */ 
/*     */ 
/*     */   
/*     */   protected AssetExtraInfo.Data data;
/*     */ 
/*     */ 
/*     */   
/*     */   protected String id;
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected String name;
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected ApplicationEffects applicationEffects;
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected String worldRemovalSoundEventId;
/*     */ 
/*     */   
/* 297 */   protected transient int worldRemovalSoundEventIndex = 0;
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected String localRemovalSoundEventId;
/*     */ 
/*     */   
/* 304 */   protected transient int localRemovalSoundEventIndex = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected DamageCalculator damageCalculator;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float damageCalculatorCooldown;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected DamageEffects damageEffects;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected DamageEffects statModifierEffects;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected ModelOverride modelOverride;
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected String modelChange;
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected Object2FloatMap<String> unknownEntityStats;
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected Int2FloatMap entityStats;
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 353 */   protected ValueType valueType = ValueType.Absolute;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 359 */   protected float duration = 0.0F;
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 364 */   protected OverlapBehavior overlapBehavior = OverlapBehavior.IGNORE;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 370 */   protected RemovalBehavior removalBehavior = RemovalBehavior.COMPLETE;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean infinite;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean debuff;
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
/*     */   @Nullable
/*     */   protected String locale;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean invulnerable = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected Map<String, StaticModifier[]> rawStatModifiers;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected Int2ObjectMap<StaticModifier[]> statModifiers;
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected Map<String, StaticModifier[]> damageResistanceValuesRaw;
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected Map<DamageCause, StaticModifier[]> damageResistanceValues;
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private SoftReference<EntityEffect> cachedPacket;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityEffect(@Nonnull String id) {
/* 430 */     this.id = id;
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
/*     */   @Nullable
/*     */   public String getName() {
/* 446 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Int2ObjectMap<StaticModifier[]> getStatModifiers() {
/* 454 */     return this.statModifiers;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getId() {
/* 462 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public ApplicationEffects getApplicationEffects() {
/* 470 */     return this.applicationEffects;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public DamageCalculator getDamageCalculator() {
/* 478 */     return this.damageCalculator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getDamageCalculatorCooldown() {
/* 485 */     return this.damageCalculatorCooldown;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public DamageEffects getDamageEffects() {
/* 493 */     return this.damageEffects;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public DamageEffects getStatModifierEffects() {
/* 498 */     return this.statModifierEffects;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public ModelOverride getModelOverride() {
/* 506 */     return this.modelOverride;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String getModelChange() {
/* 514 */     return this.modelChange;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Int2FloatMap getEntityStats() {
/* 522 */     return this.entityStats;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getDuration() {
/* 529 */     return this.duration;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public OverlapBehavior getOverlapBehavior() {
/* 537 */     return this.overlapBehavior;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInfinite() {
/* 544 */     return this.infinite;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDebuff() {
/* 551 */     return this.debuff;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String getStatusEffectIcon() {
/* 559 */     return this.statusEffectIcon;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String getLocale() {
/* 567 */     return this.locale;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public RemovalBehavior getRemovalBehavior() {
/* 575 */     return this.removalBehavior;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ValueType getValueType() {
/* 583 */     return this.valueType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInvulnerable() {
/* 590 */     return this.invulnerable;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Map<DamageCause, StaticModifier[]> getDamageResistanceValues() {
/* 595 */     return this.damageResistanceValues;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public EntityEffect toPacket() {
/* 601 */     EntityEffect cached = (this.cachedPacket == null) ? null : this.cachedPacket.get();
/* 602 */     if (cached != null) return cached;
/*     */     
/* 604 */     EntityEffect packet = new EntityEffect();
/*     */     
/* 606 */     packet.id = this.id;
/* 607 */     packet.name = this.name;
/* 608 */     if (this.applicationEffects != null) packet.applicationEffects = this.applicationEffects.toPacket(); 
/* 609 */     packet.worldRemovalSoundEventIndex = this.worldRemovalSoundEventIndex;
/* 610 */     packet.localRemovalSoundEventIndex = (this.localRemovalSoundEventIndex != 0) ? this.localRemovalSoundEventIndex : this.worldRemovalSoundEventIndex;
/* 611 */     if (this.modelOverride != null) packet.modelOverride = this.modelOverride.toPacket(); 
/* 612 */     packet.duration = this.duration;
/* 613 */     packet.infinite = this.infinite;
/* 614 */     packet.debuff = this.debuff;
/* 615 */     packet.statusEffectIcon = this.statusEffectIcon;
/* 616 */     switch (this.overlapBehavior) { default: throw new MatchException(null, null);case EXTEND: case OVERWRITE: case IGNORE: break; }  packet.overlapBehavior = 
/*     */ 
/*     */       
/* 619 */       OverlapBehavior.Ignore;
/*     */     
/* 621 */     packet.damageCalculatorCooldown = this.damageCalculatorCooldown;
/* 622 */     packet.statModifiers = (Map)this.entityStats;
/* 623 */     packet.valueType = this.valueType;
/*     */     
/* 625 */     this.cachedPacket = new SoftReference<>(packet);
/* 626 */     return packet;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 632 */     return "EntityEffect{id='" + this.id + "', name=" + this.name + ", applicationEffects=" + String.valueOf(this.applicationEffects) + ", damageCalculator=" + String.valueOf(this.damageCalculator) + ", damageCalculatorCooldown=" + this.damageCalculatorCooldown + ", damageEffects=" + String.valueOf(this.damageEffects) + ", modelOverride=" + String.valueOf(this.modelOverride) + ", modelChange='" + this.modelChange + "', unknownEntityStats=" + String.valueOf(this.unknownEntityStats) + ", entityStats=" + String.valueOf(this.entityStats) + ", valueType=" + String.valueOf(this.valueType) + ", duration=" + this.duration + ", overlapBehavior=" + String.valueOf(this.overlapBehavior) + ", infinite=" + this.infinite + ", debuff=" + this.debuff + ", statusEffectIcon=" + this.statusEffectIcon + ", locale=" + this.locale + ", removalBehavior=" + String.valueOf(this.removalBehavior) + ", invulnerable=" + this.invulnerable + ", damageResistanceValues=" + String.valueOf(this.damageResistanceValues) + "}";
/*     */   }
/*     */   
/*     */   protected EntityEffect() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\entityeffect\config\EntityEffect.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */