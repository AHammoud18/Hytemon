/*    */ package com.hypixel.hytale.server.core.event.events.ecs;
/*    */ 
/*    */ import com.hypixel.hytale.component.system.CancellableEcsEvent;
/*    */ import com.hypixel.hytale.server.core.asset.type.item.config.CraftingRecipe;
/*    */ import javax.annotation.Nonnull;
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
/*    */ public abstract class CraftRecipeEvent
/*    */   extends CancellableEcsEvent
/*    */ {
/*    */   @Nonnull
/*    */   private final CraftingRecipe craftedRecipe;
/*    */   private final int quantity;
/*    */   
/*    */   public CraftRecipeEvent(@Nonnull CraftingRecipe craftedRecipe, int quantity) {
/* 31 */     this.craftedRecipe = craftedRecipe;
/* 32 */     this.quantity = quantity;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public CraftingRecipe getCraftedRecipe() {
/* 40 */     return this.craftedRecipe;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getQuantity() {
/* 47 */     return this.quantity;
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
/*    */ 
/*    */   
/*    */   public static final class Pre
/*    */     extends CraftRecipeEvent
/*    */   {
/*    */     public Pre(@Nonnull CraftingRecipe craftedRecipe, int quantity) {
/* 64 */       super(craftedRecipe, quantity);
/*    */     }
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
/*    */ 
/*    */   
/*    */   public static final class Post
/*    */     extends CraftRecipeEvent
/*    */   {
/*    */     public Post(@Nonnull CraftingRecipe craftedRecipe, int quantity) {
/* 82 */       super(craftedRecipe, quantity);
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\event\events\ecs\CraftRecipeEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */