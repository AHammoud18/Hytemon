/*    */ package com.hypixel.hytale.server.npc.movement.steeringforces;
/*    */ 
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.movement.Steering;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class SteeringForceWithGroup
/*    */   implements SteeringForce
/*    */ {
/*    */   @Nonnull
/* 20 */   protected final Vector3d selfPosition = new Vector3d();
/*    */   
/*    */   protected Vector3d componentSelector;
/*    */ 
/*    */   
/*    */   public void setSelf(@Nonnull Ref<EntityStore> ref, @Nonnull Vector3d position, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 26 */     this.selfPosition.assign(position.getX(), position.getY(), position.getZ());
/*    */   }
/*    */   
/*    */   public void setComponentSelector(Vector3d componentSelector) {
/* 30 */     this.componentSelector = componentSelector;
/*    */   }
/*    */ 
/*    */   
/*    */   public abstract void reset();
/*    */   
/*    */   public abstract void add(@Nonnull Ref<EntityStore> paramRef, @Nonnull CommandBuffer<EntityStore> paramCommandBuffer);
/*    */   
/*    */   public boolean compute(Steering output) {
/* 39 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\movement\steeringforces\SteeringForceWithGroup.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */