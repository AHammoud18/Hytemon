/*    */ package com.hypixel.hytale.server.npc.corecomponents.entity.filters;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.HeadRotation;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.EntityFilterBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.entity.filters.builders.BuilderEntityFilterViewSector;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.util.NPCPhysicsMath;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class EntityFilterViewSector
/*    */   extends EntityFilterBase {
/*    */   public static final int COST = 300;
/* 20 */   protected static final ComponentType<EntityStore, TransformComponent> TRANSFORM_COMPONENT_TYPE = TransformComponent.getComponentType();
/*    */   
/*    */   protected final float viewCone;
/*    */   
/*    */   public EntityFilterViewSector(@Nonnull BuilderEntityFilterViewSector builder, @Nonnull BuilderSupport support) {
/* 25 */     this.viewCone = builder.getViewSectorRadians(support);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matchesEntity(@Nonnull Ref<EntityStore> ref, @Nonnull Ref<EntityStore> targetRef, @Nonnull Role role, @Nonnull Store<EntityStore> store) {
/* 30 */     TransformComponent transformComponent = (TransformComponent)store.getComponent(ref, TRANSFORM_COMPONENT_TYPE);
/* 31 */     assert transformComponent != null;
/* 32 */     Vector3d position = transformComponent.getPosition();
/*    */     
/* 34 */     HeadRotation headRotationComponent = (HeadRotation)store.getComponent(ref, HeadRotation.getComponentType());
/* 35 */     assert headRotationComponent != null;
/*    */     
/* 37 */     TransformComponent targetTransformComponent = (TransformComponent)store.getComponent(targetRef, TRANSFORM_COMPONENT_TYPE);
/* 38 */     assert targetTransformComponent != null;
/* 39 */     Vector3d targetPosition = targetTransformComponent.getPosition();
/*    */     
/* 41 */     return NPCPhysicsMath.inViewSector(position.getX(), position.getZ(), headRotationComponent.getRotation().getYaw(), this.viewCone, targetPosition.getX(), targetPosition.getZ());
/*    */   }
/*    */ 
/*    */   
/*    */   public int cost() {
/* 46 */     return 300;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\entity\filters\EntityFilterViewSector.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */