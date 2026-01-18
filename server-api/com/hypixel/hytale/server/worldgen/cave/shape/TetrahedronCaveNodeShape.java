/*     */ package com.hypixel.hytale.server.worldgen.cave.shape;
/*     */ 
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.server.worldgen.cave.CaveNodeType;
/*     */ import com.hypixel.hytale.server.worldgen.cave.CaveType;
/*     */ import com.hypixel.hytale.server.worldgen.cave.element.CaveNode;
/*     */ import com.hypixel.hytale.server.worldgen.util.bounds.IWorldBounds;
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ @Deprecated
/*     */ public class TetrahedronCaveNodeShape
/*     */   extends AbstractCaveNodeShape implements IWorldBounds {
/*     */   @Nonnull
/*     */   private final Vector3d o;
/*     */   @Nonnull
/*     */   private final Vector3d a;
/*     */   @Nonnull
/*     */   private final Vector3d b;
/*     */   @Nonnull
/*     */   private final Vector3d c;
/*     */   @Nonnull
/*     */   private final Vector3d n1;
/*     */   @Nonnull
/*     */   private final Vector3d n2;
/*     */   @Nonnull
/*     */   private final Vector3d n3;
/*     */   
/*     */   public TetrahedronCaveNodeShape(@Nonnull Vector3d o) {
/*  30 */     this.o = o;
/*  31 */     this.a = new Vector3d(10.0D, 0.0D, 0.0D);
/*  32 */     this.b = new Vector3d(0.0D, 10.0D, 0.0D);
/*  33 */     this.c = new Vector3d(0.0D, 0.0D, 10.0D);
/*     */     
/*  35 */     this.n1 = this.c.cross(this.b);
/*  36 */     this.n2 = this.b.cross(this.a);
/*  37 */     this.n3 = this.a.cross(this.c);
/*     */     
/*  39 */     Vector3d ba = this.a.clone().subtract(this.b);
/*  40 */     Vector3d bc = this.c.clone().subtract(this.b);
/*  41 */     this.n4 = bc.cross(ba);
/*     */     
/*  43 */     this.lowBoundX = (int)(o.x - 10.0D);
/*  44 */     this.lowBoundY = (int)(o.y - 10.0D);
/*  45 */     this.lowBoundZ = (int)(o.z - 10.0D);
/*  46 */     this.highBoundX = (int)(o.x + 10.0D);
/*  47 */     this.highBoundY = (int)(o.y + 10.0D);
/*  48 */     this.highBoundZ = (int)(o.z + 10.0D);
/*     */   }
/*     */   @Nonnull
/*     */   private final Vector3d n4; private final int lowBoundX; private final int lowBoundY; private final int lowBoundZ; private final int highBoundX; private final int highBoundY; private final int highBoundZ;
/*     */   @Nonnull
/*     */   public Vector3d getStart() {
/*  54 */     return this.o.clone();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d getEnd() {
/*  60 */     return this.o.clone().add(this.c);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d getAnchor(@Nonnull Vector3d vector, double tx, double ty, double tz) {
/*  66 */     return CaveNodeShapeUtils.getBoxAnchor(vector, this, tx, ty, tz);
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
/*     */   
/*     */   public boolean shouldReplace(int seed, double x, double z, int y) {
/* 107 */     if (determine(this.o, this.n1, x, y, z) && 
/* 108 */       determine(this.o, this.n2, x, y, z) && 
/* 109 */       determine(this.o, this.n3, x, y, z)) {
/* 110 */       double ox = this.o.x + this.b.x;
/* 111 */       double oy = this.o.y + this.b.y;
/* 112 */       double oz = this.o.z + this.b.z;
/* 113 */       return true;
/*     */     } 
/* 115 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getFloorPosition(int seed, double x, double z) {
/*     */     int y;
/* 122 */     for (y = getLowBoundY(); y < getHighBoundY(); y++) {
/* 123 */       if (shouldReplace(seed, x, z, y)) {
/* 124 */         return y;
/*     */       }
/*     */     } 
/* 127 */     return y;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getCeilingPosition(int seed, double x, double z) {
/*     */     int y;
/* 133 */     for (y = getHighBoundY(); y < getLowBoundY(); y--) {
/* 134 */       if (shouldReplace(seed, x, z, y)) {
/* 135 */         return y;
/*     */       }
/*     */     } 
/* 138 */     return y;
/*     */   }
/*     */   
/*     */   private static boolean determine(@Nonnull Vector3d o, @Nonnull Vector3d n, double px, double py, double pz) {
/* 142 */     return determine(o.x, o.y, o.z, n, px, py, pz);
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean determine(double ox, double oy, double oz, @Nonnull Vector3d n, double px, double py, double pz) {
/* 147 */     double x = (px - ox) * n.x;
/* 148 */     double y = (py - oy) * n.y;
/* 149 */     double z = (pz - oz) * n.z;
/* 150 */     return (x + y + z >= 0.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class TetrahedronCaveNodeShapeGenerator
/*     */     implements CaveNodeShapeEnum.CaveNodeShapeGenerator
/*     */   {
/*     */     @Nonnull
/*     */     public CaveNodeShape generateCaveNodeShape(Random random, CaveType caveType, CaveNode parentNode, CaveNodeType.CaveNodeChildEntry childEntry, @Nonnull Vector3d origin, float yaw, float pitch) {
/* 161 */       return new TetrahedronCaveNodeShape(origin);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\cave\shape\TetrahedronCaveNodeShape.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */