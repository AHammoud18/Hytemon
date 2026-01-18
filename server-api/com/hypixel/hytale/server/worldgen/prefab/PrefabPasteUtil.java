/*     */ package com.hypixel.hytale.server.worldgen.prefab;
/*     */ 
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.util.FastRandom;
/*     */ import com.hypixel.hytale.math.util.HashUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.procedurallib.condition.DefaultCoordinateCondition;
/*     */ import com.hypixel.hytale.procedurallib.condition.DefaultCoordinateRndCondition;
/*     */ import com.hypixel.hytale.procedurallib.condition.ICoordinateCondition;
/*     */ import com.hypixel.hytale.procedurallib.condition.ICoordinateRndCondition;
/*     */ import com.hypixel.hytale.server.core.prefab.PrefabRotation;
/*     */ import com.hypixel.hytale.server.core.prefab.PrefabWeights;
/*     */ import com.hypixel.hytale.server.core.prefab.selection.buffer.PrefabBufferCall;
/*     */ import com.hypixel.hytale.server.worldgen.chunk.ChunkGeneratorExecution;
/*     */ import com.hypixel.hytale.server.worldgen.loader.WorldGenPrefabSupplier;
/*     */ import com.hypixel.hytale.server.worldgen.util.BlockFluidEntry;
/*     */ import com.hypixel.hytale.server.worldgen.util.condition.BlockMaskCondition;
/*     */ import com.hypixel.hytale.server.worldgen.util.condition.DefaultBlockMaskCondition;
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PrefabPasteUtil
/*     */ {
/*     */   public static final int MAX_RECURSION_DEPTH = 10;
/*     */   
/*     */   public static class PrefabPasteBuffer
/*     */     extends PrefabBufferCall
/*     */   {
/*     */     @Nullable
/*     */     public ChunkGeneratorExecution execution;
/*  36 */     public final Vector3i posWorld = new Vector3i();
/*  37 */     public final Vector3i posChunk = new Vector3i();
/*  38 */     public final Random childRandom = (Random)new FastRandom(0L);
/*     */     
/*     */     public int originHeight;
/*     */     public int yOffset;
/*     */     public int seed;
/*     */     public int specificSeed;
/*     */     public boolean fitHeightmap;
/*     */     public boolean deepSearch;
/*     */     public BlockMaskCondition blockMask;
/*     */     public int environmentId;
/*     */     public byte priority;
/*     */     public ICoordinateCondition heightCondition;
/*     */     public ICoordinateRndCondition spawnCondition;
/*     */     @Nullable
/*     */     public WorldGenPrefabSupplier supplier;
/*     */     private int depth;
/*     */     
/*     */     public PrefabPasteBuffer() {
/*  56 */       this.random = (Random)new FastRandom(0L);
/*  57 */       reset();
/*     */     }
/*     */     
/*     */     public void setSeed(int worldSeed, long externalSeed) {
/*  61 */       this.seed = worldSeed;
/*  62 */       this.specificSeed = (int)externalSeed;
/*  63 */       this.random.setSeed(externalSeed);
/*  64 */       this.childRandom.setSeed(externalSeed);
/*     */     }
/*     */     
/*     */     void reset() {
/*  68 */       this.execution = null;
/*  69 */       this.fitHeightmap = false;
/*  70 */       this.deepSearch = false;
/*  71 */       this.blockMask = (BlockMaskCondition)DefaultBlockMaskCondition.DEFAULT_TRUE;
/*  72 */       this.environmentId = Integer.MIN_VALUE;
/*  73 */       this.heightCondition = (ICoordinateCondition)DefaultCoordinateCondition.DEFAULT_TRUE;
/*  74 */       this.spawnCondition = (ICoordinateRndCondition)DefaultCoordinateRndCondition.DEFAULT_TRUE;
/*  75 */       this.supplier = null;
/*  76 */       this.depth = 0;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void generate(@Nonnull PrefabPasteBuffer buffer, PrefabRotation rotation, @Nonnull WorldGenPrefabSupplier supplier, int x, int y, int z, int cx, int cz) {
/*  85 */     buffer.supplier = supplier;
/*  86 */     buffer.posWorld.assign(x, y, z);
/*  87 */     buffer.posChunk.assign(cx, y, cz);
/*  88 */     buffer.rotation = rotation;
/*     */     
/*  90 */     generate0(buffer, supplier);
/*  91 */     buffer.reset();
/*     */   }
/*     */   
/*     */   private static void generate0(@Nonnull PrefabPasteBuffer _buffer, @Nonnull WorldGenPrefabSupplier supplier) {
/*  95 */     if (_buffer.fitHeightmap) {
/*  96 */       _buffer.originHeight = _buffer.execution.getChunkGenerator().getHeight(_buffer.seed, _buffer.posWorld.x, _buffer.posWorld.z);
/*  97 */       _buffer.posChunk.y = _buffer.originHeight;
/*  98 */       _buffer.posWorld.y = _buffer.originHeight;
/*     */     } 
/*     */     
/* 101 */     supplier.get().forEach((cx, cz, blocks, buffer) -> { int bx = cx + buffer.posChunk.x; int bz = cz + buffer.posChunk.z; if (!ChunkUtil.isWithinLocalChunk(bx, bz)) return false;  if (buffer.fitHeightmap) { buffer.yOffset = buffer.execution.getChunkGenerator().getHeight(buffer.seed, buffer.posWorld.x + cx, buffer.posWorld.z + cz) - buffer.originHeight; } else { buffer.yOffset = 0; }  return true; }(cx, cy, cz, block, holder, supportValue, rotation, filler, buffer, fluidId, fluidLevel) -> { if (buffer.blockMask == DefaultBlockMaskCondition.DEFAULT_FALSE) return;  int bx = cx + buffer.posChunk.x; int by = cy + buffer.posChunk.y + buffer.yOffset; int bz = cz + buffer.posChunk.z; if (by < 0 || by >= 320) return;  int currentBlock = buffer.execution.getBlock(bx, by, bz); int currentFluid = buffer.execution.getFluid(bx, by, bz); if (buffer.blockMask != DefaultBlockMaskCondition.DEFAULT_TRUE && !buffer.blockMask.eval(currentBlock, currentFluid, new BlockFluidEntry(block, 0, fluidId))) return;  buffer.execution.setBlock(bx, by, bz, buffer.priority, block, (holder != null) ? holder.clone() : null, supportValue, rotation, filler); buffer.execution.setFluid(bx, by, bz, buffer.priority, fluidId, buffer.environmentId); buffer.execution.setEnvironment(bx, by, bz, buffer.environmentId); }(cx, cz, entityWrappers, buffer) -> { Holder[] arrayOfHolder = new Holder[entityWrappers.length]; for (int i = 0; i < entityWrappers.length; i++) arrayOfHolder[i] = entityWrappers[i].clone();  Vector3i offset = new Vector3i(buffer.posWorld.x, buffer.posWorld.y + buffer.yOffset, buffer.posWorld.z); buffer.execution.getEntityChunk().addEntities(offset, buffer.rotation, arrayOfHolder, buffer.specificSeed); }(cx, cy, cz, path, fitHeightmap, inheritSeed, inheritHeightCondition, weights, rotation, buffer) -> { if (buffer.depth >= 10) return;  buffer.depth++; int _localX = buffer.posChunk.x; int _localY = buffer.posChunk.y; int _localZ = buffer.posChunk.z; int _worldX = buffer.posWorld.x; int _worldY = buffer.posWorld.y; int _worldZ = buffer.posWorld.z; int _yOffset = buffer.yOffset; int _originHeight = buffer.originHeight; int _specificSeed = buffer.specificSeed; PrefabRotation _rotation = buffer.rotation; boolean _fitHeightmap = buffer.fitHeightmap; generateChild(cx, cy, cz, path, fitHeightmap, inheritSeed, inheritHeightCondition, weights, rotation, buffer, buffer.childRandom); buffer.posChunk.assign(_localX, _localY, _localZ); buffer.posWorld.assign(_worldX, _worldY, _worldZ); buffer.yOffset = _yOffset; buffer.originHeight = _originHeight; buffer.rotation = _rotation; buffer.specificSeed = _specificSeed; buffer.fitHeightmap = _fitHeightmap; buffer.depth--; }_buffer);
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
/*     */   private static void generateChild(int cx, int cy, int cz, String path, boolean fitHeightmap, boolean inheritSeed, boolean inheritHeightCondition, @Nonnull PrefabWeights weights, @Nonnull PrefabRotation rotation, @Nonnull PrefabPasteBuffer buffer, Random random) {
/* 196 */     int parentSpecificSeed = buffer.specificSeed;
/* 197 */     boolean parentFitHeightmap = buffer.fitHeightmap;
/*     */     
/* 199 */     buffer.posChunk.add(cx, cy, cz);
/* 200 */     buffer.posWorld.add(cx, cy, cz);
/* 201 */     buffer.fitHeightmap = fitHeightmap;
/* 202 */     buffer.rotation = buffer.rotation.add(rotation);
/*     */ 
/*     */     
/* 205 */     if (!inheritSeed) {
/* 206 */       buffer.specificSeed = parentSpecificSeed;
/*     */     } else {
/* 208 */       buffer.specificSeed = (int)HashUtil.hash(parentSpecificSeed, cx, cy, cz);
/* 209 */       if (buffer.specificSeed == parentSpecificSeed)
/*     */       {
/* 211 */         buffer.specificSeed++;
/*     */       }
/*     */     } 
/*     */     
/* 215 */     if (parentFitHeightmap) {
/* 216 */       int yOffset = buffer.execution.getChunkGenerator().getHeight(buffer.seed, buffer.posWorld.x, buffer.posWorld.z) - buffer.originHeight;
/* 217 */       buffer.posChunk.y += yOffset;
/* 218 */       buffer.posWorld.y += yOffset;
/*     */     } 
/*     */     
/* 221 */     if (buffer.posChunk.y >= 0 && buffer.posChunk.y < 320 && ChunkUtil.isWithinLocalChunk(buffer.posChunk.x, buffer.posChunk.z)) {
/* 222 */       buffer.execution.setBlock(buffer.posChunk.x, buffer.posChunk.y, buffer.posChunk.z, buffer.priority, 0, null);
/*     */     }
/*     */ 
/*     */     
/* 226 */     if (inheritHeightCondition && !buffer.spawnCondition.eval(buffer.seed, buffer.posWorld.x, buffer.posWorld.z, buffer.posWorld.y, random)) {
/*     */       return;
/*     */     }
/*     */     
/* 230 */     WorldGenPrefabSupplier[] prefabSuppliers = buffer.supplier.getLoader().get(path);
/* 231 */     if (prefabSuppliers == null || prefabSuppliers.length == 0)
/*     */       return; 
/* 233 */     WorldGenPrefabSupplier prefabSupplier = nextPrefab(buffer.childRandom, prefabSuppliers, weights);
/* 234 */     generate0(buffer, prefabSupplier);
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
/*     */   @Nonnull
/*     */   private static WorldGenPrefabSupplier nextPrefab(@Nonnull Random random, @Nonnull WorldGenPrefabSupplier[] prefabSuppliers, @Nonnull PrefabWeights weights) {
/* 249 */     if (prefabSuppliers.length == 1) return prefabSuppliers[0];
/*     */ 
/*     */     
/* 252 */     WorldGenPrefabSupplier prefab = null;
/*     */     
/* 254 */     if (weights.size() > 0) {
/* 255 */       prefab = (WorldGenPrefabSupplier)weights.get((Object[])prefabSuppliers, WorldGenPrefabSupplier::getPrefabName, random);
/*     */     }
/*     */ 
/*     */     
/* 259 */     if (prefab == null) return nextRandomPrefab(random, prefabSuppliers);
/*     */     
/* 261 */     return prefab;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private static WorldGenPrefabSupplier nextRandomPrefab(@Nonnull Random random, @Nonnull WorldGenPrefabSupplier[] prefabSuppliers) {
/* 273 */     return prefabSuppliers[random.nextInt(prefabSuppliers.length)];
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\prefab\PrefabPasteUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */