/*    */ package com.hypixel.hytale.metrics;
/*    */ 
/*    */ import java.util.function.Function;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface MetricProvider
/*    */ {
/*    */   @Nullable
/*    */   MetricResults toMetricResults();
/*    */   
/*    */   @Nonnull
/*    */   static <T, R> Function<T, MetricProvider> maybe(@Nonnull Function<T, R> func) {
/* 17 */     return t -> {
/*    */         R r = func.apply(t);
/*    */         return (r instanceof MetricProvider) ? (MetricProvider)r : null;
/*    */       };
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\metrics\MetricProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */