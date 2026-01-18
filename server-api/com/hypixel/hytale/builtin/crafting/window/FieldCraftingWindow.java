/*    */ package com.hypixel.hytale.builtin.crafting.window;
/*    */ 
/*    */ import com.google.gson.JsonArray;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.hypixel.hytale.builtin.adventure.memories.MemoriesPlugin;
/*    */ import com.hypixel.hytale.builtin.crafting.CraftingPlugin;
/*    */ import com.hypixel.hytale.builtin.crafting.component.CraftingManager;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.protocol.BenchType;
/*    */ import com.hypixel.hytale.protocol.SoundCategory;
/*    */ import com.hypixel.hytale.protocol.packets.window.CraftRecipeAction;
/*    */ import com.hypixel.hytale.protocol.packets.window.WindowAction;
/*    */ import com.hypixel.hytale.protocol.packets.window.WindowType;
/*    */ import com.hypixel.hytale.server.core.asset.type.item.config.FieldcraftCategory;
/*    */ import com.hypixel.hytale.server.core.entity.entities.player.windows.Window;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.SoundUtil;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.core.util.TempAssetIdUtil;
/*    */ import java.util.Set;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class FieldCraftingWindow
/*    */   extends Window
/*    */ {
/*    */   @Nonnull
/*    */   private final JsonObject windowData;
/*    */   
/*    */   public FieldCraftingWindow() {
/* 34 */     super(WindowType.PocketCrafting);
/*    */     
/* 36 */     this.windowData = new JsonObject();
/* 37 */     this.windowData.addProperty("type", Integer.valueOf(BenchType.Crafting.ordinal()));
/*    */     
/* 39 */     this.windowData.addProperty("id", "Fieldcraft");
/* 40 */     this.windowData.addProperty("name", "server.ui.inventory.fieldcraft.title");
/* 41 */     JsonArray categories = new JsonArray();
/*    */ 
/*    */     
/* 44 */     for (FieldcraftCategory fieldcraftCategory : FieldcraftCategory.getAssetMap().getAssetMap().values()) {
/* 45 */       JsonObject category = new JsonObject();
/* 46 */       category.addProperty("id", fieldcraftCategory.getId());
/* 47 */       category.addProperty("icon", fieldcraftCategory.getIcon());
/* 48 */       category.addProperty("name", fieldcraftCategory.getName());
/*    */       
/* 50 */       Set<String> recipes = CraftingPlugin.getAvailableRecipesForCategory("Fieldcraft", fieldcraftCategory.getId());
/*    */       
/* 52 */       if (recipes != null) {
/* 53 */         JsonArray itemsArray = new JsonArray();
/* 54 */         for (String recipeId : recipes) {
/* 55 */           itemsArray.add(recipeId);
/*    */         }
/* 57 */         category.add("craftableRecipes", (JsonElement)itemsArray);
/*    */       } 
/*    */     } 
/*    */     
/* 61 */     this.windowData.add("categories", (JsonElement)categories);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public JsonObject getData() {
/* 66 */     return this.windowData;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onOpen0() {
/* 71 */     PlayerRef playerRef = getPlayerRef();
/* 72 */     Ref<EntityStore> ref = playerRef.getReference();
/* 73 */     Store<EntityStore> store = ref.getStore();
/* 74 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*    */     
/* 76 */     this.windowData.addProperty("worldMemoriesLevel", Integer.valueOf(MemoriesPlugin.get().getMemoriesLevel(world.getGameplayConfig())));
/* 77 */     invalidate();
/*    */     
/* 79 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onClose0() {}
/*    */ 
/*    */   
/*    */   public void handleAction(@Nonnull Ref<EntityStore> ref, @Nonnull Store<EntityStore> store, @Nonnull WindowAction action) {
/* 88 */     if (action instanceof CraftRecipeAction) { CraftRecipeAction craftAction = (CraftRecipeAction)action;
/* 89 */       CraftingManager craftingManager = (CraftingManager)store.getComponent(ref, CraftingManager.getComponentType());
/* 90 */       if (CraftingWindow.craftSimpleItem(store, ref, craftingManager, craftAction))
/* 91 */         SoundUtil.playSoundEvent2d(ref, TempAssetIdUtil.getSoundEventIndex("SFX_Player_Craft_Item_Inventory"), SoundCategory.UI, (ComponentAccessor)store);  }
/*    */   
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\crafting\window\FieldCraftingWindow.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */