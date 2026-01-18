/*    */ package com.hypixel.hytale.server.core.inventory.container.filter;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.asset.type.item.config.Item;
/*    */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*    */ import com.hypixel.hytale.server.core.inventory.container.SimpleItemContainer;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class NoDuplicateFilter
/*    */   implements ItemSlotFilter {
/*    */   private final SimpleItemContainer container;
/*    */   
/*    */   public NoDuplicateFilter(SimpleItemContainer container) {
/* 13 */     this.container = container;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean test(@Nullable Item item) {
/* 18 */     if (item == null || item.getId() == null) return false; 
/* 19 */     for (short i = 0; i < this.container.getCapacity(); i = (short)(i + 1)) {
/* 20 */       ItemStack itemStack = this.container.getItemStack(i);
/* 21 */       if (itemStack != null && 
/* 22 */         itemStack.getItemId().equals(item.getId())) {
/* 23 */         return false;
/*    */       }
/*    */     } 
/*    */     
/* 27 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\inventory\container\filter\NoDuplicateFilter.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */