/*    */ package com.hypixel.hytale.procedurallib.json;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.procedurallib.logic.cell.jitter.CellJitter;
/*    */ import java.nio.file.Path;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public abstract class AbstractCellJitterJsonLoader<K extends SeedResource, T>
/*    */   extends JsonLoader<K, T>
/*    */ {
/*    */   public AbstractCellJitterJsonLoader(SeedString<K> seed, Path dataFolder, JsonElement json) {
/* 13 */     super(seed, dataFolder, json);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   protected CellJitter loadJitter() {
/* 18 */     double defaultJitter = loadDefaultJitter();
/* 19 */     double x = loadJitterX(defaultJitter);
/* 20 */     double y = loadJitterY(defaultJitter);
/* 21 */     double z = loadJitterZ(defaultJitter);
/* 22 */     return CellJitter.of(x, y, z);
/*    */   }
/*    */   
/*    */   protected double loadDefaultJitter() {
/* 26 */     return loadJitter(this, "Jitter", 1.0D);
/*    */   }
/*    */   
/*    */   protected double loadJitterX(double defaultJitter) {
/* 30 */     return loadJitter(this, "JitterX", defaultJitter);
/*    */   }
/*    */   
/*    */   protected double loadJitterY(double defaultJitter) {
/* 34 */     return loadJitter(this, "JitterY", defaultJitter);
/*    */   }
/*    */   
/*    */   protected double loadJitterZ(double defaultJitter) {
/* 38 */     return loadJitter(this, "JitterZ", defaultJitter);
/*    */   }
/*    */   
/*    */   protected static double loadJitter(@Nonnull JsonLoader<?, ?> loader, String key, double defaultJitter) {
/* 42 */     if (!loader.has(key)) return defaultJitter;
/*    */     
/* 44 */     return loader.get(key)
/* 45 */       .getAsDouble();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\json\AbstractCellJitterJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */