/*      */ package io.sentry;
/*      */ import io.sentry.clientreport.DiscardReason;
/*      */ import io.sentry.exception.SentryEnvelopeException;
/*      */ import io.sentry.hints.AbnormalExit;
/*      */ import io.sentry.hints.Backfillable;
/*      */ import io.sentry.hints.Cached;
/*      */ import io.sentry.hints.DiskFlushNotification;
/*      */ import io.sentry.hints.TransactionEnd;
/*      */ import io.sentry.logger.ILoggerBatchProcessor;
/*      */ import io.sentry.logger.NoOpLoggerBatchProcessor;
/*      */ import io.sentry.protocol.Contexts;
/*      */ import io.sentry.protocol.DebugMeta;
/*      */ import io.sentry.protocol.FeatureFlags;
/*      */ import io.sentry.protocol.Feedback;
/*      */ import io.sentry.protocol.SentryId;
/*      */ import io.sentry.protocol.SentryTransaction;
/*      */ import io.sentry.transport.RateLimiter;
/*      */ import io.sentry.util.CheckInUtils;
/*      */ import io.sentry.util.ExceptionUtils;
/*      */ import io.sentry.util.HintUtils;
/*      */ import io.sentry.util.JsonSerializationUtils;
/*      */ import io.sentry.util.Objects;
/*      */ import io.sentry.util.Random;
/*      */ import io.sentry.util.TracingUtils;
/*      */ import java.io.Closeable;
/*      */ import java.io.IOException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Comparator;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import org.jetbrains.annotations.ApiStatus.Internal;
/*      */ import org.jetbrains.annotations.NotNull;
/*      */ import org.jetbrains.annotations.Nullable;
/*      */ import org.jetbrains.annotations.TestOnly;
/*      */ 
/*      */ public final class SentryClient implements ISentryClient {
/*      */   static final String SENTRY_PROTOCOL_VERSION = "7";
/*      */   private boolean enabled;
/*      */   @NotNull
/*   43 */   private final SortBreadcrumbsByDate sortBreadcrumbsByDate = new SortBreadcrumbsByDate(); @NotNull
/*      */   private final SentryOptions options; @NotNull
/*      */   private final ITransport transport; @NotNull
/*      */   private final ILoggerBatchProcessor loggerBatchProcessor;
/*      */   public boolean isEnabled() {
/*   48 */     return this.enabled;
/*      */   }
/*      */   
/*      */   public SentryClient(@NotNull SentryOptions options) {
/*   52 */     this.options = (SentryOptions)Objects.requireNonNull(options, "SentryOptions is required.");
/*   53 */     this.enabled = true;
/*      */     
/*   55 */     ITransportFactory transportFactory = options.getTransportFactory();
/*   56 */     if (transportFactory instanceof NoOpTransportFactory) {
/*   57 */       transportFactory = new AsyncHttpTransportFactory();
/*   58 */       options.setTransportFactory(transportFactory);
/*      */     } 
/*      */     
/*   61 */     RequestDetailsResolver requestDetailsResolver = new RequestDetailsResolver(options);
/*   62 */     this.transport = transportFactory.create(options, requestDetailsResolver.resolve());
/*   63 */     if (options.getLogs().isEnabled()) {
/*   64 */       this
/*   65 */         .loggerBatchProcessor = options.getLogs().getLoggerBatchProcessorFactory().create(options, this);
/*      */     } else {
/*   67 */       this.loggerBatchProcessor = (ILoggerBatchProcessor)NoOpLoggerBatchProcessor.getInstance();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean shouldApplyScopeData(@NotNull SentryBaseEvent event, @NotNull Hint hint) {
/*   73 */     if (HintUtils.shouldApplyScopeData(hint)) {
/*   74 */       return true;
/*      */     }
/*   76 */     this.options
/*   77 */       .getLogger()
/*   78 */       .log(SentryLevel.DEBUG, "Event was cached so not applying scope: %s", new Object[] { event.getEventId() });
/*   79 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean shouldApplyScopeData(@NotNull CheckIn event, @NotNull Hint hint) {
/*   84 */     if (HintUtils.shouldApplyScopeData(hint)) {
/*   85 */       return true;
/*      */     }
/*   87 */     this.options
/*   88 */       .getLogger()
/*   89 */       .log(SentryLevel.DEBUG, "Check-in was cached so not applying scope: %s", new Object[] {
/*      */ 
/*      */           
/*   92 */           event.getCheckInId() });
/*   93 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public SentryId captureEvent(@NotNull SentryEvent event, @Nullable IScope scope, @Nullable Hint hint) {
/*  100 */     Objects.requireNonNull(event, "SentryEvent is required.");
/*      */     
/*  102 */     if (hint == null) {
/*  103 */       hint = new Hint();
/*      */     }
/*      */     
/*  106 */     if (shouldApplyScopeData(event, hint)) {
/*  107 */       addScopeAttachmentsToHint(scope, hint);
/*      */     }
/*      */     
/*  110 */     this.options.getLogger().log(SentryLevel.DEBUG, "Capturing event: %s", new Object[] { event.getEventId() });
/*      */     
/*  112 */     if (event != null) {
/*  113 */       Throwable eventThrowable = event.getThrowable();
/*  114 */       if (eventThrowable != null && 
/*  115 */         ExceptionUtils.isIgnored(this.options.getIgnoredExceptionsForType(), eventThrowable)) {
/*  116 */         this.options
/*  117 */           .getLogger()
/*  118 */           .log(SentryLevel.DEBUG, "Event was dropped as the exception %s is ignored", new Object[] {
/*      */ 
/*      */               
/*  121 */               eventThrowable.getClass() });
/*  122 */         this.options
/*  123 */           .getClientReportRecorder()
/*  124 */           .recordLostEvent(DiscardReason.EVENT_PROCESSOR, DataCategory.Error);
/*  125 */         return SentryId.EMPTY_ID;
/*      */       } 
/*      */       
/*  128 */       if (ErrorUtils.isIgnored(this.options.getIgnoredErrors(), event)) {
/*  129 */         this.options
/*  130 */           .getLogger()
/*  131 */           .log(SentryLevel.DEBUG, "Event was dropped as it matched a string/pattern in ignoredErrors", new Object[] {
/*      */ 
/*      */               
/*  134 */               event.getMessage() });
/*  135 */         this.options
/*  136 */           .getClientReportRecorder()
/*  137 */           .recordLostEvent(DiscardReason.EVENT_PROCESSOR, DataCategory.Error);
/*  138 */         return SentryId.EMPTY_ID;
/*      */       } 
/*      */     } 
/*      */     
/*  142 */     if (shouldApplyScopeData(event, hint)) {
/*      */ 
/*      */ 
/*      */       
/*  146 */       event = applyScope(event, scope, hint);
/*      */       
/*  148 */       if (event == null) {
/*  149 */         this.options.getLogger().log(SentryLevel.DEBUG, "Event was dropped by applyScope", new Object[0]);
/*  150 */         return SentryId.EMPTY_ID;
/*      */       } 
/*      */     } 
/*      */     
/*  154 */     event = processEvent(event, hint, this.options.getEventProcessors());
/*      */     
/*  156 */     if (event != null) {
/*  157 */       event = executeBeforeSend(event, hint);
/*      */       
/*  159 */       if (event == null) {
/*  160 */         this.options.getLogger().log(SentryLevel.DEBUG, "Event was dropped by beforeSend", new Object[0]);
/*  161 */         this.options
/*  162 */           .getClientReportRecorder()
/*  163 */           .recordLostEvent(DiscardReason.BEFORE_SEND, DataCategory.Error);
/*      */       } 
/*      */     } 
/*      */     
/*  167 */     if (event != null) {
/*  168 */       event = EventSizeLimitingUtils.limitEventSize(event, hint, this.options);
/*      */     }
/*      */     
/*  171 */     if (event == null) {
/*  172 */       return SentryId.EMPTY_ID;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  177 */     Session sessionBeforeUpdate = (scope != null) ? scope.withSession(session -> {  }) : null;
/*  178 */     Session session = null;
/*      */     
/*  180 */     if (event != null) {
/*      */       
/*  182 */       if (sessionBeforeUpdate == null || !sessionBeforeUpdate.isTerminated()) {
/*  183 */         session = updateSessionData(event, hint, scope);
/*      */       }
/*      */       
/*  186 */       if (!sample()) {
/*  187 */         this.options
/*  188 */           .getLogger()
/*  189 */           .log(SentryLevel.DEBUG, "Event %s was dropped due to sampling decision.", new Object[] {
/*      */ 
/*      */               
/*  192 */               event.getEventId() });
/*  193 */         this.options
/*  194 */           .getClientReportRecorder()
/*  195 */           .recordLostEvent(DiscardReason.SAMPLE_RATE, DataCategory.Error);
/*      */         
/*  197 */         event = null;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  202 */     boolean shouldSendSessionUpdate = shouldSendSessionUpdateForDroppedEvent(sessionBeforeUpdate, session);
/*      */     
/*  204 */     if (event == null && !shouldSendSessionUpdate) {
/*  205 */       this.options
/*  206 */         .getLogger()
/*  207 */         .log(SentryLevel.DEBUG, "Not sending session update for dropped event as it did not cause the session health to change.", new Object[0]);
/*      */ 
/*      */       
/*  210 */       return SentryId.EMPTY_ID;
/*      */     } 
/*      */     
/*  213 */     SentryId sentryId = SentryId.EMPTY_ID;
/*  214 */     if (event != null && event.getEventId() != null) {
/*  215 */       sentryId = event.getEventId();
/*      */     }
/*      */     
/*  218 */     boolean isBackfillable = HintUtils.hasType(hint, Backfillable.class);
/*      */     
/*  220 */     boolean isCached = (HintUtils.hasType(hint, Cached.class) && !HintUtils.hasType(hint, ApplyScopeData.class));
/*      */ 
/*      */ 
/*      */     
/*  224 */     if (event != null && !isBackfillable && !isCached && (event.isErrored() || event.isCrashed())) {
/*  225 */       this.options.getReplayController().captureReplay(Boolean.valueOf(event.isCrashed()));
/*      */     }
/*      */     
/*      */     try {
/*  229 */       TraceContext traceContext = getTraceContext(scope, hint, event);
/*  230 */       boolean shouldSendAttachments = (event != null);
/*  231 */       List<Attachment> attachments = shouldSendAttachments ? getAttachments(hint) : null;
/*      */       
/*  233 */       SentryEnvelope envelope = buildEnvelope(event, attachments, session, traceContext, null);
/*      */       
/*  235 */       hint.clear();
/*  236 */       if (envelope != null) {
/*  237 */         sentryId = sendEnvelope(envelope, hint);
/*      */       }
/*  239 */     } catch (IOException|SentryEnvelopeException e) {
/*  240 */       this.options.getLogger().log(SentryLevel.WARNING, e, "Capturing event %s failed.", new Object[] { sentryId });
/*      */ 
/*      */       
/*  243 */       sentryId = SentryId.EMPTY_ID;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  248 */     if (scope != null) {
/*  249 */       finalizeTransaction(scope, hint);
/*      */     }
/*      */     
/*  252 */     return sentryId;
/*      */   }
/*      */   
/*      */   private void finalizeTransaction(@NotNull IScope scope, @NotNull Hint hint) {
/*  256 */     ITransaction transaction = scope.getTransaction();
/*  257 */     if (transaction != null && 
/*  258 */       HintUtils.hasType(hint, TransactionEnd.class)) {
/*  259 */       Object sentrySdkHint = HintUtils.getSentrySdkHint(hint);
/*  260 */       if (sentrySdkHint instanceof DiskFlushNotification) {
/*  261 */         ((DiskFlushNotification)sentrySdkHint).setFlushable(transaction.getEventId());
/*  262 */         transaction.forceFinish(SpanStatus.ABORTED, false, hint);
/*      */       } else {
/*  264 */         transaction.forceFinish(SpanStatus.ABORTED, false, (Hint)null);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public SentryId captureReplayEvent(@NotNull SentryReplayEvent event, @Nullable IScope scope, @Nullable Hint hint) {
/*  273 */     Objects.requireNonNull(event, "SessionReplay is required.");
/*      */     
/*  275 */     if (hint == null) {
/*  276 */       hint = new Hint();
/*      */     }
/*      */     
/*  279 */     if (shouldApplyScopeData(event, hint)) {
/*  280 */       applyScope(event, scope);
/*      */     }
/*      */     
/*  283 */     this.options.getLogger().log(SentryLevel.DEBUG, "Capturing session replay: %s", new Object[] { event.getEventId() });
/*      */     
/*  285 */     SentryId sentryId = SentryId.EMPTY_ID;
/*  286 */     if (event.getEventId() != null) {
/*  287 */       sentryId = event.getEventId();
/*      */     }
/*      */     
/*  290 */     event = processReplayEvent(event, hint, this.options.getEventProcessors());
/*      */     
/*  292 */     if (event != null) {
/*  293 */       event = executeBeforeSendReplay(event, hint);
/*      */       
/*  295 */       if (event == null) {
/*  296 */         this.options.getLogger().log(SentryLevel.DEBUG, "Event was dropped by beforeSendReplay", new Object[0]);
/*  297 */         this.options
/*  298 */           .getClientReportRecorder()
/*  299 */           .recordLostEvent(DiscardReason.BEFORE_SEND, DataCategory.Replay);
/*      */       } 
/*      */     } 
/*      */     
/*  303 */     if (event == null) {
/*  304 */       return SentryId.EMPTY_ID;
/*      */     }
/*      */     
/*      */     try {
/*  308 */       TraceContext traceContext = getTraceContext(scope, hint, event, null);
/*  309 */       boolean cleanupReplayFolder = HintUtils.hasType(hint, Backfillable.class);
/*      */       
/*  311 */       SentryEnvelope envelope = buildEnvelope(event, hint.getReplayRecording(), traceContext, cleanupReplayFolder);
/*      */       
/*  313 */       hint.clear();
/*  314 */       this.transport.send(envelope, hint);
/*  315 */     } catch (IOException e) {
/*  316 */       this.options.getLogger().log(SentryLevel.WARNING, e, "Capturing event %s failed.", new Object[] { sentryId });
/*      */ 
/*      */       
/*  319 */       sentryId = SentryId.EMPTY_ID;
/*      */     } 
/*      */     
/*  322 */     return sentryId;
/*      */   }
/*      */   
/*      */   private void addScopeAttachmentsToHint(@Nullable IScope scope, @NotNull Hint hint) {
/*  326 */     if (scope != null) {
/*  327 */       hint.addAttachments(scope.getAttachments());
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean shouldSendSessionUpdateForDroppedEvent(@Nullable Session sessionBeforeUpdate, @Nullable Session sessionAfterUpdate) {
/*  333 */     if (sessionAfterUpdate == null) {
/*  334 */       return false;
/*      */     }
/*      */     
/*  337 */     if (sessionBeforeUpdate == null) {
/*  338 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  343 */     boolean didSessionMoveToCrashedState = (sessionAfterUpdate.getStatus() == Session.State.Crashed && sessionBeforeUpdate.getStatus() != Session.State.Crashed);
/*  344 */     if (didSessionMoveToCrashedState) {
/*  345 */       return true;
/*      */     }
/*      */ 
/*      */     
/*  349 */     boolean didSessionMoveToErroredState = (sessionAfterUpdate.errorCount() > 0 && sessionBeforeUpdate.errorCount() <= 0);
/*  350 */     if (didSessionMoveToErroredState) {
/*  351 */       return true;
/*      */     }
/*      */     
/*  354 */     return false;
/*      */   }
/*      */   @Nullable
/*      */   private List<Attachment> getAttachments(@NotNull Hint hint) {
/*  358 */     List<Attachment> attachments = hint.getAttachments();
/*      */     
/*  360 */     Attachment screenshot = hint.getScreenshot();
/*  361 */     if (screenshot != null) {
/*  362 */       attachments.add(screenshot);
/*      */     }
/*      */     
/*  365 */     Attachment viewHierarchy = hint.getViewHierarchy();
/*  366 */     if (viewHierarchy != null) {
/*  367 */       attachments.add(viewHierarchy);
/*      */     }
/*      */     
/*  370 */     Attachment threadDump = hint.getThreadDump();
/*  371 */     if (threadDump != null) {
/*  372 */       attachments.add(threadDump);
/*      */     }
/*      */     
/*  375 */     return attachments;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   private SentryEnvelope buildEnvelope(@Nullable SentryBaseEvent event, @Nullable List<Attachment> attachments, @Nullable Session session, @Nullable TraceContext traceContext, @Nullable ProfilingTraceData profilingTraceData) throws IOException, SentryEnvelopeException {
/*  385 */     SentryId sentryId = null;
/*      */     
/*  387 */     List<SentryEnvelopeItem> envelopeItems = new ArrayList<>();
/*      */     
/*  389 */     if (event != null) {
/*      */       
/*  391 */       SentryEnvelopeItem eventItem = SentryEnvelopeItem.fromEvent(this.options.getSerializer(), event);
/*  392 */       envelopeItems.add(eventItem);
/*  393 */       sentryId = event.getEventId();
/*      */     } 
/*      */     
/*  396 */     if (session != null) {
/*      */       
/*  398 */       SentryEnvelopeItem sessionItem = SentryEnvelopeItem.fromSession(this.options.getSerializer(), session);
/*  399 */       envelopeItems.add(sessionItem);
/*      */     } 
/*      */     
/*  402 */     if (profilingTraceData != null) {
/*      */       
/*  404 */       SentryEnvelopeItem profilingTraceItem = SentryEnvelopeItem.fromProfilingTrace(profilingTraceData, this.options
/*  405 */           .getMaxTraceFileSize(), this.options.getSerializer());
/*  406 */       envelopeItems.add(profilingTraceItem);
/*      */       
/*  408 */       if (sentryId == null) {
/*  409 */         sentryId = new SentryId(profilingTraceData.getProfileId());
/*      */       }
/*      */     } 
/*      */     
/*  413 */     if (attachments != null) {
/*  414 */       for (Attachment attachment : attachments) {
/*      */         
/*  416 */         SentryEnvelopeItem attachmentItem = SentryEnvelopeItem.fromAttachment(this.options
/*  417 */             .getSerializer(), this.options
/*  418 */             .getLogger(), attachment, this.options
/*      */             
/*  420 */             .getMaxAttachmentSize());
/*  421 */         envelopeItems.add(attachmentItem);
/*      */       } 
/*      */     }
/*      */     
/*  425 */     if (!envelopeItems.isEmpty()) {
/*      */       
/*  427 */       SentryEnvelopeHeader envelopeHeader = new SentryEnvelopeHeader(sentryId, this.options.getSdkVersion(), traceContext);
/*      */       
/*  429 */       return new SentryEnvelope(envelopeHeader, envelopeItems);
/*      */     } 
/*      */     
/*  432 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   private SentryEvent processEvent(@NotNull SentryEvent event, @NotNull Hint hint, @NotNull List<EventProcessor> eventProcessors) {
/*  440 */     for (EventProcessor processor : eventProcessors) {
/*      */ 
/*      */       
/*      */       try {
/*  444 */         boolean isBackfillingProcessor = processor instanceof BackfillingEventProcessor;
/*  445 */         boolean isBackfillable = HintUtils.hasType(hint, Backfillable.class);
/*  446 */         if (isBackfillable && isBackfillingProcessor) {
/*  447 */           event = processor.process(event, hint);
/*  448 */         } else if (!isBackfillable && !isBackfillingProcessor) {
/*  449 */           event = processor.process(event, hint);
/*      */         } 
/*  451 */       } catch (Throwable e) {
/*  452 */         this.options
/*  453 */           .getLogger()
/*  454 */           .log(SentryLevel.ERROR, e, "An exception occurred while processing event by processor: %s", new Object[] {
/*      */ 
/*      */ 
/*      */               
/*  458 */               processor.getClass().getName()
/*      */             });
/*      */       } 
/*  461 */       if (event == null) {
/*  462 */         this.options
/*  463 */           .getLogger()
/*  464 */           .log(SentryLevel.DEBUG, "Event was dropped by a processor: %s", new Object[] {
/*      */ 
/*      */               
/*  467 */               processor.getClass().getName() });
/*  468 */         this.options
/*  469 */           .getClientReportRecorder()
/*  470 */           .recordLostEvent(DiscardReason.EVENT_PROCESSOR, DataCategory.Error);
/*      */         break;
/*      */       } 
/*      */     } 
/*  474 */     return event;
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   private SentryLogEvent processLogEvent(@NotNull SentryLogEvent event, @NotNull List<EventProcessor> eventProcessors) {
/*  480 */     for (EventProcessor processor : eventProcessors) {
/*      */       try {
/*  482 */         event = processor.process(event);
/*  483 */       } catch (Throwable e) {
/*  484 */         this.options
/*  485 */           .getLogger()
/*  486 */           .log(SentryLevel.ERROR, e, "An exception occurred while processing log event by processor: %s", new Object[] {
/*      */ 
/*      */ 
/*      */               
/*  490 */               processor.getClass().getName()
/*      */             });
/*      */       } 
/*  493 */       if (event == null) {
/*  494 */         this.options
/*  495 */           .getLogger()
/*  496 */           .log(SentryLevel.DEBUG, "Log event was dropped by a processor: %s", new Object[] {
/*      */ 
/*      */               
/*  499 */               processor.getClass().getName() });
/*  500 */         this.options
/*  501 */           .getClientReportRecorder()
/*  502 */           .recordLostEvent(DiscardReason.EVENT_PROCESSOR, DataCategory.LogItem);
/*      */         break;
/*      */       } 
/*      */     } 
/*  506 */     return event;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   private SentryTransaction processTransaction(@NotNull SentryTransaction transaction, @NotNull Hint hint, @NotNull List<EventProcessor> eventProcessors) {
/*  513 */     for (EventProcessor processor : eventProcessors) {
/*  514 */       int spanCountBeforeProcessor = transaction.getSpans().size();
/*      */       try {
/*  516 */         transaction = processor.process(transaction, hint);
/*  517 */       } catch (Throwable e) {
/*  518 */         this.options
/*  519 */           .getLogger()
/*  520 */           .log(SentryLevel.ERROR, e, "An exception occurred while processing transaction by processor: %s", new Object[] {
/*      */ 
/*      */ 
/*      */               
/*  524 */               processor.getClass().getName() });
/*      */       } 
/*  526 */       int spanCountAfterProcessor = (transaction == null) ? 0 : transaction.getSpans().size();
/*      */       
/*  528 */       if (transaction == null) {
/*  529 */         this.options
/*  530 */           .getLogger()
/*  531 */           .log(SentryLevel.DEBUG, "Transaction was dropped by a processor: %s", new Object[] {
/*      */ 
/*      */               
/*  534 */               processor.getClass().getName() });
/*  535 */         this.options
/*  536 */           .getClientReportRecorder()
/*  537 */           .recordLostEvent(DiscardReason.EVENT_PROCESSOR, DataCategory.Transaction);
/*      */         
/*  539 */         this.options
/*  540 */           .getClientReportRecorder()
/*  541 */           .recordLostEvent(DiscardReason.EVENT_PROCESSOR, DataCategory.Span, (spanCountBeforeProcessor + 1));
/*      */         break;
/*      */       } 
/*  544 */       if (spanCountAfterProcessor < spanCountBeforeProcessor) {
/*      */         
/*  546 */         int droppedSpanCount = spanCountBeforeProcessor - spanCountAfterProcessor;
/*  547 */         this.options
/*  548 */           .getLogger()
/*  549 */           .log(SentryLevel.DEBUG, "%d spans were dropped by a processor: %s", new Object[] {
/*      */ 
/*      */               
/*  552 */               Integer.valueOf(droppedSpanCount), processor
/*  553 */               .getClass().getName() });
/*  554 */         this.options
/*  555 */           .getClientReportRecorder()
/*  556 */           .recordLostEvent(DiscardReason.EVENT_PROCESSOR, DataCategory.Span, droppedSpanCount);
/*      */       } 
/*      */     } 
/*  559 */     return transaction;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   private SentryReplayEvent processReplayEvent(@NotNull SentryReplayEvent replayEvent, @NotNull Hint hint, @NotNull List<EventProcessor> eventProcessors) {
/*  567 */     for (EventProcessor processor : eventProcessors) {
/*      */       try {
/*  569 */         replayEvent = processor.process(replayEvent, hint);
/*  570 */       } catch (Throwable e) {
/*  571 */         this.options
/*  572 */           .getLogger()
/*  573 */           .log(SentryLevel.ERROR, e, "An exception occurred while processing replay event by processor: %s", new Object[] {
/*      */ 
/*      */ 
/*      */               
/*  577 */               processor.getClass().getName()
/*      */             });
/*      */       } 
/*  580 */       if (replayEvent == null) {
/*  581 */         this.options
/*  582 */           .getLogger()
/*  583 */           .log(SentryLevel.DEBUG, "Replay event was dropped by a processor: %s", new Object[] {
/*      */ 
/*      */               
/*  586 */               processor.getClass().getName() });
/*  587 */         this.options
/*  588 */           .getClientReportRecorder()
/*  589 */           .recordLostEvent(DiscardReason.EVENT_PROCESSOR, DataCategory.Replay);
/*      */         break;
/*      */       } 
/*      */     } 
/*  593 */     return replayEvent;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   private SentryEvent processFeedbackEvent(@NotNull SentryEvent feedbackEvent, @NotNull Hint hint, @NotNull List<EventProcessor> eventProcessors) {
/*  601 */     for (EventProcessor processor : eventProcessors) {
/*      */       try {
/*  603 */         feedbackEvent = processor.process(feedbackEvent, hint);
/*  604 */       } catch (Throwable e) {
/*  605 */         this.options
/*  606 */           .getLogger()
/*  607 */           .log(SentryLevel.ERROR, e, "An exception occurred while processing feedback event by processor: %s", new Object[] {
/*      */ 
/*      */ 
/*      */               
/*  611 */               processor.getClass().getName()
/*      */             });
/*      */       } 
/*  614 */       if (feedbackEvent == null) {
/*  615 */         this.options
/*  616 */           .getLogger()
/*  617 */           .log(SentryLevel.DEBUG, "Feedback event was dropped by a processor: %s", new Object[] {
/*      */ 
/*      */               
/*  620 */               processor.getClass().getName() });
/*  621 */         this.options
/*  622 */           .getClientReportRecorder()
/*  623 */           .recordLostEvent(DiscardReason.EVENT_PROCESSOR, DataCategory.Feedback);
/*      */         break;
/*      */       } 
/*      */     } 
/*  627 */     return feedbackEvent;
/*      */   }
/*      */ 
/*      */   
/*      */   public void captureUserFeedback(@NotNull UserFeedback userFeedback) {
/*  632 */     Objects.requireNonNull(userFeedback, "SentryEvent is required.");
/*      */     
/*  634 */     if (SentryId.EMPTY_ID.equals(userFeedback.getEventId())) {
/*  635 */       this.options.getLogger().log(SentryLevel.WARNING, "Capturing userFeedback without a Sentry Id.", new Object[0]);
/*      */       return;
/*      */     } 
/*  638 */     this.options
/*  639 */       .getLogger()
/*  640 */       .log(SentryLevel.DEBUG, "Capturing userFeedback: %s", new Object[] { userFeedback.getEventId() });
/*      */     
/*      */     try {
/*  643 */       SentryEnvelope envelope = buildEnvelope(userFeedback);
/*  644 */       sendEnvelope(envelope, null);
/*  645 */     } catch (IOException e) {
/*  646 */       this.options
/*  647 */         .getLogger()
/*  648 */         .log(SentryLevel.WARNING, e, "Capturing user feedback %s failed.", new Object[] {
/*      */ 
/*      */ 
/*      */             
/*  652 */             userFeedback.getEventId() });
/*      */     } 
/*      */   }
/*      */   @NotNull
/*      */   private SentryEnvelope buildEnvelope(@NotNull UserFeedback userFeedback) {
/*  657 */     List<SentryEnvelopeItem> envelopeItems = new ArrayList<>();
/*      */ 
/*      */     
/*  660 */     SentryEnvelopeItem userFeedbackItem = SentryEnvelopeItem.fromUserFeedback(this.options.getSerializer(), userFeedback);
/*  661 */     envelopeItems.add(userFeedbackItem);
/*      */ 
/*      */     
/*  664 */     SentryEnvelopeHeader envelopeHeader = new SentryEnvelopeHeader(userFeedback.getEventId(), this.options.getSdkVersion());
/*      */     
/*  666 */     return new SentryEnvelope(envelopeHeader, envelopeItems);
/*      */   }
/*      */   
/*      */   @NotNull
/*      */   private SentryEnvelope buildEnvelope(@NotNull CheckIn checkIn, @Nullable TraceContext traceContext) {
/*  671 */     List<SentryEnvelopeItem> envelopeItems = new ArrayList<>();
/*      */ 
/*      */     
/*  674 */     SentryEnvelopeItem checkInItem = SentryEnvelopeItem.fromCheckIn(this.options.getSerializer(), checkIn);
/*  675 */     envelopeItems.add(checkInItem);
/*      */ 
/*      */     
/*  678 */     SentryEnvelopeHeader envelopeHeader = new SentryEnvelopeHeader(checkIn.getCheckInId(), this.options.getSdkVersion(), traceContext);
/*      */     
/*  680 */     return new SentryEnvelope(envelopeHeader, envelopeItems);
/*      */   }
/*      */   @NotNull
/*      */   private SentryEnvelope buildEnvelope(@NotNull SentryLogEvents logEvents) {
/*  684 */     List<SentryEnvelopeItem> envelopeItems = new ArrayList<>();
/*      */ 
/*      */     
/*  687 */     SentryEnvelopeItem logItem = SentryEnvelopeItem.fromLogs(this.options.getSerializer(), logEvents);
/*  688 */     envelopeItems.add(logItem);
/*      */ 
/*      */     
/*  691 */     SentryEnvelopeHeader envelopeHeader = new SentryEnvelopeHeader(null, this.options.getSdkVersion(), null);
/*      */     
/*  693 */     return new SentryEnvelope(envelopeHeader, envelopeItems);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   private SentryEnvelope buildEnvelope(@NotNull SentryReplayEvent event, @Nullable ReplayRecording replayRecording, @Nullable TraceContext traceContext, boolean cleanupReplayFolder) {
/*  701 */     List<SentryEnvelopeItem> envelopeItems = new ArrayList<>();
/*      */ 
/*      */     
/*  704 */     SentryEnvelopeItem replayItem = SentryEnvelopeItem.fromReplay(this.options
/*  705 */         .getSerializer(), this.options
/*  706 */         .getLogger(), event, replayRecording, cleanupReplayFolder);
/*      */ 
/*      */ 
/*      */     
/*  710 */     envelopeItems.add(replayItem);
/*  711 */     SentryId sentryId = event.getEventId();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  717 */     SentryEnvelopeHeader envelopeHeader = new SentryEnvelopeHeader(sentryId, this.options.getSessionReplay().getSdkVersion(), traceContext);
/*      */     
/*  719 */     return new SentryEnvelope(envelopeHeader, envelopeItems);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @TestOnly
/*      */   @Nullable
/*      */   Session updateSessionData(@NotNull SentryEvent event, @NotNull Hint hint, @Nullable IScope scope) {
/*  733 */     Session clonedSession = null;
/*      */     
/*  735 */     if (HintUtils.shouldApplyScopeData(hint)) {
/*  736 */       if (scope != null) {
/*      */         
/*  738 */         clonedSession = scope.withSession(session -> {
/*      */               if (session != null) {
/*      */                 Session.State status = null;
/*      */                 
/*      */                 if (event.isCrashed()) {
/*      */                   status = Session.State.Crashed;
/*      */                 }
/*      */                 
/*      */                 boolean crashedOrErrored = false;
/*      */                 
/*      */                 if (Session.State.Crashed == status || event.isErrored()) {
/*      */                   crashedOrErrored = true;
/*      */                 }
/*      */                 
/*      */                 String userAgent = null;
/*      */                 
/*      */                 if (event.getRequest() != null && event.getRequest().getHeaders() != null && event.getRequest().getHeaders().containsKey("user-agent")) {
/*      */                   userAgent = (String)event.getRequest().getHeaders().get("user-agent");
/*      */                 }
/*      */                 
/*      */                 Object sentrySdkHint = HintUtils.getSentrySdkHint(hint);
/*      */                 
/*      */                 String abnormalMechanism = null;
/*      */                 
/*      */                 if (sentrySdkHint instanceof AbnormalExit) {
/*      */                   abnormalMechanism = ((AbnormalExit)sentrySdkHint).mechanism();
/*      */                   
/*      */                   status = Session.State.Abnormal;
/*      */                 } 
/*      */                 
/*      */                 if (session.update(status, userAgent, crashedOrErrored, abnormalMechanism)) {
/*      */                   if (session.isTerminated()) {
/*      */                     session.end();
/*      */                   }
/*      */                 }
/*      */               } else {
/*      */                 this.options.getLogger().log(SentryLevel.INFO, "Session is null on scope.withSession", new Object[0]);
/*      */               } 
/*      */             });
/*      */       } else {
/*  778 */         this.options.getLogger().log(SentryLevel.INFO, "Scope is null on client.captureEvent", new Object[0]);
/*      */       } 
/*      */     }
/*  781 */     return clonedSession;
/*      */   }
/*      */   
/*      */   @Internal
/*      */   public void captureSession(@NotNull Session session, @Nullable Hint hint) {
/*      */     SentryEnvelope envelope;
/*  787 */     Objects.requireNonNull(session, "Session is required.");
/*      */     
/*  789 */     if (session.getRelease() == null || session.getRelease().isEmpty()) {
/*  790 */       this.options
/*  791 */         .getLogger()
/*  792 */         .log(SentryLevel.WARNING, "Sessions can't be captured without setting a release.", new Object[0]);
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*      */     try {
/*  798 */       envelope = SentryEnvelope.from(this.options.getSerializer(), session, this.options.getSdkVersion());
/*  799 */     } catch (IOException e) {
/*  800 */       this.options.getLogger().log(SentryLevel.ERROR, "Failed to capture session.", e);
/*      */       
/*      */       return;
/*      */     } 
/*  804 */     captureEnvelope(envelope, hint);
/*      */   }
/*      */ 
/*      */   
/*      */   @Internal
/*      */   @NotNull
/*      */   public SentryId captureEnvelope(@NotNull SentryEnvelope envelope, @Nullable Hint hint) {
/*  811 */     Objects.requireNonNull(envelope, "SentryEnvelope is required.");
/*      */     
/*  813 */     if (hint == null) {
/*  814 */       hint = new Hint();
/*      */     }
/*      */     
/*      */     try {
/*  818 */       hint.clear();
/*  819 */       return sendEnvelope(envelope, hint);
/*  820 */     } catch (IOException e) {
/*  821 */       this.options.getLogger().log(SentryLevel.ERROR, "Failed to capture envelope.", e);
/*      */       
/*  823 */       return SentryId.EMPTY_ID;
/*      */     } 
/*      */   }
/*      */   
/*      */   @NotNull
/*      */   private SentryId sendEnvelope(@NotNull SentryEnvelope envelope, @Nullable Hint hint) throws IOException {
/*  829 */     SentryOptions.BeforeEnvelopeCallback beforeEnvelopeCallback = this.options.getBeforeEnvelopeCallback();
/*  830 */     if (beforeEnvelopeCallback != null) {
/*      */       try {
/*  832 */         beforeEnvelopeCallback.execute(envelope, hint);
/*  833 */       } catch (Throwable e) {
/*  834 */         this.options
/*  835 */           .getLogger()
/*  836 */           .log(SentryLevel.ERROR, "The BeforeEnvelope callback threw an exception.", e);
/*      */       } 
/*      */     }
/*      */     
/*  840 */     SentryIntegrationPackageStorage.getInstance().checkForMixedVersions(this.options.getLogger());
/*      */     
/*  842 */     if (hint == null) {
/*  843 */       this.transport.send(envelope);
/*      */     } else {
/*  845 */       this.transport.send(envelope, hint);
/*      */     } 
/*  847 */     SentryId id = envelope.getHeader().getEventId();
/*  848 */     return (id != null) ? id : SentryId.EMPTY_ID;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public SentryId captureTransaction(@NotNull SentryTransaction transaction, @Nullable TraceContext traceContext, @Nullable IScope scope, @Nullable Hint hint, @Nullable ProfilingTraceData profilingTraceData) {
/*  858 */     Objects.requireNonNull(transaction, "Transaction is required.");
/*      */     
/*  860 */     if (hint == null) {
/*  861 */       hint = new Hint();
/*      */     }
/*      */     
/*  864 */     if (shouldApplyScopeData((SentryBaseEvent)transaction, hint)) {
/*  865 */       addScopeAttachmentsToHint(scope, hint);
/*      */     }
/*      */     
/*  868 */     this.options
/*  869 */       .getLogger()
/*  870 */       .log(SentryLevel.DEBUG, "Capturing transaction: %s", new Object[] { transaction.getEventId() });
/*      */     
/*  872 */     if (TracingUtils.isIgnored(this.options.getIgnoredTransactions(), transaction.getTransaction())) {
/*  873 */       this.options
/*  874 */         .getLogger()
/*  875 */         .log(SentryLevel.DEBUG, "Transaction was dropped as transaction name %s is ignored", new Object[] {
/*      */ 
/*      */             
/*  878 */             transaction.getTransaction() });
/*  879 */       this.options
/*  880 */         .getClientReportRecorder()
/*  881 */         .recordLostEvent(DiscardReason.EVENT_PROCESSOR, DataCategory.Transaction);
/*  882 */       this.options
/*  883 */         .getClientReportRecorder()
/*  884 */         .recordLostEvent(DiscardReason.EVENT_PROCESSOR, DataCategory.Span, (transaction
/*  885 */           .getSpans().size() + 1));
/*  886 */       return SentryId.EMPTY_ID;
/*      */     } 
/*      */     
/*  889 */     SentryId sentryId = SentryId.EMPTY_ID;
/*  890 */     if (transaction.getEventId() != null) {
/*  891 */       sentryId = transaction.getEventId();
/*      */     }
/*      */     
/*  894 */     if (shouldApplyScopeData((SentryBaseEvent)transaction, hint)) {
/*  895 */       transaction = applyScope(transaction, scope);
/*      */       
/*  897 */       if (transaction != null && scope != null) {
/*  898 */         transaction = processTransaction(transaction, hint, scope.getEventProcessors());
/*      */       }
/*      */       
/*  901 */       if (transaction == null) {
/*  902 */         this.options.getLogger().log(SentryLevel.DEBUG, "Transaction was dropped by applyScope", new Object[0]);
/*      */       }
/*      */     } 
/*      */     
/*  906 */     if (transaction != null) {
/*  907 */       transaction = processTransaction(transaction, hint, this.options.getEventProcessors());
/*      */     }
/*      */     
/*  910 */     if (transaction == null) {
/*  911 */       this.options.getLogger().log(SentryLevel.DEBUG, "Transaction was dropped by Event processors.", new Object[0]);
/*  912 */       return SentryId.EMPTY_ID;
/*      */     } 
/*      */     
/*  915 */     int spanCountBeforeCallback = transaction.getSpans().size();
/*  916 */     transaction = executeBeforeSendTransaction(transaction, hint);
/*  917 */     int spanCountAfterCallback = (transaction == null) ? 0 : transaction.getSpans().size();
/*      */     
/*  919 */     if (transaction == null) {
/*  920 */       this.options
/*  921 */         .getLogger()
/*  922 */         .log(SentryLevel.DEBUG, "Transaction was dropped by beforeSendTransaction.", new Object[0]);
/*  923 */       this.options
/*  924 */         .getClientReportRecorder()
/*  925 */         .recordLostEvent(DiscardReason.BEFORE_SEND, DataCategory.Transaction);
/*      */       
/*  927 */       this.options
/*  928 */         .getClientReportRecorder()
/*  929 */         .recordLostEvent(DiscardReason.BEFORE_SEND, DataCategory.Span, (spanCountBeforeCallback + 1));
/*      */       
/*  931 */       return SentryId.EMPTY_ID;
/*  932 */     }  if (spanCountAfterCallback < spanCountBeforeCallback) {
/*      */       
/*  934 */       int droppedSpanCount = spanCountBeforeCallback - spanCountAfterCallback;
/*  935 */       this.options
/*  936 */         .getLogger()
/*  937 */         .log(SentryLevel.DEBUG, "%d spans were dropped by beforeSendTransaction.", new Object[] {
/*      */ 
/*      */             
/*  940 */             Integer.valueOf(droppedSpanCount) });
/*  941 */       this.options
/*  942 */         .getClientReportRecorder()
/*  943 */         .recordLostEvent(DiscardReason.BEFORE_SEND, DataCategory.Span, droppedSpanCount);
/*      */     } 
/*      */ 
/*      */     
/*      */     try {
/*  948 */       SentryEnvelope envelope = buildEnvelope((SentryBaseEvent)transaction, 
/*      */           
/*  950 */           filterForTransaction(getAttachments(hint)), null, traceContext, profilingTraceData);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  955 */       hint.clear();
/*  956 */       if (envelope != null) {
/*  957 */         sentryId = sendEnvelope(envelope, hint);
/*      */       }
/*  959 */     } catch (IOException|SentryEnvelopeException e) {
/*  960 */       this.options.getLogger().log(SentryLevel.WARNING, e, "Capturing transaction %s failed.", new Object[] { sentryId });
/*      */       
/*  962 */       sentryId = SentryId.EMPTY_ID;
/*      */     } 
/*      */     
/*  965 */     return sentryId;
/*      */   }
/*      */ 
/*      */   
/*      */   @Internal
/*      */   @NotNull
/*      */   public SentryId captureProfileChunk(@NotNull ProfileChunk profileChunk, @Nullable IScope scope) {
/*  972 */     Objects.requireNonNull(profileChunk, "profileChunk is required.");
/*      */     
/*  974 */     this.options
/*  975 */       .getLogger()
/*  976 */       .log(SentryLevel.DEBUG, "Capturing profile chunk: %s", new Object[] { profileChunk.getChunkId() });
/*      */     
/*  978 */     SentryId sentryId = profileChunk.getChunkId();
/*  979 */     DebugMeta debugMeta = DebugMeta.buildDebugMeta(profileChunk.getDebugMeta(), this.options);
/*  980 */     if (debugMeta != null) {
/*  981 */       profileChunk.setDebugMeta(debugMeta);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  990 */       SentryEnvelope envelope = new SentryEnvelope(new SentryEnvelopeHeader(sentryId, this.options.getSdkVersion(), null), Collections.singletonList(
/*  991 */             SentryEnvelopeItem.fromProfileChunk(profileChunk, this.options
/*  992 */               .getSerializer(), this.options.getProfilerConverter())));
/*  993 */       sentryId = sendEnvelope(envelope, null);
/*  994 */     } catch (IOException|SentryEnvelopeException e) {
/*  995 */       this.options
/*  996 */         .getLogger()
/*  997 */         .log(SentryLevel.WARNING, e, "Capturing profile chunk %s failed.", new Object[] { sentryId });
/*      */       
/*  999 */       sentryId = SentryId.EMPTY_ID;
/*      */     } 
/*      */     
/* 1002 */     return sentryId;
/*      */   }
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public SentryId captureCheckIn(@NotNull CheckIn checkIn, @Nullable IScope scope, @Nullable Hint hint) {
/* 1008 */     if (hint == null) {
/* 1009 */       hint = new Hint();
/*      */     }
/*      */     
/* 1012 */     if (checkIn.getEnvironment() == null) {
/* 1013 */       checkIn.setEnvironment(this.options.getEnvironment());
/*      */     }
/*      */     
/* 1016 */     if (checkIn.getRelease() == null) {
/* 1017 */       checkIn.setRelease(this.options.getRelease());
/*      */     }
/*      */     
/* 1020 */     if (shouldApplyScopeData(checkIn, hint)) {
/* 1021 */       checkIn = applyScope(checkIn, scope);
/*      */     }
/*      */     
/* 1024 */     if (CheckInUtils.isIgnored(this.options.getIgnoredCheckIns(), checkIn.getMonitorSlug())) {
/* 1025 */       this.options
/* 1026 */         .getLogger()
/* 1027 */         .log(SentryLevel.DEBUG, "Check-in was dropped as slug %s is ignored", new Object[] {
/*      */ 
/*      */             
/* 1030 */             checkIn.getMonitorSlug() });
/* 1031 */       this.options
/* 1032 */         .getClientReportRecorder()
/* 1033 */         .recordLostEvent(DiscardReason.EVENT_PROCESSOR, DataCategory.Monitor);
/* 1034 */       return SentryId.EMPTY_ID;
/*      */     } 
/*      */     
/* 1037 */     this.options.getLogger().log(SentryLevel.DEBUG, "Capturing check-in: %s", new Object[] { checkIn.getCheckInId() });
/*      */     
/* 1039 */     SentryId sentryId = checkIn.getCheckInId();
/*      */     
/*      */     try {
/* 1042 */       TraceContext traceContext = getTraceContext(scope, hint, null);
/* 1043 */       SentryEnvelope envelope = buildEnvelope(checkIn, traceContext);
/*      */       
/* 1045 */       hint.clear();
/* 1046 */       sentryId = sendEnvelope(envelope, hint);
/* 1047 */     } catch (IOException e) {
/* 1048 */       this.options.getLogger().log(SentryLevel.WARNING, e, "Capturing check-in %s failed.", new Object[] { sentryId });
/*      */       
/* 1050 */       sentryId = SentryId.EMPTY_ID;
/*      */     } 
/*      */     
/* 1053 */     return sentryId;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public SentryId captureFeedback(@NotNull Feedback feedback, @Nullable Hint hint, @NotNull IScope scope) {
/* 1067 */     SentryEvent event = new SentryEvent();
/* 1068 */     event.getContexts().setFeedback(feedback);
/*      */     
/* 1070 */     if (hint == null) {
/* 1071 */       hint = new Hint();
/*      */     }
/*      */     
/* 1074 */     if (feedback.getUrl() == null) {
/* 1075 */       feedback.setUrl(scope.getScreen());
/*      */     }
/*      */     
/* 1078 */     this.options.getLogger().log(SentryLevel.DEBUG, "Capturing feedback: %s", new Object[] { event.getEventId() });
/*      */     
/* 1080 */     if (shouldApplyScopeData(event, hint)) {
/*      */ 
/*      */ 
/*      */       
/* 1084 */       event = applyFeedbackScope(event, scope, hint);
/*      */       
/* 1086 */       if (event == null) {
/* 1087 */         this.options.getLogger().log(SentryLevel.DEBUG, "Feedback was dropped by applyScope", new Object[0]);
/* 1088 */         return SentryId.EMPTY_ID;
/*      */       } 
/*      */     } 
/*      */     
/* 1092 */     event = processFeedbackEvent(event, hint, this.options.getEventProcessors());
/*      */     
/* 1094 */     if (event != null) {
/* 1095 */       event = executeBeforeSendFeedback(event, hint);
/*      */       
/* 1097 */       if (event == null) {
/* 1098 */         this.options.getLogger().log(SentryLevel.DEBUG, "Event was dropped by beforeSend", new Object[0]);
/* 1099 */         this.options
/* 1100 */           .getClientReportRecorder()
/* 1101 */           .recordLostEvent(DiscardReason.BEFORE_SEND, DataCategory.Feedback);
/*      */       } 
/*      */     } 
/*      */     
/* 1105 */     if (event == null) {
/* 1106 */       return SentryId.EMPTY_ID;
/*      */     }
/*      */     
/* 1109 */     SentryId sentryId = SentryId.EMPTY_ID;
/* 1110 */     if (event.getEventId() != null) {
/* 1111 */       sentryId = event.getEventId();
/*      */     }
/*      */ 
/*      */     
/* 1115 */     if (feedback.getReplayId() == null) {
/* 1116 */       this.options.getReplayController().captureReplay(Boolean.valueOf(false));
/* 1117 */       SentryId replayId = scope.getReplayId();
/* 1118 */       if (!replayId.equals(SentryId.EMPTY_ID)) {
/* 1119 */         feedback.setReplayId(replayId);
/*      */       }
/*      */     } 
/*      */     
/*      */     try {
/* 1124 */       TraceContext traceContext = getTraceContext(scope, hint, event);
/* 1125 */       List<Attachment> attachments = getAttachments(hint);
/*      */       
/* 1127 */       SentryEnvelope envelope = buildEnvelope(event, attachments, null, traceContext, null);
/*      */       
/* 1129 */       hint.clear();
/* 1130 */       if (envelope != null) {
/* 1131 */         sentryId = sendEnvelope(envelope, hint);
/*      */       }
/* 1133 */     } catch (IOException|SentryEnvelopeException e) {
/* 1134 */       this.options.getLogger().log(SentryLevel.WARNING, e, "Capturing feedback %s failed.", new Object[] { sentryId });
/*      */ 
/*      */       
/* 1137 */       sentryId = SentryId.EMPTY_ID;
/*      */     } 
/*      */     
/* 1140 */     return sentryId;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   private TraceContext getTraceContext(@Nullable IScope scope, @NotNull Hint hint, @Nullable SentryEvent event) {
/* 1145 */     return getTraceContext(scope, hint, event, (event != null) ? event.getTransaction() : null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   private TraceContext getTraceContext(@Nullable IScope scope, @NotNull Hint hint, @Nullable SentryBaseEvent event, @Nullable String txn) {
/* 1153 */     TraceContext traceContext = null;
/* 1154 */     boolean isBackfillable = HintUtils.hasType(hint, Backfillable.class);
/* 1155 */     if (isBackfillable) {
/*      */       
/* 1157 */       if (event != null) {
/* 1158 */         Baggage baggage = Baggage.fromEvent(event, txn, this.options);
/* 1159 */         traceContext = baggage.toTraceContext();
/*      */       } 
/* 1161 */     } else if (scope != null) {
/* 1162 */       ITransaction transaction = scope.getTransaction();
/* 1163 */       if (transaction != null) {
/* 1164 */         traceContext = transaction.traceContext();
/*      */       } else {
/*      */         
/* 1167 */         PropagationContext propagationContext = TracingUtils.maybeUpdateBaggage(scope, this.options);
/* 1168 */         traceContext = propagationContext.traceContext();
/*      */       } 
/*      */     } 
/* 1171 */     return traceContext;
/*      */   }
/*      */ 
/*      */   
/*      */   @Experimental
/*      */   public void captureLog(@Nullable SentryLogEvent logEvent, @Nullable IScope scope) {
/* 1177 */     if (logEvent != null && scope != null) {
/* 1178 */       logEvent = processLogEvent(logEvent, scope.getEventProcessors());
/* 1179 */       if (logEvent == null) {
/*      */         return;
/*      */       }
/*      */     } 
/*      */     
/* 1184 */     if (logEvent != null) {
/* 1185 */       logEvent = processLogEvent(logEvent, this.options.getEventProcessors());
/* 1186 */       if (logEvent == null) {
/*      */         return;
/*      */       }
/*      */     } 
/*      */     
/* 1191 */     if (logEvent != null) {
/* 1192 */       SentryLogEvent tmpLogEvent = logEvent;
/* 1193 */       logEvent = executeBeforeSendLog(logEvent);
/*      */       
/* 1195 */       if (logEvent == null) {
/* 1196 */         this.options.getLogger().log(SentryLevel.DEBUG, "Log Event was dropped by beforeSendLog", new Object[0]);
/* 1197 */         this.options
/* 1198 */           .getClientReportRecorder()
/* 1199 */           .recordLostEvent(DiscardReason.BEFORE_SEND, DataCategory.LogItem);
/*      */         
/* 1201 */         long logEventNumberOfBytes = JsonSerializationUtils.byteSizeOf(this.options
/* 1202 */             .getSerializer(), this.options.getLogger(), tmpLogEvent);
/* 1203 */         this.options
/* 1204 */           .getClientReportRecorder()
/* 1205 */           .recordLostEvent(DiscardReason.BEFORE_SEND, DataCategory.LogByte, logEventNumberOfBytes);
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/* 1210 */       this.loggerBatchProcessor.add(logEvent);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   @Internal
/*      */   public void captureBatchedLogEvents(@NotNull SentryLogEvents logEvents) {
/*      */     try {
/* 1218 */       SentryEnvelope envelope = buildEnvelope(logEvents);
/* 1219 */       sendEnvelope(envelope, null);
/* 1220 */     } catch (IOException e) {
/* 1221 */       this.options.getLogger().log(SentryLevel.WARNING, e, "Capturing log failed.", new Object[0]);
/*      */     } 
/*      */   }
/*      */   @Nullable
/*      */   private List<Attachment> filterForTransaction(@Nullable List<Attachment> attachments) {
/* 1226 */     if (attachments == null) {
/* 1227 */       return null;
/*      */     }
/*      */     
/* 1230 */     List<Attachment> attachmentsToSend = new ArrayList<>();
/* 1231 */     for (Attachment attachment : attachments) {
/* 1232 */       if (attachment.isAddToTransactions()) {
/* 1233 */         attachmentsToSend.add(attachment);
/*      */       }
/*      */     } 
/*      */     
/* 1237 */     return attachmentsToSend;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   private SentryEvent applyScope(@NotNull SentryEvent event, @Nullable IScope scope, @NotNull Hint hint) {
/* 1242 */     if (scope != null) {
/* 1243 */       applyScope(event, scope);
/*      */       
/* 1245 */       if (event.getTransaction() == null) {
/* 1246 */         event.setTransaction(scope.getTransactionName());
/*      */       }
/* 1248 */       if (event.getFingerprints() == null) {
/* 1249 */         event.setFingerprints(scope.getFingerprint());
/*      */       }
/*      */       
/* 1252 */       if (scope.getLevel() != null) {
/* 1253 */         event.setLevel(scope.getLevel());
/*      */       }
/*      */       
/* 1256 */       ISpan span = scope.getSpan();
/* 1257 */       if (event.getContexts().getTrace() == null) {
/* 1258 */         if (span == null) {
/* 1259 */           event
/* 1260 */             .getContexts()
/* 1261 */             .setTrace(TransactionContext.fromPropagationContext(scope.getPropagationContext()));
/*      */         } else {
/* 1263 */           event.getContexts().setTrace(span.getSpanContext());
/*      */         } 
/*      */       }
/*      */       
/* 1267 */       if (event.getContexts().getFeatureFlags() == null) {
/* 1268 */         FeatureFlags featureFlags = scope.getFeatureFlags();
/* 1269 */         if (featureFlags != null) {
/* 1270 */           event.getContexts().setFeatureFlags(featureFlags);
/*      */         }
/*      */       } 
/*      */       
/* 1274 */       event = processEvent(event, hint, scope.getEventProcessors());
/*      */     } 
/* 1276 */     return event;
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   private SentryEvent applyFeedbackScope(@NotNull SentryEvent event, @NotNull IScope scope, @NotNull Hint hint) {
/* 1282 */     if (event.getUser() == null) {
/* 1283 */       event.setUser(scope.getUser());
/*      */     }
/* 1285 */     if (event.getTags() == null) {
/* 1286 */       event.setTags(new HashMap<>(scope.getTags()));
/*      */     } else {
/* 1288 */       for (Map.Entry<String, String> item : scope.getTags().entrySet()) {
/* 1289 */         if (!event.getTags().containsKey(item.getKey())) {
/* 1290 */           event.getTags().put(item.getKey(), item.getValue());
/*      */         }
/*      */       } 
/*      */     } 
/* 1294 */     Contexts contexts = event.getContexts();
/* 1295 */     for (Map.Entry<String, Object> entry : (Iterable<Map.Entry<String, Object>>)(new Contexts(scope.getContexts())).entrySet()) {
/* 1296 */       if (!contexts.containsKey(entry.getKey())) {
/* 1297 */         contexts.put(entry.getKey(), entry.getValue());
/*      */       }
/*      */     } 
/*      */     
/* 1301 */     ISpan span = scope.getSpan();
/* 1302 */     if (event.getContexts().getTrace() == null) {
/* 1303 */       if (span == null) {
/* 1304 */         event
/* 1305 */           .getContexts()
/* 1306 */           .setTrace(TransactionContext.fromPropagationContext(scope.getPropagationContext()));
/*      */       } else {
/* 1308 */         event.getContexts().setTrace(span.getSpanContext());
/*      */       } 
/*      */     }
/*      */     
/* 1312 */     event = processFeedbackEvent(event, hint, scope.getEventProcessors());
/* 1313 */     return event;
/*      */   }
/*      */   @NotNull
/*      */   private CheckIn applyScope(@NotNull CheckIn checkIn, @Nullable IScope scope) {
/* 1317 */     if (scope != null) {
/*      */       
/* 1319 */       ISpan span = scope.getSpan();
/* 1320 */       if (checkIn.getContexts().getTrace() == null) {
/* 1321 */         if (span == null) {
/* 1322 */           checkIn
/* 1323 */             .getContexts()
/* 1324 */             .setTrace(TransactionContext.fromPropagationContext(scope.getPropagationContext()));
/*      */         } else {
/* 1326 */           checkIn.getContexts().setTrace(span.getSpanContext());
/*      */         } 
/*      */       }
/*      */     } 
/* 1330 */     return checkIn;
/*      */   }
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   private SentryReplayEvent applyScope(@NotNull SentryReplayEvent replayEvent, @Nullable IScope scope) {
/* 1336 */     if (scope != null) {
/* 1337 */       if (replayEvent.getRequest() == null) {
/* 1338 */         replayEvent.setRequest(scope.getRequest());
/*      */       }
/* 1340 */       if (replayEvent.getUser() == null) {
/* 1341 */         replayEvent.setUser(scope.getUser());
/*      */       }
/* 1343 */       if (replayEvent.getTags() == null) {
/* 1344 */         replayEvent.setTags(new HashMap<>(scope.getTags()));
/*      */       } else {
/* 1346 */         for (Map.Entry<String, String> item : scope.getTags().entrySet()) {
/* 1347 */           if (!replayEvent.getTags().containsKey(item.getKey())) {
/* 1348 */             replayEvent.getTags().put(item.getKey(), item.getValue());
/*      */           }
/*      */         } 
/*      */       } 
/* 1352 */       Contexts contexts = replayEvent.getContexts();
/* 1353 */       for (Map.Entry<String, Object> entry : (Iterable<Map.Entry<String, Object>>)(new Contexts(scope.getContexts())).entrySet()) {
/* 1354 */         if (!contexts.containsKey(entry.getKey())) {
/* 1355 */           contexts.put(entry.getKey(), entry.getValue());
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 1360 */       ISpan span = scope.getSpan();
/* 1361 */       if (replayEvent.getContexts().getTrace() == null) {
/* 1362 */         if (span == null) {
/* 1363 */           replayEvent
/* 1364 */             .getContexts()
/* 1365 */             .setTrace(TransactionContext.fromPropagationContext(scope.getPropagationContext()));
/*      */         } else {
/* 1367 */           replayEvent.getContexts().setTrace(span.getSpanContext());
/*      */         } 
/*      */       }
/*      */     } 
/* 1371 */     return replayEvent;
/*      */   }
/*      */   
/*      */   @NotNull
/*      */   private <T extends SentryBaseEvent> T applyScope(@NotNull T sentryBaseEvent, @Nullable IScope scope) {
/* 1376 */     if (scope != null) {
/* 1377 */       if (sentryBaseEvent.getRequest() == null) {
/* 1378 */         sentryBaseEvent.setRequest(scope.getRequest());
/*      */       }
/* 1380 */       if (sentryBaseEvent.getUser() == null) {
/* 1381 */         sentryBaseEvent.setUser(scope.getUser());
/*      */       }
/* 1383 */       if (sentryBaseEvent.getTags() == null) {
/* 1384 */         sentryBaseEvent.setTags(new HashMap<>(scope.getTags()));
/*      */       } else {
/* 1386 */         for (Map.Entry<String, String> item : scope.getTags().entrySet()) {
/* 1387 */           if (!sentryBaseEvent.getTags().containsKey(item.getKey())) {
/* 1388 */             sentryBaseEvent.getTags().put(item.getKey(), item.getValue());
/*      */           }
/*      */         } 
/*      */       } 
/* 1392 */       if (sentryBaseEvent.getBreadcrumbs() == null) {
/* 1393 */         sentryBaseEvent.setBreadcrumbs(new ArrayList<>(scope.getBreadcrumbs()));
/*      */       } else {
/* 1395 */         sortBreadcrumbsByDate((SentryBaseEvent)sentryBaseEvent, scope.getBreadcrumbs());
/*      */       } 
/* 1397 */       if (sentryBaseEvent.getExtras() == null) {
/* 1398 */         sentryBaseEvent.setExtras(new HashMap<>(scope.getExtras()));
/*      */       } else {
/* 1400 */         for (Map.Entry<String, Object> item : scope.getExtras().entrySet()) {
/* 1401 */           if (!sentryBaseEvent.getExtras().containsKey(item.getKey())) {
/* 1402 */             sentryBaseEvent.getExtras().put(item.getKey(), item.getValue());
/*      */           }
/*      */         } 
/*      */       } 
/* 1406 */       Contexts contexts = sentryBaseEvent.getContexts();
/* 1407 */       for (Map.Entry<String, Object> entry : (Iterable<Map.Entry<String, Object>>)(new Contexts(scope.getContexts())).entrySet()) {
/* 1408 */         if (!contexts.containsKey(entry.getKey())) {
/* 1409 */           contexts.put(entry.getKey(), entry.getValue());
/*      */         }
/*      */       } 
/*      */     } 
/* 1413 */     return sentryBaseEvent;
/*      */   }
/*      */ 
/*      */   
/*      */   private void sortBreadcrumbsByDate(@NotNull SentryBaseEvent event, @NotNull Collection<Breadcrumb> breadcrumbs) {
/* 1418 */     List<Breadcrumb> sortedBreadcrumbs = event.getBreadcrumbs();
/*      */     
/* 1420 */     if (sortedBreadcrumbs != null && !breadcrumbs.isEmpty()) {
/* 1421 */       sortedBreadcrumbs.addAll(breadcrumbs);
/* 1422 */       Collections.sort(sortedBreadcrumbs, this.sortBreadcrumbsByDate);
/*      */     } 
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   private SentryEvent executeBeforeSend(@NotNull SentryEvent event, @NotNull Hint hint) {
/* 1428 */     SentryOptions.BeforeSendCallback beforeSend = this.options.getBeforeSend();
/* 1429 */     if (beforeSend != null) {
/*      */       try {
/* 1431 */         event = beforeSend.execute(event, hint);
/* 1432 */       } catch (Throwable e) {
/* 1433 */         this.options
/* 1434 */           .getLogger()
/* 1435 */           .log(SentryLevel.ERROR, "The BeforeSend callback threw an exception. It will be added as breadcrumb and continue.", e);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1441 */         event = null;
/*      */       } 
/*      */     }
/* 1444 */     return event;
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   private SentryTransaction executeBeforeSendTransaction(@NotNull SentryTransaction transaction, @NotNull Hint hint) {
/* 1450 */     SentryOptions.BeforeSendTransactionCallback beforeSendTransaction = this.options.getBeforeSendTransaction();
/* 1451 */     if (beforeSendTransaction != null) {
/*      */       try {
/* 1453 */         transaction = beforeSendTransaction.execute(transaction, hint);
/* 1454 */       } catch (Throwable e) {
/* 1455 */         this.options
/* 1456 */           .getLogger()
/* 1457 */           .log(SentryLevel.ERROR, "The BeforeSendTransaction callback threw an exception. It will be added as breadcrumb and continue.", e);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1463 */         transaction = null;
/*      */       } 
/*      */     }
/* 1466 */     return transaction;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   private SentryEvent executeBeforeSendFeedback(@NotNull SentryEvent event, @NotNull Hint hint) {
/* 1471 */     SentryOptions.BeforeSendCallback beforeSendFeedback = this.options.getBeforeSendFeedback();
/* 1472 */     if (beforeSendFeedback != null) {
/*      */       try {
/* 1474 */         event = beforeSendFeedback.execute(event, hint);
/* 1475 */       } catch (Throwable e) {
/* 1476 */         this.options
/* 1477 */           .getLogger()
/* 1478 */           .log(SentryLevel.ERROR, "The BeforeSendFeedback callback threw an exception.", e);
/*      */ 
/*      */         
/* 1481 */         event = null;
/*      */       } 
/*      */     }
/* 1484 */     return event;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   private SentryReplayEvent executeBeforeSendReplay(@NotNull SentryReplayEvent event, @NotNull Hint hint) {
/* 1489 */     SentryOptions.BeforeSendReplayCallback beforeSendReplay = this.options.getBeforeSendReplay();
/* 1490 */     if (beforeSendReplay != null) {
/*      */       try {
/* 1492 */         event = beforeSendReplay.execute(event, hint);
/* 1493 */       } catch (Throwable e) {
/* 1494 */         this.options
/* 1495 */           .getLogger()
/* 1496 */           .log(SentryLevel.ERROR, "The BeforeSendReplay callback threw an exception. It will be added as breadcrumb and continue.", e);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1502 */         event = null;
/*      */       } 
/*      */     }
/* 1505 */     return event;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   private SentryLogEvent executeBeforeSendLog(@NotNull SentryLogEvent event) {
/* 1510 */     SentryOptions.Logs.BeforeSendLogCallback beforeSendLog = this.options.getLogs().getBeforeSend();
/* 1511 */     if (beforeSendLog != null) {
/*      */       try {
/* 1513 */         event = beforeSendLog.execute(event);
/* 1514 */       } catch (Throwable e) {
/* 1515 */         this.options
/* 1516 */           .getLogger()
/* 1517 */           .log(SentryLevel.ERROR, "The BeforeSendLog callback threw an exception. Dropping log event.", e);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1523 */         event = null;
/*      */       } 
/*      */     }
/* 1526 */     return event;
/*      */   }
/*      */ 
/*      */   
/*      */   public void close() {
/* 1531 */     close(false);
/*      */   }
/*      */ 
/*      */   
/*      */   public void close(boolean isRestarting) {
/* 1536 */     this.options.getLogger().log(SentryLevel.INFO, "Closing SentryClient.", new Object[0]);
/*      */     try {
/* 1538 */       flush(isRestarting ? 0L : this.options.getShutdownTimeoutMillis());
/* 1539 */       this.loggerBatchProcessor.close(isRestarting);
/* 1540 */       this.transport.close(isRestarting);
/* 1541 */     } catch (IOException e) {
/* 1542 */       this.options
/* 1543 */         .getLogger()
/* 1544 */         .log(SentryLevel.WARNING, "Failed to close the connection to the Sentry Server.", e);
/*      */     } 
/* 1546 */     for (EventProcessor eventProcessor : this.options.getEventProcessors()) {
/* 1547 */       if (eventProcessor instanceof Closeable) {
/*      */         try {
/* 1549 */           ((Closeable)eventProcessor).close();
/* 1550 */         } catch (IOException e) {
/* 1551 */           this.options
/* 1552 */             .getLogger()
/* 1553 */             .log(SentryLevel.WARNING, "Failed to close the event processor {}.", new Object[] { eventProcessor, e });
/*      */         } 
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1561 */     this.enabled = false;
/*      */   }
/*      */ 
/*      */   
/*      */   public void flush(long timeoutMillis) {
/* 1566 */     this.loggerBatchProcessor.flush(timeoutMillis);
/* 1567 */     this.transport.flush(timeoutMillis);
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public RateLimiter getRateLimiter() {
/* 1572 */     return this.transport.getRateLimiter();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isHealthy() {
/* 1577 */     return this.transport.isHealthy();
/*      */   }
/*      */   
/*      */   private boolean sample() {
/* 1581 */     Random random = (this.options.getSampleRate() == null) ? null : SentryRandom.current();
/*      */     
/* 1583 */     if (this.options.getSampleRate() != null && random != null) {
/* 1584 */       double sampling = this.options.getSampleRate().doubleValue();
/* 1585 */       return (sampling >= random.nextDouble());
/*      */     } 
/* 1587 */     return true;
/*      */   }
/*      */   
/*      */   private static final class SortBreadcrumbsByDate
/*      */     implements Comparator<Breadcrumb> {
/*      */     private SortBreadcrumbsByDate() {}
/*      */     
/*      */     public int compare(@NotNull Breadcrumb b1, @NotNull Breadcrumb b2) {
/* 1595 */       return b1.getTimestamp().compareTo(b2.getTimestamp());
/*      */     }
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\SentryClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */