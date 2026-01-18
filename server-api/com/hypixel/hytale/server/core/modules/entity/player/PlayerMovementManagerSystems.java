/*    */ package com.hypixel.hytale.server.core.modules.entity.player;
/*    */ 
/*    */ import com.hypixel.hytale.component.AddReason;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Holder;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.RemoveReason;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.system.HolderSystem;
/*    */ import com.hypixel.hytale.component.system.RefSystem;
/*    */ import com.hypixel.hytale.server.core.entity.entities.player.movement.MovementManager;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PlayerMovementManagerSystems
/*    */ {
/*    */   public static class AssignmentSystem
/*    */     extends HolderSystem<EntityStore>
/*    */   {
/*    */     @Nonnull
/* 27 */     private static final ComponentType<EntityStore, MovementManager> MOVEMENT_MANAGER_COMPONENT_TYPE = MovementManager.getComponentType();
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     @Nonnull
/* 33 */     private static final Query<EntityStore> QUERY = (Query<EntityStore>)Query.and(new Query[] { (Query)PlayerRef.getComponentType(), (Query)Query.not((Query)MOVEMENT_MANAGER_COMPONENT_TYPE) });
/*    */ 
/*    */     
/*    */     @Nonnull
/*    */     public Query<EntityStore> getQuery() {
/* 38 */       return QUERY;
/*    */     }
/*    */ 
/*    */     
/*    */     public void onEntityAdd(@Nonnull Holder<EntityStore> holder, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store) {
/* 43 */       holder.ensureComponent(MOVEMENT_MANAGER_COMPONENT_TYPE);
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public void onEntityRemoved(@Nonnull Holder<EntityStore> holder, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store) {}
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static class PostAssignmentSystem
/*    */     extends RefSystem<EntityStore>
/*    */   {
/*    */     @Nonnull
/* 60 */     private static final ComponentType<EntityStore, MovementManager> MOVEMENT_MANAGER_COMPONENT_TYPE = MovementManager.getComponentType();
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     @Nonnull
/* 66 */     private static final Query<EntityStore> QUERY = (Query<EntityStore>)Query.and(new Query[] { (Query)MOVEMENT_MANAGER_COMPONENT_TYPE, (Query)PlayerRef.getComponentType() });
/*    */ 
/*    */     
/*    */     @Nonnull
/*    */     public Query<EntityStore> getQuery() {
/* 71 */       return QUERY;
/*    */     }
/*    */ 
/*    */     
/*    */     public void onEntityAdded(@Nonnull Ref<EntityStore> ref, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 76 */       MovementManager movementManagerComponent = (MovementManager)commandBuffer.getComponent(ref, MOVEMENT_MANAGER_COMPONENT_TYPE);
/* 77 */       assert movementManagerComponent != null;
/*    */       
/* 79 */       movementManagerComponent.resetDefaultsAndUpdate(ref, (ComponentAccessor)commandBuffer);
/*    */     }
/*    */     
/*    */     public void onEntityRemove(@Nonnull Ref<EntityStore> ref, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {}
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\player\PlayerMovementManagerSystems.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */