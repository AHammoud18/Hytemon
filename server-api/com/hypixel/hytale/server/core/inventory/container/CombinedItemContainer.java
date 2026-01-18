/*     */ package com.hypixel.hytale.server.core.inventory.container;
/*     */ 
/*     */ import com.hypixel.hytale.event.EventRegistration;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.inventory.container.filter.FilterActionType;
/*     */ import com.hypixel.hytale.server.core.inventory.container.filter.FilterType;
/*     */ import com.hypixel.hytale.server.core.inventory.container.filter.SlotFilter;
/*     */ import com.hypixel.hytale.server.core.inventory.transaction.ClearTransaction;
/*     */ import com.hypixel.hytale.server.core.inventory.transaction.Transaction;
/*     */ import java.util.Objects;
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
/*     */ public class CombinedItemContainer
/*     */   extends ItemContainer
/*     */ {
/*     */   protected final ItemContainer[] containers;
/*     */   
/*     */   public CombinedItemContainer(ItemContainer... containers) {
/*  27 */     this.containers = containers;
/*     */   }
/*     */   
/*     */   public ItemContainer getContainer(int index) {
/*  31 */     return this.containers[index];
/*     */   }
/*     */   
/*     */   public int getContainersSize() {
/*  35 */     return this.containers.length;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public ItemContainer getContainerForSlot(short slot) {
/*  40 */     for (ItemContainer container : this.containers) {
/*  41 */       short capacity = container.getCapacity();
/*  42 */       if (slot >= capacity) {
/*  43 */         slot = (short)(slot - capacity);
/*     */       }
/*     */       else {
/*     */         
/*  47 */         return container;
/*     */       } 
/*  49 */     }  return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected <V> V readAction(@Nonnull Supplier<V> action) {
/*  54 */     return readAction0(0, action);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private <V> V readAction0(int i, @Nonnull Supplier<V> action) {
/*  61 */     if (i >= this.containers.length) return action.get(); 
/*  62 */     return this.containers[i].readAction(() -> readAction0(i + 1, action));
/*     */   }
/*     */ 
/*     */   
/*     */   protected <X, V> V readAction(@Nonnull Function<X, V> action, X x) {
/*  67 */     return readAction0(0, action, x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private <X, V> V readAction0(int i, @Nonnull Function<X, V> action, X x) {
/*  74 */     if (i >= this.containers.length) return action.apply(x); 
/*  75 */     return this.containers[i].readAction(() -> readAction0(i + 1, action, x));
/*     */   }
/*     */ 
/*     */   
/*     */   protected <V> V writeAction(@Nonnull Supplier<V> action) {
/*  80 */     return writeAction0(0, action);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private <V> V writeAction0(int i, @Nonnull Supplier<V> action) {
/*  87 */     if (i >= this.containers.length) return action.get(); 
/*  88 */     return this.containers[i].writeAction(() -> writeAction0(i + 1, action));
/*     */   }
/*     */ 
/*     */   
/*     */   protected <X, V> V writeAction(@Nonnull Function<X, V> action, X x) {
/*  93 */     return writeAction0(0, action, x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private <X, V> V writeAction0(int i, @Nonnull Function<X, V> action, X x) {
/* 100 */     if (i >= this.containers.length) return action.apply(x); 
/* 101 */     return this.containers[i].writeAction(() -> writeAction0(i + 1, action, x));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected ClearTransaction internal_clear() {
/* 107 */     ItemStack[] itemStacks = new ItemStack[getCapacity()];
/* 108 */     short start = 0;
/* 109 */     for (ItemContainer container : this.containers) {
/* 110 */       ClearTransaction clear = container.internal_clear();
/* 111 */       ItemStack[] items = clear.getItems(); short slot;
/* 112 */       for (slot = 0; slot < itemStacks.length; slot = (short)(slot + 1)) {
/* 113 */         itemStacks[(short)(start + slot)] = items[slot];
/*     */       }
/* 115 */       start = (short)(start + container.getCapacity());
/*     */     } 
/* 117 */     return new ClearTransaction(true, (short)0, itemStacks);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected ItemStack internal_getSlot(short slot) {
/* 123 */     for (ItemContainer container : this.containers) {
/* 124 */       short capacity = container.getCapacity();
/* 125 */       if (slot >= capacity) {
/* 126 */         slot = (short)(slot - capacity);
/*     */       }
/*     */       else {
/*     */         
/* 130 */         return container.internal_getSlot(slot);
/*     */       } 
/* 132 */     }  return null;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected ItemStack internal_setSlot(short slot, ItemStack itemStack) {
/* 138 */     if (ItemStack.isEmpty(itemStack)) return internal_removeSlot(slot); 
/* 139 */     for (ItemContainer container : this.containers) {
/* 140 */       short capacity = container.getCapacity();
/* 141 */       if (slot >= capacity) {
/* 142 */         slot = (short)(slot - capacity);
/*     */       }
/*     */       else {
/*     */         
/* 146 */         return container.internal_setSlot(slot, itemStack);
/*     */       } 
/* 148 */     }  return null;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected ItemStack internal_removeSlot(short slot) {
/* 154 */     for (ItemContainer container : this.containers) {
/* 155 */       short capacity = container.getCapacity();
/* 156 */       if (slot >= capacity) {
/* 157 */         slot = (short)(slot - capacity);
/*     */       }
/*     */       else {
/*     */         
/* 161 */         return container.internal_removeSlot(slot);
/*     */       } 
/* 163 */     }  return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean cantAddToSlot(short slot, ItemStack itemStack, ItemStack slotItemStack) {
/* 168 */     for (ItemContainer container : this.containers) {
/* 169 */       short capacity = container.getCapacity();
/* 170 */       if (slot >= capacity) {
/* 171 */         slot = (short)(slot - capacity);
/*     */       }
/*     */       else {
/*     */         
/* 175 */         return container.cantAddToSlot(slot, itemStack, slotItemStack);
/*     */       } 
/* 177 */     }  return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean cantRemoveFromSlot(short slot) {
/* 182 */     for (ItemContainer container : this.containers) {
/* 183 */       short capacity = container.getCapacity();
/* 184 */       if (slot >= capacity) {
/* 185 */         slot = (short)(slot - capacity);
/*     */       }
/*     */       else {
/*     */         
/* 189 */         return container.cantRemoveFromSlot(slot);
/*     */       } 
/* 191 */     }  return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean cantDropFromSlot(short slot) {
/* 196 */     for (ItemContainer container : this.containers) {
/* 197 */       short capacity = container.getCapacity();
/* 198 */       if (slot >= capacity) {
/* 199 */         slot = (short)(slot - capacity);
/*     */       }
/*     */       else {
/*     */         
/* 203 */         return container.cantDropFromSlot(slot);
/*     */       } 
/* 205 */     }  return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean cantMoveToSlot(ItemContainer fromContainer, short slotFrom) {
/* 210 */     for (ItemContainer container : this.containers) {
/* 211 */       boolean cantMoveToSlot = container.cantMoveToSlot(fromContainer, slotFrom);
/* 212 */       if (cantMoveToSlot) return true; 
/*     */     } 
/* 214 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public short getCapacity() {
/* 219 */     short capacity = 0;
/* 220 */     for (ItemContainer container : this.containers) {
/* 221 */       capacity = (short)(capacity + container.getCapacity());
/*     */     }
/* 223 */     return capacity;
/*     */   }
/*     */ 
/*     */   
/*     */   public CombinedItemContainer clone() {
/* 228 */     throw new UnsupportedOperationException("clone() is not supported for CombinedItemContainer");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public EventRegistration registerChangeEvent(short priority, @Nonnull Consumer<ItemContainer.ItemContainerChangeEvent> consumer) {
/* 235 */     EventRegistration thisRegistration = super.registerChangeEvent(priority, consumer);
/* 236 */     EventRegistration[] containerRegistrations = new EventRegistration[this.containers.length];
/* 237 */     short start = 0;
/* 238 */     for (int i = 0; i < this.containers.length; i++) {
/* 239 */       ItemContainer container = this.containers[i];
/* 240 */       short finalStart = start;
/* 241 */       containerRegistrations[i] = container.internalChangeEventRegistry.register(priority, null, event -> consumer.accept(new ItemContainer.ItemContainerChangeEvent(this, event.transaction().toParent(this, finalStart, container))));
/*     */ 
/*     */       
/* 244 */       start = (short)(start + container.getCapacity());
/*     */     } 
/* 246 */     return EventRegistration.combine(thisRegistration, containerRegistrations);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void sendUpdate(@Nonnull Transaction transaction) {
/* 251 */     if (!transaction.succeeded())
/* 252 */       return;  super.sendUpdate(transaction);
/* 253 */     short start = 0;
/* 254 */     ItemContainer[] arrayOfItemContainer = this.containers; int i = arrayOfItemContainer.length; byte b = 0; while (true) { ItemContainer container; if (b < i) { container = arrayOfItemContainer[b];
/* 255 */         Transaction containerTransaction = transaction.fromParent(this, start, container);
/* 256 */         if (containerTransaction != null)
/* 257 */         { if (!containerTransaction.succeeded())
/* 258 */           { start = (short)(start + container.getCapacity()); }
/*     */           else
/*     */           
/* 261 */           { container.sendUpdate(containerTransaction);
/*     */             
/* 263 */             start = (short)(start + container.getCapacity()); }  continue; }  } else { break; }  start = (short)(start + container.getCapacity());
/*     */       b++; }
/*     */   
/*     */   }
/*     */   
/*     */   public boolean containsContainer(ItemContainer itemContainer) {
/* 269 */     if (itemContainer == this) return true; 
/* 270 */     for (ItemContainer container : this.containers) {
/* 271 */       if (container.containsContainer(itemContainer)) return true; 
/*     */     } 
/* 273 */     return false;
/*     */   }
/*     */   
/*     */   public boolean equals(Object o) {
/*     */     CombinedItemContainer that;
/* 278 */     if (this == o) return true; 
/* 279 */     if (o instanceof CombinedItemContainer) { that = (CombinedItemContainer)o; } else { return false; }
/*     */     
/* 281 */     short capacity = getCapacity();
/* 282 */     if (capacity != that.getCapacity()) return false;
/*     */     
/* 284 */     return ((Boolean)readAction(_that -> (Boolean)_that.<CombinedItemContainer, Boolean>readAction((), _that), that)).booleanValue();
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
/*     */   public int hashCode() {
/* 297 */     short capacity = getCapacity();
/* 298 */     int result = ((Integer)readAction(() -> { int hash = 0; for (short i = 0; i < capacity; i = (short)(i + 1)) { ItemStack itemStack = internal_getSlot(i); hash = 31 * hash + ((itemStack != null) ? itemStack.hashCode() : 0); }  return Integer.valueOf(hash); })).intValue();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 306 */     result = 31 * result + capacity;
/* 307 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGlobalFilter(FilterType globalFilter) {
/* 317 */     throw new UnsupportedOperationException("setGlobalFilter(FilterType) is not supported in CombinedItemContainer");
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSlotFilter(FilterActionType actionType, short slot, SlotFilter filter) {
/* 322 */     for (ItemContainer container : this.containers) {
/* 323 */       short capacity = container.getCapacity();
/* 324 */       if (slot >= capacity) {
/* 325 */         slot = (short)(slot - capacity);
/*     */       }
/*     */       else {
/*     */         
/* 329 */         container.setSlotFilter(actionType, slot, filter);
/*     */         return;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\inventory\container\CombinedItemContainer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */