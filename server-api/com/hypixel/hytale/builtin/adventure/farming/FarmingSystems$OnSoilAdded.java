/*    */ package com.hypixel.hytale.builtin.adventure.farming;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.adventure.farming.states.TilledSoilBlock;
/*    */ import com.hypixel.hytale.component.AddReason;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.RemoveReason;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.system.RefSystem;
/*    */ import com.hypixel.hytale.math.util.ChunkUtil;
/*    */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*    */ import com.hypixel.hytale.server.core.modules.block.BlockModule;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockChunk;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.section.BlockSection;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*    */ import java.time.Instant;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class OnSoilAdded
/*    */   extends RefSystem<ChunkStore>
/*    */ {
/* 46 */   private static final Query<ChunkStore> QUERY = (Query<ChunkStore>)Query.and(new Query[] {
/* 47 */         (Query)BlockModule.BlockStateInfo.getComponentType(), 
/* 48 */         (Query)TilledSoilBlock.getComponentType()
/*    */       });
/*    */ 
/*    */   
/*    */   public void onEntityAdded(@Nonnull Ref<ChunkStore> ref, @Nonnull AddReason reason, @Nonnull Store<ChunkStore> store, @Nonnull CommandBuffer<ChunkStore> commandBuffer) {
/* 53 */     TilledSoilBlock soil = (TilledSoilBlock)commandBuffer.getComponent(ref, TilledSoilBlock.getComponentType());
/* 54 */     assert soil != null;
/* 55 */     BlockModule.BlockStateInfo info = (BlockModule.BlockStateInfo)commandBuffer.getComponent(ref, BlockModule.BlockStateInfo.getComponentType());
/* 56 */     assert info != null;
/*    */     
/* 58 */     if (!soil.isPlanted()) {
/* 59 */       int x = ChunkUtil.xFromBlockInColumn(info.getIndex());
/* 60 */       int y = ChunkUtil.yFromBlockInColumn(info.getIndex());
/* 61 */       int z = ChunkUtil.zFromBlockInColumn(info.getIndex());
/*    */       
/* 63 */       assert info.getChunkRef() != null;
/* 64 */       BlockChunk blockChunk = (BlockChunk)commandBuffer.getComponent(info.getChunkRef(), BlockChunk.getComponentType());
/* 65 */       assert blockChunk != null;
/* 66 */       BlockSection blockSection = blockChunk.getSectionAtBlockY(y);
/*    */ 
/*    */       
/* 69 */       Instant decayTime = soil.getDecayTime();
/* 70 */       if (decayTime == null) {
/*    */ 
/*    */         
/* 73 */         BlockType blockType = (BlockType)BlockType.getAssetMap().getAsset(blockSection.get(x, y, z));
/* 74 */         FarmingSystems.updateSoilDecayTime(commandBuffer, soil, blockType);
/*    */       } 
/* 76 */       if (decayTime == null) {
/*    */         return;
/*    */       }
/*    */       
/* 80 */       blockSection.scheduleTick(ChunkUtil.indexBlock(x, y, z), decayTime);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onEntityRemove(@Nonnull Ref<ChunkStore> ref, @Nonnull RemoveReason reason, @Nonnull Store<ChunkStore> store, @Nonnull CommandBuffer<ChunkStore> commandBuffer) {}
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public Query<ChunkStore> getQuery() {
/* 92 */     return QUERY;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\farming\FarmingSystems$OnSoilAdded.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */