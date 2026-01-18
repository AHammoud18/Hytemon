/*     */ package com.hypixel.hytale.server.core.modules.blockset;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.map.BlockTypeAssetMap;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.Item;
/*     */ import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
/*     */ import it.unimi.dsi.fastutil.ints.IntSet;
/*     */ import it.unimi.dsi.fastutil.ints.IntSets;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectMaps;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockSetLookupTable
/*     */ {
/*     */   @Nonnull
/*     */   private final Object2ObjectMap<String, IntSet> blockNameIdMap;
/*     */   @Nonnull
/*     */   private final Object2ObjectMap<String, IntSet> groupNameIdMap;
/*     */   @Nonnull
/*     */   private final Object2ObjectMap<String, IntSet> hitboxNameIdMap;
/*     */   @Nonnull
/*     */   private final Object2ObjectMap<String, IntSet> categoryIdMap;
/*     */   
/*     */   public BlockSetLookupTable(@Nonnull Map<String, BlockType> blockTypeMap) {
/*  41 */     Object2ObjectOpenHashMap object2ObjectOpenHashMap1 = new Object2ObjectOpenHashMap();
/*  42 */     Object2ObjectOpenHashMap object2ObjectOpenHashMap2 = new Object2ObjectOpenHashMap();
/*  43 */     Object2ObjectOpenHashMap object2ObjectOpenHashMap3 = new Object2ObjectOpenHashMap();
/*  44 */     Object2ObjectOpenHashMap object2ObjectOpenHashMap4 = new Object2ObjectOpenHashMap();
/*     */     
/*  46 */     BlockTypeAssetMap<String, BlockType> assetMap = BlockType.getAssetMap();
/*  47 */     blockTypeMap.keySet().forEach(blockName -> {
/*     */           int index = assetMap.getIndex(blockName);
/*     */ 
/*     */           
/*     */           if (index == Integer.MIN_VALUE) {
/*     */             throw new IllegalArgumentException("Unknown key! " + blockName);
/*     */           }
/*     */           
/*     */           ((IntSet)blockNameIdMap.computeIfAbsent(blockName, ())).add(index);
/*     */         });
/*     */     
/*  58 */     blockTypeMap.forEach((blockTypeKey, blockType) -> {
/*     */           String group = blockType.getGroup();
/*     */ 
/*     */           
/*     */           if (group != null && !group.isEmpty()) {
/*     */             int index = assetMap.getIndex(blockTypeKey);
/*     */             
/*     */             if (index == Integer.MIN_VALUE) {
/*     */               throw new IllegalArgumentException("Unknown key! " + blockTypeKey);
/*     */             }
/*     */             
/*     */             ((IntSet)groupNameIdMap.computeIfAbsent(group, ())).add(index);
/*     */           } 
/*     */           
/*     */           String hitboxType = blockType.getHitboxType();
/*     */           
/*     */           if (hitboxType != null && !hitboxType.isEmpty()) {
/*     */             int index = hitboxType.indexOf('|');
/*     */             
/*     */             if (index != 0) {
/*     */               if (index > 0) {
/*     */                 hitboxType = hitboxType.substring(0, index);
/*     */               }
/*     */               
/*     */               int index1 = assetMap.getIndex(blockTypeKey);
/*     */               
/*     */               if (index1 == Integer.MIN_VALUE) {
/*     */                 throw new IllegalArgumentException("Unknown key! " + blockTypeKey);
/*     */               }
/*     */               
/*     */               ((IntSet)hitboxNameIdMap.computeIfAbsent(hitboxType, ())).add(index1);
/*     */             } 
/*     */           } 
/*     */           
/*     */           String name = blockType.getId();
/*     */           
/*     */           Item item = (Item)Item.getAssetMap().getAsset(name);
/*     */           
/*     */           if (item != null) {
/*     */             String[] categories = item.getCategories();
/*     */             
/*     */             if (categories != null) {
/*     */               for (String category : categories) {
/*     */                 int index = assetMap.getIndex(blockTypeKey);
/*     */                 
/*     */                 if (index == Integer.MIN_VALUE) {
/*     */                   throw new IllegalArgumentException("Unknown key! " + blockTypeKey);
/*     */                 }
/*     */                 
/*     */                 ((IntSet)categoryIdMap.computeIfAbsent(category, ())).add(index);
/*     */               } 
/*     */             }
/*     */           } 
/*     */         });
/*     */     
/* 113 */     object2ObjectOpenHashMap1.replaceAll((s, tIntSet) -> {
/*     */           ((IntOpenHashSet)tIntSet).trim();
/*     */           return IntSets.unmodifiable(tIntSet);
/*     */         });
/* 117 */     object2ObjectOpenHashMap2.replaceAll((s, tIntSet) -> {
/*     */           ((IntOpenHashSet)tIntSet).trim();
/*     */           return IntSets.unmodifiable(tIntSet);
/*     */         });
/* 121 */     object2ObjectOpenHashMap3.replaceAll((s, tIntSet) -> {
/*     */           ((IntOpenHashSet)tIntSet).trim();
/*     */           return IntSets.unmodifiable(tIntSet);
/*     */         });
/* 125 */     object2ObjectOpenHashMap4.replaceAll((s, tIntSet) -> {
/*     */           ((IntOpenHashSet)tIntSet).trim();
/*     */           
/*     */           return IntSets.unmodifiable(tIntSet);
/*     */         });
/* 130 */     this.blockNameIdMap = Object2ObjectMaps.unmodifiable((Object2ObjectMap)object2ObjectOpenHashMap1);
/* 131 */     this.groupNameIdMap = Object2ObjectMaps.unmodifiable((Object2ObjectMap)object2ObjectOpenHashMap2);
/* 132 */     this.hitboxNameIdMap = Object2ObjectMaps.unmodifiable((Object2ObjectMap)object2ObjectOpenHashMap3);
/* 133 */     this.categoryIdMap = Object2ObjectMaps.unmodifiable((Object2ObjectMap)object2ObjectOpenHashMap4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addAll(@Nonnull IntSet result) {
/* 141 */     Objects.requireNonNull(result); this.blockNameIdMap.values().forEach(result::addAll);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Object2ObjectMap<String, IntSet> getBlockNameIdMap() {
/* 146 */     return this.blockNameIdMap;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Object2ObjectMap<String, IntSet> getGroupNameIdMap() {
/* 151 */     return this.groupNameIdMap;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Object2ObjectMap<String, IntSet> getHitboxNameIdMap() {
/* 156 */     return this.hitboxNameIdMap;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Object2ObjectMap<String, IntSet> getCategoryIdMap() {
/* 161 */     return this.categoryIdMap;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 165 */     return (this.blockNameIdMap.isEmpty() && this.groupNameIdMap.isEmpty() && this.hitboxNameIdMap.isEmpty() && this.categoryIdMap.isEmpty());
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\blockset\BlockSetLookupTable.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */