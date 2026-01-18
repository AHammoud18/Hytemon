/*     */ package com.hypixel.hytale.server.core.blocktype;
/*     */ import com.hypixel.hytale.assetstore.map.BlockTypeAssetMap;
/*     */ import com.hypixel.hytale.assetstore.map.IndexedLookupTableAssetMap;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.common.plugin.PluginManifest;
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.dependency.Dependency;
/*     */ import com.hypixel.hytale.component.dependency.Order;
/*     */ import com.hypixel.hytale.component.dependency.SystemDependency;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.protocol.BenchType;
/*     */ import com.hypixel.hytale.protocol.BlockMaterial;
/*     */ import com.hypixel.hytale.server.core.asset.type.blockhitbox.BlockBoundingBoxes;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.StateData;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.bench.Bench;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.bench.CraftingBench;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.bench.DiagramCraftingBench;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.bench.ProcessingBench;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.bench.StructuralCraftingBench;
/*     */ import com.hypixel.hytale.server.core.blocktype.component.BlockPhysics;
/*     */ import com.hypixel.hytale.server.core.modules.LegacyModule;
/*     */ import com.hypixel.hytale.server.core.modules.migrations.ChunkColumnMigrationSystem;
/*     */ import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.accessor.BlockAccessor;
/*     */ import com.hypixel.hytale.server.core.universe.world.accessor.ChunkAccessor;
/*     */ import com.hypixel.hytale.server.core.universe.world.accessor.LocalCachedChunkAccessor;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.ChunkColumn;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.ChunkFlag;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.BlockSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.systems.ChunkSystems;
/*     */ import com.hypixel.hytale.server.core.universe.world.events.ChunkPreLoadProcessEvent;
/*     */ import com.hypixel.hytale.server.core.universe.world.meta.BlockState;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.util.FillerBlockUtil;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class BlockTypeModule extends JavaPlugin {
/*  53 */   public static final PluginManifest MANIFEST = PluginManifest.corePlugin(BlockTypeModule.class)
/*  54 */     .depends(new Class[] { ItemModule.class
/*  55 */       }).depends(new Class[] { LegacyModule.class
/*  56 */       }).build();
/*     */   
/*     */   public static final int SET_BLOCK_SETTINGS = 157;
/*     */   
/*     */   public static final String DEBUG_CUBE_TEXTURE_UP = "BlockTextures/_Debug/Up.png";
/*     */   
/*     */   public static final String DEBUG_CUBE_TEXTURE_DOWN = "BlockTextures/_Debug/Down.png";
/*     */   
/*     */   public static final String DEBUG_CUBE_TEXTURE_NORTH = "BlockTextures/_Debug/North.png";
/*     */   
/*     */   public static final String DEBUG_CUBE_TEXTURE_SOUTH = "BlockTextures/_Debug/South.png";
/*     */   public static final String DEBUG_CUBE_TEXTURE_EAST = "BlockTextures/_Debug/East.png";
/*     */   public static final String DEBUG_CUBE_TEXTURE_WEST = "BlockTextures/_Debug/West.png";
/*     */   public static final String DEBUG_MODEL_MODEL = "Blocks/_Debug/Model.blockymodel";
/*     */   public static final String DEBUG_MODEL_BLOCK_TEXTURE = "Blocks/_Debug/Texture.png";
/*     */   public static final String DEBUG_MODEL_ENTITY_TEXTURE = "Characters/_Debug/Texture.png";
/*  72 */   private static final ThreadLocal<BlockType[]> TEMP_BLOCKS = (ThreadLocal)ThreadLocal.withInitial(() -> new BlockType[327680]);
/*     */   
/*     */   private static BlockTypeModule instance;
/*     */   
/*     */   private ComponentType<ChunkStore, BlockPhysics> blockPhysicsComponentType;
/*     */   
/*     */   public static BlockTypeModule get() {
/*  79 */     return instance;
/*     */   }
/*     */   
/*     */   public BlockTypeModule(@Nonnull JavaPluginInit init) {
/*  83 */     super(init);
/*  84 */     instance = this;
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
/*     */   protected void setup() {
/*  96 */     Bench.CODEC.register(BenchType.Crafting, CraftingBench.class, (Codec)CraftingBench.CODEC);
/*  97 */     Bench.CODEC.register(BenchType.Processing, ProcessingBench.class, (Codec)ProcessingBench.CODEC);
/*  98 */     Bench.CODEC.register(BenchType.DiagramCrafting, DiagramCraftingBench.class, (Codec)DiagramCraftingBench.CODEC);
/*  99 */     Bench.CODEC.register(BenchType.StructuralCrafting, StructuralCraftingBench.class, (Codec)StructuralCraftingBench.CODEC);
/*     */     
/* 101 */     this.blockPhysicsComponentType = getChunkStoreRegistry().registerComponent(BlockPhysics.class, "BlockPhysics", BlockPhysics.CODEC);
/* 102 */     getChunkStoreRegistry().registerSystem((ISystem)new MigrateLegacySections());
/*     */   }
/*     */   
/*     */   public ComponentType<ChunkStore, BlockPhysics> getBlockPhysicsComponentType() {
/* 106 */     return this.blockPhysicsComponentType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   private static void onChunkPreLoadProcessEnsureBlockState(@Nonnull ChunkPreLoadProcessEvent event) {
/* 117 */     if (!event.isNewlyGenerated())
/*     */       return; 
/* 119 */     BlockTypeAssetMap<String, BlockType> blockTypeAssetMap = BlockType.getAssetMap();
/*     */     
/* 121 */     Holder<ChunkStore> holder = event.getHolder();
/* 122 */     WorldChunk chunk = event.getChunk();
/*     */     
/* 124 */     ChunkColumn column = (ChunkColumn)holder.getComponent(ChunkColumn.getComponentType());
/* 125 */     if (column == null)
/*     */       return; 
/* 127 */     Holder[] arrayOfHolder = column.getSectionHolders();
/* 128 */     if (arrayOfHolder == null)
/*     */       return; 
/* 130 */     for (int sectionIndex = 0; sectionIndex < 10; sectionIndex++) {
/* 131 */       BlockSection section = (BlockSection)arrayOfHolder[sectionIndex].ensureAndGetComponent(BlockSection.getComponentType());
/* 132 */       if (!section.isSolidAir()) {
/*     */         
/* 134 */         int sectionYBlock = sectionIndex << 5;
/*     */         
/* 136 */         for (int sectionY = 0; sectionY < 32; sectionY++) {
/* 137 */           int y = sectionYBlock | sectionY;
/* 138 */           for (int x = 0; x < 32; x++) {
/* 139 */             for (int z = 0; z < 32; z++) {
/*     */               
/* 141 */               int blockId = section.get(x, y, z);
/* 142 */               BlockType blockType = (BlockType)blockTypeAssetMap.getAsset(blockId);
/* 143 */               if (blockType != null && !blockType.isUnknown() && 
/* 144 */                 section.getFiller(x, y, z) == 0) {
/*     */ 
/*     */                 
/* 147 */                 StateData state = blockType.getState();
/* 148 */                 if (state != null && state.getId() != null)
/*     */                 {
/* 150 */                   if (chunk.getState(x, y, z) == null) {
/*     */ 
/*     */                     
/* 153 */                     Vector3i position = new Vector3i(x, y, z);
/* 154 */                     BlockState blockState = BlockStateModule.get().createBlockState(state.getId(), chunk, position, blockType);
/* 155 */                     if (blockState != null)
/*     */                     {
/* 157 */                       chunk.setState(x, y, z, blockState); } 
/*     */                   }  } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void onChunkPreLoadProcess(@Nonnull ChunkPreLoadProcessEvent event) {
/* 168 */     if (!event.isNewlyGenerated())
/*     */       return; 
/* 170 */     WorldChunk chunk = event.getChunk();
/* 171 */     BlockChunk blockChunk = chunk.getBlockChunk();
/* 172 */     Holder<ChunkStore> holder = event.getHolder();
/*     */     
/* 174 */     ChunkColumn column = (ChunkColumn)holder.getComponent(ChunkColumn.getComponentType());
/* 175 */     if (column == null)
/*     */       return; 
/* 177 */     Holder[] arrayOfHolder = column.getSectionHolders();
/* 178 */     if (arrayOfHolder == null)
/*     */       return; 
/* 180 */     BlockType[] tempBlocks = TEMP_BLOCKS.get();
/* 181 */     Arrays.fill((Object[])tempBlocks, (Object)null);
/*     */     
/* 183 */     for (int sectionIndex = 0; sectionIndex < 10; sectionIndex++) {
/* 184 */       BlockSection section = (BlockSection)arrayOfHolder[sectionIndex].ensureAndGetComponent(BlockSection.getComponentType());
/* 185 */       if (!section.isSolidAir() && 
/* 186 */         section.getMaximumHitboxExtent() > 0.0D)
/*     */       {
/* 188 */         onChunksectionPreLoadProcess(chunk, section, sectionIndex, tempBlocks); } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void onChunksectionPreLoadProcess(@Nonnull WorldChunk chunk, @Nonnull BlockSection section, int sectionIndex, @Nonnull BlockType[] blocks) {
/* 193 */     int sectionYBlock = sectionIndex << 5;
/*     */     
/* 195 */     BlockTypeAssetMap<String, BlockType> blockTypeAssetMap = BlockType.getAssetMap();
/* 196 */     IndexedLookupTableAssetMap<String, BlockBoundingBoxes> hitboxAssetMap = BlockBoundingBoxes.getAssetMap();
/*     */     
/* 198 */     for (int y = 0; y < 32; y++) {
/* 199 */       int finalY = sectionYBlock | y;
/* 200 */       for (int x = 0; x < 32; x++) {
/* 201 */         int finalX = x;
/* 202 */         for (int z = 0; z < 32; z++) {
/* 203 */           int finalZ = z;
/*     */           
/* 205 */           BlockType blockType = getBlockType(blockTypeAssetMap, blocks, section, finalX, finalY, finalZ, true);
/* 206 */           if (blockType != null) {
/* 207 */             int rotation = section.getRotationIndex(x, y, z);
/*     */             
/* 209 */             int filler = section.getFiller(x, y, z);
/* 210 */             if (filler != 0)
/* 211 */             { int blockX = finalX - FillerBlockUtil.unpackX(filler);
/* 212 */               if (blockX >= 0 && blockX < 32) {
/*     */                 
/* 214 */                 int blockY = finalY - FillerBlockUtil.unpackY(filler);
/* 215 */                 if (blockY >= 0 && blockY < 320) {
/*     */                   
/* 217 */                   int blockZ = finalZ - FillerBlockUtil.unpackZ(filler);
/* 218 */                   if (blockZ >= 0 && blockZ < 32) {
/*     */                     
/* 220 */                     BlockType originBlockType = getBlockType(blockTypeAssetMap, blocks, section, blockX, blockY, blockZ, false);
/* 221 */                     if (originBlockType != null) {
/*     */                       
/* 223 */                       String blockTypeKey = blockType.getId();
/* 224 */                       if (blockType.isUnknown() || !blockTypeKey.equals(originBlockType.getId()))
/* 225 */                         chunk.breakBlock(finalX, finalY, finalZ, 157); 
/*     */                     } 
/*     */                   } 
/*     */                 } 
/*     */               }  }
/* 230 */             else { int blockId = blockTypeAssetMap.getIndex(blockType.getId());
/* 231 */               FillerBlockUtil.forEachFillerBlock(((BlockBoundingBoxes)hitboxAssetMap.getAsset(blockType.getHitboxTypeIndex())).get(rotation), (x1, y1, z1) -> {
/*     */                     if (x1 == 0 && y1 == 0 && z1 == 0)
/*     */                       return; 
/*     */                     int blockX = finalX + x1;
/*     */                     if (blockX < 0 || blockX >= 32)
/*     */                       return; 
/*     */                     int blockY = finalY + y1;
/*     */                     if (blockY < 0 || blockY >= 320)
/*     */                       return; 
/*     */                     int blockZ = finalZ + z1;
/*     */                     if (blockZ < 0 || blockZ >= 32)
/*     */                       return; 
/*     */                     BlockType neighbourBlockType = getBlockType(blockTypeAssetMap, blocks, section, blockX, blockY, blockZ, false);
/*     */                     if (neighbourBlockType == null || neighbourBlockType.getMaterial() == BlockMaterial.Solid)
/*     */                       return; 
/*     */                     int newFiller = FillerBlockUtil.pack(x1, y1, z1);
/*     */                     chunk.setBlock(blockX, blockY, blockZ, blockId, blockType, rotation, newFiller, 157);
/*     */                   }); }
/*     */           
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   @Nullable
/*     */   private static BlockType getBlockType(@Nonnull BlockTypeAssetMap<String, BlockType> blockTypeAssetMap, @Nonnull BlockType[] blocks, @Nonnull BlockSection section, int blockX, int blockY, int blockZ, boolean skipEmpty) {
/* 257 */     int indexBlock = ChunkUtil.indexBlockInColumn(blockX, blockY, blockZ);
/* 258 */     BlockType blockType = blocks[indexBlock];
/* 259 */     if (blockType == null) {
/* 260 */       int blockId = section.get(blockX, blockY, blockZ);
/* 261 */       if (blockId == 0) {
/* 262 */         blocks[indexBlock] = BlockType.EMPTY;
/* 263 */         return skipEmpty ? null : BlockType.EMPTY;
/*     */       } 
/*     */       
/* 266 */       blocks[indexBlock] = (BlockType)blockTypeAssetMap.getAsset(blockId); return (BlockType)blockTypeAssetMap.getAsset(blockId);
/*     */     } 
/*     */     
/* 269 */     if (skipEmpty && "Empty".equals(blockType.getId())) return null; 
/* 270 */     return blockType;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void breakOrSetFillerBlocks(@Nonnull BlockTypeAssetMap<String, BlockType> blockTypeAssetMap, @Nonnull IndexedLookupTableAssetMap<String, BlockBoundingBoxes> hitboxAssetMap, @Nonnull ChunkAccessor<?> accessor, @Nonnull BlockAccessor chunk, int finalX, int finalY, int finalZ, @Nonnull BlockType blockType, int rotation) {
/* 275 */     int filler = chunk.getFiller(finalX, finalY, finalZ);
/* 276 */     if (filler != 0) {
/* 277 */       if (!isFillerValid(blockTypeAssetMap, accessor, chunk, blockType, filler, finalX, finalY, finalZ)) {
/* 278 */         chunk.breakBlock(finalX, finalY, finalZ, 157);
/*     */       } else {
/* 280 */         int originX = finalX - FillerBlockUtil.unpackX(filler);
/* 281 */         int originY = finalY - FillerBlockUtil.unpackY(filler);
/* 282 */         int originZ = finalZ - FillerBlockUtil.unpackZ(filler);
/* 283 */         setFillerBlocks(blockTypeAssetMap, hitboxAssetMap, accessor, chunk, originX, originY, originZ, blockType, rotation);
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/* 288 */     setFillerBlocks(blockTypeAssetMap, hitboxAssetMap, accessor, chunk, finalX, finalY, finalZ, blockType, rotation);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private static BlockType getOriginBlockType(@Nonnull BlockTypeAssetMap<String, BlockType> blockTypeAssetMap, @Nonnull ChunkAccessor<?> accessor, @Nonnull BlockAccessor section, int originX, int originY, int originZ) {
/* 294 */     if (originX < 0 || originX >= 32 || originY < 0 || originY >= 320 || originZ < 0 || originZ >= 32) {
/*     */ 
/*     */       
/* 297 */       int worldX = (section.getX() << 5) + originX;
/* 298 */       int worldZ = (section.getZ() << 5) + originZ;
/* 299 */       BlockAccessor fillerOriginChunk = accessor.getChunkIfInMemory(ChunkUtil.indexChunkFromBlock(worldX, worldZ));
/* 300 */       if (fillerOriginChunk != null) {
/* 301 */         int j = fillerOriginChunk.getBlock(originX, originY, originZ);
/* 302 */         return (BlockType)blockTypeAssetMap.getAsset(j);
/*     */       } 
/*     */ 
/*     */       
/* 306 */       get().getLogger().at(Level.WARNING).log("Blocking chunk load when trying to get origin block for filler! Origin: %s, %s, %s", Integer.valueOf(originX), Integer.valueOf(originY), Integer.valueOf(originZ));
/* 307 */       fillerOriginChunk = accessor.getNonTickingChunk(ChunkUtil.indexChunkFromBlock(worldX, worldZ));
/* 308 */       int i = fillerOriginChunk.getBlock(originX, originY, originZ);
/* 309 */       return (BlockType)blockTypeAssetMap.getAsset(i);
/*     */     } 
/*     */     
/* 312 */     int originBlockId = section.getBlock(originX, originY, originZ);
/* 313 */     return (BlockType)blockTypeAssetMap.getAsset(originBlockId);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void setFillerBlocks(@Nonnull BlockTypeAssetMap<String, BlockType> blockTypeAssetMap, @Nonnull IndexedLookupTableAssetMap<String, BlockBoundingBoxes> hitboxAssetMap, @Nonnull ChunkAccessor<?> accessor, @Nonnull BlockAccessor chunk, int finalX, int finalY, int finalZ, @Nonnull BlockType originBlockType, int rotation) {
/* 318 */     int originBlockId = blockTypeAssetMap.getIndex(originBlockType.getId());
/* 319 */     FillerBlockUtil.forEachFillerBlock(((BlockBoundingBoxes)hitboxAssetMap.getAsset(originBlockType.getHitboxTypeIndex())).get(rotation), (x1, y1, z1) -> {
/*     */           if (x1 == 0 && y1 == 0 && z1 == 0) {
/*     */             return;
/*     */           }
/*     */           int blockX = finalX + x1;
/*     */           int blockY = finalY + y1;
/*     */           int blockZ = finalZ + z1;
/*     */           if (blockX < 0 || blockX >= 32 || blockY < 0 || blockY >= 320 || blockZ < 0 || blockZ >= 32) {
/*     */             int worldX = (chunk.getX() << 5) + blockX;
/*     */             int worldZ = (chunk.getZ() << 5) + blockZ;
/*     */             BlockAccessor neighbourChunk = accessor.getChunkIfInMemory(ChunkUtil.indexChunkFromBlock(worldX, worldZ));
/*     */             if (neighbourChunk == null) {
/*     */               return;
/*     */             }
/*     */             int i = neighbourChunk.getBlock(blockX, blockY, blockZ);
/*     */             int j = neighbourChunk.getRotationIndex(blockX, blockY, blockZ);
/*     */             int k = neighbourChunk.getFiller(blockX, blockY, blockZ);
/*     */             BlockType blockType1 = (BlockType)blockTypeAssetMap.getAsset(i);
/*     */             if ((k != 0 && isFillerValid(blockTypeAssetMap, accessor, chunk, blockType1, k, blockX, blockY, blockZ)) || blockType1.getMaterial() == BlockMaterial.Solid) {
/*     */               return;
/*     */             }
/*     */             int m = FillerBlockUtil.pack(x1, y1, z1);
/*     */             neighbourChunk.setBlock(blockX, blockY, blockZ, originBlockId, originBlockType, j, m, 157);
/*     */             return;
/*     */           } 
/*     */           int blockId = chunk.getBlock(blockX, blockY, blockZ);
/*     */           int currentRotation = chunk.getRotationIndex(blockX, blockY, blockZ);
/*     */           int currentFiller = chunk.getFiller(blockX, blockY, blockZ);
/*     */           BlockType blockType = (BlockType)blockTypeAssetMap.getAsset(blockId);
/*     */           if ((currentFiller != 0 && isFillerValid(blockTypeAssetMap, accessor, chunk, blockType, currentFiller, blockX, blockY, blockZ)) || blockType.getMaterial() == BlockMaterial.Solid) {
/*     */             return;
/*     */           }
/*     */           int filler = FillerBlockUtil.pack(x1, y1, z1);
/*     */           chunk.setBlock(blockX, blockY, blockZ, originBlockId, originBlockType, currentRotation, filler, 157);
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isFillerValid(@Nonnull BlockTypeAssetMap<String, BlockType> blockTypeAssetMap, @Nonnull ChunkAccessor<?> accessor, @Nonnull BlockAccessor chunk, @Nonnull BlockType blockType, int filler, int x, int y, int z) {
/* 363 */     int originX = x - FillerBlockUtil.unpackX(filler);
/* 364 */     int originY = y - FillerBlockUtil.unpackY(filler);
/* 365 */     int originZ = z - FillerBlockUtil.unpackZ(filler);
/*     */     
/* 367 */     BlockType originBlockType = getOriginBlockType(blockTypeAssetMap, accessor, chunk, originX, originY, originZ);
/* 368 */     if (blockType.isUnknown()) return false; 
/* 369 */     String blockTypeKey = blockType.getId();
/* 370 */     return blockTypeKey.equals(originBlockType.getId());
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   private static class FixFillerBlocksSystem
/*     */     extends RefSystem<ChunkStore> implements DisableProcessingAssert {
/* 376 */     private static final ComponentType<ChunkStore, WorldChunk> COMPONENT_TYPE = WorldChunk.getComponentType();
/*     */ 
/*     */     
/*     */     public Query<ChunkStore> getQuery() {
/* 380 */       return (Query)COMPONENT_TYPE;
/*     */     }
/*     */ 
/*     */     
/*     */     public void onEntityAdded(@Nonnull Ref<ChunkStore> ref, @Nonnull AddReason reason, @Nonnull Store<ChunkStore> store, @Nonnull CommandBuffer<ChunkStore> commandBuffer) {
/* 385 */       WorldChunk chunk = (WorldChunk)store.getComponent(ref, COMPONENT_TYPE);
/*     */ 
/*     */       
/* 388 */       if (!chunk.is(ChunkFlag.NEWLY_GENERATED))
/*     */         return; 
/* 390 */       World world = ((ChunkStore)store.getExternalData()).getWorld();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 395 */       world.execute(() -> fixFillerFor(world, chunk));
/*     */     }
/*     */     
/*     */     public static void fixFillerFor(@Nonnull World world, @Nonnull WorldChunk chunk) {
/* 399 */       BlockChunk blockChunk = chunk.getBlockChunk();
/*     */       
/* 401 */       BlockTypeAssetMap<String, BlockType> blockTypeAssetMap = BlockType.getAssetMap();
/* 402 */       IndexedLookupTableAssetMap<String, BlockBoundingBoxes> hitboxAssetMap = BlockBoundingBoxes.getAssetMap();
/*     */       
/* 404 */       LocalCachedChunkAccessor accessor = LocalCachedChunkAccessor.atChunk((ChunkAccessor)world, chunk, 1);
/* 405 */       for (int x = -1; x < 2; x++) {
/* 406 */         for (int z = -1; z < 2; z++) {
/* 407 */           if (x != 0 || z != 0) {
/* 408 */             WorldChunk chunkIfInMemory = world.getChunkIfInMemory(ChunkUtil.indexChunk(x + chunk.getX(), z + chunk.getZ()));
/* 409 */             if (chunkIfInMemory != null) accessor.overwrite(chunkIfInMemory); 
/*     */           } 
/*     */         } 
/*     */       } 
/* 413 */       for (int sectionIndex = 0; sectionIndex < 10; sectionIndex++) {
/* 414 */         BlockSection section = blockChunk.getSectionAtIndex(sectionIndex);
/*     */         
/* 416 */         boolean skipInsideSection = (section.getMaximumHitboxExtent() <= 0.0D);
/*     */         
/* 418 */         int sectionYBlock = sectionIndex << 5;
/*     */         
/* 420 */         for (int yInSection = 0; yInSection < 32; yInSection++) {
/* 421 */           int y = sectionYBlock | yInSection;
/* 422 */           for (int i = -1; i < 33; i++) {
/* 423 */             for (int z = -1; z < 33; z++) {
/*     */ 
/*     */               
/* 426 */               if (i < 1 || i >= 31 || y < 1 || y >= 319 || z < 1 || z >= 31)
/*     */               {
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/* 432 */                 if (i < 0 || i >= 32 || y < 0 || y >= 320 || z < 0 || z >= 32) {
/*     */ 
/*     */                   
/* 435 */                   int worldX = (chunk.getX() << 5) + i;
/* 436 */                   int worldZ = (chunk.getZ() << 5) + z;
/* 437 */                   WorldChunk neighbourChunk = accessor.getChunkIfInMemory(ChunkUtil.indexChunkFromBlock(worldX, worldZ));
/* 438 */                   if (neighbourChunk != null) {
/*     */                     
/* 440 */                     BlockSection neighbourSection = neighbourChunk.getBlockChunk().getSectionAtBlockY(y);
/*     */                     
/* 442 */                     if (neighbourSection.getMaximumHitboxExtent() > 0.0D) {
/*     */                       
/* 444 */                       int blockId = neighbourSection.get(i, y, z);
/* 445 */                       if (blockId != 0) {
/* 446 */                         BlockType blockType = (BlockType)blockTypeAssetMap.getAsset(blockId);
/* 447 */                         int rotation = neighbourSection.getRotationIndex(i, y, z);
/*     */                         
/* 449 */                         BlockTypeModule.breakOrSetFillerBlocks(blockTypeAssetMap, hitboxAssetMap, (ChunkAccessor<?>)accessor, (BlockAccessor)chunk, i, y, z, blockType, rotation);
/*     */                       } 
/*     */                     } 
/*     */                   } 
/* 453 */                 } else if (!skipInsideSection) {
/*     */                   
/* 455 */                   int blockId = section.get(i, y, z);
/* 456 */                   if (blockId != 0) {
/* 457 */                     BlockType blockType = (BlockType)blockTypeAssetMap.getAsset(blockId);
/* 458 */                     int rotation = section.getRotationIndex(i, y, z);
/*     */                     
/* 460 */                     BlockTypeModule.breakOrSetFillerBlocks(blockTypeAssetMap, hitboxAssetMap, (ChunkAccessor<?>)accessor, (BlockAccessor)chunk, i, y, z, blockType, rotation);
/*     */                   } 
/*     */                 } 
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     public void onEntityRemove(@Nonnull Ref<ChunkStore> ref, @Nonnull RemoveReason reason, @Nonnull Store<ChunkStore> store, @Nonnull CommandBuffer<ChunkStore> commandBuffer) {}
/*     */   }
/*     */   
/*     */   @Deprecated(forRemoval = true)
/*     */   private static class MigrateLegacySections extends ChunkColumnMigrationSystem {
/* 475 */     private final Query<ChunkStore> QUERY = (Query<ChunkStore>)Query.and(new Query[] { (Query)ChunkColumn.getComponentType(), (Query)BlockChunk.getComponentType() });
/* 476 */     private final Set<Dependency<ChunkStore>> DEPENDENCIES = (Set)Set.of(new SystemDependency(Order.BEFORE, LegacyModule.MigrateLegacySections.class), new SystemDependency(Order.AFTER, ChunkSystems.OnNewChunk.class), 
/*     */ 
/*     */         
/* 479 */         RootDependency.first());
/*     */ 
/*     */ 
/*     */     
/*     */     public void onEntityAdd(@Nonnull Holder<ChunkStore> holder, @Nonnull AddReason reason, @Nonnull Store<ChunkStore> store) {
/* 484 */       ChunkColumn column = (ChunkColumn)holder.getComponent(ChunkColumn.getComponentType());
/* 485 */       assert column != null;
/* 486 */       BlockChunk blockChunk = (BlockChunk)holder.getComponent(BlockChunk.getComponentType());
/* 487 */       assert blockChunk != null;
/*     */       
/* 489 */       Holder[] arrayOfHolder = column.getSectionHolders();
/* 490 */       BlockSection[] legacySections = blockChunk.getMigratedSections();
/*     */       
/* 492 */       if (legacySections == null)
/*     */         return; 
/* 494 */       for (int i = 0; i < arrayOfHolder.length; i++) {
/* 495 */         Holder<ChunkStore> section = arrayOfHolder[i];
/* 496 */         BlockSection paletteSection = legacySections[i];
/* 497 */         if (section != null && paletteSection != null) {
/*     */           
/* 499 */           BlockPhysics phys = paletteSection.takeMigratedDecoBlocks();
/* 500 */           if (phys != null) {
/* 501 */             section.putComponent(BlockPhysics.getComponentType(), (Component)phys);
/*     */             
/* 503 */             blockChunk.markNeedsSaving();
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void onEntityRemoved(@Nonnull Holder<ChunkStore> holder, @Nonnull RemoveReason reason, @Nonnull Store<ChunkStore> store) {}
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Query<ChunkStore> getQuery() {
/* 515 */       return this.QUERY;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Set<Dependency<ChunkStore>> getDependencies() {
/* 521 */       return this.DEPENDENCIES;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\blocktype\BlockTypeModule.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */