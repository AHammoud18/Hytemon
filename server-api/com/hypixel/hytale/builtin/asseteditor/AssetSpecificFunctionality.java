/*     */ package com.hypixel.hytale.builtin.asseteditor;
/*     */ import com.hypixel.hytale.assetstore.AssetStore;
/*     */ import com.hypixel.hytale.assetstore.AssetUpdateQuery;
/*     */ import com.hypixel.hytale.assetstore.event.LoadedAssetsEvent;
/*     */ import com.hypixel.hytale.assetstore.map.IndexedLookupTableAssetMap;
/*     */ import com.hypixel.hytale.builtin.asseteditor.assettypehandler.AssetStoreTypeHandler;
/*     */ import com.hypixel.hytale.builtin.asseteditor.assettypehandler.AssetTypeHandler;
/*     */ import com.hypixel.hytale.builtin.asseteditor.event.AssetEditorActivateButtonEvent;
/*     */ import com.hypixel.hytale.builtin.asseteditor.event.AssetEditorAssetCreatedEvent;
/*     */ import com.hypixel.hytale.builtin.asseteditor.event.AssetEditorClientDisconnectEvent;
/*     */ import com.hypixel.hytale.builtin.asseteditor.event.AssetEditorFetchAutoCompleteDataEvent;
/*     */ import com.hypixel.hytale.builtin.asseteditor.event.AssetEditorRequestDataSetEvent;
/*     */ import com.hypixel.hytale.builtin.asseteditor.event.AssetEditorSelectAssetEvent;
/*     */ import com.hypixel.hytale.builtin.asseteditor.event.AssetEditorUpdateWeatherPreviewLockEvent;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.protocol.ItemArmorSlot;
/*     */ import com.hypixel.hytale.protocol.Model;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.Vector2f;
/*     */ import com.hypixel.hytale.protocol.Vector3f;
/*     */ import com.hypixel.hytale.protocol.packets.asseteditor.AssetEditorPopupNotificationType;
/*     */ import com.hypixel.hytale.protocol.packets.asseteditor.AssetEditorPreviewCameraSettings;
/*     */ import com.hypixel.hytale.protocol.packets.asseteditor.AssetEditorUpdateModelPreview;
/*     */ import com.hypixel.hytale.protocol.packets.asseteditor.AssetEditorUpdateSecondsPerGameDay;
/*     */ import com.hypixel.hytale.protocol.packets.world.UpdateEditorTimeOverride;
/*     */ import com.hypixel.hytale.protocol.packets.world.UpdateEditorWeatherOverride;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.AssetIconProperties;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.Item;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.ItemArmor;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.Model;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.ModelAsset;
/*     */ import com.hypixel.hytale.server.core.asset.type.weather.config.Weather;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.io.PacketHandler;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
/*     */ import com.hypixel.hytale.server.core.modules.item.ItemModule;
/*     */ import com.hypixel.hytale.server.core.modules.time.WorldTimeResource;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.nio.file.Path;
/*     */ import java.time.Instant;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ @Deprecated
/*     */ public class AssetSpecificFunctionality {
/*  55 */   private static final Message NO_GAME_CLIENT_MESSAGE = Message.translation("server.assetEditor.messages.noGameClient");
/*     */   
/*  57 */   private static final ClearEditorTimeOverride CLEAR_EDITOR_TIME_OVERRIDE_PACKET = new ClearEditorTimeOverride();
/*  58 */   private static final UpdateEditorWeatherOverride CLEAR_WEATHER_OVERRIDE_PACKET = new UpdateEditorWeatherOverride(0);
/*     */   
/*  60 */   private static final String MODEL_ASSET_ID = ModelAsset.class.getSimpleName();
/*  61 */   private static final String ITEM_ASSET_ID = Item.class.getSimpleName();
/*  62 */   private static final String WEATHER_ASSET_ID = Weather.class.getSimpleName();
/*  63 */   private static final String ENVIRONMENT_ASSET_ID = Environment.class.getSimpleName();
/*     */   
/*  65 */   private static final Map<UUID, PlayerPreviewData> activeWeatherPreviewMapping = new ConcurrentHashMap<>();
/*     */   
/*     */   public static class PlayerPreviewData
/*     */   {
/*     */     @Nullable
/*     */     private Path weatherAssetPath;
/*     */     private boolean keepPreview;
/*     */   }
/*  73 */   private static final AssetEditorPreviewCameraSettings DEFAULT_PREVIEW_CAMERA_SETTINGS = new AssetEditorPreviewCameraSettings(0.25F, new Vector3f(0.0F, 75.0F, 0.0F), new Vector3f(0.0F, (float)Math.toRadians(45.0D), 0.0F));
/*     */   
/*     */   public static void setup() {
/*  76 */     getEventRegistry().register(LoadedAssetsEvent.class, ModelAsset.class, AssetSpecificFunctionality::onModelAssetLoaded);
/*  77 */     getEventRegistry().register(LoadedAssetsEvent.class, Item.class, AssetSpecificFunctionality::onItemAssetLoaded);
/*     */     
/*  79 */     getEventRegistry().register(AssetEditorActivateButtonEvent.class, "EquipItem", AssetSpecificFunctionality::onEquipItem);
/*  80 */     getEventRegistry().register(AssetEditorActivateButtonEvent.class, "UseModel", AssetSpecificFunctionality::onUseModel);
/*  81 */     getEventRegistry().register(AssetEditorActivateButtonEvent.class, "ResetModel", AssetSpecificFunctionality::onResetModel);
/*     */     
/*  83 */     getEventRegistry().register(AssetEditorUpdateWeatherPreviewLockEvent.class, AssetSpecificFunctionality::onUpdateWeatherPreviewLockEvent);
/*     */     
/*  85 */     getEventRegistry().register(AssetEditorAssetCreatedEvent.class, ITEM_ASSET_ID, AssetSpecificFunctionality::onItemAssetCreated);
/*  86 */     getEventRegistry().register(AssetEditorAssetCreatedEvent.class, MODEL_ASSET_ID, AssetSpecificFunctionality::onModelAssetCreated);
/*     */     
/*  88 */     getEventRegistry().register(AssetEditorFetchAutoCompleteDataEvent.class, "BlockGroups", AssetSpecificFunctionality::onRequestBlockGroupsDataSet);
/*  89 */     getEventRegistry().register(AssetEditorFetchAutoCompleteDataEvent.class, "LocalizationKeys", AssetSpecificFunctionality::onRequestLocalizationKeyDataSet);
/*  90 */     getEventRegistry().register(AssetEditorRequestDataSetEvent.class, "ItemCategories", AssetSpecificFunctionality::onRequestItemCategoriesDataSet);
/*     */     
/*  92 */     getEventRegistry().registerGlobal(AssetEditorSelectAssetEvent.class, AssetSpecificFunctionality::onSelectAsset);
/*     */     
/*  94 */     getEventRegistry().registerGlobal(AssetEditorClientDisconnectEvent.class, AssetSpecificFunctionality::onClientDisconnected);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private static PlayerRef tryGetPlayer(@Nonnull EditorClient editorClient) {
/*  99 */     PlayerRef playerRef = editorClient.tryGetPlayer();
/* 100 */     if (playerRef == null) {
/* 101 */       editorClient.sendPopupNotification(AssetEditorPopupNotificationType.Warning, NO_GAME_CLIENT_MESSAGE);
/* 102 */       return null;
/*     */     } 
/*     */     
/* 105 */     return playerRef;
/*     */   }
/*     */   
/*     */   private static void onModelAssetLoaded(@Nonnull LoadedAssetsEvent<String, ModelAsset, ?> event) {
/* 109 */     if (event.isInitial())
/*     */       return; 
/* 111 */     Map<EditorClient, AssetPath> clientOpenAssetPathMapping = AssetEditorPlugin.get().getClientOpenAssetPathMapping();
/* 112 */     if (clientOpenAssetPathMapping.isEmpty())
/*     */       return; 
/* 114 */     for (ModelAsset modelAsset : event.getLoadedAssets().values()) {
/* 115 */       for (Map.Entry<EditorClient, AssetPath> editor : clientOpenAssetPathMapping.entrySet()) {
/* 116 */         Path path = ((AssetPath)editor.getValue()).path();
/* 117 */         if (path.toString().isEmpty())
/*     */           continue; 
/* 119 */         AssetTypeHandler assetType = AssetEditorPlugin.get().getAssetTypeRegistry().getAssetTypeHandlerForPath(path);
/* 120 */         if (!(assetType instanceof AssetStoreTypeHandler) || !((AssetStoreTypeHandler)assetType).getAssetStore().getAssetClass().equals(ModelAsset.class)) {
/*     */           continue;
/*     */         }
/*     */         
/* 124 */         String id = (String)ModelAsset.getAssetStore().decodeFilePathKey(path);
/* 125 */         if (!modelAsset.getId().equals(id))
/*     */           continue; 
/* 127 */         Model modelPacket = Model.createUnitScaleModel(modelAsset).toPacket();
/* 128 */         AssetEditorUpdateModelPreview packet = new AssetEditorUpdateModelPreview(((AssetPath)editor.getValue()).toPacket(), modelPacket, null, DEFAULT_PREVIEW_CAMERA_SETTINGS);
/* 129 */         ((EditorClient)editor.getKey()).getPacketHandler().write((Packet)packet);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void onItemAssetLoaded(@Nonnull LoadedAssetsEvent<String, Item, ?> event) {
/* 135 */     if (event.isInitial())
/*     */       return; 
/* 137 */     Map<EditorClient, AssetPath> clientOpenAssetPathMapping = AssetEditorPlugin.get().getClientOpenAssetPathMapping();
/* 138 */     if (clientOpenAssetPathMapping.isEmpty())
/*     */       return; 
/* 140 */     AssetUpdateQuery.RebuildCache rebuildCache = event.getQuery().getRebuildCache();
/* 141 */     if (!rebuildCache.isBlockTextures() && !rebuildCache.isModelTextures() && !rebuildCache.isItemIcons() && !rebuildCache.isModels())
/*     */       return; 
/* 143 */     for (Item item : event.getLoadedAssets().values()) {
/* 144 */       for (Map.Entry<EditorClient, AssetPath> editor : clientOpenAssetPathMapping.entrySet()) {
/* 145 */         Path path = ((AssetPath)editor.getValue()).path();
/* 146 */         if (path.toString().isEmpty())
/*     */           continue; 
/* 148 */         AssetTypeHandler assetType = AssetEditorPlugin.get().getAssetTypeRegistry().getAssetTypeHandlerForPath(path);
/* 149 */         if (!(assetType instanceof AssetStoreTypeHandler) || !((AssetStoreTypeHandler)assetType).getAssetStore().getAssetClass().equals(Item.class)) {
/*     */           continue;
/*     */         }
/*     */         
/* 153 */         String id = (String)Item.getAssetStore().decodeFilePathKey(path);
/* 154 */         if (!item.getId().equals(id))
/*     */           continue; 
/* 156 */         AssetEditorUpdateModelPreview packet = getModelPreviewPacketForItem(editor.getValue(), item);
/* 157 */         if (packet != null) ((EditorClient)editor.getKey()).getPacketHandler().write((Packet)packet); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void onItemAssetCreated(@Nonnull AssetEditorAssetCreatedEvent event) {
/* 163 */     if (!"EquipItem".equals(event.getButtonId()))
/* 164 */       return;  equipItem(event.getAssetPath(), event.getEditorClient());
/*     */   }
/*     */   
/*     */   private static void onModelAssetCreated(@Nonnull AssetEditorAssetCreatedEvent event) {
/* 168 */     if (!"UseModel".equals(event.getButtonId()))
/* 169 */       return;  useModel(event.getAssetPath(), event.getEditorClient());
/*     */   }
/*     */   
/*     */   private static void onEquipItem(@Nonnull AssetEditorActivateButtonEvent event) {
/* 173 */     AssetPath currentAssetPath = AssetEditorPlugin.get().getOpenAssetPath(event.getEditorClient());
/* 174 */     if (currentAssetPath == null || currentAssetPath.path().toString().isEmpty())
/*     */       return; 
/* 176 */     equipItem(currentAssetPath.path(), event.getEditorClient());
/*     */   }
/*     */   
/*     */   private static void onUseModel(@Nonnull AssetEditorActivateButtonEvent event) {
/* 180 */     AssetPath currentAssetPath = AssetEditorPlugin.get().getOpenAssetPath(event.getEditorClient());
/* 181 */     if (currentAssetPath == null || currentAssetPath.path().toString().isEmpty())
/*     */       return; 
/* 183 */     useModel(currentAssetPath.path(), event.getEditorClient());
/*     */   }
/*     */ 
/*     */   
/*     */   private static void onUpdateWeatherPreviewLockEvent(@Nonnull AssetEditorUpdateWeatherPreviewLockEvent event) {
/* 188 */     PlayerPreviewData currentPreviewSettings = activeWeatherPreviewMapping.computeIfAbsent(event.getEditorClient().getUuid(), k -> new PlayerPreviewData());
/* 189 */     currentPreviewSettings.keepPreview = event.isLocked();
/*     */   }
/*     */   
/*     */   private static void onResetModel(@Nonnull AssetEditorActivateButtonEvent event) {
/* 193 */     EditorClient editorClient = event.getEditorClient();
/* 194 */     PlayerRef playerRef = tryGetPlayer(editorClient);
/* 195 */     if (playerRef == null)
/*     */       return; 
/* 197 */     Ref<EntityStore> ref = playerRef.getReference();
/* 198 */     if (ref == null || !ref.isValid())
/*     */       return; 
/* 200 */     Store<EntityStore> store = ref.getStore();
/* 201 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/* 202 */     world.execute(() -> {
/*     */           if (!store.getArchetype(ref).contains(PlayerSkinComponent.getComponentType())) {
/*     */             Message message = Message.translation("server.assetEditor.messages.model.noAuthSkinForPlayer").param("model", "Player");
/*     */             editorClient.sendPopupNotification(AssetEditorPopupNotificationType.Error, message);
/*     */             return;
/*     */           } 
/*     */           PlayerUtil.resetPlayerModel(ref, (ComponentAccessor)store);
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void equipItem(@Nonnull Path assetPath, @Nonnull EditorClient editorClient) {
/* 218 */     PlayerRef playerRef = tryGetPlayer(editorClient);
/* 219 */     if (playerRef == null)
/*     */       return; 
/* 221 */     Player player = (Player)playerRef.getComponent(Player.getComponentType());
/* 222 */     String key = (String)Item.getAssetStore().decodeFilePathKey(assetPath);
/* 223 */     Item item = (Item)Item.getAssetMap().getAsset(key);
/* 224 */     if (item == null) {
/* 225 */       editorClient.sendPopupNotification(AssetEditorPopupNotificationType.Error, Message.translation("server.assetEditor.messages.unknownItem").param("id", key.toString()));
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 230 */     ItemArmor itemArmor = item.getArmor();
/* 231 */     if (itemArmor != null) {
/* 232 */       player.getInventory().getArmor().setItemStackForSlot((short)itemArmor.getArmorSlot().ordinal(), new ItemStack(key));
/*     */       
/*     */       return;
/*     */     } 
/* 236 */     player.getInventory().getCombinedHotbarFirst().addItemStack(new ItemStack(key));
/*     */   }
/*     */   
/*     */   private static void useModel(@Nonnull Path assetPath, @Nonnull EditorClient editorClient) {
/* 240 */     PlayerRef playerRef = tryGetPlayer(editorClient);
/* 241 */     if (playerRef == null)
/*     */       return; 
/* 243 */     Ref<EntityStore> ref = playerRef.getReference();
/* 244 */     if (ref == null || !ref.isValid())
/*     */       return; 
/* 246 */     Store<EntityStore> store = ref.getStore();
/* 247 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*     */     
/* 249 */     world.execute(() -> {
/*     */           String key = (String)ModelAsset.getAssetStore().decodeFilePathKey(assetPath);
/*     */           ModelAsset modelAsset = (ModelAsset)ModelAsset.getAssetMap().getAsset(key);
/*     */           if (modelAsset == null) {
/*     */             Message unknownModelMessage = Message.translation("server.assetEditor.messages.unknownModel").param("id", key);
/*     */             editorClient.sendPopupNotification(AssetEditorPopupNotificationType.Error, unknownModelMessage);
/*     */             return;
/*     */           } 
/*     */           Model model = Model.createRandomScaleModel(modelAsset);
/*     */           store.putComponent(ref, ModelComponent.getComponentType(), (Component)new ModelComponent(model));
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void onRequestLocalizationKeyDataSet(@Nonnull AssetEditorFetchAutoCompleteDataEvent event) {
/* 265 */     ObjectArrayList<String> results = new ObjectArrayList();
/* 266 */     String query = event.getQuery().toLowerCase();
/*     */     
/* 268 */     Set<String> messageKeys = I18nModule.get().getMessages("en-US").keySet();
/* 269 */     for (String key : messageKeys) {
/* 270 */       if (key.toLowerCase().startsWith(query)) {
/* 271 */         results.add(key);
/*     */       }
/*     */ 
/*     */       
/* 275 */       if (results.size() >= 25) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */     
/* 280 */     event.setResults((String[])results.toArray(x$0 -> new String[x$0]));
/*     */   }
/*     */   
/*     */   private static void onRequestBlockGroupsDataSet(@Nonnull AssetEditorFetchAutoCompleteDataEvent event) {
/* 284 */     ObjectArrayList<String> results = new ObjectArrayList();
/* 285 */     String query = event.getQuery().toLowerCase();
/*     */     
/* 287 */     for (String group : BlockType.getAssetMap().getGroups()) {
/* 288 */       if (group != null && !group.trim().isEmpty() && (
/* 289 */         query.isEmpty() || group.toLowerCase().contains(query))) {
/* 290 */         results.add(group);
/*     */       }
/*     */     } 
/* 293 */     event.setResults((String[])results.toArray(x$0 -> new String[x$0]));
/*     */   }
/*     */   
/*     */   private static void onRequestItemCategoriesDataSet(@Nonnull AssetEditorRequestDataSetEvent event) {
/* 297 */     ItemModule itemModule = ItemModule.get();
/* 298 */     if (itemModule.isDisabled()) {
/* 299 */       HytaleLogger.getLogger().at(Level.WARNING).log("Received ItemCategories dataset request but ItemModule is disabled!");
/*     */       
/*     */       return;
/*     */     } 
/* 303 */     event.setResults((String[])itemModule.getFlatItemCategoryList().toArray(x$0 -> new String[x$0]));
/*     */   }
/*     */   
/*     */   private static void onClientDisconnected(@Nonnull AssetEditorClientDisconnectEvent event) {
/* 307 */     AssetEditorPlugin plugin = AssetEditorPlugin.get();
/*     */     
/* 309 */     EditorClient editorClient = event.getEditorClient();
/* 310 */     PlayerRef player = editorClient.tryGetPlayer();
/* 311 */     UUID uuid = editorClient.getUuid();
/* 312 */     Set<EditorClient> editorClients = plugin.getEditorClients(uuid);
/*     */ 
/*     */     
/* 315 */     if (editorClients == null || editorClients.size() == 1) {
/* 316 */       if (player != null) {
/*     */         
/* 318 */         player.getPacketHandler().write((Packet)CLEAR_EDITOR_TIME_OVERRIDE_PACKET);
/*     */ 
/*     */         
/* 321 */         player.getPacketHandler().write((Packet)CLEAR_WEATHER_OVERRIDE_PACKET);
/*     */       } 
/*     */       
/* 324 */       activeWeatherPreviewMapping.remove(uuid);
/*     */       
/*     */       return;
/*     */     } 
/* 328 */     AssetPath openAssetPath = plugin.getOpenAssetPath(editorClient);
/* 329 */     if (openAssetPath == null || openAssetPath.equals(AssetPath.EMPTY_PATH))
/*     */       return; 
/* 331 */     AssetTypeHandler assetType = plugin.getAssetTypeRegistry().getAssetTypeHandlerForPath(openAssetPath.path());
/* 332 */     if (assetType == null || !Weather.class.getSimpleName().equals((assetType.getConfig()).id))
/*     */       return; 
/* 334 */     activeWeatherPreviewMapping.remove(uuid);
/*     */     
/* 336 */     if (player != null) {
/* 337 */       player.getPacketHandler().write((Packet)new UpdateEditorWeatherOverride(0));
/*     */     }
/*     */   }
/*     */   
/*     */   static void resetTimeSettings(@Nonnull EditorClient editorClient, @Nonnull PlayerRef playerRef) {
/* 342 */     Ref<EntityStore> ref = playerRef.getReference();
/* 343 */     if (ref == null || !ref.isValid())
/*     */       return; 
/* 345 */     Store<EntityStore> store = ref.getStore();
/* 346 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*     */     
/* 348 */     Player playerComponent = (Player)playerRef.getComponent(Player.getComponentType());
/* 349 */     assert playerComponent != null;
/*     */     
/* 351 */     WorldTimeResource worldTimeResource = (WorldTimeResource)store.getResource(WorldTimeResource.getResourceType());
/*     */     
/* 353 */     PacketHandler packetHandler = editorClient.getPacketHandler();
/*     */ 
/*     */     
/* 356 */     AssetEditorUpdateSecondsPerGameDay settingsPacket = new AssetEditorUpdateSecondsPerGameDay(world.getDaytimeDurationSeconds(), world.getNighttimeDurationSeconds());
/*     */     
/* 358 */     packetHandler.write((Packet)settingsPacket);
/*     */     
/* 360 */     Instant gameTime = worldTimeResource.getGameTime();
/* 361 */     UpdateEditorTimeOverride packet = new UpdateEditorTimeOverride(new InstantData(gameTime.getEpochSecond(), gameTime.getNano()), world.getWorldConfig().isGameTimePaused());
/* 362 */     packetHandler.write((Packet)packet);
/*     */     
/* 364 */     playerRef.getPacketHandler().write((Packet)CLEAR_EDITOR_TIME_OVERRIDE_PACKET);
/*     */   }
/*     */   
/*     */   static void handleWeatherOrEnvironmentUnselected(@Nonnull EditorClient editorClient, @Nonnull Path assetPath, boolean wasWeather) {
/* 368 */     PlayerRef player = editorClient.tryGetPlayer();
/* 369 */     if (player == null)
/*     */       return; 
/* 371 */     PlayerPreviewData currentPreviewSettings = activeWeatherPreviewMapping.computeIfAbsent(editorClient.getUuid(), k -> new PlayerPreviewData());
/* 372 */     if (currentPreviewSettings.keepPreview)
/*     */       return; 
/* 374 */     resetTimeSettings(editorClient, player);
/*     */     
/* 376 */     if (wasWeather) {
/* 377 */       if (!assetPath.equals(currentPreviewSettings.weatherAssetPath))
/*     */         return; 
/* 379 */       currentPreviewSettings.weatherAssetPath = null;
/* 380 */       player.getPacketHandler().write((Packet)CLEAR_WEATHER_OVERRIDE_PACKET);
/*     */     } 
/*     */   }
/*     */   
/*     */   static void handleWeatherOrEnvironmentSelected(@Nonnull EditorClient editorClient, @Nonnull Path assetPath, boolean isWeather) {
/* 385 */     PlayerRef player = editorClient.tryGetPlayer();
/* 386 */     if (player == null)
/*     */       return; 
/* 388 */     PlayerPreviewData currentPreviewSettings = activeWeatherPreviewMapping.computeIfAbsent(editorClient.getUuid(), k -> new PlayerPreviewData());
/* 389 */     if (!currentPreviewSettings.keepPreview) {
/* 390 */       resetTimeSettings(editorClient, player);
/*     */     }
/*     */     
/* 393 */     if (isWeather) {
/* 394 */       AssetStore<String, Weather, IndexedLookupTableAssetMap<String, Weather>> assetStore = Weather.getAssetStore();
/* 395 */       String key = (String)assetStore.decodeFilePathKey(assetPath);
/* 396 */       int weatherIndex = ((IndexedLookupTableAssetMap)assetStore.getAssetMap()).getIndex(key);
/*     */       
/* 398 */       currentPreviewSettings.weatherAssetPath = assetPath;
/* 399 */       player.getPacketHandler().write((Packet)new UpdateEditorWeatherOverride(weatherIndex));
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void onSelectAsset(@Nonnull AssetEditorSelectAssetEvent event) {
/* 404 */     String assetType = event.getAssetType();
/*     */     
/* 406 */     if (MODEL_ASSET_ID.equals(assetType)) {
/* 407 */       String key = (String)ModelAsset.getAssetStore().decodeFilePathKey(event.getAssetFilePath().path());
/* 408 */       ModelAsset modelAsset = (ModelAsset)ModelAsset.getAssetMap().getAsset(key);
/* 409 */       if (modelAsset != null) {
/* 410 */         Model modelPacket = Model.createUnitScaleModel(modelAsset).toPacket();
/* 411 */         event.getEditorClient().getPacketHandler().write((Packet)new AssetEditorUpdateModelPreview(event.getAssetFilePath().toPacket(), modelPacket, null, DEFAULT_PREVIEW_CAMERA_SETTINGS));
/*     */       } 
/*     */     } 
/*     */     
/* 415 */     if (ITEM_ASSET_ID.equals(assetType)) {
/* 416 */       AssetPath assetPath = event.getAssetFilePath();
/* 417 */       String key = (String)Item.getAssetStore().decodeFilePathKey(assetPath.path());
/* 418 */       Item item = (Item)Item.getAssetMap().getAsset(key);
/* 419 */       if (item != null) {
/* 420 */         AssetEditorUpdateModelPreview packet = getModelPreviewPacketForItem(assetPath, item);
/* 421 */         if (packet != null) event.getEditorClient().getPacketHandler().write((Packet)packet);
/*     */       
/*     */       } 
/*     */     } 
/* 425 */     String previousAssetType = event.getPreviousAssetType();
/* 426 */     boolean wasWeather = WEATHER_ASSET_ID.equals(previousAssetType);
/* 427 */     if (wasWeather || ENVIRONMENT_ASSET_ID.equals(previousAssetType)) {
/* 428 */       handleWeatherOrEnvironmentUnselected(event.getEditorClient(), event.getPreviousAssetFilePath().path(), wasWeather);
/*     */     }
/*     */     
/* 431 */     boolean isWeather = WEATHER_ASSET_ID.equals(assetType);
/* 432 */     if (isWeather || ENVIRONMENT_ASSET_ID.equals(assetType)) {
/* 433 */       handleWeatherOrEnvironmentSelected(event.getEditorClient(), event.getAssetFilePath().path(), isWeather);
/*     */     }
/*     */   }
/*     */   
/*     */   public static AssetEditorUpdateModelPreview getModelPreviewPacketForItem(@Nonnull AssetPath assetPath, @Nullable Item item) {
/* 438 */     if (item == null) return null;
/*     */     
/* 440 */     AssetIconProperties iconProperties = item.getIconProperties();
/* 441 */     AssetIconProperties defaultIconProperties = getDefaultItemIconProperties(item);
/* 442 */     if (iconProperties == null) iconProperties = defaultIconProperties;
/*     */     
/* 444 */     AssetEditorPreviewCameraSettings camera = new AssetEditorPreviewCameraSettings();
/* 445 */     camera.modelScale = iconProperties.getScale() * item.getScale();
/*     */     
/* 447 */     Vector2f translation = (iconProperties.getTranslation() != null) ? iconProperties.getTranslation() : defaultIconProperties.getTranslation();
/* 448 */     camera.cameraPosition = new Vector3f(-translation.x, -translation.y, 0.0F);
/*     */     
/* 450 */     Vector3f rotation = (iconProperties.getRotation() != null) ? iconProperties.getRotation() : defaultIconProperties.getRotation();
/* 451 */     camera.cameraOrientation = new Vector3f((float)-Math.toRadians(rotation.x), (float)-Math.toRadians(rotation.y), (float)-Math.toRadians(rotation.z));
/*     */     
/* 453 */     if (item.getBlockId() != null) {
/* 454 */       BlockType blockType = (BlockType)((BlockTypeAssetMap)BlockType.getAssetStore().getAssetMap()).getAsset(item.getBlockId());
/* 455 */       if (blockType != null) {
/* 456 */         camera.modelScale *= blockType.getCustomModelScale();
/* 457 */         return new AssetEditorUpdateModelPreview(assetPath.toPacket(), null, blockType.toPacket(), camera);
/*     */       } 
/*     */     } 
/*     */     
/* 461 */     Model modelPacket = convertToModelPacket(item);
/* 462 */     return new AssetEditorUpdateModelPreview(assetPath.toPacket(), modelPacket, null, camera);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static AssetIconProperties getDefaultItemIconProperties(@Nonnull Item item) {
/* 468 */     if (item.getWeapon() != null)
/* 469 */       return new AssetIconProperties(0.37F, new Vector2f(-24.6F, -24.6F), new Vector3f(45.0F, 90.0F, 0.0F)); 
/* 470 */     if (item.getTool() != null)
/* 471 */       return new AssetIconProperties(0.5F, new Vector2f(-17.4F, -12.0F), new Vector3f(45.0F, 270.0F, 0.0F)); 
/* 472 */     if (item.getArmor() != null) {
/* 473 */       switch (item.getArmor().getArmorSlot()) { default: throw new MatchException(null, null);case Chest: case Head: case Legs: case Hands: break; }  return 
/*     */ 
/*     */ 
/*     */         
/* 477 */         new AssetIconProperties(0.92F, new Vector2f(0.0F, -10.8F), new Vector3f(22.5F, 45.0F, 22.5F));
/*     */     } 
/*     */ 
/*     */     
/* 481 */     return new AssetIconProperties(0.58823F, new Vector2f(0.0F, -13.5F), new Vector3f(22.5F, 45.0F, 22.5F));
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Model convertToModelPacket(@Nonnull Item item) {
/* 486 */     Model packet = new Model();
/* 487 */     packet.path = item.getModel();
/* 488 */     packet.texture = item.getTexture();
/* 489 */     return packet;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   private static EventRegistry getEventRegistry() {
/* 494 */     return AssetEditorPlugin.get().getEventRegistry();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\asseteditor\AssetSpecificFunctionality.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */