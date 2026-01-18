/*    */ package com.hypixel.hytale.server.core.modules.physics.util;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PhysicsBodyStateUpdaterRK4
/*    */   extends PhysicsBodyStateUpdater
/*    */ {
/* 14 */   private final PhysicsBodyState state = new PhysicsBodyState();
/*    */ 
/*    */   
/*    */   public void update(@Nonnull PhysicsBodyState before, @Nonnull PhysicsBodyState after, double mass, double dt, boolean onGround, @Nonnull ForceProvider[] forceProvider) {
/* 18 */     double halfTime = dt * 0.5D;
/*    */ 
/*    */     
/* 21 */     computeAcceleration(before, onGround, forceProvider, mass, halfTime);
/* 22 */     assignAcceleration(after);
/*    */ 
/*    */     
/* 25 */     updateVelocity(before, this.state, halfTime);
/* 26 */     updatePositionBeforeVelocity(before, this.state, halfTime);
/*    */ 
/*    */     
/* 29 */     computeAcceleration(this.state, onGround, forceProvider, mass, halfTime);
/* 30 */     addAcceleration(after, 2.0D);
/*    */ 
/*    */     
/* 33 */     updateVelocity(before, this.state, halfTime);
/* 34 */     updatePositionAfterVelocity(before, this.state, halfTime);
/*    */ 
/*    */     
/* 37 */     computeAcceleration(this.state, onGround, forceProvider, mass, halfTime);
/* 38 */     addAcceleration(after, 2.0D);
/*    */ 
/*    */     
/* 41 */     updateVelocity(before, this.state, dt);
/* 42 */     updatePositionAfterVelocity(before, this.state, dt);
/*    */ 
/*    */     
/* 45 */     computeAcceleration(this.state, onGround, forceProvider, mass, dt);
/* 46 */     addAcceleration(after);
/*    */ 
/*    */     
/* 49 */     convertAccelerationToVelocity(before, after, dt / 6.0D);
/*    */     
/* 51 */     updatePositionAfterVelocity(before, after, dt);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\physic\\util\PhysicsBodyStateUpdaterRK4.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */