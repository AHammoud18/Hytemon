/*    */ package com.hypixel.hytale.server.core.universe.world.worldmap.markers;
/*    */ 
/*    */ import com.hypixel.hytale.math.vector.Transform;
/*    */ import com.hypixel.hytale.protocol.packets.worldmap.MapMarker;
/*    */ import com.hypixel.hytale.server.core.asset.type.gameplay.GameplayConfig;
/*    */ import com.hypixel.hytale.server.core.asset.type.gameplay.WorldMapConfig;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.entity.entities.player.data.PlayerDeathPositionData;
/*    */ import com.hypixel.hytale.server.core.entity.entities.player.data.PlayerWorldData;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.WorldMapTracker;
/*    */ import com.hypixel.hytale.server.core.universe.world.worldmap.WorldMapManager;
/*    */ import com.hypixel.hytale.server.core.util.PositionUtil;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class DeathMarkerProvider implements WorldMapManager.MarkerProvider {
/* 18 */   public static final DeathMarkerProvider INSTANCE = new DeathMarkerProvider();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void update(@Nonnull World world, @Nonnull GameplayConfig gameplayConfig, @Nonnull WorldMapTracker tracker, int chunkViewRadius, int playerChunkX, int playerChunkZ) {
/* 25 */     WorldMapConfig worldMapConfig = gameplayConfig.getWorldMapConfig();
/* 26 */     if (!worldMapConfig.isDisplayDeathMarker())
/*    */       return; 
/* 28 */     Player player = tracker.getPlayer();
/* 29 */     PlayerWorldData perWorldData = player.getPlayerConfigData().getPerWorldData(world.getName());
/* 30 */     List<PlayerDeathPositionData> deathPositions = perWorldData.getDeathPositions();
/*    */     
/* 32 */     for (PlayerDeathPositionData deathPosition : deathPositions) {
/* 33 */       addDeathMarker(tracker, playerChunkX, playerChunkZ, deathPosition);
/*    */     }
/*    */   }
/*    */   
/*    */   private static void addDeathMarker(@Nonnull WorldMapTracker tracker, int playerChunkX, int playerChunkZ, @Nonnull PlayerDeathPositionData deathPosition) {
/* 38 */     String markerId = deathPosition.getMarkerId();
/* 39 */     Transform transform = deathPosition.getTransform();
/* 40 */     int deathDay = deathPosition.getDay();
/*    */ 
/*    */     
/* 43 */     tracker.trySendMarker(-1, playerChunkX, playerChunkZ, transform
/* 44 */         .getPosition(), transform.getRotation().getYaw(), markerId, "Death (Day " + deathDay + ")", transform, (id, name, t) -> new MapMarker(id, name, "Death.png", PositionUtil.toTransformPacket(t), null));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\worldmap\markers\DeathMarkerProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */