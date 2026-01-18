/*    */ package io.sentry;
/*    */ 
/*    */ import java.util.List;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ public final class NoOpTransactionProfiler
/*    */   implements ITransactionProfiler {
/*  9 */   private static final NoOpTransactionProfiler instance = new NoOpTransactionProfiler();
/*    */ 
/*    */ 
/*    */   
/*    */   public static NoOpTransactionProfiler getInstance() {
/* 14 */     return instance;
/*    */   }
/*    */ 
/*    */   
/*    */   public void start() {}
/*    */ 
/*    */   
/*    */   public boolean isRunning() {
/* 22 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void bindTransaction(@NotNull ITransaction transaction) {}
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public ProfilingTraceData onTransactionFinish(@NotNull ITransaction transaction, @Nullable List<PerformanceCollectionData> performanceCollectionData, @NotNull SentryOptions options) {
/* 33 */     return null;
/*    */   }
/*    */   
/*    */   public void close() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\NoOpTransactionProfiler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */