/*    */ package com.hypixel.hytale.builtin.adventure.farming.interactions;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.adventure.farming.states.FarmingBlock;
/*    */ import com.hypixel.hytale.builtin.adventure.farming.states.TilledSoilBlock;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.math.util.ChunkUtil;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import com.hypixel.hytale.protocol.InteractionState;
/*    */ import com.hypixel.hytale.protocol.InteractionType;
/*    */ import com.hypixel.hytale.protocol.WaitForDataFrom;
/*    */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*    */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*    */ import com.hypixel.hytale.server.core.modules.block.BlockModule;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.client.SimpleBlockInteraction;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class FertilizeSoilInteraction extends SimpleBlockInteraction {
/*    */   public static final BuilderCodec<FertilizeSoilInteraction> CODEC;
/*    */   protected String[] refreshModifiers;
/*    */   
/*    */   static {
/* 34 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(FertilizeSoilInteraction.class, FertilizeSoilInteraction::new, SimpleBlockInteraction.CODEC).documentation("If the target block is farmable then set it to fertilized.")).addField(new KeyedCodec("RefreshModifiers", (Codec)Codec.STRING_ARRAY), (interaction, refreshModifiers) -> interaction.refreshModifiers = refreshModifiers, interaction -> interaction.refreshModifiers)).build();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public WaitForDataFrom getWaitForDataFrom() {
/* 41 */     return WaitForDataFrom.Server;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void interactWithBlock(@Nonnull World world, @Nonnull CommandBuffer<EntityStore> commandBuffer, @Nonnull InteractionType type, @Nonnull InteractionContext context, @Nullable ItemStack itemInHand, @Nonnull Vector3i targetBlock, @Nonnull CooldownHandler cooldownHandler) {
/* 46 */     int x = targetBlock.getX();
/* 47 */     int z = targetBlock.getZ();
/*    */     
/* 49 */     WorldChunk worldChunk = world.getChunk(ChunkUtil.indexChunkFromBlock(x, z));
/* 50 */     Ref<ChunkStore> blockRef = worldChunk.getBlockComponentEntity(x, targetBlock.getY(), z);
/* 51 */     if (blockRef == null) {
/* 52 */       blockRef = BlockModule.ensureBlockEntity(worldChunk, targetBlock.x, targetBlock.y, targetBlock.z);
/*    */     }
/* 54 */     if (blockRef == null) {
/* 55 */       (context.getState()).state = InteractionState.Failed;
/*    */       
/*    */       return;
/*    */     } 
/* 59 */     Store<ChunkStore> chunkStore = world.getChunkStore().getStore();
/*    */     
/* 61 */     TilledSoilBlock soil = (TilledSoilBlock)chunkStore.getComponent(blockRef, TilledSoilBlock.getComponentType());
/* 62 */     if (soil != null && !soil.isFertilized()) {
/* 63 */       soil.setFertilized(true);
/* 64 */       worldChunk.setTicking(x, targetBlock.getY(), z, true);
/* 65 */       worldChunk.setTicking(x, targetBlock.getY() + 1, z, true);
/*    */       
/*    */       return;
/*    */     } 
/* 69 */     FarmingBlock farmingState = (FarmingBlock)chunkStore.getComponent(blockRef, FarmingBlock.getComponentType());
/* 70 */     if (farmingState == null) {
/* 71 */       (context.getState()).state = InteractionState.Failed;
/*    */       
/*    */       return;
/*    */     } 
/* 75 */     Ref<ChunkStore> soilRef = worldChunk.getBlockComponentEntity(x, targetBlock.getY() - 1, z);
/* 76 */     if (soilRef == null) {
/* 77 */       (context.getState()).state = InteractionState.Failed;
/*    */       return;
/*    */     } 
/* 80 */     soil = (TilledSoilBlock)chunkStore.getComponent(soilRef, TilledSoilBlock.getComponentType());
/* 81 */     if (soil == null || soil.isFertilized()) {
/* 82 */       (context.getState()).state = InteractionState.Failed;
/*    */       
/*    */       return;
/*    */     } 
/* 86 */     soil.setFertilized(true);
/* 87 */     worldChunk.setTicking(x, targetBlock.getY() - 1, z, true);
/* 88 */     worldChunk.setTicking(x, targetBlock.getY(), z, true);
/*    */   }
/*    */   
/*    */   protected void simulateInteractWithBlock(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nullable ItemStack itemInHand, @Nonnull World world, @Nonnull Vector3i targetBlock) {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\farming\interactions\FertilizeSoilInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */