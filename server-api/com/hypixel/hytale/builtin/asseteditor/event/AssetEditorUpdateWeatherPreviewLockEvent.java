/*    */ package com.hypixel.hytale.builtin.asseteditor.event;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.asseteditor.EditorClient;
/*    */ 
/*    */ public class AssetEditorUpdateWeatherPreviewLockEvent extends EditorClientEvent<Void> {
/*    */   private final boolean locked;
/*    */   
/*    */   public AssetEditorUpdateWeatherPreviewLockEvent(EditorClient editorClient, boolean locked) {
/*  9 */     super(editorClient);
/*    */     
/* 11 */     this.locked = locked;
/*    */   }
/*    */   
/*    */   public boolean isLocked() {
/* 15 */     return this.locked;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\asseteditor\event\AssetEditorUpdateWeatherPreviewLockEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */