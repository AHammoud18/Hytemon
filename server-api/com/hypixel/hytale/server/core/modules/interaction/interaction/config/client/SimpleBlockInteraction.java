/*     */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config.client;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.protocol.BlockFace;
/*     */ import com.hypixel.hytale.protocol.BlockPosition;
/*     */ import com.hypixel.hytale.protocol.BlockRotation;
/*     */ import com.hypixel.hytale.protocol.Interaction;
/*     */ import com.hypixel.hytale.protocol.InteractionState;
/*     */ import com.hypixel.hytale.protocol.InteractionSyncData;
/*     */ import com.hypixel.hytale.protocol.InteractionType;
/*     */ import com.hypixel.hytale.protocol.WaitForDataFrom;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.RotationTuple;
/*     */ import com.hypixel.hytale.server.core.entity.Entity;
/*     */ import com.hypixel.hytale.server.core.entity.EntityUtils;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*     */ import com.hypixel.hytale.server.core.entity.LivingEntity;
/*     */ import com.hypixel.hytale.server.core.inventory.Inventory;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInteraction;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.BlockSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.core.util.TargetUtil;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
/*     */ 
/*     */ public abstract class SimpleBlockInteraction
/*     */   extends SimpleInteraction {
/*     */   @Nonnull
/*     */   public static final BuilderCodec<SimpleBlockInteraction> CODEC;
/*     */   private boolean useLatestTarget;
/*     */   
/*     */   static {
/*  48 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.abstractBuilder(SimpleBlockInteraction.class, SimpleInteraction.CODEC).appendInherited(new KeyedCodec("UseLatestTarget", (Codec)Codec.BOOLEAN), (interaction, s) -> interaction.useLatestTarget = s.booleanValue(), interaction -> Boolean.valueOf(interaction.useLatestTarget), (interaction, parent) -> interaction.useLatestTarget = parent.useLatestTarget).documentation("Determines whether to use the clients latest target block position for this interaction.").add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SimpleBlockInteraction(@Nonnull String id) {
/*  56 */     super(id);
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
/*     */ 
/*     */ 
/*     */     
/*  71 */     this.useLatestTarget = false; } protected SimpleBlockInteraction() { this.useLatestTarget = false; }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public WaitForDataFrom getWaitForDataFrom() {
/*  76 */     return WaitForDataFrom.Client;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void tick0(boolean firstRun, float time, @Nonnull InteractionType type, @Nonnull InteractionContext context, @Nonnull CooldownHandler cooldownHandler) {
/*     */     LivingEntity livingEntity;
/*  82 */     if (!firstRun)
/*     */       return; 
/*  84 */     Ref<EntityStore> ref = context.getEntity();
/*  85 */     CommandBuffer<EntityStore> commandBuffer = context.getCommandBuffer();
/*  86 */     assert commandBuffer != null;
/*     */     
/*  88 */     World world = ((EntityStore)commandBuffer.getExternalData()).getWorld();
/*     */ 
/*     */     
/*  91 */     if (this.useLatestTarget) {
/*  92 */       InteractionSyncData clientState = context.getClientState();
/*  93 */       if (clientState != null && clientState.blockPosition != null) {
/*  94 */         BlockPosition latestBlockPos = clientState.blockPosition;
/*     */         
/*  96 */         TransformComponent transformComponent = (TransformComponent)commandBuffer.getComponent(ref, TransformComponent.getComponentType());
/*  97 */         assert transformComponent != null;
/*     */ 
/*     */ 
/*     */         
/* 101 */         double distanceSquared = transformComponent.getPosition().distanceSquaredTo(latestBlockPos.x + 0.5D, latestBlockPos.y + 0.5D, latestBlockPos.z + 0.5D);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 109 */         BlockPosition baseBlock = world.getBaseBlock(latestBlockPos);
/* 110 */         context.getMetaStore().putMetaObject(Interaction.TARGET_BLOCK, baseBlock);
/* 111 */         context.getMetaStore().putMetaObject(Interaction.TARGET_BLOCK_RAW, latestBlockPos);
/*     */       } else {
/* 113 */         (context.getState()).state = InteractionState.Failed;
/* 114 */         super.tick0(firstRun, time, type, context, cooldownHandler);
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/*     */     
/* 120 */     BlockPosition targetBlockPos = context.getTargetBlock();
/* 121 */     if (targetBlockPos == null) {
/* 122 */       (context.getState()).state = InteractionState.Failed;
/* 123 */       super.tick0(firstRun, time, type, context, cooldownHandler);
/*     */       
/*     */       return;
/*     */     } 
/* 127 */     Entity entity = EntityUtils.getEntity(ref, (ComponentAccessor)commandBuffer);
/* 128 */     if (entity instanceof LivingEntity) { livingEntity = (LivingEntity)entity; }
/*     */     else { return; }
/* 130 */      Inventory inventory = livingEntity.getInventory();
/* 131 */     ItemStack itemInHand = inventory.getItemInHand();
/* 132 */     Vector3i targetBlock = new Vector3i(targetBlockPos.x, targetBlockPos.y, targetBlockPos.z);
/*     */     
/* 134 */     WorldChunk chunk = world.getChunkIfInMemory(ChunkUtil.indexChunkFromBlock(targetBlock.x, targetBlock.z));
/* 135 */     if (chunk == null) {
/* 136 */       (context.getState()).state = InteractionState.Failed;
/* 137 */       super.tick0(firstRun, time, type, context, cooldownHandler);
/*     */       
/*     */       return;
/*     */     } 
/* 141 */     int blockId = chunk.getBlock(targetBlock);
/* 142 */     if (blockId == 1 || blockId == 0) {
/* 143 */       (context.getState()).state = InteractionState.Failed;
/* 144 */       super.tick0(firstRun, time, type, context, cooldownHandler);
/*     */       
/*     */       return;
/*     */     } 
/* 148 */     interactWithBlock(world, commandBuffer, type, context, itemInHand, targetBlock, cooldownHandler);
/*     */     
/* 150 */     super.tick0(firstRun, time, type, context, cooldownHandler);
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
/*     */   
/*     */   protected void simulateTick0(boolean firstRun, float time, @Nonnull InteractionType type, @Nonnull InteractionContext context, @NonNullDecl CooldownHandler cooldownHandler) {
/*     */     LivingEntity livingEntity;
/*     */     Vector3i targetBlock;
/* 176 */     if (!firstRun)
/*     */       return; 
/* 178 */     Ref<EntityStore> ref = context.getEntity();
/* 179 */     CommandBuffer<EntityStore> commandBuffer = context.getCommandBuffer();
/* 180 */     assert commandBuffer != null;
/*     */     
/* 182 */     World world = ((EntityStore)commandBuffer.getExternalData()).getWorld();
/* 183 */     Entity entity = EntityUtils.getEntity(ref, (ComponentAccessor)commandBuffer);
/* 184 */     if (entity instanceof LivingEntity) { livingEntity = (LivingEntity)entity; }
/*     */     else { return; }
/* 186 */      Inventory inventory = livingEntity.getInventory();
/* 187 */     ItemStack itemInHand = inventory.getItemInHand();
/*     */     
/* 189 */     (context.getState()).blockFace = BlockFace.Up;
/*     */ 
/*     */ 
/*     */     
/* 193 */     BlockPosition contextTargetBlock = context.getTargetBlock();
/* 194 */     if (contextTargetBlock == null) {
/* 195 */       targetBlock = TargetUtil.getTargetBlock(ref, 8.0D, (ComponentAccessor)commandBuffer);
/* 196 */       if (targetBlock == null) {
/* 197 */         (context.getState()).state = InteractionState.Failed;
/* 198 */         super.tick0(firstRun, time, type, context, cooldownHandler);
/*     */         return;
/*     */       } 
/* 201 */       (context.getState()).blockPosition = new BlockPosition(targetBlock.x, targetBlock.y, targetBlock.z);
/*     */     } else {
/* 203 */       (context.getState()).blockPosition = contextTargetBlock;
/* 204 */       targetBlock = new Vector3i(contextTargetBlock.x, contextTargetBlock.y, contextTargetBlock.z);
/*     */     } 
/*     */     
/* 207 */     WorldChunk chunk = world.getChunkIfInMemory(ChunkUtil.indexChunkFromBlock(targetBlock.x, targetBlock.z));
/* 208 */     if (chunk == null) {
/* 209 */       (context.getState()).state = InteractionState.Failed;
/* 210 */       super.tick0(firstRun, time, type, context, cooldownHandler);
/*     */       
/*     */       return;
/*     */     } 
/* 214 */     int blockId = chunk.getBlock(targetBlock);
/* 215 */     if (blockId == 1 || blockId == 0) {
/* 216 */       (context.getState()).state = InteractionState.Failed;
/* 217 */       super.tick0(firstRun, time, type, context, cooldownHandler);
/*     */       
/*     */       return;
/*     */     } 
/* 221 */     simulateInteractWithBlock(type, context, itemInHand, world, targetBlock);
/* 222 */     super.tick0(firstRun, time, type, context, cooldownHandler);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void computeCurrentBlockSyncData(@Nonnull InteractionContext context) {
/* 243 */     BlockPosition targetBlockPos = context.getTargetBlock();
/* 244 */     if (targetBlockPos == null)
/*     */       return; 
/* 246 */     World world = ((EntityStore)context.getCommandBuffer().getStore().getExternalData()).getWorld();
/* 247 */     ChunkStore chunkStore = world.getChunkStore();
/*     */     
/* 249 */     long chunkIndex = ChunkUtil.indexChunkFromBlock(targetBlockPos.x, targetBlockPos.z);
/* 250 */     Ref<ChunkStore> chunkReference = chunkStore.getChunkReference(chunkIndex);
/* 251 */     if (chunkReference == null || !chunkReference.isValid())
/*     */       return; 
/* 253 */     BlockChunk blockChunk = (BlockChunk)chunkStore.getStore().getComponent(chunkReference, BlockChunk.getComponentType());
/* 254 */     if (targetBlockPos.y < 0 || targetBlockPos.y >= 320)
/* 255 */       return;  BlockSection section = blockChunk.getSectionAtBlockY(targetBlockPos.y);
/*     */     
/* 257 */     (context.getState()).blockPosition = new BlockPosition(targetBlockPos.x, targetBlockPos.y, targetBlockPos.z);
/* 258 */     (context.getState()).placedBlockId = section.get(targetBlockPos.x, targetBlockPos.y, targetBlockPos.z);
/* 259 */     RotationTuple resultRotation = section.getRotation(targetBlockPos.x, targetBlockPos.y, targetBlockPos.z);
/* 260 */     (context.getState()).blockRotation = new BlockRotation(resultRotation.yaw().toPacket(), resultRotation.pitch().toPacket(), resultRotation.roll().toPacket());
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected Interaction generatePacket() {
/* 266 */     return (Interaction)new com.hypixel.hytale.protocol.SimpleBlockInteraction();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void configurePacket(Interaction packet) {
/* 271 */     super.configurePacket(packet);
/* 272 */     com.hypixel.hytale.protocol.SimpleBlockInteraction p = (com.hypixel.hytale.protocol.SimpleBlockInteraction)packet;
/* 273 */     p.useLatestTarget = this.useLatestTarget;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean needsRemoteSync() {
/* 278 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 284 */     return "SimpleBlockInteraction{} " + super.toString();
/*     */   }
/*     */   
/*     */   protected abstract void interactWithBlock(@Nonnull World paramWorld, @Nonnull CommandBuffer<EntityStore> paramCommandBuffer, @Nonnull InteractionType paramInteractionType, @Nonnull InteractionContext paramInteractionContext, @Nullable ItemStack paramItemStack, @Nonnull Vector3i paramVector3i, @Nonnull CooldownHandler paramCooldownHandler);
/*     */   
/*     */   protected abstract void simulateInteractWithBlock(@Nonnull InteractionType paramInteractionType, @Nonnull InteractionContext paramInteractionContext, @Nullable ItemStack paramItemStack, @Nonnull World paramWorld, @Nonnull Vector3i paramVector3i);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\client\SimpleBlockInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */