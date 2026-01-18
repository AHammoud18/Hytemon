/*     */ package com.hypixel.hytale.server.core.util.concurrent;
/*     */ 
/*     */ import java.security.Permission;
/*     */ import java.util.concurrent.ExecutorService;
/*     */ import java.util.concurrent.Semaphore;
/*     */ import java.util.concurrent.SynchronousQueue;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import java.util.concurrent.ThreadPoolExecutor;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.atomic.AtomicLong;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Predicate;
/*     */ import javax.annotation.Nonnull;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ThreadUtil
/*     */ {
/*     */   public static void forceTimeHighResolution() {
/*  60 */     Thread t = new Thread(() -> { try {
/*     */             while (!Thread.interrupted())
/*     */               Thread.sleep(Long.MAX_VALUE); 
/*  63 */           } catch (InterruptedException ignored) {
/*     */             Thread.currentThread().interrupt();
/*     */           } 
/*     */         }"ForceTimeHighResolution");
/*  67 */     t.setDaemon(true);
/*  68 */     t.start();
/*     */   }
/*     */   
/*     */   public static void createKeepAliveThread(@Nonnull Semaphore alive) {
/*  72 */     Thread t = new Thread(() -> {
/*     */           try {
/*     */             alive.acquire();
/*  75 */           } catch (InterruptedException ignored) {
/*     */             Thread.currentThread().interrupt();
/*     */           } 
/*     */         }"KeepAlive");
/*  79 */     t.setDaemon(false);
/*  80 */     t.start();
/*     */   }
/*     */   
/*     */   static class ThreadWatcher
/*     */     extends SecurityManager {
/*     */     private final Predicate<Thread> predicate;
/*     */     private final Consumer<Thread> action;
/*     */     
/*     */     public ThreadWatcher(Predicate<Thread> predicate, Consumer<Thread> action) {
/*  89 */       this.predicate = predicate;
/*  90 */       this.action = action;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void checkPermission(Permission perm) {}
/*     */ 
/*     */ 
/*     */     
/*     */     public void checkPermission(Permission perm, Object context) {}
/*     */ 
/*     */ 
/*     */     
/*     */     public void checkAccess(ThreadGroup g) {
/* 105 */       Thread creatingThread = Thread.currentThread();
/* 106 */       if (this.predicate.test(creatingThread)) {
/* 107 */         this.action.accept(creatingThread);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ExecutorService newCachedThreadPool(int maximumPoolSize, @Nonnull ThreadFactory threadFactory) {
/* 114 */     return new ThreadPoolExecutor(0, maximumPoolSize, 60L, TimeUnit.SECONDS, new SynchronousQueue<>(), threadFactory);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ThreadFactory daemon(@Nonnull String name) {
/* 119 */     return r -> {
/*     */         Thread t = new Thread(r, name);
/*     */         t.setDaemon(true);
/*     */         return t;
/*     */       };
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ThreadFactory daemonCounted(@Nonnull String name) {
/* 128 */     AtomicLong count = new AtomicLong();
/* 129 */     return r -> {
/*     */         Thread t = new Thread(r, String.format(name, new Object[] { Long.valueOf(count.incrementAndGet()) }));
/*     */         t.setDaemon(true);
/*     */         return t;
/*     */       };
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\util\concurrent\ThreadUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */