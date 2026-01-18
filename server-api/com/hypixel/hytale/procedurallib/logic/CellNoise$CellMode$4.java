/*     */ package com.hypixel.hytale.procedurallib.logic;
/*     */ 
/*     */ import com.hypixel.hytale.math.util.HashUtil;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.math.util.TrigMathUtil;
/*     */ import com.hypixel.hytale.procedurallib.logic.cell.CellDistanceFunction;
/*     */ import com.hypixel.hytale.procedurallib.property.NoiseProperty;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class null
/*     */   implements CellNoise.CellFunction
/*     */ {
/*     */   public double eval(int seed, int offsetSeed, double x, double y, @Nonnull ResultBuffer.ResultBuffer2d buffer, CellDistanceFunction cellFunction, NoiseProperty noiseLookup) {
/* 160 */     float angle = (float)getAngleNoise(seed, offsetSeed, buffer, noiseLookup) * 6.2831855F;
/*     */     
/* 162 */     float dx = TrigMathUtil.sin(angle);
/* 163 */     float dy = TrigMathUtil.cos(angle);
/*     */ 
/*     */     
/* 166 */     double ax = buffer.x;
/* 167 */     double ay = buffer.y;
/* 168 */     double bx = ax + dx;
/* 169 */     double by = ay + dy;
/*     */     
/* 171 */     double distance2 = MathUtil.distanceToInfLineSq(x, y, ax, ay, bx, by);
/* 172 */     double distance = MathUtil.clamp(Math.sqrt(distance2), 0.0D, 1.0D);
/*     */ 
/*     */     
/* 175 */     int side = MathUtil.sideOfLine(x, y, ax, ay, bx, by);
/*     */ 
/*     */ 
/*     */     
/* 179 */     return 0.5D + side * distance * 0.5D;
/*     */   }
/*     */ 
/*     */   
/*     */   public double eval(int seed, int offsetSeed, double x, double y, double z, ResultBuffer.ResultBuffer3d buffer, CellDistanceFunction cellFunction, NoiseProperty noiseLookup) {
/* 184 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 190 */     return "DirectionCellFunction{}";
/*     */   }
/*     */   
/*     */   private double getAngleNoise(int seed, int offsetSeed, @Nonnull ResultBuffer.ResultBuffer2d buffer, @Nullable NoiseProperty noiseProperty) {
/* 194 */     if (noiseProperty != null) return noiseProperty.get(seed, buffer.x, buffer.y);
/*     */     
/* 196 */     return HashUtil.random(offsetSeed, buffer.ix, buffer.iy);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\logic\CellNoise$CellMode$4.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */