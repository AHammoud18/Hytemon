/*     */ package com.hypixel.hytale.procedurallib.json;
/*     */ 
/*     */ import com.hypixel.hytale.procedurallib.condition.IIntCondition;
/*     */ import com.hypixel.hytale.procedurallib.logic.BranchNoise;
/*     */ import com.hypixel.hytale.procedurallib.logic.DistanceNoise;
/*     */ import com.hypixel.hytale.procedurallib.logic.ResultBuffer;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LoadedBranchNoise
/*     */   extends BranchNoise
/*     */ {
/*     */   protected final SeedResource seedResource;
/*     */   
/*     */   public LoadedBranchNoise(CellDistanceFunction parentFunction, PointEvaluator parentEvaluator, double parentValue, IDoubleRange parentFade, IIntCondition parentDensity, DistanceNoise.Distance2Function distance2Function, NoiseFormulaProperty.NoiseFormula.Formula noiseFormula, CellDistanceFunction lineFunction, PointEvaluator lineEvaluator, double lineScale, IDoubleRange lineThickness, SeedResource seedResource) {
/* 171 */     super(parentFunction, parentEvaluator, parentValue, parentFade, parentDensity, distance2Function, noiseFormula, lineFunction, lineEvaluator, lineScale, lineThickness);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 182 */     this.seedResource = seedResource;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected ResultBuffer.ResultBuffer2d localBuffer2d() {
/* 188 */     return this.seedResource.localBuffer2d();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 194 */     return "LoadedBranchNoise{seedResource=" + String.valueOf(this.seedResource) + ", parentFunction=" + String.valueOf(this.parentFunction) + ", parentEvaluator=" + String.valueOf(this.parentEvaluator) + ", parentValue=" + this.parentValue + ", parentFade=" + String.valueOf(this.parentFade) + ", distance2Function=" + String.valueOf(this.distance2Function) + ", noiseFormula=" + String.valueOf(this.noiseFormula) + ", lineFunction=" + String.valueOf(this.lineFunction) + ", lineEvaluator=" + String.valueOf(this.lineEvaluator) + ", lineScale=" + this.lineScale + ", lineThickness=" + String.valueOf(this.lineThickness) + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\json\BranchNoiseJsonLoader$LoadedBranchNoise.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */