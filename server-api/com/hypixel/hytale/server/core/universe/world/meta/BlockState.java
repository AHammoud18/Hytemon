/*     */ package com.hypixel.hytale.server.core.universe.world.meta;
/*     */ 
/*     */ import com.google.common.flogger.StackSize;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.ExtraInfo;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.lookup.ACodecMapCodec;
/*     */ import com.hypixel.hytale.codec.lookup.CodecMapCodec;
/*     */ import com.hypixel.hytale.component.Archetype;
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.StateData;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.meta.state.exceptions.NoSuchBlockStateException;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ import org.bson.BsonDocument;
/*     */ import org.bson.BsonValue;
/*     */ 
/*     */ @Deprecated(forRemoval = true)
/*     */ public abstract class BlockState
/*     */   implements Component<ChunkStore>
/*     */ {
/*  38 */   private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */   
/*  40 */   public static final CodecMapCodec<BlockState> CODEC = new CodecMapCodec("Type");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final BuilderCodec<BlockState> BASE_CODEC;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  51 */     BASE_CODEC = ((BuilderCodec.Builder)BuilderCodec.abstractBuilder(BlockState.class).addField(new KeyedCodec("Position", (Codec)Vector3i.CODEC), (entity, o) -> entity.position = o, entity -> Vector3i.ZERO.equals(entity.position) ? null : entity.position)).build();
/*     */   }
/*  53 */   public static final KeyedCodec<String> TYPE_STRUCTURE = new KeyedCodec("Type", (Codec)Codec.STRING);
/*     */   
/*     */   public static final String OPEN_WINDOW = "OpenWindow";
/*     */   
/*     */   public static final String CLOSE_WINDOW = "CloseWindow";
/*     */   
/*  59 */   final AtomicBoolean initialized = new AtomicBoolean(false);
/*     */   @Nullable
/*     */   private WorldChunk chunk;
/*     */   private Vector3i position;
/*     */   protected Ref<ChunkStore> reference;
/*     */   
/*     */   public void setReference(Ref<ChunkStore> reference) {
/*  66 */     if (this.reference != null && this.reference.isValid()) {
/*  67 */       throw new IllegalArgumentException("Entity already has a valid EntityReference: " + String.valueOf(this.reference) + " new reference " + String.valueOf(reference));
/*     */     }
/*  69 */     this.reference = reference;
/*     */   }
/*     */   
/*     */   public Ref<ChunkStore> getReference() {
/*  73 */     return this.reference;
/*     */   }
/*     */   
/*     */   public void unloadFromWorld() {
/*  77 */     if (this.reference != null && this.reference.isValid()) {
/*  78 */       throw new IllegalArgumentException("Tried to unlock used block state");
/*     */     }
/*     */ 
/*     */     
/*  82 */     this.chunk = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean initialize(BlockType blockType) {
/*  87 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUnload() {}
/*     */   
/*     */   public void validateInitialized() {
/*  94 */     if (!this.initialized.get()) throw new IllegalArgumentException(String.valueOf(this)); 
/*     */   }
/*     */   
/*     */   public int getIndex() {
/*  98 */     return ChunkUtil.indexBlockInColumn(this.position.x, this.position.y, this.position.z);
/*     */   }
/*     */   
/*     */   public void setPosition(WorldChunk chunk, @Nullable Vector3i position) {
/* 102 */     this.chunk = chunk;
/* 103 */     if (position != null) {
/* 104 */       position.assign(position.getX() & 0x1F, position.getY(), position.getZ() & 0x1F);
/* 105 */       if (position.equals(Vector3i.ZERO)) ((HytaleLogger.Api)LOGGER.at(Level.WARNING).withStackTrace(StackSize.FULL)).log("BlockState position set to (0,0,0): %s", this); 
/* 106 */       this.position = position;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setPosition(@Nonnull Vector3i position) {
/* 111 */     position.assign(position.getX() & 0x1F, position.getY(), position.getZ() & 0x1F);
/* 112 */     if (position.equals(Vector3i.ZERO)) ((HytaleLogger.Api)LOGGER.at(Level.WARNING).withStackTrace(StackSize.FULL)).log("BlockState position set to (0,0,0): %s", this); 
/* 113 */     this.position = position;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Vector3i getPosition() {
/* 118 */     return this.position.clone();
/*     */   }
/*     */   
/*     */   public Vector3i __internal_getPosition() {
/* 122 */     return this.position;
/*     */   }
/*     */   
/*     */   public int getBlockX() {
/* 126 */     return this.chunk.getX() << 5 | this.position.getX();
/*     */   }
/*     */   
/*     */   public int getBlockY() {
/* 130 */     return this.position.y;
/*     */   }
/*     */   
/*     */   public int getBlockZ() {
/* 134 */     return this.chunk.getZ() << 5 | this.position.getZ();
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Vector3i getBlockPosition() {
/* 139 */     return new Vector3i(getBlockX(), getBlockY(), getBlockZ());
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d getCenteredBlockPosition() {
/* 144 */     BlockType blockType = getBlockType();
/* 145 */     Vector3d blockCenter = new Vector3d(0.0D, 0.0D, 0.0D);
/* 146 */     blockType.getBlockCenter(getRotationIndex(), blockCenter);
/* 147 */     return blockCenter.add(getBlockX(), getBlockY(), getBlockZ());
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public WorldChunk getChunk() {
/* 152 */     return this.chunk;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public BlockType getBlockType() {
/* 157 */     return getChunk().getBlockType(this.position);
/*     */   }
/*     */   
/*     */   public int getRotationIndex() {
/* 161 */     return getChunk().getRotationIndex(this.position.x, this.position.y, this.position.z);
/*     */   }
/*     */ 
/*     */   
/*     */   public void invalidate() {}
/*     */   
/*     */   public void markNeedsSave() {
/* 168 */     getChunk().markNeedsSaving();
/*     */   }
/*     */   
/*     */   public BsonDocument saveToDocument() {
/* 172 */     return CODEC.encode(this).asDocument();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Component<ChunkStore> clone() {
/* 179 */     BsonDocument document = CODEC.encode(this, ExtraInfo.THREAD_LOCAL.get()).asDocument();
/* 180 */     return (Component<ChunkStore>)CODEC.decode((BsonValue)document, ExtraInfo.THREAD_LOCAL.get());
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Holder<ChunkStore> toHolder() {
/* 186 */     if (this.reference != null && this.reference.isValid() && this.chunk != null) {
/* 187 */       Holder<ChunkStore> holder1 = ChunkStore.REGISTRY.newHolder();
/* 188 */       Store<ChunkStore> componentStore = this.chunk.getWorld().getChunkStore().getStore();
/* 189 */       Archetype<ChunkStore> archetype = componentStore.getArchetype(this.reference);
/* 190 */       for (int i = archetype.getMinIndex(); i < archetype.length(); i++) {
/* 191 */         ComponentType componentType1 = archetype.get(i);
/* 192 */         if (componentType1 != null)
/* 193 */           holder1.addComponent(componentType1, componentStore.getComponent(this.reference, componentType1)); 
/*     */       } 
/* 195 */       return holder1;
/*     */     } 
/*     */     
/* 198 */     Holder<ChunkStore> holder = ChunkStore.REGISTRY.newHolder();
/* 199 */     ComponentType<ChunkStore, ? extends BlockState> componentType = (ComponentType)BlockStateModule.get().getComponentType(getClass());
/* 200 */     if (componentType == null) throw new IllegalArgumentException("Unable to find component type for: " + String.valueOf(this));
/*     */     
/* 202 */     holder.addComponent(componentType, this);
/* 203 */     return holder;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static BlockState load(BsonDocument doc, @Nonnull WorldChunk chunk, @Nonnull Vector3i pos) throws NoSuchBlockStateException {
/* 208 */     return load(doc, chunk, pos, chunk.getBlockType(pos.getX(), pos.getY(), pos.getZ()));
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static BlockState load(BsonDocument doc, @Nullable WorldChunk chunk, Vector3i pos, BlockType blockType) throws NoSuchBlockStateException {
/*     */     BlockState blockState;
/*     */     try {
/* 215 */       blockState = (BlockState)CODEC.decode((BsonValue)doc);
/* 216 */     } catch (com.hypixel.hytale.codec.lookup.ACodecMapCodec.UnknownIdException e) {
/* 217 */       throw new NoSuchBlockStateException(e);
/*     */     } 
/*     */     
/* 220 */     blockState.setPosition(chunk, pos);
/* 221 */     if (chunk != null) {
/* 222 */       if (!blockState.initialize(blockType)) return null;
/*     */       
/* 224 */       blockState.initialized.set(true);
/*     */     } 
/* 226 */     return blockState;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   @Deprecated
/*     */   public static BlockState ensureState(@Nonnull WorldChunk worldChunk, int x, int y, int z) {
/* 232 */     BlockType blockType = worldChunk.getBlockType(x, y, z);
/* 233 */     if (blockType == null || blockType.isUnknown()) return null;
/*     */     
/* 235 */     StateData state = blockType.getState();
/* 236 */     if (state == null || state.getId() == null) return null;
/*     */ 
/*     */     
/* 239 */     Vector3i position = new Vector3i(x, y, z);
/* 240 */     BlockState blockState = BlockStateModule.get().createBlockState(state.getId(), worldChunk, position, blockType);
/* 241 */     if (blockState != null) {
/* 242 */       worldChunk.setState(x, y, z, blockState);
/*     */     }
/* 244 */     return blockState;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public static BlockState getBlockState(@Nullable Ref<ChunkStore> reference, @Nonnull ComponentAccessor<ChunkStore> componentAccessor) {
/* 249 */     if (reference == null) return null; 
/* 250 */     ComponentType<ChunkStore, BlockState> componentType = findComponentType(componentAccessor.getArchetype(reference), BlockState.class);
/* 251 */     if (componentType == null) return null; 
/* 252 */     return (BlockState)componentAccessor.getComponent(reference, componentType);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   @Deprecated
/*     */   public static BlockState getBlockState(int index, @Nonnull ArchetypeChunk<ChunkStore> archetypeChunk) {
/* 258 */     ComponentType<ChunkStore, BlockState> componentType = findComponentType(archetypeChunk.getArchetype(), BlockState.class);
/* 259 */     if (componentType == null) return null; 
/* 260 */     return (BlockState)archetypeChunk.getComponent(index, componentType);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   @Deprecated
/*     */   public static BlockState getBlockState(@Nonnull Holder<ChunkStore> holder) {
/* 266 */     ComponentType<ChunkStore, BlockState> componentType = findComponentType(holder.getArchetype(), BlockState.class);
/* 267 */     if (componentType == null) return null; 
/* 268 */     return (BlockState)holder.getComponent(componentType);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private static <C extends Component<ChunkStore>, T extends C> ComponentType<ChunkStore, T> findComponentType(@Nonnull Archetype<ChunkStore> archetype, @Nonnull Class<C> entityClass) {
/* 273 */     for (int i = archetype.getMinIndex(); i < archetype.length(); i++) {
/* 274 */       ComponentType<ChunkStore, ? extends Component<ChunkStore>> componentType = archetype.get(i);
/* 275 */       if (componentType != null && 
/* 276 */         entityClass.isAssignableFrom(componentType.getTypeClass()))
/*     */       {
/* 278 */         return (ComponentType)componentType;
/*     */       }
/*     */     } 
/* 281 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\meta\BlockState.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */