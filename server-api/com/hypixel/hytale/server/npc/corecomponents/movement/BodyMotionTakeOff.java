/*    */ package com.hypixel.hytale.server.npc.corecomponents.movement;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.BodyMotionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderBodyMotionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.movement.builders.BuilderBodyMotionTakeOff;
/*    */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*    */ import com.hypixel.hytale.server.npc.movement.Steering;
/*    */ import com.hypixel.hytale.server.npc.movement.controllers.MotionControllerFly;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class BodyMotionTakeOff
/*    */   extends BodyMotionBase {
/*    */   public BodyMotionTakeOff(@Nonnull BuilderBodyMotionTakeOff builderBodyMotionTakeOff) {
/* 22 */     super((BuilderBodyMotionBase)builderBodyMotionTakeOff);
/* 23 */     this.jumpSpeed = builderBodyMotionTakeOff.getJumpSpeed();
/*    */   }
/*    */   
/*    */   protected final double jumpSpeed;
/*    */   
/*    */   public boolean computeSteering(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, @Nullable InfoProvider sensorInfo, double dt, @Nonnull Steering desiredSteering, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 29 */     if (!role.getActiveMotionController().matchesType(MotionControllerFly.class)) {
/* 30 */       TransformComponent transformComponent = (TransformComponent)componentAccessor.getComponent(ref, TransformComponent.getComponentType());
/* 31 */       assert transformComponent != null;
/*    */       
/* 33 */       NPCEntity npcComponent = (NPCEntity)componentAccessor.getComponent(ref, NPCEntity.getComponentType());
/* 34 */       assert npcComponent != null;
/*    */       
/* 36 */       role.setActiveMotionController(ref, npcComponent, "Fly", componentAccessor);
/* 37 */       Vector3d position = transformComponent.getPosition();
/* 38 */       position.setY(transformComponent.getPosition().getY() + 0.1D);
/* 39 */       ((MotionControllerFly)role.getActiveMotionController()).takeOff(ref, this.jumpSpeed, componentAccessor);
/*    */     } 
/*    */     
/* 42 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\movement\BodyMotionTakeOff.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */