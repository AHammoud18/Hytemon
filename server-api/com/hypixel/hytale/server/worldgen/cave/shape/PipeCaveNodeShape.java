/*     */ package com.hypixel.hytale.server.worldgen.cave.shape;
/*     */ 
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.math.util.TrigMathUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.procedurallib.supplier.IDoubleRange;
/*     */ import com.hypixel.hytale.server.worldgen.cave.CaveNodeType;
/*     */ import com.hypixel.hytale.server.worldgen.cave.CaveType;
/*     */ import com.hypixel.hytale.server.worldgen.cave.element.CaveNode;
/*     */ import com.hypixel.hytale.server.worldgen.util.bounds.IWorldBounds;
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class PipeCaveNodeShape extends AbstractCaveNodeShape implements IWorldBounds {
/*     */   private final CaveType caveType;
/*     */   @Nonnull
/*     */   private final Vector3d o;
/*     */   @Nonnull
/*     */   private final Vector3d v;
/*     */   private final int lowBoundX;
/*     */   private final int lowBoundY;
/*     */   private final int lowBoundZ;
/*     */   private final int highBoundX;
/*     */   private final int highBoundY;
/*     */   private final int highBoundZ;
/*     */   private final double radius1;
/*     */   private final double radius2;
/*     */   private final double middleRadius;
/*     */   
/*     */   public PipeCaveNodeShape(CaveType caveType, @Nonnull Vector3d o, @Nonnull Vector3d v, double radius1, double radius2, double middleRadius) {
/*  31 */     this.caveType = caveType;
/*  32 */     this.o = o;
/*  33 */     this.v = v;
/*  34 */     this.radius1 = radius1;
/*  35 */     this.radius2 = radius2;
/*  36 */     this.middleRadius = middleRadius;
/*     */     
/*  38 */     this.lowBoundX = MathUtil.floor(Math.min(o.x, o.x + v.x) - Math.max(radius1, radius2));
/*  39 */     this.lowBoundY = MathUtil.floor(Math.min(o.y, o.y + v.y) - Math.max(radius1, radius2));
/*  40 */     this.lowBoundZ = MathUtil.floor(Math.min(o.z, o.z + v.z) - Math.max(radius1, radius2));
/*  41 */     this.highBoundX = MathUtil.ceil(Math.max(o.x, o.x + v.x) + Math.max(radius1, radius2));
/*  42 */     this.highBoundY = MathUtil.ceil(Math.max(o.y, o.y + v.y) + Math.max(radius1, radius2));
/*  43 */     this.highBoundZ = MathUtil.ceil(Math.max(o.z, o.z + v.z) + Math.max(radius1, radius2));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d getStart() {
/*  49 */     return this.o.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d getEnd() {
/*  56 */     double x = this.o.x + this.v.x;
/*  57 */     double y = this.o.y + this.v.y;
/*  58 */     double z = this.o.z + this.v.z;
/*  59 */     return new Vector3d(x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d getAnchor(@Nonnull Vector3d vector, double t, double tv, double th) {
/*  65 */     double radius = getRadiusAt(t);
/*  66 */     return CaveNodeShapeUtils.getPipeAnchor(vector, this.o, this.v, radius, radius, radius, t, tv, th);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public IWorldBounds getBounds() {
/*  72 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLowBoundX() {
/*  77 */     return this.lowBoundX;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLowBoundZ() {
/*  82 */     return this.lowBoundZ;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHighBoundX() {
/*  87 */     return this.highBoundX;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHighBoundZ() {
/*  92 */     return this.highBoundZ;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLowBoundY() {
/*  97 */     return this.lowBoundY;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHighBoundY() {
/* 102 */     return this.highBoundY;
/*     */   }
/*     */   
/*     */   public double getRadius1() {
/* 106 */     return this.radius1;
/*     */   }
/*     */   
/*     */   public double getRadius2() {
/* 110 */     return this.radius2;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldReplace(int seed, double x, double z, int y) {
/* 115 */     double dx, dy, dz, r, t = projectPointOnNode(x, y, z);
/*     */     
/* 117 */     if (t < 0.0D) {
/* 118 */       dx = this.o.x;
/* 119 */       dy = this.o.y;
/* 120 */       dz = this.o.z;
/* 121 */       r = this.radius1;
/* 122 */     } else if (t > 1.0D) {
/* 123 */       dx = this.o.x + this.v.x;
/* 124 */       dy = this.o.y + this.v.y;
/* 125 */       dz = this.o.z + this.v.z;
/* 126 */       r = this.radius2;
/*     */     } else {
/* 128 */       dx = this.o.x + this.v.x * t;
/* 129 */       dy = this.o.y + this.v.y * t;
/* 130 */       dz = this.o.z + this.v.z * t;
/* 131 */       r = getRadiusAt(t);
/*     */     } 
/* 133 */     r *= this.caveType.getHeightRadiusFactor(seed, x, z, MathUtil.floor(dy));
/* 134 */     dx -= x;
/* 135 */     dy -= y;
/* 136 */     dz -= z;
/* 137 */     return (dx * dx + dy * dy + dz * dz < r * r);
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
/*     */   private double projectPointOnNode(double px, double py, double pz) {
/* 151 */     double t = (px - this.o.x) * this.v.x + (py - this.o.y) * this.v.y + (pz - this.o.z) * this.v.z;
/* 152 */     t /= this.v.x * this.v.x + this.v.y * this.v.y + this.v.z * this.v.z;
/* 153 */     return t;
/*     */   }
/*     */   
/*     */   private double getRadiusAt(double t) {
/* 157 */     if (t < 0.5D) {
/* 158 */       return (this.middleRadius - this.radius1) * 2.0D * t + this.radius1;
/*     */     }
/* 160 */     return (this.radius2 - this.middleRadius) * 2.0D * (t - 0.5D) + this.middleRadius;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getFloorPosition(int seed, double x, double z) {
/* 167 */     for (int y = getLowBoundY(); y < getHighBoundY(); y++) {
/* 168 */       if (shouldReplace(seed, x, z, y)) {
/* 169 */         return (y - 1);
/*     */       }
/*     */     } 
/* 172 */     return -1.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getCeilingPosition(int seed, double x, double z) {
/* 178 */     for (int y = getHighBoundY(); y > getLowBoundY(); y--) {
/* 179 */       if (shouldReplace(seed, x, z, y)) {
/* 180 */         return (y + 1);
/*     */       }
/*     */     } 
/* 183 */     return -1.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 189 */     return "PipeCaveNodeShape{caveType=" + String.valueOf(this.caveType) + ", o=" + String.valueOf(this.o) + ", v=" + String.valueOf(this.v) + ", lowBoundX=" + this.lowBoundX + ", lowBoundY=" + this.lowBoundY + ", lowBoundZ=" + this.lowBoundZ + ", highBoundX=" + this.highBoundX + ", highBoundY=" + this.highBoundY + ", highBoundZ=" + this.highBoundZ + ", radius1=" + this.radius1 + ", radius2=" + this.radius2 + ", middleRadius=" + this.middleRadius + "}";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class PipeCaveNodeShapeGenerator
/*     */     implements CaveNodeShapeEnum.CaveNodeShapeGenerator
/*     */   {
/*     */     private final IDoubleRange radius;
/*     */ 
/*     */ 
/*     */     
/*     */     private final IDoubleRange middleRadius;
/*     */ 
/*     */     
/*     */     private final IDoubleRange length;
/*     */ 
/*     */     
/*     */     private final boolean inheritParentRadius;
/*     */ 
/*     */ 
/*     */     
/*     */     public PipeCaveNodeShapeGenerator(IDoubleRange radius, IDoubleRange middleRadius, IDoubleRange length, boolean inheritParentRadius) {
/* 213 */       this.radius = radius;
/* 214 */       this.middleRadius = middleRadius;
/* 215 */       this.length = length;
/* 216 */       this.inheritParentRadius = inheritParentRadius;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public CaveNodeShape generateCaveNodeShape(@Nonnull Random random, CaveType caveType, CaveNode parentNode, @Nonnull CaveNodeType.CaveNodeChildEntry childEntry, @Nonnull Vector3d origin, float yaw, float pitch) {
/* 226 */       double radius1, l = this.length.getValue(random.nextDouble());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 232 */       Vector3d direction = (new Vector3d((TrigMathUtil.sin(pitch) * TrigMathUtil.cos(yaw)), TrigMathUtil.cos(pitch), (TrigMathUtil.sin(pitch) * TrigMathUtil.sin(yaw)))).scale(l);
/*     */       
/* 234 */       if (this.inheritParentRadius) {
/* 235 */         radius1 = CaveNodeShapeUtils.getEndRadius(parentNode, this.radius, random);
/*     */       } else {
/* 237 */         radius1 = this.radius.getValue(random.nextDouble());
/*     */       } 
/* 239 */       double radius2 = this.radius.getValue(random.nextDouble());
/*     */       
/* 241 */       double radius3 = (radius2 - radius1) * 0.5D + radius1;
/* 242 */       if (this.middleRadius != null) {
/* 243 */         radius3 = this.middleRadius.getValue(random.nextDouble());
/*     */       }
/*     */       
/* 246 */       Vector3d offset = CaveNodeShapeUtils.getOffset(parentNode, childEntry);
/* 247 */       origin.add(offset);
/*     */       
/* 249 */       return new PipeCaveNodeShape(caveType, origin, direction, radius1, radius2, radius3);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\cave\shape\PipeCaveNodeShape.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */