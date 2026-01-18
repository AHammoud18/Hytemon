/*     */ package com.hypixel.hytale.builtin.adventure.farming;
/*     */ import com.hypixel.hytale.builtin.adventure.farming.states.FarmingBlock;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.protocol.Rangef;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.HarvestingDropType;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.farming.FarmingData;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.farming.FarmingStageData;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.farming.GrowthModifierAsset;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.ModelAsset;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.PersistentModel;
/*     */ import com.hypixel.hytale.server.core.modules.time.WorldTimeResource;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockComponentChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.BlockSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.ChunkSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.metadata.CapturedNPCMetadata;
/*     */ import java.time.Instant;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class FarmingUtil {
/*     */   private static final int MAX_SECONDS_BETWEEN_TICKS = 15;
/*     */   
/*     */   public static void tickFarming(CommandBuffer<ChunkStore> commandBuffer, BlockSection blockSection, Ref<ChunkStore> sectionRef, Ref<ChunkStore> blockRef, FarmingBlock farmingBlock, int x, int y, int z, boolean initialTick) {
/*  36 */     World world = ((ChunkStore)commandBuffer.getExternalData()).getWorld();
/*  37 */     WorldTimeResource worldTimeResource = (WorldTimeResource)world.getEntityStore().getStore().getResource(WorldTimeResource.getResourceType());
/*  38 */     Instant currentTime = worldTimeResource.getGameTime();
/*     */ 
/*     */ 
/*     */     
/*  42 */     BlockType blockType = (farmingBlock.getPreviousBlockType() != null) ? (BlockType)BlockType.getAssetMap().getAsset(farmingBlock.getPreviousBlockType()) : (BlockType)BlockType.getAssetMap().getAsset(blockSection.get(x, y, z));
/*  43 */     if (blockType == null)
/*  44 */       return;  if (blockType.getFarming() == null)
/*     */       return; 
/*  46 */     FarmingData farmingConfig = blockType.getFarming();
/*  47 */     if (farmingConfig.getStages() == null)
/*     */       return; 
/*  49 */     float currentProgress = farmingBlock.getGrowthProgress();
/*  50 */     int currentStage = (int)currentProgress;
/*  51 */     String currentStageSet = farmingBlock.getCurrentStageSet();
/*     */     
/*  53 */     FarmingStageData[] stages = (FarmingStageData[])farmingConfig.getStages().get(currentStageSet);
/*  54 */     if (stages == null)
/*     */       return; 
/*  56 */     if (currentStage < 0) {
/*  57 */       currentStage = 0;
/*  58 */       currentProgress = 0.0F;
/*  59 */       farmingBlock.setGrowthProgress(0.0F);
/*     */     } 
/*     */     
/*  62 */     if (currentStage >= stages.length) {
/*     */       
/*  64 */       commandBuffer.removeEntity(blockRef, RemoveReason.REMOVE);
/*     */       
/*     */       return;
/*     */     } 
/*  68 */     long remainingTimeSeconds = currentTime.getEpochSecond() - farmingBlock.getLastTickGameTime().getEpochSecond();
/*     */     
/*  70 */     ChunkSection section = (ChunkSection)commandBuffer.getComponent(sectionRef, ChunkSection.getComponentType());
/*     */     
/*  72 */     int worldX = ChunkUtil.worldCoordFromLocalCoord(section.getX(), x);
/*  73 */     int worldY = ChunkUtil.worldCoordFromLocalCoord(section.getY(), y);
/*  74 */     int worldZ = ChunkUtil.worldCoordFromLocalCoord(section.getZ(), z);
/*     */     
/*  76 */     while (currentStage < stages.length) {
/*  77 */       FarmingStageData stage = stages[currentStage];
/*     */       
/*  79 */       if (stage.shouldStop((ComponentAccessor)commandBuffer, sectionRef, blockRef, x, y, z)) {
/*     */         
/*  81 */         farmingBlock.setGrowthProgress(stages.length);
/*     */         
/*  83 */         commandBuffer.removeEntity(blockRef, RemoveReason.REMOVE);
/*     */         
/*     */         break;
/*     */       } 
/*     */       
/*  88 */       Rangef range = stage.getDuration();
/*  89 */       if (range == null) {
/*     */         
/*  91 */         commandBuffer.removeEntity(blockRef, RemoveReason.REMOVE);
/*     */         
/*     */         break;
/*     */       } 
/*  95 */       double rand = HashUtil.random(farmingBlock.getGeneration(), worldX, worldY, worldZ);
/*     */       
/*  97 */       double baseDuration = range.min + (range.max - range.min) * rand;
/*  98 */       long remainingDurationSeconds = Math.round(baseDuration * (1.0D - currentProgress % 1.0D));
/*     */ 
/*     */       
/* 101 */       double growthMultiplier = 1.0D;
/* 102 */       if (farmingConfig.getGrowthModifiers() != null) {
/* 103 */         for (String modifierName : farmingConfig.getGrowthModifiers()) {
/* 104 */           GrowthModifierAsset modifier = (GrowthModifierAsset)GrowthModifierAsset.getAssetMap().getAsset(modifierName);
/* 105 */           if (modifier != null) {
/* 106 */             growthMultiplier *= modifier.getCurrentGrowthMultiplier(commandBuffer, sectionRef, blockRef, x, y, z, initialTick);
/*     */           }
/*     */         } 
/*     */       }
/*     */       
/* 111 */       remainingDurationSeconds = Math.round(remainingDurationSeconds / growthMultiplier);
/*     */       
/* 113 */       if (remainingTimeSeconds < remainingDurationSeconds) {
/*     */         
/* 115 */         currentProgress += (float)(remainingTimeSeconds / baseDuration / growthMultiplier);
/* 116 */         farmingBlock.setGrowthProgress(currentProgress);
/* 117 */         long nextGrowthInNanos = (remainingDurationSeconds - remainingTimeSeconds) * 1000000000L;
/* 118 */         long randCap = (long)((15.0D + 10.0D * HashUtil.random(farmingBlock.getGeneration() ^ 0xCAFEBEEFL, worldX, worldY, worldZ)) * world.getTps() * WorldTimeResource.getSecondsPerTick(world) * 1.0E9D);
/* 119 */         long cappedNextGrowthInNanos = Math.min(nextGrowthInNanos, randCap);
/* 120 */         blockSection.scheduleTick(ChunkUtil.indexBlock(x, y, z), currentTime.plusNanos(cappedNextGrowthInNanos));
/*     */         
/*     */         break;
/*     */       } 
/* 124 */       remainingTimeSeconds -= remainingDurationSeconds;
/* 125 */       currentStage++;
/* 126 */       currentProgress = currentStage;
/* 127 */       farmingBlock.setGrowthProgress(currentProgress);
/*     */ 
/*     */       
/* 130 */       farmingBlock.setGeneration(farmingBlock.getGeneration() + 1);
/* 131 */       if (currentStage >= stages.length) {
/* 132 */         if (stages[currentStage - 1].implementsShouldStop()) {
/* 133 */           currentStage = stages.length - 1;
/* 134 */           farmingBlock.setGrowthProgress(currentStage);
/* 135 */           stages[currentStage].apply((ComponentAccessor)commandBuffer, sectionRef, blockRef, x, y, z, stages[currentStage]); continue;
/*     */         } 
/* 137 */         farmingBlock.setGrowthProgress(stages.length);
/*     */         
/* 139 */         commandBuffer.removeEntity(blockRef, RemoveReason.REMOVE);
/*     */         
/*     */         continue;
/*     */       } 
/* 143 */       farmingBlock.setExecutions(0);
/* 144 */       stages[currentStage].apply((ComponentAccessor)commandBuffer, sectionRef, blockRef, x, y, z, stages[currentStage - 1]);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 149 */     farmingBlock.setLastTickGameTime(currentTime);
/*     */   }
/*     */   private static final int BETWEEN_RANDOM = 10;
/*     */   
/*     */   public static void harvest(@Nonnull World world, @Nonnull ComponentAccessor<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull BlockType blockType, int rotationIndex, @Nonnull Vector3i blockPosition) {
/* 154 */     if (world.getGameplayConfig().getWorldConfig().isBlockGatheringAllowed()) {
/* 155 */       harvest0(store, ref, blockType, rotationIndex, blockPosition);
/*     */     }
/*     */   }
/*     */   
/*     */   @NullableDecl
/*     */   public static CapturedNPCMetadata generateCapturedNPCMetadata(ComponentAccessor<EntityStore> componentAccessor, Ref<EntityStore> entityRef, int roleIndex) {
/* 161 */     PersistentModel persistentModel = (PersistentModel)componentAccessor.getComponent(entityRef, PersistentModel.getComponentType());
/* 162 */     if (persistentModel == null) {
/* 163 */       return null;
/*     */     }
/* 165 */     ModelAsset modelAsset = (ModelAsset)ModelAsset.getAssetMap().getAsset(persistentModel.getModelReference().getModelAssetId());
/* 166 */     CapturedNPCMetadata meta = new CapturedNPCMetadata();
/*     */     
/* 168 */     if (modelAsset != null)
/* 169 */       meta.setIconPath(modelAsset.getIcon()); 
/* 170 */     meta.setRoleIndex(roleIndex);
/*     */     
/* 172 */     return meta;
/*     */   }
/*     */   protected static boolean harvest0(@Nonnull ComponentAccessor<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull BlockType blockType, int rotationIndex, @Nonnull Vector3i blockPosition) {
/*     */     FarmingBlock farmingBlock;
/* 176 */     FarmingData farmingConfig = blockType.getFarming();
/* 177 */     if (farmingConfig == null || farmingConfig.getStages() == null) {
/* 178 */       return false;
/*     */     }
/*     */     
/* 181 */     if (blockType.getGathering().getHarvest() == null) {
/* 182 */       return false;
/*     */     }
/*     */     
/* 185 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*     */     
/* 187 */     Vector3d centerPosition = new Vector3d();
/* 188 */     blockType.getBlockCenter(rotationIndex, centerPosition);
/* 189 */     centerPosition.add(blockPosition);
/*     */     
/* 191 */     if (farmingConfig.getStageSetAfterHarvest() == null) {
/*     */ 
/*     */       
/* 194 */       giveDrops(store, ref, centerPosition, blockType);
/*     */       
/* 196 */       WorldChunk chunk = world.getChunkIfInMemory(ChunkUtil.indexChunkFromBlock(blockPosition.x, blockPosition.z));
/* 197 */       if (chunk != null) {
/* 198 */         chunk.breakBlock(blockPosition.x, blockPosition.y, blockPosition.z);
/*     */       }
/* 200 */       return true;
/*     */     } 
/*     */     
/* 203 */     giveDrops(store, ref, centerPosition, blockType);
/*     */     
/* 205 */     Map<String, FarmingStageData[]> stageSets = farmingConfig.getStages();
/* 206 */     FarmingStageData[] stages = stageSets.get(farmingConfig.getStartingStageSet());
/* 207 */     if (stages == null) {
/* 208 */       return false;
/*     */     }
/* 210 */     int currentStageIndex = stages.length - 1;
/* 211 */     FarmingStageData previousStage = stages[currentStageIndex];
/*     */ 
/*     */     
/* 214 */     String newStageSet = farmingConfig.getStageSetAfterHarvest();
/* 215 */     FarmingStageData[] newStages = stageSets.get(newStageSet);
/* 216 */     if (newStages == null || newStages.length == 0) {
/* 217 */       WorldChunk chunk = world.getChunkIfInMemory(ChunkUtil.indexChunkFromBlock(blockPosition.x, blockPosition.z));
/* 218 */       if (chunk != null) {
/* 219 */         chunk.breakBlock(blockPosition.x, blockPosition.y, blockPosition.z);
/*     */       }
/* 221 */       return false;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 226 */     Store<ChunkStore> chunkStore = world.getChunkStore().getStore();
/* 227 */     Ref<ChunkStore> chunkRef = world.getChunkStore().getChunkReference(ChunkUtil.indexChunkFromBlock(blockPosition.x, blockPosition.z));
/* 228 */     if (chunkRef == null) return false; 
/* 229 */     BlockComponentChunk blockComponentChunk = (BlockComponentChunk)chunkStore.getComponent(chunkRef, BlockComponentChunk.getComponentType());
/* 230 */     if (blockComponentChunk == null) return false;
/*     */ 
/*     */     
/* 233 */     Instant now = ((WorldTimeResource)((EntityStore)store.getExternalData()).getWorld().getEntityStore().getStore().getResource(WorldTimeResource.getResourceType())).getGameTime();
/*     */     
/* 235 */     int blockIndexColumn = ChunkUtil.indexBlockInColumn(blockPosition.x, blockPosition.y, blockPosition.z);
/* 236 */     Ref<ChunkStore> blockRef = blockComponentChunk.getEntityReference(blockIndexColumn);
/* 237 */     if (blockRef == null) {
/* 238 */       Holder<ChunkStore> blockEntity = ChunkStore.REGISTRY.newHolder();
/* 239 */       blockEntity.putComponent(BlockModule.BlockStateInfo.getComponentType(), (Component)new BlockModule.BlockStateInfo(blockIndexColumn, chunkRef));
/* 240 */       farmingBlock = new FarmingBlock();
/*     */       
/* 242 */       farmingBlock.setLastTickGameTime(now);
/* 243 */       farmingBlock.setCurrentStageSet(newStageSet);
/* 244 */       blockEntity.addComponent(FarmingBlock.getComponentType(), (Component)farmingBlock);
/* 245 */       blockRef = chunkStore.addEntity(blockEntity, AddReason.SPAWN);
/*     */     } else {
/* 247 */       farmingBlock = (FarmingBlock)chunkStore.ensureAndGetComponent(blockRef, FarmingBlock.getComponentType());
/*     */     } 
/*     */     
/* 250 */     farmingBlock.setCurrentStageSet(newStageSet);
/* 251 */     farmingBlock.setGrowthProgress(0.0F);
/* 252 */     farmingBlock.setExecutions(0);
/* 253 */     farmingBlock.setGeneration(farmingBlock.getGeneration() + 1);
/*     */     
/* 255 */     farmingBlock.setLastTickGameTime(now);
/*     */     
/* 257 */     Ref<ChunkStore> sectionRef = world.getChunkStore().getChunkSectionReference(ChunkUtil.chunkCoordinate(blockPosition.x), ChunkUtil.chunkCoordinate(blockPosition.y), ChunkUtil.chunkCoordinate(blockPosition.z));
/* 258 */     if (sectionRef == null) {
/* 259 */       return false;
/*     */     }
/*     */     
/* 262 */     if (blockRef == null) {
/* 263 */       return false;
/*     */     }
/*     */     
/* 266 */     BlockSection section = (BlockSection)chunkStore.getComponent(sectionRef, BlockSection.getComponentType());
/* 267 */     if (section != null) {
/* 268 */       section.scheduleTick(ChunkUtil.indexBlock(blockPosition.x, blockPosition.y, blockPosition.z), now);
/*     */     }
/*     */     
/* 271 */     newStages[0].apply((ComponentAccessor)chunkStore, sectionRef, blockRef, blockPosition.x, blockPosition.y, blockPosition.z, previousStage);
/* 272 */     return true;
/*     */   }
/*     */   
/*     */   protected static void giveDrops(@Nonnull ComponentAccessor<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull Vector3d origin, @Nonnull BlockType blockType) {
/* 276 */     HarvestingDropType harvest = blockType.getGathering().getHarvest();
/* 277 */     String itemId = harvest.getItemId();
/* 278 */     String dropListId = harvest.getDropListId();
/* 279 */     BlockHarvestUtils.getDrops(blockType, 1, itemId, dropListId).forEach(itemStack -> ItemUtils.interactivelyPickupItem(ref, itemStack, origin, store));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\farming\FarmingUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */