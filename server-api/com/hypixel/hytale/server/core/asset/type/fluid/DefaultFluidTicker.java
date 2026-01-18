/*     */ package com.hypixel.hytale.server.core.asset.type.fluid;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.map.BlockTypeAssetMap;
/*     */ import com.hypixel.hytale.assetstore.map.IndexedLookupTableAssetMap;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.map.MapCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.common.util.MapUtil;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector2i;
/*     */ import com.hypixel.hytale.protocol.SoundCategory;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktick.BlockTickStrategy;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.asset.type.soundevent.config.SoundEvent;
/*     */ import com.hypixel.hytale.server.core.universe.world.SoundUtil;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.BlockSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.FluidSection;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*     */ import java.util.Collections;
/*     */ import java.util.Map;
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
/*     */ public class DefaultFluidTicker
/*     */   extends FluidTicker
/*     */ {
/*     */   public static final BuilderCodec<DefaultFluidTicker> CODEC;
/*     */   private static final int MAX_DROP_DISTANCE = 5;
/*     */   
/*     */   static {
/*  46 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(DefaultFluidTicker.class, DefaultFluidTicker::new, BASE_CODEC).appendInherited(new KeyedCodec("SpreadFluid", (Codec)Codec.STRING), (ticker, o) -> ticker.spreadFluid = o, ticker -> ticker.spreadFluid, (ticker, parent) -> ticker.spreadFluid = parent.spreadFluid).addValidator((Validator)Fluid.VALIDATOR_CACHE.getValidator().late()).add()).appendInherited(new KeyedCodec("Collisions", (Codec)new MapCodec((Codec)FluidCollisionConfig.CODEC, java.util.HashMap::new)), (ticker, o) -> ticker.rawCollisionMap = MapUtil.combineUnmodifiable(ticker.rawCollisionMap, o), ticker -> ticker.rawCollisionMap, (ticker, parent) -> ticker.rawCollisionMap = parent.rawCollisionMap).documentation("Defines what happens when this fluid tries to spread into another fluid").add()).build();
/*     */   }
/*     */ 
/*     */   
/*  50 */   public static final DefaultFluidTicker INSTANCE = new DefaultFluidTicker();
/*     */   
/*     */   private String spreadFluid;
/*     */   
/*     */   private int spreadFluidId;
/*  55 */   private Map<String, FluidCollisionConfig> rawCollisionMap = Collections.emptyMap(); @Nullable
/*  56 */   private transient Int2ObjectMap<FluidCollisionConfig> collisionMap = null;
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected BlockTickStrategy spread(@Nonnull World world, long tick, @Nonnull FluidTicker.Accessor accessor, @Nonnull FluidSection fluidSection, BlockSection blockSection, @Nonnull Fluid fluid, int fluidId, byte fluidLevel, int worldX, int worldY, int worldZ) {
/*  62 */     if (worldY == 0) return BlockTickStrategy.SLEEP; 
/*  63 */     BlockTypeAssetMap<String, BlockType> blockMap = BlockType.getAssetMap();
/*  64 */     IndexedLookupTableAssetMap<String, Fluid> fluidMap = Fluid.getAssetMap();
/*     */     
/*  66 */     boolean isDifferentSectionBelow = (fluidSection.getY() != ChunkUtil.chunkCoordinate(worldY - 1));
/*     */     
/*  68 */     FluidSection fluidSectionBelow = isDifferentSectionBelow ? accessor.getFluidSectionByBlock(worldX, worldY - 1, worldZ) : fluidSection;
/*  69 */     BlockSection blockSectionBelow = isDifferentSectionBelow ? accessor.getBlockSectionByBlock(worldX, worldY - 1, worldZ) : blockSection;
/*  70 */     if (fluidSectionBelow == null || blockSectionBelow == null) return BlockTickStrategy.SLEEP;
/*     */     
/*  72 */     int fluidBelowId = fluidSectionBelow.getFluidId(worldX, worldY - 1, worldZ);
/*  73 */     Fluid fluidBelow = (Fluid)fluidMap.getAsset(fluidBelowId);
/*  74 */     byte fluidLevelBelow = fluidSectionBelow.getFluidLevel(worldX, worldY - 1, worldZ);
/*     */     
/*  76 */     int spreadFluidId = getSpreadFluidId(fluidId);
/*     */     
/*  78 */     int blockIdBelow = blockSectionBelow.get(worldX, worldY - 1, worldZ);
/*  79 */     BlockType blockBelow = (BlockType)blockMap.getAsset(blockIdBelow);
/*  80 */     if (!isSolid(blockBelow)) {
/*  81 */       FluidCollisionConfig config = (FluidCollisionConfig)getCollisionMap().get(fluidBelowId);
/*  82 */       if (config != null && 
/*  83 */         !executeCollision(world, accessor, fluidSectionBelow, blockSectionBelow, config, worldX, worldY - 1, worldZ)) {
/*  84 */         return BlockTickStrategy.CONTINUE;
/*     */       }
/*     */ 
/*     */       
/*  88 */       if ((fluidBelowId == 0 && !isSolid(blockBelow)) || (fluidBelowId == spreadFluidId && fluidLevelBelow < fluidBelow.getMaxFluidLevel())) {
/*     */         
/*  90 */         int spreadId = getSpreadFluidId(fluidId);
/*  91 */         Fluid spreadFluid = (Fluid)fluidMap.getAsset(spreadId);
/*  92 */         boolean changed = fluidSectionBelow.setFluid(worldX, worldY - 1, worldZ, spreadId, (byte)spreadFluid.getMaxFluidLevel());
/*  93 */         if (changed) {
/*  94 */           blockSectionBelow.setTicking(worldX, worldY - 1, worldZ, true);
/*     */         }
/*     */       } 
/*  97 */       return BlockTickStrategy.SLEEP;
/*     */     } 
/*     */     
/* 100 */     if (fluidBelowId == 0) {
/* 101 */       if (fluidLevel == 1 && fluid.getMaxFluidLevel() != 1) return BlockTickStrategy.SLEEP;
/*     */       
/* 103 */       int offsets = getSpreadOffsets(blockMap, accessor, fluidSection, blockSection, worldX, worldY, worldZ, ORTO_OFFSETS, fluidId, 5);
/*     */ 
/*     */       
/* 106 */       if (offsets == 2147483646) return BlockTickStrategy.WAIT_FOR_ADJACENT_CHUNK_LOAD;
/*     */       
/* 108 */       int childFillLevel = fluidLevel - 1;
/* 109 */       if (spreadFluidId != fluidId) {
/* 110 */         childFillLevel = ((Fluid)Fluid.getAssetMap().getAsset(spreadFluidId)).getMaxFluidLevel() - 1;
/*     */       }
/*     */ 
/*     */       
/* 114 */       BlockType sourceBlock = (BlockType)blockMap.getAsset(blockSection.get(worldX, worldY, worldZ));
/* 115 */       int sourceRotationIndex = blockSection.getRotationIndex(worldX, worldY, worldZ);
/* 116 */       int sourceFiller = blockSection.getFiller(worldX, worldY, worldZ);
/*     */       
/* 118 */       for (int i = 0; i < ORTO_OFFSETS.length; i++) {
/* 119 */         if (offsets != 0 && (offsets & 1 << i) == 0)
/*     */           continue; 
/* 121 */         Vector2i offset = ORTO_OFFSETS[i];
/* 122 */         int x = offset.x;
/* 123 */         int z = offset.y;
/* 124 */         int blockX = worldX + x;
/* 125 */         int blockZ = worldZ + z;
/*     */ 
/*     */ 
/*     */         
/* 129 */         if (blocksFluidFrom(sourceBlock, sourceRotationIndex, -x, -z, sourceFiller))
/*     */           continue; 
/* 131 */         boolean isDifferentSection = !ChunkUtil.isSameChunkSection(worldX, worldY, worldZ, blockX, worldY, blockZ);
/*     */         
/* 133 */         FluidSection otherFluidSection = isDifferentSection ? accessor.getFluidSectionByBlock(blockX, worldY, blockZ) : fluidSection;
/* 134 */         BlockSection otherBlockSection = isDifferentSection ? accessor.getBlockSectionByBlock(blockX, worldY, blockZ) : blockSection;
/* 135 */         if (otherFluidSection == null || otherBlockSection == null) return BlockTickStrategy.WAIT_FOR_ADJACENT_CHUNK_LOAD;
/*     */ 
/*     */         
/* 138 */         BlockType block = (BlockType)blockMap.getAsset(otherBlockSection.get(blockX, worldY, blockZ));
/* 139 */         int rotationIndex = otherBlockSection.getRotationIndex(blockX, worldY, blockZ);
/* 140 */         int destFiller = otherBlockSection.getFiller(blockX, worldY, blockZ);
/* 141 */         if (blocksFluidFrom(block, rotationIndex, x, z, destFiller))
/*     */           continue; 
/* 143 */         int otherFluidId = otherFluidSection.getFluidId(blockX, worldY, blockZ);
/* 144 */         if (otherFluidId != 0 && otherFluidId != spreadFluidId) {
/* 145 */           FluidCollisionConfig config = (FluidCollisionConfig)getCollisionMap().get(otherFluidId);
/* 146 */           if (config == null)
/*     */             continue; 
/* 148 */           if (executeCollision(world, accessor, otherFluidSection, otherBlockSection, config, blockX, worldY, blockZ))
/*     */             continue; 
/* 150 */         }  byte fillLevel = otherFluidSection.getFluidLevel(blockX, worldY, blockZ);
/* 151 */         if (otherFluidId != spreadFluidId || fillLevel < childFillLevel)
/*     */         {
/* 153 */           if (childFillLevel == 0) {
/* 154 */             otherFluidSection.setFluid(blockX, worldY, blockZ, 0, (byte)0);
/*     */           } else {
/* 156 */             otherFluidSection.setFluid(blockX, worldY, blockZ, spreadFluidId, (byte)childFillLevel);
/* 157 */             otherBlockSection.setTicking(blockX, worldY, blockZ, true);
/*     */           }  } 
/*     */         continue;
/*     */       } 
/*     */     } 
/* 162 */     return BlockTickStrategy.SLEEP;
/*     */   }
/*     */   
/*     */   private static boolean executeCollision(@Nonnull World world, @Nonnull FluidTicker.Accessor accessor, @Nonnull FluidSection fluidSection, BlockSection blockSection, @Nonnull FluidCollisionConfig config, int blockX, int blockY, int blockZ) {
/* 166 */     int blockToPlace = config.getBlockToPlaceIndex();
/* 167 */     if (blockToPlace != Integer.MIN_VALUE) {
/* 168 */       accessor.setBlock(blockX, blockY, blockZ, blockToPlace);
/* 169 */       setTickingSurrounding(accessor, blockSection, blockX, blockY, blockZ);
/* 170 */       fluidSection.setFluid(blockX, blockY, blockZ, 0, (byte)0);
/*     */     } 
/*     */     
/* 173 */     int soundEvent = config.getSoundEventIndex();
/* 174 */     if (soundEvent != Integer.MIN_VALUE)
/*     */     {
/* 176 */       world.execute(() -> SoundUtil.playSoundEvent3d(soundEvent, SoundCategory.SFX, blockX, blockY, blockZ, (ComponentAccessor)world.getEntityStore().getStore()));
/*     */     }
/*     */     
/* 179 */     return !config.placeFluid;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSelfFluid(int selfFluidId, int otherFluidId) {
/* 184 */     return (super.isSelfFluid(selfFluidId, otherFluidId) || otherFluidId == getSpreadFluidId(selfFluidId));
/*     */   }
/*     */   
/*     */   private int getSpreadFluidId(int fluidId) {
/* 188 */     if (this.spreadFluidId == 0) {
/* 189 */       if (this.spreadFluid != null) {
/* 190 */         this.spreadFluidId = Fluid.getAssetMap().getIndex(this.spreadFluid);
/*     */       } else {
/* 192 */         this.spreadFluidId = Integer.MIN_VALUE;
/*     */       } 
/*     */     }
/* 195 */     if (this.spreadFluidId == Integer.MIN_VALUE) return fluidId; 
/* 196 */     return this.spreadFluidId;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Int2ObjectMap<FluidCollisionConfig> getCollisionMap() {
/* 201 */     if (this.collisionMap == null) {
/* 202 */       Int2ObjectOpenHashMap<FluidCollisionConfig> collisionMap = new Int2ObjectOpenHashMap(this.rawCollisionMap.size());
/* 203 */       for (Map.Entry<String, FluidCollisionConfig> entry : this.rawCollisionMap.entrySet()) {
/* 204 */         int index = Fluid.getAssetMap().getIndex(entry.getKey());
/* 205 */         if (index == Integer.MIN_VALUE) {
/*     */           continue;
/*     */         }
/* 208 */         collisionMap.put(index, entry.getValue());
/*     */       } 
/*     */       
/* 211 */       this.collisionMap = (Int2ObjectMap<FluidCollisionConfig>)collisionMap;
/*     */     } 
/* 213 */     return this.collisionMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class FluidCollisionConfig
/*     */   {
/*     */     public static final BuilderCodec<FluidCollisionConfig> CODEC;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private String blockToPlace;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/* 242 */       CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(FluidCollisionConfig.class, FluidCollisionConfig::new).appendInherited(new KeyedCodec("BlockToPlace", (Codec)Codec.STRING), (o, v) -> o.blockToPlace = v, o -> o.blockToPlace, (o, p) -> o.blockToPlace = p.blockToPlace).documentation("The block to place when a collision occurs").add()).appendInherited(new KeyedCodec("SoundEvent", (Codec)Codec.STRING), (o, v) -> o.soundEvent = v, o -> o.soundEvent, (o, p) -> o.soundEvent = p.soundEvent).addValidator(SoundEvent.VALIDATOR_CACHE.getValidator()).add()).appendInherited(new KeyedCodec("PlaceFluid", (Codec)Codec.BOOLEAN), (o, v) -> o.placeFluid = v.booleanValue(), o -> Boolean.valueOf(o.placeFluid), (o, p) -> o.placeFluid = p.placeFluid).documentation("Whether to still place the fluid on collision").add()).build();
/*     */     }
/*     */     
/* 245 */     private int blockToPlaceIndex = Integer.MIN_VALUE;
/*     */     public boolean placeFluid = false;
/*     */     private String soundEvent;
/* 248 */     private int soundEventIndex = Integer.MIN_VALUE;
/*     */     
/*     */     public int getBlockToPlaceIndex() {
/* 251 */       if (this.blockToPlaceIndex == Integer.MIN_VALUE && this.blockToPlace != null) {
/* 252 */         this.blockToPlaceIndex = BlockType.getBlockIdOrUnknown(this.blockToPlace, "Unknown block type %s", new Object[] { this.blockToPlace });
/*     */       }
/* 254 */       return this.blockToPlaceIndex;
/*     */     }
/*     */     
/*     */     public int getSoundEventIndex() {
/* 258 */       if (this.soundEventIndex == Integer.MIN_VALUE && this.soundEvent != null) {
/* 259 */         this.soundEventIndex = SoundEvent.getAssetMap().getIndex(this.soundEvent);
/*     */       }
/* 261 */       return this.soundEventIndex;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\fluid\DefaultFluidTicker.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */