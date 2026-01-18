/*    */ package com.hypixel.hytale.server.worldgen.loader.util;
/*    */ 
/*    */ import com.google.gson.JsonArray;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.hypixel.hytale.math.vector.Vector2d;
/*    */ import com.hypixel.hytale.procedurallib.json.JsonLoader;
/*    */ import com.hypixel.hytale.procedurallib.json.SeedString;
/*    */ import com.hypixel.hytale.server.worldgen.SeedStringResource;
/*    */ import java.nio.file.Path;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Vector2dJsonLoader
/*    */   extends JsonLoader<SeedStringResource, Vector2d>
/*    */ {
/*    */   public Vector2dJsonLoader(SeedString<SeedStringResource> seed, Path dataFolder, JsonElement json) {
/* 24 */     super(seed, dataFolder, json);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Vector2d load() {
/* 30 */     if (this.json == null || this.json.isJsonNull())
/* 31 */       return new Vector2d(); 
/* 32 */     if (this.json.isJsonArray()) {
/* 33 */       JsonArray array = this.json.getAsJsonArray();
/* 34 */       if (array.isEmpty())
/* 35 */         return new Vector2d(); 
/* 36 */       if (array.size() == 1) {
/* 37 */         double value = array.get(0).getAsDouble();
/* 38 */         return new Vector2d(value, value);
/*    */       } 
/* 40 */       double x = array.get(0).getAsDouble();
/* 41 */       double y = array.get(1).getAsDouble();
/* 42 */       return new Vector2d(x, y);
/*    */     } 
/* 44 */     if (this.json.isJsonObject()) {
/* 45 */       JsonObject object = this.json.getAsJsonObject();
/* 46 */       double x = object.get("X").getAsDouble();
/* 47 */       double y = object.get("Y").getAsDouble();
/* 48 */       return new Vector2d(x, y);
/*    */     } 
/* 50 */     return new Vector2d();
/*    */   }
/*    */   
/*    */   public static interface Constants {
/*    */     public static final String KEY_X = "X";
/*    */     public static final String KEY_Y = "Y";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loade\\util\Vector2dJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */