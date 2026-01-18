/*    */ package com.hypixel.hytale.builtin.asseteditor.assettypehandler;
/*    */ import com.hypixel.hytale.assetstore.AssetPack;
/*    */ import com.hypixel.hytale.assetstore.AssetUpdateQuery;
/*    */ import com.hypixel.hytale.builtin.asseteditor.AssetPath;
/*    */ import com.hypixel.hytale.builtin.asseteditor.EditorClient;
/*    */ import com.hypixel.hytale.builtin.asseteditor.util.AssetPathUtil;
/*    */ import com.hypixel.hytale.common.util.PathUtil;
/*    */ import com.hypixel.hytale.logger.HytaleLogger;
/*    */ import com.hypixel.hytale.protocol.packets.asseteditor.AssetEditorAssetType;
/*    */ import com.hypixel.hytale.protocol.packets.asseteditor.AssetEditorEditorType;
/*    */ import com.hypixel.hytale.server.core.asset.AssetModule;
/*    */ import com.hypixel.hytale.server.core.asset.common.CommonAsset;
/*    */ import com.hypixel.hytale.server.core.asset.common.CommonAssetModule;
/*    */ import com.hypixel.hytale.server.core.asset.common.CommonAssetRegistry;
/*    */ import com.hypixel.hytale.server.core.asset.common.asset.FileCommonAsset;
/*    */ import com.hypixel.hytale.server.core.universe.Universe;
/*    */ import it.unimi.dsi.fastutil.booleans.BooleanObjectPair;
/*    */ import java.io.IOException;
/*    */ import java.nio.file.Files;
/*    */ import java.nio.file.Path;
/*    */ import java.util.Collections;
/*    */ import java.util.logging.Level;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class CommonAssetTypeHandler extends AssetTypeHandler {
/* 26 */   private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*    */   
/*    */   public CommonAssetTypeHandler(String id, String icon, String fileExtension, AssetEditorEditorType editorType) {
/* 29 */     super(new AssetEditorAssetType(id, icon, true, "Common", fileExtension, editorType));
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public AssetTypeHandler.AssetLoadResult loadAsset(AssetPath path, Path dataPath, byte[] data, AssetUpdateQuery updateQuery, EditorClient editorClient) {
/* 35 */     String relativePath = PathUtil.toUnixPathString(AssetPathUtil.PATH_DIR_COMMON.relativize(path.path()));
/* 36 */     FileCommonAsset newAsset = new FileCommonAsset(dataPath, relativePath, data);
/*    */     
/* 38 */     CommonAssetRegistry.AddCommonAssetResult result = CommonAssetRegistry.addCommonAsset(path.packId(), (CommonAsset)newAsset);
/* 39 */     CommonAssetRegistry.PackAsset asset = result.getNewPackAsset();
/* 40 */     CommonAssetRegistry.PackAsset oldAsset = result.getPreviousNameAsset();
/* 41 */     if (oldAsset == null || !oldAsset.asset().getHash().equals(asset.asset().getHash())) {
/* 42 */       return AssetTypeHandler.AssetLoadResult.COMMON_ASSETS_CHANGED;
/*    */     }
/*    */     
/* 45 */     return AssetTypeHandler.AssetLoadResult.ASSETS_UNCHANGED;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public AssetTypeHandler.AssetLoadResult unloadAsset(@Nonnull AssetPath path, @Nonnull AssetUpdateQuery updateQuery) {
/* 51 */     BooleanObjectPair<CommonAssetRegistry.PackAsset> removedCommonAsset = CommonAssetRegistry.removeCommonAssetByName(path.packId(), PathUtil.toUnixPathString(AssetPathUtil.PATH_DIR_COMMON.relativize(path.path())));
/* 52 */     if (removedCommonAsset != null) {
/* 53 */       if (Universe.get().getPlayerCount() > 0) {
/* 54 */         if (removedCommonAsset.firstBoolean()) {
/* 55 */           CommonAssetModule.get().sendAsset(((CommonAssetRegistry.PackAsset)removedCommonAsset.second()).asset(), updateQuery.getRebuildCache().isCommonAssetsRebuild());
/*    */         } else {
/* 57 */           CommonAssetModule.get().sendRemoveAssets(Collections.singletonList((CommonAssetRegistry.PackAsset)removedCommonAsset.second()), updateQuery.getRebuildCache().isCommonAssetsRebuild());
/*    */         } 
/*    */       }
/* 60 */       return AssetTypeHandler.AssetLoadResult.COMMON_ASSETS_CHANGED;
/*    */     } 
/*    */     
/* 63 */     return AssetTypeHandler.AssetLoadResult.ASSETS_UNCHANGED;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public AssetTypeHandler.AssetLoadResult restoreOriginalAsset(@Nonnull AssetPath originalAssetPath, AssetUpdateQuery updateQuery) {
/* 69 */     AssetPack pack = AssetModule.get().getAssetPack(originalAssetPath.packId());
/* 70 */     Path absolutePath = pack.getRoot().resolve(originalAssetPath.path()).toAbsolutePath();
/* 71 */     byte[] bytes = null;
/*    */     try {
/* 73 */       bytes = Files.readAllBytes(absolutePath);
/* 74 */     } catch (IOException e) {
/* 75 */       ((HytaleLogger.Api)LOGGER.at(Level.WARNING).withCause(e)).log("Failed to load file %s", absolutePath);
/*    */     } 
/*    */     
/* 78 */     if (bytes == null) return AssetTypeHandler.AssetLoadResult.ASSETS_UNCHANGED;
/*    */     
/* 80 */     String relativePath = PathUtil.toUnixPathString(AssetPathUtil.PATH_DIR_COMMON.relativize(originalAssetPath.path()));
/* 81 */     FileCommonAsset commonAsset = new FileCommonAsset(absolutePath, relativePath, bytes);
/*    */     
/* 83 */     CommonAssetRegistry.AddCommonAssetResult result = CommonAssetRegistry.addCommonAsset(originalAssetPath.packId(), (CommonAsset)commonAsset);
/* 84 */     CommonAssetRegistry.PackAsset oldAsset = result.getPreviousNameAsset();
/* 85 */     CommonAssetRegistry.PackAsset newAsset = result.getNewPackAsset();
/* 86 */     if (oldAsset == null || !oldAsset.asset().getHash().equals(newAsset.asset().getHash())) {
/* 87 */       return AssetTypeHandler.AssetLoadResult.COMMON_ASSETS_CHANGED;
/*    */     }
/*    */     
/* 90 */     return AssetTypeHandler.AssetLoadResult.ASSETS_UNCHANGED;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public AssetUpdateQuery getDefaultUpdateQuery() {
/* 96 */     if (this.cachedDefaultUpdateQuery == null) this.cachedDefaultUpdateQuery = new AssetUpdateQuery(new AssetUpdateQuery.RebuildCache(false, false, false, false, false, true)); 
/* 97 */     return this.cachedDefaultUpdateQuery;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\asseteditor\assettypehandler\CommonAssetTypeHandler.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */