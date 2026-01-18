/*    */ package com.hypixel.hytale.server.npc.corecomponents.interaction;
/*    */ import com.hypixel.hytale.component.Archetype;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.server.core.asset.type.attitude.Attitude;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.HeadRotation;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*    */ import com.hypixel.hytale.server.core.modules.entity.damage.DeathComponent;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.SensorBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderSensorBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.interaction.builders.BuilderSensorCanInteract;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import java.util.EnumSet;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class SensorCanInteract extends SensorBase {
/*    */   protected final float viewCone;
/*    */   
/*    */   public SensorCanInteract(@Nonnull BuilderSensorCanInteract builder, @Nonnull BuilderSupport support) {
/* 25 */     super((BuilderSensorBase)builder);
/* 26 */     this.viewCone = builder.getViewSectorRadians(support);
/* 27 */     this.attitudes = builder.getAttitudes(support);
/*    */   }
/*    */   protected final EnumSet<Attitude> attitudes;
/*    */   
/*    */   public boolean matches(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, double dt, @Nonnull Store<EntityStore> store) {
/* 32 */     if (!super.matches(ref, role, dt, store)) return false;
/*    */     
/* 34 */     Ref<EntityStore> targetRef = role.getStateSupport().getInteractionIterationTarget();
/* 35 */     if (targetRef == null) return false;
/*    */     
/* 37 */     Archetype<EntityStore> targetArchetype = store.getArchetype(targetRef);
/* 38 */     if (targetArchetype.contains(DeathComponent.getComponentType())) return false;
/*    */     
/* 40 */     TransformComponent targetTransformComponent = (TransformComponent)store.getComponent(targetRef, TransformComponent.getComponentType());
/* 41 */     if (targetTransformComponent == null) return false;
/*    */     
/* 43 */     Vector3d targetPosition = targetTransformComponent.getPosition();
/*    */     
/* 45 */     if (this.viewCone > 0.0F) {
/* 46 */       TransformComponent transformComponent = (TransformComponent)store.getComponent(ref, TransformComponent.getComponentType());
/* 47 */       assert transformComponent != null;
/*    */       
/* 49 */       HeadRotation headRotationComponent = (HeadRotation)store.getComponent(ref, HeadRotation.getComponentType());
/* 50 */       assert headRotationComponent != null;
/*    */       
/* 52 */       Vector3d position = transformComponent.getPosition();
/* 53 */       float headRotationYaw = headRotationComponent.getRotation().getYaw();
/* 54 */       if (!NPCPhysicsMath.inViewSector(position.getX(), position.getZ(), headRotationYaw, this.viewCone, targetPosition.getX(), targetPosition.getZ())) {
/* 55 */         return false;
/*    */       }
/*    */     } 
/*    */     
/* 59 */     Attitude attitude = role.getWorldSupport().getAttitude(ref, targetRef, (ComponentAccessor)store);
/* 60 */     return this.attitudes.contains(attitude);
/*    */   }
/*    */ 
/*    */   
/*    */   public void registerWithSupport(@Nonnull Role role) {
/* 65 */     role.getWorldSupport().requireAttitudeCache();
/*    */   }
/*    */ 
/*    */   
/*    */   public InfoProvider getSensorInfo() {
/* 70 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\interaction\SensorCanInteract.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */