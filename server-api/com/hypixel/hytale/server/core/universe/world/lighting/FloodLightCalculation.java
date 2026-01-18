/*      */ package com.hypixel.hytale.server.core.universe.world.lighting;
/*      */ import com.hypixel.hytale.common.util.FormatUtil;
/*      */ import com.hypixel.hytale.component.Ref;
/*      */ import com.hypixel.hytale.math.util.ChunkUtil;
/*      */ import com.hypixel.hytale.math.util.MathUtil;
/*      */ import com.hypixel.hytale.math.vector.Vector2i;
/*      */ import com.hypixel.hytale.math.vector.Vector3i;
/*      */ import com.hypixel.hytale.metrics.metric.AverageCollector;
/*      */ import com.hypixel.hytale.protocol.ColorLight;
/*      */ import com.hypixel.hytale.protocol.Opacity;
/*      */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*      */ import com.hypixel.hytale.server.core.asset.type.fluid.Fluid;
/*      */ import com.hypixel.hytale.server.core.universe.world.World;
/*      */ import com.hypixel.hytale.server.core.universe.world.accessor.ChunkAccessor;
/*      */ import com.hypixel.hytale.server.core.universe.world.accessor.LocalCachedChunkAccessor;
/*      */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockChunk;
/*      */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*      */ import com.hypixel.hytale.server.core.universe.world.chunk.section.BlockSection;
/*      */ import com.hypixel.hytale.server.core.universe.world.chunk.section.ChunkLightData;
/*      */ import com.hypixel.hytale.server.core.universe.world.chunk.section.ChunkLightDataBuilder;
/*      */ import com.hypixel.hytale.server.core.universe.world.chunk.section.FluidSection;
/*      */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*      */ import it.unimi.dsi.fastutil.ints.Int2IntFunction;
/*      */ import it.unimi.dsi.fastutil.ints.IntIterator;
/*      */ import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
/*      */ import java.util.Arrays;
/*      */ import java.util.BitSet;
/*      */ import java.util.Objects;
/*      */ import java.util.concurrent.CompletableFuture;
/*      */ import java.util.concurrent.Executor;
/*      */ import java.util.concurrent.atomic.AtomicLong;
/*      */ import java.util.function.IntBinaryOperator;
/*      */ import java.util.logging.Level;
/*      */ import javax.annotation.Nonnull;
/*      */ import javax.annotation.Nullable;
/*      */ 
/*      */ public class FloodLightCalculation implements LightCalculation {
/*      */   protected final ChunkLightingManager chunkLightingManager;
/*   39 */   protected final AverageCollector emptyAvg = new AverageCollector();
/*   40 */   protected final AverageCollector blocksAvg = new AverageCollector();
/*      */   
/*   42 */   protected final AverageCollector borderAvg = new AverageCollector();
/*      */   
/*   44 */   protected final AverageCollector avgChunk = new AverageCollector();
/*      */   
/*   46 */   protected final BlockSection[][] fromSections = new BlockSection[][] { new BlockSection[Vector3i.BLOCK_SIDES.length], new BlockSection[Vector3i.BLOCK_EDGES.length], new BlockSection[Vector3i.BLOCK_CORNERS.length] };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FloodLightCalculation(ChunkLightingManager chunkLightingManager) {
/*   53 */     this.chunkLightingManager = chunkLightingManager;
/*      */   }
/*      */ 
/*      */   
/*      */   public void init(@Nonnull WorldChunk chunk) {
/*   58 */     this.chunkLightingManager.getWorld().debugAssertInTickingThread();
/*      */     
/*   60 */     int x = chunk.getX();
/*   61 */     int z = chunk.getZ();
/*      */     
/*   63 */     initChunk(chunk, x, z);
/*   64 */     initNeighbours(x, z);
/*      */   }
/*      */   
/*      */   private void initChunk(int x, int z) {
/*   68 */     WorldChunk chunk = this.chunkLightingManager.getWorld().getChunkIfInMemory(ChunkUtil.indexChunk(x, z));
/*   69 */     if (chunk == null)
/*      */       return; 
/*   71 */     initChunk(chunk, x, z);
/*      */   }
/*      */   
/*      */   private void initChunk(@Nonnull WorldChunk chunk, int x, int z) {
/*   75 */     for (int y = 0; y < 10; y++) {
/*   76 */       initSection(chunk, x, y, z);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private void initNeighbours(int x, int z) {
/*   82 */     initChunk(x - 1, z - 1);
/*   83 */     initChunk(x - 1, z + 1);
/*   84 */     initChunk(x + 1, z - 1);
/*   85 */     initChunk(x + 1, z + 1);
/*      */     
/*   87 */     initChunk(x - 1, z);
/*   88 */     initChunk(x + 1, z);
/*   89 */     initChunk(x, z - 1);
/*   90 */     initChunk(x, z + 1);
/*      */   }
/*      */   
/*      */   private void initSection(@Nonnull WorldChunk chunk, int x, int y, int z) {
/*   94 */     BlockSection section = chunk.getBlockChunk().getSectionAtIndex(y);
/*      */ 
/*      */     
/*   97 */     if (!section.hasLocalLight()) {
/*   98 */       this.chunkLightingManager.getLogger().at(Level.FINEST).log("Init chunk %d, %d, %d because doesn't have local light", Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(z));
/*   99 */     } else if (!section.hasGlobalLight()) {
/*  100 */       this.chunkLightingManager.getLogger().at(Level.FINEST).log("Init chunk %d, %d, %d because doesn't have global light", Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(z));
/*      */     } else {
/*      */       return;
/*      */     } 
/*      */     
/*  105 */     this.chunkLightingManager.addToQueue(new Vector3i(x, y, z));
/*      */   }
/*      */ 
/*      */   
/*      */   private void initNeighbours(@Nonnull LocalCachedChunkAccessor accessor, int chunkX, int chunkY, int chunkZ) {
/*  110 */     initNeighbourSections(accessor, chunkX - 1, chunkY, chunkZ - 1);
/*  111 */     initNeighbourSections(accessor, chunkX - 1, chunkY, chunkZ + 1);
/*  112 */     initNeighbourSections(accessor, chunkX + 1, chunkY, chunkZ - 1);
/*  113 */     initNeighbourSections(accessor, chunkX + 1, chunkY, chunkZ + 1);
/*      */     
/*  115 */     initNeighbourSections(accessor, chunkX - 1, chunkY, chunkZ);
/*  116 */     initNeighbourSections(accessor, chunkX + 1, chunkY, chunkZ);
/*  117 */     initNeighbourSections(accessor, chunkX, chunkY, chunkZ - 1);
/*  118 */     initNeighbourSections(accessor, chunkX, chunkY, chunkZ + 1);
/*      */   }
/*      */ 
/*      */   
/*      */   private void initNeighbourSections(@Nonnull LocalCachedChunkAccessor accessor, int x, int y, int z) {
/*  123 */     WorldChunk chunk = accessor.getChunkIfInMemory(x, z);
/*  124 */     if (chunk == null)
/*      */       return; 
/*  126 */     if (y < 9) initSection(chunk, x, y + 1, z); 
/*  127 */     if (y > 0) initSection(chunk, x, y - 1, z);
/*      */   
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public CalculationResult calculateLight(@Nonnull Vector3i chunkPosition) {
/*  133 */     int chunkX = chunkPosition.x;
/*  134 */     int chunkY = chunkPosition.y;
/*  135 */     int chunkZ = chunkPosition.z;
/*      */     
/*  137 */     WorldChunk worldChunk = this.chunkLightingManager.getWorld().getChunkIfInMemory(ChunkUtil.indexChunk(chunkX, chunkZ));
/*  138 */     if (worldChunk == null) return CalculationResult.NOT_LOADED;
/*      */     
/*  140 */     AtomicLong chunkLightTiming = worldChunk.chunkLightTiming;
/*  141 */     boolean fineLoggable = this.chunkLightingManager.getLogger().at(Level.FINE).isEnabled();
/*      */     
/*  143 */     LocalCachedChunkAccessor accessor = LocalCachedChunkAccessor.atChunkCoords((ChunkAccessor)this.chunkLightingManager.getWorld(), chunkX, chunkZ, 1);
/*  144 */     accessor.overwrite(worldChunk);
/*      */ 
/*      */     
/*  147 */     Objects.requireNonNull(accessor); CompletableFuture.runAsync(accessor::cacheChunksInRadius, (Executor)this.chunkLightingManager.getWorld()).join();
/*      */     
/*  149 */     BlockSection toSection = worldChunk.getBlockChunk().getSectionAtIndex(chunkY);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  156 */     FluidSection fluidSection = CompletableFuture.<FluidSection>supplyAsync(() -> { Ref<ChunkStore> section = this.chunkLightingManager.getWorld().getChunkStore().getChunkSectionReference(chunkX, chunkY, chunkZ); return (section == null) ? null : (FluidSection)section.getStore().getComponent(section, FluidSection.getComponentType()); }(Executor)this.chunkLightingManager.getWorld()).join();
/*  157 */     if (fluidSection == null) return CalculationResult.NOT_LOADED;
/*      */ 
/*      */     
/*  160 */     if (toSection.hasLocalLight() && toSection.hasGlobalLight()) {
/*  161 */       initNeighbours(accessor, chunkX, chunkY, chunkZ);
/*  162 */       return CalculationResult.DONE;
/*      */     } 
/*      */     
/*  165 */     if (!toSection.hasLocalLight()) {
/*  166 */       CalculationResult localLightResult = updateLocalLight(accessor, worldChunk, chunkX, chunkY, chunkZ, toSection, fluidSection, chunkLightTiming, fineLoggable);
/*  167 */       switch (localLightResult) { case NOT_LOADED: case INVALIDATED:
/*      */         case WAITING_FOR_NEIGHBOUR:
/*  169 */           return localLightResult; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  176 */       initNeighbours(accessor, chunkX, chunkY, chunkZ);
/*      */     } 
/*      */     
/*  179 */     if (!toSection.hasGlobalLight()) {
/*  180 */       CalculationResult globalLightResult = updateGlobalLight(accessor, worldChunk, chunkX, chunkY, chunkZ, toSection, chunkLightTiming, fineLoggable);
/*  181 */       switch (globalLightResult) { case NOT_LOADED: case INVALIDATED:
/*      */         case WAITING_FOR_NEIGHBOUR:
/*  183 */           return globalLightResult; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     } 
/*  191 */     if (fineLoggable) {
/*  192 */       long chunkDiff = chunkLightTiming.get();
/*  193 */       boolean done = (chunkDiff != 0L);
/*  194 */       for (int i = 0; i < 10; i++) {
/*  195 */         BlockSection section = worldChunk.getBlockChunk().getSectionAtIndex(i);
/*  196 */         done = (done && section.hasLocalLight() && section.hasGlobalLight());
/*      */       } 
/*      */       
/*  199 */       if (done) {
/*  200 */         this.avgChunk.add(chunkDiff);
/*  201 */         this.chunkLightingManager.getLogger().at(Level.FINE).log("Flood Chunk: Took %s at %d, %d - Avg: %s", 
/*  202 */             FormatUtil.nanosToString(chunkDiff), Integer.valueOf(chunkX), Integer.valueOf(chunkZ), FormatUtil.nanosToString((long)this.avgChunk.get()));
/*      */       } 
/*      */     } 
/*      */     
/*  206 */     if (BlockChunk.SEND_LOCAL_LIGHTING_DATA || BlockChunk.SEND_GLOBAL_LIGHTING_DATA) worldChunk.getBlockChunk().invalidateChunkSection(chunkY); 
/*  207 */     return CalculationResult.DONE;
/*      */   }
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public CalculationResult updateLocalLight(LocalCachedChunkAccessor accessor, @Nonnull WorldChunk worldChunk, int chunkX, int chunkY, int chunkZ, @Nonnull BlockSection toSection, @Nonnull FluidSection fluidSection, @Nonnull AtomicLong chunkLightTiming, boolean fineLoggable) {
/*      */     ChunkLightDataBuilder localLight;
/*  214 */     long start = System.nanoTime();
/*      */     
/*  216 */     boolean solidAir = (toSection.isSolidAir() && fluidSection.isEmpty());
/*      */ 
/*      */     
/*  219 */     if (solidAir) {
/*  220 */       localLight = floodEmptyChunkSection(worldChunk, toSection.getLocalChangeCounter(), chunkY);
/*      */     } else {
/*  222 */       localLight = floodChunkSection(worldChunk, toSection, fluidSection, chunkY);
/*      */     } 
/*      */ 
/*      */     
/*  226 */     toSection.setLocalLight(localLight);
/*  227 */     worldChunk.markNeedsSaving();
/*      */     
/*  229 */     if (fineLoggable) {
/*  230 */       long end = System.nanoTime();
/*  231 */       long diff = end - start;
/*  232 */       if (solidAir) {
/*  233 */         this.emptyAvg.add(diff);
/*      */       } else {
/*  235 */         this.blocksAvg.add(diff);
/*      */       } 
/*  237 */       chunkLightTiming.addAndGet(diff);
/*  238 */       this.chunkLightingManager.getLogger().at(Level.FINER).log("Flood Chunk Section (local): Took %s at %d, %d, %d - %s Avg: %s", FormatUtil.nanosToString(diff), Integer.valueOf(chunkX), Integer.valueOf(chunkY), Integer.valueOf(chunkZ), solidAir ? "air" : "blocks", FormatUtil.nanosToString((long)(solidAir ? this.emptyAvg.get() : this.blocksAvg.get())));
/*      */     } 
/*      */     
/*  241 */     if (!toSection.hasLocalLight()) {
/*      */       
/*  243 */       this.chunkLightingManager.getLogger().at(Level.FINEST).log("Chunk Section still needs relight! (local) %d, %d, %d - %d != %d (counter != id)", Integer.valueOf(chunkX), Integer.valueOf(chunkY), Integer.valueOf(chunkZ), Short.valueOf(toSection.getLocalChangeCounter()), Short.valueOf(toSection.getLocalLight().getChangeId()));
/*  244 */       return CalculationResult.INVALIDATED;
/*      */     } 
/*  246 */     return CalculationResult.DONE;
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public CalculationResult updateGlobalLight(@Nonnull LocalCachedChunkAccessor accessor, @Nonnull WorldChunk worldChunk, int chunkX, int chunkY, int chunkZ, @Nonnull BlockSection toSection, @Nonnull AtomicLong chunkLightTiming, boolean fineLoggable) {
/*  251 */     long start = System.nanoTime();
/*      */     
/*  253 */     if (testNeighboursForLocalLight(accessor, worldChunk, chunkX, chunkY, chunkZ)) return CalculationResult.WAITING_FOR_NEIGHBOUR;
/*      */ 
/*      */     
/*  256 */     ChunkLightDataBuilder globalLight = new ChunkLightDataBuilder(toSection.getLocalLight(), toSection.getGlobalChangeCounter());
/*      */     
/*  258 */     BitSet bitSetQueue = new BitSet(32768);
/*      */     
/*  260 */     propagateSides(toSection, globalLight, bitSetQueue);
/*      */     
/*  262 */     propagateEdges(toSection, globalLight, bitSetQueue);
/*      */     
/*  264 */     propagateCorners(toSection, globalLight, bitSetQueue);
/*      */     
/*  266 */     propagateLight(bitSetQueue, toSection, globalLight);
/*      */     
/*  268 */     toSection.setGlobalLight(globalLight);
/*  269 */     worldChunk.markNeedsSaving();
/*      */     
/*  271 */     if (fineLoggable) {
/*  272 */       long end = System.nanoTime();
/*  273 */       long diff = end - start;
/*  274 */       chunkLightTiming.addAndGet(diff);
/*  275 */       this.borderAvg.add(diff);
/*  276 */       this.chunkLightingManager.getLogger().at(Level.FINER).log("Flood Chunk Section (global): Took " + FormatUtil.nanosToString(diff) + " at " + chunkX + ", " + chunkY + ", " + chunkZ + " - Avg: " + 
/*  277 */           FormatUtil.nanosToString((long)this.borderAvg.get()));
/*      */     } 
/*      */     
/*  280 */     if (!toSection.hasGlobalLight()) {
/*      */       
/*  282 */       this.chunkLightingManager.getLogger().at(Level.FINEST).log("Chunk Section still needs relight! (global) %d, %d, %d - %d != %d (counter != id)", 
/*  283 */           Integer.valueOf(chunkX), Integer.valueOf(chunkY), Integer.valueOf(chunkZ), Short.valueOf(toSection.getGlobalChangeCounter()), Short.valueOf(toSection.getGlobalLight().getChangeId()));
/*  284 */       return CalculationResult.INVALIDATED;
/*      */     } 
/*  286 */     return CalculationResult.DONE;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean invalidateLightAtBlock(@Nonnull WorldChunk worldChunk, int blockX, int blockY, int blockZ, @Nonnull BlockType blockType, int oldHeight, int newHeight) {
/*  291 */     int chunkX = worldChunk.getX();
/*  292 */     int chunkY = blockY >> 5;
/*  293 */     int chunkZ = worldChunk.getZ();
/*      */ 
/*      */     
/*  296 */     int oldHeightChunk = oldHeight >> 5;
/*  297 */     int newHeightChunk = newHeight >> 5;
/*      */     
/*  299 */     int from = Math.max(MathUtil.minValue(oldHeightChunk, newHeightChunk, chunkY), 0);
/*  300 */     int to = MathUtil.maxValue(oldHeightChunk, newHeightChunk, chunkY) + 1;
/*      */     
/*  302 */     boolean handled = invalidateLightInChunkSections(worldChunk, from, to);
/*  303 */     this.chunkLightingManager.getLogger().at(Level.FINER).log("updateLightAtBlock(%d, %d, %d, %s): %d, %d, %d", Integer.valueOf(blockX), Integer.valueOf(blockY), Integer.valueOf(blockZ), blockType.getId(), Integer.valueOf(chunkX), Integer.valueOf(chunkY), Integer.valueOf(chunkZ));
/*  304 */     return handled;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean invalidateLightInChunkSections(@Nonnull WorldChunk worldChunk, int sectionIndexFrom, int sectionIndexTo) {
/*  309 */     int chunkX = worldChunk.getX();
/*  310 */     int chunkZ = worldChunk.getZ();
/*      */     
/*  312 */     World world = this.chunkLightingManager.getWorld();
/*  313 */     LocalCachedChunkAccessor accessor = LocalCachedChunkAccessor.atChunkCoords((ChunkAccessor)world, chunkX, chunkZ, 1);
/*  314 */     accessor.overwrite(worldChunk);
/*      */ 
/*      */     
/*  317 */     if (!world.isInThread()) {
/*  318 */       Objects.requireNonNull(accessor); CompletableFuture.runAsync(accessor::cacheChunksInRadius, (Executor)world).join();
/*      */     } else {
/*  320 */       accessor.cacheChunksInRadius();
/*      */     } 
/*      */     
/*      */     int x;
/*  324 */     for (x = chunkX - 1; x <= chunkX + 1; x++) {
/*  325 */       for (int z = chunkZ - 1; z <= chunkZ + 1; z++) {
/*  326 */         WorldChunk worldChunkTemp = accessor.getChunkIfInMemory(x, z);
/*  327 */         if (worldChunkTemp != null)
/*      */         {
/*  329 */           for (int y = sectionIndexTo - 1; y >= sectionIndexFrom; y--) {
/*  330 */             BlockSection section = worldChunkTemp.getBlockChunk().getSectionAtIndex(y);
/*  331 */             if (worldChunkTemp == worldChunk) {
/*  332 */               section.invalidateLocalLight();
/*      */             } else {
/*  334 */               section.invalidateGlobalLight();
/*      */             } 
/*  336 */             if (BlockChunk.SEND_LOCAL_LIGHTING_DATA || BlockChunk.SEND_GLOBAL_LIGHTING_DATA) worldChunkTemp.getBlockChunk().invalidateChunkSection(y); 
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*  341 */     for (x = chunkX - 1; x <= chunkX + 1; x++) {
/*  342 */       for (int z = chunkZ - 1; z <= chunkZ + 1; z++) {
/*  343 */         WorldChunk worldChunkTemp = accessor.getChunkIfInMemory(x, z);
/*  344 */         if (worldChunkTemp != null)
/*      */         {
/*  346 */           for (int y = sectionIndexTo - 1; y >= sectionIndexFrom; y--) {
/*  347 */             this.chunkLightingManager.addToQueue(new Vector3i(x, y, z));
/*      */           }
/*      */         }
/*      */       } 
/*      */     } 
/*  352 */     return false;
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   private ChunkLightDataBuilder floodEmptyChunkSection(@Nonnull WorldChunk worldChunk, short changeCounter, int chunkY) {
/*  357 */     int sectionY = chunkY * 32;
/*      */     
/*  359 */     ChunkLightDataBuilder light = new ChunkLightDataBuilder(changeCounter);
/*  360 */     BitSet bitSetQueue = new BitSet(1024);
/*      */ 
/*      */     
/*  363 */     for (int x = 0; x < 32; x++) {
/*  364 */       for (int z = 0; z < 32; z++) {
/*  365 */         int column = ChunkUtil.indexColumn(x, z);
/*  366 */         short height = worldChunk.getHeight(column);
/*      */ 
/*      */ 
/*      */         
/*  370 */         if (sectionY > height) {
/*  371 */           for (int y = 0; y < 32; y++) {
/*  372 */             light.setLight(ChunkUtil.indexBlockFromColumn(column, y), 3, (byte)15);
/*      */           }
/*  374 */           bitSetQueue.set(column);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  382 */     if (bitSetQueue.cardinality() < 1024) {
/*      */       
/*  384 */       IntOpenHashSet intOpenHashSet = new IntOpenHashSet(1024);
/*      */       
/*  386 */       int counter = 0;
/*      */       while (true) {
/*  388 */         int column = bitSetQueue.nextSetBit(counter);
/*  389 */         if (column == -1) {
/*      */           
/*  391 */           if (bitSetQueue.isEmpty()) {
/*      */             break;
/*      */           }
/*  394 */           counter = 0;
/*      */           
/*      */           continue;
/*      */         } 
/*  398 */         bitSetQueue.clear(column);
/*  399 */         counter = column;
/*      */         
/*  401 */         int i = ChunkUtil.xFromColumn(column);
/*  402 */         int z = ChunkUtil.zFromColumn(column);
/*      */         
/*  404 */         byte skyLight = light.getLight(column, 3);
/*      */         
/*  406 */         byte propagatedValue = (byte)(skyLight - 1);
/*  407 */         if (propagatedValue < 1)
/*      */           continue; 
/*  409 */         for (Vector2i side : Vector2i.DIRECTIONS) {
/*  410 */           int nx = i + side.x;
/*  411 */           int nz = z + side.y;
/*  412 */           if (nx >= 0 && nx < 32 && 
/*  413 */             nz >= 0 && nz < 32) {
/*      */             
/*  415 */             int neighbourColumn = ChunkUtil.indexColumn(nx, nz);
/*  416 */             byte neighbourSkyLight = light.getLight(neighbourColumn, 3);
/*      */             
/*  418 */             if (neighbourSkyLight < propagatedValue) {
/*  419 */               light.setLight(neighbourColumn, 3, propagatedValue);
/*  420 */               intOpenHashSet.add(neighbourColumn);
/*  421 */               if (propagatedValue > 1) bitSetQueue.set(neighbourColumn);
/*      */             
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*  427 */       IntIterator iterator = intOpenHashSet.iterator();
/*  428 */       while (iterator.hasNext()) {
/*  429 */         int column = iterator.nextInt();
/*  430 */         byte skyLight = light.getLight(column, 3);
/*  431 */         for (int y = 1; y < 32; y++) {
/*  432 */           light.setLight(ChunkUtil.indexBlockFromColumn(column, y), 3, skyLight);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  437 */     return light;
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   private ChunkLightDataBuilder floodChunkSection(@Nonnull WorldChunk worldChunk, @Nonnull BlockSection toSection, @Nonnull FluidSection fluidSection, int chunkY) {
/*  442 */     int sectionY = chunkY * 32;
/*      */     
/*  444 */     ChunkLightDataBuilder toLight = new ChunkLightDataBuilder(toSection.getLocalChangeCounter());
/*  445 */     BitSet bitSetQueue = new BitSet(32768);
/*      */     
/*  447 */     for (int x = 0; x < 32; x++) {
/*  448 */       for (int z = 0; z < 32; z++) {
/*  449 */         int column = ChunkUtil.indexColumn(x, z);
/*  450 */         short height = worldChunk.getHeight(column);
/*  451 */         for (int y = 0; y < 32; y++) {
/*  452 */           int blockIndex = ChunkUtil.indexBlockFromColumn(column, y);
/*      */           
/*  454 */           byte skyValue = getSkyValue(worldChunk, chunkY, x, y, z, sectionY, height);
/*  455 */           short lightValue = (short)(skyValue << 12);
/*      */           
/*  457 */           int blockId = toSection.get(blockIndex);
/*  458 */           BlockType blockType = (BlockType)BlockType.getAssetMap().getAsset(blockId);
/*  459 */           ColorLight blockTypeLight = blockType.getLight();
/*      */           
/*  461 */           int fluidId = fluidSection.getFluidId(blockIndex);
/*  462 */           Fluid fluid = (Fluid)Fluid.getAssetMap().getAsset(fluidId);
/*  463 */           ColorLight fluidLight = fluid.getLight();
/*      */           
/*  465 */           if (blockTypeLight != null && fluidLight != null) {
/*  466 */             lightValue = ChunkLightData.combineLightValues(
/*  467 */                 (byte)Math.max(blockTypeLight.red, fluidLight.red), 
/*  468 */                 (byte)Math.max(blockTypeLight.green, fluidLight.green), 
/*  469 */                 (byte)Math.max(blockTypeLight.blue, fluidLight.blue), skyValue);
/*  470 */           } else if (fluidLight != null) {
/*  471 */             lightValue = ChunkLightData.combineLightValues(fluidLight.red, fluidLight.green, fluidLight.blue, skyValue);
/*  472 */           } else if (blockTypeLight != null) {
/*  473 */             lightValue = ChunkLightData.combineLightValues(blockTypeLight.red, blockTypeLight.green, blockTypeLight.blue, skyValue);
/*      */           } 
/*      */           
/*  476 */           if (lightValue != 0) {
/*  477 */             toLight.setLightRaw(blockIndex, lightValue);
/*  478 */             bitSetQueue.set(blockIndex);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  484 */     propagateLight(bitSetQueue, toSection, toLight);
/*      */     
/*  486 */     return toLight;
/*      */   }
/*      */   
/*      */   protected byte getSkyValue(WorldChunk worldChunk, int chunkY, int blockX, int blockY, int blockZ, int sectionY, int height) {
/*  490 */     int originY = sectionY + blockY;
/*  491 */     boolean hasSky = (originY >= height);
/*  492 */     return (byte)(hasSky ? 15 : 0);
/*      */   }
/*      */   
/*      */   private void propagateLight(@Nonnull BitSet bitSetQueue, @Nonnull BlockSection section, @Nonnull ChunkLightDataBuilder light) {
/*  496 */     int counter = 0;
/*      */     while (true) {
/*  498 */       int blockIndex = bitSetQueue.nextSetBit(counter);
/*  499 */       if (blockIndex == -1) {
/*      */         
/*  501 */         if (bitSetQueue.isEmpty()) {
/*      */           break;
/*      */         }
/*  504 */         counter = 0;
/*      */         
/*      */         continue;
/*      */       } 
/*  508 */       bitSetQueue.clear(blockIndex);
/*  509 */       counter = blockIndex;
/*      */       
/*  511 */       BlockType fromBlockType = (BlockType)BlockType.getAssetMap().getAsset(section.get(blockIndex));
/*  512 */       Opacity fromOpacity = fromBlockType.getOpacity();
/*      */ 
/*      */       
/*  515 */       if (fromOpacity == Opacity.Solid)
/*      */         continue; 
/*  517 */       short lightValue = light.getLightRaw(blockIndex);
/*  518 */       byte redLight = ChunkLightData.getLightValue(lightValue, 0);
/*  519 */       byte greenLight = ChunkLightData.getLightValue(lightValue, 1);
/*  520 */       byte blueLight = ChunkLightData.getLightValue(lightValue, 2);
/*  521 */       byte skyLight = ChunkLightData.getLightValue(lightValue, 3);
/*  522 */       if (redLight < 2 && greenLight < 2 && blueLight < 2 && skyLight < 2) {
/*      */         continue;
/*      */       }
/*      */ 
/*      */       
/*  527 */       byte propagatedRedValue = (byte)(redLight - 1);
/*  528 */       byte propagatedGreenValue = (byte)(greenLight - 1);
/*  529 */       byte propagatedBlueValue = (byte)(blueLight - 1);
/*  530 */       byte propagatedSkyValue = (byte)(skyLight - 1);
/*      */       
/*  532 */       if (fromOpacity == Opacity.Semitransparent || fromOpacity == Opacity.Cutout) {
/*      */         
/*  534 */         propagatedRedValue = (byte)(propagatedRedValue - 1);
/*  535 */         propagatedGreenValue = (byte)(propagatedGreenValue - 1);
/*  536 */         propagatedBlueValue = (byte)(propagatedBlueValue - 1);
/*  537 */         propagatedSkyValue = (byte)(propagatedSkyValue - 1);
/*      */       } 
/*      */       
/*  540 */       if (propagatedRedValue < 1 && propagatedGreenValue < 1 && propagatedBlueValue < 1 && propagatedSkyValue < 1) {
/*      */         continue;
/*      */       }
/*      */       
/*  544 */       int x = ChunkUtil.xFromIndex(blockIndex);
/*  545 */       int y = ChunkUtil.yFromIndex(blockIndex);
/*  546 */       int z = ChunkUtil.zFromIndex(blockIndex);
/*      */       
/*  548 */       for (Vector3i side : Vector3i.BLOCK_SIDES) {
/*  549 */         int nx = x + side.x;
/*  550 */         if (nx >= 0 && nx < 32) {
/*      */           
/*  552 */           int ny = y + side.y;
/*  553 */           if (ny >= 0 && ny < 32) {
/*      */             
/*  555 */             int nz = z + side.z;
/*  556 */             if (nz >= 0 && nz < 32) {
/*      */               
/*  558 */               int neighbourBlock = ChunkUtil.indexBlock(nx, ny, nz);
/*      */               
/*  560 */               propagateLight(bitSetQueue, propagatedRedValue, propagatedGreenValue, propagatedBlueValue, propagatedSkyValue, section, light, neighbourBlock);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   public boolean testNeighboursForLocalLight(@Nonnull LocalCachedChunkAccessor accessor, @Nonnull WorldChunk worldChunk, int chunkX, int chunkY, int chunkZ) {
/*  568 */     Vector3i[][] blockParts = Vector3i.BLOCK_PARTS;
/*  569 */     for (int partType = 0; partType < this.fromSections.length; partType++) {
/*      */       
/*  571 */       BlockSection[] partSections = this.fromSections[partType];
/*      */       
/*  573 */       Arrays.fill((Object[])partSections, (Object)null);
/*      */ 
/*      */       
/*  576 */       Vector3i[] directions = blockParts[partType];
/*  577 */       for (int i = 0; i < directions.length; i++) {
/*  578 */         Vector3i side = directions[i];
/*  579 */         int nx = chunkX + side.x;
/*  580 */         int ny = chunkY + side.y;
/*  581 */         int nz = chunkZ + side.z;
/*      */ 
/*      */         
/*  584 */         if (ny >= 0 && ny < 10)
/*      */         {
/*  586 */           if (nx == chunkX && nz == chunkZ) {
/*  587 */             BlockSection fromSection = worldChunk.getBlockChunk().getSectionAtIndex(ny);
/*      */ 
/*      */             
/*  590 */             if (!fromSection.hasLocalLight()) return true;
/*      */             
/*  592 */             partSections[i] = fromSection;
/*      */           } else {
/*  594 */             WorldChunk neighbourChunk = accessor.getChunkIfInMemory(nx, nz);
/*      */ 
/*      */             
/*  597 */             if (neighbourChunk == null) return true;
/*      */             
/*  599 */             BlockSection fromSection = neighbourChunk.getBlockChunk().getSectionAtIndex(ny);
/*      */ 
/*      */             
/*  602 */             if (!fromSection.hasLocalLight()) return true; 
/*  603 */             partSections[i] = fromSection;
/*      */           }  } 
/*      */       } 
/*      */     } 
/*  607 */     return false;
/*      */   }
/*      */   
/*      */   public void propagateSides(@Nonnull BlockSection toSection, @Nonnull ChunkLightDataBuilder globalLight, @Nonnull BitSet bitSetQueue) {
/*  611 */     BlockSection[] fromSectionsSides = this.fromSections[0];
/*      */     
/*  613 */     int i = 0;
/*      */ 
/*      */ 
/*      */     
/*  617 */     propagateSide(bitSetQueue, fromSectionsSides[i++], toSection, globalLight, (a, b) -> ChunkUtil.indexBlock(a, 0, b), (a, b) -> ChunkUtil.indexBlock(a, 31, b));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  624 */     propagateSide(bitSetQueue, fromSectionsSides[i++], toSection, globalLight, (a, b) -> ChunkUtil.indexBlock(a, 31, b), (a, b) -> ChunkUtil.indexBlock(a, 0, b));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  631 */     propagateSide(bitSetQueue, fromSectionsSides[i++], toSection, globalLight, (a, b) -> ChunkUtil.indexBlock(a, b, 31), (a, b) -> ChunkUtil.indexBlock(a, b, 0));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  638 */     propagateSide(bitSetQueue, fromSectionsSides[i++], toSection, globalLight, (a, b) -> ChunkUtil.indexBlock(a, b, 0), (a, b) -> ChunkUtil.indexBlock(a, b, 31));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  645 */     propagateSide(bitSetQueue, fromSectionsSides[i++], toSection, globalLight, (a, b) -> ChunkUtil.indexBlock(31, a, b), (a, b) -> ChunkUtil.indexBlock(0, a, b));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  652 */     propagateSide(bitSetQueue, fromSectionsSides[i++], toSection, globalLight, (a, b) -> ChunkUtil.indexBlock(0, a, b), (a, b) -> ChunkUtil.indexBlock(31, a, b));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void propagateSide(@Nonnull BitSet bitSetQueue, @Nullable BlockSection fromSection, @Nonnull BlockSection toSection, @Nonnull ChunkLightDataBuilder toLight, @Nonnull IntBinaryOperator fromIndex, @Nonnull IntBinaryOperator toIndex) {
/*  663 */     if (fromSection == null)
/*  664 */       return;  ChunkLightData fromLight = fromSection.getLocalLight();
/*      */     
/*  666 */     for (int a = 0; a < 32; a++) {
/*  667 */       for (int b = 0; b < 32; b++) {
/*  668 */         int fromBlockIndex = fromIndex.applyAsInt(a, b);
/*  669 */         int toBlockIndex = toIndex.applyAsInt(a, b);
/*      */         
/*  671 */         BlockType fromBlockType = (BlockType)BlockType.getAssetMap().getAsset(fromSection.get(fromBlockIndex));
/*  672 */         Opacity fromOpacity = fromBlockType.getOpacity();
/*      */ 
/*      */         
/*  675 */         if (fromOpacity != Opacity.Solid) {
/*      */           
/*  677 */           short lightValue = fromLight.getLightRaw(fromBlockIndex);
/*  678 */           byte redLight = ChunkLightData.getLightValue(lightValue, 0);
/*  679 */           byte greenLight = ChunkLightData.getLightValue(lightValue, 1);
/*  680 */           byte blueLight = ChunkLightData.getLightValue(lightValue, 2);
/*  681 */           byte skyLight = ChunkLightData.getLightValue(lightValue, 3);
/*  682 */           if (redLight >= 2 || greenLight >= 2 || blueLight >= 2 || skyLight >= 2) {
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  687 */             byte propagatedRedValue = (byte)(redLight - 1);
/*  688 */             byte propagatedGreenValue = (byte)(greenLight - 1);
/*  689 */             byte propagatedBlueValue = (byte)(blueLight - 1);
/*  690 */             byte propagatedSkyValue = (byte)(skyLight - 1);
/*      */             
/*  692 */             if (fromOpacity == Opacity.Semitransparent || fromOpacity == Opacity.Cutout) {
/*      */               
/*  694 */               propagatedRedValue = (byte)(propagatedRedValue - 1);
/*  695 */               propagatedGreenValue = (byte)(propagatedGreenValue - 1);
/*  696 */               propagatedBlueValue = (byte)(propagatedBlueValue - 1);
/*  697 */               propagatedSkyValue = (byte)(propagatedSkyValue - 1);
/*      */             } 
/*      */             
/*  700 */             if (propagatedRedValue >= 1 || propagatedGreenValue >= 1 || propagatedBlueValue >= 1 || propagatedSkyValue >= 1)
/*      */             {
/*      */ 
/*      */               
/*  704 */               propagateLight(bitSetQueue, propagatedRedValue, propagatedGreenValue, propagatedBlueValue, propagatedSkyValue, toSection, toLight, toBlockIndex); } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public void propagateEdges(@Nonnull BlockSection toSection, @Nonnull ChunkLightDataBuilder globalLight, @Nonnull BitSet bitSetQueue) {
/*  712 */     BlockSection[] fromSectionsEdges = this.fromSections[1];
/*      */     
/*  714 */     int i = 0;
/*      */ 
/*      */ 
/*      */     
/*  718 */     propagateEdge(bitSetQueue, fromSectionsEdges[i++], toSection, globalLight, a -> ChunkUtil.indexBlock(a, 0, 31), a -> ChunkUtil.indexBlock(a, 31, 0));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  725 */     propagateEdge(bitSetQueue, fromSectionsEdges[i++], toSection, globalLight, a -> ChunkUtil.indexBlock(a, 31, 31), a -> ChunkUtil.indexBlock(a, 0, 0));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  732 */     propagateEdge(bitSetQueue, fromSectionsEdges[i++], toSection, globalLight, a -> ChunkUtil.indexBlock(a, 0, 0), a -> ChunkUtil.indexBlock(a, 31, 31));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  739 */     propagateEdge(bitSetQueue, fromSectionsEdges[i++], toSection, globalLight, a -> ChunkUtil.indexBlock(a, 31, 0), a -> ChunkUtil.indexBlock(a, 0, 31));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  746 */     propagateEdge(bitSetQueue, fromSectionsEdges[i++], toSection, globalLight, a -> ChunkUtil.indexBlock(31, 0, a), a -> ChunkUtil.indexBlock(0, 31, a));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  753 */     propagateEdge(bitSetQueue, fromSectionsEdges[i++], toSection, globalLight, a -> ChunkUtil.indexBlock(31, 31, a), a -> ChunkUtil.indexBlock(0, 0, a));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  760 */     propagateEdge(bitSetQueue, fromSectionsEdges[i++], toSection, globalLight, a -> ChunkUtil.indexBlock(0, 0, a), a -> ChunkUtil.indexBlock(31, 31, a));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  767 */     propagateEdge(bitSetQueue, fromSectionsEdges[i++], toSection, globalLight, a -> ChunkUtil.indexBlock(0, 31, a), a -> ChunkUtil.indexBlock(31, 0, a));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  774 */     propagateEdge(bitSetQueue, fromSectionsEdges[i++], toSection, globalLight, a -> ChunkUtil.indexBlock(31, a, 31), a -> ChunkUtil.indexBlock(0, a, 0));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  781 */     propagateEdge(bitSetQueue, fromSectionsEdges[i++], toSection, globalLight, a -> ChunkUtil.indexBlock(0, a, 31), a -> ChunkUtil.indexBlock(31, a, 0));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  788 */     propagateEdge(bitSetQueue, fromSectionsEdges[i++], toSection, globalLight, a -> ChunkUtil.indexBlock(31, a, 0), a -> ChunkUtil.indexBlock(0, a, 31));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  795 */     propagateEdge(bitSetQueue, fromSectionsEdges[i++], toSection, globalLight, a -> ChunkUtil.indexBlock(0, a, 0), a -> ChunkUtil.indexBlock(31, a, 31));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void propagateEdge(@Nonnull BitSet bitSetQueue, @Nullable BlockSection fromSection, @Nonnull BlockSection toSection, @Nonnull ChunkLightDataBuilder toLight, @Nonnull Int2IntFunction fromIndex, @Nonnull Int2IntFunction toIndex) {
/*  806 */     if (fromSection == null)
/*  807 */       return;  ChunkLightData fromLight = fromSection.getLocalLight();
/*      */     
/*  809 */     for (int a = 0; a < 32; a++) {
/*  810 */       int fromBlockIndex = fromIndex.applyAsInt(a);
/*  811 */       int toBlockIndex = toIndex.applyAsInt(a);
/*      */       
/*  813 */       BlockType fromBlockType = (BlockType)BlockType.getAssetMap().getAsset(fromSection.get(fromBlockIndex));
/*  814 */       Opacity fromOpacity = fromBlockType.getOpacity();
/*      */ 
/*      */       
/*  817 */       if (fromOpacity != Opacity.Solid) {
/*      */         
/*  819 */         short lightValue = fromLight.getLightRaw(fromBlockIndex);
/*  820 */         byte redLight = ChunkLightData.getLightValue(lightValue, 0);
/*  821 */         byte greenLight = ChunkLightData.getLightValue(lightValue, 1);
/*  822 */         byte blueLight = ChunkLightData.getLightValue(lightValue, 2);
/*  823 */         byte skyLight = ChunkLightData.getLightValue(lightValue, 3);
/*  824 */         if (redLight >= 3 || greenLight >= 3 || blueLight >= 3 || skyLight >= 3) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  833 */           byte propagatedRedValue = (byte)(redLight - 2);
/*  834 */           byte propagatedGreenValue = (byte)(greenLight - 2);
/*  835 */           byte propagatedBlueValue = (byte)(blueLight - 2);
/*  836 */           byte propagatedSkyValue = (byte)(skyLight - 2);
/*      */           
/*  838 */           if (fromOpacity == Opacity.Semitransparent || fromOpacity == Opacity.Cutout) {
/*      */             
/*  840 */             propagatedRedValue = (byte)(propagatedRedValue - 1);
/*  841 */             propagatedGreenValue = (byte)(propagatedGreenValue - 1);
/*  842 */             propagatedBlueValue = (byte)(propagatedBlueValue - 1);
/*  843 */             propagatedSkyValue = (byte)(propagatedSkyValue - 1);
/*      */           } 
/*      */           
/*  846 */           if (propagatedRedValue >= 1 || propagatedGreenValue >= 1 || propagatedBlueValue >= 1 || propagatedSkyValue >= 1)
/*      */           {
/*      */ 
/*      */             
/*  850 */             propagateLight(bitSetQueue, propagatedRedValue, propagatedGreenValue, propagatedBlueValue, propagatedSkyValue, toSection, toLight, toBlockIndex); } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public void propagateCorners(@Nonnull BlockSection toSection, @Nonnull ChunkLightDataBuilder globalLight, @Nonnull BitSet bitSetQueue) {
/*  857 */     BlockSection[] fromSectionsCorners = this.fromSections[2];
/*      */     
/*  859 */     int i = 0;
/*      */ 
/*      */ 
/*      */     
/*  863 */     propagateCorner(bitSetQueue, fromSectionsCorners[i++], toSection, globalLight, 
/*      */         
/*  865 */         ChunkUtil.indexBlock(31, 0, 31), 
/*  866 */         ChunkUtil.indexBlock(0, 31, 0));
/*      */ 
/*      */ 
/*      */     
/*  870 */     propagateCorner(bitSetQueue, fromSectionsCorners[i++], toSection, globalLight, 
/*      */         
/*  872 */         ChunkUtil.indexBlock(0, 0, 31), 
/*  873 */         ChunkUtil.indexBlock(31, 31, 0));
/*      */ 
/*      */ 
/*      */     
/*  877 */     propagateCorner(bitSetQueue, fromSectionsCorners[i++], toSection, globalLight, 
/*      */         
/*  879 */         ChunkUtil.indexBlock(31, 31, 31), 
/*  880 */         ChunkUtil.indexBlock(0, 0, 0));
/*      */ 
/*      */ 
/*      */     
/*  884 */     propagateCorner(bitSetQueue, fromSectionsCorners[i++], toSection, globalLight, 
/*      */         
/*  886 */         ChunkUtil.indexBlock(0, 31, 31), 
/*  887 */         ChunkUtil.indexBlock(31, 0, 0));
/*      */ 
/*      */ 
/*      */     
/*  891 */     propagateCorner(bitSetQueue, fromSectionsCorners[i++], toSection, globalLight, 
/*      */         
/*  893 */         ChunkUtil.indexBlock(31, 0, 0), 
/*  894 */         ChunkUtil.indexBlock(0, 31, 31));
/*      */ 
/*      */ 
/*      */     
/*  898 */     propagateCorner(bitSetQueue, fromSectionsCorners[i++], toSection, globalLight, 
/*      */         
/*  900 */         ChunkUtil.indexBlock(0, 0, 0), 
/*  901 */         ChunkUtil.indexBlock(31, 31, 31));
/*      */ 
/*      */ 
/*      */     
/*  905 */     propagateCorner(bitSetQueue, fromSectionsCorners[i++], toSection, globalLight, 
/*      */         
/*  907 */         ChunkUtil.indexBlock(31, 31, 0), 
/*  908 */         ChunkUtil.indexBlock(0, 0, 31));
/*      */ 
/*      */ 
/*      */     
/*  912 */     propagateCorner(bitSetQueue, fromSectionsCorners[i++], toSection, globalLight, 
/*      */         
/*  914 */         ChunkUtil.indexBlock(0, 31, 0), 
/*  915 */         ChunkUtil.indexBlock(31, 0, 31));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void propagateCorner(@Nonnull BitSet bitSetQueue, @Nullable BlockSection fromSection, @Nonnull BlockSection toSection, @Nonnull ChunkLightDataBuilder toLight, int fromBlockIndex, int toBlockIndex) {
/*  923 */     if (fromSection == null)
/*  924 */       return;  ChunkLightData fromLight = fromSection.getLocalLight();
/*      */     
/*  926 */     BlockType fromBlockType = (BlockType)BlockType.getAssetMap().getAsset(fromSection.get(fromBlockIndex));
/*  927 */     Opacity fromOpacity = fromBlockType.getOpacity();
/*      */ 
/*      */     
/*  930 */     if (fromOpacity == Opacity.Solid)
/*      */       return; 
/*  932 */     short lightValue = fromLight.getLightRaw(fromBlockIndex);
/*  933 */     byte redLight = ChunkLightData.getLightValue(lightValue, 0);
/*  934 */     byte greenLight = ChunkLightData.getLightValue(lightValue, 1);
/*  935 */     byte blueLight = ChunkLightData.getLightValue(lightValue, 2);
/*  936 */     byte skyLight = ChunkLightData.getLightValue(lightValue, 3);
/*  937 */     if (redLight < 4 && greenLight < 4 && blueLight < 4 && skyLight < 4) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  946 */     byte propagatedRedValue = (byte)(redLight - 3);
/*  947 */     byte propagatedGreenValue = (byte)(greenLight - 3);
/*  948 */     byte propagatedBlueValue = (byte)(blueLight - 3);
/*  949 */     byte propagatedSkyValue = (byte)(skyLight - 3);
/*      */     
/*  951 */     if (fromOpacity == Opacity.Semitransparent || fromOpacity == Opacity.Cutout) {
/*      */       
/*  953 */       propagatedRedValue = (byte)(propagatedRedValue - 1);
/*  954 */       propagatedGreenValue = (byte)(propagatedGreenValue - 1);
/*  955 */       propagatedBlueValue = (byte)(propagatedBlueValue - 1);
/*  956 */       propagatedSkyValue = (byte)(propagatedSkyValue - 1);
/*      */     } 
/*      */     
/*  959 */     if (propagatedRedValue < 1 && propagatedGreenValue < 1 && propagatedBlueValue < 1 && propagatedSkyValue < 1) {
/*      */       return;
/*      */     }
/*      */     
/*  963 */     propagateLight(bitSetQueue, propagatedRedValue, propagatedGreenValue, propagatedBlueValue, propagatedSkyValue, toSection, toLight, toBlockIndex);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void propagateLight(@Nonnull BitSet bitSetQueue, byte propagatedRedValue, byte propagatedGreenValue, byte propagatedBlueValue, byte propagatedSkyValue, @Nonnull BlockSection toSection, @Nonnull ChunkLightDataBuilder toLight, int toBlockIndex) {
/*  972 */     BlockType toBlockType = (BlockType)BlockType.getAssetMap().getAsset(toSection.get(toBlockIndex));
/*  973 */     Opacity toOpacity = toBlockType.getOpacity();
/*      */ 
/*      */     
/*  976 */     if (toOpacity == Opacity.Cutout) {
/*  977 */       propagatedRedValue = (byte)(propagatedRedValue - 1);
/*  978 */       propagatedGreenValue = (byte)(propagatedGreenValue - 1);
/*  979 */       propagatedBlueValue = (byte)(propagatedBlueValue - 1);
/*  980 */       propagatedSkyValue = (byte)(propagatedSkyValue - 1);
/*      */     } 
/*      */     
/*  983 */     if (propagatedRedValue < 1 && propagatedGreenValue < 1 && propagatedBlueValue < 1 && propagatedSkyValue < 1) {
/*      */       return;
/*      */     }
/*      */     
/*  987 */     short oldLightValue = toLight.getLightRaw(toBlockIndex);
/*  988 */     byte neighbourRedLight = ChunkLightData.getLightValue(oldLightValue, 0);
/*  989 */     byte neighbourGreenLight = ChunkLightData.getLightValue(oldLightValue, 1);
/*  990 */     byte neighbourBlueLight = ChunkLightData.getLightValue(oldLightValue, 2);
/*  991 */     byte neighbourSkyLight = ChunkLightData.getLightValue(oldLightValue, 3);
/*      */     
/*  993 */     if (neighbourRedLight < propagatedRedValue) neighbourRedLight = propagatedRedValue; 
/*  994 */     if (neighbourGreenLight < propagatedGreenValue) neighbourGreenLight = propagatedGreenValue; 
/*  995 */     if (neighbourBlueLight < propagatedBlueValue) neighbourBlueLight = propagatedBlueValue; 
/*  996 */     if (neighbourSkyLight < propagatedSkyValue) neighbourSkyLight = propagatedSkyValue;
/*      */     
/*  998 */     short newLightValue = (short)((neighbourRedLight & 0xF) << 0);
/*  999 */     newLightValue = (short)(newLightValue | (neighbourGreenLight & 0xF) << 4);
/* 1000 */     newLightValue = (short)(newLightValue | (neighbourBlueLight & 0xF) << 8);
/* 1001 */     newLightValue = (short)(newLightValue | (neighbourSkyLight & 0xF) << 12);
/*      */     
/* 1003 */     toLight.setLightRaw(toBlockIndex, newLightValue);
/*      */ 
/*      */     
/* 1006 */     if (newLightValue != oldLightValue && (propagatedRedValue > 1 || propagatedGreenValue > 1 || propagatedBlueValue > 1 || propagatedSkyValue > 1))
/*      */     {
/*      */ 
/*      */       
/* 1010 */       bitSetQueue.set(toBlockIndex);
/*      */     }
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\lighting\FloodLightCalculation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */