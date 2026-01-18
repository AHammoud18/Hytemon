/*    */ package com.hypixel.hytale.server.worldgen.loader;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.prefab.selection.buffer.PrefabLoader;
/*    */ import com.hypixel.hytale.server.worldgen.prefab.PrefabStoreRoot;
/*    */ import com.hypixel.hytale.server.worldgen.util.cache.TimeoutCache;
/*    */ import com.hypixel.hytale.sneakythrow.SneakyThrow;
/*    */ import java.io.IOException;
/*    */ import java.nio.file.Path;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.concurrent.TimeUnit;
/*    */ import java.util.function.Consumer;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ public class WorldGenPrefabLoader
/*    */ {
/*    */   public static final String PREFAB_FOLDER = "Prefabs";
/*    */   @Nonnull
/*    */   private final PrefabStoreRoot store;
/*    */   @Nonnull
/*    */   private final PrefabLoader prefabLoader;
/*    */   @Nonnull
/*    */   private final TimeoutCache<String, WorldGenPrefabSupplier[]> cache;
/*    */   
/*    */   public WorldGenPrefabLoader(@Nonnull PrefabStoreRoot store, @Nonnull Path dataFolder) {
/* 28 */     Path storePath = PrefabStoreRoot.resolvePrefabStore(store, dataFolder);
/* 29 */     this.store = store;
/* 30 */     this.prefabLoader = new PrefabLoader(storePath);
/* 31 */     this.cache = new TimeoutCache(30L, TimeUnit.SECONDS, SneakyThrow.sneakyFunction(key -> { List<WorldGenPrefabSupplier> suppliers = new ArrayList<>(); resolve(key, ()); return (WorldGenPrefabSupplier[])suppliers.toArray(()); }), null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public PrefabStoreRoot getStore() {
/* 40 */     return this.store;
/*    */   }
/*    */   
/*    */   public Path getRootFolder() {
/* 44 */     return this.prefabLoader.getRootFolder();
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public WorldGenPrefabSupplier[] get(String prefabName) {
/* 49 */     return (WorldGenPrefabSupplier[])this.cache.get(prefabName);
/*    */   }
/*    */   
/*    */   private void resolve(@Nonnull String prefabName, @Nonnull Consumer<Path> consumer) throws IOException {
/* 53 */     this.prefabLoader.resolvePrefabs(prefabName, consumer);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\WorldGenPrefabLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */