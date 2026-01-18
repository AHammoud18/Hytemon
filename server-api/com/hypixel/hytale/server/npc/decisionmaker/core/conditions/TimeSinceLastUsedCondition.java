/*    */ package com.hypixel.hytale.server.npc.decisionmaker.core.conditions;
/*    */ 
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.ArchetypeChunk;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.decisionmaker.core.EvaluationContext;
/*    */ import com.hypixel.hytale.server.npc.decisionmaker.core.conditions.base.ScaledCurveCondition;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TimeSinceLastUsedCondition
/*    */   extends ScaledCurveCondition
/*    */ {
/* 19 */   public static final BuilderCodec<TimeSinceLastUsedCondition> CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(TimeSinceLastUsedCondition.class, TimeSinceLastUsedCondition::new, ScaledCurveCondition.ABSTRACT_CODEC)
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 24 */     .documentation("A scaled curve condition that returns a utility value based on how long it has been since the Option was last used."))
/* 25 */     .build();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected double getInput(int selfIndex, ArchetypeChunk<EntityStore> archetypeChunk, Ref<EntityStore> target, CommandBuffer<EntityStore> commandBuffer, @Nonnull EvaluationContext context) {
/* 33 */     long interval = System.nanoTime() - context.getLastUsedNanos();
/* 34 */     return interval / 1.0E9D;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\decisionmaker\core\conditions\TimeSinceLastUsedCondition.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */