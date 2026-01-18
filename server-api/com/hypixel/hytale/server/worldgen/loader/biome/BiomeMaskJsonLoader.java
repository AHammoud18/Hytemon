/*     */ package com.hypixel.hytale.server.worldgen.loader.biome;
/*     */ 
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.hypixel.hytale.procedurallib.condition.ConstantIntCondition;
/*     */ import com.hypixel.hytale.procedurallib.condition.IIntCondition;
/*     */ import com.hypixel.hytale.procedurallib.json.JsonLoader;
/*     */ import com.hypixel.hytale.procedurallib.json.SeedString;
/*     */ import com.hypixel.hytale.server.worldgen.SeedStringResource;
/*     */ import com.hypixel.hytale.server.worldgen.loader.context.BiomeFileContext;
/*     */ import com.hypixel.hytale.server.worldgen.loader.context.FileContext;
/*     */ import com.hypixel.hytale.server.worldgen.loader.context.FileLoadingContext;
/*     */ import com.hypixel.hytale.server.worldgen.loader.context.ZoneFileContext;
/*     */ import com.hypixel.hytale.server.worldgen.loader.util.FileMaskCache;
/*     */ import com.hypixel.hytale.server.worldgen.util.condition.IntConditionBuilder;
/*     */ import java.nio.file.Path;
/*     */ import java.util.Map;
/*     */ import java.util.function.Supplier;
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
/*     */ public class BiomeMaskJsonLoader
/*     */   extends JsonLoader<SeedStringResource, IIntCondition>
/*     */ {
/*     */   private final ZoneFileContext zoneContext;
/*     */   @Nullable
/*  44 */   private String fileName = null;
/*     */ 
/*     */   
/*     */   public BiomeMaskJsonLoader(@Nonnull SeedString<SeedStringResource> seed, Path dataFolder, JsonElement json, String maskName, ZoneFileContext zoneContext) {
/*  48 */     super(seed.append(".BiomeMask-" + maskName), dataFolder, json);
/*  49 */     this.zoneContext = zoneContext;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public IIntCondition load() {
/*  55 */     FileMaskCache<IIntCondition> biomeMaskRegistry = ((SeedStringResource)this.seed.get()).getBiomeMaskRegistry();
/*     */     
/*  57 */     if (this.fileName != null) {
/*  58 */       IIntCondition iIntCondition = (IIntCondition)biomeMaskRegistry.getIfPresentFileMask(this.fileName);
/*  59 */       if (iIntCondition != null) return iIntCondition;
/*     */     
/*     */     } 
/*  62 */     IIntCondition mask = loadMask();
/*     */     
/*  64 */     if (this.fileName != null) biomeMaskRegistry.putFileMask(this.fileName, mask);
/*     */     
/*  66 */     return mask;
/*     */   }
/*     */   protected IIntCondition loadMask() {
/*     */     IIntCondition iIntCondition;
/*  70 */     ConstantIntCondition constantIntCondition = ConstantIntCondition.DEFAULT_TRUE;
/*     */     
/*  72 */     if (this.json.isJsonArray()) {
/*  73 */       IntConditionBuilder builder = new IntConditionBuilder(it.unimi.dsi.fastutil.ints.IntOpenHashSet::new, -1);
/*  74 */       JsonArray array = this.json.getAsJsonArray();
/*  75 */       for (int i = 0; i < array.size(); i++) {
/*  76 */         JsonElement element = array.get(i);
/*  77 */         String rule = element.getAsString();
/*  78 */         parseRule(rule, builder);
/*     */       } 
/*  80 */       iIntCondition = builder.buildOrDefault((IIntCondition)ConstantIntCondition.DEFAULT_TRUE);
/*     */     } 
/*     */     
/*  83 */     return iIntCondition;
/*     */   }
/*     */   protected void parseRule(@Nonnull String rule, @Nonnull IntConditionBuilder builder) {
/*     */     boolean result;
/*  87 */     int zoneMarker = rule.indexOf('.');
/*  88 */     int typeMarker = rule.indexOf('#');
/*     */     
/*  90 */     ZoneFileContext zone = parseZone(rule, zoneMarker, this.zoneContext);
/*  91 */     String biomeName = parseBiomeName(rule, zoneMarker, typeMarker);
/*  92 */     BiomeFileContext.Type biomeType = parseBiomeType(rule, typeMarker + 1);
/*     */ 
/*     */     
/*  95 */     if (biomeType == null) {
/*  96 */       result = collectBiomes(zone.getTileBiomes(), biomeName, builder);
/*  97 */       result |= collectBiomes(zone.getCustomBiomes(), biomeName, builder);
/*     */     } else {
/*  99 */       result = collectBiomes(zone.getBiomes(biomeType), biomeName, builder);
/*     */     } 
/*     */     
/* 102 */     if (!result) {
/* 103 */       throw new Error(String.format("Failed to parse BiomeMask rule '%s'. Unable to find a %s called %s in %s", new Object[] { rule, getDisplayName(biomeType), biomeName, zone.getName() }));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected JsonElement loadFileConstructor(String filePath) {
/* 109 */     this.fileName = filePath;
/* 110 */     return ((SeedStringResource)this.seed.get()).getBiomeMaskRegistry().cachedFile(filePath, x$0 -> super.loadFileConstructor(x$0));
/*     */   }
/*     */   
/*     */   private static boolean collectBiomes(@Nonnull FileContext.Registry<BiomeFileContext> registry, @Nonnull String biomeName, @Nonnull IntConditionBuilder builder) {
/* 114 */     if (biomeName.equals("*")) {
/* 115 */       for (Map.Entry<String, BiomeFileContext> biomeEntry : registry) {
/* 116 */         builder.add(((BiomeFileContext)biomeEntry.getValue()).getId());
/*     */       }
/* 118 */       return true;
/*     */     } 
/* 120 */     if (registry.contains(biomeName)) {
/* 121 */       BiomeFileContext biome = (BiomeFileContext)registry.get(biomeName);
/* 122 */       builder.add(biome.getId());
/* 123 */       return true;
/*     */     } 
/* 125 */     return false;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   private static ZoneFileContext parseZone(@Nonnull String rule, int marker, @Nonnull ZoneFileContext context) {
/* 130 */     if (marker <= 0) return context; 
/* 131 */     String zoneName = rule.substring(0, marker);
/* 132 */     return (ZoneFileContext)((FileLoadingContext)context.getParentContext()).getZones().get(zoneName);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private static BiomeFileContext.Type parseBiomeType(@Nonnull String rule, int marker) {
/* 137 */     if (marker <= 0) return null; 
/* 138 */     String typeName = rule.substring(marker);
/* 139 */     return BiomeFileContext.Type.valueOf(typeName);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   private static String parseBiomeName(@Nonnull String rule, int zoneMarker, int typeMarker) {
/* 144 */     int nameStart = zoneMarker + 1;
/* 145 */     int nameEnd = (typeMarker > zoneMarker) ? typeMarker : rule.length();
/* 146 */     if (nameStart == nameEnd) return "*"; 
/* 147 */     return rule.substring(nameStart, nameEnd);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   private static String getDisplayName(@Nullable BiomeFileContext.Type type) {
/* 152 */     return (type == null) ? "Biome" : type.getDisplayName();
/*     */   }
/*     */   
/*     */   public static interface Constants {
/*     */     public static final char ZONE_MARKER = '.';
/*     */     public static final char TYPE_MARKER = '#';
/*     */     public static final int NULL_BIOME_ID = -1;
/*     */     public static final String WILDCARD_BIOME_NAME = "*";
/*     */     public static final String BIOME_TYPE_ANY_DISPLAY_NAME = "Biome";
/*     */     public static final String ERROR_PARSE_RULE = "Failed to parse BiomeMask rule '%s'. Unable to find a %s called %s in %s";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\biome\BiomeMaskJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */