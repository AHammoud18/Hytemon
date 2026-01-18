/*    */ package com.hypixel.hytale.builtin.asseteditor.assettypehandler;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.AssetUpdateQuery;
/*    */ import com.hypixel.hytale.builtin.asseteditor.AssetPath;
/*    */ import com.hypixel.hytale.builtin.asseteditor.EditorClient;
/*    */ import com.hypixel.hytale.codec.util.RawJsonReader;
/*    */ import com.hypixel.hytale.logger.HytaleLogger;
/*    */ import com.hypixel.hytale.protocol.packets.asseteditor.AssetEditorAssetType;
/*    */ import java.nio.charset.StandardCharsets;
/*    */ import java.nio.file.Path;
/*    */ import java.util.logging.Level;
/*    */ import javax.annotation.Nonnull;
/*    */ import org.bson.BsonDocument;
/*    */ 
/*    */ 
/*    */ public abstract class JsonTypeHandler
/*    */   extends AssetTypeHandler
/*    */ {
/* 19 */   private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*    */   
/*    */   protected JsonTypeHandler(@Nonnull AssetEditorAssetType config) {
/* 22 */     super(config);
/*    */   }
/*    */ 
/*    */   
/*    */   public AssetTypeHandler.AssetLoadResult loadAsset(AssetPath path, Path dataPath, byte[] data, AssetUpdateQuery updateQuery, EditorClient editorClient) {
/*    */     BsonDocument doc;
/*    */     try {
/* 29 */       doc = BsonDocument.parse(new String(data, StandardCharsets.UTF_8));
/* 30 */     } catch (Exception exception) {
/* 31 */       ((HytaleLogger.Api)LOGGER.at(Level.WARNING).withCause(exception)).log("Failed to parse JSON for " + String.valueOf(path));
/* 32 */       return AssetTypeHandler.AssetLoadResult.ASSETS_UNCHANGED;
/*    */     } 
/*    */     
/* 35 */     return loadAssetFromDocument(path, dataPath, doc, updateQuery, editorClient);
/*    */   }
/*    */   
/*    */   public abstract AssetTypeHandler.AssetLoadResult loadAssetFromDocument(AssetPath paramAssetPath, Path paramPath, BsonDocument paramBsonDocument, AssetUpdateQuery paramAssetUpdateQuery, EditorClient paramEditorClient);
/*    */   
/*    */   public AssetTypeHandler.AssetLoadResult loadAssetFromDocument(AssetPath path, Path dataPath, BsonDocument document, EditorClient editorClient) {
/* 41 */     return loadAssetFromDocument(path, dataPath, document, getDefaultUpdateQuery(), editorClient);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isValidData(@Nonnull byte[] data) {
/*    */     try {
/* 47 */       String str = new String(data, StandardCharsets.UTF_8);
/* 48 */       char[] buffer = str.toCharArray();
/* 49 */       RawJsonReader.validateBsonDocument(new RawJsonReader(buffer));
/* 50 */     } catch (Exception exception) {
/* 51 */       return false;
/*    */     } 
/*    */     
/* 54 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\asseteditor\assettypehandler\JsonTypeHandler.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */