/*     */ package io.sentry;
/*     */ 
/*     */ import io.sentry.hints.AbnormalExit;
/*     */ import io.sentry.hints.Cached;
/*     */ import io.sentry.protocol.DebugMeta;
/*     */ import io.sentry.protocol.SdkVersion;
/*     */ import io.sentry.protocol.SentryException;
/*     */ import io.sentry.protocol.SentryTransaction;
/*     */ import io.sentry.protocol.User;
/*     */ import io.sentry.util.HintUtils;
/*     */ import io.sentry.util.Objects;
/*     */ import java.io.Closeable;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ import org.jetbrains.annotations.VisibleForTesting;
/*     */ 
/*     */ @Internal
/*     */ public final class MainEventProcessor
/*     */   implements EventProcessor, Closeable {
/*     */   @NotNull
/*     */   private final SentryOptions options;
/*     */   @Nullable
/*  29 */   private volatile HostnameCache hostnameCache = null; @NotNull
/*     */   private final SentryThreadFactory sentryThreadFactory; @NotNull
/*     */   private final SentryExceptionFactory sentryExceptionFactory; public MainEventProcessor(@NotNull SentryOptions options) {
/*  32 */     this.options = (SentryOptions)Objects.requireNonNull(options, "The SentryOptions is required.");
/*     */     
/*  34 */     SentryStackTraceFactory sentryStackTraceFactory = new SentryStackTraceFactory(this.options);
/*     */ 
/*     */     
/*  37 */     this.sentryExceptionFactory = new SentryExceptionFactory(sentryStackTraceFactory);
/*  38 */     this.sentryThreadFactory = new SentryThreadFactory(sentryStackTraceFactory);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   MainEventProcessor(@NotNull SentryOptions options, @NotNull SentryThreadFactory sentryThreadFactory, @NotNull SentryExceptionFactory sentryExceptionFactory) {
/*  45 */     this.options = (SentryOptions)Objects.requireNonNull(options, "The SentryOptions is required.");
/*  46 */     this
/*  47 */       .sentryThreadFactory = (SentryThreadFactory)Objects.requireNonNull(sentryThreadFactory, "The SentryThreadFactory is required.");
/*  48 */     this
/*  49 */       .sentryExceptionFactory = (SentryExceptionFactory)Objects.requireNonNull(sentryExceptionFactory, "The SentryExceptionFactory is required.");
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public SentryEvent process(@NotNull SentryEvent event, @NotNull Hint hint) {
/*  54 */     setCommons(event);
/*  55 */     setExceptions(event);
/*  56 */     setDebugMeta(event);
/*  57 */     setModules(event);
/*     */     
/*  59 */     if (shouldApplyScopeData(event, hint)) {
/*  60 */       processNonCachedEvent(event);
/*  61 */       setThreads(event, hint);
/*     */     } 
/*     */     
/*  64 */     return event;
/*     */   }
/*     */   
/*     */   private void setDebugMeta(@NotNull SentryBaseEvent event) {
/*  68 */     DebugMeta debugMeta = DebugMeta.buildDebugMeta(event.getDebugMeta(), this.options);
/*  69 */     if (debugMeta != null) {
/*  70 */       event.setDebugMeta(debugMeta);
/*     */     }
/*     */   }
/*     */   
/*     */   private void setModules(@NotNull SentryEvent event) {
/*  75 */     Map<String, String> modules = this.options.getModulesLoader().getOrLoadModules();
/*  76 */     if (modules == null) {
/*     */       return;
/*     */     }
/*     */     
/*  80 */     Map<String, String> eventModules = event.getModules();
/*  81 */     if (eventModules == null) {
/*  82 */       event.setModules(modules);
/*     */     } else {
/*  84 */       eventModules.putAll(modules);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean shouldApplyScopeData(@NotNull SentryBaseEvent event, @NotNull Hint hint) {
/*  90 */     if (HintUtils.shouldApplyScopeData(hint)) {
/*  91 */       return true;
/*     */     }
/*  93 */     this.options
/*  94 */       .getLogger()
/*  95 */       .log(SentryLevel.DEBUG, "Event was cached so not applying data relevant to the current app execution/version: %s", new Object[] {
/*     */ 
/*     */           
/*  98 */           event.getEventId() });
/*  99 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private void processNonCachedEvent(@NotNull SentryBaseEvent event) {
/* 104 */     setRelease(event);
/* 105 */     setEnvironment(event);
/* 106 */     setServerName(event);
/* 107 */     setDist(event);
/* 108 */     setSdk(event);
/* 109 */     setTags(event);
/* 110 */     mergeUser(event);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public SentryTransaction process(@NotNull SentryTransaction transaction, @NotNull Hint hint) {
/* 116 */     setCommons((SentryBaseEvent)transaction);
/* 117 */     setDebugMeta((SentryBaseEvent)transaction);
/*     */     
/* 119 */     if (shouldApplyScopeData((SentryBaseEvent)transaction, hint)) {
/* 120 */       processNonCachedEvent((SentryBaseEvent)transaction);
/*     */     }
/*     */     
/* 123 */     return transaction;
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public SentryReplayEvent process(@NotNull SentryReplayEvent event, @NotNull Hint hint) {
/* 129 */     setCommons(event);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 134 */     if (shouldApplyScopeData(event, hint)) {
/* 135 */       processNonCachedEvent(event);
/* 136 */       SdkVersion replaySdkVersion = this.options.getSessionReplay().getSdkVersion();
/* 137 */       if (replaySdkVersion != null)
/*     */       {
/* 139 */         event.setSdk(replaySdkVersion);
/*     */       }
/*     */     } 
/* 142 */     return event;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public SentryLogEvent process(@NotNull SentryLogEvent event) {
/* 147 */     return event;
/*     */   }
/*     */   
/*     */   private void setCommons(@NotNull SentryBaseEvent event) {
/* 151 */     setPlatform(event);
/*     */   }
/*     */   
/*     */   private void setPlatform(@NotNull SentryBaseEvent event) {
/* 155 */     if (event.getPlatform() == null)
/*     */     {
/* 157 */       event.setPlatform("java");
/*     */     }
/*     */   }
/*     */   
/*     */   private void setRelease(@NotNull SentryBaseEvent event) {
/* 162 */     if (event.getRelease() == null) {
/* 163 */       event.setRelease(this.options.getRelease());
/*     */     }
/*     */   }
/*     */   
/*     */   private void setEnvironment(@NotNull SentryBaseEvent event) {
/* 168 */     if (event.getEnvironment() == null) {
/* 169 */       event.setEnvironment(this.options.getEnvironment());
/*     */     }
/*     */   }
/*     */   
/*     */   private void setServerName(@NotNull SentryBaseEvent event) {
/* 174 */     if (event.getServerName() == null) {
/* 175 */       event.setServerName(this.options.getServerName());
/*     */     }
/*     */     
/* 178 */     if (this.options.isAttachServerName() && event.getServerName() == null) {
/* 179 */       ensureHostnameCache();
/* 180 */       if (this.hostnameCache != null) {
/* 181 */         event.setServerName(this.hostnameCache.getHostname());
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void ensureHostnameCache() {
/* 187 */     if (this.hostnameCache == null) {
/* 188 */       this.hostnameCache = HostnameCache.getInstance();
/*     */     }
/*     */   }
/*     */   
/*     */   private void setDist(@NotNull SentryBaseEvent event) {
/* 193 */     if (event.getDist() == null) {
/* 194 */       event.setDist(this.options.getDist());
/*     */     }
/*     */   }
/*     */   
/*     */   private void setSdk(@NotNull SentryBaseEvent event) {
/* 199 */     if (event.getSdk() == null) {
/* 200 */       event.setSdk(this.options.getSdkVersion());
/*     */     }
/*     */   }
/*     */   
/*     */   private void setTags(@NotNull SentryBaseEvent event) {
/* 205 */     if (event.getTags() == null) {
/* 206 */       event.setTags(new HashMap<>(this.options.getTags()));
/*     */     } else {
/* 208 */       for (Map.Entry<String, String> item : this.options.getTags().entrySet()) {
/* 209 */         if (!event.getTags().containsKey(item.getKey())) {
/* 210 */           event.setTag(item.getKey(), item.getValue());
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void mergeUser(@NotNull SentryBaseEvent event) {
/* 217 */     User user = event.getUser();
/* 218 */     if (user == null) {
/* 219 */       user = new User();
/* 220 */       event.setUser(user);
/*     */     } 
/* 222 */     if (user.getIpAddress() == null && this.options.isSendDefaultPii()) {
/* 223 */       user.setIpAddress("{{auto}}");
/*     */     }
/*     */   }
/*     */   
/*     */   private void setExceptions(@NotNull SentryEvent event) {
/* 228 */     Throwable throwable = event.getThrowableMechanism();
/* 229 */     if (throwable != null) {
/* 230 */       event.setExceptions(this.sentryExceptionFactory.getSentryExceptions(throwable));
/*     */     }
/*     */   }
/*     */   
/*     */   private void setThreads(@NotNull SentryEvent event, @NotNull Hint hint) {
/* 235 */     if (event.getThreads() == null) {
/*     */ 
/*     */       
/* 238 */       List<Long> mechanismThreadIds = null;
/*     */       
/* 240 */       List<SentryException> eventExceptions = event.getExceptions();
/*     */       
/* 242 */       if (eventExceptions != null && !eventExceptions.isEmpty()) {
/* 243 */         for (SentryException item : eventExceptions) {
/* 244 */           if (item.getMechanism() != null && item.getThreadId() != null) {
/* 245 */             if (mechanismThreadIds == null) {
/* 246 */               mechanismThreadIds = new ArrayList<>();
/*     */             }
/* 248 */             mechanismThreadIds.add(item.getThreadId());
/*     */           } 
/*     */         } 
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 255 */       if (this.options.isAttachThreads() || HintUtils.hasType(hint, AbnormalExit.class)) {
/* 256 */         Object sentrySdkHint = HintUtils.getSentrySdkHint(hint);
/* 257 */         boolean ignoreCurrentThread = false;
/* 258 */         boolean attachStacktrace = this.options.isAttachStacktrace();
/* 259 */         if (sentrySdkHint instanceof AbnormalExit) {
/* 260 */           ignoreCurrentThread = ((AbnormalExit)sentrySdkHint).ignoreCurrentThread();
/* 261 */           attachStacktrace = true;
/*     */         } 
/* 263 */         event.setThreads(this.sentryThreadFactory
/* 264 */             .getCurrentThreads(mechanismThreadIds, ignoreCurrentThread, attachStacktrace));
/*     */       }
/* 266 */       else if (this.options.isAttachStacktrace() && (eventExceptions == null || eventExceptions
/* 267 */         .isEmpty()) && 
/* 268 */         !isCachedHint(hint)) {
/*     */ 
/*     */         
/* 271 */         event.setThreads(this.sentryThreadFactory.getCurrentThread(this.options.isAttachStacktrace()));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isCachedHint(@NotNull Hint hint) {
/* 284 */     return HintUtils.hasType(hint, Cached.class);
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 289 */     if (this.hostnameCache != null) {
/* 290 */       this.hostnameCache.close();
/*     */     }
/*     */   }
/*     */   
/*     */   boolean isClosed() {
/* 295 */     if (this.hostnameCache != null) {
/* 296 */       return this.hostnameCache.isClosed();
/*     */     }
/* 298 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   @VisibleForTesting
/*     */   @Nullable
/*     */   HostnameCache getHostnameCache() {
/* 305 */     return this.hostnameCache;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Long getOrder() {
/* 310 */     return Long.valueOf(0L);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\MainEventProcessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */