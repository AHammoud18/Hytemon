/*    */ package com.hypixel.hytale.server.npc.decisionmaker.core.conditions.base;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.component.ArchetypeChunk;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.math.util.MathUtil;
/*    */ import com.hypixel.hytale.server.core.asset.type.responsecurve.config.ResponseCurve;
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
/*    */ public abstract class CurveCondition
/*    */   extends Condition
/*    */ {
/*    */   public static final BuilderCodec<CurveCondition> ABSTRACT_CODEC;
/*    */   protected String responseCurve;
/*    */   protected ResponseCurve.Reference responseCurveReference;
/*    */   
/*    */   static {
/* 41 */     ABSTRACT_CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.abstractBuilder(CurveCondition.class, BASE_CODEC).appendInherited(new KeyedCodec("Curve", (Codec)Codec.STRING), (condition, s) -> condition.responseCurve = s, condition -> condition.responseCurve, (condition, parent) -> condition.responseCurve = parent.responseCurve).documentation("The response curve used to evaluate the condition.").addValidator(Validators.nonNull()).addValidator(ResponseCurve.VALIDATOR_CACHE.getValidator()).add()).afterDecode(condition -> { if (condition.responseCurve != null) { int index = ResponseCurve.getAssetMap().getIndex(condition.responseCurve); condition.responseCurveReference = new ResponseCurve.Reference(index, (ResponseCurve)ResponseCurve.getAssetMap().getAsset(index)); }  })).build();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getResponseCurve() {
/* 50 */     return this.responseCurve;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public double calculateUtility(int selfIndex, ArchetypeChunk<EntityStore> archetypeChunk, Ref<EntityStore> target, CommandBuffer<EntityStore> commandBuffer, EvaluationContext context) {
/* 56 */     ResponseCurve curve = this.responseCurveReference.get();
/* 57 */     if (curve == null) throw new IllegalStateException("No such response curve asset: " + this.responseCurve);
/*    */     
/* 59 */     double input = getNormalisedInput(selfIndex, archetypeChunk, target, commandBuffer, context);
/* 60 */     if (input == Double.MAX_VALUE) return 0.0D;
/*    */     
/* 62 */     return MathUtil.clamp(curve.computeY(input), 0.0D, 1.0D);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSimplicity() {
/* 67 */     return 20;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 76 */     return "CurveCondition{responseCurve=" + this.responseCurve + "} " + super
/*    */       
/* 78 */       .toString();
/*    */   }
/*    */   
/*    */   protected abstract double getNormalisedInput(int paramInt, ArchetypeChunk<EntityStore> paramArchetypeChunk, Ref<EntityStore> paramRef, CommandBuffer<EntityStore> paramCommandBuffer, EvaluationContext paramEvaluationContext);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\decisionmaker\core\conditions\base\CurveCondition.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */