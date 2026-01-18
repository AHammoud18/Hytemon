/*      */ package io.sentry;
/*      */ import io.sentry.clientreport.DiscardReason;
/*      */ import io.sentry.hints.SessionEndHint;
/*      */ import io.sentry.hints.SessionStartHint;
/*      */ import io.sentry.logger.ILoggerApi;
/*      */ import io.sentry.logger.LoggerApi;
/*      */ import io.sentry.protocol.Feedback;
/*      */ import io.sentry.protocol.SentryId;
/*      */ import io.sentry.protocol.SentryTransaction;
/*      */ import io.sentry.protocol.User;
/*      */ import io.sentry.util.HintUtils;
/*      */ import io.sentry.util.Objects;
/*      */ import io.sentry.util.SpanUtils;
/*      */ import io.sentry.util.TracingUtils;
/*      */ import java.util.List;
/*      */ import java.util.concurrent.RejectedExecutionException;
/*      */ import org.jetbrains.annotations.ApiStatus.Internal;
/*      */ import org.jetbrains.annotations.NotNull;
/*      */ import org.jetbrains.annotations.Nullable;
/*      */ 
/*      */ public final class Scopes implements IScopes {
/*      */   @NotNull
/*      */   private final IScope scope;
/*      */   @NotNull
/*      */   private final IScope isolationScope;
/*      */   @NotNull
/*      */   private final IScope globalScope;
/*      */   @Nullable
/*      */   private final Scopes parentScopes;
/*      */   @NotNull
/*      */   private final String creator;
/*      */   @NotNull
/*      */   private final CompositePerformanceCollector compositePerformanceCollector;
/*      */   @NotNull
/*      */   private final CombinedScopeView combinedScope;
/*      */   @NotNull
/*      */   private final ILoggerApi logger;
/*      */   
/*      */   public Scopes(@NotNull IScope scope, @NotNull IScope isolationScope, @NotNull IScope globalScope, @NotNull String creator) {
/*   40 */     this(scope, isolationScope, globalScope, null, creator);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Scopes(@NotNull IScope scope, @NotNull IScope isolationScope, @NotNull IScope globalScope, @Nullable Scopes parentScopes, @NotNull String creator) {
/*   49 */     this.combinedScope = new CombinedScopeView(globalScope, isolationScope, scope);
/*   50 */     this.scope = scope;
/*   51 */     this.isolationScope = isolationScope;
/*   52 */     this.globalScope = globalScope;
/*   53 */     this.parentScopes = parentScopes;
/*   54 */     this.creator = creator;
/*      */     
/*   56 */     SentryOptions options = getOptions();
/*   57 */     validateOptions(options);
/*   58 */     this.compositePerformanceCollector = options.getCompositePerformanceCollector();
/*   59 */     this.logger = (ILoggerApi)new LoggerApi(this);
/*      */   }
/*      */   @NotNull
/*      */   public String getCreator() {
/*   63 */     return this.creator;
/*      */   }
/*      */   
/*      */   @Internal
/*      */   @NotNull
/*      */   public IScope getScope() {
/*   69 */     return this.scope;
/*      */   }
/*      */   
/*      */   @Internal
/*      */   @NotNull
/*      */   public IScope getIsolationScope() {
/*   75 */     return this.isolationScope;
/*      */   }
/*      */   
/*      */   @Internal
/*      */   @NotNull
/*      */   public IScope getGlobalScope() {
/*   81 */     return this.globalScope;
/*      */   }
/*      */   
/*      */   @Internal
/*      */   @Nullable
/*      */   public IScopes getParentScopes() {
/*   87 */     return this.parentScopes;
/*      */   }
/*      */ 
/*      */   
/*      */   @Internal
/*      */   public boolean isAncestorOf(@Nullable IScopes otherScopes) {
/*   93 */     if (otherScopes == null) {
/*   94 */       return false;
/*      */     }
/*      */     
/*   97 */     if (this == otherScopes) {
/*   98 */       return true;
/*      */     }
/*      */     
/*  101 */     if (otherScopes.getParentScopes() != null) {
/*  102 */       return isAncestorOf(otherScopes.getParentScopes());
/*      */     }
/*      */     
/*  105 */     return false;
/*      */   }
/*      */   
/*      */   @NotNull
/*      */   public IScopes forkedScopes(@NotNull String creator) {
/*  110 */     return new Scopes(this.scope.clone(), this.isolationScope.clone(), this.globalScope, this, creator);
/*      */   }
/*      */   
/*      */   @NotNull
/*      */   public IScopes forkedCurrentScope(@NotNull String creator) {
/*  115 */     return new Scopes(this.scope.clone(), this.isolationScope, this.globalScope, this, creator);
/*      */   }
/*      */   
/*      */   @NotNull
/*      */   public IScopes forkedRootScopes(@NotNull String creator) {
/*  120 */     return Sentry.forkedRootScopes(creator);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEnabled() {
/*  125 */     return getClient().isEnabled();
/*      */   }
/*      */   
/*      */   @NotNull
/*      */   public SentryId captureEvent(@NotNull SentryEvent event, @Nullable Hint hint) {
/*  130 */     return captureEventInternal(event, hint, null);
/*      */   }
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public SentryId captureEvent(@NotNull SentryEvent event, @Nullable Hint hint, @NotNull ScopeCallback callback) {
/*  136 */     return captureEventInternal(event, hint, callback);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   private SentryId captureEventInternal(@NotNull SentryEvent event, @Nullable Hint hint, @Nullable ScopeCallback scopeCallback) {
/*  143 */     SentryId sentryId = SentryId.EMPTY_ID;
/*  144 */     if (!isEnabled()) {
/*  145 */       getOptions()
/*  146 */         .getLogger()
/*  147 */         .log(SentryLevel.WARNING, "Instance is disabled and this 'captureEvent' call is a no-op.", new Object[0]);
/*      */     }
/*  149 */     else if (event == null) {
/*  150 */       getOptions().getLogger().log(SentryLevel.WARNING, "captureEvent called with null parameter.", new Object[0]);
/*      */     } else {
/*      */       try {
/*  153 */         assignTraceContext(event);
/*  154 */         IScope localScope = buildLocalScope(getCombinedScopeView(), scopeCallback);
/*      */         
/*  156 */         sentryId = getClient().captureEvent(event, localScope, hint);
/*  157 */         updateLastEventId(sentryId);
/*  158 */       } catch (Throwable e) {
/*  159 */         getOptions()
/*  160 */           .getLogger()
/*  161 */           .log(SentryLevel.ERROR, "Error while capturing event with id: " + event
/*  162 */             .getEventId(), e);
/*      */       } 
/*      */     } 
/*  165 */     return sentryId;
/*      */   }
/*      */   
/*      */   @Internal
/*      */   @NotNull
/*      */   public ISentryClient getClient() {
/*  171 */     return getCombinedScopeView().getClient();
/*      */   }
/*      */   
/*      */   private void assignTraceContext(@NotNull SentryEvent event) {
/*  175 */     getCombinedScopeView().assignTraceContext(event);
/*      */   }
/*      */   
/*      */   @NotNull
/*      */   private IScope buildLocalScope(@NotNull IScope parentScope, @Nullable ScopeCallback callback) {
/*  180 */     if (callback != null) {
/*      */       try {
/*  182 */         IScope localScope = parentScope.clone();
/*  183 */         callback.run(localScope);
/*  184 */         return localScope;
/*  185 */       } catch (Throwable t) {
/*  186 */         getOptions()
/*  187 */           .getLogger()
/*  188 */           .log(SentryLevel.ERROR, "Error in the 'ScopeCallback' callback.", t);
/*      */       } 
/*      */     }
/*  191 */     return parentScope;
/*      */   }
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public SentryId captureMessage(@NotNull String message, @NotNull SentryLevel level) {
/*  197 */     return captureMessageInternal(message, level, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public SentryId captureMessage(@NotNull String message, @NotNull SentryLevel level, @NotNull ScopeCallback callback) {
/*  205 */     return captureMessageInternal(message, level, callback);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   private SentryId captureMessageInternal(@NotNull String message, @NotNull SentryLevel level, @Nullable ScopeCallback scopeCallback) {
/*  212 */     SentryId sentryId = SentryId.EMPTY_ID;
/*  213 */     if (!isEnabled()) {
/*  214 */       getOptions()
/*  215 */         .getLogger()
/*  216 */         .log(SentryLevel.WARNING, "Instance is disabled and this 'captureMessage' call is a no-op.", new Object[0]);
/*      */     
/*      */     }
/*  219 */     else if (message == null) {
/*  220 */       getOptions()
/*  221 */         .getLogger()
/*  222 */         .log(SentryLevel.WARNING, "captureMessage called with null parameter.", new Object[0]);
/*      */     } else {
/*      */       try {
/*  225 */         IScope localScope = buildLocalScope(getCombinedScopeView(), scopeCallback);
/*      */         
/*  227 */         sentryId = getClient().captureMessage(message, level, localScope);
/*  228 */       } catch (Throwable e) {
/*  229 */         getOptions()
/*  230 */           .getLogger()
/*  231 */           .log(SentryLevel.ERROR, "Error while capturing message: " + message, e);
/*      */       } 
/*      */     } 
/*  234 */     updateLastEventId(sentryId);
/*  235 */     return sentryId;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public SentryId captureFeedback(@NotNull Feedback feedback, @Nullable Hint hint, @Nullable ScopeCallback scopeCallback) {
/*  243 */     SentryId sentryId = SentryId.EMPTY_ID;
/*  244 */     if (!isEnabled()) {
/*  245 */       getOptions()
/*  246 */         .getLogger()
/*  247 */         .log(SentryLevel.WARNING, "Instance is disabled and this 'captureFeedback' call is a no-op.", new Object[0]);
/*      */     
/*      */     }
/*  250 */     else if (feedback.getMessage().isEmpty()) {
/*  251 */       getOptions()
/*  252 */         .getLogger()
/*  253 */         .log(SentryLevel.WARNING, "captureFeedback called with empty message.", new Object[0]);
/*      */     } else {
/*      */       try {
/*  256 */         IScope localScope = buildLocalScope(getCombinedScopeView(), scopeCallback);
/*      */         
/*  258 */         sentryId = getClient().captureFeedback(feedback, hint, localScope);
/*  259 */       } catch (Throwable e) {
/*  260 */         getOptions()
/*  261 */           .getLogger()
/*  262 */           .log(SentryLevel.ERROR, "Error while capturing feedback: " + feedback.getMessage(), e);
/*      */       } 
/*      */     } 
/*  265 */     return sentryId;
/*      */   }
/*      */ 
/*      */   
/*      */   @Internal
/*      */   @NotNull
/*      */   public SentryId captureEnvelope(@NotNull SentryEnvelope envelope, @Nullable Hint hint) {
/*  272 */     Objects.requireNonNull(envelope, "SentryEnvelope is required.");
/*      */     
/*  274 */     SentryId sentryId = SentryId.EMPTY_ID;
/*  275 */     if (!isEnabled()) {
/*  276 */       getOptions()
/*  277 */         .getLogger()
/*  278 */         .log(SentryLevel.WARNING, "Instance is disabled and this 'captureEnvelope' call is a no-op.", new Object[0]);
/*      */     } else {
/*      */ 
/*      */       
/*      */       try {
/*  283 */         SentryId capturedEnvelopeId = getClient().captureEnvelope(envelope, hint);
/*  284 */         if (capturedEnvelopeId != null) {
/*  285 */           sentryId = capturedEnvelopeId;
/*      */         }
/*  287 */       } catch (Throwable e) {
/*  288 */         getOptions().getLogger().log(SentryLevel.ERROR, "Error while capturing envelope.", e);
/*      */       } 
/*      */     } 
/*  291 */     return sentryId;
/*      */   }
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public SentryId captureException(@NotNull Throwable throwable, @Nullable Hint hint) {
/*  297 */     return captureExceptionInternal(throwable, hint, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public SentryId captureException(@NotNull Throwable throwable, @Nullable Hint hint, @NotNull ScopeCallback callback) {
/*  306 */     return captureExceptionInternal(throwable, hint, callback);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   private SentryId captureExceptionInternal(@NotNull Throwable throwable, @Nullable Hint hint, @Nullable ScopeCallback scopeCallback) {
/*  313 */     SentryId sentryId = SentryId.EMPTY_ID;
/*  314 */     if (!isEnabled()) {
/*  315 */       getOptions()
/*  316 */         .getLogger()
/*  317 */         .log(SentryLevel.WARNING, "Instance is disabled and this 'captureException' call is a no-op.", new Object[0]);
/*      */     
/*      */     }
/*  320 */     else if (throwable == null) {
/*  321 */       getOptions()
/*  322 */         .getLogger()
/*  323 */         .log(SentryLevel.WARNING, "captureException called with null parameter.", new Object[0]);
/*      */     } else {
/*      */       try {
/*  326 */         SentryEvent event = new SentryEvent(throwable);
/*  327 */         assignTraceContext(event);
/*      */         
/*  329 */         IScope localScope = buildLocalScope(getCombinedScopeView(), scopeCallback);
/*      */         
/*  331 */         sentryId = getClient().captureEvent(event, localScope, hint);
/*  332 */       } catch (Throwable e) {
/*  333 */         getOptions()
/*  334 */           .getLogger()
/*  335 */           .log(SentryLevel.ERROR, "Error while capturing exception: " + throwable
/*  336 */             .getMessage(), e);
/*      */       } 
/*      */     } 
/*  339 */     updateLastEventId(sentryId);
/*  340 */     return sentryId;
/*      */   }
/*      */ 
/*      */   
/*      */   public void captureUserFeedback(@NotNull UserFeedback userFeedback) {
/*  345 */     if (!isEnabled()) {
/*  346 */       getOptions()
/*  347 */         .getLogger()
/*  348 */         .log(SentryLevel.WARNING, "Instance is disabled and this 'captureUserFeedback' call is a no-op.", new Object[0]);
/*      */     } else {
/*      */ 
/*      */       
/*      */       try {
/*  353 */         getClient().captureUserFeedback(userFeedback);
/*  354 */       } catch (Throwable e) {
/*  355 */         getOptions()
/*  356 */           .getLogger()
/*  357 */           .log(SentryLevel.ERROR, "Error while capturing captureUserFeedback: " + userFeedback
/*      */             
/*  359 */             .toString(), e);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void startSession() {
/*  367 */     if (!isEnabled()) {
/*  368 */       getOptions()
/*  369 */         .getLogger()
/*  370 */         .log(SentryLevel.WARNING, "Instance is disabled and this 'startSession' call is a no-op.", new Object[0]);
/*      */     } else {
/*      */       
/*  373 */       Scope.SessionPair pair = getCombinedScopeView().startSession();
/*  374 */       if (pair != null) {
/*      */ 
/*      */ 
/*      */         
/*  378 */         if (pair.getPrevious() != null) {
/*  379 */           Hint hint1 = HintUtils.createWithTypeCheckHint(new SessionEndHint());
/*      */           
/*  381 */           getClient().captureSession(pair.getPrevious(), hint1);
/*      */         } 
/*      */         
/*  384 */         Hint hint = HintUtils.createWithTypeCheckHint(new SessionStartHint());
/*      */         
/*  386 */         getClient().captureSession(pair.getCurrent(), hint);
/*      */       } else {
/*  388 */         getOptions().getLogger().log(SentryLevel.WARNING, "Session could not be started.", new Object[0]);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void endSession() {
/*  395 */     if (!isEnabled()) {
/*  396 */       getOptions()
/*  397 */         .getLogger()
/*  398 */         .log(SentryLevel.WARNING, "Instance is disabled and this 'endSession' call is a no-op.", new Object[0]);
/*      */     } else {
/*  400 */       Session previousSession = getCombinedScopeView().endSession();
/*  401 */       if (previousSession != null) {
/*  402 */         Hint hint = HintUtils.createWithTypeCheckHint(new SessionEndHint());
/*      */         
/*  404 */         getClient().captureSession(previousSession, hint);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   @Internal
/*      */   @NotNull
/*      */   public IScope getCombinedScopeView() {
/*  412 */     return this.combinedScope;
/*      */   }
/*      */ 
/*      */   
/*      */   public void close() {
/*  417 */     close(false);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void close(boolean isRestarting) {
/*  423 */     if (!isEnabled()) {
/*  424 */       getOptions()
/*  425 */         .getLogger()
/*  426 */         .log(SentryLevel.WARNING, "Instance is disabled and this 'close' call is a no-op.", new Object[0]);
/*      */     } else {
/*      */       try {
/*  429 */         for (Integration integration : getOptions().getIntegrations()) {
/*  430 */           if (integration instanceof Closeable) {
/*      */             try {
/*  432 */               ((Closeable)integration).close();
/*  433 */             } catch (Throwable e) {
/*  434 */               getOptions()
/*  435 */                 .getLogger()
/*  436 */                 .log(SentryLevel.WARNING, "Failed to close the integration {}.", new Object[] { integration, e });
/*      */             } 
/*      */           }
/*      */         } 
/*      */         
/*  441 */         configureScope(scope -> scope.clear());
/*  442 */         configureScope(ScopeType.ISOLATION, scope -> scope.clear());
/*  443 */         getOptions().getBackpressureMonitor().close();
/*  444 */         getOptions().getTransactionProfiler().close();
/*  445 */         getOptions().getContinuousProfiler().close(true);
/*  446 */         getOptions().getCompositePerformanceCollector().close();
/*  447 */         getOptions().getConnectionStatusProvider().close();
/*  448 */         ISentryExecutorService executorService = getOptions().getExecutorService();
/*  449 */         if (isRestarting) {
/*      */           try {
/*  451 */             executorService.submit(() -> executorService.close(getOptions().getShutdownTimeoutMillis()));
/*      */           }
/*  453 */           catch (RejectedExecutionException e) {
/*  454 */             getOptions()
/*  455 */               .getLogger()
/*  456 */               .log(SentryLevel.WARNING, "Failed to submit executor service shutdown task during restart. Shutting down synchronously.", e);
/*      */ 
/*      */ 
/*      */             
/*  460 */             executorService.close(getOptions().getShutdownTimeoutMillis());
/*      */           } 
/*      */         } else {
/*  463 */           executorService.close(getOptions().getShutdownTimeoutMillis());
/*      */         } 
/*      */ 
/*      */         
/*  467 */         configureScope(ScopeType.CURRENT, scope -> scope.getClient().close(isRestarting));
/*  468 */         configureScope(ScopeType.ISOLATION, scope -> scope.getClient().close(isRestarting));
/*  469 */         configureScope(ScopeType.GLOBAL, scope -> scope.getClient().close(isRestarting));
/*  470 */       } catch (Throwable e) {
/*  471 */         getOptions().getLogger().log(SentryLevel.ERROR, "Error while closing the Scopes.", e);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void addBreadcrumb(@NotNull Breadcrumb breadcrumb, @Nullable Hint hint) {
/*  478 */     if (!isEnabled()) {
/*  479 */       getOptions()
/*  480 */         .getLogger()
/*  481 */         .log(SentryLevel.WARNING, "Instance is disabled and this 'addBreadcrumb' call is a no-op.", new Object[0]);
/*      */     
/*      */     }
/*  484 */     else if (breadcrumb == null) {
/*  485 */       getOptions()
/*  486 */         .getLogger()
/*  487 */         .log(SentryLevel.WARNING, "addBreadcrumb called with null parameter.", new Object[0]);
/*      */     } else {
/*  489 */       getCombinedScopeView().addBreadcrumb(breadcrumb, hint);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void addBreadcrumb(@NotNull Breadcrumb breadcrumb) {
/*  495 */     addBreadcrumb(breadcrumb, new Hint());
/*      */   }
/*      */ 
/*      */   
/*      */   public void setLevel(@Nullable SentryLevel level) {
/*  500 */     if (!isEnabled()) {
/*  501 */       getOptions()
/*  502 */         .getLogger()
/*  503 */         .log(SentryLevel.WARNING, "Instance is disabled and this 'setLevel' call is a no-op.", new Object[0]);
/*      */     } else {
/*  505 */       getCombinedScopeView().setLevel(level);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void setTransaction(@Nullable String transaction) {
/*  511 */     if (!isEnabled()) {
/*  512 */       getOptions()
/*  513 */         .getLogger()
/*  514 */         .log(SentryLevel.WARNING, "Instance is disabled and this 'setTransaction' call is a no-op.", new Object[0]);
/*      */     
/*      */     }
/*  517 */     else if (transaction != null) {
/*  518 */       getCombinedScopeView().setTransaction(transaction);
/*      */     } else {
/*  520 */       getOptions().getLogger().log(SentryLevel.WARNING, "Transaction cannot be null", new Object[0]);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void setUser(@Nullable User user) {
/*  526 */     if (!isEnabled()) {
/*  527 */       getOptions()
/*  528 */         .getLogger()
/*  529 */         .log(SentryLevel.WARNING, "Instance is disabled and this 'setUser' call is a no-op.", new Object[0]);
/*      */     } else {
/*  531 */       getCombinedScopeView().setUser(user);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void setFingerprint(@NotNull List<String> fingerprint) {
/*  537 */     if (!isEnabled()) {
/*  538 */       getOptions()
/*  539 */         .getLogger()
/*  540 */         .log(SentryLevel.WARNING, "Instance is disabled and this 'setFingerprint' call is a no-op.", new Object[0]);
/*      */     
/*      */     }
/*  543 */     else if (fingerprint == null) {
/*  544 */       getOptions()
/*  545 */         .getLogger()
/*  546 */         .log(SentryLevel.WARNING, "setFingerprint called with null parameter.", new Object[0]);
/*      */     } else {
/*  548 */       getCombinedScopeView().setFingerprint(fingerprint);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void clearBreadcrumbs() {
/*  554 */     if (!isEnabled()) {
/*  555 */       getOptions()
/*  556 */         .getLogger()
/*  557 */         .log(SentryLevel.WARNING, "Instance is disabled and this 'clearBreadcrumbs' call is a no-op.", new Object[0]);
/*      */     }
/*      */     else {
/*      */       
/*  561 */       getCombinedScopeView().clearBreadcrumbs();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void setTag(@Nullable String key, @Nullable String value) {
/*  567 */     if (!isEnabled()) {
/*  568 */       getOptions()
/*  569 */         .getLogger()
/*  570 */         .log(SentryLevel.WARNING, "Instance is disabled and this 'setTag' call is a no-op.", new Object[0]);
/*  571 */     } else if (key == null || value == null) {
/*  572 */       getOptions().getLogger().log(SentryLevel.WARNING, "setTag called with null parameter.", new Object[0]);
/*      */     } else {
/*  574 */       getCombinedScopeView().setTag(key, value);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void removeTag(@Nullable String key) {
/*  580 */     if (!isEnabled()) {
/*  581 */       getOptions()
/*  582 */         .getLogger()
/*  583 */         .log(SentryLevel.WARNING, "Instance is disabled and this 'removeTag' call is a no-op.", new Object[0]);
/*  584 */     } else if (key == null) {
/*  585 */       getOptions().getLogger().log(SentryLevel.WARNING, "removeTag called with null parameter.", new Object[0]);
/*      */     } else {
/*  587 */       getCombinedScopeView().removeTag(key);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void setExtra(@Nullable String key, @Nullable String value) {
/*  593 */     if (!isEnabled()) {
/*  594 */       getOptions()
/*  595 */         .getLogger()
/*  596 */         .log(SentryLevel.WARNING, "Instance is disabled and this 'setExtra' call is a no-op.", new Object[0]);
/*  597 */     } else if (key == null || value == null) {
/*  598 */       getOptions().getLogger().log(SentryLevel.WARNING, "setExtra called with null parameter.", new Object[0]);
/*      */     } else {
/*  600 */       getCombinedScopeView().setExtra(key, value);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void removeExtra(@Nullable String key) {
/*  606 */     if (!isEnabled()) {
/*  607 */       getOptions()
/*  608 */         .getLogger()
/*  609 */         .log(SentryLevel.WARNING, "Instance is disabled and this 'removeExtra' call is a no-op.", new Object[0]);
/*  610 */     } else if (key == null) {
/*  611 */       getOptions().getLogger().log(SentryLevel.WARNING, "removeExtra called with null parameter.", new Object[0]);
/*      */     } else {
/*  613 */       getCombinedScopeView().removeExtra(key);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void updateLastEventId(@NotNull SentryId lastEventId) {
/*  618 */     getCombinedScopeView().setLastEventId(lastEventId);
/*      */   }
/*      */   
/*      */   @NotNull
/*      */   public SentryId getLastEventId() {
/*  623 */     return getCombinedScopeView().getLastEventId();
/*      */   }
/*      */ 
/*      */   
/*      */   public ISentryLifecycleToken pushScope() {
/*  628 */     if (!isEnabled()) {
/*  629 */       getOptions()
/*  630 */         .getLogger()
/*  631 */         .log(SentryLevel.WARNING, "Instance is disabled and this 'pushScope' call is a no-op.", new Object[0]);
/*  632 */       return NoOpScopesLifecycleToken.getInstance();
/*      */     } 
/*  634 */     IScopes scopes = forkedCurrentScope("pushScope");
/*  635 */     return scopes.makeCurrent();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ISentryLifecycleToken pushIsolationScope() {
/*  641 */     if (!isEnabled()) {
/*  642 */       getOptions()
/*  643 */         .getLogger()
/*  644 */         .log(SentryLevel.WARNING, "Instance is disabled and this 'pushIsolationScope' call is a no-op.", new Object[0]);
/*      */ 
/*      */       
/*  647 */       return NoOpScopesLifecycleToken.getInstance();
/*      */     } 
/*  649 */     IScopes scopes = forkedScopes("pushIsolationScope");
/*  650 */     return scopes.makeCurrent();
/*      */   }
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public ISentryLifecycleToken makeCurrent() {
/*  656 */     return Sentry.setCurrentScopes(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public void popScope() {
/*  666 */     if (!isEnabled()) {
/*  667 */       getOptions()
/*  668 */         .getLogger()
/*  669 */         .log(SentryLevel.WARNING, "Instance is disabled and this 'popScope' call is a no-op.", new Object[0]);
/*      */     } else {
/*  671 */       Scopes parent = this.parentScopes;
/*  672 */       if (parent != null) {
/*  673 */         parent.makeCurrent();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void withScope(@NotNull ScopeCallback callback) {
/*  680 */     if (!isEnabled()) {
/*      */       try {
/*  682 */         callback.run(NoOpScope.getInstance());
/*  683 */       } catch (Throwable e) {
/*  684 */         getOptions().getLogger().log(SentryLevel.ERROR, "Error in the 'withScope' callback.", e);
/*      */       } 
/*      */     } else {
/*      */       
/*  688 */       IScopes forkedScopes = forkedCurrentScope("withScope"); 
/*  689 */       try { ISentryLifecycleToken ignored = forkedScopes.makeCurrent(); 
/*  690 */         try { callback.run(forkedScopes.getScope());
/*  691 */           if (ignored != null) ignored.close();  } catch (Throwable throwable) { if (ignored != null) try { ignored.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (Throwable e)
/*  692 */       { getOptions().getLogger().log(SentryLevel.ERROR, "Error in the 'withScope' callback.", e); }
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void withIsolationScope(@NotNull ScopeCallback callback) {
/*  699 */     if (!isEnabled()) {
/*      */       try {
/*  701 */         callback.run(NoOpScope.getInstance());
/*  702 */       } catch (Throwable e) {
/*  703 */         getOptions()
/*  704 */           .getLogger()
/*  705 */           .log(SentryLevel.ERROR, "Error in the 'withIsolationScope' callback.", e);
/*      */       } 
/*      */     } else {
/*      */       
/*  709 */       IScopes forkedScopes = forkedScopes("withIsolationScope"); 
/*  710 */       try { ISentryLifecycleToken ignored = forkedScopes.makeCurrent(); 
/*  711 */         try { callback.run(forkedScopes.getIsolationScope());
/*  712 */           if (ignored != null) ignored.close();  } catch (Throwable throwable) { if (ignored != null) try { ignored.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (Throwable e)
/*  713 */       { getOptions()
/*  714 */           .getLogger()
/*  715 */           .log(SentryLevel.ERROR, "Error in the 'withIsolationScope' callback.", e); }
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void configureScope(@Nullable ScopeType scopeType, @NotNull ScopeCallback callback) {
/*  723 */     if (!isEnabled()) {
/*  724 */       getOptions()
/*  725 */         .getLogger()
/*  726 */         .log(SentryLevel.WARNING, "Instance is disabled and this 'configureScope' call is a no-op.", new Object[0]);
/*      */     } else {
/*      */ 
/*      */       
/*      */       try {
/*  731 */         callback.run(this.combinedScope.getSpecificScope(scopeType));
/*  732 */       } catch (Throwable e) {
/*  733 */         getOptions()
/*  734 */           .getLogger()
/*  735 */           .log(SentryLevel.ERROR, "Error in the 'configureScope' callback.", e);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void bindClient(@NotNull ISentryClient client) {
/*  742 */     if (client != null) {
/*  743 */       getOptions().getLogger().log(SentryLevel.DEBUG, "New client bound to scope.", new Object[0]);
/*  744 */       getCombinedScopeView().bindClient(client);
/*      */     } else {
/*  746 */       getOptions().getLogger().log(SentryLevel.DEBUG, "NoOp client bound to scope.", new Object[0]);
/*  747 */       getCombinedScopeView().bindClient(NoOpSentryClient.getInstance());
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isHealthy() {
/*  753 */     return getClient().isHealthy();
/*      */   }
/*      */ 
/*      */   
/*      */   public void flush(long timeoutMillis) {
/*  758 */     if (!isEnabled()) {
/*  759 */       getOptions()
/*  760 */         .getLogger()
/*  761 */         .log(SentryLevel.WARNING, "Instance is disabled and this 'flush' call is a no-op.", new Object[0]);
/*      */     } else {
/*      */       try {
/*  764 */         getClient().flush(timeoutMillis);
/*  765 */       } catch (Throwable e) {
/*  766 */         getOptions().getLogger().log(SentryLevel.ERROR, "Error in the 'client.flush'.", e);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @NotNull
/*      */   public IHub clone() {
/*  779 */     if (!isEnabled()) {
/*  780 */       getOptions().getLogger().log(SentryLevel.WARNING, "Disabled Scopes cloned.", new Object[0]);
/*      */     }
/*  782 */     return new HubScopesWrapper(forkedScopes("scopes clone"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Internal
/*      */   @NotNull
/*      */   public SentryId captureTransaction(@NotNull SentryTransaction transaction, @Nullable TraceContext traceContext, @Nullable Hint hint, @Nullable ProfilingTraceData profilingTraceData) {
/*  792 */     Objects.requireNonNull(transaction, "transaction is required");
/*      */     
/*  794 */     SentryId sentryId = SentryId.EMPTY_ID;
/*  795 */     if (!isEnabled()) {
/*  796 */       getOptions()
/*  797 */         .getLogger()
/*  798 */         .log(SentryLevel.WARNING, "Instance is disabled and this 'captureTransaction' call is a no-op.", new Object[0]);
/*      */ 
/*      */     
/*      */     }
/*  802 */     else if (!transaction.isFinished()) {
/*  803 */       getOptions()
/*  804 */         .getLogger()
/*  805 */         .log(SentryLevel.WARNING, "Transaction: %s is not finished and this 'captureTransaction' call is a no-op.", new Object[] {
/*      */ 
/*      */             
/*  808 */             transaction.getEventId()
/*      */           });
/*  810 */     } else if (!Boolean.TRUE.equals(Boolean.valueOf(transaction.isSampled()))) {
/*  811 */       getOptions()
/*  812 */         .getLogger()
/*  813 */         .log(SentryLevel.DEBUG, "Transaction %s was dropped due to sampling decision.", new Object[] {
/*      */ 
/*      */             
/*  816 */             transaction.getEventId() });
/*  817 */       if (getOptions().getBackpressureMonitor().getDownsampleFactor() > 0) {
/*  818 */         getOptions()
/*  819 */           .getClientReportRecorder()
/*  820 */           .recordLostEvent(DiscardReason.BACKPRESSURE, DataCategory.Transaction);
/*  821 */         getOptions()
/*  822 */           .getClientReportRecorder()
/*  823 */           .recordLostEvent(DiscardReason.BACKPRESSURE, DataCategory.Span, (transaction
/*      */ 
/*      */             
/*  826 */             .getSpans().size() + 1));
/*      */       } else {
/*  828 */         getOptions()
/*  829 */           .getClientReportRecorder()
/*  830 */           .recordLostEvent(DiscardReason.SAMPLE_RATE, DataCategory.Transaction);
/*  831 */         getOptions()
/*  832 */           .getClientReportRecorder()
/*  833 */           .recordLostEvent(DiscardReason.SAMPLE_RATE, DataCategory.Span, (transaction
/*      */ 
/*      */             
/*  836 */             .getSpans().size() + 1));
/*      */       } 
/*      */     } else {
/*      */ 
/*      */       
/*      */       try {
/*  842 */         sentryId = getClient().captureTransaction(transaction, traceContext, 
/*      */ 
/*      */             
/*  845 */             getCombinedScopeView(), hint, profilingTraceData);
/*      */       
/*      */       }
/*  848 */       catch (Throwable e) {
/*  849 */         getOptions()
/*  850 */           .getLogger()
/*  851 */           .log(SentryLevel.ERROR, "Error while capturing transaction with id: " + transaction
/*      */             
/*  853 */             .getEventId(), e);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  859 */     return sentryId;
/*      */   }
/*      */ 
/*      */   
/*      */   @Internal
/*      */   @NotNull
/*      */   public SentryId captureProfileChunk(@NotNull ProfileChunk profilingContinuousData) {
/*  866 */     Objects.requireNonNull(profilingContinuousData, "profilingContinuousData is required");
/*      */     
/*  868 */     SentryId sentryId = SentryId.EMPTY_ID;
/*  869 */     if (!isEnabled()) {
/*  870 */       getOptions()
/*  871 */         .getLogger()
/*  872 */         .log(SentryLevel.WARNING, "Instance is disabled and this 'captureTransaction' call is a no-op.", new Object[0]);
/*      */     } else {
/*      */ 
/*      */       
/*      */       try {
/*  877 */         sentryId = getClient().captureProfileChunk(profilingContinuousData, getScope());
/*  878 */       } catch (Throwable e) {
/*  879 */         getOptions()
/*  880 */           .getLogger()
/*  881 */           .log(SentryLevel.ERROR, "Error while capturing profile chunk with id: " + profilingContinuousData
/*      */ 
/*      */             
/*  884 */             .getChunkId(), e);
/*      */       } 
/*      */     } 
/*      */     
/*  888 */     return sentryId;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public ITransaction startTransaction(@NotNull TransactionContext transactionContext, @NotNull TransactionOptions transactionOptions) {
/*  895 */     return createTransaction(transactionContext, transactionOptions);
/*      */   }
/*      */   
/*      */   @NotNull
/*      */   private ITransaction createTransaction(@NotNull TransactionContext transactionContext, @NotNull TransactionOptions transactionOptions) {
/*      */     ITransaction transaction;
/*  901 */     Objects.requireNonNull(transactionContext, "transactionContext is required");
/*  902 */     transactionContext.setOrigin(transactionOptions.getOrigin());
/*      */ 
/*      */     
/*  905 */     if (!isEnabled()) {
/*  906 */       getOptions()
/*  907 */         .getLogger()
/*  908 */         .log(SentryLevel.WARNING, "Instance is disabled and this 'startTransaction' returns a no-op.", new Object[0]);
/*      */ 
/*      */       
/*  911 */       transaction = NoOpTransaction.getInstance();
/*  912 */     } else if (SpanUtils.isIgnored(
/*  913 */         getOptions().getIgnoredSpanOrigins(), transactionContext.getOrigin())) {
/*  914 */       getOptions()
/*  915 */         .getLogger()
/*  916 */         .log(SentryLevel.DEBUG, "Returning no-op for span origin %s as the SDK has been configured to ignore it", new Object[] {
/*      */ 
/*      */             
/*  919 */             transactionContext.getOrigin() });
/*  920 */       transaction = NoOpTransaction.getInstance();
/*      */     }
/*  922 */     else if (!getOptions().getInstrumenter().equals(transactionContext.getInstrumenter())) {
/*  923 */       getOptions()
/*  924 */         .getLogger()
/*  925 */         .log(SentryLevel.DEBUG, "Returning no-op for instrumenter %s as the SDK has been configured to use instrumenter %s", new Object[] {
/*      */ 
/*      */             
/*  928 */             transactionContext.getInstrumenter(), 
/*  929 */             getOptions().getInstrumenter() });
/*  930 */       transaction = NoOpTransaction.getInstance();
/*  931 */     } else if (!getOptions().isTracingEnabled()) {
/*  932 */       getOptions()
/*  933 */         .getLogger()
/*  934 */         .log(SentryLevel.INFO, "Tracing is disabled and this 'startTransaction' returns a no-op.", new Object[0]);
/*      */       
/*  936 */       transaction = NoOpTransaction.getInstance();
/*      */     } else {
/*  938 */       Double sampleRand = getSampleRand(transactionContext);
/*      */ 
/*      */       
/*  941 */       SamplingContext samplingContext = new SamplingContext(transactionContext, transactionOptions.getCustomSamplingContext(), sampleRand, null);
/*  942 */       TracesSampler tracesSampler = getOptions().getInternalTracesSampler();
/*  943 */       TracesSamplingDecision samplingDecision = tracesSampler.sample(samplingContext);
/*  944 */       transactionContext.setSamplingDecision(samplingDecision);
/*      */       
/*  946 */       ISpanFactory maybeSpanFactory = transactionOptions.getSpanFactory();
/*      */       
/*  948 */       ISpanFactory spanFactory = (maybeSpanFactory == null) ? getOptions().getSpanFactory() : maybeSpanFactory;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  955 */       if (samplingDecision.getSampled().booleanValue() && 
/*  956 */         getOptions().isContinuousProfilingEnabled() && 
/*  957 */         getOptions().getProfileLifecycle() == ProfileLifecycle.TRACE && transactionContext
/*  958 */         .getProfilerId().equals(SentryId.EMPTY_ID)) {
/*  959 */         getOptions()
/*  960 */           .getContinuousProfiler()
/*  961 */           .startProfiler(ProfileLifecycle.TRACE, getOptions().getInternalTracesSampler());
/*      */       }
/*      */ 
/*      */       
/*  965 */       transaction = spanFactory.createTransaction(transactionContext, this, transactionOptions, this.compositePerformanceCollector);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  973 */       if (samplingDecision.getSampled().booleanValue())
/*      */       {
/*  975 */         if (samplingDecision.getProfileSampled().booleanValue()) {
/*  976 */           ITransactionProfiler transactionProfiler = getOptions().getTransactionProfiler();
/*      */           
/*  978 */           if (!transactionProfiler.isRunning()) {
/*  979 */             transactionProfiler.start();
/*  980 */             transactionProfiler.bindTransaction(transaction);
/*  981 */           } else if (transactionOptions.isAppStartTransaction()) {
/*      */             
/*  983 */             transactionProfiler.bindTransaction(transaction);
/*      */           } 
/*      */         } 
/*      */       }
/*      */     } 
/*  988 */     if (transactionOptions.isBindToScope()) {
/*  989 */       transaction.makeCurrent();
/*      */     }
/*  991 */     return transaction;
/*      */   }
/*      */   @NotNull
/*      */   private Double getSampleRand(@NotNull TransactionContext transactionContext) {
/*  995 */     Baggage baggage = transactionContext.getBaggage();
/*  996 */     if (baggage != null) {
/*  997 */       Double sampleRandFromBaggageMaybe = baggage.getSampleRand();
/*  998 */       if (sampleRandFromBaggageMaybe != null) {
/*  999 */         return sampleRandFromBaggageMaybe;
/*      */       }
/*      */     } 
/*      */     
/* 1003 */     return getCombinedScopeView().getPropagationContext().getSampleRand();
/*      */   }
/*      */ 
/*      */   
/*      */   public void startProfiler() {
/* 1008 */     if (getOptions().isContinuousProfilingEnabled()) {
/* 1009 */       if (getOptions().getProfileLifecycle() != ProfileLifecycle.MANUAL) {
/* 1010 */         getOptions()
/* 1011 */           .getLogger()
/* 1012 */           .log(SentryLevel.WARNING, "Profiling lifecycle is %s. Profiling cannot be started manually.", new Object[] {
/*      */ 
/*      */               
/* 1015 */               getOptions().getProfileLifecycle().name() });
/*      */         return;
/*      */       } 
/* 1018 */       getOptions()
/* 1019 */         .getContinuousProfiler()
/* 1020 */         .startProfiler(ProfileLifecycle.MANUAL, getOptions().getInternalTracesSampler());
/* 1021 */     } else if (getOptions().isProfilingEnabled()) {
/* 1022 */       getOptions()
/* 1023 */         .getLogger()
/* 1024 */         .log(SentryLevel.WARNING, "Continuous Profiling is not enabled. Set profilesSampleRate and profilesSampler to null to enable it.", new Object[0]);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void stopProfiler() {
/* 1032 */     if (getOptions().isContinuousProfilingEnabled()) {
/* 1033 */       if (getOptions().getProfileLifecycle() != ProfileLifecycle.MANUAL) {
/* 1034 */         getOptions()
/* 1035 */           .getLogger()
/* 1036 */           .log(SentryLevel.WARNING, "Profiling lifecycle is %s. Profiling cannot be stopped manually.", new Object[] {
/*      */ 
/*      */               
/* 1039 */               getOptions().getProfileLifecycle().name() });
/*      */         return;
/*      */       } 
/* 1042 */       getOptions().getLogger().log(SentryLevel.DEBUG, "Stopped continuous Profiling.", new Object[0]);
/* 1043 */       getOptions().getContinuousProfiler().stopProfiler(ProfileLifecycle.MANUAL);
/*      */     } else {
/* 1045 */       getOptions()
/* 1046 */         .getLogger()
/* 1047 */         .log(SentryLevel.WARNING, "Continuous Profiling is not enabled. Set profilesSampleRate and profilesSampler to null to enable it.", new Object[0]);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Internal
/*      */   public void setSpanContext(@NotNull Throwable throwable, @NotNull ISpan span, @NotNull String transactionName) {
/* 1059 */     getCombinedScopeView().setSpanContext(throwable, span, transactionName);
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public ISpan getSpan() {
/* 1064 */     if (!isEnabled()) {
/* 1065 */       getOptions()
/* 1066 */         .getLogger()
/* 1067 */         .log(SentryLevel.WARNING, "Instance is disabled and this 'getSpan' call is a no-op.", new Object[0]);
/*      */     } else {
/* 1069 */       return getCombinedScopeView().getSpan();
/*      */     } 
/* 1071 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setActiveSpan(@Nullable ISpan span) {
/* 1076 */     getCombinedScopeView().setActiveSpan(span);
/*      */   }
/*      */   
/*      */   @Internal
/*      */   @Nullable
/*      */   public ITransaction getTransaction() {
/* 1082 */     ITransaction span = null;
/* 1083 */     if (!isEnabled()) {
/* 1084 */       getOptions()
/* 1085 */         .getLogger()
/* 1086 */         .log(SentryLevel.WARNING, "Instance is disabled and this 'getTransaction' call is a no-op.", new Object[0]);
/*      */     }
/*      */     else {
/*      */       
/* 1090 */       span = getCombinedScopeView().getTransaction();
/*      */     } 
/* 1092 */     return span;
/*      */   }
/*      */   
/*      */   @NotNull
/*      */   public SentryOptions getOptions() {
/* 1097 */     return this.combinedScope.getOptions();
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public Boolean isCrashedLastRun() {
/* 1102 */     return SentryCrashLastRunState.getInstance()
/* 1103 */       .isCrashedLastRun(
/* 1104 */         getOptions().getCacheDirPath(), !getOptions().isEnableAutoSessionTracking());
/*      */   }
/*      */ 
/*      */   
/*      */   public void reportFullyDisplayed() {
/* 1109 */     if (getOptions().isEnableTimeToFullDisplayTracing()) {
/* 1110 */       getOptions().getFullyDisplayedReporter().reportFullyDrawn();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public TransactionContext continueTrace(@Nullable String sentryTrace, @Nullable List<String> baggageHeaders) {
/* 1119 */     PropagationContext propagationContext = PropagationContext.fromHeaders(getOptions().getLogger(), sentryTrace, baggageHeaders);
/* 1120 */     configureScope(scope -> scope.withPropagationContext(()));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1127 */     if (getOptions().isTracingEnabled()) {
/* 1128 */       return TransactionContext.fromPropagationContext(propagationContext);
/*      */     }
/* 1130 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public SentryTraceHeader getTraceparent() {
/* 1136 */     if (!isEnabled()) {
/* 1137 */       getOptions()
/* 1138 */         .getLogger()
/* 1139 */         .log(SentryLevel.WARNING, "Instance is disabled and this 'getTraceparent' call is a no-op.", new Object[0]);
/*      */     
/*      */     }
/*      */     else {
/*      */       
/* 1144 */       TracingUtils.TracingHeaders headers = TracingUtils.trace(this, null, getSpan());
/* 1145 */       if (headers != null) {
/* 1146 */         return headers.getSentryTraceHeader();
/*      */       }
/*      */     } 
/*      */     
/* 1150 */     return null;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public BaggageHeader getBaggage() {
/* 1155 */     if (!isEnabled()) {
/* 1156 */       getOptions()
/* 1157 */         .getLogger()
/* 1158 */         .log(SentryLevel.WARNING, "Instance is disabled and this 'getBaggage' call is a no-op.", new Object[0]);
/*      */     } else {
/*      */       
/* 1161 */       TracingUtils.TracingHeaders headers = TracingUtils.trace(this, null, getSpan());
/* 1162 */       if (headers != null) {
/* 1163 */         return headers.getBaggageHeader();
/*      */       }
/*      */     } 
/*      */     
/* 1167 */     return null;
/*      */   }
/*      */   
/*      */   @NotNull
/*      */   public SentryId captureCheckIn(@NotNull CheckIn checkIn) {
/* 1172 */     SentryId sentryId = SentryId.EMPTY_ID;
/* 1173 */     if (!isEnabled()) {
/* 1174 */       getOptions()
/* 1175 */         .getLogger()
/* 1176 */         .log(SentryLevel.WARNING, "Instance is disabled and this 'captureCheckIn' call is a no-op.", new Object[0]);
/*      */     } else {
/*      */ 
/*      */       
/*      */       try {
/* 1181 */         sentryId = getClient().captureCheckIn(checkIn, getCombinedScopeView(), null);
/* 1182 */       } catch (Throwable e) {
/* 1183 */         getOptions()
/* 1184 */           .getLogger()
/* 1185 */           .log(SentryLevel.ERROR, "Error while capturing check-in for slug", e);
/*      */       } 
/*      */     } 
/* 1188 */     updateLastEventId(sentryId);
/* 1189 */     return sentryId;
/*      */   }
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public SentryId captureReplay(@NotNull SentryReplayEvent replay, @Nullable Hint hint) {
/* 1195 */     SentryId sentryId = SentryId.EMPTY_ID;
/* 1196 */     if (!isEnabled()) {
/* 1197 */       getOptions()
/* 1198 */         .getLogger()
/* 1199 */         .log(SentryLevel.WARNING, "Instance is disabled and this 'captureReplay' call is a no-op.", new Object[0]);
/*      */     } else {
/*      */ 
/*      */       
/*      */       try {
/* 1204 */         sentryId = getClient().captureReplayEvent(replay, getCombinedScopeView(), hint);
/* 1205 */       } catch (Throwable e) {
/* 1206 */         getOptions().getLogger().log(SentryLevel.ERROR, "Error while capturing replay", e);
/*      */       } 
/*      */     } 
/* 1209 */     return sentryId;
/*      */   }
/*      */   
/*      */   @Internal
/*      */   @Nullable
/*      */   public RateLimiter getRateLimiter() {
/* 1215 */     return getClient().getRateLimiter();
/*      */   }
/*      */   
/*      */   @NotNull
/*      */   public ILoggerApi logger() {
/* 1220 */     return this.logger;
/*      */   }
/*      */ 
/*      */   
/*      */   public void addFeatureFlag(@Nullable String flag, @Nullable Boolean result) {
/* 1225 */     this.combinedScope.addFeatureFlag(flag, result);
/*      */   }
/*      */   
/*      */   private static void validateOptions(@NotNull SentryOptions options) {
/* 1229 */     Objects.requireNonNull(options, "SentryOptions is required.");
/* 1230 */     if (options.getDsn() == null || options.getDsn().isEmpty())
/* 1231 */       throw new IllegalArgumentException("Scopes requires a DSN to be instantiated. Considering using the NoOpScopes if no DSN is available."); 
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\Scopes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */