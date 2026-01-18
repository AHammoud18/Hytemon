/*    */ package com.hypixel.hytale.server.worldgen.chunk.populator;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*    */ import com.hypixel.hytale.server.core.asset.type.fluid.FluidTicker;
/*    */ import com.hypixel.hytale.server.worldgen.biome.Biome;
/*    */ import com.hypixel.hytale.server.worldgen.chunk.ChunkGeneratorExecution;
/*    */ import com.hypixel.hytale.server.worldgen.container.WaterContainer;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WaterPopulator
/*    */ {
/*    */   public static void populate(int seed, @Nonnull ChunkGeneratorExecution execution) {
/* 18 */     for (int cx = 0; cx < 32; cx++) {
/* 19 */       for (int cz = 0; cz < 32; cz++) {
/* 20 */         submergeColumn(seed, cx, cz, execution);
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private static void submergeColumn(int seed, int cx, int cz, @Nonnull ChunkGeneratorExecution execution) {
/* 27 */     Biome biome = execution.zoneBiomeResult(cx, cz).getBiome();
/* 28 */     int x = execution.globalX(cx), z = execution.globalZ(cz);
/*    */     
/* 30 */     WaterContainer waterContainer = biome.getWaterContainer();
/* 31 */     for (WaterContainer.Entry waterEntry : waterContainer.getEntries()) {
/* 32 */       if (waterEntry.shouldPopulate(seed, x, z)) {
/*    */         
/* 34 */         int waterMin = waterEntry.getMin(seed, x, z);
/* 35 */         int waterMax = waterEntry.getMax(seed, x, z);
/* 36 */         if (waterMin <= waterMax) {
/*    */           
/* 38 */           int blockId = waterEntry.getBlock();
/* 39 */           int fluidId = waterEntry.getFluid();
/*    */           
/* 41 */           for (int y = waterMin; y <= waterMax; y++) {
/* 42 */             submergeBlock(cx, y, cz, blockId, fluidId, execution);
/*    */           }
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   private static void submergeBlock(int cx, int y, int cz, int blockId, int fluidId, @Nonnull ChunkGeneratorExecution execution) {
/*    */     int currentBlockId;
/* 51 */     byte rawPriority = execution.getPriorityChunk().getRaw(cx, y, cz);
/* 52 */     byte priority = (byte)(rawPriority & 0x1F);
/*    */ 
/*    */     
/* 55 */     if (priority >= 4 && (rawPriority & 0x20) == 0) {
/*    */       return;
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 62 */     if (blockId != 0 && priority < 4) {
/* 63 */       execution.setBlock(cx, y, cz, (byte)-1, blockId);
/* 64 */       currentBlockId = blockId;
/*    */     } else {
/* 66 */       currentBlockId = execution.getBlock(cx, y, cz);
/*    */     } 
/*    */ 
/*    */     
/* 70 */     BlockType blockType = (BlockType)BlockType.getAssetMap().getAsset(currentBlockId);
/* 71 */     if (blockType == null || FluidTicker.isSolid(blockType)) {
/*    */       return;
/*    */     }
/*    */     
/* 75 */     execution.setFluid(cx, y, cz, (byte)-1, fluidId);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\chunk\populator\WaterPopulator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */