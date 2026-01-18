/*     */ package com.hypixel.hytale.server.worldgen.loader.prefab;
/*     */ 
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.hypixel.hytale.assetstore.map.BlockTypeAssetMap;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.procedurallib.json.JsonLoader;
/*     */ import com.hypixel.hytale.procedurallib.json.SeedString;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.worldgen.SeedStringResource;
/*     */ import com.hypixel.hytale.server.worldgen.loader.util.ResolvedBlockArrayJsonLoader;
/*     */ import com.hypixel.hytale.server.worldgen.loader.util.ResolvedVariantsBlockArrayLoader;
/*     */ import com.hypixel.hytale.server.worldgen.prefab.BlockPlacementMask;
/*     */ import com.hypixel.hytale.server.worldgen.util.BlockFluidEntry;
/*     */ import com.hypixel.hytale.server.worldgen.util.ResolvedBlockArray;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import java.nio.file.Path;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockPlacementMaskJsonLoader
/*     */   extends JsonLoader<SeedStringResource, BlockPlacementMask>
/*     */ {
/*  33 */   private static final BlockPlacementMask.IEntry WILDCARD_FALSE = (BlockPlacementMask.IEntry)new BlockPlacementMask.WildcardEntry(false);
/*  34 */   private static final BlockPlacementMask.IEntry WILDCARD_TRUE = (BlockPlacementMask.IEntry)new BlockPlacementMask.WildcardEntry(true);
/*     */   
/*     */   private String fileName;
/*     */   
/*     */   public BlockPlacementMaskJsonLoader(@Nonnull SeedString<SeedStringResource> seed, Path dataFolder, JsonElement json) {
/*  39 */     super(seed.append(".BlockPlacementMask"), dataFolder, json);
/*     */   }
/*     */   public BlockPlacementMask load() {
/*     */     BlockPlacementMask.IMask defaultMask;
/*     */     Long2ObjectOpenHashMap long2ObjectOpenHashMap;
/*  44 */     BlockPlacementMaskRegistry registry = ((SeedStringResource)this.seed.get()).getBlockMaskRegistry();
/*  45 */     if (this.fileName != null) {
/*  46 */       BlockPlacementMask blockPlacementMask = (BlockPlacementMask)registry.getIfPresentFileMask(this.fileName);
/*  47 */       if (blockPlacementMask != null) return blockPlacementMask;
/*     */     
/*     */     } 
/*     */     
/*  51 */     Long2ObjectMap<BlockPlacementMask.Mask> specificMasks = null;
/*     */     
/*  53 */     if (this.json == null || this.json.isJsonNull()) {
/*  54 */       defaultMask = BlockPlacementMask.DEFAULT_MASK;
/*     */     } else {
/*  56 */       if (has("Default")) {
/*     */         
/*  58 */         BlockPlacementMask.Mask mask1 = new BlockPlacementMask.Mask(loadEntries(get("Default").getAsJsonArray()));
/*     */       } else {
/*     */         
/*  61 */         defaultMask = BlockPlacementMask.DEFAULT_MASK;
/*     */       } 
/*     */       
/*  64 */       if (has("Specific")) {
/*  65 */         BlockTypeAssetMap<String, BlockType> assetMap = BlockType.getAssetMap();
/*  66 */         long2ObjectOpenHashMap = new Long2ObjectOpenHashMap();
/*  67 */         JsonArray array = get("Specific").getAsJsonArray();
/*  68 */         for (int i = 0; i < array.size(); i++) {
/*     */           try {
/*  70 */             JsonObject specificObject = array.get(i).getAsJsonObject();
/*  71 */             JsonElement blocksElement = specificObject.get("Block");
/*  72 */             ResolvedBlockArray blocks = (new ResolvedBlockArrayJsonLoader(this.seed, this.dataFolder, blocksElement)).load();
/*  73 */             for (BlockFluidEntry blockEntry : blocks.getEntries()) {
/*  74 */               String key = ((BlockType)assetMap.getAsset(blockEntry.blockId())).getId();
/*  75 */               for (ObjectIterator<String> objectIterator = assetMap.getSubKeys(key).iterator(); objectIterator.hasNext(); ) { String variant = objectIterator.next();
/*  76 */                 int index = assetMap.getIndex(variant);
/*  77 */                 if (index == Integer.MIN_VALUE) throw new IllegalArgumentException("Unknown key! " + variant); 
/*  78 */                 JsonArray rule = specificObject.getAsJsonArray("Rule");
/*  79 */                 long2ObjectOpenHashMap.put(MathUtil.packLong(index, blockEntry.fluidId()), new BlockPlacementMask.Mask(
/*  80 */                       loadEntries(rule))); }
/*     */ 
/*     */             
/*     */             } 
/*  84 */           } catch (Throwable e) {
/*  85 */             throw new Error(String.format("Error while reading specific block mask #%s!", new Object[] { Integer.valueOf(i) }), e);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  91 */     BlockPlacementMask mask = registry.retainOrAllocateMask(defaultMask, (Long2ObjectMap<BlockPlacementMask.Mask>)long2ObjectOpenHashMap);
/*  92 */     if (this.fileName != null) {
/*  93 */       registry.putFileMask(this.fileName, mask);
/*     */     }
/*  95 */     return mask;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected BlockPlacementMask.IEntry[] loadEntries(@Nonnull JsonArray jsonArray) {
/* 100 */     BlockPlacementMask.IEntry[] entries = new BlockPlacementMask.IEntry[jsonArray.size()];
/*     */     
/* 102 */     int head = 0, tail = entries.length;
/* 103 */     for (JsonElement element : jsonArray) {
/* 104 */       if (element.isJsonObject()) {
/* 105 */         JsonObject obj = element.getAsJsonObject();
/* 106 */         boolean bool = true;
/* 107 */         if (obj.has("Replace")) {
/* 108 */           bool = obj.get("Replace").getAsBoolean();
/*     */         }
/* 110 */         ResolvedBlockArray resolvedBlockArray = ResolvedVariantsBlockArrayLoader.loadSingleBlock(obj);
/* 111 */         entries[head++] = (BlockPlacementMask.IEntry)((SeedStringResource)this.seed.get()).getBlockMaskRegistry().retainOrAllocateEntry(resolvedBlockArray, bool);
/*     */         continue;
/*     */       } 
/* 114 */       String string = element.getAsString();
/* 115 */       boolean replace = true;
/* 116 */       int beginIndex = 0;
/* 117 */       if (string.charAt(0) == '!') {
/* 118 */         replace = false;
/* 119 */         beginIndex = 1;
/*     */       } 
/* 121 */       if (string.length() == beginIndex + 1 && string.charAt(beginIndex) == '*') {
/*     */         
/* 123 */         if (tail < entries.length) System.arraycopy(entries, tail, entries, tail - 1, entries.length - tail);
/*     */         
/* 125 */         entries[entries.length - 1] = replace ? WILDCARD_TRUE : WILDCARD_FALSE;
/* 126 */         tail--; continue;
/*     */       } 
/* 128 */       string = string.substring(beginIndex);
/* 129 */       ResolvedBlockArray blocks = ResolvedVariantsBlockArrayLoader.loadSingleBlock(string);
/* 130 */       entries[head++] = (BlockPlacementMask.IEntry)((SeedStringResource)this.seed.get()).getBlockMaskRegistry().retainOrAllocateEntry(blocks, replace);
/*     */     } 
/*     */     
/* 133 */     return entries;
/*     */   }
/*     */ 
/*     */   
/*     */   protected JsonElement loadFileConstructor(String filePath) {
/* 138 */     this.fileName = filePath;
/* 139 */     return ((SeedStringResource)this.seed.get()).getBlockMaskRegistry().cachedFile(filePath, file -> super.loadFileConstructor(file));
/*     */   }
/*     */   
/*     */   public static interface Constants {
/*     */     public static final String KEY_DEFAULT = "Default";
/*     */     public static final String KEY_SPECIFIC = "Specific";
/*     */     public static final String KEY_BLOCK = "Block";
/*     */     public static final String KEY_RULE = "Rule";
/*     */     public static final String ERROR_FAIL_SPECIFIC = "Error while reading specific block mask #%s!";
/*     */     public static final String ERROR_BLOCK_INVALID = "Failed to resolve block \"%s\"";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\prefab\BlockPlacementMaskJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */