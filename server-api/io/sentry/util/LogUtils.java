/*    */ package io.sentry.util;
/*    */ 
/*    */ import io.sentry.ILogger;
/*    */ import io.sentry.SentryLevel;
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Internal
/*    */ public final class LogUtils
/*    */ {
/*    */   public static void logNotInstanceOf(@NotNull Class<?> expectedClass, @Nullable Object sentrySdkHint, @NotNull ILogger logger) {
/* 16 */     logger.log(SentryLevel.DEBUG, "%s is not %s", new Object[] {
/*    */ 
/*    */           
/* 19 */           (sentrySdkHint != null) ? sentrySdkHint.getClass().getCanonicalName() : "Hint", expectedClass
/* 20 */           .getCanonicalName()
/*    */         });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentr\\util\LogUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */