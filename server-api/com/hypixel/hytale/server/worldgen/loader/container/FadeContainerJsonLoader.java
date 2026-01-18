/*    */ package com.hypixel.hytale.server.worldgen.loader.container;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.procedurallib.json.JsonLoader;
/*    */ import com.hypixel.hytale.procedurallib.json.SeedString;
/*    */ import com.hypixel.hytale.server.worldgen.SeedStringResource;
/*    */ import com.hypixel.hytale.server.worldgen.container.FadeContainer;
/*    */ import java.nio.file.Path;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FadeContainerJsonLoader
/*    */   extends JsonLoader<SeedStringResource, FadeContainer>
/*    */ {
/*    */   public FadeContainerJsonLoader(@Nonnull SeedString<SeedStringResource> seed, Path dataFolder, JsonElement json) {
/* 21 */     super(seed.append(".FadeContainer"), dataFolder, json);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public FadeContainer load() {
/* 27 */     return new FadeContainer(
/* 28 */         loadFadeStart(), loadFadeLength(), 
/* 29 */         loadTerrainStart(), loadTerrainLength(), 
/* 30 */         loadFadeHeightmap());
/*    */   }
/*    */ 
/*    */   
/*    */   protected double loadFadeStart() {
/* 35 */     return has("FadeStart") ? get("FadeStart").getAsDouble() : 0.0D;
/*    */   }
/*    */   
/*    */   protected double loadFadeLength() {
/* 39 */     return has("FadeLength") ? get("FadeLength").getAsDouble() : 0.0D;
/*    */   }
/*    */   
/*    */   protected double loadTerrainStart() {
/* 43 */     return has("TerrainStart") ? get("TerrainStart").getAsDouble() : 0.0D;
/*    */   }
/*    */   
/*    */   protected double loadTerrainLength() {
/* 47 */     return has("TerrainLength") ? get("TerrainLength").getAsDouble() : 0.0D;
/*    */   }
/*    */   
/*    */   protected double loadFadeHeightmap() {
/* 51 */     double mod = Double.NEGATIVE_INFINITY;
/* 52 */     if (has("FadeHeightmap")) {
/* 53 */       mod = get("FadeHeightmap").getAsDouble();
/*    */     }
/* 55 */     return mod;
/*    */   }
/*    */   
/*    */   public static interface Constants {
/*    */     public static final String KEY_FADE_START = "FadeStart";
/*    */     public static final String KEY_FADE_LENGTH = "FadeLength";
/*    */     public static final String KEY_TERRAIN_START = "TerrainStart";
/*    */     public static final String KEY_TERRAIN_LENGTH = "TerrainLength";
/*    */     public static final String KEY_FADE_HEIGHTMAP = "FadeHeightmap";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\container\FadeContainerJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */