/*     */ package com.hypixel.hytale.builtin.path;
/*     */ 
/*     */ import com.hypixel.fastutil.ints.Int2ObjectConcurrentHashMap;
/*     */ import com.hypixel.hytale.builtin.path.entities.PatrolPathMarkerEntity;
/*     */ import com.hypixel.hytale.builtin.path.path.IPrefabPath;
/*     */ import com.hypixel.hytale.builtin.path.waypoint.IPrefabPathWaypoint;
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.ResourceType;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.dependency.Dependency;
/*     */ import com.hypixel.hytale.component.dependency.Order;
/*     */ import com.hypixel.hytale.component.dependency.SystemDependency;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.HolderSystem;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.HiddenFromAdventurePlayers;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.WorldGenId;
/*     */ import com.hypixel.hytale.server.core.modules.entity.system.ModelSystems;
/*     */ import com.hypixel.hytale.server.core.prefab.PrefabCopyableComponent;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class AddOrRemove
/*     */   extends HolderSystem<EntityStore> {
/*  35 */   public static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */   
/*     */   @Nullable
/*  38 */   private static final ComponentType<EntityStore, PatrolPathMarkerEntity> PATH_MARKER_ENTITY_COMPONENT_TYPE = PatrolPathMarkerEntity.getComponentType();
/*  39 */   private static final ComponentType<EntityStore, ModelComponent> MODEL_COMPONENT_TYPE = ModelComponent.getComponentType();
/*  40 */   private static final ResourceType<EntityStore, WorldPathData> STORE_WORLD_PATH_DATA_RESOURCE_TYPE = WorldPathData.getResourceType();
/*  41 */   private static final ComponentType<EntityStore, WorldGenId> WORLD_GEN_ID_COMPONENT_TYPE = WorldGenId.getComponentType();
/*  42 */   private static final Set<Dependency<EntityStore>> DEPENDENCIES = (Set)Set.of(new SystemDependency(Order.BEFORE, ModelSystems.ModelSpawned.class));
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Query<EntityStore> getQuery() {
/*  47 */     return (Query)PATH_MARKER_ENTITY_COMPONENT_TYPE;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Set<Dependency<EntityStore>> getDependencies() {
/*  53 */     return DEPENDENCIES;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEntityAdd(@Nonnull Holder<EntityStore> holder, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store) {
/*  58 */     PatrolPathMarkerEntity pathMarker = (PatrolPathMarkerEntity)holder.getComponent(PATH_MARKER_ENTITY_COMPONENT_TYPE);
/*  59 */     WorldPathData worldPathData = (WorldPathData)store.getResource(STORE_WORLD_PATH_DATA_RESOURCE_TYPE);
/*  60 */     WorldGenId worldGenIdComponent = (WorldGenId)holder.getComponent(WORLD_GEN_ID_COMPONENT_TYPE);
/*  61 */     int worldgenId = (worldGenIdComponent != null) ? worldGenIdComponent.getWorldGenId() : 0;
/*  62 */     String pathName = pathMarker.getPathName();
/*     */     
/*  64 */     UUID pathId = pathMarker.getPathId();
/*  65 */     if (pathId == null) {
/*     */ 
/*     */       
/*  68 */       pathId = UUID.nameUUIDFromBytes((pathName + pathName).getBytes(StandardCharsets.UTF_8));
/*  69 */       pathMarker.setPathId(pathId);
/*     */       
/*  71 */       int lastIndex = pathName.lastIndexOf('~');
/*  72 */       if (lastIndex != -1) {
/*  73 */         pathMarker.setPathName(pathName.substring(0, lastIndex));
/*  74 */         pathMarker.markNeedsSave();
/*  75 */         LOGGER.at(Level.INFO).log("Migrating path marker from path %s to use new UUID %s", pathName, pathId);
/*     */       } 
/*     */     } 
/*     */     
/*  79 */     IPrefabPath path = worldPathData.getOrConstructPrefabPath(worldgenId, pathId, pathName, com.hypixel.hytale.builtin.path.path.PatrolPath::new);
/*  80 */     path.addLoadedWaypoint((IPrefabPathWaypoint)pathMarker, pathMarker.getTempPathLength(), pathMarker.getOrder(), worldgenId);
/*  81 */     pathMarker.setParentPath(path);
/*     */     
/*  83 */     holder.putComponent(MODEL_COMPONENT_TYPE, (Component)new ModelComponent(PathPlugin.get().getPathMarkerModel()));
/*  84 */     pathMarker.markNeedsSave();
/*     */     
/*  86 */     holder.ensureComponent(HiddenFromAdventurePlayers.getComponentType());
/*  87 */     holder.ensureComponent(PrefabCopyableComponent.getComponentType());
/*     */   }
/*     */   
/*     */   public void onEntityRemoved(@Nonnull Holder<EntityStore> holder, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store) {
/*     */     UUID path;
/*  92 */     PatrolPathMarkerEntity pathMarker = (PatrolPathMarkerEntity)holder.getComponent(PatrolPathMarkerEntity.getComponentType());
/*  93 */     WorldPathData worldPathData = (WorldPathData)store.getResource(WorldPathData.getResourceType());
/*  94 */     WorldGenId worldGenIdComponent = (WorldGenId)holder.getComponent(WORLD_GEN_ID_COMPONENT_TYPE);
/*  95 */     int worldgenId = (worldGenIdComponent != null) ? worldGenIdComponent.getWorldGenId() : 0;
/*  96 */     switch (PrefabPathSystems.null.$SwitchMap$com$hypixel$hytale$component$RemoveReason[reason.ordinal()]) { case 1:
/*  97 */         worldPathData.unloadPrefabPathWaypoint(worldgenId, pathMarker.getPathId(), pathMarker.getOrder()); break;
/*     */       case 2:
/*  99 */         path = pathMarker.getPathId();
/* 100 */         if (path != null)
/* 101 */           worldPathData.removePrefabPathWaypoint(worldgenId, path, pathMarker.getOrder()); 
/*     */         break; }
/*     */   
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\path\PrefabPathSystems$AddOrRemove.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */