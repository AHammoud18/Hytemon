/*    */ package io.sentry;
/*    */ 
/*    */ import java.util.List;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ public final class NoOpCompositePerformanceCollector
/*    */   implements CompositePerformanceCollector {
/*  9 */   private static final NoOpCompositePerformanceCollector instance = new NoOpCompositePerformanceCollector();
/*    */ 
/*    */   
/*    */   public static NoOpCompositePerformanceCollector getInstance() {
/* 13 */     return instance;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void start(@NotNull ITransaction transaction) {}
/*    */ 
/*    */   
/*    */   public void start(@NotNull String id) {}
/*    */ 
/*    */   
/*    */   public void onSpanStarted(@NotNull ISpan span) {}
/*    */ 
/*    */   
/*    */   public void onSpanFinished(@NotNull ISpan span) {}
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public List<PerformanceCollectionData> stop(@NotNull ITransaction transaction) {
/* 32 */     return null;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public List<PerformanceCollectionData> stop(@NotNull String id) {
/* 37 */     return null;
/*    */   }
/*    */   
/*    */   public void close() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\NoOpCompositePerformanceCollector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */