/*    */ package com.hypixel.hytale.builtin.tagset;
/*    */ import com.hypixel.hytale.assetstore.AssetMap;
/*    */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*    */ import com.hypixel.hytale.assetstore.AssetStore;
/*    */ import com.hypixel.hytale.assetstore.codec.AssetCodec;
/*    */ import com.hypixel.hytale.assetstore.map.IndexedLookupTableAssetMap;
/*    */ import com.hypixel.hytale.builtin.tagset.config.NPCGroup;
/*    */ import com.hypixel.hytale.server.core.asset.HytaleAssetStore;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction;
/*    */ import com.hypixel.hytale.server.core.plugin.JavaPlugin;
/*    */ import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*    */ import it.unimi.dsi.fastutil.ints.IntSet;
/*    */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*    */ import java.util.Map;
/*    */ import java.util.Objects;
/*    */ import java.util.concurrent.ConcurrentHashMap;
/*    */ import java.util.function.Function;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class TagSetPlugin extends JavaPlugin {
/*    */   public static TagSetPlugin get() {
/* 26 */     return instance;
/*    */   }
/*    */   private static TagSetPlugin instance;
/* 29 */   private final Map<Class<? extends TagSet>, TagSetLookup> lookups = new ConcurrentHashMap<>();
/*    */   
/*    */   public TagSetPlugin(@Nonnull JavaPluginInit init) {
/* 32 */     super(init);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void setup() {
/* 37 */     instance = this;
/*    */     
/* 39 */     AssetRegistry.register((AssetStore)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.builder(NPCGroup.class, (AssetMap)new IndexedLookupTableAssetMap(x$0 -> new NPCGroup[x$0]))
/* 40 */         .setPath("NPC/Groups"))
/* 41 */         .setCodec((AssetCodec)NPCGroup.CODEC))
/* 42 */         .setKeyFunction(NPCGroup::getId))
/* 43 */         .setReplaceOnRemove(NPCGroup::new))
/* 44 */         .loadsBefore(new Class[] { Interaction.class
/* 45 */           })).build());
/*    */     
/* 47 */     registerTagSetType(NPCGroup.class);
/*    */   }
/*    */   
/*    */   public <T extends TagSet> void registerTagSetType(Class<T> clazz) {
/* 51 */     if (isDisabled())
/* 52 */       return;  this.lookups.computeIfAbsent(clazz, c -> new TagSetLookup());
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static <T extends TagSet> TagSetLookup get(Class<T> clazz) {
/* 57 */     return Objects.<TagSetLookup>requireNonNull(instance.lookups.get(clazz), "Class is not registered with the TagSet module!");
/*    */   }
/*    */   
/*    */   public static class TagSetLookup
/*    */   {
/*    */     @Nonnull
/* 63 */     private Int2ObjectMap<IntSet> flattenedSets = Int2ObjectMaps.unmodifiable((Int2ObjectMap)new Int2ObjectOpenHashMap());
/*    */     
/*    */     public <T extends TagSet> void putAssetSets(@Nonnull Map<String, T> tagSetAssets, @Nonnull Object2IntMap<String> tagSetIndexMap, @Nonnull Object2IntMap<String> tagIndexMap) {
/* 66 */       TagSetLookupTable<T> lookupTable = new TagSetLookupTable<>(tagSetAssets, tagSetIndexMap, tagIndexMap);
/* 67 */       this.flattenedSets = Int2ObjectMaps.unmodifiable(lookupTable.getFlattenedSet());
/*    */     }
/*    */     
/*    */     public boolean tagInSet(int tagSet, int tagIndex) {
/* 71 */       IntSet set = (IntSet)this.flattenedSets.get(tagSet);
/* 72 */       if (set == null) {
/* 73 */         throw new IllegalArgumentException("Attempting to access a tagset which does not exist!");
/*    */       }
/* 75 */       return set.contains(tagIndex);
/*    */     }
/*    */     
/*    */     @Nullable
/*    */     public IntSet getSet(int tagSet) {
/* 80 */       return (IntSet)this.flattenedSets.get(tagSet);
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\tagset\TagSetPlugin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */