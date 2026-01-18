/*    */ package com.hypixel.hytale.server.worldgen.loader.util;
/*    */ 
/*    */ import com.google.gson.JsonArray;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
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
/*    */ public class Vector3dJsonLoader
/*    */   extends JsonLoader<SeedStringResource, Vector3d>
/*    */ {
/*    */   public Vector3dJsonLoader(SeedString<SeedStringResource> seed, Path dataFolder, JsonElement json) {
/* 23 */     super(seed, dataFolder, json);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Vector3d load() {
/* 29 */     if (this.json == null || this.json.isJsonNull())
/* 30 */       return new Vector3d(); 
/* 31 */     if (this.json.isJsonArray()) {
/* 32 */       JsonArray array = this.json.getAsJsonArray();
/* 33 */       if (array.isEmpty())
/* 34 */         return new Vector3d(); 
/* 35 */       if (array.size() == 1) {
/* 36 */         double value = array.get(0).getAsDouble();
/* 37 */         return new Vector3d(value, value, value);
/*    */       } 
/* 39 */       double x = array.get(0).getAsDouble();
/* 40 */       double y = array.get(1).getAsDouble();
/* 41 */       double z = array.get(2).getAsDouble();
/* 42 */       return new Vector3d(x, y, z);
/*    */     } 
/* 44 */     if (this.json.isJsonObject()) {
/* 45 */       JsonObject object = this.json.getAsJsonObject();
/* 46 */       double x = object.get("X").getAsDouble();
/* 47 */       double y = object.get("Y").getAsDouble();
/* 48 */       double z = object.get("Z").getAsDouble();
/* 49 */       return new Vector3d(x, y, z);
/*    */     } 
/* 51 */     throw new Error("No valid definition for Vector3d found!");
/*    */   }
/*    */   
/*    */   public static interface Constants {
/*    */     public static final String KEY_X = "X";
/*    */     public static final String KEY_Y = "Y";
/*    */     public static final String KEY_Z = "Z";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loade\\util\Vector3dJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */