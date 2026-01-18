/*    */ package com.hypixel.hytale.server.spawning.world.manager;
/*    */ 
/*    */ import com.hypixel.hytale.server.spawning.assets.spawns.config.NPCSpawn;
/*    */ import com.hypixel.hytale.server.spawning.assets.spawns.config.WorldNPCSpawn;
/*    */ import com.hypixel.hytale.server.spawning.wrappers.SpawnWrapper;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldSpawnWrapper
/*    */   extends SpawnWrapper<WorldNPCSpawn>
/*    */ {
/*    */   public WorldSpawnWrapper(@Nonnull WorldNPCSpawn spawn) {
/* 14 */     super(WorldNPCSpawn.getAssetMap().getIndex(spawn.getId()), (NPCSpawn)spawn);
/*    */   }
/*    */   
/*    */   public double getMoonPhaseWeightModifier(int moonPhase) {
/* 18 */     double[] moonPhaseWeights = ((WorldNPCSpawn)this.spawn).getMoonPhaseWeightModifiers();
/* 19 */     if (moonPhaseWeights == null) return 1.0D; 
/* 20 */     if (moonPhase >= moonPhaseWeights.length) return 0.0D; 
/* 21 */     return moonPhaseWeights[moonPhase];
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\world\manager\WorldSpawnWrapper.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */