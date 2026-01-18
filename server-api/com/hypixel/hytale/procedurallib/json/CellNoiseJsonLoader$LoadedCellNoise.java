/*    */ package com.hypixel.hytale.procedurallib.json;
/*    */ 
/*    */ import com.hypixel.hytale.procedurallib.logic.CellNoise;
/*    */ import com.hypixel.hytale.procedurallib.logic.ResultBuffer;
/*    */ import com.hypixel.hytale.procedurallib.logic.cell.CellDistanceFunction;
/*    */ import com.hypixel.hytale.procedurallib.logic.cell.evaluator.PointEvaluator;
/*    */ import com.hypixel.hytale.procedurallib.property.NoiseProperty;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class LoadedCellNoise
/*    */   extends CellNoise
/*    */ {
/*    */   private final SeedResource seedResource;
/*    */   
/*    */   public LoadedCellNoise(CellDistanceFunction cellDistanceFunction, PointEvaluator pointEvaluator, CellNoise.CellFunction cellFunction, @Nullable NoiseProperty noiseLookup, SeedResource seedResource) {
/* 76 */     super(cellDistanceFunction, pointEvaluator, cellFunction, noiseLookup);
/* 77 */     this.seedResource = seedResource;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   protected ResultBuffer.ResultBuffer2d localBuffer2d() {
/* 83 */     return this.seedResource.localBuffer2d();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   protected ResultBuffer.ResultBuffer3d localBuffer3d() {
/* 89 */     return this.seedResource.localBuffer3d();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\json\CellNoiseJsonLoader$LoadedCellNoise.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */