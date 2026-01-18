/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.materialproviders.spaceanddepth.conditionassets;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.materialproviders.spaceanddepth.SpaceAndDepthMaterialProvider;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.materialproviders.spaceanddepth.conditions.AlwaysTrueCondition;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class AlwaysTrueConditionAsset
/*    */   extends ConditionAsset {
/* 11 */   public static final BuilderCodec<AlwaysTrueConditionAsset> CODEC = BuilderCodec.builder(AlwaysTrueConditionAsset.class, AlwaysTrueConditionAsset::new, ConditionAsset.ABSTRACT_CODEC)
/* 12 */     .build();
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public SpaceAndDepthMaterialProvider.Condition build() {
/* 17 */     return (SpaceAndDepthMaterialProvider.Condition)AlwaysTrueCondition.INSTANCE;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\materialproviders\spaceanddepth\conditionassets\AlwaysTrueConditionAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */