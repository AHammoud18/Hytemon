/*     */ package com.hypixel.hytale.server.worldgen.chunk.populator;
/*     */ 
/*     */ import com.hypixel.hytale.math.util.FastRandom;
/*     */ import com.hypixel.hytale.math.util.HashUtil;
/*     */ import com.hypixel.hytale.procedurallib.condition.ConstantIntCondition;
/*     */ import com.hypixel.hytale.procedurallib.condition.IBlockFluidCondition;
/*     */ import com.hypixel.hytale.server.core.universe.world.worldgen.GeneratedBlockChunk;
/*     */ import com.hypixel.hytale.server.worldgen.biome.Biome;
/*     */ import com.hypixel.hytale.server.worldgen.chunk.ChunkGenerator;
/*     */ import com.hypixel.hytale.server.worldgen.chunk.ChunkGeneratorExecution;
/*     */ import com.hypixel.hytale.server.worldgen.chunk.HeightThresholdInterpolator;
/*     */ import com.hypixel.hytale.server.worldgen.container.CoverContainer;
/*     */ import com.hypixel.hytale.server.worldgen.container.LayerContainer;
/*     */ import com.hypixel.hytale.server.worldgen.container.WaterContainer;
/*     */ import com.hypixel.hytale.server.worldgen.util.BlockFluidEntry;
/*     */ import com.hypixel.hytale.server.worldgen.util.NoiseBlockArray;
/*     */ import it.unimi.dsi.fastutil.ints.IntList;
/*     */ import java.util.Random;
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
/*     */ public class BlockPopulator
/*     */ {
/*     */   public static void populate(int seed, @Nonnull ChunkGeneratorExecution execution) {
/*  33 */     FastRandom fastRandom = new FastRandom(HashUtil.hash(seed, execution.getX(), execution.getZ(), 5647422603192711886L));
/*     */     
/*  35 */     for (int cx = 0; cx < 32; cx++) {
/*  36 */       for (int cz = 0; cz < 32; cz++) {
/*  37 */         generateBlockColumn(seed, execution, cx, cz, (Random)fastRandom);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void generateBlockColumn(int seed, @Nonnull ChunkGeneratorExecution execution, int cx, int cz, @Nonnull Random random) {
/*  43 */     HeightThresholdInterpolator interpolator = execution.getInterpolator();
/*  44 */     IntList surfaceBlockList = (ChunkGenerator.getResource()).coverArray;
/*     */     
/*  46 */     Biome biome = execution.zoneBiomeResult(cx, cz).getBiome();
/*  47 */     LayerContainer layerContainer = biome.getLayerContainer();
/*  48 */     int x = execution.globalX(cx);
/*  49 */     int z = execution.globalZ(cz);
/*  50 */     double heightmapNoise = interpolator.getHeightNoise(cx, cz);
/*     */     
/*  52 */     BlockFluidEntry filling = layerContainer.getFilling();
/*  53 */     int fillingEnvironment = layerContainer.getFillingEnvironment();
/*  54 */     int highest = 0;
/*  55 */     int min = interpolator.getLowestNonOne(cx, cz);
/*  56 */     int y = interpolator.getHighestNonZero(cx, cz);
/*  57 */     boolean empty = true;
/*     */     
/*  59 */     for (; y >= min; y--) {
/*  60 */       double threshold = interpolator.getHeightThreshold(seed, x, z, y);
/*  61 */       if (threshold > heightmapNoise || threshold == 1.0D) {
/*  62 */         if (y > highest) highest = y; 
/*  63 */         execution.setBlock(cx, y, cz, (byte)1, filling, fillingEnvironment);
/*  64 */         if (empty) {
/*  65 */           surfaceBlockList.add(y);
/*  66 */           empty = false;
/*     */         } 
/*     */       } else {
/*  69 */         empty = true;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  74 */     if (empty) surfaceBlockList.add(y); 
/*  75 */     if (y > highest) highest = y; 
/*  76 */     for (; y >= 0; y--) {
/*  77 */       execution.setBlock(cx, y, cz, (byte)1, filling);
/*     */     }
/*     */ 
/*     */     
/*  81 */     execution.getChunkGenerator().putHeight(seed, x, z, highest);
/*     */     
/*  83 */     LayerPopulator.generateLayers(seed, execution, cx, cz, x, z, biome, surfaceBlockList);
/*  84 */     generateCovers(seed, execution, cx, cz, x, z, random, biome, surfaceBlockList);
/*  85 */     surfaceBlockList.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void generateCovers(int seed, @Nonnull ChunkGeneratorExecution execution, int cx, int cz, int x, int z, @Nonnull Random random, @Nonnull Biome biome, @Nonnull IntList surfaceBlockList) {
/*  91 */     CoverContainer coverContainer = biome.getCoverContainer();
/*     */     
/*  93 */     int size = surfaceBlockList.size();
/*     */ 
/*     */     
/*  96 */     if (size == 0) {
/*     */       return;
/*     */     }
/*  99 */     for (WaterContainer.Entry waterContainer : biome.getWaterContainer().getEntries()) {
/* 100 */       for (CoverContainer.CoverContainerEntry coverContainerEntry : coverContainer.getEntries()) {
/* 101 */         if (coverContainerEntry.isOnWater() && 
/* 102 */           isMatchingCoverColumn(seed, coverContainerEntry, random, x, z)) {
/*     */           
/* 104 */           int y = waterContainer.getMax(seed, x, z) + 1;
/* 105 */           if (isMatchingCoverHeight(seed, coverContainerEntry, random, x, y, z) && 
/* 106 */             isMatchingParentCover(execution, coverContainerEntry, cx, cz, y, waterContainer.getBlock(), waterContainer.getFluid())) {
/*     */             
/* 108 */             CoverContainer.CoverContainerEntry.CoverContainerEntryPart coverEntry = coverContainerEntry.get(random);
/* 109 */             execution.setBlock(cx, y + coverEntry.getOffset(), cz, (byte)3, coverEntry.getEntry());
/* 110 */             execution.setFluid(cx, y + coverEntry.getOffset(), cz, (byte)3, coverEntry.getEntry().fluidId());
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 116 */     for (int i = 0; i < size; i++) {
/* 117 */       int y = surfaceBlockList.getInt(i) + 1;
/* 118 */       for (CoverContainer.CoverContainerEntry coverContainerEntry : coverContainer.getEntries()) {
/* 119 */         if (!coverContainerEntry.isOnWater() && 
/* 120 */           isMatchingParentCover(execution, coverContainerEntry, cx, cz, y, 0, 0) && 
/* 121 */           isMatchingCoverColumn(seed, coverContainerEntry, random, x, z) && 
/* 122 */           isMatchingCoverHeight(seed, coverContainerEntry, random, x, y, z)) {
/* 123 */           CoverContainer.CoverContainerEntry.CoverContainerEntryPart coverEntry = coverContainerEntry.get(random);
/* 124 */           execution.setBlock(cx, y + coverEntry.getOffset(), cz, (byte)3, coverEntry.getEntry());
/* 125 */           execution.setFluid(cx, y + coverEntry.getOffset(), cz, (byte)3, coverEntry.getEntry().fluidId());
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isMatchingParentCover(@Nonnull ChunkGeneratorExecution execution, @Nonnull CoverContainer.CoverContainerEntry coverContainerEntry, int cx, int cz, int y, int defaultId, int defaultFluidId) {
/* 136 */     if (y <= 0 || y >= 320) return false;
/*     */     
/* 138 */     IBlockFluidCondition parentCondition = coverContainerEntry.getParentCondition();
/* 139 */     if (parentCondition == ConstantIntCondition.DEFAULT_TRUE) return true; 
/* 140 */     if (parentCondition == ConstantIntCondition.DEFAULT_FALSE) return false;
/*     */     
/* 142 */     GeneratedBlockChunk chunk = execution.getChunk();
/* 143 */     int block = chunk.getBlock(cx, y - 1, cz);
/*     */     
/* 145 */     if (block == 0) block = defaultId; 
/* 146 */     int fluid = execution.getFluid(cx, y - 1, cz);
/* 147 */     if (fluid == 0) fluid = defaultFluidId;
/*     */     
/* 149 */     return parentCondition.eval(block, fluid);
/*     */   }
/*     */   
/*     */   private static boolean isMatchingCoverColumn(int seed, @Nonnull CoverContainer.CoverContainerEntry coverContainerEntry, @Nonnull Random random, int x, int z) {
/* 153 */     return (random.nextDouble() < coverContainerEntry.getCoverDensity() && coverContainerEntry
/* 154 */       .getMapCondition().eval(seed, x, z));
/*     */   }
/*     */   
/*     */   private static boolean isMatchingCoverHeight(int seed, @Nonnull CoverContainer.CoverContainerEntry coverContainerEntry, Random random, int x, int y, int z) {
/* 158 */     return coverContainerEntry.getHeightCondition().eval(seed, x, z, y, random);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class LayerPopulator
/*     */   {
/*     */     static void generateLayers(int seed, @Nonnull ChunkGeneratorExecution execution, int cx, int cz, int x, int z, @Nonnull Biome biome, @Nonnull IntList surfaceBlockList) {
/* 168 */       generateStaticLayers(seed, execution, cx, cz, x, z, biome);
/* 169 */       generateDynamicLayers(seed, execution, cx, cz, x, z, biome, surfaceBlockList);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static void generateDynamicLayers(int seed, @Nonnull ChunkGeneratorExecution execution, int cx, int cz, int x, int z, @Nonnull Biome biome, @Nonnull IntList surfaceBlockList) {
/* 177 */       LayerContainer layers = biome.getLayerContainer();
/* 178 */       for (int i = 0, size = surfaceBlockList.size(); i < size; i++) {
/* 179 */         int surfaceY = surfaceBlockList.getInt(i);
/* 180 */         int y = surfaceY;
/* 181 */         int maxY = surfaceY;
/*     */         
/* 183 */         label29: for (LayerContainer.DynamicLayer layer : layers.getDynamicLayers()) {
/* 184 */           LayerContainer.DynamicLayerEntry entry = (LayerContainer.DynamicLayerEntry)layer.getActiveEntry(seed, x, z);
/* 185 */           if (entry != null) {
/*     */             
/* 187 */             int environmentId = layer.getEnvironmentId();
/* 188 */             y += layer.getOffset(seed, x, z);
/* 189 */             maxY = Math.max(maxY, y);
/* 190 */             NoiseBlockArray blockArray = entry.getBlockArray();
/* 191 */             for (NoiseBlockArray.Entry blockArrayEntry : blockArray.getEntries()) {
/* 192 */               int repetitions = blockArrayEntry.getRepetitions(seed, x, z);
/* 193 */               for (int j = 0; j < repetitions; ) {
/* 194 */                 if (y > surfaceY || execution.getBlock(cx, y, cz) != 0) {
/* 195 */                   execution.setBlock(cx, y, cz, (byte)2, blockArrayEntry.getBlockEntry(), environmentId);
/* 196 */                   execution.setFluid(cx, y, cz, (byte)2, blockArrayEntry.getBlockEntry().fluidId(), environmentId);
/* 197 */                   y--; j++;
/*     */                 } 
/*     */                 break label29;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/* 204 */         if (maxY > surfaceY) {
/* 205 */           surfaceBlockList.set(i, maxY);
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static void generateStaticLayers(int seed, @Nonnull ChunkGeneratorExecution execution, int cx, int cz, int x, int z, @Nonnull Biome biome) {
/* 214 */       LayerContainer layers = biome.getLayerContainer();
/* 215 */       for (LayerContainer.StaticLayer layer : layers.getStaticLayers()) {
/* 216 */         LayerContainer.StaticLayerEntry entry = (LayerContainer.StaticLayerEntry)layer.getActiveEntry(seed, x, z);
/* 217 */         if (entry != null) {
/*     */           
/* 219 */           int environmentId = layer.getEnvironmentId();
/* 220 */           NoiseBlockArray.Entry[] blockEntries = entry.getBlockArray().getEntries();
/* 221 */           int min = Math.max(entry.getMinInt(seed, x, z), 0);
/* 222 */           int max = Math.min(entry.getMaxInt(seed, x, z), 320);
/*     */           
/* 224 */           int layerY = entry.getMaxInt(seed, x, z);
/* 225 */           BlockFluidEntry lastBlock = null;
/* 226 */           for (NoiseBlockArray.Entry blockEntry : blockEntries) {
/* 227 */             int repetitions = blockEntry.getRepetitions(seed, x, z);
/* 228 */             if (repetitions > 0) {
/* 229 */               BlockFluidEntry block = blockEntry.getBlockEntry();
/* 230 */               lastBlock = block;
/* 231 */               for (int i = 0; i < repetitions; i++) {
/* 232 */                 int currentBlock = execution.getBlock(cx, --layerY, cz);
/* 233 */                 if (currentBlock != 0) {
/* 234 */                   execution.setBlock(cx, layerY, cz, (byte)2, block, environmentId);
/* 235 */                   execution.setFluid(cx, layerY, cz, (byte)2, block.fluidId(), environmentId);
/*     */                 } 
/* 237 */                 if (layerY <= min) {
/*     */                   return;
/*     */                 }
/*     */               } 
/*     */             } 
/*     */           } 
/* 243 */           if (blockEntries.length == 0 && environmentId != Integer.MIN_VALUE) {
/* 244 */             for (int y = max - 1; y >= min; y--) {
/* 245 */               execution.setEnvironment(cx, y, cz, environmentId);
/*     */             }
/*     */           }
/*     */           
/* 249 */           if (lastBlock != null)
/* 250 */             while (layerY > min) {
/* 251 */               int currentBlock = execution.getBlock(cx, --layerY, cz);
/* 252 */               if (currentBlock != 0) {
/* 253 */                 execution.setBlock(cx, layerY, cz, (byte)2, lastBlock);
/* 254 */                 execution.setFluid(cx, layerY, cz, (byte)2, lastBlock.fluidId());
/*     */               } 
/*     */             }  
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\chunk\populator\BlockPopulator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */