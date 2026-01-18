/*    */ package com.hypixel.hytale.server.npc.asset.builder;
/*    */ 
/*    */ import com.hypixel.hytale.server.npc.asset.builder.providerevaluators.ProviderEvaluator;
/*    */ import com.hypixel.hytale.server.npc.util.expression.ExecutionContext;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import java.util.function.BiConsumer;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FeatureEvaluatorHelper
/*    */ {
/*    */   @Nonnull
/* 16 */   private List<ProviderEvaluator> evaluators = (List<ProviderEvaluator>)new ObjectArrayList();
/*    */   
/*    */   private List<BiConsumer<BuilderManager, ExecutionContext>> providerReferenceValidators;
/*    */   
/*    */   private List<BiConsumer<FeatureEvaluatorHelper, ExecutionContext>> componentRequirementValidators;
/*    */   
/*    */   private boolean locked;
/*    */   private boolean containsProviderReference;
/*    */   private boolean isFeatureRequiringComponent;
/*    */   private boolean disallowParameterProviders;
/*    */   
/*    */   public FeatureEvaluatorHelper() {}
/*    */   
/*    */   public FeatureEvaluatorHelper(boolean couldRequireFeature) {
/* 30 */     this.isFeatureRequiringComponent = couldRequireFeature;
/*    */   }
/*    */   
/*    */   public boolean isDisallowParameterProviders() {
/* 34 */     return this.disallowParameterProviders;
/*    */   }
/*    */   
/*    */   public void add(ProviderEvaluator evaluator) {
/* 38 */     this.evaluators.add(evaluator);
/*    */   }
/*    */   
/*    */   public boolean canAddProvider() {
/* 42 */     return !this.locked;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public FeatureEvaluatorHelper lock() {
/* 47 */     this.locked = true;
/* 48 */     this.evaluators = Collections.unmodifiableList(this.evaluators);
/* 49 */     return this;
/*    */   }
/*    */   
/*    */   public void setContainsReference() {
/* 53 */     this.containsProviderReference = true;
/*    */   }
/*    */   
/*    */   public void addProviderReferenceValidator(BiConsumer<BuilderManager, ExecutionContext> referenceValidator) {
/* 57 */     if (this.providerReferenceValidators == null) this.providerReferenceValidators = (List<BiConsumer<BuilderManager, ExecutionContext>>)new ObjectArrayList(); 
/* 58 */     this.providerReferenceValidators.add(referenceValidator);
/*    */   }
/*    */   
/*    */   public void addComponentRequirementValidator(BiConsumer<FeatureEvaluatorHelper, ExecutionContext> validator) {
/* 62 */     if (this.componentRequirementValidators == null) this.componentRequirementValidators = (List<BiConsumer<FeatureEvaluatorHelper, ExecutionContext>>)new ObjectArrayList(); 
/* 63 */     this.componentRequirementValidators.add(validator);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public List<ProviderEvaluator> getProviders() {
/* 68 */     return this.evaluators;
/*    */   }
/*    */   
/*    */   public boolean requiresProviderReferenceEvaluation() {
/* 72 */     return this.containsProviderReference;
/*    */   }
/*    */   
/*    */   public boolean belongsToFeatureRequiringComponent() {
/* 76 */     return this.isFeatureRequiringComponent;
/*    */   }
/*    */   
/*    */   public void validateProviderReferences(BuilderManager manager, ExecutionContext context) {
/* 80 */     if (this.providerReferenceValidators == null)
/*    */       return; 
/* 82 */     for (BiConsumer<BuilderManager, ExecutionContext> validator : this.providerReferenceValidators) {
/* 83 */       validator.accept(manager, context);
/*    */     }
/*    */   }
/*    */   
/*    */   public void validateComponentRequirements(FeatureEvaluatorHelper providers, ExecutionContext context) {
/* 88 */     if (this.componentRequirementValidators == null)
/*    */       return; 
/* 90 */     for (BiConsumer<FeatureEvaluatorHelper, ExecutionContext> validator : this.componentRequirementValidators) {
/* 91 */       validator.accept(providers, context);
/*    */     }
/*    */   }
/*    */   
/*    */   public void disallowParameterProviders() {
/* 96 */     this.disallowParameterProviders = true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\FeatureEvaluatorHelper.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */