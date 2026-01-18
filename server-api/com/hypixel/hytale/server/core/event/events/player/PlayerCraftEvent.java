/*    */ package com.hypixel.hytale.server.core.event.events.player;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.server.core.asset.type.item.config.CraftingRecipe;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Deprecated(forRemoval = true)
/*    */ public class PlayerCraftEvent
/*    */   extends PlayerEvent<String>
/*    */ {
/*    */   private final CraftingRecipe craftedRecipe;
/*    */   private final int quantity;
/*    */   
/*    */   public PlayerCraftEvent(@Nonnull Ref<EntityStore> ref, @Nonnull Player player, CraftingRecipe craftedRecipe, int quantity) {
/* 22 */     super(ref, player);
/* 23 */     this.craftedRecipe = craftedRecipe;
/* 24 */     this.quantity = quantity;
/*    */   }
/*    */   
/*    */   public CraftingRecipe getCraftedRecipe() {
/* 28 */     return this.craftedRecipe;
/*    */   }
/*    */   
/*    */   public int getQuantity() {
/* 32 */     return this.quantity;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 38 */     return "PlayerCraftEvent{craftingRecipe=" + String.valueOf(this.craftedRecipe) + ", quantity=" + this.quantity + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\event\events\player\PlayerCraftEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */