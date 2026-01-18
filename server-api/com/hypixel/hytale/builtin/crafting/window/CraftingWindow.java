/*     */ package com.hypixel.hytale.builtin.crafting.window;
/*     */ 
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.hypixel.hytale.builtin.adventure.memories.MemoriesGameplayConfig;
/*     */ import com.hypixel.hytale.builtin.crafting.CraftingPlugin;
/*     */ import com.hypixel.hytale.builtin.crafting.component.CraftingManager;
/*     */ import com.hypixel.hytale.builtin.crafting.state.BenchState;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.protocol.SoundCategory;
/*     */ import com.hypixel.hytale.protocol.packets.window.CraftRecipeAction;
/*     */ import com.hypixel.hytale.protocol.packets.window.WindowType;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.bench.Bench;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.bench.CraftingBench;
/*     */ import com.hypixel.hytale.server.core.asset.type.gameplay.GameplayConfig;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.CraftingRecipe;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.SoundUtil;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public abstract class CraftingWindow
/*     */   extends BenchWindow
/*     */ {
/*     */   public static final int SET_BLOCK_SETTINGS = 6;
/*     */   protected static final String CRAFT_COMPLETED = "CraftCompleted";
/*     */   protected static final String CRAFT_COMPLETED_INSTANT = "CraftCompletedInstant";
/*     */   
/*     */   public CraftingWindow(@Nonnull WindowType windowType, BenchState benchState) {
/*  40 */     super(windowType, benchState);
/*     */     
/*  42 */     JsonArray categories = new JsonArray();
/*     */     
/*  44 */     Bench bench = this.bench; if (bench instanceof CraftingBench) { CraftingBench craftingBench = (CraftingBench)bench;
/*     */ 
/*     */       
/*  47 */       for (CraftingBench.BenchCategory benchCategory : craftingBench.getCategories()) {
/*  48 */         JsonObject category = new JsonObject();
/*  49 */         categories.add((JsonElement)category);
/*     */         
/*  51 */         category.addProperty("id", benchCategory.getId());
/*  52 */         category.addProperty("name", benchCategory.getName());
/*  53 */         category.addProperty("icon", benchCategory.getIcon());
/*     */         
/*  55 */         Set<String> recipes = CraftingPlugin.getAvailableRecipesForCategory(this.bench.getId(), benchCategory.getId());
/*  56 */         if (recipes != null) {
/*  57 */           JsonArray recipesArray = new JsonArray();
/*  58 */           for (String recipeId : recipes) {
/*  59 */             recipesArray.add(recipeId);
/*     */           }
/*  61 */           category.add("craftableRecipes", (JsonElement)recipesArray);
/*     */         } 
/*     */         
/*  64 */         if (benchCategory.getItemCategories() != null) {
/*     */           
/*  66 */           JsonArray itemCategories = new JsonArray();
/*  67 */           for (CraftingBench.BenchItemCategory benchItemCategory : benchCategory.getItemCategories()) {
/*  68 */             JsonObject itemCategory = new JsonObject();
/*  69 */             itemCategory.addProperty("id", benchItemCategory.getId());
/*  70 */             itemCategory.addProperty("icon", benchItemCategory.getIcon());
/*  71 */             itemCategory.addProperty("diagram", benchItemCategory.getDiagram());
/*  72 */             itemCategory.addProperty("slots", Integer.valueOf(benchItemCategory.getSlots()));
/*  73 */             itemCategory.addProperty("specialSlot", Boolean.valueOf(benchItemCategory.isSpecialSlot()));
/*  74 */             itemCategories.add((JsonElement)itemCategory);
/*     */           } 
/*  76 */           category.add("itemCategories", (JsonElement)itemCategories);
/*     */         } 
/*  78 */       }  this.windowData.add("categories", (JsonElement)categories); }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean onOpen0() {
/*  84 */     super.onOpen0();
/*     */     
/*  86 */     PlayerRef playerRef = getPlayerRef();
/*  87 */     Ref<EntityStore> ref = playerRef.getReference();
/*  88 */     Store<EntityStore> store = ref.getStore();
/*     */     
/*  90 */     GameplayConfig gameplayConfig = ((EntityStore)store.getExternalData()).getWorld().getGameplayConfig();
/*  91 */     MemoriesGameplayConfig memoriesConfig = MemoriesGameplayConfig.get(gameplayConfig);
/*  92 */     if (memoriesConfig != null) {
/*  93 */       int[] memoriesAmountPerLevel = memoriesConfig.getMemoriesAmountPerLevel();
/*  94 */       if (memoriesAmountPerLevel != null && memoriesAmountPerLevel.length > 1) {
/*  95 */         JsonArray memoriesPerLevel = new JsonArray();
/*  96 */         for (int i = 0; i < memoriesAmountPerLevel.length; i++) {
/*  97 */           memoriesPerLevel.add(Integer.valueOf(memoriesAmountPerLevel[i]));
/*     */         }
/*  99 */         this.windowData.add("memoriesPerLevel", (JsonElement)memoriesPerLevel);
/*     */       } 
/*     */     } 
/*     */     
/* 103 */     if (this.bench.getLocalOpenSoundEventIndex() != 0) {
/* 104 */       SoundUtil.playSoundEvent2d(ref, this.bench.getLocalOpenSoundEventIndex(), SoundCategory.UI, (ComponentAccessor)store);
/*     */     }
/*     */     
/* 107 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onClose0() {
/* 112 */     super.onClose0();
/*     */     
/* 114 */     PlayerRef playerRef = getPlayerRef();
/* 115 */     Ref<EntityStore> ref = playerRef.getReference();
/* 116 */     Store<EntityStore> store = ref.getStore();
/* 117 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*     */ 
/*     */     
/* 120 */     setBlockInteractionState(this.benchState.getTierStateName(), world, 6);
/*     */     
/* 122 */     if (this.bench.getLocalCloseSoundEventIndex() != 0) {
/* 123 */       SoundUtil.playSoundEvent2d(ref, this.bench.getLocalCloseSoundEventIndex(), SoundCategory.UI, (ComponentAccessor)store);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setBlockInteractionState(@Nonnull String state, @Nonnull World world, int setBlockSettings) {
/* 128 */     WorldChunk worldChunk = world.getChunk(ChunkUtil.indexChunkFromBlock(this.x, this.z));
/* 129 */     BlockType blockType = worldChunk.getBlockType(this.x, this.y, this.z);
/* 130 */     worldChunk.setBlockInteractionState(this.x, this.y, this.z, blockType, state, true);
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
/*     */   public static boolean craftSimpleItem(@Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull CraftingManager craftingManager, @Nonnull CraftRecipeAction action) {
/* 143 */     String recipeId = action.recipeId;
/* 144 */     int quantity = action.quantity;
/*     */     
/* 146 */     if (recipeId == null) return false;
/*     */     
/* 148 */     CraftingRecipe recipe = (CraftingRecipe)CraftingRecipe.getAssetMap().getAsset(recipeId);
/* 149 */     if (recipe == null) {
/* 150 */       PlayerRef playerRef = (PlayerRef)store.getComponent(ref, PlayerRef.getComponentType());
/*     */ 
/*     */       
/* 153 */       playerRef.getPacketHandler().disconnect("Attempted to craft unknown recipe!");
/* 154 */       return false;
/*     */     } 
/*     */     
/* 157 */     Player player = (Player)store.getComponent(ref, Player.getComponentType());
/*     */     
/* 159 */     craftingManager.craftItem(ref, (ComponentAccessor)store, recipe, quantity, (ItemContainer)player.getInventory().getCombinedBackpackStorageHotbar());
/* 160 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\crafting\window\CraftingWindow.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */