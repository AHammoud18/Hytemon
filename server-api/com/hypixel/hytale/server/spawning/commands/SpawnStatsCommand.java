/*     */ package com.hypixel.hytale.server.spawning.commands;
/*     */ import com.hypixel.hytale.component.Archetype;
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.asset.type.environment.config.Environment;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.FlagArg;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*     */ import com.hypixel.hytale.server.npc.components.SpawnMarkerReference;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import com.hypixel.hytale.server.spawning.SpawnRejection;
/*     */ import com.hypixel.hytale.server.spawning.spawnmarkers.SpawnMarkerEntity;
/*     */ import com.hypixel.hytale.server.spawning.world.ChunkEnvironmentSpawnData;
/*     */ import com.hypixel.hytale.server.spawning.world.WorldEnvironmentSpawnData;
/*     */ import com.hypixel.hytale.server.spawning.world.WorldNPCSpawnStat;
/*     */ import com.hypixel.hytale.server.spawning.world.component.ChunkSpawnData;
/*     */ import com.hypixel.hytale.server.spawning.world.component.WorldSpawnData;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
/*     */ import java.util.HashMap;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class SpawnStatsCommand extends AbstractWorldCommand {
/*     */   @Nonnull
/*  38 */   private final FlagArg environmentsArg = withFlagArg("environments", "server.commands.spawning.stats.arg.environments.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  44 */   private final FlagArg markersArg = withFlagArg("markers", "server.commands.spawning.stats.arg.markers.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  50 */   private final FlagArg verboseArg = withFlagArg("verbose", "server.commands.spawning.stats.arg.verbose.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SpawnStatsCommand() {
/*  56 */     super("stats", "server.commands.spawning.stats.desc");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/*  61 */     if (((Boolean)this.environmentsArg.get(context)).booleanValue()) {
/*  62 */       WorldSpawnData worldSpawnData = (WorldSpawnData)store.getResource(WorldSpawnData.getResourceType());
/*  63 */       AtomicInteger filtered = new AtomicInteger();
/*  64 */       boolean verbose = ((Boolean)this.verboseArg.get(context)).booleanValue();
/*  65 */       worldSpawnData.forEachEnvironmentSpawnData(worldEnvironmentSpawnData -> {
/*     */             if (!verbose && (!worldEnvironmentSpawnData.hasNPCs() || worldEnvironmentSpawnData.getExpectedNPCs() == 0.0D)) {
/*     */               filtered.getAndIncrement();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/*     */               return;
/*     */             } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             int environmentIndex = worldEnvironmentSpawnData.getEnvironmentIndex();
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             String name = ((Environment)Environment.getAssetMap().getAsset(environmentIndex)).getId();
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             Store<ChunkStore> chunkStore = world.getChunkStore().getStore();
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             double[] chunkExpected = { 0.0D };
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             worldEnvironmentSpawnData.getChunkRefSet().forEach(());
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             String message = String.format("Environment: %-30s Exp %6.2f Act %4d Blk %s Chunk exp: %6.2f", new Object[] { name, Double.valueOf(worldEnvironmentSpawnData.getExpectedNPCs()), Integer.valueOf(worldEnvironmentSpawnData.getActualNPCs()), Integer.valueOf(worldEnvironmentSpawnData.getSegmentCount()), Double.valueOf(chunkExpected[0]) });
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             ((HytaleLogger.Api)NPCPlugin.get().getLogger().atInfo()).log(message);
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             worldEnvironmentSpawnData.forEachNpcStat(());
/*     */           });
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 121 */       AtomicInteger trackedNPC = new AtomicInteger();
/* 122 */       AtomicInteger totalNPC = new AtomicInteger();
/*     */       
/* 124 */       store.forEachEntityParallel((Query)NPCEntity.getComponentType(), (index, archetypeChunk, commandBuffer) -> {
/*     */             totalNPC.getAndIncrement();
/*     */             NPCEntity npc = (NPCEntity)archetypeChunk.getComponent(index, NPCEntity.getComponentType());
/*     */             if (npc.getEnvironment() == Integer.MIN_VALUE || npc.getSpawnConfiguration() == Integer.MIN_VALUE)
/*     */               return; 
/*     */             trackedNPC.getAndIncrement();
/*     */           });
/* 131 */       int spawnJobsCompleted = worldSpawnData.getTotalSpawnJobsCompleted();
/* 132 */       String message = String.format("Total: Exp %.2f Exp-Empty %.2f Act %d Job Pending Act %d Tracked %d Total %d Unspawnable %s AvgSegCount %s Chunks %s Filtered empty envs %d Active Jobs %d Total Jobs Run %d Avg Job Budget %.2f", new Object[] {
/*     */             
/* 134 */             Double.valueOf(worldSpawnData.getExpectedNPCs()), Double.valueOf(worldSpawnData.getExpectedNPCsInEmptyEnvironments()), 
/* 135 */             Integer.valueOf(worldSpawnData.getActualNPCs()), Integer.valueOf(worldSpawnData.getTrackedCountFromJobs()), Integer.valueOf(trackedNPC.get()), 
/* 136 */             Integer.valueOf(totalNPC.get()), Boolean.valueOf(worldSpawnData.isUnspawnable()), Double.valueOf(worldSpawnData.averageSegmentCount()), 
/* 137 */             Integer.valueOf(worldSpawnData.getChunkCount()), Integer.valueOf(filtered.get()), Integer.valueOf(worldSpawnData.getActiveSpawnJobs()), 
/* 138 */             Integer.valueOf(spawnJobsCompleted), 
/* 139 */             Double.valueOf((spawnJobsCompleted > 0) ? (worldSpawnData.getTotalSpawnJobBudgetUsed() / spawnJobsCompleted) : 0.0D) });
/* 140 */       ((HytaleLogger.Api)NPCPlugin.get().getLogger().atInfo()).log(message);
/*     */     } 
/*     */     
/* 143 */     if (((Boolean)this.markersArg.get(context)).booleanValue()) {
/* 144 */       AtomicInteger spawnMarkerCount = new AtomicInteger();
/* 145 */       AtomicInteger inactiveSpawnMarkerCount = new AtomicInteger();
/* 146 */       Object2IntOpenHashMap<String> spawnMarkerTypeCounts = new Object2IntOpenHashMap();
/*     */       
/* 148 */       store.forEachChunk((Query)SpawnMarkerEntity.getComponentType(), (archetypeChunk, componentStoreCommandBuffer) -> {
/*     */             for (int index = 0; index < archetypeChunk.size(); index++) {
/*     */               SpawnMarkerEntity entity = (SpawnMarkerEntity)archetypeChunk.getComponent(index, SpawnMarkerEntity.getComponentType());
/*     */               spawnMarkerCount.getAndIncrement();
/*     */               spawnMarkerTypeCounts.mergeInt(entity.getSpawnMarkerId(), 1, Integer::sum);
/*     */               if (entity.getSpawnCount() == 0) {
/*     */                 inactiveSpawnMarkerCount.getAndIncrement();
/*     */               }
/*     */             } 
/*     */           });
/* 158 */       AtomicInteger spawnMarkerNPCCount = new AtomicInteger();
/* 159 */       Object2IntOpenHashMap<String> roleCounts = new Object2IntOpenHashMap();
/* 160 */       HashMap<String, Object2IntMap<String>> roleCountsPerMarkerType = new HashMap<>();
/*     */       
/* 162 */       store.forEachChunk((Query)Archetype.of(new ComponentType[] { NPCEntity.getComponentType(), SpawnMarkerReference.getComponentType() }, ), (archetypeChunk, componentStoreCommandBuffer) -> {
/*     */             for (int index = 0; index < archetypeChunk.size(); index++) {
/*     */               NPCEntity entity = (NPCEntity)archetypeChunk.getComponent(index, NPCEntity.getComponentType());
/*     */               
/*     */               SpawnMarkerReference spawnMarkerReference = (SpawnMarkerReference)archetypeChunk.getComponent(index, SpawnMarkerReference.getComponentType());
/*     */               
/*     */               spawnMarkerNPCCount.getAndIncrement();
/*     */               
/*     */               String roleName = entity.getRoleName();
/*     */               
/*     */               roleCounts.mergeInt(roleName, 1, Integer::sum);
/*     */               
/*     */               Ref<EntityStore> markerRef = spawnMarkerReference.getReference().getEntity((ComponentAccessor)componentStoreCommandBuffer);
/*     */               SpawnMarkerEntity marker = (SpawnMarkerEntity)componentStoreCommandBuffer.getComponent(markerRef, SpawnMarkerEntity.getComponentType());
/*     */               Object2IntMap<String> spawnedRoles = roleCountsPerMarkerType.computeIfAbsent(marker.getSpawnMarkerId(), ());
/*     */               spawnedRoles.mergeInt(roleName, 1, Integer::sum);
/*     */             } 
/*     */           });
/* 180 */       StringBuilder sb = new StringBuilder();
/* 181 */       sb.append("Markers: ").append(spawnMarkerCount.get()).append(" (With zero spawns: ").append(inactiveSpawnMarkerCount.get()).append(")\nSpawned NPCs: ")
/* 182 */         .append(spawnMarkerNPCCount.get());
/* 183 */       roleCounts.object2IntEntrySet().fastForEach(stringEntry -> sb.append("\n  ").append((String)stringEntry.getKey()).append(": ").append(stringEntry.getIntValue()));
/* 184 */       sb.append("\nRoles by marker type:");
/* 185 */       roleCountsPerMarkerType.forEach((key, spawnedRoles) -> {
/*     */             sb.append("\n  ").append(key).append(" (Instances: ").append(spawnMarkerTypeCounts.getInt(key)).append(")");
/*     */             
/*     */             spawnedRoles.object2IntEntrySet().forEach(());
/*     */           });
/* 190 */       ((HytaleLogger.Api)NPCPlugin.get().getLogger().atInfo()).log(sb.toString());
/*     */     } 
/*     */     
/* 193 */     context.sendMessage(Message.translation("server.commands.spawning.stats.results"));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\commands\SpawnStatsCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */