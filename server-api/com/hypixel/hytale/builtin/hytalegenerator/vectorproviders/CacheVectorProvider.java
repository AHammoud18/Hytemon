/*    */ package com.hypixel.hytale.builtin.hytalegenerator.vectorproviders;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.threadindexer.WorkerIndexer;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class CacheVectorProvider extends VectorProvider {
/*    */   @Nonnull
/*    */   private final VectorProvider vectorProvider;
/*    */   @Nonnull
/*    */   private final WorkerIndexer.Data<Cache> threadData;
/*    */   
/*    */   public CacheVectorProvider(@Nonnull VectorProvider vectorProvider, int threadCount) {
/* 15 */     if (threadCount <= 0) {
/* 16 */       throw new IllegalArgumentException("threadCount must be greater than 0");
/*    */     }
/* 18 */     this.vectorProvider = vectorProvider;
/* 19 */     this.threadData = new WorkerIndexer.Data(threadCount, Cache::new);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public Vector3d process(@Nonnull VectorProvider.Context context) {
/* 24 */     Cache cache = (Cache)this.threadData.get(context.workerId);
/*    */     
/* 26 */     if (cache.position != null && cache.position.equals(context.position)) {
/* 27 */       return cache.value;
/*    */     }
/*    */     
/* 30 */     if (cache.position == null) {
/* 31 */       cache.position = new Vector3d();
/*    */     }
/*    */     
/* 34 */     cache.position.assign(context.position);
/* 35 */     cache.value = this.vectorProvider.process(context);
/* 36 */     return cache.value;
/*    */   }
/*    */   
/*    */   public static class Cache {
/*    */     Vector3d position;
/*    */     Vector3d value;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\vectorproviders\CacheVectorProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */