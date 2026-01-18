package io.sentry.logger;

import io.sentry.SentryLogEvent;
import org.jetbrains.annotations.NotNull;

public interface ILoggerBatchProcessor {
  void add(@NotNull SentryLogEvent paramSentryLogEvent);
  
  void close(boolean paramBoolean);
  
  void flush(long paramLong);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\logger\ILoggerBatchProcessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */