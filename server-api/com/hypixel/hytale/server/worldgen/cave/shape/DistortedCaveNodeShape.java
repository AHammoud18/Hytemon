/*     */ package com.hypixel.hytale.server.worldgen.cave.shape;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.map.BlockTypeAssetMap;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.math.util.TrigMathUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.procedurallib.logic.GeneralNoise;
/*     */ import com.hypixel.hytale.procedurallib.supplier.IDoubleRange;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.universe.world.worldgen.GeneratedBlockChunk;
/*     */ import com.hypixel.hytale.server.worldgen.cave.Cave;
/*     */ import com.hypixel.hytale.server.worldgen.cave.CaveNodeType;
/*     */ import com.hypixel.hytale.server.worldgen.cave.CaveType;
/*     */ import com.hypixel.hytale.server.worldgen.cave.element.CaveNode;
/*     */ import com.hypixel.hytale.server.worldgen.cave.shape.distorted.AbstractDistortedShape;
/*     */ import com.hypixel.hytale.server.worldgen.cave.shape.distorted.DistortedShape;
/*     */ import com.hypixel.hytale.server.worldgen.cave.shape.distorted.ShapeDistortion;
/*     */ import com.hypixel.hytale.server.worldgen.chunk.ChunkGeneratorExecution;
/*     */ import com.hypixel.hytale.server.worldgen.util.BlockFluidEntry;
/*     */ import com.hypixel.hytale.server.worldgen.util.bounds.IWorldBounds;
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DistortedCaveNodeShape
/*     */   implements CaveNodeShape
/*     */ {
/*     */   private final CaveType caveType;
/*     */   private final DistortedShape shape;
/*     */   private final ShapeDistortion distortion;
/*     */   
/*     */   public DistortedCaveNodeShape(CaveType caveType, DistortedShape shape, ShapeDistortion distortion) {
/*  37 */     this.caveType = caveType;
/*  38 */     this.shape = shape;
/*  39 */     this.distortion = distortion;
/*     */   }
/*     */   
/*     */   public DistortedShape getShape() {
/*  43 */     return this.shape;
/*     */   }
/*     */ 
/*     */   
/*     */   public Vector3d getStart() {
/*  48 */     return this.shape.getStart();
/*     */   }
/*     */ 
/*     */   
/*     */   public Vector3d getEnd() {
/*  53 */     return this.shape.getEnd();
/*     */   }
/*     */ 
/*     */   
/*     */   public Vector3d getAnchor(Vector3d vector, double tx, double ty, double tz) {
/*  58 */     return this.shape.getAnchor(vector, tx, ty, tz);
/*     */   }
/*     */ 
/*     */   
/*     */   public IWorldBounds getBounds() {
/*  63 */     return (IWorldBounds)this.shape;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasGeometry() {
/*  68 */     return this.shape.hasGeometry();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldReplace(int seed, double x, double z, int y) {
/*  73 */     double t = this.shape.getProjection(x, z);
/*  74 */     if (this.shape.isValidProjection(t)) {
/*  75 */       double centerY = this.shape.getYAt(t);
/*  76 */       double shapeHeight = this.shape.getHeightAtProjection(seed, x, z, t, centerY, this.caveType, this.distortion);
/*  77 */       if (shapeHeight > 0.0D) {
/*  78 */         int minY = getBounds().getLowBoundY();
/*  79 */         int floor = getFloor(seed, x, z, centerY, shapeHeight, minY);
/*  80 */         if (y < floor) {
/*  81 */           return false;
/*     */         }
/*     */         
/*  84 */         int maxY = getBounds().getHighBoundY();
/*  85 */         int ceiling = getCeiling(seed, x, z, centerY, shapeHeight, maxY);
/*  86 */         return (y <= ceiling);
/*     */       } 
/*     */     } 
/*  89 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getFloorPosition(int seed, double x, double z) {
/*  94 */     double t = this.shape.getProjection(x, z);
/*  95 */     if (this.shape.isValidProjection(t)) {
/*  96 */       double centerY = this.shape.getYAt(t);
/*  97 */       double shapeHeight = this.shape.getHeightAtProjection(seed, x, z, t, centerY, this.caveType, this.distortion);
/*  98 */       if (shapeHeight > 0.0D) {
/*  99 */         int minY = getBounds().getLowBoundY();
/*     */         
/* 101 */         return (getFloor(seed, x, z, centerY, shapeHeight, minY) - 1);
/*     */       } 
/*     */     } 
/* 104 */     return -1.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getCeilingPosition(int seed, double x, double z) {
/* 109 */     double t = this.shape.getProjection(x, z);
/* 110 */     if (this.shape.isValidProjection(t)) {
/* 111 */       double centerY = this.shape.getYAt(t);
/* 112 */       double shapeHeight = this.shape.getHeightAtProjection(seed, x, z, t, centerY, this.caveType, this.distortion);
/* 113 */       if (shapeHeight > 0.0D) {
/* 114 */         int maxY = getBounds().getHighBoundY();
/*     */         
/* 116 */         return (getCeiling(seed, x, z, centerY, shapeHeight, maxY) + 1);
/*     */       } 
/*     */     } 
/* 119 */     return -1.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public void populateChunk(int seed, @Nonnull ChunkGeneratorExecution execution, @Nonnull Cave cave, @Nonnull CaveNode node, @Nonnull Random random) {
/* 124 */     GeneratedBlockChunk chunk = execution.getChunk();
/* 125 */     BlockTypeAssetMap<String, BlockType> blockTypeMap = BlockType.getAssetMap();
/*     */     
/* 127 */     CaveType caveType = cave.getCaveType();
/* 128 */     CaveNodeType caveNodeType = node.getCaveNodeType();
/* 129 */     IWorldBounds shapeBounds = getBounds();
/* 130 */     boolean surfaceLimited = cave.getCaveType().isSurfaceLimited();
/* 131 */     int environment = node.getCaveNodeType().hasEnvironment() ? node.getCaveNodeType().getEnvironment() : caveType.getEnvironment();
/*     */     
/* 133 */     int chunkLowX = ChunkUtil.minBlock(execution.getX());
/* 134 */     int chunkLowZ = ChunkUtil.minBlock(execution.getZ());
/* 135 */     int chunkHighX = ChunkUtil.maxBlock(execution.getX());
/* 136 */     int chunkHighZ = ChunkUtil.maxBlock(execution.getZ());
/*     */     
/* 138 */     int minX = Math.max(chunkLowX, shapeBounds.getLowBoundX());
/* 139 */     int minY = shapeBounds.getLowBoundY();
/* 140 */     int minZ = Math.max(chunkLowZ, shapeBounds.getLowBoundZ());
/* 141 */     int maxX = Math.min(chunkHighX, shapeBounds.getHighBoundX());
/* 142 */     int maxY = shapeBounds.getHighBoundY();
/* 143 */     int maxZ = Math.min(chunkHighZ, shapeBounds.getHighBoundZ());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 148 */     for (int x = minX; x <= maxX; x++) {
/* 149 */       int cx = x - chunkLowX;
/* 150 */       for (int z = minZ; z <= maxZ; z++) {
/* 151 */         int cz = z - chunkLowZ;
/*     */ 
/*     */         
/* 154 */         int maximumY = maxY;
/* 155 */         boolean heightLimited = false;
/* 156 */         if (surfaceLimited) {
/* 157 */           int chunkHeight = chunk.getHeight(cx, cz);
/* 158 */           if (maximumY >= chunkHeight) {
/* 159 */             maximumY = chunkHeight;
/* 160 */             heightLimited = true;
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 165 */         int lowest = Integer.MAX_VALUE;
/* 166 */         int lowestPossible = Integer.MAX_VALUE;
/*     */ 
/*     */         
/* 169 */         int highest = Integer.MIN_VALUE;
/* 170 */         int highestPossible = Integer.MIN_VALUE;
/*     */ 
/*     */         
/* 173 */         double t = this.shape.getProjection(x, z);
/* 174 */         if (this.shape.isValidProjection(t)) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 179 */           double centerY = this.shape.getYAt(t);
/* 180 */           double shapeHeight = this.shape.getHeightAtProjection(seed, x, z, t, centerY, caveType, this.distortion);
/* 181 */           if (shapeHeight > 0.0D) {
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 186 */             int floorY = getFloor(seed, x, z, centerY, shapeHeight, minY);
/* 187 */             int ceilingY = getCeiling(seed, x, z, centerY, shapeHeight, maximumY);
/*     */             
/* 189 */             if (floorY < lowestPossible) lowestPossible = floorY; 
/* 190 */             if (ceilingY > highestPossible) highestPossible = ceilingY;
/*     */             
/* 192 */             for (int y = floorY; y <= ceilingY; y++) {
/* 193 */               int current = execution.getBlock(cx, y, cz);
/* 194 */               int currentFluid = execution.getFluid(cx, y, cz);
/* 195 */               boolean isCandidateBlock = (!surfaceLimited || current != 0);
/* 196 */               if (isCandidateBlock) {
/* 197 */                 BlockFluidEntry blockEntry = CaveNodeShapeUtils.getFillingBlock(caveType, caveNodeType, y, random);
/* 198 */                 if (caveType.getBlockMask().eval(current, currentFluid, blockEntry)) {
/* 199 */                   if (execution.setBlock(cx, y, cz, (byte)6, blockEntry, environment)) {
/* 200 */                     if (y < lowest) lowest = y; 
/* 201 */                     if (y > highest) highest = y; 
/*     */                   } 
/* 203 */                   if (execution.setFluid(cx, y, cz, (byte)6, blockEntry.fluidId(), environment)) {
/* 204 */                     if (y < lowest) lowest = y; 
/* 205 */                     if (y > highest) highest = y;
/*     */                   
/*     */                   } 
/*     */                 } 
/*     */               } 
/*     */             } 
/* 211 */             CaveNodeType.CaveNodeCoverEntry[] covers = caveNodeType.getCovers();
/* 212 */             for (CaveNodeType.CaveNodeCoverEntry cover : covers) {
/* 213 */               CaveNodeType.CaveNodeCoverEntry.Entry entry = cover.get(random);
/* 214 */               int i = CaveNodeShapeUtils.getCoverHeight(lowest, lowestPossible, highest, highestPossible, heightLimited, cover, entry);
/*     */               
/* 216 */               if (i >= 0 && cover.getDensityCondition().eval(seed + node.getSeedOffset(), x, z) && cover
/* 217 */                 .getHeightCondition().eval(seed, x, z, i, random) && cover
/* 218 */                 .getMapCondition().eval(seed, x, z) && 
/* 219 */                 CaveNodeShapeUtils.isCoverMatchingParent(cx, cz, i, execution, cover)) {
/*     */                 
/* 221 */                 execution.setBlock(cx, i, cz, (byte)5, entry.getEntry(), environment);
/* 222 */                 execution.setFluid(cx, i, cz, (byte)5, entry.getEntry().fluidId(), environment);
/*     */               } 
/*     */             } 
/*     */ 
/*     */             
/* 227 */             if (CaveNodeShapeUtils.invalidateCover(cx, lowest - 1, cz, CaveNodeType.CaveNodeCoverType.CEILING, execution, blockTypeMap)) {
/* 228 */               BlockFluidEntry blockEntry = CaveNodeShapeUtils.getFillingBlock(caveType, caveNodeType, lowest - 1, random);
/* 229 */               execution.overrideBlock(cx, lowest - 1, cz, (byte)6, blockEntry);
/* 230 */               execution.overrideFluid(cx, lowest - 1, cz, (byte)6, blockEntry.fluidId());
/*     */             } 
/*     */ 
/*     */             
/* 234 */             if (CaveNodeShapeUtils.invalidateCover(cx, highest + 1, cz, CaveNodeType.CaveNodeCoverType.FLOOR, execution, blockTypeMap)) {
/* 235 */               BlockFluidEntry blockEntry = CaveNodeShapeUtils.getFillingBlock(caveType, caveNodeType, highest + 1, random);
/* 236 */               execution.overrideBlock(cx, highest + 1, cz, (byte)6, blockEntry);
/* 237 */               execution.overrideFluid(cx, highest + 1, cz, (byte)6, blockEntry.fluidId());
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   } private int getFloor(int seed, double x, double z, double centerY, double height, int minY) {
/* 244 */     height *= this.distortion.getFloorFactor(seed, x, z);
/* 245 */     double floorY = this.shape.getFloor(x, z, centerY, height);
/* 246 */     return Math.max(MathUtil.floor(floorY), minY);
/*     */   }
/*     */   
/*     */   private int getCeiling(int seed, double x, double z, double centerY, double height, int maxY) {
/* 250 */     height *= this.distortion.getCeilingFactor(seed, x, z);
/* 251 */     double ceilingY = this.shape.getCeiling(x, z, centerY, height);
/* 252 */     return Math.min(MathUtil.ceil(ceilingY), maxY);
/*     */   }
/*     */ 
/*     */   
/*     */   public static class DistortedCaveNodeShapeGenerator
/*     */     implements CaveNodeShapeEnum.CaveNodeShapeGenerator
/*     */   {
/*     */     private final DistortedShape.Factory shapeFactory;
/*     */     
/*     */     private final IDoubleRange widthRange;
/*     */     
/*     */     private final IDoubleRange midWidthRange;
/*     */     
/*     */     private final IDoubleRange heightRange;
/*     */     
/*     */     private final IDoubleRange midHeightRange;
/*     */     
/*     */     private final IDoubleRange lengthRange;
/*     */     private final ShapeDistortion distortion;
/*     */     private final boolean inheritParentRadius;
/*     */     private final GeneralNoise.InterpolationFunction interpolation;
/*     */     
/*     */     public DistortedCaveNodeShapeGenerator(DistortedShape.Factory shapeFactory, IDoubleRange widthRange, IDoubleRange heightRange, @Nullable IDoubleRange midWidthRange, @Nullable IDoubleRange midHeightRange, @Nullable IDoubleRange lengthRange, boolean inheritParentRadius, ShapeDistortion distortion, @Nullable GeneralNoise.InterpolationFunction interpolation) {
/* 275 */       this.shapeFactory = shapeFactory;
/* 276 */       this.widthRange = widthRange;
/* 277 */       this.heightRange = heightRange;
/* 278 */       this.midWidthRange = midWidthRange;
/* 279 */       this.midHeightRange = midHeightRange;
/* 280 */       this.lengthRange = lengthRange;
/* 281 */       this.distortion = distortion;
/* 282 */       this.inheritParentRadius = inheritParentRadius;
/* 283 */       this.interpolation = interpolation;
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
/*     */     @Nonnull
/*     */     public CaveNodeShape generateCaveNodeShape(Random random, CaveType caveType, @Nullable CaveNode parentNode, @Nonnull CaveNodeType.CaveNodeChildEntry childEntry, @Nonnull Vector3d position, float yaw, float pitch) {
/* 296 */       double length = getLength(this.lengthRange, random);
/* 297 */       Vector3d origin = getOrigin(position, parentNode, childEntry);
/* 298 */       Vector3d direction = getDirection(yaw, pitch, length);
/*     */       
/* 300 */       double startWidth = getStartWidth(this.inheritParentRadius, parentNode, this.widthRange, random);
/* 301 */       double startHeight = getStartHeight(this.inheritParentRadius, parentNode, this.heightRange, random);
/* 302 */       double endWidth = this.widthRange.getValue(random);
/* 303 */       double endHeight = this.heightRange.getValue(random);
/* 304 */       double midWidth = getMiddleRadius(startWidth, endWidth, this.midWidthRange, random);
/* 305 */       double midHeight = getMiddleRadius(startHeight, endHeight, this.midHeightRange, random);
/*     */       
/* 307 */       DistortedShape shape = this.shapeFactory.create(origin, direction, length, startWidth, startHeight, midWidth, midHeight, endWidth, endHeight, this.interpolation);
/*     */       
/* 309 */       return new DistortedCaveNodeShape(caveType, shape, this.distortion);
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     private static Vector3d getOrigin(@Nonnull Vector3d origin, @Nullable CaveNode parentNode, @Nonnull CaveNodeType.CaveNodeChildEntry childEntry) {
/* 314 */       if (parentNode == null) {
/* 315 */         return origin;
/*     */       }
/*     */       
/* 318 */       Vector3d offset = CaveNodeShapeUtils.getOffset(parentNode, childEntry);
/* 319 */       origin.add(offset);
/*     */       
/* 321 */       return origin.add(offset);
/*     */     }
/*     */     
/*     */     private static double getLength(@Nullable IDoubleRange lengthRange, Random random) {
/* 325 */       if (lengthRange == null) {
/* 326 */         return 0.0D;
/*     */       }
/* 328 */       return lengthRange.getValue(random);
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     private static Vector3d getDirection(double yaw, double pitch, double length) {
/* 333 */       if (length == 0.0D) {
/* 334 */         return Vector3d.ZERO;
/*     */       }
/*     */       
/* 337 */       pitch = AbstractDistortedShape.clampPitch(pitch);
/*     */       
/* 339 */       return (new Vector3d((
/* 340 */           TrigMathUtil.sin(pitch) * TrigMathUtil.cos(yaw)), 
/* 341 */           TrigMathUtil.cos(pitch), (
/* 342 */           TrigMathUtil.sin(pitch) * TrigMathUtil.sin(yaw))))
/* 343 */         .scale(length);
/*     */     }
/*     */     
/*     */     private static double getStartWidth(boolean inheritParentRadius, @Nullable CaveNode parentNode, @Nonnull IDoubleRange fallback, Random random) {
/* 347 */       if (inheritParentRadius) {
/* 348 */         return CaveNodeShapeUtils.getEndWidth(parentNode, fallback, random);
/*     */       }
/* 350 */       return fallback.getValue(random);
/*     */     }
/*     */     
/*     */     private static double getStartHeight(boolean inheritParentRadius, @Nullable CaveNode parentNode, @Nonnull IDoubleRange fallback, Random random) {
/* 354 */       if (inheritParentRadius) {
/* 355 */         return CaveNodeShapeUtils.getEndHeight(parentNode, fallback, random);
/*     */       }
/* 357 */       return fallback.getValue(random);
/*     */     }
/*     */     
/*     */     private static double getMiddleRadius(double start, double end, @Nullable IDoubleRange range, Random random) {
/* 361 */       return (range == null) ? ((start - end) * 0.5D + start) : range.getValue(random);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\cave\shape\DistortedCaveNodeShape.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */