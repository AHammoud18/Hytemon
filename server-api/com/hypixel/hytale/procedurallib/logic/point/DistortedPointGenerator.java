/*    */ package com.hypixel.hytale.procedurallib.logic.point;
/*    */ 
/*    */ import com.hypixel.hytale.procedurallib.logic.ResultBuffer;
/*    */ import com.hypixel.hytale.procedurallib.random.ICoordinateRandomizer;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DistortedPointGenerator
/*    */   implements IPointGenerator
/*    */ {
/*    */   protected final IPointGenerator pointGenerator;
/*    */   protected final ICoordinateRandomizer coordinateRandomizer;
/*    */   
/*    */   public DistortedPointGenerator(IPointGenerator pointGenerator, ICoordinateRandomizer coordinateRandomizer) {
/* 20 */     this.pointGenerator = pointGenerator;
/* 21 */     this.coordinateRandomizer = coordinateRandomizer;
/*    */   }
/*    */ 
/*    */   
/*    */   public ResultBuffer.ResultBuffer2d nearest2D(int seed, double x, double y) {
/* 26 */     return this.pointGenerator.nearest2D(seed, this.coordinateRandomizer
/* 27 */         .randomDoubleX(seed, x, y), this.coordinateRandomizer
/* 28 */         .randomDoubleY(seed, x, y));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ResultBuffer.ResultBuffer3d nearest3D(int seed, double x, double y, double z) {
/* 34 */     return this.pointGenerator.nearest3D(seed, this.coordinateRandomizer
/* 35 */         .randomDoubleX(seed, x, y, z), this.coordinateRandomizer
/* 36 */         .randomDoubleY(seed, x, y, z), this.coordinateRandomizer
/* 37 */         .randomDoubleZ(seed, x, y, z));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ResultBuffer.ResultBuffer2d transition2D(int seed, double x, double y) {
/* 43 */     return this.pointGenerator.transition2D(seed, this.coordinateRandomizer
/* 44 */         .randomDoubleX(seed, x, y), this.coordinateRandomizer
/* 45 */         .randomDoubleY(seed, x, y));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ResultBuffer.ResultBuffer3d transition3D(int seed, double x, double y, double z) {
/* 51 */     return this.pointGenerator.transition3D(seed, this.coordinateRandomizer
/* 52 */         .randomDoubleX(seed, x, y, z), this.coordinateRandomizer
/* 53 */         .randomDoubleY(seed, x, y, z), this.coordinateRandomizer
/* 54 */         .randomDoubleZ(seed, x, y, z));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public double getInterval() {
/* 60 */     return this.pointGenerator.getInterval();
/*    */   }
/*    */ 
/*    */   
/*    */   public void collect(int seed, double minX, double minY, double maxX, double maxY, IPointGenerator.PointConsumer2d consumer) {
/* 65 */     this.pointGenerator.collect(seed, minX, minY, maxX, maxY, consumer);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(@Nullable Object o) {
/* 70 */     if (this == o) return true; 
/* 71 */     if (o == null || getClass() != o.getClass()) return false;
/*    */     
/* 73 */     DistortedPointGenerator that = (DistortedPointGenerator)o;
/*    */     
/* 75 */     if (!this.pointGenerator.equals(that.pointGenerator)) return false; 
/* 76 */     return this.coordinateRandomizer.equals(that.coordinateRandomizer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 81 */     int result = this.pointGenerator.hashCode();
/* 82 */     result = 31 * result + this.coordinateRandomizer.hashCode();
/* 83 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 89 */     return "DistortedPointGenerator{pointGenerator=" + String.valueOf(this.pointGenerator) + ", coordinateRandomizer=" + String.valueOf(this.coordinateRandomizer) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\logic\point\DistortedPointGenerator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */