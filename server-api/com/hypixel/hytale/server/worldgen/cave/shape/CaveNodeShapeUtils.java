/*     */ package com.hypixel.hytale.server.worldgen.cave.shape;
/*     */ import com.hypixel.hytale.function.function.BiDoubleToDoubleFunction;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.procedurallib.condition.IBlockFluidCondition;
/*     */ import com.hypixel.hytale.procedurallib.supplier.IDoubleRange;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockFace;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.RequiredBlockFaceSupport;
/*     */ import com.hypixel.hytale.server.worldgen.cave.CaveNodeType;
/*     */ import com.hypixel.hytale.server.worldgen.cave.CaveType;
/*     */ import com.hypixel.hytale.server.worldgen.cave.element.CaveNode;
/*     */ import com.hypixel.hytale.server.worldgen.chunk.ChunkGeneratorExecution;
/*     */ import com.hypixel.hytale.server.worldgen.util.bounds.IWorldBounds;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class CaveNodeShapeUtils {
/*     */   public static final BiDoubleToDoubleFunction LEFT;
/*     */   public static final BiDoubleToDoubleFunction RIGHT;
/*     */   
/*     */   static {
/*  24 */     LEFT = ((l, r) -> l);
/*  25 */     RIGHT = ((l, r) -> r);
/*  26 */   } public static final BiDoubleToDoubleFunction MIN = Math::min;
/*  27 */   public static final BiDoubleToDoubleFunction MAX = Math::max;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static Vector3d getBoxAnchor(@Nonnull Vector3d vector, @Nonnull IWorldBounds bounds, double tx, double ty, double tz) {
/*  41 */     double x = bounds.fractionX(tx);
/*  42 */     double y = bounds.fractionY(ty);
/*  43 */     double z = bounds.fractionZ(tz);
/*  44 */     return vector.assign(x, y, z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static Vector3d getLineAnchor(@Nonnull Vector3d vector, @Nonnull Vector3d o, @Nonnull Vector3d v, double t) {
/*  58 */     double x = o.x + v.x * t;
/*  59 */     double y = o.y + v.y * t;
/*  60 */     double z = o.z + v.z * t;
/*  61 */     return vector.assign(x, y, z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static Vector3d getSphereAnchor(@Nonnull Vector3d vector, @Nonnull Vector3d origin, double rx, double ry, double rz, double tx, double ty, double tz) {
/*  80 */     double fx = tx * 2.0D - 1.0D;
/*  81 */     double fy = ty * 2.0D - 1.0D;
/*  82 */     double fz = tz * 2.0D - 1.0D;
/*  83 */     return getRadialProjection(vector, origin.x, origin.y, origin.z, rx, ry, rz, fx, fy, fz);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static Vector3d getPipeAnchor(@Nonnull Vector3d vector, @Nonnull Vector3d o, @Nonnull Vector3d v, double rx, double ry, double rz, double t, double tv, double th) {
/* 102 */     double x = o.x + v.x * t;
/* 103 */     double y = o.y + v.y * t;
/* 104 */     double z = o.z + v.z * t;
/*     */ 
/*     */     
/* 107 */     double len = v.length();
/* 108 */     double nx = v.x / len;
/* 109 */     double ny = v.y / len;
/* 110 */     double nz = v.z / len;
/*     */ 
/*     */     
/* 113 */     double fv = 2.0D * tv - 1.0D;
/* 114 */     double fh = 2.0D * th - 1.0D;
/*     */ 
/*     */     
/* 117 */     double fx = -ny * fv - nz * fh;
/* 118 */     double fy = nx * fv;
/* 119 */     double fz = nx * fh;
/*     */     
/* 121 */     return getRadialProjection(vector, x, y, z, rx, ry, rz, fx, fy, fz);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static Vector3d getOffset(@Nullable CaveNode parent, @Nonnull CaveNodeType.CaveNodeChildEntry childEntry) {
/* 134 */     Vector3d offset = childEntry.getOffset();
/* 135 */     if (offset == Vector3d.ZERO) return offset;
/*     */     
/* 137 */     if (parent != null && parent.getShape() instanceof PrefabCaveNodeShape) {
/* 138 */       offset = offset.clone();
/* 139 */       ((PrefabCaveNodeShape)parent.getShape()).getPrefabRotation().rotate(offset);
/*     */     } 
/*     */     
/* 142 */     return offset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double getEndRadius(@Nullable CaveNode node, @Nonnull IDoubleRange range, Random random) {
/* 156 */     if (node != null) {
/* 157 */       double radius = getEndRadius(node.getShape(), MIN);
/* 158 */       if (radius != -1.0D) {
/* 159 */         return radius;
/*     */       }
/*     */     } 
/* 162 */     return range.getValue(random);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double getEndWidth(@Nullable CaveNode node, @Nonnull IDoubleRange range, Random random) {
/* 174 */     if (node != null) {
/* 175 */       double radius = getEndRadius(node.getShape(), LEFT);
/* 176 */       if (radius != -1.0D) {
/* 177 */         return radius;
/*     */       }
/*     */     } 
/* 180 */     return range.getValue(random);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double getEndHeight(@Nullable CaveNode node, @Nonnull IDoubleRange range, Random random) {
/* 192 */     if (node != null) {
/* 193 */       double radius = getEndRadius(node.getShape(), RIGHT);
/* 194 */       if (radius != -1.0D) {
/* 195 */         return radius;
/*     */       }
/*     */     } 
/* 198 */     return range.getValue(random);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double getEndRadius(@Nonnull CaveNodeShape shape, @Nonnull BiDoubleToDoubleFunction widthHeightSelector) {
/* 209 */     if (shape instanceof CylinderCaveNodeShape) {
/* 210 */       return ((CylinderCaveNodeShape)shape).getRadius2();
/*     */     }
/* 212 */     if (shape instanceof PipeCaveNodeShape) {
/* 213 */       return ((PipeCaveNodeShape)shape).getRadius2();
/*     */     }
/* 215 */     if (shape instanceof DistortedCaveNodeShape) {
/* 216 */       double width = ((DistortedCaveNodeShape)shape).getShape().getWidthAt(1.0D);
/* 217 */       double height = ((DistortedCaveNodeShape)shape).getShape().getHeightAt(1.0D);
/* 218 */       return widthHeightSelector.apply(width, height);
/*     */     } 
/* 220 */     return -1.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static BlockFluidEntry getFillingBlock(@Nonnull CaveType cave, @Nonnull CaveNodeType node, int y, @Nonnull Random random) {
/* 234 */     if (cave.getFluidLevel().getHeight() >= y) {
/* 235 */       return cave.getFluidLevel().getBlockEntry();
/*     */     }
/* 237 */     return node.getFilling(random);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static int getCoverHeight(int lowest, int lowestPossible, int highest, int highestPossible, boolean heightLimited, @Nonnull CaveNodeType.CaveNodeCoverEntry cover, @Nonnull CaveNodeType.CaveNodeCoverEntry.Entry entry) {
/* 258 */     switch (cover.getType()) {
/*     */       case FLOOR:
/* 260 */         if (lowest == Integer.MAX_VALUE || lowestPossible != lowest) return -1; 
/* 261 */         return lowest - 1 + entry.getOffset();
/*     */ 
/*     */       
/*     */       case CEILING:
/* 265 */         if (heightLimited) return -1; 
/* 266 */         if (highest == Integer.MIN_VALUE || highestPossible != highest) return -1; 
/* 267 */         return highest + 1 - entry.getOffset();
/*     */     } 
/* 269 */     throw new AssertionError("Not all cases covered!");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isCoverMatchingParent(int cx, int cz, int y, @Nonnull ChunkGeneratorExecution execution, @Nonnull CaveNodeType.CaveNodeCoverEntry cover) {
/* 284 */     int parentY = y + (cover.getType()).parentOffset;
/* 285 */     if (parentY < 0 || parentY > 319) return false;
/*     */     
/* 287 */     IBlockFluidCondition parentCondition = cover.getParentCondition();
/* 288 */     if (parentCondition == ConstantBlockFluidCondition.DEFAULT_TRUE) return true; 
/* 289 */     if (parentCondition == ConstantBlockFluidCondition.DEFAULT_FALSE) return false;
/*     */     
/* 291 */     int parent = execution.getBlock(cx, parentY, cz);
/* 292 */     int parentFluid = execution.getFluid(cx, parentY, cz);
/* 293 */     return parentCondition.eval(parent, parentFluid);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean invalidateCover(int x, int y, int z, CaveNodeType.CaveNodeCoverType type, @Nonnull ChunkGeneratorExecution execution, @Nonnull BlockTypeAssetMap<String, BlockType> blockTypeMap) {
/* 312 */     if (y < 0 || y > 319) return false;
/*     */     
/* 314 */     byte priority = execution.getPriorityChunk().get(x, y, z);
/*     */ 
/*     */     
/* 317 */     if (priority == 3) return true;
/*     */ 
/*     */     
/* 320 */     if (priority != 5) return false;
/*     */ 
/*     */     
/* 323 */     int block = execution.getBlock(x, y, z);
/* 324 */     BlockType blockType = (BlockType)blockTypeMap.getAsset(block);
/* 325 */     Map<BlockFace, RequiredBlockFaceSupport[]> supportsMap = blockType.getSupport(execution.getRotationIndex(x, y, z));
/*     */     
/* 327 */     if (supportsMap == null) return false;
/*     */     
/* 329 */     switch (type) { default: throw new MatchException(null, null);case FLOOR: case CEILING: break; }  return 
/*     */ 
/*     */ 
/*     */       
/* 333 */       supportsMap.containsKey(BlockFace.UP);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected static Vector3d getRadialProjection(@Nonnull Vector3d vector, double x, double y, double z, double rx, double ry, double rz, double tx, double ty, double tz) {
/* 354 */     double len2 = tx * tx + ty * ty + tz * tz;
/* 355 */     if (len2 == 0.0D) return vector.assign(x, y, z);
/*     */ 
/*     */     
/* 358 */     double invLen = Math.sqrt(1.0D / len2);
/* 359 */     double dx = Math.abs(tx) * rx * invLen;
/* 360 */     double dy = Math.abs(ty) * ry * invLen;
/* 361 */     double dz = Math.abs(tz) * rz * invLen;
/*     */ 
/*     */     
/* 364 */     x += dx * tx;
/* 365 */     y += dy * ty;
/* 366 */     z += dz * tz;
/*     */     
/* 368 */     return vector.assign(x, y, z);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\cave\shape\CaveNodeShapeUtils.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */