/*    */ package com.hypixel.hytale.server.core.ui.browser;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FileBrowserEventData
/*    */ {
/*    */   public static final String KEY_FILE = "File";
/*    */   public static final String KEY_ROOT = "@Root";
/*    */   public static final String KEY_SEARCH_QUERY = "@SearchQuery";
/*    */   public static final String KEY_SEARCH_RESULT = "SearchResult";
/*    */   public static final String KEY_BROWSE = "Browse";
/*    */   public static final BuilderCodec<FileBrowserEventData> CODEC;
/*    */   @Nullable
/*    */   private String file;
/*    */   @Nullable
/*    */   private String root;
/*    */   @Nullable
/*    */   private String searchQuery;
/*    */   @Nullable
/*    */   private String searchResult;
/*    */   @Nullable
/*    */   private Boolean browse;
/*    */   
/*    */   static {
/* 47 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(FileBrowserEventData.class, FileBrowserEventData::new).addField(new KeyedCodec("File", (Codec)Codec.STRING), (entry, s) -> entry.file = s, entry -> entry.file)).addField(new KeyedCodec("@Root", (Codec)Codec.STRING), (entry, s) -> entry.root = s, entry -> entry.root)).addField(new KeyedCodec("@SearchQuery", (Codec)Codec.STRING), (entry, s) -> entry.searchQuery = s, entry -> entry.searchQuery)).addField(new KeyedCodec("SearchResult", (Codec)Codec.STRING), (entry, s) -> entry.searchResult = s, entry -> entry.searchResult)).addField(new KeyedCodec("Browse", (Codec)Codec.STRING), (entry, s) -> entry.browse = Boolean.valueOf("true".equalsIgnoreCase(s)), entry -> (entry.browse != null && entry.browse.booleanValue()) ? "true" : null)).build();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public String getFile() {
/* 62 */     return this.file;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public String getRoot() {
/* 67 */     return this.root;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public String getSearchQuery() {
/* 72 */     return this.searchQuery;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public String getSearchResult() {
/* 77 */     return this.searchResult;
/*    */   }
/*    */   
/*    */   public boolean isBrowseRequested() {
/* 81 */     return (this.browse != null && this.browse.booleanValue());
/*    */   }
/*    */   
/*    */   public static FileBrowserEventData file(String file) {
/* 85 */     FileBrowserEventData data = new FileBrowserEventData();
/* 86 */     data.file = file;
/* 87 */     return data;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\ui\browser\FileBrowserEventData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */