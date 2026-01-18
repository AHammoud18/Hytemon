/*     */ package com.hypixel.hytale.server.core.util.thread;
/*     */ 
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.metrics.metric.HistoricMetric;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
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
/*     */ public abstract class TickingThread
/*     */   implements Runnable
/*     */ {
/*     */   public static final int NANOS_IN_ONE_MILLI = 1000000;
/*     */   public static final int NANOS_IN_ONE_SECOND = 1000000000;
/*     */   public static final int TPS = 30;
/*  27 */   public static long SLEEP_OFFSET = 3000000L;
/*     */ 
/*     */ 
/*     */   
/*     */   private final String threadName;
/*     */ 
/*     */   
/*     */   private final boolean daemon;
/*     */ 
/*     */   
/*  37 */   private final AtomicBoolean needsShutdown = new AtomicBoolean(true);
/*     */   
/*     */   private int tps;
/*     */   
/*     */   private int tickStepNanos;
/*     */   private HistoricMetric bufferedTickLengthMetricSet;
/*     */   @Nullable
/*     */   private Thread thread;
/*     */   @Nonnull
/*  46 */   private CompletableFuture<Void> startedFuture = new CompletableFuture<>();
/*     */ 
/*     */   
/*     */   public TickingThread(String threadName) {
/*  50 */     this(threadName, 30, false);
/*     */   }
/*     */   
/*     */   public TickingThread(String threadName, int tps, boolean daemon) {
/*  54 */     this.threadName = threadName;
/*  55 */     this.daemon = daemon;
/*     */     
/*  57 */     this.tps = tps;
/*  58 */     this.tickStepNanos = 1000000000 / tps;
/*     */ 
/*     */     
/*  61 */     this
/*     */ 
/*     */ 
/*     */       
/*  65 */       .bufferedTickLengthMetricSet = HistoricMetric.builder(this.tickStepNanos, TimeUnit.NANOSECONDS).addPeriod(10L, TimeUnit.SECONDS).addPeriod(1L, TimeUnit.MINUTES).addPeriod(5L, TimeUnit.MINUTES).build();
/*     */   }
/*     */ 
/*     */   
/*     */   public void run() {
/*     */     try {
/*  71 */       onStart();
/*  72 */       this.startedFuture.complete(null);
/*     */       
/*  74 */       long beforeTick = System.nanoTime() - this.tickStepNanos;
/*  75 */       while (this.thread != null && !this.thread.isInterrupted()) {
/*     */         long delta;
/*     */         
/*  78 */         if (!isIdle()) {
/*     */ 
/*     */           
/*  81 */           while ((delta = System.nanoTime() - beforeTick) < this.tickStepNanos) {
/*  82 */             Thread.onSpinWait();
/*     */           }
/*     */         } else {
/*  85 */           delta = System.nanoTime() - beforeTick;
/*     */         } 
/*     */         
/*  88 */         beforeTick = System.nanoTime();
/*  89 */         tick((float)delta / 1.0E9F);
/*     */ 
/*     */         
/*  92 */         long tickLength = System.nanoTime() - beforeTick;
/*     */         
/*  94 */         this.bufferedTickLengthMetricSet.add(System.nanoTime(), tickLength);
/*     */         
/*  96 */         long sleepLength = this.tickStepNanos - tickLength;
/*  97 */         if (!isIdle()) sleepLength -= SLEEP_OFFSET; 
/*  98 */         if (sleepLength > 0L) {
/*  99 */           Thread.sleep(sleepLength / 1000000L);
/*     */         }
/*     */       } 
/* 102 */     } catch (InterruptedException ignored) {
/* 103 */       Thread.currentThread().interrupt();
/* 104 */     } catch (Throwable t) {
/* 105 */       ((HytaleLogger.Api)HytaleLogger.getLogger().at(Level.SEVERE).withCause(t)).log("Exception in thread %s:", this.thread);
/*     */     } 
/*     */     
/* 108 */     if (this.needsShutdown.getAndSet(false)) {
/* 109 */       onShutdown();
/*     */     }
/*     */   }
/*     */   
/*     */   protected boolean isIdle() {
/* 114 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract void tick(float paramFloat);
/*     */   
/*     */   protected void onStart() {}
/*     */   
/*     */   protected abstract void onShutdown();
/*     */   
/*     */   @Nonnull
/*     */   public CompletableFuture<Void> start() {
/* 126 */     if (this.thread == null) {
/*     */ 
/*     */       
/* 129 */       this.thread = new Thread(this, this.threadName);
/* 130 */       this.thread.setDaemon(this.daemon);
/* 131 */     } else if (this.thread.isAlive()) {
/* 132 */       throw new IllegalStateException("Thread '" + this.thread.getName() + "' is already started!");
/*     */     } 
/*     */     
/* 135 */     this.thread.start();
/* 136 */     return this.startedFuture;
/*     */   }
/*     */   
/*     */   public boolean interrupt() {
/* 140 */     if (this.thread != null && this.thread.isAlive()) {
/* 141 */       this.thread.interrupt();
/* 142 */       return true;
/*     */     } 
/* 144 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void stop() {
/* 149 */     Thread thread = this.thread;
/* 150 */     if (thread == null)
/*     */       return;  try {
/* 152 */       int i = 0;
/* 153 */       while (thread.isAlive()) {
/* 154 */         thread.interrupt();
/* 155 */         thread.join((this.tickStepNanos / 1000000));
/*     */ 
/*     */         
/* 158 */         i += this.tickStepNanos / 1000000;
/* 159 */         if (i > 30000) {
/* 160 */           StringBuilder sb = new StringBuilder();
/* 161 */           for (StackTraceElement traceElement : thread.getStackTrace())
/* 162 */             sb.append("\tat ").append(traceElement).append('\n'); 
/* 163 */           HytaleLogger.getLogger().at(Level.SEVERE).log("Forcing TickingThread %s to stop:\n%s", thread, sb.toString());
/* 164 */           thread.stop();
/* 165 */           thread = null;
/* 166 */           if (this.needsShutdown.getAndSet(false)) {
/* 167 */             onShutdown();
/*     */           }
/*     */           return;
/*     */         } 
/*     */       } 
/* 172 */       thread = null;
/* 173 */     } catch (InterruptedException ignored) {
/* 174 */       Thread.currentThread().interrupt();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setTps(int tps) {
/* 179 */     debugAssertInTickingThread();
/* 180 */     if (tps <= 0 || tps > 2048) throw new IllegalArgumentException("UpdatesPerSecond is out of bounds (<=0 or >2048): " + tps);
/*     */     
/* 182 */     this.tps = tps;
/* 183 */     this.tickStepNanos = 1000000000 / tps;
/*     */ 
/*     */ 
/*     */     
/* 187 */     this
/*     */ 
/*     */ 
/*     */       
/* 191 */       .bufferedTickLengthMetricSet = HistoricMetric.builder(this.tickStepNanos, TimeUnit.NANOSECONDS).addPeriod(10L, TimeUnit.SECONDS).addPeriod(1L, TimeUnit.MINUTES).addPeriod(5L, TimeUnit.MINUTES).build();
/*     */   }
/*     */   
/*     */   public int getTps() {
/* 195 */     return this.tps;
/*     */   }
/*     */   
/*     */   public int getTickStepNanos() {
/* 199 */     return this.tickStepNanos;
/*     */   }
/*     */   
/*     */   public HistoricMetric getBufferedTickLengthMetricSet() {
/* 203 */     return this.bufferedTickLengthMetricSet;
/*     */   }
/*     */   
/*     */   public void clearMetrics() {
/* 207 */     this.bufferedTickLengthMetricSet.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void debugAssertInTickingThread() {
/* 215 */     if (!Thread.currentThread().equals(this.thread) && this.thread != null) {
/* 216 */       throw new AssertionError("Assert not in ticking thread!");
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isInThread() {
/* 221 */     return Thread.currentThread().equals(this.thread);
/*     */   }
/*     */   
/*     */   public boolean isStarted() {
/* 225 */     return (this.thread != null && this.thread.isAlive() && this.needsShutdown.get());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected void setThread(Thread thread) {
/* 234 */     this.thread = thread;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected Thread getThread() {
/* 239 */     return this.thread;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\util\thread\TickingThread.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */