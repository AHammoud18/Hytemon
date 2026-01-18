/*     */ package com.hypixel.hytale.builtin.crafting;
/*     */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*     */ import com.hypixel.hytale.assetstore.event.LoadedAssetsEvent;
/*     */ import com.hypixel.hytale.assetstore.event.RemovedAssetsEvent;
/*     */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*     */ import com.hypixel.hytale.builtin.crafting.component.CraftingManager;
/*     */ import com.hypixel.hytale.builtin.crafting.interaction.LearnRecipeInteraction;
/*     */ import com.hypixel.hytale.builtin.crafting.interaction.OpenBenchPageInteraction;
/*     */ import com.hypixel.hytale.builtin.crafting.interaction.OpenProcessingBenchInteraction;
/*     */ import com.hypixel.hytale.builtin.crafting.state.BenchState;
/*     */ import com.hypixel.hytale.builtin.crafting.state.ProcessingBenchState;
/*     */ import com.hypixel.hytale.builtin.crafting.system.PlayerCraftingSystems;
/*     */ import com.hypixel.hytale.common.util.ArrayUtil;
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.ComponentRegistryProxy;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.ISystem;
/*     */ import com.hypixel.hytale.protocol.BenchRequirement;
/*     */ import com.hypixel.hytale.protocol.BenchType;
/*     */ import com.hypixel.hytale.protocol.CraftingRecipe;
/*     */ import com.hypixel.hytale.protocol.ItemResourceType;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.bench.Bench;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.bench.BenchUpgradeRequirement;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.CraftingRecipe;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.Item;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.entity.entities.player.data.PlayerConfigData;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.inventory.MaterialQuantity;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.RootInteraction;
/*     */ import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.meta.BlockStateRegistry;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class CraftingPlugin extends JavaPlugin {
/*  53 */   private static final Map<String, BenchRecipeRegistry> registries = (Map<String, BenchRecipeRegistry>)new Object2ObjectOpenHashMap(); private static CraftingPlugin instance;
/*  54 */   private static final Map<String, String[]> itemGeneratedRecipes = (Map<String, String[]>)new Object2ObjectOpenHashMap();
/*     */   private ComponentType<EntityStore, CraftingManager> craftingManagerComponentType;
/*     */   
/*     */   public CraftingPlugin(@Nonnull JavaPluginInit init) {
/*  58 */     super(init);
/*  59 */     instance = this;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static Set<String> getAvailableRecipesForCategory(String benchId, String benchCategoryId) {
/*  64 */     BenchRecipeRegistry benchRecipeRegistry = registries.get(benchId);
/*  65 */     if (benchRecipeRegistry == null) {
/*  66 */       return null;
/*     */     }
/*  68 */     return benchRecipeRegistry.getRecipesForCategory(benchCategoryId);
/*     */   }
/*     */   
/*     */   public static boolean isValidCraftingMaterialForBench(BenchState benchState, ItemStack itemStack) {
/*  72 */     BenchRecipeRegistry benchRecipeRegistry = registries.get(benchState.getBench().getId());
/*  73 */     if (benchRecipeRegistry == null) {
/*  74 */       return false;
/*     */     }
/*  76 */     return benchRecipeRegistry.isValidCraftingMaterial(itemStack);
/*     */   }
/*     */   
/*     */   public static boolean isValidUpgradeMaterialForBench(BenchState benchState, ItemStack itemStack) {
/*  80 */     BenchUpgradeRequirement nextLevelUpgradeMaterials = benchState.getNextLevelUpgradeMaterials();
/*  81 */     if (nextLevelUpgradeMaterials == null) return false;
/*     */     
/*  83 */     for (MaterialQuantity upgradeMaterial : nextLevelUpgradeMaterials.getInput()) {
/*  84 */       if (itemStack.getItemId().equals(upgradeMaterial.getItemId())) return true; 
/*  85 */       ItemResourceType[] resourceTypeId = itemStack.getItem().getResourceTypes();
/*  86 */       if (resourceTypeId != null)
/*  87 */         for (ItemResourceType resTypeId : resourceTypeId) {
/*  88 */           if (resTypeId.id.equals(upgradeMaterial.getResourceTypeId())) return true;
/*     */         
/*     */         }  
/*     */     } 
/*  92 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setup() {
/*  97 */     AssetRegistry.getAssetStore(Interaction.class).loadAssets("Hytale:Hytale", List.of(OpenBenchPageInteraction.SIMPLE_CRAFTING, OpenBenchPageInteraction.DIAGRAM_CRAFTING, OpenBenchPageInteraction.STRUCTURAL_CRAFTING));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 102 */     AssetRegistry.getAssetStore(RootInteraction.class).loadAssets("Hytale:Hytale", List.of(OpenBenchPageInteraction.SIMPLE_CRAFTING_ROOT, OpenBenchPageInteraction.DIAGRAM_CRAFTING_ROOT, OpenBenchPageInteraction.STRUCTURAL_CRAFTING_ROOT));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 108 */     ComponentRegistryProxy<EntityStore> entityStoreRegistry = getEntityStoreRegistry();
/* 109 */     this.craftingManagerComponentType = entityStoreRegistry.registerComponent(CraftingManager.class, CraftingManager::new);
/* 110 */     entityStoreRegistry.registerSystem((ISystem)new PlayerCraftingSystems.PlayerCraftingSystem(this.craftingManagerComponentType));
/* 111 */     entityStoreRegistry.registerSystem((ISystem)new PlayerCraftingSystems.CraftingManagerAddSystem(this.craftingManagerComponentType));
/*     */     
/* 113 */     getCodecRegistry(Interaction.CODEC)
/* 114 */       .register("OpenBenchPage", OpenBenchPageInteraction.class, OpenBenchPageInteraction.CODEC)
/* 115 */       .register("OpenProcessingBench", OpenProcessingBenchInteraction.class, OpenProcessingBenchInteraction.CODEC);
/*     */     
/* 117 */     Bench.registerRootInteraction(BenchType.Crafting, OpenBenchPageInteraction.SIMPLE_CRAFTING_ROOT);
/* 118 */     Bench.registerRootInteraction(BenchType.DiagramCrafting, OpenBenchPageInteraction.DIAGRAM_CRAFTING_ROOT);
/* 119 */     Bench.registerRootInteraction(BenchType.StructuralCrafting, OpenBenchPageInteraction.STRUCTURAL_CRAFTING_ROOT);
/*     */     
/* 121 */     BlockStateRegistry blockStateRegistry = getBlockStateRegistry();
/* 122 */     blockStateRegistry.registerBlockState(ProcessingBenchState.class, "processingBench", ProcessingBenchState.CODEC);
/* 123 */     blockStateRegistry.registerBlockState(BenchState.class, "crafting", (Codec)BenchState.CODEC);
/*     */     
/* 125 */     Window.CLIENT_REQUESTABLE_WINDOW_TYPES.put(WindowType.PocketCrafting, com.hypixel.hytale.builtin.crafting.window.FieldCraftingWindow::new);
/*     */     
/* 127 */     getEventRegistry().register(LoadedAssetsEvent.class, CraftingRecipe.class, CraftingPlugin::onRecipeLoad);
/* 128 */     getEventRegistry().register(RemovedAssetsEvent.class, CraftingRecipe.class, CraftingPlugin::onRecipeRemove);
/*     */     
/* 130 */     getEventRegistry().register(LoadedAssetsEvent.class, Item.class, CraftingPlugin::onItemAssetLoad);
/* 131 */     getEventRegistry().register(RemovedAssetsEvent.class, Item.class, CraftingPlugin::onItemAssetRemove);
/*     */     
/* 133 */     Interaction.CODEC.register("LearnRecipe", LearnRecipeInteraction.class, LearnRecipeInteraction.CODEC);
/*     */     
/* 135 */     CommandManager.get().registerSystemCommand((AbstractCommand)new RecipeCommand());
/* 136 */     entityStoreRegistry.registerSystem((ISystem)new PlayerAddedSystem());
/*     */   }
/*     */   
/*     */   private static void onItemAssetLoad(LoadedAssetsEvent<String, Item, DefaultAssetMap<String, Item>> event) {
/* 140 */     ObjectArrayList objectArrayList = new ObjectArrayList();
/*     */     
/* 142 */     for (Item item : event.getLoadedAssets().values()) {
/* 143 */       if (!item.hasRecipesToGenerate()) {
/*     */         continue;
/*     */       }
/* 146 */       ObjectArrayList objectArrayList1 = new ObjectArrayList();
/* 147 */       item.collectRecipesToGenerate((Collection)objectArrayList1);
/*     */       
/* 149 */       ObjectArrayList<String> objectArrayList2 = new ObjectArrayList();
/* 150 */       for (CraftingRecipe generatedRecipe : objectArrayList1) {
/* 151 */         String id = generatedRecipe.getId();
/* 152 */         objectArrayList2.add(id);
/*     */       } 
/*     */       
/* 155 */       itemGeneratedRecipes.put(item.getId(), (String[])objectArrayList2.toArray(x$0 -> new String[x$0]));
/* 156 */       objectArrayList.addAll((Collection)objectArrayList1);
/*     */     } 
/*     */     
/* 159 */     if (!objectArrayList.isEmpty()) {
/* 160 */       CraftingRecipe.getAssetStore().loadAssets("Hytale:Hytale", (List)objectArrayList);
/*     */     }
/*     */   }
/*     */   
/*     */   private static void onItemAssetRemove(@Nonnull RemovedAssetsEvent<String, Item, DefaultAssetMap<String, Item>> event) {
/* 165 */     for (String id : event.getRemovedAssets()) {
/* 166 */       String[] generatedRecipes = itemGeneratedRecipes.get(id);
/* 167 */       if (generatedRecipes == null) {
/*     */         continue;
/*     */       }
/*     */       
/* 171 */       CraftingRecipe.getAssetStore().removeAssets(List.of(generatedRecipes));
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void onRecipeLoad(LoadedAssetsEvent<String, CraftingRecipe, DefaultAssetMap<String, CraftingRecipe>> event) {
/* 176 */     for (CraftingRecipe recipe : event.getLoadedAssets().values()) {
/* 177 */       for (BenchRecipeRegistry registry : registries.values()) {
/* 178 */         registry.removeRecipe(recipe.getId());
/*     */       }
/*     */       
/* 181 */       if (recipe.getBenchRequirement() == null) {
/*     */         continue;
/*     */       }
/*     */       
/* 185 */       for (BenchRequirement benchRequirement : recipe.getBenchRequirement()) {
/* 186 */         BenchRecipeRegistry benchRecipeRegistry = registries.computeIfAbsent(benchRequirement.id, BenchRecipeRegistry::new);
/* 187 */         benchRecipeRegistry.addRecipe(benchRequirement, recipe);
/*     */       } 
/*     */     } 
/*     */     
/* 191 */     computeBenchRecipeRegistries();
/*     */   }
/*     */   
/*     */   private static void onRecipeRemove(RemovedAssetsEvent<String, CraftingRecipe, DefaultAssetMap<String, CraftingRecipe>> event) {
/* 195 */     for (String removedRecipeId : event.getRemovedAssets()) {
/* 196 */       for (BenchRecipeRegistry registry : registries.values()) {
/* 197 */         registry.removeRecipe(removedRecipeId);
/*     */       }
/*     */     } 
/*     */     
/* 201 */     computeBenchRecipeRegistries();
/*     */   }
/*     */   
/*     */   private static void computeBenchRecipeRegistries() {
/* 205 */     for (BenchRecipeRegistry registry : registries.values()) {
/* 206 */       registry.recompute();
/*     */     }
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static List<CraftingRecipe> getBenchRecipes(@Nonnull Bench bench) {
/* 212 */     return getBenchRecipes(bench.getType(), bench.getId());
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static List<CraftingRecipe> getBenchRecipes(BenchType benchType, String name) {
/* 217 */     return getBenchRecipes(benchType, name, (String)null);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static List<CraftingRecipe> getBenchRecipes(BenchType benchType, String benchId, @Nullable String category) {
/* 222 */     BenchRecipeRegistry registry = registries.get(benchId);
/* 223 */     if (registry == null) {
/* 224 */       return List.of();
/*     */     }
/*     */     
/* 227 */     ObjectArrayList<CraftingRecipe> objectArrayList = new ObjectArrayList();
/*     */ 
/*     */     
/* 230 */     for (CraftingRecipe recipe : registry.getAllRecipes()) {
/* 231 */       BenchRequirement[] benchRequirement = recipe.getBenchRequirement();
/* 232 */       if (benchRequirement != null)
/*     */       {
/* 234 */         for (BenchRequirement requirement : benchRequirement) {
/* 235 */           if (requirement.type == benchType && requirement.id.equals(benchId) && (category == null || 
/* 236 */             hasCategory(recipe, category))) {
/* 237 */             objectArrayList.add(recipe);
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/* 243 */     return (List<CraftingRecipe>)objectArrayList;
/*     */   }
/*     */   
/*     */   private static boolean hasCategory(@Nonnull CraftingRecipe recipe, String category) {
/* 247 */     for (BenchRequirement benchRequirement : recipe.getBenchRequirement()) {
/* 248 */       if (benchRequirement.categories != null && ArrayUtil.contains((Object[])benchRequirement.categories, category)) {
/* 249 */         return true;
/*     */       }
/*     */     } 
/* 252 */     return false;
/*     */   }
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
/*     */   public static boolean learnRecipe(@Nonnull Ref<EntityStore> ref, @Nonnull String recipeId, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 266 */     Player playerComponent = (Player)componentAccessor.getComponent(ref, Player.getComponentType());
/* 267 */     assert playerComponent != null;
/*     */     
/* 269 */     PlayerConfigData playerConfigData = playerComponent.getPlayerConfigData();
/*     */ 
/*     */     
/* 272 */     Set<String> knownRecipes = new HashSet<>(playerConfigData.getKnownRecipes());
/* 273 */     if (knownRecipes.add(recipeId)) {
/* 274 */       playerConfigData.setKnownRecipes(knownRecipes);
/* 275 */       sendKnownRecipes(ref, componentAccessor);
/* 276 */       return true;
/*     */     } 
/* 278 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean forgetRecipe(@Nonnull Ref<EntityStore> ref, @Nonnull String itemId, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 290 */     Player playerComponent = (Player)componentAccessor.getComponent(ref, Player.getComponentType());
/* 291 */     assert playerComponent != null;
/*     */     
/* 293 */     PlayerConfigData playerConfigData = playerComponent.getPlayerConfigData();
/*     */ 
/*     */     
/* 296 */     ObjectOpenHashSet objectOpenHashSet = new ObjectOpenHashSet(playerConfigData.getKnownRecipes());
/* 297 */     if (objectOpenHashSet.remove(itemId)) {
/* 298 */       playerConfigData.setKnownRecipes((Set)objectOpenHashSet);
/* 299 */       sendKnownRecipes(ref, componentAccessor);
/* 300 */       return true;
/*     */     } 
/* 302 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void sendKnownRecipes(@Nonnull Ref<EntityStore> ref, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 312 */     PlayerRef playerRefComponent = (PlayerRef)componentAccessor.getComponent(ref, PlayerRef.getComponentType());
/* 313 */     assert playerRefComponent != null;
/*     */     
/* 315 */     Player playerComponent = (Player)componentAccessor.getComponent(ref, Player.getComponentType());
/* 316 */     assert playerComponent != null;
/*     */     
/* 318 */     PlayerConfigData playerConfigData = playerComponent.getPlayerConfigData();
/* 319 */     DefaultAssetMap<String, Item> itemAssetMap = Item.getAssetMap();
/*     */     
/* 321 */     Object2ObjectOpenHashMap<String, CraftingRecipe> object2ObjectOpenHashMap = new Object2ObjectOpenHashMap();
/* 322 */     for (String id : playerConfigData.getKnownRecipes()) {
/* 323 */       Item item = (Item)itemAssetMap.getAsset(id);
/* 324 */       if (item == null)
/*     */         continue; 
/* 326 */       for (BenchRecipeRegistry registry : registries.values()) {
/* 327 */         Iterable<String> incomingRecipes = registry.getIncomingRecipesForItem(item.getId());
/*     */         
/* 329 */         for (String recipeId : incomingRecipes) {
/* 330 */           CraftingRecipe recipe = (CraftingRecipe)CraftingRecipe.getAssetMap().getAsset(recipeId);
/* 331 */           if (recipe == null) {
/*     */             continue;
/*     */           }
/*     */           
/* 335 */           object2ObjectOpenHashMap.put(id, recipe.toPacket(id));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 340 */     playerRefComponent.getPacketHandler().writeNoCache((Packet)new UpdateKnownRecipes((Map)object2ObjectOpenHashMap));
/*     */   }
/*     */   
/*     */   public ComponentType<EntityStore, CraftingManager> getCraftingManagerComponentType() {
/* 344 */     return this.craftingManagerComponentType;
/*     */   }
/*     */   
/*     */   public static CraftingPlugin get() {
/* 348 */     return instance;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class PlayerAddedSystem
/*     */     extends RefSystem<EntityStore>
/*     */   {
/* 355 */     private static final Query<EntityStore> QUERY = (Query<EntityStore>)Archetype.of(new ComponentType[] { Player.getComponentType(), PlayerRef.getComponentType() });
/*     */ 
/*     */     
/*     */     public Query<EntityStore> getQuery() {
/* 359 */       return QUERY;
/*     */     }
/*     */ 
/*     */     
/*     */     public void onEntityAdded(@Nonnull Ref<EntityStore> ref, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 364 */       CraftingPlugin.sendKnownRecipes(ref, (ComponentAccessor<EntityStore>)commandBuffer);
/*     */     }
/*     */     
/*     */     public void onEntityRemove(@Nonnull Ref<EntityStore> ref, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {}
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\crafting\CraftingPlugin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */