/*    */ package com.hypixel.hytale.server.npc.decisionmaker.core.conditions;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.component.ArchetypeChunk;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.decisionmaker.core.EvaluationContext;
/*    */ import com.hypixel.hytale.server.npc.decisionmaker.core.conditions.base.Condition;
/*    */ import java.util.concurrent.ThreadLocalRandom;
/*    */ import java.util.function.Supplier;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RandomiserCondition
/*    */   extends Condition
/*    */ {
/*    */   public static final BuilderCodec<RandomiserCondition> CODEC;
/*    */   protected double minValue;
/*    */   protected double maxValue;
/*    */   
/*    */   static {
/* 41 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(RandomiserCondition.class, RandomiserCondition::new, BASE_CODEC).documentation("A condition that jitters between two defined values to add a small amount of randomness to the final utility value.")).appendInherited(new KeyedCodec("MinValue", (Codec)Codec.DOUBLE), (condition, d) -> condition.minValue = d.doubleValue(), condition -> Double.valueOf(condition.minValue), (condition, parent) -> condition.minValue = parent.minValue).documentation("The minimum bound of the jitter.").addValidator(Validators.range(Double.valueOf(0.0D), Double.valueOf(1.0D))).add()).appendInherited(new KeyedCodec("MaxValue", (Codec)Codec.DOUBLE), (condition, d) -> condition.maxValue = d.doubleValue(), condition -> Double.valueOf(condition.maxValue), (condition, parent) -> condition.maxValue = parent.maxValue).documentation("The maximum bound of the jitter.").addValidator(Validators.range(Double.valueOf(0.0D), Double.valueOf(1.0D))).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double calculateUtility(int selfIndex, ArchetypeChunk<EntityStore> archetypeChunk, Ref<EntityStore> target, CommandBuffer<EntityStore> commandBuffer, EvaluationContext context) {
/* 48 */     return ThreadLocalRandom.current().nextDouble(this.minValue, this.maxValue);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSimplicity() {
/* 53 */     return 10;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\decisionmaker\core\conditions\RandomiserCondition.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */