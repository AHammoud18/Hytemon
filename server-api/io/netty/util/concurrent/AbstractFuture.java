/*    */ package io.netty.util.concurrent;
/*    */ 
/*    */ import java.util.Locale;
/*    */ import java.util.concurrent.CancellationException;
/*    */ import java.util.concurrent.ExecutionException;
/*    */ import java.util.concurrent.TimeUnit;
/*    */ import java.util.concurrent.TimeoutException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class AbstractFuture<V>
/*    */   implements Future<V>
/*    */ {
/*    */   public V get() throws InterruptedException, ExecutionException {
/* 33 */     await();
/*    */     
/* 35 */     Throwable cause = cause();
/* 36 */     if (cause == null) {
/* 37 */       return getNow();
/*    */     }
/* 39 */     if (cause instanceof CancellationException) {
/* 40 */       throw (CancellationException)cause;
/*    */     }
/* 42 */     throw new ExecutionException(cause);
/*    */   }
/*    */ 
/*    */   
/*    */   public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
/* 47 */     if (await(timeout, unit)) {
/* 48 */       Throwable cause = cause();
/* 49 */       if (cause == null) {
/* 50 */         return getNow();
/*    */       }
/* 52 */       if (cause instanceof CancellationException) {
/* 53 */         throw (CancellationException)cause;
/*    */       }
/* 55 */       throw new ExecutionException(cause);
/*    */     } 
/* 57 */     throw new TimeoutException("timeout after " + timeout + " " + unit.name().toLowerCase(Locale.ENGLISH));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\concurrent\AbstractFuture.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */