/*     */ package com.hypixel.hytale.server.core.asset.type.gameplay;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*     */ import com.hypixel.hytale.assetstore.AssetKeyValidator;
/*     */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*     */ import com.hypixel.hytale.assetstore.AssetStore;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*     */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*     */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.lookup.MapKeyMapCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.ValidatorCache;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.ItemEntityConfig;
/*     */ import com.hypixel.hytale.server.core.asset.type.soundset.config.SoundSet;
/*     */ import java.util.Map;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GameplayConfig
/*     */   implements JsonAssetWithMap<String, DefaultAssetMap<String, GameplayConfig>>
/*     */ {
/*     */   public static final String DEFAULT_ID = "Default";
/*  33 */   public static final GameplayConfig DEFAULT = new GameplayConfig();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  39 */   public static final MapKeyMapCodec<Object> PLUGIN_CODEC = new MapKeyMapCodec(true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */   public static final AssetBuilderCodec<String, GameplayConfig> CODEC;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static AssetStore<String, GameplayConfig, DefaultAssetMap<String, GameplayConfig>> ASSET_STORE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 172 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(GameplayConfig.class, GameplayConfig::new, (Codec)Codec.STRING, (gameplayConfig, s) -> gameplayConfig.id = s, GameplayConfig::getId, (asset, data) -> asset.data = data, asset -> asset.data).appendInherited(new KeyedCodec("Gathering", (Codec)GatheringConfig.CODEC), (gameplayConfig, o) -> gameplayConfig.gatheringConfig = o, gameplayConfig -> gameplayConfig.gatheringConfig, (gameplayConfig, parent) -> gameplayConfig.gatheringConfig = parent.gatheringConfig).add()).appendInherited(new KeyedCodec("World", (Codec)WorldConfig.CODEC), (gameplayConfig, o) -> gameplayConfig.worldConfig = o, gameplayConfig -> gameplayConfig.worldConfig, (gameplayConfig, parent) -> gameplayConfig.worldConfig = parent.worldConfig).add()).appendInherited(new KeyedCodec("WorldMap", (Codec)WorldMapConfig.CODEC), (gameplayConfig, o) -> gameplayConfig.worldMapConfig = o, gameplayConfig -> gameplayConfig.worldMapConfig, (gameplayConfig, parent) -> gameplayConfig.worldMapConfig = parent.worldMapConfig).add()).appendInherited(new KeyedCodec("Death", (Codec)DeathConfig.CODEC), (gameplayConfig, o) -> gameplayConfig.deathConfig = o, gameplayConfig -> gameplayConfig.deathConfig, (gameplayConfig, parent) -> gameplayConfig.deathConfig = parent.deathConfig).add()).appendInherited(new KeyedCodec("Respawn", (Codec)RespawnConfig.CODEC), (gameplayConfig, o) -> gameplayConfig.respawnConfig = o, gameplayConfig -> gameplayConfig.respawnConfig, (gameplayConfig, parent) -> gameplayConfig.respawnConfig = parent.respawnConfig).add()).appendInherited(new KeyedCodec("ShowItemPickupNotifications", (Codec)Codec.BOOLEAN), (gameplayConfig, showItemPickupNotifications) -> gameplayConfig.showItemPickupNotifications = showItemPickupNotifications.booleanValue(), gameplayConfig -> Boolean.valueOf(gameplayConfig.showItemPickupNotifications), (gameplayConfig, parent) -> gameplayConfig.showItemPickupNotifications = parent.showItemPickupNotifications).add()).appendInherited(new KeyedCodec("ItemDurability", (Codec)ItemDurabilityConfig.CODEC), (gameplayConfig, o) -> gameplayConfig.itemDurabilityConfig = o, gameplayConfig -> gameplayConfig.itemDurabilityConfig, (gameplayConfig, parent) -> gameplayConfig.itemDurabilityConfig = parent.itemDurabilityConfig).add()).appendInherited(new KeyedCodec("ItemEntity", (Codec)ItemEntityConfig.CODEC), (gameplayConfig, itemEntityConfig) -> gameplayConfig.itemEntityConfig = itemEntityConfig, gameplayConfig -> gameplayConfig.itemEntityConfig, (gameplayConfig, parent) -> gameplayConfig.itemEntityConfig = parent.itemEntityConfig).add()).appendInherited(new KeyedCodec("Combat", (Codec)CombatConfig.CODEC), (gameplayConfig, combatConfig) -> gameplayConfig.combatConfig = combatConfig, gameplayConfig -> gameplayConfig.combatConfig, (gameplayConfig, parent) -> gameplayConfig.combatConfig = parent.combatConfig).add()).appendInherited(new KeyedCodec("Plugin", (Codec)PLUGIN_CODEC), (o, i) -> { if (o.pluginConfig.isEmpty()) { o.pluginConfig = i; return; }  MapKeyMapCodec.TypeMap<Object> temp = o.pluginConfig; o.pluginConfig = new MapKeyMapCodec.TypeMap(PLUGIN_CODEC); o.pluginConfig.putAll((Map)temp); o.pluginConfig.putAll((Map)i); }o -> o.pluginConfig, (o, p) -> o.pluginConfig = p.pluginConfig).addValidator(Validators.nonNull()).add()).appendInherited(new KeyedCodec("Player", (Codec)PlayerConfig.CODEC), (gameplayConfig, playerConfig) -> gameplayConfig.playerConfig = playerConfig, gameplayConfig -> gameplayConfig.playerConfig, (gameplayConfig, parent) -> gameplayConfig.playerConfig = parent.playerConfig).add()).appendInherited(new KeyedCodec("CameraEffects", (Codec)CameraEffectsConfig.CODEC), (o, i) -> o.cameraEffectsConfig = i, o -> o.cameraEffectsConfig, (o, p) -> o.cameraEffectsConfig = p.cameraEffectsConfig).addValidator(Validators.nonNull()).add()).appendInherited(new KeyedCodec("CreativePlaySoundSet", SoundSet.CHILD_ASSET_CODEC), (o, i) -> o.creativePlaySoundSet = i, o -> o.creativePlaySoundSet, (o, p) -> o.creativePlaySoundSet = p.creativePlaySoundSet).addValidator(SoundSet.VALIDATOR_CACHE.getValidator()).add()).appendInherited(new KeyedCodec("Crafting", (Codec)CraftingConfig.CODEC), (gameplayConfig, o) -> gameplayConfig.craftingConfig = o, gameplayConfig -> gameplayConfig.craftingConfig, (gameplayConfig, parent) -> gameplayConfig.craftingConfig = parent.craftingConfig).add()).appendInherited(new KeyedCodec("Spawn", (Codec)SpawnConfig.CODEC), (o, v) -> o.spawnConfig = v, o -> o.spawnConfig, (o, p) -> o.spawnConfig = p.spawnConfig).add()).appendInherited(new KeyedCodec("MaxEnvironmentalNPCSpawns", (Codec)Codec.INTEGER), (o, v) -> o.maxEnvironmentalNPCSpawns = v.intValue(), o -> Integer.valueOf(o.maxEnvironmentalNPCSpawns), (o, p) -> o.maxEnvironmentalNPCSpawns = p.maxEnvironmentalNPCSpawns).documentation("The absolute maximum number of environmental NPC spawns. < 0 for infinite.").add()).afterDecode(GameplayConfig::processConfig)).build();
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
/*     */   public static AssetStore<String, GameplayConfig, DefaultAssetMap<String, GameplayConfig>> getAssetStore() {
/* 185 */     if (ASSET_STORE == null) ASSET_STORE = AssetRegistry.getAssetStore(GameplayConfig.class); 
/* 186 */     return ASSET_STORE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DefaultAssetMap<String, GameplayConfig> getAssetMap() {
/* 195 */     return (DefaultAssetMap<String, GameplayConfig>)getAssetStore().getAssetMap();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 202 */   public static final ValidatorCache<String> VALIDATOR_CACHE = new ValidatorCache((Validator)new AssetKeyValidator(GameplayConfig::getAssetStore));
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AssetExtraInfo.Data data;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String id;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 217 */   protected GatheringConfig gatheringConfig = new GatheringConfig();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 222 */   protected WorldConfig worldConfig = new WorldConfig();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 227 */   protected WorldMapConfig worldMapConfig = new WorldMapConfig();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 232 */   protected DeathConfig deathConfig = new DeathConfig();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 237 */   protected ItemDurabilityConfig itemDurabilityConfig = new ItemDurabilityConfig();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 242 */   protected ItemEntityConfig itemEntityConfig = new ItemEntityConfig();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 247 */   protected RespawnConfig respawnConfig = new RespawnConfig();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 252 */   protected CombatConfig combatConfig = new CombatConfig();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 257 */   protected MapKeyMapCodec.TypeMap<Object> pluginConfig = MapKeyMapCodec.TypeMap.empty();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 262 */   protected PlayerConfig playerConfig = new PlayerConfig();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 267 */   protected CameraEffectsConfig cameraEffectsConfig = new CameraEffectsConfig();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 272 */   protected CraftingConfig craftingConfig = new CraftingConfig();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 277 */   protected SpawnConfig spawnConfig = new SpawnConfig();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String creativePlaySoundSet;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean showItemPickupNotifications = true;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected transient int creativePlaySoundSetIndex;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 297 */   protected int maxEnvironmentalNPCSpawns = 500;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GatheringConfig getGatheringConfig() {
/* 303 */     return this.gatheringConfig;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldConfig getWorldConfig() {
/* 310 */     return this.worldConfig;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldMapConfig getWorldMapConfig() {
/* 317 */     return this.worldMapConfig;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DeathConfig getDeathConfig() {
/* 324 */     return this.deathConfig;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getShowItemPickupNotifications() {
/* 331 */     return this.showItemPickupNotifications;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemDurabilityConfig getItemDurabilityConfig() {
/* 338 */     return this.itemDurabilityConfig;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemEntityConfig getItemEntityConfig() {
/* 345 */     return this.itemEntityConfig;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RespawnConfig getRespawnConfig() {
/* 352 */     return this.respawnConfig;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CombatConfig getCombatConfig() {
/* 359 */     return this.combatConfig;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MapKeyMapCodec.TypeMap<Object> getPluginConfig() {
/* 366 */     return this.pluginConfig;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PlayerConfig getPlayerConfig() {
/* 373 */     return this.playerConfig;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CameraEffectsConfig getCameraEffectsConfig() {
/* 380 */     return this.cameraEffectsConfig;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCreativePlaySoundSet() {
/* 387 */     return this.creativePlaySoundSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCreativePlaySoundSetIndex() {
/* 394 */     return this.creativePlaySoundSetIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CraftingConfig getCraftingConfig() {
/* 401 */     return this.craftingConfig;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxEnvironmentalNPCSpawns() {
/* 408 */     return this.maxEnvironmentalNPCSpawns;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public SpawnConfig getSpawnConfig() {
/* 416 */     return this.spawnConfig;
/*     */   }
/*     */   
/*     */   protected void processConfig() {
/* 420 */     if (this.creativePlaySoundSet != null) {
/* 421 */       this.creativePlaySoundSetIndex = SoundSet.getAssetMap().getIndex(this.creativePlaySoundSet);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String getId() {
/* 427 */     return this.id;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\gameplay\GameplayConfig.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */