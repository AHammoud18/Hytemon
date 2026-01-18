/*    */ package com.hypixel.hytale.server.worldgen.loader.climate;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.procedurallib.json.JsonLoader;
/*    */ import com.hypixel.hytale.procedurallib.json.SeedResource;
/*    */ import com.hypixel.hytale.procedurallib.json.SeedString;
/*    */ import com.hypixel.hytale.server.worldgen.climate.ClimatePoint;
/*    */ import java.nio.file.Path;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ClimatePointJsonLoader<K extends SeedResource>
/*    */   extends JsonLoader<K, ClimatePoint>
/*    */ {
/*    */   public ClimatePointJsonLoader(SeedString<K> seed, Path dataFolder, @Nullable JsonElement json) {
/* 18 */     super(seed, dataFolder, json);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public ClimatePoint load() {
/* 24 */     return new ClimatePoint(
/* 25 */         loadTemperature(), 
/* 26 */         loadIntensity(), 
/* 27 */         loadModifier());
/*    */   }
/*    */ 
/*    */   
/*    */   protected double loadTemperature() {
/* 32 */     return mustGetNumber("Temperature", Constants.DEFAULT_TEMPERATURE).doubleValue();
/*    */   }
/*    */   
/*    */   protected double loadIntensity() {
/* 36 */     return mustGetNumber("Intensity", Constants.DEFAULT_INTENSITY).doubleValue();
/*    */   }
/*    */   
/*    */   protected double loadModifier() {
/* 40 */     return mustGetNumber("Modifier", Constants.DEFAULT_MODIFIER).doubleValue();
/*    */   }
/*    */   
/*    */   public static interface Constants
/*    */   {
/*    */     public static final String KEY_TEMPERATURE = "Temperature";
/*    */     public static final String KEY_INTENSITY = "Intensity";
/*    */     public static final String KEY_MODIFIER = "Modifier";
/* 48 */     public static final Double DEFAULT_TEMPERATURE = Double.valueOf(0.5D);
/* 49 */     public static final Double DEFAULT_INTENSITY = Double.valueOf(0.5D);
/* 50 */     public static final Double DEFAULT_MODIFIER = Double.valueOf(1.0D);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\climate\ClimatePointJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */