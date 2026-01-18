/*     */ package com.hypixel.hytale.server.core.plugin;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.codec.AssetCodecMapCodec;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.EnumCodec;
/*     */ import com.hypixel.hytale.codec.lookup.MapKeyMapCodec;
/*     */ import com.hypixel.hytale.codec.lookup.StringCodecMapCodec;
/*     */ import com.hypixel.hytale.common.plugin.PluginIdentifier;
/*     */ import com.hypixel.hytale.common.plugin.PluginManifest;
/*     */ import com.hypixel.hytale.component.ComponentRegistryProxy;
/*     */ import com.hypixel.hytale.event.EventRegistry;
/*     */ import com.hypixel.hytale.event.IEventRegistry;
/*     */ import com.hypixel.hytale.function.consumer.BooleanConsumer;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.metrics.MetricProvider;
/*     */ import com.hypixel.hytale.metrics.MetricsRegistry;
/*     */ import com.hypixel.hytale.server.core.HytaleServer;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandOwner;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandRegistry;
/*     */ import com.hypixel.hytale.server.core.modules.entity.EntityRegistry;
/*     */ import com.hypixel.hytale.server.core.plugin.registry.AssetRegistry;
/*     */ import com.hypixel.hytale.server.core.plugin.registry.CodecMapRegistry;
/*     */ import com.hypixel.hytale.server.core.plugin.registry.IRegistry;
/*     */ import com.hypixel.hytale.server.core.plugin.registry.MapKeyMapRegistry;
/*     */ import com.hypixel.hytale.server.core.registry.ClientFeatureRegistry;
/*     */ import com.hypixel.hytale.server.core.task.TaskRegistry;
/*     */ import com.hypixel.hytale.server.core.universe.world.meta.BlockStateRegistry;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.core.util.Config;
/*     */ import java.nio.file.Path;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ import java.util.function.Function;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class PluginBase
/*     */   implements CommandOwner
/*     */ {
/*     */   @Nonnull
/*     */   public static final MetricsRegistry<PluginBase> METRICS_REGISTRY;
/*     */   @Nonnull
/*     */   private final HytaleLogger logger;
/*     */   @Nonnull
/*     */   private final PluginIdentifier identifier;
/*     */   @Nonnull
/*     */   private final PluginManifest manifest;
/*     */   @Nonnull
/*     */   private final Path dataDirectory;
/*     */   
/*     */   static {
/*  61 */     METRICS_REGISTRY = (new MetricsRegistry(MetricProvider.maybe(Function.identity()))).register("Identifier", plugin -> plugin.identifier.toString(), (Codec)Codec.STRING).register("Type", PluginBase::getType, (Codec)new EnumCodec(PluginType.class)).register("Manifest", plugin -> plugin.manifest, PluginManifest.CODEC).register("State", plugin -> plugin.state, (Codec)new EnumCodec(PluginState.class)).register("Builtin", plugin -> {
/*     */           if (plugin instanceof JavaPlugin) {
/*     */             JavaPlugin jp = (JavaPlugin)plugin;
/*     */             if (jp.getClassLoader().isInServerClassPath());
/*     */           } 
/*     */           return (Function)Boolean.valueOf(false);
/*     */         }(Codec)Codec.BOOLEAN);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  87 */   private final List<Config<?>> configs = new CopyOnWriteArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  95 */   private PluginState state = PluginState.NONE;
/*     */ 
/*     */   
/*  98 */   private final String notEnabledString = "The plugin " + String.valueOf(getIdentifier()) + " is not enabled!";
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 103 */   private final CopyOnWriteArrayList<BooleanConsumer> shutdownTasks = new CopyOnWriteArrayList<>();
/*     */ 
/*     */ 
/*     */   
/* 107 */   private final ClientFeatureRegistry clientFeatureRegistry = new ClientFeatureRegistry(this.shutdownTasks, () -> (this.state != PluginState.NONE && this.state != PluginState.DISABLED), this.notEnabledString, this);
/* 108 */   private final CommandRegistry commandRegistry = new CommandRegistry(this.shutdownTasks, () -> (this.state != PluginState.NONE && this.state != PluginState.DISABLED), this.notEnabledString, this);
/* 109 */   private final EventRegistry eventRegistry = new EventRegistry(this.shutdownTasks, () -> (this.state != PluginState.NONE && this.state != PluginState.DISABLED), this.notEnabledString, (IEventRegistry)HytaleServer.get().getEventBus());
/* 110 */   private final BlockStateRegistry blockStateRegistry = new BlockStateRegistry(this.shutdownTasks, () -> (this.state != PluginState.NONE && this.state != PluginState.DISABLED), this.notEnabledString);
/* 111 */   private final EntityRegistry entityRegistry = new EntityRegistry(this.shutdownTasks, () -> (this.state != PluginState.NONE && this.state != PluginState.DISABLED), this.notEnabledString);
/* 112 */   private final TaskRegistry taskRegistry = new TaskRegistry(this.shutdownTasks, () -> (this.state != PluginState.NONE && this.state != PluginState.DISABLED), this.notEnabledString);
/*     */   
/* 114 */   private final ComponentRegistryProxy<EntityStore> entityStoreRegistry = new ComponentRegistryProxy(this.shutdownTasks, EntityStore.REGISTRY);
/* 115 */   private final ComponentRegistryProxy<ChunkStore> chunkStoreRegistry = new ComponentRegistryProxy(this.shutdownTasks, ChunkStore.REGISTRY);
/* 116 */   private final AssetRegistry assetRegistry = new AssetRegistry(this.shutdownTasks);
/* 117 */   private final Map<Codec<?>, IRegistry> codecMapRegistries = new ConcurrentHashMap<>();
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final String basePermission;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PluginBase(@Nonnull PluginInit init) {
/* 128 */     PluginManifest pluginManifest = init.getPluginManifest();
/* 129 */     String pluginName = pluginManifest.getName();
/* 130 */     boolean isPlugin = (getType() == PluginType.PLUGIN);
/*     */     
/* 132 */     this.logger = HytaleLogger.get(pluginName + pluginName);
/* 133 */     this.dataDirectory = init.getDataDirectory();
/* 134 */     this.identifier = new PluginIdentifier(pluginManifest);
/* 135 */     this.manifest = pluginManifest;
/*     */ 
/*     */ 
/*     */     
/* 139 */     if (!init.isInServerClassPath()) {
/* 140 */       this.logger.setPropagatesSentryToParent(false);
/*     */     }
/*     */     
/* 143 */     this.basePermission = (pluginManifest.getGroup() + "." + pluginManifest.getGroup()).toLowerCase();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected final <T> Config<T> withConfig(@Nonnull BuilderCodec<T> configCodec) {
/* 158 */     return withConfig("config", configCodec);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected final <T> Config<T> withConfig(@Nonnull String name, @Nonnull BuilderCodec<T> configCodec) {
/* 175 */     if (this.state != PluginState.NONE) throw new IllegalStateException("Must be called before setup"); 
/* 176 */     Config<T> config = new Config(this.dataDirectory, name, configCodec);
/* 177 */     this.configs.add(config);
/* 178 */     return config;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public CompletableFuture<Void> preLoad() {
/* 183 */     if (this.configs.isEmpty()) return null; 
/* 184 */     CompletableFuture[] arrayOfCompletableFuture = new CompletableFuture[this.configs.size()];
/* 185 */     for (int i = 0; i < this.configs.size(); i++) {
/* 186 */       arrayOfCompletableFuture[i] = ((Config)this.configs.get(i)).load();
/*     */     }
/* 188 */     return CompletableFuture.allOf((CompletableFuture<?>[])arrayOfCompletableFuture);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String getName() {
/* 194 */     return this.identifier.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public HytaleLogger getLogger() {
/* 202 */     return this.logger;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public PluginIdentifier getIdentifier() {
/* 210 */     return this.identifier;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public PluginManifest getManifest() {
/* 218 */     return this.manifest;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Path getDataDirectory() {
/* 226 */     return this.dataDirectory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public PluginState getState() {
/* 234 */     return this.state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ClientFeatureRegistry getClientFeatureRegistry() {
/* 242 */     return this.clientFeatureRegistry;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public CommandRegistry getCommandRegistry() {
/* 250 */     return this.commandRegistry;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public EventRegistry getEventRegistry() {
/* 258 */     return this.eventRegistry;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public BlockStateRegistry getBlockStateRegistry() {
/* 266 */     return this.blockStateRegistry;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public EntityRegistry getEntityRegistry() {
/* 274 */     return this.entityRegistry;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public TaskRegistry getTaskRegistry() {
/* 282 */     return this.taskRegistry;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ComponentRegistryProxy<EntityStore> getEntityStoreRegistry() {
/* 290 */     return this.entityStoreRegistry;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ComponentRegistryProxy<ChunkStore> getChunkStoreRegistry() {
/* 298 */     return this.chunkStoreRegistry;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public AssetRegistry getAssetRegistry() {
/* 306 */     return this.assetRegistry;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public <T, C extends Codec<? extends T>> CodecMapRegistry<T, C> getCodecRegistry(@Nonnull StringCodecMapCodec<T, C> mapCodec) {
/* 320 */     IRegistry registry = this.codecMapRegistries.computeIfAbsent(mapCodec, v -> new CodecMapRegistry(this.shutdownTasks, mapCodec));
/* 321 */     return (CodecMapRegistry<T, C>)registry;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public <K, T extends com.hypixel.hytale.assetstore.JsonAsset<K>> CodecMapRegistry.Assets<T, ?> getCodecRegistry(@Nonnull AssetCodecMapCodec<K, T> mapCodec) {
/* 336 */     IRegistry registry = this.codecMapRegistries.computeIfAbsent(mapCodec, v -> new CodecMapRegistry.Assets(this.shutdownTasks, (StringCodecMapCodec)mapCodec));
/* 337 */     return (CodecMapRegistry.Assets<T, ?>)registry;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public <V> MapKeyMapRegistry<V> getCodecRegistry(@Nonnull MapKeyMapCodec<V> mapCodec) {
/* 350 */     IRegistry registry = this.codecMapRegistries.computeIfAbsent(mapCodec, v -> new MapKeyMapRegistry(this.shutdownTasks, mapCodec));
/* 351 */     return (MapKeyMapRegistry<V>)registry;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public final String getBasePermission() {
/* 359 */     return this.basePermission;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDisabled() {
/* 366 */     return (this.state == PluginState.NONE || this.state == PluginState.DISABLED || this.state == PluginState.SHUTDOWN);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEnabled() {
/* 375 */     return !isDisabled();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setup0() {
/* 382 */     if (this.state != PluginState.NONE && this.state != PluginState.DISABLED) {
/* 383 */       throw new IllegalArgumentException(String.valueOf(this.state));
/*     */     }
/* 385 */     this.state = PluginState.SETUP;
/*     */ 
/*     */     
/*     */     try {
/* 389 */       setup();
/* 390 */     } catch (Throwable t) {
/* 391 */       ((HytaleLogger.Api)this.logger.at(Level.SEVERE).withCause(t)).log("Failed to setup plugin %s", this.identifier);
/* 392 */       this.state = PluginState.DISABLED;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setup() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void start0() {
/* 406 */     if (this.state != PluginState.SETUP) throw new IllegalArgumentException(String.valueOf(this.state)); 
/* 407 */     this.state = PluginState.START;
/*     */     
/*     */     try {
/* 410 */       start();
/* 411 */       this.state = PluginState.ENABLED;
/* 412 */     } catch (Throwable t) {
/* 413 */       ((HytaleLogger.Api)this.logger.at(Level.SEVERE).withCause(t)).log("Failed to start %s", this.identifier);
/* 414 */       this.state = PluginState.DISABLED;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void start() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void shutdown0(boolean shutdown) {
/* 430 */     this.state = PluginState.SHUTDOWN;
/*     */     try {
/* 432 */       shutdown();
/* 433 */       this.state = PluginState.DISABLED;
/* 434 */     } catch (Throwable t) {
/* 435 */       ((HytaleLogger.Api)this.logger.at(Level.SEVERE).withCause(t)).log("Failed to shutdown %s", this.identifier);
/*     */     } 
/* 437 */     cleanup(shutdown);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void shutdown() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void cleanup(boolean shutdown) {
/* 452 */     this.commandRegistry.shutdown();
/* 453 */     this.eventRegistry.shutdown();
/* 454 */     this.clientFeatureRegistry.shutdown();
/* 455 */     this.blockStateRegistry.shutdown();
/* 456 */     this.taskRegistry.shutdown();
/*     */     
/* 458 */     this.entityStoreRegistry.shutdown();
/* 459 */     this.chunkStoreRegistry.shutdown();
/*     */     
/* 461 */     this.codecMapRegistries.forEach((k, v) -> v.shutdown());
/* 462 */     this.assetRegistry.shutdown();
/*     */ 
/*     */     
/* 465 */     for (int i = this.shutdownTasks.size() - 1; i >= 0; i--)
/* 466 */       ((BooleanConsumer)this.shutdownTasks.get(i)).accept(shutdown); 
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public abstract PluginType getType();
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\plugin\PluginBase.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */