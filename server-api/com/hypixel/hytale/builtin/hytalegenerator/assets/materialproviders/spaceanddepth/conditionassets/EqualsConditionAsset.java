/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.materialproviders.spaceanddepth.conditionassets;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.materialproviders.spaceanddepth.SpaceAndDepthMaterialProvider;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.materialproviders.spaceanddepth.conditions.ConditionParameter;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.materialproviders.spaceanddepth.conditions.EqualsCondition;
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
/*    */ public class EqualsConditionAsset
/*    */   extends ConditionAsset
/*    */ {
/*    */   public static final BuilderCodec<EqualsConditionAsset> CODEC;
/*    */   
/*    */   static {
/* 26 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(EqualsConditionAsset.class, EqualsConditionAsset::new, ConditionAsset.ABSTRACT_CODEC).append(new KeyedCodec("ContextToCheck", ConditionParameter.CODEC, true), (t, k) -> t.parameter = k, k -> k.parameter).add()).append(new KeyedCodec("Value", (Codec)Codec.INTEGER, true), (t, k) -> t.value = k.intValue(), k -> Integer.valueOf(k.value)).add()).build();
/*    */   }
/* 28 */   private ConditionParameter parameter = ConditionParameter.SPACE_ABOVE_FLOOR;
/* 29 */   private int value = 0;
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public SpaceAndDepthMaterialProvider.Condition build() {
/* 34 */     return (SpaceAndDepthMaterialProvider.Condition)new EqualsCondition(this.value, this.parameter);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\materialproviders\spaceanddepth\conditionassets\EqualsConditionAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */