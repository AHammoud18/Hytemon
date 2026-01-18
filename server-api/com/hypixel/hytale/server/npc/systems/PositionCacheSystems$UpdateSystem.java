/*     */ package com.hypixel.hytale.server.npc.systems;
/*     */ 
/*     */ import com.hypixel.hytale.common.collection.BucketItemPool;
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.ResourceType;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.dependency.Dependency;
/*     */ import com.hypixel.hytale.component.dependency.Order;
/*     */ import com.hypixel.hytale.component.dependency.OrderPriority;
/*     */ import com.hypixel.hytale.component.dependency.SystemDependency;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.spatial.SpatialResource;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.protocol.BlockMaterial;
/*     */ import com.hypixel.hytale.server.core.entity.LivingEntity;
/*     */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.system.PlayerSpatialSystem;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import com.hypixel.hytale.server.npc.role.Role;
/*     */ import com.hypixel.hytale.server.npc.role.support.EntityList;
/*     */ import com.hypixel.hytale.server.npc.role.support.PositionCache;
/*     */ import com.hypixel.hytale.server.spawning.SpawningPlugin;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectList;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
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
/*     */ public class UpdateSystem
/*     */   extends SteppableTickingSystem
/*     */ {
/*     */   @Nonnull
/* 196 */   private static final ThreadLocal<BucketItemPool<Ref<EntityStore>>> BUCKET_POOL_THREAD_LOCAL = ThreadLocal.withInitial(BucketItemPool::new);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, NPCEntity> npcComponentType;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, ModelComponent> modelComponentType;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, TransformComponent> transformComponentType;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final ResourceType<EntityStore, SpatialResource<Ref<EntityStore>, EntityStore>> playerSpatialResource;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final ResourceType<EntityStore, SpatialResource<Ref<EntityStore>, EntityStore>> npcSpatialResource;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final ResourceType<EntityStore, SpatialResource<Ref<EntityStore>, EntityStore>> itemSpatialResource;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final Query<EntityStore> query;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 244 */   private final Set<Dependency<EntityStore>> dependencies = (Set)Set.of(new SystemDependency(Order.AFTER, PlayerSpatialSystem.class, OrderPriority.CLOSEST), new SystemDependency(Order.BEFORE, RoleSystems.PreBehaviourSupportTickSystem.class));
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
/*     */   public UpdateSystem(@Nonnull ComponentType<EntityStore, NPCEntity> npcComponentType, @Nonnull ResourceType<EntityStore, SpatialResource<Ref<EntityStore>, EntityStore>> npcSpatialResource) {
/* 256 */     this.npcComponentType = npcComponentType;
/* 257 */     this.modelComponentType = ModelComponent.getComponentType();
/* 258 */     this.transformComponentType = TransformComponent.getComponentType();
/* 259 */     this.playerSpatialResource = EntityModule.get().getPlayerSpatialResourceType();
/* 260 */     this.npcSpatialResource = npcSpatialResource;
/* 261 */     this.itemSpatialResource = EntityModule.get().getItemSpatialResourceType();
/* 262 */     this.query = (Query<EntityStore>)Query.and(new Query[] { (Query)npcComponentType, (Query)this.transformComponentType, (Query)this.modelComponentType });
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Set<Dependency<EntityStore>> getDependencies() {
/* 268 */     return this.dependencies;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isParallel(int archetypeChunkSize, int taskCount) {
/* 275 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Query<EntityStore> getQuery() {
/* 281 */     return this.query;
/*     */   }
/*     */ 
/*     */   
/*     */   public void steppedTick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 286 */     Ref<EntityStore> ref = archetypeChunk.getReferenceTo(index);
/*     */     
/* 288 */     NPCEntity npcComponent = (NPCEntity)archetypeChunk.getComponent(index, this.npcComponentType);
/* 289 */     assert npcComponent != null;
/*     */     
/* 291 */     Role role = npcComponent.getRole();
/* 292 */     PositionCache positionCache = role.getPositionCache();
/* 293 */     positionCache.setBenchmarking(NPCPlugin.get().isBenchmarkingSensorSupport());
/*     */     
/* 295 */     long packed = LivingEntity.getPackedMaterialAndFluidAtBreathingHeight(ref, (ComponentAccessor)commandBuffer);
/* 296 */     BlockMaterial material = BlockMaterial.VALUES[MathUtil.unpackLeft(packed)];
/* 297 */     int fluidId = MathUtil.unpackRight(packed);
/*     */     
/* 299 */     positionCache.setCouldBreathe(role.canBreathe(material, fluidId));
/*     */     
/* 301 */     if (!positionCache.tickPositionCacheNextUpdate(dt))
/* 302 */       return;  positionCache.resetPositionCacheNextUpdate();
/*     */     
/* 304 */     TransformComponent transformComponent = (TransformComponent)archetypeChunk.getComponent(index, this.transformComponentType);
/* 305 */     assert transformComponent != null;
/*     */     
/* 307 */     Vector3d position = transformComponent.getPosition();
/*     */     
/* 309 */     EntityList players = positionCache.getPlayers();
/* 310 */     if (players.getSearchRadius() > 0) {
/*     */       
/* 312 */       SpatialResource<Ref<EntityStore>, EntityStore> spatialResource = (SpatialResource<Ref<EntityStore>, EntityStore>)store.getResource(this.playerSpatialResource);
/* 313 */       players.setBucketItemPool(BUCKET_POOL_THREAD_LOCAL.get());
/* 314 */       if (positionCache.isBenchmarking()) {
/*     */         
/* 316 */         long startTime = System.nanoTime();
/* 317 */         addEntities(ref, position, players, spatialResource, commandBuffer);
/* 318 */         long getTime = System.nanoTime();
/* 319 */         NPCPlugin.get().collectSensorSupportPlayerList(role.getRoleIndex(), getTime - startTime, players.getMaxDistanceSorted(), players
/* 320 */             .getMaxDistanceUnsorted(), players.getMaxDistanceAvoidance(), 0);
/*     */       } else {
/* 322 */         addEntities(ref, position, players, spatialResource, commandBuffer);
/*     */       } 
/*     */     } 
/*     */     
/* 326 */     EntityList npcEntities = positionCache.getNpcs();
/* 327 */     if (npcEntities.getSearchRadius() > 0) {
/*     */       
/* 329 */       SpatialResource<Ref<EntityStore>, EntityStore> spatialResource = (SpatialResource<Ref<EntityStore>, EntityStore>)store.getResource(this.npcSpatialResource);
/* 330 */       npcEntities.setBucketItemPool(BUCKET_POOL_THREAD_LOCAL.get());
/* 331 */       if (positionCache.isBenchmarking()) {
/*     */         
/* 333 */         long startTime = System.nanoTime();
/* 334 */         addEntities(ref, position, npcEntities, spatialResource, commandBuffer);
/* 335 */         long getTime = System.nanoTime();
/* 336 */         NPCPlugin.get().collectSensorSupportEntityList(role.getRoleIndex(), getTime - startTime, npcEntities.getMaxDistanceSorted(), npcEntities
/* 337 */             .getMaxDistanceUnsorted(), npcEntities.getMaxDistanceAvoidance(), 0);
/*     */       } else {
/* 339 */         addEntities(ref, position, npcEntities, spatialResource, commandBuffer);
/*     */       } 
/*     */     } 
/*     */     
/* 343 */     double maxDroppedItemDistance = positionCache.getMaxDroppedItemDistance();
/* 344 */     if (maxDroppedItemDistance > 0.0D) {
/* 345 */       SpatialResource<Ref<EntityStore>, EntityStore> spatialResource = (SpatialResource<Ref<EntityStore>, EntityStore>)store.getResource(this.itemSpatialResource);
/* 346 */       List<Ref<EntityStore>> list = positionCache.getDroppedItemList();
/* 347 */       list.clear();
/*     */       
/* 349 */       spatialResource.getSpatialStructure().ordered(position, ((int)maxDroppedItemDistance + 1), list);
/*     */     } 
/*     */     
/* 352 */     double maxSpawnMarkerDistance = positionCache.getMaxSpawnMarkerDistance();
/* 353 */     if (maxSpawnMarkerDistance > 0.0D) {
/* 354 */       SpatialResource<Ref<EntityStore>, EntityStore> spatialResource = (SpatialResource<Ref<EntityStore>, EntityStore>)store.getResource(SpawningPlugin.get().getSpawnMarkerSpatialResource());
/* 355 */       List<Ref<EntityStore>> list = positionCache.getSpawnMarkerList();
/* 356 */       list.clear();
/*     */       
/* 358 */       spatialResource.getSpatialStructure().collect(position, ((int)maxSpawnMarkerDistance + 1), list);
/*     */     } 
/*     */     
/* 361 */     int maxSpawnBeaconDistance = positionCache.getMaxSpawnBeaconDistance();
/* 362 */     if (maxSpawnBeaconDistance > 0) {
/* 363 */       SpatialResource<Ref<EntityStore>, EntityStore> spatialResource = (SpatialResource<Ref<EntityStore>, EntityStore>)store.getResource(SpawningPlugin.get().getManualSpawnBeaconSpatialResource());
/* 364 */       List<Ref<EntityStore>> list = positionCache.getSpawnBeaconList();
/* 365 */       list.clear();
/*     */       
/* 367 */       spatialResource.getSpatialStructure().ordered(position, (maxSpawnBeaconDistance + 1), list);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void addEntities(@Nonnull Ref<EntityStore> self, @Nonnull Vector3d position, @Nonnull EntityList entityList, @Nonnull SpatialResource<Ref<EntityStore>, EntityStore> spatialResource, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 376 */     ObjectList objectList = SpatialResource.getThreadLocalReferenceList();
/*     */ 
/*     */     
/* 379 */     spatialResource.getSpatialStructure().collect(position, entityList.getSearchRadius(), (List)objectList);
/* 380 */     for (Ref<EntityStore> result : (Iterable<Ref<EntityStore>>)objectList) {
/* 381 */       if (!result.isValid() || result.equals(self)) {
/*     */         continue;
/*     */       }
/* 384 */       entityList.add(result, position, commandBuffer);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\systems\PositionCacheSystems$UpdateSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */