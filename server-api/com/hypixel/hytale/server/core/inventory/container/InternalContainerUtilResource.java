/*     */ package com.hypixel.hytale.server.core.inventory.container;
/*     */ 
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.protocol.ItemResourceType;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.Item;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.inventory.ResourceQuantity;
/*     */ import com.hypixel.hytale.server.core.inventory.transaction.ActionType;
/*     */ import com.hypixel.hytale.server.core.inventory.transaction.ListTransaction;
/*     */ import com.hypixel.hytale.server.core.inventory.transaction.ResourceSlotTransaction;
/*     */ import com.hypixel.hytale.server.core.inventory.transaction.ResourceTransaction;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.stream.Collectors;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InternalContainerUtilResource
/*     */ {
/*     */   protected static ResourceSlotTransaction internal_removeResourceFromSlot(@Nonnull ItemContainer itemContainer, short slot, @Nonnull ResourceQuantity resource, boolean allOrNothing, boolean filter) {
/*  27 */     ItemContainer.validateSlotIndex(slot, itemContainer.getCapacity());
/*  28 */     ItemContainer.validateQuantity(resource.getQuantity());
/*     */     
/*  30 */     return itemContainer.<ResourceSlotTransaction>writeAction(() -> {
/*     */           if (filter && itemContainer.cantRemoveFromSlot(slot)) {
/*     */             ItemStack itemStack = itemContainer.internal_getSlot(slot);
/*     */             return new ResourceSlotTransaction(false, ActionType.REMOVE, slot, itemStack, itemStack, null, allOrNothing, false, filter, resource, resource.getQuantity(), 0);
/*     */           } 
/*     */           ItemStack slotItemStack = itemContainer.internal_getSlot(slot);
/*     */           if (slotItemStack == null) {
/*     */             return new ResourceSlotTransaction(false, ActionType.REMOVE, slot, null, null, null, allOrNothing, false, filter, resource, resource.getQuantity(), 0);
/*     */           }
/*     */           Item slotItem = slotItemStack.getItem();
/*     */           int quantityInItems = slotItemStack.getQuantity();
/*     */           ItemResourceType resourceType = resource.getResourceType(slotItem);
/*     */           if (resourceType == null) {
/*     */             return new ResourceSlotTransaction(false, ActionType.REMOVE, slot, slotItemStack, slotItemStack, null, allOrNothing, false, filter, resource, resource.getQuantity(), 0);
/*     */           }
/*     */           int resourceTypeQuantity = resourceType.quantity;
/*     */           int quantityRemaining = resource.getQuantity();
/*     */           int quantityInItemsRemaining = MathUtil.ceil(quantityRemaining / resourceTypeQuantity);
/*     */           int quantityInItemsAdjustment = Math.min(quantityInItems, quantityInItemsRemaining);
/*     */           int newItemStackQuantity = Math.max(quantityInItems - quantityInItemsAdjustment, 0);
/*     */           int quantityAdjustment = quantityInItemsAdjustment * resourceTypeQuantity;
/*     */           quantityRemaining -= quantityAdjustment;
/*     */           if (allOrNothing && quantityRemaining > 0) {
/*     */             return new ResourceSlotTransaction(false, ActionType.REMOVE, slot, slotItemStack, slotItemStack, null, allOrNothing, false, filter, resource, resource.getQuantity(), 0);
/*     */           }
/*     */           if (quantityAdjustment <= 0) {
/*     */             return new ResourceSlotTransaction(false, ActionType.REMOVE, slot, slotItemStack, slotItemStack, null, allOrNothing, false, filter, resource, resource.getQuantity(), 0);
/*     */           }
/*     */           ItemStack slotNewItemStack = slotItemStack.withQuantity(newItemStackQuantity);
/*     */           itemContainer.internal_setSlot(slot, slotNewItemStack);
/*     */           ItemStack newStack = slotItemStack.withQuantity(quantityInItemsAdjustment);
/*     */           return new ResourceSlotTransaction(true, ActionType.REMOVE, slot, slotItemStack, slotNewItemStack, newStack, allOrNothing, false, filter, resource, quantityRemaining, quantityAdjustment);
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
/*     */ 
/*     */   
/*     */   protected static ResourceTransaction internal_removeResource(@Nonnull ItemContainer itemContainer, @Nonnull ResourceQuantity resource, boolean allOrNothing, boolean exactAmount, boolean filter) {
/*  78 */     return itemContainer.<ResourceTransaction>writeAction(() -> {
/*     */           if (allOrNothing || exactAmount) {
/*     */             int testQuantityRemaining = testRemoveResourceFromItems(itemContainer, resource, resource.getQuantity(), filter);
/*     */             if (testQuantityRemaining > 0) {
/*     */               return new ResourceTransaction(false, ActionType.REMOVE, resource, resource.getQuantity(), 0, allOrNothing, exactAmount, filter, Collections.emptyList());
/*     */             }
/*     */             if (exactAmount && testQuantityRemaining < 0) {
/*     */               return new ResourceTransaction(false, ActionType.REMOVE, resource, resource.getQuantity(), 0, allOrNothing, exactAmount, filter, Collections.emptyList());
/*     */             }
/*     */           } 
/*     */           ObjectArrayList<ResourceSlotTransaction> objectArrayList = new ObjectArrayList();
/*     */           int consumed = 0;
/*     */           int quantityRemaining = resource.getQuantity();
/*     */           short i = 0;
/*     */           while (i < itemContainer.getCapacity() && quantityRemaining > 0) {
/*     */             ResourceQuantity clone = resource.clone(quantityRemaining);
/*     */             ResourceSlotTransaction transaction = internal_removeResourceFromSlot(itemContainer, i, clone, false, filter);
/*     */             if (transaction.succeeded()) {
/*     */               objectArrayList.add(transaction);
/*     */               quantityRemaining = transaction.getRemainder();
/*     */               consumed += transaction.getConsumed();
/*     */             } 
/*     */             i = (short)(i + 1);
/*     */           } 
/*     */           return new ResourceTransaction((quantityRemaining != resource.getQuantity()), ActionType.REMOVE, resource, quantityRemaining, consumed, allOrNothing, exactAmount, filter, (List)objectArrayList);
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
/*     */   protected static ListTransaction<ResourceTransaction> internal_removeResources(@Nonnull ItemContainer itemContainer, @Nullable List<ResourceQuantity> resources, boolean allOrNothing, boolean exactAmount, boolean filter) {
/* 114 */     if (resources == null || resources.isEmpty()) {
/* 115 */       return ListTransaction.getEmptyTransaction(true);
/*     */     }
/*     */     
/* 118 */     return itemContainer.<ListTransaction<ResourceTransaction>>writeAction(() -> {
/*     */           if (allOrNothing || exactAmount) {
/*     */             for (ResourceQuantity resource : resources) {
/*     */               int testQuantityRemaining = testRemoveResourceFromItems(itemContainer, resource, resource.getQuantity(), filter);
/*     */               if (testQuantityRemaining > 0) {
/*     */                 return new ListTransaction(false, (List)resources.stream().map(()).collect(Collectors.toList()));
/*     */               }
/*     */               if (exactAmount && testQuantityRemaining < 0) {
/*     */                 return new ListTransaction(false, (List)resources.stream().map(()).collect(Collectors.toList()));
/*     */               }
/*     */             } 
/*     */           }
/*     */           ObjectArrayList<ResourceTransaction> objectArrayList = new ObjectArrayList();
/*     */           for (ResourceQuantity resource : resources) {
/*     */             objectArrayList.add(internal_removeResource(itemContainer, resource, allOrNothing, exactAmount, filter));
/*     */           }
/*     */           return new ListTransaction(true, (List)objectArrayList);
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
/*     */ 
/*     */   
/*     */   public static int testRemoveResourceFromItems(@Nonnull ItemContainer container, @Nonnull ResourceQuantity resource, int testQuantityRemaining, boolean filter) {
/*     */     short i;
/* 163 */     for (i = 0; i < container.getCapacity() && testQuantityRemaining > 0; i = (short)(i + 1)) {
/* 164 */       testQuantityRemaining = testRemoveResourceFromSlot(container, i, resource, testQuantityRemaining, filter);
/*     */     }
/* 166 */     return testQuantityRemaining;
/*     */   }
/*     */   
/*     */   public static TestRemoveItemSlotResult testRemoveResourceSlotFromItems(@Nonnull ItemContainer container, @Nonnull ResourceQuantity resource, int testQuantityRemaining, boolean filter) {
/* 170 */     TestRemoveItemSlotResult result = new TestRemoveItemSlotResult(testQuantityRemaining);
/*     */     short i;
/* 172 */     for (i = 0; i < container.getCapacity() && result.quantityRemaining > 0; i = (short)(i + 1)) {
/* 173 */       int newValue = testRemoveResourceFromSlot(container, i, resource, result.quantityRemaining, filter);
/* 174 */       if (newValue != result.quantityRemaining) {
/* 175 */         int diff = result.quantityRemaining - newValue;
/* 176 */         result.quantityRemaining = newValue;
/* 177 */         result.picked.put(Short.valueOf(i), Integer.valueOf(diff));
/*     */       } 
/*     */     } 
/* 180 */     return result;
/*     */   }
/*     */   
/*     */   public static int testRemoveResourceFromSlot(@Nonnull ItemContainer container, short slot, @Nonnull ResourceQuantity resource, int testQuantityRemaining, boolean filter) {
/* 184 */     if (filter && container.cantRemoveFromSlot(slot)) return testQuantityRemaining;
/*     */     
/* 186 */     ItemStack slotItemStack = container.internal_getSlot(slot);
/* 187 */     if (ItemStack.isEmpty(slotItemStack)) return testQuantityRemaining; 
/* 188 */     Item slotItem = slotItemStack.getItem();
/* 189 */     ItemResourceType resourceType = resource.getResourceType(slotItem);
/* 190 */     if (resourceType == null) return testQuantityRemaining;
/*     */     
/* 192 */     int resourceTypeQuantity = resourceType.quantity;
/* 193 */     int quantityInItemsRemaining = MathUtil.ceil(testQuantityRemaining / resourceTypeQuantity);
/*     */     
/* 195 */     int quantityInItems = slotItemStack.getQuantity();
/* 196 */     int quantityInItemsAdjustment = Math.min(quantityInItems, quantityInItemsRemaining);
/*     */     
/* 198 */     int quantityAdjustment = quantityInItemsAdjustment * resourceTypeQuantity;
/*     */     
/* 200 */     testQuantityRemaining -= quantityAdjustment;
/*     */     
/* 202 */     return testQuantityRemaining;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\inventory\container\InternalContainerUtilResource.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */