/*     */ package com.hypixel.hytale.server.core.inventory.container;
/*     */ 
/*     */ import com.hypixel.fastutil.ints.Int2ObjectConcurrentHashMap;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.EmptyExtraInfo;
/*     */ import com.hypixel.hytale.codec.ExtraInfo;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.ItemStackContainerConfig;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.inventory.container.filter.FilterActionType;
/*     */ import com.hypixel.hytale.server.core.inventory.container.filter.FilterType;
/*     */ import com.hypixel.hytale.server.core.inventory.container.filter.SlotFilter;
/*     */ import com.hypixel.hytale.server.core.inventory.container.filter.TagFilter;
/*     */ import com.hypixel.hytale.server.core.inventory.transaction.ClearTransaction;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.locks.ReadWriteLock;
/*     */ import java.util.concurrent.locks.ReentrantReadWriteLock;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ import org.bson.BsonDocument;
/*     */ 
/*     */ public class ItemStackItemContainer
/*     */   extends ItemContainer {
/*     */   @Nonnull
/*  29 */   public static KeyedCodec<BsonDocument> CONTAINER_CODEC = new KeyedCodec("Container", (Codec)Codec.BSON_DOCUMENT); @Nonnull
/*     */   public static KeyedCodec<ItemStack[]> ITEMS_CODEC; @Nonnull
/*  31 */   public static KeyedCodec<Short> CAPACITY_CODEC = new KeyedCodec("Capacity", (Codec)Codec.SHORT);
/*     */   static {
/*  33 */     ITEMS_CODEC = new KeyedCodec("Items", (Codec)new ArrayCodec((Codec)ItemStack.CODEC, x$0 -> new ItemStack[x$0]));
/*     */   }
/*  35 */   protected final ReadWriteLock lock = new ReentrantReadWriteLock();
/*     */   
/*     */   protected final ItemContainer parentContainer;
/*     */   protected final short itemStackSlot;
/*     */   protected final ItemStack originalItemStack;
/*     */   protected final short capacity;
/*     */   protected ItemStack[] items;
/*  42 */   private final Map<FilterActionType, Int2ObjectConcurrentHashMap<SlotFilter>> slotFilters = new ConcurrentHashMap<>(); @Nonnull
/*  43 */   private FilterType globalFilter = FilterType.ALLOW_ALL;
/*     */ 
/*     */   
/*     */   private ItemStackItemContainer(ItemContainer parentContainer, short itemStackSlot, ItemStack originalItemStack, short capacity, ItemStack[] items) {
/*  47 */     this.parentContainer = parentContainer;
/*  48 */     this.itemStackSlot = itemStackSlot;
/*  49 */     this.originalItemStack = originalItemStack;
/*  50 */     this.capacity = capacity;
/*  51 */     this.items = items;
/*     */   }
/*     */   
/*     */   public ItemContainer getParentContainer() {
/*  55 */     return this.parentContainer;
/*     */   }
/*     */   
/*     */   public short getItemStackSlot() {
/*  59 */     return this.itemStackSlot;
/*     */   }
/*     */   
/*     */   public ItemStack getOriginalItemStack() {
/*  63 */     return this.originalItemStack;
/*     */   }
/*     */   
/*     */   public boolean isItemStackValid() {
/*  67 */     ItemStack itemStack = this.parentContainer.getItemStack(this.itemStackSlot);
/*  68 */     if (ItemStack.isEmpty(itemStack)) return false; 
/*  69 */     return ItemStack.isSameItemType(itemStack, this.originalItemStack);
/*     */   }
/*     */ 
/*     */   
/*     */   public short getCapacity() {
/*  74 */     return this.capacity;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setGlobalFilter(@Nonnull FilterType globalFilter) {
/*  79 */     this.globalFilter = globalFilter;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSlotFilter(FilterActionType actionType, short slot, @Nullable SlotFilter filter) {
/*  84 */     validateSlotIndex(slot, getCapacity());
/*  85 */     if (filter != null) {
/*  86 */       ((Int2ObjectConcurrentHashMap)this.slotFilters.computeIfAbsent(actionType, k -> new Int2ObjectConcurrentHashMap())).put(slot, filter);
/*     */     } else {
/*  88 */       this.slotFilters.computeIfPresent(actionType, (k, map) -> {
/*     */             map.remove(slot);
/*     */             return map.isEmpty() ? null : map;
/*     */           });
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemContainer clone() {
/*  97 */     throw new UnsupportedOperationException("Item stack containers don't support clone");
/*     */   }
/*     */ 
/*     */   
/*     */   protected <V> V readAction(@Nonnull Supplier<V> action) {
/* 102 */     this.lock.readLock().lock();
/*     */     try {
/* 104 */       return action.get();
/*     */     } finally {
/* 106 */       this.lock.readLock().unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected <X, V> V readAction(@Nonnull Function<X, V> action, X x) {
/* 112 */     this.lock.readLock().lock();
/*     */     try {
/* 114 */       return action.apply(x);
/*     */     } finally {
/* 116 */       this.lock.readLock().unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected <V> V writeAction(@Nonnull Supplier<V> action) {
/* 122 */     this.lock.writeLock().lock();
/*     */     try {
/* 124 */       return action.get();
/*     */     } finally {
/* 126 */       this.lock.writeLock().unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected <X, V> V writeAction(@Nonnull Function<X, V> action, X x) {
/* 132 */     this.lock.writeLock().lock();
/*     */     try {
/* 134 */       return action.apply(x);
/*     */     } finally {
/* 136 */       this.lock.writeLock().unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 142 */     this.lock.readLock().lock();
/*     */     try {
/* 144 */       if (this.items == null) return true;  short i;
/* 145 */       for (i = 0; i < this.items.length; i = (short)(i + 1)) {
/* 146 */         if (!ItemStack.isEmpty(this.items[i])) return false; 
/*     */       } 
/* 148 */       i = 0; return i;
/*     */     } finally {
/* 150 */       this.lock.readLock().unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected ClearTransaction internal_clear() {
/* 157 */     if (this.items == null) return new ClearTransaction(true, (short)0, ItemStack.EMPTY_ARRAY); 
/* 158 */     ItemStack[] oldItems = this.items;
/* 159 */     this.items = new ItemStack[oldItems.length];
/* 160 */     writeToItemStack(this.parentContainer, this.itemStackSlot, this.originalItemStack, this.items);
/* 161 */     return new ClearTransaction(true, (short)0, oldItems);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected ItemStack internal_getSlot(short slot) {
/* 167 */     return (this.items != null) ? this.items[slot] : null;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected ItemStack internal_setSlot(short slot, ItemStack itemStack) {
/* 173 */     if (this.items == null) return null; 
/* 174 */     if (ItemStack.isEmpty(itemStack)) return internal_removeSlot(slot); 
/* 175 */     ItemStack old = this.items[slot];
/* 176 */     this.items[slot] = itemStack;
/* 177 */     writeToItemStack(this.parentContainer, this.itemStackSlot, this.originalItemStack, this.items);
/* 178 */     return old;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected ItemStack internal_removeSlot(short slot) {
/* 184 */     if (this.items == null) return null; 
/* 185 */     ItemStack old = this.items[slot];
/* 186 */     this.items[slot] = null;
/* 187 */     writeToItemStack(this.parentContainer, this.itemStackSlot, this.originalItemStack, this.items);
/* 188 */     return old;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean cantAddToSlot(short slot, ItemStack itemStack, ItemStack slotItemStack) {
/* 193 */     if (!this.globalFilter.allowInput()) return true; 
/* 194 */     return testFilter(FilterActionType.ADD, slot, itemStack);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean cantRemoveFromSlot(short slot) {
/* 199 */     if (!this.globalFilter.allowOutput()) return true; 
/* 200 */     return testFilter(FilterActionType.REMOVE, slot, (ItemStack)null);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean cantDropFromSlot(short slot) {
/* 205 */     return testFilter(FilterActionType.DROP, slot, (ItemStack)null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean cantMoveToSlot(ItemContainer fromContainer, short slotFrom) {
/* 211 */     if (fromContainer == this.parentContainer && slotFrom == this.itemStackSlot) {
/* 212 */       return true;
/*     */     }
/*     */     
/* 215 */     return false;
/*     */   }
/*     */   
/*     */   private boolean testFilter(FilterActionType actionType, short slot, ItemStack itemStack) {
/* 219 */     Int2ObjectConcurrentHashMap<SlotFilter> map = this.slotFilters.get(actionType);
/* 220 */     if (map == null) return false;
/*     */     
/* 222 */     SlotFilter filter = (SlotFilter)map.get(slot);
/* 223 */     if (filter == null) return false;
/*     */     
/* 225 */     return !filter.test(actionType, this, slot, itemStack);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public ItemStack getItemStack(short slot) {
/* 234 */     validateSlotIndex(slot, getCapacity());
/*     */     
/* 236 */     this.lock.readLock().lock();
/*     */     try {
/* 238 */       return internal_getSlot(slot);
/*     */     } finally {
/* 240 */       this.lock.readLock().unlock();
/*     */     } 
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
/*     */   public static void writeToItemStack(@Nonnull ItemContainer itemContainer, short slot, ItemStack originalItemStack, ItemStack[] items) {
/* 253 */     if (ItemStack.isEmpty(originalItemStack)) throw new IllegalStateException("Item stack container is empty");
/*     */     
/* 255 */     ItemStack itemStack = itemContainer.getItemStack(slot);
/* 256 */     if (!ItemStack.isSameItemType(itemStack, originalItemStack)) throw new IllegalStateException("Item stack in parent container changed!");
/*     */     
/* 258 */     BsonDocument newMetadata = itemStack.getMetadata();
/*     */     
/* 260 */     BsonDocument containerDocument = (BsonDocument)CONTAINER_CODEC.getOrNull(newMetadata, (ExtraInfo)EmptyExtraInfo.EMPTY);
/* 261 */     if (containerDocument == null) throw new IllegalStateException("Item stack container is empty!");
/*     */     
/* 263 */     ITEMS_CODEC.put(containerDocument, items, (ExtraInfo)EmptyExtraInfo.EMPTY);
/*     */     
/* 265 */     itemContainer.setItemStackForSlot(slot, itemStack.withMetadata(newMetadata));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static ItemStackItemContainer getContainer(@Nonnull ItemContainer itemContainer, short slot) {
/* 277 */     ItemStack itemStack = itemContainer.getItemStack(slot);
/* 278 */     if (ItemStack.isEmpty(itemStack)) return null;
/*     */     
/* 280 */     BsonDocument containerDocument = (BsonDocument)itemStack.getFromMetadataOrNull(CONTAINER_CODEC);
/* 281 */     if (containerDocument == null) return null;
/*     */     
/* 283 */     Short capacity = (Short)CAPACITY_CODEC.getOrNull(containerDocument, (ExtraInfo)EmptyExtraInfo.EMPTY);
/* 284 */     if (capacity == null || capacity.shortValue() <= 0) return null;
/*     */     
/* 286 */     ItemStack[] items = (ItemStack[])ITEMS_CODEC.getOrNull(containerDocument, (ExtraInfo)EmptyExtraInfo.EMPTY);
/* 287 */     if (items == null) items = new ItemStack[capacity.shortValue()];
/*     */     
/* 289 */     return new ItemStackItemContainer(itemContainer, slot, itemStack, capacity.shortValue(), items);
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
/*     */   @Nonnull
/*     */   public static ItemStackItemContainer makeContainerWithCapacity(@Nonnull ItemContainer itemContainer, short slot, short capacity) {
/* 305 */     if (capacity <= 0) throw new IllegalArgumentException("Capacity must be > 0");
/*     */     
/* 307 */     ItemStack itemStack = itemContainer.getItemStack(slot);
/* 308 */     if (ItemStack.isEmpty(itemStack)) throw new IllegalArgumentException("Item stack is empty!");
/*     */     
/* 310 */     ItemStackItemContainer itemStackItemContainer = getContainer(itemContainer, slot);
/* 311 */     if (itemStackItemContainer != null && itemStackItemContainer.getCapacity() != 0) throw new IllegalStateException("Item stack already has a container!");
/*     */     
/* 313 */     BsonDocument newMetadata = itemStack.getMetadata();
/* 314 */     if (newMetadata == null) newMetadata = new BsonDocument();
/*     */     
/* 316 */     BsonDocument containerDocument = (BsonDocument)CONTAINER_CODEC.getOrNull(newMetadata, (ExtraInfo)EmptyExtraInfo.EMPTY);
/* 317 */     if (containerDocument == null) {
/* 318 */       containerDocument = new BsonDocument();
/* 319 */       CONTAINER_CODEC.put(newMetadata, containerDocument, (ExtraInfo)EmptyExtraInfo.EMPTY);
/*     */     } 
/*     */     
/* 322 */     CAPACITY_CODEC.put(containerDocument, Short.valueOf(capacity), (ExtraInfo)EmptyExtraInfo.EMPTY);
/*     */     
/* 324 */     itemContainer.setItemStackForSlot(slot, itemStack.withMetadata(newMetadata));
/*     */     
/* 326 */     return new ItemStackItemContainer(itemContainer, slot, itemStack, capacity, new ItemStack[capacity]);
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
/*     */   @Nullable
/*     */   public static ItemStackItemContainer ensureContainer(@Nonnull ItemContainer itemContainer, short slot, short capacity) {
/* 341 */     if (capacity <= 0) throw new IllegalArgumentException("Capacity must be > 0");
/*     */     
/* 343 */     ItemStack itemStack = itemContainer.getItemStack(slot);
/* 344 */     if (ItemStack.isEmpty(itemStack)) return null;
/*     */     
/* 346 */     ItemStackItemContainer itemStackItemContainer = getContainer(itemContainer, slot);
/* 347 */     if (itemStackItemContainer != null && itemStackItemContainer.getCapacity() != 0) return itemStackItemContainer;
/*     */     
/* 349 */     BsonDocument newMetadata = itemStack.getMetadata();
/* 350 */     if (newMetadata == null) newMetadata = new BsonDocument();
/*     */     
/* 352 */     BsonDocument containerDocument = (BsonDocument)CONTAINER_CODEC.getOrNull(newMetadata, (ExtraInfo)EmptyExtraInfo.EMPTY);
/* 353 */     if (containerDocument == null) {
/* 354 */       containerDocument = new BsonDocument();
/* 355 */       CONTAINER_CODEC.put(newMetadata, containerDocument, (ExtraInfo)EmptyExtraInfo.EMPTY);
/*     */     } 
/*     */     
/* 358 */     CAPACITY_CODEC.put(containerDocument, Short.valueOf(capacity), (ExtraInfo)EmptyExtraInfo.EMPTY);
/*     */     
/* 360 */     itemContainer.setItemStackForSlot(slot, itemStack.withMetadata(newMetadata));
/*     */     
/* 362 */     return new ItemStackItemContainer(itemContainer, slot, itemStack, capacity, new ItemStack[capacity]);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static ItemStackItemContainer ensureConfiguredContainer(@Nonnull ItemContainer itemContainer, short slot, @Nonnull ItemStackContainerConfig config) {
/* 367 */     ItemStackItemContainer itemStackItemContainer = ensureContainer(itemContainer, slot, config.getCapacity());
/* 368 */     if (itemStackItemContainer == null) return null;
/*     */     
/* 370 */     itemStackItemContainer.setGlobalFilter(config.getGlobalFilter());
/*     */     
/* 372 */     int tagIndex = config.getTagIndex();
/* 373 */     if (tagIndex != Integer.MIN_VALUE) {
/* 374 */       short i; for (i = 0; i < itemStackItemContainer.getCapacity(); i = (short)(i + 1)) {
/* 375 */         itemStackItemContainer.setSlotFilter(FilterActionType.ADD, i, (SlotFilter)new TagFilter(tagIndex));
/*     */       }
/*     */     } 
/*     */     
/* 379 */     return itemStackItemContainer;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\inventory\container\ItemStackItemContainer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */