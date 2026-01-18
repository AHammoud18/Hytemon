package io.sentry.logger;

import io.sentry.SentryClient;
import io.sentry.SentryOptions;
import org.jetbrains.annotations.NotNull;

public interface ILoggerBatchProcessorFactory {
  @NotNull
  ILoggerBatchProcessor create(@NotNull SentryOptions paramSentryOptions, @NotNull SentryClient paramSentryClient);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\logger\ILoggerBatchProcessorFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */