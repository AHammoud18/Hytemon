/*     */ package io.sentry.clientreport;
/*     */ 
/*     */ import io.sentry.DataCategory;
/*     */ import io.sentry.DateUtils;
/*     */ import io.sentry.SentryEnvelope;
/*     */ import io.sentry.SentryEnvelopeItem;
/*     */ import io.sentry.SentryItemType;
/*     */ import io.sentry.SentryLevel;
/*     */ import io.sentry.SentryLogEvent;
/*     */ import io.sentry.SentryLogEvents;
/*     */ import io.sentry.SentryOptions;
/*     */ import io.sentry.protocol.SentrySpan;
/*     */ import io.sentry.protocol.SentryTransaction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ @Internal
/*     */ public final class ClientReportRecorder
/*     */   implements IClientReportRecorder {
/*     */   @NotNull
/*     */   private final IClientReportStorage storage;
/*     */   
/*     */   public ClientReportRecorder(@NotNull SentryOptions options) {
/*  28 */     this.options = options;
/*  29 */     this.storage = new AtomicClientReportStorage();
/*     */   } @NotNull
/*     */   private final SentryOptions options;
/*     */   @NotNull
/*     */   public SentryEnvelope attachReportToEnvelope(@NotNull SentryEnvelope envelope) {
/*  34 */     ClientReport clientReport = resetCountsAndGenerateClientReport();
/*  35 */     if (clientReport == null) {
/*  36 */       return envelope;
/*     */     }
/*     */     
/*     */     try {
/*  40 */       this.options.getLogger().log(SentryLevel.DEBUG, "Attaching client report to envelope.", new Object[0]);
/*     */       
/*  42 */       List<SentryEnvelopeItem> items = new ArrayList<>();
/*     */       
/*  44 */       for (SentryEnvelopeItem item : envelope.getItems()) {
/*  45 */         items.add(item);
/*     */       }
/*     */       
/*  48 */       items.add(SentryEnvelopeItem.fromClientReport(this.options.getSerializer(), clientReport));
/*     */       
/*  50 */       return new SentryEnvelope(envelope.getHeader(), items);
/*  51 */     } catch (Throwable e) {
/*  52 */       this.options.getLogger().log(SentryLevel.ERROR, e, "Unable to attach client report to envelope.", new Object[0]);
/*  53 */       return envelope;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void recordLostEnvelope(@NotNull DiscardReason reason, @Nullable SentryEnvelope envelope) {
/*  59 */     if (envelope == null) {
/*     */       return;
/*     */     }
/*     */     
/*     */     try {
/*  64 */       for (SentryEnvelopeItem item : envelope.getItems()) {
/*  65 */         recordLostEnvelopeItem(reason, item);
/*     */       }
/*  67 */     } catch (Throwable e) {
/*  68 */       this.options.getLogger().log(SentryLevel.ERROR, e, "Unable to record lost envelope.", new Object[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void recordLostEnvelopeItem(@NotNull DiscardReason reason, @Nullable SentryEnvelopeItem envelopeItem) {
/*  75 */     if (envelopeItem == null) {
/*     */       return;
/*     */     }
/*     */     
/*     */     try {
/*  80 */       SentryItemType itemType = envelopeItem.getHeader().getType();
/*  81 */       if (SentryItemType.ClientReport.equals(itemType)) {
/*     */         try {
/*  83 */           ClientReport clientReport = envelopeItem.getClientReport(this.options.getSerializer());
/*  84 */           restoreCountsFromClientReport(clientReport);
/*  85 */         } catch (Exception e) {
/*  86 */           this.options
/*  87 */             .getLogger()
/*  88 */             .log(SentryLevel.ERROR, "Unable to restore counts from previous client report.", new Object[0]);
/*     */         } 
/*     */       } else {
/*  91 */         DataCategory itemCategory = categoryFromItemType(itemType);
/*  92 */         if (itemCategory.equals(DataCategory.Transaction)) {
/*     */           
/*  94 */           SentryTransaction transaction = envelopeItem.getTransaction(this.options.getSerializer());
/*  95 */           if (transaction != null) {
/*  96 */             List<SentrySpan> spans = transaction.getSpans();
/*     */ 
/*     */             
/*  99 */             recordLostEventInternal(reason
/* 100 */                 .getReason(), DataCategory.Span.getCategory(), Long.valueOf(spans.size() + 1L));
/* 101 */             executeOnDiscard(reason, DataCategory.Span, Long.valueOf(spans.size() + 1L));
/*     */           } 
/* 103 */           recordLostEventInternal(reason.getReason(), itemCategory.getCategory(), Long.valueOf(1L));
/* 104 */           executeOnDiscard(reason, itemCategory, Long.valueOf(1L));
/* 105 */         } else if (itemCategory.equals(DataCategory.LogItem)) {
/* 106 */           SentryLogEvents logs = envelopeItem.getLogs(this.options.getSerializer());
/* 107 */           if (logs != null) {
/* 108 */             List<SentryLogEvent> items = logs.getItems();
/* 109 */             long count = items.size();
/* 110 */             recordLostEventInternal(reason.getReason(), itemCategory.getCategory(), Long.valueOf(count));
/* 111 */             long logBytes = (envelopeItem.getData()).length;
/* 112 */             recordLostEventInternal(reason
/* 113 */                 .getReason(), DataCategory.LogByte.getCategory(), Long.valueOf(logBytes));
/* 114 */             executeOnDiscard(reason, itemCategory, Long.valueOf(count));
/*     */           } else {
/* 116 */             this.options.getLogger().log(SentryLevel.ERROR, "Unable to parse lost logs envelope item.", new Object[0]);
/*     */           } 
/*     */         } else {
/* 119 */           recordLostEventInternal(reason.getReason(), itemCategory.getCategory(), Long.valueOf(1L));
/* 120 */           executeOnDiscard(reason, itemCategory, Long.valueOf(1L));
/*     */         } 
/*     */       } 
/* 123 */     } catch (Throwable e) {
/* 124 */       this.options.getLogger().log(SentryLevel.ERROR, e, "Unable to record lost envelope item.", new Object[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void recordLostEvent(@NotNull DiscardReason reason, @NotNull DataCategory category) {
/* 130 */     recordLostEvent(reason, category, 1L);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void recordLostEvent(@NotNull DiscardReason reason, @NotNull DataCategory category, long count) {
/*     */     try {
/* 137 */       recordLostEventInternal(reason.getReason(), category.getCategory(), Long.valueOf(count));
/* 138 */       executeOnDiscard(reason, category, Long.valueOf(count));
/* 139 */     } catch (Throwable e) {
/* 140 */       this.options.getLogger().log(SentryLevel.ERROR, e, "Unable to record lost event.", new Object[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void executeOnDiscard(@NotNull DiscardReason reason, @NotNull DataCategory category, @NotNull Long countToAdd) {
/* 146 */     if (this.options.getOnDiscard() != null) {
/*     */       try {
/* 148 */         this.options.getOnDiscard().execute(reason, category, countToAdd);
/* 149 */       } catch (Throwable e) {
/* 150 */         this.options.getLogger().log(SentryLevel.ERROR, "The onDiscard callback threw an exception.", e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void recordLostEventInternal(@NotNull String reason, @NotNull String category, @NotNull Long countToAdd) {
/* 157 */     ClientReportKey key = new ClientReportKey(reason, category);
/* 158 */     this.storage.addCount(key, countToAdd);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   ClientReport resetCountsAndGenerateClientReport() {
/* 163 */     Date currentDate = DateUtils.getCurrentDateTime();
/* 164 */     List<DiscardedEvent> discardedEvents = this.storage.resetCountsAndGet();
/*     */     
/* 166 */     if (discardedEvents.isEmpty()) {
/* 167 */       return null;
/*     */     }
/* 169 */     return new ClientReport(currentDate, discardedEvents);
/*     */   }
/*     */ 
/*     */   
/*     */   private void restoreCountsFromClientReport(@Nullable ClientReport clientReport) {
/* 174 */     if (clientReport == null) {
/*     */       return;
/*     */     }
/*     */     
/* 178 */     for (DiscardedEvent discardedEvent : clientReport.getDiscardedEvents()) {
/* 179 */       recordLostEventInternal(discardedEvent
/* 180 */           .getReason(), discardedEvent.getCategory(), discardedEvent.getQuantity());
/*     */     }
/*     */   }
/*     */   
/*     */   private DataCategory categoryFromItemType(SentryItemType itemType) {
/* 185 */     if (SentryItemType.Event.equals(itemType)) {
/* 186 */       return DataCategory.Error;
/*     */     }
/* 188 */     if (SentryItemType.Session.equals(itemType)) {
/* 189 */       return DataCategory.Session;
/*     */     }
/* 191 */     if (SentryItemType.Transaction.equals(itemType)) {
/* 192 */       return DataCategory.Transaction;
/*     */     }
/* 194 */     if (SentryItemType.UserFeedback.equals(itemType)) {
/* 195 */       return DataCategory.UserReport;
/*     */     }
/* 197 */     if (SentryItemType.Feedback.equals(itemType)) {
/* 198 */       return DataCategory.Feedback;
/*     */     }
/* 200 */     if (SentryItemType.Profile.equals(itemType)) {
/* 201 */       return DataCategory.Profile;
/*     */     }
/* 203 */     if (SentryItemType.ProfileChunk.equals(itemType)) {
/* 204 */       return DataCategory.ProfileChunkUi;
/*     */     }
/* 206 */     if (SentryItemType.Attachment.equals(itemType)) {
/* 207 */       return DataCategory.Attachment;
/*     */     }
/* 209 */     if (SentryItemType.CheckIn.equals(itemType)) {
/* 210 */       return DataCategory.Monitor;
/*     */     }
/* 212 */     if (SentryItemType.ReplayVideo.equals(itemType)) {
/* 213 */       return DataCategory.Replay;
/*     */     }
/* 215 */     if (SentryItemType.Log.equals(itemType)) {
/* 216 */       return DataCategory.LogItem;
/*     */     }
/* 218 */     if (SentryItemType.Span.equals(itemType)) {
/* 219 */       return DataCategory.Span;
/*     */     }
/* 221 */     if (SentryItemType.TraceMetric.equals(itemType)) {
/* 222 */       return DataCategory.TraceMetric;
/*     */     }
/*     */     
/* 225 */     return DataCategory.Default;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\clientreport\ClientReportRecorder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */