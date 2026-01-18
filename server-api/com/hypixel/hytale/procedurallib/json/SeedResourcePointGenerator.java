/*    */ package com.hypixel.hytale.procedurallib.json;
/*    */ 
/*    */ import com.hypixel.hytale.procedurallib.logic.ResultBuffer;
/*    */ import com.hypixel.hytale.procedurallib.logic.cell.CellDistanceFunction;
/*    */ import com.hypixel.hytale.procedurallib.logic.cell.evaluator.PointEvaluator;
/*    */ import com.hypixel.hytale.procedurallib.logic.point.PointGenerator;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class SeedResourcePointGenerator
/*    */   extends PointGenerator {
/*    */   private final SeedResource seedResource;
/*    */   
/*    */   public SeedResourcePointGenerator(int seedOffset, CellDistanceFunction cellDistanceFunction, PointEvaluator pointEvaluator, SeedResource seedResource) {
/* 14 */     super(seedOffset, cellDistanceFunction, pointEvaluator);
/* 15 */     this.seedResource = seedResource;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   protected ResultBuffer.Bounds2d localBounds2d() {
/* 21 */     return this.seedResource.localBounds2d();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   protected ResultBuffer.ResultBuffer2d localBuffer2d() {
/* 27 */     return this.seedResource.localBuffer2d();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   protected ResultBuffer.ResultBuffer3d localBuffer3d() {
/* 33 */     return this.seedResource.localBuffer3d();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\json\SeedResourcePointGenerator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */