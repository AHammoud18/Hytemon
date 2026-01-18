/*     */ package com.hypixel.hytale.server.core.modules.entity.system;
/*     */ 
/*     */ import com.hypixel.hytale.component.AddReason;
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
/*     */ import com.hypixel.hytale.component.system.HolderSystem;
/*     */ import com.hypixel.hytale.component.system.RefSystem;
/*     */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*     */ import com.hypixel.hytale.protocol.ColorLight;
/*     */ import com.hypixel.hytale.protocol.ComponentUpdate;
/*     */ import com.hypixel.hytale.protocol.ComponentUpdateType;
/*     */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.DynamicLight;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.FromPrefab;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.FromWorldGen;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.NewSpawnComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.tracker.EntityTrackerSystems;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.EntityChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
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
/*     */ public class EntitySystems
/*     */ {
/*     */   public static abstract class ClearMarker<T extends Component<EntityStore>>
/*     */     extends RefSystem<EntityStore>
/*     */   {
/*     */     @Nonnull
/*     */     private final ComponentType<EntityStore, T> componentType;
/*     */     @Nonnull
/*     */     private final Set<Dependency<EntityStore>> dependencies;
/*     */     
/*     */     public ClearMarker(@Nonnull ComponentType<EntityStore, T> componentType, @Nonnull SystemGroup<EntityStore> preGroup) {
/*  62 */       this.componentType = componentType;
/*  63 */       this.dependencies = Set.of(new SystemGroupDependency(Order.AFTER, preGroup));
/*     */     }
/*     */ 
/*     */     
/*     */     public void onEntityAdded(@Nonnull Ref<EntityStore> ref, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/*  68 */       commandBuffer.removeComponent(ref, this.componentType);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void onEntityRemove(@Nonnull Ref<EntityStore> ref, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {}
/*     */ 
/*     */ 
/*     */     
/*     */     public Query<EntityStore> getQuery() {
/*  78 */       return (Query)this.componentType;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Set<Dependency<EntityStore>> getDependencies() {
/*  84 */       return this.dependencies;
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
/*     */   
/*     */   public static class ClearFromPrefabMarker
/*     */     extends ClearMarker<FromPrefab>
/*     */   {
/*     */     public ClearFromPrefabMarker(@Nonnull ComponentType<EntityStore, FromPrefab> componentType, @Nonnull SystemGroup<EntityStore> preGroup) {
/* 101 */       super(componentType, preGroup);
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
/*     */   
/*     */   public static class ClearFromWorldGenMarker
/*     */     extends ClearMarker<FromWorldGen>
/*     */   {
/*     */     public ClearFromWorldGenMarker(@Nonnull ComponentType<EntityStore, FromWorldGen> componentType, @Nonnull SystemGroup<EntityStore> preGroup) {
/* 118 */       super(componentType, preGroup);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class OnLoadFromExternal
/*     */     extends HolderSystem<EntityStore>
/*     */   {
/*     */     @Nonnull
/*     */     private final Query<EntityStore> query;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     private final SystemGroup<EntityStore> group;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     private final Set<Dependency<EntityStore>> dependencies;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public OnLoadFromExternal(@Nonnull ComponentType<EntityStore, FromPrefab> fromPrefab, @Nonnull ComponentType<EntityStore, FromWorldGen> fromWorldGen, @Nonnull SystemGroup<EntityStore> group) {
/* 155 */       this.query = (Query<EntityStore>)Query.and(new Query[] { (Query)Query.or(new Query[] { (Query)fromPrefab, (Query)fromWorldGen }), (Query)UUIDComponent.getComponentType() });
/* 156 */       this.group = group;
/*     */       
/* 158 */       this.dependencies = Set.of(new SystemDependency(Order.BEFORE, EntityStore.UUIDSystem.class), new SystemDependency(Order.AFTER, EntityModule.LegacyUUIDSystem.class));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void onEntityAdd(@Nonnull Holder<EntityStore> holder, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store) {
/* 167 */       holder.putComponent(UUIDComponent.getComponentType(), (Component)UUIDComponent.generateVersion3UUID());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void onEntityRemoved(@Nonnull Holder<EntityStore> holder, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store) {}
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Set<Dependency<EntityStore>> getDependencies() {
/* 178 */       return this.dependencies;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Query<EntityStore> getQuery() {
/* 184 */       return this.query;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public SystemGroup<EntityStore> getGroup() {
/* 190 */       return this.group;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class UnloadEntityFromChunk
/*     */     extends RefSystem<EntityStore>
/*     */   {
/*     */     public Query<EntityStore> getQuery() {
/* 201 */       return (Query<EntityStore>)TransformComponent.getComponentType();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void onEntityAdded(@Nonnull Ref<EntityStore> ref, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {}
/*     */ 
/*     */ 
/*     */     
/*     */     public void onEntityRemove(@Nonnull Ref<EntityStore> ref, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 211 */       TransformComponent transformComponent = (TransformComponent)store.getComponent(ref, TransformComponent.getComponentType());
/* 212 */       assert transformComponent != null;
/*     */       
/* 214 */       Ref<ChunkStore> chunkRef = transformComponent.getChunkRef();
/* 215 */       if (chunkRef == null || !chunkRef.isValid())
/*     */         return; 
/* 217 */       World world = ((EntityStore)commandBuffer.getExternalData()).getWorld();
/* 218 */       ChunkStore chunkStore = world.getChunkStore();
/* 219 */       Store<ChunkStore> chunkComponentStore = chunkStore.getStore();
/*     */       
/* 221 */       EntityChunk entityChunkComponent = (EntityChunk)chunkComponentStore.getComponent(chunkRef, EntityChunk.getComponentType());
/* 222 */       assert entityChunkComponent != null;
/*     */       
/* 224 */       switch (reason) { case REMOVE:
/* 225 */           entityChunkComponent.removeEntityReference(ref); break;
/* 226 */         case UNLOAD: entityChunkComponent.unloadEntityReference(ref);
/*     */           break; }
/*     */     
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class DynamicLightTracker
/*     */     extends EntityTickingSystem<EntityStore>
/*     */   {
/*     */     @Nonnull
/*     */     private final ComponentType<EntityStore, EntityTrackerSystems.Visible> componentType;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     private final ComponentType<EntityStore, DynamicLight> dynamicLightType;
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
/*     */     public DynamicLightTracker(@Nonnull ComponentType<EntityStore, EntityTrackerSystems.Visible> componentType) {
/* 260 */       this.componentType = componentType;
/* 261 */       this.dynamicLightType = DynamicLight.getComponentType();
/* 262 */       this.query = (Query<EntityStore>)Query.and(new Query[] { (Query)componentType, (Query)this.dynamicLightType });
/*     */     }
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public SystemGroup<EntityStore> getGroup() {
/* 268 */       return EntityTrackerSystems.QUEUE_UPDATE_GROUP;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Query<EntityStore> getQuery() {
/* 274 */       return this.query;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isParallel(int archetypeChunkSize, int taskCount) {
/* 279 */       return EntityTickingSystem.maybeUseParallel(archetypeChunkSize, taskCount);
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 284 */       EntityTrackerSystems.Visible visibleComponent = (EntityTrackerSystems.Visible)archetypeChunk.getComponent(index, this.componentType);
/* 285 */       assert visibleComponent != null;
/*     */       
/* 287 */       DynamicLight dynamicLightComponent = (DynamicLight)archetypeChunk.getComponent(index, this.dynamicLightType);
/* 288 */       assert dynamicLightComponent != null;
/*     */       
/* 290 */       ColorLight dynamicLight = dynamicLightComponent.getColorLight();
/*     */ 
/*     */       
/* 293 */       if (dynamicLightComponent.consumeNetworkOutdated()) {
/* 294 */         if (dynamicLight != null) {
/* 295 */           queueUpdatesFor(archetypeChunk.getReferenceTo(index), dynamicLight, visibleComponent.visibleTo);
/*     */         } else {
/* 297 */           queueRemoveFor(archetypeChunk.getReferenceTo(index), visibleComponent.visibleTo);
/*     */         } 
/* 299 */       } else if (!visibleComponent.newlyVisibleTo.isEmpty() && dynamicLight != null) {
/* 300 */         queueUpdatesFor(archetypeChunk.getReferenceTo(index), dynamicLight, visibleComponent.newlyVisibleTo);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private static void queueUpdatesFor(@Nonnull Ref<EntityStore> ref, @Nonnull ColorLight dynamicLight, @Nonnull Map<Ref<EntityStore>, EntityTrackerSystems.EntityViewer> visibleTo) {
/* 307 */       ComponentUpdate update = new ComponentUpdate();
/* 308 */       update.type = ComponentUpdateType.DynamicLight;
/* 309 */       update.dynamicLight = dynamicLight;
/*     */       
/* 311 */       for (EntityTrackerSystems.EntityViewer viewer : visibleTo.values()) {
/* 312 */         viewer.queueUpdate(ref, update);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     private static void queueRemoveFor(@Nonnull Ref<EntityStore> ref, @Nonnull Map<Ref<EntityStore>, EntityTrackerSystems.EntityViewer> visibleTo) {
/* 318 */       for (EntityTrackerSystems.EntityViewer viewer : visibleTo.values()) {
/* 319 */         viewer.queueRemove(ref, ComponentUpdateType.DynamicLight);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class NewSpawnEntityTrackerUpdate
/*     */     extends EntityTickingSystem<EntityStore>
/*     */   {
/*     */     @Nonnull
/* 333 */     private final ComponentType<EntityStore, EntityTrackerSystems.Visible> visibleComponentType = EntityTrackerSystems.Visible.getComponentType();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/* 339 */     private final ComponentType<EntityStore, NewSpawnComponent> newSpawnComponentType = NewSpawnComponent.getComponentType();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/* 345 */     private final Query<EntityStore> query = (Query<EntityStore>)Query.and(new Query[] { (Query)this.visibleComponentType, (Query)this.newSpawnComponentType });
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public SystemGroup<EntityStore> getGroup() {
/* 350 */       return EntityTrackerSystems.QUEUE_UPDATE_GROUP;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Query<EntityStore> getQuery() {
/* 356 */       return this.query;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isParallel(int archetypeChunkSize, int taskCount) {
/* 361 */       return EntityTickingSystem.maybeUseParallel(archetypeChunkSize, taskCount);
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 366 */       EntityTrackerSystems.Visible visibleComponent = (EntityTrackerSystems.Visible)archetypeChunk.getComponent(index, this.visibleComponentType);
/* 367 */       assert visibleComponent != null;
/*     */       
/* 369 */       NewSpawnComponent newSpawnComponent = (NewSpawnComponent)archetypeChunk.getComponent(index, this.newSpawnComponentType);
/* 370 */       assert newSpawnComponent != null;
/*     */       
/* 372 */       Ref<EntityStore> ref = archetypeChunk.getReferenceTo(index);
/*     */       
/* 374 */       if (!visibleComponent.newlyVisibleTo.isEmpty()) {
/* 375 */         ComponentUpdate update = new ComponentUpdate();
/* 376 */         update.type = ComponentUpdateType.NewSpawn;
/* 377 */         for (Map.Entry<Ref<EntityStore>, EntityTrackerSystems.EntityViewer> entry : (Iterable<Map.Entry<Ref<EntityStore>, EntityTrackerSystems.EntityViewer>>)visibleComponent.newlyVisibleTo.entrySet()) {
/* 378 */           ((EntityTrackerSystems.EntityViewer)entry.getValue()).queueUpdate(ref, update);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class NewSpawnTick
/*     */     extends EntityTickingSystem<EntityStore>
/*     */   {
/*     */     @Nonnull
/* 393 */     private final ComponentType<EntityStore, NewSpawnComponent> newSpawnComponentType = NewSpawnComponent.getComponentType();
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Query<EntityStore> getQuery() {
/* 398 */       return (Query)this.newSpawnComponentType;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isParallel(int archetypeChunkSize, int taskCount) {
/* 403 */       return EntityTickingSystem.maybeUseParallel(archetypeChunkSize, taskCount);
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 408 */       NewSpawnComponent newSpawnComponent = (NewSpawnComponent)archetypeChunk.getComponent(index, this.newSpawnComponentType);
/* 409 */       assert newSpawnComponent != null;
/*     */       
/* 411 */       if (newSpawnComponent.newSpawnWindowPassed(dt))
/* 412 */         commandBuffer.removeComponent(archetypeChunk.getReferenceTo(index), this.newSpawnComponentType); 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\system\EntitySystems.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */