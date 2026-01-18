/*    */ package com.hypixel.hytale.server.core.inventory;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
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
/*    */ public class ItemContext
/*    */ {
/*    */   @Nonnull
/*    */   private final ItemContainer container;
/*    */   private final short slot;
/*    */   @Nonnull
/*    */   private final ItemStack itemStack;
/*    */   
/*    */   public ItemContext(@Nonnull ItemContainer container, short slot, @Nonnull ItemStack itemStack) {
/* 24 */     this.container = container;
/* 25 */     this.slot = slot;
/* 26 */     this.itemStack = itemStack;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public ItemContainer getContainer() {
/* 31 */     return this.container;
/*    */   }
/*    */   
/*    */   public short getSlot() {
/* 35 */     return this.slot;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public ItemStack getItemStack() {
/* 40 */     return this.itemStack;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 46 */     return "ItemContext{container=" + String.valueOf(this.container) + ", slot=" + this.slot + ", itemStack=" + String.valueOf(this.itemStack) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\inventory\ItemContext.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */