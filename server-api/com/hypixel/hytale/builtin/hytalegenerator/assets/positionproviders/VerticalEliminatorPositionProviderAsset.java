/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.positionproviders;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.positionproviders.PositionProvider;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.positionproviders.VerticalEliminatorPositionProvider;
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
/*    */ 
/*    */ public class VerticalEliminatorPositionProviderAsset
/*    */   extends PositionProviderAsset
/*    */ {
/*    */   public static final BuilderCodec<VerticalEliminatorPositionProviderAsset> CODEC;
/*    */   
/*    */   static {
/* 29 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(VerticalEliminatorPositionProviderAsset.class, VerticalEliminatorPositionProviderAsset::new, PositionProviderAsset.ABSTRACT_CODEC).append(new KeyedCodec("MaxY", (Codec)Codec.INTEGER, true), (asset, v) -> asset.maxY = v.intValue(), asset -> Integer.valueOf(asset.maxY)).add()).append(new KeyedCodec("MinY", (Codec)Codec.INTEGER, true), (asset, v) -> asset.minY = v.intValue(), asset -> Integer.valueOf(asset.minY)).add()).append(new KeyedCodec("Positions", (Codec)PositionProviderAsset.CODEC, true), (asset, v) -> asset.positionProviderAsset = v, asset -> asset.positionProviderAsset).add()).build();
/*    */   }
/* 31 */   private int maxY = 0;
/* 32 */   private int minY = 0;
/* 33 */   private PositionProviderAsset positionProviderAsset = new ListPositionProviderAsset();
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public PositionProvider build(@Nonnull PositionProviderAsset.Argument argument) {
/* 38 */     if (skip()) {
/* 39 */       return PositionProvider.noPositionProvider();
/*    */     }
/*    */     
/* 42 */     PositionProvider positionProvider = this.positionProviderAsset.build(argument);
/* 43 */     return (PositionProvider)new VerticalEliminatorPositionProvider(this.minY, this.maxY, positionProvider);
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 48 */     this.positionProviderAsset.cleanUp();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\positionproviders\VerticalEliminatorPositionProviderAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */