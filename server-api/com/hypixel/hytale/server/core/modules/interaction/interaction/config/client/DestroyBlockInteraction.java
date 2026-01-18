/*    */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config.client;
/*    */ 
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.math.util.ChunkUtil;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import com.hypixel.hytale.protocol.BlockPosition;
/*    */ import com.hypixel.hytale.protocol.InteractionType;
/*    */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.BlockHarvestUtils;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class DestroyBlockInteraction extends SimpleInstantInteraction {
/*    */   @Nonnull
/* 24 */   public static final BuilderCodec<DestroyBlockInteraction> CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(DestroyBlockInteraction.class, DestroyBlockInteraction::new, SimpleInstantInteraction.CODEC)
/* 25 */     .documentation("Destroys the target block."))
/* 26 */     .build();
/*    */ 
/*    */   
/*    */   protected void firstRun(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nonnull CooldownHandler cooldownHandler) {
/* 30 */     BlockPosition blockPosition = context.getTargetBlock();
/*    */ 
/*    */     
/* 33 */     if (blockPosition == null) {
/*    */       return;
/*    */     }
/*    */     
/* 37 */     Ref<EntityStore> ref = context.getEntity();
/* 38 */     CommandBuffer<EntityStore> commandBuffer = context.getCommandBuffer();
/* 39 */     assert commandBuffer != null;
/*    */     
/* 41 */     World world = ((EntityStore)commandBuffer.getExternalData()).getWorld();
/* 42 */     ChunkStore chunkStore = world.getChunkStore();
/*    */     
/* 44 */     long chunkIndex = ChunkUtil.indexChunkFromBlock(blockPosition.x, blockPosition.z);
/* 45 */     Ref<ChunkStore> chunkReference = chunkStore.getChunkReference(chunkIndex);
/* 46 */     if (chunkReference == null) {
/*    */       return;
/*    */     }
/*    */     
/* 50 */     Vector3i position = new Vector3i(blockPosition.x, blockPosition.y, blockPosition.z);
/* 51 */     Store<ChunkStore> chunkStoreStore = chunkStore.getStore();
/* 52 */     BlockHarvestUtils.performBlockBreak(ref, null, position, chunkReference, (ComponentAccessor)context.getCommandBuffer(), (ComponentAccessor)chunkStoreStore);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\client\DestroyBlockInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */