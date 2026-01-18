/*     */ package com.hypixel.hytale.server.core;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.common.plugin.PluginManifest;
/*     */ import com.hypixel.hytale.common.thread.HytaleForkJoinThreadFactory;
/*     */ import com.hypixel.hytale.common.util.FormatUtil;
/*     */ import com.hypixel.hytale.common.util.GCUtil;
/*     */ import com.hypixel.hytale.common.util.HardwareUtil;
/*     */ import com.hypixel.hytale.common.util.NetworkUtil;
/*     */ import com.hypixel.hytale.common.util.java.ManifestUtil;
/*     */ import com.hypixel.hytale.event.EventBus;
/*     */ import com.hypixel.hytale.event.IBaseEvent;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.logger.backend.HytaleLogManager;
/*     */ import com.hypixel.hytale.logger.backend.HytaleLoggerBackend;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.math.util.TrigMathUtil;
/*     */ import com.hypixel.hytale.metrics.JVMMetrics;
/*     */ import com.hypixel.hytale.metrics.MetricsRegistry;
/*     */ import com.hypixel.hytale.plugin.early.EarlyPluginLoader;
/*     */ import com.hypixel.hytale.server.core.asset.AssetRegistryLoader;
/*     */ import com.hypixel.hytale.server.core.asset.LoadAssetEvent;
/*     */ import com.hypixel.hytale.server.core.auth.ServerAuthManager;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandManager;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandSender;
/*     */ import com.hypixel.hytale.server.core.console.ConsoleSender;
/*     */ import com.hypixel.hytale.server.core.event.events.BootEvent;
/*     */ import com.hypixel.hytale.server.core.event.events.ShutdownEvent;
/*     */ import com.hypixel.hytale.server.core.io.ServerManager;
/*     */ import com.hypixel.hytale.server.core.io.netty.NettyUtil;
/*     */ import com.hypixel.hytale.server.core.modules.singleplayer.SingleplayerModule;
/*     */ import com.hypixel.hytale.server.core.plugin.PluginBase;
/*     */ import com.hypixel.hytale.server.core.plugin.PluginClassLoader;
/*     */ import com.hypixel.hytale.server.core.plugin.PluginManager;
/*     */ import com.hypixel.hytale.server.core.plugin.PluginState;
/*     */ import com.hypixel.hytale.server.core.universe.Universe;
/*     */ import com.hypixel.hytale.server.core.universe.datastore.DataStoreProvider;
/*     */ import com.hypixel.hytale.server.core.universe.datastore.DiskDataStoreProvider;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.util.concurrent.ThreadUtil;
/*     */ import com.sun.management.GarbageCollectionNotificationInfo;
/*     */ import io.netty.handler.codec.quic.Quic;
/*     */ import io.sentry.Hint;
/*     */ import io.sentry.IScope;
/*     */ import io.sentry.Sentry;
/*     */ import io.sentry.SentryEvent;
/*     */ import io.sentry.SentryOptions;
/*     */ import io.sentry.protocol.Contexts;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.io.IOException;
/*     */ import java.time.Instant;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.ScheduledExecutorService;
/*     */ import java.util.concurrent.Semaphore;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import java.util.concurrent.atomic.AtomicReference;
/*     */ import java.util.function.Function;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ import joptsimple.OptionSet;
/*     */ 
/*     */ public class HytaleServer {
/*     */   public static final int DEFAULT_PORT = 5520;
/*  70 */   public static final ScheduledExecutorService SCHEDULED_EXECUTOR = Executors.newSingleThreadScheduledExecutor(ThreadUtil.daemon("Scheduler"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static final MetricsRegistry<HytaleServer> METRICS_REGISTRY;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  89 */     METRICS_REGISTRY = (new MetricsRegistry()).register("Time", server -> Instant.now(), (Codec)Codec.INSTANT).register("Boot", server -> server.boot, (Codec)Codec.INSTANT).register("BootStart", server -> Long.valueOf(server.bootStart), (Codec)Codec.LONG).register("Booting", server -> Boolean.valueOf(server.booting.get()), (Codec)Codec.BOOLEAN).register("ShutdownReason", server -> { ShutdownReason reason = server.shutdown.get(); return (Function)((reason == null) ? null : reason.toString()); }(Codec)Codec.STRING).register("PluginManager", HytaleServer::getPluginManager, (Codec)PluginManager.METRICS_REGISTRY).register("Config", HytaleServer::getConfig, (Codec)HytaleServerConfig.CODEC).register("JVM", JVMMetrics.METRICS_REGISTRY);
/*     */   }
/*  91 */   private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */ 
/*     */ 
/*     */   
/*     */   private static HytaleServer instance;
/*     */ 
/*     */ 
/*     */   
/*  99 */   private final Semaphore aliveLock = new Semaphore(0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 105 */   private final AtomicBoolean booting = new AtomicBoolean(false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 111 */   private final AtomicBoolean booted = new AtomicBoolean(false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 117 */   private final AtomicReference<ShutdownReason> shutdown = new AtomicReference<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 122 */   private final EventBus eventBus = new EventBus(Options.getOptionSet().has(Options.EVENT_DEBUG));
/* 123 */   private final PluginManager pluginManager = new PluginManager();
/* 124 */   private final CommandManager commandManager = new CommandManager();
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final HytaleServerConfig hytaleServerConfig;
/*     */ 
/*     */   
/*     */   private final Instant boot;
/*     */   
/*     */   private final long bootStart;
/*     */   
/*     */   private int pluginsProgress;
/*     */ 
/*     */   
/*     */   public HytaleServer() throws IOException {
/* 139 */     instance = this;
/*     */     
/* 141 */     Quic.ensureAvailability();
/*     */     
/* 143 */     HytaleLoggerBackend.setIndent(25);
/*     */     
/* 145 */     ThreadUtil.forceTimeHighResolution();
/* 146 */     ThreadUtil.createKeepAliveThread(this.aliveLock);
/*     */     
/* 148 */     this.boot = Instant.now();
/* 149 */     this.bootStart = System.nanoTime();
/* 150 */     LOGGER.at(Level.INFO).log("Starting HytaleServer");
/*     */     
/* 152 */     Constants.init();
/*     */     
/* 154 */     DataStoreProvider.CODEC.register("Disk", DiskDataStoreProvider.class, (Codec)DiskDataStoreProvider.CODEC);
/*     */ 
/*     */     
/* 157 */     LOGGER.at(Level.INFO).log("Loading config...");
/* 158 */     this.hytaleServerConfig = HytaleServerConfig.load();
/* 159 */     HytaleLoggerBackend.reloadLogLevels();
/*     */ 
/*     */     
/* 162 */     System.setProperty("java.util.concurrent.ForkJoinPool.common.threadFactory", HytaleForkJoinThreadFactory.class.getName());
/*     */     
/* 164 */     OptionSet optionSet = Options.getOptionSet();
/*     */ 
/*     */     
/* 167 */     LOGGER.at(Level.INFO).log("Authentication mode: %s", optionSet.valueOf(Options.AUTH_MODE));
/* 168 */     ServerAuthManager.getInstance().initialize();
/*     */     
/* 170 */     if (EarlyPluginLoader.hasTransformers()) {
/* 171 */       HytaleLogger.getLogger().at(Level.INFO).log("Early plugins loaded!! Disabling Sentry!!");
/* 172 */     } else if (!optionSet.has(Options.DISABLE_SENTRY)) {
/* 173 */       LOGGER.at(Level.INFO).log("Enabling Sentry");
/* 174 */       SentryOptions options = new SentryOptions();
/* 175 */       options.setDsn("https://6043a13c7b5c45b5c834b6d896fb378e@sentry.hytale.com/4");
/* 176 */       options.setRelease(ManifestUtil.getImplementationVersion());
/* 177 */       options.setDist(ManifestUtil.getImplementationRevisionId());
/* 178 */       options.setEnvironment("release");
/* 179 */       options.setTag("patchline", ManifestUtil.getPatchline());
/* 180 */       options.setServerName(NetworkUtil.getHostName());
/*     */       
/* 182 */       options.setBeforeSend((event, hint) -> {
/*     */             Throwable throwable = event.getThrowable();
/*     */             
/*     */             if (PluginClassLoader.isFromThirdPartyPlugin(throwable)) {
/*     */               return null;
/*     */             }
/*     */             
/*     */             Contexts contexts = event.getContexts();
/*     */             
/*     */             HashMap<String, Object> serverContext = new HashMap<>();
/*     */             
/*     */             serverContext.put("name", getServerName());
/*     */             
/*     */             serverContext.put("max-players", Integer.valueOf(getConfig().getMaxPlayers()));
/*     */             
/*     */             ServerManager serverManager = ServerManager.get();
/*     */             
/*     */             if (serverManager != null) {
/*     */               serverContext.put("listeners", serverManager.getListeners().stream().map(Object::toString).toList());
/*     */             }
/*     */             
/*     */             contexts.put("server", serverContext);
/*     */             
/*     */             Universe universe = Universe.get();
/*     */             if (universe != null) {
/*     */               HashMap<String, Object> universeContext = new HashMap<>();
/*     */               universeContext.put("path", universe.getPath().toString());
/*     */               universeContext.put("player-count", Integer.valueOf(universe.getPlayerCount()));
/*     */               universeContext.put("worlds", universe.getWorlds().keySet().stream().toList());
/*     */               contexts.put("universe", universeContext);
/*     */             } 
/*     */             HashMap<String, Object> pluginsContext = new HashMap<>();
/*     */             for (PluginBase plugin : this.pluginManager.getPlugins()) {
/*     */               PluginManifest manifest = plugin.getManifest();
/*     */               HashMap<String, Object> pluginInfo = new HashMap<>();
/*     */               pluginInfo.put("version", manifest.getVersion().toString());
/*     */               pluginInfo.put("state", plugin.getState().name());
/*     */               pluginsContext.put(plugin.getIdentifier().toString(), pluginInfo);
/*     */             } 
/*     */             contexts.put("plugins", pluginsContext);
/*     */             return event;
/*     */           });
/* 224 */       Sentry.init(options);
/*     */       
/* 226 */       Sentry.configureScope(scope -> {
/*     */             UUID hardwareUUID = HardwareUtil.getUUID();
/*     */ 
/*     */ 
/*     */             
/*     */             if (hardwareUUID != null) {
/*     */               scope.setContexts("hardware", Map.of("uuid", hardwareUUID.toString()));
/*     */             }
/*     */ 
/*     */ 
/*     */             
/*     */             scope.setContexts("build", Map.of("version", String.valueOf(ManifestUtil.getImplementationVersion()), "revision-id", String.valueOf(ManifestUtil.getImplementationRevisionId()), "patchline", String.valueOf(ManifestUtil.getPatchline()), "environment", "release"));
/*     */ 
/*     */ 
/*     */             
/*     */             if (Constants.SINGLEPLAYER) {
/*     */               scope.setContexts("singleplayer", Map.of("owner-uuid", String.valueOf(SingleplayerModule.getUuid()), "owner-name", SingleplayerModule.getUsername()));
/*     */             }
/*     */           });
/*     */ 
/*     */       
/* 247 */       HytaleLogger.getLogger().setSentryClient(Sentry.getCurrentScopes());
/*     */     } 
/*     */ 
/*     */     
/* 251 */     NettyUtil.init();
/*     */ 
/*     */     
/* 254 */     float sin = TrigMathUtil.sin(0.0F);
/* 255 */     float atan2 = TrigMathUtil.atan2(0.0F, 0.0F);
/*     */ 
/*     */     
/* 258 */     Thread shutdownHook = new Thread(() -> { if (this.shutdown.getAndSet(ShutdownReason.SIGINT) != null) return;  shutdown0(ShutdownReason.SIGINT); }"ShutdownHook");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 263 */     shutdownHook.setDaemon(false);
/* 264 */     Runtime.getRuntime().addShutdownHook(shutdownHook);
/*     */     
/* 266 */     AssetRegistryLoader.init();
/*     */ 
/*     */     
/* 269 */     for (PluginManifest manifest : Constants.CORE_PLUGINS) {
/* 270 */       this.pluginManager.registerCorePlugin(manifest);
/*     */     }
/*     */     
/* 273 */     GCUtil.register(info -> {
/*     */           Universe universe = Universe.get();
/*     */           
/*     */           if (universe == null) {
/*     */             return;
/*     */           }
/*     */           
/*     */           for (World world : universe.getWorlds().values()) {
/*     */             world.markGCHasRun();
/*     */           }
/*     */         });
/* 284 */     boot();
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public EventBus getEventBus() {
/* 289 */     return this.eventBus;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public PluginManager getPluginManager() {
/* 294 */     return this.pluginManager;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public CommandManager getCommandManager() {
/* 299 */     return this.commandManager;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public HytaleServerConfig getConfig() {
/* 304 */     return this.hytaleServerConfig;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void boot() {
/* 312 */     if (this.booting.getAndSet(true))
/*     */       return; 
/* 314 */     LOGGER.at(Level.INFO).log("Booting up HytaleServer - Version: " + 
/* 315 */         ManifestUtil.getImplementationVersion() + ", Revision: " + 
/* 316 */         ManifestUtil.getImplementationRevisionId());
/*     */     
/*     */     try {
/* 319 */       this.pluginsProgress = 0;
/* 320 */       sendSingleplayerProgress();
/* 321 */       if (isShuttingDown()) {
/*     */         return;
/*     */       }
/* 324 */       LOGGER.at(Level.INFO).log("Setup phase...");
/* 325 */       this.commandManager.registerCommands();
/* 326 */       this.pluginManager.setup();
/*     */ 
/*     */       
/* 329 */       ServerAuthManager.getInstance().initializeCredentialStore();
/*     */       
/* 331 */       LOGGER.at(Level.INFO).log("Setup phase completed! Boot time %s", FormatUtil.nanosToString(System.nanoTime() - this.bootStart));
/* 332 */       if (isShuttingDown()) {
/*     */         return;
/*     */       }
/* 335 */       LoadAssetEvent loadAssetEvent = (LoadAssetEvent)get().getEventBus().dispatchFor(LoadAssetEvent.class).dispatch((IBaseEvent)new LoadAssetEvent(this.bootStart));
/* 336 */       if (isShuttingDown())
/*     */         return; 
/* 338 */       if (loadAssetEvent.isShouldShutdown()) {
/* 339 */         List<String> reasons = loadAssetEvent.getReasons();
/* 340 */         String join = String.join(", ", (Iterable)reasons);
/* 341 */         LOGGER.at(Level.SEVERE).log("Asset validation FAILED with %d reason(s): %s", reasons.size(), join);
/* 342 */         shutdownServer(ShutdownReason.VALIDATE_ERROR.withMessage(join));
/*     */         
/*     */         return;
/*     */       } 
/* 346 */       if (Options.getOptionSet().has(Options.SHUTDOWN_AFTER_VALIDATE)) {
/* 347 */         LOGGER.at(Level.INFO).log("Asset validation passed");
/* 348 */         shutdownServer(ShutdownReason.SHUTDOWN);
/*     */         
/*     */         return;
/*     */       } 
/* 352 */       this.pluginsProgress = 0;
/* 353 */       sendSingleplayerProgress();
/* 354 */       if (isShuttingDown()) {
/*     */         return;
/*     */       }
/* 357 */       LOGGER.at(Level.INFO).log("Starting plugin manager...");
/* 358 */       this.pluginManager.start();
/* 359 */       LOGGER.at(Level.INFO).log("Plugin manager started! Startup time so far: %s", FormatUtil.nanosToString(System.nanoTime() - this.bootStart));
/* 360 */       if (isShuttingDown())
/*     */         return; 
/* 362 */       sendSingleplayerSignal("-=|Enabled|0");
/* 363 */     } catch (Throwable throwable) {
/* 364 */       ((HytaleLogger.Api)LOGGER.at(Level.SEVERE).withCause(throwable)).log("Failed to boot HytaleServer!");
/*     */       
/* 366 */       Throwable t = throwable;
/* 367 */       for (; t.getCause() != null; t = t.getCause());
/* 368 */       shutdownServer(ShutdownReason.CRASH.withMessage("Failed to start server! " + t.getMessage()));
/*     */     } 
/*     */ 
/*     */     
/* 372 */     if (this.hytaleServerConfig.consumeHasChanged()) {
/* 373 */       HytaleServerConfig.save(this.hytaleServerConfig).join();
/*     */     }
/*     */ 
/*     */     
/* 377 */     SCHEDULED_EXECUTOR.scheduleWithFixedDelay(() -> {
/*     */           try {
/*     */             if (this.hytaleServerConfig.consumeHasChanged()) {
/*     */               HytaleServerConfig.save(this.hytaleServerConfig).join();
/*     */             }
/* 382 */           } catch (Exception e) {
/*     */             ((HytaleLogger.Api)LOGGER.at(Level.SEVERE).withCause(e)).log("Failed to save server config!");
/*     */           } 
/*     */         }1L, 1L, TimeUnit.MINUTES);
/*     */     
/* 387 */     LOGGER.at(Level.INFO).log("Getting Hytale Universe ready...");
/*     */ 
/*     */     
/* 390 */     Universe.get().getUniverseReady().join();
/* 391 */     LOGGER.at(Level.INFO).log("Universe ready!");
/*     */     
/* 393 */     ObjectArrayList<String> objectArrayList = new ObjectArrayList();
/* 394 */     if (Constants.SINGLEPLAYER) {
/* 395 */       objectArrayList.add("Singleplayer");
/*     */     } else {
/* 397 */       objectArrayList.add("Multiplayer");
/*     */     } 
/* 399 */     if (Constants.FRESH_UNIVERSE) {
/* 400 */       objectArrayList.add("Fresh Universe");
/*     */     }
/*     */     
/* 403 */     this.booted.set(true);
/* 404 */     ServerManager.get().waitForBindComplete();
/* 405 */     this.eventBus.dispatch(BootEvent.class);
/*     */ 
/*     */     
/* 408 */     List<String> bootCommands = Options.getOptionSet().valuesOf(Options.BOOT_COMMAND);
/* 409 */     if (!bootCommands.isEmpty()) {
/* 410 */       CommandManager.get().handleCommands((CommandSender)ConsoleSender.INSTANCE, new ArrayDeque<>(bootCommands)).join();
/*     */     }
/*     */     
/* 413 */     String border = "\033[0;32m===============================================================================================";
/* 414 */     LOGGER.at(Level.INFO).log("\033[0;32m===============================================================================================");
/* 415 */     LOGGER.at(Level.INFO).log("%s         Hytale Server Booted! [%s] took %s", "\033[0;32m", 
/*     */         
/* 417 */         String.join(", ", (Iterable)objectArrayList), 
/* 418 */         FormatUtil.nanosToString(System.nanoTime() - this.bootStart));
/*     */     
/* 420 */     LOGGER.at(Level.INFO).log("\033[0;32m===============================================================================================");
/*     */     
/* 422 */     ServerAuthManager authManager = ServerAuthManager.getInstance();
/* 423 */     if (!authManager.isSingleplayer() && authManager.getAuthMode() == ServerAuthManager.AuthMode.NONE) {
/* 424 */       LOGGER.at(Level.WARNING).log("%sNo server tokens configured. Use /auth login to authenticate.", "\033[0;31m");
/*     */     }
/*     */     
/* 427 */     sendSingleplayerSignal(">> Singleplayer Ready <<");
/*     */   }
/*     */   
/*     */   public void shutdownServer() {
/* 431 */     shutdownServer(ShutdownReason.SHUTDOWN);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void shutdownServer(@Nonnull ShutdownReason reason) {
/* 440 */     Objects.requireNonNull(reason, "Server shutdown reason can't be null!");
/*     */ 
/*     */     
/* 443 */     if (this.shutdown.getAndSet(reason) != null)
/* 444 */       return;  if (reason.getMessage() != null) sendSingleplayerSignal("-=|Shutdown|" + reason.getMessage());
/*     */     
/* 446 */     Thread shutdownThread = new Thread(() -> shutdown0(reason), "ShutdownThread");
/* 447 */     shutdownThread.setDaemon(false);
/* 448 */     shutdownThread.start();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void shutdown0(@Nonnull ShutdownReason reason) {
/* 456 */     LOGGER.at(Level.INFO).log("Shutdown triggered!!!");
/*     */     
/*     */     try {
/* 459 */       LOGGER.at(Level.INFO).log("Shutting down... %d  '%s'", reason.getExitCode(), reason.getMessage());
/*     */ 
/*     */       
/* 462 */       this.eventBus.dispatch(ShutdownEvent.class);
/* 463 */       this.pluginManager.shutdown();
/* 464 */       this.commandManager.shutdown();
/* 465 */       this.eventBus.shutdown();
/* 466 */       ServerAuthManager.getInstance().shutdown();
/*     */ 
/*     */       
/* 469 */       LOGGER.at(Level.INFO).log("Saving config...");
/* 470 */       if (this.hytaleServerConfig.consumeHasChanged()) {
/* 471 */         HytaleServerConfig.save(this.hytaleServerConfig).join();
/*     */       }
/*     */       
/* 474 */       LOGGER.at(Level.INFO).log("Shutdown completed!");
/* 475 */     } catch (Throwable t) {
/* 476 */       ((HytaleLogger.Api)LOGGER.at(Level.SEVERE).withCause(t)).log("Exception while shutting down:");
/*     */     } 
/*     */ 
/*     */     
/* 480 */     this.aliveLock.release();
/*     */     
/* 482 */     HytaleLogManager.resetFinally();
/*     */ 
/*     */     
/* 485 */     SCHEDULED_EXECUTOR.schedule(() -> { LOGGER.at(Level.SEVERE).log("Forcing shutdown!"); Runtime.getRuntime().halt(reason.getExitCode()); }3L, TimeUnit.SECONDS);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 491 */     if (reason != ShutdownReason.SIGINT) System.exit(reason.getExitCode()); 
/*     */   }
/*     */   
/*     */   public void doneSetup(PluginBase plugin) {
/* 495 */     this.pluginsProgress++;
/* 496 */     sendSingleplayerProgress();
/*     */   }
/*     */   
/*     */   public void doneStart(PluginBase plugin) {
/* 500 */     this.pluginsProgress++;
/* 501 */     sendSingleplayerProgress();
/*     */   }
/*     */   
/*     */   public void doneStop(PluginBase plugin) {
/* 505 */     this.pluginsProgress--;
/* 506 */     sendSingleplayerProgress();
/*     */   }
/*     */   
/*     */   public void sendSingleplayerProgress() {
/* 510 */     List<PluginBase> plugins = this.pluginManager.getPlugins();
/* 511 */     if (this.shutdown.get() != null) {
/* 512 */       sendSingleplayerSignal("-=|Shutdown Modules|" + MathUtil.round((plugins.size() - this.pluginsProgress) / plugins.size(), 2) * 100.0D);
/* 513 */     } else if (this.pluginManager.getState() == PluginState.SETUP) {
/* 514 */       sendSingleplayerSignal("-=|Setup|" + MathUtil.round(this.pluginsProgress / plugins.size(), 2) * 100.0D);
/* 515 */     } else if (this.pluginManager.getState() == PluginState.START) {
/* 516 */       sendSingleplayerSignal("-=|Starting|" + MathUtil.round(this.pluginsProgress / plugins.size(), 2) * 100.0D);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getServerName() {
/* 521 */     return getConfig().getServerName();
/*     */   }
/*     */   
/*     */   public boolean isBooting() {
/* 525 */     return this.booting.get();
/*     */   }
/*     */   
/*     */   public boolean isBooted() {
/* 529 */     return this.booted.get();
/*     */   }
/*     */   
/*     */   public boolean isShuttingDown() {
/* 533 */     return (this.shutdown.get() != null);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Instant getBoot() {
/* 538 */     return this.boot;
/*     */   }
/*     */   
/*     */   public long getBootStart() {
/* 542 */     return this.bootStart;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public ShutdownReason getShutdownReason() {
/* 547 */     return this.shutdown.get();
/*     */   }
/*     */   
/*     */   private void sendSingleplayerSignal(String message) {
/* 551 */     if (Constants.SINGLEPLAYER) {
/* 552 */       HytaleLoggerBackend.rawLog(message);
/*     */     }
/*     */   }
/*     */   
/*     */   public void reportSingleplayerStatus(String message) {
/* 557 */     if (Constants.SINGLEPLAYER) {
/* 558 */       HytaleLoggerBackend.rawLog("-=|" + message + "|0");
/*     */     }
/*     */   }
/*     */   
/*     */   public void reportSaveProgress(@Nonnull World world, int saved, int total) {
/* 563 */     if (!isShuttingDown())
/* 564 */       return;  double progress = MathUtil.round(saved / total, 2) * 100.0D;
/* 565 */     if (Constants.SINGLEPLAYER) {
/* 566 */       sendSingleplayerSignal("-=|Saving world " + world.getName() + " chunks|" + progress);
/*     */     
/*     */     }
/* 569 */     else if (total < 10 || saved % total / 10 == 0) {
/* 570 */       world.getLogger().at(Level.INFO).log("Saving chunks: %.0f%%", Double.valueOf(progress));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static HytaleServer get() {
/* 581 */     return instance;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\HytaleServer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */