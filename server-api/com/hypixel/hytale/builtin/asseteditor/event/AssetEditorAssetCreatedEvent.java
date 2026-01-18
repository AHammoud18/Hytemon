/*    */ package com.hypixel.hytale.builtin.asseteditor.event;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.asseteditor.EditorClient;
/*    */ import java.nio.file.Path;
/*    */ 
/*    */ public class AssetEditorAssetCreatedEvent
/*    */   extends EditorClientEvent<String> {
/*    */   private final String assetType;
/*    */   private final Path assetPath;
/*    */   private final byte[] data;
/*    */   private final String buttonId;
/*    */   
/*    */   public AssetEditorAssetCreatedEvent(EditorClient editorClient, String assetType, Path assetPath, byte[] data, String buttonId) {
/* 14 */     super(editorClient);
/* 15 */     this.assetType = assetType;
/* 16 */     this.assetPath = assetPath;
/* 17 */     this.data = data;
/* 18 */     this.buttonId = buttonId;
/*    */   }
/*    */   
/*    */   public String getAssetType() {
/* 22 */     return this.assetType;
/*    */   }
/*    */   
/*    */   public Path getAssetPath() {
/* 26 */     return this.assetPath;
/*    */   }
/*    */   
/*    */   public byte[] getData() {
/* 30 */     return this.data;
/*    */   }
/*    */   
/*    */   public String getButtonId() {
/* 34 */     return this.buttonId;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\asseteditor\event\AssetEditorAssetCreatedEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */