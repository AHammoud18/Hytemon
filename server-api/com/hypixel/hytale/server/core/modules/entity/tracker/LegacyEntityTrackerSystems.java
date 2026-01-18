/*     */ package com.hypixel.hytale.server.core.modules.entity.tracker;
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.SystemGroup;
/*     */ import com.hypixel.hytale.component.dependency.Dependency;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*     */ import com.hypixel.hytale.protocol.ComponentUpdate;
/*     */ import com.hypixel.hytale.protocol.ComponentUpdateType;
/*     */ import com.hypixel.hytale.protocol.EntityUpdate;
/*     */ import com.hypixel.hytale.server.core.entity.Entity;
/*     */ import com.hypixel.hytale.server.core.entity.EntityUtils;
/*     */ import com.hypixel.hytale.server.core.entity.LivingEntity;
/*     */ import com.hypixel.hytale.server.core.entity.effect.EffectControllerComponent;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.entity.nameplate.Nameplate;
/*     */ import com.hypixel.hytale.server.core.inventory.Inventory;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.BoundingBox;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.EntityScaleComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.HeadRotation;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.player.PlayerSettings;
/*     */ import com.hypixel.hytale.server.core.modules.entity.player.PlayerSkinComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
/*     */ import com.hypixel.hytale.server.core.modules.projectile.component.PredictedProjectile;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.core.util.PositionUtil;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class LegacyEntityTrackerSystems {
/*     */   @Deprecated
/*     */   public static void sendPlayerSelf(@Nonnull Ref<EntityStore> viewerRef, @Nonnull Store<EntityStore> store) {
/*  46 */     EntityTrackerSystems.EntityViewer viewer = (EntityTrackerSystems.EntityViewer)store.getComponent(viewerRef, EntityTrackerSystems.EntityViewer.getComponentType());
/*  47 */     if (viewer == null) throw new IllegalArgumentException("Not EntityViewer");
/*     */     
/*  49 */     LivingEntity entity = (LivingEntity)EntityUtils.getEntity(viewerRef, (ComponentAccessor)store);
/*  50 */     TransformComponent transformComponent = (TransformComponent)store.getComponent(viewerRef, TransformComponent.getComponentType());
/*  51 */     HeadRotation headRotationComponent = (HeadRotation)store.getComponent(viewerRef, HeadRotation.getComponentType());
/*  52 */     ModelComponent modelComponent = (ModelComponent)store.getComponent(viewerRef, ModelComponent.getComponentType());
/*  53 */     EntityStatMap statMapComponent = (EntityStatMap)store.getComponent(viewerRef, EntityStatMap.getComponentType());
/*  54 */     PredictedProjectile predictionComponent = (PredictedProjectile)store.getComponent(viewerRef, PredictedProjectile.getComponentType());
/*  55 */     EffectControllerComponent effectControllerComponent = (EffectControllerComponent)store.getComponent(viewerRef, EffectControllerComponent.getComponentType());
/*  56 */     Nameplate nameplateComponent = (Nameplate)store.getComponent(viewerRef, Nameplate.getComponentType());
/*     */     
/*  58 */     EntityUpdate entityUpdate = new EntityUpdate();
/*  59 */     entityUpdate.networkId = entity.getNetworkId();
/*     */     
/*  61 */     ObjectArrayList<ComponentUpdate> list = new ObjectArrayList();
/*     */     
/*  63 */     if (store.getArchetype(viewerRef).contains(Interactable.getComponentType())) {
/*  64 */       ComponentUpdate componentUpdate = new ComponentUpdate();
/*  65 */       componentUpdate.type = ComponentUpdateType.Interactable;
/*  66 */       list.add(componentUpdate);
/*     */     } 
/*     */     
/*  69 */     if (store.getArchetype(viewerRef).contains(Intangible.getComponentType())) {
/*  70 */       ComponentUpdate componentUpdate = new ComponentUpdate();
/*  71 */       componentUpdate.type = ComponentUpdateType.Intangible;
/*  72 */       list.add(componentUpdate);
/*     */     } 
/*     */     
/*  75 */     if (store.getArchetype(viewerRef).contains(Invulnerable.getComponentType())) {
/*  76 */       ComponentUpdate componentUpdate = new ComponentUpdate();
/*  77 */       componentUpdate.type = ComponentUpdateType.Invulnerable;
/*  78 */       list.add(componentUpdate);
/*     */     } 
/*     */     
/*  81 */     if (store.getArchetype(viewerRef).contains(RespondToHit.getComponentType())) {
/*  82 */       ComponentUpdate componentUpdate = new ComponentUpdate();
/*  83 */       componentUpdate.type = ComponentUpdateType.RespondToHit;
/*  84 */       list.add(componentUpdate);
/*     */     } 
/*     */     
/*  87 */     if (nameplateComponent != null) {
/*  88 */       ComponentUpdate componentUpdate = new ComponentUpdate();
/*  89 */       componentUpdate.type = ComponentUpdateType.Nameplate;
/*  90 */       componentUpdate.nameplate = new Nameplate();
/*  91 */       componentUpdate.nameplate.text = nameplateComponent.getText();
/*  92 */       list.add(componentUpdate);
/*     */     } 
/*     */     
/*  95 */     if (predictionComponent != null) {
/*  96 */       ComponentUpdate componentUpdate = new ComponentUpdate();
/*  97 */       componentUpdate.type = ComponentUpdateType.Prediction;
/*  98 */       componentUpdate.predictionId = predictionComponent.getUuid();
/*  99 */       list.add(componentUpdate);
/*     */     } 
/*     */ 
/*     */     
/* 103 */     ComponentUpdate componentUpdate1 = new ComponentUpdate();
/* 104 */     componentUpdate1.type = ComponentUpdateType.Model;
/* 105 */     componentUpdate1.model = (modelComponent != null) ? modelComponent.getModel().toPacket() : null;
/* 106 */     EntityScaleComponent entityScaleComponent = (EntityScaleComponent)store.getComponent(viewerRef, EntityScaleComponent.getComponentType());
/* 107 */     if (entityScaleComponent != null) {
/* 108 */       componentUpdate1.entityScale = entityScaleComponent.getScale();
/*     */     }
/* 110 */     list.add(componentUpdate1);
/*     */ 
/*     */ 
/*     */     
/* 114 */     componentUpdate1 = new ComponentUpdate();
/* 115 */     componentUpdate1.type = ComponentUpdateType.PlayerSkin;
/* 116 */     PlayerSkinComponent component = (PlayerSkinComponent)store.getComponent(viewerRef, PlayerSkinComponent.getComponentType());
/* 117 */     componentUpdate1.skin = (component != null) ? component.getPlayerSkin() : null;
/* 118 */     list.add(componentUpdate1);
/*     */ 
/*     */ 
/*     */     
/* 122 */     Inventory inventory = entity.getInventory();
/*     */     
/* 124 */     ComponentUpdate componentUpdate2 = new ComponentUpdate();
/* 125 */     componentUpdate2.type = ComponentUpdateType.Equipment;
/* 126 */     componentUpdate2.equipment = new Equipment();
/*     */     
/* 128 */     ItemContainer armor = inventory.getArmor();
/* 129 */     componentUpdate2.equipment.armorIds = new String[armor.getCapacity()];
/* 130 */     Arrays.fill((Object[])componentUpdate2.equipment.armorIds, "");
/* 131 */     armor.forEachWithMeta((slot, itemStack, armorIds) -> armorIds[slot] = itemStack.getItemId(), componentUpdate2.equipment.armorIds);
/*     */ 
/*     */ 
/*     */     
/* 135 */     ItemStack itemInHand = inventory.getItemInHand();
/* 136 */     componentUpdate2.equipment.rightHandItemId = (itemInHand != null) ? itemInHand.getItemId() : "Empty";
/*     */     
/* 138 */     ItemStack utilityItem = inventory.getUtilityItem();
/* 139 */     componentUpdate2.equipment.leftHandItemId = (utilityItem != null) ? utilityItem.getItemId() : "Empty";
/* 140 */     list.add(componentUpdate2);
/*     */ 
/*     */ 
/*     */     
/* 144 */     ComponentUpdate update = new ComponentUpdate();
/* 145 */     update.type = ComponentUpdateType.Transform;
/* 146 */     update.transform = new ModelTransform();
/* 147 */     update.transform.position = PositionUtil.toPositionPacket(transformComponent.getPosition());
/* 148 */     update.transform.bodyOrientation = PositionUtil.toDirectionPacket(transformComponent.getRotation());
/* 149 */     update.transform.lookOrientation = PositionUtil.toDirectionPacket(headRotationComponent.getRotation());
/* 150 */     list.add(update);
/*     */ 
/*     */ 
/*     */     
/* 154 */     update = new ComponentUpdate();
/* 155 */     update.type = ComponentUpdateType.EntityEffects;
/* 156 */     update.entityEffectUpdates = effectControllerComponent.createInitUpdates();
/* 157 */     list.add(update);
/*     */ 
/*     */ 
/*     */     
/* 161 */     update = new ComponentUpdate();
/* 162 */     update.type = ComponentUpdateType.EntityStats;
/* 163 */     update.entityStatUpdates = (Map)statMapComponent.createInitUpdate(true);
/* 164 */     list.add(update);
/*     */ 
/*     */     
/* 167 */     entityUpdate.updates = (ComponentUpdate[])list.toArray(x$0 -> new ComponentUpdate[x$0]);
/* 168 */     viewer.packetReceiver.writeNoCache((Packet)new EntityUpdates(null, new EntityUpdate[] { entityUpdate }));
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
/*     */   @Deprecated
/*     */   public static boolean clear(@Nonnull Player player, @Nonnull Holder<EntityStore> holder) {
/* 181 */     World world = player.getWorld();
/* 182 */     if (world != null && world.isInThread()) {
/* 183 */       return EntityTrackerSystems.clear(player.getReference(), world.getEntityStore().getStore());
/*     */     }
/*     */     
/* 186 */     EntityTrackerSystems.EntityViewer entityViewerComponent = (EntityTrackerSystems.EntityViewer)holder.getComponent(EntityTrackerSystems.EntityViewer.getComponentType());
/* 187 */     if (entityViewerComponent == null) return false;
/*     */ 
/*     */     
/* 190 */     entityViewerComponent.sent.clear();
/* 191 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class LegacyLODCull
/*     */     extends EntityTickingSystem<EntityStore>
/*     */   {
/*     */     public static final double ENTITY_LOD_RATIO_DEFAULT = 3.5E-5D;
/*     */     
/* 200 */     public static double ENTITY_LOD_RATIO = 3.5E-5D;
/*     */     
/*     */     private final ComponentType<EntityStore, EntityTrackerSystems.EntityViewer> componentType;
/*     */     private final ComponentType<EntityStore, BoundingBox> boundingBoxComponentType;
/*     */     @Nonnull
/*     */     private final Query<EntityStore> query;
/*     */     @Nonnull
/*     */     private final Set<Dependency<EntityStore>> dependencies;
/*     */     
/*     */     public LegacyLODCull(ComponentType<EntityStore, EntityTrackerSystems.EntityViewer> componentType) {
/* 210 */       this.componentType = componentType;
/* 211 */       this.boundingBoxComponentType = BoundingBox.getComponentType();
/* 212 */       this.query = (Query<EntityStore>)Query.and(new Query[] { (Query)componentType, (Query)TransformComponent.getComponentType() });
/* 213 */       this.dependencies = Collections.singleton(new SystemDependency(Order.AFTER, EntityTrackerSystems.CollectVisible.class));
/*     */     }
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public SystemGroup<EntityStore> getGroup() {
/* 219 */       return EntityTrackerSystems.FIND_VISIBLE_ENTITIES_GROUP;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Set<Dependency<EntityStore>> getDependencies() {
/* 225 */       return this.dependencies;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Query<EntityStore> getQuery() {
/* 231 */       return this.query;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isParallel(int archetypeChunkSize, int taskCount) {
/* 236 */       return EntityTickingSystem.maybeUseParallel(archetypeChunkSize, taskCount);
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 241 */       EntityTrackerSystems.EntityViewer entityViewerComponent = (EntityTrackerSystems.EntityViewer)archetypeChunk.getComponent(index, this.componentType);
/* 242 */       assert entityViewerComponent != null;
/*     */       
/* 244 */       TransformComponent transformComponent = (TransformComponent)archetypeChunk.getComponent(index, TransformComponent.getComponentType());
/* 245 */       assert transformComponent != null;
/* 246 */       Vector3d position = transformComponent.getPosition();
/*     */       
/* 248 */       for (Iterator<Ref<EntityStore>> iterator = entityViewerComponent.visible.iterator(); iterator.hasNext(); ) {
/* 249 */         Ref<EntityStore> ref = iterator.next();
/*     */ 
/*     */         
/* 252 */         BoundingBox boundingBoxComponent = (BoundingBox)commandBuffer.getComponent(ref, this.boundingBoxComponentType);
/* 253 */         if (boundingBoxComponent == null)
/*     */           continue; 
/* 255 */         TransformComponent otherTransformComponent = (TransformComponent)commandBuffer.getComponent(ref, TransformComponent.getComponentType());
/* 256 */         assert otherTransformComponent != null;
/*     */ 
/*     */         
/* 259 */         double distanceSq = otherTransformComponent.getPosition().distanceSquaredTo(position);
/* 260 */         double maximumThickness = boundingBoxComponent.getBoundingBox().getMaximumThickness();
/* 261 */         if (maximumThickness < ENTITY_LOD_RATIO * distanceSq) {
/* 262 */           entityViewerComponent.lodExcludedCount++;
/* 263 */           iterator.remove();
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class LegacyHideFromEntity
/*     */     extends EntityTickingSystem<EntityStore>
/*     */   {
/*     */     private final ComponentType<EntityStore, EntityTrackerSystems.EntityViewer> entityViewerComponentType;
/*     */     
/*     */     private final ComponentType<EntityStore, PlayerSettings> playerSettingsComponentType;
/*     */     
/*     */     @Nonnull
/*     */     private final Query<EntityStore> query;
/*     */     
/*     */     @Nonnull
/*     */     private final Set<Dependency<EntityStore>> dependencies;
/*     */     
/*     */     public LegacyHideFromEntity(ComponentType<EntityStore, EntityTrackerSystems.EntityViewer> entityViewerComponentType) {
/* 284 */       this.entityViewerComponentType = entityViewerComponentType;
/* 285 */       this.playerSettingsComponentType = EntityModule.get().getPlayerSettingsComponentType();
/* 286 */       this.query = (Query<EntityStore>)Query.and(new Query[] { (Query)entityViewerComponentType, (Query)AllLegacyLivingEntityTypesQuery.INSTANCE });
/* 287 */       this.dependencies = Collections.singleton(new SystemDependency(Order.AFTER, EntityTrackerSystems.CollectVisible.class));
/*     */     }
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public SystemGroup<EntityStore> getGroup() {
/* 293 */       return EntityTrackerSystems.FIND_VISIBLE_ENTITIES_GROUP;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Set<Dependency<EntityStore>> getDependencies() {
/* 299 */       return this.dependencies;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Query<EntityStore> getQuery() {
/* 305 */       return this.query;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isParallel(int archetypeChunkSize, int taskCount) {
/* 310 */       return EntityTickingSystem.maybeUseParallel(archetypeChunkSize, taskCount);
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 315 */       Ref<EntityStore> viewerRef = archetypeChunk.getReferenceTo(index);
/* 316 */       PlayerSettings settings = (PlayerSettings)archetypeChunk.getComponent(index, this.playerSettingsComponentType);
/* 317 */       if (settings == null) {
/* 318 */         settings = PlayerSettings.defaults();
/*     */       }
/*     */       
/* 321 */       EntityTrackerSystems.EntityViewer entityViewerComponent = (EntityTrackerSystems.EntityViewer)archetypeChunk.getComponent(index, this.entityViewerComponentType);
/* 322 */       assert entityViewerComponent != null;
/*     */       
/* 324 */       for (Iterator<Ref<EntityStore>> iterator = entityViewerComponent.visible.iterator(); iterator.hasNext(); ) {
/* 325 */         Ref<EntityStore> ref = iterator.next();
/*     */         
/* 327 */         Entity entity = EntityUtils.getEntity(ref, (ComponentAccessor)commandBuffer);
/* 328 */         if (entity == null)
/*     */           continue; 
/* 330 */         if (entity.isHiddenFromLivingEntity(ref, viewerRef, (ComponentAccessor)commandBuffer) && canHideEntities(entity, settings)) {
/* 331 */           entityViewerComponent.hiddenCount++;
/* 332 */           iterator.remove();
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     private static boolean canHideEntities(Entity entity, @Nonnull PlayerSettings settings) {
/* 338 */       return (entity instanceof Player && !settings.showEntityMarkers());
/*     */     }
/*     */   }
/*     */   
/*     */   public static class LegacyEntityModel extends EntityTickingSystem<EntityStore> {
/*     */     private final ComponentType<EntityStore, EntityTrackerSystems.Visible> componentType;
/*     */     private final ComponentType<EntityStore, ModelComponent> modelComponentType;
/*     */     @Nonnull
/*     */     private final Query<EntityStore> query;
/*     */     
/*     */     public LegacyEntityModel(ComponentType<EntityStore, EntityTrackerSystems.Visible> componentType) {
/* 349 */       this.componentType = componentType;
/* 350 */       this.modelComponentType = ModelComponent.getComponentType();
/* 351 */       this.query = (Query<EntityStore>)Query.and(new Query[] { (Query)componentType, (Query)this.modelComponentType });
/*     */     }
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public SystemGroup<EntityStore> getGroup() {
/* 357 */       return EntityTrackerSystems.QUEUE_UPDATE_GROUP;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Query<EntityStore> getQuery() {
/* 363 */       return this.query;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isParallel(int archetypeChunkSize, int taskCount) {
/* 368 */       return EntityTickingSystem.maybeUseParallel(archetypeChunkSize, taskCount);
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 373 */       EntityTrackerSystems.Visible visibleComponent = (EntityTrackerSystems.Visible)archetypeChunk.getComponent(index, this.componentType);
/* 374 */       assert visibleComponent != null;
/*     */       
/* 376 */       ModelComponent modelComponent = (ModelComponent)archetypeChunk.getComponent(index, this.modelComponentType);
/* 377 */       assert modelComponent != null;
/*     */       
/* 379 */       float entityScale = 0.0F;
/* 380 */       boolean scaleOutdated = false;
/* 381 */       EntityScaleComponent entityScaleComponent = (EntityScaleComponent)archetypeChunk.getComponent(index, EntityScaleComponent.getComponentType());
/* 382 */       if (entityScaleComponent != null) {
/* 383 */         entityScale = entityScaleComponent.getScale();
/* 384 */         scaleOutdated = entityScaleComponent.consumeNetworkOutdated();
/*     */       } 
/*     */       
/* 387 */       boolean modelOutdated = modelComponent.consumeNetworkOutdated();
/* 388 */       if (modelOutdated || scaleOutdated) {
/* 389 */         queueUpdatesFor(archetypeChunk.getReferenceTo(index), modelComponent, entityScale, visibleComponent.visibleTo);
/* 390 */       } else if (!visibleComponent.newlyVisibleTo.isEmpty()) {
/* 391 */         queueUpdatesFor(archetypeChunk.getReferenceTo(index), modelComponent, entityScale, visibleComponent.newlyVisibleTo);
/*     */       } 
/*     */     }
/*     */     
/*     */     private static void queueUpdatesFor(Ref<EntityStore> ref, @Nullable ModelComponent model, float entityScale, @Nonnull Map<Ref<EntityStore>, EntityTrackerSystems.EntityViewer> visibleTo) {
/* 396 */       ComponentUpdate update = new ComponentUpdate();
/* 397 */       update.type = ComponentUpdateType.Model;
/* 398 */       update.model = (model != null) ? model.getModel().toPacket() : null;
/* 399 */       update.entityScale = entityScale;
/*     */       
/* 401 */       for (EntityTrackerSystems.EntityViewer viewer : visibleTo.values())
/* 402 */         viewer.queueUpdate(ref, update); 
/*     */     }
/*     */   }
/*     */   
/*     */   public static class LegacyEntitySkin
/*     */     extends EntityTickingSystem<EntityStore> {
/*     */     private final ComponentType<EntityStore, PlayerSkinComponent> playerSkinComponentComponentType;
/*     */     private final ComponentType<EntityStore, EntityTrackerSystems.Visible> visibleComponentType;
/*     */     @Nonnull
/*     */     private final Query<EntityStore> query;
/*     */     
/*     */     public LegacyEntitySkin(ComponentType<EntityStore, EntityTrackerSystems.Visible> visibleComponentType, ComponentType<EntityStore, PlayerSkinComponent> playerSkinComponentComponentType) {
/* 414 */       this.visibleComponentType = visibleComponentType;
/* 415 */       this.playerSkinComponentComponentType = playerSkinComponentComponentType;
/* 416 */       this.query = (Query<EntityStore>)Query.and(new Query[] { (Query)visibleComponentType, (Query)playerSkinComponentComponentType });
/*     */     }
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public SystemGroup<EntityStore> getGroup() {
/* 422 */       return EntityTrackerSystems.QUEUE_UPDATE_GROUP;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Query<EntityStore> getQuery() {
/* 428 */       return this.query;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isParallel(int archetypeChunkSize, int taskCount) {
/* 433 */       return EntityTickingSystem.maybeUseParallel(archetypeChunkSize, taskCount);
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 438 */       EntityTrackerSystems.Visible visibleComponent = (EntityTrackerSystems.Visible)archetypeChunk.getComponent(index, this.visibleComponentType);
/* 439 */       assert visibleComponent != null;
/*     */ 
/*     */       
/* 442 */       if (((PlayerSkinComponent)archetypeChunk.getComponent(index, this.playerSkinComponentComponentType)).consumeNetworkOutdated()) {
/* 443 */         queueUpdatesFor(archetypeChunk.getReferenceTo(index), (PlayerSkinComponent)archetypeChunk.getComponent(index, this.playerSkinComponentComponentType), visibleComponent.visibleTo);
/* 444 */       } else if (!visibleComponent.newlyVisibleTo.isEmpty()) {
/* 445 */         queueUpdatesFor(archetypeChunk.getReferenceTo(index), (PlayerSkinComponent)archetypeChunk.getComponent(index, this.playerSkinComponentComponentType), visibleComponent.newlyVisibleTo);
/*     */       } 
/*     */     }
/*     */     
/*     */     private static void queueUpdatesFor(Ref<EntityStore> ref, @Nonnull PlayerSkinComponent component, @Nonnull Map<Ref<EntityStore>, EntityTrackerSystems.EntityViewer> visibleTo) {
/* 450 */       ComponentUpdate update = new ComponentUpdate();
/* 451 */       update.type = ComponentUpdateType.PlayerSkin;
/* 452 */       update.skin = component.getPlayerSkin();
/*     */       
/* 454 */       for (EntityTrackerSystems.EntityViewer viewer : visibleTo.values())
/* 455 */         viewer.queueUpdate(ref, update); 
/*     */     }
/*     */   }
/*     */   
/*     */   public static class LegacyEquipment
/*     */     extends EntityTickingSystem<EntityStore> {
/*     */     private final ComponentType<EntityStore, EntityTrackerSystems.Visible> componentType;
/*     */     @Nonnull
/*     */     private final Query<EntityStore> query;
/*     */     
/*     */     public LegacyEquipment(ComponentType<EntityStore, EntityTrackerSystems.Visible> componentType) {
/* 466 */       this.componentType = componentType;
/* 467 */       this.query = (Query<EntityStore>)Query.and(new Query[] { (Query)componentType, (Query)AllLegacyLivingEntityTypesQuery.INSTANCE });
/*     */     }
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public SystemGroup<EntityStore> getGroup() {
/* 473 */       return EntityTrackerSystems.QUEUE_UPDATE_GROUP;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Query<EntityStore> getQuery() {
/* 479 */       return this.query;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isParallel(int archetypeChunkSize, int taskCount) {
/* 484 */       return EntityTickingSystem.maybeUseParallel(archetypeChunkSize, taskCount);
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 489 */       EntityTrackerSystems.Visible visibleComponent = (EntityTrackerSystems.Visible)archetypeChunk.getComponent(index, this.componentType);
/* 490 */       assert visibleComponent != null;
/*     */       
/* 492 */       LivingEntity entity = (LivingEntity)EntityUtils.getEntity(index, archetypeChunk);
/* 493 */       assert entity != null;
/*     */ 
/*     */       
/* 496 */       if (entity.consumeEquipmentNetworkOutdated()) {
/* 497 */         queueUpdatesFor(archetypeChunk.getReferenceTo(index), entity, visibleComponent.visibleTo);
/* 498 */       } else if (!visibleComponent.newlyVisibleTo.isEmpty()) {
/* 499 */         queueUpdatesFor(archetypeChunk.getReferenceTo(index), entity, visibleComponent.newlyVisibleTo);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private static void queueUpdatesFor(@Nonnull Ref<EntityStore> ref, @Nonnull LivingEntity entity, @Nonnull Map<Ref<EntityStore>, EntityTrackerSystems.EntityViewer> visibleTo) {
/* 506 */       ComponentUpdate update = new ComponentUpdate();
/* 507 */       update.type = ComponentUpdateType.Equipment;
/* 508 */       update.equipment = new Equipment();
/*     */       
/* 510 */       Inventory inventory = entity.getInventory();
/*     */       
/* 512 */       ItemContainer armor = inventory.getArmor();
/* 513 */       update.equipment.armorIds = new String[armor.getCapacity()];
/* 514 */       Arrays.fill((Object[])update.equipment.armorIds, "");
/* 515 */       armor.forEachWithMeta((slot, itemStack, armorIds) -> armorIds[slot] = itemStack.getItemId(), update.equipment.armorIds);
/*     */ 
/*     */ 
/*     */       
/* 519 */       ItemStack itemInHand = inventory.getItemInHand();
/* 520 */       update.equipment.rightHandItemId = (itemInHand != null) ? itemInHand.getItemId() : "Empty";
/*     */       
/* 522 */       ItemStack utilityItem = inventory.getUtilityItem();
/* 523 */       update.equipment.leftHandItemId = (utilityItem != null) ? utilityItem.getItemId() : "Empty";
/*     */       
/* 525 */       for (EntityTrackerSystems.EntityViewer viewer : visibleTo.values())
/* 526 */         viewer.queueUpdate(ref, update); 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\tracker\LegacyEntityTrackerSystems.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */