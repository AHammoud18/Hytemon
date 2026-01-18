/*     */ package com.hypixel.hytale.builtin.asseteditor.datasource;
/*     */ import com.hypixel.hytale.builtin.asseteditor.AssetTree;
/*     */ import com.hypixel.hytale.builtin.asseteditor.EditorClient;
/*     */ import com.hypixel.hytale.builtin.asseteditor.assettypehandler.AssetTypeHandler;
/*     */ import com.hypixel.hytale.builtin.asseteditor.data.AssetState;
/*     */ import com.hypixel.hytale.builtin.asseteditor.data.ModifiedAsset;
/*     */ import com.hypixel.hytale.codec.ExtraInfo;
/*     */ import com.hypixel.hytale.common.plugin.PluginManifest;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.server.core.Options;
/*     */ import com.hypixel.hytale.server.core.plugin.PluginManager;
/*     */ import com.hypixel.hytale.server.core.util.BsonUtil;
/*     */ import com.hypixel.hytale.server.core.util.HashUtil;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.OpenOption;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.StandardOpenOption;
/*     */ import java.nio.file.attribute.FileAttribute;
/*     */ import java.time.Instant;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.Collection;
/*     */ import java.util.Deque;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ScheduledFuture;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import java.util.logging.Level;
/*     */ import org.bson.BsonArray;
/*     */ import org.bson.BsonDocument;
/*     */ import org.bson.BsonValue;
/*     */ 
/*     */ public class StandardDataSource implements DataSource {
/*  35 */   private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */   
/*     */   private final Path rootPath;
/*     */   private final ConcurrentHashMap<Path, Deque<EditorFileSaveInfo>> editorSaves;
/*     */   private final AssetTree assetTree;
/*     */   private final String packKey;
/*     */   private final PluginManifest manifest;
/*     */   private final boolean isImmutable;
/*     */   private final Path recentModificationsFilePath;
/*  44 */   private final AtomicBoolean indexNeedsSaving = new AtomicBoolean();
/*  45 */   private final Map<Path, ModifiedAsset> modifiedAssets = new ConcurrentHashMap<>(); private ScheduledFuture<?> saveSchedule; private boolean isAssetPackBeDeleteable;
/*     */   static final class EditorFileSaveInfo extends Record { private final String hash;
/*     */     private final long expiryMs;
/*     */     
/*  49 */     EditorFileSaveInfo(String hash, long expiryMs) { this.hash = hash; this.expiryMs = expiryMs; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/builtin/asseteditor/datasource/StandardDataSource$EditorFileSaveInfo;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #49	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*  49 */       //   0	7	0	this	Lcom/hypixel/hytale/builtin/asseteditor/datasource/StandardDataSource$EditorFileSaveInfo; } public String hash() { return this.hash; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/builtin/asseteditor/datasource/StandardDataSource$EditorFileSaveInfo;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #49	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/hypixel/hytale/builtin/asseteditor/datasource/StandardDataSource$EditorFileSaveInfo; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/builtin/asseteditor/datasource/StandardDataSource$EditorFileSaveInfo;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #49	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lcom/hypixel/hytale/builtin/asseteditor/datasource/StandardDataSource$EditorFileSaveInfo;
/*  49 */       //   0	8	1	o	Ljava/lang/Object; } public long expiryMs() { return this.expiryMs; }
/*     */      }
/*     */   
/*     */   public StandardDataSource(String packKey, Path rootPath, boolean isImmutable, PluginManifest manifest) {
/*  53 */     this.rootPath = rootPath;
/*  54 */     this.editorSaves = new ConcurrentHashMap<>();
/*  55 */     this.packKey = packKey;
/*  56 */     this.isImmutable = isImmutable;
/*  57 */     this.manifest = manifest;
/*  58 */     this.isAssetPackBeDeleteable = (!isImmutable && isInModsDirectory(rootPath));
/*  59 */     this.assetTree = new AssetTree(rootPath, packKey, isImmutable, this.isAssetPackBeDeleteable);
/*  60 */     this.recentModificationsFilePath = Path.of("assetEditor", new String[] { "recentAssetEdits_" + packKey.replace(':', '-') + ".json" });
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isInModsDirectory(Path path) {
/*  65 */     if (path.startsWith(PluginManager.MODS_PATH)) {
/*  66 */       return true;
/*     */     }
/*     */     
/*  69 */     for (Path modsPath : Options.getOptionSet().valuesOf(Options.MODS_DIRECTORIES)) {
/*  70 */       if (path.startsWith(modsPath)) {
/*  71 */         return true;
/*     */       }
/*     */     } 
/*  74 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void start() {
/*  79 */     loadRecentModifications();
/*     */     
/*  81 */     this.saveSchedule = HytaleServer.SCHEDULED_EXECUTOR.scheduleWithFixedDelay(() -> {
/*     */           try {
/*     */             saveRecentModifications();
/*  84 */           } catch (Exception e) {
/*     */             ((HytaleLogger.Api)LOGGER.at(Level.SEVERE).withCause(e)).log("Failed to save assets index");
/*     */           } 
/*     */         }1L, 1L, TimeUnit.MINUTES);
/*     */   }
/*     */ 
/*     */   
/*     */   public void shutdown() {
/*  92 */     this.saveSchedule.cancel(false);
/*  93 */     saveRecentModifications();
/*     */   }
/*     */   
/*     */   private void loadRecentModifications() {
/*  97 */     Path path = this.recentModificationsFilePath;
/*  98 */     if (!Files.exists(path, new java.nio.file.LinkOption[0])) {
/*  99 */       path = path.resolveSibling(String.valueOf(path.getFileName()) + ".bak");
/* 100 */       if (!Files.exists(path, new java.nio.file.LinkOption[0]))
/*     */         return; 
/*     */     } 
/* 103 */     BsonDocument doc = BsonUtil.readDocument(path).join();
/* 104 */     BsonArray assets = doc.getArray("Assets");
/*     */     
/* 106 */     for (BsonValue asset : assets) {
/* 107 */       ModifiedAsset modifiedAsset = (ModifiedAsset)ModifiedAsset.CODEC.decode(asset, new ExtraInfo());
/* 108 */       if (modifiedAsset == null) {
/*     */         continue;
/*     */       }
/*     */       
/* 112 */       this.modifiedAssets.put(modifiedAsset.path, modifiedAsset);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void saveRecentModifications() {
/* 117 */     if (!this.indexNeedsSaving.getAndSet(false))
/*     */       return; 
/* 119 */     LOGGER.at(Level.INFO).log("Saving recent asset modification index...");
/*     */     
/* 121 */     BsonDocument doc = new BsonDocument();
/*     */     
/* 123 */     BsonArray assetsArray = new BsonArray();
/* 124 */     for (Map.Entry<Path, ModifiedAsset> modifiedAsset : this.modifiedAssets.entrySet()) {
/* 125 */       assetsArray.add((BsonValue)ModifiedAsset.CODEC.encode(modifiedAsset.getValue(), new ExtraInfo()));
/*     */     }
/* 127 */     doc.append("Assets", (BsonValue)assetsArray);
/*     */     
/*     */     try {
/* 130 */       BsonUtil.writeDocument(this.recentModificationsFilePath, doc);
/* 131 */     } catch (Exception ex) {
/* 132 */       ((HytaleLogger.Api)LOGGER.at(Level.SEVERE).withCause(ex)).log("Failed to save recent asset modification index...");
/* 133 */       this.indexNeedsSaving.set(true);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean canAssetPackBeDeleted() {
/* 138 */     return this.isAssetPackBeDeleteable;
/*     */   }
/*     */ 
/*     */   
/*     */   public Path resolveAbsolutePath(Path path) {
/* 143 */     return this.rootPath.resolve(path.toString()).toAbsolutePath();
/*     */   }
/*     */ 
/*     */   
/*     */   public Path getFullPathToAssetData(Path assetPath) {
/* 148 */     return resolveAbsolutePath(assetPath);
/*     */   }
/*     */ 
/*     */   
/*     */   public AssetTree getAssetTree() {
/* 153 */     return this.assetTree;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isImmutable() {
/* 158 */     return this.isImmutable;
/*     */   }
/*     */ 
/*     */   
/*     */   public Path getRootPath() {
/* 163 */     return this.rootPath;
/*     */   }
/*     */ 
/*     */   
/*     */   public PluginManifest getManifest() {
/* 168 */     return this.manifest;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean doesDirectoryExist(Path folderPath) {
/* 173 */     return Files.isDirectory(resolveAbsolutePath(folderPath), new java.nio.file.LinkOption[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean createDirectory(Path dirPath, EditorClient editorClient) {
/*     */     try {
/* 179 */       Files.createDirectory(resolveAbsolutePath(dirPath), (FileAttribute<?>[])new FileAttribute[0]);
/* 180 */     } catch (IOException e) {
/* 181 */       ((HytaleLogger.Api)LOGGER.at(Level.WARNING).withCause(e)).log("Failed to create directory %s", dirPath);
/* 182 */       return false;
/*     */     } 
/*     */     
/* 185 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean deleteDirectory(Path dirPath) {
/*     */     try {
/* 191 */       Files.deleteIfExists(resolveAbsolutePath(dirPath));
/* 192 */     } catch (IOException e) {
/* 193 */       ((HytaleLogger.Api)LOGGER.at(Level.WARNING).withCause(e)).log("Failed to delete directory %s", dirPath);
/* 194 */       return false;
/*     */     } 
/*     */     
/* 197 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean moveDirectory(Path oldDirPath, Path newDirPath) {
/*     */     try {
/* 203 */       Files.move(resolveAbsolutePath(oldDirPath), resolveAbsolutePath(newDirPath), new java.nio.file.CopyOption[0]);
/* 204 */     } catch (IOException e) {
/* 205 */       ((HytaleLogger.Api)LOGGER.at(Level.WARNING).withCause(e)).log("Failed to move directory %s to %s", oldDirPath, newDirPath);
/* 206 */       return false;
/*     */     } 
/*     */     
/* 209 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean doesAssetExist(Path assetPath) {
/* 214 */     return Files.isRegularFile(resolveAbsolutePath(assetPath), new java.nio.file.LinkOption[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] getAssetBytes(Path assetPath) {
/*     */     try {
/* 220 */       return Files.readAllBytes(resolveAbsolutePath(assetPath));
/* 221 */     } catch (IOException e) {
/* 222 */       ((HytaleLogger.Api)LOGGER.at(Level.WARNING).withCause(e)).log("Failed to read asset %s", assetPath);
/* 223 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean updateAsset(Path assetPath, byte[] bytes, EditorClient editorClient) {
/* 229 */     Path path = resolveAbsolutePath(assetPath);
/*     */     try {
/* 231 */       String hash = HashUtil.sha256(bytes);
/* 232 */       trackEditorFileSave(assetPath, hash);
/*     */       
/* 234 */       Files.write(path, bytes, new OpenOption[] { StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE });
/*     */       
/* 236 */       ModifiedAsset modifiedAsset = new ModifiedAsset();
/* 237 */       modifiedAsset.path = assetPath;
/* 238 */       modifiedAsset.state = AssetState.CHANGED;
/* 239 */       modifiedAsset.markEditedBy(editorClient);
/* 240 */       putModifiedAsset(modifiedAsset);
/* 241 */     } catch (IOException e) {
/* 242 */       ((HytaleLogger.Api)LOGGER.at(Level.WARNING).withCause(e)).log("Failed to update asset %s", assetPath);
/* 243 */       return false;
/*     */     } 
/* 245 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean createAsset(Path assetPath, byte[] bytes, EditorClient editorClient) {
/* 250 */     Path path = resolveAbsolutePath(assetPath);
/*     */     try {
/* 252 */       String hash = HashUtil.sha256(bytes);
/* 253 */       trackEditorFileSave(assetPath, hash);
/*     */       
/* 255 */       Files.createDirectories(path.getParent(), (FileAttribute<?>[])new FileAttribute[0]);
/* 256 */       Files.write(path, bytes, new OpenOption[] { StandardOpenOption.CREATE });
/*     */       
/* 258 */       ModifiedAsset modifiedAsset = new ModifiedAsset();
/* 259 */       modifiedAsset.path = assetPath;
/* 260 */       modifiedAsset.state = AssetState.NEW;
/* 261 */       modifiedAsset.markEditedBy(editorClient);
/* 262 */       putModifiedAsset(modifiedAsset);
/* 263 */     } catch (IOException e) {
/* 264 */       ((HytaleLogger.Api)LOGGER.at(Level.WARNING).withCause(e)).log("Failed to create asset %s", assetPath);
/* 265 */       return false;
/*     */     } 
/* 267 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean deleteAsset(Path assetPath, EditorClient editorClient) {
/*     */     try {
/* 273 */       Files.deleteIfExists(resolveAbsolutePath(assetPath));
/*     */       
/* 275 */       ModifiedAsset modifiedAsset = new ModifiedAsset();
/* 276 */       modifiedAsset.path = assetPath;
/* 277 */       modifiedAsset.state = AssetState.DELETED;
/* 278 */       modifiedAsset.markEditedBy(editorClient);
/* 279 */       putModifiedAsset(modifiedAsset);
/* 280 */     } catch (IOException e) {
/* 281 */       ((HytaleLogger.Api)LOGGER.at(Level.WARNING).withCause(e)).log("Failed to delete asset %s", assetPath);
/* 282 */       return false;
/*     */     } 
/*     */     
/* 285 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldReloadAssetFromDisk(Path assetPath) {
/* 290 */     Deque<EditorFileSaveInfo> fileSaveInfos = this.editorSaves.get(assetPath);
/* 291 */     if (fileSaveInfos == null || fileSaveInfos.isEmpty()) {
/* 292 */       return true;
/*     */     }
/*     */     
/* 295 */     byte[] bytes = getAssetBytes(assetPath);
/* 296 */     if (bytes == null) {
/* 297 */       return true;
/*     */     }
/*     */     
/* 300 */     String hash = HashUtil.sha256(bytes);
/* 301 */     long now = System.currentTimeMillis();
/*     */     
/* 303 */     synchronized (fileSaveInfos) {
/* 304 */       fileSaveInfos.removeIf(m -> (m.expiryMs <= now));
/*     */       
/* 306 */       for (EditorFileSaveInfo m : fileSaveInfos) {
/* 307 */         if (m.hash.equals(hash)) {
/* 308 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 313 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public Instant getLastModificationTimestamp(Path assetPath) {
/* 318 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean moveAsset(Path oldAssetPath, Path newAssetPath, EditorClient editorClient) {
/*     */     try {
/* 324 */       Files.move(resolveAbsolutePath(oldAssetPath), resolveAbsolutePath(newAssetPath), new java.nio.file.CopyOption[0]);
/*     */       
/* 326 */       ModifiedAsset modifiedAsset = new ModifiedAsset();
/* 327 */       modifiedAsset.path = newAssetPath;
/* 328 */       modifiedAsset.oldPath = oldAssetPath;
/* 329 */       modifiedAsset.state = AssetState.CHANGED;
/* 330 */       modifiedAsset.markEditedBy(editorClient);
/* 331 */       putModifiedAsset(modifiedAsset);
/* 332 */     } catch (IOException e) {
/* 333 */       ((HytaleLogger.Api)LOGGER.at(Level.WARNING).withCause(e)).log("Failed to move asset %s to %s", oldAssetPath, newAssetPath);
/* 334 */       return false;
/*     */     } 
/*     */     
/* 337 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public AssetTree loadAssetTree(Collection<AssetTypeHandler> assetTypes) {
/* 342 */     return new AssetTree(this.rootPath, this.packKey, this.isImmutable, this.isAssetPackBeDeleteable, assetTypes);
/*     */   }
/*     */   
/*     */   public void putModifiedAsset(ModifiedAsset modifiedAsset) {
/* 346 */     this.modifiedAssets.put(modifiedAsset.path, modifiedAsset);
/*     */     
/* 348 */     if (this.modifiedAssets.size() > 50) {
/* 349 */       ModifiedAsset oldestAsset = null;
/* 350 */       for (ModifiedAsset asset : this.modifiedAssets.values()) {
/* 351 */         if (oldestAsset == null) {
/* 352 */           oldestAsset = asset; continue;
/* 353 */         }  if (asset.lastModificationTimestamp.isBefore(oldestAsset.lastModificationTimestamp)) {
/* 354 */           oldestAsset = asset;
/*     */         }
/*     */       } 
/*     */       
/* 358 */       this.modifiedAssets.remove(oldestAsset.path);
/*     */     } 
/*     */     
/* 361 */     this.indexNeedsSaving.set(true);
/*     */   }
/*     */   
/*     */   public Map<Path, ModifiedAsset> getRecentlyModifiedAssets() {
/* 365 */     return this.modifiedAssets;
/*     */   }
/*     */   
/*     */   private void trackEditorFileSave(Path path, String hash) {
/* 369 */     Deque<EditorFileSaveInfo> fileSaves = this.editorSaves.computeIfAbsent(path, p -> new ArrayDeque());
/*     */     
/* 371 */     synchronized (fileSaves) {
/* 372 */       fileSaves.addLast(new EditorFileSaveInfo(hash, System.currentTimeMillis() + 30000L));
/*     */ 
/*     */       
/* 375 */       while (fileSaves.size() > 20)
/* 376 */         fileSaves.removeFirst(); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\asseteditor\datasource\StandardDataSource.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */