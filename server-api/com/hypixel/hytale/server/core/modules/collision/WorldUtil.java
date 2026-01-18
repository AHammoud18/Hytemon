/*     */ package com.hypixel.hytale.server.core.modules.collision;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.map.BlockTypeAssetMap;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.protocol.BlockMaterial;
/*     */ import com.hypixel.hytale.server.core.asset.type.blockhitbox.BlockBoundingBoxes;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.asset.type.fluid.Fluid;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.ChunkColumn;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.BlockSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.FluidSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.util.FillerBlockUtil;
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
/*     */ public final class WorldUtil
/*     */ {
/*     */   public static boolean isFluidOnlyBlock(@Nonnull BlockType blockType, int fluidId) {
/*  33 */     return (blockType.getMaterial() == BlockMaterial.Empty && fluidId != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isSolidOnlyBlock(@Nonnull BlockType blockType, int fluidId) {
/*  44 */     return (blockType.getMaterial() == BlockMaterial.Solid && fluidId == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isEmptyOnlyBlock(@Nonnull BlockType blockType, int fluidId) {
/*  55 */     return (blockType.getMaterial() == BlockMaterial.Empty && fluidId == 0);
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
/*     */   public static int getFluidIdAtPosition(@Nonnull ComponentAccessor<ChunkStore> chunkStore, @Nonnull ChunkColumn chunkColumnComponent, int x, int y, int z) {
/*  73 */     if (y < 0 || y >= 320) {
/*  74 */       return 0;
/*     */     }
/*     */     
/*  77 */     Ref<ChunkStore> sectionRef = chunkColumnComponent.getSection(ChunkUtil.chunkCoordinate(y));
/*  78 */     if (sectionRef == null || !sectionRef.isValid()) {
/*  79 */       return 0;
/*     */     }
/*     */     
/*  82 */     FluidSection fluidSectionComponent = (FluidSection)chunkStore.getComponent(sectionRef, FluidSection.getComponentType());
/*  83 */     if (fluidSectionComponent == null) {
/*  84 */       return 0;
/*     */     }
/*     */     
/*  87 */     return fluidSectionComponent.getFluidId(x, y, z);
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
/*     */   public static long getPackedMaterialAndFluidAtPosition(@Nonnull Ref<ChunkStore> chunkRef, @Nonnull ComponentAccessor<ChunkStore> chunkStore, double x, double y, double z) {
/* 111 */     if (y < 0.0D || y >= 320.0D) {
/* 112 */       return MathUtil.packLong(BlockMaterial.Empty.ordinal(), 0);
/*     */     }
/*     */     
/* 115 */     int blockX = MathUtil.floor(x);
/* 116 */     int blockY = MathUtil.floor(y);
/* 117 */     int blockZ = MathUtil.floor(z);
/*     */     
/* 119 */     ChunkColumn chunkColumnComponent = (ChunkColumn)chunkStore.getComponent(chunkRef, ChunkColumn.getComponentType());
/* 120 */     if (chunkColumnComponent == null) {
/* 121 */       return MathUtil.packLong(BlockMaterial.Empty.ordinal(), 0);
/*     */     }
/*     */     
/* 124 */     BlockChunk blockChunkComponent = (BlockChunk)chunkStore.getComponent(chunkRef, BlockChunk.getComponentType());
/* 125 */     if (blockChunkComponent == null) {
/* 126 */       return MathUtil.packLong(BlockMaterial.Empty.ordinal(), 0);
/*     */     }
/*     */ 
/*     */     
/* 130 */     BlockSection blockSection = blockChunkComponent.getSectionAtBlockY(blockY);
/*     */ 
/*     */     
/* 133 */     int fluidId = 0;
/* 134 */     Ref<ChunkStore> sectionRef = chunkColumnComponent.getSection(ChunkUtil.chunkCoordinate(y));
/* 135 */     if (sectionRef != null && sectionRef.isValid()) {
/*     */       
/* 137 */       FluidSection fluidSectionComponent = (FluidSection)chunkStore.getComponent(sectionRef, FluidSection.getComponentType());
/* 138 */       if (fluidSectionComponent != null) {
/*     */         
/* 140 */         fluidId = fluidSectionComponent.getFluidId(blockX, blockY, blockZ);
/* 141 */         if (fluidId != 0) {
/*     */           
/* 143 */           Fluid fluid = (Fluid)Fluid.getAssetMap().getAsset(fluidId);
/* 144 */           if (fluid != null) {
/* 145 */             double yTest = y - blockY;
/* 146 */             if (yTest > fluidSectionComponent.getFluidLevel(blockX, blockY, blockZ) / fluid.getMaxFluidLevel()) {
/* 147 */               fluidId = 0;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 155 */     int blockId = blockSection.get(blockX, blockY, blockZ);
/* 156 */     if (blockId == 0) {
/* 157 */       return MathUtil.packLong(BlockMaterial.Empty.ordinal(), fluidId);
/*     */     }
/*     */     
/* 160 */     BlockType blockType = (BlockType)BlockType.getAssetMap().getAsset(blockId);
/* 161 */     if (blockType == null || blockType.isUnknown()) {
/* 162 */       return MathUtil.packLong(BlockMaterial.Empty.ordinal(), fluidId);
/*     */     }
/*     */ 
/*     */     
/* 166 */     double relativeY = y - blockY;
/*     */     
/* 168 */     String blockTypeKey = blockType.getId();
/* 169 */     BlockType blockTypeAsset = (BlockType)BlockType.getAssetMap().getAsset(blockTypeKey);
/* 170 */     if (blockTypeAsset == null) {
/* 171 */       return MathUtil.packLong(BlockMaterial.Empty.ordinal(), fluidId);
/*     */     }
/*     */     
/* 174 */     BlockMaterial blockTypeMaterial = blockType.getMaterial();
/*     */ 
/*     */     
/* 177 */     int filler = blockSection.getFiller(blockX, blockY, blockZ);
/* 178 */     int rotation = blockSection.getRotationIndex(blockX, blockY, blockZ);
/*     */     
/* 180 */     if (filler != 0 && blockTypeAsset.getMaterial() == BlockMaterial.Solid) {
/*     */ 
/*     */       
/* 183 */       BlockBoundingBoxes boundingBoxes = (BlockBoundingBoxes)BlockBoundingBoxes.getAssetMap().getAsset(blockType.getHitboxTypeIndex());
/* 184 */       if (boundingBoxes == null) {
/* 185 */         return MathUtil.packLong(BlockMaterial.Empty.ordinal(), fluidId);
/*     */       }
/*     */       
/* 188 */       BlockBoundingBoxes.RotatedVariantBoxes rotatedBoxes = boundingBoxes.get(rotation);
/* 189 */       int fillerX = FillerBlockUtil.unpackX(filler);
/* 190 */       int fillerY = FillerBlockUtil.unpackY(filler);
/* 191 */       int fillerZ = FillerBlockUtil.unpackZ(filler);
/* 192 */       if (rotatedBoxes.containsPosition(x - blockX + fillerX, relativeY + fillerY, z - blockZ + fillerZ)) {
/* 193 */         return MathUtil.packLong(BlockMaterial.Solid.ordinal(), fluidId);
/*     */       }
/* 195 */     } else if (blockTypeMaterial == BlockMaterial.Solid) {
/* 196 */       BlockBoundingBoxes boundingBoxes = (BlockBoundingBoxes)BlockBoundingBoxes.getAssetMap().getAsset(blockType.getHitboxTypeIndex());
/* 197 */       if (boundingBoxes == null) {
/* 198 */         return MathUtil.packLong(BlockMaterial.Empty.ordinal(), fluidId);
/*     */       }
/*     */       
/* 201 */       BlockBoundingBoxes.RotatedVariantBoxes rotatedBoxes = boundingBoxes.get(rotation);
/* 202 */       if (rotatedBoxes.containsPosition(x - blockX, relativeY, z - blockZ)) {
/* 203 */         return MathUtil.packLong(BlockMaterial.Solid.ordinal(), fluidId);
/*     */       }
/*     */     } 
/*     */     
/* 207 */     return MathUtil.packLong(BlockMaterial.Empty.ordinal(), fluidId);
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
/*     */   public static int findFluidBlock(@Nonnull ComponentAccessor<ChunkStore> chunkStore, @Nonnull ChunkColumn chunkColumnComponent, @Nonnull BlockChunk blockChunkComponent, int x, int y, int z, boolean allowBubble) {
/* 232 */     if (y < 0 || y >= 320) {
/* 233 */       return -1;
/*     */     }
/*     */     
/* 236 */     if (getFluidIdAtPosition(chunkStore, chunkColumnComponent, x, y++, z) != 0) {
/* 237 */       return y;
/*     */     }
/*     */ 
/*     */     
/* 241 */     if (y == 320 || !allowBubble) {
/* 242 */       return -1;
/*     */     }
/*     */     
/* 245 */     BlockSection blockSection = blockChunkComponent.getSectionAtBlockY(y);
/* 246 */     int blockId = blockSection.get(x, y++, z);
/* 247 */     BlockType blockType = (BlockType)BlockType.getAssetMap().getAsset(blockId);
/* 248 */     BlockMaterial materialLowerBlock = (blockType != null) ? blockType.getMaterial() : BlockMaterial.Empty;
/*     */ 
/*     */     
/* 251 */     if (getFluidIdAtPosition(chunkStore, chunkColumnComponent, x, y++, z) != 0) {
/* 252 */       return y;
/*     */     }
/*     */ 
/*     */     
/* 256 */     if (materialLowerBlock != BlockMaterial.Solid || y == 320) {
/* 257 */       return -1;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 262 */     return (getFluidIdAtPosition(chunkStore, chunkColumnComponent, x, y++, z) != 0) ? y : -1;
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
/*     */   public static int getWaterLevel(@Nonnull ComponentAccessor<ChunkStore> chunkStore, @Nonnull ChunkColumn chunkColumnComponent, @Nonnull BlockChunk blockChunkComponent, int x, int z, int startY) {
/* 285 */     startY = findFluidBlock(chunkStore, chunkColumnComponent, blockChunkComponent, x, startY, z, true);
/* 286 */     if (startY == -1) {
/* 287 */       return -1;
/*     */     }
/*     */     
/* 290 */     while (startY + 1 < 320) {
/* 291 */       BlockSection blockSection = blockChunkComponent.getSectionAtBlockY(startY + 1);
/* 292 */       int blockId = blockSection.get(x, startY + 1, z);
/* 293 */       BlockType blockType = (BlockType)BlockType.getAssetMap().getAsset(blockId);
/* 294 */       if (blockType == null) {
/*     */         break;
/*     */       }
/*     */       
/* 298 */       int fluidId = getFluidIdAtPosition(chunkStore, chunkColumnComponent, x, startY + 1, z);
/* 299 */       if (!isFluidOnlyBlock(blockType, fluidId)) {
/*     */         break;
/*     */       }
/* 302 */       startY++;
/*     */     } 
/*     */     
/* 305 */     return startY;
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
/*     */   public static int findFarthestEmptySpaceBelow(@Nonnull ComponentAccessor<ChunkStore> chunkStore, @Nonnull ChunkColumn chunkColumnComponent, @Nonnull BlockChunk blockChunkComponent, int x, int y, int z, int yFail) {
/* 327 */     if (y < 0) {
/* 328 */       return yFail;
/*     */     }
/* 330 */     if (y >= 320) {
/* 331 */       y = 319;
/*     */     }
/*     */     
/* 334 */     BlockTypeAssetMap<String, BlockType> assetMap = BlockType.getAssetMap();
/*     */     
/* 336 */     int indexSection = ChunkUtil.indexSection(y);
/* 337 */     while (indexSection >= 0) {
/* 338 */       Ref<ChunkStore> sectionRef = chunkColumnComponent.getSection(indexSection);
/* 339 */       FluidSection fluidSectionComponent = (FluidSection)chunkStore.getComponent(sectionRef, FluidSection.getComponentType());
/* 340 */       BlockSection chunkSection = blockChunkComponent.getSectionAtIndex(indexSection);
/*     */       
/* 342 */       if (chunkSection.isSolidAir() && fluidSectionComponent != null && fluidSectionComponent.isEmpty()) {
/* 343 */         y = 32 * indexSection - 1;
/* 344 */         if (y <= 0) {
/* 345 */           return 0;
/*     */         }
/* 347 */         indexSection--;
/*     */         
/*     */         continue;
/*     */       } 
/* 351 */       int yBottom = 32 * indexSection--;
/* 352 */       while (y >= yBottom) {
/* 353 */         int blockId = chunkSection.get(x, y--, z);
/* 354 */         int fluidId = (fluidSectionComponent != null) ? fluidSectionComponent.getFluidId(x, y, z) : 0;
/*     */         
/* 356 */         if (blockId == 0 && fluidId != 0) {
/*     */           continue;
/*     */         }
/*     */         
/* 360 */         BlockType blockType = (BlockType)assetMap.getAsset(blockId);
/* 361 */         if (blockType == null || blockType.isUnknown()) {
/* 362 */           return y + 2;
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 368 */         int filler = chunkSection.getFiller(x, y, z);
/* 369 */         if (filler != 0 || !isEmptyOnlyBlock(blockType, fluidId)) {
/* 370 */           return y + 2;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 375 */     return 0;
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
/*     */   public static int findFarthestEmptySpaceAbove(@Nonnull ComponentAccessor<ChunkStore> chunkStore, @Nonnull ChunkColumn chunkColumnComponent, @Nonnull BlockChunk blockChunkComponent, int x, int y, int z, int yFail) {
/* 397 */     if (y >= 320) {
/* 398 */       return Integer.MAX_VALUE;
/*     */     }
/* 400 */     if (y < 0) {
/* 401 */       return yFail;
/*     */     }
/*     */     
/* 404 */     BlockTypeAssetMap<String, BlockType> assetMap = BlockType.getAssetMap();
/* 405 */     int sectionCount = blockChunkComponent.getSectionCount();
/*     */     
/* 407 */     int indexSection = ChunkUtil.indexSection(y);
/* 408 */     while (indexSection < sectionCount) {
/* 409 */       Ref<ChunkStore> sectionRef = chunkColumnComponent.getSection(indexSection);
/* 410 */       FluidSection fluidSectionComponent = (FluidSection)chunkStore.getComponent(sectionRef, FluidSection.getComponentType());
/* 411 */       BlockSection chunkSection = blockChunkComponent.getSectionAtIndex(indexSection);
/*     */       
/* 413 */       if (chunkSection.isSolidAir() && fluidSectionComponent != null && fluidSectionComponent.isEmpty()) {
/* 414 */         indexSection++;
/* 415 */         y = 32 * indexSection;
/* 416 */         if (y >= 320) {
/* 417 */           return 319;
/*     */         }
/*     */         
/*     */         continue;
/*     */       } 
/* 422 */       int yTop = 32 * ++indexSection;
/* 423 */       while (y < yTop) {
/* 424 */         int blockId = chunkSection.get(x, y++, z);
/* 425 */         int fluidId = (fluidSectionComponent != null) ? fluidSectionComponent.getFluidId(x, y, z) : 0;
/*     */         
/* 427 */         if (blockId == 0 && fluidId == 0) {
/*     */           continue;
/*     */         }
/*     */         
/* 431 */         BlockType blockType = (BlockType)assetMap.getAsset(blockId);
/* 432 */         if (blockType == null || blockType.isUnknown()) {
/* 433 */           return y - 1;
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 439 */         int filler = chunkSection.getFiller(x, y, z);
/* 440 */         if (filler != 0 || !isEmptyOnlyBlock(blockType, fluidId)) {
/* 441 */           return y - 1;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 446 */     return Integer.MAX_VALUE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\collision\WorldUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */