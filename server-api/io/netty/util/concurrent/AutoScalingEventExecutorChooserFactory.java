/*     */ package io.netty.util.concurrent;
/*     */ 
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.atomic.AtomicLong;
/*     */ import java.util.concurrent.atomic.AtomicReference;
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
/*     */ public final class AutoScalingEventExecutorChooserFactory
/*     */   implements EventExecutorChooserFactory
/*     */ {
/*     */   public static final class AutoScalingUtilizationMetric
/*     */   {
/*     */     private final EventExecutor executor;
/*  56 */     private final AtomicLong utilizationBits = new AtomicLong();
/*     */     
/*     */     AutoScalingUtilizationMetric(EventExecutor executor) {
/*  59 */       this.executor = executor;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double utilization() {
/*  67 */       return Double.longBitsToDouble(this.utilizationBits.get());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public EventExecutor executor() {
/*  75 */       return this.executor;
/*     */     }
/*     */     
/*     */     void setUtilization(double utilization) {
/*  79 */       long bits = Double.doubleToRawLongBits(utilization);
/*  80 */       this.utilizationBits.lazySet(bits);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static final Runnable NO_OOP_TASK = () -> {
/*     */     
/*     */     };
/*     */ 
/*     */   
/*     */   private final int minChildren;
/*     */ 
/*     */   
/*     */   private final int maxChildren;
/*     */ 
/*     */   
/*     */   private final long utilizationCheckPeriodNanos;
/*     */ 
/*     */   
/*     */   private final double scaleDownThreshold;
/*     */   
/*     */   private final double scaleUpThreshold;
/*     */   
/*     */   private final int maxRampUpStep;
/*     */   
/*     */   private final int maxRampDownStep;
/*     */   
/*     */   private final int scalingPatienceCycles;
/*     */ 
/*     */   
/*     */   public AutoScalingEventExecutorChooserFactory(int minThreads, int maxThreads, long utilizationWindow, TimeUnit windowUnit, double scaleDownThreshold, double scaleUpThreshold, int maxRampUpStep, int maxRampDownStep, int scalingPatienceCycles) {
/* 111 */     this.minChildren = ObjectUtil.checkPositiveOrZero(minThreads, "minThreads");
/* 112 */     this.maxChildren = ObjectUtil.checkPositive(maxThreads, "maxThreads");
/* 113 */     if (minThreads > maxThreads)
/* 114 */       throw new IllegalArgumentException(String.format("minThreads: %d must not be greater than maxThreads: %d", new Object[] {
/* 115 */               Integer.valueOf(minThreads), Integer.valueOf(maxThreads)
/*     */             })); 
/* 117 */     this
/* 118 */       .utilizationCheckPeriodNanos = ((TimeUnit)ObjectUtil.checkNotNull(windowUnit, "windowUnit")).toNanos(ObjectUtil.checkPositive(utilizationWindow, "utilizationWindow"));
/*     */     
/* 120 */     this.scaleDownThreshold = ObjectUtil.checkInRange(scaleDownThreshold, 0.0D, 1.0D, "scaleDownThreshold");
/* 121 */     this.scaleUpThreshold = ObjectUtil.checkInRange(scaleUpThreshold, 0.0D, 1.0D, "scaleUpThreshold");
/* 122 */     if (scaleDownThreshold >= scaleUpThreshold) {
/* 123 */       throw new IllegalArgumentException("scaleDownThreshold must be less than scaleUpThreshold: " + scaleDownThreshold + " >= " + scaleUpThreshold);
/*     */     }
/*     */ 
/*     */     
/* 127 */     this.maxRampUpStep = ObjectUtil.checkPositive(maxRampUpStep, "maxRampUpStep");
/* 128 */     this.maxRampDownStep = ObjectUtil.checkPositive(maxRampDownStep, "maxRampDownStep");
/* 129 */     this.scalingPatienceCycles = ObjectUtil.checkPositiveOrZero(scalingPatienceCycles, "scalingPatienceCycles");
/*     */   }
/*     */ 
/*     */   
/*     */   public EventExecutorChooserFactory.EventExecutorChooser newChooser(EventExecutor[] executors) {
/* 134 */     return new AutoScalingEventExecutorChooser(executors);
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class AutoScalingState
/*     */   {
/*     */     final int activeChildrenCount;
/*     */     
/*     */     final long nextWakeUpIndex;
/*     */     
/*     */     final EventExecutor[] activeExecutors;
/*     */     final EventExecutorChooserFactory.EventExecutorChooser activeExecutorsChooser;
/*     */     
/*     */     AutoScalingState(int activeChildrenCount, long nextWakeUpIndex, EventExecutor[] activeExecutors) {
/* 148 */       this.activeChildrenCount = activeChildrenCount;
/* 149 */       this.nextWakeUpIndex = nextWakeUpIndex;
/* 150 */       this.activeExecutors = activeExecutors;
/* 151 */       this.activeExecutorsChooser = DefaultEventExecutorChooserFactory.INSTANCE.newChooser(activeExecutors);
/*     */     }
/*     */   }
/*     */   
/*     */   private final class AutoScalingEventExecutorChooser implements EventExecutorChooserFactory.ObservableEventExecutorChooser {
/*     */     private final EventExecutor[] executors;
/*     */     private final EventExecutorChooserFactory.EventExecutorChooser allExecutorsChooser;
/*     */     private final AtomicReference<AutoScalingEventExecutorChooserFactory.AutoScalingState> state;
/*     */     private final List<AutoScalingEventExecutorChooserFactory.AutoScalingUtilizationMetric> utilizationMetrics;
/*     */     
/*     */     AutoScalingEventExecutorChooser(EventExecutor[] executors) {
/* 162 */       this.executors = executors;
/* 163 */       List<AutoScalingEventExecutorChooserFactory.AutoScalingUtilizationMetric> metrics = new ArrayList<>(executors.length);
/* 164 */       for (EventExecutor executor : executors) {
/* 165 */         metrics.add(new AutoScalingEventExecutorChooserFactory.AutoScalingUtilizationMetric(executor));
/*     */       }
/* 167 */       this.utilizationMetrics = Collections.unmodifiableList(metrics);
/* 168 */       this.allExecutorsChooser = DefaultEventExecutorChooserFactory.INSTANCE.newChooser(executors);
/*     */       
/* 170 */       AutoScalingEventExecutorChooserFactory.AutoScalingState initialState = new AutoScalingEventExecutorChooserFactory.AutoScalingState(AutoScalingEventExecutorChooserFactory.this.maxChildren, 0L, executors);
/* 171 */       this.state = new AtomicReference<>(initialState);
/*     */       
/* 173 */       ScheduledFuture<?> utilizationMonitoringTask = GlobalEventExecutor.INSTANCE.scheduleAtFixedRate(new UtilizationMonitor(), AutoScalingEventExecutorChooserFactory.this
/* 174 */           .utilizationCheckPeriodNanos, AutoScalingEventExecutorChooserFactory.this.utilizationCheckPeriodNanos, TimeUnit.NANOSECONDS);
/*     */ 
/*     */       
/* 177 */       if (executors.length > 0) {
/* 178 */         executors[0].terminationFuture().addListener(future -> utilizationMonitoringTask.cancel(false));
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public EventExecutor next() {
/* 189 */       AutoScalingEventExecutorChooserFactory.AutoScalingState currentState = this.state.get();
/*     */       
/* 191 */       if (currentState.activeExecutors.length == 0) {
/*     */ 
/*     */ 
/*     */         
/* 195 */         tryScaleUpBy(1);
/* 196 */         return this.allExecutorsChooser.next();
/*     */       } 
/* 198 */       return currentState.activeExecutorsChooser.next();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void tryScaleUpBy(int amount) {
/*     */       AutoScalingEventExecutorChooserFactory.AutoScalingState oldState;
/*     */       AutoScalingEventExecutorChooserFactory.AutoScalingState newState;
/* 208 */       if (amount <= 0) {
/*     */         return;
/*     */       }
/*     */       
/*     */       do {
/* 213 */         oldState = this.state.get();
/* 214 */         if (oldState.activeChildrenCount >= AutoScalingEventExecutorChooserFactory.this.maxChildren) {
/*     */           return;
/*     */         }
/*     */         
/* 218 */         int canAdd = Math.min(amount, AutoScalingEventExecutorChooserFactory.this.maxChildren - oldState.activeChildrenCount);
/* 219 */         List<EventExecutor> wokenUp = new ArrayList<>(canAdd);
/* 220 */         long startIndex = oldState.nextWakeUpIndex;
/*     */         
/* 222 */         for (int i = 0; i < this.executors.length; i++) {
/* 223 */           EventExecutor child = this.executors[(int)Math.abs((startIndex + i) % this.executors.length)];
/*     */           
/* 225 */           if (wokenUp.size() >= canAdd) {
/*     */             break;
/*     */           }
/* 228 */           if (child instanceof SingleThreadEventExecutor) {
/* 229 */             SingleThreadEventExecutor stee = (SingleThreadEventExecutor)child;
/* 230 */             if (stee.isSuspended()) {
/* 231 */               stee.execute(AutoScalingEventExecutorChooserFactory.NO_OOP_TASK);
/* 232 */               wokenUp.add(stee);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 237 */         if (wokenUp.isEmpty()) {
/*     */           return;
/*     */         }
/*     */ 
/*     */         
/* 242 */         List<EventExecutor> newActiveList = new ArrayList<>(oldState.activeExecutors.length + wokenUp.size());
/* 243 */         Collections.addAll(newActiveList, oldState.activeExecutors);
/* 244 */         newActiveList.addAll(wokenUp);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 249 */         newState = new AutoScalingEventExecutorChooserFactory.AutoScalingState(oldState.activeChildrenCount + wokenUp.size(), startIndex + wokenUp.size(), newActiveList.<EventExecutor>toArray(new EventExecutor[0]));
/*     */       }
/* 251 */       while (!this.state.compareAndSet(oldState, newState));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int activeExecutorCount() {
/* 260 */       return ((AutoScalingEventExecutorChooserFactory.AutoScalingState)this.state.get()).activeChildrenCount;
/*     */     }
/*     */ 
/*     */     
/*     */     public List<AutoScalingEventExecutorChooserFactory.AutoScalingUtilizationMetric> executorUtilizations() {
/* 265 */       return this.utilizationMetrics;
/*     */     }
/*     */     
/*     */     private final class UtilizationMonitor implements Runnable {
/* 269 */       private final List<SingleThreadEventExecutor> consistentlyIdleChildren = new ArrayList<>(AutoScalingEventExecutorChooserFactory.this.maxChildren);
/*     */       private long lastCheckTimeNanos;
/*     */       
/*     */       public void run() {
/*     */         long totalTime;
/* 274 */         if (AutoScalingEventExecutorChooserFactory.AutoScalingEventExecutorChooser.this.executors.length == 0 || AutoScalingEventExecutorChooserFactory.AutoScalingEventExecutorChooser.this.executors[0].isShuttingDown()) {
/*     */           return;
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 281 */         long now = AutoScalingEventExecutorChooserFactory.AutoScalingEventExecutorChooser.this.executors[0].ticker().nanoTime();
/*     */ 
/*     */         
/* 284 */         if (this.lastCheckTimeNanos == 0L) {
/*     */           
/* 286 */           totalTime = AutoScalingEventExecutorChooserFactory.this.utilizationCheckPeriodNanos;
/*     */         } else {
/*     */           
/* 289 */           totalTime = now - this.lastCheckTimeNanos;
/*     */         } 
/*     */ 
/*     */         
/* 293 */         this.lastCheckTimeNanos = now;
/*     */         
/* 295 */         if (totalTime <= 0L) {
/*     */           return;
/*     */         }
/*     */ 
/*     */         
/* 300 */         int consistentlyBusyChildren = 0;
/* 301 */         this.consistentlyIdleChildren.clear();
/*     */         
/* 303 */         AutoScalingEventExecutorChooserFactory.AutoScalingState currentState = AutoScalingEventExecutorChooserFactory.AutoScalingEventExecutorChooser.this.state.get();
/*     */         
/* 305 */         for (int i = 0; i < AutoScalingEventExecutorChooserFactory.AutoScalingEventExecutorChooser.this.executors.length; i++) {
/* 306 */           EventExecutor child = AutoScalingEventExecutorChooserFactory.AutoScalingEventExecutorChooser.this.executors[i];
/* 307 */           if (child instanceof SingleThreadEventExecutor) {
/*     */ 
/*     */ 
/*     */             
/* 311 */             SingleThreadEventExecutor eventExecutor = (SingleThreadEventExecutor)child;
/*     */             
/* 313 */             double utilization = 0.0D;
/* 314 */             if (!eventExecutor.isSuspended()) {
/* 315 */               long activeTime = eventExecutor.getAndResetAccumulatedActiveTimeNanos();
/*     */               
/* 317 */               if (activeTime == 0L) {
/* 318 */                 long lastActivity = eventExecutor.getLastActivityTimeNanos();
/* 319 */                 long idleTime = now - lastActivity;
/*     */ 
/*     */ 
/*     */                 
/* 323 */                 if (idleTime < totalTime) {
/* 324 */                   activeTime = totalTime - idleTime;
/*     */                 }
/*     */               } 
/*     */ 
/*     */               
/* 329 */               utilization = Math.min(1.0D, activeTime / totalTime);
/*     */               
/* 331 */               if (utilization < AutoScalingEventExecutorChooserFactory.this.scaleDownThreshold) {
/*     */                 
/* 333 */                 int idleCycles = eventExecutor.getAndIncrementIdleCycles();
/* 334 */                 eventExecutor.resetBusyCycles();
/* 335 */                 if (idleCycles >= AutoScalingEventExecutorChooserFactory.this.scalingPatienceCycles && eventExecutor
/* 336 */                   .getNumOfRegisteredChannels() <= 0) {
/* 337 */                   this.consistentlyIdleChildren.add(eventExecutor);
/*     */                 }
/* 339 */               } else if (utilization > AutoScalingEventExecutorChooserFactory.this.scaleUpThreshold) {
/*     */                 
/* 341 */                 int busyCycles = eventExecutor.getAndIncrementBusyCycles();
/* 342 */                 eventExecutor.resetIdleCycles();
/* 343 */                 if (busyCycles >= AutoScalingEventExecutorChooserFactory.this.scalingPatienceCycles) {
/* 344 */                   consistentlyBusyChildren++;
/*     */                 }
/*     */               } else {
/*     */                 
/* 348 */                 eventExecutor.resetIdleCycles();
/* 349 */                 eventExecutor.resetBusyCycles();
/*     */               } 
/*     */             } 
/*     */             
/* 353 */             ((AutoScalingEventExecutorChooserFactory.AutoScalingUtilizationMetric)AutoScalingEventExecutorChooserFactory.AutoScalingEventExecutorChooser.this.utilizationMetrics.get(i)).setUtilization(utilization);
/*     */           } 
/*     */         } 
/* 356 */         int currentActive = currentState.activeChildrenCount;
/*     */ 
/*     */         
/* 359 */         if (consistentlyBusyChildren > 0 && currentActive < AutoScalingEventExecutorChooserFactory.this.maxChildren) {
/*     */           
/* 361 */           int threadsToAdd = Math.min(consistentlyBusyChildren, AutoScalingEventExecutorChooserFactory.this.maxRampUpStep);
/* 362 */           threadsToAdd = Math.min(threadsToAdd, AutoScalingEventExecutorChooserFactory.this.maxChildren - currentActive);
/* 363 */           if (threadsToAdd > 0) {
/* 364 */             AutoScalingEventExecutorChooserFactory.AutoScalingEventExecutorChooser.this.tryScaleUpBy(threadsToAdd);
/*     */             
/*     */             return;
/*     */           } 
/*     */         } 
/*     */         
/* 370 */         boolean changed = false;
/* 371 */         if (!this.consistentlyIdleChildren.isEmpty() && currentActive > AutoScalingEventExecutorChooserFactory.this.minChildren) {
/*     */ 
/*     */           
/* 374 */           int threadsToRemove = Math.min(this.consistentlyIdleChildren.size(), AutoScalingEventExecutorChooserFactory.this.maxRampDownStep);
/* 375 */           threadsToRemove = Math.min(threadsToRemove, currentActive - AutoScalingEventExecutorChooserFactory.this.minChildren);
/*     */           
/* 377 */           for (int j = 0; j < threadsToRemove; j++) {
/* 378 */             SingleThreadEventExecutor childToSuspend = this.consistentlyIdleChildren.get(j);
/* 379 */             if (childToSuspend.trySuspend()) {
/*     */               
/* 381 */               childToSuspend.resetBusyCycles();
/* 382 */               childToSuspend.resetIdleCycles();
/* 383 */               changed = true;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 389 */         if (changed || currentActive != currentState.activeExecutors.length) {
/* 390 */           rebuildActiveExecutors();
/*     */         }
/*     */       }
/*     */ 
/*     */       
/*     */       private void rebuildActiveExecutors() {
/*     */         AutoScalingEventExecutorChooserFactory.AutoScalingState oldState;
/*     */         AutoScalingEventExecutorChooserFactory.AutoScalingState newState;
/*     */         do {
/* 399 */           oldState = AutoScalingEventExecutorChooserFactory.AutoScalingEventExecutorChooser.this.state.get();
/* 400 */           List<EventExecutor> active = new ArrayList<>(oldState.activeChildrenCount);
/* 401 */           for (EventExecutor executor : AutoScalingEventExecutorChooserFactory.AutoScalingEventExecutorChooser.this.executors) {
/* 402 */             if (!executor.isSuspended()) {
/* 403 */               active.add(executor);
/*     */             }
/*     */           } 
/* 406 */           EventExecutor[] newActiveExecutors = active.<EventExecutor>toArray(new EventExecutor[0]);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 411 */           newState = new AutoScalingEventExecutorChooserFactory.AutoScalingState(newActiveExecutors.length, oldState.nextWakeUpIndex, newActiveExecutors);
/*     */         
/*     */         }
/* 414 */         while (!AutoScalingEventExecutorChooserFactory.AutoScalingEventExecutorChooser.this.state.compareAndSet(oldState, newState));
/*     */       }
/*     */       
/*     */       private UtilizationMonitor() {}
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\concurrent\AutoScalingEventExecutorChooserFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */