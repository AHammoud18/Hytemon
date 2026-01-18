/*    */ package com.hypixel.hytale.builtin.asseteditor.event;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.asseteditor.EditorClient;
/*    */ import com.hypixel.hytale.event.IAsyncEvent;
/*    */ import java.util.Arrays;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class AssetEditorRequestDataSetEvent
/*    */   implements IAsyncEvent<String> {
/*    */   private final EditorClient editorClient;
/*    */   private final String dataSet;
/*    */   private String[] results;
/*    */   
/*    */   public AssetEditorRequestDataSetEvent(EditorClient editorClient, String dataSet, String[] results) {
/* 15 */     this.editorClient = editorClient;
/* 16 */     this.dataSet = dataSet;
/* 17 */     this.results = results;
/*    */   }
/*    */   
/*    */   public String getDataSet() {
/* 21 */     return this.dataSet;
/*    */   }
/*    */   
/*    */   public EditorClient getEditorClient() {
/* 25 */     return this.editorClient;
/*    */   }
/*    */   
/*    */   public String[] getResults() {
/* 29 */     return this.results;
/*    */   }
/*    */   
/*    */   public void setResults(String[] results) {
/* 33 */     this.results = results;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 39 */     return "AssetEditorRequestDataSetEvent{editorClient=" + String.valueOf(this.editorClient) + ", dataSet='" + this.dataSet + "', results=" + 
/*    */ 
/*    */       
/* 42 */       Arrays.toString((Object[])this.results) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\asseteditor\event\AssetEditorRequestDataSetEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */