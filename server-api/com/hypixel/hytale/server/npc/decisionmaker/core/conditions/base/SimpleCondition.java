/*    */ package com.hypixel.hytale.server.npc.decisionmaker.core.conditions.base;
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
/*    */ import javax.annotation.Nonnull;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class SimpleCondition
/*    */   extends Condition
/*    */ {
/*    */   public static final BuilderCodec<SimpleCondition> ABSTRACT_CODEC;
/*    */   
/*    */   static {
/* 41 */     ABSTRACT_CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.abstractBuilder(SimpleCondition.class, BASE_CODEC).appendInherited(new KeyedCodec("FalseValue", (Codec)Codec.DOUBLE), (condition, d) -> condition.falseValue = d.doubleValue(), condition -> Double.valueOf(condition.falseValue), (condition, parent) -> condition.falseValue = parent.falseValue).documentation("The utility value to use when the condition evaluates false.").addValidator(Validators.range(Double.valueOf(0.0D), Double.valueOf(1.0D))).add()).appendInherited(new KeyedCodec("TrueValue", (Codec)Codec.DOUBLE), (condition, d) -> condition.trueValue = d.doubleValue(), condition -> Double.valueOf(condition.trueValue), (condition, parent) -> condition.trueValue = parent.trueValue).documentation("The utility value to use when the condition evaluates true.").addValidator(Validators.range(Double.valueOf(0.0D), Double.valueOf(1.0D))).add()).build();
/*    */   }
/* 43 */   protected double falseValue = 0.0D;
/* 44 */   protected double trueValue = 1.0D;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double calculateUtility(int selfIndex, ArchetypeChunk<EntityStore> archetypeChunk, Ref<EntityStore> target, CommandBuffer<EntityStore> commandBuffer, EvaluationContext context) {
/* 52 */     return evaluate(selfIndex, archetypeChunk, target, commandBuffer, context) ? this.trueValue : this.falseValue;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSimplicity() {
/* 57 */     return 10;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 66 */     return "SimpleCondition{falseValue=" + this.falseValue + ", trueValue=" + this.trueValue + "} " + super
/*    */ 
/*    */       
/* 69 */       .toString();
/*    */   }
/*    */   
/*    */   protected abstract boolean evaluate(int paramInt, ArchetypeChunk<EntityStore> paramArchetypeChunk, Ref<EntityStore> paramRef, CommandBuffer<EntityStore> paramCommandBuffer, EvaluationContext paramEvaluationContext);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\decisionmaker\core\conditions\base\SimpleCondition.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */