/*    */ package com.hypixel.hytale.server.worldgen.loader.climate;
/*    */ 
/*    */ import com.google.gson.JsonArray;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.procedurallib.json.JsonLoader;
/*    */ import com.hypixel.hytale.procedurallib.json.SeedResource;
/*    */ import com.hypixel.hytale.procedurallib.json.SeedString;
/*    */ import com.hypixel.hytale.server.worldgen.climate.ClimateGraph;
/*    */ import com.hypixel.hytale.server.worldgen.climate.ClimateType;
/*    */ import java.nio.file.Path;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class ClimateGraphJsonLoader<K extends SeedResource>
/*    */   extends JsonLoader<K, ClimateGraph>
/*    */ {
/*    */   public ClimateGraphJsonLoader(SeedString<K> seed, Path dataFolder, JsonElement json) {
/* 18 */     super(seed, dataFolder, json);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public ClimateGraph load() {
/* 24 */     ClimateType[] climates = loadClimates();
/* 25 */     ClimateGraph.FadeMode fadeMode = loadFadeMode();
/* 26 */     double fadeRadius = loadFadeRadius();
/* 27 */     double fadeDistance = loadFadeDistance();
/* 28 */     return new ClimateGraph(512, climates, fadeMode, fadeRadius, fadeDistance);
/*    */   }
/*    */   
/*    */   protected ClimateGraph.FadeMode loadFadeMode() {
/* 32 */     String fadeMode = mustGetString("FadeMode", Constants.DEFAULT_FADE_MODE);
/* 33 */     return ClimateGraph.FadeMode.valueOf(fadeMode);
/*    */   }
/*    */   
/*    */   protected double loadFadeRadius() {
/* 37 */     return mustGetNumber("FadeRadius", Constants.DEFAULT_FADE_RADIUS).doubleValue();
/*    */   }
/*    */   
/*    */   protected double loadFadeDistance() {
/* 41 */     return mustGetNumber("FadeDistance", Constants.DEFAULT_FADE_DISTANCE).doubleValue();
/*    */   }
/*    */   @Nonnull
/*    */   protected ClimateType[] loadClimates() {
/* 45 */     JsonArray climatesArr = mustGetArray("Climates", Constants.DEFAULT_CLIMATES);
/* 46 */     ClimateType[] climates = new ClimateType[climatesArr.size()];
/* 47 */     for (int i = 0; i < climatesArr.size(); i++) {
/* 48 */       JsonElement climateJson = climatesArr.get(i);
/* 49 */       climates[i] = (new ClimateTypeJsonLoader<>(this.seed, this.dataFolder, climateJson, null)).load();
/*    */     } 
/* 51 */     return climates;
/*    */   }
/*    */   
/*    */   public static interface Constants
/*    */   {
/*    */     public static final String KEY_CLIMATE = "Climate";
/*    */     public static final String KEY_FADE_MODE = "FadeMode";
/*    */     public static final String KEY_FADE_RADIUS = "FadeRadius";
/*    */     public static final String KEY_FADE_DISTANCE = "FadeDistance";
/*    */     public static final String KEY_CLIMATES = "Climates";
/* 61 */     public static final JsonArray DEFAULT_CLIMATES = new JsonArray();
/* 62 */     public static final Double DEFAULT_FADE_RADIUS = Double.valueOf(50.0D);
/* 63 */     public static final Double DEFAULT_FADE_DISTANCE = Double.valueOf(100.0D);
/* 64 */     public static final String DEFAULT_FADE_MODE = ClimateGraph.FadeMode.CHILDREN.name();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\climate\ClimateGraphJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */