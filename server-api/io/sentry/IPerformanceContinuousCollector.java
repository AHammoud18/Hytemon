package io.sentry;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

@Internal
public interface IPerformanceContinuousCollector extends IPerformanceCollector {
  void onSpanStarted(@NotNull ISpan paramISpan);
  
  void onSpanFinished(@NotNull ISpan paramISpan);
  
  void clear();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\IPerformanceContinuousCollector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */