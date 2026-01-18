/*     */ package com.hypixel.hytale.builtin.hytalegenerator.newsystem;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.ArrayUtil;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.FutureUtils;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.LoggerUtil;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.bounds.Bounds3i;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.chunkgenerator.ChunkGenerator;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.chunkgenerator.ChunkRequest;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.datastructures.voxelspace.VoxelSpace;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.material.Material;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.material.MaterialCache;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.material.SolidMaterial;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle.NBufferBundle;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle.buffers.NBuffer;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle.buffers.NEntityBuffer;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle.buffers.NSimplePixelBuffer;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle.buffers.NVoxelBuffer;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle.buffers.type.NBufferType;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle.buffers.type.NParametrizedBufferType;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.performanceinstruments.TimeInstrument;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.stages.NStage;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.views.NEntityBufferView;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.views.NPixelBufferView;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.views.NVoxelBufferView;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.props.entity.EntityPlacementData;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.threadindexer.WorkerIndexer;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.blocktype.component.BlockPhysics;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.FluidSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.worldgen.GeneratedBlockChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.worldgen.GeneratedBlockStateChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.worldgen.GeneratedChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.worldgen.GeneratedEntityChunk;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.ExecutorService;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class NStagedChunkGenerator implements ChunkGenerator {
/*     */   public static final int WORLD_MIN_Y_BUFFER_GRID = 0;
/*     */   public static final int WORLD_MAX_Y_BUFFER_GRID = 40;
/*     */   public static final int WORLD_HEIGHT_BUFFER_GRID = 40;
/*  54 */   public static final Bounds3i CHUNK_BOUNDS_BUFFER_GRID = new Bounds3i(Vector3i.ZERO, new Vector3i(4, 40, 4));
/*  55 */   public static final Bounds3i SINGLE_BUFFER_TILE_BOUNDS_BUFFER_GRID = new Bounds3i(new Vector3i(0, 0, 0), new Vector3i(NVoxelBuffer.SIZE.x, 320, NVoxelBuffer.SIZE.x));
/*     */   
/*     */   private NBufferType materialOutput_bufferType;
/*     */   
/*     */   private NBufferType tintOutput_bufferType;
/*     */   
/*     */   private NBufferType environmentOutput_bufferType;
/*     */   
/*     */   private NBufferType entityOutput_bufferType;
/*     */   
/*     */   private NStage[] stages;
/*     */   
/*     */   private Bounds3i[] stagesOutputBounds_bufferGrid;
/*     */   
/*     */   private NBufferBundle bufferBundle;
/*     */   private ExecutorService concurrentExecutor;
/*     */   private MaterialCache materialCache;
/*     */   private WorkerIndexer workerIndexer;
/*     */   private TimeInstrument timeInstrument;
/*     */   private Set<Integer> statsCheckpoints;
/*     */   private int generatedChunkCount;
/*     */   private long totalCacheBufferRequests;
/*     */   private long missedCacheBufferRequests;
/*     */   
/*     */   public GeneratedChunk generate(@Nonnull ChunkRequest.Arguments arguments) {
/*  80 */     if (arguments.stillNeeded() != null && !arguments.stillNeeded().test(arguments.index())) {
/*  81 */       return null;
/*     */     }
/*  83 */     this.generatedChunkCount++;
/*     */     
/*  85 */     TimeInstrument.Probe total_timeProbe = (new TimeInstrument.Probe("Total")).start();
/*  86 */     TimeInstrument.Probe contentGeneration_timeProbe = total_timeProbe.createProbe("Content Generation").start();
/*  87 */     TimeInstrument.Probe accessInit_timeProbe = contentGeneration_timeProbe.createProbe("Access Initialization").start();
/*     */     
/*  89 */     Bounds3i localChunkBounds_bufferGrid = GridUtils.createChunkBounds_bufferGrid(arguments.x(), arguments.z());
/*  90 */     Map<NBufferType, NBufferBundle.Access> accessMap = createAccesses(localChunkBounds_bufferGrid);
/*  91 */     accessInit_timeProbe.stop();
/*     */     
/*  93 */     for (int stageIndex = 0; stageIndex < this.stages.length; stageIndex++) {
/*  94 */       TimeInstrument.Probe stage_timeProbe = contentGeneration_timeProbe.createProbe(this.stages[stageIndex].getName() + " (Stage " + this.stages[stageIndex].getName() + ")").start();
/*  95 */       TimeInstrument.Probe stagePrep_timeProbe = stage_timeProbe.createProbe("Preparation").start();
/*     */       
/*  97 */       int stageIndexConst = stageIndex;
/*  98 */       NStage stage = this.stages[stageIndex];
/*  99 */       List<NBufferType> outputTypes = stage.getOutputTypes();
/* 100 */       List<NBufferBundle.Grid> outputGrids = new ArrayList<>(outputTypes.size());
/*     */       
/* 102 */       for (NBufferType type : outputTypes) {
/* 103 */         NBufferBundle.Grid grid = this.bufferBundle.getGrid(type);
/* 104 */         outputGrids.add(grid);
/*     */       } 
/*     */       
/* 107 */       Bounds3i stageChunkOutputBounds_bufferGrid = this.stagesOutputBounds_bufferGrid[stageIndexConst].clone();
/* 108 */       stageChunkOutputBounds_bufferGrid.stack(CHUNK_BOUNDS_BUFFER_GRID);
/* 109 */       stageChunkOutputBounds_bufferGrid.offset(localChunkBounds_bufferGrid.min);
/*     */       
/* 111 */       List<Vector3i> positions_bufferGrid = new ArrayList<>();
/* 112 */       Vector3i tilePos_bufferGrid = new Vector3i(0, 0, 0);
/*     */       
/* 114 */       for (tilePos_bufferGrid.x = stageChunkOutputBounds_bufferGrid.min.x; tilePos_bufferGrid.x < stageChunkOutputBounds_bufferGrid.max.x; tilePos_bufferGrid.x++) {
/* 115 */         for (tilePos_bufferGrid.z = stageChunkOutputBounds_bufferGrid.min.z; tilePos_bufferGrid.z < stageChunkOutputBounds_bufferGrid.max.z; tilePos_bufferGrid.z++) {
/* 116 */           tilePos_bufferGrid.dropHash();
/* 117 */           this.totalCacheBufferRequests++;
/*     */ 
/*     */           
/* 120 */           boolean isOutputCached = true;
/* 121 */           for (NBufferBundle.Grid grid : outputGrids) {
/*     */             
/* 123 */             NBufferBundle.Access access = accessMap.get(grid.getBufferType());
/* 124 */             if (!isColumnCached(access, tilePos_bufferGrid, stageIndex)) {
/* 125 */               isOutputCached = false;
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/* 130 */           if (!isOutputCached) {
/*     */ 
/*     */ 
/*     */             
/* 134 */             this.missedCacheBufferRequests++;
/* 135 */             positions_bufferGrid.add(tilePos_bufferGrid.clone());
/*     */           } 
/*     */         } 
/*     */       } 
/* 139 */       List<List<Vector3i>> splitPositions_bufferGrid = ArrayUtil.split(positions_bufferGrid, this.workerIndexer.getWorkerCount());
/* 140 */       WorkerIndexer.Session workerSession = this.workerIndexer.createSession();
/* 141 */       List<List<Runnable>> allTasks = new ArrayList<>();
/*     */       
/* 143 */       for (int i = 0; i < splitPositions_bufferGrid.size(); i++) {
/*     */         
/* 145 */         List<Vector3i> workerPositions_bufferGrid = splitPositions_bufferGrid.get(i);
/* 146 */         WorkerIndexer.Id workerId = workerSession.next();
/* 147 */         List<Runnable> workerTasks = new ArrayList<>(workerPositions_bufferGrid.size());
/* 148 */         for (int j = 0; j < workerPositions_bufferGrid.size(); j++) {
/*     */           
/* 150 */           Vector3i position_bufferGrid = workerPositions_bufferGrid.get(j);
/* 151 */           Runnable tileTask = createTileTask(stageIndexConst, position_bufferGrid, workerId, accessMap);
/* 152 */           workerTasks.add(tileTask);
/*     */         } 
/*     */         
/* 155 */         allTasks.add(workerTasks);
/*     */       } 
/*     */       
/* 158 */       stagePrep_timeProbe.stop();
/* 159 */       TimeInstrument.Probe stageExecution_timeProbe = stage_timeProbe.createProbe("Execution").start();
/* 160 */       TimeInstrument.Probe taskStart_timeProbe = stageExecution_timeProbe.createProbe("Async Processes Start").start();
/*     */ 
/*     */       
/* 163 */       List<CompletableFuture<Void>> list = new ArrayList<>();
/* 164 */       for (List<Runnable> workerTasks : allTasks) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 170 */         CompletableFuture<Void> future = CompletableFuture.runAsync(() -> { for (Runnable task : workerTasks) task.run();  }this.concurrentExecutor).handle((r, e) -> {
/*     */               if (e == null)
/*     */                 return r; 
/*     */               LoggerUtil.logException("during async execution of stage " + String.valueOf(stage), e);
/*     */               return null;
/*     */             });
/* 176 */         list.add(future);
/*     */       } 
/* 178 */       taskStart_timeProbe.stop();
/*     */       
/* 180 */       FutureUtils.allOf(list).join();
/*     */       
/* 182 */       stageExecution_timeProbe.stop();
/* 183 */       stage_timeProbe.stop();
/*     */     } 
/*     */     
/* 186 */     contentGeneration_timeProbe.stop();
/* 187 */     TimeInstrument.Probe transfer_timeProbe = total_timeProbe.createProbe("Data Transfer").start();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 193 */     GeneratedChunk outputChunk = new GeneratedChunk(new GeneratedBlockChunk(arguments.index(), arguments.x(), arguments.z()), new GeneratedBlockStateChunk(), new GeneratedEntityChunk(), GeneratedChunk.makeSections());
/*     */ 
/*     */ 
/*     */     
/* 197 */     List<CompletableFuture<Void>> futures = new ArrayList<>();
/* 198 */     futures.add(transferMaterials(arguments, outputChunk, transfer_timeProbe));
/* 199 */     futures.add(transferEnvironments(arguments, outputChunk, transfer_timeProbe));
/* 200 */     futures.add(transferTints(arguments, outputChunk, transfer_timeProbe));
/* 201 */     futures.add(transferEntities(arguments, outputChunk, transfer_timeProbe));
/* 202 */     futures.add(transferBlockStates(arguments, outputChunk.getBlockStateChunk(), transfer_timeProbe));
/*     */     
/* 204 */     FutureUtils.allOf(futures).join();
/*     */     
/* 206 */     transfer_timeProbe.stop();
/* 207 */     total_timeProbe.stop();
/* 208 */     this.timeInstrument.takeSample(total_timeProbe);
/*     */ 
/*     */     
/* 211 */     if (this.statsCheckpoints.contains(Integer.valueOf(this.generatedChunkCount))) {
/* 212 */       NBufferBundle.MemoryReport bufferMemoryReport = this.bufferBundle.createMemoryReport();
/* 213 */       StringBuilder stringBuilder = new StringBuilder();
/* 214 */       stringBuilder
/* 215 */         .append(this.timeInstrument.toString())
/* 216 */         .append(bufferMemoryReport.toString())
/* 217 */         .append(createContextDependencyReport(0))
/* 218 */         .append(createBufferRequestCacheReport());
/* 219 */       LoggerUtil.getLogger().info(stringBuilder.toString());
/*     */     } 
/*     */     
/* 222 */     this.bufferBundle.closeALlAccesses();
/* 223 */     return outputChunk;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   private Map<NBufferType, NBufferBundle.Access> createAccesses(@Nonnull Bounds3i localChunkBounds_bufferGrid) {
/* 228 */     Map<NBufferType, NBufferBundle.Access> accessMap = new HashMap<>();
/*     */     
/* 230 */     for (int stageIndex = 0; stageIndex < this.stages.length; stageIndex++) {
/* 231 */       NStage stage = this.stages[stageIndex];
/* 232 */       List<NBufferType> outputTypes = stage.getOutputTypes();
/*     */       
/* 234 */       Bounds3i bounds_bufferGrid = this.stagesOutputBounds_bufferGrid[stageIndex].clone();
/* 235 */       bounds_bufferGrid.stack(CHUNK_BOUNDS_BUFFER_GRID);
/* 236 */       bounds_bufferGrid.offset(localChunkBounds_bufferGrid.min);
/*     */       
/* 238 */       for (NBufferType bufferType : outputTypes) {
/* 239 */         NBufferBundle.Access access = this.bufferBundle.createBufferAccess(bufferType, bounds_bufferGrid);
/* 240 */         accessMap.put(bufferType, access);
/*     */       } 
/*     */     } 
/*     */     
/* 244 */     return accessMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private Runnable createTileTask(int stageIndex, @Nonnull Vector3i position_bufferTileGrid, @Nonnull WorkerIndexer.Id workerId, @Nonnull Map<NBufferType, NBufferBundle.Access> accessMap) {
/* 254 */     NStage stage = this.stages[stageIndex];
/* 255 */     Map<NBufferType, Bounds3i> inputTypesAndBounds_tileGrid = stage.getInputTypesAndBounds_bufferGrid();
/* 256 */     List<NBufferType> outputTypes = stage.getOutputTypes();
/*     */ 
/*     */     
/* 259 */     int bufferAccessCount = inputTypesAndBounds_tileGrid.size() + outputTypes.size();
/* 260 */     NStage.Context context = new NStage.Context(new HashMap<>(bufferAccessCount), workerId);
/*     */     
/* 262 */     for (Map.Entry<NBufferType, Bounds3i> entry : inputTypesAndBounds_tileGrid.entrySet()) {
/*     */       
/* 264 */       NBufferType bufferType = entry.getKey();
/* 265 */       Bounds3i localInputBounds_bufferGrid = ((Bounds3i)entry.getValue()).clone().offset(position_bufferTileGrid);
/* 266 */       NBufferBundle.Access.View bufferAccess = ((NBufferBundle.Access)accessMap.get(bufferType)).createView(localInputBounds_bufferGrid);
/*     */       
/* 268 */       context.bufferAccess.put(bufferType, bufferAccess);
/*     */     } 
/*     */ 
/*     */     
/* 272 */     for (NBufferType bufferType : stage.getOutputTypes()) {
/* 273 */       assert !context.bufferAccess.containsKey(bufferType);
/*     */       
/* 275 */       Bounds3i columnBounds_bufferGrid = GridUtils.createColumnBounds_bufferGrid(position_bufferTileGrid, 0, 40);
/* 276 */       NBufferBundle.Access.View bufferAccess = ((NBufferBundle.Access)accessMap.get(bufferType)).createView(columnBounds_bufferGrid);
/*     */       
/* 278 */       context.bufferAccess.put(bufferType, bufferAccess);
/*     */     } 
/*     */     
/* 281 */     Vector3i bufferPositionClone_bufferTileGrid = position_bufferTileGrid.clone();
/* 282 */     return () -> {
/*     */         stage.run(context);
/*     */         for (NBufferType outputType : stage.getOutputTypes()) {
/*     */           updateTrackersForColumn(stageIndex, ((NBufferBundle.Access)accessMap.get(outputType)).createView(), bufferPositionClone_bufferTileGrid);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private CompletableFuture<Void> transferBlockStates(@Nonnull ChunkRequest.Arguments arguments, @Nonnull GeneratedBlockStateChunk blockStateChunk, @Nonnull TimeInstrument.Probe transfer_timeProbe) {
/* 298 */     Bounds3i chunkBounds_voxelGrid = GridUtils.createChunkBounds_voxelGrid(arguments.x(), arguments.z());
/* 299 */     Bounds3i chunkBounds_bufferGrid = GridUtils.createChunkBounds_bufferGrid(arguments.x(), arguments.z());
/*     */     
/* 301 */     NBufferBundle.Access materialBufferAccess = this.bufferBundle.createBufferAccess(this.materialOutput_bufferType, chunkBounds_bufferGrid);
/* 302 */     NVoxelBufferView nVoxelBufferView = new NVoxelBufferView(materialBufferAccess.createView(), Material.class);
/*     */     
/* 304 */     TimeInstrument.Probe timeProbe = transfer_timeProbe.createProbe("Block States");
/*     */     
/* 306 */     return CompletableFuture.runAsync(() -> { timeProbe.start(); Vector3i position_voxelGrid = new Vector3i(); position_voxelGrid.x = chunkBounds_voxelGrid.min.x; while (position_voxelGrid.x < chunkBounds_voxelGrid.max.x) { position_voxelGrid.z = chunkBounds_voxelGrid.min.z; while (position_voxelGrid.z < chunkBounds_voxelGrid.max.z) { position_voxelGrid.y = chunkBounds_voxelGrid.min.y; while (position_voxelGrid.y < chunkBounds_voxelGrid.max.y) { SolidMaterial solidMaterial = ((Material)materialVoxelSpace.getContent(position_voxelGrid)).solid(); if (solidMaterial != null && solidMaterial.holder != null) blockStateChunk.setState(position_voxelGrid.x, position_voxelGrid.y, position_voxelGrid.z, solidMaterial.holder);  position_voxelGrid.y++; }  position_voxelGrid.z++; }  position_voxelGrid.x++; }  timeProbe.stop(); }this.concurrentExecutor)
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
/* 324 */       .handle((r, e) -> {
/*     */           if (e == null) {
/*     */             return r;
/*     */           }
/*     */           LoggerUtil.logException("a HytaleGenerator async process", e, LoggerUtil.getLogger());
/*     */           return null;
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private CompletableFuture<Void> transferMaterials(@Nonnull ChunkRequest.Arguments arguments, @Nonnull GeneratedChunk generatedChunk, @Nonnull TimeInstrument.Probe transfer_timeProbe) {
/* 339 */     Bounds3i chunkBounds_voxelGrid = GridUtils.createChunkBounds_voxelGrid(arguments.x(), arguments.z());
/* 340 */     Bounds3i chunkBounds_bufferGrid = GridUtils.createChunkBounds_bufferGrid(arguments.x(), arguments.z());
/*     */     
/* 342 */     NBufferBundle.Access materialBufferAccess = this.bufferBundle.createBufferAccess(this.materialOutput_bufferType, chunkBounds_bufferGrid);
/* 343 */     NVoxelBufferView nVoxelBufferView = new NVoxelBufferView(materialBufferAccess.createView(), Material.class);
/*     */     
/* 345 */     GeneratedBlockChunk blockChunk = generatedChunk.getBlockChunk();
/* 346 */     Holder[] arrayOfHolder = generatedChunk.getSections();
/* 347 */     FluidSection[] fluidSections = new FluidSection[arrayOfHolder.length];
/*     */     
/* 349 */     for (int sectionIndex = 0; sectionIndex < 10; sectionIndex++) {
/* 350 */       Holder<ChunkStore> section = arrayOfHolder[sectionIndex];
/* 351 */       FluidSection fluidSection = (FluidSection)section.ensureAndGetComponent(FluidSection.getComponentType());
/* 352 */       fluidSections[sectionIndex] = fluidSection;
/*     */     } 
/*     */     
/* 355 */     CompletableFuture[] arrayOfCompletableFuture = new CompletableFuture[10];
/*     */     
/* 357 */     for (int i = 0; i < 10; i++) {
/*     */       
/* 359 */       int sectionIndexFinal = i;
/*     */       
/* 361 */       TimeInstrument.Probe section_timeProbe = transfer_timeProbe.createProbe("Materials Section " + sectionIndexFinal);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 397 */       CompletableFuture<Void> task = CompletableFuture.runAsync(() -> { section_timeProbe.start(); Holder<ChunkStore> section = sections[sectionIndexFinal]; FluidSection fluidSection = fluidSections[sectionIndexFinal]; for (int x_voxelGrid = 0; x_voxelGrid < 32; x_voxelGrid++) { for (int z_voxelGrid = 0; z_voxelGrid < 32; z_voxelGrid++) { int minY_voxelGrid = sectionIndexFinal * 32; int maxY_voxelGrid = minY_voxelGrid + 32; for (int y_voxelGrid = minY_voxelGrid; y_voxelGrid < maxY_voxelGrid; y_voxelGrid++) { int sectionY = y_voxelGrid - minY_voxelGrid; int worldX_voxelGrid = x_voxelGrid + chunkBounds_voxelGrid.min.x; int worldY_voxelGrid = y_voxelGrid + chunkBounds_voxelGrid.min.y; int worldZ_voxelGrid = z_voxelGrid + chunkBounds_voxelGrid.min.z; Material material = (Material)materialVoxelSpace.getContent(worldX_voxelGrid, worldY_voxelGrid, worldZ_voxelGrid); if (material == null) { blockChunk.setBlock(x_voxelGrid, y_voxelGrid, z_voxelGrid, 0, 0, 0); fluidSection.setFluid(x_voxelGrid, sectionY, z_voxelGrid, this.materialCache.EMPTY_FLUID.fluidId, this.materialCache.EMPTY_FLUID.fluidLevel); } else { blockChunk.setBlock(x_voxelGrid, y_voxelGrid, z_voxelGrid, (material.solid()).blockId, (material.solid()).rotation, (material.solid()).filler); setSupport(generatedChunk, x_voxelGrid, y_voxelGrid, z_voxelGrid, (material.solid()).blockId, (material.solid()).support); fluidSection.setFluid(x_voxelGrid, sectionY, z_voxelGrid, (material.fluid()).fluidId, (material.fluid()).fluidLevel); }  }  }  }  section_timeProbe.stop(); }this.concurrentExecutor).handle((r, e) -> {
/*     */             if (e == null) {
/*     */               return r;
/*     */             }
/*     */             LoggerUtil.logException("a HytaleGenerator async process", e, LoggerUtil.getLogger());
/*     */             return null;
/*     */           });
/* 404 */       arrayOfCompletableFuture[i] = task;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 409 */     Objects.requireNonNull(blockChunk); return CompletableFuture.allOf((CompletableFuture<?>[])arrayOfCompletableFuture).thenRun(blockChunk::generateHeight);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private CompletableFuture<Void> transferTints(@Nonnull ChunkRequest.Arguments arguments, @Nonnull GeneratedChunk generatedChunk, @Nonnull TimeInstrument.Probe transfer_timeProbe) {
/* 418 */     Bounds3i chunkBounds_voxelGrid = GridUtils.createChunkBounds_voxelGrid(arguments.x(), arguments.z());
/* 419 */     Bounds3i chunkBounds_bufferGrid = GridUtils.createChunkBounds_bufferGrid(arguments.x(), arguments.z());
/*     */     
/* 421 */     NBufferBundle.Access tintBufferAccess = this.bufferBundle.createBufferAccess(this.tintOutput_bufferType, chunkBounds_bufferGrid);
/* 422 */     NPixelBufferView nPixelBufferView = new NPixelBufferView(tintBufferAccess.createView(), Integer.class);
/* 423 */     GeneratedBlockChunk blockChunk = generatedChunk.getBlockChunk();
/*     */     
/* 425 */     int worldY_voxelGrid = 0;
/* 426 */     TimeInstrument.Probe tintsTransfer_timeProbe = transfer_timeProbe.createProbe("Tints");
/*     */     
/* 428 */     return 
/* 429 */       CompletableFuture.runAsync(() -> {
/*     */           tintsTransfer_timeProbe.start();
/*     */           
/*     */           for (int x_voxelGrid = 0; x_voxelGrid < 32; x_voxelGrid++) {
/*     */             for (int z_voxelGrid = 0; z_voxelGrid < 32; z_voxelGrid++) {
/*     */               int worldX_voxelGrid = x_voxelGrid + chunkBounds_voxelGrid.min.x;
/*     */               
/*     */               int worldZ_voxelGrid = z_voxelGrid + chunkBounds_voxelGrid.min.z;
/*     */               
/*     */               Integer tint = (Integer)tintVoxelSpace.getContent(worldX_voxelGrid, 0, worldZ_voxelGrid);
/*     */               
/*     */               if (tint == null) {
/*     */                 blockChunk.setTint(x_voxelGrid, z_voxelGrid, 0);
/*     */               } else {
/*     */                 blockChunk.setTint(x_voxelGrid, z_voxelGrid, tint.intValue());
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           tintsTransfer_timeProbe.stop();
/* 448 */         }this.concurrentExecutor).handle((r, e) -> {
/*     */           if (e == null) {
/*     */             return r;
/*     */           }
/*     */           LoggerUtil.logException("a HytaleGenerator async process", e, LoggerUtil.getLogger());
/*     */           return null;
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private CompletableFuture<Void> transferEnvironments(@Nonnull ChunkRequest.Arguments arguments, @Nonnull GeneratedChunk generatedChunk, @Nonnull TimeInstrument.Probe transfer_timeProbe) {
/* 463 */     Bounds3i chunkBounds_voxelGrid = GridUtils.createChunkBounds_voxelGrid(arguments.x(), arguments.z());
/* 464 */     Bounds3i chunkBounds_bufferGrid = GridUtils.createChunkBounds_bufferGrid(arguments.x(), arguments.z());
/*     */     
/* 466 */     NBufferBundle.Access environmentBufferAccess = this.bufferBundle.createBufferAccess(this.environmentOutput_bufferType, chunkBounds_bufferGrid);
/* 467 */     NVoxelBufferView nVoxelBufferView = new NVoxelBufferView(environmentBufferAccess.createView(), Integer.class);
/* 468 */     GeneratedBlockChunk blockChunk = generatedChunk.getBlockChunk();
/* 469 */     TimeInstrument.Probe timeProbe = transfer_timeProbe.createProbe("Environment");
/*     */     
/* 471 */     return CompletableFuture.runAsync(() -> { timeProbe.start(); for (int x_voxelGrid = 0; x_voxelGrid < 32; x_voxelGrid++) { for (int z_voxelGrid = 0; z_voxelGrid < 32; z_voxelGrid++) { int minY_voxelGrid = 0; int maxY_voxelGrid = 320; for (int y_voxelGrid = 0; y_voxelGrid < 320; y_voxelGrid++) { int worldX_voxelGrid = x_voxelGrid + chunkBounds_voxelGrid.min.x; int worldY_voxelGrid = y_voxelGrid + chunkBounds_voxelGrid.min.y; int worldZ_voxelGrid = z_voxelGrid + chunkBounds_voxelGrid.min.z; Integer environment = (Integer)environmentVoxelSpace.getContent(worldX_voxelGrid, worldY_voxelGrid, worldZ_voxelGrid); blockChunk.setEnvironment(x_voxelGrid, y_voxelGrid, z_voxelGrid, ((Integer)Objects.<Integer>requireNonNullElse(environment, Integer.valueOf(0))).intValue()); }  }  }  timeProbe.stop(); }this.concurrentExecutor)
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
/* 492 */       .handle((r, e) -> {
/*     */           if (e == null) {
/*     */             return r;
/*     */           }
/*     */           LoggerUtil.logException("a HytaleGenerator async process", e, LoggerUtil.getLogger());
/*     */           return null;
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private CompletableFuture<Void> transferEntities(@Nonnull ChunkRequest.Arguments arguments, @Nonnull GeneratedChunk generatedChunk, @Nonnull TimeInstrument.Probe transfer_timeProbe) {
/* 507 */     Bounds3i chunkBounds_bufferGrid = GridUtils.createChunkBounds_bufferGrid(arguments.x(), arguments.z());
/*     */     
/* 509 */     NBufferBundle.Access entityBufferAccess = this.bufferBundle.createBufferAccess(this.entityOutput_bufferType, chunkBounds_bufferGrid);
/* 510 */     NEntityBufferView entityView = new NEntityBufferView(entityBufferAccess.createView());
/*     */     
/* 512 */     GeneratedEntityChunk entityChunk = generatedChunk.getEntityChunk();
/* 513 */     TimeInstrument.Probe entitesTransfer_timeProbe = transfer_timeProbe.createProbe("Entities");
/*     */     
/* 515 */     return CompletableFuture.runAsync(() -> { entitesTransfer_timeProbe.start(); entityView.forEach(()); entitesTransfer_timeProbe.stop(); }this.concurrentExecutor)
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 524 */       .handle((r, e) -> {
/*     */           if (e == null) {
/*     */             return r;
/*     */           }
/*     */           LoggerUtil.logException("a HytaleGenerator async process", e, LoggerUtil.getLogger());
/*     */           return null;
/*     */         });
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   private String createBufferRequestCacheReport() {
/* 535 */     StringBuilder builder = new StringBuilder();
/*     */     
/* 537 */     builder.append("Buffer Cache Report\n");
/* 538 */     builder.append("Total Cache Buffer Requests: ").append(this.totalCacheBufferRequests).append("\n");
/* 539 */     builder.append("Missed Cache Buffer Requests: ").append(this.missedCacheBufferRequests).append("\n");
/*     */     
/* 541 */     double ratio = this.missedCacheBufferRequests / this.totalCacheBufferRequests * 100.0D;
/*     */     
/* 543 */     builder.append("Missed/Total Ratio: ").append(ratio).append("%\n");
/*     */     
/* 545 */     return builder.toString();
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   private String createContextDependencyReport(int indentation) {
/* 550 */     StringBuilder builder = new StringBuilder();
/*     */     
/* 552 */     builder.append("Context Dependency Report\n");
/*     */     
/* 554 */     for (int stageIndex = 0; stageIndex < this.stages.length; stageIndex++) {
/* 555 */       Bounds3i bounds_bufferGrid = this.stagesOutputBounds_bufferGrid[stageIndex];
/*     */       
/* 557 */       Vector3i size_bufferGrid = bounds_bufferGrid.getSize().add(3, 0, 3);
/* 558 */       Vector3d size_chunkGrid = new Vector3d(size_bufferGrid);
/* 559 */       size_chunkGrid.scale(0.25D);
/*     */       
/* 561 */       builder.append("\t".repeat(indentation)).append(this.stages[stageIndex].getName()).append(" (Stage ").append(stageIndex).append("):\n");
/* 562 */       builder.append("\t".repeat(indentation + 1)).append("Output Size (Buffer Column): {x=").append(size_bufferGrid.x).append(", z=").append(size_bufferGrid.z).append("}\n");
/* 563 */       builder.append("\t".repeat(indentation + 1)).append("Output Size (Chunk Column): {x=").append(size_chunkGrid.x).append(", z=").append(size_chunkGrid.z).append("}\n");
/*     */     } 
/*     */     
/* 566 */     return builder.toString();
/*     */   }
/*     */   
/*     */   private static void setSupport(@Nonnull GeneratedChunk chunk, int x, int y, int z, int blockId, int supportValue) {
/* 570 */     Holder[] arrayOfHolder = chunk.getSections();
/* 571 */     Holder<ChunkStore> section = arrayOfHolder[ChunkUtil.chunkCoordinate(y)];
/*     */ 
/*     */ 
/*     */     
/* 575 */     if (supportValue >= 0) {
/* 576 */       BlockPhysics.setSupportValue(section, x, y, z, supportValue);
/*     */     } else {
/* 578 */       BlockType blockType = (BlockType)BlockType.getAssetMap().getAsset(blockId);
/* 579 */       if (blockType == null || !blockType.hasSupport()) {
/* 580 */         BlockPhysics.clear(section, x, y, z);
/*     */       } else {
/* 582 */         BlockPhysics.reset(section, x, y, z);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void setBoundsToWorldHeight_bufferGrid(@Nonnull Bounds3i bounds_bufferGrid) {
/* 588 */     bounds_bufferGrid.min.setY(0);
/* 589 */     bounds_bufferGrid.max.setY(40);
/*     */   }
/*     */   
/*     */   private static boolean isColumnCached(@Nonnull NBufferBundle.Access access, @Nonnull Vector3i position_bufferGrid, int stageIndex) {
/* 593 */     assert position_bufferGrid.y == 0;
/*     */     
/* 595 */     NBufferBundle.Tracker tracker = access.getBuffer(position_bufferGrid).tracker();
/* 596 */     return (tracker.stageIndex == stageIndex);
/*     */   }
/*     */   
/*     */   private static void updateTrackersForColumn(int stageIndex, @Nonnull NBufferBundle.Access.View access, @Nonnull Vector3i position_bufferGrid) {
/* 600 */     for (position_bufferGrid.y = 0; position_bufferGrid.y < 40; position_bufferGrid.y++) {
/* 601 */       position_bufferGrid.dropHash();
/*     */       
/* 603 */       NBufferBundle.Tracker tracker = access.getBuffer(position_bufferGrid).tracker();
/* 604 */       tracker.stageIndex = stageIndex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static class Builder {
/* 609 */     public final NParametrizedBufferType MATERIAL_OUTPUT_BUFFER_TYPE = new NParametrizedBufferType("MaterialResult", -1, NVoxelBuffer.class, Material.class, () -> new NVoxelBuffer(Material.class));
/* 610 */     public final NParametrizedBufferType TINT_OUTPUT_BUFFER_TYPE = new NParametrizedBufferType("TintResult", -3, NSimplePixelBuffer.class, Integer.class, () -> new NSimplePixelBuffer(Integer.class));
/* 611 */     public final NParametrizedBufferType ENVIRONMENT_OUTPUT_BUFFER_TYPE = new NParametrizedBufferType("EnvironmentResult", -4, NVoxelBuffer.class, Integer.class, () -> new NVoxelBuffer(Integer.class));
/* 612 */     public final NBufferType ENTITY_OUTPUT_BUFFER_TYPE = new NBufferType("EntityResult", -5, NEntityBuffer.class, NEntityBuffer::new);
/*     */     
/*     */     private List<NStage> stages;
/*     */     
/*     */     private ExecutorService concurrentExecutor;
/*     */     private MaterialCache materialCache;
/*     */     private WorkerIndexer workerIndexer;
/*     */     private String statsHeader;
/*     */     private Set<Integer> statsCheckpoints;
/*     */     private double bufferCapacityFactor;
/*     */     private double targetViewDistance;
/*     */     private double targetPlayerCount;
/*     */     
/*     */     public Builder() {
/* 626 */       this.stages = new ArrayList<>();
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public NStagedChunkGenerator build() {
/* 631 */       assert this.concurrentExecutor != null;
/* 632 */       assert this.materialCache != null;
/* 633 */       assert this.workerIndexer != null;
/* 634 */       assert this.statsHeader != null;
/* 635 */       assert this.statsCheckpoints != null;
/*     */       
/* 637 */       NStagedChunkGenerator instance = new NStagedChunkGenerator();
/*     */       
/* 639 */       instance.materialOutput_bufferType = (NBufferType)this.MATERIAL_OUTPUT_BUFFER_TYPE;
/* 640 */       instance.tintOutput_bufferType = (NBufferType)this.TINT_OUTPUT_BUFFER_TYPE;
/* 641 */       instance.environmentOutput_bufferType = (NBufferType)this.ENVIRONMENT_OUTPUT_BUFFER_TYPE;
/* 642 */       instance.entityOutput_bufferType = this.ENTITY_OUTPUT_BUFFER_TYPE;
/*     */       
/* 644 */       instance.stages = new NStage[this.stages.size()];
/* 645 */       this.stages.toArray(instance.stages);
/*     */       
/* 647 */       Set<NBufferType> allUsedBufferTypes = createListOfAllBufferTypes();
/*     */       
/* 649 */       Map<Integer, Set<Integer>> laterToEalierStageMap = createStageDependencyMap();
/* 650 */       instance.stagesOutputBounds_bufferGrid = createTotalOutputBoundsArray(laterToEalierStageMap);
/*     */       
/* 652 */       instance.bufferBundle = new NBufferBundle();
/* 653 */       instance.bufferBundle.createGrid((NBufferType)this.MATERIAL_OUTPUT_BUFFER_TYPE, resolveBufferCapacity((NBufferType)this.MATERIAL_OUTPUT_BUFFER_TYPE, instance.stagesOutputBounds_bufferGrid));
/* 654 */       instance.bufferBundle.createGrid((NBufferType)this.TINT_OUTPUT_BUFFER_TYPE, resolveBufferCapacity((NBufferType)this.TINT_OUTPUT_BUFFER_TYPE, instance.stagesOutputBounds_bufferGrid));
/* 655 */       instance.bufferBundle.createGrid((NBufferType)this.ENVIRONMENT_OUTPUT_BUFFER_TYPE, resolveBufferCapacity((NBufferType)this.ENVIRONMENT_OUTPUT_BUFFER_TYPE, instance.stagesOutputBounds_bufferGrid));
/* 656 */       instance.bufferBundle.createGrid(this.ENTITY_OUTPUT_BUFFER_TYPE, resolveBufferCapacity(this.ENTITY_OUTPUT_BUFFER_TYPE, instance.stagesOutputBounds_bufferGrid));
/*     */       
/* 658 */       for (NBufferType bufferType : allUsedBufferTypes) {
/* 659 */         if (isGeneratorOutputBufferType(bufferType)) {
/*     */           continue;
/*     */         }
/* 662 */         instance.bufferBundle.createGrid(bufferType, resolveBufferCapacity(bufferType, instance.stagesOutputBounds_bufferGrid));
/*     */       } 
/*     */       
/* 665 */       instance.concurrentExecutor = this.concurrentExecutor;
/* 666 */       instance.materialCache = this.materialCache;
/* 667 */       instance.workerIndexer = this.workerIndexer;
/* 668 */       instance.timeInstrument = new TimeInstrument(this.statsHeader);
/* 669 */       instance.statsCheckpoints = new HashSet<>(this.statsCheckpoints);
/* 670 */       instance.generatedChunkCount = 0;
/*     */       
/* 672 */       return instance;
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public Builder withStats(@Nonnull String statsHeader, @Nonnull Set<Integer> statsCheckpoints) {
/* 677 */       this.statsHeader = statsHeader;
/* 678 */       this.statsCheckpoints = new HashSet<>(statsCheckpoints);
/* 679 */       return this;
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public Builder withConcurrentExecutor(@Nonnull ExecutorService executor, @Nonnull WorkerIndexer workerIndexer) {
/* 684 */       this.concurrentExecutor = executor;
/* 685 */       this.workerIndexer = workerIndexer;
/* 686 */       return this;
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public Builder withMaterialCache(@Nonnull MaterialCache materialCache) {
/* 691 */       this.materialCache = materialCache;
/* 692 */       return this;
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public Builder withBufferCapacity(double factor, double targetViewDistance, double targetPlayerCount) {
/* 697 */       assert factor >= 0.0D;
/* 698 */       assert targetViewDistance >= 0.0D;
/* 699 */       assert targetPlayerCount >= 0.0D;
/*     */       
/* 701 */       this.bufferCapacityFactor = factor;
/* 702 */       this.targetViewDistance = targetViewDistance;
/* 703 */       this.targetPlayerCount = targetPlayerCount;
/* 704 */       return this;
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public Builder appendStage(@Nonnull NStage stage) {
/* 709 */       this.stages.add(stage);
/* 710 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     private List<Integer> createStagesThatReadFrom(int stageIndex) {
/* 716 */       NStage stage = this.stages.get(stageIndex);
/*     */       
/* 718 */       List<Integer> stagesThatReadFromThis = new ArrayList<>();
/* 719 */       List<NBufferType> outputTypes = stage.getOutputTypes();
/* 720 */       for (int i = 0; i < outputTypes.size(); i++) {
/* 721 */         NBufferType outputType = outputTypes.get(i);
/*     */         
/* 723 */         for (int j = 0; j < this.stages.size(); j++) {
/* 724 */           NStage dependentStage = this.stages.get(j);
/*     */           
/* 726 */           if (dependentStage.getInputTypesAndBounds_bufferGrid().containsKey(outputType)) {
/* 727 */             stagesThatReadFromThis.add(Integer.valueOf(j));
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 732 */       return stagesThatReadFromThis;
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     private Map<Integer, Set<Integer>> createStageDependencyMap() {
/* 737 */       Map<Integer, Set<Integer>> dependencyMap = new HashMap<>();
/*     */       
/*     */       int stageIndex;
/* 740 */       for (stageIndex = 0; stageIndex < this.stages.size(); stageIndex++) {
/* 741 */         dependencyMap.put(Integer.valueOf(stageIndex), new HashSet<>(1));
/*     */       }
/*     */       
/* 744 */       for (stageIndex = 0; stageIndex < this.stages.size(); stageIndex++) {
/* 745 */         List<Integer> stagesThatReadFromThis = createStagesThatReadFrom(stageIndex);
/* 746 */         for (Integer dependentStage : stagesThatReadFromThis) {
/* 747 */           ((Set<Integer>)dependencyMap.get(dependentStage)).add(Integer.valueOf(stageIndex));
/*     */         }
/*     */       } 
/*     */       
/* 751 */       return dependencyMap;
/*     */     }
/*     */     
/*     */     private int resolveBufferCapacity(@Nonnull NBufferType bufferType, @Nonnull Bounds3i[] stagesOutputBounds) {
/* 755 */       int stageIndex = 0;
/* 756 */       for (; stageIndex < stagesOutputBounds.length && 
/* 757 */         !((NStage)this.stages.get(stageIndex)).getOutputTypes().contains(bufferType); stageIndex++);
/*     */ 
/*     */ 
/*     */       
/* 761 */       if (stageIndex >= stagesOutputBounds.length) {
/* 762 */         return 0;
/*     */       }
/*     */       
/* 765 */       Bounds3i outputBounds = stagesOutputBounds[stageIndex];
/* 766 */       return calculateCapacityFromBounds(outputBounds, this.bufferCapacityFactor, this.targetViewDistance, this.targetPlayerCount);
/*     */     }
/*     */     private static int calculateCapacityFromBounds(@Nonnull Bounds3i bounds, double factor, double viewDistance_voxelGrid, double playerCount) {
/*     */       double holeArea;
/* 770 */       assert factor >= 0.0D;
/* 771 */       assert viewDistance_voxelGrid >= 0.0D;
/* 772 */       assert playerCount >= 0.0D;
/*     */       
/* 774 */       Vector3i size = bounds.getSize();
/* 775 */       if (size.x == 1 && size.z == 1) {
/* 776 */         return 0;
/*     */       }
/*     */       
/* 779 */       double viewDistance_bufferGrid = viewDistance_voxelGrid / NVoxelBuffer.SIZE.x;
/*     */       
/* 781 */       double entireArea = size.x + viewDistance_bufferGrid * 2.0D;
/* 782 */       entireArea *= size.z + viewDistance_bufferGrid * 2.0D;
/*     */ 
/*     */       
/* 785 */       if (size.x > viewDistance_bufferGrid || size.z > viewDistance_bufferGrid) {
/* 786 */         holeArea = 0.0D;
/*     */       } else {
/* 788 */         holeArea = (viewDistance_bufferGrid - size.x / 2.0D) * (viewDistance_bufferGrid - size.z / 2.0D);
/*     */       } 
/*     */       
/* 791 */       double ringArea = entireArea - holeArea;
/* 792 */       double totalPlayersArea = ringArea * playerCount;
/* 793 */       double factoredArea = totalPlayersArea * factor;
/* 794 */       double totalVolume = factoredArea * 40.0D;
/* 795 */       assert totalVolume >= 0.0D;
/*     */       
/* 797 */       return Math.max(0, (int)totalVolume);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void createTotalOutputBoundsForStage(int stageIndex, @Nonnull Map<Integer, Set<Integer>> stageDependencyMap, @Nonnull Bounds3i[] totalOutputBoundsPerStage_bufferGrid) {
/* 805 */       Bounds3i initialOutputBounds_bufferGrid = new Bounds3i(Vector3i.ZERO, Vector3i.ALL_ONES);
/*     */       
/* 807 */       NStage stage = this.stages.get(stageIndex);
/* 808 */       List<Bounds3i> allOutputBounds = new ArrayList<>();
/* 809 */       for (int dependentStageIndex = this.stages.size() - 1; dependentStageIndex >= stageIndex + 1; dependentStageIndex--) {
/* 810 */         if (((Set)stageDependencyMap.get(Integer.valueOf(dependentStageIndex))).contains(Integer.valueOf(stageIndex))) {
/*     */ 
/*     */ 
/*     */           
/* 814 */           NStage dependentStage = this.stages.get(dependentStageIndex);
/* 815 */           Map<NBufferType, Bounds3i> dependentInputTypesAndBounds_bufferGrid = dependentStage.getInputTypesAndBounds_bufferGrid();
/*     */           
/* 817 */           for (NBufferType thisStageOutputTypes : stage.getOutputTypes()) {
/* 818 */             Bounds3i dependentStageInputBounds_bufferGrid = dependentInputTypesAndBounds_bufferGrid.get(thisStageOutputTypes);
/* 819 */             if (dependentStageInputBounds_bufferGrid == null) {
/*     */               continue;
/*     */             }
/*     */             
/* 823 */             Bounds3i totalDependentStageOutputBounds_bufferGrid = totalOutputBoundsPerStage_bufferGrid[dependentStageIndex];
/*     */ 
/*     */ 
/*     */             
/* 827 */             Bounds3i totalThisStageOutputBounds_bufferGrid = totalDependentStageOutputBounds_bufferGrid.clone().stack(dependentStageInputBounds_bufferGrid);
/* 828 */             allOutputBounds.add(totalThisStageOutputBounds_bufferGrid);
/*     */           } 
/*     */         } 
/*     */       } 
/* 832 */       if (allOutputBounds.isEmpty()) {
/*     */         
/* 834 */         NStagedChunkGenerator.setBoundsToWorldHeight_bufferGrid(initialOutputBounds_bufferGrid);
/* 835 */         totalOutputBoundsPerStage_bufferGrid[stageIndex] = initialOutputBounds_bufferGrid;
/*     */         
/*     */         return;
/*     */       } 
/* 839 */       Bounds3i totalOutputBounds_bufferGrid = ((Bounds3i)allOutputBounds.getFirst()).clone();
/* 840 */       for (int i = 1; i < allOutputBounds.size(); i++) {
/* 841 */         totalOutputBounds_bufferGrid.encompass(allOutputBounds.get(i));
/*     */       }
/*     */       
/* 844 */       NStagedChunkGenerator.setBoundsToWorldHeight_bufferGrid(totalOutputBounds_bufferGrid);
/* 845 */       totalOutputBoundsPerStage_bufferGrid[stageIndex] = totalOutputBounds_bufferGrid;
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     private Bounds3i[] createTotalOutputBoundsArray(@Nonnull Map<Integer, Set<Integer>> stageDependencyMap) {
/* 850 */       Bounds3i[] totalOutputBounds_bufferGrid = new Bounds3i[this.stages.size()];
/*     */       
/* 852 */       for (int stageIndex = this.stages.size() - 1; stageIndex >= 0; stageIndex--) {
/* 853 */         createTotalOutputBoundsForStage(stageIndex, stageDependencyMap, totalOutputBounds_bufferGrid);
/*     */       }
/*     */       
/* 856 */       return totalOutputBounds_bufferGrid;
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     private Set<NBufferType> createListOfAllBufferTypes() {
/* 861 */       Set<NBufferType> allBufferTypes = new HashSet<>();
/*     */       
/* 863 */       for (int stageIndex = 0; stageIndex < this.stages.size(); stageIndex++) {
/* 864 */         NStage stage = this.stages.get(stageIndex);
/* 865 */         allBufferTypes.addAll(stage.getInputTypesAndBounds_bufferGrid().keySet());
/* 866 */         allBufferTypes.addAll(stage.getOutputTypes());
/*     */       } 
/*     */       
/* 869 */       return allBufferTypes;
/*     */     }
/*     */     
/*     */     private static Bounds3i getEncompassingBounds(@Nonnull Collection<Bounds3i> set) {
/* 873 */       Bounds3i out = new Bounds3i();
/* 874 */       for (Bounds3i bounds : set)
/*     */       {
/* 876 */         out.encompass(bounds);
/*     */       }
/* 878 */       return out;
/*     */     }
/*     */     
/*     */     private boolean isGeneratorOutputBufferType(@Nonnull NBufferType bufferType) {
/* 882 */       return (bufferType.equals(this.MATERIAL_OUTPUT_BUFFER_TYPE) || bufferType
/* 883 */         .equals(this.TINT_OUTPUT_BUFFER_TYPE) || bufferType
/* 884 */         .equals(this.ENVIRONMENT_OUTPUT_BUFFER_TYPE) || bufferType
/* 885 */         .equals(this.ENTITY_OUTPUT_BUFFER_TYPE));
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\newsystem\NStagedChunkGenerator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */