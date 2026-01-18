/*    */ package com.hypixel.hytale.server.core.universe.world.worldmap.provider.chunk;
/*    */ 
/*    */ import com.hypixel.hytale.protocol.packets.worldmap.MapMarker;
/*    */ import com.hypixel.hytale.protocol.packets.worldmap.UpdateWorldMapSettings;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.map.WorldMap;
/*    */ import com.hypixel.hytale.server.core.universe.world.worldmap.IWorldMap;
/*    */ import com.hypixel.hytale.server.core.universe.world.worldmap.WorldMapSettings;
/*    */ import it.unimi.dsi.fastutil.longs.LongIterator;
/*    */ import it.unimi.dsi.fastutil.longs.LongSet;
/*    */ import java.util.Collections;
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChunkWorldMap
/*    */   implements IWorldMap
/*    */ {
/* 21 */   public static final ChunkWorldMap INSTANCE = new ChunkWorldMap();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public WorldMapSettings getWorldMapSettings() {
/* 29 */     UpdateWorldMapSettings settingsPacket = new UpdateWorldMapSettings();
/* 30 */     settingsPacket.defaultScale = 128.0F;
/* 31 */     settingsPacket.minScale = 32.0F;
/* 32 */     settingsPacket.maxScale = 175.0F;
/* 33 */     return new WorldMapSettings(null, 3.0F, 2.0F, 3, 32, settingsPacket);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public CompletableFuture<WorldMap> generate(World world, int imageWidth, int imageHeight, @Nonnull LongSet chunksToGenerate) {
/* 45 */     CompletableFuture[] arrayOfCompletableFuture = new CompletableFuture[chunksToGenerate.size()];
/*    */     
/* 47 */     int futureIndex = 0;
/* 48 */     LongIterator iterator = chunksToGenerate.iterator();
/* 49 */     while (iterator.hasNext()) {
/* 50 */       arrayOfCompletableFuture[futureIndex++] = ImageBuilder.build(iterator.nextLong(), imageWidth, imageHeight, world);
/*    */     }
/*    */     
/* 53 */     return CompletableFuture.allOf((CompletableFuture<?>[])arrayOfCompletableFuture).thenApply(unused -> {
/*    */           WorldMap worldMap = new WorldMap(futures.length);
/*    */           for (CompletableFuture<ImageBuilder> future : futures) {
/*    */             ImageBuilder builder = future.getNow(null);
/*    */             if (builder != null) {
/*    */               worldMap.getChunks().put(builder.getIndex(), builder.getImage());
/*    */             }
/*    */           } 
/*    */           return worldMap;
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public CompletableFuture<Map<String, MapMarker>> generatePointsOfInterest(World world) {
/* 70 */     return CompletableFuture.completedFuture(Collections.emptyMap());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\worldmap\provider\chunk\ChunkWorldMap.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */