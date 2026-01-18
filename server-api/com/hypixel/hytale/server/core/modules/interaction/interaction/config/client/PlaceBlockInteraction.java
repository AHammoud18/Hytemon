/*     */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config.client;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.validation.LateValidator;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.protocol.BlockPosition;
/*     */ import com.hypixel.hytale.protocol.BlockRotation;
/*     */ import com.hypixel.hytale.protocol.GameMode;
/*     */ import com.hypixel.hytale.protocol.Interaction;
/*     */ import com.hypixel.hytale.protocol.InteractionSyncData;
/*     */ import com.hypixel.hytale.protocol.InteractionType;
/*     */ import com.hypixel.hytale.protocol.Rotation;
/*     */ import com.hypixel.hytale.protocol.WaitForDataFrom;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockFace;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.RotationTuple;
/*     */ import com.hypixel.hytale.server.core.entity.Entity;
/*     */ import com.hypixel.hytale.server.core.entity.EntityUtils;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*     */ import com.hypixel.hytale.server.core.entity.LivingEntity;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.inventory.Inventory;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.BlockPlaceUtils;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInteraction;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.BlockSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PlaceBlockInteraction
/*     */   extends SimpleInteraction
/*     */ {
/*     */   public static final int MAX_ADVENTURE_PLACEMENT_RANGE_SQUARED = 36;
/*     */   @Nonnull
/*     */   public static final BuilderCodec<PlaceBlockInteraction> CODEC;
/*     */   @Nullable
/*     */   protected String blockTypeKey;
/*     */   
/*     */   static {
/*  68 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(PlaceBlockInteraction.class, PlaceBlockInteraction::new, SimpleInteraction.CODEC).documentation("Places the current or given block.")).append(new KeyedCodec("BlockTypeToPlace", (Codec)Codec.STRING), (placeBlockInteraction, blockTypeKey) -> placeBlockInteraction.blockTypeKey = blockTypeKey, placeBlockInteraction -> placeBlockInteraction.blockTypeKey).addValidatorLate(() -> BlockType.VALIDATOR_CACHE.getValidator().late()).documentation("Overrides the placed block type of the held item with the provided block type.").add()).append(new KeyedCodec("RemoveItemInHand", (Codec)Codec.BOOLEAN), (placeBlockInteraction, aBoolean) -> placeBlockInteraction.removeItemInHand = aBoolean.booleanValue(), placeBlockInteraction -> Boolean.valueOf(placeBlockInteraction.removeItemInHand)).documentation("Determines whether to remove the item that is in the instigating entities hand.").add()).appendInherited(new KeyedCodec("AllowDragPlacement", (Codec)Codec.BOOLEAN), (placeBlockInteraction, aBoolean) -> placeBlockInteraction.allowDragPlacement = aBoolean.booleanValue(), placeBlockInteraction -> Boolean.valueOf(placeBlockInteraction.allowDragPlacement), (placeBlockInteraction, parent) -> placeBlockInteraction.allowDragPlacement = parent.allowDragPlacement).documentation("If drag placement should be used when click is held for this interaction.").add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean removeItemInHand = true;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean allowDragPlacement = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public WaitForDataFrom getWaitForDataFrom() {
/*  89 */     return WaitForDataFrom.Client;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void tick0(boolean firstRun, float time, @NonNullDecl InteractionType type, @Nonnull InteractionContext context, @NonNullDecl CooldownHandler cooldownHandler) {
/*  95 */     InteractionSyncData clientState = context.getClientState();
/*  96 */     assert clientState != null;
/*     */     
/*  98 */     if (!firstRun) {
/*  99 */       (context.getState()).state = clientState.state;
/*     */       
/*     */       return;
/*     */     } 
/* 103 */     Ref<EntityStore> ref = context.getEntity();
/* 104 */     CommandBuffer<EntityStore> commandBuffer = context.getCommandBuffer();
/* 105 */     assert commandBuffer != null;
/*     */ 
/*     */ 
/*     */     
/* 109 */     BlockPosition blockPosition = clientState.blockPosition;
/* 110 */     BlockRotation blockRotation = clientState.blockRotation;
/*     */ 
/*     */     
/* 113 */     if (blockPosition != null && blockRotation != null) {
/* 114 */       World world = ((EntityStore)commandBuffer.getExternalData()).getWorld();
/*     */       
/* 116 */       Store<ChunkStore> chunkStore = world.getChunkStore().getStore();
/* 117 */       long chunkIndex = ChunkUtil.indexChunkFromBlock(blockPosition.x, blockPosition.z);
/* 118 */       Ref<ChunkStore> chunkReference = ((ChunkStore)chunkStore.getExternalData()).getChunkReference(chunkIndex);
/*     */ 
/*     */       
/* 121 */       if (chunkReference == null || !chunkReference.isValid()) {
/*     */         return;
/*     */       }
/* 124 */       ItemStack heldItemStack = context.getHeldItem();
/* 125 */       if (heldItemStack == null) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 130 */       ItemContainer heldItemContainer = context.getHeldItemContainer();
/* 131 */       if (heldItemContainer == null) {
/*     */         return;
/*     */       }
/* 134 */       TransformComponent transformComponent = (TransformComponent)commandBuffer.getComponent(ref, TransformComponent.getComponentType());
/* 135 */       Player playerComponent = (Player)commandBuffer.getComponent(ref, Player.getComponentType());
/*     */       
/* 137 */       if (transformComponent != null && playerComponent != null && playerComponent.getGameMode() != GameMode.Creative) {
/* 138 */         Vector3d position = transformComponent.getPosition();
/* 139 */         Vector3d blockCenter = new Vector3d(blockPosition.x + 0.5D, blockPosition.y + 0.5D, blockPosition.z + 0.5D);
/*     */         
/* 141 */         if (position.distanceSquaredTo(blockCenter) > 36.0D) {
/*     */           return;
/*     */         }
/*     */       } 
/*     */       
/* 146 */       Inventory inventory = null;
/* 147 */       Entity entity = EntityUtils.getEntity(ref, (ComponentAccessor)commandBuffer); if (entity instanceof LivingEntity) { LivingEntity livingEntity = (LivingEntity)entity;
/* 148 */         inventory = livingEntity.getInventory(); }
/*     */ 
/*     */ 
/*     */       
/* 152 */       Vector3i targetBlockPosition = new Vector3i(blockPosition.x, blockPosition.y, blockPosition.z);
/*     */ 
/*     */       
/* 155 */       String interactionBlockTypeKey = (this.blockTypeKey != null) ? this.blockTypeKey : heldItemStack.getBlockKey();
/* 156 */       if (interactionBlockTypeKey == null)
/* 157 */         return;  BlockType interactionBlockType = (BlockType)BlockType.getAssetMap().getAsset(interactionBlockTypeKey);
/*     */       
/* 159 */       int clientPlacedBlockId = clientState.placedBlockId;
/* 160 */       String clientPlacedBlockTypeKey = (clientPlacedBlockId == -1) ? null : ((BlockType)BlockType.getAssetMap().getAsset(clientPlacedBlockId)).getId();
/*     */ 
/*     */       
/* 163 */       if (clientPlacedBlockTypeKey != null && !clientPlacedBlockTypeKey.equals(this.blockTypeKey) && (interactionBlockType == null || !BlockPlaceUtils.canPlaceBlock(interactionBlockType, clientPlacedBlockTypeKey))) {
/* 164 */         clientPlacedBlockTypeKey = null;
/*     */       }
/*     */ 
/*     */       
/* 168 */       if (blockPosition.y < 0 || blockPosition.y >= 320) {
/*     */         return;
/*     */       }
/*     */       
/* 172 */       BlockPlaceUtils.placeBlock(ref, heldItemStack, 
/*     */ 
/*     */           
/* 175 */           (clientPlacedBlockTypeKey != null) ? clientPlacedBlockTypeKey : this.blockTypeKey, heldItemContainer, 
/*     */           
/* 177 */           BlockFace.fromProtocolFace((context.getClientState()).blockFace).getDirection(), targetBlockPosition, blockRotation, inventory, context
/*     */ 
/*     */ 
/*     */           
/* 181 */           .getHeldItemSlot(), this.removeItemInHand, chunkReference, (ComponentAccessor)chunkStore, (ComponentAccessor)commandBuffer);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 188 */       boolean isAdventure = (playerComponent == null || playerComponent.getGameMode() == GameMode.Adventure);
/* 189 */       if (isAdventure && heldItemStack.getQuantity() == 1 && this.removeItemInHand) {
/* 190 */         context.setHeldItem(null);
/*     */       }
/*     */       
/* 193 */       BlockChunk blockChunk = (BlockChunk)chunkStore.getComponent(chunkReference, BlockChunk.getComponentType());
/* 194 */       BlockSection section = blockChunk.getSectionAtBlockY(blockPosition.y);
/* 195 */       (context.getState()).blockPosition = blockPosition;
/* 196 */       (context.getState()).placedBlockId = section.get(blockPosition.x, blockPosition.y, blockPosition.z);
/* 197 */       RotationTuple resultRotation = section.getRotation(blockPosition.x, blockPosition.y, blockPosition.z);
/* 198 */       (context.getState()).blockRotation = new BlockRotation(resultRotation.yaw().toPacket(), resultRotation.pitch().toPacket(), resultRotation.roll().toPacket());
/*     */     } 
/*     */     
/* 201 */     super.tick0(firstRun, time, type, context, cooldownHandler);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void simulateTick0(boolean firstRun, float time, @NonNullDecl InteractionType type, @Nonnull InteractionContext context, @NonNullDecl CooldownHandler cooldownHandler) {
/* 207 */     super.simulateTick0(firstRun, time, type, context, cooldownHandler);
/* 208 */     if (Interaction.failed((context.getState()).state))
/*     */       return; 
/* 210 */     InteractionSyncData clientState = context.getClientState();
/* 211 */     assert clientState != null;
/*     */     
/* 213 */     if (!firstRun) {
/* 214 */       (context.getState()).state = (context.getClientState()).state;
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 219 */     clientState.blockRotation = new BlockRotation(Rotation.None, Rotation.None, Rotation.None);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected Interaction generatePacket() {
/* 225 */     return (Interaction)new com.hypixel.hytale.protocol.PlaceBlockInteraction();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void configurePacket(Interaction packet) {
/* 230 */     super.configurePacket(packet);
/* 231 */     com.hypixel.hytale.protocol.PlaceBlockInteraction p = (com.hypixel.hytale.protocol.PlaceBlockInteraction)packet;
/* 232 */     p.blockId = (this.blockTypeKey == null) ? -1 : BlockType.getAssetMap().getIndex(this.blockTypeKey);
/* 233 */     p.removeItemInHand = this.removeItemInHand;
/* 234 */     p.allowDragPlacement = this.allowDragPlacement;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean needsRemoteSync() {
/* 239 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 244 */     return "PlaceBlockInteraction{blockTypeKey='" + this.blockTypeKey + "', removeItemInHand=" + this.removeItemInHand + ", allowDragPlacement=" + this.allowDragPlacement + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\client\PlaceBlockInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */