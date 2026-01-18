/*     */ package com.hypixel.hytale.server.worldgen.chunk.populator;
/*     */ 
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.util.HashUtil;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.procedurallib.condition.DefaultCoordinateCondition;
/*     */ import com.hypixel.hytale.procedurallib.condition.ICoordinateCondition;
/*     */ import com.hypixel.hytale.procedurallib.logic.point.IPointGenerator;
/*     */ import com.hypixel.hytale.server.core.prefab.PrefabRotation;
/*     */ import com.hypixel.hytale.server.worldgen.cave.Cave;
/*     */ import com.hypixel.hytale.server.worldgen.cave.CaveBlockPriorityModifier;
/*     */ import com.hypixel.hytale.server.worldgen.cave.CaveType;
/*     */ import com.hypixel.hytale.server.worldgen.cave.element.CaveNode;
/*     */ import com.hypixel.hytale.server.worldgen.cave.element.CavePrefab;
/*     */ import com.hypixel.hytale.server.worldgen.chunk.BlockPriorityModifier;
/*     */ import com.hypixel.hytale.server.worldgen.chunk.ChunkGenerator;
/*     */ import com.hypixel.hytale.server.worldgen.chunk.ChunkGeneratorExecution;
/*     */ import com.hypixel.hytale.server.worldgen.chunk.ZoneBiomeResult;
/*     */ import com.hypixel.hytale.server.worldgen.loader.WorldGenPrefabSupplier;
/*     */ import com.hypixel.hytale.server.worldgen.prefab.PrefabPasteUtil;
/*     */ import com.hypixel.hytale.server.worldgen.util.condition.BlockMaskCondition;
/*     */ import com.hypixel.hytale.server.worldgen.zone.Zone;
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
/*     */ public class CavePopulator
/*     */ {
/*     */   public static void populate(int seed, @Nonnull ChunkGeneratorExecution execution) {
/*  36 */     for (Zone zone : execution.getChunkGenerator().getZonePatternProvider().getZones()) {
/*  37 */       if (zone.caveGenerator() != null) {
/*  38 */         for (CaveType caveType : zone.caveGenerator().getCaveTypes()) {
/*  39 */           IPointGenerator cavePointGenerator = caveType.getEntryPointGenerator();
/*  40 */           cavePointGenerator.collect(seed, 
/*  41 */               ChunkUtil.minBlock(execution.getX()) - caveType.getMaximumSize(), 
/*  42 */               ChunkUtil.minBlock(execution.getZ()) - caveType.getMaximumSize(), 
/*  43 */               ChunkUtil.maxBlock(execution.getX()) + caveType.getMaximumSize(), 
/*  44 */               ChunkUtil.maxBlock(execution.getZ()) + caveType.getMaximumSize(), (x, z) -> run(seed, x, z, execution, zone, caveType));
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void run(int seed, double dx, double dz, @Nonnull ChunkGeneratorExecution execution, Zone zone, @Nonnull CaveType caveType) {
/*  55 */     ChunkGenerator chunkGenerator = execution.getChunkGenerator();
/*  56 */     int x = MathUtil.floor(dx), z = MathUtil.floor(dz);
/*  57 */     ZoneBiomeResult result = chunkGenerator.getZoneBiomeResultAt(seed, x, z);
/*  58 */     if (result.getZoneResult().getZone() == zone && caveType.isEntryThreshold(seed, x, z) && 
/*  59 */       isMatchingHeightThreshold(seed, x, z, chunkGenerator, caveType)) {
/*  60 */       populate(seed, execution, execution.getChunkGenerator().getCave(caveType, seed, x, z));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void populate(int seed, @Nonnull ChunkGeneratorExecution execution, @Nonnull Cave cave) {
/*  67 */     long chunkIndex = execution.getIndex();
/*     */     
/*  69 */     if (!cave.contains(chunkIndex))
/*     */       return; 
/*  71 */     int chunkX = execution.getX();
/*  72 */     int chunkZ = execution.getZ();
/*     */     
/*  74 */     Random random = new Random();
/*  75 */     int environment = cave.getCaveType().getEnvironment();
/*     */ 
/*     */     
/*  78 */     execution.setPriorityModifier(CaveBlockPriorityModifier.INSTANCE);
/*     */ 
/*     */     
/*  81 */     for (CaveNode node : cave.getCaveNodes(chunkIndex)) {
/*  82 */       populateCaveNode(seed, execution, cave, node, random);
/*     */     }
/*     */ 
/*     */     
/*  86 */     execution.setPriorityModifier(BlockPriorityModifier.NONE);
/*     */     
/*  88 */     for (CaveNode node : cave.getCaveNodes(chunkIndex)) {
/*  89 */       for (CavePrefab prefab : node.getCavePrefabs()) {
/*  90 */         if (prefab.getBounds().intersectsChunk(chunkX, chunkZ)) {
/*  91 */           populatePrefab(seed, environment, execution, cave, node, prefab);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void populateCaveNode(int seed, @Nonnull ChunkGeneratorExecution execution, @Nonnull Cave cave, @Nonnull CaveNode caveNode, @Nonnull Random random) {
/*  98 */     random.setSeed((seed + caveNode.getSeedOffset()));
/*     */     
/* 100 */     caveNode.getShape().populateChunk(seed, execution, cave, caveNode, random);
/*     */ 
/*     */     
/* 103 */     if (execution.getChunkGenerator().getBenchmark().isEnabled()) {
/* 104 */       int minX = caveNode.getBounds().getLowBoundX();
/* 105 */       int minZ = caveNode.getBounds().getLowBoundZ();
/* 106 */       if (ChunkUtil.isInsideChunk(execution.getX(), execution.getZ(), minX, minZ)) {
/* 107 */         execution.getChunkGenerator().getBenchmark().registerCaveNode("Cave\t" + cave.getCaveType().getName() + "\t" + caveNode.getCaveNodeType().getName());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void populatePrefab(int seed, int environment, @Nonnull ChunkGeneratorExecution execution, @Nonnull Cave cave, @Nonnull CaveNode node, @Nonnull CavePrefab prefab) {
/* 115 */     generatePrefabAt(seed, prefab
/* 116 */         .getX(), prefab.getZ(), prefab.getY(), environment, execution, cave, node, prefab
/*     */         
/* 118 */         .getConfiguration(), prefab
/* 119 */         .getPrefab(), prefab.getRotation());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void generatePrefabAt(int seed, int x, int z, int y, int environment, @Nonnull ChunkGeneratorExecution execution, @Nonnull Cave cave, @Nonnull CaveNode node, BlockMaskCondition configuration, @Nonnull WorldGenPrefabSupplier supplier, PrefabRotation rotation) {
/* 127 */     int cx = x - ChunkUtil.minBlock(execution.getX());
/* 128 */     int cz = z - ChunkUtil.minBlock(execution.getZ());
/* 129 */     long externalSeed = HashUtil.rehash(x, z) * -99562191L;
/*     */     
/* 131 */     boolean submerge = cave.getCaveType().isSubmerge();
/*     */     
/* 133 */     PrefabPasteUtil.PrefabPasteBuffer buffer = (ChunkGenerator.getResource()).prefabBuffer;
/* 134 */     buffer.setSeed(seed, externalSeed);
/* 135 */     buffer.blockMask = configuration;
/* 136 */     buffer.execution = execution;
/* 137 */     buffer.environmentId = environment;
/* 138 */     buffer.priority = submerge ? 39 : 7;
/*     */     
/* 140 */     if (execution.getChunkGenerator().getBenchmark().isEnabled() && ChunkUtil.isInsideChunkRelative(cx, cz)) {
/* 141 */       execution.getChunkGenerator().getBenchmark().registerPrefab("CavePrefab: " + cave
/* 142 */           .getCaveType().getName() + "\t" + node.getCaveNodeType().getName() + "\t" + supplier.getName());
/*     */     }
/*     */ 
/*     */     
/* 146 */     PrefabPasteUtil.generate(buffer, rotation, supplier, x, y, z, cx, cz);
/*     */   }
/*     */   
/*     */   private static boolean isMatchingHeightThreshold(int seed, int x, int z, @Nonnull ChunkGenerator chunkGenerator, @Nonnull CaveType caveType) {
/* 150 */     ICoordinateCondition heightCondition = caveType.getHeightCondition();
/* 151 */     if (heightCondition == DefaultCoordinateCondition.DEFAULT_TRUE) return true; 
/* 152 */     if (heightCondition == DefaultCoordinateCondition.DEFAULT_FALSE) return false;
/*     */     
/* 154 */     int height = chunkGenerator.getHeight(seed, x, z);
/* 155 */     return heightCondition.eval(seed + -173220171, x, height, z);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\chunk\populator\CavePopulator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */