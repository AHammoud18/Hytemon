/*     */ package com.hypixel.hytale.server.spawning.world.component;
/*     */ 
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Resource;
/*     */ import com.hypixel.hytale.component.ResourceType;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.math.iterator.SpiralIterator;
/*     */ import com.hypixel.hytale.server.core.asset.type.environment.config.Environment;
/*     */ import com.hypixel.hytale.server.core.modules.time.WorldTimeResource;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*     */ import com.hypixel.hytale.server.spawning.SpawningPlugin;
/*     */ import com.hypixel.hytale.server.spawning.world.WorldEnvironmentSpawnData;
/*     */ import com.hypixel.hytale.server.spawning.world.manager.EnvironmentSpawnParameters;
/*     */ import com.hypixel.hytale.server.spawning.world.manager.WorldSpawnWrapper;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldSpawnData
/*     */   implements Resource<EntityStore>
/*     */ {
/*     */   public static ResourceType<EntityStore, WorldSpawnData> getResourceType() {
/*  34 */     return SpawningPlugin.get().getWorldSpawnDataResourceType();
/*     */   }
/*     */   
/*  37 */   private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */   
/*  39 */   private final Int2ObjectMap<WorldEnvironmentSpawnData> worldEnvironmentSpawnData = (Int2ObjectMap<WorldEnvironmentSpawnData>)new Int2ObjectOpenHashMap();
/*     */   
/*  41 */   private final ArrayDeque<UnspawnableEntry> unspawnableProcessingQueue = new ArrayDeque<>();
/*     */   
/*     */   private int actualNPCs;
/*     */   
/*     */   private double expectedNPCs;
/*     */   
/*     */   private double expectedNPCsInEmptyEnvironments;
/*     */   
/*     */   private boolean unspawnable;
/*     */   
/*     */   private int chunkCount;
/*     */   
/*     */   private int segmentCount;
/*     */   private int activeSpawnJobs;
/*     */   private int trackedCountFromJobs;
/*     */   private int totalSpawnJobBudgetUsed;
/*     */   private int totalSpawnJobsCompleted;
/*  58 */   private final SpiralIterator spiralIterator = new SpiralIterator();
/*     */   
/*     */   public int getActualNPCs() {
/*  61 */     return this.actualNPCs;
/*     */   }
/*     */   
/*     */   public double getExpectedNPCs() {
/*  65 */     return this.expectedNPCs;
/*     */   }
/*     */   
/*     */   public double getExpectedNPCsInEmptyEnvironments() {
/*  69 */     return this.expectedNPCsInEmptyEnvironments;
/*     */   }
/*     */   
/*     */   public boolean isUnspawnable() {
/*  73 */     return this.unspawnable;
/*     */   }
/*     */   
/*     */   public void setUnspawnable(boolean unspawnable) {
/*  77 */     this.unspawnable = unspawnable;
/*     */   }
/*     */   
/*     */   public int getChunkCount() {
/*  81 */     return this.chunkCount;
/*     */   }
/*     */   
/*     */   public void adjustChunkCount(int amount) {
/*  85 */     this.chunkCount += amount;
/*     */   }
/*     */   
/*     */   public void adjustSegmentCount(int amount) {
/*  89 */     this.segmentCount += amount;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public SpiralIterator getSpiralIterator() {
/*  94 */     return this.spiralIterator;
/*     */   }
/*     */   
/*     */   public double averageSegmentCount() {
/*  98 */     return (this.chunkCount == 0) ? 0.0D : (this.segmentCount / this.chunkCount);
/*     */   }
/*     */   
/*     */   public int getActiveSpawnJobs() {
/* 102 */     return this.activeSpawnJobs;
/*     */   }
/*     */   
/*     */   public void adjustActiveSpawnJobs(int amount, int trackedCount) {
/* 106 */     this.activeSpawnJobs += amount;
/* 107 */     this.trackedCountFromJobs += trackedCount;
/*     */   }
/*     */   
/*     */   public int getTrackedCountFromJobs() {
/* 111 */     return this.trackedCountFromJobs;
/*     */   }
/*     */   
/*     */   public int getTotalSpawnJobBudgetUsed() {
/* 115 */     return this.totalSpawnJobBudgetUsed;
/*     */   }
/*     */   
/*     */   public int getTotalSpawnJobsCompleted() {
/* 119 */     return this.totalSpawnJobsCompleted;
/*     */   }
/*     */   
/*     */   public void addCompletedSpawnJob(int budgetUsed) {
/* 123 */     this.totalSpawnJobBudgetUsed += budgetUsed;
/* 124 */     this.totalSpawnJobsCompleted++;
/*     */   }
/*     */   
/*     */   public WorldEnvironmentSpawnData getWorldEnvironmentSpawnData(int environmentIndex) {
/* 128 */     return (WorldEnvironmentSpawnData)this.worldEnvironmentSpawnData.get(environmentIndex);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public WorldEnvironmentSpawnData getOrCreateWorldEnvironmentSpawnData(int environmentIndex, @Nonnull World world, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 135 */     WorldTimeResource worldTimeResource = (WorldTimeResource)componentAccessor.getResource(WorldTimeResource.getResourceType());
/*     */     
/* 137 */     return (WorldEnvironmentSpawnData)this.worldEnvironmentSpawnData.computeIfAbsent(environmentIndex, envIndex -> {
/*     */           WorldEnvironmentSpawnData newWorldEnvironmentSpawnData = new WorldEnvironmentSpawnData(envIndex);
/*     */           EnvironmentSpawnParameters envSpawnParameters = SpawningPlugin.get().getWorldEnvironmentSpawnParameters(envIndex);
/*     */           if (envSpawnParameters == null) {
/*     */             Environment env = (Environment)Environment.getAssetMap().getAsset(envIndex);
/*     */             LOGGER.at(Level.WARNING).log("No environment data found for '%s' [%s] but used in chunk", (env == null) ? null : env.getId(), envIndex);
/*     */             return newWorldEnvironmentSpawnData;
/*     */           } 
/*     */           for (WorldSpawnWrapper config : envSpawnParameters.getSpawnWrappers()) {
/*     */             newWorldEnvironmentSpawnData.updateNPCs(config, world);
/*     */           }
/*     */           int moonPhase = worldTimeResource.getMoonPhase();
/*     */           newWorldEnvironmentSpawnData.recalculateWeight(moonPhase);
/*     */           newWorldEnvironmentSpawnData.resetUnspawnable();
/*     */           return newWorldEnvironmentSpawnData;
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getWorldEnvironmentSpawnDataIndexes() {
/* 158 */     return this.worldEnvironmentSpawnData.keySet().toIntArray();
/*     */   }
/*     */   
/*     */   public void updateSpawnability() {
/* 162 */     this.unspawnable = true;
/* 163 */     for (ObjectIterator<WorldEnvironmentSpawnData> objectIterator = this.worldEnvironmentSpawnData.values().iterator(); objectIterator.hasNext(); ) { WorldEnvironmentSpawnData stats = objectIterator.next();
/* 164 */       if (!stats.isUnspawnable()) {
/* 165 */         this.unspawnable = false;
/*     */         break;
/*     */       }  }
/*     */   
/*     */   }
/*     */   
/*     */   public void forEachEnvironmentSpawnData(Consumer<WorldEnvironmentSpawnData> consumer) {
/* 172 */     this.worldEnvironmentSpawnData.values().forEach(consumer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean trackNPC(int environmentIndex, int roleIndex, int npcCount, @Nonnull World world, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 180 */     if (roleIndex < 0 || environmentIndex < 0) return false;
/*     */     
/* 182 */     WorldEnvironmentSpawnData stats = getOrCreateWorldEnvironmentSpawnData(environmentIndex, world, componentAccessor);
/* 183 */     stats.trackSpawn(roleIndex, npcCount);
/* 184 */     this.actualNPCs += npcCount;
/* 185 */     return true;
/*     */   }
/*     */   
/*     */   public boolean untrackNPC(int environmentIndex, int roleIndex, int npcCount) {
/* 189 */     if (environmentIndex < 0 || roleIndex < 0) return false;
/*     */     
/* 191 */     WorldEnvironmentSpawnData stats = (WorldEnvironmentSpawnData)this.worldEnvironmentSpawnData.get(environmentIndex);
/* 192 */     if (stats == null) {
/* 193 */       LOGGER.at(Level.WARNING).log("Removing NPC %s from environment %s which is not contained in the world environment spawn data", 
/* 194 */           NPCPlugin.get().getName(roleIndex), ((Environment)Environment.getAssetMap().getAsset(environmentIndex)).getId());
/* 195 */       return false;
/*     */     } 
/*     */     
/* 198 */     stats.trackDespawn(roleIndex, npcCount);
/* 199 */     this.actualNPCs -= npcCount;
/* 200 */     return true;
/*     */   }
/*     */   
/*     */   public void recalculateWorldCount() {
/* 204 */     this.actualNPCs = 0;
/* 205 */     this.expectedNPCs = 0.0D;
/* 206 */     this.expectedNPCsInEmptyEnvironments = 0.0D;
/* 207 */     for (ObjectIterator<WorldEnvironmentSpawnData> objectIterator = this.worldEnvironmentSpawnData.values().iterator(); objectIterator.hasNext(); ) { WorldEnvironmentSpawnData stats = objectIterator.next();
/* 208 */       this.actualNPCs += stats.getActualNPCs();
/* 209 */       if (stats.hasNPCs()) {
/* 210 */         this.expectedNPCs += stats.getExpectedNPCs(); continue;
/*     */       } 
/* 212 */       this.expectedNPCsInEmptyEnvironments += stats.getExpectedNPCs(); }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public void queueUnspawnableChunk(int environmentIndex, long chunkIndex) {
/* 218 */     this.unspawnableProcessingQueue.add(new UnspawnableEntry(environmentIndex, chunkIndex));
/*     */   }
/*     */   
/*     */   public boolean hasUnprocessedUnspawnableChunks() {
/* 222 */     return !this.unspawnableProcessingQueue.isEmpty();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public UnspawnableEntry nextUnspawnableChunk() {
/* 227 */     if (this.unspawnableProcessingQueue.isEmpty()) return null; 
/* 228 */     return this.unspawnableProcessingQueue.poll();
/*     */   }
/*     */ 
/*     */   
/*     */   public Resource<EntityStore> clone() {
/* 233 */     throw new UnsupportedOperationException("Not implemented!");
/*     */   }
/*     */   
/*     */   public static class UnspawnableEntry {
/*     */     private final int environmentIndex;
/*     */     private final long chunkIndex;
/*     */     
/*     */     public UnspawnableEntry(int environmentIndex, long chunkIndex) {
/* 241 */       this.environmentIndex = environmentIndex;
/* 242 */       this.chunkIndex = chunkIndex;
/*     */     }
/*     */     
/*     */     public int getEnvironmentIndex() {
/* 246 */       return this.environmentIndex;
/*     */     }
/*     */     
/*     */     public long getChunkIndex() {
/* 250 */       return this.chunkIndex;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\world\component\WorldSpawnData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */