/*     */ package io.netty.util;
/*     */ 
/*     */ import io.netty.util.concurrent.DefaultThreadFactory;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import io.netty.util.internal.SystemPropertyUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Queue;
/*     */ import java.util.concurrent.ConcurrentLinkedQueue;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
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
/*     */ @Deprecated
/*     */ public final class ThreadDeathWatcher
/*     */ {
/*  49 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(ThreadDeathWatcher.class);
/*     */ 
/*     */   
/*     */   static final ThreadFactory threadFactory;
/*     */ 
/*     */   
/*  55 */   private static final Queue<Entry> pendingEntries = new ConcurrentLinkedQueue<>();
/*  56 */   private static final Watcher watcher = new Watcher();
/*  57 */   private static final AtomicBoolean started = new AtomicBoolean();
/*     */   private static volatile Thread watcherThread;
/*     */   
/*     */   static {
/*  61 */     String poolName = "threadDeathWatcher";
/*  62 */     String serviceThreadPrefix = SystemPropertyUtil.get("io.netty.serviceThreadPrefix");
/*  63 */     if (!StringUtil.isNullOrEmpty(serviceThreadPrefix)) {
/*  64 */       poolName = serviceThreadPrefix + poolName;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  69 */     threadFactory = (ThreadFactory)new DefaultThreadFactory(poolName, true, 1, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void watch(Thread thread, Runnable task) {
/*  81 */     ObjectUtil.checkNotNull(thread, "thread");
/*  82 */     ObjectUtil.checkNotNull(task, "task");
/*     */     
/*  84 */     if (!thread.isAlive()) {
/*  85 */       throw new IllegalArgumentException("thread must be alive.");
/*     */     }
/*     */     
/*  88 */     schedule(thread, task, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void unwatch(Thread thread, Runnable task) {
/*  95 */     schedule((Thread)ObjectUtil.checkNotNull(thread, "thread"), 
/*  96 */         (Runnable)ObjectUtil.checkNotNull(task, "task"), false);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void schedule(Thread thread, Runnable task, boolean isWatch) {
/* 101 */     pendingEntries.add(new Entry(thread, task, isWatch));
/*     */     
/* 103 */     if (started.compareAndSet(false, true)) {
/* 104 */       final Thread watcherThread = threadFactory.newThread(watcher);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 110 */       AccessController.doPrivileged(new PrivilegedAction<Void>()
/*     */           {
/*     */             public Void run() {
/* 113 */               watcherThread.setContextClassLoader(null);
/* 114 */               return null;
/*     */             }
/*     */           });
/*     */       
/* 118 */       watcherThread.start();
/* 119 */       ThreadDeathWatcher.watcherThread = watcherThread;
/*     */     } 
/*     */   }
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
/*     */   public static boolean awaitInactivity(long timeout, TimeUnit unit) throws InterruptedException {
/* 133 */     ObjectUtil.checkNotNull(unit, "unit");
/*     */     
/* 135 */     Thread watcherThread = ThreadDeathWatcher.watcherThread;
/* 136 */     if (watcherThread != null) {
/* 137 */       watcherThread.join(unit.toMillis(timeout));
/* 138 */       return !watcherThread.isAlive();
/*     */     } 
/* 140 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class Watcher
/*     */     implements Runnable
/*     */   {
/* 148 */     private final List<ThreadDeathWatcher.Entry> watchees = new ArrayList<>();
/*     */ 
/*     */     
/*     */     public void run() {
/*     */       while (true) {
/* 153 */         fetchWatchees();
/* 154 */         notifyWatchees();
/*     */ 
/*     */         
/* 157 */         fetchWatchees();
/* 158 */         notifyWatchees();
/*     */         
/*     */         try {
/* 161 */           Thread.sleep(1000L);
/* 162 */         } catch (InterruptedException interruptedException) {}
/*     */ 
/*     */ 
/*     */         
/* 166 */         if (this.watchees.isEmpty() && ThreadDeathWatcher.pendingEntries.isEmpty()) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 171 */           boolean stopped = ThreadDeathWatcher.started.compareAndSet(true, false);
/* 172 */           assert stopped;
/*     */ 
/*     */           
/* 175 */           if (ThreadDeathWatcher.pendingEntries.isEmpty()) {
/*     */             break;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 184 */           if (!ThreadDeathWatcher.started.compareAndSet(false, true)) {
/*     */             break;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void fetchWatchees() {
/*     */       while (true) {
/* 199 */         ThreadDeathWatcher.Entry e = ThreadDeathWatcher.pendingEntries.poll();
/* 200 */         if (e == null) {
/*     */           break;
/*     */         }
/*     */         
/* 204 */         if (e.isWatch) {
/* 205 */           this.watchees.add(e); continue;
/*     */         } 
/* 207 */         this.watchees.remove(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private void notifyWatchees() {
/* 213 */       List<ThreadDeathWatcher.Entry> watchees = this.watchees;
/* 214 */       for (int i = 0; i < watchees.size(); ) {
/* 215 */         ThreadDeathWatcher.Entry e = watchees.get(i);
/* 216 */         if (!e.thread.isAlive()) {
/* 217 */           watchees.remove(i);
/*     */           try {
/* 219 */             e.task.run();
/* 220 */           } catch (Throwable t) {
/* 221 */             ThreadDeathWatcher.logger.warn("Thread death watcher task raised an exception:", t);
/*     */           }  continue;
/*     */         } 
/* 224 */         i++;
/*     */       } 
/*     */     }
/*     */     
/*     */     private Watcher() {} }
/*     */   
/*     */   private static final class Entry {
/*     */     final Thread thread;
/*     */     final Runnable task;
/*     */     final boolean isWatch;
/*     */     
/*     */     Entry(Thread thread, Runnable task, boolean isWatch) {
/* 236 */       this.thread = thread;
/* 237 */       this.task = task;
/* 238 */       this.isWatch = isWatch;
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 243 */       return this.thread.hashCode() ^ this.task.hashCode();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object obj) {
/* 248 */       if (obj == this) {
/* 249 */         return true;
/*     */       }
/*     */       
/* 252 */       if (!(obj instanceof Entry)) {
/* 253 */         return false;
/*     */       }
/*     */       
/* 256 */       Entry that = (Entry)obj;
/* 257 */       return (this.thread == that.thread && this.task == that.task);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\ThreadDeathWatcher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */