/*    */ package com.hypixel.hytale.server.npc.asset.builder.providerevaluators;
/*    */ 
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderManager;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.Feature;
/*    */ import java.util.EnumSet;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class UnconditionalFeatureProviderEvaluator
/*    */   implements FeatureProviderEvaluator
/*    */ {
/*    */   @Nonnull
/*    */   private final Feature feature;
/*    */   private final String description;
/*    */   
/*    */   public UnconditionalFeatureProviderEvaluator(@Nonnull Feature feature) {
/* 18 */     this.feature = feature;
/* 19 */     this.description = feature.get();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean provides(@Nonnull EnumSet<Feature> feature) {
/* 24 */     return feature.contains(this.feature);
/*    */   }
/*    */   
/*    */   public void resolveReferences(BuilderManager manager) {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\providerevaluators\UnconditionalFeatureProviderEvaluator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */