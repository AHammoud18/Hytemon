/*     */ package com.hypixel.hytale.server.npc.movement;
/*     */ 
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.HeadRotation;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.physics.component.Velocity;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.util.NPCPhysicsMath;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GroupSteeringAccumulator
/*     */ {
/*     */   @Nonnull
/*  22 */   private static final ComponentType<EntityStore, TransformComponent> TRANSFORM_COMPONENT_TYPE = TransformComponent.getComponentType();
/*     */   
/*  24 */   private final Vector3d sumOfVelocities = new Vector3d();
/*  25 */   private final Vector3d sumOfDistances = new Vector3d();
/*  26 */   private final Vector3d sumOfPositions = new Vector3d();
/*  27 */   private final Vector3d temp = new Vector3d();
/*     */   private int count;
/*     */   private double x;
/*     */   private double y;
/*     */   private double z;
/*     */   private double xViewDirection;
/*     */   private double yViewDirection;
/*     */   private double zViewDirection;
/*  35 */   private Vector3d componentSelector = Vector3d.ALL_ONES;
/*  36 */   private double maxRangeSquared = Double.MAX_VALUE;
/*  37 */   private double maxDistance = Double.MAX_VALUE;
/*  38 */   private float collisionViewHalfAngleCosine = 1.0F;
/*     */   
/*     */   public void begin(double x, double y, double z, double xViewDirection, double yViewDirection, double zViewDirection) {
/*  41 */     this.x = x;
/*  42 */     this.y = y;
/*  43 */     this.z = z;
/*  44 */     this.xViewDirection = xViewDirection;
/*  45 */     this.yViewDirection = yViewDirection;
/*  46 */     this.zViewDirection = zViewDirection;
/*  47 */     this.sumOfDistances.assign(0.0D);
/*  48 */     this.sumOfPositions.assign(0.0D);
/*  49 */     this.sumOfVelocities.assign(0.0D);
/*  50 */     this.count = 0;
/*     */   }
/*     */   
/*     */   public void begin(@Nonnull Ref<EntityStore> ref, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  54 */     HeadRotation headRotationComponent = (HeadRotation)componentAccessor.getComponent(ref, HeadRotation.getComponentType());
/*  55 */     assert headRotationComponent != null;
/*     */     
/*  57 */     Vector3f headRotation = headRotationComponent.getRotation();
/*     */ 
/*     */     
/*  60 */     NPCPhysicsMath.getViewDirection(headRotation, this.temp);
/*  61 */     this.temp.normalize();
/*     */     
/*  63 */     TransformComponent transformComponent = (TransformComponent)componentAccessor.getComponent(ref, TRANSFORM_COMPONENT_TYPE);
/*  64 */     assert transformComponent != null;
/*     */     
/*  66 */     Vector3d position = transformComponent.getPosition();
/*  67 */     begin(position.getX(), position.getY(), position.getZ(), this.temp.x, this.temp.y, this.temp.z);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void processEntity(@Nonnull Ref<EntityStore> ref, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  73 */     Velocity velocityComponent = (Velocity)componentAccessor.getComponent(ref, Velocity.getComponentType());
/*  74 */     assert velocityComponent != null;
/*  75 */     Vector3d velocity = velocityComponent.getVelocity();
/*     */     
/*  77 */     TransformComponent transformComponent = (TransformComponent)componentAccessor.getComponent(ref, TRANSFORM_COMPONENT_TYPE);
/*  78 */     assert transformComponent != null;
/*  79 */     Vector3d position = transformComponent.getPosition();
/*     */     
/*  81 */     double xPosition = position.getX();
/*  82 */     double yPosition = position.getY();
/*  83 */     double zPosition = position.getZ();
/*  84 */     double dx = xPosition - this.x;
/*  85 */     double dy = yPosition - this.y;
/*  86 */     double dz = zPosition - this.z;
/*  87 */     if (NPCPhysicsMath.dotProduct(dx, dy, dz, this.componentSelector) < this.maxRangeSquared && 
/*  88 */       NPCPhysicsMath.isInViewCone(this.xViewDirection, this.yViewDirection, this.zViewDirection, this.collisionViewHalfAngleCosine, dx, dy, dz)) {
/*  89 */       this.sumOfDistances.add(dx, dy, dz);
/*  90 */       this.sumOfPositions.add(xPosition, yPosition, zPosition);
/*  91 */       this.sumOfVelocities.add(velocity);
/*  92 */       this.count++;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processEntity(@Nonnull Ref<EntityStore> ref, double distanceWeight, double positionWeight, double velocityWeight, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 101 */     TransformComponent transformComponent = (TransformComponent)componentAccessor.getComponent(ref, TRANSFORM_COMPONENT_TYPE);
/* 102 */     assert transformComponent != null;
/*     */     
/* 104 */     Vector3d position = transformComponent.getPosition();
/*     */     
/* 106 */     Velocity velocityComponent = (Velocity)componentAccessor.getComponent(ref, Velocity.getComponentType());
/* 107 */     assert velocityComponent != null;
/*     */     
/* 109 */     Vector3d velocity = velocityComponent.getVelocity();
/*     */     
/* 111 */     double dx = position.getX() - this.x;
/* 112 */     double dy = position.getY() - this.y;
/* 113 */     double dz = position.getZ() - this.z;
/*     */     
/* 115 */     double d = NPCPhysicsMath.dotProduct(dx, dy, dz, this.componentSelector);
/* 116 */     if (d < this.maxRangeSquared && 
/* 117 */       NPCPhysicsMath.isInViewCone(this.xViewDirection, this.yViewDirection, this.zViewDirection, this.collisionViewHalfAngleCosine, dx, dy, dz)) {
/* 118 */       d = 1.0D - Math.sqrt(d) / this.maxDistance;
/*     */       
/* 120 */       double w = Math.pow(d, distanceWeight);
/* 121 */       this.sumOfDistances.add(dx * w, dy * w, dz * w);
/* 122 */       w = Math.pow(d, positionWeight);
/* 123 */       this.sumOfPositions.addScaled(position, w);
/* 124 */       w = Math.pow(d, velocityWeight);
/* 125 */       this.sumOfVelocities.addScaled(velocity, w);
/* 126 */       this.count++;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void end() {
/* 131 */     if (this.count > 0) {
/* 132 */       double scale = 1.0D / this.count;
/* 133 */       this.sumOfDistances.scale(scale).scale(this.componentSelector);
/* 134 */       this.sumOfPositions.scale(scale).scale(this.componentSelector);
/* 135 */       this.sumOfVelocities.scale(scale).scale(this.componentSelector);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setComponentSelector(Vector3d componentSelector) {
/* 140 */     this.componentSelector = componentSelector;
/*     */   }
/*     */   
/*     */   public void setMaxRange(double maxRange) {
/* 144 */     this.maxRangeSquared = maxRange * maxRange;
/* 145 */     this.maxDistance = maxRange;
/*     */   }
/*     */   
/*     */   public void setViewConeHalfAngleCosine(float collisionViewHalfAngleCosine) {
/* 149 */     this.collisionViewHalfAngleCosine = collisionViewHalfAngleCosine;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d getSumOfVelocities() {
/* 154 */     return this.sumOfVelocities;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d getSumOfDistances() {
/* 159 */     return this.sumOfDistances;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d getSumOfPositions() {
/* 164 */     return this.sumOfPositions;
/*     */   }
/*     */   
/*     */   public int getCount() {
/* 168 */     return this.count;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\movement\GroupSteeringAccumulator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */