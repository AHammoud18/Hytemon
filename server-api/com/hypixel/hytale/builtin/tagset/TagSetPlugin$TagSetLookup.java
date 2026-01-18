/*    */ package com.hypixel.hytale.builtin.tagset;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*    */ import it.unimi.dsi.fastutil.ints.IntSet;
/*    */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TagSetLookup
/*    */ {
/*    */   @Nonnull
/* 63 */   private Int2ObjectMap<IntSet> flattenedSets = Int2ObjectMaps.unmodifiable((Int2ObjectMap)new Int2ObjectOpenHashMap());
/*    */   
/*    */   public <T extends TagSet> void putAssetSets(@Nonnull Map<String, T> tagSetAssets, @Nonnull Object2IntMap<String> tagSetIndexMap, @Nonnull Object2IntMap<String> tagIndexMap) {
/* 66 */     TagSetLookupTable<T> lookupTable = new TagSetLookupTable<>(tagSetAssets, tagSetIndexMap, tagIndexMap);
/* 67 */     this.flattenedSets = Int2ObjectMaps.unmodifiable(lookupTable.getFlattenedSet());
/*    */   }
/*    */   
/*    */   public boolean tagInSet(int tagSet, int tagIndex) {
/* 71 */     IntSet set = (IntSet)this.flattenedSets.get(tagSet);
/* 72 */     if (set == null) {
/* 73 */       throw new IllegalArgumentException("Attempting to access a tagset which does not exist!");
/*    */     }
/* 75 */     return set.contains(tagIndex);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public IntSet getSet(int tagSet) {
/* 80 */     return (IntSet)this.flattenedSets.get(tagSet);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\tagset\TagSetPlugin$TagSetLookup.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */