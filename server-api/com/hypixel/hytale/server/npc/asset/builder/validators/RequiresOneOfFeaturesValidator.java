/*    */ package com.hypixel.hytale.server.npc.asset.builder.validators;
/*    */ 
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderBase;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.Feature;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.FeatureEvaluatorHelper;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.providerevaluators.FeatureProviderEvaluator;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.providerevaluators.ProviderEvaluator;
/*    */ import java.util.EnumSet;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class RequiresOneOfFeaturesValidator
/*    */   extends RequiredFeatureValidator
/*    */ {
/*    */   @Nonnull
/*    */   private final EnumSet<Feature> requiredFeature;
/*    */   @Nonnull
/*    */   private final String[] description;
/*    */   
/*    */   private RequiresOneOfFeaturesValidator(@Nonnull EnumSet<Feature> requiredFeature) {
/* 21 */     this.requiredFeature = requiredFeature;
/* 22 */     this.description = BuilderBase.getDescriptionArray(requiredFeature);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean validate(@Nonnull FeatureEvaluatorHelper evaluatorHelper) {
/* 27 */     for (ProviderEvaluator provider : evaluatorHelper.getProviders()) {
/* 28 */       if (provider instanceof FeatureProviderEvaluator && ((FeatureProviderEvaluator)provider).provides(this.requiredFeature)) return true; 
/*    */     } 
/* 30 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String getErrorMessage(String context) {
/* 36 */     return String.format("At least one of required features %s must be provided at %s", new Object[] { String.join(", ", (CharSequence[])this.description), context });
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static RequiresOneOfFeaturesValidator withFeatures(@Nonnull EnumSet<Feature> requiredFeature) {
/* 41 */     return new RequiresOneOfFeaturesValidator(requiredFeature);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\validators\RequiresOneOfFeaturesValidator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */