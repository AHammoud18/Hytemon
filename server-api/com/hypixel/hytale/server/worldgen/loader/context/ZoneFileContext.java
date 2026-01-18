/*     */ package com.hypixel.hytale.server.worldgen.loader.context;
/*     */ 
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import java.nio.file.Path;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ZoneFileContext
/*     */   extends FileContext<FileLoadingContext>
/*     */ {
/*  18 */   private final FileContext.Registry<BiomeFileContext> tileBiomes = new FileContext.Registry<>("TileBiome");
/*  19 */   private final FileContext.Registry<BiomeFileContext> customBiomes = new FileContext.Registry<>("CustomBiome");
/*     */   
/*     */   public ZoneFileContext(int id, String name, Path filepath, FileLoadingContext context) {
/*  22 */     super(id, name, filepath, context);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public FileContext.Registry<BiomeFileContext> getTileBiomes() {
/*  32 */     return this.tileBiomes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public FileContext.Registry<BiomeFileContext> getCustomBiomes() {
/*  42 */     return this.customBiomes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public FileContext.Registry<BiomeFileContext> getBiomes(@Nonnull BiomeFileContext.Type type) {
/*  53 */     switch (type) { default: throw new MatchException(null, null);case Tile: case Custom: break; }  return 
/*     */       
/*  55 */       getCustomBiomes();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static interface Constants
/*     */   {
/*     */     public static final String ZONE_PREFIX = "Zones.";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ZoneFileContext matchContext(@Nullable JsonElement json, String key) {
/*  70 */     if (json == null || !json.isJsonObject()) return this;
/*     */     
/*  72 */     JsonElement element = json.getAsJsonObject().get(key);
/*  73 */     if (element == null || !element.isJsonObject()) return this;
/*     */     
/*  75 */     JsonObject object = element.getAsJsonObject();
/*  76 */     if (!object.has("File")) return this;
/*     */     
/*  78 */     String filePath = object.get("File").getAsString();
/*  79 */     return matchContext(filePath);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ZoneFileContext matchContext(@Nonnull String filePath) {
/*  92 */     if (!filePath.startsWith("Zones.")) return this;
/*     */     
/*  94 */     int nameStart = "Zones.".length();
/*  95 */     int nameEnd = filePath.indexOf('.', nameStart);
/*  96 */     if (nameEnd < nameStart) return this; 
/*  97 */     if (filePath.regionMatches(nameStart, getName(), 0, nameEnd - nameStart)) return this;
/*     */     
/*  99 */     String zoneName = filePath.substring(nameStart, nameEnd);
/* 100 */     FileContext.Registry<ZoneFileContext> zoneRegistry = getParentContext().getZones();
/* 101 */     if (!zoneRegistry.contains(zoneName)) return this;
/*     */     
/* 103 */     return zoneRegistry.get(zoneName);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected BiomeFileContext createBiome(String name, Path path, BiomeFileContext.Type type) {
/* 108 */     return createBiome(getParentContext().nextBiomeId(), name, path, type);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected BiomeFileContext createBiome(int id, String name, Path path, BiomeFileContext.Type type) {
/* 113 */     return new BiomeFileContext(getParentContext().updateBiomeId(id), name, path, type, this);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\context\ZoneFileContext.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */