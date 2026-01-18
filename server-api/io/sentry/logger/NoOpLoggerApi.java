/*    */ package io.sentry.logger;
/*    */ 
/*    */ import io.sentry.SentryDate;
/*    */ import io.sentry.SentryLogLevel;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ public final class NoOpLoggerApi
/*    */   implements ILoggerApi {
/* 10 */   private static final NoOpLoggerApi instance = new NoOpLoggerApi();
/*    */ 
/*    */ 
/*    */   
/*    */   public static NoOpLoggerApi getInstance() {
/* 15 */     return instance;
/*    */   }
/*    */   
/*    */   public void trace(@Nullable String message, @Nullable Object... args) {}
/*    */   
/*    */   public void debug(@Nullable String message, @Nullable Object... args) {}
/*    */   
/*    */   public void info(@Nullable String message, @Nullable Object... args) {}
/*    */   
/*    */   public void warn(@Nullable String message, @Nullable Object... args) {}
/*    */   
/*    */   public void error(@Nullable String message, @Nullable Object... args) {}
/*    */   
/*    */   public void fatal(@Nullable String message, @Nullable Object... args) {}
/*    */   
/*    */   public void log(@NotNull SentryLogLevel level, @Nullable String message, @Nullable Object... args) {}
/*    */   
/*    */   public void log(@NotNull SentryLogLevel level, @Nullable SentryDate timestamp, @Nullable String message, @Nullable Object... args) {}
/*    */   
/*    */   public void log(@NotNull SentryLogLevel level, @NotNull SentryLogParameters params, @Nullable String message, @Nullable Object... args) {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\logger\NoOpLoggerApi.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */