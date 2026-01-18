/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.positionproviders;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.positionproviders.PositionProvider;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.positionproviders.UnionPositionProvider;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*    */ import java.util.ArrayList;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class UnionPositionProviderAsset
/*    */   extends PositionProviderAsset
/*    */ {
/*    */   public static final BuilderCodec<UnionPositionProviderAsset> CODEC;
/*    */   
/*    */   static {
/* 20 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(UnionPositionProviderAsset.class, UnionPositionProviderAsset::new, PositionProviderAsset.ABSTRACT_CODEC).append(new KeyedCodec("Positions", (Codec)new ArrayCodec((Codec)PositionProviderAsset.CODEC, x$0 -> new PositionProviderAsset[x$0]), true), (asset, v) -> asset.positionProviderAssets = v, asset -> asset.positionProviderAssets).add()).build();
/*    */   }
/* 22 */   private PositionProviderAsset[] positionProviderAssets = new PositionProviderAsset[0];
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public PositionProvider build(@Nonnull PositionProviderAsset.Argument argument) {
/* 27 */     if (skip()) {
/* 28 */       return PositionProvider.noPositionProvider();
/*    */     }
/*    */     
/* 31 */     ArrayList<PositionProvider> list = new ArrayList<>();
/* 32 */     for (PositionProviderAsset asset : this.positionProviderAssets) {
/* 33 */       PositionProvider positionProvider = asset.build(argument);
/* 34 */       list.add(positionProvider);
/*    */     } 
/* 36 */     return (PositionProvider)new UnionPositionProvider(list);
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 41 */     for (PositionProviderAsset positionProviderAsset : this.positionProviderAssets)
/* 42 */       positionProviderAsset.cleanUp(); 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\positionproviders\UnionPositionProviderAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */