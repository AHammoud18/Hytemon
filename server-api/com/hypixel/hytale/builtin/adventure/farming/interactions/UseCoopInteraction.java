/*    */ package com.hypixel.hytale.builtin.adventure.farming.interactions;
/*    */ import com.hypixel.hytale.builtin.adventure.farming.states.CoopBlock;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import com.hypixel.hytale.protocol.InteractionState;
/*    */ import com.hypixel.hytale.protocol.InteractionType;
/*    */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*    */ import com.hypixel.hytale.server.core.entity.EntityUtils;
/*    */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*    */ import com.hypixel.hytale.server.core.entity.LivingEntity;
/*    */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*    */ import com.hypixel.hytale.server.core.inventory.container.CombinedItemContainer;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.client.SimpleBlockInteraction;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
/*    */ import org.checkerframework.checker.nullness.compatqual.NullableDecl;
/*    */ 
/*    */ public class UseCoopInteraction extends SimpleBlockInteraction {
/* 25 */   public static final BuilderCodec<UseCoopInteraction> CODEC = BuilderCodec.builder(UseCoopInteraction.class, UseCoopInteraction::new, SimpleBlockInteraction.CODEC)
/* 26 */     .build();
/*    */ 
/*    */   
/*    */   protected void interactWithBlock(@NonNullDecl World world, @NonNullDecl CommandBuffer<EntityStore> commandBuffer, @NonNullDecl InteractionType type, @NonNullDecl InteractionContext context, @NullableDecl ItemStack itemInHand, @NonNullDecl Vector3i targetBlock, @NonNullDecl CooldownHandler cooldownHandler) {
/* 30 */     int x = targetBlock.getX();
/* 31 */     int z = targetBlock.getZ();
/*    */     
/* 33 */     WorldChunk worldChunk = world.getChunk(ChunkUtil.indexChunkFromBlock(x, z));
/* 34 */     if (worldChunk == null) {
/* 35 */       (context.getState()).state = InteractionState.Failed;
/*    */       
/*    */       return;
/*    */     } 
/* 39 */     Ref<ChunkStore> blockRef = worldChunk.getBlockComponentEntity(x, targetBlock.getY(), z);
/* 40 */     if (blockRef == null) {
/* 41 */       blockRef = BlockModule.ensureBlockEntity(worldChunk, targetBlock.x, targetBlock.y, targetBlock.z);
/*    */     }
/*    */     
/* 44 */     if (blockRef == null) {
/* 45 */       (context.getState()).state = InteractionState.Failed;
/*    */       
/*    */       return;
/*    */     } 
/* 49 */     Store<ChunkStore> chunkStore = world.getChunkStore().getStore();
/*    */     
/* 51 */     CoopBlock coopBlockState = (CoopBlock)chunkStore.getComponent(blockRef, CoopBlock.getComponentType());
/* 52 */     if (coopBlockState == null) {
/* 53 */       (context.getState()).state = InteractionState.Failed;
/*    */       
/*    */       return;
/*    */     } 
/* 57 */     Ref<EntityStore> playerRef = context.getEntity();
/* 58 */     LivingEntity playerEntity = (LivingEntity)EntityUtils.getEntity(playerRef, (ComponentAccessor)commandBuffer);
/* 59 */     if (playerEntity == null) {
/* 60 */       (context.getState()).state = InteractionState.Failed;
/*    */       
/*    */       return;
/*    */     } 
/* 64 */     CombinedItemContainer playerInventoryContainer = playerEntity.getInventory().getCombinedHotbarFirst();
/* 65 */     if (playerInventoryContainer == null) {
/*    */       return;
/*    */     }
/* 68 */     coopBlockState.gatherProduceFromInventory((ItemContainer)playerInventoryContainer);
/*    */     
/* 70 */     BlockType currentBlockType = worldChunk.getBlockType(targetBlock);
/* 71 */     assert currentBlockType != null;
/*    */     
/* 73 */     worldChunk.setBlockInteractionState(targetBlock, currentBlockType, 
/* 74 */         coopBlockState.hasProduce() ? "Produce_Ready" : "default");
/*    */   }
/*    */   
/*    */   protected void simulateInteractWithBlock(@NonNullDecl InteractionType type, @NonNullDecl InteractionContext context, @NullableDecl ItemStack itemInHand, @NonNullDecl World world, @NonNullDecl Vector3i targetBlock) {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\farming\interactions\UseCoopInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */