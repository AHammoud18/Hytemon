/*    */ package io.sentry;
/*    */ 
/*    */ import io.sentry.protocol.SentryRuntime;
/*    */ import io.sentry.protocol.SentryTransaction;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ final class SentryRuntimeEventProcessor implements EventProcessor {
/*    */   @Nullable
/*    */   private final String javaVersion;
/*    */   @Nullable
/*    */   private final String javaVendor;
/*    */   
/*    */   public SentryRuntimeEventProcessor(@Nullable String javaVersion, @Nullable String javaVendor) {
/* 15 */     this.javaVersion = javaVersion;
/* 16 */     this.javaVendor = javaVendor;
/*    */   }
/*    */   
/*    */   public SentryRuntimeEventProcessor() {
/* 20 */     this(System.getProperty("java.version"), System.getProperty("java.vendor"));
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public SentryEvent process(@NotNull SentryEvent event, @Nullable Hint hint) {
/* 25 */     return process(event);
/*    */   }
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public SentryTransaction process(@NotNull SentryTransaction transaction, @Nullable Hint hint) {
/* 31 */     return process(transaction);
/*    */   }
/*    */   @NotNull
/*    */   private <T extends SentryBaseEvent> T process(@NotNull T event) {
/* 35 */     if (event.getContexts().getRuntime() == null) {
/* 36 */       event.getContexts().setRuntime(new SentryRuntime());
/*    */     }
/* 38 */     SentryRuntime runtime = event.getContexts().getRuntime();
/* 39 */     if (runtime != null && runtime.getName() == null && runtime.getVersion() == null) {
/* 40 */       runtime.setName(this.javaVendor);
/* 41 */       runtime.setVersion(this.javaVersion);
/*    */     } 
/* 43 */     return event;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public Long getOrder() {
/* 48 */     return Long.valueOf(2000L);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\SentryRuntimeEventProcessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */