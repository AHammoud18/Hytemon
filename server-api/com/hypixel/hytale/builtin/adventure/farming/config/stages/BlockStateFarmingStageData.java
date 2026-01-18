/*    */ package com.hypixel.hytale.builtin.adventure.farming.config.stages;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
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
/*    */ public class BlockStateFarmingStageData
/*    */   extends FarmingStageData
/*    */ {
/*    */   @Nonnull
/*    */   public static BuilderCodec<BlockStateFarmingStageData> CODEC;
/*    */   protected String state;
/*    */   
/*    */   static {
/* 27 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(BlockStateFarmingStageData.class, BlockStateFarmingStageData::new, FarmingStageData.BASE_CODEC).append(new KeyedCodec("State", (Codec)Codec.STRING), (stage, block) -> stage.state = block, stage -> stage.state).add()).build();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getState() {
/* 32 */     return this.state;
/*    */   }
/*    */ 
/*    */   
/*    */   public void apply(ComponentAccessor<ChunkStore> commandBuffer, Ref<ChunkStore> sectionRef, Ref<ChunkStore> blockRef, int x, int y, int z, @Nullable FarmingStageData previousStage) {
/* 37 */     super.apply(commandBuffer, sectionRef, blockRef, x, y, z, previousStage);
/* 38 */     ChunkSection section = (ChunkSection)commandBuffer.getComponent(sectionRef, ChunkSection.getComponentType());
/* 39 */     WorldChunk worldChunk = (WorldChunk)commandBuffer.getComponent(section.getChunkColumnReference(), WorldChunk.getComponentType());
/*    */     
/* 41 */     int origBlockId = worldChunk.getBlock(x, y, z);
/* 42 */     BlockType origBlockType = (BlockType)BlockType.getAssetMap().getAsset(origBlockId);
/* 43 */     BlockType blockType = origBlockType.getBlockForState(this.state);
/* 44 */     if (blockType == null)
/* 45 */       return;  int newType = BlockType.getAssetMap().getIndex(blockType.getId());
/*    */     
/* 47 */     if (origBlockId == newType)
/*    */       return; 
/* 49 */     int rotation = worldChunk.getRotationIndex(x, y, z);
/*    */ 
/*    */     
/* 52 */     ((ChunkStore)commandBuffer.getExternalData()).getWorld().execute(() -> worldChunk.setBlock(x, y, z, newType, blockType, rotation, 0, 2));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 60 */     return "BlockStateFarmingStageData{state='" + this.state + "'} " + super
/*    */       
/* 62 */       .toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\farming\config\stages\BlockStateFarmingStageData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */