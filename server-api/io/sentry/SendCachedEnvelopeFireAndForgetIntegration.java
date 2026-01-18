/*     */ package io.sentry;
/*     */ import io.sentry.transport.RateLimiter;
/*     */ import io.sentry.util.AutoClosableReentrantLock;
/*     */ import io.sentry.util.IntegrationUtils;
/*     */ import io.sentry.util.Objects;
/*     */ import java.io.Closeable;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.concurrent.RejectedExecutionException;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ public final class SendCachedEnvelopeFireAndForgetIntegration implements Integration, IConnectionStatusProvider.IConnectionStatusObserver, Closeable {
/*     */   @NotNull
/*     */   private final SendFireAndForgetFactory factory;
/*     */   @Nullable
/*     */   private IConnectionStatusProvider connectionStatusProvider;
/*     */   @Nullable
/*     */   private IScopes scopes;
/*     */   @Nullable
/*     */   private SentryOptions options;
/*     */   @Nullable
/*     */   private SendFireAndForget sender;
/*  25 */   private final AtomicBoolean isInitialized = new AtomicBoolean(false);
/*  26 */   private final AtomicBoolean isClosed = new AtomicBoolean(false); @NotNull
/*  27 */   private final AutoClosableReentrantLock lock = new AutoClosableReentrantLock();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static interface SendFireAndForgetFactory
/*     */   {
/*     */     @Nullable
/*     */     SendCachedEnvelopeFireAndForgetIntegration.SendFireAndForget create(@NotNull IScopes param1IScopes, @NotNull SentryOptions param1SentryOptions);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     default boolean hasValidPath(@Nullable String dirPath, @NotNull ILogger logger) {
/*  43 */       if (dirPath == null || dirPath.isEmpty()) {
/*  44 */         logger.log(SentryLevel.INFO, "No cached dir path is defined in options.", new Object[0]);
/*  45 */         return false;
/*     */       } 
/*  47 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     @NotNull
/*     */     default SendCachedEnvelopeFireAndForgetIntegration.SendFireAndForget processDir(@NotNull DirectoryProcessor directoryProcessor, @NotNull String dirPath, @NotNull ILogger logger) {
/*  54 */       File dirFile = new File(dirPath);
/*  55 */       return () -> {
/*     */           logger.log(SentryLevel.DEBUG, "Started processing cached files from %s", new Object[] { dirPath });
/*     */           directoryProcessor.processDirectory(dirFile);
/*     */           logger.log(SentryLevel.DEBUG, "Finished processing cached files from %s", new Object[] { dirPath });
/*     */         };
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SendCachedEnvelopeFireAndForgetIntegration(@NotNull SendFireAndForgetFactory factory) {
/*  67 */     this.factory = (SendFireAndForgetFactory)Objects.requireNonNull(factory, "SendFireAndForgetFactory is required");
/*     */   }
/*     */ 
/*     */   
/*     */   public void register(@NotNull IScopes scopes, @NotNull SentryOptions options) {
/*  72 */     this.scopes = (IScopes)Objects.requireNonNull(scopes, "Scopes are required");
/*  73 */     this.options = (SentryOptions)Objects.requireNonNull(options, "SentryOptions is required");
/*     */     
/*  75 */     String cachedDir = options.getCacheDirPath();
/*  76 */     if (!this.factory.hasValidPath(cachedDir, options.getLogger())) {
/*  77 */       options.getLogger().log(SentryLevel.ERROR, "No cache dir path is defined in options.", new Object[0]);
/*     */       
/*     */       return;
/*     */     } 
/*  81 */     options
/*  82 */       .getLogger()
/*  83 */       .log(SentryLevel.DEBUG, "SendCachedEventFireAndForgetIntegration installed.", new Object[0]);
/*  84 */     IntegrationUtils.addIntegrationToSdkVersion("SendCachedEnvelopeFireAndForget");
/*     */     
/*  86 */     sendCachedEnvelopes(scopes, options);
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/*  91 */     this.isClosed.set(true);
/*  92 */     if (this.connectionStatusProvider != null) {
/*  93 */       this.connectionStatusProvider.removeConnectionStatusObserver(this);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onConnectionStatusChanged(@NotNull IConnectionStatusProvider.ConnectionStatus status) {
/* 100 */     if (this.scopes != null && this.options != null && status != IConnectionStatusProvider.ConnectionStatus.DISCONNECTED)
/*     */     {
/*     */       
/* 103 */       sendCachedEnvelopes(this.scopes, this.options);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void sendCachedEnvelopes(@NotNull IScopes scopes, @NotNull SentryOptions options) {
/*     */     
/* 110 */     try { ISentryLifecycleToken ignored = this.lock.acquire(); 
/* 111 */       try { options
/* 112 */           .getExecutorService()
/* 113 */           .submit(() -> {
/*     */               try {
/*     */                 if (this.isClosed.get()) {
/*     */                   options.getLogger().log(SentryLevel.INFO, "SendCachedEnvelopeFireAndForgetIntegration, not trying to send after closing.", new Object[0]);
/*     */ 
/*     */ 
/*     */                   
/*     */                   return;
/*     */                 } 
/*     */ 
/*     */                 
/*     */                 if (!this.isInitialized.getAndSet(true)) {
/*     */                   this.connectionStatusProvider = options.getConnectionStatusProvider();
/*     */ 
/*     */                   
/*     */                   this.connectionStatusProvider.addConnectionStatusObserver(this);
/*     */ 
/*     */                   
/*     */                   this.sender = this.factory.create(scopes, options);
/*     */                 } 
/*     */ 
/*     */                 
/*     */                 if (this.connectionStatusProvider != null && this.connectionStatusProvider.getConnectionStatus() == IConnectionStatusProvider.ConnectionStatus.DISCONNECTED) {
/*     */                   options.getLogger().log(SentryLevel.INFO, "SendCachedEnvelopeFireAndForgetIntegration, no connection.", new Object[0]);
/*     */ 
/*     */                   
/*     */                   return;
/*     */                 } 
/*     */ 
/*     */                 
/*     */                 RateLimiter rateLimiter = scopes.getRateLimiter();
/*     */ 
/*     */                 
/*     */                 if (rateLimiter != null && rateLimiter.isActiveForCategory(DataCategory.All)) {
/*     */                   options.getLogger().log(SentryLevel.INFO, "SendCachedEnvelopeFireAndForgetIntegration, rate limiting active.", new Object[0]);
/*     */ 
/*     */                   
/*     */                   return;
/*     */                 } 
/*     */ 
/*     */                 
/*     */                 if (this.sender == null) {
/*     */                   options.getLogger().log(SentryLevel.ERROR, "SendFireAndForget factory is null.", new Object[0]);
/*     */ 
/*     */                   
/*     */                   return;
/*     */                 } 
/*     */ 
/*     */                 
/*     */                 this.sender.send();
/* 163 */               } catch (Throwable e) {
/*     */                 options.getLogger().log(SentryLevel.ERROR, "Failed trying to send cached events.", e);
/*     */               } 
/*     */             });
/*     */ 
/*     */         
/* 169 */         if (ignored != null) ignored.close();  } catch (Throwable throwable) { if (ignored != null) try { ignored.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (RejectedExecutionException e)
/* 170 */     { options
/* 171 */         .getLogger()
/* 172 */         .log(SentryLevel.ERROR, "Failed to call the executor. Cached events will not be sent. Did you call Sentry.close()?", e);
/*     */       
/*     */        }
/*     */     
/* 176 */     catch (Throwable e)
/* 177 */     { options
/* 178 */         .getLogger()
/* 179 */         .log(SentryLevel.ERROR, "Failed to call the executor. Cached events will not be sent", e); }
/*     */   
/*     */   }
/*     */   
/*     */   public static interface SendFireAndForget {
/*     */     void send();
/*     */   }
/*     */   
/*     */   public static interface SendFireAndForgetDirPath {
/*     */     @Nullable
/*     */     String getDirPath();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\SendCachedEnvelopeFireAndForgetIntegration.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */