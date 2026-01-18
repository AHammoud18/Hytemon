/*    */ package com.hypixel.hytale.procedurallib.json;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.procedurallib.condition.IHeightThresholdInterpreter;
/*    */ import java.nio.file.Path;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HeightThresholdInterpreterJsonLoader<K extends SeedResource>
/*    */   extends JsonLoader<K, IHeightThresholdInterpreter>
/*    */ {
/*    */   protected final int length;
/*    */   
/*    */   public HeightThresholdInterpreterJsonLoader(@Nonnull SeedString<K> seed, Path dataFolder, JsonElement json, int length) {
/* 17 */     super(seed.append(".HeightThresholdInterpreter"), dataFolder, json);
/* 18 */     this.length = length;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public IHeightThresholdInterpreter load() {
/* 24 */     if (NoiseHeightThresholdInterpreterJsonLoader.shouldHandle(this.json.getAsJsonObject())) {
/* 25 */       return (IHeightThresholdInterpreter)(new NoiseHeightThresholdInterpreterJsonLoader<>(this.seed, this.dataFolder, this.json, this.length))
/* 26 */         .load();
/*    */     }
/* 28 */     return (IHeightThresholdInterpreter)(new BasicHeightThresholdInterpreterJsonLoader<>(this.seed, this.dataFolder, this.json, this.length))
/* 29 */       .load();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\json\HeightThresholdInterpreterJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */