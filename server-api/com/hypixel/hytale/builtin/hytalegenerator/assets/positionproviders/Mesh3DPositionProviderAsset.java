/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.positionproviders;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.pointgenerators.NoPointGeneratorAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.pointgenerators.PointGeneratorAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.positionproviders.Mesh3DPositionProvider;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.positionproviders.PositionProvider;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class Mesh3DPositionProviderAsset
/*    */   extends PositionProviderAsset
/*    */ {
/*    */   public static final BuilderCodec<Mesh3DPositionProviderAsset> CODEC;
/*    */   
/*    */   static {
/* 20 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(Mesh3DPositionProviderAsset.class, Mesh3DPositionProviderAsset::new, PositionProviderAsset.ABSTRACT_CODEC).append(new KeyedCodec("PointGenerator", (Codec)PointGeneratorAsset.CODEC, true), (asset, v) -> asset.pointGeneratorAsset = v, asset -> asset.pointGeneratorAsset).add()).build();
/*    */   }
/* 22 */   private PointGeneratorAsset pointGeneratorAsset = (PointGeneratorAsset)new NoPointGeneratorAsset();
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public PositionProvider build(@Nonnull PositionProviderAsset.Argument argument) {
/* 27 */     if (skip()) {
/* 28 */       return PositionProvider.noPositionProvider();
/*    */     }
/*    */     
/* 31 */     return (PositionProvider)new Mesh3DPositionProvider(this.pointGeneratorAsset.build(argument.parentSeed));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\positionproviders\Mesh3DPositionProviderAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */