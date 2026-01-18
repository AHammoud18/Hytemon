/*    */ package com.hypixel.hytale.server.core.modules.physics.util;
/*    */ 
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ForceAccumulator
/*    */ {
/*    */   public double speed;
/* 13 */   public final Vector3d force = new Vector3d();
/* 14 */   public final Vector3d resistanceForceLimit = new Vector3d();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void initialize(@Nonnull PhysicsBodyState state, double mass, double timeStep) {
/* 24 */     this.force.assign(Vector3d.ZERO);
/* 25 */     this.speed = state.velocity.length();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 31 */     this.resistanceForceLimit.assign(state.velocity).scale(-mass / timeStep);
/*    */   }
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
/*    */   protected void computeResultingForce(@Nonnull PhysicsBodyState state, boolean onGround, @Nonnull ForceProvider[] forceProviders, double mass, double timeStep) {
/* 44 */     initialize(state, mass, timeStep);
/* 45 */     for (ForceProvider provider : forceProviders)
/* 46 */       provider.update(state, this, onGround); 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\physic\\util\ForceAccumulator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */