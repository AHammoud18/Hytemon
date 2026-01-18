/*     */ package com.hypixel.hytale.procedurallib.logic;
/*     */ 
/*     */ import com.hypixel.hytale.math.vector.Vector2i;
/*     */ import com.hypixel.hytale.procedurallib.NoiseFunction;
/*     */ import com.hypixel.hytale.procedurallib.condition.IIntCondition;
/*     */ import com.hypixel.hytale.procedurallib.logic.cell.GridCellDistanceFunction;
/*     */ 
/*     */ 
/*     */ public class MeshNoise
/*     */   implements NoiseFunction
/*     */ {
/*  12 */   public static final Vector2i[] ADJACENT_CELLS = new Vector2i[] { new Vector2i(-1, 0), new Vector2i(0, -1), new Vector2i(1, 0), new Vector2i(0, 1) };
/*     */   
/*     */   private final IIntCondition density;
/*     */   
/*     */   private final double thickness;
/*     */   
/*     */   private final double thicknessSquared;
/*     */   
/*     */   private final double jitterX;
/*     */   private final double jitterY;
/*     */   
/*     */   public MeshNoise(IIntCondition density, double thickness, double jitterX, double jitterY) {
/*  24 */     this.density = density;
/*  25 */     this.thickness = thickness;
/*  26 */     this.thicknessSquared = thickness * thickness;
/*  27 */     this.jitterX = jitterX;
/*  28 */     this.jitterY = jitterY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double get(int seed, int offsetSeed, double x, double y) {
/*  37 */     double thickness = this.thickness;
/*  38 */     double lowest = this.thicknessSquared;
/*     */ 
/*     */     
/*  41 */     int _x = GeneralNoise.fastFloor(x);
/*  42 */     int _y = GeneralNoise.fastFloor(y);
/*  43 */     double rx = x - _x;
/*  44 */     double ry = y - _y;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  50 */     for (int c = 0; c < 8; c++) {
/*     */       int cx; int cy; int xr; int yr; int cellHash; DoubleArray.Double2 cell; double cellX; double cellY; double centerX; double centerY;
/*     */       double qX;
/*     */       double qY;
/*  54 */       switch (c) {
/*     */         case 0:
/*  56 */           if (rx >= thickness || ry >= thickness)
/*     */             break; 
/*     */         case 1:
/*  59 */           if (rx >= thickness)
/*     */             break; 
/*     */         case 2:
/*  62 */           if (rx >= thickness || ry < 1.0D - thickness)
/*     */             break; 
/*     */         case 3:
/*  65 */           if (ry >= thickness) {
/*     */             break;
/*     */           }
/*     */         case 5:
/*  69 */           if (ry < 1.0D - thickness)
/*     */             break; 
/*     */         case 6:
/*  72 */           if (rx < 1.0D - thickness || ry >= thickness)
/*     */             break; 
/*     */         case 7:
/*  75 */           if (rx < 1.0D - thickness) {
/*     */             break;
/*     */           }
/*     */         default:
/*  79 */           cx = c / 3;
/*  80 */           cy = c % 3;
/*     */ 
/*     */           
/*  83 */           xr = _x + cx - 1;
/*  84 */           yr = _y + cy - 1;
/*     */ 
/*     */           
/*  87 */           cellHash = GridCellDistanceFunction.getHash(offsetSeed, xr, yr);
/*  88 */           if (!this.density.eval(cellHash)) {
/*     */             break;
/*     */           }
/*  91 */           cell = CellularNoise.CELL_2D[cellHash & 0xFF];
/*  92 */           cellX = cell.x * this.jitterX;
/*  93 */           cellY = cell.y * this.jitterY;
/*     */ 
/*     */           
/*  96 */           centerX = xr + cellX;
/*  97 */           centerY = yr + cellY;
/*     */ 
/*     */           
/* 100 */           qX = x - centerX;
/* 101 */           qY = y - centerY;
/*     */ 
/*     */           
/* 104 */           for (Vector2i v : ADJACENT_CELLS) {
/*     */ 
/*     */             
/* 107 */             int xi = xr + v.x;
/* 108 */             int yi = yr + v.y;
/*     */             
/* 110 */             int vecHash = GridCellDistanceFunction.getHash(offsetSeed, xi, yi);
/* 111 */             if (this.density.eval(vecHash)) {
/*     */               
/* 113 */               DoubleArray.Double2 vec = CellularNoise.CELL_2D[vecHash & 0xFF];
/* 114 */               double vecX = vec.x * this.jitterX;
/* 115 */               double vecY = vec.y * this.jitterY;
/*     */               
/* 117 */               double vx = v.x + vecX - cellX;
/* 118 */               double vy = v.y + vecY - cellY;
/*     */ 
/*     */               
/* 121 */               double t = (qX * vx + qY * vy) / (vx * vx + vy * vy);
/* 122 */               if (t < 0.0D) {
/* 123 */                 t = 0.0D;
/* 124 */               } else if (t > 1.0D) {
/* 125 */                 t = 1.0D;
/*     */               } 
/*     */ 
/*     */               
/* 129 */               double lx = centerX + vx * t;
/* 130 */               double ly = centerY + vy * t;
/*     */ 
/*     */               
/* 133 */               double dx = x - lx;
/* 134 */               double dy = y - ly;
/* 135 */               double distance = dx * dx + dy * dy;
/* 136 */               if (distance < lowest)
/* 137 */                 lowest = distance; 
/*     */             } 
/*     */           } 
/*     */           break;
/*     */       } 
/*     */     } 
/* 143 */     if (lowest != this.thicknessSquared) {
/* 144 */       double distance = Math.sqrt(lowest);
/* 145 */       double d = distance / thickness;
/* 146 */       return d * 2.0D - 1.0D;
/*     */     } 
/* 148 */     return 1.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double get(int seed, int offsetSeed, double x, double y, double z) {
/* 154 */     throw new UnsupportedOperationException("3d not supported");
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\logic\MeshNoise.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */