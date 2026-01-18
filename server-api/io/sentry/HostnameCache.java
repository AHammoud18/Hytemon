/*     */ package io.sentry;
/*     */ 
/*     */ import io.sentry.util.AutoClosableReentrantLock;
/*     */ import io.sentry.util.Objects;
/*     */ import java.net.InetAddress;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.ExecutionException;
/*     */ import java.util.concurrent.ExecutorService;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
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
/*     */ @Internal
/*     */ public final class HostnameCache
/*     */ {
/*  32 */   private static final long HOSTNAME_CACHE_DURATION = TimeUnit.HOURS.toMillis(5L);
/*     */ 
/*     */   
/*  35 */   private static final long GET_HOSTNAME_TIMEOUT = TimeUnit.SECONDS.toMillis(1L); @Nullable
/*     */   private static volatile HostnameCache INSTANCE;
/*     */   @NotNull
/*  38 */   private static final AutoClosableReentrantLock staticLock = new AutoClosableReentrantLock();
/*     */ 
/*     */   
/*     */   private final long cacheDuration;
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private volatile String hostname;
/*     */ 
/*     */   
/*     */   private volatile long expirationTimestamp;
/*     */   
/*     */   @NotNull
/*  51 */   private final AtomicBoolean updateRunning = new AtomicBoolean(false);
/*     */   
/*     */   @NotNull
/*     */   private final Callable<InetAddress> getLocalhost;
/*     */   @NotNull
/*  56 */   private final ExecutorService executorService = Executors.newSingleThreadExecutor(new HostnameCacheThreadFactory());
/*     */   @NotNull
/*     */   public static HostnameCache getInstance() {
/*  59 */     if (INSTANCE == null) {
/*  60 */       ISentryLifecycleToken ignored = staticLock.acquire(); 
/*  61 */       try { if (INSTANCE == null) {
/*  62 */           INSTANCE = new HostnameCache();
/*     */         }
/*  64 */         if (ignored != null) ignored.close();  } catch (Throwable throwable) { if (ignored != null)
/*     */           try { ignored.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }
/*     */     
/*  67 */     }  return INSTANCE;
/*     */   }
/*     */   
/*     */   private HostnameCache() {
/*  71 */     this(HOSTNAME_CACHE_DURATION);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   HostnameCache(long cacheDuration) {
/*  77 */     this(cacheDuration, () -> InetAddress.getLocalHost());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   HostnameCache(long cacheDuration, @NotNull Callable<InetAddress> getLocalhost) {
/*  88 */     this.cacheDuration = cacheDuration;
/*  89 */     this.getLocalhost = (Callable<InetAddress>)Objects.requireNonNull(getLocalhost, "getLocalhost is required");
/*  90 */     updateCache();
/*     */   }
/*     */   
/*     */   void close() {
/*  94 */     this.executorService.shutdown();
/*     */   }
/*     */   
/*     */   boolean isClosed() {
/*  98 */     return this.executorService.isShutdown();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String getHostname() {
/* 110 */     if (this.expirationTimestamp < System.currentTimeMillis() && this.updateRunning
/* 111 */       .compareAndSet(false, true)) {
/* 112 */       updateCache();
/*     */     }
/*     */     
/* 115 */     return this.hostname;
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateCache() {
/* 120 */     Callable<Void> hostRetriever = () -> {
/*     */         try {
/*     */           this.hostname = ((InetAddress)this.getLocalhost.call()).getCanonicalHostName();
/*     */           
/*     */           this.expirationTimestamp = System.currentTimeMillis() + this.cacheDuration;
/*     */         } finally {
/*     */           this.updateRunning.set(false);
/*     */         } 
/*     */         
/*     */         return null;
/*     */       };
/*     */     
/*     */     try {
/* 133 */       Future<Void> futureTask = this.executorService.submit(hostRetriever);
/* 134 */       futureTask.get(GET_HOSTNAME_TIMEOUT, TimeUnit.MILLISECONDS);
/* 135 */     } catch (InterruptedException e) {
/* 136 */       Thread.currentThread().interrupt();
/* 137 */       handleCacheUpdateFailure();
/* 138 */     } catch (ExecutionException|java.util.concurrent.TimeoutException|RuntimeException e) {
/* 139 */       handleCacheUpdateFailure();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void handleCacheUpdateFailure() {
/* 144 */     this.expirationTimestamp = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(1L);
/*     */   }
/*     */   private static final class HostnameCacheThreadFactory implements ThreadFactory { private int cnt;
/*     */     
/*     */     private HostnameCacheThreadFactory() {}
/*     */     
/*     */     @NotNull
/*     */     public Thread newThread(@NotNull Runnable r) {
/* 152 */       Thread ret = new Thread(r, "SentryHostnameCache-" + this.cnt++);
/* 153 */       ret.setDaemon(true);
/* 154 */       return ret;
/*     */     } }
/*     */ 
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\HostnameCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */