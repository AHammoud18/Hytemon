/*     */ package com.hypixel.hytale.server.core.modules.block;
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.Resource;
/*     */ import com.hypixel.hytale.component.ResourceType;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.data.unknown.UnknownComponents;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.ISystem;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.StateData;
/*     */ import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockComponentChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.ChunkColumn;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.BlockSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.events.AddWorldEvent;
/*     */ import com.hypixel.hytale.server.core.universe.world.events.ChunkPreLoadProcessEvent;
/*     */ import com.hypixel.hytale.server.core.universe.world.meta.BlockState;
/*     */ import com.hypixel.hytale.server.core.universe.world.meta.state.BlockMapMarker;
/*     */ import com.hypixel.hytale.server.core.universe.world.meta.state.BlockMapMarkersResource;
/*     */ import com.hypixel.hytale.server.core.universe.world.meta.state.LaunchPad;
/*     */ import com.hypixel.hytale.server.core.universe.world.meta.state.RespawnBlock;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class BlockModule extends JavaPlugin {
/*  36 */   public static final PluginManifest MANIFEST = PluginManifest.corePlugin(BlockModule.class)
/*  37 */     .depends(new Class[] { LegacyModule.class
/*  38 */       }).build(); private static BlockModule instance;
/*     */   private SystemType<ChunkStore, MigrationSystem> migrationSystemType;
/*     */   private ComponentType<ChunkStore, LaunchPad> launchPadComponentType;
/*     */   private ComponentType<ChunkStore, RespawnBlock> respawnBlockComponentType;
/*     */   private ComponentType<ChunkStore, BlockMapMarker> blockMapMarkerComponentType;
/*     */   private ResourceType<ChunkStore, BlockMapMarkersResource> blockMapMarkersResourceType;
/*     */   private ComponentType<ChunkStore, BlockStateInfo> blockStateInfoComponentType;
/*     */   private ResourceType<ChunkStore, BlockStateInfoNeedRebuild> blockStateInfoNeedRebuildResourceType;
/*     */   
/*     */   public static BlockModule get() {
/*  48 */     return instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockModule(@Nonnull JavaPluginInit init) {
/*  55 */     super(init);
/*  56 */     instance = this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setup() {
/*  61 */     this.migrationSystemType = getChunkStoreRegistry().registerSystemType(MigrationSystem.class);
/*     */     
/*  63 */     this.blockStateInfoComponentType = getChunkStoreRegistry().registerComponent(BlockStateInfo.class, () -> {
/*     */           throw new UnsupportedOperationException();
/*     */         });
/*     */     
/*  67 */     getChunkStoreRegistry().registerSystem((ISystem)new BlockStateInfoRefSystem(this.blockStateInfoComponentType));
/*     */ 
/*     */     
/*  70 */     this.launchPadComponentType = getChunkStoreRegistry().registerComponent(LaunchPad.class, "LaunchPad", LaunchPad.CODEC);
/*  71 */     getChunkStoreRegistry().registerSystem((ISystem)new MigrateLaunchPad());
/*     */     
/*  73 */     this.respawnBlockComponentType = getChunkStoreRegistry().registerComponent(RespawnBlock.class, "RespawnBlock", RespawnBlock.CODEC);
/*  74 */     getChunkStoreRegistry().registerSystem((ISystem)new RespawnBlock.OnRemove());
/*     */     
/*  76 */     this.blockMapMarkerComponentType = getChunkStoreRegistry().registerComponent(BlockMapMarker.class, "BlockMapMarker", BlockMapMarker.CODEC);
/*  77 */     this.blockMapMarkersResourceType = getChunkStoreRegistry().registerResource(BlockMapMarkersResource.class, "BlockMapMarkers", BlockMapMarkersResource.CODEC);
/*  78 */     getChunkStoreRegistry().registerSystem((ISystem)new BlockMapMarker.OnAddRemove());
/*  79 */     getEventRegistry().registerGlobal(AddWorldEvent.class, event -> event.getWorld().getWorldMapManager().getMarkerProviders().put("blockMapMarkers", BlockMapMarker.MarkerProvider.INSTANCE));
/*     */     
/*  81 */     this.blockStateInfoNeedRebuildResourceType = getChunkStoreRegistry().registerResource(BlockStateInfoNeedRebuild.class, BlockStateInfoNeedRebuild::new);
/*     */     
/*  83 */     getEventRegistry().registerGlobal(EventPriority.EARLY, ChunkPreLoadProcessEvent.class, BlockModule::onChunkPreLoadProcessEnsureBlockEntity);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static Ref<ChunkStore> ensureBlockEntity(WorldChunk chunk, int x, int y, int z) {
/*  92 */     Ref<ChunkStore> blockRef = chunk.getBlockComponentEntity(x, y, z);
/*  93 */     if (blockRef != null) {
/*  94 */       return blockRef;
/*     */     }
/*  96 */     BlockType blockType = chunk.getBlockType(x, y, z);
/*  97 */     if (blockType == null) return null;
/*     */     
/*  99 */     if (blockType.getBlockEntity() != null) {
/* 100 */       Holder<ChunkStore> data = blockType.getBlockEntity().clone();
/* 101 */       data.putComponent(BlockStateInfo.getComponentType(), new BlockStateInfo(ChunkUtil.indexBlockInColumn(x, y, z), chunk.getReference()));
/* 102 */       blockRef = chunk.getWorld().getChunkStore().getStore().addEntity(data, AddReason.SPAWN);
/* 103 */       return blockRef;
/*     */     } 
/* 105 */     BlockState state = BlockState.ensureState(chunk, x, y, z);
/* 106 */     if (state != null) {
/* 107 */       return state.getReference();
/*     */     }
/* 109 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private static void onChunkPreLoadProcessEnsureBlockEntity(@Nonnull ChunkPreLoadProcessEvent event) {
/* 114 */     if (!event.isNewlyGenerated())
/*     */       return; 
/* 116 */     BlockTypeAssetMap<String, BlockType> blockTypeAssetMap = BlockType.getAssetMap();
/*     */     
/* 118 */     Holder<ChunkStore> holder = event.getHolder();
/* 119 */     WorldChunk chunk = event.getChunk();
/*     */     
/* 121 */     ChunkColumn column = (ChunkColumn)holder.getComponent(ChunkColumn.getComponentType());
/* 122 */     if (column == null)
/*     */       return; 
/* 124 */     Holder[] arrayOfHolder = column.getSectionHolders();
/* 125 */     if (arrayOfHolder == null)
/*     */       return; 
/* 127 */     BlockComponentChunk blockComponentModule = (BlockComponentChunk)holder.getComponent(BlockComponentChunk.getComponentType());
/*     */     
/* 129 */     for (int sectionIndex = 0; sectionIndex < 10; sectionIndex++) {
/* 130 */       BlockSection section = (BlockSection)arrayOfHolder[sectionIndex].ensureAndGetComponent(BlockSection.getComponentType());
/* 131 */       if (!section.isSolidAir()) {
/*     */         
/* 133 */         int sectionYBlock = sectionIndex << 5;
/*     */         
/* 135 */         for (int sectionY = 0; sectionY < 32; sectionY++) {
/* 136 */           int y = sectionYBlock | sectionY;
/* 137 */           for (int z = 0; z < 32; z++) {
/* 138 */             for (int x = 0; x < 32; x++) {
/*     */               
/* 140 */               int blockId = section.get(x, y, z);
/* 141 */               BlockType blockType = (BlockType)blockTypeAssetMap.getAsset(blockId);
/* 142 */               if (blockType == null || blockType.isUnknown() || 
/* 143 */                 section.getFiller(x, y, z) != 0)
/*     */                 continue; 
/* 145 */               int index = ChunkUtil.indexBlockInColumn(x, y, z);
/*     */               
/* 147 */               if (blockType.getBlockEntity() != null) {
/*     */                 
/* 149 */                 if (blockComponentModule.getEntityHolder(index) != null)
/*     */                   continue; 
/* 151 */                 blockComponentModule.addEntityHolder(index, blockType.getBlockEntity().clone());
/*     */               } 
/*     */ 
/*     */ 
/*     */               
/* 156 */               StateData state = blockType.getState();
/* 157 */               if (state != null && state.getId() != null)
/*     */               {
/* 159 */                 if (blockComponentModule.getEntityHolder(index) == null) {
/*     */ 
/*     */                   
/* 162 */                   Vector3i position = new Vector3i(x, y, z);
/* 163 */                   BlockState blockState = BlockStateModule.get().createBlockState(state.getId(), chunk, position, blockType);
/* 164 */                   if (blockState != null)
/*     */                   {
/* 166 */                     blockComponentModule.addEntityHolder(index, blockState.toHolder()); } 
/*     */                 }  } 
/*     */               continue;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   } public SystemType<ChunkStore, MigrationSystem> getMigrationSystemType() {
/* 175 */     return this.migrationSystemType;
/*     */   }
/*     */   
/*     */   public ComponentType<ChunkStore, BlockStateInfo> getBlockStateInfoComponentType() {
/* 179 */     return this.blockStateInfoComponentType;
/*     */   }
/*     */   
/*     */   public ComponentType<ChunkStore, LaunchPad> getLaunchPadComponentType() {
/* 183 */     return this.launchPadComponentType;
/*     */   }
/*     */   
/*     */   public ComponentType<ChunkStore, RespawnBlock> getRespawnBlockComponentType() {
/* 187 */     return this.respawnBlockComponentType;
/*     */   }
/*     */   
/*     */   public ComponentType<ChunkStore, BlockMapMarker> getBlockMapMarkerComponentType() {
/* 191 */     return this.blockMapMarkerComponentType;
/*     */   }
/*     */   
/*     */   public ResourceType<ChunkStore, BlockMapMarkersResource> getBlockMapMarkersResourceType() {
/* 195 */     return this.blockMapMarkersResourceType;
/*     */   }
/*     */   
/*     */   public ResourceType<ChunkStore, BlockStateInfoNeedRebuild> getBlockStateInfoNeedRebuildResourceType() {
/* 199 */     return this.blockStateInfoNeedRebuildResourceType;
/*     */   }
/*     */   
/*     */   public static class BlockStateInfoNeedRebuild implements Resource<ChunkStore> { private boolean needRebuild;
/*     */     
/*     */     public static ResourceType<ChunkStore, BlockStateInfoNeedRebuild> getResourceType() {
/* 205 */       return BlockModule.get().getBlockStateInfoNeedRebuildResourceType();
/*     */     }
/*     */     
/*     */     public BlockStateInfoNeedRebuild() {
/* 209 */       this.needRebuild = false;
/*     */     }
/*     */     
/*     */     public BlockStateInfoNeedRebuild(boolean needRebuild) {
/* 213 */       this.needRebuild = needRebuild;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean invalidateAndReturnIfNeedRebuild() {
/* 219 */       if (this.needRebuild) {
/* 220 */         this.needRebuild = false;
/* 221 */         return true;
/*     */       } 
/* 223 */       return false;
/*     */     }
/*     */     
/*     */     public void markAsNeedRebuild() {
/* 227 */       this.needRebuild = true;
/*     */     }
/*     */ 
/*     */     
/*     */     public Resource<ChunkStore> clone() {
/* 232 */       return new BlockStateInfoNeedRebuild(this.needRebuild);
/*     */     } }
/*     */   public static class BlockStateInfo implements Component<ChunkStore> { private final int index; @Nonnull
/*     */     private final Ref<ChunkStore> chunkRef;
/*     */     
/*     */     public static ComponentType<ChunkStore, BlockStateInfo> getComponentType() {
/* 238 */       return BlockModule.get().getBlockStateInfoComponentType();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public BlockStateInfo(int index, @Nonnull Ref<ChunkStore> chunkRef) {
/* 250 */       Objects.requireNonNull(chunkRef);
/* 251 */       this.index = index;
/* 252 */       this.chunkRef = chunkRef;
/*     */     }
/*     */     
/*     */     public int getIndex() {
/* 256 */       return this.index;
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public Ref<ChunkStore> getChunkRef() {
/* 261 */       return this.chunkRef;
/*     */     }
/*     */     
/*     */     public void markNeedsSaving() {
/* 265 */       if (this.chunkRef == null || !this.chunkRef.isValid())
/*     */         return; 
/* 267 */       BlockComponentChunk blockComponentChunk = (BlockComponentChunk)this.chunkRef.getStore().getComponent(this.chunkRef, BlockComponentChunk.getComponentType());
/* 268 */       if (blockComponentChunk != null) blockComponentChunk.markNeedsSaving();
/*     */     
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public Component<ChunkStore> clone() {
/* 274 */       return new BlockStateInfo(this.index, this.chunkRef);
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   public static abstract class MigrationSystem
/*     */     extends HolderSystem<ChunkStore> {}
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static Ref<ChunkStore> getBlockEntity(@Nonnull World world, int x, int y, int z) {
/* 285 */     ChunkStore chunkStore = world.getChunkStore();
/* 286 */     Ref<ChunkStore> chunkRef = chunkStore.getChunkReference(ChunkUtil.indexChunkFromBlock(x, z));
/* 287 */     if (chunkRef == null) return null;
/*     */     
/* 289 */     BlockComponentChunk blockComponentChunk = (BlockComponentChunk)chunkStore.getStore().getComponent(chunkRef, BlockComponentChunk.getComponentType());
/* 290 */     if (blockComponentChunk == null) return null;
/*     */     
/* 292 */     int blockIndex = ChunkUtil.indexBlockInColumn(x, y, z);
/* 293 */     Ref<ChunkStore> blockRef = blockComponentChunk.getEntityReference(blockIndex);
/* 294 */     if (blockRef == null || !blockRef.isValid()) {
/* 295 */       return null;
/*     */     }
/* 297 */     return blockRef;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public <T extends Component<ChunkStore>> T getComponent(ComponentType<ChunkStore, T> componentType, World world, int x, int y, int z) {
/* 302 */     Store<ChunkStore> chunkStore = world.getChunkStore().getStore();
/*     */     
/* 304 */     Ref<ChunkStore> chunkRef = world.getChunkStore().getChunkReference(ChunkUtil.indexChunkFromBlock(x, z));
/* 305 */     BlockComponentChunk blockComponentChunk = (BlockComponentChunk)chunkStore.getComponent(chunkRef, BlockComponentChunk.getComponentType());
/* 306 */     if (blockComponentChunk == null) return null;
/*     */     
/* 308 */     int blockIndex = ChunkUtil.indexBlockInColumn(x, y, z);
/* 309 */     Ref<ChunkStore> blockRef = blockComponentChunk.getEntityReference(blockIndex);
/* 310 */     if (blockRef == null || !blockRef.isValid()) return null;
/*     */     
/* 312 */     return (T)chunkStore.getComponent(blockRef, componentType);
/*     */   }
/*     */   
/*     */   public static class BlockStateInfoRefSystem extends RefSystem<ChunkStore> {
/*     */     private final ComponentType<ChunkStore, BlockModule.BlockStateInfo> componentType;
/*     */     
/*     */     public BlockStateInfoRefSystem(ComponentType<ChunkStore, BlockModule.BlockStateInfo> componentType) {
/* 319 */       this.componentType = componentType;
/*     */     }
/*     */ 
/*     */     
/*     */     public Query<ChunkStore> getQuery() {
/* 324 */       return (Query)this.componentType;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void onEntityAdded(@Nonnull Ref<ChunkStore> ref, @Nonnull AddReason reason, @Nonnull Store<ChunkStore> store, @Nonnull CommandBuffer<ChunkStore> commandBuffer) {
/*     */       // Byte code:
/*     */       //   0: aload #4
/*     */       //   2: aload_1
/*     */       //   3: aload_0
/*     */       //   4: getfield componentType : Lcom/hypixel/hytale/component/ComponentType;
/*     */       //   7: invokevirtual getComponent : (Lcom/hypixel/hytale/component/Ref;Lcom/hypixel/hytale/component/ComponentType;)Lcom/hypixel/hytale/component/Component;
/*     */       //   10: checkcast com/hypixel/hytale/server/core/modules/block/BlockModule$BlockStateInfo
/*     */       //   13: astore #5
/*     */       //   15: aload #5
/*     */       //   17: getfield chunkRef : Lcom/hypixel/hytale/component/Ref;
/*     */       //   20: astore #6
/*     */       //   22: aload #6
/*     */       //   24: ifnull -> 101
/*     */       //   27: aload #4
/*     */       //   29: aload #6
/*     */       //   31: invokestatic getComponentType : ()Lcom/hypixel/hytale/component/ComponentType;
/*     */       //   34: invokevirtual getComponent : (Lcom/hypixel/hytale/component/Ref;Lcom/hypixel/hytale/component/ComponentType;)Lcom/hypixel/hytale/component/Component;
/*     */       //   37: checkcast com/hypixel/hytale/server/core/universe/world/chunk/BlockComponentChunk
/*     */       //   40: astore #7
/*     */       //   42: getstatic com/hypixel/hytale/server/core/modules/block/BlockModule$1.$SwitchMap$com$hypixel$hytale$component$AddReason : [I
/*     */       //   45: aload_2
/*     */       //   46: invokevirtual ordinal : ()I
/*     */       //   49: iaload
/*     */       //   50: lookupswitch default -> 101, 1 -> 76, 2 -> 90
/*     */       //   76: aload #7
/*     */       //   78: aload #5
/*     */       //   80: invokevirtual getIndex : ()I
/*     */       //   83: aload_1
/*     */       //   84: invokevirtual addEntityReference : (ILcom/hypixel/hytale/component/Ref;)V
/*     */       //   87: goto -> 101
/*     */       //   90: aload #7
/*     */       //   92: aload #5
/*     */       //   94: invokevirtual getIndex : ()I
/*     */       //   97: aload_1
/*     */       //   98: invokevirtual loadEntityReference : (ILcom/hypixel/hytale/component/Ref;)V
/*     */       //   101: return
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #329	-> 0
/*     */       //   #330	-> 15
/*     */       //   #331	-> 22
/*     */       //   #332	-> 27
/*     */       //   #333	-> 42
/*     */       //   #334	-> 76
/*     */       //   #335	-> 90
/*     */       //   #338	-> 101
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   42	59	7	blockComponentChunk	Lcom/hypixel/hytale/server/core/universe/world/chunk/BlockComponentChunk;
/*     */       //   0	102	0	this	Lcom/hypixel/hytale/server/core/modules/block/BlockModule$BlockStateInfoRefSystem;
/*     */       //   0	102	1	ref	Lcom/hypixel/hytale/component/Ref;
/*     */       //   0	102	2	reason	Lcom/hypixel/hytale/component/AddReason;
/*     */       //   0	102	3	store	Lcom/hypixel/hytale/component/Store;
/*     */       //   0	102	4	commandBuffer	Lcom/hypixel/hytale/component/CommandBuffer;
/*     */       //   15	87	5	blockState	Lcom/hypixel/hytale/server/core/modules/block/BlockModule$BlockStateInfo;
/*     */       //   22	80	6	chunk	Lcom/hypixel/hytale/component/Ref;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/*     */       //   0	102	1	ref	Lcom/hypixel/hytale/component/Ref<Lcom/hypixel/hytale/server/core/universe/world/storage/ChunkStore;>;
/*     */       //   0	102	3	store	Lcom/hypixel/hytale/component/Store<Lcom/hypixel/hytale/server/core/universe/world/storage/ChunkStore;>;
/*     */       //   0	102	4	commandBuffer	Lcom/hypixel/hytale/component/CommandBuffer<Lcom/hypixel/hytale/server/core/universe/world/storage/ChunkStore;>;
/*     */       //   22	80	6	chunk	Lcom/hypixel/hytale/component/Ref<Lcom/hypixel/hytale/server/core/universe/world/storage/ChunkStore;>;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void onEntityRemove(@Nonnull Ref<ChunkStore> ref, @Nonnull RemoveReason reason, @Nonnull Store<ChunkStore> store, @Nonnull CommandBuffer<ChunkStore> commandBuffer) {
/*     */       // Byte code:
/*     */       //   0: aload #4
/*     */       //   2: aload_1
/*     */       //   3: aload_0
/*     */       //   4: getfield componentType : Lcom/hypixel/hytale/component/ComponentType;
/*     */       //   7: invokevirtual getComponent : (Lcom/hypixel/hytale/component/Ref;Lcom/hypixel/hytale/component/ComponentType;)Lcom/hypixel/hytale/component/Component;
/*     */       //   10: checkcast com/hypixel/hytale/server/core/modules/block/BlockModule$BlockStateInfo
/*     */       //   13: astore #5
/*     */       //   15: aload #5
/*     */       //   17: getfield chunkRef : Lcom/hypixel/hytale/component/Ref;
/*     */       //   20: astore #6
/*     */       //   22: aload #6
/*     */       //   24: ifnull -> 101
/*     */       //   27: aload #4
/*     */       //   29: aload #6
/*     */       //   31: invokestatic getComponentType : ()Lcom/hypixel/hytale/component/ComponentType;
/*     */       //   34: invokevirtual getComponent : (Lcom/hypixel/hytale/component/Ref;Lcom/hypixel/hytale/component/ComponentType;)Lcom/hypixel/hytale/component/Component;
/*     */       //   37: checkcast com/hypixel/hytale/server/core/universe/world/chunk/BlockComponentChunk
/*     */       //   40: astore #7
/*     */       //   42: getstatic com/hypixel/hytale/server/core/modules/block/BlockModule$1.$SwitchMap$com$hypixel$hytale$component$RemoveReason : [I
/*     */       //   45: aload_2
/*     */       //   46: invokevirtual ordinal : ()I
/*     */       //   49: iaload
/*     */       //   50: lookupswitch default -> 101, 1 -> 76, 2 -> 90
/*     */       //   76: aload #7
/*     */       //   78: aload #5
/*     */       //   80: invokevirtual getIndex : ()I
/*     */       //   83: aload_1
/*     */       //   84: invokevirtual removeEntityReference : (ILcom/hypixel/hytale/component/Ref;)V
/*     */       //   87: goto -> 101
/*     */       //   90: aload #7
/*     */       //   92: aload #5
/*     */       //   94: invokevirtual getIndex : ()I
/*     */       //   97: aload_1
/*     */       //   98: invokevirtual unloadEntityReference : (ILcom/hypixel/hytale/component/Ref;)V
/*     */       //   101: return
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #342	-> 0
/*     */       //   #343	-> 15
/*     */       //   #344	-> 22
/*     */       //   #345	-> 27
/*     */       //   #346	-> 42
/*     */       //   #347	-> 76
/*     */       //   #348	-> 90
/*     */       //   #351	-> 101
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   42	59	7	blockComponentChunk	Lcom/hypixel/hytale/server/core/universe/world/chunk/BlockComponentChunk;
/*     */       //   0	102	0	this	Lcom/hypixel/hytale/server/core/modules/block/BlockModule$BlockStateInfoRefSystem;
/*     */       //   0	102	1	ref	Lcom/hypixel/hytale/component/Ref;
/*     */       //   0	102	2	reason	Lcom/hypixel/hytale/component/RemoveReason;
/*     */       //   0	102	3	store	Lcom/hypixel/hytale/component/Store;
/*     */       //   0	102	4	commandBuffer	Lcom/hypixel/hytale/component/CommandBuffer;
/*     */       //   15	87	5	blockState	Lcom/hypixel/hytale/server/core/modules/block/BlockModule$BlockStateInfo;
/*     */       //   22	80	6	chunk	Lcom/hypixel/hytale/component/Ref;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/*     */       //   0	102	1	ref	Lcom/hypixel/hytale/component/Ref<Lcom/hypixel/hytale/server/core/universe/world/storage/ChunkStore;>;
/*     */       //   0	102	3	store	Lcom/hypixel/hytale/component/Store<Lcom/hypixel/hytale/server/core/universe/world/storage/ChunkStore;>;
/*     */       //   0	102	4	commandBuffer	Lcom/hypixel/hytale/component/CommandBuffer<Lcom/hypixel/hytale/server/core/universe/world/storage/ChunkStore;>;
/*     */       //   22	80	6	chunk	Lcom/hypixel/hytale/component/Ref<Lcom/hypixel/hytale/server/core/universe/world/storage/ChunkStore;>;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 356 */       return "BlockStateInfoRefSystem{componentType=" + String.valueOf(this.componentType) + "}";
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated(forRemoval = true)
/*     */   public static class MigrateLaunchPad
/*     */     extends MigrationSystem
/*     */   {
/*     */     public void onEntityAdd(@Nonnull Holder<ChunkStore> holder, @Nonnull AddReason reason, @Nonnull Store<ChunkStore> store) {
/* 366 */       UnknownComponents<ChunkStore> unknown = (UnknownComponents<ChunkStore>)holder.getComponent(ChunkStore.REGISTRY.getUnknownComponentType());
/* 367 */       assert unknown != null;
/* 368 */       LaunchPad launchPad = (LaunchPad)unknown.removeComponent("launchPad", (Codec)LaunchPad.CODEC);
/* 369 */       if (launchPad != null) {
/* 370 */         holder.putComponent(LaunchPad.getComponentType(), (Component)launchPad);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void onEntityRemoved(@Nonnull Holder<ChunkStore> holder, @Nonnull RemoveReason reason, @Nonnull Store<ChunkStore> store) {}
/*     */ 
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public Query<ChunkStore> getQuery() {
/* 382 */       return (Query<ChunkStore>)ChunkStore.REGISTRY.getUnknownComponentType();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\block\BlockModule.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */