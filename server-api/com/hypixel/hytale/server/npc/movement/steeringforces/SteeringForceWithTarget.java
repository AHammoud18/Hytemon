/*    */ package com.hypixel.hytale.server.npc.movement.steeringforces;
/*    */ 
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.server.npc.movement.Steering;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class SteeringForceWithTarget
/*    */   implements SteeringForce
/*    */ {
/* 12 */   protected final Vector3d selfPosition = new Vector3d();
/* 13 */   protected final Vector3d targetPosition = new Vector3d();
/*    */   private Vector3d componentSelector;
/*    */   
/*    */   @Nonnull
/*    */   public Vector3d getSelfPosition() {
/* 18 */     return this.selfPosition;
/*    */   }
/*    */   
/*    */   public void setSelfPosition(@Nonnull Vector3d selfPosition) {
/* 22 */     this.selfPosition.assign(selfPosition);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public Vector3d getTargetPosition() {
/* 27 */     return this.targetPosition;
/*    */   }
/*    */   
/*    */   public void setTargetPosition(@Nonnull Vector3d targetPosition) {
/* 31 */     this.targetPosition.assign(targetPosition);
/*    */   }
/*    */   
/*    */   public void setTargetPosition(double x, double y, double z) {
/* 35 */     this.targetPosition.assign(x, y, z);
/*    */   }
/*    */   
/*    */   public void setPositions(@Nonnull Vector3d self, @Nonnull Vector3d target) {
/* 39 */     setSelfPosition(self);
/* 40 */     setTargetPosition(target);
/*    */   }
/*    */   
/*    */   public void setSelfPosition(double x, double y, double z) {
/* 44 */     this.selfPosition.assign(x, y, z);
/*    */   }
/*    */   
/*    */   public void setComponentSelector(Vector3d componentSelector) {
/* 48 */     this.componentSelector = componentSelector;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean compute(Steering output) {
/* 53 */     this.selfPosition.scale(this.componentSelector);
/* 54 */     this.targetPosition.scale(this.componentSelector);
/* 55 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\movement\steeringforces\SteeringForceWithTarget.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */