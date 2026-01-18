/*    */ package com.hypixel.hytale.builtin.deployables.system;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.deployables.component.DeployableComponent;
/*    */ import com.hypixel.hytale.component.ArchetypeChunk;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
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
/*    */ 
/*    */ public class DeployableTicker
/*    */   extends EntityTickingSystem<EntityStore>
/*    */ {
/*    */   public Query<EntityStore> getQuery() {
/* 65 */     return (Query<EntityStore>)Query.and(new Query[] { (Query)DeployableComponent.getComponentType() });
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void tick(float dt, int index, ArchetypeChunk<EntityStore> archetypeChunk, Store<EntityStore> store, CommandBuffer<EntityStore> commandBuffer) {
/* 71 */     DeployableComponent comp = (DeployableComponent)archetypeChunk.getComponent(index, DeployableComponent.getComponentType());
/* 72 */     comp.tick(dt, index, archetypeChunk, store, commandBuffer);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\deployables\system\DeployablesSystem$DeployableTicker.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */