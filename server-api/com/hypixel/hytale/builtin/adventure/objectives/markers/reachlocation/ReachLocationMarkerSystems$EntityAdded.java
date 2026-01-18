/*    */ package com.hypixel.hytale.builtin.adventure.objectives.markers.reachlocation;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.Objective;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.ObjectiveDataStore;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.ObjectivePlugin;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.task.ObjectiveTaskRef;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.task.ReachLocationTask;
/*    */ import com.hypixel.hytale.component.AddReason;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.RemoveReason;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.system.RefSystem;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.Set;
/*    */ import javax.annotation.Nonnull;
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
/*    */ public class EntityAdded
/*    */   extends RefSystem<EntityStore>
/*    */ {
/*    */   private final ComponentType<EntityStore, ReachLocationMarker> reachLocationMarkerComponent;
/*    */   private final ComponentType<EntityStore, TransformComponent> transformComponentType;
/*    */   @Nonnull
/*    */   private final Query<EntityStore> query;
/*    */   
/*    */   public EntityAdded(ComponentType<EntityStore, ReachLocationMarker> reachLocationMarkerComponent) {
/* 40 */     this.reachLocationMarkerComponent = reachLocationMarkerComponent;
/* 41 */     this.transformComponentType = TransformComponent.getComponentType();
/* 42 */     this.query = (Query<EntityStore>)Query.and(new Query[] { (Query)reachLocationMarkerComponent, (Query)this.transformComponentType });
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Query<EntityStore> getQuery() {
/* 48 */     return this.query;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEntityAdded(@Nonnull Ref<EntityStore> ref, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 53 */     ReachLocationMarker reachLocationMarkerComponent = (ReachLocationMarker)commandBuffer.getComponent(ref, this.reachLocationMarkerComponent);
/* 54 */     assert reachLocationMarkerComponent != null;
/*    */     
/* 56 */     TransformComponent transformComponent = (TransformComponent)commandBuffer.getComponent(ref, this.transformComponentType);
/* 57 */     assert transformComponent != null;
/*    */     
/* 59 */     Vector3d pos = transformComponent.getPosition();
/* 60 */     ObjectiveDataStore objectiveDataStore = ObjectivePlugin.get().getObjectiveDataStore();
/*    */     
/* 62 */     Set<ObjectiveTaskRef<ReachLocationTask>> taskRefs = objectiveDataStore.getTaskRefsForType(ReachLocationTask.class);
/* 63 */     for (ObjectiveTaskRef<ReachLocationTask> taskRef : taskRefs) {
/* 64 */       Objective objective = objectiveDataStore.getObjective(taskRef.getObjectiveUUID());
/* 65 */       if (objective == null)
/*    */         continue; 
/* 67 */       ((ReachLocationTask)taskRef.getObjectiveTask()).setupMarker(objective, reachLocationMarkerComponent, pos, commandBuffer);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void onEntityRemove(@Nonnull Ref<EntityStore> ref, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\markers\reachlocation\ReachLocationMarkerSystems$EntityAdded.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */