/*    */ package com.hypixel.hytale.server.core.universe.world.worldmap.markers;
/*    */ 
/*    */ import com.hypixel.hytale.protocol.packets.worldmap.MapMarker;
/*    */ import com.hypixel.hytale.server.core.asset.type.gameplay.GameplayConfig;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.WorldMapTracker;
/*    */ import com.hypixel.hytale.server.core.universe.world.worldmap.WorldMapManager;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class POIMarkerProvider
/*    */   implements WorldMapManager.MarkerProvider
/*    */ {
/* 16 */   public static final POIMarkerProvider INSTANCE = new POIMarkerProvider();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void update(@Nonnull World world, @Nonnull GameplayConfig gameplayConfig, @Nonnull WorldMapTracker tracker, int chunkViewRadius, int playerChunkX, int playerChunkZ) {
/* 23 */     Map<String, MapMarker> globalMarkers = world.getWorldMapManager().getPointsOfInterest();
/* 24 */     if (globalMarkers.isEmpty())
/*    */       return; 
/* 26 */     for (MapMarker marker : globalMarkers.values())
/* 27 */       tracker.trySendMarker(chunkViewRadius, playerChunkX, playerChunkZ, marker); 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\worldmap\markers\POIMarkerProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */