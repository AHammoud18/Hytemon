/*    */ package com.hypixel.hytale.builtin.adventure.objectives.interactions;
/*    */ 
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.math.util.ChunkUtil;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import com.hypixel.hytale.protocol.InteractionState;
/*    */ import com.hypixel.hytale.protocol.InteractionType;
/*    */ import com.hypixel.hytale.protocol.WaitForDataFrom;
/*    */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*    */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*    */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.client.SimpleBlockInteraction;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockComponentChunk;
/*    */ import com.hypixel.hytale.server.core.universe.world.meta.state.RespawnBlock;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.UUID;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class CanBreakRespawnPointInteraction extends SimpleBlockInteraction {
/* 27 */   public static final BuilderCodec<CanBreakRespawnPointInteraction> CODEC = BuilderCodec.builder(CanBreakRespawnPointInteraction.class, CanBreakRespawnPointInteraction::new, SimpleBlockInteraction.CODEC)
/* 28 */     .build();
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public WaitForDataFrom getWaitForDataFrom() {
/* 33 */     return WaitForDataFrom.Server;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void interactWithBlock(@Nonnull World world, @Nonnull CommandBuffer<EntityStore> commandBuffer, @Nonnull InteractionType type, @Nonnull InteractionContext context, @Nullable ItemStack itemInHand, @Nonnull Vector3i targetBlock, @Nonnull CooldownHandler cooldownHandler) {
/* 38 */     ChunkStore chunkStore = world.getChunkStore();
/* 39 */     Ref<ChunkStore> chunk = chunkStore.getChunkReference(ChunkUtil.indexChunkFromBlock(targetBlock.x, targetBlock.z));
/* 40 */     if (chunk == null) {
/* 41 */       (context.getState()).state = InteractionState.Failed;
/*    */       return;
/*    */     } 
/* 44 */     BlockComponentChunk blockComp = (BlockComponentChunk)chunkStore.getStore().getComponent(chunk, BlockComponentChunk.getComponentType());
/* 45 */     if (blockComp == null) {
/* 46 */       (context.getState()).state = InteractionState.Failed;
/*    */       
/*    */       return;
/*    */     } 
/* 50 */     Ref<ChunkStore> blockEntity = blockComp.getEntityReference(ChunkUtil.indexBlockInColumn(targetBlock.x, targetBlock.y, targetBlock.z));
/* 51 */     if (blockEntity == null) {
/*    */       
/* 53 */       (context.getState()).state = InteractionState.Finished;
/*    */       
/*    */       return;
/*    */     } 
/* 57 */     RespawnBlock respawnState = (RespawnBlock)chunkStore.getStore().getComponent(blockEntity, RespawnBlock.getComponentType());
/* 58 */     if (respawnState == null) {
/*    */       
/* 60 */       (context.getState()).state = InteractionState.Finished;
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 65 */     UUIDComponent uuidComponent = (UUIDComponent)commandBuffer.getComponent(context.getOwningEntity(), UUIDComponent.getComponentType());
/* 66 */     if (uuidComponent == null) {
/*    */       
/* 68 */       (context.getState()).state = InteractionState.Failed;
/*    */       
/*    */       return;
/*    */     } 
/* 72 */     UUID ownerUUID = respawnState.getOwnerUUID();
/*    */ 
/*    */     
/* 75 */     if (ownerUUID == null || uuidComponent.getUuid().equals(ownerUUID)) {
/* 76 */       (context.getState()).state = InteractionState.Finished;
/*    */       return;
/*    */     } 
/* 79 */     (context.getState()).state = InteractionState.Failed;
/*    */   }
/*    */   
/*    */   protected void simulateInteractWithBlock(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nullable ItemStack itemInHand, @Nonnull World world, @Nonnull Vector3i targetBlock) {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\interactions\CanBreakRespawnPointInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */