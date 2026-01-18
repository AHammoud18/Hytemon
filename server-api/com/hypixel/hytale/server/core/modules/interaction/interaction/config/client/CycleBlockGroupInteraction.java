/*     */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config.client;
/*     */ 
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.protocol.BlockSoundEvent;
/*     */ import com.hypixel.hytale.protocol.InteractionState;
/*     */ import com.hypixel.hytale.protocol.InteractionSyncData;
/*     */ import com.hypixel.hytale.protocol.InteractionType;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocksound.config.BlockSoundSet;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.asset.type.gameplay.GameplayConfig;
/*     */ import com.hypixel.hytale.server.core.asset.type.gameplay.WorldConfig;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.BlockGroup;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.Item;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*     */ import com.hypixel.hytale.server.core.universe.world.SoundUtil;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.BlockSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class CycleBlockGroupInteraction
/*     */   extends SimpleBlockInteraction
/*     */ {
/*     */   private static final int SET_SETTINGS = 256;
/*     */   @Nonnull
/*  43 */   public static final BuilderCodec<CycleBlockGroupInteraction> CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(CycleBlockGroupInteraction.class, CycleBlockGroupInteraction::new, SimpleBlockInteraction.CODEC)
/*  44 */     .documentation("Attempts to cycle the target block through its block set."))
/*  45 */     .build();
/*     */ 
/*     */   
/*     */   protected void interactWithBlock(@Nonnull World world, @Nonnull CommandBuffer<EntityStore> commandBuffer, @Nonnull InteractionType type, @Nonnull InteractionContext context, @Nullable ItemStack heldItemStack, @Nonnull Vector3i targetBlock, @Nonnull CooldownHandler cooldownHandler) {
/*  49 */     Ref<EntityStore> ref = context.getEntity();
/*  50 */     Store<EntityStore> store = ref.getStore();
/*  51 */     Player playerComponent = (Player)commandBuffer.getComponent(ref, Player.getComponentType());
/*     */     
/*  53 */     InteractionSyncData state = context.getState();
/*  54 */     state.state = InteractionState.Failed;
/*     */     
/*  56 */     if (playerComponent == null) {
/*  57 */       ((HytaleLogger.Api)HytaleLogger.getLogger().at(Level.INFO)
/*  58 */         .atMostEvery(5, TimeUnit.MINUTES))
/*  59 */         .log("CycleBlockGroupInteraction requires a Player but was used for: %s", ref);
/*     */       
/*     */       return;
/*     */     } 
/*  63 */     ChunkStore chunkStore = world.getChunkStore();
/*  64 */     Store<ChunkStore> chunkStoreStore = chunkStore.getStore();
/*     */     
/*  66 */     long chunkIndex = ChunkUtil.indexChunkFromBlock(targetBlock.x, targetBlock.z);
/*  67 */     Ref<ChunkStore> chunkReference = chunkStore.getChunkReference(chunkIndex);
/*  68 */     if (chunkReference == null || !chunkReference.isValid())
/*     */       return; 
/*  70 */     WorldChunk worldChunkComponent = (WorldChunk)chunkStoreStore.getComponent(chunkReference, WorldChunk.getComponentType());
/*  71 */     assert worldChunkComponent != null;
/*     */     
/*  73 */     BlockChunk blockChunkComponent = (BlockChunk)chunkStoreStore.getComponent(chunkReference, BlockChunk.getComponentType());
/*  74 */     assert blockChunkComponent != null;
/*     */     
/*  76 */     BlockSection blockSection = blockChunkComponent.getSectionAtBlockY(targetBlock.getY());
/*  77 */     GameplayConfig gameplayConfig = world.getGameplayConfig();
/*  78 */     WorldConfig worldConfig = gameplayConfig.getWorldConfig();
/*     */     
/*  80 */     boolean blockBreakingAllowed = worldConfig.isBlockBreakingAllowed();
/*  81 */     if (!blockBreakingAllowed)
/*     */       return; 
/*  83 */     int blockIndex = blockSection.get(targetBlock.x, targetBlock.y, targetBlock.z);
/*  84 */     BlockType targetBlockType = (BlockType)BlockType.getAssetMap().getAsset(blockIndex);
/*     */     
/*  86 */     if (targetBlockType == null)
/*     */       return; 
/*  88 */     Item targetBlockItem = targetBlockType.getItem();
/*  89 */     BlockGroup set = BlockGroup.findItemGroup(targetBlockItem);
/*     */     
/*  91 */     if (set == null)
/*     */       return; 
/*  93 */     int currentIndex = set.getIndex(targetBlockItem);
/*  94 */     if (currentIndex == -1)
/*     */       return; 
/*  96 */     String nextBlockKey = set.get((currentIndex + 1) % set.size());
/*  97 */     BlockType nextBlockType = (BlockType)BlockType.getAssetMap().getAsset(nextBlockKey);
/*  98 */     if (nextBlockType == null) {
/*     */       return;
/*     */     }
/* 101 */     ItemStack heldItem = context.getHeldItem();
/* 102 */     if (heldItem != null && playerComponent.canDecreaseItemStackDurability(ref, (ComponentAccessor)store) && !heldItem.isUnbreakable()) {
/* 103 */       playerComponent.updateItemStackDurability(ref, heldItem, playerComponent.getInventory().getHotbar(), context.getHeldItemSlot(), -heldItem.getItem().getDurabilityLossOnHit(), (ComponentAccessor)commandBuffer);
/*     */     }
/*     */     
/* 106 */     int newBlockId = BlockType.getAssetMap().getIndex(nextBlockType.getId());
/* 107 */     int rotation = worldChunkComponent.getRotationIndex(targetBlock.x, targetBlock.y, targetBlock.z);
/* 108 */     worldChunkComponent.setBlock(targetBlock.getX(), targetBlock.getY(), targetBlock.getZ(), newBlockId, nextBlockType, rotation, 0, 256);
/* 109 */     state.state = InteractionState.NotFinished;
/*     */     
/* 111 */     BlockSoundSet soundSet = (BlockSoundSet)BlockSoundSet.getAssetMap().getAsset(nextBlockType.getBlockSoundSetIndex());
/*     */     
/* 113 */     if (soundSet != null) {
/* 114 */       int soundEventIndex = soundSet.getSoundEventIndices().getOrDefault(BlockSoundEvent.Hit, 0);
/* 115 */       if (soundEventIndex != 0) {
/* 116 */         SoundUtil.playSoundEvent3d(ref, soundEventIndex, targetBlock.x + 0.5D, targetBlock.y + 0.5D, targetBlock.z + 0.5D, (ComponentAccessor)commandBuffer);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void simulateInteractWithBlock(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nullable ItemStack itemInHand, @Nonnull World world, @Nonnull Vector3i targetBlock) {}
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 129 */     return "CycleBlockGroupInteraction{}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\client\CycleBlockGroupInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */