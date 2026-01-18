/*    */ package com.hypixel.hytale.server.worldgen.loader.biome;
/*    */ 
/*    */ import com.hypixel.hytale.procedurallib.logic.cell.PointDistanceFunction;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class LoadedPointGeneratorDistanceFunction
/*    */   implements PointDistanceFunction
/*    */ {
/*    */   private final BiomePatternGeneratorJsonLoader.ISizeModifierProvider sizeModifierProvider;
/*    */   private final PointDistanceFunction distanceFunction;
/*    */   
/*    */   public LoadedPointGeneratorDistanceFunction(BiomePatternGeneratorJsonLoader.ISizeModifierProvider sizeModifierProvider, PointDistanceFunction distanceFunction) {
/* 77 */     this.sizeModifierProvider = sizeModifierProvider;
/* 78 */     this.distanceFunction = distanceFunction;
/*    */   }
/*    */ 
/*    */   
/*    */   public double distance2D(double deltaX, double deltaY) {
/* 83 */     return this.distanceFunction.distance2D(deltaX, deltaY);
/*    */   }
/*    */ 
/*    */   
/*    */   public double distance3D(double deltaX, double deltaY, double deltaZ) {
/* 88 */     return this.distanceFunction.distance3D(deltaX, deltaY, deltaZ);
/*    */   }
/*    */ 
/*    */   
/*    */   public double distance2D(int seed, int cellX, int cellY, double cellCentreX, double cellCentreY, double deltaX, double deltaY) {
/* 93 */     return this.distanceFunction.distance2D(seed, cellX, cellY, cellCentreX, cellCentreY, deltaX, deltaY) * this.sizeModifierProvider.get(seed, cellX, cellY);
/*    */   }
/*    */ 
/*    */   
/*    */   public double distance3D(int seed, int cellX, int cellY, int cellZ, double cellCentreX, double cellCentreY, double cellCentreZ, double deltaX, double deltaY, double deltaZ) {
/* 98 */     return this.distanceFunction.distance3D(seed, cellX, cellY, cellZ, cellCentreX, cellCentreY, cellCentreZ, deltaX, deltaY, deltaZ);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\biome\BiomePatternGeneratorJsonLoader$LoadedPointGeneratorDistanceFunction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */