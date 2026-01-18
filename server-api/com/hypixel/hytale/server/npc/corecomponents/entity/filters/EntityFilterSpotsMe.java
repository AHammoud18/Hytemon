/*    */ package com.hypixel.hytale.server.npc.corecomponents.entity.filters;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.math.util.TrigMathUtil;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.HeadRotation;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.EntityFilterBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.entity.filters.builders.BuilderEntityFilterSpotsMe;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.util.NPCPhysicsMath;
/*    */ import com.hypixel.hytale.server.npc.util.ViewTest;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class EntityFilterSpotsMe
/*    */   extends EntityFilterBase {
/*    */   public static final int COST = 400;
/* 22 */   protected static final ComponentType<EntityStore, TransformComponent> TRANSFORM_COMPONENT_TYPE = TransformComponent.getComponentType();
/*    */   
/*    */   protected final float viewAngle;
/*    */   
/*    */   protected final boolean testLineOfSight;
/*    */   protected final ViewTest viewTest;
/* 28 */   protected final Vector3d view = new Vector3d();
/*    */   
/*    */   public EntityFilterSpotsMe(@Nonnull BuilderEntityFilterSpotsMe builder) {
/* 31 */     this.viewAngle = builder.getViewAngle();
/* 32 */     this.testLineOfSight = builder.testLineOfSight();
/* 33 */     this.viewTest = builder.getViewTest();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matchesEntity(@Nonnull Ref<EntityStore> ref, @Nonnull Ref<EntityStore> targetRef, @Nonnull Role role, @Nonnull Store<EntityStore> store) {
/* 38 */     return (inViewTest(targetRef, ref, store) && (!this.testLineOfSight || role.getPositionCache().hasInverseLineOfSight(ref, targetRef, (ComponentAccessor)store)));
/*    */   }
/*    */ 
/*    */   
/*    */   public int cost() {
/* 43 */     return 400;
/*    */   }
/*    */   
/*    */   protected boolean inViewTest(@Nonnull Ref<EntityStore> ref, @Nonnull Ref<EntityStore> targetRef, @Nonnull Store<EntityStore> store) {
/* 47 */     switch (this.viewTest) { case VIEW_CONE: case VIEW_SECTOR:  }  return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean inViewSector(@Nonnull Ref<EntityStore> ref, @Nonnull Ref<EntityStore> targetRef, @Nonnull Store<EntityStore> store) {
/* 55 */     TransformComponent transformComponent = (TransformComponent)store.getComponent(ref, TRANSFORM_COMPONENT_TYPE);
/* 56 */     assert transformComponent != null;
/*    */     
/* 58 */     Vector3d position = transformComponent.getPosition();
/*    */     
/* 60 */     TransformComponent targetTransformComponent = (TransformComponent)store.getComponent(targetRef, TRANSFORM_COMPONENT_TYPE);
/* 61 */     assert targetTransformComponent != null;
/*    */     
/* 63 */     Vector3d targetPosition = targetTransformComponent.getPosition();
/*    */     
/* 65 */     HeadRotation headRotationComponent = (HeadRotation)store.getComponent(ref, HeadRotation.getComponentType());
/* 66 */     assert headRotationComponent != null;
/*    */     
/* 68 */     return NPCPhysicsMath.inViewSector(position.getX(), position.getZ(), headRotationComponent.getRotation().getYaw(), this.viewAngle, targetPosition
/* 69 */         .getX(), targetPosition.getZ());
/*    */   }
/*    */   
/*    */   protected boolean inViewCone(@Nonnull Ref<EntityStore> ref, @Nonnull Ref<EntityStore> targetRef, @Nonnull Store<EntityStore> store) {
/* 73 */     TransformComponent transformComponent = (TransformComponent)store.getComponent(ref, TRANSFORM_COMPONENT_TYPE);
/* 74 */     assert transformComponent != null;
/*    */     
/* 76 */     HeadRotation headRotationComponent = (HeadRotation)store.getComponent(ref, HeadRotation.getComponentType());
/* 77 */     assert headRotationComponent != null;
/*    */     
/* 79 */     TransformComponent targetTransformComponent = (TransformComponent)store.getComponent(targetRef, TRANSFORM_COMPONENT_TYPE);
/* 80 */     assert targetTransformComponent != null;
/*    */     
/* 82 */     NPCPhysicsMath.getViewDirection(headRotationComponent.getRotation(), this.view);
/* 83 */     this.view.normalize();
/*    */     
/* 85 */     return NPCPhysicsMath.isInViewCone(transformComponent
/* 86 */         .getPosition(), this.view, TrigMathUtil.cos(this.viewAngle / 2.0F), targetTransformComponent
/* 87 */         .getPosition());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\entity\filters\EntityFilterSpotsMe.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */