/*     */ package com.hypixel.hytale.server.npc.role.support;
/*     */ import com.hypixel.hytale.assetstore.map.BlockTypeAssetMap;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.function.consumer.DoubleQuadObjectConsumer;
/*     */ import com.hypixel.hytale.function.consumer.QuadConsumer;
/*     */ import com.hypixel.hytale.function.consumer.TriConsumer;
/*     */ import com.hypixel.hytale.function.predicate.QuadPredicate;
/*     */ import com.hypixel.hytale.math.iterator.BlockIterator;
/*     */ import com.hypixel.hytale.math.random.RandomExtra;
/*     */ import com.hypixel.hytale.math.shape.Box;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector2d;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.protocol.GameMode;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.modules.blockset.BlockSetModule;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.BoundingBox;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.item.ItemComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.player.PlayerSettings;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import com.hypixel.hytale.server.npc.role.Role;
/*     */ import com.hypixel.hytale.server.npc.util.NPCPhysicsMath;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.ints.IntSet;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ByteMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ByteOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.function.BiPredicate;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Predicate;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class PositionCache {
/*     */   public static final BiPredicate<Ref<EntityStore>, ComponentAccessor<EntityStore>> IS_VALID_PLAYER;
/*     */   public static final BiPredicate<Ref<EntityStore>, ComponentAccessor<EntityStore>> IS_VALID_NPC;
/*     */   public static final double MIN_LOS_BLOCKING_DISTANCE_SQUARED = 1.0E-6D;
/*     */   public static final String FUNCTION_CAN_BE_ONLY_CALLED_WHILE_CONFIGURING_POSITION_CACHE = "function can be only called while configuring PositionCache";
/*     */   private static final float LOS_CACHE_TTL_MIN_SECONDS = 0.09F;
/*     */   private static final float LOS_CACHE_TTL_MAX_SECONDS = 0.11F;
/*     */   private static final float POSITION_CACHE_TTL_SECONDS = 0.2F;
/*     */   
/*     */   static {
/*  56 */     IS_VALID_PLAYER = ((ref, componentAccessor) -> {
/*     */         Player playerComponent = (Player)componentAccessor.getComponent(ref, Player.getComponentType());
/*     */         
/*     */         if (playerComponent == null || playerComponent.isWaitingForClientReady()) {
/*     */           return false;
/*     */         }
/*     */         
/*     */         if (playerComponent.getGameMode() == GameMode.Adventure) {
/*     */           return true;
/*     */         }
/*     */         if (playerComponent.getGameMode() == GameMode.Creative) {
/*     */           PlayerSettings playerSettingsComponent = (PlayerSettings)componentAccessor.getComponent(ref, PlayerSettings.getComponentType());
/*  68 */           return (playerSettingsComponent != null && playerSettingsComponent.creativeSettings().allowNPCDetection());
/*     */         } 
/*     */         return false;
/*     */       });
/*  72 */     IS_VALID_NPC = ((ref, accessor) -> accessor.getArchetype(ref).contains(NPCEntity.getComponentType()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  81 */   private static final ComponentType<EntityStore, TransformComponent> TRANSFORM_COMPONENT_TYPE = TransformComponent.getComponentType();
/*  82 */   private static final ComponentType<EntityStore, ItemComponent> ITEM_COMPONENT_TYPE = ItemComponent.getComponentType();
/*  83 */   private static final ComponentType<EntityStore, ModelComponent> MODEL_COMPONENT_TYPE = ModelComponent.getComponentType();
/*  84 */   protected static final ComponentType<EntityStore, BoundingBox> BOUNDING_BOX_COMPONENT_TYPE = BoundingBox.getComponentType();
/*     */   
/*     */   private double maxDroppedItemDistance;
/*     */   
/*     */   private double maxSpawnMarkerDistance;
/*     */   
/*     */   private int maxSpawnBeaconDistance;
/*     */   
/*     */   @Nonnull
/*     */   private final Role role;
/*     */   
/*     */   private int opaqueBlockSet;
/*     */   protected EntityList players;
/*     */   protected EntityList npcs;
/*  98 */   protected final List<Consumer<Role>> externalRegistrations = (List<Consumer<Role>>)new ObjectArrayList();
/*     */   
/* 100 */   private final List<Ref<EntityStore>> droppedItems = (List<Ref<EntityStore>>)new ObjectArrayList();
/*     */   
/* 102 */   private final List<Ref<EntityStore>> spawnMarkers = (List<Ref<EntityStore>>)new ObjectArrayList();
/* 103 */   private final List<Ref<EntityStore>> spawnBeacons = (List<Ref<EntityStore>>)new ObjectArrayList();
/*     */   
/* 105 */   private final Object2ByteMap<Ref<EntityStore>> lineOfSightCache = (Object2ByteMap<Ref<EntityStore>>)new Object2ByteOpenHashMap();
/* 106 */   private final Object2ByteMap<Ref<EntityStore>> inverseLineOfSightCache = (Object2ByteMap<Ref<EntityStore>>)new Object2ByteOpenHashMap();
/* 107 */   private final Object2ByteMap<Ref<EntityStore>> friendlyFireCache = (Object2ByteMap<Ref<EntityStore>>)new Object2ByteOpenHashMap();
/*     */   
/* 109 */   protected final LineOfSightBuffer lineOfSightComputeBuffer = new LineOfSightBuffer();
/* 110 */   protected final LineOfSightEntityBuffer lineOfSightEntityComputeBuffer = new LineOfSightEntityBuffer();
/*     */   
/* 112 */   private float cacheTTL = 0.09F;
/*     */ 
/*     */   
/*     */   private float positionCacheNextUpdate;
/*     */   
/*     */   private boolean isBenchmarking;
/*     */   
/*     */   private boolean isConfiguring;
/*     */   
/*     */   private boolean couldBreathe = true;
/*     */ 
/*     */   
/*     */   public PositionCache(@Nonnull Role role) {
/* 125 */     this.role = role;
/*     */ 
/*     */     
/* 128 */     this.players = new EntityList(null, IS_VALID_PLAYER);
/* 129 */     this.npcs = new EntityList(null, IS_VALID_NPC);
/*     */   }
/*     */   
/*     */   public boolean isBenchmarking() {
/* 133 */     return this.isBenchmarking;
/*     */   }
/*     */   
/*     */   public void setBenchmarking(boolean benchmarking) {
/* 137 */     this.isBenchmarking = benchmarking;
/*     */   }
/*     */   
/*     */   public void setCouldBreathe(boolean couldBreathe) {
/* 141 */     this.couldBreathe = couldBreathe;
/*     */   }
/*     */   
/*     */   public EntityList getPlayers() {
/* 145 */     return this.players;
/*     */   }
/*     */   
/*     */   public EntityList getNpcs() {
/* 149 */     return this.npcs;
/*     */   }
/*     */   
/*     */   public boolean tickPositionCacheNextUpdate(float dt) {
/* 153 */     return ((this.positionCacheNextUpdate -= dt) <= 0.0F);
/*     */   }
/*     */   
/*     */   public void resetPositionCacheNextUpdate() {
/* 157 */     this.positionCacheNextUpdate = 0.2F;
/*     */   }
/*     */   
/*     */   public double getMaxDroppedItemDistance() {
/* 161 */     return this.maxDroppedItemDistance;
/*     */   }
/*     */   
/*     */   public double getMaxSpawnMarkerDistance() {
/* 165 */     return this.maxSpawnMarkerDistance;
/*     */   }
/*     */   
/*     */   public int getMaxSpawnBeaconDistance() {
/* 169 */     return this.maxSpawnBeaconDistance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addExternalPositionCacheRegistration(Consumer<Role> registration) {
/* 179 */     this.externalRegistrations.add(registration);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public List<Consumer<Role>> getExternalRegistrations() {
/* 184 */     return this.externalRegistrations;
/*     */   }
/*     */   
/*     */   public void reset(boolean isConfiguring) {
/* 188 */     this.players.reset();
/* 189 */     this.npcs.reset();
/* 190 */     this.maxDroppedItemDistance = 0.0D;
/* 191 */     this.droppedItems.clear();
/* 192 */     this.spawnMarkers.clear();
/* 193 */     this.spawnBeacons.clear();
/*     */     
/* 195 */     this.positionCacheNextUpdate = RandomExtra.randomRange(0.0F, 0.2F);
/* 196 */     clearLineOfSightCache();
/* 197 */     this.isConfiguring = isConfiguring;
/*     */   }
/*     */   
/*     */   public void finalizeConfiguration() {
/* 201 */     this.isConfiguring = false;
/* 202 */     this.npcs.finalizeConfiguration();
/* 203 */     this.players.finalizeConfiguration();
/* 204 */     RoleStats roleStats = this.role.getRoleStats();
/* 205 */     if (roleStats != null) {
/* 206 */       roleStats.trackBuckets(false, this.npcs.getBucketRanges());
/* 207 */       roleStats.trackBuckets(true, this.players.getBucketRanges());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear(double tickTime) {
/* 213 */     clearLineOfSightCache(tickTime);
/* 214 */     if (this.isBenchmarking) NPCPlugin.get().collectSensorSupportTickDone(this.role.getRoleIndex()); 
/* 215 */     this.isBenchmarking = false;
/*     */   }
/*     */   
/*     */   public boolean couldBreatheCached() {
/* 219 */     return this.couldBreathe;
/*     */   }
/*     */ 
/*     */   
/*     */   public <T, U, V> void forEachPlayer(@Nonnull DoubleQuadObjectConsumer<Ref<EntityStore>, T, U, V> consumer, T t, U u, V v, double d, ComponentAccessor<EntityStore> componentAccessor) {
/* 224 */     this.players.forEachEntity(consumer, t, u, v, d, componentAccessor);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Ref<EntityStore> getClosestPlayerInRange(double minRange, double maxRange, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 229 */     return getClosestPlayerInRange(minRange, maxRange, p -> true, componentAccessor);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Ref<EntityStore> getClosestPlayerInRange(double minRange, double maxRange, @Nonnull Predicate<Ref<EntityStore>> filter, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 235 */     return this.players.getClosestEntityInRange(minRange, maxRange, filter, componentAccessor);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Ref<EntityStore> getClosestNPCInRange(double minRange, double maxRange, @Nonnull Predicate<Ref<EntityStore>> filter, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 241 */     return this.npcs.getClosestEntityInRange(minRange, maxRange, filter, componentAccessor);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <S, T> void processNPCsInRange(@Nonnull Ref<EntityStore> ref, double minRange, double maxRange, boolean useProjectedDistance, Ref<EntityStore> ignoredEntityReference, @Nonnull Role role, @Nonnull QuadPredicate<S, Ref<EntityStore>, Role, T> filter, S s, T t, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 247 */     processEntitiesInRange(ref, this.npcs, minRange, maxRange, useProjectedDistance, ignoredEntityReference, role, filter, s, t, componentAccessor);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <S, T> void processPlayersInRange(@Nonnull Ref<EntityStore> ref, double minRange, double maxRange, boolean useProjectedDistance, Ref<EntityStore> ignoredEntityReference, @Nonnull Role role, @Nonnull QuadPredicate<S, Ref<EntityStore>, Role, T> filter, S s, T t, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 253 */     processEntitiesInRange(ref, this.players, minRange, maxRange, useProjectedDistance, ignoredEntityReference, role, filter, s, t, componentAccessor);
/*     */   }
/*     */ 
/*     */   
/*     */   public <S, T> void processEntitiesInRange(@Nonnull Ref<EntityStore> ref, @Nonnull EntityList entities, double minRange, double maxRange, boolean useProjectedDistance, Ref<EntityStore> ignoredEntityReference, @Nonnull Role role, @Nonnull QuadPredicate<S, Ref<EntityStore>, Role, T> filter, S s, T t, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 258 */     if (useProjectedDistance) {
/* 259 */       entities.getClosestEntityInRangeProjected(ref, ignoredEntityReference, role.getActiveMotionController(), minRange, maxRange, filter, role, s, t, componentAccessor);
/*     */     } else {
/* 261 */       entities.getClosestEntityInRange(ignoredEntityReference, minRange, maxRange, filter, role, s, t, componentAccessor);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public <S> Ref<EntityStore> getClosestDroppedItemInRange(@Nonnull Ref<EntityStore> ref, double minRange, double maxRange, @Nonnull QuadPredicate<S, Ref<EntityStore>, Role, ComponentAccessor<EntityStore>> filter, Role role, S s, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 268 */     int droppedItemsSize = this.droppedItems.size();
/* 269 */     if (droppedItemsSize == 0) return null;
/*     */     
/* 271 */     TransformComponent transformComponent = (TransformComponent)componentAccessor.getComponent(ref, TRANSFORM_COMPONENT_TYPE);
/* 272 */     assert transformComponent != null;
/*     */     
/* 274 */     Vector3d position = transformComponent.getPosition();
/* 275 */     minRange *= minRange;
/* 276 */     maxRange *= maxRange;
/* 277 */     for (int index = 0; index < droppedItemsSize; index++) {
/* 278 */       Ref<EntityStore> itemEntityRef = this.droppedItems.get(index);
/* 279 */       if (itemEntityRef.isValid()) {
/*     */         
/* 281 */         ItemComponent itemComponent = (ItemComponent)componentAccessor.getComponent(itemEntityRef, ITEM_COMPONENT_TYPE);
/* 282 */         if (itemComponent != null) {
/*     */           
/* 284 */           TransformComponent itemEntityTransformComponent = (TransformComponent)componentAccessor.getComponent(itemEntityRef, TRANSFORM_COMPONENT_TYPE);
/* 285 */           assert itemEntityTransformComponent != null;
/*     */           
/* 287 */           double squaredDistance = itemEntityTransformComponent.getPosition().distanceSquaredTo(position);
/* 288 */           if (squaredDistance >= minRange)
/* 289 */           { if (squaredDistance >= maxRange)
/*     */               break; 
/* 291 */             if (filter.test(s, itemEntityRef, role, componentAccessor))
/* 292 */               return itemEntityRef;  } 
/*     */         } 
/*     */       } 
/* 295 */     }  return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public <S> boolean isEntityCountInRange(double minRange, double maxRange, int minCount, int maxCount, boolean findPlayers, Role role, @Nonnull QuadPredicate<S, Ref<EntityStore>, Role, ComponentAccessor<EntityStore>> filter, S s, ComponentAccessor<EntityStore> componentAccessor) {
/* 300 */     int count = 0;
/*     */ 
/*     */     
/* 303 */     if (findPlayers) {
/* 304 */       count = this.players.countEntitiesInRange(minRange, maxRange, maxCount + 1, filter, s, role, componentAccessor);
/* 305 */       if (count > maxCount) return false; 
/*     */     } 
/* 307 */     count += this.npcs.<S, Role>countEntitiesInRange(minRange, maxRange, maxCount - count + 1, filter, s, role, componentAccessor);
/* 308 */     return (count >= minCount && count <= maxCount);
/*     */   }
/*     */ 
/*     */   
/*     */   public <S, T> int countEntitiesInRange(double minRange, double maxRange, boolean findPlayers, @Nonnull QuadPredicate<S, Ref<EntityStore>, T, ComponentAccessor<EntityStore>> filter, S s, T t, ComponentAccessor<EntityStore> componentAccessor) {
/* 313 */     int count = 0;
/*     */     
/* 315 */     if (findPlayers) {
/* 316 */       count = this.players.countEntitiesInRange(minRange, maxRange, 2147483647, filter, s, t, componentAccessor);
/*     */     }
/* 318 */     return count + this.npcs.<S, T>countEntitiesInRange(minRange, maxRange, 2147483647, filter, s, t, componentAccessor);
/*     */   }
/*     */ 
/*     */   
/*     */   public void requirePlayerDistanceSorted(double v) {
/* 323 */     int value = MathUtil.ceil(v);
/* 324 */     if (!this.isConfiguring) throw new IllegalStateException("function can be only called while configuring PositionCache"); 
/* 325 */     this.players.requireDistanceSorted(value);
/* 326 */     RoleStats roleStats = this.role.getRoleStats();
/* 327 */     if (roleStats != null) roleStats.trackRange(true, RoleStats.RangeType.SORTED, value);
/*     */   
/*     */   }
/*     */   
/*     */   public void requirePlayerDistanceUnsorted(double v) {
/* 332 */     int value = MathUtil.ceil(v);
/* 333 */     if (!this.isConfiguring) throw new IllegalStateException("function can be only called while configuring PositionCache"); 
/* 334 */     this.players.requireDistanceUnsorted(value);
/* 335 */     RoleStats roleStats = this.role.getRoleStats();
/* 336 */     if (roleStats != null) roleStats.trackRange(true, RoleStats.RangeType.UNSORTED, value);
/*     */   
/*     */   }
/*     */   
/*     */   public void requirePlayerDistanceAvoidance(double v) {
/* 341 */     int value = MathUtil.ceil(v);
/* 342 */     if (!this.isConfiguring) throw new IllegalStateException("function can be only called while configuring PositionCache"); 
/* 343 */     this.players.requireDistanceAvoidance(value);
/* 344 */     RoleStats roleStats = this.role.getRoleStats();
/* 345 */     if (roleStats != null) roleStats.trackRange(true, RoleStats.RangeType.AVOIDANCE, value);
/*     */   
/*     */   }
/*     */   
/*     */   public void requireEntityDistanceSorted(double v) {
/* 350 */     int value = MathUtil.ceil(v);
/* 351 */     if (!this.isConfiguring) throw new IllegalStateException("function can be only called while configuring PositionCache"); 
/* 352 */     this.npcs.requireDistanceSorted(value);
/* 353 */     RoleStats roleStats = this.role.getRoleStats();
/* 354 */     if (roleStats != null) roleStats.trackRange(false, RoleStats.RangeType.SORTED, value);
/*     */   
/*     */   }
/*     */   
/*     */   public void requireEntityDistanceUnsorted(double v) {
/* 359 */     int value = MathUtil.ceil(v);
/* 360 */     if (!this.isConfiguring) throw new IllegalStateException("function can be only called while configuring PositionCache"); 
/* 361 */     this.npcs.requireDistanceUnsorted(value);
/* 362 */     RoleStats roleStats = this.role.getRoleStats();
/* 363 */     if (roleStats != null) roleStats.trackRange(false, RoleStats.RangeType.UNSORTED, value);
/*     */   
/*     */   }
/*     */   
/*     */   public void requireEntityDistanceAvoidance(double v) {
/* 368 */     int value = MathUtil.ceil(v);
/* 369 */     if (!this.isConfiguring) throw new IllegalStateException("function can be only called while configuring PositionCache"); 
/* 370 */     value = this.npcs.requireDistanceAvoidance(value);
/* 371 */     RoleStats roleStats = this.role.getRoleStats();
/* 372 */     if (roleStats != null) roleStats.trackRange(false, RoleStats.RangeType.AVOIDANCE, value);
/*     */   
/*     */   }
/*     */   
/*     */   public void requireDroppedItemDistance(double value) {
/* 377 */     if (this.maxDroppedItemDistance < value) {
/* 378 */       this.maxDroppedItemDistance = value;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void requireSpawnMarkerDistance(double value) {
/* 384 */     if (this.maxSpawnMarkerDistance < value) {
/* 385 */       this.maxSpawnMarkerDistance = value;
/*     */     }
/*     */   }
/*     */   
/*     */   public void requireSpawnBeaconDistance(int value) {
/* 390 */     if (this.maxSpawnBeaconDistance < value) {
/* 391 */       this.maxSpawnBeaconDistance = value;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Role getRole() {
/* 400 */     return this.role;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T, U, V, R> void forEachNPCUnordered(double maxDistance, @Nonnull QuadPredicate<Ref<EntityStore>, T, U, ComponentAccessor<EntityStore>> predicate, @Nonnull QuadConsumer<Ref<EntityStore>, T, V, R> consumer, T t, U u, V v, R r, ComponentAccessor<EntityStore> componentAccessor) {
/* 406 */     this.npcs.forEachEntityUnordered(maxDistance, predicate, consumer, t, u, v, r, componentAccessor);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> void forEachEntityInAvoidanceRange(@Nonnull Set<Ref<EntityStore>> ignoredEntitiesForAvoidance, @Nonnull TriConsumer<Ref<EntityStore>, T, CommandBuffer<EntityStore>> consumer, T t, CommandBuffer<EntityStore> commandBuffer) {
/* 412 */     this.npcs.forEachEntityAvoidance(ignoredEntitiesForAvoidance, consumer, t, commandBuffer);
/* 413 */     this.players.forEachEntityAvoidance(ignoredEntitiesForAvoidance, consumer, t, commandBuffer);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T, U> void forEachEntityInAvoidanceRange(@Nonnull Set<Ref<EntityStore>> ignoredEntitiesForAvoidance, @Nonnull QuadConsumer<Ref<EntityStore>, T, U, CommandBuffer<EntityStore>> consumer, T t, U u, CommandBuffer<EntityStore> commandBuffer) {
/* 418 */     this.npcs.forEachEntityAvoidance(ignoredEntitiesForAvoidance, consumer, t, u, commandBuffer);
/* 419 */     this.players.forEachEntityAvoidance(ignoredEntitiesForAvoidance, consumer, t, u, commandBuffer);
/*     */   }
/*     */   
/*     */   private static class LineOfSightBuffer {
/*     */     @Nullable
/*     */     public World world;
/*     */     @Nullable
/*     */     public WorldChunk chunk;
/*     */     @Nullable
/*     */     public IntSet opaqueSet;
/*     */     @Nullable
/*     */     public BlockTypeAssetMap<String, BlockType> assetMap;
/*     */     public boolean result;
/*     */     
/*     */     public void clearRefs() {
/* 434 */       this.world = null;
/* 435 */       this.chunk = null;
/* 436 */       this.opaqueSet = null;
/* 437 */       this.assetMap = null;
/*     */     }
/*     */   }
/*     */   
/*     */   public void setOpaqueBlockSet(int blockSet) {
/* 442 */     this.opaqueBlockSet = blockSet;
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
/*     */   private static <T> boolean testLineOfSightRays(@Nonnull Ref<EntityStore> ref, @Nonnull Ref<EntityStore> targetRef, @Nonnull RayPredicate<T> predicate, @Nonnull T t, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 455 */     TransformComponent transformComponent = (TransformComponent)componentAccessor.getComponent(ref, TRANSFORM_COMPONENT_TYPE);
/* 456 */     assert transformComponent != null;
/* 457 */     Vector3d position = transformComponent.getPosition();
/*     */     
/* 459 */     ModelComponent modelComponent = (ModelComponent)componentAccessor.getComponent(ref, MODEL_COMPONENT_TYPE);
/* 460 */     float eyeHeight = (modelComponent != null) ? modelComponent.getModel().getEyeHeight() : 0.0F;
/*     */     
/* 462 */     double sx = position.getX();
/* 463 */     double sy = position.getY() + eyeHeight;
/* 464 */     double sz = position.getZ();
/*     */     
/* 466 */     TransformComponent targetTransformComponent = (TransformComponent)componentAccessor.getComponent(targetRef, TRANSFORM_COMPONENT_TYPE);
/* 467 */     assert targetTransformComponent != null;
/*     */     
/* 469 */     Vector3d targetPosition = targetTransformComponent.getPosition();
/* 470 */     double tx = targetPosition.getX();
/* 471 */     double ty = targetPosition.getY();
/* 472 */     double tz = targetPosition.getZ();
/*     */     
/* 474 */     ModelComponent targetModelComponent = (ModelComponent)componentAccessor.getComponent(targetRef, MODEL_COMPONENT_TYPE);
/*     */     
/* 476 */     if (targetModelComponent != null) {
/* 477 */       return predicate.test(sx, sy, sz, tx, ty + targetModelComponent.getModel().getEyeHeight(), tz, t, componentAccessor);
/*     */     }
/* 479 */     double ox = 0.0D, oy = 0.0D, oz = 0.0D;
/*     */     
/* 481 */     BoundingBox boundingBoxComponent = (BoundingBox)componentAccessor.getComponent(targetRef, BOUNDING_BOX_COMPONENT_TYPE);
/* 482 */     if (boundingBoxComponent != null) {
/* 483 */       Box boundingBox = boundingBoxComponent.getBoundingBox();
/* 484 */       ox = (boundingBox.getMax().getX() + boundingBox.getMin().getX()) / 2.0D;
/* 485 */       oy = (boundingBox.getMax().getY() + boundingBox.getMin().getY()) / 2.0D;
/* 486 */       oz = (boundingBox.getMax().getZ() + boundingBox.getMin().getZ()) / 2.0D;
/*     */     } 
/*     */     
/* 489 */     return predicate.test(sx, sy, sz, tx + ox, ty + oy, tz + oz, t, componentAccessor);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean hasLineOfSightInternal(@Nonnull Ref<EntityStore> ref, @Nonnull Ref<EntityStore> targetRef, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 494 */     if (ref.equals(targetRef)) {
/* 495 */       return false;
/*     */     }
/*     */     
/* 498 */     TransformComponent transformComponent = (TransformComponent)componentAccessor.getComponent(ref, TRANSFORM_COMPONENT_TYPE);
/* 499 */     assert transformComponent != null;
/*     */     
/* 501 */     TransformComponent targetTransformComponent = (TransformComponent)componentAccessor.getComponent(targetRef, TRANSFORM_COMPONENT_TYPE);
/* 502 */     assert targetTransformComponent != null;
/*     */     
/* 504 */     if (transformComponent.getPosition()
/* 505 */       .distanceSquaredTo(targetTransformComponent.getPosition()) <= 1.0E-12D) {
/* 506 */       return true;
/*     */     }
/*     */     
/* 509 */     World world = ((EntityStore)componentAccessor.getExternalData()).getWorld();
/* 510 */     Objects.requireNonNull(world, "World can't be null in isLOS");
/*     */     
/* 512 */     Int2ObjectMap<IntSet> blockSets = BlockSetModule.getInstance().getBlockSets();
/* 513 */     IntSet opaqueSet = (this.opaqueBlockSet >= 0 && blockSets != null) ? (IntSet)blockSets.get(this.opaqueBlockSet) : null;
/*     */     
/*     */     try {
/* 516 */       this.lineOfSightComputeBuffer.result = true;
/* 517 */       this.lineOfSightComputeBuffer.assetMap = BlockType.getAssetMap();
/* 518 */       this.lineOfSightComputeBuffer.opaqueSet = opaqueSet;
/* 519 */       this.lineOfSightComputeBuffer.world = world;
/*     */       
/* 521 */       return testLineOfSightRays(ref, targetRef, (sx, sy, sz, tx, ty, tz, buffer, accessor) -> { buffer.chunk = buffer.world.getChunkIfInMemory(ChunkUtil.indexChunkFromBlock(sx, sz)); if (buffer.chunk == null) return false;  BlockIterator.iterateFromTo(sx, sy, sz, tx, ty, tz, (), buffer); return buffer.result; }this.lineOfSightComputeBuffer, componentAccessor);
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
/*     */     }
/*     */     finally {
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
/* 552 */       this.lineOfSightComputeBuffer.clearRefs();
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean hasLineOfSight(@Nonnull Ref<EntityStore> ref, @Nonnull Ref<EntityStore> targetRef, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 557 */     boolean hasLineOfSight, cached = this.lineOfSightCache.containsKey(targetRef);
/*     */     
/* 559 */     if (cached) {
/* 560 */       if (this.isBenchmarking) NPCPlugin.get().collectSensorSupportLosTest(this.role.getRoleIndex(), true, 0L); 
/* 561 */       return (this.lineOfSightCache.getByte(targetRef) != 0);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 566 */     if (this.isBenchmarking) {
/* 567 */       long start = System.nanoTime();
/* 568 */       hasLineOfSight = hasLineOfSightInternal(ref, targetRef, componentAccessor);
/* 569 */       NPCPlugin.get().collectSensorSupportLosTest(this.role.getRoleIndex(), false, System.nanoTime() - start);
/*     */     } else {
/* 571 */       hasLineOfSight = hasLineOfSightInternal(ref, targetRef, componentAccessor);
/*     */     } 
/* 573 */     this.lineOfSightCache.put(targetRef, hasLineOfSight ? 1 : 0);
/* 574 */     return hasLineOfSight;
/*     */   }
/*     */   
/*     */   public boolean hasInverseLineOfSight(@Nonnull Ref<EntityStore> ref, @Nonnull Ref<EntityStore> targetRef, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 578 */     boolean cached = this.inverseLineOfSightCache.containsKey(targetRef);
/*     */     
/* 580 */     if (this.isBenchmarking) NPCPlugin.get().collectSensorSupportInverseLosTest(this.role.getRoleIndex(), cached);
/*     */     
/* 582 */     if (cached) {
/* 583 */       return (this.inverseLineOfSightCache.getByte(targetRef) != 0);
/*     */     }
/*     */     
/* 586 */     boolean hasLineOfSight = hasLineOfSightInternal(targetRef, ref, componentAccessor);
/* 587 */     this.inverseLineOfSightCache.put(targetRef, hasLineOfSight ? 1 : 0);
/* 588 */     return hasLineOfSight;
/*     */   }
/*     */   private static class LineOfSightEntityBuffer { public final Vector3d pos; public final Vector3d dir; public final Vector2d minMax;
/*     */     private LineOfSightEntityBuffer() {
/* 592 */       this.pos = new Vector3d();
/* 593 */       this.dir = new Vector3d();
/* 594 */       this.minMax = new Vector2d();
/*     */     } }
/*     */   
/*     */   public boolean isFriendlyBlockingLineOfSight(@Nonnull Ref<EntityStore> ref, @Nonnull Ref<EntityStore> targetRef, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 598 */     boolean cached = this.friendlyFireCache.containsKey(targetRef);
/*     */     
/* 600 */     if (this.isBenchmarking) NPCPlugin.get().collectSensorSupportFriendlyBlockingTest(this.role.getRoleIndex(), cached);
/*     */     
/* 602 */     if (cached) {
/* 603 */       return (this.friendlyFireCache.getByte(targetRef) != 0);
/*     */     }
/*     */     
/* 606 */     boolean blocking = testLineOfSightRays(ref, targetRef, (sx, sy, sz, tx, ty, tz, _this, accessor) -> {
/*     */           LineOfSightEntityBuffer buffer = _this.lineOfSightEntityComputeBuffer;
/*     */ 
/*     */           
/*     */           buffer.pos.assign(sx, sy, sz);
/*     */ 
/*     */           
/*     */           buffer.dir.assign(tx - sx, ty - sy, tz - sz);
/*     */ 
/*     */           
/*     */           double squaredLength = buffer.dir.squaredLength();
/*     */           
/* 618 */           return (squaredLength < 1.0E-6D) ? false : ((_this.players.testAnyEntityDistanceSquared(squaredLength, (), _this, buffer, accessor) || _this.npcs.testAnyEntityDistanceSquared(squaredLength, (), _this, buffer, accessor)));
/*     */         }this, componentAccessor);
/*     */     
/* 621 */     this.friendlyFireCache.put(targetRef, blocking ? 1 : 0);
/* 622 */     return blocking;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean testLineOfSightEntity(@Nonnull Ref<EntityStore> ref, @Nonnull Ref<EntityStore> targetRef, @Nonnull LineOfSightEntityBuffer buffer, @Nonnull ComponentAccessor<EntityStore> componentAccessor, double length2) {
/* 628 */     return (!targetRef.equals(ref) && this.role
/* 629 */       .isFriendly(targetRef, componentAccessor) && 
/* 630 */       rayIsIntersectingEntity(targetRef, buffer.pos, buffer.dir, buffer.minMax, length2, componentAccessor));
/*     */   }
/*     */   
/*     */   private void clearLineOfSightCache(double tickTime) {
/* 634 */     this.cacheTTL = (float)(this.cacheTTL - tickTime);
/* 635 */     if (this.cacheTTL <= 0.0F) {
/* 636 */       clearLineOfSightCache();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void clearLineOfSightCache() {
/* 642 */     this.cacheTTL = RandomExtra.randomRange(0.09F, 0.11F);
/*     */     
/* 644 */     this.lineOfSightCache.clear();
/* 645 */     this.inverseLineOfSightCache.clear();
/* 646 */     this.friendlyFireCache.clear();
/*     */   }
/*     */   
/*     */   protected static boolean rayIsIntersectingEntity(@Nonnull Ref<EntityStore> ref, @Nonnull Vector3d pos, @Nonnull Vector3d dir, @Nonnull Vector2d minMax, double length2, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 650 */     BoundingBox boundingBoxComponent = (BoundingBox)componentAccessor.getComponent(ref, BOUNDING_BOX_COMPONENT_TYPE);
/* 651 */     if (boundingBoxComponent == null) return false;
/*     */     
/* 653 */     TransformComponent transformComponent = (TransformComponent)componentAccessor.getComponent(ref, TRANSFORM_COMPONENT_TYPE);
/* 654 */     assert transformComponent != null;
/*     */     
/* 656 */     Vector3d position = transformComponent.getPosition();
/* 657 */     double px = position.getX();
/* 658 */     double py = position.getY();
/* 659 */     double pz = position.getZ();
/*     */ 
/*     */     
/* 662 */     double dx = px - pos.x;
/* 663 */     double dy = py - pos.y;
/* 664 */     double dz = pz - pos.z;
/* 665 */     double dotProduct = NPCPhysicsMath.dotProduct(dir.x, dir.y, dir.z, dx, dy, dz);
/* 666 */     if (dotProduct <= 0.0D)
/*     */     {
/* 668 */       return false;
/*     */     }
/*     */     
/* 671 */     double dist2 = NPCPhysicsMath.dotProduct(dx, dy, dz);
/* 672 */     if (dotProduct * dotProduct >= dist2 * length2)
/*     */     {
/* 674 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 678 */     return CollisionMath.intersectRayAABB(pos, dir, px, py, pz, boundingBoxComponent.getBoundingBox(), minMax);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public List<Ref<EntityStore>> getDroppedItemList() {
/* 683 */     return this.droppedItems;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public List<Ref<EntityStore>> getSpawnMarkerList() {
/* 688 */     return this.spawnMarkers;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public List<Ref<EntityStore>> getSpawnBeaconList() {
/* 693 */     return this.spawnBeacons;
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface RayPredicate<T> {
/*     */     boolean test(double param1Double1, double param1Double2, double param1Double3, double param1Double4, double param1Double5, double param1Double6, T param1T, @Nonnull ComponentAccessor<EntityStore> param1ComponentAccessor);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\role\support\PositionCache.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */