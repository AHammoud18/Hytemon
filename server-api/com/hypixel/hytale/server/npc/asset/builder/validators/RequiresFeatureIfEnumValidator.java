/*    */ package com.hypixel.hytale.server.npc.asset.builder.validators;
/*    */ 
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderBase;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.Feature;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.FeatureEvaluatorHelper;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.providerevaluators.FeatureProviderEvaluator;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.providerevaluators.ProviderEvaluator;
/*    */ import java.util.EnumSet;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RequiresFeatureIfEnumValidator<E extends Enum<E> & Supplier<String>>
/*    */   extends RequiredFeatureValidator
/*    */ {
/*    */   @Nonnull
/*    */   private final String[] description;
/*    */   private final String attribute;
/*    */   private final E value;
/*    */   
/*    */   private RequiresFeatureIfEnumValidator(String attribute, E value, @Nonnull EnumSet<Feature> feature) {
/* 24 */     this.attribute = attribute;
/* 25 */     this.description = BuilderBase.getDescriptionArray(feature);
/* 26 */     this.value = value;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean validate(FeatureEvaluatorHelper evaluatorHelper) {
/* 31 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public String getErrorMessage(String context) {
/* 37 */     return null;
/*    */   }
/*    */   
/*    */   public static <E extends Enum<E> & Supplier<String>> boolean staticValidate(@Nonnull FeatureEvaluatorHelper evaluatorHelper, EnumSet<Feature> requiredFeature, E requiredValue, E value) {
/* 41 */     if (requiredValue != value) return true;
/*    */     
/* 43 */     for (ProviderEvaluator providedFeature : evaluatorHelper.getProviders()) {
/* 44 */       if (providedFeature instanceof FeatureProviderEvaluator && ((FeatureProviderEvaluator)providedFeature).provides(requiredFeature)) return true;
/*    */     
/*    */     } 
/* 47 */     return false;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static <E extends Enum<E> & Supplier<String>> RequiresFeatureIfEnumValidator withAttributes(String attribute, E value, @Nonnull EnumSet<Feature> feature) {
/* 52 */     return new RequiresFeatureIfEnumValidator<>(attribute, value, feature);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\validators\RequiresFeatureIfEnumValidator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */