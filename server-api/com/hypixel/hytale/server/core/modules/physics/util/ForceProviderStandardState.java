/*    */ package com.hypixel.hytale.server.core.modules.physics.util;
/*    */ 
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ForceProviderStandardState
/*    */ {
/*    */   public double displacedMass;
/*    */   public double dragCoefficient;
/*    */   public double gravity;
/* 17 */   public final Vector3d nextTickVelocity = new Vector3d();
/* 18 */   public final Vector3d externalVelocity = new Vector3d();
/* 19 */   public final Vector3d externalAcceleration = new Vector3d();
/* 20 */   public final Vector3d externalForce = new Vector3d();
/* 21 */   public final Vector3d externalImpulse = new Vector3d();
/*    */   
/*    */   public ForceProviderStandardState() {
/* 24 */     this.nextTickVelocity.assign(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);
/*    */   }
/*    */   
/*    */   public void convertToForces(double dt, double mass) {
/* 28 */     this.externalForce.addScaled(this.externalAcceleration, 1.0D / mass);
/* 29 */     this.externalForce.addScaled(this.externalImpulse, 1.0D / dt);
/* 30 */     this.externalAcceleration.assign(Vector3d.ZERO);
/* 31 */     this.externalImpulse.assign(Vector3d.ZERO);
/*    */   }
/*    */   
/*    */   public void updateVelocity(@Nonnull Vector3d velocity) {
/* 35 */     if (this.nextTickVelocity.x < Double.MAX_VALUE) {
/* 36 */       velocity.assign(this.nextTickVelocity);
/* 37 */       this.nextTickVelocity.assign(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);
/*    */     } 
/* 39 */     velocity.add(this.externalVelocity);
/* 40 */     this.externalVelocity.assign(Vector3d.ZERO);
/*    */   }
/*    */   
/*    */   public void clear() {
/* 44 */     this.externalForce.assign(Vector3d.ZERO);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\physic\\util\ForceProviderStandardState.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */