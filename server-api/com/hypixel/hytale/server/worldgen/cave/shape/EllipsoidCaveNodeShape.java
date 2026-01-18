/*     */ package com.hypixel.hytale.server.worldgen.cave.shape;
/*     */ 
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.procedurallib.supplier.IDoubleRange;
/*     */ import com.hypixel.hytale.server.worldgen.cave.CaveNodeType;
/*     */ import com.hypixel.hytale.server.worldgen.cave.CaveType;
/*     */ import com.hypixel.hytale.server.worldgen.cave.element.CaveNode;
/*     */ import com.hypixel.hytale.server.worldgen.util.bounds.IWorldBounds;
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ public class EllipsoidCaveNodeShape
/*     */   extends AbstractCaveNodeShape
/*     */   implements IWorldBounds
/*     */ {
/*     */   private final CaveType caveType;
/*     */   @Nonnull
/*     */   private final Vector3d o;
/*     */   private final double rx;
/*     */   private final double ry;
/*     */   private final double rz;
/*     */   
/*     */   public EllipsoidCaveNodeShape(CaveType caveType, @Nonnull Vector3d o, double rx, double ry, double rz) {
/*  26 */     this.caveType = caveType;
/*  27 */     this.o = o;
/*  28 */     this.rx = rx;
/*  29 */     this.ry = ry;
/*  30 */     this.rz = rz;
/*     */     
/*  32 */     this.lowBoundX = MathUtil.floor(Math.min(o.x - rx, o.x + rx));
/*  33 */     this.lowBoundY = MathUtil.floor(Math.min(o.y - ry, o.y + ry));
/*  34 */     this.lowBoundZ = MathUtil.floor(Math.min(o.z - rz, o.z + rz));
/*  35 */     this.highBoundX = MathUtil.ceil(Math.max(o.x - rx, o.x + rx));
/*  36 */     this.highBoundY = MathUtil.ceil(Math.max(o.y - ry, o.y + ry));
/*  37 */     this.highBoundZ = MathUtil.ceil(Math.max(o.z - rz, o.z + rz));
/*     */   }
/*     */   private final int lowBoundX; private final int lowBoundY; private final int lowBoundZ; private final int highBoundX; private final int highBoundY; private final int highBoundZ;
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d getStart() {
/*  43 */     return this.o.clone();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d getEnd() {
/*  49 */     return new Vector3d(this.o.x, this.lowBoundY, this.o.z);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d getAnchor(@Nonnull Vector3d vector, double tx, double ty, double tz) {
/*  55 */     return CaveNodeShapeUtils.getSphereAnchor(vector, this.o, this.rx, this.ry, this.rz, tx, ty, tz);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public IWorldBounds getBounds() {
/*  61 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLowBoundX() {
/*  66 */     return this.lowBoundX;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLowBoundZ() {
/*  71 */     return this.lowBoundZ;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHighBoundX() {
/*  76 */     return this.highBoundX;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHighBoundZ() {
/*  81 */     return this.highBoundZ;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLowBoundY() {
/*  86 */     return this.lowBoundY;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHighBoundY() {
/*  91 */     return this.highBoundY;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldReplace(int seed, double x, double z, int y) {
/*  96 */     double fx = x, fy = y, fz = z;
/*     */     
/*  98 */     fx -= this.o.x;
/*  99 */     fy -= this.o.y;
/* 100 */     fz -= this.o.z;
/*     */     
/* 102 */     fx /= this.rx;
/* 103 */     fy /= this.ry;
/* 104 */     fz /= this.rz;
/*     */     
/* 106 */     double t = this.caveType.getHeightRadiusFactor(seed, x, z, y);
/* 107 */     return (fx * fx + fy * fy + fz * fz <= t * t);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getFloorPosition(int seed, double x, double z) {
/* 113 */     for (int y = getLowBoundY(); y < this.o.y; y++) {
/* 114 */       if (shouldReplace(seed, x, z, y)) {
/* 115 */         return (y - 1);
/*     */       }
/*     */     } 
/* 118 */     return -1.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getCeilingPosition(int seed, double x, double z) {
/* 124 */     for (int y = getHighBoundY(); y > this.o.y; y--) {
/* 125 */       if (shouldReplace(seed, x, z, y)) {
/* 126 */         return (y + 1);
/*     */       }
/*     */     } 
/* 129 */     return -1.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 135 */     return "EllipsoidCaveNodeShape{caveType=" + String.valueOf(this.caveType) + ", o=" + String.valueOf(this.o) + ", rx=" + this.rx + ", ry=" + this.ry + ", rz=" + this.rz + ", lowBoundX=" + this.lowBoundX + ", lowBoundY=" + this.lowBoundY + ", lowBoundZ=" + this.lowBoundZ + ", highBoundX=" + this.highBoundX + ", highBoundY=" + this.highBoundY + ", highBoundZ=" + this.highBoundZ + "}";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EllipsoidCaveNodeShapeGenerator
/*     */     implements CaveNodeShapeEnum.CaveNodeShapeGenerator
/*     */   {
/*     */     private final IDoubleRange radiusX;
/*     */ 
/*     */     
/*     */     private final IDoubleRange radiusY;
/*     */ 
/*     */     
/*     */     private final IDoubleRange radiusZ;
/*     */ 
/*     */ 
/*     */     
/*     */     public EllipsoidCaveNodeShapeGenerator(IDoubleRange radiusX, IDoubleRange radiusY, IDoubleRange radiusZ) {
/* 155 */       this.radiusX = radiusX;
/* 156 */       this.radiusY = radiusY;
/* 157 */       this.radiusZ = radiusZ;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public CaveNodeShape generateCaveNodeShape(Random random, CaveType caveType, CaveNode parentNode, @Nonnull CaveNodeType.CaveNodeChildEntry childEntry, @Nonnull Vector3d origin, float yaw, float pitch) {
/* 167 */       double rx = this.radiusX.getValue(random);
/* 168 */       double ry = this.radiusY.getValue(random);
/* 169 */       double rz = this.radiusZ.getValue(random);
/* 170 */       Vector3d offset = CaveNodeShapeUtils.getOffset(parentNode, childEntry);
/* 171 */       origin.add(offset);
/*     */       
/* 173 */       return new EllipsoidCaveNodeShape(caveType, origin, rx, ry, rz);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\cave\shape\EllipsoidCaveNodeShape.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */