/*     */ package com.hypixel.hytale.builtin.buildertools.scriptedbrushes.ui;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*     */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.ScriptedBrushAsset;
/*     */ import com.hypixel.hytale.common.util.StringCompareUtil;
/*     */ import com.hypixel.hytale.server.core.ui.browser.FileListProvider;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.nio.file.Path;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.stream.Collectors;
/*     */ import javax.annotation.Nonnull;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ScriptedBrushListProvider
/*     */   implements FileListProvider
/*     */ {
/*     */   @Nonnull
/*     */   public List<FileListProvider.FileEntry> getFiles(@Nonnull Path currentDir, @Nonnull String searchQuery) {
/* 129 */     DefaultAssetMap<String, ScriptedBrushAsset> assetMap = ScriptedBrushAsset.getAssetMap();
/*     */     
/* 131 */     if (searchQuery.isEmpty()) {
/* 132 */       return (List<FileListProvider.FileEntry>)assetMap.getAssetMap().keySet().stream()
/* 133 */         .sorted()
/* 134 */         .map(name -> new FileListProvider.FileEntry(name, false))
/* 135 */         .collect(Collectors.toList());
/*     */     }
/*     */ 
/*     */     
/* 139 */     ObjectArrayList<FileListProvider.FileEntry> objectArrayList = new ObjectArrayList();
/* 140 */     for (String name : assetMap.getAssetMap().keySet()) {
/* 141 */       int score = StringCompareUtil.getFuzzyDistance(name, searchQuery, Locale.ENGLISH);
/* 142 */       if (score > 0) {
/* 143 */         objectArrayList.add(new FileListProvider.FileEntry(name, name, false, score));
/*     */       }
/*     */     } 
/*     */     
/* 147 */     objectArrayList.sort(Comparator.<FileListProvider.FileEntry>comparingInt(FileListProvider.FileEntry::matchScore).reversed());
/*     */     
/* 149 */     if (objectArrayList.size() > 50) {
/* 150 */       return objectArrayList.subList(0, 50);
/*     */     }
/* 152 */     return (List<FileListProvider.FileEntry>)objectArrayList;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\scriptedbrushe\\ui\ScriptedBrushPage$ScriptedBrushListProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */