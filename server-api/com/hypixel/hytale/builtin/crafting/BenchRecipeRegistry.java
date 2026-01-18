/*     */ package com.hypixel.hytale.builtin.crafting;
/*     */ import com.hypixel.hytale.protocol.BenchRequirement;
/*     */ import com.hypixel.hytale.protocol.ItemResourceType;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.CraftingRecipe;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.inventory.MaterialQuantity;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
/*     */ import java.util.Collections;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class BenchRecipeRegistry {
/*  18 */   private final Map<String, Set<String>> categoryMap = (Map<String, Set<String>>)new Object2ObjectOpenHashMap(); private final String benchId;
/*  19 */   private final Map<String, Set<String>> itemToIncomingRecipe = (Map<String, Set<String>>)new Object2ObjectOpenHashMap();
/*  20 */   private final Set<String> uncategorizedRecipes = (Set<String>)new ObjectOpenHashSet();
/*     */   
/*  22 */   private final Set<String> allMaterialIds = (Set<String>)new ObjectOpenHashSet();
/*  23 */   private final Set<String> allMaterialResourceType = (Set<String>)new ObjectOpenHashSet();
/*     */   
/*     */   public BenchRecipeRegistry(String benchId) {
/*  26 */     this.benchId = benchId;
/*     */   }
/*     */   
/*     */   public Iterable<String> getIncomingRecipesForItem(@Nonnull String itemId) {
/*  30 */     Set<String> recipes = this.itemToIncomingRecipe.get(itemId);
/*     */     
/*  32 */     if (recipes == null) {
/*  33 */       return Collections.emptySet();
/*     */     }
/*     */     
/*  36 */     return recipes;
/*     */   }
/*     */   
/*     */   public void removeRecipe(@Nonnull String id) {
/*  40 */     this.uncategorizedRecipes.remove(id);
/*  41 */     for (Map.Entry<String, Set<String>> entry : this.categoryMap.entrySet()) {
/*  42 */       ((Set)entry.getValue()).remove(id);
/*     */     }
/*     */   }
/*     */   
/*     */   public void addRecipe(@Nonnull BenchRequirement benchRequirement, @Nonnull CraftingRecipe recipe) {
/*  47 */     if (benchRequirement.categories == null || benchRequirement.categories.length == 0) {
/*  48 */       this.uncategorizedRecipes.add(recipe.getId());
/*     */     } else {
/*  50 */       for (String category : benchRequirement.categories) {
/*  51 */         ((Set<String>)this.categoryMap.computeIfAbsent(category, k -> new ObjectOpenHashSet())).add(recipe.getId());
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public CraftingRecipe[] getAllRecipes() {
/*  57 */     ObjectOpenHashSet<String> objectOpenHashSet = new ObjectOpenHashSet(this.uncategorizedRecipes);
/*     */     
/*  59 */     for (Set<String> recipes : this.categoryMap.values()) {
/*  60 */       objectOpenHashSet.addAll(recipes);
/*     */     }
/*     */     
/*  63 */     ObjectArrayList<CraftingRecipe> objectArrayList = new ObjectArrayList(objectOpenHashSet.size());
/*  64 */     for (String recipeId : objectOpenHashSet) {
/*  65 */       CraftingRecipe recipe = (CraftingRecipe)CraftingRecipe.getAssetMap().getAsset(recipeId);
/*  66 */       if (recipe != null) {
/*  67 */         objectArrayList.add(recipe);
/*     */       }
/*     */     } 
/*     */     
/*  71 */     return (CraftingRecipe[])objectArrayList.toArray(x$0 -> new CraftingRecipe[x$0]);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Set<String> getRecipesForCategory(@Nonnull String benchCategoryId) {
/*  76 */     return this.categoryMap.get(benchCategoryId);
/*     */   }
/*     */   
/*     */   public void recompute() {
/*  80 */     this.allMaterialIds.clear();
/*  81 */     this.allMaterialResourceType.clear();
/*  82 */     this.itemToIncomingRecipe.clear();
/*     */     
/*  84 */     for (Set<String> recipes : this.categoryMap.values()) {
/*  85 */       extractMaterialFromRecipes(recipes);
/*     */     }
/*     */     
/*  88 */     extractMaterialFromRecipes(this.uncategorizedRecipes);
/*     */   }
/*     */   
/*     */   private void extractMaterialFromRecipes(Set<String> recipes) {
/*  92 */     for (String recipeId : recipes) {
/*  93 */       CraftingRecipe recipe = (CraftingRecipe)CraftingRecipe.getAssetMap().getAsset(recipeId);
/*  94 */       if (recipe == null)
/*     */         continue; 
/*  96 */       BenchRequirement[] benchRequirements = recipe.getBenchRequirement();
/*  97 */       if (benchRequirements == null)
/*     */         continue; 
/*  99 */       boolean matchesRegistry = false;
/* 100 */       for (BenchRequirement requirement : benchRequirements) {
/* 101 */         if (requirement.id.equals(this.benchId)) {
/* 102 */           matchesRegistry = true;
/*     */           break;
/*     */         } 
/*     */       } 
/* 106 */       if (!matchesRegistry)
/*     */         continue; 
/* 108 */       for (MaterialQuantity material : recipe.getInput()) {
/* 109 */         if (material.getItemId() != null) this.allMaterialIds.add(material.getItemId()); 
/* 110 */         if (material.getResourceTypeId() != null) this.allMaterialResourceType.add(material.getResourceTypeId());
/*     */       
/*     */       } 
/* 113 */       for (MaterialQuantity output : recipe.getOutputs()) {
/* 114 */         ((Set<String>)this.itemToIncomingRecipe.computeIfAbsent(output.getItemId(), k -> new ObjectOpenHashSet())).add(recipeId);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isValidCraftingMaterial(@Nonnull ItemStack itemStack) {
/* 120 */     if (this.allMaterialIds.contains(itemStack.getItemId())) {
/* 121 */       return true;
/*     */     }
/*     */     
/* 124 */     ItemResourceType[] resourceTypeId = itemStack.getItem().getResourceTypes();
/*     */     
/* 126 */     if (resourceTypeId != null) {
/* 127 */       for (ItemResourceType resTypeId : resourceTypeId) {
/* 128 */         if (this.allMaterialResourceType.contains(resTypeId.id)) {
/* 129 */           return true;
/*     */         }
/*     */       } 
/*     */     }
/* 133 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 138 */     if (o == null || getClass() != o.getClass()) return false; 
/* 139 */     BenchRecipeRegistry that = (BenchRecipeRegistry)o;
/* 140 */     return (Objects.equals(this.benchId, that.benchId) && Objects.equals(this.categoryMap, that.categoryMap) && Objects.equals(this.uncategorizedRecipes, that.uncategorizedRecipes) && Objects.equals(this.allMaterialIds, that.allMaterialIds) && Objects.equals(this.allMaterialResourceType, that.allMaterialResourceType));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 145 */     return Objects.hash(new Object[] { this.benchId, this.categoryMap, this.uncategorizedRecipes, this.allMaterialIds, this.allMaterialResourceType });
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 150 */     return "BenchRecipeRegistry{benchId='" + this.benchId + "', categoryMap=" + String.valueOf(this.categoryMap) + ", uncategorizedRecipes=" + String.valueOf(this.uncategorizedRecipes) + ", allMaterialIds=" + String.valueOf(this.allMaterialIds) + ", allMaterialResourceType=" + String.valueOf(this.allMaterialResourceType) + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\crafting\BenchRecipeRegistry.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */