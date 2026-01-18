/*     */ package com.hypixel.hytale.server.core.inventory.container;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.Item;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.inventory.transaction.ActionType;
/*     */ import com.hypixel.hytale.server.core.inventory.transaction.TagSlotTransaction;
/*     */ import com.hypixel.hytale.server.core.inventory.transaction.TagTransaction;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InternalContainerUtilTag
/*     */ {
/*     */   protected static TagSlotTransaction internal_removeTagFromSlot(@Nonnull ItemContainer itemContainer, short slot, int tagIndex, int quantity, boolean allOrNothing, boolean filter) {
/*  22 */     ItemContainer.validateSlotIndex(slot, itemContainer.getCapacity());
/*  23 */     ItemContainer.validateQuantity(quantity);
/*     */     
/*  25 */     return itemContainer.<TagSlotTransaction>writeAction(() -> {
/*     */           int quantityRemaining = quantity;
/*     */           if (filter && itemContainer.cantRemoveFromSlot(slot)) {
/*     */             ItemStack itemStack = itemContainer.internal_getSlot(slot);
/*     */             return new TagSlotTransaction(false, ActionType.REMOVE, slot, itemStack, itemStack, null, allOrNothing, false, filter, tagIndex, quantity);
/*     */           } 
/*     */           ItemStack slotItemStack = itemContainer.internal_getSlot(slot);
/*     */           if (slotItemStack == null) {
/*     */             return new TagSlotTransaction(false, ActionType.REMOVE, slot, null, null, null, allOrNothing, false, filter, tagIndex, quantity);
/*     */           }
/*     */           Item slotItem = slotItemStack.getItem();
/*     */           int quantityInItems = slotItemStack.getQuantity();
/*     */           if (slotItem.getData() == null || !slotItem.getData().getExpandedTagIndexes().contains(tagIndex)) {
/*     */             return new TagSlotTransaction(false, ActionType.REMOVE, slot, slotItemStack, slotItemStack, null, allOrNothing, false, filter, tagIndex, quantity);
/*     */           }
/*     */           int quantityInItemsRemaining = quantityRemaining;
/*     */           int quantityInItemsAdjustment = Math.min(quantityInItems, quantityInItemsRemaining);
/*     */           int newItemStackQuantity = quantityInItems - quantityInItemsAdjustment;
/*     */           quantityRemaining -= quantityInItemsAdjustment;
/*     */           if (allOrNothing && quantityRemaining > 0) {
/*     */             return new TagSlotTransaction(false, ActionType.REMOVE, slot, slotItemStack, slotItemStack, null, allOrNothing, false, filter, tagIndex, quantity);
/*     */           }
/*     */           ItemStack slotNewItemStack = slotItemStack.withQuantity(newItemStackQuantity);
/*     */           itemContainer.internal_setSlot(slot, slotNewItemStack);
/*     */           ItemStack newStack = slotItemStack.withQuantity(quantityInItemsAdjustment);
/*     */           return new TagSlotTransaction(true, ActionType.REMOVE, slot, slotItemStack, slotNewItemStack, newStack, allOrNothing, false, filter, tagIndex, quantityRemaining);
/*     */         });
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
/*     */   
/*     */   protected static TagTransaction internal_removeTag(@Nonnull ItemContainer itemContainer, int tagIndex, int quantity, boolean allOrNothing, boolean exactAmount, boolean filter) {
/*  65 */     return itemContainer.<TagTransaction>writeAction(() -> {
/*     */           if (allOrNothing || exactAmount) {
/*     */             int testQuantityRemaining = testRemoveTagFromItems(itemContainer, tagIndex, quantity, filter);
/*     */             if (testQuantityRemaining > 0) {
/*     */               return new TagTransaction(false, ActionType.REMOVE, tagIndex, quantity, allOrNothing, exactAmount, filter, Collections.emptyList());
/*     */             }
/*     */             if (exactAmount && testQuantityRemaining < 0) {
/*     */               return new TagTransaction(false, ActionType.REMOVE, tagIndex, quantity, allOrNothing, exactAmount, filter, Collections.emptyList());
/*     */             }
/*     */           } 
/*     */           ObjectArrayList<TagSlotTransaction> objectArrayList = new ObjectArrayList();
/*     */           int quantityRemaining = quantity;
/*     */           short i = 0;
/*     */           while (i < itemContainer.getCapacity() && quantityRemaining > 0) {
/*     */             TagSlotTransaction transaction = internal_removeTagFromSlot(itemContainer, i, tagIndex, quantityRemaining, allOrNothing, filter);
/*     */             objectArrayList.add(transaction);
/*     */             quantityRemaining = transaction.getRemainder();
/*     */             i = (short)(i + 1);
/*     */           } 
/*     */           return new TagTransaction(true, ActionType.REMOVE, tagIndex, quantityRemaining, allOrNothing, exactAmount, filter, (List)objectArrayList);
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   protected static int testRemoveTagFromItems(@Nonnull ItemContainer container, int tagIndex, int testQuantityRemaining, boolean filter) {
/*     */     short i;
/*  91 */     for (i = 0; i < container.getCapacity() && testQuantityRemaining > 0; i = (short)(i + 1)) {
/*  92 */       if (!filter || !container.cantRemoveFromSlot(i)) {
/*     */         
/*  94 */         ItemStack slotItemStack = container.internal_getSlot(i);
/*  95 */         if (!ItemStack.isEmpty(slotItemStack)) {
/*  96 */           AssetExtraInfo.Data slotItemData = slotItemStack.getItem().getData();
/*  97 */           if (slotItemData != null && slotItemData.getExpandedTagIndexes().contains(tagIndex))
/*     */           
/*  99 */           { int quantityInItems = slotItemStack.getQuantity();
/* 100 */             testQuantityRemaining -= Math.min(quantityInItems, testQuantityRemaining); } 
/*     */         } 
/*     */       } 
/* 103 */     }  return testQuantityRemaining;
/*     */   }
/*     */   
/*     */   protected static TestRemoveItemSlotResult testRemoveTagSlotFromItems(@Nonnull ItemContainer container, int tagIndex, int testQuantityRemaining, boolean filter) {
/* 107 */     TestRemoveItemSlotResult result = new TestRemoveItemSlotResult(testQuantityRemaining);
/*     */     short i;
/* 109 */     for (i = 0; i < container.getCapacity() && result.quantityRemaining > 0; i = (short)(i + 1)) {
/* 110 */       if (!filter || !container.cantRemoveFromSlot(i)) {
/*     */         
/* 112 */         ItemStack slotItemStack = container.internal_getSlot(i);
/* 113 */         if (!ItemStack.isEmpty(slotItemStack)) {
/* 114 */           AssetExtraInfo.Data slotItemData = slotItemStack.getItem().getData();
/* 115 */           if (slotItemData != null && slotItemData.getExpandedTagIndexes().contains(tagIndex))
/*     */           
/* 117 */           { int quantityInItems = slotItemStack.getQuantity();
/* 118 */             result.quantityRemaining -= Math.min(quantityInItems, result.quantityRemaining); } 
/*     */         } 
/*     */       } 
/* 121 */     }  return result;
/*     */   }
/*     */   
/*     */   protected static int testRemoveTagFromSlot(@Nonnull ItemContainer container, short slot, int tagIndex, int testQuantityRemaining, boolean filter) {
/* 125 */     if (filter && container.cantRemoveFromSlot(slot)) return testQuantityRemaining;
/*     */     
/* 127 */     ItemStack slotItemStack = container.internal_getSlot(slot);
/* 128 */     if (ItemStack.isEmpty(slotItemStack)) return testQuantityRemaining; 
/* 129 */     Item slotItem = slotItemStack.getItem();
/* 130 */     if (!slotItem.getData().getExpandedTagIndexes().contains(tagIndex)) return testQuantityRemaining;
/*     */     
/* 132 */     int quantityInItems = slotItemStack.getQuantity();
/* 133 */     testQuantityRemaining -= Math.min(quantityInItems, testQuantityRemaining);
/*     */     
/* 135 */     return testQuantityRemaining;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\inventory\container\InternalContainerUtilTag.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */