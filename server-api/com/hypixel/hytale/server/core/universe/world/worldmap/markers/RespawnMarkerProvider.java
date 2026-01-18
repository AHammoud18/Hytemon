/*    */ package com.hypixel.hytale.server.core.universe.world.worldmap.markers;
/*    */ 
/*    */ import com.hypixel.hytale.math.vector.Transform;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import com.hypixel.hytale.protocol.packets.worldmap.MapMarker;
/*    */ import com.hypixel.hytale.server.core.asset.type.gameplay.GameplayConfig;
/*    */ import com.hypixel.hytale.server.core.asset.type.gameplay.WorldMapConfig;
/*    */ import com.hypixel.hytale.server.core.entity.entities.player.data.PlayerRespawnPointData;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.WorldMapTracker;
/*    */ import com.hypixel.hytale.server.core.universe.world.worldmap.WorldMapManager;
/*    */ import com.hypixel.hytale.server.core.util.PositionUtil;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class RespawnMarkerProvider
/*    */   implements WorldMapManager.MarkerProvider
/*    */ {
/* 18 */   public static final RespawnMarkerProvider INSTANCE = new RespawnMarkerProvider();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void update(@Nonnull World world, @Nonnull GameplayConfig gameplayConfig, @Nonnull WorldMapTracker tracker, int chunkViewRadius, int playerChunkX, int playerChunkZ) {
/* 25 */     WorldMapConfig worldMapConfig = gameplayConfig.getWorldMapConfig();
/* 26 */     if (!worldMapConfig.isDisplayHome())
/*    */       return; 
/* 28 */     PlayerRespawnPointData[] respawnPoints = tracker.getPlayer().getPlayerConfigData().getPerWorldData(world.getName()).getRespawnPoints();
/* 29 */     if (respawnPoints == null)
/*    */       return; 
/* 31 */     for (int i = 0; i < respawnPoints.length; i++) {
/* 32 */       addRespawnMarker(tracker, playerChunkX, playerChunkZ, respawnPoints[i], i);
/*    */     }
/*    */   }
/*    */   
/*    */   private static void addRespawnMarker(@Nonnull WorldMapTracker tracker, int playerChunkX, int playerChunkZ, @Nonnull PlayerRespawnPointData respawnPoint, int index) {
/* 37 */     String respawnPointName = respawnPoint.getName();
/* 38 */     Vector3i respawnPointPosition = respawnPoint.getBlockPosition();
/*    */ 
/*    */     
/* 41 */     tracker.trySendMarker(-1, playerChunkX, playerChunkZ, respawnPointPosition
/* 42 */         .toVector3d(), 0.0F, respawnPointName + respawnPointName, respawnPointName, respawnPointPosition, (id, name, rp) -> new MapMarker(id, name, "Home.png", PositionUtil.toTransformPacket(new Transform(rp)), null));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\worldmap\markers\RespawnMarkerProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */