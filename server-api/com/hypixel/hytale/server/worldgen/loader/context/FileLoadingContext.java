/*    */ package com.hypixel.hytale.server.worldgen.loader.context;
/*    */ 
/*    */ import com.hypixel.hytale.server.worldgen.prefab.PrefabCategory;
/*    */ import java.nio.file.Path;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class FileLoadingContext
/*    */   extends FileContext<FileLoadingContext>
/*    */ {
/* 11 */   private final FileContext.Registry<ZoneFileContext> zones = new FileContext.Registry<>("Zone");
/* 12 */   private final FileContext.Registry<PrefabCategory> prefabCategories = new FileContext.Registry<>("Category");
/* 13 */   private int zoneIdCounter = -1;
/* 14 */   private int biomeIdCounter = -1;
/*    */   
/*    */   public FileLoadingContext(@Nonnull Path filepath) {
/* 17 */     super(-1, filepath.getFileName().toString(), filepath, null);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public FileLoadingContext getParentContext() {
/* 23 */     return this;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public FileContext.Registry<ZoneFileContext> getZones() {
/* 33 */     return this.zones;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public FileContext.Registry<PrefabCategory> getPrefabCategories() {
/* 38 */     return this.prefabCategories;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   protected ZoneFileContext createZone(String name, Path path) {
/* 43 */     return createZone(nextZoneId(), name, path);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   protected ZoneFileContext createZone(int id, String name, Path path) {
/* 48 */     return new ZoneFileContext(updateZoneId(id), name, path, this);
/*    */   }
/*    */   
/*    */   protected int nextZoneId() {
/* 52 */     return this.zoneIdCounter + 1;
/*    */   }
/*    */   
/*    */   protected int nextBiomeId() {
/* 56 */     return this.biomeIdCounter + 1;
/*    */   }
/*    */   
/*    */   protected int updateZoneId(int id) {
/* 60 */     validateId(id, this.zoneIdCounter, "Zone");
/* 61 */     this.zoneIdCounter = id;
/* 62 */     return id;
/*    */   }
/*    */   
/*    */   protected int updateBiomeId(int id) {
/* 66 */     validateId(id, this.biomeIdCounter, "Biome");
/* 67 */     this.biomeIdCounter = id;
/* 68 */     return id;
/*    */   }
/*    */ 
/*    */   
/*    */   protected static void validateId(int id, int currentId, String type) {
/* 73 */     if (id < 0 || id <= currentId)
/* 74 */       throw new Error(String.format("Invalid ID '%s' registered for type %s. Current ID counter: %s", new Object[] { Integer.valueOf(id), type, Integer.valueOf(currentId) })); 
/*    */   }
/*    */   
/*    */   public static interface Constants {
/*    */     public static final String ID_TYPE_ZONE = "Zone";
/*    */     public static final String ID_TYPE_BIOME = "Biome";
/*    */     public static final String ERROR_INVALID_ID = "Invalid ID '%s' registered for type %s. Current ID counter: %s";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\context\FileLoadingContext.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */