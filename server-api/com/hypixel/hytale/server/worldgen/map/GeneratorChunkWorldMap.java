/*    */ package com.hypixel.hytale.server.worldgen.map;
/*    */ 
/*    */ import com.hypixel.hytale.logger.sentry.SkipSentryException;
/*    */ import com.hypixel.hytale.protocol.packets.worldmap.BiomeData;
/*    */ import com.hypixel.hytale.protocol.packets.worldmap.MapMarker;
/*    */ import com.hypixel.hytale.protocol.packets.worldmap.UpdateWorldMapSettings;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.map.WorldMap;
/*    */ import com.hypixel.hytale.server.core.universe.world.worldmap.WorldMapSettings;
/*    */ import com.hypixel.hytale.server.core.universe.world.worldmap.provider.chunk.ChunkWorldMap;
/*    */ import com.hypixel.hytale.server.worldgen.biome.Biome;
/*    */ import com.hypixel.hytale.server.worldgen.chunk.ChunkGenerator;
/*    */ import com.hypixel.hytale.server.worldgen.container.UniquePrefabContainer;
/*    */ import com.hypixel.hytale.server.worldgen.zone.Zone;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import java.util.concurrent.Executor;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GeneratorChunkWorldMap
/*    */   extends ChunkWorldMap
/*    */ {
/* 27 */   private static final WorldMap EMPTY = new WorldMap(0);
/*    */   
/*    */   @Nonnull
/*    */   private final ChunkGenerator generator;
/*    */   @Nonnull
/*    */   private final Executor executor;
/*    */   
/*    */   public GeneratorChunkWorldMap(@Nonnull ChunkGenerator generator, @Nonnull Executor executor) {
/* 35 */     this.generator = generator;
/* 36 */     this.executor = executor;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public CompletableFuture<Map<String, MapMarker>> generatePointsOfInterest(@Nonnull World world) {
/* 42 */     int seed = (int)world.getWorldConfig().getSeed();
/*    */     
/* 44 */     UniquePrefabContainer.UniquePrefabEntry[] uniquePrefabs = this.generator.getUniquePrefabs(seed);
/* 45 */     if (uniquePrefabs == null || uniquePrefabs.length == 0) {
/* 46 */       return CompletableFuture.completedFuture(EMPTY.getPointsOfInterest());
/*    */     }
/*    */     
/* 49 */     return CompletableFuture.<Map<String, MapMarker>>supplyAsync(() -> { WorldMap worldMap = new WorldMap(0); for (UniquePrefabContainer.UniquePrefabEntry entry : uniquePrefabs) { if (!entry.isSpawnLocation() && entry.isShowOnMap()) worldMap.addPointOfInterest("UniquePrefab-" + entry.getName() + "-" + String.valueOf(entry.getPosition()), entry.getName(), "Prefab.png", entry.getPosition());  }  return worldMap.getPointsOfInterest(); }this.executor)
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 59 */       .exceptionally(t -> {
/*    */           throw new SkipSentryException(t);
/*    */         });
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public WorldMapSettings getWorldMapSettings() {
/* 67 */     Map<Short, BiomeData> biomeDataMap = new HashMap<>();
/*    */     
/* 69 */     for (Zone zone : this.generator.getZonePatternProvider().getZones()) {
/* 70 */       for (Biome biome : zone.biomePatternGenerator().getBiomes()) {
/* 71 */         int biomeId = biome.getId();
/* 72 */         if (biomeId < 0 || biomeId > 32767) {
/* 73 */           throw new IllegalArgumentException("Biome Id can't be < 0 || > 32767! BiomeId: " + biomeId);
/*    */         }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */         
/* 80 */         BiomeData biomeData = new BiomeData(zone.id(), zone.name(), biome.getName(), biome.getMapColor());
/*    */         
/* 82 */         BiomeData old = biomeDataMap.putIfAbsent(Short.valueOf((short)biomeId), biomeData);
/* 83 */         if (old != null) {
/* 84 */           throw new IllegalArgumentException("Multiple biomes with the same ID! New: " + String.valueOf(biomeData) + ", Old: " + String.valueOf(old));
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 89 */     UpdateWorldMapSettings settingsPacket = new UpdateWorldMapSettings();
/* 90 */     settingsPacket.biomeDataMap = biomeDataMap;
/* 91 */     settingsPacket.defaultScale = 128.0F;
/* 92 */     settingsPacket.minScale = 32.0F;
/* 93 */     settingsPacket.maxScale = 175.0F;
/* 94 */     return new WorldMapSettings(null, 3.0F, 2.0F, 3, 32, settingsPacket);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\map\GeneratorChunkWorldMap.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */