/*     */ package com.hypixel.hytale.procedurallib.logic;
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */   public double eval(int seed, int offsetSeed, double x, double y, @Nonnull ResultBuffer.ResultBuffer2d buffer, @Nonnull CellDistanceFunction cellFunction, @Nonnull NoiseProperty noiseLookup) {
/* 122 */     double px = cellFunction.invScale(buffer.x);
/* 123 */     double py = cellFunction.invScale(buffer.y);
/* 124 */     return noiseLookup.get(seed, px, py);
/*     */   }
/*     */ 
/*     */   
/*     */   public double eval(int seed, int offsetSeed, double x, double y, double z, @Nonnull ResultBuffer.ResultBuffer3d buffer, @Nonnull CellDistanceFunction cellFunction, @Nonnull NoiseProperty noiseLookup) {
/* 129 */     double px = cellFunction.invScale(buffer.x);
/* 130 */     double py = cellFunction.invScale(buffer.y);
/* 131 */     double pz = cellFunction.invScale(buffer.z);
/* 132 */     return noiseLookup.get(seed, px, py, pz);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 138 */     return "NoiseLookupCellFunction{}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\logic\CellNoise$CellMode$2.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */