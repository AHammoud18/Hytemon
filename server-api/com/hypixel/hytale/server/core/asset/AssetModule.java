/*     */ package com.hypixel.hytale.server.core.asset;
/*     */ import com.hypixel.hytale.assetstore.AssetLoadResult;
/*     */ import com.hypixel.hytale.assetstore.AssetMap;
/*     */ import com.hypixel.hytale.assetstore.AssetPack;
/*     */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*     */ import com.hypixel.hytale.assetstore.AssetStore;
/*     */ import com.hypixel.hytale.assetstore.event.RegisterAssetStoreEvent;
/*     */ import com.hypixel.hytale.assetstore.event.RemoveAssetStoreEvent;
/*     */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.ExtraInfo;
/*     */ import com.hypixel.hytale.codec.util.RawJsonReader;
/*     */ import com.hypixel.hytale.common.plugin.PluginIdentifier;
/*     */ import com.hypixel.hytale.common.plugin.PluginManifest;
/*     */ import com.hypixel.hytale.common.util.FormatUtil;
/*     */ import com.hypixel.hytale.event.IBaseEvent;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.server.core.HytaleServer;
/*     */ import com.hypixel.hytale.server.core.HytaleServerConfig;
/*     */ import com.hypixel.hytale.server.core.Options;
/*     */ import com.hypixel.hytale.server.core.asset.monitor.AssetMonitor;
/*     */ import com.hypixel.hytale.server.core.asset.type.gameplay.respawn.HomeOrSpawnPoint;
/*     */ import com.hypixel.hytale.server.core.asset.type.gameplay.respawn.RespawnController;
/*     */ import com.hypixel.hytale.server.core.asset.type.gameplay.respawn.WorldSpawnPoint;
/*     */ import com.hypixel.hytale.server.core.event.events.BootEvent;
/*     */ import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
/*     */ import com.hypixel.hytale.server.core.universe.world.worldgen.IWorldGen;
/*     */ import com.hypixel.hytale.server.core.universe.world.worldgen.WorldGenLoadException;
/*     */ import com.hypixel.hytale.server.core.universe.world.worldgen.provider.IWorldGenProvider;
/*     */ import com.hypixel.hytale.server.core.universe.world.worldmap.provider.IWorldMapProvider;
/*     */ import com.hypixel.hytale.sneakythrow.SneakyThrow;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.FileReader;
/*     */ import java.io.IOException;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.nio.file.DirectoryStream;
/*     */ import java.nio.file.FileSystem;
/*     */ import java.nio.file.FileSystems;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.util.Arrays;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class AssetModule extends JavaPlugin {
/*  50 */   public static final PluginManifest MANIFEST = PluginManifest.corePlugin(AssetModule.class)
/*  51 */     .build(); private static AssetModule instance;
/*     */   @Nullable
/*     */   private AssetMonitor assetMonitor;
/*     */   
/*     */   public static AssetModule get() {
/*  56 */     return instance;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*  61 */   private final List<AssetPack> assetPacks = new CopyOnWriteArrayList<>();
/*     */   
/*     */   private boolean hasLoaded = false;
/*  64 */   private final List<AssetStore<?, ?, ?>> pendingAssetStores = new CopyOnWriteArrayList<>();
/*     */   
/*     */   public AssetModule(@Nonnull JavaPluginInit init) {
/*  67 */     super(init);
/*  68 */     instance = this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setup() {
/*  73 */     if (Options.getOptionSet().has(Options.DISABLE_FILE_WATCHER)) {
/*  74 */       getLogger().at(Level.WARNING).log("Not running asset watcher because --disable-file-watcher was set");
/*     */     } else {
/*     */       try {
/*  77 */         this.assetMonitor = new AssetMonitor();
/*  78 */         getLogger().at(Level.INFO).log("Asset monitor enabled!");
/*  79 */       } catch (IOException e) {
/*  80 */         ((HytaleLogger.Api)getLogger().at(Level.SEVERE).withCause(e)).log("Failed to create asset monitor!");
/*     */       } 
/*     */     } 
/*     */     
/*  84 */     List<Path> paths = Options.getOptionSet().valuesOf(Options.ASSET_DIRECTORY);
/*  85 */     for (Path path : paths) {
/*  86 */       loadAndRegisterPack(path);
/*     */     }
/*     */ 
/*     */     
/*  90 */     loadPacksFromDirectory(PluginManager.MODS_PATH);
/*     */ 
/*     */     
/*  93 */     for (Path modsPath : Options.getOptionSet().valuesOf(Options.MODS_DIRECTORIES)) {
/*  94 */       loadPacksFromDirectory(modsPath);
/*     */     }
/*     */     
/*  97 */     getEventRegistry().register((short)-16, LoadAssetEvent.class, event -> {
/*     */           if (this.hasLoaded) {
/*     */             throw new IllegalStateException("LoadAssetEvent has already been dispatched");
/*     */           }
/*     */           
/*     */           AssetRegistry.ASSET_LOCK.writeLock().lock();
/*     */           
/*     */           try {
/*     */             this.hasLoaded = true;
/*     */             AssetRegistryLoader.preLoadAssets(event);
/*     */             for (AssetPack pack : this.assetPacks) {
/*     */               AssetRegistryLoader.loadAssets(event, pack);
/*     */             }
/*     */           } finally {
/*     */             AssetRegistry.ASSET_LOCK.writeLock().unlock();
/*     */           } 
/*     */         });
/* 114 */     getEventRegistry().register((short)-16, AssetPackRegisterEvent.class, event -> AssetRegistryLoader.loadAssets(null, event.getAssetPack()));
/*     */ 
/*     */     
/* 117 */     getEventRegistry().register(AssetPackUnregisterEvent.class, event -> {
/*     */           for (AssetStore<?, ?, ?> assetStore : (Iterable<AssetStore<?, ?, ?>>)AssetRegistry.getStoreMap().values()) {
/*     */             assetStore.removeAssetPack(event.getAssetPack().getName());
/*     */           }
/*     */         });
/*     */     
/* 123 */     getEventRegistry().register(LoadAssetEvent.class, AssetModule::validateWorldGen);
/* 124 */     getEventRegistry().register(EventPriority.FIRST, LoadAssetEvent.class, SneakyThrow.sneakyConsumer(AssetRegistryLoader::writeSchemas));
/* 125 */     getEventRegistry().register(RegisterAssetStoreEvent.class, this::onNewStore);
/* 126 */     getEventRegistry().register(RemoveAssetStoreEvent.class, this::onRemoveStore);
/*     */     
/* 128 */     getEventRegistry().registerGlobal(BootEvent.class, event -> {
/*     */           StringBuilder sb = new StringBuilder("Total Loaded Assets: ");
/*     */           
/*     */           AssetStore[] assetStores = (AssetStore[])AssetRegistry.getStoreMap().values().toArray(());
/*     */           Arrays.sort(assetStores, Comparator.comparingInt(()));
/*     */           for (int i = assetStores.length - 1; i >= 0; i--) {
/*     */             AssetStore assetStore = assetStores[i];
/*     */             String simpleName = assetStore.getAssetClass().getSimpleName();
/*     */             int assetCount = assetStore.getAssetMap().getAssetCount();
/*     */             sb.append(simpleName).append(": ").append(assetCount).append(", ");
/*     */           } 
/*     */           sb.setLength(sb.length() - 2);
/*     */           getLogger().at(Level.INFO).log(sb.toString());
/*     */         });
/* 142 */     RespawnController.CODEC.register("HomeOrSpawnPoint", HomeOrSpawnPoint.class, (Codec)HomeOrSpawnPoint.CODEC);
/* 143 */     RespawnController.CODEC.register("WorldSpawnPoint", WorldSpawnPoint.class, (Codec)WorldSpawnPoint.CODEC);
/*     */     
/* 145 */     getCommandRegistry().registerCommand((AbstractCommand)new DroplistCommand());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void shutdown() {
/* 150 */     if (this.assetMonitor != null) {
/* 151 */       this.assetMonitor.shutdown();
/* 152 */       this.assetMonitor = null;
/*     */     } 
/*     */     
/* 155 */     for (AssetPack pack : this.assetPacks) {
/* 156 */       if (pack.getFileSystem() != null) {
/*     */         try {
/* 158 */           pack.getFileSystem().close();
/* 159 */         } catch (IOException e) {
/*     */           
/* 161 */           ((HytaleLogger.Api)getLogger().at(Level.WARNING).withCause(e)).log("Failed to close asset pack filesystem: %s", pack.getName());
/*     */         } 
/*     */       }
/*     */     } 
/* 165 */     this.assetPacks.clear();
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public AssetPack getBaseAssetPack() {
/* 170 */     return this.assetPacks.getFirst();
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public List<AssetPack> getAssetPacks() {
/* 175 */     return this.assetPacks;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public AssetMonitor getAssetMonitor() {
/* 180 */     return this.assetMonitor;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public AssetPack findAssetPackForPath(Path path) {
/* 185 */     path = path.toAbsolutePath().normalize();
/* 186 */     for (AssetPack pack : this.assetPacks) {
/* 187 */       if (path.startsWith(pack.getRoot())) return pack; 
/*     */     } 
/* 189 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAssetPathImmutable(@Nonnull Path path) {
/* 197 */     AssetPack pack = findAssetPackForPath(path);
/* 198 */     return (pack != null && pack.isImmutable());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private PluginManifest loadPackManifest(Path packPath) throws IOException {
/* 210 */     if (packPath.getFileName().toString().toLowerCase().endsWith(".zip"))
/* 211 */     { FileSystem fs = FileSystems.newFileSystem(packPath, (ClassLoader)null); 
/* 212 */       try { Path manifestPath = fs.getPath("manifest.json", new String[0]);
/* 213 */         if (Files.exists(manifestPath, new java.nio.file.LinkOption[0]))
/* 214 */         { BufferedReader reader = Files.newBufferedReader(manifestPath, StandardCharsets.UTF_8); 
/* 215 */           try { char[] buffer = RawJsonReader.READ_BUFFER.get();
/* 216 */             RawJsonReader rawJsonReader = new RawJsonReader(reader, buffer);
/* 217 */             ExtraInfo extraInfo = ExtraInfo.THREAD_LOCAL.get();
/* 218 */             PluginManifest manifest = (PluginManifest)PluginManifest.CODEC.decodeJson(rawJsonReader, extraInfo);
/* 219 */             extraInfo.getValidationResults().logOrThrowValidatorExceptions(getLogger());
/* 220 */             PluginManifest pluginManifest1 = manifest;
/* 221 */             if (reader != null) reader.close();
/*     */             
/* 223 */             if (fs != null) fs.close();  return pluginManifest1; } catch (Throwable throwable) { if (reader != null) try { reader.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  }  if (fs != null) fs.close();  } catch (Throwable throwable) { if (fs != null)
/* 224 */           try { fs.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } else if (Files.isDirectory(packPath, new java.nio.file.LinkOption[0]))
/*     */     
/* 226 */     { Path manifestPath = packPath.resolve("manifest.json");
/* 227 */       if (Files.exists(manifestPath, new java.nio.file.LinkOption[0])) {
/* 228 */         FileReader reader = new FileReader(manifestPath.toFile(), StandardCharsets.UTF_8); 
/* 229 */         try { char[] buffer = RawJsonReader.READ_BUFFER.get();
/* 230 */           RawJsonReader rawJsonReader = new RawJsonReader(reader, buffer);
/* 231 */           ExtraInfo extraInfo = ExtraInfo.THREAD_LOCAL.get();
/* 232 */           PluginManifest manifest = (PluginManifest)PluginManifest.CODEC.decodeJson(rawJsonReader, extraInfo);
/* 233 */           extraInfo.getValidationResults().logOrThrowValidatorExceptions(getLogger());
/* 234 */           PluginManifest pluginManifest1 = manifest;
/* 235 */           reader.close(); return pluginManifest1; } catch (Throwable throwable) { try { reader.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */            throw throwable; }
/*     */       
/*     */       }  }
/* 239 */      return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void loadPacksFromDirectory(Path modsPath) {
/* 248 */     if (!Files.isDirectory(modsPath, new java.nio.file.LinkOption[0]))
/*     */       return; 
/* 250 */     getLogger().at(Level.INFO).log("Loading packs from directory: %s", modsPath); 
/* 251 */     try { DirectoryStream<Path> stream = Files.newDirectoryStream(modsPath); 
/* 252 */       try { for (Path packPath : stream) {
/* 253 */           if (packPath.getFileName() == null || packPath.getFileName().toString().toLowerCase().endsWith(".jar"))
/* 254 */             continue;  loadAndRegisterPack(packPath);
/*     */         } 
/* 256 */         if (stream != null) stream.close();  } catch (Throwable throwable) { if (stream != null) try { stream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException e)
/* 257 */     { ((HytaleLogger.Api)getLogger().at(Level.SEVERE).withCause(e)).log("Failed to load mods from: %s", modsPath); }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void loadAndRegisterPack(Path packPath) {
/*     */     PluginManifest manifest;
/*     */     try {
/* 271 */       manifest = loadPackManifest(packPath);
/* 272 */       if (manifest == null) {
/* 273 */         getLogger().at(Level.WARNING).log("Skipping pack at %s: missing or invalid manifest.json", packPath.getFileName());
/*     */         return;
/*     */       } 
/* 276 */     } catch (Exception e) {
/* 277 */       ((HytaleLogger.Api)getLogger().at(Level.WARNING).withCause(e)).log("Failed to load manifest for pack at %s", packPath);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 282 */     PluginIdentifier packIdentifier = new PluginIdentifier(manifest);
/*     */ 
/*     */     
/* 285 */     HytaleServerConfig.ModConfig modConfig = (HytaleServerConfig.ModConfig)HytaleServer.get().getConfig().getModConfig().get(packIdentifier);
/* 286 */     boolean enabled = (modConfig == null || modConfig.getEnabled() == null || modConfig.getEnabled().booleanValue());
/*     */     
/* 288 */     String packId = packIdentifier.toString();
/* 289 */     if (enabled) {
/* 290 */       registerPack(packId, packPath, manifest);
/* 291 */       getLogger().at(Level.INFO).log("Loaded pack: %s from %s", packId, packPath.getFileName());
/*     */     } else {
/* 293 */       getLogger().at(Level.INFO).log("Skipped disabled pack: %s", packId);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void registerPack(@Nonnull String name, @Nonnull Path path, @Nonnull PluginManifest manifest) {
/* 298 */     Path absolutePath = path.toAbsolutePath().normalize();
/* 299 */     Path packLocation = absolutePath;
/* 300 */     FileSystem fileSystem = null;
/* 301 */     boolean isImmutable = false;
/*     */ 
/*     */     
/* 304 */     String lowerFileName = absolutePath.getFileName().toString().toLowerCase();
/* 305 */     if (lowerFileName.endsWith(".zip") || lowerFileName.endsWith(".jar")) {
/*     */       try {
/* 307 */         fileSystem = FileSystems.newFileSystem(absolutePath, (ClassLoader)null);
/* 308 */         absolutePath = fileSystem.getPath("", new String[0]).toAbsolutePath().normalize();
/* 309 */         isImmutable = true;
/* 310 */       } catch (IOException e) {
/* 311 */         throw SneakyThrow.sneakyThrow(e);
/*     */       } 
/*     */     } else {
/*     */       
/* 315 */       isImmutable = Files.isRegularFile(absolutePath.resolve("CommonAssetsIndex.hashes"), new java.nio.file.LinkOption[0]);
/*     */     } 
/*     */     
/* 318 */     AssetPack pack = new AssetPack(packLocation, name, absolutePath, fileSystem, isImmutable, manifest);
/* 319 */     this.assetPacks.add(pack);
/*     */     
/* 321 */     AssetRegistry.ASSET_LOCK.writeLock().lock();
/*     */     try {
/* 323 */       if (!this.hasLoaded)
/*     */         return; 
/* 325 */       HytaleServer.get().getEventBus()
/* 326 */         .dispatchFor(AssetPackRegisterEvent.class)
/* 327 */         .dispatch((IBaseEvent)new AssetPackRegisterEvent(pack));
/*     */     } finally {
/* 329 */       AssetRegistry.ASSET_LOCK.writeLock().unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void unregisterPack(@Nonnull String name) {
/* 334 */     AssetPack pack = getAssetPack(name);
/* 335 */     if (pack == null) {
/* 336 */       getLogger().at(Level.WARNING).log("Tried to unregister non-existent asset pack: %s", name);
/*     */       
/*     */       return;
/*     */     } 
/* 340 */     this.assetPacks.remove(pack);
/*     */     
/* 342 */     if (pack.getFileSystem() != null) {
/*     */       try {
/* 344 */         pack.getFileSystem().close();
/* 345 */       } catch (IOException e) {
/* 346 */         throw SneakyThrow.sneakyThrow(e);
/*     */       } 
/*     */     }
/*     */     
/* 350 */     AssetRegistry.ASSET_LOCK.writeLock().lock();
/*     */     try {
/* 352 */       HytaleServer.get().getEventBus()
/* 353 */         .dispatchFor(AssetPackUnregisterEvent.class)
/* 354 */         .dispatch((IBaseEvent)new AssetPackUnregisterEvent(pack));
/*     */     } finally {
/* 356 */       AssetRegistry.ASSET_LOCK.writeLock().unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   public AssetPack getAssetPack(@Nonnull String name) {
/* 361 */     for (AssetPack pack : this.assetPacks) {
/* 362 */       if (name.equals(pack.getName())) return pack; 
/*     */     } 
/* 364 */     return null;
/*     */   }
/*     */   
/*     */   private void onRemoveStore(@Nonnull RemoveAssetStoreEvent event) {
/* 368 */     AssetStore<?, ? extends JsonAssetWithMap<?, ? extends AssetMap<?, ?>>, ? extends AssetMap<?, ? extends JsonAssetWithMap<?, ?>>> assetStore = event.getAssetStore();
/* 369 */     String path = assetStore.getPath();
/* 370 */     if (path == null)
/*     */       return; 
/* 372 */     for (AssetPack pack : this.assetPacks) {
/* 373 */       if (pack.isImmutable())
/*     */         continue; 
/* 375 */       Path assetsPath = pack.getRoot().resolve("Server").resolve(path);
/* 376 */       if (Files.isDirectory(assetsPath, new java.nio.file.LinkOption[0])) {
/* 377 */         assetStore.removeFileMonitor(assetsPath);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void onNewStore(@Nonnull RegisterAssetStoreEvent event) {
/* 384 */     if (!AssetRegistry.HAS_INIT)
/* 385 */       return;  this.pendingAssetStores.add(event.getAssetStore());
/*     */   }
/*     */   
/*     */   public void initPendingStores() {
/* 389 */     for (int i = 0; i < this.pendingAssetStores.size(); i++) {
/* 390 */       initStore(this.pendingAssetStores.get(i));
/*     */     }
/* 392 */     this.pendingAssetStores.clear();
/*     */   }
/*     */   
/*     */   private void initStore(@Nonnull AssetStore<?, ?, ?> assetStore) {
/* 396 */     AssetRegistry.ASSET_LOCK.writeLock().lock();
/*     */     try {
/* 398 */       List<?> preAddedAssets = assetStore.getPreAddedAssets();
/* 399 */       if (preAddedAssets != null && !preAddedAssets.isEmpty()) {
/*     */         
/* 401 */         AssetLoadResult loadResult = assetStore.loadAssets("Hytale:Hytale", preAddedAssets);
/* 402 */         if (loadResult.hasFailed()) throw new RuntimeException("Failed to load asset store: " + String.valueOf(assetStore.getAssetClass()));
/*     */       
/*     */       } 
/* 405 */       for (AssetPack pack : this.assetPacks) {
/* 406 */         Path serverAssetDirectory = pack.getRoot().resolve("Server");
/* 407 */         String path = assetStore.getPath();
/* 408 */         if (path != null) {
/* 409 */           Path assetsPath = serverAssetDirectory.resolve(path);
/* 410 */           if (Files.isDirectory(assetsPath, new java.nio.file.LinkOption[0])) {
/* 411 */             AssetLoadResult<?, ? extends JsonAssetWithMap<?, ? extends AssetMap<?, ?>>> loadResult = assetStore.loadAssetsFromDirectory(pack.getName(), assetsPath);
/* 412 */             if (loadResult.hasFailed()) throw new RuntimeException("Failed to load asset store: " + String.valueOf(assetStore.getAssetClass())); 
/*     */           } else {
/* 414 */             getLogger().at(Level.SEVERE).log("Path for %s isn't a directory or doesn't exist: %s", assetStore.getAssetClass().getSimpleName(), assetsPath);
/*     */           } 
/*     */         } 
/*     */         
/* 418 */         assetStore.validateCodecDefaults();
/* 419 */         if (path != null) {
/* 420 */           Path assetsPath = serverAssetDirectory.resolve(path);
/* 421 */           if (Files.isDirectory(assetsPath, new java.nio.file.LinkOption[0])) {
/* 422 */             assetStore.addFileMonitor(pack.getName(), assetsPath);
/*     */           }
/*     */         } 
/*     */       } 
/* 426 */     } catch (IOException e) {
/* 427 */       throw SneakyThrow.sneakyThrow(e);
/*     */     } finally {
/* 429 */       AssetRegistry.ASSET_LOCK.writeLock().unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void validateWorldGen(@Nonnull LoadAssetEvent event) {
/* 435 */     if (!Options.getOptionSet().has(Options.VALIDATE_WORLD_GEN))
/* 436 */       return;  long start = System.nanoTime();
/*     */     
/*     */     try {
/* 439 */       IWorldGenProvider provider = (IWorldGenProvider)IWorldGenProvider.CODEC.getDefault();
/*     */ 
/*     */       
/* 442 */       IWorldGen generator = provider.getGenerator();
/*     */ 
/*     */       
/* 445 */       generator.getDefaultSpawnProvider(0);
/*     */       
/* 447 */       if (generator instanceof ValidatableWorldGen) {
/* 448 */         boolean valid = ((ValidatableWorldGen)generator).validate();
/* 449 */         if (!valid) event.failed(true, "failed to validate world gen");
/*     */       
/*     */       } 
/* 452 */       if (generator instanceof IWorldMapProvider) { IWorldMapProvider worldMapProvider = (IWorldMapProvider)generator;
/* 453 */         IWorldMap worldMap = worldMapProvider.getGenerator(null);
/*     */ 
/*     */         
/* 456 */         worldMap.getWorldMapSettings(); }
/*     */     
/* 458 */     } catch (WorldGenLoadException e) {
/* 459 */       ((HytaleLogger.Api)HytaleLogger.getLogger().at(Level.SEVERE).withCause((Throwable)e)).log("Failed to load default world gen!");
/* 460 */       HytaleLogger.getLogger().at(Level.SEVERE).log("\n" + e.getTraceMessage("\n"));
/* 461 */       event.failed(true, "failed to validate world gen: " + e.getTraceMessage(" -> "));
/* 462 */     } catch (Throwable e) {
/* 463 */       ((HytaleLogger.Api)HytaleLogger.getLogger().at(Level.SEVERE).withCause(e)).log("Failed to load default world gen!");
/* 464 */       event.failed(true, "failed to validate world gen");
/*     */     } 
/*     */     
/* 467 */     HytaleLogger.getLogger().at(Level.INFO).log("Validate world gen phase completed! Boot time %s, Took %s", FormatUtil.nanosToString(System.nanoTime() - event.getBootStart()), FormatUtil.nanosToString(System.nanoTime() - start));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\AssetModule.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */