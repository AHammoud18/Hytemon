/*     */ package com.hypixel.hytale.server.worldgen.loader.util;
/*     */ 
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.hypixel.hytale.assetstore.map.BlockTypeAssetMap;
/*     */ import com.hypixel.hytale.assetstore.map.IndexedLookupTableAssetMap;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.procedurallib.json.JsonLoader;
/*     */ import com.hypixel.hytale.procedurallib.json.SeedString;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.asset.type.fluid.Fluid;
/*     */ import com.hypixel.hytale.server.core.prefab.selection.mask.BlockPattern;
/*     */ import com.hypixel.hytale.server.worldgen.SeedStringResource;
/*     */ import com.hypixel.hytale.server.worldgen.util.BlockFluidEntry;
/*     */ import com.hypixel.hytale.server.worldgen.util.ResolvedBlockArray;
/*     */ import java.nio.file.Path;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ResolvedBlockArrayJsonLoader
/*     */   extends JsonLoader<SeedStringResource, ResolvedBlockArray>
/*     */ {
/*     */   public ResolvedBlockArrayJsonLoader(@Nonnull SeedString<SeedStringResource> seed, Path dataFolder, JsonElement json) {
/*  29 */     super(seed.append("ResolvedBlockArray"), dataFolder, json);
/*     */   }
/*     */ 
/*     */   
/*     */   public ResolvedBlockArray load() {
/*  34 */     if (this.json == null || this.json.isJsonNull()) return ResolvedBlockArray.EMPTY;
/*     */     
/*  36 */     if (!this.json.isJsonArray()) {
/*  37 */       if (this.json.isJsonObject()) return loadSingleBlock(this.json.getAsJsonObject()); 
/*  38 */       return loadSingleBlock(this.json.getAsString());
/*     */     } 
/*     */     
/*  41 */     JsonArray jsonArray = this.json.getAsJsonArray();
/*     */ 
/*     */     
/*  44 */     if (jsonArray.size() == 1) {
/*  45 */       if (jsonArray.get(0).isJsonObject()) return loadSingleBlock(jsonArray.get(0).getAsJsonObject()); 
/*  46 */       return loadSingleBlock(jsonArray.get(0).getAsString());
/*     */     } 
/*     */     
/*  49 */     BlockTypeAssetMap<String, BlockType> assetMap = BlockType.getAssetMap();
/*  50 */     IndexedLookupTableAssetMap<String, Fluid> fluidMap = Fluid.getAssetMap();
/*  51 */     BlockFluidEntry[] blocks = new BlockFluidEntry[jsonArray.size()];
/*  52 */     for (int k = 0; k < blocks.length; k++) {
/*  53 */       JsonElement elm = jsonArray.get(k);
/*  54 */       if (elm.isJsonObject()) {
/*  55 */         JsonObject obj = elm.getAsJsonObject();
/*  56 */         int blockIndex = 0;
/*  57 */         int rotation = 0;
/*  58 */         int fluidIndex = 0;
/*  59 */         if (obj.has("Block")) {
/*  60 */           BlockPattern.BlockEntry key = BlockPattern.BlockEntry.decode(obj.get("Block").getAsString());
/*  61 */           int index = BlockType.getBlockIdOrUnknown(key.blockTypeKey(), "Failed to find block '%s' in resolved block array!", new Object[] { key.blockTypeKey() });
/*  62 */           if (index == Integer.MIN_VALUE) throw new IllegalArgumentException("Unknown key! " + String.valueOf(key)); 
/*  63 */           blockIndex = index;
/*  64 */           rotation = key.rotation();
/*     */         } 
/*  66 */         if (obj.has("Fluid")) {
/*  67 */           String key = obj.get("Fluid").getAsString();
/*  68 */           int index = Fluid.getFluidIdOrUnknown(key, "Failed to find fluid '%s' in resolved block array!", new Object[] { key });
/*  69 */           if (index == Integer.MIN_VALUE) throw new IllegalArgumentException("Unknown key! " + key); 
/*  70 */           fluidIndex = index;
/*     */         } 
/*  72 */         blocks[k] = new BlockFluidEntry(blockIndex, rotation, fluidIndex);
/*     */       }
/*     */       else {
/*     */         
/*  76 */         String blockName = elm.getAsString();
/*     */         try {
/*  78 */           BlockPattern.BlockEntry key = BlockPattern.BlockEntry.decode(blockName);
/*  79 */           int index = BlockType.getBlockIdOrUnknown(key.blockTypeKey(), "Failed to find block '%s' in resolved block array!", new Object[] { key.blockTypeKey() });
/*  80 */           if (index == Integer.MIN_VALUE) throw new IllegalArgumentException("Unknown key! " + String.valueOf(key)); 
/*  81 */           blocks[k] = new BlockFluidEntry(index, key.rotation(), 0);
/*  82 */         } catch (IllegalArgumentException e) {
/*  83 */           throw new IllegalArgumentException("BlockLayer " + blockName + " does not exist in BlockTypes", e);
/*     */         } 
/*     */       } 
/*     */     } 
/*  87 */     return new ResolvedBlockArray(blocks);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public ResolvedBlockArray loadSingleBlock(@Nonnull String blockName) {
/*  92 */     BlockTypeAssetMap<String, BlockType> assetMap = BlockType.getAssetMap();
/*     */     try {
/*  94 */       BlockPattern.BlockEntry key = BlockPattern.BlockEntry.decode(blockName);
/*  95 */       int index = assetMap.getIndex(key.blockTypeKey());
/*  96 */       if (index == Integer.MIN_VALUE) throw new IllegalArgumentException("Unknown key! " + String.valueOf(key));
/*     */       
/*  98 */       long mapIndex = MathUtil.packLong(index, 0);
/*  99 */       if (key.rotation() == 0) {
/* 100 */         ResolvedBlockArray cachedResolvedBlockArray = (ResolvedBlockArray)ResolvedBlockArray.RESOLVED_BLOCKS.get(mapIndex);
/* 101 */         if (cachedResolvedBlockArray != null) return cachedResolvedBlockArray;
/*     */       
/*     */       } 
/* 104 */       ResolvedBlockArray resolvedBlockArray = new ResolvedBlockArray(new BlockFluidEntry[] { new BlockFluidEntry(index, key.rotation(), 0) });
/* 105 */       if (key.rotation() == 0) {
/* 106 */         ResolvedBlockArray.RESOLVED_BLOCKS.put(mapIndex, resolvedBlockArray);
/*     */       }
/* 108 */       return resolvedBlockArray;
/* 109 */     } catch (IllegalArgumentException e) {
/* 110 */       throw new IllegalArgumentException("BlockLayer does not exist in BlockTypes", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ResolvedBlockArray loadSingleBlock(@Nonnull JsonObject obj) {
/* 116 */     BlockTypeAssetMap<String, BlockType> assetMap = BlockType.getAssetMap();
/* 117 */     IndexedLookupTableAssetMap<String, Fluid> fluidMap = Fluid.getAssetMap();
/*     */     try {
/* 119 */       int blockIndex = 0;
/* 120 */       int rotation = 0;
/* 121 */       int fluidIndex = 0;
/* 122 */       if (obj.has("Block")) {
/* 123 */         BlockPattern.BlockEntry key = BlockPattern.BlockEntry.decode(obj.get("Block").getAsString());
/* 124 */         int index = assetMap.getIndex(key.blockTypeKey());
/* 125 */         if (index == Integer.MIN_VALUE) throw new IllegalArgumentException("Unknown key! " + String.valueOf(key)); 
/* 126 */         blockIndex = index;
/* 127 */         rotation = key.rotation();
/*     */       } 
/* 129 */       if (obj.has("Fluid")) {
/* 130 */         String key = obj.get("Fluid").getAsString();
/* 131 */         int index = fluidMap.getIndex(key);
/* 132 */         if (index == Integer.MIN_VALUE) throw new IllegalArgumentException("Unknown key! " + key); 
/* 133 */         fluidIndex = index;
/*     */       } 
/*     */       
/* 136 */       long mapIndex = MathUtil.packLong(blockIndex, fluidIndex);
/* 137 */       if (rotation == 0) {
/* 138 */         ResolvedBlockArray cachedResolvedBlockArray = (ResolvedBlockArray)ResolvedBlockArray.RESOLVED_BLOCKS.get(mapIndex);
/* 139 */         if (cachedResolvedBlockArray != null) return cachedResolvedBlockArray;
/*     */       
/*     */       } 
/* 142 */       ResolvedBlockArray resolvedBlockArray = new ResolvedBlockArray(new BlockFluidEntry[] { new BlockFluidEntry(blockIndex, rotation, fluidIndex) });
/*     */       
/* 144 */       if (rotation == 0) {
/* 145 */         ResolvedBlockArray.RESOLVED_BLOCKS.put(mapIndex, resolvedBlockArray);
/*     */       }
/* 147 */       return resolvedBlockArray;
/* 148 */     } catch (IllegalArgumentException e) {
/* 149 */       throw new IllegalArgumentException("BlockLayer does not exist in BlockTypes", e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loade\\util\ResolvedBlockArrayJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */