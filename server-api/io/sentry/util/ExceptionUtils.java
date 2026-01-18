/*    */ package io.sentry.util;
/*    */ 
/*    */ import java.util.Set;
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Internal
/*    */ public final class ExceptionUtils
/*    */ {
/*    */   @NotNull
/*    */   public static Throwable findRootCause(@NotNull Throwable throwable) {
/* 17 */     Objects.requireNonNull(throwable, "throwable cannot be null");
/* 18 */     Throwable rootCause = throwable;
/* 19 */     while (rootCause.getCause() != null && rootCause.getCause() != rootCause) {
/* 20 */       rootCause = rootCause.getCause();
/*    */     }
/* 22 */     return rootCause;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Internal
/*    */   public static boolean isIgnored(@NotNull Set<Class<? extends Throwable>> ignoredExceptionsForType, @NotNull Throwable throwable) {
/* 30 */     return ignoredExceptionsForType.contains(throwable.getClass());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentr\\util\ExceptionUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */