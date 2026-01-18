/*     */ package io.sentry;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ public final class ExternalOptions {
/*     */   private static final String PROXY_PORT_DEFAULT = "80";
/*     */   @Nullable
/*     */   private String dsn;
/*     */   @Nullable
/*     */   private String environment;
/*     */   @Nullable
/*     */   private String release;
/*     */   @Nullable
/*     */   private String dist;
/*     */   @Nullable
/*     */   private String serverName;
/*     */   @Nullable
/*     */   private Boolean enableUncaughtExceptionHandler;
/*     */   @Nullable
/*     */   private Boolean debug;
/*     */   @Nullable
/*     */   private Boolean enableDeduplication;
/*     */   @Nullable
/*     */   private Double tracesSampleRate;
/*     */   @Nullable
/*     */   private Double profilesSampleRate;
/*     */   @Nullable
/*     */   private SentryOptions.RequestSize maxRequestBodySize;
/*     */   @NotNull
/*  29 */   private final Map<String, String> tags = new ConcurrentHashMap<>(); @Nullable
/*     */   private SentryOptions.Proxy proxy; @NotNull
/*  31 */   private final List<String> inAppExcludes = new CopyOnWriteArrayList<>(); @NotNull
/*  32 */   private final List<String> inAppIncludes = new CopyOnWriteArrayList<>(); @Nullable
/*  33 */   private List<String> tracePropagationTargets = null; @NotNull
/*  34 */   private final List<String> contextTags = new CopyOnWriteArrayList<>(); @Nullable
/*     */   private String proguardUuid; @Nullable
/*     */   private Long idleTimeout; @NotNull
/*  37 */   private final Set<Class<? extends Throwable>> ignoredExceptionsForType = new CopyOnWriteArraySet<>(); @Nullable
/*     */   private List<String> ignoredErrors; @Nullable
/*     */   private Boolean printUncaughtStackTrace; @Nullable
/*     */   private Boolean sendClientReports;
/*     */   @NotNull
/*  42 */   private Set<String> bundleIds = new CopyOnWriteArraySet<>();
/*     */   
/*     */   @Nullable
/*     */   private Boolean enabled;
/*     */   
/*     */   @Nullable
/*     */   private Boolean enablePrettySerializationOutput;
/*     */   
/*     */   @Nullable
/*     */   private Boolean enableSpotlight;
/*     */   
/*     */   @Nullable
/*     */   private Boolean enableLogs;
/*     */   
/*     */   @Nullable
/*     */   private String spotlightConnectionUrl;
/*     */   
/*     */   @Nullable
/*     */   private List<String> ignoredCheckIns;
/*     */   @Nullable
/*     */   private List<String> ignoredTransactions;
/*     */   @Nullable
/*     */   private Boolean sendModules;
/*     */   
/*     */   @NotNull
/*     */   public static ExternalOptions from(@NotNull PropertiesProvider propertiesProvider, @NotNull ILogger logger) {
/*  68 */     ExternalOptions options = new ExternalOptions();
/*  69 */     options.setDsn(propertiesProvider.getProperty("dsn"));
/*  70 */     options.setEnvironment(propertiesProvider.getProperty("environment"));
/*  71 */     options.setRelease(propertiesProvider.getProperty("release"));
/*  72 */     options.setDist(propertiesProvider.getProperty("dist"));
/*  73 */     options.setServerName(propertiesProvider.getProperty("servername"));
/*  74 */     options.setEnableUncaughtExceptionHandler(propertiesProvider
/*  75 */         .getBooleanProperty("uncaught.handler.enabled"));
/*  76 */     options.setPrintUncaughtStackTrace(propertiesProvider
/*  77 */         .getBooleanProperty("uncaught.handler.print-stacktrace"));
/*  78 */     options.setTracesSampleRate(propertiesProvider.getDoubleProperty("traces-sample-rate"));
/*  79 */     options.setProfilesSampleRate(propertiesProvider.getDoubleProperty("profiles-sample-rate"));
/*  80 */     options.setDebug(propertiesProvider.getBooleanProperty("debug"));
/*  81 */     options.setEnableDeduplication(propertiesProvider.getBooleanProperty("enable-deduplication"));
/*  82 */     options.setSendClientReports(propertiesProvider.getBooleanProperty("send-client-reports"));
/*  83 */     options.setForceInit(propertiesProvider.getBooleanProperty("force-init"));
/*  84 */     String maxRequestBodySize = propertiesProvider.getProperty("max-request-body-size");
/*  85 */     if (maxRequestBodySize != null) {
/*  86 */       options.setMaxRequestBodySize(
/*  87 */           SentryOptions.RequestSize.valueOf(maxRequestBodySize.toUpperCase(Locale.ROOT)));
/*     */     }
/*  89 */     Map<String, String> tags = propertiesProvider.getMap("tags");
/*  90 */     for (Map.Entry<String, String> tag : tags.entrySet()) {
/*  91 */       options.setTag(tag.getKey(), tag.getValue());
/*     */     }
/*     */     
/*  94 */     String proxyHost = propertiesProvider.getProperty("proxy.host");
/*  95 */     String proxyUser = propertiesProvider.getProperty("proxy.user");
/*  96 */     String proxyPass = propertiesProvider.getProperty("proxy.pass");
/*  97 */     String proxyPort = propertiesProvider.getProperty("proxy.port", "80");
/*     */     
/*  99 */     if (proxyHost != null) {
/* 100 */       options.setProxy(new SentryOptions.Proxy(proxyHost, proxyPort, proxyUser, proxyPass));
/*     */     }
/*     */     
/* 103 */     for (String inAppInclude : propertiesProvider.getList("in-app-includes")) {
/* 104 */       options.addInAppInclude(inAppInclude);
/*     */     }
/* 106 */     for (String inAppExclude : propertiesProvider.getList("in-app-excludes")) {
/* 107 */       options.addInAppExclude(inAppExclude);
/*     */     }
/*     */     
/* 110 */     List<String> tracePropagationTargets = null;
/*     */     
/* 112 */     if (propertiesProvider.getProperty("trace-propagation-targets") != null) {
/* 113 */       tracePropagationTargets = propertiesProvider.getList("trace-propagation-targets");
/*     */     }
/*     */ 
/*     */     
/* 117 */     if (tracePropagationTargets == null && propertiesProvider
/* 118 */       .getProperty("tracing-origins") != null) {
/* 119 */       tracePropagationTargets = propertiesProvider.getList("tracing-origins");
/*     */     }
/*     */     
/* 122 */     if (tracePropagationTargets != null) {
/* 123 */       for (String tracePropagationTarget : tracePropagationTargets) {
/* 124 */         options.addTracePropagationTarget(tracePropagationTarget);
/*     */       }
/*     */     }
/*     */     
/* 128 */     for (String contextTag : propertiesProvider.getList("context-tags")) {
/* 129 */       options.addContextTag(contextTag);
/*     */     }
/* 131 */     options.setProguardUuid(propertiesProvider.getProperty("proguard-uuid"));
/* 132 */     for (String bundleId : propertiesProvider.getList("bundle-ids")) {
/* 133 */       options.addBundleId(bundleId);
/*     */     }
/* 135 */     options.setIdleTimeout(propertiesProvider.getLongProperty("idle-timeout"));
/*     */     
/* 137 */     options.setIgnoredErrors(propertiesProvider.getListOrNull("ignored-errors"));
/*     */     
/* 139 */     options.setEnabled(propertiesProvider.getBooleanProperty("enabled"));
/*     */     
/* 141 */     options.setEnablePrettySerializationOutput(propertiesProvider
/* 142 */         .getBooleanProperty("enable-pretty-serialization-output"));
/*     */     
/* 144 */     options.setSendModules(propertiesProvider.getBooleanProperty("send-modules"));
/* 145 */     options.setSendDefaultPii(propertiesProvider.getBooleanProperty("send-default-pii"));
/*     */     
/* 147 */     options.setIgnoredCheckIns(propertiesProvider.getListOrNull("ignored-checkins"));
/* 148 */     options.setIgnoredTransactions(propertiesProvider.getListOrNull("ignored-transactions"));
/*     */     
/* 150 */     options.setEnableBackpressureHandling(propertiesProvider
/* 151 */         .getBooleanProperty("enable-backpressure-handling"));
/*     */     
/* 153 */     options.setGlobalHubMode(propertiesProvider.getBooleanProperty("global-hub-mode"));
/*     */     
/* 155 */     options.setCaptureOpenTelemetryEvents(propertiesProvider
/* 156 */         .getBooleanProperty("capture-open-telemetry-events"));
/*     */     
/* 158 */     options.setEnableLogs(propertiesProvider.getBooleanProperty("logs.enabled"));
/*     */ 
/*     */     
/* 161 */     for (String ignoredExceptionType : propertiesProvider.getList("ignored-exceptions-for-type")) {
/*     */       try {
/* 163 */         Class<?> clazz = Class.forName(ignoredExceptionType);
/* 164 */         if (Throwable.class.isAssignableFrom(clazz)) {
/* 165 */           options.addIgnoredExceptionForType((Class)clazz); continue;
/*     */         } 
/* 167 */         logger.log(SentryLevel.WARNING, "Skipping setting %s as ignored-exception-for-type. Reason: %s does not extend Throwable", new Object[] { ignoredExceptionType, ignoredExceptionType });
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       }
/* 173 */       catch (ClassNotFoundException e) {
/* 174 */         logger.log(SentryLevel.WARNING, "Skipping setting %s as ignored-exception-for-type. Reason: %s class is not found", new Object[] { ignoredExceptionType, ignoredExceptionType });
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 183 */     Long cronDefaultCheckinMargin = propertiesProvider.getLongProperty("cron.default-checkin-margin");
/*     */     
/* 185 */     Long cronDefaultMaxRuntime = propertiesProvider.getLongProperty("cron.default-max-runtime");
/* 186 */     String cronDefaultTimezone = propertiesProvider.getProperty("cron.default-timezone");
/*     */     
/* 188 */     Long cronDefaultFailureIssueThreshold = propertiesProvider.getLongProperty("cron.default-failure-issue-threshold");
/*     */     
/* 190 */     Long cronDefaultRecoveryThreshold = propertiesProvider.getLongProperty("cron.default-recovery-threshold");
/*     */     
/* 192 */     if (cronDefaultCheckinMargin != null || cronDefaultMaxRuntime != null || cronDefaultTimezone != null || cronDefaultFailureIssueThreshold != null || cronDefaultRecoveryThreshold != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 197 */       SentryOptions.Cron cron = new SentryOptions.Cron();
/* 198 */       cron.setDefaultCheckinMargin(cronDefaultCheckinMargin);
/* 199 */       cron.setDefaultMaxRuntime(cronDefaultMaxRuntime);
/* 200 */       cron.setDefaultTimezone(cronDefaultTimezone);
/* 201 */       cron.setDefaultFailureIssueThreshold(cronDefaultFailureIssueThreshold);
/* 202 */       cron.setDefaultRecoveryThreshold(cronDefaultRecoveryThreshold);
/*     */       
/* 204 */       options.setCron(cron);
/*     */     } 
/*     */     
/* 207 */     options.setEnableSpotlight(propertiesProvider.getBooleanProperty("enable-spotlight"));
/* 208 */     options.setSpotlightConnectionUrl(propertiesProvider.getProperty("spotlight-connection-url"));
/* 209 */     options.setProfileSessionSampleRate(propertiesProvider
/* 210 */         .getDoubleProperty("profile-session-sample-rate"));
/*     */     
/* 212 */     options.setProfilingTracesDirPath(propertiesProvider.getProperty("profiling-traces-dir-path"));
/*     */     
/* 214 */     String profileLifecycleString = propertiesProvider.getProperty("profile-lifecycle");
/* 215 */     if (profileLifecycleString != null && !profileLifecycleString.isEmpty()) {
/* 216 */       options.setProfileLifecycle(ProfileLifecycle.valueOf(profileLifecycleString.toUpperCase()));
/*     */     }
/*     */     
/* 219 */     return options; } @Nullable private Boolean sendDefaultPii; @Nullable private Boolean enableBackpressureHandling; @Nullable private Boolean globalHubMode; @Nullable private Boolean forceInit; @Nullable private Boolean captureOpenTelemetryEvents; @Nullable private Double profileSessionSampleRate; @Nullable
/*     */   private String profilingTracesDirPath; @Nullable
/*     */   private ProfileLifecycle profileLifecycle; @Nullable
/*     */   private SentryOptions.Cron cron; @Nullable
/* 223 */   public String getDsn() { return this.dsn; }
/*     */ 
/*     */   
/*     */   public void setDsn(@Nullable String dsn) {
/* 227 */     this.dsn = dsn;
/*     */   }
/*     */   @Nullable
/*     */   public String getEnvironment() {
/* 231 */     return this.environment;
/*     */   }
/*     */   
/*     */   public void setEnvironment(@Nullable String environment) {
/* 235 */     this.environment = environment;
/*     */   }
/*     */   @Nullable
/*     */   public String getRelease() {
/* 239 */     return this.release;
/*     */   }
/*     */   
/*     */   public void setRelease(@Nullable String release) {
/* 243 */     this.release = release;
/*     */   }
/*     */   @Nullable
/*     */   public String getDist() {
/* 247 */     return this.dist;
/*     */   }
/*     */   
/*     */   public void setDist(@Nullable String dist) {
/* 251 */     this.dist = dist;
/*     */   }
/*     */   @Nullable
/*     */   public String getServerName() {
/* 255 */     return this.serverName;
/*     */   }
/*     */   
/*     */   public void setServerName(@Nullable String serverName) {
/* 259 */     this.serverName = serverName;
/*     */   }
/*     */   @Nullable
/*     */   public Boolean getEnableUncaughtExceptionHandler() {
/* 263 */     return this.enableUncaughtExceptionHandler;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setEnableUncaughtExceptionHandler(@Nullable Boolean enableUncaughtExceptionHandler) {
/* 268 */     this.enableUncaughtExceptionHandler = enableUncaughtExceptionHandler;
/*     */   }
/*     */   @Nullable
/*     */   public List<String> getTracePropagationTargets() {
/* 272 */     return this.tracePropagationTargets;
/*     */   }
/*     */   @Nullable
/*     */   public Boolean getDebug() {
/* 276 */     return this.debug;
/*     */   }
/*     */   
/*     */   public void setDebug(@Nullable Boolean debug) {
/* 280 */     this.debug = debug;
/*     */   }
/*     */   @Nullable
/*     */   public Boolean getEnableDeduplication() {
/* 284 */     return this.enableDeduplication;
/*     */   }
/*     */   
/*     */   public void setEnableDeduplication(@Nullable Boolean enableDeduplication) {
/* 288 */     this.enableDeduplication = enableDeduplication;
/*     */   }
/*     */   @Nullable
/*     */   public Double getTracesSampleRate() {
/* 292 */     return this.tracesSampleRate;
/*     */   }
/*     */   
/*     */   public void setTracesSampleRate(@Nullable Double tracesSampleRate) {
/* 296 */     this.tracesSampleRate = tracesSampleRate;
/*     */   }
/*     */   @Nullable
/*     */   public Double getProfilesSampleRate() {
/* 300 */     return this.profilesSampleRate;
/*     */   }
/*     */   
/*     */   public void setProfilesSampleRate(@Nullable Double profilesSampleRate) {
/* 304 */     this.profilesSampleRate = profilesSampleRate;
/*     */   }
/*     */   @Nullable
/*     */   public SentryOptions.RequestSize getMaxRequestBodySize() {
/* 308 */     return this.maxRequestBodySize;
/*     */   }
/*     */   
/*     */   public void setMaxRequestBodySize(@Nullable SentryOptions.RequestSize maxRequestBodySize) {
/* 312 */     this.maxRequestBodySize = maxRequestBodySize;
/*     */   }
/*     */   @NotNull
/*     */   public Map<String, String> getTags() {
/* 316 */     return this.tags;
/*     */   }
/*     */   @Nullable
/*     */   public SentryOptions.Proxy getProxy() {
/* 320 */     return this.proxy;
/*     */   }
/*     */   
/*     */   public void setProxy(@Nullable SentryOptions.Proxy proxy) {
/* 324 */     this.proxy = proxy;
/*     */   }
/*     */   @NotNull
/*     */   public List<String> getInAppExcludes() {
/* 328 */     return this.inAppExcludes;
/*     */   }
/*     */   @NotNull
/*     */   public List<String> getInAppIncludes() {
/* 332 */     return this.inAppIncludes;
/*     */   }
/*     */   @NotNull
/*     */   public List<String> getContextTags() {
/* 336 */     return this.contextTags;
/*     */   }
/*     */   @Nullable
/*     */   public String getProguardUuid() {
/* 340 */     return this.proguardUuid;
/*     */   }
/*     */   
/*     */   public void setProguardUuid(@Nullable String proguardUuid) {
/* 344 */     this.proguardUuid = proguardUuid;
/*     */   }
/*     */   @NotNull
/*     */   public Set<Class<? extends Throwable>> getIgnoredExceptionsForType() {
/* 348 */     return this.ignoredExceptionsForType;
/*     */   }
/*     */   
/*     */   public void addInAppInclude(@NotNull String include) {
/* 352 */     this.inAppIncludes.add(include);
/*     */   }
/*     */   
/*     */   public void addInAppExclude(@NotNull String exclude) {
/* 356 */     this.inAppExcludes.add(exclude);
/*     */   }
/*     */   
/*     */   public void addTracePropagationTarget(@NotNull String tracePropagationTarget) {
/* 360 */     if (this.tracePropagationTargets == null) {
/* 361 */       this.tracePropagationTargets = new CopyOnWriteArrayList<>();
/*     */     }
/* 363 */     if (!tracePropagationTarget.isEmpty()) {
/* 364 */       this.tracePropagationTargets.add(tracePropagationTarget);
/*     */     }
/*     */   }
/*     */   
/*     */   public void addContextTag(@NotNull String contextTag) {
/* 369 */     this.contextTags.add(contextTag);
/*     */   }
/*     */   
/*     */   public void addIgnoredExceptionForType(@NotNull Class<? extends Throwable> exceptionType) {
/* 373 */     this.ignoredExceptionsForType.add(exceptionType);
/*     */   }
/*     */   
/*     */   public void setTag(@NotNull String key, @NotNull String value) {
/* 377 */     this.tags.put(key, value);
/*     */   }
/*     */   @Nullable
/*     */   public Boolean getPrintUncaughtStackTrace() {
/* 381 */     return this.printUncaughtStackTrace;
/*     */   }
/*     */   
/*     */   public void setPrintUncaughtStackTrace(@Nullable Boolean printUncaughtStackTrace) {
/* 385 */     this.printUncaughtStackTrace = printUncaughtStackTrace;
/*     */   }
/*     */   @Nullable
/*     */   public Long getIdleTimeout() {
/* 389 */     return this.idleTimeout;
/*     */   }
/*     */   
/*     */   public void setIdleTimeout(@Nullable Long idleTimeout) {
/* 393 */     this.idleTimeout = idleTimeout;
/*     */   }
/*     */   @Nullable
/*     */   public List<String> getIgnoredErrors() {
/* 397 */     return this.ignoredErrors;
/*     */   }
/*     */   
/*     */   public void setIgnoredErrors(@Nullable List<String> ignoredErrors) {
/* 401 */     this.ignoredErrors = ignoredErrors;
/*     */   }
/*     */   @Nullable
/*     */   public Boolean getSendClientReports() {
/* 405 */     return this.sendClientReports;
/*     */   }
/*     */   
/*     */   public void setSendClientReports(@Nullable Boolean sendClientReports) {
/* 409 */     this.sendClientReports = sendClientReports;
/*     */   }
/*     */   @NotNull
/*     */   public Set<String> getBundleIds() {
/* 413 */     return this.bundleIds;
/*     */   }
/*     */   
/*     */   public void addBundleId(@NotNull String bundleId) {
/* 417 */     this.bundleIds.add(bundleId);
/*     */   }
/*     */   @Nullable
/*     */   public Boolean isEnabled() {
/* 421 */     return this.enabled;
/*     */   }
/*     */   
/*     */   public void setEnabled(@Nullable Boolean enabled) {
/* 425 */     this.enabled = enabled;
/*     */   }
/*     */   @Nullable
/*     */   public Boolean isEnablePrettySerializationOutput() {
/* 429 */     return this.enablePrettySerializationOutput;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setEnablePrettySerializationOutput(@Nullable Boolean enablePrettySerializationOutput) {
/* 434 */     this.enablePrettySerializationOutput = enablePrettySerializationOutput;
/*     */   }
/*     */   @Nullable
/*     */   public Boolean isSendModules() {
/* 438 */     return this.sendModules;
/*     */   }
/*     */   
/*     */   public void setSendModules(@Nullable Boolean sendModules) {
/* 442 */     this.sendModules = sendModules;
/*     */   }
/*     */   @Nullable
/*     */   public Boolean isSendDefaultPii() {
/* 446 */     return this.sendDefaultPii;
/*     */   }
/*     */   
/*     */   public void setSendDefaultPii(@Nullable Boolean sendDefaultPii) {
/* 450 */     this.sendDefaultPii = sendDefaultPii;
/*     */   }
/*     */   
/*     */   public void setIgnoredCheckIns(@Nullable List<String> ignoredCheckIns) {
/* 454 */     this.ignoredCheckIns = ignoredCheckIns;
/*     */   }
/*     */   @Nullable
/*     */   public List<String> getIgnoredCheckIns() {
/* 458 */     return this.ignoredCheckIns;
/*     */   }
/*     */   
/*     */   public void setIgnoredTransactions(@Nullable List<String> ignoredTransactions) {
/* 462 */     this.ignoredTransactions = ignoredTransactions;
/*     */   }
/*     */   @Nullable
/*     */   public List<String> getIgnoredTransactions() {
/* 466 */     return this.ignoredTransactions;
/*     */   }
/*     */   
/*     */   @Experimental
/*     */   public void setEnableBackpressureHandling(@Nullable Boolean enableBackpressureHandling) {
/* 471 */     this.enableBackpressureHandling = enableBackpressureHandling;
/*     */   }
/*     */   @Experimental
/*     */   @Nullable
/*     */   public Boolean isEnableBackpressureHandling() {
/* 476 */     return this.enableBackpressureHandling;
/*     */   }
/*     */   
/*     */   public void setGlobalHubMode(@Nullable Boolean globalHubMode) {
/* 480 */     this.globalHubMode = globalHubMode;
/*     */   }
/*     */   @Experimental
/*     */   @Nullable
/*     */   public Boolean isGlobalHubMode() {
/* 485 */     return this.globalHubMode;
/*     */   }
/*     */   
/*     */   public void setForceInit(@Nullable Boolean forceInit) {
/* 489 */     this.forceInit = forceInit;
/*     */   }
/*     */   @Nullable
/*     */   public Boolean isForceInit() {
/* 493 */     return this.forceInit;
/*     */   }
/*     */   @Nullable
/*     */   public SentryOptions.Cron getCron() {
/* 497 */     return this.cron;
/*     */   }
/*     */   
/*     */   public void setCron(@Nullable SentryOptions.Cron cron) {
/* 501 */     this.cron = cron;
/*     */   }
/*     */   
/*     */   @Experimental
/*     */   public void setEnableSpotlight(@Nullable Boolean enableSpotlight) {
/* 506 */     this.enableSpotlight = enableSpotlight;
/*     */   }
/*     */   @Experimental
/*     */   @Nullable
/*     */   public Boolean isEnableSpotlight() {
/* 511 */     return this.enableSpotlight;
/*     */   }
/*     */   @Experimental
/*     */   @Nullable
/*     */   public String getSpotlightConnectionUrl() {
/* 516 */     return this.spotlightConnectionUrl;
/*     */   }
/*     */   
/*     */   @Experimental
/*     */   public void setSpotlightConnectionUrl(@Nullable String spotlightConnectionUrl) {
/* 521 */     this.spotlightConnectionUrl = spotlightConnectionUrl;
/*     */   }
/*     */   
/*     */   @Experimental
/*     */   public void setCaptureOpenTelemetryEvents(@Nullable Boolean captureOpenTelemetryEvents) {
/* 526 */     this.captureOpenTelemetryEvents = captureOpenTelemetryEvents;
/*     */   }
/*     */   @Experimental
/*     */   @Nullable
/*     */   public Boolean isCaptureOpenTelemetryEvents() {
/* 531 */     return this.captureOpenTelemetryEvents;
/*     */   }
/*     */   
/*     */   public void setEnableLogs(@Nullable Boolean enableLogs) {
/* 535 */     this.enableLogs = enableLogs;
/*     */   }
/*     */   @Nullable
/*     */   public Boolean isEnableLogs() {
/* 539 */     return this.enableLogs;
/*     */   }
/*     */   @Nullable
/*     */   public Double getProfileSessionSampleRate() {
/* 543 */     return this.profileSessionSampleRate;
/*     */   }
/*     */   
/*     */   public void setProfileSessionSampleRate(@Nullable Double profileSessionSampleRate) {
/* 547 */     this.profileSessionSampleRate = profileSessionSampleRate;
/*     */   }
/*     */   @Nullable
/*     */   public String getProfilingTracesDirPath() {
/* 551 */     return this.profilingTracesDirPath;
/*     */   }
/*     */   
/*     */   public void setProfilingTracesDirPath(@Nullable String profilingTracesDirPath) {
/* 555 */     this.profilingTracesDirPath = profilingTracesDirPath;
/*     */   }
/*     */   @Nullable
/*     */   public ProfileLifecycle getProfileLifecycle() {
/* 559 */     return this.profileLifecycle;
/*     */   }
/*     */   
/*     */   public void setProfileLifecycle(@Nullable ProfileLifecycle profileLifecycle) {
/* 563 */     this.profileLifecycle = profileLifecycle;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\ExternalOptions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */