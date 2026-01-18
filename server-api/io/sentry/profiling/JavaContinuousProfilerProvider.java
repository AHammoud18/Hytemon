package io.sentry.profiling;

import io.sentry.IContinuousProfiler;
import io.sentry.ILogger;
import io.sentry.ISentryExecutorService;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

@Internal
public interface JavaContinuousProfilerProvider {
  @NotNull
  IContinuousProfiler getContinuousProfiler(ILogger paramILogger, String paramString, int paramInt, ISentryExecutorService paramISentryExecutorService);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\profiling\JavaContinuousProfilerProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */