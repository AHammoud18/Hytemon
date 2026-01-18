/*    */ package io.sentry;
/*    */ 
/*    */ import io.sentry.util.Objects;
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ @Internal
/*    */ public final class SendFireAndForgetOutboxSender
/*    */   implements SendCachedEnvelopeFireAndForgetIntegration.SendFireAndForgetFactory
/*    */ {
/*    */   @NotNull
/*    */   private final SendCachedEnvelopeFireAndForgetIntegration.SendFireAndForgetDirPath sendFireAndForgetDirPath;
/*    */   
/*    */   public SendFireAndForgetOutboxSender(@NotNull SendCachedEnvelopeFireAndForgetIntegration.SendFireAndForgetDirPath sendFireAndForgetDirPath) {
/* 18 */     this
/* 19 */       .sendFireAndForgetDirPath = (SendCachedEnvelopeFireAndForgetIntegration.SendFireAndForgetDirPath)Objects.requireNonNull(sendFireAndForgetDirPath, "SendFireAndForgetDirPath is required");
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public SendCachedEnvelopeFireAndForgetIntegration.SendFireAndForget create(@NotNull IScopes scopes, @NotNull SentryOptions options) {
/* 25 */     Objects.requireNonNull(scopes, "Scopes are required");
/* 26 */     Objects.requireNonNull(options, "SentryOptions is required");
/*    */     
/* 28 */     String dirPath = this.sendFireAndForgetDirPath.getDirPath();
/* 29 */     if (dirPath == null || !hasValidPath(dirPath, options.getLogger())) {
/* 30 */       options.getLogger().log(SentryLevel.ERROR, "No outbox dir path is defined in options.", new Object[0]);
/* 31 */       return null;
/*    */     } 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 41 */     OutboxSender outboxSender = new OutboxSender(scopes, options.getEnvelopeReader(), options.getSerializer(), options.getLogger(), options.getFlushTimeoutMillis(), options.getMaxQueueSize());
/*    */     
/* 43 */     return processDir(outboxSender, dirPath, options.getLogger());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\SendFireAndForgetOutboxSender.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */