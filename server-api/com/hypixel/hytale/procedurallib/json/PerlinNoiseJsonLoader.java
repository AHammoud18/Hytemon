/*    */ package com.hypixel.hytale.procedurallib.json;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.procedurallib.NoiseFunction;
/*    */ import com.hypixel.hytale.procedurallib.logic.GeneralNoise;
/*    */ import com.hypixel.hytale.procedurallib.logic.PerlinNoise;
/*    */ import java.nio.file.Path;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PerlinNoiseJsonLoader<K extends SeedResource>
/*    */   extends JsonLoader<K, NoiseFunction>
/*    */ {
/*    */   public PerlinNoiseJsonLoader(@Nonnull SeedString<K> seed, Path dataFolder, JsonElement json) {
/* 20 */     super(seed.append(".PerlinNoise"), dataFolder, json);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public NoiseFunction load() {
/* 26 */     return (NoiseFunction)new PerlinNoise(
/* 27 */         loadInterpolationFunction());
/*    */   }
/*    */ 
/*    */   
/*    */   protected GeneralNoise.InterpolationFunction loadInterpolationFunction() {
/* 32 */     GeneralNoise.InterpolationMode interpolationMode = Constants.DEFAULT_INTERPOLATION_MODE;
/* 33 */     if (has("InterpolationMode")) {
/* 34 */       interpolationMode = GeneralNoise.InterpolationMode.valueOf(get("InterpolationMode").getAsString());
/*    */     }
/* 36 */     return interpolationMode.getFunction();
/*    */   }
/*    */   
/*    */   public static interface Constants {
/*    */     public static final String KEY_INTERPOLATION_MODE = "InterpolationMode";
/* 41 */     public static final GeneralNoise.InterpolationMode DEFAULT_INTERPOLATION_MODE = GeneralNoise.InterpolationMode.QUINTIC;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\json\PerlinNoiseJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */