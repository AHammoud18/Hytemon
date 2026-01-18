/*    */ package com.hypixel.hytale.server.worldgen.loader.climate;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.procedurallib.json.JsonLoader;
/*    */ import com.hypixel.hytale.procedurallib.json.SeedResource;
/*    */ import com.hypixel.hytale.procedurallib.json.SeedString;
/*    */ import com.hypixel.hytale.server.worldgen.climate.ClimateColor;
/*    */ import com.hypixel.hytale.server.worldgen.loader.util.ColorUtil;
/*    */ import java.nio.file.Path;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class ClimateColorJsonLoader<K extends SeedResource>
/*    */   extends JsonLoader<K, ClimateColor>
/*    */ {
/*    */   @Nullable
/*    */   private final ClimateColor parent;
/*    */   
/*    */   public ClimateColorJsonLoader(SeedString<K> seed, Path dataFolder, @Nullable JsonElement json, @Nullable ClimateColor parent) {
/* 20 */     super(seed, dataFolder, json);
/* 21 */     this.parent = parent;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public ClimateColor load() {
/* 27 */     return new ClimateColor(
/* 28 */         loadColor("Color", -1), 
/* 29 */         loadColor("Shore", (this.parent != null) ? this.parent.shore : -1), 
/* 30 */         loadColor("Ocean", (this.parent != null) ? this.parent.ocean : -1), 
/* 31 */         loadColor("ShallowOcean", (this.parent != null) ? this.parent.shallowOcean : -1));
/*    */   }
/*    */ 
/*    */   
/*    */   protected int loadColor(@Nonnull String key, int defaultColor) {
/* 36 */     if (has(key)) {
/* 37 */       String color = mustGetString(key, null);
/* 38 */       return ColorUtil.hexString(color);
/*    */     } 
/* 40 */     return defaultColor;
/*    */   }
/*    */   
/*    */   public static interface Constants {
/*    */     public static final String KEY_COLOR = "Color";
/*    */     public static final String KEY_SHORE = "Shore";
/*    */     public static final String KEY_OCEAN = "Ocean";
/*    */     public static final String KEY_SHALLOW_OCEAN = "ShallowOcean";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\climate\ClimateColorJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */