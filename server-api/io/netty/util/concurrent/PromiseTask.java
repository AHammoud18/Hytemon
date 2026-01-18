/*     */ package io.netty.util.concurrent;
/*     */ 
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.RunnableFuture;
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
/*     */ class PromiseTask<V>
/*     */   extends DefaultPromise<V>
/*     */   implements RunnableFuture<V>
/*     */ {
/*     */   private static final class RunnableAdapter<T>
/*     */     implements Callable<T>
/*     */   {
/*     */     final Runnable task;
/*     */     final T result;
/*     */     
/*     */     RunnableAdapter(Runnable task, T result) {
/*  28 */       this.task = task;
/*  29 */       this.result = result;
/*     */     }
/*     */ 
/*     */     
/*     */     public T call() {
/*  34 */       this.task.run();
/*  35 */       return this.result;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/*  40 */       return "Callable(task: " + this.task + ", result: " + this.result + ')';
/*     */     }
/*     */   }
/*     */   
/*  44 */   private static final Runnable COMPLETED = new SentinelRunnable("COMPLETED");
/*  45 */   private static final Runnable CANCELLED = new SentinelRunnable("CANCELLED");
/*  46 */   private static final Runnable FAILED = new SentinelRunnable("FAILED");
/*     */   private Object task;
/*     */   
/*     */   private static class SentinelRunnable
/*     */     implements Runnable {
/*     */     SentinelRunnable(String name) {
/*  52 */       this.name = name;
/*     */     }
/*     */     
/*     */     private final String name;
/*     */     
/*     */     public void run() {}
/*     */     
/*     */     public String toString() {
/*  60 */       return this.name;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   PromiseTask(EventExecutor executor, Runnable runnable, V result) {
/*  68 */     super(executor);
/*  69 */     this.task = (result == null) ? runnable : new RunnableAdapter<>(runnable, result);
/*     */   }
/*     */   
/*     */   PromiseTask(EventExecutor executor, Runnable runnable) {
/*  73 */     super(executor);
/*  74 */     this.task = runnable;
/*     */   }
/*     */   
/*     */   PromiseTask(EventExecutor executor, Callable<V> callable) {
/*  78 */     super(executor);
/*  79 */     this.task = callable;
/*     */   }
/*     */ 
/*     */   
/*     */   public final int hashCode() {
/*  84 */     return System.identityHashCode(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean equals(Object obj) {
/*  89 */     return (this == obj);
/*     */   }
/*     */ 
/*     */   
/*     */   V runTask() throws Throwable {
/*  94 */     Object task = this.task;
/*  95 */     if (task instanceof Callable) {
/*  96 */       return ((Callable<V>)task).call();
/*     */     }
/*  98 */     ((Runnable)task).run();
/*  99 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void run() {
/*     */     try {
/* 105 */       if (setUncancellableInternal()) {
/* 106 */         V result = runTask();
/* 107 */         setSuccessInternal(result);
/*     */       } 
/* 109 */     } catch (Throwable e) {
/* 110 */       setFailureInternal(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean clearTaskAfterCompletion(boolean done, Runnable result) {
/* 115 */     if (done)
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 120 */       this.task = result;
/*     */     }
/* 122 */     return done;
/*     */   }
/*     */ 
/*     */   
/*     */   public final Promise<V> setFailure(Throwable cause) {
/* 127 */     throw new IllegalStateException();
/*     */   }
/*     */   
/*     */   protected final Promise<V> setFailureInternal(Throwable cause) {
/* 131 */     super.setFailure(cause);
/* 132 */     clearTaskAfterCompletion(true, FAILED);
/* 133 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean tryFailure(Throwable cause) {
/* 138 */     return false;
/*     */   }
/*     */   
/*     */   protected final boolean tryFailureInternal(Throwable cause) {
/* 142 */     return clearTaskAfterCompletion(super.tryFailure(cause), FAILED);
/*     */   }
/*     */ 
/*     */   
/*     */   public final Promise<V> setSuccess(V result) {
/* 147 */     throw new IllegalStateException();
/*     */   }
/*     */   
/*     */   protected final Promise<V> setSuccessInternal(V result) {
/* 151 */     super.setSuccess(result);
/* 152 */     clearTaskAfterCompletion(true, COMPLETED);
/* 153 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean trySuccess(V result) {
/* 158 */     return false;
/*     */   }
/*     */   
/*     */   protected final boolean trySuccessInternal(V result) {
/* 162 */     return clearTaskAfterCompletion(super.trySuccess(result), COMPLETED);
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean setUncancellable() {
/* 167 */     throw new IllegalStateException();
/*     */   }
/*     */   
/*     */   protected final boolean setUncancellableInternal() {
/* 171 */     return super.setUncancellable();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean cancel(boolean mayInterruptIfRunning) {
/* 176 */     return clearTaskAfterCompletion(super.cancel(mayInterruptIfRunning), CANCELLED);
/*     */   }
/*     */ 
/*     */   
/*     */   protected StringBuilder toStringBuilder() {
/* 181 */     StringBuilder buf = super.toStringBuilder();
/* 182 */     buf.setCharAt(buf.length() - 1, ',');
/*     */     
/* 184 */     return buf.append(" task: ")
/* 185 */       .append(this.task)
/* 186 */       .append(')');
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\concurrent\PromiseTask.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */