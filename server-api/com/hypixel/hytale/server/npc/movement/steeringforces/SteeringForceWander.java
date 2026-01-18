/*    */ package com.hypixel.hytale.server.npc.movement.steeringforces;
/*    */ 
/*    */ import com.hypixel.hytale.math.random.RandomExtra;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.server.core.modules.physics.util.PhysicsMath;
/*    */ import com.hypixel.hytale.server.npc.movement.Steering;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SteeringForceWander
/*    */   implements SteeringForce
/*    */ {
/*    */   private double time;
/*    */   private double turnInterval;
/* 17 */   private double jitter = 0.5D;
/*    */   
/* 19 */   private final Vector3d velocity = new Vector3d(0.0D, 0.0D, -1.0D);
/*    */   
/*    */   public SteeringForceWander() {
/* 22 */     setTurnTime(5.0D);
/* 23 */     this.jitter = 0.5D;
/*    */   }
/*    */   
/*    */   public void setTurnTime(double t) {
/* 27 */     this.turnInterval = 0.0D;
/* 28 */     this.time = this.turnInterval;
/*    */   }
/*    */   
/*    */   public void updateTime(double dt) {
/* 32 */     this.time += dt;
/*    */   }
/*    */   
/*    */   public void setHeading(float heading) {
/* 36 */     this.velocity.x = PhysicsMath.headingX(heading);
/* 37 */     this.velocity.z = PhysicsMath.headingZ(heading);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean compute(@Nonnull Steering output) {
/* 42 */     if (this.time < this.turnInterval) {
/* 43 */       this.velocity.x += RandomExtra.randomBinomial() * this.jitter;
/* 44 */       this.velocity.z += RandomExtra.randomBinomial() * this.jitter;
/* 45 */       this.velocity.normalize();
/*    */     } 
/* 47 */     output.clearRotation();
/* 48 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\movement\steeringforces\SteeringForceWander.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */