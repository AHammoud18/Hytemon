/*    */ package com.hypixel.hytale.server.npc.asset.builder.providerevaluators;
/*    */ 
/*    */ import com.hypixel.hytale.server.npc.asset.builder.Builder;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderManager;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.Feature;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.FeatureEvaluatorHelper;
/*    */ import java.util.EnumSet;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ReferenceProviderEvaluator
/*    */   implements FeatureProviderEvaluator, ParameterProviderEvaluator
/*    */ {
/*    */   private final int referenceIndex;
/*    */   private final Class<?> classType;
/*    */   private FeatureEvaluatorHelper resolvedProviderSet;
/*    */   
/*    */   public ReferenceProviderEvaluator(int referenceIndex, Class<?> classType) {
/* 20 */     this.referenceIndex = referenceIndex;
/* 21 */     this.classType = classType;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean provides(EnumSet<Feature> feature) {
/* 26 */     if (this.resolvedProviderSet == null) return false;
/*    */     
/* 28 */     for (ProviderEvaluator evaluator : this.resolvedProviderSet.getProviders()) {
/* 29 */       if (evaluator instanceof FeatureProviderEvaluator && ((FeatureProviderEvaluator)evaluator).provides(feature)) return true;
/*    */     
/*    */     } 
/* 32 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasParameter(String parameter, ParameterType type) {
/* 37 */     if (this.resolvedProviderSet == null) return false;
/*    */     
/* 39 */     for (ProviderEvaluator evaluator : this.resolvedProviderSet.getProviders()) {
/* 40 */       if (evaluator instanceof ParameterProviderEvaluator && ((ParameterProviderEvaluator)evaluator).hasParameter(parameter, type)) return true;
/*    */     
/*    */     } 
/* 43 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public void resolveReferences(@Nonnull BuilderManager manager) {
/* 48 */     Builder<Object> referencedBuilder = manager.getCachedBuilder(this.referenceIndex, this.classType);
/* 49 */     this.resolvedProviderSet = referencedBuilder.getEvaluatorHelper();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\providerevaluators\ReferenceProviderEvaluator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */