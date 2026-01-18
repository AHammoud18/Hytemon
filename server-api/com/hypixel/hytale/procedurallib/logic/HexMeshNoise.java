/*     */ package com.hypixel.hytale.procedurallib.logic;
/*     */ 
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.procedurallib.NoiseFunction;
/*     */ import com.hypixel.hytale.procedurallib.condition.IIntCondition;
/*     */ import com.hypixel.hytale.procedurallib.logic.cell.HexCellDistanceFunction;
/*     */ import com.hypixel.hytale.procedurallib.logic.cell.jitter.CellJitter;
/*     */ 
/*     */ public class HexMeshNoise implements NoiseFunction {
/*     */   protected final IIntCondition density;
/*     */   protected final double thickness;
/*     */   protected final double thicknessSquared;
/*     */   protected final CellJitter jitter;
/*     */   protected final boolean linesX;
/*     */   protected final boolean linesY;
/*     */   protected final boolean linesZ;
/*     */   
/*     */   public HexMeshNoise(IIntCondition density, double thickness, CellJitter jitter, boolean linesX, boolean linesY, boolean linesZ) {
/*  19 */     double domainLocalThickness = HexCellDistanceFunction.DISTANCE_FUNCTION.scale(thickness);
/*     */     
/*  21 */     this.density = density;
/*  22 */     this.thickness = domainLocalThickness;
/*  23 */     this.thicknessSquared = domainLocalThickness * domainLocalThickness;
/*  24 */     this.jitter = jitter;
/*  25 */     this.linesX = linesX;
/*  26 */     this.linesY = linesY;
/*  27 */     this.linesZ = linesZ;
/*     */   }
/*     */ 
/*     */   
/*     */   public double get(int seed, int offsetSeed, double x, double y) {
/*  32 */     x = HexCellDistanceFunction.DISTANCE_FUNCTION.scale(x);
/*  33 */     y = HexCellDistanceFunction.DISTANCE_FUNCTION.scale(y);
/*     */     
/*  35 */     int cx = HexCellDistanceFunction.toGridX(x, y);
/*  36 */     int cy = HexCellDistanceFunction.toGridY(x, y);
/*     */     
/*  38 */     double nearest = this.thicknessSquared;
/*  39 */     nearest = checkConnections(offsetSeed, x, y, cx - 1, cy - 1, nearest);
/*  40 */     nearest = checkConnections(offsetSeed, x, y, cx - 1, cy + 0, nearest);
/*  41 */     nearest = checkConnections(offsetSeed, x, y, cx + 1, cy + 0, nearest);
/*  42 */     nearest = checkConnections(offsetSeed, x, y, cx + 0, cy - 1, nearest);
/*  43 */     nearest = checkConnections(offsetSeed, x, y, cx + 0, cy + 1, nearest);
/*     */     
/*  45 */     if (this.linesZ) {
/*  46 */       nearest = checkDiagonalConnections(offsetSeed, x, y, cx + 0, cy + 0, nearest);
/*  47 */       nearest = checkDiagonalConnections(offsetSeed, x, y, cx + 0, cy - 1, nearest);
/*  48 */       nearest = checkDiagonalConnections(offsetSeed, x, y, cx + 0, cy + 1, nearest);
/*  49 */       nearest = checkDiagonalConnections(offsetSeed, x, y, cx - 1, cy + 0, nearest);
/*  50 */       nearest = checkDiagonalConnections(offsetSeed, x, y, cx - 1, cy - 1, nearest);
/*     */     } 
/*     */     
/*  53 */     if (nearest < this.thicknessSquared) {
/*  54 */       double distance = Math.sqrt(nearest);
/*  55 */       double d = distance / this.thickness;
/*  56 */       return d * 2.0D - 1.0D;
/*     */     } 
/*  58 */     return 1.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double get(int seed, int offsetSeed, double x, double y, double z) {
/*  64 */     throw new UnsupportedOperationException("3d not supported");
/*     */   }
/*     */   
/*     */   protected double checkConnections(int offsetSeed, double x, double y, int cx, int cy, double nearest) {
/*  68 */     int hash = HexCellDistanceFunction.getHash(offsetSeed, cx, cy);
/*  69 */     if (!this.density.eval(hash)) return nearest;
/*     */     
/*  71 */     DoubleArray.Double2 vec = HexCellDistanceFunction.HEX_CELL_2D[hash & 0xFF];
/*  72 */     double px = this.jitter.getPointX(cx, vec);
/*  73 */     double py = this.jitter.getPointY(cy, vec);
/*     */     
/*  75 */     double ax = HexCellDistanceFunction.toHexX(px, py);
/*  76 */     double ay = HexCellDistanceFunction.toHexY(px, py);
/*     */     
/*  78 */     double adx = x - ax;
/*  79 */     double ady = y - ay;
/*     */     
/*  81 */     if (this.linesX) {
/*  82 */       nearest = Math.min(nearest, dist2Cell(offsetSeed, x, y, adx, ady, ax, ay, cx - 1, cy));
/*  83 */       nearest = Math.min(nearest, dist2Cell(offsetSeed, x, y, adx, ady, ax, ay, cx + 1, cy));
/*     */     } 
/*     */     
/*  86 */     if (this.linesY) {
/*  87 */       nearest = Math.min(nearest, dist2Cell(offsetSeed, x, y, adx, ady, ax, ay, cx, cy - 1));
/*  88 */       nearest = Math.min(nearest, dist2Cell(offsetSeed, x, y, adx, ady, ax, ay, cx, cy + 1));
/*     */     } 
/*     */     
/*  91 */     return nearest;
/*     */   }
/*     */   
/*     */   protected double checkDiagonalConnections(int offsetSeed, double x, double y, int cx, int cy, double nearest) {
/*  95 */     int hash = HexCellDistanceFunction.getHash(offsetSeed, cx, cy);
/*  96 */     if (!this.density.eval(hash)) return nearest;
/*     */     
/*  98 */     DoubleArray.Double2 vec = HexCellDistanceFunction.HEX_CELL_2D[hash & 0xFF];
/*  99 */     double px = this.jitter.getPointX(cx, vec);
/* 100 */     double py = this.jitter.getPointY(cy, vec);
/*     */     
/* 102 */     double ax = HexCellDistanceFunction.toHexX(px, py);
/* 103 */     double ay = HexCellDistanceFunction.toHexY(px, py);
/*     */     
/* 105 */     double adx = x - ax;
/* 106 */     double ady = y - ay;
/*     */     
/* 108 */     nearest = Math.min(nearest, dist2Cell(offsetSeed, x, y, adx, ady, ax, ay, cx - 1, cy + 1));
/* 109 */     nearest = Math.min(nearest, dist2Cell(offsetSeed, x, y, adx, ady, ax, ay, cx + 1, cy - 1));
/*     */     
/* 111 */     return nearest;
/*     */   }
/*     */   
/*     */   protected double dist2Cell(int offsetSeed, double x, double y, double adx, double ady, double ax, double ay, int cx, int cy) {
/* 115 */     int hash = HexCellDistanceFunction.getHash(offsetSeed, cx, cy);
/* 116 */     if (!this.density.eval(hash)) return Double.MAX_VALUE;
/*     */     
/* 118 */     DoubleArray.Double2 vec = HexCellDistanceFunction.HEX_CELL_2D[hash & 0xFF];
/* 119 */     double px = this.jitter.getPointX(cx, vec);
/* 120 */     double py = this.jitter.getPointY(cy, vec);
/*     */     
/* 122 */     double bx = HexCellDistanceFunction.toHexX(px, py);
/* 123 */     double by = HexCellDistanceFunction.toHexY(px, py);
/*     */     
/* 125 */     return MathUtil.distanceToLineSq(x, y, ax, ay, bx, by, adx, ady, bx - ax, by - ay);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\logic\HexMeshNoise.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */