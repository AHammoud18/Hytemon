/*      */ package com.hypixel.hytale.server.core.universe;
/*      */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*      */ import com.hypixel.hytale.codec.Codec;
/*      */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*      */ import com.hypixel.hytale.codec.lookup.Priority;
/*      */ import com.hypixel.hytale.common.plugin.PluginIdentifier;
/*      */ import com.hypixel.hytale.common.plugin.PluginManifest;
/*      */ import com.hypixel.hytale.common.semver.SemverRange;
/*      */ import com.hypixel.hytale.common.util.CompletableFutureUtil;
/*      */ import com.hypixel.hytale.component.Component;
/*      */ import com.hypixel.hytale.component.ComponentRegistryProxy;
/*      */ import com.hypixel.hytale.component.ComponentType;
/*      */ import com.hypixel.hytale.component.Holder;
/*      */ import com.hypixel.hytale.component.Ref;
/*      */ import com.hypixel.hytale.component.ResourceType;
/*      */ import com.hypixel.hytale.component.Store;
/*      */ import com.hypixel.hytale.component.system.ISystem;
/*      */ import com.hypixel.hytale.event.EventRegistry;
/*      */ import com.hypixel.hytale.event.IBaseEvent;
/*      */ import com.hypixel.hytale.event.IEventDispatcher;
/*      */ import com.hypixel.hytale.logger.HytaleLogger;
/*      */ import com.hypixel.hytale.math.vector.Transform;
/*      */ import com.hypixel.hytale.metrics.MetricsRegistry;
/*      */ import com.hypixel.hytale.protocol.Packet;
/*      */ import com.hypixel.hytale.protocol.PlayerSkin;
/*      */ import com.hypixel.hytale.protocol.packets.setup.ServerTags;
/*      */ import com.hypixel.hytale.server.core.Constants;
/*      */ import com.hypixel.hytale.server.core.HytaleServer;
/*      */ import com.hypixel.hytale.server.core.HytaleServerConfig;
/*      */ import com.hypixel.hytale.server.core.Message;
/*      */ import com.hypixel.hytale.server.core.NameMatching;
/*      */ import com.hypixel.hytale.server.core.Options;
/*      */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*      */ import com.hypixel.hytale.server.core.command.system.CommandRegistry;
/*      */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*      */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*      */ import com.hypixel.hytale.server.core.entity.entities.player.data.PlayerConfigData;
/*      */ import com.hypixel.hytale.server.core.event.events.PrepareUniverseEvent;
/*      */ import com.hypixel.hytale.server.core.event.events.ShutdownEvent;
/*      */ import com.hypixel.hytale.server.core.event.events.player.PlayerConnectEvent;
/*      */ import com.hypixel.hytale.server.core.event.events.player.PlayerDisconnectEvent;
/*      */ import com.hypixel.hytale.server.core.io.PacketHandler;
/*      */ import com.hypixel.hytale.server.core.io.ProtocolVersion;
/*      */ import com.hypixel.hytale.server.core.io.handlers.game.GamePacketHandler;
/*      */ import com.hypixel.hytale.server.core.io.netty.NettyUtil;
/*      */ import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
/*      */ import com.hypixel.hytale.server.core.modules.entity.component.PositionDataComponent;
/*      */ import com.hypixel.hytale.server.core.modules.entity.player.ChunkTracker;
/*      */ import com.hypixel.hytale.server.core.modules.entity.player.PlayerSkinComponent;
/*      */ import com.hypixel.hytale.server.core.modules.entity.tracker.EntityTrackerSystems;
/*      */ import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
/*      */ import com.hypixel.hytale.server.core.plugin.PluginManager;
/*      */ import com.hypixel.hytale.server.core.receiver.IPacketReceiver;
/*      */ import com.hypixel.hytale.server.core.universe.playerdata.PlayerStorage;
/*      */ import com.hypixel.hytale.server.core.universe.world.World;
/*      */ import com.hypixel.hytale.server.core.universe.world.WorldConfig;
/*      */ import com.hypixel.hytale.server.core.universe.world.WorldConfigProvider;
/*      */ import com.hypixel.hytale.server.core.universe.world.events.AddWorldEvent;
/*      */ import com.hypixel.hytale.server.core.universe.world.events.AllWorldsLoadedEvent;
/*      */ import com.hypixel.hytale.server.core.universe.world.events.RemoveWorldEvent;
/*      */ import com.hypixel.hytale.server.core.universe.world.spawn.FitToHeightMapSpawnProvider;
/*      */ import com.hypixel.hytale.server.core.universe.world.spawn.GlobalSpawnProvider;
/*      */ import com.hypixel.hytale.server.core.universe.world.spawn.ISpawnProvider;
/*      */ import com.hypixel.hytale.server.core.universe.world.spawn.IndividualSpawnProvider;
/*      */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*      */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*      */ import com.hypixel.hytale.server.core.universe.world.storage.component.ChunkSavingSystems;
/*      */ import com.hypixel.hytale.server.core.universe.world.storage.provider.DefaultChunkStorageProvider;
/*      */ import com.hypixel.hytale.server.core.universe.world.storage.provider.EmptyChunkStorageProvider;
/*      */ import com.hypixel.hytale.server.core.universe.world.storage.provider.IChunkStorageProvider;
/*      */ import com.hypixel.hytale.server.core.universe.world.storage.provider.IndexedStorageChunkStorageProvider;
/*      */ import com.hypixel.hytale.server.core.universe.world.storage.provider.MigrationChunkStorageProvider;
/*      */ import com.hypixel.hytale.server.core.universe.world.storage.resources.DefaultResourceStorageProvider;
/*      */ import com.hypixel.hytale.server.core.universe.world.storage.resources.DiskResourceStorageProvider;
/*      */ import com.hypixel.hytale.server.core.universe.world.storage.resources.EmptyResourceStorageProvider;
/*      */ import com.hypixel.hytale.server.core.universe.world.storage.resources.IResourceStorageProvider;
/*      */ import com.hypixel.hytale.server.core.universe.world.worldgen.provider.DummyWorldGenProvider;
/*      */ import com.hypixel.hytale.server.core.universe.world.worldgen.provider.IWorldGenProvider;
/*      */ import com.hypixel.hytale.server.core.universe.world.worldgen.provider.VoidWorldGenProvider;
/*      */ import com.hypixel.hytale.server.core.universe.world.worldmap.provider.DisabledWorldMapProvider;
/*      */ import com.hypixel.hytale.server.core.universe.world.worldmap.provider.IWorldMapProvider;
/*      */ import com.hypixel.hytale.server.core.universe.world.worldmap.provider.chunk.WorldGenWorldMapProvider;
/*      */ import com.hypixel.hytale.server.core.util.backup.BackupTask;
/*      */ import io.netty.channel.Channel;
/*      */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*      */ import java.io.IOException;
/*      */ import java.nio.file.DirectoryStream;
/*      */ import java.nio.file.Files;
/*      */ import java.nio.file.Path;
/*      */ import java.nio.file.attribute.FileAttribute;
/*      */ import java.util.Collections;
/*      */ import java.util.Comparator;
/*      */ import java.util.ConcurrentModificationException;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.UUID;
/*      */ import java.util.concurrent.CompletableFuture;
/*      */ import java.util.concurrent.CompletionStage;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import java.util.concurrent.Executor;
/*      */ import java.util.concurrent.TimeUnit;
/*      */ import java.util.function.BiPredicate;
/*      */ import java.util.logging.Level;
/*      */ import javax.annotation.CheckReturnValue;
/*      */ import javax.annotation.Nonnull;
/*      */ import javax.annotation.Nullable;
/*      */ import joptsimple.OptionSet;
/*      */ import org.bson.BsonDocument;
/*      */ import org.bson.BsonValue;
/*      */ 
/*      */ public class Universe extends JavaPlugin implements IMessageReceiver, MetricProvider {
/*      */   @Nonnull
/*  115 */   public static final PluginManifest MANIFEST = PluginManifest.corePlugin(Universe.class)
/*  116 */     .build();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*  122 */   private static Map<Integer, String> LEGACY_BLOCK_ID_MAP = Collections.emptyMap();
/*      */   
/*      */   @Nonnull
/*      */   public static final MetricsRegistry<Universe> METRICS_REGISTRY;
/*      */   private static Universe instance;
/*      */   private ComponentType<EntityStore, PlayerRef> playerRefComponentType;
/*      */   
/*      */   static {
/*  130 */     METRICS_REGISTRY = (new MetricsRegistry()).register("Worlds", universe -> (World[])universe.getWorlds().values().toArray(()), (Codec)new ArrayCodec((Codec)World.METRICS_REGISTRY, x$0 -> new World[x$0])).register("PlayerCount", Universe::getPlayerCount, (Codec)Codec.INTEGER);
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
/*      */   public static Universe get() {
/*  143 */     return instance;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*  149 */   private final Path path = Constants.UNIVERSE_PATH;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*  155 */   private final Map<UUID, PlayerRef> players = new ConcurrentHashMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*  161 */   private final Map<String, World> worlds = new ConcurrentHashMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*  167 */   private final Map<UUID, World> worldsByUuid = new ConcurrentHashMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*  174 */   private final Map<String, World> unmodifiableWorlds = Collections.unmodifiableMap(this.worlds);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private PlayerStorage playerStorage;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private WorldConfigProvider worldConfigProvider;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ResourceType<ChunkStore, IndexedStorageChunkStorageProvider.IndexedStorageCache> indexedStorageCacheResourceType;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private CompletableFuture<Void> universeReady;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Universe(@Nonnull JavaPluginInit init) {
/*  202 */     super(init);
/*  203 */     instance = this;
/*      */     
/*  205 */     if (!Files.isDirectory(this.path, new java.nio.file.LinkOption[0]) && !Options.getOptionSet().has(Options.BARE)) {
/*      */       try {
/*  207 */         Files.createDirectories(this.path, (FileAttribute<?>[])new FileAttribute[0]);
/*  208 */       } catch (IOException e) {
/*  209 */         throw new RuntimeException("Failed to create universe directory", e);
/*      */       } 
/*      */     }
/*      */     
/*  213 */     if (Options.getOptionSet().has(Options.BACKUP)) {
/*  214 */       int frequencyMinutes = Math.max(((Integer)Options.getOptionSet().valueOf(Options.BACKUP_FREQUENCY_MINUTES)).intValue(), 1);
/*  215 */       getLogger().at(Level.INFO).log("Scheduled backup to run every %d minute(s)", frequencyMinutes);
/*      */       
/*  217 */       HytaleServer.SCHEDULED_EXECUTOR.scheduleWithFixedDelay(() -> {
/*      */             try {
/*      */               getLogger().at(Level.INFO).log("Backing up universe...");
/*      */ 
/*      */               
/*      */               runBackup().thenAccept(());
/*  223 */             } catch (Exception e) {
/*      */               ((HytaleLogger.Api)getLogger().at(Level.SEVERE).withCause(e)).log("Error backing up universe");
/*      */             } 
/*      */           }frequencyMinutes, frequencyMinutes, TimeUnit.MINUTES);
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
/*      */   @Nonnull
/*      */   public CompletableFuture<Void> runBackup() {
/*  239 */     return CompletableFuture.allOf((CompletableFuture<?>[])this.worlds.values().stream()
/*  240 */         .map(world -> CompletableFuture.supplyAsync((), (Executor)world).thenCompose(ChunkSavingSystems.Data::waitForSavingChunks))
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  247 */         .toArray(x$0 -> new CompletableFuture[x$0]))
/*  248 */       .thenCompose(aVoid -> BackupTask.start(this.path, (Path)Options.getOptionSet().valueOf(Options.BACKUP_DIRECTORY)))
/*  249 */       .thenCompose(success -> CompletableFuture.allOf((CompletableFuture<?>[])this.worlds.values().stream().map(()).toArray(())).thenApply(()));
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
/*      */   protected void setup() {
/*  261 */     EventRegistry eventRegistry = getEventRegistry();
/*  262 */     ComponentRegistryProxy<ChunkStore> chunkStoreRegistry = getChunkStoreRegistry();
/*  263 */     ComponentRegistryProxy<EntityStore> entityStoreRegistry = getEntityStoreRegistry();
/*  264 */     CommandRegistry commandRegistry = getCommandRegistry();
/*      */     
/*  266 */     eventRegistry.register((short)-48, ShutdownEvent.class, event -> disconnectAllPLayers());
/*  267 */     eventRegistry.register((short)-32, ShutdownEvent.class, event -> shutdownAllWorlds());
/*      */     
/*  269 */     ISpawnProvider.CODEC.register("Global", GlobalSpawnProvider.class, (Codec)GlobalSpawnProvider.CODEC);
/*  270 */     ISpawnProvider.CODEC.register("Individual", IndividualSpawnProvider.class, (Codec)IndividualSpawnProvider.CODEC);
/*  271 */     ISpawnProvider.CODEC.register("FitToHeightMap", FitToHeightMapSpawnProvider.class, (Codec)FitToHeightMapSpawnProvider.CODEC);
/*      */     
/*  273 */     IWorldGenProvider.CODEC.register("Flat", FlatWorldGenProvider.class, (Codec)FlatWorldGenProvider.CODEC);
/*  274 */     IWorldGenProvider.CODEC.register("Dummy", DummyWorldGenProvider.class, (Codec)DummyWorldGenProvider.CODEC);
/*  275 */     IWorldGenProvider.CODEC.register(Priority.DEFAULT, "Void", VoidWorldGenProvider.class, (Codec)VoidWorldGenProvider.CODEC);
/*      */     
/*  277 */     IWorldMapProvider.CODEC.register("Disabled", DisabledWorldMapProvider.class, (Codec)DisabledWorldMapProvider.CODEC);
/*  278 */     IWorldMapProvider.CODEC.register(Priority.DEFAULT, "WorldGen", WorldGenWorldMapProvider.class, (Codec)WorldGenWorldMapProvider.CODEC);
/*      */     
/*  280 */     IChunkStorageProvider.CODEC.register(Priority.DEFAULT, "Hytale", DefaultChunkStorageProvider.class, (Codec)DefaultChunkStorageProvider.CODEC);
/*  281 */     IChunkStorageProvider.CODEC.register("Migration", MigrationChunkStorageProvider.class, (Codec)MigrationChunkStorageProvider.CODEC);
/*  282 */     IChunkStorageProvider.CODEC.register("IndexedStorage", IndexedStorageChunkStorageProvider.class, (Codec)IndexedStorageChunkStorageProvider.CODEC);
/*  283 */     IChunkStorageProvider.CODEC.register("Empty", EmptyChunkStorageProvider.class, (Codec)EmptyChunkStorageProvider.CODEC);
/*      */     
/*  285 */     IResourceStorageProvider.CODEC.register(Priority.DEFAULT, "Hytale", DefaultResourceStorageProvider.class, (Codec)DefaultResourceStorageProvider.CODEC);
/*  286 */     IResourceStorageProvider.CODEC.register("Disk", DiskResourceStorageProvider.class, (Codec)DiskResourceStorageProvider.CODEC);
/*  287 */     IResourceStorageProvider.CODEC.register("Empty", EmptyResourceStorageProvider.class, (Codec)EmptyResourceStorageProvider.CODEC);
/*      */     
/*  289 */     this.indexedStorageCacheResourceType = chunkStoreRegistry.registerResource(IndexedStorageChunkStorageProvider.IndexedStorageCache.class, com.hypixel.hytale.server.core.universe.world.storage.provider.IndexedStorageChunkStorageProvider.IndexedStorageCache::new);
/*  290 */     chunkStoreRegistry.registerSystem((ISystem)new IndexedStorageChunkStorageProvider.IndexedStorageCacheSetupSystem());
/*  291 */     chunkStoreRegistry.registerSystem((ISystem)new WorldPregenerateSystem());
/*      */     
/*  293 */     entityStoreRegistry.registerSystem((ISystem)new WorldConfigSaveSystem());
/*      */     
/*  295 */     this.playerRefComponentType = entityStoreRegistry.registerComponent(PlayerRef.class, () -> {
/*      */           throw new UnsupportedOperationException();
/*      */         });
/*      */     
/*  299 */     entityStoreRegistry.registerSystem((ISystem)new PlayerPingSystem());
/*      */     
/*  301 */     entityStoreRegistry.registerSystem((ISystem)new PlayerConnectionFlushSystem(this.playerRefComponentType));
/*  302 */     entityStoreRegistry.registerSystem((ISystem)new PlayerRefAddedSystem(this.playerRefComponentType));
/*      */     
/*  304 */     commandRegistry.registerCommand((AbstractCommand)new SetTickingCommand());
/*  305 */     commandRegistry.registerCommand((AbstractCommand)new BlockCommand());
/*  306 */     commandRegistry.registerCommand((AbstractCommand)new BlockSelectCommand());
/*  307 */     commandRegistry.registerCommand((AbstractCommand)new WorldCommand());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void start() {
/*      */     WorldConfigProvider.Default default_1;
/*  314 */     HytaleServerConfig config = HytaleServer.get().getConfig();
/*  315 */     if (config == null) {
/*  316 */       throw new IllegalStateException("Server config is not loaded!");
/*      */     }
/*      */     
/*  319 */     this.playerStorage = config.getPlayerStorageProvider().getPlayerStorage();
/*      */ 
/*      */     
/*  322 */     WorldConfigProvider.Default defaultConfigProvider = new WorldConfigProvider.Default();
/*      */ 
/*      */     
/*  325 */     PrepareUniverseEvent event = (PrepareUniverseEvent)HytaleServer.get().getEventBus().dispatchFor(PrepareUniverseEvent.class).dispatch((IBaseEvent)new PrepareUniverseEvent((WorldConfigProvider)defaultConfigProvider));
/*      */     
/*  327 */     WorldConfigProvider worldConfigProvider = event.getWorldConfigProvider();
/*  328 */     if (worldConfigProvider == null) {
/*  329 */       default_1 = defaultConfigProvider;
/*      */     }
/*  331 */     this.worldConfigProvider = (WorldConfigProvider)default_1;
/*      */     
/*      */     try {
/*  334 */       Path blockIdMapPath = this.path.resolve("blockIdMap.json");
/*  335 */       Path path = this.path.resolve("blockIdMap.legacy.json");
/*  336 */       if (Files.isRegularFile(blockIdMapPath, new java.nio.file.LinkOption[0])) Files.move(blockIdMapPath, path, new CopyOption[] { StandardCopyOption.REPLACE_EXISTING }); 
/*  337 */       Files.deleteIfExists(this.path.resolve("blockIdMap.json.bak"));
/*      */       
/*  339 */       if (Files.isRegularFile(path, new java.nio.file.LinkOption[0])) {
/*  340 */         Int2ObjectOpenHashMap<Integer, String> int2ObjectOpenHashMap = new Int2ObjectOpenHashMap();
/*  341 */         for (BsonValue bsonValue : BsonUtil.readDocument(
/*  342 */             path).thenApply(document -> document.getArray("Blocks")).join()) {
/*  343 */           BsonDocument bsonDocument = bsonValue.asDocument();
/*  344 */           int2ObjectOpenHashMap.put(
/*  345 */               Integer.valueOf(bsonDocument.getNumber("Id").intValue()), bsonDocument
/*  346 */               .getString("BlockType").getValue());
/*      */         } 
/*      */ 
/*      */         
/*  350 */         LEGACY_BLOCK_ID_MAP = Collections.unmodifiableMap((Map<? extends Integer, ? extends String>)int2ObjectOpenHashMap);
/*      */       } 
/*  352 */     } catch (IOException e) {
/*  353 */       ((HytaleLogger.Api)getLogger().at(Level.SEVERE).withCause(e)).log("Failed to delete blockIdMap.json");
/*      */     } 
/*      */ 
/*      */     
/*  357 */     if (Options.getOptionSet().has(Options.BARE)) {
/*  358 */       this.universeReady = CompletableFuture.completedFuture(null);
/*  359 */       HytaleServer.get().getEventBus().dispatch(AllWorldsLoadedEvent.class);
/*      */       
/*      */       return;
/*      */     } 
/*  363 */     ObjectArrayList<CompletableFuture<?>> loadingWorlds = new ObjectArrayList();
/*      */     
/*      */     try {
/*  366 */       Path worldsPath = this.path.resolve("worlds");
/*  367 */       Files.createDirectories(worldsPath, (FileAttribute<?>[])new FileAttribute[0]);
/*  368 */       DirectoryStream<Path> stream = Files.newDirectoryStream(worldsPath); 
/*  369 */       try { for (Path file : stream)
/*  370 */         { if (HytaleServer.get().isShuttingDown())
/*      */           
/*      */           { 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  383 */             if (stream != null) stream.close();  return; }  if (file.equals(worldsPath) || !Files.isDirectory(file, new java.nio.file.LinkOption[0])) continue;  String name = file.getFileName().toString(); if (getWorld(name) == null) { loadingWorlds.add(loadWorldFromStart(file, name).exceptionally(throwable -> { ((HytaleLogger.Api)getLogger().at(Level.SEVERE).withCause(throwable)).log("Failed to load world: %s", name); return null; })); continue; }  getLogger().at(Level.SEVERE).log("Skipping loading world '%s' because it already exists!", name); }  if (stream != null) stream.close();  } catch (Throwable throwable) { if (stream != null)
/*      */           try { stream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }
/*  385 */        this.universeReady = CompletableFutureUtil._catch(CompletableFuture.allOf((CompletableFuture<?>[])loadingWorlds.toArray(x$0 -> new CompletableFuture[x$0]))
/*  386 */           .thenCompose(v -> {
/*      */               String worldName = config.getDefaults().getWorld();
/*      */ 
/*      */               
/*  390 */               return (worldName != null && !this.worlds.containsKey(worldName.toLowerCase())) ? CompletableFutureUtil._catch(addWorld(worldName)) : CompletableFuture.completedFuture(null);
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  395 */             }).thenRun(() -> HytaleServer.get().getEventBus().dispatch(AllWorldsLoadedEvent.class)));
/*  396 */     } catch (IOException e) {
/*  397 */       throw new RuntimeException("Failed to load Worlds", e);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void shutdown() {
/*  403 */     disconnectAllPLayers();
/*  404 */     shutdownAllWorlds();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void disconnectAllPLayers() {
/*  412 */     this.players.values().forEach(player -> player.getPacketHandler().disconnect("Stopping server!"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void shutdownAllWorlds() {
/*  422 */     for (Iterator<World> iterator = this.worlds.values().iterator(); iterator.hasNext(); ) {
/*  423 */       World world = iterator.next();
/*  424 */       world.stop();
/*  425 */       iterator.remove();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public MetricResults toMetricResults() {
/*  432 */     return METRICS_REGISTRY.toMetricResults(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CompletableFuture<Void> getUniverseReady() {
/*  439 */     return this.universeReady;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ResourceType<ChunkStore, IndexedStorageChunkStorageProvider.IndexedStorageCache> getIndexedStorageCacheResourceType() {
/*  446 */     return this.indexedStorageCacheResourceType;
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
/*      */   public boolean isWorldLoadable(@Nonnull String name) {
/*  459 */     Path savePath = this.path.resolve("worlds").resolve(name);
/*  460 */     return (Files.isDirectory(savePath, new java.nio.file.LinkOption[0]) && (
/*  461 */       Files.exists(savePath.resolve("config.bson"), new java.nio.file.LinkOption[0]) || Files.exists(savePath.resolve("config.json"), new java.nio.file.LinkOption[0])));
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
/*      */   @CheckReturnValue
/*      */   public CompletableFuture<World> addWorld(@Nonnull String name) {
/*  474 */     return addWorld(name, (String)null, (String)null);
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
/*      */   @Nonnull
/*      */   @Deprecated
/*      */   @CheckReturnValue
/*      */   public CompletableFuture<World> addWorld(@Nonnull String name, @Nullable String generatorType, @Nullable String chunkStorageType) {
/*  492 */     if (this.worlds.containsKey(name)) {
/*  493 */       throw new IllegalArgumentException("World " + name + " already exists!");
/*      */     }
/*      */     
/*  496 */     if (isWorldLoadable(name)) {
/*  497 */       throw new IllegalArgumentException("World " + name + " already exists on disk!");
/*      */     }
/*      */     
/*  500 */     Path savePath = this.path.resolve("worlds").resolve(name);
/*  501 */     return this.worldConfigProvider.load(savePath, name)
/*  502 */       .thenCompose(worldConfig -> {
/*      */           if (generatorType != null && !"default".equals(generatorType)) {
/*      */             BuilderCodec<? extends IWorldGenProvider> providerCodec = (BuilderCodec<? extends IWorldGenProvider>)IWorldGenProvider.CODEC.getCodecFor(generatorType);
/*      */             if (providerCodec == null) {
/*      */               throw new IllegalArgumentException("Unknown generatorType '" + generatorType + "'");
/*      */             }
/*      */             IWorldGenProvider provider = (IWorldGenProvider)providerCodec.getDefaultValue();
/*      */             worldConfig.setWorldGenProvider(provider);
/*      */             worldConfig.markChanged();
/*      */           } 
/*      */           if (chunkStorageType != null && !"default".equals(chunkStorageType)) {
/*      */             BuilderCodec<? extends IChunkStorageProvider> providerCodec = (BuilderCodec<? extends IChunkStorageProvider>)IChunkStorageProvider.CODEC.getCodecFor(chunkStorageType);
/*      */             if (providerCodec == null) {
/*      */               throw new IllegalArgumentException("Unknown chunkStorageType '" + chunkStorageType + "'");
/*      */             }
/*      */             IChunkStorageProvider provider = (IChunkStorageProvider)providerCodec.getDefaultValue();
/*      */             worldConfig.setChunkStorageProvider(provider);
/*      */             worldConfig.markChanged();
/*      */           } 
/*      */           return makeWorld(name, savePath, worldConfig);
/*      */         });
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
/*      */   @Nonnull
/*      */   @CheckReturnValue
/*      */   public CompletableFuture<World> makeWorld(@Nonnull String name, @Nonnull Path savePath, @Nonnull WorldConfig worldConfig) {
/*  537 */     return makeWorld(name, savePath, worldConfig, true);
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   @CheckReturnValue
/*      */   public CompletableFuture<World> makeWorld(@Nonnull String name, @Nonnull Path savePath, @Nonnull WorldConfig worldConfig, boolean start) {
/*  543 */     Map<PluginIdentifier, SemverRange> map = worldConfig.getRequiredPlugins();
/*  544 */     if (map != null) {
/*  545 */       PluginManager pluginManager = PluginManager.get();
/*  546 */       for (Map.Entry<PluginIdentifier, SemverRange> entry : map.entrySet()) {
/*  547 */         if (!pluginManager.hasPlugin(entry.getKey(), entry.getValue())) {
/*  548 */           getLogger().at(Level.SEVERE).log("Failed to load world! Missing plugin: %s, Version: %s", entry.getKey(), entry.getValue());
/*  549 */           throw new IllegalStateException("Missing plugin");
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  554 */     if (this.worlds.containsKey(name)) throw new IllegalArgumentException("World " + name + " already exists!"); 
/*  555 */     return CompletableFuture.supplyAsync(SneakyThrow.sneakySupplier(() -> {
/*      */             World world = new World(name, savePath, worldConfig);
/*      */ 
/*      */             
/*      */             AddWorldEvent event = (AddWorldEvent)HytaleServer.get().getEventBus().dispatchFor(AddWorldEvent.class, name).dispatch((IBaseEvent)new AddWorldEvent(world));
/*      */             
/*      */             if (!event.isCancelled() && !HytaleServer.get().isShuttingDown()) {
/*      */               World oldWorldByName = this.worlds.putIfAbsent(name.toLowerCase(), world);
/*      */               
/*      */               if (oldWorldByName != null) {
/*      */                 throw new ConcurrentModificationException("World with name " + name + " already exists but didn't before! Looks like you have a race condition.");
/*      */               }
/*      */               
/*      */               World oldWorldByUuid = this.worldsByUuid.putIfAbsent(worldConfig.getUuid(), world);
/*      */               
/*      */               if (oldWorldByUuid != null) {
/*      */                 throw new ConcurrentModificationException("World with UUID " + String.valueOf(worldConfig.getUuid()) + " already exists but didn't before! Looks like you have a race condition.");
/*      */               }
/*      */               
/*      */               return world;
/*      */             } 
/*      */             
/*      */             throw new WorldLoadCancelledException();
/*  578 */           })).thenCompose(World::init)
/*  579 */       .thenCompose(world -> 
/*  580 */         (!Options.getOptionSet().has(Options.MIGRATIONS) && start) ? world.start().thenApply(()) : CompletableFuture.completedFuture(world))
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  585 */       .whenComplete((world, throwable) -> {
/*      */           if (throwable != null) {
/*      */             String nameLower = name.toLowerCase();
/*      */             if (this.worlds.containsKey(nameLower)) {
/*      */               try {
/*      */                 removeWorldExceptionally(name);
/*  591 */               } catch (Exception e) {
/*      */                 ((HytaleLogger.Api)getLogger().at(Level.WARNING).withCause(e)).log("Failed to clean up world '%s' after init failure", name);
/*      */               } 
/*      */             }
/*      */           } 
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private CompletableFuture<Void> loadWorldFromStart(@Nonnull Path savePath, @Nonnull String name) {
/*  607 */     return this.worldConfigProvider.load(savePath, name)
/*  608 */       .thenCompose(worldConfig -> worldConfig.isDeleteOnUniverseStart() ? CompletableFuture.runAsync(()) : makeWorld(name, savePath, worldConfig).thenApply(()));
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
/*      */   @Nonnull
/*      */   @CheckReturnValue
/*      */   public CompletableFuture<World> loadWorld(@Nonnull String name) {
/*  632 */     if (this.worlds.containsKey(name)) {
/*  633 */       throw new IllegalArgumentException("World " + name + " already loaded!");
/*      */     }
/*      */     
/*  636 */     Path savePath = this.path.resolve("worlds").resolve(name);
/*  637 */     if (!Files.isDirectory(savePath, new java.nio.file.LinkOption[0])) {
/*  638 */       throw new IllegalArgumentException("World " + name + " does not exist!");
/*      */     }
/*      */     
/*  641 */     return this.worldConfigProvider.load(savePath, name)
/*  642 */       .thenCompose(worldConfig -> makeWorld(name, savePath, worldConfig));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public World getWorld(@Nullable String worldName) {
/*  653 */     if (worldName == null) return null; 
/*  654 */     return this.worlds.get(worldName.toLowerCase());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public World getWorld(@Nonnull UUID uuid) {
/*  665 */     return this.worldsByUuid.get(uuid);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public World getDefaultWorld() {
/*  673 */     HytaleServerConfig config = HytaleServer.get().getConfig();
/*  674 */     if (config == null) return null;
/*      */     
/*  676 */     String worldName = config.getDefaults().getWorld();
/*  677 */     return (worldName != null) ? getWorld(worldName) : null;
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
/*      */   public boolean removeWorld(@Nonnull String name) {
/*  689 */     Objects.requireNonNull(name, "Name can't be null!");
/*      */     
/*  691 */     String nameLower = name.toLowerCase();
/*  692 */     World world = this.worlds.get(nameLower);
/*  693 */     if (world == null) throw new NullPointerException("World " + name + " doesn't exist!");
/*      */ 
/*      */     
/*  696 */     RemoveWorldEvent event = (RemoveWorldEvent)HytaleServer.get().getEventBus().dispatchFor(RemoveWorldEvent.class, name).dispatch((IBaseEvent)new RemoveWorldEvent(world, RemoveWorldEvent.RemovalReason.GENERAL));
/*  697 */     if (event.isCancelled()) {
/*  698 */       return false;
/*      */     }
/*      */     
/*  701 */     this.worlds.remove(nameLower);
/*  702 */     this.worldsByUuid.remove(world.getWorldConfig().getUuid());
/*      */     
/*  704 */     if (world.isAlive()) world.stopIndividualWorld(); 
/*  705 */     world.validateDeleteOnRemove();
/*      */     
/*  707 */     return true;
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
/*      */   public void removeWorldExceptionally(@Nonnull String name) {
/*  719 */     Objects.requireNonNull(name, "Name can't be null!");
/*      */     
/*  721 */     getLogger().at(Level.INFO).log("Removing world exceptionally: %s", name);
/*      */     
/*  723 */     String nameLower = name.toLowerCase();
/*  724 */     World world = this.worlds.get(nameLower);
/*  725 */     if (world == null) throw new NullPointerException("World " + name + " doesn't exist!");
/*      */     
/*  727 */     HytaleServer.get().getEventBus().dispatchFor(RemoveWorldEvent.class, name)
/*  728 */       .dispatch((IBaseEvent)new RemoveWorldEvent(world, RemoveWorldEvent.RemovalReason.EXCEPTIONAL));
/*      */     
/*  730 */     this.worlds.remove(nameLower);
/*  731 */     this.worldsByUuid.remove(world.getWorldConfig().getUuid());
/*      */     
/*  733 */     if (world.isAlive()) world.stopIndividualWorld(); 
/*  734 */     world.validateDeleteOnRemove();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public Path getPath() {
/*  742 */     return this.path;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public Map<String, World> getWorlds() {
/*  750 */     return this.unmodifiableWorlds;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public List<PlayerRef> getPlayers() {
/*  758 */     return (List<PlayerRef>)new ObjectArrayList(this.players.values());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public PlayerRef getPlayer(@Nonnull UUID uuid) {
/*  769 */     return this.players.get(uuid);
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
/*      */   public PlayerRef getPlayer(@Nonnull String value, @Nonnull NameMatching matching) {
/*  781 */     return (PlayerRef)matching.find(this.players.values(), value, v -> ((PlayerRef)v.<PlayerRef>getComponent(PlayerRef.getComponentType())).getUsername());
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
/*      */   public PlayerRef getPlayer(@Nonnull String value, @Nonnull Comparator<String> comparator, @Nonnull BiPredicate<String, String> equality) {
/*  794 */     return (PlayerRef)NameMatching.find(this.players.values(), value, v -> ((PlayerRef)v.<PlayerRef>getComponent(PlayerRef.getComponentType())).getUsername(), comparator, equality);
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
/*      */   public PlayerRef getPlayerByUsername(@Nonnull String value, @Nonnull NameMatching matching) {
/*  806 */     return (PlayerRef)matching.find(this.players.values(), value, PlayerRef::getUsername);
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
/*      */   public PlayerRef getPlayerByUsername(@Nonnull String value, @Nonnull Comparator<String> comparator, @Nonnull BiPredicate<String, String> equality) {
/*  819 */     return (PlayerRef)NameMatching.find(this.players.values(), value, PlayerRef::getUsername, comparator, equality);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getPlayerCount() {
/*  826 */     return this.players.size();
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
/*      */   @Nonnull
/*      */   public CompletableFuture<PlayerRef> addPlayer(@Nonnull Channel channel, @Nonnull String language, @Nonnull ProtocolVersion protocolVersion, @Nonnull UUID uuid, @Nonnull String username, @Nonnull PlayerAuthentication auth, int clientViewRadiusChunks, @Nullable PlayerSkin skin) {
/*  851 */     GamePacketHandler playerConnection = new GamePacketHandler(channel, protocolVersion, auth);
/*  852 */     playerConnection.setQueuePackets(false);
/*      */     
/*  854 */     getLogger().at(Level.INFO).log("Adding player '%s (%s)", username, uuid);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  859 */     return this.playerStorage.load(uuid)
/*  860 */       .exceptionally(throwable -> {
/*      */           
/*      */           throw new RuntimeException("Exception when adding player to universe:", throwable);
/*  863 */         }).thenCompose(holder -> {
/*      */           ChunkTracker chunkTrackerComponent = new ChunkTracker();
/*      */           PlayerRef playerRefComponent = new PlayerRef(holder, uuid, username, language, (PacketHandler)playerConnection, chunkTrackerComponent);
/*      */           chunkTrackerComponent.setDefaultMaxChunksPerSecond(playerRefComponent);
/*      */           holder.putComponent(PlayerRef.getComponentType(), playerRefComponent);
/*      */           holder.putComponent(ChunkTracker.getComponentType(), (Component)chunkTrackerComponent);
/*      */           holder.putComponent(UUIDComponent.getComponentType(), (Component)new UUIDComponent(uuid));
/*      */           holder.ensureComponent(PositionDataComponent.getComponentType());
/*      */           holder.ensureComponent(MovementAudioComponent.getComponentType());
/*      */           Player playerComponent = (Player)holder.ensureAndGetComponent(Player.getComponentType());
/*      */           playerComponent.init(uuid, playerRefComponent);
/*      */           PlayerConfigData playerConfig = playerComponent.getPlayerConfigData();
/*      */           playerConfig.cleanup(this);
/*      */           PacketHandler.logConnectionTimings(channel, "Load Player Config", Level.FINEST);
/*      */           if (skin != null) {
/*      */             holder.putComponent(PlayerSkinComponent.getComponentType(), (Component)new PlayerSkinComponent(skin));
/*      */             holder.putComponent(ModelComponent.getComponentType(), (Component)new ModelComponent(CosmeticsModule.get().createModel(skin)));
/*      */           } 
/*      */           playerConnection.setPlayerRef(playerRefComponent, playerComponent);
/*      */           NettyUtil.setChannelHandler(channel, (PacketHandler)playerConnection);
/*      */           playerComponent.setClientViewRadius(clientViewRadiusChunks);
/*      */           EntityTrackerSystems.EntityViewer entityViewerComponent = (EntityTrackerSystems.EntityViewer)holder.getComponent(EntityTrackerSystems.EntityViewer.getComponentType());
/*      */           if (entityViewerComponent != null) {
/*      */             entityViewerComponent.viewRadiusBlocks = playerComponent.getViewRadius() * 32;
/*      */           } else {
/*      */             entityViewerComponent = new EntityTrackerSystems.EntityViewer(playerComponent.getViewRadius() * 32, (IPacketReceiver)playerConnection);
/*      */             holder.addComponent(EntityTrackerSystems.EntityViewer.getComponentType(), (Component)entityViewerComponent);
/*      */           } 
/*      */           PlayerRef existingPlayer = this.players.putIfAbsent(uuid, playerRefComponent);
/*      */           if (existingPlayer != null) {
/*      */             getLogger().at(Level.WARNING).log("Player '%s' (%s) already joining from another connection, rejecting duplicate", username, uuid);
/*      */             playerConnection.disconnect("A connection with this account is already in progress");
/*      */             return CompletableFuture.completedFuture(null);
/*      */           } 
/*      */           String lastWorldName = playerConfig.getWorld();
/*      */           World lastWorld = getWorld(lastWorldName);
/*      */           PlayerConnectEvent event = (PlayerConnectEvent)HytaleServer.get().getEventBus().dispatchFor(PlayerConnectEvent.class).dispatch((IBaseEvent)new PlayerConnectEvent(holder, playerRefComponent, (lastWorld != null) ? lastWorld : getDefaultWorld()));
/*      */           World world = (event.getWorld() != null) ? event.getWorld() : getDefaultWorld();
/*      */           if (world == null) {
/*      */             this.players.remove(uuid, playerRefComponent);
/*      */             playerConnection.disconnect("No world available to join");
/*      */             getLogger().at(Level.SEVERE).log("Player '%s' (%s) could not join - no default world configured", username, uuid);
/*      */             return CompletableFuture.completedFuture(null);
/*      */           } 
/*      */           if (lastWorldName != null && lastWorld == null) {
/*      */             playerComponent.sendMessage(Message.translation("server.universe.failedToFindWorld").param("lastWorldName", lastWorldName).param("name", world.getName()));
/*      */           }
/*      */           PacketHandler.logConnectionTimings(channel, "Processed Referral", Level.FINEST);
/*      */           playerRefComponent.getPacketHandler().write((Packet)new ServerTags((Map)AssetRegistry.getClientTags()));
/*      */           return world.addPlayer(playerRefComponent, null, Boolean.valueOf(false), Boolean.valueOf(false)).thenApply(()).exceptionally(());
/*      */         });
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
/*      */   public void removePlayer(@Nonnull PlayerRef playerRef) {
/*  961 */     getLogger().at(Level.INFO).log("Removing player '" + playerRef.getUsername() + "' (" + String.valueOf(playerRef.getUuid()) + ")");
/*      */ 
/*      */ 
/*      */     
/*  965 */     IEventDispatcher<PlayerDisconnectEvent, PlayerDisconnectEvent> eventDispatcher = HytaleServer.get().getEventBus().dispatchFor(PlayerDisconnectEvent.class);
/*  966 */     if (eventDispatcher.hasListener()) {
/*  967 */       eventDispatcher.dispatch((IBaseEvent)new PlayerDisconnectEvent(playerRef));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  975 */     Ref<EntityStore> ref = playerRef.getReference();
/*  976 */     if (ref == null) {
/*      */       
/*  978 */       finalizePlayerRemoval(playerRef);
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  984 */     World world = ((EntityStore)ref.getStore().getExternalData()).getWorld();
/*  985 */     if (world.isInThread()) {
/*      */ 
/*      */       
/*  988 */       Player playerComponent = (Player)ref.getStore().getComponent(ref, Player.getComponentType());
/*  989 */       if (playerComponent != null) {
/*  990 */         playerComponent.remove();
/*      */       }
/*      */ 
/*      */       
/*  994 */       finalizePlayerRemoval(playerRef);
/*      */     }
/*      */     else {
/*      */       
/*  998 */       CompletableFuture.runAsync(() -> { Player playerComponent = (Player)ref.getStore().getComponent(ref, Player.getComponentType()); if (playerComponent != null) playerComponent.remove();  }(Executor)world)
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1004 */         .orTimeout(5L, TimeUnit.SECONDS)
/* 1005 */         .whenComplete((result, error) -> {
/*      */             if (error != null) {
/*      */               ((HytaleLogger.Api)getLogger().at(Level.WARNING).withCause(error)).log("Timeout or error waiting for player '%s' removal from world store", playerRef.getUsername());
/*      */             }
/*      */             finalizePlayerRemoval(playerRef);
/*      */           });
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
/*      */ 
/*      */ 
/*      */   
/*      */   private void finalizePlayerRemoval(@Nonnull PlayerRef playerRef) {
/* 1025 */     this.players.remove(playerRef.getUuid());
/*      */     
/* 1027 */     if (Constants.SINGLEPLAYER) {
/* 1028 */       if (this.players.isEmpty()) {
/* 1029 */         getLogger().at(Level.INFO).log("No players left on singleplayer server shutting down!");
/* 1030 */         HytaleServer.get().shutdownServer();
/* 1031 */       } else if (SingleplayerModule.isOwner(playerRef)) {
/* 1032 */         getLogger().at(Level.INFO).log("Owner left the singleplayer server shutting down!");
/* 1033 */         getPlayers().forEach(p -> p.getPacketHandler().disconnect(playerRef.getUsername() + " left! Shutting down singleplayer world!"));
/* 1034 */         HytaleServer.get().shutdownServer();
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
/*      */   @Nonnull
/*      */   public CompletableFuture<PlayerRef> resetPlayer(@Nonnull PlayerRef oldPlayer) {
/* 1047 */     return this.playerStorage.load(oldPlayer.getUuid())
/* 1048 */       .exceptionally(throwable -> {
/*      */           
/*      */           throw new RuntimeException("Exception when adding player to universe:", throwable);
/* 1051 */         }).thenCompose(holder -> resetPlayer(oldPlayer, holder));
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
/*      */   public CompletableFuture<PlayerRef> resetPlayer(@Nonnull PlayerRef oldPlayer, @Nonnull Holder<EntityStore> holder) {
/* 1063 */     return resetPlayer(oldPlayer, holder, (World)null, (Transform)null);
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
/*      */   public CompletableFuture<PlayerRef> resetPlayer(@Nonnull PlayerRef playerRef, @Nonnull Holder<EntityStore> holder, @Nullable World world, @Nullable Transform transform) {
/*      */     World targetWorld;
/* 1077 */     UUID uuid = playerRef.getUuid();
/* 1078 */     Player oldPlayer = playerRef.<Player>getComponent(Player.getComponentType());
/*      */ 
/*      */     
/* 1081 */     if (world == null) {
/* 1082 */       targetWorld = oldPlayer.getWorld();
/*      */     } else {
/* 1084 */       targetWorld = world;
/*      */     } 
/*      */     
/* 1087 */     getLogger().at(Level.INFO).log("Resetting player '%s', moving to world '%s' at location %s (%s)", playerRef.getUsername(), (world != null) ? world.getName() : null, transform, playerRef.getUuid());
/*      */     
/* 1089 */     GamePacketHandler playerConnection = (GamePacketHandler)playerRef.getPacketHandler();
/*      */ 
/*      */     
/* 1092 */     Player newPlayer = (Player)holder.ensureAndGetComponent(Player.getComponentType());
/* 1093 */     newPlayer.init(uuid, playerRef);
/*      */     
/* 1095 */     CompletableFuture<Void> leaveWorld = new CompletableFuture<>();
/* 1096 */     if (oldPlayer.getWorld() != null) {
/* 1097 */       oldPlayer.getWorld().execute(() -> {
/*      */             playerRef.removeFromStore();
/*      */             leaveWorld.complete(null);
/*      */           });
/*      */     } else {
/* 1102 */       leaveWorld.complete(null);
/*      */     } 
/*      */     
/* 1105 */     return leaveWorld.thenAccept(v -> {
/*      */           oldPlayer.resetManagers(holder);
/*      */           
/*      */           newPlayer.copyFrom(oldPlayer);
/*      */           
/*      */           EntityTrackerSystems.EntityViewer viewer = (EntityTrackerSystems.EntityViewer)holder.getComponent(EntityTrackerSystems.EntityViewer.getComponentType());
/*      */           if (viewer != null) {
/*      */             viewer.viewRadiusBlocks = newPlayer.getViewRadius() * 32;
/*      */           } else {
/*      */             viewer = new EntityTrackerSystems.EntityViewer(newPlayer.getViewRadius() * 32, (IPacketReceiver)playerConnection);
/*      */             holder.addComponent(EntityTrackerSystems.EntityViewer.getComponentType(), (Component)viewer);
/*      */           } 
/*      */           playerConnection.setPlayerRef(playerRef, newPlayer);
/*      */           playerRef.replaceHolder(holder);
/*      */           holder.putComponent(PlayerRef.getComponentType(), playerRef);
/* 1120 */         }).thenCompose(v -> targetWorld.addPlayer(playerRef, transform));
/*      */   }
/*      */ 
/*      */   
/*      */   public void sendMessage(@Nonnull Message message) {
/* 1125 */     for (PlayerRef ref : this.players.values()) {
/* 1126 */       ref.sendMessage(message);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void broadcastPacket(@Nonnull Packet packet) {
/* 1137 */     for (PlayerRef player : this.players.values()) {
/* 1138 */       player.getPacketHandler().write(packet);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void broadcastPacketNoCache(@Nonnull Packet packet) {
/* 1148 */     for (PlayerRef player : this.players.values()) {
/* 1149 */       player.getPacketHandler().writeNoCache(packet);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void broadcastPacket(@Nonnull Packet... packets) {
/* 1159 */     for (PlayerRef player : this.players.values()) {
/* 1160 */       player.getPacketHandler().write(packets);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PlayerStorage getPlayerStorage() {
/* 1168 */     return this.playerStorage;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPlayerStorage(@Nonnull PlayerStorage playerStorage) {
/* 1177 */     this.playerStorage = playerStorage;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public WorldConfigProvider getWorldConfigProvider() {
/* 1184 */     return this.worldConfigProvider;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public ComponentType<EntityStore, PlayerRef> getPlayerRefComponentType() {
/* 1192 */     return this.playerRefComponentType;
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   @Deprecated
/*      */   public static Map<Integer, String> getLegacyBlockIdMap() {
/* 1198 */     return LEGACY_BLOCK_ID_MAP;
/*      */   }
/*      */   
/*      */   public static Path getWorldGenPath() {
/*      */     Path worldGenPath;
/* 1203 */     OptionSet optionSet = Options.getOptionSet();
/* 1204 */     if (optionSet.has(Options.WORLD_GEN_DIRECTORY)) {
/* 1205 */       worldGenPath = (Path)optionSet.valueOf(Options.WORLD_GEN_DIRECTORY);
/*      */     } else {
/* 1207 */       worldGenPath = AssetUtil.getHytaleAssetsPath().resolve("Server").resolve("World");
/*      */     } 
/* 1209 */     return worldGenPath;
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\Universe.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */