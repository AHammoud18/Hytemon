/*      */ package io.sentry;
/*      */ 
/*      */ import io.sentry.backpressure.IBackpressureMonitor;
/*      */ import io.sentry.backpressure.NoOpBackpressureMonitor;
/*      */ import io.sentry.cache.IEnvelopeCache;
/*      */ import io.sentry.cache.PersistingScopeObserver;
/*      */ import io.sentry.clientreport.ClientReportRecorder;
/*      */ import io.sentry.clientreport.DiscardReason;
/*      */ import io.sentry.clientreport.IClientReportRecorder;
/*      */ import io.sentry.clientreport.NoOpClientReportRecorder;
/*      */ import io.sentry.internal.debugmeta.IDebugMetaLoader;
/*      */ import io.sentry.internal.debugmeta.NoOpDebugMetaLoader;
/*      */ import io.sentry.internal.gestures.GestureTargetLocator;
/*      */ import io.sentry.internal.modules.IModulesLoader;
/*      */ import io.sentry.internal.modules.NoOpModulesLoader;
/*      */ import io.sentry.internal.viewhierarchy.ViewHierarchyExporter;
/*      */ import io.sentry.logger.DefaultLoggerBatchProcessorFactory;
/*      */ import io.sentry.logger.ILoggerBatchProcessorFactory;
/*      */ import io.sentry.protocol.SdkVersion;
/*      */ import io.sentry.protocol.SentryId;
/*      */ import io.sentry.protocol.SentryTransaction;
/*      */ import io.sentry.transport.ITransportGate;
/*      */ import io.sentry.transport.NoOpEnvelopeCache;
/*      */ import io.sentry.transport.NoOpTransportGate;
/*      */ import io.sentry.util.AutoClosableReentrantLock;
/*      */ import io.sentry.util.LazyEvaluator;
/*      */ import io.sentry.util.LoadClass;
/*      */ import io.sentry.util.Platform;
/*      */ import io.sentry.util.SampleRateUtils;
/*      */ import io.sentry.util.StringUtils;
/*      */ import io.sentry.util.runtime.IRuntimeManager;
/*      */ import io.sentry.util.runtime.NeutralRuntimeManager;
/*      */ import io.sentry.util.thread.IThreadChecker;
/*      */ import io.sentry.util.thread.NoOpThreadChecker;
/*      */ import java.io.File;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import java.util.concurrent.CopyOnWriteArrayList;
/*      */ import java.util.concurrent.CopyOnWriteArraySet;
/*      */ import javax.net.ssl.SSLSocketFactory;
/*      */ import org.jetbrains.annotations.ApiStatus.Experimental;
/*      */ import org.jetbrains.annotations.ApiStatus.Internal;
/*      */ import org.jetbrains.annotations.NotNull;
/*      */ import org.jetbrains.annotations.Nullable;
/*      */ import org.jetbrains.annotations.TestOnly;
/*      */ 
/*      */ 
/*      */ 
/*      */ public class SentryOptions
/*      */ {
/*      */   @Internal
/*      */   @NotNull
/*      */   public static final String DEFAULT_PROPAGATION_TARGETS = ".*";
/*   60 */   static final SentryLevel DEFAULT_DIAGNOSTIC_LEVEL = SentryLevel.DEBUG;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String DEFAULT_ENVIRONMENT = "production";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*   72 */   private final List<EventProcessor> eventProcessors = new CopyOnWriteArrayList<>();
/*      */   
/*      */   @NotNull
/*   75 */   private final Set<Class<? extends Throwable>> ignoredExceptionsForType = new CopyOnWriteArraySet<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*   82 */   private List<FilterString> ignoredErrors = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*   88 */   private final List<Integration> integrations = new CopyOnWriteArrayList<>();
/*      */   
/*      */   @NotNull
/*   91 */   private final Set<String> bundleIds = new CopyOnWriteArraySet<>();
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   private String dsn;
/*      */ 
/*      */   
/*      */   @NotNull
/*  100 */   private final LazyEvaluator<Dsn> parsedDsn = new LazyEvaluator(() -> new Dsn(this.dsn));
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   private String dsnHash;
/*      */ 
/*      */ 
/*      */   
/*  110 */   private long shutdownTimeoutMillis = 2000L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  117 */   private long flushTimeoutMillis = 15000L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  124 */   private long sessionFlushTimeoutMillis = 15000L;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean debug;
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*  133 */   private ILogger logger = NoOpLogger.getInstance(); @Experimental
/*      */   @NotNull
/*  135 */   private ILogger fatalLogger = NoOpLogger.getInstance();
/*      */   
/*      */   @NotNull
/*  138 */   private SentryLevel diagnosticLevel = DEFAULT_DIAGNOSTIC_LEVEL;
/*      */   
/*      */   @NotNull
/*  141 */   private final LazyEvaluator<ISerializer> serializer = new LazyEvaluator(() -> new JsonSerializer(this));
/*      */ 
/*      */   
/*      */   @NotNull
/*  145 */   private final LazyEvaluator<IEnvelopeReader> envelopeReader = new LazyEvaluator(() -> new EnvelopeReader((ISerializer)this.serializer.getValue()));
/*      */ 
/*      */ 
/*      */   
/*  149 */   private int maxDepth = 100;
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   private String sentryClientName;
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   private BeforeSendCallback beforeSend;
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   private BeforeSendCallback beforeSendFeedback;
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   private BeforeSendTransactionCallback beforeSendTransaction;
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   private BeforeSendReplayCallback beforeSendReplay;
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   private BeforeBreadcrumbCallback beforeBreadcrumb;
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   private OnDiscardCallback onDiscard;
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   private String cacheDirPath;
/*      */ 
/*      */ 
/*      */   
/*  193 */   private int maxCacheItems = 30;
/*      */ 
/*      */   
/*  196 */   private int maxQueueSize = this.maxCacheItems;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  201 */   private int maxBreadcrumbs = 100;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  208 */   private int maxFeatureFlags = 100;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   private String release;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   private String environment;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   private Proxy proxy;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   private Double sampleRate;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   private Double tracesSampleRate;
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   private TracesSamplerCallback tracesSampler;
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   private volatile TracesSampler internalTracesSampler;
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*  253 */   private final List<String> inAppExcludes = new CopyOnWriteArrayList<>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*  259 */   private final List<String> inAppIncludes = new CopyOnWriteArrayList<>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*  265 */   private ITransportFactory transportFactory = NoOpTransportFactory.getInstance();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*  271 */   private ITransportGate transportGate = (ITransportGate)NoOpTransportGate.getInstance();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   private String dist;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean attachThreads;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean attachStacktrace = true;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean enableAutoSessionTracking = true;
/*      */ 
/*      */ 
/*      */   
/*  293 */   private long sessionTrackingIntervalMillis = 30000L;
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   private String distinctId;
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   private String serverName;
/*      */ 
/*      */   
/*      */   private boolean attachServerName = true;
/*      */ 
/*      */   
/*      */   private boolean enableUncaughtExceptionHandler = true;
/*      */ 
/*      */   
/*      */   private boolean printUncaughtStackTrace = false;
/*      */ 
/*      */   
/*      */   @NotNull
/*  314 */   private ISentryExecutorService executorService = NoOpSentryExecutorService.getInstance();
/*      */ 
/*      */   
/*  317 */   private int connectionTimeoutMillis = 30000;
/*      */ 
/*      */   
/*  320 */   private int readTimeoutMillis = 30000;
/*      */   
/*      */   @NotNull
/*  323 */   private IEnvelopeCache envelopeDiskCache = (IEnvelopeCache)NoOpEnvelopeCache.getInstance();
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   private SdkVersion sdkVersion;
/*      */   
/*      */   private boolean sendDefaultPii = false;
/*      */   
/*      */   @Nullable
/*      */   private SSLSocketFactory sslSocketFactory;
/*      */   
/*      */   @NotNull
/*  335 */   private final List<IScopeObserver> observers = new CopyOnWriteArrayList<>();
/*      */   @NotNull
/*  337 */   private final List<IOptionsObserver> optionsObservers = new CopyOnWriteArrayList<>();
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean enableExternalConfiguration;
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*  346 */   private final Map<String, String> tags = new ConcurrentHashMap<>();
/*      */ 
/*      */   
/*  349 */   private long maxAttachmentSize = 20971520L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean enableDeduplication = true;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean enableEventSizeLimiting = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   private OnOversizedEventCallback onOversizedEvent;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  371 */   private int maxSpans = 1000;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean enableShutdownHook = true;
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*  380 */   private RequestSize maxRequestBodySize = RequestSize.NONE;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean traceSampling = true;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   private Double profilesSampleRate;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   private ProfilesSamplerCallback profilesSampler;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  403 */   private long maxTraceFileSize = 5242880L;
/*      */   
/*      */   @NotNull
/*  406 */   private ITransactionProfiler transactionProfiler = NoOpTransactionProfiler.getInstance();
/*      */   
/*      */   @NotNull
/*  409 */   private IContinuousProfiler continuousProfiler = NoOpContinuousProfiler.getInstance();
/*      */   
/*      */   @NotNull
/*  412 */   private IProfileConverter profilerConverter = NoOpProfileConverter.getInstance();
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*  417 */   private List<String> tracePropagationTargets = null;
/*      */   
/*      */   @NotNull
/*  420 */   private final List<String> defaultTracePropagationTargets = Collections.singletonList(".*");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean propagateTraceparent = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   private String proguardUuid;
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*  436 */   private Long idleTimeout = Long.valueOf(3000L);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*  442 */   private final List<String> contextTags = new CopyOnWriteArrayList<>();
/*      */ 
/*      */   
/*      */   private boolean sendClientReports = true;
/*      */   
/*      */   @NotNull
/*  448 */   IClientReportRecorder clientReportRecorder = (IClientReportRecorder)new ClientReportRecorder(this);
/*      */   
/*      */   @NotNull
/*  451 */   private IModulesLoader modulesLoader = (IModulesLoader)NoOpModulesLoader.getInstance();
/*      */   
/*      */   @NotNull
/*  454 */   private IDebugMetaLoader debugMetaLoader = (IDebugMetaLoader)NoOpDebugMetaLoader.getInstance();
/*      */ 
/*      */   
/*      */   private boolean enableUserInteractionTracing = false;
/*      */ 
/*      */   
/*      */   private boolean enableUserInteractionBreadcrumbs = true;
/*      */   
/*      */   @NotNull
/*  463 */   private Instrumenter instrumenter = Instrumenter.SENTRY;
/*      */   
/*      */   @NotNull
/*  466 */   private final List<GestureTargetLocator> gestureTargetLocators = new ArrayList<>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*  472 */   private final List<ViewHierarchyExporter> viewHierarchyExporters = new ArrayList<>();
/*      */   @NotNull
/*  474 */   private IThreadChecker threadChecker = (IThreadChecker)NoOpThreadChecker.getInstance();
/*      */ 
/*      */   
/*      */   private boolean traceOptionsRequests = true;
/*      */   
/*      */   @Internal
/*      */   @NotNull
/*  481 */   private final LazyEvaluator<SentryDateProvider> dateProvider = new LazyEvaluator(() -> new SentryAutoDateProvider());
/*      */ 
/*      */   
/*      */   @NotNull
/*  485 */   private final List<IPerformanceCollector> performanceCollectors = new ArrayList<>();
/*      */ 
/*      */   
/*      */   @NotNull
/*  489 */   private CompositePerformanceCollector compositePerformanceCollector = NoOpCompositePerformanceCollector.getInstance();
/*      */ 
/*      */   
/*      */   private boolean enableTimeToFullDisplayTracing = false;
/*      */ 
/*      */   
/*      */   @NotNull
/*  496 */   private FullyDisplayedReporter fullyDisplayedReporter = FullyDisplayedReporter.getInstance();
/*      */   @NotNull
/*  498 */   private IConnectionStatusProvider connectionStatusProvider = new NoOpConnectionStatusProvider();
/*      */ 
/*      */   
/*      */   private boolean enabled = true;
/*      */ 
/*      */   
/*      */   private boolean enablePrettySerializationOutput = true;
/*      */   
/*      */   private boolean sendModules = true;
/*      */   
/*      */   @Nullable
/*      */   private BeforeEnvelopeCallback beforeEnvelopeCallback;
/*      */   
/*      */   private boolean enableSpotlight = false;
/*      */   
/*      */   @Nullable
/*      */   private String spotlightConnectionUrl;
/*      */   
/*      */   private boolean enableScopePersistence = true;
/*      */   
/*      */   @Experimental
/*      */   @Nullable
/*  520 */   private List<FilterString> ignoredCheckIns = null;
/*      */ 
/*      */ 
/*      */   
/*      */   @Experimental
/*      */   @Nullable
/*  526 */   private List<FilterString> ignoredSpanOrigins = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*  532 */   private List<FilterString> ignoredTransactions = null;
/*      */   @Experimental
/*      */   @NotNull
/*  535 */   private IBackpressureMonitor backpressureMonitor = (IBackpressureMonitor)NoOpBackpressureMonitor.getInstance();
/*      */ 
/*      */   
/*      */   private boolean enableBackpressureHandling = true;
/*      */ 
/*      */   
/*      */   private boolean enableAppStartProfiling = false;
/*      */ 
/*      */   
/*      */   @NotNull
/*  545 */   private ISpanFactory spanFactory = NoOpSpanFactory.getInstance();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  552 */   private int profilingTracesHz = 101; @Experimental
/*      */   @Nullable
/*  554 */   private Cron cron = null;
/*      */   @NotNull
/*      */   private final ExperimentalOptions experimental;
/*      */   @NotNull
/*  558 */   private ReplayController replayController = NoOpReplayController.getInstance();
/*      */   @NotNull
/*  560 */   private IDistributionApi distributionController = NoOpDistributionApi.getInstance();
/*      */ 
/*      */   
/*      */   @Experimental
/*      */   private boolean enableScreenTracking = true;
/*      */ 
/*      */   
/*      */   @NotNull
/*  568 */   private ScopeType defaultScopeType = ScopeType.ISOLATION;
/*      */   @NotNull
/*  570 */   private InitPriority initPriority = InitPriority.MEDIUM;
/*      */   
/*      */   private boolean forceInit = false;
/*      */   
/*      */   @Nullable
/*  575 */   private Boolean globalHubMode = null;
/*      */   @NotNull
/*  577 */   protected final AutoClosableReentrantLock lock = new AutoClosableReentrantLock();
/*      */   @NotNull
/*  579 */   private SentryOpenTelemetryMode openTelemetryMode = SentryOpenTelemetryMode.AUTO;
/*      */   @NotNull
/*      */   private SentryReplayOptions sessionReplay;
/*      */   @NotNull
/*      */   private SentryFeedbackOptions feedbackOptions;
/*      */   @Experimental
/*      */   private boolean captureOpenTelemetryEvents = false;
/*      */   @NotNull
/*  587 */   private IVersionDetector versionDetector = NoopVersionDetector.getInstance();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   private Double profileSessionSampleRate;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*  600 */   private ProfileLifecycle profileLifecycle = ProfileLifecycle.MANUAL;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean startProfilerOnAppStart = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  620 */   private long deadlineTimeout = 30000L;
/*      */   @NotNull
/*  622 */   private Logs logs = new Logs();
/*      */   @NotNull
/*  624 */   private ISocketTagger socketTagger = NoOpSocketTagger.getInstance();
/*      */   
/*      */   @NotNull
/*  627 */   private IRuntimeManager runtimeManager = (IRuntimeManager)new NeutralRuntimeManager(); @Nullable
/*      */   private String profilingTracesDirPath;
/*      */   
/*      */   @NotNull
/*      */   public IProfileConverter getProfilerConverter() {
/*  632 */     return this.profilerConverter;
/*      */   }
/*      */   
/*      */   public void setProfilerConverter(@NotNull IProfileConverter profilerConverter) {
/*  636 */     this.profilerConverter = profilerConverter;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Experimental
/*      */   public static final class DistributionOptions
/*      */   {
/*  647 */     public String orgAuthToken = "";
/*      */ 
/*      */     
/*  650 */     public String orgSlug = "";
/*      */ 
/*      */     
/*  653 */     public String projectSlug = "";
/*      */ 
/*      */     
/*  656 */     public String sentryBaseUrl = "https://sentry.io";
/*      */     
/*      */     @Nullable
/*  659 */     public String buildConfiguration = null;
/*      */   }
/*      */   @NotNull
/*  662 */   private DistributionOptions distribution = new DistributionOptions();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addEventProcessor(@NotNull EventProcessor eventProcessor) {
/*  670 */     this.eventProcessors.add(eventProcessor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public List<EventProcessor> getEventProcessors() {
/*  679 */     return this.eventProcessors;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addIntegration(@NotNull Integration integration) {
/*  688 */     this.integrations.add(integration);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public List<Integration> getIntegrations() {
/*  697 */     return this.integrations;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public String getDsn() {
/*  706 */     return this.dsn;
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
/*      */   @NotNull
/*      */   Dsn retrieveParsedDsn() throws IllegalArgumentException {
/*  719 */     return (Dsn)this.parsedDsn.getValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDsn(@Nullable String dsn) {
/*  728 */     this.dsn = dsn;
/*  729 */     this.parsedDsn.resetValue();
/*      */     
/*  731 */     this.dsnHash = StringUtils.calculateStringHash(this.dsn, this.logger);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isDebug() {
/*  740 */     return this.debug;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDebug(boolean debug) {
/*  749 */     this.debug = debug;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public ILogger getLogger() {
/*  758 */     return this.logger;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLogger(@Nullable ILogger logger) {
/*  767 */     this.logger = (logger == null) ? NoOpLogger.getInstance() : new DiagnosticLogger(this, logger);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Experimental
/*      */   @NotNull
/*      */   public ILogger getFatalLogger() {
/*  777 */     return this.fatalLogger;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Experimental
/*      */   public void setFatalLogger(@Nullable ILogger logger) {
/*  787 */     this.fatalLogger = (logger == null) ? NoOpLogger.getInstance() : logger;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public SentryLevel getDiagnosticLevel() {
/*  796 */     return this.diagnosticLevel;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDiagnosticLevel(@Nullable SentryLevel diagnosticLevel) {
/*  805 */     this.diagnosticLevel = (diagnosticLevel != null) ? diagnosticLevel : DEFAULT_DIAGNOSTIC_LEVEL;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public ISerializer getSerializer() {
/*  814 */     return (ISerializer)this.serializer.getValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSerializer(@Nullable ISerializer serializer) {
/*  823 */     this.serializer.setValue((serializer != null) ? serializer : NoOpSerializer.getInstance());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxDepth() {
/*  832 */     return this.maxDepth;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMaxDepth(int maxDepth) {
/*  841 */     this.maxDepth = maxDepth;
/*      */   }
/*      */   @NotNull
/*      */   public IEnvelopeReader getEnvelopeReader() {
/*  845 */     return (IEnvelopeReader)this.envelopeReader.getValue();
/*      */   }
/*      */   
/*      */   public void setEnvelopeReader(@Nullable IEnvelopeReader envelopeReader) {
/*  849 */     this.envelopeReader.setValue(
/*  850 */         (envelopeReader != null) ? envelopeReader : NoOpEnvelopeReader.getInstance());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getShutdownTimeoutMillis() {
/*  859 */     return this.shutdownTimeoutMillis;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setShutdownTimeoutMillis(long shutdownTimeoutMillis) {
/*  868 */     this.shutdownTimeoutMillis = shutdownTimeoutMillis;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public String getSentryClientName() {
/*  877 */     return this.sentryClientName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSentryClientName(@Nullable String sentryClientName) {
/*  886 */     this.sentryClientName = sentryClientName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public BeforeSendCallback getBeforeSend() {
/*  895 */     return this.beforeSend;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBeforeSend(@Nullable BeforeSendCallback beforeSend) {
/*  904 */     this.beforeSend = beforeSend;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public BeforeSendTransactionCallback getBeforeSendTransaction() {
/*  913 */     return this.beforeSendTransaction;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBeforeSendTransaction(@Nullable BeforeSendTransactionCallback beforeSendTransaction) {
/*  923 */     this.beforeSendTransaction = beforeSendTransaction;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public BeforeSendCallback getBeforeSendFeedback() {
/*  932 */     return this.beforeSendFeedback;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBeforeSendFeedback(@Nullable BeforeSendCallback beforeSendFeedback) {
/*  941 */     this.beforeSendFeedback = beforeSendFeedback;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public BeforeSendReplayCallback getBeforeSendReplay() {
/*  950 */     return this.beforeSendReplay;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBeforeSendReplay(@Nullable BeforeSendReplayCallback beforeSendReplay) {
/*  959 */     this.beforeSendReplay = beforeSendReplay;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public BeforeBreadcrumbCallback getBeforeBreadcrumb() {
/*  968 */     return this.beforeBreadcrumb;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBeforeBreadcrumb(@Nullable BeforeBreadcrumbCallback beforeBreadcrumb) {
/*  977 */     this.beforeBreadcrumb = beforeBreadcrumb;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public OnDiscardCallback getOnDiscard() {
/*  986 */     return this.onDiscard;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setOnDiscard(@Nullable OnDiscardCallback onDiscard) {
/*  995 */     this.onDiscard = onDiscard;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public String getCacheDirPath() {
/* 1004 */     if (this.cacheDirPath == null || this.cacheDirPath.isEmpty()) {
/* 1005 */       return null;
/*      */     }
/*      */     
/* 1008 */     return (this.dsnHash != null) ? (new File(this.cacheDirPath, this.dsnHash)).getAbsolutePath() : this.cacheDirPath;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   String getCacheDirPathWithoutDsn() {
/* 1018 */     if (this.cacheDirPath == null || this.cacheDirPath.isEmpty()) {
/* 1019 */       return null;
/*      */     }
/*      */     
/* 1022 */     return this.cacheDirPath;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public String getOutboxPath() {
/* 1031 */     String cacheDirPath = getCacheDirPath();
/* 1032 */     if (cacheDirPath == null) {
/* 1033 */       return null;
/*      */     }
/* 1035 */     return (new File(cacheDirPath, "outbox")).getAbsolutePath();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCacheDirPath(@Nullable String cacheDirPath) {
/* 1044 */     this.cacheDirPath = cacheDirPath;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxBreadcrumbs() {
/* 1053 */     return this.maxBreadcrumbs;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMaxBreadcrumbs(int maxBreadcrumbs) {
/* 1062 */     this.maxBreadcrumbs = maxBreadcrumbs;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxFeatureFlags() {
/* 1071 */     return this.maxFeatureFlags;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMaxFeatureFlags(int maxFeatureFlags) {
/* 1080 */     this.maxFeatureFlags = maxFeatureFlags;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public String getRelease() {
/* 1089 */     return this.release;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRelease(@Nullable String release) {
/* 1098 */     this.release = release;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public String getEnvironment() {
/* 1107 */     return (this.environment != null) ? this.environment : "production";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEnvironment(@Nullable String environment) {
/* 1116 */     this.environment = environment;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public Proxy getProxy() {
/* 1125 */     return this.proxy;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setProxy(@Nullable Proxy proxy) {
/* 1134 */     this.proxy = proxy;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public Double getSampleRate() {
/* 1143 */     return this.sampleRate;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSampleRate(@Nullable Double sampleRate) {
/* 1152 */     if (!SampleRateUtils.isValidSampleRate(sampleRate)) {
/* 1153 */       throw new IllegalArgumentException("The value " + sampleRate + " is not valid. Use null to disable or values >= 0.0 and <= 1.0.");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1158 */     this.sampleRate = sampleRate;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public Double getTracesSampleRate() {
/* 1167 */     return this.tracesSampleRate;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTracesSampleRate(@Nullable Double tracesSampleRate) {
/* 1176 */     if (!SampleRateUtils.isValidTracesSampleRate(tracesSampleRate)) {
/* 1177 */       throw new IllegalArgumentException("The value " + tracesSampleRate + " is not valid. Use null to disable or values between 0.0 and 1.0.");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1182 */     this.tracesSampleRate = tracesSampleRate;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public TracesSamplerCallback getTracesSampler() {
/* 1191 */     return this.tracesSampler;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTracesSampler(@Nullable TracesSamplerCallback tracesSampler) {
/* 1200 */     this.tracesSampler = tracesSampler;
/*      */   }
/*      */   @Internal
/*      */   @NotNull
/*      */   public TracesSampler getInternalTracesSampler() {
/* 1205 */     if (this.internalTracesSampler == null) {
/* 1206 */       ISentryLifecycleToken ignored = this.lock.acquire(); 
/* 1207 */       try { if (this.internalTracesSampler == null) {
/* 1208 */           this.internalTracesSampler = new TracesSampler(this);
/*      */         }
/* 1210 */         if (ignored != null) ignored.close();  } catch (Throwable throwable) { if (ignored != null)
/*      */           try { ignored.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; } 
/* 1212 */     }  return this.internalTracesSampler;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public List<String> getInAppExcludes() {
/* 1221 */     return this.inAppExcludes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addInAppExclude(@NotNull String exclude) {
/* 1230 */     this.inAppExcludes.add(exclude);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public List<String> getInAppIncludes() {
/* 1239 */     return this.inAppIncludes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addInAppInclude(@NotNull String include) {
/* 1248 */     this.inAppIncludes.add(include);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public ITransportFactory getTransportFactory() {
/* 1257 */     return this.transportFactory;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTransportFactory(@Nullable ITransportFactory transportFactory) {
/* 1266 */     this
/* 1267 */       .transportFactory = (transportFactory != null) ? transportFactory : NoOpTransportFactory.getInstance();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public String getDist() {
/* 1276 */     return this.dist;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDist(@Nullable String dist) {
/* 1285 */     this.dist = dist;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public ITransportGate getTransportGate() {
/* 1294 */     return this.transportGate;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTransportGate(@Nullable ITransportGate transportGate) {
/* 1303 */     this.transportGate = (transportGate != null) ? transportGate : (ITransportGate)NoOpTransportGate.getInstance();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAttachStacktrace() {
/* 1312 */     return this.attachStacktrace;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAttachStacktrace(boolean attachStacktrace) {
/* 1321 */     this.attachStacktrace = attachStacktrace;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAttachThreads() {
/* 1330 */     return this.attachThreads;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAttachThreads(boolean attachThreads) {
/* 1339 */     this.attachThreads = attachThreads;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEnableAutoSessionTracking() {
/* 1348 */     return this.enableAutoSessionTracking;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEnableAutoSessionTracking(boolean enableAutoSessionTracking) {
/* 1357 */     this.enableAutoSessionTracking = enableAutoSessionTracking;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public String getServerName() {
/* 1366 */     return this.serverName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setServerName(@Nullable String serverName) {
/* 1375 */     this.serverName = serverName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAttachServerName() {
/* 1384 */     return this.attachServerName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAttachServerName(boolean attachServerName) {
/* 1393 */     this.attachServerName = attachServerName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getSessionTrackingIntervalMillis() {
/* 1402 */     return this.sessionTrackingIntervalMillis;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSessionTrackingIntervalMillis(long sessionTrackingIntervalMillis) {
/* 1411 */     this.sessionTrackingIntervalMillis = sessionTrackingIntervalMillis;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public String getDistinctId() {
/* 1420 */     return this.distinctId;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDistinctId(@Nullable String distinctId) {
/* 1429 */     this.distinctId = distinctId;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getFlushTimeoutMillis() {
/* 1438 */     return this.flushTimeoutMillis;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFlushTimeoutMillis(long flushTimeoutMillis) {
/* 1447 */     this.flushTimeoutMillis = flushTimeoutMillis;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEnableUncaughtExceptionHandler() {
/* 1456 */     return this.enableUncaughtExceptionHandler;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEnableUncaughtExceptionHandler(boolean enableUncaughtExceptionHandler) {
/* 1465 */     this.enableUncaughtExceptionHandler = enableUncaughtExceptionHandler;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isPrintUncaughtStackTrace() {
/* 1474 */     return this.printUncaughtStackTrace;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPrintUncaughtStackTrace(boolean printUncaughtStackTrace) {
/* 1483 */     this.printUncaughtStackTrace = printUncaughtStackTrace;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Internal
/*      */   @NotNull
/*      */   public ISentryExecutorService getExecutorService() {
/* 1494 */     return this.executorService;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Internal
/*      */   @TestOnly
/*      */   public void setExecutorService(@NotNull ISentryExecutorService executorService) {
/* 1505 */     if (executorService != null) {
/* 1506 */       this.executorService = executorService;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getConnectionTimeoutMillis() {
/* 1516 */     return this.connectionTimeoutMillis;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setConnectionTimeoutMillis(int connectionTimeoutMillis) {
/* 1525 */     this.connectionTimeoutMillis = connectionTimeoutMillis;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getReadTimeoutMillis() {
/* 1534 */     return this.readTimeoutMillis;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setReadTimeoutMillis(int readTimeoutMillis) {
/* 1543 */     this.readTimeoutMillis = readTimeoutMillis;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public IEnvelopeCache getEnvelopeDiskCache() {
/* 1552 */     return this.envelopeDiskCache;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEnvelopeDiskCache(@Nullable IEnvelopeCache envelopeDiskCache) {
/* 1561 */     this
/* 1562 */       .envelopeDiskCache = (envelopeDiskCache != null) ? envelopeDiskCache : (IEnvelopeCache)NoOpEnvelopeCache.getInstance();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxQueueSize() {
/* 1571 */     return this.maxQueueSize;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMaxQueueSize(int maxQueueSize) {
/* 1580 */     if (maxQueueSize > 0) {
/* 1581 */       this.maxQueueSize = maxQueueSize;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public SdkVersion getSdkVersion() {
/* 1591 */     return this.sdkVersion;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public SSLSocketFactory getSslSocketFactory() {
/* 1600 */     return this.sslSocketFactory;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSslSocketFactory(@Nullable SSLSocketFactory sslSocketFactory) {
/* 1609 */     this.sslSocketFactory = sslSocketFactory;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Internal
/*      */   public void setSdkVersion(@Nullable SdkVersion sdkVersion) {
/* 1619 */     SdkVersion replaySdkVersion = getSessionReplay().getSdkVersion();
/* 1620 */     if (this.sdkVersion != null && replaySdkVersion != null && this.sdkVersion
/*      */       
/* 1622 */       .equals(replaySdkVersion))
/*      */     {
/* 1624 */       getSessionReplay().setSdkVersion(sdkVersion);
/*      */     }
/* 1626 */     this.sdkVersion = sdkVersion;
/*      */   }
/*      */   
/*      */   public boolean isSendDefaultPii() {
/* 1630 */     return this.sendDefaultPii;
/*      */   }
/*      */   
/*      */   public void setSendDefaultPii(boolean sendDefaultPii) {
/* 1634 */     this.sendDefaultPii = sendDefaultPii;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addScopeObserver(@NotNull IScopeObserver observer) {
/* 1643 */     this.observers.add(observer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public List<IScopeObserver> getScopeObservers() {
/* 1653 */     return this.observers;
/*      */   }
/*      */   
/*      */   @Internal
/*      */   @Nullable
/*      */   public PersistingScopeObserver findPersistingScopeObserver() {
/* 1659 */     for (IScopeObserver observer : this.observers) {
/* 1660 */       if (observer instanceof PersistingScopeObserver) {
/* 1661 */         return (PersistingScopeObserver)observer;
/*      */       }
/*      */     } 
/* 1664 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addOptionsObserver(@NotNull IOptionsObserver observer) {
/* 1673 */     this.optionsObservers.add(observer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public List<IOptionsObserver> getOptionsObservers() {
/* 1683 */     return this.optionsObservers;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEnableExternalConfiguration() {
/* 1692 */     return this.enableExternalConfiguration;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEnableExternalConfiguration(boolean enableExternalConfiguration) {
/* 1702 */     this.enableExternalConfiguration = enableExternalConfiguration;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public Map<String, String> getTags() {
/* 1711 */     return this.tags;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTag(@Nullable String key, @Nullable String value) {
/* 1721 */     if (key == null) {
/*      */       return;
/*      */     }
/* 1724 */     if (value == null) {
/* 1725 */       this.tags.remove(key);
/*      */     } else {
/* 1727 */       this.tags.put(key, value);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getMaxAttachmentSize() {
/* 1737 */     return this.maxAttachmentSize;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMaxAttachmentSize(long maxAttachmentSize) {
/* 1748 */     this.maxAttachmentSize = maxAttachmentSize;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEnableDeduplication() {
/* 1757 */     return this.enableDeduplication;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEnableDeduplication(boolean enableDeduplication) {
/* 1766 */     this.enableDeduplication = enableDeduplication;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEnableEventSizeLimiting() {
/* 1775 */     return this.enableEventSizeLimiting;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEnableEventSizeLimiting(boolean enableEventSizeLimiting) {
/* 1785 */     this.enableEventSizeLimiting = enableEventSizeLimiting;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public OnOversizedEventCallback getOnOversizedEvent() {
/* 1794 */     return this.onOversizedEvent;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setOnOversizedEvent(@Nullable OnOversizedEventCallback onOversizedEvent) {
/* 1804 */     this.onOversizedEvent = onOversizedEvent;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isTracingEnabled() {
/* 1814 */     return (getTracesSampleRate() != null || getTracesSampler() != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public Set<Class<? extends Throwable>> getIgnoredExceptionsForType() {
/* 1825 */     return this.ignoredExceptionsForType;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addIgnoredExceptionForType(@NotNull Class<? extends Throwable> exceptionType) {
/* 1834 */     this.ignoredExceptionsForType.add(exceptionType);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean containsIgnoredExceptionForType(@NotNull Throwable throwable) {
/* 1844 */     return this.ignoredExceptionsForType.contains(throwable.getClass());
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
/*      */   @Nullable
/*      */   public List<FilterString> getIgnoredErrors() {
/* 1857 */     return this.ignoredErrors;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setIgnoredErrors(@Nullable List<String> ignoredErrors) {
/* 1868 */     if (ignoredErrors == null) {
/* 1869 */       this.ignoredErrors = null;
/*      */     } else {
/* 1871 */       List<FilterString> patterns = new ArrayList<>();
/* 1872 */       for (String pattern : ignoredErrors) {
/* 1873 */         if (pattern != null && !pattern.isEmpty()) {
/* 1874 */           patterns.add(new FilterString(pattern));
/*      */         }
/*      */       } 
/*      */       
/* 1878 */       this.ignoredErrors = patterns;
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
/*      */   public void addIgnoredError(@NotNull String pattern) {
/* 1890 */     if (this.ignoredErrors == null) {
/* 1891 */       this.ignoredErrors = new ArrayList<>();
/*      */     }
/* 1893 */     this.ignoredErrors.add(new FilterString(pattern));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Experimental
/*      */   public int getMaxSpans() {
/* 1903 */     return this.maxSpans;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Experimental
/*      */   public void setMaxSpans(int maxSpans) {
/* 1913 */     this.maxSpans = maxSpans;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEnableShutdownHook() {
/* 1922 */     return this.enableShutdownHook;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEnableShutdownHook(boolean enableShutdownHook) {
/* 1931 */     this.enableShutdownHook = enableShutdownHook;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxCacheItems() {
/* 1940 */     return this.maxCacheItems;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMaxCacheItems(int maxCacheItems) {
/* 1949 */     this.maxCacheItems = maxCacheItems;
/*      */   }
/*      */   @NotNull
/*      */   public RequestSize getMaxRequestBodySize() {
/* 1953 */     return this.maxRequestBodySize;
/*      */   }
/*      */   
/*      */   public void setMaxRequestBodySize(@NotNull RequestSize maxRequestBodySize) {
/* 1957 */     this.maxRequestBodySize = maxRequestBodySize;
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
/*      */   @Experimental
/*      */   public boolean isTraceSampling() {
/* 1970 */     return this.traceSampling;
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
/*      */   @Deprecated
/*      */   public void setTraceSampling(boolean traceSampling) {
/* 1984 */     this.traceSampling = traceSampling;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getMaxTraceFileSize() {
/* 1993 */     return this.maxTraceFileSize;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMaxTraceFileSize(long maxTraceFileSize) {
/* 2002 */     this.maxTraceFileSize = maxTraceFileSize;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public ITransactionProfiler getTransactionProfiler() {
/* 2011 */     return this.transactionProfiler;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTransactionProfiler(@Nullable ITransactionProfiler transactionProfiler) {
/* 2022 */     if (this.transactionProfiler == NoOpTransactionProfiler.getInstance() && transactionProfiler != null)
/*      */     {
/* 2024 */       this.transactionProfiler = transactionProfiler;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public IContinuousProfiler getContinuousProfiler() {
/* 2034 */     return this.continuousProfiler;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setContinuousProfiler(@Nullable IContinuousProfiler continuousProfiler) {
/* 2044 */     if (this.continuousProfiler == NoOpContinuousProfiler.getInstance() && continuousProfiler != null)
/*      */     {
/* 2046 */       this.continuousProfiler = continuousProfiler;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isProfilingEnabled() {
/* 2056 */     return ((this.profilesSampleRate != null && this.profilesSampleRate.doubleValue() > 0.0D) || this.profilesSampler != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Internal
/*      */   public boolean isContinuousProfilingEnabled() {
/* 2067 */     return (this.profilesSampleRate == null && this.profilesSampler == null && this.profileSessionSampleRate != null && this.profileSessionSampleRate
/*      */ 
/*      */       
/* 2070 */       .doubleValue() > 0.0D);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public ProfilesSamplerCallback getProfilesSampler() {
/* 2079 */     return this.profilesSampler;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setProfilesSampler(@Nullable ProfilesSamplerCallback profilesSampler) {
/* 2088 */     this.profilesSampler = profilesSampler;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public Double getProfilesSampleRate() {
/* 2097 */     return this.profilesSampleRate;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setProfilesSampleRate(@Nullable Double profilesSampleRate) {
/* 2108 */     if (!SampleRateUtils.isValidProfilesSampleRate(profilesSampleRate)) {
/* 2109 */       throw new IllegalArgumentException("The value " + profilesSampleRate + " is not valid. Use null to disable or values between 0.0 and 1.0.");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 2114 */     this.profilesSampleRate = profilesSampleRate;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public Double getProfileSessionSampleRate() {
/* 2125 */     return this.profileSessionSampleRate;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setProfileSessionSampleRate(@Nullable Double profileSessionSampleRate) {
/* 2134 */     if (!SampleRateUtils.isValidContinuousProfilesSampleRate(profileSessionSampleRate)) {
/* 2135 */       throw new IllegalArgumentException("The value " + profileSessionSampleRate + " is not valid. Use values between 0.0 and 1.0.");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 2140 */     this.profileSessionSampleRate = profileSessionSampleRate;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public ProfileLifecycle getProfileLifecycle() {
/* 2150 */     return this.profileLifecycle;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setProfileLifecycle(@NotNull ProfileLifecycle profileLifecycle) {
/* 2155 */     this.profileLifecycle = profileLifecycle;
/* 2156 */     if (profileLifecycle == ProfileLifecycle.TRACE && !isTracingEnabled()) {
/* 2157 */       this.logger.log(SentryLevel.WARNING, "Profiling lifecycle is set to TRACE but tracing is disabled. Profiling will not be started automatically.", new Object[0]);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isStartProfilerOnAppStart() {
/* 2168 */     return this.startProfilerOnAppStart;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setStartProfilerOnAppStart(boolean startProfilerOnAppStart) {
/* 2175 */     this.startProfilerOnAppStart = startProfilerOnAppStart;
/*      */   }
/*      */   
/*      */   public long getDeadlineTimeout() {
/* 2179 */     return this.deadlineTimeout;
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
/*      */   public void setDeadlineTimeout(long deadlineTimeout) {
/* 2193 */     this.deadlineTimeout = deadlineTimeout;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public String getProfilingTracesDirPath() {
/* 2202 */     if (this.profilingTracesDirPath != null && !this.profilingTracesDirPath.isEmpty()) {
/* 2203 */       return (this.dsnHash != null) ? (
/* 2204 */         new File(this.profilingTracesDirPath, this.dsnHash)).getAbsolutePath() : 
/* 2205 */         this.profilingTracesDirPath;
/*      */     }
/*      */     
/* 2208 */     String cacheDirPath = getCacheDirPath();
/*      */     
/* 2210 */     if (cacheDirPath == null) {
/* 2211 */       return null;
/*      */     }
/*      */     
/* 2214 */     return (new File(cacheDirPath, "profiling_traces")).getAbsolutePath();
/*      */   }
/*      */   
/*      */   public void setProfilingTracesDirPath(@Nullable String profilingTracesDirPath) {
/* 2218 */     this.profilingTracesDirPath = profilingTracesDirPath;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public List<String> getTracePropagationTargets() {
/* 2227 */     if (this.tracePropagationTargets == null) {
/* 2228 */       return this.defaultTracePropagationTargets;
/*      */     }
/* 2230 */     return this.tracePropagationTargets;
/*      */   }
/*      */   
/*      */   public void setTracePropagationTargets(@Nullable List<String> tracePropagationTargets) {
/* 2234 */     if (tracePropagationTargets == null) {
/* 2235 */       this.tracePropagationTargets = null;
/*      */     } else {
/* 2237 */       List<String> filteredTracePropagationTargets = new ArrayList<>();
/* 2238 */       for (String target : tracePropagationTargets) {
/* 2239 */         if (!target.isEmpty()) {
/* 2240 */           filteredTracePropagationTargets.add(target);
/*      */         }
/*      */       } 
/*      */       
/* 2244 */       this.tracePropagationTargets = filteredTracePropagationTargets;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isPropagateTraceparent() {
/* 2254 */     return this.propagateTraceparent;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPropagateTraceparent(boolean propagateTraceparent) {
/* 2263 */     this.propagateTraceparent = propagateTraceparent;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public String getProguardUuid() {
/* 2272 */     return this.proguardUuid;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setProguardUuid(@Nullable String proguardUuid) {
/* 2281 */     this.proguardUuid = proguardUuid;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addBundleId(@Nullable String bundleId) {
/* 2292 */     if (bundleId != null) {
/* 2293 */       String trimmedBundleId = bundleId.trim();
/* 2294 */       if (!trimmedBundleId.isEmpty()) {
/* 2295 */         this.bundleIds.add(trimmedBundleId);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public Set<String> getBundleIds() {
/* 2306 */     return this.bundleIds;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public List<String> getContextTags() {
/* 2315 */     return this.contextTags;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addContextTag(@NotNull String contextTag) {
/* 2324 */     this.contextTags.add(contextTag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public Long getIdleTimeout() {
/* 2333 */     return this.idleTimeout;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setIdleTimeout(@Nullable Long idleTimeout) {
/* 2342 */     this.idleTimeout = idleTimeout;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSendClientReports() {
/* 2351 */     return this.sendClientReports;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSendClientReports(boolean sendClientReports) {
/* 2360 */     this.sendClientReports = sendClientReports;
/*      */     
/* 2362 */     if (sendClientReports) {
/* 2363 */       this.clientReportRecorder = (IClientReportRecorder)new ClientReportRecorder(this);
/*      */     } else {
/* 2365 */       this.clientReportRecorder = (IClientReportRecorder)new NoOpClientReportRecorder();
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean isEnableUserInteractionTracing() {
/* 2370 */     return this.enableUserInteractionTracing;
/*      */   }
/*      */   
/*      */   public void setEnableUserInteractionTracing(boolean enableUserInteractionTracing) {
/* 2374 */     this.enableUserInteractionTracing = enableUserInteractionTracing;
/*      */   }
/*      */   
/*      */   public boolean isEnableUserInteractionBreadcrumbs() {
/* 2378 */     return this.enableUserInteractionBreadcrumbs;
/*      */   }
/*      */   
/*      */   public void setEnableUserInteractionBreadcrumbs(boolean enableUserInteractionBreadcrumbs) {
/* 2382 */     this.enableUserInteractionBreadcrumbs = enableUserInteractionBreadcrumbs;
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
/*      */   @Deprecated
/*      */   public void setInstrumenter(@NotNull Instrumenter instrumenter) {
/* 2399 */     this.instrumenter = instrumenter;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public Instrumenter getInstrumenter() {
/* 2408 */     return this.instrumenter;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Internal
/*      */   @NotNull
/*      */   public IClientReportRecorder getClientReportRecorder() {
/* 2418 */     return this.clientReportRecorder;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Internal
/*      */   @NotNull
/*      */   public IModulesLoader getModulesLoader() {
/* 2428 */     return this.modulesLoader;
/*      */   }
/*      */   
/*      */   @Internal
/*      */   public void setModulesLoader(@Nullable IModulesLoader modulesLoader) {
/* 2433 */     this.modulesLoader = (modulesLoader != null) ? modulesLoader : (IModulesLoader)NoOpModulesLoader.getInstance();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Internal
/*      */   @NotNull
/*      */   public IDebugMetaLoader getDebugMetaLoader() {
/* 2444 */     return this.debugMetaLoader;
/*      */   }
/*      */   
/*      */   @Internal
/*      */   public void setDebugMetaLoader(@Nullable IDebugMetaLoader debugMetaLoader) {
/* 2449 */     this
/* 2450 */       .debugMetaLoader = (debugMetaLoader != null) ? debugMetaLoader : (IDebugMetaLoader)NoOpDebugMetaLoader.getInstance();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<GestureTargetLocator> getGestureTargetLocators() {
/* 2460 */     return this.gestureTargetLocators;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setGestureTargetLocators(@NotNull List<GestureTargetLocator> locators) {
/* 2470 */     this.gestureTargetLocators.clear();
/* 2471 */     this.gestureTargetLocators.addAll(locators);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public final List<ViewHierarchyExporter> getViewHierarchyExporters() {
/* 2482 */     return this.viewHierarchyExporters;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setViewHierarchyExporters(@NotNull List<ViewHierarchyExporter> exporters) {
/* 2491 */     this.viewHierarchyExporters.clear();
/* 2492 */     this.viewHierarchyExporters.addAll(exporters);
/*      */   }
/*      */   @NotNull
/*      */   public IThreadChecker getThreadChecker() {
/* 2496 */     return this.threadChecker;
/*      */   }
/*      */   
/*      */   public void setThreadChecker(@NotNull IThreadChecker threadChecker) {
/* 2500 */     this.threadChecker = threadChecker;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Internal
/*      */   @NotNull
/*      */   public CompositePerformanceCollector getCompositePerformanceCollector() {
/* 2510 */     return this.compositePerformanceCollector;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Internal
/*      */   public void setCompositePerformanceCollector(@NotNull CompositePerformanceCollector compositePerformanceCollector) {
/* 2521 */     this.compositePerformanceCollector = compositePerformanceCollector;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEnableTimeToFullDisplayTracing() {
/* 2530 */     return this.enableTimeToFullDisplayTracing;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEnableTimeToFullDisplayTracing(boolean enableTimeToFullDisplayTracing) {
/* 2539 */     this.enableTimeToFullDisplayTracing = enableTimeToFullDisplayTracing;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Internal
/*      */   @NotNull
/*      */   public FullyDisplayedReporter getFullyDisplayedReporter() {
/* 2549 */     return this.fullyDisplayedReporter;
/*      */   }
/*      */ 
/*      */   
/*      */   @Internal
/*      */   @TestOnly
/*      */   public void setFullyDisplayedReporter(@NotNull FullyDisplayedReporter fullyDisplayedReporter) {
/* 2556 */     this.fullyDisplayedReporter = fullyDisplayedReporter;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isTraceOptionsRequests() {
/* 2565 */     return this.traceOptionsRequests;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTraceOptionsRequests(boolean traceOptionsRequests) {
/* 2574 */     this.traceOptionsRequests = traceOptionsRequests;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEnabled() {
/* 2583 */     return this.enabled;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEnabled(boolean enabled) {
/* 2592 */     this.enabled = enabled;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEnablePrettySerializationOutput() {
/* 2601 */     return this.enablePrettySerializationOutput;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSendModules() {
/* 2610 */     return this.sendModules;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEnablePrettySerializationOutput(boolean enablePrettySerializationOutput) {
/* 2619 */     this.enablePrettySerializationOutput = enablePrettySerializationOutput;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEnableAppStartProfiling() {
/* 2630 */     return ((isProfilingEnabled() || isContinuousProfilingEnabled()) && this.enableAppStartProfiling);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEnableAppStartProfiling(boolean enableAppStartProfiling) {
/* 2640 */     this.enableAppStartProfiling = enableAppStartProfiling;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSendModules(boolean sendModules) {
/* 2649 */     this.sendModules = sendModules;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Experimental
/*      */   @Nullable
/*      */   public List<FilterString> getIgnoredSpanOrigins() {
/* 2660 */     return this.ignoredSpanOrigins;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Experimental
/*      */   public void addIgnoredSpanOrigin(String ignoredSpanOrigin) {
/* 2671 */     if (this.ignoredSpanOrigins == null) {
/* 2672 */       this.ignoredSpanOrigins = new ArrayList<>();
/*      */     }
/* 2674 */     this.ignoredSpanOrigins.add(new FilterString(ignoredSpanOrigin));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Experimental
/*      */   public void setIgnoredSpanOrigins(@Nullable List<String> ignoredSpanOrigins) {
/* 2685 */     if (ignoredSpanOrigins == null) {
/* 2686 */       this.ignoredSpanOrigins = null;
/*      */     } else {
/* 2688 */       List<FilterString> filtered = new ArrayList<>();
/* 2689 */       for (String origin : ignoredSpanOrigins) {
/* 2690 */         if (origin != null && !origin.isEmpty()) {
/* 2691 */           filtered.add(new FilterString(origin));
/*      */         }
/*      */       } 
/*      */       
/* 2695 */       this.ignoredSpanOrigins = filtered;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Experimental
/*      */   @Nullable
/*      */   public List<FilterString> getIgnoredCheckIns() {
/* 2706 */     return this.ignoredCheckIns;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Experimental
/*      */   public void addIgnoredCheckIn(String ignoredCheckIn) {
/* 2717 */     if (this.ignoredCheckIns == null) {
/* 2718 */       this.ignoredCheckIns = new ArrayList<>();
/*      */     }
/* 2720 */     this.ignoredCheckIns.add(new FilterString(ignoredCheckIn));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Experimental
/*      */   public void setIgnoredCheckIns(@Nullable List<String> ignoredCheckIns) {
/* 2730 */     if (ignoredCheckIns == null) {
/* 2731 */       this.ignoredCheckIns = null;
/*      */     } else {
/* 2733 */       List<FilterString> filteredIgnoredCheckIns = new ArrayList<>();
/* 2734 */       for (String slug : ignoredCheckIns) {
/* 2735 */         if (!slug.isEmpty()) {
/* 2736 */           filteredIgnoredCheckIns.add(new FilterString(slug));
/*      */         }
/*      */       } 
/*      */       
/* 2740 */       this.ignoredCheckIns = filteredIgnoredCheckIns;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public List<FilterString> getIgnoredTransactions() {
/* 2751 */     return this.ignoredTransactions;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Experimental
/*      */   public void addIgnoredTransaction(String ignoredTransaction) {
/* 2762 */     if (this.ignoredTransactions == null) {
/* 2763 */       this.ignoredTransactions = new ArrayList<>();
/*      */     }
/* 2765 */     this.ignoredTransactions.add(new FilterString(ignoredTransaction));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Experimental
/*      */   public void setIgnoredTransactions(@Nullable List<String> ignoredTransactions) {
/* 2776 */     if (ignoredTransactions == null) {
/* 2777 */       this.ignoredTransactions = null;
/*      */     } else {
/* 2779 */       List<FilterString> filtered = new ArrayList<>();
/* 2780 */       for (String transactionName : ignoredTransactions) {
/* 2781 */         if (transactionName != null && !transactionName.isEmpty()) {
/* 2782 */           filtered.add(new FilterString(transactionName));
/*      */         }
/*      */       } 
/*      */       
/* 2786 */       this.ignoredTransactions = filtered;
/*      */     } 
/*      */   }
/*      */   
/*      */   @Internal
/*      */   @NotNull
/*      */   public SentryDateProvider getDateProvider() {
/* 2793 */     return (SentryDateProvider)this.dateProvider.getValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Internal
/*      */   public void setDateProvider(@NotNull SentryDateProvider dateProvider) {
/* 2804 */     this.dateProvider.setValue(dateProvider);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Internal
/*      */   public void addPerformanceCollector(@NotNull IPerformanceCollector collector) {
/* 2814 */     this.performanceCollectors.add(collector);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Internal
/*      */   @NotNull
/*      */   public List<IPerformanceCollector> getPerformanceCollectors() {
/* 2824 */     return this.performanceCollectors;
/*      */   }
/*      */   
/*      */   @NotNull
/*      */   public IConnectionStatusProvider getConnectionStatusProvider() {
/* 2829 */     return this.connectionStatusProvider;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setConnectionStatusProvider(@NotNull IConnectionStatusProvider connectionStatusProvider) {
/* 2834 */     this.connectionStatusProvider = connectionStatusProvider;
/*      */   }
/*      */   
/*      */   @Internal
/*      */   @NotNull
/*      */   public IBackpressureMonitor getBackpressureMonitor() {
/* 2840 */     return this.backpressureMonitor;
/*      */   }
/*      */   
/*      */   @Internal
/*      */   public void setBackpressureMonitor(@NotNull IBackpressureMonitor backpressureMonitor) {
/* 2845 */     this.backpressureMonitor = backpressureMonitor;
/*      */   }
/*      */   
/*      */   @Experimental
/*      */   public void setEnableBackpressureHandling(boolean enableBackpressureHandling) {
/* 2850 */     this.enableBackpressureHandling = enableBackpressureHandling;
/*      */   }
/*      */   
/*      */   @Internal
/*      */   @NotNull
/*      */   public IVersionDetector getVersionDetector() {
/* 2856 */     return this.versionDetector;
/*      */   }
/*      */   
/*      */   @Internal
/*      */   public void setVersionDetector(@NotNull IVersionDetector versionDetector) {
/* 2861 */     this.versionDetector = versionDetector;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Internal
/*      */   public int getProfilingTracesHz() {
/* 2871 */     return this.profilingTracesHz;
/*      */   }
/*      */ 
/*      */   
/*      */   @Internal
/*      */   public void setProfilingTracesHz(int profilingTracesHz) {
/* 2877 */     this.profilingTracesHz = profilingTracesHz;
/*      */   }
/*      */   
/*      */   @Experimental
/*      */   public boolean isEnableBackpressureHandling() {
/* 2882 */     return this.enableBackpressureHandling;
/*      */   }
/*      */   
/*      */   @Internal
/*      */   public long getSessionFlushTimeoutMillis() {
/* 2887 */     return this.sessionFlushTimeoutMillis;
/*      */   }
/*      */   
/*      */   @Internal
/*      */   public void setSessionFlushTimeoutMillis(long sessionFlushTimeoutMillis) {
/* 2892 */     this.sessionFlushTimeoutMillis = sessionFlushTimeoutMillis;
/*      */   }
/*      */   
/*      */   @Internal
/*      */   @Nullable
/*      */   public BeforeEnvelopeCallback getBeforeEnvelopeCallback() {
/* 2898 */     return this.beforeEnvelopeCallback;
/*      */   }
/*      */ 
/*      */   
/*      */   @Internal
/*      */   public void setBeforeEnvelopeCallback(@Nullable BeforeEnvelopeCallback beforeEnvelopeCallback) {
/* 2904 */     this.beforeEnvelopeCallback = beforeEnvelopeCallback;
/*      */   }
/*      */   
/*      */   @Experimental
/*      */   @Nullable
/*      */   public String getSpotlightConnectionUrl() {
/* 2910 */     return this.spotlightConnectionUrl;
/*      */   }
/*      */   
/*      */   @Experimental
/*      */   public void setSpotlightConnectionUrl(@Nullable String spotlightConnectionUrl) {
/* 2915 */     this.spotlightConnectionUrl = spotlightConnectionUrl;
/*      */   }
/*      */   
/*      */   @Experimental
/*      */   public boolean isEnableSpotlight() {
/* 2920 */     return this.enableSpotlight;
/*      */   }
/*      */   
/*      */   @Experimental
/*      */   public void setEnableSpotlight(boolean enableSpotlight) {
/* 2925 */     this.enableSpotlight = enableSpotlight;
/*      */   }
/*      */   
/*      */   public boolean isEnableScopePersistence() {
/* 2929 */     return this.enableScopePersistence;
/*      */   }
/*      */   
/*      */   public void setEnableScopePersistence(boolean enableScopePersistence) {
/* 2933 */     this.enableScopePersistence = enableScopePersistence;
/*      */   }
/*      */   @Nullable
/*      */   public Cron getCron() {
/* 2937 */     return this.cron;
/*      */   }
/*      */   
/*      */   @Experimental
/*      */   public void setCron(@Nullable Cron cron) {
/* 2942 */     this.cron = cron;
/*      */   }
/*      */   
/*      */   @NotNull
/*      */   public ExperimentalOptions getExperimental() {
/* 2947 */     return this.experimental;
/*      */   }
/*      */   @NotNull
/*      */   public ReplayController getReplayController() {
/* 2951 */     return this.replayController;
/*      */   }
/*      */   
/*      */   public void setReplayController(@Nullable ReplayController replayController) {
/* 2955 */     this
/* 2956 */       .replayController = (replayController != null) ? replayController : NoOpReplayController.getInstance();
/*      */   }
/*      */   @Experimental
/*      */   @NotNull
/*      */   public IDistributionApi getDistributionController() {
/* 2961 */     return this.distributionController;
/*      */   }
/*      */   
/*      */   @Experimental
/*      */   public void setDistributionController(@Nullable IDistributionApi distributionController) {
/* 2966 */     this
/* 2967 */       .distributionController = (distributionController != null) ? distributionController : NoOpDistributionApi.getInstance();
/*      */   }
/*      */   
/*      */   @Experimental
/*      */   public boolean isEnableScreenTracking() {
/* 2972 */     return this.enableScreenTracking;
/*      */   }
/*      */   
/*      */   @Experimental
/*      */   public void setEnableScreenTracking(boolean enableScreenTracking) {
/* 2977 */     this.enableScreenTracking = enableScreenTracking;
/*      */   }
/*      */   
/*      */   public void setDefaultScopeType(@NotNull ScopeType scopeType) {
/* 2981 */     this.defaultScopeType = scopeType;
/*      */   }
/*      */   @NotNull
/*      */   public ScopeType getDefaultScopeType() {
/* 2985 */     return this.defaultScopeType;
/*      */   }
/*      */   
/*      */   @Internal
/*      */   public void setInitPriority(@NotNull InitPriority initPriority) {
/* 2990 */     this.initPriority = initPriority;
/*      */   }
/*      */   @Internal
/*      */   @NotNull
/*      */   public InitPriority getInitPriority() {
/* 2995 */     return this.initPriority;
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
/*      */   public void setForceInit(boolean forceInit) {
/* 3008 */     this.forceInit = forceInit;
/*      */   }
/*      */   
/*      */   public boolean isForceInit() {
/* 3012 */     return this.forceInit;
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
/*      */   public void setGlobalHubMode(@Nullable Boolean globalHubMode) {
/* 3029 */     this.globalHubMode = globalHubMode;
/*      */   }
/*      */   @Nullable
/*      */   public Boolean isGlobalHubMode() {
/* 3033 */     return this.globalHubMode;
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
/*      */   public void setOpenTelemetryMode(@NotNull SentryOpenTelemetryMode openTelemetryMode) {
/* 3048 */     this.openTelemetryMode = openTelemetryMode;
/*      */   }
/*      */   @NotNull
/*      */   public SentryOpenTelemetryMode getOpenTelemetryMode() {
/* 3052 */     return this.openTelemetryMode;
/*      */   }
/*      */   
/*      */   @NotNull
/*      */   public SentryReplayOptions getSessionReplay() {
/* 3057 */     return this.sessionReplay;
/*      */   }
/*      */   
/*      */   public void setSessionReplay(@NotNull SentryReplayOptions sessionReplayOptions) {
/* 3061 */     this.sessionReplay = sessionReplayOptions;
/*      */   }
/*      */   @NotNull
/*      */   public SentryFeedbackOptions getFeedbackOptions() {
/* 3065 */     return this.feedbackOptions;
/*      */   }
/*      */   
/*      */   public void setFeedbackOptions(@NotNull SentryFeedbackOptions feedbackOptions) {
/* 3069 */     this.feedbackOptions = feedbackOptions;
/*      */   }
/*      */   
/*      */   @Experimental
/*      */   public void setCaptureOpenTelemetryEvents(boolean captureOpenTelemetryEvents) {
/* 3074 */     this.captureOpenTelemetryEvents = captureOpenTelemetryEvents;
/*      */   }
/*      */   
/*      */   @Experimental
/*      */   public boolean isCaptureOpenTelemetryEvents() {
/* 3079 */     return this.captureOpenTelemetryEvents;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public ISocketTagger getSocketTagger() {
/* 3088 */     return this.socketTagger;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSocketTagger(@Nullable ISocketTagger socketTagger) {
/* 3097 */     this.socketTagger = (socketTagger != null) ? socketTagger : NoOpSocketTagger.getInstance();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Internal
/*      */   @NotNull
/*      */   public IRuntimeManager getRuntimeManager() {
/* 3107 */     return this.runtimeManager;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Internal
/*      */   public void setRuntimeManager(@NotNull IRuntimeManager runtimeManager) {
/* 3117 */     this.runtimeManager = runtimeManager;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void loadLazyFields() {
/* 3125 */     getSerializer();
/* 3126 */     retrieveParsedDsn();
/* 3127 */     getEnvelopeReader();
/* 3128 */     getDateProvider();
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
/*      */   @NotNull
/*      */   public static SentryOptions empty() {
/* 3284 */     return new SentryOptions(true);
/*      */   }
/*      */ 
/*      */   
/*      */   public SentryOptions() {
/* 3289 */     this(false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private SentryOptions(boolean empty) {
/* 3298 */     SdkVersion sdkVersion = createSdkVersion();
/* 3299 */     this.experimental = new ExperimentalOptions(empty, sdkVersion);
/* 3300 */     this.sessionReplay = new SentryReplayOptions(empty, sdkVersion);
/* 3301 */     this.feedbackOptions = new SentryFeedbackOptions((associatedEventId, configurator) -> this.logger.log(SentryLevel.WARNING, "showDialog() can only be called in Android.", new Object[0]));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3306 */     if (!empty) {
/* 3307 */       setSpanFactory(SpanFactoryFactory.create(new LoadClass(), NoOpLogger.getInstance()));
/*      */ 
/*      */       
/* 3310 */       this.executorService = new SentryExecutorService(this);
/* 3311 */       this.executorService.prewarm();
/*      */ 
/*      */ 
/*      */       
/* 3315 */       this.integrations.add(new UncaughtExceptionHandlerIntegration());
/*      */       
/* 3317 */       this.integrations.add(new ShutdownHookIntegration());
/* 3318 */       this.integrations.add(new SpotlightIntegration());
/*      */       
/* 3320 */       this.eventProcessors.add(new MainEventProcessor(this));
/* 3321 */       this.eventProcessors.add(new DuplicateEventDetectionEventProcessor(this));
/*      */       
/* 3323 */       if (Platform.isJvm()) {
/* 3324 */         this.eventProcessors.add(new SentryRuntimeEventProcessor());
/*      */       }
/*      */       
/* 3327 */       setSentryClientName("sentry.java/8.29.0");
/* 3328 */       setSdkVersion(sdkVersion);
/* 3329 */       addPackageInfo();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void merge(@NotNull ExternalOptions options) {
/* 3340 */     if (options.getDsn() != null) {
/* 3341 */       setDsn(options.getDsn());
/*      */     }
/* 3343 */     if (options.getEnvironment() != null) {
/* 3344 */       setEnvironment(options.getEnvironment());
/*      */     }
/* 3346 */     if (options.getRelease() != null) {
/* 3347 */       setRelease(options.getRelease());
/*      */     }
/* 3349 */     if (options.getDist() != null) {
/* 3350 */       setDist(options.getDist());
/*      */     }
/* 3352 */     if (options.getServerName() != null) {
/* 3353 */       setServerName(options.getServerName());
/*      */     }
/* 3355 */     if (options.getProxy() != null) {
/* 3356 */       setProxy(options.getProxy());
/*      */     }
/* 3358 */     if (options.getEnableUncaughtExceptionHandler() != null) {
/* 3359 */       setEnableUncaughtExceptionHandler(options.getEnableUncaughtExceptionHandler().booleanValue());
/*      */     }
/* 3361 */     if (options.getPrintUncaughtStackTrace() != null) {
/* 3362 */       setPrintUncaughtStackTrace(options.getPrintUncaughtStackTrace().booleanValue());
/*      */     }
/* 3364 */     if (options.getTracesSampleRate() != null) {
/* 3365 */       setTracesSampleRate(options.getTracesSampleRate());
/*      */     }
/* 3367 */     if (options.getProfilesSampleRate() != null) {
/* 3368 */       setProfilesSampleRate(options.getProfilesSampleRate());
/*      */     }
/* 3370 */     if (options.getDebug() != null) {
/* 3371 */       setDebug(options.getDebug().booleanValue());
/*      */     }
/* 3373 */     if (options.getEnableDeduplication() != null) {
/* 3374 */       setEnableDeduplication(options.getEnableDeduplication().booleanValue());
/*      */     }
/* 3376 */     if (options.getSendClientReports() != null) {
/* 3377 */       setSendClientReports(options.getSendClientReports().booleanValue());
/*      */     }
/* 3379 */     if (options.isForceInit() != null) {
/* 3380 */       setForceInit(options.isForceInit().booleanValue());
/*      */     }
/* 3382 */     Map<String, String> tags = new HashMap<>(options.getTags());
/* 3383 */     for (Map.Entry<String, String> tag : tags.entrySet()) {
/* 3384 */       this.tags.put(tag.getKey(), tag.getValue());
/*      */     }
/* 3386 */     List<String> inAppIncludes = new ArrayList<>(options.getInAppIncludes());
/* 3387 */     for (String inAppInclude : inAppIncludes) {
/* 3388 */       addInAppInclude(inAppInclude);
/*      */     }
/* 3390 */     List<String> inAppExcludes = new ArrayList<>(options.getInAppExcludes());
/* 3391 */     for (String inAppExclude : inAppExcludes) {
/* 3392 */       addInAppExclude(inAppExclude);
/*      */     }
/*      */     
/* 3395 */     for (Class<? extends Throwable> exceptionType : (Iterable<Class<? extends Throwable>>)new HashSet(options.getIgnoredExceptionsForType())) {
/* 3396 */       addIgnoredExceptionForType(exceptionType);
/*      */     }
/* 3398 */     if (options.getTracePropagationTargets() != null) {
/*      */       
/* 3400 */       List<String> tracePropagationTargets = new ArrayList<>(options.getTracePropagationTargets());
/* 3401 */       setTracePropagationTargets(tracePropagationTargets);
/*      */     } 
/* 3403 */     List<String> contextTags = new ArrayList<>(options.getContextTags());
/* 3404 */     for (String contextTag : contextTags) {
/* 3405 */       addContextTag(contextTag);
/*      */     }
/* 3407 */     if (options.getProguardUuid() != null) {
/* 3408 */       setProguardUuid(options.getProguardUuid());
/*      */     }
/* 3410 */     if (options.getIdleTimeout() != null) {
/* 3411 */       setIdleTimeout(options.getIdleTimeout());
/*      */     }
/* 3413 */     for (String bundleId : options.getBundleIds()) {
/* 3414 */       addBundleId(bundleId);
/*      */     }
/*      */     
/* 3417 */     if (options.isEnabled() != null) {
/* 3418 */       setEnabled(options.isEnabled().booleanValue());
/*      */     }
/* 3420 */     if (options.isEnablePrettySerializationOutput() != null) {
/* 3421 */       setEnablePrettySerializationOutput(options.isEnablePrettySerializationOutput().booleanValue());
/*      */     }
/*      */     
/* 3424 */     if (options.isSendModules() != null) {
/* 3425 */       setSendModules(options.isSendModules().booleanValue());
/*      */     }
/* 3427 */     if (options.getIgnoredCheckIns() != null) {
/* 3428 */       List<String> ignoredCheckIns = new ArrayList<>(options.getIgnoredCheckIns());
/* 3429 */       setIgnoredCheckIns(ignoredCheckIns);
/*      */     } 
/* 3431 */     if (options.getIgnoredTransactions() != null) {
/* 3432 */       List<String> ignoredTransactions = new ArrayList<>(options.getIgnoredTransactions());
/* 3433 */       setIgnoredTransactions(ignoredTransactions);
/*      */     } 
/* 3435 */     if (options.getIgnoredErrors() != null) {
/* 3436 */       List<String> ignoredExceptions = new ArrayList<>(options.getIgnoredErrors());
/* 3437 */       setIgnoredErrors(ignoredExceptions);
/*      */     } 
/* 3439 */     if (options.isEnableBackpressureHandling() != null) {
/* 3440 */       setEnableBackpressureHandling(options.isEnableBackpressureHandling().booleanValue());
/*      */     }
/* 3442 */     if (options.getMaxRequestBodySize() != null) {
/* 3443 */       setMaxRequestBodySize(options.getMaxRequestBodySize());
/*      */     }
/* 3445 */     if (options.isSendDefaultPii() != null) {
/* 3446 */       setSendDefaultPii(options.isSendDefaultPii().booleanValue());
/*      */     }
/* 3448 */     if (options.isCaptureOpenTelemetryEvents() != null) {
/* 3449 */       setCaptureOpenTelemetryEvents(options.isCaptureOpenTelemetryEvents().booleanValue());
/*      */     }
/* 3451 */     if (options.isEnableSpotlight() != null) {
/* 3452 */       setEnableSpotlight(options.isEnableSpotlight().booleanValue());
/*      */     }
/*      */     
/* 3455 */     if (options.getSpotlightConnectionUrl() != null) {
/* 3456 */       setSpotlightConnectionUrl(options.getSpotlightConnectionUrl());
/*      */     }
/*      */     
/* 3459 */     if (options.isGlobalHubMode() != null) {
/* 3460 */       setGlobalHubMode(options.isGlobalHubMode());
/*      */     }
/*      */     
/* 3463 */     if (options.getCron() != null) {
/* 3464 */       if (getCron() == null) {
/* 3465 */         setCron(options.getCron());
/*      */       } else {
/* 3467 */         if (options.getCron().getDefaultCheckinMargin() != null) {
/* 3468 */           getCron().setDefaultCheckinMargin(options.getCron().getDefaultCheckinMargin());
/*      */         }
/* 3470 */         if (options.getCron().getDefaultMaxRuntime() != null) {
/* 3471 */           getCron().setDefaultMaxRuntime(options.getCron().getDefaultMaxRuntime());
/*      */         }
/* 3473 */         if (options.getCron().getDefaultTimezone() != null) {
/* 3474 */           getCron().setDefaultTimezone(options.getCron().getDefaultTimezone());
/*      */         }
/* 3476 */         if (options.getCron().getDefaultFailureIssueThreshold() != null) {
/* 3477 */           getCron()
/* 3478 */             .setDefaultFailureIssueThreshold(options.getCron().getDefaultFailureIssueThreshold());
/*      */         }
/* 3480 */         if (options.getCron().getDefaultRecoveryThreshold() != null) {
/* 3481 */           getCron().setDefaultRecoveryThreshold(options.getCron().getDefaultRecoveryThreshold());
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/* 3486 */     if (options.isEnableLogs() != null) {
/* 3487 */       getLogs().setEnabled(options.isEnableLogs().booleanValue());
/*      */     }
/*      */     
/* 3490 */     if (options.getProfileSessionSampleRate() != null) {
/* 3491 */       setProfileSessionSampleRate(options.getProfileSessionSampleRate());
/*      */     }
/*      */     
/* 3494 */     if (options.getProfilingTracesDirPath() != null) {
/* 3495 */       setProfilingTracesDirPath(options.getProfilingTracesDirPath());
/*      */     }
/*      */     
/* 3498 */     if (options.getProfileLifecycle() != null)
/* 3499 */       setProfileLifecycle(options.getProfileLifecycle()); 
/*      */   }
/*      */   
/*      */   @NotNull
/*      */   private SdkVersion createSdkVersion() {
/* 3504 */     String version = "8.29.0";
/* 3505 */     SdkVersion sdkVersion = new SdkVersion("sentry.java", "8.29.0");
/*      */     
/* 3507 */     sdkVersion.setVersion("8.29.0");
/*      */     
/* 3509 */     return sdkVersion;
/*      */   }
/*      */   
/*      */   private void addPackageInfo() {
/* 3513 */     SentryIntegrationPackageStorage.getInstance()
/* 3514 */       .addPackage("maven:io.sentry:sentry", "8.29.0");
/*      */   }
/*      */   @Internal
/*      */   @NotNull
/*      */   public ISpanFactory getSpanFactory() {
/* 3519 */     return this.spanFactory;
/*      */   }
/*      */   
/*      */   @Internal
/*      */   public void setSpanFactory(@NotNull ISpanFactory spanFactory) {
/* 3524 */     this.spanFactory = spanFactory;
/*      */   }
/*      */   @Experimental
/*      */   @NotNull
/*      */   public Logs getLogs() {
/* 3529 */     return this.logs;
/*      */   }
/*      */   
/*      */   @Experimental
/*      */   public void setLogs(@NotNull Logs logs) {
/* 3534 */     this.logs = logs;
/*      */   }
/*      */   public static final class Proxy { @Nullable
/*      */     private String host; @Nullable
/*      */     private String port; @Nullable
/*      */     private String user; @Nullable
/*      */     private String pass;
/*      */     @Nullable
/*      */     private java.net.Proxy.Type type;
/*      */     
/*      */     public Proxy() {
/* 3545 */       this(null, null, null, null, null);
/*      */     }
/*      */     
/*      */     public Proxy(@Nullable String host, @Nullable String port) {
/* 3549 */       this(host, port, null, null, null);
/*      */     }
/*      */     
/*      */     public Proxy(@Nullable String host, @Nullable String port, @Nullable java.net.Proxy.Type type) {
/* 3553 */       this(host, port, type, null, null);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Proxy(@Nullable String host, @Nullable String port, @Nullable String user, @Nullable String pass) {
/* 3561 */       this(host, port, null, user, pass);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Proxy(@Nullable String host, @Nullable String port, @Nullable java.net.Proxy.Type type, @Nullable String user, @Nullable String pass) {
/* 3570 */       this.host = host;
/* 3571 */       this.port = port;
/* 3572 */       this.type = type;
/* 3573 */       this.user = user;
/* 3574 */       this.pass = pass;
/*      */     }
/*      */     @Nullable
/*      */     public String getHost() {
/* 3578 */       return this.host;
/*      */     }
/*      */     
/*      */     public void setHost(@Nullable String host) {
/* 3582 */       this.host = host;
/*      */     }
/*      */     @Nullable
/*      */     public String getPort() {
/* 3586 */       return this.port;
/*      */     }
/*      */     
/*      */     public void setPort(@Nullable String port) {
/* 3590 */       this.port = port;
/*      */     }
/*      */     @Nullable
/*      */     public String getUser() {
/* 3594 */       return this.user;
/*      */     }
/*      */     
/*      */     public void setUser(@Nullable String user) {
/* 3598 */       this.user = user;
/*      */     }
/*      */     @Nullable
/*      */     public String getPass() {
/* 3602 */       return this.pass;
/*      */     }
/*      */     
/*      */     public void setPass(@Nullable String pass) {
/* 3606 */       this.pass = pass;
/*      */     }
/*      */     @Nullable
/*      */     public java.net.Proxy.Type getType() {
/* 3610 */       return this.type;
/*      */     }
/*      */     
/*      */     public void setType(@Nullable java.net.Proxy.Type type) {
/* 3614 */       this.type = type;
/*      */     } }
/*      */   public static final class Cron { @Nullable
/*      */     private Long defaultCheckinMargin; @Nullable
/*      */     private Long defaultMaxRuntime; @Nullable
/*      */     private String defaultTimezone; @Nullable
/*      */     private Long defaultFailureIssueThreshold;
/*      */     @Nullable
/*      */     private Long defaultRecoveryThreshold;
/*      */     
/*      */     @Nullable
/*      */     public Long getDefaultCheckinMargin() {
/* 3626 */       return this.defaultCheckinMargin;
/*      */     }
/*      */     
/*      */     public void setDefaultCheckinMargin(@Nullable Long defaultCheckinMargin) {
/* 3630 */       this.defaultCheckinMargin = defaultCheckinMargin;
/*      */     }
/*      */     @Nullable
/*      */     public Long getDefaultMaxRuntime() {
/* 3634 */       return this.defaultMaxRuntime;
/*      */     }
/*      */     
/*      */     public void setDefaultMaxRuntime(@Nullable Long defaultMaxRuntime) {
/* 3638 */       this.defaultMaxRuntime = defaultMaxRuntime;
/*      */     }
/*      */     @Nullable
/*      */     public String getDefaultTimezone() {
/* 3642 */       return this.defaultTimezone;
/*      */     }
/*      */     
/*      */     public void setDefaultTimezone(@Nullable String defaultTimezone) {
/* 3646 */       this.defaultTimezone = defaultTimezone;
/*      */     }
/*      */     @Nullable
/*      */     public Long getDefaultFailureIssueThreshold() {
/* 3650 */       return this.defaultFailureIssueThreshold;
/*      */     }
/*      */     
/*      */     public void setDefaultFailureIssueThreshold(@Nullable Long defaultFailureIssueThreshold) {
/* 3654 */       this.defaultFailureIssueThreshold = defaultFailureIssueThreshold;
/*      */     }
/*      */     @Nullable
/*      */     public Long getDefaultRecoveryThreshold() {
/* 3658 */       return this.defaultRecoveryThreshold;
/*      */     }
/*      */     
/*      */     public void setDefaultRecoveryThreshold(@Nullable Long defaultRecoveryThreshold) {
/* 3662 */       this.defaultRecoveryThreshold = defaultRecoveryThreshold;
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final class Logs
/*      */   {
/*      */     private boolean enable = false;
/*      */ 
/*      */     
/*      */     @Nullable
/*      */     private BeforeSendLogCallback beforeSend;
/*      */     
/*      */     @NotNull
/* 3677 */     private ILoggerBatchProcessorFactory loggerBatchProcessorFactory = (ILoggerBatchProcessorFactory)new DefaultLoggerBatchProcessorFactory();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isEnabled() {
/* 3686 */       return this.enable;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setEnabled(boolean enableLogs) {
/* 3695 */       this.enable = enableLogs;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Nullable
/*      */     public BeforeSendLogCallback getBeforeSend() {
/* 3704 */       return this.beforeSend;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setBeforeSend(@Nullable BeforeSendLogCallback beforeSendLog) {
/* 3713 */       this.beforeSend = beforeSendLog;
/*      */     }
/*      */     @Internal
/*      */     @NotNull
/*      */     public ILoggerBatchProcessorFactory getLoggerBatchProcessorFactory() {
/* 3718 */       return this.loggerBatchProcessorFactory;
/*      */     }
/*      */ 
/*      */     
/*      */     @Internal
/*      */     public void setLoggerBatchProcessorFactory(@NotNull ILoggerBatchProcessorFactory loggerBatchProcessorFactory) {
/* 3724 */       this.loggerBatchProcessorFactory = loggerBatchProcessorFactory;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static interface BeforeSendLogCallback
/*      */     {
/*      */       @Nullable
/*      */       SentryLogEvent execute(@NotNull SentryLogEvent param2SentryLogEvent);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Experimental
/*      */   @NotNull
/*      */   public DistributionOptions getDistribution() {
/* 3743 */     return this.distribution;
/*      */   }
/*      */   
/*      */   @Experimental
/*      */   public void setDistribution(@NotNull DistributionOptions distribution) {
/* 3748 */     this.distribution = (distribution != null) ? distribution : new DistributionOptions();
/*      */   }
/*      */   
/*      */   public enum RequestSize {
/* 3752 */     NONE,
/* 3753 */     SMALL,
/* 3754 */     MEDIUM,
/* 3755 */     ALWAYS;
/*      */   }
/*      */   
/*      */   public static interface BeforeSendCallback {
/*      */     @Nullable
/*      */     SentryEvent execute(@NotNull SentryEvent param1SentryEvent, @NotNull Hint param1Hint);
/*      */   }
/*      */   
/*      */   public static interface BeforeSendTransactionCallback {
/*      */     @Nullable
/*      */     SentryTransaction execute(@NotNull SentryTransaction param1SentryTransaction, @NotNull Hint param1Hint);
/*      */   }
/*      */   
/*      */   public static interface BeforeSendReplayCallback {
/*      */     @Nullable
/*      */     SentryReplayEvent execute(@NotNull SentryReplayEvent param1SentryReplayEvent, @NotNull Hint param1Hint);
/*      */   }
/*      */   
/*      */   public static interface BeforeBreadcrumbCallback {
/*      */     @Nullable
/*      */     Breadcrumb execute(@NotNull Breadcrumb param1Breadcrumb, @NotNull Hint param1Hint);
/*      */   }
/*      */   
/*      */   public static interface OnDiscardCallback {
/*      */     void execute(@NotNull DiscardReason param1DiscardReason, @NotNull DataCategory param1DataCategory, @NotNull Long param1Long);
/*      */   }
/*      */   
/*      */   public static interface TracesSamplerCallback {
/*      */     @Nullable
/*      */     Double sample(@NotNull SamplingContext param1SamplingContext);
/*      */   }
/*      */   
/*      */   public static interface OnOversizedEventCallback {
/*      */     @NotNull
/*      */     SentryEvent execute(@NotNull SentryEvent param1SentryEvent, @NotNull Hint param1Hint);
/*      */   }
/*      */   
/*      */   public static interface ProfilesSamplerCallback {
/*      */     @Nullable
/*      */     Double sample(@NotNull SamplingContext param1SamplingContext);
/*      */   }
/*      */   
/*      */   @Internal
/*      */   public static interface BeforeEnvelopeCallback {
/*      */     void execute(@NotNull SentryEnvelope param1SentryEnvelope, @Nullable Hint param1Hint);
/*      */   }
/*      */   
/*      */   @Experimental
/*      */   public static interface BeforeEmitMetricCallback {
/*      */     boolean execute(@NotNull String param1String, @Nullable Map<String, String> param1Map);
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\SentryOptions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */