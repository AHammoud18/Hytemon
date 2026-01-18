/*     */ package com.hypixel.hytale.server.core.prefab;
/*     */ import com.hypixel.hytale.assetstore.AssetPack;
/*     */ import com.hypixel.hytale.common.plugin.PluginManifest;
/*     */ import com.hypixel.hytale.server.core.asset.AssetModule;
/*     */ import com.hypixel.hytale.server.core.prefab.config.SelectionPrefabSerializer;
/*     */ import com.hypixel.hytale.server.core.prefab.selection.standard.BlockSelection;
/*     */ import com.hypixel.hytale.server.core.universe.Universe;
/*     */ import com.hypixel.hytale.server.core.util.AssetUtil;
/*     */ import com.hypixel.hytale.server.core.util.BsonUtil;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Predicate;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.stream.Collectors;
/*     */ import java.util.stream.Stream;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class PrefabStore {
/*     */   public static final Predicate<Path> PREFAB_FILTER;
/*     */   
/*     */   static {
/*  30 */     PREFAB_FILTER = (path -> path.toString().endsWith(".prefab.json"));
/*     */   }
/*  32 */   public static final Path PREFABS_PATH = Path.of("prefabs", new String[0]);
/*     */   
/*     */   private static final String DEFAULT_WORLDGEN_NAME = "Default";
/*  35 */   private static final PrefabStore INSTANCE = new PrefabStore();
/*  36 */   private final Map<Path, BlockSelection> PREFAB_CACHE = new ConcurrentHashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public BlockSelection getServerPrefab(@Nonnull String key) {
/*  43 */     return getPrefab(getServerPrefabsPath().resolve(key));
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public BlockSelection getPrefab(@Nonnull Path path) {
/*  48 */     return this.PREFAB_CACHE.computeIfAbsent(path.toAbsolutePath().normalize(), p -> {
/*     */           if (Files.exists(p, new java.nio.file.LinkOption[0])) {
/*     */             return SelectionPrefabSerializer.deserialize(BsonUtil.readDocument(p).join());
/*     */           }
/*     */           throw new PrefabLoadException(PrefabLoadException.Type.NOT_FOUND);
/*     */         });
/*     */   }
/*     */   
/*     */   public Path getServerPrefabsPath() {
/*  57 */     return PREFABS_PATH;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Map<Path, BlockSelection> getServerPrefabDir(@Nonnull String key) {
/*  62 */     return getPrefabDir(getServerPrefabsPath().resolve(key));
/*     */   }
/*     */   @Nonnull
/*     */   public Map<Path, BlockSelection> getPrefabDir(@Nonnull Path dir) {
/*     */     
/*  67 */     try { Stream<Path> stream = Files.list(dir); 
/*  68 */       try { Map<Path, BlockSelection> map = (Map)stream.filter(PREFAB_FILTER).sorted().collect(Collectors.toMap(Function.identity(), this::getPrefab, (u, v) -> { throw new IllegalStateException(String.format("Duplicate key %s", new Object[] { u })); }java.util.LinkedHashMap::new));
/*     */ 
/*     */         
/*  71 */         if (stream != null) stream.close();  return map; } catch (Throwable throwable) { if (stream != null) try { stream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException e)
/*  72 */     { throw new RuntimeException("Failed to list directory " + String.valueOf(dir), e); }
/*     */   
/*     */   }
/*     */   
/*     */   public void saveServerPrefab(@Nonnull String key, @Nonnull BlockSelection prefab) {
/*  77 */     saveWorldGenPrefab(key, prefab, false);
/*     */   }
/*     */   
/*     */   public void saveWorldGenPrefab(@Nonnull String key, @Nonnull BlockSelection prefab, boolean overwrite) {
/*  81 */     savePrefab(getWorldGenPrefabsPath().resolve(key), prefab, overwrite);
/*     */   }
/*     */   
/*     */   public void savePrefab(@Nonnull Path path, @Nonnull BlockSelection prefab, boolean overwrite) {
/*  85 */     File file = path.toFile();
/*  86 */     if (!file.exists() || overwrite) {
/*  87 */       file.getParentFile().mkdirs();
/*     */       try {
/*  89 */         BsonUtil.writeDocument(path, SelectionPrefabSerializer.serialize(prefab)).join();
/*  90 */       } catch (Throwable e) {
/*  91 */         throw new PrefabSaveException(PrefabSaveException.Type.ERROR, e);
/*     */       } 
/*     */     } else {
/*  94 */       throw new PrefabSaveException(PrefabSaveException.Type.ALREADY_EXISTS);
/*     */     } 
/*     */     
/*  97 */     this.PREFAB_CACHE.remove(path);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Path getWorldGenPrefabsPath() {
/* 102 */     return getWorldGenPrefabsPath("Default");
/*     */   }
/*     */   
/*     */   public Path getAssetRootPath() {
/* 106 */     return AssetUtil.getHytaleAssetsPath();
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Path getWorldGenPrefabsPath(@Nullable String name) {
/* 111 */     name = (name == null) ? "Default" : name;
/* 112 */     return Universe.getWorldGenPath().resolve(name).resolve("Prefabs");
/*     */   }
/*     */   
/*     */   public void saveServerPrefab(@Nonnull String key, @Nonnull BlockSelection prefab, boolean overwrite) {
/* 116 */     savePrefab(getServerPrefabsPath().resolve(key), prefab, overwrite);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Path getAssetPrefabsPath() {
/* 121 */     return AssetUtil.getHytaleAssetsPath().resolve("Server").resolve("Prefabs");
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Path getAssetPrefabsPathForPack(@Nonnull AssetPack pack) {
/* 126 */     return pack.getRoot().resolve("Server").resolve("Prefabs");
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public List<AssetPackPrefabPath> getAllAssetPrefabPaths() {
/* 131 */     ObjectArrayList<AssetPackPrefabPath> objectArrayList = new ObjectArrayList();
/* 132 */     for (AssetPack pack : AssetModule.get().getAssetPacks()) {
/* 133 */       Path prefabsPath = getAssetPrefabsPathForPack(pack);
/* 134 */       if (Files.isDirectory(prefabsPath, new java.nio.file.LinkOption[0])) {
/* 135 */         objectArrayList.add(new AssetPackPrefabPath(pack, prefabsPath));
/*     */       }
/*     */     } 
/* 138 */     return (List<AssetPackPrefabPath>)objectArrayList;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public BlockSelection getAssetPrefabFromAnyPack(@Nonnull String key) {
/* 143 */     for (AssetPack pack : AssetModule.get().getAssetPacks()) {
/* 144 */       Path prefabsPath = getAssetPrefabsPathForPack(pack);
/* 145 */       Path prefabPath = prefabsPath.resolve(key);
/* 146 */       if (Files.exists(prefabPath, new java.nio.file.LinkOption[0])) {
/* 147 */         return getPrefab(prefabPath);
/*     */       }
/*     */     } 
/* 150 */     return null;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Path findAssetPrefabPath(@Nonnull String key) {
/* 155 */     for (AssetPack pack : AssetModule.get().getAssetPacks()) {
/* 156 */       Path prefabsPath = getAssetPrefabsPathForPack(pack);
/* 157 */       Path prefabPath = prefabsPath.resolve(key);
/* 158 */       if (Files.exists(prefabPath, new java.nio.file.LinkOption[0])) {
/* 159 */         return prefabPath;
/*     */       }
/*     */     } 
/* 162 */     return null;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public AssetPack findAssetPackForPrefabPath(@Nonnull Path prefabPath) {
/* 167 */     Path normalizedPath = prefabPath.toAbsolutePath().normalize();
/* 168 */     for (AssetPack pack : AssetModule.get().getAssetPacks()) {
/* 169 */       Path prefabsPath = getAssetPrefabsPathForPack(pack).toAbsolutePath().normalize();
/* 170 */       if (normalizedPath.startsWith(prefabsPath)) {
/* 171 */         return pack;
/*     */       }
/*     */     } 
/* 174 */     return null;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public BlockSelection getAssetPrefab(@Nonnull String key) {
/* 179 */     return getPrefab(getAssetPrefabsPath().resolve(key));
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Map<Path, BlockSelection> getAssetPrefabDir(@Nonnull String key) {
/* 184 */     return getPrefabDir(getAssetPrefabsPath().resolve(key));
/*     */   }
/*     */   
/*     */   public void saveAssetPrefab(@Nonnull String key, @Nonnull BlockSelection prefab) {
/* 188 */     saveWorldGenPrefab(key, prefab, false);
/*     */   }
/*     */   
/*     */   public void saveAssetPrefab(@Nonnull String key, @Nonnull BlockSelection prefab, boolean overwrite) {
/* 192 */     savePrefab(getAssetPrefabsPath().resolve(key), prefab, overwrite);
/*     */   } public static final class AssetPackPrefabPath extends Record { @Nullable
/*     */     private final AssetPack pack; @Nonnull
/* 195 */     private final Path prefabsPath; public AssetPackPrefabPath(@Nullable AssetPack pack, @Nonnull Path prefabsPath) { this.pack = pack; this.prefabsPath = prefabsPath; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/server/core/prefab/PrefabStore$AssetPackPrefabPath;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #195	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 195 */       //   0	7	0	this	Lcom/hypixel/hytale/server/core/prefab/PrefabStore$AssetPackPrefabPath; } @Nullable public AssetPack pack() { return this.pack; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/server/core/prefab/PrefabStore$AssetPackPrefabPath;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #195	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/hypixel/hytale/server/core/prefab/PrefabStore$AssetPackPrefabPath; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/server/core/prefab/PrefabStore$AssetPackPrefabPath;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #195	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lcom/hypixel/hytale/server/core/prefab/PrefabStore$AssetPackPrefabPath;
/* 195 */       //   0	8	1	o	Ljava/lang/Object; } @Nonnull public Path prefabsPath() { return this.prefabsPath; }
/*     */      public boolean isBasePack() {
/* 197 */       return (this.pack != null && this.pack.equals(AssetModule.get().getBaseAssetPack()));
/*     */     }
/*     */     
/*     */     public boolean isFromAssetPack() {
/* 201 */       return (this.pack != null);
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public String getPackName() {
/* 206 */       return (this.pack != null) ? this.pack.getName() : "Server";
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public String getDisplayName() {
/* 211 */       if (this.pack == null) return "Server"; 
/* 212 */       if (isBasePack()) return "Assets"; 
/* 213 */       PluginManifest manifest = this.pack.getManifest();
/* 214 */       return (manifest != null) ? manifest.getName() : this.pack.getName();
/*     */     } }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public BlockSelection getWorldGenPrefab(@Nonnull String key) {
/* 220 */     return getWorldGenPrefab(getWorldGenPrefabsPath(), key);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public BlockSelection getWorldGenPrefab(@Nonnull Path prefabsPath, @Nonnull String key) {
/* 225 */     return getPrefab(prefabsPath.resolve(key));
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Map<Path, BlockSelection> getWorldGenPrefabDir(@Nonnull String key) {
/* 230 */     return getPrefabDir(getWorldGenPrefabsPath().resolve(key));
/*     */   }
/*     */   
/*     */   public void saveWorldGenPrefab(@Nonnull String key, @Nonnull BlockSelection prefab) {
/* 234 */     saveWorldGenPrefab(key, prefab, false);
/*     */   }
/*     */   
/*     */   public static PrefabStore get() {
/* 238 */     return INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\prefab\PrefabStore.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */