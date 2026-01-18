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
/*     */ public class CylinderCaveNodeShape
/*     */   extends AbstractCaveNodeShape implements IWorldBounds {
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
/*     */   public CylinderCaveNodeShape(CaveType caveType, @Nonnull Vector3d o, @Nonnull Vector3d v, double radius1, double radius2, double middleRadius) {
/*  32 */     this.caveType = caveType;
/*  33 */     this.o = o;
/*  34 */     this.v = v;
/*  35 */     this.radius1 = radius1;
/*  36 */     this.radius2 = radius2;
/*  37 */     this.middleRadius = middleRadius;
/*     */     
/*  39 */     this.lowBoundX = MathUtil.floor(Math.min(o.x, o.x + v.x) - Math.max(radius1, radius2));
/*  40 */     this.lowBoundY = MathUtil.floor(Math.min(o.y, o.y + v.y) - Math.max(radius1, radius2));
/*  41 */     this.lowBoundZ = MathUtil.floor(Math.min(o.z, o.z + v.z) - Math.max(radius1, radius2));
/*  42 */     this.highBoundX = MathUtil.ceil(Math.max(o.x, o.x + v.x) + Math.max(radius1, radius2));
/*  43 */     this.highBoundY = MathUtil.ceil(Math.max(o.y, o.y + v.y) + Math.max(radius1, radius2));
/*  44 */     this.highBoundZ = MathUtil.ceil(Math.max(o.z, o.z + v.z) + Math.max(radius1, radius2));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d getStart() {
/*  50 */     return this.o.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d getEnd() {
/*  57 */     double x = this.o.x + this.v.x;
/*  58 */     double y = this.o.y + this.v.y;
/*  59 */     double z = this.o.z + this.v.z;
/*  60 */     return new Vector3d(x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d getAnchor(@Nonnull Vector3d vector, double t, double tv, double th) {
/*  66 */     double radius = getRadiusAt(t);
/*  67 */     return CaveNodeShapeUtils.getPipeAnchor(vector, this.o, this.v, radius, radius, radius, t, tv, th);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public IWorldBounds getBounds() {
/*  73 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLowBoundX() {
/*  78 */     return this.lowBoundX;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLowBoundZ() {
/*  83 */     return this.lowBoundZ;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHighBoundX() {
/*  88 */     return this.highBoundX;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHighBoundZ() {
/*  93 */     return this.highBoundZ;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLowBoundY() {
/*  98 */     return this.lowBoundY;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHighBoundY() {
/* 103 */     return this.highBoundY;
/*     */   }
/*     */   
/*     */   public double getRadius1() {
/* 107 */     return this.radius1;
/*     */   }
/*     */   
/*     */   public double getRadius2() {
/* 111 */     return this.radius2;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldReplace(int seed, double x, double z, int y) {
/* 116 */     double t = projectPointOnNode(x, y, z);
/*     */     
/* 118 */     if (t < 0.0D || t > 1.0D) {
/* 119 */       return false;
/*     */     }
/* 121 */     double dx = this.o.x + this.v.x * t;
/* 122 */     double dy = this.o.y + this.v.y * t;
/* 123 */     double dz = this.o.z + this.v.z * t;
/* 124 */     double r = getRadiusAt(t) * this.caveType.getHeightRadiusFactor(seed, x, z, MathUtil.floor(dy));
/* 125 */     dx -= x;
/* 126 */     dy -= y;
/* 127 */     dz -= z;
/* 128 */     return (dx * dx + dy * dy + dz * dz < r * r);
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
/* 142 */     double t = (px - this.o.x) * this.v.x + (py - this.o.y) * this.v.y + (pz - this.o.z) * this.v.z;
/* 143 */     t /= this.v.x * this.v.x + this.v.y * this.v.y + this.v.z * this.v.z;
/* 144 */     return t;
/*     */   }
/*     */   
/*     */   private double getRadiusAt(double t) {
/* 148 */     if (t < 0.5D) {
/* 149 */       return (this.middleRadius - this.radius1) * 2.0D * t + this.radius1;
/*     */     }
/* 151 */     return (this.radius2 - this.middleRadius) * 2.0D * (t - 0.5D) + this.middleRadius;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getFloorPosition(int seed, double x, double z) {
/* 158 */     for (int y = getLowBoundY(); y < getHighBoundY(); y++) {
/* 159 */       if (shouldReplace(seed, x, z, y)) {
/* 160 */         return (y - 1);
/*     */       }
/*     */     } 
/* 163 */     return -1.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getCeilingPosition(int seed, double x, double z) {
/* 169 */     for (int y = getHighBoundY(); y > getLowBoundY(); y--) {
/* 170 */       if (shouldReplace(seed, x, z, y)) {
/* 171 */         return (y + 1);
/*     */       }
/*     */     } 
/* 174 */     return -1.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 180 */     return "CylinderCaveNodeShape{caveType=" + String.valueOf(this.caveType) + ", o=" + String.valueOf(this.o) + ", v=" + String.valueOf(this.v) + ", lowBoundX=" + this.lowBoundX + ", lowBoundY=" + this.lowBoundY + ", lowBoundZ=" + this.lowBoundZ + ", highBoundX=" + this.highBoundX + ", highBoundY=" + this.highBoundY + ", highBoundZ=" + this.highBoundZ + ", radius1=" + this.radius1 + ", radius2=" + this.radius2 + ", middleRadius=" + this.middleRadius + "}";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class CylinderCaveNodeShapeGenerator
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
/*     */     public CylinderCaveNodeShapeGenerator(IDoubleRange radius, IDoubleRange middleRadius, IDoubleRange length, boolean inheritParentRadius) {
/* 204 */       this.radius = radius;
/* 205 */       this.middleRadius = middleRadius;
/* 206 */       this.length = length;
/* 207 */       this.inheritParentRadius = inheritParentRadius;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public CaveNodeShape generateCaveNodeShape(@Nonnull Random random, CaveType caveType, CaveNode parentNode, @Nonnull CaveNodeType.CaveNodeChildEntry childEntry, @Nonnull Vector3d origin, float yaw, float pitch) {
/* 217 */       double radius1, l = this.length.getValue(random.nextDouble());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 223 */       Vector3d direction = (new Vector3d((TrigMathUtil.sin(pitch) * TrigMathUtil.cos(yaw)), TrigMathUtil.cos(pitch), (TrigMathUtil.sin(pitch) * TrigMathUtil.sin(yaw)))).scale(l);
/*     */       
/* 225 */       if (this.inheritParentRadius) {
/* 226 */         radius1 = CaveNodeShapeUtils.getEndRadius(parentNode, this.radius, random);
/*     */       } else {
/* 228 */         radius1 = this.radius.getValue(random.nextDouble());
/*     */       } 
/* 230 */       double radius2 = this.radius.getValue(random.nextDouble());
/*     */       
/* 232 */       double radius3 = (radius2 - radius1) * 0.5D + radius1;
/* 233 */       if (this.middleRadius != null) {
/* 234 */         radius3 = this.middleRadius.getValue(random.nextDouble());
/*     */       }
/*     */       
/* 237 */       Vector3d offset = CaveNodeShapeUtils.getOffset(parentNode, childEntry);
/* 238 */       origin.add(offset);
/*     */       
/* 240 */       return new CylinderCaveNodeShape(caveType, origin, direction, radius1, radius2, radius3);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\cave\shape\CylinderCaveNodeShape.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */