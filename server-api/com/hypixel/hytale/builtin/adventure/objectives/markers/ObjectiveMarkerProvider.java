/*    */ package com.hypixel.hytale.builtin.adventure.objectives.markers;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.Objective;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.ObjectiveDataStore;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.ObjectivePlugin;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.task.ObjectiveTask;
/*    */ import com.hypixel.hytale.protocol.packets.worldmap.MapMarker;
/*    */ import com.hypixel.hytale.server.core.asset.type.gameplay.GameplayConfig;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.WorldMapTracker;
/*    */ import com.hypixel.hytale.server.core.universe.world.worldmap.WorldMapManager;
/*    */ import java.util.Set;
/*    */ import java.util.UUID;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ObjectiveMarkerProvider
/*    */   implements WorldMapManager.MarkerProvider {
/* 19 */   public static final ObjectiveMarkerProvider INSTANCE = new ObjectiveMarkerProvider();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void update(@Nonnull World world, @Nonnull GameplayConfig gameplayConfig, @Nonnull WorldMapTracker tracker, int chunkViewRadius, int playerChunkX, int playerChunkZ) {
/* 26 */     Player player = tracker.getPlayer();
/* 27 */     Set<UUID> activeObjectiveUUIDs = player.getPlayerConfigData().getActiveObjectiveUUIDs();
/*    */     
/* 29 */     if (activeObjectiveUUIDs.isEmpty())
/*    */       return; 
/* 31 */     UUID playerUUID = player.getUuid();
/* 32 */     ObjectiveDataStore objectiveDataStore = ObjectivePlugin.get().getObjectiveDataStore();
/*    */     
/* 34 */     for (UUID objectiveUUID : activeObjectiveUUIDs) {
/* 35 */       Objective objective = objectiveDataStore.getObjective(objectiveUUID);
/* 36 */       if (objective == null) {
/*    */         continue;
/*    */       }
/* 39 */       if (!objective.getActivePlayerUUIDs().contains(playerUUID))
/*    */         continue; 
/* 41 */       ObjectiveTask[] tasks = objective.getCurrentTasks();
/* 42 */       if (tasks == null)
/*    */         continue; 
/* 44 */       for (ObjectiveTask task : tasks) {
/* 45 */         for (MapMarker marker : task.getMarkers())
/* 46 */           tracker.trySendMarker(chunkViewRadius, playerChunkX, playerChunkZ, marker); 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\markers\ObjectiveMarkerProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */