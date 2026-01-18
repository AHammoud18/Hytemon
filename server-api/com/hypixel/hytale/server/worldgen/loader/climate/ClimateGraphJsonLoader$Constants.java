/*    */ package com.hypixel.hytale.server.worldgen.loader.climate;
/*    */ 
/*    */ import com.google.gson.JsonArray;
/*    */ import com.hypixel.hytale.server.worldgen.climate.ClimateGraph;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface Constants
/*    */ {
/*    */   public static final String KEY_CLIMATE = "Climate";
/*    */   public static final String KEY_FADE_MODE = "FadeMode";
/*    */   public static final String KEY_FADE_RADIUS = "FadeRadius";
/*    */   public static final String KEY_FADE_DISTANCE = "FadeDistance";
/*    */   public static final String KEY_CLIMATES = "Climates";
/* 61 */   public static final JsonArray DEFAULT_CLIMATES = new JsonArray();
/* 62 */   public static final Double DEFAULT_FADE_RADIUS = Double.valueOf(50.0D);
/* 63 */   public static final Double DEFAULT_FADE_DISTANCE = Double.valueOf(100.0D);
/* 64 */   public static final String DEFAULT_FADE_MODE = ClimateGraph.FadeMode.CHILDREN.name();
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\climate\ClimateGraphJsonLoader$Constants.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */