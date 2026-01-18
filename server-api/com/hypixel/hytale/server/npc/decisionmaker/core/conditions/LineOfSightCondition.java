/*    */ package com.hypixel.hytale.server.npc.decisionmaker.core.conditions;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.ArchetypeChunk;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.decisionmaker.core.EvaluationContext;
/*    */ import com.hypixel.hytale.server.npc.decisionmaker.core.conditions.base.SimpleCondition;
/*    */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*    */ import com.hypixel.hytale.server.npc.role.support.PositionCache;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class LineOfSightCondition extends SimpleCondition {
/* 17 */   public static final BuilderCodec<LineOfSightCondition> CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(LineOfSightCondition.class, LineOfSightCondition::new, ABSTRACT_CODEC)
/* 18 */     .documentation("A simple boolean condition that returns whether or not there is a line of sight to the target."))
/* 19 */     .build();
/*    */   
/*    */   @Nullable
/* 22 */   protected static final ComponentType<EntityStore, NPCEntity> NPC_COMPONENT_TYPE = NPCEntity.getComponentType();
/*    */ 
/*    */   
/*    */   public int getSimplicity() {
/* 26 */     return 40;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean evaluate(int selfIndex, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nullable Ref<EntityStore> targetRef, @Nonnull CommandBuffer<EntityStore> commandBuffer, EvaluationContext context) {
/* 32 */     if (targetRef == null || !targetRef.isValid()) return false;
/*    */     
/* 34 */     Ref<EntityStore> selfRef = archetypeChunk.getReferenceTo(selfIndex);
/* 35 */     NPCEntity selfNpcComponent = (NPCEntity)archetypeChunk.getComponent(selfIndex, NPC_COMPONENT_TYPE);
/* 36 */     assert selfNpcComponent != null;
/*    */     
/* 38 */     PositionCache positionCache = selfNpcComponent.getRole().getPositionCache();
/* 39 */     return positionCache.hasLineOfSight(selfRef, targetRef, (ComponentAccessor)commandBuffer);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\decisionmaker\core\conditions\LineOfSightCondition.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */