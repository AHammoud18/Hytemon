/*    */ package com.hypixel.hytale.server.npc.corecomponents.movement;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.server.core.entity.group.EntityGroup;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.flock.FlockPlugin;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.movement.builders.BuilderBodyMotionWanderBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.movement.builders.BuilderBodyMotionWanderInCircle;
/*    */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*    */ import com.hypixel.hytale.server.npc.movement.controllers.MotionController;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.util.NPCPhysicsMath;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class BodyMotionWanderInCircle
/*    */   extends BodyMotionWanderBase {
/*    */   protected final double radius;
/* 23 */   protected final Vector3d referencePoint = new Vector3d(); protected final boolean flock; protected final boolean useSphere;
/*    */   
/*    */   public BodyMotionWanderInCircle(@Nonnull BuilderBodyMotionWanderInCircle builder, @Nonnull BuilderSupport builderSupport) {
/* 26 */     super((BuilderBodyMotionWanderBase)builder, builderSupport);
/* 27 */     this.radius = builder.getRadius(builderSupport);
/* 28 */     this.flock = builder.isFlock();
/* 29 */     this.useSphere = builder.isUseSphere();
/*    */   }
/*    */ 
/*    */   
/*    */   protected double constrainMove(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, @Nonnull Vector3d probePosition, @Nonnull Vector3d targetPosition, double moveDist, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 34 */     Vector3d referencePoint = getReferencePoint(ref, componentAccessor);
/* 35 */     double r2 = this.radius * this.radius;
/* 36 */     MotionController activeMotionController = role.getActiveMotionController();
/* 37 */     if (this.useSphere) {
/* 38 */       double d1 = activeMotionController.waypointDistanceSquared(targetPosition, referencePoint);
/*    */       
/* 40 */       if (d1 <= r2)
/*    */       {
/* 42 */         return moveDist;
/*    */       }
/* 44 */       double d2 = activeMotionController.waypointDistanceSquared(probePosition, referencePoint);
/* 45 */       if (d2 >= r2) {
/* 46 */         return (d1 <= d2) ? moveDist : 0.0D;
/*    */       }
/* 48 */       return NPCPhysicsMath.intersectLineSphereLerp(referencePoint, this.radius, probePosition, targetPosition, activeMotionController.getComponentSelector()) * moveDist;
/*    */     } 
/* 50 */     Vector3d n = activeMotionController.getWorldNormal();
/*    */     
/* 52 */     double endDist2 = NPCPhysicsMath.squaredDistProjected(targetPosition.getX(), targetPosition.getY(), targetPosition.getZ(), referencePoint, n);
/* 53 */     if (endDist2 <= r2) {
/* 54 */       return moveDist;
/*    */     }
/*    */     
/* 57 */     double startDist2 = NPCPhysicsMath.squaredDistProjected(probePosition.getX(), probePosition.getY(), probePosition.getZ(), referencePoint, n);
/* 58 */     if (startDist2 >= r2) {
/* 59 */       return (endDist2 <= startDist2) ? moveDist : 0.0D;
/*    */     }
/*    */     
/* 62 */     return moveDist * Math.max(0.0D, NPCPhysicsMath.rayCircleIntersect(probePosition, targetPosition, referencePoint, this.radius, n));
/*    */   }
/*    */ 
/*    */   
/*    */   protected Vector3d getReferencePoint(@Nonnull Ref<EntityStore> ref, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 67 */     if (this.flock) {
/* 68 */       World world = ((EntityStore)componentAccessor.getExternalData()).getWorld();
/*    */       
/* 70 */       TransformComponent entityTransformComponent = (TransformComponent)componentAccessor.getComponent(ref, TransformComponent.getComponentType());
/* 71 */       assert entityTransformComponent != null;
/*    */       
/* 73 */       Vector3d entityPosition = entityTransformComponent.getPosition();
/*    */       
/* 75 */       Ref<EntityStore> flockReference = FlockPlugin.getFlockReference(ref, componentAccessor);
/* 76 */       if (flockReference != null) {
/* 77 */         EntityGroup entityGroupComponent = (EntityGroup)componentAccessor.getComponent(flockReference, EntityGroup.getComponentType());
/* 78 */         assert entityGroupComponent != null;
/*    */         
/* 80 */         Ref<EntityStore> leaderRef = entityGroupComponent.getLeaderRef();
/* 81 */         if (leaderRef.isValid()) {
/* 82 */           TransformComponent leaderTransformComponent = (TransformComponent)componentAccessor.getComponent(leaderRef, TransformComponent.getComponentType());
/* 83 */           assert leaderTransformComponent != null;
/*    */           
/* 85 */           Vector3d leaderPosition = leaderTransformComponent.getPosition();
/* 86 */           this.referencePoint.assign(leaderPosition.getX(), leaderPosition.getY(), leaderPosition.getZ());
/* 87 */           return this.referencePoint;
/*    */         } 
/*    */       } 
/* 90 */       this.referencePoint.assign(entityPosition);
/* 91 */       return this.referencePoint;
/*    */     } 
/* 93 */     NPCEntity npcComponent = (NPCEntity)componentAccessor.getComponent(ref, NPCEntity.getComponentType());
/* 94 */     assert npcComponent != null;
/* 95 */     return npcComponent.getLeashPoint();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\movement\BodyMotionWanderInCircle.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */