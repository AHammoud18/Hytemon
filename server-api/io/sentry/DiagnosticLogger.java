/*    */ package io.sentry;
/*    */ 
/*    */ import io.sentry.util.Objects;
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ import org.jetbrains.annotations.TestOnly;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Internal
/*    */ public final class DiagnosticLogger
/*    */   implements ILogger
/*    */ {
/*    */   @NotNull
/*    */   private final SentryOptions options;
/*    */   @Nullable
/*    */   private final ILogger logger;
/*    */   
/*    */   public DiagnosticLogger(@NotNull SentryOptions options, @Nullable ILogger logger) {
/* 22 */     this.options = (SentryOptions)Objects.requireNonNull(options, "SentryOptions is required.");
/* 23 */     this.logger = logger;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isEnabled(@Nullable SentryLevel level) {
/* 34 */     SentryLevel diagLevel = this.options.getDiagnosticLevel();
/* 35 */     if (level == null) {
/* 36 */       return false;
/*    */     }
/* 38 */     return (this.options.isDebug() && level.ordinal() >= diagLevel.ordinal());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void log(@NotNull SentryLevel level, @NotNull String message, @Nullable Object... args) {
/* 53 */     if (this.logger != null && isEnabled(level)) {
/* 54 */       this.logger.log(level, message, args);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void log(@NotNull SentryLevel level, @NotNull String message, @Nullable Throwable throwable) {
/* 70 */     if (this.logger != null && isEnabled(level)) {
/* 71 */       this.logger.log(level, message, throwable);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void log(@NotNull SentryLevel level, @Nullable Throwable throwable, @NotNull String message, @Nullable Object... args) {
/* 89 */     if (this.logger != null && isEnabled(level))
/* 90 */       this.logger.log(level, throwable, message, args); 
/*    */   }
/*    */   
/*    */   @TestOnly
/*    */   @Nullable
/*    */   public ILogger getLogger() {
/* 96 */     return this.logger;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\DiagnosticLogger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */