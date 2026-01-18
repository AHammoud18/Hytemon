/*    */ package com.hypixel.hytale.builtin.adventure.farming.config.stages;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.LateValidator;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*    */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.farming.FarmingStageData;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.section.ChunkSection;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockTypeFarmingStageData
/*    */   extends FarmingStageData
/*    */ {
/*    */   @Nonnull
/*    */   public static BuilderCodec<BlockTypeFarmingStageData> CODEC;
/*    */   protected String block;
/*    */   
/*    */   static {
/* 31 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(BlockTypeFarmingStageData.class, BlockTypeFarmingStageData::new, FarmingStageData.BASE_CODEC).append(new KeyedCodec("Block", (Codec)Codec.STRING), (stage, block) -> stage.block = block, stage -> stage.block).addValidatorLate(() -> BlockType.VALIDATOR_CACHE.getValidator().late()).add()).build();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getBlock() {
/* 36 */     return this.block;
/*    */   }
/*    */ 
/*    */   
/*    */   public void apply(ComponentAccessor<ChunkStore> commandBuffer, Ref<ChunkStore> sectionRef, Ref<ChunkStore> blockRef, int x, int y, int z, @Nullable FarmingStageData previousStage) {
/* 41 */     super.apply(commandBuffer, sectionRef, blockRef, x, y, z, previousStage);
/* 42 */     ChunkSection section = (ChunkSection)commandBuffer.getComponent(sectionRef, ChunkSection.getComponentType());
/* 43 */     WorldChunk worldChunk = (WorldChunk)commandBuffer.getComponent(section.getChunkColumnReference(), WorldChunk.getComponentType());
/*    */     
/* 45 */     int blockId = BlockType.getAssetMap().getIndex(this.block);
/* 46 */     if (blockId == worldChunk.getBlock(x, y, z))
/*    */       return; 
/* 48 */     BlockType blockType = (BlockType)BlockType.getAssetMap().getAsset(blockId);
/*    */ 
/*    */     
/* 51 */     ((ChunkStore)commandBuffer.getExternalData()).getWorld().execute(() -> worldChunk.setBlock(x, y, z, blockId, blockType, worldChunk.getRotationIndex(x, y, z), 0, 2));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 59 */     return "BlockTypeFarmingStageData{block=" + this.block + "} " + super
/*    */       
/* 61 */       .toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\farming\config\stages\BlockTypeFarmingStageData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */