/*     */ package io.sentry;
/*     */ import io.sentry.util.AutoClosableReentrantLock;
/*     */ import io.sentry.util.Objects;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Timer;
/*     */ import java.util.TimerTask;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ @Internal
/*     */ public final class DefaultCompositePerformanceCollector implements CompositePerformanceCollector {
/*     */   private static final long TRANSACTION_COLLECTION_INTERVAL_MILLIS = 100L;
/*     */   private static final long TRANSACTION_COLLECTION_TIMEOUT_MILLIS = 30000L;
/*     */   @NotNull
/*  21 */   private final AutoClosableReentrantLock timerLock = new AutoClosableReentrantLock(); @Nullable
/*  22 */   private volatile Timer timer = null; @NotNull
/*  23 */   private final Map<String, CompositeData> compositeDataMap = new ConcurrentHashMap<>(); @NotNull
/*     */   private final List<IPerformanceSnapshotCollector> snapshotCollectors; @NotNull
/*     */   private final List<IPerformanceContinuousCollector> continuousCollectors; private final boolean hasNoCollectors;
/*     */   @NotNull
/*     */   private final SentryOptions options;
/*     */   @NotNull
/*  29 */   private final AtomicBoolean isStarted = new AtomicBoolean(false);
/*  30 */   private long lastCollectionTimestamp = 0L;
/*     */   
/*     */   public DefaultCompositePerformanceCollector(@NotNull SentryOptions options) {
/*  33 */     this.options = (SentryOptions)Objects.requireNonNull(options, "The options object is required.");
/*  34 */     this.snapshotCollectors = new ArrayList<>();
/*  35 */     this.continuousCollectors = new ArrayList<>();
/*     */ 
/*     */     
/*  38 */     List<IPerformanceCollector> performanceCollectors = options.getPerformanceCollectors();
/*  39 */     for (IPerformanceCollector performanceCollector : performanceCollectors) {
/*  40 */       if (performanceCollector instanceof IPerformanceSnapshotCollector) {
/*  41 */         this.snapshotCollectors.add((IPerformanceSnapshotCollector)performanceCollector);
/*     */       }
/*  43 */       if (performanceCollector instanceof IPerformanceContinuousCollector) {
/*  44 */         this.continuousCollectors.add((IPerformanceContinuousCollector)performanceCollector);
/*     */       }
/*     */     } 
/*     */     
/*  48 */     this.hasNoCollectors = (this.snapshotCollectors.isEmpty() && this.continuousCollectors.isEmpty());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void start(@NotNull ITransaction transaction) {
/*  54 */     if (this.hasNoCollectors) {
/*  55 */       this.options
/*  56 */         .getLogger()
/*  57 */         .log(SentryLevel.INFO, "No collector found. Performance stats will not be captured during transactions.", new Object[0]);
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  63 */     for (IPerformanceContinuousCollector collector : this.continuousCollectors) {
/*  64 */       collector.onSpanStarted(transaction);
/*     */     }
/*     */     
/*  67 */     String id = transaction.getEventId().toString();
/*  68 */     if (!this.compositeDataMap.containsKey(id)) {
/*  69 */       this.compositeDataMap.put(id, new CompositeData(transaction));
/*     */     }
/*  71 */     start(id);
/*     */   }
/*     */   
/*     */   public void start(@NotNull String id)
/*     */   {
/*  76 */     if (this.hasNoCollectors) {
/*  77 */       this.options
/*  78 */         .getLogger()
/*  79 */         .log(SentryLevel.INFO, "No collector found. Performance stats will not be captured during transactions.", new Object[0]);
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  85 */     if (!this.compositeDataMap.containsKey(id))
/*     */     {
/*     */       
/*  88 */       this.compositeDataMap.put(id, new CompositeData(null));
/*     */     }
/*  90 */     if (!this.isStarted.getAndSet(true)) {
/*  91 */       ISentryLifecycleToken ignored = this.timerLock.acquire(); 
/*  92 */       try { if (this.timer == null) {
/*  93 */           this.timer = new Timer(true);
/*     */         }
/*     */         
/*  96 */         this.timer.schedule(new TimerTask()
/*     */             {
/*     */               public void run()
/*     */               {
/* 100 */                 for (IPerformanceSnapshotCollector collector : DefaultCompositePerformanceCollector.this.snapshotCollectors) {
/* 101 */                   collector.setup();
/*     */                 }
/*     */               }
/*     */             },  0L);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 110 */         final List<ITransaction> timedOutTransactions = new ArrayList<>();
/* 111 */         TimerTask timerTask = new TimerTask()
/*     */           {
/*     */             public void run()
/*     */             {
/* 115 */               long now = System.currentTimeMillis();
/*     */ 
/*     */ 
/*     */               
/* 119 */               if (now - DefaultCompositePerformanceCollector.this.lastCollectionTimestamp <= 10L) {
/*     */                 return;
/*     */               }
/* 122 */               timedOutTransactions.clear();
/*     */               
/* 124 */               DefaultCompositePerformanceCollector.this.lastCollectionTimestamp = now;
/*     */               
/* 126 */               PerformanceCollectionData tempData = new PerformanceCollectionData(DefaultCompositePerformanceCollector.this.options.getDateProvider().now().nanoTimestamp());
/*     */ 
/*     */               
/* 129 */               for (IPerformanceSnapshotCollector collector : DefaultCompositePerformanceCollector.this.snapshotCollectors) {
/* 130 */                 collector.collect(tempData);
/*     */               }
/*     */ 
/*     */ 
/*     */               
/* 135 */               for (DefaultCompositePerformanceCollector.CompositeData data : DefaultCompositePerformanceCollector.this.compositeDataMap.values()) {
/* 136 */                 if (data.addDataAndCheckTimeout(tempData))
/*     */                 {
/* 138 */                   if (data.transaction != null) {
/* 139 */                     timedOutTransactions.add(data.transaction);
/*     */                   }
/*     */                 }
/*     */               } 
/*     */ 
/*     */               
/* 145 */               for (ITransaction t : timedOutTransactions) {
/* 146 */                 DefaultCompositePerformanceCollector.this.stop(t);
/*     */               }
/*     */             }
/*     */           };
/* 150 */         this.timer.scheduleAtFixedRate(timerTask, 100L, 100L);
/*     */ 
/*     */ 
/*     */         
/* 154 */         if (ignored != null) ignored.close();  }
/*     */       catch (Throwable throwable) { if (ignored != null)
/*     */           try { ignored.close(); }
/*     */           catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */             throw throwable; }
/*     */     
/* 160 */     }  } public void onSpanStarted(@NotNull ISpan span) { for (IPerformanceContinuousCollector collector : this.continuousCollectors) {
/* 161 */       collector.onSpanStarted(span);
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onSpanFinished(@NotNull ISpan span) {
/* 167 */     for (IPerformanceContinuousCollector collector : this.continuousCollectors) {
/* 168 */       collector.onSpanFinished(span);
/*     */     }
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public List<PerformanceCollectionData> stop(@NotNull ITransaction transaction) {
/* 174 */     this.options
/* 175 */       .getLogger()
/* 176 */       .log(SentryLevel.DEBUG, "stop collecting performance info for transactions %s (%s)", new Object[] {
/*     */ 
/*     */           
/* 179 */           transaction.getName(), transaction
/* 180 */           .getSpanContext().getTraceId().toString()
/*     */         });
/* 182 */     for (IPerformanceContinuousCollector collector : this.continuousCollectors) {
/* 183 */       collector.onSpanFinished(transaction);
/*     */     }
/*     */     
/* 186 */     return stop(transaction.getEventId().toString());
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public List<PerformanceCollectionData> stop(@NotNull String id) {
/* 191 */     CompositeData data = this.compositeDataMap.remove(id);
/* 192 */     this.options.getLogger().log(SentryLevel.DEBUG, "stop collecting performance info for " + id, new Object[0]);
/*     */ 
/*     */     
/* 195 */     if (this.compositeDataMap.isEmpty()) {
/* 196 */       close();
/*     */     }
/* 198 */     return (data != null) ? data.dataList : null;
/*     */   }
/*     */   
/*     */   public void close()
/*     */   {
/* 203 */     this.options
/* 204 */       .getLogger()
/* 205 */       .log(SentryLevel.DEBUG, "stop collecting all performance info for transactions", new Object[0]);
/*     */     
/* 207 */     this.compositeDataMap.clear();
/* 208 */     for (IPerformanceContinuousCollector collector : this.continuousCollectors) {
/* 209 */       collector.clear();
/*     */     }
/* 211 */     if (this.isStarted.getAndSet(false)) {
/* 212 */       ISentryLifecycleToken ignored = this.timerLock.acquire(); try {
/* 213 */         if (this.timer != null) {
/* 214 */           this.timer.cancel();
/* 215 */           this.timer = null;
/*     */         } 
/* 217 */         if (ignored != null) ignored.close(); 
/*     */       } catch (Throwable throwable) {
/*     */         if (ignored != null)
/*     */           try {
/*     */             ignored.close();
/*     */           } catch (Throwable throwable1) {
/*     */             throwable.addSuppressed(throwable1);
/*     */           }  
/*     */         throw throwable;
/*     */       } 
/* 227 */     }  } private class CompositeData { @NotNull private final List<PerformanceCollectionData> dataList; private CompositeData(ITransaction transaction) { this.dataList = new ArrayList<>();
/* 228 */       this.transaction = transaction;
/* 229 */       this.startTimestamp = DefaultCompositePerformanceCollector.this.options.getDateProvider().now().nanoTimestamp(); }
/*     */ 
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     private final ITransaction transaction;
/*     */     
/*     */     private final long startTimestamp;
/*     */     
/*     */     boolean addDataAndCheckTimeout(@NotNull PerformanceCollectionData data) {
/* 239 */       this.dataList.add(data);
/* 240 */       return (this.transaction != null && DefaultCompositePerformanceCollector.this
/* 241 */         .options.getDateProvider().now().nanoTimestamp() > this.startTimestamp + TimeUnit.MILLISECONDS
/*     */         
/* 243 */         .toNanos(30000L));
/*     */     } }
/*     */ 
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\DefaultCompositePerformanceCollector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */