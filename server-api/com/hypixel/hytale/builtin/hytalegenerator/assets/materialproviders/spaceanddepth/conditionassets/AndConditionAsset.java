/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.materialproviders.spaceanddepth.conditionassets;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.LoggerUtil;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.materialproviders.spaceanddepth.SpaceAndDepthMaterialProvider;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.materialproviders.spaceanddepth.conditions.AndCondition;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validator;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import java.util.ArrayList;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AndConditionAsset
/*    */   extends ConditionAsset
/*    */ {
/*    */   public static final BuilderCodec<AndConditionAsset> CODEC;
/*    */   
/*    */   static {
/* 24 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(AndConditionAsset.class, AndConditionAsset::new, ConditionAsset.ABSTRACT_CODEC).append(new KeyedCodec("Conditions", (Codec)new ArrayCodec((Codec)ConditionAsset.CODEC, x$0 -> new ConditionAsset[x$0]), true), (t, k) -> t.conditionAssets = k, k -> k.conditionAssets).addValidator((Validator)Validators.nonNullArrayElements()).add()).build();
/*    */   }
/* 26 */   private ConditionAsset[] conditionAssets = new ConditionAsset[0];
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public SpaceAndDepthMaterialProvider.Condition build() {
/* 31 */     ArrayList<SpaceAndDepthMaterialProvider.Condition> conditions = new ArrayList<>(this.conditionAssets.length);
/* 32 */     for (ConditionAsset asset : this.conditionAssets) {
/* 33 */       if (asset == null) {
/* 34 */         LoggerUtil.getLogger().warning("Null condition asset found, skipped.");
/*    */       } else {
/*    */         
/* 37 */         conditions.add(asset.build());
/*    */       } 
/* 39 */     }  return (SpaceAndDepthMaterialProvider.Condition)new AndCondition(conditions);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\materialproviders\spaceanddepth\conditionassets\AndConditionAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */