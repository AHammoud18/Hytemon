/*    */ package com.hypixel.hytale.server.core.modules.physics.util;
/*    */ 
/*    */ import com.hypixel.hytale.logger.HytaleLogger;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ForceProviderStandard
/*    */   implements ForceProvider
/*    */ {
/*    */   @Nonnull
/* 16 */   public static HytaleLogger LOGGER = HytaleLogger.forEnclosingClass(); @Nonnull
/* 17 */   protected Vector3d dragForce = new Vector3d();
/*    */ 
/*    */   
/*    */   public abstract double getMass(double paramDouble);
/*    */ 
/*    */   
/*    */   public abstract double getVolume();
/*    */   
/*    */   public abstract double getDensity();
/*    */   
/*    */   public abstract double getProjectedArea(PhysicsBodyState paramPhysicsBodyState, double paramDouble);
/*    */   
/*    */   public abstract double getFrictionCoefficient();
/*    */   
/*    */   public abstract ForceProviderStandardState getForceProviderStandardState();
/*    */   
/*    */   public void update(@Nonnull PhysicsBodyState bodyState, @Nonnull ForceAccumulator accumulator, boolean onGround) {
/* 34 */     ForceProviderStandardState standardState = getForceProviderStandardState();
/* 35 */     Vector3d extForce = standardState.externalForce;
/* 36 */     double extForceY = extForce.y;
/*    */     
/* 38 */     accumulator.force.add(extForce);
/*    */ 
/*    */     
/* 41 */     double speed = accumulator.speed;
/* 42 */     double dragForceDivSpeed = standardState.dragCoefficient * getProjectedArea(bodyState, speed) * speed;
/*    */     
/* 44 */     this.dragForce.assign(bodyState.velocity).scale(-dragForceDivSpeed);
/* 45 */     clipForce(this.dragForce, accumulator.resistanceForceLimit);
/* 46 */     accumulator.force.add(this.dragForce);
/*    */ 
/*    */     
/* 49 */     double gravityForce = -standardState.gravity * getMass(getVolume());
/* 50 */     if (onGround) {
/* 51 */       double frictionForce = (gravityForce + extForceY) * getFrictionCoefficient();
/* 52 */       if (speed > 0.0D && frictionForce > 0.0D) {
/* 53 */         frictionForce /= speed;
/* 54 */         accumulator.force.x -= bodyState.velocity.x * frictionForce;
/* 55 */         accumulator.force.z -= bodyState.velocity.z * frictionForce;
/*    */       } 
/*    */     } else {
/* 58 */       accumulator.force.y += gravityForce;
/*    */     } 
/*    */     
/* 61 */     if (standardState.displacedMass != 0.0D)
/*    */     {
/* 63 */       accumulator.force.y += standardState.displacedMass * standardState.gravity;
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void clipForce(@Nonnull Vector3d value, @Nonnull Vector3d threshold) {
/* 74 */     if (threshold.x < 0.0D)
/* 75 */     { if (value.x < threshold.x) value.x = threshold.x;
/*    */        }
/* 77 */     else if (value.x > threshold.x) { value.x = threshold.x; }
/*    */     
/* 79 */     if (threshold.y < 0.0D)
/* 80 */     { if (value.y < threshold.y) value.y = threshold.y;
/*    */        }
/* 82 */     else if (value.y > threshold.y) { value.y = threshold.y; }
/*    */     
/* 84 */     if (threshold.z < 0.0D)
/* 85 */     { if (value.z < threshold.z) value.z = threshold.z;
/*    */        }
/* 87 */     else if (value.z > threshold.z) { value.z = threshold.z; }
/*    */   
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\physic\\util\ForceProviderStandard.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */