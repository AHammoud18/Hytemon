/*     */ package com.hypixel.hytale.procedurallib.logic.cell;
/*     */ 
/*     */ import com.hypixel.hytale.math.util.HashUtil;
/*     */ import com.hypixel.hytale.procedurallib.logic.CellularNoise;
/*     */ import com.hypixel.hytale.procedurallib.logic.DoubleArray;
/*     */ import com.hypixel.hytale.procedurallib.logic.ResultBuffer;
/*     */ import com.hypixel.hytale.procedurallib.logic.cell.evaluator.PointEvaluator;
/*     */ import com.hypixel.hytale.procedurallib.logic.cell.jitter.CellJitter;
/*     */ import com.hypixel.hytale.procedurallib.logic.point.PointConsumer;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class GridCellDistanceFunction
/*     */   implements CellDistanceFunction
/*     */ {
/*  15 */   public static final GridCellDistanceFunction DISTANCE_FUNCTION = new GridCellDistanceFunction();
/*  16 */   public static final CellPointFunction POINT_FUNCTION = new CellPointFunction()
/*     */     {
/*     */       public int getHash(int seed, int cellX, int cellY) {
/*  19 */         return GridCellDistanceFunction.getHash(seed, cellX, cellY);
/*     */       }
/*     */ 
/*     */       
/*     */       public DoubleArray.Double2 getOffsets(int hash) {
/*  24 */         return CellularNoise.CELL_2D[hash & 0xFF];
/*     */       }
/*     */ 
/*     */       
/*     */       public double getX(double x, double y) {
/*  29 */         return x;
/*     */       }
/*     */ 
/*     */       
/*     */       public double getY(double x, double y) {
/*  34 */         return y;
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   public void nearest2D(int seed, double x, double y, int cellX, int cellY, ResultBuffer.ResultBuffer2d buffer, @Nonnull PointEvaluator pointEvaluator) {
/*  40 */     for (int cy = cellY - 1; cy <= cellY + 1; cy++) {
/*  41 */       for (int cx = cellX - 1; cx <= cellX + 1; cx++) {
/*  42 */         evalPoint(seed, x, y, cx, cy, buffer, pointEvaluator);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void nearest3D(int seed, double x, double y, double z, int cellX, int cellY, int cellZ, ResultBuffer.ResultBuffer3d buffer, @Nonnull PointEvaluator pointEvaluator) {
/*  49 */     for (int cx = cellX - 1; cx <= cellX + 1; cx++) {
/*  50 */       for (int cy = cellY - 1; cy <= cellY + 1; cy++) {
/*  51 */         for (int cz = cellZ - 1; cz <= cellZ + 1; cz++) {
/*  52 */           evalPoint(seed, x, y, z, cx, cy, cz, buffer, pointEvaluator);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void transition2D(int seed, double x, double y, int cellX, int cellY, ResultBuffer.ResultBuffer2d buffer, @Nonnull PointEvaluator pointEvaluator) {
/*  60 */     for (int cy = cellY - 1; cy <= cellY + 1; cy++) {
/*  61 */       for (int cx = cellX - 1; cx <= cellX + 1; cx++) {
/*  62 */         evalPoint2(seed, x, y, cx, cy, buffer, pointEvaluator);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void transition3D(int seed, double x, double y, double z, int cellX, int cellY, int cellZ, ResultBuffer.ResultBuffer3d buffer, @Nonnull PointEvaluator pointEvaluator) {
/*  69 */     for (int cx = cellX - 1; cx <= cellX + 1; cx++) {
/*  70 */       for (int cy = cellY - 1; cy <= cellY + 1; cy++) {
/*  71 */         for (int cz = cellZ - 1; cz <= cellZ + 1; cz++) {
/*  72 */           evalPoint2(seed, x, y, z, cx, cy, cz, buffer, pointEvaluator);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void evalPoint(int seed, double x, double y, int cellX, int cellY, ResultBuffer.ResultBuffer2d buffer, @Nonnull PointEvaluator pointEvaluator) {
/*  80 */     int cellHash = getHash(seed, cellX, cellY);
/*  81 */     DoubleArray.Double2 vec = CellularNoise.CELL_2D[cellHash & 0xFF];
/*     */     
/*  83 */     CellJitter jitter = pointEvaluator.getJitter();
/*  84 */     double px = jitter.getPointX(cellX, vec);
/*  85 */     double py = jitter.getPointY(cellY, vec);
/*     */     
/*  87 */     pointEvaluator.evalPoint(seed, x, y, cellHash, cellX, cellY, px, py, buffer);
/*     */   }
/*     */ 
/*     */   
/*     */   public void evalPoint(int seed, double x, double y, double z, int cellX, int cellY, int cellZ, ResultBuffer.ResultBuffer3d buffer, @Nonnull PointEvaluator pointEvaluator) {
/*  92 */     int cellHash = getHash(seed, cellX, cellY);
/*  93 */     DoubleArray.Double3 vec = CellularNoise.CELL_3D[cellHash & 0xFF];
/*     */     
/*  95 */     CellJitter jitter = pointEvaluator.getJitter();
/*  96 */     double px = jitter.getPointX(cellX, vec);
/*  97 */     double py = jitter.getPointY(cellX, vec);
/*  98 */     double pz = jitter.getPointZ(cellX, vec);
/*     */     
/* 100 */     pointEvaluator.evalPoint(seed, x, y, z, cellHash, cellX, cellY, cellZ, px, py, pz, buffer);
/*     */   }
/*     */ 
/*     */   
/*     */   public void evalPoint2(int seed, double x, double y, int cellX, int cellY, ResultBuffer.ResultBuffer2d buffer, @Nonnull PointEvaluator pointEvaluator) {
/* 105 */     int cellHash = getHash(seed, cellX, cellY);
/* 106 */     DoubleArray.Double2 vec = CellularNoise.CELL_2D[cellHash & 0xFF];
/*     */     
/* 108 */     CellJitter jitter = pointEvaluator.getJitter();
/* 109 */     double px = jitter.getPointX(cellX, vec);
/* 110 */     double py = jitter.getPointY(cellY, vec);
/*     */     
/* 112 */     pointEvaluator.evalPoint2(seed, x, y, cellHash, cellX, cellY, px, py, buffer);
/*     */   }
/*     */ 
/*     */   
/*     */   public void evalPoint2(int seed, double x, double y, double z, int cellX, int cellY, int cellZ, ResultBuffer.ResultBuffer3d buffer, @Nonnull PointEvaluator pointEvaluator) {
/* 117 */     int cellHash = getHash(seed, cellX, cellY);
/* 118 */     DoubleArray.Double3 vec = CellularNoise.CELL_3D[cellHash & 0xFF];
/*     */     
/* 120 */     CellJitter jitter = pointEvaluator.getJitter();
/* 121 */     double px = jitter.getPointX(cellX, vec);
/* 122 */     double py = jitter.getPointY(cellX, vec);
/* 123 */     double pz = jitter.getPointZ(cellX, vec);
/*     */     
/* 125 */     pointEvaluator.evalPoint2(seed, x, y, z, cellHash, cellX, cellY, cellZ, px, py, pz, buffer);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> void collect(int originalSeed, int seed, int minX, int minY, int maxX, int maxY, ResultBuffer.Bounds2d bounds, T ctx, @Nonnull PointConsumer<T> collector, @Nonnull PointEvaluator pointEvaluator) {
/* 130 */     CellJitter jitter = pointEvaluator.getJitter();
/* 131 */     for (int cy = minY; cy <= maxY; cy++) {
/* 132 */       for (int cx = minX; cx <= maxX; cx++) {
/* 133 */         int cellHash = getHash(seed, cx, cy);
/*     */         
/* 135 */         DoubleArray.Double2 vec = CellularNoise.CELL_2D[cellHash & 0xFF];
/* 136 */         double px = jitter.getPointX(cx, vec);
/* 137 */         double py = jitter.getPointY(cy, vec);
/*     */         
/* 139 */         pointEvaluator.collectPoint(cellHash, cx, cy, px, py, ctx, collector);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 147 */     return "GridCellFunction{}";
/*     */   }
/*     */   
/*     */   public static int getHash(int seed, int cellX, int cellY) {
/* 151 */     return (int)HashUtil.rehash(seed, cellX, cellY);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\logic\cell\GridCellDistanceFunction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */