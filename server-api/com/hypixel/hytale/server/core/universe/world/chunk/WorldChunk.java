/*     */ package com.hypixel.hytale.server.core.universe.world.chunk;
/*     */ import com.hypixel.hytale.assetstore.map.BlockTypeAssetMap;
/*     */ import com.hypixel.hytale.assetstore.map.IndexedLookupTableAssetMap;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.common.collection.Flag;
/*     */ import com.hypixel.hytale.common.collection.Flags;
/*     */ import com.hypixel.hytale.common.util.CompletableFutureUtil;
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.Archetype;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.protocol.BlockParticleEvent;
/*     */ import com.hypixel.hytale.server.core.asset.type.blockhitbox.BlockBoundingBoxes;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktick.BlockTickManager;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktick.config.TickProcedure;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.blocktype.component.BlockPhysics;
/*     */ import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
/*     */ import com.hypixel.hytale.server.core.modules.LegacyModule;
/*     */ import com.hypixel.hytale.server.core.modules.block.BlockModule;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.WorldNotificationHandler;
/*     */ import com.hypixel.hytale.server.core.universe.world.accessor.BlockAccessor;
/*     */ import com.hypixel.hytale.server.core.universe.world.accessor.ChunkAccessor;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.environment.EnvironmentChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.BlockSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.FluidSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.meta.BlockState;
/*     */ import com.hypixel.hytale.server.core.universe.world.meta.state.ItemContainerState;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.util.FillerBlockUtil;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.concurrent.atomic.AtomicLong;
/*     */ import java.util.concurrent.locks.StampedLock;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class WorldChunk implements BlockAccessor, Component<ChunkStore> {
/*  49 */   public static final BuilderCodec<WorldChunk> CODEC = BuilderCodec.builder(WorldChunk.class, WorldChunk::new)
/*  50 */     .build(); public static final int KEEP_ALIVE_DEFAULT = 15;
/*     */   
/*     */   public static ComponentType<ChunkStore, WorldChunk> getComponentType() {
/*  53 */     return LegacyModule.get().getWorldChunkComponentType();
/*     */   }
/*     */   
/*  56 */   private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */   
/*     */   private World world;
/*     */   
/*  60 */   private final StampedLock flagsLock = new StampedLock();
/*     */   
/*     */   private final Flags<ChunkFlag> flags;
/*     */   
/*     */   private Ref<ChunkStore> reference;
/*     */   @Nullable
/*     */   private BlockChunk blockChunk;
/*     */   @Nullable
/*     */   private BlockComponentChunk blockComponentChunk;
/*     */   @Nullable
/*     */   private EntityChunk entityChunk;
/*  71 */   private int keepAlive = 15;
/*  72 */   private int activeTimer = 15;
/*     */   private boolean needsSaving;
/*     */   private boolean isSaving;
/*     */   private boolean keepLoaded;
/*     */   private boolean lightingUpdatesEnabled = true;
/*     */   @Deprecated
/*  78 */   public final AtomicLong chunkLightTiming = new AtomicLong();
/*     */ 
/*     */   
/*     */   private WorldChunk() {
/*  82 */     this.flags = new Flags((Flag[])new ChunkFlag[0]);
/*     */   }
/*     */   
/*     */   private WorldChunk(World world, Flags<ChunkFlag> flags) {
/*  86 */     this.world = world;
/*  87 */     this.flags = flags;
/*     */   }
/*     */   
/*     */   public WorldChunk(World world, Flags<ChunkFlag> state, BlockChunk blockChunk, BlockComponentChunk blockComponentChunk, EntityChunk entityChunk) {
/*  91 */     this(world, state);
/*  92 */     this.blockChunk = blockChunk;
/*  93 */     this.blockComponentChunk = blockComponentChunk;
/*  94 */     this.entityChunk = entityChunk;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Holder<ChunkStore> toHolder() {
/* 100 */     if (this.reference != null && this.reference.isValid() && this.world != null) {
/* 101 */       Holder<ChunkStore> holder1 = ChunkStore.REGISTRY.newHolder();
/* 102 */       Store<ChunkStore> componentStore = this.world.getChunkStore().getStore();
/* 103 */       Archetype<ChunkStore> archetype = componentStore.getArchetype(this.reference);
/* 104 */       for (int i = archetype.getMinIndex(); i < archetype.length(); i++) {
/* 105 */         ComponentType componentType = archetype.get(i);
/* 106 */         if (componentType != null)
/* 107 */           holder1.addComponent(componentType, componentStore.getComponent(this.reference, componentType)); 
/*     */       } 
/* 109 */       return holder1;
/*     */     } 
/*     */     
/* 112 */     Holder<ChunkStore> holder = ChunkStore.REGISTRY.newHolder();
/* 113 */     holder.addComponent(getComponentType(), this);
/* 114 */     holder.addComponent(BlockChunk.getComponentType(), this.blockChunk);
/* 115 */     holder.addComponent(EnvironmentChunk.getComponentType(), (Component)this.blockChunk.getEnvironmentChunk());
/* 116 */     holder.addComponent(EntityChunk.getComponentType(), this.entityChunk);
/* 117 */     holder.addComponent(BlockComponentChunk.getComponentType(), this.blockComponentChunk);
/* 118 */     return holder;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public void setReference(Ref<ChunkStore> reference) {
/* 123 */     if (this.reference != null && this.reference.isValid()) {
/* 124 */       throw new IllegalArgumentException("Chunk already has a valid EntityReference: " + String.valueOf(this.reference) + " new reference " + String.valueOf(reference));
/*     */     }
/* 126 */     this.reference = reference;
/*     */   }
/*     */   
/*     */   public Ref<ChunkStore> getReference() {
/* 130 */     return this.reference;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Component<ChunkStore> clone() {
/* 136 */     return new WorldChunk();
/*     */   }
/*     */   
/*     */   public boolean is(@Nonnull ChunkFlag flag) {
/* 140 */     long stamp = this.flagsLock.tryOptimisticRead();
/* 141 */     boolean value = this.flags.is(flag);
/* 142 */     if (this.flagsLock.validate(stamp)) {
/* 143 */       return value;
/*     */     }
/* 145 */     stamp = this.flagsLock.readLock();
/*     */     try {
/* 147 */       return this.flags.is(flag);
/*     */     } finally {
/* 149 */       this.flagsLock.unlockRead(stamp);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean not(@Nonnull ChunkFlag flag) {
/* 154 */     long stamp = this.flagsLock.tryOptimisticRead();
/* 155 */     boolean value = this.flags.not(flag);
/* 156 */     if (this.flagsLock.validate(stamp)) {
/* 157 */       return value;
/*     */     }
/* 159 */     stamp = this.flagsLock.readLock();
/*     */     try {
/* 161 */       return this.flags.not(flag);
/*     */     } finally {
/* 163 */       this.flagsLock.unlockRead(stamp);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setFlag(@Nonnull ChunkFlag flag, boolean value) {
/*     */     boolean isInit;
/* 169 */     long lock = this.flagsLock.writeLock();
/*     */     
/*     */     try {
/* 172 */       if (!this.flags.set(flag, value))
/* 173 */         return;  isInit = this.flags.is(ChunkFlag.INIT);
/*     */     } finally {
/* 175 */       this.flagsLock.unlockWrite(lock);
/*     */     } 
/* 177 */     if (isInit)
/*     */     {
/* 179 */       updateFlag(flag, value);
/*     */     }
/* 181 */     LOGGER.at(Level.FINER).log("[%d, %d] updated chunk flag (init: %s): %s, %s ", Integer.valueOf(getX()), Integer.valueOf(getZ()), Boolean.valueOf(isInit), flag, Boolean.valueOf(value));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean toggleFlag(@Nonnull ChunkFlag flag) {
/*     */     boolean value, isInit;
/* 187 */     long lock = this.flagsLock.writeLock();
/*     */     try {
/* 189 */       value = this.flags.toggle(flag);
/* 190 */       isInit = this.flags.is(ChunkFlag.INIT);
/*     */     } finally {
/* 192 */       this.flagsLock.unlockWrite(lock);
/*     */     } 
/* 194 */     if (isInit)
/*     */     {
/* 196 */       updateFlag(flag, value);
/*     */     }
/* 198 */     LOGGER.at(Level.FINER).log("[%d, %d] updated chunk flag (init: %s): %s, %s ", Integer.valueOf(getX()), Integer.valueOf(getZ()), Boolean.valueOf(isInit), flag, Boolean.valueOf(value));
/* 199 */     return value;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public void loadFromHolder(World world, int x, int z, @Nonnull Holder<ChunkStore> holder) {
/* 204 */     this.world = world;
/* 205 */     this.blockChunk = (BlockChunk)holder.getComponent(BlockChunk.getComponentType());
/* 206 */     this.blockChunk.setEnvironmentChunk((EnvironmentChunk)holder.getComponent(EnvironmentChunk.getComponentType()));
/* 207 */     this.blockComponentChunk = (BlockComponentChunk)holder.getComponent(BlockComponentChunk.getComponentType());
/* 208 */     this.entityChunk = (EntityChunk)holder.getComponent(EntityChunk.getComponentType());
/* 209 */     this.blockChunk.load(x, z);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public void setBlockComponentChunk(BlockComponentChunk blockComponentChunk) {
/* 214 */     this.blockComponentChunk = blockComponentChunk;
/*     */   }
/*     */   
/*     */   public void initFlags() {
/* 218 */     this.world.debugAssertInTickingThread();
/* 219 */     if (!is(ChunkFlag.START_INIT)) throw new IllegalArgumentException("START_INIT hasn't been set!"); 
/* 220 */     if (is(ChunkFlag.INIT)) throw new IllegalArgumentException("INIT is already set!");
/*     */ 
/*     */     
/* 223 */     for (int i = 0; i < ChunkFlag.VALUES.length; i++) {
/* 224 */       ChunkFlag flag = ChunkFlag.VALUES[i];
/* 225 */       updateFlag(flag, is(flag));
/*     */     } 
/* 227 */     setFlag(ChunkFlag.INIT, true);
/*     */   }
/*     */   
/*     */   private void updateFlag(ChunkFlag flag, boolean value) {
/* 231 */     if (flag == ChunkFlag.TICKING) {
/*     */ 
/*     */       
/* 234 */       this.world.debugAssertInTickingThread();
/* 235 */       resetKeepAlive();
/* 236 */       if (value) {
/* 237 */         startsTicking();
/*     */       } else {
/* 239 */         stopsTicking();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void startsTicking() {
/* 245 */     this.world.debugAssertInTickingThread();
/* 246 */     LOGGER.at(Level.FINER).log("Chunk started ticking %s", this);
/*     */ 
/*     */     
/* 249 */     Store<ChunkStore> componentStore = this.world.getChunkStore().getStore();
/* 250 */     componentStore.tryRemoveComponent(this.reference, ChunkStore.REGISTRY.getNonTickingComponentType());
/*     */   }
/*     */   
/*     */   private void stopsTicking() {
/* 254 */     this.world.debugAssertInTickingThread();
/* 255 */     LOGGER.at(Level.FINER).log("Chunk stopped ticking %s", this);
/*     */ 
/*     */     
/* 258 */     Store<ChunkStore> componentStore = this.world.getChunkStore().getStore();
/* 259 */     componentStore.ensureComponent(this.reference, ChunkStore.REGISTRY.getNonTickingComponentType());
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public BlockChunk getBlockChunk() {
/* 264 */     return this.blockChunk;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BlockComponentChunk getBlockComponentChunk() {
/* 274 */     return this.blockComponentChunk;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public EntityChunk getEntityChunk() {
/* 284 */     return this.entityChunk;
/*     */   }
/*     */   
/*     */   public boolean shouldKeepLoaded() {
/* 288 */     return this.keepLoaded;
/*     */   }
/*     */   
/*     */   public void setKeepLoaded(boolean keepLoaded) {
/* 292 */     this.keepLoaded = keepLoaded;
/*     */   }
/*     */   
/*     */   public int pollKeepAlive(int pollCount) {
/* 296 */     return this.keepAlive = Math.max(this.keepAlive - pollCount, 0);
/*     */   }
/*     */   
/*     */   public void resetKeepAlive() {
/* 300 */     this.keepAlive = 15;
/*     */   }
/*     */   
/*     */   public int pollActiveTimer(int pollCount) {
/* 304 */     return this.activeTimer = Math.max(this.activeTimer - pollCount, 0);
/*     */   }
/*     */   
/*     */   public void resetActiveTimer() {
/* 308 */     this.activeTimer = 15;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChunkAccessor getChunkAccessor() {
/* 313 */     return (ChunkAccessor)this.world;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBlock(int x, int y, int z) {
/* 318 */     if (y < 0 || y >= 320) return 0; 
/* 319 */     return this.blockChunk.getBlock(x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean setBlock(int x, int y, int z, int id, @Nonnull BlockType blockType, int rotation, int filler, int settings) {
/* 324 */     if (y < 0 || y >= 320) return false;
/*     */     
/* 326 */     short oldHeight = this.blockChunk.getHeight(x, z);
/* 327 */     BlockSection blockSection = this.blockChunk.getSectionAtBlockY(y);
/* 328 */     int oldRotation = blockSection.getRotationIndex(x, y, z);
/* 329 */     int oldBlock = blockSection.get(x, y, z);
/*     */     
/* 331 */     boolean changed = ((oldBlock != id || rotation != oldRotation) && blockSection.set(x, y, z, id, rotation, filler));
/*     */     
/* 333 */     if (changed || (settings & 0x40) != 0) {
/* 334 */       int worldX = (getX() << 5) + (x & 0x1F);
/* 335 */       int worldZ = (getZ() << 5) + (z & 0x1F);
/*     */       
/* 337 */       if ((settings & 0x40) != 0) {
/* 338 */         blockSection.invalidateBlock(x, y, z);
/*     */       }
/*     */       
/* 341 */       short newHeight = oldHeight;
/* 342 */       if ((settings & 0x200) == 0)
/*     */       {
/* 344 */         if (oldHeight <= y) {
/* 345 */           if (oldHeight == y && id == 0) {
/*     */             
/* 347 */             newHeight = this.blockChunk.updateHeight(x, z, (short)y);
/* 348 */           } else if (oldHeight < y && id != 0 && 
/* 349 */             blockType.getOpacity() != Opacity.Transparent) {
/*     */             
/* 351 */             newHeight = (short)y;
/* 352 */             this.blockChunk.setHeight(x, z, newHeight);
/*     */           } 
/*     */         }
/*     */       }
/*     */ 
/*     */       
/* 358 */       WorldNotificationHandler notificationHandler = getWorld().getNotificationHandler();
/*     */       
/* 360 */       if ((settings & 0x4) == 0)
/*     */       {
/*     */         
/* 363 */         if (oldBlock == 0 && id != 0) {
/* 364 */           notificationHandler.sendBlockParticle(worldX + 0.5D, y + 0.5D, worldZ + 0.5D, id, BlockParticleEvent.Build);
/*     */         
/*     */         }
/* 367 */         else if (oldBlock != 0 && id == 0) {
/* 368 */           BlockParticleEvent particleType = ((settings & 0x20) != 0) ? BlockParticleEvent.Physics : BlockParticleEvent.Break;
/* 369 */           notificationHandler.sendBlockParticle(worldX + 0.5D, y + 0.5D, worldZ + 0.5D, oldBlock, particleType);
/*     */         }
/*     */         else {
/*     */           
/* 373 */           notificationHandler.sendBlockParticle(worldX + 0.5D, y + 0.5D, worldZ + 0.5D, oldBlock, BlockParticleEvent.Break);
/* 374 */           notificationHandler.sendBlockParticle(worldX + 0.5D, y + 0.5D, worldZ + 0.5D, id, BlockParticleEvent.Build);
/*     */         } 
/*     */       }
/*     */       
/* 378 */       BlockTypeAssetMap<String, BlockType> blockTypeAssetMap = BlockType.getAssetMap();
/* 379 */       IndexedLookupTableAssetMap<String, BlockBoundingBoxes> hitboxAssetMap = BlockBoundingBoxes.getAssetMap();
/*     */       
/* 381 */       String blockTypeKey = blockType.getId();
/* 382 */       if ((settings & 0x2) == 0) {
/* 383 */         Holder<ChunkStore> blockEntity = blockType.getBlockEntity();
/* 384 */         if (blockEntity != null && filler == 0) {
/* 385 */           Holder<ChunkStore> newComponents = blockEntity.clone();
/* 386 */           setState(x, y, z, newComponents);
/*     */         } else {
/* 388 */           BlockState blockState = null;
/* 389 */           String blockStateType = (blockType.getState() != null) ? blockType.getState().getId() : null;
/* 390 */           if (id != 0 && blockStateType != null && filler == 0) {
/* 391 */             blockState = BlockStateModule.get().createBlockState(blockStateType, this, new Vector3i(x, y, z), blockType);
/* 392 */             if (blockState == null) {
/* 393 */               LOGGER.at(Level.WARNING).log("Failed to create BlockState: %s for BlockType: %s", blockStateType, blockTypeKey);
/*     */             }
/*     */           } 
/* 396 */           BlockState oldState = getState(x, y, z);
/*     */ 
/*     */           
/* 399 */           if (blockState instanceof ItemContainerState) { ItemContainerState newState = (ItemContainerState)blockState;
/* 400 */             FillerBlockUtil.forEachFillerBlock(((BlockBoundingBoxes)hitboxAssetMap.getAsset(blockType.getHitboxTypeIndex())).get(rotation), (x1, y1, z1) -> {
/*     */                   int blockX = worldX + x1;
/*     */                   
/*     */                   int blockY = y + y1;
/*     */                   int blockZ = worldZ + z1;
/* 405 */                   boolean isZero = (x1 == 0 && y1 == 0 && z1 == 0);
/*     */                   BlockState stateAtFiller = isZero ? oldState : getState(blockX, blockY, blockZ);
/*     */                   if (stateAtFiller instanceof ItemContainerState) {
/*     */                     ItemContainerState oldContainer = (ItemContainerState)stateAtFiller;
/*     */                     oldContainer.getItemContainer().moveAllItemStacksTo(new ItemContainer[] { newState.getItemContainer() });
/*     */                   } 
/*     */                 }); }
/*     */           
/* 413 */           setState(x, y, z, blockState, ((settings & 0x1) == 0));
/*     */         } 
/*     */       } 
/*     */       
/* 417 */       if (this.lightingUpdatesEnabled) {
/* 418 */         this.world.getChunkLighting().invalidateLightAtBlock(this, x, y, z, blockType, oldHeight, newHeight);
/*     */       }
/*     */       
/* 421 */       TickProcedure tickProcedure = BlockTickManager.getBlockTickProvider().getTickProcedure(id);
/* 422 */       this.blockChunk.setTicking(x, y, z, (tickProcedure != null));
/*     */       
/* 424 */       if ((settings & 0x10) == 0) {
/* 425 */         int settingsWithoutFiller = settings | 0x8 | 0x10;
/*     */         
/* 427 */         BlockType oldBlockType = (BlockType)blockTypeAssetMap.getAsset(oldBlock);
/* 428 */         String oldBlockKey = oldBlockType.getId();
/*     */ 
/*     */         
/* 431 */         FillerBlockUtil.forEachFillerBlock(((BlockBoundingBoxes)hitboxAssetMap.getAsset(oldBlockType.getHitboxTypeIndex())).get(oldRotation), (x1, y1, z1) -> {
/*     */               if (x1 == 0 && y1 == 0 && z1 == 0) {
/*     */                 return;
/*     */               }
/*     */               
/*     */               int blockX = worldX + x1;
/*     */               int blockY = y + y1;
/*     */               int blockZ = worldZ + z1;
/*     */               if (ChunkUtil.isSameChunk(worldX, worldZ, blockX, blockZ)) {
/*     */                 String blockTypeKey1 = getBlockType(blockX, blockY, blockZ).getId();
/*     */                 if (blockTypeKey1.equals(oldBlockKey)) {
/*     */                   breakBlock(blockX, blockY, blockZ, settingsWithoutFiller);
/*     */                 }
/*     */               } else {
/*     */                 String blockTypeKey1 = getWorld().getBlockType(blockX, blockY, blockZ).getId();
/*     */                 if (blockTypeKey1.equals(oldBlockKey)) {
/*     */                   getWorld().breakBlock(blockX, blockY, blockZ, settingsWithoutFiller);
/*     */                 }
/*     */               } 
/*     */             });
/*     */       } 
/* 452 */       if ((settings & 0x8) == 0 && filler == 0) {
/* 453 */         int settingsWithoutSetFiller = settings | 0x8;
/*     */ 
/*     */ 
/*     */         
/* 457 */         BlockType finalBlockType = blockType;
/* 458 */         int blockId = id;
/* 459 */         int finalRotation = rotation;
/* 460 */         FillerBlockUtil.forEachFillerBlock(((BlockBoundingBoxes)hitboxAssetMap.getAsset(blockType.getHitboxTypeIndex())).get(rotation), (x1, y1, z1) -> {
/*     */               if (x1 == 0 && y1 == 0 && z1 == 0) {
/*     */                 return;
/*     */               }
/*     */               
/*     */               int blockX = worldX + x1;
/*     */               
/*     */               int blockY = y + y1;
/*     */               int blockZ = worldZ + z1;
/*     */               boolean sameChunk = ChunkUtil.isSameChunk(worldX, worldZ, blockX, blockZ);
/*     */               if (sameChunk) {
/*     */                 setBlock(blockX, blockY, blockZ, blockId, finalBlockType, finalRotation, FillerBlockUtil.pack(x1, y1, z1), settingsWithoutSetFiller);
/*     */               } else {
/*     */                 getWorld().getNonTickingChunk(ChunkUtil.indexChunkFromBlock(blockX, blockZ)).setBlock(blockX, blockY, blockZ, blockId, finalBlockType, finalRotation, FillerBlockUtil.pack(x1, y1, z1), settingsWithoutSetFiller);
/*     */               } 
/*     */             });
/*     */       } 
/* 477 */       if ((settings & 0x100) != 0)
/*     */       {
/* 479 */         FillerBlockUtil.forEachFillerBlock(((BlockBoundingBoxes)hitboxAssetMap.getAsset(blockType.getHitboxTypeIndex())).get(rotation), (x1, y1, z1) -> getChunkAccessor().performBlockUpdate(worldX + x1, y + y1, worldZ + z1));
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 485 */       if (this.reference != null && this.reference.isValid()) {
/* 486 */         if (this.world.isInThread()) {
/* 487 */           setBlockPhysics(x, y, z, blockType);
/*     */         } else {
/* 489 */           BlockType tempFinalBlockType = blockType;
/* 490 */           CompletableFutureUtil._catch(CompletableFuture.runAsync(() -> setBlockPhysics(x, y, z, tempFinalBlockType), (Executor)this.world));
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 497 */     return changed;
/*     */   }
/*     */   
/*     */   private void setBlockPhysics(int x, int y, int z, @Nonnull BlockType blockType) {
/* 501 */     Store<ChunkStore> store = this.reference.getStore();
/* 502 */     ChunkColumn column = (ChunkColumn)store.getComponent(this.reference, ChunkColumn.getComponentType());
/* 503 */     Ref<ChunkStore> section = column.getSection(ChunkUtil.chunkCoordinate(y));
/* 504 */     if (section != null) {
/* 505 */       if (!blockType.hasSupport()) {
/* 506 */         BlockPhysics.clear(store, section, x, y, z);
/*     */       }
/*     */       else {
/*     */         
/* 510 */         BlockPhysics.reset(store, section, x, y, z);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated(forRemoval = true)
/*     */   public int getFiller(int x, int y, int z) {
/* 518 */     if (y < 0 || y >= 320) return 0; 
/* 519 */     return this.blockChunk.getSectionAtBlockY(y).getFiller(x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated(forRemoval = true)
/*     */   public int getRotationIndex(int x, int y, int z) {
/* 525 */     if (y < 0 || y >= 320) return 0; 
/* 526 */     return this.blockChunk.getSectionAtBlockY(y).getRotationIndex(x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean setTicking(int x, int y, int z, boolean ticking) {
/* 531 */     return this.blockChunk.setTicking(x, y, z, ticking);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isTicking(int x, int y, int z) {
/* 536 */     return this.blockChunk.isTicking(x, y, z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public short getHeight(int x, int z) {
/* 543 */     return this.blockChunk.getHeight(x, z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public short getHeight(int index) {
/* 550 */     return this.blockChunk.getHeight(index);
/*     */   }
/*     */   
/*     */   public int getTint(int x, int z) {
/* 554 */     return this.blockChunk.getTint(x, z);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BlockState getState(int x, int y, int z) {
/* 560 */     if (y < 0 || y >= 320) return null;
/*     */     
/* 562 */     if (!this.world.isInThread()) {
/* 563 */       return CompletableFuture.<BlockState>supplyAsync(() -> getState(x, y, z), (Executor)this.world).join();
/*     */     }
/*     */     
/* 566 */     int index = ChunkUtil.indexBlockInColumn(x, y, z);
/*     */     
/* 568 */     Ref<ChunkStore> reference = this.blockComponentChunk.getEntityReference(index);
/* 569 */     if (reference != null) return BlockState.getBlockState(reference, (ComponentAccessor)reference.getStore());
/*     */     
/* 571 */     Holder<ChunkStore> holder = this.blockComponentChunk.getEntityHolder(index);
/* 572 */     if (holder != null) return BlockState.getBlockState(holder);
/*     */     
/* 574 */     return null;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Ref<ChunkStore> getBlockComponentEntity(int x, int y, int z) {
/* 579 */     if (y < 0 || y >= 320) return null;
/*     */     
/* 581 */     if (!this.world.isInThread()) {
/* 582 */       return CompletableFuture.<Ref<ChunkStore>>supplyAsync(() -> getBlockComponentEntity(x, y, z), (Executor)this.world).join();
/*     */     }
/*     */     
/* 585 */     int index = ChunkUtil.indexBlockInColumn(x, y, z);
/*     */     
/* 587 */     return this.blockComponentChunk.getEntityReference(index);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Holder<ChunkStore> getBlockComponentHolder(int x, int y, int z) {
/* 593 */     if (y < 0 || y >= 320) return null;
/*     */     
/* 595 */     if (!this.world.isInThread()) {
/* 596 */       return CompletableFuture.<Holder<ChunkStore>>supplyAsync(() -> getBlockComponentHolder(x, y, z), (Executor)this.world).join();
/*     */     }
/*     */     
/* 599 */     int index = ChunkUtil.indexBlockInColumn(x, y, z);
/*     */     
/* 601 */     Ref<ChunkStore> reference = this.blockComponentChunk.getEntityReference(index);
/* 602 */     return (reference == null) ? null : reference.getStore().copyEntity(reference);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setState(int x, int y, int z, @Nullable BlockState state, boolean notify) {
/* 607 */     if (y < 0 || y >= 320)
/*     */       return; 
/* 609 */     Holder<ChunkStore> holder = (state != null) ? state.toHolder() : null;
/* 610 */     setState(x, y, z, holder);
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated(forRemoval = true)
/*     */   public int getFluidId(int x, int y, int z) {
/* 616 */     Ref<ChunkStore> columnRef = getReference();
/* 617 */     Store<ChunkStore> store = columnRef.getStore();
/* 618 */     ChunkColumn column = (ChunkColumn)store.getComponent(columnRef, ChunkColumn.getComponentType());
/* 619 */     Ref<ChunkStore> section = column.getSection(ChunkUtil.chunkCoordinate(y));
/* 620 */     if (section == null) return Integer.MIN_VALUE; 
/* 621 */     FluidSection fluidSection = (FluidSection)store.getComponent(section, FluidSection.getComponentType());
/* 622 */     return fluidSection.getFluidId(x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated(forRemoval = true)
/*     */   public byte getFluidLevel(int x, int y, int z) {
/* 628 */     Ref<ChunkStore> columnRef = getReference();
/* 629 */     Store<ChunkStore> store = columnRef.getStore();
/* 630 */     ChunkColumn column = (ChunkColumn)store.getComponent(columnRef, ChunkColumn.getComponentType());
/* 631 */     Ref<ChunkStore> section = column.getSection(ChunkUtil.chunkCoordinate(y));
/* 632 */     if (section == null) return 0; 
/* 633 */     FluidSection fluidSection = (FluidSection)store.getComponent(section, FluidSection.getComponentType());
/* 634 */     return fluidSection.getFluidLevel(x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated(forRemoval = true)
/*     */   public int getSupportValue(int x, int y, int z) {
/* 640 */     Ref<ChunkStore> columnRef = getReference();
/* 641 */     Store<ChunkStore> store = columnRef.getStore();
/* 642 */     ChunkColumn column = (ChunkColumn)store.getComponent(columnRef, ChunkColumn.getComponentType());
/* 643 */     Ref<ChunkStore> section = column.getSection(ChunkUtil.chunkCoordinate(y));
/* 644 */     if (section == null) return 0; 
/* 645 */     BlockPhysics blockPhysics = (BlockPhysics)store.getComponent(section, BlockPhysics.getComponentType());
/* 646 */     return (blockPhysics != null) ? blockPhysics.get(x, y, z) : 0;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public void setState(int x, int y, int z, @Nullable Holder<ChunkStore> holder) {
/* 651 */     if (y < 0 || y >= 320)
/*     */       return; 
/* 653 */     if (!this.world.isInThread()) {
/* 654 */       CompletableFutureUtil._catch(CompletableFuture.runAsync(() -> setState(x, y, z, holder), (Executor)this.world));
/*     */       
/*     */       return;
/*     */     } 
/* 658 */     boolean notify = true;
/*     */     
/* 660 */     int index = ChunkUtil.indexBlockInColumn(x, y, z);
/*     */     
/* 662 */     if (holder == null) {
/* 663 */       Ref<ChunkStore> reference = this.blockComponentChunk.getEntityReference(index);
/* 664 */       if (reference != null) {
/* 665 */         Holder<ChunkStore> oldHolder = reference.getStore().removeEntity(reference, RemoveReason.REMOVE);
/* 666 */         BlockState blockState = BlockState.getBlockState(oldHolder);
/*     */         
/* 668 */         if (notify) {
/* 669 */           this.world.getNotificationHandler().updateState(x, y, z, null, blockState);
/*     */         }
/*     */       } else {
/* 672 */         this.blockComponentChunk.removeEntityHolder(index);
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/* 677 */     BlockState state = BlockState.getBlockState(holder);
/* 678 */     if (state != null) {
/* 679 */       state.setPosition(this, new Vector3i(x, y, z));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 686 */     Store<ChunkStore> blockComponentStore = this.world.getChunkStore().getStore();
/*     */     
/* 688 */     if (!is(ChunkFlag.TICKING)) {
/* 689 */       Holder<ChunkStore> oldHolder = this.blockComponentChunk.getEntityHolder(index);
/*     */       
/* 691 */       BlockState blockState = null;
/* 692 */       if (oldHolder != null) {
/* 693 */         blockState = BlockState.getBlockState(oldHolder);
/*     */       }
/* 695 */       this.blockComponentChunk.removeEntityHolder(index);
/*     */       
/* 697 */       holder.putComponent(BlockModule.BlockStateInfo.getComponentType(), (Component)new BlockModule.BlockStateInfo(index, this.reference));
/* 698 */       this.blockComponentChunk.addEntityHolder(index, holder);
/*     */       
/* 700 */       if (notify) {
/* 701 */         this.world.getNotificationHandler().updateState(x, y, z, state, blockState);
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/* 706 */     Ref<ChunkStore> oldReference = this.blockComponentChunk.getEntityReference(index);
/*     */     
/* 708 */     BlockState oldState = null;
/* 709 */     if (oldReference != null) {
/* 710 */       Holder<ChunkStore> oldEntityHolder = blockComponentStore.removeEntity(oldReference, RemoveReason.REMOVE);
/* 711 */       oldState = BlockState.getBlockState(oldEntityHolder);
/*     */     } else {
/* 713 */       this.blockComponentChunk.removeEntityHolder(index);
/*     */     } 
/*     */     
/* 716 */     holder.putComponent(BlockModule.BlockStateInfo.getComponentType(), (Component)new BlockModule.BlockStateInfo(index, this.reference));
/* 717 */     blockComponentStore.addEntity(holder, AddReason.SPAWN);
/*     */     
/* 719 */     if (notify) {
/* 720 */       this.world.getNotificationHandler().updateState(x, y, z, state, oldState);
/*     */     }
/*     */   }
/*     */   
/*     */   public void markNeedsSaving() {
/* 725 */     this.needsSaving = true;
/*     */   }
/*     */   
/*     */   public boolean getNeedsSaving() {
/* 729 */     return (this.needsSaving || this.blockChunk.getNeedsSaving() || this.blockComponentChunk.getNeedsSaving() || this.entityChunk.getNeedsSaving());
/*     */   }
/*     */   
/*     */   public boolean consumeNeedsSaving() {
/* 733 */     boolean out = this.needsSaving;
/* 734 */     if (this.blockChunk.consumeNeedsSaving()) out = true; 
/* 735 */     if (this.blockComponentChunk.consumeNeedsSaving()) out = true; 
/* 736 */     if (this.entityChunk.consumeNeedsSaving()) out = true; 
/* 737 */     this.needsSaving = false;
/* 738 */     return out;
/*     */   }
/*     */   
/*     */   public boolean isSaving() {
/* 742 */     return this.isSaving;
/*     */   }
/*     */   
/*     */   public void setSaving(boolean saving) {
/* 746 */     this.isSaving = saving;
/*     */   }
/*     */   
/*     */   public long getIndex() {
/* 750 */     return this.blockChunk.getIndex();
/*     */   }
/*     */   
/*     */   public int getX() {
/* 754 */     return this.blockChunk.getX();
/*     */   }
/*     */   
/*     */   public int getZ() {
/* 758 */     return this.blockChunk.getZ();
/*     */   }
/*     */   
/*     */   public void setLightingUpdatesEnabled(boolean enableLightUpdates) {
/* 762 */     this.lightingUpdatesEnabled = enableLightUpdates;
/*     */   }
/*     */   
/*     */   public boolean isLightingUpdatesEnabled() {
/* 766 */     return this.lightingUpdatesEnabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public World getWorld() {
/* 773 */     return this.world;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 779 */     return "WorldChunk{x=" + this.blockChunk
/* 780 */       .getX() + ", z=" + this.blockChunk
/* 781 */       .getZ() + ", flags=" + String.valueOf(this.flags) + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\chunk\WorldChunk.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */