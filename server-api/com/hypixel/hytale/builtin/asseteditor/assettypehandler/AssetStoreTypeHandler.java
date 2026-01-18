/*     */ package com.hypixel.hytale.builtin.asseteditor.assettypehandler;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetStore;
/*     */ import com.hypixel.hytale.assetstore.AssetUpdateQuery;
/*     */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*     */ import com.hypixel.hytale.builtin.asseteditor.AssetEditorPlugin;
/*     */ import com.hypixel.hytale.builtin.asseteditor.AssetPath;
/*     */ import com.hypixel.hytale.builtin.asseteditor.EditorClient;
/*     */ import com.hypixel.hytale.builtin.asseteditor.util.AssetPathUtil;
/*     */ import com.hypixel.hytale.codec.schema.config.Schema;
/*     */ import com.hypixel.hytale.codec.schema.metadata.ui.UIRebuildCaches;
/*     */ import com.hypixel.hytale.common.util.PathUtil;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.logger.sentry.SkipSentryException;
/*     */ import com.hypixel.hytale.protocol.packets.asseteditor.AssetEditorAssetType;
/*     */ import com.hypixel.hytale.protocol.packets.asseteditor.AssetEditorEditorType;
/*     */ import com.hypixel.hytale.protocol.packets.asseteditor.AssetEditorPopupNotificationType;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import java.nio.file.Path;
/*     */ import java.util.Collections;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import org.bson.BsonDocument;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AssetStoreTypeHandler
/*     */   extends JsonTypeHandler
/*     */ {
/*  31 */   private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */   
/*     */   @Nonnull
/*     */   private final AssetStore assetStore;
/*     */   
/*     */   public AssetStoreTypeHandler(@Nonnull AssetStore assetStore) {
/*  37 */     super(new AssetEditorAssetType(assetStore.getAssetClass().getSimpleName(), null, false, PathUtil.toUnixPathString(AssetPathUtil.PATH_DIR_SERVER.resolve(assetStore.getPath())), assetStore.getExtension(), AssetEditorEditorType.JsonConfig));
/*     */     
/*  39 */     this.assetStore = assetStore;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public AssetStore getAssetStore() {
/*  44 */     return this.assetStore;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public AssetTypeHandler.AssetLoadResult loadAssetFromDocument(AssetPath path, Path dataPath, BsonDocument document, AssetUpdateQuery updateQuery, EditorClient editorClient) {
/*     */     try {
/*  51 */       Object key = this.assetStore.decodeFilePathKey(path.path());
/*  52 */       JsonAssetWithMap decodedAsset = this.assetStore.decode(path.packId(), key, document.clone());
/*  53 */       this.assetStore.loadAssets(path.packId(), Collections.singletonList(decodedAsset), updateQuery, true);
/*  54 */     } catch (Exception e) {
/*  55 */       ((HytaleLogger.Api)LOGGER.at(Level.WARNING).withCause((Throwable)new SkipSentryException(e))).log("Failed to load asset", path);
/*     */       
/*  57 */       if (editorClient != null) {
/*  58 */         editorClient.sendPopupNotification(AssetEditorPopupNotificationType.Error, Message.translation("server.assetEditor.messages.failedToDecodeAsset").param("message", e.getMessage()));
/*     */       }
/*     */       
/*  61 */       return AssetTypeHandler.AssetLoadResult.ASSETS_UNCHANGED;
/*     */     } 
/*     */     
/*  64 */     return AssetTypeHandler.AssetLoadResult.ASSETS_CHANGED;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public AssetTypeHandler.AssetLoadResult unloadAsset(@Nonnull AssetPath path, @Nonnull AssetUpdateQuery updateQuery) {
/*  70 */     this.assetStore.removeAssets(path.packId(), true, Collections.singleton(this.assetStore.decodeFilePathKey(path.path())), updateQuery);
/*  71 */     return AssetTypeHandler.AssetLoadResult.ASSETS_CHANGED;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public AssetTypeHandler.AssetLoadResult restoreOriginalAsset(@Nonnull AssetPath originalAssetPath, @Nonnull AssetUpdateQuery updateQuery) {
/*     */     try {
/*  78 */       this.assetStore.loadAssetsFromPaths(originalAssetPath.packId(), Collections.singletonList(originalAssetPath.path()), updateQuery, true);
/*  79 */     } catch (Exception e) {
/*  80 */       ((HytaleLogger.Api)LOGGER.at(Level.WARNING).withCause((Throwable)new SkipSentryException(e))).log("Failed to restore asset", originalAssetPath);
/*  81 */       return AssetTypeHandler.AssetLoadResult.ASSETS_UNCHANGED;
/*     */     } 
/*     */     
/*  84 */     return AssetTypeHandler.AssetLoadResult.ASSETS_CHANGED;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public AssetUpdateQuery getDefaultUpdateQuery() {
/*  90 */     if (this.cachedDefaultUpdateQuery == null) {
/*  91 */       Schema schema = AssetEditorPlugin.get().getSchema(this.config.id + ".json");
/*  92 */       if (schema == null) return AssetUpdateQuery.DEFAULT;
/*     */       
/*  94 */       AssetUpdateQuery.RebuildCacheBuilder rebuildCacheBuilder = AssetUpdateQuery.RebuildCache.builder();
/*     */       
/*  96 */       if (schema.getHytale().getUiRebuildCaches() != null) {
/*  97 */         for (UIRebuildCaches.ClientCache cache : schema.getHytale().getUiRebuildCaches()) {
/*  98 */           switch (cache) { case MODELS:
/*  99 */               rebuildCacheBuilder.setModels(true); break;
/* 100 */             case MODEL_TEXTURES: rebuildCacheBuilder.setModelTextures(true); break;
/* 101 */             case ITEM_ICONS: rebuildCacheBuilder.setItemIcons(true); break;
/* 102 */             case BLOCK_TEXTURES: rebuildCacheBuilder.setBlockTextures(true); break;
/* 103 */             case MAP_GEOMETRY: rebuildCacheBuilder.setMapGeometry(true);
/*     */               break; }
/*     */         
/*     */         } 
/*     */       }
/* 108 */       this.cachedDefaultUpdateQuery = new AssetUpdateQuery(rebuildCacheBuilder.build());
/*     */     } 
/*     */     
/* 111 */     return this.cachedDefaultUpdateQuery;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\asseteditor\assettypehandler\AssetStoreTypeHandler.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */