/*     */ package com.hypixel.hytale.server.npc.movement.steeringforces;
/*     */ 
/*     */ import com.hypixel.hytale.server.core.modules.physics.util.PhysicsMath;
/*     */ import com.hypixel.hytale.server.npc.movement.Steering;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SteeringForceEvade
/*     */   extends SteeringForceWithTarget
/*     */ {
/*     */   private double slowdownDistance;
/*     */   private double stopDistance;
/*  14 */   private double falloff = 3.0D;
/*     */   
/*     */   private double squaredSlowdownDistance;
/*     */   
/*     */   private double squaredStopDistance;
/*     */   private double distanceDelta;
/*     */   private float directionHint;
/*     */   private boolean adhereToDirectionHint;
/*     */   
/*     */   public SteeringForceEvade() {
/*  24 */     this(20.0D, 25.0D);
/*     */   }
/*     */   
/*     */   public SteeringForceEvade(double slowdownDistance, double stopDistance) {
/*  28 */     setDistances(slowdownDistance, stopDistance);
/*     */   }
/*     */   
/*     */   public void setDistances(double min, double max) {
/*  32 */     this.slowdownDistance = min;
/*  33 */     this.stopDistance = max;
/*  34 */     this.squaredSlowdownDistance = min * min;
/*  35 */     this.squaredStopDistance = max * max;
/*  36 */     this.distanceDelta = max - min;
/*     */   }
/*     */   
/*     */   public void setDirectionHint(float heading) {
/*  40 */     this.directionHint = heading;
/*     */   }
/*     */   
/*     */   public void setAdhereToDirectionHint(boolean adhereToDirectionHint) {
/*  44 */     this.adhereToDirectionHint = adhereToDirectionHint;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean compute(@Nonnull Steering output) {
/*  49 */     if (super.compute(output)) {
/*  50 */       output.setTranslation(this.selfPosition).getTranslation().subtract(this.targetPosition);
/*     */       
/*  52 */       double distanceSquared = output.getTranslation().squaredLength();
/*     */       
/*  54 */       if (distanceSquared >= this.squaredStopDistance) {
/*  55 */         output.clear();
/*  56 */         return false;
/*     */       } 
/*     */       
/*  59 */       output.clearRotation();
/*     */ 
/*     */ 
/*     */       
/*  63 */       if (distanceSquared < 1.0E-6D) {
/*  64 */         output.setTranslation(PhysicsMath.headingX(this.directionHint), 0.0D, PhysicsMath.headingZ(this.directionHint));
/*  65 */         return true;
/*     */       } 
/*     */       
/*  68 */       if (this.adhereToDirectionHint) {
/*  69 */         output.setTranslation(PhysicsMath.headingX(this.directionHint), 0.0D, PhysicsMath.headingZ(this.directionHint));
/*     */       }
/*     */       
/*  72 */       if (distanceSquared < this.squaredSlowdownDistance || this.distanceDelta == 0.0D) {
/*  73 */         output.getTranslation().normalize();
/*  74 */         return true;
/*     */       } 
/*     */       
/*  77 */       double scale = Math.pow((this.stopDistance - Math.sqrt(distanceSquared)) / this.distanceDelta, 1.0D / this.falloff);
/*     */ 
/*     */       
/*  80 */       output.getTranslation().setLength(scale);
/*  81 */       return true;
/*     */     } 
/*  83 */     return false;
/*     */   }
/*     */   
/*     */   public double getSlowdownDistance() {
/*  87 */     return this.slowdownDistance;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSlowdownDistance(double slowdownDistance) {
/*  92 */     setDistances(slowdownDistance, getStopDistance());
/*     */   }
/*     */   
/*     */   public double getStopDistance() {
/*  96 */     return this.stopDistance;
/*     */   }
/*     */   
/*     */   public void setStopDistance(double stopDistance) {
/* 100 */     setDistances(getSlowdownDistance(), stopDistance);
/* 101 */     this.stopDistance = stopDistance;
/*     */   }
/*     */   
/*     */   public double getFalloff() {
/* 105 */     return this.falloff;
/*     */   }
/*     */   
/*     */   public void setFalloff(double falloff) {
/* 109 */     this.falloff = falloff;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\movement\steeringforces\SteeringForceEvade.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */