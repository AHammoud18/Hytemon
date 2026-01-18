/*    */ package io.sentry;
/*    */ 
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ @Internal
/*    */ public final class DefaultVersionDetector implements IVersionDetector {
/*    */   @NotNull
/*    */   private final SentryOptions options;
/*    */   
/*    */   public DefaultVersionDetector(@NotNull SentryOptions options) {
/* 12 */     this.options = options;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean checkForMixedVersions() {
/* 17 */     return SentryIntegrationPackageStorage.getInstance()
/* 18 */       .checkForMixedVersions(this.options.getFatalLogger());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\DefaultVersionDetector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */