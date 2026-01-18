/*    */ package com.hypixel.hytale.builtin.asseteditor.event;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.asseteditor.EditorClient;
/*    */ import com.hypixel.hytale.event.IAsyncEvent;
/*    */ import java.util.Arrays;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class AssetEditorFetchAutoCompleteDataEvent
/*    */   implements IAsyncEvent<String> {
/*    */   private final EditorClient editorClient;
/*    */   private final String dataSet;
/*    */   private final String query;
/*    */   private String[] results;
/*    */   
/*    */   public AssetEditorFetchAutoCompleteDataEvent(EditorClient editorClient, String dataSet, String query) {
/* 16 */     this.editorClient = editorClient;
/* 17 */     this.dataSet = dataSet;
/* 18 */     this.query = query;
/*    */   }
/*    */   
/*    */   public String getQuery() {
/* 22 */     return this.query;
/*    */   }
/*    */   
/*    */   public String getDataSet() {
/* 26 */     return this.dataSet;
/*    */   }
/*    */   
/*    */   public EditorClient getEditorClient() {
/* 30 */     return this.editorClient;
/*    */   }
/*    */   
/*    */   public String[] getResults() {
/* 34 */     return this.results;
/*    */   }
/*    */   
/*    */   public void setResults(String[] results) {
/* 38 */     this.results = results;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 44 */     return "AssetEditorFetchAutoCompleteDataEvent{editorClient=" + String.valueOf(this.editorClient) + ", dataSet='" + this.dataSet + "', query='" + this.query + "', results=" + 
/*    */ 
/*    */ 
/*    */       
/* 48 */       Arrays.toString((Object[])this.results) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\asseteditor\event\AssetEditorFetchAutoCompleteDataEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */