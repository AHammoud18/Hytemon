package io.sentry.clientreport;

import io.sentry.DataCategory;
import io.sentry.SentryEnvelope;
import io.sentry.SentryEnvelopeItem;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Internal
public interface IClientReportRecorder {
  void recordLostEnvelope(@NotNull DiscardReason paramDiscardReason, @Nullable SentryEnvelope paramSentryEnvelope);
  
  void recordLostEnvelopeItem(@NotNull DiscardReason paramDiscardReason, @Nullable SentryEnvelopeItem paramSentryEnvelopeItem);
  
  void recordLostEvent(@NotNull DiscardReason paramDiscardReason, @NotNull DataCategory paramDataCategory);
  
  void recordLostEvent(@NotNull DiscardReason paramDiscardReason, @NotNull DataCategory paramDataCategory, long paramLong);
  
  @NotNull
  SentryEnvelope attachReportToEnvelope(@NotNull SentryEnvelope paramSentryEnvelope);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\clientreport\IClientReportRecorder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */