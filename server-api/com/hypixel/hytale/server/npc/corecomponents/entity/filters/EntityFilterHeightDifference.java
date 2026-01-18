/*    */ package com.hypixel.hytale.server.npc.corecomponents.entity.filters;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.math.shape.Box;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.BoundingBox;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.EntityFilterBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.entity.filters.builders.BuilderEntityFilterHeightDifference;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class EntityFilterHeightDifference
/*    */   extends EntityFilterBase {
/*    */   public static final int COST = 200;
/* 21 */   protected static final ComponentType<EntityStore, TransformComponent> TRANSFORM_COMPONENT_TYPE = TransformComponent.getComponentType();
/* 22 */   protected static final ComponentType<EntityStore, ModelComponent> MODEL_COMPONENT_TYPE = ModelComponent.getComponentType();
/* 23 */   protected static final ComponentType<EntityStore, BoundingBox> BOUNDING_BOX_COMPONENT_TYPE = BoundingBox.getComponentType();
/*    */   
/*    */   protected final double minHeightDifference;
/*    */   protected final double maxHeightDifference;
/*    */   protected final boolean useEyePosition;
/*    */   
/*    */   public EntityFilterHeightDifference(@Nonnull BuilderEntityFilterHeightDifference builder, @Nonnull BuilderSupport support) {
/* 30 */     double[] heightDifference = builder.getHeightDifference(support);
/* 31 */     this.minHeightDifference = heightDifference[0];
/* 32 */     this.maxHeightDifference = heightDifference[1];
/* 33 */     this.useEyePosition = builder.isUseEyePosition(support);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matchesEntity(@Nonnull Ref<EntityStore> ref, @Nonnull Ref<EntityStore> targetRef, @Nonnull Role role, @Nonnull Store<EntityStore> store) {
/* 38 */     BoundingBox targetBoundingBoxComponent = (BoundingBox)store.getComponent(targetRef, BOUNDING_BOX_COMPONENT_TYPE);
/* 39 */     if (targetBoundingBoxComponent == null) return false;
/*    */     
/* 41 */     TransformComponent targetTransformComponent = (TransformComponent)store.getComponent(targetRef, TRANSFORM_COMPONENT_TYPE);
/* 42 */     assert targetTransformComponent != null;
/*    */     
/* 44 */     ModelComponent targetModelComponent = (ModelComponent)store.getComponent(targetRef, MODEL_COMPONENT_TYPE);
/* 45 */     float targetEyeHeight = (targetModelComponent != null) ? targetModelComponent.getModel().getEyeHeight() : 0.0F;
/*    */     
/* 47 */     Vector3d targetPosition = targetTransformComponent.getPosition();
/* 48 */     double targetY = targetPosition.y;
/* 49 */     Box box = targetBoundingBoxComponent.getBoundingBox();
/* 50 */     double minY = targetY + box.min.y;
/* 51 */     double maxY = targetY + box.max.y;
/*    */     
/* 53 */     TransformComponent transformComponent = (TransformComponent)store.getComponent(ref, TRANSFORM_COMPONENT_TYPE);
/* 54 */     assert transformComponent != null;
/*    */     
/* 56 */     double posY = (transformComponent.getPosition()).y;
/* 57 */     if (this.useEyePosition) {
/* 58 */       posY += targetEyeHeight;
/*    */     }
/* 60 */     return (minY - posY < this.maxHeightDifference && maxY - posY > this.minHeightDifference);
/*    */   }
/*    */ 
/*    */   
/*    */   public int cost() {
/* 65 */     return 200;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\entity\filters\EntityFilterHeightDifference.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */