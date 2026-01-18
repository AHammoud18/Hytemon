/*    */ package com.hypixel.hytale.server.core.inventory.container.filter;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*    */ import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public interface SlotFilter {
/*    */   public static final SlotFilter ALLOW = (actionType, container, slot, itemStack) -> true;
/*    */   public static final SlotFilter DENY = (actionType, container, slot, itemStack) -> false;
/*    */   
/*    */   boolean test(FilterActionType paramFilterActionType, ItemContainer paramItemContainer, short paramShort, @Nullable ItemStack paramItemStack);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\inventory\container\filter\SlotFilter.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */