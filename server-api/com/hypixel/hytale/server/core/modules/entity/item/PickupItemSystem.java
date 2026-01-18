/*     */ package com.hypixel.hytale.server.core.modules.entity.item;
/*     */ 
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
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
/*     */ public class PickupItemSystem
/*     */   extends EntityTickingSystem<EntityStore>
/*     */ {
/*     */   private static final float EYE_HEIGHT_SCALE = 5.0F;
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, PickupItemComponent> pickupItemComponentType;
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, TransformComponent> transformComponentType;
/*     */   @Nonnull
/*     */   private final Query<EntityStore> query;
/*     */   
/*     */   public PickupItemSystem(@Nonnull ComponentType<EntityStore, PickupItemComponent> pickupItemComponentType, @Nonnull ComponentType<EntityStore, TransformComponent> transformComponentType) {
/*  51 */     this.pickupItemComponentType = pickupItemComponentType;
/*  52 */     this.transformComponentType = transformComponentType;
/*  53 */     this.query = (Query<EntityStore>)Query.and(new Query[] { (Query)pickupItemComponentType, (Query)transformComponentType });
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/*  58 */     PickupItemComponent pickupItemComponent = (PickupItemComponent)archetypeChunk.getComponent(index, this.pickupItemComponentType);
/*  59 */     assert pickupItemComponent != null;
/*     */ 
/*     */     
/*  62 */     if (pickupItemComponent.hasFinished()) {
/*  63 */       commandBuffer.removeEntity(archetypeChunk.getReferenceTo(index), RemoveReason.REMOVE);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  68 */     Ref<EntityStore> targetRef = pickupItemComponent.getTargetRef();
/*  69 */     if (!targetRef.isValid()) {
/*  70 */       commandBuffer.removeEntity(archetypeChunk.getReferenceTo(index), RemoveReason.REMOVE);
/*     */       
/*     */       return;
/*     */     } 
/*  74 */     TransformComponent transformComponent = (TransformComponent)archetypeChunk.getComponent(index, this.transformComponentType);
/*  75 */     assert transformComponent != null;
/*  76 */     Vector3d position = transformComponent.getPosition();
/*     */     
/*  78 */     TransformComponent targetTransformComponent = (TransformComponent)commandBuffer.getComponent(targetRef, this.transformComponentType);
/*  79 */     assert targetTransformComponent != null;
/*  80 */     Vector3d targetPosition = targetTransformComponent.getPosition().clone();
/*     */     
/*  82 */     ModelComponent targetModelComponent = (ModelComponent)commandBuffer.getComponent(targetRef, ModelComponent.getComponentType());
/*  83 */     if (targetModelComponent != null) {
/*  84 */       float targetModelEyeHeight = targetModelComponent.getModel().getEyeHeight(targetRef, (ComponentAccessor)commandBuffer);
/*  85 */       targetPosition.add(0.0D, (targetModelEyeHeight / 5.0F), 0.0D);
/*     */     } 
/*     */     
/*  88 */     if (updateMovement(pickupItemComponent, position, targetPosition, dt)) {
/*  89 */       pickupItemComponent.setFinished(true);
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
/*     */   private static boolean updateMovement(@Nonnull PickupItemComponent pickupItemComponent, @Nonnull Vector3d current, @Nonnull Vector3d target, float dt) {
/* 103 */     float remainingTime = pickupItemComponent.getLifeTime();
/* 104 */     float originalLifeTime = pickupItemComponent.getOriginalLifeTime();
/*     */     
/* 106 */     float progress = 1.0F - remainingTime / originalLifeTime;
/* 107 */     if (progress >= 1.0F) {
/* 108 */       current.assign(target);
/* 109 */       return true;
/*     */     } 
/*     */     
/* 112 */     current.assign(Vector3d.lerp(pickupItemComponent.getStartPosition(), target, progress));
/* 113 */     pickupItemComponent.decreaseLifetime(dt);
/* 114 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Query<EntityStore> getQuery() {
/* 120 */     return this.query;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\item\PickupItemSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */