/*    */ package com.hypixel.hytale.server.spawning.wrappers;
/*    */ 
/*    */ import com.hypixel.hytale.common.map.IWeightedMap;
/*    */ import com.hypixel.hytale.common.map.WeightedMap;
/*    */ import com.hypixel.hytale.server.spawning.assets.spawns.config.BeaconNPCSpawn;
/*    */ import com.hypixel.hytale.server.spawning.assets.spawns.config.RoleSpawnParameters;
/*    */ import java.util.Random;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BeaconSpawnWrapper
/*    */   extends SpawnWrapper<BeaconNPCSpawn>
/*    */ {
/*    */   @Nonnull
/*    */   private final IWeightedMap<RoleSpawnParameters> weightedRoles;
/*    */   private final double minDistanceFromPlayerSquared;
/*    */   private final double targetDistanceFromPlayerSquared;
/*    */   
/*    */   public BeaconSpawnWrapper(@Nonnull BeaconNPCSpawn spawn) {
/* 24 */     super(BeaconNPCSpawn.getAssetMap().getIndex(spawn.getId()), spawn);
/* 25 */     WeightedMap.Builder<RoleSpawnParameters> mapBuilder = WeightedMap.builder((Object[])RoleSpawnParameters.EMPTY_ARRAY);
/* 26 */     for (RoleSpawnParameters npc : spawn.getNPCs()) {
/* 27 */       if (!hasInvalidNPC(npc.getId())) {
/* 28 */         mapBuilder.put(npc, npc.getWeight());
/*    */       }
/*    */     } 
/* 31 */     this.weightedRoles = mapBuilder.build();
/* 32 */     double minDistance = spawn.getMinDistanceFromPlayer();
/* 33 */     this.minDistanceFromPlayerSquared = minDistance * minDistance;
/* 34 */     double targetDistance = spawn.getTargetDistanceFromPlayer();
/* 35 */     this.targetDistanceFromPlayerSquared = targetDistance * targetDistance;
/*    */   }
/*    */   
/*    */   public double getMinDistanceFromPlayerSquared() {
/* 39 */     return this.minDistanceFromPlayerSquared;
/*    */   }
/*    */   
/*    */   public double getTargetDistanceFromPlayerSquared() {
/* 43 */     return this.targetDistanceFromPlayerSquared;
/*    */   }
/*    */   
/*    */   public double getBeaconRadius() {
/* 47 */     return this.spawn.getBeaconRadius();
/*    */   }
/*    */   
/*    */   public double getSpawnRadius() {
/* 51 */     return this.spawn.getSpawnRadius();
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public RoleSpawnParameters pickRole(Random chanceProvider) {
/* 56 */     if (this.weightedRoles.size() == 0) {
/* 57 */       return null;
/*    */     }
/* 59 */     return (RoleSpawnParameters)this.weightedRoles.get(chanceProvider);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\wrappers\BeaconSpawnWrapper.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */