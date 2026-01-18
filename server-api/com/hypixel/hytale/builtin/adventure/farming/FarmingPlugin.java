/*     */ package com.hypixel.hytale.builtin.adventure.farming;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetMap;
/*     */ import com.hypixel.hytale.assetstore.AssetStore;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetCodec;
/*     */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*     */ import com.hypixel.hytale.builtin.adventure.farming.component.CoopResidentComponent;
/*     */ import com.hypixel.hytale.builtin.adventure.farming.config.FarmingCoopAsset;
/*     */ import com.hypixel.hytale.builtin.adventure.farming.config.modifiers.FertilizerGrowthModifierAsset;
/*     */ import com.hypixel.hytale.builtin.adventure.farming.config.modifiers.LightLevelGrowthModifierAsset;
/*     */ import com.hypixel.hytale.builtin.adventure.farming.config.modifiers.WaterGrowthModifierAsset;
/*     */ import com.hypixel.hytale.builtin.adventure.farming.config.stages.BlockStateFarmingStageData;
/*     */ import com.hypixel.hytale.builtin.adventure.farming.config.stages.BlockTypeFarmingStageData;
/*     */ import com.hypixel.hytale.builtin.adventure.farming.config.stages.PrefabFarmingStageData;
/*     */ import com.hypixel.hytale.builtin.adventure.farming.config.stages.spread.DirectionalGrowthBehaviour;
/*     */ import com.hypixel.hytale.builtin.adventure.farming.config.stages.spread.SpreadFarmingStageData;
/*     */ import com.hypixel.hytale.builtin.adventure.farming.config.stages.spread.SpreadGrowthBehaviour;
/*     */ import com.hypixel.hytale.builtin.adventure.farming.interactions.ChangeFarmingStageInteraction;
/*     */ import com.hypixel.hytale.builtin.adventure.farming.interactions.FertilizeSoilInteraction;
/*     */ import com.hypixel.hytale.builtin.adventure.farming.interactions.HarvestCropInteraction;
/*     */ import com.hypixel.hytale.builtin.adventure.farming.interactions.UseCaptureCrateInteraction;
/*     */ import com.hypixel.hytale.builtin.adventure.farming.interactions.UseCoopInteraction;
/*     */ import com.hypixel.hytale.builtin.adventure.farming.interactions.UseWateringCanInteraction;
/*     */ import com.hypixel.hytale.builtin.adventure.farming.states.CoopBlock;
/*     */ import com.hypixel.hytale.builtin.adventure.farming.states.FarmingBlock;
/*     */ import com.hypixel.hytale.builtin.adventure.farming.states.FarmingBlockState;
/*     */ import com.hypixel.hytale.builtin.adventure.farming.states.TilledSoilBlock;
/*     */ import com.hypixel.hytale.builtin.tagset.config.NPCGroup;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.lookup.StringCodecMapCodec;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.system.ISystem;
/*     */ import com.hypixel.hytale.event.EventPriority;
/*     */ import com.hypixel.hytale.server.core.asset.HytaleAssetStore;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.farming.FarmingStageData;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.farming.GrowthModifierAsset;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.ItemDropList;
/*     */ import com.hypixel.hytale.server.core.asset.type.weather.config.Weather;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction;
/*     */ import com.hypixel.hytale.server.core.plugin.JavaPlugin;
/*     */ import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockComponentChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.events.ChunkPreLoadProcessEvent;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class FarmingPlugin extends JavaPlugin {
/*     */   public static FarmingPlugin get() {
/*  52 */     return instance;
/*     */   }
/*     */ 
/*     */   
/*     */   protected static FarmingPlugin instance;
/*     */   
/*     */   private ComponentType<ChunkStore, TilledSoilBlock> tiledSoilBlockComponentType;
/*     */   
/*     */   private ComponentType<ChunkStore, FarmingBlock> farmingBlockComponentType;
/*     */   
/*     */   private ComponentType<ChunkStore, FarmingBlockState> farmingBlockStateComponentType;
/*     */   
/*     */   private ComponentType<ChunkStore, CoopBlock> coopBlockStateComponentType;
/*     */   private ComponentType<EntityStore, CoopResidentComponent> coopResidentComponentType;
/*     */   
/*     */   public FarmingPlugin(@Nonnull JavaPluginInit init) {
/*  68 */     super(init);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setup() {
/*  73 */     instance = this;
/*     */     
/*  75 */     getAssetRegistry().register((AssetStore)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.builder(GrowthModifierAsset.class, (AssetMap)new DefaultAssetMap())
/*  76 */         .setPath("Farming/Modifiers"))
/*  77 */         .setCodec((AssetCodec)GrowthModifierAsset.CODEC))
/*  78 */         .loadsAfter(new Class[] { Weather.class
/*  79 */           })).setKeyFunction(GrowthModifierAsset::getId))
/*  80 */         .build());
/*     */     
/*  82 */     getAssetRegistry().register((AssetStore)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.builder(FarmingCoopAsset.class, (AssetMap)new DefaultAssetMap())
/*  83 */         .setPath("Farming/Coops"))
/*  84 */         .setCodec((AssetCodec)FarmingCoopAsset.CODEC))
/*  85 */         .loadsAfter(new Class[] { ItemDropList.class, NPCGroup.class
/*  86 */           })).setKeyFunction(FarmingCoopAsset::getId))
/*  87 */         .build());
/*     */     
/*  89 */     getCodecRegistry(Interaction.CODEC)
/*  90 */       .register("HarvestCrop", HarvestCropInteraction.class, HarvestCropInteraction.CODEC)
/*  91 */       .register("FertilizeSoil", FertilizeSoilInteraction.class, FertilizeSoilInteraction.CODEC)
/*  92 */       .register("ChangeFarmingStage", ChangeFarmingStageInteraction.class, ChangeFarmingStageInteraction.CODEC)
/*  93 */       .register("UseWateringCan", UseWateringCanInteraction.class, UseWateringCanInteraction.CODEC)
/*  94 */       .register("UseCoop", UseCoopInteraction.class, UseCoopInteraction.CODEC)
/*  95 */       .register("UseCaptureCrate", UseCaptureCrateInteraction.class, UseCaptureCrateInteraction.CODEC);
/*     */     
/*  97 */     getCodecRegistry(GrowthModifierAsset.CODEC).register("Fertilizer", FertilizerGrowthModifierAsset.class, FertilizerGrowthModifierAsset.CODEC);
/*  98 */     getCodecRegistry(GrowthModifierAsset.CODEC).register("LightLevel", LightLevelGrowthModifierAsset.class, LightLevelGrowthModifierAsset.CODEC);
/*  99 */     getCodecRegistry(GrowthModifierAsset.CODEC).register("Water", WaterGrowthModifierAsset.class, WaterGrowthModifierAsset.CODEC);
/*     */     
/* 101 */     getCodecRegistry((StringCodecMapCodec)FarmingStageData.CODEC).register("BlockType", BlockTypeFarmingStageData.class, (Codec)BlockTypeFarmingStageData.CODEC);
/* 102 */     getCodecRegistry((StringCodecMapCodec)FarmingStageData.CODEC).register("BlockState", BlockStateFarmingStageData.class, (Codec)BlockStateFarmingStageData.CODEC);
/* 103 */     getCodecRegistry((StringCodecMapCodec)FarmingStageData.CODEC).register("Prefab", PrefabFarmingStageData.class, (Codec)PrefabFarmingStageData.CODEC);
/* 104 */     getCodecRegistry((StringCodecMapCodec)FarmingStageData.CODEC).register("Spread", SpreadFarmingStageData.class, (Codec)SpreadFarmingStageData.CODEC);
/*     */     
/* 106 */     getCodecRegistry((StringCodecMapCodec)SpreadGrowthBehaviour.CODEC).register("Directional", DirectionalGrowthBehaviour.class, (Codec)DirectionalGrowthBehaviour.CODEC);
/*     */     
/* 108 */     this.tiledSoilBlockComponentType = getChunkStoreRegistry().registerComponent(TilledSoilBlock.class, "TilledSoil", TilledSoilBlock.CODEC);
/* 109 */     this.farmingBlockComponentType = getChunkStoreRegistry().registerComponent(FarmingBlock.class, "FarmingBlock", FarmingBlock.CODEC);
/* 110 */     this.farmingBlockStateComponentType = getChunkStoreRegistry().registerComponent(FarmingBlockState.class, "Farming", FarmingBlockState.CODEC);
/* 111 */     this.coopBlockStateComponentType = getChunkStoreRegistry().registerComponent(CoopBlock.class, "Coop", CoopBlock.CODEC);
/*     */     
/* 113 */     this.coopResidentComponentType = getEntityStoreRegistry().registerComponent(CoopResidentComponent.class, "CoopResident", CoopResidentComponent.CODEC);
/*     */     
/* 115 */     getChunkStoreRegistry().registerSystem((ISystem)new FarmingSystems.OnSoilAdded());
/* 116 */     getChunkStoreRegistry().registerSystem((ISystem)new FarmingSystems.OnFarmBlockAdded());
/* 117 */     getChunkStoreRegistry().registerSystem((ISystem)new FarmingSystems.Ticking());
/* 118 */     getChunkStoreRegistry().registerSystem((ISystem)new FarmingSystems.MigrateFarming());
/* 119 */     getChunkStoreRegistry().registerSystem((ISystem)new FarmingSystems.OnCoopAdded());
/*     */     
/* 121 */     getEntityStoreRegistry().registerSystem((ISystem)new FarmingSystems.CoopResidentEntitySystem());
/* 122 */     getEntityStoreRegistry().registerSystem((ISystem)new FarmingSystems.CoopResidentTicking());
/*     */     
/* 124 */     getEventRegistry().registerGlobal(EventPriority.LAST, ChunkPreLoadProcessEvent.class, FarmingPlugin::preventSpreadOnNew);
/*     */   }
/*     */   
/*     */   private static void preventSpreadOnNew(ChunkPreLoadProcessEvent event) {
/* 128 */     if (!event.isNewlyGenerated())
/* 129 */       return;  BlockComponentChunk components = (BlockComponentChunk)event.getHolder().getComponent(BlockComponentChunk.getComponentType());
/* 130 */     if (components == null)
/*     */       return; 
/* 132 */     Int2ObjectMap<Holder<ChunkStore>> holders = components.getEntityHolders();
/* 133 */     holders.values().forEach(v -> {
/*     */           FarmingBlock farming = (FarmingBlock)v.getComponent(FarmingBlock.getComponentType());
/*     */           if (farming == null)
/*     */             return; 
/*     */           farming.setSpreadRate(0.0F);
/*     */         });
/*     */   }
/*     */   public ComponentType<ChunkStore, TilledSoilBlock> getTiledSoilBlockComponentType() {
/* 141 */     return this.tiledSoilBlockComponentType;
/*     */   }
/*     */   
/*     */   public ComponentType<ChunkStore, FarmingBlock> getFarmingBlockComponentType() {
/* 145 */     return this.farmingBlockComponentType;
/*     */   }
/*     */   
/*     */   public ComponentType<ChunkStore, FarmingBlockState> getFarmingBlockStateComponentType() {
/* 149 */     return this.farmingBlockStateComponentType;
/*     */   }
/*     */   
/*     */   public ComponentType<ChunkStore, CoopBlock> getCoopBlockStateComponentType() {
/* 153 */     return this.coopBlockStateComponentType;
/*     */   }
/*     */   
/*     */   public ComponentType<EntityStore, CoopResidentComponent> getCoopResidentComponentType() {
/* 157 */     return this.coopResidentComponentType;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\farming\FarmingPlugin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */