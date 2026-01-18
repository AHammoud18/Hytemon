/*    */ package com.hypixel.hytale.server.core.universe.world.worldmap.provider;
/*    */ 
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.protocol.packets.worldmap.MapMarker;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.map.WorldMap;
/*    */ import com.hypixel.hytale.server.core.universe.world.worldmap.IWorldMap;
/*    */ import com.hypixel.hytale.server.core.universe.world.worldmap.WorldMapLoadException;
/*    */ import com.hypixel.hytale.server.core.universe.world.worldmap.WorldMapSettings;
/*    */ import it.unimi.dsi.fastutil.longs.LongSet;
/*    */ import java.util.Collections;
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class DisabledWorldMapProvider
/*    */   implements IWorldMapProvider
/*    */ {
/*    */   public static final String ID = "Disabled";
/* 21 */   public static final BuilderCodec<DisabledWorldMapProvider> CODEC = BuilderCodec.builder(DisabledWorldMapProvider.class, DisabledWorldMapProvider::new)
/* 22 */     .build();
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public IWorldMap getGenerator(World world) throws WorldMapLoadException {
/* 27 */     return DisabledWorldMap.INSTANCE;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 33 */     return "DisabledWorldMapProvider{}";
/*    */   }
/*    */   
/*    */   static class DisabledWorldMap implements IWorldMap {
/* 37 */     public static final IWorldMap INSTANCE = new DisabledWorldMap();
/*    */ 
/*    */     
/*    */     @Nonnull
/*    */     public WorldMapSettings getWorldMapSettings() {
/* 42 */       return WorldMapSettings.DISABLED;
/*    */     }
/*    */ 
/*    */     
/*    */     @Nonnull
/*    */     public CompletableFuture<WorldMap> generate(World world, int imageWidth, int imageHeight, LongSet chunksToGenerate) {
/* 48 */       return CompletableFuture.completedFuture(new WorldMap(0));
/*    */     }
/*    */ 
/*    */     
/*    */     @Nonnull
/*    */     public CompletableFuture<Map<String, MapMarker>> generatePointsOfInterest(World world) {
/* 54 */       return CompletableFuture.completedFuture(Collections.emptyMap());
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\worldmap\provider\DisabledWorldMapProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */