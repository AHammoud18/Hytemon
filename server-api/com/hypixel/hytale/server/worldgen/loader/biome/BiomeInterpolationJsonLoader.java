/*    */ package com.hypixel.hytale.server.worldgen.loader.biome;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.hypixel.hytale.procedurallib.condition.IIntCondition;
/*    */ import com.hypixel.hytale.procedurallib.json.JsonLoader;
/*    */ import com.hypixel.hytale.procedurallib.json.SeedString;
/*    */ import com.hypixel.hytale.server.worldgen.SeedStringResource;
/*    */ import com.hypixel.hytale.server.worldgen.biome.BiomeInterpolation;
/*    */ import com.hypixel.hytale.server.worldgen.loader.context.ZoneFileContext;
/*    */ import com.hypixel.hytale.server.worldgen.util.condition.HashSetIntCondition;
/*    */ import it.unimi.dsi.fastutil.ints.Int2IntMap;
/*    */ import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
/*    */ import it.unimi.dsi.fastutil.ints.IntIterator;
/*    */ import it.unimi.dsi.fastutil.ints.IntSet;
/*    */ import java.nio.file.Path;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class BiomeInterpolationJsonLoader
/*    */   extends JsonLoader<SeedStringResource, BiomeInterpolation>
/*    */ {
/*    */   protected final ZoneFileContext zoneFileContext;
/*    */   
/*    */   public BiomeInterpolationJsonLoader(SeedString<SeedStringResource> seed, Path dataFolder, JsonElement json, ZoneFileContext zoneFileContext) {
/* 26 */     super(seed, dataFolder, json);
/* 27 */     this.zoneFileContext = zoneFileContext;
/*    */   }
/*    */ 
/*    */   
/*    */   public BiomeInterpolation load() {
/* 32 */     int defaultRadius = loadDefaultRadius();
/* 33 */     Int2IntMap biomeRadii = loadBiomeRadii(defaultRadius);
/* 34 */     return BiomeInterpolation.create(defaultRadius, biomeRadii);
/*    */   }
/*    */   
/*    */   protected int loadDefaultRadius() {
/* 38 */     if (!has("DefaultRadius")) return 5;
/*    */     
/* 40 */     int radius = get("DefaultRadius").getAsInt();
/* 41 */     if (radius < 0 || radius > 5) throw new Error(String.format("Default biome interpolation radius %s lies outside the range 0-5", new Object[] { Integer.valueOf(radius) }));
/*    */     
/* 43 */     return radius;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   protected Int2IntMap loadBiomeRadii(int maxRadius) {
/* 48 */     if (!has("Biomes")) return BiomeInterpolation.EMPTY_MAP;
/*    */     
/* 50 */     JsonElement biomes = get("Biomes");
/* 51 */     if (!biomes.isJsonArray()) throw new Error("Invalid json-type for Biomes property. Must be an array!");
/*    */     
/* 53 */     Int2IntOpenHashMap biomeRadii = new Int2IntOpenHashMap();
/* 54 */     for (JsonElement entry : biomes.getAsJsonArray()) {
/* 55 */       loadBiomeEntry(entry, maxRadius, (Int2IntMap)biomeRadii);
/*    */     }
/*    */     
/* 58 */     return (Int2IntMap)biomeRadii;
/*    */   }
/*    */   
/*    */   protected void loadBiomeEntry(@Nonnull JsonElement entry, int defaultRadius, @Nonnull Int2IntMap biomeRadii) {
/* 62 */     if (!entry.isJsonObject()) throw new Error("Invalid json-type for biome entry. Must be an object!");
/*    */     
/* 64 */     int radius = loadBiomeRadius(entry.getAsJsonObject(), defaultRadius);
/* 65 */     if (radius == defaultRadius)
/*    */       return; 
/* 67 */     IIntCondition mask = loadBiomeMask(entry.getAsJsonObject());
/* 68 */     addBiomes(mask, radius, biomeRadii);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   protected IIntCondition loadBiomeMask(@Nonnull JsonObject entry) {
/* 73 */     if (!entry.has("Mask")) throw new Error(String.format("Missing property %s", new Object[] { "Mask" }));
/*    */     
/* 75 */     return (new BiomeMaskJsonLoader(this.seed, this.dataFolder, entry.get("Mask"), "InterpolationMask", this.zoneFileContext)).load();
/*    */   }
/*    */   
/*    */   protected static int loadBiomeRadius(@Nonnull JsonObject entry, int maxRadius) {
/* 79 */     if (!entry.has("Radius")) throw new Error(String.format("Missing property %s", new Object[] { "Radius" }));
/*    */     
/* 81 */     int radius = entry.get("Radius").getAsInt();
/* 82 */     if (radius < 0 || radius > maxRadius) throw new Error(String.format("Biome interpolation radius %s is outside the range 0-%s", new Object[] { Integer.valueOf(radius), Integer.valueOf(maxRadius) }));
/*    */     
/* 84 */     return radius;
/*    */   }
/*    */ 
/*    */   
/*    */   protected static void addBiomes(IIntCondition mask, int radius, @Nonnull Int2IntMap biomeRadii) {
/* 89 */     if (!(mask instanceof HashSetIntCondition))
/*    */       return; 
/* 91 */     int radius2 = radius * radius;
/* 92 */     IntSet biomes = ((HashSetIntCondition)mask).getSet();
/* 93 */     for (IntIterator<Integer> intIterator = biomes.iterator(); intIterator.hasNext(); ) { int biome = ((Integer)intIterator.next()).intValue();
/* 94 */       if (biomeRadii.containsKey(biome)) throw new Error("Duplicate biome detected in interpolation rules");
/*    */       
/* 96 */       biomeRadii.put(biome, radius2); }
/*    */   
/*    */   }
/*    */   
/*    */   public static interface Constants {
/*    */     public static final String KEY_DEFAULT_RADIUS = "DefaultRadius";
/*    */     public static final String KEY_RADIUS = "Radius";
/*    */     public static final String KEY_BIOMES = "Biomes";
/*    */     public static final String KEY_MASK = "Mask";
/*    */     public static final String SEED_OFFSET_MASK = "InterpolationMask";
/*    */     public static final String ERROR_MISSING_PROPERTY = "Missing property %s";
/*    */     public static final String ERROR_INVALID_BIOME_LIST = "Invalid json-type for Biomes property. Must be an array!";
/*    */     public static final String ERROR_INVALID_BIOME_ENTRY = "Invalid json-type for biome entry. Must be an object!";
/*    */     public static final String ERROR_DUPLICATE_BIOME = "Duplicate biome detected in interpolation rules";
/*    */     public static final String ERROR_BIOME_RADIUS = "Biome interpolation radius %s is outside the range 0-%s";
/*    */     public static final String ERROR_DEFAULT_RADIUS = "Default biome interpolation radius %s lies outside the range 0-5";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\biome\BiomeInterpolationJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */