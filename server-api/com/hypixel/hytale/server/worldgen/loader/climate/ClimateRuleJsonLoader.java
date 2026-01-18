/*    */ package com.hypixel.hytale.server.worldgen.loader.climate;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.hypixel.hytale.procedurallib.json.JsonLoader;
/*    */ import com.hypixel.hytale.procedurallib.json.SeedResource;
/*    */ import com.hypixel.hytale.procedurallib.json.SeedString;
/*    */ import com.hypixel.hytale.server.worldgen.climate.ClimateSearch;
/*    */ import java.nio.file.Path;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ClimateRuleJsonLoader<K extends SeedResource>
/*    */   extends JsonLoader<K, ClimateSearch.Rule>
/*    */ {
/*    */   public ClimateRuleJsonLoader(SeedString<K> seed, Path dataFolder, @Nullable JsonElement json) {
/* 21 */     super(seed, dataFolder, json);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public ClimateSearch.Rule load() {
/* 27 */     return new ClimateSearch.Rule(
/* 28 */         loadRange("Continent"), 
/* 29 */         loadRange("Temperature"), 
/* 30 */         loadRange("Intensity"), 
/* 31 */         loadRange("Fade"));
/*    */   }
/*    */ 
/*    */   
/*    */   protected ClimateSearch.Range loadRange(String key) {
/* 36 */     if (has(key)) {
/* 37 */       JsonObject json = mustGetObject(key, null);
/* 38 */       double target = loadTarget(json);
/* 39 */       double radius = loadRadius(json);
/* 40 */       double weight = loadWeight(json);
/* 41 */       return new ClimateSearch.Range(target, radius, weight);
/*    */     } 
/* 43 */     return ClimateSearch.Range.DEFAULT;
/*    */   }
/*    */   
/*    */   protected static double loadTarget(@Nonnull JsonObject json) {
/* 47 */     return ((Double)mustGet("Target", json.get("Target"), null, Double.class, x$0 -> JsonLoader.isNumber(x$0), JsonElement::getAsDouble)).doubleValue();
/*    */   }
/*    */   
/*    */   protected static double loadRadius(@Nonnull JsonObject json) {
/* 51 */     return ((Double)mustGet("Radius", json.get("Radius"), null, Double.class, x$0 -> JsonLoader.isNumber(x$0), JsonElement::getAsDouble)).doubleValue();
/*    */   }
/*    */   
/*    */   protected static double loadWeight(@Nonnull JsonObject json) {
/* 55 */     return ((Double)mustGet("Weight", json.get("Weight"), null, Double.class, x$0 -> JsonLoader.isNumber(x$0), JsonElement::getAsDouble)).doubleValue();
/*    */   }
/*    */   
/*    */   protected static interface Constants {
/*    */     public static final String KEY_TARGET = "Target";
/*    */     public static final String KEY_RADIUS = "Radius";
/*    */     public static final String KEY_WEIGHT = "Weight";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\climate\ClimateRuleJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */