/*    */ package com.hypixel.hytale.builtin.adventure.farming.config.modifiers;
/*    */ import com.hypixel.hytale.builtin.adventure.farming.states.TilledSoilBlock;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.math.util.ChunkUtil;
/*    */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.farming.GrowthModifierAsset;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockComponentChunk;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.section.ChunkSection;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*    */ import java.util.function.Supplier;
/*    */ 
/*    */ public class FertilizerGrowthModifierAsset extends GrowthModifierAsset {
/* 14 */   public static final BuilderCodec<FertilizerGrowthModifierAsset> CODEC = BuilderCodec.builder(FertilizerGrowthModifierAsset.class, FertilizerGrowthModifierAsset::new, ABSTRACT_CODEC)
/* 15 */     .build();
/*    */ 
/*    */   
/*    */   public double getCurrentGrowthMultiplier(CommandBuffer<ChunkStore> commandBuffer, Ref<ChunkStore> sectionRef, Ref<ChunkStore> blockRef, int x, int y, int z, boolean initialTick) {
/* 19 */     ChunkSection chunkSection = (ChunkSection)commandBuffer.getComponent(sectionRef, ChunkSection.getComponentType());
/* 20 */     Ref<ChunkStore> chunk = chunkSection.getChunkColumnReference();
/* 21 */     BlockComponentChunk blockComponentChunk = (BlockComponentChunk)commandBuffer.getComponent(chunk, BlockComponentChunk.getComponentType());
/*    */     
/* 23 */     Ref<ChunkStore> blockRefBelow = blockComponentChunk.getEntityReference(ChunkUtil.indexBlockInColumn(x, y - 1, z));
/* 24 */     if (blockRefBelow == null) return 1.0D;
/*    */     
/* 26 */     TilledSoilBlock soil = (TilledSoilBlock)commandBuffer.getComponent(blockRefBelow, TilledSoilBlock.getComponentType());
/* 27 */     if (soil != null && soil.isFertilized()) {
/* 28 */       return super.getCurrentGrowthMultiplier(commandBuffer, sectionRef, blockRef, x, y, z, initialTick);
/*    */     }
/* 30 */     return 1.0D;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\farming\config\modifiers\FertilizerGrowthModifierAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */