/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.positionproviders;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.density.ConstantDensityAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.density.DensityAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.positionproviders.FieldFunctionOccurrencePositionProvider;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.positionproviders.PositionProvider;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FieldFunctionOccurrencePositionProviderAsset
/*    */   extends PositionProviderAsset
/*    */ {
/*    */   public static final BuilderCodec<FieldFunctionOccurrencePositionProviderAsset> CODEC;
/*    */   
/*    */   static {
/* 31 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(FieldFunctionOccurrencePositionProviderAsset.class, FieldFunctionOccurrencePositionProviderAsset::new, PositionProviderAsset.ABSTRACT_CODEC).append(new KeyedCodec("Seed", (Codec)Codec.STRING, true), (asset, v) -> asset.seed = v, asset -> asset.seed).add()).append(new KeyedCodec("FieldFunction", (Codec)DensityAsset.CODEC, true), (asset, v) -> asset.densityAsset = v, asset -> asset.densityAsset).add()).append(new KeyedCodec("Positions", (Codec)PositionProviderAsset.CODEC, true), (asset, v) -> asset.positionProviderAsset = v, asset -> asset.positionProviderAsset).add()).build();
/*    */   }
/* 33 */   private String seed = "";
/* 34 */   private DensityAsset densityAsset = (DensityAsset)new ConstantDensityAsset();
/* 35 */   private PositionProviderAsset positionProviderAsset = new ListPositionProviderAsset();
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public PositionProvider build(@Nonnull PositionProviderAsset.Argument argument) {
/* 40 */     if (skip()) return PositionProvider.noPositionProvider();
/*    */     
/* 42 */     Density functionTree = this.densityAsset.build(DensityAsset.from(argument));
/* 43 */     PositionProvider positionProvider = this.positionProviderAsset.build(argument);
/* 44 */     int intSeed = ((Integer)argument.parentSeed.child(this.seed).createSupplier().get()).intValue();
/* 45 */     return (PositionProvider)new FieldFunctionOccurrencePositionProvider(functionTree, positionProvider, intSeed);
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 50 */     this.densityAsset.cleanUp();
/* 51 */     this.positionProviderAsset.cleanUp();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\positionproviders\FieldFunctionOccurrencePositionProviderAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */