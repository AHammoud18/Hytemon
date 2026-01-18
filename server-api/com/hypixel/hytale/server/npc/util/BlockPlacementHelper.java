/*    */ package com.hypixel.hytale.server.npc.util;
/*    */ 
/*    */ import com.hypixel.hytale.math.shape.Box;
/*    */ import com.hypixel.hytale.math.util.ChunkUtil;
/*    */ import com.hypixel.hytale.protocol.BlockMaterial;
/*    */ import com.hypixel.hytale.server.core.asset.type.blockhitbox.BlockBoundingBoxes;
/*    */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockPlacementHelper
/*    */ {
/*    */   public static boolean canPlaceUnitBlock(@Nonnull World world, BlockType placedBlockType, boolean allowEmptyMaterials, int x, int y, int z) {
/* 22 */     WorldChunk chunk = world.getChunkIfInMemory(ChunkUtil.indexChunkFromBlock(x, z));
/* 23 */     if (chunk == null) return false;
/*    */ 
/*    */     
/* 26 */     int target = chunk.getBlock(x, y, z);
/* 27 */     BlockType targetBlockType = (BlockType)BlockType.getAssetMap().getAsset(target);
/* 28 */     if (!testBlock(placedBlockType, targetBlockType, allowEmptyMaterials)) return false;
/*    */ 
/*    */     
/* 31 */     target = chunk.getBlock(x, y - 1, z);
/* 32 */     targetBlockType = (BlockType)BlockType.getAssetMap().getAsset(target);
/* 33 */     int filler = chunk.getFiller(x, y - 1, z);
/* 34 */     int rotation = chunk.getRotationIndex(x, y - 1, z);
/* 35 */     return testSupportingBlock(targetBlockType, rotation, filler);
/*    */   }
/*    */   
/*    */   public static boolean canPlaceBlock(@Nonnull World world, @Nonnull BlockType placedBlockType, int rotationIndex, boolean allowEmptyMaterials, int x, int y, int z) {
/* 39 */     return world.testBlockTypes(x, y, z, placedBlockType, rotationIndex, (blockX, blockY, blockZ, blockType, rotation, filler) -> testBlock(placedBlockType, blockType, allowEmptyMaterials));
/*    */   }
/*    */   
/*    */   public static boolean testBlock(BlockType placedBlockType, @Nonnull BlockType blockType, boolean allowEmptyMaterials) {
/* 43 */     if (blockType == BlockType.EMPTY) return true; 
/* 44 */     if (allowEmptyMaterials && blockType.getMaterial() == BlockMaterial.Empty) return true; 
/* 45 */     return true;
/*    */   }
/*    */   
/*    */   public static boolean testSupportingBlock(@Nonnull BlockType blockType, int rotation, int filler) {
/* 49 */     Box targetHitbox = ((BlockBoundingBoxes)BlockBoundingBoxes.getAssetMap().getAsset(blockType.getHitboxTypeIndex())).get(rotation).getBoundingBox();
/* 50 */     return (blockType != BlockType.EMPTY && blockType != BlockType.UNKNOWN && blockType.getMaterial() == BlockMaterial.Solid && filler == 0 && targetHitbox
/* 51 */       .isUnitBox());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\np\\util\BlockPlacementHelper.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */