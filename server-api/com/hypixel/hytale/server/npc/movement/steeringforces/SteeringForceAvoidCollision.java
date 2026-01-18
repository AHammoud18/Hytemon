/*     */ package com.hypixel.hytale.server.npc.movement.steeringforces;
/*     */ 
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.physics.component.Velocity;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*     */ import com.hypixel.hytale.server.npc.movement.Steering;
/*     */ import com.hypixel.hytale.server.npc.role.Role;
/*     */ import com.hypixel.hytale.server.npc.util.NPCPhysicsMath;
/*     */ import java.util.UUID;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ public class SteeringForceAvoidCollision
/*     */   extends SteeringForceWithGroup
/*     */ {
/*  24 */   private final Vector3d selfVelocity = new Vector3d();
/*     */   private double selfRadius;
/*  26 */   private double collisionTime = Double.MAX_VALUE;
/*  27 */   private final Vector3d colliderPosition = new Vector3d();
/*  28 */   private final double[] tempTime = new double[2];
/*  29 */   private final Vector3d tempPos = new Vector3d();
/*  30 */   private final Vector3d tempVel = new Vector3d();
/*     */   private double maxDistance;
/*  32 */   private double falloff = 2.0D;
/*  33 */   private double strength = 1.0D;
/*  34 */   private Role.AvoidanceMode avoidanceMode = Role.AvoidanceMode.Slowdown;
/*     */   private Ref<EntityStore> selfReference;
/*     */   @Nullable
/*     */   private Ref<EntityStore> otherReference;
/*     */   private double velocity;
/*     */   private double maxTime;
/*     */   private boolean canSlowDown;
/*     */   private boolean overlap;
/*     */   @Nonnull
/*  43 */   protected final Vector3d lastSteeringDirection = new Vector3d();
/*     */   
/*     */   private boolean debug;
/*     */   
/*     */   public void setDebug(boolean debug) {
/*  48 */     this.debug = debug;
/*     */   }
/*     */   
/*     */   public Role.AvoidanceMode getAvoidanceMode() {
/*  52 */     return this.avoidanceMode;
/*     */   }
/*     */   
/*     */   public void setAvoidanceMode(Role.AvoidanceMode avoidanceMode) {
/*  56 */     this.avoidanceMode = avoidanceMode;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSelf(@Nonnull Ref<EntityStore> ref, @Nonnull Vector3d position, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  61 */     setSelf(ref, position, (Vector3d)null, -1.0D, componentAccessor);
/*     */   }
/*     */   
/*     */   public void setSelf(@Nonnull Ref<EntityStore> ref, @Nonnull Vector3d position, @Nullable Vector3d velocity, double radius, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  65 */     super.setSelf(ref, position, componentAccessor);
/*  66 */     this.selfReference = ref;
/*  67 */     this.otherReference = null;
/*  68 */     if (velocity != null) {
/*  69 */       this.selfVelocity.assign(velocity);
/*     */     } else {
/*  71 */       setVelocityFromEntity(this.selfReference, componentAccessor);
/*     */     } 
/*  73 */     if (radius < 0.0D) {
/*  74 */       setRadiusFromEntity(this.selfReference, componentAccessor);
/*     */     } else {
/*  76 */       this.selfRadius = radius;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void reset() {
/*  82 */     this.collisionTime = Double.MAX_VALUE;
/*  83 */     this.otherReference = null;
/*  84 */     this.canSlowDown = true;
/*  85 */     this.overlap = false;
/*  86 */     double velocitySquared = this.selfVelocity.squaredLength();
/*  87 */     if (velocitySquared > 0.001D) {
/*  88 */       this.velocity = Math.sqrt(velocitySquared);
/*  89 */       this.maxTime = this.maxDistance / velocitySquared;
/*     */     } else {
/*  91 */       this.velocity = 0.0D;
/*  92 */       this.maxTime = 0.0D;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean compute(@Nonnull Steering output) {
/*  98 */     this.lastSteeringDirection.assign(Vector3d.ZERO);
/*  99 */     if (this.velocity == 0.0D) {
/* 100 */       return false;
/*     */     }
/* 102 */     if (super.compute(output)) {
/* 103 */       double l = output.getTranslation().length();
/* 104 */       if (l > 1.0E-4D) {
/* 105 */         double distance = this.collisionTime * this.selfVelocity.length();
/* 106 */         if (distance <= this.maxDistance) {
/* 107 */           switch (this.avoidanceMode) {
/*     */             case Slowdown:
/* 109 */               this.canSlowDown = true;
/*     */               break;
/*     */             case Evade:
/* 112 */               this.canSlowDown = this.overlap;
/*     */               break;
/*     */           } 
/*     */ 
/*     */           
/* 117 */           if (this.canSlowDown) {
/* 118 */             double s = Math.pow(distance / this.maxDistance, 1.0D / this.falloff);
/* 119 */             output.scaleTranslation(s);
/* 120 */             if (this.debug) {
/* 121 */               NPCPlugin.get().getLogger().at(Level.INFO).log("--> Avoidance slowdown=%s dist=%s maxDist=%s", Double.valueOf(s), Double.valueOf(distance), Double.valueOf(this.maxDistance));
/*     */             }
/*     */           } else {
/* 124 */             this.tempPos.assign(this.colliderPosition).subtract(this.selfPosition);
/*     */             
/* 126 */             NPCPhysicsMath.rejection(this.selfVelocity, this.tempPos, this.tempVel);
/* 127 */             this.tempVel.negate();
/*     */ 
/*     */             
/* 130 */             if (this.tempVel.squaredLength() < 0.001D) {
/* 131 */               this.selfVelocity.cross(Vector3d.UP, this.tempVel);
/* 132 */               if (this.tempVel.squaredLength() < 0.001D) {
/* 133 */                 this.selfVelocity.cross(Vector3d.RIGHT, this.tempVel);
/*     */               }
/*     */             } 
/*     */ 
/*     */             
/* 138 */             double s = Math.pow(1.0D - distance / this.maxDistance, 1.0D / this.falloff);
/*     */ 
/*     */             
/* 141 */             this.tempVel.setLength(l * s * this.strength).scale(this.componentSelector);
/* 142 */             this.lastSteeringDirection.assign(this.tempVel);
/*     */             
/* 144 */             output.scaleTranslation(1.0D - s);
/*     */             
/* 146 */             output.getTranslation().add(this.tempVel).setLength(l);
/*     */             
/* 148 */             if (this.debug) {
/* 149 */               NPCPlugin.get().getLogger().at(Level.INFO).log("--> Avoidance dist=%.2f l=%.2f s=%.2f maxDist=%.2f", Double.valueOf(distance), Double.valueOf(l), Double.valueOf(s), Double.valueOf(this.maxDistance));
/*     */             }
/*     */             
/* 152 */             if (!output.getTranslation().isFinite()) {
/* 153 */               if (this.debug) {
/* 154 */                 NPCPlugin.get().getLogger().at(Level.WARNING).log("Denormalized avoidance steering dist=%s l=%s s=%s", Double.valueOf(distance), Double.valueOf(l), Double.valueOf(s));
/*     */               }
/* 156 */               output.clearTranslation();
/* 157 */               return false;
/*     */             } 
/*     */           } 
/* 160 */           return true;
/*     */         } 
/*     */       } 
/*     */     } 
/* 164 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void add(@Nonnull Ref<EntityStore> ref, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 169 */     if (this.velocity == 0.0D || this.collisionTime == 0.0D) {
/*     */       return;
/*     */     }
/*     */     
/* 173 */     TransformComponent transformComponent = (TransformComponent)commandBuffer.getComponent(ref, TransformComponent.getComponentType());
/* 174 */     assert transformComponent != null;
/* 175 */     Vector3d position = transformComponent.getPosition();
/*     */     
/* 177 */     UUIDComponent uuidComponent = (UUIDComponent)commandBuffer.getComponent(ref, UUIDComponent.getComponentType());
/* 178 */     assert uuidComponent != null;
/* 179 */     UUID uuid = uuidComponent.getUuid();
/*     */     
/* 181 */     this.tempPos.assign(position);
/*     */     
/* 183 */     boolean departing = (this.tempVel.assign(this.tempPos).subtract(this.selfPosition).dot(this.selfVelocity) <= 0.0D);
/*     */     
/* 185 */     if (departing) {
/*     */       
/* 187 */       if (this.debug) {
/* 188 */         NPCPlugin.get().getLogger().at(Level.INFO).log("Avoidance add: Entity %s - Moving away, ignoring", uuid);
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/* 193 */     double entityRadius = NPCPhysicsMath.collisionSphereRadius(ref, (ComponentAccessor)commandBuffer);
/* 194 */     double sumRadius = this.selfRadius + entityRadius;
/*     */     
/* 196 */     this.overlap = (this.selfPosition.distanceSquaredTo(this.tempPos) <= sumRadius * sumRadius);
/* 197 */     if (this.overlap) {
/*     */       
/* 199 */       this.collisionTime = 0.0D;
/* 200 */       this.canSlowDown = true;
/* 201 */       if (this.debug) {
/* 202 */         NPCPlugin.get().getLogger().at(Level.INFO).log("Avoidance add: Overlap with %s - Stopping", uuid);
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/* 207 */     Velocity velocityComponent = (Velocity)commandBuffer.getComponent(ref, Velocity.getComponentType());
/* 208 */     velocityComponent.assignVelocityTo(this.tempVel);
/* 209 */     int solutions = NPCPhysicsMath.intersectSweptSpheresFootpoint(this.selfPosition, this.selfVelocity, this.selfRadius, this.tempPos, this.tempVel, entityRadius, Vector3d.ALL_ONES, this.tempTime);
/*     */     
/* 211 */     if (this.debug && solutions > 0) {
/* 212 */       NPCPlugin.get().getLogger().at(Level.INFO).log("Avoidance add: Solutions with %s=%s, time=[%s, %s], maxTime=%s, collisionTime=%s", uuid, Integer.valueOf(solutions), Double.valueOf(this.tempTime[0]), Double.valueOf(this.tempTime[1]), Double.valueOf(this.maxTime), Double.valueOf(this.collisionTime));
/*     */     }
/*     */     
/* 215 */     if (solutions == 0 || this.tempTime[0] < 0.0D || this.tempTime[0] > this.maxTime || this.tempTime[0] >= this.collisionTime) {
/*     */       return;
/*     */     }
/*     */     
/* 219 */     double tempVelocity = this.tempVel.length();
/* 220 */     double dot = (tempVelocity > 0.0D) ? (this.selfVelocity.dot(this.tempVel) / tempVelocity * this.velocity) : 0.0D;
/* 221 */     boolean antiParallel = (dot < -0.8D);
/*     */     
/* 223 */     if (this.debug && solutions > 0) {
/* 224 */       NPCPlugin.get().getLogger().at(Level.INFO).log("Avoidance add: New solution with %s, time=[%s, %s], maxTime=%s, antiParallel=%s, dot=%s, departing=%s, collisionTime=%s", uuid, Double.valueOf(this.tempTime[0]), Double.valueOf(this.tempTime[1]), Double.valueOf(this.maxTime), Boolean.valueOf(antiParallel), Double.valueOf(dot), Boolean.valueOf(departing), Double.valueOf(this.collisionTime));
/*     */     }
/*     */     
/* 227 */     this.colliderPosition.assign(position);
/* 228 */     this.collisionTime = this.tempTime[0];
/* 229 */     this.otherReference = ref;
/*     */ 
/*     */ 
/*     */     
/* 233 */     this.canSlowDown = (!antiParallel && this.otherReference.getIndex() < this.selfReference.getIndex());
/*     */   }
/*     */   
/*     */   public void setVelocityFromEntity(@Nonnull Ref<EntityStore> ref, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 237 */     Velocity velocityComponent = (Velocity)componentAccessor.getComponent(ref, Velocity.getComponentType());
/* 238 */     velocityComponent.assignVelocityTo(this.selfVelocity);
/*     */   }
/*     */   
/*     */   public void setRadiusFromEntity(@Nonnull Ref<EntityStore> ref, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 242 */     this.selfRadius = NPCPhysicsMath.collisionSphereRadius(ref, componentAccessor);
/*     */   }
/*     */   
/*     */   public void setMaxDistance(double distance) {
/* 246 */     this.maxDistance = distance;
/*     */   }
/*     */   
/*     */   public void setFalloff(double falloff) {
/* 250 */     this.falloff = falloff;
/*     */   }
/*     */   
/*     */   public void setSelfVelocity(@Nonnull Vector3d selfVelocity) {
/* 254 */     this.selfVelocity.assign(selfVelocity);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d getSelfVelocity() {
/* 259 */     return this.selfVelocity;
/*     */   }
/*     */   
/*     */   public double getSelfRadius() {
/* 263 */     return this.selfRadius;
/*     */   }
/*     */   
/*     */   public void setSelfRadius(double selfRadius) {
/* 267 */     this.selfRadius = selfRadius;
/*     */   }
/*     */   
/*     */   public double getStrength() {
/* 271 */     return this.strength;
/*     */   }
/*     */   
/*     */   public void setStrength(double strength) {
/* 275 */     this.strength = strength;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d getLastSteeringDirection() {
/* 280 */     return this.lastSteeringDirection;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\movement\steeringforces\SteeringForceAvoidCollision.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */