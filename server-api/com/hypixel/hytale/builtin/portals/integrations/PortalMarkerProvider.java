/*    */ package com.hypixel.hytale.builtin.portals.integrations;
/*    */ 
/*    */ import com.hypixel.hytale.math.vector.Transform;
/*    */ import com.hypixel.hytale.protocol.packets.worldmap.MapMarker;
/*    */ import com.hypixel.hytale.server.core.asset.type.gameplay.GameplayConfig;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.WorldMapTracker;
/*    */ import com.hypixel.hytale.server.core.universe.world.spawn.ISpawnProvider;
/*    */ import com.hypixel.hytale.server.core.universe.world.spawn.IndividualSpawnProvider;
/*    */ import com.hypixel.hytale.server.core.universe.world.worldmap.WorldMapManager;
/*    */ import com.hypixel.hytale.server.core.util.PositionUtil;
/*    */ 
/*    */ public class PortalMarkerProvider implements WorldMapManager.MarkerProvider {
/* 14 */   public static final PortalMarkerProvider INSTANCE = new PortalMarkerProvider();
/*    */ 
/*    */   
/*    */   public void update(World world, GameplayConfig gameplayConfig, WorldMapTracker tracker, int chunkViewRadius, int playerChunkX, int playerChunkZ) {
/* 18 */     addSpawn(world, tracker, chunkViewRadius, playerChunkX, playerChunkZ);
/*    */   }
/*    */   private static void addSpawn(World world, WorldMapTracker tracker, int chunkViewRadius, int playerChunkX, int playerChunkZ) {
/*    */     IndividualSpawnProvider individualSpawnProvider;
/* 22 */     ISpawnProvider spawnProvider = world.getWorldConfig().getSpawnProvider();
/* 23 */     if (spawnProvider instanceof IndividualSpawnProvider) { individualSpawnProvider = (IndividualSpawnProvider)spawnProvider; }
/*    */     else { return; }
/* 25 */      Transform spawnPoint = individualSpawnProvider.getFirstSpawnPoint();
/* 26 */     if (spawnPoint == null)
/*    */       return; 
/* 28 */     tracker.trySendMarker(chunkViewRadius, playerChunkX, playerChunkZ, spawnPoint
/* 29 */         .getPosition(), spawnPoint.getRotation().getYaw(), "Portal", "Fragment Exit", spawnPoint, (id, name, sp) -> new MapMarker(id, name, "Portal.png", PositionUtil.toTransformPacket(sp), null));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\portals\integrations\PortalMarkerProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */