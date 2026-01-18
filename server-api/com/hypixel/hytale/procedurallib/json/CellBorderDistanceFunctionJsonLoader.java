/*    */ package com.hypixel.hytale.procedurallib.json;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.procedurallib.condition.IDoubleCondition;
/*    */ import com.hypixel.hytale.procedurallib.logic.cell.BorderDistanceFunction;
/*    */ import com.hypixel.hytale.procedurallib.logic.cell.CellDistanceFunction;
/*    */ import com.hypixel.hytale.procedurallib.logic.cell.MeasurementMode;
/*    */ import com.hypixel.hytale.procedurallib.logic.cell.evaluator.PointEvaluator;
/*    */ import java.nio.file.Path;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class CellBorderDistanceFunctionJsonLoader<K extends SeedResource>
/*    */   extends JsonLoader<K, BorderDistanceFunction> {
/*    */   protected final CellDistanceFunction distanceFunction;
/*    */   
/*    */   public CellBorderDistanceFunctionJsonLoader(SeedString<K> seed, Path dataFolder, JsonElement json, CellDistanceFunction distanceFunction) {
/* 18 */     super(seed, dataFolder, json);
/* 19 */     this.distanceFunction = distanceFunction;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public BorderDistanceFunction load() {
/* 25 */     return new BorderDistanceFunction(this.distanceFunction, 
/*    */         
/* 27 */         loadPointEvaluator(), 
/* 28 */         loadDensity());
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   protected PointEvaluator loadPointEvaluator() {
/* 34 */     return (new PointEvaluatorJsonLoader<>(this.seed, this.dataFolder, this.json, MeasurementMode.BORDER_DISTANCE, null))
/* 35 */       .load();
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   protected IDoubleCondition loadDensity() {
/* 40 */     return (new PointEvaluatorJsonLoader<>(this.seed, this.dataFolder, this.json))
/* 41 */       .loadDensity();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\json\CellBorderDistanceFunctionJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */