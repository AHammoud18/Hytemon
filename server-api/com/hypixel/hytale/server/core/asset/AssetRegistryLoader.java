/*     */ package com.hypixel.hytale.server.core.asset;
/*     */ import com.hypixel.hytale.assetstore.AssetLoadResult;
/*     */ import com.hypixel.hytale.assetstore.AssetMap;
/*     */ import com.hypixel.hytale.assetstore.AssetPack;
/*     */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*     */ import com.hypixel.hytale.assetstore.AssetStore;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetCodec;
/*     */ import com.hypixel.hytale.assetstore.iterator.AssetStoreIterator;
/*     */ import com.hypixel.hytale.assetstore.iterator.CircularDependencyException;
/*     */ import com.hypixel.hytale.assetstore.map.BlockTypeAssetMap;
/*     */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*     */ import com.hypixel.hytale.assetstore.map.IndexedAssetMap;
/*     */ import com.hypixel.hytale.assetstore.map.IndexedLookupTableAssetMap;
/*     */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*     */ import com.hypixel.hytale.codec.EmptyExtraInfo;
/*     */ import com.hypixel.hytale.codec.ExtraInfo;
/*     */ import com.hypixel.hytale.codec.schema.SchemaContext;
/*     */ import com.hypixel.hytale.codec.schema.config.Schema;
/*     */ import com.hypixel.hytale.common.util.FormatUtil;
/*     */ import com.hypixel.hytale.event.IBaseEvent;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.protocol.ItemWithAllMetadata;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.server.core.HytaleServer;
/*     */ import com.hypixel.hytale.server.core.Options;
/*     */ import com.hypixel.hytale.server.core.ShutdownReason;
/*     */ import com.hypixel.hytale.server.core.asset.packet.AssetPacketGenerator;
/*     */ import com.hypixel.hytale.server.core.asset.type.ambiencefx.AmbienceFXPacketGenerator;
/*     */ import com.hypixel.hytale.server.core.asset.type.ambiencefx.config.AmbienceFX;
/*     */ import com.hypixel.hytale.server.core.asset.type.audiocategory.config.AudioCategory;
/*     */ import com.hypixel.hytale.server.core.asset.type.blockbreakingdecal.config.BlockBreakingDecal;
/*     */ import com.hypixel.hytale.server.core.asset.type.blockhitbox.BlockBoundingBoxes;
/*     */ import com.hypixel.hytale.server.core.asset.type.blockhitbox.BlockBoundingBoxesPacketGenerator;
/*     */ import com.hypixel.hytale.server.core.asset.type.blockparticle.config.BlockParticleSet;
/*     */ import com.hypixel.hytale.server.core.asset.type.blockset.config.BlockSet;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocksound.config.BlockSoundSet;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.BlockGroupPacketGenerator;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.BlockTypePacketGenerator;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockMigration;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.asset.type.buildertool.config.BlockTypeListAsset;
/*     */ import com.hypixel.hytale.server.core.asset.type.buildertool.config.PrefabListAsset;
/*     */ import com.hypixel.hytale.server.core.asset.type.camera.CameraEffect;
/*     */ import com.hypixel.hytale.server.core.asset.type.entityeffect.EntityEffectPacketGenerator;
/*     */ import com.hypixel.hytale.server.core.asset.type.entityeffect.config.EntityEffect;
/*     */ import com.hypixel.hytale.server.core.asset.type.environment.EnvironmentPacketGenerator;
/*     */ import com.hypixel.hytale.server.core.asset.type.environment.config.Environment;
/*     */ import com.hypixel.hytale.server.core.asset.type.equalizereffect.config.EqualizerEffect;
/*     */ import com.hypixel.hytale.server.core.asset.type.fluid.Fluid;
/*     */ import com.hypixel.hytale.server.core.asset.type.fluid.FluidTypePacketGenerator;
/*     */ import com.hypixel.hytale.server.core.asset.type.fluidfx.FluidFXPacketGenerator;
/*     */ import com.hypixel.hytale.server.core.asset.type.fluidfx.config.FluidFX;
/*     */ import com.hypixel.hytale.server.core.asset.type.gamemode.GameModeType;
/*     */ import com.hypixel.hytale.server.core.asset.type.gameplay.GameplayConfig;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.FieldcraftCategoryPacketGenerator;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.BlockGroup;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.BuilderToolItemReferenceAsset;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.CraftingRecipe;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.FieldcraftCategory;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.Item;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.ItemCategory;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.ItemDropList;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.ItemQuality;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.ItemReticleConfig;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.ItemToolSpec;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.ResourceType;
/*     */ import com.hypixel.hytale.server.core.asset.type.itemanimation.config.ItemPlayerAnimations;
/*     */ import com.hypixel.hytale.server.core.asset.type.itemsound.ItemSoundSetPacketGenerator;
/*     */ import com.hypixel.hytale.server.core.asset.type.itemsound.config.ItemSoundSet;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.ModelAsset;
/*     */ import com.hypixel.hytale.server.core.asset.type.modelvfx.config.ModelVFX;
/*     */ import com.hypixel.hytale.server.core.asset.type.particle.config.ParticleSpawner;
/*     */ import com.hypixel.hytale.server.core.asset.type.particle.config.ParticleSystem;
/*     */ import com.hypixel.hytale.server.core.asset.type.portalworld.PortalType;
/*     */ import com.hypixel.hytale.server.core.asset.type.projectile.config.Projectile;
/*     */ import com.hypixel.hytale.server.core.asset.type.responsecurve.config.ResponseCurve;
/*     */ import com.hypixel.hytale.server.core.asset.type.reverbeffect.config.ReverbEffect;
/*     */ import com.hypixel.hytale.server.core.asset.type.soundevent.SoundEventPacketGenerator;
/*     */ import com.hypixel.hytale.server.core.asset.type.soundevent.config.SoundEvent;
/*     */ import com.hypixel.hytale.server.core.asset.type.soundset.SoundSetPacketGenerator;
/*     */ import com.hypixel.hytale.server.core.asset.type.soundset.config.SoundSet;
/*     */ import com.hypixel.hytale.server.core.asset.type.tagpattern.config.AndPatternOp;
/*     */ import com.hypixel.hytale.server.core.asset.type.tagpattern.config.EqualsTagOp;
/*     */ import com.hypixel.hytale.server.core.asset.type.tagpattern.config.NotPatternOp;
/*     */ import com.hypixel.hytale.server.core.asset.type.tagpattern.config.OrPatternOp;
/*     */ import com.hypixel.hytale.server.core.asset.type.tagpattern.config.TagPattern;
/*     */ import com.hypixel.hytale.server.core.asset.type.trail.TrailPacketGenerator;
/*     */ import com.hypixel.hytale.server.core.asset.type.trail.config.Trail;
/*     */ import com.hypixel.hytale.server.core.asset.type.weather.WeatherPacketGenerator;
/*     */ import com.hypixel.hytale.server.core.asset.type.weather.config.Weather;
/*     */ import com.hypixel.hytale.server.core.asset.type.wordlist.WordList;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.io.PacketHandler;
/*     */ import com.hypixel.hytale.server.core.modules.entity.damage.DamageCause;
/*     */ import com.hypixel.hytale.server.core.modules.entity.hitboxcollision.HitboxCollisionConfig;
/*     */ import com.hypixel.hytale.server.core.modules.entitystats.asset.EntityStatType;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction;
/*     */ import com.hypixel.hytale.server.core.modules.item.CraftingRecipePacketGenerator;
/*     */ import com.hypixel.hytale.server.core.modules.item.ItemPacketGenerator;
/*     */ import com.hypixel.hytale.server.core.modules.projectile.config.ProjectileConfig;
/*     */ import com.hypixel.hytale.server.core.util.BsonUtil;
/*     */ import com.hypixel.hytale.sneakythrow.SneakyThrow;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.attribute.FileAttribute;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Function;
/*     */ import java.util.logging.Level;
/*     */ import java.util.stream.Stream;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ import org.bson.BsonArray;
/*     */ import org.bson.BsonDocument;
/*     */ import org.bson.BsonString;
/*     */ import org.bson.BsonValue;
/*     */ 
/*     */ public class AssetRegistryLoader {
/* 126 */   public static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */ 
/*     */ 
/*     */   
/*     */   public static void init() {}
/*     */ 
/*     */   
/*     */   public static void preLoadAssets(@Nonnull LoadAssetEvent event) {
/*     */     try {
/* 135 */       preLoadAssets0(event);
/* 136 */     } catch (Throwable t) {
/* 137 */       event.failed(true, "failed to validate assets");
/* 138 */       throw SneakyThrow.sneakyThrow(t);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void loadAssets(@Nullable LoadAssetEvent event, @Nonnull AssetPack assetPack) {
/* 144 */     AssetRegistry.ASSET_LOCK.writeLock().lock();
/*     */     try {
/* 146 */       loadAssets0(event, assetPack);
/* 147 */       AssetRegistry.HAS_INIT = true;
/* 148 */     } catch (Throwable t) {
/* 149 */       if (event != null) event.failed(true, "failed to validate assets"); 
/* 150 */       throw SneakyThrow.sneakyThrow(t);
/*     */     } finally {
/* 152 */       AssetRegistry.ASSET_LOCK.writeLock().unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void preLoadAssets0(@Nonnull LoadAssetEvent event) {
/* 157 */     AssetStore.DISABLE_DYNAMIC_DEPENDENCIES = true;
/*     */     
/* 159 */     Collection<AssetStore<?, ?, ?>> values = AssetRegistry.getStoreMap().values();
/* 160 */     LOGGER.at(Level.INFO).log("Loading %s asset stores...", values.size());
/*     */     
/* 162 */     for (AssetStore<?, ?, ?> assetStore : values) {
/* 163 */       assetStore.simplifyLoadBeforeDependencies();
/*     */     }
/*     */     
/* 166 */     boolean failedToLoadAsset = false;
/*     */     
/* 168 */     LOGGER.at(Level.INFO).log("Pre-adding assets...");
/* 169 */     AssetStoreIterator iterator = new AssetStoreIterator(values); 
/* 170 */     try { while (iterator.hasNext())
/*     */       
/* 172 */       { if (HytaleServer.get().isShuttingDown())
/* 173 */         { LOGGER.at(Level.INFO).log("Aborted asset loading due to server shutdown!");
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
/* 202 */           iterator.close(); return; }  AssetStore<?, ? extends JsonAssetWithMap<?, ? extends AssetMap<?, ?>>, ? extends AssetMap<?, ? extends JsonAssetWithMap<?, ?>>> assetStore = iterator.next(); if (assetStore == null) throw new CircularDependencyException(values, iterator);  long start = System.nanoTime(); Class<? extends JsonAssetWithMap<?, ? extends AssetMap<?, ?>>> assetClass = assetStore.getAssetClass(); try { List<?> preAddedAssets = assetStore.getPreAddedAssets(); if (preAddedAssets != null && !preAddedAssets.isEmpty()) { AssetLoadResult loadResult = assetStore.loadAssets("Hytale:Hytale", preAddedAssets); failedToLoadAsset |= loadResult.hasFailed(); }  } catch (Exception e) { failedToLoadAsset = true; long end = System.nanoTime(); long diff = end - start; if (iterator.isBeingWaitedFor(assetStore)) throw new RuntimeException(String.format("Failed to pre-add %s took %s", new Object[] { assetClass.getSimpleName(), FormatUtil.nanosToString(diff) }), e);  ((HytaleLogger.Api)LOGGER.at(Level.SEVERE).withCause(e)).log("Failed to pre-add %s took %s", assetClass.getSimpleName(), FormatUtil.nanosToString(diff)); }  }  iterator.close(); } catch (Throwable throwable) { try { iterator.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */        throw throwable; }
/* 204 */      if (failedToLoadAsset) event.failed(Options.getOptionSet().has(Options.VALIDATE_ASSETS), "failed to validate internal assets"); 
/*     */   }
/*     */   
/*     */   private static void loadAssets0(@Nullable LoadAssetEvent event, @Nonnull AssetPack assetPack) {
/* 208 */     AssetStore.DISABLE_DYNAMIC_DEPENDENCIES = true;
/* 209 */     Path serverAssetDirectory = assetPack.getRoot().resolve("Server");
/* 210 */     HytaleLogger.getLogger().at(Level.INFO).log("Loading assets from: %s", serverAssetDirectory);
/*     */     
/* 212 */     long startAll = System.nanoTime();
/* 213 */     boolean failedToLoadAsset = false;
/*     */     
/* 215 */     LOGGER.at(Level.INFO).log("Loading assets from %s", serverAssetDirectory);
/* 216 */     Collection<AssetStore<?, ?, ?>> values = AssetRegistry.getStoreMap().values();
/*     */     
/* 218 */     AssetStoreIterator iterator = new AssetStoreIterator(values); 
/* 219 */     try { while (iterator.hasNext())
/*     */       
/* 221 */       { if (HytaleServer.get().isShuttingDown())
/* 222 */         { LOGGER.at(Level.INFO).log("Aborted asset loading due to server shutdown!");
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
/* 253 */           iterator.close(); return; }  AssetStore<?, ? extends JsonAssetWithMap<?, ? extends AssetMap<?, ?>>, ? extends AssetMap<?, ? extends JsonAssetWithMap<?, ?>>> assetStore = iterator.next(); if (assetStore == null) throw new CircularDependencyException(values, iterator);  long start = System.nanoTime(); Class<? extends JsonAssetWithMap<?, ? extends AssetMap<?, ?>>> assetClass = assetStore.getAssetClass(); try { String path = assetStore.getPath(); if (path != null) { Path assetsPath = serverAssetDirectory.resolve(path); if (Files.isDirectory(assetsPath, new java.nio.file.LinkOption[0])) { AssetLoadResult<?, ? extends JsonAssetWithMap<?, ? extends AssetMap<?, ?>>> loadResult = assetStore.loadAssetsFromDirectory(assetPack.getName(), assetsPath); failedToLoadAsset |= loadResult.hasFailed(); }  }  } catch (Exception e) { failedToLoadAsset = true; long end = System.nanoTime(); long diff = end - start; if (iterator.isBeingWaitedFor(assetStore)) throw new RuntimeException(String.format("Failed to load %s from path '%s' took %s", new Object[] { assetClass.getSimpleName(), assetStore.getPath(), FormatUtil.nanosToString(diff) }), e);  ((HytaleLogger.Api)LOGGER.at(Level.SEVERE).withCause(e)).log("Failed to load %s from path '%s' took %s", assetClass.getSimpleName(), assetStore.getPath(), FormatUtil.nanosToString(diff)); }  }  iterator.close(); } catch (Throwable throwable) { try { iterator.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */        throw throwable; }
/* 255 */      for (AssetStore<?, ?, ?> assetStore : values) {
/* 256 */       if (assetPack.getName().equals("Hytale:Hytale"))
/*     */       {
/* 258 */         assetStore.validateCodecDefaults();
/*     */       }
/*     */ 
/*     */       
/* 262 */       String path = assetStore.getPath();
/* 263 */       if (path != null) {
/* 264 */         Path assetsPath = serverAssetDirectory.resolve(path);
/* 265 */         if (Files.isDirectory(assetsPath, new java.nio.file.LinkOption[0]) && !assetPack.isImmutable()) {
/* 266 */           assetStore.addFileMonitor(assetPack.getName(), assetsPath);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 271 */     long endAll = System.nanoTime();
/* 272 */     long diffAll = endAll - startAll;
/* 273 */     LOGGER.at(Level.INFO).log("Took %s to load all assets", FormatUtil.nanosToString(diffAll));
/*     */     
/* 275 */     if (failedToLoadAsset && event != null) event.failed(Options.getOptionSet().has(Options.VALIDATE_ASSETS), "failed to validate assets"); 
/*     */   }
/*     */   
/*     */   public static void sendAssets(@Nonnull PacketHandler packetHandler) {
/* 279 */     Objects.requireNonNull(packetHandler); Consumer<Packet[]> packetConsumer = packetHandler::write;
/* 280 */     Objects.requireNonNull(packetHandler); Consumer<Packet> singlePacketConsumer = packetHandler::write;
/*     */     
/* 282 */     AssetRegistry.ASSET_LOCK.writeLock().lock();
/*     */     try {
/* 284 */       HytaleAssetStore.SETUP_PACKET_CONSUMERS.add(singlePacketConsumer);
/*     */     } finally {
/* 286 */       AssetRegistry.ASSET_LOCK.writeLock().unlock();
/*     */     } 
/*     */     
/*     */     try {
/* 290 */       for (AssetStore<?, ?, ?> assetStore : (Iterable<AssetStore<?, ?, ?>>)AssetRegistry.getStoreMap().values())
/*     */       {
/* 292 */         ((HytaleAssetStore)assetStore).sendAssets(packetConsumer);
/*     */       }
/*     */     } finally {
/* 295 */       AssetRegistry.ASSET_LOCK.writeLock().lock();
/*     */       try {
/* 297 */         HytaleAssetStore.SETUP_PACKET_CONSUMERS.remove(singlePacketConsumer);
/*     */       } finally {
/* 299 */         AssetRegistry.ASSET_LOCK.writeLock().unlock();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Map<String, Schema> generateSchemas(@Nonnull SchemaContext context, @Nonnull BsonDocument vsCodeConfig) {
/* 306 */     AssetStore[] values = (AssetStore[])AssetRegistry.getStoreMap().values().toArray(x$0 -> new AssetStore[x$0]);
/* 307 */     Arrays.sort(values, Comparator.comparing(store -> store.getAssetClass().getSimpleName()));
/*     */     
/* 309 */     BsonArray vsCodeSchemas = new BsonArray();
/* 310 */     BsonDocument vsCodeFiles = new BsonDocument();
/* 311 */     vsCodeConfig.put("json.schemas", (BsonValue)vsCodeSchemas);
/* 312 */     vsCodeConfig.put("files.associations", (BsonValue)vsCodeFiles);
/* 313 */     vsCodeConfig.put("editor.tabSize", (BsonValue)new BsonInt32(2));
/*     */     
/* 315 */     for (AssetStore store : values) {
/* 316 */       Class assetClass = store.getAssetClass();
/* 317 */       String name = assetClass.getSimpleName();
/* 318 */       AssetCodec codec = store.getCodec();
/* 319 */       context.addFileReference(name + ".json", (SchemaConvertable)codec);
/*     */     } 
/*     */     
/* 322 */     HashMap<String, Schema> schemas = new HashMap<>();
/* 323 */     for (AssetStore store : values) {
/* 324 */       Class assetClass = store.getAssetClass();
/* 325 */       String path = store.getPath();
/* 326 */       String name = assetClass.getSimpleName();
/* 327 */       AssetCodec codec = store.getCodec();
/*     */       
/* 329 */       Schema schema = codec.toSchema(context);
/* 330 */       if (codec instanceof com.hypixel.hytale.assetstore.codec.AssetCodecMapCodec) schema.setTitle(name); 
/* 331 */       schema.setId(name + ".json");
/* 332 */       Schema.HytaleMetadata hytale = schema.getHytale();
/* 333 */       hytale.setPath(path);
/* 334 */       hytale.setExtension(store.getExtension());
/*     */       
/* 336 */       Class idProvider = store.getIdProvider();
/* 337 */       if (idProvider != null) {
/* 338 */         hytale.setIdProvider(idProvider.getSimpleName());
/*     */       }
/*     */       
/* 341 */       List preload = store.getPreAddedAssets();
/* 342 */       if (preload != null && !preload.isEmpty()) {
/* 343 */         String[] internal = new String[preload.size()];
/* 344 */         for (int i = 0; i < preload.size(); i++) {
/* 345 */           Object p = preload.get(i);
/* 346 */           Object k = store.getKeyFunction().apply(p);
/* 347 */           internal[i] = k.toString();
/*     */         } 
/* 349 */         hytale.setInternalKeys(internal);
/*     */       } 
/*     */       
/* 352 */       BsonDocument config = new BsonDocument();
/* 353 */       config.put("fileMatch", (BsonValue)new BsonArray(List.of(new BsonString("/Server/" + path + "/*" + store
/* 354 */                 .getExtension()), new BsonString("/Server/" + path + "/**/*" + store
/* 355 */                 .getExtension()))));
/*     */       
/* 357 */       config.put("url", (BsonValue)new BsonString("./Schema/" + name + ".json"));
/* 358 */       vsCodeSchemas.add((BsonValue)config);
/* 359 */       if (!store.getExtension().equals(".json")) {
/* 360 */         vsCodeFiles.put("*" + store.getExtension(), (BsonValue)new BsonString("json"));
/*     */       }
/*     */       
/* 363 */       schemas.put(name + ".json", schema);
/*     */     } 
/*     */     
/* 366 */     HytaleServer.get().getEventBus()
/* 367 */       .dispatchFor(GenerateSchemaEvent.class)
/* 368 */       .dispatch((IBaseEvent)new GenerateSchemaEvent(schemas, context, vsCodeConfig));
/*     */     
/* 370 */     Schema definitions = new Schema();
/* 371 */     definitions.setDefinitions(context.getDefinitions());
/* 372 */     definitions.setId("common.json");
/*     */     
/* 374 */     schemas.put("common.json", definitions);
/*     */     
/* 376 */     Schema otherDefinitions = new Schema();
/* 377 */     otherDefinitions.setDefinitions(context.getOtherDefinitions());
/* 378 */     otherDefinitions.setId("other.json");
/*     */     
/* 380 */     schemas.put("other.json", otherDefinitions);
/*     */     
/* 382 */     return schemas;
/*     */   }
/*     */   
/*     */   public static void writeSchemas(LoadAssetEvent event) {
/* 386 */     if (!Options.getOptionSet().has(Options.GENERATE_SCHEMA))
/*     */       return; 
/*     */     try {
/* 389 */       AssetPack pack = AssetModule.get().getBaseAssetPack();
/* 390 */       if (pack.isImmutable()) {
/* 391 */         LOGGER.at(Level.SEVERE).log("Not generating schema due launcher assets");
/* 392 */         HytaleServer.get().shutdownServer(ShutdownReason.VALIDATE_ERROR.withMessage("Not generating scheme due launcher assets"));
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 397 */       BsonDocument vsCodeConfig = new BsonDocument();
/*     */       
/* 399 */       Path assetDirectory = pack.getRoot();
/* 400 */       Path schemaDir = assetDirectory.resolve("Schema");
/* 401 */       Files.createDirectories(schemaDir, (FileAttribute<?>[])new FileAttribute[0]);
/*     */       
/* 403 */       Stream<Path> stream = Files.walk(schemaDir, 1, new java.nio.file.FileVisitOption[0]); 
/* 404 */       try { stream
/* 405 */           .filter(v -> v.toString().endsWith(".json"))
/* 406 */           .forEach(SneakyThrow.sneakyConsumer(Files::delete));
/* 407 */         if (stream != null) stream.close();  } catch (Throwable throwable) { if (stream != null)
/*     */           try { stream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }
/* 409 */        SchemaContext context = new SchemaContext();
/* 410 */       Map<String, Schema> schemas = generateSchemas(context, vsCodeConfig);
/*     */       
/* 412 */       for (Map.Entry<String, Schema> schema : schemas.entrySet()) {
/* 413 */         BsonUtil.writeDocument(schemaDir
/* 414 */             .resolve(schema.getKey()), Schema.CODEC
/* 415 */             .encode(schema.getValue(), (ExtraInfo)EmptyExtraInfo.EMPTY).asDocument(), false)
/*     */           
/* 417 */           .join();
/*     */       }
/*     */       
/* 420 */       Files.createDirectories(assetDirectory.resolve(".vscode"), (FileAttribute<?>[])new FileAttribute[0]);
/* 421 */       BsonUtil.writeDocument(assetDirectory.resolve(".vscode/settings.json"), vsCodeConfig, false).join();
/* 422 */     } catch (Throwable t) {
/* 423 */       ((HytaleLogger.Api)LOGGER.at(Level.SEVERE).withCause(t)).log("Schema generation failed");
/* 424 */       HytaleServer.get().shutdownServer(ShutdownReason.CRASH.withMessage("Schema generation failed"));
/*     */       return;
/*     */     } 
/* 427 */     HytaleServer.get().shutdownServer(ShutdownReason.SHUTDOWN.withMessage("Schema generated"));
/*     */   }
/*     */   
/*     */   static {
/* 431 */     AssetRegistry.register(((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.<AmbienceFX, IndexedAssetMap>builder(AmbienceFX.class, new IndexedAssetMap())
/* 432 */         .setPath("Audio/AmbienceFX"))
/* 433 */         .setCodec((AssetCodec)AmbienceFX.CODEC))
/* 434 */         .setKeyFunction(AmbienceFX::getId))
/* 435 */         .setReplaceOnRemove(AmbienceFX::new))
/* 436 */         .setPacketGenerator((AssetPacketGenerator)new AmbienceFXPacketGenerator())
/* 437 */         .loadsAfter(new Class[] { Weather.class, Environment.class, FluidFX.class, SoundEvent.class, BlockSoundSet.class, TagPattern.class, AudioCategory.class, ReverbEffect.class, EqualizerEffect.class
/*     */           
/* 439 */           })).preLoadAssets(Collections.singletonList(AmbienceFX.EMPTY)))
/* 440 */         .build());
/*     */     
/* 442 */     AssetRegistry.register(((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.<BlockBoundingBoxes, IndexedLookupTableAssetMap>builder(BlockBoundingBoxes.class, new IndexedLookupTableAssetMap(x$0 -> new BlockBoundingBoxes[x$0]))
/* 443 */         .setPath("Item/Block/Hitboxes"))
/* 444 */         .setCodec((AssetCodec)BlockBoundingBoxes.CODEC))
/* 445 */         .setKeyFunction(BlockBoundingBoxes::getId))
/* 446 */         .setReplaceOnRemove(BlockBoundingBoxes::getUnitBoxFor))
/* 447 */         .setPacketGenerator((AssetPacketGenerator)new BlockBoundingBoxesPacketGenerator())
/* 448 */         .preLoadAssets(Collections.singletonList(BlockBoundingBoxes.UNIT_BOX)))
/* 449 */         .build());
/*     */     
/* 451 */     AssetRegistry.register(((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.<BlockSet, IndexedLookupTableAssetMap>builder(BlockSet.class, new IndexedLookupTableAssetMap(x$0 -> new BlockSet[x$0]))
/* 452 */         .setPath("Item/Block/Sets"))
/* 453 */         .setCodec((AssetCodec)BlockSet.CODEC))
/* 454 */         .setKeyFunction(BlockSet::getId))
/* 455 */         .setReplaceOnRemove(BlockSet::new))
/* 456 */         .setPacketGenerator((AssetPacketGenerator)new BlockSetPacketGenerator())
/* 457 */         .loadsBefore(new Class[] { Item.class
/* 458 */           })).build());
/*     */     
/* 460 */     AssetRegistry.register(((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.<BlockSoundSet, IndexedLookupTableAssetMap>builder(BlockSoundSet.class, new IndexedLookupTableAssetMap(x$0 -> new BlockSoundSet[x$0]))
/* 461 */         .setPath("Item/Block/Sounds"))
/* 462 */         .setCodec((AssetCodec)BlockSoundSet.CODEC))
/* 463 */         .setKeyFunction(BlockSoundSet::getId))
/* 464 */         .setReplaceOnRemove(BlockSoundSet::new))
/* 465 */         .setPacketGenerator((AssetPacketGenerator)new BlockSoundSetPacketGenerator())
/* 466 */         .loadsAfter(new Class[] { SoundEvent.class
/* 467 */           })).preLoadAssets(Collections.singletonList(BlockSoundSet.EMPTY_BLOCK_SOUND_SET)))
/* 468 */         .build());
/*     */     
/* 470 */     AssetRegistry.register(((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.<ItemSoundSet, IndexedLookupTableAssetMap>builder(ItemSoundSet.class, new IndexedLookupTableAssetMap(x$0 -> new ItemSoundSet[x$0]))
/* 471 */         .setPath("Audio/ItemSounds"))
/* 472 */         .setCodec((AssetCodec)ItemSoundSet.CODEC))
/* 473 */         .setKeyFunction(ItemSoundSet::getId))
/* 474 */         .setReplaceOnRemove(ItemSoundSet::new))
/* 475 */         .setPacketGenerator((AssetPacketGenerator)new ItemSoundSetPacketGenerator())
/* 476 */         .loadsAfter(new Class[] { SoundEvent.class
/* 477 */           })).build());
/*     */     
/* 479 */     AssetRegistry.register(((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.<BlockParticleSet, DefaultAssetMap>builder(BlockParticleSet.class, new DefaultAssetMap())
/* 480 */         .setPath("Item/Block/Particles"))
/* 481 */         .setCodec((AssetCodec)BlockParticleSet.CODEC))
/* 482 */         .setKeyFunction(BlockParticleSet::getId))
/* 483 */         .setPacketGenerator((AssetPacketGenerator)new BlockParticleSetPacketGenerator())
/* 484 */         .loadsAfter(new Class[] { ParticleSystem.class
/* 485 */           })).build());
/*     */     
/* 487 */     AssetRegistry.register(((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.<BlockBreakingDecal, DefaultAssetMap>builder(BlockBreakingDecal.class, new DefaultAssetMap())
/* 488 */         .setPath("Item/Block/BreakingDecals"))
/* 489 */         .setCodec(BlockBreakingDecal.CODEC))
/* 490 */         .setKeyFunction(BlockBreakingDecal::getId))
/* 491 */         .setPacketGenerator((AssetPacketGenerator)new BlockBreakingDecalPacketGenerator())
/* 492 */         .build());
/*     */     
/* 494 */     AssetRegistry.register(((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.<Integer, BlockMigration, DefaultAssetMap>builder(Integer.class, BlockMigration.class, new DefaultAssetMap())
/* 495 */         .setPath("Item/Block/Migrations"))
/* 496 */         .setCodec((AssetCodec)BlockMigration.CODEC))
/* 497 */         .setKeyFunction(BlockMigration::getId))
/* 498 */         .build());
/*     */     
/* 500 */     BlockTypeAssetMap<String, BlockType> blockTypeAssetMap = new BlockTypeAssetMap(x$0 -> new BlockType[x$0], BlockType::getGroup);
/* 501 */     AssetRegistry.register(((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.<BlockType, BlockTypeAssetMap<String, BlockType>>builder(BlockType.class, blockTypeAssetMap)
/* 502 */         .setPath("Item/Block/Blocks"))
/* 503 */         .setCodec((AssetCodec)BlockType.CODEC))
/* 504 */         .setKeyFunction(BlockType::getId))
/* 505 */         .setPacketGenerator((AssetPacketGenerator)new BlockTypePacketGenerator())
/* 506 */         .loadsAfter(new Class[] { BlockBoundingBoxes.class, BlockSoundSet.class, SoundEvent.class, BlockParticleSet.class, BlockBreakingDecal.class, CustomConnectedBlockTemplateAsset.class, PrefabListAsset.class, BlockTypeListAsset.class
/*     */           
/* 508 */           })).setNotificationItemFunction(item -> (new ItemStack(item, 1)).toPacket())
/* 509 */         .setReplaceOnRemove(BlockType::getUnknownFor))
/* 510 */         .preLoadAssets(Arrays.asList(new BlockType[] { BlockType.EMPTY, BlockType.UNKNOWN, BlockType.DEBUG_CUBE, BlockType.DEBUG_MODEL
/* 511 */             }))).setIdProvider(Item.class))
/* 512 */         .build());
/*     */     
/* 514 */     AssetRegistry.register(((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.<Fluid, IndexedLookupTableAssetMap>builder(Fluid.class, new IndexedLookupTableAssetMap(x$0 -> new Fluid[x$0]))
/* 515 */         .setPath("Item/Block/Fluids"))
/* 516 */         .setCodec((AssetCodec)Fluid.CODEC))
/* 517 */         .setKeyFunction(Fluid::getId))
/* 518 */         .setReplaceOnRemove(Fluid::getUnknownFor))
/* 519 */         .setPacketGenerator((AssetPacketGenerator)new FluidTypePacketGenerator())
/* 520 */         .loadsAfter(new Class[] { FluidFX.class, BlockSoundSet.class, BlockParticleSet.class, SoundEvent.class
/* 521 */           })).preLoadAssets(List.of(Fluid.EMPTY, Fluid.UNKNOWN)))
/* 522 */         .build());
/*     */     
/* 524 */     AssetRegistry.register(((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.<ItemPlayerAnimations, DefaultAssetMap>builder(ItemPlayerAnimations.class, new DefaultAssetMap())
/* 525 */         .setPath("Item/Animations"))
/* 526 */         .setCodec((AssetCodec)ItemPlayerAnimations.CODEC))
/* 527 */         .setKeyFunction(ItemPlayerAnimations::getId))
/* 528 */         .setPacketGenerator((AssetPacketGenerator)new ItemPlayerAnimationsPacketGenerator())
/* 529 */         .build());
/*     */ 
/*     */     
/* 532 */     AssetRegistry.register(((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.<Environment, IndexedLookupTableAssetMap>builder(Environment.class, new IndexedLookupTableAssetMap(x$0 -> new Environment[x$0]))
/* 533 */         .setPath("Environments"))
/* 534 */         .setCodec((AssetCodec)Environment.CODEC))
/* 535 */         .setKeyFunction(Environment::getId))
/* 536 */         .setReplaceOnRemove(Environment::getUnknownFor))
/* 537 */         .setPacketGenerator((AssetPacketGenerator)new EnvironmentPacketGenerator())
/* 538 */         .loadsAfter(new Class[] { Weather.class, FluidFX.class, ParticleSystem.class
/* 539 */           })).preLoadAssets(Collections.singletonList(Environment.UNKNOWN)))
/* 540 */         .build());
/*     */     
/* 542 */     AssetRegistry.register(((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.<FluidFX, IndexedLookupTableAssetMap>builder(FluidFX.class, new IndexedLookupTableAssetMap(x$0 -> new FluidFX[x$0]))
/* 543 */         .setPath("Item/Block/FluidFX"))
/* 544 */         .setCodec((AssetCodec)FluidFX.CODEC))
/* 545 */         .setKeyFunction(FluidFX::getId))
/* 546 */         .setReplaceOnRemove(FluidFX::getUnknownFor))
/* 547 */         .setPacketGenerator((AssetPacketGenerator)new FluidFXPacketGenerator())
/* 548 */         .loadsAfter(new Class[] { ParticleSystem.class
/* 549 */           })).preLoadAssets(Collections.singletonList(FluidFX.EMPTY_FLUID_FX)))
/* 550 */         .build());
/*     */ 
/*     */     
/* 553 */     AssetRegistry.register(((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.<ItemCategory, DefaultAssetMap>builder(ItemCategory.class, new DefaultAssetMap(Collections.synchronizedMap((Map<?, ?>)new Object2ObjectLinkedOpenHashMap())))
/* 554 */         .setPath("Item/Category/CreativeLibrary"))
/* 555 */         .setCodec((AssetCodec)ItemCategory.CODEC))
/* 556 */         .setKeyFunction(ItemCategory::getId))
/* 557 */         .setPacketGenerator((AssetPacketGenerator)new ItemCategoryPacketGenerator())
/* 558 */         .build());
/*     */ 
/*     */     
/* 561 */     AssetRegistry.register(((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.<FieldcraftCategory, DefaultAssetMap>builder(FieldcraftCategory.class, new DefaultAssetMap(Collections.synchronizedMap((Map<?, ?>)new Object2ObjectLinkedOpenHashMap())))
/* 562 */         .setPath("Item/Category/Fieldcraft"))
/* 563 */         .setCodec((AssetCodec)FieldcraftCategory.CODEC))
/* 564 */         .setKeyFunction(FieldcraftCategory::getId))
/* 565 */         .setPacketGenerator((AssetPacketGenerator)new FieldcraftCategoryPacketGenerator())
/* 566 */         .build());
/*     */     
/* 568 */     AssetRegistry.register(((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.<ItemDropList, DefaultAssetMap>builder(ItemDropList.class, new DefaultAssetMap())
/* 569 */         .setPath("Drops"))
/* 570 */         .setCodec((AssetCodec)ItemDropList.CODEC))
/* 571 */         .setKeyFunction(ItemDropList::getId))
/* 572 */         .build());
/*     */     
/* 574 */     AssetRegistry.register(((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.<WordList, DefaultAssetMap>builder(WordList.class, new DefaultAssetMap())
/* 575 */         .setPath("WordLists"))
/* 576 */         .setCodec((AssetCodec)WordList.CODEC))
/* 577 */         .setKeyFunction(WordList::getId))
/* 578 */         .build());
/*     */     
/* 580 */     AssetRegistry.register(((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.<ItemReticleConfig, IndexedLookupTableAssetMap>builder(ItemReticleConfig.class, new IndexedLookupTableAssetMap(x$0 -> new ItemReticleConfig[x$0]))
/* 581 */         .setPath("Item/Reticles"))
/* 582 */         .setCodec((AssetCodec)ItemReticleConfig.CODEC))
/* 583 */         .setKeyFunction(ItemReticleConfig::getId))
/* 584 */         .setReplaceOnRemove(ItemReticleConfig::new))
/* 585 */         .setPacketGenerator((AssetPacketGenerator)new ItemReticleConfigPacketGenerator())
/* 586 */         .preLoadAssets(Collections.singletonList(ItemReticleConfig.DEFAULT)))
/* 587 */         .build());
/*     */     
/* 589 */     AssetRegistry.register(((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.<ItemToolSpec, DefaultAssetMap>builder(ItemToolSpec.class, new DefaultAssetMap())
/* 590 */         .setPath("Item/Unarmed/Gathering"))
/* 591 */         .setCodec(ItemToolSpec.CODEC))
/* 592 */         .setKeyFunction(ItemToolSpec::getGatherType))
/* 593 */         .loadsAfter(new Class[] { SoundEvent.class
/* 594 */           })).build());
/*     */     
/* 596 */     AssetRegistry.register(((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.<PortalType, DefaultAssetMap>builder(PortalType.class, new DefaultAssetMap())
/* 597 */         .setPath("PortalTypes"))
/* 598 */         .setCodec((AssetCodec)PortalType.CODEC))
/* 599 */         .setKeyFunction(PortalType::getId))
/* 600 */         .build());
/*     */     
/* 602 */     AssetRegistry.register(((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.<Item, DefaultAssetMap>builder(Item.class, new DefaultAssetMap())
/* 603 */         .setPath("Item/Items"))
/* 604 */         .setCodec(Item.CODEC))
/* 605 */         .setKeyFunction(Item::getId))
/* 606 */         .setPacketGenerator((AssetPacketGenerator)new ItemPacketGenerator())
/* 607 */         .loadsAfter(new Class[] {
/*     */ 
/*     */             
/*     */             ItemCategory.class, ItemPlayerAnimations.class, UnarmedInteractions.class, ResourceType.class, BlockType.class, EntityEffect.class, ItemQuality.class, ItemReticleConfig.class, SoundEvent.class, PortalType.class,
/*     */ 
/*     */             
/*     */             ItemSoundSet.class
/* 614 */           })).setNotificationItemFunction(item -> (new ItemStack(item, 1)).toPacket())
/* 615 */         .preLoadAssets(Collections.singletonList(Item.UNKNOWN)))
/* 616 */         .build());
/*     */     
/* 618 */     AssetRegistry.register(((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.<CraftingRecipe, DefaultAssetMap>builder(CraftingRecipe.class, new DefaultAssetMap())
/* 619 */         .setPath("Item/Recipes"))
/* 620 */         .setCodec((AssetCodec)CraftingRecipe.CODEC))
/* 621 */         .setKeyFunction(CraftingRecipe::getId))
/* 622 */         .setPacketGenerator((AssetPacketGenerator)new CraftingRecipePacketGenerator())
/* 623 */         .loadsAfter(new Class[] { Item.class, BlockType.class
/* 624 */           })).build());
/*     */     
/* 626 */     AssetRegistry.register(((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.<ModelAsset, DefaultAssetMap>builder(ModelAsset.class, new DefaultAssetMap())
/* 627 */         .setPath("Models"))
/* 628 */         .setCodec((AssetCodec)ModelAsset.CODEC))
/* 629 */         .setKeyFunction(ModelAsset::getId))
/* 630 */         .loadsAfter(new Class[] { ParticleSystem.class, SoundEvent.class, Trail.class
/* 631 */           })).preLoadAssets(List.of(ModelAsset.DEBUG)))
/* 632 */         .build());
/*     */     
/* 634 */     AssetRegistry.register(((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.<ParticleSpawner, DefaultAssetMap>builder(ParticleSpawner.class, new DefaultAssetMap())
/* 635 */         .setPath("Particles"))
/* 636 */         .setExtension(".particlespawner"))
/* 637 */         .setCodec((AssetCodec)ParticleSpawner.CODEC))
/* 638 */         .setKeyFunction(ParticleSpawner::getId))
/* 639 */         .setPacketGenerator((AssetPacketGenerator)new ParticleSpawnerPacketGenerator())
/* 640 */         .build());
/*     */     
/* 642 */     AssetRegistry.register(((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.<ParticleSystem, DefaultAssetMap>builder(ParticleSystem.class, new DefaultAssetMap())
/* 643 */         .setPath("Particles"))
/* 644 */         .setExtension(".particlesystem"))
/* 645 */         .setCodec((AssetCodec)ParticleSystem.CODEC))
/* 646 */         .setKeyFunction(ParticleSystem::getId))
/* 647 */         .setPacketGenerator((AssetPacketGenerator)new ParticleSystemPacketGenerator())
/* 648 */         .loadsAfter(new Class[] { ParticleSpawner.class
/* 649 */           })).build());
/*     */     
/* 651 */     AssetRegistry.register(((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.<Trail, DefaultAssetMap>builder(Trail.class, new DefaultAssetMap())
/* 652 */         .setPath("Entity/Trails"))
/* 653 */         .setCodec((AssetCodec)Trail.CODEC))
/* 654 */         .setKeyFunction(Trail::getId))
/* 655 */         .setPacketGenerator((AssetPacketGenerator)new TrailPacketGenerator())
/* 656 */         .build());
/*     */     
/* 658 */     AssetRegistry.register(((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.<Projectile, DefaultAssetMap>builder(Projectile.class, new DefaultAssetMap())
/* 659 */         .setPath("Projectiles"))
/* 660 */         .setCodec((AssetCodec)Projectile.CODEC))
/* 661 */         .setKeyFunction(Projectile::getId))
/* 662 */         .loadsAfter(new Class[] { SoundEvent.class, ModelAsset.class, ParticleSystem.class
/* 663 */           })).build());
/*     */     
/* 665 */     AssetRegistry.register(((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.<EntityEffect, IndexedLookupTableAssetMap>builder(EntityEffect.class, new IndexedLookupTableAssetMap(x$0 -> new EntityEffect[x$0]))
/* 666 */         .setPath("Entity/Effects"))
/* 667 */         .setCodec((AssetCodec)EntityEffect.CODEC))
/* 668 */         .setKeyFunction(EntityEffect::getId))
/* 669 */         .setReplaceOnRemove(EntityEffect::new))
/* 670 */         .setPacketGenerator((AssetPacketGenerator)new EntityEffectPacketGenerator())
/* 671 */         .loadsAfter(new Class[] { ModelAsset.class, ParticleSystem.class, EntityStatType.class, ModelVFX.class, DamageCause.class, CameraEffect.class, SoundEvent.class
/* 672 */           })).build());
/*     */     
/* 674 */     AssetRegistry.register(((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.<ModelVFX, IndexedLookupTableAssetMap>builder(ModelVFX.class, new IndexedLookupTableAssetMap(x$0 -> new ModelVFX[x$0]))
/* 675 */         .setPath("Entity/ModelVFX"))
/* 676 */         .setCodec((AssetCodec)ModelVFX.CODEC))
/* 677 */         .setKeyFunction(ModelVFX::getId))
/* 678 */         .setReplaceOnRemove(ModelVFX::new))
/* 679 */         .setPacketGenerator((AssetPacketGenerator)new ModelVFXPacketGenerator())
/*     */         
/* 681 */         .build());
/*     */     
/* 683 */     AssetRegistry.register(((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.<GameModeType, DefaultAssetMap>builder(GameModeType.class, new DefaultAssetMap())
/* 684 */         .setPath("Entity/GameMode"))
/* 685 */         .setCodec((AssetCodec)GameModeType.CODEC))
/* 686 */         .setKeyFunction(GameModeType::getId))
/* 687 */         .loadsAfter(new Class[] { Interaction.class
/* 688 */           })).build());
/*     */     
/* 690 */     AssetRegistry.register(((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.<ResourceType, DefaultAssetMap>builder(ResourceType.class, new DefaultAssetMap())
/* 691 */         .setPath("Item/ResourceTypes"))
/* 692 */         .setCodec((AssetCodec)ResourceType.CODEC))
/* 693 */         .setKeyFunction(ResourceType::getId))
/* 694 */         .setPacketGenerator((AssetPacketGenerator)new ResourceTypePacketGenerator())
/* 695 */         .build());
/*     */     
/* 697 */     AssetRegistry.register(((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.<Weather, IndexedLookupTableAssetMap>builder(Weather.class, new IndexedLookupTableAssetMap(x$0 -> new Weather[x$0]))
/* 698 */         .setPath("Weathers"))
/* 699 */         .setCodec((AssetCodec)Weather.CODEC))
/* 700 */         .setKeyFunction(Weather::getId))
/* 701 */         .setReplaceOnRemove(Weather::new))
/* 702 */         .setPacketGenerator((AssetPacketGenerator)new WeatherPacketGenerator())
/* 703 */         .loadsAfter(new Class[] { ParticleSystem.class
/* 704 */           })).preLoadAssets(Collections.singletonList(Weather.UNKNOWN)))
/* 705 */         .build());
/*     */     
/* 707 */     AssetRegistry.register(((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.<GameplayConfig, DefaultAssetMap>builder(GameplayConfig.class, new DefaultAssetMap())
/* 708 */         .setPath("GameplayConfigs"))
/* 709 */         .setCodec((AssetCodec)GameplayConfig.CODEC))
/* 710 */         .setKeyFunction(GameplayConfig::getId))
/* 711 */         .loadsAfter(new Class[] { Item.class, SoundEvent.class, SoundSet.class, BlockType.class, EntityEffect.class, HitboxCollisionConfig.class, DamageCause.class, RepulsionConfig.class, ParticleSystem.class, AmbienceFX.class
/*     */           
/* 713 */           })).build());
/*     */     
/* 715 */     AssetRegistry.register(((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.<SoundEvent, IndexedLookupTableAssetMap>builder(SoundEvent.class, new IndexedLookupTableAssetMap(x$0 -> new SoundEvent[x$0]))
/* 716 */         .setPath("Audio/SoundEvents"))
/* 717 */         .setCodec((AssetCodec)SoundEvent.CODEC))
/* 718 */         .setKeyFunction(SoundEvent::getId))
/* 719 */         .setReplaceOnRemove(SoundEvent::new))
/* 720 */         .setPacketGenerator((AssetPacketGenerator)new SoundEventPacketGenerator())
/* 721 */         .preLoadAssets(Collections.singletonList(SoundEvent.EMPTY_SOUND_EVENT)))
/* 722 */         .loadsAfter(new Class[] { AudioCategory.class
/* 723 */           })).build());
/*     */     
/* 725 */     AssetRegistry.register(((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.<SoundSet, IndexedLookupTableAssetMap>builder(SoundSet.class, new IndexedLookupTableAssetMap(x$0 -> new SoundSet[x$0]))
/* 726 */         .setPath("Audio/SoundSets"))
/* 727 */         .setCodec((AssetCodec)SoundSet.CODEC))
/* 728 */         .setKeyFunction(SoundSet::getId))
/* 729 */         .setReplaceOnRemove(SoundSet::new))
/* 730 */         .setPacketGenerator((AssetPacketGenerator)new SoundSetPacketGenerator())
/* 731 */         .loadsAfter(new Class[] { SoundEvent.class
/* 732 */           })).preLoadAssets(Collections.singletonList(SoundSet.EMPTY_SOUND_SET)))
/* 733 */         .build());
/*     */     
/* 735 */     AssetRegistry.register(((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.<AudioCategory, IndexedLookupTableAssetMap>builder(AudioCategory.class, new IndexedLookupTableAssetMap(x$0 -> new AudioCategory[x$0]))
/* 736 */         .setPath("Audio/AudioCategories"))
/* 737 */         .setCodec((AssetCodec)AudioCategory.CODEC))
/* 738 */         .setKeyFunction(AudioCategory::getId))
/* 739 */         .setReplaceOnRemove(AudioCategory::new))
/* 740 */         .setPacketGenerator((AssetPacketGenerator)new AudioCategoryPacketGenerator())
/* 741 */         .preLoadAssets(Collections.singletonList(AudioCategory.EMPTY_AUDIO_CATEGORY)))
/* 742 */         .build());
/*     */     
/* 744 */     AssetRegistry.register(((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.<ReverbEffect, IndexedLookupTableAssetMap>builder(ReverbEffect.class, new IndexedLookupTableAssetMap(x$0 -> new ReverbEffect[x$0]))
/* 745 */         .setPath("Audio/Reverb"))
/* 746 */         .setCodec((AssetCodec)ReverbEffect.CODEC))
/* 747 */         .setKeyFunction(ReverbEffect::getId))
/* 748 */         .setReplaceOnRemove(ReverbEffect::new))
/* 749 */         .setPacketGenerator((AssetPacketGenerator)new ReverbEffectPacketGenerator())
/* 750 */         .preLoadAssets(Collections.singletonList(ReverbEffect.EMPTY_REVERB_EFFECT)))
/* 751 */         .build());
/*     */     
/* 753 */     AssetRegistry.register(((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.<EqualizerEffect, IndexedLookupTableAssetMap>builder(EqualizerEffect.class, new IndexedLookupTableAssetMap(x$0 -> new EqualizerEffect[x$0]))
/* 754 */         .setPath("Audio/EQ"))
/* 755 */         .setCodec((AssetCodec)EqualizerEffect.CODEC))
/* 756 */         .setKeyFunction(EqualizerEffect::getId))
/* 757 */         .setReplaceOnRemove(EqualizerEffect::new))
/* 758 */         .setPacketGenerator((AssetPacketGenerator)new EqualizerEffectPacketGenerator())
/* 759 */         .preLoadAssets(Collections.singletonList(EqualizerEffect.EMPTY_EQUALIZER_EFFECT)))
/* 760 */         .build());
/*     */     
/* 762 */     AssetRegistry.register(((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.<ResponseCurve, IndexedLookupTableAssetMap>builder(ResponseCurve.class, new IndexedLookupTableAssetMap(x$0 -> new ResponseCurve[x$0]))
/* 763 */         .setPath("ResponseCurves"))
/* 764 */         .setCodec((AssetCodec)ResponseCurve.CODEC))
/* 765 */         .setKeyFunction(ResponseCurve::getId))
/* 766 */         .setReplaceOnRemove(com.hypixel.hytale.server.core.asset.type.responsecurve.config.ExponentialResponseCurve::new))
/* 767 */         .build());
/*     */     
/* 769 */     AssetRegistry.register(((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.<ItemQuality, IndexedLookupTableAssetMap>builder(ItemQuality.class, new IndexedLookupTableAssetMap(x$0 -> new ItemQuality[x$0]))
/* 770 */         .setPath("Item/Qualities"))
/* 771 */         .setCodec((AssetCodec)ItemQuality.CODEC))
/* 772 */         .setKeyFunction(ItemQuality::getId))
/* 773 */         .setPacketGenerator((AssetPacketGenerator)new ItemQualityPacketGenerator())
/* 774 */         .setReplaceOnRemove(ItemQuality::new))
/* 775 */         .loadsAfter(new Class[] { ParticleSystem.class
/* 776 */           })).preLoadAssets(Collections.singletonList(ItemQuality.DEFAULT_ITEM_QUALITY)))
/* 777 */         .build());
/*     */     
/* 779 */     AssetRegistry.register(((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.<DamageCause, IndexedLookupTableAssetMap>builder(DamageCause.class, new IndexedLookupTableAssetMap(x$0 -> new DamageCause[x$0]))
/* 780 */         .setPath("Entity/Damage"))
/* 781 */         .setCodec((AssetCodec)DamageCause.CODEC))
/* 782 */         .setKeyFunction(DamageCause::getId))
/* 783 */         .setReplaceOnRemove(DamageCause::new))
/* 784 */         .loadsBefore(new Class[] { Item.class, Interaction.class
/* 785 */           })).build());
/*     */     
/* 787 */     AssetRegistry.register(((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.<ProjectileConfig, DefaultAssetMap>builder(ProjectileConfig.class, new DefaultAssetMap())
/* 788 */         .setPath("ProjectileConfigs"))
/* 789 */         .setCodec((AssetCodec)ProjectileConfig.CODEC))
/* 790 */         .setKeyFunction(ProjectileConfig::getId))
/* 791 */         .loadsAfter(new Class[] { Interaction.class, SoundEvent.class, ModelAsset.class, ParticleSystem.class
/* 792 */           })).setPacketGenerator((AssetPacketGenerator)new ProjectileConfigPacketGenerator())
/* 793 */         .build());
/*     */     
/* 795 */     AssetRegistry.register(((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.<BlockGroup, DefaultAssetMap>builder(BlockGroup.class, new DefaultAssetMap())
/* 796 */         .setPath("Item/Groups"))
/* 797 */         .setCodec(BlockGroup.CODEC))
/* 798 */         .setKeyFunction(BlockGroup::getId))
/* 799 */         .loadsAfter(new Class[] { BlockType.class, Item.class
/* 800 */           })).setPacketGenerator((AssetPacketGenerator)new BlockGroupPacketGenerator())
/* 801 */         .build());
/*     */     
/* 803 */     AssetRegistry.register(((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.<BuilderToolItemReferenceAsset, DefaultAssetMap>builder(BuilderToolItemReferenceAsset.class, new DefaultAssetMap())
/* 804 */         .setPath("Item/PlayerToolsMenuConfig"))
/* 805 */         .setCodec(BuilderToolItemReferenceAsset.CODEC))
/* 806 */         .setKeyFunction(BuilderToolItemReferenceAsset::getId))
/* 807 */         .loadsAfter(new Class[] { BlockType.class, Item.class
/* 808 */           })).build());
/*     */     
/* 810 */     AssetRegistry.register(((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.<BlockTypeListAsset, DefaultAssetMap>builder(BlockTypeListAsset.class, new DefaultAssetMap())
/* 811 */         .setPath("BlockTypeList"))
/* 812 */         .setKeyFunction(BlockTypeListAsset::getId))
/* 813 */         .setCodec((AssetCodec)BlockTypeListAsset.CODEC))
/* 814 */         .build());
/*     */     
/* 816 */     AssetRegistry.register(((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.<PrefabListAsset, DefaultAssetMap>builder(PrefabListAsset.class, new DefaultAssetMap())
/* 817 */         .setPath("PrefabList"))
/* 818 */         .setKeyFunction(PrefabListAsset::getId))
/* 819 */         .setCodec((AssetCodec)PrefabListAsset.CODEC))
/* 820 */         .build());
/*     */     
/* 822 */     AssetRegistry.register(((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.<CameraEffect, IndexedLookupTableAssetMap>builder(CameraEffect.class, new IndexedLookupTableAssetMap(x$0 -> new CameraEffect[x$0]))
/* 823 */         .loadsBefore(new Class[] { GameplayConfig.class, Interaction.class
/* 824 */           })).setPath("Camera/CameraEffect"))
/* 825 */         .setCodec((AssetCodec)CameraEffect.CODEC))
/* 826 */         .setKeyFunction(CameraEffect::getId))
/* 827 */         .setReplaceOnRemove(com.hypixel.hytale.server.core.asset.type.camera.CameraEffect.MissingCameraEffect::new))
/* 828 */         .build());
/*     */     
/* 830 */     AssetRegistry.register(((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.<TagPattern, IndexedLookupTableAssetMap>builder(TagPattern.class, new IndexedLookupTableAssetMap(x$0 -> new TagPattern[x$0]))
/* 831 */         .setPath("TagPatterns"))
/* 832 */         .setCodec((AssetCodec)TagPattern.CODEC))
/* 833 */         .setKeyFunction(TagPattern::getId))
/* 834 */         .setReplaceOnRemove(EqualsTagOp::new))
/* 835 */         .setPacketGenerator((AssetPacketGenerator)new TagPatternPacketGenerator())
/* 836 */         .build());
/*     */     
/* 838 */     TagPattern.CODEC.register("Equals", EqualsTagOp.class, EqualsTagOp.CODEC);
/* 839 */     TagPattern.CODEC.register("And", AndPatternOp.class, AndPatternOp.CODEC);
/* 840 */     TagPattern.CODEC.register("Or", OrPatternOp.class, OrPatternOp.CODEC);
/* 841 */     TagPattern.CODEC.register("Not", NotPatternOp.class, NotPatternOp.CODEC);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\AssetRegistryLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */