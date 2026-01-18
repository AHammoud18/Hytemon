/*    */ package com.hypixel.hytale.builtin.npceditor;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.AssetUpdateQuery;
/*    */ import com.hypixel.hytale.builtin.asseteditor.AssetPath;
/*    */ import com.hypixel.hytale.builtin.asseteditor.EditorClient;
/*    */ import com.hypixel.hytale.builtin.asseteditor.assettypehandler.AssetTypeHandler;
/*    */ import com.hypixel.hytale.builtin.asseteditor.assettypehandler.JsonTypeHandler;
/*    */ import com.hypixel.hytale.protocol.packets.asseteditor.AssetEditorAssetType;
/*    */ import com.hypixel.hytale.protocol.packets.asseteditor.AssetEditorEditorType;
/*    */ import com.hypixel.hytale.server.core.asset.AssetModule;
/*    */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*    */ import java.nio.file.Path;
/*    */ import javax.annotation.Nonnull;
/*    */ import org.bson.BsonDocument;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NPCRoleAssetTypeHandler
/*    */   extends JsonTypeHandler
/*    */ {
/*    */   public static final String TYPE_ID = "NPCRole";
/*    */   
/*    */   public NPCRoleAssetTypeHandler() {
/* 28 */     super(new AssetEditorAssetType("NPCRole", null, false, NPCPlugin.ROLE_ASSETS_PATH, ".json", AssetEditorEditorType.JsonSource));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public AssetTypeHandler.AssetLoadResult loadAssetFromDocument(AssetPath assetPath, Path dataPath, BsonDocument document, AssetUpdateQuery updateQuery, EditorClient editorClient) {
/* 35 */     NPCPlugin.get().getBuilderManager().assetEditorLoadFile(dataPath);
/* 36 */     return AssetTypeHandler.AssetLoadResult.ASSETS_CHANGED;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public AssetTypeHandler.AssetLoadResult unloadAsset(@Nonnull AssetPath path, AssetUpdateQuery updateQuery) {
/* 42 */     Path rootPath = AssetModule.get().getAssetPack(path.packId()).getRoot();
/*    */     
/* 44 */     NPCPlugin.get().getBuilderManager().assetEditorRemoveFile(rootPath.resolve(path.path()).toAbsolutePath());
/* 45 */     return AssetTypeHandler.AssetLoadResult.ASSETS_CHANGED;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public AssetTypeHandler.AssetLoadResult restoreOriginalAsset(@Nonnull AssetPath originalAssetPath, AssetUpdateQuery updateQuery) {
/* 51 */     Path rootPath = AssetModule.get().getAssetPack(originalAssetPath.packId()).getRoot();
/* 52 */     NPCPlugin.get().getBuilderManager().assetEditorLoadFile(rootPath.resolve(originalAssetPath.path()).toAbsolutePath());
/* 53 */     return AssetTypeHandler.AssetLoadResult.ASSETS_CHANGED;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public AssetUpdateQuery getDefaultUpdateQuery() {
/* 59 */     return AssetUpdateQuery.DEFAULT_NO_REBUILD;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\npceditor\NPCRoleAssetTypeHandler.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */