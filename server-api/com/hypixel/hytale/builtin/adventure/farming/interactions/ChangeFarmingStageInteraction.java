/*     */ package com.hypixel.hytale.builtin.adventure.farming.interactions;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.adventure.farming.states.FarmingBlock;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.protocol.InteractionState;
/*     */ import com.hypixel.hytale.protocol.InteractionType;
/*     */ import com.hypixel.hytale.protocol.WaitForDataFrom;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.farming.FarmingData;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.farming.FarmingStageData;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.modules.block.BlockModule;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.client.SimpleBlockInteraction;
/*     */ import com.hypixel.hytale.server.core.modules.time.WorldTimeResource;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockComponentChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.BlockSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.time.Instant;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChangeFarmingStageInteraction
/*     */   extends SimpleBlockInteraction
/*     */ {
/*  48 */   private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
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
/*     */   public static final BuilderCodec<ChangeFarmingStageInteraction> CODEC;
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
/*     */   static {
/*  85 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ChangeFarmingStageInteraction.class, ChangeFarmingStageInteraction::new, SimpleBlockInteraction.CODEC).documentation("Changes the farming stage of the target block.")).appendInherited(new KeyedCodec("Stage", (Codec)Codec.INTEGER), (interaction, stage) -> interaction.targetStage = stage.intValue(), interaction -> Integer.valueOf(interaction.targetStage), (o, p) -> o.targetStage = p.targetStage).documentation("The stage index to set (0, 1, 2, etc.). Use -1 for the final stage. Ignored if Increase is set.").add()).appendInherited(new KeyedCodec("Increase", (Codec)Codec.INTEGER), (interaction, increase) -> interaction.increaseBy = increase, interaction -> interaction.increaseBy, (o, p) -> o.increaseBy = p.increaseBy).documentation("Add this amount to the current stage (e.g., 1 = advance one stage, 2 = advance two stages). Takes priority over Decrease and Stage.").add()).appendInherited(new KeyedCodec("Decrease", (Codec)Codec.INTEGER), (interaction, decrease) -> interaction.decreaseBy = decrease, interaction -> interaction.decreaseBy, (o, p) -> o.decreaseBy = p.decreaseBy).documentation("Subtract this amount from the current stage (e.g., 1 = go back one stage). Takes priority over Stage.").add()).appendInherited(new KeyedCodec("StageSet", (Codec)Codec.STRING), (interaction, stageSet) -> interaction.targetStageSet = stageSet, interaction -> interaction.targetStageSet, (o, p) -> o.targetStageSet = p.targetStageSet).documentation("Optional. The stage set to switch to (e.g., 'Default', 'Harvested'). If not provided, uses current stage set.").add()).build();
/*     */   }
/*  87 */   protected int targetStage = -1;
/*     */   @Nullable
/*  89 */   protected Integer increaseBy = null;
/*     */   
/*     */   @Nullable
/*  92 */   protected Integer decreaseBy = null;
/*     */   
/*     */   @Nullable
/*  95 */   protected String targetStageSet = null;
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public WaitForDataFrom getWaitForDataFrom() {
/* 101 */     return WaitForDataFrom.Server;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void interactWithBlock(@Nonnull World world, @Nonnull CommandBuffer<EntityStore> commandBuffer, @Nonnull InteractionType type, @Nonnull InteractionContext context, @Nullable ItemStack itemInHand, @Nonnull Vector3i targetBlock, @Nonnull CooldownHandler cooldownHandler) {
/*     */     FarmingBlock farmingBlock;
/* 109 */     int stageIndex, x = targetBlock.getX();
/* 110 */     int y = targetBlock.getY();
/* 111 */     int z = targetBlock.getZ();
/*     */     
/* 113 */     ((HytaleLogger.Api)LOGGER.atInfo()).log("[ChangeFarmingStage] Starting interaction at pos=(%d, %d, %d), increaseBy=%s, decreaseBy=%s, targetStage=%d, targetStageSet=%s", 
/* 114 */         Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(z), this.increaseBy, this.decreaseBy, Integer.valueOf(this.targetStage), this.targetStageSet);
/*     */     
/* 116 */     WorldChunk worldChunk = world.getChunk(ChunkUtil.indexChunkFromBlock(x, z));
/* 117 */     if (worldChunk == null) {
/* 118 */       ((HytaleLogger.Api)LOGGER.atWarning()).log("[ChangeFarmingStage] FAILED: worldChunk is null at pos=(%d, %d, %d)", Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(z));
/* 119 */       (context.getState()).state = InteractionState.Failed;
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 124 */     BlockType blockType = worldChunk.getBlockType(targetBlock);
/* 125 */     if (blockType == null) {
/* 126 */       ((HytaleLogger.Api)LOGGER.atWarning()).log("[ChangeFarmingStage] FAILED: blockType is null at pos=(%d, %d, %d)", Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(z));
/* 127 */       (context.getState()).state = InteractionState.Failed;
/*     */       
/*     */       return;
/*     */     } 
/* 131 */     ((HytaleLogger.Api)LOGGER.atInfo()).log("[ChangeFarmingStage] Block type: %s (id=%s)", blockType.getId(), blockType.getClass().getSimpleName());
/*     */     
/* 133 */     FarmingData farmingConfig = blockType.getFarming();
/* 134 */     if (farmingConfig == null || farmingConfig.getStages() == null) {
/* 135 */       ((HytaleLogger.Api)LOGGER.atWarning()).log("[ChangeFarmingStage] FAILED: farmingConfig is null or has no stages. blockType=%s, hasFarmingConfig=%s", blockType
/* 136 */           .getId(), (farmingConfig != null));
/* 137 */       (context.getState()).state = InteractionState.Failed;
/*     */       
/*     */       return;
/*     */     } 
/* 141 */     ((HytaleLogger.Api)LOGGER.atInfo()).log("[ChangeFarmingStage] Farming config found. StartingStageSet=%s, StageSetAfterHarvest=%s, AvailableStageSets=%s", farmingConfig
/* 142 */         .getStartingStageSet(), farmingConfig.getStageSetAfterHarvest(), 
/* 143 */         (farmingConfig.getStages() != null) ? farmingConfig.getStages().keySet() : "null");
/*     */     
/* 145 */     Store<ChunkStore> chunkStore = world.getChunkStore().getStore();
/*     */ 
/*     */     
/* 148 */     WorldTimeResource worldTimeResource = (WorldTimeResource)world.getEntityStore().getStore().getResource(WorldTimeResource.getResourceType());
/* 149 */     Instant now = worldTimeResource.getGameTime();
/*     */ 
/*     */     
/* 152 */     Ref<ChunkStore> chunkRef = world.getChunkStore().getChunkReference(ChunkUtil.indexChunkFromBlock(x, z));
/* 153 */     if (chunkRef == null) {
/* 154 */       ((HytaleLogger.Api)LOGGER.atWarning()).log("[ChangeFarmingStage] FAILED: chunkRef is null at pos=(%d, %d, %d)", Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(z));
/* 155 */       (context.getState()).state = InteractionState.Failed;
/*     */       
/*     */       return;
/*     */     } 
/* 159 */     BlockComponentChunk blockComponentChunk = (BlockComponentChunk)chunkStore.getComponent(chunkRef, BlockComponentChunk.getComponentType());
/* 160 */     if (blockComponentChunk == null) {
/* 161 */       ((HytaleLogger.Api)LOGGER.atWarning()).log("[ChangeFarmingStage] FAILED: blockComponentChunk is null at pos=(%d, %d, %d)", Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(z));
/* 162 */       (context.getState()).state = InteractionState.Failed;
/*     */       
/*     */       return;
/*     */     } 
/* 166 */     int blockIndexColumn = ChunkUtil.indexBlockInColumn(x, y, z);
/*     */ 
/*     */     
/* 169 */     Ref<ChunkStore> blockRef = blockComponentChunk.getEntityReference(blockIndexColumn);
/* 170 */     boolean hadExistingBlockRef = (blockRef != null);
/* 171 */     ((HytaleLogger.Api)LOGGER.atInfo()).log("[ChangeFarmingStage] Initial blockRef from getEntityReference: %s", hadExistingBlockRef ? "exists" : "null");
/*     */ 
/*     */     
/* 174 */     String initialStageSetLookup = (this.targetStageSet != null) ? this.targetStageSet : farmingConfig.getStartingStageSet();
/* 175 */     FarmingStageData[] stages = (FarmingStageData[])farmingConfig.getStages().get(initialStageSetLookup);
/* 176 */     if (stages == null || stages.length == 0) {
/* 177 */       ((HytaleLogger.Api)LOGGER.atWarning()).log("[ChangeFarmingStage] FAILED: stages is null or empty for stageSet=%s", initialStageSetLookup);
/* 178 */       (context.getState()).state = InteractionState.Failed;
/*     */       return;
/*     */     } 
/* 181 */     ((HytaleLogger.Api)LOGGER.atInfo()).log("[ChangeFarmingStage] Initial stages lookup: stageSet=%s, stageCount=%d", initialStageSetLookup, stages.length);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 187 */     if (blockRef == null) {
/*     */       
/* 189 */       ((HytaleLogger.Api)LOGGER.atInfo()).log("[ChangeFarmingStage] Creating new block entity (harvest0 pattern)");
/* 190 */       Holder<ChunkStore> blockEntity = ChunkStore.REGISTRY.newHolder();
/* 191 */       blockEntity.putComponent(BlockModule.BlockStateInfo.getComponentType(), (Component)new BlockModule.BlockStateInfo(blockIndexColumn, chunkRef));
/*     */       
/* 193 */       farmingBlock = new FarmingBlock();
/* 194 */       farmingBlock.setLastTickGameTime(now);
/* 195 */       farmingBlock.setCurrentStageSet((this.targetStageSet != null) ? this.targetStageSet : farmingConfig.getStartingStageSet());
/*     */ 
/*     */       
/* 198 */       int initStage = Math.max(0, stages.length - 2);
/* 199 */       farmingBlock.setGrowthProgress(initStage);
/* 200 */       blockEntity.addComponent(FarmingBlock.getComponentType(), (Component)farmingBlock);
/*     */       
/* 202 */       blockRef = chunkStore.addEntity(blockEntity, AddReason.SPAWN);
/* 203 */       ((HytaleLogger.Api)LOGGER.atInfo()).log("[ChangeFarmingStage] Created new block entity with FarmingBlock: stageSet=%s, initialProgress=%d (second-to-last to avoid removal)", farmingBlock
/* 204 */           .getCurrentStageSet(), initStage);
/*     */ 
/*     */       
/* 207 */       if (blockRef != null) {
/* 208 */         farmingBlock.setGrowthProgress((stages.length - 1));
/* 209 */         ((HytaleLogger.Api)LOGGER.atInfo()).log("[ChangeFarmingStage] Updated growthProgress to %d (actual final stage)", stages.length - 1);
/*     */       } 
/*     */     } else {
/*     */       
/* 213 */       farmingBlock = (FarmingBlock)chunkStore.getComponent(blockRef, FarmingBlock.getComponentType());
/* 214 */       boolean hadExistingFarmingBlock = (farmingBlock != null);
/* 215 */       ((HytaleLogger.Api)LOGGER.atInfo()).log("[ChangeFarmingStage] Block entity exists, FarmingBlock component: %s", hadExistingFarmingBlock ? "exists" : "null");
/*     */       
/* 217 */       if (farmingBlock == null) {
/*     */         
/* 219 */         farmingBlock = new FarmingBlock();
/* 220 */         farmingBlock.setLastTickGameTime(now);
/* 221 */         farmingBlock.setCurrentStageSet((this.targetStageSet != null) ? this.targetStageSet : farmingConfig.getStartingStageSet());
/* 222 */         farmingBlock.setGrowthProgress((stages.length - 1));
/* 223 */         chunkStore.putComponent(blockRef, FarmingBlock.getComponentType(), (Component)farmingBlock);
/* 224 */         ((HytaleLogger.Api)LOGGER.atInfo()).log("[ChangeFarmingStage] Added FarmingBlock to existing entity: stageSet=%s, initialProgress=%d", farmingBlock
/* 225 */             .getCurrentStageSet(), stages.length - 1);
/*     */       } else {
/* 227 */         ((HytaleLogger.Api)LOGGER.atInfo()).log("[ChangeFarmingStage] Existing FarmingBlock: stageSet=%s, growthProgress=%.2f, lastTickGameTime=%d", farmingBlock
/* 228 */             .getCurrentStageSet(), Float.valueOf(farmingBlock.getGrowthProgress()), farmingBlock.getLastTickGameTime());
/*     */       } 
/*     */     } 
/*     */     
/* 232 */     if (blockRef == null) {
/* 233 */       ((HytaleLogger.Api)LOGGER.atWarning()).log("[ChangeFarmingStage] FAILED: blockRef is still null after entity creation");
/* 234 */       (context.getState()).state = InteractionState.Failed;
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 239 */     String stageSetName = (this.targetStageSet != null) ? this.targetStageSet : farmingBlock.getCurrentStageSet();
/*     */     
/* 241 */     stages = (FarmingStageData[])farmingConfig.getStages().get(stageSetName);
/* 242 */     if (stages == null || stages.length == 0) {
/* 243 */       ((HytaleLogger.Api)LOGGER.atWarning()).log("[ChangeFarmingStage] FAILED: stages null/empty after re-fetch with stageSet=%s", stageSetName);
/* 244 */       (context.getState()).state = InteractionState.Failed;
/*     */       return;
/*     */     } 
/* 247 */     ((HytaleLogger.Api)LOGGER.atInfo()).log("[ChangeFarmingStage] Using stageSet=%s with %d stages", stageSetName, stages.length);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 252 */     int currentStage = (int)farmingBlock.getGrowthProgress();
/* 253 */     int originalCurrentStage = currentStage;
/*     */ 
/*     */     
/* 256 */     if (currentStage >= stages.length) {
/* 257 */       ((HytaleLogger.Api)LOGGER.atInfo()).log("[ChangeFarmingStage] Clamping currentStage from %d to %d (was >= stages.length)", currentStage, stages.length - 1);
/* 258 */       currentStage = stages.length - 1;
/*     */     } 
/*     */     
/* 261 */     if (this.increaseBy != null) {
/*     */       
/* 263 */       stageIndex = currentStage + this.increaseBy.intValue();
/* 264 */       ((HytaleLogger.Api)LOGGER.atInfo()).log("[ChangeFarmingStage] Mode=INCREASE: %d + %d = %d", Integer.valueOf(currentStage), this.increaseBy, Integer.valueOf(stageIndex));
/* 265 */     } else if (this.decreaseBy != null) {
/*     */       
/* 267 */       stageIndex = currentStage - this.decreaseBy.intValue();
/* 268 */       ((HytaleLogger.Api)LOGGER.atInfo()).log("[ChangeFarmingStage] Mode=DECREASE: %d - %d = %d", Integer.valueOf(currentStage), this.decreaseBy, Integer.valueOf(stageIndex));
/*     */     } else {
/*     */       
/* 271 */       stageIndex = this.targetStage;
/* 272 */       if (stageIndex < 0)
/*     */       {
/* 274 */         stageIndex = stages.length - 1;
/*     */       }
/* 276 */       ((HytaleLogger.Api)LOGGER.atInfo()).log("[ChangeFarmingStage] Mode=ABSOLUTE: targetStage=%d, resolved=%d", this.targetStage, stageIndex);
/*     */     } 
/*     */     
/* 279 */     int preClampStageIndex = stageIndex;
/*     */     
/* 281 */     if (stageIndex < 0) {
/* 282 */       stageIndex = 0;
/*     */     }
/* 284 */     if (stageIndex >= stages.length) {
/* 285 */       stageIndex = stages.length - 1;
/*     */     }
/* 287 */     if (preClampStageIndex != stageIndex) {
/* 288 */       ((HytaleLogger.Api)LOGGER.atInfo()).log("[ChangeFarmingStage] Clamped stageIndex from %d to %d", preClampStageIndex, stageIndex);
/*     */     }
/*     */ 
/*     */     
/* 292 */     int previousStageIndex = (int)farmingBlock.getGrowthProgress();
/* 293 */     FarmingStageData previousStage = null;
/* 294 */     FarmingStageData[] currentStages = (FarmingStageData[])farmingConfig.getStages().get(farmingBlock.getCurrentStageSet());
/* 295 */     if (currentStages != null && previousStageIndex >= 0 && previousStageIndex < currentStages.length) {
/* 296 */       previousStage = currentStages[previousStageIndex];
/*     */     }
/* 298 */     ((HytaleLogger.Api)LOGGER.atInfo()).log("[ChangeFarmingStage] Previous stage data: index=%d, hasPreviousStage=%s", previousStageIndex, (previousStage != null));
/*     */ 
/*     */     
/* 301 */     farmingBlock.setCurrentStageSet(stageSetName);
/* 302 */     farmingBlock.setGrowthProgress(stageIndex);
/* 303 */     farmingBlock.setExecutions(0);
/* 304 */     farmingBlock.setGeneration(farmingBlock.getGeneration() + 1);
/*     */ 
/*     */     
/* 307 */     farmingBlock.setLastTickGameTime(now);
/*     */     
/* 309 */     ((HytaleLogger.Api)LOGGER.atInfo()).log("[ChangeFarmingStage] Updated FarmingBlock: stageSet=%s, growthProgress=%d, generation=%d", stageSetName, 
/* 310 */         Integer.valueOf(stageIndex), Integer.valueOf(farmingBlock.getGeneration()));
/*     */ 
/*     */     
/* 313 */     Ref<ChunkStore> sectionRef = world.getChunkStore().getChunkSectionReference(
/* 314 */         ChunkUtil.chunkCoordinate(x), ChunkUtil.chunkCoordinate(y), ChunkUtil.chunkCoordinate(z));
/* 315 */     if (sectionRef != null) {
/* 316 */       BlockSection section = (BlockSection)chunkStore.getComponent(sectionRef, BlockSection.getComponentType());
/* 317 */       if (section != null) {
/* 318 */         section.scheduleTick(ChunkUtil.indexBlock(x, y, z), now);
/*     */       }
/* 320 */       stages[stageIndex].apply((ComponentAccessor)chunkStore, sectionRef, blockRef, x, y, z, previousStage);
/* 321 */       ((HytaleLogger.Api)LOGGER.atInfo()).log("[ChangeFarmingStage] Applied stage %d via stages[%d].apply()", stageIndex, stageIndex);
/*     */     } else {
/* 323 */       ((HytaleLogger.Api)LOGGER.atWarning()).log("[ChangeFarmingStage] sectionRef was null - could not apply stage!");
/*     */     } 
/*     */ 
/*     */     
/* 327 */     worldChunk.setTicking(x, y, z, true);
/*     */     
/* 329 */     ((HytaleLogger.Api)LOGGER.atInfo()).log("[ChangeFarmingStage] SUCCESS: Changed stage from %d to %d at pos=(%d, %d, %d)", 
/* 330 */         Integer.valueOf(originalCurrentStage), Integer.valueOf(stageIndex), Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(z));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void simulateInteractWithBlock(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nullable ItemStack itemInHand, @Nonnull World world, @Nonnull Vector3i targetBlock) {}
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 342 */     return "ChangeFarmingStageInteraction{targetStage=" + this.targetStage + ", increaseBy=" + this.increaseBy + ", decreaseBy=" + this.decreaseBy + ", targetStageSet='" + this.targetStageSet + "'} " + super
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 347 */       .toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\farming\interactions\ChangeFarmingStageInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */