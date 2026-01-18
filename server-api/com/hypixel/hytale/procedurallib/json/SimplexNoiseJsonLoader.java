/*    */ package com.hypixel.hytale.procedurallib.json;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.procedurallib.NoiseFunction;
/*    */ import com.hypixel.hytale.procedurallib.logic.SimplexNoise;
/*    */ import java.nio.file.Path;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SimplexNoiseJsonLoader<K extends SeedResource>
/*    */   extends JsonLoader<K, NoiseFunction>
/*    */ {
/*    */   public SimplexNoiseJsonLoader(@Nonnull SeedString<K> seed, Path dataFolder, JsonElement json) {
/* 16 */     super(seed.append(".SimplexNoise"), dataFolder, json);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public NoiseFunction load() {
/* 22 */     return (NoiseFunction)SimplexNoise.INSTANCE;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\json\SimplexNoiseJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */