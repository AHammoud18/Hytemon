/*    */ package io.sentry;
/*    */ 
/*    */ import java.util.concurrent.Callable;
/*    */ import java.util.function.Supplier;
/*    */ import org.jetbrains.annotations.NotNull;
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
/*    */ 
/*    */ 
/*    */ public final class SentryWrapper
/*    */ {
/*    */   public static <U> Callable<U> wrapCallable(@NotNull Callable<U> callable) {
/* 32 */     IScopes newScopes = Sentry.getCurrentScopes().forkedScopes("SentryWrapper.wrapCallable");
/*    */     
/* 34 */     return () -> { ISentryLifecycleToken ignored = newScopes.makeCurrent(); try { Object object = callable.call(); if (ignored != null)
/* 35 */             ignored.close();  return object; } catch (Throwable throwable) { if (ignored != null) try { ignored.close(); } catch (Throwable throwable1)
/*    */             { throwable.addSuppressed(throwable1); }
/*    */           
/*    */           
/*    */           throw throwable; }
/*    */       
/*    */       };
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static <U> Supplier<U> wrapSupplier(@NotNull Supplier<U> supplier) {
/* 52 */     IScopes newScopes = Sentry.forkedScopes("SentryWrapper.wrapSupplier");
/*    */     
/* 54 */     return () -> { ISentryLifecycleToken ignore = newScopes.makeCurrent(); try { Object object = supplier.get(); if (ignore != null)
/* 55 */             ignore.close();  return object; } catch (Throwable throwable) { if (ignore != null) try { ignore.close(); } catch (Throwable throwable1)
/*    */             { throwable.addSuppressed(throwable1); }
/*    */           
/*    */           
/*    */           throw throwable; }
/*    */       
/*    */       };
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Runnable wrapRunnable(@NotNull Runnable runnable) {
/* 71 */     IScopes newScopes = Sentry.forkedScopes("SentryWrapper.wrapRunnable");
/*    */     
/* 73 */     return () -> { ISentryLifecycleToken ignore = newScopes.makeCurrent(); try { runnable.run(); if (ignore != null)
/* 74 */             ignore.close();  } catch (Throwable throwable) { if (ignore != null) try { ignore.close(); } catch (Throwable throwable1)
/*    */             { throwable.addSuppressed(throwable1); }
/*    */              
/*    */           throw throwable; }
/*    */       
/*    */       };
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\SentryWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */