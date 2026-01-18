/*    */ package com.hypixel.hytale.server.worldgen.loader.climate;
/*    */ 
/*    */ import com.google.gson.JsonArray;
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
/*    */   public static final String KEY_NAME = "Name";
/*    */   public static final String KEY_POINTS = "Points";
/*    */   public static final String KEY_CHILDREN = "Children";
/*    */   public static final String KEY_ISLAND = "Island";
/* 84 */   public static final JsonArray DEFAULT_POINTS = new JsonArray();
/* 85 */   public static final JsonArray DEFAULT_CHILDREN = new JsonArray();
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\climate\ClimateTypeJsonLoader$Constants.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */