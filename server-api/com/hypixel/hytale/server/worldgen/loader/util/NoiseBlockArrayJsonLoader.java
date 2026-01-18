/*     */ package com.hypixel.hytale.server.worldgen.loader.util;
/*     */ 
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.hypixel.hytale.procedurallib.json.DoubleRangeJsonLoader;
/*     */ import com.hypixel.hytale.procedurallib.json.JsonLoader;
/*     */ import com.hypixel.hytale.procedurallib.json.NoisePropertyJsonLoader;
/*     */ import com.hypixel.hytale.procedurallib.json.SeedString;
/*     */ import com.hypixel.hytale.procedurallib.property.NoiseProperty;
/*     */ import com.hypixel.hytale.procedurallib.supplier.DoubleRange;
/*     */ import com.hypixel.hytale.procedurallib.supplier.IDoubleRange;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.asset.type.fluid.Fluid;
/*     */ import com.hypixel.hytale.server.core.prefab.selection.mask.BlockPattern;
/*     */ import com.hypixel.hytale.server.worldgen.SeedStringResource;
/*     */ import com.hypixel.hytale.server.worldgen.util.BlockFluidEntry;
/*     */ import com.hypixel.hytale.server.worldgen.util.ConstantNoiseProperty;
/*     */ import com.hypixel.hytale.server.worldgen.util.NoiseBlockArray;
/*     */ import java.nio.file.Path;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NoiseBlockArrayJsonLoader
/*     */   extends JsonLoader<SeedStringResource, NoiseBlockArray>
/*     */ {
/*     */   public NoiseBlockArrayJsonLoader(@Nonnull SeedString<SeedStringResource> seed, Path dataFolder, JsonElement json) {
/*  35 */     super(seed.append(".NoiseBlockArray"), dataFolder, json);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public NoiseBlockArray load() {
/*     */     JsonElement entriesElement;
/*  42 */     if (this.json.isJsonPrimitive()) {
/*  43 */       return new NoiseBlockArray(new NoiseBlockArray.Entry[] { (new EntryJsonLoader(this.seed, this.dataFolder, this.json))
/*  44 */             .load() });
/*     */     }
/*  46 */     if (this.json.isJsonObject() && !has("Entries")) {
/*  47 */       return new NoiseBlockArray(new NoiseBlockArray.Entry[] {
/*  48 */             loadEntry(this.json, 0)
/*     */           });
/*     */     }
/*  51 */     if (this.json.isJsonArray()) {
/*  52 */       entriesElement = this.json;
/*     */     } else {
/*  54 */       entriesElement = get("Entries");
/*     */     } 
/*  56 */     if (entriesElement == null || entriesElement.isJsonNull()) throw new IllegalArgumentException("Could not find entries in block array. Keyword: Entries"); 
/*  57 */     JsonArray entriesArray = entriesElement.getAsJsonArray();
/*  58 */     NoiseBlockArray.Entry[] entries = new NoiseBlockArray.Entry[entriesArray.size()];
/*  59 */     for (int i = 0; i < entriesArray.size(); i++) {
/*     */       try {
/*  61 */         entries[i] = loadEntry(entriesArray.get(i), i);
/*  62 */       } catch (Throwable e) {
/*  63 */         throw new Error(String.format("Failed to load block array entry #%s", new Object[] { Integer.valueOf(i) }), e);
/*     */       } 
/*     */     } 
/*  66 */     return new NoiseBlockArray(entries);
/*     */   }
/*     */   public static interface Constants {
/*     */     public static final String KEY_ENTRIES = "Entries"; public static final String KEY_ENTRY_TYPE = "Type"; public static final String KEY_ENTRY_REPEAT = "Repeat"; public static final String KEY_ENTRY_REPEAT_NOISE = "RepeatNoise"; public static final String ERROR_NO_ENTRIES = "Could not find entries in block array. Keyword: Entries"; public static final String ERROR_ENTRY_FAIL = "Failed to load block array entry #%s"; }
/*     */   @Nonnull
/*     */   protected NoiseBlockArray.Entry loadEntry(JsonElement element, int i) {
/*  72 */     return (new EntryJsonLoader(this.seed.append("-" + i), this.dataFolder, element))
/*  73 */       .load();
/*     */   }
/*     */   
/*     */   protected static class EntryJsonLoader
/*     */     extends JsonLoader<SeedStringResource, NoiseBlockArray.Entry> {
/*     */     public EntryJsonLoader(@Nonnull SeedString<SeedStringResource> seed, Path dataFolder, JsonElement json) {
/*  79 */       super(seed.append(".Entry"), dataFolder, json);
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public NoiseBlockArray.Entry load() {
/*  85 */       if (this.json.isJsonPrimitive()) {
/*  86 */         String blockName = this.json.getAsString();
/*  87 */         int repetitions = 1;
/*  88 */         if (blockName.contains(":")) {
/*  89 */           String[] parts = blockName.split(":");
/*  90 */           blockName = parts[0];
/*  91 */           repetitions = Integer.parseInt(parts[1]);
/*     */         } 
/*  93 */         BlockFluidEntry blockEntry = resolveBlockId(blockName);
/*  94 */         return new NoiseBlockArray.Entry(blockName, blockEntry, (IDoubleRange)new DoubleRange.Constant(repetitions), ConstantNoiseProperty.DEFAULT_ZERO);
/*     */       } 
/*     */ 
/*     */       
/*  98 */       if (this.json.isJsonObject()) {
/*  99 */         String blockName = get("Type").getAsString();
/* 100 */         BlockFluidEntry blockEntry = resolveBlockId(blockName);
/* 101 */         IDoubleRange repetitions = loadRepetitions();
/* 102 */         NoiseProperty noise = loadNoise();
/* 103 */         return new NoiseBlockArray.Entry(blockName, blockEntry, repetitions, noise);
/*     */       } 
/* 105 */       throw new IllegalArgumentException("Unsupported Json Entry: " + String.valueOf(this.json));
/*     */     }
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     protected IDoubleRange loadRepetitions() {
/* 111 */       return (new DoubleRangeJsonLoader(this.seed, this.dataFolder, get("Repeat"), 1.0D))
/* 112 */         .load();
/*     */     }
/*     */     
/*     */     @Nullable
/*     */     protected NoiseProperty loadNoise() {
/* 117 */       NoiseProperty noise = ConstantNoiseProperty.DEFAULT_ZERO;
/* 118 */       if (has("RepeatNoise"))
/*     */       {
/* 120 */         noise = (new NoisePropertyJsonLoader(this.seed, this.dataFolder, get("RepeatNoise"))).load();
/*     */       }
/* 122 */       return noise;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     protected BlockFluidEntry resolveBlockId(@Nonnull String name) {
/*     */       try {
/* 129 */         if (name.startsWith("fluid#")) {
/* 130 */           String str = name.substring("fluid#".length());
/* 131 */           int i = Fluid.getAssetMap().getIndex(str);
/* 132 */           if (i == Integer.MIN_VALUE) throw new IllegalArgumentException("Unknown key! " + str); 
/* 133 */           return new BlockFluidEntry(0, 0, i);
/*     */         } 
/* 135 */         BlockPattern.BlockEntry key = BlockPattern.BlockEntry.decode(name);
/* 136 */         int index = BlockType.getAssetMap().getIndex(key.blockTypeKey());
/* 137 */         if (index == Integer.MIN_VALUE) throw new IllegalArgumentException("Unknown key! " + String.valueOf(key)); 
/* 138 */         return new BlockFluidEntry(index, key.rotation(), 0);
/* 139 */       } catch (IllegalArgumentException e) {
/* 140 */         throw new Error("BlockLayer does not exist in BlockTypes", e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loade\\util\NoiseBlockArrayJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */