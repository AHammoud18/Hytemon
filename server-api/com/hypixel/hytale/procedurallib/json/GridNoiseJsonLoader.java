/*    */ package com.hypixel.hytale.procedurallib.json;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.procedurallib.NoiseFunction;
/*    */ import com.hypixel.hytale.procedurallib.logic.GridNoise;
/*    */ import java.nio.file.Path;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GridNoiseJsonLoader<K extends SeedResource>
/*    */   extends JsonLoader<K, NoiseFunction>
/*    */ {
/*    */   public GridNoiseJsonLoader(@Nonnull SeedString<K> seed, Path dataFolder, JsonElement json) {
/* 18 */     super(seed.append(".GridNoise"), dataFolder, json);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public NoiseFunction load() {
/* 24 */     double defaultThickness = loadDefaultThickness();
/* 25 */     return (NoiseFunction)new GridNoise(
/* 26 */         loadThicknessX(defaultThickness), 
/* 27 */         loadThicknessY(defaultThickness), 
/* 28 */         loadThicknessZ(defaultThickness));
/*    */   }
/*    */ 
/*    */   
/*    */   protected double loadDefaultThickness() {
/* 33 */     if (!has("Thickness")) return Double.NaN; 
/* 34 */     return get("Thickness").getAsDouble();
/*    */   }
/*    */   
/*    */   protected double loadThicknessX(double defaultThickness) {
/* 38 */     return loadThickness("ThicknessX", defaultThickness);
/*    */   }
/*    */   
/*    */   protected double loadThicknessY(double defaultThickness) {
/* 42 */     return loadThickness("ThicknessY", defaultThickness);
/*    */   }
/*    */ 
/*    */   
/*    */   protected double loadThicknessZ(double defaultThickness) {
/* 47 */     if (Double.isNaN(defaultThickness)) {
/* 48 */       defaultThickness = 0.0D;
/*    */     }
/* 50 */     return loadThickness("ThicknessZ", defaultThickness);
/*    */   }
/*    */   
/*    */   protected double loadThickness(String key, double defaultThickness) {
/* 54 */     double value = defaultThickness;
/*    */     
/* 56 */     if (has(key)) {
/* 57 */       value = get(key).getAsDouble();
/*    */     }
/*    */     
/* 60 */     if (Double.isNaN(value)) {
/* 61 */       throw new Error(String.format("Could not find thickness '%s' and no default 'Thickness' value defined!", new Object[] { key }));
/*    */     }
/*    */     
/* 64 */     return value;
/*    */   }
/*    */   
/*    */   public static interface Constants {
/*    */     public static final double DEFAULT_NO_THICKNESS = NaND;
/*    */     public static final double DEFAULT_THICKNESS_Z = 0.0D;
/*    */     public static final String KEY_THICKNESS = "Thickness";
/*    */     public static final String KEY_THICKNESS_X = "ThicknessX";
/*    */     public static final String KEY_THICKNESS_Y = "ThicknessY";
/*    */     public static final String KEY_THICKNESS_Z = "ThicknessZ";
/*    */     public static final String ERROR_NO_THICKNESS = "Could not find thickness '%s' and no default 'Thickness' value defined!";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\json\GridNoiseJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */