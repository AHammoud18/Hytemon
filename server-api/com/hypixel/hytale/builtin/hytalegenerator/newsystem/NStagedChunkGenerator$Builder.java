/*     */ package com.hypixel.hytale.builtin.hytalegenerator.newsystem;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.bounds.Bounds3i;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.material.Material;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.material.MaterialCache;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle.NBufferBundle;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle.buffers.NBuffer;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle.buffers.NEntityBuffer;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle.buffers.NSimplePixelBuffer;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle.buffers.NVoxelBuffer;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle.buffers.type.NBufferType;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle.buffers.type.NParametrizedBufferType;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.performanceinstruments.TimeInstrument;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.stages.NStage;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.threadindexer.WorkerIndexer;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ExecutorService;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
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
/*     */ public class Builder
/*     */ {
/* 609 */   public final NParametrizedBufferType MATERIAL_OUTPUT_BUFFER_TYPE = new NParametrizedBufferType("MaterialResult", -1, NVoxelBuffer.class, Material.class, () -> new NVoxelBuffer(Material.class));
/* 610 */   public final NParametrizedBufferType TINT_OUTPUT_BUFFER_TYPE = new NParametrizedBufferType("TintResult", -3, NSimplePixelBuffer.class, Integer.class, () -> new NSimplePixelBuffer(Integer.class));
/* 611 */   public final NParametrizedBufferType ENVIRONMENT_OUTPUT_BUFFER_TYPE = new NParametrizedBufferType("EnvironmentResult", -4, NVoxelBuffer.class, Integer.class, () -> new NVoxelBuffer(Integer.class));
/* 612 */   public final NBufferType ENTITY_OUTPUT_BUFFER_TYPE = new NBufferType("EntityResult", -5, NEntityBuffer.class, NEntityBuffer::new);
/*     */   
/*     */   private List<NStage> stages;
/*     */   
/*     */   private ExecutorService concurrentExecutor;
/*     */   private MaterialCache materialCache;
/*     */   private WorkerIndexer workerIndexer;
/*     */   private String statsHeader;
/*     */   private Set<Integer> statsCheckpoints;
/*     */   private double bufferCapacityFactor;
/*     */   private double targetViewDistance;
/*     */   private double targetPlayerCount;
/*     */   
/*     */   public Builder() {
/* 626 */     this.stages = new ArrayList<>();
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public NStagedChunkGenerator build() {
/* 631 */     assert this.concurrentExecutor != null;
/* 632 */     assert this.materialCache != null;
/* 633 */     assert this.workerIndexer != null;
/* 634 */     assert this.statsHeader != null;
/* 635 */     assert this.statsCheckpoints != null;
/*     */     
/* 637 */     NStagedChunkGenerator instance = new NStagedChunkGenerator();
/*     */     
/* 639 */     instance.materialOutput_bufferType = (NBufferType)this.MATERIAL_OUTPUT_BUFFER_TYPE;
/* 640 */     instance.tintOutput_bufferType = (NBufferType)this.TINT_OUTPUT_BUFFER_TYPE;
/* 641 */     instance.environmentOutput_bufferType = (NBufferType)this.ENVIRONMENT_OUTPUT_BUFFER_TYPE;
/* 642 */     instance.entityOutput_bufferType = this.ENTITY_OUTPUT_BUFFER_TYPE;
/*     */     
/* 644 */     instance.stages = new NStage[this.stages.size()];
/* 645 */     this.stages.toArray(instance.stages);
/*     */     
/* 647 */     Set<NBufferType> allUsedBufferTypes = createListOfAllBufferTypes();
/*     */     
/* 649 */     Map<Integer, Set<Integer>> laterToEalierStageMap = createStageDependencyMap();
/* 650 */     instance.stagesOutputBounds_bufferGrid = createTotalOutputBoundsArray(laterToEalierStageMap);
/*     */     
/* 652 */     instance.bufferBundle = new NBufferBundle();
/* 653 */     instance.bufferBundle.createGrid((NBufferType)this.MATERIAL_OUTPUT_BUFFER_TYPE, resolveBufferCapacity((NBufferType)this.MATERIAL_OUTPUT_BUFFER_TYPE, instance.stagesOutputBounds_bufferGrid));
/* 654 */     instance.bufferBundle.createGrid((NBufferType)this.TINT_OUTPUT_BUFFER_TYPE, resolveBufferCapacity((NBufferType)this.TINT_OUTPUT_BUFFER_TYPE, instance.stagesOutputBounds_bufferGrid));
/* 655 */     instance.bufferBundle.createGrid((NBufferType)this.ENVIRONMENT_OUTPUT_BUFFER_TYPE, resolveBufferCapacity((NBufferType)this.ENVIRONMENT_OUTPUT_BUFFER_TYPE, instance.stagesOutputBounds_bufferGrid));
/* 656 */     instance.bufferBundle.createGrid(this.ENTITY_OUTPUT_BUFFER_TYPE, resolveBufferCapacity(this.ENTITY_OUTPUT_BUFFER_TYPE, instance.stagesOutputBounds_bufferGrid));
/*     */     
/* 658 */     for (NBufferType bufferType : allUsedBufferTypes) {
/* 659 */       if (isGeneratorOutputBufferType(bufferType)) {
/*     */         continue;
/*     */       }
/* 662 */       instance.bufferBundle.createGrid(bufferType, resolveBufferCapacity(bufferType, instance.stagesOutputBounds_bufferGrid));
/*     */     } 
/*     */     
/* 665 */     instance.concurrentExecutor = this.concurrentExecutor;
/* 666 */     instance.materialCache = this.materialCache;
/* 667 */     instance.workerIndexer = this.workerIndexer;
/* 668 */     instance.timeInstrument = new TimeInstrument(this.statsHeader);
/* 669 */     instance.statsCheckpoints = new HashSet<>(this.statsCheckpoints);
/* 670 */     instance.generatedChunkCount = 0;
/*     */     
/* 672 */     return instance;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Builder withStats(@Nonnull String statsHeader, @Nonnull Set<Integer> statsCheckpoints) {
/* 677 */     this.statsHeader = statsHeader;
/* 678 */     this.statsCheckpoints = new HashSet<>(statsCheckpoints);
/* 679 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Builder withConcurrentExecutor(@Nonnull ExecutorService executor, @Nonnull WorkerIndexer workerIndexer) {
/* 684 */     this.concurrentExecutor = executor;
/* 685 */     this.workerIndexer = workerIndexer;
/* 686 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Builder withMaterialCache(@Nonnull MaterialCache materialCache) {
/* 691 */     this.materialCache = materialCache;
/* 692 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Builder withBufferCapacity(double factor, double targetViewDistance, double targetPlayerCount) {
/* 697 */     assert factor >= 0.0D;
/* 698 */     assert targetViewDistance >= 0.0D;
/* 699 */     assert targetPlayerCount >= 0.0D;
/*     */     
/* 701 */     this.bufferCapacityFactor = factor;
/* 702 */     this.targetViewDistance = targetViewDistance;
/* 703 */     this.targetPlayerCount = targetPlayerCount;
/* 704 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Builder appendStage(@Nonnull NStage stage) {
/* 709 */     this.stages.add(stage);
/* 710 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private List<Integer> createStagesThatReadFrom(int stageIndex) {
/* 716 */     NStage stage = this.stages.get(stageIndex);
/*     */     
/* 718 */     List<Integer> stagesThatReadFromThis = new ArrayList<>();
/* 719 */     List<NBufferType> outputTypes = stage.getOutputTypes();
/* 720 */     for (int i = 0; i < outputTypes.size(); i++) {
/* 721 */       NBufferType outputType = outputTypes.get(i);
/*     */       
/* 723 */       for (int j = 0; j < this.stages.size(); j++) {
/* 724 */         NStage dependentStage = this.stages.get(j);
/*     */         
/* 726 */         if (dependentStage.getInputTypesAndBounds_bufferGrid().containsKey(outputType)) {
/* 727 */           stagesThatReadFromThis.add(Integer.valueOf(j));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 732 */     return stagesThatReadFromThis;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   private Map<Integer, Set<Integer>> createStageDependencyMap() {
/* 737 */     Map<Integer, Set<Integer>> dependencyMap = new HashMap<>();
/*     */     
/*     */     int stageIndex;
/* 740 */     for (stageIndex = 0; stageIndex < this.stages.size(); stageIndex++) {
/* 741 */       dependencyMap.put(Integer.valueOf(stageIndex), new HashSet<>(1));
/*     */     }
/*     */     
/* 744 */     for (stageIndex = 0; stageIndex < this.stages.size(); stageIndex++) {
/* 745 */       List<Integer> stagesThatReadFromThis = createStagesThatReadFrom(stageIndex);
/* 746 */       for (Integer dependentStage : stagesThatReadFromThis) {
/* 747 */         ((Set<Integer>)dependencyMap.get(dependentStage)).add(Integer.valueOf(stageIndex));
/*     */       }
/*     */     } 
/*     */     
/* 751 */     return dependencyMap;
/*     */   }
/*     */   
/*     */   private int resolveBufferCapacity(@Nonnull NBufferType bufferType, @Nonnull Bounds3i[] stagesOutputBounds) {
/* 755 */     int stageIndex = 0;
/* 756 */     for (; stageIndex < stagesOutputBounds.length && 
/* 757 */       !((NStage)this.stages.get(stageIndex)).getOutputTypes().contains(bufferType); stageIndex++);
/*     */ 
/*     */ 
/*     */     
/* 761 */     if (stageIndex >= stagesOutputBounds.length) {
/* 762 */       return 0;
/*     */     }
/*     */     
/* 765 */     Bounds3i outputBounds = stagesOutputBounds[stageIndex];
/* 766 */     return calculateCapacityFromBounds(outputBounds, this.bufferCapacityFactor, this.targetViewDistance, this.targetPlayerCount);
/*     */   }
/*     */   private static int calculateCapacityFromBounds(@Nonnull Bounds3i bounds, double factor, double viewDistance_voxelGrid, double playerCount) {
/*     */     double holeArea;
/* 770 */     assert factor >= 0.0D;
/* 771 */     assert viewDistance_voxelGrid >= 0.0D;
/* 772 */     assert playerCount >= 0.0D;
/*     */     
/* 774 */     Vector3i size = bounds.getSize();
/* 775 */     if (size.x == 1 && size.z == 1) {
/* 776 */       return 0;
/*     */     }
/*     */     
/* 779 */     double viewDistance_bufferGrid = viewDistance_voxelGrid / NVoxelBuffer.SIZE.x;
/*     */     
/* 781 */     double entireArea = size.x + viewDistance_bufferGrid * 2.0D;
/* 782 */     entireArea *= size.z + viewDistance_bufferGrid * 2.0D;
/*     */ 
/*     */     
/* 785 */     if (size.x > viewDistance_bufferGrid || size.z > viewDistance_bufferGrid) {
/* 786 */       holeArea = 0.0D;
/*     */     } else {
/* 788 */       holeArea = (viewDistance_bufferGrid - size.x / 2.0D) * (viewDistance_bufferGrid - size.z / 2.0D);
/*     */     } 
/*     */     
/* 791 */     double ringArea = entireArea - holeArea;
/* 792 */     double totalPlayersArea = ringArea * playerCount;
/* 793 */     double factoredArea = totalPlayersArea * factor;
/* 794 */     double totalVolume = factoredArea * 40.0D;
/* 795 */     assert totalVolume >= 0.0D;
/*     */     
/* 797 */     return Math.max(0, (int)totalVolume);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void createTotalOutputBoundsForStage(int stageIndex, @Nonnull Map<Integer, Set<Integer>> stageDependencyMap, @Nonnull Bounds3i[] totalOutputBoundsPerStage_bufferGrid) {
/* 805 */     Bounds3i initialOutputBounds_bufferGrid = new Bounds3i(Vector3i.ZERO, Vector3i.ALL_ONES);
/*     */     
/* 807 */     NStage stage = this.stages.get(stageIndex);
/* 808 */     List<Bounds3i> allOutputBounds = new ArrayList<>();
/* 809 */     for (int dependentStageIndex = this.stages.size() - 1; dependentStageIndex >= stageIndex + 1; dependentStageIndex--) {
/* 810 */       if (((Set)stageDependencyMap.get(Integer.valueOf(dependentStageIndex))).contains(Integer.valueOf(stageIndex))) {
/*     */ 
/*     */ 
/*     */         
/* 814 */         NStage dependentStage = this.stages.get(dependentStageIndex);
/* 815 */         Map<NBufferType, Bounds3i> dependentInputTypesAndBounds_bufferGrid = dependentStage.getInputTypesAndBounds_bufferGrid();
/*     */         
/* 817 */         for (NBufferType thisStageOutputTypes : stage.getOutputTypes()) {
/* 818 */           Bounds3i dependentStageInputBounds_bufferGrid = dependentInputTypesAndBounds_bufferGrid.get(thisStageOutputTypes);
/* 819 */           if (dependentStageInputBounds_bufferGrid == null) {
/*     */             continue;
/*     */           }
/*     */           
/* 823 */           Bounds3i totalDependentStageOutputBounds_bufferGrid = totalOutputBoundsPerStage_bufferGrid[dependentStageIndex];
/*     */ 
/*     */ 
/*     */           
/* 827 */           Bounds3i totalThisStageOutputBounds_bufferGrid = totalDependentStageOutputBounds_bufferGrid.clone().stack(dependentStageInputBounds_bufferGrid);
/* 828 */           allOutputBounds.add(totalThisStageOutputBounds_bufferGrid);
/*     */         } 
/*     */       } 
/*     */     } 
/* 832 */     if (allOutputBounds.isEmpty()) {
/*     */       
/* 834 */       NStagedChunkGenerator.setBoundsToWorldHeight_bufferGrid(initialOutputBounds_bufferGrid);
/* 835 */       totalOutputBoundsPerStage_bufferGrid[stageIndex] = initialOutputBounds_bufferGrid;
/*     */       
/*     */       return;
/*     */     } 
/* 839 */     Bounds3i totalOutputBounds_bufferGrid = ((Bounds3i)allOutputBounds.getFirst()).clone();
/* 840 */     for (int i = 1; i < allOutputBounds.size(); i++) {
/* 841 */       totalOutputBounds_bufferGrid.encompass(allOutputBounds.get(i));
/*     */     }
/*     */     
/* 844 */     NStagedChunkGenerator.setBoundsToWorldHeight_bufferGrid(totalOutputBounds_bufferGrid);
/* 845 */     totalOutputBoundsPerStage_bufferGrid[stageIndex] = totalOutputBounds_bufferGrid;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   private Bounds3i[] createTotalOutputBoundsArray(@Nonnull Map<Integer, Set<Integer>> stageDependencyMap) {
/* 850 */     Bounds3i[] totalOutputBounds_bufferGrid = new Bounds3i[this.stages.size()];
/*     */     
/* 852 */     for (int stageIndex = this.stages.size() - 1; stageIndex >= 0; stageIndex--) {
/* 853 */       createTotalOutputBoundsForStage(stageIndex, stageDependencyMap, totalOutputBounds_bufferGrid);
/*     */     }
/*     */     
/* 856 */     return totalOutputBounds_bufferGrid;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   private Set<NBufferType> createListOfAllBufferTypes() {
/* 861 */     Set<NBufferType> allBufferTypes = new HashSet<>();
/*     */     
/* 863 */     for (int stageIndex = 0; stageIndex < this.stages.size(); stageIndex++) {
/* 864 */       NStage stage = this.stages.get(stageIndex);
/* 865 */       allBufferTypes.addAll(stage.getInputTypesAndBounds_bufferGrid().keySet());
/* 866 */       allBufferTypes.addAll(stage.getOutputTypes());
/*     */     } 
/*     */     
/* 869 */     return allBufferTypes;
/*     */   }
/*     */   
/*     */   private static Bounds3i getEncompassingBounds(@Nonnull Collection<Bounds3i> set) {
/* 873 */     Bounds3i out = new Bounds3i();
/* 874 */     for (Bounds3i bounds : set)
/*     */     {
/* 876 */       out.encompass(bounds);
/*     */     }
/* 878 */     return out;
/*     */   }
/*     */   
/*     */   private boolean isGeneratorOutputBufferType(@Nonnull NBufferType bufferType) {
/* 882 */     return (bufferType.equals(this.MATERIAL_OUTPUT_BUFFER_TYPE) || bufferType
/* 883 */       .equals(this.TINT_OUTPUT_BUFFER_TYPE) || bufferType
/* 884 */       .equals(this.ENVIRONMENT_OUTPUT_BUFFER_TYPE) || bufferType
/* 885 */       .equals(this.ENTITY_OUTPUT_BUFFER_TYPE));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\newsystem\NStagedChunkGenerator$Builder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */