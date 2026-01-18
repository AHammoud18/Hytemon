/*    */ package com.hypixel.hytale.procedurallib.logic.point;
/*    */ 
/*    */ import com.hypixel.hytale.procedurallib.logic.ResultBuffer;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class OffsetPointGenerator
/*    */   implements IPointGenerator {
/*    */   private final IPointGenerator generator;
/*    */   private final double offsetX;
/*    */   
/*    */   public OffsetPointGenerator(IPointGenerator generator, double offsetX, double offsetY, double offsetZ) {
/* 12 */     this.generator = generator;
/* 13 */     this.offsetX = offsetX;
/* 14 */     this.offsetY = offsetY;
/* 15 */     this.offsetZ = offsetZ;
/*    */   }
/*    */   private final double offsetY; private final double offsetZ;
/*    */   public double getOffsetX() {
/* 19 */     return this.offsetX;
/*    */   }
/*    */   
/*    */   public double getOffsetY() {
/* 23 */     return this.offsetY;
/*    */   }
/*    */   
/*    */   public double getOffsetZ() {
/* 27 */     return this.offsetZ;
/*    */   }
/*    */ 
/*    */   
/*    */   public ResultBuffer.ResultBuffer2d nearest2D(int seed, double x, double y) {
/* 32 */     return this.generator.nearest2D(seed, x + this.offsetX, y + this.offsetY);
/*    */   }
/*    */ 
/*    */   
/*    */   public ResultBuffer.ResultBuffer3d nearest3D(int seed, double x, double y, double z) {
/* 37 */     return this.generator.nearest3D(seed, x + this.offsetX, y + this.offsetY, z + this.offsetZ);
/*    */   }
/*    */ 
/*    */   
/*    */   public ResultBuffer.ResultBuffer2d transition2D(int seed, double x, double y) {
/* 42 */     return this.generator.transition2D(seed, x + this.offsetX, y + this.offsetY);
/*    */   }
/*    */ 
/*    */   
/*    */   public ResultBuffer.ResultBuffer3d transition3D(int seed, double x, double y, double z) {
/* 47 */     return this.generator.transition3D(seed, x + this.offsetX, y + this.offsetY, z + this.offsetZ);
/*    */   }
/*    */ 
/*    */   
/*    */   public void collect(int seed, double minX, double minY, double maxX, double maxY, @Nonnull IPointGenerator.PointConsumer2d consumer) {
/* 52 */     this.generator.collect(seed, minX, minY, maxX, maxY, (x, y) -> consumer.accept(x + this.offsetX, y + this.offsetY));
/*    */   }
/*    */ 
/*    */   
/*    */   public double getInterval() {
/* 57 */     return this.generator.getInterval();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\logic\point\OffsetPointGenerator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */