/*    */ package com.hypixel.hytale.builtin.adventure.farming.interactions;
/*    */ import com.hypixel.hytale.builtin.adventure.farming.FarmingUtil;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import com.hypixel.hytale.protocol.InteractionType;
/*    */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*    */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*    */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.client.SimpleBlockInteraction;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockChunk;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.section.BlockSection;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class HarvestCropInteraction extends SimpleBlockInteraction {
/* 22 */   public static final BuilderCodec<HarvestCropInteraction> CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(HarvestCropInteraction.class, HarvestCropInteraction::new, SimpleBlockInteraction.CODEC)
/* 23 */     .documentation("Harvests the resources from the target farmable block."))
/* 24 */     .build();
/*    */ 
/*    */   
/*    */   protected void interactWithBlock(@Nonnull World world, @Nonnull CommandBuffer<EntityStore> commandBuffer, @Nonnull InteractionType type, @Nonnull InteractionContext context, @Nullable ItemStack itemInHand, @Nonnull Vector3i targetBlock, @Nonnull CooldownHandler cooldownHandler) {
/* 28 */     Ref<EntityStore> ref = context.getEntity();
/*    */     
/* 30 */     ChunkStore chunkStore = world.getChunkStore();
/* 31 */     long chunkIndex = ChunkUtil.indexChunkFromBlock(targetBlock.x, targetBlock.z);
/* 32 */     Ref<ChunkStore> chunkRef = chunkStore.getChunkReference(chunkIndex);
/* 33 */     if (chunkRef == null || !chunkRef.isValid())
/*    */       return; 
/* 35 */     BlockChunk blockChunkComponent = (BlockChunk)chunkStore.getStore().getComponent(chunkRef, BlockChunk.getComponentType());
/* 36 */     assert blockChunkComponent != null;
/*    */     
/* 38 */     BlockSection section = blockChunkComponent.getSectionAtBlockY(targetBlock.y);
/* 39 */     if (section == null)
/*    */       return; 
/* 41 */     WorldChunk worldChunkComponent = (WorldChunk)chunkStore.getStore().getComponent(chunkRef, WorldChunk.getComponentType());
/* 42 */     assert worldChunkComponent != null;
/*    */     
/* 44 */     BlockType blockType = worldChunkComponent.getBlockType(targetBlock);
/* 45 */     if (blockType == null)
/*    */       return; 
/* 47 */     int rotationIndex = section.getRotationIndex(targetBlock.x, targetBlock.y, targetBlock.z);
/* 48 */     FarmingUtil.harvest(world, (ComponentAccessor)commandBuffer, ref, blockType, rotationIndex, targetBlock);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void simulateInteractWithBlock(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nullable ItemStack itemInHand, @Nonnull World world, @Nonnull Vector3i targetBlock) {}
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 59 */     return "HarvestCropInteraction{} " + super.toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\farming\interactions\HarvestCropInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */