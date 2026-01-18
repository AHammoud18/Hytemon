/*    */ package com.hypixel.hytale.server.npc.decisionmaker.core.conditions.base;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.component.ArchetypeChunk;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.server.core.asset.type.responsecurve.ScaledResponseCurve;
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
/*    */ public abstract class ScaledCurveCondition
/*    */   extends Condition
/*    */ {
/*    */   public static final BuilderCodec<ScaledCurveCondition> ABSTRACT_CODEC;
/*    */   protected ScaledResponseCurve responseCurve;
/*    */   
/*    */   static {
/* 32 */     ABSTRACT_CODEC = ((BuilderCodec.Builder)BuilderCodec.abstractBuilder(ScaledCurveCondition.class, BASE_CODEC).appendInherited(new KeyedCodec("Curve", (Codec)ScaledResponseCurve.CODEC), (condition, s) -> condition.responseCurve = s, condition -> condition.responseCurve, (condition, parent) -> condition.responseCurve = parent.responseCurve).documentation("The scaled response curve used to evaluate the condition.").addValidator(Validators.nonNull()).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ScaledResponseCurve getResponseCurve() {
/* 40 */     return this.responseCurve;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public double calculateUtility(int selfIndex, ArchetypeChunk<EntityStore> archetypeChunk, Ref<EntityStore> target, CommandBuffer<EntityStore> commandBuffer, EvaluationContext context) {
/* 46 */     double input = getInput(selfIndex, archetypeChunk, target, commandBuffer, context);
/* 47 */     if (input == Double.MAX_VALUE) return 0.0D;
/*    */     
/* 49 */     return this.responseCurve.computeY(input);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSimplicity() {
/* 54 */     return 30;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 63 */     return "ScaledCurveCondition{responseCurve=" + String.valueOf(this.responseCurve) + "} " + super
/*    */       
/* 65 */       .toString();
/*    */   }
/*    */   
/*    */   protected abstract double getInput(int paramInt, ArchetypeChunk<EntityStore> paramArchetypeChunk, Ref<EntityStore> paramRef, CommandBuffer<EntityStore> paramCommandBuffer, EvaluationContext paramEvaluationContext);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\decisionmaker\core\conditions\base\ScaledCurveCondition.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */