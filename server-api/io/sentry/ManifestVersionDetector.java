/*    */ package io.sentry;
/*    */ 
/*    */ import io.sentry.internal.ManifestVersionReader;
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ @Internal
/*    */ public final class ManifestVersionDetector implements IVersionDetector {
/*    */   @NotNull
/*    */   private final SentryOptions options;
/*    */   
/*    */   public ManifestVersionDetector(@NotNull SentryOptions options) {
/* 13 */     this.options = options;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean checkForMixedVersions() {
/* 18 */     ManifestVersionReader.getInstance().readManifestFiles();
/* 19 */     return SentryIntegrationPackageStorage.getInstance()
/* 20 */       .checkForMixedVersions(this.options.getFatalLogger());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\ManifestVersionDetector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */