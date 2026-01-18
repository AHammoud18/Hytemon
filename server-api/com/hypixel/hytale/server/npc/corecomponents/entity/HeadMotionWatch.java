/*    */ package com.hypixel.hytale.server.npc.corecomponents.entity;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.server.core.asset.type.model.config.Model;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*    */ import com.hypixel.hytale.server.core.modules.physics.util.PhysicsMath;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderHeadMotionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.entity.builders.BuilderHeadMotionWatch;
/*    */ import com.hypixel.hytale.server.npc.movement.Steering;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.IPositionProvider;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class HeadMotionWatch extends HeadMotionBase {
/* 21 */   protected static final ComponentType<EntityStore, TransformComponent> TRANSFORM_COMPONENT_TYPE = TransformComponent.getComponentType();
/* 22 */   protected static final ComponentType<EntityStore, ModelComponent> MODEL_COMPONENT_TYPE = ModelComponent.getComponentType();
/*    */   
/*    */   protected final double relativeTurnSpeed;
/*    */   
/*    */   public HeadMotionWatch(@Nonnull BuilderHeadMotionWatch builderHeadMotionWatch, @Nonnull BuilderSupport support) {
/* 27 */     super((BuilderHeadMotionBase)builderHeadMotionWatch);
/* 28 */     this.relativeTurnSpeed = builderHeadMotionWatch.getRelativeTurnSpeed(support);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean computeSteering(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, @Nullable InfoProvider sensorInfo, double dt, @Nonnull Steering desiredSteering, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 33 */     if (sensorInfo == null || !sensorInfo.hasPosition()) {
/* 34 */       desiredSteering.clear();
/* 35 */       return true;
/*    */     } 
/*    */     
/* 38 */     IPositionProvider positionProvider = sensorInfo.getPositionProvider();
/* 39 */     Ref<EntityStore> targetRef = positionProvider.getTarget();
/*    */     
/* 41 */     double x = positionProvider.getX();
/* 42 */     double y = positionProvider.getY();
/* 43 */     double z = positionProvider.getZ();
/*    */     
/* 45 */     if (targetRef != null) {
/* 46 */       ModelComponent targetModelComponent = (ModelComponent)componentAccessor.getComponent(targetRef, MODEL_COMPONENT_TYPE);
/* 47 */       y += (targetModelComponent != null) ? targetModelComponent.getModel().getEyeHeight() : 0.0D;
/*    */     } 
/*    */     
/* 50 */     TransformComponent transformComponent = (TransformComponent)componentAccessor.getComponent(ref, TRANSFORM_COMPONENT_TYPE);
/* 51 */     assert transformComponent != null;
/*    */     
/* 53 */     ModelComponent modelComponent = (ModelComponent)componentAccessor.getComponent(ref, MODEL_COMPONENT_TYPE);
/* 54 */     assert modelComponent != null;
/* 55 */     Model model = modelComponent.getModel();
/*    */     
/* 57 */     Vector3d position = transformComponent.getPosition();
/* 58 */     x -= position.getX();
/* 59 */     y -= position.getY() + model.getEyeHeight();
/* 60 */     z -= position.getZ();
/*    */     
/* 62 */     float yaw = PhysicsMath.normalizeTurnAngle(PhysicsMath.headingFromDirection(x, z));
/* 63 */     float pitch = PhysicsMath.pitchFromDirection(x, y, z);
/*    */     
/* 65 */     desiredSteering.clearTranslation();
/* 66 */     desiredSteering.setYaw(yaw);
/* 67 */     desiredSteering.setPitch(pitch);
/* 68 */     desiredSteering.setRelativeTurnSpeed(this.relativeTurnSpeed);
/* 69 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\entity\HeadMotionWatch.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */