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
/*    */ public class PhysicsBodyStateUpdaterSymplecticEuler
/*    */   extends PhysicsBodyStateUpdater
/*    */ {
/*    */   public void update(@Nonnull PhysicsBodyState before, @Nonnull PhysicsBodyState after, double mass, double dt, boolean onGround, @Nonnull ForceProvider[] forceProvider) {
/* 16 */     computeAcceleration(before, onGround, forceProvider, mass, dt);
/* 17 */     updateAndClampVelocity(before, after, dt);
/* 18 */     updatePositionAfterVelocity(before, after, dt);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\physic\\util\PhysicsBodyStateUpdaterSymplecticEuler.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */