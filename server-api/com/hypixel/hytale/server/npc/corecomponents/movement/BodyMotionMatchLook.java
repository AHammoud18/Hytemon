/*    */ package com.hypixel.hytale.server.npc.corecomponents.movement;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.HeadRotation;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.BodyMotionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderBodyMotionBase;
/*    */ import com.hypixel.hytale.server.npc.movement.Steering;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class BodyMotionMatchLook
/*    */   extends BodyMotionBase {
/*    */   public BodyMotionMatchLook(@Nonnull BuilderBodyMotionBase builderMotionBase) {
/* 18 */     super(builderMotionBase);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean computeSteering(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, @Nullable InfoProvider sensorInfo, double dt, @Nonnull Steering desiredSteering, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 23 */     HeadRotation headRotationComponent = (HeadRotation)componentAccessor.getComponent(ref, HeadRotation.getComponentType());
/* 24 */     assert headRotationComponent != null;
/*    */ 
/*    */     
/* 27 */     float headYaw = headRotationComponent.getRotation().getYaw();
/* 28 */     desiredSteering.setYaw(headYaw);
/* 29 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\movement\BodyMotionMatchLook.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */