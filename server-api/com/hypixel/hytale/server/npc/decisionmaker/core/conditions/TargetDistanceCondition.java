/*    */ package com.hypixel.hytale.server.npc.decisionmaker.core.conditions;
/*    */ 
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.ArchetypeChunk;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.decisionmaker.core.EvaluationContext;
/*    */ import com.hypixel.hytale.server.npc.decisionmaker.core.conditions.base.ScaledCurveCondition;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TargetDistanceCondition
/*    */   extends ScaledCurveCondition
/*    */ {
/* 25 */   public static final BuilderCodec<TargetDistanceCondition> CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(TargetDistanceCondition.class, TargetDistanceCondition::new, ScaledCurveCondition.ABSTRACT_CODEC)
/* 26 */     .documentation("A scaled curve condition that returns a utility value based on the distance between the NPC and the target."))
/* 27 */     .build();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 33 */   private static final ComponentType<EntityStore, TransformComponent> TRANSFORM_COMPONENT_TYPE = TransformComponent.getComponentType();
/*    */ 
/*    */   
/*    */   protected double getInput(int selfIndex, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nullable Ref<EntityStore> target, @Nonnull CommandBuffer<EntityStore> commandBuffer, EvaluationContext context) {
/* 37 */     if (target == null || !target.isValid()) return Double.MAX_VALUE;
/*    */     
/* 39 */     TransformComponent selfTransformComponent = (TransformComponent)archetypeChunk.getComponent(selfIndex, TRANSFORM_COMPONENT_TYPE);
/* 40 */     assert selfTransformComponent != null;
/* 41 */     Vector3d selfPos = selfTransformComponent.getPosition();
/*    */     
/* 43 */     TransformComponent targetTransformComponent = (TransformComponent)commandBuffer.getComponent(target, TRANSFORM_COMPONENT_TYPE);
/* 44 */     assert targetTransformComponent != null;
/* 45 */     Vector3d targetPos = targetTransformComponent.getPosition();
/* 46 */     return selfPos.distanceTo(targetPos);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\decisionmaker\core\conditions\TargetDistanceCondition.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */