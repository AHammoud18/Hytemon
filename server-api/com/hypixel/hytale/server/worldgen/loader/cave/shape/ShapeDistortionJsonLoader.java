/*    */ package com.hypixel.hytale.server.worldgen.loader.cave.shape;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.procedurallib.json.JsonLoader;
/*    */ import com.hypixel.hytale.procedurallib.json.NoisePropertyJsonLoader;
/*    */ import com.hypixel.hytale.procedurallib.json.SeedResource;
/*    */ import com.hypixel.hytale.procedurallib.json.SeedString;
/*    */ import com.hypixel.hytale.procedurallib.property.NoiseProperty;
/*    */ import com.hypixel.hytale.server.worldgen.cave.shape.distorted.ShapeDistortion;
/*    */ import java.nio.file.Path;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class ShapeDistortionJsonLoader<K extends SeedResource>
/*    */   extends JsonLoader<K, ShapeDistortion> {
/*    */   public ShapeDistortionJsonLoader(@Nonnull SeedString<K> seed, Path dataFolder, JsonElement json) {
/* 17 */     super(seed.append(".ShapeDistortion"), dataFolder, json);
/*    */   }
/*    */ 
/*    */   
/*    */   public ShapeDistortion load() {
/* 22 */     return ShapeDistortion.of(
/* 23 */         loadWidth(), 
/* 24 */         loadFloor(), 
/* 25 */         loadCeiling());
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   private NoiseProperty loadWidth() {
/* 31 */     if (has("Width")) {
/* 32 */       return (new NoisePropertyJsonLoader(this.seed, this.dataFolder, get("Width"))).load();
/*    */     }
/* 34 */     return null;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   private NoiseProperty loadFloor() {
/* 39 */     if (has("Floor")) {
/* 40 */       return (new NoisePropertyJsonLoader(this.seed, this.dataFolder, get("Floor"))).load();
/*    */     }
/* 42 */     return null;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   private NoiseProperty loadCeiling() {
/* 47 */     if (has("Ceiling")) {
/* 48 */       return (new NoisePropertyJsonLoader(this.seed, this.dataFolder, get("Ceiling"))).load();
/*    */     }
/* 50 */     return null;
/*    */   }
/*    */   
/*    */   public static interface Constants {
/*    */     public static final String KEY_WIDTH = "Width";
/*    */     public static final String KEY_FLOOR = "Floor";
/*    */     public static final String KEY_CEILING = "Ceiling";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\cave\shape\ShapeDistortionJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */