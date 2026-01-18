/*     */ package com.hypixel.hytale.server.worldgen.loader.util;
/*     */ 
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.hypixel.hytale.assetstore.map.BlockTypeAssetMap;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.procedurallib.json.JsonLoader;
/*     */ import com.hypixel.hytale.procedurallib.json.SeedString;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.asset.type.fluid.Fluid;
/*     */ import com.hypixel.hytale.server.worldgen.SeedStringResource;
/*     */ import com.hypixel.hytale.server.worldgen.util.BlockFluidEntry;
/*     */ import com.hypixel.hytale.server.worldgen.util.ResolvedBlockArray;
/*     */ import java.nio.file.Path;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ResolvedVariantsBlockArrayLoader
/*     */   extends JsonLoader<SeedStringResource, ResolvedBlockArray>
/*     */ {
/*     */   public ResolvedVariantsBlockArrayLoader(SeedString<SeedStringResource> seed, Path dataFolder, JsonElement json) {
/*  30 */     super(seed, dataFolder, json);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ResolvedBlockArray load() {
/*  36 */     if (this.json == null || this.json.isJsonNull()) return ResolvedBlockArray.EMPTY;
/*     */     
/*  38 */     if (!this.json.isJsonArray()) return loadSingleBlock(this.json.getAsString());
/*     */     
/*  40 */     JsonArray jsonArray = this.json.getAsJsonArray();
/*     */ 
/*     */     
/*  43 */     if (jsonArray.size() == 1) {
/*  44 */       return loadSingleBlock(jsonArray.get(0).getAsString());
/*     */     }
/*     */     
/*  47 */     BlockTypeAssetMap<String, BlockType> assetMap = BlockType.getAssetMap();
/*     */ 
/*     */     
/*  50 */     List<BlockFluidEntry[]> resolvedBlocksList = (List)new ArrayList<>();
/*  51 */     int size = 0;
/*  52 */     for (int k = 0; k < jsonArray.size(); k++) {
/*  53 */       String blockName = jsonArray.get(k).getAsString(); try {
/*     */         BlockFluidEntry[] blockVariantArray;
/*  55 */         if (assetMap.getAsset(blockName) == null) throw new IllegalArgumentException(String.valueOf(blockName));
/*     */ 
/*     */         
/*  58 */         int index = assetMap.getIndex(blockName);
/*  59 */         if (index == Integer.MIN_VALUE) throw new IllegalArgumentException("Unknown key! " + blockName); 
/*  60 */         ResolvedBlockArray cachedResolvedBlockArray = (ResolvedBlockArray)ResolvedBlockArray.RESOLVED_BLOCKS_WITH_VARIANTS.get(index);
/*     */ 
/*     */         
/*  63 */         if (cachedResolvedBlockArray != null) {
/*  64 */           blockVariantArray = cachedResolvedBlockArray.getEntries();
/*     */         } else {
/*  66 */           blockVariantArray = resolveBlockArrayWithVariants(blockName, assetMap, 0);
/*     */         } 
/*     */         
/*  69 */         resolvedBlocksList.add(blockVariantArray);
/*  70 */         size += blockVariantArray.length;
/*  71 */       } catch (IllegalArgumentException e) {
/*  72 */         throw new IllegalArgumentException("BlockLayer does not exist in BlockTypes", e);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  77 */     BlockFluidEntry[] blocks = new BlockFluidEntry[size];
/*  78 */     for (BlockFluidEntry[] blockArray : resolvedBlocksList) {
/*  79 */       for (BlockFluidEntry block : blockArray) {
/*  80 */         blocks[--size] = block;
/*     */       }
/*     */     } 
/*     */     
/*  84 */     return new ResolvedBlockArray(blocks);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ResolvedBlockArray loadSingleBlock(@Nonnull String blockName) {
/*  89 */     BlockTypeAssetMap<String, BlockType> assetMap = BlockType.getAssetMap();
/*     */     try {
/*  91 */       if (assetMap.getAsset(blockName) == null) throw new IllegalArgumentException(String.valueOf(blockName));
/*     */       
/*  93 */       int blockId = assetMap.getIndex(blockName);
/*  94 */       if (blockId == Integer.MIN_VALUE) throw new IllegalArgumentException("Unknown block! " + blockName); 
/*  95 */       long mapIndex = MathUtil.packLong(blockId, 0);
/*  96 */       ResolvedBlockArray cachedResolvedBlockArray = (ResolvedBlockArray)ResolvedBlockArray.RESOLVED_BLOCKS_WITH_VARIANTS.get(mapIndex);
/*  97 */       if (cachedResolvedBlockArray != null) return cachedResolvedBlockArray;
/*     */       
/*  99 */       BlockFluidEntry[] blocks = resolveBlockArrayWithVariants(blockName, assetMap, 0);
/* 100 */       ResolvedBlockArray resolvedBlockArray = new ResolvedBlockArray(blocks);
/* 101 */       ResolvedBlockArray.RESOLVED_BLOCKS_WITH_VARIANTS.put(mapIndex, resolvedBlockArray);
/* 102 */       return resolvedBlockArray;
/* 103 */     } catch (IllegalArgumentException e) {
/* 104 */       throw new IllegalArgumentException("BlockLayer does not exist in BlockTypes", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static ResolvedBlockArray loadSingleBlock(@Nonnull JsonObject object) {
/* 111 */     BlockTypeAssetMap<String, BlockType> assetMap = BlockType.getAssetMap();
/*     */     try {
/* 113 */       if (object.has("Block")) {
/* 114 */         String blockName = object.get("Block").getAsString();
/* 115 */         if (assetMap.getAsset(blockName) == null) throw new IllegalArgumentException(String.valueOf(blockName));
/*     */         
/* 117 */         int blockId = assetMap.getIndex(blockName);
/* 118 */         if (blockId == Integer.MIN_VALUE) throw new IllegalArgumentException("Unknown block! " + blockName);
/*     */         
/* 120 */         int fluidId = 0;
/* 121 */         if (object.has("Fluid")) {
/* 122 */           String fluidName = object.get("Fluid").getAsString();
/* 123 */           fluidId = Fluid.getAssetMap().getIndex(fluidName);
/* 124 */           if (fluidId == Integer.MIN_VALUE) throw new IllegalArgumentException("Unknown fluid! " + fluidName);
/*     */         
/*     */         } 
/* 127 */         long mapIndex = MathUtil.packLong(blockId, fluidId);
/* 128 */         ResolvedBlockArray cachedResolvedBlockArray = (ResolvedBlockArray)ResolvedBlockArray.RESOLVED_BLOCKS_WITH_VARIANTS.get(mapIndex);
/* 129 */         if (cachedResolvedBlockArray != null) return cachedResolvedBlockArray;
/*     */         
/* 131 */         BlockFluidEntry[] blocks = resolveBlockArrayWithVariants(blockName, assetMap, fluidId);
/* 132 */         ResolvedBlockArray resolvedBlockArray = new ResolvedBlockArray(blocks);
/* 133 */         ResolvedBlockArray.RESOLVED_BLOCKS_WITH_VARIANTS.put(mapIndex, resolvedBlockArray);
/* 134 */         return resolvedBlockArray;
/* 135 */       }  if (object.has("Fluid")) {
/* 136 */         return ResolvedBlockArrayJsonLoader.loadSingleBlock(object);
/*     */       }
/* 138 */       throw new IllegalArgumentException("Required either Block or Fluid key");
/*     */     }
/* 140 */     catch (IllegalArgumentException e) {
/* 141 */       throw new IllegalArgumentException("BlockLayer does not exist in BlockTypes", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static BlockFluidEntry[] resolveBlockArrayWithVariants(String baseKey, @Nonnull BlockTypeAssetMap<String, BlockType> assetMap, int fluidId) {
/* 147 */     List<String> variants = new ArrayList<>((Collection<? extends String>)assetMap.getSubKeys(baseKey));
/* 148 */     BlockFluidEntry[] blocks = new BlockFluidEntry[variants.size()];
/* 149 */     for (int i = 0; i < variants.size(); i++) {
/* 150 */       String key = variants.get(i);
/* 151 */       int index = assetMap.getIndex(key);
/* 152 */       if (index == Integer.MIN_VALUE) throw new IllegalArgumentException("Unknown key! " + key); 
/* 153 */       blocks[i] = new BlockFluidEntry(index, 0, fluidId);
/*     */     } 
/*     */     
/* 156 */     return blocks;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loade\\util\ResolvedVariantsBlockArrayLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */