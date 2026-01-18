/*    */ package com.hypixel.hytale.server.npc.movement.steeringforces;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.server.core.entity.Entity;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.movement.Steering;
/*    */ import com.hypixel.hytale.server.npc.util.NPCPhysicsMath;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SteeringForceRotate
/*    */   implements SteeringForce
/*    */ {
/*    */   private float desiredHeading;
/*    */   private float heading;
/* 20 */   private double tolerance = 0.05235987901687622D;
/*    */ 
/*    */   
/*    */   public boolean compute(@Nonnull Steering output) {
/* 24 */     output.clear();
/* 25 */     float turnAngle = NPCPhysicsMath.turnAngle(this.desiredHeading, this.heading);
/* 26 */     if (Math.abs(turnAngle) >= this.tolerance) {
/* 27 */       output.setYaw(this.desiredHeading);
/* 28 */       return true;
/*    */     } 
/* 30 */     return false;
/*    */   }
/*    */   
/*    */   public void setDesiredHeading(float desiredHeading) {
/* 34 */     this.desiredHeading = desiredHeading;
/*    */   }
/*    */   
/*    */   public void setHeading(float heading) {
/* 38 */     this.heading = heading;
/*    */   }
/*    */   
/*    */   public void setHeading(Ref<EntityStore> ref, @Nonnull Entity entity, ComponentAccessor<EntityStore> componentAccessor) {
/* 42 */     TransformComponent transformComponent = (TransformComponent)componentAccessor.getComponent(ref, TransformComponent.getComponentType());
/* 43 */     assert transformComponent != null;
/*    */     
/* 45 */     this.heading = transformComponent.getRotation().getYaw();
/*    */   }
/*    */   
/*    */   public void setTolerance(double tolerance) {
/* 49 */     this.tolerance = tolerance;
/*    */   }
/*    */   
/*    */   public double getDesiredHeading() {
/* 53 */     return this.desiredHeading;
/*    */   }
/*    */   
/*    */   public double getHeading() {
/* 57 */     return this.heading;
/*    */   }
/*    */   
/*    */   public double getTolerance() {
/* 61 */     return this.tolerance;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\movement\steeringforces\SteeringForceRotate.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */