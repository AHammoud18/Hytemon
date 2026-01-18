/*    */ package io.sentry;
/*    */ 
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ public final class NoOpLogger
/*    */   implements ILogger
/*    */ {
/*  9 */   private static final NoOpLogger instance = new NoOpLogger();
/*    */   
/*    */   public static NoOpLogger getInstance() {
/* 12 */     return instance;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void log(@NotNull SentryLevel level, @NotNull String message, @Nullable Object... args) {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void log(@NotNull SentryLevel level, @NotNull String message, @Nullable Throwable throwable) {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void log(@NotNull SentryLevel level, @Nullable Throwable throwable, @NotNull String message, @Nullable Object... args) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isEnabled(@Nullable SentryLevel level) {
/* 33 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\NoOpLogger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */