/*    */ package com.hypixel.hytale.server.worldgen.util.bounds;
/*    */ 
/*    */ import com.hypixel.hytale.math.util.ChunkUtil;
/*    */ import com.hypixel.hytale.server.core.prefab.PrefabRotation;
/*    */ import java.util.Random;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface IChunkBounds
/*    */ {
/*    */   int getLowBoundX();
/*    */   
/*    */   int getLowBoundZ();
/*    */   
/*    */   int getHighBoundX();
/*    */   
/*    */   int getHighBoundZ();
/*    */   
/*    */   default int getLowBoundX(@Nonnull PrefabRotation rotation) {
/* 24 */     return Math.min(rotation.getX(getLowBoundX(), getLowBoundZ()), rotation.getX(getHighBoundX(), getHighBoundZ()));
/*    */   }
/*    */   
/*    */   default int getLowBoundZ(@Nonnull PrefabRotation rotation) {
/* 28 */     return Math.min(rotation.getZ(getLowBoundX(), getLowBoundZ()), rotation.getZ(getHighBoundX(), getHighBoundZ()));
/*    */   }
/*    */   
/*    */   default int getHighBoundX(@Nonnull PrefabRotation rotation) {
/* 32 */     return Math.max(rotation.getX(getLowBoundX(), getLowBoundZ()), rotation.getX(getHighBoundX(), getHighBoundZ()));
/*    */   }
/*    */   
/*    */   default int getHighBoundZ(@Nonnull PrefabRotation rotation) {
/* 36 */     return Math.max(rotation.getZ(getLowBoundX(), getLowBoundZ()), rotation.getZ(getHighBoundX(), getHighBoundZ()));
/*    */   }
/*    */   
/*    */   default boolean intersectsChunk(long chunkIndex) {
/* 40 */     return intersectsChunk(ChunkUtil.xOfChunkIndex(chunkIndex), ChunkUtil.zOfChunkIndex(chunkIndex));
/*    */   }
/*    */   
/*    */   default boolean intersectsChunk(int chunkX, int chunkZ) {
/* 44 */     return (ChunkUtil.maxBlock(chunkX) >= getLowBoundX() && 
/* 45 */       ChunkUtil.minBlock(chunkX) <= getHighBoundX() && 
/* 46 */       ChunkUtil.maxBlock(chunkZ) >= getLowBoundZ() && 
/* 47 */       ChunkUtil.minBlock(chunkZ) <= getHighBoundZ());
/*    */   }
/*    */   
/*    */   default int randomX(@Nonnull Random random) {
/* 51 */     return random.nextInt(getHighBoundX() - getLowBoundX()) + getLowBoundX();
/*    */   }
/*    */   
/*    */   default int randomZ(@Nonnull Random random) {
/* 55 */     return random.nextInt(getHighBoundZ() - getLowBoundZ()) + getLowBoundZ();
/*    */   }
/*    */   
/*    */   default double fractionX(double d) {
/* 59 */     return (getHighBoundX() - getLowBoundX()) * d + getLowBoundX();
/*    */   }
/*    */   
/*    */   default double fractionZ(double d) {
/* 63 */     return (getHighBoundZ() - getLowBoundZ()) * d + getLowBoundZ();
/*    */   }
/*    */   
/*    */   default int getLowChunkX() {
/* 67 */     return ChunkUtil.chunkCoordinate(getLowBoundX());
/*    */   }
/*    */   
/*    */   default int getLowChunkZ() {
/* 71 */     return ChunkUtil.chunkCoordinate(getLowBoundZ());
/*    */   }
/*    */   
/*    */   default int getHighChunkX() {
/* 75 */     return ChunkUtil.chunkCoordinate(getHighBoundX());
/*    */   }
/*    */   
/*    */   default int getHighChunkZ() {
/* 79 */     return ChunkUtil.chunkCoordinate(getHighBoundZ());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldge\\util\bounds\IChunkBounds.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */