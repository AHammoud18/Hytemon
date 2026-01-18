/*     */ package com.hypixel.hytale.server.worldgen.loader.context;
/*     */ 
/*     */ import com.google.gson.JsonParser;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.server.worldgen.prefab.PrefabCategory;
/*     */ import com.hypixel.hytale.server.worldgen.util.LogUtil;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.attribute.BasicFileAttributes;
/*     */ import java.util.Comparator;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.function.BiPredicate;
/*     */ import java.util.logging.Level;
/*     */ import java.util.stream.Stream;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ public class FileContextLoader
/*     */ {
/*  23 */   private static final Comparator<Path> ZONES_ORDER = Comparator.comparing(Path::getFileName);
/*  24 */   private static final Comparator<Path> BIOME_ORDER = Comparator.comparing(BiomeFileContext::getBiomeType).thenComparing(Path::getFileName); private static final BiPredicate<Path, BasicFileAttributes> ZONE_FILTER; private static final BiPredicate<Path, BasicFileAttributes> BIOME_FILTER; private final Path dataFolder; private final Set<String> zoneRequirement; static {
/*  25 */     ZONE_FILTER = ((path, attributes) -> Files.isDirectory(path, new java.nio.file.LinkOption[0]));
/*  26 */     BIOME_FILTER = ((path, attributes) -> isValidBiomeFile(path));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public FileContextLoader(Path dataFolder, Set<String> zoneRequirement) {
/*  32 */     this.dataFolder = dataFolder;
/*  33 */     this.zoneRequirement = zoneRequirement;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public FileLoadingContext load() {
/*  38 */     FileLoadingContext context = new FileLoadingContext(this.dataFolder);
/*  39 */     Path zonesFolder = this.dataFolder.resolve("Zones");
/*     */     
/*  41 */     try { Stream<Path> stream = Files.find(zonesFolder, 1, ZONE_FILTER, new java.nio.file.FileVisitOption[0]); 
/*  42 */       try { stream.sorted(ZONES_ORDER).forEach(path -> {
/*     */               String zoneName = path.getFileName().toString();
/*     */               
/*     */               if (zoneName.startsWith("!")) {
/*     */                 LogUtil.getLogger().at(Level.INFO).log("Zone \"%s\" is disabled. Remove \"!\" from folder name to enable it.", zoneName);
/*     */                 
/*     */                 return;
/*     */               } 
/*     */               
/*     */               if (!this.zoneRequirement.contains(zoneName)) {
/*     */                 return;
/*     */               }
/*     */               ZoneFileContext zone = loadZoneContext(zoneName, path, context);
/*     */               context.getZones().register(zoneName, zone);
/*     */             });
/*  57 */         if (stream != null) stream.close();  } catch (Throwable throwable) { if (stream != null) try { stream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException e)
/*  58 */     { ((HytaleLogger.Api)HytaleLogger.getLogger().at(Level.SEVERE).withCause(e)).log("Failed to load zones"); }
/*     */ 
/*     */     
/*     */     try {
/*  62 */       validateZones(context, this.zoneRequirement);
/*  63 */     } catch (Error e) {
/*  64 */       throw new Error("Failed to validate zones!", e);
/*     */     } 
/*     */     
/*  67 */     loadPrefabCategories(this.dataFolder, context);
/*     */     
/*  69 */     return context;
/*     */   }
/*     */   
/*     */   protected static void loadPrefabCategories(@Nonnull Path folder, @Nonnull FileLoadingContext context) {
/*  73 */     Path path = folder.resolve("PrefabCategories.json");
/*  74 */     if (!Files.exists(path, new java.nio.file.LinkOption[0])) {
/*     */       return;
/*     */     }
/*     */     
/*  78 */     try { BufferedReader reader = Files.newBufferedReader(path); 
/*  79 */       try { Objects.requireNonNull(context.getPrefabCategories()); PrefabCategory.parse(JsonParser.parseReader(reader), context.getPrefabCategories()::register);
/*  80 */         if (reader != null) reader.close();  } catch (Throwable throwable) { if (reader != null) try { reader.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException e)
/*  81 */     { throw new Error("Failed to open Categories.json", e); }
/*     */   
/*     */   }
/*     */   @Nonnull
/*     */   protected static ZoneFileContext loadZoneContext(String name, @Nonnull Path folder, @Nonnull FileLoadingContext context) {
/*     */     
/*  87 */     try { Stream<Path> stream = Files.find(folder, 1, BIOME_FILTER, new java.nio.file.FileVisitOption[0]); 
/*  88 */       try { ZoneFileContext zone = context.createZone(name, folder);
/*  89 */         stream.sorted(BIOME_ORDER).forEach(path -> {
/*     */               BiomeFileContext.Type type = BiomeFileContext.getBiomeType(path);
/*     */               String biomeName = parseName(path, type);
/*     */               BiomeFileContext biome = zone.createBiome(biomeName, path, type);
/*     */               zone.getBiomes(type).register(biomeName, biome);
/*     */             });
/*  95 */         ZoneFileContext zoneFileContext1 = zone;
/*  96 */         if (stream != null) stream.close();  return zoneFileContext1; } catch (Throwable throwable) { if (stream != null) try { stream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException e)
/*  97 */     { throw new Error(String.format("Failed to list files in: %s", new Object[] { folder }), e); }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   protected static int compareBiomePaths(@Nonnull Path a, @Nonnull Path b) {
/* 103 */     BiomeFileContext.Type typeA = BiomeFileContext.getBiomeType(a);
/* 104 */     BiomeFileContext.Type typeB = BiomeFileContext.getBiomeType(b);
/* 105 */     int result = typeA.compareTo(typeB);
/* 106 */     if (result != 0) return result; 
/* 107 */     return a.getFileName().compareTo(b.getFileName());
/*     */   }
/*     */   
/*     */   protected static boolean isValidBiomeFile(@Nonnull Path path) {
/* 111 */     if (Files.isDirectory(path, new java.nio.file.LinkOption[0])) return false; 
/* 112 */     String filename = path.getFileName().toString();
/* 113 */     for (BiomeFileContext.Type type : BiomeFileContext.Type.values()) {
/* 114 */       if (filename.endsWith(type.getSuffix()) && filename.startsWith(type.getPrefix())) {
/* 115 */         return true;
/*     */       }
/*     */     } 
/* 118 */     return false;
/*     */   }
/*     */   
/*     */   protected static void validateZones(@Nonnull FileLoadingContext context, @Nonnull Set<String> zoneRequirement) throws Error {
/* 122 */     for (String key : zoneRequirement)
/*     */     {
/* 124 */       context.getZones().get(key);
/*     */     }
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   private static String parseName(@Nonnull Path path, @Nonnull BiomeFileContext.Type type) {
/* 130 */     String filename = path.getFileName().toString();
/* 131 */     int start = type.getPrefix().length();
/* 132 */     int end = filename.length() - type.getSuffix().length();
/* 133 */     return filename.substring(start, end);
/*     */   }
/*     */   
/*     */   public static interface Constants {
/*     */     public static final int ZONE_SEARCH_DEPTH = 1;
/*     */     public static final int BIOME_SEARCH_DEPTH = 1;
/*     */     public static final String IDENTIFIER_DISABLE_ZONE = "!";
/*     */     public static final String INFO_ZONE_IS_DISABLED = "Zone \"%s\" is disabled. Remove \"!\" from folder name to enable it.";
/*     */     public static final String ERROR_LIST_FILES = "Failed to list files in: %s";
/*     */     public static final String ERROR_ZONE_VALIDATION = "Failed to validate zones!";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\context\FileContextLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */