/*     */ package com.hypixel.hytale.server.core.inventory.container;
/*     */ 
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.inventory.MaterialQuantity;
/*     */ import com.hypixel.hytale.server.core.inventory.transaction.ActionType;
/*     */ import com.hypixel.hytale.server.core.inventory.transaction.ItemStackSlotTransaction;
/*     */ import com.hypixel.hytale.server.core.inventory.transaction.ListTransaction;
/*     */ import com.hypixel.hytale.server.core.inventory.transaction.MaterialSlotTransaction;
/*     */ import com.hypixel.hytale.server.core.inventory.transaction.MaterialTransaction;
/*     */ import com.hypixel.hytale.server.core.inventory.transaction.ResourceSlotTransaction;
/*     */ import com.hypixel.hytale.server.core.inventory.transaction.SlotTransaction;
/*     */ import com.hypixel.hytale.server.core.inventory.transaction.TagSlotTransaction;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.stream.Collectors;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class InternalContainerUtilMaterial {
/*     */   @Nonnull
/*     */   protected static MaterialSlotTransaction internal_removeMaterialFromSlot(@Nonnull ItemContainer itemContainer, short slot, @Nonnull MaterialQuantity material, boolean allOrNothing, boolean filter) {
/*  23 */     ItemContainer.validateSlotIndex(slot, itemContainer.getCapacity());
/*  24 */     ItemContainer.validateQuantity(material.getQuantity());
/*     */     
/*  26 */     if (material.getItemId() != null) {
/*  27 */       ItemStackSlotTransaction slotTransaction = InternalContainerUtilItemStack.internal_removeItemStackFromSlot(itemContainer, slot, material.toItemStack(), material.getQuantity(), allOrNothing, filter, (a, b) -> ItemStack.isEquivalentType(a, b));
/*  28 */       return new MaterialSlotTransaction(material, (slotTransaction.getRemainder() != null) ? slotTransaction.getRemainder().getQuantity() : 0, (SlotTransaction)slotTransaction);
/*     */     } 
/*  30 */     if (material.getTagIndex() != Integer.MIN_VALUE) {
/*  31 */       TagSlotTransaction tagTransaction = InternalContainerUtilTag.internal_removeTagFromSlot(itemContainer, slot, material.getTagIndex(), material.getQuantity(), allOrNothing, filter);
/*  32 */       return new MaterialSlotTransaction(material, tagTransaction.getRemainder(), (SlotTransaction)tagTransaction);
/*     */     } 
/*  34 */     ResourceSlotTransaction resourceTransaction = InternalContainerUtilResource.internal_removeResourceFromSlot(itemContainer, slot, material.toResource(), allOrNothing, filter);
/*  35 */     return new MaterialSlotTransaction(material, resourceTransaction.getRemainder(), (SlotTransaction)resourceTransaction);
/*     */   }
/*     */   
/*     */   protected static MaterialTransaction internal_removeMaterial(@Nonnull ItemContainer itemContainer, @Nonnull MaterialQuantity material, boolean allOrNothing, boolean exactAmount, boolean filter) {
/*  39 */     return itemContainer.<MaterialTransaction>writeAction(() -> {
/*     */           if (allOrNothing || exactAmount) {
/*     */             int testQuantityRemaining = testRemoveMaterialFromItems(itemContainer, material, material.getQuantity(), filter);
/*     */             if (testQuantityRemaining > 0) {
/*     */               return new MaterialTransaction(false, ActionType.REMOVE, material, material.getQuantity(), allOrNothing, exactAmount, filter, Collections.emptyList());
/*     */             }
/*     */             if (exactAmount && testQuantityRemaining < 0) {
/*     */               return new MaterialTransaction(false, ActionType.REMOVE, material, material.getQuantity(), allOrNothing, exactAmount, filter, Collections.emptyList());
/*     */             }
/*     */           } 
/*     */           ObjectArrayList<MaterialSlotTransaction> objectArrayList = new ObjectArrayList();
/*     */           int quantityRemaining = material.getQuantity();
/*     */           short i = 0;
/*     */           while (i < itemContainer.getCapacity() && quantityRemaining > 0) {
/*     */             MaterialQuantity clone = material.clone(quantityRemaining);
/*     */             MaterialSlotTransaction transaction = internal_removeMaterialFromSlot(itemContainer, i, clone, false, filter);
/*     */             if (transaction.succeeded()) {
/*     */               objectArrayList.add(transaction);
/*     */               quantityRemaining = transaction.getRemainder();
/*     */             } 
/*     */             i = (short)(i + 1);
/*     */           } 
/*     */           return new MaterialTransaction((quantityRemaining != material.getQuantity()), ActionType.REMOVE, material, material.getQuantity(), allOrNothing, exactAmount, filter, (List)objectArrayList);
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static ListTransaction<MaterialTransaction> internal_removeMaterials(@Nonnull ItemContainer itemContainer, @Nullable List<MaterialQuantity> materials, boolean allOrNothing, boolean exactAmount, boolean filter) {
/*  71 */     if (materials == null || materials.isEmpty()) {
/*  72 */       return ListTransaction.getEmptyTransaction(true);
/*     */     }
/*     */     
/*  75 */     return itemContainer.<ListTransaction<MaterialTransaction>>writeAction(() -> {
/*     */           if (allOrNothing || exactAmount) {
/*     */             for (MaterialQuantity material : materials) {
/*     */               int testQuantityRemaining = testRemoveMaterialFromItems(itemContainer, material, material.getQuantity(), filter);
/*     */               if (testQuantityRemaining > 0) {
/*     */                 return new ListTransaction(false, (List)materials.stream().map(()).collect(Collectors.toList()));
/*     */               }
/*     */               if (exactAmount && testQuantityRemaining < 0) {
/*     */                 return new ListTransaction(false, (List)materials.stream().map(()).collect(Collectors.toList()));
/*     */               }
/*     */             } 
/*     */           }
/*     */           ObjectArrayList<MaterialTransaction> objectArrayList = new ObjectArrayList();
/*     */           for (MaterialQuantity material : materials) {
/*     */             objectArrayList.add(internal_removeMaterial(itemContainer, material, allOrNothing, exactAmount, filter));
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
/*     */   public static int testRemoveMaterialFromItems(@Nonnull ItemContainer container, @Nonnull MaterialQuantity material, int testQuantityRemaining, boolean filter) {
/* 107 */     if (material.getItemId() != null) {
/* 108 */       return InternalContainerUtilItemStack.testRemoveItemStackFromItems(container, material.toItemStack(), testQuantityRemaining, filter);
/*     */     }
/* 110 */     if (material.getTagIndex() != Integer.MIN_VALUE) {
/* 111 */       return InternalContainerUtilTag.testRemoveTagFromItems(container, material.getTagIndex(), testQuantityRemaining, filter);
/*     */     }
/* 113 */     return InternalContainerUtilResource.testRemoveResourceFromItems(container, material.toResource(), testQuantityRemaining, filter);
/*     */   }
/*     */   
/*     */   public static TestRemoveItemSlotResult getTestRemoveMaterialFromItems(@Nonnull ItemContainer container, @Nonnull MaterialQuantity material, int testQuantityRemaining, boolean filter) {
/* 117 */     if (material.getItemId() != null) {
/* 118 */       return InternalContainerUtilItemStack.testRemoveItemStackSlotFromItems(container, material.toItemStack(), testQuantityRemaining, filter, (a, b) -> ItemStack.isEquivalentType(a, b));
/*     */     }
/* 120 */     if (material.getTagIndex() != Integer.MIN_VALUE) {
/* 121 */       return InternalContainerUtilTag.testRemoveTagSlotFromItems(container, material.getTagIndex(), testQuantityRemaining, filter);
/*     */     }
/* 123 */     return InternalContainerUtilResource.testRemoveResourceSlotFromItems(container, material.toResource(), testQuantityRemaining, filter);
/*     */   }
/*     */   
/*     */   protected static ListTransaction<MaterialSlotTransaction> internal_removeMaterialsOrdered(@Nonnull ItemContainer itemContainer, short offset, @Nullable List<MaterialQuantity> materials, boolean allOrNothing, boolean exactAmount, boolean filter) {
/* 127 */     if (materials == null || materials.isEmpty()) {
/* 128 */       return ListTransaction.getEmptyTransaction(true);
/*     */     }
/*     */ 
/*     */     
/* 132 */     if (offset + materials.size() > itemContainer.getCapacity()) {
/* 133 */       return ListTransaction.getEmptyTransaction(false);
/*     */     }
/*     */     
/* 136 */     return itemContainer.<ListTransaction<MaterialSlotTransaction>>writeAction(() -> {
/*     */           if (allOrNothing || exactAmount) {
/*     */             short s;
/*     */             for (s = 0; s < materials.size(); s = (short)(s + 1)) {
/*     */               short slot = (short)(offset + s);
/*     */               MaterialQuantity material = materials.get(s);
/*     */               int testQuantityRemaining = testRemoveMaterialFromSlot(itemContainer, slot, material, material.getQuantity(), filter);
/*     */               if (testQuantityRemaining > 0) {
/*     */                 ObjectArrayList<MaterialSlotTransaction> objectArrayList1 = new ObjectArrayList();
/*     */                 short i1;
/*     */                 for (i1 = 0; i1 < materials.size(); i1 = (short)(i1 + 1)) {
/*     */                   short islot = (short)(offset + i1);
/*     */                   objectArrayList1.add(new MaterialSlotTransaction(material, material.getQuantity(), new SlotTransaction(false, ActionType.REMOVE, islot, null, null, null, allOrNothing, exactAmount, filter)));
/*     */                 } 
/*     */                 return new ListTransaction(false, (List)objectArrayList1);
/*     */               } 
/*     */               if (exactAmount && testQuantityRemaining < 0) {
/*     */                 ObjectArrayList<MaterialSlotTransaction> objectArrayList1 = new ObjectArrayList();
/*     */                 short i1;
/*     */                 for (i1 = 0; i1 < materials.size(); i1 = (short)(i1 + 1)) {
/*     */                   short islot = (short)(offset + i1);
/*     */                   objectArrayList1.add(new MaterialSlotTransaction(material, material.getQuantity(), new SlotTransaction(false, ActionType.REMOVE, islot, null, null, null, allOrNothing, exactAmount, filter)));
/*     */                 } 
/*     */                 return new ListTransaction(false, (List)objectArrayList1);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           ObjectArrayList<MaterialSlotTransaction> objectArrayList = new ObjectArrayList();
/*     */           short i;
/*     */           for (i = 0; i < materials.size(); i = (short)(i + 1)) {
/*     */             short slot = (short)(offset + i);
/*     */             MaterialQuantity material = materials.get(i);
/*     */             objectArrayList.add(internal_removeMaterialFromSlot(itemContainer, slot, material, allOrNothing, filter));
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
/*     */   public static int testRemoveMaterialFromSlot(@Nonnull ItemContainer container, short slot, @Nonnull MaterialQuantity material, int testQuantityRemaining, boolean filter) {
/* 180 */     if (material.getItemId() != null) {
/* 181 */       return InternalContainerUtilItemStack.testRemoveItemStackFromSlot(container, slot, material.toItemStack(), testQuantityRemaining, filter, (a, b) -> ItemStack.isEquivalentType(a, b));
/*     */     }
/* 183 */     if (material.getTagIndex() != Integer.MIN_VALUE) {
/* 184 */       return InternalContainerUtilTag.testRemoveTagFromSlot(container, slot, material.getTagIndex(), testQuantityRemaining, filter);
/*     */     }
/* 186 */     return InternalContainerUtilResource.testRemoveResourceFromSlot(container, slot, material.toResource(), testQuantityRemaining, filter);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\inventory\container\InternalContainerUtilMaterial.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */