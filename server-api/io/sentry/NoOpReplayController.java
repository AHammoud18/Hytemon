/*    */ package io.sentry;
/*    */ 
/*    */ import io.sentry.protocol.SentryId;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ public final class NoOpReplayController
/*    */   implements ReplayController {
/*  9 */   private static final NoOpReplayController instance = new NoOpReplayController();
/*    */   
/*    */   public static NoOpReplayController getInstance() {
/* 12 */     return instance;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void start() {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void stop() {}
/*    */ 
/*    */   
/*    */   public void pause() {}
/*    */ 
/*    */   
/*    */   public void resume() {}
/*    */ 
/*    */   
/*    */   public boolean isRecording() {
/* 31 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public void captureReplay(@Nullable Boolean isTerminating) {}
/*    */   
/*    */   @NotNull
/*    */   public SentryId getReplayId() {
/* 39 */     return SentryId.EMPTY_ID;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setBreadcrumbConverter(@NotNull ReplayBreadcrumbConverter converter) {}
/*    */   
/*    */   @NotNull
/*    */   public ReplayBreadcrumbConverter getBreadcrumbConverter() {
/* 47 */     return NoOpReplayBreadcrumbConverter.getInstance();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isDebugMaskingOverlayEnabled() {
/* 52 */     return false;
/*    */   }
/*    */   
/*    */   public void enableDebugMaskingOverlay() {}
/*    */   
/*    */   public void disableDebugMaskingOverlay() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\NoOpReplayController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */