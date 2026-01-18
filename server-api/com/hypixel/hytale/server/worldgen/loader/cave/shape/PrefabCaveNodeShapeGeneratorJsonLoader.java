/*    */ package com.hypixel.hytale.server.worldgen.loader.cave.shape;
/*    */ 
/*    */ import com.google.gson.JsonArray;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.procedurallib.json.SeedString;
/*    */ import com.hypixel.hytale.server.worldgen.SeedStringResource;
/*    */ import com.hypixel.hytale.server.worldgen.cave.shape.PrefabCaveNodeShape;
/*    */ import com.hypixel.hytale.server.worldgen.loader.WorldGenPrefabLoader;
/*    */ import com.hypixel.hytale.server.worldgen.loader.WorldGenPrefabSupplier;
/*    */ import com.hypixel.hytale.server.worldgen.loader.prefab.BlockPlacementMaskJsonLoader;
/*    */ import com.hypixel.hytale.server.worldgen.prefab.BlockPlacementMask;
/*    */ import com.hypixel.hytale.server.worldgen.util.condition.BlockMaskCondition;
/*    */ import com.hypixel.hytale.server.worldgen.util.condition.DefaultBlockMaskCondition;
/*    */ import java.nio.file.Path;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PrefabCaveNodeShapeGeneratorJsonLoader
/*    */   extends CaveNodeShapeGeneratorJsonLoader
/*    */ {
/*    */   public PrefabCaveNodeShapeGeneratorJsonLoader(@Nonnull SeedString<SeedStringResource> seed, Path dataFolder, JsonElement json) {
/* 31 */     super(seed.append(".PrefabCaveNodeShapeGenerator"), dataFolder, json);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public PrefabCaveNodeShape.PrefabCaveNodeShapeGenerator load() {
/* 37 */     return new PrefabCaveNodeShape.PrefabCaveNodeShapeGenerator(
/* 38 */         loadPrefabs(), 
/* 39 */         loadMask());
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   protected List<WorldGenPrefabSupplier> loadPrefabs() {
/* 45 */     WorldGenPrefabLoader loader = ((SeedStringResource)this.seed.get()).getLoader();
/* 46 */     List<WorldGenPrefabSupplier> prefabs = new ArrayList<>();
/* 47 */     JsonElement prefabElement = get("Prefab");
/* 48 */     if (prefabElement.isJsonArray()) {
/* 49 */       JsonArray prefabArray = prefabElement.getAsJsonArray();
/* 50 */       for (JsonElement prefabArrayElement : prefabArray) {
/* 51 */         String prefabString = prefabArrayElement.getAsString();
/* 52 */         Collections.addAll(prefabs, loader.get(prefabString));
/*    */       } 
/*    */     } else {
/* 55 */       String prefabString = prefabElement.getAsString();
/* 56 */       Collections.addAll(prefabs, loader.get(prefabString));
/*    */     } 
/* 58 */     if (prefabs.isEmpty()) throw new IllegalArgumentException("Prefabs are empty! Key: Prefab"); 
/* 59 */     return prefabs;
/*    */   }
/*    */   @Nullable
/*    */   protected BlockMaskCondition loadMask() {
/*    */     BlockPlacementMask blockPlacementMask;
/* 64 */     DefaultBlockMaskCondition defaultBlockMaskCondition = DefaultBlockMaskCondition.DEFAULT_TRUE;
/* 65 */     if (has("Mask"))
/*    */     {
/* 67 */       blockPlacementMask = (new BlockPlacementMaskJsonLoader(this.seed, this.dataFolder, getRaw("Mask"))).load();
/*    */     }
/* 69 */     return (BlockMaskCondition)blockPlacementMask;
/*    */   }
/*    */   
/*    */   public static interface Constants {
/*    */     public static final String KEY_PREFAB = "Prefab";
/*    */     public static final String KEY_MASK = "Mask";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\cave\shape\PrefabCaveNodeShapeGeneratorJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */