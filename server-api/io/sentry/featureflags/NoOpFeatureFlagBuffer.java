/*    */ package io.sentry.featureflags;
/*    */ 
/*    */ import io.sentry.protocol.FeatureFlags;
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ @Internal
/*    */ public final class NoOpFeatureFlagBuffer implements IFeatureFlagBuffer {
/* 10 */   private static final NoOpFeatureFlagBuffer instance = new NoOpFeatureFlagBuffer();
/*    */   
/*    */   public static NoOpFeatureFlagBuffer getInstance() {
/* 13 */     return instance;
/*    */   }
/*    */ 
/*    */   
/*    */   public void add(@Nullable String flag, @Nullable Boolean result) {}
/*    */   
/*    */   @Nullable
/*    */   public FeatureFlags getFeatureFlags() {
/* 21 */     return null;
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public IFeatureFlagBuffer clone() {
/* 26 */     return instance;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\featureflags\NoOpFeatureFlagBuffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */