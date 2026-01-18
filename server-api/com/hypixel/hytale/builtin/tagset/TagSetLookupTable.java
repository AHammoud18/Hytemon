/*     */ package com.hypixel.hytale.builtin.tagset;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.ints.IntArrayList;
/*     */ import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
/*     */ import it.unimi.dsi.fastutil.ints.IntSet;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.function.IntConsumer;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class TagSetLookupTable<T extends TagSet> {
/*     */   @Nonnull
/*  16 */   private Int2ObjectMap<IntSet> tagMatcher = (Int2ObjectMap<IntSet>)new Int2ObjectOpenHashMap();
/*     */ 
/*     */   
/*     */   public TagSetLookupTable(@Nonnull Map<String, T> tagSetMap, @Nonnull Object2IntMap<String> tagSetIndexMap, @Nonnull Object2IntMap<String> tagIndexMap) {
/*  20 */     createTagMap(tagSetMap, tagSetIndexMap, tagIndexMap);
/*     */   }
/*     */   
/*     */   private void createTagMap(@Nonnull Map<String, T> tagSetMap, @Nonnull Object2IntMap<String> tagSetIndexMap, @Nonnull Object2IntMap<String> tagIndexMap) {
/*  24 */     IntArrayList path = new IntArrayList();
/*  25 */     tagSetMap.forEach((key, entry) -> {
/*     */           int id = tagSetIndexMap.getOrDefault(key, -1);
/*     */           if (id >= 0 && this.tagMatcher.containsKey(id)) {
/*     */             return;
/*     */           }
/*     */           try {
/*     */             createTagSet((T)entry, tagSetMap, tagSetIndexMap, tagIndexMap, path);
/*  32 */           } catch (IllegalStateException e) {
/*     */             throw new IllegalStateException(key + ": ", e);
/*     */           } 
/*     */           path.clear();
/*     */         });
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   private IntSet createTagSet(@Nonnull T tagSet, @Nonnull Map<String, T> tagSetMap, @Nonnull Object2IntMap<String> tagSetIndexMap, @Nonnull Object2IntMap<String> tagIndexMap, @Nonnull IntArrayList path) {
/*  41 */     IntOpenHashSet set = new IntOpenHashSet();
/*     */     
/*  43 */     int index = tagSetIndexMap.getInt(tagSet.getId());
/*  44 */     if (path.contains(index)) {
/*  45 */       throw new IllegalStateException("Cyclic reference to set detected: " + (String)tagSet.getId());
/*     */     }
/*  47 */     path.add(index);
/*  48 */     this.tagMatcher.put(index, set);
/*     */ 
/*     */     
/*  51 */     if (!tagIndexMap.isEmpty()) {
/*     */       
/*  53 */       String[] includedTagSets = tagSet.getIncludedTagSets();
/*  54 */       if (includedTagSets != null) {
/*  55 */         for (String tag : includedTagSets) {
/*  56 */           Objects.requireNonNull(set); consumeSet(tag, tagSetMap, tagSetIndexMap, tagIndexMap, path, set::addAll);
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/*  61 */       String[] excludedTagSets = tagSet.getExcludedTagSets();
/*  62 */       if (excludedTagSets != null) {
/*  63 */         for (String tag : excludedTagSets) {
/*  64 */           Objects.requireNonNull(set); consumeSet(tag, tagSetMap, tagSetIndexMap, tagIndexMap, path, set::removeAll);
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/*  69 */       String[] includedTags = tagSet.getIncludedTags();
/*  70 */       if (includedTags != null) {
/*  71 */         for (String tag : includedTags) {
/*  72 */           Objects.requireNonNull(set); consumeTag(tag, tagSet, tagIndexMap, set::add);
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/*  77 */       String[] excludedTags = tagSet.getExcludedTags();
/*  78 */       if (excludedTags != null) {
/*  79 */         for (String tag : excludedTags) {
/*  80 */           Objects.requireNonNull(set); consumeTag(tag, tagSet, tagIndexMap, set::remove);
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/*  85 */     return (IntSet)set;
/*     */   }
/*     */   
/*     */   private void consumeSet(String tag, @Nonnull Map<String, T> tagSetMap, @Nonnull Object2IntMap<String> tagSetIndexMap, @Nonnull Object2IntMap<String> tagIndexMap, @Nonnull IntArrayList path, @Nonnull Consumer<IntSet> predicate) {
/*  89 */     IntSet s = getOrCreateTagSet(tag, tagSetMap, tagSetIndexMap, tagIndexMap, path);
/*  90 */     if (s != null) {
/*  91 */       predicate.accept(s);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void consumeTag(@Nonnull String tag, @Nonnull T tagSet, @Nonnull Object2IntMap<String> tagIndexMap, @Nonnull IntConsumer predicate) {
/*  97 */     if (StringUtil.isGlobPattern(tag)) {
/*  98 */       ObjectIterator<Object2IntMap.Entry<String>> it = Object2IntMaps.fastIterator(tagIndexMap);
/*  99 */       while (it.hasNext()) {
/* 100 */         Object2IntMap.Entry<String> entry = (Object2IntMap.Entry<String>)it.next();
/* 101 */         if (StringUtil.isGlobMatching(tag, (String)entry.getKey())) predicate.accept(entry.getIntValue());
/*     */       
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/* 107 */     int index = tagIndexMap.getOrDefault(tag, -1);
/* 108 */     if (index >= 0) {
/* 109 */       predicate.accept(index);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 114 */     TagSetPlugin.get().getLogger().at(Level.WARNING).log("Tag Set '%s' references '%s' which is not a pattern and does not otherwise exist", tagSet.getId(), tag);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private IntSet getOrCreateTagSet(String identifier, @Nonnull Map<String, T> tagSetMap, @Nonnull Object2IntMap<String> tagSetIndexMap, @Nonnull Object2IntMap<String> tagIndexMap, @Nonnull IntArrayList path) {
/* 120 */     int tagSetIndex = tagSetIndexMap.getOrDefault(identifier, -1);
/* 121 */     IntSet intSet = null;
/* 122 */     if (tagSetIndex >= 0 && this.tagMatcher.containsKey(tagSetIndex)) {
/* 123 */       if (path.contains(tagSetIndex)) {
/* 124 */         throw new IllegalStateException("Cyclic reference to set detected: " + identifier);
/*     */       }
/* 126 */       path.add(tagSetIndex);
/* 127 */       intSet = (IntSet)this.tagMatcher.get(tagSetIndex);
/*     */     } else {
/*     */       
/* 130 */       TagSet tagSet = (TagSet)tagSetMap.get(identifier);
/* 131 */       if (tagSet != null) {
/* 132 */         intSet = createTagSet((T)tagSet, tagSetMap, tagSetIndexMap, tagIndexMap, path);
/*     */       } else {
/*     */         
/* 135 */         TagSetPlugin.get().getLogger().at(Level.WARNING).log("Creating tag sets: Tag Set '%s' does not exist, but is being referenced as a tag", identifier);
/*     */       } 
/*     */     } 
/* 138 */     path.removeInt(path.size() - 1);
/* 139 */     return intSet;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Int2ObjectMap<IntSet> getFlattenedSet() {
/* 144 */     return this.tagMatcher;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\tagset\TagSetLookupTable.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */