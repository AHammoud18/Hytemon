/*    */ package com.hypixel.hytale.procedurallib.json;
/*    */ 
/*    */ import com.google.gson.JsonArray;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.hypixel.hytale.procedurallib.condition.IHeightThresholdInterpreter;
/*    */ import com.hypixel.hytale.procedurallib.condition.NoiseHeightThresholdInterpreter;
/*    */ import com.hypixel.hytale.procedurallib.property.NoiseProperty;
/*    */ import java.nio.file.Path;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NoiseHeightThresholdInterpreterJsonLoader<K extends SeedResource>
/*    */   extends JsonLoader<K, NoiseHeightThresholdInterpreter>
/*    */ {
/*    */   protected final int length;
/*    */   
/*    */   public NoiseHeightThresholdInterpreterJsonLoader(@Nonnull SeedString<K> seed, Path dataFolder, JsonElement json, int length) {
/* 24 */     super(seed.append(".NoiseHeightThresholdInterpreter"), dataFolder, json);
/* 25 */     this.length = length;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public NoiseHeightThresholdInterpreter load() {
/* 31 */     IHeightThresholdInterpreter[] interpreters = loadInterpreters();
/* 32 */     float[] keys = loadKeys();
/* 33 */     if (keys.length != interpreters.length) throw new IllegalArgumentException("Keys and Thresholds array do not have the same length!"); 
/* 34 */     return new NoiseHeightThresholdInterpreter(
/* 35 */         loadNoise(), keys, interpreters);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   protected NoiseProperty loadNoise() {
/* 43 */     if (!has("Noise")) throw new IllegalStateException("Could not find noise map data in NoiseHeightThresholdInterpreter. Keyword: Noise"); 
/* 44 */     return (new NoisePropertyJsonLoader<>(this.seed, this.dataFolder, get("Noise")))
/* 45 */       .load();
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   protected IHeightThresholdInterpreter[] loadInterpreters() {
/* 50 */     if (!has("Thresholds")) throw new IllegalStateException("Could not find threshold data in NoiseHeightThresholdInterpreter. Keyword: Thresholds"); 
/* 51 */     JsonArray array = get("Thresholds").getAsJsonArray();
/* 52 */     IHeightThresholdInterpreter[] interpreters = new IHeightThresholdInterpreter[array.size()];
/* 53 */     for (int i = 0; i < interpreters.length; i++) {
/* 54 */       interpreters[i] = (new HeightThresholdInterpreterJsonLoader<>(this.seed.append("-" + i), this.dataFolder, array.get(i), this.length))
/* 55 */         .load();
/*    */     }
/* 57 */     return interpreters;
/*    */   }
/*    */   
/*    */   protected float[] loadKeys() {
/* 61 */     if (!has("Keys")) throw new IllegalStateException("Could not find key data in NoiseHeightThresholdInterpreter. Keyword: Keys"); 
/* 62 */     JsonArray array = get("Keys").getAsJsonArray();
/* 63 */     float[] keys = new float[array.size()];
/* 64 */     for (int i = 0; i < keys.length; i++) {
/* 65 */       keys[i] = array.get(i).getAsFloat();
/*    */     }
/* 67 */     return keys;
/*    */   }
/*    */   
/*    */   public static boolean shouldHandle(@Nonnull JsonObject jsonObject) {
/* 71 */     return jsonObject.has("Thresholds");
/*    */   }
/*    */   
/*    */   public static interface Constants {
/*    */     public static final String KEY_NOISE = "Noise";
/*    */     public static final String KEY_THRESHOLDS = "Thresholds";
/*    */     public static final String KEY_KEYS = "Keys";
/*    */     public static final String ERROR_NO_NOISE = "Could not find noise map data in NoiseHeightThresholdInterpreter. Keyword: Noise";
/*    */     public static final String ERROR_NO_THRESHOLDS = "Could not find threshold data in NoiseHeightThresholdInterpreter. Keyword: Thresholds";
/*    */     public static final String ERROR_NO_KEYS = "Could not find key data in NoiseHeightThresholdInterpreter. Keyword: Keys";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\json\NoiseHeightThresholdInterpreterJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */