/*     */ package com.hypixel.hytale.server.worldgen.climate;
/*     */ 
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.procedurallib.logic.ResultBuffer;
/*     */ import com.hypixel.hytale.procedurallib.logic.cell.CellDistanceFunction;
/*     */ import com.hypixel.hytale.procedurallib.logic.cell.evaluator.PointEvaluator;
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
/*     */ public class Grid
/*     */ {
/*     */   public final int seedOffset;
/*     */   public final double scale;
/*     */   @Nonnull
/*     */   public final PointEvaluator evaluator;
/*     */   @Nonnull
/*     */   public final CellDistanceFunction grid;
/*     */   public final transient double invScale;
/*     */   
/*     */   public Grid(int seedOffset, double scale, @Nonnull CellDistanceFunction grid, @Nonnull PointEvaluator evaluator) {
/*  90 */     this.seedOffset = seedOffset;
/*  91 */     this.scale = scale;
/*  92 */     this.evaluator = evaluator;
/*  93 */     this.grid = grid;
/*  94 */     this.invScale = 1.0D / scale;
/*     */   }
/*     */   
/*     */   public void eval(int seed, double x, double y, ResultBuffer.ResultBuffer2d buffer) {
/*  98 */     x *= this.scale;
/*  99 */     y *= this.scale;
/* 100 */     buffer.distance = Double.MAX_VALUE;
/* 101 */     buffer.distance2 = Double.MAX_VALUE;
/* 102 */     this.grid.nearest2D(seed + this.seedOffset, x, y, MathUtil.floor(x), MathUtil.floor(y), buffer, this.evaluator);
/* 103 */     buffer.x *= this.invScale;
/* 104 */     buffer.y *= this.invScale;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\climate\ClimateNoise$Grid.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */