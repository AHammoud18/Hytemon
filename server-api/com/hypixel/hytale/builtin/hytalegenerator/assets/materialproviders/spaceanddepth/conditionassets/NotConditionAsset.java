/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.materialproviders.spaceanddepth.conditionassets;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.materialproviders.spaceanddepth.SpaceAndDepthMaterialProvider;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.materialproviders.spaceanddepth.conditions.NotCondition;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class NotConditionAsset
/*    */   extends ConditionAsset
/*    */ {
/*    */   public static final BuilderCodec<NotConditionAsset> CODEC;
/*    */   
/*    */   static {
/* 18 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(NotConditionAsset.class, NotConditionAsset::new, ConditionAsset.ABSTRACT_CODEC).append(new KeyedCodec("Condition", (Codec)ConditionAsset.CODEC, true), (t, k) -> t.conditionAsset = k, k -> k.conditionAsset).add()).build();
/*    */   }
/* 20 */   private ConditionAsset conditionAsset = new AlwaysTrueConditionAsset();
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public SpaceAndDepthMaterialProvider.Condition build() {
/* 25 */     return (SpaceAndDepthMaterialProvider.Condition)new NotCondition(this.conditionAsset.build());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\materialproviders\spaceanddepth\conditionassets\NotConditionAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */