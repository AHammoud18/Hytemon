/*     */ package com.hypixel.hytale.server.core.entity.entities.player.data;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.codec.codecs.map.MapCodec;
/*     */ import com.hypixel.hytale.codec.codecs.map.Object2IntMapCodec;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockMigration;
/*     */ import com.hypixel.hytale.server.core.universe.Universe;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntMaps;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import java.util.function.Function;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class PlayerConfigData
/*     */ {
/*     */   @Nonnull
/*     */   public static final BuilderCodec<PlayerConfigData> CODEC;
/*     */   
/*     */   static {
/* 124 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(PlayerConfigData.class, PlayerConfigData::new).addField(new KeyedCodec("BlockIdVersion", (Codec)Codec.INTEGER), (playerConfigData, s) -> playerConfigData.blockIdVersion = s.intValue(), playerConfigData -> Integer.valueOf(playerConfigData.blockIdVersion))).addField(new KeyedCodec("World", (Codec)Codec.STRING), (playerConfigData, s) -> playerConfigData.world = s, playerConfigData -> playerConfigData.world)).addField(new KeyedCodec("Preset", (Codec)Codec.STRING), (playerConfigData, s) -> playerConfigData.preset = s, playerConfigData -> playerConfigData.preset)).addField(new KeyedCodec("KnownRecipes", (Codec)new ArrayCodec((Codec)Codec.STRING, x$0 -> new String[x$0])), (playerConfigData, knownRecipes) -> { playerConfigData.knownRecipes = Set.of(knownRecipes); playerConfigData.unmodifiableKnownRecipes = Collections.unmodifiableSet(playerConfigData.knownRecipes); }playerConfigData -> (String[])playerConfigData.knownRecipes.toArray(()))).addField(new KeyedCodec("PerWorldData", (Codec)new MapCodec((Codec)PlayerWorldData.CODEC, ConcurrentHashMap::new, false)), (playerConfigData, perWorldData) -> { playerConfigData.perWorldData = perWorldData; playerConfigData.unmodifiablePerWorldData = Collections.unmodifiableMap(perWorldData); }playerConfigData -> playerConfigData.perWorldData)).addField(new KeyedCodec("DiscoveredZones", (Codec)Codec.STRING_ARRAY), (playerConfigData, discoveredZones) -> { playerConfigData.discoveredZones = Set.of(discoveredZones); playerConfigData.unmodifiableDiscoveredZones = Collections.unmodifiableSet(playerConfigData.discoveredZones); }playerConfigData -> (String[])playerConfigData.discoveredZones.toArray(()))).addField(new KeyedCodec("DiscoveredInstances", (Codec)new ArrayCodec((Codec)Codec.UUID_BINARY, x$0 -> new UUID[x$0])), (playerConfigData, discoveredInstances) -> { playerConfigData.discoveredInstances = Set.of(discoveredInstances); playerConfigData.unmodifiableDiscoveredInstances = Collections.unmodifiableSet(playerConfigData.discoveredInstances); }playerConfigData -> (UUID[])playerConfigData.discoveredInstances.toArray(()))).addField(new KeyedCodec("ReputationData", (Codec)new Object2IntMapCodec((Codec)Codec.STRING, Object2IntOpenHashMap::new, false)), (playerConfigData, reputationData) -> { playerConfigData.reputationData = reputationData; playerConfigData.unmodifiableReputationData = Object2IntMaps.unmodifiable(reputationData); }playerConfigData -> playerConfigData.reputationData)).addField(new KeyedCodec("ActiveObjectiveUUIDs", (Codec)new ArrayCodec((Codec)Codec.UUID_BINARY, x$0 -> new UUID[x$0])), (playerConfigData, objectives) -> Collections.addAll(playerConfigData.activeObjectiveUUIDs, objectives), playerConfigData -> (UUID[])playerConfigData.activeObjectiveUUIDs.toArray(()))).afterDecode(data -> { for (PlayerWorldData worldData : data.perWorldData.values()) worldData.setPlayerConfigData(data);  int v = data.getBlockIdVersion(); Map<Integer, BlockMigration> blockMigrationMap = BlockMigration.getAssetMap().getAssetMap(); BlockMigration migration = blockMigrationMap.get(Integer.valueOf(v)); Function<String, String> blockMigration = null; while (migration != null) { if (blockMigration == null) { Objects.requireNonNull(migration); blockMigration = migration::getMigration; } else { Objects.requireNonNull(migration); blockMigration = blockMigration.andThen(migration::getMigration); }  migration = blockMigrationMap.get(Integer.valueOf(++v)); }  data.setBlockIdVersion(v); if (blockMigration != null) { Set<String> oldKnownRecipes = data.getKnownRecipes(); if (!oldKnownRecipes.isEmpty()) { Set<String> knownRecipes = new HashSet<>(); for (String blockTypeKey : oldKnownRecipes) knownRecipes.add(blockMigration.apply(blockTypeKey));  data.setKnownRecipes(knownRecipes); }  }  })).build();
/*     */   } @Nonnull
/* 126 */   private final transient AtomicBoolean hasChanged = new AtomicBoolean();
/*     */ 
/*     */   
/* 129 */   private int blockIdVersion = 1;
/*     */   private String world;
/*     */   private String preset;
/*     */   @Nonnull
/* 133 */   private Set<String> knownRecipes = new HashSet<>();
/*     */   
/*     */   @Nonnull
/* 136 */   private Set<String> unmodifiableKnownRecipes = Collections.unmodifiableSet(this.knownRecipes);
/* 137 */   private Map<String, PlayerWorldData> perWorldData = new ConcurrentHashMap<>();
/*     */   @Nonnull
/* 139 */   private Map<String, PlayerWorldData> unmodifiablePerWorldData = Collections.unmodifiableMap(this.perWorldData); @Nonnull
/* 140 */   private Set<String> discoveredZones = new HashSet<>();
/*     */   
/*     */   @Nonnull
/* 143 */   private Set<String> unmodifiableDiscoveredZones = Collections.unmodifiableSet(this.discoveredZones); @Nonnull
/* 144 */   private Set<UUID> discoveredInstances = new HashSet<>();
/*     */   
/*     */   @Nonnull
/* 147 */   private Set<UUID> unmodifiableDiscoveredInstances = Collections.unmodifiableSet(this.discoveredInstances);
/* 148 */   private Object2IntMap<String> reputationData = (Object2IntMap<String>)new Object2IntOpenHashMap();
/*     */   @Nonnull
/* 150 */   private Object2IntMap<String> unmodifiableReputationData = Object2IntMaps.unmodifiable(this.reputationData);
/*     */   @Nonnull
/* 152 */   private Set<UUID> activeObjectiveUUIDs = ConcurrentHashMap.newKeySet();
/*     */   @Nonnull
/* 154 */   private Set<UUID> unmodifiableActiveObjectiveUUIDs = Collections.unmodifiableSet(this.activeObjectiveUUIDs);
/*     */ 
/*     */   
/* 157 */   public final Vector3d lastSavedPosition = new Vector3d();
/* 158 */   public final Vector3f lastSavedRotation = new Vector3f();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBlockIdVersion() {
/* 164 */     return this.blockIdVersion;
/*     */   }
/*     */   
/*     */   public void setBlockIdVersion(int blockIdVersion) {
/* 168 */     this.blockIdVersion = blockIdVersion;
/*     */   }
/*     */   
/*     */   public String getWorld() {
/* 172 */     return this.world;
/*     */   }
/*     */   
/*     */   public void setWorld(@Nonnull String world) {
/* 176 */     this.world = world;
/* 177 */     markChanged();
/*     */   }
/*     */   
/*     */   public String getPreset() {
/* 181 */     return this.preset;
/*     */   }
/*     */   
/*     */   public void setPreset(@Nonnull String preset) {
/* 185 */     this.preset = preset;
/* 186 */     markChanged();
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Set<String> getKnownRecipes() {
/* 191 */     return this.unmodifiableKnownRecipes;
/*     */   }
/*     */   
/*     */   public void setKnownRecipes(@Nonnull Set<String> knownRecipes) {
/* 195 */     this.knownRecipes = knownRecipes;
/* 196 */     this.unmodifiableKnownRecipes = Collections.unmodifiableSet(knownRecipes);
/* 197 */     markChanged();
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Map<String, PlayerWorldData> getPerWorldData() {
/* 202 */     return this.unmodifiablePerWorldData;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public PlayerWorldData getPerWorldData(@Nonnull String worldName) {
/* 207 */     return this.perWorldData.computeIfAbsent(worldName, s -> new PlayerWorldData(this));
/*     */   }
/*     */   
/*     */   public void setPerWorldData(@Nonnull Map<String, PlayerWorldData> perWorldData) {
/* 211 */     this.perWorldData = perWorldData;
/* 212 */     this.unmodifiablePerWorldData = Collections.unmodifiableMap(perWorldData);
/* 213 */     markChanged();
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Set<String> getDiscoveredZones() {
/* 218 */     return this.unmodifiableDiscoveredZones;
/*     */   }
/*     */   
/*     */   public void setDiscoveredZones(@Nonnull Set<String> discoveredZones) {
/* 222 */     this.discoveredZones = discoveredZones;
/* 223 */     this.unmodifiableDiscoveredZones = Collections.unmodifiableSet(discoveredZones);
/* 224 */     markChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Set<UUID> getDiscoveredInstances() {
/* 232 */     return this.unmodifiableDiscoveredInstances;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDiscoveredInstances(@Nonnull Set<UUID> discoveredInstances) {
/* 241 */     this.discoveredInstances = discoveredInstances;
/* 242 */     this.unmodifiableDiscoveredInstances = Collections.unmodifiableSet(discoveredInstances);
/* 243 */     markChanged();
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Object2IntMap<String> getReputationData() {
/* 248 */     return this.unmodifiableReputationData;
/*     */   }
/*     */   
/*     */   public void setReputationData(@Nonnull Object2IntMap<String> reputationData) {
/* 252 */     this.reputationData = reputationData;
/* 253 */     this.unmodifiableReputationData = Object2IntMaps.unmodifiable(reputationData);
/* 254 */     markChanged();
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Set<UUID> getActiveObjectiveUUIDs() {
/* 259 */     return this.unmodifiableActiveObjectiveUUIDs;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setActiveObjectiveUUIDs(@Nonnull Set<UUID> activeObjectiveUUIDs) {
/* 264 */     this.activeObjectiveUUIDs.clear();
/* 265 */     this.activeObjectiveUUIDs.addAll(activeObjectiveUUIDs);
/* 266 */     markChanged();
/*     */   }
/*     */   
/*     */   public void markChanged() {
/* 270 */     this.hasChanged.set(true);
/*     */   }
/*     */   
/*     */   public boolean consumeHasChanged() {
/* 274 */     return this.hasChanged.getAndSet(false);
/*     */   }
/*     */   
/*     */   public void cleanup(@Nonnull Universe universe) {
/* 278 */     Set<String> keySet = this.perWorldData.keySet();
/* 279 */     for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext(); ) {
/* 280 */       String worldName = iterator.next();
/*     */ 
/*     */       
/* 283 */       if (worldName.startsWith("instance-") && universe.getWorld(worldName) == null)
/* 284 */         iterator.remove(); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\entity\entities\player\data\PlayerConfigData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */