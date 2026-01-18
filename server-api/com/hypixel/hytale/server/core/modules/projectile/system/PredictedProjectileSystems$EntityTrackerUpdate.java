/*    */ package com.hypixel.hytale.server.core.modules.projectile.system;
/*    */ 
/*    */ import com.hypixel.hytale.component.ArchetypeChunk;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.SystemGroup;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*    */ import com.hypixel.hytale.protocol.ComponentUpdate;
/*    */ import com.hypixel.hytale.protocol.ComponentUpdateType;
/*    */ import com.hypixel.hytale.server.core.modules.entity.tracker.EntityTrackerSystems;
/*    */ import com.hypixel.hytale.server.core.modules.projectile.component.PredictedProjectile;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityTrackerUpdate
/*    */   extends EntityTickingSystem<EntityStore>
/*    */ {
/*    */   @Nonnull
/* 30 */   private final ComponentType<EntityStore, EntityTrackerSystems.Visible> visibleComponentType = EntityTrackerSystems.Visible.getComponentType();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 36 */   private final ComponentType<EntityStore, PredictedProjectile> predictedProjectileComponentType = PredictedProjectile.getComponentType();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 42 */   private final Query<EntityStore> query = (Query<EntityStore>)Query.and(new Query[] { (Query)this.visibleComponentType, (Query)this.predictedProjectileComponentType });
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public SystemGroup<EntityStore> getGroup() {
/* 47 */     return EntityTrackerSystems.QUEUE_UPDATE_GROUP;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Query<EntityStore> getQuery() {
/* 53 */     return this.query;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isParallel(int archetypeChunkSize, int taskCount) {
/* 58 */     return EntityTickingSystem.maybeUseParallel(archetypeChunkSize, taskCount);
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 63 */     EntityTrackerSystems.Visible visibleComponent = (EntityTrackerSystems.Visible)archetypeChunk.getComponent(index, this.visibleComponentType);
/* 64 */     assert visibleComponent != null;
/*    */     
/* 66 */     PredictedProjectile predictedProjectileComponent = (PredictedProjectile)archetypeChunk.getComponent(index, this.predictedProjectileComponentType);
/* 67 */     assert predictedProjectileComponent != null;
/*    */     
/* 69 */     if (!visibleComponent.newlyVisibleTo.isEmpty()) {
/* 70 */       queueUpdatesFor(archetypeChunk.getReferenceTo(index), predictedProjectileComponent, visibleComponent.newlyVisibleTo);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static void queueUpdatesFor(@Nonnull Ref<EntityStore> ref, @Nonnull PredictedProjectile predictedProjectile, @Nonnull Map<Ref<EntityStore>, EntityTrackerSystems.EntityViewer> visibleTo) {
/* 84 */     ComponentUpdate update = new ComponentUpdate();
/* 85 */     update.type = ComponentUpdateType.Prediction;
/* 86 */     update.predictionId = predictedProjectile.getUuid();
/*    */     
/* 88 */     for (Map.Entry<Ref<EntityStore>, EntityTrackerSystems.EntityViewer> entry : visibleTo.entrySet())
/* 89 */       ((EntityTrackerSystems.EntityViewer)entry.getValue()).queueUpdate(ref, update); 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\projectile\system\PredictedProjectileSystems$EntityTrackerUpdate.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */