/*    */ package com.hypixel.hytale.server.npc.corecomponents.movement;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.movement.builders.BuilderBodyMotionFind;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.movement.builders.BuilderBodyMotionLand;
/*    */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*    */ import com.hypixel.hytale.server.npc.movement.Steering;
/*    */ import com.hypixel.hytale.server.npc.movement.controllers.MotionController;
/*    */ import com.hypixel.hytale.server.npc.movement.controllers.MotionControllerFly;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BodyMotionLand
/*    */   extends BodyMotionFind
/*    */ {
/*    */   protected final double goalLenience;
/*    */   protected final double goalLenienceSquared;
/*    */   
/*    */   public BodyMotionLand(@Nonnull BuilderBodyMotionLand builderMotionLand, @Nonnull BuilderSupport support) {
/* 30 */     super((BuilderBodyMotionFind)builderMotionLand, support);
/* 31 */     this.goalLenience = builderMotionLand.getGoalLenience(support);
/* 32 */     this.goalLenienceSquared = this.goalLenience * this.goalLenience;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean computeSteering(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, @Nullable InfoProvider infoProvider, double dt, @Nonnull Steering desiredSteering, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 37 */     boolean result = super.computeSteering(ref, role, infoProvider, dt, desiredSteering, componentAccessor);
/*    */     
/* 39 */     TransformComponent transformComponent = (TransformComponent)componentAccessor.getComponent(ref, TransformComponent.getComponentType());
/* 40 */     assert transformComponent != null;
/*    */     
/* 42 */     if (isGoalReached(ref, role.getActiveMotionController(), transformComponent.getPosition(), componentAccessor)) {
/* 43 */       NPCEntity npcComponent = (NPCEntity)componentAccessor.getComponent(ref, NPCEntity.getComponentType());
/* 44 */       assert npcComponent != null;
/*    */ 
/*    */ 
/*    */       
/* 48 */       role.setActiveMotionController(ref, npcComponent, "Walk", componentAccessor);
/* 49 */       return false;
/*    */     } 
/*    */     
/* 52 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canComputeMotion(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, @Nullable InfoProvider positionProvider, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 57 */     MotionController activeMotionController = role.getActiveMotionController();
/* 58 */     return (activeMotionController.matchesType(MotionControllerFly.class) && !activeMotionController.isObstructed() && super
/* 59 */       .canComputeMotion(ref, role, positionProvider, componentAccessor));
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean isGoalReached(@Nonnull Ref<EntityStore> ref, @Nonnull MotionController motionController, @Nonnull Vector3d position, @Nonnull Vector3d targetPosition, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 64 */     double differenceY = targetPosition.y - position.y;
/* 65 */     if (differenceY < this.heightDifferenceMin || differenceY > this.heightDifferenceMax) return false;
/*    */     
/* 67 */     double waypointDistanceSquared = motionController.waypointDistanceSquared(position, targetPosition);
/* 68 */     if (waypointDistanceSquared > this.effectiveDistanceSquared && waypointDistanceSquared > this.goalLenienceSquared) return false;
/*    */     
/* 70 */     return (!this.reachable || canReachTarget(ref, motionController, position, targetPosition, componentAccessor));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\movement\BodyMotionLand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */