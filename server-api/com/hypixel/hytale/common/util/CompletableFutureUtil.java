/*     */ package com.hypixel.hytale.common.util;
/*     */ 
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.function.Function;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class CompletableFutureUtil {
/*     */   @Nonnull
/*     */   public static <T> CompletableFuture<T> whenComplete(@Nonnull CompletableFuture<T> future, @Nonnull CompletableFuture<T> callee) {
/*  14 */     return future.whenComplete((result, throwable) -> {
/*     */           if (throwable != null) {
/*     */             callee.completeExceptionally(throwable);
/*     */           } else {
/*     */             callee.complete(result);
/*     */           } 
/*     */         });
/*     */   } public static final Function<Throwable, ?> fn;
/*     */   static {
/*  23 */     fn = (throwable -> {
/*     */         if (!(throwable instanceof TailedRuntimeException))
/*     */           ((HytaleLogger.Api)HytaleLogger.getLogger().at(Level.SEVERE).withCause(throwable)).log("Unhandled exception! " + String.valueOf(Thread.currentThread())); 
/*     */         throw new TailedRuntimeException(throwable);
/*     */       });
/*     */   }
/*     */   
/*     */   public static boolean isCanceled(Throwable throwable) {
/*  31 */     return (throwable instanceof java.util.concurrent.CancellationException || (throwable instanceof java.util.concurrent.CompletionException && throwable
/*  32 */       .getCause() != null && throwable.getCause() != throwable && isCanceled(throwable.getCause())));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static <T> CompletableFuture<T> _catch(@Nonnull CompletableFuture<T> future) {
/*  38 */     return future.exceptionally((Function)fn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static <T> CompletableFuture<T> completionCanceled() {
/*  50 */     CompletableFuture<T> out = new CompletableFuture<>();
/*  51 */     out.cancel(false);
/*  52 */     return out;
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
/*     */   public static void joinWithProgress(@Nonnull List<CompletableFuture<?>> list, @Nonnull ProgressConsumer callback, int millisSleep, int millisProgress) throws InterruptedException {
/*  70 */     CompletableFuture<?> all = CompletableFuture.allOf((CompletableFuture<?>[])list.toArray(x$0 -> new CompletableFuture[x$0]));
/*     */     
/*  72 */     long last = System.nanoTime();
/*     */ 
/*     */     
/*  75 */     long nanosProgress = TimeUnit.MILLISECONDS.toNanos(millisProgress);
/*     */ 
/*     */     
/*  78 */     int listSize = list.size();
/*     */ 
/*     */     
/*  81 */     while (!all.isDone()) {
/*     */ 
/*     */       
/*  84 */       Thread.sleep(millisSleep);
/*     */       
/*     */       long now;
/*  87 */       if (last + nanosProgress < (now = System.nanoTime())) {
/*  88 */         last = now;
/*     */ 
/*     */         
/*  91 */         int done = 0;
/*  92 */         for (CompletableFuture<?> c : list) {
/*  93 */           if (c.isDone()) {
/*  94 */             done++;
/*     */           }
/*     */         } 
/*     */ 
/*     */         
/*  99 */         if (done < listSize)
/*     */         {
/*     */           
/* 102 */           callback.accept(done / listSize, done, listSize);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 108 */     callback.accept(1.0D, listSize, listSize);
/*     */ 
/*     */     
/* 111 */     all.join();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface ProgressConsumer
/*     */   {
/*     */     void accept(double param1Double, int param1Int1, int param1Int2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static class TailedRuntimeException
/*     */     extends RuntimeException
/*     */   {
/*     */     public TailedRuntimeException(Throwable cause) {
/* 128 */       super(cause);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\commo\\util\CompletableFutureUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */