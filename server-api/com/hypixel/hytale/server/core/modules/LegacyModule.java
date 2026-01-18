/*     */ package com.hypixel.hytale.server.core.modules;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.common.plugin.PluginManifest;
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.Archetype;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.dependency.Dependency;
/*     */ import com.hypixel.hytale.component.dependency.RootDependency;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.ISystem;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.server.core.modules.migrations.ChunkColumnMigrationSystem;
/*     */ import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockComponentChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.ChunkColumn;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.EntityChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.environment.EnvironmentChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.BlockSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.ChunkSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.FluidSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.blockpositions.BlockPositionProvider;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.systems.ChunkSystems;
/*     */ import com.hypixel.hytale.server.core.universe.world.meta.BlockState;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class LegacyModule extends JavaPlugin {
/*  40 */   public static final PluginManifest MANIFEST = PluginManifest.corePlugin(LegacyModule.class)
/*  41 */     .build(); private static LegacyModule instance; private ComponentType<ChunkStore, WorldChunk> worldChunkComponentType; private ComponentType<ChunkStore, BlockChunk> blockChunkComponentType;
/*     */   private ComponentType<ChunkStore, EntityChunk> entityChunkComponentType;
/*     */   private ComponentType<ChunkStore, BlockComponentChunk> blockComponentChunkComponentType;
/*     */   
/*     */   public static LegacyModule get() {
/*  46 */     return instance;
/*     */   }
/*     */ 
/*     */   
/*     */   private ComponentType<ChunkStore, EnvironmentChunk> environmentChunkComponentType;
/*     */   
/*     */   private ComponentType<ChunkStore, ChunkColumn> chunkColumnComponentType;
/*     */   
/*     */   private ComponentType<ChunkStore, ChunkSection> chunkSectionComponentType;
/*     */   
/*     */   private ComponentType<ChunkStore, BlockSection> blockSectionComponentType;
/*     */   
/*     */   private ComponentType<ChunkStore, FluidSection> fluidSectionComponentType;
/*     */   private ComponentType<ChunkStore, BlockPositionProvider> blockPositionProviderComponentType;
/*     */   
/*     */   public LegacyModule(@Nonnull JavaPluginInit init) {
/*  62 */     super(init);
/*  63 */     instance = this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setup() {
/*  68 */     this.worldChunkComponentType = getChunkStoreRegistry().registerComponent(WorldChunk.class, "WorldChunk", WorldChunk.CODEC);
/*  69 */     this.blockChunkComponentType = getChunkStoreRegistry().registerComponent(BlockChunk.class, "BlockChunk", BlockChunk.CODEC);
/*  70 */     this.entityChunkComponentType = getChunkStoreRegistry().registerComponent(EntityChunk.class, "EntityChunk", EntityChunk.CODEC);
/*  71 */     this.blockComponentChunkComponentType = getChunkStoreRegistry().registerComponent(BlockComponentChunk.class, "BlockComponentChunk", BlockComponentChunk.CODEC);
/*  72 */     this.environmentChunkComponentType = getChunkStoreRegistry().registerComponent(EnvironmentChunk.class, "EnvironmentChunk", EnvironmentChunk.CODEC);
/*  73 */     this.chunkColumnComponentType = getChunkStoreRegistry().registerComponent(ChunkColumn.class, "ChunkColumn", ChunkColumn.CODEC);
/*     */     
/*  75 */     this.chunkSectionComponentType = getChunkStoreRegistry().registerComponent(ChunkSection.class, "ChunkSection", ChunkSection.CODEC);
/*  76 */     this.blockSectionComponentType = getChunkStoreRegistry().registerComponent(BlockSection.class, "Block", BlockSection.CODEC);
/*  77 */     this.fluidSectionComponentType = getChunkStoreRegistry().registerComponent(FluidSection.class, "Fluid", FluidSection.CODEC);
/*  78 */     this.blockPositionProviderComponentType = getChunkStoreRegistry().registerComponent(BlockPositionProvider.class, () -> {
/*     */           throw new UnsupportedOperationException("BlockPositionProvider cannot be constructed");
/*     */         });
/*     */     
/*  82 */     getChunkStoreRegistry().registerSystem((ISystem)new ChunkSystems.OnNewChunk());
/*  83 */     getChunkStoreRegistry().registerSystem((ISystem)new ChunkSystems.OnChunkLoad());
/*  84 */     getChunkStoreRegistry().registerSystem((ISystem)new ChunkSystems.OnNonTicking());
/*  85 */     getChunkStoreRegistry().registerSystem((ISystem)new ChunkSystems.EnsureBlockSection());
/*  86 */     getChunkStoreRegistry().registerSystem((ISystem)new MigrateLegacySections());
/*  87 */     getChunkStoreRegistry().registerSystem((ISystem)new ChunkSystems.LoadBlockSection());
/*  88 */     getChunkStoreRegistry().registerSystem((ISystem)new ChunkSystems.ReplicateChanges());
/*     */     
/*  90 */     getChunkStoreRegistry().registerSystem((ISystem)new BlockChunk.LoadBlockChunkPacketSystem(this.blockChunkComponentType));
/*     */     
/*  92 */     getChunkStoreRegistry().registerSystem((ISystem)new EntityChunk.EntityChunkLoadingSystem());
/*     */     
/*  94 */     getChunkStoreRegistry().registerSystem((ISystem)new BlockComponentChunk.BlockComponentChunkLoadingSystem());
/*     */     
/*  96 */     getChunkStoreRegistry().registerSystem((ISystem)new BlockComponentChunk.LoadBlockComponentPacketSystem(this.blockComponentChunkComponentType));
/*  97 */     getChunkStoreRegistry().registerSystem((ISystem)new BlockComponentChunk.UnloadBlockComponentPacketSystem(this.blockComponentChunkComponentType));
/*     */     
/*  99 */     ComponentType<ChunkStore, LegacyBlockStateChunk> legacyBlockStateComponentType = getChunkStoreRegistry().registerComponent(LegacyBlockStateChunk.class, "BlockStateChunk", LegacyBlockStateChunk.CODEC, true);
/* 100 */     getChunkStoreRegistry().registerSystem((ISystem)new MigrateLegacyBlockStateChunkSystem(legacyBlockStateComponentType, this.blockComponentChunkComponentType));
/*     */   }
/*     */   
/*     */   public ComponentType<ChunkStore, WorldChunk> getWorldChunkComponentType() {
/* 104 */     return this.worldChunkComponentType;
/*     */   }
/*     */   
/*     */   public ComponentType<ChunkStore, BlockChunk> getBlockChunkComponentType() {
/* 108 */     return this.blockChunkComponentType;
/*     */   }
/*     */   
/*     */   public ComponentType<ChunkStore, EntityChunk> getEntityChunkComponentType() {
/* 112 */     return this.entityChunkComponentType;
/*     */   }
/*     */   
/*     */   public ComponentType<ChunkStore, BlockComponentChunk> getBlockComponentChunkComponentType() {
/* 116 */     return this.blockComponentChunkComponentType;
/*     */   }
/*     */   
/*     */   public ComponentType<ChunkStore, EnvironmentChunk> getEnvironmentChunkComponentType() {
/* 120 */     return this.environmentChunkComponentType;
/*     */   }
/*     */   
/*     */   public ComponentType<ChunkStore, ChunkColumn> getChunkColumnComponentType() {
/* 124 */     return this.chunkColumnComponentType;
/*     */   }
/*     */   
/*     */   public ComponentType<ChunkStore, ChunkSection> getChunkSectionComponentType() {
/* 128 */     return this.chunkSectionComponentType;
/*     */   }
/*     */   
/*     */   public ComponentType<ChunkStore, BlockSection> getBlockSectionComponentType() {
/* 132 */     return this.blockSectionComponentType;
/*     */   }
/*     */   
/*     */   public ComponentType<ChunkStore, FluidSection> getFluidSectionComponentType() {
/* 136 */     return this.fluidSectionComponentType;
/*     */   }
/*     */   
/*     */   public ComponentType<ChunkStore, BlockPositionProvider> getBlockPositionProviderComponentType() {
/* 140 */     return this.blockPositionProviderComponentType;
/*     */   }
/*     */ 
/*     */   
/*     */   private static class LegacyBlockStateChunk
/*     */     implements Component<ChunkStore>
/*     */   {
/*     */     public static final BuilderCodec<LegacyBlockStateChunk> CODEC;
/*     */     
/*     */     public Holder<ChunkStore>[] holders;
/*     */     
/*     */     static {
/* 152 */       CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(LegacyBlockStateChunk.class, LegacyBlockStateChunk::new).addField(new KeyedCodec("States", (Codec)new ArrayCodec((Codec)new StoredCodec(ChunkStore.HOLDER_CODEC_KEY), x$0 -> new Holder[x$0])), (entityChunk, array) -> entityChunk.holders = (Holder<ChunkStore>[])array, entityChunk -> { throw new UnsupportedOperationException("Serialise is not allowed for BlockStateChunk"); })).build();
/*     */     }
/*     */ 
/*     */     
/*     */     public LegacyBlockStateChunk() {}
/*     */ 
/*     */     
/*     */     public LegacyBlockStateChunk(Holder<ChunkStore>[] holders) {
/* 160 */       this.holders = holders;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Component<ChunkStore> clone() {
/* 167 */       Holder[] arrayOfHolder = new Holder[this.holders.length];
/* 168 */       for (int i = 0; i < this.holders.length; i++) {
/* 169 */         arrayOfHolder[i] = this.holders[i].clone();
/*     */       }
/* 171 */       return new LegacyBlockStateChunk((Holder<ChunkStore>[])arrayOfHolder);
/*     */     }
/*     */   }
/*     */   
/*     */   @Deprecated(forRemoval = true)
/*     */   public static class MigrateLegacySections
/*     */     extends ChunkColumnMigrationSystem {
/* 178 */     private final Query<ChunkStore> QUERY = (Query<ChunkStore>)Query.and(new Query[] { (Query)ChunkColumn.getComponentType(), (Query)BlockChunk.getComponentType() });
/* 179 */     private final Set<Dependency<ChunkStore>> DEPENDENCIES = (Set)Set.of(new SystemDependency(Order.AFTER, ChunkSystems.OnNewChunk.class), 
/*     */         
/* 181 */         RootDependency.first());
/*     */ 
/*     */ 
/*     */     
/*     */     public void onEntityAdd(@Nonnull Holder<ChunkStore> holder, @Nonnull AddReason reason, @Nonnull Store<ChunkStore> store) {
/* 186 */       ChunkColumn column = (ChunkColumn)holder.getComponent(ChunkColumn.getComponentType());
/* 187 */       assert column != null;
/* 188 */       BlockChunk blockChunk = (BlockChunk)holder.getComponent(BlockChunk.getComponentType());
/* 189 */       assert blockChunk != null;
/*     */       
/* 191 */       Holder[] arrayOfHolder = column.getSectionHolders();
/* 192 */       BlockSection[] migratedSections = blockChunk.takeMigratedSections();
/* 193 */       if (migratedSections != null) {
/* 194 */         for (int i = 0; i < arrayOfHolder.length; i++) {
/* 195 */           Holder<ChunkStore> section = arrayOfHolder[i];
/* 196 */           BlockSection blockSection = migratedSections[i];
/* 197 */           if (section != null && blockSection != null) {
/*     */             
/* 199 */             section.putComponent(BlockSection.getComponentType(), (Component)blockSection);
/*     */             
/* 201 */             blockChunk.markNeedsSaving();
/*     */           } 
/*     */         } 
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void onEntityRemoved(@Nonnull Holder<ChunkStore> holder, @Nonnull RemoveReason reason, @Nonnull Store<ChunkStore> store) {}
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Query<ChunkStore> getQuery() {
/* 214 */       return this.QUERY;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Set<Dependency<ChunkStore>> getDependencies() {
/* 220 */       return this.DEPENDENCIES;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class MigrateLegacyBlockStateChunkSystem extends ChunkColumnMigrationSystem {
/* 225 */     private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */     private final ComponentType<ChunkStore, LegacyModule.LegacyBlockStateChunk> legacyComponentType;
/*     */     private final ComponentType<ChunkStore, BlockComponentChunk> componentType;
/*     */     private final Archetype<ChunkStore> archetype;
/*     */     
/*     */     public MigrateLegacyBlockStateChunkSystem(ComponentType<ChunkStore, LegacyModule.LegacyBlockStateChunk> legacyComponentType, ComponentType<ChunkStore, BlockComponentChunk> componentType) {
/* 231 */       this.legacyComponentType = legacyComponentType;
/* 232 */       this.componentType = componentType;
/* 233 */       this.archetype = Archetype.of(new ComponentType[] { legacyComponentType, WorldChunk.getComponentType() });
/*     */     }
/*     */ 
/*     */     
/*     */     public Query<ChunkStore> getQuery() {
/* 238 */       return (Query<ChunkStore>)this.archetype;
/*     */     }
/*     */ 
/*     */     
/*     */     public void onEntityAdd(@Nonnull Holder<ChunkStore> holder, @Nonnull AddReason reason, @Nonnull Store<ChunkStore> store) {
/* 243 */       LegacyModule.LegacyBlockStateChunk component = (LegacyModule.LegacyBlockStateChunk)holder.getComponent(this.legacyComponentType);
/* 244 */       assert component != null;
/*     */       
/* 246 */       holder.removeComponent(this.legacyComponentType);
/*     */       
/* 248 */       Int2ObjectOpenHashMap<Holder<ChunkStore>> holders = new Int2ObjectOpenHashMap();
/* 249 */       for (Holder<ChunkStore> blockComponentHolder : component.holders) {
/* 250 */         BlockState blockState = BlockState.getBlockState(blockComponentHolder);
/* 251 */         Vector3i position = blockState.__internal_getPosition();
/* 252 */         if (position == null) {
/* 253 */           LOGGER.at(Level.SEVERE).log("Skipping migration for BlockState with null position!", blockComponentHolder);
/*     */         } else {
/*     */           
/* 256 */           holders.put(blockState.getIndex(), blockComponentHolder);
/*     */         } 
/* 258 */       }  BlockComponentChunk blockComponentChunk = new BlockComponentChunk((Int2ObjectMap)holders, (Int2ObjectMap)new Int2ObjectOpenHashMap());
/* 259 */       holder.addComponent(this.componentType, (Component)blockComponentChunk);
/*     */       
/* 261 */       ((WorldChunk)holder.getComponent(WorldChunk.getComponentType())).setBlockComponentChunk(blockComponentChunk);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void onEntityRemoved(@Nonnull Holder<ChunkStore> holder, @Nonnull RemoveReason reason, @Nonnull Store<ChunkStore> store) {}
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Set<Dependency<ChunkStore>> getDependencies() {
/* 271 */       return RootDependency.firstSet();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\LegacyModule.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */