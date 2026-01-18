/*    */ package com.hypixel.hytale.server.npc.asset.builder;
/*    */ 
/*    */ import com.hypixel.hytale.codec.ExtraInfo;
/*    */ import com.hypixel.hytale.server.npc.decisionmaker.core.Evaluator;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BuilderValidationHelper
/*    */ {
/*    */   private final String name;
/*    */   private final FeatureEvaluatorHelper featureEvaluatorHelper;
/*    */   private final InternalReferenceResolver internalReferenceResolver;
/*    */   private final StateMappingHelper stateMappingHelper;
/*    */   private final InstructionContextHelper instructionContextHelper;
/*    */   private final ExtraInfo extraInfo;
/*    */   private final List<Evaluator<?>> evaluators;
/*    */   private final List<String> readErrors;
/*    */   
/*    */   public BuilderValidationHelper(String name, FeatureEvaluatorHelper featureEvaluator, InternalReferenceResolver internalReferenceResolver, StateMappingHelper stateMappingHelper, InstructionContextHelper instructionContextHelper, ExtraInfo extraInfo, List<Evaluator<?>> evaluators, List<String> readErrors) {
/* 27 */     this.name = name;
/* 28 */     this.featureEvaluatorHelper = featureEvaluator;
/* 29 */     this.internalReferenceResolver = internalReferenceResolver;
/* 30 */     this.stateMappingHelper = stateMappingHelper;
/* 31 */     this.instructionContextHelper = instructionContextHelper;
/* 32 */     this.extraInfo = extraInfo;
/* 33 */     this.evaluators = evaluators;
/* 34 */     this.readErrors = readErrors;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 38 */     return this.name;
/*    */   }
/*    */   
/*    */   public FeatureEvaluatorHelper getFeatureEvaluatorHelper() {
/* 42 */     return this.featureEvaluatorHelper;
/*    */   }
/*    */   
/*    */   public InternalReferenceResolver getInternalReferenceResolver() {
/* 46 */     return this.internalReferenceResolver;
/*    */   }
/*    */   
/*    */   public StateMappingHelper getStateMappingHelper() {
/* 50 */     return this.stateMappingHelper;
/*    */   }
/*    */   
/*    */   public InstructionContextHelper getInstructionContextHelper() {
/* 54 */     return this.instructionContextHelper;
/*    */   }
/*    */   
/*    */   public ExtraInfo getExtraInfo() {
/* 58 */     return this.extraInfo;
/*    */   }
/*    */   
/*    */   public List<String> getReadErrors() {
/* 62 */     return this.readErrors;
/*    */   }
/*    */   
/*    */   public List<Evaluator<?>> getEvaluators() {
/* 66 */     return this.evaluators;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\BuilderValidationHelper.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */