/*    */ package io.sentry.logger;
/*    */ 
/*    */ import io.sentry.ISentryClient;
/*    */ import io.sentry.SentryClient;
/*    */ import io.sentry.SentryOptions;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ public final class DefaultLoggerBatchProcessorFactory implements ILoggerBatchProcessorFactory {
/*    */   @NotNull
/*    */   public ILoggerBatchProcessor create(@NotNull SentryOptions options, @NotNull SentryClient client) {
/* 11 */     return new LoggerBatchProcessor(options, (ISentryClient)client);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\logger\DefaultLoggerBatchProcessorFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */