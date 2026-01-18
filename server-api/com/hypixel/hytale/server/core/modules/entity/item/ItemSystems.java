/*     */ package com.hypixel.hytale.server.core.modules.entity.item;
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.HolderSystem;
/*     */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*     */ import com.hypixel.hytale.math.shape.Box;
/*     */ import com.hypixel.hytale.protocol.ColorLight;
/*     */ import com.hypixel.hytale.protocol.ComponentUpdate;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.BoundingBox;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.DynamicLight;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.EntityScaleComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.tracker.EntityTrackerSystems;
/*     */ import com.hypixel.hytale.server.core.modules.entity.tracker.NetworkId;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class ItemSystems {
/*     */   public static class EnsureRequiredComponents extends HolderSystem<EntityStore> {
/*  30 */     private static final ComponentType<EntityStore, ItemComponent> ITEM_COMPONENT_TYPE = ItemComponent.getComponentType();
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Query<EntityStore> getQuery() {
/*  35 */       return (Query)ITEM_COMPONENT_TYPE;
/*     */     }
/*     */ 
/*     */     
/*     */     public void onEntityAdd(@Nonnull Holder<EntityStore> holder, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store) {
/*  40 */       if (!holder.getArchetype().contains(NetworkId.getComponentType())) {
/*  41 */         holder.addComponent(NetworkId.getComponentType(), (Component)new NetworkId(((EntityStore)store.getExternalData()).takeNextNetworkId()));
/*     */       }
/*  43 */       holder.ensureComponent(ItemPhysicsComponent.getComponentType());
/*     */ 
/*     */ 
/*     */       
/*  47 */       holder.putComponent(BoundingBox.getComponentType(), (Component)new BoundingBox(Box.horizontallyCentered(0.5D, 0.5D, 0.5D)));
/*     */       
/*  49 */       ItemComponent itemComponent = (ItemComponent)holder.getComponent(ItemComponent.getComponentType());
/*  50 */       assert itemComponent != null;
/*     */       
/*  52 */       ColorLight itemDynamicLight = itemComponent.computeDynamicLight();
/*  53 */       if (itemDynamicLight != null) {
/*  54 */         holder.putComponent(DynamicLight.getComponentType(), (Component)new DynamicLight(itemDynamicLight));
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void onEntityRemoved(@Nonnull Holder<EntityStore> holder, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class TrackerSystem
/*     */     extends EntityTickingSystem<EntityStore>
/*     */   {
/*     */     @Nonnull
/*     */     private final ComponentType<EntityStore, EntityTrackerSystems.Visible> visibleComponentType;
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     private final Query<EntityStore> query;
/*     */ 
/*     */ 
/*     */     
/*     */     public TrackerSystem(@Nonnull ComponentType<EntityStore, EntityTrackerSystems.Visible> visibleComponentType) {
/*  81 */       this.visibleComponentType = visibleComponentType;
/*  82 */       this.query = (Query<EntityStore>)Query.and(new Query[] { (Query)visibleComponentType, (Query)ItemComponent.getComponentType() });
/*     */     }
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public SystemGroup<EntityStore> getGroup() {
/*  88 */       return EntityTrackerSystems.QUEUE_UPDATE_GROUP;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Query<EntityStore> getQuery() {
/*  94 */       return this.query;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isParallel(int archetypeChunkSize, int taskCount) {
/*  99 */       return EntityTickingSystem.maybeUseParallel(archetypeChunkSize, taskCount);
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 104 */       EntityTrackerSystems.Visible visibleComponent = (EntityTrackerSystems.Visible)archetypeChunk.getComponent(index, this.visibleComponentType);
/* 105 */       assert visibleComponent != null;
/*     */       
/* 107 */       ItemComponent itemComponent = (ItemComponent)archetypeChunk.getComponent(index, ItemComponent.getComponentType());
/* 108 */       assert itemComponent != null;
/*     */       
/* 110 */       float entityScale = 0.0F;
/* 111 */       EntityScaleComponent entityScaleComponent = (EntityScaleComponent)archetypeChunk.getComponent(index, EntityScaleComponent.getComponentType());
/* 112 */       if (entityScaleComponent != null) {
/* 113 */         entityScale = entityScaleComponent.getScale();
/*     */       }
/*     */ 
/*     */       
/* 117 */       if (itemComponent.consumeNetworkOutdated()) {
/* 118 */         queueUpdatesFor(archetypeChunk.getReferenceTo(index), itemComponent, entityScale, visibleComponent.visibleTo);
/* 119 */       } else if (!visibleComponent.newlyVisibleTo.isEmpty()) {
/* 120 */         queueUpdatesFor(archetypeChunk.getReferenceTo(index), itemComponent, entityScale, visibleComponent.newlyVisibleTo);
/*     */       } 
/*     */     }
/*     */     
/*     */     private static void queueUpdatesFor(@Nonnull Ref<EntityStore> ref, @Nonnull ItemComponent item, float entityScale, @Nonnull Map<Ref<EntityStore>, EntityTrackerSystems.EntityViewer> visibleTo) {
/* 125 */       ComponentUpdate update = new ComponentUpdate();
/* 126 */       update.type = ComponentUpdateType.Item;
/* 127 */       ItemStack itemStack = item.getItemStack();
/* 128 */       update.item = (itemStack != null) ? itemStack.toPacket() : null;
/* 129 */       update.entityScale = entityScale;
/*     */       
/* 131 */       for (EntityTrackerSystems.EntityViewer viewer : visibleTo.values())
/* 132 */         viewer.queueUpdate(ref, update); 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\item\ItemSystems.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */