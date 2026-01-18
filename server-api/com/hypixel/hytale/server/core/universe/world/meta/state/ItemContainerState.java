/*     */ package com.hypixel.hytale.server.core.universe.world.meta.state;
/*     */ 
/*     */ import com.hypixel.fastutil.shorts.Short2ObjectConcurrentHashMap;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.event.EventPriority;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.StateData;
/*     */ import com.hypixel.hytale.server.core.entity.entities.player.windows.ContainerBlockWindow;
/*     */ import com.hypixel.hytale.server.core.entity.entities.player.windows.WindowManager;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
/*     */ import com.hypixel.hytale.server.core.inventory.container.SimpleItemContainer;
/*     */ import com.hypixel.hytale.server.core.modules.entity.item.ItemComponent;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.meta.BlockState;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.worldmap.WorldMapManager;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.logging.Level;
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
/*     */ public class ItemContainerState
/*     */   extends BlockState
/*     */   implements ItemContainerBlockState, DestroyableBlockState, MarkerBlockState
/*     */ {
/*     */   public static final Codec<ItemContainerState> CODEC;
/*     */   
/*     */   static {
/*  60 */     CODEC = (Codec<ItemContainerState>)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ItemContainerState.class, ItemContainerState::new, BlockState.BASE_CODEC).addField(new KeyedCodec("Custom", (Codec)Codec.BOOLEAN), (state, o) -> state.custom = o.booleanValue(), state -> Boolean.valueOf(state.custom))).addField(new KeyedCodec("AllowViewing", (Codec)Codec.BOOLEAN), (state, o) -> state.allowViewing = o.booleanValue(), state -> Boolean.valueOf(state.allowViewing))).addField(new KeyedCodec("Droplist", (Codec)Codec.STRING), (state, o) -> state.droplist = o, state -> state.droplist)).addField(new KeyedCodec("Marker", (Codec)WorldMapManager.MarkerReference.CODEC), (state, o) -> state.marker = o, state -> state.marker)).addField(new KeyedCodec("ItemContainer", (Codec)SimpleItemContainer.CODEC), (state, o) -> state.itemContainer = o, state -> state.itemContainer)).build();
/*     */   }
/*  62 */   private final Map<UUID, ContainerBlockWindow> windows = new ConcurrentHashMap<>();
/*     */   
/*     */   protected boolean custom;
/*     */   
/*     */   protected boolean allowViewing = true;
/*     */   
/*     */   @Nullable
/*     */   protected String droplist;
/*     */   protected SimpleItemContainer itemContainer;
/*     */   protected WorldMapManager.MarkerReference marker;
/*     */   
/*     */   public boolean initialize(@Nonnull BlockType blockType) {
/*  74 */     if (!super.initialize(blockType)) return false; 
/*  75 */     if (this.custom) return true;
/*     */     
/*  77 */     short capacity = 20;
/*  78 */     StateData stateData = blockType.getState(); if (stateData instanceof ItemContainerStateData) { ItemContainerStateData itemContainerStateData = (ItemContainerStateData)stateData;
/*  79 */       capacity = itemContainerStateData.getCapacity(); }
/*     */ 
/*     */     
/*  82 */     ObjectArrayList objectArrayList = new ObjectArrayList();
/*  83 */     this.itemContainer = (SimpleItemContainer)ItemContainer.ensureContainerCapacity((ItemContainer)this.itemContainer, capacity, SimpleItemContainer::new, (List)objectArrayList);
/*  84 */     this.itemContainer.registerChangeEvent(EventPriority.LAST, this::onItemChange);
/*     */ 
/*     */     
/*  87 */     if (!objectArrayList.isEmpty()) {
/*  88 */       WorldChunk chunk = getChunk();
/*  89 */       World world = chunk.getWorld();
/*  90 */       Store<EntityStore> store = world.getEntityStore().getStore();
/*     */       
/*  92 */       ((HytaleLogger.Api)HytaleLogger.getLogger().at(Level.WARNING).withCause(new Throwable())).log("Dropping %d excess items from item container: %s at world: %s, chunk: %s, block: %s", Integer.valueOf(objectArrayList.size()), blockType.getId(), chunk.getWorld().getName(), chunk, getPosition());
/*  93 */       Vector3i blockPosition = getBlockPosition();
/*  94 */       Holder[] arrayOfHolder = ItemComponent.generateItemDrops((ComponentAccessor)store, (List)objectArrayList, blockPosition.toVector3d(), Vector3f.ZERO);
/*  95 */       store.addEntities(arrayOfHolder, AddReason.SPAWN);
/*     */     } 
/*     */     
/*  98 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canOpen(@Nonnull Ref<EntityStore> ref, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 109 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onOpen(@Nonnull Ref<EntityStore> ref, @Nonnull World world, @Nonnull Store<EntityStore> store) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDestroy() {
/* 125 */     WindowManager.closeAndRemoveAll(this.windows);
/*     */     
/* 127 */     WorldChunk chunk = getChunk();
/* 128 */     World world = chunk.getWorld();
/* 129 */     Store<EntityStore> store = world.getEntityStore().getStore();
/*     */ 
/*     */     
/* 132 */     List<ItemStack> allItemStacks = this.itemContainer.dropAllItemStacks();
/* 133 */     Vector3d dropPosition = getBlockPosition().toVector3d().add(0.5D, 0.0D, 0.5D);
/* 134 */     Holder[] arrayOfHolder = ItemComponent.generateItemDrops((ComponentAccessor)store, allItemStacks, dropPosition, Vector3f.ZERO);
/* 135 */     if (arrayOfHolder.length > 0)
/*     */     {
/* 137 */       world.execute(() -> store.addEntities(itemEntityHolders, AddReason.SPAWN));
/*     */     }
/*     */     
/* 140 */     if (this.marker != null) this.marker.remove(); 
/*     */   }
/*     */   
/*     */   public void setCustom(boolean custom) {
/* 144 */     this.custom = custom;
/* 145 */     markNeedsSave();
/*     */   }
/*     */   
/*     */   public void setAllowViewing(boolean allowViewing) {
/* 149 */     this.allowViewing = allowViewing;
/* 150 */     markNeedsSave();
/*     */   }
/*     */   
/*     */   public boolean isAllowViewing() {
/* 154 */     return this.allowViewing;
/*     */   }
/*     */   
/*     */   public void setItemContainer(SimpleItemContainer itemContainer) {
/* 158 */     this.itemContainer = itemContainer;
/* 159 */     markNeedsSave();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String getDroplist() {
/* 164 */     return this.droplist;
/*     */   }
/*     */   
/*     */   public void setDroplist(@Nullable String droplist) {
/* 168 */     this.droplist = droplist;
/* 169 */     markNeedsSave();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMarker(WorldMapManager.MarkerReference marker) {
/* 174 */     this.marker = marker;
/* 175 */     markNeedsSave();
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Map<UUID, ContainerBlockWindow> getWindows() {
/* 180 */     return this.windows;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemContainer getItemContainer() {
/* 185 */     return (ItemContainer)this.itemContainer;
/*     */   }
/*     */   
/*     */   public void onItemChange(ItemContainer.ItemContainerChangeEvent event) {
/* 189 */     markNeedsSave();
/*     */   }
/*     */ 
/*     */   
/*     */   public static class ItemContainerStateData
/*     */     extends StateData
/*     */   {
/*     */     public static final BuilderCodec<ItemContainerStateData> CODEC;
/*     */ 
/*     */     
/*     */     static {
/* 200 */       CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(ItemContainerStateData.class, ItemContainerStateData::new, StateData.DEFAULT_CODEC).appendInherited(new KeyedCodec("Capacity", (Codec)Codec.INTEGER), (t, i) -> t.capacity = i.shortValue(), t -> Integer.valueOf(t.capacity), (o, p) -> o.capacity = p.capacity).add()).build();
/*     */     }
/* 202 */     private short capacity = 20;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public short getCapacity() {
/* 208 */       return this.capacity;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 214 */       return "ItemContainerStateData{capacity=" + this.capacity + "} " + super
/*     */         
/* 216 */         .toString();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\meta\state\ItemContainerState.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */