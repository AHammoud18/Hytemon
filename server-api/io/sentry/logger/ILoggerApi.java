package io.sentry.logger;

import io.sentry.SentryDate;
import io.sentry.SentryLogLevel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ILoggerApi {
  void trace(@Nullable String paramString, @Nullable Object... paramVarArgs);
  
  void debug(@Nullable String paramString, @Nullable Object... paramVarArgs);
  
  void info(@Nullable String paramString, @Nullable Object... paramVarArgs);
  
  void warn(@Nullable String paramString, @Nullable Object... paramVarArgs);
  
  void error(@Nullable String paramString, @Nullable Object... paramVarArgs);
  
  void fatal(@Nullable String paramString, @Nullable Object... paramVarArgs);
  
  void log(@NotNull SentryLogLevel paramSentryLogLevel, @Nullable String paramString, @Nullable Object... paramVarArgs);
  
  void log(@NotNull SentryLogLevel paramSentryLogLevel, @Nullable SentryDate paramSentryDate, @Nullable String paramString, @Nullable Object... paramVarArgs);
  
  void log(@NotNull SentryLogLevel paramSentryLogLevel, @NotNull SentryLogParameters paramSentryLogParameters, @Nullable String paramString, @Nullable Object... paramVarArgs);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\logger\ILoggerApi.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */