/*     */ package com.hypixel.hytale.server.npc.navigation;
/*     */ 
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.server.core.modules.physics.util.PhysicsMath;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*     */ import com.hypixel.hytale.server.npc.movement.Steering;
/*     */ import com.hypixel.hytale.server.npc.movement.controllers.MotionController;
/*     */ import com.hypixel.hytale.server.npc.movement.controllers.ProbeMoveData;
/*     */ import com.hypixel.hytale.server.npc.util.NPCPhysicsMath;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PathFollower
/*     */ {
/*     */   @Nullable
/*     */   protected IWaypoint currentWaypoint;
/*     */   protected double currentWaypointDistanceSquared;
/*     */   protected FrozenWaypoint frozenWaypoint;
/*     */   protected boolean isWaypointFrozen;
/*  31 */   protected final Vector3d lastWaypointPosition = new Vector3d();
/*  32 */   protected final Vector3d direction = new Vector3d();
/*  33 */   protected final Vector3d tempVector = new Vector3d();
/*  34 */   protected final Vector3d tempPath = new Vector3d();
/*  35 */   protected final Vector3d projection = new Vector3d();
/*  36 */   protected final Vector3d rejection = new Vector3d();
/*     */   
/*     */   protected int pathSmoothing;
/*     */   
/*     */   protected double blendHeading;
/*     */   protected double relativeSpeed;
/*     */   protected double relativeSpeedWaypoint;
/*     */   protected double waypointRadius;
/*  44 */   protected double rejectionWeight = 3.0D;
/*     */   
/*     */   protected double waypointRadiusSquared;
/*     */   protected boolean debugNodes = false;
/*     */   protected boolean shouldSmoothPath = true;
/*     */   
/*     */   public void setPathSmoothing(int pathSmoothing) {
/*  51 */     this.pathSmoothing = pathSmoothing;
/*     */   }
/*     */   
/*     */   public double getRelativeSpeed() {
/*  55 */     return this.relativeSpeed;
/*     */   }
/*     */   
/*     */   public void setRelativeSpeed(double relativeSpeed) {
/*  59 */     this.relativeSpeed = relativeSpeed;
/*     */   }
/*     */   
/*     */   public void setRelativeSpeedWaypoint(double relativeSpeedWaypoint) {
/*  63 */     this.relativeSpeedWaypoint = relativeSpeedWaypoint;
/*     */   }
/*     */   
/*     */   public void setWaypointRadius(double waypointRadius) {
/*  67 */     this.waypointRadius = waypointRadius;
/*  68 */     this.waypointRadiusSquared = waypointRadius * waypointRadius;
/*     */   }
/*     */   
/*     */   public void setDebugNodes(boolean debugNodes) {
/*  72 */     this.debugNodes = debugNodes;
/*     */   }
/*     */   
/*     */   public boolean shouldSmoothPath() {
/*  76 */     return this.shouldSmoothPath;
/*     */   }
/*     */   
/*     */   public void setRejectionWeight(double rejectionWeight) {
/*  80 */     this.rejectionWeight = rejectionWeight;
/*     */   }
/*     */   
/*     */   public void setBlendHeading(double blendHeading) {
/*  84 */     if (blendHeading < 0.0D || blendHeading > 1.0D) blendHeading = 0.0D; 
/*  85 */     this.blendHeading = blendHeading;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public IWaypoint getCurrentWaypoint() {
/*  90 */     return this.currentWaypoint;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Vector3d getCurrentWaypointPosition() {
/*  95 */     return (this.currentWaypoint == null) ? null : this.currentWaypoint.getPosition();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public IWaypoint getNextWaypoint() {
/* 100 */     return (this.currentWaypoint == null) ? null : this.currentWaypoint.next();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Vector3d getNextWaypointPosition() {
/* 105 */     IWaypoint waypoint = getNextWaypoint();
/* 106 */     return (waypoint == null) ? null : waypoint.getPosition();
/*     */   }
/*     */   
/*     */   public void setPath(IWaypoint firstWaypoint, @Nonnull Vector3d startPosition) {
/* 110 */     this.currentWaypoint = firstWaypoint;
/* 111 */     this.lastWaypointPosition.assign(startPosition);
/* 112 */     this.currentWaypointDistanceSquared = Double.MAX_VALUE;
/* 113 */     this.shouldSmoothPath = true;
/* 114 */     this.isWaypointFrozen = false;
/*     */   }
/*     */   
/*     */   public void clearPath() {
/* 118 */     this.currentWaypoint = null;
/* 119 */     this.isWaypointFrozen = false;
/*     */   }
/*     */   
/*     */   public boolean pathInFinalStage() {
/* 123 */     if (this.currentWaypoint == null) {
/* 124 */       return true;
/*     */     }
/* 126 */     if (this.currentWaypoint.next() != null) {
/* 127 */       return false;
/*     */     }
/* 129 */     freezeWaypoint();
/* 130 */     return true;
/*     */   }
/*     */   
/*     */   public boolean freezeWaypoint() {
/* 134 */     if (this.currentWaypoint == null) return false; 
/* 135 */     if (this.frozenWaypoint == null) this.frozenWaypoint = new FrozenWaypoint();
/*     */     
/* 137 */     if (this.currentWaypoint == this.frozenWaypoint) return true;
/*     */     
/* 139 */     this.frozenWaypoint.position.assign(this.currentWaypoint.getPosition());
/* 140 */     this.currentWaypoint = this.frozenWaypoint;
/* 141 */     this.isWaypointFrozen = true;
/* 142 */     return true;
/*     */   }
/*     */   
/*     */   public boolean isWaypointFrozen() {
/* 146 */     return this.isWaypointFrozen;
/*     */   }
/*     */   
/*     */   public void setWaypointFrozen(boolean waypointFrozen) {
/* 150 */     this.isWaypointFrozen = waypointFrozen;
/*     */   }
/*     */   
/*     */   public void executePath(@Nonnull Vector3d currentPosition, @Nonnull MotionController activeMotionController, @Nonnull Steering desiredSteering) {
/* 154 */     Vector3d target = getCurrentWaypointPosition();
/*     */     
/* 156 */     if (target == null)
/*     */       return; 
/* 158 */     this.tempVector.assign(target).subtract(currentPosition);
/* 159 */     double length = this.tempVector.length();
/*     */     
/* 161 */     desiredSteering.setMaxDistance(length);
/* 162 */     if (length > this.waypointRadius) {
/* 163 */       this.direction.assign(this.tempVector);
/* 164 */       computeRejection(currentPosition, target, activeMotionController);
/* 165 */       this.direction.subtract(this.rejection);
/* 166 */       desiredSteering.setTranslation(this.direction.scale(this.relativeSpeed / length));
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 171 */     if (length > 0.1D) this.direction.assign(this.tempVector); 
/* 172 */     desiredSteering.setTranslation(this.direction.scale(this.relativeSpeedWaypoint / length));
/*     */   }
/*     */   
/*     */   public void computeRejection(@Nonnull Vector3d currentPosition, @Nonnull Vector3d target, @Nonnull MotionController activeMotionController) {
/* 176 */     this.tempPath.assign(target).subtract(this.lastWaypointPosition).scale(activeMotionController.getComponentSelector());
/* 177 */     this.tempVector.assign(currentPosition).subtract(this.lastWaypointPosition).scale(activeMotionController.getComponentSelector());
/*     */     
/* 179 */     double dotDD = this.tempPath.squaredLength();
/* 180 */     double dotDP = this.tempPath.dot(this.tempVector);
/*     */     
/* 182 */     this.projection.assign(this.tempPath).scale(dotDP / dotDD);
/* 183 */     this.rejection.assign(this.tempVector).subtract(this.projection).scale(this.rejectionWeight);
/*     */   }
/*     */   public boolean updateCurrentTarget(@Nonnull Vector3d entityPosition, @Nonnull MotionController motionController) {
/*     */     boolean reachedWaypoint;
/* 187 */     if (this.currentWaypoint == null) return false;
/*     */     
/* 189 */     Vector3d waypointPosition = this.currentWaypoint.getPosition();
/*     */ 
/*     */     
/* 192 */     double distanceSquared = motionController.waypointDistanceSquared(waypointPosition, entityPosition);
/* 193 */     double projectionLength = 0.0D;
/*     */ 
/*     */     
/* 196 */     if (distanceSquared <= 1.0000000000000002E-10D || (distanceSquared < 0.01D && distanceSquared > this.currentWaypointDistanceSquared)) {
/* 197 */       reachedWaypoint = true;
/*     */     } else {
/* 199 */       projectionLength = NPCPhysicsMath.dotProduct(waypointPosition, this.lastWaypointPosition, entityPosition, motionController.getComponentSelector());
/* 200 */       reachedWaypoint = (projectionLength < 0.0D);
/*     */     } 
/*     */     
/* 203 */     this.currentWaypointDistanceSquared = distanceSquared;
/*     */     
/* 205 */     if (this.debugNodes) {
/* 206 */       NPCPlugin.get().getLogger().at(Level.INFO).log("=== Target len=%s before=%s targetdist=%s  proj=%s pos=%s tgt=%s", 
/* 207 */           Integer.valueOf(this.currentWaypoint.getLength()), Boolean.valueOf(!reachedWaypoint), Double.valueOf(Math.sqrt(distanceSquared)), Double.valueOf(projectionLength), 
/* 208 */           Vector3d.formatShortString(entityPosition), Vector3d.formatShortString(waypointPosition));
/*     */     }
/*     */     
/* 211 */     if (reachedWaypoint) {
/* 212 */       this.lastWaypointPosition.assign(waypointPosition);
/* 213 */       this.currentWaypoint = this.currentWaypoint.next();
/* 214 */       if (this.currentWaypoint == null) {
/* 215 */         this.isWaypointFrozen = false;
/* 216 */         return false;
/*     */       } 
/*     */       
/* 219 */       this.currentWaypointDistanceSquared = Double.MAX_VALUE;
/* 220 */       this.shouldSmoothPath = true;
/* 221 */       waypointPosition = this.currentWaypoint.getPosition();
/*     */       
/* 223 */       if (this.blendHeading > 0.0D) {
/* 224 */         distanceSquared = motionController.waypointDistanceSquared(waypointPosition, entityPosition);
/*     */       }
/*     */     } 
/*     */     
/* 228 */     motionController.requirePreciseMovement(waypointPosition);
/*     */ 
/*     */     
/* 231 */     if (this.blendHeading <= 0.0D) return true;
/*     */     
/* 233 */     motionController.enableHeadingBlending();
/*     */     
/* 235 */     if (distanceSquared > this.waypointRadiusSquared) return true;
/*     */     
/* 237 */     IWaypoint nextWaypoint = getNextWaypoint();
/* 238 */     if (nextWaypoint == null) return true;
/*     */     
/* 240 */     this.tempVector.assign(nextWaypoint.getPosition()).subtract(waypointPosition);
/* 241 */     distanceSquared = NPCPhysicsMath.projectedLengthSquared(this.tempVector, motionController.getComponentSelector());
/* 242 */     if (distanceSquared < 0.001D) return true;
/*     */     
/* 244 */     float yaw = PhysicsMath.headingFromDirection(this.tempVector.x, this.tempVector.z);
/*     */     
/* 246 */     motionController.enableHeadingBlending(yaw, waypointPosition, this.blendHeading);
/* 247 */     return true;
/*     */   } public void smoothPath(@Nonnull Ref<EntityStore> ref, @Nonnull Vector3d position, @Nonnull MotionController motionController, @Nonnull ProbeMoveData probeMoveData, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*     */     IWaypoint startNode;
/*     */     int skip;
/* 251 */     this.shouldSmoothPath = false;
/*     */ 
/*     */     
/* 254 */     if (this.pathSmoothing <= 0)
/*     */       return; 
/* 256 */     IWaypoint node = this.currentWaypoint;
/* 257 */     if (node == null)
/*     */       return; 
/* 259 */     int startLength = node.getLength();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     do {
/* 265 */       startNode = node;
/* 266 */       int length = startNode.getLength();
/* 267 */       if (length == 1) {
/*     */         
/* 269 */         int i = 0;
/*     */         break;
/*     */       } 
/* 272 */       skip = Math.min(this.pathSmoothing, length - 1);
/* 273 */       node = startNode.advance(skip);
/* 274 */     } while (canMoveTo(ref, motionController, position, node.getPosition(), probeMoveData, componentAccessor));
/*     */ 
/*     */     
/* 277 */     while (skip > 1) {
/* 278 */       int middleSkip = skip / 2;
/* 279 */       IWaypoint middleNode = startNode.advance(middleSkip);
/* 280 */       if (canMoveTo(ref, motionController, position, middleNode.getPosition(), probeMoveData, componentAccessor)) {
/* 281 */         startNode = middleNode;
/* 282 */         skip -= middleSkip; continue;
/*     */       } 
/* 284 */       skip = middleSkip;
/*     */     } 
/*     */ 
/*     */     
/* 288 */     if (this.debugNodes) {
/* 289 */       int l = startNode.getLength();
/* 290 */       NPCPlugin.get().getLogger().at(Level.INFO).log("=== New Target len=%s skipped=%s pos=%s tgt=%s dist=%s", 
/* 291 */           Integer.valueOf(l), Integer.valueOf(startLength - l), Vector3d.formatShortString(position), Vector3d.formatShortString(startNode.getPosition()), Double.valueOf(position.distanceTo(startNode.getPosition())));
/*     */     } 
/*     */     
/* 294 */     this.currentWaypoint = startNode;
/* 295 */     this.currentWaypointDistanceSquared = Double.MAX_VALUE;
/*     */   }
/*     */   
/*     */   protected boolean canMoveTo(@Nonnull Ref<EntityStore> ref, @Nonnull MotionController motionController, @Nonnull Vector3d position, @Nonnull Vector3d targetPosition, @Nonnull ProbeMoveData probeMoveData, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 299 */     probeMoveData.setPosition(position).setTargetPosition(targetPosition);
/*     */     
/* 301 */     return probeMoveData.canMoveTo(ref, motionController, 9.999999994736442E-8D, 0.5D, componentAccessor);
/*     */   }
/*     */   
/*     */   private static class FrozenWaypoint implements IWaypoint {
/* 305 */     protected final Vector3d position = new Vector3d();
/*     */ 
/*     */     
/*     */     public int getLength() {
/* 309 */       return 1;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Vector3d getPosition() {
/* 315 */       return this.position;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public IWaypoint advance(int skip) {
/* 321 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public IWaypoint next() {
/* 327 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\navigation\PathFollower.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */