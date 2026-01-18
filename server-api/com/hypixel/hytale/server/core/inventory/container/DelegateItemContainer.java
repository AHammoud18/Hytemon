/*     */ package com.hypixel.hytale.server.core.inventory.container;
/*     */ 
/*     */ import com.hypixel.fastutil.ints.Int2ObjectConcurrentHashMap;
/*     */ import com.hypixel.hytale.event.EventRegistration;
/*     */ import com.hypixel.hytale.event.IBaseEvent;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.inventory.container.filter.FilterActionType;
/*     */ import com.hypixel.hytale.server.core.inventory.container.filter.FilterType;
/*     */ import com.hypixel.hytale.server.core.inventory.container.filter.SlotFilter;
/*     */ import com.hypixel.hytale.server.core.inventory.transaction.ClearTransaction;
/*     */ import com.hypixel.hytale.server.core.inventory.transaction.Transaction;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DelegateItemContainer<T extends ItemContainer>
/*     */   extends ItemContainer
/*     */ {
/*     */   private T delegate;
/*  29 */   private final Map<FilterActionType, Int2ObjectConcurrentHashMap<SlotFilter>> slotFilters = new ConcurrentHashMap<>(); @Nonnull
/*  30 */   private FilterType globalFilter = FilterType.ALLOW_ALL;
/*     */ 
/*     */   
/*     */   public DelegateItemContainer(T delegate) {
/*  34 */     Objects.requireNonNull(delegate, "Delegate can't be null!");
/*  35 */     this.delegate = delegate;
/*     */   }
/*     */   
/*     */   public T getDelegate() {
/*  39 */     return this.delegate;
/*     */   }
/*     */ 
/*     */   
/*     */   protected <V> V readAction(Supplier<V> action) {
/*  44 */     return this.delegate.readAction(action);
/*     */   }
/*     */ 
/*     */   
/*     */   protected <X, V> V readAction(Function<X, V> action, X x) {
/*  49 */     return this.delegate.readAction(action, x);
/*     */   }
/*     */ 
/*     */   
/*     */   protected <V> V writeAction(Supplier<V> action) {
/*  54 */     return this.delegate.writeAction(action);
/*     */   }
/*     */ 
/*     */   
/*     */   protected <X, V> V writeAction(Function<X, V> action, X x) {
/*  59 */     return this.delegate.writeAction(action, x);
/*     */   }
/*     */ 
/*     */   
/*     */   protected ClearTransaction internal_clear() {
/*  64 */     return this.delegate.internal_clear();
/*     */   }
/*     */ 
/*     */   
/*     */   protected ItemStack internal_getSlot(short slot) {
/*  69 */     return this.delegate.internal_getSlot(slot);
/*     */   }
/*     */ 
/*     */   
/*     */   protected ItemStack internal_setSlot(short slot, ItemStack itemStack) {
/*  74 */     return this.delegate.internal_setSlot(slot, itemStack);
/*     */   }
/*     */ 
/*     */   
/*     */   protected ItemStack internal_removeSlot(short slot) {
/*  79 */     return this.delegate.internal_removeSlot(slot);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean cantAddToSlot(short slot, ItemStack itemStack, ItemStack slotItemStack) {
/*  84 */     if (!this.globalFilter.allowInput()) return true; 
/*  85 */     if (testFilter(FilterActionType.ADD, slot, itemStack)) return true; 
/*  86 */     return this.delegate.cantAddToSlot(slot, itemStack, slotItemStack);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean cantRemoveFromSlot(short slot) {
/*  91 */     if (!this.globalFilter.allowOutput()) return true; 
/*  92 */     if (testFilter(FilterActionType.REMOVE, slot, null)) return true; 
/*  93 */     return this.delegate.cantRemoveFromSlot(slot);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean cantDropFromSlot(short slot) {
/*  98 */     if (testFilter(FilterActionType.DROP, slot, null)) return true; 
/*  99 */     return this.delegate.cantDropFromSlot(slot);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean cantMoveToSlot(ItemContainer fromContainer, short slotFrom) {
/* 104 */     return this.delegate.cantMoveToSlot(fromContainer, slotFrom);
/*     */   }
/*     */   
/*     */   private boolean testFilter(FilterActionType actionType, short slot, ItemStack itemStack) {
/* 108 */     Int2ObjectConcurrentHashMap<SlotFilter> map = this.slotFilters.get(actionType);
/* 109 */     if (map == null) return false;
/*     */     
/* 111 */     SlotFilter filter = (SlotFilter)map.get(slot);
/* 112 */     if (filter == null) return false;
/*     */     
/* 114 */     return !filter.test(actionType, this, slot, itemStack);
/*     */   }
/*     */ 
/*     */   
/*     */   public short getCapacity() {
/* 119 */     return this.delegate.getCapacity();
/*     */   }
/*     */ 
/*     */   
/*     */   public ClearTransaction clear() {
/* 124 */     return this.delegate.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public DelegateItemContainer<T> clone() {
/* 130 */     return new DelegateItemContainer(this.delegate);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 135 */     return this.delegate.isEmpty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGlobalFilter(@Nonnull FilterType globalFilter) {
/* 143 */     this.globalFilter = Objects.<FilterType>requireNonNull(globalFilter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSlotFilter(FilterActionType actionType, short slot, @Nullable SlotFilter filter) {
/* 150 */     validateSlotIndex(slot, getCapacity());
/* 151 */     if (filter != null) {
/* 152 */       ((Int2ObjectConcurrentHashMap)this.slotFilters.computeIfAbsent(actionType, k -> new Int2ObjectConcurrentHashMap())).put(slot, filter);
/*     */     } else {
/* 154 */       this.slotFilters.computeIfPresent(actionType, (k, map) -> {
/*     */             map.remove(slot);
/*     */             return map.isEmpty() ? null : map;
/*     */           });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public EventRegistration registerChangeEvent(short priority, @Nonnull Consumer<ItemContainer.ItemContainerChangeEvent> consumer) {
/* 165 */     EventRegistration thisRegistration = super.registerChangeEvent(priority, consumer);
/*     */     
/* 167 */     EventRegistration[] delegateRegistration = { ((ItemContainer)this.delegate).internalChangeEventRegistry.register(priority, null, event -> consumer.accept(new ItemContainer.ItemContainerChangeEvent(this, event.transaction().toParent(this, (short)0, (ItemContainer)this.delegate)))) };
/*     */ 
/*     */ 
/*     */     
/* 171 */     return EventRegistration.combine(thisRegistration, delegateRegistration);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void sendUpdate(@Nonnull Transaction transaction) {
/* 176 */     if (!transaction.succeeded())
/* 177 */       return;  super.sendUpdate(transaction);
/* 178 */     ((ItemContainer)this.delegate).externalChangeEventRegistry.dispatchFor(null).dispatch((IBaseEvent)new ItemContainer.ItemContainerChangeEvent((ItemContainer)this.delegate, transaction));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object o) {
/* 183 */     if (this == o) return true; 
/* 184 */     if (o == null || getClass() != o.getClass()) return false;
/*     */     
/* 186 */     DelegateItemContainer<?> that = (DelegateItemContainer)o;
/*     */     
/* 188 */     if ((this.delegate != null) ? !this.delegate.equals(that.delegate) : (that.delegate != null)) return false; 
/* 189 */     if ((this.slotFilters != null) ? !this.slotFilters.equals(that.slotFilters) : (that.slotFilters != null)) return false; 
/* 190 */     return (this.globalFilter == that.globalFilter);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 195 */     int result = (this.delegate != null) ? this.delegate.hashCode() : 0;
/* 196 */     result = 31 * result + ((this.slotFilters != null) ? this.slotFilters.hashCode() : 0);
/* 197 */     result = 31 * result + ((this.globalFilter != null) ? this.globalFilter.hashCode() : 0);
/* 198 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\inventory\container\DelegateItemContainer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */