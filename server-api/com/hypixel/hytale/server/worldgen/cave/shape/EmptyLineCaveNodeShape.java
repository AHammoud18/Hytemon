/*     */ package com.hypixel.hytale.server.worldgen.cave.shape;
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EmptyLineCaveNodeShape
/*     */   extends AbstractCaveNodeShape
/*     */   implements IWorldBounds
/*     */ {
/*     */   private final Vector3d o;
/*     */   private final Vector3d v;
/*     */   
/*     */   public EmptyLineCaveNodeShape(Vector3d o, Vector3d v) {
/*  25 */     this.o = o;
/*  26 */     this.v = v;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d getStart() {
/*  32 */     return this.o.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d getEnd() {
/*  39 */     double x = this.o.x + this.v.x;
/*  40 */     double y = this.o.y + this.v.y;
/*  41 */     double z = this.o.z + this.v.z;
/*  42 */     return new Vector3d(x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d getAnchor(@Nonnull Vector3d vector, double t, double tv, double th) {
/*  48 */     return CaveNodeShapeUtils.getLineAnchor(vector, this.o, this.v, t);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public IWorldBounds getBounds() {
/*  54 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLowBoundX() {
/*  59 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLowBoundZ() {
/*  64 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHighBoundX() {
/*  69 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHighBoundZ() {
/*  74 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLowBoundY() {
/*  79 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHighBoundY() {
/*  84 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldReplace(int seed, double x, double z, int y) {
/*  89 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getFloorPosition(int seed, double x, double z) {
/*  94 */     return -1.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getCeilingPosition(int seed, double x, double z) {
/*  99 */     return -1.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasGeometry() {
/* 104 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 110 */     return "EmptyLineCaveNodeShape{o=" + String.valueOf(this.o) + ", v=" + String.valueOf(this.v) + "}";
/*     */   }
/*     */ 
/*     */   
/*     */   public static class EmptyLineCaveNodeShapeGenerator
/*     */     implements CaveNodeShapeEnum.CaveNodeShapeGenerator
/*     */   {
/*     */     private final IDoubleRange length;
/*     */ 
/*     */     
/*     */     public EmptyLineCaveNodeShapeGenerator(IDoubleRange length) {
/* 121 */       this.length = length;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public CaveNodeShape generateCaveNodeShape(@Nonnull Random random, CaveType caveType, CaveNode parentNode, CaveNodeType.CaveNodeChildEntry childEntry, Vector3d origin, float yaw, float pitch) {
/* 131 */       double l = this.length.getValue(random.nextDouble());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 137 */       Vector3d direction = (new Vector3d((TrigMathUtil.sin(pitch) * TrigMathUtil.cos(yaw)), TrigMathUtil.cos(pitch), (TrigMathUtil.sin(pitch) * TrigMathUtil.sin(yaw)))).scale(l);
/*     */       
/* 139 */       return new EmptyLineCaveNodeShape(origin, direction);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\cave\shape\EmptyLineCaveNodeShape.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */