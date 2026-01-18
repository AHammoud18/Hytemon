/*    */ package com.hypixel.hytale.server.worldgen.loader.climate;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.math.vector.Vector2i;
/*    */ import com.hypixel.hytale.procedurallib.json.JsonLoader;
/*    */ import com.hypixel.hytale.procedurallib.json.SeedResource;
/*    */ import com.hypixel.hytale.procedurallib.json.SeedString;
/*    */ import com.hypixel.hytale.server.worldgen.climate.ClimateSearch;
/*    */ import com.hypixel.hytale.server.worldgen.climate.UniqueClimateGenerator;
/*    */ import com.hypixel.hytale.server.worldgen.loader.util.ColorUtil;
/*    */ import java.nio.file.Path;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ public class UniqueClimateJsonLoader<K extends SeedResource>
/*    */   extends JsonLoader<K, UniqueClimateGenerator.Entry>
/*    */ {
/*    */   public UniqueClimateJsonLoader(SeedString<K> seed, Path dataFolder, @Nullable JsonElement json) {
/* 20 */     super(seed, dataFolder, json);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public UniqueClimateGenerator.Entry load() {
/* 26 */     return new UniqueClimateGenerator.Entry(
/* 27 */         loadName(), 
/* 28 */         loadParent(), 
/* 29 */         loadColor(), 
/* 30 */         loadRadius(), 
/* 31 */         loadOrigin(), 
/* 32 */         loadMinDistance(), 
/* 33 */         loadDistance(), 
/* 34 */         loadRule());
/*    */   }
/*    */ 
/*    */   
/*    */   protected String loadName() {
/* 39 */     return mustGetString("Name", null);
/*    */   }
/*    */   
/*    */   protected String loadParent() {
/* 43 */     return mustGetString("Parent", "");
/*    */   }
/*    */   
/*    */   protected int loadColor() {
/* 47 */     return ColorUtil.hexString(mustGetString("Color", null));
/*    */   }
/*    */   
/*    */   protected int loadRadius() {
/* 51 */     return mustGetNumber("Radius", Constants.DEFAULT_RADIUS).intValue();
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   protected Vector2i loadOrigin() {
/* 56 */     int x = mustGetNumber("OriginX", Constants.DEFAULT_OFFSET).intValue();
/* 57 */     int y = mustGetNumber("OriginY", Constants.DEFAULT_OFFSET).intValue();
/* 58 */     return new Vector2i(x, y);
/*    */   }
/*    */   
/*    */   protected int loadDistance() {
/* 62 */     return mustGetNumber("Distance", Constants.DEFAULT_SEARCH_RADIUS).intValue();
/*    */   }
/*    */   
/*    */   protected int loadMinDistance() {
/* 66 */     return mustGetNumber("MinDistance", Constants.DEFAULT_SEARCH_MIN_RADIUS).intValue();
/*    */   }
/*    */   
/*    */   protected ClimateSearch.Rule loadRule() {
/* 70 */     return (new ClimateRuleJsonLoader<>(this.seed, this.dataFolder, (JsonElement)mustGetObject("Rule", null)))
/* 71 */       .load();
/*    */   }
/*    */   
/*    */   protected static interface Constants
/*    */   {
/*    */     public static final String KEY_ZONE = "Name";
/*    */     public static final String KEY_PARENT = "Parent";
/*    */     public static final String KEY_COLOR = "Color";
/*    */     public static final String KEY_RADIUS = "Radius";
/*    */     public static final String KEY_ORIGIN_X = "OriginX";
/*    */     public static final String KEY_ORIGIN_Y = "OriginY";
/*    */     public static final String KEY_DISTANCE = "Distance";
/*    */     public static final String KEY_MIN_DISTANCE = "MinDistance";
/*    */     public static final String KEY_RULE = "Rule";
/* 85 */     public static final Integer DEFAULT_RADIUS = Integer.valueOf(8);
/* 86 */     public static final Integer DEFAULT_OFFSET = Integer.valueOf(8);
/* 87 */     public static final Integer DEFAULT_SEARCH_RADIUS = Integer.valueOf(5000);
/* 88 */     public static final Integer DEFAULT_SEARCH_MIN_RADIUS = Integer.valueOf(100);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\climate\UniqueClimateJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */