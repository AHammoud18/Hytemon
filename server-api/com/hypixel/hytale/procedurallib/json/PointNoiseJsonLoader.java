/*    */ package com.hypixel.hytale.procedurallib.json;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.procedurallib.logic.PointNoise;
/*    */ import java.nio.file.Path;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ public class PointNoiseJsonLoader<K extends SeedResource>
/*    */   extends JsonLoader<K, PointNoise>
/*    */ {
/*    */   public PointNoiseJsonLoader(SeedString<K> seed, Path dataFolder, @Nullable JsonElement json) {
/* 13 */     super(seed, dataFolder, json);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public PointNoise load() {
/* 19 */     return new PointNoise(
/* 20 */         mustGetNumber("X", Constants.DEFAULT_COORD).doubleValue(), 
/* 21 */         mustGetNumber("Y", Constants.DEFAULT_COORD).doubleValue(), 
/* 22 */         mustGetNumber("Z", Constants.DEFAULT_COORD).doubleValue(), 
/* 23 */         mustGetNumber("InnerRadius", Constants.DEFAULT_RADIUS).doubleValue(), 
/* 24 */         mustGetNumber("OuterRadius", Constants.DEFAULT_RADIUS).doubleValue());
/*    */   }
/*    */ 
/*    */   
/*    */   public static interface Constants
/*    */   {
/*    */     public static final String KEY_X = "X";
/*    */     public static final String KEY_Y = "Y";
/*    */     public static final String KEY_Z = "Z";
/*    */     public static final String KEY_INNER_RADIUS = "InnerRadius";
/*    */     public static final String KEY_OUTER_RADIUS = "OuterRadius";
/* 35 */     public static final Double DEFAULT_COORD = Double.valueOf(0.0D);
/* 36 */     public static final Double DEFAULT_RADIUS = Double.valueOf(0.0D);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\json\PointNoiseJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */