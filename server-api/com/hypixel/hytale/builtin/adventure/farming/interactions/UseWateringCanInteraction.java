/*     */ package com.hypixel.hytale.builtin.adventure.farming.interactions;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.adventure.farming.states.FarmingBlock;
/*     */ import com.hypixel.hytale.builtin.adventure.farming.states.TilledSoilBlock;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.protocol.InteractionState;
/*     */ import com.hypixel.hytale.protocol.InteractionType;
/*     */ import com.hypixel.hytale.protocol.WaitForDataFrom;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.modules.block.BlockModule;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.client.SimpleBlockInteraction;
/*     */ import com.hypixel.hytale.server.core.modules.time.WorldTimeResource;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.time.Instant;
/*     */ import java.time.temporal.ChronoUnit;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UseWateringCanInteraction
/*     */   extends SimpleBlockInteraction
/*     */ {
/*     */   public static final BuilderCodec<UseWateringCanInteraction> CODEC;
/*     */   protected long duration;
/*     */   protected String[] refreshModifiers;
/*     */   
/*     */   static {
/*  42 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(UseWateringCanInteraction.class, UseWateringCanInteraction::new, SimpleBlockInteraction.CODEC).documentation("Waters the target farmable block.")).addField(new KeyedCodec("Duration", (Codec)Codec.LONG), (interaction, duration) -> interaction.duration = duration.longValue(), interaction -> Long.valueOf(interaction.duration))).addField(new KeyedCodec("RefreshModifiers", (Codec)Codec.STRING_ARRAY), (interaction, refreshModifiers) -> interaction.refreshModifiers = refreshModifiers, interaction -> interaction.refreshModifiers)).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public WaitForDataFrom getWaitForDataFrom() {
/*  50 */     return WaitForDataFrom.Server;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void interactWithBlock(@Nonnull World world, @Nonnull CommandBuffer<EntityStore> commandBuffer, @Nonnull InteractionType type, @Nonnull InteractionContext context, @Nullable ItemStack itemInHand, @Nonnull Vector3i targetBlock, @Nonnull CooldownHandler cooldownHandler) {
/*  55 */     int x = targetBlock.getX();
/*  56 */     int z = targetBlock.getZ();
/*     */     
/*  58 */     WorldChunk worldChunk = world.getChunk(ChunkUtil.indexChunkFromBlock(x, z));
/*  59 */     Ref<ChunkStore> blockRef = worldChunk.getBlockComponentEntity(x, targetBlock.getY(), z);
/*  60 */     if (blockRef == null) {
/*  61 */       blockRef = BlockModule.ensureBlockEntity(worldChunk, targetBlock.x, targetBlock.y, targetBlock.z);
/*     */     }
/*  63 */     if (blockRef == null) {
/*  64 */       (context.getState()).state = InteractionState.Failed;
/*     */       
/*     */       return;
/*     */     } 
/*  68 */     Store<ChunkStore> chunkStore = world.getChunkStore().getStore();
/*     */     
/*  70 */     WorldTimeResource worldTimeResource = (WorldTimeResource)commandBuffer.getResource(WorldTimeResource.getResourceType());
/*     */     
/*  72 */     TilledSoilBlock soil = (TilledSoilBlock)chunkStore.getComponent(blockRef, TilledSoilBlock.getComponentType());
/*  73 */     if (soil != null) {
/*  74 */       Instant instant = worldTimeResource.getGameTime().plus(this.duration, ChronoUnit.SECONDS);
/*  75 */       soil.setWateredUntil(instant);
/*  76 */       worldChunk.setTicking(x, targetBlock.getY(), z, true);
/*  77 */       worldChunk.getBlockChunk().getSectionAtBlockY(targetBlock.y).scheduleTick(ChunkUtil.indexBlock(x, targetBlock.y, z), instant);
/*     */       
/*  79 */       worldChunk.setTicking(x, targetBlock.getY() + 1, z, true);
/*     */       
/*     */       return;
/*     */     } 
/*  83 */     FarmingBlock farmingState = (FarmingBlock)chunkStore.getComponent(blockRef, FarmingBlock.getComponentType());
/*  84 */     if (farmingState == null) {
/*  85 */       (context.getState()).state = InteractionState.Failed;
/*     */       
/*     */       return;
/*     */     } 
/*  89 */     Ref<ChunkStore> soilRef = worldChunk.getBlockComponentEntity(x, targetBlock.getY() - 1, z);
/*  90 */     if (soilRef == null) {
/*  91 */       (context.getState()).state = InteractionState.Failed;
/*     */       return;
/*     */     } 
/*  94 */     soil = (TilledSoilBlock)chunkStore.getComponent(soilRef, TilledSoilBlock.getComponentType());
/*  95 */     if (soil == null) {
/*  96 */       (context.getState()).state = InteractionState.Failed;
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 101 */     Instant wateredUntil = worldTimeResource.getGameTime().plus(this.duration, ChronoUnit.SECONDS);
/*     */     
/* 103 */     soil.setWateredUntil(wateredUntil);
/* 104 */     worldChunk.getBlockChunk().getSectionAtBlockY(targetBlock.y - 1).scheduleTick(ChunkUtil.indexBlock(x, targetBlock.y - 1, z), wateredUntil);
/* 105 */     worldChunk.setTicking(x, targetBlock.getY() - 1, z, true);
/*     */     
/* 107 */     worldChunk.setTicking(x, targetBlock.getY(), z, true);
/*     */   }
/*     */   
/*     */   protected void simulateInteractWithBlock(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nullable ItemStack itemInHand, @Nonnull World world, @Nonnull Vector3i targetBlock) {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\farming\interactions\UseWateringCanInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */