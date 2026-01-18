/*     */ package com.hypixel.hytale.server.npc.corecomponents.world;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.random.RandomExtra;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.ActionBase;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderActionBase;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.world.builders.BuilderActionTriggerSpawners;
/*     */ import com.hypixel.hytale.server.npc.role.Role;
/*     */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*     */ import com.hypixel.hytale.server.spawning.spawnmarkers.SpawnMarkerEntity;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class ActionTriggerSpawners extends ActionBase {
/*  22 */   protected static final ComponentType<EntityStore, SpawnMarkerEntity> SPAWN_MARKER_ENTITY_COMPONENT_TYPE = SpawnMarkerEntity.getComponentType();
/*  23 */   protected static final ComponentType<EntityStore, TransformComponent> TRANSFORM_COMPONENT_TYPE = TransformComponent.getComponentType();
/*     */   
/*     */   protected final String spawner;
/*     */   
/*     */   protected final double range;
/*     */   protected final double rangeSquared;
/*     */   protected final int count;
/*     */   @Nullable
/*     */   protected final List<Ref<EntityStore>> triggerList;
/*     */   protected Ref<EntityStore> parentRef;
/*     */   
/*     */   public ActionTriggerSpawners(@Nonnull BuilderActionTriggerSpawners builder, @Nonnull BuilderSupport support) {
/*  35 */     super((BuilderActionBase)builder);
/*  36 */     this.spawner = builder.getSpawner(support);
/*  37 */     this.range = builder.getRange(support);
/*  38 */     this.rangeSquared = this.range * this.range;
/*  39 */     this.count = builder.getCount(support);
/*  40 */     this.triggerList = (this.count > 0) ? (List<Ref<EntityStore>>)new ObjectArrayList(this.count) : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerWithSupport(@Nonnull Role role) {
/*  45 */     role.getPositionCache().requireSpawnMarkerDistance(this.range);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/*  50 */     super.execute(ref, role, sensorInfo, dt, store);
/*     */     
/*  52 */     this.parentRef = ref;
/*     */     
/*  54 */     List<Ref<EntityStore>> spawners = role.getPositionCache().getSpawnMarkerList();
/*     */     
/*  56 */     if (this.count <= 0) {
/*     */ 
/*     */       
/*  59 */       for (int j = 0; j < spawners.size(); j++) {
/*  60 */         Ref<EntityStore> spawnMarkerRef = filterMarker(spawners.get(j), store);
/*  61 */         if (spawnMarkerRef != null) {
/*  62 */           SpawnMarkerEntity spawnMarkerEntityComponent = (SpawnMarkerEntity)store.getComponent(spawnMarkerRef, SPAWN_MARKER_ENTITY_COMPONENT_TYPE);
/*  63 */           assert spawnMarkerEntityComponent != null;
/*     */           
/*  65 */           spawnMarkerEntityComponent.trigger(spawnMarkerRef, store);
/*     */         } 
/*     */       } 
/*  68 */       return true;
/*     */     } 
/*     */ 
/*     */     
/*  72 */     RandomExtra.reservoirSample(spawners, (reference, _this, _store) -> _this.filterMarker(reference, _store), this.count, this.triggerList, this, store);
/*     */     
/*  74 */     for (int i = 0; i < this.triggerList.size(); i++) {
/*  75 */       Ref<EntityStore> spawnMarkerRef = this.triggerList.get(i);
/*     */       
/*  77 */       SpawnMarkerEntity spawnMarkerEntityComponent = (SpawnMarkerEntity)store.getComponent(spawnMarkerRef, SPAWN_MARKER_ENTITY_COMPONENT_TYPE);
/*  78 */       assert spawnMarkerEntityComponent != null;
/*     */       
/*  80 */       spawnMarkerEntityComponent.trigger(spawnMarkerRef, store);
/*     */     } 
/*     */     
/*  83 */     this.triggerList.clear();
/*  84 */     return true;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected Ref<EntityStore> filterMarker(@Nonnull Ref<EntityStore> targetRef, @Nonnull Store<EntityStore> store) {
/*  89 */     if (!targetRef.isValid()) return null;
/*     */     
/*  91 */     TransformComponent parentTransformComponent = (TransformComponent)store.getComponent(this.parentRef, TRANSFORM_COMPONENT_TYPE);
/*  92 */     assert parentTransformComponent != null;
/*  93 */     Vector3d parentPosition = parentTransformComponent.getPosition();
/*     */     
/*  95 */     TransformComponent targetTransformComponent = (TransformComponent)store.getComponent(targetRef, TRANSFORM_COMPONENT_TYPE);
/*  96 */     assert targetTransformComponent != null;
/*  97 */     Vector3d targetPosition = targetTransformComponent.getPosition();
/*     */     
/*  99 */     SpawnMarkerEntity targetMarkerEntityComponent = (SpawnMarkerEntity)store.getComponent(targetRef, SPAWN_MARKER_ENTITY_COMPONENT_TYPE);
/*     */     
/* 101 */     return 
/*     */ 
/*     */       
/* 104 */       (targetMarkerEntityComponent != null && targetMarkerEntityComponent.isManualTrigger() && parentPosition.distanceSquaredTo(targetPosition) <= this.rangeSquared && (this.spawner == null || this.spawner.equals(targetMarkerEntityComponent.getSpawnMarkerId()))) ? targetRef : null;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\world\ActionTriggerSpawners.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */