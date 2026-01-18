/*    */ package com.hypixel.hytale.procedurallib.json;
/*    */ 
/*    */ import com.google.gson.JsonArray;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.hypixel.hytale.procedurallib.property.BlendNoiseProperty;
/*    */ import com.hypixel.hytale.procedurallib.property.NoiseProperty;
/*    */ import java.nio.file.Path;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class BlendNoisePropertyJsonLoader<K extends SeedResource>
/*    */   extends JsonLoader<K, BlendNoiseProperty>
/*    */ {
/*    */   public BlendNoisePropertyJsonLoader(SeedString<K> seed, Path dataFolder, @Nullable JsonElement json) {
/* 16 */     super(seed, dataFolder, json);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public BlendNoiseProperty load() {
/* 22 */     NoiseProperty alpha = loadAlpha();
/* 23 */     NoiseProperty[] noise = loadNoise();
/* 24 */     double[] thresholds = loadThresholds();
/* 25 */     validate(noise, thresholds);
/* 26 */     return new BlendNoiseProperty(alpha, noise, thresholds);
/*    */   }
/*    */   
/*    */   protected NoiseProperty loadAlpha() {
/* 30 */     return (new NoisePropertyJsonLoader<>(this.seed, this.dataFolder, (JsonElement)mustGetObject("Alpha", (JsonObject)null)))
/* 31 */       .load();
/*    */   }
/*    */   
/*    */   protected NoiseProperty[] loadNoise() {
/* 35 */     JsonArray noise = mustGetArray("Noise", Constants.EMPTY_ARRAY);
/* 36 */     NoiseProperty[] noises = new NoiseProperty[noise.size()];
/* 37 */     for (int i = 0; i < noise.size(); i++) {
/* 38 */       noises[i] = (new NoisePropertyJsonLoader<>(this.seed, this.dataFolder, noise.get(i)))
/* 39 */         .load();
/*    */     }
/* 41 */     return noises;
/*    */   }
/*    */   
/*    */   protected double[] loadThresholds() {
/* 45 */     JsonArray thresholds = mustGetArray("Thresholds", Constants.EMPTY_ARRAY);
/* 46 */     double[] values = new double[thresholds.size()];
/* 47 */     for (int i = 0; i < thresholds.size(); i++) {
/* 48 */       values[i] = ((Number)mustGet("$" + i, thresholds.get(i), (Object)null, Number.class, JsonLoader::isNumber, JsonElement::getAsNumber))
/* 49 */         .doubleValue();
/*    */     }
/* 51 */     return values;
/*    */   }
/*    */   
/*    */   protected static void validate(NoiseProperty[] noises, double[] thresholds) {
/* 55 */     if (noises.length != thresholds.length) {
/* 56 */       throw new IllegalStateException("Number of noises must match number of thresholds");
/*    */     }
/*    */     
/* 59 */     double previous = Double.NEGATIVE_INFINITY;
/* 60 */     for (int i = 0; i < thresholds.length; i++) {
/* 61 */       if (thresholds[i] <= previous) {
/* 62 */         throw new IllegalStateException("Thresholds must be in ascending order and cannot be equal");
/*    */       }
/* 64 */       previous = thresholds[i];
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static interface Constants
/*    */   {
/*    */     public static final String KEY_ALPHA = "Alpha";
/*    */     public static final String KEY_NOISE = "Noise";
/*    */     public static final String KEY_THRESHOLDS = "Thresholds";
/* 74 */     public static final JsonArray EMPTY_ARRAY = new JsonArray();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\json\BlendNoisePropertyJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */