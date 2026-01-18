/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.props.prefabprop.directionality;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.patterns.ConstantPatternAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.patterns.PatternAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.props.directionality.Directionality;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.props.directionality.RandomDirectionality;
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
/*    */ public class RandomDirectionalityAsset
/*    */   extends DirectionalityAsset
/*    */ {
/*    */   public static final BuilderCodec<RandomDirectionalityAsset> CODEC;
/*    */   
/*    */   static {
/* 26 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(RandomDirectionalityAsset.class, RandomDirectionalityAsset::new, DirectionalityAsset.ABSTRACT_CODEC).append(new KeyedCodec("Seed", (Codec)Codec.STRING, true), (asset, v) -> asset.seed = v, asset -> asset.seed).add()).append(new KeyedCodec("Pattern", (Codec)PatternAsset.CODEC, true), (asset, v) -> asset.patternAsset = v, asset -> asset.patternAsset).add()).build();
/*    */   }
/* 28 */   private String seed = "A";
/* 29 */   private PatternAsset patternAsset = (PatternAsset)new ConstantPatternAsset();
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Directionality build(@Nonnull DirectionalityAsset.Argument argument) {
/* 34 */     return (Directionality)new RandomDirectionality(this.patternAsset.build(PatternAsset.argumentFrom(argument)), ((Integer)argument.parentSeed.child(this.seed).createSupplier().get()).intValue());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\props\prefabprop\directionality\RandomDirectionalityAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */