/*    */ package com.hypixel.hytale.server.core.universe.world.worldmap.markers;
/*    */ 
/*    */ import com.hypixel.hytale.math.vector.Transform;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.protocol.packets.worldmap.MapMarker;
/*    */ import com.hypixel.hytale.server.core.asset.type.gameplay.GameplayConfig;
/*    */ import com.hypixel.hytale.server.core.asset.type.gameplay.WorldMapConfig;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.WorldMapTracker;
/*    */ import com.hypixel.hytale.server.core.universe.world.worldmap.WorldMapManager;
/*    */ import com.hypixel.hytale.server.core.util.PositionUtil;
/*    */ import java.util.function.Predicate;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class PlayerIconMarkerProvider
/*    */   implements WorldMapManager.MarkerProvider
/*    */ {
/* 21 */   public static final PlayerIconMarkerProvider INSTANCE = new PlayerIconMarkerProvider();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void update(@Nonnull World world, @Nonnull GameplayConfig gameplayConfig, @Nonnull WorldMapTracker tracker, int chunkViewRadius, int playerChunkX, int playerChunkZ) {
/* 28 */     WorldMapConfig worldMapConfig = gameplayConfig.getWorldMapConfig();
/* 29 */     if (!worldMapConfig.isDisplayPlayers())
/* 30 */       return;  if (!tracker.shouldUpdatePlayerMarkers())
/*    */       return; 
/* 32 */     Player player = tracker.getPlayer();
/* 33 */     int chunkViewRadiusSq = chunkViewRadius * chunkViewRadius;
/*    */     
/* 35 */     Predicate<PlayerRef> playerMapFilter = tracker.getPlayerMapFilter();
/*    */     
/* 37 */     for (PlayerRef otherPlayer : world.getPlayerRefs()) {
/* 38 */       if (otherPlayer.getUuid().equals(player.getUuid()))
/*    */         continue; 
/* 40 */       Transform otherPlayerTransform = otherPlayer.getTransform();
/* 41 */       Vector3d otherPos = otherPlayerTransform.getPosition();
/*    */       
/* 43 */       int otherChunkX = (int)otherPos.x >> 5;
/* 44 */       int otherChunkZ = (int)otherPos.z >> 5;
/* 45 */       int chunkDiffX = otherChunkX - playerChunkX;
/* 46 */       int chunkDiffZ = otherChunkZ - playerChunkZ;
/*    */       
/* 48 */       int chunkDistSq = chunkDiffX * chunkDiffX + chunkDiffZ * chunkDiffZ;
/* 49 */       if (chunkDistSq > chunkViewRadiusSq)
/*    */         continue; 
/* 51 */       if (playerMapFilter != null && playerMapFilter.test(otherPlayer))
/*    */         continue; 
/* 53 */       tracker.trySendMarker(chunkViewRadius, playerChunkX, playerChunkZ, otherPos, otherPlayer
/* 54 */           .getHeadRotation().getYaw(), "Player-" + 
/* 55 */           String.valueOf(otherPlayer.getUuid()), "Player: " + otherPlayer.getUsername(), otherPlayer, (id, name, op) -> new MapMarker(id, name, "Player.png", PositionUtil.toTransformPacket(op.getTransform()), null));
/*    */     } 
/*    */ 
/*    */     
/* 59 */     tracker.resetPlayerMarkersUpdateTimer();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\worldmap\markers\PlayerIconMarkerProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */