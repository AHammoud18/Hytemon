/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.positionproviders;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.pointgenerators.NoPointGeneratorAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.pointgenerators.PointGeneratorAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.positionproviders.Mesh2DPositionProvider;
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
/*    */ public class Mesh2DPositionProviderAsset
/*    */   extends PositionProviderAsset
/*    */ {
/*    */   public static final BuilderCodec<Mesh2DPositionProviderAsset> CODEC;
/*    */   
/*    */   static {
/* 26 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(Mesh2DPositionProviderAsset.class, Mesh2DPositionProviderAsset::new, PositionProviderAsset.ABSTRACT_CODEC).append(new KeyedCodec("PointGenerator", (Codec)PointGeneratorAsset.CODEC, true), (asset, v) -> asset.pointGeneratorAsset = v, asset -> asset.pointGeneratorAsset).add()).append(new KeyedCodec("PointsY", (Codec)Codec.INTEGER, true), (asset, v) -> asset.y = v.intValue(), asset -> Integer.valueOf(asset.y)).add()).build();
/*    */   }
/* 28 */   private PointGeneratorAsset pointGeneratorAsset = (PointGeneratorAsset)new NoPointGeneratorAsset();
/* 29 */   private int y = 0;
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public PositionProvider build(@Nonnull PositionProviderAsset.Argument argument) {
/* 34 */     if (skip()) {
/* 35 */       return PositionProvider.noPositionProvider();
/*    */     }
/*    */     
/* 38 */     return (PositionProvider)new Mesh2DPositionProvider(this.pointGeneratorAsset.build(argument.parentSeed), this.y);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\positionproviders\Mesh2DPositionProviderAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */