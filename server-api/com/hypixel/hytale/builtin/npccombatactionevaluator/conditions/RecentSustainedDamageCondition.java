/*    */ package com.hypixel.hytale.builtin.npccombatactionevaluator.conditions;
/*    */ import com.hypixel.hytale.builtin.npccombatactionevaluator.memory.DamageMemory;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.ArchetypeChunk;
/*    */ import com.hypixel.hytale.component.Holder;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.decisionmaker.core.EvaluationContext;
/*    */ import com.hypixel.hytale.server.npc.decisionmaker.core.conditions.base.ScaledCurveCondition;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class RecentSustainedDamageCondition extends ScaledCurveCondition {
/* 13 */   public static final BuilderCodec<RecentSustainedDamageCondition> CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(RecentSustainedDamageCondition.class, RecentSustainedDamageCondition::new, ScaledCurveCondition.ABSTRACT_CODEC)
/* 14 */     .documentation("A scaled curve condition that returns a utility value based on damage taken since the combat action evaluator was last run."))
/* 15 */     .build();
/*    */   
/* 17 */   protected static final ComponentType<EntityStore, DamageMemory> DAMAGE_MEMORY_COMPONENT_TYPE = DamageMemory.getComponentType();
/*    */ 
/*    */   
/*    */   protected double getInput(int selfIndex, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, Ref<EntityStore> target, CommandBuffer<EntityStore> commandBuffer, EvaluationContext context) {
/* 21 */     DamageMemory memory = (DamageMemory)archetypeChunk.getComponent(selfIndex, DAMAGE_MEMORY_COMPONENT_TYPE);
/* 22 */     return memory.getRecentDamage();
/*    */   }
/*    */ 
/*    */   
/*    */   public void setupNPC(@Nonnull Holder<EntityStore> holder) {
/* 27 */     holder.ensureComponent(DAMAGE_MEMORY_COMPONENT_TYPE);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\npccombatactionevaluator\conditions\RecentSustainedDamageCondition.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */