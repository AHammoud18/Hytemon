/*     */ package com.hypixel.hytale.builtin.hytalegenerator.props;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.BlockMask;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.bounds.Bounds3i;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.bounds.SpaceSize;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.conveyor.stagedconveyor.ContextDependency;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.datastructures.voxelspace.VoxelSpace;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.material.Material;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.material.MaterialCache;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.patterns.Pattern;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.props.directionality.Directionality;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.props.directionality.RotatedPosition;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.props.directionality.RotatedPositionsScanResult;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.scanners.Scanner;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.threadindexer.WorkerIndexer;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.Rotation;
/*     */ import com.hypixel.hytale.server.core.prefab.PrefabRotation;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ColumnProp
/*     */   extends Prop
/*     */ {
/*     */   private final int[] yPositions;
/*     */   private final Material[] blocks0;
/*     */   private final Material[] blocks90;
/*     */   private final Material[] blocks180;
/*     */   private final Material[] blocks270;
/*     */   private final BlockMask blockMask;
/*     */   private final Scanner scanner;
/*     */   private final ContextDependency contextDependency;
/*     */   private final Directionality directionality;
/*     */   private final Bounds3i writeBounds_voxelGrid;
/*     */   
/*     */   public ColumnProp(@Nonnull List<Integer> propYPositions, @Nonnull List<Material> blocks, @Nonnull BlockMask blockMask, @Nonnull Scanner scanner, @Nonnull Directionality directionality, @Nonnull MaterialCache materialCache) {
/*  43 */     if (propYPositions.size() != blocks.size()) {
/*  44 */       throw new IllegalArgumentException("blocks and positions sizes don't match");
/*     */     }
/*  46 */     this.blockMask = blockMask;
/*     */     
/*  48 */     this.yPositions = new int[propYPositions.size()];
/*  49 */     this.blocks0 = new Material[blocks.size()];
/*  50 */     this.blocks90 = new Material[blocks.size()];
/*  51 */     this.blocks180 = new Material[blocks.size()];
/*  52 */     this.blocks270 = new Material[blocks.size()];
/*  53 */     for (int i = 0; i < this.yPositions.length; i++) {
/*  54 */       this.yPositions[i] = ((Integer)propYPositions.get(i)).intValue();
/*  55 */       this.blocks0[i] = blocks.get(i);
/*  56 */       this.blocks90[i] = new Material(materialCache.getSolidMaterialRotatedY(((Material)blocks.get(i)).solid(), Rotation.Ninety), ((Material)blocks.get(i)).fluid());
/*  57 */       this.blocks180[i] = new Material(materialCache.getSolidMaterialRotatedY(((Material)blocks.get(i)).solid(), Rotation.OneEighty), ((Material)blocks.get(i)).fluid());
/*  58 */       this.blocks270[i] = new Material(materialCache.getSolidMaterialRotatedY(((Material)blocks.get(i)).solid(), Rotation.TwoSeventy), ((Material)blocks.get(i)).fluid());
/*     */     } 
/*     */     
/*  61 */     this.scanner = scanner;
/*  62 */     this.directionality = directionality;
/*     */ 
/*     */     
/*  65 */     SpaceSize writeSpace = new SpaceSize(new Vector3i(0, 0, 0), new Vector3i(1, 0, 1));
/*  66 */     writeSpace = SpaceSize.stack(writeSpace, scanner.readSpaceWith(directionality.getGeneralPattern()));
/*  67 */     Vector3i writeRange = writeSpace.getRange();
/*     */     
/*  69 */     Vector3i readRange = directionality.getReadRangeWith(scanner);
/*     */     
/*  71 */     this.contextDependency = new ContextDependency(readRange, writeRange);
/*  72 */     this.writeBounds_voxelGrid = this.contextDependency.getTotalPropBounds_voxelGrid();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ScanResult scan(@Nonnull Vector3i position, @Nonnull VoxelSpace<Material> materialSpace, @Nonnull WorkerIndexer.Id id) {
/*  79 */     Scanner.Context scannerContext = new Scanner.Context(position, this.directionality.getGeneralPattern(), materialSpace, id);
/*  80 */     List<Vector3i> validPositions = this.scanner.scan(scannerContext);
/*     */     
/*  82 */     Vector3i patternPosition = new Vector3i();
/*  83 */     Pattern.Context patternContext = new Pattern.Context(patternPosition, materialSpace, id);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  90 */     RotatedPositionsScanResult scanResult = new RotatedPositionsScanResult(new ArrayList());
/*  91 */     for (Vector3i validPosition : validPositions) {
/*  92 */       patternPosition.assign(validPosition);
/*     */       
/*  94 */       PrefabRotation rotation = this.directionality.getRotationAt(patternContext);
/*  95 */       if (rotation == null) {
/*     */         continue;
/*     */       }
/*  98 */       scanResult.positions.add(new RotatedPosition(validPosition.x, validPosition.y, validPosition.z, rotation));
/*     */     } 
/*     */     
/* 101 */     return (ScanResult)scanResult;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void place(@Nonnull Prop.Context context) {
/* 107 */     List<RotatedPosition> positions = (RotatedPositionsScanResult.cast(context.scanResult)).positions;
/*     */     
/* 109 */     for (RotatedPosition position : positions) {
/* 110 */       place(position, context.materialSpace);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void place(@Nonnull RotatedPosition position, @Nonnull VoxelSpace<Material> materialSpace) {
/* 116 */     PrefabRotation rotation = position.rotation;
/* 117 */     switch (rotation) { default: throw new MatchException(null, null);case ROTATION_0: case ROTATION_90: case ROTATION_180: case ROTATION_270: break; }  Material[] blocks = 
/*     */ 
/*     */ 
/*     */       
/* 121 */       this.blocks270;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 128 */     for (int i = 0; i < this.yPositions.length; i++) {
/* 129 */       int y = this.yPositions[i] + position.y;
/* 130 */       Material propBlock = blocks[i];
/*     */       
/* 132 */       if (materialSpace.isInsideSpace(position.x, y, position.z))
/*     */       {
/* 134 */         if (this.blockMask.canPlace(propBlock)) {
/*     */ 
/*     */           
/* 137 */           Material worldMaterial = (Material)materialSpace.getContent(position.x, y, position.z);
/* 138 */           assert worldMaterial != null;
/* 139 */           int worldMaterialHash = worldMaterial.hashMaterialIds();
/*     */           
/* 141 */           if (this.blockMask.canReplace(propBlock.hashMaterialIds(), worldMaterialHash))
/*     */           {
/*     */             
/* 144 */             materialSpace.set(propBlock, position.x, y, position.z); } 
/*     */         }  } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public ContextDependency getContextDependency() {
/* 150 */     return this.contextDependency.clone();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Bounds3i getWriteBounds() {
/* 156 */     return this.writeBounds_voxelGrid;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\props\ColumnProp.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */