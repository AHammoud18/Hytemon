/*     */ package io.netty.util.concurrent;
/*     */ 
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
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
/*     */ public abstract class MultithreadEventExecutorGroup
/*     */   extends AbstractEventExecutorGroup
/*     */ {
/*     */   private final EventExecutor[] children;
/*     */   private final Set<EventExecutor> readonlyChildren;
/*  41 */   private final AtomicInteger terminatedChildren = new AtomicInteger();
/*  42 */   private final Promise<?> terminationFuture = new DefaultPromise(GlobalEventExecutor.INSTANCE);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final EventExecutorChooserFactory.EventExecutorChooser chooser;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected MultithreadEventExecutorGroup(int nThreads, ThreadFactory threadFactory, Object... args) {
/*  53 */     this(nThreads, (threadFactory == null) ? null : new ThreadPerTaskExecutor(threadFactory), args);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected MultithreadEventExecutorGroup(int nThreads, Executor executor, Object... args) {
/*  64 */     this(nThreads, executor, DefaultEventExecutorChooserFactory.INSTANCE, args);
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
/*     */   protected MultithreadEventExecutorGroup(int nThreads, Executor executor, EventExecutorChooserFactory chooserFactory, Object... args) {
/*  77 */     ObjectUtil.checkPositive(nThreads, "nThreads");
/*     */     
/*  79 */     if (executor == null) {
/*  80 */       executor = new ThreadPerTaskExecutor(newDefaultThreadFactory());
/*     */     }
/*     */     
/*  83 */     this.children = new EventExecutor[nThreads];
/*     */     
/*  85 */     for (int i = 0; i < nThreads; i++) {
/*  86 */       boolean success = false;
/*     */     }
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
/* 115 */     this.chooser = chooserFactory.newChooser(this.children);
/*     */     
/* 117 */     FutureListener<Object> terminationListener = new FutureListener()
/*     */       {
/*     */         public void operationComplete(Future<Object> future) throws Exception {
/* 120 */           if (MultithreadEventExecutorGroup.this.terminatedChildren.incrementAndGet() == MultithreadEventExecutorGroup.this.children.length) {
/* 121 */             MultithreadEventExecutorGroup.this.terminationFuture.setSuccess(null);
/*     */           }
/*     */         }
/*     */       };
/*     */     
/* 126 */     for (EventExecutor e : this.children) {
/* 127 */       e.terminationFuture().addListener(terminationListener);
/*     */     }
/*     */     
/* 130 */     Set<EventExecutor> childrenSet = new LinkedHashSet<>(this.children.length);
/* 131 */     Collections.addAll(childrenSet, this.children);
/* 132 */     this.readonlyChildren = Collections.unmodifiableSet(childrenSet);
/*     */   }
/*     */   
/*     */   protected ThreadFactory newDefaultThreadFactory() {
/* 136 */     return new DefaultThreadFactory(getClass());
/*     */   }
/*     */ 
/*     */   
/*     */   public EventExecutor next() {
/* 141 */     return this.chooser.next();
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<EventExecutor> iterator() {
/* 146 */     return this.readonlyChildren.iterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int executorCount() {
/* 154 */     return this.children.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int activeExecutorCount() {
/* 165 */     if (this.chooser instanceof EventExecutorChooserFactory.ObservableEventExecutorChooser) {
/* 166 */       return ((EventExecutorChooserFactory.ObservableEventExecutorChooser)this.chooser).activeExecutorCount();
/*     */     }
/* 168 */     return executorCount();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<AutoScalingEventExecutorChooserFactory.AutoScalingUtilizationMetric> executorUtilizations() {
/* 178 */     if (this.chooser instanceof EventExecutorChooserFactory.ObservableEventExecutorChooser) {
/* 179 */       return ((EventExecutorChooserFactory.ObservableEventExecutorChooser)this.chooser).executorUtilizations();
/*     */     }
/* 181 */     return Collections.emptyList();
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
/*     */   public Future<?> shutdownGracefully(long quietPeriod, long timeout, TimeUnit unit) {
/* 193 */     for (EventExecutor l : this.children) {
/* 194 */       l.shutdownGracefully(quietPeriod, timeout, unit);
/*     */     }
/* 196 */     return terminationFuture();
/*     */   }
/*     */ 
/*     */   
/*     */   public Future<?> terminationFuture() {
/* 201 */     return this.terminationFuture;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void shutdown() {
/* 207 */     for (EventExecutor l : this.children) {
/* 208 */       l.shutdown();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isShuttingDown() {
/* 214 */     for (EventExecutor l : this.children) {
/* 215 */       if (!l.isShuttingDown()) {
/* 216 */         return false;
/*     */       }
/*     */     } 
/* 219 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isShutdown() {
/* 224 */     for (EventExecutor l : this.children) {
/* 225 */       if (!l.isShutdown()) {
/* 226 */         return false;
/*     */       }
/*     */     } 
/* 229 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isTerminated() {
/* 234 */     for (EventExecutor l : this.children) {
/* 235 */       if (!l.isTerminated()) {
/* 236 */         return false;
/*     */       }
/*     */     } 
/* 239 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
/* 245 */     long deadline = System.nanoTime() + unit.toNanos(timeout); EventExecutor[] arrayOfEventExecutor; int i; byte b;
/* 246 */     for (arrayOfEventExecutor = this.children, i = arrayOfEventExecutor.length, b = 0; b < i; ) { EventExecutor l = arrayOfEventExecutor[b];
/*     */       while (true) {
/* 248 */         long timeLeft = deadline - System.nanoTime();
/* 249 */         if (timeLeft <= 0L) {
/*     */           break;
/*     */         }
/* 252 */         if (l.awaitTermination(timeLeft, TimeUnit.NANOSECONDS)) {
/*     */           b++;
/*     */         }
/*     */       }  }
/*     */     
/* 257 */     return isTerminated();
/*     */   }
/*     */   
/*     */   protected abstract EventExecutor newChild(Executor paramExecutor, Object... paramVarArgs) throws Exception;
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\concurrent\MultithreadEventExecutorGroup.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */