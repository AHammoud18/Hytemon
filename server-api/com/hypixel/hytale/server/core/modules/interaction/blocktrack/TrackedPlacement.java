/*     */ package com.hypixel.hytale.server.core.modules.interaction.blocktrack;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.modules.block.BlockModule;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class TrackedPlacement implements Component<ChunkStore> {
/*     */   public static ComponentType<ChunkStore, TrackedPlacement> getComponentType() {
/*  22 */     return InteractionModule.get().getTrackedPlacementComponentType();
/*     */   }
/*     */ 
/*     */   
/*     */   public static final BuilderCodec<TrackedPlacement> CODEC;
/*     */   
/*     */   private String blockName;
/*     */ 
/*     */   
/*     */   static {
/*  32 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(TrackedPlacement.class, TrackedPlacement::new).append(new KeyedCodec("BlockName", (Codec)Codec.STRING), (o, v) -> o.blockName = v, o -> o.blockName).add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public TrackedPlacement() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public TrackedPlacement(String blockName) {
/*  42 */     this.blockName = blockName;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Component<ChunkStore> clone() {
/*  49 */     return new TrackedPlacement(this.blockName);
/*     */   }
/*     */   
/*     */   public static class OnAddRemove
/*     */     extends RefSystem<ChunkStore> {
/*  54 */     private static final ComponentType<ChunkStore, TrackedPlacement> COMPONENT_TYPE = TrackedPlacement.getComponentType();
/*  55 */     private static final ResourceType<ChunkStore, BlockCounter> BLOCK_COUNTER_RESOURCE_TYPE = BlockCounter.getResourceType();
/*     */ 
/*     */     
/*     */     public void onEntityAdded(@Nonnull Ref<ChunkStore> ref, @Nonnull AddReason reason, @Nonnull Store<ChunkStore> store, @Nonnull CommandBuffer<ChunkStore> commandBuffer) {
/*  59 */       if (reason != AddReason.SPAWN) {
/*     */         return;
/*     */       }
/*  62 */       BlockModule.BlockStateInfo blockInfo = (BlockModule.BlockStateInfo)commandBuffer.getComponent(ref, BlockModule.BlockStateInfo.getComponentType());
/*  63 */       assert blockInfo != null;
/*  64 */       Ref<ChunkStore> chunkRef = blockInfo.getChunkRef();
/*  65 */       if (chunkRef == null || !chunkRef.isValid()) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/*  70 */       BlockChunk blockChunk = (BlockChunk)commandBuffer.getComponent(chunkRef, BlockChunk.getComponentType());
/*  71 */       assert blockChunk != null;
/*  72 */       int blockId = blockChunk.getBlock(
/*  73 */           ChunkUtil.xFromBlockInColumn(blockInfo.getIndex()), 
/*  74 */           ChunkUtil.yFromBlockInColumn(blockInfo.getIndex()), 
/*  75 */           ChunkUtil.zFromBlockInColumn(blockInfo.getIndex()));
/*     */       
/*  77 */       BlockType blockType = (BlockType)BlockType.getAssetMap().getAsset(blockId);
/*  78 */       if (blockType == null)
/*     */         return; 
/*  80 */       BlockCounter counter = (BlockCounter)commandBuffer.getResource(BLOCK_COUNTER_RESOURCE_TYPE);
/*  81 */       String blockName = blockType.getId();
/*  82 */       counter.trackBlock(blockName);
/*     */       
/*  84 */       TrackedPlacement tracked = (TrackedPlacement)commandBuffer.getComponent(ref, COMPONENT_TYPE);
/*  85 */       assert tracked != null;
/*  86 */       tracked.blockName = blockName;
/*     */     }
/*     */ 
/*     */     
/*     */     public void onEntityRemove(@Nonnull Ref<ChunkStore> ref, @Nonnull RemoveReason reason, @Nonnull Store<ChunkStore> store, @Nonnull CommandBuffer<ChunkStore> commandBuffer) {
/*  91 */       if (reason != RemoveReason.REMOVE)
/*     */         return; 
/*  93 */       TrackedPlacement tracked = (TrackedPlacement)commandBuffer.getComponent(ref, COMPONENT_TYPE);
/*  94 */       assert tracked != null;
/*  95 */       BlockCounter counter = (BlockCounter)commandBuffer.getResource(BLOCK_COUNTER_RESOURCE_TYPE);
/*  96 */       counter.untrackBlock(tracked.blockName);
/*     */     }
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public Query<ChunkStore> getQuery() {
/* 102 */       return (Query)COMPONENT_TYPE;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\blocktrack\TrackedPlacement.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */