/*     */ package io.sentry;
/*     */ 
/*     */ import io.sentry.exception.ExceptionMechanismException;
/*     */ import io.sentry.hints.BlockingFlushHint;
/*     */ import io.sentry.hints.EventDropReason;
/*     */ import io.sentry.hints.SessionEnd;
/*     */ import io.sentry.hints.TransactionEnd;
/*     */ import io.sentry.protocol.Mechanism;
/*     */ import io.sentry.protocol.SentryId;
/*     */ import io.sentry.util.AutoClosableReentrantLock;
/*     */ import io.sentry.util.HintUtils;
/*     */ import io.sentry.util.IntegrationUtils;
/*     */ import io.sentry.util.Objects;
/*     */ import java.io.Closeable;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.atomic.AtomicReference;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ import org.jetbrains.annotations.TestOnly;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class UncaughtExceptionHandlerIntegration
/*     */   implements Integration, Thread.UncaughtExceptionHandler, Closeable
/*     */ {
/*     */   @Nullable
/*     */   private Thread.UncaughtExceptionHandler defaultExceptionHandler;
/*     */   @NotNull
/*  34 */   private static final AutoClosableReentrantLock lock = new AutoClosableReentrantLock(); @Nullable
/*     */   private IScopes scopes;
/*     */   @Nullable
/*     */   private SentryOptions options;
/*     */   private boolean registered = false;
/*     */   @NotNull
/*     */   private final UncaughtExceptionHandler threadAdapter;
/*     */   
/*     */   public UncaughtExceptionHandlerIntegration() {
/*  43 */     this(UncaughtExceptionHandler.Adapter.getInstance());
/*     */   }
/*     */   
/*     */   UncaughtExceptionHandlerIntegration(@NotNull UncaughtExceptionHandler threadAdapter) {
/*  47 */     this.threadAdapter = (UncaughtExceptionHandler)Objects.requireNonNull(threadAdapter, "threadAdapter is required.");
/*     */   }
/*     */ 
/*     */   
/*     */   public final void register(@NotNull IScopes scopes, @NotNull SentryOptions options) {
/*  52 */     if (this.registered) {
/*  53 */       options
/*  54 */         .getLogger()
/*  55 */         .log(SentryLevel.ERROR, "Attempt to register a UncaughtExceptionHandlerIntegration twice.", new Object[0]);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  60 */     this.registered = true;
/*     */     
/*  62 */     this.scopes = (IScopes)Objects.requireNonNull(scopes, "Scopes are required");
/*  63 */     this.options = (SentryOptions)Objects.requireNonNull(options, "SentryOptions is required");
/*     */     
/*  65 */     this.options
/*  66 */       .getLogger()
/*  67 */       .log(SentryLevel.DEBUG, "UncaughtExceptionHandlerIntegration enabled: %s", new Object[] {
/*     */ 
/*     */           
/*  70 */           Boolean.valueOf(this.options.isEnableUncaughtExceptionHandler())
/*     */         });
/*  72 */     if (this.options.isEnableUncaughtExceptionHandler()) {
/*  73 */       ISentryLifecycleToken ignored = lock.acquire();
/*     */       
/*  75 */       try { Thread.UncaughtExceptionHandler currentHandler = this.threadAdapter.getDefaultUncaughtExceptionHandler();
/*  76 */         if (currentHandler != null) {
/*  77 */           this.options
/*  78 */             .getLogger()
/*  79 */             .log(SentryLevel.DEBUG, "default UncaughtExceptionHandler class='" + currentHandler
/*     */ 
/*     */               
/*  82 */               .getClass().getName() + "'", new Object[0]);
/*     */           
/*  84 */           if (currentHandler instanceof UncaughtExceptionHandlerIntegration) {
/*  85 */             UncaughtExceptionHandlerIntegration currentHandlerIntegration = (UncaughtExceptionHandlerIntegration)currentHandler;
/*     */             
/*  87 */             if (currentHandlerIntegration.scopes != null && scopes
/*  88 */               .getGlobalScope() == currentHandlerIntegration.scopes.getGlobalScope()) {
/*  89 */               this.defaultExceptionHandler = currentHandlerIntegration.defaultExceptionHandler;
/*     */             } else {
/*  91 */               this.defaultExceptionHandler = currentHandler;
/*     */             } 
/*     */           } else {
/*  94 */             this.defaultExceptionHandler = currentHandler;
/*     */           } 
/*     */         } 
/*     */         
/*  98 */         this.threadAdapter.setDefaultUncaughtExceptionHandler(this);
/*  99 */         if (ignored != null) ignored.close();  } catch (Throwable throwable) { if (ignored != null)
/*     */           try { ignored.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }
/* 101 */        this.options
/* 102 */         .getLogger()
/* 103 */         .log(SentryLevel.DEBUG, "UncaughtExceptionHandlerIntegration installed.", new Object[0]);
/* 104 */       IntegrationUtils.addIntegrationToSdkVersion("UncaughtExceptionHandler");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void uncaughtException(Thread thread, Throwable thrown) {
/* 110 */     if (this.options != null && this.scopes != null) {
/* 111 */       this.options.getLogger().log(SentryLevel.INFO, "Uncaught exception received.", new Object[0]);
/*     */ 
/*     */       
/*     */       try {
/* 115 */         UncaughtExceptionHint exceptionHint = new UncaughtExceptionHint(this.options.getFlushTimeoutMillis(), this.options.getLogger());
/* 116 */         Throwable throwable = getUnhandledThrowable(thread, thrown);
/* 117 */         SentryEvent event = new SentryEvent(throwable);
/* 118 */         event.setLevel(SentryLevel.FATAL);
/*     */         
/* 120 */         ITransaction transaction = this.scopes.getTransaction();
/* 121 */         if (transaction == null && event.getEventId() != null)
/*     */         {
/* 123 */           exceptionHint.setFlushable(event.getEventId());
/*     */         }
/* 125 */         Hint hint = HintUtils.createWithTypeCheckHint(exceptionHint);
/*     */         
/* 127 */         SentryId sentryId = this.scopes.captureEvent(event, hint);
/* 128 */         boolean isEventDropped = sentryId.equals(SentryId.EMPTY_ID);
/* 129 */         EventDropReason eventDropReason = HintUtils.getEventDropReason(hint);
/*     */ 
/*     */         
/* 132 */         if (!isEventDropped || EventDropReason.MULTITHREADED_DEDUPLICATION
/* 133 */           .equals(eventDropReason))
/*     */         {
/* 135 */           if (!exceptionHint.waitFlush())
/* 136 */             this.options
/* 137 */               .getLogger()
/* 138 */               .log(SentryLevel.WARNING, "Timed out waiting to flush event to disk before crashing. Event: %s", new Object[] {
/*     */ 
/*     */                   
/* 141 */                   event.getEventId()
/*     */                 }); 
/*     */         }
/* 144 */       } catch (Throwable e) {
/* 145 */         this.options
/* 146 */           .getLogger()
/* 147 */           .log(SentryLevel.ERROR, "Error sending uncaught exception to Sentry.", e);
/*     */       } 
/*     */       
/* 150 */       if (this.defaultExceptionHandler != null) {
/* 151 */         this.options.getLogger().log(SentryLevel.INFO, "Invoking inner uncaught exception handler.", new Object[0]);
/* 152 */         this.defaultExceptionHandler.uncaughtException(thread, thrown);
/*     */       }
/* 154 */       else if (this.options.isPrintUncaughtStackTrace()) {
/* 155 */         thrown.printStackTrace();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @TestOnly
/*     */   @NotNull
/*     */   static Throwable getUnhandledThrowable(@NotNull Thread thread, @NotNull Throwable thrown) {
/* 165 */     Mechanism mechanism = new Mechanism();
/* 166 */     mechanism.setHandled(Boolean.valueOf(false));
/* 167 */     mechanism.setType("UncaughtExceptionHandler");
/* 168 */     return (Throwable)new ExceptionMechanismException(mechanism, thrown, thread);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() {
/* 179 */     ISentryLifecycleToken ignored = lock.acquire(); try {
/* 180 */       if (this == this.threadAdapter.getDefaultUncaughtExceptionHandler()) {
/* 181 */         this.threadAdapter.setDefaultUncaughtExceptionHandler(this.defaultExceptionHandler);
/*     */         
/* 183 */         if (this.options != null) {
/* 184 */           this.options
/* 185 */             .getLogger()
/* 186 */             .log(SentryLevel.DEBUG, "UncaughtExceptionHandlerIntegration removed.", new Object[0]);
/*     */         }
/*     */       } else {
/* 189 */         removeFromHandlerTree(this.threadAdapter.getDefaultUncaughtExceptionHandler());
/*     */       } 
/* 191 */       if (ignored != null) ignored.close(); 
/*     */     } catch (Throwable throwable) {
/*     */       if (ignored != null)
/*     */         try {
/*     */           ignored.close();
/*     */         } catch (Throwable throwable1) {
/*     */           throwable.addSuppressed(throwable1);
/*     */         }   throw throwable;
/* 199 */     }  } private void removeFromHandlerTree(@Nullable Thread.UncaughtExceptionHandler currentHandler) { removeFromHandlerTree(currentHandler, new HashSet<>()); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void removeFromHandlerTree(@Nullable Thread.UncaughtExceptionHandler currentHandler, @NotNull Set<Thread.UncaughtExceptionHandler> visited) {
/* 221 */     if (currentHandler == null) {
/* 222 */       if (this.options != null) {
/* 223 */         this.options.getLogger().log(SentryLevel.DEBUG, "Found no UncaughtExceptionHandler to remove.", new Object[0]);
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/* 228 */     if (!visited.add(currentHandler)) {
/* 229 */       if (this.options != null) {
/* 230 */         this.options
/* 231 */           .getLogger()
/* 232 */           .log(SentryLevel.WARNING, "Cycle detected in UncaughtExceptionHandler chain while removing handler.", new Object[0]);
/*     */       }
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 239 */     if (!(currentHandler instanceof UncaughtExceptionHandlerIntegration)) {
/*     */       return;
/*     */     }
/*     */     
/* 243 */     UncaughtExceptionHandlerIntegration currentHandlerIntegration = (UncaughtExceptionHandlerIntegration)currentHandler;
/*     */ 
/*     */     
/* 246 */     if (this == currentHandlerIntegration.defaultExceptionHandler) {
/* 247 */       currentHandlerIntegration.defaultExceptionHandler = this.defaultExceptionHandler;
/* 248 */       if (this.options != null) {
/* 249 */         this.options.getLogger().log(SentryLevel.DEBUG, "UncaughtExceptionHandlerIntegration removed.", new Object[0]);
/*     */       }
/*     */     } else {
/* 252 */       removeFromHandlerTree(currentHandlerIntegration.defaultExceptionHandler, visited);
/*     */     } 
/*     */   }
/*     */   
/*     */   @Internal
/*     */   public static class UncaughtExceptionHint
/*     */     extends BlockingFlushHint
/*     */     implements SessionEnd, TransactionEnd
/*     */   {
/* 261 */     private final AtomicReference<SentryId> flushableEventId = new AtomicReference<>();
/*     */     
/*     */     public UncaughtExceptionHint(long flushTimeoutMillis, @NotNull ILogger logger) {
/* 264 */       super(flushTimeoutMillis, logger);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isFlushable(@Nullable SentryId eventId) {
/* 269 */       SentryId unwrapped = this.flushableEventId.get();
/* 270 */       return (unwrapped != null && unwrapped.equals(eventId));
/*     */     }
/*     */ 
/*     */     
/*     */     public void setFlushable(@NotNull SentryId eventId) {
/* 275 */       this.flushableEventId.set(eventId);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\UncaughtExceptionHandlerIntegration.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */