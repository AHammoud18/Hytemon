/*     */ package com.hypixel.hytale.builtin.adventure.farming.states;
/*     */ import com.hypixel.fastutil.shorts.Short2ObjectConcurrentHashMap;
/*     */ import com.hypixel.hytale.builtin.adventure.farming.FarmingPlugin;
/*     */ import com.hypixel.hytale.builtin.adventure.farming.component.CoopResidentComponent;
/*     */ import com.hypixel.hytale.builtin.adventure.farming.config.FarmingCoopAsset;
/*     */ import com.hypixel.hytale.builtin.tagset.TagSetPlugin;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.math.range.IntRange;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.ItemDrop;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.ItemDropList;
/*     */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*     */ import com.hypixel.hytale.server.core.entity.reference.PersistentRef;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
/*     */ import com.hypixel.hytale.server.core.modules.entity.damage.DeathComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.item.ItemComponent;
/*     */ import com.hypixel.hytale.server.core.modules.time.WorldTimeResource;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.Builder;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import com.hypixel.hytale.server.npc.metadata.CapturedNPCMetadata;
/*     */ import com.hypixel.hytale.server.npc.role.Role;
/*     */ import com.hypixel.hytale.server.spawning.ISpawnableWithModel;
/*     */ import com.hypixel.hytale.server.spawning.SpawningContext;
/*     */ import it.unimi.dsi.fastutil.Pair;
/*     */ import java.time.Duration;
/*     */ import java.time.Instant;
/*     */ import java.time.LocalDateTime;
/*     */ import java.time.temporal.ChronoUnit;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.ThreadLocalRandom;
/*     */ import java.util.function.Supplier;
/*     */ import org.checkerframework.checker.nullness.compatqual.NullableDecl;
/*     */ 
/*     */ public class CoopBlock implements Component<ChunkStore> {
/*     */   public static ComponentType<ChunkStore, CoopBlock> getComponentType() {
/*  56 */     return FarmingPlugin.get().getCoopBlockStateComponentType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String STATE_PRODUCE = "Produce_Ready";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final BuilderCodec<CoopBlock> CODEC;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  79 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(CoopBlock.class, CoopBlock::new).append(new KeyedCodec("FarmingCoopId", (Codec)Codec.STRING, true), (coop, s) -> coop.coopAssetId = s, coop -> coop.coopAssetId).add()).append(new KeyedCodec("Residents", (Codec)new ArrayCodec((Codec)CoopResident.CODEC, x$0 -> new CoopResident[x$0])), (coop, residents) -> coop.residents = new ArrayList<>(Arrays.asList(residents)), coop -> (CoopResident[])coop.residents.toArray(())).add()).append(new KeyedCodec("Storage", (Codec)ItemContainer.CODEC), (coop, storage) -> coop.itemContainer = storage, coop -> coop.itemContainer).add()).build();
/*     */   }
/*  81 */   HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */   
/*     */   protected String coopAssetId;
/*  84 */   protected List<CoopResident> residents = new ArrayList<>();
/*  85 */   protected ItemContainer itemContainer = (ItemContainer)EmptyItemContainer.INSTANCE;
/*     */   
/*     */   public CoopBlock() {
/*  88 */     ArrayList<ItemStack> remainder = new ArrayList<>();
/*  89 */     this.itemContainer = ItemContainer.ensureContainerCapacity(this.itemContainer, (short)5, com.hypixel.hytale.server.core.inventory.container.SimpleItemContainer::new, remainder);
/*     */   }
/*     */   
/*     */   @NullableDecl
/*     */   public FarmingCoopAsset getCoopAsset() {
/*  94 */     return (FarmingCoopAsset)FarmingCoopAsset.getAssetMap().getAsset(this.coopAssetId);
/*     */   }
/*     */   
/*     */   public CoopBlock(String farmingCoopId, List<CoopResident> residents, ItemContainer itemContainer) {
/*  98 */     this.coopAssetId = farmingCoopId;
/*  99 */     this.residents.addAll(residents);
/* 100 */     this.itemContainer = itemContainer.clone();
/*     */     
/* 102 */     ArrayList<ItemStack> remainder = new ArrayList<>();
/* 103 */     this.itemContainer = ItemContainer.ensureContainerCapacity(this.itemContainer, (short)5, com.hypixel.hytale.server.core.inventory.container.SimpleItemContainer::new, remainder);
/*     */   }
/*     */   
/*     */   public boolean tryPutResident(CapturedNPCMetadata metadata, WorldTimeResource worldTimeResource) {
/* 107 */     FarmingCoopAsset coopAsset = getCoopAsset();
/* 108 */     if (coopAsset == null) {
/* 109 */       return false;
/*     */     }
/*     */     
/* 112 */     if (this.residents.size() >= coopAsset.getMaxResidents()) {
/* 113 */       return false;
/*     */     }
/*     */     
/* 116 */     if (!getCoopAcceptsNPCGroup(metadata.getRoleIndex())) {
/* 117 */       return false;
/*     */     }
/*     */     
/* 120 */     this.residents.add(new CoopResident(metadata, null, worldTimeResource.getGameTime()));
/* 121 */     return true;
/*     */   }
/*     */   
/*     */   public boolean tryPutWildResidentFromWild(Store<EntityStore> store, Ref<EntityStore> entityRef, WorldTimeResource worldTimeResource, Vector3i coopLocation) {
/* 125 */     FarmingCoopAsset coopAsset = getCoopAsset();
/* 126 */     if (coopAsset == null) {
/* 127 */       return false;
/*     */     }
/*     */     
/* 130 */     NPCEntity npcComponent = (NPCEntity)store.getComponent(entityRef, NPCEntity.getComponentType());
/* 131 */     if (npcComponent == null) {
/* 132 */       return false;
/*     */     }
/*     */     
/* 135 */     CoopResidentComponent coopResidentComponent = (CoopResidentComponent)store.getComponent(entityRef, CoopResidentComponent.getComponentType());
/* 136 */     if (coopResidentComponent != null) {
/* 137 */       return false;
/*     */     }
/*     */     
/* 140 */     if (!getCoopAcceptsNPCGroup(npcComponent.getRoleIndex())) {
/* 141 */       return false;
/*     */     }
/*     */     
/* 144 */     if (this.residents.size() >= coopAsset.getMaxResidents()) {
/* 145 */       return false;
/*     */     }
/*     */     
/* 148 */     coopResidentComponent = (CoopResidentComponent)store.ensureAndGetComponent(entityRef, CoopResidentComponent.getComponentType());
/* 149 */     coopResidentComponent.setCoopLocation(coopLocation);
/*     */     
/* 151 */     UUIDComponent uuidComponent = (UUIDComponent)store.getComponent(entityRef, UUIDComponent.getComponentType());
/* 152 */     if (uuidComponent == null) {
/* 153 */       return false;
/*     */     }
/*     */     
/* 156 */     PersistentRef persistentRef = new PersistentRef();
/* 157 */     persistentRef.setEntity(entityRef, uuidComponent.getUuid());
/*     */     
/* 159 */     CapturedNPCMetadata metadata = FarmingUtil.generateCapturedNPCMetadata((ComponentAccessor)store, entityRef, npcComponent.getRoleIndex());
/* 160 */     CoopResident residentRecord = new CoopResident(metadata, persistentRef, worldTimeResource.getGameTime());
/* 161 */     residentRecord.deployedToWorld = true;
/* 162 */     this.residents.add(residentRecord);
/*     */     
/* 164 */     return true;
/*     */   }
/*     */   
/*     */   public boolean getCoopAcceptsNPCGroup(int npcRoleIndex) {
/* 168 */     TagSetPlugin.TagSetLookup tagSetPlugin = TagSetPlugin.get(NPCGroup.class);
/* 169 */     FarmingCoopAsset coopAsset = getCoopAsset();
/* 170 */     if (coopAsset == null) {
/* 171 */       return false;
/*     */     }
/*     */     
/* 174 */     int[] acceptedNpcGroupIndexes = coopAsset.getAcceptedNpcGroupIndexes();
/* 175 */     if (acceptedNpcGroupIndexes == null) {
/* 176 */       return true;
/*     */     }
/*     */     
/* 179 */     for (int group : acceptedNpcGroupIndexes) {
/* 180 */       if (tagSetPlugin.tagInSet(group, npcRoleIndex)) {
/* 181 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 185 */     return false;
/*     */   }
/*     */   
/*     */   public void generateProduceToInventory(WorldTimeResource worldTimeResource) {
/* 189 */     Instant currentTime = worldTimeResource.getGameTime();
/*     */     
/* 191 */     FarmingCoopAsset coopAsset = getCoopAsset();
/* 192 */     if (coopAsset == null) {
/*     */       return;
/*     */     }
/*     */     
/* 196 */     Map<String, String> produceDropsMap = coopAsset.getProduceDrops();
/* 197 */     if (produceDropsMap.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/* 201 */     ThreadLocalRandom random = ThreadLocalRandom.current();
/*     */ 
/*     */     
/* 204 */     List<ItemStack> generatedItemDrops = new ArrayList<>();
/* 205 */     for (CoopResident resident : this.residents) {
/* 206 */       Instant lastProduced = resident.getLastProduced();
/*     */       
/* 208 */       if (lastProduced == null) {
/* 209 */         resident.setLastProduced(currentTime);
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 214 */       CapturedNPCMetadata residentMeta = resident.getMetadata();
/* 215 */       int npcRoleIndex = residentMeta.getRoleIndex();
/* 216 */       String npcName = NPCPlugin.get().getName(npcRoleIndex);
/* 217 */       String npcDropListName = produceDropsMap.get(npcName);
/* 218 */       if (npcDropListName == null) {
/*     */         continue;
/*     */       }
/*     */       
/* 222 */       ItemDropList dropListAsset = (ItemDropList)ItemDropList.getAssetMap().getAsset(npcDropListName);
/* 223 */       if (dropListAsset == null) {
/*     */         continue;
/*     */       }
/*     */       
/* 227 */       Duration harvestDiff = Duration.between(lastProduced, currentTime);
/* 228 */       long hoursSinceLastHarvest = harvestDiff.toHours();
/* 229 */       int produceCount = MathUtil.ceil(((float)hoursSinceLastHarvest / WorldTimeResource.HOURS_PER_DAY));
/*     */ 
/*     */       
/* 232 */       List<ItemDrop> configuredItemDrops = new ArrayList<>();
/* 233 */       for (int i = 0; i < produceCount; i++) {
/* 234 */         Objects.requireNonNull(random); dropListAsset.getContainer().populateDrops(configuredItemDrops, random::nextDouble, npcDropListName);
/*     */         
/* 236 */         for (ItemDrop drop : configuredItemDrops) {
/* 237 */           if (drop == null || drop.getItemId() == null) {
/* 238 */             ((HytaleLogger.Api)HytaleLogger.forEnclosingClass().atWarning()).log("Tried to create ItemDrop for non-existent item in drop list id '%s'", npcDropListName);
/*     */             
/*     */             continue;
/*     */           } 
/* 242 */           int amount = drop.getRandomQuantity(random);
/* 243 */           if (amount > 0) {
/* 244 */             generatedItemDrops.add(new ItemStack(drop.getItemId(), amount, drop.getMetadata()));
/*     */           }
/*     */         } 
/* 247 */         configuredItemDrops.clear();
/*     */       } 
/*     */       
/* 250 */       resident.setLastProduced(currentTime);
/*     */     } 
/*     */     
/* 253 */     this.itemContainer.addItemStacks(generatedItemDrops);
/*     */   }
/*     */   
/*     */   public void gatherProduceFromInventory(ItemContainer playerInventory) {
/* 257 */     for (ItemStack item : this.itemContainer.removeAllItemStacks()) {
/* 258 */       playerInventory.addItemStack(item);
/*     */     }
/*     */   }
/*     */   
/*     */   public void ensureSpawnResidentsInWorld(World world, Store<EntityStore> store, Vector3d coopLocation, Vector3d spawnOffset) {
/* 263 */     NPCPlugin npcModule = NPCPlugin.get();
/*     */     
/* 265 */     FarmingCoopAsset coopAsset = getCoopAsset();
/* 266 */     if (coopAsset == null) {
/*     */       return;
/*     */     }
/*     */     
/* 270 */     float radiansPerSpawn = 6.2831855F / coopAsset.getMaxResidents();
/* 271 */     Vector3d spawnOffsetIteration = spawnOffset;
/*     */     
/* 273 */     SpawningContext spawningContext = new SpawningContext();
/*     */     
/* 275 */     for (CoopResident resident : this.residents) {
/* 276 */       CapturedNPCMetadata residentMeta = resident.getMetadata();
/* 277 */       int npcRoleIndex = residentMeta.getRoleIndex();
/*     */       
/* 279 */       boolean residentDeployed = resident.getDeployedToWorld();
/* 280 */       PersistentRef residentEntityId = resident.getPersistentRef();
/*     */       
/* 282 */       if (residentDeployed || residentEntityId != null) {
/*     */         continue;
/*     */       }
/*     */       
/* 286 */       Vector3d residentSpawnLocation = (new Vector3d()).assign(coopLocation).add(spawnOffsetIteration);
/* 287 */       Builder<Role> roleBuilder = NPCPlugin.get().tryGetCachedValidRole(npcRoleIndex);
/* 288 */       if (roleBuilder == null) {
/*     */         continue;
/*     */       }
/* 291 */       spawningContext.setSpawnable((ISpawnableWithModel)roleBuilder);
/* 292 */       if (!spawningContext.set(world, residentSpawnLocation.x, residentSpawnLocation.y, residentSpawnLocation.z) || spawningContext
/* 293 */         .canSpawn() != SpawnTestResult.TEST_OK) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */       
/* 298 */       Pair<Ref<EntityStore>, NPCEntity> npcPair = npcModule.spawnEntity(store, npcRoleIndex, spawningContext.newPosition(), Vector3f.ZERO, null, null);
/* 299 */       if (npcPair == null) {
/* 300 */         resident.setPersistentRef(null);
/* 301 */         resident.setDeployedToWorld(false);
/*     */         
/*     */         continue;
/*     */       } 
/* 305 */       Ref<EntityStore> npcRef = (Ref<EntityStore>)npcPair.first();
/* 306 */       NPCEntity npcComponent = (NPCEntity)npcPair.second();
/*     */       
/* 308 */       npcComponent.getLeashPoint().assign(coopLocation);
/*     */       
/* 310 */       if (npcRef == null || !npcRef.isValid()) {
/* 311 */         resident.setPersistentRef(null);
/* 312 */         resident.setDeployedToWorld(false);
/*     */         
/*     */         continue;
/*     */       } 
/* 316 */       UUIDComponent uuidComponent = (UUIDComponent)store.getComponent(npcRef, UUIDComponent.getComponentType());
/* 317 */       if (uuidComponent == null) {
/* 318 */         resident.setPersistentRef(null);
/* 319 */         resident.setDeployedToWorld(false);
/*     */         
/*     */         continue;
/*     */       } 
/* 323 */       CoopResidentComponent coopResidentComponent = new CoopResidentComponent();
/* 324 */       coopResidentComponent.setCoopLocation(coopLocation.toVector3i());
/* 325 */       store.addComponent(npcRef, CoopResidentComponent.getComponentType(), (Component)coopResidentComponent);
/*     */       
/* 327 */       PersistentRef persistentRef = new PersistentRef();
/* 328 */       persistentRef.setEntity(npcRef, uuidComponent.getUuid());
/*     */       
/* 330 */       resident.setPersistentRef(persistentRef);
/* 331 */       resident.setDeployedToWorld(true);
/*     */       
/* 333 */       spawnOffsetIteration = spawnOffsetIteration.rotateY(radiansPerSpawn);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void ensureNoResidentsInWorld(Store<EntityStore> store) {
/* 338 */     ArrayList<CoopResident> residentsToRemove = new ArrayList<>();
/*     */     
/* 340 */     for (CoopResident resident : this.residents) {
/* 341 */       boolean deployed = resident.getDeployedToWorld();
/* 342 */       PersistentRef entityUuid = resident.getPersistentRef();
/* 343 */       if (!deployed && entityUuid == null) {
/*     */         continue;
/*     */       }
/*     */       
/* 347 */       Ref<EntityStore> entityRef = entityUuid.getEntity((ComponentAccessor)store);
/* 348 */       if (entityRef == null) {
/* 349 */         residentsToRemove.add(resident);
/*     */         
/*     */         continue;
/*     */       } 
/* 353 */       CoopResidentComponent coopResidentComponent = (CoopResidentComponent)store.getComponent(entityRef, CoopResidentComponent.getComponentType());
/* 354 */       if (coopResidentComponent == null) {
/* 355 */         residentsToRemove.add(resident);
/*     */         
/*     */         continue;
/*     */       } 
/* 359 */       DeathComponent deathComponent = (DeathComponent)store.getComponent(entityRef, DeathComponent.getComponentType());
/* 360 */       if (deathComponent != null) {
/* 361 */         residentsToRemove.add(resident);
/*     */         
/*     */         continue;
/*     */       } 
/* 365 */       coopResidentComponent.setMarkedForDespawn(true);
/* 366 */       resident.setPersistentRef(null);
/* 367 */       resident.setDeployedToWorld(false);
/*     */     } 
/*     */     
/* 370 */     for (CoopResident resident : residentsToRemove) {
/* 371 */       this.residents.remove(resident);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean shouldResidentsBeInCoop(WorldTimeResource worldTimeResource) {
/* 376 */     FarmingCoopAsset coopAsset = getCoopAsset();
/* 377 */     if (coopAsset == null) {
/* 378 */       return true;
/*     */     }
/*     */     
/* 381 */     IntRange roamTimeRange = coopAsset.getResidentRoamTime();
/* 382 */     if (roamTimeRange == null) {
/* 383 */       return true;
/*     */     }
/*     */     
/* 386 */     int gameHour = worldTimeResource.getCurrentHour();
/* 387 */     return !roamTimeRange.includes(gameHour);
/*     */   }
/*     */   
/*     */   @NullableDecl
/*     */   public Instant getNextScheduledTick(WorldTimeResource worldTimeResource) {
/* 392 */     Instant gameTime = worldTimeResource.getGameTime();
/* 393 */     LocalDateTime gameDateTime = worldTimeResource.getGameDateTime();
/* 394 */     int gameHour = worldTimeResource.getCurrentHour();
/* 395 */     int minutes = gameDateTime.getMinute();
/*     */     
/* 397 */     FarmingCoopAsset coopAsset = getCoopAsset();
/* 398 */     if (coopAsset == null) {
/* 399 */       return null;
/*     */     }
/*     */     
/* 402 */     IntRange roamTimeRange = coopAsset.getResidentRoamTime();
/* 403 */     if (roamTimeRange == null) {
/* 404 */       return null;
/*     */     }
/*     */     
/* 407 */     int nextScheduledHour = 0;
/* 408 */     int minTime = roamTimeRange.getInclusiveMin();
/* 409 */     int maxTime = roamTimeRange.getInclusiveMax();
/*     */     
/* 411 */     if (coopAsset.getResidentRoamTime().includes(gameHour)) {
/* 412 */       nextScheduledHour = coopAsset.getResidentRoamTime().getInclusiveMax() + 1 - gameHour;
/*     */     }
/* 414 */     else if (gameHour > maxTime) {
/* 415 */       nextScheduledHour = WorldTimeResource.HOURS_PER_DAY - gameHour + minTime;
/*     */     } else {
/* 417 */       nextScheduledHour = minTime - gameHour;
/*     */     } 
/*     */ 
/*     */     
/* 421 */     return gameTime.plus(nextScheduledHour * 60L - minutes, ChronoUnit.MINUTES);
/*     */   }
/*     */   
/*     */   public void handleResidentDespawn(UUID entityUuid) {
/* 425 */     CoopResident removedResident = null;
/*     */     
/* 427 */     for (CoopResident resident : this.residents) {
/* 428 */       if (resident.persistentRef == null) {
/*     */         continue;
/*     */       }
/*     */       
/* 432 */       if (resident.persistentRef.getUuid() == entityUuid) {
/* 433 */         removedResident = resident;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 438 */     if (removedResident == null) {
/*     */       return;
/*     */     }
/*     */     
/* 442 */     this.residents.remove(removedResident);
/*     */   }
/*     */   
/*     */   public void handleBlockBroken(World world, WorldTimeResource worldTimeResource, Store<EntityStore> store, int blockX, int blockY, int blockZ) {
/* 446 */     Vector3i location = new Vector3i(blockX, blockY, blockZ);
/* 447 */     world.execute(() -> ensureSpawnResidentsInWorld(world, store, location.toVector3d(), (new Vector3d()).assign(Vector3d.FORWARD)));
/*     */     
/* 449 */     generateProduceToInventory(worldTimeResource);
/*     */     
/* 451 */     Vector3d dropPosition = new Vector3d((blockX + 0.5F), blockY, (blockZ + 0.5F));
/*     */     
/* 453 */     Holder[] arrayOfHolder = ItemComponent.generateItemDrops((ComponentAccessor)store, this.itemContainer.removeAllItemStacks(), dropPosition, Vector3f.ZERO);
/* 454 */     if (arrayOfHolder.length > 0) {
/* 455 */       world.execute(() -> store.addEntities(itemEntityHolders, AddReason.SPAWN));
/*     */     }
/*     */     
/* 458 */     world.execute(() -> {
/*     */           for (CoopResident resident : this.residents) {
/*     */             PersistentRef persistentRef = resident.getPersistentRef();
/*     */             if (persistentRef == null) {
/*     */               continue;
/*     */             }
/*     */             Ref<EntityStore> ref = persistentRef.getEntity((ComponentAccessor)store);
/*     */             if (ref == null) {
/*     */               return;
/*     */             }
/*     */             store.tryRemoveComponent(ref, CoopResidentComponent.getComponentType());
/*     */           } 
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasProduce() {
/* 476 */     return !this.itemContainer.isEmpty();
/*     */   }
/*     */ 
/*     */   
/*     */   public Component<ChunkStore> clone() {
/* 481 */     return new CoopBlock(this.coopAssetId, this.residents, this.itemContainer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class CoopResident
/*     */   {
/*     */     public static final BuilderCodec<CoopResident> CODEC;
/*     */ 
/*     */ 
/*     */     
/*     */     protected CapturedNPCMetadata metadata;
/*     */ 
/*     */ 
/*     */     
/*     */     @NullableDecl
/*     */     protected PersistentRef persistentRef;
/*     */ 
/*     */ 
/*     */     
/*     */     protected boolean deployedToWorld;
/*     */ 
/*     */     
/*     */     protected Instant lastProduced;
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/* 510 */       CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(CoopResident.class, CoopResident::new).append(new KeyedCodec("Metadata", (Codec)CapturedNPCMetadata.CODEC), (coop, meta) -> coop.metadata = meta, coop -> coop.metadata).add()).append(new KeyedCodec("PersistentRef", (Codec)PersistentRef.CODEC), (coop, persistentRef) -> coop.persistentRef = persistentRef, coop -> coop.persistentRef).add()).append(new KeyedCodec("DeployedToWorld", (Codec)Codec.BOOLEAN), (coop, deployedToWorld) -> coop.deployedToWorld = deployedToWorld.booleanValue(), coop -> Boolean.valueOf(coop.deployedToWorld)).add()).append(new KeyedCodec("LastHarvested", (Codec)Codec.INSTANT), (coop, instant) -> coop.lastProduced = instant, coop -> coop.lastProduced).add()).build();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public CoopResident() {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public CoopResident(CapturedNPCMetadata metadata, PersistentRef persistentRef, Instant lastProduced) {
/* 523 */       this.metadata = metadata;
/* 524 */       this.persistentRef = persistentRef;
/* 525 */       this.lastProduced = lastProduced;
/*     */     }
/*     */     
/*     */     public CapturedNPCMetadata getMetadata() {
/* 529 */       return this.metadata;
/*     */     }
/*     */     
/*     */     @NullableDecl
/*     */     public PersistentRef getPersistentRef() {
/* 534 */       return this.persistentRef;
/*     */     }
/*     */     
/*     */     public void setPersistentRef(@NullableDecl PersistentRef persistentRef) {
/* 538 */       this.persistentRef = persistentRef;
/*     */     }
/*     */     public boolean getDeployedToWorld() {
/* 541 */       return this.deployedToWorld;
/*     */     } public void setDeployedToWorld(boolean deployedToWorld) {
/* 543 */       this.deployedToWorld = deployedToWorld;
/*     */     }
/*     */     public Instant getLastProduced() {
/* 546 */       return this.lastProduced;
/*     */     }
/*     */     
/*     */     public void setLastProduced(Instant lastProduced) {
/* 550 */       this.lastProduced = lastProduced;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\farming\states\CoopBlock.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */