/*    */ package io.sentry.clientreport;
/*    */ 
/*    */ import io.sentry.DataCategory;
/*    */ import io.sentry.SentryEnvelope;
/*    */ import io.sentry.SentryEnvelopeItem;
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Internal
/*    */ public final class NoOpClientReportRecorder
/*    */   implements IClientReportRecorder
/*    */ {
/*    */   public void recordLostEnvelope(@NotNull DiscardReason reason, @Nullable SentryEnvelope envelope) {}
/*    */   
/*    */   public void recordLostEnvelopeItem(@NotNull DiscardReason reason, @Nullable SentryEnvelopeItem envelopeItem) {}
/*    */   
/*    */   public void recordLostEvent(@NotNull DiscardReason reason, @NotNull DataCategory category) {}
/*    */   
/*    */   public void recordLostEvent(@NotNull DiscardReason reason, @NotNull DataCategory category, long count) {}
/*    */   
/*    */   @NotNull
/*    */   public SentryEnvelope attachReportToEnvelope(@NotNull SentryEnvelope envelope) {
/* 36 */     return envelope;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\clientreport\NoOpClientReportRecorder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */