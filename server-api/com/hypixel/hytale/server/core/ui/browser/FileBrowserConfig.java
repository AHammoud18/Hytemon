/*     */ package com.hypixel.hytale.server.core.ui.browser;
/*     */ public final class FileBrowserConfig extends Record { @Nonnull
/*     */   private final String listElementId;
/*     */   @Nullable
/*     */   private final String rootSelectorId;
/*     */   @Nullable
/*     */   private final String searchInputId;
/*     */   @Nullable
/*     */   private final String currentPathId;
/*     */   @Nonnull
/*     */   private final List<RootEntry> roots;
/*     */   @Nonnull
/*     */   private final Set<String> allowedExtensions;
/*     */   
/*  15 */   public FileBrowserConfig(@Nonnull String listElementId, @Nullable String rootSelectorId, @Nullable String searchInputId, @Nullable String currentPathId, @Nonnull List<RootEntry> roots, @Nonnull Set<String> allowedExtensions, boolean enableRootSelector, boolean enableSearch, boolean enableDirectoryNav, boolean enableMultiSelect, int maxResults, @Nullable FileListProvider customProvider) { this.listElementId = listElementId; this.rootSelectorId = rootSelectorId; this.searchInputId = searchInputId; this.currentPathId = currentPathId; this.roots = roots; this.allowedExtensions = allowedExtensions; this.enableRootSelector = enableRootSelector; this.enableSearch = enableSearch; this.enableDirectoryNav = enableDirectoryNav; this.enableMultiSelect = enableMultiSelect; this.maxResults = maxResults; this.customProvider = customProvider; } private final boolean enableRootSelector; private final boolean enableSearch; private final boolean enableDirectoryNav; private final boolean enableMultiSelect; private final int maxResults; @Nullable private final FileListProvider customProvider; public final String toString() { // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/server/core/ui/browser/FileBrowserConfig;)Ljava/lang/String;
/*     */     //   6: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #15	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lcom/hypixel/hytale/server/core/ui/browser/FileBrowserConfig; } public final int hashCode() { // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/server/core/ui/browser/FileBrowserConfig;)I
/*     */     //   6: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #15	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lcom/hypixel/hytale/server/core/ui/browser/FileBrowserConfig; } public final boolean equals(Object o) { // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/server/core/ui/browser/FileBrowserConfig;Ljava/lang/Object;)Z
/*     */     //   7: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #15	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	8	0	this	Lcom/hypixel/hytale/server/core/ui/browser/FileBrowserConfig;
/*  15 */     //   0	8	1	o	Ljava/lang/Object; } @Nonnull public String listElementId() { return this.listElementId; } @Nullable public String rootSelectorId() { return this.rootSelectorId; } @Nullable public String searchInputId() { return this.searchInputId; } @Nullable public String currentPathId() { return this.currentPathId; } @Nonnull public List<RootEntry> roots() { return this.roots; } @Nonnull public Set<String> allowedExtensions() { return this.allowedExtensions; } public boolean enableRootSelector() { return this.enableRootSelector; } public boolean enableSearch() { return this.enableSearch; } public boolean enableDirectoryNav() { return this.enableDirectoryNav; } public boolean enableMultiSelect() { return this.enableMultiSelect; } public int maxResults() { return this.maxResults; } @Nullable public FileListProvider customProvider() { return this.customProvider; }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class RootEntry
/*     */     extends Record
/*     */   {
/*     */     @Nonnull
/*     */     private final LocalizableString displayName;
/*     */     
/*     */     @Nonnull
/*     */     private final Path path;
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Path path()
/*     */     {
/*  32 */       return this.path; } @Nonnull public LocalizableString displayName() { return this.displayName; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/server/core/ui/browser/FileBrowserConfig$RootEntry;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #32	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lcom/hypixel/hytale/server/core/ui/browser/FileBrowserConfig$RootEntry;
/*     */       //   0	8	1	o	Ljava/lang/Object; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/server/core/ui/browser/FileBrowserConfig$RootEntry;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #32	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/hypixel/hytale/server/core/ui/browser/FileBrowserConfig$RootEntry; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/server/core/ui/browser/FileBrowserConfig$RootEntry;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #32	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*  32 */       //   0	7	0	this	Lcom/hypixel/hytale/server/core/ui/browser/FileBrowserConfig$RootEntry; } public RootEntry(@Nonnull LocalizableString displayName, @Nonnull Path path) { this.displayName = displayName; this.path = path; }
/*     */      public RootEntry(@Nonnull String displayName, @Nonnull Path path) {
/*  34 */       this(LocalizableString.fromString(displayName), path);
/*     */     }
/*     */   }
/*     */   
/*     */   public static Builder builder() {
/*  39 */     return new Builder();
/*     */   }
/*     */   
/*     */   public static class Builder {
/*  43 */     private String listElementId = "#FileList";
/*  44 */     private String rootSelectorId = "#RootSelector";
/*  45 */     private String searchInputId = "#SearchInput";
/*  46 */     private String currentPathId = null;
/*  47 */     private List<FileBrowserConfig.RootEntry> roots = List.of();
/*  48 */     private Set<String> allowedExtensions = Set.of();
/*     */     private boolean enableRootSelector = true;
/*     */     private boolean enableSearch = true;
/*     */     private boolean enableDirectoryNav = true;
/*     */     private boolean enableMultiSelect = false;
/*  53 */     private int maxResults = 50;
/*  54 */     private FileListProvider customProvider = null;
/*     */     
/*     */     public Builder listElementId(@Nonnull String listElementId) {
/*  57 */       this.listElementId = listElementId;
/*  58 */       return this;
/*     */     }
/*     */     
/*     */     public Builder rootSelectorId(@Nullable String rootSelectorId) {
/*  62 */       this.rootSelectorId = rootSelectorId;
/*  63 */       return this;
/*     */     }
/*     */     
/*     */     public Builder searchInputId(@Nullable String searchInputId) {
/*  67 */       this.searchInputId = searchInputId;
/*  68 */       return this;
/*     */     }
/*     */     
/*     */     public Builder currentPathId(@Nullable String currentPathId) {
/*  72 */       this.currentPathId = currentPathId;
/*  73 */       return this;
/*     */     }
/*     */     
/*     */     public Builder roots(@Nonnull List<FileBrowserConfig.RootEntry> roots) {
/*  77 */       this.roots = roots;
/*  78 */       return this;
/*     */     }
/*     */     
/*     */     public Builder allowedExtensions(@Nonnull String... extensions) {
/*  82 */       this.allowedExtensions = Set.of(extensions);
/*  83 */       return this;
/*     */     }
/*     */     
/*     */     public Builder allowedExtensions(@Nonnull Set<String> extensions) {
/*  87 */       this.allowedExtensions = extensions;
/*  88 */       return this;
/*     */     }
/*     */     
/*     */     public Builder enableRootSelector(boolean enable) {
/*  92 */       this.enableRootSelector = enable;
/*  93 */       return this;
/*     */     }
/*     */     
/*     */     public Builder enableSearch(boolean enable) {
/*  97 */       this.enableSearch = enable;
/*  98 */       return this;
/*     */     }
/*     */     
/*     */     public Builder enableDirectoryNav(boolean enable) {
/* 102 */       this.enableDirectoryNav = enable;
/* 103 */       return this;
/*     */     }
/*     */     
/*     */     public Builder enableMultiSelect(boolean enable) {
/* 107 */       this.enableMultiSelect = enable;
/* 108 */       return this;
/*     */     }
/*     */     
/*     */     public Builder maxResults(int maxResults) {
/* 112 */       this.maxResults = maxResults;
/* 113 */       return this;
/*     */     }
/*     */     
/*     */     public Builder customProvider(@Nullable FileListProvider provider) {
/* 117 */       this.customProvider = provider;
/* 118 */       return this;
/*     */     }
/*     */     
/*     */     public FileBrowserConfig build() {
/* 122 */       return new FileBrowserConfig(this.listElementId, this.rootSelectorId, this.searchInputId, this.currentPathId, this.roots, this.allowedExtensions, this.enableRootSelector, this.enableSearch, this.enableDirectoryNav, this.enableMultiSelect, this.maxResults, this.customProvider);
/*     */     }
/*     */   } }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\ui\browser\FileBrowserConfig.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */