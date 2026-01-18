/*     */ package com.hypixel.hytale.server.core.inventory.container;
/*     */ 
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.event.EventRegistration;
/*     */ import com.hypixel.hytale.function.consumer.ShortObjectConsumer;
/*     */ import com.hypixel.hytale.protocol.ItemWithAllMetadata;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.inventory.container.filter.FilterActionType;
/*     */ import com.hypixel.hytale.server.core.inventory.container.filter.FilterType;
/*     */ import com.hypixel.hytale.server.core.inventory.container.filter.SlotFilter;
/*     */ import com.hypixel.hytale.server.core.inventory.transaction.ClearTransaction;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EmptyItemContainer
/*     */   extends ItemContainer
/*     */ {
/*  27 */   public static final EmptyItemContainer INSTANCE = new EmptyItemContainer();
/*     */   
/*  29 */   public static final BuilderCodec<EmptyItemContainer> CODEC = BuilderCodec.builder(EmptyItemContainer.class, () -> INSTANCE)
/*  30 */     .build();
/*     */   
/*  32 */   private static final EventRegistration<Void, ItemContainer.ItemContainerChangeEvent> EVENT_REGISTRATION = new EventRegistration(ItemContainer.ItemContainerChangeEvent.class, () -> false, () -> {
/*     */       
/*     */       });
/*     */ 
/*     */ 
/*     */   
/*     */   public short getCapacity() {
/*  39 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ClearTransaction clear() {
/*  45 */     return ClearTransaction.EMPTY;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void forEach(ShortObjectConsumer<ItemStack> action) {}
/*     */ 
/*     */   
/*     */   protected <V> V readAction(@Nonnull Supplier<V> action) {
/*  54 */     return action.get();
/*     */   }
/*     */ 
/*     */   
/*     */   protected <X, V> V readAction(@Nonnull Function<X, V> action, X x) {
/*  59 */     return action.apply(x);
/*     */   }
/*     */ 
/*     */   
/*     */   protected <V> V writeAction(@Nonnull Supplier<V> action) {
/*  64 */     return action.get();
/*     */   }
/*     */ 
/*     */   
/*     */   protected <X, V> V writeAction(@Nonnull Function<X, V> action, X x) {
/*  69 */     return action.apply(x);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected ClearTransaction internal_clear() {
/*  75 */     return ClearTransaction.EMPTY;
/*     */   }
/*     */ 
/*     */   
/*     */   protected ItemStack internal_getSlot(short slot) {
/*  80 */     throw new UnsupportedOperationException("getSlot(int) is not supported in EmptyItemContainer");
/*     */   }
/*     */ 
/*     */   
/*     */   protected ItemStack internal_setSlot(short slot, ItemStack itemStack) {
/*  85 */     throw new UnsupportedOperationException("setSlot(int, ItemStack) is not supported in EmptyItemContainer");
/*     */   }
/*     */ 
/*     */   
/*     */   protected ItemStack internal_removeSlot(short slot) {
/*  90 */     throw new UnsupportedOperationException("removeSlot(int) is not supported in EmptyItemContainer");
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean cantAddToSlot(short slot, ItemStack itemStack, ItemStack slotItemStack) {
/*  95 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean cantRemoveFromSlot(short slot) {
/* 100 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean cantDropFromSlot(short slot) {
/* 105 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean cantMoveToSlot(ItemContainer fromContainer, short slotFrom) {
/* 110 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public List<ItemStack> removeAllItemStacks() {
/* 116 */     return Collections.emptyList();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Map<Integer, ItemWithAllMetadata> toProtocolMap() {
/* 122 */     return Collections.emptyMap();
/*     */   }
/*     */ 
/*     */   
/*     */   public EmptyItemContainer clone() {
/* 127 */     return INSTANCE;
/*     */   }
/*     */ 
/*     */   
/*     */   public EventRegistration registerChangeEvent(short priority, Consumer<ItemContainer.ItemContainerChangeEvent> consumer) {
/* 132 */     return EVENT_REGISTRATION;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGlobalFilter(FilterType globalFilter) {}
/*     */ 
/*     */   
/*     */   public void setSlotFilter(FilterActionType actionType, short slot, SlotFilter filter) {
/* 141 */     validateSlotIndex(slot, 0);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\inventory\container\EmptyItemContainer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */