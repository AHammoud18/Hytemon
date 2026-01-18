/*     */ package com.hypixel.hytale.builtin.adventure.farming.config.stages;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetPack;
/*     */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*     */ import com.hypixel.hytale.assetstore.map.BlockTypeAssetMap;
/*     */ import com.hypixel.hytale.builtin.adventure.farming.states.FarmingBlock;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.common.map.IWeightedElement;
/*     */ import com.hypixel.hytale.common.map.IWeightedMap;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.util.FastRandom;
/*     */ import com.hypixel.hytale.math.util.HashUtil;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.protocol.BlockMaterial;
/*     */ import com.hypixel.hytale.server.core.asset.AssetModule;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.Rotation;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.RotationTuple;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.farming.FarmingStageData;
/*     */ import com.hypixel.hytale.server.core.codec.WeightedMapCodec;
/*     */ import com.hypixel.hytale.server.core.prefab.PrefabRotation;
/*     */ import com.hypixel.hytale.server.core.prefab.PrefabStore;
/*     */ import com.hypixel.hytale.server.core.prefab.selection.buffer.PrefabBufferCall;
/*     */ import com.hypixel.hytale.server.core.prefab.selection.buffer.PrefabBufferUtil;
/*     */ import com.hypixel.hytale.server.core.prefab.selection.buffer.impl.IPrefabBuffer;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.accessor.ChunkAccessor;
/*     */ import com.hypixel.hytale.server.core.universe.world.accessor.LocalCachedChunkAccessor;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.BlockSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.ChunkSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.util.PrefabUtil;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.util.Arrays;
/*     */ import java.util.Random;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class PrefabFarmingStageData
/*     */   extends FarmingStageData {
/*     */   public static final float MIN_VOLUME_PREFAB = 125.0F;
/*     */   public static final float MAX_VOLUME_PREFAB = 1000.0F;
/*  54 */   private static final String[] EMPTY_REPLACE_MASK = new String[0];
/*     */   
/*     */   public static final float MIN_BROKEN_PARTICLE_RATE = 0.25F;
/*     */   
/*     */   public static final float MAX_BROKEN_PARTICLE_RATE = 0.75F;
/*     */   
/*     */   @Nonnull
/*     */   public static BuilderCodec<PrefabFarmingStageData> CODEC;
/*     */   
/*     */   protected IWeightedMap<PrefabStage> prefabStages;
/*     */ 
/*     */   
/*     */   static {
/*  67 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(PrefabFarmingStageData.class, PrefabFarmingStageData::new, FarmingStageData.BASE_CODEC).append(new KeyedCodec("Prefabs", (Codec)new WeightedMapCodec(PrefabStage.CODEC, (IWeightedElement[])PrefabStage.EMPTY_ARRAY)), (stage, prefabStages) -> stage.prefabStages = prefabStages, stage -> stage.prefabStages).add()).append(new KeyedCodec("ReplaceMaskTags", (Codec)new ArrayCodec((Codec)Codec.STRING, x$0 -> new String[x$0])), (stage, replaceMask) -> stage.replaceMaskTags = replaceMask, stage -> stage.replaceMaskTags).add()).afterDecode(PrefabFarmingStageData::processConfig)).build();
/*     */   }
/*     */   
/*  70 */   private String[] replaceMaskTags = EMPTY_REPLACE_MASK;
/*     */   private int[] replaceMaskTagIndices;
/*     */   
/*     */   private static double computeParticlesRate(@Nonnull IPrefabBuffer prefab) {
/*  74 */     double xLength = (prefab.getMaxX() - prefab.getMinX());
/*  75 */     double yLength = (prefab.getMaxY() - prefab.getMinY());
/*  76 */     double zLength = (prefab.getMaxZ() - prefab.getMinZ());
/*  77 */     double volume = xLength * yLength * zLength;
/*     */ 
/*     */ 
/*     */     
/*  81 */     double ratio = -5.714285653084517E-4D;
/*     */ 
/*     */     
/*  84 */     double rate = (volume - 125.0D) * ratio;
/*     */ 
/*     */ 
/*     */     
/*  88 */     return MathUtil.clamp(rate + 0.75D, 0.25D, 0.75D);
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
/*     */   private static boolean isPrefabBlockIntact(LocalCachedChunkAccessor chunkAccessor, int worldX, int worldY, int worldZ, int blockX, int blockY, int blockZ, int blockId, int rotation, PrefabRotation prefabRotation) {
/* 107 */     int globalX = prefabRotation.getX(blockX, blockZ) + worldX;
/* 108 */     int globalY = blockY + worldY;
/* 109 */     int globalZ = prefabRotation.getZ(blockX, blockZ) + worldZ;
/*     */     
/* 111 */     BlockType block = (BlockType)BlockType.getAssetMap().getAsset(blockId);
/* 112 */     if (block.getMaterial() == BlockMaterial.Empty) return true;
/*     */     
/* 114 */     WorldChunk chunk = chunkAccessor.getNonTickingChunk(ChunkUtil.indexChunkFromBlock(globalX, globalZ));
/* 115 */     if (chunk == null) return false;
/*     */     
/* 117 */     int worldBlockId = chunk.getBlock(globalX, globalY, globalZ);
/* 118 */     if (worldBlockId != blockId) return false;
/*     */     
/* 120 */     int expectedRotation = prefabRotation.getRotation(rotation);
/* 121 */     int worldRotation = chunk.getRotationIndex(globalX, globalY, globalZ);
/*     */     
/* 123 */     return (worldRotation == expectedRotation);
/*     */   }
/*     */   
/*     */   private static boolean isPrefabIntact(IPrefabBuffer prefabBuffer, LocalCachedChunkAccessor chunkAccessor, int worldX, int worldY, int worldZ, PrefabRotation prefabRotation, FastRandom random) {
/* 127 */     return prefabBuffer.forEachRaw(IPrefabBuffer.iterateAllColumns(), (blockX, blockY, blockZ, blockId, chance, holder, supportValue, rotation, filler, t) -> isPrefabBlockIntact(chunkAccessor, worldX, worldY, worldZ, blockX, blockY, blockZ, blockId, rotation, prefabRotation), (fluidX, fluidY, fluidZ, fluidId, level, o) -> true, null, new PrefabBufferCall((Random)random, prefabRotation));
/*     */   }
/*     */   
/*     */   public IWeightedMap<PrefabStage> getPrefabStages() {
/* 131 */     return this.prefabStages;
/*     */   }
/*     */ 
/*     */   
/*     */   public void apply(ComponentAccessor<ChunkStore> commandBuffer, Ref<ChunkStore> sectionRef, Ref<ChunkStore> blockRef, int x, int y, int z, @Nullable FarmingStageData previousStage) {
/* 136 */     FarmingBlock farming = (FarmingBlock)commandBuffer.getComponent(blockRef, FarmingBlock.getComponentType());
/* 137 */     IPrefabBuffer prefabBuffer = getCachedPrefab(x, y, z, farming.getGeneration());
/* 138 */     BlockSection blockSection = (BlockSection)commandBuffer.getComponent(sectionRef, BlockSection.getComponentType());
/*     */     
/* 140 */     int randomRotation = HashUtil.randomInt(x, y, z, Rotation.VALUES.length);
/* 141 */     RotationTuple yaw = RotationTuple.of(Rotation.VALUES[randomRotation], Rotation.None);
/*     */     
/* 143 */     World world = ((ChunkStore)commandBuffer.getExternalData()).getWorld();
/* 144 */     ChunkSection section = (ChunkSection)commandBuffer.getComponent(sectionRef, ChunkSection.getComponentType());
/*     */     
/* 146 */     int worldX = ChunkUtil.worldCoordFromLocalCoord(section.getX(), x);
/* 147 */     int worldY = ChunkUtil.worldCoordFromLocalCoord(section.getY(), y);
/* 148 */     int worldZ = ChunkUtil.worldCoordFromLocalCoord(section.getZ(), z);
/*     */     
/* 150 */     if (farming.getPreviousBlockType() == null) {
/* 151 */       farming.setPreviousBlockType(((BlockType)BlockType.getAssetMap().getAsset(blockSection.get(x, y, z))).getId());
/*     */     }
/*     */     
/* 154 */     double xLength = (prefabBuffer.getMaxX() - prefabBuffer.getMinX());
/* 155 */     double zLength = (prefabBuffer.getMaxZ() - prefabBuffer.getMinZ());
/* 156 */     int prefabRadius = (int)MathUtil.fastFloor(0.5D * Math.sqrt(xLength * xLength + zLength * zLength));
/* 157 */     LocalCachedChunkAccessor chunkAccessor = LocalCachedChunkAccessor.atWorldCoords((ChunkAccessor)world, x, z, prefabRadius);
/*     */     
/* 159 */     FastRandom random = new FastRandom();
/* 160 */     PrefabRotation prefabRotation = PrefabRotation.fromRotation(yaw.yaw());
/*     */     
/* 162 */     BlockTypeAssetMap<String, BlockType> blockTypeMap = BlockType.getAssetMap();
/*     */     
/* 164 */     if (previousStage instanceof PrefabFarmingStageData) { PrefabFarmingStageData oldPrefab = (PrefabFarmingStageData)previousStage;
/*     */       
/* 166 */       IPrefabBuffer oldPrefabBuffer = oldPrefab.getCachedPrefab(worldX, worldY, worldZ, farming.getGeneration() - 1);
/*     */       
/* 168 */       double brokenParticlesRate = computeParticlesRate(prefabBuffer);
/*     */       
/* 170 */       world.execute(() -> {
/*     */             boolean isIntact = isPrefabIntact(oldPrefabBuffer, chunkAccessor, worldX, worldY, worldZ, prefabRotation, random);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             if (!isIntact) {
/*     */               return;
/*     */             }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             boolean isUnobstructed = prefabBuffer.compare((), new PrefabBufferCall((Random)random, prefabRotation), oldPrefabBuffer);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             if (!isUnobstructed) {
/*     */               return;
/*     */             }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             prefabBuffer.compare((), new PrefabBufferCall((Random)random, prefabRotation), oldPrefabBuffer);
/*     */           });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       return; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 229 */     super.apply(commandBuffer, sectionRef, blockRef, x, y, z, previousStage);
/*     */     
/* 231 */     world.execute(() -> {
/*     */           boolean isUnObstructed = prefabBuffer.forEachRaw(IPrefabBuffer.iterateAllColumns(), (), (), null, new PrefabBufferCall((Random)random, prefabRotation));
/*     */           if (!isUnObstructed) {
/*     */             return;
/*     */           }
/*     */           prefabBuffer.forEach(IPrefabBuffer.iterateAllColumns(), (), null, null, new PrefabBufferCall((Random)random, prefabRotation));
/*     */         });
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
/*     */   private boolean doesBlockObstruct(int blockId, int worldBlockId) {
/* 275 */     BlockTypeAssetMap<String, BlockType> assetMap = BlockType.getAssetMap();
/* 276 */     BlockType blockType = (BlockType)assetMap.getAsset(blockId);
/* 277 */     if (blockType == null || blockType.getMaterial() == BlockMaterial.Empty) {
/* 278 */       return false;
/*     */     }
/*     */     
/* 281 */     return !canReplace(worldBlockId, assetMap);
/*     */   }
/*     */   
/*     */   private boolean canReplace(int worldBlockId, BlockTypeAssetMap<String, BlockType> assetMap) {
/* 285 */     BlockType worldBlockType = (BlockType)assetMap.getAsset(worldBlockId);
/*     */     
/* 287 */     if (worldBlockType == null || worldBlockType.getMaterial() == BlockMaterial.Empty) {
/* 288 */       return true;
/*     */     }
/*     */     
/* 291 */     for (int tagIndex : this.replaceMaskTagIndices) {
/* 292 */       if (assetMap.getIndexesForTag(tagIndex).contains(worldBlockId)) {
/* 293 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 297 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void remove(ComponentAccessor<ChunkStore> commandBuffer, Ref<ChunkStore> sectionRef, Ref<ChunkStore> blockRef, int x, int y, int z) {
/* 302 */     super.remove(commandBuffer, sectionRef, blockRef, x, y, z);
/*     */     
/* 304 */     ChunkSection section = (ChunkSection)commandBuffer.getComponent(sectionRef, ChunkSection.getComponentType());
/*     */     
/* 306 */     int worldX = ChunkUtil.worldCoordFromLocalCoord(section.getX(), x);
/* 307 */     int worldY = ChunkUtil.worldCoordFromLocalCoord(section.getY(), y);
/* 308 */     int worldZ = ChunkUtil.worldCoordFromLocalCoord(section.getZ(), z);
/*     */     
/* 310 */     FarmingBlock farming = (FarmingBlock)commandBuffer.getComponent(blockRef, FarmingBlock.getComponentType());
/* 311 */     IPrefabBuffer prefab = getCachedPrefab(worldX, worldY, worldZ, farming.getGeneration() - 1);
/* 312 */     RotationTuple rotation = ((BlockSection)commandBuffer.getComponent(sectionRef, BlockSection.getComponentType())).getRotation(x, y, z);
/* 313 */     double rate = computeParticlesRate(prefab);
/* 314 */     World world = ((ChunkStore)commandBuffer.getExternalData()).getWorld();
/*     */     
/* 316 */     world.execute(() -> PrefabUtil.remove(prefab, world, new Vector3i(worldX, worldY, worldZ), rotation.yaw(), true, (Random)new FastRandom(), 2, rate));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private IPrefabBuffer getCachedPrefab(int x, int y, int z, int generation) {
/* 323 */     return PrefabBufferUtil.getCached(((PrefabStage)this.prefabStages.get(HashUtil.random(x, y, z, generation))).getResolvedPath());
/*     */   }
/*     */   
/*     */   private void processConfig() {
/* 327 */     this.replaceMaskTagIndices = new int[this.replaceMaskTags.length];
/*     */     
/* 329 */     for (int i = 0; i < this.replaceMaskTags.length; i++) {
/* 330 */       this.replaceMaskTagIndices[i] = AssetRegistry.getOrCreateTagIndex(this.replaceMaskTags[i]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 337 */     return "PrefabFarmingStageData{replaceMaskTags=" + Arrays.toString((Object[])this.replaceMaskTags) + ", prefabStages=" + String.valueOf(this.prefabStages) + "}";
/*     */   }
/*     */   
/*     */   public static class PrefabStage
/*     */     implements IWeightedElement
/*     */   {
/* 343 */     public static final PrefabStage[] EMPTY_ARRAY = new PrefabStage[0];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public static Codec<PrefabStage> CODEC;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/* 357 */       CODEC = (Codec<PrefabStage>)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(PrefabStage.class, PrefabStage::new).append(new KeyedCodec("Weight", (Codec)Codec.INTEGER), (prefabStage, integer) -> prefabStage.weight = integer.intValue(), prefabStage -> Integer.valueOf(prefabStage.weight)).addValidator(Validators.greaterThanOrEqual(Integer.valueOf(1))).add()).append(new KeyedCodec("Path", (Codec)Codec.STRING), (prefabStage, s) -> prefabStage.path = s, prefabStage -> prefabStage.path).addValidator(Validators.nonNull()).add()).build();
/*     */     }
/* 359 */     protected int weight = 1;
/*     */     
/*     */     protected String path;
/*     */     
/*     */     public double getWeight() {
/* 364 */       return this.weight;
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public Path getResolvedPath() {
/* 369 */       for (AssetPack pack : AssetModule.get().getAssetPacks()) {
/* 370 */         Path assetPath = pack.getRoot().resolve("Server").resolve("Prefabs").resolve(this.path);
/* 371 */         if (Files.exists(assetPath, new java.nio.file.LinkOption[0])) return assetPath; 
/*     */       } 
/* 373 */       return PrefabStore.get().getAssetPrefabsPath().resolve(this.path);
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 379 */       return "PrefabStage{weight=" + this.weight + ", path='" + this.path + "'}";
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\farming\config\stages\PrefabFarmingStageData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */