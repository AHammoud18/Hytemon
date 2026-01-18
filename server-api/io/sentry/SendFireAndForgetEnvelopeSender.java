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
/*    */ public final class SendFireAndForgetEnvelopeSender
/*    */   implements SendCachedEnvelopeFireAndForgetIntegration.SendFireAndForgetFactory
/*    */ {
/*    */   @NotNull
/*    */   private final SendCachedEnvelopeFireAndForgetIntegration.SendFireAndForgetDirPath sendFireAndForgetDirPath;
/*    */   
/*    */   public SendFireAndForgetEnvelopeSender(@NotNull SendCachedEnvelopeFireAndForgetIntegration.SendFireAndForgetDirPath sendFireAndForgetDirPath) {
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
/* 30 */       options.getLogger().log(SentryLevel.ERROR, "No cache dir path is defined in options.", new Object[0]);
/* 31 */       return null;
/*    */     } 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 40 */     EnvelopeSender envelopeSender = new EnvelopeSender(scopes, options.getSerializer(), options.getLogger(), options.getFlushTimeoutMillis(), options.getMaxQueueSize());
/*    */     
/* 42 */     return processDir(envelopeSender, dirPath, options.getLogger());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\SendFireAndForgetEnvelopeSender.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */