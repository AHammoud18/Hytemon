/*    */ package com.hypixel.hytale.server.worldgen.cache;
/*    */ 
/*    */ import com.hypixel.hytale.server.worldgen.cave.Cave;
/*    */ import com.hypixel.hytale.server.worldgen.cave.CaveType;
/*    */ import com.hypixel.hytale.server.worldgen.chunk.ChunkGenerator;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CaveGeneratorCache
/*    */   extends ExtendedCoordinateCache<CaveType, Cave>
/*    */ {
/*    */   public CaveGeneratorCache(@Nonnull CaveFunction caveFunction, int maxSize, long expireAfterSeconds) {
/* 18 */     super(caveFunction::compute, null, maxSize, expireAfterSeconds);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   protected ExtendedCoordinateCache.ExtendedCoordinateKey<CaveType> localKey() {
/* 27 */     return (ChunkGenerator.getResource()).cacheCaveCoordinateKey;
/*    */   }
/*    */   
/*    */   @FunctionalInterface
/*    */   public static interface CaveFunction {
/*    */     Cave compute(CaveType param1CaveType, int param1Int1, int param1Int2, int param1Int3);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\cache\CaveGeneratorCache.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */