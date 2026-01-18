/*     */ package io.sentry;
/*     */ 
/*     */ import io.sentry.featureflags.IFeatureFlagBuffer;
/*     */ import io.sentry.featureflags.NoOpFeatureFlagBuffer;
/*     */ import io.sentry.internal.eventprocessor.EventProcessorAndOrder;
/*     */ import io.sentry.protocol.Contexts;
/*     */ import io.sentry.protocol.FeatureFlags;
/*     */ import io.sentry.protocol.Request;
/*     */ import io.sentry.protocol.SentryId;
/*     */ import io.sentry.protocol.User;
/*     */ import io.sentry.util.LazyEvaluator;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Queue;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ public final class NoOpScope
/*     */   implements IScope {
/*  25 */   private static final NoOpScope instance = new NoOpScope();
/*     */   @NotNull
/*  27 */   private final LazyEvaluator<SentryOptions> emptyOptions = new LazyEvaluator(() -> SentryOptions.empty());
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NoOpScope getInstance() {
/*  33 */     return instance;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public SentryLevel getLevel() {
/*  38 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLevel(@Nullable SentryLevel level) {}
/*     */   
/*     */   @Nullable
/*     */   public String getTransactionName() {
/*  46 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTransaction(@NotNull String transaction) {}
/*     */   
/*     */   @Nullable
/*     */   public ISpan getSpan() {
/*  54 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setActiveSpan(@Nullable ISpan span) {}
/*     */ 
/*     */   
/*     */   public void setTransaction(@Nullable ITransaction transaction) {}
/*     */   
/*     */   @Nullable
/*     */   public User getUser() {
/*  65 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUser(@Nullable User user) {}
/*     */   
/*     */   @Internal
/*     */   @Nullable
/*     */   public String getScreen() {
/*  74 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   @Internal
/*     */   public void setScreen(@Nullable String screen) {}
/*     */   
/*     */   @NotNull
/*     */   public SentryId getReplayId() {
/*  83 */     return SentryId.EMPTY_ID;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setReplayId(@Nullable SentryId replayId) {}
/*     */   
/*     */   @Nullable
/*     */   public Request getRequest() {
/*  91 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRequest(@Nullable Request request) {}
/*     */   
/*     */   @Internal
/*     */   @NotNull
/*     */   public List<String> getFingerprint() {
/* 100 */     return new ArrayList<>();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFingerprint(@NotNull List<String> fingerprint) {}
/*     */   
/*     */   @Internal
/*     */   @NotNull
/*     */   public Queue<Breadcrumb> getBreadcrumbs() {
/* 109 */     return new ArrayDeque<>();
/*     */   }
/*     */ 
/*     */   
/*     */   public void addBreadcrumb(@NotNull Breadcrumb breadcrumb, @Nullable Hint hint) {}
/*     */ 
/*     */   
/*     */   public void addBreadcrumb(@NotNull Breadcrumb breadcrumb) {}
/*     */ 
/*     */   
/*     */   public void clearBreadcrumbs() {}
/*     */ 
/*     */   
/*     */   public void clearTransaction() {}
/*     */   
/*     */   @Nullable
/*     */   public ITransaction getTransaction() {
/* 126 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {}
/*     */   
/*     */   @Internal
/*     */   @NotNull
/*     */   public Map<String, String> getTags() {
/* 135 */     return new HashMap<>();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTag(@Nullable String key, @Nullable String value) {}
/*     */ 
/*     */   
/*     */   public void removeTag(@Nullable String key) {}
/*     */   
/*     */   @Internal
/*     */   @NotNull
/*     */   public Map<String, Object> getExtras() {
/* 147 */     return new HashMap<>();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setExtra(@Nullable String key, @Nullable String value) {}
/*     */ 
/*     */   
/*     */   public void removeExtra(@Nullable String key) {}
/*     */   
/*     */   @NotNull
/*     */   public Contexts getContexts() {
/* 158 */     return new Contexts();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setContexts(@Nullable String key, @Nullable Object value) {}
/*     */ 
/*     */   
/*     */   public void setContexts(@Nullable String key, @Nullable Boolean value) {}
/*     */ 
/*     */   
/*     */   public void setContexts(@Nullable String key, @Nullable String value) {}
/*     */ 
/*     */   
/*     */   public void setContexts(@Nullable String key, @Nullable Number value) {}
/*     */ 
/*     */   
/*     */   public void setContexts(@Nullable String key, @Nullable Collection<?> value) {}
/*     */ 
/*     */   
/*     */   public void setContexts(@Nullable String key, @Nullable Object[] value) {}
/*     */ 
/*     */   
/*     */   public void setContexts(@Nullable String key, @Nullable Character value) {}
/*     */ 
/*     */   
/*     */   public void removeContexts(@Nullable String key) {}
/*     */   
/*     */   @Internal
/*     */   @NotNull
/*     */   public List<Attachment> getAttachments() {
/* 188 */     return new ArrayList<>();
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAttachment(@NotNull Attachment attachment) {}
/*     */ 
/*     */   
/*     */   public void clearAttachments() {}
/*     */   
/*     */   @Internal
/*     */   @NotNull
/*     */   public List<EventProcessor> getEventProcessors() {
/* 200 */     return new ArrayList<>();
/*     */   }
/*     */   
/*     */   @Internal
/*     */   @NotNull
/*     */   public List<EventProcessorAndOrder> getEventProcessorsWithOrder() {
/* 206 */     return new ArrayList<>();
/*     */   }
/*     */ 
/*     */   
/*     */   public void addEventProcessor(@NotNull EventProcessor eventProcessor) {}
/*     */   
/*     */   @Internal
/*     */   @Nullable
/*     */   public Session withSession(Scope.IWithSession sessionCallback) {
/* 215 */     return null;
/*     */   }
/*     */   
/*     */   @Internal
/*     */   @Nullable
/*     */   public Scope.SessionPair startSession() {
/* 221 */     return null;
/*     */   }
/*     */   
/*     */   @Internal
/*     */   @Nullable
/*     */   public Session endSession() {
/* 227 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   @Internal
/*     */   public void withTransaction(Scope.IWithTransaction callback) {}
/*     */   
/*     */   @Internal
/*     */   @NotNull
/*     */   public SentryOptions getOptions() {
/* 237 */     return (SentryOptions)this.emptyOptions.getValue();
/*     */   }
/*     */   
/*     */   @Internal
/*     */   @Nullable
/*     */   public Session getSession() {
/* 243 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   @Internal
/*     */   public void clearSession() {}
/*     */ 
/*     */   
/*     */   @Internal
/*     */   public void setPropagationContext(@NotNull PropagationContext propagationContext) {}
/*     */   
/*     */   @Internal
/*     */   @NotNull
/*     */   public PropagationContext getPropagationContext() {
/* 257 */     return new PropagationContext();
/*     */   }
/*     */ 
/*     */   
/*     */   @Internal
/*     */   @NotNull
/*     */   public PropagationContext withPropagationContext(Scope.IWithPropagationContext callback) {
/* 264 */     return new PropagationContext();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLastEventId(@NotNull SentryId lastEventId) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public IScope clone() {
/* 277 */     return getInstance();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public SentryId getLastEventId() {
/* 282 */     return SentryId.EMPTY_ID;
/*     */   }
/*     */ 
/*     */   
/*     */   public void bindClient(@NotNull ISentryClient client) {}
/*     */   
/*     */   @NotNull
/*     */   public ISentryClient getClient() {
/* 290 */     return NoOpSentryClient.getInstance();
/*     */   }
/*     */ 
/*     */   
/*     */   public void assignTraceContext(@NotNull SentryEvent event) {}
/*     */ 
/*     */   
/*     */   public void setSpanContext(@NotNull Throwable throwable, @NotNull ISpan span, @NotNull String transactionName) {}
/*     */ 
/*     */   
/*     */   public void replaceOptions(@NotNull SentryOptions options) {}
/*     */ 
/*     */   
/*     */   public void addFeatureFlag(@Nullable String flag, @Nullable Boolean result) {}
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public FeatureFlags getFeatureFlags() {
/* 308 */     return null;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public IFeatureFlagBuffer getFeatureFlagBuffer() {
/* 313 */     return (IFeatureFlagBuffer)NoOpFeatureFlagBuffer.getInstance();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\NoOpScope.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */