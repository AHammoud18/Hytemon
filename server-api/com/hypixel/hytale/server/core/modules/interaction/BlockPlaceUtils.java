/*     */ package com.hypixel.hytale.server.core.modules.interaction;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.protocol.BlockRotation;
/*     */ import com.hypixel.hytale.protocol.GameMode;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.asset.type.blockhitbox.BlockBoundingBoxes;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockGathering;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockPlacementSettings;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.Rotation;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.RotationTuple;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.SoftBlockDropType;
/*     */ import com.hypixel.hytale.server.core.asset.type.buildertool.config.PrefabListAsset;
/*     */ import com.hypixel.hytale.server.core.asset.type.environment.config.Environment;
/*     */ import com.hypixel.hytale.server.core.asset.type.gameplay.WorldConfig;
/*     */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.event.events.ecs.PlaceBlockEvent;
/*     */ import com.hypixel.hytale.server.core.inventory.Inventory;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
/*     */ import com.hypixel.hytale.server.core.inventory.transaction.ItemStackSlotTransaction;
/*     */ import com.hypixel.hytale.server.core.inventory.transaction.ItemStackTransaction;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.components.PlacedByInteractionComponent;
/*     */ import com.hypixel.hytale.server.core.prefab.selection.buffer.PrefabBufferUtil;
/*     */ import com.hypixel.hytale.server.core.prefab.selection.buffer.impl.IPrefabBuffer;
/*     */ import com.hypixel.hytale.server.core.prefab.selection.buffer.impl.PrefabBuffer;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockComponentChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.ChunkColumn;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.BlockSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.meta.BlockState;
/*     */ import com.hypixel.hytale.server.core.universe.world.meta.state.PlacedByBlockState;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.core.util.FillerBlockUtil;
/*     */ import com.hypixel.hytale.server.core.util.PrefabUtil;
/*     */ import com.hypixel.hytale.sneakythrow.SneakyThrow;
/*     */ import java.nio.file.Path;
/*     */ import java.util.Random;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ import org.bson.BsonDocument;
/*     */ import org.bson.BsonValue;
/*     */ 
/*     */ public class BlockPlaceUtils {
/*     */   @Nonnull
/*  58 */   private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */   
/*     */   @Nonnull
/*  61 */   private static final Message MESSAGE_MODULES_INTERACTION_FAILED_ADD_BACK_AFTER_FAILED_PLACE = Message.translation("server.modules.interaction.failedAddBackAfterFailedPlace");
/*     */   @Nonnull
/*  63 */   private static final Message MESSAGE_MODULES_INTERACTION_FAILED_CHECK_BLOCK = Message.translation("server.modules.interaction.failedCheckBlock");
/*     */   @Nonnull
/*  65 */   private static final Message MESSAGE_MODULES_INTERACTION_FAILED_CHECK_EMPTY = Message.translation("server.modules.interaction.failedCheckEmpty");
/*     */   @Nonnull
/*  67 */   private static final Message MESSAGE_MODULES_INTERACTION_FAILED_CHECK_UNKNOWN = Message.translation("server.modules.interaction.failedCheckUnknown");
/*     */   @Nonnull
/*  69 */   private static final Message MESSAGE_MODULES_INTERACTION_FAILED_CHECK = Message.translation("server.modules.interaction.failedCheck");
/*     */   @Nonnull
/*  71 */   private static final Message MESSAGE_MODULES_INTERACTION_BUILD_FORBIDDEN = Message.translation("server.modules.interaction.buildForbidden");
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
/*     */   public static void placeBlock(@Nonnull Ref<EntityStore> ref, @Nonnull ItemStack itemStack, @Nullable String blockTypeKey, @Nonnull ItemContainer itemContainer, @Nonnull Vector3i placementNormal, @Nonnull Vector3i blockPosition, @Nonnull BlockRotation blockRotation, @Nullable Inventory inventory, byte activeSlot, boolean removeItemInHand, @Nonnull Ref<ChunkStore> chunkReference, @Nonnull ComponentAccessor<ChunkStore> chunkStore, @Nonnull ComponentAccessor<EntityStore> entityStore) {
/* 105 */     if (blockPosition.getY() < 0 || blockPosition.getY() >= 320) {
/*     */       return;
/*     */     }
/*     */     
/* 109 */     Ref<ChunkStore> targetChunkReference = chunkReference;
/* 110 */     Vector3i targetBlockPosition = blockPosition;
/* 111 */     RotationTuple targetRotation = RotationTuple.of(
/* 112 */         Rotation.valueOf(blockRotation.rotationYaw), 
/* 113 */         Rotation.valueOf(blockRotation.rotationPitch), 
/* 114 */         Rotation.valueOf(blockRotation.rotationRoll));
/*     */     
/* 116 */     BlockChunk targetBlockChunkComponent = (BlockChunk)chunkStore.getComponent(chunkReference, BlockChunk.getComponentType());
/* 117 */     assert targetBlockChunkComponent != null;
/* 118 */     BlockSection targetBlockSection = targetBlockChunkComponent.getSectionAtBlockY(blockPosition.getY());
/*     */ 
/*     */ 
/*     */     
/* 122 */     PlaceBlockEvent event = new PlaceBlockEvent(itemStack, blockPosition, targetRotation);
/* 123 */     entityStore.invoke(ref, (EcsEvent)event);
/*     */ 
/*     */     
/* 126 */     if (event.isCancelled()) {
/* 127 */       targetBlockSection.invalidateBlock(blockPosition.getX(), blockPosition.getY(), blockPosition.getZ());
/*     */       
/*     */       return;
/*     */     } 
/* 131 */     targetBlockPosition = event.getTargetBlock();
/* 132 */     targetRotation = event.getRotation();
/*     */     
/* 134 */     boolean positionIsDifferent = !ChunkUtil.isSameChunk(targetBlockPosition.x, targetBlockPosition.z, blockPosition.x, blockPosition.z);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 140 */     if (positionIsDifferent) {
/* 141 */       long chunkIndex = ChunkUtil.indexChunkFromBlock(targetBlockPosition.x, targetBlockPosition.z);
/* 142 */       targetChunkReference = ((ChunkStore)chunkStore.getExternalData()).getChunkReference(chunkIndex);
/*     */ 
/*     */       
/* 145 */       if (targetChunkReference == null || !targetChunkReference.isValid()) {
/*     */         return;
/*     */       }
/*     */     } 
/* 149 */     if (positionIsDifferent) {
/* 150 */       targetBlockSection.invalidateBlock(blockPosition.getX(), blockPosition.getY(), blockPosition.getZ());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 155 */     if (!targetChunkReference.equals(chunkReference) || targetBlockPosition.y != blockPosition.y) {
/* 156 */       targetBlockChunkComponent = (BlockChunk)chunkStore.getComponent(targetChunkReference, BlockChunk.getComponentType());
/* 157 */       assert targetBlockChunkComponent != null;
/* 158 */       targetBlockSection = targetBlockChunkComponent.getSectionAtBlockY(targetBlockPosition.getY());
/*     */     } 
/*     */     
/* 161 */     PlayerRef playerRefComponent = (PlayerRef)entityStore.getComponent(ref, PlayerRef.getComponentType());
/* 162 */     Player playerComponent = (Player)entityStore.getComponent(ref, Player.getComponentType());
/*     */ 
/*     */     
/* 165 */     boolean isAdventureMode = (playerComponent == null || playerComponent.getGameMode() == GameMode.Adventure);
/* 166 */     if (isAdventureMode && removeItemInHand) {
/*     */ 
/*     */       
/* 169 */       ItemStackSlotTransaction transaction = itemContainer.removeItemStackFromSlot((short)activeSlot, itemStack, 1);
/* 170 */       if (!transaction.succeeded()) {
/* 171 */         if (playerRefComponent != null) {
/* 172 */           playerRefComponent.sendMessage(MESSAGE_MODULES_INTERACTION_FAILED_CHECK);
/*     */         }
/*     */         
/*     */         return;
/*     */       } 
/* 177 */       itemStack = transaction.getOutput();
/*     */     } 
/*     */ 
/*     */     
/* 181 */     if (blockTypeKey == null && itemStack != null)
/*     */     {
/*     */ 
/*     */       
/* 185 */       blockTypeKey = itemStack.getBlockKey();
/*     */     }
/*     */ 
/*     */     
/* 189 */     if (!validateBlockToPlace(blockTypeKey, playerRefComponent))
/* 190 */       return;  assert blockTypeKey != null;
/*     */     
/* 192 */     BlockType blockTypeAsset = (BlockType)BlockType.getAssetMap().getAsset(blockTypeKey);
/* 193 */     if (blockTypeAsset != null) {
/* 194 */       String prefabListAssetId = blockTypeAsset.getPrefabListAssetId();
/* 195 */       if (prefabListAssetId != null && 
/* 196 */         !validateAndPlacePrefab(blockPosition, prefabListAssetId, playerRefComponent, entityStore)) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 202 */     WorldChunk worldChunkComponent = (WorldChunk)chunkStore.getComponent(targetChunkReference, WorldChunk.getComponentType());
/* 203 */     if (worldChunkComponent == null) {
/*     */       return;
/*     */     }
/* 206 */     boolean success = tryPlaceBlock(ref, placementNormal, targetBlockPosition, blockTypeKey, targetRotation, worldChunkComponent, targetBlockChunkComponent, chunkReference, chunkStore, entityStore);
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
/* 217 */     if (success) {
/* 218 */       onPlaceBlockSuccess(itemStack, worldChunkComponent, targetBlockPosition);
/*     */     } else {
/* 220 */       onPlaceBlockFailure(itemStack, inventory, activeSlot, playerComponent, targetBlockSection, targetBlockPosition);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void onPlaceBlockFailure(@Nullable ItemStack itemStack, @Nullable Inventory inventory, byte activeSlot, @Nullable Player playerComponent, @Nonnull BlockSection blockSection, @Nonnull Vector3i blockPosition) {
/* 242 */     boolean isAdventure = (playerComponent == null || playerComponent.getGameMode() == GameMode.Adventure);
/* 243 */     if (inventory != null && itemStack != null && isAdventure) {
/* 244 */       ItemContainer hotbar = inventory.getHotbar();
/*     */       
/* 246 */       ItemStackSlotTransaction transaction = hotbar.addItemStackToSlot((short)activeSlot, itemStack);
/* 247 */       if (!transaction.succeeded()) {
/* 248 */         ItemStackTransaction itemStackTransaction = hotbar.addItemStack(itemStack);
/* 249 */         if (!itemStackTransaction.succeeded() && 
/* 250 */           playerComponent != null) {
/* 251 */           playerComponent.sendMessage(MESSAGE_MODULES_INTERACTION_FAILED_ADD_BACK_AFTER_FAILED_PLACE);
/*     */         }
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/*     */     
/* 259 */     blockSection.invalidateBlock(blockPosition.getX(), blockPosition.getY(), blockPosition.getZ());
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
/*     */   private static void onPlaceBlockSuccess(@Nullable ItemStack itemStack, @Nonnull WorldChunk worldChunkComponent, @Nonnull Vector3i blockPosition) {
/* 274 */     if (itemStack == null)
/*     */       return; 
/* 276 */     BsonDocument metadata = itemStack.getMetadata();
/* 277 */     if (metadata == null)
/*     */       return; 
/* 279 */     BsonValue bsonValue = metadata.get("BlockState");
/* 280 */     if (bsonValue == null)
/*     */       return; 
/*     */     try {
/* 283 */       BsonDocument document = bsonValue.asDocument();
/* 284 */       BlockState blockState = BlockState.load(document, worldChunkComponent, blockPosition.clone());
/*     */       
/* 286 */       if (blockState != null) {
/* 287 */         worldChunkComponent.setState(blockPosition.getX(), blockPosition.getY(), blockPosition.getZ(), blockState);
/*     */       } else {
/* 289 */         LOGGER.at(Level.WARNING).log("Failed to set BlockState from item metadata: %s, %s", itemStack.getItemId(), document);
/*     */       } 
/* 291 */     } catch (Exception e) {
/* 292 */       throw SneakyThrow.sneakyThrow(e);
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
/*     */   private static boolean validateBlockToPlace(@Nullable String blockTypeKey, @Nullable PlayerRef playerRefComponent) {
/* 307 */     if (blockTypeKey == null) {
/* 308 */       if (playerRefComponent != null) {
/* 309 */         playerRefComponent.sendMessage(MESSAGE_MODULES_INTERACTION_FAILED_CHECK_BLOCK);
/*     */       }
/* 311 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 315 */     if (blockTypeKey.equals("Empty")) {
/* 316 */       if (playerRefComponent != null) {
/* 317 */         playerRefComponent.sendMessage(MESSAGE_MODULES_INTERACTION_FAILED_CHECK_EMPTY);
/*     */       }
/* 319 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 323 */     if (blockTypeKey.equals("Unknown")) {
/* 324 */       if (playerRefComponent != null) {
/* 325 */         playerRefComponent.sendMessage(MESSAGE_MODULES_INTERACTION_FAILED_CHECK_UNKNOWN);
/*     */       }
/* 327 */       return false;
/*     */     } 
/* 329 */     return true;
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
/*     */   private static boolean validateAndPlacePrefab(@Nonnull Vector3i blockPosition, @Nonnull String prefabListAssetId, @Nullable PlayerRef playerRefComponent, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 346 */     PrefabListAsset prefabListAsset = (PrefabListAsset)PrefabListAsset.getAssetMap().getAsset(prefabListAssetId);
/* 347 */     if (prefabListAsset == null) {
/* 348 */       if (playerRefComponent != null) {
/* 349 */         playerRefComponent.sendMessage(Message.translation("server.modules.interaction.placeBlock.prefabListNotFound")
/* 350 */             .param("name", prefabListAssetId));
/*     */       }
/* 352 */       return false;
/*     */     } 
/*     */     
/* 355 */     Path randomPrefab = prefabListAsset.getRandomPrefab();
/* 356 */     if (randomPrefab == null) {
/* 357 */       if (playerRefComponent != null) {
/* 358 */         playerRefComponent.sendMessage(Message.translation("server.modules.interaction.placeBlock.prefabListEmpty")
/* 359 */             .param("name", prefabListAssetId));
/*     */       }
/* 361 */       return false;
/*     */     } 
/*     */     
/* 364 */     if (!Files.exists(randomPrefab, new java.nio.file.LinkOption[0])) {
/* 365 */       if (playerRefComponent != null) {
/* 366 */         playerRefComponent.sendMessage(Message.translation("server.commands.editprefab.prefabNotFound")
/* 367 */             .param("name", randomPrefab.toString()));
/*     */       }
/* 369 */       return false;
/*     */     } 
/*     */     
/* 372 */     World world = ((EntityStore)componentAccessor.getExternalData()).getWorld();
/* 373 */     PrefabBuffer prefabBuffer = PrefabBufferUtil.loadBuffer(randomPrefab);
/* 374 */     world.execute(() -> {
/*     */           Store<EntityStore> store = world.getEntityStore().getStore();
/*     */           
/*     */           PrefabBuffer.PrefabBufferAccessor prefabBufferAccessor = prefabBuffer.newAccess();
/*     */           PrefabUtil.paste((IPrefabBuffer)prefabBufferAccessor, world, blockPosition, Rotation.None, true, new Random(), (ComponentAccessor)store);
/*     */           prefabBufferAccessor.release();
/*     */         });
/* 381 */     return true;
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
/*     */   private static boolean tryPlaceBlock(@Nonnull Ref<EntityStore> ref, @Nonnull Vector3i placementNormal, @Nonnull Vector3i blockPosition, @Nonnull String blockTypeKey, @Nonnull RotationTuple rotation, @Nonnull WorldChunk worldChunkComponent, @Nonnull BlockChunk blockChunkComponent, @Nonnull Ref<ChunkStore> chunkReference, @Nonnull ComponentAccessor<ChunkStore> chunkStore, @Nonnull ComponentAccessor<EntityStore> entityStore) {
/* 408 */     WorldConfig worldConfig = ((EntityStore)entityStore.getExternalData()).getWorld().getGameplayConfig().getWorldConfig();
/* 409 */     if (!worldConfig.isBlockPlacementAllowed()) {
/* 410 */       return false;
/*     */     }
/*     */     
/* 413 */     Player playerComponent = (Player)entityStore.getComponent(ref, Player.getComponentType());
/* 414 */     PlayerRef playerRefComponent = (PlayerRef)entityStore.getComponent(ref, PlayerRef.getComponentType());
/*     */     
/* 416 */     boolean isAdventure = (playerComponent == null || playerComponent.getGameMode() == GameMode.Adventure);
/* 417 */     if (isAdventure) {
/* 418 */       int environmentId = blockChunkComponent.getEnvironment(blockPosition);
/* 419 */       Environment environment = (Environment)Environment.getAssetMap().getAsset(environmentId);
/* 420 */       if (environment != null && !environment.isBlockModificationAllowed()) {
/* 421 */         if (playerRefComponent != null) {
/* 422 */           playerRefComponent.sendMessage(MESSAGE_MODULES_INTERACTION_BUILD_FORBIDDEN);
/*     */         }
/* 424 */         return false;
/*     */       } 
/*     */     } 
/*     */     
/* 428 */     BlockType blockType = (BlockType)BlockType.getAssetMap().getAsset(blockTypeKey);
/* 429 */     int rotationIndex = rotation.index();
/*     */     
/* 431 */     if (blockType == null || !worldChunkComponent.testPlaceBlock(blockPosition.getX(), blockPosition.getY(), blockPosition.getZ(), blockType, rotationIndex)) {
/* 432 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 436 */     BlockBoundingBoxes hitBoxType = (BlockBoundingBoxes)BlockBoundingBoxes.getAssetMap().getAsset(blockType.getHitboxTypeIndex());
/* 437 */     if (hitBoxType != null) {
/* 438 */       FillerBlockUtil.forEachFillerBlock(hitBoxType.get(rotationIndex), (x1, y1, z1) -> breakAndDropReplacedBlock(blockPosition.clone().add(x1, y1, z1), worldChunkComponent, chunkReference, ref, chunkStore, entityStore));
/*     */     } else {
/*     */       
/* 441 */       breakAndDropReplacedBlock(blockPosition, worldChunkComponent, chunkReference, ref, chunkStore, entityStore);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 446 */     int placeBlockSettings = 10;
/* 447 */     if (!worldChunkComponent.placeBlock(blockPosition.getX(), blockPosition.getY(), blockPosition.getZ(), blockTypeKey, rotation, 10, false)) {
/* 448 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 452 */     if (playerComponent != null && !playerComponent.isOverrideBlockPlacementRestrictions() && blockType.canBePlacedAsDeco()) {
/* 453 */       ChunkColumn chunkColumnComponent = (ChunkColumn)chunkStore.getComponent(chunkReference, ChunkColumn.getComponentType());
/* 454 */       assert chunkColumnComponent != null;
/*     */       
/* 456 */       Ref<ChunkStore> sectionRef = chunkColumnComponent.getSection(ChunkUtil.chunkCoordinate(blockPosition.y));
/* 457 */       if (sectionRef != null && sectionRef.isValid()) {
/* 458 */         BlockPhysics.markDeco(chunkStore, sectionRef, blockPosition.x, blockPosition.y, blockPosition.z);
/*     */       }
/*     */     } 
/*     */     
/* 462 */     BlockState blockState = worldChunkComponent.getState(blockPosition.x, blockPosition.y, blockPosition.z);
/* 463 */     if (blockState instanceof PlacedByBlockState) { PlacedByBlockState placedByBlockState = (PlacedByBlockState)blockState;
/* 464 */       placedByBlockState.placedBy(ref, blockTypeKey, blockState, entityStore); }
/*     */ 
/*     */     
/* 467 */     int blockIndexInChunk = ChunkUtil.indexBlockInColumn(blockPosition.x, blockPosition.y, blockPosition.z);
/* 468 */     BlockComponentChunk blockComponentChunk = worldChunkComponent.getBlockComponentChunk();
/*     */ 
/*     */     
/* 471 */     Ref<ChunkStore> blockRef = (blockComponentChunk == null) ? null : blockComponentChunk.getEntityReference(blockIndexInChunk);
/* 472 */     if (blockRef != null) {
/* 473 */       UUIDComponent uuidComponent = (UUIDComponent)entityStore.getComponent(ref, UUIDComponent.getComponentType());
/* 474 */       assert uuidComponent != null;
/*     */       
/* 476 */       PlacedByInteractionComponent placedByInteractionComponent = new PlacedByInteractionComponent(uuidComponent.getUuid());
/* 477 */       chunkStore.putComponent(blockRef, PlacedByInteractionComponent.getComponentType(), (Component)placedByInteractionComponent);
/*     */     } 
/*     */     
/* 480 */     ConnectedBlocksUtil.setConnectedBlockAndNotifyNeighbors(
/* 481 */         BlockType.getAssetMap().getIndex(blockTypeKey), rotation, placementNormal, blockPosition, worldChunkComponent, blockChunkComponent);
/*     */ 
/*     */ 
/*     */     
/* 485 */     return true;
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
/*     */   private static void breakAndDropReplacedBlock(@Nonnull Vector3i blockPosition, @Nonnull WorldChunk worldChunkComponent, @Nonnull Ref<ChunkStore> chunkReference, @Nonnull Ref<EntityStore> ref, @Nonnull ComponentAccessor<ChunkStore> chunkStore, @Nonnull ComponentAccessor<EntityStore> entityStore) {
/* 505 */     BlockType existingBlock = worldChunkComponent.getBlockType(blockPosition);
/*     */     
/* 507 */     if (existingBlock != null) {
/*     */       
/* 509 */       if (existingBlock.getMaterial() != BlockMaterial.Empty) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 514 */       BlockGathering gathering = existingBlock.getGathering();
/*     */       
/* 516 */       int dropQuantity = 1;
/* 517 */       String itemId = null;
/* 518 */       String dropListId = null;
/*     */       
/* 520 */       if (gathering != null) {
/* 521 */         SoftBlockDropType softGathering = gathering.getSoft();
/*     */         
/* 523 */         if (softGathering != null) {
/* 524 */           itemId = softGathering.getItemId();
/* 525 */           dropListId = softGathering.getDropListId();
/*     */         } 
/*     */       } 
/*     */       
/* 529 */       int setBlockSettings = 288;
/* 530 */       BlockHarvestUtils.performBlockBreak(((ChunkStore)chunkStore.getExternalData()).getWorld(), blockPosition, existingBlock, null, dropQuantity, itemId, dropListId, 288, ref, chunkReference, entityStore, chunkStore);
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
/*     */   public static boolean canPlaceBlock(@Nonnull BlockType blockType, @Nonnull String placedBlockTypeKey) {
/* 543 */     if (blockType.getId().equals(placedBlockTypeKey)) return true;
/*     */     
/* 545 */     BlockPlacementSettings placementSettings = blockType.getPlacementSettings();
/* 546 */     if (placementSettings == null) return false;
/*     */     
/* 548 */     return (placedBlockTypeKey.equals(placementSettings.getWallPlacementOverrideBlockId()) || placedBlockTypeKey
/* 549 */       .equals(placementSettings.getFloorPlacementOverrideBlockId()) || placedBlockTypeKey
/* 550 */       .equals(placementSettings.getCeilingPlacementOverrideBlockId()));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\BlockPlaceUtils.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */