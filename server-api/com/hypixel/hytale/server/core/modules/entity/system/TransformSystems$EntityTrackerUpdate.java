/*     */ package com.hypixel.hytale.server.core.modules.entity.system;
/*     */ 
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.SystemGroup;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.protocol.ComponentUpdate;
/*     */ import com.hypixel.hytale.protocol.ComponentUpdateType;
/*     */ import com.hypixel.hytale.protocol.Direction;
/*     */ import com.hypixel.hytale.protocol.ModelTransform;
/*     */ import com.hypixel.hytale.protocol.Position;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.HeadRotation;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.tracker.EntityTrackerSystems;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.core.util.PositionUtil;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityTrackerUpdate
/*     */   extends EntityTickingSystem<EntityStore>
/*     */ {
/*     */   @Nonnull
/*  35 */   private final ComponentType<EntityStore, EntityTrackerSystems.Visible> visibleComponentType = EntityTrackerSystems.Visible.getComponentType();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  41 */   private final ComponentType<EntityStore, TransformComponent> transformComponentType = TransformComponent.getComponentType();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  47 */   private final ComponentType<EntityStore, HeadRotation> headRotationComponentType = HeadRotation.getComponentType();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  53 */   private final Query<EntityStore> query = (Query<EntityStore>)Query.and(new Query[] { (Query)this.visibleComponentType, (Query)this.transformComponentType });
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public SystemGroup<EntityStore> getGroup() {
/*  58 */     return EntityTrackerSystems.QUEUE_UPDATE_GROUP;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Query<EntityStore> getQuery() {
/*  64 */     return this.query;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isParallel(int archetypeChunkSize, int taskCount) {
/*  69 */     return EntityTickingSystem.maybeUseParallel(archetypeChunkSize, taskCount);
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/*  74 */     EntityTrackerSystems.Visible visibleComponent = (EntityTrackerSystems.Visible)archetypeChunk.getComponent(index, this.visibleComponentType);
/*  75 */     assert visibleComponent != null;
/*     */     
/*  77 */     TransformComponent transformComponent = (TransformComponent)archetypeChunk.getComponent(index, this.transformComponentType);
/*  78 */     assert transformComponent != null;
/*     */     
/*  80 */     HeadRotation headRotationComponent = (HeadRotation)archetypeChunk.getComponent(index, this.headRotationComponentType);
/*     */     
/*  82 */     ModelTransform sentTransform = transformComponent.getSentTransform();
/*     */     
/*  84 */     Vector3d position = transformComponent.getPosition();
/*     */     
/*  86 */     Vector3f headRotation = (headRotationComponent != null) ? headRotationComponent.getRotation() : Vector3f.ZERO;
/*  87 */     Vector3f bodyRotation = transformComponent.getRotation();
/*     */     
/*  89 */     Position sentPosition = sentTransform.position;
/*  90 */     Direction sentLookOrientation = sentTransform.lookOrientation;
/*  91 */     Direction sentBodyOrientation = sentTransform.bodyOrientation;
/*     */ 
/*     */     
/*  94 */     if (!PositionUtil.equals(position, sentPosition) || 
/*  95 */       !PositionUtil.equals(headRotation, sentLookOrientation) || 
/*  96 */       !PositionUtil.equals(bodyRotation, sentBodyOrientation)) {
/*  97 */       PositionUtil.assign(sentPosition, position);
/*  98 */       PositionUtil.assign(sentLookOrientation, headRotation);
/*  99 */       PositionUtil.assign(sentBodyOrientation, bodyRotation);
/*     */       
/* 101 */       queueUpdatesFor(archetypeChunk.getReferenceTo(index), sentTransform, visibleComponent.visibleTo, false);
/* 102 */     } else if (!visibleComponent.newlyVisibleTo.isEmpty()) {
/* 103 */       queueUpdatesFor(archetypeChunk.getReferenceTo(index), sentTransform, visibleComponent.newlyVisibleTo, true);
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
/*     */ 
/*     */   
/*     */   private static void queueUpdatesFor(@Nonnull Ref<EntityStore> ref, @Nonnull ModelTransform sentTransform, @Nonnull Map<Ref<EntityStore>, EntityTrackerSystems.EntityViewer> visibleTo, boolean newlyVisible) {
/* 119 */     ComponentUpdate update = new ComponentUpdate();
/* 120 */     update.type = ComponentUpdateType.Transform;
/* 121 */     update.transform = sentTransform;
/*     */     
/* 123 */     for (Map.Entry<Ref<EntityStore>, EntityTrackerSystems.EntityViewer> entry : visibleTo.entrySet()) {
/*     */       
/* 125 */       if (!newlyVisible && ref.equals(entry.getKey()))
/*     */         continue; 
/* 127 */       ((EntityTrackerSystems.EntityViewer)entry.getValue()).queueUpdate(ref, update);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\system\TransformSystems$EntityTrackerUpdate.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */