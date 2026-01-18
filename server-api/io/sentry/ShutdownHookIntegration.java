/*    */ package io.sentry;
/*    */ 
/*    */ import io.sentry.util.IntegrationUtils;
/*    */ import io.sentry.util.Objects;
/*    */ import java.io.Closeable;
/*    */ import java.io.IOException;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ import org.jetbrains.annotations.TestOnly;
/*    */ import org.jetbrains.annotations.VisibleForTesting;
/*    */ 
/*    */ public final class ShutdownHookIntegration
/*    */   implements Integration, Closeable
/*    */ {
/*    */   @NotNull
/*    */   private final Runtime runtime;
/*    */   @Nullable
/*    */   private Thread thread;
/*    */   
/*    */   @TestOnly
/*    */   public ShutdownHookIntegration(@NotNull Runtime runtime) {
/* 22 */     this.runtime = (Runtime)Objects.requireNonNull(runtime, "Runtime is required");
/*    */   }
/*    */   
/*    */   public ShutdownHookIntegration() {
/* 26 */     this(Runtime.getRuntime());
/*    */   }
/*    */ 
/*    */   
/*    */   public void register(@NotNull IScopes scopes, @NotNull SentryOptions options) {
/* 31 */     Objects.requireNonNull(scopes, "Scopes are required");
/* 32 */     Objects.requireNonNull(options, "SentryOptions is required");
/*    */     
/* 34 */     if (options.isEnableShutdownHook()) {
/* 35 */       this.thread = new Thread(() -> scopes.flush(options.getFlushTimeoutMillis()), "sentry-shutdownhook");
/*    */       
/* 37 */       handleShutdownInProgress(() -> {
/*    */             this.runtime.addShutdownHook(this.thread);
/*    */             
/*    */             options.getLogger().log(SentryLevel.DEBUG, "ShutdownHookIntegration installed.", new Object[0]);
/*    */             IntegrationUtils.addIntegrationToSdkVersion("ShutdownHook");
/*    */           });
/*    */     } else {
/* 44 */       options.getLogger().log(SentryLevel.INFO, "enableShutdownHook is disabled.", new Object[0]);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void close() throws IOException {
/* 50 */     if (this.thread != null) {
/* 51 */       handleShutdownInProgress(() -> this.runtime.removeShutdownHook(this.thread));
/*    */     }
/*    */   }
/*    */   
/*    */   private void handleShutdownInProgress(@NotNull Runnable runnable) {
/*    */     try {
/* 57 */       runnable.run();
/* 58 */     } catch (IllegalStateException e) {
/* 59 */       String message = e.getMessage();
/*    */       
/* 61 */       if (message == null || (
/* 62 */         !message.equals("Shutdown in progress") && 
/* 63 */         !message.equals("VM already shutting down")))
/*    */       {
/*    */         
/* 66 */         throw e;
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   @VisibleForTesting
/*    */   @Nullable
/*    */   Thread getHook() {
/* 74 */     return this.thread;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\ShutdownHookIntegration.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */