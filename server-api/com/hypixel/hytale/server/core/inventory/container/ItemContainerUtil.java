/*    */ package com.hypixel.hytale.server.core.inventory.container;
/*    */ 
/*    */ import com.hypixel.hytale.protocol.ItemArmorSlot;
/*    */ import com.hypixel.hytale.server.core.inventory.container.filter.ArmorSlotAddFilter;
/*    */ import com.hypixel.hytale.server.core.inventory.container.filter.FilterActionType;
/*    */ import com.hypixel.hytale.server.core.inventory.container.filter.NoDuplicateFilter;
/*    */ import com.hypixel.hytale.server.core.inventory.container.filter.SlotFilter;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemContainerUtil
/*    */ {
/*    */   public static <T extends ItemContainer> T trySetArmorFilters(T container) {
/* 14 */     if (container instanceof SimpleItemContainer) { SimpleItemContainer itemContainer = (SimpleItemContainer)container;
/* 15 */       ItemArmorSlot[] itemArmorSlots = ItemArmorSlot.VALUES;
/* 16 */       for (short i = 0; i < itemContainer.getCapacity(); i = (short)(i + 1)) {
/* 17 */         if (i < itemArmorSlots.length) {
/* 18 */           if (i < 5) {
/* 19 */             itemContainer.setSlotFilter(FilterActionType.ADD, i, (SlotFilter)new ArmorSlotAddFilter(itemArmorSlots[i]));
/*    */           } else {
/* 21 */             itemContainer.setSlotFilter(FilterActionType.ADD, i, (SlotFilter)new NoDuplicateFilter(itemContainer));
/*    */           } 
/*    */         } else {
/*    */           
/* 25 */           itemContainer.setSlotFilter(FilterActionType.ADD, i, SlotFilter.DENY);
/*    */         } 
/*    */       }  }
/*    */ 
/*    */     
/* 30 */     return container;
/*    */   }
/*    */   
/*    */   public static <T extends ItemContainer> T trySetSlotFilters(T container, SlotFilter filter) {
/* 34 */     if (container instanceof SimpleItemContainer) { SimpleItemContainer itemContainer = (SimpleItemContainer)container;
/* 35 */       for (short i = 0; i < itemContainer.getCapacity(); i = (short)(i + 1)) {
/* 36 */         itemContainer.setSlotFilter(FilterActionType.ADD, i, filter);
/*    */       } }
/*    */     
/* 39 */     return container;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\inventory\container\ItemContainerUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */