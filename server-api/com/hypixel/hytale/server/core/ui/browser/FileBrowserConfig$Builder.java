/*     */ package com.hypixel.hytale.server.core.ui.browser;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Builder
/*     */ {
/*  43 */   private String listElementId = "#FileList";
/*  44 */   private String rootSelectorId = "#RootSelector";
/*  45 */   private String searchInputId = "#SearchInput";
/*  46 */   private String currentPathId = null;
/*  47 */   private List<FileBrowserConfig.RootEntry> roots = List.of();
/*  48 */   private Set<String> allowedExtensions = Set.of();
/*     */   private boolean enableRootSelector = true;
/*     */   private boolean enableSearch = true;
/*     */   private boolean enableDirectoryNav = true;
/*     */   private boolean enableMultiSelect = false;
/*  53 */   private int maxResults = 50;
/*  54 */   private FileListProvider customProvider = null;
/*     */   
/*     */   public Builder listElementId(@Nonnull String listElementId) {
/*  57 */     this.listElementId = listElementId;
/*  58 */     return this;
/*     */   }
/*     */   
/*     */   public Builder rootSelectorId(@Nullable String rootSelectorId) {
/*  62 */     this.rootSelectorId = rootSelectorId;
/*  63 */     return this;
/*     */   }
/*     */   
/*     */   public Builder searchInputId(@Nullable String searchInputId) {
/*  67 */     this.searchInputId = searchInputId;
/*  68 */     return this;
/*     */   }
/*     */   
/*     */   public Builder currentPathId(@Nullable String currentPathId) {
/*  72 */     this.currentPathId = currentPathId;
/*  73 */     return this;
/*     */   }
/*     */   
/*     */   public Builder roots(@Nonnull List<FileBrowserConfig.RootEntry> roots) {
/*  77 */     this.roots = roots;
/*  78 */     return this;
/*     */   }
/*     */   
/*     */   public Builder allowedExtensions(@Nonnull String... extensions) {
/*  82 */     this.allowedExtensions = Set.of(extensions);
/*  83 */     return this;
/*     */   }
/*     */   
/*     */   public Builder allowedExtensions(@Nonnull Set<String> extensions) {
/*  87 */     this.allowedExtensions = extensions;
/*  88 */     return this;
/*     */   }
/*     */   
/*     */   public Builder enableRootSelector(boolean enable) {
/*  92 */     this.enableRootSelector = enable;
/*  93 */     return this;
/*     */   }
/*     */   
/*     */   public Builder enableSearch(boolean enable) {
/*  97 */     this.enableSearch = enable;
/*  98 */     return this;
/*     */   }
/*     */   
/*     */   public Builder enableDirectoryNav(boolean enable) {
/* 102 */     this.enableDirectoryNav = enable;
/* 103 */     return this;
/*     */   }
/*     */   
/*     */   public Builder enableMultiSelect(boolean enable) {
/* 107 */     this.enableMultiSelect = enable;
/* 108 */     return this;
/*     */   }
/*     */   
/*     */   public Builder maxResults(int maxResults) {
/* 112 */     this.maxResults = maxResults;
/* 113 */     return this;
/*     */   }
/*     */   
/*     */   public Builder customProvider(@Nullable FileListProvider provider) {
/* 117 */     this.customProvider = provider;
/* 118 */     return this;
/*     */   }
/*     */   
/*     */   public FileBrowserConfig build() {
/* 122 */     return new FileBrowserConfig(this.listElementId, this.rootSelectorId, this.searchInputId, this.currentPathId, this.roots, this.allowedExtensions, this.enableRootSelector, this.enableSearch, this.enableDirectoryNav, this.enableMultiSelect, this.maxResults, this.customProvider);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\ui\browser\FileBrowserConfig$Builder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */