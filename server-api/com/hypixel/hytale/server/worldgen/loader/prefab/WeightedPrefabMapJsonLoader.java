/*    */ package com.hypixel.hytale.server.worldgen.loader.prefab;
/*    */ 
/*    */ import com.google.gson.JsonArray;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.common.map.IWeightedMap;
/*    */ import com.hypixel.hytale.common.map.WeightedMap;
/*    */ import com.hypixel.hytale.procedurallib.json.JsonLoader;
/*    */ import com.hypixel.hytale.procedurallib.json.SeedString;
/*    */ import com.hypixel.hytale.server.worldgen.SeedStringResource;
/*    */ import com.hypixel.hytale.server.worldgen.loader.WorldGenPrefabLoader;
/*    */ import com.hypixel.hytale.server.worldgen.loader.WorldGenPrefabSupplier;
/*    */ import java.nio.file.Path;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WeightedPrefabMapJsonLoader
/*    */   extends JsonLoader<SeedStringResource, IWeightedMap<WorldGenPrefabSupplier>>
/*    */ {
/*    */   protected final String prefabsKey;
/*    */   protected final String weightsKey;
/*    */   
/*    */   public WeightedPrefabMapJsonLoader(@Nonnull SeedString<SeedStringResource> seed, Path dataFolder, JsonElement json, String prefabsKey, String weightsKey) {
/* 27 */     super(seed.append(".WeightedPrefabMap"), dataFolder, json);
/* 28 */     this.prefabsKey = prefabsKey;
/* 29 */     this.weightsKey = weightsKey;
/*    */   }
/*    */ 
/*    */   
/*    */   public IWeightedMap<WorldGenPrefabSupplier> load() {
/* 34 */     WorldGenPrefabLoader prefabLoader = ((SeedStringResource)this.seed.get()).getLoader();
/* 35 */     WeightedMap.Builder<WorldGenPrefabSupplier> builder = WeightedMap.builder((Object[])WorldGenPrefabSupplier.EMPTY_ARRAY);
/* 36 */     if (!has(this.prefabsKey)) throw new IllegalArgumentException(this.prefabsKey); 
/* 37 */     JsonElement prefabElement = get(this.prefabsKey);
/* 38 */     if (prefabElement.isJsonArray()) {
/* 39 */       JsonArray prefabArray = prefabElement.getAsJsonArray();
/* 40 */       JsonArray weightArray = has(this.weightsKey) ? get(this.weightsKey).getAsJsonArray() : null;
/* 41 */       if (weightArray != null && prefabArray.size() != weightArray.size()) throw new IllegalArgumentException("Weight array size is different from prefab name array.");
/*    */       
/* 43 */       for (int i = 0; i < prefabArray.size(); i++) {
/* 44 */         JsonElement prefabArrayElement = prefabArray.get(i);
/* 45 */         String prefabString = prefabArrayElement.getAsString();
/* 46 */         WorldGenPrefabSupplier[] suppliers = prefabLoader.get(prefabString);
/* 47 */         double weight = (weightArray != null) ? (weightArray.get(i).getAsDouble() / suppliers.length) : 1.0D;
/* 48 */         for (WorldGenPrefabSupplier supplier : suppliers) builder.put(supplier, weight); 
/*    */       } 
/*    */     } else {
/* 51 */       String prefabString = prefabElement.getAsString();
/* 52 */       WorldGenPrefabSupplier[] suppliers = prefabLoader.get(prefabString);
/* 53 */       for (WorldGenPrefabSupplier supplier : suppliers) builder.put(supplier, 1.0D); 
/*    */     } 
/* 55 */     if (builder.size() <= 0) throw new IllegalArgumentException("Prefabs are defined but could not find a valid entry!"); 
/* 56 */     return builder.build();
/*    */   }
/*    */   
/*    */   public static interface Constants {
/*    */     public static final String ERROR_ENTRY_NO_PREFAB = "Could not find prefab names. Keyword: %s";
/*    */     public static final String ERROR_ENTRY_WEIGHT_SIZE = "Weight array size is different from prefab name array.";
/*    */     public static final String ERROR_NO_PREFABS = "Prefabs are defined but could not find a valid entry!";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\prefab\WeightedPrefabMapJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */