/*    */ package com.hypixel.hytale.server.core.asset.type.blocktype.config.bench;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*    */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*    */ import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
/*    */ import java.util.Collection;
/*    */ import java.util.Collections;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StructuralCraftingBench
/*    */   extends Bench
/*    */ {
/*    */   public static final BuilderCodec<StructuralCraftingBench> CODEC;
/*    */   private String[] headerCategories;
/*    */   private ObjectOpenHashSet<String> headerCategoryMap;
/*    */   private String[] sortedCategories;
/*    */   private Object2IntMap<String> categoryToIndexMap;
/*    */   private boolean allowBlockGroupCycling;
/*    */   private boolean alwaysShowInventoryHints;
/*    */   
/*    */   static {
/* 33 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(StructuralCraftingBench.class, StructuralCraftingBench::new, Bench.BASE_CODEC).append(new KeyedCodec("Categories", (Codec)new ArrayCodec((Codec)Codec.STRING, x$0 -> new String[x$0])), (bench, categories) -> bench.sortedCategories = categories, bench -> bench.sortedCategories).add()).append(new KeyedCodec("HeaderCategories", (Codec)new ArrayCodec((Codec)Codec.STRING, x$0 -> new String[x$0])), (bench, headerCategories) -> bench.headerCategories = headerCategories, bench -> bench.headerCategories).add()).append(new KeyedCodec("AlwaysShowInventoryHints", (Codec)Codec.BOOLEAN), (bench, alwaysShowInventoryHints) -> bench.alwaysShowInventoryHints = alwaysShowInventoryHints.booleanValue(), bench -> Boolean.valueOf(bench.alwaysShowInventoryHints)).add()).append(new KeyedCodec("AllowBlockGroupCycling", (Codec)Codec.BOOLEAN), (bench, allowBlockGroupCycling) -> bench.allowBlockGroupCycling = allowBlockGroupCycling.booleanValue(), bench -> Boolean.valueOf(bench.allowBlockGroupCycling)).add()).afterDecode(StructuralCraftingBench::processConfig)).build();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private void processConfig() {
/* 45 */     if (this.headerCategories != null) {
/* 46 */       this.headerCategoryMap = new ObjectOpenHashSet();
/* 47 */       Collections.addAll((Collection<? super String>)this.headerCategoryMap, this.headerCategories);
/*    */     } 
/*    */     
/* 50 */     if (this.sortedCategories == null) {
/*    */       return;
/*    */     }
/*    */     
/* 54 */     this.categoryToIndexMap = (Object2IntMap<String>)new Object2IntOpenHashMap();
/*    */     
/* 56 */     for (int i = 0; i < this.sortedCategories.length; i++) {
/* 57 */       this.categoryToIndexMap.put(this.sortedCategories[i], i);
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean isHeaderCategory(@Nonnull String category) {
/* 62 */     return (this.headerCategoryMap != null && this.headerCategoryMap.contains(category));
/*    */   }
/*    */   
/*    */   public int getCategoryIndex(@Nonnull String category) {
/* 66 */     return this.categoryToIndexMap.getOrDefault(category, 2147483647);
/*    */   }
/*    */   
/*    */   public boolean shouldAllowBlockGroupCycling() {
/* 70 */     return this.allowBlockGroupCycling;
/*    */   }
/*    */   
/*    */   public boolean shouldAlwaysShowInventoryHints() {
/* 74 */     return this.alwaysShowInventoryHints;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 79 */     return "StructuralCraftingBench{}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\blocktype\config\bench\StructuralCraftingBench.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */