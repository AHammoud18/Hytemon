/*     */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config;
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
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.codec.codecs.map.EnumMapCodec;
/*     */ import com.hypixel.hytale.codec.codecs.map.MapCodec;
/*     */ import com.hypixel.hytale.codec.validation.LateValidator;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.ValidatorCache;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.codec.validation.validator.FloatArrayValidator;
/*     */ import com.hypixel.hytale.common.util.ArrayUtil;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.protocol.GameMode;
/*     */ import com.hypixel.hytale.protocol.InteractionCooldown;
/*     */ import com.hypixel.hytale.protocol.RootInteraction;
/*     */ import com.hypixel.hytale.protocol.RootInteractionSettings;
/*     */ import com.hypixel.hytale.protocol.WaitForDataFrom;
/*     */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.operation.Operation;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.operation.OperationsBuilder;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.logging.Level;
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
/*     */ public class RootInteraction
/*     */   implements JsonAssetWithMap<String, IndexedLookupTableAssetMap<String, RootInteraction>>, NetworkSerializable<RootInteraction>
/*     */ {
/*     */   @Nonnull
/*     */   public static final BuilderCodec<InteractionCooldown> COOLDOWN_CODEC;
/*     */   @Nonnull
/*     */   public static final AssetBuilderCodec<String, RootInteraction> CODEC;
/*     */   
/*     */   static {
/* 100 */     COOLDOWN_CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(InteractionCooldown.class, InteractionCooldown::new).appendInherited(new KeyedCodec("Id", (Codec)Codec.STRING), (interactionCooldown, s) -> interactionCooldown.cooldownId = s, interactionCooldown -> interactionCooldown.cooldownId, (interactionCooldown, parent) -> interactionCooldown.cooldownId = parent.cooldownId).documentation("The Id for the cooldown.\n\nCooldowns can be used on different interactions but share a cooldown.").add()).appendInherited(new KeyedCodec("Cooldown", (Codec)Codec.FLOAT), (interactionCooldown, s) -> interactionCooldown.cooldown = s.floatValue(), interactionCooldown -> Float.valueOf(interactionCooldown.cooldown), (interactionCooldown, parent) -> interactionCooldown.cooldown = parent.cooldown).addValidator(Validators.greaterThanOrEqual(Float.valueOf(0.0F))).documentation("The time in seconds this cooldown should last for.").add()).appendInherited(new KeyedCodec("Charges", (Codec)Codec.FLOAT_ARRAY), (interactionCharges, s) -> interactionCharges.chargeTimes = s, interactionCharges -> interactionCharges.chargeTimes, (interactionCharges, parent) -> interactionCharges.chargeTimes = parent.chargeTimes).addValidator((Validator)new FloatArrayValidator(Validators.greaterThanOrEqual(Float.valueOf(0.0F)))).documentation("The charge times available for this interaction.").add()).appendInherited(new KeyedCodec("SkipCooldownReset", (Codec)Codec.BOOLEAN), (interactionCharges, s) -> interactionCharges.skipCooldownReset = s.booleanValue(), interactionCharges -> Boolean.valueOf(interactionCharges.skipCooldownReset), (interactionCharges, parent) -> interactionCharges.skipCooldownReset = parent.skipCooldownReset).documentation("Determines whether resetting cooldown should be skipped.").add()).appendInherited(new KeyedCodec("InterruptRecharge", (Codec)Codec.BOOLEAN), (interactionCharges, s) -> interactionCharges.interruptRecharge = s.booleanValue(), interactionCharges -> Boolean.valueOf(interactionCharges.interruptRecharge), (interactionCharges, parent) -> interactionCharges.interruptRecharge = parent.interruptRecharge).documentation("Determines whether recharge is interrupted by use.").add()).appendInherited(new KeyedCodec("ClickBypass", (Codec)Codec.BOOLEAN), (interactionCooldown, s) -> interactionCooldown.clickBypass = s.booleanValue(), interactionCooldown -> Boolean.valueOf(interactionCooldown.clickBypass), (interactionCooldown, parent) -> interactionCooldown.clickBypass = parent.clickBypass).documentation("Whether this cooldown can be bypassed by clicking.").add()).build();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 205 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(RootInteraction.class, RootInteraction::new, (Codec)Codec.STRING, (o, i) -> o.id = i, o -> o.id, (o, i) -> o.data = i, o -> o.data).documentation("A **RootInteraction** serves as an entry point into a set of **Interaction**s.\n\nIn order to start an interaction chain a **RootInteraction** is required.\nA basic **RootInteraction** can simply contain a reference to single interaction within _Interactions_ field which will be the entire chain. More complex cases can configure the other fields.\n\nMost fields configured here apply to all **Interaction**s contained the root and any **Interaction**s they contain as well. Systems that look at tags for interactions may also check the root interaction as well reducing the need to duplicate them on all nested interactions.")).appendInherited(new KeyedCodec("Interactions", Interaction.CHILD_ASSET_CODEC_ARRAY), (o, i) -> o.interactionIds = i, o -> o.interactionIds, (o, p) -> o.interactionIds = p.interactionIds).addValidator(Validators.nonNull()).addValidator(Validators.nonEmptyArray()).addValidatorLate(() -> Interaction.VALIDATOR_CACHE.getArrayValidator().late()).documentation("The list of interactions that will be run when starting a chain with this root interaction. Interactions in this list will be run in sequence.").add()).appendInherited(new KeyedCodec("Cooldown", (Codec)COOLDOWN_CODEC), (o, i) -> o.cooldown = i, o -> o.cooldown, (o, p) -> o.cooldown = p.cooldown).documentation("Cooldowns are used to prevent an interaction from running repeatedly too quickly.\n\nDuring a cooldown attempting to run an interaction with the same cooldown id will fail.").add()).appendInherited(new KeyedCodec("Rules", (Codec)InteractionRules.CODEC), (o, i) -> o.rules = i, o -> o.rules, (o, p) -> o.rules = p.rules).documentation("A set of rules that control when this root interaction can run or what interactions this root being active prevents.").addValidator(Validators.nonNull()).add()).appendInherited(new KeyedCodec("Settings", (Codec)new EnumMapCodec(GameMode.class, (Codec)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(RootInteractionSettings.class, RootInteractionSettings::new).appendInherited(new KeyedCodec("Cooldown", (Codec)COOLDOWN_CODEC), (o, i) -> o.cooldown = i, o -> o.cooldown, (o, p) -> o.cooldown = p.cooldown).documentation("Cooldowns are used to prevent an interaction from running repeatedly too quickly.\n\nDuring a cooldown attempting to run an interaction with the same cooldown id will fail.").add()).appendInherited(new KeyedCodec("AllowSkipChainOnClick", (Codec)Codec.BOOLEAN), (o, i) -> o.allowSkipChainOnClick = i.booleanValue(), o -> Boolean.valueOf(o.allowSkipChainOnClick), (o, p) -> o.allowSkipChainOnClick = p.allowSkipChainOnClick).documentation("Whether to skip the whole interaction chain when another click is sent.").add()).build())), (o, i) -> o.settings = i, o -> o.settings, (o, p) -> o.settings = p.settings).documentation("Per a gamemode settings.").add()).appendInherited(new KeyedCodec("ClickQueuingTimeout", (Codec)Codec.FLOAT), (interaction, s) -> interaction.clickQueuingTimeout = s.floatValue(), interaction -> Float.valueOf(interaction.clickQueuingTimeout), (interaction, parent) -> interaction.clickQueuingTimeout = parent.clickQueuingTimeout).documentation("Controls the amount of time this root interaction can remain in the click queue before being discarded.").add()).appendInherited(new KeyedCodec("RequireNewClick", (Codec)Codec.BOOLEAN), (o, i) -> o.requireNewClick = i.booleanValue(), o -> Boolean.valueOf(o.requireNewClick), (o, p) -> o.requireNewClick = p.requireNewClick).documentation("Requires the user to click again before running another root interaction of the same type.").add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 211 */   public static final ValidatorCache<String> VALIDATOR_CACHE = new ValidatorCache((Validator)new AssetKeyValidator(RootInteraction::getAssetStore));
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 217 */   public static final ContainedAssetCodec<String, RootInteraction, ?> CHILD_ASSET_CODEC = new ContainedAssetCodec(RootInteraction.class, (AssetCodec)CODEC);
/*     */   
/*     */   @Nonnull
/*     */   public static final Codec<String[]> CHILD_ASSET_CODEC_ARRAY;
/*     */   
/*     */   static {
/* 223 */     CHILD_ASSET_CODEC_ARRAY = (Codec<String[]>)new ArrayCodec((Codec)CHILD_ASSET_CODEC, x$0 -> new String[x$0]);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 229 */   public static final MapCodec<String, HashMap<String, String>> CHILD_ASSET_CODEC_MAP = new MapCodec((Codec)CHILD_ASSET_CODEC, HashMap::new);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static AssetStore<String, RootInteraction, IndexedLookupTableAssetMap<String, RootInteraction>> ASSET_STORE;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String id;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AssetExtraInfo.Data data;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RootInteraction(@Nonnull String id, @Nonnull String... interactionIds) {
/* 251 */     this.id = id;
/* 252 */     this.interactionIds = interactionIds;
/* 253 */     this.data = new AssetExtraInfo.Data(RootInteraction.class, id, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RootInteraction(@Nonnull String id, @Nullable InteractionCooldown cooldown, @Nonnull String... interactionIds) {
/* 264 */     this.id = id;
/* 265 */     this.cooldown = cooldown;
/* 266 */     this.interactionIds = interactionIds;
/* 267 */     this.data = new AssetExtraInfo.Data(RootInteraction.class, id, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static AssetStore<String, RootInteraction, IndexedLookupTableAssetMap<String, RootInteraction>> getAssetStore() {
/* 275 */     if (ASSET_STORE == null) ASSET_STORE = AssetRegistry.getAssetStore(RootInteraction.class); 
/* 276 */     return ASSET_STORE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static IndexedLookupTableAssetMap<String, RootInteraction> getAssetMap() {
/* 284 */     return (IndexedLookupTableAssetMap<String, RootInteraction>)getAssetStore().getAssetMap();
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
/*     */   @Nonnull
/* 300 */   protected String[] interactionIds = ArrayUtil.EMPTY_STRING_ARRAY;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected InteractionCooldown cooldown;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 313 */   protected Map<GameMode, RootInteractionSettings> settings = Collections.emptyMap();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean requireNewClick;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float clickQueuingTimeout;
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 328 */   protected InteractionRules rules = InteractionRules.DEFAULT_RULES;
/*     */ 
/*     */ 
/*     */   
/*     */   protected Operation[] operations;
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean needsRemoteSync;
/*     */ 
/*     */ 
/*     */   
/*     */   public String getId() {
/* 341 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean needsRemoteSync() {
/* 348 */     return this.needsRemoteSync;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean resetCooldownOnStart() {
/* 355 */     return (this.cooldown == null || !this.cooldown.skipCooldownReset);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Operation getOperation(int index) {
/* 366 */     if (index >= this.operations.length) return null; 
/* 367 */     return this.operations[index];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOperationMax() {
/* 374 */     return this.operations.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getInteractionIds() {
/* 381 */     return this.interactionIds;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Map<GameMode, RootInteractionSettings> getSettings() {
/* 389 */     return this.settings;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getClickQueuingTimeout() {
/* 396 */     return this.clickQueuingTimeout;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public InteractionRules getRules() {
/* 404 */     return this.rules;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public InteractionCooldown getCooldown() {
/* 412 */     return this.cooldown;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AssetExtraInfo.Data getData() {
/* 419 */     return this.data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void build(@Nonnull Set<String> modifiedInteractions) {
/* 428 */     if (this.operations == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 448 */     build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void build() {
/* 455 */     if (this.interactionIds == null)
/* 456 */       return;  OperationsBuilder builder = new OperationsBuilder();
/*     */     
/* 458 */     boolean needsSyncRemote = false;
/* 459 */     for (String interactionId : this.interactionIds) {
/* 460 */       Interaction interaction = (Interaction)Interaction.getAssetMap().getAsset(interactionId);
/* 461 */       if (interaction != null) {
/*     */ 
/*     */ 
/*     */         
/* 465 */         interaction.compile(builder);
/* 466 */         needsSyncRemote |= interaction.needsRemoteSync();
/*     */       } 
/*     */     } 
/* 469 */     this.operations = builder.build();
/* 470 */     this.needsRemoteSync = needsSyncRemote;
/*     */ 
/*     */     
/* 473 */     for (Operation op : this.operations) {
/* 474 */       op = op.getInnerOperation();
/* 475 */       if (op instanceof Interaction) { Interaction inter = (Interaction)op;
/* 476 */         if (inter.getWaitForDataFrom() == WaitForDataFrom.Client && !inter.needsRemoteSync()) {
/* 477 */           throw new IllegalArgumentException(String.valueOf(inter) + " needs client data but isn't marked as requiring syncing to remote clients");
/*     */         } }
/*     */     
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public RootInteraction toPacket() {
/* 486 */     RootInteraction packet = new RootInteraction();
/* 487 */     packet.id = this.id;
/* 488 */     packet.interactions = new int[this.interactionIds.length];
/* 489 */     for (int i = 0; i < this.interactionIds.length; i++) {
/* 490 */       packet.interactions[i] = Interaction.getInteractionIdOrUnknown(this.interactionIds[i]);
/*     */     }
/* 492 */     packet.clickQueuingTimeout = this.clickQueuingTimeout;
/* 493 */     packet.requireNewClick = this.requireNewClick;
/* 494 */     packet.rules = this.rules.toPacket();
/* 495 */     packet.settings = this.settings;
/* 496 */     packet.cooldown = this.cooldown;
/* 497 */     if (this.data != null) {
/* 498 */       packet.tags = this.data.getTags().keySet().toIntArray();
/*     */     }
/* 500 */     return packet;
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
/*     */   @Nullable
/*     */   @Deprecated
/*     */   public static RootInteraction getRootInteractionOrUnknown(@Nonnull String id) {
/* 515 */     return (RootInteraction)getAssetMap().getAsset(getRootInteractionIdOrUnknown(id));
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
/*     */   public static int getRootInteractionIdOrUnknown(@Nullable String id) {
/* 529 */     if (id == null) return Integer.MIN_VALUE;
/*     */     
/* 531 */     IndexedLookupTableAssetMap<String, RootInteraction> assetMap = getAssetMap();
/*     */     
/* 533 */     int interactionId = assetMap.getIndex(id);
/* 534 */     if (interactionId == Integer.MIN_VALUE) {
/* 535 */       HytaleLogger.getLogger().at(Level.WARNING).log("Missing root interaction %s", id);
/* 536 */       getAssetStore().loadAssets("Hytale:Hytale", List.of(new RootInteraction(id, new String[0])));
/*     */       
/* 538 */       int index = assetMap.getIndex(id);
/* 539 */       if (index == Integer.MIN_VALUE) throw new IllegalArgumentException("Unknown key! " + id);
/*     */       
/* 541 */       interactionId = index;
/*     */     } 
/* 543 */     return interactionId;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 549 */     return "RootInteraction{id='" + this.id + "', interactionIds=" + 
/*     */       
/* 551 */       Arrays.toString((Object[])this.interactionIds) + ", settings=" + String.valueOf(this.settings) + ", requireNewClick=" + this.requireNewClick + ", clickQueuingTimeout=" + this.clickQueuingTimeout + ", rules=" + String.valueOf(this.rules) + ", operations=" + 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 556 */       Arrays.toString((Object[])this.operations) + ", needsRemoteSync=" + this.needsRemoteSync + "}";
/*     */   }
/*     */   
/*     */   public RootInteraction() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\RootInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */