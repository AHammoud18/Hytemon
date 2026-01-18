/*    */ package com.hypixel.hytale.builtin.asseteditor.event;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.asseteditor.AssetPath;
/*    */ import com.hypixel.hytale.builtin.asseteditor.EditorClient;
/*    */ 
/*    */ public class AssetEditorSelectAssetEvent
/*    */   extends EditorClientEvent<Void> {
/*    */   private final String assetType;
/*    */   private final AssetPath assetFilePath;
/*    */   private final String previousAssetType;
/*    */   private final AssetPath previousAssetFilePath;
/*    */   
/*    */   public AssetEditorSelectAssetEvent(EditorClient editorClient, String assetType, AssetPath assetFilePath, String previousAssetType, AssetPath previousAssetFilePath) {
/* 14 */     super(editorClient);
/* 15 */     this.assetType = assetType;
/* 16 */     this.assetFilePath = assetFilePath;
/* 17 */     this.previousAssetType = previousAssetType;
/* 18 */     this.previousAssetFilePath = previousAssetFilePath;
/*    */   }
/*    */   
/*    */   public String getAssetType() {
/* 22 */     return this.assetType;
/*    */   }
/*    */   
/*    */   public AssetPath getAssetFilePath() {
/* 26 */     return this.assetFilePath;
/*    */   }
/*    */   
/*    */   public String getPreviousAssetType() {
/* 30 */     return this.previousAssetType;
/*    */   }
/*    */   
/*    */   public AssetPath getPreviousAssetFilePath() {
/* 34 */     return this.previousAssetFilePath;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\asseteditor\event\AssetEditorSelectAssetEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */