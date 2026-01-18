/*     */ package com.hypixel.hytale.server.spawning.world.system;
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.math.iterator.SpiralIterator;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.server.core.asset.type.environment.config.Environment;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import com.hypixel.hytale.server.spawning.world.ChunkEnvironmentSpawnData;
/*     */ import com.hypixel.hytale.server.spawning.world.component.ChunkSpawnData;
/*     */ import com.hypixel.hytale.server.spawning.world.component.ChunkSpawnedNPCData;
/*     */ import com.hypixel.hytale.server.spawning.world.component.WorldSpawnData;
/*     */ import java.util.Iterator;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class WorldSpawnTrackingSystem extends RefSystem<EntityStore> {
/*  28 */   private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */   
/*     */   private static final int COUNT_SPREAD_RADIUS = 3;
/*     */   
/*     */   @Nullable
/*     */   private final ComponentType<EntityStore, NPCEntity> npcComponentType;
/*     */   
/*     */   private final ComponentType<EntityStore, TransformComponent> transformComponentType;
/*     */   
/*     */   private final ResourceType<EntityStore, WorldSpawnData> worldSpawnDataResourceType;
/*     */   private final ComponentType<ChunkStore, ChunkSpawnData> chunkSpawnDataComponentType;
/*     */   private final ComponentType<ChunkStore, ChunkSpawnedNPCData> chunkSpawnedNPCDataComponentType;
/*     */   @Nonnull
/*     */   private final Query<EntityStore> query;
/*     */   
/*     */   public WorldSpawnTrackingSystem(@Nonnull ResourceType<EntityStore, WorldSpawnData> worldSpawnDataResourceType, @Nonnull ComponentType<ChunkStore, ChunkSpawnData> chunkSpawnDataComponentType, @Nonnull ComponentType<ChunkStore, ChunkSpawnedNPCData> chunkSpawnedNPCDataComponentType) {
/*  44 */     this.npcComponentType = NPCEntity.getComponentType();
/*  45 */     this.transformComponentType = TransformComponent.getComponentType();
/*  46 */     this.worldSpawnDataResourceType = worldSpawnDataResourceType;
/*  47 */     this.chunkSpawnDataComponentType = chunkSpawnDataComponentType;
/*  48 */     this.chunkSpawnedNPCDataComponentType = chunkSpawnedNPCDataComponentType;
/*  49 */     this.query = (Query<EntityStore>)Query.and(new Query[] { (Query)this.npcComponentType, (Query)this.transformComponentType });
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Query<EntityStore> getQuery() {
/*  55 */     return this.query; } public void onEntityAdded(@Nonnull Ref<EntityStore> ref, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) { int environmentIndex; ChunkStore chunkStore; Store<ChunkStore> chunkComponentStore; Vector3d position; int originX, originZ;
/*     */     Ref<ChunkStore> chunkRef;
/*     */     double count;
/*     */     SpiralIterator spiralIterator;
/*     */     int checkedCount, unloadedCount;
/*  60 */     NPCEntity npcComponent = (NPCEntity)store.getComponent(ref, this.npcComponentType);
/*  61 */     assert npcComponent != null;
/*     */     
/*  63 */     boolean isTracked = npcComponent.updateSpawnTrackingState(true);
/*  64 */     if (isTracked)
/*     */       return; 
/*  66 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*  67 */     WorldSpawnData worldSpawnData = (WorldSpawnData)store.getResource(this.worldSpawnDataResourceType);
/*  68 */     switch (reason) {
/*     */       case REMOVE:
/*  70 */         environmentIndex = npcComponent.getEnvironment();
/*  71 */         if (!trackNPC(environmentIndex, npcComponent.getSpawnRoleIndex(), worldSpawnData, world, (ComponentAccessor<EntityStore>)commandBuffer))
/*     */           return; 
/*  73 */         chunkStore = world.getChunkStore();
/*  74 */         chunkComponentStore = chunkStore.getStore();
/*     */ 
/*     */         
/*  77 */         position = ((TransformComponent)store.getComponent(ref, this.transformComponentType)).getPosition();
/*     */         
/*  79 */         originX = ChunkUtil.chunkCoordinate(position.getX());
/*  80 */         originZ = ChunkUtil.chunkCoordinate(position.getZ());
/*     */         
/*  82 */         chunkRef = chunkStore.getChunkReference(ChunkUtil.indexChunk(originX, originZ));
/*  83 */         count = trackNewNPC(chunkRef, environmentIndex, 1.0D, this.chunkSpawnDataComponentType, this.chunkSpawnedNPCDataComponentType, chunkComponentStore);
/*  84 */         if (count <= 0.0D)
/*     */           return; 
/*  86 */         spiralIterator = worldSpawnData.getSpiralIterator();
/*     */         
/*  88 */         spiralIterator.init(originX, originZ, 3);
/*  89 */         if (!spiralIterator.hasNext()) {
/*     */           return;
/*     */         }
/*  92 */         spiralIterator.next();
/*     */         
/*  94 */         checkedCount = 0;
/*  95 */         unloadedCount = 0;
/*  96 */         while (spiralIterator.hasNext() && count > 0.0D) {
/*  97 */           checkedCount++;
/*  98 */           long chunkIndex = spiralIterator.next();
/*  99 */           chunkRef = chunkStore.getChunkReference(chunkIndex);
/* 100 */           if (chunkRef == null) {
/* 101 */             unloadedCount++;
/*     */             
/*     */             continue;
/*     */           } 
/* 105 */           count = trackNewNPC(chunkRef, environmentIndex, count, this.chunkSpawnDataComponentType, this.chunkSpawnedNPCDataComponentType, chunkComponentStore);
/*     */         } 
/*     */         
/* 108 */         if (count > 0.0D) {
/* 109 */           HytaleLogger.Api context = LOGGER.at(Level.FINE);
/* 110 */           if (context.isEnabled()) {
/* 111 */             context.log("Failed to spread %s of an NPC spawn to neighbouring chunks. Checked %s chunks, %s not in memory. Centered on chunk (%s, %s), spreading to other chunks with matching environment", 
/* 112 */                 Double.valueOf(count), Integer.valueOf(checkedCount), 
/* 113 */                 Integer.valueOf(unloadedCount), Integer.valueOf(originX), Integer.valueOf(originZ));
/*     */           }
/*     */           
/* 116 */           Set<Ref<ChunkStore>> chunkOptions = worldSpawnData.getWorldEnvironmentSpawnData(environmentIndex).getChunkRefSet();
/* 117 */           Iterator<Ref<ChunkStore>> iterator = chunkOptions.iterator();
/* 118 */           while (iterator.hasNext() && count > 0.0D) {
/* 119 */             count = trackNewNPC(iterator.next(), environmentIndex, count, this.chunkSpawnDataComponentType, this.chunkSpawnedNPCDataComponentType, chunkComponentStore);
/*     */           }
/*     */           
/* 122 */           if (count > 0.0D && 
/* 123 */             context.isEnabled()) {
/* 124 */             WorldEnvironmentSpawnData worldEnvironmentSpawnData = worldSpawnData.getWorldEnvironmentSpawnData(environmentIndex);
/* 125 */             WorldNPCSpawnStat npcSpawnStat = (WorldNPCSpawnStat)worldEnvironmentSpawnData.getNpcStatMap().get(npcComponent.getRoleIndex());
/* 126 */             context.log("Failed to spread %s of an NPC spawn across random chunks with matching environments (%s). NPC Type: %s. World environment exp: %s act: %s. Stat exp: %s act: %s", 
/* 127 */                 Double.valueOf(count), ((Environment)Environment.getAssetMap().getAsset(environmentIndex)).getId(), 
/* 128 */                 NPCPlugin.get().getName(npcComponent.getRoleIndex()), Double.valueOf(worldEnvironmentSpawnData.getExpectedNPCs()), 
/* 129 */                 Integer.valueOf(worldEnvironmentSpawnData.getActualNPCs()), Double.valueOf(npcSpawnStat.getExpected()), Integer.valueOf(npcSpawnStat.getActual()));
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 134 */         spiralIterator.reset(); break;
/*     */       case UNLOAD:
/* 136 */         trackNPC(npcComponent.getEnvironment(), npcComponent.getSpawnRoleIndex(), worldSpawnData, world, (ComponentAccessor<EntityStore>)commandBuffer); break;
/*     */     }  } public void onEntityRemove(@Nonnull Ref<EntityStore> ref, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) { int environmentIndex; World world; ChunkStore chunkStore; Store<ChunkStore> chunkComponentStore; TransformComponent transformComponent; Vector3d position;
/*     */     int originX, originZ;
/*     */     Ref<ChunkStore> chunkRef;
/*     */     double count;
/*     */     SpiralIterator spiralIterator;
/* 142 */     NPCEntity npcComponent = (NPCEntity)store.getComponent(ref, this.npcComponentType);
/* 143 */     assert npcComponent != null;
/*     */     
/* 145 */     boolean isTracked = npcComponent.updateSpawnTrackingState(false);
/* 146 */     if (!isTracked)
/*     */       return; 
/* 148 */     WorldSpawnData worldSpawnData = (WorldSpawnData)store.getResource(this.worldSpawnDataResourceType);
/* 149 */     switch (reason) {
/*     */       case REMOVE:
/* 151 */         environmentIndex = npcComponent.getEnvironment();
/* 152 */         if (!untrackNPC(environmentIndex, npcComponent.getSpawnRoleIndex(), worldSpawnData))
/*     */           return; 
/* 154 */         world = ((EntityStore)store.getExternalData()).getWorld();
/* 155 */         chunkStore = world.getChunkStore();
/* 156 */         chunkComponentStore = chunkStore.getStore();
/*     */ 
/*     */ 
/*     */         
/* 160 */         transformComponent = (TransformComponent)store.getComponent(ref, this.transformComponentType);
/* 161 */         assert transformComponent != null;
/*     */         
/* 163 */         position = transformComponent.getPosition();
/*     */         
/* 165 */         originX = ChunkUtil.chunkCoordinate(position.getX());
/* 166 */         originZ = ChunkUtil.chunkCoordinate(position.getZ());
/*     */         
/* 168 */         chunkRef = chunkStore.getChunkReference(ChunkUtil.indexChunk(originX, originZ));
/* 169 */         count = untrackRemovedNPC(chunkRef, environmentIndex, 1.0D, this.chunkSpawnDataComponentType, this.chunkSpawnedNPCDataComponentType, chunkComponentStore);
/* 170 */         if (count <= 0.0D)
/*     */           return; 
/* 172 */         spiralIterator = worldSpawnData.getSpiralIterator();
/*     */         
/* 174 */         spiralIterator.init(originX, originZ, 3);
/* 175 */         if (!spiralIterator.hasNext()) {
/*     */           return;
/*     */         }
/* 178 */         spiralIterator.next();
/*     */         
/* 180 */         while (spiralIterator.hasNext() && count > 0.0D) {
/* 181 */           long chunkIndex = spiralIterator.next();
/* 182 */           chunkRef = chunkStore.getChunkReference(chunkIndex);
/* 183 */           if (chunkRef == null)
/*     */             continue; 
/* 185 */           count = untrackRemovedNPC(chunkRef, environmentIndex, count, this.chunkSpawnDataComponentType, this.chunkSpawnedNPCDataComponentType, chunkComponentStore);
/*     */         } 
/*     */         
/* 188 */         if (count > 0.0D) {
/* 189 */           HytaleLogger.Api context = LOGGER.at(Level.FINE);
/* 190 */           if (context.isEnabled()) {
/* 191 */             context.log("Failed to remove %s of a spread NPC spawn from neighbouring chunks, spreading to other chunks with matching environment", Double.valueOf(count));
/*     */           }
/*     */           
/* 194 */           Set<Ref<ChunkStore>> chunkOptions = worldSpawnData.getWorldEnvironmentSpawnData(environmentIndex).getChunkRefSet();
/* 195 */           Iterator<Ref<ChunkStore>> iterator = chunkOptions.iterator();
/* 196 */           while (iterator.hasNext() && count > 0.0D) {
/* 197 */             count = untrackRemovedNPC(iterator.next(), environmentIndex, count, this.chunkSpawnDataComponentType, this.chunkSpawnedNPCDataComponentType, chunkComponentStore);
/*     */           }
/*     */           
/* 200 */           if (count > 0.0D && 
/* 201 */             context.isEnabled()) {
/* 202 */             context.log("Failed to remove %s of an NPC spawn from random chunks with matching environments", Double.valueOf(count));
/*     */           }
/*     */         } 
/*     */ 
/*     */         
/* 207 */         spiralIterator.reset(); break;
/*     */       case UNLOAD:
/* 209 */         untrackNPC(npcComponent.getEnvironment(), npcComponent.getSpawnRoleIndex(), worldSpawnData);
/*     */         break;
/*     */     }  }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean trackNPC(int environmentIndex, int roleIndex, @Nonnull WorldSpawnData worldSpawnData, @Nonnull World world, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 218 */     if (!worldSpawnData.trackNPC(environmentIndex, roleIndex, 1, world, componentAccessor)) return false;
/*     */     
/* 220 */     HytaleLogger.Api context = LOGGER.at(Level.FINER);
/* 221 */     if (context.isEnabled()) {
/* 222 */       context.log("Track Spawn env=%s role=%s", getEnvironmentName(environmentIndex), NPCPlugin.get().getName(roleIndex));
/*     */     }
/* 224 */     return true;
/*     */   }
/*     */   
/*     */   private static boolean untrackNPC(int environmentIndex, int roleIndex, @Nonnull WorldSpawnData worldSpawnData) {
/* 228 */     if (!worldSpawnData.untrackNPC(environmentIndex, roleIndex, 1)) return false;
/*     */     
/* 230 */     HytaleLogger.Api context = LOGGER.at(Level.FINER);
/* 231 */     if (context.isEnabled()) {
/* 232 */       context.log("Despawn env=%s role=%s", getEnvironmentName(environmentIndex), NPCPlugin.get().getName(roleIndex));
/*     */     }
/* 234 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static double trackNewNPC(@Nonnull Ref<ChunkStore> ref, int environmentIndex, double count, @Nonnull ComponentType<ChunkStore, ChunkSpawnData> chunkSpawnDataComponentType, @Nonnull ComponentType<ChunkStore, ChunkSpawnedNPCData> chunkSpawnedNPCDataComponentType, @Nonnull Store<ChunkStore> store) {
/* 242 */     ChunkSpawnData chunkSpawnData = (ChunkSpawnData)store.getComponent(ref, chunkSpawnDataComponentType);
/* 243 */     if (chunkSpawnData == null) return count;
/*     */     
/* 245 */     ChunkEnvironmentSpawnData spawnData = (ChunkEnvironmentSpawnData)chunkSpawnData.getChunkEnvironmentSpawnDataMap().get(environmentIndex);
/* 246 */     if (spawnData == null) return count;
/*     */     
/* 248 */     ChunkSpawnedNPCData chunkSpawnedNPCDataComponent = (ChunkSpawnedNPCData)store.getComponent(ref, chunkSpawnedNPCDataComponentType);
/* 249 */     assert chunkSpawnedNPCDataComponent != null;
/*     */     
/* 251 */     double spawnedNPCs = chunkSpawnedNPCDataComponent.getEnvironmentSpawnCount(environmentIndex);
/* 252 */     if (spawnData.isFullyPopulated(spawnedNPCs)) return count;
/*     */     
/* 254 */     WorldChunk worldChunkComponent = (WorldChunk)store.getComponent(ref, WorldChunk.getComponentType());
/* 255 */     assert worldChunkComponent != null;
/*     */     
/* 257 */     worldChunkComponent.markNeedsSaving();
/* 258 */     double expectedNPCs = spawnData.getExpectedNPCs();
/* 259 */     double remainingSpace = expectedNPCs - spawnedNPCs;
/* 260 */     if (count > remainingSpace) {
/* 261 */       HytaleLogger.Api api = LOGGER.at(Level.FINEST);
/* 262 */       if (api.isEnabled()) {
/* 263 */         api.log("Spreading " + remainingSpace + " to chunk " + worldChunkComponent.getIndex() + " with total capacity " + expectedNPCs);
/*     */       }
/* 265 */       count -= remainingSpace;
/* 266 */       chunkSpawnedNPCDataComponent.setEnvironmentSpawnCount(environmentIndex, expectedNPCs);
/* 267 */       return count;
/*     */     } 
/*     */     
/* 270 */     HytaleLogger.Api context = LOGGER.at(Level.FINEST);
/* 271 */     if (context.isEnabled()) {
/* 272 */       context.log("Spreading " + count + " to chunk " + worldChunkComponent.getIndex() + " with total capacity " + expectedNPCs);
/*     */     }
/*     */     
/* 275 */     chunkSpawnedNPCDataComponent.setEnvironmentSpawnCount(environmentIndex, spawnedNPCs + count);
/* 276 */     return 0.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static double untrackRemovedNPC(@Nonnull Ref<ChunkStore> ref, int environmentIndex, double count, @Nonnull ComponentType<ChunkStore, ChunkSpawnData> chunkSpawnDataComponentType, @Nonnull ComponentType<ChunkStore, ChunkSpawnedNPCData> chunkSpawnedNPCDataComponentType, @Nonnull Store<ChunkStore> store) {
/* 284 */     ChunkSpawnData chunkSpawnData = (ChunkSpawnData)store.getComponent(ref, chunkSpawnDataComponentType);
/* 285 */     if (chunkSpawnData == null) return count;
/*     */     
/* 287 */     ChunkEnvironmentSpawnData spawnData = (ChunkEnvironmentSpawnData)chunkSpawnData.getChunkEnvironmentSpawnDataMap().get(environmentIndex);
/* 288 */     if (spawnData == null) return count;
/*     */     
/* 290 */     ChunkSpawnedNPCData chunkSpawnedNPCDataComponent = (ChunkSpawnedNPCData)store.getComponent(ref, chunkSpawnedNPCDataComponentType);
/* 291 */     assert chunkSpawnedNPCDataComponent != null;
/*     */     
/* 293 */     double spawnedNPCs = chunkSpawnedNPCDataComponent.getEnvironmentSpawnCount(environmentIndex);
/* 294 */     if (spawnedNPCs <= 0.0D) return count;
/*     */     
/* 296 */     WorldChunk worldChunkComponent = (WorldChunk)store.getComponent(ref, WorldChunk.getComponentType());
/* 297 */     assert worldChunkComponent != null;
/*     */     
/* 299 */     worldChunkComponent.markNeedsSaving();
/* 300 */     double expectedNPCs = spawnData.getExpectedNPCs();
/* 301 */     if (spawnedNPCs < count) {
/* 302 */       HytaleLogger.Api api = LOGGER.at(Level.FINEST);
/* 303 */       if (api.isEnabled()) {
/* 304 */         api.log("Spreading removal of " + spawnedNPCs + " to chunk " + worldChunkComponent.getIndex() + " with total capacity " + expectedNPCs);
/*     */       }
/*     */       
/* 307 */       count -= spawnedNPCs;
/* 308 */       chunkSpawnedNPCDataComponent.setEnvironmentSpawnCount(environmentIndex, 0.0D);
/* 309 */       return count;
/*     */     } 
/*     */     
/* 312 */     HytaleLogger.Api context = LOGGER.at(Level.FINEST);
/* 313 */     if (context.isEnabled()) {
/* 314 */       context.log("Spreading removal of " + count + " to chunk " + worldChunkComponent.getIndex() + " with total capacity " + expectedNPCs);
/*     */     }
/*     */     
/* 317 */     chunkSpawnedNPCDataComponent.setEnvironmentSpawnCount(environmentIndex, spawnedNPCs - count);
/* 318 */     return 0.0D;
/*     */   }
/*     */   
/*     */   private static String getEnvironmentName(int id) {
/* 322 */     Environment env = (Environment)Environment.getAssetMap().getAsset(id);
/* 323 */     return (env != null) ? env.getId() : ("<" + id + ">");
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\world\system\WorldSpawnTrackingSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */