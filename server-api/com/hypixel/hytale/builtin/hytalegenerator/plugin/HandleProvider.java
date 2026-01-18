/*    */ package com.hypixel.hytale.builtin.hytalegenerator.plugin;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.chunkgenerator.ChunkRequest;
/*    */ import com.hypixel.hytale.math.vector.Transform;
/*    */ import com.hypixel.hytale.server.core.universe.world.worldgen.IWorldGen;
/*    */ import com.hypixel.hytale.server.core.universe.world.worldgen.WorldGenLoadException;
/*    */ import com.hypixel.hytale.server.core.universe.world.worldgen.provider.IWorldGenProvider;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class HandleProvider
/*    */   implements IWorldGenProvider {
/*    */   public static final String ID = "HytaleGenerator";
/*    */   public static final String DEFAULT_WORLD_STRUCTURE_NAME = "Default";
/* 15 */   public static final Transform DEFAULT_PLAYER_SPAWN = new Transform(0.0D, 140.0D, 0.0D);
/*    */   
/*    */   @Nonnull
/*    */   private final HytaleGenerator plugin;
/*    */   @Nonnull
/* 20 */   private String worldStructureName = "Default";
/*    */   @Nonnull
/* 22 */   private Transform playerSpawn = DEFAULT_PLAYER_SPAWN;
/*    */ 
/*    */   
/*    */   public HandleProvider(@Nonnull HytaleGenerator plugin) {
/* 26 */     this.plugin = plugin;
/*    */   }
/*    */   
/*    */   public void setWorldStructureName(@Nullable String worldStructureName) {
/* 30 */     this.worldStructureName = worldStructureName;
/*    */   }
/*    */   
/*    */   public void setPlayerSpawn(@Nullable Transform worldSpawn) {
/* 34 */     if (worldSpawn == null) {
/* 35 */       this.playerSpawn = DEFAULT_PLAYER_SPAWN;
/*    */       return;
/*    */     } 
/* 38 */     this.playerSpawn = worldSpawn.clone();
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public String getWorldStructureName() {
/* 43 */     return this.worldStructureName;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public Transform getPlayerSpawn() {
/* 48 */     return this.playerSpawn;
/*    */   }
/*    */ 
/*    */   
/*    */   public IWorldGen getGenerator() throws WorldGenLoadException {
/* 53 */     return new Handle(this.plugin, new ChunkRequest.GeneratorProfile(this.worldStructureName, this.playerSpawn.clone(), 0));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\plugin\HandleProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */