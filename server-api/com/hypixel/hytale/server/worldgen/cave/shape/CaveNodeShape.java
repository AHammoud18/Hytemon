/*    */ package com.hypixel.hytale.server.worldgen.cave.shape;
/*    */ 
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.server.worldgen.cave.Cave;
/*    */ import com.hypixel.hytale.server.worldgen.cave.element.CaveNode;
/*    */ import com.hypixel.hytale.server.worldgen.chunk.ChunkGeneratorExecution;
/*    */ import com.hypixel.hytale.server.worldgen.util.bounds.IWorldBounds;
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface CaveNodeShape
/*    */ {
/*    */   Vector3d getStart();
/*    */   
/*    */   Vector3d getEnd();
/*    */   
/*    */   Vector3d getAnchor(Vector3d paramVector3d, double paramDouble1, double paramDouble2, double paramDouble3);
/*    */   
/*    */   IWorldBounds getBounds();
/*    */   
/*    */   boolean shouldReplace(int paramInt1, double paramDouble1, double paramDouble2, int paramInt2);
/*    */   
/*    */   double getFloorPosition(int paramInt, double paramDouble1, double paramDouble2);
/*    */   
/*    */   double getCeilingPosition(int paramInt, double paramDouble1, double paramDouble2);
/*    */   
/*    */   void populateChunk(int paramInt, ChunkGeneratorExecution paramChunkGeneratorExecution, Cave paramCave, CaveNode paramCaveNode, Random paramRandom);
/*    */   
/*    */   default boolean hasGeometry() {
/* 33 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\cave\shape\CaveNodeShape.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */