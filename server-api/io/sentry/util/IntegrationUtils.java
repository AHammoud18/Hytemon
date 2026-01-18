/*    */ package io.sentry.util;
/*    */ 
/*    */ import io.sentry.SentryIntegrationPackageStorage;
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ @Internal
/*    */ public final class IntegrationUtils
/*    */ {
/*    */   public static void addIntegrationToSdkVersion(@NotNull String name) {
/* 11 */     SentryIntegrationPackageStorage.getInstance().addIntegration(name);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentr\\util\IntegrationUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */