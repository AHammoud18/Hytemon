/*      */ package io.sentry;
/*      */ import io.sentry.protocol.Contexts;
/*      */ import io.sentry.protocol.SentryId;
/*      */ import io.sentry.protocol.SentryTransaction;
/*      */ import io.sentry.protocol.TransactionNameSource;
/*      */ import io.sentry.util.AutoClosableReentrantLock;
/*      */ import io.sentry.util.CollectionUtils;
/*      */ import io.sentry.util.Objects;
/*      */ import io.sentry.util.SpanUtils;
/*      */ import io.sentry.util.thread.IThreadChecker;
/*      */ import java.util.List;
/*      */ import java.util.ListIterator;
/*      */ import java.util.Map;
/*      */ import java.util.Timer;
/*      */ import java.util.TimerTask;
/*      */ import java.util.concurrent.CopyOnWriteArrayList;
/*      */ import java.util.concurrent.atomic.AtomicBoolean;
/*      */ import java.util.concurrent.atomic.AtomicReference;
/*      */ import org.jetbrains.annotations.ApiStatus.Internal;
/*      */ import org.jetbrains.annotations.NotNull;
/*      */ import org.jetbrains.annotations.Nullable;
/*      */ import org.jetbrains.annotations.TestOnly;
/*      */ 
/*      */ @Internal
/*      */ public final class SentryTracer implements ITransaction {
/*      */   @NotNull
/*   27 */   private final SentryId eventId = new SentryId(); @NotNull
/*      */   private final Span root; @NotNull
/*   29 */   private final List<Span> children = new CopyOnWriteArrayList<>();
/*      */   
/*      */   @NotNull
/*      */   private final IScopes scopes;
/*      */   
/*      */   @NotNull
/*      */   private String name;
/*      */   
/*      */   @NotNull
/*   38 */   private FinishStatus finishStatus = FinishStatus.NOT_FINISHED; @Nullable
/*      */   private volatile TimerTask idleTimeoutTask;
/*      */   @Nullable
/*      */   private volatile TimerTask deadlineTimeoutTask;
/*      */   @Nullable
/*   43 */   private volatile Timer timer = null; @NotNull
/*   44 */   private final AutoClosableReentrantLock timerLock = new AutoClosableReentrantLock(); @NotNull
/*   45 */   private final AutoClosableReentrantLock tracerLock = new AutoClosableReentrantLock();
/*      */   @NotNull
/*   47 */   private final AtomicBoolean isIdleFinishTimerRunning = new AtomicBoolean(false); @NotNull
/*   48 */   private final AtomicBoolean isDeadlineTimerRunning = new AtomicBoolean(false); @NotNull
/*      */   private TransactionNameSource transactionNameSource; @NotNull
/*      */   private final Instrumenter instrumenter;
/*      */   @NotNull
/*   52 */   private final Contexts contexts = new Contexts(); @Nullable
/*      */   private final CompositePerformanceCollector compositePerformanceCollector; @NotNull
/*      */   private final TransactionOptions transactionOptions;
/*      */   
/*      */   public SentryTracer(@NotNull TransactionContext context, @NotNull IScopes scopes) {
/*   57 */     this(context, scopes, new TransactionOptions(), null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SentryTracer(@NotNull TransactionContext context, @NotNull IScopes scopes, @NotNull TransactionOptions transactionOptions) {
/*   64 */     this(context, scopes, transactionOptions, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   SentryTracer(@NotNull TransactionContext context, @NotNull IScopes scopes, @NotNull TransactionOptions transactionOptions, @Nullable CompositePerformanceCollector compositePerformanceCollector) {
/*   72 */     Objects.requireNonNull(context, "context is required");
/*   73 */     Objects.requireNonNull(scopes, "scopes are required");
/*      */     
/*   75 */     this.root = new Span(context, this, scopes, transactionOptions);
/*      */     
/*   77 */     this.name = context.getName();
/*   78 */     this.instrumenter = context.getInstrumenter();
/*   79 */     this.scopes = scopes;
/*      */     
/*   81 */     this
/*   82 */       .compositePerformanceCollector = Boolean.TRUE.equals(isSampled()) ? compositePerformanceCollector : null;
/*   83 */     this.transactionNameSource = context.getTransactionNameSource();
/*   84 */     this.transactionOptions = transactionOptions;
/*      */     
/*   86 */     setDefaultSpanData(this.root);
/*      */     
/*   88 */     SentryId continuousProfilerId = getProfilerId();
/*      */     
/*   90 */     if (!continuousProfilerId.equals(SentryId.EMPTY_ID) && Boolean.TRUE.equals(isSampled())) {
/*   91 */       this.contexts.setProfile(new ProfileContext(continuousProfilerId));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*   96 */     if (this.compositePerformanceCollector != null) {
/*   97 */       this.compositePerformanceCollector.start(this);
/*      */     }
/*      */     
/*  100 */     if (transactionOptions.getIdleTimeout() != null || transactionOptions
/*  101 */       .getDeadlineTimeout() != null) {
/*  102 */       this.timer = new Timer(true);
/*      */       
/*  104 */       scheduleDeadlineTimeout();
/*  105 */       scheduleFinish();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void scheduleFinish() {
/*  111 */     ISentryLifecycleToken ignored = this.timerLock.acquire(); 
/*  112 */     try { if (this.timer != null) {
/*  113 */         Long idleTimeout = this.transactionOptions.getIdleTimeout();
/*      */         
/*  115 */         if (idleTimeout != null) {
/*  116 */           cancelIdleTimer();
/*  117 */           this.isIdleFinishTimerRunning.set(true);
/*  118 */           this.idleTimeoutTask = new TimerTask()
/*      */             {
/*      */               public void run()
/*      */               {
/*  122 */                 SentryTracer.this.onIdleTimeoutReached();
/*      */               }
/*      */             };
/*      */           
/*      */           try {
/*  127 */             this.timer.schedule(this.idleTimeoutTask, idleTimeout.longValue());
/*  128 */           } catch (Throwable e) {
/*  129 */             this.scopes
/*  130 */               .getOptions()
/*  131 */               .getLogger()
/*  132 */               .log(SentryLevel.WARNING, "Failed to schedule finish timer", e);
/*      */ 
/*      */             
/*  135 */             onIdleTimeoutReached();
/*      */           } 
/*      */         } 
/*      */       } 
/*  139 */       if (ignored != null) ignored.close();  } catch (Throwable throwable) { if (ignored != null)
/*      */         try { ignored.close(); }
/*      */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*      */           throw throwable; }
/*  143 */      } private void onIdleTimeoutReached() { SpanStatus status = getStatus();
/*  144 */     finish((status != null) ? status : SpanStatus.OK);
/*  145 */     this.isIdleFinishTimerRunning.set(false); }
/*      */ 
/*      */   
/*      */   private void onDeadlineTimeoutReached() {
/*  149 */     SpanStatus status = getStatus();
/*  150 */     forceFinish(
/*  151 */         (status != null) ? status : SpanStatus.DEADLINE_EXCEEDED, 
/*  152 */         (this.transactionOptions.getIdleTimeout() != null), null);
/*      */     
/*  154 */     this.isDeadlineTimerRunning.set(false);
/*      */   }
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public void forceFinish(@NotNull SpanStatus status, boolean dropIfNoChildren, @Nullable Hint hint) {
/*  160 */     if (isFinished()) {
/*      */       return;
/*      */     }
/*      */     
/*  164 */     SentryDate finishTimestamp = this.scopes.getOptions().getDateProvider().now();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  171 */     ListIterator<Span> iterator = CollectionUtils.reverseListIterator((CopyOnWriteArrayList)this.children);
/*  172 */     while (iterator.hasPrevious()) {
/*  173 */       Span span = iterator.previous();
/*  174 */       span.setSpanFinishedCallback(null);
/*  175 */       span.finish(status, finishTimestamp);
/*      */     } 
/*  177 */     finish(status, finishTimestamp, dropIfNoChildren, hint);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void finish(@Nullable SpanStatus status, @Nullable SentryDate finishDate, boolean dropIfNoChildren, @Nullable Hint hint) {
/*  187 */     SentryDate finishTimestamp = this.root.getFinishDate();
/*      */ 
/*      */     
/*  190 */     if (finishDate != null) {
/*  191 */       finishTimestamp = finishDate;
/*      */     }
/*      */ 
/*      */     
/*  195 */     if (finishTimestamp == null) {
/*  196 */       finishTimestamp = this.scopes.getOptions().getDateProvider().now();
/*      */     }
/*      */ 
/*      */     
/*  200 */     for (Span span : this.children) {
/*  201 */       if (span.getOptions().isIdle()) {
/*  202 */         span.finish((status != null) ? status : (getSpanContext()).status, finishTimestamp);
/*      */       }
/*      */     } 
/*      */     
/*  206 */     this.finishStatus = FinishStatus.finishing(status);
/*  207 */     if (!this.root.isFinished() && (
/*  208 */       !this.transactionOptions.isWaitForChildren() || hasAllChildrenFinished())) {
/*      */       
/*  210 */       AtomicReference<List<PerformanceCollectionData>> performanceCollectionData = new AtomicReference<>();
/*      */ 
/*      */ 
/*      */       
/*  214 */       SpanFinishedCallback oldCallback = this.root.getSpanFinishedCallback();
/*  215 */       this.root.setSpanFinishedCallback(span -> {
/*      */             if (oldCallback != null) {
/*      */               oldCallback.execute(span);
/*      */             }
/*      */ 
/*      */ 
/*      */             
/*      */             TransactionFinishedCallback finishedCallback = this.transactionOptions.getTransactionFinishedCallback();
/*      */ 
/*      */ 
/*      */             
/*      */             if (finishedCallback != null) {
/*      */               finishedCallback.execute(this);
/*      */             }
/*      */ 
/*      */ 
/*      */             
/*      */             if (this.compositePerformanceCollector != null) {
/*      */               performanceCollectionData.set(this.compositePerformanceCollector.stop(this));
/*      */             }
/*      */           });
/*      */ 
/*      */       
/*  238 */       this.root.finish(this.finishStatus.spanStatus, finishTimestamp);
/*      */       
/*  240 */       ProfilingTraceData profilingTraceData = null;
/*  241 */       if (Boolean.TRUE.equals(isSampled()) && Boolean.TRUE.equals(isProfileSampled()))
/*      */       {
/*      */ 
/*      */ 
/*      */         
/*  246 */         profilingTraceData = this.scopes.getOptions().getTransactionProfiler().onTransactionFinish(this, performanceCollectionData.get(), this.scopes.getOptions());
/*      */       }
/*  248 */       if (this.scopes.getOptions().isContinuousProfilingEnabled() && this.scopes
/*  249 */         .getOptions().getProfileLifecycle() == ProfileLifecycle.TRACE && this.root
/*  250 */         .getSpanContext().getProfilerId().equals(SentryId.EMPTY_ID)) {
/*  251 */         this.scopes.getOptions().getContinuousProfiler().stopProfiler(ProfileLifecycle.TRACE);
/*      */       }
/*  253 */       if (performanceCollectionData.get() != null) {
/*  254 */         ((List)performanceCollectionData.get()).clear();
/*      */       }
/*      */       
/*  257 */       this.scopes.configureScope(scope -> scope.withTransaction(()));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  266 */       SentryTransaction transaction = new SentryTransaction(this);
/*      */       
/*  268 */       if (this.timer != null) {
/*  269 */         ISentryLifecycleToken ignored = this.timerLock.acquire(); 
/*  270 */         try { if (this.timer != null) {
/*  271 */             cancelIdleTimer();
/*  272 */             cancelDeadlineTimer();
/*  273 */             this.timer.cancel();
/*  274 */             this.timer = null;
/*      */           } 
/*  276 */           if (ignored != null) ignored.close();  } catch (Throwable throwable) { if (ignored != null)
/*      */             try { ignored.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }
/*      */       
/*  279 */       }  if (dropIfNoChildren && this.children.isEmpty() && this.transactionOptions.getIdleTimeout() != null) {
/*      */         
/*  281 */         this.scopes
/*  282 */           .getOptions()
/*  283 */           .getLogger()
/*  284 */           .log(SentryLevel.DEBUG, "Dropping idle transaction %s because it has no child spans", new Object[] { this.name });
/*      */ 
/*      */         
/*      */         return;
/*      */       } 
/*      */ 
/*      */       
/*  291 */       transaction.getMeasurements().putAll(this.root.getMeasurements());
/*  292 */       this.scopes.captureTransaction(transaction, traceContext(), hint, profilingTraceData);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void cancelIdleTimer() {
/*  297 */     ISentryLifecycleToken ignored = this.timerLock.acquire(); 
/*  298 */     try { if (this.idleTimeoutTask != null) {
/*  299 */         this.idleTimeoutTask.cancel();
/*  300 */         this.isIdleFinishTimerRunning.set(false);
/*  301 */         this.idleTimeoutTask = null;
/*      */       } 
/*  303 */       if (ignored != null) ignored.close();  } catch (Throwable throwable) { if (ignored != null)
/*      */         try { ignored.close(); }
/*      */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*      */           throw throwable; }
/*  307 */      } private void scheduleDeadlineTimeout() { Long deadlineTimeOut = this.transactionOptions.getDeadlineTimeout();
/*  308 */     if (deadlineTimeOut != null) {
/*  309 */       ISentryLifecycleToken ignored = this.timerLock.acquire(); 
/*  310 */       try { if (this.timer != null) {
/*  311 */           cancelDeadlineTimer();
/*  312 */           this.isDeadlineTimerRunning.set(true);
/*  313 */           this.deadlineTimeoutTask = new TimerTask()
/*      */             {
/*      */               public void run()
/*      */               {
/*  317 */                 SentryTracer.this.onDeadlineTimeoutReached();
/*      */               }
/*      */             };
/*      */           try {
/*  321 */             this.timer.schedule(this.deadlineTimeoutTask, deadlineTimeOut.longValue());
/*  322 */           } catch (Throwable e) {
/*  323 */             this.scopes
/*  324 */               .getOptions()
/*  325 */               .getLogger()
/*  326 */               .log(SentryLevel.WARNING, "Failed to schedule finish timer", e);
/*      */ 
/*      */             
/*  329 */             onDeadlineTimeoutReached();
/*      */           } 
/*      */         } 
/*  332 */         if (ignored != null) ignored.close();  } catch (Throwable throwable) { if (ignored != null)
/*      */           try { ignored.close(); }
/*      */           catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*      */             throw throwable; }
/*      */     
/*  337 */     }  } private void cancelDeadlineTimer() { ISentryLifecycleToken ignored = this.timerLock.acquire(); 
/*  338 */     try { if (this.deadlineTimeoutTask != null) {
/*  339 */         this.deadlineTimeoutTask.cancel();
/*  340 */         this.isDeadlineTimerRunning.set(false);
/*  341 */         this.deadlineTimeoutTask = null;
/*      */       } 
/*  343 */       if (ignored != null) ignored.close();  } catch (Throwable throwable) { if (ignored != null)
/*      */         try { ignored.close(); }
/*      */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*      */           throw throwable; }
/*  347 */      } @NotNull public List<Span> getChildren() { return this.children; }
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public SentryDate getStartDate() {
/*  352 */     return this.root.getStartDate();
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public SentryDate getFinishDate() {
/*  357 */     return this.root.getFinishDate();
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
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   ISpan startChild(@NotNull SpanId parentSpanId, @NotNull String operation, @Nullable String description) {
/*  373 */     return startChild(parentSpanId, operation, description, new SpanOptions());
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   ISpan startChild(@NotNull SpanId parentSpanId, @NotNull String operation, @Nullable String description, @NotNull SpanOptions spanOptions) {
/*  391 */     return createChild(parentSpanId, operation, description, spanOptions);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   ISpan startChild(@NotNull SpanId parentSpanId, @NotNull String operation, @Nullable String description, @Nullable SentryDate timestamp, @NotNull Instrumenter instrumenter) {
/*  402 */     SpanContext spanContext = getSpanContext().copyForChild(operation, parentSpanId, null);
/*  403 */     spanContext.setDescription(description);
/*  404 */     spanContext.setInstrumenter(instrumenter);
/*      */     
/*  406 */     SpanOptions spanOptions = new SpanOptions();
/*  407 */     spanOptions.setStartTimestamp(timestamp);
/*      */     
/*  409 */     return createChild(spanContext, spanOptions);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   ISpan startChild(@NotNull SpanId parentSpanId, @NotNull String operation, @Nullable String description, @Nullable SentryDate timestamp, @NotNull Instrumenter instrumenter, @NotNull SpanOptions spanOptions) {
/*  421 */     SpanContext spanContext = getSpanContext().copyForChild(operation, parentSpanId, null);
/*  422 */     spanContext.setDescription(description);
/*  423 */     spanContext.setInstrumenter(instrumenter);
/*      */     
/*  425 */     spanOptions.setStartTimestamp(timestamp);
/*      */     
/*  427 */     return createChild(spanContext, spanOptions);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   private ISpan createChild(@NotNull SpanId parentSpanId, @NotNull String operation, @Nullable String description, @NotNull SpanOptions options) {
/*  446 */     SpanContext spanContext = getSpanContext().copyForChild(operation, parentSpanId, null);
/*  447 */     spanContext.setDescription(description);
/*  448 */     spanContext.setInstrumenter(Instrumenter.SENTRY);
/*      */     
/*  450 */     return createChild(spanContext, options);
/*      */   }
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   private ISpan createChild(@NotNull SpanContext spanContext, @NotNull SpanOptions spanOptions) {
/*  456 */     if (this.root.isFinished()) {
/*  457 */       return NoOpSpan.getInstance();
/*      */     }
/*      */     
/*  460 */     if (!this.instrumenter.equals(spanContext.getInstrumenter())) {
/*  461 */       return NoOpSpan.getInstance();
/*      */     }
/*      */     
/*  464 */     if (SpanUtils.isIgnored(this.scopes.getOptions().getIgnoredSpanOrigins(), spanOptions.getOrigin())) {
/*  465 */       return NoOpSpan.getInstance();
/*      */     }
/*      */     
/*  468 */     SpanId parentSpanId = spanContext.getParentSpanId();
/*  469 */     String operation = spanContext.getOperation();
/*  470 */     String description = spanContext.getDescription();
/*      */     
/*  472 */     if (this.children.size() < this.scopes.getOptions().getMaxSpans()) {
/*  473 */       Objects.requireNonNull(parentSpanId, "parentSpanId is required");
/*  474 */       Objects.requireNonNull(operation, "operation is required");
/*  475 */       cancelIdleTimer();
/*  476 */       Span span = new Span(this, this.scopes, spanContext, spanOptions, finishingSpan -> {
/*      */             if (this.compositePerformanceCollector != null) {
/*      */               this.compositePerformanceCollector.onSpanFinished(finishingSpan);
/*      */             }
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
/*      */ 
/*      */ 
/*      */             
/*      */             FinishStatus finishStatus = this.finishStatus;
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
/*      */ 
/*      */ 
/*      */             
/*      */             if (this.transactionOptions.getIdleTimeout() != null) {
/*      */               if (!this.transactionOptions.isWaitForChildren() || hasAllChildrenFinished()) {
/*      */                 scheduleFinish();
/*      */               }
/*      */             } else if (finishStatus.isFinishing) {
/*      */               finish(finishStatus.spanStatus);
/*      */             } 
/*      */           });
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
/*      */ 
/*      */       
/*  527 */       setDefaultSpanData(span);
/*  528 */       this.children.add(span);
/*  529 */       if (this.compositePerformanceCollector != null) {
/*  530 */         this.compositePerformanceCollector.onSpanStarted(span);
/*      */       }
/*  532 */       return span;
/*      */     } 
/*  534 */     this.scopes
/*  535 */       .getOptions()
/*  536 */       .getLogger()
/*  537 */       .log(SentryLevel.WARNING, "Span operation: %s, description: %s dropped due to limit reached. Returning NoOpSpan.", new Object[] { operation, description });
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  542 */     return NoOpSpan.getInstance();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void setDefaultSpanData(@NotNull ISpan span) {
/*  548 */     IThreadChecker threadChecker = this.scopes.getOptions().getThreadChecker();
/*  549 */     SentryId profilerId = getProfilerId();
/*  550 */     if (!profilerId.equals(SentryId.EMPTY_ID) && Boolean.TRUE.equals(span.isSampled())) {
/*  551 */       span.setData("profiler_id", profilerId.toString());
/*      */     }
/*  553 */     span.setData("thread.id", 
/*  554 */         String.valueOf(threadChecker.currentThreadSystemId()));
/*  555 */     span.setData("thread.name", threadChecker.getCurrentThreadName());
/*      */   }
/*      */   @NotNull
/*      */   private SentryId getProfilerId() {
/*  559 */     return !this.root.getSpanContext().getProfilerId().equals(SentryId.EMPTY_ID) ? 
/*  560 */       this.root.getSpanContext().getProfilerId() : 
/*  561 */       this.scopes.getOptions().getContinuousProfiler().getProfilerId();
/*      */   }
/*      */   
/*      */   @NotNull
/*      */   public ISpan startChild(@NotNull String operation) {
/*  566 */     return startChild(operation, (String)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public ISpan startChild(@NotNull String operation, @Nullable String description, @Nullable SentryDate timestamp, @NotNull Instrumenter instrumenter) {
/*  575 */     return startChild(operation, description, timestamp, instrumenter, new SpanOptions());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public ISpan startChild(@NotNull String operation, @Nullable String description, @Nullable SentryDate timestamp, @NotNull Instrumenter instrumenter, @NotNull SpanOptions spanOptions) {
/*  585 */     return createChild(operation, description, timestamp, instrumenter, spanOptions);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public ISpan startChild(@NotNull String operation, @Nullable String description, @Nullable SentryDate timestamp) {
/*  593 */     return createChild(operation, description, timestamp, Instrumenter.SENTRY, new SpanOptions());
/*      */   }
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public ISpan startChild(@NotNull String operation, @Nullable String description) {
/*  599 */     return startChild(operation, description, (SentryDate)null, Instrumenter.SENTRY, new SpanOptions());
/*      */   }
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public ISpan startChild(@NotNull String operation, @Nullable String description, @NotNull SpanOptions spanOptions) {
/*  605 */     return createChild(operation, description, null, Instrumenter.SENTRY, spanOptions);
/*      */   }
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public ISpan startChild(@NotNull SpanContext spanContext, @NotNull SpanOptions spanOptions) {
/*  611 */     return createChild(spanContext, spanOptions);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   private ISpan createChild(@NotNull String operation, @Nullable String description, @Nullable SentryDate timestamp, @NotNull Instrumenter instrumenter, @NotNull SpanOptions spanOptions) {
/*  620 */     if (this.root.isFinished()) {
/*  621 */       return NoOpSpan.getInstance();
/*      */     }
/*      */     
/*  624 */     if (!this.instrumenter.equals(instrumenter)) {
/*  625 */       return NoOpSpan.getInstance();
/*      */     }
/*      */     
/*  628 */     if (this.children.size() < this.scopes.getOptions().getMaxSpans()) {
/*  629 */       return this.root.startChild(operation, description, timestamp, instrumenter, spanOptions);
/*      */     }
/*  631 */     this.scopes
/*  632 */       .getOptions()
/*  633 */       .getLogger()
/*  634 */       .log(SentryLevel.WARNING, "Span operation: %s, description: %s dropped due to limit reached. Returning NoOpSpan.", new Object[] { operation, description });
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  639 */     return NoOpSpan.getInstance();
/*      */   }
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public SentryTraceHeader toSentryTrace() {
/*  645 */     return this.root.toSentryTrace();
/*      */   }
/*      */ 
/*      */   
/*      */   public void finish() {
/*  650 */     finish(getStatus());
/*      */   }
/*      */ 
/*      */   
/*      */   public void finish(@Nullable SpanStatus status) {
/*  655 */     finish(status, null);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @Internal
/*      */   public void finish(@Nullable SpanStatus status, @Nullable SentryDate finishDate) {
/*  662 */     finish(status, finishDate, true, null);
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public TraceContext traceContext() {
/*  667 */     if (this.scopes.getOptions().isTraceSampling()) {
/*  668 */       Baggage baggage = getSpanContext().getBaggage();
/*  669 */       if (baggage != null) {
/*  670 */         updateBaggageValues(baggage);
/*  671 */         return baggage.toTraceContext();
/*      */       } 
/*      */     } 
/*  674 */     return null;
/*      */   }
/*      */   
/*      */   private void updateBaggageValues(@NotNull Baggage baggage) {
/*  678 */     ISentryLifecycleToken ignored = this.tracerLock.acquire(); 
/*  679 */     try { if (baggage.isMutable()) {
/*  680 */         AtomicReference<SentryId> replayId = new AtomicReference<>();
/*  681 */         this.scopes.configureScope(scope -> replayId.set(scope.getReplayId()));
/*      */ 
/*      */ 
/*      */         
/*  685 */         baggage.setValuesFromTransaction(
/*  686 */             getSpanContext().getTraceId(), replayId
/*  687 */             .get(), this.scopes
/*  688 */             .getOptions(), 
/*  689 */             getSamplingDecision(), 
/*  690 */             getName(), 
/*  691 */             getTransactionNameSource());
/*  692 */         baggage.freeze();
/*      */       } 
/*  694 */       if (ignored != null) ignored.close();  }
/*      */     catch (Throwable throwable) { if (ignored != null)
/*      */         try { ignored.close(); }
/*      */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*      */           throw throwable; }
/*  699 */      } @Nullable public BaggageHeader toBaggageHeader(@Nullable List<String> thirdPartyBaggageHeaders) { if (this.scopes.getOptions().isTraceSampling()) {
/*  700 */       Baggage baggage = getSpanContext().getBaggage();
/*  701 */       if (baggage != null) {
/*  702 */         updateBaggageValues(baggage);
/*  703 */         return BaggageHeader.fromBaggageAndOutgoingHeader(baggage, thirdPartyBaggageHeaders);
/*      */       } 
/*      */     } 
/*  706 */     return null; }
/*      */ 
/*      */   
/*      */   private boolean hasAllChildrenFinished() {
/*  710 */     ListIterator<Span> iterator = this.children.listIterator();
/*  711 */     while (iterator.hasNext()) {
/*  712 */       Span span = iterator.next();
/*      */ 
/*      */       
/*  715 */       if (!span.isFinished() && span.getFinishDate() == null) {
/*  716 */         return false;
/*      */       }
/*      */     } 
/*  719 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setOperation(@NotNull String operation) {
/*  724 */     if (this.root.isFinished()) {
/*  725 */       this.scopes
/*  726 */         .getOptions()
/*  727 */         .getLogger()
/*  728 */         .log(SentryLevel.DEBUG, "The transaction is already finished. Operation %s cannot be set", new Object[] { operation });
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */ 
/*      */     
/*  735 */     this.root.setOperation(operation);
/*      */   }
/*      */   
/*      */   @NotNull
/*      */   public String getOperation() {
/*  740 */     return this.root.getOperation();
/*      */   }
/*      */ 
/*      */   
/*      */   public void setDescription(@Nullable String description) {
/*  745 */     if (this.root.isFinished()) {
/*  746 */       this.scopes
/*  747 */         .getOptions()
/*  748 */         .getLogger()
/*  749 */         .log(SentryLevel.DEBUG, "The transaction is already finished. Description %s cannot be set", new Object[] { description });
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */ 
/*      */     
/*  756 */     this.root.setDescription(description);
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public String getDescription() {
/*  761 */     return this.root.getDescription();
/*      */   }
/*      */ 
/*      */   
/*      */   public void setStatus(@Nullable SpanStatus status) {
/*  766 */     if (this.root.isFinished()) {
/*  767 */       this.scopes
/*  768 */         .getOptions()
/*  769 */         .getLogger()
/*  770 */         .log(SentryLevel.DEBUG, "The transaction is already finished. Status %s cannot be set", new Object[] {
/*      */ 
/*      */             
/*  773 */             (status == null) ? "null" : status.name()
/*      */           });
/*      */       return;
/*      */     } 
/*  777 */     this.root.setStatus(status);
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public SpanStatus getStatus() {
/*  782 */     return this.root.getStatus();
/*      */   }
/*      */ 
/*      */   
/*      */   public void setThrowable(@Nullable Throwable throwable) {
/*  787 */     if (this.root.isFinished()) {
/*  788 */       this.scopes
/*  789 */         .getOptions()
/*  790 */         .getLogger()
/*  791 */         .log(SentryLevel.DEBUG, "The transaction is already finished. Throwable cannot be set", new Object[0]);
/*      */       
/*      */       return;
/*      */     } 
/*  795 */     this.root.setThrowable(throwable);
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public Throwable getThrowable() {
/*  800 */     return this.root.getThrowable();
/*      */   }
/*      */   
/*      */   @NotNull
/*      */   public SpanContext getSpanContext() {
/*  805 */     return this.root.getSpanContext();
/*      */   }
/*      */ 
/*      */   
/*      */   public void setTag(@Nullable String key, @Nullable String value) {
/*  810 */     if (this.root.isFinished()) {
/*  811 */       this.scopes
/*  812 */         .getOptions()
/*  813 */         .getLogger()
/*  814 */         .log(SentryLevel.DEBUG, "The transaction is already finished. Tag %s cannot be set", new Object[] { key });
/*      */       
/*      */       return;
/*      */     } 
/*  818 */     this.root.setTag(key, value);
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public String getTag(@Nullable String key) {
/*  823 */     return this.root.getTag(key);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isFinished() {
/*  828 */     return this.root.isFinished();
/*      */   }
/*      */ 
/*      */   
/*      */   public void setData(@Nullable String key, @Nullable Object value) {
/*  833 */     if (this.root.isFinished()) {
/*  834 */       this.scopes
/*  835 */         .getOptions()
/*  836 */         .getLogger()
/*  837 */         .log(SentryLevel.DEBUG, "The transaction is already finished. Data %s cannot be set", new Object[] { key });
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  842 */     this.root.setData(key, value);
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public Object getData(@Nullable String key) {
/*  847 */     return this.root.getData(key);
/*      */   }
/*      */ 
/*      */   
/*      */   @Internal
/*      */   public void setMeasurementFromChild(@NotNull String name, @NotNull Number value) {
/*  853 */     if (!this.root.getMeasurements().containsKey(name)) {
/*  854 */       setMeasurement(name, value);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Internal
/*      */   public void setMeasurementFromChild(@NotNull String name, @NotNull Number value, @NotNull MeasurementUnit unit) {
/*  864 */     if (!this.root.getMeasurements().containsKey(name)) {
/*  865 */       setMeasurement(name, value, unit);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void setMeasurement(@NotNull String name, @NotNull Number value) {
/*  871 */     this.root.setMeasurement(name, value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMeasurement(@NotNull String name, @NotNull Number value, @NotNull MeasurementUnit unit) {
/*  879 */     this.root.setMeasurement(name, value, unit);
/*      */   }
/*      */   @Nullable
/*      */   public Map<String, Object> getData() {
/*  883 */     return this.root.getData();
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public Boolean isSampled() {
/*  888 */     return this.root.isSampled();
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public Boolean isProfileSampled() {
/*  893 */     return this.root.isProfileSampled();
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public TracesSamplingDecision getSamplingDecision() {
/*  898 */     return this.root.getSamplingDecision();
/*      */   }
/*      */ 
/*      */   
/*      */   public void setName(@NotNull String name) {
/*  903 */     setName(name, TransactionNameSource.CUSTOM);
/*      */   }
/*      */ 
/*      */   
/*      */   @Internal
/*      */   public void setName(@NotNull String name, @NotNull TransactionNameSource transactionNameSource) {
/*  909 */     if (this.root.isFinished()) {
/*  910 */       this.scopes
/*  911 */         .getOptions()
/*  912 */         .getLogger()
/*  913 */         .log(SentryLevel.DEBUG, "The transaction is already finished. Name %s cannot be set", new Object[] { name });
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */ 
/*      */     
/*  920 */     this.name = name;
/*  921 */     this.transactionNameSource = transactionNameSource;
/*      */   }
/*      */   
/*      */   @NotNull
/*      */   public String getName() {
/*  926 */     return this.name;
/*      */   }
/*      */   
/*      */   @NotNull
/*      */   public TransactionNameSource getTransactionNameSource() {
/*  931 */     return this.transactionNameSource;
/*      */   }
/*      */   
/*      */   @NotNull
/*      */   public List<Span> getSpans() {
/*  936 */     return this.children;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public ISpan getLatestActiveSpan() {
/*  943 */     ListIterator<Span> iterator = CollectionUtils.reverseListIterator((CopyOnWriteArrayList)this.children);
/*  944 */     while (iterator.hasPrevious()) {
/*  945 */       Span span = iterator.previous();
/*  946 */       if (!span.isFinished()) {
/*  947 */         return span;
/*      */       }
/*      */     } 
/*  950 */     return null;
/*      */   }
/*      */   
/*      */   @NotNull
/*      */   public SentryId getEventId() {
/*  955 */     return this.eventId;
/*      */   }
/*      */   
/*      */   @Internal
/*      */   @NotNull
/*      */   public ISentryLifecycleToken makeCurrent() {
/*  961 */     this.scopes.configureScope(scope -> scope.setTransaction(this));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  966 */     return NoOpScopesLifecycleToken.getInstance();
/*      */   }
/*      */   
/*      */   @NotNull
/*      */   Span getRoot() {
/*  971 */     return this.root;
/*      */   }
/*      */   
/*      */   @TestOnly
/*      */   @Nullable
/*      */   TimerTask getIdleTimeoutTask() {
/*  977 */     return this.idleTimeoutTask;
/*      */   }
/*      */   
/*      */   @TestOnly
/*      */   @Nullable
/*      */   TimerTask getDeadlineTimeoutTask() {
/*  983 */     return this.deadlineTimeoutTask;
/*      */   }
/*      */   
/*      */   @TestOnly
/*      */   @Nullable
/*      */   Timer getTimer() {
/*  989 */     return this.timer;
/*      */   }
/*      */   
/*      */   @TestOnly
/*      */   @NotNull
/*      */   AtomicBoolean isFinishTimerRunning() {
/*  995 */     return this.isIdleFinishTimerRunning;
/*      */   }
/*      */   
/*      */   @TestOnly
/*      */   @NotNull
/*      */   AtomicBoolean isDeadlineTimerRunning() {
/* 1001 */     return this.isDeadlineTimerRunning;
/*      */   }
/*      */ 
/*      */   
/*      */   @Internal
/*      */   public void setContext(@Nullable String key, @Nullable Object context) {
/* 1007 */     this.contexts.put(key, context);
/*      */   }
/*      */   
/*      */   @Internal
/*      */   @NotNull
/*      */   public Contexts getContexts() {
/* 1013 */     return this.contexts;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean updateEndDate(@NotNull SentryDate date) {
/* 1018 */     return this.root.updateEndDate(date);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isNoOp() {
/* 1023 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public void addFeatureFlag(@Nullable String flag, @Nullable Boolean result) {
/* 1028 */     this.root.addFeatureFlag(flag, result);
/*      */   }
/*      */   
/*      */   private static final class FinishStatus {
/* 1032 */     static final FinishStatus NOT_FINISHED = notFinished(); private final boolean isFinishing;
/*      */     @Nullable
/*      */     private final SpanStatus spanStatus;
/*      */     
/*      */     @NotNull
/*      */     static FinishStatus finishing(@Nullable SpanStatus finishStatus) {
/* 1038 */       return new FinishStatus(true, finishStatus);
/*      */     }
/*      */     @NotNull
/*      */     private static FinishStatus notFinished() {
/* 1042 */       return new FinishStatus(false, null);
/*      */     }
/*      */     
/*      */     private FinishStatus(boolean isFinishing, @Nullable SpanStatus spanStatus) {
/* 1046 */       this.isFinishing = isFinishing;
/* 1047 */       this.spanStatus = spanStatus;
/*      */     }
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\SentryTracer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */