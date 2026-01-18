/*     */ package com.hypixel.hytale.server.worldgen.cave;
/*     */ 
/*     */ import com.hypixel.hytale.math.util.FastRandom;
/*     */ import com.hypixel.hytale.math.util.HashUtil;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.procedurallib.condition.ConstantIntCondition;
/*     */ import com.hypixel.hytale.procedurallib.condition.DefaultCoordinateCondition;
/*     */ import com.hypixel.hytale.procedurallib.condition.ICoordinateCondition;
/*     */ import com.hypixel.hytale.procedurallib.condition.IIntCondition;
/*     */ import com.hypixel.hytale.procedurallib.supplier.IDoubleRange;
/*     */ import com.hypixel.hytale.server.core.prefab.PrefabRotation;
/*     */ import com.hypixel.hytale.server.worldgen.cave.element.CaveNode;
/*     */ import com.hypixel.hytale.server.worldgen.cave.element.CavePrefab;
/*     */ import com.hypixel.hytale.server.worldgen.cave.prefab.CavePrefabContainer;
/*     */ import com.hypixel.hytale.server.worldgen.cave.shape.CaveNodeShape;
/*     */ import com.hypixel.hytale.server.worldgen.cave.shape.PrefabCaveNodeShape;
/*     */ import com.hypixel.hytale.server.worldgen.chunk.ChunkGenerator;
/*     */ import com.hypixel.hytale.server.worldgen.chunk.ZoneBiomeResult;
/*     */ import com.hypixel.hytale.server.worldgen.loader.WorldGenPrefabSupplier;
/*     */ import com.hypixel.hytale.server.worldgen.util.ArrayUtli;
/*     */ import com.hypixel.hytale.server.worldgen.util.condition.flag.Int2FlagsCondition;
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CaveGenerator
/*     */ {
/*     */   private final CaveType[] caveTypes;
/*     */   
/*     */   public CaveGenerator(CaveType[] caveTypes) {
/*  36 */     this.caveTypes = caveTypes;
/*     */   }
/*     */   
/*     */   public CaveType[] getCaveTypes() {
/*  40 */     return this.caveTypes;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Cave generate(int seed, @Nonnull ChunkGenerator chunkGenerator, @Nonnull CaveType caveType, int x, int y, int z) {
/*  45 */     int seedOffset = (int)HashUtil.rehash(seed, x, y, z);
/*  46 */     FastRandom fastRandom = new FastRandom(seedOffset);
/*     */     
/*  48 */     Cave cave = newCave(caveType);
/*  49 */     Vector3d origin = new Vector3d(x, y, z);
/*     */     
/*  51 */     origin.y = caveType.getModifiedStartHeight(seed + seedOffset, x, y, z, (Random)fastRandom);
/*     */     
/*  53 */     startCave(seed, chunkGenerator, cave, origin, (Random)fastRandom);
/*  54 */     cave.compile();
/*     */     
/*  56 */     return cave;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected Cave newCave(CaveType caveType) {
/*  61 */     return new Cave(caveType);
/*     */   }
/*     */   
/*     */   protected void startCave(int seed, @Nonnull ChunkGenerator chunkGenerator, @Nonnull Cave cave, @Nonnull Vector3d origin, @Nonnull Random random) {
/*  65 */     Int2FlagsCondition biomeMask = cave.getCaveType().getBiomeMask();
/*  66 */     int startBiomeMaskResult = getBiomeMaskResult(seed, chunkGenerator, biomeMask, origin);
/*  67 */     if (!CaveBiomeMaskFlags.canGenerate(startBiomeMaskResult))
/*     */       return; 
/*  69 */     CaveType caveType = cave.getCaveType();
/*  70 */     int depth = caveType.getStartDepth(random);
/*     */     
/*  72 */     CaveNodeType type = caveType.getEntryNode();
/*  73 */     float yaw = caveType.getStartYaw(random);
/*  74 */     float pitch = caveType.getStartPitch(random);
/*  75 */     int seedOffset = random.nextInt();
/*     */     
/*  77 */     CaveNodeShape shape = type.generateCaveNodeShape(random, caveType, null, null, origin, yaw, pitch);
/*     */     
/*  79 */     int endBiomeMaskResult = getBiomeMaskResult(seed, chunkGenerator, biomeMask, shape.getEnd());
/*  80 */     if (!CaveBiomeMaskFlags.canGenerate(endBiomeMaskResult))
/*     */       return; 
/*  82 */     CaveNode node = new CaveNode(seed + seedOffset, type, shape, yaw, pitch);
/*  83 */     if (shape.hasGeometry() && CaveBiomeMaskFlags.canPopulate(startBiomeMaskResult) && CaveBiomeMaskFlags.canPopulate(endBiomeMaskResult)) {
/*  84 */       cave.addNode(node);
/*     */     }
/*     */     
/*  87 */     continueNode(seed, chunkGenerator, cave, node, depth, random);
/*     */   }
/*     */   
/*     */   protected void continueNode(int seed, @Nonnull ChunkGenerator chunkGenerator, @Nonnull Cave cave, @Nonnull CaveNode parent, int depth, @Nonnull Random random) {
/*  91 */     if (depth <= 0)
/*     */       return; 
/*  93 */     Int2FlagsCondition biomeMask = cave.getCaveType().getBiomeMask();
/*  94 */     CaveNodeType.CaveNodeChildEntry[] childEntries = getChildEntriesRandomized(parent.getCaveNodeType(), random);
/*  95 */     int childrenCount = getChildrenCount(parent.getCaveNodeType(), random);
/*  96 */     int generatedChildren = 0;
/*  97 */     for (CaveNodeType.CaveNodeChildEntry childEntry : childEntries) {
/*  98 */       int repeat = getRepeatCounter(childEntry, random);
/*  99 */       for (int j = 0; j < repeat; j++) {
/* 100 */         if (!shouldGenerateChild(childEntry, random))
/* 101 */           continue;  if (generatedChildren >= childrenCount)
/*     */           return; 
/* 103 */         PrefabRotation parentRotation = getRotation(parent);
/* 104 */         Vector3d origin = getChildOrigin(parent, parentRotation, childEntry);
/* 105 */         CaveNodeType type = (CaveNodeType)childEntry.getTypes().get(random);
/* 106 */         if (!isMatchingHeight(seed, origin, type.getHeightCondition()))
/*     */           continue; 
/* 108 */         float yaw = getChildYaw(parent, parentRotation, childEntry, random);
/* 109 */         float pitch = childEntry.getPitchModifier().calc(parent.getPitch(), random);
/* 110 */         int hash = random.nextInt();
/*     */         
/* 112 */         CaveNodeShape shape = type.generateCaveNodeShape(random, cave.getCaveType(), parent, childEntry, origin, yaw, pitch);
/* 113 */         if (!isMatchingHeight(seed, shape.getEnd(), type.getHeightCondition())) {
/*     */           continue;
/*     */         }
/* 116 */         int biomeMaskResult = getBiomeMaskResult(seed, chunkGenerator, biomeMask, shape.getEnd());
/* 117 */         if (!CaveBiomeMaskFlags.canGenerate(biomeMaskResult)) {
/* 118 */           if (CaveBiomeMaskFlags.canContinue(biomeMaskResult))
/*     */             continue; 
/*     */           break;
/*     */         } 
/* 122 */         CaveNode node = new CaveNode(hash, type, shape, yaw, pitch);
/* 123 */         if (shape.hasGeometry() && CaveBiomeMaskFlags.canPopulate(biomeMaskResult)) {
/* 124 */           generatePrefabs(seed, chunkGenerator, parent, node);
/* 125 */           cave.addNode(node);
/*     */         } 
/*     */         
/* 128 */         int nextDepth = getNextDepth(childEntry, depth, random);
/* 129 */         continueNode(seed, chunkGenerator, cave, node, nextDepth, random);
/* 130 */         generatedChildren++;
/*     */         continue;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   protected int getChildrenCount(@Nonnull CaveNodeType type, Random random) {
/* 136 */     IDoubleRange countArray = type.getChildrenCountBounds();
/* 137 */     if (countArray == null) {
/* 138 */       return Integer.MAX_VALUE;
/*     */     }
/* 140 */     return MathUtil.floor(countArray.getValue(random));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected CaveNodeType.CaveNodeChildEntry[] getChildEntriesRandomized(@Nonnull CaveNodeType type, @Nonnull Random random) {
/* 146 */     CaveNodeType.CaveNodeChildEntry[] childEntries = type.getChildren();
/*     */     
/* 148 */     if (type.getChildrenCountBounds() == null || childEntries.length == 0) {
/* 149 */       return childEntries;
/*     */     }
/* 151 */     CaveNodeType.CaveNodeChildEntry[] randomized = new CaveNodeType.CaveNodeChildEntry[childEntries.length];
/* 152 */     System.arraycopy(childEntries, 0, randomized, 0, randomized.length);
/* 153 */     ArrayUtli.shuffleArray((Object[])randomized, random);
/* 154 */     return randomized;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getRepeatCounter(@Nonnull CaveNodeType.CaveNodeChildEntry entry, Random random) {
/* 159 */     return MathUtil.floor(entry.getRepeat().getValue(random));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected PrefabRotation getRotation(@Nonnull CaveNode caveNode) {
/* 170 */     CaveNodeShape shape = caveNode.getShape();
/* 171 */     if (shape instanceof PrefabCaveNodeShape) return ((PrefabCaveNodeShape)shape).getPrefabRotation();
/*     */     
/* 173 */     return null;
/*     */   }
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
/*     */   protected Vector3d getChildOrigin(@Nonnull CaveNode parentNode, @Nullable PrefabRotation parentRotation, @Nonnull CaveNodeType.CaveNodeChildEntry childEntry) {
/* 188 */     Vector3d vector = parentNode.getEnd();
/*     */     
/* 190 */     Vector3d anchor = childEntry.getAnchor();
/* 191 */     if (anchor == Vector3d.ZERO) return vector;
/*     */ 
/*     */     
/* 194 */     vector.assign(anchor);
/*     */     
/* 196 */     if (parentRotation != null && parentRotation != PrefabRotation.ROTATION_0) {
/* 197 */       vector.subtract(0.5D, 0.5D, 0.5D);
/* 198 */       parentRotation.rotate(vector);
/* 199 */       vector.add(0.5D, 0.5D, 0.5D);
/*     */     } 
/*     */     
/* 202 */     return parentNode.getShape().getAnchor(vector, vector.x, vector.y, vector.z);
/*     */   }
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
/*     */   protected float getChildYaw(@Nonnull CaveNode parentNode, @Nullable PrefabRotation parentRotation, @Nonnull CaveNodeType.CaveNodeChildEntry childEntry, Random random) {
/* 217 */     float yaw = childEntry.getYawMode().combine(parentNode.getYaw(), parentRotation);
/*     */     
/* 219 */     return childEntry.getYawModifier().calc(yaw, random);
/*     */   }
/*     */   
/*     */   protected boolean shouldGenerateChild(@Nonnull CaveNodeType.CaveNodeChildEntry entry, @Nonnull Random random) {
/* 223 */     return (random.nextDouble() < entry.getChance());
/*     */   }
/*     */   
/*     */   protected boolean isMatchingHeight(int seed, @Nonnull Vector3d vec, @Nonnull ICoordinateCondition condition) {
/* 227 */     if (condition == DefaultCoordinateCondition.DEFAULT_TRUE) return true; 
/* 228 */     if (condition == DefaultCoordinateCondition.DEFAULT_FALSE) return false; 
/* 229 */     int x = MathUtil.floor(vec.x);
/* 230 */     int y = MathUtil.floor(vec.y);
/* 231 */     int z = MathUtil.floor(vec.z);
/* 232 */     return condition.eval(seed, x, y, z);
/*     */   }
/*     */   
/*     */   protected int getNextDepth(@Nonnull CaveNodeType.CaveNodeChildEntry entry, int depth, Random random) {
/* 236 */     int nextDepth = depth - 1;
/* 237 */     if (entry.getChildrenLimit() != null) {
/* 238 */       int limit = MathUtil.floor(entry.getChildrenLimit().getValue(random));
/* 239 */       if (limit < nextDepth) {
/* 240 */         return limit;
/*     */       }
/*     */     } 
/* 243 */     return nextDepth;
/*     */   }
/*     */   
/*     */   protected void generatePrefabs(int seed, @Nonnull ChunkGenerator chunkGenerator, CaveNode parent, @Nonnull CaveNode node) {
/* 247 */     Random random = ChunkGenerator.getResource().getRandom();
/* 248 */     random.setSeed((seed + node.getSeedOffset()));
/*     */     
/* 250 */     CavePrefabContainer container = node.getCaveNodeType().getPrefabContainer();
/* 251 */     if (container == null)
/* 252 */       return;  for (CavePrefabContainer.CavePrefabEntry entry : container.getEntries()) {
/* 253 */       generatePrefab(seed, chunkGenerator, parent, node, entry, random);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void generatePrefab(int seed, @Nonnull ChunkGenerator chunkGenerator, @Nullable CaveNode parent, @Nonnull CaveNode caveNode, @Nonnull CavePrefabContainer.CavePrefabEntry entry, @Nonnull Random random) {
/* 258 */     CavePrefabContainer.CavePrefabEntry.CavePrefabConfig config = entry.getConfig();
/* 259 */     int iterations = config.getIterations(random.nextDouble());
/* 260 */     for (int i = 0; i < iterations; i++) {
/* 261 */       int x = caveNode.getBounds().randomX(random);
/* 262 */       int z = caveNode.getBounds().randomZ(random);
/* 263 */       if (isMatchingBiome(seed, chunkGenerator, config.getBiomeMask(), x, z))
/*     */       {
/* 265 */         if (config.isMatchingNoiseDensity(seed, x, z)) {
/* 266 */           int y = config.getHeight(seed, x, z, caveNode);
/* 267 */           if (y != -1 && config.isMatchingHeight(seed, x, y, z, random) && (
/* 268 */             parent == null || !parent.getShape().shouldReplace(seed, x, z, y))) {
/*     */             
/* 270 */             WorldGenPrefabSupplier prefab = entry.getPrefab(random.nextDouble());
/* 271 */             PrefabRotation rotation = config.getRotation(random);
/*     */             
/* 273 */             CavePrefab entity = new CavePrefab(prefab, rotation, config.getBiomeMask(), config.getBlockMask(), x, y, z);
/* 274 */             caveNode.addPrefab(entity);
/*     */           } 
/*     */         }  } 
/*     */     } 
/*     */   }
/*     */   protected boolean isMatchingBiome(int seed, @Nonnull ChunkGenerator chunkGenerator, @Nonnull IIntCondition condition, int x, int z) {
/* 280 */     if (condition == ConstantIntCondition.DEFAULT_TRUE) return true; 
/* 281 */     if (condition == ConstantIntCondition.DEFAULT_FALSE) return false;
/*     */     
/* 283 */     ZoneBiomeResult biomeResult = chunkGenerator.getZoneBiomeResultAt(seed, x, z);
/* 284 */     return condition.eval(biomeResult.getBiome().getId());
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getBiomeMaskResult(int seed, @Nonnull ChunkGenerator chunkGenerator, @Nonnull Int2FlagsCondition mask, @Nonnull Vector3d vec) {
/* 289 */     if (mask == CaveBiomeMaskFlags.DEFAULT_ALLOW) return 7; 
/* 290 */     if (mask == CaveBiomeMaskFlags.DEFAULT_DENY) return 0;
/*     */     
/* 292 */     int x = MathUtil.floor(vec.getX());
/* 293 */     int z = MathUtil.floor(vec.getZ());
/* 294 */     ZoneBiomeResult biomeResult = chunkGenerator.getZoneBiomeResultAt(seed, x, z);
/* 295 */     return mask.eval(biomeResult.getBiome().getId());
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\cave\CaveGenerator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */