/*     */ package io.netty.util.concurrent;
/*     */ 
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Queue;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.ExecutionException;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.concurrent.RejectedExecutionException;
/*     */ import java.util.concurrent.ScheduledFuture;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.TimeoutException;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
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
/*     */ public final class NonStickyEventExecutorGroup
/*     */   implements EventExecutorGroup
/*     */ {
/*     */   private final EventExecutorGroup group;
/*     */   private final int maxTaskExecutePerRun;
/*     */   
/*     */   public NonStickyEventExecutorGroup(EventExecutorGroup group) {
/*  51 */     this(group, 1024);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NonStickyEventExecutorGroup(EventExecutorGroup group, int maxTaskExecutePerRun) {
/*  59 */     this.group = verify(group);
/*  60 */     this.maxTaskExecutePerRun = ObjectUtil.checkPositive(maxTaskExecutePerRun, "maxTaskExecutePerRun");
/*     */   }
/*     */   
/*     */   private static EventExecutorGroup verify(EventExecutorGroup group) {
/*  64 */     Iterator<EventExecutor> executors = ((EventExecutorGroup)ObjectUtil.checkNotNull(group, "group")).iterator();
/*  65 */     while (executors.hasNext()) {
/*  66 */       EventExecutor executor = executors.next();
/*  67 */       if (executor instanceof OrderedEventExecutor) {
/*  68 */         throw new IllegalArgumentException("EventExecutorGroup " + group + " contains OrderedEventExecutors: " + executor);
/*     */       }
/*     */     } 
/*     */     
/*  72 */     return group;
/*     */   }
/*     */   
/*     */   private NonStickyOrderedEventExecutor newExecutor(EventExecutor executor) {
/*  76 */     return new NonStickyOrderedEventExecutor(executor, this.maxTaskExecutePerRun);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isShuttingDown() {
/*  81 */     return this.group.isShuttingDown();
/*     */   }
/*     */ 
/*     */   
/*     */   public Future<?> shutdownGracefully() {
/*  86 */     return this.group.shutdownGracefully();
/*     */   }
/*     */ 
/*     */   
/*     */   public Future<?> shutdownGracefully(long quietPeriod, long timeout, TimeUnit unit) {
/*  91 */     return this.group.shutdownGracefully(quietPeriod, timeout, unit);
/*     */   }
/*     */ 
/*     */   
/*     */   public Future<?> terminationFuture() {
/*  96 */     return this.group.terminationFuture();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void shutdown() {
/* 102 */     this.group.shutdown();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Runnable> shutdownNow() {
/* 108 */     return this.group.shutdownNow();
/*     */   }
/*     */ 
/*     */   
/*     */   public EventExecutor next() {
/* 113 */     return newExecutor(this.group.next());
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<EventExecutor> iterator() {
/* 118 */     final Iterator<EventExecutor> itr = this.group.iterator();
/* 119 */     return new Iterator<EventExecutor>()
/*     */       {
/*     */         public boolean hasNext() {
/* 122 */           return itr.hasNext();
/*     */         }
/*     */ 
/*     */         
/*     */         public EventExecutor next() {
/* 127 */           return NonStickyEventExecutorGroup.this.newExecutor(itr.next());
/*     */         }
/*     */ 
/*     */         
/*     */         public void remove() {
/* 132 */           itr.remove();
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public Future<?> submit(Runnable task) {
/* 139 */     return this.group.submit(task);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> Future<T> submit(Runnable task, T result) {
/* 144 */     return this.group.submit(task, result);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> Future<T> submit(Callable<T> task) {
/* 149 */     return this.group.submit(task);
/*     */   }
/*     */ 
/*     */   
/*     */   public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
/* 154 */     return this.group.schedule(command, delay, unit);
/*     */   }
/*     */ 
/*     */   
/*     */   public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
/* 159 */     return this.group.schedule(callable, delay, unit);
/*     */   }
/*     */ 
/*     */   
/*     */   public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
/* 164 */     return this.group.scheduleAtFixedRate(command, initialDelay, period, unit);
/*     */   }
/*     */ 
/*     */   
/*     */   public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
/* 169 */     return this.group.scheduleWithFixedDelay(command, initialDelay, delay, unit);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isShutdown() {
/* 174 */     return this.group.isShutdown();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isTerminated() {
/* 179 */     return this.group.isTerminated();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
/* 184 */     return this.group.awaitTermination(timeout, unit);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
/* 190 */     return this.group.invokeAll(tasks);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
/* 196 */     return this.group.invokeAll(tasks, timeout, unit);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
/* 201 */     return this.group.invokeAny(tasks);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
/* 207 */     return this.group.invokeAny(tasks, timeout, unit);
/*     */   }
/*     */ 
/*     */   
/*     */   public void execute(Runnable command) {
/* 212 */     this.group.execute(command);
/*     */   }
/*     */   
/*     */   private static final class NonStickyOrderedEventExecutor
/*     */     extends AbstractEventExecutor implements Runnable, OrderedEventExecutor {
/*     */     private final EventExecutor executor;
/* 218 */     private final Queue<Runnable> tasks = PlatformDependent.newMpscQueue();
/*     */     
/*     */     private static final int NONE = 0;
/*     */     
/*     */     private static final int SUBMITTED = 1;
/*     */     private static final int RUNNING = 2;
/* 224 */     private final AtomicInteger state = new AtomicInteger();
/*     */     
/*     */     private final int maxTaskExecutePerRun;
/* 227 */     private final AtomicReference<Thread> executingThread = new AtomicReference<>();
/*     */     
/*     */     NonStickyOrderedEventExecutor(EventExecutor executor, int maxTaskExecutePerRun) {
/* 230 */       super(executor);
/* 231 */       this.executor = executor;
/* 232 */       this.maxTaskExecutePerRun = maxTaskExecutePerRun;
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
/*     */ 
/*     */ 
/*     */     
/*     */     public void run() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: getfield state : Ljava/util/concurrent/atomic/AtomicInteger;
/*     */       //   4: iconst_1
/*     */       //   5: iconst_2
/*     */       //   6: invokevirtual compareAndSet : (II)Z
/*     */       //   9: ifne -> 13
/*     */       //   12: return
/*     */       //   13: invokestatic currentThread : ()Ljava/lang/Thread;
/*     */       //   16: astore_1
/*     */       //   17: aload_0
/*     */       //   18: getfield executingThread : Ljava/util/concurrent/atomic/AtomicReference;
/*     */       //   21: aload_1
/*     */       //   22: invokevirtual set : (Ljava/lang/Object;)V
/*     */       //   25: iconst_0
/*     */       //   26: istore_2
/*     */       //   27: iload_2
/*     */       //   28: aload_0
/*     */       //   29: getfield maxTaskExecutePerRun : I
/*     */       //   32: if_icmpge -> 65
/*     */       //   35: aload_0
/*     */       //   36: getfield tasks : Ljava/util/Queue;
/*     */       //   39: invokeinterface poll : ()Ljava/lang/Object;
/*     */       //   44: checkcast java/lang/Runnable
/*     */       //   47: astore_3
/*     */       //   48: aload_3
/*     */       //   49: ifnonnull -> 55
/*     */       //   52: goto -> 65
/*     */       //   55: aload_3
/*     */       //   56: invokestatic safeExecute : (Ljava/lang/Runnable;)V
/*     */       //   59: iinc #2, 1
/*     */       //   62: goto -> 27
/*     */       //   65: iload_2
/*     */       //   66: aload_0
/*     */       //   67: getfield maxTaskExecutePerRun : I
/*     */       //   70: if_icmpne -> 122
/*     */       //   73: aload_0
/*     */       //   74: getfield state : Ljava/util/concurrent/atomic/AtomicInteger;
/*     */       //   77: iconst_1
/*     */       //   78: invokevirtual set : (I)V
/*     */       //   81: aload_0
/*     */       //   82: getfield executingThread : Ljava/util/concurrent/atomic/AtomicReference;
/*     */       //   85: aload_1
/*     */       //   86: aconst_null
/*     */       //   87: invokevirtual compareAndSet : (Ljava/lang/Object;Ljava/lang/Object;)Z
/*     */       //   90: pop
/*     */       //   91: aload_0
/*     */       //   92: getfield executor : Lio/netty/util/concurrent/EventExecutor;
/*     */       //   95: aload_0
/*     */       //   96: invokeinterface execute : (Ljava/lang/Runnable;)V
/*     */       //   101: return
/*     */       //   102: astore_3
/*     */       //   103: aload_0
/*     */       //   104: getfield executingThread : Ljava/util/concurrent/atomic/AtomicReference;
/*     */       //   107: aload_1
/*     */       //   108: invokevirtual set : (Ljava/lang/Object;)V
/*     */       //   111: aload_0
/*     */       //   112: getfield state : Ljava/util/concurrent/atomic/AtomicInteger;
/*     */       //   115: iconst_2
/*     */       //   116: invokevirtual set : (I)V
/*     */       //   119: goto -> 271
/*     */       //   122: aload_0
/*     */       //   123: getfield state : Ljava/util/concurrent/atomic/AtomicInteger;
/*     */       //   126: iconst_0
/*     */       //   127: invokevirtual set : (I)V
/*     */       //   130: aload_0
/*     */       //   131: getfield tasks : Ljava/util/Queue;
/*     */       //   134: invokeinterface isEmpty : ()Z
/*     */       //   139: ifne -> 154
/*     */       //   142: aload_0
/*     */       //   143: getfield state : Ljava/util/concurrent/atomic/AtomicInteger;
/*     */       //   146: iconst_0
/*     */       //   147: iconst_2
/*     */       //   148: invokevirtual compareAndSet : (II)Z
/*     */       //   151: ifne -> 271
/*     */       //   154: aload_0
/*     */       //   155: getfield executingThread : Ljava/util/concurrent/atomic/AtomicReference;
/*     */       //   158: aload_1
/*     */       //   159: aconst_null
/*     */       //   160: invokevirtual compareAndSet : (Ljava/lang/Object;Ljava/lang/Object;)Z
/*     */       //   163: pop
/*     */       //   164: return
/*     */       //   165: astore #4
/*     */       //   167: iload_2
/*     */       //   168: aload_0
/*     */       //   169: getfield maxTaskExecutePerRun : I
/*     */       //   172: if_icmpne -> 225
/*     */       //   175: aload_0
/*     */       //   176: getfield state : Ljava/util/concurrent/atomic/AtomicInteger;
/*     */       //   179: iconst_1
/*     */       //   180: invokevirtual set : (I)V
/*     */       //   183: aload_0
/*     */       //   184: getfield executingThread : Ljava/util/concurrent/atomic/AtomicReference;
/*     */       //   187: aload_1
/*     */       //   188: aconst_null
/*     */       //   189: invokevirtual compareAndSet : (Ljava/lang/Object;Ljava/lang/Object;)Z
/*     */       //   192: pop
/*     */       //   193: aload_0
/*     */       //   194: getfield executor : Lio/netty/util/concurrent/EventExecutor;
/*     */       //   197: aload_0
/*     */       //   198: invokeinterface execute : (Ljava/lang/Runnable;)V
/*     */       //   203: return
/*     */       //   204: astore #5
/*     */       //   206: aload_0
/*     */       //   207: getfield executingThread : Ljava/util/concurrent/atomic/AtomicReference;
/*     */       //   210: aload_1
/*     */       //   211: invokevirtual set : (Ljava/lang/Object;)V
/*     */       //   214: aload_0
/*     */       //   215: getfield state : Ljava/util/concurrent/atomic/AtomicInteger;
/*     */       //   218: iconst_2
/*     */       //   219: invokevirtual set : (I)V
/*     */       //   222: goto -> 268
/*     */       //   225: aload_0
/*     */       //   226: getfield state : Ljava/util/concurrent/atomic/AtomicInteger;
/*     */       //   229: iconst_0
/*     */       //   230: invokevirtual set : (I)V
/*     */       //   233: aload_0
/*     */       //   234: getfield tasks : Ljava/util/Queue;
/*     */       //   237: invokeinterface isEmpty : ()Z
/*     */       //   242: ifne -> 257
/*     */       //   245: aload_0
/*     */       //   246: getfield state : Ljava/util/concurrent/atomic/AtomicInteger;
/*     */       //   249: iconst_0
/*     */       //   250: iconst_2
/*     */       //   251: invokevirtual compareAndSet : (II)Z
/*     */       //   254: ifne -> 268
/*     */       //   257: aload_0
/*     */       //   258: getfield executingThread : Ljava/util/concurrent/atomic/AtomicReference;
/*     */       //   261: aload_1
/*     */       //   262: aconst_null
/*     */       //   263: invokevirtual compareAndSet : (Ljava/lang/Object;Ljava/lang/Object;)Z
/*     */       //   266: pop
/*     */       //   267: return
/*     */       //   268: aload #4
/*     */       //   270: athrow
/*     */       //   271: goto -> 25
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #237	-> 0
/*     */       //   #238	-> 12
/*     */       //   #240	-> 13
/*     */       //   #241	-> 17
/*     */       //   #243	-> 25
/*     */       //   #245	-> 27
/*     */       //   #246	-> 35
/*     */       //   #247	-> 48
/*     */       //   #248	-> 52
/*     */       //   #250	-> 55
/*     */       //   #245	-> 59
/*     */       //   #253	-> 65
/*     */       //   #255	-> 73
/*     */       //   #257	-> 81
/*     */       //   #258	-> 91
/*     */       //   #259	-> 101
/*     */       //   #260	-> 102
/*     */       //   #262	-> 103
/*     */       //   #264	-> 111
/*     */       //   #268	-> 119
/*     */       //   #270	-> 122
/*     */       //   #286	-> 130
/*     */       //   #288	-> 154
/*     */       //   #289	-> 164
/*     */       //   #253	-> 165
/*     */       //   #255	-> 175
/*     */       //   #257	-> 183
/*     */       //   #258	-> 193
/*     */       //   #259	-> 203
/*     */       //   #260	-> 204
/*     */       //   #262	-> 206
/*     */       //   #264	-> 214
/*     */       //   #268	-> 222
/*     */       //   #270	-> 225
/*     */       //   #286	-> 233
/*     */       //   #288	-> 257
/*     */       //   #289	-> 267
/*     */       //   #292	-> 268
/*     */       //   #293	-> 271
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   48	11	3	task	Ljava/lang/Runnable;
/*     */       //   103	16	3	ignore	Ljava/lang/Throwable;
/*     */       //   206	16	5	ignore	Ljava/lang/Throwable;
/*     */       //   27	244	2	i	I
/*     */       //   0	274	0	this	Lio/netty/util/concurrent/NonStickyEventExecutorGroup$NonStickyOrderedEventExecutor;
/*     */       //   17	257	1	current	Ljava/lang/Thread;
/*     */       // Exception table:
/*     */       //   from	to	target	type
/*     */       //   27	65	165	finally
/*     */       //   73	101	102	java/lang/Throwable
/*     */       //   165	167	165	finally
/*     */       //   175	203	204	java/lang/Throwable
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
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean inEventLoop(Thread thread) {
/* 298 */       return (this.executingThread.get() == thread);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isShuttingDown() {
/* 303 */       return this.executor.isShutdown();
/*     */     }
/*     */ 
/*     */     
/*     */     public Future<?> shutdownGracefully(long quietPeriod, long timeout, TimeUnit unit) {
/* 308 */       return this.executor.shutdownGracefully(quietPeriod, timeout, unit);
/*     */     }
/*     */ 
/*     */     
/*     */     public Future<?> terminationFuture() {
/* 313 */       return this.executor.terminationFuture();
/*     */     }
/*     */ 
/*     */     
/*     */     public void shutdown() {
/* 318 */       this.executor.shutdown();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isShutdown() {
/* 323 */       return this.executor.isShutdown();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isTerminated() {
/* 328 */       return this.executor.isTerminated();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
/* 333 */       return this.executor.awaitTermination(timeout, unit);
/*     */     }
/*     */ 
/*     */     
/*     */     public void execute(Runnable command) {
/* 338 */       if (!this.tasks.offer(command)) {
/* 339 */         throw new RejectedExecutionException();
/*     */       }
/* 341 */       if (this.state.compareAndSet(0, 1))
/*     */       {
/*     */         
/* 344 */         this.executor.execute(this);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\concurrent\NonStickyEventExecutorGroup.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */