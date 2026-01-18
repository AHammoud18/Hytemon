/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.props.prefabprop.directionality;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.patterns.ConstantPatternAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.patterns.PatternAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.props.directionality.Directionality;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.props.directionality.StaticDirectionality;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.ValidationResults;
/*    */ import com.hypixel.hytale.server.core.prefab.PrefabRotation;
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
/*    */ 
/*    */ public class StaticDirectionalityAsset
/*    */   extends DirectionalityAsset
/*    */ {
/*    */   public static final BuilderCodec<StaticDirectionalityAsset> CODEC;
/*    */   
/*    */   static {
/* 30 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(StaticDirectionalityAsset.class, StaticDirectionalityAsset::new, DirectionalityAsset.ABSTRACT_CODEC).append(new KeyedCodec("Rotation", (Codec)Codec.INTEGER, false), (asset, v) -> asset.rotation = v.intValue(), asset -> Integer.valueOf(asset.rotation)).addValidator((v, r) -> { if (v.intValue() != 0 && v.intValue() != 90 && v.intValue() != 180 && v.intValue() != 270) r.fail("Rotation can only have the values: 0, 90, 180, 270");  }).add()).append(new KeyedCodec("Pattern", (Codec)PatternAsset.CODEC, true), (asset, v) -> asset.patternAsset = v, asset -> asset.patternAsset).add()).build();
/*    */   }
/* 32 */   private int rotation = 0;
/* 33 */   private PatternAsset patternAsset = (PatternAsset)new ConstantPatternAsset();
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Directionality build(@Nonnull DirectionalityAsset.Argument argument) {
/* 38 */     switch (this.rotation) { case 90:
/*    */       
/*    */       case 180:
/*    */       
/*    */       case 270:
/* 43 */        }  PrefabRotation prefabRotation = PrefabRotation.ROTATION_0;
/* 44 */     return (Directionality)new StaticDirectionality(prefabRotation, this.patternAsset.build(PatternAsset.argumentFrom(argument)));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\props\prefabprop\directionality\StaticDirectionalityAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */