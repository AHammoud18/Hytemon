/*    */ package io.sentry.logger;
/*    */ 
/*    */ import io.sentry.SentryLogEvent;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ public final class NoOpLoggerBatchProcessor
/*    */   implements ILoggerBatchProcessor {
/*  8 */   private static final NoOpLoggerBatchProcessor instance = new NoOpLoggerBatchProcessor();
/*    */ 
/*    */ 
/*    */   
/*    */   public static NoOpLoggerBatchProcessor getInstance() {
/* 13 */     return instance;
/*    */   }
/*    */   
/*    */   public void add(@NotNull SentryLogEvent event) {}
/*    */   
/*    */   public void close(boolean isRestarting) {}
/*    */   
/*    */   public void flush(long timeoutMillis) {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\logger\NoOpLoggerBatchProcessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */