/*    */ package com.hypixel.hytale.server.core.inventory.container.filter;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.asset.type.item.config.Item;
/*    */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*    */ import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface ItemSlotFilter
/*    */   extends SlotFilter
/*    */ {
/*    */   default boolean test(@Nonnull FilterActionType actionType, @Nonnull ItemContainer container, short slot, @Nullable ItemStack itemStack) {
/* 18 */     switch (actionType) { default: throw new MatchException(null, null);
/*    */       case ADD: 
/*    */       case REMOVE: case DROP:
/* 21 */         break; }  itemStack = container.getItemStack(slot);
/* 22 */     return test((itemStack != null) ? itemStack.getItem() : null);
/*    */   }
/*    */   
/*    */   boolean test(@Nullable Item paramItem);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\inventory\container\filter\ItemSlotFilter.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */