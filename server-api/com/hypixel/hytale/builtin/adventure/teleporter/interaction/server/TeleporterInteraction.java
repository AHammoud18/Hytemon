/*     */ package com.hypixel.hytale.builtin.adventure.teleporter.interaction.server;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.adventure.teleporter.component.Teleporter;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.component.Archetype;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.spatial.SpatialResource;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.protocol.InteractionType;
/*     */ import com.hypixel.hytale.protocol.WaitForDataFrom;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.modules.block.BlockModule;
/*     */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.teleport.PendingTeleport;
/*     */ import com.hypixel.hytale.server.core.modules.entity.teleport.Teleport;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.client.SimpleBlockInteraction;
/*     */ import com.hypixel.hytale.server.core.universe.world.ParticleUtil;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockComponentChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectList;
/*     */ import java.util.List;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TeleporterInteraction
/*     */   extends SimpleBlockInteraction
/*     */ {
/*     */   @Nonnull
/*     */   public static final BuilderCodec<TeleporterInteraction> CODEC;
/*     */   @Nullable
/*     */   private String particle;
/*     */   
/*     */   static {
/*  52 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(TeleporterInteraction.class, TeleporterInteraction::new, SimpleBlockInteraction.CODEC).appendInherited(new KeyedCodec("Particle", (Codec)Codec.STRING), (interaction, s) -> interaction.particle = s, interaction -> interaction.particle, (interaction, parent) -> interaction.particle = parent.particle).documentation("The particle to play on the entity when teleporting.").add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public WaitForDataFrom getWaitForDataFrom() {
/*  63 */     return WaitForDataFrom.Server;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void interactWithBlock(@Nonnull World world, @Nonnull CommandBuffer<EntityStore> commandBuffer, @Nonnull InteractionType type, @Nonnull InteractionContext context, @Nullable ItemStack itemInHand, @Nonnull Vector3i targetBlock, @Nonnull CooldownHandler cooldownHandler) {
/*  68 */     ChunkStore chunkStore = world.getChunkStore();
/*     */     
/*  70 */     long chunkIndex = ChunkUtil.indexChunkFromBlock(targetBlock.getX(), targetBlock.getZ());
/*  71 */     BlockComponentChunk blockComponentChunk = (BlockComponentChunk)chunkStore.getChunkComponent(chunkIndex, BlockComponentChunk.getComponentType());
/*  72 */     if (blockComponentChunk == null)
/*     */       return; 
/*  74 */     int blockIndex = ChunkUtil.indexBlockInColumn(targetBlock.x, targetBlock.y, targetBlock.z);
/*  75 */     Ref<ChunkStore> blockRef = blockComponentChunk.getEntityReference(blockIndex);
/*  76 */     if (blockRef == null || !blockRef.isValid())
/*     */       return; 
/*  78 */     BlockModule.BlockStateInfo blockStateInfoComponent = (BlockModule.BlockStateInfo)blockRef.getStore().getComponent(blockRef, BlockModule.BlockStateInfo.getComponentType());
/*  79 */     if (blockStateInfoComponent == null)
/*     */       return; 
/*  81 */     Ref<ChunkStore> chunkRef = blockStateInfoComponent.getChunkRef();
/*  82 */     if (chunkRef == null && !chunkRef.isValid())
/*     */       return; 
/*  84 */     Teleporter teleporter = (Teleporter)chunkStore.getStore().getComponent(blockRef, Teleporter.getComponentType());
/*  85 */     if (teleporter == null)
/*     */       return; 
/*  87 */     Ref<EntityStore> ref = context.getEntity();
/*     */     
/*  89 */     Player playerComponent = (Player)commandBuffer.getComponent(ref, Player.getComponentType());
/*  90 */     if (playerComponent != null && playerComponent.isWaitingForClientReady()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  96 */     Archetype<EntityStore> archetype = commandBuffer.getArchetype(ref);
/*  97 */     if (archetype.contains(Teleport.getComponentType()) || archetype.contains(PendingTeleport.getComponentType())) {
/*     */       return;
/*     */     }
/* 100 */     if (!teleporter.isValid()) {
/* 101 */       WorldChunk worldChunkComponent = (WorldChunk)chunkRef.getStore().getComponent(chunkRef, WorldChunk.getComponentType());
/* 102 */       assert worldChunkComponent != null;
/*     */       
/* 104 */       BlockType blockType = worldChunkComponent.getBlockType(targetBlock.x, targetBlock.y, targetBlock.z);
/* 105 */       String currentState = blockType.getStateForBlock(blockType);
/* 106 */       if (!"default".equals(currentState)) {
/* 107 */         BlockType variantBlockType = blockType.getBlockForState("default");
/* 108 */         if (variantBlockType != null) {
/* 109 */           worldChunkComponent.setBlockInteractionState(targetBlock.x, targetBlock.y, targetBlock.z, variantBlockType, "default", true);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 114 */     TransformComponent transformComponent = (TransformComponent)commandBuffer.getComponent(ref, TransformComponent.getComponentType());
/* 115 */     assert transformComponent != null;
/*     */     
/* 117 */     Teleport teleportComponent = teleporter.toTeleport(transformComponent.getPosition(), transformComponent.getRotation(), targetBlock);
/* 118 */     if (teleportComponent == null)
/*     */       return; 
/* 120 */     commandBuffer.addComponent(ref, Teleport.getComponentType(), (Component)teleportComponent);
/*     */     
/* 122 */     if (this.particle != null) {
/* 123 */       Vector3d particlePosition = transformComponent.getPosition();
/* 124 */       SpatialResource<Ref<EntityStore>, EntityStore> playerSpatialResource = (SpatialResource<Ref<EntityStore>, EntityStore>)commandBuffer.getResource(EntityModule.get().getPlayerSpatialResourceType());
/* 125 */       ObjectList<Ref<EntityStore>> results = SpatialResource.getThreadLocalReferenceList();
/* 126 */       playerSpatialResource.getSpatialStructure().collect(particlePosition, 75.0D, (List)results);
/*     */       
/* 128 */       ParticleUtil.spawnParticleEffect(this.particle, particlePosition, (List)results, (ComponentAccessor)commandBuffer);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void simulateInteractWithBlock(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nullable ItemStack itemInHand, @Nonnull World world, @Nonnull Vector3i targetBlock) {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\teleporter\interaction\server\TeleporterInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */