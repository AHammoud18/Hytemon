/*      */ package com.hypixel.hytale.server.core;
/*      */ 
/*      */ import com.hypixel.hytale.codec.Codec;
/*      */ import com.hypixel.hytale.codec.DocumentContainingCodec;
/*      */ import com.hypixel.hytale.codec.ExtraInfo;
/*      */ import com.hypixel.hytale.codec.KeyedCodec;
/*      */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*      */ import com.hypixel.hytale.codec.codecs.map.MapCodec;
/*      */ import com.hypixel.hytale.codec.codecs.map.ObjectMapCodec;
/*      */ import com.hypixel.hytale.codec.lookup.Priority;
/*      */ import com.hypixel.hytale.codec.util.RawJsonReader;
/*      */ import com.hypixel.hytale.common.plugin.PluginIdentifier;
/*      */ import com.hypixel.hytale.common.semver.SemverRange;
/*      */ import com.hypixel.hytale.logger.HytaleLogger;
/*      */ import com.hypixel.hytale.protocol.GameMode;
/*      */ import com.hypixel.hytale.server.core.auth.AuthCredentialStoreProvider;
/*      */ import com.hypixel.hytale.server.core.codec.ProtocolCodecs;
/*      */ import com.hypixel.hytale.server.core.universe.playerdata.DefaultPlayerStorageProvider;
/*      */ import com.hypixel.hytale.server.core.universe.playerdata.DiskPlayerStorageProvider;
/*      */ import com.hypixel.hytale.server.core.universe.playerdata.PlayerStorageProvider;
/*      */ import com.hypixel.hytale.server.core.util.BsonUtil;
/*      */ import java.nio.file.Files;
/*      */ import java.nio.file.Path;
/*      */ import java.time.Duration;
/*      */ import java.time.temporal.ChronoUnit;
/*      */ import java.util.Collections;
/*      */ import java.util.Map;
/*      */ import java.util.Optional;
/*      */ import java.util.concurrent.CompletableFuture;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import java.util.concurrent.atomic.AtomicBoolean;
/*      */ import java.util.function.Supplier;
/*      */ import java.util.logging.Level;
/*      */ import javax.annotation.Nonnull;
/*      */ import javax.annotation.Nullable;
/*      */ import org.bson.BsonDocument;
/*      */ import org.bson.BsonValue;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class HytaleServerConfig
/*      */ {
/*      */   public static final int VERSION = 3;
/*      */   public static final int DEFAULT_MAX_VIEW_RADIUS = 32;
/*      */   @Nonnull
/*   59 */   public static final Path PATH = Path.of("config.json", new String[0]);
/*      */   
/*      */   @Nonnull
/*      */   public static final BuilderCodec<HytaleServerConfig> CODEC;
/*      */   
/*      */   static {
/*   65 */     PlayerStorageProvider.CODEC.register(Priority.DEFAULT, "Hytale", DefaultPlayerStorageProvider.class, (Codec)DefaultPlayerStorageProvider.CODEC);
/*   66 */     PlayerStorageProvider.CODEC.register("Disk", DiskPlayerStorageProvider.class, (Codec)DiskPlayerStorageProvider.CODEC);
/*      */ 
/*      */     
/*   69 */     Module.BUILDER_CODEC_BUILDER.addField(new KeyedCodec("Modules", (Codec)new MapCodec((Codec)Module.CODEC, ConcurrentHashMap::new, false)), (o, m) -> o.modules = m, o -> o.modules);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  186 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(HytaleServerConfig.class, HytaleServerConfig::new).versioned()).codecVersion(3)).append(new KeyedCodec("ServerName", (Codec)Codec.STRING), (o, s) -> o.serverName = s, o -> o.serverName).add()).append(new KeyedCodec("MOTD", (Codec)Codec.STRING), (o, s) -> o.motd = s, o -> o.motd).add()).append(new KeyedCodec("Password", (Codec)Codec.STRING), (o, s) -> o.password = s, o -> o.password).add()).append(new KeyedCodec("MaxPlayers", (Codec)Codec.INTEGER), (o, i) -> o.maxPlayers = i.intValue(), o -> Integer.valueOf(o.maxPlayers)).add()).append(new KeyedCodec("MaxViewRadius", (Codec)Codec.INTEGER), (o, i) -> o.maxViewRadius = i.intValue(), o -> Integer.valueOf(o.maxViewRadius)).add()).append(new KeyedCodec("LocalCompressionEnabled", (Codec)Codec.BOOLEAN), (o, i) -> o.localCompressionEnabled = i.booleanValue(), o -> Boolean.valueOf(o.localCompressionEnabled)).add()).append(new KeyedCodec("Defaults", (Codec)Defaults.CODEC), (o, obj) -> o.defaults = obj, o -> o.defaults).add()).append(new KeyedCodec("ConnectionTimeouts", ConnectionTimeouts.CODEC), (o, m) -> o.connectionTimeouts = m, o -> o.connectionTimeouts).add()).append(new KeyedCodec("RateLimit", RateLimitConfig.CODEC), (o, m) -> o.rateLimitConfig = m, o -> o.rateLimitConfig).add()).append(new KeyedCodec("Modules", (Codec)new MapCodec((Codec)Module.CODEC, ConcurrentHashMap::new, false)), (o, m) -> { o.modules = m; o.unmodifiableModules = Collections.unmodifiableMap(m); }o -> o.modules).add()).append(new KeyedCodec("LogLevels", (Codec)new MapCodec((Codec)Codec.LOG_LEVEL, ConcurrentHashMap::new, false)), (o, m) -> { o.logLevels = m; o.unmodifiableLogLevels = Collections.unmodifiableMap(o.logLevels); }o -> o.logLevels).add()).append(new KeyedCodec("Plugins", (Codec)new ObjectMapCodec((Codec)ModConfig.CODEC, it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap::new, PluginIdentifier::toString, PluginIdentifier::fromString, false)), (o, i) -> o.legacyPluginConfig = i, o -> null).setVersionRange(0, 2).add()).append(new KeyedCodec("Mods", (Codec)new ObjectMapCodec((Codec)ModConfig.CODEC, ConcurrentHashMap::new, PluginIdentifier::toString, PluginIdentifier::fromString, false)), (o, i) -> o.modConfig = i, o -> o.modConfig).add()).append(new KeyedCodec("DisplayTmpTagsInStrings", (Codec)Codec.BOOLEAN), (o, displayTmpTagsInStrings) -> o.displayTmpTagsInStrings = displayTmpTagsInStrings.booleanValue(), o -> Boolean.valueOf(o.displayTmpTagsInStrings)).add()).append(new KeyedCodec("PlayerStorage", (Codec)PlayerStorageProvider.CODEC), (o, obj) -> o.playerStorageProvider = obj, o -> o.playerStorageProvider).add()).append(new KeyedCodec("AuthCredentialStore", (Codec)Codec.BSON_DOCUMENT), (o, value) -> o.authCredentialStoreConfig = value, o -> o.authCredentialStoreConfig).add()).afterDecode(config -> { config.defaults.hytaleServerConfig = config; config.connectionTimeouts.hytaleServerConfig = config; config.rateLimitConfig.hytaleServerConfig = config; config.modules.values().forEach(()); if (config.legacyPluginConfig != null && !config.legacyPluginConfig.isEmpty()) { for (Map.Entry<PluginIdentifier, ModConfig> entry : config.legacyPluginConfig.entrySet()) config.modConfig.putIfAbsent(entry.getKey(), entry.getValue());  config.legacyPluginConfig = null; config.markChanged(); }  })).build();
/*      */   }
/*      */ 
/*      */   
/*      */   @Nonnull
/*  191 */   private final transient AtomicBoolean hasChanged = new AtomicBoolean();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  197 */   private String serverName = "Hytale Server";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  202 */   private String motd = "";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  207 */   private String password = "";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  212 */   private int maxPlayers = 100;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  217 */   private int maxViewRadius = 32;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean localCompressionEnabled;
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*  227 */   private Defaults defaults = new Defaults(this);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*  233 */   private ConnectionTimeouts connectionTimeouts = new ConnectionTimeouts(this);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*  239 */   private RateLimitConfig rateLimitConfig = new RateLimitConfig(this);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*  245 */   private Map<String, Module> modules = new ConcurrentHashMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*  252 */   private Map<String, Level> logLevels = Collections.emptyMap();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   private transient Map<PluginIdentifier, ModConfig> legacyPluginConfig;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*  265 */   private Map<PluginIdentifier, ModConfig> modConfig = new ConcurrentHashMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*  272 */   private Map<String, Module> unmodifiableModules = Collections.unmodifiableMap(this.modules);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*  278 */   private Map<String, Level> unmodifiableLogLevels = Collections.unmodifiableMap(this.logLevels);
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*  283 */   private PlayerStorageProvider playerStorageProvider = (PlayerStorageProvider)PlayerStorageProvider.CODEC
/*  284 */     .getDefault();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*  290 */   private BsonDocument authCredentialStoreConfig = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*  296 */   private transient AuthCredentialStoreProvider authCredentialStoreProvider = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean displayTmpTagsInStrings;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getServerName() {
/*  308 */     return this.serverName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setServerName(@Nonnull String serverName) {
/*  317 */     this.serverName = serverName;
/*  318 */     markChanged();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getMotd() {
/*  325 */     return this.motd;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMotd(@Nonnull String motd) {
/*  334 */     this.motd = motd;
/*  335 */     markChanged();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getPassword() {
/*  342 */     return this.password;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPassword(@Nonnull String password) {
/*  351 */     this.password = password;
/*  352 */     markChanged();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isDisplayTmpTagsInStrings() {
/*  359 */     return this.displayTmpTagsInStrings;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDisplayTmpTagsInStrings(boolean displayTmpTagsInStrings) {
/*  368 */     this.displayTmpTagsInStrings = displayTmpTagsInStrings;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxPlayers() {
/*  375 */     return this.maxPlayers;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMaxPlayers(int maxPlayers) {
/*  384 */     this.maxPlayers = maxPlayers;
/*  385 */     markChanged();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxViewRadius() {
/*  392 */     return this.maxViewRadius;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMaxViewRadius(int maxViewRadius) {
/*  401 */     this.maxViewRadius = maxViewRadius;
/*  402 */     markChanged();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isLocalCompressionEnabled() {
/*  409 */     return this.localCompressionEnabled;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLocalCompressionEnabled(boolean localCompression) {
/*  418 */     this.localCompressionEnabled = localCompression;
/*  419 */     markChanged();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public Defaults getDefaults() {
/*  427 */     return this.defaults;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDefaults(@Nonnull Defaults defaults) {
/*  436 */     this.defaults = defaults;
/*  437 */     markChanged();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public ConnectionTimeouts getConnectionTimeouts() {
/*  445 */     return this.connectionTimeouts;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setConnectionTimeouts(@Nonnull ConnectionTimeouts connectionTimeouts) {
/*  454 */     this.connectionTimeouts = connectionTimeouts;
/*  455 */     markChanged();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public RateLimitConfig getRateLimitConfig() {
/*  463 */     return this.rateLimitConfig;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRateLimitConfig(@Nonnull RateLimitConfig rateLimitConfig) {
/*  472 */     this.rateLimitConfig = rateLimitConfig;
/*  473 */     markChanged();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public Map<String, Module> getModules() {
/*  481 */     return this.unmodifiableModules;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public Module getModule(String moduleName) {
/*  492 */     return this.modules.computeIfAbsent(moduleName, k -> new Module(this));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setModules(@Nonnull Map<String, Module> modules) {
/*  501 */     this.modules = modules;
/*  502 */     markChanged();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public Map<String, Level> getLogLevels() {
/*  510 */     return this.unmodifiableLogLevels;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLogLevels(@Nonnull Map<String, Level> logLevels) {
/*  519 */     this.logLevels = logLevels;
/*  520 */     markChanged();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public Map<PluginIdentifier, ModConfig> getModConfig() {
/*  528 */     return this.modConfig;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setModConfig(@Nonnull Map<PluginIdentifier, ModConfig> modConfig) {
/*  537 */     this.modConfig = modConfig;
/*  538 */     markChanged();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public PlayerStorageProvider getPlayerStorageProvider() {
/*  546 */     return this.playerStorageProvider;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPlayerStorageProvider(@Nonnull PlayerStorageProvider playerStorageProvider) {
/*  555 */     this.playerStorageProvider = playerStorageProvider;
/*  556 */     markChanged();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public AuthCredentialStoreProvider getAuthCredentialStoreProvider() {
/*  567 */     if (this.authCredentialStoreProvider != null) {
/*  568 */       return this.authCredentialStoreProvider;
/*      */     }
/*      */     
/*  571 */     if (this.authCredentialStoreConfig != null) {
/*  572 */       this.authCredentialStoreProvider = (AuthCredentialStoreProvider)AuthCredentialStoreProvider.CODEC.decode((BsonValue)this.authCredentialStoreConfig);
/*      */     } else {
/*  574 */       this.authCredentialStoreProvider = (AuthCredentialStoreProvider)AuthCredentialStoreProvider.CODEC.getDefault();
/*      */     } 
/*      */     
/*  577 */     return this.authCredentialStoreProvider;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAuthCredentialStoreProvider(@Nonnull AuthCredentialStoreProvider provider) {
/*  586 */     this.authCredentialStoreProvider = provider;
/*  587 */     this.authCredentialStoreConfig = (BsonDocument)AuthCredentialStoreProvider.CODEC.encode(provider);
/*  588 */     markChanged();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeModule(@Nonnull String module) {
/*  597 */     this.modules.remove(module);
/*  598 */     markChanged();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void markChanged() {
/*  605 */     this.hasChanged.set(true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean consumeHasChanged() {
/*  612 */     return this.hasChanged.getAndSet(false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public static HytaleServerConfig load() {
/*  620 */     return load(PATH);
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
/*      */   @Nonnull
/*      */   public static HytaleServerConfig load(@Nonnull Path path) {
/*  633 */     if (!Files.isRegularFile(path, new java.nio.file.LinkOption[0])) {
/*  634 */       HytaleServerConfig hytaleServerConfig = new HytaleServerConfig();
/*      */ 
/*      */       
/*  637 */       if (!Options.getOptionSet().has(Options.BARE)) {
/*  638 */         save(hytaleServerConfig).join();
/*      */       }
/*  640 */       return hytaleServerConfig;
/*      */     } 
/*      */     
/*      */     try {
/*  644 */       HytaleServerConfig config = (HytaleServerConfig)RawJsonReader.readSyncWithBak(path, (Codec)CODEC, HytaleLogger.getLogger());
/*  645 */       if (config == null) {
/*  646 */         throw new RuntimeException("Failed to load server config from " + String.valueOf(path));
/*      */       }
/*  648 */       return config;
/*  649 */     } catch (Exception e) {
/*  650 */       throw new RuntimeException("Failed to read server config!", e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public static CompletableFuture<Void> save(@Nonnull HytaleServerConfig hytaleServerConfig) {
/*  662 */     return save(PATH, hytaleServerConfig);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public static CompletableFuture<Void> save(@Nonnull Path path, @Nonnull HytaleServerConfig hytaleServerConfig) {
/*  674 */     BsonDocument document = CODEC.encode(hytaleServerConfig, ExtraInfo.THREAD_LOCAL.get()).asDocument();
/*  675 */     return BsonUtil.writeDocument(path, document);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class Module
/*      */   {
/*      */     @Nonnull
/*      */     protected static BuilderCodec.Builder<Module> BUILDER_CODEC_BUILDER;
/*      */ 
/*      */ 
/*      */     
/*      */     static {
/*  689 */       BUILDER_CODEC_BUILDER = (BuilderCodec.Builder<Module>)BuilderCodec.builder(Module.class, Module::new).addField(new KeyedCodec("Enabled", (Codec)Codec.BOOLEAN), (o, i) -> o.enabled = i, o -> o.enabled);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     @Nonnull
/*  695 */     protected static BuilderCodec<Module> BUILDER_CODEC = BUILDER_CODEC_BUILDER.build(); @Nonnull
/*      */     public static final DocumentContainingCodec<Module> CODEC;
/*      */     private transient HytaleServerConfig hytaleServerConfig;
/*      */     private Boolean enabled;
/*      */     
/*      */     static {
/*  701 */       CODEC = new DocumentContainingCodec(BUILDER_CODEC, (o, i) -> o.document = i, o -> o.document);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Nonnull
/*  717 */     private Map<String, Module> modules = new ConcurrentHashMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Nonnull
/*  723 */     private BsonDocument document = new BsonDocument();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Module(@Nonnull HytaleServerConfig hytaleServerConfig) {
/*  740 */       this.hytaleServerConfig = hytaleServerConfig;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isEnabled(boolean def) {
/*  750 */       return (this.enabled != null) ? this.enabled.booleanValue() : def;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setEnabled(boolean enabled) {
/*  759 */       this.enabled = Boolean.valueOf(enabled);
/*  760 */       this.hytaleServerConfig.markChanged();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Boolean getEnabled() {
/*  767 */       return this.enabled;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Nonnull
/*      */     public Map<String, Module> getModules() {
/*  775 */       return Collections.unmodifiableMap(this.modules);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Nonnull
/*      */     public Module getModule(@Nonnull String moduleName) {
/*  786 */       return this.modules.computeIfAbsent(moduleName, k -> new Module(this.hytaleServerConfig));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setModules(@Nonnull Map<String, Module> modules) {
/*  795 */       this.modules = modules;
/*  796 */       this.hytaleServerConfig.markChanged();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Nonnull
/*      */     public BsonDocument getDocument() {
/*  804 */       return this.document;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Nullable
/*      */     public <T> T decode(@Nonnull Codec<T> codec) {
/*  816 */       return (T)codec.decode((BsonValue)this.document);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public <T> void encode(@Nonnull Codec<T> codec, @Nonnull T t) {
/*  827 */       this.document = codec.encode(t).asDocument();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Nonnull
/*      */     public <T> Optional<T> getData(@Nonnull KeyedCodec<T> keyedCodec) {
/*  839 */       return keyedCodec.get(this.document);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Nullable
/*      */     public <T> T getDataOrNull(@Nonnull KeyedCodec<T> keyedCodec) {
/*  851 */       return (T)keyedCodec.getOrNull(this.document);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public <T> T getDataNow(@Nonnull KeyedCodec<T> keyedCodec) {
/*  862 */       return (T)keyedCodec.getNow(this.document);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public <T> void put(@Nonnull KeyedCodec<T> keyedCodec, T t) {
/*  873 */       keyedCodec.put(this.document, t);
/*  874 */       this.hytaleServerConfig.markChanged();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setDocument(@Nonnull BsonDocument document) {
/*  883 */       this.document = document;
/*  884 */       this.hytaleServerConfig.markChanged();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void setHytaleServerConfig(@Nonnull HytaleServerConfig hytaleServerConfig) {
/*  893 */       this.hytaleServerConfig = hytaleServerConfig;
/*  894 */       this.modules.values().forEach(module -> module.setHytaleServerConfig(hytaleServerConfig));
/*      */     }
/*      */     
/*      */     private Module() {} }
/*      */   
/*  899 */   public static class Defaults { public static final KeyedCodec<String> WORLD = new KeyedCodec("World", (Codec)Codec.STRING);
/*  900 */     public static final KeyedCodec<GameMode> GAMEMODE = new KeyedCodec("GameMode", (Codec)ProtocolCodecs.GAMEMODE_LEGACY);
/*      */     public static final BuilderCodec<Defaults> CODEC;
/*      */     private transient HytaleServerConfig hytaleServerConfig;
/*      */     
/*      */     static {
/*  905 */       CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(Defaults.class, Defaults::new).addField(WORLD, (o, i) -> o.world = i, o -> o.world)).addField(GAMEMODE, (o, s) -> o.gameMode = s, o -> o.gameMode)).build();
/*      */     }
/*      */ 
/*      */     
/*  909 */     private String world = "default";
/*  910 */     private GameMode gameMode = GameMode.Adventure;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Defaults(HytaleServerConfig hytaleServerConfig) {
/*  916 */       this.hytaleServerConfig = hytaleServerConfig;
/*      */     }
/*      */     
/*      */     public String getWorld() {
/*  920 */       return this.world;
/*      */     }
/*      */     
/*      */     public void setWorld(String world) {
/*  924 */       this.world = world;
/*  925 */       this.hytaleServerConfig.markChanged();
/*      */     }
/*      */     
/*      */     public GameMode getGameMode() {
/*  929 */       return this.gameMode;
/*      */     }
/*      */     
/*      */     public void setGameMode(GameMode gameMode) {
/*  933 */       this.gameMode = gameMode;
/*  934 */       this.hytaleServerConfig.markChanged();
/*      */     }
/*      */     
/*      */     private Defaults() {} }
/*      */   
/*  939 */   public static class ConnectionTimeouts { public static final Duration DEFAULT_INITIAL_TIMEOUT = Duration.of(10L, ChronoUnit.SECONDS);
/*  940 */     public static final Duration DEFAULT_AUTH_TIMEOUT = Duration.of(30L, ChronoUnit.SECONDS);
/*  941 */     public static final Duration DEFAULT_PLAY_TIMEOUT = Duration.of(1L, ChronoUnit.MINUTES);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static final Codec<ConnectionTimeouts> CODEC;
/*      */ 
/*      */ 
/*      */     
/*      */     private Duration initialTimeout;
/*      */ 
/*      */ 
/*      */     
/*      */     private Duration authTimeout;
/*      */ 
/*      */ 
/*      */     
/*      */     private Duration playTimeout;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static {
/*  964 */       CODEC = (Codec<ConnectionTimeouts>)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ConnectionTimeouts.class, ConnectionTimeouts::new).addField(new KeyedCodec("InitialTimeout", (Codec)Codec.DURATION), (o, d) -> o.initialTimeout = d, o -> o.initialTimeout)).addField(new KeyedCodec("AuthTimeout", (Codec)Codec.DURATION), (o, d) -> o.authTimeout = d, o -> o.authTimeout)).addField(new KeyedCodec("PlayTimeout", (Codec)Codec.DURATION), (o, d) -> o.playTimeout = d, o -> o.playTimeout)).addField(new KeyedCodec("JoinTimeouts", (Codec)new MapCodec((Codec)Codec.DURATION, ConcurrentHashMap::new, false)), (o, m) -> o.joinTimeouts = m, o -> o.joinTimeouts)).build();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  969 */     private Map<String, Duration> joinTimeouts = new ConcurrentHashMap<>();
/*      */     @Nonnull
/*  971 */     private Map<String, Duration> unmodifiableJoinTimeouts = Collections.unmodifiableMap(this.joinTimeouts);
/*      */ 
/*      */     
/*      */     private transient HytaleServerConfig hytaleServerConfig;
/*      */ 
/*      */ 
/*      */     
/*      */     public ConnectionTimeouts(HytaleServerConfig hytaleServerConfig) {
/*  979 */       this.hytaleServerConfig = hytaleServerConfig;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Duration getInitialTimeout() {
/*  987 */       return (this.initialTimeout != null) ? this.initialTimeout : DEFAULT_INITIAL_TIMEOUT;
/*      */     }
/*      */     
/*      */     public void setInitialTimeout(Duration initialTimeout) {
/*  991 */       this.initialTimeout = initialTimeout;
/*  992 */       this.hytaleServerConfig.markChanged();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Duration getAuthTimeout() {
/* 1000 */       return (this.authTimeout != null) ? this.authTimeout : DEFAULT_AUTH_TIMEOUT;
/*      */     }
/*      */     
/*      */     public void setAuthTimeout(Duration authTimeout) {
/* 1004 */       this.authTimeout = authTimeout;
/* 1005 */       this.hytaleServerConfig.markChanged();
/*      */     }
/*      */     
/*      */     public Duration getPlayTimeout() {
/* 1009 */       return (this.playTimeout != null) ? this.playTimeout : DEFAULT_PLAY_TIMEOUT;
/*      */     }
/*      */     
/*      */     public void setPlayTimeout(Duration playTimeout) {
/* 1013 */       this.playTimeout = playTimeout;
/* 1014 */       this.hytaleServerConfig.markChanged();
/*      */     }
/*      */     
/*      */     @Nonnull
/*      */     public Map<String, Duration> getJoinTimeouts() {
/* 1019 */       return this.unmodifiableJoinTimeouts;
/*      */     }
/*      */     
/*      */     public void setJoinTimeouts(Map<String, Duration> joinTimeouts) {
/* 1023 */       this.joinTimeouts = joinTimeouts;
/* 1024 */       this.hytaleServerConfig.markChanged();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public ConnectionTimeouts() {} }
/*      */ 
/*      */ 
/*      */   
/*      */   public static class RateLimitConfig
/*      */   {
/*      */     public static final int DEFAULT_PACKETS_PER_SECOND = 2000;
/*      */     
/*      */     public static final int DEFAULT_BURST_CAPACITY = 500;
/*      */     
/*      */     public static final Codec<RateLimitConfig> CODEC;
/*      */     
/*      */     private Boolean enabled;
/*      */     
/*      */     private Integer packetsPerSecond;
/*      */     
/*      */     private Integer burstCapacity;
/*      */     
/*      */     transient HytaleServerConfig hytaleServerConfig;
/*      */ 
/*      */     
/*      */     static {
/* 1051 */       CODEC = (Codec<RateLimitConfig>)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(RateLimitConfig.class, RateLimitConfig::new).addField(new KeyedCodec("Enabled", (Codec)Codec.BOOLEAN), (o, b) -> o.enabled = b, o -> o.enabled)).addField(new KeyedCodec("PacketsPerSecond", (Codec)Codec.INTEGER), (o, i) -> o.packetsPerSecond = i, o -> o.packetsPerSecond)).addField(new KeyedCodec("BurstCapacity", (Codec)Codec.INTEGER), (o, i) -> o.burstCapacity = i, o -> o.burstCapacity)).build();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public RateLimitConfig() {}
/*      */ 
/*      */ 
/*      */     
/*      */     public RateLimitConfig(HytaleServerConfig hytaleServerConfig) {
/* 1062 */       this.hytaleServerConfig = hytaleServerConfig;
/*      */     }
/*      */     
/*      */     public boolean isEnabled() {
/* 1066 */       return (this.enabled != null) ? this.enabled.booleanValue() : true;
/*      */     }
/*      */     
/*      */     public void setEnabled(boolean enabled) {
/* 1070 */       this.enabled = Boolean.valueOf(enabled);
/* 1071 */       if (this.hytaleServerConfig != null) this.hytaleServerConfig.markChanged(); 
/*      */     }
/*      */     
/*      */     public int getPacketsPerSecond() {
/* 1075 */       return (this.packetsPerSecond != null) ? this.packetsPerSecond.intValue() : 2000;
/*      */     }
/*      */     
/*      */     public void setPacketsPerSecond(int packetsPerSecond) {
/* 1079 */       this.packetsPerSecond = Integer.valueOf(packetsPerSecond);
/* 1080 */       if (this.hytaleServerConfig != null) this.hytaleServerConfig.markChanged(); 
/*      */     }
/*      */     
/*      */     public int getBurstCapacity() {
/* 1084 */       return (this.burstCapacity != null) ? this.burstCapacity.intValue() : 500;
/*      */     }
/*      */     
/*      */     public void setBurstCapacity(int burstCapacity) {
/* 1088 */       this.burstCapacity = Integer.valueOf(burstCapacity);
/* 1089 */       if (this.hytaleServerConfig != null) this.hytaleServerConfig.markChanged();
/*      */     
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static class ModConfig
/*      */   {
/*      */     public static final BuilderCodec<ModConfig> CODEC;
/*      */ 
/*      */     
/*      */     @Nullable
/*      */     private Boolean enabled;
/*      */     
/*      */     @Nullable
/*      */     private SemverRange requiredVersion;
/*      */ 
/*      */     
/*      */     static {
/* 1109 */       CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ModConfig.class, ModConfig::new).append(new KeyedCodec("Enabled", (Codec)Codec.BOOLEAN), (modConfig, enabled) -> modConfig.enabled = enabled, modConfig -> modConfig.enabled).add()).append(new KeyedCodec("RequiredVersion", SemverRange.CODEC), (modConfig, semverRange) -> modConfig.requiredVersion = semverRange, modConfig -> modConfig.requiredVersion).add()).build();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Nullable
/*      */     public Boolean getEnabled() {
/* 1119 */       return this.enabled;
/*      */     }
/*      */     
/*      */     public void setEnabled(Boolean enabled) {
/* 1123 */       this.enabled = enabled;
/*      */     }
/*      */     
/*      */     @Nullable
/*      */     public SemverRange getRequiredVersion() {
/* 1128 */       return this.requiredVersion;
/*      */     }
/*      */     
/*      */     public void setRequiredVersion(SemverRange requiredVersion) {
/* 1132 */       this.requiredVersion = requiredVersion;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static void setBoot(HytaleServerConfig serverConfig, PluginIdentifier identifier, boolean enabled) {
/* 1143 */       ((ModConfig)serverConfig.getModConfig()
/* 1144 */         .computeIfAbsent((K)identifier, id -> new ModConfig()))
/* 1145 */         .enabled = Boolean.valueOf(enabled);
/*      */     }
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\HytaleServerConfig.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */