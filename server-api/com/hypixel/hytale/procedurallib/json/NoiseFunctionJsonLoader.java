/*    */ package com.hypixel.hytale.procedurallib.json;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.procedurallib.NoiseFunction;
/*    */ import java.nio.file.Path;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NoiseFunctionJsonLoader<K extends SeedResource>
/*    */   extends JsonLoader<K, NoiseFunction>
/*    */ {
/*    */   public NoiseFunctionJsonLoader(@Nonnull SeedString<K> seed, Path dataFolder, JsonElement json) {
/* 18 */     super(seed.append(".NoiseFunction"), dataFolder, json);
/*    */   }
/*    */ 
/*    */   
/*    */   public NoiseFunction load() {
/* 23 */     if (!has("NoiseType")) throw new IllegalStateException(String.format("Could not find noise type for noise map! Keyword: %s", new Object[] { "NoiseType" })); 
/* 24 */     NoiseTypeJson noiseTypeJson = NoiseTypeJson.valueOf(get("NoiseType").getAsString());
/* 25 */     return newLoader(noiseTypeJson).load();
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   protected JsonLoader<K, NoiseFunction> newLoader(@Nonnull NoiseTypeJson noiseTypeJson) {
/* 30 */     return noiseTypeJson.newLoader(this.seed, this.dataFolder, this.json);
/*    */   }
/*    */   
/*    */   public static interface Constants {
/*    */     public static final String KEY_NOISE_TYPE = "NoiseType";
/*    */     public static final String ERROR_NO_NOISE_TYPE = "Could not find noise type for noise map! Keyword: %s";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\json\NoiseFunctionJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */