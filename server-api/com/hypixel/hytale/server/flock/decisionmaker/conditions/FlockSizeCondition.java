/*    */ package com.hypixel.hytale.server.flock.decisionmaker.conditions;
/*    */ 
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.ArchetypeChunk;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.server.core.entity.group.EntityGroup;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.flock.FlockMembership;
/*    */ import com.hypixel.hytale.server.npc.decisionmaker.core.EvaluationContext;
/*    */ import com.hypixel.hytale.server.npc.decisionmaker.core.conditions.base.ScaledCurveCondition;
/*    */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FlockSizeCondition
/*    */   extends ScaledCurveCondition
/*    */ {
/* 21 */   public static final BuilderCodec<FlockSizeCondition> CODEC = BuilderCodec.builder(FlockSizeCondition.class, FlockSizeCondition::new, ScaledCurveCondition.ABSTRACT_CODEC)
/* 22 */     .build();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected double getInput(int selfIndex, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, Ref<EntityStore> target, @Nonnull CommandBuffer<EntityStore> commandBuffer, EvaluationContext context) {
/* 29 */     NPCEntity self = (NPCEntity)archetypeChunk.getComponent(selfIndex, NPCEntity.getComponentType());
/* 30 */     FlockMembership membership = (FlockMembership)archetypeChunk.getComponent(selfIndex, FlockMembership.getComponentType());
/* 31 */     if (membership == null) return 1.0D;
/*    */     
/* 33 */     Ref<EntityStore> flockReference = membership.getFlockRef();
/* 34 */     if (flockReference == null || !flockReference.isValid()) return 1.0D; 
/* 35 */     return ((EntityGroup)commandBuffer.getComponent(flockReference, EntityGroup.getComponentType())).size();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 41 */     return "FlockSizeCondition{} " + super.toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\flock\decisionmaker\conditions\FlockSizeCondition.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */