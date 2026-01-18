/*    */ package com.hypixel.hytale.server.npc.movement.steeringforces;
/*    */ 
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.server.npc.movement.Steering;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SteeringForcePursue
/*    */   extends SteeringForceWithTarget
/*    */ {
/*    */   private double stopDistance;
/*    */   private double slowdownDistance;
/* 14 */   private double falloff = 3.0D;
/* 15 */   private double invFalloff = 1.0D / this.falloff;
/*    */   
/*    */   private double squaredStopDistance;
/*    */   private double squaredSlowdownDistance;
/*    */   private double distanceDelta;
/*    */   
/*    */   public SteeringForcePursue() {
/* 22 */     this(20.0D, 25.0D);
/*    */   }
/*    */   
/*    */   public SteeringForcePursue(double stopDistance, double slowdownDistance) {
/* 26 */     setDistances(stopDistance, slowdownDistance);
/*    */   }
/*    */   
/*    */   public void setDistances(double slowdown, double stop) {
/* 30 */     this.stopDistance = stop;
/* 31 */     this.slowdownDistance = slowdown;
/* 32 */     this.squaredStopDistance = stop * stop;
/* 33 */     this.squaredSlowdownDistance = slowdown * slowdown;
/* 34 */     this.distanceDelta = slowdown - stop;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean compute(@Nonnull Steering output) {
/* 39 */     if (super.compute(output)) {
/*    */       
/* 41 */       output.setTranslation(this.targetPosition);
/* 42 */       Vector3d translation = output.getTranslation();
/*    */       
/* 44 */       translation.subtract(this.selfPosition);
/*    */       
/* 46 */       double distanceSquared = translation.squaredLength();
/* 47 */       if (distanceSquared <= this.squaredStopDistance) {
/* 48 */         output.clear();
/* 49 */         return false;
/*    */       } 
/*    */       
/* 52 */       double distance = Math.sqrt(distanceSquared);
/* 53 */       if (distanceSquared >= this.squaredSlowdownDistance) {
/* 54 */         translation.scale(1.0D / distance);
/* 55 */         output.clearRotation();
/* 56 */         return true;
/*    */       } 
/*    */       
/* 59 */       double scale = Math.pow((distance - this.stopDistance) / this.distanceDelta, this.invFalloff);
/*    */       
/* 61 */       translation.setLength(scale);
/* 62 */       output.clearRotation();
/* 63 */       return true;
/*    */     } 
/* 65 */     return false;
/*    */   }
/*    */   
/*    */   public double getStopDistance() {
/* 69 */     return this.stopDistance;
/*    */   }
/*    */   
/*    */   public void setStopDistance(double stopDistance) {
/* 73 */     setDistances(getSlowdownDistance(), stopDistance);
/*    */   }
/*    */   
/*    */   public double getSlowdownDistance() {
/* 77 */     return this.slowdownDistance;
/*    */   }
/*    */   
/*    */   public void setSlowdownDistance(double slowdownDistance) {
/* 81 */     setDistances(slowdownDistance, getStopDistance());
/*    */   }
/*    */   
/*    */   public double getFalloff() {
/* 85 */     return this.falloff;
/*    */   }
/*    */   
/*    */   public void setFalloff(double falloff) {
/* 89 */     this.falloff = falloff;
/* 90 */     this.invFalloff = 1.0D / falloff;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\movement\steeringforces\SteeringForcePursue.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */