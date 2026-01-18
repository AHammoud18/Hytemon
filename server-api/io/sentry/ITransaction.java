package io.sentry;

import io.sentry.protocol.SentryId;
import io.sentry.protocol.TransactionNameSource;
import java.util.List;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.TestOnly;

public interface ITransaction extends ISpan {
  void setName(@NotNull String paramString);
  
  @Internal
  void setName(@NotNull String paramString, @NotNull TransactionNameSource paramTransactionNameSource);
  
  @NotNull
  String getName();
  
  @NotNull
  TransactionNameSource getTransactionNameSource();
  
  @NotNull
  @TestOnly
  List<Span> getSpans();
  
  @NotNull
  ISpan startChild(@NotNull String paramString1, @Nullable String paramString2, @Nullable SentryDate paramSentryDate);
  
  @Nullable
  Boolean isProfileSampled();
  
  @Nullable
  ISpan getLatestActiveSpan();
  
  void scheduleFinish();
  
  @Internal
  void forceFinish(@NotNull SpanStatus paramSpanStatus, boolean paramBoolean, @Nullable Hint paramHint);
  
  @Internal
  void finish(@Nullable SpanStatus paramSpanStatus, @Nullable SentryDate paramSentryDate, boolean paramBoolean, @Nullable Hint paramHint);
  
  @NotNull
  SentryId getEventId();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\ITransaction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */