package io.sentry;

import io.sentry.protocol.SentryId;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

@Internal
public interface IContinuousProfiler {
  boolean isRunning();
  
  void startProfiler(@NotNull ProfileLifecycle paramProfileLifecycle, @NotNull TracesSampler paramTracesSampler);
  
  void stopProfiler(@NotNull ProfileLifecycle paramProfileLifecycle);
  
  void close(boolean paramBoolean);
  
  void reevaluateSampling();
  
  @NotNull
  SentryId getProfilerId();
  
  @NotNull
  SentryId getChunkId();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\IContinuousProfiler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */