/*    */ package com.hypixel.hytale.server.worldgen.loader.climate;
/*    */ 
/*    */ import com.google.gson.JsonArray;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.procedurallib.json.JsonLoader;
/*    */ import com.hypixel.hytale.procedurallib.json.SeedResource;
/*    */ import com.hypixel.hytale.procedurallib.json.SeedString;
/*    */ import com.hypixel.hytale.server.worldgen.climate.UniqueClimateGenerator;
/*    */ import java.nio.file.Path;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class UniqueClimateGeneratorJsonLoader<K extends SeedResource> extends JsonLoader<K, UniqueClimateGenerator> {
/*    */   @Nonnull
/*    */   private final JsonArray array;
/*    */   
/*    */   public UniqueClimateGeneratorJsonLoader(SeedString<K> seed, Path dataFolder, @Nonnull JsonArray json) {
/* 17 */     super(seed, dataFolder, (JsonElement)json);
/* 18 */     this.array = json;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public UniqueClimateGenerator load() {
/* 24 */     if (this.array.isEmpty()) {
/* 25 */       return UniqueClimateGenerator.EMPTY;
/*    */     }
/* 27 */     return new UniqueClimateGenerator(
/* 28 */         loadEntries());
/*    */   }
/*    */ 
/*    */   
/*    */   protected UniqueClimateGenerator.Entry[] loadEntries() {
/* 33 */     UniqueClimateGenerator.Entry[] entries = new UniqueClimateGenerator.Entry[this.array.size()];
/* 34 */     for (int i = 0; i < this.array.size(); i++) {
/* 35 */       entries[i] = (new UniqueClimateJsonLoader<>(this.seed, this.dataFolder, this.array.get(i)))
/* 36 */         .load();
/*    */     }
/* 38 */     return entries;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\climate\UniqueClimateGeneratorJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */