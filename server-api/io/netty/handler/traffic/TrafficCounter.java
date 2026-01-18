/*     */ package io.netty.handler.traffic;
/*     */ 
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.util.concurrent.ScheduledExecutorService;
/*     */ import java.util.concurrent.ScheduledFuture;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.atomic.AtomicLong;
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
/*     */ public class TrafficCounter
/*     */ {
/*  40 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(TrafficCounter.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long milliSecondFromNano() {
/*  46 */     return System.nanoTime() / 1000000L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  52 */   private final AtomicLong currentWrittenBytes = new AtomicLong();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  57 */   private final AtomicLong currentReadBytes = new AtomicLong();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private long writingTime;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private long readingTime;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  72 */   private final AtomicLong cumulativeWrittenBytes = new AtomicLong();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  77 */   private final AtomicLong cumulativeReadBytes = new AtomicLong();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private long lastCumulativeTime;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private long lastWriteThroughput;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private long lastReadThroughput;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  97 */   final AtomicLong lastTime = new AtomicLong();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private volatile long lastWrittenBytes;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private volatile long lastReadBytes;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private volatile long lastWritingTime;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private volatile long lastReadingTime;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 122 */   private final AtomicLong realWrittenBytes = new AtomicLong();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private long realWriteThroughput;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 132 */   final AtomicLong checkInterval = new AtomicLong(1000L);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final String name;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final AbstractTrafficShapingHandler trafficShapingHandler;
/*     */ 
/*     */ 
/*     */   
/*     */   final ScheduledExecutorService executor;
/*     */ 
/*     */ 
/*     */   
/*     */   Runnable monitor;
/*     */ 
/*     */ 
/*     */   
/*     */   volatile ScheduledFuture<?> scheduledFuture;
/*     */ 
/*     */ 
/*     */   
/*     */   volatile boolean monitorActive;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final class TrafficMonitoringTask
/*     */     implements Runnable
/*     */   {
/*     */     private TrafficMonitoringTask() {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void run() {
/* 172 */       if (!TrafficCounter.this.monitorActive) {
/*     */         return;
/*     */       }
/* 175 */       TrafficCounter.this.resetAccounting(TrafficCounter.milliSecondFromNano());
/* 176 */       if (TrafficCounter.this.trafficShapingHandler != null) {
/* 177 */         TrafficCounter.this.trafficShapingHandler.doAccounting(TrafficCounter.this);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void start() {
/* 186 */     if (this.monitorActive) {
/*     */       return;
/*     */     }
/* 189 */     this.lastTime.set(milliSecondFromNano());
/* 190 */     long localCheckInterval = this.checkInterval.get();
/*     */     
/* 192 */     if (localCheckInterval > 0L && this.executor != null) {
/* 193 */       this.monitorActive = true;
/* 194 */       this.monitor = new TrafficMonitoringTask();
/* 195 */       this
/* 196 */         .scheduledFuture = this.executor.scheduleAtFixedRate(this.monitor, 0L, localCheckInterval, TimeUnit.MILLISECONDS);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void stop() {
/* 204 */     if (!this.monitorActive) {
/*     */       return;
/*     */     }
/* 207 */     this.monitorActive = false;
/* 208 */     resetAccounting(milliSecondFromNano());
/* 209 */     if (this.trafficShapingHandler != null) {
/* 210 */       this.trafficShapingHandler.doAccounting(this);
/*     */     }
/* 212 */     if (this.scheduledFuture != null) {
/* 213 */       this.scheduledFuture.cancel(true);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized void resetAccounting(long newLastTime) {
/* 223 */     long interval = newLastTime - this.lastTime.getAndSet(newLastTime);
/* 224 */     if (interval == 0L) {
/*     */       return;
/*     */     }
/*     */     
/* 228 */     if (logger.isDebugEnabled() && interval > checkInterval() << 1L) {
/* 229 */       logger.debug("Acct schedule not ok: " + interval + " > 2*" + checkInterval() + " from " + this.name);
/*     */     }
/* 231 */     this.lastReadBytes = this.currentReadBytes.getAndSet(0L);
/* 232 */     this.lastWrittenBytes = this.currentWrittenBytes.getAndSet(0L);
/* 233 */     this.lastReadThroughput = this.lastReadBytes * 1000L / interval;
/*     */     
/* 235 */     this.lastWriteThroughput = this.lastWrittenBytes * 1000L / interval;
/*     */     
/* 237 */     this.realWriteThroughput = this.realWrittenBytes.getAndSet(0L) * 1000L / interval;
/* 238 */     this.lastWritingTime = Math.max(this.lastWritingTime, this.writingTime);
/* 239 */     this.lastReadingTime = Math.max(this.lastReadingTime, this.readingTime);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TrafficCounter(ScheduledExecutorService executor, String name, long checkInterval) {
/* 256 */     this.name = (String)ObjectUtil.checkNotNull(name, "name");
/* 257 */     this.trafficShapingHandler = null;
/* 258 */     this.executor = executor;
/*     */     
/* 260 */     init(checkInterval);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TrafficCounter(AbstractTrafficShapingHandler trafficShapingHandler, ScheduledExecutorService executor, String name, long checkInterval) {
/* 280 */     this.name = (String)ObjectUtil.checkNotNull(name, "name");
/* 281 */     this.trafficShapingHandler = (AbstractTrafficShapingHandler)ObjectUtil.checkNotNullWithIAE(trafficShapingHandler, "trafficShapingHandler");
/* 282 */     this.executor = executor;
/*     */     
/* 284 */     init(checkInterval);
/*     */   }
/*     */ 
/*     */   
/*     */   private void init(long checkInterval) {
/* 289 */     this.lastCumulativeTime = System.currentTimeMillis();
/* 290 */     this.writingTime = milliSecondFromNano();
/* 291 */     this.readingTime = this.writingTime;
/* 292 */     this.lastWritingTime = this.writingTime;
/* 293 */     this.lastReadingTime = this.writingTime;
/* 294 */     configure(checkInterval);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void configure(long newCheckInterval) {
/* 303 */     long newInterval = newCheckInterval / 10L * 10L;
/* 304 */     if (this.checkInterval.getAndSet(newInterval) != newInterval) {
/* 305 */       if (newInterval <= 0L) {
/* 306 */         stop();
/*     */         
/* 308 */         this.lastTime.set(milliSecondFromNano());
/*     */       } else {
/*     */         
/* 311 */         stop();
/* 312 */         start();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void bytesRecvFlowControl(long recv) {
/* 324 */     this.currentReadBytes.addAndGet(recv);
/* 325 */     this.cumulativeReadBytes.addAndGet(recv);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void bytesWriteFlowControl(long write) {
/* 335 */     this.currentWrittenBytes.addAndGet(write);
/* 336 */     this.cumulativeWrittenBytes.addAndGet(write);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void bytesRealWriteFlowControl(long write) {
/* 346 */     this.realWrittenBytes.addAndGet(write);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long checkInterval() {
/* 354 */     return this.checkInterval.get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long lastReadThroughput() {
/* 361 */     return this.lastReadThroughput;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long lastWriteThroughput() {
/* 368 */     return this.lastWriteThroughput;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long lastReadBytes() {
/* 375 */     return this.lastReadBytes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long lastWrittenBytes() {
/* 382 */     return this.lastWrittenBytes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long currentReadBytes() {
/* 389 */     return this.currentReadBytes.get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long currentWrittenBytes() {
/* 396 */     return this.currentWrittenBytes.get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long lastTime() {
/* 403 */     return this.lastTime.get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long cumulativeWrittenBytes() {
/* 410 */     return this.cumulativeWrittenBytes.get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long cumulativeReadBytes() {
/* 417 */     return this.cumulativeReadBytes.get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long lastCumulativeTime() {
/* 425 */     return this.lastCumulativeTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AtomicLong getRealWrittenBytes() {
/* 432 */     return this.realWrittenBytes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getRealWriteThroughput() {
/* 439 */     return this.realWriteThroughput;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetCumulativeTime() {
/* 447 */     this.lastCumulativeTime = System.currentTimeMillis();
/* 448 */     this.cumulativeReadBytes.set(0L);
/* 449 */     this.cumulativeWrittenBytes.set(0L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String name() {
/* 456 */     return this.name;
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
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public long readTimeToWait(long size, long limitTraffic, long maxTime) {
/* 473 */     return readTimeToWait(size, limitTraffic, maxTime, milliSecondFromNano());
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long readTimeToWait(long size, long limitTraffic, long maxTime, long now) {
/* 490 */     bytesRecvFlowControl(size);
/* 491 */     if (size == 0L || limitTraffic == 0L) {
/* 492 */       return 0L;
/*     */     }
/* 494 */     long lastTimeCheck = this.lastTime.get();
/* 495 */     long sum = this.currentReadBytes.get();
/* 496 */     long localReadingTime = this.readingTime;
/* 497 */     long lastRB = this.lastReadBytes;
/* 498 */     long interval = now - lastTimeCheck;
/* 499 */     long pastDelay = Math.max(this.lastReadingTime - lastTimeCheck, 0L);
/* 500 */     if (interval > 10L) {
/*     */       
/* 502 */       long l = sum * 1000L / limitTraffic - interval + pastDelay;
/* 503 */       if (l > 10L) {
/* 504 */         if (logger.isDebugEnabled()) {
/* 505 */           logger.debug("Time: " + l + ':' + sum + ':' + interval + ':' + pastDelay);
/*     */         }
/* 507 */         if (l > maxTime && now + l - localReadingTime > maxTime) {
/* 508 */           l = maxTime;
/*     */         }
/* 510 */         this.readingTime = Math.max(localReadingTime, now + l);
/* 511 */         return l;
/*     */       } 
/* 513 */       this.readingTime = Math.max(localReadingTime, now);
/* 514 */       return 0L;
/*     */     } 
/*     */     
/* 517 */     long lastsum = sum + lastRB;
/* 518 */     long lastinterval = interval + this.checkInterval.get();
/* 519 */     long time = lastsum * 1000L / limitTraffic - lastinterval + pastDelay;
/* 520 */     if (time > 10L) {
/* 521 */       if (logger.isDebugEnabled()) {
/* 522 */         logger.debug("Time: " + time + ':' + lastsum + ':' + lastinterval + ':' + pastDelay);
/*     */       }
/* 524 */       if (time > maxTime && now + time - localReadingTime > maxTime) {
/* 525 */         time = maxTime;
/*     */       }
/* 527 */       this.readingTime = Math.max(localReadingTime, now + time);
/* 528 */       return time;
/*     */     } 
/* 530 */     this.readingTime = Math.max(localReadingTime, now);
/* 531 */     return 0L;
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
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public long writeTimeToWait(long size, long limitTraffic, long maxTime) {
/* 548 */     return writeTimeToWait(size, limitTraffic, maxTime, milliSecondFromNano());
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long writeTimeToWait(long size, long limitTraffic, long maxTime, long now) {
/* 565 */     bytesWriteFlowControl(size);
/* 566 */     if (size == 0L || limitTraffic == 0L) {
/* 567 */       return 0L;
/*     */     }
/* 569 */     long lastTimeCheck = this.lastTime.get();
/* 570 */     long sum = this.currentWrittenBytes.get();
/* 571 */     long lastWB = this.lastWrittenBytes;
/* 572 */     long localWritingTime = this.writingTime;
/* 573 */     long pastDelay = Math.max(this.lastWritingTime - lastTimeCheck, 0L);
/* 574 */     long interval = now - lastTimeCheck;
/* 575 */     if (interval > 10L) {
/*     */       
/* 577 */       long l = sum * 1000L / limitTraffic - interval + pastDelay;
/* 578 */       if (l > 10L) {
/* 579 */         if (logger.isDebugEnabled()) {
/* 580 */           logger.debug("Time: " + l + ':' + sum + ':' + interval + ':' + pastDelay);
/*     */         }
/* 582 */         if (l > maxTime && now + l - localWritingTime > maxTime) {
/* 583 */           l = maxTime;
/*     */         }
/* 585 */         this.writingTime = Math.max(localWritingTime, now + l);
/* 586 */         return l;
/*     */       } 
/* 588 */       this.writingTime = Math.max(localWritingTime, now);
/* 589 */       return 0L;
/*     */     } 
/*     */     
/* 592 */     long lastsum = sum + lastWB;
/* 593 */     long lastinterval = interval + this.checkInterval.get();
/* 594 */     long time = lastsum * 1000L / limitTraffic - lastinterval + pastDelay;
/* 595 */     if (time > 10L) {
/* 596 */       if (logger.isDebugEnabled()) {
/* 597 */         logger.debug("Time: " + time + ':' + lastsum + ':' + lastinterval + ':' + pastDelay);
/*     */       }
/* 599 */       if (time > maxTime && now + time - localWritingTime > maxTime) {
/* 600 */         time = maxTime;
/*     */       }
/* 602 */       this.writingTime = Math.max(localWritingTime, now + time);
/* 603 */       return time;
/*     */     } 
/* 605 */     this.writingTime = Math.max(localWritingTime, now);
/* 606 */     return 0L;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 611 */     return (new StringBuilder(165)).append("Monitor ").append(this.name)
/* 612 */       .append(" Current Speed Read: ").append(this.lastReadThroughput >> 10L).append(" KB/s, ")
/* 613 */       .append("Asked Write: ").append(this.lastWriteThroughput >> 10L).append(" KB/s, ")
/* 614 */       .append("Real Write: ").append(this.realWriteThroughput >> 10L).append(" KB/s, ")
/* 615 */       .append("Current Read: ").append(this.currentReadBytes.get() >> 10L).append(" KB, ")
/* 616 */       .append("Current asked Write: ").append(this.currentWrittenBytes.get() >> 10L).append(" KB, ")
/* 617 */       .append("Current real Write: ").append(this.realWrittenBytes.get() >> 10L).append(" KB").toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\traffic\TrafficCounter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */