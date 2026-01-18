/*    */ package com.hypixel.hytale.server.npc.asset.builder.validators;
/*    */ 
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderBase;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.Feature;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.FeatureEvaluatorHelper;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.providerevaluators.FeatureProviderEvaluator;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.providerevaluators.ProviderEvaluator;
/*    */ import java.util.EnumSet;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ public class RequiresFeatureIfValidator
/*    */   extends RequiredFeatureValidator
/*    */ {
/*    */   @Nonnull
/*    */   private final String[] description;
/*    */   private final String attribute;
/*    */   private final boolean value;
/*    */   
/*    */   private RequiresFeatureIfValidator(String attribute, boolean value, @Nonnull EnumSet<Feature> feature) {
/* 22 */     this.attribute = attribute;
/* 23 */     this.description = BuilderBase.getDescriptionArray(feature);
/* 24 */     this.value = value;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean validate(FeatureEvaluatorHelper evaluatorHelper) {
/* 29 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public String getErrorMessage(String context) {
/* 35 */     return null;
/*    */   }
/*    */   
/*    */   public static boolean staticValidate(@Nonnull FeatureEvaluatorHelper evaluatorHelper, EnumSet<Feature> requiredFeature, boolean requiredValue, boolean value) {
/* 39 */     if (requiredValue != value) return true;
/*    */     
/* 41 */     for (ProviderEvaluator providedFeature : evaluatorHelper.getProviders()) {
/* 42 */       if (providedFeature instanceof FeatureProviderEvaluator && ((FeatureProviderEvaluator)providedFeature).provides(requiredFeature)) return true;
/*    */     
/*    */     } 
/* 45 */     return false;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static RequiresFeatureIfValidator withAttributes(String attribute, boolean value, @Nonnull EnumSet<Feature> feature) {
/* 50 */     return new RequiresFeatureIfValidator(attribute, value, feature);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\validators\RequiresFeatureIfValidator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */