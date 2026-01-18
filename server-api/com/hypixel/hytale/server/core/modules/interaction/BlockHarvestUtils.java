/*     */ package com.hypixel.hytale.server.core.modules.interaction;
/*     */ 
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.spatial.SpatialResource;
/*     */ import com.hypixel.hytale.component.system.EcsEvent;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.protocol.BlockSoundEvent;
/*     */ import com.hypixel.hytale.protocol.SoundCategory;
/*     */ import com.hypixel.hytale.server.core.asset.type.blockhitbox.BlockBoundingBoxes;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocksound.config.BlockSoundSet;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockBreakingDropType;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockGathering;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.HarvestingDropType;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.PhysicsDropType;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.RotationTuple;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.SoftBlockDropType;
/*     */ import com.hypixel.hytale.server.core.asset.type.environment.config.Environment;
/*     */ import com.hypixel.hytale.server.core.asset.type.gameplay.BrokenPenalties;
/*     */ import com.hypixel.hytale.server.core.asset.type.gameplay.GameplayConfig;
/*     */ import com.hypixel.hytale.server.core.asset.type.gameplay.GatheringEffectsConfig;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.Item;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.ItemTool;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.ItemToolSpec;
/*     */ import com.hypixel.hytale.server.core.blocktype.component.BlockPhysics;
/*     */ import com.hypixel.hytale.server.core.entity.ItemUtils;
/*     */ import com.hypixel.hytale.server.core.entity.LivingEntity;
/*     */ import com.hypixel.hytale.server.core.event.events.ecs.BreakBlockEvent;
/*     */ import com.hypixel.hytale.server.core.event.events.ecs.DamageBlockEvent;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
/*     */ import com.hypixel.hytale.server.core.modules.blockhealth.BlockHealth;
/*     */ import com.hypixel.hytale.server.core.modules.blockhealth.BlockHealthChunk;
/*     */ import com.hypixel.hytale.server.core.modules.blockhealth.BlockHealthModule;
/*     */ import com.hypixel.hytale.server.core.modules.blockset.BlockSetModule;
/*     */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*     */ import com.hypixel.hytale.server.core.modules.entity.item.ItemComponent;
/*     */ import com.hypixel.hytale.server.core.modules.item.ItemModule;
/*     */ import com.hypixel.hytale.server.core.modules.time.TimeResource;
/*     */ import com.hypixel.hytale.server.core.universe.world.ParticleUtil;
/*     */ import com.hypixel.hytale.server.core.universe.world.SoundUtil;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.ChunkColumn;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.BlockSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.connectedblocks.ConnectedBlocksUtil;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.core.util.FillerBlockUtil;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectList;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectLists;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class BlockHarvestUtils
/*     */ {
/*     */   @Nullable
/*     */   public static ItemToolSpec getSpecPowerDamageBlock(@Nullable Item item, @Nullable BlockType blockType, @Nullable ItemTool tool) {
/*  72 */     if (blockType == null) return null;
/*     */     
/*  74 */     BlockGathering gathering = blockType.getGathering();
/*  75 */     if (gathering == null) return null;
/*     */     
/*  77 */     BlockBreakingDropType breaking = gathering.getBreaking();
/*  78 */     if (breaking == null) return null;
/*     */     
/*  80 */     String gatherType = breaking.getGatherType();
/*  81 */     if (gatherType == null) return null;
/*     */     
/*  83 */     if (item != null && (
/*  84 */       item.getWeapon() != null || item.getBuilderToolData() != null)) {
/*  85 */       return null;
/*     */     }
/*     */ 
/*     */     
/*  89 */     if (tool != null) {
/*  90 */       if (tool.getSpecs() != null) {
/*  91 */         for (ItemToolSpec spec : tool.getSpecs()) {
/*  92 */           if (Objects.equals(spec.getGatherType(), gatherType)) {
/*  93 */             return spec;
/*     */           }
/*     */         } 
/*     */       }
/*     */       
/*  98 */       return null;
/*     */     } 
/*     */     
/* 101 */     return (ItemToolSpec)ItemToolSpec.getAssetMap().getAsset(gatherType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double calculateDurabilityUse(@Nonnull Item item, @Nullable BlockType blockType) {
/* 112 */     if (blockType == null) return 0.0D; 
/* 113 */     if (blockType.getGathering().isSoft()) return 0.0D; 
/* 114 */     if (item.getTool() == null) return 0.0D;
/*     */     
/* 116 */     ItemTool itemTool = item.getTool();
/* 117 */     ItemTool.DurabilityLossBlockTypes[] durabilityLossBlockTypes = itemTool.getDurabilityLossBlockTypes();
/* 118 */     if (durabilityLossBlockTypes == null) return item.getDurabilityLossOnHit();
/*     */     
/* 120 */     String hitBlockTypeId = blockType.getId();
/* 121 */     int hitBlockTypeIndex = BlockType.getAssetMap().getIndex(hitBlockTypeId);
/* 122 */     if (hitBlockTypeIndex == Integer.MIN_VALUE) throw new IllegalArgumentException("Unknown key! " + hitBlockTypeId); 
/* 123 */     BlockSetModule blockSetModule = BlockSetModule.getInstance();
/*     */     
/* 125 */     for (ItemTool.DurabilityLossBlockTypes durabilityLossBlockType : durabilityLossBlockTypes) {
/* 126 */       int[] blockTypeIndexes = durabilityLossBlockType.getBlockTypeIndexes();
/* 127 */       if (blockTypeIndexes != null) {
/* 128 */         for (int blockTypeIndex : blockTypeIndexes) {
/* 129 */           if (blockTypeIndex == hitBlockTypeIndex) {
/* 130 */             return durabilityLossBlockType.getDurabilityLossOnHit();
/*     */           }
/*     */         } 
/*     */       }
/*     */       
/* 135 */       int[] blockSetIndexes = durabilityLossBlockType.getBlockSetIndexes();
/* 136 */       if (blockSetIndexes != null) {
/* 137 */         for (int blockSetIndex : blockSetIndexes) {
/* 138 */           if (blockSetModule.blockInSet(blockSetIndex, hitBlockTypeId)) {
/* 139 */             return durabilityLossBlockType.getDurabilityLossOnHit();
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 145 */     return item.getDurabilityLossOnHit();
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
/*     */   public static boolean performBlockDamage(@Nonnull Vector3i targetBlock, @Nullable ItemStack itemStack, @Nullable ItemTool tool, float damageScale, int setBlockSettings, @Nonnull Ref<ChunkStore> chunkReference, @Nonnull CommandBuffer<EntityStore> commandBuffer, @Nonnull ComponentAccessor<ChunkStore> chunkStore) {
/* 169 */     return performBlockDamage(null, null, targetBlock, itemStack, tool, null, false, damageScale, setBlockSettings, chunkReference, (ComponentAccessor<EntityStore>)commandBuffer, chunkStore);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean performBlockDamage(@Nullable LivingEntity entity, @Nullable Ref<EntityStore> ref, @Nonnull Vector3i targetBlockPos, @Nullable ItemStack itemStack, @Nullable ItemTool tool, @Nullable String toolId, boolean matchTool, float damageScale, int setBlockSettings, @Nonnull Ref<ChunkStore> chunkReference, @Nonnull ComponentAccessor<EntityStore> entityStore, @Nonnull ComponentAccessor<ChunkStore> chunkStore) {
/*     */     String itemId, dropListId;
/* 202 */     World world = ((EntityStore)entityStore.getExternalData()).getWorld();
/* 203 */     GameplayConfig gameplayConfig = world.getGameplayConfig();
/*     */     
/* 205 */     WorldChunk worldChunkComponent = (WorldChunk)chunkStore.getComponent(chunkReference, WorldChunk.getComponentType());
/* 206 */     if (worldChunkComponent == null) return false;
/*     */     
/* 208 */     BlockChunk blockChunkComponent = (BlockChunk)chunkStore.getComponent(chunkReference, BlockChunk.getComponentType());
/* 209 */     assert blockChunkComponent != null;
/*     */     
/* 211 */     BlockSection targetSection = blockChunkComponent.getSectionAtBlockY(targetBlockPos.y);
/* 212 */     int targetRotationIndex = targetSection.getRotationIndex(targetBlockPos.x, targetBlockPos.y, targetBlockPos.z);
/*     */     
/* 214 */     boolean brokeBlock = false;
/* 215 */     int environmentId = blockChunkComponent.getEnvironment(targetBlockPos.x, targetBlockPos.y, targetBlockPos.z);
/* 216 */     Environment environmentAsset = (Environment)Environment.getAssetMap().getAsset(environmentId);
/*     */     
/* 218 */     SpatialResource<Ref<EntityStore>, EntityStore> playerSpatialResource = (SpatialResource<Ref<EntityStore>, EntityStore>)entityStore.getResource(EntityModule.get().getPlayerSpatialResourceType());
/*     */ 
/*     */ 
/*     */     
/* 222 */     if (environmentAsset != null && !environmentAsset.isBlockModificationAllowed()) {
/* 223 */       targetSection.invalidateBlock(targetBlockPos.x, targetBlockPos.y, targetBlockPos.z);
/* 224 */       return false;
/*     */     } 
/*     */     
/* 227 */     BlockType targetBlockType = worldChunkComponent.getBlockType(targetBlockPos.x, targetBlockPos.y, targetBlockPos.z);
/* 228 */     if (targetBlockType == null) return false;
/*     */     
/* 230 */     BlockGathering blockGathering = targetBlockType.getGathering();
/* 231 */     if (blockGathering == null) return false;
/*     */ 
/*     */     
/* 234 */     if (matchTool && !blockGathering.getToolData().containsKey(toolId)) return false;
/*     */     
/* 236 */     Vector3d targetBlockCenterPos = new Vector3d();
/* 237 */     targetBlockType.getBlockCenter(targetRotationIndex, targetBlockCenterPos);
/* 238 */     targetBlockCenterPos.add(targetBlockPos);
/*     */     
/* 240 */     Vector3i originBlock = new Vector3i(targetBlockPos.x, targetBlockPos.y, targetBlockPos.z);
/*     */     
/* 242 */     if (!targetBlockType.isUnknown()) {
/* 243 */       int filler = targetSection.getFiller(targetBlockPos.x, targetBlockPos.y, targetBlockPos.z);
/* 244 */       int fillerX = FillerBlockUtil.unpackX(filler);
/* 245 */       int fillerY = FillerBlockUtil.unpackY(filler);
/* 246 */       int fillerZ = FillerBlockUtil.unpackZ(filler);
/*     */       
/* 248 */       if (fillerX != 0 || fillerY != 0 || fillerZ != 0) {
/*     */         
/* 250 */         originBlock = originBlock.clone().subtract(fillerX, fillerY, fillerZ);
/*     */         
/* 252 */         String oldBlockTypeKey = targetBlockType.getId();
/* 253 */         targetBlockType = world.getBlockType(originBlock.getX(), originBlock.getY(), originBlock.getZ());
/* 254 */         if (targetBlockType == null) return false;
/*     */         
/* 256 */         if (!oldBlockTypeKey.equals(targetBlockType.getId())) {
/* 257 */           worldChunkComponent.breakBlock(targetBlockPos.x, targetBlockPos.y, targetBlockPos.z);
/* 258 */           return true;
/*     */         } 
/*     */         
/* 261 */         blockGathering = targetBlockType.getGathering();
/* 262 */         if (blockGathering == null) return false;
/*     */       
/*     */       } 
/*     */     } 
/* 266 */     Item heldItem = (itemStack != null) ? itemStack.getItem() : null;
/* 267 */     if (tool == null && heldItem != null) {
/* 268 */       tool = heldItem.getTool();
/*     */     }
/*     */     
/* 271 */     ItemToolSpec itemToolSpec = getSpecPowerDamageBlock(heldItem, targetBlockType, tool);
/* 272 */     float specPower = (itemToolSpec != null) ? itemToolSpec.getPower() : 0.0F;
/*     */     
/* 274 */     boolean canApplyItemStackPenalties = (entity != null && entity.canApplyItemStackPenalties(ref, entityStore));
/* 275 */     if (specPower != 0.0F && heldItem != null && heldItem.getTool() != null && itemStack.isBroken() && canApplyItemStackPenalties) {
/* 276 */       BrokenPenalties brokenPenalties = gameplayConfig.getItemDurabilityConfig().getBrokenPenalties();
/* 277 */       specPower *= 1.0F - (float)brokenPenalties.getTool(0.0D);
/*     */     } 
/*     */ 
/*     */     
/* 281 */     int dropQuantity = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 286 */     if (specPower != 0.0F) {
/* 287 */       BlockBreakingDropType breaking = blockGathering.getBreaking();
/*     */       
/* 289 */       damage = specPower;
/* 290 */       dropQuantity = breaking.getQuantity();
/* 291 */       itemId = breaking.getItemId();
/* 292 */       dropListId = breaking.getDropListId();
/*     */     
/*     */     }
/* 295 */     else if (blockGathering.isSoft()) {
/* 296 */       SoftBlockDropType soft = blockGathering.getSoft();
/*     */       
/* 298 */       if (!soft.isWeaponBreakable() && heldItem != null && heldItem.getWeapon() != null) {
/* 299 */         return false;
/*     */       }
/* 301 */       damage = 1.0F;
/* 302 */       itemId = soft.getItemId();
/* 303 */       dropListId = soft.getDropListId();
/*     */ 
/*     */       
/* 306 */       damageScale = 1.0F;
/*     */     }
/*     */     else {
/*     */       
/* 310 */       if (heldItem != null && heldItem.getWeapon() == null) {
/* 311 */         if (ref != null && entity != null) {
/* 312 */           GatheringEffectsConfig unbreakableBlockConfig = gameplayConfig.getGatheringConfig().getUnbreakableBlockConfig();
/* 313 */           if ((setBlockSettings & 0x4) == 0) {
/* 314 */             String particleSystemId = unbreakableBlockConfig.getParticleSystemId();
/* 315 */             if (particleSystemId != null) {
/* 316 */               ObjectList<Ref<EntityStore>> results = SpatialResource.getThreadLocalReferenceList();
/* 317 */               playerSpatialResource.getSpatialStructure().collect(targetBlockCenterPos, 75.0D, (List)results);
/*     */               
/* 319 */               ParticleUtil.spawnParticleEffect(particleSystemId, targetBlockCenterPos, (List)results, entityStore);
/*     */             } 
/*     */           } 
/*     */           
/* 323 */           if ((setBlockSettings & 0x400) == 0) {
/* 324 */             int soundEventIndex = unbreakableBlockConfig.getSoundEventIndex();
/* 325 */             if (soundEventIndex != 0) {
/* 326 */               SoundUtil.playSoundEvent3d(ref, soundEventIndex, targetBlockCenterPos, entityStore);
/*     */             }
/*     */             
/* 329 */             if (heldItem.getTool() != null) {
/* 330 */               int hitSoundEventLayerIndex = heldItem.getTool().getIncorrectMaterialSoundLayerIndex();
/* 331 */               if (hitSoundEventLayerIndex != 0) {
/* 332 */                 SoundUtil.playSoundEvent3d(ref, hitSoundEventLayerIndex, targetBlockCenterPos, entityStore);
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 338 */         return false;
/*     */       } 
/* 340 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 344 */     damage *= damageScale;
/*     */     
/* 346 */     ChunkColumn chunkColumnComponent = (ChunkColumn)chunkStore.getComponent(chunkReference, ChunkColumn.getComponentType());
/* 347 */     Ref<ChunkStore> chunkSectionRef = (chunkColumnComponent != null) ? chunkColumnComponent.getSection(ChunkUtil.chunkCoordinate(targetBlockPos.y)) : null;
/*     */     
/* 349 */     if (targetBlockType.getGathering().shouldUseDefaultDropWhenPlaced()) {
/* 350 */       BlockPhysics decoBlocks = (chunkSectionRef != null) ? (BlockPhysics)chunkStore.getComponent(chunkSectionRef, BlockPhysics.getComponentType()) : null;
/* 351 */       boolean isDeco = (decoBlocks != null && decoBlocks.isDeco(targetBlockPos.x, targetBlockPos.y, targetBlockPos.z));
/* 352 */       if (isDeco) {
/* 353 */         itemId = null;
/* 354 */         dropListId = null;
/*     */       } 
/*     */     } 
/*     */     
/* 358 */     TimeResource timeResource = (TimeResource)entityStore.getResource(TimeResource.getResourceType());
/* 359 */     BlockHealthChunk blockHealthComponent = (BlockHealthChunk)chunkStore.getComponent(chunkReference, BlockHealthModule.get().getBlockHealthChunkComponentType());
/* 360 */     assert blockHealthComponent != null;
/*     */     
/* 362 */     float current = blockHealthComponent.getBlockHealth(originBlock);
/* 363 */     DamageBlockEvent event = new DamageBlockEvent(itemStack, originBlock, targetBlockType, current, damage);
/*     */     
/* 365 */     if (ref != null) {
/* 366 */       entityStore.invoke(ref, (EcsEvent)event);
/*     */     } else {
/* 368 */       entityStore.invoke((EcsEvent)event);
/*     */     } 
/*     */ 
/*     */     
/* 372 */     if (event.isCancelled()) {
/* 373 */       targetSection.invalidateBlock(targetBlockPos.x, targetBlockPos.y, targetBlockPos.z);
/* 374 */       return false;
/*     */     } 
/* 376 */     float damage = event.getDamage();
/*     */ 
/*     */     
/* 379 */     targetBlockType = event.getBlockType();
/* 380 */     targetBlockPos = event.getTargetBlock();
/* 381 */     targetSection = blockChunkComponent.getSectionAtBlockY(targetBlockPos.y);
/* 382 */     targetRotationIndex = targetSection.getRotationIndex(targetBlockPos.x, targetBlockPos.y, targetBlockPos.z);
/* 383 */     targetBlockType.getBlockCenter(targetRotationIndex, targetBlockCenterPos);
/* 384 */     targetBlockCenterPos.add(targetBlockPos);
/*     */     
/* 386 */     BlockHealth blockDamage = blockHealthComponent.damageBlock(timeResource.getNow(), world, targetBlockPos, damage);
/*     */     
/* 388 */     if (blockHealthComponent.isBlockFragile(targetBlockPos) || blockDamage.isDestroyed()) {
/* 389 */       BlockGathering.BlockToolData requiredTool = (BlockGathering.BlockToolData)blockGathering.getToolData().get(toolId);
/* 390 */       boolean toolsMatch = (requiredTool != null);
/*     */       
/* 392 */       if (!toolsMatch) {
/* 393 */         performBlockBreak(world, targetBlockPos, targetBlockType, itemStack, dropQuantity, itemId, dropListId, setBlockSettings, ref, chunkReference, entityStore, chunkStore);
/* 394 */         brokeBlock = true;
/*     */       } else {
/*     */         
/* 397 */         String toolStateId = requiredTool.getStateId();
/* 398 */         BlockType newBlockType = (toolStateId != null) ? targetBlockType.getBlockForState(toolStateId) : null;
/* 399 */         boolean shouldChangeState = (newBlockType != null && !targetBlockType.getId().equals(newBlockType.getId()));
/*     */         
/* 401 */         if (shouldChangeState) {
/*     */           
/* 403 */           blockDamage.setHealth(1.0F);
/*     */           
/* 405 */           worldChunkComponent.setBlock(targetBlockPos.x, targetBlockPos.y, targetBlockPos.z, newBlockType);
/*     */           
/* 407 */           if ((setBlockSettings & 0x400) == 0) {
/* 408 */             BlockSoundSet soundSet = (BlockSoundSet)BlockSoundSet.getAssetMap().getAsset(targetBlockType.getBlockSoundSetIndex());
/* 409 */             if (soundSet != null) {
/* 410 */               int soundEventIndex = soundSet.getSoundEventIndices().getOrDefault(BlockSoundEvent.Break, 0);
/* 411 */               if (soundEventIndex != 0) {
/* 412 */                 SoundUtil.playSoundEvent3d(soundEventIndex, SoundCategory.SFX, targetBlockCenterPos, entityStore);
/*     */               }
/*     */             } 
/*     */           } 
/*     */ 
/*     */           
/* 418 */           if ((setBlockSettings & 0x800) == 0) {
/* 419 */             List<ItemStack> itemStacks = getDrops(targetBlockType, 1, requiredTool.getItemId(), requiredTool.getDropListId());
/* 420 */             Vector3d dropPosition = new Vector3d(targetBlockPos.x + 0.5D, targetBlockPos.y, targetBlockPos.z + 0.5D);
/* 421 */             Holder[] arrayOfHolder = ItemComponent.generateItemDrops(entityStore, itemStacks, dropPosition, Vector3f.ZERO);
/* 422 */             entityStore.addEntities(arrayOfHolder, AddReason.SPAWN);
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 427 */           performBlockBreak(world, targetBlockPos, targetBlockType, itemStack, dropQuantity, itemId, dropListId, setBlockSettings | 0x800, ref, chunkReference, entityStore, chunkStore);
/* 428 */           brokeBlock = true;
/*     */ 
/*     */           
/* 431 */           if ((setBlockSettings & 0x800) == 0) {
/* 432 */             List<ItemStack> toolDrops = getDrops(targetBlockType, 1, requiredTool.getItemId(), requiredTool.getDropListId());
/* 433 */             if (!toolDrops.isEmpty()) {
/* 434 */               Vector3d dropPosition = new Vector3d(targetBlockPos.x + 0.5D, targetBlockPos.y, targetBlockPos.z + 0.5D);
/* 435 */               Holder[] arrayOfHolder = ItemComponent.generateItemDrops(entityStore, toolDrops, dropPosition, Vector3f.ZERO);
/* 436 */               entityStore.addEntities(arrayOfHolder, AddReason.SPAWN);
/*     */             }
/*     */           
/*     */           }
/*     */         
/*     */         } 
/*     */       } 
/* 443 */     } else if ((setBlockSettings & 0x400) == 0) {
/* 444 */       BlockSoundSet soundSet = (BlockSoundSet)BlockSoundSet.getAssetMap().getAsset(targetBlockType.getBlockSoundSetIndex());
/* 445 */       if (soundSet != null) {
/* 446 */         int soundEventIndex = soundSet.getSoundEventIndices().getOrDefault(BlockSoundEvent.Hit, 0);
/* 447 */         if (soundEventIndex != 0) {
/* 448 */           SoundUtil.playSoundEvent3d(soundEventIndex, SoundCategory.SFX, targetBlockCenterPos, entityStore);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 454 */     if (ref != null && entity != null) {
/* 455 */       if ((setBlockSettings & 0x400) == 0 && 
/* 456 */         !targetBlockCenterPos.equals(Vector3d.MAX)) {
/* 457 */         int hitSoundEventLayerIndex = 0;
/* 458 */         if (itemToolSpec != null) {
/* 459 */           hitSoundEventLayerIndex = itemToolSpec.getHitSoundLayerIndex();
/*     */         }
/*     */         
/* 462 */         if (hitSoundEventLayerIndex == 0 && heldItem != null && heldItem.getTool() != null) {
/* 463 */           hitSoundEventLayerIndex = heldItem.getTool().getHitSoundLayerIndex();
/*     */         }
/*     */         
/* 466 */         if (hitSoundEventLayerIndex != 0) {
/* 467 */           SoundUtil.playSoundEvent3d(ref, hitSoundEventLayerIndex, targetBlockCenterPos.getX(), targetBlockCenterPos.getY(), targetBlockCenterPos.getZ(), entityStore);
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 472 */       if (itemToolSpec != null && itemToolSpec.isIncorrect()) {
/* 473 */         GatheringEffectsConfig incorrectToolConfig = gameplayConfig.getGatheringConfig().getIncorrectToolConfig();
/* 474 */         if (incorrectToolConfig != null) {
/* 475 */           if ((setBlockSettings & 0x4) == 0) {
/* 476 */             String particleSystemId = incorrectToolConfig.getParticleSystemId();
/* 477 */             if (particleSystemId != null) {
/* 478 */               ObjectList<Ref<EntityStore>> results = SpatialResource.getThreadLocalReferenceList();
/* 479 */               playerSpatialResource.getSpatialStructure().collect(targetBlockCenterPos, 75.0D, (List)results);
/*     */               
/* 481 */               ParticleUtil.spawnParticleEffect(particleSystemId, targetBlockCenterPos, (List)results, entityStore);
/*     */             } 
/*     */           } 
/*     */           
/* 485 */           if ((setBlockSettings & 0x400) == 0) {
/* 486 */             int soundEventIndex = incorrectToolConfig.getSoundEventIndex();
/* 487 */             if (soundEventIndex != 0) {
/* 488 */               SoundUtil.playSoundEvent3d(ref, soundEventIndex, targetBlockCenterPos, entityStore);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 495 */     if (entity != null && ref != null && 
/* 496 */       entity.canDecreaseItemStackDurability(ref, entityStore) && itemStack != null && !itemStack.isUnbreakable()) {
/* 497 */       byte activeHotbarSlot = entity.getInventory().getActiveHotbarSlot();
/* 498 */       if (activeHotbarSlot != -1) {
/* 499 */         double durability = calculateDurabilityUse(heldItem, targetBlockType);
/* 500 */         ItemContainer hotbar = entity.getInventory().getHotbar();
/* 501 */         entity.updateItemStackDurability(ref, itemStack, hotbar, activeHotbarSlot, -durability, entityStore);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 506 */     return brokeBlock;
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
/*     */   public static void performBlockBreak(@Nullable Ref<EntityStore> ref, @Nullable ItemStack heldItemStack, @Nonnull Vector3i targetBlock, @Nonnull Ref<ChunkStore> chunkReference, @Nonnull ComponentAccessor<EntityStore> entityStore, @Nonnull ComponentAccessor<ChunkStore> chunkStore) {
/* 525 */     performBlockBreak(ref, heldItemStack, targetBlock, 0, chunkReference, entityStore, chunkStore);
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
/*     */   public static void performBlockBreak(@Nullable Ref<EntityStore> ref, @Nullable ItemStack heldItemStack, @Nonnull Vector3i targetBlock, int setBlockSettings, @Nonnull Ref<ChunkStore> chunkReference, @Nonnull ComponentAccessor<EntityStore> entityStore, @Nonnull ComponentAccessor<ChunkStore> chunkStore) {
/* 546 */     World world = ((ChunkStore)chunkStore.getExternalData()).getWorld();
/*     */     
/* 548 */     int targetBlockX = targetBlock.getX();
/* 549 */     int targetBlockY = targetBlock.getY();
/* 550 */     int targetBlockZ = targetBlock.getZ();
/*     */     
/* 552 */     WorldChunk worldChunkComponent = (WorldChunk)chunkStore.getComponent(chunkReference, WorldChunk.getComponentType());
/* 553 */     assert worldChunkComponent != null;
/*     */     
/* 555 */     int targetBlockTypeIndex = worldChunkComponent.getBlock(targetBlockX, targetBlockY, targetBlockZ);
/* 556 */     BlockType targetBlockTypeAsset = (BlockType)BlockType.getAssetMap().getAsset(targetBlockTypeIndex);
/* 557 */     if (targetBlockTypeAsset == null)
/*     */       return; 
/* 559 */     Vector3i affectedBlock = targetBlock;
/* 560 */     if (!targetBlockTypeAsset.isUnknown()) {
/* 561 */       BlockChunk blockChunkComponent = (BlockChunk)chunkStore.getComponent(chunkReference, BlockChunk.getComponentType());
/* 562 */       assert blockChunkComponent != null;
/*     */       
/* 564 */       BlockSection targetBlockSection = blockChunkComponent.getSectionAtBlockY(targetBlockY);
/* 565 */       int filler = targetBlockSection.getFiller(targetBlockX, targetBlockY, targetBlockZ);
/*     */       
/* 567 */       int fillerX = FillerBlockUtil.unpackX(filler);
/* 568 */       int fillerY = FillerBlockUtil.unpackY(filler);
/* 569 */       int fillerZ = FillerBlockUtil.unpackZ(filler);
/*     */       
/* 571 */       if (fillerX != 0 || fillerY != 0 || fillerZ != 0) {
/*     */         
/* 573 */         affectedBlock = affectedBlock.clone().subtract(fillerX, fillerY, fillerZ);
/*     */         
/* 575 */         BlockType originBlock = world.getBlockType(affectedBlock);
/* 576 */         if (originBlock != null && !targetBlockTypeAsset.getId().equals(originBlock.getId())) {
/* 577 */           world.breakBlock(targetBlockX, targetBlockY, targetBlockZ, setBlockSettings);
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     } 
/* 583 */     performBlockBreak(world, affectedBlock, targetBlockTypeAsset, heldItemStack, 0, null, null, setBlockSettings, ref, chunkReference, entityStore, chunkStore);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void performBlockBreak(@Nonnull World world, @Nonnull Vector3i blockPosition, @Nonnull BlockType targetBlockTypeKey, @Nullable ItemStack heldItemStack, int dropQuantity, @Nullable String dropItemId, @Nullable String dropListId, int setBlockSettings, @Nullable Ref<EntityStore> ref, @Nonnull Ref<ChunkStore> chunkReference, @Nonnull ComponentAccessor<EntityStore> entityStore, @Nonnull ComponentAccessor<ChunkStore> chunkStore) {
/*     */     Store store;
/* 614 */     World targetWorld = world;
/* 615 */     Vector3i targetBlockPosition = blockPosition;
/* 616 */     Ref<ChunkStore> targetChunkReference = chunkReference;
/* 617 */     ComponentAccessor<ChunkStore> targetChunkStore = chunkStore;
/*     */     
/* 619 */     if (ref != null) {
/* 620 */       BreakBlockEvent event = new BreakBlockEvent(heldItemStack, targetBlockPosition, targetBlockTypeKey);
/* 621 */       entityStore.invoke(ref, (EcsEvent)event);
/*     */       
/* 623 */       if (event.isCancelled()) {
/* 624 */         BlockChunk blockChunk = (BlockChunk)chunkStore.getComponent(targetChunkReference, BlockChunk.getComponentType());
/* 625 */         assert blockChunk != null;
/*     */         
/* 627 */         BlockSection blockSection1 = blockChunk.getSectionAtBlockY(targetBlockPosition.getY());
/* 628 */         blockSection1.invalidateBlock(targetBlockPosition.getX(), targetBlockPosition.getY(), targetBlockPosition.getZ());
/*     */         
/*     */         return;
/*     */       } 
/* 632 */       targetBlockPosition = event.getTargetBlock();
/* 633 */       store = targetWorld.getChunkStore().getStore();
/*     */       
/* 635 */       long chunkIndex = ChunkUtil.indexChunkFromBlock(targetBlockPosition.x, targetBlockPosition.z);
/* 636 */       targetChunkReference = ((ChunkStore)store.getExternalData()).getChunkReference(chunkIndex);
/* 637 */       if (targetChunkReference == null || !targetChunkReference.isValid()) {
/*     */         return;
/*     */       }
/*     */     } 
/* 641 */     if (!targetBlockPosition.equals(blockPosition) || !targetWorld.equals(world)) {
/*     */ 
/*     */       
/* 644 */       BlockChunk blockChunk = (BlockChunk)chunkStore.getComponent(chunkReference, BlockChunk.getComponentType());
/* 645 */       assert blockChunk != null;
/*     */       
/* 647 */       BlockSection blockSection1 = blockChunk.getSectionAtBlockY(blockPosition.getY());
/* 648 */       blockSection1.invalidateBlock(blockPosition.getX(), blockPosition.getY(), blockPosition.getZ());
/*     */     } 
/*     */     
/* 651 */     int x = blockPosition.getX();
/* 652 */     int y = blockPosition.getY();
/* 653 */     int z = blockPosition.getZ();
/*     */     
/* 655 */     BlockChunk blockChunkComponent = (BlockChunk)chunkStore.getComponent(targetChunkReference, BlockChunk.getComponentType());
/* 656 */     assert blockChunkComponent != null;
/*     */     
/* 658 */     BlockSection blockSection = blockChunkComponent.getSectionAtBlockY(y);
/* 659 */     int filler = blockSection.getFiller(x, y, z);
/*     */     
/* 661 */     int blockTypeIndex = blockSection.get(x, y, z);
/* 662 */     BlockType blockTypeAsset = (BlockType)BlockType.getAssetMap().getAsset(blockTypeIndex);
/*     */ 
/*     */     
/* 665 */     boolean isNaturalBlockBreak = BlockInteractionUtils.isNaturalAction(ref, entityStore);
/* 666 */     setBlockSettings |= 0x100;
/*     */     
/* 668 */     if (!isNaturalBlockBreak) {
/* 669 */       setBlockSettings |= 0x800;
/*     */     }
/*     */     
/* 672 */     naturallyRemoveBlock(targetBlockPosition, blockTypeAsset, filler, dropQuantity, dropItemId, dropListId, setBlockSettings, targetChunkReference, entityStore, (ComponentAccessor<ChunkStore>)store);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static void naturallyRemoveBlockByPhysics(@Nonnull Vector3i blockPosition, @Nonnull BlockType blockType, int filler, int setBlockSettings, @Nonnull Ref<ChunkStore> chunkReference, @Nonnull ComponentAccessor<EntityStore> entityStore, @Nonnull ComponentAccessor<ChunkStore> chunkStore) {
/* 704 */     int quantity = 1;
/* 705 */     String itemId = null;
/* 706 */     String dropListId = null;
/*     */ 
/*     */     
/* 709 */     BlockGathering blockGathering = blockType.getGathering();
/* 710 */     if (blockGathering != null) {
/* 711 */       PhysicsDropType physics = blockGathering.getPhysics();
/* 712 */       BlockBreakingDropType breaking = blockGathering.getBreaking();
/* 713 */       SoftBlockDropType soft = blockGathering.getSoft();
/* 714 */       HarvestingDropType harvest = blockGathering.getHarvest();
/*     */       
/* 716 */       if (physics != null) {
/* 717 */         itemId = physics.getItemId();
/* 718 */         dropListId = physics.getDropListId();
/* 719 */       } else if (breaking != null) {
/* 720 */         quantity = breaking.getQuantity();
/* 721 */         itemId = breaking.getItemId();
/* 722 */         dropListId = breaking.getDropListId();
/* 723 */       } else if (soft != null) {
/* 724 */         itemId = soft.getItemId();
/* 725 */         dropListId = soft.getDropListId();
/* 726 */       } else if (harvest != null) {
/* 727 */         itemId = harvest.getItemId();
/* 728 */         dropListId = harvest.getDropListId();
/*     */       } 
/*     */     } 
/*     */     
/* 732 */     setBlockSettings |= 0x20;
/* 733 */     naturallyRemoveBlock(blockPosition, blockType, filler, quantity, itemId, dropListId, setBlockSettings, chunkReference, entityStore, chunkStore);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static void naturallyRemoveBlock(@Nonnull Vector3i blockPosition, @Nullable BlockType blockType, int filler, int quantity, String itemId, String dropListId, int setBlockSettings, @Nonnull Ref<ChunkStore> chunkReference, @Nonnull ComponentAccessor<EntityStore> entityStore, @Nonnull ComponentAccessor<ChunkStore> chunkStore) {
/* 760 */     if (blockType == null)
/*     */       return; 
/* 762 */     WorldChunk worldChunkComponent = (WorldChunk)chunkStore.getComponent(chunkReference, WorldChunk.getComponentType());
/* 763 */     assert worldChunkComponent != null;
/*     */     
/* 765 */     BlockChunk blockChunkComponent = (BlockChunk)chunkStore.getComponent(chunkReference, BlockChunk.getComponentType());
/* 766 */     assert blockChunkComponent != null;
/*     */     
/* 768 */     Vector3i affectedBlock = blockPosition;
/* 769 */     if (!blockType.isUnknown()) {
/* 770 */       int fillerX = FillerBlockUtil.unpackX(filler);
/* 771 */       int fillerY = FillerBlockUtil.unpackY(filler);
/* 772 */       int fillerZ = FillerBlockUtil.unpackZ(filler);
/*     */ 
/*     */       
/* 775 */       if (fillerX != 0 || fillerY != 0 || fillerZ != 0) {
/*     */         
/* 777 */         affectedBlock = affectedBlock.clone().subtract(fillerX, fillerY, fillerZ);
/*     */         
/* 779 */         String oldBlockTypeKey = blockType.getId();
/* 780 */         blockType = worldChunkComponent.getBlockType(affectedBlock.getX(), affectedBlock.getY(), affectedBlock.getZ());
/* 781 */         if (blockType == null) {
/* 782 */           throw new IllegalStateException("Null block type fetched for " + String.valueOf(affectedBlock) + " during block break");
/*     */         }
/*     */         
/* 785 */         if (!oldBlockTypeKey.equals(blockType.getId())) {
/* 786 */           worldChunkComponent.breakBlock(blockPosition.getX(), blockPosition.getY(), blockPosition.getZ(), setBlockSettings);
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 793 */     if ((setBlockSettings & 0x400) == 0) {
/* 794 */       BlockSoundSet soundSet = (BlockSoundSet)BlockSoundSet.getAssetMap().getAsset(blockType.getBlockSoundSetIndex());
/*     */       
/* 796 */       if (soundSet != null) {
/* 797 */         int soundEventIndex = soundSet.getSoundEventIndices().getOrDefault(BlockSoundEvent.Break, 0);
/*     */         
/* 799 */         if (soundEventIndex != 0) {
/* 800 */           BlockSection section = blockChunkComponent.getSectionAtBlockY(blockPosition.getY());
/* 801 */           int rotationIndex = section.getRotationIndex(blockPosition.getX(), blockPosition.getY(), blockPosition.getZ());
/* 802 */           Vector3d centerPosition = new Vector3d();
/* 803 */           blockType.getBlockCenter(rotationIndex, centerPosition);
/* 804 */           centerPosition.add(blockPosition);
/*     */           
/* 806 */           SoundUtil.playSoundEvent3d(soundEventIndex, SoundCategory.SFX, centerPosition, entityStore);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 811 */     removeBlock(affectedBlock, blockType, setBlockSettings, chunkReference, chunkStore);
/*     */ 
/*     */     
/* 814 */     if ((setBlockSettings & 0x800) == 0 && quantity > 0) {
/* 815 */       Vector3d dropPosition = blockPosition.toVector3d().add(0.5D, 0.0D, 0.5D);
/* 816 */       List<ItemStack> itemStacks = getDrops(blockType, quantity, itemId, dropListId);
/* 817 */       Holder[] arrayOfHolder = ItemComponent.generateItemDrops(entityStore, itemStacks, dropPosition, Vector3f.ZERO);
/* 818 */       entityStore.addEntities(arrayOfHolder, AddReason.SPAWN);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean shouldPickupByInteraction(@Nullable BlockType blockType) {
/* 829 */     return (blockType != null && blockType.getGathering() != null && blockType.getGathering().isHarvestable());
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
/*     */   public static void performPickupByInteraction(@Nonnull Ref<EntityStore> ref, @Nonnull Vector3i targetBlock, @Nonnull BlockType blockType, int filler, @Nonnull Ref<ChunkStore> chunkReference, @Nonnull ComponentAccessor<EntityStore> entityStore, @Nonnull ComponentAccessor<ChunkStore> chunkStore) {
/* 850 */     WorldChunk worldChunkComponent = (WorldChunk)chunkStore.getComponent(chunkReference, WorldChunk.getComponentType());
/* 851 */     assert worldChunkComponent != null;
/*     */     
/* 853 */     BlockChunk blockChunkComponent = (BlockChunk)chunkStore.getComponent(chunkReference, BlockChunk.getComponentType());
/* 854 */     assert blockChunkComponent != null;
/*     */     
/* 856 */     Vector3i affectedBlock = targetBlock;
/*     */     
/* 858 */     if (!blockType.isUnknown()) {
/* 859 */       int fillerX = FillerBlockUtil.unpackX(filler);
/* 860 */       int fillerY = FillerBlockUtil.unpackY(filler);
/* 861 */       int fillerZ = FillerBlockUtil.unpackZ(filler);
/*     */       
/* 863 */       if (fillerX != 0 || fillerY != 0 || fillerZ != 0) {
/*     */         
/* 865 */         affectedBlock = affectedBlock.clone().subtract(fillerX, fillerY, fillerZ);
/*     */         
/* 867 */         String oldBlockTypeKey = blockType.getId();
/* 868 */         blockType = worldChunkComponent.getBlockType(affectedBlock.getX(), affectedBlock.getY(), affectedBlock.getZ());
/* 869 */         if (blockType == null)
/*     */           return; 
/* 871 */         if (!oldBlockTypeKey.equals(blockType.getId())) {
/* 872 */           worldChunkComponent.breakBlock(targetBlock.getX(), targetBlock.getY(), targetBlock.getZ());
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     } 
/* 878 */     BlockSection section = blockChunkComponent.getSectionAtBlockY(targetBlock.getY());
/* 879 */     Vector3d centerPosition = new Vector3d();
/* 880 */     int rotationIndex = section.getRotationIndex(targetBlock.getX(), targetBlock.getY(), targetBlock.getZ());
/* 881 */     blockType.getBlockCenter(rotationIndex, centerPosition);
/* 882 */     centerPosition.add(targetBlock);
/*     */     
/* 884 */     int setBlockSettings = 0;
/* 885 */     setBlockSettings |= 0x100;
/*     */     
/* 887 */     if (!BlockInteractionUtils.isNaturalAction(ref, entityStore)) {
/* 888 */       setBlockSettings |= 0x800;
/*     */     }
/*     */     
/* 891 */     removeBlock(affectedBlock, blockType, setBlockSettings, chunkReference, chunkStore);
/*     */     
/* 893 */     HarvestingDropType harvest = blockType.getGathering().getHarvest();
/* 894 */     String itemId = harvest.getItemId();
/* 895 */     String dropListId = harvest.getDropListId();
/* 896 */     for (ItemStack itemStack : getDrops(blockType, 1, itemId, dropListId)) {
/* 897 */       ItemUtils.interactivelyPickupItem(ref, itemStack, centerPosition, entityStore);
/*     */     }
/*     */ 
/*     */     
/* 901 */     if ((setBlockSettings & 0x400) == 0) {
/* 902 */       BlockSoundSet soundSet = (BlockSoundSet)BlockSoundSet.getAssetMap().getAsset(blockType.getBlockSoundSetIndex());
/* 903 */       if (soundSet != null) {
/* 904 */         int soundEventIndex = soundSet.getSoundEventIndices().getOrDefault(BlockSoundEvent.Harvest, 0);
/* 905 */         if (soundEventIndex != 0) {
/* 906 */           SoundUtil.playSoundEvent3d(soundEventIndex, SoundCategory.SFX, centerPosition, entityStore);
/*     */         }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static void removeBlock(@Nonnull Vector3i blockPosition, @Nonnull BlockType blockType, int setBlockSettings, @Nonnull Ref<ChunkStore> chunkReference, @Nonnull ComponentAccessor<ChunkStore> chunkStore) {
/* 926 */     World world = ((ChunkStore)chunkStore.getExternalData()).getWorld();
/*     */     
/* 928 */     ComponentType<ChunkStore, BlockHealthChunk> blockHealthComponentType = BlockHealthModule.get().getBlockHealthChunkComponentType();
/* 929 */     BlockHealthChunk blockHealthComponent = (BlockHealthChunk)chunkStore.getComponent(chunkReference, blockHealthComponentType);
/* 930 */     assert blockHealthComponent != null;
/*     */     
/* 932 */     blockHealthComponent.removeBlock(world, blockPosition);
/*     */     
/* 934 */     WorldChunk worldChunkComponent = (WorldChunk)chunkStore.getComponent(chunkReference, WorldChunk.getComponentType());
/* 935 */     assert worldChunkComponent != null;
/*     */     
/* 937 */     BlockChunk blockChunkComponent = (BlockChunk)chunkStore.getComponent(chunkReference, BlockChunk.getComponentType());
/* 938 */     assert blockChunkComponent != null;
/*     */     
/* 940 */     worldChunkComponent.breakBlock(blockPosition.getX(), blockPosition.getY(), blockPosition.getZ(), setBlockSettings);
/*     */     
/* 942 */     if ((setBlockSettings & 0x100) != 0) {
/* 943 */       BlockSection section = blockChunkComponent.getSectionAtBlockY(blockPosition.y);
/* 944 */       int rotationIndex = section.getRotationIndex(blockPosition.x, blockPosition.y, blockPosition.z);
/* 945 */       BlockBoundingBoxes hitBoxType = (BlockBoundingBoxes)BlockBoundingBoxes.getAssetMap().getAsset(blockType.getHitboxTypeIndex());
/* 946 */       if (hitBoxType != null) {
/* 947 */         FillerBlockUtil.forEachFillerBlock(hitBoxType.get(rotationIndex), (x, y, z) -> world.performBlockUpdate(blockPosition.getX() + x, blockPosition.getY() + y, blockPosition.getZ() + z, false));
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 952 */     ConnectedBlocksUtil.setConnectedBlockAndNotifyNeighbors(
/* 953 */         BlockType.getAssetMap().getIndex("Empty"), RotationTuple.NONE, Vector3i.ZERO, blockPosition, worldChunkComponent, blockChunkComponent);
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
/*     */   @Nonnull
/*     */   public static List<ItemStack> getDrops(@Nonnull BlockType blockType, int quantity, @Nullable String itemId, @Nullable String dropListId) {
/* 972 */     if (dropListId == null && itemId == null) {
/* 973 */       Item item = blockType.getItem();
/* 974 */       if (item == null) return (List<ItemStack>)ObjectLists.emptyList(); 
/* 975 */       return (List<ItemStack>)ObjectLists.singleton(new ItemStack(item.getId(), quantity));
/*     */     } 
/*     */     
/* 978 */     ObjectArrayList<ItemStack> objectArrayList = new ObjectArrayList();
/*     */     
/* 980 */     if (dropListId != null) {
/* 981 */       ItemModule itemModule = ItemModule.get();
/*     */       
/* 983 */       if (itemModule.isEnabled()) {
/* 984 */         for (int i = 0; i < quantity; i++) {
/* 985 */           List<ItemStack> randomItemsToDrop = itemModule.getRandomItemDrops(dropListId);
/* 986 */           objectArrayList.addAll(randomItemsToDrop);
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 991 */     if (itemId != null) {
/* 992 */       objectArrayList.add(new ItemStack(itemId, quantity));
/*     */     }
/*     */     
/* 995 */     return (List<ItemStack>)objectArrayList;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\BlockHarvestUtils.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */