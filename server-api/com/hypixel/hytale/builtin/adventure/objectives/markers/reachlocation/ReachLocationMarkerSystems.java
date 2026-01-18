/*     */ package com.hypixel.hytale.builtin.adventure.objectives.markers.reachlocation;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.Objective;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.ObjectiveDataStore;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.ObjectivePlugin;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.task.ObjectiveTaskRef;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.task.ReachLocationTask;
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.ResourceType;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.spatial.SpatialResource;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.tracker.NetworkId;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectList;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class ReachLocationMarkerSystems {
/*  29 */   private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */   
/*  31 */   private static final ThreadLocal<Set<UUID>> THREAD_LOCAL_TEMP_UUIDS = ThreadLocal.withInitial(java.util.HashSet::new);
/*     */   
/*     */   public static class EntityAdded extends RefSystem<EntityStore> {
/*     */     private final ComponentType<EntityStore, ReachLocationMarker> reachLocationMarkerComponent;
/*     */     private final ComponentType<EntityStore, TransformComponent> transformComponentType;
/*     */     @Nonnull
/*     */     private final Query<EntityStore> query;
/*     */     
/*     */     public EntityAdded(ComponentType<EntityStore, ReachLocationMarker> reachLocationMarkerComponent) {
/*  40 */       this.reachLocationMarkerComponent = reachLocationMarkerComponent;
/*  41 */       this.transformComponentType = TransformComponent.getComponentType();
/*  42 */       this.query = (Query<EntityStore>)Query.and(new Query[] { (Query)reachLocationMarkerComponent, (Query)this.transformComponentType });
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Query<EntityStore> getQuery() {
/*  48 */       return this.query;
/*     */     }
/*     */ 
/*     */     
/*     */     public void onEntityAdded(@Nonnull Ref<EntityStore> ref, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/*  53 */       ReachLocationMarker reachLocationMarkerComponent = (ReachLocationMarker)commandBuffer.getComponent(ref, this.reachLocationMarkerComponent);
/*  54 */       assert reachLocationMarkerComponent != null;
/*     */       
/*  56 */       TransformComponent transformComponent = (TransformComponent)commandBuffer.getComponent(ref, this.transformComponentType);
/*  57 */       assert transformComponent != null;
/*     */       
/*  59 */       Vector3d pos = transformComponent.getPosition();
/*  60 */       ObjectiveDataStore objectiveDataStore = ObjectivePlugin.get().getObjectiveDataStore();
/*     */       
/*  62 */       Set<ObjectiveTaskRef<ReachLocationTask>> taskRefs = objectiveDataStore.getTaskRefsForType(ReachLocationTask.class);
/*  63 */       for (ObjectiveTaskRef<ReachLocationTask> taskRef : taskRefs) {
/*  64 */         Objective objective = objectiveDataStore.getObjective(taskRef.getObjectiveUUID());
/*  65 */         if (objective == null)
/*     */           continue; 
/*  67 */         ((ReachLocationTask)taskRef.getObjectiveTask()).setupMarker(objective, reachLocationMarkerComponent, pos, commandBuffer);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void onEntityRemove(@Nonnull Ref<EntityStore> ref, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {}
/*     */   }
/*     */   
/*     */   public static class EnsureNetworkSendable
/*     */     extends HolderSystem<EntityStore>
/*     */   {
/*  77 */     private final Query<EntityStore> query = (Query<EntityStore>)Query.and(new Query[] { (Query)ReachLocationMarker.getComponentType(), (Query)Query.not((Query)NetworkId.getComponentType()) });
/*     */ 
/*     */     
/*     */     public void onEntityAdd(@Nonnull Holder<EntityStore> holder, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store) {
/*  81 */       holder.addComponent(NetworkId.getComponentType(), (Component)new NetworkId(((EntityStore)store.getExternalData()).takeNextNetworkId()));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void onEntityRemoved(@Nonnull Holder<EntityStore> holder, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store) {}
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Query<EntityStore> getQuery() {
/*  91 */       return this.query;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Ticking extends EntityTickingSystem<EntityStore> {
/*     */     private final ComponentType<EntityStore, ReachLocationMarker> reachLocationMarkerComponent;
/*     */     private final ComponentType<EntityStore, TransformComponent> transformComponentType;
/*     */     private final ResourceType<EntityStore, SpatialResource<Ref<EntityStore>, EntityStore>> playerSpatialComponent;
/*  99 */     private final ComponentType<EntityStore, UUIDComponent> uuidComponentType = UUIDComponent.getComponentType();
/*     */     
/*     */     @Nonnull
/*     */     private final Query<EntityStore> query;
/*     */     @Nonnull
/*     */     private final Set<Dependency<EntityStore>> dependencies;
/*     */     
/*     */     public Ticking(ComponentType<EntityStore, ReachLocationMarker> reachLocationMarkerComponent, ResourceType<EntityStore, SpatialResource<Ref<EntityStore>, EntityStore>> playerSpatialComponent) {
/* 107 */       this.reachLocationMarkerComponent = reachLocationMarkerComponent;
/* 108 */       this.transformComponentType = TransformComponent.getComponentType();
/*     */ 
/*     */       
/* 111 */       this.playerSpatialComponent = playerSpatialComponent;
/* 112 */       this.query = (Query<EntityStore>)Query.and(new Query[] { (Query)reachLocationMarkerComponent, (Query)this.transformComponentType });
/* 113 */       this.dependencies = Set.of(new SystemDependency(Order.AFTER, PlayerSpatialSystem.class, OrderPriority.CLOSEST));
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Set<Dependency<EntityStore>> getDependencies() {
/* 119 */       return this.dependencies;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Query<EntityStore> getQuery() {
/* 125 */       return this.query;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isParallel(int archetypeChunkSize, int taskCount) {
/* 130 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 135 */       ReachLocationMarker reachLocationMarkerComponent = (ReachLocationMarker)archetypeChunk.getComponent(index, this.reachLocationMarkerComponent);
/* 136 */       assert reachLocationMarkerComponent != null;
/*     */       
/* 138 */       String markerId = reachLocationMarkerComponent.getMarkerId();
/* 139 */       ReachLocationMarkerAsset asset = (ReachLocationMarkerAsset)ReachLocationMarkerAsset.getAssetMap().getAsset(markerId);
/* 140 */       if (asset == null) {
/* 141 */         ReachLocationMarkerSystems.LOGGER.at(Level.WARNING).log("No ReachLocationMarkerAsset found for ID '%s', entity removed! %s", markerId, reachLocationMarkerComponent);
/* 142 */         commandBuffer.removeEntity(archetypeChunk.getReferenceTo(index), RemoveReason.REMOVE);
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 147 */       Set<UUID> previousPlayers = ReachLocationMarkerSystems.THREAD_LOCAL_TEMP_UUIDS.get();
/* 148 */       previousPlayers.clear();
/* 149 */       previousPlayers.addAll(reachLocationMarkerComponent.getPlayers());
/*     */ 
/*     */       
/* 152 */       Set<UUID> players = reachLocationMarkerComponent.getPlayers();
/* 153 */       players.clear();
/*     */       
/* 155 */       SpatialResource<Ref<EntityStore>, EntityStore> spatialResource = (SpatialResource<Ref<EntityStore>, EntityStore>)store.getResource(this.playerSpatialComponent);
/* 156 */       ObjectList<Ref<EntityStore>> results = SpatialResource.getThreadLocalReferenceList();
/*     */       
/* 158 */       TransformComponent transformComponent = (TransformComponent)archetypeChunk.getComponent(index, this.transformComponentType);
/* 159 */       assert transformComponent != null;
/*     */       
/* 161 */       Vector3d position = transformComponent.getPosition();
/* 162 */       spatialResource.getSpatialStructure().ordered(position, asset.getRadius(), (List)results);
/*     */       
/* 164 */       ObjectiveDataStore objectiveDataStore = ObjectivePlugin.get().getObjectiveDataStore();
/*     */       
/* 166 */       for (int i = 0; i < results.size(); i++) {
/* 167 */         Ref<EntityStore> otherEntityReference = (Ref<EntityStore>)results.get(i);
/* 168 */         UUIDComponent otherUuidComponent = (UUIDComponent)commandBuffer.getComponent(otherEntityReference, this.uuidComponentType);
/* 169 */         assert otherUuidComponent != null;
/*     */         
/* 171 */         UUID otherUuid = otherUuidComponent.getUuid();
/* 172 */         players.add(otherUuid);
/*     */ 
/*     */         
/* 175 */         if (!previousPlayers.contains(otherUuid)) {
/*     */           
/* 177 */           Set<ObjectiveTaskRef<ReachLocationTask>> taskRefs = objectiveDataStore.getTaskRefsForType(ReachLocationTask.class);
/* 178 */           for (ObjectiveTaskRef<ReachLocationTask> taskRef : taskRefs) {
/* 179 */             Objective objective = objectiveDataStore.getObjective(taskRef.getObjectiveUUID());
/* 180 */             if (objective == null)
/*     */               continue; 
/* 182 */             ((ReachLocationTask)taskRef.getObjectiveTask()).onPlayerReachLocationMarker(store, otherEntityReference, markerId, objective);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\markers\reachlocation\ReachLocationMarkerSystems.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */