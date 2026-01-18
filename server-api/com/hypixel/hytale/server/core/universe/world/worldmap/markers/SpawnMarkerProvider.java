/*    */ package com.hypixel.hytale.server.core.universe.world.worldmap.markers;
/*    */ 
/*    */ import com.hypixel.hytale.math.vector.Transform;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.protocol.packets.worldmap.MapMarker;
/*    */ import com.hypixel.hytale.server.core.asset.type.gameplay.GameplayConfig;
/*    */ import com.hypixel.hytale.server.core.asset.type.gameplay.WorldMapConfig;
/*    */ import com.hypixel.hytale.server.core.entity.Entity;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.WorldMapTracker;
/*    */ import com.hypixel.hytale.server.core.universe.world.worldmap.WorldMapManager;
/*    */ import com.hypixel.hytale.server.core.util.PositionUtil;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SpawnMarkerProvider
/*    */   implements WorldMapManager.MarkerProvider
/*    */ {
/* 21 */   public static final SpawnMarkerProvider INSTANCE = new SpawnMarkerProvider();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void update(@Nonnull World world, @Nonnull GameplayConfig gameplayConfig, @Nonnull WorldMapTracker tracker, int chunkViewRadius, int playerChunkX, int playerChunkZ) {
/* 31 */     WorldMapConfig worldMapConfig = gameplayConfig.getWorldMapConfig();
/* 32 */     if (!worldMapConfig.isDisplaySpawn())
/*    */       return; 
/* 34 */     Player player = tracker.getPlayer();
/* 35 */     Transform spawnPoint = world.getWorldConfig().getSpawnProvider().getSpawnPoint((Entity)player);
/* 36 */     if (spawnPoint == null)
/*    */       return; 
/* 38 */     Vector3d spawnPosition = spawnPoint.getPosition();
/*    */ 
/*    */     
/* 41 */     tracker.trySendMarker(chunkViewRadius, playerChunkX, playerChunkZ, spawnPosition, spawnPoint
/* 42 */         .getRotation().getYaw(), "Spawn", "Spawn", spawnPosition, (id, name, pos) -> new MapMarker(id, name, "Spawn.png", PositionUtil.toTransformPacket(new Transform(pos)), null));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\worldmap\markers\SpawnMarkerProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */