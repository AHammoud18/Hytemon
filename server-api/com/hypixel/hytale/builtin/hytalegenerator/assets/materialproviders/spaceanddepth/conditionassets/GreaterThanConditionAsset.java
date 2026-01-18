/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.materialproviders.spaceanddepth.conditionassets;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.materialproviders.spaceanddepth.SpaceAndDepthMaterialProvider;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.materialproviders.spaceanddepth.conditions.ConditionParameter;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.materialproviders.spaceanddepth.conditions.GreaterThanCondition;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import java.util.function.Supplier;
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
/*    */ public class GreaterThanConditionAsset
/*    */   extends ConditionAsset
/*    */ {
/*    */   public static final BuilderCodec<GreaterThanConditionAsset> CODEC;
/*    */   
/*    */   static {
/* 26 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(GreaterThanConditionAsset.class, GreaterThanConditionAsset::new, ConditionAsset.ABSTRACT_CODEC).append(new KeyedCodec("ContextToCheck", ConditionParameter.CODEC, true), (t, k) -> t.parameter = k, k -> k.parameter).add()).append(new KeyedCodec("Threshold", (Codec)Codec.INTEGER, true), (t, k) -> t.threshold = k.intValue(), k -> Integer.valueOf(k.threshold)).add()).build();
/*    */   }
/* 28 */   private ConditionParameter parameter = ConditionParameter.SPACE_ABOVE_FLOOR;
/* 29 */   private int threshold = 0;
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public SpaceAndDepthMaterialProvider.Condition build() {
/* 34 */     return (SpaceAndDepthMaterialProvider.Condition)new GreaterThanCondition(this.threshold, this.parameter);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\materialproviders\spaceanddepth\conditionassets\GreaterThanConditionAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */