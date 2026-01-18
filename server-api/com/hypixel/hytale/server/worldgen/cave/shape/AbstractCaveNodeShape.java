/*     */ package com.hypixel.hytale.server.worldgen.cave.shape;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.map.BlockTypeAssetMap;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.universe.world.worldgen.GeneratedBlockChunk;
/*     */ import com.hypixel.hytale.server.worldgen.cave.Cave;
/*     */ import com.hypixel.hytale.server.worldgen.cave.CaveNodeType;
/*     */ import com.hypixel.hytale.server.worldgen.cave.CaveType;
/*     */ import com.hypixel.hytale.server.worldgen.cave.element.CaveNode;
/*     */ import com.hypixel.hytale.server.worldgen.chunk.ChunkGeneratorExecution;
/*     */ import com.hypixel.hytale.server.worldgen.util.BlockFluidEntry;
/*     */ import com.hypixel.hytale.server.worldgen.util.bounds.IWorldBounds;
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
/*     */ public abstract class AbstractCaveNodeShape
/*     */   implements CaveNodeShape
/*     */ {
/*     */   public void populateChunk(int seed, @Nonnull ChunkGeneratorExecution execution, @Nonnull Cave cave, @Nonnull CaveNode node, @Nonnull Random random) {
/*  30 */     GeneratedBlockChunk chunk = execution.getChunk();
/*  31 */     BlockTypeAssetMap<String, BlockType> blockTypeMap = BlockType.getAssetMap();
/*     */     
/*  33 */     CaveType caveType = cave.getCaveType();
/*  34 */     CaveNodeType caveNodeType = node.getCaveNodeType();
/*  35 */     IWorldBounds shapeBounds = getBounds();
/*  36 */     boolean surfaceLimited = cave.getCaveType().isSurfaceLimited();
/*  37 */     int environment = node.getCaveNodeType().hasEnvironment() ? node.getCaveNodeType().getEnvironment() : caveType.getEnvironment();
/*     */     
/*  39 */     int chunkLowX = ChunkUtil.minBlock(execution.getX());
/*  40 */     int chunkLowZ = ChunkUtil.minBlock(execution.getZ());
/*  41 */     int chunkHighX = ChunkUtil.maxBlock(execution.getX());
/*  42 */     int chunkHighZ = ChunkUtil.maxBlock(execution.getZ());
/*     */     
/*  44 */     int minX = Math.max(chunkLowX, shapeBounds.getLowBoundX());
/*  45 */     int minY = shapeBounds.getLowBoundY();
/*  46 */     int minZ = Math.max(chunkLowZ, shapeBounds.getLowBoundZ());
/*  47 */     int maxX = Math.min(chunkHighX, shapeBounds.getHighBoundX());
/*  48 */     int maxY = shapeBounds.getHighBoundY();
/*  49 */     int maxZ = Math.min(chunkHighZ, shapeBounds.getHighBoundZ());
/*     */     
/*  51 */     for (int x = minX; x <= maxX; x++) {
/*  52 */       int cx = x - chunkLowX;
/*  53 */       for (int z = minZ; z <= maxZ; z++) {
/*  54 */         int cz = z - chunkLowZ;
/*     */ 
/*     */         
/*  57 */         int height = maxY;
/*  58 */         boolean heightLimited = false;
/*  59 */         if (surfaceLimited) {
/*  60 */           int chunkHeight = chunk.getHeight(cx, cz);
/*  61 */           if (height >= chunkHeight) {
/*  62 */             height = chunkHeight;
/*  63 */             heightLimited = true;
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/*  68 */         int lowest = Integer.MAX_VALUE;
/*  69 */         int lowestPossible = Integer.MAX_VALUE;
/*     */ 
/*     */         
/*  72 */         int highest = Integer.MIN_VALUE;
/*  73 */         int highestPossible = Integer.MIN_VALUE;
/*     */         
/*  75 */         for (int y = minY; y <= height; y++) {
/*  76 */           if (shouldReplace(seed, x, z, y)) {
/*  77 */             if (y < lowestPossible) lowestPossible = y; 
/*  78 */             if (y > highestPossible) highestPossible = y; 
/*  79 */             int current = execution.getBlock(cx, y, cz);
/*  80 */             int currentFluid = execution.getFluid(cx, y, cz);
/*  81 */             boolean isCandidateBlock = (!surfaceLimited || current != 0);
/*  82 */             if (isCandidateBlock) {
/*  83 */               BlockFluidEntry blockEntry = CaveNodeShapeUtils.getFillingBlock(caveType, caveNodeType, y, random);
/*  84 */               if (caveType.getBlockMask().eval(current, currentFluid, blockEntry)) {
/*  85 */                 if (execution.setBlock(cx, y, cz, (byte)6, blockEntry, environment)) {
/*  86 */                   if (y < lowest) lowest = y; 
/*  87 */                   if (y > highest) highest = y;
/*     */                 
/*     */                 } 
/*  90 */                 if (execution.setFluid(cx, y, cz, (byte)6, blockEntry.fluidId(), environment)) {
/*  91 */                   if (y < lowest) lowest = y; 
/*  92 */                   if (y > highest) highest = y; 
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*  98 */         CaveNodeType.CaveNodeCoverEntry[] covers = caveNodeType.getCovers();
/*  99 */         for (CaveNodeType.CaveNodeCoverEntry cover : covers) {
/* 100 */           CaveNodeType.CaveNodeCoverEntry.Entry entry = cover.get(random);
/* 101 */           int i = CaveNodeShapeUtils.getCoverHeight(lowest, lowestPossible, highest, highestPossible, heightLimited, cover, entry);
/*     */           
/* 103 */           if (i >= 0 && cover.getDensityCondition().eval(seed + node.getSeedOffset(), x, z) && cover
/* 104 */             .getHeightCondition().eval(seed, x, z, i, random) && cover
/* 105 */             .getMapCondition().eval(seed, x, z) && 
/* 106 */             CaveNodeShapeUtils.isCoverMatchingParent(cx, cz, i, execution, cover)) {
/*     */             
/* 108 */             execution.setBlock(cx, i, cz, (byte)5, entry.getEntry(), environment);
/* 109 */             execution.setFluid(cx, i, cz, (byte)5, entry.getEntry().fluidId(), environment);
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 114 */         if (CaveNodeShapeUtils.invalidateCover(cx, lowest - 1, cz, CaveNodeType.CaveNodeCoverType.CEILING, execution, blockTypeMap)) {
/* 115 */           BlockFluidEntry blockEntry = CaveNodeShapeUtils.getFillingBlock(caveType, caveNodeType, lowest - 1, random);
/* 116 */           execution.overrideBlock(cx, lowest - 1, cz, (byte)6, blockEntry);
/* 117 */           execution.overrideFluid(cx, lowest - 1, cz, (byte)6, blockEntry.fluidId());
/*     */         } 
/*     */ 
/*     */         
/* 121 */         if (CaveNodeShapeUtils.invalidateCover(cx, highest + 1, cz, CaveNodeType.CaveNodeCoverType.FLOOR, execution, blockTypeMap)) {
/* 122 */           BlockFluidEntry blockEntry = CaveNodeShapeUtils.getFillingBlock(caveType, caveNodeType, highest + 1, random);
/* 123 */           execution.overrideBlock(cx, highest + 1, cz, (byte)6, blockEntry);
/* 124 */           execution.overrideFluid(cx, highest + 1, cz, (byte)6, blockEntry.fluidId());
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\cave\shape\AbstractCaveNodeShape.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */