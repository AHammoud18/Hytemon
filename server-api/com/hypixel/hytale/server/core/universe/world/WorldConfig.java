/*     */ package com.hypixel.hytale.server.core.universe.world;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.ExtraInfo;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.map.ObjectMapCodec;
/*     */ import com.hypixel.hytale.codec.lookup.MapKeyMapCodec;
/*     */ import com.hypixel.hytale.codec.schema.metadata.Metadata;
/*     */ import com.hypixel.hytale.codec.schema.metadata.NoDefaultValue;
/*     */ import com.hypixel.hytale.codec.util.RawJsonReader;
/*     */ import com.hypixel.hytale.codec.validation.LegacyValidator;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.common.plugin.PluginIdentifier;
/*     */ import com.hypixel.hytale.common.semver.SemverRange;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.math.shape.Box;
/*     */ import com.hypixel.hytale.math.shape.Box2D;
/*     */ import com.hypixel.hytale.math.vector.Transform;
/*     */ import com.hypixel.hytale.math.vector.Vector2d;
/*     */ import com.hypixel.hytale.protocol.GameMode;
/*     */ import com.hypixel.hytale.server.core.HytaleServer;
/*     */ import com.hypixel.hytale.server.core.asset.type.gameplay.DeathConfig;
/*     */ import com.hypixel.hytale.server.core.asset.type.gameplay.GameplayConfig;
/*     */ import com.hypixel.hytale.server.core.asset.type.weather.config.Weather;
/*     */ import com.hypixel.hytale.server.core.codec.ProtocolCodecs;
/*     */ import com.hypixel.hytale.server.core.codec.ShapeCodecs;
/*     */ import com.hypixel.hytale.server.core.modules.time.WorldTimeResource;
/*     */ import com.hypixel.hytale.server.core.universe.world.spawn.GlobalSpawnProvider;
/*     */ import com.hypixel.hytale.server.core.universe.world.spawn.ISpawnProvider;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.provider.IChunkStorageProvider;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.resources.IResourceStorageProvider;
/*     */ import com.hypixel.hytale.server.core.universe.world.worldgen.IWorldGen;
/*     */ import com.hypixel.hytale.server.core.universe.world.worldgen.provider.IWorldGenProvider;
/*     */ import com.hypixel.hytale.server.core.universe.world.worldmap.provider.IWorldMapProvider;
/*     */ import com.hypixel.hytale.server.core.util.BsonUtil;
/*     */ import java.nio.file.Path;
/*     */ import java.time.Instant;
/*     */ import java.time.temporal.ChronoUnit;
/*     */ import java.util.Collections;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ import org.bson.BsonDocument;
/*     */ import org.bson.BsonValue;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldConfig
/*     */ {
/*     */   public static final int VERSION = 4;
/*     */   public static final int INITIAL_GAME_DAY_START_HOUR = 5;
/*     */   public static final int INITIAL_GAME_DAY_START_MINS = 30;
/*  58 */   public static final MapKeyMapCodec<Object> PLUGIN_CODEC = new MapKeyMapCodec(false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final BuilderCodec<WorldConfig> CODEC;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 359 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(WorldConfig.class, () -> new WorldConfig(null)).versioned()).codecVersion(4)).documentation("Configuration for a single world. Settings in here only affect the world this configuration belongs to.\n\nInstances share this configuration but ignore certain parameters (e.g. *UUID*). In this case the configuration willbe cloned before loading the instance.")).append(new KeyedCodec("UUID", (Codec)Codec.UUID_BINARY), (o, s) -> o.uuid = s, o -> o.uuid).documentation("The unique identifier for this world.\n\nInstances will ignore this and replace it with a freshly generated UUID when spawning the instance.").add()).append(new KeyedCodec("DisplayName", (Codec)Codec.STRING), (o, s) -> o.displayName = s, o -> o.displayName).documentation("The player facing name of this world.").add()).append(new KeyedCodec("Seed", (Codec)Codec.LONG), (o, i) -> o.seed = i.longValue(), o -> Long.valueOf(o.seed)).documentation("The seed of the world, used for world generation.\n\nIf a seed is not set then one will be randomly generated.").metadata((Metadata)NoDefaultValue.INSTANCE).add()).append(new KeyedCodec("SpawnPoint", (Codec)Transform.CODEC), (o, s) -> o.spawnProvider = (ISpawnProvider)new GlobalSpawnProvider(s), o -> null).documentation("**Deprecated**: Please use **SpawnProvider** instead.").setVersionRange(0, 1).add()).append(new KeyedCodec("SpawnProvider", (Codec)ISpawnProvider.CODEC), (o, s) -> o.spawnProvider = s, o -> o.spawnProvider).documentation("Sets the spawn provider for the world. \n\nThis controls where new players will enter the world at. This can be provided by world generation in some cases.").add()).append(new KeyedCodec("WorldGen", (Codec)IWorldGenProvider.CODEC), (o, i) -> o.worldGenProvider = i, o -> o.worldGenProvider).documentation("Sets the world generator that will be used by the world.").add()).append(new KeyedCodec("WorldMap", (Codec)IWorldMapProvider.CODEC), (o, i) -> o.worldMapProvider = i, o -> o.worldMapProvider).add()).append(new KeyedCodec("ChunkStorage", (Codec)IChunkStorageProvider.CODEC), (o, i) -> o.chunkStorageProvider = i, o -> o.chunkStorageProvider).documentation("Sets the storage system that will be used by the world to store chunks.").add()).append(new KeyedCodec("ChunkConfig", (Codec)ChunkConfig.CODEC), (o, i) -> o.chunkConfig = i, o -> o.chunkConfig).documentation("Configuration for chunk related settings").addValidator(Validators.nonNull()).add()).append(new KeyedCodec("IsTicking", (Codec)Codec.BOOLEAN), (o, i) -> o.isTicking = i.booleanValue(), o -> Boolean.valueOf(o.isTicking)).documentation("Sets whether chunks in this world are ticking or not.").add()).append(new KeyedCodec("IsBlockTicking", (Codec)Codec.BOOLEAN), (o, i) -> o.isBlockTicking = i.booleanValue(), o -> Boolean.valueOf(o.isBlockTicking)).documentation("Sets whether blocks in this world are ticking or not.").add()).append(new KeyedCodec("IsPvpEnabled", (Codec)Codec.BOOLEAN), (o, i) -> o.isPvpEnabled = i.booleanValue(), o -> Boolean.valueOf(o.isPvpEnabled)).documentation("Sets whether PvP is allowed in this world or not.").add()).append(new KeyedCodec("IsFallDamageEnabled", (Codec)Codec.BOOLEAN), (o, i) -> o.isFallDamageEnabled = i.booleanValue(), o -> Boolean.valueOf(o.isFallDamageEnabled)).documentation("Sets whether fall damage is allowed in this world or not.").add()).append(new KeyedCodec("IsGameTimePaused", (Codec)Codec.BOOLEAN), (o, i) -> o.isGameTimePaused = i.booleanValue(), o -> Boolean.valueOf(o.isGameTimePaused)).documentation("Sets whether the game time is paused.\n\nThis effects things like day/night cycles and things that rely on those.").add()).append(new KeyedCodec("GameTime", (Codec)Codec.INSTANT), (o, i) -> o.gameTime = i, o -> o.gameTime).documentation("The current time of day in the world.").add()).append(new KeyedCodec("ForcedWeather", (Codec)Codec.STRING), (o, i) -> o.forcedWeather = i, o -> o.forcedWeather).documentation("Sets the type of weather that is being forced to be active in this world.").addValidator(Weather.VALIDATOR_CACHE.getValidator()).add()).append(new KeyedCodec("ClientEffects", (Codec)ClientEffectWorldSettings.CODEC), (o, i) -> o.clientEffects = i, o -> o.clientEffects).documentation("Settings for the client's weather and post-processing effects in this world.").add()).append(new KeyedCodec("PregenerateRegion", (Codec)ShapeCodecs.BOX), (o, i) -> o.chunkConfig.setPregenerateRegion(new Box2D(new Vector2d(i.min.x, i.min.z), new Vector2d(i.max.x, i.max.z))), o -> null).setVersionRange(1, 3).addValidator((LegacyValidator)Validators.deprecated()).add()).append(new KeyedCodec("RequiredPlugins", (Codec)new ObjectMapCodec(SemverRange.CODEC, java.util.HashMap::new, PluginIdentifier::toString, PluginIdentifier::fromString, false)), (o, i) -> o.requiredPlugins = i, o -> o.requiredPlugins).documentation("Sets the plugins that are required to be enabled for this world to function.").add()).append(new KeyedCodec("GameMode", (Codec)ProtocolCodecs.GAMEMODE), (o, i) -> o.gameMode = i, o -> o.gameMode).documentation("Sets the default gamemode for this world.").add()).append(new KeyedCodec("IsSpawningNPC", (Codec)Codec.BOOLEAN), (o, i) -> o.isSpawningNPC = i.booleanValue(), o -> Boolean.valueOf(o.isSpawningNPC)).documentation("Whether NPCs can spawn in this world or not.").add()).append(new KeyedCodec("IsSpawnMarkersEnabled", (Codec)Codec.BOOLEAN), (o, i) -> o.isSpawnMarkersEnabled = i.booleanValue(), o -> Boolean.valueOf(o.isSpawnMarkersEnabled)).documentation("Whether spawn markers are enabled for this world.").add()).append(new KeyedCodec("IsAllNPCFrozen", (Codec)Codec.BOOLEAN), (o, i) -> o.isAllNPCFrozen = i.booleanValue(), o -> Boolean.valueOf(o.isAllNPCFrozen)).documentation("Whether all NPCs are frozen for this world").add()).append(new KeyedCodec("GameplayConfig", (Codec)Codec.STRING), (o, i) -> o.gameplayConfig = i, o -> o.gameplayConfig).addValidator(GameplayConfig.VALIDATOR_CACHE.getValidator()).documentation("The gameplay configuration being used by this world").add()).append(new KeyedCodec("Death", (Codec)DeathConfig.CODEC), (o, i) -> o.deathConfigOverride = i, o -> o.deathConfigOverride).documentation("Inline death configuration overrides for this world. If set, these values take precedence over the referenced GameplayConfig.").add()).append(new KeyedCodec("DaytimeDurationSeconds", (Codec)Codec.INTEGER), (o, i) -> o.daytimeDurationSecondsOverride = i, o -> o.daytimeDurationSecondsOverride).documentation("Override for the duration of daytime in seconds. If set, takes precedence over the referenced GameplayConfig.").add()).append(new KeyedCodec("NighttimeDurationSeconds", (Codec)Codec.INTEGER), (o, i) -> o.nighttimeDurationSecondsOverride = i, o -> o.nighttimeDurationSecondsOverride).documentation("Override for the duration of nighttime in seconds. If set, takes precedence over the referenced GameplayConfig.").add()).append(new KeyedCodec("IsCompassUpdating", (Codec)Codec.BOOLEAN), (o, i) -> o.isCompassUpdating = i.booleanValue(), o -> Boolean.valueOf(o.isCompassUpdating)).documentation("Whether the compass is updating in this world").add()).append(new KeyedCodec("IsSavingPlayers", (Codec)Codec.BOOLEAN), (o, i) -> o.isSavingPlayers = i.booleanValue(), o -> Boolean.valueOf(o.isSavingPlayers)).documentation("Whether the configuration for player's is being saved for players in this world.").add()).append(new KeyedCodec("IsSavingChunks", (Codec)Codec.BOOLEAN), (o, i) -> o.canSaveChunks = i.booleanValue(), o -> Boolean.valueOf(o.canSaveChunks)).documentation("Whether the chunk data is allowed to be saved to the disk for this world.").add()).append(new KeyedCodec("SaveNewChunks", (Codec)Codec.BOOLEAN), (o, i) -> o.saveNewChunks = i.booleanValue(), o -> Boolean.valueOf(o.saveNewChunks)).documentation("Whether newly generated chunks should be marked for saving or not.\nEnabling this can prevent random chunks from being out of place if/when worldgen changes but will increase the size of the world on disk.").add()).append(new KeyedCodec("IsUnloadingChunks", (Codec)Codec.BOOLEAN), (o, i) -> o.canUnloadChunks = i.booleanValue(), o -> Boolean.valueOf(o.canUnloadChunks)).documentation("Whether the chunks should be unloaded like normally, or should be prevented from unloading at all.").add()).append(new KeyedCodec("IsObjectiveMarkersEnabled", (Codec)Codec.BOOLEAN), (o, i) -> o.isObjectiveMarkersEnabled = i.booleanValue(), o -> Boolean.valueOf(o.isObjectiveMarkersEnabled)).documentation("Whether objective markers are enabled for this world.").add()).append(new KeyedCodec("DeleteOnUniverseStart", (Codec)Codec.BOOLEAN), (o, i) -> o.deleteOnUniverseStart = i.booleanValue(), o -> Boolean.valueOf(o.deleteOnUniverseStart)).documentation("Whether this world should be deleted when loaded from Universe start. By default this is when going through the world folders in the universe directory.").add()).append(new KeyedCodec("DeleteOnRemove", (Codec)Codec.BOOLEAN), (o, i) -> o.deleteOnRemove = i.booleanValue(), o -> Boolean.valueOf(o.deleteOnRemove)).documentation("Whether this world should be deleted once its been removed from the server").add()).append(new KeyedCodec("Instance", (Codec)Codec.BSON_DOCUMENT), (o, i, e) -> o.pluginConfig.put(PLUGIN_CODEC.getKeyForId("Instance"), PLUGIN_CODEC.decodeById("Instance", (BsonValue)i, e)), (o, e) -> null).setVersionRange(0, 2).documentation("Instance specific configuration.").addValidator((LegacyValidator)Validators.deprecated()).add()).append(new KeyedCodec("ResourceStorage", (Codec)IResourceStorageProvider.CODEC), (o, i) -> o.resourceStorageProvider = i, o -> o.resourceStorageProvider).documentation("Sets the storage system that will be used to store resources.").add()).appendInherited(new KeyedCodec("Plugin", (Codec)PLUGIN_CODEC), (o, i) -> { if (o.pluginConfig.isEmpty()) { o.pluginConfig = i; return; }  MapKeyMapCodec.TypeMap<Object> temp = o.pluginConfig; o.pluginConfig.putAll((Map)temp); o.pluginConfig.putAll((Map)i); }o -> o.pluginConfig, (o, p) -> o.pluginConfig = p.pluginConfig).addValidator(Validators.nonNull()).add()).build();
/*     */   } @Nonnull
/* 361 */   private transient AtomicBoolean hasChanged = new AtomicBoolean();
/*     */ 
/*     */   
/* 364 */   private UUID uuid = UUID.randomUUID();
/*     */   private String displayName;
/* 366 */   private long seed = System.currentTimeMillis(); @Nullable
/* 367 */   private ISpawnProvider spawnProvider = null;
/*     */   
/* 369 */   private IWorldGenProvider worldGenProvider = (IWorldGenProvider)IWorldGenProvider.CODEC.getDefault();
/* 370 */   private IWorldMapProvider worldMapProvider = (IWorldMapProvider)IWorldMapProvider.CODEC.getDefault();
/* 371 */   private IChunkStorageProvider chunkStorageProvider = (IChunkStorageProvider)IChunkStorageProvider.CODEC.getDefault(); @Nonnull
/* 372 */   private ChunkConfig chunkConfig = new ChunkConfig();
/*     */   
/*     */   private boolean isTicking = true;
/*     */   private boolean isBlockTicking = true;
/*     */   private boolean isPvpEnabled = false;
/*     */   private boolean isFallDamageEnabled = true;
/*     */   private boolean isGameTimePaused = false;
/* 379 */   private Instant gameTime = WorldTimeResource.ZERO_YEAR.plus(5L, ChronoUnit.HOURS).plus(30L, ChronoUnit.MINUTES);
/*     */   private String forcedWeather;
/* 381 */   private ClientEffectWorldSettings clientEffects = new ClientEffectWorldSettings();
/* 382 */   private Map<PluginIdentifier, SemverRange> requiredPlugins = Collections.emptyMap();
/*     */   private GameMode gameMode;
/*     */   private boolean isSpawningNPC = true;
/*     */   private boolean isSpawnMarkersEnabled = true;
/*     */   private boolean isAllNPCFrozen = false;
/* 387 */   private String gameplayConfig = "Default"; @Nullable
/* 388 */   private DeathConfig deathConfigOverride = null;
/*     */   @Nullable
/* 390 */   private Integer daytimeDurationSecondsOverride = null;
/*     */   @Nullable
/* 392 */   private Integer nighttimeDurationSecondsOverride = null;
/*     */ 
/*     */   
/*     */   private boolean isCompassUpdating = true;
/*     */ 
/*     */   
/*     */   private boolean isSavingPlayers = true;
/*     */ 
/*     */   
/*     */   private boolean canSaveChunks = true;
/*     */ 
/*     */   
/*     */   private boolean saveNewChunks = true;
/*     */ 
/*     */   
/*     */   private boolean canUnloadChunks = true;
/*     */ 
/*     */   
/*     */   private boolean isObjectiveMarkersEnabled = true;
/*     */ 
/*     */   
/*     */   private boolean deleteOnUniverseStart = false;
/*     */   
/*     */   private boolean deleteOnRemove = false;
/*     */   
/* 417 */   private IResourceStorageProvider resourceStorageProvider = (IResourceStorageProvider)IResourceStorageProvider.CODEC.getDefault();
/* 418 */   protected MapKeyMapCodec.TypeMap<Object> pluginConfig = new MapKeyMapCodec.TypeMap(PLUGIN_CODEC);
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private transient ISpawnProvider defaultSpawnProvider;
/*     */ 
/*     */   
/*     */   private transient boolean isSavingConfig = true;
/*     */ 
/*     */   
/*     */   public WorldConfig() {
/* 429 */     markChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public UUID getUuid() {
/* 437 */     return this.uuid;
/*     */   }
/*     */   
/*     */   public void setUuid(UUID uuid) {
/* 441 */     this.uuid = uuid;
/*     */   }
/*     */   
/*     */   public boolean isDeleteOnUniverseStart() {
/* 445 */     return this.deleteOnUniverseStart;
/*     */   }
/*     */   
/*     */   public void setDeleteOnUniverseStart(boolean deleteOnUniverseStart) {
/* 449 */     this.deleteOnUniverseStart = deleteOnUniverseStart;
/*     */   }
/*     */   
/*     */   public boolean isDeleteOnRemove() {
/* 453 */     return this.deleteOnRemove;
/*     */   }
/*     */   
/*     */   public void setDeleteOnRemove(boolean deleteOnRemove) {
/* 457 */     this.deleteOnRemove = deleteOnRemove;
/*     */   }
/*     */   
/*     */   public boolean isSavingConfig() {
/* 461 */     return this.isSavingConfig;
/*     */   }
/*     */   
/*     */   public void setSavingConfig(boolean savingConfig) {
/* 465 */     this.isSavingConfig = savingConfig;
/*     */   }
/*     */   
/*     */   public String getDisplayName() {
/* 469 */     return this.displayName;
/*     */   }
/*     */   
/*     */   public void setDisplayName(String name) {
/* 473 */     this.displayName = name;
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
/*     */   public static String formatDisplayName(@Nonnull String name) {
/* 485 */     return name
/* 486 */       .replaceAll("([a-z])([A-Z])", "$1 $2")
/* 487 */       .replaceAll("([A-Za-z])([0-9])", "$1 $2")
/* 488 */       .replaceAll("_", " ");
/*     */   }
/*     */   
/*     */   public long getSeed() {
/* 492 */     return this.seed;
/*     */   }
/*     */   
/*     */   public void setSeed(long seed) {
/* 496 */     this.seed = seed;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public ISpawnProvider getSpawnProvider() {
/* 501 */     if (this.spawnProvider != null) return this.spawnProvider; 
/* 502 */     return this.defaultSpawnProvider;
/*     */   }
/*     */   
/*     */   public void setSpawnProvider(ISpawnProvider spawnProvider) {
/* 506 */     this.spawnProvider = spawnProvider;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDefaultSpawnProvider(@Nonnull IWorldGen generator) {
/* 511 */     this.defaultSpawnProvider = generator.getDefaultSpawnProvider((int)this.seed);
/*     */   }
/*     */   
/*     */   public IWorldGenProvider getWorldGenProvider() {
/* 515 */     return this.worldGenProvider;
/*     */   }
/*     */   
/*     */   public void setWorldGenProvider(IWorldGenProvider worldGenProvider) {
/* 519 */     this.worldGenProvider = worldGenProvider;
/*     */   }
/*     */   
/*     */   public IWorldMapProvider getWorldMapProvider() {
/* 523 */     return this.worldMapProvider;
/*     */   }
/*     */   
/*     */   public void setWorldMapProvider(IWorldMapProvider worldMapProvider) {
/* 527 */     this.worldMapProvider = worldMapProvider;
/*     */   }
/*     */   
/*     */   public IChunkStorageProvider getChunkStorageProvider() {
/* 531 */     return this.chunkStorageProvider;
/*     */   }
/*     */   
/*     */   public void setChunkStorageProvider(IChunkStorageProvider chunkStorageProvider) {
/* 535 */     this.chunkStorageProvider = chunkStorageProvider;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public ChunkConfig getChunkConfig() {
/* 540 */     return this.chunkConfig;
/*     */   }
/*     */   
/*     */   public void setChunkConfig(@Nonnull ChunkConfig chunkConfig) {
/* 544 */     this.chunkConfig = chunkConfig;
/*     */   }
/*     */   
/*     */   public boolean isTicking() {
/* 548 */     return this.isTicking;
/*     */   }
/*     */   
/*     */   public void setTicking(boolean ticking) {
/* 552 */     this.isTicking = ticking;
/*     */   }
/*     */   
/*     */   public boolean isBlockTicking() {
/* 556 */     return this.isBlockTicking;
/*     */   }
/*     */   
/*     */   public void setBlockTicking(boolean ticking) {
/* 560 */     this.isBlockTicking = ticking;
/*     */   }
/*     */   
/*     */   public boolean isPvpEnabled() {
/* 564 */     return this.isPvpEnabled;
/*     */   }
/*     */   
/*     */   public boolean isFallDamageEnabled() {
/* 568 */     return this.isFallDamageEnabled;
/*     */   }
/*     */   
/*     */   public void setPvpEnabled(boolean pvpEnabled) {
/* 572 */     this.isPvpEnabled = pvpEnabled;
/*     */   }
/*     */   
/*     */   public boolean isGameTimePaused() {
/* 576 */     return this.isGameTimePaused;
/*     */   }
/*     */   
/*     */   public void setGameTimePaused(boolean gameTimePaused) {
/* 580 */     this.isGameTimePaused = gameTimePaused;
/*     */   }
/*     */   
/*     */   public Instant getGameTime() {
/* 584 */     return this.gameTime;
/*     */   }
/*     */   
/*     */   public void setGameTime(Instant gameTime) {
/* 588 */     this.gameTime = gameTime;
/*     */   }
/*     */   
/*     */   public String getForcedWeather() {
/* 592 */     return this.forcedWeather;
/*     */   }
/*     */   
/*     */   public void setForcedWeather(String forcedWeather) {
/* 596 */     this.forcedWeather = forcedWeather;
/*     */   }
/*     */   
/*     */   public void setClientEffects(ClientEffectWorldSettings clientEffects) {
/* 600 */     this.clientEffects = clientEffects;
/*     */   }
/*     */   
/*     */   public ClientEffectWorldSettings getClientEffects() {
/* 604 */     return this.clientEffects;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Map<PluginIdentifier, SemverRange> getRequiredPlugins() {
/* 609 */     return Collections.unmodifiableMap(this.requiredPlugins);
/*     */   }
/*     */   
/*     */   public void setRequiredPlugins(Map<PluginIdentifier, SemverRange> requiredPlugins) {
/* 613 */     this.requiredPlugins = requiredPlugins;
/*     */   }
/*     */   
/*     */   public GameMode getGameMode() {
/* 617 */     if (this.gameMode != null) return this.gameMode; 
/* 618 */     return HytaleServer.get().getConfig().getDefaults().getGameMode();
/*     */   }
/*     */   
/*     */   public void setGameMode(GameMode gameMode) {
/* 622 */     this.gameMode = gameMode;
/*     */   }
/*     */   
/*     */   public boolean isSpawningNPC() {
/* 626 */     return this.isSpawningNPC;
/*     */   }
/*     */   
/*     */   public void setSpawningNPC(boolean spawningNPC) {
/* 630 */     this.isSpawningNPC = spawningNPC;
/*     */   }
/*     */   
/*     */   public boolean isSpawnMarkersEnabled() {
/* 634 */     return this.isSpawnMarkersEnabled;
/*     */   }
/*     */   
/*     */   public void setIsSpawnMarkersEnabled(boolean spawnMarkersEnabled) {
/* 638 */     this.isSpawnMarkersEnabled = spawnMarkersEnabled;
/*     */   }
/*     */   
/*     */   public boolean isAllNPCFrozen() {
/* 642 */     return this.isAllNPCFrozen;
/*     */   }
/*     */   
/*     */   public void setIsAllNPCFrozen(boolean allNPCFrozen) {
/* 646 */     this.isAllNPCFrozen = allNPCFrozen;
/*     */   }
/*     */   
/*     */   public String getGameplayConfig() {
/* 650 */     return this.gameplayConfig;
/*     */   }
/*     */   
/*     */   public void setGameplayConfig(String gameplayConfig) {
/* 654 */     this.gameplayConfig = gameplayConfig;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public DeathConfig getDeathConfigOverride() {
/* 659 */     return this.deathConfigOverride;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Integer getDaytimeDurationSecondsOverride() {
/* 664 */     return this.daytimeDurationSecondsOverride;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Integer getNighttimeDurationSecondsOverride() {
/* 669 */     return this.nighttimeDurationSecondsOverride;
/*     */   }
/*     */   
/*     */   public boolean isCompassUpdating() {
/* 673 */     return this.isCompassUpdating;
/*     */   }
/*     */   
/*     */   public void setCompassUpdating(boolean compassUpdating) {
/* 677 */     this.isCompassUpdating = compassUpdating;
/*     */   }
/*     */   
/*     */   public boolean isSavingPlayers() {
/* 681 */     return this.isSavingPlayers;
/*     */   }
/*     */   
/*     */   public void setSavingPlayers(boolean savingPlayers) {
/* 685 */     this.isSavingPlayers = savingPlayers;
/*     */   }
/*     */   
/*     */   public boolean canUnloadChunks() {
/* 689 */     return this.canUnloadChunks;
/*     */   }
/*     */   
/*     */   public void setCanUnloadChunks(boolean unloadingChunks) {
/* 693 */     this.canUnloadChunks = unloadingChunks;
/*     */   }
/*     */   
/*     */   public boolean canSaveChunks() {
/* 697 */     return this.canSaveChunks;
/*     */   }
/*     */   
/*     */   public void setCanSaveChunks(boolean savingChunks) {
/* 701 */     this.canSaveChunks = savingChunks;
/*     */   }
/*     */   
/*     */   public boolean shouldSaveNewChunks() {
/* 705 */     return this.saveNewChunks;
/*     */   }
/*     */   
/*     */   public void setSaveNewChunks(boolean saveNewChunks) {
/* 709 */     this.saveNewChunks = saveNewChunks;
/*     */   }
/*     */   
/*     */   public boolean isObjectiveMarkersEnabled() {
/* 713 */     return this.isObjectiveMarkersEnabled;
/*     */   }
/*     */   
/*     */   public void setObjectiveMarkersEnabled(boolean objectiveMarkersEnabled) {
/* 717 */     this.isObjectiveMarkersEnabled = objectiveMarkersEnabled;
/*     */   }
/*     */   
/*     */   public IResourceStorageProvider getResourceStorageProvider() {
/* 721 */     return this.resourceStorageProvider;
/*     */   }
/*     */   
/*     */   public void setResourceStorageProvider(@Nonnull IResourceStorageProvider resourceStorageProvider) {
/* 725 */     this.resourceStorageProvider = resourceStorageProvider;
/*     */   }
/*     */   
/*     */   public MapKeyMapCodec.TypeMap<Object> getPluginConfig() {
/* 729 */     return this.pluginConfig;
/*     */   }
/*     */   
/*     */   public void markChanged() {
/* 733 */     this.hasChanged.set(true);
/*     */   }
/*     */   
/*     */   public boolean consumeHasChanged() {
/* 737 */     return this.hasChanged.getAndSet(false);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static CompletableFuture<WorldConfig> load(@Nonnull Path path) {
/* 742 */     return CompletableFuture.supplyAsync(() -> {
/*     */           WorldConfig config = (WorldConfig)RawJsonReader.readSyncWithBak(path, (Codec)CODEC, HytaleLogger.getLogger());
/*     */           return (config != null) ? config : new WorldConfig();
/*     */         });
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static CompletableFuture<Void> save(@Nonnull Path path, WorldConfig worldConfig) {
/* 750 */     BsonDocument document = CODEC.encode(worldConfig, ExtraInfo.THREAD_LOCAL.get());
/* 751 */     return BsonUtil.writeDocument(path, document);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private WorldConfig(Void dummy) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class ChunkConfig
/*     */   {
/*     */     public static final BuilderCodec<ChunkConfig> CODEC;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/* 782 */       CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ChunkConfig.class, ChunkConfig::new).appendInherited(new KeyedCodec("PregenerateRegion", (Codec)Box2D.CODEC), (o, i) -> o.pregenerateRegion = i, o -> o.pregenerateRegion, (o, p) -> o.pregenerateRegion = p.pregenerateRegion).documentation("Sets the region that will be pregenerated for the world.\n\nIf set, the specified region will be pregenerated when the world starts.").add()).appendInherited(new KeyedCodec("KeepLoadedRegion", (Codec)Box2D.CODEC), (o, i) -> o.keepLoadedRegion = i, o -> o.keepLoadedRegion, (o, p) -> o.keepLoadedRegion = p.keepLoadedRegion).documentation("Sets a region of chunks that will never be unloaded.").add()).afterDecode(o -> { if (o.pregenerateRegion != null) o.pregenerateRegion.normalize();  if (o.keepLoadedRegion != null) o.keepLoadedRegion.normalize();  })).build();
/*     */     }
/* 784 */     private static final Box2D DEFAULT_PREGENERATE_REGION = new Box2D(new Vector2d(-512.0D, -512.0D), new Vector2d(512.0D, 512.0D));
/*     */     
/*     */     @Nullable
/*     */     private Box2D pregenerateRegion;
/*     */     @Nullable
/*     */     private Box2D keepLoadedRegion;
/*     */     
/*     */     @Nullable
/*     */     public Box2D getPregenerateRegion() {
/* 793 */       return this.pregenerateRegion;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setPregenerateRegion(@Nullable Box2D pregenerateRegion) {
/* 804 */       if (pregenerateRegion != null) pregenerateRegion.normalize(); 
/* 805 */       this.pregenerateRegion = pregenerateRegion;
/*     */     }
/*     */     
/*     */     @Nullable
/*     */     public Box2D getKeepLoadedRegion() {
/* 810 */       return this.keepLoadedRegion;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setKeepLoadedRegion(@Nullable Box2D keepLoadedRegion) {
/* 821 */       if (keepLoadedRegion != null) keepLoadedRegion.normalize(); 
/* 822 */       this.keepLoadedRegion = keepLoadedRegion;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\WorldConfig.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */