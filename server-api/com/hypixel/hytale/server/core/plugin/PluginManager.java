/*      */ package com.hypixel.hytale.server.core.plugin;
/*      */ import com.hypixel.hytale.assetstore.AssetStore;
/*      */ import com.hypixel.hytale.codec.Codec;
/*      */ import com.hypixel.hytale.codec.ExtraInfo;
/*      */ import com.hypixel.hytale.codec.util.RawJsonReader;
/*      */ import com.hypixel.hytale.common.plugin.PluginIdentifier;
/*      */ import com.hypixel.hytale.common.plugin.PluginManifest;
/*      */ import com.hypixel.hytale.common.semver.Semver;
/*      */ import com.hypixel.hytale.common.semver.SemverRange;
/*      */ import com.hypixel.hytale.event.IEventDispatcher;
/*      */ import com.hypixel.hytale.logger.HytaleLogger;
/*      */ import com.hypixel.hytale.server.core.HytaleServer;
/*      */ import com.hypixel.hytale.server.core.HytaleServerConfig;
/*      */ import com.hypixel.hytale.server.core.Options;
/*      */ import com.hypixel.hytale.server.core.plugin.event.PluginSetupEvent;
/*      */ import com.hypixel.hytale.server.core.plugin.pending.PendingLoadJavaPlugin;
/*      */ import com.hypixel.hytale.server.core.plugin.pending.PendingLoadPlugin;
/*      */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.InputStreamReader;
/*      */ import java.net.JarURLConnection;
/*      */ import java.net.URI;
/*      */ import java.net.URISyntaxException;
/*      */ import java.net.URL;
/*      */ import java.net.URLClassLoader;
/*      */ import java.net.URLConnection;
/*      */ import java.nio.charset.StandardCharsets;
/*      */ import java.nio.file.DirectoryStream;
/*      */ import java.nio.file.Files;
/*      */ import java.nio.file.Path;
/*      */ import java.nio.file.Paths;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.concurrent.CompletableFuture;
/*      */ import java.util.concurrent.locks.ReentrantReadWriteLock;
/*      */ import java.util.logging.Level;
/*      */ import javax.annotation.Nonnull;
/*      */ import javax.annotation.Nullable;
/*      */ 
/*      */ public class PluginManager {
/*      */   @Nonnull
/*   50 */   private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*      */   
/*      */   @Nonnull
/*   53 */   public static final Path MODS_PATH = Path.of("mods", new String[0]); @Nonnull
/*      */   public static final MetricsRegistry<PluginManager> METRICS_REGISTRY; private static PluginManager instance;
/*      */   
/*      */   static {
/*   57 */     METRICS_REGISTRY = (new MetricsRegistry()).register("Plugins", pluginManager -> (PluginBase[])pluginManager.getPlugins().toArray(()), (Codec)new ArrayCodec((Codec)PluginBase.METRICS_REGISTRY, x$0 -> new PluginBase[x$0]));
/*      */   }
/*      */ 
/*      */   
/*      */   public static PluginManager get() {
/*   62 */     return instance;
/*      */   }
/*      */   @Nonnull
/*   65 */   private final PluginClassLoader corePluginClassLoader = new PluginClassLoader(this, true, new URL[0]);
/*      */   
/*      */   @Nonnull
/*   68 */   private final List<PendingLoadPlugin> corePlugins = (List<PendingLoadPlugin>)new ObjectArrayList();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   74 */   private final PluginBridgeClassLoader bridgeClassLoader = new PluginBridgeClassLoader(this, PluginManager.class.getClassLoader());
/*      */   
/*   76 */   private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
/*   77 */   private final Map<PluginIdentifier, PluginBase> plugins = (Map<PluginIdentifier, PluginBase>)new Object2ObjectLinkedOpenHashMap();
/*      */   
/*   79 */   private final Map<Path, PluginClassLoader> classLoaders = new ConcurrentHashMap<>();
/*      */   
/*      */   private final boolean loadExternalPlugins = true;
/*      */   
/*      */   @Nonnull
/*   84 */   private PluginState state = PluginState.NONE;
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   private List<PendingLoadPlugin> loadOrder;
/*      */   
/*      */   @Nullable
/*      */   private Map<PluginIdentifier, PluginBase> loading;
/*      */   
/*      */   @Nonnull
/*   94 */   private final Map<PluginIdentifier, PluginManifest> availablePlugins = (Map<PluginIdentifier, PluginManifest>)new Object2ObjectLinkedOpenHashMap();
/*      */   
/*      */   public PluginListPageManager pluginListPageManager;
/*      */   
/*      */   private ComponentType<EntityStore, PluginListPageManager.SessionSettings> sessionSettingsComponentType;
/*      */ 
/*      */   
/*      */   public PluginManager() {
/*  102 */     instance = this;
/*  103 */     this.pluginListPageManager = new PluginListPageManager();
/*      */   }
/*      */   
/*      */   public void registerCorePlugin(@Nonnull PluginManifest builder) {
/*  107 */     this.corePlugins.add(new PendingLoadJavaPlugin(null, builder, this.corePluginClassLoader));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean canLoadOnBoot(@Nonnull PluginManifest manifest) {
/*      */     boolean enabled;
/*  117 */     PluginIdentifier identifier = new PluginIdentifier(manifest);
/*  118 */     HytaleServerConfig.ModConfig modConfig = (HytaleServerConfig.ModConfig)HytaleServer.get().getConfig().getModConfig().get(identifier);
/*      */ 
/*      */     
/*  121 */     if (modConfig == null || modConfig.getEnabled() == null) {
/*  122 */       enabled = !manifest.isDisabledByDefault();
/*      */     } else {
/*  124 */       enabled = modConfig.getEnabled().booleanValue();
/*      */     } 
/*      */     
/*  127 */     if (enabled) return true;
/*      */     
/*  129 */     LOGGER.at(Level.WARNING).log("Skipping mod %s (Disabled by server config)", identifier);
/*  130 */     return false;
/*      */   }
/*      */   public void setup() {
/*      */     Path self;
/*  134 */     if (this.state != PluginState.NONE) throw new IllegalStateException("Expected PluginState.NONE but found " + String.valueOf(this.state)); 
/*  135 */     this.state = PluginState.SETUP;
/*      */     
/*  137 */     CommandManager.get().registerSystemCommand((AbstractCommand)new PluginCommand());
/*      */     
/*  139 */     this.sessionSettingsComponentType = EntityStore.REGISTRY.registerComponent(PluginListPageManager.SessionSettings.class, SessionSettings::new);
/*      */     
/*  141 */     HashMap<PluginIdentifier, PendingLoadPlugin> pending = new HashMap<>();
/*  142 */     this.availablePlugins.clear();
/*      */     
/*  144 */     LOGGER.at(Level.INFO).log("Loading pending core plugins!");
/*  145 */     for (int i = 0; i < this.corePlugins.size(); i++) {
/*  146 */       PendingLoadPlugin plugin = this.corePlugins.get(i);
/*  147 */       LOGGER.at(Level.INFO).log("- %s", plugin.getIdentifier());
/*  148 */       if (canLoadOnBoot(plugin.getManifest())) {
/*  149 */         loadPendingPlugin(pending, plugin);
/*      */       } else {
/*  151 */         this.availablePlugins.put(plugin.getIdentifier(), plugin.getManifest());
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  158 */       self = Paths.get(PluginManager.class.getProtectionDomain().getCodeSource().getLocation().toURI());
/*  159 */     } catch (URISyntaxException e) {
/*  160 */       throw new RuntimeException(e);
/*      */     } 
/*  162 */     loadPluginsFromDirectory(pending, self.getParent().resolve("builtin"), false, this.availablePlugins);
/*  163 */     loadPluginsInClasspath(pending, this.availablePlugins);
/*      */ 
/*      */ 
/*      */     
/*  167 */     loadPluginsFromDirectory(pending, MODS_PATH, !Options.getOptionSet().has(Options.BARE), this.availablePlugins);
/*      */ 
/*      */     
/*  170 */     for (Path modsPath : Options.getOptionSet().valuesOf(Options.MODS_DIRECTORIES)) {
/*  171 */       loadPluginsFromDirectory(pending, modsPath, false, this.availablePlugins);
/*      */     }
/*      */ 
/*      */     
/*  175 */     this.lock.readLock().lock();
/*      */     
/*      */     try {
/*  178 */       this.plugins.keySet().forEach(key -> {
/*      */             pending.remove(key);
/*      */             
/*      */             LOGGER.at(Level.WARNING).log("Skipping loading of %s because it is already loaded!", key);
/*      */           });
/*      */       
/*  184 */       for (Iterator<PendingLoadPlugin> iterator = pending.values().iterator(); iterator.hasNext(); ) {
/*  185 */         PendingLoadPlugin pendingLoadPlugin = iterator.next();
/*      */         
/*      */         try {
/*  188 */           validatePluginDeps(pendingLoadPlugin, pending);
/*  189 */         } catch (MissingPluginDependencyException e) {
/*  190 */           LOGGER.at(Level.SEVERE).log(e.getMessage());
/*  191 */           iterator.remove();
/*      */         } 
/*      */       } 
/*      */     } finally {
/*      */       
/*  196 */       this.lock.readLock().unlock();
/*      */     } 
/*      */     
/*  199 */     this.loadOrder = PendingLoadPlugin.calculateLoadOrder(pending);
/*  200 */     this.loading = (Map<PluginIdentifier, PluginBase>)new Object2ObjectOpenHashMap();
/*      */ 
/*      */     
/*  203 */     pending.forEach((identifier, pendingLoad) -> this.availablePlugins.put(identifier, pendingLoad.getManifest()));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  208 */     ObjectArrayList<CompletableFuture<Void>> preLoadFutures = new ObjectArrayList();
/*  209 */     this.lock.writeLock().lock();
/*      */     try {
/*  211 */       LOGGER.at(Level.FINE).log("Loading plugins!");
/*  212 */       for (PendingLoadPlugin pendingLoadPlugin : this.loadOrder) {
/*  213 */         LOGGER.at(Level.FINE).log("- %s", pendingLoadPlugin.getIdentifier());
/*      */         
/*  215 */         PluginBase plugin = pendingLoadPlugin.load();
/*  216 */         if (plugin != null) {
/*  217 */           this.plugins.put(plugin.getIdentifier(), plugin);
/*  218 */           this.loading.put(plugin.getIdentifier(), plugin);
/*  219 */           CompletableFuture<Void> future = plugin.preLoad();
/*  220 */           if (future != null) {
/*  221 */             preLoadFutures.add(future);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } finally {
/*  226 */       this.lock.writeLock().unlock();
/*      */     } 
/*      */ 
/*      */     
/*  230 */     CompletableFuture.allOf((CompletableFuture<?>[])preLoadFutures.toArray(x$0 -> new CompletableFuture[x$0])).join();
/*      */ 
/*      */     
/*  233 */     for (PendingLoadPlugin pendingPlugin : this.loadOrder) {
/*  234 */       PluginBase plugin = this.loading.get(pendingPlugin.getIdentifier());
/*  235 */       if (plugin != null && 
/*  236 */         !setup(plugin)) this.loading.remove(pendingPlugin.getIdentifier()); 
/*      */     } 
/*      */   }
/*      */   
/*      */   public void start() {
/*  241 */     if (this.state != PluginState.SETUP) throw new IllegalStateException("Expected PluginState.SETUP but found " + String.valueOf(this.state)); 
/*  242 */     this.state = PluginState.START;
/*      */ 
/*      */     
/*  245 */     for (PendingLoadPlugin pendingPlugin : this.loadOrder) {
/*  246 */       PluginBase plugin = this.loading.get(pendingPlugin.getIdentifier());
/*  247 */       if (plugin != null && 
/*  248 */         !start(plugin)) this.loading.remove(pendingPlugin.getIdentifier());
/*      */     
/*      */     } 
/*      */     
/*  252 */     this.loadOrder = null;
/*  253 */     this.loading = null;
/*      */     
/*  255 */     StringBuilder sb = new StringBuilder();
/*      */     
/*  257 */     for (Map.Entry<PluginIdentifier, HytaleServerConfig.ModConfig> entry : (Iterable<Map.Entry<PluginIdentifier, HytaleServerConfig.ModConfig>>)HytaleServer.get().getConfig().getModConfig().entrySet()) {
/*  258 */       PluginIdentifier identifier = entry.getKey();
/*  259 */       HytaleServerConfig.ModConfig modConfig = entry.getValue();
/*      */       
/*  261 */       SemverRange requiredVersion = modConfig.getRequiredVersion();
/*  262 */       if (requiredVersion != null && 
/*  263 */         !hasPlugin(identifier, requiredVersion)) {
/*  264 */         sb.append(String.format("%s, Version: %s\n", new Object[] { identifier, modConfig }));
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/*      */     
/*  270 */     if (!sb.isEmpty()) {
/*  271 */       String msg = "Failed to start server! Missing Mods:\n" + String.valueOf(sb);
/*  272 */       LOGGER.at(Level.SEVERE).log(msg);
/*  273 */       HytaleServer.get().shutdownServer(ShutdownReason.MISSING_REQUIRED_PLUGIN.withMessage(msg));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void shutdown() {
/*  280 */     this.state = PluginState.SHUTDOWN;
/*      */     
/*  282 */     LOGGER.at(Level.INFO).log("Saving plugins config...");
/*      */     
/*  284 */     this.lock.writeLock().lock();
/*      */     try {
/*  286 */       ObjectArrayList<PluginBase> objectArrayList = new ObjectArrayList(this.plugins.values());
/*  287 */       for (int i = objectArrayList.size() - 1; i >= 0; i--) {
/*  288 */         PluginBase plugin = objectArrayList.get(i);
/*  289 */         if (plugin.getState() == PluginState.ENABLED) {
/*  290 */           LOGGER.at(Level.FINE).log("Shutting down %s %s", plugin.getType().getDisplayName(), plugin.getIdentifier());
/*  291 */           plugin.shutdown0(true);
/*  292 */           HytaleServer.get().doneStop(plugin);
/*  293 */           LOGGER.at(Level.INFO).log("Shut down plugin %s", plugin.getIdentifier());
/*      */         } 
/*      */       } 
/*  296 */       this.plugins.clear();
/*      */     } finally {
/*  298 */       this.lock.writeLock().unlock();
/*      */     } 
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public PluginState getState() {
/*  304 */     return this.state;
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public PluginBridgeClassLoader getBridgeClassLoader() {
/*  309 */     return this.bridgeClassLoader;
/*      */   }
/*      */   
/*      */   private void validatePluginDeps(@Nonnull PendingLoadPlugin pendingLoadPlugin, @Nullable Map<PluginIdentifier, PendingLoadPlugin> pending) {
/*  313 */     Semver serverVersion = ManifestUtil.getVersion();
/*      */ 
/*      */     
/*  316 */     SemverRange serverVersionRange = pendingLoadPlugin.getManifest().getServerVersion();
/*  317 */     if (serverVersionRange != null && serverVersion != null && !serverVersionRange.satisfies(serverVersion)) {
/*  318 */       throw new MissingPluginDependencyException(String.format("Failed to load '%s' because version of server does not satisfy '%s'! ", new Object[] { pendingLoadPlugin.getIdentifier(), serverVersion }));
/*      */     }
/*      */ 
/*      */     
/*  322 */     for (Map.Entry<PluginIdentifier, SemverRange> entry : (Iterable<Map.Entry<PluginIdentifier, SemverRange>>)pendingLoadPlugin.getManifest().getDependencies().entrySet()) {
/*  323 */       PluginIdentifier identifier = entry.getKey();
/*      */ 
/*      */       
/*  326 */       PluginManifest dependency = null;
/*      */ 
/*      */       
/*  329 */       if (pending != null) {
/*  330 */         PendingLoadPlugin pendingDependency = pending.get(identifier);
/*  331 */         if (pendingDependency != null) {
/*  332 */           dependency = pendingDependency.getManifest();
/*      */         }
/*      */       } 
/*      */       
/*  336 */       if (dependency == null) {
/*      */         
/*  338 */         PluginBase loadedBase = this.plugins.get(identifier);
/*  339 */         if (loadedBase != null) {
/*  340 */           dependency = loadedBase.getManifest();
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  346 */       if (dependency == null) {
/*  347 */         throw new MissingPluginDependencyException(String.format("Failed to load '%s' because the dependency '%s' could not be found!", new Object[] { pendingLoadPlugin.getIdentifier(), identifier }));
/*      */       }
/*      */ 
/*      */       
/*  351 */       SemverRange expectedVersion = entry.getValue();
/*  352 */       if (!dependency.getVersion().satisfies(expectedVersion)) {
/*  353 */         throw new MissingPluginDependencyException(String.format("Failed to load '%s' because version of dependency '%s'(%s) does not satisfy '%s'!", new Object[] { pendingLoadPlugin.getIdentifier(), identifier, dependency.getVersion(), expectedVersion }));
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private void loadPluginsFromDirectory(@Nonnull Map<PluginIdentifier, PendingLoadPlugin> pending, @Nonnull Path path, boolean create, @Nonnull Map<PluginIdentifier, PluginManifest> bootRejectMap) {
/*  359 */     if (!Files.isDirectory(path, new java.nio.file.LinkOption[0])) {
/*  360 */       if (create) {
/*      */         try {
/*  362 */           Files.createDirectories(path, (FileAttribute<?>[])new FileAttribute[0]);
/*  363 */         } catch (IOException e) {
/*  364 */           ((HytaleLogger.Api)LOGGER.at(Level.SEVERE).withCause(e)).log("Failed to create directory: %s", path);
/*      */         } 
/*      */       }
/*      */       
/*      */       return;
/*      */     } 
/*  370 */     LOGGER.at(Level.INFO).log("Loading pending plugins from directory: " + String.valueOf(path)); 
/*  371 */     try { DirectoryStream<Path> stream = Files.newDirectoryStream(path); 
/*  372 */       try { for (Path file : stream) {
/*  373 */           if (!Files.isRegularFile(file, new java.nio.file.LinkOption[0]) || 
/*  374 */             !file.getFileName().toString().toLowerCase().endsWith(".jar"))
/*      */             continue; 
/*  376 */           PendingLoadJavaPlugin plugin = loadPendingJavaPlugin(file);
/*  377 */           if (plugin != null) {
/*  378 */             assert plugin.getPath() != null;
/*  379 */             LOGGER.at(Level.INFO).log("- %s from path %s", plugin.getIdentifier(), path.relativize(plugin.getPath()));
/*  380 */             if (canLoadOnBoot(plugin.getManifest())) {
/*  381 */               loadPendingPlugin(pending, (PendingLoadPlugin)plugin); continue;
/*      */             } 
/*  383 */             bootRejectMap.put(plugin.getIdentifier(), plugin.getManifest());
/*      */           } 
/*      */         } 
/*      */         
/*  387 */         if (stream != null) stream.close();  } catch (Throwable throwable) { if (stream != null) try { stream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException e)
/*  388 */     { ((HytaleLogger.Api)LOGGER.at(Level.SEVERE).withCause(e)).log("Failed to find pending plugins from: %s", path); }
/*      */   
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   private PendingLoadJavaPlugin loadPendingJavaPlugin(@Nonnull Path file) {
/*      */     
/*  395 */     try { URL url = file.toUri().toURL();
/*  396 */       PluginClassLoader pluginClassLoader = this.classLoaders.computeIfAbsent(file, path -> new PluginClassLoader(this, false, new URL[] { url }));
/*  397 */       URL resource = pluginClassLoader.findResource("manifest.json");
/*  398 */       if (resource == null) {
/*  399 */         LOGGER.at(Level.SEVERE).log("Failed to load pending plugin from '%s'. Failed to load manifest file!", file.toString());
/*  400 */         return null;
/*      */       } 
/*  402 */       InputStream stream = resource.openStream(); 
/*  403 */       try { InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8); 
/*  404 */         try { char[] buffer = RawJsonReader.READ_BUFFER.get();
/*  405 */           RawJsonReader rawJsonReader = new RawJsonReader(reader, buffer);
/*  406 */           ExtraInfo extraInfo = ExtraInfo.THREAD_LOCAL.get();
/*  407 */           PluginManifest manifest = (PluginManifest)PluginManifest.CODEC.decodeJson(rawJsonReader, extraInfo);
/*  408 */           extraInfo.getValidationResults().logOrThrowValidatorExceptions(LOGGER);
/*  409 */           PendingLoadJavaPlugin pendingLoadJavaPlugin = new PendingLoadJavaPlugin(file, manifest, pluginClassLoader);
/*  410 */           reader.close(); if (stream != null) stream.close();  return pendingLoadJavaPlugin; } catch (Throwable throwable) { try { reader.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (Throwable throwable) { if (stream != null)
/*  411 */           try { stream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (MalformedURLException e)
/*  412 */     { ((HytaleLogger.Api)LOGGER.at(Level.SEVERE).withCause(e)).log("Failed to load pending plugin from '%s'. Failed to create URLClassLoader!", file.toString()); }
/*  413 */     catch (IOException e)
/*  414 */     { ((HytaleLogger.Api)LOGGER.at(Level.SEVERE).withCause(e)).log("Failed to load pending plugin %s. Failed to load manifest file!", file.toString()); }
/*      */     
/*  416 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   private void loadPluginsInClasspath(@Nonnull Map<PluginIdentifier, PendingLoadPlugin> pending, @Nonnull Map<PluginIdentifier, PluginManifest> rejectedBootList) {
/*  421 */     LOGGER.at(Level.INFO).log("Loading pending classpath plugins!");
/*      */     try {
/*  423 */       URI uri = PluginManager.class.getProtectionDomain().getCodeSource().getLocation().toURI();
/*  424 */       ClassLoader classLoader = PluginManager.class.getClassLoader();
/*      */       try {
/*  426 */         HashSet<URL> manifestUrls = new HashSet<>(Collections.list(classLoader.getResources("manifest.json")));
/*  427 */         for (URL manifestUrl : manifestUrls) {
/*  428 */           URLConnection connection = manifestUrl.openConnection();
/*  429 */           InputStream stream = connection.getInputStream(); 
/*  430 */           try { InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8); 
/*  431 */             try { PendingLoadJavaPlugin plugin; char[] buffer = RawJsonReader.READ_BUFFER.get();
/*  432 */               RawJsonReader rawJsonReader = new RawJsonReader(reader, buffer);
/*  433 */               ExtraInfo extraInfo = ExtraInfo.THREAD_LOCAL.get();
/*  434 */               PluginManifest manifest = (PluginManifest)PluginManifest.CODEC.decodeJson(rawJsonReader, extraInfo);
/*  435 */               extraInfo.getValidationResults().logOrThrowValidatorExceptions(LOGGER);
/*      */ 
/*      */               
/*  438 */               if (connection instanceof JarURLConnection) { JarURLConnection jarURLConnection = (JarURLConnection)connection;
/*  439 */                 URL classpathUrl = jarURLConnection.getJarFileURL();
/*  440 */                 Path path = Path.of(classpathUrl.toURI());
/*  441 */                 PluginClassLoader pluginClassLoader = this.classLoaders.computeIfAbsent(path, f -> new PluginClassLoader(this, true, new URL[] { classpathUrl }));
/*  442 */                 plugin = new PendingLoadJavaPlugin(path, manifest, pluginClassLoader); }
/*      */               else
/*  444 */               { URI pluginUri = manifestUrl.toURI().resolve(".");
/*  445 */                 Path path = Paths.get(pluginUri);
/*  446 */                 URL classpathUrl = pluginUri.toURL();
/*  447 */                 PluginClassLoader pluginClassLoader = this.classLoaders.computeIfAbsent(path, f -> new PluginClassLoader(this, true, new URL[] { classpathUrl }));
/*  448 */                 plugin = new PendingLoadJavaPlugin(path, manifest, pluginClassLoader); }
/*      */ 
/*      */               
/*  451 */               LOGGER.at(Level.INFO).log("- %s", plugin.getIdentifier());
/*  452 */               if (canLoadOnBoot(plugin.getManifest())) {
/*  453 */                 loadPendingPlugin(pending, (PendingLoadPlugin)plugin);
/*      */               } else {
/*  455 */                 rejectedBootList.put(plugin.getIdentifier(), plugin.getManifest());
/*      */               } 
/*  457 */               reader.close(); } catch (Throwable throwable) { try { reader.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  if (stream != null) stream.close();  } catch (Throwable throwable) { if (stream != null)
/*      */               try { stream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }
/*      */         
/*  460 */         }  URL manifestsUrl = classLoader.getResource("manifests.json");
/*  461 */         if (manifestsUrl != null)
/*  462 */         { InputStream stream = manifestsUrl.openStream(); 
/*  463 */           try { InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8); 
/*  464 */             try { char[] buffer = RawJsonReader.READ_BUFFER.get();
/*  465 */               RawJsonReader rawJsonReader = new RawJsonReader(reader, buffer);
/*  466 */               ExtraInfo extraInfo = ExtraInfo.THREAD_LOCAL.get();
/*  467 */               PluginManifest[] manifests = (PluginManifest[])PluginManifest.ARRAY_CODEC.decodeJson(rawJsonReader, extraInfo);
/*  468 */               extraInfo.getValidationResults().logOrThrowValidatorExceptions(LOGGER);
/*      */               
/*  470 */               URL url = uri.toURL();
/*  471 */               Path path = Paths.get(uri);
/*  472 */               PluginClassLoader pluginClassLoader = this.classLoaders.computeIfAbsent(path, f -> new PluginClassLoader(this, true, new URL[] { url }));
/*      */               
/*  474 */               for (PluginManifest manifest : manifests) {
/*  475 */                 PendingLoadJavaPlugin plugin = new PendingLoadJavaPlugin(path, manifest, pluginClassLoader);
/*      */                 
/*  477 */                 LOGGER.at(Level.INFO).log("- %s", plugin.getIdentifier());
/*  478 */                 if (canLoadOnBoot(plugin.getManifest())) {
/*  479 */                   loadPendingPlugin(pending, (PendingLoadPlugin)plugin);
/*      */                 } else {
/*  481 */                   rejectedBootList.put(plugin.getIdentifier(), plugin.getManifest());
/*      */                 } 
/*      */               } 
/*  484 */               reader.close(); } catch (Throwable throwable) { try { reader.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  if (stream != null) stream.close();  } catch (Throwable throwable) { if (stream != null)
/*      */               try { stream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } 
/*  486 */       } catch (IOException e) {
/*  487 */         ((HytaleLogger.Api)LOGGER.at(Level.SEVERE).withCause(e)).log("Failed to load pending classpath plugin from '%s'. Failed to load manifest file!", uri.toString());
/*      */       } 
/*  489 */     } catch (URISyntaxException e) {
/*  490 */       ((HytaleLogger.Api)LOGGER.at(Level.SEVERE).withCause(e)).log("Failed to get jar path!");
/*      */     } 
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public List<PluginBase> getPlugins() {
/*  496 */     this.lock.readLock().lock();
/*      */     try {
/*  498 */       return (List<PluginBase>)new ObjectArrayList(this.plugins.values());
/*      */     } finally {
/*  500 */       this.lock.readLock().unlock();
/*      */     } 
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public PluginBase getPlugin(PluginIdentifier identifier) {
/*  506 */     this.lock.readLock().lock();
/*      */     try {
/*  508 */       return this.plugins.get(identifier);
/*      */     } finally {
/*  510 */       this.lock.readLock().unlock();
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean hasPlugin(PluginIdentifier identifier, @Nonnull SemverRange range) {
/*  515 */     PluginBase plugin = getPlugin(identifier);
/*  516 */     return (plugin != null && plugin.getManifest().getVersion().satisfies(range));
/*      */   }
/*      */   
/*      */   public boolean reload(@Nonnull PluginIdentifier identifier) {
/*  520 */     boolean result = (unload(identifier) && load(identifier));
/*  521 */     this.pluginListPageManager.notifyPluginChange(this.plugins, identifier);
/*  522 */     return result;
/*      */   }
/*      */   
/*      */   public boolean unload(@Nonnull PluginIdentifier identifier) {
/*  526 */     this.lock.writeLock().lock();
/*      */     
/*  528 */     AssetRegistry.ASSET_LOCK.writeLock().lock();
/*      */     try {
/*  530 */       PluginBase plugin = this.plugins.get(identifier);
/*  531 */       if (plugin.getState() == PluginState.ENABLED) {
/*  532 */         plugin.shutdown0(false);
/*  533 */         HytaleServer.get().doneStop(plugin);
/*      */         
/*  535 */         this.plugins.remove(identifier);
/*      */         
/*  537 */         if (plugin instanceof JavaPlugin) { JavaPlugin javaPlugin = (JavaPlugin)plugin;
/*  538 */           unloadJavaPlugin(javaPlugin); }
/*      */ 
/*      */         
/*  541 */         this.pluginListPageManager.notifyPluginChange(this.plugins, identifier);
/*  542 */         return true;
/*      */       } 
/*  544 */       this.pluginListPageManager.notifyPluginChange(this.plugins, identifier);
/*  545 */       return false;
/*      */     } finally {
/*  547 */       AssetRegistry.ASSET_LOCK.writeLock().unlock();
/*  548 */       this.lock.writeLock().unlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void unloadJavaPlugin(JavaPlugin plugin) {
/*  558 */     Path path = plugin.getFile();
/*  559 */     PluginClassLoader classLoader = this.classLoaders.remove(path);
/*  560 */     if (classLoader != null) {
/*      */       try {
/*  562 */         classLoader.close();
/*  563 */       } catch (IOException e) {
/*  564 */         LOGGER.at(Level.SEVERE).log("Failed to close Class Loader for JavaPlugin %s", plugin.getIdentifier());
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean load(@Nonnull PluginIdentifier identifier) {
/*  570 */     this.lock.readLock().lock();
/*      */     try {
/*  572 */       PluginBase plugin = this.plugins.get(identifier);
/*  573 */       if (plugin != null) {
/*  574 */         this.pluginListPageManager.notifyPluginChange(this.plugins, identifier);
/*  575 */         return false;
/*      */       } 
/*      */     } finally {
/*  578 */       this.lock.readLock().unlock();
/*      */     } 
/*  580 */     boolean result = findAndLoadPlugin(identifier);
/*  581 */     this.pluginListPageManager.notifyPluginChange(this.plugins, identifier);
/*  582 */     return result;
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean findAndLoadPlugin(PluginIdentifier identifier) {
/*  587 */     for (PendingLoadPlugin plugin : this.corePlugins) {
/*  588 */       if (plugin.getIdentifier().equals(identifier)) {
/*  589 */         return load(plugin);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*      */     try {
/*  595 */       URI uri = PluginManager.class.getProtectionDomain().getCodeSource().getLocation().toURI();
/*  596 */       ClassLoader classLoader = PluginManager.class.getClassLoader();
/*      */       
/*  598 */       HashSet<URL> manifestUrls = new HashSet<>(Collections.list(classLoader.getResources("manifest.json")));
/*  599 */       for (URL manifestUrl : manifestUrls) {
/*  600 */         InputStream stream = manifestUrl.openStream(); 
/*  601 */         try { InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8); 
/*  602 */           try { char[] buffer = RawJsonReader.READ_BUFFER.get();
/*  603 */             RawJsonReader rawJsonReader = new RawJsonReader(reader, buffer);
/*  604 */             ExtraInfo extraInfo = ExtraInfo.THREAD_LOCAL.get();
/*  605 */             PluginManifest manifest = (PluginManifest)PluginManifest.CODEC.decodeJson(rawJsonReader, extraInfo);
/*  606 */             extraInfo.getValidationResults().logOrThrowValidatorExceptions(LOGGER);
/*      */             
/*  608 */             if (!(new PluginIdentifier(manifest)).equals(identifier))
/*      */             
/*      */             { 
/*      */ 
/*      */               
/*  613 */               reader.close(); if (stream != null) stream.close();  continue; }  PluginClassLoader pluginClassLoader = new PluginClassLoader(this, true, new URL[] { uri.toURL() }); PendingLoadJavaPlugin plugin = new PendingLoadJavaPlugin(Paths.get(uri), manifest, pluginClassLoader); boolean bool = load((PendingLoadPlugin)plugin); reader.close(); if (stream != null) stream.close();  return bool; } catch (Throwable throwable) { try { reader.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (Throwable throwable) { if (stream != null)
/*      */             try { stream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }
/*      */       
/*  616 */       }  URL manifestsUrl = classLoader.getResource("manifests.json");
/*  617 */       if (manifestsUrl != null) {
/*  618 */         InputStream stream = manifestsUrl.openStream(); 
/*  619 */         try { InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8); 
/*  620 */           try { char[] buffer = RawJsonReader.READ_BUFFER.get();
/*  621 */             RawJsonReader rawJsonReader = new RawJsonReader(reader, buffer);
/*  622 */             ExtraInfo extraInfo = ExtraInfo.THREAD_LOCAL.get();
/*  623 */             PluginManifest[] manifests = (PluginManifest[])PluginManifest.ARRAY_CODEC.decodeJson(rawJsonReader, extraInfo);
/*  624 */             extraInfo.getValidationResults().logOrThrowValidatorExceptions(LOGGER); PluginManifest[] arrayOfPluginManifest1; int i;
/*      */             byte b;
/*  626 */             for (arrayOfPluginManifest1 = manifests, i = arrayOfPluginManifest1.length, b = 0; b < i; ) { PluginManifest manifest = arrayOfPluginManifest1[b];
/*  627 */               if (!(new PluginIdentifier(manifest)).equals(identifier)) {
/*      */                 b++; continue;
/*  629 */               }  PluginClassLoader pluginClassLoader = new PluginClassLoader(this, true, new URL[] { uri.toURL() });
/*  630 */               PendingLoadJavaPlugin plugin = new PendingLoadJavaPlugin(Paths.get(uri), manifest, pluginClassLoader);
/*  631 */               boolean bool = load((PendingLoadPlugin)plugin);
/*      */               
/*  633 */               reader.close(); if (stream != null) stream.close();  return bool; }  reader.close(); } catch (Throwable throwable) { try { reader.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  if (stream != null) stream.close();  } catch (Throwable throwable) { if (stream != null)
/*      */             try { stream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*      */               throw throwable; }
/*      */       
/*  637 */       }  Path path = Paths.get(uri).getParent().resolve("builtin");
/*  638 */       if (Files.exists(path, new java.nio.file.LinkOption[0])) {
/*  639 */         try { DirectoryStream<Path> stream = Files.newDirectoryStream(path); 
/*  640 */           try { for (Path file : stream)
/*  641 */             { if (!Files.isRegularFile(file, new java.nio.file.LinkOption[0]) || 
/*  642 */                 !file.getFileName().toString().toLowerCase().endsWith(".jar"))
/*      */                 continue; 
/*  644 */               PluginManifest manifest = loadManifest(file);
/*  645 */               if (manifest != null && (new PluginIdentifier(manifest)).equals(identifier))
/*  646 */               { PendingLoadJavaPlugin pendingLoadJavaPlugin = loadPendingJavaPlugin(file);
/*  647 */                 if (pendingLoadJavaPlugin != null)
/*  648 */                 { boolean bool = load((PendingLoadPlugin)pendingLoadJavaPlugin);
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  653 */                   if (stream != null) stream.close();  return bool; }  break; }  }  if (stream != null) stream.close();  } catch (Throwable throwable) { if (stream != null) try { stream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException e)
/*  654 */         { ((HytaleLogger.Api)LOGGER.at(Level.SEVERE).withCause(e)).log("Failed to find plugins!"); }
/*      */       
/*      */       }
/*  657 */     } catch (IOException|URISyntaxException e) {
/*  658 */       ((HytaleLogger.Api)LOGGER.at(Level.SEVERE).withCause(e)).log("Failed to load pending classpath plugin. Failed to load manifest file!");
/*      */     } 
/*      */ 
/*      */     
/*  662 */     Boolean result = findPluginInDirectory(identifier, MODS_PATH);
/*  663 */     if (result != null) return result.booleanValue();
/*      */ 
/*      */     
/*  666 */     for (Path modsPath : Options.getOptionSet().valuesOf(Options.MODS_DIRECTORIES)) {
/*  667 */       result = findPluginInDirectory(identifier, modsPath);
/*  668 */       if (result != null) return result.booleanValue(); 
/*      */     } 
/*  670 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   private Boolean findPluginInDirectory(@Nonnull PluginIdentifier identifier, @Nonnull Path modsPath) {
/*  682 */     if (!Files.isDirectory(modsPath, new java.nio.file.LinkOption[0])) return null; 
/*      */     
/*  684 */     try { DirectoryStream<Path> stream = Files.newDirectoryStream(modsPath); 
/*  685 */       try { for (Path file : stream)
/*  686 */         { if (!Files.isRegularFile(file, new java.nio.file.LinkOption[0]) || 
/*  687 */             !file.getFileName().toString().toLowerCase().endsWith(".jar"))
/*      */             continue; 
/*  689 */           PluginManifest manifest = loadManifest(file);
/*  690 */           if (manifest != null && (new PluginIdentifier(manifest)).equals(identifier))
/*  691 */           { PendingLoadJavaPlugin pendingLoadJavaPlugin = loadPendingJavaPlugin(file);
/*  692 */             if (pendingLoadJavaPlugin != null)
/*  693 */             { Boolean bool1 = Boolean.valueOf(load((PendingLoadPlugin)pendingLoadJavaPlugin));
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  698 */               if (stream != null) stream.close();  return bool1; }  Boolean bool = Boolean.valueOf(false); if (stream != null) stream.close();  return bool; }  }  if (stream != null) stream.close();  } catch (Throwable throwable) { if (stream != null) try { stream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException e)
/*  699 */     { ((HytaleLogger.Api)LOGGER.at(Level.SEVERE).withCause(e)).log("Failed to find plugins in %s!", modsPath); }
/*      */     
/*  701 */     return null;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   private static PluginManifest loadManifest(@Nonnull Path file) {
/*      */     
/*  707 */     try { URLClassLoader urlClassLoader = new URLClassLoader(new URL[] { file.toUri().toURL() }, PluginManager.class.getClassLoader()); 
/*  708 */       try { InputStream stream = urlClassLoader.findResource("manifest.json").openStream(); 
/*  709 */         try { InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8); 
/*  710 */           try { char[] buffer = RawJsonReader.READ_BUFFER.get();
/*  711 */             RawJsonReader rawJsonReader = new RawJsonReader(reader, buffer);
/*  712 */             ExtraInfo extraInfo = ExtraInfo.THREAD_LOCAL.get();
/*  713 */             PluginManifest manifest = (PluginManifest)PluginManifest.CODEC.decodeJson(rawJsonReader, extraInfo);
/*  714 */             extraInfo.getValidationResults().logOrThrowValidatorExceptions(LOGGER);
/*  715 */             PluginManifest pluginManifest1 = manifest;
/*  716 */             reader.close(); if (stream != null) stream.close(); 
/*  717 */             urlClassLoader.close(); return pluginManifest1; } catch (Throwable throwable) { try { reader.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (Throwable throwable) { if (stream != null)
/*  718 */             try { stream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (Throwable throwable) { try { urlClassLoader.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (IOException e)
/*  719 */     { ((HytaleLogger.Api)LOGGER.at(Level.SEVERE).withCause(e)).log("Failed to load manifest %s.", file);
/*  720 */       return null; }
/*      */   
/*      */   }
/*      */   
/*      */   private boolean load(@Nullable PendingLoadPlugin pendingLoadPlugin) {
/*  725 */     if (pendingLoadPlugin == null) return false; 
/*  726 */     validatePluginDeps(pendingLoadPlugin, null);
/*  727 */     PluginBase plugin = pendingLoadPlugin.load();
/*  728 */     if (plugin != null) {
/*  729 */       this.lock.writeLock().lock();
/*      */       try {
/*  731 */         this.plugins.put(plugin.getIdentifier(), plugin);
/*      */       } finally {
/*  733 */         this.lock.writeLock().unlock();
/*      */       } 
/*  735 */       CompletableFuture<Void> preload = plugin.preLoad();
/*  736 */       if (preload == null) {
/*  737 */         boolean result = (setup(plugin) && start(plugin));
/*  738 */         this.pluginListPageManager.notifyPluginChange(this.plugins, plugin.getIdentifier());
/*  739 */         return result;
/*      */       } 
/*      */       
/*  742 */       preload.thenAccept(v -> {
/*      */             setup(plugin);
/*      */             start(plugin);
/*      */             this.pluginListPageManager.notifyPluginChange(this.plugins, plugin.getIdentifier());
/*      */           });
/*      */     } 
/*  748 */     this.pluginListPageManager.notifyPluginChange(this.plugins, pendingLoadPlugin.getIdentifier());
/*  749 */     return false;
/*      */   }
/*      */   
/*      */   private boolean setup(@Nonnull PluginBase plugin) {
/*  753 */     if (plugin.getState() == PluginState.NONE && dependenciesMatchState(plugin, PluginState.SETUP, PluginState.SETUP)) {
/*  754 */       LOGGER.at(Level.FINE).log("Setting up plugin %s", plugin.getIdentifier());
/*  755 */       boolean prev = AssetStore.DISABLE_DYNAMIC_DEPENDENCIES;
/*  756 */       AssetStore.DISABLE_DYNAMIC_DEPENDENCIES = false;
/*  757 */       plugin.setup0();
/*  758 */       AssetStore.DISABLE_DYNAMIC_DEPENDENCIES = prev;
/*  759 */       AssetModule.get().initPendingStores();
/*  760 */       HytaleServer.get().doneSetup(plugin);
/*      */       
/*  762 */       if (plugin.getState() == PluginState.DISABLED) {
/*  763 */         plugin.shutdown0(false);
/*  764 */         this.plugins.remove(plugin.getIdentifier());
/*      */       } else {
/*  766 */         IEventDispatcher<PluginSetupEvent, PluginSetupEvent> dispatch = HytaleServer.get().getEventBus().dispatchFor(PluginSetupEvent.class, plugin.getClass());
/*  767 */         if (dispatch.hasListener()) dispatch.dispatch((IBaseEvent)new PluginSetupEvent(plugin));
/*      */         
/*  769 */         return true;
/*      */       } 
/*      */     } else {
/*  772 */       plugin.shutdown0(false);
/*  773 */       this.plugins.remove(plugin.getIdentifier());
/*      */     } 
/*  775 */     return false;
/*      */   }
/*      */   
/*      */   private boolean start(@Nonnull PluginBase plugin) {
/*  779 */     if (plugin.getState() == PluginState.SETUP && dependenciesMatchState(plugin, PluginState.ENABLED, PluginState.START)) {
/*  780 */       LOGGER.at(Level.FINE).log("Starting plugin %s", plugin.getIdentifier());
/*  781 */       plugin.start0();
/*  782 */       HytaleServer.get().doneStart(plugin);
/*      */       
/*  784 */       if (plugin.getState() == PluginState.DISABLED) {
/*  785 */         plugin.shutdown0(false);
/*  786 */         this.plugins.remove(plugin.getIdentifier());
/*      */       } else {
/*  788 */         LOGGER.at(Level.INFO).log("Enabled plugin %s", plugin.getIdentifier());
/*  789 */         return true;
/*      */       } 
/*      */     } else {
/*  792 */       plugin.shutdown0(false);
/*  793 */       this.plugins.remove(plugin.getIdentifier());
/*      */     } 
/*  795 */     return false;
/*      */   }
/*      */   
/*      */   private boolean dependenciesMatchState(PluginBase plugin, PluginState requiredState, PluginState stage) {
/*  799 */     Set<PluginIdentifier> dependenciesOnManifest = plugin.getManifest().getDependencies().keySet();
/*  800 */     for (PluginIdentifier dependencyOnManifest : dependenciesOnManifest) {
/*  801 */       PluginBase dependency = this.plugins.get(dependencyOnManifest);
/*  802 */       if (dependency == null || dependency.getState() != requiredState) {
/*  803 */         LOGGER.at(Level.SEVERE).log(plugin.getName() + " is lacking dependency " + plugin.getName() + " at stage " + dependencyOnManifest.getName());
/*  804 */         LOGGER.at(Level.SEVERE).log(plugin.getName() + " DISABLED!");
/*  805 */         return false;
/*      */       } 
/*      */     } 
/*  808 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   private static void loadPendingPlugin(@Nonnull Map<PluginIdentifier, PendingLoadPlugin> pending, @Nonnull PendingLoadPlugin plugin) {
/*  813 */     if (pending.putIfAbsent(plugin.getIdentifier(), plugin) != null) throw new IllegalArgumentException("Tried to load duplicate plugin"); 
/*  814 */     for (PendingLoadPlugin subPlugin : plugin.createSubPendingLoadPlugins()) {
/*  815 */       loadPendingPlugin(pending, subPlugin);
/*      */     }
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public Map<PluginIdentifier, PluginManifest> getAvailablePlugins() {
/*  821 */     return this.availablePlugins;
/*      */   }
/*      */   
/*      */   public ComponentType<EntityStore, PluginListPageManager.SessionSettings> getSessionSettingsComponentType() {
/*  825 */     return this.sessionSettingsComponentType;
/*      */   }
/*      */   public static class PluginBridgeClassLoader extends ClassLoader { private final PluginManager pluginManager;
/*      */     
/*      */     static {
/*  830 */       registerAsParallelCapable();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public PluginBridgeClassLoader(PluginManager pluginManager, ClassLoader parent) {
/*  836 */       super(parent);
/*  837 */       this.pluginManager = pluginManager;
/*      */     }
/*      */ 
/*      */     
/*      */     @Nonnull
/*      */     protected Class<?> loadClass(@Nonnull String name, boolean resolve) throws ClassNotFoundException {
/*  843 */       return loadClass0(name, null);
/*      */     }
/*      */     
/*      */     @Nonnull
/*      */     public Class<?> loadClass0(@Nonnull String name, PluginClassLoader pluginClassLoader) throws ClassNotFoundException {
/*  848 */       this.pluginManager.lock.readLock().lock();
/*      */       try {
/*  850 */         for (Map.Entry<PluginIdentifier, PluginBase> entry : this.pluginManager.plugins.entrySet()) {
/*  851 */           PluginBase pluginBase = entry.getValue();
/*  852 */           Class<?> loadClass = tryGetClass(name, pluginClassLoader, pluginBase);
/*  853 */           if (loadClass != null) return loadClass; 
/*      */         } 
/*      */       } finally {
/*  856 */         this.pluginManager.lock.readLock().unlock();
/*      */       } 
/*  858 */       throw new ClassNotFoundException();
/*      */     }
/*      */     
/*      */     @Nonnull
/*      */     public Class<?> loadClass0(@Nonnull String name, PluginClassLoader pluginClassLoader, @Nonnull PluginManifest manifest) throws ClassNotFoundException {
/*  863 */       this.pluginManager.lock.readLock().lock();
/*      */       try {
/*  865 */         for (PluginIdentifier pluginIdentifier : manifest.getDependencies().keySet()) {
/*  866 */           PluginBase pluginBase = this.pluginManager.plugins.get(pluginIdentifier);
/*  867 */           Class<?> loadClass = tryGetClass(name, pluginClassLoader, pluginBase);
/*  868 */           if (loadClass != null) return loadClass;
/*      */         
/*      */         } 
/*  871 */         for (PluginIdentifier pluginIdentifier : manifest.getOptionalDependencies().keySet()) {
/*  872 */           if (manifest.getDependencies().containsKey(pluginIdentifier))
/*      */             continue; 
/*  874 */           PluginBase pluginBase = this.pluginManager.plugins.get(pluginIdentifier);
/*  875 */           if (pluginBase == null)
/*      */             continue; 
/*  877 */           Class<?> loadClass = tryGetClass(name, pluginClassLoader, pluginBase);
/*  878 */           if (loadClass != null) return loadClass;
/*      */         
/*      */         } 
/*  881 */         for (Map.Entry<PluginIdentifier, PluginBase> entry : this.pluginManager.plugins.entrySet()) {
/*  882 */           if (manifest.getDependencies().containsKey(entry.getKey()) || 
/*  883 */             manifest.getOptionalDependencies().containsKey(entry.getKey()))
/*      */             continue; 
/*  885 */           PluginBase pluginBase = entry.getValue();
/*  886 */           Class<?> loadClass = tryGetClass(name, pluginClassLoader, pluginBase);
/*  887 */           if (loadClass != null) return loadClass; 
/*      */         } 
/*      */       } finally {
/*  890 */         this.pluginManager.lock.readLock().unlock();
/*      */       } 
/*  892 */       throw new ClassNotFoundException();
/*      */     }
/*      */     
/*      */     public static Class<?> tryGetClass(@Nonnull String name, PluginClassLoader pluginClassLoader, PluginBase pluginBase) {
/*  896 */       if (!(pluginBase instanceof JavaPlugin)) return null;
/*      */       
/*      */       try {
/*  899 */         PluginClassLoader classLoader = ((JavaPlugin)pluginBase).getClassLoader();
/*  900 */         if (classLoader != pluginClassLoader) {
/*  901 */           Class<?> loadClass = classLoader.loadLocalClass(name);
/*  902 */           if (loadClass != null) return loadClass; 
/*      */         } 
/*  904 */       } catch (ClassNotFoundException classNotFoundException) {}
/*      */       
/*  906 */       return null;
/*      */     }
/*      */     
/*      */     @Nullable
/*      */     public URL getResource0(@Nonnull String name, @Nullable PluginClassLoader pluginClassLoader) {
/*  911 */       this.pluginManager.lock.readLock().lock();
/*      */       try {
/*  913 */         for (Map.Entry<PluginIdentifier, PluginBase> entry : this.pluginManager.plugins.entrySet()) {
/*  914 */           URL resource = tryGetResource(name, pluginClassLoader, entry.getValue());
/*  915 */           if (resource != null) return resource; 
/*      */         } 
/*      */       } finally {
/*  918 */         this.pluginManager.lock.readLock().unlock();
/*      */       } 
/*  920 */       return null;
/*      */     }
/*      */     
/*      */     @Nullable
/*      */     public URL getResource0(@Nonnull String name, @Nullable PluginClassLoader pluginClassLoader, @Nonnull PluginManifest manifest) {
/*  925 */       this.pluginManager.lock.readLock().lock();
/*      */       try {
/*  927 */         for (PluginIdentifier pluginIdentifier : manifest.getDependencies().keySet()) {
/*  928 */           URL resource = tryGetResource(name, pluginClassLoader, this.pluginManager.plugins.get(pluginIdentifier));
/*  929 */           if (resource != null) return resource;
/*      */         
/*      */         } 
/*  932 */         for (PluginIdentifier pluginIdentifier : manifest.getOptionalDependencies().keySet()) {
/*  933 */           if (manifest.getDependencies().containsKey(pluginIdentifier))
/*      */             continue; 
/*  935 */           PluginBase pluginBase = this.pluginManager.plugins.get(pluginIdentifier);
/*  936 */           if (pluginBase == null)
/*      */             continue; 
/*  938 */           URL resource = tryGetResource(name, pluginClassLoader, pluginBase);
/*  939 */           if (resource != null) return resource;
/*      */         
/*      */         } 
/*  942 */         for (Map.Entry<PluginIdentifier, PluginBase> entry : this.pluginManager.plugins.entrySet()) {
/*  943 */           if (manifest.getDependencies().containsKey(entry.getKey()) || 
/*  944 */             manifest.getOptionalDependencies().containsKey(entry.getKey()))
/*      */             continue; 
/*  946 */           URL resource = tryGetResource(name, pluginClassLoader, entry.getValue());
/*  947 */           if (resource != null) return resource; 
/*      */         } 
/*      */       } finally {
/*  950 */         this.pluginManager.lock.readLock().unlock();
/*      */       } 
/*  952 */       return null;
/*      */     }
/*      */     
/*      */     @Nonnull
/*      */     public Enumeration<URL> getResources0(@Nonnull String name, @Nullable PluginClassLoader pluginClassLoader) {
/*  957 */       ObjectArrayList<URL> results = new ObjectArrayList();
/*  958 */       this.pluginManager.lock.readLock().lock();
/*      */       try {
/*  960 */         for (Map.Entry<PluginIdentifier, PluginBase> entry : this.pluginManager.plugins.entrySet()) {
/*  961 */           URL resource = tryGetResource(name, pluginClassLoader, entry.getValue());
/*  962 */           if (resource != null) results.add(resource); 
/*      */         } 
/*      */       } finally {
/*  965 */         this.pluginManager.lock.readLock().unlock();
/*      */       } 
/*  967 */       return Collections.enumeration((Collection<URL>)results);
/*      */     }
/*      */     
/*      */     @Nonnull
/*      */     public Enumeration<URL> getResources0(@Nonnull String name, @Nullable PluginClassLoader pluginClassLoader, @Nonnull PluginManifest manifest) {
/*  972 */       ObjectArrayList<URL> results = new ObjectArrayList();
/*  973 */       this.pluginManager.lock.readLock().lock();
/*      */       try {
/*  975 */         for (PluginIdentifier pluginIdentifier : manifest.getDependencies().keySet()) {
/*  976 */           URL resource = tryGetResource(name, pluginClassLoader, this.pluginManager.plugins.get(pluginIdentifier));
/*  977 */           if (resource != null) results.add(resource);
/*      */         
/*      */         } 
/*  980 */         for (PluginIdentifier pluginIdentifier : manifest.getOptionalDependencies().keySet()) {
/*  981 */           if (manifest.getDependencies().containsKey(pluginIdentifier))
/*      */             continue; 
/*  983 */           PluginBase pluginBase = this.pluginManager.plugins.get(pluginIdentifier);
/*  984 */           if (pluginBase == null)
/*      */             continue; 
/*  986 */           URL resource = tryGetResource(name, pluginClassLoader, pluginBase);
/*  987 */           if (resource != null) results.add(resource);
/*      */         
/*      */         } 
/*  990 */         for (Map.Entry<PluginIdentifier, PluginBase> entry : this.pluginManager.plugins.entrySet()) {
/*  991 */           if (manifest.getDependencies().containsKey(entry.getKey()) || 
/*  992 */             manifest.getOptionalDependencies().containsKey(entry.getKey()))
/*      */             continue; 
/*  994 */           URL resource = tryGetResource(name, pluginClassLoader, entry.getValue());
/*  995 */           if (resource != null) results.add(resource); 
/*      */         } 
/*      */       } finally {
/*  998 */         this.pluginManager.lock.readLock().unlock();
/*      */       } 
/* 1000 */       return Collections.enumeration((Collection<URL>)results);
/*      */     }
/*      */     @Nullable
/*      */     private static URL tryGetResource(@Nonnull String name, @Nullable PluginClassLoader pluginClassLoader, @Nullable PluginBase pluginBase) {
/*      */       JavaPlugin javaPlugin;
/* 1005 */       if (pluginBase instanceof JavaPlugin) { javaPlugin = (JavaPlugin)pluginBase; } else { return null; }
/*      */       
/* 1007 */       PluginClassLoader classLoader = javaPlugin.getClassLoader();
/* 1008 */       if (classLoader != pluginClassLoader) {
/* 1009 */         return classLoader.findResource(name);
/*      */       }
/* 1011 */       return null;
/*      */     } }
/*      */ 
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\plugin\PluginManager.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */