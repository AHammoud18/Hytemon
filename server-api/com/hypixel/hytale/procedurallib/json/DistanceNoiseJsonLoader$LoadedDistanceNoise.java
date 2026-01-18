/*    */ package com.hypixel.hytale.procedurallib.json;
/*    */ 
/*    */ import com.hypixel.hytale.procedurallib.logic.DistanceNoise;
/*    */ import com.hypixel.hytale.procedurallib.logic.ResultBuffer;
/*    */ import com.hypixel.hytale.procedurallib.logic.cell.CellDistanceFunction;
/*    */ import com.hypixel.hytale.procedurallib.logic.cell.evaluator.PointEvaluator;
/*    */ import javax.annotation.Nonnull;
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
/*    */ class LoadedDistanceNoise
/*    */   extends DistanceNoise
/*    */ {
/*    */   private final SeedResource seedResource;
/*    */   
/*    */   public LoadedDistanceNoise(CellDistanceFunction cellDistanceFunction, PointEvaluator pointEvaluator, DistanceNoise.Distance2Function distance2Function, SeedResource seedResource) {
/* 71 */     super(cellDistanceFunction, pointEvaluator, distance2Function);
/* 72 */     this.seedResource = seedResource;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   protected ResultBuffer.ResultBuffer2d localBuffer2d() {
/* 78 */     return this.seedResource.localBuffer2d();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   protected ResultBuffer.ResultBuffer3d localBuffer3d() {
/* 84 */     return this.seedResource.localBuffer3d();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\json\DistanceNoiseJsonLoader$LoadedDistanceNoise.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */