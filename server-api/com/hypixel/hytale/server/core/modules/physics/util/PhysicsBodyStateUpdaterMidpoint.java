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
/*    */ 
/*    */ 
/*    */ public class PhysicsBodyStateUpdaterMidpoint
/*    */   extends PhysicsBodyStateUpdater
/*    */ {
/*    */   public void update(@Nonnull PhysicsBodyState before, @Nonnull PhysicsBodyState after, double mass, double dt, boolean onGround, @Nonnull ForceProvider[] forceProvider) {
/* 17 */     double halfTime = 0.5D * dt;
/*    */ 
/*    */     
/* 20 */     computeAcceleration(before, onGround, forceProvider, mass, halfTime);
/* 21 */     updateVelocity(before, after, halfTime);
/*    */     
/* 23 */     updatePositionBeforeVelocity(before, after, halfTime);
/*    */ 
/*    */     
/* 26 */     computeAcceleration(after, onGround, forceProvider, mass, dt);
/*    */     
/* 28 */     updateAndClampVelocity(before, after, dt);
/*    */ 
/*    */     
/* 31 */     updatePositionAfterVelocity(before, after, dt);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\physic\\util\PhysicsBodyStateUpdaterMidpoint.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */