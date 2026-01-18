/*    */ package com.hypixel.hytale.server.worldgen.loader.climate;
/*    */ 
/*    */ import com.google.gson.JsonArray;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.hypixel.hytale.procedurallib.json.JsonLoader;
/*    */ import com.hypixel.hytale.procedurallib.json.SeedResource;
/*    */ import com.hypixel.hytale.procedurallib.json.SeedString;
/*    */ import com.hypixel.hytale.server.worldgen.climate.ClimateColor;
/*    */ import com.hypixel.hytale.server.worldgen.climate.ClimatePoint;
/*    */ import com.hypixel.hytale.server.worldgen.climate.ClimateType;
/*    */ import java.nio.file.Path;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class ClimateTypeJsonLoader<K extends SeedResource>
/*    */   extends JsonLoader<K, ClimateType>
/*    */ {
/*    */   @Nullable
/*    */   private final ClimateType parent;
/*    */   
/*    */   public ClimateTypeJsonLoader(SeedString<K> seed, Path dataFolder, JsonElement json, @Nullable ClimateType parent) {
/* 23 */     super(seed, dataFolder, json);
/* 24 */     this.parent = parent;
/*    */   }
/*    */ 
/*    */   
/*    */   public ClimateType load() {
/* 29 */     String name = loadName();
/* 30 */     ClimateColor color = (new ClimateColorJsonLoader<>(this.seed, this.dataFolder, this.json, (this.parent != null) ? this.parent.color : null)).load();
/* 31 */     ClimateColor island = loadIslandColor(color);
/* 32 */     ClimatePoint[] points = loadClimatePoints();
/* 33 */     ClimateType[] children = loadChildren(new ClimateType(name, color, island, points, ClimateType.EMPTY_ARRAY));
/* 34 */     return new ClimateType(name, color, island, points, children);
/*    */   }
/*    */   @Nonnull
/*    */   protected String loadName() {
/* 38 */     return mustGetString("Name", null);
/*    */   }
/*    */   @Nonnull
/*    */   protected ClimateColor loadIslandColor(@Nonnull ClimateColor color) {
/* 42 */     if (has("Island")) {
/* 43 */       return (new ClimateColorJsonLoader<>(this.seed, this.dataFolder, get("Island"), (this.parent != null) ? this.parent.island : null))
/* 44 */         .load();
/*    */     }
/* 46 */     return color;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   protected ClimatePoint[] loadClimatePoints() {
/* 51 */     JsonArray pointsArr = mustGetArray("Points", Constants.DEFAULT_POINTS);
/* 52 */     ClimatePoint[] points = new ClimatePoint[pointsArr.size()];
/* 53 */     for (int i = 0; i < pointsArr.size(); i++) {
/* 54 */       points[i] = loadPoint(i, pointsArr.get(i));
/*    */     }
/* 56 */     return points;
/*    */   }
/*    */   @Nonnull
/*    */   protected ClimateType[] loadChildren(ClimateType parent) {
/* 60 */     JsonArray childrenArr = mustGetArray("Children", Constants.DEFAULT_CHILDREN);
/* 61 */     ClimateType[] children = new ClimateType[childrenArr.size()];
/* 62 */     for (int i = 0; i < childrenArr.size(); i++) {
/* 63 */       JsonObject childJson = childrenArr.get(i).getAsJsonObject();
/* 64 */       children[i] = (new ClimateTypeJsonLoader(this.seed, this.dataFolder, (JsonElement)childJson, parent)).load();
/*    */     } 
/* 66 */     return children;
/*    */   }
/*    */   @Nonnull
/*    */   protected ClimatePoint loadPoint(int index, JsonElement pointsJson) {
/*    */     try {
/* 71 */       return (new ClimatePointJsonLoader<>(this.seed, this.dataFolder, pointsJson))
/* 72 */         .load();
/* 73 */     } catch (Throwable t) {
/* 74 */       throw error(t, "Invalid climate point at index: %d", new Object[] { Integer.valueOf(index) });
/*    */     } 
/*    */   }
/*    */   
/*    */   public static interface Constants
/*    */   {
/*    */     public static final String KEY_NAME = "Name";
/*    */     public static final String KEY_POINTS = "Points";
/*    */     public static final String KEY_CHILDREN = "Children";
/*    */     public static final String KEY_ISLAND = "Island";
/* 84 */     public static final JsonArray DEFAULT_POINTS = new JsonArray();
/* 85 */     public static final JsonArray DEFAULT_CHILDREN = new JsonArray();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\climate\ClimateTypeJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */