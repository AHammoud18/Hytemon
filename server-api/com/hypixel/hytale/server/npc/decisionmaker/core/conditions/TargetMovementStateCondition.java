/*    */ package com.hypixel.hytale.server.npc.decisionmaker.core.conditions;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.EnumCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.component.ArchetypeChunk;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.decisionmaker.core.EvaluationContext;
/*    */ import com.hypixel.hytale.server.npc.decisionmaker.core.conditions.base.SimpleCondition;
/*    */ import com.hypixel.hytale.server.npc.movement.MovementState;
/*    */ import com.hypixel.hytale.server.npc.movement.controllers.MotionController;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TargetMovementStateCondition
/*    */   extends SimpleCondition
/*    */ {
/*    */   public static final BuilderCodec<TargetMovementStateCondition> CODEC;
/*    */   protected MovementState movementState;
/*    */   
/*    */   static {
/* 37 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(TargetMovementStateCondition.class, TargetMovementStateCondition::new, ABSTRACT_CODEC).documentation("A simple boolean condition that returns whether the target is in a given movement state.")).appendInherited(new KeyedCodec("State", (Codec)new EnumCodec(MovementState.class)), (condition, e) -> condition.movementState = e, condition -> condition.movementState, (condition, parent) -> condition.movementState = parent.movementState).addValidator(Validators.nonNull()).documentation("The movement state to check for.").add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean evaluate(int selfIndex, ArchetypeChunk<EntityStore> archetypeChunk, @Nullable Ref<EntityStore> target, @Nonnull CommandBuffer<EntityStore> commandBuffer, EvaluationContext context) {
/* 44 */     if (target == null || !target.isValid()) return false; 
/* 45 */     return MotionController.isInMovementState(target, this.movementState, (ComponentAccessor)commandBuffer);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\decisionmaker\core\conditions\TargetMovementStateCondition.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */