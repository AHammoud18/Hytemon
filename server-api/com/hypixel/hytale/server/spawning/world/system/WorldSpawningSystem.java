/*     */ package com.hypixel.hytale.server.spawning.world.system;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.ResourceType;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.server.core.asset.type.environment.config.Environment;
/*     */ import com.hypixel.hytale.server.core.asset.type.gameplay.GameplayConfig;
/*     */ import com.hypixel.hytale.server.core.modules.time.WorldTimeResource;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.flock.config.FlockAsset;
/*     */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*     */ import com.hypixel.hytale.server.spawning.SpawningPlugin;
/*     */ import com.hypixel.hytale.server.spawning.world.ChunkEnvironmentSpawnData;
/*     */ import com.hypixel.hytale.server.spawning.world.WorldEnvironmentSpawnData;
/*     */ import com.hypixel.hytale.server.spawning.world.WorldNPCSpawnStat;
/*     */ import com.hypixel.hytale.server.spawning.world.component.ChunkSpawnData;
/*     */ import com.hypixel.hytale.server.spawning.world.component.ChunkSpawnedNPCData;
/*     */ import com.hypixel.hytale.server.spawning.world.component.SpawnJobData;
/*     */ import com.hypixel.hytale.server.spawning.world.component.WorldSpawnData;
/*     */ import com.hypixel.hytale.server.spawning.wrappers.SpawnWrapper;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ThreadLocalRandom;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class WorldSpawningSystem extends TickingSystem<ChunkStore> {
/*  34 */   private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*  35 */   private static final long SPAWN_COOLDOWN_NANOS = Duration.ofSeconds(1L).toNanos();
/*     */   
/*     */   private final ResourceType<EntityStore, WorldSpawnData> worldSpawnDataResourceType;
/*     */   
/*     */   private final ComponentType<ChunkStore, ChunkSpawnData> chunkSpawnDataComponentType;
/*     */   
/*     */   private final ComponentType<ChunkStore, ChunkSpawnedNPCData> chunkSpawnedNPCDataComponentType;
/*     */   
/*     */   private final ComponentType<ChunkStore, SpawnJobData> spawnJobDataComponentType;
/*     */   private final ComponentType<ChunkStore, WorldChunk> worldChunkComponentType;
/*     */   
/*     */   public WorldSpawningSystem(@Nonnull ResourceType<EntityStore, WorldSpawnData> worldSpawnDataResourceType, @Nonnull ComponentType<ChunkStore, ChunkSpawnData> chunkSpawnDataComponentType, @Nonnull ComponentType<ChunkStore, ChunkSpawnedNPCData> chunkSpawnedNPCDataComponentType, @Nonnull ComponentType<ChunkStore, SpawnJobData> spawnJobDataComponentType) {
/*  47 */     this.worldSpawnDataResourceType = worldSpawnDataResourceType;
/*  48 */     this.chunkSpawnDataComponentType = chunkSpawnDataComponentType;
/*  49 */     this.chunkSpawnedNPCDataComponentType = chunkSpawnedNPCDataComponentType;
/*  50 */     this.spawnJobDataComponentType = spawnJobDataComponentType;
/*  51 */     this.worldChunkComponentType = WorldChunk.getComponentType();
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(float dt, int systemIndex, @Nonnull Store<ChunkStore> store) {
/*  56 */     World world = ((ChunkStore)store.getExternalData()).getWorld();
/*  57 */     if (!world.getWorldConfig().isSpawningNPC() || world.getPlayerCount() == 0) {
/*     */       return;
/*     */     }
/*     */     
/*  61 */     GameplayConfig gameplayConfig = world.getGameplayConfig();
/*  62 */     Store<EntityStore> entityStore = world.getEntityStore().getStore();
/*  63 */     WorldSpawnData worldSpawnDataResource = (WorldSpawnData)entityStore.getResource(this.worldSpawnDataResourceType);
/*  64 */     if (worldSpawnDataResource.isUnspawnable() || world.getChunkStore().getStore().getEntityCount() == 0 || (gameplayConfig
/*  65 */       .getMaxEnvironmentalNPCSpawns() > 0 && worldSpawnDataResource.getActualNPCs() >= gameplayConfig.getMaxEnvironmentalNPCSpawns()) || worldSpawnDataResource
/*  66 */       .getActualNPCs() > worldSpawnDataResource.getExpectedNPCs()) {
/*     */       return;
/*     */     }
/*     */     
/*  70 */     WorldTimeResource worldTimeResource = (WorldTimeResource)entityStore.getResource(WorldTimeResource.getResourceType());
/*     */     
/*  72 */     if (worldSpawnDataResource.hasUnprocessedUnspawnableChunks()) {
/*  73 */       while (worldSpawnDataResource.hasUnprocessedUnspawnableChunks()) {
/*  74 */         WorldSpawnData.UnspawnableEntry entry = worldSpawnDataResource.nextUnspawnableChunk();
/*  75 */         Ref<ChunkStore> chunkRef = world.getChunkStore().getChunkReference(entry.getChunkIndex());
/*  76 */         if (chunkRef == null)
/*     */           continue; 
/*  78 */         int environmentIndex = entry.getEnvironmentIndex();
/*  79 */         ChunkSpawnData chunkSpawnDataComponent = (ChunkSpawnData)store.getComponent(chunkRef, this.chunkSpawnDataComponentType);
/*  80 */         assert chunkSpawnDataComponent != null;
/*     */         
/*  82 */         ChunkEnvironmentSpawnData environmentSpawnData = chunkSpawnDataComponent.getEnvironmentSpawnData(environmentIndex);
/*  83 */         int segmentCount = -environmentSpawnData.getSegmentCount();
/*  84 */         worldSpawnDataResource.adjustSegmentCount(segmentCount);
/*     */         
/*  86 */         WorldEnvironmentSpawnData worldEnvironmentSpawnData = worldSpawnDataResource.getWorldEnvironmentSpawnData(environmentIndex);
/*  87 */         double expectedNPCs = worldEnvironmentSpawnData.getExpectedNPCs();
/*  88 */         worldEnvironmentSpawnData.adjustSegmentCount(segmentCount);
/*  89 */         worldEnvironmentSpawnData.updateExpectedNPCs(worldTimeResource.getMoonPhase());
/*     */         
/*  91 */         environmentSpawnData.markProcessedAsUnspawnable();
/*     */         
/*  93 */         HytaleLogger.Api context = LOGGER.at(Level.FINEST);
/*  94 */         if (context.isEnabled()) {
/*  95 */           Environment environmentAsset = (Environment)Environment.getAssetMap().getAsset(environmentIndex);
/*     */           
/*  97 */           if (environmentAsset != null) {
/*  98 */             String environment = environmentAsset.getId();
/*  99 */             context.log("Reducing expected NPC count for %s due to un-spawnable chunk. Was %s, now %s", environment, Double.valueOf(expectedNPCs), 
/* 100 */                 Double.valueOf(worldEnvironmentSpawnData.getExpectedNPCs()));
/*     */           } 
/*     */         } 
/*     */       } 
/* 104 */       worldSpawnDataResource.recalculateWorldCount();
/*     */     } 
/*     */     
/* 107 */     int activeJobs = worldSpawnDataResource.getActiveSpawnJobs();
/* 108 */     int maxActiveJobs = SpawningPlugin.get().getMaxActiveJobs();
/* 109 */     while (activeJobs < maxActiveJobs && worldSpawnDataResource.getActualNPCs() < MathUtil.floor(worldSpawnDataResource.getExpectedNPCs()) && 
/* 110 */       createRandomSpawnJob(worldSpawnDataResource, store, (ComponentAccessor<EntityStore>)entityStore))
/*     */     {
/*     */       
/* 113 */       activeJobs = worldSpawnDataResource.getActiveSpawnJobs(); } 
/*     */   }
/*     */   
/*     */   private boolean createRandomSpawnJob(@Nonnull WorldSpawnData worldData, @Nonnull Store<ChunkStore> chunkStore, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*     */     WorldNPCSpawnStat npcSpawnStat;
/*     */     WorldEnvironmentSpawnData worldEnvironmentSpawnData;
/* 119 */     int environmentIndex, environmentDataKeySet[] = worldData.getWorldEnvironmentSpawnDataIndexes();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     do {
/* 125 */       environmentIndex = getAndConsumeNextEnvironmentIndex(worldData, environmentDataKeySet);
/*     */       
/* 127 */       if (environmentIndex == Integer.MIN_VALUE)
/*     */       {
/* 129 */         return false;
/*     */       }
/*     */       
/* 132 */       worldEnvironmentSpawnData = worldData.getWorldEnvironmentSpawnData(environmentIndex);
/* 133 */       npcSpawnStat = worldEnvironmentSpawnData.pickRandomSpawnNPCStat(componentAccessor);
/*     */     }
/* 135 */     while (npcSpawnStat == null);
/*     */     
/* 137 */     int availableSlots = npcSpawnStat.getAvailableSlots();
/* 138 */     if (availableSlots == 0) return false;
/*     */     
/* 140 */     Ref<ChunkStore> chunkRef = pickRandomChunk(worldEnvironmentSpawnData, npcSpawnStat, worldData, chunkStore);
/* 141 */     if (chunkRef == null) {
/* 142 */       return false;
/*     */     }
/*     */     
/* 145 */     Environment environment = (Environment)Environment.getAssetMap().getAsset(environmentIndex);
/* 146 */     HytaleLogger.Api context = LOGGER.at(Level.FINER);
/* 147 */     if (context.isEnabled()) {
/* 148 */       WorldChunk worldChunkComponent = (WorldChunk)chunkStore.getComponent(chunkRef, this.worldChunkComponentType);
/* 149 */       assert worldChunkComponent != null;
/*     */       
/* 151 */       String roleName = NPCPlugin.get().getName(npcSpawnStat.getRoleIndex());
/* 152 */       context.log("Trying SpawnJob env=%s role=%s chunk=[%s/%s] env(exp/act)=%s/%s npc(exp/act)=%s/%s", environment
/* 153 */           .getId(), roleName, Integer.valueOf(worldChunkComponent.getX()), Integer.valueOf(worldChunkComponent.getZ()), 
/* 154 */           Integer.valueOf((int)worldEnvironmentSpawnData.getExpectedNPCs()), Integer.valueOf(worldEnvironmentSpawnData.getActualNPCs()), 
/* 155 */           Integer.valueOf((int)npcSpawnStat.getExpected()), Integer.valueOf(npcSpawnStat.getActual()));
/*     */     } 
/*     */ 
/*     */     
/* 159 */     SpawnJobData spawnJobDataComponent = (SpawnJobData)chunkStore.addComponent(chunkRef, this.spawnJobDataComponentType);
/* 160 */     FlockAsset flockDefinition = npcSpawnStat.getSpawnParams().getFlockDefinition();
/*     */     
/* 162 */     int flockSize = (flockDefinition != null) ? flockDefinition.pickFlockSize() : 1;
/* 163 */     int roleIndex = npcSpawnStat.getRoleIndex();
/*     */     
/* 165 */     if (flockSize > availableSlots) flockSize = availableSlots; 
/* 166 */     spawnJobDataComponent.init(roleIndex, environment, environmentIndex, (SpawnWrapper)npcSpawnStat.getSpawnWrapper(), flockDefinition, flockSize);
/* 167 */     if (worldEnvironmentSpawnData.isFullyPopulated()) spawnJobDataComponent.setIgnoreFullyPopulated(true);
/*     */ 
/*     */     
/* 170 */     ChunkSpawnData chunkSpawnDataComponent = (ChunkSpawnData)chunkStore.getComponent(chunkRef, this.chunkSpawnDataComponentType);
/* 171 */     assert chunkSpawnDataComponent != null;
/* 172 */     chunkSpawnDataComponent.getEnvironmentSpawnData(environmentIndex).getRandomChunkColumnIterator()
/* 173 */       .saveIteratorPosition();
/*     */ 
/*     */     
/* 176 */     World world = ((ChunkStore)chunkStore.getExternalData()).getWorld();
/* 177 */     WorldSpawnData worldSpawnData = (WorldSpawnData)world.getEntityStore().getStore().getResource(WorldSpawnData.getResourceType());
/* 178 */     worldSpawnData.trackNPC(environmentIndex, roleIndex, flockSize, world, componentAccessor);
/*     */     
/* 180 */     HytaleLogger.Api finestContext = LOGGER.at(Level.FINEST);
/* 181 */     if (finestContext.isEnabled()) {
/* 182 */       WorldChunk worldChunkComponent = (WorldChunk)chunkStore.getComponent(chunkRef, this.worldChunkComponentType);
/* 183 */       assert worldChunkComponent != null;
/* 184 */       finestContext.log("Start Spawnjob id=%s env=%s role=%s chunk=[%s/%s]", Integer.valueOf(spawnJobDataComponent.getJobId()), environment.getId(), 
/* 185 */           NPCPlugin.get().getName(roleIndex), Integer.valueOf(worldChunkComponent.getX()), Integer.valueOf(worldChunkComponent.getZ()));
/*     */     } 
/* 187 */     worldData.adjustActiveSpawnJobs(1, flockSize);
/* 188 */     return true;
/*     */   }
/*     */   
/*     */   private static int getAndConsumeNextEnvironmentIndex(@Nonnull WorldSpawnData worldSpawnData, @Nonnull int[] environmentKeySet) {
/* 192 */     double weightSum = 0.0D;
/*     */     
/* 194 */     for (int keyReference : environmentKeySet) {
/* 195 */       if (keyReference != Integer.MIN_VALUE) {
/* 196 */         weightSum += worldSpawnData.getWorldEnvironmentSpawnData(keyReference).spawnWeight();
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 201 */     if (weightSum == 0.0D) {
/* 202 */       return Integer.MIN_VALUE;
/*     */     }
/*     */     
/* 205 */     weightSum *= ThreadLocalRandom.current().nextDouble();
/*     */     
/* 207 */     for (int i = 0; i < environmentKeySet.length; i++) {
/* 208 */       int keyReference = environmentKeySet[i];
/* 209 */       if (keyReference != Integer.MIN_VALUE) {
/* 210 */         weightSum -= worldSpawnData.getWorldEnvironmentSpawnData(keyReference).spawnWeight();
/*     */         
/* 212 */         if (weightSum <= 0.0D) {
/*     */           
/* 214 */           environmentKeySet[i] = Integer.MIN_VALUE;
/* 215 */           return keyReference;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 220 */     return Integer.MIN_VALUE;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private Ref<ChunkStore> pickRandomChunk(@Nonnull WorldEnvironmentSpawnData spawnData, @Nonnull WorldNPCSpawnStat stat, @Nonnull WorldSpawnData worldSpawnData, @Nonnull Store<ChunkStore> store) {
/* 226 */     int roleIndex = stat.getRoleIndex();
/* 227 */     boolean wasFullyPopulated = spawnData.isFullyPopulated();
/* 228 */     Set<Ref<ChunkStore>> chunkRefSet = spawnData.getChunkRefSet();
/* 229 */     int environmentIndex = spawnData.getEnvironmentIndex();
/*     */     
/* 231 */     double weight = 0.0D;
/* 232 */     boolean spawnable = false;
/* 233 */     boolean fullyPopulated = true;
/* 234 */     if (wasFullyPopulated) {
/* 235 */       for (Ref<ChunkStore> chunkRef : chunkRefSet) {
/* 236 */         ChunkSpawnData chunkSpawnDataComponent = (ChunkSpawnData)store.getComponent(chunkRef, this.chunkSpawnDataComponentType);
/* 237 */         assert chunkSpawnDataComponent != null;
/*     */         
/* 239 */         ChunkSpawnedNPCData chunkSpawnedNPCDataComponent = (ChunkSpawnedNPCData)store.getComponent(chunkRef, this.chunkSpawnedNPCDataComponentType);
/* 240 */         assert chunkSpawnedNPCDataComponent != null;
/*     */         
/* 242 */         ChunkEnvironmentSpawnData chunkEnvironmentSpawnData = chunkSpawnDataComponent.getEnvironmentSpawnData(environmentIndex);
/*     */         
/* 244 */         fullyPopulated = (fullyPopulated && chunkEnvironmentSpawnData.isFullyPopulated(chunkSpawnedNPCDataComponent.getEnvironmentSpawnCount(environmentIndex)));
/* 245 */         if (chunkEnvironmentSpawnData.isRoleSpawnable(roleIndex)) {
/* 246 */           spawnable = true;
/* 247 */           weight += (store.getComponent(chunkRef, this.spawnJobDataComponentType) == null && !getAndUpdateSpawnCooldown(chunkSpawnDataComponent)) ? 1.0D : 0.0D;
/*     */         } 
/*     */       } 
/*     */     } else {
/* 251 */       for (Ref<ChunkStore> chunkRef : chunkRefSet) {
/* 252 */         ChunkSpawnData chunkSpawnDataComponent = (ChunkSpawnData)store.getComponent(chunkRef, this.chunkSpawnDataComponentType);
/* 253 */         assert chunkSpawnDataComponent != null;
/*     */         
/* 255 */         ChunkSpawnedNPCData chunkSpawnedNPCDataComponent = (ChunkSpawnedNPCData)store.getComponent(chunkRef, this.chunkSpawnedNPCDataComponentType);
/* 256 */         assert chunkSpawnedNPCDataComponent != null;
/*     */         
/* 258 */         ChunkEnvironmentSpawnData chunkEnvironmentSpawnData = chunkSpawnDataComponent.getEnvironmentSpawnData(environmentIndex);
/* 259 */         double spawnCount = chunkSpawnedNPCDataComponent.getEnvironmentSpawnCount(environmentIndex);
/*     */         
/* 261 */         fullyPopulated = (fullyPopulated && chunkEnvironmentSpawnData.isFullyPopulated(spawnCount));
/* 262 */         if (chunkEnvironmentSpawnData.isRoleSpawnable(roleIndex)) {
/* 263 */           spawnable = true;
/* 264 */           weight += (store.getComponent(chunkRef, this.spawnJobDataComponentType) == null && !getAndUpdateSpawnCooldown(chunkSpawnDataComponent)) ? chunkEnvironmentSpawnData.getWeight(spawnCount) : 0.0D;
/*     */         } 
/*     */       } 
/*     */     } 
/* 268 */     spawnData.setFullyPopulated(fullyPopulated);
/*     */     
/* 270 */     if (!spawnable) {
/*     */       
/* 272 */       stat.setUnspawnable(true);
/* 273 */       boolean unspawnable = true;
/* 274 */       for (ObjectIterator<WorldNPCSpawnStat> objectIterator = spawnData.getNpcStatMap().values().iterator(); objectIterator.hasNext(); ) { WorldNPCSpawnStat npcStat = objectIterator.next();
/* 275 */         if (!npcStat.isUnspawnable()) {
/* 276 */           unspawnable = false;
/*     */           break;
/*     */         }  }
/*     */       
/* 280 */       spawnData.setUnspawnable(unspawnable);
/* 281 */       worldSpawnData.updateSpawnability();
/* 282 */       return null;
/*     */     } 
/*     */     
/* 285 */     return (Ref<ChunkStore>)RandomExtra.randomWeightedElement(chunkRefSet, (chunkRef, index) -> {
/*     */           ChunkSpawnData chunkSpawnDataComponent = (ChunkSpawnData)store.getComponent(chunkRef, this.chunkSpawnDataComponentType);
/*     */           
/*     */           assert chunkSpawnDataComponent != null;
/*     */           
/*     */           ChunkEnvironmentSpawnData chunkEnvironmentSpawnData = chunkSpawnDataComponent.getEnvironmentSpawnData(environmentIndex);
/*     */           
/*     */           return chunkEnvironmentSpawnData.isRoleSpawnable(index.intValue());
/* 293 */         }wasFullyPopulated ? ((chunkRef, index) -> {
/*     */           ChunkSpawnData spawnChunkDataComponent = (ChunkSpawnData)store.getComponent(chunkRef, this.chunkSpawnDataComponentType);
/*     */           
/*     */           assert spawnChunkDataComponent != null;
/*     */           
/* 298 */           return (store.getComponent(chunkRef, this.spawnJobDataComponentType) == null && !spawnChunkDataComponent.isOnSpawnCooldown()) ? 1.0D : 0.0D;
/*     */         }) : ((chunkRef, index) -> {
/*     */           ChunkSpawnData chunkSpawnDataComponent = (ChunkSpawnData)store.getComponent(chunkRef, this.chunkSpawnDataComponentType);
/*     */           
/*     */           assert chunkSpawnDataComponent != null;
/*     */           
/*     */           ChunkSpawnedNPCData chunkSpawnedNPCDataComponent = (ChunkSpawnedNPCData)store.getComponent(chunkRef, this.chunkSpawnedNPCDataComponentType);
/*     */           
/*     */           assert chunkSpawnedNPCDataComponent != null;
/*     */           
/*     */           ChunkEnvironmentSpawnData chunkEnvironmentSpawnData = chunkSpawnDataComponent.getEnvironmentSpawnData(environmentIndex);
/* 309 */           return (store.getComponent(chunkRef, this.spawnJobDataComponentType) == null && !chunkSpawnDataComponent.isOnSpawnCooldown()) ? chunkEnvironmentSpawnData.getWeight(chunkSpawnedNPCDataComponent.getEnvironmentSpawnCount(environmentIndex)) : 0.0D;
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 314 */         }), weight, Integer.valueOf(roleIndex));
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean getAndUpdateSpawnCooldown(@Nonnull ChunkSpawnData chunkSpawnData) {
/* 319 */     boolean onCooldown = chunkSpawnData.isOnSpawnCooldown();
/* 320 */     if (onCooldown && System.nanoTime() - chunkSpawnData.getLastSpawn() > SPAWN_COOLDOWN_NANOS) {
/* 321 */       chunkSpawnData.setLastSpawn(0L);
/* 322 */       onCooldown = false;
/*     */     } 
/* 324 */     return onCooldown;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\world\system\WorldSpawningSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */