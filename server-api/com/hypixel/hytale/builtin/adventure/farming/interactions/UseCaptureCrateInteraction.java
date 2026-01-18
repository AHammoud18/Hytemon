/*     */ package com.hypixel.hytale.builtin.adventure.farming.interactions;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.adventure.farming.states.CoopBlock;
/*     */ import com.hypixel.hytale.builtin.tagset.TagSetPlugin;
/*     */ import com.hypixel.hytale.builtin.tagset.config.NPCGroup;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.protocol.BlockPosition;
/*     */ import com.hypixel.hytale.protocol.InteractionState;
/*     */ import com.hypixel.hytale.protocol.InteractionType;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockFace;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.ModelAsset;
/*     */ import com.hypixel.hytale.server.core.entity.EntityUtils;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*     */ import com.hypixel.hytale.server.core.entity.LivingEntity;
/*     */ import com.hypixel.hytale.server.core.inventory.Inventory;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.modules.block.BlockModule;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.PersistentModel;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInteraction;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.client.SimpleBlockInteraction;
/*     */ import com.hypixel.hytale.server.core.modules.time.WorldTimeResource;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import com.hypixel.hytale.server.npc.metadata.CapturedNPCMetadata;
/*     */ import java.util.function.Supplier;
/*     */ import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
/*     */ import org.checkerframework.checker.nullness.compatqual.NullableDecl;
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
/*     */ public class UseCaptureCrateInteraction
/*     */   extends SimpleBlockInteraction
/*     */ {
/*     */   public static final BuilderCodec<UseCaptureCrateInteraction> CODEC;
/*     */   protected String[] acceptedNpcGroupIds;
/*     */   protected int[] acceptedNpcGroupIndexes;
/*     */   protected String fullIcon;
/*     */   
/*     */   static {
/*  64 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(UseCaptureCrateInteraction.class, UseCaptureCrateInteraction::new, SimpleInteraction.CODEC).appendInherited(new KeyedCodec("AcceptedNpcGroups", NPCGroup.CHILD_ASSET_CODEC_ARRAY), (o, v) -> o.acceptedNpcGroupIds = v, o -> o.acceptedNpcGroupIds, (o, p) -> o.acceptedNpcGroupIds = p.acceptedNpcGroupIds).addValidator((Validator)NPCGroup.VALIDATOR_CACHE.getArrayValidator()).add()).appendInherited(new KeyedCodec("FullIcon", (Codec)Codec.STRING), (o, v) -> o.fullIcon = v, o -> o.fullIcon, (o, p) -> o.fullIcon = p.fullIcon).add()).afterDecode(captureData -> { if (captureData.acceptedNpcGroupIds != null) { captureData.acceptedNpcGroupIndexes = new int[captureData.acceptedNpcGroupIds.length]; for (int i = 0; i < captureData.acceptedNpcGroupIds.length; i++) { int assetIdx = NPCGroup.getAssetMap().getIndex(captureData.acceptedNpcGroupIds[i]); captureData.acceptedNpcGroupIndexes[i] = assetIdx; }  }  })).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void tick0(boolean firstRun, float time, @NonNullDecl InteractionType type, @NonNullDecl InteractionContext context, @NonNullDecl CooldownHandler cooldownHandler) {
/*  72 */     CommandBuffer<EntityStore> commandBuffer = context.getCommandBuffer();
/*     */     
/*  74 */     if (commandBuffer == null) {
/*  75 */       (context.getState()).state = InteractionState.Failed;
/*     */       
/*     */       return;
/*     */     } 
/*  79 */     ItemStack item = context.getHeldItem();
/*  80 */     if (item == null) {
/*  81 */       (context.getState()).state = InteractionState.Failed;
/*     */       
/*     */       return;
/*     */     } 
/*  85 */     Ref<EntityStore> playerRef = context.getEntity();
/*  86 */     LivingEntity playerEntity = (LivingEntity)EntityUtils.getEntity(playerRef, (ComponentAccessor)commandBuffer);
/*  87 */     Inventory playerInventory = playerEntity.getInventory();
/*  88 */     byte activeHotbarSlot = playerInventory.getActiveHotbarSlot();
/*  89 */     ItemStack inHandItemStack = playerInventory.getActiveHotbarItem();
/*     */ 
/*     */     
/*  92 */     CapturedNPCMetadata existingMeta = (CapturedNPCMetadata)item.getFromMetadataOrNull("CapturedEntity", (Codec)CapturedNPCMetadata.CODEC);
/*     */     
/*  94 */     if (existingMeta == null) {
/*  95 */       Ref<EntityStore> targetEntity = context.getTargetEntity();
/*  96 */       if (targetEntity == null) {
/*  97 */         (context.getState()).state = InteractionState.Failed;
/*     */         
/*     */         return;
/*     */       } 
/* 101 */       NPCEntity npc = (NPCEntity)commandBuffer.getComponent(targetEntity, NPCEntity.getComponentType());
/* 102 */       if (npc == null) {
/* 103 */         (context.getState()).state = InteractionState.Failed;
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 108 */       TagSetPlugin.TagSetLookup tagSetPlugin = TagSetPlugin.get(NPCGroup.class);
/* 109 */       boolean tagFound = false;
/* 110 */       for (int group : this.acceptedNpcGroupIndexes) {
/* 111 */         if (tagSetPlugin.tagInSet(group, npc.getRoleIndex())) {
/* 112 */           tagFound = true;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 117 */       if (!tagFound) {
/* 118 */         (context.getState()).state = InteractionState.Failed;
/*     */         
/*     */         return;
/*     */       } 
/* 122 */       PersistentModel persistentModel = (PersistentModel)commandBuffer.getComponent(targetEntity, PersistentModel.getComponentType());
/* 123 */       if (persistentModel == null) {
/* 124 */         (context.getState()).state = InteractionState.Failed;
/*     */         
/*     */         return;
/*     */       } 
/* 128 */       ModelAsset modelAsset = (ModelAsset)ModelAsset.getAssetMap().getAsset(persistentModel.getModelReference().getModelAssetId());
/* 129 */       CapturedNPCMetadata meta = (CapturedNPCMetadata)inHandItemStack.getFromMetadataOrDefault("CapturedEntity", CapturedNPCMetadata.CODEC);
/*     */       
/* 131 */       if (modelAsset != null) {
/* 132 */         meta.setIconPath(modelAsset.getIcon());
/*     */       }
/* 134 */       meta.setRoleIndex(npc.getRoleIndex());
/*     */ 
/*     */       
/* 137 */       String npcName = NPCPlugin.get().getName(npc.getRoleIndex());
/* 138 */       if (npcName != null) {
/* 139 */         meta.setNpcNameKey(npcName);
/*     */       }
/*     */ 
/*     */       
/* 143 */       if (this.fullIcon != null) {
/* 144 */         meta.setFullItemIcon(this.fullIcon);
/*     */       }
/*     */       
/* 147 */       ItemStack itemWithNPC = inHandItemStack.withMetadata(CapturedNPCMetadata.KEYED_CODEC, meta);
/* 148 */       playerInventory.getHotbar().replaceItemStackInSlot((short)activeHotbarSlot, item, itemWithNPC);
/*     */       
/* 150 */       commandBuffer.removeEntity(targetEntity, RemoveReason.REMOVE);
/*     */       
/*     */       return;
/*     */     } 
/* 154 */     super.tick0(firstRun, time, type, context, cooldownHandler);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void interactWithBlock(@NonNullDecl World world, @NonNullDecl CommandBuffer<EntityStore> commandBuffer, @NonNullDecl InteractionType type, @NonNullDecl InteractionContext context, @NullableDecl ItemStack itemInHand, @NonNullDecl Vector3i targetBlock, @NonNullDecl CooldownHandler cooldownHandler) {
/* 159 */     ItemStack item = context.getHeldItem();
/* 160 */     if (item == null) {
/* 161 */       (context.getState()).state = InteractionState.Failed;
/*     */       
/*     */       return;
/*     */     } 
/* 165 */     Ref<EntityStore> playerRef = context.getEntity();
/* 166 */     LivingEntity playerEntity = (LivingEntity)EntityUtils.getEntity(playerRef, (ComponentAccessor)commandBuffer);
/* 167 */     Inventory playerInventory = playerEntity.getInventory();
/* 168 */     byte activeHotbarSlot = playerInventory.getActiveHotbarSlot();
/*     */ 
/*     */     
/* 171 */     CapturedNPCMetadata existingMeta = (CapturedNPCMetadata)item.getFromMetadataOrNull("CapturedEntity", (Codec)CapturedNPCMetadata.CODEC);
/* 172 */     if (existingMeta == null) {
/* 173 */       (context.getState()).state = InteractionState.Failed;
/*     */       
/*     */       return;
/*     */     } 
/* 177 */     BlockPosition pos = context.getTargetBlock();
/* 178 */     if (pos == null) {
/* 179 */       (context.getState()).state = InteractionState.Failed;
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 184 */     WorldChunk worldChunk = world.getChunk(ChunkUtil.indexChunkFromBlock(pos.x, pos.z));
/* 185 */     Ref<ChunkStore> blockRef = worldChunk.getBlockComponentEntity(pos.x, pos.y, pos.z);
/* 186 */     if (blockRef == null) {
/* 187 */       blockRef = BlockModule.ensureBlockEntity(worldChunk, pos.x, pos.y, pos.z);
/*     */     }
/*     */     
/* 190 */     ItemStack noMetaItemStack = item.withMetadata(null);
/*     */     
/* 192 */     if (blockRef != null) {
/* 193 */       Store<ChunkStore> chunkStore = world.getChunkStore().getStore();
/*     */       
/* 195 */       CoopBlock coopBlockState = (CoopBlock)chunkStore.getComponent(blockRef, CoopBlock.getComponentType());
/* 196 */       if (coopBlockState != null) {
/* 197 */         WorldTimeResource worldTimeResource = (WorldTimeResource)commandBuffer.getResource(WorldTimeResource.getResourceType());
/* 198 */         if (coopBlockState.tryPutResident(existingMeta, worldTimeResource)) {
/* 199 */           world.execute(() -> coopBlockState.ensureSpawnResidentsInWorld(world, world.getEntityStore().getStore(), new Vector3d(pos.x, pos.y, pos.z), (new Vector3d()).assign(Vector3d.FORWARD)));
/* 200 */           playerInventory.getHotbar().replaceItemStackInSlot((short)activeHotbarSlot, item, noMetaItemStack);
/*     */         } else {
/* 202 */           (context.getState()).state = InteractionState.Failed;
/*     */         } 
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/*     */     
/* 210 */     Vector3d spawnPos = new Vector3d((pos.x + 0.5F), pos.y, (pos.z + 0.5F));
/*     */     
/* 212 */     if (context.getClientState() != null) {
/* 213 */       BlockFace blockFace = BlockFace.fromProtocolFace((context.getClientState()).blockFace);
/* 214 */       if (blockFace != null) {
/* 215 */         spawnPos.add(blockFace.getDirection());
/*     */       }
/*     */     } 
/* 218 */     NPCPlugin npcModule = NPCPlugin.get();
/* 219 */     Store<EntityStore> store = context.getCommandBuffer().getStore();
/* 220 */     int roleIndex = existingMeta.getRoleIndex();
/*     */     
/* 222 */     commandBuffer.run(_store -> npcModule.spawnEntity(store, roleIndex, spawnPos, Vector3f.ZERO, null, null));
/*     */ 
/*     */ 
/*     */     
/* 226 */     playerInventory.getHotbar().replaceItemStackInSlot((short)activeHotbarSlot, item, noMetaItemStack);
/*     */   }
/*     */   
/*     */   protected void simulateInteractWithBlock(@NonNullDecl InteractionType type, @NonNullDecl InteractionContext context, @NullableDecl ItemStack itemInHand, @NonNullDecl World world, @NonNullDecl Vector3i targetBlock) {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\farming\interactions\UseCaptureCrateInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */