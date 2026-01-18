/*    */ package com.hypixel.hytale.server.core.universe.world.worldmap.markers;
/*    */ 
/*    */ import com.hypixel.hytale.protocol.packets.worldmap.MapMarker;
/*    */ import com.hypixel.hytale.server.core.asset.type.gameplay.GameplayConfig;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.entity.entities.player.data.PlayerWorldData;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.WorldMapTracker;
/*    */ import com.hypixel.hytale.server.core.universe.world.worldmap.WorldMapManager;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class PlayerMarkersProvider
/*    */   implements WorldMapManager.MarkerProvider
/*    */ {
/* 15 */   public static final PlayerMarkersProvider INSTANCE = new PlayerMarkersProvider();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void update(@Nonnull World world, @Nonnull GameplayConfig gameplayConfig, @Nonnull WorldMapTracker tracker, int chunkViewRadius, int playerChunkX, int playerChunkZ) {
/* 22 */     Player player = tracker.getPlayer();
/* 23 */     PlayerWorldData perWorldData = player.getPlayerConfigData().getPerWorldData(world.getName());
/* 24 */     MapMarker[] worldMapMarkers = perWorldData.getWorldMapMarkers();
/*    */     
/* 26 */     if (worldMapMarkers == null)
/*    */       return; 
/* 28 */     for (MapMarker marker : worldMapMarkers)
/* 29 */       tracker.trySendMarker(chunkViewRadius, playerChunkX, playerChunkZ, marker); 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\worldmap\markers\PlayerMarkersProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */