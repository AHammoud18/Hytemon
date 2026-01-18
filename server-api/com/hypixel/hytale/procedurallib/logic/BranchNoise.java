/*     */ package com.hypixel.hytale.procedurallib.logic;
/*     */ 
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.procedurallib.NoiseFunction;
/*     */ import com.hypixel.hytale.procedurallib.condition.IIntCondition;
/*     */ import com.hypixel.hytale.procedurallib.logic.cell.CellDistanceFunction;
/*     */ import com.hypixel.hytale.procedurallib.logic.cell.evaluator.PointEvaluator;
/*     */ import com.hypixel.hytale.procedurallib.property.NoiseFormulaProperty;
/*     */ import com.hypixel.hytale.procedurallib.supplier.IDoubleRange;
/*     */ import javax.annotation.Nonnull;
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
/*     */ 
/*     */ public class BranchNoise
/*     */   implements NoiseFunction
/*     */ {
/*     */   protected final CellDistanceFunction parentFunction;
/*     */   protected final PointEvaluator parentEvaluator;
/*     */   protected final double parentValue;
/*     */   protected final double emptyValue;
/*     */   protected final IDoubleRange parentFade;
/*     */   protected final IIntCondition parentDensity;
/*     */   protected final DistanceNoise.Distance2Function distance2Function;
/*     */   protected final NoiseFormulaProperty.NoiseFormula.Formula noiseFormula;
/*     */   protected final CellDistanceFunction lineFunction;
/*     */   protected final PointEvaluator lineEvaluator;
/*     */   protected final double lineScale;
/*     */   protected final IDoubleRange lineThickness;
/*     */   
/*     */   public BranchNoise(CellDistanceFunction parentFunction, PointEvaluator parentEvaluator, double parentValue, IDoubleRange parentFade, IIntCondition parentDensity, DistanceNoise.Distance2Function distance2Function, NoiseFormulaProperty.NoiseFormula.Formula noiseFormula, CellDistanceFunction lineFunction, PointEvaluator lineEvaluator, double lineScale, IDoubleRange lineThickness) {
/*  40 */     this.parentFunction = parentFunction;
/*  41 */     this.parentEvaluator = parentEvaluator;
/*  42 */     this.parentValue = parentValue;
/*  43 */     this.emptyValue = toOutputRange(parentValue);
/*  44 */     this.parentFade = parentFade;
/*  45 */     this.parentDensity = parentDensity;
/*  46 */     this.distance2Function = distance2Function;
/*  47 */     this.noiseFormula = noiseFormula;
/*  48 */     this.lineFunction = lineFunction;
/*  49 */     this.lineEvaluator = lineEvaluator;
/*  50 */     this.lineScale = lineScale;
/*  51 */     this.lineThickness = lineThickness;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double get(int seed, int offsetSeed, double x, double y) {
/*  57 */     ResultBuffer.ResultBuffer2d parent = getParentNoise(offsetSeed, x, y);
/*  58 */     if (!this.parentDensity.eval(parent.hash)) return this.emptyValue;
/*     */     
/*  60 */     double parentDistance = this.noiseFormula.eval(this.distance2Function.eval(parent.distance, parent.distance2));
/*     */ 
/*     */     
/*  63 */     double lineValue = getLineValue(offsetSeed, x, y, parent.hash, parent.x, parent.y, parentDistance, parent);
/*     */ 
/*     */     
/*  66 */     double parentFade = this.parentFade.getValue(parentDistance);
/*     */ 
/*     */     
/*  69 */     double noiseValue = MathUtil.lerp(this.parentValue, lineValue, parentFade);
/*     */     
/*  71 */     return toOutputRange(noiseValue);
/*     */   }
/*     */ 
/*     */   
/*     */   public double get(int seed, int offsetSeed, double x, double y, double z) {
/*  76 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected ResultBuffer.ResultBuffer2d localBuffer2d() {
/*  81 */     return ResultBuffer.buffer2d;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected ResultBuffer.ResultBuffer2d getParentNoise(int seed, double x, double y) {
/*  86 */     ResultBuffer.ResultBuffer2d buffer = localBuffer2d();
/*  87 */     buffer.distance = Double.POSITIVE_INFINITY;
/*  88 */     buffer.distance2 = Double.POSITIVE_INFINITY;
/*     */     
/*  90 */     x = this.parentFunction.scale(x);
/*  91 */     y = this.parentFunction.scale(y);
/*     */     
/*  93 */     int cellX = this.parentFunction.getCellX(x, y);
/*  94 */     int cellY = this.parentFunction.getCellY(x, y);
/*  95 */     this.parentFunction.transition2D(seed, x, y, cellX, cellY, buffer, this.parentEvaluator);
/*     */     
/*  97 */     return buffer;
/*     */   }
/*     */ 
/*     */   
/*     */   protected double getLineValue(int seed, double x, double y, int parentHash, double parentX, double parentY, double parentDistance, @Nonnull ResultBuffer.ResultBuffer2d buffer) {
/* 102 */     double thickness = this.lineThickness.getValue(parentDistance);
/* 103 */     if (thickness == 0.0D) return 1.0D;
/*     */     
/* 105 */     buffer.distance = Double.POSITIVE_INFINITY;
/*     */ 
/*     */ 
/*     */     
/* 109 */     buffer.x2 = parentX;
/* 110 */     buffer.y2 = parentY;
/* 111 */     buffer.ix2 = parentHash;
/* 112 */     buffer.distance2 = thickness;
/*     */     
/* 114 */     x *= this.lineScale;
/* 115 */     y *= this.lineScale;
/*     */     
/* 117 */     x = this.lineFunction.scale(x);
/* 118 */     y = this.lineFunction.scale(y);
/*     */     
/* 120 */     int cellX = this.lineFunction.getCellX(x, y);
/* 121 */     int cellY = this.lineFunction.getCellY(x, y);
/*     */     
/* 123 */     this.lineFunction.nearest2D(seed, x, y, cellX, cellY, buffer, this.lineEvaluator);
/*     */     
/* 125 */     double distance = buffer.distance;
/* 126 */     if (distance >= thickness * thickness) return 1.0D;
/*     */     
/* 128 */     return Math.sqrt(distance) / thickness;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 134 */     return "BranchNoise{parentFunction=" + String.valueOf(this.parentFunction) + ", parentEvaluator=" + String.valueOf(this.parentEvaluator) + ", parentValue=" + this.parentValue + ", parentFade=" + String.valueOf(this.parentFade) + ", distance2Function=" + String.valueOf(this.distance2Function) + ", noiseFormula=" + String.valueOf(this.noiseFormula) + ", lineFunction=" + String.valueOf(this.lineFunction) + ", lineEvaluator=" + String.valueOf(this.lineEvaluator) + ", lineScale=" + this.lineScale + ", lineThickness=" + String.valueOf(this.lineThickness) + "}";
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static double toOutputRange(double value) {
/* 153 */     return 2.0D * value - 1.0D;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\logic\BranchNoise.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */