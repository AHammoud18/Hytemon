/*     */ package io.sentry;
/*     */ 
/*     */ import io.sentry.featureflags.FeatureFlagBuffer;
/*     */ import io.sentry.featureflags.IFeatureFlagBuffer;
/*     */ import io.sentry.internal.eventprocessor.EventProcessorAndOrder;
/*     */ import io.sentry.protocol.Contexts;
/*     */ import io.sentry.protocol.FeatureFlags;
/*     */ import io.sentry.protocol.Request;
/*     */ import io.sentry.protocol.SentryId;
/*     */ import io.sentry.protocol.User;
/*     */ import io.sentry.util.EventProcessorUtils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Queue;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class CombinedScopeView
/*     */   implements IScope
/*     */ {
/*     */   private final IScope globalScope;
/*     */   private final IScope isolationScope;
/*     */   private final IScope scope;
/*     */   
/*     */   public CombinedScopeView(@NotNull IScope globalScope, @NotNull IScope isolationScope, @NotNull IScope scope) {
/*  36 */     this.globalScope = globalScope;
/*  37 */     this.isolationScope = isolationScope;
/*  38 */     this.scope = scope;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public SentryLevel getLevel() {
/*  43 */     SentryLevel current = this.scope.getLevel();
/*  44 */     if (current != null) {
/*  45 */       return current;
/*     */     }
/*  47 */     SentryLevel isolation = this.isolationScope.getLevel();
/*  48 */     if (isolation != null) {
/*  49 */       return isolation;
/*     */     }
/*  51 */     return this.globalScope.getLevel();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLevel(@Nullable SentryLevel level) {
/*  56 */     getDefaultWriteScope().setLevel(level);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String getTransactionName() {
/*  61 */     String current = this.scope.getTransactionName();
/*  62 */     if (current != null) {
/*  63 */       return current;
/*     */     }
/*  65 */     String isolation = this.isolationScope.getTransactionName();
/*  66 */     if (isolation != null) {
/*  67 */       return isolation;
/*     */     }
/*  69 */     return this.globalScope.getTransactionName();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTransaction(@NotNull String transaction) {
/*  74 */     getDefaultWriteScope().setTransaction(transaction);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public ISpan getSpan() {
/*  79 */     ISpan current = this.scope.getSpan();
/*  80 */     if (current != null) {
/*  81 */       return current;
/*     */     }
/*  83 */     ISpan isolation = this.isolationScope.getSpan();
/*  84 */     if (isolation != null) {
/*  85 */       return isolation;
/*     */     }
/*  87 */     return this.globalScope.getSpan();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setActiveSpan(@Nullable ISpan span) {
/*  92 */     this.scope.setActiveSpan(span);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTransaction(@Nullable ITransaction transaction) {
/*  97 */     getDefaultWriteScope().setTransaction(transaction);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public User getUser() {
/* 102 */     User current = this.scope.getUser();
/* 103 */     if (current != null) {
/* 104 */       return current;
/*     */     }
/* 106 */     User isolation = this.isolationScope.getUser();
/* 107 */     if (isolation != null) {
/* 108 */       return isolation;
/*     */     }
/* 110 */     return this.globalScope.getUser();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUser(@Nullable User user) {
/* 115 */     getDefaultWriteScope().setUser(user);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String getScreen() {
/* 120 */     String current = this.scope.getScreen();
/* 121 */     if (current != null) {
/* 122 */       return current;
/*     */     }
/* 124 */     String isolation = this.isolationScope.getScreen();
/* 125 */     if (isolation != null) {
/* 126 */       return isolation;
/*     */     }
/* 128 */     return this.globalScope.getScreen();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setScreen(@Nullable String screen) {
/* 133 */     getDefaultWriteScope().setScreen(screen);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Request getRequest() {
/* 138 */     Request current = this.scope.getRequest();
/* 139 */     if (current != null) {
/* 140 */       return current;
/*     */     }
/* 142 */     Request isolation = this.isolationScope.getRequest();
/* 143 */     if (isolation != null) {
/* 144 */       return isolation;
/*     */     }
/* 146 */     return this.globalScope.getRequest();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRequest(@Nullable Request request) {
/* 151 */     getDefaultWriteScope().setRequest(request);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public List<String> getFingerprint() {
/* 156 */     List<String> current = this.scope.getFingerprint();
/* 157 */     if (!current.isEmpty()) {
/* 158 */       return current;
/*     */     }
/* 160 */     List<String> isolation = this.isolationScope.getFingerprint();
/* 161 */     if (!isolation.isEmpty()) {
/* 162 */       return isolation;
/*     */     }
/* 164 */     return this.globalScope.getFingerprint();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFingerprint(@NotNull List<String> fingerprint) {
/* 169 */     getDefaultWriteScope().setFingerprint(fingerprint);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Queue<Breadcrumb> getBreadcrumbs() {
/* 174 */     List<Breadcrumb> allBreadcrumbs = new ArrayList<>();
/* 175 */     allBreadcrumbs.addAll(this.globalScope.getBreadcrumbs());
/* 176 */     allBreadcrumbs.addAll(this.isolationScope.getBreadcrumbs());
/* 177 */     allBreadcrumbs.addAll(this.scope.getBreadcrumbs());
/* 178 */     Collections.sort(allBreadcrumbs);
/*     */ 
/*     */     
/* 181 */     Queue<Breadcrumb> breadcrumbs = Scope.createBreadcrumbsList(this.scope.getOptions().getMaxBreadcrumbs());
/* 182 */     breadcrumbs.addAll(allBreadcrumbs);
/*     */     
/* 184 */     return breadcrumbs;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addBreadcrumb(@NotNull Breadcrumb breadcrumb, @Nullable Hint hint) {
/* 189 */     getDefaultWriteScope().addBreadcrumb(breadcrumb, hint);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addBreadcrumb(@NotNull Breadcrumb breadcrumb) {
/* 194 */     getDefaultWriteScope().addBreadcrumb(breadcrumb);
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearBreadcrumbs() {
/* 199 */     getDefaultWriteScope().clearBreadcrumbs();
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearTransaction() {
/* 204 */     getDefaultWriteScope().clearTransaction();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public ITransaction getTransaction() {
/* 209 */     ITransaction current = this.scope.getTransaction();
/* 210 */     if (current != null) {
/* 211 */       return current;
/*     */     }
/* 213 */     ITransaction isolation = this.isolationScope.getTransaction();
/* 214 */     if (isolation != null) {
/* 215 */       return isolation;
/*     */     }
/* 217 */     return this.globalScope.getTransaction();
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 222 */     getDefaultWriteScope().clear();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Map<String, String> getTags() {
/* 227 */     Map<String, String> allTags = new ConcurrentHashMap<>();
/* 228 */     allTags.putAll(this.globalScope.getTags());
/* 229 */     allTags.putAll(this.isolationScope.getTags());
/* 230 */     allTags.putAll(this.scope.getTags());
/* 231 */     return allTags;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTag(@Nullable String key, @Nullable String value) {
/* 236 */     getDefaultWriteScope().setTag(key, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeTag(@Nullable String key) {
/* 241 */     getDefaultWriteScope().removeTag(key);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Map<String, Object> getExtras() {
/* 246 */     Map<String, Object> allTags = new ConcurrentHashMap<>();
/* 247 */     allTags.putAll(this.globalScope.getExtras());
/* 248 */     allTags.putAll(this.isolationScope.getExtras());
/* 249 */     allTags.putAll(this.scope.getExtras());
/* 250 */     return allTags;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setExtra(@Nullable String key, @Nullable String value) {
/* 255 */     getDefaultWriteScope().setExtra(key, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeExtra(@Nullable String key) {
/* 260 */     getDefaultWriteScope().removeExtra(key);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Contexts getContexts() {
/* 265 */     return new CombinedContextsView(this.globalScope
/* 266 */         .getContexts(), this.isolationScope
/* 267 */         .getContexts(), this.scope
/* 268 */         .getContexts(), 
/* 269 */         getOptions().getDefaultScopeType());
/*     */   }
/*     */ 
/*     */   
/*     */   public void setContexts(@Nullable String key, @Nullable Object value) {
/* 274 */     getDefaultWriteScope().setContexts(key, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setContexts(@Nullable String key, @Nullable Boolean value) {
/* 279 */     getDefaultWriteScope().setContexts(key, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setContexts(@Nullable String key, @Nullable String value) {
/* 284 */     getDefaultWriteScope().setContexts(key, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setContexts(@Nullable String key, @Nullable Number value) {
/* 289 */     getDefaultWriteScope().setContexts(key, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setContexts(@Nullable String key, @Nullable Collection<?> value) {
/* 294 */     getDefaultWriteScope().setContexts(key, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setContexts(@Nullable String key, @Nullable Object[] value) {
/* 299 */     getDefaultWriteScope().setContexts(key, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setContexts(@Nullable String key, @Nullable Character value) {
/* 304 */     getDefaultWriteScope().setContexts(key, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeContexts(@Nullable String key) {
/* 309 */     getDefaultWriteScope().removeContexts(key);
/*     */   }
/*     */   @NotNull
/*     */   private IScope getDefaultWriteScope() {
/* 313 */     return getSpecificScope(null);
/*     */   }
/*     */   
/*     */   IScope getSpecificScope(@Nullable ScopeType scopeType) {
/* 317 */     if (scopeType != null) {
/* 318 */       switch (scopeType) {
/*     */         case CURRENT:
/* 320 */           return this.scope;
/*     */         case ISOLATION:
/* 322 */           return this.isolationScope;
/*     */         case GLOBAL:
/* 324 */           return this.globalScope;
/*     */         case COMBINED:
/* 326 */           return this;
/*     */       } 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 332 */     switch (getOptions().getDefaultScopeType()) {
/*     */       case CURRENT:
/* 334 */         return this.scope;
/*     */       case ISOLATION:
/* 336 */         return this.isolationScope;
/*     */       case GLOBAL:
/* 338 */         return this.globalScope;
/*     */     } 
/*     */     
/* 341 */     return this.scope;
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public List<Attachment> getAttachments() {
/* 347 */     List<Attachment> allAttachments = new CopyOnWriteArrayList<>();
/* 348 */     allAttachments.addAll(this.globalScope.getAttachments());
/* 349 */     allAttachments.addAll(this.isolationScope.getAttachments());
/* 350 */     allAttachments.addAll(this.scope.getAttachments());
/* 351 */     return allAttachments;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAttachment(@NotNull Attachment attachment) {
/* 356 */     getDefaultWriteScope().addAttachment(attachment);
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearAttachments() {
/* 361 */     getDefaultWriteScope().clearAttachments();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public List<EventProcessorAndOrder> getEventProcessorsWithOrder() {
/* 366 */     List<EventProcessorAndOrder> allEventProcessors = new CopyOnWriteArrayList<>();
/* 367 */     allEventProcessors.addAll(this.globalScope.getEventProcessorsWithOrder());
/* 368 */     allEventProcessors.addAll(this.isolationScope.getEventProcessorsWithOrder());
/* 369 */     allEventProcessors.addAll(this.scope.getEventProcessorsWithOrder());
/* 370 */     Collections.sort(allEventProcessors);
/* 371 */     return allEventProcessors;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public List<EventProcessor> getEventProcessors() {
/* 376 */     return EventProcessorUtils.unwrap(getEventProcessorsWithOrder());
/*     */   }
/*     */ 
/*     */   
/*     */   public void addEventProcessor(@NotNull EventProcessor eventProcessor) {
/* 381 */     getDefaultWriteScope().addEventProcessor(eventProcessor);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Session withSession(Scope.IWithSession sessionCallback) {
/* 386 */     return getDefaultWriteScope().withSession(sessionCallback);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Scope.SessionPair startSession() {
/* 391 */     return getDefaultWriteScope().startSession();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Session endSession() {
/* 396 */     return getDefaultWriteScope().endSession();
/*     */   }
/*     */ 
/*     */   
/*     */   public void withTransaction(Scope.IWithTransaction callback) {
/* 401 */     getDefaultWriteScope().withTransaction(callback);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public SentryOptions getOptions() {
/* 406 */     return this.globalScope.getOptions();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Session getSession() {
/* 411 */     Session current = this.scope.getSession();
/* 412 */     if (current != null) {
/* 413 */       return current;
/*     */     }
/* 415 */     Session isolation = this.isolationScope.getSession();
/* 416 */     if (isolation != null) {
/* 417 */       return isolation;
/*     */     }
/* 419 */     return this.globalScope.getSession();
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearSession() {
/* 424 */     getDefaultWriteScope().clearSession();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPropagationContext(@NotNull PropagationContext propagationContext) {
/* 429 */     getDefaultWriteScope().setPropagationContext(propagationContext);
/*     */   }
/*     */   
/*     */   @Internal
/*     */   @NotNull
/*     */   public PropagationContext getPropagationContext() {
/* 435 */     return getDefaultWriteScope().getPropagationContext();
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public PropagationContext withPropagationContext(Scope.IWithPropagationContext callback) {
/* 441 */     return getDefaultWriteScope().withPropagationContext(callback);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public IScope clone() {
/* 446 */     return new CombinedScopeView(this.globalScope, this.isolationScope.clone(), this.scope.clone());
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLastEventId(@NotNull SentryId lastEventId) {
/* 451 */     this.globalScope.setLastEventId(lastEventId);
/* 452 */     this.isolationScope.setLastEventId(lastEventId);
/* 453 */     this.scope.setLastEventId(lastEventId);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public SentryId getLastEventId() {
/* 458 */     return this.globalScope.getLastEventId();
/*     */   }
/*     */ 
/*     */   
/*     */   public void bindClient(@NotNull ISentryClient client) {
/* 463 */     getDefaultWriteScope().bindClient(client);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public ISentryClient getClient() {
/* 468 */     ISentryClient current = this.scope.getClient();
/* 469 */     if (!(current instanceof NoOpSentryClient)) {
/* 470 */       return current;
/*     */     }
/* 472 */     ISentryClient isolation = this.isolationScope.getClient();
/* 473 */     if (!(isolation instanceof NoOpSentryClient)) {
/* 474 */       return isolation;
/*     */     }
/* 476 */     return this.globalScope.getClient();
/*     */   }
/*     */ 
/*     */   
/*     */   public void assignTraceContext(@NotNull SentryEvent event) {
/* 481 */     this.globalScope.assignTraceContext(event);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSpanContext(@NotNull Throwable throwable, @NotNull ISpan span, @NotNull String transactionName) {
/* 487 */     this.globalScope.setSpanContext(throwable, span, transactionName);
/*     */   }
/*     */ 
/*     */   
/*     */   @Internal
/*     */   public void replaceOptions(@NotNull SentryOptions options) {
/* 493 */     this.globalScope.replaceOptions(options);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public SentryId getReplayId() {
/* 498 */     SentryId current = this.scope.getReplayId();
/* 499 */     if (!SentryId.EMPTY_ID.equals(current)) {
/* 500 */       return current;
/*     */     }
/* 502 */     SentryId isolation = this.isolationScope.getReplayId();
/* 503 */     if (!SentryId.EMPTY_ID.equals(isolation)) {
/* 504 */       return isolation;
/*     */     }
/* 506 */     return this.globalScope.getReplayId();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setReplayId(@NotNull SentryId replayId) {
/* 511 */     getDefaultWriteScope().setReplayId(replayId);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addFeatureFlag(@Nullable String flag, @Nullable Boolean result) {
/* 516 */     getDefaultWriteScope().addFeatureFlag(flag, result);
/* 517 */     ISpan span = getSpan();
/* 518 */     if (span != null) {
/* 519 */       span.addFeatureFlag(flag, result);
/*     */     }
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public FeatureFlags getFeatureFlags() {
/* 525 */     return getFeatureFlagBuffer().getFeatureFlags();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public IFeatureFlagBuffer getFeatureFlagBuffer() {
/* 530 */     return FeatureFlagBuffer.merged(
/* 531 */         getOptions(), this.globalScope
/* 532 */         .getFeatureFlagBuffer(), this.isolationScope
/* 533 */         .getFeatureFlagBuffer(), this.scope
/* 534 */         .getFeatureFlagBuffer());
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\CombinedScopeView.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */