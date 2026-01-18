/*    */ package com.hypixel.hytale.procedurallib.json;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.procedurallib.logic.GeneralNoise;
/*    */ import com.hypixel.hytale.procedurallib.logic.ValueNoise;
/*    */ import java.nio.file.Path;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ValueNoiseJsonLoader<K extends SeedResource>
/*    */   extends JsonLoader<K, ValueNoise>
/*    */ {
/*    */   public ValueNoiseJsonLoader(@Nonnull SeedString<K> seed, Path dataFolder, JsonElement json) {
/* 19 */     super(seed.append(".ValueNoise"), dataFolder, json);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public ValueNoise load() {
/* 25 */     return new ValueNoise(
/* 26 */         loadInterpolationFunction());
/*    */   }
/*    */ 
/*    */   
/*    */   protected GeneralNoise.InterpolationFunction loadInterpolationFunction() {
/* 31 */     GeneralNoise.InterpolationMode interpolationMode = Constants.DEFAULT_INTERPOLATION_MODE;
/* 32 */     if (has("InterpolationMode")) {
/* 33 */       interpolationMode = GeneralNoise.InterpolationMode.valueOf(get("InterpolationMode").getAsString());
/*    */     }
/* 35 */     return interpolationMode.getFunction();
/*    */   }
/*    */   
/*    */   public static interface Constants {
/*    */     public static final String KEY_INTERPOLATION_MODE = "InterpolationMode";
/* 40 */     public static final GeneralNoise.InterpolationMode DEFAULT_INTERPOLATION_MODE = GeneralNoise.InterpolationMode.QUINTIC;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\json\ValueNoiseJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */