/*    */ package com.hypixel.hytale.server.worldgen.util.bounds;
/*    */ 
/*    */ import com.hypixel.hytale.math.util.ChunkUtil;
/*    */ import java.util.Random;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public interface IWorldBounds
/*    */   extends IChunkBounds
/*    */ {
/*    */   int getLowBoundY();
/*    */   
/*    */   int getHighBoundY();
/*    */   
/*    */   default boolean intersectsChunk(long chunkIndex) {
/* 15 */     return intersectsChunk(ChunkUtil.xOfChunkIndex(chunkIndex), ChunkUtil.zOfChunkIndex(chunkIndex));
/*    */   }
/*    */   
/*    */   default int randomY(@Nonnull Random random) {
/* 19 */     return random.nextInt(getHighBoundY() - getLowBoundY()) + getLowBoundY();
/*    */   }
/*    */   
/*    */   default double fractionY(double d) {
/* 23 */     return (getHighBoundY() - getLowBoundY()) * d + getLowBoundY();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldge\\util\bounds\IWorldBounds.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */