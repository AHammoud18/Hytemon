/*     */ package io.netty.handler.traffic;
/*     */ 
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.util.concurrent.ScheduledExecutorService;
/*     */ import java.util.concurrent.TimeUnit;
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
/*     */ public class GlobalChannelTrafficCounter
/*     */   extends TrafficCounter
/*     */ {
/*     */   public GlobalChannelTrafficCounter(GlobalChannelTrafficShapingHandler trafficShapingHandler, ScheduledExecutorService executor, String name, long checkInterval) {
/*  40 */     super(trafficShapingHandler, executor, name, checkInterval);
/*  41 */     ObjectUtil.checkNotNullWithIAE(executor, "executor");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class MixedTrafficMonitoringTask
/*     */     implements Runnable
/*     */   {
/*     */     private final GlobalChannelTrafficShapingHandler trafficShapingHandler1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final TrafficCounter counter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     MixedTrafficMonitoringTask(GlobalChannelTrafficShapingHandler trafficShapingHandler, TrafficCounter counter) {
/*  66 */       this.trafficShapingHandler1 = trafficShapingHandler;
/*  67 */       this.counter = counter;
/*     */     }
/*     */ 
/*     */     
/*     */     public void run() {
/*  72 */       if (!this.counter.monitorActive) {
/*     */         return;
/*     */       }
/*  75 */       long newLastTime = TrafficCounter.milliSecondFromNano();
/*  76 */       this.counter.resetAccounting(newLastTime);
/*  77 */       for (GlobalChannelTrafficShapingHandler.PerChannel perChannel : this.trafficShapingHandler1.channelQueues.values()) {
/*  78 */         perChannel.channelTrafficCounter.resetAccounting(newLastTime);
/*     */       }
/*  80 */       this.trafficShapingHandler1.doAccounting(this.counter);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void start() {
/*  89 */     if (this.monitorActive) {
/*     */       return;
/*     */     }
/*  92 */     this.lastTime.set(milliSecondFromNano());
/*  93 */     long localCheckInterval = this.checkInterval.get();
/*  94 */     if (localCheckInterval > 0L) {
/*  95 */       this.monitorActive = true;
/*  96 */       this.monitor = new MixedTrafficMonitoringTask((GlobalChannelTrafficShapingHandler)this.trafficShapingHandler, this);
/*  97 */       this
/*  98 */         .scheduledFuture = this.executor.scheduleAtFixedRate(this.monitor, 0L, localCheckInterval, TimeUnit.MILLISECONDS);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void stop() {
/* 107 */     if (!this.monitorActive) {
/*     */       return;
/*     */     }
/* 110 */     this.monitorActive = false;
/* 111 */     resetAccounting(milliSecondFromNano());
/* 112 */     this.trafficShapingHandler.doAccounting(this);
/* 113 */     if (this.scheduledFuture != null) {
/* 114 */       this.scheduledFuture.cancel(true);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetCumulativeTime() {
/* 121 */     for (GlobalChannelTrafficShapingHandler.PerChannel perChannel : ((GlobalChannelTrafficShapingHandler)this.trafficShapingHandler).channelQueues.values()) {
/* 122 */       perChannel.channelTrafficCounter.resetCumulativeTime();
/*     */     }
/* 124 */     super.resetCumulativeTime();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\traffic\GlobalChannelTrafficCounter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */