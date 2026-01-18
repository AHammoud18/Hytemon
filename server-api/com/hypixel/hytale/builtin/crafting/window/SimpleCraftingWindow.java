/*    */ package com.hypixel.hytale.builtin.crafting.window;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.crafting.component.CraftingManager;
/*    */ import com.hypixel.hytale.builtin.crafting.state.BenchState;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.protocol.SoundCategory;
/*    */ import com.hypixel.hytale.protocol.packets.window.CraftRecipeAction;
/*    */ import com.hypixel.hytale.protocol.packets.window.WindowAction;
/*    */ import com.hypixel.hytale.protocol.packets.window.WindowType;
/*    */ import com.hypixel.hytale.server.core.asset.type.item.config.CraftingRecipe;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.entity.entities.player.windows.MaterialContainerWindow;
/*    */ import com.hypixel.hytale.server.core.entity.entities.player.windows.WindowManager;
/*    */ import com.hypixel.hytale.server.core.inventory.container.CombinedItemContainer;
/*    */ import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.SoundUtil;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SimpleCraftingWindow
/*    */   extends CraftingWindow
/*    */   implements MaterialContainerWindow
/*    */ {
/*    */   public SimpleCraftingWindow(BenchState benchState) {
/* 35 */     super(WindowType.BasicCrafting, benchState);
/*    */   }
/*    */ 
/*    */   
/*    */   public void init(@Nonnull PlayerRef playerRef, @Nonnull WindowManager manager) {
/* 40 */     super.init(playerRef, manager);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handleAction(@Nonnull Ref<EntityStore> ref, @Nonnull Store<EntityStore> store, @Nonnull WindowAction action) {
/* 45 */     CraftingManager craftingManager = (CraftingManager)store.getComponent(ref, CraftingManager.getComponentType());
/* 46 */     if (craftingManager == null)
/*    */       return; 
/* 48 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*    */     
/* 50 */     if (action instanceof CraftRecipeAction) { boolean accepted; CraftRecipeAction craftAction = (CraftRecipeAction)action;
/* 51 */       String recipeId = craftAction.recipeId;
/* 52 */       int quantity = craftAction.quantity;
/*    */       
/* 54 */       CraftingRecipe craftRecipe = (CraftingRecipe)CraftingRecipe.getAssetMap().getAsset(recipeId);
/*    */       
/* 56 */       if (craftRecipe == null) {
/* 57 */         PlayerRef playerRef = (PlayerRef)store.getComponent(ref, PlayerRef.getComponentType());
/*    */         
/* 59 */         playerRef.getPacketHandler().disconnect("Attempted to craft unknown recipe!");
/*    */         
/*    */         return;
/*    */       } 
/* 63 */       Player player = (Player)store.getComponent(ref, Player.getComponentType());
/* 64 */       CombinedItemContainer combined = player.getInventory().getCombinedBackpackStorageHotbar();
/* 65 */       CombinedItemContainer playerAndContainerInventory = new CombinedItemContainer(new ItemContainer[] { (ItemContainer)combined, getExtraResourcesSection().getItemContainer() });
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 70 */       if (craftRecipe.getTimeSeconds() > 0.0F) {
/* 71 */         accepted = craftingManager.queueCraft(ref, (ComponentAccessor)store, this, 0, craftRecipe, quantity, (ItemContainer)playerAndContainerInventory, CraftingManager.InputRemovalType.NORMAL);
/*    */       } else {
/* 73 */         accepted = craftingManager.craftItem(ref, (ComponentAccessor)store, craftRecipe, quantity, (ItemContainer)playerAndContainerInventory);
/*    */       } 
/*    */ 
/*    */       
/* 77 */       invalidateExtraResources();
/*    */       
/* 79 */       if (accepted) {
/* 80 */         String completedState = (craftRecipe.getTimeSeconds() > 0.0F) ? "CraftCompleted" : "CraftCompletedInstant";
/* 81 */         setBlockInteractionState(completedState, world, 70);
/*    */         
/* 83 */         if (this.bench.getCompletedSoundEventIndex() != 0) {
/* 84 */           Vector3d pos = new Vector3d();
/* 85 */           this.blockType.getBlockCenter(this.rotationIndex, pos);
/* 86 */           pos.add(this.x, this.y, this.z);
/* 87 */           SoundUtil.playSoundEvent3d(this.bench.getCompletedSoundEventIndex(), SoundCategory.SFX, pos, (ComponentAccessor)store);
/*    */         } 
/*    */       }  }
/* 90 */     else if (action instanceof com.hypixel.hytale.protocol.packets.window.TierUpgradeAction && 
/* 91 */       craftingManager.startTierUpgrade(ref, (ComponentAccessor)store, this))
/* 92 */     { setBlockInteractionState("BenchUpgrading", world, 70);
/* 93 */       if (this.bench.getBenchUpgradeSoundEventIndex() != 0) {
/* 94 */         Vector3d pos = new Vector3d();
/* 95 */         this.blockType.getBlockCenter(this.rotationIndex, pos);
/* 96 */         pos.add(this.x, this.y, this.z);
/* 97 */         SoundUtil.playSoundEvent3d(this.bench.getBenchUpgradeSoundEventIndex(), SoundCategory.SFX, pos, (ComponentAccessor)store);
/*    */       }  }
/*    */   
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\crafting\window\SimpleCraftingWindow.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */