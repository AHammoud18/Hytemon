/*    */ package com.hypixel.hytale.procedurallib.json;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.procedurallib.property.GradientNoiseProperty;
/*    */ import com.hypixel.hytale.procedurallib.property.NoiseProperty;
/*    */ import java.nio.file.Path;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class GradientNoisePropertyJsonLoader<K extends SeedResource>
/*    */   extends JsonLoader<K, GradientNoiseProperty>
/*    */ {
/*    */   protected final NoiseProperty noise;
/*    */   
/*    */   public GradientNoisePropertyJsonLoader(SeedString<K> seed, Path dataFolder, JsonElement json, NoiseProperty noise) {
/* 16 */     super(seed, dataFolder, json);
/* 17 */     this.noise = noise;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public GradientNoiseProperty load() {
/* 23 */     return new GradientNoiseProperty(this.noise, 
/*    */         
/* 25 */         loadMode(), 
/* 26 */         loadDistance(), 
/* 27 */         loadNormalization());
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   protected GradientNoiseProperty.GradientMode loadMode() {
/* 33 */     GradientNoiseProperty.GradientMode mode = Constants.DEFAULT_MODE;
/* 34 */     if (has("Mode")) {
/* 35 */       mode = GradientNoiseProperty.GradientMode.valueOf(get("Mode").getAsString());
/*    */     }
/* 37 */     return mode;
/*    */   }
/*    */   
/*    */   protected double loadDistance() {
/* 41 */     double distance = 5.0D;
/* 42 */     if (has("Distance")) {
/* 43 */       distance = get("Distance").getAsDouble();
/*    */     }
/* 45 */     return distance;
/*    */   }
/*    */   
/*    */   protected double loadNormalization() {
/* 49 */     double distance = 0.1D;
/* 50 */     if (has("Normalize")) {
/* 51 */       distance = get("Normalize").getAsDouble();
/*    */     }
/* 53 */     return distance;
/*    */   }
/*    */   
/*    */   public static interface Constants
/*    */   {
/*    */     public static final String KEY_MODE = "Mode";
/*    */     public static final String KEY_DISTANCE = "Distance";
/*    */     public static final String KEY_NORMALIZE = "Normalize";
/* 61 */     public static final GradientNoiseProperty.GradientMode DEFAULT_MODE = GradientNoiseProperty.GradientMode.MAGNITUDE;
/*    */     public static final double DEFAULT_DISTANCE = 5.0D;
/*    */     public static final double DEFAULT_NORMALIZATION = 0.1D;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\json\GradientNoisePropertyJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */