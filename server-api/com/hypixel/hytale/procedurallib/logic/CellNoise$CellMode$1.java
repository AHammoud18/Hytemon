/*     */ package com.hypixel.hytale.procedurallib.logic;
/*     */ 
/*     */ import com.hypixel.hytale.math.util.HashUtil;
/*     */ import com.hypixel.hytale.procedurallib.logic.cell.CellDistanceFunction;
/*     */ import com.hypixel.hytale.procedurallib.property.NoiseProperty;
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
/* 106 */     return HashUtil.random(offsetSeed, buffer.ix, buffer.iy);
/*     */   }
/*     */ 
/*     */   
/*     */   public double eval(int seed, int offsetSeed, double x, double y, double z, @Nonnull ResultBuffer.ResultBuffer3d buffer, CellDistanceFunction cellFunction, NoiseProperty noiseLookup) {
/* 111 */     return HashUtil.random(offsetSeed, buffer.ix, buffer.iy, buffer.iz);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 117 */     return "CellValueCellFunction{}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\logic\CellNoise$CellMode$1.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */