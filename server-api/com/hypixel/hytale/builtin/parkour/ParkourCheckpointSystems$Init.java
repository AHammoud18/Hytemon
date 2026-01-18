/*    */ package com.hypixel.hytale.builtin.parkour;
/*    */ 
/*    */ import com.hypixel.hytale.component.AddReason;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.RemoveReason;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.system.RefSystem;
/*    */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
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
/*    */ 
/*    */ 
/*    */ public class Init
/*    */   extends RefSystem<EntityStore>
/*    */ {
/*    */   private final ComponentType<EntityStore, ParkourCheckpoint> parkourCheckpointComponentType;
/*    */   @Nonnull
/*    */   private final ComponentType<EntityStore, UUIDComponent> uuidComponentComponentType;
/*    */   @Nonnull
/*    */   private final Query<EntityStore> query;
/*    */   
/*    */   public Init(ComponentType<EntityStore, ParkourCheckpoint> parkourCheckpointComponentType) {
/* 37 */     this.parkourCheckpointComponentType = parkourCheckpointComponentType;
/* 38 */     this.uuidComponentComponentType = UUIDComponent.getComponentType();
/*    */     
/* 40 */     ComponentType<EntityStore, TransformComponent> transformComponentType = TransformComponent.getComponentType();
/*    */     
/* 42 */     this.query = (Query<EntityStore>)Query.and(new Query[] { (Query)parkourCheckpointComponentType, (Query)transformComponentType });
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
/* 53 */     ParkourCheckpoint entity = (ParkourCheckpoint)store.getComponent(ref, this.parkourCheckpointComponentType);
/* 54 */     ParkourPlugin.get().updateLastIndex(entity.getIndex());
/* 55 */     ParkourPlugin.get().getCheckpointUUIDMap().put(entity.getIndex(), ((UUIDComponent)store.getComponent(ref, this.uuidComponentComponentType)).getUuid());
/*    */   }
/*    */   
/*    */   public void onEntityRemove(@Nonnull Ref<EntityStore> ref, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\parkour\ParkourCheckpointSystems$Init.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */