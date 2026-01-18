/*     */ package com.hypixel.hytale.builtin.adventure.farming.config.modifiers;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.adventure.farming.states.TilledSoilBlock;
/*     */ import com.hypixel.hytale.builtin.weather.resources.WeatherResource;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.farming.GrowthModifierAsset;
/*     */ import com.hypixel.hytale.server.core.asset.type.fluid.Fluid;
/*     */ import com.hypixel.hytale.server.core.asset.type.weather.config.Weather;
/*     */ import com.hypixel.hytale.server.core.modules.time.WorldTimeResource;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockComponentChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.BlockSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.ChunkSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.FluidSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
/*     */ import java.time.Instant;
/*     */ import java.util.Arrays;
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
/*     */ public class WaterGrowthModifierAsset
/*     */   extends GrowthModifierAsset
/*     */ {
/*     */   public static final BuilderCodec<WaterGrowthModifierAsset> CODEC;
/*     */   protected String[] fluids;
/*     */   protected IntOpenHashSet fluidIds;
/*     */   protected String[] weathers;
/*     */   protected IntOpenHashSet weatherIds;
/*     */   protected int rainDuration;
/*     */   
/*     */   static {
/*  70 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(WaterGrowthModifierAsset.class, WaterGrowthModifierAsset::new, ABSTRACT_CODEC).append(new KeyedCodec("Fluids", (Codec)new ArrayCodec((Codec)Codec.STRING, x$0 -> new String[x$0])), (asset, blocks) -> asset.fluids = blocks, asset -> asset.fluids).addValidator((Validator)Fluid.VALIDATOR_CACHE.getArrayValidator().late()).add()).append(new KeyedCodec("Weathers", (Codec)Codec.STRING_ARRAY), (asset, weathers) -> asset.weathers = weathers, asset -> asset.weathers).addValidator((Validator)Weather.VALIDATOR_CACHE.getArrayValidator()).add()).addField(new KeyedCodec("RainDuration", (Codec)Codec.INTEGER), (asset, duration) -> asset.rainDuration = duration.intValue(), asset -> Integer.valueOf(asset.rainDuration))).afterDecode(asset -> { if (asset.fluids != null) { asset.fluidIds = new IntOpenHashSet(); for (int i = 0; i < asset.fluids.length; i++) asset.fluidIds.add(Fluid.getAssetMap().getIndex(asset.fluids[i]));  }  if (asset.weathers != null) { asset.weatherIds = new IntOpenHashSet(); for (int i = 0; i < asset.weathers.length; i++) asset.weatherIds.add(Weather.getAssetMap().getIndex(asset.weathers[i]));  }  })).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getFluids() {
/*  81 */     return this.fluids;
/*     */   }
/*     */   
/*     */   public IntOpenHashSet getFluidIds() {
/*  85 */     return this.fluidIds;
/*     */   }
/*     */   
/*     */   public String[] getWeathers() {
/*  89 */     return this.weathers;
/*     */   }
/*     */   
/*     */   public IntOpenHashSet getWeatherIds() {
/*  93 */     return this.weatherIds;
/*     */   }
/*     */   
/*     */   public int getRainDuration() {
/*  97 */     return this.rainDuration;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getCurrentGrowthMultiplier(CommandBuffer<ChunkStore> commandBuffer, Ref<ChunkStore> sectionRef, Ref<ChunkStore> blockRef, int x, int y, int z, boolean initialTick) {
/* 103 */     boolean hasWaterBlock = checkIfWaterSource(commandBuffer, sectionRef, blockRef, x, y, z);
/* 104 */     boolean isRaining = checkIfRaining(commandBuffer, sectionRef, x, y, z);
/*     */     
/* 106 */     boolean active = (hasWaterBlock || isRaining);
/*     */     
/* 108 */     TilledSoilBlock soil = getSoil(commandBuffer, sectionRef, x, y, z);
/* 109 */     if (soil != null) {
/* 110 */       if (soil.hasExternalWater() != active) {
/* 111 */         soil.setExternalWater(active);
/* 112 */         ((BlockSection)commandBuffer.getComponent(sectionRef, BlockSection.getComponentType())).setTicking(x, y, z, true);
/*     */       } 
/*     */       
/* 115 */       active |= isSoilWaterExpiring((WorldTimeResource)((ChunkStore)commandBuffer.getExternalData()).getWorld().getEntityStore().getStore().getResource(WorldTimeResource.getResourceType()), soil);
/*     */     } 
/*     */     
/* 118 */     if (!active) {
/* 119 */       return 1.0D;
/*     */     }
/* 121 */     return super.getCurrentGrowthMultiplier(commandBuffer, sectionRef, blockRef, x, y, z, initialTick);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private static TilledSoilBlock getSoil(CommandBuffer<ChunkStore> commandBuffer, Ref<ChunkStore> sectionRef, int x, int y, int z) {
/* 126 */     ChunkSection chunkSection = (ChunkSection)commandBuffer.getComponent(sectionRef, ChunkSection.getComponentType());
/* 127 */     Ref<ChunkStore> chunk = chunkSection.getChunkColumnReference();
/* 128 */     BlockComponentChunk blockComponentChunk = (BlockComponentChunk)commandBuffer.getComponent(chunk, BlockComponentChunk.getComponentType());
/*     */     
/* 130 */     Ref<ChunkStore> blockRefBelow = blockComponentChunk.getEntityReference(ChunkUtil.indexBlockInColumn(x, y - 1, z));
/* 131 */     if (blockRefBelow == null) return null;
/*     */     
/* 133 */     return (TilledSoilBlock)commandBuffer.getComponent(blockRefBelow, TilledSoilBlock.getComponentType());
/*     */   }
/*     */   
/*     */   protected boolean checkIfWaterSource(CommandBuffer<ChunkStore> commandBuffer, Ref<ChunkStore> sectionRef, Ref<ChunkStore> blockRef, int x, int y, int z) {
/* 137 */     IntOpenHashSet waterBlocks = this.fluidIds;
/* 138 */     if (waterBlocks == null) {
/* 139 */       return false;
/*     */     }
/*     */     
/* 142 */     TilledSoilBlock soil = getSoil(commandBuffer, sectionRef, x, y, z);
/* 143 */     if (soil == null) {
/* 144 */       return false;
/*     */     }
/*     */     
/* 147 */     int[] fluids = getNeighbourFluids(commandBuffer, sectionRef, x, y - 1, z);
/* 148 */     for (int block : fluids) {
/* 149 */       if (waterBlocks.contains(block))
/*     */       {
/* 151 */         return true;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 156 */     return false;
/*     */   }
/*     */   
/*     */   private int[] getNeighbourFluids(CommandBuffer<ChunkStore> commandBuffer, Ref<ChunkStore> sectionRef, int x, int y, int z) {
/* 160 */     ChunkSection section = (ChunkSection)commandBuffer.getComponent(sectionRef, ChunkSection.getComponentType());
/*     */     
/* 162 */     return new int[] {
/* 163 */         getFluidAtPos(x - 1, y, z, sectionRef, section, commandBuffer), 
/* 164 */         getFluidAtPos(x + 1, y, z, sectionRef, section, commandBuffer), 
/* 165 */         getFluidAtPos(x, y, z - 1, sectionRef, section, commandBuffer), 
/* 166 */         getFluidAtPos(x, y, z + 1, sectionRef, section, commandBuffer)
/*     */       };
/*     */   }
/*     */   
/*     */   private int getFluidAtPos(int posX, int posY, int posZ, Ref<ChunkStore> sectionRef, ChunkSection currentChunkSection, CommandBuffer<ChunkStore> commandBuffer) {
/* 171 */     Ref<ChunkStore> chunkToUse = sectionRef;
/*     */     
/* 173 */     int chunkX = ChunkUtil.worldCoordFromLocalCoord(currentChunkSection.getX(), posX);
/* 174 */     int chunkY = ChunkUtil.worldCoordFromLocalCoord(currentChunkSection.getY(), posY);
/* 175 */     int chunkZ = ChunkUtil.worldCoordFromLocalCoord(currentChunkSection.getZ(), posZ);
/* 176 */     if (ChunkUtil.isSameChunkSection(chunkX, chunkY, chunkZ, currentChunkSection.getX(), currentChunkSection.getY(), currentChunkSection.getZ())) {
/* 177 */       chunkToUse = ((ChunkStore)commandBuffer.getExternalData()).getChunkSectionReference(chunkX, chunkY, chunkZ);
/*     */     }
/*     */     
/* 180 */     if (chunkToUse == null) return Integer.MIN_VALUE;
/*     */     
/* 182 */     return ((FluidSection)commandBuffer.getComponent(chunkToUse, FluidSection.getComponentType())).getFluidId(posX, posY, posZ);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean checkIfRaining(CommandBuffer<ChunkStore> commandBuffer, Ref<ChunkStore> sectionRef, int x, int y, int z) {
/*     */     int weatherId;
/* 188 */     if (this.weatherIds == null)
/*     */     {
/* 190 */       return false;
/*     */     }
/*     */     
/* 193 */     ChunkSection section = (ChunkSection)commandBuffer.getComponent(sectionRef, ChunkSection.getComponentType());
/* 194 */     Ref<ChunkStore> chunk = section.getChunkColumnReference();
/* 195 */     BlockChunk blockChunk = (BlockChunk)commandBuffer.getComponent(chunk, BlockChunk.getComponentType());
/*     */     
/* 197 */     int cropId = blockChunk.getBlock(x, y, z);
/*     */     
/* 199 */     Store<EntityStore> store = ((ChunkStore)commandBuffer.getExternalData()).getWorld().getEntityStore().getStore();
/* 200 */     WorldTimeResource worldTimeResource = (WorldTimeResource)store.getResource(WorldTimeResource.getResourceType());
/* 201 */     WeatherResource weatherResource = (WeatherResource)store.getResource(WeatherResource.getResourceType());
/*     */     
/* 203 */     int environment = blockChunk.getEnvironment(x, y, z);
/*     */ 
/*     */     
/* 206 */     if (weatherResource.getForcedWeatherIndex() != 0) {
/* 207 */       weatherId = weatherResource.getForcedWeatherIndex();
/*     */     } else {
/* 209 */       weatherId = weatherResource.getWeatherIndexForEnvironment(environment);
/*     */     } 
/*     */     
/* 212 */     if (this.weatherIds.contains(weatherId)) {
/*     */       
/* 214 */       boolean unobstructed = true;
/* 215 */       for (int searchY = y + 1; searchY < 320; searchY++) {
/* 216 */         int block = blockChunk.getBlock(x, searchY, z);
/* 217 */         if (block != 0 && block != cropId) {
/* 218 */           unobstructed = false;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 223 */       if (unobstructed) {
/* 224 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 228 */     return false;
/*     */   }
/*     */   
/*     */   private boolean isSoilWaterExpiring(WorldTimeResource worldTimeResource, TilledSoilBlock soilBlock) {
/* 232 */     Instant until = soilBlock.getWateredUntil();
/* 233 */     if (until == null) {
/* 234 */       return false;
/*     */     }
/*     */     
/* 237 */     Instant now = worldTimeResource.getGameTime();
/* 238 */     if (now.isAfter(until)) {
/*     */       
/* 240 */       soilBlock.setWateredUntil(null);
/* 241 */       return false;
/*     */     } 
/* 243 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 250 */     return "WaterGrowthModifierAsset{blocks=" + Arrays.toString((Object[])this.fluids) + ", blockIds=" + String.valueOf(this.fluidIds) + ", weathers=" + 
/*     */       
/* 252 */       Arrays.toString((Object[])this.weathers) + ", weatherIds=" + String.valueOf(this.weatherIds) + ", rainDuration=" + this.rainDuration + "} " + super
/*     */ 
/*     */       
/* 255 */       .toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\farming\config\modifiers\WaterGrowthModifierAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */