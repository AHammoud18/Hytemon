/*    */ package com.hypixel.hytale.builtin.buildertools.prefabeditor;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.buildertools.BuilderToolsPlugin;
/*    */ import com.hypixel.hytale.protocol.packets.worldmap.MapMarker;
/*    */ import com.hypixel.hytale.server.core.asset.type.gameplay.GameplayConfig;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.WorldMapTracker;
/*    */ import com.hypixel.hytale.server.core.universe.world.worldmap.WorldMapManager;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class PrefabMarkerProvider implements WorldMapManager.MarkerProvider {
/* 13 */   public static final PrefabMarkerProvider INSTANCE = new PrefabMarkerProvider();
/*    */ 
/*    */ 
/*    */   
/*    */   public void update(@Nonnull World world, @Nonnull GameplayConfig gameplayConfig, @Nonnull WorldMapTracker tracker, int chunkViewRadius, int playerChunkX, int playerChunkZ) {
/* 18 */     PrefabEditSessionManager sessionManager = BuilderToolsPlugin.get().getPrefabEditSessionManager();
/* 19 */     Player player = tracker.getPlayer();
/* 20 */     PrefabEditSession session = sessionManager.getPrefabEditSession(player.getUuid());
/*    */     
/* 22 */     if (session == null || !session.getWorldName().equals(world.getName()))
/*    */       return; 
/* 24 */     for (PrefabEditingMetadata metadata : session.getLoadedPrefabMetadata().values()) {
/* 25 */       MapMarker marker = PrefabEditSession.createPrefabMarker(metadata);
/* 26 */       tracker.trySendMarker(chunkViewRadius, playerChunkX, playerChunkZ, marker);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\prefabeditor\PrefabMarkerProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */