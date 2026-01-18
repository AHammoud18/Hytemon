/*     */ package com.hypixel.hytale.server.core.inventory.container;
/*     */ 
/*     */ import com.hypixel.fastutil.ints.Int2ObjectConcurrentHashMap;
/*     */ import com.hypixel.fastutil.shorts.Short2ObjectConcurrentHashMap;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.map.Short2ObjectMapCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.server.core.entity.ItemUtils;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.inventory.container.filter.FilterActionType;
/*     */ import com.hypixel.hytale.server.core.inventory.container.filter.FilterType;
/*     */ import com.hypixel.hytale.server.core.inventory.container.filter.SlotFilter;
/*     */ import com.hypixel.hytale.server.core.inventory.transaction.ClearTransaction;
/*     */ import com.hypixel.hytale.server.core.inventory.transaction.ItemStackSlotTransaction;
/*     */ import com.hypixel.hytale.server.core.inventory.transaction.ItemStackTransaction;
/*     */ import com.hypixel.hytale.server.core.inventory.transaction.ListTransaction;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import it.unimi.dsi.fastutil.shorts.Short2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.shorts.Short2ObjectOpenHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.locks.ReadWriteLock;
/*     */ import java.util.concurrent.locks.ReentrantReadWriteLock;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
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
/*     */ public class SimpleItemContainer
/*     */   extends ItemContainer
/*     */ {
/*     */   public static final BuilderCodec<SimpleItemContainer> CODEC;
/*     */   protected short capacity;
/*     */   
/*     */   static {
/*  58 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(SimpleItemContainer.class, SimpleItemContainer::new).append(new KeyedCodec("Capacity", (Codec)Codec.SHORT), (o, i) -> o.capacity = i.shortValue(), o -> Short.valueOf(o.capacity)).addValidator(Validators.greaterThanOrEqual(Short.valueOf((short)0))).add()).append(new KeyedCodec("Items", (Codec)new Short2ObjectMapCodec((Codec)ItemStack.CODEC, Short2ObjectOpenHashMap::new, false)), (o, i) -> o.items = i, o -> o.items).add()).afterDecode(i -> { if (i.items == null) i.items = (Short2ObjectMap<ItemStack>)new Short2ObjectOpenHashMap(i.capacity);  i.items.short2ObjectEntrySet().removeIf(()); })).build();
/*     */   }
/*     */   
/*  61 */   protected final ReadWriteLock lock = new ReentrantReadWriteLock();
/*     */   
/*     */   protected Short2ObjectMap<ItemStack> items;
/*  64 */   private final Map<FilterActionType, Int2ObjectConcurrentHashMap<SlotFilter>> slotFilters = new ConcurrentHashMap<>();
/*  65 */   private FilterType globalFilter = FilterType.ALLOW_ALL;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SimpleItemContainer(short capacity) {
/*  71 */     if (capacity <= 0) throw new IllegalArgumentException("Capacity is less than or equal zero! " + capacity + " <= 0");
/*     */     
/*  73 */     this.capacity = capacity;
/*  74 */     this.items = (Short2ObjectMap<ItemStack>)new Short2ObjectOpenHashMap(capacity);
/*     */   }
/*     */   
/*     */   public SimpleItemContainer(@Nonnull SimpleItemContainer other) {
/*  78 */     this.capacity = other.capacity;
/*     */     
/*  80 */     other.lock.readLock().lock();
/*     */     try {
/*  82 */       this.items = (Short2ObjectMap<ItemStack>)new Short2ObjectOpenHashMap(other.items);
/*     */     } finally {
/*  84 */       other.lock.readLock().unlock();
/*     */     } 
/*  86 */     this.slotFilters.putAll(other.slotFilters);
/*  87 */     this.globalFilter = other.globalFilter;
/*     */   }
/*     */ 
/*     */   
/*     */   protected <V> V readAction(@Nonnull Supplier<V> action) {
/*  92 */     this.lock.readLock().lock();
/*     */     try {
/*  94 */       return action.get();
/*     */     } finally {
/*  96 */       this.lock.readLock().unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected <X, V> V readAction(@Nonnull Function<X, V> action, X x) {
/* 102 */     this.lock.readLock().lock();
/*     */     try {
/* 104 */       return action.apply(x);
/*     */     } finally {
/* 106 */       this.lock.readLock().unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected <V> V writeAction(@Nonnull Supplier<V> action) {
/* 112 */     this.lock.writeLock().lock();
/*     */     try {
/* 114 */       return action.get();
/*     */     } finally {
/* 116 */       this.lock.writeLock().unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected <X, V> V writeAction(@Nonnull Function<X, V> action, X x) {
/* 122 */     this.lock.writeLock().lock();
/*     */     try {
/* 124 */       return action.apply(x);
/*     */     } finally {
/* 126 */       this.lock.writeLock().unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected ItemStack internal_getSlot(short slot) {
/* 132 */     return (ItemStack)this.items.get(slot);
/*     */   }
/*     */ 
/*     */   
/*     */   protected ItemStack internal_setSlot(short slot, ItemStack itemStack) {
/* 137 */     if (ItemStack.isEmpty(itemStack)) return internal_removeSlot(slot); 
/* 138 */     return (ItemStack)this.items.put(slot, itemStack);
/*     */   }
/*     */ 
/*     */   
/*     */   protected ItemStack internal_removeSlot(short slot) {
/* 143 */     return (ItemStack)this.items.remove(slot);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean cantAddToSlot(short slot, ItemStack itemStack, ItemStack slotItemStack) {
/* 148 */     if (!this.globalFilter.allowInput()) return true; 
/* 149 */     return testFilter(FilterActionType.ADD, slot, itemStack);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean cantRemoveFromSlot(short slot) {
/* 154 */     if (!this.globalFilter.allowOutput()) return true; 
/* 155 */     return testFilter(FilterActionType.REMOVE, slot, null);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean cantDropFromSlot(short slot) {
/* 160 */     return testFilter(FilterActionType.DROP, slot, null);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean cantMoveToSlot(ItemContainer fromContainer, short slotFrom) {
/* 165 */     return false;
/*     */   }
/*     */   
/*     */   private boolean testFilter(FilterActionType actionType, short slot, ItemStack itemStack) {
/* 169 */     Int2ObjectConcurrentHashMap<SlotFilter> map = this.slotFilters.get(actionType);
/* 170 */     if (map == null) return false;
/*     */     
/* 172 */     SlotFilter filter = (SlotFilter)map.get(slot);
/* 173 */     if (filter == null) return false;
/*     */     
/* 175 */     return !filter.test(actionType, this, slot, itemStack);
/*     */   }
/*     */ 
/*     */   
/*     */   public short getCapacity() {
/* 180 */     return this.capacity;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected ClearTransaction internal_clear() {
/* 186 */     ItemStack[] itemStacks = new ItemStack[getCapacity()];
/* 187 */     for (short i = 0; i < itemStacks.length; i = (short)(i + 1)) {
/* 188 */       itemStacks[i] = (ItemStack)this.items.get(i);
/*     */     }
/* 190 */     this.items.clear();
/* 191 */     return new ClearTransaction(true, (short)0, itemStacks);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public SimpleItemContainer clone() {
/* 197 */     return new SimpleItemContainer(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 202 */     this.lock.readLock().lock();
/*     */     try {
/* 204 */       if (this.items.isEmpty()) return true; 
/*     */     } finally {
/* 206 */       this.lock.readLock().unlock();
/*     */     } 
/*     */     
/* 209 */     return super.isEmpty();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setGlobalFilter(@Nonnull FilterType globalFilter) {
/* 214 */     this.globalFilter = Objects.<FilterType>requireNonNull(globalFilter);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSlotFilter(FilterActionType actionType, short slot, @Nullable SlotFilter filter) {
/* 219 */     validateSlotIndex(slot, getCapacity());
/* 220 */     if (filter != null) {
/* 221 */       ((Int2ObjectConcurrentHashMap)this.slotFilters.computeIfAbsent(actionType, k -> new Int2ObjectConcurrentHashMap())).put(slot, filter);
/*     */     } else {
/* 223 */       this.slotFilters.computeIfPresent(actionType, (k, map) -> {
/*     */             map.remove(slot);
/*     */             return map.isEmpty() ? null : map;
/*     */           });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public ItemStack getItemStack(short slot) {
/* 236 */     validateSlotIndex(slot, getCapacity());
/*     */     
/* 238 */     this.lock.readLock().lock();
/*     */     try {
/* 240 */       return internal_getSlot(slot);
/*     */     } finally {
/* 242 */       this.lock.readLock().unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean equals(Object o) {
/*     */     SimpleItemContainer that;
/* 248 */     if (this == o) return true; 
/* 249 */     if (o instanceof SimpleItemContainer) { that = (SimpleItemContainer)o; } else { return false; }
/*     */     
/* 251 */     if (this.capacity != that.capacity) return false; 
/* 252 */     this.lock.readLock().lock();
/*     */     try {
/* 254 */       return this.items.equals(that.items);
/*     */     } finally {
/* 256 */       this.lock.readLock().unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 263 */     this.lock.readLock().lock();
/*     */     try {
/* 265 */       result = this.items.hashCode();
/*     */     } finally {
/* 267 */       this.lock.readLock().unlock();
/*     */     } 
/* 269 */     int result = 31 * result + this.capacity;
/* 270 */     return result;
/*     */   }
/*     */   
/*     */   public static ItemContainer getNewContainer(short capacity) {
/* 274 */     return ItemContainer.getNewContainer(capacity, SimpleItemContainer::new);
/*     */   }
/*     */   
/*     */   public static boolean addOrDropItemStack(@Nonnull ComponentAccessor<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull ItemContainer itemContainer, @Nonnull ItemStack itemStack) {
/* 278 */     ItemStackTransaction transaction = itemContainer.addItemStack(itemStack);
/* 279 */     ItemStack remainder = transaction.getRemainder();
/* 280 */     if (!ItemStack.isEmpty(remainder)) {
/* 281 */       ItemUtils.dropItem(ref, remainder, store);
/* 282 */       return true;
/*     */     } 
/* 284 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean addOrDropItemStack(@Nonnull ComponentAccessor<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull ItemContainer itemContainer, short slot, @Nonnull ItemStack itemStack) {
/* 288 */     ItemStackSlotTransaction transaction = itemContainer.addItemStackToSlot(slot, itemStack);
/* 289 */     ItemStack remainder = transaction.getRemainder();
/* 290 */     if (!ItemStack.isEmpty(remainder)) {
/* 291 */       return addOrDropItemStack(store, ref, itemContainer, itemStack);
/*     */     }
/* 293 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean addOrDropItemStacks(@Nonnull ComponentAccessor<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull ItemContainer itemContainer, List<ItemStack> itemStacks) {
/* 297 */     ListTransaction<ItemStackTransaction> transaction = itemContainer.addItemStacks(itemStacks);
/* 298 */     boolean droppedItem = false;
/* 299 */     for (ItemStackTransaction stackTransaction : transaction.getList()) {
/* 300 */       ItemStack remainder = stackTransaction.getRemainder();
/* 301 */       if (!ItemStack.isEmpty(remainder)) {
/* 302 */         ItemUtils.dropItem(ref, remainder, store);
/* 303 */         droppedItem = true;
/*     */       } 
/*     */     } 
/* 306 */     return droppedItem;
/*     */   }
/*     */   public static boolean tryAddOrderedOrDropItemStacks(@Nonnull ComponentAccessor<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull ItemContainer itemContainer, List<ItemStack> itemStacks) {
/*     */     ObjectArrayList<ItemStack> objectArrayList;
/* 310 */     ListTransaction<ItemStackSlotTransaction> transaction = itemContainer.addItemStacksOrdered(itemStacks);
/* 311 */     List<ItemStack> remainderItemStacks = null;
/*     */     
/* 313 */     for (ItemStackSlotTransaction stackTransaction : transaction.getList()) {
/*     */       
/* 315 */       ItemStack remainder = stackTransaction.getRemainder();
/* 316 */       if (!ItemStack.isEmpty(remainder)) {
/* 317 */         if (remainderItemStacks == null) objectArrayList = new ObjectArrayList(); 
/* 318 */         objectArrayList.add(remainder);
/*     */       } 
/*     */     } 
/* 321 */     return addOrDropItemStacks(store, ref, itemContainer, (List<ItemStack>)objectArrayList);
/*     */   }
/*     */   
/*     */   protected SimpleItemContainer() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\inventory\container\SimpleItemContainer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */