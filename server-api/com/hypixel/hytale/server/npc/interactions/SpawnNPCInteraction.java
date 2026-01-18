/*     */ package com.hypixel.hytale.server.npc.interactions;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.protocol.InteractionType;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.RotationTuple;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.client.SimpleBlockInteraction;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.BlockSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*     */ import com.hypixel.hytale.server.npc.validators.NPCRoleValidator;
/*     */ import java.util.concurrent.ThreadLocalRandom;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
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
/*     */ public class SpawnNPCInteraction
/*     */   extends SimpleBlockInteraction
/*     */ {
/*     */   @Nonnull
/*     */   public static final BuilderCodec<SpawnNPCInteraction> CODEC;
/*     */   protected String entityId;
/*     */   
/*     */   static {
/*  73 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(SpawnNPCInteraction.class, SpawnNPCInteraction::new, SimpleBlockInteraction.CODEC).documentation("Spawns an NPC on the block that is being interacted with.")).append(new KeyedCodec("EntityId", (Codec)Codec.STRING), (spawnNPCInteraction, s) -> spawnNPCInteraction.entityId = s, spawnNPCInteraction -> spawnNPCInteraction.entityId).documentation("The ID of the entity asset to spawn.").addValidator((Validator)NPCRoleValidator.INSTANCE).add()).append(new KeyedCodec("SpawnOffset", (Codec)Vector3d.CODEC), (spawnNPCInteraction, s) -> spawnNPCInteraction.spawnOffset.assign(s), spawnNPCInteraction -> spawnNPCInteraction.spawnOffset).documentation("The offset to apply to the spawn position of the NPC, relative to the block's rotation and center.").add()).append(new KeyedCodec("SpawnYawOffset", (Codec)Codec.FLOAT), (spawnNPCInteraction, f) -> spawnNPCInteraction.spawnYawOffset = f.floatValue(), spawnNPCInteraction -> Float.valueOf(spawnNPCInteraction.spawnYawOffset)).documentation("The yaw rotation offset in radians to apply to the NPC rotation, relative to the block's yaw.").add()).append(new KeyedCodec("SpawnChance", (Codec)Codec.FLOAT), (spawnNPCInteraction, f) -> spawnNPCInteraction.spawnChance = f.floatValue(), spawnNPCInteraction -> Float.valueOf(spawnNPCInteraction.spawnChance)).documentation("The chance of the NPC spawning when the interaction is triggered.").add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  83 */   protected Vector3d spawnOffset = new Vector3d();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float spawnYawOffset;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  94 */   protected float spawnChance = 1.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void spawnNPC(@Nonnull Store<EntityStore> store, @Nonnull Vector3i targetBlock) {
/* 103 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/* 104 */     SpawnData spawnData = computeSpawnData(world, targetBlock);
/*     */     
/* 106 */     NPCPlugin.get().spawnNPC(store, this.entityId, null, spawnData.position(), spawnData.rotation());
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
/*     */   @Nonnull
/*     */   private SpawnData computeSpawnData(@Nonnull World world, @Nonnull Vector3i targetBlock) {
/* 120 */     long chunkIndex = ChunkUtil.indexChunkFromBlock(targetBlock.x, targetBlock.z);
/* 121 */     ChunkStore chunkStore = world.getChunkStore();
/*     */     
/* 123 */     Ref<ChunkStore> chunkRef = chunkStore.getChunkReference(chunkIndex);
/* 124 */     if (chunkRef == null || !chunkRef.isValid()) {
/* 125 */       return new SpawnData(this.spawnOffset.clone().add(targetBlock).add(0.5D, 0.5D, 0.5D), Vector3f.ZERO);
/*     */     }
/*     */     
/* 128 */     WorldChunk worldChunkComponent = (WorldChunk)chunkStore.getStore().getComponent(chunkRef, WorldChunk.getComponentType());
/* 129 */     assert worldChunkComponent != null;
/*     */     
/* 131 */     BlockType blockType = worldChunkComponent.getBlockType(targetBlock.x, targetBlock.y, targetBlock.z);
/* 132 */     if (blockType == null) {
/* 133 */       return new SpawnData(this.spawnOffset.clone().add(targetBlock).add(0.5D, 0.5D, 0.5D), Vector3f.ZERO);
/*     */     }
/*     */     
/* 136 */     BlockChunk blockChunkComponent = (BlockChunk)chunkStore.getStore().getComponent(chunkRef, BlockChunk.getComponentType());
/* 137 */     if (blockChunkComponent == null) {
/* 138 */       return new SpawnData(this.spawnOffset.clone().add(targetBlock).add(0.5D, 0.5D, 0.5D), Vector3f.ZERO);
/*     */     }
/*     */     
/* 141 */     BlockSection section = blockChunkComponent.getSectionAtBlockY(targetBlock.y);
/* 142 */     int rotationIndex = section.getRotationIndex(targetBlock.x, targetBlock.y, targetBlock.z);
/* 143 */     RotationTuple rotationTuple = RotationTuple.get(rotationIndex);
/*     */ 
/*     */     
/* 146 */     Vector3d position = rotationTuple.rotate(this.spawnOffset);
/* 147 */     Vector3d blockCenter = new Vector3d();
/* 148 */     blockType.getBlockCenter(rotationIndex, blockCenter);
/* 149 */     position.add(blockCenter).add(targetBlock);
/*     */ 
/*     */     
/* 152 */     Vector3f rotation = new Vector3f(0.0F, (float)(rotationTuple.yaw().getRadians() + Math.toRadians(this.spawnYawOffset)), 0.0F);
/*     */     
/* 154 */     return new SpawnData(position, rotation);
/*     */   } private static final class SpawnData extends Record {
/*     */     @Nonnull
/*     */     private final Vector3d position; @Nonnull
/*     */     private final Vector3f rotation; public final String toString() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/server/npc/interactions/SpawnNPCInteraction$SpawnData;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #163	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/hypixel/hytale/server/npc/interactions/SpawnNPCInteraction$SpawnData;
/*     */     } public final int hashCode() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/server/npc/interactions/SpawnNPCInteraction$SpawnData;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #163	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/hypixel/hytale/server/npc/interactions/SpawnNPCInteraction$SpawnData;
/*     */     }
/* 163 */     private SpawnData(@Nonnull Vector3d position, @Nonnull Vector3f rotation) { this.position = position; this.rotation = rotation; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/server/npc/interactions/SpawnNPCInteraction$SpawnData;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #163	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lcom/hypixel/hytale/server/npc/interactions/SpawnNPCInteraction$SpawnData;
/* 163 */       //   0	8	1	o	Ljava/lang/Object; } @Nonnull public Vector3d position() { return this.position; } @Nonnull public Vector3f rotation() { return this.rotation; }
/*     */   
/*     */   }
/*     */   
/*     */   protected void interactWithBlock(@Nonnull World world, @Nonnull CommandBuffer<EntityStore> commandBuffer, @Nonnull InteractionType type, @Nonnull InteractionContext context, @Nullable ItemStack itemInHand, @Nonnull Vector3i targetBlock, @Nonnull CooldownHandler cooldownHandler) {
/* 168 */     if (ThreadLocalRandom.current().nextFloat() > this.spawnChance) {
/*     */       return;
/*     */     }
/* 171 */     commandBuffer.run(store -> spawnNPC(world.getEntityStore().getStore(), targetBlock));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void simulateInteractWithBlock(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nullable ItemStack itemInHand, @Nonnull World world, @Nonnull Vector3i targetBlock) {
/* 176 */     if (ThreadLocalRandom.current().nextFloat() > this.spawnChance) {
/*     */       return;
/*     */     }
/*     */     
/* 180 */     CommandBuffer<EntityStore> commandBuffer = context.getCommandBuffer();
/* 181 */     assert commandBuffer != null;
/*     */     
/* 183 */     commandBuffer.run(store -> spawnNPC(world.getEntityStore().getStore(), targetBlock));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\interactions\SpawnNPCInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */