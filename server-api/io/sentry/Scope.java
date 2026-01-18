/*      */ package io.sentry;
/*      */ 
/*      */ import io.sentry.featureflags.FeatureFlagBuffer;
/*      */ import io.sentry.featureflags.IFeatureFlagBuffer;
/*      */ import io.sentry.internal.eventprocessor.EventProcessorAndOrder;
/*      */ import io.sentry.protocol.App;
/*      */ import io.sentry.protocol.Contexts;
/*      */ import io.sentry.protocol.FeatureFlags;
/*      */ import io.sentry.protocol.Request;
/*      */ import io.sentry.protocol.SentryId;
/*      */ import io.sentry.protocol.TransactionNameSource;
/*      */ import io.sentry.protocol.User;
/*      */ import io.sentry.util.AutoClosableReentrantLock;
/*      */ import io.sentry.util.CollectionUtils;
/*      */ import io.sentry.util.EventProcessorUtils;
/*      */ import io.sentry.util.ExceptionUtils;
/*      */ import io.sentry.util.Objects;
/*      */ import io.sentry.util.Pair;
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Queue;
/*      */ import java.util.WeakHashMap;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import java.util.concurrent.CopyOnWriteArrayList;
/*      */ import org.jetbrains.annotations.ApiStatus.Internal;
/*      */ import org.jetbrains.annotations.NotNull;
/*      */ import org.jetbrains.annotations.Nullable;
/*      */ 
/*      */ 
/*      */ public final class Scope
/*      */   implements IScope
/*      */ {
/*      */   @NotNull
/*      */   private volatile SentryId lastEventId;
/*      */   @Nullable
/*      */   private SentryLevel level;
/*      */   @Nullable
/*      */   private ITransaction transaction;
/*      */   @NotNull
/*   45 */   private WeakReference<ISpan> activeSpan = new WeakReference<>(null);
/*      */   
/*      */   @Nullable
/*      */   private String transactionName;
/*      */   
/*      */   @Nullable
/*      */   private User user;
/*      */   
/*      */   @Nullable
/*      */   private String screen;
/*      */   
/*      */   @Nullable
/*      */   private Request request;
/*      */   
/*      */   @NotNull
/*   60 */   private List<String> fingerprint = new ArrayList<>();
/*      */   
/*      */   @NotNull
/*      */   private volatile Queue<Breadcrumb> breadcrumbs;
/*      */   
/*      */   @NotNull
/*   66 */   private Map<String, String> tags = new ConcurrentHashMap<>();
/*      */   
/*      */   @NotNull
/*   69 */   private Map<String, Object> extra = new ConcurrentHashMap<>();
/*      */   
/*      */   @NotNull
/*   72 */   private List<EventProcessorAndOrder> eventProcessors = new CopyOnWriteArrayList<>();
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   private volatile SentryOptions options;
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   private volatile Session session;
/*      */   
/*      */   @NotNull
/*   83 */   private final AutoClosableReentrantLock sessionLock = new AutoClosableReentrantLock();
/*      */   
/*      */   @NotNull
/*   86 */   private final AutoClosableReentrantLock transactionLock = new AutoClosableReentrantLock();
/*      */ 
/*      */   
/*      */   @NotNull
/*   90 */   private final AutoClosableReentrantLock propagationContextLock = new AutoClosableReentrantLock();
/*      */ 
/*      */   
/*      */   @NotNull
/*   94 */   private Contexts contexts = new Contexts();
/*      */   
/*      */   @NotNull
/*   97 */   private List<Attachment> attachments = new CopyOnWriteArrayList<>();
/*      */   
/*      */   @NotNull
/*      */   private PropagationContext propagationContext;
/*      */   @NotNull
/*  102 */   private SentryId replayId = SentryId.EMPTY_ID;
/*      */   @NotNull
/*  104 */   private ISentryClient client = NoOpSentryClient.getInstance();
/*      */   
/*      */   @NotNull
/*  107 */   private final Map<Throwable, Pair<WeakReference<ISpan>, String>> throwableToSpan = Collections.synchronizedMap(new WeakHashMap<>());
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   private final IFeatureFlagBuffer featureFlags;
/*      */ 
/*      */ 
/*      */   
/*      */   public Scope(@NotNull SentryOptions options) {
/*  117 */     this.options = (SentryOptions)Objects.requireNonNull(options, "SentryOptions is required.");
/*  118 */     this.breadcrumbs = createBreadcrumbsList(this.options.getMaxBreadcrumbs());
/*  119 */     this.featureFlags = FeatureFlagBuffer.create(options);
/*  120 */     this.propagationContext = new PropagationContext();
/*  121 */     this.lastEventId = SentryId.EMPTY_ID;
/*      */   }
/*      */   
/*      */   private Scope(@NotNull Scope scope) {
/*  125 */     this.transaction = scope.transaction;
/*  126 */     this.transactionName = scope.transactionName;
/*  127 */     this.activeSpan = scope.activeSpan;
/*  128 */     this.session = scope.session;
/*  129 */     this.options = scope.options;
/*  130 */     this.level = scope.level;
/*  131 */     this.client = scope.client;
/*  132 */     this.lastEventId = scope.getLastEventId();
/*      */     
/*  134 */     User userRef = scope.user;
/*  135 */     this.user = (userRef != null) ? new User(userRef) : null;
/*  136 */     this.screen = scope.screen;
/*  137 */     this.replayId = scope.replayId;
/*      */     
/*  139 */     Request requestRef = scope.request;
/*  140 */     this.request = (requestRef != null) ? new Request(requestRef) : null;
/*      */     
/*  142 */     this.fingerprint = new ArrayList<>(scope.fingerprint);
/*  143 */     this.eventProcessors = new CopyOnWriteArrayList<>(scope.eventProcessors);
/*      */     
/*  145 */     Breadcrumb[] breadcrumbsRef = (Breadcrumb[])scope.breadcrumbs.toArray((Object[])new Breadcrumb[0]);
/*      */     
/*  147 */     Queue<Breadcrumb> breadcrumbsClone = createBreadcrumbsList(scope.options.getMaxBreadcrumbs());
/*      */     
/*  149 */     for (Breadcrumb item : breadcrumbsRef) {
/*  150 */       Breadcrumb breadcrumbClone = new Breadcrumb(item);
/*  151 */       breadcrumbsClone.add(breadcrumbClone);
/*      */     } 
/*  153 */     this.breadcrumbs = breadcrumbsClone;
/*      */     
/*  155 */     Map<String, String> tagsRef = scope.tags;
/*      */     
/*  157 */     Map<String, String> tagsClone = new ConcurrentHashMap<>();
/*      */     
/*  159 */     for (Map.Entry<String, String> item : tagsRef.entrySet()) {
/*  160 */       if (item != null) {
/*  161 */         tagsClone.put(item.getKey(), item.getValue());
/*      */       }
/*      */     } 
/*      */     
/*  165 */     this.tags = tagsClone;
/*      */     
/*  167 */     Map<String, Object> extraRef = scope.extra;
/*      */     
/*  169 */     Map<String, Object> extraClone = new ConcurrentHashMap<>();
/*      */     
/*  171 */     for (Map.Entry<String, Object> item : extraRef.entrySet()) {
/*  172 */       if (item != null) {
/*  173 */         extraClone.put(item.getKey(), item.getValue());
/*      */       }
/*      */     } 
/*      */     
/*  177 */     this.extra = extraClone;
/*      */     
/*  179 */     this.contexts = new Contexts(scope.contexts);
/*      */     
/*  181 */     this.attachments = new CopyOnWriteArrayList<>(scope.attachments);
/*      */     
/*  183 */     this.featureFlags = scope.featureFlags.clone();
/*      */     
/*  185 */     this.propagationContext = new PropagationContext(scope.propagationContext);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public SentryLevel getLevel() {
/*  195 */     return this.level;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLevel(@Nullable SentryLevel level) {
/*  205 */     this.level = level;
/*      */     
/*  207 */     for (IScopeObserver observer : this.options.getScopeObservers()) {
/*  208 */       observer.setLevel(level);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public String getTransactionName() {
/*  219 */     ITransaction tx = this.transaction;
/*  220 */     return (tx != null) ? tx.getName() : this.transactionName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTransaction(@NotNull String transaction) {
/*  230 */     if (transaction != null) {
/*  231 */       ITransaction tx = this.transaction;
/*  232 */       if (tx != null) {
/*  233 */         tx.setName(transaction, TransactionNameSource.CUSTOM);
/*      */       }
/*  235 */       this.transactionName = transaction;
/*      */       
/*  237 */       for (IScopeObserver observer : this.options.getScopeObservers()) {
/*  238 */         observer.setTransaction(transaction);
/*      */       }
/*      */     } else {
/*  241 */       this.options.getLogger().log(SentryLevel.WARNING, "Transaction cannot be null", new Object[0]);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public ISpan getSpan() {
/*  253 */     ISpan activeSpan = this.activeSpan.get();
/*  254 */     if (activeSpan != null) {
/*  255 */       return activeSpan;
/*      */     }
/*      */     
/*  258 */     ITransaction tx = this.transaction;
/*  259 */     if (tx != null) {
/*  260 */       ISpan span = tx.getLatestActiveSpan();
/*      */       
/*  262 */       if (span != null) {
/*  263 */         return span;
/*      */       }
/*      */     } 
/*  266 */     return tx;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setActiveSpan(@Nullable ISpan span) {
/*  271 */     this.activeSpan = new WeakReference<>(span);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTransaction(@Nullable ITransaction transaction) {
/*  281 */     ISentryLifecycleToken ignored = this.transactionLock.acquire(); try {
/*  282 */       this.transaction = transaction;
/*      */       
/*  284 */       for (IScopeObserver observer : this.options.getScopeObservers()) {
/*  285 */         if (transaction != null) {
/*  286 */           observer.setTransaction(transaction.getName());
/*  287 */           observer.setTrace(transaction.getSpanContext(), this); continue;
/*      */         } 
/*  289 */         observer.setTransaction(null);
/*  290 */         observer.setTrace(null, this);
/*      */       } 
/*      */       
/*  293 */       if (ignored != null) ignored.close(); 
/*      */     } catch (Throwable throwable) {
/*      */       if (ignored != null)
/*      */         try {
/*      */           ignored.close();
/*      */         } catch (Throwable throwable1) {
/*      */           throwable.addSuppressed(throwable1);
/*      */         }  
/*      */       throw throwable;
/*      */     }  } @Nullable
/*  303 */   public User getUser() { return this.user; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUser(@Nullable User user) {
/*  313 */     this.user = user;
/*      */     
/*  315 */     for (IScopeObserver observer : this.options.getScopeObservers()) {
/*  316 */       observer.setUser(user);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Internal
/*      */   @Nullable
/*      */   public String getScreen() {
/*  328 */     return this.screen;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Internal
/*      */   public void setScreen(@Nullable String screen) {
/*  339 */     this.screen = screen;
/*      */     
/*  341 */     Contexts contexts = getContexts();
/*  342 */     App app = contexts.getApp();
/*  343 */     if (app == null) {
/*  344 */       app = new App();
/*  345 */       contexts.setApp(app);
/*      */     } 
/*      */     
/*  348 */     if (screen == null) {
/*  349 */       app.setViewNames(null);
/*      */     } else {
/*  351 */       List<String> viewNames = new ArrayList<>(1);
/*  352 */       viewNames.add(screen);
/*  353 */       app.setViewNames(viewNames);
/*      */     } 
/*      */     
/*  356 */     for (IScopeObserver observer : this.options.getScopeObservers()) {
/*  357 */       observer.setContexts(contexts);
/*      */     }
/*      */   }
/*      */   
/*      */   @NotNull
/*      */   public SentryId getReplayId() {
/*  363 */     return this.replayId;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setReplayId(@NotNull SentryId replayId) {
/*  368 */     this.replayId = replayId;
/*      */     
/*  370 */     for (IScopeObserver observer : this.options.getScopeObservers()) {
/*  371 */       observer.setReplayId(replayId);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public Request getRequest() {
/*  382 */     return this.request;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRequest(@Nullable Request request) {
/*  392 */     this.request = request;
/*      */     
/*  394 */     for (IScopeObserver observer : this.options.getScopeObservers()) {
/*  395 */       observer.setRequest(request);
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
/*      */   @NotNull
/*      */   public List<String> getFingerprint() {
/*  408 */     return this.fingerprint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFingerprint(@NotNull List<String> fingerprint) {
/*  418 */     if (fingerprint == null) {
/*      */       return;
/*      */     }
/*  421 */     this.fingerprint = new ArrayList<>(fingerprint);
/*      */     
/*  423 */     for (IScopeObserver observer : this.options.getScopeObservers()) {
/*  424 */       observer.setFingerprint(fingerprint);
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
/*      */   @NotNull
/*      */   public Queue<Breadcrumb> getBreadcrumbs() {
/*  437 */     return this.breadcrumbs;
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
/*      */   @Nullable
/*      */   private Breadcrumb executeBeforeBreadcrumb(@NotNull SentryOptions.BeforeBreadcrumbCallback callback, @NotNull Breadcrumb breadcrumb, @NotNull Hint hint) {
/*      */     try {
/*  453 */       breadcrumb = callback.execute(breadcrumb, hint);
/*  454 */     } catch (Throwable e) {
/*  455 */       this.options
/*  456 */         .getLogger()
/*  457 */         .log(SentryLevel.ERROR, "The BeforeBreadcrumbCallback callback threw an exception. Exception details will be added to the breadcrumb.", e);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  462 */       if (e.getMessage() != null) {
/*  463 */         breadcrumb.setData("sentry:message", e.getMessage());
/*      */       }
/*      */     } 
/*  466 */     return breadcrumb;
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
/*      */   public void addBreadcrumb(@NotNull Breadcrumb breadcrumb, @Nullable Hint hint) {
/*  478 */     if (breadcrumb == null || this.breadcrumbs instanceof DisabledQueue) {
/*      */       return;
/*      */     }
/*  481 */     if (hint == null) {
/*  482 */       hint = new Hint();
/*      */     }
/*      */     
/*  485 */     SentryOptions.BeforeBreadcrumbCallback callback = this.options.getBeforeBreadcrumb();
/*  486 */     if (callback != null) {
/*  487 */       breadcrumb = executeBeforeBreadcrumb(callback, breadcrumb, hint);
/*      */     }
/*  489 */     if (breadcrumb != null) {
/*  490 */       this.breadcrumbs.add(breadcrumb);
/*      */       
/*  492 */       for (IScopeObserver observer : this.options.getScopeObservers()) {
/*  493 */         observer.addBreadcrumb(breadcrumb);
/*  494 */         observer.setBreadcrumbs(this.breadcrumbs);
/*      */       } 
/*      */     } else {
/*  497 */       this.options.getLogger().log(SentryLevel.INFO, "Breadcrumb was dropped by beforeBreadcrumb", new Object[0]);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addBreadcrumb(@NotNull Breadcrumb breadcrumb) {
/*  509 */     addBreadcrumb(breadcrumb, null);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void clearBreadcrumbs() {
/*  515 */     this.breadcrumbs.clear();
/*      */     
/*  517 */     for (IScopeObserver observer : this.options.getScopeObservers()) {
/*  518 */       observer.setBreadcrumbs(this.breadcrumbs);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void clearTransaction() {
/*  525 */     ISentryLifecycleToken ignored = this.transactionLock.acquire(); 
/*  526 */     try { this.transaction = null;
/*  527 */       if (ignored != null) ignored.close();  } catch (Throwable throwable) { if (ignored != null)
/*  528 */         try { ignored.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  this.transactionName = null;
/*      */     
/*  530 */     for (IScopeObserver observer : this.options.getScopeObservers()) {
/*  531 */       observer.setTransaction(null);
/*  532 */       observer.setTrace(null, this);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public ITransaction getTransaction() {
/*  544 */     return this.transaction;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void clear() {
/*  550 */     this.level = null;
/*  551 */     this.user = null;
/*  552 */     this.request = null;
/*  553 */     this.screen = null;
/*  554 */     this.fingerprint.clear();
/*  555 */     clearBreadcrumbs();
/*  556 */     this.tags.clear();
/*  557 */     this.extra.clear();
/*  558 */     this.eventProcessors.clear();
/*  559 */     clearTransaction();
/*  560 */     clearAttachments();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Internal
/*      */   @NotNull
/*      */   public Map<String, String> getTags() {
/*  572 */     return CollectionUtils.newConcurrentHashMap(this.tags);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTag(@Nullable String key, @Nullable String value) {
/*  583 */     if (key == null) {
/*      */       return;
/*      */     }
/*  586 */     if (value == null) {
/*  587 */       removeTag(key);
/*      */     } else {
/*  589 */       this.tags.put(key, value);
/*      */       
/*  591 */       for (IScopeObserver observer : this.options.getScopeObservers()) {
/*  592 */         observer.setTag(key, value);
/*  593 */         observer.setTags(this.tags);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeTag(@Nullable String key) {
/*  605 */     if (key == null) {
/*      */       return;
/*      */     }
/*  608 */     this.tags.remove(key);
/*      */     
/*  610 */     for (IScopeObserver observer : this.options.getScopeObservers()) {
/*  611 */       observer.removeTag(key);
/*  612 */       observer.setTags(this.tags);
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
/*      */   @NotNull
/*      */   public Map<String, Object> getExtras() {
/*  625 */     return this.extra;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setExtra(@Nullable String key, @Nullable String value) {
/*  636 */     if (key == null) {
/*      */       return;
/*      */     }
/*  639 */     if (value == null) {
/*  640 */       removeExtra(key);
/*      */     } else {
/*  642 */       this.extra.put(key, value);
/*      */       
/*  644 */       for (IScopeObserver observer : this.options.getScopeObservers()) {
/*  645 */         observer.setExtra(key, value);
/*  646 */         observer.setExtras(this.extra);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeExtra(@Nullable String key) {
/*  658 */     if (key == null) {
/*      */       return;
/*      */     }
/*  661 */     this.extra.remove(key);
/*      */     
/*  663 */     for (IScopeObserver observer : this.options.getScopeObservers()) {
/*  664 */       observer.removeExtra(key);
/*  665 */       observer.setExtras(this.extra);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public Contexts getContexts() {
/*  676 */     return this.contexts;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setContexts(@Nullable String key, @Nullable Object value) {
/*  687 */     if (key == null) {
/*      */       return;
/*      */     }
/*  690 */     this.contexts.put(key, value);
/*      */     
/*  692 */     for (IScopeObserver observer : this.options.getScopeObservers()) {
/*  693 */       observer.setContexts(this.contexts);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setContexts(@Nullable String key, @Nullable Boolean value) {
/*  705 */     if (key == null) {
/*      */       return;
/*      */     }
/*  708 */     if (value == null) {
/*      */       
/*  710 */       setContexts(key, (Object)null);
/*      */     } else {
/*  712 */       Map<String, Boolean> map = new HashMap<>();
/*  713 */       map.put("value", value);
/*  714 */       setContexts(key, map);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setContexts(@Nullable String key, @Nullable String value) {
/*  726 */     if (key == null) {
/*      */       return;
/*      */     }
/*  729 */     if (value == null) {
/*      */       
/*  731 */       setContexts(key, (Object)null);
/*      */     } else {
/*  733 */       Map<String, String> map = new HashMap<>();
/*  734 */       map.put("value", value);
/*  735 */       setContexts(key, map);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setContexts(@Nullable String key, @Nullable Number value) {
/*  747 */     if (key == null) {
/*      */       return;
/*      */     }
/*  750 */     if (value == null) {
/*      */       
/*  752 */       setContexts(key, (Object)null);
/*      */     } else {
/*  754 */       Map<String, Number> map = new HashMap<>();
/*  755 */       map.put("value", value);
/*  756 */       setContexts(key, map);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setContexts(@Nullable String key, @Nullable Collection<?> value) {
/*  768 */     if (key == null) {
/*      */       return;
/*      */     }
/*  771 */     if (value == null) {
/*      */       
/*  773 */       setContexts(key, (Object)null);
/*      */     } else {
/*  775 */       Map<String, Collection<?>> map = new HashMap<>();
/*  776 */       map.put("value", value);
/*  777 */       setContexts(key, map);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setContexts(@Nullable String key, @Nullable Object[] value) {
/*  789 */     if (key == null) {
/*      */       return;
/*      */     }
/*  792 */     if (value == null) {
/*      */       
/*  794 */       setContexts(key, (Object)null);
/*      */     } else {
/*  796 */       Map<String, Object[]> map = (Map)new HashMap<>();
/*  797 */       map.put("value", value);
/*  798 */       setContexts(key, map);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setContexts(@Nullable String key, @Nullable Character value) {
/*  810 */     if (key == null) {
/*      */       return;
/*      */     }
/*  813 */     if (value == null) {
/*      */       
/*  815 */       setContexts(key, (Object)null);
/*      */     } else {
/*  817 */       Map<String, Character> map = new HashMap<>();
/*  818 */       map.put("value", value);
/*  819 */       setContexts(key, map);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeContexts(@Nullable String key) {
/*  830 */     if (key == null) {
/*      */       return;
/*      */     }
/*  833 */     this.contexts.remove(key);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Internal
/*      */   @NotNull
/*      */   public List<Attachment> getAttachments() {
/*  845 */     return new CopyOnWriteArrayList<>(this.attachments);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addAttachment(@NotNull Attachment attachment) {
/*  856 */     this.attachments.add(attachment);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void clearAttachments() {
/*  862 */     this.attachments.clear();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   static Queue<Breadcrumb> createBreadcrumbsList(int maxBreadcrumb) {
/*  872 */     return (maxBreadcrumb > 0) ? 
/*  873 */       SynchronizedQueue.<Breadcrumb>synchronizedQueue(new CircularFifoQueue<>(maxBreadcrumb)) : 
/*  874 */       new DisabledQueue<>();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Internal
/*      */   @NotNull
/*      */   public List<EventProcessor> getEventProcessors() {
/*  886 */     return EventProcessorUtils.unwrap(this.eventProcessors);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Internal
/*      */   @NotNull
/*      */   public List<EventProcessorAndOrder> getEventProcessorsWithOrder() {
/*  898 */     return this.eventProcessors;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addEventProcessor(@NotNull EventProcessor eventProcessor) {
/*  908 */     this.eventProcessors.add(new EventProcessorAndOrder(eventProcessor, eventProcessor.getOrder()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Internal
/*      */   @Nullable
/*      */   public Session withSession(@NotNull IWithSession sessionCallback) {
/*  921 */     Session cloneSession = null;
/*  922 */     ISentryLifecycleToken ignored = this.sessionLock.acquire(); 
/*  923 */     try { sessionCallback.accept(this.session);
/*      */       
/*  925 */       if (this.session != null) {
/*  926 */         cloneSession = this.session.clone();
/*      */       }
/*  928 */       if (ignored != null) ignored.close();  } catch (Throwable throwable) { if (ignored != null)
/*  929 */         try { ignored.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  return cloneSession;
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Internal
/*      */   @Nullable
/*      */   public SessionPair startSession() {
/*  953 */     SessionPair pair = null;
/*  954 */     ISentryLifecycleToken ignored = this.sessionLock.acquire(); 
/*  955 */     try { if (this.session != null) {
/*      */         
/*  957 */         this.session.end();
/*      */         
/*  959 */         this.options.getContinuousProfiler().reevaluateSampling();
/*      */       } 
/*  961 */       Session previousSession = this.session;
/*      */       
/*  963 */       if (this.options.getRelease() != null) {
/*  964 */         this
/*      */           
/*  966 */           .session = new Session(this.options.getDistinctId(), this.user, this.options.getEnvironment(), this.options.getRelease());
/*      */         
/*  968 */         Session previousClone = (previousSession != null) ? previousSession.clone() : null;
/*  969 */         pair = new SessionPair(this.session.clone(), previousClone);
/*      */       } else {
/*  971 */         this.options
/*  972 */           .getLogger()
/*  973 */           .log(SentryLevel.WARNING, "Release is not set on SentryOptions. Session could not be started", new Object[0]);
/*      */       } 
/*      */ 
/*      */       
/*  977 */       if (ignored != null) ignored.close();  } catch (Throwable throwable) { if (ignored != null)
/*  978 */         try { ignored.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  return pair;
/*      */   }
/*      */ 
/*      */   
/*      */   static interface IWithSession
/*      */   {
/*      */     void accept(@Nullable Session param1Session);
/*      */   }
/*      */ 
/*      */   
/*      */   static final class SessionPair
/*      */   {
/*      */     @Nullable
/*      */     private final Session previous;
/*      */     
/*      */     @NotNull
/*      */     private final Session current;
/*      */     
/*      */     public SessionPair(@NotNull Session current, @Nullable Session previous) {
/*  997 */       this.current = current;
/*  998 */       this.previous = previous;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Nullable
/*      */     public Session getPrevious() {
/* 1007 */       return this.previous;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @NotNull
/*      */     public Session getCurrent() {
/* 1016 */       return this.current;
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
/*      */   @Nullable
/*      */   public Session endSession() {
/* 1029 */     Session previousSession = null;
/* 1030 */     ISentryLifecycleToken ignored = this.sessionLock.acquire(); 
/* 1031 */     try { if (this.session != null) {
/* 1032 */         this.session.end();
/*      */         
/* 1034 */         this.options.getContinuousProfiler().reevaluateSampling();
/* 1035 */         previousSession = this.session.clone();
/* 1036 */         this.session = null;
/*      */       } 
/* 1038 */       if (ignored != null) ignored.close();  } catch (Throwable throwable) { if (ignored != null)
/* 1039 */         try { ignored.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  return previousSession;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Internal
/*      */   public void withTransaction(@NotNull IWithTransaction callback) {
/* 1050 */     ISentryLifecycleToken ignored = this.transactionLock.acquire(); 
/* 1051 */     try { callback.accept(this.transaction);
/* 1052 */       if (ignored != null) ignored.close();  }
/*      */     catch (Throwable throwable) { if (ignored != null)
/*      */         try {
/*      */           ignored.close();
/*      */         } catch (Throwable throwable1) {
/*      */           throwable.addSuppressed(throwable1);
/*      */         }   throw throwable; }
/* 1059 */      } @Internal @NotNull public SentryOptions getOptions() { return this.options; }
/*      */ 
/*      */   
/*      */   @Internal
/*      */   @Nullable
/*      */   public Session getSession() {
/* 1065 */     return this.session;
/*      */   }
/*      */ 
/*      */   
/*      */   @Internal
/*      */   public void clearSession() {
/* 1071 */     this.session = null;
/*      */   }
/*      */ 
/*      */   
/*      */   @Internal
/*      */   public void setPropagationContext(@NotNull PropagationContext propagationContext) {
/* 1077 */     this.propagationContext = propagationContext;
/*      */     
/* 1079 */     SpanContext spanContext = propagationContext.toSpanContext();
/* 1080 */     for (IScopeObserver observer : this.options.getScopeObservers()) {
/* 1081 */       observer.setTrace(spanContext, this);
/*      */     }
/*      */   }
/*      */   
/*      */   @Internal
/*      */   @NotNull
/*      */   public PropagationContext getPropagationContext() {
/* 1088 */     return this.propagationContext;
/*      */   }
/*      */ 
/*      */   
/*      */   @Internal
/*      */   @NotNull
/*      */   public PropagationContext withPropagationContext(@NotNull IWithPropagationContext callback) {
/* 1095 */     ISentryLifecycleToken ignored = this.propagationContextLock.acquire(); try {
/* 1096 */       callback.accept(this.propagationContext);
/* 1097 */       PropagationContext propagationContext = new PropagationContext(this.propagationContext);
/* 1098 */       if (ignored != null) ignored.close(); 
/*      */       return propagationContext;
/*      */     } catch (Throwable throwable) {
/*      */       if (ignored != null)
/*      */         try {
/*      */           ignored.close();
/*      */         } catch (Throwable throwable1) {
/*      */           throwable.addSuppressed(throwable1);
/*      */         }  
/*      */       throw throwable;
/* 1108 */     }  } @NotNull public IScope clone() { return new Scope(this); }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLastEventId(@NotNull SentryId lastEventId) {
/* 1113 */     this.lastEventId = lastEventId;
/*      */   }
/*      */   
/*      */   @NotNull
/*      */   public SentryId getLastEventId() {
/* 1118 */     return this.lastEventId;
/*      */   }
/*      */ 
/*      */   
/*      */   public void bindClient(@NotNull ISentryClient client) {
/* 1123 */     this.client = client;
/*      */   }
/*      */   
/*      */   @NotNull
/*      */   public ISentryClient getClient() {
/* 1128 */     return this.client;
/*      */   }
/*      */ 
/*      */   
/*      */   public void addFeatureFlag(@Nullable String flag, @Nullable Boolean result) {
/* 1133 */     this.featureFlags.add(flag, result);
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public FeatureFlags getFeatureFlags() {
/* 1138 */     return this.featureFlags.getFeatureFlags();
/*      */   }
/*      */   
/*      */   @NotNull
/*      */   public IFeatureFlagBuffer getFeatureFlagBuffer() {
/* 1143 */     return this.featureFlags;
/*      */   }
/*      */ 
/*      */   
/*      */   @Internal
/*      */   public void assignTraceContext(@NotNull SentryEvent event) {
/* 1149 */     if (this.options.isTracingEnabled() && event.getThrowable() != null) {
/*      */       
/* 1151 */       Pair<WeakReference<ISpan>, String> pair = this.throwableToSpan.get(ExceptionUtils.findRootCause(event.getThrowable()));
/* 1152 */       if (pair != null) {
/* 1153 */         WeakReference<ISpan> spanWeakRef = (WeakReference<ISpan>)pair.getFirst();
/* 1154 */         if (event.getContexts().getTrace() == null && spanWeakRef != null) {
/* 1155 */           ISpan span = spanWeakRef.get();
/* 1156 */           if (span != null) {
/* 1157 */             event.getContexts().setTrace(span.getSpanContext());
/*      */           }
/*      */         } 
/* 1160 */         String transactionName = (String)pair.getSecond();
/* 1161 */         if (event.getTransaction() == null && transactionName != null) {
/* 1162 */           event.setTransaction(transactionName);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Internal
/*      */   public void setSpanContext(@NotNull Throwable throwable, @NotNull ISpan span, @NotNull String transactionName) {
/* 1174 */     Objects.requireNonNull(throwable, "throwable is required");
/* 1175 */     Objects.requireNonNull(span, "span is required");
/* 1176 */     Objects.requireNonNull(transactionName, "transactionName is required");
/*      */     
/* 1178 */     Throwable rootCause = ExceptionUtils.findRootCause(throwable);
/*      */     
/* 1180 */     if (!this.throwableToSpan.containsKey(rootCause)) {
/* 1181 */       this.throwableToSpan.put(rootCause, new Pair(new WeakReference<>(span), transactionName));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   @Internal
/*      */   public void replaceOptions(@NotNull SentryOptions options) {
/* 1188 */     this.options = options;
/* 1189 */     Queue<Breadcrumb> oldBreadcrumbs = this.breadcrumbs;
/* 1190 */     this.breadcrumbs = createBreadcrumbsList(options.getMaxBreadcrumbs());
/* 1191 */     for (Breadcrumb breadcrumb : oldBreadcrumbs)
/*      */     {
/*      */ 
/*      */ 
/*      */       
/* 1196 */       addBreadcrumb(breadcrumb);
/*      */     }
/*      */   }
/*      */   
/*      */   @Internal
/*      */   public static interface IWithTransaction {
/*      */     void accept(@Nullable ITransaction param1ITransaction);
/*      */   }
/*      */   
/*      */   @Internal
/*      */   public static interface IWithPropagationContext {
/*      */     void accept(@NotNull PropagationContext param1PropagationContext);
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\Scope.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */