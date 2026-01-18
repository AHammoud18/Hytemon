/*     */ package com.hypixel.hytale.server.spawning.local;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.weather.components.WeatherTracker;
/*     */ import com.hypixel.hytale.component.Archetype;
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.ResourceType;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.spatial.SpatialResource;
/*     */ import com.hypixel.hytale.component.system.tick.TickingSystem;
/*     */ import com.hypixel.hytale.function.function.TriIntObjectDoubleToByteFunction;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.protocol.BlockMaterial;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.time.WorldTimeResource;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.spawning.SpawningPlugin;
/*     */ import com.hypixel.hytale.server.spawning.assets.spawns.LightType;
/*     */ import com.hypixel.hytale.server.spawning.assets.spawns.config.BeaconNPCSpawn;
/*     */ import com.hypixel.hytale.server.spawning.beacons.LegacySpawnBeaconEntity;
/*     */ import com.hypixel.hytale.server.spawning.util.LightRangePredicate;
/*     */ import com.hypixel.hytale.server.spawning.wrappers.BeaconSpawnWrapper;
/*     */ import it.unimi.dsi.fastutil.Pair;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ByteMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ByteOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectList;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class LocalSpawnControllerSystem
/*     */   extends TickingSystem<EntityStore>
/*     */ {
/*     */   public static final double RUN_FREQUENCY_SECONDS = 5.0D;
/*     */   private static final int LIGHT_LEVEL_EVALUATION_RADIUS = 4;
/*     */   private final Archetype<EntityStore> controllerArchetype;
/*     */   private final ComponentType<EntityStore, LocalSpawnController> spawnControllerComponentType;
/*     */   private final ComponentType<EntityStore, TransformComponent> transformComponentype;
/*     */   private final ComponentType<EntityStore, WeatherTracker> weatherTrackerComponentType;
/*     */   private final ComponentType<EntityStore, LocalSpawnBeacon> localSpawnBeaconComponentType;
/*     */   private final ComponentType<EntityStore, LegacySpawnBeaconEntity> spawnBeaconComponentType;
/*     */   private final ResourceType<EntityStore, LocalSpawnState> localSpawnStateResourceType;
/*     */   private final ResourceType<EntityStore, SpatialResource<Ref<EntityStore>, EntityStore>> beaconSpatialComponent;
/*     */   
/*     */   public LocalSpawnControllerSystem(ComponentType<EntityStore, LocalSpawnController> spawnControllerComponentType, ComponentType<EntityStore, TransformComponent> transformComponentype, ComponentType<EntityStore, WeatherTracker> weatherTrackerComponentType, ComponentType<EntityStore, LocalSpawnBeacon> localSpawnBeaconComponentType, ComponentType<EntityStore, LegacySpawnBeaconEntity> spawnBeaconComponentType, ResourceType<EntityStore, LocalSpawnState> localSpawnStateResourceType, ResourceType<EntityStore, SpatialResource<Ref<EntityStore>, EntityStore>> beaconSpatialComponent) {
/*  57 */     this.spawnControllerComponentType = spawnControllerComponentType;
/*  58 */     this.transformComponentype = transformComponentype;
/*  59 */     this.weatherTrackerComponentType = weatherTrackerComponentType;
/*  60 */     this.localSpawnBeaconComponentType = localSpawnBeaconComponentType;
/*  61 */     this.spawnBeaconComponentType = spawnBeaconComponentType;
/*  62 */     this.localSpawnStateResourceType = localSpawnStateResourceType;
/*  63 */     this.beaconSpatialComponent = beaconSpatialComponent;
/*  64 */     this.controllerArchetype = Archetype.of(new ComponentType[] { spawnControllerComponentType, PlayerRef.getComponentType() });
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(float dt, int systemIndex, @Nonnull Store<EntityStore> store) {
/*  69 */     LocalSpawnState localSpawnState = (LocalSpawnState)store.getResource(this.localSpawnStateResourceType);
/*     */ 
/*     */     
/*  72 */     List<Ref<EntityStore>> controllers = localSpawnState.getLocalControllerList();
/*     */ 
/*     */     
/*  75 */     store.forEachChunk((Query)this.controllerArchetype, (archetypeChunk, commandBuffer) -> {
/*     */           for (int index = 0; index < archetypeChunk.size(); index++) {
/*     */             LocalSpawnController spawnControllerComponent = (LocalSpawnController)archetypeChunk.getComponent(index, this.spawnControllerComponentType);
/*     */ 
/*     */             
/*     */             assert spawnControllerComponent != null;
/*     */ 
/*     */             
/*     */             if (spawnControllerComponent.tickTimeToNextRunSeconds(dt)) {
/*     */               controllers.add(archetypeChunk.getReferenceTo(index));
/*     */             }
/*     */           } 
/*     */         });
/*     */     
/*  89 */     if (controllers.isEmpty())
/*     */       return; 
/*  91 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*  92 */     List<LegacySpawnBeaconEntity> pendingSpawns = localSpawnState.getLocalPendingSpawns();
/*  93 */     ObjectList<Ref<EntityStore>> existingBeacons = SpatialResource.getThreadLocalReferenceList();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  98 */     for (int index = 0; index < controllers.size(); index++) {
/*  99 */       Ref<EntityStore> reference = controllers.get(index);
/* 100 */       LocalSpawnController spawnControllerComponent = (LocalSpawnController)store.getComponent(reference, this.spawnControllerComponentType);
/* 101 */       assert spawnControllerComponent != null;
/*     */       
/* 103 */       PlayerRef playerRefComponent = (PlayerRef)store.getComponent(reference, PlayerRef.getComponentType());
/* 104 */       assert playerRefComponent != null;
/*     */       
/* 106 */       SpawningPlugin.get().getLogger().at(Level.FINE).log("Running local spawn controller for player %s", playerRefComponent.getUsername());
/* 107 */       TransformComponent transformComponent = (TransformComponent)store.getComponent(reference, this.transformComponentype);
/* 108 */       assert transformComponent != null;
/*     */ 
/*     */       
/* 111 */       WeatherTracker weatherTrackerComponent = (WeatherTracker)store.getComponent(reference, this.weatherTrackerComponentType);
/* 112 */       assert weatherTrackerComponent != null;
/*     */       
/* 114 */       weatherTrackerComponent.updateEnvironment(transformComponent, (ComponentAccessor)store);
/* 115 */       int environmentIndex = weatherTrackerComponent.getEnvironmentId();
/*     */ 
/*     */       
/* 118 */       List<BeaconSpawnWrapper> possibleBeacons = SpawningPlugin.get().getBeaconSpawnsForEnvironment(environmentIndex);
/* 119 */       if (possibleBeacons == null || possibleBeacons.isEmpty()) {
/* 120 */         spawnControllerComponent.setTimeToNextRunSeconds(5.0D);
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 125 */         BeaconSpawnWrapper firstBeacon = possibleBeacons.getFirst();
/* 126 */         double largestDistance = firstBeacon.getBeaconRadius();
/* 127 */         int[] firstRange = ((BeaconNPCSpawn)firstBeacon.getSpawn()).getYRange();
/* 128 */         int lowestY = firstRange[0];
/* 129 */         int highestY = firstRange[1];
/* 130 */         for (int i = 1; i < possibleBeacons.size(); i++) {
/* 131 */           BeaconSpawnWrapper beacon = possibleBeacons.get(i);
/* 132 */           double radius = beacon.getBeaconRadius();
/* 133 */           if (radius > largestDistance) largestDistance = radius; 
/* 134 */           int[] yRange = ((BeaconNPCSpawn)beacon.getSpawn()).getYRange();
/* 135 */           if (yRange[0] < lowestY) lowestY = yRange[0]; 
/* 136 */           if (yRange[1] > highestY) highestY = yRange[1];
/*     */         
/*     */         } 
/*     */         
/* 140 */         largestDistance *= 2.0D;
/*     */         
/* 142 */         Vector3d position = transformComponent.getPosition();
/* 143 */         double largestDistanceSquared = largestDistance * largestDistance;
/*     */ 
/*     */         
/* 146 */         int yDistance = Math.abs(lowestY) + Math.abs(highestY);
/* 147 */         int y = MathUtil.floor(position.getY());
/* 148 */         int minY = Math.max(0, y - yDistance);
/* 149 */         int maxY = Math.min(319, y + yDistance);
/*     */         
/* 151 */         SpatialResource<Ref<EntityStore>, EntityStore> spatialResource = (SpatialResource<Ref<EntityStore>, EntityStore>)store.getResource(this.beaconSpatialComponent);
/* 152 */         spatialResource.getSpatialStructure().ordered(position, largestDistance, (List)existingBeacons);
/*     */         
/* 154 */         WorldTimeResource worldTimeResource = (WorldTimeResource)store.getResource(WorldTimeResource.getResourceType());
/*     */         
/* 156 */         double sunlightFactor = worldTimeResource.getSunlightFactor();
/* 157 */         int xPos = MathUtil.floor(position.getX());
/* 158 */         int yPos = MathUtil.floor(position.getY());
/* 159 */         int zPos = MathUtil.floor(position.getZ());
/*     */         
/* 161 */         Object2ByteOpenHashMap<LightType> averageLightValues = new Object2ByteOpenHashMap();
/* 162 */         averageLightValues.defaultReturnValue((byte)-1);
/*     */         int j;
/* 164 */         label101: for (j = 0; j < possibleBeacons.size(); j++) {
/* 165 */           BeaconSpawnWrapper possibleBeacon = possibleBeacons.get(j);
/*     */ 
/*     */           
/* 168 */           if (possibleBeacon.spawnParametersMatch((ComponentAccessor)store)) {
/*     */             int k;
/* 170 */             for (k = 0; k < existingBeacons.size(); k++) {
/* 171 */               Ref<EntityStore> existingBeaconReference = (Ref<EntityStore>)existingBeacons.get(k);
/*     */               
/* 173 */               LegacySpawnBeaconEntity existingBeaconComponent = (LegacySpawnBeaconEntity)store.getComponent(existingBeaconReference, this.spawnBeaconComponentType);
/* 174 */               assert existingBeaconComponent != null;
/*     */               
/* 176 */               TransformComponent existingBeaconTransformComponent = (TransformComponent)store.getComponent(existingBeaconReference, this.transformComponentype);
/* 177 */               assert existingBeaconTransformComponent != null;
/*     */               
/* 179 */               double existingY = existingBeaconTransformComponent.getPosition().getY();
/*     */               
/* 181 */               if (existingY <= maxY && existingY >= minY) {
/*     */                 
/* 183 */                 int existingBeaconIndex = existingBeaconComponent.getSpawnWrapper().getSpawnIndex();
/* 184 */                 if (existingBeaconIndex == possibleBeacon.getSpawnIndex()) {
/*     */                   continue label101;
/*     */                 }
/*     */               } 
/*     */             } 
/* 189 */             for (k = 0; k < pendingSpawns.size(); k++) {
/*     */               
/* 191 */               LegacySpawnBeaconEntity pending = pendingSpawns.get(k);
/* 192 */               Ref<EntityStore> pendingReference = pending.getReference();
/*     */               
/* 194 */               TransformComponent pendingTransformComponent = (TransformComponent)store.getComponent(pendingReference, TransformComponent.getComponentType());
/* 195 */               assert pendingTransformComponent != null;
/*     */               
/* 197 */               Vector3d pendingPosition = pendingTransformComponent.getPosition();
/* 198 */               double pendingY = pendingPosition.getY();
/* 199 */               if (pendingY <= maxY && pendingY >= minY) {
/*     */                 
/* 201 */                 double xDiff = position.x - pendingPosition.x;
/* 202 */                 double zDiff = position.z - pendingPosition.z;
/* 203 */                 double distSquared = xDiff * xDiff + zDiff * zDiff;
/* 204 */                 if (distSquared <= largestDistanceSquared) {
/*     */                   
/* 206 */                   int existingBeaconIndex = pending.getSpawnWrapper().getSpawnIndex();
/* 207 */                   if (existingBeaconIndex == possibleBeacon.getSpawnIndex()) {
/*     */                     continue label101;
/*     */                   }
/*     */                 } 
/*     */               } 
/*     */             } 
/* 213 */             if (spawnLightLevelMatches(world, xPos, yPos, zPos, sunlightFactor, possibleBeacon, (Object2ByteMap<LightType>)averageLightValues)) {
/*     */ 
/*     */               
/* 216 */               Pair<Ref<EntityStore>, LegacySpawnBeaconEntity> beaconEntityPair = LegacySpawnBeaconEntity.create(possibleBeacon, transformComponent.getPosition(), transformComponent.getRotation(), (ComponentAccessor)store);
/* 217 */               Ref<EntityStore> beaconRef = (Ref<EntityStore>)beaconEntityPair.first();
/*     */ 
/*     */ 
/*     */               
/* 221 */               if (beaconRef != null && beaconRef.isValid())
/*     */               
/*     */               { 
/*     */                 
/* 225 */                 store.ensureComponent(beaconRef, this.localSpawnBeaconComponentType);
/* 226 */                 SpawningPlugin.get().getLogger().at(Level.FINE).log("Placed spawn beacon of type %s at position %s for player %s", ((BeaconNPCSpawn)possibleBeacon
/* 227 */                     .getSpawn()).getId(), position, playerRefComponent.getUsername());
/* 228 */                 pendingSpawns.add((LegacySpawnBeaconEntity)beaconEntityPair.second()); } 
/*     */             } 
/*     */           } 
/* 231 */         }  existingBeacons.clear();
/* 232 */         averageLightValues.clear();
/* 233 */         spawnControllerComponent.setTimeToNextRunSeconds(5.0D);
/*     */       } 
/*     */     } 
/* 236 */     controllers.clear();
/* 237 */     pendingSpawns.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean spawnLightLevelMatches(@Nonnull World world, int x, int y, int z, double sunlightFactor, @Nonnull BeaconSpawnWrapper wrapper, @Nonnull Object2ByteMap<LightType> averageValues) {
/* 243 */     LightRangePredicate lightRangePredicate = wrapper.getLightRangePredicate();
/* 244 */     if (lightRangePredicate.isTestLightValue()) {
/* 245 */       byte lightValue = getCachedAverageLightValue(LightType.Light, world, x, y, z, sunlightFactor, (_x, _y, _z, _chunk, _sunlightFactor) -> LightRangePredicate.calculateLightValue(_chunk, _x, _y, _z, _sunlightFactor), averageValues);
/*     */       
/* 247 */       if (!lightRangePredicate.testLight(lightValue)) return false;
/*     */     
/*     */     } 
/* 250 */     if (lightRangePredicate.isTestSkyLightValue()) {
/* 251 */       byte lightValue = getCachedAverageLightValue(LightType.SkyLight, world, x, y, z, sunlightFactor, (_x, _y, _z, _chunk, _sunlightFactor) -> _chunk.getSkyLight(_x, _y, _z), averageValues);
/*     */       
/* 253 */       if (!lightRangePredicate.testSkyLight(lightValue)) return false;
/*     */     
/*     */     } 
/* 256 */     if (lightRangePredicate.isTestSunlightValue()) {
/* 257 */       byte lightValue = getCachedAverageLightValue(LightType.Sunlight, world, x, y, z, sunlightFactor, (_x, _y, _z, _chunk, _sunlightFactor) -> (byte)(int)(_chunk.getSkyLight(_x, _y, _z) * _sunlightFactor), averageValues);
/*     */       
/* 259 */       if (!lightRangePredicate.testSunlight(lightValue)) return false;
/*     */     
/*     */     } 
/* 262 */     if (lightRangePredicate.isTestRedLightValue()) {
/* 263 */       byte lightValue = getCachedAverageLightValue(LightType.RedLight, world, x, y, z, sunlightFactor, (_x, _y, _z, _chunk, _sunlightFactor) -> _chunk.getRedBlockLight(_x, _y, _z), averageValues);
/*     */       
/* 265 */       if (!lightRangePredicate.testRedLight(lightValue)) return false;
/*     */     
/*     */     } 
/* 268 */     if (lightRangePredicate.isTestGreenLightValue()) {
/* 269 */       byte lightValue = getCachedAverageLightValue(LightType.GreenLight, world, x, y, z, sunlightFactor, (_x, _y, _z, _chunk, _sunlightFactor) -> _chunk.getGreenBlockLight(_x, _y, _z), averageValues);
/*     */       
/* 271 */       if (!lightRangePredicate.testGreenLight(lightValue)) return false;
/*     */     
/*     */     } 
/* 274 */     if (lightRangePredicate.isTestBlueLightValue()) {
/* 275 */       byte lightValue = getCachedAverageLightValue(LightType.BlueLight, world, x, y, z, sunlightFactor, (_x, _y, _z, _chunk, _sunlightFactor) -> _chunk.getBlueBlockLight(_x, _y, _z), averageValues);
/*     */       
/* 277 */       return lightRangePredicate.testBlueLight(lightValue);
/*     */     } 
/*     */     
/* 280 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private static byte getCachedAverageLightValue(LightType lightType, @Nonnull World world, int x, int y, int z, double sunlightFactor, @Nonnull TriIntObjectDoubleToByteFunction<BlockChunk> valueCalculator, @Nonnull Object2ByteMap<LightType> averageValues) {
/* 285 */     byte cachedValue = averageValues.getByte(lightType);
/* 286 */     if (cachedValue < 0) {
/* 287 */       int counted = 0;
/* 288 */       int total = 0;
/*     */ 
/*     */       
/* 291 */       for (int xOffset = x - 4; xOffset < x + 4; xOffset++) {
/* 292 */         for (int zOffset = z - 4; zOffset < z + 4; zOffset++) {
/* 293 */           WorldChunk chunk = world.getChunkIfInMemory(ChunkUtil.indexChunkFromBlock(xOffset, zOffset));
/* 294 */           if (chunk != null) {
/*     */             
/* 296 */             BlockChunk blockChunk = chunk.getBlockChunk();
/* 297 */             for (int yOffset = y; yOffset < y + 4; yOffset++) {
/*     */               
/* 299 */               int blockId = chunk.getBlock(xOffset, yOffset, zOffset);
/* 300 */               if (blockId == 0 || ((BlockType)BlockType.getAssetMap().getAsset(blockId)).getMaterial() != BlockMaterial.Solid) {
/* 301 */                 counted++;
/* 302 */                 total += valueCalculator.apply(xOffset, yOffset, zOffset, blockChunk, sunlightFactor);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/* 308 */       cachedValue = (counted > 0) ? (byte)(int)(total / counted) : 0;
/* 309 */       averageValues.put(lightType, cachedValue);
/*     */     } 
/* 311 */     return cachedValue;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\local\LocalSpawnControllerSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */