/*     */ package com.hypixel.hytale.procedurallib.logic.point;
/*     */ 
/*     */ import com.hypixel.hytale.procedurallib.logic.ResultBuffer;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ScaledPointGenerator
/*     */   implements IPointGenerator
/*     */ {
/*     */   protected final PointGenerator pointGenerator;
/*     */   protected final double scale;
/*     */   
/*     */   public ScaledPointGenerator(PointGenerator pointGenerator, double scale) {
/*  19 */     this.pointGenerator = pointGenerator;
/*  20 */     this.scale = scale;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ResultBuffer.ResultBuffer2d nearest2D(int seed, double x, double y) {
/*  26 */     ResultBuffer.ResultBuffer2d buf = this.pointGenerator.nearest2D(seed, x * this.scale, y * this.scale);
/*  27 */     buf.x /= this.scale;
/*  28 */     buf.y /= this.scale;
/*  29 */     buf.distance = Math.sqrt(buf.distance) / this.scale;
/*  30 */     return buf;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ResultBuffer.ResultBuffer3d nearest3D(int seed, double x, double y, double z) {
/*  36 */     ResultBuffer.ResultBuffer3d buf = this.pointGenerator.nearest3D(seed, x * this.scale, y * this.scale, z * this.scale);
/*  37 */     buf.x /= this.scale;
/*  38 */     buf.y /= this.scale;
/*  39 */     buf.z /= this.scale;
/*  40 */     buf.distance = Math.sqrt(buf.distance) / this.scale;
/*  41 */     return buf;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ResultBuffer.ResultBuffer2d transition2D(int seed, double x, double y) {
/*  47 */     ResultBuffer.ResultBuffer2d buf = this.pointGenerator.transition2D(seed, x * this.scale, y * this.scale);
/*  48 */     buf.x /= this.scale;
/*  49 */     buf.x2 /= this.scale;
/*  50 */     buf.y /= this.scale;
/*  51 */     buf.y2 /= this.scale;
/*  52 */     buf.distance = Math.sqrt(buf.distance) / this.scale;
/*  53 */     buf.distance2 = Math.sqrt(buf.distance2) / this.scale;
/*  54 */     return buf;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ResultBuffer.ResultBuffer3d transition3D(int seed, double x, double y, double z) {
/*  60 */     ResultBuffer.ResultBuffer3d buf = this.pointGenerator.transition3D(seed, x * this.scale, y * this.scale, z * this.scale);
/*  61 */     buf.x /= this.scale;
/*  62 */     buf.x2 /= this.scale;
/*  63 */     buf.y /= this.scale;
/*  64 */     buf.y2 /= this.scale;
/*  65 */     buf.z /= this.scale;
/*  66 */     buf.z2 /= this.scale;
/*  67 */     buf.distance = Math.sqrt(buf.distance) / this.scale;
/*  68 */     buf.distance2 = Math.sqrt(buf.distance2) / this.scale;
/*  69 */     return buf;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getInterval() {
/*  74 */     return this.pointGenerator.getInterval() / this.scale;
/*     */   }
/*     */ 
/*     */   
/*     */   public void collect(int seed, double minX, double minY, double maxX, double maxY, IPointGenerator.PointConsumer2d consumer) {
/*  79 */     minX *= this.scale;
/*  80 */     minY *= this.scale;
/*  81 */     maxX *= this.scale;
/*  82 */     maxY *= this.scale;
/*  83 */     this.pointGenerator.collect0(seed, minX, minY, maxX, maxY, (x, y, t) -> t.accept(x / this.scale, y / this.scale), consumer);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object o) {
/*  88 */     if (this == o) return true; 
/*  89 */     if (o == null || getClass() != o.getClass()) return false;
/*     */     
/*  91 */     ScaledPointGenerator that = (ScaledPointGenerator)o;
/*     */     
/*  93 */     if (Double.compare(that.scale, this.scale) != 0) return false; 
/*  94 */     return this.pointGenerator.equals(that.pointGenerator);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 101 */     int result = this.pointGenerator.hashCode();
/* 102 */     long temp = Double.doubleToLongBits(this.scale);
/* 103 */     result = 31 * result + (int)(temp ^ temp >>> 32L);
/* 104 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 110 */     return "ScaledPointGenerator{pointGenerator=" + String.valueOf(this.pointGenerator) + ", scale=" + this.scale + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\logic\point\ScaledPointGenerator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */