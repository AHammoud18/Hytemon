/*     */ package com.hypixel.hytale.server.core.modules.entity.tracker;
/*     */ 
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.Archetype;
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.SystemGroup;
/*     */ import com.hypixel.hytale.component.dependency.Dependency;
/*     */ import com.hypixel.hytale.component.dependency.Order;
/*     */ import com.hypixel.hytale.component.dependency.SystemDependency;
/*     */ import com.hypixel.hytale.component.dependency.SystemGroupDependency;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.spatial.SpatialResource;
/*     */ import com.hypixel.hytale.component.spatial.SpatialStructure;
/*     */ import com.hypixel.hytale.component.system.HolderSystem;
/*     */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.protocol.ComponentUpdate;
/*     */ import com.hypixel.hytale.protocol.ComponentUpdateType;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.packets.entities.EntityUpdates;
/*     */ import com.hypixel.hytale.server.core.entity.effect.EffectControllerComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.system.NetworkSendableSpatialSystem;
/*     */ import com.hypixel.hytale.server.core.receiver.IPacketReceiver;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.ints.IntList;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectList;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.EnumSet;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.locks.StampedLock;
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
/*     */ 
/*     */ public class EntityTrackerSystems
/*     */ {
/*     */   public static boolean despawnAll(@Nonnull Ref<EntityStore> viewerRef, @Nonnull Store<EntityStore> store) {
/* 112 */     EntityViewer viewer = (EntityViewer)store.getComponent(viewerRef, EntityViewer.getComponentType());
/* 113 */     if (viewer == null) return false;
/*     */ 
/*     */     
/* 116 */     int networkId = viewer.sent.removeInt(viewerRef);
/*     */ 
/*     */     
/* 119 */     EntityUpdates packet = new EntityUpdates();
/* 120 */     packet.removed = viewer.sent.values().toIntArray();
/* 121 */     viewer.packetReceiver.writeNoCache((Packet)packet);
/*     */     
/* 123 */     clear(viewerRef, store);
/*     */ 
/*     */     
/* 126 */     viewer.sent.put(viewerRef, networkId);
/* 127 */     return true;
/*     */   }
/*     */   
/*     */   public static boolean clear(@Nonnull Ref<EntityStore> viewerRef, @Nonnull Store<EntityStore> store) {
/* 131 */     EntityViewer viewer = (EntityViewer)store.getComponent(viewerRef, EntityViewer.getComponentType());
/* 132 */     if (viewer == null) return false;
/*     */ 
/*     */     
/* 135 */     for (ObjectIterator<Ref<EntityStore>> objectIterator = viewer.sent.keySet().iterator(); objectIterator.hasNext(); ) { Ref<EntityStore> ref = objectIterator.next();
/* 136 */       Visible visible = (Visible)store.getComponent(ref, Visible.getComponentType());
/* 137 */       if (visible != null) visible.visibleTo.remove(viewerRef);
/*     */        }
/*     */ 
/*     */     
/* 141 */     viewer.sent.clear();
/* 142 */     return true;
/*     */   }
/*     */   
/*     */   public static class EntityUpdate {
/*     */     @Nonnull
/* 147 */     private final StampedLock removeLock = new StampedLock();
/*     */     
/*     */     @Nonnull
/*     */     private final EnumSet<ComponentUpdateType> removed;
/*     */     
/*     */     @Nonnull
/* 153 */     private final StampedLock updatesLock = new StampedLock();
/*     */     
/*     */     @Nonnull
/*     */     private final List<ComponentUpdate> updates;
/*     */ 
/*     */     
/*     */     public EntityUpdate() {
/* 160 */       this.removed = EnumSet.noneOf(ComponentUpdateType.class);
/* 161 */       this.updates = (List<ComponentUpdate>)new ObjectArrayList();
/*     */     }
/*     */     
/*     */     public EntityUpdate(@Nonnull EntityUpdate other) {
/* 165 */       this.removed = EnumSet.copyOf(other.removed);
/* 166 */       this.updates = (List<ComponentUpdate>)new ObjectArrayList(other.updates);
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public EntityUpdate clone() {
/* 172 */       return new EntityUpdate(this);
/*     */     }
/*     */     
/*     */     public void queueRemove(@Nonnull ComponentUpdateType type) {
/* 176 */       long stamp = this.removeLock.writeLock();
/*     */       try {
/* 178 */         this.removed.add(type);
/*     */       } finally {
/* 180 */         this.removeLock.unlockWrite(stamp);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void queueUpdate(@Nonnull ComponentUpdate update) {
/* 185 */       long stamp = this.updatesLock.writeLock();
/*     */       try {
/* 187 */         this.updates.add(update);
/*     */       } finally {
/* 189 */         this.updatesLock.unlockWrite(stamp);
/*     */       } 
/*     */     }
/*     */     
/*     */     @Nullable
/*     */     public ComponentUpdateType[] toRemovedArray() {
/* 195 */       return this.removed.isEmpty() ? null : (ComponentUpdateType[])this.removed.toArray(x$0 -> new ComponentUpdateType[x$0]);
/*     */     }
/*     */     
/*     */     @Nullable
/*     */     public ComponentUpdate[] toUpdatesArray() {
/* 200 */       return this.updates.isEmpty() ? null : (ComponentUpdate[])this.updates.toArray(x$0 -> new ComponentUpdate[x$0]);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class EntityViewer implements Component<EntityStore> {
/*     */     public int viewRadiusBlocks;
/*     */     public IPacketReceiver packetReceiver;
/*     */     public Set<Ref<EntityStore>> visible;
/*     */     
/*     */     public static ComponentType<EntityStore, EntityViewer> getComponentType() {
/* 210 */       return EntityModule.get().getEntityViewerComponentType();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Map<Ref<EntityStore>, EntityTrackerSystems.EntityUpdate> updates;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Object2IntMap<Ref<EntityStore>> sent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int lodExcludedCount;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int hiddenCount;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public EntityViewer(int viewRadiusBlocks, IPacketReceiver packetReceiver) {
/* 251 */       this.viewRadiusBlocks = viewRadiusBlocks;
/* 252 */       this.packetReceiver = packetReceiver;
/*     */       
/* 254 */       this.visible = (Set<Ref<EntityStore>>)new ObjectOpenHashSet();
/* 255 */       this.updates = new ConcurrentHashMap<>();
/* 256 */       this.sent = (Object2IntMap<Ref<EntityStore>>)new Object2IntOpenHashMap();
/* 257 */       this.sent.defaultReturnValue(-1);
/*     */     }
/*     */     
/*     */     public EntityViewer(@Nonnull EntityViewer other) {
/* 261 */       this.viewRadiusBlocks = other.viewRadiusBlocks;
/* 262 */       this.packetReceiver = other.packetReceiver;
/* 263 */       this.visible = new HashSet<>(other.visible);
/* 264 */       this.updates = new ConcurrentHashMap<>(other.updates.size());
/* 265 */       for (Map.Entry<Ref<EntityStore>, EntityTrackerSystems.EntityUpdate> entry : other.updates.entrySet()) {
/* 266 */         this.updates.put(entry.getKey(), ((EntityTrackerSystems.EntityUpdate)entry.getValue()).clone());
/*     */       }
/* 268 */       this.sent = (Object2IntMap<Ref<EntityStore>>)new Object2IntOpenHashMap(other.sent);
/* 269 */       this.sent.defaultReturnValue(-1);
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Component<EntityStore> clone() {
/* 275 */       return new EntityViewer(this);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void queueRemove(Ref<EntityStore> ref, ComponentUpdateType type) {
/* 285 */       if (!this.visible.contains(ref)) throw new IllegalArgumentException("Entity is not visible!"); 
/* 286 */       ((EntityTrackerSystems.EntityUpdate)this.updates.computeIfAbsent(ref, k -> new EntityTrackerSystems.EntityUpdate())).queueRemove(type);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void queueUpdate(Ref<EntityStore> ref, ComponentUpdate update) {
/* 296 */       if (!this.visible.contains(ref)) throw new IllegalArgumentException("Entity is not visible!"); 
/* 297 */       ((EntityTrackerSystems.EntityUpdate)this.updates.computeIfAbsent(ref, k -> new EntityTrackerSystems.EntityUpdate())).queueUpdate(update);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Visible
/*     */     implements Component<EntityStore>
/*     */   {
/*     */     @Nonnull
/*     */     private final StampedLock lock;
/*     */     @Nonnull
/*     */     public Map<Ref<EntityStore>, EntityTrackerSystems.EntityViewer> previousVisibleTo;
/*     */     @Nonnull
/*     */     public Map<Ref<EntityStore>, EntityTrackerSystems.EntityViewer> visibleTo;
/*     */     @Nonnull
/*     */     public Map<Ref<EntityStore>, EntityTrackerSystems.EntityViewer> newlyVisibleTo;
/*     */     
/*     */     public Visible() {
/* 314 */       this.lock = new StampedLock();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 320 */       this.previousVisibleTo = (Map<Ref<EntityStore>, EntityTrackerSystems.EntityViewer>)new Object2ObjectOpenHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 326 */       this.visibleTo = (Map<Ref<EntityStore>, EntityTrackerSystems.EntityViewer>)new Object2ObjectOpenHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 332 */       this.newlyVisibleTo = (Map<Ref<EntityStore>, EntityTrackerSystems.EntityViewer>)new Object2ObjectOpenHashMap();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Component<EntityStore> clone() {
/* 339 */       return new Visible();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public static ComponentType<EntityStore, Visible> getComponentType() {
/*     */       return EntityModule.get().getVisibleComponentType();
/*     */     }
/*     */ 
/*     */     
/*     */     public void addViewerParallel(Ref<EntityStore> ref, EntityTrackerSystems.EntityViewer entityViewer) {
/* 351 */       long stamp = this.lock.writeLock();
/*     */       try {
/* 353 */         this.visibleTo.put(ref, entityViewer);
/*     */         
/* 355 */         if (!this.previousVisibleTo.containsKey(ref)) {
/* 356 */           this.newlyVisibleTo.put(ref, entityViewer);
/*     */         }
/*     */       } finally {
/* 359 */         this.lock.unlockWrite(stamp);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public static class ClearEntityViewers extends EntityTickingSystem<EntityStore> {
/* 365 */     public static final Set<Dependency<EntityStore>> DEPENDENCIES = (Set)Collections.singleton(new SystemGroupDependency(Order.BEFORE, EntityTrackerSystems.FIND_VISIBLE_ENTITIES_GROUP));
/*     */     
/*     */     private final ComponentType<EntityStore, EntityTrackerSystems.EntityViewer> componentType;
/*     */     
/*     */     public ClearEntityViewers(ComponentType<EntityStore, EntityTrackerSystems.EntityViewer> componentType) {
/* 370 */       this.componentType = componentType;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Set<Dependency<EntityStore>> getDependencies() {
/* 376 */       return DEPENDENCIES;
/*     */     }
/*     */ 
/*     */     
/*     */     public Query<EntityStore> getQuery() {
/* 381 */       return (Query)this.componentType;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isParallel(int archetypeChunkSize, int taskCount) {
/* 386 */       return EntityTickingSystem.maybeUseParallel(archetypeChunkSize, taskCount);
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 391 */       EntityTrackerSystems.EntityViewer viewer = (EntityTrackerSystems.EntityViewer)archetypeChunk.getComponent(index, this.componentType);
/* 392 */       viewer.visible.clear();
/* 393 */       viewer.lodExcludedCount = 0;
/* 394 */       viewer.hiddenCount = 0;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class CollectVisible
/*     */     extends EntityTickingSystem<EntityStore> {
/*     */     private final ComponentType<EntityStore, EntityTrackerSystems.EntityViewer> componentType;
/*     */     private final Query<EntityStore> query;
/*     */     @Nonnull
/*     */     private final Set<Dependency<EntityStore>> dependencies;
/*     */     
/*     */     public CollectVisible(ComponentType<EntityStore, EntityTrackerSystems.EntityViewer> componentType) {
/* 406 */       this.componentType = componentType;
/* 407 */       this.query = (Query<EntityStore>)Archetype.of(new ComponentType[] { componentType, TransformComponent.getComponentType() });
/*     */       
/* 409 */       this.dependencies = Collections.singleton(new SystemDependency(Order.AFTER, NetworkSendableSpatialSystem.class));
/*     */     }
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public SystemGroup<EntityStore> getGroup() {
/* 415 */       return EntityTrackerSystems.FIND_VISIBLE_ENTITIES_GROUP;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Set<Dependency<EntityStore>> getDependencies() {
/* 421 */       return this.dependencies;
/*     */     }
/*     */ 
/*     */     
/*     */     public Query<EntityStore> getQuery() {
/* 426 */       return this.query;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isParallel(int archetypeChunkSize, int taskCount) {
/* 431 */       return EntityTickingSystem.maybeUseParallel(archetypeChunkSize, taskCount);
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 436 */       TransformComponent transform = (TransformComponent)archetypeChunk.getComponent(index, TransformComponent.getComponentType());
/* 437 */       Vector3d position = transform.getPosition();
/* 438 */       EntityTrackerSystems.EntityViewer entityViewer = (EntityTrackerSystems.EntityViewer)archetypeChunk.getComponent(index, this.componentType);
/* 439 */       SpatialStructure<Ref<EntityStore>> spatialStructure = ((SpatialResource)store.getResource(EntityModule.get().getNetworkSendableSpatialResourceType())).getSpatialStructure();
/*     */       
/* 441 */       ObjectList<Ref<EntityStore>> results = SpatialResource.getThreadLocalReferenceList();
/* 442 */       spatialStructure.collect(position, entityViewer.viewRadiusBlocks, (List)results);
/* 443 */       entityViewer.visible.addAll((Collection<? extends Ref<EntityStore>>)results);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 450 */   public static final SystemGroup<EntityStore> FIND_VISIBLE_ENTITIES_GROUP = EntityStore.REGISTRY.registerSystemGroup();
/*     */   
/*     */   public static class ClearPreviouslyVisible extends EntityTickingSystem<EntityStore> {
/* 453 */     public static final Set<Dependency<EntityStore>> DEPENDENCIES = (Set)Set.of(new SystemDependency(Order.AFTER, EntityTrackerSystems.ClearEntityViewers.class), new SystemGroupDependency(Order.AFTER, EntityTrackerSystems.FIND_VISIBLE_ENTITIES_GROUP));
/*     */ 
/*     */ 
/*     */     
/*     */     private final ComponentType<EntityStore, EntityTrackerSystems.Visible> componentType;
/*     */ 
/*     */ 
/*     */     
/*     */     public ClearPreviouslyVisible(ComponentType<EntityStore, EntityTrackerSystems.Visible> componentType) {
/* 462 */       this.componentType = componentType;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Set<Dependency<EntityStore>> getDependencies() {
/* 468 */       return DEPENDENCIES;
/*     */     }
/*     */ 
/*     */     
/*     */     public Query<EntityStore> getQuery() {
/* 473 */       return (Query)this.componentType;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isParallel(int archetypeChunkSize, int taskCount) {
/* 478 */       return EntityTickingSystem.maybeUseParallel(archetypeChunkSize, taskCount);
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 483 */       EntityTrackerSystems.Visible visible = (EntityTrackerSystems.Visible)archetypeChunk.getComponent(index, this.componentType);
/*     */ 
/*     */       
/* 486 */       Map<Ref<EntityStore>, EntityTrackerSystems.EntityViewer> oldVisibleTo = visible.previousVisibleTo;
/* 487 */       visible.previousVisibleTo = visible.visibleTo;
/* 488 */       visible.visibleTo = oldVisibleTo;
/*     */ 
/*     */       
/* 491 */       visible.visibleTo.clear();
/* 492 */       visible.newlyVisibleTo.clear();
/*     */     }
/*     */   }
/*     */   
/*     */   public static class EnsureVisibleComponent extends EntityTickingSystem<EntityStore> {
/* 497 */     public static final Set<Dependency<EntityStore>> DEPENDENCIES = (Set)Collections.singleton(new SystemDependency(Order.AFTER, EntityTrackerSystems.ClearPreviouslyVisible.class));
/*     */     
/*     */     private final ComponentType<EntityStore, EntityTrackerSystems.EntityViewer> entityViewerComponentType;
/*     */     private final ComponentType<EntityStore, EntityTrackerSystems.Visible> visibleComponentType;
/*     */     
/*     */     public EnsureVisibleComponent(ComponentType<EntityStore, EntityTrackerSystems.EntityViewer> entityViewerComponentType, ComponentType<EntityStore, EntityTrackerSystems.Visible> visibleComponentType) {
/* 503 */       this.entityViewerComponentType = entityViewerComponentType;
/* 504 */       this.visibleComponentType = visibleComponentType;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Set<Dependency<EntityStore>> getDependencies() {
/* 510 */       return DEPENDENCIES;
/*     */     }
/*     */ 
/*     */     
/*     */     public Query<EntityStore> getQuery() {
/* 515 */       return (Query)this.entityViewerComponentType;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isParallel(int archetypeChunkSize, int taskCount) {
/* 520 */       return EntityTickingSystem.maybeUseParallel(archetypeChunkSize, taskCount);
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 525 */       for (Ref<EntityStore> ref : ((EntityTrackerSystems.EntityViewer)archetypeChunk.getComponent(index, this.entityViewerComponentType)).visible) {
/*     */         
/* 527 */         if (!commandBuffer.getArchetype(ref).contains(this.visibleComponentType))
/*     */         {
/* 529 */           commandBuffer.ensureComponent(ref, this.visibleComponentType);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public static class AddToVisible extends EntityTickingSystem<EntityStore> {
/* 536 */     public static final Set<Dependency<EntityStore>> DEPENDENCIES = (Set)Collections.singleton(new SystemDependency(Order.AFTER, EntityTrackerSystems.EnsureVisibleComponent.class));
/*     */     
/*     */     private final ComponentType<EntityStore, EntityTrackerSystems.EntityViewer> entityViewerComponentType;
/*     */     private final ComponentType<EntityStore, EntityTrackerSystems.Visible> visibleComponentType;
/*     */     
/*     */     public AddToVisible(ComponentType<EntityStore, EntityTrackerSystems.EntityViewer> entityViewerComponentType, ComponentType<EntityStore, EntityTrackerSystems.Visible> visibleComponentType) {
/* 542 */       this.entityViewerComponentType = entityViewerComponentType;
/* 543 */       this.visibleComponentType = visibleComponentType;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Set<Dependency<EntityStore>> getDependencies() {
/* 549 */       return DEPENDENCIES;
/*     */     }
/*     */ 
/*     */     
/*     */     public Query<EntityStore> getQuery() {
/* 554 */       return (Query)this.entityViewerComponentType;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isParallel(int archetypeChunkSize, int taskCount) {
/* 559 */       return EntityTickingSystem.maybeUseParallel(archetypeChunkSize, taskCount);
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 564 */       Ref<EntityStore> viewerRef = archetypeChunk.getReferenceTo(index);
/* 565 */       EntityTrackerSystems.EntityViewer viewer = (EntityTrackerSystems.EntityViewer)archetypeChunk.getComponent(index, this.entityViewerComponentType);
/* 566 */       for (Ref<EntityStore> ref : viewer.visible)
/*     */       {
/* 568 */         ((EntityTrackerSystems.Visible)commandBuffer.getComponent(ref, this.visibleComponentType)).addViewerParallel(viewerRef, viewer);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public static class RemoveEmptyVisibleComponent extends EntityTickingSystem<EntityStore> {
/* 574 */     public static final Set<Dependency<EntityStore>> DEPENDENCIES = (Set)Set.of(new SystemDependency(Order.AFTER, EntityTrackerSystems.AddToVisible.class), new SystemGroupDependency(Order.BEFORE, EntityTrackerSystems.QUEUE_UPDATE_GROUP));
/*     */ 
/*     */     
/*     */     private final ComponentType<EntityStore, EntityTrackerSystems.Visible> componentType;
/*     */ 
/*     */ 
/*     */     
/*     */     public RemoveEmptyVisibleComponent(ComponentType<EntityStore, EntityTrackerSystems.Visible> componentType) {
/* 582 */       this.componentType = componentType;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Set<Dependency<EntityStore>> getDependencies() {
/* 588 */       return DEPENDENCIES;
/*     */     }
/*     */ 
/*     */     
/*     */     public Query<EntityStore> getQuery() {
/* 593 */       return (Query)this.componentType;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isParallel(int archetypeChunkSize, int taskCount) {
/* 598 */       return EntityTickingSystem.maybeUseParallel(archetypeChunkSize, taskCount);
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 603 */       if (((EntityTrackerSystems.Visible)archetypeChunk.getComponent(index, this.componentType)).visibleTo.isEmpty()) {
/* 604 */         commandBuffer.removeComponent(archetypeChunk.getReferenceTo(index), this.componentType);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class RemoveVisibleComponent
/*     */     extends HolderSystem<EntityStore>
/*     */   {
/*     */     private final ComponentType<EntityStore, EntityTrackerSystems.Visible> componentType;
/*     */ 
/*     */     
/*     */     public RemoveVisibleComponent(ComponentType<EntityStore, EntityTrackerSystems.Visible> componentType) {
/* 618 */       this.componentType = componentType;
/*     */     }
/*     */ 
/*     */     
/*     */     public Query<EntityStore> getQuery() {
/* 623 */       return (Query)this.componentType;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void onEntityAdd(@Nonnull Holder<EntityStore> holder, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store) {}
/*     */ 
/*     */     
/*     */     public void onEntityRemoved(@Nonnull Holder<EntityStore> holder, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store) {
/* 632 */       holder.removeComponent(this.componentType);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EffectControllerSystem
/*     */     extends EntityTickingSystem<EntityStore>
/*     */   {
/*     */     @Nonnull
/*     */     private final ComponentType<EntityStore, EntityTrackerSystems.Visible> visibleComponentType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     private final ComponentType<EntityStore, EffectControllerComponent> effectControllerComponentType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     private final Query<EntityStore> query;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public EffectControllerSystem(@Nonnull ComponentType<EntityStore, EntityTrackerSystems.Visible> visibleComponentType, @Nonnull ComponentType<EntityStore, EffectControllerComponent> effectControllerComponentType) {
/* 667 */       this.visibleComponentType = visibleComponentType;
/* 668 */       this.effectControllerComponentType = effectControllerComponentType;
/* 669 */       this.query = (Query<EntityStore>)Query.and(new Query[] { (Query)visibleComponentType, (Query)effectControllerComponentType });
/*     */     }
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public SystemGroup<EntityStore> getGroup() {
/* 675 */       return EntityTrackerSystems.QUEUE_UPDATE_GROUP;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Query<EntityStore> getQuery() {
/* 681 */       return this.query;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isParallel(int archetypeChunkSize, int taskCount) {
/* 686 */       return EntityTickingSystem.maybeUseParallel(archetypeChunkSize, taskCount);
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 691 */       EntityTrackerSystems.Visible visibleComponent = (EntityTrackerSystems.Visible)archetypeChunk.getComponent(index, this.visibleComponentType);
/* 692 */       assert visibleComponent != null;
/*     */       
/* 694 */       Ref<EntityStore> entityRef = archetypeChunk.getReferenceTo(index);
/*     */       
/* 696 */       EffectControllerComponent effectControllerComponent = (EffectControllerComponent)archetypeChunk.getComponent(index, this.effectControllerComponentType);
/* 697 */       assert effectControllerComponent != null;
/*     */       
/* 699 */       if (!visibleComponent.newlyVisibleTo.isEmpty()) {
/* 700 */         queueFullUpdate(entityRef, effectControllerComponent, visibleComponent.newlyVisibleTo);
/*     */       }
/*     */       
/* 703 */       if (effectControllerComponent.consumeNetworkOutdated()) {
/* 704 */         queueUpdatesFor(entityRef, effectControllerComponent, visibleComponent.visibleTo, visibleComponent.newlyVisibleTo);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private static void queueFullUpdate(@Nonnull Ref<EntityStore> ref, @Nonnull EffectControllerComponent effectControllerComponent, @Nonnull Map<Ref<EntityStore>, EntityTrackerSystems.EntityViewer> visibleTo) {
/* 711 */       ComponentUpdate update = new ComponentUpdate();
/* 712 */       update.type = ComponentUpdateType.EntityEffects;
/* 713 */       update.entityEffectUpdates = effectControllerComponent.createInitUpdates();
/*     */       
/* 715 */       for (EntityTrackerSystems.EntityViewer viewer : visibleTo.values()) {
/* 716 */         viewer.queueUpdate(ref, update);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static void queueUpdatesFor(@Nonnull Ref<EntityStore> ref, @Nonnull EffectControllerComponent effectControllerComponent, @Nonnull Map<Ref<EntityStore>, EntityTrackerSystems.EntityViewer> visibleTo, @Nonnull Map<Ref<EntityStore>, EntityTrackerSystems.EntityViewer> exclude) {
/* 724 */       ComponentUpdate update = new ComponentUpdate();
/* 725 */       update.type = ComponentUpdateType.EntityEffects;
/* 726 */       update.entityEffectUpdates = effectControllerComponent.consumeChanges();
/*     */ 
/*     */       
/* 729 */       if (exclude.isEmpty()) {
/* 730 */         for (EntityTrackerSystems.EntityViewer viewer : visibleTo.values()) {
/* 731 */           viewer.queueUpdate(ref, update);
/*     */         }
/*     */         
/*     */         return;
/*     */       } 
/* 736 */       for (Map.Entry<Ref<EntityStore>, EntityTrackerSystems.EntityViewer> entry : visibleTo.entrySet()) {
/*     */         
/* 738 */         if (exclude.containsKey(entry.getKey()))
/* 739 */           continue;  ((EntityTrackerSystems.EntityViewer)entry.getValue()).queueUpdate(ref, update);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 747 */   public static final SystemGroup<EntityStore> QUEUE_UPDATE_GROUP = EntityStore.REGISTRY.registerSystemGroup();
/*     */   
/*     */   public static class SendPackets extends EntityTickingSystem<EntityStore> {
/* 750 */     public static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */     
/* 752 */     public static final ThreadLocal<IntList> INT_LIST_THREAD_LOCAL = ThreadLocal.withInitial(it.unimi.dsi.fastutil.ints.IntArrayList::new);
/* 753 */     public static final Set<Dependency<EntityStore>> DEPENDENCIES = (Set)Set.of(new SystemGroupDependency(Order.AFTER, EntityTrackerSystems.QUEUE_UPDATE_GROUP));
/*     */ 
/*     */     
/*     */     private final ComponentType<EntityStore, EntityTrackerSystems.EntityViewer> componentType;
/*     */ 
/*     */     
/*     */     public SendPackets(ComponentType<EntityStore, EntityTrackerSystems.EntityViewer> componentType) {
/* 760 */       this.componentType = componentType;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public SystemGroup<EntityStore> getGroup() {
/* 766 */       return EntityStore.SEND_PACKET_GROUP;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Set<Dependency<EntityStore>> getDependencies() {
/* 772 */       return DEPENDENCIES;
/*     */     }
/*     */ 
/*     */     
/*     */     public Query<EntityStore> getQuery() {
/* 777 */       return (Query)this.componentType;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isParallel(int archetypeChunkSize, int taskCount) {
/* 782 */       return EntityTickingSystem.maybeUseParallel(archetypeChunkSize, taskCount);
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 787 */       EntityTrackerSystems.EntityViewer viewer = (EntityTrackerSystems.EntityViewer)archetypeChunk.getComponent(index, this.componentType);
/*     */       
/* 789 */       IntList removedEntities = INT_LIST_THREAD_LOCAL.get();
/* 790 */       removedEntities.clear();
/*     */       
/* 792 */       int before = viewer.updates.size();
/* 793 */       viewer.updates.entrySet().removeIf(v -> !((Ref)v.getKey()).isValid());
/* 794 */       if (before != viewer.updates.size()) {
/* 795 */         ((HytaleLogger.Api)LOGGER.atWarning()).log("Removed %d invalid updates for removed entities.", before - viewer.updates.size());
/*     */       }
/*     */       
/* 798 */       for (ObjectIterator<Object2IntMap.Entry<Ref<EntityStore>>> iterator = viewer.sent.object2IntEntrySet().iterator(); iterator.hasNext(); ) {
/* 799 */         Object2IntMap.Entry<Ref<EntityStore>> entry = (Object2IntMap.Entry<Ref<EntityStore>>)iterator.next();
/* 800 */         Ref<EntityStore> ref = (Ref<EntityStore>)entry.getKey();
/*     */ 
/*     */         
/* 803 */         if (!ref.isValid() || !viewer.visible.contains(ref)) {
/* 804 */           removedEntities.add(entry.getIntValue());
/* 805 */           iterator.remove();
/*     */           
/* 807 */           if (viewer.updates.remove(ref) != null) {
/* 808 */             ((HytaleLogger.Api)LOGGER.atSevere()).log("Entity can't be removed and also receive an update! " + String.valueOf(ref));
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 813 */       if (!removedEntities.isEmpty() || !viewer.updates.isEmpty()) {
/*     */         
/* 815 */         for (Iterator<Ref<EntityStore>> iterator1 = viewer.updates.keySet().iterator(); iterator1.hasNext(); ) {
/* 816 */           Ref<EntityStore> ref = iterator1.next();
/* 817 */           if (!ref.isValid() || ref.getStore() != store) {
/* 818 */             iterator1.remove();
/*     */             continue;
/*     */           } 
/* 821 */           if (!viewer.sent.containsKey(ref)) {
/* 822 */             int networkId = ((NetworkId)commandBuffer.getComponent(ref, NetworkId.getComponentType())).getId();
/* 823 */             if (networkId == -1) throw new IllegalArgumentException("Invalid entity network id: " + String.valueOf(ref)); 
/* 824 */             viewer.sent.put(ref, networkId);
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 829 */         EntityUpdates packet = new EntityUpdates();
/* 830 */         packet.removed = !removedEntities.isEmpty() ? removedEntities.toIntArray() : null;
/* 831 */         packet.updates = new com.hypixel.hytale.protocol.EntityUpdate[viewer.updates.size()];
/*     */         
/* 833 */         int i = 0;
/* 834 */         for (Map.Entry<Ref<EntityStore>, EntityTrackerSystems.EntityUpdate> entry : viewer.updates.entrySet()) {
/* 835 */           com.hypixel.hytale.protocol.EntityUpdate entityUpdate = packet.updates[i++] = new com.hypixel.hytale.protocol.EntityUpdate();
/*     */           
/* 837 */           entityUpdate.networkId = viewer.sent.getInt(entry.getKey());
/*     */           
/* 839 */           EntityTrackerSystems.EntityUpdate update = entry.getValue();
/* 840 */           entityUpdate.removed = update.toRemovedArray();
/* 841 */           entityUpdate.updates = update.toUpdatesArray();
/*     */         } 
/* 843 */         viewer.updates.clear();
/*     */         
/* 845 */         viewer.packetReceiver.writeNoCache((Packet)packet);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\tracker\EntityTrackerSystems.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */