/*     */ package com.hypixel.hytale.server.core.modules.blockset;
/*     */ import com.hypixel.hytale.assetstore.event.LoadedAssetsEvent;
/*     */ import com.hypixel.hytale.assetstore.map.BlockTypeAssetMap;
/*     */ import com.hypixel.hytale.common.plugin.PluginManifest;
/*     */ import com.hypixel.hytale.common.util.StringUtil;
/*     */ import com.hypixel.hytale.server.core.asset.type.blockset.config.BlockSet;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*     */ import com.hypixel.hytale.server.core.plugin.JavaPlugin;
/*     */ import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.ints.IntCollection;
/*     */ import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
/*     */ import it.unimi.dsi.fastutil.ints.IntSet;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ @Deprecated(forRemoval = true)
/*     */ public class BlockSetModule extends JavaPlugin {
/*  27 */   public static final PluginManifest MANIFEST = PluginManifest.corePlugin(BlockSetModule.class)
/*  28 */     .build();
/*     */   
/*     */   private static BlockSetModule INSTANCE;
/*     */   
/*     */   @Nonnull
/*  33 */   private Int2ObjectMap<IntSet> flattenedBlockSets = (Int2ObjectMap<IntSet>)new Int2ObjectOpenHashMap();
/*     */   
/*     */   @Nonnull
/*  36 */   private Int2ObjectMap<IntSet> unmodifiableFlattenedBlockSets = Int2ObjectMaps.unmodifiable(this.flattenedBlockSets);
/*     */ 
/*     */   
/*     */   private BlockSetLookupTable blockSetLookupTable;
/*     */ 
/*     */   
/*     */   public BlockSetModule(@Nonnull JavaPluginInit module) {
/*  43 */     super(module);
/*  44 */     INSTANCE = this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setup() {
/*  49 */     getCommandRegistry().registerCommand((AbstractCommand)new BlockSetCommand(this));
/*  50 */     getEventRegistry().register(LoadedAssetsEvent.class, BlockType.class, this::onBlockTypesChanged);
/*  51 */     getEventRegistry().register(LoadedAssetsEvent.class, BlockSet.class, this::onBlockSetsChanged);
/*     */   }
/*     */   
/*     */   private void onBlockTypesChanged(@Nonnull LoadedAssetsEvent<String, BlockType, BlockTypeAssetMap<String, BlockType>> event) {
/*  55 */     this.blockSetLookupTable = new BlockSetLookupTable(((BlockTypeAssetMap)event.getAssetMap()).getAssetMap());
/*  56 */     this.flattenedBlockSets = flattenBlockSets(this.blockSetLookupTable);
/*  57 */     this.unmodifiableFlattenedBlockSets = Int2ObjectMaps.unmodifiable(this.flattenedBlockSets);
/*     */   }
/*     */   
/*     */   private void onBlockSetsChanged(LoadedAssetsEvent<String, BlockSet, DefaultAssetMap<String, BlockSet>> event) {
/*  61 */     this.blockSetLookupTable = new BlockSetLookupTable(BlockType.getAssetMap().getAssetMap());
/*  62 */     this.flattenedBlockSets = flattenBlockSets(this.blockSetLookupTable);
/*  63 */     this.unmodifiableFlattenedBlockSets = Int2ObjectMaps.unmodifiable(this.flattenedBlockSets);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   private Int2ObjectMap<IntSet> flattenBlockSets(@Nonnull BlockSetLookupTable lookupTable) {
/*  68 */     Int2ObjectOpenHashMap<IntSet> flattenedSets = new Int2ObjectOpenHashMap();
/*     */     
/*  70 */     if (!lookupTable.isEmpty())
/*  71 */       BlockSet.getAssetMap().getAssetMap().forEach((s, blockSet) -> {
/*     */             int index = BlockSet.getAssetMap().getIndex(s);
/*     */             if (index == Integer.MIN_VALUE)
/*     */               throw new IllegalArgumentException("Unknown key! " + s); 
/*     */             IntSet tIntSet = (IntSet)flattenedSets.get(index);
/*     */             if (tIntSet == null) {
/*     */               IntOpenHashSet set = createSet(blockSet, lookupTable, (Int2ObjectMap<IntSet>)flattenedSets);
/*     */               set.trim();
/*     */               flattenedSets.put(index, set);
/*     */             } 
/*     */           }); 
/*  82 */     return Int2ObjectMaps.unmodifiable((Int2ObjectMap)flattenedSets);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   private IntOpenHashSet createSet(@Nonnull BlockSet blockSet, @Nonnull BlockSetLookupTable lookupTable, @Nonnull Int2ObjectMap<IntSet> flattenedSets) {
/*  87 */     IntOpenHashSet result = new IntOpenHashSet();
/*  88 */     String parent = blockSet.getParent();
/*     */     
/*  90 */     if (parent != null && !parent.isEmpty()) {
/*  91 */       int parentIndex = BlockSet.getAssetMap().getIndex(parent);
/*  92 */       if (parentIndex == Integer.MIN_VALUE) throw new IllegalArgumentException("Unknown key! " + parent); 
/*  93 */       result.addAll((IntCollection)flattenedSets.computeIfAbsent(parentIndex, s -> {
/*     */               IntOpenHashSet set = createSet(parent, lookupTable, flattenedSets);
/*     */               
/*     */               set.trim();
/*     */               return (IntSet)set;
/*     */             }));
/*     */     } 
/* 100 */     if (blockSet.isIncludeAll()) {
/* 101 */       lookupTable.addAll((IntSet)result);
/*     */     }
/*     */     
/* 104 */     Objects.requireNonNull(result); consume(blockSet.getIncludeBlockTypes(), (Map<String, IntSet>)lookupTable.getBlockNameIdMap(), "block name", result::addAll);
/* 105 */     Objects.requireNonNull(result); consume(blockSet.getIncludeBlockGroups(), (Map<String, IntSet>)lookupTable.getGroupNameIdMap(), "group name", result::addAll);
/* 106 */     Objects.requireNonNull(result); consume(blockSet.getIncludeHitboxTypes(), (Map<String, IntSet>)lookupTable.getHitboxNameIdMap(), "hitbox name", result::addAll);
/*     */     
/* 108 */     Objects.requireNonNull(result); consume(blockSet.getExcludeBlockTypes(), (Map<String, IntSet>)lookupTable.getBlockNameIdMap(), "block name", result::removeAll);
/* 109 */     Objects.requireNonNull(result); consume(blockSet.getExcludeBlockGroups(), (Map<String, IntSet>)lookupTable.getGroupNameIdMap(), "group name", result::removeAll);
/* 110 */     Objects.requireNonNull(result); consume(blockSet.getExcludeHitboxTypes(), (Map<String, IntSet>)lookupTable.getHitboxNameIdMap(), "hitbox name", result::removeAll);
/*     */     
/* 112 */     Objects.requireNonNull(result); consume(blockSet.getIncludeCategories(), lookupTable, result::addAll);
/* 113 */     Objects.requireNonNull(result); consume(blockSet.getExcludeCategories(), lookupTable, result::removeAll);
/*     */     
/* 115 */     return result;
/*     */   }
/*     */   
/*     */   private void consume(@Nullable String[] values, @Nonnull Map<String, IntSet> map, String typeString, @Nonnull Consumer<IntSet> addAll) {
/* 119 */     if (values != null) {
/* 120 */       for (String s : values) {
/* 121 */         consumeEntry(s, addAll, map, typeString);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void consume(@Nullable String[][] values, @Nonnull BlockSetLookupTable lookupTable, @Nonnull Consumer<IntSet> addAll) {
/* 127 */     if (values != null) {
/* 128 */       for (String[] s : values) {
/* 129 */         consumeCategory(s, addAll, lookupTable);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   private IntOpenHashSet createSet(String name, @Nonnull BlockSetLookupTable lookupTable, @Nonnull Int2ObjectMap<IntSet> flattenedSets) {
/* 136 */     Map<String, BlockSet> blockSets = BlockSet.getAssetMap().getAssetMap();
/* 137 */     BlockSet blockSet = blockSets.get(name);
/* 138 */     if (blockSet == null) {
/* 139 */       getLogger().at(Level.WARNING).log("Creating block sets: Failed to find block set '%s'", name);
/* 140 */       return new IntOpenHashSet();
/*     */     } 
/* 142 */     return createSet(blockSet, lookupTable, flattenedSets);
/*     */   }
/*     */   
/*     */   private void consumeCategory(@Nullable String[] categories, @Nonnull Consumer<IntSet> predicate, @Nonnull BlockSetLookupTable lookupTable) {
/* 146 */     if (categories == null || categories.length == 0)
/*     */       return; 
/* 148 */     Object2ObjectMap<String, IntSet> object2ObjectMap = lookupTable.getCategoryIdMap();
/* 149 */     IntSet catSet = object2ObjectMap.get(categories[0]);
/* 150 */     if (catSet == null) {
/* 151 */       getLogger().at(Level.WARNING).log("Creating block sets: '%s' does not match any block category", categories[0]);
/*     */       return;
/*     */     } 
/* 154 */     if (categories.length == 1) {
/* 155 */       predicate.accept(catSet);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 160 */     IntOpenHashSet intOpenHashSet = new IntOpenHashSet((IntCollection)catSet);
/* 161 */     for (int i = 1; i < categories.length; i++) {
/* 162 */       catSet = object2ObjectMap.get(categories[i]);
/* 163 */       if (catSet == null) {
/* 164 */         getLogger().at(Level.WARNING).log("Creating block sets: '%s' does not match any block category", categories[i]);
/*     */         return;
/*     */       } 
/* 167 */       intOpenHashSet.removeAll((IntCollection)catSet);
/* 168 */       if (intOpenHashSet.isEmpty())
/*     */         return; 
/* 170 */     }  predicate.accept(intOpenHashSet);
/*     */   }
/*     */   
/*     */   private void consumeEntry(@Nonnull String name, @Nonnull Consumer<IntSet> predicate, @Nonnull Map<String, IntSet> nameIdMap, String typeString) {
/* 174 */     if (StringUtil.isGlobPattern(name)) {
/* 175 */       boolean[] found = { false };
/* 176 */       nameIdMap.forEach((s, tIntSet) -> {
/*     */             if (StringUtil.isGlobMatching(name, s)) {
/*     */               predicate.accept(tIntSet);
/*     */               found[0] = true;
/*     */             } 
/*     */           });
/* 182 */       if (!found[0]) {
/* 183 */         getLogger().at(Level.FINE).log("Creating block sets: '%s' does not match any %s", name, typeString);
/*     */       }
/*     */     } else {
/* 186 */       IntSet ids = nameIdMap.get(name);
/*     */       
/* 188 */       if (ids == null) {
/* 189 */         getLogger().at(Level.WARNING).log("Creating block sets: Failed to find %s '%s'", typeString, name);
/*     */       } else {
/* 191 */         predicate.accept(ids);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Int2ObjectMap<IntSet> getBlockSets() {
/* 198 */     return this.unmodifiableFlattenedBlockSets;
/*     */   }
/*     */   
/*     */   public boolean blockInSet(int set, int blockId) {
/* 202 */     IntSet s = (IntSet)this.flattenedBlockSets.get(set);
/* 203 */     return (s != null && s.contains(blockId));
/*     */   }
/*     */   
/*     */   public boolean blockInSet(int set, @Nullable BlockType blockType) {
/* 207 */     return (blockType != null && blockInSet(set, blockType.getId()));
/*     */   }
/*     */   
/*     */   public boolean blockInSet(int set, @Nullable String blockTypeKey) {
/* 211 */     if (blockTypeKey == null) return false; 
/* 212 */     IntSet s = (IntSet)this.flattenedBlockSets.get(set);
/* 213 */     if (s == null) return false; 
/* 214 */     int index = BlockType.getAssetMap().getIndex(blockTypeKey);
/* 215 */     if (index == Integer.MIN_VALUE) throw new IllegalArgumentException("Unknown key! " + blockTypeKey); 
/* 216 */     return s.contains(index);
/*     */   }
/*     */   
/*     */   public static BlockSetModule getInstance() {
/* 220 */     return INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\blockset\BlockSetModule.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */