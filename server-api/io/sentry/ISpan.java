package io.sentry;

import io.sentry.protocol.Contexts;
import java.util.List;
import org.jetbrains.annotations.ApiStatus.Experimental;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ISpan {
  @NotNull
  ISpan startChild(@NotNull String paramString);
  
  @Internal
  @NotNull
  ISpan startChild(@NotNull String paramString1, @Nullable String paramString2, @NotNull SpanOptions paramSpanOptions);
  
  @Internal
  @NotNull
  ISpan startChild(@NotNull SpanContext paramSpanContext, @NotNull SpanOptions paramSpanOptions);
  
  @Internal
  @NotNull
  ISpan startChild(@NotNull String paramString1, @Nullable String paramString2, @Nullable SentryDate paramSentryDate, @NotNull Instrumenter paramInstrumenter);
  
  @Internal
  @NotNull
  ISpan startChild(@NotNull String paramString1, @Nullable String paramString2, @Nullable SentryDate paramSentryDate, @NotNull Instrumenter paramInstrumenter, @NotNull SpanOptions paramSpanOptions);
  
  @NotNull
  ISpan startChild(@NotNull String paramString1, @Nullable String paramString2);
  
  @NotNull
  SentryTraceHeader toSentryTrace();
  
  @Nullable
  @Experimental
  TraceContext traceContext();
  
  @Nullable
  @Experimental
  BaggageHeader toBaggageHeader(@Nullable List<String> paramList);
  
  void finish();
  
  void finish(@Nullable SpanStatus paramSpanStatus);
  
  void finish(@Nullable SpanStatus paramSpanStatus, @Nullable SentryDate paramSentryDate);
  
  void setOperation(@NotNull String paramString);
  
  @NotNull
  String getOperation();
  
  void setDescription(@Nullable String paramString);
  
  @Nullable
  String getDescription();
  
  void setStatus(@Nullable SpanStatus paramSpanStatus);
  
  @Nullable
  SpanStatus getStatus();
  
  void setThrowable(@Nullable Throwable paramThrowable);
  
  @Nullable
  Throwable getThrowable();
  
  @NotNull
  SpanContext getSpanContext();
  
  void setTag(@Nullable String paramString1, @Nullable String paramString2);
  
  @Nullable
  String getTag(@Nullable String paramString);
  
  boolean isFinished();
  
  void setData(@Nullable String paramString, @Nullable Object paramObject);
  
  @Nullable
  Object getData(@Nullable String paramString);
  
  void setMeasurement(@NotNull String paramString, @NotNull Number paramNumber);
  
  void setMeasurement(@NotNull String paramString, @NotNull Number paramNumber, @NotNull MeasurementUnit paramMeasurementUnit);
  
  @Internal
  boolean updateEndDate(@NotNull SentryDate paramSentryDate);
  
  @Internal
  @NotNull
  SentryDate getStartDate();
  
  @Internal
  @Nullable
  SentryDate getFinishDate();
  
  @Internal
  boolean isNoOp();
  
  void setContext(@Nullable String paramString, @Nullable Object paramObject);
  
  @NotNull
  Contexts getContexts();
  
  @Nullable
  Boolean isSampled();
  
  @Nullable
  TracesSamplingDecision getSamplingDecision();
  
  @Internal
  @NotNull
  ISentryLifecycleToken makeCurrent();
  
  void addFeatureFlag(@Nullable String paramString, @Nullable Boolean paramBoolean);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\ISpan.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */