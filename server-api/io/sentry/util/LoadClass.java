/*    */ package io.sentry.util;
/*    */ 
/*    */ import io.sentry.ILogger;
/*    */ import io.sentry.SentryLevel;
/*    */ import io.sentry.SentryOptions;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
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
/*    */ public class LoadClass
/*    */ {
/*    */   @Nullable
/*    */   public Class<?> loadClass(@NotNull String clazz, @Nullable ILogger logger) {
/*    */     try {
/* 23 */       return Class.forName(clazz);
/* 24 */     } catch (ClassNotFoundException e) {
/* 25 */       if (logger != null) {
/* 26 */         logger.log(SentryLevel.INFO, "Class not available: " + clazz, new Object[0]);
/*    */       }
/* 28 */     } catch (UnsatisfiedLinkError e) {
/* 29 */       if (logger != null) {
/* 30 */         logger.log(SentryLevel.ERROR, "Failed to load (UnsatisfiedLinkError) " + clazz, e);
/*    */       }
/* 32 */     } catch (Throwable e) {
/* 33 */       if (logger != null) {
/* 34 */         logger.log(SentryLevel.ERROR, "Failed to initialize " + clazz, e);
/*    */       }
/*    */     } 
/* 37 */     return null;
/*    */   }
/*    */   
/*    */   public boolean isClassAvailable(@NotNull String clazz, @Nullable ILogger logger) {
/* 41 */     return (loadClass(clazz, logger) != null);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isClassAvailable(@NotNull String clazz, @Nullable SentryOptions options) {
/* 46 */     return isClassAvailable(clazz, (options != null) ? options.getLogger() : null);
/*    */   }
/*    */ 
/*    */   
/*    */   public LazyEvaluator<Boolean> isClassAvailableLazy(@NotNull String clazz, @Nullable ILogger logger) {
/* 51 */     return new LazyEvaluator<>(() -> Boolean.valueOf(isClassAvailable(clazz, logger)));
/*    */   }
/*    */ 
/*    */   
/*    */   public LazyEvaluator<Boolean> isClassAvailableLazy(@NotNull String clazz, @Nullable SentryOptions options) {
/* 56 */     return new LazyEvaluator<>(() -> Boolean.valueOf(isClassAvailable(clazz, options)));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentr\\util\LoadClass.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */