/*    */ package com.hypixel.hytale.procedurallib.json;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.procedurallib.NoiseFunction;
/*    */ import com.hypixel.hytale.procedurallib.logic.ConstantNoise;
/*    */ import java.nio.file.Path;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ConstantNoiseJsonLoader<K extends SeedResource>
/*    */   extends JsonLoader<K, NoiseFunction>
/*    */ {
/*    */   public ConstantNoiseJsonLoader(@Nonnull SeedString<K> seed, Path dataFolder, JsonElement json) {
/* 19 */     super(seed.append(".ConstantNoise"), dataFolder, json);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public NoiseFunction load() {
/* 25 */     return (NoiseFunction)new ConstantNoise(
/* 26 */         loadValue());
/*    */   }
/*    */ 
/*    */   
/*    */   protected double loadValue() {
/* 31 */     return has("Value") ? get("Value").getAsDouble() : 0.5D;
/*    */   }
/*    */   
/*    */   public static interface Constants {
/*    */     public static final String KEY_VALUE = "Value";
/*    */     public static final double DEFAULT_VALUE = 0.5D;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\json\ConstantNoiseJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */