package io.sentry;

import java.util.List;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface CompositePerformanceCollector {
  void start(@NotNull ITransaction paramITransaction);
  
  void start(@NotNull String paramString);
  
  void onSpanStarted(@NotNull ISpan paramISpan);
  
  void onSpanFinished(@NotNull ISpan paramISpan);
  
  @Nullable
  List<PerformanceCollectionData> stop(@NotNull ITransaction paramITransaction);
  
  @Nullable
  List<PerformanceCollectionData> stop(@NotNull String paramString);
  
  @Internal
  void close();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\CompositePerformanceCollector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */