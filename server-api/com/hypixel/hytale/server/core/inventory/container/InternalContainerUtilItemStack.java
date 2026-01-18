/*     */ package com.hypixel.hytale.server.core.inventory.container;
/*     */ 
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.Item;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.inventory.transaction.ActionType;
/*     */ import com.hypixel.hytale.server.core.inventory.transaction.ItemStackSlotTransaction;
/*     */ import com.hypixel.hytale.server.core.inventory.transaction.ItemStackTransaction;
/*     */ import com.hypixel.hytale.server.core.inventory.transaction.ListTransaction;
/*     */ import com.hypixel.hytale.server.core.inventory.transaction.SlotTransaction;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.function.BiPredicate;
/*     */ import java.util.stream.Collectors;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ public class InternalContainerUtilItemStack
/*     */ {
/*     */   protected static int testAddToExistingSlot(@Nonnull ItemContainer abstractItemContainer, short slot, ItemStack itemStack, int itemMaxStack, int testQuantityRemaining, boolean filter) {
/*  22 */     ItemStack slotItemStack = abstractItemContainer.internal_getSlot(slot);
/*  23 */     if (ItemStack.isEmpty(slotItemStack)) return testQuantityRemaining; 
/*  24 */     if (!slotItemStack.isStackableWith(itemStack)) return testQuantityRemaining; 
/*  25 */     if (filter && abstractItemContainer.cantAddToSlot(slot, itemStack, slotItemStack)) return testQuantityRemaining;
/*     */     
/*  27 */     int quantity = slotItemStack.getQuantity();
/*  28 */     int quantityAdjustment = Math.min(itemMaxStack - quantity, testQuantityRemaining);
/*  29 */     testQuantityRemaining -= quantityAdjustment;
/*  30 */     return testQuantityRemaining;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected static ItemStackSlotTransaction internal_addToExistingSlot(@Nonnull ItemContainer container, short slot, @Nonnull ItemStack itemStack, int itemMaxStack, boolean filter) {
/*  35 */     ItemStack slotItemStack = container.internal_getSlot(slot);
/*  36 */     if (ItemStack.isEmpty(slotItemStack)) {
/*  37 */       return new ItemStackSlotTransaction(false, ActionType.ADD, slot, slotItemStack, slotItemStack, null, false, false, filter, true, itemStack, itemStack);
/*     */     }
/*  39 */     if (!slotItemStack.isStackableWith(itemStack)) {
/*  40 */       return new ItemStackSlotTransaction(false, ActionType.ADD, slot, slotItemStack, slotItemStack, null, false, false, filter, true, itemStack, itemStack);
/*     */     }
/*  42 */     if (filter && container.cantAddToSlot(slot, itemStack, slotItemStack)) {
/*  43 */       return new ItemStackSlotTransaction(false, ActionType.ADD, slot, slotItemStack, slotItemStack, null, false, false, filter, true, itemStack, itemStack);
/*     */     }
/*     */     
/*  46 */     int quantityRemaining = itemStack.getQuantity();
/*     */     
/*  48 */     int quantity = slotItemStack.getQuantity();
/*  49 */     int quantityAdjustment = Math.min(itemMaxStack - quantity, quantityRemaining);
/*  50 */     int newQuantity = quantity + quantityAdjustment;
/*  51 */     quantityRemaining -= quantityAdjustment;
/*     */ 
/*     */     
/*  54 */     if (quantityAdjustment <= 0) {
/*  55 */       return new ItemStackSlotTransaction(false, ActionType.ADD, slot, slotItemStack, slotItemStack, null, false, false, filter, true, itemStack, itemStack);
/*     */     }
/*     */     
/*  58 */     ItemStack slotNew = slotItemStack.withQuantity(newQuantity);
/*  59 */     if (newQuantity > 0) {
/*  60 */       container.internal_setSlot(slot, slotNew);
/*     */     } else {
/*  62 */       container.internal_removeSlot(slot);
/*     */     } 
/*  64 */     ItemStack remainder = (quantityRemaining != itemStack.getQuantity()) ? itemStack.withQuantity(quantityRemaining) : itemStack;
/*  65 */     return new ItemStackSlotTransaction(true, ActionType.ADD, slot, slotItemStack, slotNew, null, false, false, filter, true, itemStack, remainder);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected static ItemStackSlotTransaction internal_addToEmptySlot(@Nonnull ItemContainer container, short slot, @Nonnull ItemStack itemStack, int itemMaxStack, boolean filter) {
/*  70 */     ItemStack slotItemStack = container.internal_getSlot(slot);
/*  71 */     if (slotItemStack != null && !slotItemStack.isEmpty()) {
/*  72 */       return new ItemStackSlotTransaction(false, ActionType.ADD, slot, slotItemStack, slotItemStack, null, false, false, filter, false, itemStack, itemStack);
/*     */     }
/*  74 */     if (filter && container.cantAddToSlot(slot, itemStack, slotItemStack)) {
/*  75 */       return new ItemStackSlotTransaction(false, ActionType.ADD, slot, slotItemStack, slotItemStack, null, false, false, filter, false, itemStack, itemStack);
/*     */     }
/*     */     
/*  78 */     int quantityRemaining = itemStack.getQuantity();
/*  79 */     int quantityAdjustment = Math.min(itemMaxStack, quantityRemaining);
/*  80 */     quantityRemaining -= quantityAdjustment;
/*     */     
/*  82 */     ItemStack slotNew = itemStack.withQuantity(quantityAdjustment);
/*  83 */     container.internal_setSlot(slot, slotNew);
/*  84 */     ItemStack remainder = (itemStack.getQuantity() != quantityRemaining) ? itemStack.withQuantity(quantityRemaining) : itemStack;
/*  85 */     return new ItemStackSlotTransaction(true, ActionType.ADD, slot, slotItemStack, slotNew, null, false, false, filter, false, itemStack, remainder);
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
/*     */   protected static int testAddToEmptySlots(@Nonnull ItemContainer container, ItemStack itemStack, int itemMaxStack, int testQuantityRemaining, boolean filter) {
/*     */     short i;
/*  99 */     for (i = 0; i < container.getCapacity() && testQuantityRemaining > 0; i = (short)(i + 1)) {
/* 100 */       ItemStack slotItemStack = container.internal_getSlot(i);
/* 101 */       if ((slotItemStack == null || slotItemStack.isEmpty()) && (
/* 102 */         !filter || !container.cantAddToSlot(i, itemStack, slotItemStack))) {
/*     */         
/* 104 */         int quantityAdjustment = Math.min(itemMaxStack, testQuantityRemaining);
/* 105 */         testQuantityRemaining -= quantityAdjustment;
/*     */       } 
/*     */     } 
/* 108 */     return testQuantityRemaining;
/*     */   }
/*     */   
/*     */   protected static ItemStackSlotTransaction internal_addItemStackToSlot(@Nonnull ItemContainer itemContainer, short slot, @Nonnull ItemStack itemStack, boolean allOrNothing, boolean filter) {
/* 112 */     ItemContainer.validateSlotIndex(slot, itemContainer.getCapacity());
/*     */     
/* 114 */     return itemContainer.<ItemStackSlotTransaction>writeAction(() -> {
/*     */           int quantityRemaining = itemStack.getQuantity();
/*     */           ItemStack slotItemStack = itemContainer.internal_getSlot(slot);
/*     */           if (filter && itemContainer.cantAddToSlot(slot, itemStack, slotItemStack)) {
/*     */             return new ItemStackSlotTransaction(false, ActionType.ADD, slot, slotItemStack, slotItemStack, null, allOrNothing, false, filter, false, itemStack, itemStack);
/*     */           }
/*     */           if (slotItemStack == null) {
/*     */             itemContainer.internal_setSlot(slot, itemStack);
/*     */             return new ItemStackSlotTransaction(true, ActionType.ADD, slot, null, itemStack, null, allOrNothing, false, filter, false, itemStack, null);
/*     */           } 
/*     */           int quantity = slotItemStack.getQuantity();
/*     */           if (!itemStack.isStackableWith(slotItemStack)) {
/*     */             return new ItemStackSlotTransaction(false, ActionType.ADD, slot, slotItemStack, slotItemStack, null, allOrNothing, false, filter, false, itemStack, itemStack);
/*     */           }
/*     */           int quantityAdjustment = Math.min(slotItemStack.getItem().getMaxStack() - quantity, quantityRemaining);
/*     */           int newQuantity = quantity + quantityAdjustment;
/*     */           quantityRemaining -= quantityAdjustment;
/*     */           if (allOrNothing && quantityRemaining > 0) {
/*     */             return new ItemStackSlotTransaction(false, ActionType.ADD, slot, slotItemStack, slotItemStack, null, allOrNothing, false, filter, false, itemStack, itemStack);
/*     */           }
/*     */           if (quantityAdjustment <= 0) {
/*     */             return new ItemStackSlotTransaction(false, ActionType.ADD, slot, slotItemStack, slotItemStack, null, allOrNothing, false, filter, false, itemStack, itemStack);
/*     */           }
/*     */           ItemStack newItemStack = slotItemStack.withQuantity(newQuantity);
/*     */           itemContainer.internal_setSlot(slot, newItemStack);
/*     */           ItemStack remainder = itemStack.withQuantity(quantityRemaining);
/*     */           return new ItemStackSlotTransaction(true, ActionType.ADD, slot, slotItemStack, newItemStack, null, allOrNothing, false, filter, false, itemStack, remainder);
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
/*     */   @Nonnull
/*     */   protected static ItemStackSlotTransaction internal_setItemStackForSlot(@Nonnull ItemContainer itemContainer, short slot, ItemStack itemStack, boolean filter) {
/* 156 */     ItemContainer.validateSlotIndex(slot, itemContainer.getCapacity());
/*     */     
/* 158 */     return itemContainer.<ItemStackSlotTransaction>writeAction(() -> {
/*     */           ItemStack slotItemStack = itemContainer.internal_getSlot(slot);
/*     */           if (filter && itemContainer.cantAddToSlot(slot, itemStack, slotItemStack)) {
/*     */             return new ItemStackSlotTransaction(false, ActionType.SET, slot, slotItemStack, slotItemStack, null, false, false, filter, false, itemStack, itemStack);
/*     */           }
/*     */           ItemStack oldItemStack = itemContainer.internal_setSlot(slot, itemStack);
/*     */           return new ItemStackSlotTransaction(true, ActionType.SET, slot, oldItemStack, itemStack, null, false, false, filter, false, itemStack, null);
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected static SlotTransaction internal_removeItemStackFromSlot(@Nonnull ItemContainer itemContainer, short slot, boolean filter) {
/* 172 */     ItemContainer.validateSlotIndex(slot, itemContainer.getCapacity());
/*     */     
/* 174 */     return itemContainer.<SlotTransaction>writeAction(() -> {
/*     */           if (filter && itemContainer.cantRemoveFromSlot(slot)) {
/*     */             ItemStack itemStack = itemContainer.internal_getSlot(slot);
/*     */             return (SlotTransaction)new ItemStackSlotTransaction(false, ActionType.REMOVE, slot, itemStack, itemStack, null, false, false, filter, false, null, itemStack);
/*     */           } 
/*     */           ItemStack oldItemStack = itemContainer.internal_removeSlot(slot);
/*     */           return new SlotTransaction(true, ActionType.REMOVE, slot, oldItemStack, null, oldItemStack, false, false, false);
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   protected static ItemStackSlotTransaction internal_removeItemStackFromSlot(@Nonnull ItemContainer itemContainer, short slot, int quantityToRemove, boolean allOrNothing, boolean filter) {
/* 186 */     ItemContainer.validateSlotIndex(slot, itemContainer.getCapacity());
/* 187 */     ItemContainer.validateQuantity(quantityToRemove);
/*     */     
/* 189 */     return itemContainer.<ItemStackSlotTransaction>writeAction(() -> {
/*     */           int quantityRemaining = quantityToRemove;
/*     */           if (filter && itemContainer.cantRemoveFromSlot(slot)) {
/*     */             ItemStack itemStack1 = itemContainer.internal_getSlot(slot);
/*     */             return new ItemStackSlotTransaction(false, ActionType.REMOVE, slot, itemStack1, itemStack1, null, allOrNothing, false, filter, false, null, itemStack1.withQuantity(quantityRemaining));
/*     */           } 
/*     */           ItemStack slotItemStack = itemContainer.internal_getSlot(slot);
/*     */           if (slotItemStack == null) {
/*     */             return new ItemStackSlotTransaction(false, ActionType.REMOVE, slot, null, null, null, allOrNothing, false, filter, false, null, null);
/*     */           }
/*     */           int quantity = slotItemStack.getQuantity();
/*     */           int quantityAdjustment = Math.min(quantity, quantityRemaining);
/*     */           int newQuantity = quantity - quantityAdjustment;
/*     */           quantityRemaining -= quantityAdjustment;
/*     */           if (allOrNothing && quantityRemaining > 0) {
/*     */             return new ItemStackSlotTransaction(false, ActionType.REMOVE, slot, slotItemStack, slotItemStack, null, allOrNothing, false, filter, false, null, slotItemStack.withQuantity(quantityRemaining));
/*     */           }
/*     */           ItemStack itemStack = slotItemStack.withQuantity(newQuantity);
/*     */           itemContainer.internal_setSlot(slot, itemStack);
/*     */           ItemStack newStack = slotItemStack.withQuantity(quantityAdjustment);
/*     */           ItemStack remainder = slotItemStack.withQuantity(quantityRemaining);
/*     */           return new ItemStackSlotTransaction(true, ActionType.REMOVE, slot, slotItemStack, itemStack, newStack, allOrNothing, false, filter, false, null, remainder);
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static ItemStackSlotTransaction internal_removeItemStackFromSlot(@Nonnull ItemContainer itemContainer, short slot, @Nullable ItemStack itemStackToRemove, int quantityToRemove, boolean allOrNothing, boolean filter) {
/* 221 */     return internal_removeItemStackFromSlot(itemContainer, slot, itemStackToRemove, quantityToRemove, allOrNothing, filter, (a, b) -> ItemStack.isStackableWith(a, b));
/*     */   }
/*     */   
/*     */   protected static ItemStackSlotTransaction internal_removeItemStackFromSlot(@Nonnull ItemContainer itemContainer, short slot, @Nullable ItemStack itemStackToRemove, int quantityToRemove, boolean allOrNothing, boolean filter, BiPredicate<ItemStack, ItemStack> predicate) {
/* 225 */     ItemContainer.validateSlotIndex(slot, itemContainer.getCapacity());
/* 226 */     ItemContainer.validateQuantity(quantityToRemove);
/*     */     
/* 228 */     return itemContainer.<ItemStackSlotTransaction>writeAction(() -> {
/*     */           int quantityRemaining = quantityToRemove;
/*     */           if (filter && itemContainer.cantRemoveFromSlot(slot)) {
/*     */             ItemStack itemStack1 = itemContainer.internal_getSlot(slot);
/*     */             return new ItemStackSlotTransaction(false, ActionType.REMOVE, slot, itemStack1, itemStack1, null, allOrNothing, false, filter, false, itemStackToRemove, itemStackToRemove);
/*     */           } 
/*     */           ItemStack slotItemStack = itemContainer.internal_getSlot(slot);
/*     */           if ((slotItemStack == null && itemStackToRemove != null) || (slotItemStack != null && itemStackToRemove == null) || (slotItemStack != null && !predicate.test(slotItemStack, itemStackToRemove))) {
/*     */             return new ItemStackSlotTransaction(false, ActionType.REMOVE, slot, slotItemStack, slotItemStack, null, allOrNothing, false, filter, false, itemStackToRemove, itemStackToRemove);
/*     */           }
/*     */           if (slotItemStack == null) {
/*     */             return new ItemStackSlotTransaction(true, ActionType.REMOVE, slot, null, null, null, allOrNothing, false, filter, false, itemStackToRemove, itemStackToRemove);
/*     */           }
/*     */           int quantity = slotItemStack.getQuantity();
/*     */           int quantityAdjustment = Math.min(quantity, quantityRemaining);
/*     */           int newQuantity = quantity - quantityAdjustment;
/*     */           quantityRemaining -= quantityAdjustment;
/*     */           if (allOrNothing && quantityRemaining > 0) {
/*     */             return new ItemStackSlotTransaction(false, ActionType.REMOVE, slot, slotItemStack, slotItemStack, null, allOrNothing, false, filter, false, itemStackToRemove, itemStackToRemove);
/*     */           }
/*     */           ItemStack itemStack = slotItemStack.withQuantity(newQuantity);
/*     */           itemContainer.internal_setSlot(slot, itemStack);
/*     */           ItemStack newStack = slotItemStack.withQuantity(quantityAdjustment);
/*     */           ItemStack remainder = itemStackToRemove.withQuantity(quantityRemaining);
/*     */           return new ItemStackSlotTransaction(true, ActionType.REMOVE, slot, slotItemStack, itemStack, newStack, allOrNothing, false, filter, false, itemStackToRemove, remainder);
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
/*     */   protected static int testRemoveItemStackFromSlot(@Nonnull ItemContainer container, short slot, ItemStack itemStack, int testQuantityRemaining, boolean filter) {
/* 267 */     return testRemoveItemStackFromSlot(container, slot, itemStack, testQuantityRemaining, filter, (a, b) -> ItemStack.isStackableWith(a, b));
/*     */   }
/*     */   
/*     */   protected static int testRemoveItemStackFromSlot(@Nonnull ItemContainer container, short slot, ItemStack itemStack, int testQuantityRemaining, boolean filter, BiPredicate<ItemStack, ItemStack> predicate) {
/* 271 */     if (filter && container.cantRemoveFromSlot(slot)) return testQuantityRemaining;
/*     */     
/* 273 */     ItemStack slotItemStack = container.internal_getSlot(slot);
/* 274 */     if (ItemStack.isEmpty(slotItemStack)) return testQuantityRemaining; 
/* 275 */     if (!predicate.test(slotItemStack, itemStack)) return testQuantityRemaining;
/*     */     
/* 277 */     int quantity = slotItemStack.getQuantity();
/* 278 */     int quantityAdjustment = Math.min(quantity, testQuantityRemaining);
/* 279 */     testQuantityRemaining -= quantityAdjustment;
/*     */     
/* 281 */     return testQuantityRemaining;
/*     */   }
/*     */   
/*     */   protected static ItemStackTransaction internal_addItemStack(@Nonnull ItemContainer itemContainer, @Nonnull ItemStack itemStack, boolean allOrNothing, boolean fullStacks, boolean filter) {
/* 285 */     Item item = itemStack.getItem();
/* 286 */     if (item == null) throw new IllegalArgumentException(itemStack.getItemId() + " is an invalid item!");
/*     */     
/* 288 */     int itemMaxStack = item.getMaxStack();
/*     */     
/* 290 */     return itemContainer.<ItemStackTransaction>writeAction(() -> {
/*     */           if (allOrNothing) {
/*     */             int testQuantityRemaining = itemStack.getQuantity();
/*     */             if (!fullStacks) {
/*     */               testQuantityRemaining = testAddToExistingItemStacks(itemContainer, itemStack, itemMaxStack, testQuantityRemaining, filter);
/*     */             }
/*     */             testQuantityRemaining = testAddToEmptySlots(itemContainer, itemStack, itemMaxStack, testQuantityRemaining, filter);
/*     */             if (testQuantityRemaining > 0) {
/*     */               return new ItemStackTransaction(false, ActionType.ADD, itemStack, itemStack, allOrNothing, filter, Collections.emptyList());
/*     */             }
/*     */           } 
/*     */           ObjectArrayList<ItemStackSlotTransaction> objectArrayList = new ObjectArrayList();
/*     */           ItemStack remaining = itemStack;
/*     */           if (!fullStacks) {
/*     */             short s = 0;
/*     */             while (s < itemContainer.getCapacity() && !ItemStack.isEmpty(remaining)) {
/*     */               ItemStackSlotTransaction transaction = internal_addToExistingSlot(itemContainer, s, remaining, itemMaxStack, filter);
/*     */               objectArrayList.add(transaction);
/*     */               remaining = transaction.getRemainder();
/*     */               s = (short)(s + 1);
/*     */             } 
/*     */           } 
/*     */           short i = 0;
/*     */           while (i < itemContainer.getCapacity() && !ItemStack.isEmpty(remaining)) {
/*     */             ItemStackSlotTransaction transaction = internal_addToEmptySlot(itemContainer, i, remaining, itemMaxStack, filter);
/*     */             objectArrayList.add(transaction);
/*     */             remaining = transaction.getRemainder();
/*     */             i = (short)(i + 1);
/*     */           } 
/*     */           return new ItemStackTransaction(true, ActionType.ADD, itemStack, remaining, allOrNothing, filter, (List)objectArrayList);
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static ListTransaction<ItemStackTransaction> internal_addItemStacks(@Nonnull ItemContainer itemContainer, @Nullable List<ItemStack> itemStacks, boolean allOrNothing, boolean fullStacks, boolean filter) {
/* 330 */     if (itemStacks == null || itemStacks.isEmpty()) {
/* 331 */       return ListTransaction.getEmptyTransaction(true);
/*     */     }
/*     */     
/* 334 */     return itemContainer.<ListTransaction<ItemStackTransaction>>writeAction(() -> {
/*     */           if (allOrNothing) {
/*     */             for (ItemStack itemStack : itemStacks) {
/*     */               int itemMaxStack = itemStack.getItem().getMaxStack();
/*     */               int testQuantityRemaining = itemStack.getQuantity();
/*     */               if (!fullStacks) {
/*     */                 testQuantityRemaining = testAddToExistingItemStacks(itemContainer, itemStack, itemMaxStack, testQuantityRemaining, filter);
/*     */               }
/*     */               testQuantityRemaining = testAddToEmptySlots(itemContainer, itemStack, itemMaxStack, testQuantityRemaining, filter);
/*     */               if (testQuantityRemaining > 0) {
/*     */                 return new ListTransaction(false, (List)itemStacks.stream().map(()).collect(Collectors.toList()));
/*     */               }
/*     */             } 
/*     */           }
/*     */           ObjectArrayList<ItemStackTransaction> objectArrayList = new ObjectArrayList();
/*     */           for (ItemStack itemStack : itemStacks) {
/*     */             objectArrayList.add(internal_addItemStack(itemContainer, itemStack, allOrNothing, fullStacks, filter));
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
/*     */   protected static ListTransaction<ItemStackSlotTransaction> internal_addItemStacksOrdered(@Nonnull ItemContainer itemContainer, short offset, @Nullable List<ItemStack> itemStacks, boolean allOrNothing, boolean filter) {
/* 367 */     if (itemStacks == null || itemStacks.isEmpty()) {
/* 368 */       return ListTransaction.getEmptyTransaction(true);
/*     */     }
/*     */     
/* 371 */     ItemContainer.validateSlotIndex(offset, itemContainer.getCapacity());
/* 372 */     ItemContainer.validateSlotIndex((short)(offset + itemStacks.size()), itemContainer.getCapacity());
/*     */     
/* 374 */     return itemContainer.<ListTransaction<ItemStackSlotTransaction>>writeAction(() -> {
/*     */           if (allOrNothing) {
/*     */             short s;
/*     */             for (s = 0; s < itemStacks.size(); s = (short)(s + 1)) {
/*     */               short slot = (short)(offset + s);
/*     */               ItemStack itemStack = itemStacks.get(s);
/*     */               int itemMaxStack = itemStack.getItem().getMaxStack();
/*     */               int testQuantityRemaining = itemStack.getQuantity();
/*     */               testQuantityRemaining = testAddToExistingSlot(itemContainer, slot, itemStack, itemMaxStack, testQuantityRemaining, filter);
/*     */               if (testQuantityRemaining > 0) {
/*     */                 ObjectArrayList<ItemStackSlotTransaction> objectArrayList1 = new ObjectArrayList();
/*     */                 short i1;
/*     */                 for (i1 = 0; i1 < itemStacks.size(); i1 = (short)(i1 + 1)) {
/*     */                   short islot = (short)(offset + i1);
/*     */                   objectArrayList1.add(new ItemStackSlotTransaction(false, ActionType.ADD, islot, null, null, null, allOrNothing, false, filter, false, itemStack, itemStack));
/*     */                 } 
/*     */                 return new ListTransaction(false, (List)objectArrayList1);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           ObjectArrayList<ItemStackSlotTransaction> objectArrayList = new ObjectArrayList();
/*     */           short i;
/*     */           for (i = 0; i < itemStacks.size(); i = (short)(i + 1)) {
/*     */             short slot = (short)(offset + i);
/*     */             objectArrayList.add(internal_addItemStackToSlot(itemContainer, slot, itemStacks.get(i), allOrNothing, filter));
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
/*     */   protected static int testAddToExistingItemStacks(@Nonnull ItemContainer container, ItemStack itemStack, int itemMaxStack, int testQuantityRemaining, boolean filter) {
/*     */     short i;
/* 422 */     for (i = 0; i < container.getCapacity() && testQuantityRemaining > 0; i = (short)(i + 1)) {
/* 423 */       testQuantityRemaining = testAddToExistingSlot(container, i, itemStack, itemMaxStack, testQuantityRemaining, filter);
/*     */     }
/*     */     
/* 426 */     return testQuantityRemaining;
/*     */   }
/*     */   
/*     */   protected static ItemStackTransaction internal_removeItemStack(@Nonnull ItemContainer itemContainer, @Nonnull ItemStack itemStack, boolean allOrNothing, boolean filter) {
/* 430 */     Item item = itemStack.getItem();
/* 431 */     if (item == null) throw new IllegalArgumentException(itemStack.getItemId() + " is an invalid item!");
/*     */     
/* 433 */     return itemContainer.<ItemStackTransaction>writeAction(() -> {
/*     */           if (allOrNothing) {
/*     */             int testQuantityRemaining = testRemoveItemStackFromItems(itemContainer, itemStack, itemStack.getQuantity(), filter);
/*     */             if (testQuantityRemaining > 0) {
/*     */               return new ItemStackTransaction(false, ActionType.REMOVE, itemStack, itemStack, allOrNothing, filter, Collections.emptyList());
/*     */             }
/*     */           } 
/*     */           ObjectArrayList<ItemStackSlotTransaction> objectArrayList = new ObjectArrayList();
/*     */           int quantityRemaining = itemStack.getQuantity();
/*     */           short i = 0;
/*     */           while (i < itemContainer.getCapacity() && quantityRemaining > 0) {
/*     */             ItemStack slotItemStack = itemContainer.internal_getSlot(i);
/*     */             if (!ItemStack.isEmpty(slotItemStack) && slotItemStack.isStackableWith(itemStack)) {
/*     */               ItemStackSlotTransaction transaction = internal_removeItemStackFromSlot(itemContainer, i, quantityRemaining, false, filter);
/*     */               objectArrayList.add(transaction);
/*     */               quantityRemaining = (transaction.getRemainder() != null) ? transaction.getRemainder().getQuantity() : 0;
/*     */             } 
/*     */             i = (short)(i + 1);
/*     */           } 
/*     */           ItemStack remainder = (quantityRemaining > 0) ? itemStack.withQuantity(quantityRemaining) : null;
/*     */           return new ItemStackTransaction(true, ActionType.REMOVE, itemStack, remainder, allOrNothing, filter, (List)objectArrayList);
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static ListTransaction<ItemStackTransaction> internal_removeItemStacks(@Nonnull ItemContainer itemContainer, @Nullable List<ItemStack> itemStacks, boolean allOrNothing, boolean filter) {
/* 464 */     if (itemStacks == null || itemStacks.isEmpty()) {
/* 465 */       return ListTransaction.getEmptyTransaction(true);
/*     */     }
/*     */     
/* 468 */     for (ItemStack itemStack : itemStacks) {
/* 469 */       Item item = itemStack.getItem();
/* 470 */       if (item == null) throw new IllegalArgumentException(itemStack.getItemId() + " is an invalid item!");
/*     */     
/*     */     } 
/* 473 */     return itemContainer.<ListTransaction<ItemStackTransaction>>writeAction(() -> {
/*     */           if (allOrNothing) {
/*     */             for (ItemStack itemStack : itemStacks) {
/*     */               int testQuantityRemaining = testRemoveItemStackFromItems(itemContainer, itemStack, itemStack.getQuantity(), filter);
/*     */               if (testQuantityRemaining > 0) {
/*     */                 return new ListTransaction(false, (List)itemStacks.stream().map(()).collect(Collectors.toList()));
/*     */               }
/*     */             } 
/*     */           }
/*     */           ObjectArrayList<ItemStackTransaction> objectArrayList = new ObjectArrayList();
/*     */           short i;
/*     */           for (i = 0; i < itemStacks.size(); i = (short)(i + 1)) {
/*     */             objectArrayList.add(internal_removeItemStack(itemContainer, itemStacks.get(i), allOrNothing, filter));
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
/*     */   protected static int testRemoveItemStackFromItems(@Nonnull ItemContainer container, ItemStack itemStack, int testQuantityRemaining, boolean filter) {
/*     */     short i;
/* 510 */     for (i = 0; i < container.getCapacity() && testQuantityRemaining > 0; i = (short)(i + 1)) {
/* 511 */       if (!filter || !container.cantRemoveFromSlot(i)) {
/*     */         
/* 513 */         ItemStack slotItemStack = container.internal_getSlot(i);
/* 514 */         if (!ItemStack.isEmpty(slotItemStack) && 
/* 515 */           slotItemStack.isStackableWith(itemStack)) {
/*     */           
/* 517 */           int quantity = slotItemStack.getQuantity();
/* 518 */           int quantityAdjustment = Math.min(quantity, testQuantityRemaining);
/* 519 */           testQuantityRemaining -= quantityAdjustment;
/*     */         } 
/*     */       } 
/* 522 */     }  return testQuantityRemaining;
/*     */   }
/*     */   
/*     */   protected static TestRemoveItemSlotResult testRemoveItemStackSlotFromItems(@Nonnull ItemContainer container, ItemStack itemStack, int testQuantityRemaining, boolean filter) {
/* 526 */     return testRemoveItemStackSlotFromItems(container, itemStack, testQuantityRemaining, filter, (a, b) -> ItemStack.isStackableWith(a, b));
/*     */   }
/*     */   
/*     */   protected static TestRemoveItemSlotResult testRemoveItemStackSlotFromItems(@Nonnull ItemContainer container, ItemStack itemStack, int testQuantityRemaining, boolean filter, BiPredicate<ItemStack, ItemStack> predicate) {
/* 530 */     TestRemoveItemSlotResult result = new TestRemoveItemSlotResult(testQuantityRemaining);
/*     */     short i;
/* 532 */     for (i = 0; i < container.getCapacity() && result.quantityRemaining > 0; i = (short)(i + 1)) {
/* 533 */       if (!filter || !container.cantRemoveFromSlot(i)) {
/*     */         
/* 535 */         ItemStack slotItemStack = container.internal_getSlot(i);
/* 536 */         if (!ItemStack.isEmpty(slotItemStack) && 
/* 537 */           predicate.test(slotItemStack, itemStack)) {
/*     */           
/* 539 */           int quantity = slotItemStack.getQuantity();
/* 540 */           int quantityAdjustment = Math.min(quantity, result.quantityRemaining);
/* 541 */           result.quantityRemaining -= quantityAdjustment;
/* 542 */           result.picked.put(Short.valueOf(i), Integer.valueOf(quantityAdjustment));
/*     */         } 
/*     */       } 
/* 545 */     }  return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\inventory\container\InternalContainerUtilItemStack.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */