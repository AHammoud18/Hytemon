/*     */ package com.hypixel.hytale.builtin.instances.interactions;
/*     */ import com.hypixel.hytale.assetstore.map.IndexedLookupTableAssetMap;
/*     */ import com.hypixel.hytale.builtin.instances.InstancesPlugin;
/*     */ import com.hypixel.hytale.builtin.instances.blocks.ConfigurableInstanceBlock;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.component.Archetype;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.Axis;
/*     */ import com.hypixel.hytale.math.shape.Box;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.vector.Transform;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.protocol.BlockPosition;
/*     */ import com.hypixel.hytale.protocol.InteractionType;
/*     */ import com.hypixel.hytale.protocol.WaitForDataFrom;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.asset.type.blockhitbox.BlockBoundingBoxes;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.RotationTuple;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.modules.entity.teleport.PendingTeleport;
/*     */ import com.hypixel.hytale.server.core.modules.entity.teleport.Teleport;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.client.SimpleBlockInteraction;
/*     */ import com.hypixel.hytale.server.core.universe.Universe;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockComponentChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.BlockSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class TeleportConfigInstanceInteraction extends SimpleBlockInteraction {
/*     */   @Nonnull
/*  47 */   private static final Message MESSAGE_GENERAL_INTERACTION_CONFIGURE_INSTANCE_NO_INSTANCE_NAME = Message.translation("server.general.interaction.configureInstance.noInstanceName");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  53 */   public static final BuilderCodec<TeleportConfigInstanceInteraction> CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(TeleportConfigInstanceInteraction.class, TeleportConfigInstanceInteraction::new, SimpleBlockInteraction.CODEC)
/*  54 */     .documentation("Teleports the **Player** to the named instance, creating it if required.\n\nThis is configured via a UI instead of inside the interaction. This interaction just executes that set configuration."))
/*     */ 
/*     */     
/*  57 */     .build();
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int SET_BLOCK_SETTINGS = 256;
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public WaitForDataFrom getWaitForDataFrom() {
/*  67 */     return WaitForDataFrom.Server;
/*     */   }
/*     */   
/*     */   protected void interactWithBlock(@Nonnull World world, @Nonnull CommandBuffer<EntityStore> commandBuffer, @Nonnull InteractionType type, @Nonnull InteractionContext context, @Nullable ItemStack itemInHand, @Nonnull Vector3i targetBlock, @Nonnull CooldownHandler cooldownHandler) {
/*     */     World targetWorld;
/*  72 */     Ref<EntityStore> ref = context.getEntity();
/*     */ 
/*     */     
/*  75 */     Player playerComponent = (Player)commandBuffer.getComponent(ref, Player.getComponentType());
/*  76 */     if (playerComponent == null || playerComponent.isWaitingForClientReady()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  82 */     Archetype<EntityStore> archetype = commandBuffer.getArchetype(ref);
/*  83 */     if (archetype.contains(Teleport.getComponentType()) || archetype.contains(PendingTeleport.getComponentType()))
/*     */       return; 
/*  85 */     InstancesPlugin module = InstancesPlugin.get();
/*  86 */     Universe universe = Universe.get();
/*     */     
/*  88 */     ChunkStore chunkStore = world.getChunkStore();
/*  89 */     Ref<ChunkStore> chunkRef = chunkStore.getChunkReference(ChunkUtil.indexChunkFromBlock(targetBlock.x, targetBlock.z));
/*  90 */     if (chunkRef == null || !chunkRef.isValid())
/*     */       return; 
/*  92 */     BlockComponentChunk blockComponentChunk = (BlockComponentChunk)chunkStore.getStore().getComponent(chunkRef, BlockComponentChunk.getComponentType());
/*  93 */     assert blockComponentChunk != null;
/*     */     
/*  95 */     Ref<ChunkStore> blockRef = blockComponentChunk.getEntityReference(ChunkUtil.indexBlockInColumn(targetBlock.x, targetBlock.y, targetBlock.z));
/*  96 */     if (blockRef == null || !blockRef.isValid())
/*     */       return; 
/*  98 */     ConfigurableInstanceBlock configurableInstanceBlock = (ConfigurableInstanceBlock)chunkStore.getStore().getComponent(blockRef, ConfigurableInstanceBlock.getComponentType());
/*  99 */     if (configurableInstanceBlock == null)
/*     */       return; 
/* 101 */     if (configurableInstanceBlock.getInstanceName() == null) {
/* 102 */       playerComponent.sendMessage(MESSAGE_GENERAL_INTERACTION_CONFIGURE_INSTANCE_NO_INSTANCE_NAME);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 107 */     CompletableFuture<World> targetWorldFuture = null;
/* 108 */     Transform returnPoint = null;
/*     */     
/* 110 */     if (configurableInstanceBlock.getInstanceKey() != null) {
/*     */       
/* 112 */       targetWorld = universe.getWorld(configurableInstanceBlock.getInstanceKey());
/* 113 */       if (targetWorld == null) {
/* 114 */         returnPoint = makeReturnPoint(configurableInstanceBlock, context, (ComponentAccessor<EntityStore>)commandBuffer);
/* 115 */         targetWorldFuture = module.spawnInstance(configurableInstanceBlock.getInstanceName(), configurableInstanceBlock.getInstanceKey(), world, returnPoint);
/*     */       } 
/*     */     } else {
/* 118 */       UUID worldUuid = configurableInstanceBlock.getWorldUUID();
/* 119 */       targetWorldFuture = configurableInstanceBlock.getWorldFuture();
/* 120 */       targetWorld = (worldUuid != null) ? universe.getWorld(worldUuid) : null;
/* 121 */       if (targetWorld == null && targetWorldFuture == null) {
/*     */ 
/*     */         
/* 124 */         returnPoint = makeReturnPoint(configurableInstanceBlock, context, (ComponentAccessor<EntityStore>)commandBuffer);
/* 125 */         targetWorldFuture = module.spawnInstance(configurableInstanceBlock.getInstanceName(), world, returnPoint);
/* 126 */         configurableInstanceBlock.setWorldFuture(targetWorldFuture);
/* 127 */         targetWorldFuture.thenAccept(instanceWorld -> {
/*     */               if (!blockRef.isValid())
/*     */                 return; 
/*     */               configurableInstanceBlock.setWorldFuture(null);
/*     */               configurableInstanceBlock.setWorldUUID(instanceWorld.getWorldConfig().getUuid());
/*     */               blockComponentChunk.markNeedsSaving();
/*     */             });
/*     */       } 
/*     */     } 
/* 136 */     if (targetWorldFuture != null) {
/* 137 */       Transform personalReturnPoint = getPersonalReturnPoint(configurableInstanceBlock, context, returnPoint, (ComponentAccessor<EntityStore>)commandBuffer);
/* 138 */       InstancesPlugin.teleportPlayerToLoadingInstance(ref, (ComponentAccessor)commandBuffer, targetWorldFuture, personalReturnPoint);
/* 139 */     } else if (targetWorld != null) {
/* 140 */       Transform personalReturnPoint = getPersonalReturnPoint(configurableInstanceBlock, context, returnPoint, (ComponentAccessor<EntityStore>)commandBuffer);
/* 141 */       InstancesPlugin.teleportPlayerToInstance(ref, (ComponentAccessor)commandBuffer, targetWorld, personalReturnPoint);
/*     */     } 
/*     */     
/* 144 */     double removeBlockAfter = configurableInstanceBlock.getRemoveBlockAfter();
/* 145 */     if (removeBlockAfter >= 0.0D)
/*     */     {
/*     */       
/* 148 */       if (removeBlockAfter == 0.0D) {
/*     */         
/* 150 */         long chunkIndex = ChunkUtil.indexChunkFromBlock(targetBlock.x, targetBlock.z);
/* 151 */         WorldChunk worldChunk = world.getChunk(chunkIndex);
/* 152 */         worldChunk.setBlock(targetBlock.x, targetBlock.y, targetBlock.z, 0, 256);
/*     */       } else {
/* 154 */         int block = world.getBlock(targetBlock);
/*     */         
/* 156 */         (new CompletableFuture())
/* 157 */           .completeOnTimeout(null, (long)(removeBlockAfter * 1.0E9D), TimeUnit.NANOSECONDS)
/* 158 */           .thenRunAsync(() -> {
/*     */               if (world.getBlock(targetBlock) == block) {
/*     */                 long chunkIndex = ChunkUtil.indexChunkFromBlock(targetBlock.x, targetBlock.z);
/*     */                 WorldChunk worldChunk = world.getChunk(chunkIndex);
/*     */                 worldChunk.setBlock(targetBlock.x, targetBlock.y, targetBlock.z, 0, 256);
/*     */               } 
/*     */             }(Executor)world);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void simulateInteractWithBlock(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nullable ItemStack itemInHand, @Nonnull World world, @Nonnull Vector3i targetBlock) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private static Transform getPersonalReturnPoint(@Nonnull ConfigurableInstanceBlock state, @Nonnull InteractionContext context, @Nullable Transform returnPoint, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 187 */     if (!state.isPersonalReturnPoint()) return null; 
/* 188 */     if (returnPoint == null) return makeReturnPoint(state, context, componentAccessor); 
/* 189 */     return returnPoint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private static Transform makeReturnPoint(@Nonnull ConfigurableInstanceBlock state, @Nonnull InteractionContext context, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 202 */     BlockPosition targetBlock = context.getTargetBlock();
/* 203 */     if (targetBlock == null) throw new IllegalArgumentException("Can't use OriginSource.BLOCK without a target block");
/*     */     
/* 205 */     World world = ((EntityStore)componentAccessor.getExternalData()).getWorld();
/* 206 */     ChunkStore chunkStore = world.getChunkStore();
/* 207 */     Store<ChunkStore> chunkComponentStore = chunkStore.getStore();
/*     */     
/* 209 */     long chunkIndex = ChunkUtil.indexChunkFromBlock(targetBlock.x, targetBlock.z);
/* 210 */     Ref<ChunkStore> chunkRef = chunkStore.getChunkReference(chunkIndex);
/* 211 */     if (chunkRef == null || !chunkRef.isValid()) {
/* 212 */       throw new IllegalArgumentException("Chunk not loaded");
/*     */     }
/*     */     
/* 215 */     BlockChunk blockChunkComponent = (BlockChunk)chunkComponentStore.getComponent(chunkRef, BlockChunk.getComponentType());
/* 216 */     assert blockChunkComponent != null;
/*     */     
/* 218 */     WorldChunk worldChunkComponent = (WorldChunk)chunkComponentStore.getComponent(chunkRef, WorldChunk.getComponentType());
/* 219 */     assert worldChunkComponent != null;
/*     */     
/* 221 */     BlockType blockType = worldChunkComponent.getBlockType(targetBlock.x, targetBlock.y, targetBlock.z);
/* 222 */     if (blockType == null) {
/* 223 */       throw new IllegalArgumentException("Block type not found");
/*     */     }
/*     */     
/* 226 */     IndexedLookupTableAssetMap<String, BlockBoundingBoxes> hitboxAssetMap = BlockBoundingBoxes.getAssetMap();
/*     */     
/* 228 */     BlockSection section = blockChunkComponent.getSectionAtBlockY(targetBlock.y);
/* 229 */     int rotationIndex = section.getRotationIndex(targetBlock.x, targetBlock.y, targetBlock.z);
/*     */     
/* 231 */     RotationTuple rotation = RotationTuple.get(rotationIndex);
/* 232 */     Box hitbox = ((BlockBoundingBoxes)hitboxAssetMap.getAsset(blockType.getHitboxTypeIndex())).get(rotationIndex).getBoundingBox();
/*     */     
/* 234 */     Vector3d position = (state.getPositionOffset() != null) ? rotation.rotate(state.getPositionOffset()) : new Vector3d();
/*     */     
/* 236 */     position.x += hitbox.middleX() + targetBlock.x;
/* 237 */     position.y += hitbox.middleY() + targetBlock.y;
/* 238 */     position.z += hitbox.middleZ() + targetBlock.z;
/*     */     
/* 240 */     Vector3f rotationOutput = Vector3f.NaN;
/* 241 */     if (state.getRotation() != null) {
/* 242 */       rotationOutput = state.getRotation().clone();
/* 243 */       rotationOutput.addRotationOnAxis(Axis.Y, rotation.yaw().getDegrees());
/* 244 */       rotationOutput.addRotationOnAxis(Axis.X, rotation.pitch().getDegrees());
/*     */     } 
/*     */     
/* 247 */     return new Transform(position, rotationOutput);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\instances\interactions\TeleportConfigInstanceInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */