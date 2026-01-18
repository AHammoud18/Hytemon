/*    */ package com.hypixel.hytale.builtin.asseteditor;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.asseteditor.data.AssetUndoRedoInfo;
/*    */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class UndoRedoManager
/*    */ {
/*  9 */   private final Map<AssetPath, AssetUndoRedoInfo> assetUndoRedoInfo = (Map<AssetPath, AssetUndoRedoInfo>)new Object2ObjectOpenHashMap();
/*    */   
/*    */   public AssetUndoRedoInfo getOrCreateUndoRedoStack(AssetPath path) {
/* 12 */     return this.assetUndoRedoInfo.computeIfAbsent(path, k -> new AssetUndoRedoInfo());
/*    */   }
/*    */   
/*    */   public AssetUndoRedoInfo getUndoRedoStack(AssetPath path) {
/* 16 */     return this.assetUndoRedoInfo.get(path);
/*    */   }
/*    */   
/*    */   public void putUndoRedoStack(AssetPath path, AssetUndoRedoInfo undoRedoInfo) {
/* 20 */     this.assetUndoRedoInfo.put(path, undoRedoInfo);
/*    */   }
/*    */   
/*    */   public AssetUndoRedoInfo clearUndoRedoStack(AssetPath path) {
/* 24 */     return this.assetUndoRedoInfo.remove(path);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\asseteditor\UndoRedoManager.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */