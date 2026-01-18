/*     */ package com.hypixel.hytale.builtin.hytalegenerator.props.prefab;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.BlockMask;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.bounds.Bounds3i;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.bounds.SpaceSize;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.conveyor.stagedconveyor.ContextDependency;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.datastructures.WeightedMap;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.datastructures.voxelspace.ArrayVoxelSpace;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.datastructures.voxelspace.VoxelSpace;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.framework.math.Calculator;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.framework.math.SeedGenerator;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.material.FluidMaterial;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.material.Material;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.material.MaterialCache;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.material.SolidMaterial;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.views.EntityContainer;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.patterns.Pattern;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.props.Prop;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.props.ScanResult;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.props.directionality.Directionality;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.props.directionality.RotatedPosition;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.props.directionality.RotatedPositionsScanResult;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.props.directionality.StaticDirectionality;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.props.entity.EntityPlacementData;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.scanners.OriginScanner;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.scanners.Scanner;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.seed.SeedBox;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.threadindexer.WorkerIndexer;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.prefab.PrefabRotation;
/*     */ import com.hypixel.hytale.server.core.prefab.PrefabWeights;
/*     */ import com.hypixel.hytale.server.core.prefab.selection.buffer.PrefabBufferCall;
/*     */ import com.hypixel.hytale.server.core.prefab.selection.buffer.impl.IPrefabBuffer;
/*     */ import com.hypixel.hytale.server.core.prefab.selection.buffer.impl.PrefabBuffer;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PrefabProp
/*     */   extends Prop
/*     */ {
/*     */   private final WeightedMap<List<PrefabBuffer>> prefabPool;
/*     */   private final Scanner scanner;
/*     */   private ContextDependency contextDependency;
/*     */   private final MaterialCache materialCache;
/*     */   private final SeedGenerator seedGenerator;
/*     */   private final BlockMask materialMask;
/*     */   private final Directionality directionality;
/*     */   private final Bounds3i writeBounds_voxelGrid;
/*     */   private final Bounds3i prefabBounds_voxelGrid;
/*     */   private final List<PrefabProp> childProps;
/*     */   private final List<RotatedPosition> childPositions;
/*     */   private final Function<String, List<PrefabBuffer>> childPrefabLoader;
/*     */   private final Scanner moldingScanner;
/*     */   private final Pattern moldingPattern;
/*     */   private final MoldingDirection moldingDirection;
/*     */   private final boolean moldChildren;
/*     */   private final int prefabId;
/*     */   private boolean loadEntities;
/*     */   
/*     */   public PrefabProp(@Nonnull WeightedMap<List<PrefabBuffer>> prefabPool, @Nonnull Scanner scanner, @Nonnull Directionality directionality, @Nonnull MaterialCache materialCache, @Nonnull BlockMask materialMask, @Nonnull PrefabMoldingConfiguration prefabMoldingConfiguration, @Nullable Function<String, List<PrefabBuffer>> childPrefabLoader, @Nonnull SeedBox seedBox, boolean loadEntities) {
/*  76 */     this.prefabId = hashCode();
/*  77 */     this.prefabPool = prefabPool;
/*  78 */     this.scanner = scanner;
/*  79 */     this.directionality = directionality;
/*  80 */     this.materialCache = materialCache;
/*  81 */     this.seedGenerator = new SeedGenerator(((Integer)seedBox.createSupplier().get()).intValue());
/*  82 */     this.materialMask = materialMask;
/*  83 */     this.loadEntities = loadEntities;
/*     */     
/*  85 */     this.childProps = new ArrayList<>();
/*  86 */     this.childPositions = new ArrayList<>();
/*  87 */     this.childPrefabLoader = (childPrefabLoader == null) ? (s -> null) : childPrefabLoader;
/*     */     
/*  89 */     this.moldingScanner = prefabMoldingConfiguration.moldingScanner;
/*  90 */     this.moldingPattern = prefabMoldingConfiguration.moldingPattern;
/*  91 */     this.moldingDirection = prefabMoldingConfiguration.moldingDirection;
/*  92 */     this.moldChildren = prefabMoldingConfiguration.moldChildren;
/*     */ 
/*     */     
/*  95 */     this.contextDependency = new ContextDependency();
/*  96 */     Vector3i readRange = directionality.getReadRangeWith(scanner);
/*  97 */     for (List<PrefabBuffer> prefabList : (Iterable<List<PrefabBuffer>>)prefabPool.allElements()) {
/*  98 */       if (prefabList.isEmpty()) throw new IllegalArgumentException("prefab pool contains empty list");
/*     */       
/* 100 */       for (PrefabBuffer prefab : prefabList) {
/* 101 */         if (prefab == null) throw new IllegalArgumentException("prefab pool contains list with null element");
/*     */         
/* 103 */         PrefabBuffer.PrefabBufferAccessor prefabAccess = prefab.newAccess();
/*     */ 
/*     */         
/* 106 */         PrefabBuffer.ChildPrefab[] childPrefabs = prefabAccess.getChildPrefabs();
/* 107 */         int childId = 0;
/* 108 */         for (PrefabBuffer.ChildPrefab child : childPrefabs) {
/* 109 */           RotatedPosition childPosition = new RotatedPosition(child.getX(), child.getY(), child.getZ(), child.getRotation());
/* 110 */           String childPath = child.getPath().replace('.', '/');
/* 111 */           childPath = childPath.replace("*", "");
/* 112 */           List<PrefabBuffer> childPrefabBuffers = this.childPrefabLoader.apply(childPath);
/*     */           
/* 114 */           WeightedMap<List<PrefabBuffer>> weightedChildPrefabs = new WeightedMap();
/* 115 */           weightedChildPrefabs.add(childPrefabBuffers, 1.0D);
/*     */           
/* 117 */           StaticDirectionality childDirectionality = new StaticDirectionality(child.getRotation(), Pattern.yesPattern());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 126 */           PrefabProp childProp = new PrefabProp(weightedChildPrefabs, (Scanner)OriginScanner.getInstance(), (Directionality)childDirectionality, materialCache, materialMask, this.moldChildren ? prefabMoldingConfiguration : PrefabMoldingConfiguration.none(), childPrefabLoader, seedBox.child(String.valueOf(childId++)), loadEntities);
/*     */           
/* 128 */           this.childProps.add(childProp);
/* 129 */           this.childPositions.add(childPosition);
/*     */         } 
/*     */ 
/*     */         
/* 133 */         Vector3i writeRange = getWriteRange(prefabAccess);
/* 134 */         for (int i = 0; i < this.childPositions.size(); i++) {
/* 135 */           PrefabProp child = this.childProps.get(i);
/* 136 */           Vector3i position = ((RotatedPosition)this.childPositions.get(i)).toVector3i();
/* 137 */           Vector3i childWriteRange = child.getContextDependency().getWriteRange();
/*     */           
/* 139 */           int maxRange = Calculator.max(new int[] { position.x, position.y, position.z });
/* 140 */           maxRange += Calculator.max(new int[] { childWriteRange.x, childWriteRange.y, childWriteRange.z });
/*     */           
/* 142 */           writeRange.x = Math.max(writeRange.x, maxRange);
/* 143 */           writeRange.y = Math.max(writeRange.y, maxRange);
/* 144 */           writeRange.z = Math.max(writeRange.z, maxRange);
/*     */         } 
/*     */         
/* 147 */         ContextDependency contextDependency = new ContextDependency(readRange, writeRange);
/* 148 */         this.contextDependency = ContextDependency.mostOf(this.contextDependency, contextDependency);
/*     */         
/* 150 */         prefabAccess.release();
/*     */       } 
/*     */     } 
/*     */     
/* 154 */     this.writeBounds_voxelGrid = this.contextDependency.getTotalPropBounds_voxelGrid();
/* 155 */     this.prefabBounds_voxelGrid = new Bounds3i();
/* 156 */     this.prefabBounds_voxelGrid.min.assign(this.contextDependency.getWriteRange()).scale(-1);
/* 157 */     this.prefabBounds_voxelGrid.max.assign(this.contextDependency.getWriteRange()).add(Vector3i.ALL_ONES);
/*     */   }
/*     */   
/*     */   private Vector3i getWriteRange(PrefabBuffer.PrefabBufferAccessor prefabAccess) {
/* 161 */     SpaceSize space = new SpaceSize();
/* 162 */     for (PrefabRotation rotation : this.directionality.getPossibleRotations()) {
/* 163 */       Vector3i max = PropPrefabUtil.getMax(prefabAccess, rotation);
/* 164 */       max.add(1, 1, 1);
/* 165 */       Vector3i min = PropPrefabUtil.getMin(prefabAccess, rotation);
/* 166 */       space = SpaceSize.merge(space, new SpaceSize(min, max));
/*     */     } 
/* 168 */     space = SpaceSize.stack(space, this.scanner.readSpaceWith(this.directionality.getGeneralPattern()));
/* 169 */     return space.getRange();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ScanResult scan(@Nonnull Vector3i position, @Nonnull VoxelSpace<Material> materialSpace, @Nonnull WorkerIndexer.Id id) {
/* 176 */     Scanner.Context scannerContext = new Scanner.Context(position, this.directionality.getGeneralPattern(), materialSpace, id);
/* 177 */     List<Vector3i> validPositions = this.scanner.scan(scannerContext);
/*     */     
/* 179 */     Vector3i patternPosition = new Vector3i();
/* 180 */     Pattern.Context patternContext = new Pattern.Context(patternPosition, materialSpace, id);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 187 */     RotatedPositionsScanResult scanResult = new RotatedPositionsScanResult(new ArrayList());
/* 188 */     for (Vector3i validPosition : validPositions) {
/* 189 */       patternPosition.assign(validPosition);
/*     */       
/* 191 */       PrefabRotation rotation = this.directionality.getRotationAt(patternContext);
/* 192 */       if (rotation == null) {
/*     */         continue;
/*     */       }
/* 195 */       scanResult.positions.add(new RotatedPosition(validPosition.x, validPosition.y, validPosition.z, rotation));
/*     */     } 
/*     */     
/* 198 */     return (ScanResult)scanResult;
/*     */   }
/*     */ 
/*     */   
/*     */   public void place(@Nonnull Prop.Context context) {
/* 203 */     if (this.prefabPool.size() == 0)
/*     */       return; 
/* 205 */     List<RotatedPosition> positions = (RotatedPositionsScanResult.cast(context.scanResult)).positions;
/* 206 */     if (positions == null)
/*     */       return; 
/* 208 */     Bounds3i writeSpaceBounds_voxelGrid = context.materialSpace.getBounds();
/*     */     
/* 210 */     for (RotatedPosition position : positions) {
/* 211 */       Bounds3i localPrefabWriteBounds_voxelGrid = this.prefabBounds_voxelGrid.clone().offset(position.toVector3i());
/* 212 */       if (!localPrefabWriteBounds_voxelGrid.intersects(writeSpaceBounds_voxelGrid)) {
/*     */         continue;
/*     */       }
/* 215 */       place(position, context.materialSpace, context.entityBuffer, context.workerId);
/*     */     } 
/*     */   }
/*     */   
/*     */   private PrefabBuffer pickPrefab(Random rand) {
/* 220 */     List<PrefabBuffer> list = (List<PrefabBuffer>)this.prefabPool.pick(rand);
/* 221 */     int randomIndex = rand.nextInt(list.size());
/* 222 */     return list.get(randomIndex);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void place(RotatedPosition position, @Nonnull VoxelSpace<Material> materialSpace, @Nonnull EntityContainer entityBuffer, @Nonnull WorkerIndexer.Id id) {
/*     */     ArrayVoxelSpace arrayVoxelSpace;
/* 229 */     Random random = new Random(this.seedGenerator.seedAt(position.x, position.y, position.z));
/* 230 */     PrefabBufferCall callInstance = new PrefabBufferCall(random, position.rotation);
/* 231 */     PrefabBuffer prefab = pickPrefab(random);
/* 232 */     PrefabBuffer.PrefabBufferAccessor prefabAccess = prefab.newAccess();
/*     */     
/* 234 */     VoxelSpace<Integer> moldingOffsets = null;
/* 235 */     if (this.moldingDirection != MoldingDirection.NONE) {
/* 236 */       int prefabMinX = prefabAccess.getMinX(position.rotation);
/* 237 */       int prefabMinZ = prefabAccess.getMinZ(position.rotation);
/* 238 */       int prefabMaxX = prefabAccess.getMaxX(position.rotation);
/* 239 */       int prefabMaxZ = prefabAccess.getMaxZ(position.rotation);
/* 240 */       int prefabSizeX = prefabMaxX - prefabMinX;
/* 241 */       int prefabSizeZ = prefabMaxZ - prefabMinZ;
/* 242 */       arrayVoxelSpace = new ArrayVoxelSpace(prefabSizeX, 1, prefabSizeZ);
/* 243 */       arrayVoxelSpace.setOrigin(-position.x - prefabMinX, 0, -position.z - prefabMinZ);
/*     */       
/* 245 */       if (this.moldingDirection == MoldingDirection.DOWN || this.moldingDirection == MoldingDirection.UP) {
/* 246 */         Vector3i pointer = new Vector3i(0, position.y, 0);
/* 247 */         Scanner.Context scannerContext = new Scanner.Context(pointer, this.moldingPattern, materialSpace, id);
/*     */         
/* 249 */         for (pointer.x = arrayVoxelSpace.minX(); pointer.x < arrayVoxelSpace.maxX(); pointer.x++) {
/* 250 */           for (pointer.z = arrayVoxelSpace.minZ(); pointer.z < arrayVoxelSpace.maxZ(); pointer.z++) {
/*     */             
/* 252 */             List<Vector3i> scanResult = this.moldingScanner.scan(scannerContext);
/*     */ 
/*     */             
/* 255 */             Integer offset = scanResult.isEmpty() ? null : Integer.valueOf(((Vector3i)scanResult.getFirst()).y - position.y);
/*     */             
/* 257 */             if (offset != null && this.moldingDirection == MoldingDirection.UP) {
/* 258 */               offset = Integer.valueOf(offset.intValue() - 1);
/*     */             }
/* 260 */             arrayVoxelSpace.set(offset, pointer.x, 0, pointer.z);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 269 */       Vector3i prefabPositionVector = position.toVector3i();
/* 270 */       ArrayVoxelSpace arrayVoxelSpace1 = arrayVoxelSpace;
/* 271 */       prefabAccess.forEach(IPrefabBuffer.iterateAllColumns(), (x, y, z, blockId, holder, support, rotation, filler, call, fluidId, fluidLevel) -> { int worldX = position.x + x; int worldY = position.y + y; int worldZ = position.z + z; if (!materialSpace.isInsideSpace(worldX, worldY, worldZ)) return;  SolidMaterial solid = this.materialCache.getSolidMaterial(blockId, support, rotation, filler, (holder != null) ? holder.clone() : null); FluidMaterial fluid = this.materialCache.getFluidMaterial(fluidId, (byte)fluidLevel); Material material = this.materialCache.getMaterial(solid, fluid); int materialHash = material.hashMaterialIds(); if (!this.materialMask.canPlace(materialHash)) return;  if (this.moldingDirection == MoldingDirection.DOWN || this.moldingDirection == MoldingDirection.UP) { Integer offset = null; if (moldingOffsetsFinal.isInsideSpace(worldX, 0, worldZ)) offset = (Integer)moldingOffsetsFinal.getContent(worldX, 0, worldZ);  if (offset == null) return;  worldY += offset.intValue(); }  Material worldMaterial = (Material)materialSpace.getContent(worldX, worldY, worldZ); int worldMaterialHash = worldMaterial.hashMaterialIds(); if (!this.materialMask.canReplace(materialHash, worldMaterialHash)) return;  materialSpace.set(material, worldX, worldY, worldZ); }(cx, cz, entityWrappers, buffer) -> { if (!this.loadEntities) return;  if (entityWrappers == null) return;  for (int i = 0; i < entityWrappers.length; i++) { TransformComponent transformComp = (TransformComponent)entityWrappers[i].getComponent(TransformComponent.getComponentType()); if (transformComp != null) { Vector3d entityPosition = transformComp.getPosition().clone(); buffer.rotation.rotate(entityPosition); Vector3d entityWorldPosition = entityPosition.add(prefabPositionVector); if (entityBuffer.isInsideBuffer((int)entityWorldPosition.x, (int)entityWorldPosition.y, (int)entityWorldPosition.z)) { Holder<EntityStore> entityClone = entityWrappers[i].clone(); transformComp = (TransformComponent)entityClone.getComponent(TransformComponent.getComponentType()); if (transformComp != null) { entityPosition = transformComp.getPosition(); entityPosition.x = entityWorldPosition.x; entityPosition.y = entityWorldPosition.y; entityPosition.z = entityWorldPosition.z; if (!materialSpace.isInsideSpace((int)Math.floor(entityPosition.x), (int)Math.floor(entityPosition.y), (int)Math.floor(entityPosition.z))) return;  EntityPlacementData placementData = new EntityPlacementData(new Vector3i(), PrefabRotation.ROTATION_0, entityClone, this.prefabId); entityBuffer.addEntity(placementData); }  }  }  }  }(x, y, z, path, fitHeightmap, inheritSeed, inheritHeightCondition, weights, rotation, t) -> {  }callInstance);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 358 */     catch (Exception e) {
/* 359 */       String msg = "Couldn't place prefab prop.";
/* 360 */       msg = msg + "\n";
/* 361 */       msg = msg + msg;
/* 362 */       ((HytaleLogger.Api)HytaleLogger.getLogger().atWarning()).log(msg);
/*     */     } finally {
/*     */       
/* 365 */       prefabAccess.release();
/*     */     } 
/*     */ 
/*     */     
/* 369 */     for (int i = 0; i < this.childProps.size(); i++) {
/* 370 */       PrefabProp prop = this.childProps.get(i);
/* 371 */       RotatedPosition childPosition = ((RotatedPosition)this.childPositions.get(i)).getRelativeTo(position);
/* 372 */       Vector3i rotatedChildPositionVec = new Vector3i(childPosition.x, childPosition.y, childPosition.z);
/* 373 */       position.rotation.rotate(rotatedChildPositionVec);
/*     */ 
/*     */       
/* 376 */       if (arrayVoxelSpace != null && arrayVoxelSpace.isInsideSpace(childPosition.x, 0, childPosition.z)) {
/* 377 */         Integer offset = (Integer)arrayVoxelSpace.getContent(childPosition.x, 0, childPosition.z);
/* 378 */         if (offset == null)
/* 379 */           continue;  int y = childPosition.y + offset.intValue();
/* 380 */         childPosition = new RotatedPosition(childPosition.x, y, childPosition.z, childPosition.rotation);
/*     */       } 
/*     */       
/* 383 */       prop.place(childPosition, materialSpace, entityBuffer, id);
/*     */       continue;
/*     */     } 
/*     */   }
/*     */   
/*     */   public ContextDependency getContextDependency() {
/* 389 */     return this.contextDependency.clone();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Bounds3i getWriteBounds() {
/* 395 */     return this.writeBounds_voxelGrid;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\props\prefab\PrefabProp.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */