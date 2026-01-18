/*      */ package io.sentry;
/*      */ 
/*      */ import io.sentry.backpressure.BackpressureMonitor;
/*      */ import io.sentry.backpressure.IBackpressureMonitor;
/*      */ import io.sentry.cache.EnvelopeCache;
/*      */ import io.sentry.cache.IEnvelopeCache;
/*      */ import io.sentry.cache.PersistingScopeObserver;
/*      */ import io.sentry.config.PropertiesProviderFactory;
/*      */ import io.sentry.internal.debugmeta.IDebugMetaLoader;
/*      */ import io.sentry.internal.debugmeta.ResourcesDebugMetaLoader;
/*      */ import io.sentry.internal.modules.CompositeModulesLoader;
/*      */ import io.sentry.internal.modules.IModulesLoader;
/*      */ import io.sentry.internal.modules.ManifestModulesLoader;
/*      */ import io.sentry.internal.modules.NoOpModulesLoader;
/*      */ import io.sentry.internal.modules.ResourcesModulesLoader;
/*      */ import io.sentry.logger.ILoggerApi;
/*      */ import io.sentry.opentelemetry.OpenTelemetryUtil;
/*      */ import io.sentry.protocol.Feedback;
/*      */ import io.sentry.protocol.SentryId;
/*      */ import io.sentry.protocol.User;
/*      */ import io.sentry.util.AutoClosableReentrantLock;
/*      */ import io.sentry.util.DebugMetaPropertiesApplier;
/*      */ import io.sentry.util.FileUtils;
/*      */ import io.sentry.util.InitUtil;
/*      */ import io.sentry.util.LoadClass;
/*      */ import io.sentry.util.Platform;
/*      */ import io.sentry.util.SentryRandom;
/*      */ import io.sentry.util.thread.IThreadChecker;
/*      */ import io.sentry.util.thread.ThreadChecker;
/*      */ import java.io.BufferedWriter;
/*      */ import java.io.File;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.io.OutputStreamWriter;
/*      */ import java.io.Writer;
/*      */ import java.lang.reflect.InvocationTargetException;
/*      */ import java.nio.charset.Charset;
/*      */ import java.util.Arrays;
/*      */ import java.util.List;
/*      */ import java.util.Properties;
/*      */ import java.util.concurrent.RejectedExecutionException;
/*      */ import java.util.concurrent.TimeUnit;
/*      */ import org.jetbrains.annotations.ApiStatus.Experimental;
/*      */ import org.jetbrains.annotations.ApiStatus.Internal;
/*      */ import org.jetbrains.annotations.NotNull;
/*      */ import org.jetbrains.annotations.Nullable;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class Sentry
/*      */ {
/*      */   @NotNull
/*   55 */   private static volatile IScopesStorage scopesStorage = NoOpScopesStorage.getInstance();
/*      */   
/*      */   @NotNull
/*   58 */   private static volatile IScopes rootScopes = NoOpScopes.getInstance();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*   67 */   private static final IScope globalScope = new Scope(SentryOptions.empty());
/*      */ 
/*      */   
/*      */   private static final boolean GLOBAL_HUB_DEFAULT_MODE = false;
/*      */ 
/*      */   
/*      */   private static volatile boolean globalHubMode = false;
/*      */ 
/*      */   
/*      */   @Internal
/*      */   @NotNull
/*      */   public static final String APP_START_PROFILING_CONFIG_FILE_NAME = "app_start_profiling_config";
/*      */   
/*   80 */   private static final Charset UTF_8 = Charset.forName("UTF-8");
/*      */ 
/*      */   
/*   83 */   private static final long classCreationTimestamp = System.currentTimeMillis();
/*      */   
/*   85 */   private static final AutoClosableReentrantLock lock = new AutoClosableReentrantLock();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @Internal
/*      */   @NotNull
/*      */   public static IHub getCurrentHub() {
/*   97 */     return new HubScopesWrapper(getCurrentScopes());
/*      */   }
/*      */   
/*      */   @Internal
/*      */   @NotNull
/*      */   public static IScopes getCurrentScopes() {
/*  103 */     return getCurrentScopes(true);
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
/*      */   public static IScopes getCurrentScopes(boolean ensureForked) {
/*  116 */     if (globalHubMode) {
/*  117 */       return rootScopes;
/*      */     }
/*  119 */     IScopes scopes = getScopesStorage().get();
/*  120 */     if (scopes == null || scopes.isNoOp()) {
/*  121 */       if (!ensureForked) {
/*  122 */         return NoOpScopes.getInstance();
/*      */       }
/*  124 */       scopes = rootScopes.forkedScopes("getCurrentScopes");
/*  125 */       getScopesStorage().set(scopes);
/*      */     } 
/*      */     
/*  128 */     return scopes;
/*      */   }
/*      */   @NotNull
/*      */   private static IScopesStorage getScopesStorage() {
/*  132 */     return scopesStorage;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Internal
/*      */   @NotNull
/*      */   public static IScopes forkedRootScopes(@NotNull String creator) {
/*  142 */     if (globalHubMode) {
/*  143 */       return rootScopes;
/*      */     }
/*  145 */     return rootScopes.forkedScopes(creator);
/*      */   }
/*      */   @NotNull
/*      */   public static IScopes forkedScopes(@NotNull String creator) {
/*  149 */     return getCurrentScopes().forkedScopes(creator);
/*      */   }
/*      */   @NotNull
/*      */   public static IScopes forkedCurrentScope(@NotNull String creator) {
/*  153 */     return getCurrentScopes().forkedCurrentScope(creator);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @Internal
/*      */   @NotNull
/*      */   public static ISentryLifecycleToken setCurrentHub(@NotNull IHub hub) {
/*  163 */     return setCurrentScopes(hub);
/*      */   }
/*      */   @Internal
/*      */   @NotNull
/*      */   public static ISentryLifecycleToken setCurrentScopes(@NotNull IScopes scopes) {
/*  168 */     return getScopesStorage().set(scopes);
/*      */   }
/*      */   @NotNull
/*      */   public static IScope getGlobalScope() {
/*  172 */     return globalScope;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isEnabled() {
/*  181 */     return getCurrentScopes().isEnabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void init() {
/*  186 */     init(options -> options.setEnableExternalConfiguration(true), false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void init(@NotNull String dsn) {
/*  195 */     init(options -> options.setDsn(dsn));
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
/*      */   public static <T extends SentryOptions> void init(@NotNull OptionsContainer<T> clazz, @NotNull OptionsConfiguration<T> optionsConfiguration) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
/*  216 */     init(clazz, optionsConfiguration, false);
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
/*      */   public static <T extends SentryOptions> void init(@NotNull OptionsContainer<T> clazz, @NotNull OptionsConfiguration<T> optionsConfiguration, boolean globalHubMode) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
/*  239 */     SentryOptions sentryOptions = (SentryOptions)clazz.createInstance();
/*  240 */     applyOptionsConfiguration(optionsConfiguration, (T)sentryOptions);
/*  241 */     init(sentryOptions, globalHubMode);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void init(@NotNull OptionsConfiguration<SentryOptions> optionsConfiguration) {
/*  250 */     init(optionsConfiguration, false);
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
/*      */   public static void init(@NotNull OptionsConfiguration<SentryOptions> optionsConfiguration, boolean globalHubMode) {
/*  262 */     SentryOptions options = new SentryOptions();
/*  263 */     applyOptionsConfiguration(optionsConfiguration, options);
/*  264 */     init(options, globalHubMode);
/*      */   }
/*      */ 
/*      */   
/*      */   private static <T extends SentryOptions> void applyOptionsConfiguration(OptionsConfiguration<T> optionsConfiguration, T options) {
/*      */     try {
/*  270 */       optionsConfiguration.configure(options);
/*  271 */     } catch (Throwable t) {
/*  272 */       options
/*  273 */         .getLogger()
/*  274 */         .log(SentryLevel.ERROR, "Error in the 'OptionsConfiguration.configure' callback.", t);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Internal
/*      */   public static void init(@NotNull SentryOptions options) {
/*  285 */     init(options, false);
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
/*      */   private static void init(@NotNull SentryOptions options, boolean globalHubMode) {
/*  300 */     ISentryLifecycleToken ignored = lock.acquire(); 
/*  301 */     try { if (!options.getClass().getName().equals("io.sentry.android.core.SentryAndroidOptions") && 
/*  302 */         Platform.isAndroid()) {
/*  303 */         throw new IllegalArgumentException("You are running Android. Please, use SentryAndroid.init. " + options
/*      */             
/*  305 */             .getClass().getName());
/*      */       }
/*      */       
/*  308 */       if (!preInitConfigurations(options))
/*      */       
/*      */       { 
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
/*  409 */         if (ignored != null) ignored.close();  return; }  Boolean globalHubModeFromOptions = options.isGlobalHubMode(); boolean globalHubModeToUse = (globalHubModeFromOptions != null) ? globalHubModeFromOptions.booleanValue() : globalHubMode; options.getLogger().log(SentryLevel.INFO, "GlobalHubMode: '%s'", new Object[] { String.valueOf(globalHubModeToUse) }); Sentry.globalHubMode = globalHubModeToUse; initFatalLogger(options); boolean shouldInit = InitUtil.shouldInit(globalScope.getOptions(), options, isEnabled()); if (shouldInit) { if (isEnabled()) options.getLogger().log(SentryLevel.WARNING, "Sentry has been already initialized. Previous configuration will be overwritten.", new Object[0]);  IScopes scopes = getCurrentScopes(); scopes.close(true); globalScope.replaceOptions(options); IScope rootScope = new Scope(options); IScope rootIsolationScope = new Scope(options); rootScopes = new Scopes(rootScope, rootIsolationScope, globalScope, "Sentry.init"); initLogger(options); initForOpenTelemetryMaybe(options); getScopesStorage().set(rootScopes); initConfigurations(options); globalScope.bindClient(new SentryClient(options)); if (options.getExecutorService().isClosed()) { options.setExecutorService(new SentryExecutorService(options)); options.getExecutorService().prewarm(); }  try { options.getExecutorService().submit(() -> options.loadLazyFields()); } catch (RejectedExecutionException e) { options.getLogger().log(SentryLevel.DEBUG, "Failed to call the executor. Lazy fields will not be loaded. Did you call Sentry.close()?", e); }  movePreviousSession(options); for (Integration integration : options.getIntegrations()) { try { integration.register(ScopesAdapter.getInstance(), options); } catch (Throwable t) { options.getLogger().log(SentryLevel.WARNING, "Failed to register the integration " + integration.getClass().getName(), t); }  }  notifyOptionsObservers(options); finalizePreviousSession(options, ScopesAdapter.getInstance()); handleAppStartProfilingConfig(options, options.getExecutorService()); options.getLogger().log(SentryLevel.DEBUG, "Using openTelemetryMode %s", new Object[] { options.getOpenTelemetryMode() }); options.getLogger().log(SentryLevel.DEBUG, "Using span factory %s", new Object[] { options.getSpanFactory().getClass().getName() }); options.getLogger().log(SentryLevel.DEBUG, "Using scopes storage %s", new Object[] { scopesStorage.getClass().getName() }); } else { options.getLogger().log(SentryLevel.WARNING, "This init call has been ignored due to priority being too low.", new Object[0]); }  if (ignored != null) ignored.close();  } catch (Throwable throwable) { if (ignored != null)
/*      */         try { ignored.close(); }
/*      */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*      */           throw throwable; }
/*  413 */      } private static void initForOpenTelemetryMaybe(SentryOptions options) { OpenTelemetryUtil.updateOpenTelemetryModeIfAuto(options, new LoadClass());
/*  414 */     if (SentryOpenTelemetryMode.OFF == options.getOpenTelemetryMode()) {
/*  415 */       options.setSpanFactory(new DefaultSpanFactory());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  421 */     initScopesStorage(options);
/*  422 */     OpenTelemetryUtil.applyIgnoredSpanOrigins(options); }
/*      */ 
/*      */   
/*      */   private static void initLogger(@NotNull SentryOptions options) {
/*  426 */     if (options.isDebug() && options.getLogger() instanceof NoOpLogger) {
/*  427 */       options.setLogger(new SystemOutLogger());
/*      */     }
/*      */   }
/*      */   
/*      */   private static void initFatalLogger(@NotNull SentryOptions options) {
/*  432 */     if (options.getFatalLogger() instanceof NoOpLogger) {
/*  433 */       options.setFatalLogger(new SystemOutLogger());
/*      */     }
/*      */   }
/*      */   
/*      */   private static void initScopesStorage(SentryOptions options) {
/*  438 */     getScopesStorage().close();
/*  439 */     if (SentryOpenTelemetryMode.OFF == options.getOpenTelemetryMode()) {
/*  440 */       scopesStorage = new DefaultScopesStorage();
/*      */     } else {
/*  442 */       scopesStorage = ScopesStorageFactory.create(new LoadClass(), NoOpLogger.getInstance());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void handleAppStartProfilingConfig(@NotNull SentryOptions options, @NotNull ISentryExecutorService sentryExecutorService) {
/*      */     try {
/*  451 */       sentryExecutorService.submit(() -> {
/*      */             String cacheDirPath = options.getCacheDirPathWithoutDsn();
/*      */             
/*      */             if (cacheDirPath != null) {
/*      */               File appStartProfilingConfigFile = new File(cacheDirPath, "app_start_profiling_config");
/*      */               
/*      */               try {
/*      */                 FileUtils.deleteRecursively(appStartProfilingConfigFile);
/*      */                 
/*      */                 if (!options.isEnableAppStartProfiling() && !options.isStartProfilerOnAppStart()) {
/*      */                   return;
/*      */                 }
/*      */                 
/*      */                 if (!options.isStartProfilerOnAppStart() && !options.isTracingEnabled()) {
/*      */                   options.getLogger().log(SentryLevel.INFO, "Tracing is disabled and app start profiling will not start.", new Object[0]);
/*      */                   
/*      */                   return;
/*      */                 } 
/*      */                 
/*      */                 if (appStartProfilingConfigFile.createNewFile()) {
/*      */                   TracesSamplingDecision appStartSamplingDecision = options.isEnableAppStartProfiling() ? sampleAppStartProfiling(options) : new TracesSamplingDecision(Boolean.valueOf(false));
/*      */                   
/*      */                   SentryAppStartProfilingOptions appStartProfilingOptions = new SentryAppStartProfilingOptions(options, appStartSamplingDecision);
/*      */                   
/*      */                   OutputStream outputStream = new FileOutputStream(appStartProfilingConfigFile);
/*      */ 
/*      */                   
/*      */                   try { Writer writer = new BufferedWriter(new OutputStreamWriter(outputStream, UTF_8));
/*      */ 
/*      */                     
/*      */                     try { options.getSerializer().serialize(appStartProfilingOptions, writer);
/*      */                       writer.close(); }
/*  483 */                     catch (Throwable throwable) { try { writer.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  outputStream.close(); } catch (Throwable throwable) { try { outputStream.close(); } catch (Throwable throwable1)
/*      */                     { throwable.addSuppressed(throwable1); }
/*      */ 
/*      */                     
/*      */                     throw throwable; }
/*      */                 
/*      */                 } 
/*  490 */               } catch (Throwable e) {
/*      */                 
/*      */                 options.getLogger().log(SentryLevel.ERROR, "Unable to create app start profiling config file. ", e);
/*      */               }
/*      */             
/*      */             }
/*      */           
/*      */           });
/*  498 */     } catch (Throwable e) {
/*  499 */       options
/*  500 */         .getLogger()
/*  501 */         .log(SentryLevel.ERROR, "Failed to call the executor. App start profiling config will not be changed. Did you call Sentry.close()?", e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   private static TracesSamplingDecision sampleAppStartProfiling(@NotNull SentryOptions options) {
/*  510 */     TransactionContext appStartTransactionContext = new TransactionContext("app.launch", "profile");
/*  511 */     appStartTransactionContext.setForNextAppStart(true);
/*      */ 
/*      */     
/*  514 */     SamplingContext appStartSamplingContext = new SamplingContext(appStartTransactionContext, null, Double.valueOf(SentryRandom.current().nextDouble()), null);
/*  515 */     return options.getInternalTracesSampler().sample(appStartSamplingContext);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void movePreviousSession(@NotNull SentryOptions options) {
/*      */     try {
/*  522 */       options.getExecutorService().submit(new MovePreviousSession(options));
/*  523 */     } catch (Throwable e) {
/*  524 */       options.getLogger().log(SentryLevel.DEBUG, "Failed to move previous session.", e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void finalizePreviousSession(@NotNull SentryOptions options, @NotNull IScopes scopes) {
/*      */     try {
/*  535 */       options.getExecutorService().submit(new PreviousSessionFinalizer(options, scopes));
/*  536 */     } catch (Throwable e) {
/*  537 */       options.getLogger().log(SentryLevel.DEBUG, "Failed to finalize previous session.", e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void notifyOptionsObservers(@NotNull SentryOptions options) {
/*      */     try {
/*  547 */       options
/*  548 */         .getExecutorService()
/*  549 */         .submit(() -> {
/*      */             for (IOptionsObserver observer : options.getOptionsObservers()) {
/*      */               observer.setRelease(options.getRelease());
/*      */ 
/*      */               
/*      */               observer.setProguardUuid(options.getProguardUuid());
/*      */               
/*      */               observer.setSdkVersion(options.getSdkVersion());
/*      */               
/*      */               observer.setDist(options.getDist());
/*      */               
/*      */               observer.setEnvironment(options.getEnvironment());
/*      */               
/*      */               observer.setTags(options.getTags());
/*      */               
/*      */               observer.setReplayErrorSampleRate(options.getSessionReplay().getOnErrorSampleRate());
/*      */             } 
/*      */             
/*      */             PersistingScopeObserver scopeCache = options.findPersistingScopeObserver();
/*      */             
/*      */             if (scopeCache != null) {
/*      */               scopeCache.resetCache();
/*      */             }
/*      */           });
/*  573 */     } catch (Throwable e) {
/*  574 */       options.getLogger().log(SentryLevel.DEBUG, "Failed to notify options observers.", e);
/*      */     } 
/*      */   }
/*      */   
/*      */   private static boolean preInitConfigurations(@NotNull SentryOptions options) {
/*  579 */     if (options.isEnableExternalConfiguration()) {
/*  580 */       options.merge(ExternalOptions.from(PropertiesProviderFactory.create(), options.getLogger()));
/*      */     }
/*      */     
/*  583 */     String dsn = options.getDsn();
/*      */     
/*  585 */     if (!options.isEnabled() || (dsn != null && dsn.isEmpty())) {
/*  586 */       close();
/*  587 */       return false;
/*  588 */     }  if (dsn == null) {
/*  589 */       throw new IllegalArgumentException("DSN is required. Use empty string or set enabled to false in SentryOptions to disable SDK.");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  594 */     options.retrieveParsedDsn();
/*      */     
/*  596 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void initConfigurations(@NotNull SentryOptions options) {
/*  602 */     ILogger logger = options.getLogger();
/*  603 */     logger.log(SentryLevel.INFO, "Initializing SDK with DSN: '%s'", new Object[] { options.getDsn() });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  609 */     String outboxPath = options.getOutboxPath();
/*  610 */     if (outboxPath != null) {
/*  611 */       File outboxDir = new File(outboxPath);
/*  612 */       options.getRuntimeManager().runWithRelaxedPolicy(() -> Boolean.valueOf(outboxDir.mkdirs()));
/*      */     } else {
/*  614 */       logger.log(SentryLevel.INFO, "No outbox dir path is defined in options.", new Object[0]);
/*      */     } 
/*      */     
/*  617 */     String cacheDirPath = options.getCacheDirPath();
/*  618 */     if (cacheDirPath != null) {
/*  619 */       File cacheDir = new File(cacheDirPath);
/*  620 */       options.getRuntimeManager().runWithRelaxedPolicy(() -> Boolean.valueOf(cacheDir.mkdirs()));
/*  621 */       IEnvelopeCache envelopeCache = options.getEnvelopeDiskCache();
/*      */       
/*  623 */       if (envelopeCache instanceof io.sentry.transport.NoOpEnvelopeCache) {
/*  624 */         options.setEnvelopeDiskCache(EnvelopeCache.create(options));
/*      */       }
/*      */     } 
/*      */     
/*  628 */     String profilingTracesDirPath = options.getProfilingTracesDirPath();
/*  629 */     if ((options.isProfilingEnabled() || options.isContinuousProfilingEnabled()) && profilingTracesDirPath != null) {
/*      */ 
/*      */       
/*  632 */       File profilingTracesDir = new File(profilingTracesDirPath);
/*  633 */       options.getRuntimeManager().runWithRelaxedPolicy(() -> Boolean.valueOf(profilingTracesDir.mkdirs()));
/*      */       
/*      */       try {
/*  636 */         options
/*  637 */           .getExecutorService()
/*  638 */           .submit(() -> {
/*      */               File[] oldTracesDirContent = profilingTracesDir.listFiles();
/*      */ 
/*      */               
/*      */               if (oldTracesDirContent == null) {
/*      */                 return;
/*      */               }
/*      */               
/*      */               for (File f : oldTracesDirContent) {
/*      */                 if (f.lastModified() < classCreationTimestamp - TimeUnit.MINUTES.toMillis(5L)) {
/*      */                   FileUtils.deleteRecursively(f);
/*      */                 }
/*      */               } 
/*      */             });
/*  652 */       } catch (RejectedExecutionException e) {
/*  653 */         options
/*  654 */           .getLogger()
/*  655 */           .log(SentryLevel.ERROR, "Failed to call the executor. Old profiles will not be deleted. Did you call Sentry.close()?", e);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  662 */     IModulesLoader modulesLoader = options.getModulesLoader();
/*  663 */     if (!options.isSendModules()) {
/*  664 */       options.setModulesLoader((IModulesLoader)NoOpModulesLoader.getInstance());
/*  665 */     } else if (modulesLoader instanceof NoOpModulesLoader) {
/*  666 */       options.setModulesLoader((IModulesLoader)new CompositeModulesLoader(
/*      */             
/*  668 */             Arrays.asList(new IModulesLoader[] {
/*  669 */                 (IModulesLoader)new ManifestModulesLoader(options.getLogger()), (IModulesLoader)new ResourcesModulesLoader(options
/*  670 */                   .getLogger())
/*  671 */               }, ), options.getLogger()));
/*      */     } 
/*      */     
/*  674 */     if (options.getDebugMetaLoader() instanceof io.sentry.internal.debugmeta.NoOpDebugMetaLoader) {
/*  675 */       options.setDebugMetaLoader((IDebugMetaLoader)new ResourcesDebugMetaLoader(options.getLogger()));
/*      */     }
/*  677 */     List<Properties> propertiesList = options.getDebugMetaLoader().loadDebugMeta();
/*  678 */     DebugMetaPropertiesApplier.apply(options, propertiesList);
/*      */     
/*  680 */     IThreadChecker threadChecker = options.getThreadChecker();
/*      */     
/*  682 */     if (threadChecker instanceof io.sentry.util.thread.NoOpThreadChecker) {
/*  683 */       options.setThreadChecker((IThreadChecker)ThreadChecker.getInstance());
/*      */     }
/*      */     
/*  686 */     if (options.getPerformanceCollectors().isEmpty()) {
/*  687 */       options.addPerformanceCollector(new JavaMemoryCollector());
/*      */     }
/*      */     
/*  690 */     if (options.isEnableBackpressureHandling() && Platform.isJvm()) {
/*  691 */       if (options.getBackpressureMonitor() instanceof io.sentry.backpressure.NoOpBackpressureMonitor) {
/*  692 */         options.setBackpressureMonitor((IBackpressureMonitor)new BackpressureMonitor(options, 
/*  693 */               ScopesAdapter.getInstance()));
/*      */       }
/*  695 */       options.getBackpressureMonitor().start();
/*      */     } 
/*      */     
/*  698 */     initJvmContinuousProfiling(options);
/*      */     
/*  700 */     options
/*  701 */       .getLogger()
/*  702 */       .log(SentryLevel.INFO, "Continuous profiler is enabled %s mode: %s", new Object[] {
/*      */ 
/*      */           
/*  705 */           Boolean.valueOf(options.isContinuousProfilingEnabled()), options
/*  706 */           .getProfileLifecycle() });
/*      */   }
/*      */   
/*      */   private static void initJvmContinuousProfiling(@NotNull SentryOptions options) {
/*  710 */     InitUtil.initializeProfiler(options);
/*  711 */     InitUtil.initializeProfileConverter(options);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void close() {
/*  716 */     ISentryLifecycleToken ignored = lock.acquire(); try {
/*  717 */       IScopes scopes = getCurrentScopes();
/*  718 */       rootScopes = NoOpScopes.getInstance();
/*      */       
/*  720 */       getScopesStorage().close();
/*  721 */       scopes.close(false);
/*  722 */       if (ignored != null) ignored.close(); 
/*      */     } catch (Throwable throwable) {
/*      */       if (ignored != null)
/*      */         try {
/*      */           ignored.close();
/*      */         } catch (Throwable throwable1) {
/*      */           throwable.addSuppressed(throwable1);
/*      */         }  
/*      */       throw throwable;
/*      */     }  } @NotNull
/*  732 */   public static SentryId captureEvent(@NotNull SentryEvent event) { return getCurrentScopes().captureEvent(event); }
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
/*      */   public static SentryId captureEvent(@NotNull SentryEvent event, @NotNull ScopeCallback callback) {
/*  744 */     return getCurrentScopes().captureEvent(event, callback);
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
/*      */   public static SentryId captureEvent(@NotNull SentryEvent event, @Nullable Hint hint) {
/*  756 */     return getCurrentScopes().captureEvent(event, hint);
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
/*      */   @NotNull
/*      */   public static SentryId captureEvent(@NotNull SentryEvent event, @Nullable Hint hint, @NotNull ScopeCallback callback) {
/*  771 */     return getCurrentScopes().captureEvent(event, hint, callback);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public static SentryId captureMessage(@NotNull String message) {
/*  781 */     return getCurrentScopes().captureMessage(message);
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
/*      */   public static SentryId captureMessage(@NotNull String message, @NotNull ScopeCallback callback) {
/*  793 */     return getCurrentScopes().captureMessage(message, callback);
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
/*      */   public static SentryId captureMessage(@NotNull String message, @NotNull SentryLevel level) {
/*  805 */     return getCurrentScopes().captureMessage(message, level);
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
/*      */   @NotNull
/*      */   public static SentryId captureMessage(@NotNull String message, @NotNull SentryLevel level, @NotNull ScopeCallback callback) {
/*  820 */     return getCurrentScopes().captureMessage(message, level, callback);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public static SentryId captureFeedback(@NotNull Feedback feedback) {
/*  830 */     return getCurrentScopes().captureFeedback(feedback);
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
/*      */   public static SentryId captureFeedback(@NotNull Feedback feedback, @Nullable Hint hint) {
/*  842 */     return getCurrentScopes().captureFeedback(feedback, hint);
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
/*      */   @NotNull
/*      */   public static SentryId captureFeedback(@NotNull Feedback feedback, @Nullable Hint hint, @Nullable ScopeCallback callback) {
/*  857 */     return getCurrentScopes().captureFeedback(feedback, hint, callback);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public static SentryId captureException(@NotNull Throwable throwable) {
/*  867 */     return getCurrentScopes().captureException(throwable);
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
/*      */   public static SentryId captureException(@NotNull Throwable throwable, @NotNull ScopeCallback callback) {
/*  879 */     return getCurrentScopes().captureException(throwable, callback);
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
/*      */   public static SentryId captureException(@NotNull Throwable throwable, @Nullable Hint hint) {
/*  891 */     return getCurrentScopes().captureException(throwable, hint);
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
/*      */   @NotNull
/*      */   public static SentryId captureException(@NotNull Throwable throwable, @Nullable Hint hint, @NotNull ScopeCallback callback) {
/*  906 */     return getCurrentScopes().captureException(throwable, hint, callback);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void captureUserFeedback(@NotNull UserFeedback userFeedback) {
/*  915 */     getCurrentScopes().captureUserFeedback(userFeedback);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void addBreadcrumb(@NotNull Breadcrumb breadcrumb, @Nullable Hint hint) {
/*  926 */     getCurrentScopes().addBreadcrumb(breadcrumb, hint);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void addBreadcrumb(@NotNull Breadcrumb breadcrumb) {
/*  935 */     getCurrentScopes().addBreadcrumb(breadcrumb);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void addBreadcrumb(@NotNull String message) {
/*  944 */     getCurrentScopes().addBreadcrumb(message);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void addBreadcrumb(@NotNull String message, @NotNull String category) {
/*  955 */     getCurrentScopes().addBreadcrumb(message, category);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setLevel(@Nullable SentryLevel level) {
/*  964 */     getCurrentScopes().setLevel(level);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setTransaction(@Nullable String transaction) {
/*  973 */     getCurrentScopes().setTransaction(transaction);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setUser(@Nullable User user) {
/*  982 */     getCurrentScopes().setUser(user);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setFingerprint(@NotNull List<String> fingerprint) {
/*  991 */     getCurrentScopes().setFingerprint(fingerprint);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void clearBreadcrumbs() {
/*  996 */     getCurrentScopes().clearBreadcrumbs();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setTag(@Nullable String key, @Nullable String value) {
/* 1006 */     getCurrentScopes().setTag(key, value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void removeTag(@Nullable String key) {
/* 1015 */     getCurrentScopes().removeTag(key);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setExtra(@Nullable String key, @Nullable String value) {
/* 1026 */     getCurrentScopes().setExtra(key, value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void removeExtra(@Nullable String key) {
/* 1035 */     getCurrentScopes().removeExtra(key);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public static SentryId getLastEventId() {
/* 1044 */     return getCurrentScopes().getLastEventId();
/*      */   }
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public static ISentryLifecycleToken pushScope() {
/* 1050 */     if (!globalHubMode) {
/* 1051 */       return getCurrentScopes().pushScope();
/*      */     }
/* 1053 */     return NoOpScopesLifecycleToken.getInstance();
/*      */   }
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public static ISentryLifecycleToken pushIsolationScope() {
/* 1059 */     if (!globalHubMode) {
/* 1060 */       return getCurrentScopes().pushIsolationScope();
/*      */     }
/* 1062 */     return NoOpScopesLifecycleToken.getInstance();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static void popScope() {
/* 1074 */     if (!globalHubMode) {
/* 1075 */       getCurrentScopes().popScope();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void withScope(@NotNull ScopeCallback callback) {
/* 1085 */     getCurrentScopes().withScope(callback);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void withIsolationScope(@NotNull ScopeCallback callback) {
/* 1095 */     getCurrentScopes().withIsolationScope(callback);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void configureScope(@NotNull ScopeCallback callback) {
/* 1104 */     configureScope(null, callback);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void configureScope(@Nullable ScopeType scopeType, @NotNull ScopeCallback callback) {
/* 1114 */     getCurrentScopes().configureScope(scopeType, callback);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void bindClient(@NotNull ISentryClient client) {
/* 1123 */     getCurrentScopes().bindClient(client);
/*      */   }
/*      */   
/*      */   public static boolean isHealthy() {
/* 1127 */     return getCurrentScopes().isHealthy();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void flush(long timeoutMillis) {
/* 1136 */     getCurrentScopes().flush(timeoutMillis);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void startSession() {
/* 1141 */     getCurrentScopes().startSession();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void endSession() {
/* 1146 */     getCurrentScopes().endSession();
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
/*      */   public static ITransaction startTransaction(@NotNull String name, @NotNull String operation) {
/* 1158 */     return getCurrentScopes().startTransaction(name, operation);
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
/*      */   @NotNull
/*      */   public static ITransaction startTransaction(@NotNull String name, @NotNull String operation, @NotNull TransactionOptions transactionOptions) {
/* 1173 */     return getCurrentScopes().startTransaction(name, operation, transactionOptions);
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
/*      */   public static ITransaction startTransaction(@NotNull String name, @NotNull String operation, @Nullable String description, @NotNull TransactionOptions transactionOptions) {
/* 1191 */     ITransaction transaction = getCurrentScopes().startTransaction(name, operation, transactionOptions);
/* 1192 */     transaction.setDescription(description);
/* 1193 */     return transaction;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public static ITransaction startTransaction(@NotNull TransactionContext transactionContexts) {
/* 1204 */     return getCurrentScopes().startTransaction(transactionContexts);
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
/*      */   @NotNull
/*      */   public static ITransaction startTransaction(@NotNull TransactionContext transactionContext, @NotNull TransactionOptions transactionOptions) {
/* 1217 */     return getCurrentScopes().startTransaction(transactionContext, transactionOptions);
/*      */   }
/*      */ 
/*      */   
/*      */   @Experimental
/*      */   public static void startProfiler() {
/* 1223 */     getCurrentScopes().startProfiler();
/*      */   }
/*      */ 
/*      */   
/*      */   @Experimental
/*      */   public static void stopProfiler() {
/* 1229 */     getCurrentScopes().stopProfiler();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public static ISpan getSpan() {
/* 1240 */     if (globalHubMode && Platform.isAndroid()) {
/* 1241 */       return getCurrentScopes().getTransaction();
/*      */     }
/* 1243 */     return getCurrentScopes().getSpan();
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
/*      */   public static Boolean isCrashedLastRun() {
/* 1258 */     return getCurrentScopes().isCrashedLastRun();
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
/*      */   public static void reportFullyDisplayed() {
/* 1270 */     getCurrentScopes().reportFullyDisplayed();
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
/*      */   @Nullable
/*      */   public static TransactionContext continueTrace(@Nullable String sentryTrace, @Nullable List<String> baggageHeaders) {
/* 1299 */     return getCurrentScopes().continueTrace(sentryTrace, baggageHeaders);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public static SentryTraceHeader getTraceparent() {
/* 1309 */     return getCurrentScopes().getTraceparent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public static BaggageHeader getBaggage() {
/* 1319 */     return getCurrentScopes().getBaggage();
/*      */   }
/*      */   @NotNull
/*      */   public static SentryId captureCheckIn(@NotNull CheckIn checkIn) {
/* 1323 */     return getCurrentScopes().captureCheckIn(checkIn);
/*      */   }
/*      */   
/*      */   @NotNull
/*      */   public static ILoggerApi logger() {
/* 1328 */     return getCurrentScopes().logger();
/*      */   }
/*      */   
/*      */   @NotNull
/*      */   public static IReplayApi replay() {
/* 1333 */     return getCurrentScopes().getScope().getOptions().getReplayController();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public static IDistributionApi distribution() {
/* 1344 */     return getCurrentScopes().getScope().getOptions().getDistributionController();
/*      */   }
/*      */   
/*      */   public static void showUserFeedbackDialog() {
/* 1348 */     showUserFeedbackDialog(null);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void showUserFeedbackDialog(@Nullable SentryFeedbackOptions.OptionsConfigurator configurator) {
/* 1353 */     showUserFeedbackDialog(null, configurator);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void showUserFeedbackDialog(@Nullable SentryId associatedEventId, @Nullable SentryFeedbackOptions.OptionsConfigurator configurator) {
/* 1359 */     SentryOptions options = getCurrentScopes().getOptions();
/* 1360 */     options.getFeedbackOptions().getDialogHandler().showDialog(associatedEventId, configurator);
/*      */   }
/*      */   
/*      */   public static void addFeatureFlag(@Nullable String flag, @Nullable Boolean result) {
/* 1364 */     getCurrentScopes().addFeatureFlag(flag, result);
/*      */   }
/*      */   
/*      */   public static interface OptionsConfiguration<T extends SentryOptions> {
/*      */     void configure(@NotNull T param1T);
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\Sentry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */