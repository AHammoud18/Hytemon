/*    */ package com.hypixel.hytale.server.spawning.world;
/*    */ 
/*    */ import com.hypixel.hytale.math.util.MathUtil;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*    */ import com.hypixel.hytale.server.spawning.SpawningPlugin;
/*    */ import com.hypixel.hytale.server.spawning.util.ChunkColumnMask;
/*    */ import com.hypixel.hytale.server.spawning.util.RandomChunkColumnIterator;
/*    */ import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
/*    */ import it.unimi.dsi.fastutil.ints.IntSet;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChunkEnvironmentSpawnData
/*    */ {
/*    */   private IntSet possibleRoleTypes;
/* 18 */   private final IntSet unspawnableRoles = (IntSet)new IntOpenHashSet();
/*    */   
/*    */   private boolean processedAsUnspawnable;
/*    */   
/*    */   private RandomChunkColumnIterator randomChunkColumnIterator;
/*    */   
/*    */   private int segmentCount;
/*    */   
/*    */   private double expectedNPCs;
/*    */   
/*    */   public double getExpectedNPCs() {
/* 29 */     return this.expectedNPCs;
/*    */   }
/*    */   
/*    */   public RandomChunkColumnIterator getRandomChunkColumnIterator() {
/* 33 */     return this.randomChunkColumnIterator;
/*    */   }
/*    */   
/*    */   public void init(int environmentIndex, @Nonnull WorldChunk chunk) {
/* 37 */     this.randomChunkColumnIterator = new RandomChunkColumnIterator(new ChunkColumnMask(), chunk);
/* 38 */     this.possibleRoleTypes = SpawningPlugin.get().getRolesForEnvironment(environmentIndex);
/* 39 */     this.processedAsUnspawnable = false;
/*    */   }
/*    */   
/*    */   public void registerSegment(int x, int z) {
/* 43 */     this.randomChunkColumnIterator.getInitialPositions().set(x, z);
/* 44 */     this.segmentCount++;
/*    */   }
/*    */   
/*    */   public int getSegmentCount() {
/* 48 */     return this.segmentCount;
/*    */   }
/*    */   
/*    */   public void updateDensity(double density) {
/* 52 */     this.expectedNPCs = density * this.segmentCount / 1024.0D;
/*    */   }
/*    */   
/*    */   public double getWeight(double spawnedNPCs) {
/* 56 */     double missingNPCs = this.expectedNPCs - spawnedNPCs;
/* 57 */     return MathUtil.maxValue(0.0D, missingNPCs);
/*    */   }
/*    */   
/*    */   public boolean isFullyPopulated(double spawnedNPCs) {
/* 61 */     return (this.expectedNPCs <= spawnedNPCs);
/*    */   }
/*    */   
/*    */   public void markRoleAsUnspawnable(int roleIndex) {
/* 65 */     this.unspawnableRoles.add(roleIndex);
/*    */   }
/*    */   
/*    */   public boolean isRoleSpawnable(int roleIndex) {
/* 69 */     return !this.unspawnableRoles.contains(roleIndex);
/*    */   }
/*    */   
/*    */   public boolean allRolesUnspawnable() {
/* 73 */     return (this.unspawnableRoles.size() >= this.possibleRoleTypes.size());
/*    */   }
/*    */   
/*    */   public boolean wasProcessedAsUnspawnable() {
/* 77 */     return this.processedAsUnspawnable;
/*    */   }
/*    */   
/*    */   public void markProcessedAsUnspawnable() {
/* 81 */     this.processedAsUnspawnable = true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\world\ChunkEnvironmentSpawnData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */