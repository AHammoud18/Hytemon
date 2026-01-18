/*    */ package com.hypixel.hytale.builtin.hytalegenerator.density.nodes;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.threadindexer.WorkerIndexer;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class CacheDensity extends Density {
/*    */   private final WorkerIndexer.Data<Cache> threadData;
/*    */   @Nonnull
/*    */   private Density input;
/*    */   
/*    */   public CacheDensity(@Nonnull Density input, int threadCount) {
/* 15 */     this.input = input;
/* 16 */     this.threadData = new WorkerIndexer.Data(threadCount, Cache::new);
/*    */   }
/*    */ 
/*    */   
/*    */   public double process(@Nonnull Density.Context context) {
/* 21 */     Cache cache = (Cache)this.threadData.get(context.workerId);
/*    */     
/* 23 */     if (cache.position != null && cache.position.x == context.position.x && cache.position.y == context.position.y && cache.position.z == context.position.z)
/*    */     {
/*    */ 
/*    */ 
/*    */       
/* 28 */       return cache.value;
/*    */     }
/*    */     
/* 31 */     if (cache.position == null) {
/* 32 */       cache.position = new Vector3d();
/*    */     }
/*    */     
/* 35 */     cache.position.assign(context.position);
/* 36 */     cache.value = this.input.process(context);
/* 37 */     return cache.value;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setInputs(@Nonnull Density[] inputs) {
/* 42 */     assert inputs.length != 0;
/* 43 */     assert inputs[0] != null;
/* 44 */     this.input = inputs[0];
/*    */   }
/*    */   
/*    */   private static class Cache {
/*    */     Vector3d position;
/*    */     double value;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\density\nodes\CacheDensity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */