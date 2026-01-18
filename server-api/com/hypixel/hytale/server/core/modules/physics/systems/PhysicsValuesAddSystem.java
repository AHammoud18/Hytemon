/*    */ package com.hypixel.hytale.server.core.modules.physics.systems;
/*    */ 
/*    */ import com.hypixel.hytale.component.AddReason;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Holder;
/*    */ import com.hypixel.hytale.component.RemoveReason;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.dependency.Dependency;
/*    */ import com.hypixel.hytale.component.dependency.Order;
/*    */ import com.hypixel.hytale.component.dependency.SystemDependency;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.system.HolderSystem;
/*    */ import com.hypixel.hytale.server.core.modules.entity.AllLegacyEntityTypesQuery;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
/*    */ import com.hypixel.hytale.server.core.modules.entity.system.ModelSystems;
/*    */ import com.hypixel.hytale.server.core.modules.physics.component.PhysicsValues;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.Set;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class PhysicsValuesAddSystem extends HolderSystem<EntityStore> {
/* 22 */   private final Set<Dependency<EntityStore>> dependencies = (Set)Set.of(new SystemDependency(Order.AFTER, ModelSystems.SetRenderedModel.class), new SystemDependency(Order.AFTER, ModelSystems.PlayerConnect.class));
/*    */   private final ComponentType<EntityStore, PhysicsValues> physicsValuesComponentType;
/*    */   @Nonnull
/*    */   private final Query<EntityStore> query;
/*    */   
/*    */   public PhysicsValuesAddSystem(ComponentType<EntityStore, PhysicsValues> physicsValuesComponentType) {
/* 28 */     this.physicsValuesComponentType = physicsValuesComponentType;
/* 29 */     this.query = (Query<EntityStore>)Query.and(new Query[] { (Query)AllLegacyEntityTypesQuery.INSTANCE, (Query)Query.not((Query)physicsValuesComponentType) });
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Set<Dependency<EntityStore>> getDependencies() {
/* 35 */     return this.dependencies;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEntityAdd(@Nonnull Holder<EntityStore> holder, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store) {
/* 40 */     PhysicsValues physicsValues = (PhysicsValues)holder.ensureAndGetComponent(this.physicsValuesComponentType);
/* 41 */     ModelComponent modelComponent = (ModelComponent)holder.getComponent(ModelComponent.getComponentType());
/*    */     
/* 43 */     if (modelComponent != null) {
/* 44 */       physicsValues.replaceValues(modelComponent.getModel().getPhysicsValues());
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onEntityRemoved(@Nonnull Holder<EntityStore> holder, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store) {}
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Query<EntityStore> getQuery() {
/* 56 */     return this.query;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\physics\systems\PhysicsValuesAddSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */