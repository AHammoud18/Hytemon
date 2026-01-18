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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PhysicsBodyStateUpdater
/*    */ {
/* 20 */   protected static double MIN_VELOCITY = 1.0E-6D;
/*    */   @Nonnull
/* 22 */   protected Vector3d acceleration = new Vector3d();
/*    */   
/* 24 */   protected final ForceAccumulator accumulator = new ForceAccumulator();
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
/*    */   public void update(@Nonnull PhysicsBodyState before, @Nonnull PhysicsBodyState after, double mass, double dt, boolean onGround, @Nonnull ForceProvider[] forceProvider) {
/* 37 */     computeAcceleration(before, onGround, forceProvider, mass, dt);
/* 38 */     updatePositionBeforeVelocity(before, after, dt);
/* 39 */     updateAndClampVelocity(before, after, dt);
/*    */   }
/*    */   
/*    */   protected static void updatePositionBeforeVelocity(@Nonnull PhysicsBodyState before, @Nonnull PhysicsBodyState after, double dt) {
/* 43 */     after.position.assign(before.position).addScaled(before.velocity, dt);
/*    */   }
/*    */   
/*    */   protected static void updatePositionAfterVelocity(@Nonnull PhysicsBodyState before, @Nonnull PhysicsBodyState after, double dt) {
/* 47 */     after.position.assign(before.position).addScaled(after.velocity, dt);
/*    */   }
/*    */   
/*    */   protected void updateAndClampVelocity(@Nonnull PhysicsBodyState before, @Nonnull PhysicsBodyState after, double dt) {
/* 51 */     updateVelocity(before, after, dt);
/* 52 */     after.velocity.clipToZero(MIN_VELOCITY);
/*    */   }
/*    */   
/*    */   protected void updateVelocity(@Nonnull PhysicsBodyState before, @Nonnull PhysicsBodyState after, double dt) {
/* 56 */     after.velocity.assign(before.velocity).addScaled(this.acceleration, dt);
/*    */   }
/*    */   
/*    */   protected void computeAcceleration(double mass) {
/* 60 */     this.acceleration.assign(this.accumulator.force).scale(1.0D / mass);
/*    */   }
/*    */   
/*    */   protected void computeAcceleration(@Nonnull PhysicsBodyState state, boolean onGround, @Nonnull ForceProvider[] forceProviders, double mass, double timeStep) {
/* 64 */     this.accumulator.computeResultingForce(state, onGround, forceProviders, mass, timeStep);
/* 65 */     computeAcceleration(mass);
/*    */   }
/*    */   
/*    */   protected void assignAcceleration(@Nonnull PhysicsBodyState state) {
/* 69 */     state.velocity.assign(this.acceleration);
/*    */   }
/*    */   
/*    */   protected void addAcceleration(@Nonnull PhysicsBodyState state, double scale) {
/* 73 */     state.velocity.addScaled(this.acceleration, scale);
/*    */   }
/*    */   
/*    */   protected void addAcceleration(@Nonnull PhysicsBodyState state) {
/* 77 */     state.velocity.add(this.acceleration);
/*    */   }
/*    */   
/*    */   protected void convertAccelerationToVelocity(@Nonnull PhysicsBodyState before, @Nonnull PhysicsBodyState after, double scale) {
/* 81 */     after.velocity.scale(scale).add(before.velocity).clipToZero(MIN_VELOCITY);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\physic\\util\PhysicsBodyStateUpdater.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */