/*     */ package com.hypixel.hytale.server.worldgen.chunk.populator;
/*     */ 
/*     */ import com.hypixel.hytale.server.worldgen.biome.Biome;
/*     */ import com.hypixel.hytale.server.worldgen.chunk.ChunkGeneratorExecution;
/*     */ import com.hypixel.hytale.server.worldgen.container.LayerContainer;
/*     */ import com.hypixel.hytale.server.worldgen.util.BlockFluidEntry;
/*     */ import com.hypixel.hytale.server.worldgen.util.NoiseBlockArray;
/*     */ import it.unimi.dsi.fastutil.ints.IntList;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class LayerPopulator
/*     */ {
/*     */   static void generateLayers(int seed, @Nonnull ChunkGeneratorExecution execution, int cx, int cz, int x, int z, @Nonnull Biome biome, @Nonnull IntList surfaceBlockList) {
/* 168 */     generateStaticLayers(seed, execution, cx, cz, x, z, biome);
/* 169 */     generateDynamicLayers(seed, execution, cx, cz, x, z, biome, surfaceBlockList);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void generateDynamicLayers(int seed, @Nonnull ChunkGeneratorExecution execution, int cx, int cz, int x, int z, @Nonnull Biome biome, @Nonnull IntList surfaceBlockList) {
/* 177 */     LayerContainer layers = biome.getLayerContainer();
/* 178 */     for (int i = 0, size = surfaceBlockList.size(); i < size; i++) {
/* 179 */       int surfaceY = surfaceBlockList.getInt(i);
/* 180 */       int y = surfaceY;
/* 181 */       int maxY = surfaceY;
/*     */       
/* 183 */       label29: for (LayerContainer.DynamicLayer layer : layers.getDynamicLayers()) {
/* 184 */         LayerContainer.DynamicLayerEntry entry = (LayerContainer.DynamicLayerEntry)layer.getActiveEntry(seed, x, z);
/* 185 */         if (entry != null) {
/*     */           
/* 187 */           int environmentId = layer.getEnvironmentId();
/* 188 */           y += layer.getOffset(seed, x, z);
/* 189 */           maxY = Math.max(maxY, y);
/* 190 */           NoiseBlockArray blockArray = entry.getBlockArray();
/* 191 */           for (NoiseBlockArray.Entry blockArrayEntry : blockArray.getEntries()) {
/* 192 */             int repetitions = blockArrayEntry.getRepetitions(seed, x, z);
/* 193 */             for (int j = 0; j < repetitions; ) {
/* 194 */               if (y > surfaceY || execution.getBlock(cx, y, cz) != 0) {
/* 195 */                 execution.setBlock(cx, y, cz, (byte)2, blockArrayEntry.getBlockEntry(), environmentId);
/* 196 */                 execution.setFluid(cx, y, cz, (byte)2, blockArrayEntry.getBlockEntry().fluidId(), environmentId);
/* 197 */                 y--; j++;
/*     */               } 
/*     */               break label29;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/* 204 */       if (maxY > surfaceY) {
/* 205 */         surfaceBlockList.set(i, maxY);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void generateStaticLayers(int seed, @Nonnull ChunkGeneratorExecution execution, int cx, int cz, int x, int z, @Nonnull Biome biome) {
/* 214 */     LayerContainer layers = biome.getLayerContainer();
/* 215 */     for (LayerContainer.StaticLayer layer : layers.getStaticLayers()) {
/* 216 */       LayerContainer.StaticLayerEntry entry = (LayerContainer.StaticLayerEntry)layer.getActiveEntry(seed, x, z);
/* 217 */       if (entry != null) {
/*     */         
/* 219 */         int environmentId = layer.getEnvironmentId();
/* 220 */         NoiseBlockArray.Entry[] blockEntries = entry.getBlockArray().getEntries();
/* 221 */         int min = Math.max(entry.getMinInt(seed, x, z), 0);
/* 222 */         int max = Math.min(entry.getMaxInt(seed, x, z), 320);
/*     */         
/* 224 */         int layerY = entry.getMaxInt(seed, x, z);
/* 225 */         BlockFluidEntry lastBlock = null;
/* 226 */         for (NoiseBlockArray.Entry blockEntry : blockEntries) {
/* 227 */           int repetitions = blockEntry.getRepetitions(seed, x, z);
/* 228 */           if (repetitions > 0) {
/* 229 */             BlockFluidEntry block = blockEntry.getBlockEntry();
/* 230 */             lastBlock = block;
/* 231 */             for (int i = 0; i < repetitions; i++) {
/* 232 */               int currentBlock = execution.getBlock(cx, --layerY, cz);
/* 233 */               if (currentBlock != 0) {
/* 234 */                 execution.setBlock(cx, layerY, cz, (byte)2, block, environmentId);
/* 235 */                 execution.setFluid(cx, layerY, cz, (byte)2, block.fluidId(), environmentId);
/*     */               } 
/* 237 */               if (layerY <= min) {
/*     */                 return;
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/* 243 */         if (blockEntries.length == 0 && environmentId != Integer.MIN_VALUE) {
/* 244 */           for (int y = max - 1; y >= min; y--) {
/* 245 */             execution.setEnvironment(cx, y, cz, environmentId);
/*     */           }
/*     */         }
/*     */         
/* 249 */         if (lastBlock != null)
/* 250 */           while (layerY > min) {
/* 251 */             int currentBlock = execution.getBlock(cx, --layerY, cz);
/* 252 */             if (currentBlock != 0) {
/* 253 */               execution.setBlock(cx, layerY, cz, (byte)2, lastBlock);
/* 254 */               execution.setFluid(cx, layerY, cz, (byte)2, lastBlock.fluidId());
/*     */             } 
/*     */           }  
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\chunk\populator\BlockPopulator$LayerPopulator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */