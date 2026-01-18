/*    */ package io.sentry.util;
/*    */ 
/*    */ import io.sentry.ISentryLifecycleToken;
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Internal
/*    */ public final class LazyEvaluator<T>
/*    */ {
/*    */   @Nullable
/* 15 */   private volatile T value = null; @NotNull
/*    */   private final Evaluator<T> evaluator; @NotNull
/* 17 */   private final AutoClosableReentrantLock lock = new AutoClosableReentrantLock();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public LazyEvaluator(@NotNull Evaluator<T> evaluator) {
/* 26 */     this.evaluator = evaluator;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public T getValue() {
/* 36 */     if (this.value == null) {
/* 37 */       ISentryLifecycleToken ignored = this.lock.acquire(); 
/* 38 */       try { if (this.value == null) {
/* 39 */           this.value = this.evaluator.evaluate();
/*    */         }
/* 41 */         if (ignored != null) ignored.close();  } catch (Throwable throwable) { if (ignored != null)
/*    */           try { ignored.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*    */             throw throwable; }
/*    */     
/* 45 */     }  return this.value;
/*    */   }
/*    */   
/*    */   public void setValue(@Nullable T value) {
/* 49 */     ISentryLifecycleToken ignored = this.lock.acquire(); try {
/* 50 */       this.value = value;
/* 51 */       if (ignored != null) ignored.close(); 
/*    */     } catch (Throwable throwable) {
/*    */       if (ignored != null)
/*    */         try {
/*    */           ignored.close();
/*    */         } catch (Throwable throwable1) {
/*    */           throwable.addSuppressed(throwable1);
/*    */         }   throw throwable;
/* 59 */     }  } public void resetValue() { ISentryLifecycleToken ignored = this.lock.acquire(); try {
/* 60 */       this.value = null;
/* 61 */       if (ignored != null) ignored.close(); 
/*    */     } catch (Throwable throwable) {
/*    */       if (ignored != null)
/*    */         try {
/*    */           ignored.close();
/*    */         } catch (Throwable throwable1) {
/*    */           throwable.addSuppressed(throwable1);
/*    */         }  
/*    */       throw throwable;
/*    */     }  }
/*    */ 
/*    */   
/*    */   public static interface Evaluator<T> {
/*    */     @NotNull
/*    */     T evaluate();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentr\\util\LazyEvaluator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */