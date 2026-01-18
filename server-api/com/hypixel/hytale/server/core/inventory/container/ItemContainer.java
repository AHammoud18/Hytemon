/*      */ package com.hypixel.hytale.server.core.inventory.container;
/*      */ import com.hypixel.fastutil.shorts.Short2ObjectConcurrentHashMap;
/*      */ import com.hypixel.hytale.event.EventRegistration;
/*      */ import com.hypixel.hytale.event.SyncEventBusRegistry;
/*      */ import com.hypixel.hytale.protocol.InventorySection;
/*      */ import com.hypixel.hytale.protocol.ItemResourceType;
/*      */ import com.hypixel.hytale.protocol.ItemWithAllMetadata;
/*      */ import com.hypixel.hytale.server.core.asset.type.item.config.Item;
/*      */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*      */ import com.hypixel.hytale.server.core.inventory.MaterialQuantity;
/*      */ import com.hypixel.hytale.server.core.inventory.ResourceQuantity;
/*      */ import com.hypixel.hytale.server.core.inventory.transaction.ActionType;
/*      */ import com.hypixel.hytale.server.core.inventory.transaction.ClearTransaction;
/*      */ import com.hypixel.hytale.server.core.inventory.transaction.ItemStackSlotTransaction;
/*      */ import com.hypixel.hytale.server.core.inventory.transaction.ItemStackTransaction;
/*      */ import com.hypixel.hytale.server.core.inventory.transaction.ListTransaction;
/*      */ import com.hypixel.hytale.server.core.inventory.transaction.MaterialSlotTransaction;
/*      */ import com.hypixel.hytale.server.core.inventory.transaction.MaterialTransaction;
/*      */ import com.hypixel.hytale.server.core.inventory.transaction.MoveTransaction;
/*      */ import com.hypixel.hytale.server.core.inventory.transaction.MoveType;
/*      */ import com.hypixel.hytale.server.core.inventory.transaction.ResourceSlotTransaction;
/*      */ import com.hypixel.hytale.server.core.inventory.transaction.ResourceTransaction;
/*      */ import com.hypixel.hytale.server.core.inventory.transaction.SlotTransaction;
/*      */ import com.hypixel.hytale.server.core.inventory.transaction.TagSlotTransaction;
/*      */ import com.hypixel.hytale.server.core.inventory.transaction.TagTransaction;
/*      */ import com.hypixel.hytale.server.core.inventory.transaction.Transaction;
/*      */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*      */ import it.unimi.dsi.fastutil.ints.IntArrays;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.function.Consumer;
/*      */ import java.util.function.Function;
/*      */ import java.util.function.Predicate;
/*      */ import javax.annotation.Nonnull;
/*      */ import javax.annotation.Nullable;
/*      */ 
/*      */ public abstract class ItemContainer {
/*   40 */   public static final CodecMapCodec<ItemContainer> CODEC = new CodecMapCodec(true);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final boolean DEFAULT_ADD_ALL_OR_NOTHING = false;
/*      */ 
/*      */ 
/*      */   
/*      */   public static final boolean DEFAULT_REMOVE_ALL_OR_NOTHING = true;
/*      */ 
/*      */ 
/*      */   
/*      */   public static final boolean DEFAULT_FULL_STACKS = false;
/*      */ 
/*      */ 
/*      */   
/*      */   public static final boolean DEFAULT_EXACT_AMOUNT = true;
/*      */ 
/*      */ 
/*      */   
/*      */   public static final boolean DEFAULT_FILTER = true;
/*      */ 
/*      */ 
/*      */   
/*   65 */   protected static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   70 */   protected final SyncEventBusRegistry<Void, ItemContainerChangeEvent> externalChangeEventRegistry = new SyncEventBusRegistry(LOGGER, ItemContainerChangeEvent.class);
/*      */ 
/*      */ 
/*      */   
/*   74 */   protected final SyncEventBusRegistry<Void, ItemContainerChangeEvent> internalChangeEventRegistry = new SyncEventBusRegistry(LOGGER, ItemContainerChangeEvent.class);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public InventorySection toPacket() {
/*  126 */     InventorySection packet = new InventorySection();
/*  127 */     packet.capacity = getCapacity();
/*  128 */     packet.items = toProtocolMap();
/*  129 */     return packet;
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public Map<Integer, ItemWithAllMetadata> toProtocolMap() {
/*  134 */     Int2ObjectOpenHashMap int2ObjectOpenHashMap = new Int2ObjectOpenHashMap();
/*  135 */     forEachWithMeta((slot, itemStack, _map) -> { if (ItemStack.isEmpty(itemStack) || !itemStack.isValid()) return;  _map.put(Integer.valueOf(slot), itemStack.toPacket()); }int2ObjectOpenHashMap);
/*      */ 
/*      */ 
/*      */     
/*  139 */     return (Map<Integer, ItemWithAllMetadata>)int2ObjectOpenHashMap;
/*      */   }
/*      */ 
/*      */   
/*      */   public EventRegistration registerChangeEvent(@Nonnull Consumer<ItemContainerChangeEvent> consumer) {
/*  144 */     return registerChangeEvent((short)0, consumer);
/*      */   }
/*      */ 
/*      */   
/*      */   public EventRegistration registerChangeEvent(@Nonnull EventPriority priority, @Nonnull Consumer<ItemContainerChangeEvent> consumer) {
/*  149 */     return registerChangeEvent(priority.getValue(), consumer);
/*      */   }
/*      */ 
/*      */   
/*      */   public EventRegistration registerChangeEvent(short priority, @Nonnull Consumer<ItemContainerChangeEvent> consumer) {
/*  154 */     return this.externalChangeEventRegistry.register(priority, null, consumer);
/*      */   }
/*      */   
/*      */   public ClearTransaction clear() {
/*  158 */     ClearTransaction transaction = writeAction(this::internal_clear);
/*  159 */     sendUpdate((Transaction)transaction);
/*  160 */     return transaction;
/*      */   }
/*      */   
/*      */   public boolean canAddItemStackToSlot(short slot, @Nonnull ItemStack itemStack, boolean allOrNothing, boolean filter) {
/*  164 */     validateSlotIndex(slot, getCapacity());
/*      */     
/*  166 */     return ((Boolean)writeAction(() -> { int quantityRemaining = itemStack.getQuantity(); ItemStack slotItemStack = internal_getSlot(slot); if (filter && cantAddToSlot(slot, itemStack, slotItemStack)) return Boolean.valueOf(false);  if (slotItemStack == null) return Boolean.valueOf(true);  if (!itemStack.isStackableWith(slotItemStack)) return Boolean.valueOf(false);  int quantity = slotItemStack.getQuantity(); int quantityAdjustment = Math.min(slotItemStack.getItem().getMaxStack() - quantity, quantityRemaining); int newQuantityRemaining = quantityRemaining - quantityAdjustment; return allOrNothing ? Boolean.valueOf((quantityRemaining <= 0)) : Boolean.valueOf((quantityRemaining != newQuantityRemaining)); })).booleanValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public ItemStackSlotTransaction addItemStackToSlot(short slot, @Nonnull ItemStack itemStack) {
/*  185 */     return addItemStackToSlot(slot, itemStack, false, true);
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public ItemStackSlotTransaction addItemStackToSlot(short slot, @Nonnull ItemStack itemStack, boolean allOrNothing, boolean filter) {
/*  190 */     ItemStackSlotTransaction transaction = InternalContainerUtilItemStack.internal_addItemStackToSlot(this, slot, itemStack, allOrNothing, filter);
/*  191 */     sendUpdate((Transaction)transaction);
/*  192 */     return transaction;
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public ItemStackSlotTransaction setItemStackForSlot(short slot, ItemStack itemStack) {
/*  197 */     return setItemStackForSlot(slot, itemStack, true);
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public ItemStackSlotTransaction setItemStackForSlot(short slot, ItemStack itemStack, boolean filter) {
/*  202 */     ItemStackSlotTransaction transaction = InternalContainerUtilItemStack.internal_setItemStackForSlot(this, slot, itemStack, filter);
/*  203 */     sendUpdate((Transaction)transaction);
/*  204 */     return transaction;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public ItemStack getItemStack(short slot) {
/*  209 */     validateSlotIndex(slot, getCapacity());
/*      */     
/*  211 */     return readAction(() -> internal_getSlot(slot));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public ItemStackSlotTransaction replaceItemStackInSlot(short slot, ItemStack itemStackToRemove, ItemStack itemStack) {
/*  218 */     ItemStackSlotTransaction transaction = internal_replaceItemStack(slot, itemStackToRemove, itemStack);
/*  219 */     sendUpdate((Transaction)transaction);
/*  220 */     return transaction;
/*      */   }
/*      */   
/*      */   public ListTransaction<ItemStackSlotTransaction> replaceAll(SlotReplacementFunction func) {
/*  224 */     return replaceAll(func, true);
/*      */   }
/*      */   
/*      */   private ListTransaction<ItemStackSlotTransaction> replaceAll(SlotReplacementFunction func, boolean ignoreEmpty) {
/*  228 */     ListTransaction<ItemStackSlotTransaction> transaction = writeAction(() -> {
/*      */           short capacity = getCapacity(); ObjectArrayList<ItemStackSlotTransaction> transactionsList = new ObjectArrayList(capacity); short slot;
/*      */           for (slot = 0; slot < capacity; slot = (short)(slot + 1)) {
/*      */             ItemStack existing = internal_getSlot(slot);
/*      */             if (!ignoreEmpty || !ItemStack.isEmpty(existing)) {
/*      */               ItemStack replacement = func.replace(slot, existing);
/*      */               internal_setSlot(slot, replacement);
/*      */               transactionsList.add(new ItemStackSlotTransaction(true, ActionType.REPLACE, slot, existing, replacement, existing, true, false, false, false, replacement, replacement));
/*      */             } 
/*      */           } 
/*      */           return new ListTransaction(true, (List)transactionsList);
/*      */         });
/*  240 */     sendUpdate((Transaction)transaction);
/*  241 */     return transaction;
/*      */   }
/*      */   
/*      */   protected ItemStackSlotTransaction internal_replaceItemStack(short slot, @Nullable ItemStack itemStackToRemove, ItemStack itemStack) {
/*  245 */     validateSlotIndex(slot, getCapacity());
/*      */     
/*  247 */     return writeAction(() -> {
/*      */           ItemStack slotItemStack = internal_getSlot(slot);
/*      */           if ((slotItemStack == null && itemStackToRemove != null) || (slotItemStack != null && itemStackToRemove == null) || (slotItemStack != null && !itemStackToRemove.isStackableWith(slotItemStack))) {
/*      */             return new ItemStackSlotTransaction(false, ActionType.REPLACE, slot, slotItemStack, slotItemStack, null, true, false, false, false, itemStack, itemStack);
/*      */           }
/*      */           internal_setSlot(slot, itemStack);
/*      */           return new ItemStackSlotTransaction(true, ActionType.REPLACE, slot, slotItemStack, itemStack, slotItemStack, true, false, false, false, itemStack, null);
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public SlotTransaction removeItemStackFromSlot(short slot) {
/*  264 */     return removeItemStackFromSlot(slot, true);
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public SlotTransaction removeItemStackFromSlot(short slot, boolean filter) {
/*  269 */     SlotTransaction transaction = InternalContainerUtilItemStack.internal_removeItemStackFromSlot(this, slot, filter);
/*  270 */     sendUpdate((Transaction)transaction);
/*  271 */     return transaction;
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public ItemStackSlotTransaction removeItemStackFromSlot(short slot, int quantityToRemove) {
/*  276 */     return removeItemStackFromSlot(slot, quantityToRemove, true, true);
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public ItemStackSlotTransaction removeItemStackFromSlot(short slot, int quantityToRemove, boolean allOrNothing, boolean filter) {
/*  281 */     ItemStackSlotTransaction transaction = InternalContainerUtilItemStack.internal_removeItemStackFromSlot(this, slot, quantityToRemove, allOrNothing, filter);
/*  282 */     sendUpdate((Transaction)transaction);
/*  283 */     return transaction;
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public ItemStackSlotTransaction internal_removeItemStack(short slot, int quantityToRemove) {
/*  288 */     return InternalContainerUtilItemStack.internal_removeItemStackFromSlot(this, slot, quantityToRemove, true, true);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public ItemStackSlotTransaction removeItemStackFromSlot(short slot, ItemStack itemStackToRemove, int quantityToRemove) {
/*  295 */     return removeItemStackFromSlot(slot, itemStackToRemove, quantityToRemove, true, true);
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public ItemStackSlotTransaction removeItemStackFromSlot(short slot, ItemStack itemStackToRemove, int quantityToRemove, boolean allOrNothing, boolean filter) {
/*  300 */     ItemStackSlotTransaction transaction = InternalContainerUtilItemStack.internal_removeItemStackFromSlot(this, slot, itemStackToRemove, quantityToRemove, allOrNothing, filter);
/*  301 */     sendUpdate((Transaction)transaction);
/*  302 */     return transaction;
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public MaterialSlotTransaction removeMaterialFromSlot(short slot, @Nonnull MaterialQuantity material) {
/*  307 */     return removeMaterialFromSlot(slot, material, true, true, true);
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public MaterialSlotTransaction removeMaterialFromSlot(short slot, @Nonnull MaterialQuantity material, boolean allOrNothing, boolean exactAmount, boolean filter) {
/*  312 */     MaterialSlotTransaction transaction = InternalContainerUtilMaterial.internal_removeMaterialFromSlot(this, slot, material, allOrNothing, filter);
/*  313 */     sendUpdate((Transaction)transaction);
/*  314 */     return transaction;
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public ResourceSlotTransaction removeResourceFromSlot(short slot, @Nonnull ResourceQuantity resource) {
/*  319 */     return removeResourceFromSlot(slot, resource, true, true, true);
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public ResourceSlotTransaction removeResourceFromSlot(short slot, @Nonnull ResourceQuantity resource, boolean allOrNothing, boolean exactAmount, boolean filter) {
/*  324 */     ResourceSlotTransaction transaction = InternalContainerUtilResource.internal_removeResourceFromSlot(this, slot, resource, allOrNothing, filter);
/*  325 */     sendUpdate((Transaction)transaction);
/*  326 */     return transaction;
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public TagSlotTransaction removeTagFromSlot(short slot, int tagIndex, int quantity) {
/*  331 */     return removeTagFromSlot(slot, tagIndex, quantity, true, true);
/*      */   }
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public TagSlotTransaction removeTagFromSlot(short slot, int tagIndex, int quantity, boolean allOrNothing, boolean filter) {
/*  337 */     TagSlotTransaction transaction = InternalContainerUtilTag.internal_removeTagFromSlot(this, slot, tagIndex, quantity, allOrNothing, filter);
/*  338 */     sendUpdate((Transaction)transaction);
/*  339 */     return transaction;
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public MoveTransaction<ItemStackTransaction> moveItemStackFromSlot(short slot, @Nonnull ItemContainer containerTo) {
/*  344 */     return moveItemStackFromSlot(slot, containerTo, true);
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public MoveTransaction<ItemStackTransaction> moveItemStackFromSlot(short slot, @Nonnull ItemContainer containerTo, boolean filter) {
/*  349 */     return moveItemStackFromSlot(slot, containerTo, false, filter);
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public MoveTransaction<ItemStackTransaction> moveItemStackFromSlot(short slot, @Nonnull ItemContainer containerTo, boolean allOrNothing, boolean filter) {
/*  354 */     MoveTransaction<ItemStackTransaction> transaction = internal_moveItemStackFromSlot(slot, containerTo, allOrNothing, filter);
/*  355 */     sendUpdate((Transaction)transaction);
/*  356 */     containerTo.sendUpdate((Transaction)transaction.toInverted(this));
/*  357 */     return transaction;
/*      */   }
/*      */   
/*      */   protected MoveTransaction<ItemStackTransaction> internal_moveItemStackFromSlot(short slot, @Nonnull ItemContainer containerTo, boolean allOrNothing, boolean filter) {
/*  361 */     validateSlotIndex(slot, getCapacity());
/*      */     
/*  363 */     return writeAction(() -> (MoveTransaction)containerTo.<MoveTransaction>writeAction(()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public MoveTransaction<ItemStackTransaction> moveItemStackFromSlot(short slot, int quantity, @Nonnull ItemContainer containerTo) {
/*  383 */     return moveItemStackFromSlot(slot, quantity, containerTo, false, true);
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public MoveTransaction<ItemStackTransaction> moveItemStackFromSlot(short slot, int quantity, @Nonnull ItemContainer containerTo, boolean allOrNothing, boolean filter) {
/*  388 */     MoveTransaction<ItemStackTransaction> transaction = internal_moveItemStackFromSlot(slot, quantity, containerTo, allOrNothing, filter);
/*  389 */     sendUpdate((Transaction)transaction);
/*  390 */     containerTo.sendUpdate((Transaction)transaction.toInverted(this));
/*  391 */     return transaction;
/*      */   }
/*      */   
/*      */   protected MoveTransaction<ItemStackTransaction> internal_moveItemStackFromSlot(short slot, int quantity, @Nonnull ItemContainer containerTo, boolean allOrNothing, boolean filter) {
/*  395 */     validateSlotIndex(slot, getCapacity());
/*  396 */     validateQuantity(quantity);
/*      */     
/*  398 */     return writeAction(() -> (MoveTransaction)containerTo.<MoveTransaction>writeAction(()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public ListTransaction<MoveTransaction<ItemStackTransaction>> moveItemStackFromSlot(short slot, ItemContainer... containerTo) {
/*  431 */     return moveItemStackFromSlot(slot, false, true, containerTo);
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public ListTransaction<MoveTransaction<ItemStackTransaction>> moveItemStackFromSlot(short slot, boolean allOrNothing, boolean filter, @Nonnull ItemContainer... containerTo) {
/*  436 */     ListTransaction<MoveTransaction<ItemStackTransaction>> transaction = internal_moveItemStackFromSlot(slot, allOrNothing, filter, containerTo);
/*  437 */     sendUpdate((Transaction)transaction);
/*  438 */     for (MoveTransaction<ItemStackTransaction> moveItemStackTransaction : (Iterable<MoveTransaction<ItemStackTransaction>>)transaction.getList()) {
/*  439 */       moveItemStackTransaction.getOtherContainer().sendUpdate((Transaction)moveItemStackTransaction.toInverted(this));
/*      */     }
/*  441 */     return transaction;
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   private ListTransaction<MoveTransaction<ItemStackTransaction>> internal_moveItemStackFromSlot(short slot, boolean allOrNothing, boolean filter, @Nonnull ItemContainer[] containerTo) {
/*  446 */     ObjectArrayList<MoveTransaction<ItemStackTransaction>> objectArrayList = new ObjectArrayList();
/*  447 */     for (ItemContainer itemContainer : containerTo) {
/*  448 */       MoveTransaction<ItemStackTransaction> transaction = internal_moveItemStackFromSlot(slot, itemContainer, allOrNothing, filter);
/*  449 */       objectArrayList.add(transaction);
/*      */       
/*  451 */       if (transaction.succeeded())
/*      */       
/*      */       { 
/*  454 */         ItemStackTransaction addTransaction = (ItemStackTransaction)transaction.getAddTransaction();
/*  455 */         if (ItemStack.isEmpty(addTransaction.getRemainder()))
/*      */           break;  } 
/*  457 */     }  return new ListTransaction(!objectArrayList.isEmpty(), (List)objectArrayList);
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public ListTransaction<MoveTransaction<ItemStackTransaction>> moveItemStackFromSlot(short slot, int quantity, ItemContainer... containerTo) {
/*  462 */     return moveItemStackFromSlot(slot, quantity, false, true, containerTo);
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public ListTransaction<MoveTransaction<ItemStackTransaction>> moveItemStackFromSlot(short slot, int quantity, boolean allOrNothing, boolean filter, @Nonnull ItemContainer... containerTo) {
/*  467 */     ListTransaction<MoveTransaction<ItemStackTransaction>> transaction = internal_moveItemStackFromSlot(slot, quantity, allOrNothing, filter, containerTo);
/*  468 */     sendUpdate((Transaction)transaction);
/*  469 */     for (MoveTransaction<ItemStackTransaction> moveItemStackTransaction : (Iterable<MoveTransaction<ItemStackTransaction>>)transaction.getList()) {
/*  470 */       moveItemStackTransaction.getOtherContainer().sendUpdate((Transaction)moveItemStackTransaction.toInverted(this));
/*      */     }
/*  472 */     return transaction;
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   private ListTransaction<MoveTransaction<ItemStackTransaction>> internal_moveItemStackFromSlot(short slot, int quantity, boolean allOrNothing, boolean filter, @Nonnull ItemContainer[] containerTo) {
/*  477 */     ObjectArrayList<MoveTransaction<ItemStackTransaction>> objectArrayList = new ObjectArrayList();
/*  478 */     for (ItemContainer itemContainer : containerTo) {
/*  479 */       MoveTransaction<ItemStackTransaction> transaction = internal_moveItemStackFromSlot(slot, quantity, itemContainer, allOrNothing, filter);
/*  480 */       objectArrayList.add(transaction);
/*      */       
/*  482 */       if (transaction.succeeded())
/*      */       
/*      */       { 
/*  485 */         ItemStackTransaction addTransaction = (ItemStackTransaction)transaction.getAddTransaction();
/*  486 */         if (ItemStack.isEmpty(addTransaction.getRemainder()))
/*      */           break;  } 
/*  488 */     }  return new ListTransaction(!objectArrayList.isEmpty(), (List)objectArrayList);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public MoveTransaction<SlotTransaction> moveItemStackFromSlotToSlot(short slot, int quantity, @Nonnull ItemContainer containerTo, short slotTo) {
/*  495 */     return moveItemStackFromSlotToSlot(slot, quantity, containerTo, slotTo, true);
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public MoveTransaction<SlotTransaction> moveItemStackFromSlotToSlot(short slot, int quantity, @Nonnull ItemContainer containerTo, short slotTo, boolean filter) {
/*  500 */     MoveTransaction<SlotTransaction> transaction = internal_moveItemStackFromSlot(slot, quantity, containerTo, slotTo, filter);
/*  501 */     sendUpdate((Transaction)transaction);
/*  502 */     containerTo.sendUpdate((Transaction)transaction.toInverted(this));
/*  503 */     return transaction;
/*      */   }
/*      */   
/*      */   protected MoveTransaction<SlotTransaction> internal_moveItemStackFromSlot(short slot, int quantity, @Nonnull ItemContainer containerTo, short slotTo, boolean filter) {
/*  507 */     validateSlotIndex(slot, getCapacity());
/*  508 */     validateSlotIndex(slotTo, containerTo.getCapacity());
/*  509 */     validateQuantity(quantity);
/*      */     
/*  511 */     return writeAction(() -> (MoveTransaction)containerTo.<MoveTransaction>writeAction(()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public ListTransaction<MoveTransaction<ItemStackTransaction>> moveAllItemStacksTo(ItemContainer... containerTo) {
/*  606 */     return moveAllItemStacksTo(null, containerTo);
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public ListTransaction<MoveTransaction<ItemStackTransaction>> moveAllItemStacksTo(Predicate<ItemStack> itemPredicate, ItemContainer... containerTo) {
/*  611 */     ListTransaction<MoveTransaction<ItemStackTransaction>> transaction = internal_moveAllItemStacksTo(itemPredicate, containerTo);
/*  612 */     sendUpdate((Transaction)transaction);
/*  613 */     for (MoveTransaction<ItemStackTransaction> moveItemStackTransaction : (Iterable<MoveTransaction<ItemStackTransaction>>)transaction.getList()) {
/*  614 */       moveItemStackTransaction.getOtherContainer().sendUpdate((Transaction)moveItemStackTransaction.toInverted(this));
/*      */     }
/*  616 */     return transaction;
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   protected ListTransaction<MoveTransaction<ItemStackTransaction>> internal_moveAllItemStacksTo(@Nullable Predicate<ItemStack> itemPredicate, ItemContainer[] containerTo) {
/*  621 */     return writeAction(() -> {
/*      */           ObjectArrayList objectArrayList = new ObjectArrayList();
/*      */           short i;
/*      */           for (i = 0; i < getCapacity(); i = (short)(i + 1)) {
/*      */             if (!cantRemoveFromSlot(i)) {
/*      */               ItemStack checkedItem = internal_getSlot(i);
/*      */               if (!ItemStack.isEmpty(checkedItem) && (itemPredicate == null || itemPredicate.test(checkedItem)))
/*      */                 objectArrayList.addAll(moveItemStackFromSlot(i, containerTo).getList()); 
/*      */             } 
/*      */           } 
/*      */           return new ListTransaction(true, (List)objectArrayList);
/*      */         });
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public ListTransaction<MoveTransaction<ItemStackTransaction>> quickStackTo(@Nonnull ItemContainer... containerTo) {
/*  637 */     return moveAllItemStacksTo(itemStack -> { for (ItemContainer itemContainer : containerTo) { if (itemContainer.containsItemStacksStackableWith(itemStack)) return true;  }  return false; }containerTo);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public ListTransaction<MoveTransaction<SlotTransaction>> combineItemStacksIntoSlot(@Nonnull ItemContainer containerTo, short slotTo) {
/*  647 */     ListTransaction<MoveTransaction<SlotTransaction>> transaction = internal_combineItemStacksIntoSlot(containerTo, slotTo);
/*  648 */     sendUpdate((Transaction)transaction);
/*  649 */     for (MoveTransaction<SlotTransaction> moveSlotTransaction : (Iterable<MoveTransaction<SlotTransaction>>)transaction.getList()) {
/*  650 */       moveSlotTransaction.getOtherContainer().sendUpdate((Transaction)moveSlotTransaction.toInverted(this));
/*      */     }
/*  652 */     return transaction;
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   protected ListTransaction<MoveTransaction<SlotTransaction>> internal_combineItemStacksIntoSlot(@Nonnull ItemContainer containerTo, short slotTo) {
/*  657 */     validateSlotIndex(slotTo, containerTo.getCapacity());
/*  658 */     return writeAction(() -> {
/*      */           ItemStack itemStack = containerTo.internal_getSlot(slotTo);
/*      */           Item item = itemStack.getItem();
/*      */           int maxStack = item.getMaxStack();
/*      */           if (ItemStack.isEmpty(itemStack) || itemStack.getQuantity() >= maxStack) {
/*      */             return new ListTransaction(false, Collections.emptyList());
/*      */           }
/*      */           int count = 0;
/*      */           int[] quantities = new int[getCapacity()];
/*      */           int[] indexes = new int[getCapacity()];
/*      */           short i;
/*      */           for (i = 0; i < getCapacity(); i = (short)(i + 1)) {
/*      */             if (!cantRemoveFromSlot(i)) {
/*      */               ItemStack itemFrom = internal_getSlot(i);
/*      */               if (itemStack != itemFrom) {
/*      */                 if (!ItemStack.isEmpty(itemFrom) && itemFrom.isStackableWith(itemStack)) {
/*      */                   indexes[count] = i;
/*      */                   quantities[count] = itemFrom.getQuantity();
/*      */                   count++;
/*      */                 } 
/*      */               }
/*      */             } 
/*      */           } 
/*      */           IntArrays.quickSort(quantities, indexes, 0, count);
/*      */           int quantity = itemStack.getQuantity();
/*      */           ObjectArrayList<MoveTransaction<SlotTransaction>> objectArrayList = new ObjectArrayList();
/*      */           int ai = 0;
/*      */           while (ai < count && quantity < maxStack) {
/*      */             short s = (short)indexes[ai];
/*      */             ItemStack itemFrom = internal_getSlot(s);
/*      */             MoveTransaction<SlotTransaction> transaction = internal_moveItemStackFromSlot(s, itemFrom.getQuantity(), containerTo, slotTo, true);
/*      */             objectArrayList.add(transaction);
/*      */             quantity = !ItemStack.isEmpty(((SlotTransaction)transaction.getAddTransaction()).getSlotAfter()) ? ((SlotTransaction)transaction.getAddTransaction()).getSlotAfter().getQuantity() : 0;
/*      */             ai++;
/*      */           } 
/*      */           return new ListTransaction(true, (List)objectArrayList);
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public ListTransaction<MoveTransaction<SlotTransaction>> swapItems(short srcPos, @Nonnull ItemContainer containerTo, short destPos, short length) {
/*  709 */     ListTransaction<MoveTransaction<SlotTransaction>> transaction = internal_swapItems(srcPos, containerTo, destPos, length);
/*  710 */     sendUpdate((Transaction)transaction);
/*  711 */     for (MoveTransaction<SlotTransaction> moveItemStackTransaction : (Iterable<MoveTransaction<SlotTransaction>>)transaction.getList()) {
/*  712 */       moveItemStackTransaction.getOtherContainer().sendUpdate((Transaction)moveItemStackTransaction.toInverted(this));
/*      */     }
/*  714 */     return transaction;
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   protected ListTransaction<MoveTransaction<SlotTransaction>> internal_swapItems(short srcPos, @Nonnull ItemContainer containerTo, short destPos, short length) {
/*  719 */     if (srcPos < 0) throw new IndexOutOfBoundsException("srcPos < 0"); 
/*  720 */     if (srcPos + length > getCapacity()) throw new IndexOutOfBoundsException("srcPos + length > capacity");
/*      */     
/*  722 */     if (destPos < 0) throw new IndexOutOfBoundsException("destPos < 0"); 
/*  723 */     if (destPos + length > containerTo.getCapacity()) {
/*  724 */       throw new IndexOutOfBoundsException("destPos + length > dest.capacity");
/*      */     }
/*      */     
/*  727 */     return writeAction(() -> (ListTransaction)containerTo.<ListTransaction>writeAction(()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   protected MoveTransaction<SlotTransaction> internal_swapItems(@Nonnull ItemContainer containerTo, short slotFrom, short slotTo) {
/*  738 */     ItemStack itemFrom = internal_removeSlot(slotFrom);
/*  739 */     ItemStack itemTo = containerTo.internal_removeSlot(slotTo);
/*      */     
/*  741 */     if (itemTo != null && !itemTo.isEmpty()) {
/*  742 */       internal_setSlot(slotFrom, itemTo);
/*      */     }
/*  744 */     if (itemFrom != null && !itemFrom.isEmpty()) {
/*  745 */       containerTo.internal_setSlot(slotTo, itemFrom);
/*      */     }
/*  747 */     SlotTransaction from = new SlotTransaction(true, ActionType.REPLACE, slotFrom, itemFrom, itemTo, null, false, false, false);
/*  748 */     SlotTransaction to = new SlotTransaction(true, ActionType.REPLACE, slotTo, itemTo, itemFrom, null, false, false, false);
/*  749 */     return new MoveTransaction(true, from, MoveType.MOVE_FROM_SELF, containerTo, (Transaction)to);
/*      */   }
/*      */   
/*      */   public boolean canAddItemStack(@Nonnull ItemStack itemStack) {
/*  753 */     return canAddItemStack(itemStack, false, true);
/*      */   }
/*      */   
/*      */   public boolean canAddItemStack(@Nonnull ItemStack itemStack, boolean fullStacks, boolean filter) {
/*  757 */     Item item = itemStack.getItem();
/*  758 */     if (item == null) throw new IllegalArgumentException(itemStack.getItemId() + " is an invalid item!");
/*      */     
/*  760 */     int itemMaxStack = item.getMaxStack();
/*      */     
/*  762 */     return ((Boolean)readAction(() -> { int testQuantityRemaining = itemStack.getQuantity(); if (!fullStacks) testQuantityRemaining = InternalContainerUtilItemStack.testAddToExistingItemStacks(this, itemStack, itemMaxStack, testQuantityRemaining, filter);  testQuantityRemaining = InternalContainerUtilItemStack.testAddToEmptySlots(this, itemStack, itemMaxStack, testQuantityRemaining, filter); return Boolean.valueOf((testQuantityRemaining <= 0)); })).booleanValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public ItemStackTransaction addItemStack(@Nonnull ItemStack itemStack) {
/*  783 */     return addItemStack(itemStack, false, false, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public ItemStackTransaction addItemStack(@Nonnull ItemStack itemStack, boolean allOrNothing, boolean fullStacks, boolean filter) {
/*  797 */     ItemStackTransaction transaction = InternalContainerUtilItemStack.internal_addItemStack(this, itemStack, allOrNothing, fullStacks, filter);
/*  798 */     sendUpdate((Transaction)transaction);
/*  799 */     return transaction;
/*      */   }
/*      */   
/*      */   public boolean canAddItemStacks(List<ItemStack> itemStacks) {
/*  803 */     return canAddItemStacks(itemStacks, false, true);
/*      */   }
/*      */   
/*      */   public boolean canAddItemStacks(@Nullable List<ItemStack> itemStacks, boolean fullStacks, boolean filter) {
/*  807 */     if (itemStacks == null || itemStacks.isEmpty()) return true;
/*      */     
/*  809 */     ObjectArrayList<TempItemData> objectArrayList = new ObjectArrayList(itemStacks.size());
/*  810 */     for (ItemStack itemStack : itemStacks) {
/*  811 */       Item item = itemStack.getItem();
/*  812 */       if (item == null) throw new IllegalArgumentException(itemStack.getItemId() + " is an invalid item!"); 
/*  813 */       objectArrayList.add(new TempItemData(itemStack, item));
/*      */     } 
/*      */     
/*  816 */     return ((Boolean)readAction(() -> { for (TempItemData tempItemData : tempItemDataList) { int itemMaxStack = tempItemData.item().getMaxStack(); ItemStack itemStack = tempItemData.itemStack(); int testQuantityRemaining = itemStack.getQuantity(); if (!fullStacks) testQuantityRemaining = InternalContainerUtilItemStack.testAddToExistingItemStacks(this, itemStack, itemMaxStack, testQuantityRemaining, filter);  testQuantityRemaining = InternalContainerUtilItemStack.testAddToEmptySlots(this, itemStack, itemMaxStack, testQuantityRemaining, filter); if (testQuantityRemaining > 0) return Boolean.valueOf(false);  }  return Boolean.valueOf(true); })).booleanValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ListTransaction<ItemStackTransaction> addItemStacks(List<ItemStack> itemStacks) {
/*  835 */     return addItemStacks(itemStacks, false, false, true);
/*      */   }
/*      */   
/*      */   public ListTransaction<ItemStackTransaction> addItemStacks(@Nullable List<ItemStack> itemStacks, boolean allOrNothing, boolean fullStacks, boolean filter) {
/*  839 */     if (itemStacks == null || itemStacks.isEmpty()) {
/*  840 */       return ListTransaction.getEmptyTransaction(true);
/*      */     }
/*      */     
/*  843 */     ListTransaction<ItemStackTransaction> transaction = InternalContainerUtilItemStack.internal_addItemStacks(this, itemStacks, allOrNothing, fullStacks, filter);
/*  844 */     sendUpdate((Transaction)transaction);
/*  845 */     return transaction;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ListTransaction<ItemStackSlotTransaction> addItemStacksOrdered(List<ItemStack> itemStacks) {
/*  851 */     return addItemStacksOrdered(itemStacks, false, true);
/*      */   }
/*      */   
/*      */   public ListTransaction<ItemStackSlotTransaction> addItemStacksOrdered(short offset, List<ItemStack> itemStacks) {
/*  855 */     return addItemStacksOrdered(offset, itemStacks, false, true);
/*      */   }
/*      */   
/*      */   public ListTransaction<ItemStackSlotTransaction> addItemStacksOrdered(List<ItemStack> itemStacks, boolean allOrNothing, boolean filter) {
/*  859 */     return addItemStacksOrdered((short)0, itemStacks, allOrNothing, filter);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ListTransaction<ItemStackSlotTransaction> addItemStacksOrdered(short offset, @Nullable List<ItemStack> itemStacks, boolean allOrNothing, boolean filter) {
/*  867 */     if (itemStacks == null || itemStacks.isEmpty()) {
/*  868 */       return ListTransaction.getEmptyTransaction(true);
/*      */     }
/*      */     
/*  871 */     ListTransaction<ItemStackSlotTransaction> transaction = InternalContainerUtilItemStack.internal_addItemStacksOrdered(this, offset, itemStacks, allOrNothing, filter);
/*  872 */     sendUpdate((Transaction)transaction);
/*  873 */     return transaction;
/*      */   }
/*      */   
/*      */   public boolean canRemoveItemStack(ItemStack itemStack) {
/*  877 */     return canRemoveItemStack(itemStack, true, true);
/*      */   }
/*      */   
/*      */   public boolean canRemoveItemStack(@Nullable ItemStack itemStack, boolean exactAmount, boolean filter) {
/*  881 */     if (itemStack == null) return true;
/*      */     
/*  883 */     return ((Boolean)readAction(() -> {
/*      */           int testQuantityRemaining = InternalContainerUtilItemStack.testRemoveItemStackFromItems(this, itemStack, itemStack.getQuantity(), filter);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  890 */           return (testQuantityRemaining > 0) ? Boolean.valueOf(false) : Boolean.valueOf((!exactAmount || testQuantityRemaining >= 0));
/*      */         })).booleanValue();
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public ItemStackTransaction removeItemStack(@Nonnull ItemStack itemStack) {
/*  896 */     return removeItemStack(itemStack, true, true);
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public ItemStackTransaction removeItemStack(@Nonnull ItemStack itemStack, boolean allOrNothing, boolean filter) {
/*  901 */     ItemStackTransaction transaction = InternalContainerUtilItemStack.internal_removeItemStack(this, itemStack, allOrNothing, filter);
/*  902 */     sendUpdate((Transaction)transaction);
/*  903 */     return transaction;
/*      */   }
/*      */   
/*      */   public boolean canRemoveItemStacks(List<ItemStack> itemStacks) {
/*  907 */     return canRemoveItemStacks(itemStacks, true, true);
/*      */   }
/*      */   
/*      */   public boolean canRemoveItemStacks(@Nullable List<ItemStack> itemStacks, boolean exactAmount, boolean filter) {
/*  911 */     if (itemStacks == null || itemStacks.isEmpty()) return true;
/*      */     
/*  913 */     return ((Boolean)readAction(() -> { for (ItemStack itemStack : itemStacks) { int testQuantityRemaining = InternalContainerUtilItemStack.testRemoveItemStackFromItems(this, itemStack, itemStack.getQuantity(), filter); if (testQuantityRemaining > 0) return Boolean.valueOf(false);  if (exactAmount && testQuantityRemaining < 0) return Boolean.valueOf(false);  }  return Boolean.valueOf(true); })).booleanValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ListTransaction<ItemStackTransaction> removeItemStacks(List<ItemStack> itemStacks) {
/*  930 */     return removeItemStacks(itemStacks, true, true);
/*      */   }
/*      */   
/*      */   public ListTransaction<ItemStackTransaction> removeItemStacks(@Nullable List<ItemStack> itemStacks, boolean allOrNothing, boolean filter) {
/*  934 */     if (itemStacks == null || itemStacks.isEmpty()) {
/*  935 */       return ListTransaction.getEmptyTransaction(true);
/*      */     }
/*      */     
/*  938 */     ListTransaction<ItemStackTransaction> transaction = InternalContainerUtilItemStack.internal_removeItemStacks(this, itemStacks, allOrNothing, filter);
/*  939 */     sendUpdate((Transaction)transaction);
/*  940 */     return transaction;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canRemoveTag(int tagIndex, int quantity) {
/*  946 */     return canRemoveTag(tagIndex, quantity, true, true);
/*      */   }
/*      */   
/*      */   public boolean canRemoveTag(int tagIndex, int quantity, boolean exactAmount, boolean filter) {
/*  950 */     return ((Boolean)readAction(() -> {
/*      */           int testQuantityRemaining = InternalContainerUtilTag.testRemoveTagFromItems(this, tagIndex, quantity, filter);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  957 */           return (testQuantityRemaining > 0) ? Boolean.valueOf(false) : Boolean.valueOf((!exactAmount || testQuantityRemaining >= 0));
/*      */         })).booleanValue();
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public TagTransaction removeTag(int tagIndex, int quantity) {
/*  963 */     return removeTag(tagIndex, quantity, true, true, true);
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public TagTransaction removeTag(int tagIndex, int quantity, boolean allOrNothing, boolean exactAmount, boolean filter) {
/*  968 */     TagTransaction transaction = InternalContainerUtilTag.internal_removeTag(this, tagIndex, quantity, allOrNothing, exactAmount, filter);
/*  969 */     sendUpdate((Transaction)transaction);
/*  970 */     return transaction;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canRemoveResource(ResourceQuantity resource) {
/*  978 */     return canRemoveResource(resource, true, true);
/*      */   }
/*      */   
/*      */   public boolean canRemoveResource(@Nullable ResourceQuantity resource, boolean exactAmount, boolean filter) {
/*  982 */     if (resource == null) return true;
/*      */     
/*  984 */     return ((Boolean)readAction(() -> {
/*      */           int testQuantityRemaining = InternalContainerUtilResource.testRemoveResourceFromItems(this, resource, resource.getQuantity(), filter);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  991 */           return (testQuantityRemaining > 0) ? Boolean.valueOf(false) : Boolean.valueOf((!exactAmount || testQuantityRemaining >= 0));
/*      */         })).booleanValue();
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public ResourceTransaction removeResource(@Nonnull ResourceQuantity resource) {
/*  997 */     return removeResource(resource, true, true, true);
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public ResourceTransaction removeResource(@Nonnull ResourceQuantity resource, boolean allOrNothing, boolean exactAmount, boolean filter) {
/* 1002 */     ResourceTransaction transaction = InternalContainerUtilResource.internal_removeResource(this, resource, allOrNothing, exactAmount, filter);
/* 1003 */     sendUpdate((Transaction)transaction);
/* 1004 */     return transaction;
/*      */   }
/*      */   
/*      */   public boolean canRemoveResources(List<ResourceQuantity> resources) {
/* 1008 */     return canRemoveResources(resources, true, true);
/*      */   }
/*      */   
/*      */   public boolean canRemoveResources(@Nullable List<ResourceQuantity> resources, boolean exactAmount, boolean filter) {
/* 1012 */     if (resources == null || resources.isEmpty()) return true;
/*      */     
/* 1014 */     return ((Boolean)readAction(() -> { for (ResourceQuantity resource : resources) { int testQuantityRemaining = InternalContainerUtilResource.testRemoveResourceFromItems(this, resource, resource.getQuantity(), filter); if (testQuantityRemaining > 0) return Boolean.valueOf(false);  if (exactAmount && testQuantityRemaining < 0) return Boolean.valueOf(false);  }  return Boolean.valueOf(true); })).booleanValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ListTransaction<ResourceTransaction> removeResources(List<ResourceQuantity> resources) {
/* 1031 */     return removeResources(resources, true, true, true);
/*      */   }
/*      */   
/*      */   public ListTransaction<ResourceTransaction> removeResources(@Nullable List<ResourceQuantity> resources, boolean allOrNothing, boolean exactAmount, boolean filter) {
/* 1035 */     if (resources == null || resources.isEmpty()) {
/* 1036 */       return ListTransaction.getEmptyTransaction(true);
/*      */     }
/*      */     
/* 1039 */     ListTransaction<ResourceTransaction> transaction = InternalContainerUtilResource.internal_removeResources(this, resources, allOrNothing, exactAmount, filter);
/* 1040 */     sendUpdate((Transaction)transaction);
/* 1041 */     return transaction;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canRemoveMaterial(MaterialQuantity material) {
/* 1047 */     return canRemoveMaterial(material, true, true);
/*      */   }
/*      */   
/*      */   public boolean canRemoveMaterial(@Nullable MaterialQuantity material, boolean exactAmount, boolean filter) {
/* 1051 */     if (material == null) return true;
/*      */     
/* 1053 */     return ((Boolean)readAction(() -> {
/*      */           int testQuantityRemaining = InternalContainerUtilMaterial.testRemoveMaterialFromItems(this, material, material.getQuantity(), filter);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1060 */           return (testQuantityRemaining > 0) ? Boolean.valueOf(false) : Boolean.valueOf((!exactAmount || testQuantityRemaining >= 0));
/*      */         })).booleanValue();
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public MaterialTransaction removeMaterial(@Nonnull MaterialQuantity material) {
/* 1066 */     return removeMaterial(material, true, true, true);
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public MaterialTransaction removeMaterial(@Nonnull MaterialQuantity material, boolean allOrNothing, boolean exactAmount, boolean filter) {
/* 1071 */     MaterialTransaction transaction = InternalContainerUtilMaterial.internal_removeMaterial(this, material, allOrNothing, exactAmount, filter);
/* 1072 */     sendUpdate((Transaction)transaction);
/* 1073 */     return transaction;
/*      */   }
/*      */   
/*      */   public boolean canRemoveMaterials(List<MaterialQuantity> materials) {
/* 1077 */     return canRemoveMaterials(materials, true, true);
/*      */   }
/*      */   
/*      */   public boolean canRemoveMaterials(@Nullable List<MaterialQuantity> materials, boolean exactAmount, boolean filter) {
/* 1081 */     if (materials == null || materials.isEmpty()) return true;
/*      */     
/* 1083 */     return ((Boolean)readAction(() -> { for (MaterialQuantity material : materials) { int testQuantityRemaining = InternalContainerUtilMaterial.testRemoveMaterialFromItems(this, material, material.getQuantity(), filter); if (testQuantityRemaining > 0) return Boolean.valueOf(false);  if (exactAmount && testQuantityRemaining < 0) return Boolean.valueOf(false);  }  return Boolean.valueOf(true); })).booleanValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<TestRemoveItemSlotResult> getSlotMaterialsToRemove(@Nullable List<MaterialQuantity> materials, boolean exactAmount, boolean filter) {
/* 1100 */     ObjectArrayList objectArrayList = new ObjectArrayList();
/* 1101 */     if (materials == null || materials.isEmpty()) return (List<TestRemoveItemSlotResult>)objectArrayList;
/*      */     
/* 1103 */     return readAction(() -> {
/*      */           for (MaterialQuantity material : materials) {
/*      */             TestRemoveItemSlotResult testResult = InternalContainerUtilMaterial.getTestRemoveMaterialFromItems(this, material, material.getQuantity(), filter);
/*      */             if (testResult.quantityRemaining > 0) {
/*      */               slotMaterials.clear();
/*      */               return slotMaterials;
/*      */             } 
/*      */             if (exactAmount && testResult.quantityRemaining < 0) {
/*      */               slotMaterials.clear();
/*      */               return slotMaterials;
/*      */             } 
/*      */             slotMaterials.add(testResult);
/*      */           } 
/*      */           return slotMaterials;
/*      */         });
/*      */   }
/*      */   
/*      */   public ListTransaction<MaterialTransaction> removeMaterials(List<MaterialQuantity> materials) {
/* 1121 */     return removeMaterials(materials, true, true, true);
/*      */   }
/*      */   
/*      */   public ListTransaction<MaterialTransaction> removeMaterials(@Nullable List<MaterialQuantity> materials, boolean allOrNothing, boolean exactAmount, boolean filter) {
/* 1125 */     if (materials == null || materials.isEmpty()) {
/* 1126 */       return ListTransaction.getEmptyTransaction(true);
/*      */     }
/*      */     
/* 1129 */     ListTransaction<MaterialTransaction> transaction = InternalContainerUtilMaterial.internal_removeMaterials(this, materials, allOrNothing, exactAmount, filter);
/* 1130 */     sendUpdate((Transaction)transaction);
/* 1131 */     return transaction;
/*      */   }
/*      */   
/*      */   public ListTransaction<MaterialSlotTransaction> removeMaterialsOrdered(short offset, List<MaterialQuantity> materials) {
/* 1135 */     return removeMaterialsOrdered(offset, materials, true, true, true);
/*      */   }
/*      */   
/*      */   public ListTransaction<MaterialSlotTransaction> removeMaterialsOrdered(List<MaterialQuantity> materials, boolean allOrNothing, boolean exactAmount, boolean filter) {
/* 1139 */     return removeMaterialsOrdered((short)0, materials, allOrNothing, exactAmount, filter);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ListTransaction<MaterialSlotTransaction> removeMaterialsOrdered(short offset, @Nullable List<MaterialQuantity> materials, boolean allOrNothing, boolean exactAmount, boolean filter) {
/* 1147 */     if (materials == null || materials.isEmpty()) {
/* 1148 */       return ListTransaction.getEmptyTransaction(true);
/*      */     }
/*      */ 
/*      */     
/* 1152 */     if (offset + materials.size() > getCapacity()) {
/* 1153 */       return ListTransaction.getEmptyTransaction(false);
/*      */     }
/*      */     
/* 1156 */     ListTransaction<MaterialSlotTransaction> transaction = InternalContainerUtilMaterial.internal_removeMaterialsOrdered(this, offset, materials, allOrNothing, exactAmount, filter);
/* 1157 */     sendUpdate((Transaction)transaction);
/* 1158 */     return transaction;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/* 1162 */     return ((Boolean)readAction(() -> { for (short i = 0; i < getCapacity(); i = (short)(i + 1)) { ItemStack itemStack = internal_getSlot(i); if (itemStack != null && !itemStack.isEmpty()) return Boolean.valueOf(false);  }  return Boolean.valueOf(true); })).booleanValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int countItemStacks(@Nonnull Predicate<ItemStack> itemPredicate) {
/* 1174 */     return ((Integer)readAction(() -> { int count = 0; for (short i = 0; i < getCapacity(); i = (short)(i + 1)) { ItemStack itemStack = internal_getSlot(i); if (!ItemStack.isEmpty(itemStack) && itemPredicate.test(itemStack)) count += itemStack.getQuantity();  }  return Integer.valueOf(count); })).intValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean containsItemStacksStackableWith(@Nonnull ItemStack itemStack) {
/* 1186 */     return ((Boolean)readAction(() -> { short i = 0; while (i < getCapacity()) { ItemStack checked = internal_getSlot(i); if (ItemStack.isEmpty(checked) || !itemStack.isStackableWith(checked)) { i = (short)(i + 1); continue; }  return Boolean.valueOf(true); }  return Boolean.valueOf(false); })).booleanValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void forEach(@Nonnull ShortObjectConsumer<ItemStack> action) {
/* 1197 */     for (short i = 0; i < getCapacity(); i = (short)(i + 1)) {
/* 1198 */       ItemStack itemStack = getItemStack(i);
/* 1199 */       if (!ItemStack.isEmpty(itemStack))
/* 1200 */         action.accept(i, itemStack); 
/*      */     } 
/*      */   }
/*      */   
/*      */   public <T> void forEachWithMeta(@Nonnull Short2ObjectConcurrentHashMap.ShortBiObjConsumer<ItemStack, T> consumer, T meta) {
/* 1205 */     for (short i = 0; i < getCapacity(); i = (short)(i + 1)) {
/* 1206 */       ItemStack itemStack = getItemStack(i);
/* 1207 */       if (!ItemStack.isEmpty(itemStack))
/* 1208 */         consumer.accept(i, itemStack, meta); 
/*      */     } 
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public List<ItemStack> removeAllItemStacks() {
/* 1214 */     ObjectArrayList objectArrayList = new ObjectArrayList();
/* 1215 */     ListTransaction<SlotTransaction> transaction = writeAction(() -> {
/*      */           ObjectArrayList<SlotTransaction> objectArrayList = new ObjectArrayList();
/*      */           for (short i = 0; i < getCapacity(); i = (short)(i + 1)) {
/*      */             if (!cantRemoveFromSlot(i)) {
/*      */               ItemStack itemStack = internal_removeSlot(i);
/*      */               if (!ItemStack.isEmpty(itemStack)) {
/*      */                 items.add(itemStack);
/*      */                 objectArrayList.add(new SlotTransaction(true, ActionType.REMOVE, i, itemStack, null, itemStack, false, false, true));
/*      */               } 
/*      */             } 
/*      */           } 
/*      */           return new ListTransaction(true, (List)objectArrayList);
/*      */         });
/* 1228 */     sendUpdate((Transaction)transaction);
/* 1229 */     return (List<ItemStack>)objectArrayList;
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public List<ItemStack> dropAllItemStacks() {
/* 1234 */     return dropAllItemStacks(true);
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public List<ItemStack> dropAllItemStacks(boolean filter) {
/* 1239 */     ObjectArrayList objectArrayList = new ObjectArrayList();
/* 1240 */     ListTransaction<SlotTransaction> transaction = writeAction(() -> {
/*      */           ObjectArrayList<SlotTransaction> objectArrayList = new ObjectArrayList(); short i;
/*      */           for (i = 0; i < getCapacity(); i = (short)(i + 1)) {
/*      */             if (!filter || !cantDropFromSlot(i)) {
/*      */               ItemStack itemStack = internal_removeSlot(i);
/*      */               if (!ItemStack.isEmpty(itemStack)) {
/*      */                 items.add(itemStack);
/*      */                 objectArrayList.add(new SlotTransaction(true, ActionType.REMOVE, i, itemStack, null, itemStack, false, false, true));
/*      */               } 
/*      */             } 
/*      */           } 
/*      */           return new ListTransaction(true, (List)objectArrayList);
/*      */         });
/* 1253 */     sendUpdate((Transaction)transaction);
/* 1254 */     return (List<ItemStack>)objectArrayList;
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public ListTransaction<SlotTransaction> sortItems(@Nonnull SortType sort) {
/* 1259 */     ListTransaction<SlotTransaction> transaction = internal_sortItems(sort);
/* 1260 */     sendUpdate((Transaction)transaction);
/* 1261 */     return transaction;
/*      */   }
/*      */   
/*      */   protected ListTransaction<SlotTransaction> internal_sortItems(@Nonnull SortType sort) {
/* 1265 */     return writeAction(() -> {
/*      */           ItemStack[] stacks = new ItemStack[getCapacity()];
/*      */           int stackOffset = 0;
/*      */           short i;
/*      */           for (i = 0; i < stacks.length; i = (short)(i + 1)) {
/*      */             if (!cantRemoveFromSlot(i)) {
/*      */               ItemStack slot = internal_getSlot(i);
/*      */               if (slot != null) {
/*      */                 Item item = slot.getItem();
/*      */                 int maxStack = item.getMaxStack();
/*      */                 int slotQuantity = slot.getQuantity();
/*      */                 if (maxStack > 1) {
/*      */                   int k = 0;
/*      */                   while (k < stackOffset && slotQuantity > 0) {
/*      */                     ItemStack stack = stacks[k];
/*      */                     if (slot.isStackableWith(stack)) {
/*      */                       int stackQuantity = stack.getQuantity();
/*      */                       if (stackQuantity < maxStack) {
/*      */                         int adjust = Math.min(slotQuantity, maxStack - stackQuantity);
/*      */                         slotQuantity -= adjust;
/*      */                         stacks[k] = stack.withQuantity(stackQuantity + adjust);
/*      */                       } 
/*      */                     } 
/*      */                     k++;
/*      */                   } 
/*      */                 } 
/*      */                 if (slotQuantity > 0) {
/*      */                   stacks[stackOffset++] = (slotQuantity != slot.getQuantity()) ? slot.withQuantity(slotQuantity) : slot;
/*      */                 }
/*      */               } 
/*      */             } 
/*      */           } 
/*      */           Arrays.sort(stacks, sort.getComparator());
/*      */           ObjectArrayList<SlotTransaction> objectArrayList = new ObjectArrayList(stacks.length);
/*      */           stackOffset = 0;
/*      */           short s1;
/*      */           for (s1 = 0; s1 < stacks.length; s1 = (short)(s1 + 1)) {
/*      */             if (!cantRemoveFromSlot(s1)) {
/*      */               ItemStack existing = internal_getSlot(s1);
/*      */               ItemStack replacement = stacks[stackOffset];
/*      */               if (!cantAddToSlot(s1, replacement, existing)) {
/*      */                 stackOffset++;
/*      */                 if (existing != replacement) {
/*      */                   internal_setSlot(s1, replacement);
/*      */                   objectArrayList.add(new SlotTransaction(true, ActionType.REMOVE, s1, existing, null, replacement, false, false, true));
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */           for (int j = stackOffset; j < stacks.length; j++) {
/*      */             if (stacks[j] != null) {
/*      */               throw new IllegalStateException("Had leftover stacks that didn't get sorted!");
/*      */             }
/*      */           } 
/*      */           return new ListTransaction(true, (List)objectArrayList);
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void sendUpdate(@Nonnull Transaction transaction) {
/* 1338 */     if (!transaction.succeeded())
/* 1339 */       return;  ItemContainerChangeEvent event = new ItemContainerChangeEvent(this, transaction);
/* 1340 */     this.externalChangeEventRegistry.dispatchFor(null).dispatch((IBaseEvent)event);
/* 1341 */     this.internalChangeEventRegistry.dispatchFor(null).dispatch((IBaseEvent)event);
/*      */   }
/*      */   
/*      */   public boolean containsContainer(ItemContainer itemContainer) {
/* 1345 */     return (itemContainer == this);
/*      */   }
/*      */   
/*      */   public void doMigration(Function<String, String> blockMigration) {
/* 1349 */     Objects.requireNonNull(blockMigration);
/* 1350 */     writeAction(_blockMigration -> { for (short i = 0; i < getCapacity(); i = (short)(i + 1)) { ItemStack slot = internal_getSlot(i); if (!ItemStack.isEmpty(slot)) { String oldItemId = slot.getItemId(); String newItemId = _blockMigration.apply(slot.getItemId()); if (!oldItemId.equals(newItemId)) internal_setSlot(i, new ItemStack(newItemId, slot.getQuantity(), slot.getMetadata()));  }  }  return null; }blockMigration);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public static ItemResourceType getMatchingResourceType(@Nonnull Item item, @Nonnull String resourceId) {
/* 1375 */     ItemResourceType[] resourceTypes = item.getResourceTypes();
/* 1376 */     if (resourceTypes == null) return null; 
/* 1377 */     for (ItemResourceType resourceType : resourceTypes) {
/* 1378 */       if (resourceId.equals(resourceType.id)) {
/* 1379 */         return resourceType;
/*      */       }
/*      */     } 
/* 1382 */     return null;
/*      */   }
/*      */   
/*      */   public static void validateQuantity(int quantity) {
/* 1386 */     if (quantity < 0) throw new IllegalArgumentException("Quantity is less than zero! " + quantity + " < 0"); 
/*      */   }
/*      */   
/*      */   public static void validateSlotIndex(short slot, int capacity) {
/* 1390 */     if (slot < 0) throw new IllegalArgumentException("Slot is less than zero! " + slot + " < 0"); 
/* 1391 */     if (slot >= capacity) throw new IllegalArgumentException("Slot is outside capacity! " + slot + " >= " + capacity); 
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public static <T extends ItemContainer> T copy(@Nonnull ItemContainer from, @Nonnull T to, @Nullable List<ItemStack> remainder) {
/* 1396 */     from.forEach((slot, itemStack) -> {
/*      */           if (slot >= to.getCapacity()) {
/*      */             if (remainder != null)
/*      */               remainder.add(itemStack);  return;
/*      */           } 
/*      */           if (ItemStack.isEmpty(itemStack))
/*      */             return; 
/*      */           to.setItemStackForSlot(slot, itemStack);
/*      */         });
/* 1405 */     return to;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T extends ItemContainer> T ensureContainerCapacity(@Nullable T inputContainer, short capacity, @Nonnull Short2ObjectConcurrentHashMap.ShortFunction<T> newContainerSupplier, List<ItemStack> remainder) {
/* 1412 */     if (inputContainer == null) return (T)newContainerSupplier.apply(capacity); 
/* 1413 */     if (inputContainer.getCapacity() == capacity) return inputContainer; 
/* 1414 */     return copy((ItemContainer)inputContainer, (T)newContainerSupplier.apply(capacity), remainder);
/*      */   }
/*      */   public abstract short getCapacity(); public abstract void setGlobalFilter(FilterType paramFilterType); public abstract void setSlotFilter(FilterActionType paramFilterActionType, short paramShort, SlotFilter paramSlotFilter); public abstract ItemContainer clone(); protected abstract <V> V readAction(Supplier<V> paramSupplier); protected abstract <X, V> V readAction(Function<X, V> paramFunction, X paramX); protected abstract <V> V writeAction(Supplier<V> paramSupplier);
/*      */   protected abstract <X, V> V writeAction(Function<X, V> paramFunction, X paramX);
/* 1418 */   public static ItemContainer getNewContainer(short capacity, @Nonnull Short2ObjectConcurrentHashMap.ShortFunction<ItemContainer> supplier) { return (capacity > 0) ? (ItemContainer)supplier.apply(capacity) : EmptyItemContainer.INSTANCE; } protected abstract ClearTransaction internal_clear(); @Nullable
/*      */   protected abstract ItemStack internal_getSlot(short paramShort); @Nullable
/*      */   protected abstract ItemStack internal_setSlot(short paramShort, ItemStack paramItemStack); @Nullable
/* 1421 */   protected abstract ItemStack internal_removeSlot(short paramShort); protected abstract boolean cantAddToSlot(short paramShort, ItemStack paramItemStack1, ItemStack paramItemStack2); protected abstract boolean cantRemoveFromSlot(short paramShort); protected abstract boolean cantDropFromSlot(short paramShort); protected abstract boolean cantMoveToSlot(ItemContainer paramItemContainer, short paramShort); public static final class TempItemData extends Record { private final ItemStack itemStack; private final Item item; public TempItemData(ItemStack itemStack, Item item) { this.itemStack = itemStack; this.item = item; } public final String toString() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/server/core/inventory/container/ItemContainer$TempItemData;)Ljava/lang/String;
/*      */       //   6: areturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1421	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lcom/hypixel/hytale/server/core/inventory/container/ItemContainer$TempItemData; } public final int hashCode() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/server/core/inventory/container/ItemContainer$TempItemData;)I
/*      */       //   6: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1421	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lcom/hypixel/hytale/server/core/inventory/container/ItemContainer$TempItemData; } public final boolean equals(Object o) { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: aload_1
/*      */       //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/server/core/inventory/container/ItemContainer$TempItemData;Ljava/lang/Object;)Z
/*      */       //   7: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1421	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	8	0	this	Lcom/hypixel/hytale/server/core/inventory/container/ItemContainer$TempItemData;
/* 1421 */       //   0	8	1	o	Ljava/lang/Object; } public ItemStack itemStack() { return this.itemStack; } public Item item() { return this.item; }
/*      */      }
/*      */   public static final class ItemContainerChangeEvent extends Record implements IEvent<Void> { private final ItemContainer container; private final Transaction transaction;
/* 1424 */     public ItemContainerChangeEvent(ItemContainer container, Transaction transaction) { this.container = container; this.transaction = transaction; } public final int hashCode() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/server/core/inventory/container/ItemContainer$ItemContainerChangeEvent;)I
/*      */       //   6: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1424	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lcom/hypixel/hytale/server/core/inventory/container/ItemContainer$ItemContainerChangeEvent; } public final boolean equals(Object o) { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: aload_1
/*      */       //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/server/core/inventory/container/ItemContainer$ItemContainerChangeEvent;Ljava/lang/Object;)Z
/*      */       //   7: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1424	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	8	0	this	Lcom/hypixel/hytale/server/core/inventory/container/ItemContainer$ItemContainerChangeEvent;
/* 1424 */       //   0	8	1	o	Ljava/lang/Object; } public ItemContainer container() { return this.container; } public Transaction transaction() { return this.transaction; }
/*      */ 
/*      */     
/*      */     @Nonnull
/*      */     public String toString() {
/* 1429 */       return "ItemContainerChangeEvent{container=" + String.valueOf(this.container) + ", transaction=" + String.valueOf(this.transaction) + "}";
/*      */     } }
/*      */ 
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\inventory\container\ItemContainer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */