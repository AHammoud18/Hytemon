/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.positionproviders;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.positionproviders.OffsetPositionProvider;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.positionproviders.PositionProvider;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class OffsetPositionProviderAsset
/*    */   extends PositionProviderAsset
/*    */ {
/*    */   public static final BuilderCodec<OffsetPositionProviderAsset> CODEC;
/*    */   
/*    */   static {
/* 35 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(OffsetPositionProviderAsset.class, OffsetPositionProviderAsset::new, PositionProviderAsset.ABSTRACT_CODEC).append(new KeyedCodec("OffsetX", (Codec)Codec.INTEGER, true), (asset, v) -> asset.offsetX = v.intValue(), asset -> Integer.valueOf(asset.offsetX)).add()).append(new KeyedCodec("OffsetY", (Codec)Codec.INTEGER, true), (asset, v) -> asset.offsetY = v.intValue(), asset -> Integer.valueOf(asset.offsetY)).add()).append(new KeyedCodec("OffsetZ", (Codec)Codec.INTEGER, true), (asset, v) -> asset.offsetZ = v.intValue(), asset -> Integer.valueOf(asset.offsetZ)).add()).append(new KeyedCodec("Positions", (Codec)PositionProviderAsset.CODEC, true), (asset, v) -> asset.positionProviderAsset = v, asset -> asset.positionProviderAsset).add()).build();
/*    */   }
/* 37 */   private int offsetX = 0;
/* 38 */   private int offsetY = 0;
/* 39 */   private int offsetZ = 0;
/* 40 */   private PositionProviderAsset positionProviderAsset = new ListPositionProviderAsset();
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public PositionProvider build(@Nonnull PositionProviderAsset.Argument argument) {
/* 45 */     if (skip()) {
/* 46 */       return PositionProvider.noPositionProvider();
/*    */     }
/*    */     
/* 49 */     PositionProvider positionProvider = this.positionProviderAsset.build(argument);
/* 50 */     return (PositionProvider)new OffsetPositionProvider(new Vector3i(this.offsetX, this.offsetY, this.offsetZ), positionProvider);
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 55 */     this.positionProviderAsset.cleanUp();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\positionproviders\OffsetPositionProviderAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */