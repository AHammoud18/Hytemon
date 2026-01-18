/*     */ package com.hypixel.hytale.procedurallib.logic.cell;
/*     */ 
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.procedurallib.logic.CellularNoise;
/*     */ import com.hypixel.hytale.procedurallib.logic.DoubleArray;
/*     */ import com.hypixel.hytale.procedurallib.logic.ResultBuffer;
/*     */ import com.hypixel.hytale.procedurallib.logic.cell.evaluator.PointEvaluator;
/*     */ import com.hypixel.hytale.procedurallib.logic.cell.jitter.CellJitter;
/*     */ import com.hypixel.hytale.procedurallib.logic.point.PointConsumer;
/*     */ import java.util.stream.Stream;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class HexCellDistanceFunction implements CellDistanceFunction {
/*  14 */   public static final HexCellDistanceFunction DISTANCE_FUNCTION = new HexCellDistanceFunction();
/*  15 */   public static final CellPointFunction POINT_FUNCTION = new CellPointFunction()
/*     */     {
/*     */       public double scale(double value) {
/*  18 */         return value * HexCellDistanceFunction.SCALE;
/*     */       }
/*     */ 
/*     */       
/*     */       public double normalize(double value) {
/*  23 */         return value * 0.3333333333333333D;
/*     */       }
/*     */ 
/*     */       
/*     */       public int getHash(int seed, int cellX, int cellY) {
/*  28 */         return HexCellDistanceFunction.getHash(seed, cellX, cellY);
/*     */       }
/*     */ 
/*     */       
/*     */       public double getX(double x, double y) {
/*  33 */         return HexCellDistanceFunction.toHexX(x, y);
/*     */       }
/*     */ 
/*     */       
/*     */       public double getY(double x, double y) {
/*  38 */         return HexCellDistanceFunction.toHexY(x, y);
/*     */       }
/*     */ 
/*     */       
/*     */       public DoubleArray.Double2 getOffsets(int hash) {
/*  43 */         return HexCellDistanceFunction.HEX_CELL_2D[hash & 0xFF];
/*     */       }
/*     */     };
/*     */   
/*  47 */   protected static final double X_TO_GRID_X = Math.sqrt(3.0D) / 3.0D;
/*     */   
/*     */   protected static final double Y_TO_GRID_X = -0.3333333333333333D;
/*     */   protected static final double Y_TO_GRID_Y = 0.6666666666666666D;
/*  51 */   protected static final double X_TO_HEX_X = Math.sqrt(3.0D);
/*  52 */   protected static final double Y_TO_HEX_X = Math.sqrt(3.0D) / 2.0D;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final double Y_TO_HEX_Y = 1.5D;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final double NORMALIZATION = 0.3333333333333333D;
/*     */ 
/*     */ 
/*     */   
/*  66 */   protected static final double SCALE = (X_TO_HEX_X + 1.5D) / 2.0D;
/*     */ 
/*     */   
/*     */   public static final DoubleArray.Double2[] HEX_CELL_2D;
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  74 */     HEX_CELL_2D = (DoubleArray.Double2[])Stream.<DoubleArray.Double2>of(CellularNoise.CELL_2D).map(d -> new DoubleArray.Double2(d.x - 0.5D, d.y - 0.5D)).toArray(x$0 -> new DoubleArray.Double2[x$0]);
/*     */   }
/*     */   
/*     */   public double scale(double value) {
/*  78 */     return value * SCALE;
/*     */   }
/*     */ 
/*     */   
/*     */   public double invScale(double value) {
/*  83 */     return value / SCALE;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCellX(double x, double y) {
/*  88 */     return toGridX(x, y);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCellY(double x, double y) {
/*  93 */     return toGridY(x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void nearest2D(int seed, double x, double y, int cellX, int cellY, @Nonnull ResultBuffer.ResultBuffer2d buffer, @Nonnull PointEvaluator pointEvaluator) {
/*  99 */     evalPoint(seed, x, y, cellX - 1, cellY - 1, buffer, pointEvaluator);
/* 100 */     evalPoint(seed, x, y, cellX + 0, cellY - 1, buffer, pointEvaluator);
/* 101 */     evalPoint(seed, x, y, cellX + 1, cellY - 1, buffer, pointEvaluator);
/* 102 */     evalPoint(seed, x, y, cellX - 1, cellY + 0, buffer, pointEvaluator);
/* 103 */     evalPoint(seed, x, y, cellX + 0, cellY + 0, buffer, pointEvaluator);
/* 104 */     evalPoint(seed, x, y, cellX + 1, cellY + 0, buffer, pointEvaluator);
/* 105 */     evalPoint(seed, x, y, cellX - 1, cellY + 1, buffer, pointEvaluator);
/* 106 */     evalPoint(seed, x, y, cellX + 0, cellY + 1, buffer, pointEvaluator);
/* 107 */     evalPoint(seed, x, y, cellX + 1, cellY + 1, buffer, pointEvaluator);
/*     */ 
/*     */     
/* 110 */     buffer.distance *= 0.3333333333333333D;
/*     */   }
/*     */ 
/*     */   
/*     */   public void nearest3D(int seed, double x, double y, double z, int cellX, int cellY, int cellZ, ResultBuffer.ResultBuffer3d buffer, PointEvaluator pointEvaluator) {
/* 115 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void transition2D(int seed, double x, double y, int cellX, int cellY, @Nonnull ResultBuffer.ResultBuffer2d buffer, @Nonnull PointEvaluator pointEvaluator) {
/* 121 */     evalPoint2(seed, x, y, cellX - 1, cellY - 1, buffer, pointEvaluator);
/* 122 */     evalPoint2(seed, x, y, cellX + 0, cellY - 1, buffer, pointEvaluator);
/* 123 */     evalPoint2(seed, x, y, cellX + 1, cellY - 1, buffer, pointEvaluator);
/* 124 */     evalPoint2(seed, x, y, cellX - 1, cellY + 0, buffer, pointEvaluator);
/* 125 */     evalPoint2(seed, x, y, cellX + 0, cellY + 0, buffer, pointEvaluator);
/* 126 */     evalPoint2(seed, x, y, cellX + 1, cellY + 0, buffer, pointEvaluator);
/* 127 */     evalPoint2(seed, x, y, cellX - 1, cellY + 1, buffer, pointEvaluator);
/* 128 */     evalPoint2(seed, x, y, cellX + 0, cellY + 1, buffer, pointEvaluator);
/* 129 */     evalPoint2(seed, x, y, cellX + 1, cellY + 1, buffer, pointEvaluator);
/*     */ 
/*     */     
/* 132 */     CellJitter jitter = pointEvaluator.getJitter();
/* 133 */     if (jitter.getMaxX() > 0.5D) {
/* 134 */       evalPoint2(seed, x, y, cellX - 2, cellY - 1, buffer, pointEvaluator);
/* 135 */       evalPoint2(seed, x, y, cellX - 2, cellY + 0, buffer, pointEvaluator);
/* 136 */       evalPoint2(seed, x, y, cellX - 2, cellY + 1, buffer, pointEvaluator);
/*     */       
/* 138 */       evalPoint2(seed, x, y, cellX + 2, cellY + 0, buffer, pointEvaluator);
/* 139 */       evalPoint2(seed, x, y, cellX + 2, cellY - 1, buffer, pointEvaluator);
/* 140 */       evalPoint2(seed, x, y, cellX + 2, cellY + 1, buffer, pointEvaluator);
/*     */     } 
/*     */ 
/*     */     
/* 144 */     if (jitter.getMaxY() > 0.5D) {
/* 145 */       evalPoint2(seed, x, y, cellX - 1, cellY - 2, buffer, pointEvaluator);
/* 146 */       evalPoint2(seed, x, y, cellX + 0, cellY - 2, buffer, pointEvaluator);
/* 147 */       evalPoint2(seed, x, y, cellX + 1, cellY - 2, buffer, pointEvaluator);
/*     */       
/* 149 */       evalPoint2(seed, x, y, cellX - 1, cellY + 2, buffer, pointEvaluator);
/* 150 */       evalPoint2(seed, x, y, cellX + 0, cellY + 2, buffer, pointEvaluator);
/* 151 */       evalPoint2(seed, x, y, cellX + 1, cellY + 2, buffer, pointEvaluator);
/*     */     } 
/*     */ 
/*     */     
/* 155 */     buffer.distance *= 0.3333333333333333D;
/* 156 */     buffer.distance2 *= 0.3333333333333333D;
/*     */   }
/*     */ 
/*     */   
/*     */   public void transition3D(int seed, double x, double y, double z, int cellX, int cellY, int cellZ, ResultBuffer.ResultBuffer3d buffer, PointEvaluator pointEvaluator) {
/* 161 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public void evalPoint(int seed, double x, double y, int cellX, int cellY, ResultBuffer.ResultBuffer2d buffer, @Nonnull PointEvaluator pointEvaluator) {
/* 166 */     int cellHash = getHash(seed, cellX, cellY);
/* 167 */     DoubleArray.Double2 vec = HEX_CELL_2D[cellHash & 0xFF];
/*     */     
/* 169 */     CellJitter jitter = pointEvaluator.getJitter();
/* 170 */     double px = jitter.getPointX(cellX, vec);
/* 171 */     double py = jitter.getPointY(cellY, vec);
/* 172 */     double hx = toHexX(px, py);
/* 173 */     double hy = toHexY(px, py);
/*     */     
/* 175 */     pointEvaluator.evalPoint(seed, x, y, cellHash, cellX, cellY, hx, hy, buffer);
/*     */   }
/*     */ 
/*     */   
/*     */   public void evalPoint(int seed, double x, double y, double z, int cellX, int cellY, int cellZ, ResultBuffer.ResultBuffer3d buffer, PointEvaluator pointEvaluator) {
/* 180 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public void evalPoint2(int seed, double x, double y, int cellX, int cellY, ResultBuffer.ResultBuffer2d buffer, @Nonnull PointEvaluator pointEvaluator) {
/* 185 */     int cellHash = getHash(seed, cellX, cellY);
/* 186 */     DoubleArray.Double2 vec = HEX_CELL_2D[cellHash & 0xFF];
/*     */     
/* 188 */     CellJitter jitter = pointEvaluator.getJitter();
/* 189 */     double px = jitter.getPointX(cellX, vec);
/* 190 */     double py = jitter.getPointY(cellY, vec);
/* 191 */     double hx = toHexX(px, py);
/* 192 */     double hy = toHexY(px, py);
/*     */     
/* 194 */     pointEvaluator.evalPoint2(seed, x, y, cellHash, cellX, cellY, hx, hy, buffer);
/*     */   }
/*     */ 
/*     */   
/*     */   public void evalPoint2(int seed, double x, double y, double z, int cellX, int cellY, int cellZ, ResultBuffer.ResultBuffer3d buffer, PointEvaluator pointEvaluator) {
/* 199 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> void collect(int originalSeed, int seed, int minX, int minY, int maxX, int maxY, @Nonnull ResultBuffer.Bounds2d bounds, T ctx, @Nonnull PointConsumer<T> collector, @Nonnull PointEvaluator pointEvaluator) {
/* 205 */     minX--;
/* 206 */     minY--;
/* 207 */     maxX++;
/* 208 */     maxY++;
/*     */     
/* 210 */     int height = maxY - minY;
/*     */ 
/*     */     
/* 213 */     int width = maxX - minX + (height >> 1);
/*     */     
/* 215 */     CellJitter jitter = pointEvaluator.getJitter();
/* 216 */     for (int dy = 0; dy <= height; dy++) {
/* 217 */       int cy = minY + dy;
/*     */ 
/*     */ 
/*     */       
/* 221 */       int startX = minX - (dy >> 1);
/*     */       
/* 223 */       for (int dx = 0; dx <= width; dx++) {
/* 224 */         int cx = startX + dx;
/* 225 */         int cellHash = getHash(seed, cx, cy);
/* 226 */         DoubleArray.Double2 vec = HEX_CELL_2D[cellHash & 0xFF];
/*     */         
/* 228 */         double px = jitter.getPointX(cx, vec);
/* 229 */         double py = jitter.getPointY(cy, vec);
/* 230 */         double hx = toHexX(px, py);
/* 231 */         double hy = toHexY(px, py);
/*     */         
/* 233 */         if (bounds.contains(hx, hy)) {
/*     */ 
/*     */           
/* 236 */           hx /= SCALE;
/* 237 */           hy /= SCALE;
/*     */           
/* 239 */           pointEvaluator.collectPoint(cellHash, cx, cy, hx, hy, ctx, collector);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 247 */     return "HexCellDistanceFunction{}";
/*     */   }
/*     */   
/*     */   public static int getHash(int seed, int x, int y) {
/* 251 */     return SquirrelHash.hash(seed, x, y);
/*     */   }
/*     */   
/*     */   public static int toGridX(double x, double y) {
/* 255 */     return (int)MathUtil.fastRound(X_TO_GRID_X * x + -0.3333333333333333D * y);
/*     */   }
/*     */   
/*     */   public static int toGridY(double x, double y) {
/* 259 */     return (int)MathUtil.fastRound(0.6666666666666666D * y);
/*     */   }
/*     */   
/*     */   public static double toHexX(double hx, double hy) {
/* 263 */     return X_TO_HEX_X * hx + Y_TO_HEX_X * hy;
/*     */   }
/*     */   
/*     */   public static double toHexY(double hx, double hy) {
/* 267 */     return 1.5D * hy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class SquirrelHash
/*     */   {
/*     */     protected static final int HASH0 = 198491317;
/*     */ 
/*     */ 
/*     */     
/*     */     protected static final int BIT_NOISE1 = -1255572915;
/*     */ 
/*     */     
/*     */     protected static final int BIT_NOISE2 = -1255572915;
/*     */ 
/*     */     
/*     */     protected static final int BIT_NOISE3 = -1255572915;
/*     */ 
/*     */ 
/*     */     
/*     */     public static int hash(int seed, int x, int y) {
/* 290 */       int hash = x + y * 198491317;
/* 291 */       hash *= -1255572915;
/* 292 */       hash += seed;
/* 293 */       hash ^= hash >> 8;
/* 294 */       hash -= 1255572915;
/* 295 */       hash ^= hash << 8;
/* 296 */       hash *= -1255572915;
/* 297 */       hash ^= hash >> 8;
/* 298 */       return hash;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\logic\cell\HexCellDistanceFunction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */