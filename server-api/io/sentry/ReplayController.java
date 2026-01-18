package io.sentry;

import io.sentry.protocol.SentryId;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Internal
public interface ReplayController extends IReplayApi {
  void start();
  
  void stop();
  
  void pause();
  
  void resume();
  
  boolean isRecording();
  
  void captureReplay(@Nullable Boolean paramBoolean);
  
  @NotNull
  SentryId getReplayId();
  
  void setBreadcrumbConverter(@NotNull ReplayBreadcrumbConverter paramReplayBreadcrumbConverter);
  
  @NotNull
  ReplayBreadcrumbConverter getBreadcrumbConverter();
  
  boolean isDebugMaskingOverlayEnabled();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\ReplayController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */